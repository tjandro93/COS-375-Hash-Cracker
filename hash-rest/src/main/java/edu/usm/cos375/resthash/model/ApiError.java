package edu.usm.cos375.resthash.model;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/*
 * The error handling was done following these tutorials
 * 		https://www.toptal.com/java/spring-boot-rest-api-error-handling
 * 		http://www.baeldung.com/exception-handling-for-rest-with-spring
 */

public class ApiError {
	
	private HttpStatus status;
	private String timestamp;
	private String message;
	private String debugMessage;
	
	private ApiError(HttpStatus status){
		this.timestamp = formatDate();
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
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

	private String formatDate() {
		SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy - HH:mm:ss z");
		return df.format(new Date());
	}

}
