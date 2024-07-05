package com.samsoft.auth;

import com.samsoft.data.model.UserInfo;
import com.samsoft.data.repo.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class OpenIdLoginHandler {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void handleLogin(BasicUserProfile basicUserProfile) {
        UserDetails loggedInUser = userInfoRepository.findByUsername(basicUserProfile.email()).orElseGet(() -> {
            log.debug("Handling new user registration");
            return userInfoRepository.save(new UserInfo(basicUserProfile.email(),
                    basicUserProfile.profilePicture(),
                    basicUserProfile.fullName(),
                    passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(10)),
                    true));
        });
        log.debug("User with email = {} logged in", loggedInUser.getUsername());
    }
}
