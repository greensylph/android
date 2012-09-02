package com.fwhere.web;

import org.apache.struts.action.ActionForm;

@SuppressWarnings("serial")
public class LoginForm extends ActionForm {
	private String username = null;
	private String password = null;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
