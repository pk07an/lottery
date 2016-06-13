package com.npc.lottery.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.npc.lottery.common.action.BaseAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.sysmge.entity.Function;
import com.npc.lottery.sysmge.entity.Resource;
import com.npc.lottery.sysmge.logic.interf.IFunctionLogic;
import com.npc.lottery.sysmge.logic.interf.IResourceLogic;

/**
 * 资源管理
 * 
 * @author 
 *
 */
public class ResourceAction extends BaseAction {

    private static Logger log = Logger.getLogger(ResourceAction.class);

    private IResourceLogic resourceLogic = null;

    private IFunctionLogic functionLogic = null;

    public void setResourceLogic(IResourceLogic resourceLogic) {
        this.resourceLogic = resourceLogic;
    }

    public void setFunctionLogic(IFunctionLogic functionLogic) {
        this.functionLogic = functionLogic;
    }

    /**
     * 新增资源信息
     * 
     * @return
     * @throws Exception
     */
    public String addResource() throws Exception {
        log.info("addResource");

        String functionID = request.getParameter("functionID");

        if (null == function) {
            function = new Function();
        }
        function.setID(new Long(functionID));

        request.setAttribute("resStateMap",
                new Resource().getResStateMap(false));

        return "addResource";
    }

    /**
     * 查看资源信息
     * 
     * @return
     * @throws Exception
     */
    public String viewResource() throws Exception {
        log.info("viewResource");

        Resource entity = resourceLogic.findByID(ID);

        request.setAttribute("resource", entity);

        return "viewResource";
    }

    /**
     * 保存资源信息
     * 
     * @return
     * @throws Exception
     */
    public String saveResource() throws Exception {

        //查询对应的function
        Function functionEntity = functionLogic.findFunctionByID(function
                .getID());

        Resource resourceEntity = new Resource();
        //保存数据
        resourceEntity.setResCode(resCode);
        resourceEntity.setResName(resName);
        resourceEntity.setResState(resState);
        resourceEntity.setResType(resType);
        resourceEntity.setFunction(functionEntity);
        resourceEntity.setUrl(url);
        resourceEntity.setResDesc(resDesc);

        resourceLogic.saveOrUpdate(resourceEntity);
        ID = resourceEntity.getID();

        return "saveResource";
    }

    /**
     * 编辑资源信息
     * 
     * @return
     * @throws Exception
     */
    public String modifyResource() throws Exception {

        Resource resource = resourceLogic.findByID(ID);

        request.setAttribute("resource", resource);
        request.setAttribute("resStateMap",
                new Resource().getResStateMap(false));

        return "modifyResource";
    }

    /**
     * 保存编辑资源信息
     * 
     * @return
     * @throws Exception
     */
    public String saveModifyResource() throws Exception {

        Resource resourceEntity = new Resource();
        //保存数据
        resourceEntity.setID(ID);
        resourceEntity.setResCode(resCode);
        resourceEntity.setResName(resName);
        resourceEntity.setResState(resState);
        resourceEntity.setResType(resType);
        resourceEntity.setUrl(url);
        resourceEntity.setResDesc(resDesc);

        resourceLogic.saveOrUpdate(resourceEntity);
        ID = resourceEntity.getID();

        return "saveModifyResource";
    }

    /**
     * 删除资源
     * 
     * @return
     */
    public String delResource() {
        log.info("delResource");

        HttpServletRequest request = ServletActionContext.getRequest();

        Resource resource = resourceLogic.findByID(ID);
        resourceLogic.delete(resource);

        request.setAttribute(Constant.INFO_PAGE_TYPE_CLOSE, "true");
        request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                + Constant.COLOR_GREEN + "'>数据删除成功.</font>");

        return "delResource";
    }

    private Long ID;

    private String resCode;

    private String resName;

    private String resState;

    private String resType;

    private Long sortNum;

    // private Long parentRes;
    private Function function;

    private String url;

    private String resDesc;

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResState() {
        return resState;
    }

    public void setResState(String resState) {
        this.resState = resState;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public IResourceLogic getResourceLogic() {
        return resourceLogic;
    }
}
