package com.bigguy.bos.dao;

import org.apache.poi.ss.formula.functions.T;

import com.bigguy.bos.dao.base.IBaseDao;
import com.bigguy.bos.domain.User;

public interface IUserDao extends IBaseDao<User> {

	User findUserByUsernameAndPassword(String username, String password);
	
	public void  executeUpdate(String queryName,Object ...objs); 		// 同一执行更新操作

}
