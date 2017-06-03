package com.eric.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 自定义拦截器，实现登录之后返回原页面
 * @author Eric
 *
 */
public class JumpBeforeInterceptor extends MethodFilterInterceptor{
	private static final long serialVersionUID = -694872477634225391L;

	@Override
	public String doIntercept(ActionInvocation ai) throws Exception {
		System.out.println("拦截login方法成功！！");
		 HttpServletRequest request=ServletActionContext.getRequest();   
         
         HttpSession session=request.getSession();   
         //通过session 判断用户是否登录
         if(session!=null && session.getAttribute("UserName")!=null) {   
                 return ai.invoke();   
          }   
         //这里是关键点了 设置客户原来请求的url地址   
         setToGoingURL(request, session, ai); 
         return ai.invoke();   
	}
	
	 private void setToGoingURL(HttpServletRequest request,HttpSession session,ActionInvocation invocation)   
     {   
         //如果referer不为空 直接使用它。如果为空我们分别获得命名空间，action名,以及请求参数   
         //从新构造成一个URL保存在session中   
         String url=request.getHeader("referer");   
         System.out.println("待转向URL:"+request.getHeader("referer"));  
         String queryString = request.getQueryString();
         if(url!=null&&url.endsWith("itemSearch")&&!url.endsWith("register")){
        	 url += ("?" + queryString);
        	 session.setAttribute("GOTO_URL_KEY", url);
         }
     }

	

}
