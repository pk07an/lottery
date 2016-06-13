package com.npc.lottery.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.npc.lottery.common.action.BaseAction;
import com.npc.lottery.common.ConditionData;
import com.npc.lottery.common.Constant;
import com.npc.lottery.sysmge.entity.Org;
import com.npc.lottery.sysmge.entity.User;
import com.npc.lottery.sysmge.logic.interf.IOrgLogic;
import com.npc.lottery.sysmge.logic.interf.IUserLogic;
import com.npc.lottery.util.Tool;

/**
 * 组织机构 Action
 *
 * @author none
 *
 */
public class OrgAction extends BaseAction {

	private static Logger log = Logger.getLogger(OrgAction.class);

	private IOrgLogic orgLogic = null;//逻辑处理类

	private IUserLogic userLogic = null;//获取用户相关信息

	public void setOrgLogic(IOrgLogic orgLogic) {
		this.orgLogic = orgLogic;
	}

	public void setUserLogic(IUserLogic userLogic) {
		this.userLogic = userLogic;
	}

	/**
	 * 机构信息查看：主界面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewMain() throws Exception {
		log.info("viewMain");

		return "viewMain";
	}

	/**
	 * 机构信息查看：导航信息页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewLocation() throws Exception {
		log.info("viewLocation");

		return "viewLocation";
	}

	/**
	 * 机构信息查看：列表功能
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewTreeList() throws Exception {
		log.info("viewTreeList");

		//查询所有功能信息
		//TODO 暂时一次性取出所有的数据，以后如果出现性能问题或者需要优化的时候，此处的代码需要修改
		ArrayList resultList = (ArrayList) orgLogic.findAllOrg();

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("resultList", resultList);

		return "viewTreeList";
	}

	/**
	 * 机构信息查看：查看详细信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewDetail() throws Exception {
		log.info("viewDetail");
		HttpServletRequest request = ServletActionContext.getRequest();
		//获取请求的页码
		String pageCurrentNoStr = Tool.getParameter(request, "_pagecount", "1");
		int pageCurrentNo = 1;
		try {
			pageCurrentNo = Integer.parseInt(pageCurrentNoStr);
		} catch (Exception ex) {

		}
		//机构信息
		Org org = orgLogic.findOrgByID(ID);
		Org orgParent = orgLogic.findOrgByID(org.getUporgID());
		if (orgParent != null)
			org.setUpOrgName(orgParent.getOrgName());
		//用户信息
		//构造查询条件
		ConditionData conditionData = new ConditionData();
		conditionData.addEqual("userOrgID", org.getOrgID());

		//获取记录总数
		long recordAmount = userLogic.findAmountUserList(conditionData);
		log.info("记录总数：" + recordAmount);
		long sumPages = (recordAmount - 1) / Constant.LIST_PAGE_SIZE + 1;

		//查询记录列表
		ArrayList<User> userList = (ArrayList<User>) userLogic.findUserList(
				conditionData, pageCurrentNo, Constant.LIST_PAGE_SIZE);

		request.setAttribute("sumPages", sumPages + "");
		request.setAttribute("recordAmount", recordAmount + "");
		request.setAttribute("resultList", userList);
		request.setAttribute("org", org);

		return "viewDetail";
	}

	public String viewUserinfo() {
		log.info("viewUserinfo");
		HttpServletRequest request = ServletActionContext.getRequest();
		//用户信息
		//构造查询条件
		ConditionData conditionData = new ConditionData();
		conditionData.addEqual("userID", userID);
		//查询
		User user = userLogic.findUserByID(userID);
		request.setAttribute("user", user);
		return "viewUserinfo";
	}

	/**
	 * 选择机构（单选）：主界面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String selectMain() throws Exception {
		log.info("selectMain");

		return "selectMain";
	}

	/**
	 * 选择机构（单选）：列表功能
	 * 
	 * @return
	 * @throws Exception
	 */
	public String selectTreeList() throws Exception {
		log.info("selectTreeList");

		//查询所有功能信息
		ArrayList resultList = (ArrayList) orgLogic.findAllOrg();

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("resultList", resultList);

		return "selectTreeList";
	}

	/**
	 * 选择机构（多选）：主界面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String selectMultiMain() throws Exception {
		log.info("selectMultiMain");

		return "selectMultiMain";
	}

	/**
	 * 选择机构（多选）：列表功能
	 * 
	 * @return
	 * @throws Exception
	 */
	public String selectMultiTreeList() throws Exception {
		log.info("selectMultiTreeList");

		this.selectTreeList();

		return "selectMultiTreeList";
	}

	private Long ID;//机构id号

	private Long userID;//员工ID号

	public Long getID() {
		return ID;
	}

	public void setID(Long id) {
		ID = id;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}
}
