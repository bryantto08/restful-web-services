package com.bryantto08.rest.webservices.restfulwebservices.user;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.bryantto08.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.bryantto08.rest.webservices.restfulwebservices.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController 
public class UserJpaResource {
	
	private UserRepository userRepository;
	private PostRepository postRepository;
	
	public UserJpaResource(UserRepository repository, PostRepository postRepository) {
		this.userRepository = repository;
		this.postRepository = postRepository;
	}
	
	// GET METHOD /users
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}
	
	// GET METHOD /users/{id} retrieves a specific user
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) 
			throw new UserNotFoundException("id:" + id);
		
		// Creating an Entity Model that adds a link key to the JSON format of a user
		EntityModel<User> entityModel = EntityModel.of(user.get()); 
		// This Link Builder connects to the link of retrieveAllUsers
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	// POST /users creates a user
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		
		// Adds a location header to the response
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").
				buildAndExpand(savedUser.getId())
				.toUri();
		// location - users/{id}
		return ResponseEntity.created(location ).build();  // Creating new response body with 201 CREATED
	}
	
	// DELETE METHOD /users/{id} retrieves a specific user
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	
	// Get METHOD /users/{id}/posts retrieves all posts of a user
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsForUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty())
			throw new UserNotFoundException("id:" + id);
		return user.get().getPosts();
	}
	
	// POST /jpa/users/{id}/posts creates a user
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
		Optional<User> user = userRepository.findById(id);
		
		if (user.isEmpty())
			throw new UserNotFoundException("id:" +id);
		
		post.setUser(user.get());
		Post savedPost = postRepository.save(post);
		// location - users/{id}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").
				buildAndExpand(savedPost.getId())
				.toUri();
	
		return ResponseEntity.created(location).build(); // Creating new response body with 201 CREATED
	}
	
	// GET METHOD /jpa/users/{id}/posts/{id} retrieves a specific user
	@GetMapping("/jpa/users/{id}/posts/{postId}")
	public EntityModel<User> retrieveSinglePostFromUser(@PathVariable int id,
			@PathVariable int postId) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) 
			throw new UserNotFoundException("id:" + id);
		
		// Creating an Entity Model that adds a link key to the JSON format of a user
		EntityModel<User> entityModel = EntityModel.of(user.get()); 
		// This Link Builder connects to the link of retrieveAllUsers
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	
	
	
}
