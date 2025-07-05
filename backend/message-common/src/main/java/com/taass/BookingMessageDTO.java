package com.taass;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class BookingMessageDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long orderId;

    private String salonName;
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

    public BookingMessageDTO(Long orderId, String salonName, String salonAddress, String salonPhone, String salonEmail, String tStart, String tEnd, String clientName, String clientSurname, String clientEmail, String serviceName, Boolean payed) {
        this.orderId = orderId;
        this.salonName = salonName;
        this.salonAddress = salonAddress;
        this.salonPhone = salonPhone;
        this.salonEmail = salonEmail;
        this.tStart = tStart;
        this.tEnd = tEnd;
        this.clientName = clientName;
        this.clientSurname = clientSurname;
        this.clientEmail = clientEmail;
        this.serviceName = serviceName;
        this.payed = payed;
    }

    public BookingMessageDTO() {}


    public Long getOrderId() {
        return orderId;
    }

    public String getSalonName() {
        return salonName;
    }

    public String getSalonAddress() {
        return salonAddress;
    }

    public String getSalonPhone() {
        return salonPhone;
    }

    public String getSalonEmail() {
        return salonEmail;
    }

    public String getTStart() {
        return tStart;
    }

    public String getTEnd() {
        return tEnd;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Boolean getPayed() {
        return payed;
    }
}