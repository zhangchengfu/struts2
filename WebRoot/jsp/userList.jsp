<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'userList.jsp' starting page</title>
    
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
    <h1>用户列表</h1>
    <table>
    	<tr>
    		<th>ID</th>
    		<th>账号</th>
    		<th>性别</th>
    		<th>操作</th>
    	</tr>
    	<s:iterator value="userList" var="u">
    		<tr>
    			<td><s:property value="#u.id" /></td>
    			<td><s:property value="#u.name"/></td>
    			<td>
    				<s:if test="'M'==#u.gender">男</s:if>
    				<s:else>女</s:else>
    			</td>
    			<td></td>
    		</tr>
    	</s:iterator>
    	<tr></tr>
    </table>
  </body>
</html>
