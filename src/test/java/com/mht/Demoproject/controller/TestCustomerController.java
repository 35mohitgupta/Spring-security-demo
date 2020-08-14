package com.mht.Demoproject.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mht.Demoproject.dto.CustomerDTO;
import com.mht.Demoproject.service.CustomerService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class)
public class TestCustomerController {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CustomerService customerService;
	
	@Test
	public void testCustomerCreate() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		
		CustomerDTO customerDTO = new CustomerDTO("mohit", "mohit@123", "mohit@xyz.com");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer")
										.content(objectMapper.writeValueAsBytes(customerDTO))
										.accept(MediaType.TEXT_PLAIN);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assert.assertEquals("Customer created", response.getContentAsString());
		
		
	}
}
