package com.bigguy.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bigguy.bos.domain.Decidedzone;
import com.bigguy.bos.service.IDecidedzoneService;
import com.bigguy.bos.web.action.base.BaseAction;
import com.bigguy.ws.customer.Customer;
import com.bigguy.ws.customer.ICustomerService;

@Controller
public class DecidedzoneAction extends BaseAction<Decidedzone> {

	private String[] subareaId; 		// 用于接受分区id ， 将其关联到分区
	
	@Autowired
	IDecidedzoneService service;
	
	@Autowired
	ICustomerService customerService; 			// 注入操作客户的服务，这个服务是通过 spring 从webservice中注入的

	private String decidedzone_id;
	
	private String []customerIds; 		// 用于改变客户是否关联 id
	
	
	
	
	
	public String findListHasAssociation() {
		List<Customer> list = customerService.findListAssociation(decidedzone_id);
		
		String java2json = java2json(list, new String[] {});
		prinToPage(java2json);
		
		return NONE;
	}
	
//	public String findListAssociation(String decidedzone_id) {
//		List<Customer> list = customerService.findListAssociation(decidedzone_id);
//		
//		String java2json = java2json(list, new String[] {});
//		prinToPage(java2json);
//		
//		return NONE;
//	}
	
	public String findListNotAssociation() {
		List<Customer> list = customerService.findNotAssociation();
		
		String java2json = java2json(list, new String[] {});
		prinToPage(java2json);
		
		return NONE;
	}
	
	
	public String pageQuery() {
		// 发送 ajax 

		service.pageQuery(pageBean);
		// 将所有的定区拿出，其中在每个定区中包含了 ， staff 的信息
		
		// 去掉一些防止死循环的 属性  , 去掉 region 里面可能存在死循环的隐患 
		String java2json = java2json(pageBean, new String[] {"subareas","decidedzones"}); 
		
		prinToPage(java2json);
		
		return NONE;
	}
	
	public String save() {
		
		service.save(model,subareaId); 		
		
		
		return LIST;
	}

	public void setSubareaId(String[] subareaId) {
		this.subareaId = subareaId;
	}

	public String getDecidedzone_id() {
		return decidedzone_id;
	}

	public void setDecidedzone_id(String decidedzone_id) {
		this.decidedzone_id = decidedzone_id;
	}

	public String[] getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}
	
}
