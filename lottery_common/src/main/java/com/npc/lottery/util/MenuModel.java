package com.npc.lottery.util;

import java.util.ArrayList;

import com.npc.lottery.sysmge.entity.Function;

/**
 * TODO 此文件在更换菜单后的实际部署时需要删除
 * 
 * @author none
 */
public class MenuModel implements java.io.Serializable {

	private String id;//菜单ID 

	private String name;//菜单名称

	private String code;//菜单编码

	private String url;//菜单对应的URL

	private String iconPath;//菜单对应的图标

	private String parentID;//父菜单ID

	private Long sortNum;//排序

	private ArrayList<MenuModel> subMenuList = new ArrayList();//子菜单列表

	/**
	 * 默认构造函数
	 *
	 */
	public MenuModel() {

	}

	/**
	 * 根据功能实体，构建菜单对象
	 * 
	 * @param funcEntity
	 */
	public MenuModel(Function funcEntity) {
		this.id = funcEntity.getID().toString();
		this.name = funcEntity.getFuncName();
		this.code = funcEntity.getFuncCode();
		this.url = funcEntity.getFuncUrl();
		this.parentID = funcEntity.getParentFunc().getID().toString();
		this.sortNum = funcEntity.getSortNum();
		this.iconPath = funcEntity.getIconPath();
		if (null == this.sortNum) {
			this.sortNum = new Long(1);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public ArrayList<MenuModel> getSubMenuList() {
		return subMenuList;
	}

	public void setSubMenuList(ArrayList<MenuModel> subMenuList) {
		this.subMenuList = subMenuList;
	}

	public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
	}
}
