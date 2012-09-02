package com.fwhere.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.ActionSupport;

import com.fwhere.beans.User;
import com.fwhere.service.UserService;
import com.fwhere.web.LoginForm;

public class UserAction extends ActionSupport {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserService userService = (UserService) getWebApplicationContext()
				.getBean("userServiceImpl");
		List<User> users = new ArrayList<User>();
		User user = new User();
		LoginForm loginForm = (LoginForm) form;
		String username = "";
		String password = "";
		username = loginForm.getUsername();
		password = loginForm.getPassword();
		users = userService.getUser(username);
		user = (User) users.get(0);

		if (password.equals(user.getPassword())) {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			request.getSession().setAttribute("logon", true);
			request.getSession().setAttribute("user", user);
			return mapping.findForward("loginSuccess");
		}
		return mapping.findForward("loginFailed");
	}

}
