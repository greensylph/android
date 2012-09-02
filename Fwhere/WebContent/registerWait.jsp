<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.fwhere.util.*,java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册验证中</title>
</head>
<body>
<%
	EmailUtil emailUtil = new EmailUtil();
HashMap<String, String> info = (HashMap<String, String>)request.getAttribute("info");
emailUtil.setDestMail(info.get("email"));
emailUtil.setRusername(info.get("username"));
emailUtil.setRpassword(info.get("password"));
emailUtil.send();
emailUtil = null;
%>
<div align="center" style="height:500px"><br><br>
<div align="right" style="margin-right:100px"><span style="margin-right:50px"><a href="#">查收邮件</a></span><a href="login.jsp">返回登陆页</a></div>	
<br><br><br><br>
<hr>
注册验证中，请查收您的注册邮箱...
<hr><br><br><br><br><br><br><br><br>
<center><div>About Help Blog Mobile Status Jobs Terms Privacy
Advertisers Businesses Media Developers Resources © 2012 星期五</div></center>
</div>
</body>
</html>