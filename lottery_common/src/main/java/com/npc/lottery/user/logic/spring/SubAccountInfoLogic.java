package com.npc.lottery.user.logic.spring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;

import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.user.dao.interf.ISubAccountActionDao;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;
import com.npc.lottery.util.Page;

public class SubAccountInfoLogic implements ISubAccountInfoLogic{

    private ISubAccountActionDao subAccountActionDao;
    private static Logger log = Logger.getLogger(SubAccountInfoLogic.class);
    
    @Override
    public SubAccountInfo querySubAccountInfo(String propertyName, Object value) {
        return subAccountActionDao.findUniqueBy(propertyName, value);
    }

    @Override
    public Page<SubAccountInfo> findPage(Page<SubAccountInfo> page,
            Criterion... criterions) {
        return subAccountActionDao.findPage(page, criterions);
    }

    @Override
    public Page<SubAccountInfo> findPage(Page page, ManagerUser userInfo) {
        return null;
    }

    @Override
    public Long saveSubAccountInfo(SubAccountInfo entity) {
         subAccountActionDao.save(entity);
        return (long) 0;
    }

    @Override
    public void updateSubAccountInfo(SubAccountInfo entity) {
        subAccountActionDao.update(entity);
    }

    public ISubAccountActionDao getSubAccountActionDao() {
        return subAccountActionDao;
    }

    public void setSubAccountActionDao(ISubAccountActionDao subAccountActionDao) {
        this.subAccountActionDao = subAccountActionDao;
    }

	@Override
	public ManagerUser changeSubAccountInfo(ManagerUser userInfoSys) {
		ManagerUser userInfo = new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfo, userInfoSys);
		} catch (Exception e) {
			log.info("SubAccountInfoLogic方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
		
		SubAccountInfo entity = this.querySubAccountInfo("managerStaffID", userInfo.getID());
		if(entity.getParentUserType().equals(ManagerStaff.USER_TYPE_AGENT)){
			userInfo.setUserType(ManagerStaff.USER_TYPE_AGENT);							
			userInfo.setID(entity.getParentStaff());
		}else 
		if(entity.getParentUserType().equals(ManagerStaff.USER_TYPE_GEN_AGENT)){
			userInfo.setUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
			userInfo.setID(entity.getGenAgentStaff());
		}else 
		if(entity.getParentUserType().equals(ManagerStaff.USER_TYPE_STOCKHOLDER)){
			userInfo.setUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
			userInfo.setID(entity.getStockholderStaff());
		}else 
		if(entity.getParentUserType().equals(ManagerStaff.USER_TYPE_BRANCH)){
			userInfo.setUserType(ManagerStaff.USER_TYPE_BRANCH);
			userInfo.setID(entity.getBranchStaff());
		}else 
		if(entity.getParentUserType().equals(ManagerStaff.USER_TYPE_CHIEF)){
			userInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
			userInfo.setID(entity.getChiefStaff());
		}						
		//userInfo.setAccount(entity.getManagerStaff().getAccount());
		return userInfo;
	}
	
	 @Override
	 public void handleReplenishmentByParent(ManagerUser userInfo){
		 
		 Set<SubAccountInfo> set = new HashSet<SubAccountInfo>();
		 if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfo.getUserType())){
			 BranchStaffExt dbBranch =  branchStaffExtLogic.queryBranchStaffExt("account",userInfo.getAccount()); // 判断是否存在相同用户信息
			 set = dbBranch.getSubAccountInfoSet();
		 }
		 if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfo.getUserType())){
			 StockholderStaffExt db =  stockholderStaffExtLogic.queryStockholderStaffExt("account",userInfo.getAccount()); // 判断是否存在相同用户信息
			 set = db.getSubAccountInfoSet();
		 }
		 if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfo.getUserType())){
			 GenAgentStaffExt db =  genAgentStaffExtLogic.queryGenAgentStaffExt("account",userInfo.getAccount()); // 判断是否存在相同用户信息
			 set = db.getSubAccountInfoSet();
		 }
		 if(ManagerStaff.USER_TYPE_AGENT.equals(userInfo.getUserType())){
			 AgentStaffExt db =  agentStaffExtLogic.queryAgenStaffExt("account",userInfo.getAccount()); // 判断是否存在相同用户信息
			 set = db.getSubAccountInfoSet();
		 }
		 
		 if(set!=null){
			 Iterator<SubAccountInfo> it = set.iterator();
			 while(it.hasNext()){
				 SubAccountInfo sub = it.next();
				 //SubAccountInfo subInfo = querySubAccountInfo("managerStaffID",sub.getID());
				 ArrayList<String> authoriz = authorizLogic.findSubRole(sub.getID(),sub.getUserType());
				 for (int i=0;i<authoriz.size();i++) {
					 String authority = authoriz.get(i);
					 if(authority.indexOf(ManagerStaff.SUB_ROLE_REPLENISH)!=-1){
						 authoriz.remove(i);
					 }
				 }
				 authorizLogic.updateSubRole(sub.getID(), sub.getUserType(), authoriz); //保存授权列表
			 }
		 }
	 }
	 
	 private IBranchStaffExtLogic branchStaffExtLogic = null;// 公公司
	 private IGenAgentStaffExtLogic genAgentStaffExtLogic; // 总代理
	 private IAgentStaffExtLogic agentStaffExtLogic; // 代理
	 private IStockholderStaffExtLogic stockholderStaffExtLogic = null;// 股东
	 private IAuthorizLogic authorizLogic;

	public IBranchStaffExtLogic getBranchStaffExtLogic() {
		return branchStaffExtLogic;
	}

	public void setBranchStaffExtLogic(IBranchStaffExtLogic branchStaffExtLogic) {
		this.branchStaffExtLogic = branchStaffExtLogic;
	}

	public IGenAgentStaffExtLogic getGenAgentStaffExtLogic() {
		return genAgentStaffExtLogic;
	}

	public IAgentStaffExtLogic getAgentStaffExtLogic() {
		return agentStaffExtLogic;
	}

	public IStockholderStaffExtLogic getStockholderStaffExtLogic() {
		return stockholderStaffExtLogic;
	}

	public void setGenAgentStaffExtLogic(
			IGenAgentStaffExtLogic genAgentStaffExtLogic) {
		this.genAgentStaffExtLogic = genAgentStaffExtLogic;
	}

	public void setAgentStaffExtLogic(IAgentStaffExtLogic agentStaffExtLogic) {
		this.agentStaffExtLogic = agentStaffExtLogic;
	}

	public void setStockholderStaffExtLogic(
			IStockholderStaffExtLogic stockholderStaffExtLogic) {
		this.stockholderStaffExtLogic = stockholderStaffExtLogic;
	}

	public IAuthorizLogic getAuthorizLogic() {
		return authorizLogic;
	}

	public void setAuthorizLogic(IAuthorizLogic authorizLogic) {
		this.authorizLogic = authorizLogic;
	}

}
