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
import com.npc.lottery.user.dao.interf.IStockholderStaffExtDao;
import com.npc.lottery.user.dao.interf.ISubAccountActionDao;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.logic.interf.ICommonUserLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.util.Page;

public class StockholderStaffExtLogic implements IStockholderStaffExtLogic{

    Logger logger = Logger.getLogger(AgentStaffExtLogic.class);
    private IStockholderStaffExtDao stockholderStaffExtDao = null;
    private IBranchStaffExtDao branchStaffExtDao = null;
    private IChiefStaffExtDao chiefStaffExtDao = null;
    private ISubAccountActionDao subAccountActionDao;
    private IUserCommissionLogic userCommissionLogic;
    protected ICommonUserLogic commonUserLogic;
    @Override
    public long findAmountStockholderStaffExtList(ConditionData conditionData) {
        return 0;
    }

    @Override
    public List<StockholderStaffExt> findStockholderStaffExtList(
            ConditionData conditionData, int pageCurrentNo, int pageSize) {
        return null;
    }

    @Override
    public StockholderStaffExt findStockholderStaffExtByID(Long userID) {
        return stockholderStaffExtDao.get(userID);
    }

    @Override
    public Long saveStockholderStaffExt(StockholderStaffExt entity) {
            stockholderStaffExtDao.save(entity);
            return (long) 0;
    }

    @Override
    public void updateStockholderStaffExt(StockholderStaffExt entity) {
        stockholderStaffExtDao.update(entity);
        
    }

    @Override
    public void delStockholderStaffExt(StockholderStaffExt entity) {
    }


    @Override
    public StockholderStaffExt queryStockholderStaffExt(String propertyName,
            Object value) {
        StockholderStaffExt staffExt = new StockholderStaffExt();
        staffExt = stockholderStaffExtDao.findUniqueBy(propertyName, value);
//        if(staffExt != null){
//            for (GenAgentStaffExt genAgentStaffExt : staffExt.getGenAgentStaffExtSet()) {
//                for (AgentStaffExt agentStaffExt : genAgentStaffExt.getAgentStaffExtsSet()) {
//                    Hibernate.initialize(agentStaffExt.getMemberStaffExtSet());
//                }
//                Hibernate.initialize(genAgentStaffExt.getMemberStaffExtSet());
//            }
//            Hibernate.initialize(staffExt.getMemberStaffExtSet());
//        }
            return staffExt;
    }

    public StockholderStaffExt findById(long id, String scheme){
    	return stockholderStaffExtDao.findById(id, scheme);
    }
    
    @Override
    public Page<StockholderStaffExt> findPage(Page<StockholderStaffExt> page,
            Criterion... criterions) {
            return stockholderStaffExtDao.findPage(page, criterions);
    }

    public IStockholderStaffExtDao getStockholderStaffExtDao() {
        return stockholderStaffExtDao;
    }

    public void setStockholderStaffExtDao(
            IStockholderStaffExtDao stockholderStaffExtDao) {
        this.stockholderStaffExtDao = stockholderStaffExtDao;
    }

	@Override
	public Page<StockholderStaffExt> findPage(Page page, ManagerStaff userInfo, String userStatus, String account, String chName) {
		boolean isSys = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SYS);// 系统类型
		boolean isManager = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_MANAGER);// 总管类型

		if (!isSys || !isManager)// 总管和系统管理员一般
		{
			List<StockholderStaffExt> staffExts = new ArrayList<StockholderStaffExt>();
			boolean isChief = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_CHIEF);// 总监类型
			boolean isBranch = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
			boolean isSub = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB);//
			SubAccountInfo subInfo = new SubAccountInfo();
			if (isSub) {
				subInfo = subAccountActionDao.findUniqueBy("account", userInfo.getAccount());
			}
			boolean isSubChief = false;
			boolean isSubBranch = false;
			if (subInfo != null) {
				isSubChief = ManagerStaff.USER_TYPE_CHIEF.equals(subInfo.getParentUserType());// 总监类型
				isSubBranch = ManagerStaff.USER_TYPE_BRANCH.equals(subInfo.getParentUserType());// 分公司类型
			}
			
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

				// 优化股东列表
				//staffExts.addAll(stockholderStaffExtDao.findBy("chiefStaff", chiefStaffExt.getID()));
				searchMap.put("chiefStaff", chiefStaffExt.getID());
			} else if (isBranch || isSubBranch) {
				BranchStaffExt branchStaffExt = new BranchStaffExt();
				if (isBranch) {
					branchStaffExt = branchStaffExtDao.findUniqueBy("account", userInfo.getAccount());
				} else if (isSubBranch) {
					branchStaffExt = branchStaffExtDao.findUniqueBy("managerStaffID", subInfo.getParentStaff());
				}
				// 优化股东列表
				//staffExts.addAll(stockholderStaffExtDao.findBy("parentStaff", branchStaffExt.getID().intValue()));
				searchMap.put("parentStaff", branchStaffExt.getID().intValue());
			}
//			staffExts.addAll(stockholderStaffExtDao.find(Restrictions.allEq(searchMap)));
//			List<StockholderStaffExt> newStaffExts = new ArrayList<StockholderStaffExt>();
//			for (int i = 0; i < staffExts.size(); i++) {
//				StockholderStaffExt stockholderStaffExt = staffExts.get(i);
//				String acc = stockholderStaffExt.getAccount();
//				String cn = stockholderStaffExt.getChsName();
//				String status = stockholderStaffExt.getFlag();
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
//				newStaffExts.add(stockholderStaffExt);
//
//			}
//			staffExts.clear();
			page.setOrder("ASC");
			page.setOrderBy("account");
			page = stockholderStaffExtDao.findPage(page, Restrictions.allEq(searchMap));
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

    @Override
    public Page<StockholderStaffExt> findSubPage(Page page, ManagerUser userInfo) {
        boolean isSys = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_SYS);// 系统类型
        boolean isManager = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_MANAGER);// 总管类型
        if (!isSys || !isManager)// 总管和系统管理员一般
        {
            boolean iStock = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_STOCKHOLDER);// 股東类型
            boolean iStockSub = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_SUB);// 子账号股東类型
           if(iStock || iStockSub){
               StockholderStaffExt stockholderStaffExt = new StockholderStaffExt();
               List<SubAccountInfo> staffExts = new ArrayList<SubAccountInfo>();
               if(iStock){
                   stockholderStaffExt = stockholderStaffExtDao.findUniqueBy("managerStaffID",userInfo.getID());
               }else if(iStockSub){
                   SubAccountInfo subInfo = new SubAccountInfo();
                   subInfo = subAccountActionDao.findUniqueBy("managerStaffID",userInfo.getID());
                   stockholderStaffExt = stockholderStaffExtDao.findUniqueBy("managerStaffID",subInfo.getParentStaff());
               }
               
               for (SubAccountInfo subAccountInfo : stockholderStaffExt.getSubAccountInfoSet()) {
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

    public ISubAccountActionDao getSubAccountActionDao() {
        return subAccountActionDao;
    }

    public void setSubAccountActionDao(ISubAccountActionDao subAccountActionDao) {
        this.subAccountActionDao = subAccountActionDao;
    }
    /*
     * 保存股東 提取相關邏輯到事物
     */
    public Long saveUserStockholderStaff(StockholderStaffExt entity,Long createUserId)
    {
    	
    	  stockholderStaffExtDao.save(entity); 
    	  //branchStaffExtDao.update(bahStEt); 	
    	  Long branchId=entity.getBranchStaffExt().getID();
    	  List<UserCommission> userCommissions = new ArrayList<UserCommission>();  
          userCommissions = userCommissionLogic.queryCommission(branchId,ManagerStaff.USER_TYPE_BRANCH); 
          List<UserCommission>  stockCommissions=new ArrayList<UserCommission>();
          for (int i = 0; i < userCommissions.size(); i++) {
          	UserCommission userCommision=new UserCommission();
          	userCommision.setCreateTime(new Date());         	
          	userCommision.setCreateUser(createUserId);
          	userCommision.setUserId(entity.getID());
          	userCommision.setUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
          	userCommision.setCommissionA(userCommissions.get(i).getCommissionA());
          	userCommision.setCommissionB(userCommissions.get(i).getCommissionB());
          	userCommision.setCommissionC(userCommissions.get(i).getCommissionC());
          	userCommision.setPlayType(userCommissions.get(i).getPlayType());
          	userCommision.setUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
          	userCommision.setItemQuotas(userCommissions.get(i).getItemQuotas());
          	userCommision.setBettingQuotas(userCommissions.get(i).getBettingQuotas());
          	userCommision.setPlayFinalType(userCommissions.get(i).getPlayFinalType());  
          	userCommision.setChiefId(userCommissions.get(i).getChiefId());
          	stockCommissions.add(userCommision);
              //userCommissionLogic.saveCommission(userCommision);
          }
          userCommissionLogic.saveUserBatchCommission(stockCommissions);
    	
    	
    	return entity.getID();
    	
    }
    
    /*
     * 修改股東 提取相關邏輯到事物
     */
    public void updateUserStockholderStaff(StockholderStaffExt entity,String currentUserType)
    {
    	  // 如果上级禁止飞走，下级用户全部改成禁止
    	
    	 stockholderStaffExtDao.update(entity);

    	boolean isReplenis = ManagerStaff.REPLENIS_FORBID.equals(entity.getReplenishment());
        if (isReplenis) {
        	
        	 commonUserLogic.updateBelowReplenishment(entity.getAccount(), ManagerStaff.USER_TYPE_STOCKHOLDER);
        }
        boolean isUserFlagForbid = ManagerStaff.FLAG_FORBID.equals(entity.getFlag());
        boolean isUserFlagFreeze = ManagerStaff.FLAG_FREEZE.equals(entity.getFlag());
        if (isUserFlagForbid || isUserFlagFreeze) {
        	 if(isUserFlagForbid)
       	  {
       		  commonUserLogic.updateBelowForbid(entity.getAccount(), ManagerStaff.USER_TYPE_STOCKHOLDER,currentUserType);
       		  
       	  }
       	  else if(isUserFlagFreeze)
       	  {
       		  commonUserLogic.updateBelowFreeze(entity.getAccount(), ManagerStaff.USER_TYPE_STOCKHOLDER,currentUserType);
       	  }
 	
         } 
        //branchStaffExtDao.update(entity.getBranchStaffExt());
       
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
