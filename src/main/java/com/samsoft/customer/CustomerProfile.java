package com.samsoft.customer;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerProfile {

    private Long id;
    private String fullName;
    private String email;
    private String mobile;
    private LocalDate dateOfBirth;
    private String gender;
}
