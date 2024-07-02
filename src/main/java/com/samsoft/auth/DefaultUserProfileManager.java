package com.samsoft.auth;

import com.samsoft.auth.register.RegistrationRequest;
import com.samsoft.data.model.UserInfo;
import com.samsoft.data.repo.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class DefaultUserProfileManager implements UserProfileManager, UserDetailsService, InitializingBean {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegistrationRequest request) {
        userInfoRepository.save(new UserInfo(request.email(), request.mobile(),
                request.fullName(),
                passwordEncoder.encode(request.password()),
                true));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userInfoRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username %s not found".formatted(username)));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        register(new RegistrationRequest("Kumar Sambhav Jain", "kumar.sambhav.jain@gmail.com", "8527543244", "password", "password"));
    }
}
