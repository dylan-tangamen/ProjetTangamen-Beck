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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserController {

	private final Map<Long, User> users = new HashMap<>();
	
	
	@GetMapping("/AS/user/{userId}")
	@CrossOrigin
	public User users_get(@RequestBody @Valid User user) {
		users.get(user.getId());
		return user;
	}
	
	@PutMapping("/AS/users")
	@CrossOrigin
	public User users_put(@RequestBody @Valid User user) {
		users.put(user.getId(), user);
		return user;
	}
	
	
		
	
}
