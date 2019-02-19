package com.web.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.domain.User;
import com.web.domain.UserRepository;

@RestController
public class RegisterRestController {

	private UserRepository userRepository;
	
	public RegisterRestController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@PutMapping("/register")
	public void register(@RequestBody User user) {
		userRepository.save(user);
	}
}
