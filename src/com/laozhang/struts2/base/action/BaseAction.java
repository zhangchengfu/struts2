package com.laozhang.struts2.base.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.laozhang.struts2.admin.model.UserInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected HttpServletRequest getRequest() {
		ActionContext context = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) context.get(ServletActionContext.HTTP_REQUEST);
		return request;
	}
	
	protected HttpServletResponse getResponse() {
		ActionContext context = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse) context.get(ServletActionContext.HTTP_RESPONSE);
		return response;
	}
	
	protected UserInfo getCurrentUserInfo() {
		return (UserInfo) getRequest().getSession().getAttribute("UserInfo");
	}
}
