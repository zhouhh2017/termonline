package com.eric.model;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.engine.internal.Cascade;

/**
 * 用户实体类
 * @author Eric
 *
 */
@Entity(name="t_user")
public class User {
	
	@Id
	private int id;
	//用户名
	private String userName;
	//密码
	private String password;
	//注册日期
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date registerDate;
	//电子邮件
	private String email;
	//手机
	private String phone;
	//等级(0为待审核，1为普通用户，2为会员)
	private int level;

	
	public static User newUser(User user){
		User u = new User();
		u.setUserName(user.getUserName().toString());
		u.setPassword(user.getPassword().toString());
		u.setEmail(user.getEmail().toString());
		u.setPhone(user.getPhone().toString());
		u.setRegisterDate(new Date());
		u.setLevel(user.getLevel());
		return u;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
}
