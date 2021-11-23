package com.example.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingController {
	
	@ResponseBody
	@ExceptionHandler(TokenNotValidException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	String tokenNotValidHandler(TokenNotValidException ex) {
		return ex.getMessage();
	}
	
	 @ResponseBody
	 @ExceptionHandler (UserAlreadyExistException.class)
	 @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	 String UserAlreadyExistHandler(UserAlreadyExistException ex) {
		 return ex.getMessage();
	 }
	 
	 @ResponseBody
	 @ExceptionHandler (IncorrectPasswordException.class)
	 @ResponseStatus(HttpStatus.UNAUTHORIZED)
	 String IncorrectPasswordHandler(IncorrectPasswordException ex) {
		return ex.getMessage(); 
	 }
	 
	 @ResponseBody
	 @ExceptionHandler (UserNotFoundException.class)
	 @ResponseStatus(HttpStatus.NOT_FOUND)
	 String UserNotFoundHandler(UserNotFoundException ex) {
		 return ex.getMessage();
	 }
	 

	
	
}
