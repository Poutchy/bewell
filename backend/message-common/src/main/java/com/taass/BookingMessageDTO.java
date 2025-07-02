package com.taass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@Getter
public class BookingMessageDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long salonId;
    private String tStart;
    private String tEnd;
    private Long slotId;
    private Long employeeId;
    private Long serviceId;
    private Boolean payed;

    public BookingMessageDTO(Long salonId, String tStart, String tEnd, Long slotId, Long clientId, Long employeeId, Long serviceId, Boolean payed) {
        this.salonId = salonId;
        this.tStart = tStart;
        this.tEnd = tEnd;
        this.slotId = slotId;
        this.employeeId = employeeId;
        this.serviceId = serviceId;
        this.payed = payed;
    }
}