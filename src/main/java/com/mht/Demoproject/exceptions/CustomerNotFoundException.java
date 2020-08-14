package com.mht.Demoproject.exceptions;

public class CustomerNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException() {}
	
	public CustomerNotFoundException(String msg) {
		super(msg);
	}
}
