<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tiles" uri="WEB-INF/tags/struts-tiles.tld"%>
<%@ page import="com.fwhere.beans.*" %>
<html>
<script src="./scripts/jquery-1.2.3.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fwhere 首页 / Home</title>
<script type="text/javascript">
function meetups() {
	$("#content").load('meetups.do');
}
function friends() {
	$("#content").load('friends.do');
}
function others() {
	$("#content").load('others.do');
}
</script>
<style type="text/css">
<!--
body { 
/*background-image:url(images/back.jpg);*/
background:#ffffff;  
} 
--> 
</style>
</head>
<body>
<%
User user = null;
Object o = request.getSession().getAttribute("user");
if(null == o) {
	response.sendRedirect("login.do");
}
%>
<!-- header -->
<div align="center" id="top">
	<table width="1000px">
		<tiles:insert page="/header.jsp" />
	</table>
</div>
<div id="wraper">
<!-- body -->
<div align="center">
	<table width="1000px" border="1" >
		<tr>
			<td width="250px" rowspan="2" valign="top"><tiles:insert page="/leftside.jsp" /></td>
			<td width="750px">
				<div align="center">
				<a href="#" onclick="javascript:meetups();">活动</a>
				<span style="margin-left:50px"><a href="#" onclick="javascript:friends();">朋友</a></span>
				<span style="margin-left:50px"><a href="#" onclick="javascript:others();">其它</a></span></div>
			</td>
		</tr>
		<tr>
			<td width="750px" valign="top" height="750px">
				<div id="content">
					<tiles:insert page="/meetups.do" />
				</div>
			</td>
		</tr>
	</table>
</div>

<!-- footer -->
<div align="center" style="margin-top:20px">
	<table width="1000px">
		<tr>
			<td width="100%" colspan="2"><tiles:insert page="/footer.jsp" /></td>
		</tr>
	</table>
</div>
</div>

</body>
</html>
