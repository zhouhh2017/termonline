package com.eric.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Highlighter.Highlight;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.eric.model.Item;
import com.eric.service.AdminService;
import com.eric.service.ItemService;
import com.eric.service.UserService;
import com.eric.utils.HighLightUtil;
import com.eric.utils.JudgeEnOrZh;
import com.opensymphony.xwork2.ActionSupport;


@Component("searchHintAjax")
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/")
public class SearchHintAjax extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private AdminService adminService;
	private UserService userService;
	private ItemService itemService;
	
	
	//自动提示搜索结果
	private JSONArray hint;
	//自动搜索的关键词
	private String searchText;
	
	@Action(
			value="searchHintAjax",
			results={
				@Result(name="searchHint",type="json",params={"root","hint"})
			}
		)
	
	
	

	//返回联想搜索结果
	public String searchHint(){
		List<Item> queryResultLike = HighLightUtil.highLightProcess(itemService.searchItemByItemNameLikeLimit10(searchText), searchText);
		StringBuffer buffer = new StringBuffer("[");
		String resultHint = "";
		boolean flag = JudgeEnOrZh.isEnglish(searchText);
		Item tmp;
		if(queryResultLike!=null){
			if(queryResultLike.size()<10){
				if(flag){
					for(Item i:queryResultLike){
						buffer.append("\"" + i.getEnglishName() + "\t" + i.getChineseName() + "\",");
					}
					resultHint = buffer.substring(0, buffer.length()-1);
					resultHint += "]";
				}else{
					for(Item i:queryResultLike){
						buffer.append("\"" + i.getChineseName() + "\t" + i.getEnglishName() + "\",");
					}
					resultHint = buffer.substring(0, buffer.length()-1);
					resultHint += "]";
				}
				
			}else{
				if(flag){
					for(int i=0;i<10;i++){
						tmp = queryResultLike.get(i);
						buffer.append("\"" + tmp.getEnglishName() + "\t" + tmp.getChineseName() + "\",");
					}
					resultHint = buffer.substring(0, buffer.length()-1);
					resultHint += "]";
				}else{
					for(int i=0;i<10;i++){
						tmp = queryResultLike.get(i);
						buffer.append("\"" + tmp.getChineseName() + "\t" + tmp.getEnglishName() + "\",");
					}
					resultHint = buffer.substring(0, buffer.length()-1);
					resultHint += "]";
				}
				
			}
		}else{
			buffer.append("]");
		}
		System.out.println(resultHint);
		this.hint = JSONArray.fromObject(resultHint);
		return "searchHint";
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










	

	public UserService getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}



	public JSONArray getHint() {
		return hint;
	}



	public void setHint(JSONArray hint) {
		this.hint = hint;
	}



	public String getSearchText() {
		return searchText;
	}



	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}



	public ItemService getItemService() {
		return itemService;
	}


	@Resource
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}







}
