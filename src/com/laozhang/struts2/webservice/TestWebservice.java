package com.laozhang.struts2.webservice;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public class TestWebservice {
	public String hello(String name) {
		return "hello " + name; 
	}
}
