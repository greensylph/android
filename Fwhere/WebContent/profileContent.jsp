<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="com.fwhere.beans.*,java.util.*"%>
<%@ taglib uri="WEB-INF/tags/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="WEB-INF/tags/struts-html.tld" prefix="html"%>
<%@ taglib uri="WEB-INF/tags/struts-tiles.tld" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="./scripts/jquery.min.js"></script>
<script src="./scripts/jquery.Jcrop.js"></script>
<link rel="stylesheet" href="./css/jquery.Jcrop.css" type="text/css" />
<link rel="stylesheet" href="./css/demos.css" type="text/css" />
<script src="./scripts/jquery.min.js"></script>
<script>
	$(document).ready(function() {
   //code here
   //alert("hello");
   //window.location.href="http://localhost:8080/Fwhere/profile.do";
	});
</script>
</head>
<body>
<!-- header -->
<div align="center">
<table width="1000px">
	<tiles:insert page="/header.jsp" />
</table>
</div>
<%
User user = null;
Object o1 = request.getSession().getAttribute("user");
if(null == o1) {
	response.sendRedirect("login.do");
}
%>
<!-- body -->
<div align="center">
<table border=1 width="1000px">
	<tr>
		<td valign="top">
		<table  width="200px">
			<%
			Object o = request.getSession().getAttribute("imgName");
			String imgName = "";
			if(null != o) {
				imgName = (String) o;
			%>
			<tr><div align="center"><br><img src="http://localhost:8080/Fwhere/ImageShowServlet/<%=imgName%>"/></div></tr>
			<%} else {%>
			<tr><div align="center"><br><img src="./images/man.GIF"/></div></tr>
			<%} %>
			<tr>
				<td><br><div align="center"><a href="<%=request.getContextPath() %>/updateContent.do">修改资料</a></div>
				</td>
			</tr>
			<tr>
				<td><br><div align="center"><a href="<%=request.getContextPath() %>/updateImg.do">修改头像</a></div>
				</td>
			</tr>
			<tr>
			</tr>
		</table>
		</td>
		<td>
		<div align="center">
		<form action="updateProfile.do" id="profileForm">
		<table border="1" width="800px">
			<tr>
				<td></td>
				<td>
				<div align="center"></div>
				</td>
			</tr>
			<tr>
				<td>昵称：</td>
				<td>
				<div align="left"><input type="text" name="nickname"
					value="Michael Feng" /></div>
				</td>
			</tr>
			<tr>
				<td>年龄：</td>
				<td>
				<div align="left"><input type="text" name="age" value="23" /></div>
				</td>
			</tr>
			<tr>
				<td>城市：</td>
				<td>
				<div align="left">上海，浦东</div>
				</td>
			</tr>
			<tr>
				<td>联系电话：</td>
				<td>
				<div align="left"><input type="text" name="phone"
					value="13817775704" /></div>
				</td>
			</tr>
			<tr>
				<td>教育：</td>
				<td>
				<div align="left"><input type="text" name="education"
					value="博士" /></div>
				</td>
			</tr>
			<tr>
				<td>Email：</td>
				<td>
				<div align="left"><input type="text" name="email"
					value="jasonzqc@hotmail.com" /></div>
				</td>
			</tr>
			<tr>
				<td>描述：</td>
				<td>
				<div align="left"><textarea rows="4" cols="40">Programmer, dreamer, christian, love delicious food, classic
		music fun, a little nerd. Gotta be a sexy coder.</textarea></div>
				</td>
			</tr>
			<tr>
				<td>工作：</td>
				<td>
				<div align="left">Engineer</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<div align="center">
				<center><input type="button" value="保存"
					onclick="javascript:updateProfile();" /></center>
				</div>
				</td>
			</tr>
		</table>
		</form>
		</div>
		</td>
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