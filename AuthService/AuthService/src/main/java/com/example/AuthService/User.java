package com.example.AuthService;

public class User {
	
	private long id; 
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
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
