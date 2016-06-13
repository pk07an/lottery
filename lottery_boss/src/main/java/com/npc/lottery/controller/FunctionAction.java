package com.npc.lottery.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.npc.lottery.common.action.BaseAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.ConstantBusiness;
import com.npc.lottery.sysmge.entity.Function;
import com.npc.lottery.sysmge.logic.interf.IFunctionLogic;

public class FunctionAction extends BaseAction {

    private static Logger log = Logger.getLogger(FunctionAction.class);

    private IFunctionLogic functionLogic = null;

    public void setFunctionLogic(IFunctionLogic functionLogic) {
        this.functionLogic = functionLogic;
    }

    /**
     * 功能信息查看：主界面
     * 
     * @return
     * @throws Exception
     */
    public String viewMain() throws Exception {
        log.info("viewMain");

        return "viewMain";
    }

    /**
     * 功能信息查看：导航信息页面
     * 
     * @return
     * @throws Exception
     */
    public String viewLocation() throws Exception {
        log.info("viewLocation");

        return "viewLocation";
    }

    /**
     * 功能信息查看：列表功能
     * 
     * @return
     * @throws Exception
     */
    public String viewTreeList() throws Exception {
        log.info("viewTreeList");

        // 查询所有功能信息
        ArrayList resultList = (ArrayList) functionLogic.findAllFunction();

        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("resultList", resultList);

        return "viewTreeList";
    }

    /**
     * 功能信息查看：查看功能详细信息
     * 
     * @return
     * @throws Exception
     */
    public String viewDetail() throws Exception {
        log.info("viewDetail");

        Function function = functionLogic.findFunctionByID(ID);
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("function", function);

        return "viewDetail";
    }

    /**
     * 功能信息编辑：主界面
     * 
     * @return
     * @throws Exception
     */
    public String modifyMain() throws Exception {
        log.info("modifyMain");

        return "modifyMain";
    }

    /**
     * 功能信息编辑：导航信息页面
     * 
     * @return
     * @throws Exception
     */
    public String modifyLocation() throws Exception {
        log.info("modifyLocation");

        return "modifyLocation";
    }

    /**
     * 功能信息编辑：列表功能
     * 
     * @return
     * @throws Exception
     */
    public String modifyTreeList() throws Exception {
        log.info("modifyTreeList");

        HttpServletRequest request = ServletActionContext.getRequest();

        // 调用 viewTreeList 方法
        this.viewTreeList();

        request.setAttribute("viewTreeID", ID);

        return "modifyTreeList";
    }

    /**
     * 功能信息编辑：查看功能详细信息
     * 
     * @return
     * @throws Exception
     */
    public String modifyDetail() throws Exception {
        log.info("modifyDetail");

        // 调用 viewDetail 方法
        this.viewDetail();

        return "modifyDetail";
    }

    /**
     * 功能信息编辑：修改功能信息
     * 
     * @return
     * @throws Exception
     */
    public String modifyInfo() throws Exception {
        log.info("modifyInfo");
        this.viewDetail();
        return "modifyInfo";
    }

    /**
     * 功能信息编辑：新增同级功能
     * 
     * @return
     * @throws Exception
     */
    public String addSame() throws Exception {
        log.info("addSame");
        this.viewDetail();

        return "addSame";
    }

    /**
     * 新增 提交处理
     * 
     * @return
     * @throws Exception
     */

    public String submitAddSame() throws Exception {
        log.info("submitAddSame");
        Function function = this.receiveFunction();

        functionLogic.saveOrUpdate(function);

        HttpServletRequest request = ServletActionContext.getRequest();// 获取request对象
        // 保存提示信息
        request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN, "true");
        request.setAttribute(Constant.INFO_PAGE_RETURN_URL,
                "/sysmge/function/modifyDetail.action?ID=" + function.getID());
        request.setAttribute(Constant.BACK_TREE_PAGE_RETURN,
                "/sysmge/function/modifyTreeList.action?ID=" + function.getID());
        request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                + Constant.COLOR_GREEN + "'>新增节点成功！</font>");
        return "submitAddSame";
    }

    /**
     * 功能信息编辑：新增下级功能
     * 
     * @return
     * @throws Exception
     */
    public String addNext() throws Exception {
        log.info("addNext");
        this.viewDetail();
        return "addNext";
    }

    /**
     * 修改节点内容，接收来自前台内容
     * 
     * @return
     */
    public Function receiveFunction() {
        Function function = new Function();
        function.setID(ID);
        function.setFuncCode(funcCode);
        function.setFuncState(funcState);
        function.setFuncName(funcName);
        // 根据id查询
        Function parentFunc = functionLogic.findFunctionByID(parentFuncID);
        function.setParentFunc(parentFunc);
        function.setFuncUrl(funcUrl);
        function.setSortNum(sortNum);
        function.setFuncDesc(funcDesc);
        function.setIconPath(iconPath);
        return function;
    }

    /**
     * 信息修改
     * 
     * @return
     */
    public String functionSave() {
        //
        Function function = receiveFunction();
        //
        HttpServletRequest request = ServletActionContext.getRequest();// 获取request对象

        // 保存提示信息
        request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN, "true");
        if (ID == ConstantBusiness.FUNCTION_ROOT_ID) {
            request.setAttribute(Constant.INFO_PAGE_RETURN_URL,
                    "/sysmge/function/modifyDetail.action?ID=" + ID);
            request.setAttribute(
                    Constant.BACK_TREE_PAGE_RETURN,
                    "/sysmge/function/modifyTreeList.action?ID="
                            + function.getID());
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_GREEN + "'>操作失败,根目录不能修改！</font>");
        } else {
            request.setAttribute(Constant.INFO_PAGE_RETURN_URL,
                    "/sysmge/function/modifyDetail.action?ID=" + ID);
            request.setAttribute(
                    Constant.BACK_TREE_PAGE_RETURN,
                    "/sysmge/function/modifyTreeList.action?ID="
                            + function.getID());
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_GREEN + "'>数据修改成功！</font>");
            functionLogic.saveOrUpdate(function);
        }
        return "functionSave";
    }

    /**
     * 删除一个节点
     * 
     * @return
     */
    public String del() {
        HttpServletRequest request = ServletActionContext.getRequest();// 获取request对象
        Function func = functionLogic.findFunctionByID(ID);
        request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN, "true");
        if (functionLogic.getChildNodeByID(ID).size() > 0) {
            // 保存提示信息
            request.setAttribute(Constant.INFO_PAGE_RETURN_URL,
                    "/sysmge/function/modifyDetail.action?ID=" + ID);
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_GREEN + "'>操作失败：数据删除失败,有子节点！</font>");
            return "failure";
        } else if (ID == ConstantBusiness.FUNCTION_ROOT_ID) {
            // 保存提示信息
            request.setAttribute(Constant.INFO_PAGE_RETURN_URL,
                    "/sysmge/function/modifyDetail.action?ID=" + ID);
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_GREEN + "'>操作失败：不能删除根节点！</font>");
            return "failure";
        } else if (func.getFuncState().equals(Function.STATE_USE)) {
            request.setAttribute(Constant.INFO_PAGE_RETURN_URL,
                    "/sysmge/function/modifyDetail.action?ID=" + ID);
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_GREEN + "'>操作失败:功能信息类型处于启动状态,不能删除！</font>");
            return "failure";
        } else {
            // 保存提示信息
            request.setAttribute(Constant.INFO_PAGE_RETURN_URL,
                    "/sysmge/function/modifyDetail.action?ID=" + 1);
            request.setAttribute(Constant.BACK_TREE_PAGE_RETURN,
                    "/sysmge/function/modifyTreeList.action");
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_GREEN + "'>数据删除成功！</font>");
            Function funcObj = functionLogic.findFunctionByID(ID);
            //Function funcObj = new Function();
            funcObj.setID(ID);
            String resFuncSQL = "DELETE FROM TB_FRAME_RES_FUNC WHERE func_id ="
                    + ID;

            functionLogic.deleteFunction(funcObj);
            return "del";
        }
    }

    private Long ID;

    private String funcCode;// 功能代码

    private String funcState;// 功能状态

    private String funcName;// 功能名称

    private String funcUrl;// 功能首页

    private Long sortNum;// 

    private Long parentFuncID;// 上级节点

    private String funcDesc;// 功能描述

    private String iconPath;//图标路径

    public void setID(Long id) {
        ID = id;
    }

    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }

    public String getFuncState() {
        return funcState;
    }

    public void setFuncState(String funcState) {
        this.funcState = funcState;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncUrl() {
        return funcUrl;
    }

    public void setFuncUrl(String funcUrl) {
        this.funcUrl = funcUrl;
    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public String getFuncDesc() {
        return funcDesc;
    }

    public void setFuncDesc(String funcDesc) {
        this.funcDesc = funcDesc;
    }

    public IFunctionLogic getFunctionLogic() {
        return functionLogic;
    }

    public Long getID() {
        return ID;
    }

    public Long getParentFuncID() {
        return parentFuncID;
    }

    public void setParentFuncID(Long parentFuncID) {
        this.parentFuncID = parentFuncID;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

}
