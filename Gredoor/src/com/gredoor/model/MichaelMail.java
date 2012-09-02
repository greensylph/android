package com.gredoor.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MichaelMail {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EmailService mservice = new EmailService();
		try {
			String srcpath = "C:/TDDownload/tianya/3.txt";
			String mailaddress = "";
//			String mailaddress = "381369593@qq.com";
			FileReader fr;
			try {
				fr = new FileReader(srcpath);
				BufferedReader br = new BufferedReader(fr); // 缓冲指定文件的输入
				int count = 1;
				while (br.ready()) {
					mailaddress = br.readLine();// 读取一行
					if (!mailaddress.trim().equals("")) {
						mailaddress = parse(mailaddress);
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
