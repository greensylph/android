package com.gredoor.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class TXT2XLS {

	public static void main(String[] args) {
		writeExcel();
	}

	/**
	 * 生成一个Excel文件
	 * 
	 * @param fileName
	 *            要生成的Excel文件名
	 */
	String fileName = "C:/opt/tmp/tianya_1.xls";

	public static void writeExcel() {
		try {
			// 创建Excel工作表 指定名称和位置
			OutputStream os = new FileOutputStream("C:/opt/tmp/tianya_1.xls");
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			WritableSheet ws = null;
			// **************往工作表中添加数据*****************
			// 1.添加Label(列,行,内容)对象 .列，行----都从0开始

			try {
				String srcpath = "C:/opt/tmp/1.txt";
				String mailaddress = "";
				FileReader fr;
				try {
					fr = new FileReader(srcpath);
					BufferedReader br = new BufferedReader(fr); // 缓冲指定文件的输入
					int row = 1;
					int count = 1;
					Label label = null;
					Label label2 = null;
					boolean isConti = true;
					boolean sweepSheet = true;
					while (br.ready()) {
							if(sweepSheet) {
								ws = wwb.createSheet("Sheet" + count, 0);
								sweepSheet = false;
							}
							mailaddress = br.readLine();// 读取一行
							if (!mailaddress.trim().equals("")) {
								mailaddress = parse(mailaddress);
								if (!mailaddress.trim().equals("")) {
									label = new Label(0, row, "friend" + row);
									label2 = new Label(1, row, mailaddress);
									ws.addCell(label);
									ws.addCell(label2);
									// 写入工作表
									System.out.println("Add contact -> " + row);
								}
							}
							row += 1;
							if (row > 60000) {
								count += 1;
								sweepSheet = true;
								break;
							}

						}
					br.close();
					br.close();
					fr.close();
					wwb.write();
					wwb.close();
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

		} catch (Exception e) {
			e.printStackTrace();
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
