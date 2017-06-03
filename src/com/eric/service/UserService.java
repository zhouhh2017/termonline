package com.eric.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.eric.dao.ItemDAO;
import com.eric.dao.UserDAO;
import com.eric.model.Item;
import com.eric.model.NewWord;
import com.eric.model.User;

@Component

public class UserService {
	private UserDAO userDAO;
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	@Resource
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	
	
	@Transactional
	public void add(User u){
		userDAO.add(u);
//		throw new RuntimeException("need rollback");
	}
	
	//�û��Ƿ����
	public boolean checkUserExist(User u){
		return userDAO.checkUserExist(u);
	}
	//�û���¼
	public User checkUserLogin(User u) {
		// TODO Auto-generated method stub
		return userDAO.checkUserLogin(u);
	}

	@Transactional
	public void update(User user) {
		// TODO Auto-generated method stub
		userDAO.update(user);
	}

	public List<String> getItemChanged(String userName) {
		// TODO Auto-generated method stub
		return userDAO.getItemChanged(userName);
	}

	public void confirmItemChange(String userName) {
		// TODO Auto-generated method stub
		userDAO.confirmItemChange(userName);
	}

	public List<NewWord> searchHistory(String userName) {
		// TODO Auto-generated method stub
		return userDAO.searchHistory(userName);
	}

	public void historyDel(String[] delNames, String userName) {
		// TODO Auto-generated method stub
		userDAO.historyDel(delNames,userName);
	}

	public boolean checkName(String name) {
		// TODO Auto-generated method stub
		return userDAO.checkName(name);
	}

	
	
}
