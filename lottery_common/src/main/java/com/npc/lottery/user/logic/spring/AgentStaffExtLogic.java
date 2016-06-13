package com.npc.lottery.user.logic.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.common.Constant;
import com.npc.lottery.replenish.entity.ReplenishAutoSetLog;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoSetLogLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.dao.interf.IAgentStaffExtDao;
import com.npc.lottery.user.dao.interf.IBranchStaffExtDao;
import com.npc.lottery.user.dao.interf.IChiefStaffExtDao;
import com.npc.lottery.user.dao.interf.IGenAgentStaffExtDao;
import com.npc.lottery.user.dao.interf.IStockholderStaffExtDao;
import com.npc.lottery.user.dao.interf.ISubAccountActionDao;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.ICommonUserLogic;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.util.MD5;
import com.npc.lottery.util.Page;

public class AgentStaffExtLogic implements IAgentStaffExtLogic {

    Logger logger = Logger.getLogger(AgentStaffExtLogic.class);
    private IAgentStaffExtDao agentStaffExtDao = null;
    private IGenAgentStaffExtDao genAgentStaffExtDao = null;
    private IBranchStaffExtDao branchStaffExtDao = null;
    private IChiefStaffExtDao chiefStaffExtDao = null;
    private IStockholderStaffExtDao stockholderStaffExtDao = null;
    private ISubAccountActionDao subAccountActionDao;
    private IUserCommissionLogic userCommissionLogic;
    protected ICommonUserLogic commonUserLogic;
    //add by peter
    private IReplenishAutoSetLogLogic replenishAutoSetLogLogic;
    @Override
    public long findAmountAgentStaffExtList(ConditionData conditionData) {
        return 0;
    }

    @Override
    public List findAgentStaffExtList(ConditionData conditionData,
            int pageCurrentNo, int pageSize) {
        return null;
    }

    @Override
    public AgentStaffExt findManagerStaffByID(Long userID) {
        AgentStaffExt agentStaffExt = agentStaffExtDao.get(userID);
        System.out.println(agentStaffExt);
        return agentStaffExtDao.get(userID);
    }

    public AgentStaffExt findByID(Long userID,String scheme){
    	return agentStaffExtDao.findById(userID,scheme);
    }
    
    @Override
    public Long saveAgentStaffExt(AgentStaffExt entity) {
            agentStaffExtDao.save(entity);
            return (long) 0;
    }

    @Override
    public void updateAgentStaffExt(AgentStaffExt entity) {
        agentStaffExtDao.update(entity);
    }

    @Override
    public void delAgentStaffExt(AgentStaffExt entity) {

    }

    @Override
    public AgentStaffExt queryAgenStaffExt(String propertyName, Object value) {
            return agentStaffExtDao.findUniqueBy(propertyName, value);
    }

    public IAgentStaffExtDao getAgentStaffExtDao() {
        return agentStaffExtDao;
    }

    public void setAgentStaffExtDao(IAgentStaffExtDao agentStaffExtDao) {
        this.agentStaffExtDao = agentStaffExtDao;
    }

    @Override
    public List<AgentStaffExt> queryAllAgentStaffExt(String propertyName,
            Object value) {
        return agentStaffExtDao.findBy(propertyName, value);
    }

    @Override
    public Page<AgentStaffExt> findPage(Page<AgentStaffExt> page,
            Criterion... criterions) {
        return agentStaffExtDao.findPage(page, criterions);
    }

	@Override
	public Page<AgentStaffExt> findPage(Page page, ManagerStaff userInfo, String userStatus, String account, String chName) {
		boolean isSys = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SYS);// 系统类型
		boolean isManager = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_MANAGER);// 总管类型

		if (!isSys || !isManager)// 总管和系统管理员一般
		{
			boolean isChief = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_CHIEF);// 总监类型
			boolean isBranch = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
			boolean isStockholder = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_STOCKHOLDER);// 股东
			boolean isGenAgent = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_GEN_AGENT);// 总代理
			boolean isSub = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB);//
			SubAccountInfo subInfo = new SubAccountInfo();
			if (isSub) {
				subInfo = subAccountActionDao.findUniqueBy("account", userInfo.getAccount());
			}
			boolean isSubChief = false;
			boolean isSubBranch = false;
			boolean isSubStockholder = false;
			boolean isSubGenAgent = false;
			if (subInfo != null) {
				isSubChief = ManagerStaff.USER_TYPE_CHIEF.equals(subInfo.getParentUserType());// 总监类型
				isSubBranch = ManagerStaff.USER_TYPE_BRANCH.equals(subInfo.getParentUserType());// 分公司类型
				isSubStockholder = ManagerStaff.USER_TYPE_STOCKHOLDER.equals(subInfo.getParentUserType());// 股东
				isSubGenAgent = ManagerStaff.USER_TYPE_GEN_AGENT.equals(subInfo.getParentUserType());// 总代理
			}

			List<AgentStaffExt> staffExts = new ArrayList<AgentStaffExt>();
			/*
			 * add by peter for 用户会员列表优化
			 */
			Map<String,Object> searchMap = new HashMap<String, Object>();
			if (userStatus != null) {
				searchMap.put("flag", userStatus);
			}
			if (account != null) {
				searchMap.put("account", account);
			}
			if (chName != null) {
				searchMap.put("chsName", chName);
			}
			
			if (isChief || isSubChief) {
				ChiefStaffExt chiefStaffExt = new ChiefStaffExt();
				if (isChief) {
					chiefStaffExt = chiefStaffExtDao.findUniqueBy("account", userInfo.getAccount());
				} else if (isSubChief) {
					chiefStaffExt = chiefStaffExtDao.findUniqueBy("managerStaffID", subInfo.getParentStaff());
				}
				// add by peter 优化代理列表
				//staffExts.addAll(agentStaffExtDao.findBy("chiefStaff", chiefStaffExt.getID()));
				searchMap.put("chiefStaff", chiefStaffExt.getID());
			} else if (isBranch || isSubBranch) {
				BranchStaffExt branchStaffExt = new BranchStaffExt();
				if (isBranch) {
					branchStaffExt = branchStaffExtDao.findUniqueBy("account", userInfo.getAccount());
				} else if (isSubBranch) {
					branchStaffExt = branchStaffExtDao.findUniqueBy("managerStaffID", subInfo.getParentStaff());
				}

				// add by peter 优化代理列表
				//staffExts.addAll(agentStaffExtDao.findBy("branchStaff", branchStaffExt.getID()));
				searchMap.put("branchStaff", branchStaffExt.getID());

			} else if (isStockholder || isSubStockholder) {
				StockholderStaffExt stockholderStaffExt = new StockholderStaffExt();
				if (isStockholder) {
					stockholderStaffExt = stockholderStaffExtDao.findUniqueBy("account", userInfo.getAccount());
				} else if (isSubStockholder) {
					stockholderStaffExt = stockholderStaffExtDao.findUniqueBy("managerStaffID", subInfo.getParentStaff());
				}
				// add by peter 优化代理列表
				//staffExts.addAll(agentStaffExtDao.findBy("stockholderStaff", stockholderStaffExt.getID()));
				searchMap.put("stockholderStaff", stockholderStaffExt.getID());
			} else if (isGenAgent || isSubGenAgent) {
				GenAgentStaffExt genAgentStaffExt = new GenAgentStaffExt();
				if (isGenAgent) {
					genAgentStaffExt = genAgentStaffExtDao.findUniqueBy("account", userInfo.getAccount());
				} else if (isSubGenAgent) {
					genAgentStaffExt = genAgentStaffExtDao.findUniqueBy("managerStaffID", subInfo.getParentStaff());
				}
				// add by peter 优化代理列表
				//staffExts.addAll(agentStaffExtDao.findBy("parentStaff", genAgentStaffExt.getID().intValue()));
				searchMap.put("parentStaff", genAgentStaffExt.getID().intValue());

			}
//			staffExts.addAll(agentStaffExtDao.find(Restrictions.allEq(searchMap)));
			/*
			 * add by peter for 优化股东列表 start
			 */
//			List<AgentStaffExt> newStaffExts = new ArrayList<AgentStaffExt>();
//			for (int i = 0; i < staffExts.size(); i++) {
//				AgentStaffExt agentStaffExt = staffExts.get(i);
//				String acc = agentStaffExt.getAccount();
//				String cn = agentStaffExt.getChsName();
//				String status = agentStaffExt.getFlag();
//				if (userStatus != null && !userStatus.equals(status)) {
//					continue;
//				}
//				if (account != null && !account.equals(acc)) {
//					continue;
//				}
//				if (chName != null && !chName.equals(cn)) {
//					continue;
//				}
//
//				newStaffExts.add(agentStaffExt);
//
//			}
//			staffExts.clear();
			/*
			 * add by peter for 优化股东列表 end
			 */
			page.setOrder("ASC");
			page.setOrderBy("account");
			page = agentStaffExtDao.findPage(page, Restrictions.allEq(searchMap));
			return page;
		}
		return page;
	}

    public IGenAgentStaffExtDao getGenAgentStaffExtDao() {
        return genAgentStaffExtDao;
    }

    public void setGenAgentStaffExtDao(IGenAgentStaffExtDao genAgentStaffExtDao) {
        this.genAgentStaffExtDao = genAgentStaffExtDao;
    }

    public IBranchStaffExtDao getBranchStaffExtDao() {
        return branchStaffExtDao;
    }

    public void setBranchStaffExtDao(IBranchStaffExtDao branchStaffExtDao) {
        this.branchStaffExtDao = branchStaffExtDao;
    }

    public IChiefStaffExtDao getChiefStaffExtDao() {
        return chiefStaffExtDao;
    }

    public void setChiefStaffExtDao(IChiefStaffExtDao chiefStaffExtDao) {
        this.chiefStaffExtDao = chiefStaffExtDao;
    }

    public IStockholderStaffExtDao getStockholderStaffExtDao() {
        return stockholderStaffExtDao;
    }

    public void setStockholderStaffExtDao(
            IStockholderStaffExtDao stockholderStaffExtDao) {
        this.stockholderStaffExtDao = stockholderStaffExtDao;
    }

    @Override
    public Page<AgentStaffExt> findSubPage(Page page, ManagerUser userInfo) {
        boolean isSys = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_SYS);// 系统类型
        boolean isManager = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_MANAGER);// 总管类型
        if (!isSys || !isManager)// 总管和系统管理员一般
        {
            boolean iAgent = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_AGENT);// 代理类型
            boolean iAgetSub = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_SUB);// 子账号代理类型
           if(iAgent || iAgetSub){
               AgentStaffExt agentStaffExt = new AgentStaffExt();
               List<SubAccountInfo> staffExts = new ArrayList<SubAccountInfo>();
               if(iAgent){
                   agentStaffExt = agentStaffExtDao.findUniqueBy("managerStaffID",userInfo.getID());
               }else if(iAgetSub){
                   SubAccountInfo subInfo = new SubAccountInfo();
                   subInfo = subAccountActionDao.findUniqueBy("managerStaffID",userInfo.getID());
                   agentStaffExt = agentStaffExtDao.findUniqueBy("managerStaffID",subInfo.getParentStaff());
               }
               
               for (SubAccountInfo subAccountInfo : agentStaffExt.getSubAccountInfoSet()) {
                   if(!subAccountInfo.getFlag().equals(ManagerStaff.FLAG_DELETE)){
                	   if(!userInfo.getAccount().equals(subAccountInfo.getAccount())){
                    	   staffExts.add(subAccountInfo);
                       }
                   }
               }
               
               int first=page.getFirst()-1;
               int last=first+page.getPageSize();
               if(last>staffExts.size())
                   last=staffExts.size();
               Collections.sort(staffExts);
               page.setTotalCount(staffExts.size());
               page.setResult(staffExts.subList(first, last));
               return page;    
           }
        }    
        return page;
    }
    
    /*
     * 保存代 提取相關邏輯到事物
     */
    public Long saveUserAgentStaff(AgentStaffExt entity,Long createUserId)
    {
    	 List<UserCommission> userCommissions = new ArrayList<UserCommission>();
    	 agentStaffExtDao.save(entity);
    	 Long genAgentId=entity.getGenAgentStaffExt().getID();
         userCommissions = userCommissionLogic.queryCommission(genAgentId,ManagerStaff.USER_TYPE_GEN_AGENT); // 获取用户退水，跟前台进行比较
         List<UserCommission>  agentCommissions=new ArrayList<UserCommission>();
         for (int i = 0; i < userCommissions.size(); i++) {
         	
         	UserCommission userCommision=new UserCommission();
         	userCommision.setCreateTime(new Date());         	
         	userCommision.setCreateUser(createUserId);
         	userCommision.setUserId(entity.getID());
         	userCommision.setCommissionA(userCommissions.get(i).getCommissionA());
         	userCommision.setCommissionB(userCommissions.get(i).getCommissionB());
         	userCommision.setCommissionC(userCommissions.get(i).getCommissionC());
         	userCommision.setPlayType(userCommissions.get(i).getPlayType());
         	userCommision.setUserType(ManagerStaff.USER_TYPE_AGENT);
         	userCommision.setItemQuotas(userCommissions.get(i).getItemQuotas());
         	userCommision.setBettingQuotas(userCommissions.get(i).getBettingQuotas());
         	userCommision.setPlayFinalType(userCommissions.get(i).getPlayFinalType());
         	userCommision.setChiefId(userCommissions.get(i).getChiefId());
         	agentCommissions.add(userCommision);
             //userCommissionLogic.saveCommission(userCommision);
         	
         }
         userCommissionLogic.saveUserBatchCommission(agentCommissions);
         return entity.getID();
    }
    
    /*
     * 修改代 提取相關邏輯到事物
     */
    public void updateUserAgentStaff(AgentStaffExt agentStaffExt,String currentUserType)
    {
    	AgentStaffExt aStaffExt=agentStaffExtDao.findUniqueBy("account", agentStaffExt.getAccount());
    	    
        int availableMoney =aStaffExt.getGenAgentStaffExt().getAvailableCreditLine();
      
            if(StringUtils.isNotEmpty(agentStaffExt.getUserPwd())){   
                MD5 md5 = new MD5();
                String userPwdOrignMd5 = md5.getMD5ofStr(agentStaffExt.getUserPwd()).trim();
                aStaffExt.setUserPwd(userPwdOrignMd5);
                aStaffExt.setPasswordUpdateDate(agentStaffExt.getPasswordUpdateDate());
                aStaffExt.setPasswordResetFlag(agentStaffExt.getPasswordResetFlag());
            }
            int tempFlag =agentStaffExt.getTotalCreditLine() - aStaffExt.getTotalCreditLine();
            
            if(tempFlag < 0){
            	Integer changedCred=agentStaffExt.getTotalCreditLine();
            	Integer userCred= aStaffExt.getTotalCreditLine()- aStaffExt.getAvailableCreditLine();
            	if(changedCred<userCred)
            	{
  
            		 throw new RuntimeException("修改代理 信用额度 小于 已经分配的值");
            	}
            	else
            	{
            		agentStaffExt.setAvailableCreditLine(aStaffExt.getAvailableCreditLine()+tempFlag);
            	}
            	
             }else{
            	if(availableMoney-tempFlag<0){
               	 throw new RuntimeException("总信用额度不能超过剩下的信用额度");
            	}
            	else{
            		agentStaffExt.setAvailableCreditLine(tempFlag+aStaffExt.getAvailableCreditLine());
            	}
            }
            
         // 佔成修改限制
            GenAgentStaffExt genAStaffExt=aStaffExt.getGenAgentStaffExt();
            StockholderStaffExt stock=genAStaffExt.getStockholderStaffExt();
            BranchStaffExt branch=stock.getBranchStaffExt();
            
            if(agentStaffExt.getGenAgentRate()!=null)
            {
            	int maxRate=commonUserLogic.queryBelowMaxRate(aStaffExt.getID(), ManagerStaff.USER_TYPE_AGENT);
            	if(agentStaffExt.getGenAgentRate()>100-maxRate-genAStaffExt.getShareholderRate()-stock.getBranchRate()-branch.getChiefRate())
               { 
        		    throw new RuntimeException("超過代理 可以修改的最大值！！");
               }
            }
 
                aStaffExt.getGenAgentStaffExt().setAvailableCreditLine(availableMoney-tempFlag);//上级的可用信用额度
                //genAgentStaffExtLogic.updateGenAgentStaffExt(aStaffExt.getGenAgentStaffExt());
                //genAgentStaffExtDao.update(entity);
                if(agentStaffExt.getRateRestrict()!=null)
                {
                aStaffExt.setRateRestrict(agentStaffExt.getRateRestrict());
                }
                aStaffExt.setChsName(agentStaffExt.getChsName());
                aStaffExt.setAvailableCreditLine(agentStaffExt.getAvailableCreditLine());
                if(agentStaffExt.getGenAgentRate()!=null)
                {
                aStaffExt.setGenAgentRate(agentStaffExt.getGenAgentRate());
                }
                aStaffExt.setTotalCreditLine(agentStaffExt.getTotalCreditLine());
               
                if(agentStaffExt.getReplenishment()!=null)
                {
                	 aStaffExt.setReplenishment(agentStaffExt.getReplenishment());
                }
                if("0".equals(agentStaffExt.getRateRestrict()))
                {
                	aStaffExt.setBelowRateLimit(null);
                }
                else if(agentStaffExt.getBelowRateLimit()!=null)
                {
                	aStaffExt.setBelowRateLimit(agentStaffExt.getBelowRateLimit());
                }
                aStaffExt.setFlag(agentStaffExt.getFlag());
    	agentStaffExtDao.update(aStaffExt);
    
        // 如果上级禁止飞走，下级用户全部改成禁止
        boolean isReplenis = aStaffExt.getReplenishment().equals(
                ManagerStaff.REPLENIS_FORBID);
        if (isReplenis) {
        	 commonUserLogic.updateBelowReplenishment(aStaffExt.getAccount(), ManagerStaff.USER_TYPE_AGENT);
        }
        boolean isUserFlagForbid = aStaffExt.getFlag().equals(
                ManagerStaff.FLAG_FORBID);
        boolean isUserFlagFreeze = aStaffExt.getFlag().equals(
                ManagerStaff.FLAG_FREEZE);
        if (isUserFlagForbid || isUserFlagFreeze) {
        	
        	 if(isUserFlagForbid)
       	  {
       		  commonUserLogic.updateBelowForbid(aStaffExt.getAccount(), ManagerStaff.USER_TYPE_AGENT,currentUserType);
       		  
       	  }
       	  else if(isUserFlagFreeze)
       	  {
       		  commonUserLogic.updateBelowFreeze(aStaffExt.getAccount(), ManagerStaff.USER_TYPE_AGENT,currentUserType);
       	  }
        	
        }
    	
    }
    /* add by peter
     * 修改代 提取相關邏輯到事物
     */
    @Override
    public void updateUserAgentStaff(AgentStaffExt agentStaffExt,String currentUserType,ManagerUser currentUser)
    {
    	AgentStaffExt aStaffExt=agentStaffExtDao.findUniqueBy("account", agentStaffExt.getAccount());
    	
    	int availableMoney =aStaffExt.getGenAgentStaffExt().getAvailableCreditLine();
    	
    	if(StringUtils.isNotEmpty(agentStaffExt.getUserPwd())){   
    		//MD5 md5 = new MD5();
    		String userPwdOrignMd5 = agentStaffExt.getUserPwd().trim();
    		aStaffExt.setUserPwd(userPwdOrignMd5);
    		aStaffExt.setPasswordUpdateDate(agentStaffExt.getPasswordUpdateDate());
    		aStaffExt.setPasswordResetFlag(agentStaffExt.getPasswordResetFlag());
    	}
    	int tempFlag =agentStaffExt.getTotalCreditLine() - aStaffExt.getTotalCreditLine();
    	int newTotalCreditLine = agentStaffExt.getTotalCreditLine();
    	int orginalTotalCreditLine = aStaffExt.getTotalCreditLine();
    	
    	if(tempFlag < 0){
    		Integer changedCred=agentStaffExt.getTotalCreditLine();
    		Integer userCred= aStaffExt.getTotalCreditLine()- aStaffExt.getAvailableCreditLine();
    		if(changedCred<userCred)
    		{
    			
    			throw new RuntimeException("修改代理 信用额度 小于 已经分配的值");
    		}
    		else
    		{
    			agentStaffExt.setAvailableCreditLine(aStaffExt.getAvailableCreditLine()+tempFlag);
    		}
    		
    	}else{
    		if(availableMoney-tempFlag<0){
    			throw new RuntimeException("总信用额度不能超过剩下的信用额度");
    		}
    		else{
    			agentStaffExt.setAvailableCreditLine(tempFlag+aStaffExt.getAvailableCreditLine());
    		}
    	}
    	
    	// 佔成修改限制
    	GenAgentStaffExt genAStaffExt=aStaffExt.getGenAgentStaffExt();
    	StockholderStaffExt stock=genAStaffExt.getStockholderStaffExt();
    	BranchStaffExt branch=stock.getBranchStaffExt();
    	
    	if(agentStaffExt.getGenAgentRate()!=null)
    	{
    		int maxRate=commonUserLogic.queryBelowMaxRate(aStaffExt.getID(), ManagerStaff.USER_TYPE_AGENT);
    		if(agentStaffExt.getGenAgentRate()>100-maxRate-genAStaffExt.getShareholderRate()-stock.getBranchRate()-branch.getChiefRate())
    		{ 
    			throw new RuntimeException("超過代理 可以修改的最大值！！");
    		}
    	}
    	
    	aStaffExt.getGenAgentStaffExt().setAvailableCreditLine(availableMoney-tempFlag);//上级的可用信用额度
    	//genAgentStaffExtLogic.updateGenAgentStaffExt(aStaffExt.getGenAgentStaffExt());
    	//genAgentStaffExtDao.update(entity);
    	if(agentStaffExt.getRateRestrict()!=null)
    	{
    		aStaffExt.setRateRestrict(agentStaffExt.getRateRestrict());
    	}
    	aStaffExt.setChsName(agentStaffExt.getChsName());
    	aStaffExt.setAvailableCreditLine(agentStaffExt.getAvailableCreditLine());
    	if(agentStaffExt.getGenAgentRate()!=null)
    	{
    		aStaffExt.setGenAgentRate(agentStaffExt.getGenAgentRate());
    	}
    	aStaffExt.setTotalCreditLine(agentStaffExt.getTotalCreditLine());
    	
    	if(agentStaffExt.getReplenishment()!=null)
    	{
    		aStaffExt.setReplenishment(agentStaffExt.getReplenishment());
    	}
    	if("0".equals(agentStaffExt.getRateRestrict()))
    	{
    		aStaffExt.setBelowRateLimit(null);
    	}
    	else if(agentStaffExt.getBelowRateLimit()!=null)
    	{
    		aStaffExt.setBelowRateLimit(agentStaffExt.getBelowRateLimit());
    	}
    	aStaffExt.setFlag(agentStaffExt.getFlag());
    	agentStaffExtDao.update(aStaffExt);
    	
    	 //如果信用额度由修改，增加修改信息到日志表 add by peter
        if(tempFlag!=0){
        	ReplenishAutoSetLog changeLog = this.setChangeLog(currentUser, "TOTAL_CREDITLINE", String.valueOf(orginalTotalCreditLine), String.valueOf(newTotalCreditLine),aStaffExt);
        	replenishAutoSetLogLogic.saveReplenishLogSet(changeLog);
        }
    	
    	// 如果上级禁止飞走，下级用户全部改成禁止
    	boolean isReplenis = aStaffExt.getReplenishment().equals(
    			ManagerStaff.REPLENIS_FORBID);
    	if (isReplenis) {
    		commonUserLogic.updateBelowReplenishment(aStaffExt.getAccount(), ManagerStaff.USER_TYPE_AGENT);
    	}
    	boolean isUserFlagForbid = aStaffExt.getFlag().equals(
    			ManagerStaff.FLAG_FORBID);
    	boolean isUserFlagFreeze = aStaffExt.getFlag().equals(
    			ManagerStaff.FLAG_FREEZE);
    	if (isUserFlagForbid || isUserFlagFreeze) {
    		
    		if(isUserFlagForbid)
    		{
    			commonUserLogic.updateBelowForbid(aStaffExt.getAccount(), ManagerStaff.USER_TYPE_AGENT,currentUserType);
    			
    		}
    		else if(isUserFlagFreeze)
    		{
    			commonUserLogic.updateBelowFreeze(aStaffExt.getAccount(), ManagerStaff.USER_TYPE_AGENT,currentUserType);
    		}
    		
    	}
    	
    }
    
    /**
     * add by peter for log the change log
     * @param currentUser
     * @param changeSubType
     * @param orginalValue
     * @param newValue
     * @param modifyUser
     * @return
     */
    private ReplenishAutoSetLog setChangeLog(ManagerUser currentUser, String changeSubType, String orginalValue, String newValue,ManagerStaff modifyUser) {
		ReplenishAutoSetLog log = new ReplenishAutoSetLog();
		log.setChangeType(Constant.CHANGE_LOG_CHANGE_TYPE_USERINFO_UPDATE);
		log.setChangeSubType(changeSubType);

		log.setCreateUserID(modifyUser.getID());
		log.setShopID(currentUser.getShopsInfo().getID());
		log.setCreateTime(new Date());
		log.setNewValue(newValue);
		log.setOrginalValue(orginalValue);
		log.setCreateUserType(Integer.valueOf(modifyUser.getUserType()));
		log.setIp(currentUser.getLoginIp());
		
		log.setType(modifyUser.getUserTypeName());
		log.setTypeCode(modifyUser.getAccount());
		log.setMoneyOrgin(0);
		log.setMoneyNew(0);

		// 更新的用户信息
		log.setUpdateUserID(currentUser.getID());
		log.setUpdateUserType(Integer.valueOf(currentUser.getUserType()));
		return log;
	}

    public ISubAccountActionDao getSubAccountActionDao() {
        return subAccountActionDao;
    }

    public void setSubAccountActionDao(ISubAccountActionDao subAccountActionDao) {
        this.subAccountActionDao = subAccountActionDao;
    }

	public IUserCommissionLogic getUserCommissionLogic() {
		return userCommissionLogic;
	}

	public void setUserCommissionLogic(IUserCommissionLogic userCommissionLogic) {
		this.userCommissionLogic = userCommissionLogic;
	}

	public ICommonUserLogic getCommonUserLogic() {
		return commonUserLogic;
	}

	public void setCommonUserLogic(ICommonUserLogic commonUserLogic) {
		this.commonUserLogic = commonUserLogic;
	}

	public IReplenishAutoSetLogLogic getReplenishAutoSetLogLogic() {
		return replenishAutoSetLogLogic;
	}

	public void setReplenishAutoSetLogLogic(IReplenishAutoSetLogLogic replenishAutoSetLogLogic) {
		this.replenishAutoSetLogLogic = replenishAutoSetLogLogic;
	}
    
}
