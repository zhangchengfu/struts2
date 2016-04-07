package com.laozhang.struts2.base.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.laozhang.struts2.base.dao.BaseDao;
import com.laozhang.struts2.base.service.BaseService;

public class BaseServiceImpl implements BaseService {
	
	protected final Log logger = LogFactory.getLog(getClass());

	protected BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

}
