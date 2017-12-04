package edu.usm.cos375.resthash.exception;

import org.springframework.http.HttpStatus;

public class RestRequestException extends Exception {
	
	private String message;
	private HttpStatus status;
	
	public RestRequestException(){
		this.message = "An unknown exception occured processing the request";
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	
	public RestRequestException(String message) {
		this.message = "Exception processing request: " + message;
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	
	public RestRequestException(String message, HttpStatus status) {
		this.message = "Exception processing request: " + message;  
		this.status = status;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
	
	public HttpStatus getHttpStatus() {
		return this.status;
	}
	
}