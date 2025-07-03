package com.taass.booking_service.service;

import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RabbitTemplate rabbitTemplate;
    private final WebClient clientWebClient;
    private final WebClient salonWebClient;

    private BookingStatus bookingStatus;
    private final HashMap<String, String> bookingDetails = new HashMap<>();

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


// Uncomment the following lines to simulate a bookingDetails map for testing purposes
//        bookingDetails.put("salonName", (String) "salonName");
//        bookingDetails.put("salonAddress", (String) "salonAddress");
//        bookingDetails.put("salonPhone", (String) "3493618984");
//        bookingDetails.put("salonEmail", (String) "salonEmail@emal.com");
//        bookingDetails.put("clientName", (String) "Matteo");
//        bookingDetails.put("clientSurname", (String) "Bussolino");
//        bookingDetails.put("clientEmail", (String) "matteobussolino7@gmail.com");
//        bookingDetails.put("serviceName", (String) "Haircut");

        if(isBookingValid){
            try{
                BookingMessageDTO bookingMessageDTO = new BookingMessageDTO(
                        savedBooking.getId(),
                        bookingDetails.get("salonName"),
                        bookingDetails.get("salonAddress"),
                        bookingDetails.get("salonPhone"),
                        bookingDetails.get("salonEmail"),
                        savedBooking.getTStart(),
                        savedBooking.getTEnd(),
                        bookingDetails.get("clientName"),
                        bookingDetails.get("clientSurname"),
                        bookingDetails.get("clientEmail"),
                        bookingDetails.get("serviceName"),
                        savedBooking.getPayed()
                );

                log.info("BookingMessageDTO created: {}, {}, {}", bookingMessageDTO.getSalonName(), bookingMessageDTO.getTStart(), bookingMessageDTO.getTEnd());

                log.info("Booking {} approved. Publishing BookingMessageDTO to exchange '{}' with key '{}'",
                        savedBooking.getId(), exchangeName, bookingCreatedRoutingKey);

                rabbitTemplate.setMandatory(true);

                rabbitTemplate.setReturnsCallback(ret -> log.error("UNROUTED message {}", ret));

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
        try {
            String jsonResponse = salonWebClient.get()
                    .uri("/api/salons/{id}", salonId)
                    .retrieve() // Ottiene la risposta
                    .bodyToMono(String.class)
                    .block(); // Blocca finché la chiamata non è completa (ATTENZIONE: non ideale in scenari high-concurrency)
            log.info("Salon {} validation successful", salonId);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});

            bookingDetails.put("salonName", (String) responseMap.get("name"));
            bookingDetails.put("salonAddress", (String) responseMap.get("address"));
            bookingDetails.put("salonPhone", (String) responseMap.get("phone"));
            bookingDetails.put("salonEmail", (String) responseMap.get("email"));

            return true;
        } catch (WebClientResponseException e) {
            log.warn("Salon validation failed for {}: Status Code {}", salonId, e.getStatusCode(), e);
            return false;
        } catch (Exception e) {
            log.error("Error calling Salon service for Salon {}", salonId, e);
            return false;
        }
    }

    private boolean validateSlot(Long slotId) {
        log.info("Calling Salon Service to validate slot {}", slotId);
        try {
            String jsonResponse = salonWebClient.get()
                    .uri("/api/timeslots/{id}", slotId)
                    .retrieve() // Ottiene la risposta
                    .bodyToMono(String.class) // Non ci interessa il body, solo lo status code
                    .block(); // Blocca finché la chiamata non è completa (ATTENZIONE: non ideale in scenari high-concurrency)
            log.info("Slot {} validation successful", slotId);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});

            bookingDetails.put("startTime", (String) responseMap.get("start"));
            bookingDetails.put("endTime", (String) responseMap.get("end"));

            return true;
        } catch (WebClientResponseException e) {
            log.warn("Slot validation failed for {}: Status Code {}", slotId, e.getStatusCode(), e);
            return false;
        } catch (Exception e) {
            log.error("Error calling Salon service for Slot {}", slotId, e);
            return false;
        }
    }

    private boolean validateClient(Long clientId) {
        log.info("Calling Client Service to validate client {}", clientId);
        try {
            String jsonResponce = clientWebClient.get()
                    .uri("/api/clients/getClient/{id}", clientId)
                    .retrieve() // Ottiene la risposta
                    .bodyToMono(String.class) // Non ci interessa il body, solo lo status code
                    .block(); // Blocca finché la chiamata non è completa (ATTENZIONE: non ideale in scenari high-concurrency)
            log.info("Client {} validation successful", clientId);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(jsonResponce, new TypeReference<Map<String, Object>>() {});

            bookingDetails.put("clientName", (String) responseMap.get("name"));
            bookingDetails.put("clientSurname", (String) responseMap.get("surname"));
            bookingDetails.put("clientEmail", (String) responseMap.get("email"));


            return true;
        } catch (WebClientResponseException e) {
            log.warn("Client validation failed for {}: Status Code {}", clientId, e.getStatusCode(), e);
            return false;
        } catch (Exception e) {
            log.error("Error calling Client service for client {}", clientId, e);
            return false;
        }
    }
    private boolean validateEmployee(Long employeeId) {
        log.info("Calling Employee Service to validate employee {}", employeeId);
        return true;
    }

    private boolean validateService(Long serviceId) {
        log.info("Calling Salon Service to validate service {}", serviceId);
        try {
            String jsonResponce = salonWebClient.get()
                    .uri("/api/services/{id}", serviceId)
                    .retrieve() // Ottiene la risposta
                    .bodyToMono(String.class) // Non ci interessa il body, solo lo status code
                    .block(); // Blocca finché la chiamata non è completa (ATTENZIONE: non ideale in scenari high-concurrency)
            log.info("Service {} validation successful", serviceId);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(jsonResponce, new TypeReference<Map<String, Object>>() {});

            bookingDetails.put("serviceName", (String) responseMap.get("name"));

            return true;
        } catch (WebClientResponseException e) {
            log.warn("Service validation failed for {}: Status Code {}", serviceId, e.getStatusCode(), e);
            return false;
        } catch (Exception e) {
            log.error("Error calling Service service for service {}", serviceId, e);
            return false;
        }
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
