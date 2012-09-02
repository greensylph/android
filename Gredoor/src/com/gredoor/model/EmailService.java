package com.gredoor.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailService {
	private final static String HOST = "127.0.0.1";// 设置为本机IP做服务器邮件测试
//	private final static String HOST = "60.190.251.200";// 设置为本机IP做服务器邮件测试
	
	private final static String FROM = "noreplay@gredoor.com";// 发送方(OA系统)邮件地址

	
	private final static String USERNAME = "noreply";// 发送方邮件用户名
	
	private final static String PASSWORD = "noreply";// 发送发邮件密码
	
	private String to; // 接收方邮件地址
	
	private String mail_body = "您好！"
		+ "<br/><br/>"
		+ "还在为对着电脑不知道上哪的尴尬发愁吗？ 怕跟不上社会热点潮流最新信息吗？ 新闻  音乐  视频  购物  微博一网打尽"
		+ "<br/><br/>"
		+ "Gredoor 团队邀请您使用我们的导航信息网站。邀请您加入 Gredoor！"
		+ "<br/><br/>" +"-------------------------------------------------------------------------------------------------------------------------------"+ "<br/></br>"+ "更多详情：<br/><br/>"  
		+ "<br/>网站访问地址：<a href='http://www.gredoor.com' target='_blank' shape='rect'>http://www.gredoor.com</a>"
		+ "<br/><br/>"
		+ "备用访问地址：<a href='http://1.gredoor.sinaapp.com' target='_blank' shape='rect'>http://1.gredoor.sinaapp.com</a>"
		+ "<br/><br/>" + "<img src='http://1.gredoor.sinaapp.com/img/preview.png' />" + "<br/><br/>此致" + "<br/><br/>" + "Gredoor 团队敬上"
		+"<br/><br/>" +"-------------------------------------------------------------------------------------------------------------------------------"
		+"<br/>"+"本电子邮件地址只作通知用途，不接收回信，因此请勿回复本邮件。";
	
//	private String mail_body = "邀请您加入 GreDoor！"
//		+ "<br/><br/>"
//		+ "还在为对着电脑不知道上哪的尴尬发愁吗？ 怕跟不上社会热点潮流最新信息吗？ 新闻、音乐、视频、购物、微博一网打尽"
//		+ "<br/><br/>"
//		+ "GreDoor 团队邀请您使用我们的导航信息网站。"
//		+ "<br/><br/>"
//		+ "网站访问地址：http://www.gredoor.com"
//		+ "<br/><br/>"
//		+ "备用访问地址：http://1.gredoor.sinaapp.com"
//		+ "<br/><br/>" + "<img src='http://1.gredoor.sinaapp.com/img/preview.png' />" + "<br/><br/>此致" + "<br/><br/>" + "GreDoor 团队敬上";
	
	private String personalName = "Gredoor 团队";

	public Boolean sendEmai(EmailService emailService, String subject,
			String message) {
		SimpleEmail simpleEmail = new SimpleEmail();
		
		simpleEmail.setHostName(emailService.getHOST());// 设置发送主机的服务器地址
		try {
			
			simpleEmail.addTo(emailService.getTo(), emailService.getTo()
					.substring(0, (emailService.getTo().indexOf("@"))));// 设置收件人邮箱，第一个参数是收件人名，第二个参数是昵称
			
			simpleEmail.setFrom(emailService.getFROM(), personalName);// 发件人邮箱,第一个参数是发件人名，第二个参数是昵称
			
			simpleEmail.setAuthentication(emailService.getUSERNAME(),
					emailService.getPASSWORD());// 身份验证
			
			simpleEmail.setSubject(subject);// 设置邮件的主题
			
			//simpleEmail.setMsg(message);// 邮件正文消息
			
			simpleEmail.setContent(mail_body, "text/html;charset=UTF-8");
			simpleEmail.setCharset("UTF-8");// 设置邮件的编码方式
			
			simpleEmail.send();
			return true;
		} catch (EmailException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public static String getHOST() {
		return HOST;
	}

	public static String getFROM() {
		return FROM;
	}

	public static String getUSERNAME() {
		return USERNAME;
	}

	public static String getPASSWORD() {
		return PASSWORD;
	}
	
	public static String parse(String line) {
		String str = "";
		if (line != null && line.length() > 0) {
			// 邮箱正则表达式;
			String regexExpression = "[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+";
			Pattern pattern = Pattern.compile(regexExpression);
			Matcher matcher = pattern.matcher(line);
			try {
				while (matcher.find()) {
					String str2 = matcher.group();
					if (!str2.equals("")) {
						str += str2 + " ";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}
}