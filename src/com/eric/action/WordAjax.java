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

import com.eric.model.HotWord;
import com.eric.model.Word;
import com.eric.service.AdminService;
import com.eric.service.ItemService;
import com.eric.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
@Component("wordAjax")
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/")
public class WordAjax extends ActionSupport implements ServletRequestAware,ServletResponseAware,ModelDriven<Word>{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private AdminService adminService;
	private UserService userService;
	private ItemService itemService;
	private Word word = new Word();
	private JSONObject result;
	//第几页
	private int page;
	//每页多少条记录
	private int rows;
	private String ids;
	
	@Action(
			value="wordAjax",
			results={
				@Result(name="addSuccess",type="json"),
				@Result(name="updateSuccess",type="json"),
				@Result(name="delSuccess",type="json"),
				@Result(name="getWordList",type="json",params={"root","result"})
			}
		)
	
	// 添加热词
	public String wordAdd() {
		Word w = Word.newWord(word);
		adminService.add(w);
		return "addSuccess";
	}
	
	// 修改热词
	public String wordUpdate() {
		adminService.update(word);
		return "updateSuccess";
	}

	// 删除词汇
	public String wordDel() {
		adminService.wordDel(ids);
		return "delSuccess";
	}
	
	// 词汇分页
	public String getWordList() throws IOException {
		List<Word> data = adminService.getWordList(page, rows);
		List<Word> wordInfos = new ArrayList<Word>();
		int total = adminService.getWordTotal();
		for (Word tmp : data) {
			Word w = new Word();
			w.setId(tmp.getId());
			w.setChineseName(tmp.getChineseName());
			w.setEnglishName(tmp.getEnglishName());
			w.setEnglishAbbr(tmp.getEnglishAbbr());
			w.setStandard(tmp.getStandard());
			wordInfos.add(w);
		}
		String t = "{\"total\":" + total + " , \"rows\":"
				+ JSONArray.fromObject(wordInfos).toString() + "}";
		this.result = JSONObject.fromObject(t);
		return "getWordList";
	}
	
	
	
	
	
	
	
	
	@Override
	public Word getModel() {
		// TODO Auto-generated method stub
		return word;
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
