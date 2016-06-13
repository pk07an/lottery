package com.npc.lottery.user.entity;

import java.io.Serializable;
import java.util.Set;

import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.util.Tools;

public class BranchStaffExt extends ManagerStaff implements Serializable ,Comparable<ManagerStaff>{

    /**
     * 
     */
    private static final long serialVersionUID = 4478898763197439987L;
    // primary key
    private java.lang.Long managerStaffID;                        //分公司用户基础信息表所对应的记录ID，对应管理用户基础表（TB_FRAME_MANAGER_STAFF）的ID
                                                         //
    // fields                                            //
    private java.lang.Integer parentStaff;               //上级用户
    private java.lang.String replenishment;              //走飞，即补货。取值含义如下：0 — 允许走飞；1 — 禁止走飞
    private java.lang.Integer chiefRate;              //总监占成 
    private java.lang.Integer companyRate;            //公司占成
    private java.lang.Integer totalCreditLine;           //总信用额
    private java.lang.Integer availableCreditLine;       //可用信用额度
    private ChiefStaffExt chiefStaffExt;                 //多个分公司对应一个总监
    private Set<StockholderStaffExt> stockholderStaffExtSet;  //多个股东对应多个分公司
    private Set<MemberStaffExt> memberStaffExtSet;      //多个会员对应一个分公司
    private Set<SubAccountInfo> subAccountInfoSet;// 多个子帳號对应一个分公司
    private java.lang.String leftOwner;           //占余成数归 0，总监 1，分公司
    private java.lang.String openReport;          //是否开放报表 0显示 1，禁止
    private java.lang.Integer defaultCommission;	  //是否默认开户退水  0否 ，1是
    
    public Set<SubAccountInfo> getSubAccountInfoSet() {
        return subAccountInfoSet;
    }

    public void setSubAccountInfoSet(Set<SubAccountInfo> subAccountInfoSet) {
        this.subAccountInfoSet = subAccountInfoSet;
    }

    public Set<MemberStaffExt> getMemberStaffExtSet() {
        return memberStaffExtSet;
    }

    public void setMemberStaffExtSet(Set<MemberStaffExt> memberStaffExtSet) {
        this.memberStaffExtSet = memberStaffExtSet;
    }

    public ChiefStaffExt getChiefStaffExt() {
        return chiefStaffExt;
    }

    public void setChiefStaffExt(ChiefStaffExt chiefStaffExt) {
        this.chiefStaffExt = chiefStaffExt;
    }

    public java.lang.Long getManagerStaffID() {
        return managerStaffID;
    }

    public void setManagerStaffID(java.lang.Long managerStaffID) {
        this.managerStaffID = managerStaffID;
    }

    public java.lang.Integer getParentStaff() {
        return parentStaff;
    }

    public void setParentStaff(java.lang.Integer parentStaff) {
        this.parentStaff = parentStaff;
    }

    public java.lang.String getReplenishment() {
        return replenishment;
    }

    public void setReplenishment(java.lang.String replenishment) {
        this.replenishment = replenishment;
    }
   
    public java.lang.Integer getChiefRate() {
        return chiefRate;
    }

    public void setChiefRate(java.lang.Integer chiefRate) {
        this.chiefRate = chiefRate;
    }

    public java.lang.Integer getCompanyRate() {
        return companyRate;
    }

    public void setCompanyRate(java.lang.Integer companyRate) {
        this.companyRate = companyRate;
    }

    public java.lang.Integer getTotalCreditLine() {
        return totalCreditLine;
    }

    public void setTotalCreditLine(java.lang.Integer totalCreditLine) {
        this.totalCreditLine = totalCreditLine;
    }

    public java.lang.Integer getAvailableCreditLine() {
        return availableCreditLine;
    }

    public void setAvailableCreditLine(java.lang.Integer availableCreditLine) {
        this.availableCreditLine = availableCreditLine;
    }


    public Set<StockholderStaffExt> getStockholderStaffExtSet() {
        return stockholderStaffExtSet;
    }

    public void setStockholderStaffExtSet(
            Set<StockholderStaffExt> stockholderStaffExtSet) {
        this.stockholderStaffExtSet = stockholderStaffExtSet;
    }

    @Override
    public int compareTo(ManagerStaff managerStaff) {
//        if(managerStaff.getCreateDate().before(this.getCreateDate()))
//            return -1;
//       else if (managerStaff.getCreateDate().after(this.getCreateDate()))
//       return 1;
//       else return 0;
    	return this.getAccount().compareTo(managerStaff.getAccount());
    }

	public java.lang.String getLeftOwner() {
		return leftOwner;
	}

	public void setLeftOwner(java.lang.String leftOwner) {
		this.leftOwner = leftOwner;
	}

	public java.lang.String getOpenReport() {
		return openReport;
	}

	public void setOpenReport(java.lang.String openReport) {
		this.openReport = openReport;
	}
	public String getEncodeId() {
		if(managerStaffID!=null)
			return Tools.encodeWithKey(managerStaffID+"");
			else 
				return null;
	}

	public java.lang.Integer getDefaultCommission() {
		return defaultCommission;
	}

	public void setDefaultCommission(java.lang.Integer defaultCommission) {
		this.defaultCommission = defaultCommission;
	}
	
	
}
