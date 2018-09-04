package com.bigguy.bos.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bigguy.bos.dao.IStaffDao;
import com.bigguy.bos.dao.base.impl.BaseDaoImpl;
import com.bigguy.bos.domain.Staff;

@Repository
@Transactional
public class StaffDao extends BaseDaoImpl<Staff> implements IStaffDao {

	@Override
	public void add(Staff staff) {
		this.save(staff);
	}

	@Override
	public void batchDelStaff(String[] idArr) {
		
		
		
		
		
	}

}
