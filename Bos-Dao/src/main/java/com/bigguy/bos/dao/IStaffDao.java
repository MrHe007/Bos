package com.bigguy.bos.dao;

import com.bigguy.bos.dao.base.IBaseDao;
import com.bigguy.bos.domain.Staff;

public interface IStaffDao extends IBaseDao<Staff>{

	public void add(Staff staff);

	public void batchDelStaff(String[] idArr);
	
}
