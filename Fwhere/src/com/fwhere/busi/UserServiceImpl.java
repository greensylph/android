package com.fwhere.busi;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.fwhere.beans.User;
import com.fwhere.service.UserService;

public class UserServiceImpl extends HibernateDaoSupport implements UserService {

	@SuppressWarnings("unchecked")
	public List<User> getUser(String username) {
		return (List<User>) getHibernateTemplate().find(
				"from User where username = ?", username);
	}

	public void addUser(User user) {
		getHibernateTemplate().save(user);
		return;
	}

	public void updateUserImg(User user) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(user);
		
	}
}
