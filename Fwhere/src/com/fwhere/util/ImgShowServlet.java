package com.fwhere.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fwhere.controller.ProfileController;

/**
 * Servlet implementation class ImgShowServlet
 */
public class ImgShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImgShowServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int width = 300, height = 100;
		String imgName = request.getParameter("imgName");
//		BufferedImage image = createImgage(width, height);
		
		String serverPath = ProfileController.STOREPATH + imgName;
//		BufferedImage image = ImageIO.read(new File(serverPath));
		BufferedImage image = ImageIO.read(new File("C:/Temp/head/michael_head.jpg"));
		
		// ByteArrayOutputStream bao=new ByteArrayOutputStream();
		// ImageIO.write(image, Util.JPG, bao);

		IMGUtil.showImage(response, image, IMGUtil.JPG, true);
		// IMGUtil.showImage(response, bao.toByteArray(), true);
		// Util.showImage(response, "d:\\aaa.png", true);
	}

	/**
	 * 生成图片
	 * 
	 * @param width
	 *            图片宽度
	 * @param height
	 *            图片高度
	 * @return
	 */
	private BufferedImage createImgage(int width, int height) {
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		// 以下填充背景颜色
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, width, height);
		// 以下设置前景色
		g.setColor(Color.blue);
//		g.drawString("Http://www.mashupeye.com", 10, 20);
//		g.drawString("Author:Keiven[mashupeye@gmail.com]", 10, 40);
		g.drawLine(10, 50, 290, 50);
		g.drawString("Image Show", 10, 70);
		g.dispose();
		return image;
	}

}
