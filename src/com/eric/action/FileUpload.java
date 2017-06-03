package com.eric.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.eric.utils.Demo01;
import com.eric.utils.Demo02;
import com.eric.utils.Demo03;
import com.eric.utils.Demo04;
import com.opensymphony.xwork2.ActionSupport;
@Component("fileUpload")
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class FileUpload extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	private HttpServletRequest request;
	private HttpServletResponse response;
	//文件上传
	// myFile属性用来封装上传的文件  
	private File file;  
	private String fileContentType;
	private String fileFileName;
	
	
	@Action(value = "fileUpload", results = {
			@Result(name = "success", location = "/jsp/loadxml.jsp")})
	
	
	//术语表上传
	public String item() throws Exception{
		try{
			Demo04.test(file);
			request.setAttribute("state", "导入完成");
			return SUCCESS;
		}catch(Exception e){
			request.setAttribute("state", "导入失败");
			return SUCCESS;
		}
	}
	//标准表上传
	public String standard() throws Exception{
		try{
			Demo02.test(file);
			request.setAttribute("state", "导入完成");
			return SUCCESS;
		}catch(Exception e){
			request.setAttribute("state", "导入失败");
			return SUCCESS;
		}
	}
	//冰冻圈辞典上传
	public String dic() throws Exception{
		try{
			//FileInputStream in = new FileInputStream(file);
			//BufferedReader dr = new BufferedReader(new FileReader(file));
			Demo01.test(file);
			request.setAttribute("state", "导入完成");
			return SUCCESS;
		}catch(Exception e){
			request.setAttribute("state", "导入失败");
			return SUCCESS;
		}
	}
	//英汉冰冻圈字典上传
	public String word(){
		try{
			Demo03.test(file);
			request.setAttribute("state", "导入完成");
			return SUCCESS;
		}catch(Exception e){
			request.setAttribute("state", "导入失败");
			return SUCCESS;
		}
	}
	
	
	
	
	
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}






	public File getMyFile() {
		return file;
	}






	public void setMyFile(File file) {
		this.file = file;
	}






	public File getFile() {
		return file;
	}






	public void setFile(File file) {
		this.file = file;
	}






	public String getFileContentType() {
		return fileContentType;
	}






	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}






	public String getFileFileName() {
		return fileFileName;
	}






	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

}
