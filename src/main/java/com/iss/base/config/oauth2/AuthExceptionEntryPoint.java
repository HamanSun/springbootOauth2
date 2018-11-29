package com.iss.base.config.oauth2;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws ServletException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		log.error(authException.getMessage());
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("code", response.getStatus());
		map.put("message", authException.getMessage());
		map.put("data", null);

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getOutputStream(), map);
		} catch (Exception e) {
			throw new ServletException();
		}
	}

}
