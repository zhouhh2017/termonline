package com.eric.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 词典表
 * 
 * @author root
 * 
 */
@Entity(name = "t_dictionary")
public class Dictionary {
	@Id
	private int id;
	// 中文名
	private String chineseName;
	// 英文名
	private String englishName;
	// 定义
	private String definitionContent;
	// 作者
	private String author;
	// 出处
	private String standard;
	// 图片链接
	private String pictureref;
	// 图片描述
	private String discription;
	// 日期
	private String pubdate;
	public int getId() {
		return id;
	}
	public String getPictureref() {
		return pictureref;
	}
	public void setPictureref(String pictureref) {
		this.pictureref = pictureref;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
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
	public String getDefinitionContent() {
		return definitionContent;
	}
	public void setDefinitionContent(String definitionContent) {
		this.definitionContent = definitionContent;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public static Dictionary newDictionary(Dictionary dictionary) {
		Dictionary d = new Dictionary();
		d.setChineseName(dictionary.getChineseName());
		d.setEnglishName(dictionary.getEnglishName());
		d.setAuthor(dictionary.getAuthor());
		d.setDefinitionContent(dictionary.getDefinitionContent());
		d.setStandard(dictionary.getStandard());
		d.setPubdate(dictionary.getPubdate());
		return d;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

}
