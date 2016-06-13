package com.npc.lottery.sysmge.entity;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.npc.lottery.util.MenuModel;

/**
 * 用户信息，继承自 Staff
 * 此类中部分属性只有登录时才会填充，故此实体类只用来记录登录人员信息，不建议当做普通的实体类对象使用
 * 
 * 平常使用时，请使用 Staff
 * 
 * @author none
 * 
 */
public class User extends Staff {

	private static Logger log = Logger.getLogger(Staff.class);//日志

	public static final int LOGIN_STATE_SUCCESS_NORMAL = 0;//正常登录成功

	public static final int LOGIN_STATE_FAILURE_USER_INEXIST = 1;//登录失败，用户不存在

	public static final int LOGIN_STATE_FAILURE_PWD_INCORRECT = 2;//登录失败，用户密码不正确

	//角色列表
	private ArrayList<Roles> roleList;

	//菜单，TODO 增加List内容类型的描述
	private ArrayList menuList;

	//菜单为三级（不计根菜单）
	//一级菜单，存放的对象是 com.npc.lottery.util.MenuModel
	private ArrayList<MenuModel> firstMenuList = new ArrayList();

	//二级菜单，存放的对象是 com.npc.lottery.util.MenuModel
	private ArrayList<MenuModel> secondMenuList = new ArrayList();

	//三级菜单，存放的对象是 com.npc.lottery.util.MenuModel
	private ArrayList<MenuModel> thirdMenuList = new ArrayList();

	//登录状态，取值参考常量定义

	private int loginState = -1;

	private String userOrgName;//所属机构

	public User() {

	}

	/**
	 * 根据 staff 初始化用户信息
	 * 
	 * @param staff
	 */
	public User(Staff staff, String userOrgName) {
		this.fillData(staff);
		this.userOrgName = userOrgName;
	}

	public User(Staff staff) {
		this.fillData(staff);
	}

	/**
	 * 根据 staff 填充用户信息
	 * 
	 * @param staff
	 */
	public void fillData(Staff staff) {

		if (null == staff) {
			log.error("用来填充的数据为空！");
			return;
		}
		this.setID(staff.getID());//用户ID
		this.setOrgID(staff.getOrgID());//所属机构
		this.setAccount(staff.getAccount());//登录账号
		this.setFlag(staff.getFlag());//状态
		this.setUserPwd(staff.getUserPwd());//用户密码
		this.setChsName(staff.getChsName());//中文名字
		this.setEngName(staff.getEngName());//英文名字
		this.setEmailAddr(staff.getEmailAddr());//eMail地址
		this.setOfficePhone(staff.getOfficePhone());//办公室电话
		this.setMobilePhone(staff.getMobilePhone());//移动电话
		this.setFax(staff.getFax());//传真
		this.setCreateDate(staff.getCreateDate());//创建时间
		this.setUpdateDate(staff.getUpdateDate());//更新时间
		this.setLoginDate(staff.getLoginDate());//最后登录时间
		this.setLoginIp(staff.getLoginIp());//最后登录IP
		this.setComments(staff.getComments());//备注
	}

	/**
	 * 获取登录状态
	 * 
	 * 0-正常登录成功；1 -用户信息不存在；2-用户密码不正确
	 * 
	 * @return
	 */
	public int getLoginState() {
		return loginState;
	}

	/**
	 * 判断用户是否（成功）登录
	 * 
	 * @return
	 */
	public boolean isLogin() {

		boolean result = false;

		if (LOGIN_STATE_SUCCESS_NORMAL == loginState) {
			result = true;
		}

		return result;
	}

	public void setLoginState(int loginState) {
		this.loginState = loginState;
	}

	public String getUserOrgName() {
		return userOrgName;
	}

	public void setUserOrgName(String userOrgName) {
		this.userOrgName = userOrgName;
	}

	public ArrayList<MenuModel> getFirstMenuList() {
		return firstMenuList;
	}

	public void setFirstMenuList(ArrayList<MenuModel> firstMenuList) {
		this.firstMenuList = firstMenuList;
	}

	public ArrayList<MenuModel> getSecondMenuList() {
		return secondMenuList;
	}

	/**
	 * 查询指定的一级菜单所对应的二级菜单
	 * 
	 * @param firstMenuID
	 * @return
	 */
	public ArrayList<MenuModel> getSecondMenuList(String firstMenuID) {
		if (null == firstMenuID) {
			return null;
		}

		ArrayList<MenuModel> resultList = null;

		//查询对应的一级菜单

		for (int i = 0; i < firstMenuList.size(); i++) {
			if (firstMenuID.equalsIgnoreCase(firstMenuList.get(i).getId())) {
				resultList = firstMenuList.get(i).getSubMenuList();
				break;
			}
		}

		return resultList;
	}

	public void setSecondMenuList(ArrayList<MenuModel> secondMenuList) {
		this.secondMenuList = secondMenuList;
	}

	public ArrayList<MenuModel> getThirdMenuList() {
		return thirdMenuList;
	}

	/**
	 * 查询指定的二级菜单所对应的三级菜单
	 * 
	 * @param secondMenuID
	 * @return
	 */
	public ArrayList<MenuModel> getThirdMenuList(String secondMenuID) {
		if (null == secondMenuID) {
			return null;
		}

		ArrayList<MenuModel> resultList = null;

		//查询对应的一级菜单

		for (int i = 0; i < secondMenuList.size(); i++) {
			if (secondMenuID.equalsIgnoreCase(secondMenuList.get(i).getId())) {
				resultList = secondMenuList.get(i).getSubMenuList();
				break;
			}
		}

		return resultList;
	}

	public void setThirdMenuList(ArrayList<MenuModel> thirdMenuList) {
		this.thirdMenuList = thirdMenuList;
	}

	public ArrayList<Roles> getRoleList() {
		return roleList;
	}

	public void setRoleList(ArrayList<Roles> roleList) {
		this.roleList = roleList;
	}

	/**
	 * 判断是否存在指定的角色（根据角色编码判断）
	 * 
	 * @param roleCode
	 * @return
	 */
	public boolean existRoles(String roleCode) {

		boolean result = false;

		if (null == roleCode || roleCode.trim().length() < 1
				|| null == roleList || roleList.size() < 1) {
			return false;
		}

		for (int i = 0; i < roleList.size(); i++) {
			if (roleCode.trim().equalsIgnoreCase(roleList.get(i).getRoleCode())) {
				result = true;
				break;
			}
		}

		return result;
	}
}
