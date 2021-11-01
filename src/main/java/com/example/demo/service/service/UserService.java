package com.example.demo.service.service;

import com.example.demo.repository.user.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
