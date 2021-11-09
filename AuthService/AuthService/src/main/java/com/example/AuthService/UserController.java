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




public class UserController {

	private final AtomicLong counter = new AtomicLong();
	private final Map<Long, User> users = new HashMap<>();
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@PutMapping("/AS/users")
	@CrossOrigin
	public User users_put(@RequestBody @Valid User user) {
		logger.trace("PUT /AS/users");
		String password = user.getPassword();
		long new_id = counter.incrementAndGet();
		user.setId(new_id);
		users.put(new_id, user);
		logger.info(String.format("User created : [%d] %s.", new_id, user.getPassword()));
		return user;
	}
		
	
}
