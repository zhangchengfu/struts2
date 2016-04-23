package com.laozhang.struts2.admin.dao.impl;

import java.util.List;
import java.util.Map;

import com.laozhang.struts2.admin.dao.UserManageDao;
import com.laozhang.struts2.admin.model.RoleInfo;
import com.laozhang.struts2.admin.model.UserInfo;
import com.laozhang.struts2.base.dao.impl.BaseDaoImpl;
import com.laozhang.struts2.base.model.Pagination;

public class UserManageDaoImpl extends BaseDaoImpl implements UserManageDao {

	public UserInfo findUserById(String id) {
		List<UserInfo> list = findAllBy(UserInfo.class, "userId", id);
		return (list == null || list.size() < 1 ? null : list.get(0));
	}

	public List<RoleInfo> getRoleListByUserId(Map<String, String> map) {
		return (List<RoleInfo>)findListByCombinedHsql("getRoleListByUserId", map);
	}
	
	public List<RoleInfo> getAllRoleList() {
		return getAllObject(RoleInfo.class);
	}

	public boolean addUser(UserInfo userInfo) {
		try {
			saveObject(userInfo);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public UserInfo searchUserById(Long id) {
		return (UserInfo) findBy(UserInfo.class, "id", id);
	}
	
	public Pagination searchUserInfoPagination(Pagination pagination, Map map) throws Exception {
		return findPageByCombinedHsql("searchUserInfoPagination", map, pagination);
	}

}
