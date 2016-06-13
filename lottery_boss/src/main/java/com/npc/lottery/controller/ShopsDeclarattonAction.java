package com.npc.lottery.controller;

import java.util.Date;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.util.Page;
import com.npc.lottery.manage.entity.ShopsDeclaratton;
import com.npc.lottery.manage.logic.interf.IShopsDeclarattonLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.user.entity.SubAccountInfo;

public class ShopsDeclarattonAction extends BaseLotteryAction {
	private static Logger log = Logger.getLogger(ShopsDeclarattonAction.class);
	/**
     * 
     */
	private IShopsDeclarattonLogic shopsDeclarattonLogic;
	private static final long serialVersionUID = 7565811354357323207L;
	private String type = "privateAdmin";
	private ShopsDeclaratton shopsDeclaratton;
	private SubAccountInfo subAccountInfo;
	private IAuthorizLogic authorizLogic;
	private String replenishment = null;// 补货
	private String offLineAccount = null;// 下线账号管理
	private String subAccount = null;// 子账号管理
	private String crossReport = null;// 总监交收报表
	private String classifyReport = null;// 总监分类报表

	private String sitemeshType = ""; // 用于页面菜单显示

	/**
	 * 总管查看
	 * 
	 * @return
	 */
	public String queryManagerMessage() {
		Page<ShopsDeclaratton> page = new Page<ShopsDeclaratton>(10);
		int pageNo = 1;

		if (this.getRequest().getParameter("pageNo") != null)
			pageNo = this.findParamInt("pageNo");
		page.setPageNo(pageNo);
		page.setOrderBy("createDate");
		page.setOrder("desc");
		Page<ShopsDeclaratton> pages = shopsDeclarattonLogic.findShopsPage(page);
		this.getRequest().setAttribute("page", pages);
		return SUCCESS;
	}

	// 删除
	public String deleteMessage() {
		String shopID = getRequest().getParameter("shopID");
		ShopsDeclaratton declaratton = new ShopsDeclaratton();
		long idShop = Long.valueOf(shopID);
		declaratton = shopsDeclarattonLogic.queryByShopsDeclaratton("ID", Long.valueOf(idShop));
		if (declaratton != null) {
			shopsDeclarattonLogic.delete(declaratton);
			return SUCCESS;
		} else {
			getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
			getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE,
			        "<font color='" + Constant.COLOR_RED + "'>删除失败</font>");
			return "failure";
		}
	}

	/*
	 * 跳到修改消息页面
	 */
	public String updateFindMessage() {
		String shopID = getRequest().getParameter("shopID");
		long idShop = Long.valueOf(shopID);
		shopsDeclaratton = shopsDeclarattonLogic.queryByShopsDeclaratton("ID", Long.valueOf(idShop));
		return SUCCESS;
	}

	/*
	 * 修改消息
	 */
	public String updateMessage() {

		ShopsDeclaratton declaratton = new ShopsDeclaratton();

		declaratton = shopsDeclarattonLogic.queryByShopsDeclaratton("ID", shopsDeclaratton.getID());
		if (declaratton != null) {
			declaratton.setContentInfo(shopsDeclaratton.getContentInfo());
			declaratton.setManagerMessageStatus(shopsDeclaratton.getManagerMessageStatus());
			declaratton.setMemberMessageStatus(shopsDeclaratton.getMemberMessageStatus());
			declaratton.setPopupMenus(shopsDeclaratton.getPopupMenus());
			shopsDeclarattonLogic.update(declaratton);
		}
		return SUCCESS;

	}

	// 转到消息页面
	public String saveFindMessage() {
		return SUCCESS;
	}

	/**
	 * 总管保存消息
	 * 
	 * @return
	 */
	public String saveMessage() {
		ManagerUser userInfo = (ManagerUser) getRequest().getSession(true).getAttribute(
		        Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		shopsDeclaratton.getContentInfo();
		shopsDeclaratton.setCreateDate(new Date());
		shopsDeclaratton.setCreateUser(userInfo.getID());
		shopsDeclaratton.setType(ShopsDeclaratton.SHOPS_DECLARATTON_TYPE_DEFAULT);
		shopsDeclarattonLogic.saveShopsDeclare(shopsDeclaratton);
		return SUCCESS;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public IShopsDeclarattonLogic getShopsDeclarattonLogic() {
		return shopsDeclarattonLogic;
	}

	public void setShopsDeclarattonLogic(IShopsDeclarattonLogic shopsDeclarattonLogic) {
		this.shopsDeclarattonLogic = shopsDeclarattonLogic;
	}

	public ShopsDeclaratton getShopsDeclaratton() {
		return shopsDeclaratton;
	}

	public void setShopsDeclaratton(ShopsDeclaratton shopsDeclaratton) {
		this.shopsDeclaratton = shopsDeclaratton;
	}

	public SubAccountInfo getSubAccountInfo() {
		return subAccountInfo;
	}

	public void setSubAccountInfo(SubAccountInfo subAccountInfo) {
		this.subAccountInfo = subAccountInfo;
	}

	public IAuthorizLogic getAuthorizLogic() {
		return authorizLogic;
	}

	public void setAuthorizLogic(IAuthorizLogic authorizLogic) {
		this.authorizLogic = authorizLogic;
	}

	public String getReplenishment() {
		return replenishment;
	}

	public void setReplenishment(String replenishment) {
		this.replenishment = replenishment;
	}

	public String getOffLineAccount() {
		return offLineAccount;
	}

	public void setOffLineAccount(String offLineAccount) {
		this.offLineAccount = offLineAccount;
	}

	public String getSubAccount() {
		return subAccount;
	}

	public void setSubAccount(String subAccount) {
		this.subAccount = subAccount;
	}

	public String getCrossReport() {
		return crossReport;
	}

	public String getSitemeshType() {
		return sitemeshType;
	}

	public void setSitemeshType(String sitemeshType) {
		this.sitemeshType = sitemeshType;
	}

	public void setCrossReport(String crossReport) {
		this.crossReport = crossReport;
	}

	public String getClassifyReport() {
		return classifyReport;
	}

	public void setClassifyReport(String classifyReport) {
		this.classifyReport = classifyReport;
	}

}
