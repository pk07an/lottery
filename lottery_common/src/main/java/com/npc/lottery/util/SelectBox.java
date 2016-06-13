package com.npc.lottery.util;

/***
 * 用于页面显示下拉列表框的值
 * @author 朱冬志
 *
 */
public class SelectBox {

	private String key; //下拉列表框的value值
	private String value; //下拉列表框的label值

	public SelectBox(String key, String value) {
		// TODO Auto-generated constructor stub
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
