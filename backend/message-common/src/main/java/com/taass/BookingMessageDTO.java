package com.taass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingMessageDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long salonId;
    private String tStart;
    private String tEnd;
    private Long slotId;
    private Long clientId;
    private Long employeeId;
    private Long serviceId;
    private Boolean payed;

}