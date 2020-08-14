package com.mht.Demoproject.exceptions;

public class CustomerAlreadyExists  extends Exception{

	private static final long serialVersionUID = 1L;

	public CustomerAlreadyExists() {}
	
	public CustomerAlreadyExists(String msg) {
		super(msg);
	}
}
