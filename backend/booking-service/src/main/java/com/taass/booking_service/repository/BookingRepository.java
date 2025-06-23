package com.taass.booking_service.repository;

import com.taass.booking_service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    ArrayList<Booking> getAllByClientId(Long clientId);

    ArrayList<Booking> getAllBySalonId(Long salonId);

    ArrayList<Booking> getAllByServiceId(Long serviceId);

    ArrayList<Booking> getAllByEmployeeId(Long employeeId);
}
