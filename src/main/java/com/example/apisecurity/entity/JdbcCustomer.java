package com.example.apisecurity.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@Setter
public class JdbcCustomer {
    @Id
    private int customerId;
    private String fullName;
    private String email;
    private LocalDate birthDate;
    private String gender;
}
