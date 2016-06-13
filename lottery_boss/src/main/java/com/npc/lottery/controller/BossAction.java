package com.npc.lottery.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.BeanUtils;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.boss.entity.ShopsRent;
import com.npc.lottery.boss.logic.interf.IShopsLogic;
import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.common.Constant;
import com.npc.lottery.util.Page;
import com.npc.lottery.statreport.entity.ReportStatus;
import com.npc.lottery.statreport.logic.interf.IReportStatusLogic;
import com.npc.lottery.util.MD5;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;

public class BossAction extends BaseLotteryAction {

	private static Logger log = Logger.getLogger(BossAction.class);
	private static final long serialVersionUID = 1L;
	private IShopsLogic shopsLogic;
	private ShopsInfo shopsInfo;
	private ShopsRent shopsRent;
	private Integer createUser;
	private String state;
	private String oldCode;
	private String oldName;
	private IChiefStaffExtLogic chiefStaffExtLogic = null;
	private IReportStatusLogic reportStatusLogic;
	private Long userListTreeID;
	private ChiefStaffExt chiefStaffExt;
	private String chiefAccount;
	private String chiefChsName;
	private String computReportType = "N";
	private static final String STATUS_OK = "0";
	private static final String STATUS_NO = "1";

	private ShopSchemeService shopSchemeService;
	
	public ShopSchemeService getShopSchemeService() {
		return shopSchemeService;
	}

	public void setShopSchemeService(ShopSchemeService shopSchemeService) {
		this.shopSchemeService = shopSchemeService;
	}

	// 总后台登录
	public String bossLogin() {

		return SUCCESS;

	}

	// 商铺注册
	public String shopRegister() {
		getRequest().setAttribute("opStatus", "add");
		return SUCCESS;
	}

	/**
	 * @商铺注册
	 */
	public String shopRegisterSubmit() {
		ChiefStaffExt chiefStaffExt = new ChiefStaffExt();
		// 校验商铺信息注册
		ShopsInfo searchShopsInfo = new ShopsInfo();
		Date currentDate = new Date(System.currentTimeMillis());
		ManagerUser userInfo = (ManagerUser) getRequest().getSession(true).getAttribute(
		        Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		Long userID = userInfo.getID();
		String vCode;
		String vName;
		searchShopsInfo = shopsLogic.findShopsCode(shopsInfo.getShopsCode());
		if (searchShopsInfo == null) {
			vCode = STATUS_OK;
		} else {
			vCode = STATUS_NO;
		}
		searchShopsInfo = shopsLogic.findShopsName(shopsInfo.getShopsName());
		if (searchShopsInfo == null) {
			vName = STATUS_OK;
		} else {
			vName = STATUS_NO;
		}
		// 校验总监帐号注册信息
		ChiefStaffExt staffExt = new ChiefStaffExt();
		try {
			staffExt = chiefStaffExtLogic.queryChiefStaffExt("account", chiefAccount);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("执行" + this.getClass().getSimpleName() + "中的方法chiefStaffRegister时出现错误 " + e.getMessage());
		}

		if (staffExt == null && shopsRent.getExpityTime().compareTo(currentDate) == 1
		        && shopsRent.getExpityTime().compareTo(shopsRent.getExpityWarningTime()) == 1) {
			if (vCode.equals(STATUS_OK) && vName.equals(STATUS_OK)) {
				// 准备商铺注册信息
				shopsInfo.setState(Constant.SHOPS_IN_USE);
				shopsInfo.setCreateTime(currentDate);
				shopsInfo.setCreateUser(userID);
				Set<ShopsRent> shopRentSet = new HashSet<ShopsRent>();
				shopsRent.setExpityWarningTime(shopsRent.getExpityWarningTime()); // 租约到期前的提醒时间
				/*
				 * 修改人
				 */
				shopsRent.setLastModifyUser(userID);
				shopsRent.setLastModifyDate(currentDate);
				shopsRent.setRemark("");// 新注冊商铺时此字段只留空
				shopsRent.setShopsInfo(shopsInfo);
				shopRentSet.add(shopsRent);
				shopsInfo.setShopsRent(shopRentSet);
				// 准备商铺注册信息完毕

				// 准备总监注册信息
				chiefStaffExt.setCreateDate(new Date());
				MD5 md5 = new MD5();
				String userPwdOrignMd5 = md5.getMD5ofStr(chiefAccount).trim();// 新建商铺时默认密码为：总监的登录帐号
				chiefStaffExt.setAccount(chiefAccount); // 总监的登录帐号
				chiefStaffExt.setChsName(chiefChsName); // 总监的中文名
				chiefStaffExt.setUserPwd(userPwdOrignMd5);
				chiefStaffExt.setShopsCode(shopsInfo.getShopsCode());
				chiefStaffExt.setFlag(ManagerStaff.FLAG_USE);
				chiefStaffExt.setUserType(ManagerStaff.USER_TYPE_CHIEF);
				ChiefStaffExt chiefStaff = new ChiefStaffExt();
				BeanUtils.copyProperties(chiefStaffExt, chiefStaff);
				// 处理总监注册信息完毕

				try {
					shopsLogic.saveShopsRegister(shopsInfo, chiefStaff);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("执行" + this.getClass().getSimpleName() + "中的方法saveShopsRegister时出现错误 " + e.getMessage());
				}

			} else {
				return INPUT;
			}
		}
		return SUCCESS;
	}

	// 商铺信息管理
	public String shopManager() {
		Page<ShopsInfo> page = new Page<ShopsInfo>(10);
		int pageNo = 1;
		try {
			//查询shecme
			Map<String,String> map=shopSchemeService.getShopSchemeMap();
			if (this.getRequest().getParameter("pageNo") != null)
				pageNo = this.findParamInt("pageNo");
			
			Page<ShopsInfo> pages = new Page<ShopsInfo>();
			pages.setResult(shopsLogic.findShopsAll(map));
			this.getRequest().setAttribute("page", pages);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}

	// 根据商铺号查询商铺信息
	public String queryShop() {
		String code = getRequest().getParameter("code");
		String scheme=shopSchemeService.getSchemeByShopCode(code);
		shopsInfo = shopsLogic.findShopsInfoByCode(code);
		getRequest().setAttribute("opStatus", "query");

		ReportStatus reportStatus = reportStatusLogic.findReportStatus(scheme);
		computReportType = reportStatus.getOpt();
		getRequest().setAttribute("computeStatus", reportStatus.getStatus());

		return SUCCESS;
	}

	// 保存修改过的商铺的信息
	public String modifyShop() {
		ShopsInfo searchShopsInfo = new ShopsInfo();
		Date currentDate = new Date(System.currentTimeMillis());
		String vName = STATUS_OK;
		String vCode = STATUS_OK;
		ManagerUser userInfo = (ManagerUser) getRequest().getSession().getAttribute(
		        Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		Long userID = userInfo.getID();
		String scheme=shopSchemeService.getSchemeByShopCode(shopsInfo.getShopsCode());
		// 把shopsInfo和shopsRent两个ID查出来，用于更新，为了安全，所以不在JSP页面上存HIDDEN值。
		searchShopsInfo = shopsLogic.findShopsInfoByCode(shopsInfo.getShopsCode());
		if (searchShopsInfo == null) {
			vCode = STATUS_NO;
		}
		// 这里的校验其实在JSP页面已经校验，在这里再校验是为了保险，多一层防护
		if (!oldName.equals(searchShopsInfo.getShopsName())) {
			vName = STATUS_NO;
		}
		if (shopsRent.getExpityTime().compareTo(currentDate) == 1
		        && shopsRent.getExpityTime().compareTo(shopsRent.getExpityWarningTime()) == 1) {
			if (vCode.equals(STATUS_OK) && vName.equals(STATUS_OK)) {
				searchShopsInfo.setShopsCode(shopsInfo.getShopsCode());
				searchShopsInfo.setShopsName(shopsInfo.getShopsName());
				searchShopsInfo.setCreateTime(currentDate);
				searchShopsInfo.setRemark(shopsInfo.getRemark());
				searchShopsInfo.setState(Constant.SHOPS_IN_USE);
				searchShopsInfo.setCreateUser(userID);
				searchShopsInfo.setCss(shopsInfo.getCss());
				searchShopsInfo.setState(shopsInfo.getState());
				/*
				 * searchShopsInfo.setSendMailAccount(shopsInfo.getSendMailAccount
				 * ());
				 * searchShopsInfo.setSendMailPassword(shopsInfo.getSendMailPassword
				 * ());
				 * searchShopsInfo.setSendMailSMTP(shopsInfo.getSendMailSMTP());
				 * searchShopsInfo
				 * .setSendMailAddress(shopsInfo.getSendMailAddress());
				 * searchShopsInfo
				 * .setGetMailAddress1(shopsInfo.getGetMailAddress1());
				 * searchShopsInfo
				 * .setGetMailAddress2(shopsInfo.getGetMailAddress2());
				 * searchShopsInfo
				 * .setGetMailAddress3(shopsInfo.getGetMailAddress3());
				 */

				shopsRent.setExpityWarningTime(shopsRent.getExpityWarningTime()); // 租约到期前的提醒时间
				shopsRent.setID(searchShopsInfo.getShopsRentID());
				Set<ShopsRent> shopRentSet = new HashSet<ShopsRent>();
				shopRentSet.add(shopsRent);
				/*
				 * 修改人
				 */
				shopsRent.setLastModifyUser(userID);
				shopsRent.setLastModifyDate(currentDate);
				shopsRent.setRemark("");// 新注冊商铺时此字段只留空
				shopsRent.setShopsInfo(searchShopsInfo);

				searchShopsInfo.setShopsRent(shopRentSet);
				// add by peter 设置是否允许总监删除注单
				searchShopsInfo.setEnableBetDelete(shopsInfo.getEnableBetDelete()==null ? "N" : shopsInfo.getEnableBetDelete());
				// add by peter 设置是否允许总监注销注单
				searchShopsInfo.setEnableBetCancel(shopsInfo.getEnableBetCancel());
				try {
					shopsLogic.update(searchShopsInfo);
					ReportStatus reportStatus = reportStatusLogic.findReportStatus(scheme);
					reportStatus.setOpt(computReportType);
					reportStatusLogic.updateReportStatusByOpt(reportStatus.getOpt(),scheme);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				return INPUT;
			}
		}
		return SUCCESS;
	}

	/**
	 * 功能信息查看：列表功能
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewTreeList() throws Exception {
		String shopsCode;
		if (getRequest().getParameter("code") != null) {
			shopsCode = getRequest().getParameter("code");
		} else {
			shopsCode = "";
		}
		Object[] parameter = new Object[] { ManagerStaff.USER_TYPE_CHIEF, shopsCode };
		List<ChiefStaffExt> resultList = (ArrayList<ChiefStaffExt>) chiefStaffExtLogic.queryChiefByShops(parameter);
		getRequest().setAttribute("listChiefSE", resultList);

		return SUCCESS;
	}

	/**
	 * 功能信息编辑：列表功能
	 * 
	 * @return
	 * @throws Exception
	 */
	public String modifyTreeList() throws Exception {

		// 调用 viewTreeList 方法
		this.viewTreeList();

		getRequest().setAttribute("viewTreeID", userListTreeID);

		return "modifyTreeList";
	}

	public String goToUserList() {

		getRequest().setAttribute("code", getRequest().getParameter("code"));

		return SUCCESS;
	}

	public String checkPetEntry() {
		getRequest().setAttribute("code", getRequest().getParameter("code"));
		return SUCCESS;
	}

	public IShopsLogic getShopsLogic() {
		return shopsLogic;
	}

	public void setShopsLogic(IShopsLogic shopsLogic) {
		this.shopsLogic = shopsLogic;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ShopsInfo getShopsInfo() {
		return shopsInfo;
	}

	public void setShopsInfo(ShopsInfo shopsInfo) {
		this.shopsInfo = shopsInfo;
	}

	public ShopsRent getShopsRent() {
		return shopsRent;
	}

	public void setShopsRent(ShopsRent shopsRent) {
		this.shopsRent = shopsRent;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public IChiefStaffExtLogic getChiefStaffExtLogic() {
		return chiefStaffExtLogic;
	}

	public void setChiefStaffExtLogic(IChiefStaffExtLogic chiefStaffExtLogic) {
		this.chiefStaffExtLogic = chiefStaffExtLogic;
	}

	public IReportStatusLogic getReportStatusLogic() {
		return reportStatusLogic;
	}

	public void setReportStatusLogic(IReportStatusLogic reportStatusLogic) {
		this.reportStatusLogic = reportStatusLogic;
	}

	public String getChiefAccount() {
		return chiefAccount;
	}

	public String getChiefChsName() {
		return chiefChsName;
	}

	public void setChiefAccount(String chiefAccount) {
		this.chiefAccount = chiefAccount;
	}

	public void setChiefChsName(String chiefChsName) {
		this.chiefChsName = chiefChsName;
	}

	public String getComputReportType() {
		return computReportType;
	}

	public void setComputReportType(String computReportType) {
		this.computReportType = computReportType;
	}

	public Long getUserListTreeID() {
		return userListTreeID;
	}

	public void setUserListTreeID(Long userListTreeID) {
		this.userListTreeID = userListTreeID;
	}

	public ChiefStaffExt getChiefStaffExt() {
		return chiefStaffExt;
	}

	public void setChiefStaffExt(ChiefStaffExt chiefStaffExt) {
		this.chiefStaffExt = chiefStaffExt;
	}

}
