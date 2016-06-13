package com.npc.lottery.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.npc.lottery.common.action.BaseAction;
import com.npc.lottery.common.ConditionData;
import com.npc.lottery.common.Constant;
import com.npc.lottery.sysmge.entity.Param;
import com.npc.lottery.sysmge.entity.ParamValue;
import com.npc.lottery.sysmge.logic.interf.IParamLogic;
import com.npc.lottery.sysmge.logic.interf.IParamValueLogic;
import com.npc.lottery.util.Tool;

/**
 * 参数类别表 action
 * 
 * 
 */
public class ParamAction extends BaseAction {

	private static Logger log = Logger.getLogger(ParamAction.class);

	private IParamLogic paramLogic = null;

	private IParamValueLogic paramValueLogic = null;

	/**
	 * 
	 * @return
	 */
	public String list() {
		log.info("list");

		HttpServletRequest request = ServletActionContext.getRequest();

		// 获取请求的页码
		String pageCurrentNoStr = Tool.getParameter(request,
				Constant.PAGETAG_CURRENT, "1");
		int pageCurrentNo = 1;
		try {
			pageCurrentNo = Integer.parseInt(pageCurrentNoStr);
		} catch (Exception ex) {

		}

		// 获取记录总数
		long recordAmount = paramLogic.findAmountParam(null);
		log.info("记录总数：" + recordAmount);
		long sumPages = (recordAmount - 1) / Constant.LIST_PAGE_SIZE + 1;

		// 查询记录列表
		List resultList = paramLogic.findParamList(null, pageCurrentNo,
				Constant.LIST_PAGE_SIZE);

		// 保存返回的数据记录集
		request.setAttribute("sumPages", sumPages + "");
		request.setAttribute("recordAmount", recordAmount + "");
		request.setAttribute("pageCurrentNo", pageCurrentNo + "");
		request.setAttribute("resultList", resultList);
		request.setAttribute("stateMap", Param.getStateMap(true));// 状态下拉列表

		return "list";
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	public String addParam() {
		log.info("addParam");
		HttpServletRequest request = ServletActionContext.getRequest();
		Param param = new Param();
		param.setState(Param.VALUE_TYPE_OTHER);
		param.setState(Param.STATE_USE);
		param.setType(Param.TYPE_SYSTEM);
		// request.setAttribute("parentParamMap", getParentParamMap());
		request.setAttribute("param", param);
		return "addParam";
	}

	/**
	 * 获取所有记录,返回map
	 * 
	 * @return
	 */
	public HashMap getParentParamMap() {
		List parentParamList = paramLogic.findParamList(null, 1, 999999);
		HashMap parentParamMap = new HashMap();
		for (int i = 0; i < parentParamList.size(); i++) {
			Param parentParam = (Param) parentParamList.get(i);
			parentParamMap.put(parentParam.getID(), parentParam.getName());
		}
		return parentParamMap;
	}

	/**
	 * 查看详细信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewParam() throws Exception {
		log.info("viewParam");
		// 参数类型表
		HttpServletRequest request = ServletActionContext.getRequest();
		Param param = paramLogic.getParamByID(ID);

		// 构造参数值查询条件
		ConditionData conditionData = new ConditionData();
		conditionData.addEqual("param", param);
		// 查询记录列表，此处无需分页，查询出所有的值信息即可
		List resultList = paramValueLogic.findParamValueList(conditionData, 1,
				999);

		// 保存返回的数据记录集
		request.setAttribute("resultList", resultList);
		String controlShow = "";
		if (param.getType().equals(Param.TYPE_SYSTEM))
			controlShow = "style='display:none'";

		request.setAttribute("controlShow", controlShow);
		request.setAttribute("param", param);

		return "viewParam";
	}

	/**
	 * 角色信息：编辑
	 * 
	 * @return
	 * @throws Exception
	 */
	public String modifyParam() throws Exception {
		log.info("modifyParam");
		HttpServletRequest request = ServletActionContext.getRequest();
		Param param = paramLogic.getParamByID(ID);
		request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN, "true");
		if (param.getType().equals(Param.TYPE_SYSTEM)) {
			request.setAttribute(Constant.INFO_PAGE_RETURN_URL,
					"/sysmge/param/viewParam.action?ID=" + ID);
			request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
					+ Constant.COLOR_GREEN + "'>操作失败,系统初始化参数类型,不能编辑！</font>");
			return "failure";
		}
		viewParam();// 调用查看
		return "modifyParam";

	}

	public String delParam() {
		log.info("delParam");
		HttpServletRequest request = ServletActionContext.getRequest();
		Param param = paramLogic.getParamByID(ID);
		request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN, "true");
		if (param.getType().equals(Param.TYPE_SYSTEM)) {
			request.setAttribute(Constant.INFO_PAGE_RETURN_URL,
					"/sysmge/param/viewParam.action?ID=" + ID);
			request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
					+ Constant.COLOR_GREEN + "'>操作失败:系统初始化参数类型,不能删除！</font>");
			return "failure";
		} else if (param.getState().equals(Param.STATE_USE)) {
			request.setAttribute(Constant.INFO_PAGE_RETURN_URL,
					"/sysmge/param/viewParam.action?ID=" + ID);
			request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
					+ Constant.COLOR_GREEN + "'>操作失败:参数类型处于启动状态,不能删除！</font>");
			return "failure";
		} else {
			request.setAttribute(Constant.INFO_PAGE_RETURN_URL,
					"/sysmge/param/viewParam.action?ID=" + 1);
			request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
					+ Constant.COLOR_GREEN + "'>数据删除成功！</font>");
			paramLogic.delete(param);
			return "delParam";
		}
	}

	/**
	 * 角色信息：查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchParam() throws Exception {
		log.info("searchParam");
		// 查询条件
		log.info("code = " + code + "  name = " + name + " state = " + state
				+ " type =" + type);

		HttpServletRequest request = ServletActionContext.getRequest();

		// 获取请求的页码
		String pageCurrentNoStr = Tool.getParameter(request,
				Constant.PAGETAG_CURRENT, "1");
		int pageCurrentNo = 1;
		try {
			pageCurrentNo = Integer.parseInt(pageCurrentNoStr);
		} catch (Exception ex) {

		}

		// 构造查询条件
		ConditionData conditionData = new ConditionData();
		conditionData.addLike("name", name);// 设置名称的模糊查询条件
		conditionData.addLike("code", code);// 设置编码的模糊查询条件
		// 判断是否需要增加状态查询条件
		if (null != state && 0 != state.trim().length()) {
			conditionData.addEqual("state", state);// 设置状态的查询条件
		}
		if (null != type && 0 != type.trim().length()) {
			conditionData.addEqual("type", type);// 设置类型的查询条件
		}

		// 获取记录总数
		long recordAmount = paramLogic.findAmountParam(conditionData);
		log.info("记录总数：" + recordAmount);
		long sumPages = (recordAmount - 1) / Constant.LIST_PAGE_SIZE + 1;

		// 查询记录列表
		List rolesList = paramLogic.findParamList(conditionData, pageCurrentNo,
				Constant.LIST_PAGE_SIZE);

		// 保存返回的数据记录集
		request.setAttribute("sumPages", sumPages + "");
		request.setAttribute("recordAmount", recordAmount + "");
		request.setAttribute("resultList", rolesList);
		request.setAttribute("stateMap", Param.getStateMap(true));// 状态的页面下拉列表数据
		request.setAttribute("typeMap", Param.getTypeMap(true));
		return "searchParam";
	}

	/**
	 * 来自前台提交数据
	 * 
	 * @return
	 */
	public Param receiveParam() {

		Param param = new Param();
		param.setID(ID);
		param.setRemark(remark);
		param.setCode(code);
		param.setName(name);
		//param.setValueType(valueType);
		//param.setType(type);
		param.setType("2");
		param.setState(state);
		parentParamID = Long.valueOf(-1);
		Param parentParam = paramLogic.getParamByID(parentParamID);
		param.setParentParam(parentParam);
		param.setParamValues(null);
		return param;
	}

	/**
	 * 提交数据至数据库
	 * 
	 * @return
	 * @throws Exception
	 */

	public String saveParam() throws Exception {

		log.info("saveParam");
		Param param = receiveParam();
		paramLogic.saveOrUpdate(param);
		// 保存提示信息
		ID = param.getID();

		return "saveParam";
	}

	/**
	 * 新增参数值
	 */
	public String addParamValue() {
		log.info("addParamValue");
		HttpServletRequest request = ServletActionContext.getRequest();
		Param param = paramLogic.getParamByID(paramValueParamID);

		request.setAttribute(Constant.INFO_PAGE_TYPE_CLOSE, "true");
		if (param.getType().equals(Param.TYPE_SYSTEM)) {
			//			request.setAttribute(Constant.INFO_PAGE_RETURN_URL,
			//					"/sysmge/param/viewParam.action?ID=" + ID);
			request
					.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
							+ Constant.COLOR_GREEN
							+ "'>操作失败:系统初始化参数类型,不能新增参数值！</font>");
			return "failure";
		}
		ParamValue paramValue = new ParamValue();
		request.setAttribute("paramValue", paramValue);
		request.setAttribute("param", param);
		return "addParamValue";
	}

	/**
	 * 查看paramValue
	 * 
	 * @return
	 */
	public String viewParamValue() {
		log.info("viewParamValue");
		HttpServletRequest request = ServletActionContext.getRequest();
		ParamValue paramValue = paramValueLogic.getParamValueByID(paramValueID);
		request.setAttribute("paramValue", paramValue);
		return "viewParamValue";

	}

	public ParamValue receiveParamValue() {
		ParamValue paramValue = new ParamValue();
		paramValue.setID(paramValueID);
		paramValue.setName(name);
		paramValue.setCode(code);
		Param param = paramLogic.getParamByID(paramValueParamID);
		paramValue.setParam(param);
		paramValue.setSortNum(sortNum);
		paramValue.setValue(value);
		paramValue.setRemark(remark);
		return paramValue;
	}

	/**
	 * 编辑ParamValue
	 * 
	 * @return
	 */
	public String modifyParamValue() {
		log.info("mofifyParamValue");
		viewParamValue();
		return "modifyParamValue";
	}

	/**
	 * 删除ParamValue
	 * 
	 * @return
	 */
	public String delParamValue() {
		log.info("delParamValue");

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute(Constant.INFO_PAGE_TYPE_CLOSE, "true");
		ParamValue paramValue = paramValueLogic.getParamValueByID(paramValueID);

		request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
				+ Constant.COLOR_GREEN + "'>数据删除成功.</font>");
		paramValueLogic.delete(paramValue);
		return "delParamValue";
	}

	/**
	 * 提交数据
	 */
	public String saveParamValue() {
		log.info("saveParamValue");

		ParamValue paramValue = null;

		if (null == paramValueID) {
			paramValue = new ParamValue();
			paramValue.setID(paramValueID);
		} else {
			paramValue = paramValueLogic.getParamValueByID(paramValueID);
		}

		paramValue.setName(name);
		paramValue.setCode(code);
		Param param = paramLogic.getParamByID(paramValueParamID);
		paramValue.setParam(param);
		paramValue.setSortNum(sortNum);
		paramValue.setValue(value);
		paramValue.setRemark(remark);

		//ParamValue paramValue = receiveParamValue();
		paramValueLogic.saveOrUpdate(paramValue);
		paramValueID = paramValue.getID();
		return "saveParamValue";
	}

	// Param
	private Long ID;
	private String code;
	private String name;
	private String valueType;
	private Long parentParamID;// 上级节点ID
	private String remark;
	private Long sortNum;
	private String type;
	private String state;
	// ParamValue 
	private Long paramValueID;
	private Long paramValueParamID;
	private String value;

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

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public Long getParentParamID() {
		return parentParamID;
	}

	public void setParentParamID(Long parentParamID) {
		this.parentParamID = parentParamID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getParamValueParamID() {
		return paramValueParamID;
	}

	public void setParamValueParamID(Long paramValueParamID) {
		this.paramValueParamID = paramValueParamID;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public IParamLogic getParamLogic() {
		return paramLogic;
	}

	public IParamValueLogic getParamValueLogic() {
		return paramValueLogic;
	}

	public void setParamLogic(IParamLogic paramLogic) {
		this.paramLogic = paramLogic;
	}

	public void setParamValueLogic(IParamValueLogic paramValueLogic) {
		this.paramValueLogic = paramValueLogic;
	}

	public Long getParamValueID() {
		return paramValueID;
	}

	public void setParamValueID(Long paramValueID) {
		this.paramValueID = paramValueID;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long id) {
		ID = id;
	}

	public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
	}

}
