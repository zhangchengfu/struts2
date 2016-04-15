package com.laozhang.struts2.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.laozhang.struts2.admin.dao.UserManageDao;
import com.laozhang.struts2.admin.model.RoleInfo;
/**
 * 加载资源与权限的对应关系
 * @author zhang chengfu
 *
 */
public class MySecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {
	
	private UserManageDao userManageDao;
	
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	public MySecurityMetadataSource(UserManageDao userManageDao) {
		this.userManageDao = userManageDao;
		
	}
	
	//加载所有资源与权限的关系
	private void loadResourceDefine() {
		if(resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<RoleInfo> resources = userManageDao.getAllRoleList();
			for (RoleInfo resource : resources) {
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				//以权限名封装为Spring的security Object
				ConfigAttribute configAttribute = new SecurityConfig(resource.getRoleDesc());
				configAttributes.add(configAttribute);
				resourceMap.put(resource.getRoleDesc(), configAttributes);
			}
		}
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		if(resourceMap == null) {
			loadResourceDefine();
		}
		//return resourceMap.get(requestUrl);
		if (requestUrl.contains("domain")) {
			return resourceMap.get("ROLE_ADMIN");
		} else {
			return resourceMap.get("ROLE_USER");
		}
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	public UserManageDao getUserManageDao() {
		return userManageDao;
	}

	public void setUserManageDao(UserManageDao userManageDao) {
		this.userManageDao = userManageDao;
	}

	
}
