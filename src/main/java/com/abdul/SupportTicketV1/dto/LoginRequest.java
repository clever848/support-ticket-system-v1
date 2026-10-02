package com.abdul.SupportTicketV1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
	@Email(message = "invalid email")
	@NotBlank(message = "email must required")
	private String email;
	@NotBlank(message = "password must required")
	@Size(min = 3,message = "password must be greater then 3 character")
	private String password;
}
