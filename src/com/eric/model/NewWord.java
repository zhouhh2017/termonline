package com.eric.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * �´ʱ�ʵ����
 * @author Eric
 *
 */
@Entity(name="t_newword")
//@IdClass(NewWordPk.class)
public class NewWord {
	@Id
	@GeneratedValue
	private int id;
	//�´���
	private String name;
	//�����ôʵ��û�
	private String userName;
	//��������
	private int times;
	//���״̬
	private int status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	
	public static NewWord newNewWord(NewWord n){
		NewWord tmp = new NewWord();
		tmp.setName(n.getName());
		tmp.setUserName(n.getUserName());
		tmp.setTimes(n.getTimes());
		tmp.setStatus(n.getStatus());
		tmp.setDate(n.getDate());
		return tmp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
