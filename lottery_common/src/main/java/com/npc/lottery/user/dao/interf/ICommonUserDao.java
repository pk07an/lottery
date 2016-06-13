package com.npc.lottery.user.dao.interf;

import java.util.List;
import java.util.Map;

import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.sysmge.entity.ManagerUser;

public interface ICommonUserDao {
	
	public void updateBranchBelowAvailableCreditLine(String managerAccount);
	public void updateStockHolderBelowAvailableCreditLine(String managerAccount);
	public void updateGenAgentBelowAvailableCreditLine(String managerAccount);
	public void updateAgentBelowAvailableCreditLine(String managerAccount);
	public void updateMemberBelowAvailableCreditLine(String managerAccount);
	
	
	public void updateStockHolderBelowRate(String managerAccount);
	public void updateGenAgentBelowRate(String managerAccount);
	public void updateAgentBelowRate(String managerAccount);
	public void updateMemberBelowRate(String managerAccount);
	
	public void updateBranchBelowReplenishment(String managerAccount);
	public void updateStockHolderBelowReplenishment(String managerAccount);
	public void updateGenAgentBelowReplenishment(String managerAccount);
	public void updateAgentBelowReplenishment(String managerAccount);
	//public void updateMemberBelowReplenishment(Long AgentId);
	
	//对操作的本级以下的帐号的修改状态
	public void updateManagerStatus(String managerAccount,String status,String managerType);
	
	//对操作的本级帐号的修改状态--管理
	public void updateManagerStatusForMain(String managerAccount,String status,String managerType);
	//对操作的本级帐号的修改状态--会员
	public void updateMemberStatusForMain(String managerAccount,String status,String managerType);
	
	public void updateMemberStatus(String managerAccount,String status,String managerType);
	
	
/*	public void updateStockHolderBelowForbid(Long BranchId);
	public void updateGenAgentBelowForbid(Long stockHoderId);
	public void updateAgentBelowForbid(Long genAentId);
	public void updateMemberBelowForbid(Long AgentId);
	
	public void updateStockHolderBelowFreeze(Long BranchId);
	public void updateGenAgentBelowFreeze(Long stockHoderId);
	public void updateAgentBelowFreeze(Long genAentId);
	public void updateMemberBelowFreeze(Long AgentId);*/
	/*
	 * 查詢管理用戶下的某級別的所有用戶
	 */
	public List<ManagerUser> queryBelowManager(String account,String type);
	
	public List<ManagerUser> queryBelowManagerByUserId(String userId,String type);
	
	public Map queryManagerTreeCount(String account,String type,String searchUserStatus);
	//  Create by Eric
	public void deleteUserOutReplenishStaff(Long userId);
	//  Create by Eric
	public void deleteMangerStaff(Long userId);
	
	public boolean queryUserTreeHasBet(Long userId,String type);
	
	public Long queryStockHolderCredit(Long id);
	public Long queryGenAgentCredit(Long id);
	public Long queryAgentCredit(Long id);
	public Long queryMemberCredit(Long id,String userType);
	
	public int queryBelowMaxRate(Long managerId,String type);
	//查询符合相关条件的用户列表
	public List<UserVO> queryManagerStatus(String managerAccount,String status,String managerType);
	//查询符合相关条件的用户列表
	public List<UserVO> queryMemberStatus(String managerAccount,String status,String managerType);
	
	/*public void updateManagerUsed(String managerAccount,String status);
	public void updateMemberUsed(String managerAccount,String status);*/
	

}
