package com.taass.payement_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "method", nullable = false)
    private String method; // Default amount if isn't provided

    @Column(name = "amount", nullable = false)
    private Double amount; // Default amount if isn't provided

    @Column(name = "currency", nullable = false)
    private String currency; // Default currency if isn't provided

    @Column(name = "description")
    private String description; // Description of the payment

    @Column(name = "booking_id", nullable = false)
    private Long bookingId; // ID of the booking associated with this payment

    @Column(name = "client_id", nullable = false)
    private Long clientId; // ID of the client making the payment

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // Status of the payment (e.g., "pending", "completed", "failed")

    @Column(name = "created_at", nullable = false)
    private String createdAt; // Timestamp of when the payment was created
}
