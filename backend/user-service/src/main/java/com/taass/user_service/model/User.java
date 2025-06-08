package com.taass.user_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private int id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDateTime birthday;
    private Integer gender;
    private LocalDate registrationDate;
    private Integer loyaltyPoint;
    private int flag;
}
