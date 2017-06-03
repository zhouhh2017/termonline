package com.eric.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="t_vocabulary")
public class Vocabulary {
	@Id
	@GeneratedValue
	private int id;
	private String chineseName;
	private String englishName;
	private String englishAbbr;
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
	
}
