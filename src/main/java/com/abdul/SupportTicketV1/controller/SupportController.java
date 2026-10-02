package com.abdul.SupportTicketV1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abdul.SupportTicketV1.dto.SupportTicketResponse;
import com.abdul.SupportTicketV1.entity.Ticket;
import com.abdul.SupportTicketV1.enums.Status;
import com.abdul.SupportTicketV1.service.SupportService;

@RestController
@RequestMapping("/support")
public class SupportController {
	@Autowired
	SupportService supportService;
	@PreAuthorize("hasAuthority('GET_TICKET')")
	@GetMapping("/ticket/all")
	public ResponseEntity<Page<SupportTicketResponse>> getAllTicket(@RequestParam Integer pgNo,@RequestParam Integer pgSize)
	{
		return ResponseEntity.status(HttpStatus.OK).body(supportService.getAllTicket(pgNo,pgSize));
	}
	@PreAuthorize("hasAuthority('GET_TICKET')")
	@GetMapping("/ticket/{id}")
	public ResponseEntity<SupportTicketResponse> getTicketById(@PathVariable Integer id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(supportService.getTicketById(id));
	}
	@PreAuthorize("hasAuthority('PATCH_TICKET')")
	@PatchMapping("/ticket/{ticketId}/assign/{userId}")
	public ResponseEntity<SupportTicketResponse> assignTicket(
			@PathVariable Integer ticketId,@PathVariable Integer userId)
	{
		return ResponseEntity.status(HttpStatus.OK).body(supportService.assignTicket(ticketId, userId));
	}
	@PreAuthorize("hasAuthority('PATCH_TICKET')")
	@PatchMapping("/ticket/{id}/status/{status}")
	public ResponseEntity<SupportTicketResponse> updateTicketStatus(
			@PathVariable Integer id,@PathVariable Status status)
	{
		return ResponseEntity.status(HttpStatus.OK).body(supportService.updateTicketStatus(id, status));
	}
}
