package com.samsoft.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test"})
@DataJdbcTest
class UserInfoRepositoryTest {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    void testUserSave() {
        Assertions.assertNotNull(userInfoRepository);
        userInfoRepository.findAll();
    }
}