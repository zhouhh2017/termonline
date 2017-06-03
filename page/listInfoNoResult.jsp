<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String searchItem = (String) request.getAttribute("searchItem");
	String userName = (String) session.getAttribute("userName");
	boolean isLogin = userName == null ? false : true;
	String queryMode = (String) request.getAttribute("queryMode");
	String mode = (String) request.getAttribute("mode");
	String jingque;
	String mohu;
	String shuyu;
	String biaozhun;
	//System.out.println(queryMode);
	if ("jingque".equals(queryMode)) {
		jingque = "btn btn-info";
		mohu = "btn btn-default";
	} else {
		mohu = "btn btn-info";
		jingque = "btn btn-default";
	}

	if ("shuyu".equals(mode)) {
		shuyu = "active";
		biaozhun = "";
	} else {
		shuyu = "";
		biaozhun = "active";
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

<title>TermOnline</title>

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
<%@ include file="common/historyModal.jsp"%>
<!-- 历史记录查询 -->
<script src="myjs/history.js"></script>
<script type="text/javascript">
	function init() {
		$('#tree').treeview({
			data : getTree()
		});
		$('#tree').on('nodeSelected', function(event, data) {
			clickNode(event, data);
		});
	}
	window.onload = init;
	function getTree() {

		var tree = [  {
				text : "来源",
				nodes : [ {
					text : "气象标准术语库"
					},{
					text : "冰冻圈科学辞典"
					},{
					text : "英汉冰冻圈科学词汇"
					}
				]
			} , {
				text : "年份",
				nodes : [ {
					text : "2017"
					},{
					text : "2016"
					},{
					text : "2015"
					},{
					text : "2014"
					},{
					text : "2013"
					},{
					text : "2012"
					},{
					text : "2011"
					},{
					text : "2010"
					},{
					text : "2009"
					},{
					text : "2008"
					},{
					text : "2007"
					}]
			}];
		return tree;
	}
	
	function clickNode(event, data) {
              	//alert(data.text);
                if(data.text=="年份"){
                	$("#search_box").submit();
                }else if(data.text=="来源"){
                	$("#search_box").submit();
                }else if(data.text=="气象标准术语库"||data.text=="冰冻圈科学辞典"||data.text=="英汉冰冻圈科学词汇"){
                	var search = $("#search-text").val();
                	$("#s3").val(search);
	                var source = data.text;
	                //alert(source);
	                $("#s4").val(source);
	                //$("#y2").val($("#hidden1").val());
	                $("#source_search").submit();
                }else{
                	var search = $("#search-text").val();
                	$("#y3").val(search);
	                var year = data.text;
	                $("#y4").val(year);
	                //$("#y2").val($("#hidden1").val());
	                $("#year_search").submit();
	            }          
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
		$("#submit").click();
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
	function searchhotword(searchItem) {
		//alert(searchItem);
		$('#search-text').val(searchItem);
		$("#submit").click();
	}
	
	//提交表单
	function formSubmitCheck(){
		var value = $("#search-text").val();
		if(value.trim()==""){
			$("#search-text").attr("placeholder","搜索词不能为空");
			return false;
		}
	}
</script>
<style>
	.search-container {
		margin-bottom: 15px;
	}
</style>
</head>
<body>

	<header class="navbar navbar-static-top bs-docs-nav" id="top"
		role="banner">
	<div class="container-fluid">
		<nav id="bs-navbar" class="collapse navbar-collapse"> 
<img alt="" src="image/logo1.png" width="150px" style="position: absolute;"> 

		<ul class="nav navbar-nav navbar-right">
			<%
				if (isLogin == false) {
			%>
			<li><a href="index.jsp"><i class="glyphicon glyphicon-home">返回主页</i>
			</a>
			</li>
			<li><a href="user/userAction!login?searchItem=<%=searchItem %>&mode=<%=mode %>&queryMode=<%=queryMode %>&year=<%=year%>&source=<%=source %>"><i
					class="glyphicon glyphicon-user"></i>用户登录</a></li>
			<li><a href="user/userAction!register"><i
					class="glyphicon glyphicon-registration-mark"></i>用户注册</a></li>
			<%
				} else {
			%>
			<li><a href="index.jsp"><i class="glyphicon glyphicon-home">返回主页</i>
			</a>
			</li>
			<li><a href="javascript:history();"><i
					class="glyphicon glyphicon-comment">历史记录</i>
			</a>
			</li>
			<li><a><i class="glyphicon glyphicon-user">用户:<%=userName%></i>
			</a></li>
			<li><a href="user/userAction!userQuit"><i
					class="glyphicon glyphicon-off"></i>退出</a></li>
			<%
				}
			%>
		</ul>
		</nav>
	</div>
	</header>


	<!-- 历史记录查询表单 -->
	<form action="item/itemAction!itemSearch" method="post"
		id="history_box" style="display: none">
		<!-- 查询的模式 -->
		<input type="hidden" id="his1" name="mode" value="shuyu" />
		<!-- 分页的模式 -->
		<input type="hidden" id="his2" name="queryMode" value="jingque" /> <input
			type="text" id="his3" name="searchItem" />
		<button class="btn btn-warning" id="his_submit" type="submit">搜索</button>
	</form>
	<!-- 按年份查询 -->
	<form action="item/itemAction!searchItemFromItem_Dic_ByYear"
		method="post" id="year_search" style="display: none">
		<!-- 查询的模式 -->
		<input type="hidden" id="y1" name="mode" value="shuyu" />
		<!-- 分页的模式 -->
		<input type="hidden" id="y2" name="queryMode" value="jingque" /> <input
			type="text" id="y3" name="searchItem" /> <input type="hidden"
			id="y4" name="year">
		<button class="btn btn-warning" id="his_submit" type="submit">搜索</button>
	</form>
	<!-- 按来源查询 -->
	<form action="item/itemAction!searchItemFromItem_Dic_BySource" method="post" id="source_search" style="display: none">
		<!-- 查询的模式 -->
		<input type="hidden" id="s1" name="mode" value="shuyu" />
		<!-- 分页的模式 -->
		<input type="hidden" id="s2" name="queryMode" value="mohu" />
		<input type="text" id="s3" name="searchItem" />
		<input type="hidden" id="s4" name="source">
		<button class="btn btn-warning"  id="his_submit" type="submit">搜索</button>
	</form>

	<form action="item/itemAction!itemSearch" method="post" id="search_box" onsubmit="return formSubmitCheck();">

		<input type="hidden" id="hidden" name="mode" value="shuyu" /> <input
			type="hidden" id="hidden1" name="queryMode" value="mohu" />
		<!-- 是否是从查询连接跳转过去 -->
		<input type="hidden" id="isToStd" name="isToStd" value="no" />
		<input type="hidden" id="condition_source" name="source" value="<%=source %>">
		<input type="hidden" id="condition_year" name="year" value="<%=year %>">
		<div class="container search-container">
			<div class="row" style="margin-left: 14%;margin-right: 14%">
				<div>
					<!-- style="margin-left: 40%" -->
					<div class="input-group">
						<!--  style="width:72%"-->
						<input style="height: 50px;font-size: 25px" type="text"
							id="search-text" class="form-control" name="searchItem"
							placeholder="请输入要检索的术语（中文、英文）" value="<%=searchItem%>">
						<span class="input-group-btn">
							<button style="height: 50px;width:70px" id="submit"
								class="btn btn-warning" type="submit">搜索</button> </span>
					</div>
					<!-- /input-group -->
				</div>

			</div>
		</div>
		<!-- /.col-lg-6 -->

		<div class="container">
			<div class="row" style="margin-left: 14%">
				<div>
					<ul class="nav nav-pills">
						<li class="<%=shuyu%>" id="shuyu" onclick="transfer(this.id);"><a
							href="javascript:void(0)">术语</a>
						</li>
						<li id="cidian" onclick="transfer(this.id);" style="display:none"><a>词典</a>
						</li>
						<li class="<%=biaozhun%>" id="biaozhun"
							onclick="transfer(this.id);"><a href="javascript:void(0)">标准</a>
						</li>
					</ul>
				</div>
			</div>

		</div>

	</form>
	<div
		style="float:left;width:16%;margin-top: 15px;margin-left: 1%;display: none">
		<table id="resultTable" width="100%" border="4px solid #ebcbbe;"
			align="center" cellpadding="0" cellspacing="0" bordercolor="#ebcbbe">
			<tr>
				<td>
					<div id="tree"></div></td>
			</tr>
		</table>
	</div>
	<div style="float:left;width:60%; margin-left: 16%;margin-top: 15px">
		<table border="0" align="center" cellpadding="0" cellspacing="0"
			bordercolor="#CCCCCC">
			<tr align="left">
				<td colspan="4">
					<div style="color:#aaaaaa;border-radius: 5px">
						<p>
						<h3>
							<b>未找到相关内容，请重新输入！</b>
						</h3>
						</p>
					</div></td>
			</tr>
		</table>
	</div>
	<!-- 右侧div -->
	<div
		style="float: left;width:18%;margin-left: 79%;margin-top: 15px;position: fixed">
		<table width="100%" align="center"
			cellpadding="0" cellspacing="0" bordercolor="#ebcbbe">
			<tr align="left" class="hotword">
				<td>
					<div>
						<!-- style="background-color:#85DAFF;border-radius: 5px" -->
						<p>
						<h4>
							<b>搜索热词</b>
						</h4>
						</p>

						<ul style="list-style-type:none" class="list-group">
							<li class="list-group-item"><a href="javascript:void(0)"
								onclick="searchhotword($(this).text())"><b>${hotResult[0]}</b>
							</a></li>
							<li class="list-group-item"><a href="javascript:void(0)"
								onclick="searchhotword($(this).text())"><b>${hotResult[1]}</b>
							</a></li>
							<li class="list-group-item"><a href="javascript:void(0)"
								onclick="searchhotword($(this).text())"><b>${hotResult[2]}</b>
							</a></li>
							<li class="list-group-item"><a href="javascript:void(0)"
								onclick="searchhotword($(this).text())"><b>${hotResult[3]}</b>
							</a></li>
							<li class="list-group-item"><a href="javascript:void(0)"
								onclick="searchhotword($(this).text())"><b>${hotResult[4]}</b>
							</a></li>
							<li class="list-group-item"><a href="javascript:void(0)"
								onclick="searchhotword($(this).text())"><b>${hotResult[5]}</b>
							</a></li>
							<li class="list-group-item"><a href="javascript:void(0)"
								onclick="searchhotword($(this).text())"><b>${hotResult[6]}</b>
							</a></li>
							<li class="list-group-item"><a href="javascript:void(0)"
								onclick="searchhotword($(this).text())"><b>${hotResult[7]}</b>
							</a></li>
							<li class="list-group-item"><a href="javascript:void(0)"
								onclick="searchhotword($(this).text())"><b>${hotResult[8]}</b>
							</a></li>
							<li class="list-group-item"><a href="javascript:void(0)"
								onclick="searchhotword($(this).text())"><b>${hotResult[9]}</b>
							</a></li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
