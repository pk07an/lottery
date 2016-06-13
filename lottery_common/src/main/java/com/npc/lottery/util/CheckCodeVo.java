package com.npc.lottery.util;

import java.awt.image.BufferedImage;

public class CheckCodeVo {
	private String			checkCode;

	private BufferedImage	image;

	public CheckCodeVo(String checkCode, BufferedImage image) {
		super();
		this.checkCode = checkCode;
		this.image = image;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	

}
