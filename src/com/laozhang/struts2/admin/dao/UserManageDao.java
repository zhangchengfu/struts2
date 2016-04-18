package com.laozhang.struts2.admin.dao;

import java.util.List;
import java.util.Map;

import com.laozhang.struts2.admin.model.RoleInfo;
import com.laozhang.struts2.admin.model.UserInfo;
import com.laozhang.struts2.base.model.Pagination;

public interface UserManageDao {
	UserInfo findUserById(String id);
	List<RoleInfo> getRoleListByUserId(Map<String, String> map);
	boolean addUser(UserInfo userInfo);
	UserInfo searchUserById(Long id);
	List<RoleInfo> getAllRoleList();
	Pagination searchUserInfoPagination(Pagination pagination, Map map) throws Exception;
}
