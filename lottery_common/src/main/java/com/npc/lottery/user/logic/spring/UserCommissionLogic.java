package com.npc.lottery.user.logic.spring;

import java.util.List;

import com.npc.lottery.common.Constant;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.dao.interf.IAgentStaffExtDao;
import com.npc.lottery.user.dao.interf.IBranchStaffExtDao;
import com.npc.lottery.user.dao.interf.IGenAgentStaffExtDao;
import com.npc.lottery.user.dao.interf.IStockholderStaffExtDao;
import com.npc.lottery.user.dao.interf.IUserCommissionDao;
import com.npc.lottery.user.dao.interf.IUserCommissionJDBCDao;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;

import com.npc.lottery.user.vo.UserInfoVO;

public class UserCommissionLogic implements IUserCommissionLogic{

    private IUserCommissionDao userCommissionDao = null;
    private IUserCommissionJDBCDao userCommissionJDBCDao = null;
    private IBranchStaffExtDao branchStaffExtDao = null;
    private IStockholderStaffExtDao stockholderStaffExtDao = null;
    private IGenAgentStaffExtDao genAgentStaffExtDao = null;
    private IAgentStaffExtDao agentStaffExtDao = null;

    public IUserCommissionDao getUserCommissionDao() {
        return userCommissionDao;
    }

    public void setUserCommissionDao(IUserCommissionDao userCommissionDao) {
        this.userCommissionDao = userCommissionDao;
    }

    @Override
    public void saveCommission(UserCommission entity) {
        userCommissionDao.save(entity);
    }

    @Override
    public List<UserCommission> queryCommission(Object value,Object userType) {
        return userCommissionDao.find("from UserCommission where userId = ? and userType = ? order by ID asc", value,userType);
    }

    @Override
    public void updateCommission(UserCommission entity) {
        userCommissionDao.update(entity);
    }

    @Override
    public List<UserCommission> queryAll(String orderBy,boolean isAsc) {
        return userCommissionDao.getAll(orderBy,isAsc);
    }

    public UserCommission queryUserPlayTypeCommission(Long userId,String userType,String typeCode)
    {
    	Object[] values={userId,userType,typeCode};
    	String hql="select userCommission from UserCommission userCommission,PlayType playType where userCommission.userId=? and userCommission.userType=? and playType.typeCode=? and userCommission.playFinalType=playType.commissionType ";

    	return userCommissionDao.findUnique(hql, values);    	
    	
    }
    @Override
    public UserCommission queryUserPlayTypeCommissionByScheme(Long userId,String userType,String typeCode,String scheme){
    	Object[] values={userId,userType,typeCode};
    	UserCommission userCommission=userCommissionDao.queryUserPlayTypeCommission(values, scheme);
    	return userCommission;
    }
    
    @Override
    public List<UserCommission> queryCommissionByType(Object value, Object value2,Object value3) {
        return userCommissionDao.find("from UserCommission where userId=? and userType=? and  play_final_type like ? order by id asc", value,value2,value3);
    }
    
    /**
     * 新方法，使用scheme查询
     */
    public UserInfoVO queryUserInfoByScheme(ManagerUser userInfo,String scheme){
    	UserInfoVO userInfoVO = new UserInfoVO();
        boolean isChief = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_CHIEF);// 总监类型
        boolean isBranch = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
        boolean isStockholder = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_STOCKHOLDER);// 股东
        boolean isGenAgent = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_GEN_AGENT);// 总代理
        boolean isAgent = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_AGENT);// 代理
        if(isChief){
        	userInfoVO.setReplenishment(Constant.ALOW_REPLENISH);
        }
        if(isBranch){
        	BranchStaffExt branchStaffExt = branchStaffExtDao.findById(userInfo.getID(),scheme);   
        	userInfoVO.setParentUserId(branchStaffExt.getParentStaff());
        	userInfoVO.setReplenishment(branchStaffExt.getReplenishment());
        }
        if(isStockholder){
        	StockholderStaffExt stockholderStaffExt = stockholderStaffExtDao.findById(userInfo.getID(),scheme);   
        	userInfoVO.setParentUserId(stockholderStaffExt.getParentStaff());
        	userInfoVO.setReplenishment(stockholderStaffExt.getReplenishment());
        }
        if(isGenAgent){
        	GenAgentStaffExt genAgentStaffExt = genAgentStaffExtDao.findById(userInfo.getID(),scheme);   
        	userInfoVO.setParentUserId(genAgentStaffExt.getParentStaff());
        	userInfoVO.setReplenishment(genAgentStaffExt.getReplenishment());
        }
        if(isAgent){
        	AgentStaffExt agentStaffExt = agentStaffExtDao.findById(userInfo.getID(), scheme);   
        	userInfoVO.setParentUserId(agentStaffExt.getParentStaff());
        	userInfoVO.setReplenishment(agentStaffExt.getReplenishment());
        }

    	return userInfoVO;
    }
    
    
    public UserInfoVO queryUserInfo(ManagerUser userInfo){
    	UserInfoVO userInfoVO = new UserInfoVO();
        boolean isChief = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_CHIEF);// 总监类型
        boolean isBranch = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
        boolean isStockholder = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_STOCKHOLDER);// 股东
        boolean isGenAgent = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_GEN_AGENT);// 总代理
        boolean isAgent = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_AGENT);// 代理
        if(isChief){
        	userInfoVO.setReplenishment(Constant.ALOW_REPLENISH);
        }
        if(isBranch){
        	BranchStaffExt branchStaffExt = branchStaffExtDao.findUniqueBy("ID", userInfo.getID());   
        	userInfoVO.setParentUserId(branchStaffExt.getParentStaff());
        	userInfoVO.setReplenishment(branchStaffExt.getReplenishment());
        }
        if(isStockholder){
        	StockholderStaffExt stockholderStaffExt = stockholderStaffExtDao.findUniqueBy("ID", userInfo.getID());   
        	userInfoVO.setParentUserId(stockholderStaffExt.getParentStaff());
        	userInfoVO.setReplenishment(stockholderStaffExt.getReplenishment());
        }
        if(isGenAgent){
        	GenAgentStaffExt genAgentStaffExt = genAgentStaffExtDao.findUniqueBy("ID", userInfo.getID());   
        	userInfoVO.setParentUserId(genAgentStaffExt.getParentStaff());
        	userInfoVO.setReplenishment(genAgentStaffExt.getReplenishment());
        }
        if(isAgent){
        	AgentStaffExt agentStaffExt = agentStaffExtDao.findUniqueBy("ID", userInfo.getID());   
        	userInfoVO.setParentUserId(agentStaffExt.getParentStaff());
        	userInfoVO.setReplenishment(agentStaffExt.getReplenishment());
        }

    	return userInfoVO;
    	
    }

	public IBranchStaffExtDao getBranchStaffExtDao() {
		return branchStaffExtDao;
	}

	public void setBranchStaffExtDao(IBranchStaffExtDao branchStaffExtDao) {
		this.branchStaffExtDao = branchStaffExtDao;
	}

	public IStockholderStaffExtDao getStockholderStaffExtDao() {
		return stockholderStaffExtDao;
	}

	public IGenAgentStaffExtDao getGenAgentStaffExtDao() {
		return genAgentStaffExtDao;
	}

	public IAgentStaffExtDao getAgentStaffExtDao() {
		return agentStaffExtDao;
	}

	public void setStockholderStaffExtDao(
			IStockholderStaffExtDao stockholderStaffExtDao) {
		this.stockholderStaffExtDao = stockholderStaffExtDao;
	}

	public void setGenAgentStaffExtDao(IGenAgentStaffExtDao genAgentStaffExtDao) {
		this.genAgentStaffExtDao = genAgentStaffExtDao;
	}

	public void setAgentStaffExtDao(IAgentStaffExtDao agentStaffExtDao) {
		this.agentStaffExtDao = agentStaffExtDao;
	}

    @Override
    public void updateCommission(Long userId, String userType) {
        userCommissionDao.updateCommission(userId, userType);
    }
    
   public void updateUserBatchCommission(List<UserCommission> userCommissions,boolean run,boolean saveDefault)
   {
	 
	   
	   userCommissionJDBCDao.batchUpdateUserTreeCommission(userCommissions);
	   if(run)
		   userCommissionJDBCDao.batchUpdateUserCommissionRun(userCommissions);
	   else		   
       userCommissionJDBCDao.batchUpdateUserCommission(userCommissions);
	   
	  
   }
   public void updateBranchBatchCommission(List<UserCommission> userCommissions,boolean run,boolean saveDefault,Long chiefId,Long branchId)
   {
	  long t1=System.currentTimeMillis();
	   userCommissionJDBCDao.batchUpdateUserTreeCommission(userCommissions);
	   long t2=System.currentTimeMillis();
	   if(run)
		   userCommissionJDBCDao.batchUpdateUserCommissionRun(userCommissions);
	   else		   
       userCommissionJDBCDao.batchUpdateUserCommission(userCommissions);
	   long t3=System.currentTimeMillis();
	   //存为“新開户”默认退水
	   if(saveDefault)
	   {
		   //查询改总监下的所有分公司
		   List<BranchStaffExt> branchList=branchStaffExtDao.findBy("parentStaff", chiefId.intValue());
		   for (BranchStaffExt branchStaffExt : branchList) {
			   if(branchStaffExt.getManagerStaffID().equals(branchId)){
				   //设置为 新开户 默认
				   branchStaffExt.setDefaultCommission(1);
			   }else{
				   branchStaffExt.setDefaultCommission(0);
			   }
			   branchStaffExtDao.update(branchStaffExt);
		   }
//		   userCommissionJDBCDao.updateBranchforDefualtUserCommission(chiefId, branchId);
	   }else{
		   //更新当前分公司   “新開户”默认退水 为 0:否
		   BranchStaffExt branch=branchStaffExtDao.get(branchId);
		   branch.setDefaultCommission(0);
		   branchStaffExtDao.update(branch);
	   }
	   System.out.println("更新佣金樹花時間："+(t2-t1)/1000);
	   System.out.println("更新用戶本身花時間："+(t3-t2)/1000);
   }
   
   
   public void batchUpdateUserTreeCommissiono(final List<UserCommission> commissions)
   {
	   
	   userCommissionJDBCDao.batchUpdateUserTreeCommission(commissions);
	   
   }

   
   public void batchUpdateChiefUsereCommission(final List<UserCommissionDefault> commissions)
   {
	   
	   userCommissionJDBCDao.batchUpdateChiefUsereCommission(commissions);
   }
   
   
    public IUserCommissionJDBCDao getUserCommissionJDBCDao() {
        return userCommissionJDBCDao;
    }

    public void setUserCommissionJDBCDao(IUserCommissionJDBCDao userCommissionJDBCDao) {
        this.userCommissionJDBCDao = userCommissionJDBCDao;
    }
    
    public void saveUserBatchCommission(List<UserCommission> userCommissions)
    {
    	
    	userCommissionJDBCDao.batchInsertUserCommission(userCommissions);
    }
    public void batchUpdateMemberCommissiono(final List<UserCommission> commissions,final String plate)
    {
    	userCommissionJDBCDao.updateMemberUserCommission(commissions, plate);
    }
    
}
