package com.samsoft.data.repo;

import com.samsoft.data.model.UserInfo;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserInfoRepository extends ListCrudRepository<UserInfo, Integer> {
    Optional<UserDetails> findByUsername(String username);
}
