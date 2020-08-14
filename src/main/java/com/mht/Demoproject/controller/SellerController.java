package com.mht.Demoproject.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mht.Demoproject.dto.SellerDTO;
import com.mht.Demoproject.service.SellerService;

@CrossOrigin
@RestController
@RequestMapping("/seller")
public class SellerController {


	@Autowired
	private SellerService sellerService;
	
	@PostMapping(value = "/{username}", produces = "application/text")
	public ResponseEntity<String> loginCustomer(@PathVariable(name = "username") String username, @RequestBody String password) throws Exception{
		sellerService.loginSeller(username, password);
		return ResponseEntity.ok("Login successfully");
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<String> createSeller(@Valid @RequestBody SellerDTO sellerDTO, Errors errors) throws Exception {
		if(errors.hasErrors()) {
			String response = errors.getAllErrors().stream()
									.map(x -> x.getDefaultMessage()).collect(Collectors.joining(","));
			return ResponseEntity.ok(response);
		}
		sellerService.createSeller(sellerDTO);
		return ResponseEntity.ok("Seller created successfully");
	}
	
	@PutMapping
	public ResponseEntity<String> updateSellerEmail(SellerDTO newSellerDTO) throws Exception{
		sellerService.updateSellerEmail(newSellerDTO);
		return ResponseEntity.ok("Seller's email id updated successfully");
	}
	
	@GetMapping(value = "/{username}" ,params = "version=1")
	public ResponseEntity<SellerDTO> getSellerDetails(@PathVariable(value = "username") String username) throws Exception{
		SellerDTO sellerDTO = sellerService.getSellerDetails(username);
		return ResponseEntity.ok(sellerDTO);
	}
	
	/**
	 * Just added to illustrate api versioning using request parameter
	 * @param mohit
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/{username}", params = "version=2")
	public ResponseEntity<SellerDTO> getSellerDetailsv2(@PathVariable(value = "username") String username) throws Exception{
		SellerDTO sellerDTO = sellerService.getSellerDetails(username);
		return ResponseEntity.ok(sellerDTO);
	}
}
