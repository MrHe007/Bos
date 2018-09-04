package com.bigguy.bos.service;

import java.util.List;

import com.bigguy.bos.domain.Staff;
import com.bigguy.bos.utils.PageBean;

public interface IStaffService {

	public void add(Staff staff);
	
	public void pageQuery(PageBean pageBean);

	public void batchDelStaff(String ids);

	public void editStaff(Staff model);

	public List<Staff> findAvailableStaffList();
}
