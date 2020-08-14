package com.mht.Demoproject.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.mht.Demoproject.dto.CustomerDTO;
import com.mht.Demoproject.exceptions.CustomerAlreadyExists;
import com.mht.Demoproject.exceptions.CustomerNotFoundException;
import com.mht.Demoproject.exceptions.LoginException;

@Service(value = "CustomerService")
public class CustomerServiceImpl implements CustomerService{

	private Map<String, CustomerDTO> customerMap = null;
	
	@PostConstruct
	public void initializer() {
		customerMap = new HashMap<String, CustomerDTO>();
		CustomerDTO customerDTO = new CustomerDTO("abc", "abc@123", "abc@xyz.com");
		CustomerDTO customerDTO2 = new CustomerDTO("kbc", "kbc@123", "kbc@xyz.com");
		CustomerDTO customerDTO3 = new CustomerDTO("rcb", "rcb@123", "rcb@xyz.com");
		customerMap.put("abc", customerDTO);
		customerMap.put("kbc", customerDTO2);
		customerMap.put("rcb", customerDTO3);
	}
	
	@Override
	public void loginCustomer(String username, String password) throws CustomerNotFoundException, LoginException {
		CustomerDTO customerDTO = customerMap.get(username);
		if(customerDTO == null)
			throw new CustomerNotFoundException("CUSTOMER_NOT_FOUND");
		if(!customerDTO.getPassword().equals(password))
			throw new LoginException("INVALID_CREDENTIALS");
	}

	@Override
	public void createCustomer(CustomerDTO customerDTO) throws CustomerAlreadyExists {
		if(customerMap.containsKey(customerDTO.getUsername()))
				throw new CustomerAlreadyExists("CUSTOMER_ALREADY_EXISTS");
		customerMap.put(customerDTO.getUsername(), customerDTO);
	}

	@Override
	public void updateCustomerEmail(CustomerDTO newCustomerDTO) throws CustomerNotFoundException, LoginException {
		if(newCustomerDTO != null && !customerMap.containsKey(newCustomerDTO.getUsername()))
			throw new CustomerNotFoundException("CUSTOMER_NOT_FOUND");
		if(newCustomerDTO != null && !customerMap.
				get(newCustomerDTO.getUsername()).getPassword().equals(newCustomerDTO.getPassword()))
			throw new LoginException("INVALID_CREDENTIALS");
		customerMap.put(newCustomerDTO.getUsername(), newCustomerDTO);
	}

	@Override
	public CustomerDTO getCustomer(String username) throws CustomerNotFoundException {
		CustomerDTO customerDTO = customerMap.get(username);
		if(customerDTO == null)
			throw new CustomerNotFoundException("CUSTOMER_NOT_FOUND");
		return customerDTO;
	}

}
