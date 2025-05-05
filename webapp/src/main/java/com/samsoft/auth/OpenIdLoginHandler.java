package com.samsoft.auth;

import com.samsoft.auth.model.BasicUserProfile;
import com.samsoft.auth.model.User;
import com.samsoft.auth.model.UserRepository;
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

    private final UserRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void handleLogin(BasicUserProfile basicUserProfile) {
        UserDetails loggedInUser = userInfoRepository.findByUsername(basicUserProfile.email()).orElseGet(() -> {
            log.debug("Handling new user registration");
            User entity = new User();
            entity.setUsername(basicUserProfile.email());
            entity.setFullName(basicUserProfile.fullName());
            entity.setEnabled(true);
            entity.setPassword(passwordEncoder.encode(RandomStringUtils.secure().nextAlphanumeric(10)));
            return userInfoRepository.save(entity);
        });
        log.debug("User with email = {} logged in", loggedInUser.getUsername());
    }
}