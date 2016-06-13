package com.npc.lottery.sysmge.entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 资源实体类，对应资源表（TB_FRAME_RESOURCE）
 * 
 * @author none
 * 
 */
public class Resource implements Serializable {

    public final static String RES_STATE_USE = "0";// 资源状态:启用

    public final static String RES_STATE_UNUSE = "1";// 资源状态：禁用

    public final static String RES_TYPE_PAGE = "0";// 资源类型：页面

    public final static String RES_TYPE_RESOURCE = "1";// 资源类型：资源

    public final static Integer PARENT_RES_ROOT = new Integer(1);// 上级节点：根节点
    
    public final static String URL_SPLIT = ";;";//不同URL之间的分割符

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

    public void setID(Long id) {
        ID = id;
    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public static HashMap getResStateMap(boolean canBlank) {
        HashMap result = new HashMap();
        if (canBlank)
            result.put("", "");
        result.put("0", "启用");
        result.put("1", "禁用");
        return result;
    }

    /**
     *前台资源状态列表显示
     * @return
     */
    public HashMap getResStateMap() {
        return getResStateMap(false);
    }

    /**
     * 获取资源状态中文名
     * @return
     */
    public String getResStateName() {
        String resStateName = "<font color='FF0000'>数据错误</font>";

        if (RES_STATE_USE.equalsIgnoreCase(resState.trim())) {
            resStateName = "启用";
        } else if (RES_STATE_UNUSE.equalsIgnoreCase(resState.trim())) {
            resStateName = "<font color='FF0000'>禁用</font>";
        }
        return resStateName;
    }

    public String getResState() {
        return resState;
    }

    public void setResState(String resState) {
        this.resState = resState;
    }

    /**
     * 前台页面资源类型，下拉列表显示
     * @param canBlank
     * @return
     */
    public static HashMap getResTypeMap(boolean canBlank) {
        HashMap result = new HashMap();
        if (canBlank)
            result.put("", "");
        result.put("0", "页面");
        result.put("1", "资源");
        return result;
    }

    public HashMap getResTypeMap() {
        return getResTypeMap(false);
    }

    public String getResTypeName() {
        String resTypeName = "";
        if (resType.equals(Resource.RES_TYPE_PAGE))
            resTypeName = "页面";
        else if (resType.equals(Resource.RES_TYPE_RESOURCE))
            resTypeName = "资源";
        return resTypeName;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}
