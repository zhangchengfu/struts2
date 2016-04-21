package com.laozhang.struts2.domain.service;

import com.laozhang.struts2.admin.model.UserInfo;
import com.laozhang.struts2.base.model.Pagination;
import com.laozhang.struts2.base.service.BaseService;

public interface UserInfoService extends BaseService {
	Pagination searchUserInfoPagination(UserInfo userInfo, Pagination pagination) throws Exception;
	UserInfo searchUserById(Long id) throws Exception;
	void saveUser(UserInfo userInfo) throws Exception;
	UserInfo findUserById(String id) throws Exception;
}
