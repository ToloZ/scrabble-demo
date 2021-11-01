package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.config.JwtUtils;
import com.example.demo.repository.role.Role;
import com.example.demo.repository.user.User;
import com.example.demo.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm user) {
        userService.addRoleToUser(user.getUserName(), user.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                DecodedJWT decodedJWT = JwtUtils.getDecodedJwt(authorizationHeader);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String accessToken = JwtUtils.getTokenString(
                        JWT.create().withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList())),
                        user.getUsername(), 10, request.getRequestURL().toString()
                );
                JwtUtils.mapToResponse(accessToken, authorizationHeader.substring("Bearer ".length()), response);
            } catch (Exception e) {
                JwtUtils.JwtError(e, response);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
