<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.fwhere.beans.*,java.util.*" %>
<%@ taglib prefix="bean" uri="/WEB-INF/tags/struts-bean.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="./css/style.css" type="text/css"
	media="screen" />
<script src="./scripts/jquery-1.2.3.min.js"></script>
<title>Insert title here</title>
<script>
	function goto(str) {
		$("#content").load(str);
	}
</script>
</head>
<body>
<div id="content">
<% 
List<Meetup> meetups = (List<Meetup>)request.getAttribute("meetups"); 
int count = 1;
for(Meetup meetup: meetups) {
	request.setAttribute("date", meetup.getStarted());
%>

<table width="750px" border="1">
	<tr height="80px">
		<td valign="top" width="20px"><%=count %></td>
		<td width="250px" ><img src="<%=meetup.getPicture() %>" /></td>
		<td ><%=meetup.getName() %>&nbsp;-&nbsp;<bean:write name="date" format="MM/dd/yyyy HH:mm"/> <br> 
			<%=meetup.getAddress() %><br>
			<%=meetup.getDescription() %></td>
		<td ><input type="button" name="join" value=" Join " /><input type="button" name="detail" value="Detail" /></td>
	</tr>
</table>
<%
count++;
}%>
<div align="right"><br>
<%
int pageNum = 1;
%>
<a href="#" onclick="javascript:goto('meetups.do?pageNum=<%=pageNum - 1 %>');">前页&nbsp;</a>&nbsp;
<a href="#" onclick="javascript:goto('meetups.do?pageNum=1');">1</a>&nbsp; 
<a href="#" onclick="javascript:goto('meetups.do?pageNum=2');">2</a>&nbsp;
<a href="#" onclick="javascript:goto('meetups.do?pageNum=3');">3</a>&nbsp; 
<a href="#" onclick="javascript:goto('meetups.do?pageNum=4');">4</a>&nbsp;
<a href="#" onclick="javascript:goto('meetups.do?pageNum=5');">5</a>&nbsp; 
<a href="#" onclick="javascript:goto('meetups.do?pageNum=6');">6</a>&nbsp;
<a href="#" onclick="javascript:goto('meetups.do?pageNum=7');">7</a>&nbsp; 
<a href="#" onclick="javascript:goto('meetups.do?pageNum=8');">8</a>&nbsp;
<a href="#" onclick="javascript:goto('meetups.do?pageNum=<%=pageNum + 1 %>');">后页</a></div>
</div>
</body>
</html>