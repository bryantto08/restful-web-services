package com.bryantto08.rest.webservices.restfulwebservices.exception;

import java.time.LocalDate;

public class ErrorDetails {  // Class for our Custom Error Message when we get 404
	// We want: Timestamp, Message, Details
	
	private LocalDate timestamp;
	private String message;
	private String details;
	public ErrorDetails(LocalDate timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	
	public LocalDate getTimestamp() {
		return timestamp;
	}
	public String getMessage() {
		return message;
	}
	public String getDetails() {
		return details;
	}
	
	

}
