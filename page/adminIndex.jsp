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
	<style type="text/css">
		body{ 
			text-align:center; 
			vertical-align:middle; 
		} 
	</style>
  </head>
  
  <body>
    <br><br><br><br><br><br><br><br><br>
    <form name="loginForm" method="post" action="admin/adminAction!adminLoginValid">
    <center><img src="image/qixiangsmall.png"></center>
    <table width="50%"  border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
      <!--<caption>
      <span class="style1">      登 录      </span><br>
      </caption>
      -->
      <tr align="left">
      	<td colspan="2" style="text-align: center;height: 35">
      		管理员登录
      	</td>
      </tr>
      <tr align="left">
        <th width="40%" height="35" align="center" scope="row">用户名:</th>
        <td width="60%"><input name="adminname" type="text" id="adminName" maxlength="20"></td>
      </tr>
      <tr align="left">
        <th height="35" align="center" scope="row">密&nbsp;码:</th>
        <td><input name="password" type="password" id="password" maxlength="30"></td>
      </tr>
      <tr align="center">
        <th height="35" colspan="2" scope="row"><input type="submit" name="Submit" value="登录">
      </tr>
    </table>
    </form>
  </body>
</html>
