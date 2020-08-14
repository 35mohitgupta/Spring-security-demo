package com.mht.Demoproject.service;

import com.mht.Demoproject.dto.SellerDTO;

public interface SellerService {

	public void loginSeller(String username, String password) throws Exception;
	
	public void createSeller(SellerDTO sellerDTO) throws Exception;
	
	public void updateSellerEmail(SellerDTO newSellerDTO) throws Exception;
	
	public SellerDTO getSellerDetails(String username) throws Exception;
}
