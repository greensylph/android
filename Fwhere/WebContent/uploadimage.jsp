<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%
  String picUrl = "";
  String  step = "";
  Object o1 = request.getAttribute("Picurl");
  if(null != o1) 
	  picUrl = o1.toString();
  Object o2 =request.getAttribute("step"); 
  if(null != o2) 
	  step = o2.toString();
  String defaultPic ="image/man.GIF";
  if("3".equals(step))
    defaultPic = "User/UserHeadImage/"+picUrl;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<title></title>
<link href="css/main.css" type="text/css" rel="Stylesheet" />
    <script type="text/javascript" src="js/jquery1.2.6.pack.js"></script>
    <script  type="text/javascript" src="js/ui.core.packed.js" ></script>
    <script type="text/javascript" src="js/ui.draggable.packed.js" ></script>
    <script type="text/javascript" src="js/CutPic.js"></script>
    <script type="text/javascript">
        function Step1() {
            $("#Step2Container").hide();           
        }

        function Step2() {
            $("#CurruntPicContainer").hide();
        }
        function Step3() {
            $("#Step2Container").hide();          
       }
       function uploadImg() {
			$("#content").load('uploadImg.do');
		}
		$('#imgForm').ajaxSubmit({
				date:{reported:reported},
				dataType : 'json',
				success : function(data) {
				
				},
				error : function(data) {
				}
			});
    </script>
</head>
<body>
   <table border=1 width="800px">
	 <tr><td>
    <div id="content">
     <div class="left">
         <!--当前照片-->
         <div id="CurruntPicContainer">
            <div class="title"><b>当前照片</b></div>
            <div class="photocontainer">
                <img id="imgphoto" src="<%=defaultPic%>" style="border-width:0px;" />
            </div>
         </div>
         <!--Step 2-->
         <div id="Step2Container">
           <div class="title"><b> 裁切头像照片</b></div>
           <div class="uploadtooltip">您可以拖动照片以裁剪满意的头像</div>                              
                   <div id="Canvas" class="uploaddiv">
                   
                            <div id="ImageDragContainer">                               
                               <img id="ImageDrag" class="imagePhoto" src="UploadPhoto/<%=picUrl %>" style="border-width:0px;" />                                                        
                            </div>
                            <div id="IconContainer">                               
                               <img id="ImageIcon" class="imagePhoto" src="UploadPhoto/<%=picUrl%>" style="border-width:0px;" />                                                        
                            </div>                          
                    </div>
                    <div class="uploaddiv">
                       <table>
                            <tr>
                                <td id="Min">
                                        <img alt="缩小" src="image/_c.gif" onmouseover="this.src='image/_c.gif';" onmouseout="this.src='image/_h.gif';" id="moresmall" class="smallbig" />
                                </td>
                                <td>
                                    <div id="bar">
                                        <div class="child">
                                        </div>
                                    </div>
                                </td>
                                <td id="Max">
                                        <img alt="放大" src="image/c.gif" onmouseover="this.src='image/c.gif';" onmouseout="this.src='image/h.gif';" id="morebig" class="smallbig" />
                                </td>
                            </tr>
                        </table>
                    </div>
                    <form action="/Fwhere/servlet/ZoomImage" method="post">
                    <input type="hidden" name="picture" value="<%=picUrl%>"/>
                    <div class="uploaddiv">
                        <input type="submit" name="btn_Image" value="保存头像" id="btn_Image" />
                    </div>           
                    <div>
                    
                    <input name="txt_width" type="hidden" value="1" id="txt_width" /><br />
                    <input name="txt_height" type="hidden" value="1" id="txt_height" /><br />
                    <input name="txt_top" type="hidden" value="82" id="txt_top" /><br />
                    <input name="txt_left" type="hidden" value="73" id="txt_left" /><br />
                    <input name="txt_DropWidth" type="hidden" value="120" id="txt_DropWidth" /><br />
                    <input name="txt_DropHeight" type="hidden" value="120" id="txt_DropHeight" /><br />
                    <input name="txt_Zoom" type="hidden" id="txt_Zoom" />
                   </div>  </form>
         </div>
     
     </div>
     <form name="imgForm" method="post" action="uploadImg.do" id="imgForm" enctype="multipart/form-data">
     <div class="right">
         <!--Step 1-->
         <div id="Step1Container">
            <div class="title"><b>更换照片</b></div>
            <div id="uploadcontainer">
                <div class="uploadtooltip">请选择新的照片文件，文件需小于2.5MB</div>
                <div class="uploaddiv"><input type="file" name="fuPhoto" id="fuPhoto" title="选择照片" /></div>
                <div class="uploaddiv"><input type="button" name="btnUpload" value="上 传"  onclick="uploadImg();" id="btnUpload" /></div>
            </div>
         
         </div>
     </div>
     </form>
    
    <% 
      if(null==picUrl||"".equals(picUrl))
      {%>
          <script type='text/javascript'>Step1();</script>
      <%}else if(!"".equals(picUrl)&& "2".equals(step)){
      %>
      <script type='text/javascript'>Step2();</script>
      <%}else if(!"".equals(picUrl)&& "3".equals(step)){
      %>
      <script type='text/javascript'>Step3();</script>
      <%}%>
      </div>
</td></tr></table>
</body>
</html>