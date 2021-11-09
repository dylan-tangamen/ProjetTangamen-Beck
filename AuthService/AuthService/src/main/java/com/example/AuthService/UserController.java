package com.example.AuthService;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProfileService.Profile;


@RestController
public class UserController {

	private final Map<Long, User> users = new HashMap<>();
	private final Set<String> passwords = new HashSet<>();
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	

	
	@GetMapping("/AS/users/{userId}")
	@CrossOrigin
	public long users_get_id(@RequestBody @Valid User user) {
		users.get(user.getId());
		return user.getId();
	}
	
	@PutMapping("/AS/users")
	@CrossOrigin
	public User users_put(@RequestBody @Valid User user) {
		users.put(user.getId(), user);
		return user;
	}
	
	@DeleteMapping("/AS/users/{userId}")
	@CrossOrigin 
	public void user_delete(@PathVariable(value = "id") Long id) {
		logger.trace("DELETE /AS/users/{id}");
		User user = users.get(id);
		passwords.remove(user.getPassword());
		users.remove(id);
		
	}
	
	
	
		
	
}
