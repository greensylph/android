package com.fwhere.web;

import org.apache.struts.action.ActionForm;

@SuppressWarnings("serial")
public class RegisterForm extends ActionForm {
	
	private String username = null;
	private String password = null;
	private String email = null;
	
	public RegisterForm(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
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
