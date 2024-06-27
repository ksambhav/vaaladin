package com.samsoft.auth;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@DataJdbcTest
@RequiredArgsConstructor
class UserInfoRepositoryTest {

    private final UserInfoRepository userInfoRepository;

    @Test
    void testUserSave() {
        userInfoRepository.findAll();
    }
}