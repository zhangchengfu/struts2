package com.laozhang.struts2.admin.model;

import com.laozhang.struts2.base.model.BaseObject;

public class RoleInfo extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer roleId;
	
	private String roleDesc;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	
}
