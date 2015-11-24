package com.laozhang.struts2.action;

import java.util.ArrayList;
import java.util.List;

import org.fluttercode.datafactory.impl.DataFactory;

import com.laozhang.struts2.entity.User;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	private List<User> userList;
	
	public String login() {
		System.out.println(user);
		return SUCCESS;
	}
	
	public String list() {
		getInstance();
		return SUCCESS;
	}
	
	private void getInstance() {
		userList = new ArrayList<User>();
		DataFactory df = new DataFactory();
		List<String> cityList = new ArrayList<String>();
		cityList.add("SZ");
		cityList.add("BJ");
		cityList.add("SH");
		cityList.add("GZ");
		
		List<String> hobbyList = new ArrayList<String>();
		hobbyList.add("study");
		hobbyList.add("online");
		hobbyList.add("book");
		hobbyList.add("game");
		hobbyList.add("food");
		
		for (int id = 0; id < 20; id++) {
			User user = new User();
			user.setId(id+1);
			user.setName(df.getName());
			user.setPassword(df.getRandomWord());
			user.setCity(cityList.get(df.getNumberBetween(0, 3)));
			user.setGender(df.chance(60)?"M":"F");
			user.setHobby(hobbyList.get(df.getNumberBetween(0, 4)));
			user.setRemark(df.getRandomChars(20, 50));
			userList.add(user);
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	

}
