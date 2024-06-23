package com.samsoft.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface UserProfileManager {

    void register(UserRegistrationRequest request);

    record UserRegistrationRequest(@NotBlank String fullName, @Email String email, @NotNull char[] password) {
    }
}
