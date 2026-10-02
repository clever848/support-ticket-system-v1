package com.abdul.SupportTicketV1.entity;

import java.util.List;


import com.abdul.SupportTicketV1.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	private String name;
	@Email(message = "invalid email")
	@NotBlank
	private String email;
	@NotBlank
	@Size(min = 3)
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	private boolean isActive;
	@OneToMany(mappedBy = "createdBy")
	private List<Ticket> createdTickets;
	@OneToMany(mappedBy = "assignedTo")
	private List<Ticket> ticketAssigned;
}
