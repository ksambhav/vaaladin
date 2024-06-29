package com.samsoft.data.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDetails {
    @NotBlank
    private String fullName;

    @NotBlank
    @Email
    private String email;

    @Size(min = 8, max = 64, message = "Password must be 8-64 char long")
    private String password;
}
