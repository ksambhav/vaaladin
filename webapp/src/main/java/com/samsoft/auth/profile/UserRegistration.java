package com.samsoft.auth.profile;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRegistration {
    private String fullName;
    private String email;
    private String mobile;
    private LocalDate dateOfBirth;
    private String password;
    private String confirmPassword;
}
