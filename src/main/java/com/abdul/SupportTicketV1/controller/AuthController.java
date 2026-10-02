package com.abdul.SupportTicketV1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdul.SupportTicketV1.dto.LoginRequest;
import com.abdul.SupportTicketV1.dto.LoginResponse;
import com.abdul.SupportTicketV1.dto.UserResponse;
import com.abdul.SupportTicketV1.entity.User;
import com.abdul.SupportTicketV1.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AuthController {
	@Autowired
	AuthService authService;
	@PostMapping("/signup")
	public ResponseEntity<UserResponse> signup(@Valid @RequestBody User user)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(user));
	}
	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest)
	{
		System.out.println("enter");
		LoginResponse res = authService.login(loginRequest);
		System.out.println(res.getCookie().toString());
		return ResponseEntity
				.status(HttpStatus.OK)
				.header(HttpHeaders.SET_COOKIE,res.getCookie().toString())
				.body("login successfully");
	}
}
