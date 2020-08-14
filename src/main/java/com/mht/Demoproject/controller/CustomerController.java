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

import com.mht.Demoproject.dto.CustomerDTO;
import com.mht.Demoproject.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping("/customer")
@Api(value = "CustomerController, REST APIs that deals with CustomerDTO")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@PostMapping(value = "/{username}")
	public ResponseEntity<String> loginCustomer(@PathVariable(name = "username") String username, @RequestBody String password) throws Exception{
		customerService.loginCustomer(username, password);
		return ResponseEntity.ok("Login successfully");
	}
	
	@PostMapping
	public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerDTO customerDTO,
									Errors errors) throws Exception {
		if(errors.hasErrors()) {
			String response = errors.getAllErrors().stream().map(x -> x.getDefaultMessage())
									.collect(Collectors.joining(","));
			return ResponseEntity.ok(response);
		}
		customerService.createCustomer(customerDTO);
		return ResponseEntity.ok("Customer created");
	}
	
	@PutMapping
	@ApiOperation(value = "Update the Customer's Email Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Customer's email id updated successfully"),
			@ApiResponse(code = 404, message = "Customer not found")
	})
	public ResponseEntity<String> updateCustomerEmail(@RequestBody CustomerDTO newCustomerDTO) throws Exception{
		customerService.updateCustomerEmail(newCustomerDTO);
		return ResponseEntity.ok("Customer's email id updated successfully");
	}
	
	@GetMapping(value = "/{username}")
	public ResponseEntity<CustomerDTO> getCustomerDetails(@PathVariable(value = "username") String username) throws Exception{
		CustomerDTO customerDTO = customerService.getCustomer(username);
		return ResponseEntity.ok(customerDTO);
	}
}
