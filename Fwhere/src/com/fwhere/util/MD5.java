package com.fwhere.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.util.Assert;

public class MD5 {
	public static String crypt(String str) {
		Assert.notNull(str);
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] hash = md.digest();

			for (int i = 0; i < hash.length; i++)
				if ((0xFF & hash[i]) < 16)
					hexString.append("0" + Integer.toHexString(0xFF & hash[i]));
				else
					hexString.append(Integer.toHexString(0xFF & hash[i]));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}
}