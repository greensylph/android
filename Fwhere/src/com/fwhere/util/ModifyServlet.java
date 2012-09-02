package com.fwhere.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fwhere.busi.ProfileServiceImpl;
import com.fwhere.service.ProfileService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ModifyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -731078214471715021L;
	ProfileService profileService = new ProfileServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		/* 
		 * 获得图片名和裁剪后的图片信息:宽,高,裁剪起始坐标 
		 */
		@SuppressWarnings("unused")
		String userid = request.getParameter("userid");
		String x = request.getParameter("x");
		String y = request.getParameter("y");
		String w = request.getParameter("w");
		String h = request.getParameter("h");
		String img = request.getParameter("img");

		int width = Integer.parseInt(w);
		int height = Integer.parseInt(h);
//		int rx = Integer.parseInt(x);
//		int ry = Integer.parseInt(y);

		//文件格式.  
		String ext = img.substring(img.lastIndexOf(".") + 1);
		//文件源地址.  
		String imgsrc = "c:\\upload" + img;
		File srcfile = new File(imgsrc);
		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			//对文件进行比例缩放  
			Image image = ImageIO.read(srcfile);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(
					image.getScaledInstance(width, height, Image.SCALE_SMOOTH),
					0, 0, null);
			FileOutputStream out = new FileOutputStream(srcfile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
			out.close();
			//对文件进行裁剪  
			is = new FileInputStream(srcfile);
			iis = ImageIO.createImageInputStream(is);

			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(ext);
			ImageReader reader = it.next();
			reader.setInput(iis);

			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(Integer.parseInt(x), Integer
					.parseInt(y), 120, 120);
			param.setSourceRegion(rect);

			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, ext, new File("c:\\upload" + img));
			
//			Profile profile = profileService.getProfile(userid);s
			//图片地址更新入数据库用户信息  
//			profileService.updateProfile(profile);
			response.getWriter().write(
					"./upload/" + img);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			if (iis != null)
				iis.close();
		}

	}

}
