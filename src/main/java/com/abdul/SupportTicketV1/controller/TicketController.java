package com.abdul.SupportTicketV1.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdul.SupportTicketV1.dto.UserTicketResponse;
import com.abdul.SupportTicketV1.entity.Ticket;
import com.abdul.SupportTicketV1.security.MyUserDetail;
import com.abdul.SupportTicketV1.service.TicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ticket")
public class TicketController {
	@Autowired
	TicketService ticketService;
	@PreAuthorize("hasAuthority('POST_TICKET')")
	@PostMapping
	public ResponseEntity<UserTicketResponse> createTicket(@Valid @RequestBody Ticket ticket)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(ticket));
	}
	
	@PreAuthorize("hasAuthority('GET_TICKET')")
	@GetMapping("/{id}")
	public ResponseEntity<UserTicketResponse> getTicketById(@PathVariable Integer id,@AuthenticationPrincipal MyUserDetail userDetail)
	{
		return ResponseEntity.status(HttpStatus.OK).body(ticketService.getTicketById(id, userDetail));
	}
	@PreAuthorize("hasAuthority('PATCH_TICKET')")
	@PatchMapping("/{id}")
	public ResponseEntity<UserTicketResponse> patchTicket(@PathVariable Integer id,
			@RequestBody Map<String,Object> m,@AuthenticationPrincipal MyUserDetail userDetail)
	{
		return ResponseEntity.status(HttpStatus.OK).body(ticketService.patchTicket(id, m, userDetail));
	}
	@PreAuthorize("hasAuthority('DELETE_TICKET')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String,String>> cancelTicket(@PathVariable Integer id,
			@AuthenticationPrincipal MyUserDetail userDetail)
	{
		return ResponseEntity.status(HttpStatus.OK).body(ticketService.cancelTicket(id, userDetail));
	}
}
