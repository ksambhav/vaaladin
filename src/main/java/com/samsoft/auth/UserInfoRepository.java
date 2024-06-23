package com.samsoft.auth;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {
    Optional<UserDetails> findByUsername(String username);
}
