package com.fwhere.util;

import java.util.Date;

/**
 * csdn post
 * 
 * @author mingyuan
 * 
 */
public class CSDNPost {
	/**
	 * 博文创建日期
	 */
	private Date dateCreated;
	/**
	 * 博文内容
	 */
	private String description;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 博文分类
	 */
	private String[] categories;

	public CSDNPost() {

	}

	public CSDNPost(String title, String description, String[] categories,
			Date dateCreated) {
		this.dateCreated = dateCreated;
		this.description = description;
		this.title = title;
		this.categories = categories;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}
}