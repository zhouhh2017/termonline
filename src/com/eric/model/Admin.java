package com.eric.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 管理员实体类
 * @author Eric
 *
 */

@Entity(name="t_admin")
public class Admin {
	//管理员用户名
	@Id
	private String adminname;
	//管理员密码
	private String password;
	public String getAdminname() {
		return adminname;
	}
	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
