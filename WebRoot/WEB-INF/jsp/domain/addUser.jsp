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
    
    <title>My JSP 'addUser.jsp' starting page</title>
    
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
	
	function validatePwd()
	{
		var password = $("#password").val();
		var passwordConfirm = $("#passwordConfirm").val();
		var passwordErrorMsg = $("#passwordErrorMsg");
		var passwordConfirmErrorMsg = $("#passwordConfirmErrorMsg");
		var reg = /^(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]{6,}$/;
		if(password!='')
		{
			if(reg.test(password))
			{
				passwordErrorMsg.html('');
			}
			else
			{
				passwordErrorMsg.html('密码至少六位且只能包含英文字母和数字');
				return false;
			}
			
			if(passwordConfirm!='')
			{
				if(password==passwordConfirm)
				{
					passwordConfirmErrorMsg.html('');
				}
				else
				{
					passwordConfirmErrorMsg.html('两次密码不匹配');
					return false;
				}	
			}
			else
			{
				passwordConfirmErrorMsg.html('必填项');
				return false;
			}
		}
		else
		{
			passwordErrorMsg.html('必填项');
			return false;
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
	
	function validateTel()
	{
		var tel = $("#tel").val();
		var errorMsg = $("#telErrorMsg");
		var reg = /^1[3|4|5|8][0-9]\d{8}$/;
		if(tel!='')
		{
			if(reg.test(tel))
			{
				errorMsg.html('');
			}
			else
			{
				errorMsg.html('请输入有效的手机号码');
				return false;
			}	
		}
		else
		{
			errorMsg.html('必填项');
			return false;
		}
	}
	
	function save() {
		if (validateName() && validatePwd() && validateEmail() && validateTel()) {
			$.ajax({
				url:"userJson/validateUserId.action",
				type:'POST',
				data:{'userId':$("#userId").val()},
				dataType:'json',
				success:function(response){
					var result = response.result;
					if (result == true) {
						$.ajax({
							url:"domain/addUser.action",
							type:POST,
							data:$('form:first').serialize();
							dataType:'json',
							fuccess:function(response){
								var result = response.result;
								if (result != null && result==true) {
									alert('新增成功');
									window.location.href = "<%=path%>/domain/enterSearch.action";
								} else {
									alert('新增失败');
								}
							}
						});
					} else  {
						alert("用户名已被注册过")
					}
				}
			});
		}
	}
	
	function validateUserId()
	{
		var userId = $("#userId").val();
		var errorMsg = $("#userIdErrorMsg");
		var reg = /^[a-zA-Z\d]+$/;
		
		if(userId!='')
		{
			if(reg.test(userId))
			{
				errorMsg.html('');
				$.ajax({
				 	url:'userJson/validateUserId.action', 
					type: 'POST',
					data: {userId:userId}, 
					dataType:'json',
					success: function(response)
					{
						var result = response.result;
						if (result==true) {
							errorMsg.html('');
						}
						else
						{
							errorMsg.html('用户已存在');
						}
					}
		    	});
			}
			else
			{
				errorMsg.html('请输入英文字母或数字');
			}
		}
		else
		{
			errorMsg.html('必填项');
		}
	}
	
	function goBack()
	{
		window.location.href = "<%=path%>/domain/enterSearch.action";
	}
	
	</script>

  </head>
  
  <body>
    <div id="main">
    	<div id="right">
    		<div style="text-align:center; margin-top:20px;">
    			<form action="<c:url value="/userJson/addUser.action"/>" method="post">
    				<table border="0" width="950" class="form_table" style="margin-left:50px">
    					<tr>
    						<td width="100" class="table_label"><label class="control-label"><span class="star">*</span>用户名：</label></td>
    						<td width="180"><input type="text" id="userId" name="userInfo.userId" onblur="validateUserId()" maxlength="20"/></td>
    						<td width="120"><div id="userIdErrorMsg" class="msg"></div></td>
							<td width="120" class="table_label"><label class="control-label"><span class="star">*</span>用户姓名：</label></td>
							<td width="180"><input type="text" id="name" name="userInfo.name" onblur="validateName()" maxlength="40"/></td>
							<td width="120"><div id="nameErrorMsg" class="msg"></div></td>
    					</tr>
    					<tr>
							<td class="table_label"><label class="control-label"><span class="star">*</span>密码：</label></td>
							<td><input type="password" id="password" name="userInfo.password" onblur="validatePwd()"/></td>
							<td><div id="passwordErrorMsg" class="msg"></div></td>
							<td class="table_label"><label class="control-label"><span class="star">*</span>确认密码：</label></td>
							<td><input type="password" id="passwordConfirm" name="userInfo.passwordConfirm" onblur="validatePwd()"/></td>
							<td><div id="passwordConfirmErrorMsg" class="msg"></div></td>
						</tr>
						<tr>
							<td class="table_label"><label class="control-label"><span class="star">*</span>邮箱：</label></td>
							<td><input type="text" id="email" name="userInfo.email" onblur="validateEmail()"/></td>
							<td><div id="emailErrorMsg" class="msg"></div></td>
							<td class="table_label"><label class="control-label"><span class="star">*</span>手机：</label></td>
							<td><input type="text" id="tel" name="userInfo.tel" onblur="validateTel()"/></td>
							<td><div id="telErrorMsg" class="msg"></div></td>
						</tr>
						<tr>
							<td class="table_label"><label class="control-label"><span class="star">*</span>所属角色：</label></td>
							<td>
								<select id="roleId" name="userInfo.roleId" onchange="searchOrgByRoleId();">
									<c:forEach var="roleInfoList" items="${roleInfoList}" varStatus="status">
										<option value="<c:out value='${roleInfoList.roleId}'></c:out>" 
										<c:if test="${roleId==roleInfoList.roleId}">
											 selected="selected"
										</c:if>
										/><c:out value='${roleInfoList.roleDesc}'></c:out>
									</c:forEach>
								</select>
							</td>
							<td><div id="roleIdErrorMsg" class="star"></div></td>
						</tr>
						<tr>
							<td></td>
							<td colspan="5">
								<button type="button" class="btn btn-warning" onclick="save();" style="margin-left:0px">提交</button>
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
