package com.laozhang.struts2.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.laozhang.struts2.admin.dao.UserManageDao;
import com.laozhang.struts2.admin.model.RoleInfo;
import com.laozhang.struts2.admin.model.UserInfo;


public class MyUserDetailServiceImpl implements UserDetailsService {
	
	private UserManageDao userManageDao;

	public UserManageDao getUserManageDao() {
		return userManageDao;
	}

	public void setUserManageDao(UserManageDao userManageDao) {
		this.userManageDao = userManageDao;
	}


	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserInfo user = userManageDao.findUserById(username);
		Collection<GrantedAuthority> grantAuths = obtionGrantedAuthorities(user);
		boolean enables = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		//封装成spring security的user
		User userdetail = new User(user.getUserId(), user.getPassword(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantAuths); 
		return userdetail;
	}
	
	//取得用户的权限
	private Set<GrantedAuthority> obtionGrantedAuthorities(UserInfo user) {
		Set<GrantedAuthority> set = new HashSet<GrantedAuthority>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		List<RoleInfo> roles = userManageDao.getRoleListByUserId(map);
		for (RoleInfo role : roles) {
			set.add(new GrantedAuthorityImpl(role.getRoleDesc()));
		}
		return set;
	}
}
