package com.abdul.SupportTicketV1.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.abdul.SupportTicketV1.dto.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(InvalidException.class)
	public ResponseEntity<ExceptionResponse> handleIVE(InvalidException e)
	{
		ExceptionResponse res = new ExceptionResponse();
		res.setStatusCode(e.getStatusCode());
		res.setMsg(e.getMessage());
		res.setTimeStamp(LocalDateTime.now());
		return ResponseEntity.status(e.getStatusCode()).body(res);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValiditorExceptionResponse> handleMANVE(MethodArgumentNotValidException e)
	{
		Map<String,String> errors = new HashMap<String, String>();
		e.getBindingResult()
			.getFieldErrors()
			.forEach(error->{
				errors.put(error.getField(),error.getDefaultMessage());
			});
		ValiditorExceptionResponse res = new ValiditorExceptionResponse();
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setMessage("validation error");
		res.setTimeStamp(LocalDateTime.now());
		res.setError(errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ExceptionResponse> handleMATME(MethodArgumentTypeMismatchException e)
	{
		ExceptionResponse res = new ExceptionResponse();
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setMsg(e.getName()+" should be "+e.getRequiredType().getSimpleName());
		res.setTimeStamp(LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(res);
	}
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ExceptionResponse> handleMSRPE(MissingServletRequestParameterException e)
	{
		ExceptionResponse res = new ExceptionResponse();
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setMsg(e.getParameterName()+" is required");
		res.setTimeStamp(LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}
}
