/**
 * 
 */
package com.bryantto08.rest.webservices.restfulwebservices.helloworld;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author bryan
 *
 */
public class HelloWorldBean {
	
	private String message;

	public HelloWorldBean(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "HelloWorldBean [message=" + message + "]";
	}

	
}
