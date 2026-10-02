package com.abdul.SupportTicketV1.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.abdul.SupportTicketV1.entity.User;

public class MyUserDetail implements UserDetails{
	private User user;
	private Integer id;
	public MyUserDetail(User user) {
		this.user = user;
		this.id = user.getId();
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
		user.getRole().getPermissions().stream()
				.forEach(permission->authorities.add(new SimpleGrantedAuthority(permission.name())));
		authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
		return authorities;
	}

	@Override
	public @Nullable String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}
	@Override
	public boolean isEnabled()
	{
		return user.isActive();
	}
	public Integer getId()
	{
		return id;
	}
}
