package com.fwhere.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.fwhere.beans.Profile;
import com.fwhere.beans.User;
import com.fwhere.service.ProfileService;
import com.fwhere.service.UserService;
import com.fwhere.util.MD5;
import com.fwhere.util.MathUtil;

public class ProfileController extends MultiActionController {

	private ProfileService profileService;
	private UserService userService;

	public ProfileService getProfileService() {
		return profileService;
	}

	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public final static String STOREPATH = "C:/Temp/head/";

	//profile修改资料主页
	public ModelAndView profile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("profileContent.jsp");
		Object o = request.getSession().getAttribute("user");
		User user = null;
		int userid = 0;
		if (null != o) {
			user = (User) o;
			userid = user.getId();
		} else {
			mv = new ModelAndView("login.jsp");
			return mv;
		}
		List<Profile> profiles = profileService.getProfile(userid);
		Profile profile = null;
		if (null != profiles && profiles.size() > 0) {
			profile = profiles.get(0);
		}
		request.getSession().setAttribute("profileContent", profile);
		return mv;
	}

	//修改资料文本界面
	public ModelAndView updateContent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("profileContent.jsp");
		Object o = request.getSession().getAttribute("user");
		if (null == o) {
			mv = new ModelAndView("login.jsp");
		}
		return mv;
	}

	@SuppressWarnings("deprecation")
	// 上传图片，并返回给浏览器
	public ModelAndView uploadImg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("profileImg.jsp");
		Object o = request.getSession().getAttribute("user");
		if (null == o) {
			mv = new ModelAndView("login.jsp");
			return mv;
		}

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("imgFile");
		File file = new File(STOREPATH + multipartFile.getOriginalFilename());
		multipartFile.transferTo(file);

		BufferedImage bi = ImageIO.read(file);
		int w = bi.getWidth();
		int h = bi.getHeight();
		BufferedImage out = bi.getSubimage(0, 0, w, h);
		out = resizeImage(out, BufferedImage.TYPE_INT_RGB, bi.getWidth(), bi
				.getHeight());
		ImageIO.write(out, "jpg", file);
		String imgName = multipartFile.getOriginalFilename();
		mv.addObject("imgName", imgName);
		mv.addObject("imgWidth", out.getWidth());
		mv.addObject("imgHeight", out.getHeight());
		mv.addObject("imgName", multipartFile.getOriginalFilename());
		return mv;
	}

	public ModelAndView updateImg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("profileImg.jsp");
		Object o = request.getSession().getAttribute("user");
		if (null == o) {
			mv = new ModelAndView("login.jsp");
		}
		return mv;
	}

	// 保存修剪后的头像
	@SuppressWarnings("deprecation")
	public ModelAndView saveImg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("profileContent.jsp");
		Object o = request.getSession().getAttribute("user");
		User user = null;
		if (null == o) {
			mv = new ModelAndView("login.jsp");
			return mv;
		} else {
			user = (User)o;
		}
		String xStr = request.getParameter("x");
		String yStr = request.getParameter("y");
		String wStr = request.getParameter("w");
		String hStr = request.getParameter("h");
		int x = 0;
		int y = 0;
		int w = 300;
		int h = 300;
		if (null != xStr && !"".equals(xStr)) {
			x = Integer.parseInt(xStr);
		}

		if (null != yStr && !"".equals(yStr)) {
			y = Integer.parseInt(yStr);
		}
		if (null != wStr && !"".equals(wStr)) {
			w = Integer.parseInt(wStr);
		} else {
			w = 300;
		}
		if (null != hStr && !"".equals(hStr)) {
			h = Integer.parseInt(hStr);
		} else {
			h = 300;
		}

		String imgName = request.getParameter("imgName");
		// ServletContext context = getServletContext();
		// String path = context.getRealPath("/") ;
		String serverPath = STOREPATH + imgName;
		String newName = user.getUsername() + "_head" + ".jpg";
		BufferedImage bi = ImageIO.read(new File(serverPath));

		BufferedImage out = null;
		if (w > 0 && h > 0 && bi.getWidth() >= w && bi.getHeight() >= h) {
			out = bi.getSubimage(x, y, w, h);
		} else {
			out = bi.getSubimage(0, 0, bi.getWidth(), bi.getHeight());
		}

		out = resizeImage(out, BufferedImage.TYPE_INT_RGB, 130, 130);
		ImageIO.write(out, "jpg", new File(newName));
		
		int userid = user.getId();
		String imgSrc = "upload/" + imgName + ".jpg";
		profileService.updateUserImg(userid, imgSrc);
		
		response.setHeader("Pragma ", "No-cache ");
		response.setHeader("Cache-Control ", "no-cache ");
		response.setDateHeader("Expires ", 0);
		request.getSession().setAttribute("imgSrc", imgSrc);

		return mv;
	}

	// 对图片大小进行缩放处理
	public BufferedImage resizeImage(BufferedImage originalImage, int type,
			int width, int height) {
		MathUtil mathUtil = new MathUtil();
		Double rate = 0.0;
		if (width < 300 && height < 300) {
			BufferedImage resizedImage = new BufferedImage(width, height, type);
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, width, height, null);
			g.dispose();
			return resizedImage;
		} else {
			rate = mathUtil.divide(new Double(height), new Double(300));
			height = (int) mathUtil.divide(new Double(height), rate);
			width = (int) mathUtil.divide(new Double(width), rate);
		}

		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();

		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}

	
}
