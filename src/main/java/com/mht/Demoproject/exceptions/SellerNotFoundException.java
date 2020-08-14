package com.mht.Demoproject.exceptions;

public class SellerNotFoundException  extends Exception{

	private static final long serialVersionUID = 1L;

	public SellerNotFoundException() {}
	
	public SellerNotFoundException(String msg) {
		super(msg);
	}
}
