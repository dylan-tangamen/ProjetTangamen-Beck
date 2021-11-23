package com.example.AuthService;

public class UserAlreadyExistException extends RuntimeException{
	UserAlreadyExistException(Long id) {
		super("Cet utilisateur existe dejà :"+id);
	}

}
