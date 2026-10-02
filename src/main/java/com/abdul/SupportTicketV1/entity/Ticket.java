package com.abdul.SupportTicketV1.entity;

import java.time.LocalDateTime;

import com.abdul.SupportTicketV1.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Integer id;
	@NotBlank
	private String title;
	private String description;
	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private Status status;
	@JsonIgnore
	@ManyToOne
	private User createdBy;
	@JsonIgnore
	@ManyToOne
	private User assignedTo;
	@JsonIgnore
	private LocalDateTime createdAt;
	
}
