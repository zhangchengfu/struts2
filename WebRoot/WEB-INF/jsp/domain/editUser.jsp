<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/Taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'editUser.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript">
		function validateName()
		{
			var name = $("#name").val();
			var errorMsg = $("#nameErrorMsg");
			if(name=='')
			{
				errorMsg.html('必填项');
				return false;
			}
			else
			{
				errorMsg.html('');
			}
		}
		
		function validateEmail()
		{
			var email = $("#email").val();
			var errorMsg = $("#emailErrorMsg");
			var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
			if(email!='')
			{
				if(reg.test(email))
				{
					errorMsg.html('');
				}
				else
				{
					errorMsg.html('请输入有效的Email');
					return false;
				}	
			}
			else
			{
				errorMsg.html('必填项');
				return false;
			}
		}
		
		function goBack()
		{
			window.location.href = "<%=path%>/user/enterSearch.action";
			/* window.close(); */
		}
		
		function edit(id) {
			if (validateName && validateEmail) {
				document.forms[0].submit();
				/* goBack(); */
			
			}
		}
	
	</script>

  </head>
  
  <body>
    <div id="main">
    	<div id="right">
    		<div style="text-align:center; margin-top:20px;">
    			<form action="<c:url value="/domain/editUser.action"/>" method="post">
    				<input type="hidden" id="id" name="id" value="<c:out value="${userInfo.id}"></c:out>"/>
    				<table border="0" width="950" style="margin-left:50px" class="form_table">
    					<tr>
    						<td width="100"><span class="star">*</span>用户名：</td>
    						<td width="180"><input type="text" id="userId" name="userId" value="<c:out value="${userInfo.userId}"></c:out>" readonly="readonly" maxlength="20"/></td>
    						<td width="120"><div id="userIdErrorMsg" class="msg"></div></td>
    						<td width="120"><span class="star">*</span>用户姓名：</td>
    						<td width="180"><input type="text" id="name" name="name" value="<c:out value="${userInfo.name}"></c:out>" onblur="validateName()" maxlength="40"/></td>
							<td width="120"><div id="nameErrorMsg" class="msg"></div></td>
    					</tr>
    					<tr>
							<td class="table_label"><label class="control-label"><span class="star">*</span>邮箱：</label></td>
							<td>
								<input type="text" id="email" name="email" value="<c:out value="${userInfo.email}"></c:out>" onblur="validateEmail()"/>
							</td>
							<td><div id="emailErrorMsg" class="msg"></div></td>
							<td class="table_label"><label class="control-label"><span class="star">*</span>手机：</label></td>
							<td>
								<input type="text" id="tel" name="tel" value="<c:out value="${userInfo.tel}"></c:out>" onblur="validateTel()"/>
							</td>
							<td><div id="telErrorMsg" class="msg"></div></td>
						</tr>
						<tr>
							<td></td>
							<td colspan="5">
								<button type="button" class="btn btn-warning" onclick="edit('<c:out value="${userInfo.id}"></c:out>');" style="margin-left:0px" >提交</button>
								<button type="button" class="btn btn-warning" onclick="goBack();">返回</button>
							</td>
						</tr>
    				</table>
    			</form>
    		</div>
    	</div>
    </div>

  </body>
</html>
