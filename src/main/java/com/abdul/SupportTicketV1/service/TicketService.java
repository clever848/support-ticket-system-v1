package com.abdul.SupportTicketV1.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abdul.SupportTicketV1.dto.UserTicketResponse;
import com.abdul.SupportTicketV1.entity.Ticket;
import com.abdul.SupportTicketV1.entity.User;
import com.abdul.SupportTicketV1.enums.Status;
import com.abdul.SupportTicketV1.exception.InvalidException;
import com.abdul.SupportTicketV1.repository.TicketRepository;
import com.abdul.SupportTicketV1.repository.UserRepository;
import com.abdul.SupportTicketV1.security.MyUserDetail;


@Service
public class TicketService {
	@Autowired
	UserRepository userRepo;
	@Autowired
	TicketRepository ticketRepo;
	public UserTicketResponse createTicket(Ticket ticket)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails)auth.getPrincipal();
		User user= userRepo.findByEmail(userDetails.getUsername()).orElseThrow(()->
				new UsernameNotFoundException("invalid credentials"));
		ticket.setCreatedBy(user);
		ticket.setStatus(Status.OPEN);
		ticket.setCreatedAt(LocalDateTime.now());
		ticketRepo.save(ticket);
		UserTicketResponse res = new UserTicketResponse();
		res.transferTicket(ticket);
		return res;
	}
	public UserTicketResponse getTicketById(Integer ticketId,MyUserDetail userDetail)
	{
		Ticket ticket = ticketRepo.findByIdAndCreatedById(ticketId,userDetail.getId())
							.orElseThrow(()->new InvalidException("Not found",HttpStatus.NOT_FOUND.value()));
		UserTicketResponse res = new UserTicketResponse();
		 res.transferTicket(ticket);
		 return res;
	}
	public UserTicketResponse patchTicket(Integer id,Map<String,Object> m,MyUserDetail userDetail)
	{
		Ticket ticket = ticketRepo.findByIdAndCreatedById(id,userDetail.getId())
					.orElseThrow(()->new InvalidException("no ticket found",HttpStatus.NOT_FOUND.value()));
		for(Map.Entry<String,Object> e:m.entrySet())
		{
			String key = e.getKey();
			if(key.equals("title"))
			{
				ticket.setTitle((String)e.getValue());
			}
			else if(key.equals("description"))
			{
				ticket.setDescription((String)e.getValue());
			}
		}
		ticket = ticketRepo.save(ticket);
		UserTicketResponse res = new UserTicketResponse();
		return res.transferTicket(ticket);
	}
	public Map<String,String> cancelTicket(Integer ticketId,MyUserDetail userDetail)
	{
		Ticket ticket = ticketRepo.findByIdAndCreatedById(ticketId,userDetail.getId())
					     .orElseThrow(()->new InvalidException("no ticket found",HttpStatus.NOT_FOUND.value()));
		if(ticket.getStatus().name() != "OPEN")
		{
			throw new InvalidException("ticket is in "+ticket.getStatus().name().toLowerCase(),HttpStatus.BAD_REQUEST.value());
		}
		ticket.setStatus(Status.CLOSED);
		ticketRepo.save(ticket);
		Map<String,String> m = new HashMap<String, String>();
		m.put("msg","cancelled successfully");
		return m;
	}
}
