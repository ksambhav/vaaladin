package com.samsoft.customer;

import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.Optional;

public interface CustomerProfileService {
    Collection<CustomerProfile> list(PageRequest pageRequest);

    void update(CustomerProfile customerProfile);

    Optional<CustomerProfile> get(Long id);
}
