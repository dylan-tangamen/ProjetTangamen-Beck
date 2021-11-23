package com.example.AuthService;

import javax.validation.constraints.NotNull;

public class User {
	
	@NotNull(message = "Id required")
	private long id; 
	
	@NotNull(message = "Password required")
	private String password;
	
	public User(long id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public boolean checkPassword(String password) {
		return (this.password.equals(password));
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
