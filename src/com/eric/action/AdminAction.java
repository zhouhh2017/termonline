package com.eric.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.eric.model.Admin;
import com.eric.model.NewWord;
import com.eric.service.AdminService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Component("adminAction")
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/admin")
public class AdminAction extends ActionSupport implements ServletRequestAware,ModelDriven<Admin>{
	private HttpServletRequest request;
	private Admin admin = new Admin();
	private AdminService adminService;
	
	
	@Action(
		value="adminAction",
		results={
			@Result(name="admin",location="/WEB-INF/page/adminIndex.jsp"),
			@Result(name="adminLogin",location="/WEB-INF/page/admin.jsp"),
			@Result(name="addItem",location="/addItem.jsp")
		}
	)
	
	@Override
	public String execute(){
		return "admin";
	}
	
	//验证管理员登录
	public String adminLoginValid(){
		if(adminService.loginValid(admin)){
			request.getSession().setAttribute("adminName", admin.getAdminname());
			return "adminLogin";
		}else{
			return "admin";
		}
		
	}
	
	public String loadNewItemForAdd(){
		List<String[]> result = adminService.searchNewWord();
		request.setAttribute("addItemInfo", result);
		return "addItem";
	}
	
	
	
	@Override
	public Admin getModel() {
		// TODO Auto-generated method stub
		return admin;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	public AdminService getAdminService() {
		return adminService;
	}
	@Resource
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}


	
	
}
