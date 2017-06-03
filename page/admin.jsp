<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>管理员后台</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-easyui-1.5.1/jquery.min.js"></script>
	<link rel="stylesheet" href="js/jquery-easyui-1.5.1/themes/default/easyui.css">
	<link rel="stylesheet" href="js/jquery-easyui-1.5.1/themes/icon.css">
	<script type="text/javascript" src="js/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>
	<script type="text/javascript"src="js/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		$(function(){
			 $('a[title]').click(function(){
				var src = $(this).attr('title');
				var title = $(this).html();
				if($('#main').tabs('exists',title)){
					$('#main').tabs('select',title);
				}else{
					$('#main').tabs('add',{
							title:title,   
							content:'<iframe frameborder=0 style=width:100%;height:100% src='+ src +' ></iframe>',   
							closable:true
					});
				}
			}); 
		});
			
	</script>
  </head>
 
  <body >
	<div id="cc" class="easyui-layout" style="width:100%;height:100%;">
		<div region="north"  style="width:100%;height:50px;">
				<label style="text-align: center"><h3>气象术语在线后台管理</h3></label>
			
		</div>
	    <div region="west"  iconCls="icon-ok" split="true" title="菜单" style="width:200px;" >
	    	<div id="tool" class="easyui-accordion" style="padding: 0px;margin: 0px">  
				    <div title="术语管理" selected="true">  
				    	<a href="javascript:void(0)" title="jsp/additem.jsp">新词管理</a> <br/>
				    	<a href="javascript:void(0)" title="jsp/hotword.jsp">热词管理</a> <br/>
				    	<a href="javascript:void(0)" title="jsp/item.jsp">术语管理</a> <br/>
				    	<a href="javascript:void(0)" title="jsp/word.jsp">词汇管理</a> <br/>
				    	<a href="javascript:void(0)" title="jsp/dictionary.jsp">字典管理</a> <br/>
				    	<a href="javascript:void(0)" title="jsp/loadxml.jsp">导入XML</a> <br/>
				    </div>  
				    <div title="用户管理">  
				    	<a href="javascript:void(0)" title="jsp/usercheck.jsp">用户审核</a> <br/>
				    </div>  
			</div>  
	    </div>
	    <div region="center"  iconCls="icon-ok" title="主界面">
	    	<div id="main"  class="easyui-tabs" fit=true style="width:500px;height:250px;">  

			</div> 
	    </div>
	</div>	
  </body>
</html>
