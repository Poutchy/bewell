package com.taass.booking_service.service;

import com.taass.booking_service.BookingNotFoundException;
import com.taass.booking_service.dto.BookingDTO;
import com.taass.booking_service.dto.BookingRequest;
import com.taass.booking_service.model.Booking;
import com.taass.booking_service.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;

    public void addBooking(BookingRequest bookingRequest) {
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
        bookingRepository.save(booking);
        log.info("Booking added: {}", booking.getId());
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
                .build();
    }
}
