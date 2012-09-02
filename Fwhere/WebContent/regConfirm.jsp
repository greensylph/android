<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<span id="test"></span>
<%
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	String status = request.getParameter("status");
	request.setAttribute("isLogin", true);
%>
<form action="<%=request.getContextPath() %>/home.do" name="info"
	method="post"><input type="hidden" name="username"
	value="<%=username %>" /> <input type="hidden" name="password"
	value="<%=password %>" />
	<input type="hidden" name="status"
	value="<%=status %>" /></form>

<script>   
  var alltime = 5;   
  function   setTime(){   
  if   (alltime<=0){   
  	document.forms["info"].submit();  
  clearInterval(s);   
  }   
  else{   
  alltime--;   
  m=Math.floor(alltime/60);   
  se=Math.round(alltime-(Math.floor(alltime/60)*60));   
  test.innerText="登录中 Signing up..."+se+" seconds left";   
  }   
  }   
  s=setInterval("setTime()",1000)   
  </script>
</body>
</html>