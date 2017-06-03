package com.eric.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.eric.model.Admin;
import com.eric.model.Dictionary;
import com.eric.model.Item;
import com.eric.model.User;
import com.eric.model.Word;

@Component
public class AdminDAO {
	private HibernateTemplate hibernateTemplate;
	
	

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	//��֤��¼
	public boolean loginValid(Admin admin) {
		// TODO Auto-generated method stub
		String adminName = admin.getAdminname();
		String password = admin.getPassword();
		List<Admin> result = (List<Admin>)(hibernateTemplate
				.find("from t_admin where adminname = ? and password = ?", new String[]{adminName,password}));
		if(result.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	//�´ʲ�ѯ
	public List<String[]> searchNewWord() {
			// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		return (List<String[]>)session.createSQLQuery("select name,sum(times) as times from t_newword where status =0 group by name").list();
		
	}
	public List<String[]> searchNewWord(int page, int rows) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		return (List<String[]>)session.createSQLQuery(
				"select name,sum(times) as times from t_newword where status =0 group by name order by times desc limit " + rows*(page-1) + "," + rows).list();
	}
	public int getNewWordTotal() {
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		return session.createSQLQuery(
				"select name,sum(times) as times from t_newword where status =0 group by name order by times desc").list().size();
	}
	public void delNewItem(String ids) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		String[] tmp = ids.split(",");
		for (int i = 0; i < tmp.length; i++) {
			session.createSQLQuery(
					"update t_newword set status = 1 where name = " + "\"" + tmp[i] + "\"").executeUpdate();
		}
		
		
		
	}
	public void insertOneItem(Item item) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(item);
	}
	public void changeNewWordStatus(String itemName) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		session.createSQLQuery(
					"update t_newword set status = 1 where name = " + "\"" + itemName + "\"").executeUpdate();
		
	}
	
	//�õ��û��б�
	public List<User> getUserList(int page, int rows) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		return session.createQuery("from t_user").setFirstResult((page-1)*rows).setMaxResults(rows).list();
	}
	public int getUserTotal() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from t_user").size();
	}
	public void delUser(String ids) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		String[] tmp = ids.split(",");
		for (int i = 0; i < tmp.length; i++) {
			session.createSQLQuery(
					"delete from t_user where id = " + "\"" + tmp[i] + "\"").executeUpdate();
		}
	}
	/*public void updateNewWord(String userName) {
		// TODO Auto-generated method stub
		String name = userName;
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		session.createSQLQuery(
					"update t_newword set userName = " + name +" where userName = " + "\"" + name + "\"").executeUpdate();
	}*/
	public List<String[]> searchHotWord(int page, int rows) {
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		return (List<String[]>)session.createSQLQuery(
				"select id,name,times from t_hotword order by times desc limit " + rows*(page-1) + "," + rows).list();
	}
	public int getHotWordTotal() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from t_hotword").size();
	}
	
	// 词汇列表
	public List<Word> getWordList(int page, int rows) {
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		return session.createQuery("from t_word").setFirstResult((page-1)*rows).setMaxResults(rows).list();
	}
	// 词汇统计量
	public int getWordTotal() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from t_word").size();
	}
	// 添加词汇
	public void add(Word w) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(w);
	}
	// 修改词汇
	public void update(Word word) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(word);
	}
	// 删除词汇
	public void wordDel(String ids) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		String[] tmp = ids.split(",");
		for (int i = 0; i < tmp.length; i++) {
			session.createSQLQuery(
					"delete from t_word where id = " + "\"" + tmp[i] + "\"").executeUpdate();
		}
	}
	// 添加字典
	public void add(Dictionary d) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(d);
	}
	// 修改字典
	public void update(Dictionary dic) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(dic);
	}
	// 删除字典
	public void dicDel(String ids) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		String[] tmp = ids.split(",");
		for (int i = 0; i < tmp.length; i++) {
			session.createSQLQuery(
					"delete from t_dictionary where id = " + "\"" + tmp[i] + "\"")
					.executeUpdate();
		}
	}
	// 字典列表
	public List<Dictionary> getDicList(int page, int rows) {
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		return session.createQuery("from t_dictionary").setFirstResult((page-1)*rows).setMaxResults(rows).list();
	}
	// 字典统计量
	public int getDicTotal() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from t_dictionary").size();
	}
	
}
