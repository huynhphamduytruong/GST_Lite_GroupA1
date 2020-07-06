package com.gstlite.mobilestore.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gstlite.mobilestore.entites.User;
import com.gstlite.mobilestore.exceptions.ResourceNotFoundException;
import com.gstlite.mobilestore.repositories.UserRepository;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Get all users
	 * 
	 * @return List<User>
	 */
	@GetMapping("/list")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	/**
	 * Get user by id
	 * 
	 * @param userId UserId
	 * 
	 * @return User by Id
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/get/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") long userId) throws ResourceNotFoundException {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("UserId not found: " + userId));
		
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping("/add")
	public User create(@Validated @RequestBody User user) {
		return userRepository.save(user);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<User> update(@PathVariable(value = "id") Long userId,
			@Validated @RequestBody User userDetails) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on:" + userId));
		user.setPassword(userDetails.getPassword());
		user.setRole(userDetails.getRole());
		final User updateUser = userRepository.save(user);
		return ResponseEntity.ok(updateUser);
	}

	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> delete(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on:" + userId));
		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	
	/**
	 * Sign in
	 * @param username
	 * @param password
	 * @return User
	 */
	@PostMapping("/signin")
	public ResponseEntity<User> signIn(@Validated @RequestBody User u) {
		User user = userRepository.findByUsernameAndPassword(u.getUsername(), u.getPassword());

		return ResponseEntity.ok(user);
	}
}
