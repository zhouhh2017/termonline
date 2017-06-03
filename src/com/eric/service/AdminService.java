package com.eric.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.eric.dao.AdminDAO;
import com.eric.model.Admin;
import com.eric.model.Dictionary;
import com.eric.model.Item;
import com.eric.model.User;
import com.eric.model.Word;

@Component
public class AdminService {
	private AdminDAO adminDAO;

	public AdminDAO getAdminDAO() {
		return adminDAO;
	}

	@Resource
	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}
	
	
	//管理员登录验证
	public boolean loginValid(Admin admin){
		return adminDAO.loginValid(admin);
	}
	//查询新词
	public List<String[]> searchNewWord(){
		return adminDAO.searchNewWord();
	}
	//查询用户列表
	public List<User> getUserList(int page, int rows){
		return adminDAO.getUserList(page,rows);
	}

	//搜索新词
	public List<String[]> searchNewWord(int page, int rows) {
		// TODO Auto-generated method stub
		return adminDAO.searchNewWord(page,rows);
	}

	//搜索新词统计量
	public int getNewWordTotal() {
		// TODO Auto-generated method stub
		return adminDAO.getNewWordTotal();
	}

	@Transactional
	public void delNewItem(String ids) {
		// TODO Auto-generated method stub
		adminDAO.delNewItem(ids);
	}

	@Transactional
	public void insertOneItem(Item item) {
		// TODO Auto-generated method stub
		adminDAO.insertOneItem(item);
	}
	@Transactional
	public void changeNewWordStatus(String itemName) {
		// TODO Auto-generated method stub
		adminDAO.changeNewWordStatus(itemName);
	}

	public int getUserTotal() {
		// TODO Auto-generated method stub
		return adminDAO.getUserTotal();
	}
	@Transactional
	public void delUser(String ids) {
		// TODO Auto-generated method stub
		adminDAO.delUser(ids);
	}

	
	// 热词列表
	public List<String[]> searchHotWord(int page, int rows) {
		// TODO Auto-generated method stub
		return adminDAO.searchHotWord(page, rows);
	}

	// 热词统计量
	public int getHotWordTotal() {
		// TODO Auto-generated method stub
		return adminDAO.getHotWordTotal();
	}
	/*
	public void updateNewWord(String userName) {
		// TODO Auto-generated method stub
		adminDAO.updateNewWord(userName);
	}*/

	// 词汇列表
	public List<Word> getWordList(int page, int rows) {
		// TODO Auto-generated method stub
		return adminDAO.getWordList(page,rows);
	}
	// 词汇统计量
	public int getWordTotal() {
		// TODO Auto-generated method stub
		return adminDAO.getWordTotal();
	}
	// 添加词汇
	@Transactional
	public void add(Word w) {
		// TODO Auto-generated method stub
		adminDAO.add(w);
	}
	// 修改词汇
	@Transactional
	public void update(Word word) {
		// TODO Auto-generated method stub
		adminDAO.update(word);
	}
	// 删除词汇
	@Transactional
	public void wordDel(String ids) {
		// TODO Auto-generated method stub
		adminDAO.wordDel(ids);
	}

	// 添加字典
	@Transactional
	public void add(Dictionary d) {
		// TODO Auto-generated method stub
		adminDAO.add(d);
	}
	// 修改字典
	@Transactional
	public void update(Dictionary dic) {
		// TODO Auto-generated method stub
		adminDAO.update(dic);
	}
	// 删除字典
	@Transactional
	public void dicDel(String ids) {
		// TODO Auto-generated method stub
		adminDAO.dicDel(ids);
	}
	// 字典列表
	public List<Dictionary> getDicList(int page, int rows) {
		// TODO Auto-generated method stub
		return adminDAO.getDicList(page,rows);
	}
	// 字典统计量
	public int getDicTotal() {
		// TODO Auto-generated method stub
		return adminDAO.getDicTotal();
	}
}
