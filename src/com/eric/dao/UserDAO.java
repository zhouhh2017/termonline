package com.eric.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.eric.model.NewWord;
import com.eric.model.User;
@Component
public class UserDAO {
	private HibernateTemplate hibernateTemplate;
	
	//ע��ɹ������һ���û�
	public void add(User u){
		hibernateTemplate.save(u);
	}

	//����û��Ƿ����
	public boolean checkUserExist(User u) {
		List<User> users = (List<User>)hibernateTemplate.find("from t_user where username = '" + u.getUserName() + "'");
		if(users !=null && users.size()>0){
			return true;
		}
		return false;
	}
	
	//��֤�û���¼
	public User checkUserLogin(User user){
		String username = user.getUserName();
		String password = user.getPassword();
		List<User> users = (List<User>)hibernateTemplate.find("from t_user where username = '" + username 
				+ "' and password = '" + password + "'");
		if(users !=null && users.size()>0){
			return users.get(0);
		}
		return null;
	}
	
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void update(User user) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(user);
		
		/*PreparedStatement ps = null;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/termonline_bak");
			ps = conn.prepareStatement("update t_user set email=?, level=?, password=?, phone=?, registerDate=? ,userName=? where userName=?");
			ps.setString(1, user.getEmail());
			ps.setInt(2, user.getLevel());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getPhone());
			ps.setDate(5, (Date) user.getRegisterDate());
			ps.setString(6, user.getUserName());
			ps.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(ps!=null){
				ps.close();
				ps = null;
			}
			if(conn!=null){
				conn.close();
				conn = null;
			}
			
		}*/
		
	}

	public List<String> getItemChanged(String userName) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		return (List<String>)session.createSQLQuery("select name from t_newword where status = 1 and userName = " + "\""+userName+"\"").list();
	}

	public void confirmItemChange(String userName) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		session.createSQLQuery("update t_newword set status = 2 where status = 1 and userName = " + "\""+userName+"\"").executeUpdate();
	}

	//查询历史记录
	public List<NewWord> searchHistory(String userName) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
//		Calendar c = Calendar.getInstance();
//		c.setTime(new Date());
//        c.add(Calendar.MONTH, -1);
//        Date m = c.getTime();
		List<Object[]> tmp = (List<Object[]>)session.createSQLQuery("select * from t_newword where userName = " + "\""+userName+"\" " + 
					"and status<3 and DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(date) order by times desc").list();
		List<NewWord> history = new ArrayList<NewWord>();
		for(Object[] s:tmp){
			NewWord n = new NewWord();
			n.setId(Integer.valueOf(s[0].toString()));
			n.setName(s[1].toString());
			n.setUserName(s[2].toString());
			n.setStatus(Integer.valueOf(s[3].toString()));
			n.setTimes(Integer.valueOf(s[4].toString()));
			n.setDate((Date)(s[5]));
			history.add(n);
		}
		return history;
	}

	public void historyDel(String[] delNames, String userName) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		for(String s:delNames){
			session.createSQLQuery("update t_newword set status = 3 where userName = " 
					+ "\""+userName+"\" and name = \"" + s + "\"").executeUpdate();
		}
	}

	public boolean checkName(String name) {
		// TODO Auto-generated method stub
		List<User> users = (List<User>)hibernateTemplate.find("from t_user where username = '" + name + "'");
		if(users !=null && users.size()>0){
			return true;
		}
		return false;
	}
	
	
}
