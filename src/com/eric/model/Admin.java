package com.eric.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * ����Աʵ����
 * @author Eric
 *
 */

@Entity(name="t_admin")
public class Admin {
	//����Ա�û���
	@Id
	private String adminname;
	//����Ա����
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
