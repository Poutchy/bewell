package com.taass.booking_service.controller;


import com.taass.booking_service.dto.BookingDTO;
import com.taass.booking_service.dto.BookingRequest;
import com.taass.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/addBooking")
    public ResponseEntity<BookingDTO> addBooking(@RequestBody BookingRequest bookingRequest) {
        BookingDTO createdBooking = bookingService.addBooking(bookingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        BookingDTO booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<BookingDTO>> getBookingByClientId(@PathVariable Long clientId) {
        List<BookingDTO> booking = bookingService.getBookingByClientId(clientId);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<BookingDTO>> getBookingBySalonId(@PathVariable Long salonId) {
        List<BookingDTO> booking = bookingService.getBookingBySalonId(salonId);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<BookingDTO>> getBookingByServiceId(@PathVariable Long serviceId) {
        List<BookingDTO> booking = bookingService.getBookingByServiceId(serviceId);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<BookingDTO>> getBookingByEmployeeId(@PathVariable Long employeeId) {
        List<BookingDTO> booking = bookingService.getBookingByEmployeeId(employeeId);
        return ResponseEntity.ok(booking);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBookingById(@PathVariable Long id) {
        bookingService.deleteBookingWithId(id);
    }

    @PutMapping("/update")
    public ResponseEntity<BookingDTO> updateBooking(@RequestBody BookingDTO bookingDTO) {
        BookingDTO updatedBooking = bookingService.updateBooking(bookingDTO);
        return ResponseEntity.ok(updatedBooking);
    }
}
