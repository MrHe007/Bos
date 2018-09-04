package com.bigguy.bos.web.action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
public class PublicAction  extends ActionSupport{

	public String getCheckCode() throws IOException {
		
		String code = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		System.out.println("code : "+code);
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		System.out.println("=-=====");
		ServletActionContext.getResponse().getWriter().write(code);
		return NONE;
	}
	
	
}
