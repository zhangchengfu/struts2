package com.laozhang.struts2.domain.action;

import util.JsonConverter;

import com.laozhang.struts2.admin.model.UserInfo;
import com.laozhang.struts2.base.action.BaseAction;
import com.laozhang.struts2.base.model.Pagination;
import com.laozhang.struts2.domain.service.UserInfoService;

public class UserInfoAction extends BaseAction {
	private UserInfoService userInfoService;
	private UserInfo userInfo;
	private Pagination pagination;
	private Long id;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	public String enterSearch() {
		userInfo = new UserInfo();
		return SUCCESS;
	}
	
	public void searchUserInfoPagination() {
		try {
			pagination = new Pagination(getRequest(), "pagination");
			pagination = userInfoService.searchUserInfoPagination(userInfo, pagination);
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().setContentType("html/txt");
			String[] fieldName = {"id","userId","name","userEditBtn"};
			String json = JsonConverter.convert2Json(pagination, fieldName, true);
			getResponse().getWriter().print(json);
			getResponse().getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String enterEdit() throws Exception {
		userInfo = userInfoService.searchUserById(id);
		return SUCCESS;
	}
	
	public String editUser() throws Exception {
		userInfoService.saveUser(userInfo);
		return SUCCESS;
	}
	
	public String enterAdd() throws Exception {
		return SUCCESS;
	}
}
