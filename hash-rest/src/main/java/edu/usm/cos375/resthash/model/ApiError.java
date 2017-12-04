package edu.usm.cos375.resthash.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/*
 * The error handling was done following this tutorial
 * 		https://www.toptal.com/java/spring-boot-rest-api-error-handling
 */

public class ApiError {
	
	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;
	
	private ApiError(HttpStatus status){
		this.timestamp = LocalDateTime.now();
		this.status = status;
	}
	
	public ApiError(HttpStatus status, String message, Throwable ex){
		this(status);
		this.message = message;
		this.debugMessage = ex.getMessage();
	}
	
	public ApiError(HttpStatus status, Throwable ex){
		this(status);
		this.message = "Unexpected error";
		this.debugMessage = ex.getMessage();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}


}
