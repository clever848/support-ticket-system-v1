package com.abdul.SupportTicketV1.service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.abdul.SupportTicketV1.dto.SupportTicketResponse;
import com.abdul.SupportTicketV1.entity.Ticket;
import com.abdul.SupportTicketV1.entity.User;
import com.abdul.SupportTicketV1.enums.Role;
import com.abdul.SupportTicketV1.enums.Status;
import com.abdul.SupportTicketV1.exception.InvalidException;
import com.abdul.SupportTicketV1.repository.TicketRepository;
import com.abdul.SupportTicketV1.repository.UserRepository;

@Service
public class SupportService {
	@Autowired
	TicketRepository ticketRepo;
	@Autowired
	UserRepository userRepo;
	public Page<SupportTicketResponse> getAllTicket(Integer pgNo,Integer pgSize)
	{
		Page<Ticket> tickets = ticketRepo.findAll(PageRequest.of(pgNo,pgSize,Sort.by("createdAt").descending()));
		Page<SupportTicketResponse> res = tickets.map(e->new SupportTicketResponse().transfer(e));
		return res;
	}
	public SupportTicketResponse getTicketById(Integer id)
	{
		Ticket ticket = ticketRepo.findById(id).orElseThrow(()->
						new InvalidException("no ticket found",HttpStatus.NOT_FOUND.value()));
		SupportTicketResponse res= new SupportTicketResponse();
		return res.transfer(ticket);
	}
	public SupportTicketResponse assignTicket(Integer ticketId,Integer userId)
	{
		Ticket ticket = ticketRepo.findById(ticketId).orElseThrow(()->
						new InvalidException("ticket not found",HttpStatus.NOT_FOUND.value()));
		User user = userRepo.findByIdAndRole(userId,Role.ROLE_SUPPORT).orElseThrow(()->
					new InvalidException("invalid user",HttpStatus.NOT_FOUND.value()));
		ticket.setAssignedTo(user);
		ticketRepo.save(ticket);
		SupportTicketResponse res = new SupportTicketResponse();
		return res.transfer(ticket);
	}
	
	public SupportTicketResponse updateTicketStatus(Integer id,Status status)
	{
		Ticket ticket = ticketRepo.findById(id).orElseThrow(()->
						new InvalidException("no ticket found",HttpStatus.NOT_FOUND.value()));
		ticket.setStatus(status);
		ticketRepo.save(ticket);
		SupportTicketResponse res = new SupportTicketResponse();
		return res.transfer(ticket);
	}
}