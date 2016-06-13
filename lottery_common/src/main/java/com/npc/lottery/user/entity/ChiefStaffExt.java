package com.npc.lottery.user.entity;

import java.io.Serializable;
import java.util.Set;

import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.util.Tools;

public class ChiefStaffExt extends ManagerStaff implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1926872784620574947L;
    // primary key
    private java.lang.Long managerStaffID; // 总监用户基础信息表所对应的记录ID，对应管理用户基础表（TB_FRAME_MANAGER_STAFF）的ID
    private Set<BranchStaffExt> branchStaffExtSet;  //多个分公司对应一个总监
    private String shopsCode;
    private ManagerStaff managerStaff;
    private Set<MemberStaffExt> memberStaffExtSet;      //多个会员对应一个总监    
    private Set<SubAccountInfo> subAccountInfoSet;// 多个子帳號对应一个總監
    
    
    public Set<SubAccountInfo> getSubAccountInfoSet() {
        return subAccountInfoSet;
    }

    public void setSubAccountInfoSet(Set<SubAccountInfo> subAccountInfoSet) {
        this.subAccountInfoSet = subAccountInfoSet;
    }

    public String getShopsCode() {
        return shopsCode;
    }

    public void setShopsCode(String shopsCode) {
        this.shopsCode = shopsCode;
    }

    public java.lang.Long getManagerStaffID() {
        return managerStaffID;
    }

    public void setManagerStaffID(java.lang.Long managerStaffID) {
        this.managerStaffID = managerStaffID;
    }

    public Set<BranchStaffExt> getBranchStaffExtSet() {
        return branchStaffExtSet;
    }

    public void setBranchStaffExtSet(Set<BranchStaffExt> branchStaffExtSet) {
        this.branchStaffExtSet = branchStaffExtSet;
    }

	public ManagerStaff getManagerStaff() {
		return managerStaff;
	}

	public void setManagerStaff(ManagerStaff managerStaff) {
		this.managerStaff = managerStaff;
	}

    public Set<MemberStaffExt> getMemberStaffExtSet() {
        return memberStaffExtSet;
    }

    public void setMemberStaffExtSet(Set<MemberStaffExt> memberStaffExtSet) {
        this.memberStaffExtSet = memberStaffExtSet;
    }
    public String getEncodeId() {
		if(managerStaffID!=null)
			return Tools.encodeWithKey(managerStaffID+"");
			else 
				return null;
	}
}
