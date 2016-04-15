package com.laozhang.struts2.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import sun.misc.BASE64Encoder;

import com.laozhang.struts2.admin.dao.UserManageDao;
import com.laozhang.struts2.admin.model.UserInfo;

public class MyUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	
	private UserManageDao userManageDao;
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {

		String username = obtainUsername(request);
		String password = obtainPassword(request);
		if("".equals(username) || "".equals(password))
		{
			throw new AuthenticationServiceException(""); 
		}
		//UsernamePasswordAuthenticationToken实现 Authentication
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		//验证用户账号与密码
		UserInfo user = userManageDao.findUserById(username.trim());
		String pwdMD = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			pwdMD = base64en.encode(md5.digest(password.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user == null) {
			throw new AuthenticationServiceException("用户不存在！");
		} else {
			if (!pwdMD.equals(password)) {
				throw new AuthenticationServiceException("密码不正确！");
			}
		}
		//当前登录用户信息
		request.getSession().setAttribute("UserInfo", user);
		//允许子类设置详细属性
		setDetails(request, authRequest);
		//运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	@Override
	protected String obtainUsername(HttpServletRequest request) {
		String username = request.getParameter("username");
		return null == username ? "" : username;
	}
	
	@Override
	protected String obtainPassword(HttpServletRequest request) {
		String password = request.getParameter("password");
		return null == password ? "" : password;
	}

	public UserManageDao getUserManageDao() {
		return userManageDao;
	}

	public void setUserManageDao(UserManageDao userManageDao) {
		this.userManageDao = userManageDao;
	}
	
	
}
