package com.fwhere.service;

import java.util.List;

import com.fwhere.beans.Profile;
import com.fwhere.beans.User;

public interface ProfileService {

	public List<Profile> getProfile(int userid) throws Exception;

//	public void updateProfile(Profile profile) throws Exception;
//
//	public void saveProfile(Profile profile) throws Exception;
//
	public void updateUserImg(int userid, String imgPath) throws Exception;

}
