package com.example.demo;

import com.example.demo.repository.role.Role;
import com.example.demo.repository.user.User;
import com.example.demo.service.role.RoleService;
import com.example.demo.service.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService, RoleService roleService) {
		return args -> {
			roleService.saveRole(new Role(null, "ROLE_USER"));
			roleService.saveRole(new Role(null, "ROLE_MANAGER"));
			roleService.saveRole(new Role(null, "ROLE_ADMIN"));
			roleService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "Pedro Sanchez", "pedro", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Florencia Gomez", "flor", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Lucia Diaz", "lu", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Jose Rodriguez", "jose", "1234", new ArrayList<>()));

			userService.addRoleToUser("pedro", "ROLE_USER");
			userService.addRoleToUser("pedro", "ROLE_MANAGER");
			userService.addRoleToUser("flor", "ROLE_MANAGER");
			userService.addRoleToUser("lu", "ROLE_ADMIN");
			userService.addRoleToUser("jose", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("jose", "ROLE_ADMIN");
			userService.addRoleToUser("jose", "ROLE_USER");
		};
	}
}
