package com.bigguy.bos.service.impl;

import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bigguy.bos.dao.IUserDao;
import com.bigguy.bos.domain.User;
import com.bigguy.bos.service.IUserService;
import com.bigguy.bos.utils.BOSUtils;
import com.bigguy.bos.utils.MD5Utils;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	IUserDao dao;
	
	public User login(User user) {
		
		String password = MD5Utils.md5(user.getPassword()); 		// 进行 md5 加密
		
		return dao.findUserByUsernameAndPassword(user.getUsername(),password);
		
	}

	public void editPassword(String id, String password) {
		
		password = MD5Utils.md5(password);
		dao.executeUpdate("user.editpassword", password,id); 		// 执行 dao 中的通用更新方法
		
	}

}
