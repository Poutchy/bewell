package com.taass.notification_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Notification {
    private Long bookingId; // ID of the associated booking

    private Long clientId; // ID of the client receiving the notification

    private String timestamp; // ISO 8601 format

    private NotifcationStatus status; // e.g., "sent", "failed", "delivered"
}
