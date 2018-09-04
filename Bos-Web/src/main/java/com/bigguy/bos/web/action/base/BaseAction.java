package com.bigguy.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.bigguy.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	protected static final String LIST = "list"; 
//	protected Integer page;			// 将公共部分代码提取出来 
//	protected Integer rows;			// 子类可以访问 
	protected PageBean pageBean = new PageBean(); 		// 需要自动　new 
	DetachedCriteria daCriteria = null; 		// 封装规则 
	
	public void setPage(Integer page) {		// 将属性的值封装到属性中是找其 set 方法，所以可以直接将 page 和 rows 属性去掉，提供其对于的 set 方法即可 
		pageBean.setCurrentPage(page);
	}

	public void setRows(Integer rows) {
		pageBean.setPageSize(rows);
	}
	
	// 模型
	protected T model; 		// 此时是不能够实例化的
	
	public String java2json(Object obj,String []exclueds) {
//		return JSONObject.fromObject(obj);
		
		JsonConfig config = new JsonConfig();
		config.setExcludes(exclueds);
		
		return JSONObject.fromObject(obj,config).toString();
	}
	
	public String java2json(List obj,String []exclueds) {
//		return JSONObject.fromObject(obj);
		
		JsonConfig config = new JsonConfig();
		config.setExcludes(exclueds);
		
		return JSONArray.fromObject(obj,config).toString();
	}
	
	public void prinToPage( Object object) {
		// 将 object 的数据写到 页面中
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		try {
			response.getWriter().print(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public BaseAction(){
		
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = superclass.getActualTypeArguments();
		
		Class<T> entityClass = (Class<T>)actualTypeArguments[0]; 		// 因为只有一个 T，没有 K ,V 之类的
		
		daCriteria = DetachedCriteria.forClass(entityClass);			// 通过反射机制 
		pageBean.setDetachedCriteria(daCriteria); 			// 牢记将其设置进入 
		try {
			model = entityClass.newInstance();			// 反射得到实例化对象
		} catch (InstantiationException e) {
			System.out.println("反射实例化出错");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("反射实例化出错");
			e.printStackTrace();
		} 		
	}
	
	
	public T getModel() {
		return model;
	}
	
}
