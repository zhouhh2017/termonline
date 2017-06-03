<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
	//String isToStd = (String)request.getAttribute("isToStd");
	String jingque;
	String mohu;
	String shuyu;
	String biaozhun;
	//System.out.println(queryMode);
	if("jingque".equals(queryMode)){
		jingque="btn btn-primary";
		mohu="btn btn-default";
	}else{
		mohu="btn btn-primary";
		jingque="btn btn-default";
	}
	
	if("shuyu".equals(mode)){
		shuyu="active";
		biaozhun="";
	}else{
		shuyu="";
		biaozhun="active";
	}
	
	Map<String,String> fill = (Map<String,String>)session.getAttribute("fill"); 
	String y2007 = "2007 (" + (fill.get("2007")==null? "0":fill.get("2007")) + ")";
	String y2008 = "2008 (" + (fill.get("2008")==null? "0":fill.get("2008")) + ")";
	String y2009 = "2009 (" + (fill.get("2009")==null? "0":fill.get("2009")) + ")";
	String y2010 = "2010 (" + (fill.get("2010")==null? "0":fill.get("2010")) + ")";
	String y2011 = "2011 (" + (fill.get("2011")==null? "0":fill.get("2011")) + ")";
	String y2012 = "2012 (" + (fill.get("2012")==null? "0":fill.get("2012")) + ")";
	String y2013 = "2013 (" + (fill.get("2013")==null? "0":fill.get("2013")) + ")";
	String y2014 = "2014 (" + (fill.get("2014")==null? "0":fill.get("2014")) + ")";
	String y2015 = "2015 (" + (fill.get("2015")==null? "0":fill.get("2015")) + ")";
	String y2016 = "2016 (" + (fill.get("2016")==null? "0":fill.get("2016")) + ")";
	String y2017 = "2017 (" + (fill.get("2017")==null? "0":fill.get("2017")) + ")";
	//得到点击的是哪一个年份
	String year = (String)request.getAttribute("year");
	//得到点击的是哪一个来源
	String source = (String)request.getAttribute("source");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">

<title>气象术语在线</title>

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
<!-- 历史记录的模态框 -->
<%@ include file="common/historyModal.jsp" %>
<!-- 历史记录查询 -->
<script src="myjs/history.js"></script>
<script type="text/javascript">
	var a = document.getElementsByName("row");
 	var page = 1;
 	var pagemax = 1;
 	var n = 6;//一页显示个数
	function init(){
	
	getpagenum();
		if(pagemax==1){
			$("#bp").attr("disabled",true);
			$("#lp").attr("disabled",true);
		}
		for ( var i = 0; i < a.length; i++) {
			a[i].id = "tr" + i;	
				dividpage(i,page);
	}
 		$('#tree').treeview({data: getTree()});
 		$('#tree').on('nodeSelected', function(event, data) {
                    clickNode(event, data);
                });
  	}
  	window.onload = init;
  	function topage(b)//改变当前页面值
	{
		if(b==0||b==1){page=1;$("#fp").attr("disabled",true);$("#ap").attr("disabled",true);$("#bp").attr("disabled",false);$("#lp").attr("disabled",false);}
		else if(b==-1||(b>=pagemax)){page=pagemax;$("#bp").attr("disabled",true);$("#lp").attr("disabled",true);$("#fp").attr("disabled",false);$("#ap").attr("disabled",false);}
		else {page = b;$("#fp").attr("disabled",false);$("#ap").attr("disabled",false);$("#bp").attr("disabled",false);$("#lp").attr("disabled",false);}
		init();
	}
	
	function dividpage(div_id,p)//显示本页，隐藏其他
	{
		var obj = document.getElementById("tr" + div_id);
		if(parseInt(div_id/n)==parseInt(p-1)){obj.style.display = "table-row";}
		else {obj.style.display = "none";}
		//alert("topage"+parseInt((div_id-3)/n/2));
	}
	function getpagenum()//获得页码
	{
		//alert("getpage"+page);
		var obj=document.getElementById("pagenum");
		if(a.length%n==0)
		{pagemax=parseInt(a.length/n);}
		else
		{pagemax=parseInt(a.length/n+1);}
		obj.value="第"+page+"页 / 共"+pagemax + "页";
		//alert(pagemax+","+a.length%(2*n));
		return page;
	}
 function getTree() {
	var tree = [ {
				text : "年份",
				state: {
					    disabled: false,
					    selected: ("<%=year%>"=="年份")?true:false
					  	},
				nodes : [ {
					text : "<%=y2017%>",
					state: {
					    disabled: (<%=fill.get("2017")%>==null?"0":<%=fill.get("2017")%>)=="0"?true:false,
					    selected: ("<%=year%>"=="2017")?true:false
					  	}
					},{
					text : "<%=y2016%>",
					state: {
					    disabled: (<%=fill.get("2016")%>==null?"0":<%=fill.get("2016")%>)=="0"?true:false,
					    selected: ("<%=year%>"=="2016")?true:false
					  	}
					},{
					text : "<%=y2015%>",
					state: {
					    disabled: (<%=fill.get("2015")%>==null?"0":<%=fill.get("2015")%>)=="0"?true:false,
					    selected: ("<%=year%>"=="2015")?true:false
					  	}
					},{
					text : "<%=y2014%>",
					state: {
					    disabled: (<%=fill.get("2014")%>==null?"0":<%=fill.get("2014")%>)=="0"?true:false,
					    selected: ("<%=year%>"=="2014")?true:false
					  	}
					},{
					text : "<%=y2013%>",
					state: {
					    disabled: (<%=fill.get("2013")%>==null?"0":<%=fill.get("2013")%>)=="0"?true:false,
					    selected: ("<%=year%>"=="2013")?true:false
					  	}
					},{
					text : "<%=y2012%>",
					state: {
					    disabled: (<%=fill.get("2012")%>==null?"0":<%=fill.get("2012")%>)=="0"?true:false,
					    selected: ("<%=year%>"=="2012")?true:false
					  	}
					},{
					text : "<%=y2011%>",
					state: {
					    disabled: (<%=fill.get("2011")%>==null?"0":<%=fill.get("2011")%>)=="0"?true:false,
					    selected: ("<%=year%>"=="2011")?true:false
					  	}
					},{
					text : "<%=y2010%>",
					state: {
					    disabled: (<%=fill.get("2010")%>==null?"0":<%=fill.get("2010")%>)=="0"?true:false,
					    selected: ("<%=year%>"=="2010")?true:false
					  	}
					},{
					text : "<%=y2009%>",
					state: {
					    disabled: (<%=fill.get("2009")%>==null?"0":<%=fill.get("2009")%>)=="0"?true:false,
					    selected: ("<%=year%>"=="2009")?true:false
					  	}
					},{
					text : "<%=y2008%>",
					state: {
					    disabled: (<%=fill.get("2008")%>==null?"0":<%=fill.get("2008")%>)=="0"?true:false,
					    selected: ("<%=year%>"=="2008")?true:false
					  	}
					},{
					text : "<%=y2007%>",
					state: {
					    disabled: (<%=fill.get("2007")%>==null?"0":<%=fill.get("2007")%>)=="0"?true:false,
					    selected: ("<%=year%>"=="2007")?true:false
					  	}
					}]
			}];
		return tree;
	}
	
	function clickNode(event, data) {
              	if(data.text=="年份"){
              	$("#condition_year").val(data.text);
              		$("#search_box").submit();
              	}else{
              		$("#isClickNode").val("yes");
              		$("#condition_year").val(data.text.substring(0,4));
                	$("#search_box").submit();
              	}
                
               
                
            }
	
	
	function searchhotword(searchItem) {
		//alert(searchItem);
		$('#searchItem').val(searchItem);
		$("#hidden").val("shuyu");
		document.getElementById("search_box").submit();
	}
	//切换搜索类型
	function transfer(id) {
		$("#shuyu").removeClass("active");
		$("#cidian").removeClass("active");
		$("#biaozhun").removeClass("active");
		$("#" + id).addClass("active");
		$("#hidden").val(id);
		$("#isToStd").val("no");
		$("#condition_source").val("来源(全部)");
		$("#condition_year").val("年份");
		//alert($("#hidden").val());
		//window.location.href = "item/itemAction!itemSearch";
		document.getElementById("search_box").submit();
	}
	//切换查询结果类型
	function transferQuery(id) {
		if (id == "jingque") {
			$("#hidden1").val(id);
			$("#mohu").attr("class", "btn btn-default");
			$("#jingque").attr("class", "btn btn-primary");
			//$("#y2").val("jingque");
			document.getElementById("search_box").submit();
		} else {
			$("#hidden1").val(id);
			$("#jingque").attr("class", "btn btn-default");
			$("#mohu").attr("class", "btn btn-primary");
			//$("#y2").val("mohu");
			document.getElementById("search_box").submit();
			//alert($("#hidden1").val());
			//window.location.href = 'item/itemAction!itemSearch?queryMode='+$("#hidden1").val()+'&mode='+$("#hidden").val()+'&searchItem='+searchItem;
		}
	}
	//提交表单
	function formSubmitCheck(){
		var value = $("#searchItem").val();
		if(value.trim()==""){
			$("#searchItem").attr("placeholder","搜索词不能为空");
			return false;
		}
		if($("#isClickNode").val()=="no"){
			$("#condition_source").val("来源(全部)");
			$("#condition_year").val("年份");
			$("#isClickNode").val("yes");
		}
	}
	
	
	function standDetail(searchStd){
		if(<%=isLogin%>==true&&<%=level%>==2){
			var s = new Date().getMilliseconds();
			window.open("/termonline/item/itemAction!toStandard?searchStd=" + searchStd,s);
		}else{
			if(<%=level%>==0){
				var conn = confirm("请先登录！！！"); 
				if(conn==true){
					window.location.href = "/termonline/user/userAction!login?searchItem=<%=searchItem %>&mode=<%=mode %>&isToStd=no&year=<%=year%>&source=<%=source %>";
				}
			}else if(<%=level%>==1){
				var conn = confirm("普通用户没有查看权限，请充值会员！！！"); 
				if(conn==true){
					window.location.href = "/termonline/user/userAction!login";
				}
			}
		}
	}
	
	
	//window.onload = init;
</script>
<style>
	table {
		border: 0px solid #fff;
	}
	.result-table td, 
	.result-table th {
		border: 1px solid #ddd;
	}
	#bs-navbar {
		padding: 0 20px
	}
	.search-container {
		margin-bottom: 15px;
	}
	#time {
		margin-bottom: 10px;
	}
</style>
</head>
<body>




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
			<li><a href="user/userAction!login?searchItem=<%=searchItem %>&mode=<%=mode %>&isToStd=no&year=<%=year%>&source=<%=source %>"><i
					class="glyphicon glyphicon-user"></i>用户登录</a>
			</li>
			<li><a href="user/userAction!register"><i
					class="glyphicon glyphicon-registration-mark"></i>用户注册</a>
			</li>
			<%
				} else {
			%>
			<li><a href="index.jsp"><i class="glyphicon glyphicon-home">返回主页</i></a></li>
			<li><a href="javascript:history();"><i class="glyphicon glyphicon-comment">历史记录</i></a></li>
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

	
	
	<!-- 历史记录查询表单 -->
	<form action="item/itemAction!itemSearch" method="post" id="history_box" style="display: none">
		<!-- 查询的模式 -->
		<input type="hidden" id="his1" name="mode" value="shuyu" />
		<!-- 分页的模式 -->
		<input type="hidden" id="his2" name="queryMode" value="jingque" />
		<input type="text" id="his3" name="searchItem" />
		<button class="btn btn-warning"  id="his_submit" type="submit">搜索</button>
	</form>

	<form action="item/itemAction!itemSearch" method="post" id="search_box" onsubmit="return formSubmitCheck()">
	
		<!-- 查询的模式 -->
		<input type="hidden" id="hidden" name="mode" value="<%=mode %>" />
		<!-- 分页的模式 -->
		<input type="hidden" id="hidden1" name="queryMode" value="<%=queryMode %>" />
		<!-- 是否是从查询连接跳转过去 -->
		<input type="hidden" id="isToStd" name="isToStd" value="no" />
		
		<input type="hidden" id="isByYear" name="isByYear" value="yes">
		<input type="hidden" id="condition_source" name="source" value="<%=source %>">
		<input type="hidden" id="condition_year" name="year" value="<%=year %>">
		
		<input type="hidden" id="isClickNode" value="no">
		<div class="container" style="margin-bottom: 15px">
			<div class="row" style="margin-left: 14%;margin-right: 14%">
				<div ><!-- style="margin-left: 40%" -->
					<div class="input-group" ><!--  style="width:72%"-->
						<input style="height: 50px;font-size: 25px" type="text" id="searchItem" class="form-control" name="searchItem"
							placeholder="请输入要检索的术语（中文、英文）" value="<%=searchItem %>"> <span
							class="input-group-btn">
							<button style="height: 50px;width:70px" class="btn btn-warning" type="submit">搜索</button> </span>
					</div>
					<!-- /input-group -->
				</div>

			</div>
		</div>
		<!-- /.col-lg-6 -->
		
		<div class="container">
		<div class="row" style="margin-left: 14%">
			<div >
				<ul class="nav nav-pills">
			  <li class="<%=shuyu %>" id="shuyu" onclick="transfer(this.id);"><a>术语</a></li>
			  <li id="cidian" onclick="transfer(this.id);" style="display:none"><a><small>词典</small></a></li>
			  <li class="<%=biaozhun %>" id="biaozhun" onclick="transfer(this.id);"><a>标准</a></li>
				</ul>
			</div>
		</div>
			
		</div>	
		
	</form>

<div style="float:left;width:16%;margin-top: 15px;margin-left: 1%;">
		<table id="resultTable" width="100%" align="center" cellpadding="0"
					cellspacing="0" bordercolor="#ebcbbe">
			<tr>
				<td>
					<div id="tree"></div>
				</td>
			</tr>
		</table>    
	</div>
<!-- 中间div -->
	<div style="float:left;width:60%;margin-left: 1%;margin-top: 15px">
	<table id="resultTable" width="100%" align="center" cellpadding="0" cellspacing="0" >
		<tr>
				<td colspan="2" style="text-align: left">
				<div class="btn-group">
		    		<h4>标准列表</h4>
    			</div>
				</td>
				<td colspan="2" style="text-align: right">
		    		<small>本次为您找到相关结果约<font color="red">${requestScope.itemNum}</font>条，耗时
		    		<font color="red">${requestScope.useTime}</font>秒</small>
				</td>
			</tr>
	</table>
		
		<table id="resultTable" class="result-table" width="100%" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#ebcbbe">
			<tr style="background-color: #eeeeee;height: 50px">
				<th width="20%">
					<h4 style="color: black;text-align: center">
						<b>中文</b>
					</h4>
				</th>
				<th width="25%">
					<h4 style="color: black;text-align: center">
						<b>英文</b>
					</h4>
				</th>
				<th width="35%">
					<h4 style="color: black;text-align: center">
						<b>作者</b>
					</h4>
				</th>
				<th width="20%">
					<h4 style="color: black;text-align: center">
						<b>出处</b>
					</h4>
				</th>
			</tr>

			<!-- <form style="display: none" action="item/itemAction!itemSearch" method="post" id="search_box" onsubmit="return formSubmitCheck()">
				<input type="hidden" id="searchStd" >
			</form> -->
			<c:forEach var="item" items="${queryResult}">
				<c:if test="${not empty item}">
					<tr name="row" style="background-color: #ffffff">
					<td><h4 style="padding-left: 6px;font-family:'华文新魏';font-size:18px;padding-top: 0px;padding-bottom: 0px;padding-right: 6px">${item.chineseName}</h4>
					</td>
					<td><h4 style="padding-left: 6px;font-family:'Times New Roman';padding-top: 0px;padding-bottom: 0px;padding-right: 6px">${item.englishName}</h4>
					</td>
					<td><h4 style="padding-left: 6px;font-family:'华文新魏';font-size:18px;padding-top: 0px;padding-bottom: 0px;padding-right: 6px">${item.draftPerson}</h4>
					</td>
					<td style="text-align: center"><h4 style="cursor:pointer;;font-family:'Times New Roman';"><a href="javascript:void(0)" onclick="standDetail($(this).text())">${item.stdNO}</a></h4>
					</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
	<div style="margin-top: 20px">
			<div>
				<button class="btn btn-primary" id="fp" onclick="topage(1);" disabled>首页</button>
				<button class="btn btn-primary" id="ap" onclick="topage(getpagenum()-1);" disabled>上一页</button>
				<input class="btn btn-primary" type="reset" id="pagenum" disabled></input>
				<button class="btn btn-primary" id="bp" onclick="topage(getpagenum()+1);">下一页</button>
				<button class="btn btn-primary" id="lp" onclick="topage(-1);">尾页</button>
			</div>
		<br><br>
		</div>
		</div>
	<!-- 右侧div -->
	<div style="float: left;width:18%;margin-left: 1%;margin-top: 15px">
		<table width="100%" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#ebcbbe">
			 <tr align="left" class="hotword">
				<td>
					<div> <!-- style="background-color:#85DAFF;border-radius: 5px" -->
						<p>
						<h4>
							<b>搜索热词</b>
						</h4>
						</p>
					
						<ul style="list-style-type:none" class="list-group">
							<li class="list-group-item"><a href="javascript:void(0)" onclick="searchhotword($(this).text())"><b>${hotResult[0]}</b>
							</a>
							</li>
							<li class="list-group-item"><a href="javascript:void(0)" onclick="searchhotword($(this).text())"><b>${hotResult[1]}</b>
							</a>
							</li>
							<li class="list-group-item"><a href="javascript:void(0)" onclick="searchhotword($(this).text())"><b>${hotResult[2]}</b>
							</a>
							</li>
							<li class="list-group-item"><a href="javascript:void(0)" onclick="searchhotword($(this).text())"><b>${hotResult[3]}</b>
							</a>
							</li>
							<li class="list-group-item"><a href="javascript:void(0)" onclick="searchhotword($(this).text())"><b>${hotResult[4]}</b>
							</a>
							</li>
							<li class="list-group-item"><a href="javascript:void(0)" onclick="searchhotword($(this).text())"><b>${hotResult[5]}</b>
							</a>
							</li>
							<li class="list-group-item"><a href="javascript:void(0)" onclick="searchhotword($(this).text())"><b>${hotResult[6]}</b>
							</a>
							</li>
							<li class="list-group-item"><a href="javascript:void(0)" onclick="searchhotword($(this).text())"><b>${hotResult[7]}</b>
							</a>
							</li>
							<li class="list-group-item"><a href="javascript:void(0)" onclick="searchhotword($(this).text())"><b>${hotResult[8]}</b>
							</a>
							</li>
							<li class="list-group-item"><a href="javascript:void(0)" onclick="searchhotword($(this).text())"><b>${hotResult[9]}</b>
							</a>
							</li>
						</ul>
					</div></td>
			</tr> 
		</table>
	</div>
	</body>
</html>
