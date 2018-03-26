package com.hexaware.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class TestClassController {
	@Autowired
    RestTemplate restTemplete;
    
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String viewHome() {
		return "Hello World";
	}
	
}