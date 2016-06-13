package com.npc.lottery.user.entity;

import java.io.Serializable;

import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.util.Tools;

public class OutReplenishStaffExt extends ManagerStaff implements Serializable ,Comparable<ManagerStaff>{

    /**
     * 
     */
    private static final long serialVersionUID = -1359315071675309933L;

 // primary key
    private java.lang.Long managerStaffID;                        //子帐号用户基础信息表所对应的记录ID，对应管理用户基础表（TB_FRAME_MANAGER_STAFF）的ID
                                                         //
    // fields                                            //
    private Integer parentStaff;               //上级用户
    private java.lang.String plate;                  //会员盘口

	public java.lang.Long getManagerStaffID() {
		return managerStaffID;
	}

	public Integer getParentStaff() {
		return parentStaff;
	}

	public void setParentStaff(Integer parentStaff) {
		this.parentStaff = parentStaff;
	}

	public java.lang.String getPlate() {
		return plate;
	}

	public void setManagerStaffID(java.lang.Long managerStaffID) {
		this.managerStaffID = managerStaffID;
	}

	public void setPlate(java.lang.String plate) {
		this.plate = plate;
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
	public String getEncodeId() {
		if(managerStaffID!=null)
			return Tools.encodeWithKey(managerStaffID+"");
			else 
				return null;
	}
}
