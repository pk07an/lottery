package com.npc.lottery.user.logic.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.sysmge.entity.SessionStatInfo;
import com.npc.lottery.user.dao.interf.ICommonUserDao;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.ICommonUserLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;


public class CommonUserLogic implements ICommonUserLogic{

    
	private ICommonUserDao commonUserDao = null;
	/*
     * 更新管理用户下所有人的可用余额为 跟信用额度
     */
    public void clearBelowAvailableCreditLine(String managerAccount,String managerType)
    {  
    	if(ManagerStaff.USER_TYPE_AGENT.equals(managerType))
    	{
    		commonUserDao.updateMemberBelowAvailableCreditLine(managerAccount);
    		
    	}
    	if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(managerType))
    	{
    		commonUserDao.updateMemberBelowAvailableCreditLine(managerAccount);
    		commonUserDao.updateAgentBelowAvailableCreditLine(managerAccount);
    		
    	}
    	if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(managerType))
    	{
    		commonUserDao.updateMemberBelowAvailableCreditLine(managerAccount);
    		commonUserDao.updateAgentBelowAvailableCreditLine(managerAccount);
    		commonUserDao.updateGenAgentBelowAvailableCreditLine(managerAccount);
    		
    	}
    	if(ManagerStaff.USER_TYPE_BRANCH.equals(managerType))
    	{
    		commonUserDao.updateMemberBelowAvailableCreditLine(managerAccount);
    		commonUserDao.updateAgentBelowAvailableCreditLine(managerAccount);
    		commonUserDao.updateGenAgentBelowAvailableCreditLine(managerAccount);
    		commonUserDao.updateStockHolderBelowAvailableCreditLine(managerAccount);
    		
    	}
    	
    
    	
    	//commonUserDao.updateGenAgentBelowAvailableCreditLine(managerAccount);
    	//commonUserDao.updateStockHolderBelowAvailableCreditLine(managerAccount);
    	//commonUserDao.updateBranchBelowAvailableCreditLine(managerAccount);
    	
    }
    
    /*
     * 更新管理用户下所有人的占成为0
     */
    public void clearBelowRate(String managerAccount,String managerType)
    {
    	
    	if(ManagerStaff.USER_TYPE_AGENT.equals(managerType))
    	{
    		commonUserDao.updateMemberBelowRate(managerAccount);
    		
    	}
    	if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(managerType))
    	{
    		commonUserDao.updateMemberBelowRate(managerAccount);
        	commonUserDao.updateAgentBelowRate(managerAccount);
    		
    	}
    	if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(managerType))
    	{
        	commonUserDao.updateMemberBelowRate(managerAccount);
        	commonUserDao.updateAgentBelowRate(managerAccount);

        	commonUserDao.updateGenAgentBelowRate(managerAccount);
    		
    	}
    	if(ManagerStaff.USER_TYPE_BRANCH.equals(managerType))
    	{
    	  	commonUserDao.updateMemberBelowRate(managerAccount);
        	commonUserDao.updateAgentBelowRate(managerAccount);

        	commonUserDao.updateGenAgentBelowRate(managerAccount);
        	commonUserDao.updateStockHolderBelowRate(managerAccount);
    		
    	}
    	
    	
/*    	commonUserDao.updateMemberBelowRate(managerAccount);
    	commonUserDao.updateAgentBelowRate(managerAccount);

    	commonUserDao.updateGenAgentBelowRate(managerAccount);
    	commonUserDao.updateStockHolderBelowRate(managerAccount);*/
    	
    }
    
    /*
     * 更新管理用户下所有人走飞为禁止
     */
    public void updateBelowReplenishment(String managerAccount,String managerType)
    {
    	if(ManagerStaff.USER_TYPE_AGENT.equals(managerType))
    	{
    		commonUserDao.updateAgentBelowReplenishment(managerAccount);
    		
    	}
    	if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(managerType))
    	{
        	commonUserDao.updateAgentBelowReplenishment(managerAccount);
        	commonUserDao.updateGenAgentBelowReplenishment(managerAccount);
    		
    	}
    	if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(managerType))
    	{
        	commonUserDao.updateAgentBelowReplenishment(managerAccount);
        	commonUserDao.updateGenAgentBelowReplenishment(managerAccount);
        	commonUserDao.updateStockHolderBelowReplenishment(managerAccount);
    		
    	}
    	if(ManagerStaff.USER_TYPE_BRANCH.equals(managerType))
    	{
        	commonUserDao.updateAgentBelowReplenishment(managerAccount);
        	commonUserDao.updateGenAgentBelowReplenishment(managerAccount);
        	commonUserDao.updateStockHolderBelowReplenishment(managerAccount);
        	commonUserDao.updateBranchBelowReplenishment(managerAccount);
    		
    	}
    	
/*    	commonUserDao.updateAgentBelowReplenishment(managerAccount);
    	commonUserDao.updateGenAgentBelowReplenishment(managerAccount);
    	commonUserDao.updateStockHolderBelowReplenishment(managerAccount);
    	commonUserDao.updateBranchBelowReplenishment(managerAccount); */   	
    
    	
    }
    
    public void logoutUserByOther(String managerAccount,String userType){
    	//******把被禁用的用户强制踢出start
        if (SessionStatInfo.managerSessionList == null) {
            SessionStatInfo.managerSessionList = Collections
                    .synchronizedList(new ArrayList<SessionStatInfo>());
        }
    	for (int i = SessionStatInfo.managerSessionList.size() - 1; i >= 0; i--) {
            SessionStatInfo entity = SessionStatInfo.managerSessionList
                    .get(i);
            if (entity.getAccount().equals(managerAccount) && entity.getUserType().equals(userType)) {
                entity.getSession().invalidate();//自动调用remove
                break;
            }
        }
    	//******把被禁用的用户强制踢出start
    }
    
    /*
     * 更新管理用户下所有人为禁止状态
     */
    public void updateBelowForbid(String managerAccount,String managerType,String currentUserType)
    {
    	
    	if(MemberStaff.USER_TYPE_MEMBER.equals(managerType)){
    		commonUserDao.updateMemberStatusForMain(managerAccount, MemberStaff.FLAG_FORBID,managerType);
    		this.logoutUserByOther(managerAccount, MemberStaff.USER_TYPE_MEMBER);
    	}else{
    		commonUserDao.updateManagerStatusForMain(managerAccount, MemberStaff.FLAG_FORBID,managerType);//操作本级目的帐号，比如总代理修改代理，这里操作这单个代理
    		this.logoutUserByOther(managerAccount, managerType);
    		
    		if(!ManagerStaff.USER_TYPE_AGENT.equals(managerType)){//如果是代理就不执行，因为代理没有下级管理层，只有会员
    			commonUserDao.updateManagerStatus(managerAccount, MemberStaff.FLAG_FORBID,managerType);//操作状态为启用的所有下级，引上所述，这里操作代理的所有下级，而不操作代理。
    			//将受影响的用户全部踢出
    			List<UserVO> list = commonUserDao.queryManagerStatus(managerAccount, MemberStaff.FLAG_FORBID,managerType);
		    	for(UserVO vo:list){
		    		this.logoutUserByOther(vo.getAccount(), vo.getUserType());
		    	}
    		}
	    	commonUserDao.updateMemberStatus(managerAccount, MemberStaff.FLAG_FORBID,managerType);//操作状态为启用的所有下级会员，引上所述，这里操作代理的所有下级会员。
	    	this.logoutUserByOther(managerAccount, MemberStaff.USER_TYPE_MEMBER);
    	}
    }
    
    /*
     * 更新管理用户下所有人为冻结状态
     */
    public void updateBelowFreeze(String managerAccount,String managerType,String currentUserType)
    {
    	if(MemberStaff.USER_TYPE_MEMBER.equals(managerType)){
    		commonUserDao.updateMemberStatusForMain(managerAccount, MemberStaff.FLAG_FREEZE,managerType);
    		this.logoutUserByOther(managerAccount, MemberStaff.USER_TYPE_MEMBER);
    	}else{
    		commonUserDao.updateManagerStatusForMain(managerAccount, MemberStaff.FLAG_FREEZE,managerType);
    		this.logoutUserByOther(managerAccount, managerType);
    		
    		if(!ManagerStaff.USER_TYPE_AGENT.equals(managerType)){//如果是代理就不执行，因为代理没有下级管理层，只有会员
		    	commonUserDao.updateManagerStatus(managerAccount, MemberStaff.FLAG_FREEZE,managerType);
		    	//将受影响的用户全部踢出
		    	List<UserVO> list = commonUserDao.queryManagerStatus(managerAccount, MemberStaff.FLAG_FREEZE,managerType);
		    	for(UserVO vo:list){
		    		this.logoutUserByOther(vo.getAccount(), vo.getUserType());
		    	}
    		}
	    	commonUserDao.updateMemberStatus(managerAccount, MemberStaff.FLAG_FREEZE,managerType);
	    	this.logoutUserByOther(managerAccount, MemberStaff.USER_TYPE_MEMBER);
    	}
    
    	
    }
    
    public void updateBelowUsed(String managerAccount,String managerType,String currentUserType)
    {
    	if(MemberStaff.USER_TYPE_MEMBER.equals(managerType)){
    		commonUserDao.updateMemberStatusForMain(managerAccount, MemberStaff.FLAG_USE,managerType);
    	}else{
	    	commonUserDao.updateManagerStatusForMain(managerAccount, MemberStaff.FLAG_USE,managerType);
	    	commonUserDao.updateManagerStatus(managerAccount, MemberStaff.FLAG_USE,managerType);
	    	commonUserDao.updateMemberStatus(managerAccount, MemberStaff.FLAG_USE,managerType);
    	}
    }
    
    
    public Long queryBelowCreditLine(Long id,String managerType)
    {
    	
    	if(ManagerStaff.USER_TYPE_AGENT.equals(managerType))
    	{
    		Long ret= commonUserDao.queryMemberCredit(id, managerType);
    		if(ret==null)
    			ret=0L;
    		return ret;
    		
    	}
    	else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(managerType))
    	{
    		Long agent=	commonUserDao.queryAgentCredit(id);
    		if(agent==null)
    			agent=0L;
    		Long mem=   commonUserDao.queryMemberCredit(id, managerType);
    		if(mem==null)
    			mem=0L;
        	return agent+mem;
    		
    	}
    	else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(managerType))
    	{
    		Long genAgent=commonUserDao.queryGenAgentCredit(id);
    		if(genAgent==null)
    			genAgent=0L;
    	
    		Long agent=	commonUserDao.queryAgentCredit(id);
    		if(agent==null)
    			agent=0L;
    		Long mem=   commonUserDao.queryMemberCredit(id, managerType);
    		if(mem==null)
    			mem=0L;
        	return genAgent+ agent+mem;
    		
    	}
    	else if(ManagerStaff.USER_TYPE_BRANCH.equals(managerType))
    	{
    		Long stock=commonUserDao.queryStockHolderCredit(id);
    		if(stock==null)
    			stock=0L;
    		Long genAgent=commonUserDao.queryGenAgentCredit(id);
    		if(genAgent==null)
    			genAgent=0L;
    	
    		Long agent=	commonUserDao.queryAgentCredit(id);
    		if(agent==null)
    			agent=0L;
    		Long mem=   commonUserDao.queryMemberCredit(id, managerType);
    		if(mem==null)
    			mem=0L;
        	return genAgent+ agent+mem;
    		
    	}
    	return 0L;
    	
    	
    	
    	
    }
    
    public UserVO getUserVo(ManagerUser userInfoNew){
    	UserVO userVO =  new UserVO();
    	boolean isBranch = userInfoNew.getUserType().equals(
                ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
        boolean isStockholder = userInfoNew.getUserType().equals(
                ManagerStaff.USER_TYPE_STOCKHOLDER);// 股东
        boolean isGenAgent = userInfoNew.getUserType().equals(
                ManagerStaff.USER_TYPE_GEN_AGENT);// 总代理
        boolean isAgent = userInfoNew.getUserType().equals(
                ManagerStaff.USER_TYPE_AGENT);// 代理
        if(isBranch){
            BranchStaffExt branchStaffExt = branchStaffExtLogic.queryBranchStaffExt("managerStaffID",userInfoNew.getID());
            userVO.setReplenishMent(branchStaffExt.getReplenishment());
            userVO.setReport(branchStaffExt.getOpenReport());
            userVO.setChiefID(branchStaffExt.getParentStaff());
            userVO.setChiefStaffExt(branchStaffExt.getChiefStaffExt());
            
        }else if(isStockholder){
            StockholderStaffExt  stockholderStaffExt = stockholderStaffExtLogic.queryStockholderStaffExt("managerStaffID",userInfoNew.getID());
            userVO.setReplenishMent(stockholderStaffExt.getReplenishment());
        }else if(isGenAgent){
            GenAgentStaffExt genAgentStaffExt = genAgentStaffExtLogic.queryGenAgentStaffExt("managerStaffID",userInfoNew.getID());
            userVO.setReplenishMent(genAgentStaffExt.getReplenishment());
        }else if(isAgent){
            AgentStaffExt agentStaffExt = agentStaffExtLogic.queryAgenStaffExt("managerStaffID",userInfoNew.getID());
            userVO.setReplenishMent(agentStaffExt.getReplenishment());
        }
    	
		return userVO;
    	
    }
    
    private IStockholderStaffExtLogic stockholderStaffExtLogic = null;// 股东
    private IBranchStaffExtLogic branchStaffExtLogic = null;// 公公司
    private IGenAgentStaffExtLogic genAgentStaffExtLogic; // 总代理
    private IAgentStaffExtLogic agentStaffExtLogic; // 代理
    
    
    public IStockholderStaffExtLogic getStockholderStaffExtLogic() {
		return stockholderStaffExtLogic;
	}

	public IBranchStaffExtLogic getBranchStaffExtLogic() {
		return branchStaffExtLogic;
	}

	public IGenAgentStaffExtLogic getGenAgentStaffExtLogic() {
		return genAgentStaffExtLogic;
	}

	public IAgentStaffExtLogic getAgentStaffExtLogic() {
		return agentStaffExtLogic;
	}

	public void setStockholderStaffExtLogic(
			IStockholderStaffExtLogic stockholderStaffExtLogic) {
		this.stockholderStaffExtLogic = stockholderStaffExtLogic;
	}

	public void setBranchStaffExtLogic(IBranchStaffExtLogic branchStaffExtLogic) {
		this.branchStaffExtLogic = branchStaffExtLogic;
	}

	public void setGenAgentStaffExtLogic(
			IGenAgentStaffExtLogic genAgentStaffExtLogic) {
		this.genAgentStaffExtLogic = genAgentStaffExtLogic;
	}

	public void setAgentStaffExtLogic(IAgentStaffExtLogic agentStaffExtLogic) {
		this.agentStaffExtLogic = agentStaffExtLogic;
	}

	public Map queryManagerUserTree(String  account,String type,String searchUserStatus)
    {
    	
    	return commonUserDao.queryManagerTreeCount(account, type,searchUserStatus);
    }
    
    public List<ManagerUser> queryBelowManager(String  account,String type)
    {
    	return commonUserDao.queryBelowManager(account, type);
    	
    }

    public List<ManagerUser> queryBelowManagerByUserId(String  userId,String type)
    {
        
        return commonUserDao.queryBelowManager(userId, type);
        
    }
    
    public boolean queryUserTreeHasBet(Long userId,String type)
    {
    	
    	 return commonUserDao.queryUserTreeHasBet(userId, type);
    	
    }
    public int queryBelowMaxRate(Long managerId,String type)
    {
    	 return commonUserDao.queryBelowMaxRate(managerId, type);
    	
    }
    
    
	public ICommonUserDao getCommonUserDao() {
		return commonUserDao;
	}

	public void setCommonUserDao(ICommonUserDao commonUserDao) {
		this.commonUserDao = commonUserDao;
	}

 
}
