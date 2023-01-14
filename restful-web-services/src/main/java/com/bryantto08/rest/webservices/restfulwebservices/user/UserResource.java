package com.bryantto08.rest.webservices.restfulwebservices.user;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController 
public class UserResource {
	
	private UserDaoService service;
	
	public UserResource(UserDaoService service) {
		this.service = service;
	}
	
	// GET METHOD /users
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}
	
	// GET METHOD /users/{id} retrieves a specific user
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		if (user==null) 
			throw new UserNotFoundException("id:" + id);
		
		// Creating an Entity Model that adds a link key to the JSON format of a user
		EntityModel<User> entityModel = EntityModel.of(user); 
		// This Link Builder connects to the link of retrieveAllUsers
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	// POST /users creates a user
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		
		// Adds a location header to the response
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").
				buildAndExpand(savedUser.getId())
				.toUri();
		// location - users/{id}
		return ResponseEntity.created(location ).build();  // Creating new response body with 201 CREATED
	}
	
	// DELETE METHOD /users/{id} retrieves a specific user
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		service.deleteById(id);
	}
	
	
}
