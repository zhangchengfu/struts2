package com.laozhang.struts2.base.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class ContextLoaderListener extends
		org.springframework.web.context.ContextLoaderListener {
	
	public static ApplicationContext context;
	
	public void contextInitialized(ServletContextEvent event)
	{
		super.contextInitialized(event);
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils
			.getRequiredWebApplicationContext(context);
		this.context = ctx;
	}
}
