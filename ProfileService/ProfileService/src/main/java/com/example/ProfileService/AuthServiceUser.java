package com.example.ProfileService;

public class AuthServiceUser {

	private long id;
	private String password;
	
	public AuthServiceUser(long id) {
		super();
		this.id = id;
		this.password = "test";
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
