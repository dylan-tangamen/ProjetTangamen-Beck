package com.example.ProfileService;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@RestControllerAdvice
public class ProfileExceptionController {
	
	@ExceptionHandler(ProfileNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String profileNotFOundHandler(ProfileNotFoundException ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(EmailInUseException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	String emailInUseHandler(EmailInUseException ex) {
		return ex.getMessage();
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName,  errorMessage);
		}
		return errors;
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleHttpMessgaeNotReadable(HttpMessageNotReadableException ex) {
		return ex.getMessage();
	}

}
