package com.laozhang.struts2.base.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

public class ApplicationContextUtil {
	private  ApplicationContextUtil() {
		
	}
	private static ApplicationContext context;
	public static void setApplicationContext(ApplicationContext ctx) {
		context = ctx;
	}
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	public static Object getBean(String name) {
		return context.getBean(name);
	}
	public static Object getBean(String name, Class clazz) {
		return context.getBean(name, clazz);
	}
	public static boolean containsBean(String name) {
		return context.containsBean(name);
	}
	public static boolean isSingleton(String name) {
		return context.isSingleton(name);
	}
	public static Class getType(String name) {
		return context.getType(name);
	}
	public static String[] getAliases(String name) {
		return context.getAliases(name);
	}
	public static void publishEvent(ApplicationEvent applicationEvent)
	{
		context.publishEvent(applicationEvent);
	}
}
