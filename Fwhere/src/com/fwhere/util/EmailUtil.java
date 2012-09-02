package com.fwhere.util;

import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 发送普通邮件，接受普通邮件 发送带有附件的邮件，接收带有附件的邮件 发送html形式的邮件，接受html形式的邮件 发送带有图片的邮件等做了一个总结。
 */
public class EmailUtil {
	private String rusername = "";

	private String rpassword = "";

	private Boolean isSucceed = false;
	// 邮箱服务器
	private String host = "smtp.live.com";
	// 这个是你的邮箱用户名
	private String username = "jasonzqc@hotmail.com";
	// 你的邮箱密码
	private String password = "jasonjason";

	private String mailHeadName = "This is head of this mail";

	private String mailHeadValue = "This is head of this mail";

	private String destMail = "";

	private String srcMail = "jasonzqc@hotmail.com";

	private String mailSubject = "注册验证 Register confirm";

	private String mailBody = "";

	private String personalName = "Fwhere";

	public EmailUtil() {
	}

	/**
	 * 此段代码用来发送普通电子邮件
	 */
	public void send() throws Exception {
		try {
			// 获取系统环境
			Properties props = new Properties();
			// 进行邮件服务器用户认证
			Authenticator auth = new Email_Autherticator();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props, auth);
			// 设置session,和邮件服务器进行通讯。
			MimeMessage message = new MimeMessage(session);
			// 设置邮件格式
			// message.setContent("foobar, "application/x-foobar");
			// 设置邮件主题
			message.setSubject(mailSubject);
			mailBody = "You're in!\n\r"
					+ "I'm excited to invite you to join Fwhere, a social catalog. I can't wait to have you join our little community.\n\r"
					+ "To create your Fwhere account, click the link below and push the blue \"FB Connect\" button. You can also join with Twitter.\n\r"
					+ "http://localhost:8080/Fwhere/regConfirm.jsp?username="
					+ rusername
					+ "&password="
					+ rpassword + "&status=true"
					+ "\n\r"
					+ "Be Nice!\n\r"
					+ "If you have any questions, we'd love to hear from you. Email us at hi@fwhere.com or follow us on twitter @Fwhere. We also have an iPhone app.\n\r"
					+ "Happy pinning!" + "- Michael & the Fwhere Team";
			// 设置邮件正文
			message.setText(mailBody);
			// 设置邮件标题
			message.setHeader(mailHeadName, mailHeadValue);
			// 设置邮件发送日期
			message.setSentDate(new Date());
			Address address = new InternetAddress(srcMail, personalName);
			// 设置邮件发送者的地址
			message.setFrom(address);
			// 设置邮件接收方的地址
			Address toAddress = new InternetAddress(destMail);
			message.addRecipient(Message.RecipientType.TO, toAddress);
			// 发送邮件
			Transport.send(message);
			System.out.println("send ok!");
			isSucceed = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			// throw new Exception(ex.getMessage());
		}
	}

	/**
	 * 用来进行服务器对用户的认证
	 */
	public class Email_Autherticator extends Authenticator {
		public Email_Autherticator() {
			super();
		}

		public Email_Autherticator(String user, String pwd) {
			super();
			username = user;
			password = pwd;
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}

	public String getRusername() {
		return rusername;
	}

	public void setRusername(String rusername) {
		this.rusername = rusername;
	}

	public String getRpassword() {
		return rpassword;
	}

	public void setRpassword(String rpassword) {
		this.rpassword = rpassword;
	}

	public Boolean getIsSucceed() {
		return isSucceed;
	}

	public void setIsSucceed(Boolean isSucceed) {
		this.isSucceed = isSucceed;
	}

	public String getDestMail() {
		return destMail;
	}

	public void setDestMail(String destMail) {
		this.destMail = destMail;
	}

	// public static void main(String[] args) {
	// EMAILUtil sendmail = new EMAILUtil();
	// try {
	// sendmail.send();
	// } catch (Exception x) {
	// x.printStackTrace();
	// }
	// }

}