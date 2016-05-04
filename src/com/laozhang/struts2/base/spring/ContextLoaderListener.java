package com.laozhang.struts2.base.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.xml.ws.Endpoint;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.laozhang.struts2.webservice.TestWebservice;


public class ContextLoaderListener extends
		org.springframework.web.context.ContextLoaderListener {
	
	
	public void contextInitialized(ServletContextEvent event)
	{
		super.contextInitialized(event);
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils
			.getRequiredWebApplicationContext(context);
		ApplicationContextUtil.setApplicationContext(ctx);
		publishWebservice();
	}
	
	/**
	 * ʹ��JDK����WebService  ��������
	 * ����Webservice
	 */
	private void publishWebservice() {
		Endpoint.publish("http://127.0.0.1:8080/struts2-helloworld/webservice/test", new TestWebservice());
	}
	
	/**
	 * ʹ��JDK����WebService
	 * �ͻ��˵���Webservice
	 * 
	 * 1������jdk��wsimort.exe�������ɿͻ��˴��룬wsimort.exe����λ��Jdk��binĿ¼��
	 * ִ�����wsimport -keep url(urlΪwsdl�ļ���·��)���ɿͻ��˴���
	 * �������д��ڣ��л���srcĿ¼��ִ��"wsimport -keep http://192.168.1.100:8888/WebService?wsdl"���ɿͻ��˴���
	 * 
	 * 2�� �������ɵĴ����д����WebService�����ṩ�ķ���
	 * wsimport���߰����������˺ü���java�࣬������ֻ��Ҫ����WebServiceImplService���WebServiceImpl�ӿڵ�ʹ�ü���
	 * //����һ�����ڲ���WebServiceImplʵ���Ĺ�����WebServiceImplService����wsimport�������ɵ�
	 * WebServiceImplService factory = new WebServiceImplService();
	 * //ͨ����������һ��WebServiceImplʵ����WebServiceImpl��wsimport�������ɵ�
	 * WebServiceImpl wsImpl = factory.getWebServiceImplPort();
	 * //����WebService��sayHello����
	 * String resResult = wsImpl.sayHello("�°�����");
	 * 
	 */
}
