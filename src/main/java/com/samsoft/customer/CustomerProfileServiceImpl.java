package com.samsoft.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
class CustomerProfileServiceImpl implements CustomerProfileService {
    @Override
    public Collection<CustomerProfile> list(PageRequest pageRequest) {
        return List.of();
    }

    @Override
    public void update(CustomerProfile customerProfile) {
    }

    @Override
    public Optional<CustomerProfile> get(Long id) {
        return Optional.empty();
    }
}
