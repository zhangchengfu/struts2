<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <h1 style="color: blue">用户登录</h1>
    <form action="j_spring_security_check" method="post">
    	Username:<!-- <s:textfield name="user.name"></s:textfield> -->
    	<input type="text" name="username" id="username" class="table_input" tabindex="1"/>
    	<br/>
    	Password:<!-- <s:password name="user.password"></s:password> -->
    	<input name="password" id="password" type="password" class="table_input" tabindex="2"/>
    	<br/>
    	<%-- <s:submit value="登录"></s:submit> --%>
    	<span style="color:red" id="error">
			<%=session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)==null? "":session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION).toString().replaceAll("org.springframework.security.authentication.AuthenticationServiceException:","") %>
		</span>
		<br/>
    	<input type="submit" value="登录" />
    </form>
  </body>
</html>
