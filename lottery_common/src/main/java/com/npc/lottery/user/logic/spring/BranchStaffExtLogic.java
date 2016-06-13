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
import com.npc.lottery.user.dao.interf.ISubAccountActionDao;
import com.npc.lottery.user.dao.interf.IUserCommissionJDBCDao;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.ICommonUserLogic;
import com.npc.lottery.user.logic.interf.IUserCommissionDefault;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.util.Page;

public class BranchStaffExtLogic<T> implements IBranchStaffExtLogic{

    Logger logger = Logger.getLogger(AgentStaffExtLogic.class);
    private IBranchStaffExtDao branchStaffExtDao = null;
    private IChiefStaffExtDao chiefStaffExtDao = null;
    private ISubAccountActionDao subAccountActionDao;
    private IUserCommissionJDBCDao userCommissionJDBCDao = null;
    private IUserCommissionDefault userCommissionDefaultLogic;
    private IUserCommissionLogic userCommissionLogic;
    protected ICommonUserLogic commonUserLogic;
    @Override
    public long findAmountBranchStaffExtList(ConditionData conditionData) {
        return 0;
    }

    @Override
    public List findBranchStaffExtList(ConditionData conditionData,
            int pageCurrentNo, int pageSize) {
        return null;
    }

    @Override
    public BranchStaffExt findBranchStaffExtByID(Long userID) {
        return branchStaffExtDao.get(userID);
    }

    @Override
    public Long saveBranchStaffExt(BranchStaffExt entity) {
            branchStaffExtDao.save(entity);
            return (long) 0;
    }

    @Override
    public void updateBranchStaffExt(BranchStaffExt entity) {
            branchStaffExtDao.update(entity);
    }

    @Override
    public void delBranchStaffExt(BranchStaffExt entity) {
    }


    public IBranchStaffExtDao getBranchStaffExtDao() {
        return branchStaffExtDao;
    }

    public void setBranchStaffExtDao(IBranchStaffExtDao branchStaffExtDao) {
        this.branchStaffExtDao = branchStaffExtDao;
    }

    @Override
    public BranchStaffExt queryBranchStaffExt(String propertyName, Object value) {
        BranchStaffExt branchStaffExt = new BranchStaffExt();
        branchStaffExt = branchStaffExtDao.findUniqueBy(propertyName, value);
//        if(branchStaffExt !=null){
//            for (StockholderStaffExt stockholderStaffExt : branchStaffExt.getStockholderStaffExtSet()) {
//                for (GenAgentStaffExt genAgentStaffExt : stockholderStaffExt.getGenAgentStaffExtSet()) {
//                    for (AgentStaffExt agentStaffExt : genAgentStaffExt.getAgentStaffExtsSet()) {
//                        Hibernate.initialize(agentStaffExt.getMemberStaffExtSet());
//                    }
//                    Hibernate.initialize(genAgentStaffExt.getMemberStaffExtSet());
//                }
//                Hibernate.initialize(stockholderStaffExt.getMemberStaffExtSet());
//            }
//            Hibernate.initialize(branchStaffExt.getMemberStaffExtSet());
//        }
            return branchStaffExt;
    }

    
    @Override
    public List<BranchStaffExt> queryAllBranchStaffExt(String propertyName,
            Object value) {
            return branchStaffExtDao.findBy(propertyName, value);
    }
    public Page<BranchStaffExt> findPage( Page<BranchStaffExt> page, Criterion... criterions){
            return branchStaffExtDao.findPage(page, criterions);
    }

    @Override
    public Page<BranchStaffExt> findPage(Page page, ManagerUser userInfo, String userStatus, String account, String chName) {
        boolean isSys = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_SYS);// 系统类型
        boolean isManager = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_MANAGER);// 总管类型
        if (!isSys || !isManager)// 总管和系统管理员一般
        {
            boolean isChief = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_CHIEF);// 总监类型
            boolean isSub = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_SUB);// 
            SubAccountInfo subInfo = new SubAccountInfo();
            if(isSub){
                subInfo = subAccountActionDao.findUniqueBy("account",userInfo.getAccount());
            }
            boolean isSubChief = false;
            if(subInfo !=null){
                 isSubChief  = ManagerStaff.USER_TYPE_CHIEF.equals(subInfo.getParentUserType());// 总监类型
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
			
           if(isChief || isSubChief){
               ChiefStaffExt chiefStaffExt = new ChiefStaffExt();
               List<BranchStaffExt> staffExts = new ArrayList<BranchStaffExt>();
               if(isChief){
                   chiefStaffExt = chiefStaffExtDao.findUniqueBy("account",userInfo.getAccount());
               }else if(isSubChief){
                   chiefStaffExt = chiefStaffExtDao.findUniqueBy("managerStaffID",subInfo.getParentStaff());
               }
               
               // fixed by peter 优化分公司列表
               //List<BranchStaffExt> branchStaffExtList = branchStaffExtDao.findBy("parentStaff", chiefStaffExt.getID().intValue());
               searchMap.put("parentStaff", chiefStaffExt.getID().intValue());
//               staffExts.addAll(branchStaffExtDao.find(Restrictions.allEq(searchMap)));
//               for (BranchStaffExt branchStaffExt : branchStaffExtList) {
//            	   String acc=branchStaffExt.getAccount();
//            	   String cn=branchStaffExt.getChsName();
//            	   String status=branchStaffExt.getFlag();
//            	   if(userStatus!=null&&!userStatus.equals(status))
//            		   continue;
//            	   if(account!=null&&!account.equals(acc))
//            		   continue;
//            	   if(chName!=null&&!chName.equals(cn))
//            		   continue;
//            		      
//                   staffExts.add(branchStaffExt);
//               }
               
				page.setOrder("ASC");
				page.setOrderBy("account");
				page = branchStaffExtDao.findPage(page, Restrictions.allEq(searchMap));
               return page;    
           }
        }    
        return page;
    }

    public IChiefStaffExtDao getChiefStaffExtDao() {
        return chiefStaffExtDao;
    }

    public void setChiefStaffExtDao(IChiefStaffExtDao chiefStaffExtDao) {
        this.chiefStaffExtDao = chiefStaffExtDao;
    }

    @Override
    public Page<BranchStaffExt> findSubPage(Page page, ManagerUser userInfo) {
        boolean isSys = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_SYS);// 系统类型
        boolean isManager = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_MANAGER);// 总管类型
        if (!isSys || !isManager)// 总管和系统管理员一般
        {
            boolean isBranch = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
            boolean isBranchSub = userInfo.getUserType().equals(
                    ManagerStaff.USER_TYPE_SUB);// 子账号分公司类型
           if(isBranch || isBranchSub){
               BranchStaffExt branchStaffExt = new BranchStaffExt();
               List<SubAccountInfo> staffExts = new ArrayList<SubAccountInfo>();
               if(isBranch){
                   branchStaffExt = branchStaffExtDao.findUniqueBy("managerStaffID",userInfo.getID());
               }else if(isBranchSub){
                   SubAccountInfo subInfo = new SubAccountInfo();
                   subInfo = subAccountActionDao.findUniqueBy("managerStaffID",userInfo.getID());
                   branchStaffExt = branchStaffExtDao.findUniqueBy("managerStaffID",subInfo.getParentStaff());
               }
              
               for (SubAccountInfo subAccountInfo : branchStaffExt.getSubAccountInfoSet()) {
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
     * 保存分公司 提取相關邏輯到事物
     */
    public Long saveUserBranchStaff(BranchStaffExt entity,Long chiefId,Long createUserId)
    {
    	
    	List<UserCommissionDefault> userCommissionDefaults = new ArrayList<UserCommissionDefault>();        
    	branchStaffExtDao.save(entity);
    	//新开户退水数据
        List<UserCommission>  userCommissions=new ArrayList<UserCommission>();
        
    	//查询默认分公司新开户退水，DEFAULT_COMMISSION 是否为默认新开户 退水  1:是 ,0:否
        List<Criterion> branchCriterion=new ArrayList<Criterion>();
        branchCriterion.add(Restrictions.eq("parentStaff",chiefId.intValue()));
        branchCriterion.add(Restrictions.eq("defaultCommission",1));
    	BranchStaffExt branch=branchStaffExtDao.findUnique(branchCriterion.toArray(new Criterion[branchCriterion.size()]));
    	if(null == branch){	
    		//不存在，取总监退水，否则取默认分公司的
    		userCommissionDefaults = userCommissionDefaultLogic.queryCommissionDefault(chiefId);
    		if(userCommissionDefaults==null)
    	           throw new RuntimeException("查詢總監退水設置 為空");
    	        for (int i = 0; i < userCommissionDefaults.size(); i++) {
    	        	double ca=userCommissionDefaults.get(i).getCommissionA();
    	        	double cb=userCommissionDefaults.get(i).getCommissionB();
    	        	double cc=userCommissionDefaults.get(i).getCommissionC();
    	        	//ca=100-ca;
    	        	//cb=100-cb;
    	        	//cc=100-cc;
    	        	UserCommission userCommision=new UserCommission();
    	        	userCommision.setChiefId(userCommissionDefaults.get(i).getUserId());
    	        	userCommision.setCreateTime(new Date());         	
    	        	userCommision.setCreateUser(createUserId);
    	        	userCommision.setUserId(entity.getID());
    	        	userCommision.setUserType(entity.getUserType());
    	        	userCommision.setCommissionA(ca);
    	        	userCommision.setCommissionB(cb);
    	        	userCommision.setCommissionC(cc);
    	        	userCommision.setPlayType(userCommissionDefaults.get(i).getPlayType());
    	        	userCommision.setUserType(ManagerStaff.USER_TYPE_BRANCH);
    	        	userCommision.setItemQuotas(userCommissionDefaults.get(i).getItemQuotas());
    	        	userCommision.setBettingQuotas(userCommissionDefaults.get(i).getBettingQuotas());
    	        	userCommision.setPlayFinalType(userCommissionDefaults.get(i).getPlayFinalType());
    	        	userCommision.setPlayType(userCommissionDefaults.get(i).getPlayType());
    	        	
    	        	userCommissions.add(userCommision);
    	            
    	        }
    	}else{
    		//取默认分公司的
    		List<UserCommission>  userCommissionList=userCommissionLogic.queryCommission(Long.valueOf(branch.getManagerStaffID()),ManagerStaff.USER_TYPE_BRANCH);
    		for (UserCommission userCommission : userCommissionList) {
    			double ca=userCommission.getCommissionA();
            	double cb=userCommission.getCommissionB();
            	double cc=userCommission.getCommissionC();
            	//ca=100-ca;
            	//cb=100-cb;
            	//cc=100-cc;
            	UserCommission commision=new UserCommission();
            	commision.setChiefId(chiefId);
            	commision.setCreateTime(new Date());         	
            	commision.setCreateUser(createUserId);
            	commision.setUserId(entity.getID());
            	commision.setCommissionA(ca);
            	commision.setCommissionB(cb);
            	commision.setCommissionC(cc);
            	commision.setPlayType(userCommission.getPlayType());
            	commision.setUserType(ManagerStaff.USER_TYPE_BRANCH);
            	commision.setItemQuotas(userCommission.getItemQuotas());
            	commision.setBettingQuotas(userCommission.getBettingQuotas());
            	commision.setPlayFinalType(userCommission.getPlayFinalType());
            	commision.setPlayType(userCommission.getPlayType());
            	userCommissions.add(commision);
    		}
    	}
        userCommissionLogic.saveUserBatchCommission(userCommissions);
        return entity.getID();
    	
    }
    
    /*
     * 修改分公司 提取相關邏輯到事物
     */
    public void updateUserBranchStaff(BranchStaffExt dbBranch,String currentUserType)
    {
    	
    	  // 如果上级禁止飞走，下级用户全部改成禁止
        boolean isReplenis = dbBranch.getReplenishment().equals(ManagerStaff.REPLENIS_FORBID);
        if (isReplenis) {
        	
      	  commonUserLogic.updateBelowReplenishment(dbBranch.getAccount(), ManagerStaff.USER_TYPE_BRANCH);
        	
        }
        // 如果上级用户禁止或者冻结，下级用户全部改成禁止或者冻结
        boolean isUserFlagForbid = ManagerStaff.FLAG_FORBID.equals(dbBranch.getFlag());
        boolean isUserFlagFreeze = ManagerStaff.FLAG_FREEZE.equals(dbBranch.getFlag());
        if (isUserFlagForbid || isUserFlagFreeze) {
      	  if(isUserFlagForbid)
      	  {
      		  commonUserLogic.updateBelowForbid(dbBranch.getAccount(), ManagerStaff.USER_TYPE_BRANCH,currentUserType);
      		  
      	  }
      	  else if(isUserFlagFreeze)
      	  {
      		  commonUserLogic.updateBelowFreeze(dbBranch.getAccount(), ManagerStaff.USER_TYPE_BRANCH,currentUserType);
      	  }
        	
        	
        	
        }
        
        branchStaffExtDao.update(dbBranch);
    }
    
    @Override
	public BranchStaffExt findById(long id, String scheme) {
		
		return branchStaffExtDao.findById(id, scheme);
	}
    
    public ISubAccountActionDao getSubAccountActionDao() {
        return subAccountActionDao;
    }

    public void setSubAccountActionDao(ISubAccountActionDao subAccountActionDao) {
        this.subAccountActionDao = subAccountActionDao;
    }

	public IUserCommissionJDBCDao getUserCommissionJDBCDao() {
		return userCommissionJDBCDao;
	}

	public void setUserCommissionJDBCDao(
			IUserCommissionJDBCDao userCommissionJDBCDao) {
		this.userCommissionJDBCDao = userCommissionJDBCDao;
	}

	public IUserCommissionDefault getUserCommissionDefaultLogic() {
		return userCommissionDefaultLogic;
	}

	public void setUserCommissionDefaultLogic(
			IUserCommissionDefault userCommissionDefaultLogic) {
		this.userCommissionDefaultLogic = userCommissionDefaultLogic;
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
