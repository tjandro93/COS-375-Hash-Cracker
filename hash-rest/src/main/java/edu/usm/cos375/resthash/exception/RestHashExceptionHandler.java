package edu.usm.cos375.resthash.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import edu.usm.cos375.resthash.model.ApiError;

/*
 * This exception handling was done following along with
 * 		https://www.toptal.com/java/spring-boot-rest-api-error-handling
 */

@ControllerAdvice
public class RestHashExceptionHandler extends ResponseEntityExceptionHandler{

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}

	@ExceptionHandler(LmPlaintextException.class)
	protected ResponseEntity<Object> handleLmException(LmPlaintextException e){
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Provided plaintext could not be hashed", e));
	}
	
	@ExceptionHandler(RestRequestException.class)
	protected ResponseEntity<Object> handleRestRequestException(RestRequestException e){
		return buildResponseEntity(new ApiError(e.getHttpStatus(), "Request could not be completed", e));
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request){
		return buildResponseEntity (new ApiError(HttpStatus.METHOD_NOT_ALLOWED, 
				ex.getMethod() +  " is not allowed in the context", ex));                  
	}
	
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}


}
