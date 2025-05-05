package com.samsoft.auth.service;

import com.samsoft.auth.model.Role;
import com.samsoft.auth.model.RoleRepo;
import com.samsoft.auth.model.User;
import com.samsoft.auth.model.UserRepository;
import com.samsoft.auth.profile.UserRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Primary
@Service
@RequiredArgsConstructor
class UserProfileService implements UserDetailsService, UserProfileManager {

    private final UserRepository userRepository;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void register(UserRegistration userRegistration) {
        User entity = new User(userRegistration.getEmail(), passwordEncoder.encode(userRegistration.getPassword()));
        entity.setEnabled(true);
        entity.setFullName(userRegistration.getFullName());
        entity.setDob(userRegistration.getDateOfBirth());
        entity.setMobile(userRegistration.getMobile());
        entity.setRoles(Set.of(roleRepo.findByName("ROLE_USER").orElseGet(() -> roleRepo.save(new Role("ROLE_USER")))));
        userRepository.save(entity);
    }
}