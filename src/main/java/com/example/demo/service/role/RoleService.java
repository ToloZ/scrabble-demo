package com.example.demo.service.role;

import com.example.demo.repository.role.Role;

import java.util.List;

public interface RoleService {
    Role saveRole(Role role);
    List<Role> getRoles();
}
