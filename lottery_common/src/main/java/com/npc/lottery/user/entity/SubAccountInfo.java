package com.npc.lottery.user.entity;

import java.io.Serializable;

import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;
import com.npc.lottery.util.SpringUtil;
import com.npc.lottery.util.Tools;

public class SubAccountInfo extends ManagerStaff implements Serializable,Comparable<ManagerStaff>{

    /**
     * 
     */
    private static final long serialVersionUID = 1405419338678336831L;
    private java.lang.Long managerStaffID; 
    private java.lang.Long parentStaff; // 上级用户
    private ManagerStaff managerStaff;  
    private Long stockholderStaff;  //股东用户
    private Long branchStaff;            //分公司用户
    private Long chiefStaff;             //总监用户
    private Long genAgentStaff;            //总代理用户
    private String parentUserType;      //上级类型
    
    
    
    public String getParentUserType() {
        return parentUserType;
    }
    public void setParentUserType(String parentUserType) {
        this.parentUserType = parentUserType;
    }
    public java.lang.Long getManagerStaffID() {
        return managerStaffID;
    }
    public void setManagerStaffID(java.lang.Long managerStaffID) {
        this.managerStaffID = managerStaffID;
    }
   
    
    public java.lang.Long getParentStaff() {
        return parentStaff;
    }
    public void setParentStaff(java.lang.Long parentStaff) {
        this.parentStaff = parentStaff;
    }
    public ManagerStaff getManagerStaff() {
        if(ManagerStaff.USER_TYPE_CHIEF.equals(getParentUserType())){
            IChiefStaffExtLogic chiefStaffExtLogic=(IChiefStaffExtLogic)SpringUtil.getBean("chiefStaffExtLogic"); 
            managerStaff  = (ManagerStaff)chiefStaffExtLogic.queryChiefStaffExt("ID",getParentStaff());
            
        }else if(ManagerStaff.USER_TYPE_BRANCH.equals(getParentUserType())){
            IBranchStaffExtLogic branchStaffExtLogic=(IBranchStaffExtLogic)SpringUtil.getBean("branchStaffExtLogic"); 
            managerStaff  = (ManagerStaff)branchStaffExtLogic.queryBranchStaffExt("ID",getParentStaff());
        }else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(getParentUserType())){
            IStockholderStaffExtLogic stockholderStaffExtLogic=(IStockholderStaffExtLogic)SpringUtil.getBean("stockholderStaffExtLogic");
            managerStaff  = (ManagerStaff)stockholderStaffExtLogic.queryStockholderStaffExt("ID",getParentStaff());
            
        }else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(getParentUserType())){
            IGenAgentStaffExtLogic genAgentStaffExtLogic=(IGenAgentStaffExtLogic)SpringUtil.getBean("genAgentStaffExtLogic");
            managerStaff  = (ManagerStaff)genAgentStaffExtLogic.queryGenAgentStaffExt("ID",getParentStaff());
        }else if(ManagerStaff.USER_TYPE_AGENT.equals(getParentUserType())){
            IAgentStaffExtLogic agentStaffExtLogic=(IAgentStaffExtLogic)SpringUtil.getBean("agentStaffExtLogic");
            managerStaff  = (ManagerStaff)agentStaffExtLogic.queryAgenStaffExt("ID",getParentStaff());
        }
        return managerStaff;
    }
    public void setManagerStaff(ManagerStaff managerStaff) {
        this.managerStaff = managerStaff;
    }
    
    
    public Long getStockholderStaff() {
        return stockholderStaff;
    }
    public void setStockholderStaff(Long stockholderStaff) {
        this.stockholderStaff = stockholderStaff;
    }
    public Long getBranchStaff() {
        return branchStaff;
    }
    public void setBranchStaff(Long branchStaff) {
        this.branchStaff = branchStaff;
    }
    public Long getChiefStaff() {
        return chiefStaff;
    }
    public void setChiefStaff(Long chiefStaff) {
        this.chiefStaff = chiefStaff;
    }
    public Long getGenAgentStaff() {
        return genAgentStaff;
    }
    public void setGenAgentStaff(Long genAgentStaff) {
        this.genAgentStaff = genAgentStaff;
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
