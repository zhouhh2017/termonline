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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.eric.dto.UserListInfo;
import com.eric.model.HotWord;
import com.eric.model.Item;
import com.eric.model.User;
import com.eric.service.AdminService;
import com.eric.service.ItemService;
import com.eric.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
@Component("itemAjax")
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/")
public class ItemAjax extends ActionSupport implements ServletRequestAware,ServletResponseAware,ModelDriven<Item>{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private AdminService adminService;
	private UserService userService;
	private ItemService itemService;
	private Item item = new Item();
	private JSONObject result;
	//第几页
	private int page;
	//每页多少条记录
	private int rows;
	private String ids;
	
	@Action(
			value="itemAjax",
			results={
				@Result(name="addSuccess",type="json"),
				@Result(name="updateSuccess",type="json"),
				@Result(name="delSuccess",type="json"),
				@Result(name="getItemList",type="json",params={"root","result"})
			}
		)
	
	// 添加术语
	public String itemAdd() {
		Item i = Item.newItem(item);
		itemService.add(i);
		return "addSuccess";
	}
	
	// 修改术语
	public String itemUpdate() {
		itemService.update(item);
		return "updateSuccess";
	}

	// 删除术语
	public String itemDel() {
		itemService.del(ids);
		return "delSuccess";
	}
	
	// 术语分页
	public String getItemList() throws IOException {
		List<Item> data = itemService.getItemList(page, rows);
		List<Item> itemInfos = new ArrayList<Item>();
		int total = itemService.getItemTotal();
		for(Item tmp:data){
			Item i = new Item();
			i.setId(tmp.getId());
			i.setChineseName(tmp.getChineseName());
			i.setEnglishName(tmp.getEnglishName());
			i.setEnglishAbbr(tmp.getEnglishAbbr());
			i.setDefinitionContent(tmp.getDefinitionContent());
			i.setBookId(tmp.getBookId());
			i.setStandard(tmp.getStandard());
			i.setDefinitionNum(tmp.getDefinitionNum());
			i.setEntryTime(tmp.getEntryTime());
			i.setAuthor(tmp.getAuthor());
			i.setState(tmp.getState());
			itemInfos.add(i);
		}
		String t = "{\"total\":"+total+" , \"rows\":"+JSONArray.fromObject(itemInfos).toString()+"}";
		System.out.println(t);
		this.result = JSONObject.fromObject(t);
		return "getItemList";
	}
	
	
	
	
	
	
	
	
	@Override
	public Item getModel() {
		// TODO Auto-generated method stub
		return item;
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
