package com.fwhere.test;

import java.io.File;

import org.apache.struts.action.ActionForm;

public class UploadImage extends ActionForm {

	private static final long serialVersionUID = 1L;

	private File image;
	private String imageFileName;
	private String imageContentType;
	private int imageHeigth;
	private int imageWidth;
	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(File image) {
		this.image = image;
	}

	/**
	 * @return the image
	 */
	public File getImage() {
		return image;
	}

	/**
	 * @param imageFileName
	 *            the imageFileName to set
	 */
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	/**
	 * @return the imageFileName
	 */
	public String getImageFileName() {
		return imageFileName;
	}

	/**
	 * @param imageContentType
	 *            the imageContentType to set
	 */
	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	/**
	 * @return the imageContentType
	 */
	public String getImageContentType() {
		return imageContentType;
	}

	public String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	/**
	 * @param imageHeigth the imageHeigth to set
	 */
	public void setImageHeigth(int imageHeigth) {
		this.imageHeigth = imageHeigth;
	}

	/**
	 * @return the imageHeigth
	 */
	public int getImageHeigth() {
		return imageHeigth;
	}

	/**
	 * @param imageWidth the imageWidth to set
	 */
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	/**
	 * @return the imageWidth
	 */
	public int getImageWidth() {
		return imageWidth;
	}
}
