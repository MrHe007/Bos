package com.bigguy.bos.web.action.base;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bigguy.bos.domain.Staff;
import com.bigguy.bos.service.IStaffService;
import com.bigguy.bos.utils.PageBean;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{

	private Integer page;
	private Integer rows;
	
	@Autowired
	IStaffService service;
	
	
	public String getAvaibleStaffListAjax() {
		
		// 拿到没有被删除的staff
		
		List<Staff> StaffList = service.findAvailableStaffList();
		
		String java2json = java2json(StaffList, new String[] {"decidedzones"}); 		// 去掉相关联的对象防止死循环
		
		prinToPage(java2json);
		
		return NONE;
	}
	
	public String edit(){
		
		// 先通过 id 查找出 staff ,后在通过 model 去覆盖数据
		System.out.println("demo");
		service.editStaff(model);
		
		return LIST;
	}
	
	
	
	private String ids; 		// 得到的是 1-2-3 格式的id 字符串
	
	public String delStaff() {
		
		service.batchDelStaff(ids);
		
		
		
		return LIST;
	}
	
	
	public String pageQuery() throws IOException {
		
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(page); 		// 设置当前访问的页 
		pageBean.setPageSize(rows); 	// 每页显示的消息总数
		
		// 创建离线提交查询对象 
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		pageBean.setDetachedCriteria(detachedCriteria);
		
		service.pageQuery(pageBean); 		// 访问后，现在所有的数据都封装到 pageBean 中了
		
		JsonConfig config = new JsonConfig();		// 用于配置，过滤掉一些数据不传到页面 
		config.setExcludes(new String[]{"currentPage","pageSize"});
		
		JSONObject jsonObj = JSONObject.fromObject(pageBean);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(jsonObj);
		
		return NONE;
	}
	
	public String save() {
		
		System.out.println("save");
		service.add(model); 		// model 在父类中 
		
		return LIST;
	}

	
	
	
	
	

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
	
}
