package com.laozhang.struts2.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class MyUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	
	
	@Override
	protected String obtainUsername(HttpServletRequest request) {
		String username = request.getParameter("username");
		return null == username ? "" : username;
	}
	
	@Override
	protected String obtainPassword(HttpServletRequest request) {
		String password = request.getParameter("password");
		return null == password ? "" : password;
	}
}
