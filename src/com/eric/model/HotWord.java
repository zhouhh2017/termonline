package com.eric.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * �ȴʱ�ʵ����
 * @author Eric
 *
 */
@Entity(name="t_hotword")
public class HotWord {
	@Id
	@GeneratedValue
	private int id;
	//�ȴ���
	private String name;
	//���ȴ���������
	private int times;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	
	public static HotWord newHotWord(HotWord hotWord){
		HotWord h = new HotWord();
		h.setName(hotWord.getName());
		h.setTimes(hotWord.getTimes());
		return h;
	}
}
