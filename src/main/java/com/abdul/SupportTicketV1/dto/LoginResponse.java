package com.abdul.SupportTicketV1.dto;

import org.springframework.http.ResponseCookie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
	private ResponseCookie cookie;
}
