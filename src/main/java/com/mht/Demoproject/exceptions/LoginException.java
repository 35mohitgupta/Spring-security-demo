package com.mht.Demoproject.exceptions;

public class LoginException  extends Exception{

	private static final long serialVersionUID = 1L;

	public LoginException() {}
	
	public LoginException(String msg) {
		super(msg);
	}
}
