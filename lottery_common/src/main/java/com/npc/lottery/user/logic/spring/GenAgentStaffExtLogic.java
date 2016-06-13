package com.npc.lottery.user.logic.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.dao.interf.IBranchStaffExtDao;
import com.npc.lottery.user.dao.interf.IChiefStaffExtDao;
import com.npc.lottery.user.dao.interf.IGenAgentStaffExtDao;
import com.npc.lottery.user.dao.interf.IStockholderStaffExtDao;
import com.npc.lottery.user.dao.interf.ISubAccountActionDao;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.logic.interf.ICommonUserLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.util.Page;

public class GenAgentStaffExtLogic implements IGenAgentStaffExtLogic{

    Logger logger = Logger.getLogger(AgentStaffExtLogic.class);
    private IGenAgentStaffExtDao genAgentStaffExtDao = null;
    private IBranchStaffExtDao branchStaffExtDao = null;
    private IChiefStaffExtDao chiefStaffExtDao = null;
    private IStockholderStaffExtDao stockholderStaffExtDao = null;
    private ISubAccountActionDao subAccountActionDao;
    private IUserCommissionLogic userCommissionLogic;
    protected ICommonUserLogic commonUserLogic;
    
    @Override
    public long findAmountGenAgentStaffExtList(ConditionData conditionData) {
        return 0;
    }

    @Override
    public List<GenAgentStaffExt> findGenAgentStaffExtList(
            ConditionData conditionData, int pageCurrentNo, int pageSize) {
        return null;
    }

    @Override
    public GenAgentStaffExt findGenAgentStaffExtByID(Long userID) {
        return genAgentStaffExtDao.get(userID);
    }

    public GenAgentStaffExt findById(Long userID,String scheme){
    	 return genAgentStaffExtDao.findById(userID,scheme);
    }
    
    @Override
    public Long saveGenAgentStaffExt(GenAgentStaffExt entity) {
        genAgentStaffExtDao.save(entity);
        return (long) 0;
    }

    @Override
    public void updateGenAgentStaffExt(GenAgentStaffExt entity) {
        genAgentStaffExtDao.update(entity);
    }

    @Override
    public void delGenAgentStaffExt(GenAgentStaffExt entity) {
        
    }


    @Override
    public GenAgentStaffExt queryGenAgentStaffExt(String propertyName,
            Object value) {
        GenAgentStaffExt genAgentStaffExt = new GenAgentStaffExt();
        genAgentStaffExt = genAgentStaffExtDao.findUniqueBy(propertyName, value);
//        if(genAgentStaffExt !=null){
//            for (AgentStaffExt agentStaffExt : genAgentStaffExt.getAgentStaffExtsSet()) {
//                Hibernate.initialize(agentStaffExt.getMemberStaffExtSet());
//            }
//            Hibernate.initialize(genAgentStaffExt.getMemberStaffExtSet());
//        }
        
        return genAgentStaffExt;
    }

    public IGenAgentStaffExtDao getGenAgentStaffExtDao() {
        return genAgentStaffExtDao;
    }

    public void setGenAgentStaffExtDao(IGenAgentStaffExtDao genAgentStaffExtDao) {
        this.genAgentStaffExtDao = genAgentStaffExtDao;
    }

    @Override
    public Page<GenAgentStaffExt> findPage(Page<GenAgentStaffExt> page,
            Criterion... criterions) {
        return genAgentStaffExtDao.findPage(page, criterions);
    }

	@Override
	public Page<GenAgentStaffExt> findPage(Page page, ManagerStaff userInfo, String userStatus, String account, String chName) {
		boolean isSys = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SYS);// 系统类型
		boolean isManager = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_MANAGER);// 总管类型

		if (!isSys || !isManager)// 总管和系统管理员一般
		{
			boolean isChief = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_CHIEF);// 总监类型
			boolean isBranch = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
			boolean isStockholder = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_STOCKHOLDER);// 股东
			boolean isSub = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB);//
			SubAccountInfo subInfo = new SubAccountInfo();
			if (isSub) {
				subInfo = subAccountActionDao.findUniqueBy("account", userInfo.getAccount());
			}
			boolean isSubChief = false;
			boolean isSubBranch = false;
			boolean isSubStockholder = false;
			if (subInfo != null) {
				isSubChief = ManagerStaff.USER_TYPE_CHIEF.equals(subInfo.getParentUserType());// 总监类型
				isSubBranch = ManagerStaff.USER_TYPE_BRANCH.equals(subInfo.getParentUserType());// 分公司类型
				isSubStockholder = ManagerStaff.USER_TYPE_STOCKHOLDER.equals(subInfo.getParentUserType());// 股东
			}

			List<GenAgentStaffExt> staffExts = new ArrayList<GenAgentStaffExt>();
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

				// 优化总代理列表
				//staffExts.addAll(genAgentStaffExtDao.findBy("chiefStaff", chiefStaffExt.getID()));
				searchMap.put("chiefStaff", chiefStaffExt.getID());
			} else if (isBranch || isSubBranch) {
				BranchStaffExt branchStaffExt = new BranchStaffExt();
				if (isBranch) {
					branchStaffExt = branchStaffExtDao.findUniqueBy("account", userInfo.getAccount());
				} else if (isSubBranch) {
					branchStaffExt = branchStaffExtDao.findUniqueBy("managerStaffID", subInfo.getParentStaff());
				}
				// 优化总代理列表
				//staffExts.addAll(genAgentStaffExtDao.findBy("branchStaff", branchStaffExt.getID()));
				searchMap.put("branchStaff", branchStaffExt.getID());

			} else if (isStockholder || isSubStockholder) {
				StockholderStaffExt stockholderStaffExt = new StockholderStaffExt();
				if (isStockholder) {
					stockholderStaffExt = stockholderStaffExtDao.findUniqueBy("account", userInfo.getAccount());
				} else if (isSubStockholder) {
					stockholderStaffExt = stockholderStaffExtDao.findUniqueBy("managerStaffID", subInfo.getParentStaff());
				}
				// 优化总代理列表
				//staffExts.addAll(genAgentStaffExtDao.findBy("parentStaff", stockholderStaffExt.getID().intValue()));
				searchMap.put("parentStaff", stockholderStaffExt.getID().intValue());
			}
//			staffExts.addAll(genAgentStaffExtDao.find(Restrictions.allEq(searchMap)));
			
//			List<GenAgentStaffExt> newStaffExts = new ArrayList<GenAgentStaffExt>();
//			for (int i = 0; i < staffExts.size(); i++) {
//				GenAgentStaffExt genAgentStaffExt = staffExts.get(i);
//				String acc = genAgentStaffExt.getAccount();
//				String cn = genAgentStaffExt.getChsName();
//				String status = genAgentStaffExt.getFlag();
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
//				newStaffExts.add(genAgentStaffExt);
//
//			}
//			staffExts.clear();
			
			page.setOrder("ASC");
			page.setOrderBy("account");
			page = genAgentStaffExtDao.findPage(page, Restrictions.allEq(searchMap));
			return page;
		}
		return page;
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
    public Page<GenAgentStaffExt> findSubPage(Page page, ManagerUser userInfo) {
        boolean isSys = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_SYS);// 系统类型
        boolean isManager = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_MANAGER);// 总管类型
        if (!isSys || !isManager)// 总管和系统管理员一般
        {
            boolean iGenagent = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_GEN_AGENT);// 總代理类型
            boolean iGenagentSub = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_SUB);// 子账号總代理类型
           if(iGenagent || iGenagentSub){
               GenAgentStaffExt genAgentStaffExt = new GenAgentStaffExt();
               List<SubAccountInfo> staffExts = new ArrayList<SubAccountInfo>();
               if(iGenagent){
                   genAgentStaffExt = genAgentStaffExtDao.findUniqueBy("managerStaffID",userInfo.getID());
               }else{
                   SubAccountInfo subInfo = new SubAccountInfo();
                   subInfo = subAccountActionDao.findUniqueBy("managerStaffID",userInfo.getID());
                   genAgentStaffExt = genAgentStaffExtDao.findUniqueBy("managerStaffID",subInfo.getParentStaff());
               }
               for (SubAccountInfo subAccountInfo : genAgentStaffExt.getSubAccountInfoSet()) {
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
     * 保存股東 提取相關邏輯到事物
     */
    public Long saveUserGenAgentStaff(GenAgentStaffExt entity,Long createUserId)
    {
    	genAgentStaffExtDao.save(entity); 
    	List<UserCommission> userCommissions = new ArrayList<UserCommission>();
    	Long stockId=entity.getStockholderStaffExt().getID();
        userCommissions = userCommissionLogic.queryCommission(stockId,ManagerStaff.USER_TYPE_STOCKHOLDER); // 获取用户退水，跟前台进行比较
        List<UserCommission>  genAgentCommissions=new ArrayList<UserCommission>();
        for (int i = 0; i < userCommissions.size(); i++) {
        	
        	UserCommission userCommision=new UserCommission();
        	userCommision.setCreateTime(new Date());         	
        	userCommision.setCreateUser(createUserId);
        	userCommision.setUserId(entity.getID());
        	userCommision.setCommissionA(userCommissions.get(i).getCommissionA());
        	userCommision.setCommissionB(userCommissions.get(i).getCommissionB());
        	userCommision.setCommissionC(userCommissions.get(i).getCommissionC());
        	userCommision.setPlayType(userCommissions.get(i).getPlayType());
        	userCommision.setUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
        	userCommision.setItemQuotas(userCommissions.get(i).getItemQuotas());
        	userCommision.setBettingQuotas(userCommissions.get(i).getBettingQuotas());
        	userCommision.setPlayFinalType(userCommissions.get(i).getPlayFinalType());
        	userCommision.setChiefId(userCommissions.get(i).getChiefId());
        	genAgentCommissions.add(userCommision);
            //userCommissionLogic.saveCommission(userCommision);
        	
        }
        userCommissionLogic.saveUserBatchCommission(genAgentCommissions);
        return entity.getID();
    }
    
    /*
     * 修改股東 提取相關邏輯到事物
     */
    public void updateUserGenAgentStaff(GenAgentStaffExt entity,String currentUserType)
    {

    	boolean isReplenis = entity.getReplenishment().equals(
                 ManagerStaff.REPLENIS_FORBID);
         if (isReplenis) {
         	 commonUserLogic.updateBelowReplenishment(entity.getAccount(), ManagerStaff.USER_TYPE_GEN_AGENT);
         	
         }
         boolean isUserFlagForbid = entity.getFlag().equals(
                 ManagerStaff.FLAG_FORBID);
         boolean isUserFlagFreeze = entity.getFlag().equals(
                 ManagerStaff.FLAG_FREEZE);
         if (isUserFlagForbid || isUserFlagFreeze) {
         	
         	 if(isUserFlagForbid)
           	  {
           		  commonUserLogic.updateBelowForbid(entity.getAccount(), ManagerStaff.USER_TYPE_GEN_AGENT,currentUserType);
           		  
           	  }
           	  else if(isUserFlagFreeze)
           	  {
           		  commonUserLogic.updateBelowFreeze(entity.getAccount(), ManagerStaff.USER_TYPE_GEN_AGENT,currentUserType);
           	  }
            	
         	
         }
         genAgentStaffExtDao.update(entity);
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
    
}
