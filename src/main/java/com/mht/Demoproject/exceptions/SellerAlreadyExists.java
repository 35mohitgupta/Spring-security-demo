package com.mht.Demoproject.exceptions;

public class SellerAlreadyExists  extends Exception{

	private static final long serialVersionUID = 1L;

	public SellerAlreadyExists() {}
	
	public SellerAlreadyExists(String msg) {
		super(msg);
	}
}
