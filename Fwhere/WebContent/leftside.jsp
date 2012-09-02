<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/tags.css" type="text/css" media="screen" />
<script src="./scripts/tags.js"></script>
</head>
<body>
<div align="center" >
<table border="1" width="250px" >
	<tr>
		<td colspan="2"><div><img src="./images/myicon.png" /></div></td>
	</tr>
	<tr>
		<td>昵称：</td>
		<td>Michael Feng</td>
	</tr>
	<tr>
		<td>地址：</td>
		<td>Shanghai，Pudong</td>
	</tr>
	<tr>
		<td>状态：</td>
		<td>Single</td>
	</tr>
</table>
<br>

<table border="1" width="250px">
	<tr>
		<td colspan="2"><div align="left">朋友</div></td>
	</tr>
	<tr>
		<td colspan="2">城市:<select>
			<option>Shanghai</option>
		</select><select>
			<option>Pudong</option>
		</select></td>
	</tr>
	<tr>
		<td>我想找 <select>
			<option>Boy</option>
			<option>Girl</option>
		</select></td>
		<td><input type="button" name="search" value="搜索" /></td>
	</tr>
</table><br>
<table border="1" width="250px">
	<tr>
		<td colspan="2"><div align="left">活动</div></td>
	</tr>
	<tr>
		<td colspan="2">城市:<select>
			<option>Shanghai</option>
		</select><select>
			<option>Pudong</option>
		</select></td>
	</tr>
	<tr>
		<td>我想找 <select>
			<option>Music</option>
			<option>Movie</option>
		</select></td>
		<td><input type="button" name="search" value="搜索" /></td>
	</tr>
</table><br>
<table border="1" width="250px">
	<tr>
		<td><input type="text" name="tag" width="150px"/></td>
		<td><input type="button" name="search" value="增加标签" /></td>
	</tr>
</table>
<div id="tags">
	<a href="#">Movie</a>
	<a href="#" class="red">Music </a>
	<a href="#" class="blue">Girl</a>
	<a href="#" class="red">Nerd</a>
	<a href="#" class="blue">Beijing </a>
	<a href="#" class="red">China</a>
	<a href="#" class="red">IT</a>
	<a href="#" class="yellow">Spring </a>
	<a href="#" class="blue">Struts</a>
	<a href="#" class="red">Hibernate </a>
	<a href="#" class="blue">Velocity</a>
	<a href="#" class="blue">Linux</a>
	<a href="#" class="blue">MS</a>
	<a href="#" class="yellow">Fwhere</a>
</div>
</div>
</body>
</html>