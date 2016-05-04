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
	 * 使用JDK开发WebService  服务器端
	 * 发布Webservice
	 */
	private void publishWebservice() {
		Endpoint.publish("http://127.0.0.1:8080/struts2-helloworld/webservice/test", new TestWebservice());
	}
	
	/**
	 * 使用JDK开发WebService
	 * 客户端调用Webservice
	 * 
	 * 1、借助jdk的wsimort.exe工具生成客户端代码，wsimort.exe工具位于Jdk的bin目录下
	 * 执行命令：wsimport -keep url(url为wsdl文件的路径)生成客户端代码
	 * 打开命令行窗口，切换到src目录，执行"wsimport -keep http://192.168.1.100:8888/WebService?wsdl"生成客户端代码
	 * 
	 * 2、 借助生成的代码编写调用WebService对外提供的方法
	 * wsimport工具帮我们生成了好几个java类，但我们只需要关心WebServiceImplService类和WebServiceImpl接口的使用即可
	 * //创建一个用于产生WebServiceImpl实例的工厂，WebServiceImplService类是wsimport工具生成的
	 * WebServiceImplService factory = new WebServiceImplService();
	 * //通过工厂生成一个WebServiceImpl实例，WebServiceImpl是wsimport工具生成的
	 * WebServiceImpl wsImpl = factory.getWebServiceImplPort();
	 * //调用WebService的sayHello方法
	 * String resResult = wsImpl.sayHello("孤傲苍狼");
	 * 
	 */
}
