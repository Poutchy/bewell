package com.taass.payement_service.dto;

import com.taass.payement_service.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id; // Unique identifier for the payment
    private String method; // Default amount if isn't provided
    private Double amount; // Default amount if isn't provided
    private String currency; // Default currency if isn't provided
    private String description; // Description of the payment
    private Long bookingId; // ID of the booking associated with this payment
    private Long clientId; // ID of the client making the payment
    private PaymentStatus status; // Status of the payment (e.g., "pending", "completed", "failed")
    private String createdAt; // Timestamp of when the payment was created
}
