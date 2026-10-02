package com.abdul.SupportTicketV1.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidException extends RuntimeException{
	Integer statusCode;
	public InvalidException(String msg,Integer statusCode) {
		super(msg);
		this.statusCode=statusCode;
	}
}
