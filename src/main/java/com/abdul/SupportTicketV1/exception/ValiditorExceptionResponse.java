package com.abdul.SupportTicketV1.exception;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValiditorExceptionResponse {
	private Integer statusCode;
	private String message;
	private LocalDateTime timeStamp;
	private Map<String,String> error;
}
