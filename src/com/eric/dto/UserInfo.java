package com.eric.dto;

import java.util.Date;

import com.eric.model.User;

/**
 * �û�ע��ʱ����Ϣ
 * @author Eric
 *
 */
public class UserInfo {
	private String username;
	private String password;
	private String passwordConfirm;
	private String email;
	private String phone;
	private int level;
	//��������Ƿ�ƥ��
	public boolean checkPasswordEquals(){
//		System.out.println(password + "|" + passwordConfirm);
//		System.out.println("ע����û���:" + username + "|����:" + password + "|����ȷ��" + passwordConfirm
//				+ "|����:" + email + "|�ֻ��:" + phone);
		return password.equals(passwordConfirm);
	}
	//����û����Ƿ�Ϊ��
	public boolean checkUserNameIsEmpty(){
		return "".equals(username);
	}
	//��������Ƿ�Ϊ��
	public boolean checkPasswordIsEmpty(){
		return "".equals(password);
	}
	
	//Ϊ�û�ʵ����ע����Ϣ
	public void setUserInfo(User user){
		user.setUserName(username);
		user.setPassword(password);
		user.setRegisterDate(new Date());
		user.setEmail(email==null?"null":email);
		user.setPhone(phone==null?"null":phone);
		user.setLevel(0);
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
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
	
}
