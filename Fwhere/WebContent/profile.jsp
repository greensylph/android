<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.fwhere.beans.*"%>
<%@ taglib uri="WEB-INF/tags/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="WEB-INF/tags/struts-html.tld" prefix="html"%>
<%@ taglib uri="WEB-INF/tags/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>帐号 Profile</title>
<link href="css/main.css" type="text/css" rel="Stylesheet" />
<script src="./scripts/jquery-1.2.3.min.js"></script>

</head>
<%
Object o = request.getSession().getAttribute("user");
if(null == o) {
	response.sendRedirect("login.do");
}
%>
<body>
<!-- header -->
<div align="center">
<table width="1000px">
	<tiles:insert page="/header.jsp" />
</table>
</div>

<!-- body -->
<div align="center">
<table border=1 width="1000px">
	<tr>
		<td>
			<table border="1" width="200px">
			<tr>
				<td><a href="<%=request.getContextPath() %>/updateContent.do">修改资料</a>
				</td>
			</tr>
			<tr>
				<td><a href="<%=request.getContextPath() %>/updateImg.do">修改头像</a>
				</td>
			</tr>
			<tr>
			</tr>
			</table>
		</td>
		<td><div id="content">
			<%
			Object cateId = request.getAttribute("cateId");
			String cateIdtmp = "";
			if(null != cateId) {
				cateIdtmp = (String) cateId;
			}
			if("2".equals(cateIdtmp)) {
			%>
			<tiles:insert page="/step1.jsp" />
			<%} else {%>
			<tiles:insert page="/profileContent.jsp" />
			<%} %>	
		</div></td>
	</tr>
</table>
</div>
<!-- footer -->
<div align="center" style="margin-top:20px">
	<table width="1000px" >
		<tr>
			<td width="100%" colspan="2"><tiles:insert page="/footer.jsp" /></td>
		</tr>
	</table>
</div>
</body>
</html>