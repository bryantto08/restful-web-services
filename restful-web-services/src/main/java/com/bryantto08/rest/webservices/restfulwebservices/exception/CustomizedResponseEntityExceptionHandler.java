package com.bryantto08.rest.webservices.restfulwebservices.exception;

import java.time.LocalDate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bryantto08.rest.webservices.restfulwebservices.user.UserNotFoundException;

/* We are extending the Base Response that Spring Boot Provides for Exception Handling
 * and providing our own custom Response back. ResponseEntityExceptionHandler is the superclass
 */

@ControllerAdvice  // Special Component for ExceptionHandlers
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	// ResponseEntity is the object that we return that represents the Response
	@ExceptionHandler(Exception.class)  // Declares this method as ExceptionHandler
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(),
				ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);  //500 
	}
	@ExceptionHandler(UserNotFoundException.class)  // Declares this method as ExceptionHandler
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(),
				ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);  // 404
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(),
				"Total Errors:" + ex.getErrorCount() + "First Error" + ex.getFieldError().getDefaultMessage(), request.getDescription(false));
				// All these can be manipulated through the Exception object ex
		
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}
}
