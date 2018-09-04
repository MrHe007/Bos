package com.bigguy.bos.service;

import com.bigguy.bos.domain.User;

public interface IUserService {

	public User login(User user);
	
	
	public void editPassword(String id,String password);
	
}
