package com.fwhere.service;

import java.util.List;

import com.fwhere.beans.User;

public interface UserService {
	public List<User> getUser(String username);
	public void addUser(User user);
	
}
