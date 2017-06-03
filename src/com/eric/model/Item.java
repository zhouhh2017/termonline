package com.eric.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

/**
 * �����ʵ����
 * @author Eric
 *
 */
@Entity(name="t_item")
public class Item {
	@Id
	@GeneratedValue
	private int id;
	//中文名
	private String chineseName;
	//英文名
	private String englishName;
	//英文缩写
	private String englishAbbr;
	//定义
	private String definitionContent;
	//标准
	private String standard;
	//定义号
	private String definitionNum;
	//文件号
	private String bookId;
	//录入时间
	//@Temporal(TemporalType.TIMESTAMP)
	private String entryTime;
	//作者
	private String author;
	//应用状态
	private String state;
	
	
	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public static Item newItem(Item item){
		Item tmp = new Item();
		tmp.setChineseName(item.getChineseName());
		tmp.setEnglishName(item.getEnglishName());
		tmp.setEnglishAbbr(item.getEnglishAbbr());
		tmp.setDefinitionContent(item.getDefinitionContent());
		tmp.setStandard(item.getStandard());
		tmp.setDefinitionNum(item.getDefinitionNum());
		tmp.setBookId(item.getBookId());
		tmp.setEntryTime(item.getEntryTime());
		tmp.setAuthor(item.getAuthor());
		tmp.setState(item.getState());
		return tmp;
	}
	
	/*@Override
	public String toString(){
		System.out.println(standard);
		return "";
	}*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getChineseName() {
		return chineseName;
	}
	
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getEnglishAbbr() {
		return englishAbbr;
	}
	public void setEnglishAbbr(String englishAbbr) {
		this.englishAbbr = englishAbbr;
	}
	public String getDefinitionContent() {
		return definitionContent;
	}
	public void setDefinitionContent(String definitionContent) {
		this.definitionContent = definitionContent;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getDefinitionNum() {
		return definitionNum;
	}
	public void setDefinitionNum(String definitionNum) {
		this.definitionNum = definitionNum;
	}
	
	public String getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
}
