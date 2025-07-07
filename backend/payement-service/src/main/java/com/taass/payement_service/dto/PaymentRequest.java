package com.taass.payement_service.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaymentRequest {
    private String method; // Default amount if isn't provided
    private Double amount; // Default amount if isn't provided
    private String currency; // Default currency if isn't provided
    private String description; // Description of the payment
    private Long bookingId; // ID of the booking associated with this payment
    private Long clientId; // ID of the client making the payment
    private String createdAt; // Timestamp of when the payment was created
}
