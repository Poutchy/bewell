package com.taass.user_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phone_number;

    @Column(name = "address")
    private String address;

    @Column(name = "birthday")
    private LocalDateTime birthday;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "registration_date")
    private LocalDate registration_date;

    @Column(name = "loyalty_point")
    private Integer loyalty_point;

    @Column(name = "flag", nullable = false)
    private Integer flag;
}

