<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tags/struts-html.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登陆 / Fwhere</title>
<link href="css/main.css" type="text/css" rel="Stylesheet" />
</head>
<body style="background-color: #003366; background-repeat: repeat-y">
	<font face="Gabriola" color="white" size="5px"
		style="margin-left: 20px; margin-top: 20px"> Friends Anywhere.
		Meet more friends. Enjoying your life. <br> <span
		style="margin-left: 20px; font-size: 20px">更多的朋友，一起享受生活.</span>
	</font>
	<br>
	<br>
	<br>
	<br>
	<br>
	<div align="center">
		<table>
			<tr>
				<td valign="top">
					<form action="<%=request.getContextPath()%>/login.do"
						method="post">
						<table>
							<tr>
								<td><font color="white">用户名：</font>
								</td>
								<td><input type="text" name="username" id="username"
									value="michael" style="width: 200px" />
								</td>
							</tr>
							<tr>
								<td><font color="white">密码：</font>
								</td>
								<td><input type="text" name="password" value="google69181"
									style="width: 200px" /></td>
							</tr>
							<tr>
								<td colspan="2">
									<div align="right">
										<input type="checkbox"><font color="white">记住密码</font>&nbsp;&nbsp;<font
											color="white">-</font>&nbsp;&nbsp; <a
											href="passwordReset.jsp"><font color="white">忘记密码?</font>
										</a> <input type="hidden" name="status" value="false" />&nbsp;&nbsp;&nbsp;
										<input type="submit" value="登录" />
									</div>
								</td>
							</tr>
						</table>
					</form> <br>
				<br>
				<br>
				<br>
				<br>
					<form action="<%=request.getContextPath()%>/register.do"
						method="post">
						<table border="0">
							<tr>
								<td colspan="2"><div align="center">
										<font color="white">初来乍到?</font>
									</div>
								</td>
							</tr>
							<tr>
								<td><font color="white">用户名：</font>
								</td>
								<td><input type="text" name="rusername"
									style="width: 200px" />
								</td>
							</tr>
							<tr>
								<td><font color="white">邮箱：</font>
								</td>
								<td><input type="text" name="remail" style="width: 200px" />
								</td>
							</tr>
							<tr>
								<td><font color="white">密码：</font>
								</td>
								<td><input type="password" name="rpassword"
									style="width: 200px" />
								</td>
							</tr>
							<tr>
								<td colspan="2" align="right"><input type="submit"
									value="注册" />
								</td>
							</tr>
						</table>
					</form></td>
				<td width="200px">&nbsp;</td>
				<td><img src="images/loginImg.png" /></td>
			</tr>
		</table>
	</div>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<center>
		<font color="white">About Help Blog Mobile Status Jobs Terms
			Privacy Advertisers Businesses Media Developers Resources © 2012
			Fwhere</font>
	</center>
</body>
</html>