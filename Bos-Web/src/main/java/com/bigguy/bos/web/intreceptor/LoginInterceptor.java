package com.bigguy.bos.web.intreceptor;

import com.bigguy.bos.domain.User;
import com.bigguy.bos.utils.BOSUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class LoginInterceptor extends MethodFilterInterceptor {

	
	
	// 用户登入拦截未登入用户
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		// 这个拦截器可以拦截所有的请求

		ActionProxy proxy = invocation.getProxy();
		String actionName = proxy.getActionName();
		String namespace = proxy.getNamespace();
		
		String url = actionName +" " + namespace;
		
		User user = BOSUtils.getCurUser();
		
		if(user==null) { 	// 表示已经登入了
			return "login"; 			// 这里返回的 String 是表示返回的结果页面 
		}
		
		return invocation.invoke();
	}

}
