package com.mkraskiewicz.bookstore.service.impl;

import com.mkraskiewicz.bookstore.domain.Role;
import com.mkraskiewicz.bookstore.domain.RoleName;
import com.mkraskiewicz.bookstore.repository.RoleRepository;
import com.mkraskiewicz.bookstore.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> getRole(RoleName role) {
        return roleRepository.findByName(role);
    }
}
