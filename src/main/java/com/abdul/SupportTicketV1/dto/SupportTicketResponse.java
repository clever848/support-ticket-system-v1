package com.abdul.SupportTicketV1.dto;

import java.time.LocalDateTime;

import com.abdul.SupportTicketV1.entity.Ticket;
import com.abdul.SupportTicketV1.entity.User;
import com.abdul.SupportTicketV1.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupportTicketResponse {
	private Integer id;
	private String title;
	private String description;
	private Status status;
	private UserResponse createdBy;
	private UserResponse assignedTo;
	private LocalDateTime createdAt;
	
	public SupportTicketResponse transfer(Ticket ticket)
	{
		this.id = ticket.getId();
		this.title = ticket.getTitle();
		this.description = ticket.getDescription();
		this.status = ticket.getStatus();
		this.createdAt = ticket.getCreatedAt();
		this.createdBy = new UserResponse().transferUser(ticket.getCreatedBy());
		this.assignedTo = new UserResponse().transferUser(ticket.getAssignedTo());
		return this;
	}
}
