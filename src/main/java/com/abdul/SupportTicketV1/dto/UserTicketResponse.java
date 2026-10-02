package com.abdul.SupportTicketV1.dto;

import java.time.LocalDateTime;

import com.abdul.SupportTicketV1.entity.Ticket;
import com.abdul.SupportTicketV1.enums.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserTicketResponse {
	private Integer id;
	private String title;
	private String description;
	private Status status;
	private LocalDateTime createdAt;
	
	public UserTicketResponse transferTicket(Ticket ticket)
	{
		this.id = ticket.getId();
		this.title = ticket.getTitle();
		this.createdAt = ticket.getCreatedAt();
		this.description = ticket.getDescription();
		this.status = ticket.getStatus();
		return this;
	}
}
