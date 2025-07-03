package com.taass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingMessageDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long orderId;

    private String SalonName;
    private  String salonAddress;
    private String salonPhone;
    private String salonEmail;

    private String tStart;
    private String tEnd;

    private String clientName;
    private String clientSurname;
    private  String clientEmail;

    private String serviceName;

    private Boolean payed;
}