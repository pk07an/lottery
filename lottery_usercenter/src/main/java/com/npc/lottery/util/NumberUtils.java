package com.npc.lottery.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

public class NumberUtils {
	private int						width						= 60;
	private int						height						= 20;
	private static final int		NUM							= 4;

	private static final char[]		SEQ							= { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static final List<File>	CHECKCODEIMAGELIST_GREEN	= new ArrayList<File>();
	static {
		try {
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-1.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-2.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-3.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-4.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-5.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-6.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-7.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-8.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-9.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-10.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-11.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-12.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-13.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-14.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-15.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-16.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-17.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-18.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-19.jpg")));
			CHECKCODEIMAGELIST_GREEN.add(FileUtils.toFile(new URL(NumberUtils.class.getResource("") + "checkcode"+File.separator+"green" + File.separator + "code-20.jpg")));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public CheckCodeVo generate(boolean isMember) throws Exception {
		Random r = new Random();
		// 图片的内存映像
		BufferedImage image = null;
		image = ImageIO.read(CHECKCODEIMAGELIST_GREEN.get(r.nextInt(CHECKCODEIMAGELIST_GREEN.size())));

		height = image.getHeight();
		width = image.getWidth();
		// 获得画笔对象
		Graphics g = image.getGraphics();

		// 用于存储随机生成的验证码
		StringBuffer number = new StringBuffer();

		// 绘制验证码
		for (int i = 0; i < NUM; i++) {
			g.setColor(randomColor(r));
			int size = (int) ((height * 90 / 100));
			int h = (int) ((height * 80 / 100));
			g.setFont(new Font("Times New Roman", Font.BOLD, size));
			String ch = String.valueOf(SEQ[r.nextInt(SEQ.length)]);
			number.append(ch);
			g.drawString(ch, i * width / NUM * 90 / 100 + 8, h);
		}

		// 绘制干扰线
		// for (int i = 0; i <= 2; i++) {
		// g.setColor(disturbColor());
		// g.drawLine(r.nextInt(WIDTH), r.nextInt(HEIGHT), r.nextInt(WIDTH), r.nextInt(HEIGHT));
		//
		// }
		g.dispose();
		return new CheckCodeVo(number.toString(), image);

	}

	private Color randomColor(Random r) {
		// return new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
		return new Color(255, 165, 0);
	}

	private Color disturbColor() {
		return new Color(255, 165, 0);
	}

	private Color imgBackgroundColor() {
		return new Color(0, 0, 0);
	}

}
