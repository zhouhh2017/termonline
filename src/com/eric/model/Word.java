package com.eric.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 字典表
 * 
 * @author root
 * 
 */
@Entity(name = "t_word")
public class Word {
	@Id
	private int id;
	// 中文名
	private String chineseName;
	// 英文名
	private String englishName;
	// 缩写
	private String englishAbbr;
	// 出处
	private String standard;
	// 日期
	private String pubdate;
	
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
	public static Word newWord(Word word) {
		Word d = new Word();
		d.setChineseName(word.getChineseName());
		d.setEnglishName(word.getEnglishName());
		d.setEnglishAbbr(word.getEnglishAbbr());
		d.setStandard(word.getStandard());
		d.setPubdate(word.getPubdate());
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