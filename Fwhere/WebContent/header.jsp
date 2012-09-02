<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.fwhere.beans.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/main.css" type="text/css" rel="Stylesheet" />
<title>Insert title here</title>
</head>
<body>

<div align="center" id="top">
<table width="1000px" bgcolor="#ffffff" >
	<tr>
		<td width="650px"><div align="left"><a href="home.do">首页</a></div></td>
<%
User user = null;
Object o = request.getSession().getAttribute("user");
if(null != o) {
	user = (User) o;
%>
		<td width="130px"><div align="right"><%=user.getUsername() %></div></td>
<%} %>		
		<td width="60px"><div align="right"><a href="profile.do">帐户</a> </div></td>
		<td width="60px"><div align="right"><a href="logout.do">退出</a> </div></td>
	</tr>
</table>
</div>
</body>
</html>