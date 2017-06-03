package com.eric.action;

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
//import org.aspectj.apache.bcel.classfile.Utility.ResultHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.eric.dto.NewWordInfo;
import com.eric.dto.UserListInfo;
import com.eric.model.HotWord;
import com.eric.model.Item;
import com.eric.model.User;
import com.eric.service.AdminService;
import com.eric.service.ItemService;
import com.eric.service.UserService;
import com.eric.utils.HighLightUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


@Component("searchAjax")
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/")
public class SearchAjax extends ActionSupport implements ServletRequestAware,ServletResponseAware,ModelDriven<Item>{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private AdminService adminService;
	private UserService userService;
	private ItemService itemService;
	private Item item = new Item();
	
	//第几页
	private int page;
	//每页多少条记录
	private int rows;
	private JSONObject result;
	//插入成功
	private JSONObject insertSuccess;
	//
	private String ids;
	//
	private String names;
	//
	private String itemName;
	
	@Action(
			value="searchAjax",
			results={
				@Result(name="getList",type="json",params={"root","result"}),
				@Result(name="delSuccess",type="json"),
				@Result(name="insertSuccess",type="json",params={"root","insertSuccess"}),
				@Result(name="getUserList",type="json",params={"root","result"}),
				@Result(name="hotwordList",type="json",params={"root","result"})
			}
		)
	
	
	//新词分页
	public String getList() throws IOException{
		List<String[]> data = adminService.searchNewWord(page,rows);
		List<NewWordInfo> newWordInfos = new ArrayList<NewWordInfo>();
		int total = adminService.getNewWordTotal();
		for(Object[] tmp:data){
			NewWordInfo n = new NewWordInfo();
			n.setName((String)tmp[0]);
			n.setTimes(Integer.parseInt(tmp[1].toString()));
			newWordInfos.add(n);
		}
		String t = "{\"total\":"+total+" , \"rows\":"+JSONArray.fromObject(newWordInfos).toString()+"}";
		this.result = JSONObject.fromObject(t);
		return "getList";
	}

	
	
	
	//删除术语
	public String delNewItem(){
		adminService.delNewItem(ids);
		return "delSuccess";
	}
	
	
	
	//插入一条术语
	public String insertOneItem(){
		Item tmp = Item.newItem(item);
		adminService.insertOneItem(tmp);
		adminService.changeNewWordStatus(itemName);
		insertSuccess = JSONObject.fromObject("{\"status\":\"成功\",\"message\":\"操作成功\"}");
		return "insertSuccess";
	}



	
	
	
	
	
	
	public JSONObject getResult() {
		return result;
	}


	public void setResult(JSONObject result) {
		this.result = result;
	}


	public AdminService getAdminService() {
		return adminService;
	}

	@Resource
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
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


	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}

	@Override
	public Item getModel() {
		// TODO Auto-generated method stub
		return item;
	}

	public JSONObject getInsertSuccess() {
		return insertSuccess;
	}

	public void setInsertSuccess(JSONObject insertSuccess) {
		this.insertSuccess = insertSuccess;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
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







}
