package com.npc.lottery.sysmge.logic.spring;

import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IOrgDao;
import com.npc.lottery.sysmge.entity.Org;
import com.npc.lottery.sysmge.entity.Staff;
import com.npc.lottery.sysmge.entity.User;
import com.npc.lottery.sysmge.logic.interf.IOrgLogic;
import com.npc.lottery.sysmge.logic.interf.IStaffLogic;
import com.npc.lottery.sysmge.logic.interf.IUserLogic;

/**
 * 用户信息的逻辑处理类
 * 
 * 此类涉及系统用户相关的业务逻辑，包括人员信息（Staff）、人员权限等
 * 
 * @author none
 *
 */
public class UserLogic implements IUserLogic {

	private IStaffLogic staffLogic = null;//人员信息逻辑处理类

	private IOrgDao orgDao = null;//根据机构id获取机构名称

	private IOrgLogic orgLogic = null;//机构逻辑处理类

	public void setOrgLogic(IOrgLogic orgLogic) {
		this.orgLogic = orgLogic;
	}

	public void setStaffLogic(IStaffLogic staffLogic) {
		this.staffLogic = staffLogic;
	}

	public void setOrgDao(IOrgDao orgDao) {
		this.orgDao = orgDao;
	}

	/**
	 * 根据ID查询人员信息
	 * 
	 * @param userID
	 *            人员信息ID
	 * @return	数据不存在则返回 null
	 */
	public User findUserByID(Long userID) {
		Staff entity = staffLogic.findStaffByID(userID);//查询 staff

		if (null == entity) {
			return null;
		}

		User user = new User(entity);//根据 staff 构造 user

		return user;
	}

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param userName  用户名
	 * @return  数据不存在则返回 null
	 */
	public User findUserByName(String userName) {

		//判断查询条件是否为空
		if (null == userName) {
			return null;
		}

		ConditionData conditionData = new ConditionData();
		conditionData.addEqual("account", userName);

		List<User> userList = this.findUserList(conditionData, 1, 10);
		if (null == userList || 0 == userList.size()) {
			return null;
		}

		return userList.get(0);
	}

	/**
	 * 统计满足指定查询条件的人员信息记录数目
	 * 
	 * @param conditionData
	 *            查询条件信息       
	 * @return
	 */
	public long findAmountUserList(ConditionData conditionData) {
		return staffLogic.findAmountStaffList(conditionData);
	}

	/**
	 * 保存信息
	 * 
	 * @param entity    待保存的信息
	 * @return  此信息记录所对应的ID，Long类型
	 */
	public Long saveStaff(Staff entity) {
		return staffLogic.saveStaff(entity);
	}

	/**
	 * 查询满足指定查询条件的人员信息数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息   
	 * @param pageCurrentNo
	 *            第一页为 1 需要查询的页码
	 * @param pageSize
	 *            页面大小
	 * @return User 类型的 List
	 */
	public List<User> findUserList(ConditionData conditionData,
			int pageCurrentNo, int pageSize) {
		List<User> resultList = null;

		//增加人员显示顺序的排序条件
		if (null == conditionData) {
			conditionData = new ConditionData();
		}
		conditionData.addOrder("ID", ConditionData.ORDER_TYPE_ASC);
		//调用staffLogic方法
		List staffList = staffLogic.findStaffList(conditionData, pageCurrentNo,
				pageSize);

		if (null != staffList) {
			resultList = new ArrayList<User>(staffList.size());
			//将 staffList 中的数据填充到结果信息列表中
			for (int i = 0; i < staffList.size(); i++) {
				Staff staff = (Staff) staffList.get(i);
				String userOrgName = null;
				Org org = (Org) orgDao.getOrgByID(staff.getOrgID());
				if (org != null)
					userOrgName = org.getOrgName();
				resultList.add(new User(staff, userOrgName));
			}
		}

		return resultList;
	}

	/**
	 * 查询所有的机构信息
	 * 
	 * @return Org 类型的 List
	 */
	public ArrayList<Org> findAllOrg() {

		//直接调用 OrgLogic 的方法
		return orgLogic.findAllOrg();
	}

	/**
	 * 更新信息
	 * 
	 * @param entity 待更新的信息
	 */
	public void update(Staff entity) {
		staffLogic.update(entity);
	}
}
