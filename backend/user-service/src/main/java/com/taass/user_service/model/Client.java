package com.taass.user_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String surname;

    private String email;

    private String phone_number;

    private String address;

    private LocalDate birthday;

    private Integer gender;

    private LocalDate registration_date;

    private Integer loyalty_point;

    private Integer flag;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", address='" + address + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", registration_date=" + registration_date +
                ", loyalty_point=" + loyalty_point +
                ", flag=" + flag +
                '}';
    }
}

