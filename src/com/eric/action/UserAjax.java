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

import com.eric.dto.SearchInfo;
import com.eric.dto.UserInfo;
import com.eric.dto.UserListInfo;
import com.eric.model.NewWord;
import com.eric.model.User;
import com.eric.service.AdminService;
import com.eric.service.ItemService;
import com.eric.service.UserService;
import com.eric.utils.HighLightUtil;
import com.eric.utils.JudgeEnOrZh;
import com.eric.utils.SplitWord;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.ucpaas.restDemo.RestTest;


@Component("userAjax")
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/")
public class UserAjax extends ActionSupport implements ServletRequestAware,ServletResponseAware,ModelDriven<User>{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private AdminService adminService;
	private UserService userService;
	private ItemService itemService;
	private UserInfo userInfo = new UserInfo();
	private User user = new User();
	private JSONObject result;
	private JSONArray history;
	String telenum;
	String param;
	String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelenum() {
		return telenum;
	}


	public void setTelenum(String telenum) {
		this.telenum = telenum;
	}


	public String getParam() {
		return param;
	}


	public void setParam(String param) {
		this.param = param;
	}

	//查询术语标准
	private JSONArray selectTable_1;
	//页码
	private int page;
	//每页的记录数
	private int rows;
	private String ids;
	
	
	
	@Action(
			value="userAjax",
			results={
				@Result(name="addSuccess",type="json"),
				@Result(name="updateSuccess",type="json"),
				@Result(name="getUserList",type="json",params={"root","result"}),
				@Result(name="delSuccess",type="json"),
				@Result(name="history",type="json",params={"root","history"}),
				@Result(name="selectTable_1",type="json",params={"root","selectTable_1"}),
				@Result(name="getver",type="json",params={"root","result"}),
				@Result(name="usernamecheck",type="json",params={"root","result"})
				//@Result(name="Verification",type="json",params={"root","result"})
			}
		)
	
	//添加用户
	public String userAdd(){
		User u = User.newUser(user);
		userService.add(u);
		return "addSuccess";
	}
	
	
	//更新用户
	public String userUpdate(){
		userService.update(user);
		//adminService.updateNewWord(user.getUserName());
		return "updateSuccess";
	}
	
	
	//得到用户列表
	public String getUserList(){
		List<User> data = adminService.getUserList(page,rows);
		List<UserListInfo> userListInfos = new ArrayList<UserListInfo>();
		int total = adminService.getUserTotal();
		for(User tmp:data){
			UserListInfo u = new UserListInfo();
			u.setId(String.valueOf(tmp.getId()));
			u.setUserName(tmp.getUserName().toString());
			u.setPassword(tmp.getPassword().toString());
			u.setEmail(tmp.getEmail().toString());
			u.setPhone(tmp.getPhone().toString());
			u.setRegisterDate(tmp.getRegisterDate().toString());
			u.setLevel(String.valueOf(tmp.getLevel()));
			userListInfos.add(u);
		}
		String t = "{\"total\":"+total+" , \"rows\":"+JSONArray.fromObject(userListInfos).toString()+"}";
		System.out.println(t);
		this.result = JSONObject.fromObject(t);
		return "getUserList";
	}
	
	
	public String sendMsg()
	{
		
		boolean json= true;
		String accountSid="f8f251e17a10d061ff0e929dd250c143";
		String authToken="21820bdc368e88566d59723d9452d901";
		String appId="d2df6b2416e642cb8d89adceb929c9dc";
		String templateId="58287";
		try {
			RestTest.testTemplateSMS(json, accountSid, authToken, appId, templateId, telenum, param);
			//this.result=JSONObject.fromObject("{'key':" + rr +"}");
			//System.out.print(result);
			
			//request.getSession().setAttribute("key",result);
//			if (rr.equals(""))
//				this.result=JSONObject.fromObject("{'receive':'0'}");
//			else
				this.result=JSONObject.fromObject("{'receive':'1'}");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			this.result=JSONObject.fromObject("{'receive':'0'}");
		}
		return "getver";
	}
//	public String getMsg()
//	{
//		result=JSONObject.fromObject(request.getSession().getAttribute("key"));
//		return "Verification";
//	}
	//删除用户
	public String delUser(){
		System.out.println(ids);
		adminService.delUser(ids);
		return "delSuccess";
	}
	
	
	//用户确认搜索词已添加入库
	public String confirmItemChange(){
		String userName = (String)request.getSession().getAttribute("userName");
		userService.confirmItemChange(userName);
		request.getSession().setAttribute("itemChange", null);
		return "confirmItemChange";
	}

	//历史搜索记录查询
	public String historySearch(){
		String userName = (String) request.getSession().getAttribute("userName");
		if(userName!=null){
			List<NewWord> historyResult = userService.searchHistory(userName);
			this.history = JSONArray.fromObject(historyResult);
			
		}
		return "history";
	}
	
	//删除历史记录
	public String historyDel(){
		String[] delNames = request.getParameterValues("del");
		String userName = (String) request.getSession().getAttribute("userName");
//		for (String string : delNames) {
//			System.out.println(string);
//		}
		userService.historyDel(delNames,userName);
		return null;
	}
	
	//查询术语和词典
	public String searchItem_Dic() throws IOException{
		String searchItem = (String)request.getParameter("searchItem");
		System.out.println(searchItem);
		String queryMode = (String)request.getParameter("queryMode");
		String mode = (String)request.getParameter("mode");
		long start = 0;
		long end = 0;
		float time = 0;
		float timeLike =0;
		List<SearchInfo> queryResult = new ArrayList<SearchInfo>();
		List<SearchInfo> queryResultLike = new ArrayList<SearchInfo>();
		if("jingque".equals(queryMode)){
			start = System.currentTimeMillis();
			queryResult = HighLightUtil.highLightProcess(
					itemService.searchItemFromItem_Dic(searchItem), searchItem,
					true);
			end = System.currentTimeMillis();
			time = (float) ((end - start) * 1.0 / 1000);
			if(queryResult.size()==0){
				// 如果是中文，并且未找到结果，则进行分词
				if (!JudgeEnOrZh.isEnglish(searchItem)) {
					List<String> split = SplitWord.split(searchItem);
					start = System.currentTimeMillis();
					queryResult = HighLightUtil.highLightProcess(
							itemService.searchItemFromItem_Dic_Split(split),
							split, true);
					end = System.currentTimeMillis();
					time = (float) ((end - start) * 1.0 / 1000);
				}
			}
			this.selectTable_1 = JSONArray.fromObject(queryResult);
			return "selectTable_1";
		}else{
			start = System.currentTimeMillis();
			queryResultLike = HighLightUtil.highLightProcess(
					itemService.searchItemFromItem_Dic_Like(searchItem),
					searchItem, true);
			end = System.currentTimeMillis();
			timeLike = (float) ((end - start) * 1.0 / 1000);
			if(queryResultLike.size()==0){
				// 如果是中文，并且未找到结果，则进行分词
				if (!JudgeEnOrZh.isEnglish(searchItem)) {
					List<String> split = SplitWord.split(searchItem);
					start = System.currentTimeMillis();
					queryResultLike = HighLightUtil.highLightProcess(
							itemService.searchItemFromItem_Dic_Split_Like(split),
							split, true);
					end = System.currentTimeMillis();
					time = (float) ((end - start) * 1.0 / 1000);
				}
			}
			this.selectTable_1 = JSONArray.fromObject(queryResultLike);
			return "selectTable_1";
		}
		
	}
	
	
	public String Usernamecheck(){
		
		if(name.equals("")){
			this.result = JSONObject.fromObject("{\"name\":\"0\"}");
		}else{ 
			if(userService.checkName(name)){
				this.result = JSONObject.fromObject("{\"name\":\"1\"}");
			}else{
				this.result = JSONObject.fromObject("{\"name\":\"2\"}");
			}
		}
		return "usernamecheck";
	}
	
	
	
	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}


	public JSONArray getHistory() {
		return history;
	}


	public void setHistory(JSONArray history) {
		this.history = history;
	}


	public JSONArray getSelectTable_1() {
		return selectTable_1;
	}


	public void setSelectTable_1(JSONArray selectTable_1) {
		this.selectTable_1 = selectTable_1;
	}


	public ItemService getItemService() {
		return itemService;
	}

	@Resource
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}






}
