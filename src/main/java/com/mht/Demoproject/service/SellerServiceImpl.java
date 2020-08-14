package com.mht.Demoproject.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.mht.Demoproject.dto.SellerDTO;
import com.mht.Demoproject.exceptions.LoginException;
import com.mht.Demoproject.exceptions.SellerAlreadyExists;
import com.mht.Demoproject.exceptions.SellerNotFoundException;


@Service("SellerService")
public class SellerServiceImpl implements SellerService{


	private Map<String, SellerDTO> sellerMap = null;
	
	@PostConstruct
	public void initializer() {
		sellerMap = new HashMap<String, SellerDTO>();
		SellerDTO customerDTO = new SellerDTO("abc", "abc@123", "abc@xyz.com");
		SellerDTO customerDTO2 = new SellerDTO("kbc", "kbc@123", "kbc@xyz.com");
		SellerDTO customerDTO3 = new SellerDTO("rcb", "rcb@123", "rcb@xyz.com");
		sellerMap.put("abc", customerDTO);
		sellerMap.put("kbc", customerDTO2);
		sellerMap.put("rcb", customerDTO3);
	}
	
	@Override
	public void loginSeller(String username, String password) throws SellerNotFoundException, LoginException {
		SellerDTO customerDTO = sellerMap.get(username);
		if(customerDTO == null)
			throw new SellerNotFoundException("SELLER_NOT_FOUND");
		if(!customerDTO.getPassword().equals(password))
			throw new LoginException("INVALID_CREDENTIALS");
	}

	@Override
	public void createSeller(SellerDTO sellerDTO) throws SellerAlreadyExists {
		if(sellerMap.containsKey(sellerDTO.getUsername()))
			throw new SellerAlreadyExists("Seller_ALREADY_EXISTS");
		sellerMap.put(sellerDTO.getUsername(), sellerDTO);
	}

	@Override
	public void updateSellerEmail(SellerDTO newSellerDTO) throws SellerNotFoundException, LoginException {
		if(newSellerDTO != null && !sellerMap.containsKey(newSellerDTO.getUsername()))
			throw new SellerNotFoundException("CUSTOMER_NOT_FOUND");
		if(newSellerDTO != null && !sellerMap.
				get(newSellerDTO.getUsername()).getPassword().equals(newSellerDTO.getPassword()))
			throw new LoginException("INVALID_CREDENTIALS");
		sellerMap.put(newSellerDTO.getUsername(), newSellerDTO);
	}

	@Override
	public SellerDTO getSellerDetails(String username) throws SellerNotFoundException  {
		SellerDTO sellerDTO = sellerMap.get(username);
		if(sellerDTO == null)
			throw new SellerNotFoundException("SELLER_NOT_FOUND");
		return sellerDTO;
	}

}
