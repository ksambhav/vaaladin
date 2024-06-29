package com.samsoft.auth.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public record UserRegistrationForm(
        @NotBlank String fullName,
        @Email String email,
        @NotBlank String mobile,
        @NotNull char[] password
) {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UserRegistrationForm other)) return false;
        return Objects.equals(email, other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return "UserRegistrationRequest{" +
               "fullName='" + fullName + '\'' +
               ", email='" + email + '\'' +
               ", mobile='" + mobile + '\'' +
               '}';
    }
}
