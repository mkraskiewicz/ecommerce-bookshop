package com.mkraskiewicz.bookstore.service;

import com.mkraskiewicz.bookstore.domain.Role;
import com.mkraskiewicz.bookstore.domain.RoleName;

import java.util.Optional;

public interface RoleService {

    Optional<Role> getRole(RoleName role);
}
