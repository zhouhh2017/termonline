package com.eric.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.eric.dto.UserInfo;
import com.eric.model.User;
import com.eric.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Component("userAction")
@Scope("prototype")
@ParentPackage("eric")
@Namespace("/user")
public class UserAction extends ActionSupport implements ModelDriven<UserInfo>,ServletRequestAware,ServletResponseAware{
	private UserInfo userInfo = new UserInfo();
	private User user = new User();
	private UserService userService;
	private HttpServletRequest request;
	private HttpServletResponse response;

	
	
	@Action(
			value="userAction",
			results={
				@Result(name="index",location="/index.jsp"),
				@Result(name="login",location="/WEB-INF/page/login.jsp"),
				@Result(name="register",location="/WEB-INF/page/register.jsp"),
				@Result(name="registerSuccess",location="/WEB-INF/page/registerSuccess.jsp"),
				@Result(name="registerFail",location="/WEB-INF/page/registerFail.jsp")
			},
			interceptorRefs = {@InterceptorRef(value = "jumpTo")}
		)
	
	//֤
	public String registerValid(){
		userInfo.setUserInfo(user);
		if(userInfo.checkUserNameIsEmpty()){
			request.setAttribute("userNameEmpty", "用户名不能为空");
			return "register";
		}else if(userInfo.checkPasswordIsEmpty()){
			request.setAttribute("passwordEmpty", "密码不能为空");
			return "register";
		}else if(!userInfo.checkPasswordEquals()){
			request.setAttribute("passwordNotSame", "密码不一致");
			return "register";
		}else if(userService.checkUserExist(user)){
			request.setAttribute("userExist", "用户已存在");
			return "register";
		}else{
			userService.add(user);
			return "registerSuccess";
		}
		
	}
	
	
	//�û���¼��֤
	public String loginValid() throws ServletException, IOException{
		userInfo.setUserInfo(user);
		User u = new User();
		if((u=userService.checkUserLogin(user))!=null){
			if(u.getLevel()==0){
				request.setAttribute("loginError", "用户未通过审核，请耐心等待");
				return "login";
			}
			String result = "";
			List<String> tmp = userService.getItemChanged(user.getUserName());
			if(tmp.size()>3){
				for(int i=0;i<3;i++){
					result += tmp.get(i) + ",";
				}
			}else{
				for(String t:tmp){
					result += t + ",";
				}
			}
			
			if(!result.equals("")){
				result = result.substring(0,result.length()-1);
				if(tmp.size()>3){
					result += " 等";
				}
				
			}
			
			request.getSession().setAttribute("itemChange", result);
			request.getSession().setAttribute("userName", user.getUserName());
			request.getSession().setAttribute("level", u.getLevel());
			String url = (String) request.getSession().getAttribute("GOTO_URL_KEY");  
	        if (url!=null&&url.contains("searchItem")) {  
	            response.sendRedirect(url); 
	            return null;  
	        }else{
	        	return "index";
	        }
		}else{
			request.setAttribute("loginError", "请输入正确的用户名和密码");
			return "login";
		}
	}
	
	//�û��˳�
	public String userQuit(){
		request.getSession().invalidate();
		return "index";
	}
	
	
	public String login() throws ServletException, IOException{
//		String Refer_URL = request.getHeader("referer") + (request.getQueryString()==null?"":"?" + request.getQueryString());
//		request.getSession().setAttribute("Refer_URL", Refer_URL);
//		System.out.println(Refer_URL);
		return "login";
	}
	//�û�ע��
	public String register(){
		return "register";
	}
	
	
	
	@Override
	public UserInfo getModel() {
		// TODO Auto-generated method stub
		return userInfo;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
	}

	
}
