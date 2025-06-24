package com.taass.booking_service.controller;


import com.taass.booking_service.dto.BookingDTO;
import com.taass.booking_service.dto.BookingRequest;
import com.taass.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/addBooking")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBooking(@RequestBody BookingRequest bookingRequest) {
        bookingService.addBooking(bookingRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookingDTO getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping("/client/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDTO> getBookingByClientId(@PathVariable Long clientId) {
        return bookingService.getBookingByClientId(clientId);
    }

    @GetMapping("/salon/{salonId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDTO> getBookingBySalonId(@PathVariable Long salonId) {
        return bookingService.getBookingBySalonId(salonId);
    }

    @GetMapping("/service/{serviceId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDTO> getBookingByServiceId(@PathVariable Long serviceId) {
        return bookingService.getBookingByServiceId(serviceId);
    }

    @GetMapping("/employee/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDTO> getBookingByEmployeeId(@PathVariable Long employeeId) {
        return bookingService.getBookingByEmployeeId(employeeId);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBookingById(@PathVariable Long id) {
        bookingService.deleteBookingWithId(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public BookingDTO updateBooking(@RequestBody BookingDTO bookingDTO) {
        return bookingService.updateBooking(bookingDTO);
    }
}
