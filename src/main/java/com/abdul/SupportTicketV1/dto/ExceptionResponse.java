package com.abdul.SupportTicketV1.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ExceptionResponse {
	private Integer statusCode;
	private String msg;
	private LocalDateTime timeStamp;
}
