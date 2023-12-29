package com.ircs.portal.config;

import java.io.IOException;
import java.util.Calendar;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ircs.portal.util.Response;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
    	
    	System.out.println("authException-----------------"+authException.getMessage());
    	res.setContentType("application/json;charset=UTF-8");
		res.setStatus(403);
		res.setStatus(HttpStatus.UNAUTHORIZED.value());
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),String.valueOf(HttpStatus.UNAUTHORIZED.value()),true,authException.getMessage(),null,request.getRequestURI());
		res.getOutputStream().println(objectMapper.writeValueAsString(response));

    }
    
}