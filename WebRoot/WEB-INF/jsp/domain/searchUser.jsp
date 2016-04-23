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
    
    <title>My JSP 'searchUser.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<c:url value="/css/flexigrid.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<c:url value="/js/flexigrid.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			loadGrid();
		});
		
		function loadGrid(){
			$("#searchUserInfoGird").flexigrid({
				url: 'domain/searchUserInfoPagination.action?searchid=pagination',
				dataType: 'json', 
				colModel : [
					{display: '序号', name : '', width : 20, sortable : false, align: 'center',linkable:false}, 
					{display: '用户名', name : 'USER_ID', width : 100, sortable : true, align: 'center',linkable:false},
	  				{display: '用户全称', name : 'NAME', width : 112, sortable : true, align: 'center',linkable:false}, 
	  				{display: '编辑', name : 'userEditBtn', width : 30, sortable : false, align: 'center',linkable:true}
				],
				sortname: "ROLE_DESC",
				sortorder: "desc",
				usepager: true,
				rpOptions: [10, 15, 20],//可选择设定的每页结果数
				striped: true, //是否显示斑纹效果，默认是奇偶交互的形式
				title: '查询结果', 
				useRp: false, 
				rp: 10,
				nomsg: '没有找到任何记录', 
				showTableToggleBtn: true, 
				width: 'auto',
				height:'auto',
				onSuccess:function(para){
	  				userCallBack();
	  			},
	  			loadData:true
			});
		}
		
		function userCallBack(){
			$("a[name='userEditBtn']").click(function(){
	   			var str = $(this).attr("id").split("-");
	   			window.location.href = "<%=path%>/domain/enterEdit.action?id="+str[0];
   			});
		}
		
		function doSearch() {
			 $("#searchUserInfoGird").flexReload();
    	}
    	
    	function doReset() {
    		$("#userId").val('');
    		$("#name").val('');
    	}
    	
    	function doAdd() {
    		window.location.href = "<%=path%>/domain/enterAdd.action";
    	}
	</script>

  </head>
  
  <body>
    <div id="main">
    	<div id="right">
    		<div class="right_title"><a style="margin-left:10px; line-height:34px;">用户查询</a></div>
    		
    		<s:form id="searchForm" action="/domain/searchUserInfoPagination.action" method="post">
    			<div class="well widget">
    				<table border="0" width="100%" class="form_table" id="searchCondition">
    					<tr>
    						<td style="width:20%" class="table_label"><label class="control-label">用户名：</label></td>
    						<td style="width:20%"><s:textfield id="userId" name="userInfo.userId" theme="simple"/></td>
    						<td style="width:20%" class="table_label"><label class="control-label">用户姓名：</label></td>
							<td style="width:40%"><s:textfield id="name" name="userInfo.name" theme="simple" /></td>
    					</tr>
    					<tr>
    						<td></td>
    						<td>
    							<button type="button" class="btn btn-warning" onclick="doSearch();" style="margin-left:0px">查询</button>
    							<button type="button" class="btn btn-warning" onclick="doAdd();">添加</button>
    							<button type="button" class="btn btn-warning" onclick="doReset();">清空</button>
    						</td>
    					</tr>
    				</table>
    			</div>
    			
    			<div style="padding-left: 12px;padding-right: 12px">
					<table id="searchUserInfoGird"></table> 
				</div>
    		</s:form>
    	</div>
    </div>
  </body>
</html>
