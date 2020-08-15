package com.mht.Demoproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mht.Demoproject.exceptions.CustomerAlreadyExists;
import com.mht.Demoproject.exceptions.CustomerNotFoundException;
import com.mht.Demoproject.exceptions.ErrorMessage;
import com.mht.Demoproject.exceptions.LoginException;
import com.mht.Demoproject.exceptions.SellerAlreadyExists;
import com.mht.Demoproject.exceptions.SellerNotFoundException;

@RestControllerAdvice(basePackages = "com.mht.Demoproject.controller")
public class ExceptionControllerAdvice {

	@ExceptionHandler(CustomerAlreadyExists.class)
	public ResponseEntity<ErrorMessage>  customerAlreadyExistsExceptionHandler(CustomerAlreadyExists exception){
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.ok(errorMessage);
	}
	
	@ExceptionHandler(value =  {CustomerNotFoundException.class})
	public ResponseEntity<ErrorMessage>  customerNotFoundExceptionHandler(CustomerNotFoundException exception){
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND.value());
		return ResponseEntity.ok(errorMessage);
	}
	
	@ExceptionHandler(LoginException.class)
	public ResponseEntity<ErrorMessage>  loginExceptionHandler(LoginException exception){
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), HttpStatus.FORBIDDEN.value());
		return ResponseEntity.ok(errorMessage);
	}
	
	@ExceptionHandler(SellerAlreadyExists.class)
	public ResponseEntity<ErrorMessage>  sellerAlreadyExistsExceptionHandler(SellerAlreadyExists exception){
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.ok(errorMessage);
	}
	
	@ExceptionHandler(SellerNotFoundException.class)
	public ResponseEntity<ErrorMessage>  sellerNotFoundExceptionHandler(SellerNotFoundException exception){
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND.value());
		return ResponseEntity.ok(errorMessage);
	}
	
}
