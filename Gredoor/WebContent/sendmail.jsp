<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gredoor.model.*,java.io.*,java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
Email is sending...
<%
EmailService mservice = new EmailService();
try {
	String srcpath = "/opt/emails/3.txt";
	String mailaddress = "";
	FileReader fr;
	try {
		fr = new FileReader(srcpath);
		BufferedReader br = new BufferedReader(fr); // 缓冲指定文件的输入
		int count = 1;
		while (br.ready()) {
			mailaddress = br.readLine();// 读取一行
			if (!mailaddress.trim().equals("")) {
				mailaddress = EmailService.parse(mailaddress);
				if (!mailaddress.trim().equals("")) {
					mservice.setTo(mailaddress);
					mservice.sendEmai(mservice, "开始使用 Gredoor 导航", "hello");
					System.out.println("Successfully send mail No."+ count+" to " + mailaddress);
					count += 1;
				}
			}
		}
		br.close();
		br.close();
		fr.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("finished!");
} catch (Exception x) {
	x.printStackTrace();
}
%>
</body>
</html>