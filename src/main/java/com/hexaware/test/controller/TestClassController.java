package com.hexaware.test.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class TestClassController {
	private static final Logger LOG = Logger.getLogger(TestClassController.class.getName());
	@Autowired
    RestTemplate restTemplete;
 
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @RequestMapping(value = "/elkdemo")
    public String helloWorld() {
        String response = "Hello world" + new Date();
        LOG.debug("/elkdemo - &gt; " + response);
 
        return response;
    }
    
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String viewHome() {
		LOG.debug("Somethng here");
		String response = (String) restTemplete.exchange("http://localhost:8080/elkdemo", HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {
		}).getBody();
		LOG.info("Accessing demo index" + response);
		
		try {
			String exceptionrsp = (String) restTemplete.exchange("http://localhost:8080/exception", HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {
            }).getBody();
            LOG.error("/elk trying to print exception - &gt; " + exceptionrsp);
            response = response + " === " + exceptionrsp;
        } catch (Exception e) {
            // ignore
        }
		return response;
	}
	
	@RequestMapping(value = "/exception")
    public String exception() {
        String rsp = "";
        try {
            int i = 1 / 0;
            // should get exception
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e);
             
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            LOG.error("Exception As String :: - &gt; "+sStackTrace);
             
            rsp = sStackTrace;
        }
 
        return rsp;
    }
}