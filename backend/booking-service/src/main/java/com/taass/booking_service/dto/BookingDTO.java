package com.taass.booking_service.dto;

import com.taass.booking_service.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long id;
    private Long salonId;
    private String tStart;
    private String tEnd;
    private Long slotId;
    private Long clientId;
    private Long employeeId;
    private Long serviceId;
    private Boolean payed;
    private BookingStatus status; // e.g., "confirmed", "cancelled", "completed"
}
