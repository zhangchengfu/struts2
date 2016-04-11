package com.laozhang.struts2.admin.model;

import com.laozhang.struts2.base.model.BaseObject;

public class RelUserRole extends BaseObject {
private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String userId;
	
	private Long roleId;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public Long getRoleId()
	{
		return roleId;
	}

	public void setRoleId(Long roleId)
	{
		this.roleId = roleId;
	}
}
