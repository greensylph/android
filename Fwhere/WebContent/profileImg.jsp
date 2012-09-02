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
<%
Object imgWO = request.getAttribute("imgWidth");
int imgWidth = 0;
if(null != imgWO) {
	imgWidth = (Integer)imgWO;
}
Object imgHO = request.getAttribute("imgHeight");
int imgHeight= 0;
if(null != imgHO) {
	imgHeight = (Integer)imgHO;
}
%>
<script language="Javascript">

			// Remember to invoke within jQuery(window).load(...)
			// If you don't, Jcrop may not initialize properly
			jQuery(window).load(function(){

				jQuery('#cropbox').Jcrop({
					setSelect: [ 100, 100, 200, 200 ],
					onChange: showCoords,
					onSelect: showPreview,
					aspectRatio: 1
				});
			});

			// Our simple event handler, called from onChange and onSelect
			// event handlers, as per the Jcrop invocation above
			function showPreview(coords)
			{
				var imgWidth = 300;
				var imgHeight = 300;
				var imgW = <%=imgWidth %>;
				var imgH = <%=imgHeight %>;
				if(imgW != null && imgW != 0) {
					imgWidth = imgW;
				}
				if(imgH != null && imgH != 0) {
					imgHeight = imgH;
				}
			
				if (parseInt(coords.w) > 0)
				{
					var rx = 100 / coords.w;
					var ry = 100 / coords.h;

					jQuery('#preview').css({
						width: Math.round(rx * imgWidth) + 'px',
						height: Math.round(ry * imgHeight) + 'px',
						marginLeft: '-' + Math.round(rx * coords.x) + 'px',
						marginTop: '-' + Math.round(ry * coords.y) + 'px'
					});
				}
			}

			function showCoords(c)
			{
				$('#x').val(c.x);
				$('#y').val(c.y);
				$('#x2').val(c.x2);
				$('#y2').val(c.y2);
				$('#w').val(c.w);
				$('#h').val(c.h);
			};
		  
		  
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
Object o3 = request.getSession().getAttribute("user");
if(null == o3) {
	response.sendRedirect("login.do");
}
%>
<!-- body -->
<div align="center">
<table border=1 width="1000px">
	<tr>
		<td>
		<table width="200px">
			<%
			Object o2 = request.getSession().getAttribute("imgSrc");
			String imgSrc2 = "";
			if(null != o2) {
				imgSrc2 = (String) o2;
				Random r= new Random();
				int i = r.nextInt(100);
			%>
			<tr>
				<div align="center"><br>
				<img src="<%=imgSrc2 %>?id=<%=i %>" /></div>
			</tr>
			<%} else {%>
			<tr>
				<div align="center"><br>
				<img src="./images/man.GIF" /></div>
			</tr>
			<%} %>
			<tr>
				<td><br>
				<div align="center"><a
					href="<%=request.getContextPath() %>/updateContent.do">修改资料</a></div>
				</td>
			</tr>
			<tr>
				<td><br>
				<div align="center"><a
					href="<%=request.getContextPath() %>/updateImg.do">修改头像</a></div>
				</td>
			</tr>
			<tr>
			</tr>
		</table>
		</td>
		<td>
		<div id="imgSet">
		<div id="outer">
		<div class="jcExample">
		<div class="article"><!-- This is the image we're attaching Jcrop to -->

		<table width="800px">
			<tr>
				<%
				Object o = request.getAttribute("imgSrc");
				String imgSrc = "";
				if(null != o) {
					imgSrc = (String)o;
				}
				
				Object o1 = request.getAttribute("imgName");
				String imgName = "";
				if(null != o1) {
					imgName = (String)o1;
				}
				%>
				<td width="400px">
				<div align="center">
				<form action="<%=request.getContextPath() %>/uploadImg.do"
					name="imgForm1" enctype="multipart/form-data" method="POST">
				<input type="file" name="imgFile" onchange="submit();" /></form>
				<img src="<%=imgSrc %>" id="cropbox" /></div>
				</td>
				<td width="400px">
				<form action="<%=request.getContextPath() %>/saveImg.do"
					name="imgForm" method="POST"><input type="hidden"
					name="imgName" id="imgName" value="<%=imgName %>" /> <label><input
					type="hidden" size="4" id="x" name="x" /></label> <label><input
					type="hidden" size="4" id="y" name="y" /></label> <label><input
					type="hidden" size="4" id="x2" name="x2" /></label> <label><input
					type="hidden" size="4" id="y2" name="y2" /></label> <label><input
					type="hidden" size="4" id="w" name="w" /></label> <label><input
					type="hidden" size="4" id="h" name="h" /></label>
									<div align="center">
									<div style="width:100px;height:100px;overflow:hidden;">
											<img src="<%=imgSrc %>" id="preview" />
										</div><br><br>
										<input type="submit" value="保 存" name="submit"/>
									</div>
									</form> 
								</td>
							</tr>
						</table>
						
					</div>
				</div>
			</div>
		</div>
		</td>
	</tr>
</table>
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