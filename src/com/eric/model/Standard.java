package com.eric.model;

import java.util.Date;

import javax.ejb.Timeout;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 标准信息
 * @author root
 *
 */

@Entity(name="t_standard")
public class Standard {
	@Id
	@GeneratedValue
	private int id;
	private String chineseName;
	private String englishName;
	private String stdState;
	private String stdType;
	private String stdNO;
	private String ICS;
	private String CCS;
	@Temporal(TemporalType.DATE)
	private Date dateIssued;
	private Date dateImplement;
	private String issuedDep;
	private String propose;
	private String belongDep;
	private String publishDep;
	private String draftPerson;
	private String scope;
	private String keyword;
	private String fileref;
	private String bookId;
	/**
	 * @param item
	 * @return
	 */
	public static Standard newStandard(Standard item) {
		Standard s = new Standard();
		s.setChineseName(item.getChineseName());
		s.setEnglishName(item.getEnglishName());
		s.setStdState(item.getStdState());
		s.setStdType(item.getStdType());
		s.setStdNO(item.getStdNO());
		s.setICS(item.getICS());
		s.setCCS(item.getCCS());
		s.setDateIssued(item.getDateIssued());
		s.setDateImplement(item.getDateImplement());
		s.setIssuedDep(item.getIssuedDep());
		s.setPropose(item.getPropose());
		s.setBelongDep(item.getBelongDep());
		s.setPublishDep(item.getPublishDep());
		s.setDraftPerson(item.getDraftPerson());
		s.setScope(item.getScope());
		s.setKeyword(item.getKeyword());
		s.setFileref(item.getFileref());
		s.setBookId(item.getBookId());
		return s;
	}

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

	

	public String getIssuedDep() {
		return issuedDep;
	}

	public void setIssuedDep(String issuedDep) {
		this.issuedDep = issuedDep;
	}

	public String getBelongDep() {
		return belongDep;
	}

	public void setBelongDep(String belongDep) {
		this.belongDep = belongDep;
	}

	public String getPublishDep() {
		return publishDep;
	}

	public void setPublishDep(String publishDep) {
		this.publishDep = publishDep;
	}

	public String getDraftPerson() {
		return draftPerson;
	}

	public void setDraftPerson(String draftPerson) {
		this.draftPerson = draftPerson;
	}

	public String getStdState() {
		return stdState;
	}

	public void setStdState(String stdState) {
		this.stdState = stdState;
	}

	public String getPropose() {
		return propose;
	}

	public void setPropose(String propose) {
		this.propose = propose;
	}

	

	public String getFileref() {
		return fileref;
	}

	public void setFileref(String fileref) {
		this.fileref = fileref;
	}

	public String getStdNO() {
		return stdNO;
	}

	public void setStdNO(String stdNO) {
		this.stdNO = stdNO;
	}

	public String getStdType() {
		return stdType;
	}

	public void setStdType(String stdType) {
		this.stdType = stdType;
	}

	public String getICS() {
		return ICS;
	}

	public void setICS(String iCS) {
		ICS = iCS;
	}

	public String getCCS() {
		return CCS;
	}

	public void setCCS(String cCS) {
		CCS = cCS;
	}

	public Date getDateIssued() {
		return dateIssued;
	}

	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}

	public Date getDateImplement() {
		return dateImplement;
	}

	public void setDateImplement(Date dateImplement) {
		this.dateImplement = dateImplement;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
}
