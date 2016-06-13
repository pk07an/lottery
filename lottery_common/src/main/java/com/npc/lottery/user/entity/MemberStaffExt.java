package com.npc.lottery.user.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;
import com.npc.lottery.util.SpringUtil;
import com.npc.lottery.util.Tools;

public class MemberStaffExt extends MemberStaff implements Serializable ,Comparable<MemberStaff>{

    /**
     * 
     */
    private static final long serialVersionUID = -1359315071675309933L;

    // primary key
    private java.lang.Long memberStaffID;  //普通会员用户基础信息表所对应的记录ID，对应会员用户基础表（TB_FRAME_MEMBER_STAFF）的ID

    // fields
    private java.lang.Long parentStaff;           //上级用户
    private java.lang.String plate;                  //会员盘口
    private java.lang.Integer totalCreditLine;       //总信用额
    private java.lang.Integer availableCreditLine;   //可用信用额度
    private java.lang.Integer rate;          //代理占成
    private BigDecimal  backWater;                 //退水设定
    private ManagerStaff managerStaff;            //
    private String memberTypeName;
    private Long stockholderStaff;  //股东用户
    private Long branchStaff;            //分公司用户
    private Long chiefStaff;             //总监用户
    private Long genAgentStaff;            //总监用户
    private Long agentStaff;             //代理用户
    private String parentUserType;      //上级类型
    
    
    
    public String getParentUserType() {
        return parentUserType;
    }

    public void setParentUserType(String parentUserType) {
        this.parentUserType = parentUserType;
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


    public Long getAgentStaff() {
        return agentStaff;
    }


    public void setAgentStaff(Long agentStaff) {
        this.agentStaff = agentStaff;
    }


    public BigDecimal getBackWater() {
        return backWater;
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
            managerStaff  = (ManagerStaff)agentStaffExtLogic.queryAgenStaffExt("managerStaffID",getParentStaff());
        }
        return managerStaff;  
    }


    public void setManagerStaff(ManagerStaff managerStaff) {
        this.managerStaff = managerStaff;
    }


    public void setBackWater(BigDecimal backWater) {
        this.backWater = backWater;
    }

    
    public java.lang.Long getMemberStaffID() {
        return memberStaffID;
    }

    public void setMemberStaffID(java.lang.Long memberStaffID) {
        this.memberStaffID = memberStaffID;
    }


    public java.lang.Long getParentStaff() {
        return parentStaff;
    }

    public void setParentStaff(java.lang.Long parentStaff) {
        this.parentStaff = parentStaff;
    }

    public java.lang.String getPlate() {
        return plate;
    }

    public void setPlate(java.lang.String plate) {
        this.plate = plate;
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
    
    public java.lang.Integer getRate() {
        return rate;
    }

    public void setRate(java.lang.Integer rate) {
        this.rate = rate;
    }
    
    /*
     * 返回会员类型名字
     * memberTypeName
     */
    public String getMemberTypeName(){
        String result ="";
        //String parentUserType = managerStaff.getUserType();
        
        if(ManagerStaff.USER_TYPE_CHIEF.equals(getParentUserType())){
            result="总监直属会员";
        }else if(ManagerStaff.USER_TYPE_BRANCH.equals(getParentUserType())){
            result="分公司直属会员";
        }else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(getParentUserType())){
            result="股东直属会员";
        }else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(getParentUserType())){
            result="总代理直属会员";
        }else if(ManagerStaff.USER_TYPE_AGENT.equals(getParentUserType())){
            result="普通会员";
        }
        return result;
    }

    public void setMemberTypeName(String memberTypeName) {
        this.memberTypeName = memberTypeName;
    }
    
    @Override
    public int compareTo(MemberStaff memberStaff) {
//        if(memberStaff.getCreateDate().before(this.getCreateDate()))
//            return -1;
//       else if (memberStaff.getCreateDate().after(this.getCreateDate()))
//       return 1;
//       else return 0;
    	return this.getAccount().compareTo(memberStaff.getAccount());
    }
   
}
