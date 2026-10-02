package com.abdul.SupportTicketV1.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abdul.SupportTicketV1.dto.LoginRequest;
import com.abdul.SupportTicketV1.dto.LoginResponse;
import com.abdul.SupportTicketV1.dto.UserResponse;
import com.abdul.SupportTicketV1.entity.User;
import com.abdul.SupportTicketV1.enums.Role;
import com.abdul.SupportTicketV1.exception.InvalidException;
import com.abdul.SupportTicketV1.repository.UserRepository;

@Service
public class AuthService {
	@Autowired
	UserRepository userRepo;
	@Autowired
	AuthenticationManager authManager;
	@Autowired
	JwtService jwtService;
	public UserResponse signup(User user)
	{
		validateUser(user);
		user.setRole(Role.ROLE_USER);
		user.setActive(true);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
		user.setPassword(encoder.encode(user.getPassword()));
		UserResponse userResponse = new UserResponse();
		return userResponse.transferUser(userRepo.save(user));
		
	}
	
	public void validateUser(User user)
	{
		if(userRepo.existsByEmail(user.getEmail()))
		{
			throw new InvalidException("email already exists",HttpStatus.BAD_REQUEST.value());
		}
	}
	
	public LoginResponse login(LoginRequest loginRequest)
	{
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
		if(auth.isAuthenticated())
		{
			LoginResponse res = new LoginResponse();
			String token = jwtService.generateToken(loginRequest);
			ResponseCookie cookie = ResponseCookie.from("JWT",token)
									.httpOnly(true)
									.secure(false)
									.sameSite("Lax")
									.path("/")
									.maxAge(30*60)
									.build();
			res.setCookie(cookie);
			return res;
		}
		return null;
	}
	
}
