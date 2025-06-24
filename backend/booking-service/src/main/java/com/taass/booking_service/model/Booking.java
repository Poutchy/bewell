package com.taass.booking_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salon_id", nullable = false)
    private Long salonId;

    @Column(name = "t_start", nullable = false)
    private String tStart;

    @Column(name = "t_end", nullable = false)
    private String tEnd;

    @Column(name = "slot_id", nullable = false)
    private Long slotId;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(name = "payed")
    private Boolean payed;
}
