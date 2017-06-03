<%@ page language="java" import="java.util.*,com.eric.model.Standard" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- 历史记录的模态框 -->

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String searchItem = (String)request.getAttribute("searchItem");
	String userName = (String) session.getAttribute("userName");
	int level = 0;
	if(userName != null){
		level = Integer.valueOf(session.getAttribute("level").toString());
	}
	
	boolean isLogin = userName == null ? false : true;
	String queryMode = (String)request.getAttribute("queryMode");
	String mode = (String)request.getAttribute("mode");
	String jingque;
	String mohu;
	//System.out.println(queryMode);
	if("jingque".equals(queryMode)){
		jingque="btn btn-primary";
		mohu="btn btn-default";
	}else{
		mohu="btn btn-primary";
		jingque="btn btn-default";
	}
	
	String scope = ((Standard)request.getAttribute("stdResult")).getScope();
%>


<!-- 标准的详情页 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>标准详情页</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="js/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="js/bootstrap/css/bootstrap-table.css" rel="stylesheet">
<link href="js/bootstrap/css/bootstrap-treeview.css" rel="stylesheet">
<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/bootstrap/js/bootstrap.min.js"></script>
<script src="js/bootstrap/js/bootstrap-table.js"></script>
<script src="js/bootstrap/js/bootstrap-treeview.js"></script>
<script src="js/bootstrap/js/bootstrap-table-zh-CN.js"></script>
<!-- 历史记录查询 -->
<%@ include file="common/historyModal.jsp" %>
<script src="myjs/history.js"></script>
<style>
body{
    overflow: hidden;  /*此处需要设置溢出隐藏，否则文字起始位移超过页面大小就会在页面下方出现横的滚动条*/
}


/*定义一个名字为lefteaseinAnimate动画，实现从页面的左边淡入页面效果*/
@keyframes lefteaseinAnimate{
    0%{ transform: translateX(-2000px); opacity: 0;}   /*在0%时设置文字在想X轴-2000px位移处（左边），透明度为0，也就是看不见文字*/
    100%{ transform: translateX(0px); opacity: 1;}         /*在100%时设置文字在想X轴0px位移处，也就是原始布局的位置，透明度为1，也就是文字可以看见了*/
}
@-webkit-keyframes lefteaseinAnimate{
    0%{ -webkit-transform: translateX(-2000px); opacity: 0;}
    100%{ -webkit-transform: translateX(0px); opacity: 1;}    
}
@-o-keyframes lefteaseinAnimate{
    0%{ -webkit-transform: translateX(-2000px); opacity: 0;}
    100%{ -webkit-transform: translateX(0px); opacity: 1;}    
}
@-ms-keyframes lefteaseinAnimate{
    0%{ -webkit-transform: translateX(-2000px); opacity: 0;}
    100%{ -webkit-transform: translateX(0px); opacity: 1;}    
}
@-moz-keyframes lefteaseinAnimate{
    0%{ -webkit-transform: translateX(-2000px); opacity: 0;}
    100%{ -webkit-transform: translateX(0px); opacity: 1;}    
}
.cetitle{
    animation: lefteaseinAnimate 1s ease 1;    /*调用已定义好的动画lefteaseinAnimate，全程运行时间1S，进入的速度曲线为ease，只播放一次*/
    -webkit-animation: lefteaseinAnimate 1s ease 1;
    -ms-animation: lefteaseinAnimate 1s ease 1;
    -o-animation: lefteaseinAnimate 1s ease 1;
    -moz-animation: lefteaseinAnimate 1s ease 1;
    
    /*规定动画的最后状态为结束状态*/
    animation-fill-mode:forwards;
    -webkit-animation-fill-mode: forwards;  
      -o-animation-fill-mode: forwards; 
      -ms-animation-fill-mode: forwards;   
      -moz-animation-fill-mode: forwards; 
}

/*定义一个名字为righteaseinAnimate动画，实现从页面的右边淡入页面效果*/
@keyframes righteaseinAnimate{
    0%{ transform: translateX(2000px); opacity: 0;}   /*在0%时设置文字在想X轴2000px位移处（右边），透明度为0，也就是看不见文字*/
    100%{ transform: translateX(0px); opacity: 1;}      /*在100%时设置文字在想X轴0px位移处，也就是原始布局的位置，透明度为1，也就是文字可以看见了*/
}
@-webkit-keyframes righteaseinAnimate{
    0%{ -webkit-transform: translateX(2000px); opacity: 0;}
    100%{ -webkit-transform: translateX(0px); opacity: 1;}
}
@-o-keyframes righteaseinAnimate{
    0%{ -webkit-transform: translateX(2000px); opacity: 0;}
    100%{ -webkit-transform: translateX(0px); opacity: 1;}
}
@-ms-keyframes righteaseinAnimate{
    0%{ -webkit-transform: translateX(2000px); opacity: 0;}
    100%{ -webkit-transform: translateX(0px); opacity: 1;}
}
@-moz-keyframes righteaseinAnimate{
    0%{ -webkit-transform: translateX(2000px); opacity: 0;}
    100%{ -webkit-transform: translateX(0px); opacity: 1;}
}
.content{
    animation: righteaseinAnimate 1s ease 1;    /*调用已定义好的动画righteaseinAnimate，全程运行时间1S，进入的速度曲线为ease，只播放一次*/
    -webkit-animation: righteaseinAnimate 1s ease 1;
    -moz-animation: righteaseinAnimate 1s ease 1;
    -ms-animation: righteaseinAnimate 1s ease 1;
    -o-animation: righteaseinAnimate 1s ease 1;
    
    /*规定动画的最后状态为结束状态*/
    animation-fill-mode:forwards;
    -webkit-animation-fill-mode: forwards;  
      -o-animation-fill-mode: forwards; 
      -ms-animation-fill-mode: forwards;   
      -moz-animation-fill-mode: forwards; 
}

.dlpdf {
	display: inline-block;
	outline: none;
	cursor: pointer;
	text-align: center;
	text-decoration: none;
	font: 14px/100% Arial, Helvetica, sans-serif;
	padding: .5em 2em .55em;
	text-shadow: 0 1px 1px rgba(0,0,0,.3);
	-webkit-border-radius: .5em; 
	-moz-border-radius: .5em;
	border-radius: .5em;
	-webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);
	-moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);
	box-shadow: 0 1px 2px rgba(0,0,0,.2);
}
.dlpdf:hover {
	text-decoration: none;
}
.dlpdf:active {
	position: relative;
	top: 1px;
}
.orange {
	color: #fef4e9;
	border: solid 1px #da7c0c;
	background: #f78d1d;
	background: -webkit-gradient(linear, left top, left bottom, from(#faa51a), to(#f47a20));
	background: -moz-linear-gradient(top,  #faa51a,  #f47a20);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#faa51a', endColorstr='#f47a20');
}
.orange:hover {
	background: #f47c20;
	background: -webkit-gradient(linear, left top, left bottom, from(#f88e11), to(#f06015));
	background: -moz-linear-gradient(top,  #f88e11,  #f06015);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#f88e11', endColorstr='#f06015');
}
.orange:active {
	color: #fcd3a5;
	background: -webkit-gradient(linear, left top, left bottom, from(#f47a20), to(#faa51a));
	background: -moz-linear-gradient(top,  #f47a20,  #faa51a);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#f47a20', endColorstr='#faa51a');
}
</style>
</head>

<body >

<header class="navbar navbar-static-top bs-docs-nav" id="top"
		role="banner">
	<div class="container-fluid">
	<img alt="" src="image/logo1.png" width="150px" style="position: absolute;"> 
 
		<nav id="bs-navbar" class="collapse navbar-collapse">

		<ul class="nav navbar-nav navbar-right">
			<%
				if (isLogin == false) {
			%>
			<li><a href="index.jsp"><i class="glyphicon glyphicon-home">返回主页</i></a></li>
			<li><a href="user/userAction!login"><i
					class="glyphicon glyphicon-user"></i>用户登录</a>
			</li>
			<li><a href="user/userAction!register"><i
					class="glyphicon glyphicon-registration-mark"></i>用户注册</a>
			</li>
			<%
				} else {
			%>
			<li><a href="index.jsp"><i class="glyphicon glyphicon-home">返回主页</i></a></li>
			<!-- <li><a href="javascript:history();"><i class="glyphicon glyphicon-comment">历史记录</i></a></li> -->
			<li><a><i class="glyphicon glyphicon-user">用户:<%=userName%></i>
			</a>
			</li>
			<li><a href="user/userAction!userQuit"><i
					class="glyphicon glyphicon-off"></i>退出</a>
			</li>
			<%
				}
			%>
		</ul>
		</nav>
	</div>
	</header>
	
	
	<div class="cetitle" style="font-size:40px;text-align:center;"><span>${stdResult.chineseName}</span></div>
	<div class="cetitle" style="font-size:30px;text-align:center;"><span>${stdResult.englishName}</span></div>
	<br><br>
	<div class="content" style="margin-left:15%;font-size:20px;width:70%">
	<div><span>标准状态：${stdResult.stdState} </span></div>
	<div><span>标准类型：${stdResult.stdType} </span></div>
	<div><span>标准号：${stdResult.stdNO} </span></div>
	<div><span>ICS分类：${stdResult.ICS} </span></div>
	<div><span>CCS分类：${stdResult.CCS} </span></div>
	<div><span>发布日期：${stdResult.dateIssued} </span></div>
	<div><span>实施日期：${stdResult.dateImplement} </span></div>
	<div><span>发布者：${stdResult.issuedDep}</span></div>
	<div><span>提出单位：${stdResult.propose} </span></div>
	<div><span>归口单位：${stdResult.belongDep}</span></div>
	<div><span>起草单位：${stdResult.publishDep}</span></div>
	<div><span>起草人：${stdResult.draftPerson}</span></div>
	<div><span>适用范围：</span><span id="scope"></span></div>
	<div><span>关键词：${stdResult.keyword} </span></div>
	</div>
	<br><br>
	<form action="Pdf_detail.jsp" method="post" style="margin-left:20%">
		 <input type="hidden" id="pdfurl" name="uriec">
		<input type="submit"  value="查看pdf"  class="dlpdf orange" text="PDF阅读"/>
	</form>
	
	
	

	<script type="text/javascript">
    
    	$(function(){
			
			var scope = "<%=scope%>".replace(/\//g,"");
			$("#scope").html(scope);
    		var uriec = 'C:/stdPdf/' + '${stdResult.bookId}/' + '${stdResult.fileref}';
    		$("#pdfurl").val(uriec);
    	});
    </script>
</body>
</html>
