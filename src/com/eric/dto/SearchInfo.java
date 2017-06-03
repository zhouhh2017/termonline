package com.eric.dto;

import java.util.Date;

import com.eric.model.Dictionary;
import com.eric.model.Item;
import com.eric.model.Word;

public class SearchInfo {
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
	private String entryTime;
	//作者
	private String author;
	//状态
	private String state;
	
	
	//封装查询结果
	public static SearchInfo newSearchInfo(Object o){
		SearchInfo s = new SearchInfo();
		if(o.getClass()==Item.class){
			s.setChineseName(((Item)o).getChineseName());
			s.setEnglishName(((Item)o).getEnglishName());
			s.setEnglishAbbr(((Item)o).getEnglishAbbr());
			s.setDefinitionContent(((Item)o).getDefinitionContent());
			s.setStandard(((Item)o).getStandard());
			s.setDefinitionNum(((Item)o).getDefinitionNum());
			s.setBookId(((Item)o).getBookId());
			s.setEntryTime(((Item)o).getEntryTime());
			s.setAuthor(((Item)o).getAuthor());
			s.setState(((Item)o).getState());
		}else if(o.getClass()==Dictionary.class){
			s.setChineseName(((Dictionary)o).getChineseName());
			s.setEnglishName(((Dictionary)o).getEnglishName());
			s.setAuthor(((Dictionary)o).getAuthor());
			s.setDefinitionContent(((Dictionary)o).getDefinitionContent());
			s.setStandard(((Dictionary)o).getStandard());
		}else if(o.getClass()==Word.class){
			s.setChineseName(((Word)o).getChineseName());
			s.setEnglishName(((Word)o).getEnglishName());
			//s.setAuthor(((Word)o).getAbbreviation());
			s.setStandard(((Word)o).getStandard());
		}
		return s;
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
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(String string) {
		this.entryTime = string;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
