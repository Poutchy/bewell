package com.taass.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone_number;
    private String address;
    private LocalDateTime birthday;
    private Integer gender;
    private LocalDate registration_date;
    private Integer loyalty_point;
    private Integer flag;
}
