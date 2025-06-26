package com.taass.booking_service.service;

import com.taass.BookingMessageDTO;
import com.taass.booking_service.exceptions.BookingNotFoundException;
import com.taass.booking_service.dto.BookingDTO;
import com.taass.booking_service.dto.BookingRequest;
import com.taass.booking_service.model.Booking;
import com.taass.booking_service.model.BookingStatus;
import com.taass.booking_service.repository.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RabbitTemplate rabbitTemplate;
    private BookingStatus bookingStatus;

    @Value("${app.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${app.rabbitmq.routingkey.booking-created}")
    private String bookingCreatedRoutingKey;

    @Transactional
    public BookingDTO addBooking(BookingRequest bookingRequest) {
        log.info("Adding booking for request {}", bookingRequest);

        Booking booking = Booking.builder()
                .salonId(bookingRequest.getSalonId())
                .tStart(bookingRequest.getStart())
                .tEnd(bookingRequest.getEnd())
                .slotId(bookingRequest.getSlotId())
                .clientId(bookingRequest.getClientId())
                .employeeId(bookingRequest.getEmployeeId())
                .serviceId(bookingRequest.getServiceId())
                .payed(bookingRequest.getPayed())
                .build();

        booking.setStatus(BookingStatus.PENDING_VALIDATION); // Default status

        Booking savedBooking =  bookingRepository.save(booking);

        bookingStatus = BookingStatus.PENDING_VALIDATION; // Default status
        boolean isBookingValid = validateBookingRequest(bookingRequest);

        if(isBookingValid){
            try{
                BookingMessageDTO bookingMessageDTO = new BookingMessageDTO(
                        savedBooking.getSalonId(),
                        savedBooking.getTStart(),
                        savedBooking.getTEnd(),
                        savedBooking.getSlotId(),
                        savedBooking.getClientId(),
                        savedBooking.getEmployeeId(),
                        savedBooking.getServiceId(),
                        savedBooking.getPayed()
                );

                log.info("Booking {} approved. Publishing BookingMessageDTO to exchange '{}' with key '{}'",
                        savedBooking.getId(), exchangeName, bookingCreatedRoutingKey);

                rabbitTemplate.setMandatory(true);

                rabbitTemplate.setReturnsCallback(ret -> {
                    log.error("UNROUTED message {}", ret);
                });

                rabbitTemplate.setConfirmCallback((cd,ack,c) ->
                        log.info("Broker ack? {}  cause: {}", ack, c));

                rabbitTemplate.convertAndSend(exchangeName, bookingCreatedRoutingKey, bookingMessageDTO);

            } catch (Exception e) {
                log.error("Error while saving booking for request {}", bookingRequest);
                bookingStatus = BookingStatus.FAILED;
            }
        }

        savedBooking.setStatus(bookingStatus);

        log.info("Booking added: {}", booking.getId());
        Booking finalBooking = bookingRepository.save(savedBooking);

        return mapToBookingDTO(finalBooking);
    }

    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id.toString()));
        return mapToBookingDTO(booking);
    }

    public List<BookingDTO> getBookingByClientId(Long clientId) {
        ArrayList<Booking> bookings = bookingRepository.getAllByClientId(clientId);

        if (bookings.isEmpty()) {
            log.warn("No bookings found for client with id: {}", clientId);
            return new ArrayList<>();
        }

        return bookings.stream().map(this::mapToBookingDTO).toList();
    }

    public List<BookingDTO> getBookingBySalonId(Long salonId) {
        ArrayList<Booking> bookings = bookingRepository.getAllBySalonId(salonId);

        if (bookings.isEmpty()) {
            log.warn("No bookings found for salon with id: {}", salonId);
            return new ArrayList<>();
        }

        return bookings.stream().map(this::mapToBookingDTO).toList();
    }

    public List<BookingDTO> getBookingByServiceId(Long serviceId) {
        ArrayList<Booking> bookings = bookingRepository.getAllByServiceId(serviceId);

        if (bookings.isEmpty()) {
            log.warn("No bookings found for service with id: {}", serviceId);
            return new ArrayList<>();
        }

        return bookings.stream().map(this::mapToBookingDTO).toList();
    }

    public List<BookingDTO> getBookingByEmployeeId(Long employeeId) {
        ArrayList<Booking> bookings = bookingRepository.getAllByEmployeeId(employeeId);

        if (bookings.isEmpty()) {
            log.warn("No bookings found for employee with id: {}", employeeId);
            return new ArrayList<>();
        }

        return bookings.stream().map(this::mapToBookingDTO).toList();
    }

    public void deleteBookingWithId(Long id) {
        bookingRepository.deleteById(id);
        log.info("Booking deleted: {}", id);
    }

    public BookingDTO updateBooking(BookingDTO bookingDTO) {
        log.info("Updating booking with id: {}", bookingDTO.getId());
        validateBookingDTO(bookingDTO);

        Booking existingBooking = bookingRepository.findById(bookingDTO.getId())
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingDTO.getId()));

        existingBooking.setId(bookingDTO.getId());
        existingBooking.setSalonId(bookingDTO.getSalonId());
        existingBooking.setTStart(bookingDTO.getTStart());
        existingBooking.setTEnd(bookingDTO.getTEnd());
        existingBooking.setSlotId(bookingDTO.getSlotId());
        existingBooking.setClientId(bookingDTO.getClientId());
        existingBooking.setEmployeeId(bookingDTO.getEmployeeId());
        existingBooking.setServiceId(bookingDTO.getServiceId());
        existingBooking.setPayed(bookingDTO.getPayed() != null ? bookingDTO.getPayed() : false); // Default to false if not provided
        existingBooking.setStatus(bookingDTO.getStatus());

        log.info("Booking updated: {}", existingBooking.getId());
        return mapToBookingDTO(bookingRepository.save(existingBooking));
    }

    private void validateBookingDTO(BookingDTO bookingDTO) {
        if (bookingDTO.getId() == null) {
            throw new IllegalArgumentException("Booking ID is required for updates.");
        }
        if (bookingDTO.getSalonId() == null || bookingDTO.getClientId() == null || bookingDTO.getEmployeeId() == null) {
            throw new IllegalArgumentException("Salon ID, Client ID, and Employee ID are required.");
        }
        if (bookingDTO.getTStart() == null || bookingDTO.getTEnd() == null) {
            throw new IllegalArgumentException("Start and end times are required.");
        }
        if (bookingDTO.getServiceId() == null) {
            throw new IllegalArgumentException("Service ID is required.");
        }
        if (bookingDTO.getPayed() == null) {
            bookingDTO.setPayed(false); // Default to false if not provided
        }
    }

    private boolean validateBookingRequest(BookingRequest bookingRequest) {
        if (bookingRequest.getSalonId() == null || bookingRequest.getClientId() == null || bookingRequest.getEmployeeId() == null) {
            log.error("Invalid booking request: Missing required fields.");
            bookingStatus = BookingStatus.INVALID;

            return false;
        }
        if (bookingRequest.getStart() == null || bookingRequest.getEnd() == null) {
            log.error("Invalid booking request: Start and end times are required.");
            bookingStatus = BookingStatus.INVALID;

            return false;
        }
        if (bookingRequest.getServiceId() == null) {
            log.error("Invalid booking request: Service ID is required.");
            bookingStatus = BookingStatus.INVALID;

            return false;
        }
        if (bookingRequest.getSlotId() == null) {
            log.error("Invalid booking request: Slot ID is required.");
            bookingStatus = BookingStatus.INVALID;

            return false;
        }
        if (bookingRequest.getPayed() == null) {
            bookingRequest.setPayed(false); // Default to false if not provided
        }

        if(!validateSalon(bookingRequest.getSalonId())) {
            log.error("Invalid booking request: Salon validation failed for salon ID {}", bookingRequest.getSalonId());
            bookingStatus = BookingStatus.REJECTED_SALON;

            return false;
        }

        if(!validateClient(bookingRequest.getClientId())) {
            log.error("Invalid booking request: Client validation failed for client ID {}", bookingRequest.getClientId());
            bookingStatus = BookingStatus.REJECTED_CLIENT;

            return false;
        }

        if(!validateEmployee(bookingRequest.getEmployeeId())) {
            log.error("Invalid booking request: Employee validation failed for employee ID {}", bookingRequest.getEmployeeId());
            bookingStatus = BookingStatus.REJECTED_EMPLOYEE;

            return false;
        }

        if(!validateService(bookingRequest.getServiceId())) {
            log.error("Invalid booking request: Service validation failed for service ID {}", bookingRequest.getServiceId());
            bookingStatus = BookingStatus.REJECTED_SERVICE;

            return false;
        }

        if(!validateSlot(bookingRequest.getSlotId())) {
            log.error("Invalid booking request: Slot validation failed for slot ID {}", bookingRequest.getSlotId());
            bookingStatus = BookingStatus.REJECTED_SLOT;

            return false;
        }

        log.info("Booking request validation successful for salon ID {}, client ID {}, employee ID {}, service ID {}, slot ID {}",
                bookingRequest.getSalonId(), bookingRequest.getClientId(), bookingRequest.getEmployeeId(), bookingRequest.getServiceId(), bookingRequest.getSlotId());
        bookingStatus = BookingStatus.CONFIRMED; // Set to pending if all validations pass

        return true;
    }

    private boolean validateSalon(Long salonId) {
        log.info("Calling Salon Service to validate salon {}", salonId);
//        try {
//            restaurantWebClient.get()
//                    .uri("/restaurants/{id}/validate", salonId)
//                    .retrieve() // Ottiene la risposta
//                    .toBodilessEntity() // Non ci interessa il body, solo lo status code
//                    .block(); // Blocca finché la chiamata non è completa (ATTENZIONE: non ideale in scenari high-concurrency)
//            log.info("Restaurant {} validation successful", salonId);
//            return true;
//        } catch (WebClientResponseException e) {
//            log.warn("Restaurant validation failed for {}: Status Code {}", salonId, e.getStatusCode(), e);
//            // Potremmo differenziare 404 da altri errori (5xx)
//            return false;
//        } catch (Exception e) {
//            log.error("Error calling restaurant service for restaurant {}", salonId, e);
//            return false; // Considera qualsiasicccccc       fd eccezione come fallimento
//        }
        return true;
    }

    private boolean validateSlot(Long slotId) {
        log.info("Calling Salon Service to validate slot {}", slotId);
//        try {
//            restaurantWebClient.get()
//                    .uri("/restaurants/{id}/validate", slotId)
//                    .retrieve() // Ottiene la risposta
//                    .toBodilessEntity() // Non ci interessa il body, solo lo status code
//                    .block(); // Blocca finché la chiamata non è completa (ATTENZIONE: non ideale in scenari high-concurrency)
//            log.info("Restaurant {} validation successful", slotId);
//            return true;
//        } catch (WebClientResponseException e) {
//            log.warn("Restaurant validation failed for {}: Status Code {}", salonId, e.getStatusCode(), e);
//            // Potremmo differenziare 404 da altri errori (5xx)
//            return false;
//        } catch (Exception e) {
//            log.error("Error calling restaurant service for restaurant {}", slotId, e);
//            return false; // Considera qualsiasicccccc       fd eccezione come fallimento
//        }
        return true;
    }

    private boolean validateClient(Long clientId) {
        log.info("Calling Client Service to validate client {}", clientId);
//        try {
//            restaurantWebClient.get()
//                    .uri("/restaurants/{id}/validate", clientId)
//                    .retrieve() // Ottiene la risposta
//                    .toBodilessEntity() // Non ci interessa il body, solo lo status code
//                    .block(); // Blocca finché la chiamata non è completa (ATTENZIONE: non ideale in scenari high-concurrency)
//            log.info("Restaurant {} validation successful", clientId);
//            return true;
//        } catch (WebClientResponseException e) {
//            log.warn("Restaurant validation failed for {}: Status Code {}", clientId, e.getStatusCode(), e);
//            // Potremmo differenziare 404 da altri errori (5xx)
//            return false;
//        } catch (Exception e) {
//            log.error("Error calling restaurant service for restaurant {}", clientId, e);
//            return false; // Considera qualsiasicccccc       fd eccezione come fallimento
//        }
        return true;
    }
    private boolean validateEmployee(Long employeeId) {
        log.info("Calling Employee Service to validate employee {}", employeeId);
//        try {
//            restaurantWebClient.get()
//                    .uri("/restaurants/{id}/validate", clientId)
//                    .retrieve() // Ottiene la risposta
//                    .toBodilessEntity() // Non ci interessa il body, solo lo status code
//                    .block(); // Blocca finché la chiamata non è completa (ATTENZIONE: non ideale in scenari high-concurrency)
//            log.info("Restaurant {} validation successful", clientId);
//            return true;
//        } catch (WebClientResponseException e) {
//            log.warn("Restaurant validation failed for {}: Status Code {}", employeeId, e.getStatusCode(), e);
//            // Potremmo differenziare 404 da altri errori (5xx)
//            return false;
//        } catch (Exception e) {
//            log.error("Error calling restaurant service for restaurant {}", employeeId, e);
//            return false; // Considera qualsiasicccccc       fd eccezione come fallimento
//        }
        return true;
    }

    private boolean validateService(Long serviceId) {
        log.info("Calling Employee Service to validate employee {}", serviceId);
//        try {
//            restaurantWebClient.get()
//                    .uri("/restaurants/{id}/validate", serviceId)
//                    .retrieve() // Ottiene la risposta
//                    .toBodilessEntity() // Non ci interessa il body, solo lo status code
//                    .block(); // Blocca finché la chiamata non è completa (ATTENZIONE: non ideale in scenari high-concurrency)
//            log.info("Restaurant {} validation successful", serviceId);
//            return true;
//        } catch (WebClientResponseException e) {
//            log.warn("Restaurant validation failed for {}: Status Code {}", serviceId, e.getStatusCode(), e);
//            // Potremmo differenziare 404 da altri errori (5xx)
//            return false;
//        } catch (Exception e) {
//            log.error("Error calling restaurant service for restaurant {}", serviceId, e);
//            return false; // Considera qualsiasicccccc       fd eccezione come fallimento
//        }
        return true;
    }


    private BookingDTO mapToBookingDTO(Booking booking) {
        return BookingDTO.builder()
                .id(booking.getId())
                .salonId(booking.getSalonId())
                .tStart(booking.getTStart())
                .tEnd(booking.getTEnd())
                .slotId(booking.getSlotId())
                .clientId(booking.getClientId())
                .employeeId(booking.getEmployeeId())
                .serviceId(booking.getServiceId())
                .payed(booking.getPayed())
                .status(booking.getStatus())
                .build();
    }
}
