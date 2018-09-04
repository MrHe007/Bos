package com.bigguy.bos.utils;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.bigguy.bos.domain.User;

public class BOSUtils {

	public static HttpSession  getCurSession() {
		return ServletActionContext.getRequest().getSession();
	}
	
	public static User getCurUser() { 
		/// 返回当前登入的用户
		return (User) getCurSession().getAttribute("loginUser");
	}
	
}
