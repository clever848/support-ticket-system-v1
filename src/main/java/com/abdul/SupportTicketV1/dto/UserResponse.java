package com.abdul.SupportTicketV1.dto;

import com.abdul.SupportTicketV1.entity.User;
import com.abdul.SupportTicketV1.enums.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserResponse {
	private Integer id;
	private String name;
	private String email;
	private Role role;
	public UserResponse transferUser(User user)
	{
		if(user == null)
			return null;
		this.id = user.getId();
		this.email = user.getEmail();
		this.name = user.getName();
		this.role = user.getRole();
		return this;
	}
}
