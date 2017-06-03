package com.eric.action;

/**
 * 热词后台管理Action
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

import com.eric.model.HotWord;
import com.eric.service.AdminService;
import com.eric.service.ItemService;
import com.eric.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
@Component("hotWordAjax")
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/")
public class HotWordAjax extends ActionSupport implements ServletRequestAware,ServletResponseAware,ModelDriven<HotWord>{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private AdminService adminService;
	private UserService userService;
	private ItemService itemService;
	private HotWord hotWord = new HotWord();
	private JSONObject result;
	//第几页
	private int page;
	//每页多少条记录
	private int rows;
	private String ids;
	
	@Action(
			value="hotWordAjax",
			results={
				@Result(name="addSuccess",type="json"),
				@Result(name="hotwordList",type="json",params={"root","result"})
			}
		)
	
	// 添加热词
	public String hotWordAdd() {
		HotWord h = HotWord.newHotWord(hotWord);
		itemService.add(h);
		return "addSuccess";
	}
	
	// 修改热词
	public String hotWordUpdate() {
		itemService.update(hotWord);
		return "addSuccess";
	}

	// 删除热词
	public String delHotWord() {
		itemService.delHotWord(ids);
		return "delSuccess";
	}
	
	// 热词分页
	public String hotwordList() throws IOException {
		List<String[]> data = adminService.searchHotWord(page, rows);
		List<HotWord> hotwordInfos = new ArrayList<HotWord>();
		int total = adminService.getHotWordTotal();
		for (Object[] tmp : data) {
			HotWord n = new HotWord();
			n.setId(Integer.parseInt(tmp[0].toString()));
			n.setName((String) tmp[1]);
			n.setTimes(Integer.parseInt(tmp[2].toString()));
			hotwordInfos.add(n);
		}
		String t = "{\"total\":" + total + " , \"rows\":"
				+ JSONArray.fromObject(hotwordInfos).toString() + "}";
		this.result = JSONObject.fromObject(t);
		return "hotwordList";
	}
	
	
	
	
	
	
	
	
	@Override
	public HotWord getModel() {
		// TODO Auto-generated method stub
		return hotWord;
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
