package com.fwhere.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.fwhere.beans.User;
import com.fwhere.service.UserService;
import com.fwhere.util.MD5;

public class UserController extends MultiActionController {

	private UserService userService;

	public ModelAndView home(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = null;
		Boolean isLogin = false;
		Object o = request.getSession().getAttribute("isLogin");
		if (null != o) {
			isLogin = (Boolean) o;
		}
		if (isLogin) {
			mv = new ModelAndView("index.jsp");
		} else {
			mv = new ModelAndView("login.jsp");
		}
		return mv;
	}

	public ModelAndView register(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = null;
		String username = request.getParameter("rusername");
		String password = request.getParameter("rpassword");
		String email = request.getParameter("remail");
		if (null != password && !"".equals(password)) {
			password = MD5.crypt(password);
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		userService.addUser(user);
		// request.setAttribute("user", user);
		mv.addObject("user", user);
		mv = new ModelAndView("registerWait.jsp");
		return mv;
	}

	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("login.jsp");
		// request.setAttribute("user", null);
		// request.setAttribute("isLogin", false);
		request.getSession().setAttribute("user", null);
		request.getSession().setAttribute("isLogin", true);
		return mv;
	}

	public ModelAndView friends(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("friends.jsp");
		return mv;
	}

	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = null;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (null != username && !"".equals(username) && null != password && !"".equals(password)) {
			List<User> users = (List<User>) userService.getUser(username);
			User user = (User) users.get(0);
			password = MD5.crypt(password);
			if (password.equals(user.getPassword())) {
				mv = new ModelAndView("index.jsp");
				request.getSession().setAttribute("user", user);
				request.getSession().setAttribute("isLogin", true);
			} 
		} else {
			mv = new ModelAndView("login.jsp");
		}
		return mv;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
