package com.laozhang.struts2.domain.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.laozhang.struts2.admin.dao.UserManageDao;
import com.laozhang.struts2.admin.model.UserInfo;
import com.laozhang.struts2.base.model.Pagination;
import com.laozhang.struts2.base.service.impl.BaseServiceImpl;
import com.laozhang.struts2.domain.service.UserInfoService;

public class UserInfoServiceImpl extends BaseServiceImpl implements
		UserInfoService {
	
	private UserManageDao userManageDao;
	
	

	public UserManageDao getUserManageDao() {
		return userManageDao;
	}



	public void setUserManageDao(UserManageDao userManageDao) {
		this.userManageDao = userManageDao;
	}



	public Pagination searchUserInfoPagination(UserInfo userInfo,
			Pagination pagination) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(userInfo.getUserId())) {
			map.put("userId", "%" + userInfo.getUserId() + "%");
		}
		if (StringUtils.isNotBlank(userInfo.getName())) {
			map.put("name", "%" + new String(userInfo.getName().trim().getBytes("ISO-8859-1"),"UTF-8") + "%");
		}
		
		return userManageDao.searchUserInfoPagination(pagination, map);
	}
	
	@Override
	public UserInfo searchUserById(Long id) throws Exception {
		return userManageDao.searchUserById(id);
	}
	
	@Override
	public void saveUser(UserInfo userInfo) throws Exception {
		userManageDao.addUser(userInfo);
	}

}
