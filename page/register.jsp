<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	/* String userNameEmpty = (String)request.getAttribute("userNameEmpty");
	 String passwordEmpty = (String)request.getAttribute("passwordEmpty");
	 String passwordNotSame = (String)request.getAttribute("passwordNotSame");
	 String userExist = (String)request.getAttribute("userExist"); */
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>用户注册</title>
<meta http-equiv="x-ua-compatible" content="IE=7,9,10">
<link href="js/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="js/bootstrap/css/bootstrap-table.css" rel="stylesheet">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/bootstrap/js/bootstrap.min.js"></script>
<script src="js/bootstrap/js/bootstrap-table.js"></script>
<style type="text/css">
body {
	text-align: center;
	vertical-align: middle;
}
</style>

<script type="text/javascript">
	window.onload = init;
	function init() {
		var btn = document.getElementById("reset");
		btn.click();
	}

	var InterValObj; //timer变量，控制时间
	var count = 60; //间隔函数，1秒执行
	var curCount;//当前剩余秒数
	var phonenum = "";
	var param = "";
	function sendMessage() {
		curCount = count;
		param = parseInt(((Math.random() * 9 + 1) * 100000)).toString();
		// 向后台发送处理数据
		phonenum = document.getElementById("phone").value;
		//alert(phonenum);
		$.ajax({
			type : "POST", // 用POST方式传输
			dataType : "json", // 数据格式:JSON
			url : "/termonline/userAjax!sendMsg", // 目标地址
			data : {
				telenum : phonenum,
				param : param
			},
			success : function(result) {
				var json = eval(result);
				//alert(json);
				if (json.receive == '1') {
					$("#jbPhoneTip").html(
							"<img style='width:20px' src='image/True.gif'><font color='green'>短信验证码已发到您的手机,请查收</font>");
				} else {
					$("#jbPhoneTip").html(
							"<img style='width:20px' src='image/False.gif'><font color='red'>短信验证码发送失败，请重新发送</font>");
				}
			}
		});

		// 设置button效果，开始计时
		document.getElementById("btnSendCode").setAttribute("disabled", "true");//设置按钮为禁用状态
		document.getElementById("btnSendCode").value = "请在" + curCount
				+ "后再次获取";//更改按钮文字
		InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器timer处理函数，1秒执行一次

	}

	//timer处理函数

	function SetRemainTime() {
		if (curCount == 0) {
			window.clearInterval(InterValObj);// 停止计时器
			document.getElementById("btnSendCode").removeAttribute("disabled");//移除禁用状态改为可用
			document.getElementById("btnSendCode").value = "重新发送验证码";
		} else {
			curCount--;
			document.getElementById("btnSendCode").value = "请在" + curCount
					+ "秒后再次获取";
		}
	}

	//验证短信验证码
	function doCompare() {
		var codelast = document.getElementById("codelast").value;//获取输入的验证码
		if (codelast == null || codelast == '') {
			$("#jbPhoneTip").html(
							"<img style='width:20px' src='image/False.gif'><font color='red'>验证码不能为空</font>");
				
		} else {
			//var result = $;

			//var json=eval(${sessionScope.key});
			//alert(json.key);
			//alert(json.key.resp.respCode);
			if (document.getElementById("btnSendCode").getAttribute("disabled") != null) {
				//alert(json.telenum);
				if (param == codelast) {
					$("#save").submit();//验证成功
				} else {
					$("#jbPhoneTip").html(
							"<img style='width:20px' src='image/False.gif'><font color='red'>验证码不正确请重新输入</font>");
				}
			} else {
				$("#jbPhoneTip").html("<img style='width:20px' src='image/False.gif'><font color='red'>验证码已超时请重新发送</font>");
			}
		}
	}

	function Usernamecheck() {
	var username = document.getElementById("uid").value;
		$.ajax({
			'url' : "/termonline/userAjax!Usernamecheck",
			'dataType' : 'json', // 返回数据类型
			'type' : 'POST', // 请求类型 
			'data' :  {name:username},
			success : function(data) {
				//alert((eval(data)).name);
				if ((eval(data)).name == "0") {
					$("#usernamecheck").html("<img style='width:20px' src='image/False.gif'><font color='red'>用户名不能为空</font>");
				} else if ((eval(data)).name == "1") {
					$("#usernamecheck").html("<img style='width:20px' src='image/False.gif'><font color='red'>用户名已存在</font>");
				}else{
					$("#usernamecheck").html("<img style='width:20px' src='image/True.gif'><font color='green'>用户名可用</font>");
				}
			}
		});
	}
	function Passwordcheck(){
	if(document.getElementById("password").value==""){
		$("#passwordcheck").html("<img style='width:20px' src='image/False.gif'><font color='red'>密码不能为空</font>");
	}else if(document.getElementById("password").value.length<6){
		$("#passwordcheck").html("<img style='width:20px' src='image/False.gif'><font color='red'>密码至少六位</font>");
	}else{
		$("#passwordcheck").html("<img style='width:20px' src='image/True.gif'><font color='green'>密码可用</font>");
	}
	}
	function Passwordrecheck(){
	if(document.getElementById("passwordconfirm").value==""){
		$("#passwordrecheck").html("<img style='width:20px' src='image/False.gif'><font color='red'>请确认密码</font>");
	}else if(document.getElementById("password").value!=document.getElementById("passwordconfirm").value){
		$("#passwordrecheck").html("<img style='width:20px' src='image/False.gif'><font color='red'>确认失败</font>");
	}else{
		$("#passwordrecheck").html("<img style='width:20px' src='image/True.gif'><font color='green'>确认成功</font>");
	}
	
	
	}
	
	function CheckMail() {
		var  mail= document.getElementById("email").value;
	 	var filter  = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;///^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
	 	if (filter.test(mail)){
	 	 	$("#emailcheck").html("<img style='width:20px' src='image/True.gif'><font color='green'>邮箱可用</font>");
	 	}else {
	 		$("#emailcheck").html("<img style='width:20px' src='image/False.gif'><font color='red'>格式错误</font>");
	 	}
	}
	function Phonecheck(){
		var  phone= document.getElementById("phone").value;
		if((/^1[34578]\d{9}$/.test(phone))||(/^\d{3,4}\-1[34578]\d{9}$/.test(phone))){
		 	$("#phonecheck").html("<img style='width:20px' src='image/True.gif'><font color='green'>手机号可用</font>");
		
		}else{
	 		$("#phonecheck").html("<img style='width:20px' src='image/False.gif'><font color='red'>格式错误</font>");
		}
	}
</script>

</head>

<body>
	<img alt="" src="image/logo1.png" width="150px" style="float:left">
	<br>
	<br>
	<br>
	<br>
	<br>

	<div class="container" style="width:40%;text-align:center;">
		<form name="userRegisterForm" method="post" id="save"
			action="user/userAction!registerValid">
			<center>
				<img src="image/qixiangsmall.png">
			</center>
			<table class="table table-striped" width="50%" border="0"
				align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
				<tr align="left">
					<td colspan="2"
						style="text-align: center;height: 55;font-size:24px">用户注册</td>
				</tr>
				<tr align="left">
					<th width="30%" height="35"
						style="padding-left:5%;padding-top:2.5%" scope="row">用户名:</th>
					<td width="70%">&nbsp;<input width="50%" name="username" type="text"
						id="uid" maxlength="20" onblur="Usernamecheck();"> <label
						id="usernamecheck"></label></td>
				</tr>
				<tr align="left">
					<th height="35" align="center"
						style="padding-left:5%;padding-top:2.5%" scope="row">密&nbsp;码:</th>
					<td>&nbsp;<input name="password" type="password" id="password"
						maxlength="30" onblur="Passwordcheck();"> <label
						id="passwordcheck"></label></td>
				</tr>
				<tr align="left">
					<th height="35" align="center"
						style="padding-left:5%;padding-top:2.5%" scope="row">密码确认:</th>
					<td>&nbsp;<input name="passwordConfirm" type="password"
						id="passwordconfirm" maxlength="30"  onblur="Passwordrecheck();"> <label
						id="passwordrecheck"></label></td>
				</tr>
				<tr align="left">
					<th height="35" align="center"
						style="padding-left:5%;padding-top:2.5%" scope="row">邮&nbsp;箱:</th>
					<td>&nbsp;<input name="email" type="text" id="email"
						maxlength="30"  onblur="CheckMail();"><label
						id="emailcheck"></label></td>
				</tr>
				<tr align="left">
					<th height="35" align="center"
						style="padding-left:5%;padding-top:2.5%" scope="row">手机号:</th>
					<td>&nbsp;<input name="phone" type="text" id="phone"
						maxlength="30" onblur="Phonecheck();"><label
						id="phonecheck"></label></td>
				</tr>

				<tr align="center">
					<span style="text-align: center;color: red">${requestScope.userNameEmpty
						}</span>
				</tr>
				<tr align="center">
					<span style="text-align: center;color: red">${requestScope.passwordEmpty
						}</span>
				</tr>
				<tr align="center">
					<span style="text-align: center;color: red">${requestScope.passwordNotSame
						}</span>
				</tr>
				<tr align="center">
					<span style="text-align: center;color: red">${requestScope.userExist
						}</span>
				</tr>

				<tr>
					<th height="30px" style="padding-left:5%;padding-top:2.5%"
						scope="row">输入验证码：</th>
					<td style="font-size: 16px;font-weight:bold; ">&nbsp;<input
						type="text" style="width:100px;font-size:18px" maxlength="8"
						name="codelast" id="codelast"> <input
						style="font-size: 14px;" id="btnSendCode" name="btnSendCode"
						type="button" value="点击获取验证码" onclick="sendMessage();" />
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2" style="height:45px"><span
						id="jbPhoneTip"></span></td>
				</tr>

				<tr>
					<th height="35" colspan="2" scope="row"><input
						style="margin-left:30%" type="button" name="Submit"
						onClick="doCompare();" value="确认"> <input
						style="margin-left:2%" id="reset" type="reset" name="Submit2"
						value="重置"> <input style="margin-left:2%" type="button"
						name="Submit3" value="返回"
						onclick="javascript:window.location.href ='/termonline/user/userAction!login';">
					</th>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
