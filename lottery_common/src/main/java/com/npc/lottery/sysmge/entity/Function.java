package com.npc.lottery.sysmge.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.ConstantBusiness;
import com.npc.lottery.util.ListEntity;

/**
 * 功能实体类，对应功能表（TB_FRAME_FUNCTION）
 *
 * @author none
 *
 */
public class Function implements Serializable {

    //此处定义业务常量，即对应数据表中相关字段的取值常量
    public static final String STATE_USE = "0";//状态取值：启用

    public static final String STATE_UNUSE = "1";//状态取值：禁用

    public static final String ID_GROUP_SPLIT = "&&";//将多个ID组合成一个字符串时所使用的分割符

    private Long ID;

    private String funcCode;

    private String funcName;

    private String funcState;

    private String funcUrl;

    //private Long parentFunc;
    private Function parentFunc;

    private Long sortNum;

    private String funcDesc;

    private String iconPath;

    private boolean isAuthoriz = false;//是否已经授权

    private boolean isPrivateAuthoriz = false;//是否已经授权给私有角色

    private boolean isLeaf = false;//是否是树叶节点（没有子节点）

    private List resources;

    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }

    public String getFuncDesc() {
        return funcDesc;
    }

    public void setFuncDesc(String funcDesc) {
        this.funcDesc = funcDesc;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncState() {
        return funcState;
    }

    public void setFuncState(String funcState) {
        this.funcState = funcState;
    }

    public String getFuncUrl() {
        return funcUrl;
    }

    public void setFuncUrl(String funcUrl) {
        this.funcUrl = funcUrl;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long id) {
        ID = id;
    }

    //    public Long getParentFunc() {
    //        return parentFunc;
    //    }
    //
    //    public void setParentFunc(Long parentFunc) {
    //        this.parentFunc = parentFunc;
    //    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    /**
     * 根据状态的值返回中文显示名称
     * 
     * @return
     */
    public String getStateName() {
        String stateName = "<font color='FF0000'>数据错误</font>";

        if (STATE_USE.equalsIgnoreCase(funcState.trim())) {
            stateName = "启用";
        } else if (STATE_UNUSE.equalsIgnoreCase(funcState.trim())) {
            stateName = "<font color='FF0000'>禁用</font>";
        }

        return stateName;
    }

    public static HashMap getStateMap(boolean canBlank) {

        HashMap result = new HashMap();

        if (canBlank) {
            result.put(null, null);
        }

        result.put(Function.STATE_UNUSE, "禁用");
        result.put(Function.STATE_USE, "启用");

        return result;
    }

    public HashMap getStateMap() {
        return getStateMap(false);
    }

    /**
     * 获取页面下拉列表中的显示数据
     * 
     * @param canBlank
     * @return
     */
    public static ArrayList<ListEntity> getStateList(boolean canBlank) {

        ArrayList<ListEntity> resultList = new ArrayList<ListEntity>();
        ListEntity entity;

        if (canBlank) {
            entity = new ListEntity();
            entity.setKey("");
            entity.setName("");
            resultList.add(entity);
        }

        entity = new ListEntity();
        entity.setKey(Function.STATE_USE);
        entity.setName("启用");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(Function.STATE_UNUSE);
        entity.setName("禁用");
        resultList.add(entity);

        return resultList;
    }

    public ArrayList<ListEntity> getStateList() {
        return Function.getStateList(false);
    }

    public Function getParentFunc() {
        return parentFunc;
    }

    /**
     * 返回页面上显示树形结构时所需要的父节点ID
     * 
     * @return
     */
    public Long getParentTreeID() {
        //如果是根节点，则返回页面上所需要的父节点ID
        if (ConstantBusiness.FUNCTION_ROOT_ID == ID) {
            return Constant.PAGE_TREE_ROOT_PARENT_ID;
        } else {
            return parentFunc.getID();
        }
    }

    public void setParentFunc(Function parentFunc) {
        this.parentFunc = parentFunc;
    }

    public boolean isAuthoriz() {
        return isAuthoriz;
    }

    public void setAuthoriz(boolean isAuthoriz) {
        this.isAuthoriz = isAuthoriz;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public boolean isPrivateAuthoriz() {
        return isPrivateAuthoriz;
    }

    public void setPrivateAuthoriz(boolean isPrivateAuthoriz) {
        this.isPrivateAuthoriz = isPrivateAuthoriz;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public List getResources() {
        return resources;
    }

    public void setResources(List resources) {
        this.resources = resources;
    }

    /**
     * 排序功能列表数据
     * 按sortNum升序排列
     * 
     * @param functionList
     * @return
     */
    public static ArrayList<Function> sorBySortNum(
            ArrayList<Function> functionList) {

        if (null == functionList || functionList.size() < 2) {
            return functionList;
        }

        ArrayList<Function> resultList = new ArrayList<Function>();

        //排序
        resultList.add(functionList.get(0));//结果列表中放入第一个值
        Function originEntity;
        Function tempEntity;
        Long originSortNum;
        Long tempSortNum;
        for (int i = 1; i < functionList.size(); i++) {

            tempEntity = functionList.get(i);

            for (int m = 0; m < resultList.size(); m++) {
                originEntity = resultList.get(m);
                originSortNum = originEntity.getSortNum();
                tempSortNum = tempEntity.getSortNum();

                //其中之一未设置排序字段
                if (tempSortNum == null) {
                    resultList.add(tempEntity);
                    break;
                } else if (originSortNum == null) {
                    resultList.add(m, tempEntity);
                    break;
                }

                //排序
                if (tempEntity.getSortNum() < originEntity.getSortNum()) {
                    resultList.add(m, tempEntity);
                    break;
                } else if (m == (resultList.size() - 1)) {
                    //如果是最后一次比较，则保存数据
                    resultList.add(tempEntity);
                    break;
                }
            }
        }

        return resultList;
    }
}
