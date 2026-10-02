package com.abdul.SupportTicketV1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abdul.SupportTicketV1.entity.User;
import com.abdul.SupportTicketV1.repository.UserRepository;
import com.abdul.SupportTicketV1.security.MyUserDetail;

@Service
public class MyUserDetailService implements UserDetailsService{
	@Autowired
	UserRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username).orElseThrow(()->
						new UsernameNotFoundException("invalid credentials"));
		return new MyUserDetail(user);
	}
	
}
