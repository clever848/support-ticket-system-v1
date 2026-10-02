package com.abdul.SupportTicketV1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abdul.SupportTicketV1.dto.UserResponse;
import com.abdul.SupportTicketV1.entity.Ticket;
import com.abdul.SupportTicketV1.entity.User;
import com.abdul.SupportTicketV1.enums.Role;
import com.abdul.SupportTicketV1.enums.Status;
import com.abdul.SupportTicketV1.exception.InvalidException;
import com.abdul.SupportTicketV1.repository.TicketRepository;
import com.abdul.SupportTicketV1.repository.UserRepository;

@Service
public class AdminService {
	@Autowired
	UserRepository userRepo;
	@Autowired
	TicketRepository ticketRepo;
	public Page<UserResponse> getAllUser(Integer pgNo,Integer pgSize)
	{
		Page<User> page = userRepo.findAll(PageRequest.of(pgNo,pgSize,Sort.by("id")));
		Page<UserResponse> res = page.map(e->new UserResponse().transferUser(e));
		return res;
	}
	
	public UserResponse changeRoleToSupport(Integer id)
	{
		User user = userRepo.findById(id).orElseThrow(()->
					new InvalidException("user not found",HttpStatus.NOT_FOUND.value()));
		user.setRole(Role.ROLE_SUPPORT);
		userRepo.save(user);
		return new UserResponse().transferUser(user);
	}
	
	public UserResponse changeRoleToAdmin(Integer id)
	{
		User user = userRepo.findById(id).orElseThrow(()->
		new InvalidException("user not found",HttpStatus.NOT_FOUND.value()));
		user.setRole(Role.ROLE_ADMIN);
		userRepo.save(user);
		return new UserResponse().transferUser(user);
	}
	@Transactional
	public Map<String,String> deleteUser(Integer id)
	{
		User user = userRepo.findById(id).orElseThrow(()->
					new InvalidException("no user found",HttpStatus.NOT_FOUND.value()));
		if(!user.isActive())
			throw new InvalidException("already inactive user",HttpStatus.BAD_REQUEST.value());
		else if(user.getRole().equals(Role.ROLE_ADMIN) || user.getRole().equals(Role.ROLE_SUPER_ADMIN)) {
			throw new InvalidException("can't deactivate admin",HttpStatus.BAD_REQUEST.value());
		}
		else if (user.getRole().equals(Role.ROLE_USER)) {
			user.setActive(false);
		}
		else if(user.getRole().equals(Role.ROLE_SUPPORT)){
			List<Ticket> tickets = ticketRepo.findByStatusInAndAssignedToId(List.of(Status.OPEN,Status.IN_PROGRESS),user.getId());
			for(Ticket t:tickets)
			{
				t.setAssignedTo(null);
				t.setStatus(Status.OPEN);
			}
			ticketRepo.saveAll(tickets);
			user.setActive(false);
		}
		userRepo.save(user);
		Map<String,String> m = new HashMap<String, String>();
		m.put("msg","deleted successfully");
		return m;
	}
	
}
