package com.mht.Demoproject.controller;

import java.util.Base64;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mht.Demoproject.dto.SellerDTO;
import com.mht.Demoproject.exceptions.LoginException;
import com.mht.Demoproject.service.SellerService;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SellerController.class)
public class TestSellerController {

	@Autowired
	private MockMvc mockMvc;
	
	private SellerDTO sellerDTO = null;
	
	@MockBean
	private SellerService sellerService;
	
	@Before
	public void initializeCustomer() {
		System.out.println("Executed before running test cases");
		sellerDTO = new SellerDTO("mohit", "mohit", "mohit@xyz");
	}
	
	
	private HttpHeaders createHeaders(String username, String password){
		String authStr = username + ":" + password;
        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        return headers;
	}
	
	@Test
	public void testLoginFail() throws Exception {
		Mockito.doThrow(LoginException.class).when(sellerService).loginSeller(Mockito.anyString(), Mockito.anyString());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/seller/mohit")
										.headers(createHeaders("mohit", "mht"))
										.content("mohit@123")
										.contentType(MediaType.TEXT_PLAIN);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = mvcResult.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(),response.getStatus());
		
	}
	
	
}
