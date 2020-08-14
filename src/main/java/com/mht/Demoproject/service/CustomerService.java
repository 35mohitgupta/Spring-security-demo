package com.mht.Demoproject.service;

import com.mht.Demoproject.dto.CustomerDTO;

public interface CustomerService {

	public void loginCustomer(String username, String password) throws Exception;
	
	public void createCustomer(CustomerDTO customerDTO) throws Exception;
	
	public void updateCustomerEmail(CustomerDTO newCustomerDTO) throws Exception;
	
	public CustomerDTO getCustomer(String username) throws Exception;
}
