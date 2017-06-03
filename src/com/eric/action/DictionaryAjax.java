package com.eric.action;
/**
 * 词汇后台管理Action
 * 
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.eric.model.Dictionary;
import com.eric.model.HotWord;
import com.eric.model.Word;
import com.eric.service.AdminService;
import com.eric.service.ItemService;
import com.eric.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
@Component("dictionaryAjax")
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/")
public class DictionaryAjax extends ActionSupport implements ServletRequestAware,ServletResponseAware,ModelDriven<Dictionary>{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private AdminService adminService;
	private UserService userService;
	private ItemService itemService;
	private Dictionary dic = new Dictionary();
	private JSONObject result;
	//第几页
	private int page;
	//每页多少条记录
	private int rows;
	private String ids;
	
	@Action(
			value="dictionaryAjax",
			results={
				@Result(name="addSuccess",type="json"),
				@Result(name="updateSuccess",type="json"),
				@Result(name="delSuccess",type="json"),
				@Result(name="getDicList",type="json",params={"root","result"})
			}
		)
	
	// 添加字典
	public String dicAdd() {
		Dictionary d = Dictionary.newDictionary(dic);
		adminService.add(d);
		return "addSuccess";
	}
	
	// 修改字典
	public String dicUpdate() {
		adminService.update(dic);
		return "updateSuccess";
	}

	// 删除字典
	public String dicDel() {
		adminService.dicDel(ids);
		return "delSuccess";
	}
	
	// 字典分页
	public String getDicList() throws IOException {
		List<Dictionary> data = adminService.getDicList(page, rows);
		List<Dictionary> dicInfos = new ArrayList<Dictionary>();
		int total = adminService.getDicTotal();
		for (Dictionary tmp : data) {
			Dictionary d = new Dictionary();
			d.setId(tmp.getId());
			d.setChineseName(tmp.getChineseName());
			d.setEnglishName(tmp.getEnglishName());
			d.setAuthor(tmp.getAuthor());
			d.setDefinitionContent(tmp.getDefinitionContent());
			d.setStandard(tmp.getStandard());
			dicInfos.add(d);
		}
		String t = "{\"total\":" + total + " , \"rows\":"
				+ JSONArray.fromObject(dicInfos).toString() + "}";
		this.result = JSONObject.fromObject(t);
		return "getDicList";
	}
	
	
	
	
	
	
	
	
	@Override
	public Dictionary getModel() {
		// TODO Auto-generated method stub
		return dic;
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

	public AdminService getAdminService() {
		return adminService;
	}
	@Resource
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public UserService getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ItemService getItemService() {
		return itemService;
	}
	@Resource
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
