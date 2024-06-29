package com.samsoft.data.repo;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@DataJdbcTest
@RequiredArgsConstructor
class UserInfoRepositoryTest {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    void findByUsername() {
        var all = userInfoRepository.findAll();
        Assertions.assertNotNull(all);
    }
}
