package com.bigguy.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bigguy.bos.dao.IStaffDao;
import com.bigguy.bos.dao.base.impl.BaseDaoImpl;
import com.bigguy.bos.domain.Staff;
import com.bigguy.bos.service.IStaffService;
import com.bigguy.bos.utils.PageBean;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService {

	@Autowired
	IStaffDao dao;
	
	@Override
	public void add(Staff staff) {
		
		dao.add(staff);
		
	}

	public void pageQuery(PageBean pageBean) {
		dao.pageQuery(pageBean);
	}

	@Override
	public void batchDelStaff(String ids) {
		// 实际上不是正真的删除，而是把 staff 表的删除位置为1
		String[] idArr = ids.split(",");
		
		for (String id : idArr) {		// 遍历执行 sql
			dao.executeUpdate("staff.batchUpdate", id);
		}
		
	}

	@Override
	public void editStaff(Staff model) {
		
		Staff staff = dao.findById(model.getId());
		
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setStation(model.getStation());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		
		dao.update(staff);
		
	}

	@Override
	public List<Staff> findAvailableStaffList() {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Staff.class);
		criteria.add(Restrictions.eq("deltag", "0")); 		// 返回没有被删除的 staff 
		
		return dao.findByCriteria(criteria);
	}
}
