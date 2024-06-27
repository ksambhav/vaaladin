package com.samsoft.auth;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.test.ApplicationModuleTest;

@ApplicationModuleTest
@RequiredArgsConstructor
class DefaultUserProfileManagerTest {

    private final UserProfileManager userProfileManager;

    @Test
    void register() {
        Assertions.assertNotNull(userProfileManager);
    }

    @Test
    void loadUserByUsername() {
    }
}