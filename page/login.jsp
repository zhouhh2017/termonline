<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>TermOnline</title>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="js/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="js/bootstrap/css/bootstrap-table.css" rel="stylesheet">
 <script src="js/jquery-2.1.4.min.js"></script>
<script src="js/bootstrap/js/bootstrap.min.js"></script>
<script src="js/bootstrap/js/bootstrap-table.js"></script>
	<style type="text/css">
		body{ 
			text-align:center; 
			vertical-align:middle; 
		} 
	</style>
	
  </head>
  
  <body>
  <img alt="" src="image/logo1.png" width="150px" style="float:left"> 

    <br><br><br><br><br><br><br><br><br>
    <div class="container" style="width:40%;text-align:center;">
    <form name="loginForm" method="post" action="user/userAction!loginValid">
    <center><img src="image/qixiangsmall.png"></center>
    <table width="50%"  class="table table-striped"  border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
      <tr align="left">
      	<td colspan="2" style="text-align: center;height: 55;font-size:24px;">
      		用户登录
      	</td>
      </tr>
      <tr align="left">
        <th width="40%" height="35" align="center" style="padding-left:5%;padding-top:2.5%" scope="row">&nbsp;用户名:</th>
        <td width="60%">&nbsp;<input name="username" type="text" id="username" maxlength="20"></td>
      </tr>
      <tr align="left">
        <th height="35" align="center" style="padding-left:5%;padding-top:2.5%" scope="row">&nbsp;密&nbsp;码:</th>
        <td>&nbsp;<input name="password" type="password" id="password" maxlength="30"></td>
      </tr>
      
      <tr align="center">
        <span style="text-align: center;color: red">${requestScope.loginError }</span>
      </tr>
      <tr align="center">
        <th height="35" colspan="2" scope="row"><input style="margin-left:30%" type="submit" name="Submit" value="登录">
        <input style="margin-left:10%" type="button" name="Submit2" value="注册" onclick="javascript:window.location.href ='/termonline/user/userAction!register';"> </th>
      </tr>
    </table>
    </form>
    </div>
    
  </body>
</html>
