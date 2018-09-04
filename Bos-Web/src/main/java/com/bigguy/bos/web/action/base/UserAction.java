package com.bigguy.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bigguy.bos.domain.User;
import com.bigguy.bos.service.IUserService;
import com.bigguy.bos.utils.BOSUtils;
import com.bigguy.ws.customer.Customer;
import com.bigguy.ws.customer.ICustomerService;

@Controller
@Scope("prototype")		// 变成实例
public class UserAction extends BaseAction<User> {

	private String checkcode;
	
	@Autowired 				// 用的数才会注入 
	IUserService userService;
	
	
	public String editPassword() throws IOException {
		
		System.out.println("editPassword");
		int flag = 1;	 	// 用于通知 ajax 的回调函数 ， 是否更新成功的标识 
		
		User user = (User) BOSUtils.getCurSession().getAttribute("loginUser");
		try {
			userService.editPassword(user.getId(), model.getPassword());
			
		}catch(Exception e) {
			flag = 0;
			e.printStackTrace();
		}
		
		// 设置回显的方法
		ServletActionContext.getResponse().setContentType("text/html;character=utf-8");
		
		ServletActionContext.getResponse().getWriter().print(flag);
		
		return NONE; 		// 标识不返回任何的物理视图 
	}
	
	
	public String logout() {
		
		ServletActionContext.getRequest().getSession().invalidate(); 		// 销毁 session 
		
		return LOGIN;
	}
	
	public String login() {
		
		
//		List<Customer> clist = customerService.findAll();
		
		
		
		// 因为在 BaseAction 中已经实例化了 Model ，在里面会实例化 Model
		HttpSession session = ServletActionContext.getRequest().getSession(); 	// 得到 session 
		String realCode = (String) session.getAttribute("key");
		
		System.out.println("code: "+realCode);
		
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(realCode) ){ 	// 表示验证码正确
			
			 // 验证码正确，进而验证用户名密码是否匹配
			
			User user = userService.login(model);		// 跳到 UserService 进行处理 
			if(user!=null) { 	// 则登入成功
				System.out.println("登入成功!");
				session.setAttribute("loginUser", user);
				return "home";
				
			}else {
				System.out.println("用户名或密码错误!");
				this.addActionError("用户名或密码错误!");
				return "login";
			}
			
			
		}else { 	// 验证码错误
			// 跳回 login.jsp
			System.out.println("验证码错误!");
			this.addActionError("验证码错误!");
			return LOGIN;
		}
	}


	public void setCheckcode(String checkcode) {
//		System.out.println("setCheckcode");
		this.checkcode = checkcode;
	}


	public String getCheckcode() {
//		System.out.println("getCheckcode");
		return checkcode;
	}
}
