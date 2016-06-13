package com.npc.lottery.manage.entity;

import java.io.Serializable;
import java.util.Date;

import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.MemberStaff;

public class ShopsDeclaratton implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -4962622464101236530L;
    
    private Long ID;
    private String contentInfo;
    private Date startDate;
    private Date endDate;
    private String type;
    private String fontColor;
    private String remark;
    private Long createUser;
    private Date createDate;
    private String shopsCode;
    private String managerMessageStatus; //  全部人:0/ 管理层:1/  分公司:3  /股东:4/  总代理:5/  代理:6/  会员:9   总监:2/总监只用在总管发布信息时
    private String toWhoChs; //中文名稱
    private String popupMenus;
    private String memberMessageStatus; 
    public static final String SHOPS_DECLARATTON_TYPE_DEFAULT = "0";//默认
    public static final String SHOPS_DECLARATTON_TYPE_CHRIF = "1";//
    public static final String SHOPS_DECLARATTON_NO_SHOW = "0";
    public static final String SHOPS_DECLARATTON_SHOW = "1";
    
    public String getManagerMessageStatus() {
        return managerMessageStatus;
    }
    public void setManagerMessageStatus(String managerMessageStatus) {
        this.managerMessageStatus = managerMessageStatus;
    }
    public String getMemberMessageStatus() {
        return memberMessageStatus;
    }
    public String getToWhoChs() {
    	//  所有人:0    /管理层:1/  分公司:3  /股东:4/  总代理:5/  代理:6/  会员:9   总监:2/总监只用在总管发布信息时
    	if(ManagerStaff.USER_TYPE_SYS.equals(managerMessageStatus)){  //這里的sys是指全部人
    		toWhoChs = "所有人";
    	}else if(ManagerStaff.USER_TYPE_BRANCH.equals(managerMessageStatus)){
    		toWhoChs = "分公司";
    	}else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(managerMessageStatus)){
    		toWhoChs = "總代理";
    	}else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(managerMessageStatus)){
    		toWhoChs = "股東";
    	}else if(ManagerStaff.USER_TYPE_AGENT.equals(managerMessageStatus)){
    		toWhoChs = "代理";
    	}else if(MemberStaff.USER_TYPE_MEMBER.equals(managerMessageStatus)){
    		toWhoChs = "會員";
    	}else{
    		toWhoChs = "總監";
    	}
		return toWhoChs;
	}
	public void setMemberMessageStatus(String memberMessageStatus) {
        this.memberMessageStatus = memberMessageStatus;
    }
    public String getPopupMenus() {
        return popupMenus;
    }
    public void setPopupMenus(String popupMenus) {
        this.popupMenus = popupMenus;
    }
    
    public String getShopsCode() {
        return shopsCode;
    }
    public void setShopsCode(String shopsCode) {
        this.shopsCode = shopsCode;
    }
    public Long getID() {
        return ID;
    }
    public void setID(Long iD) {
        ID = iD;
    }
    public String getContentInfo() {
        return contentInfo;
    }
    public void setContentInfo(String contentInfo) {
        this.contentInfo = contentInfo;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getFontColor() {
        return fontColor;
    }
    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Long getCreateUser() {
        return createUser;
    }
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}
