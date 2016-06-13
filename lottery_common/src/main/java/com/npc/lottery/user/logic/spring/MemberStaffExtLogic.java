package com.npc.lottery.user.logic.spring;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.user.dao.interf.IAgentStaffExtDao;
import com.npc.lottery.user.dao.interf.IBranchStaffExtDao;
import com.npc.lottery.user.dao.interf.IChiefStaffExtDao;
import com.npc.lottery.user.dao.interf.IGenAgentStaffExtDao;
import com.npc.lottery.user.dao.interf.IMemberStaffExtDao;
import com.npc.lottery.user.dao.interf.IStockholderStaffExtDao;
import com.npc.lottery.user.dao.interf.ISubAccountActionDao;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.logic.interf.ICommonUserLogic;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.util.MD5;
import com.npc.lottery.util.Page;

public class MemberStaffExtLogic implements IMemberStaffExtLogic {

    Logger logger = Logger.getLogger(MemberStaffExtLogic.class);
    private IMemberStaffExtDao memberStaffExtDao = null;
    private IAgentStaffExtDao agentStaffExtDao = null;
    private IBranchStaffExtDao branchStaffExtDao = null;
    private IChiefStaffExtDao chiefStaffExtDao = null;
    private IGenAgentStaffExtDao genAgentStaffExtDao = null;
    private IStockholderStaffExtDao stockholderStaffExtDao = null;
    private ISubAccountActionDao subAccountActionDao;
    private IUserCommissionLogic userCommissionLogic;
    protected ICommonUserLogic commonUserLogic;
    private IReplenishAutoSetLogLogic replenishAutoSetLogLogic;

    @Override
    public long findAmountMemberStaffExtList(ConditionData conditionData) {
        return 0;
    }

    @Override
    public List<MemberStaffExt> findMemberStaffExtList(
            ConditionData conditionData, int pageCurrentNo, int pageSize) {
        return null;
    }

    @Override
    public MemberStaffExt findMemberStaffExtByID(Long userID) {
        return memberStaffExtDao.findUniqueBy("memberStaffID", userID);
    }

    @Override
    public Long saveMemberStaffExt(MemberStaffExt entity) {
        memberStaffExtDao.save(entity);
        return (long) 0;
    }

    @Override
    public void updateMemberStaffExt(MemberStaffExt entity) {
        memberStaffExtDao.update(entity);
    }

    @Override
    public void delMemberStaffExt(MemberStaffExt entity) {

    }
    @Override
    public MemberStaffExt queryMemberStaffExt(String propertyName, Object value) {
        return memberStaffExtDao.findUniqueBy(propertyName, value);
    }

    public IMemberStaffExtDao getMemberStaffExtDao() {
        return memberStaffExtDao;
    }

    public void setMemberStaffExtDao(IMemberStaffExtDao memberStaffExtDao) {
        this.memberStaffExtDao = memberStaffExtDao;
    }

    @Override
    public Page<MemberStaffExt> findPage(Page<MemberStaffExt> page,
            Criterion... criterions) {
        return memberStaffExtDao.findPage(page, criterions);
    }

	public Page<MemberStaffExt> findPage(Page page, ManagerStaff userInfo, String userStatus, String userManager, String account, String chName) {
		boolean isSys = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SYS);// 系统类型
		boolean isManager = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_MANAGER);// 总管类型

		List<MemberStaffExt> staffExts = new ArrayList<MemberStaffExt>();
		if (!isSys || !isManager)// 总管和系统管理员一般不操作
		{
			boolean isChief = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_CHIEF);// 总监类型
			boolean isBranch = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
			boolean isStockholder = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_STOCKHOLDER);// 股东
			boolean isGenAgent = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_GEN_AGENT);// 总代理
			boolean isAgent = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_AGENT);// 代理
			boolean isSub = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB);//
			SubAccountInfo subInfo = new SubAccountInfo();
			if (isSub) {
				subInfo = subAccountActionDao.findUniqueBy("account", userInfo.getAccount());
			}
			boolean isSubChief = false;
			boolean isSubBranch = false;
			boolean isSubStockholder = false;
			boolean isSubGenAgent = false;
			boolean isSubAgent = false;
			if (subInfo != null) {
				isSubChief = ManagerStaff.USER_TYPE_CHIEF.equals(subInfo.getParentUserType());// 总监类型
				isSubBranch = ManagerStaff.USER_TYPE_BRANCH.equals(subInfo.getParentUserType());// 分公司类型
				isSubStockholder = ManagerStaff.USER_TYPE_STOCKHOLDER.equals(subInfo.getParentUserType());// 股东
				isSubGenAgent = ManagerStaff.USER_TYPE_GEN_AGENT.equals(subInfo.getParentUserType());// 总代理
				isSubAgent = ManagerStaff.USER_TYPE_AGENT.equals(subInfo.getParentUserType());// 总代理
			}
			
			/*
			 * add by peter for 用户会员列表优化
			 */
			Map<String,Object> searchMap = new HashMap<String, Object>();
			if (userStatus != null) {
				searchMap.put("flag", userStatus);
			}
			if (userManager != null) {
				searchMap.put("parentUserType", userManager);
			}
			if (account != null) {
				searchMap.put("account", account);
			}
			if (chName != null) {
				searchMap.put("chsName", chName);
			}
			
			if (isChief || isSubChief) {// 总监进来
				// 查询总监
				ChiefStaffExt chiefStaffExt = new ChiefStaffExt();
				if (isChief) {
					chiefStaffExt = chiefStaffExtDao.findUniqueBy("account", userInfo.getAccount());
				} else if (isSubChief) {
					chiefStaffExt = chiefStaffExtDao.findUniqueBy("managerStaffID", subInfo.getParentStaff());
				}
				// fixed by peter 优化会员列表
				//staffExts.addAll(memberStaffExtDao.findBy("chiefStaff", chiefStaffExt.getID()));
				searchMap.put("chiefStaff", chiefStaffExt.getID());
			} else if (isBranch || isSubBranch) {// 分公司进来
				BranchStaffExt branchStaffExt = new BranchStaffExt();
				if (isBranch) {
					branchStaffExt = branchStaffExtDao.findUniqueBy("account", userInfo.getAccount());
				} else if (isSubBranch) {
					branchStaffExt = branchStaffExtDao.findUniqueBy("managerStaffID", subInfo.getParentStaff());
				}

				// fixed by peter 优化会员列表
				//staffExts.addAll(memberStaffExtDao.findBy("branchStaff", branchStaffExt.getID()));
				searchMap.put("branchStaff", branchStaffExt.getID());
			} else if (isStockholder || isSubStockholder) {// 股东进来
				StockholderStaffExt stockholderStaffExt = new StockholderStaffExt();
				if (isStockholder) {
					stockholderStaffExt = stockholderStaffExtDao.findUniqueBy("account", userInfo.getAccount());
				} else if (isSubStockholder) {
					stockholderStaffExt = stockholderStaffExtDao.findUniqueBy("managerStaffID", subInfo.getParentStaff());
				}
				// fixed by peter 优化会员列表
				//staffExts.addAll(memberStaffExtDao.findBy("stockholderStaff", stockholderStaffExt.getID()));
				searchMap.put("stockholderStaff", stockholderStaffExt.getID());
			} else if (isGenAgent || isSubGenAgent) {// 总代理
				GenAgentStaffExt genAgentStaffExt = new GenAgentStaffExt();
				if (isGenAgent) {
					genAgentStaffExt = genAgentStaffExtDao.findUniqueBy("account", userInfo.getAccount());
				} else if (isSubGenAgent) {
					genAgentStaffExt = genAgentStaffExtDao.findUniqueBy("managerStaffID", subInfo.getParentStaff());
				}
				// fixed by peter 优化会员列表
				//staffExts.addAll(memberStaffExtDao.findBy("genAgentStaff", genAgentStaffExt.getID()));
				searchMap.put("genAgentStaff", genAgentStaffExt.getID());

			} else if (isAgent || isSubAgent) {
				AgentStaffExt agentStaffExt = new AgentStaffExt();
				if (isAgent) {
					agentStaffExt = agentStaffExtDao.findUniqueBy("account", userInfo.getAccount());
				} else if (isSubAgent) {
					agentStaffExt = agentStaffExtDao.findUniqueBy("managerStaffID", subInfo.getParentStaff());
				}
				// fixed by peter 优化会员列表
				//staffExts.addAll(memberStaffExtDao.findBy("agentStaff", agentStaffExt.getID()));
				searchMap.put("agentStaff", agentStaffExt.getID());
			}
			//staffExts.addAll(memberStaffExtDao.find(Restrictions.allEq(searchMap)));
			page.setOrder("ASC");
			page.setOrderBy("account");
			page = memberStaffExtDao.findPage(page, Restrictions.allEq(searchMap));
		}
//		List<MemberStaffExt> newStaffExts = new ArrayList<MemberStaffExt>();
//		for (int i = 0; i < staffExts.size(); i++) {
//			MemberStaffExt memberStaffExt = staffExts.get(i);
//			String flag = memberStaffExt.getFlag();
//			String parent = memberStaffExt.getParentUserType();
//			String acc = memberStaffExt.getAccount();
//			String cn = memberStaffExt.getChsName();
//			if (userStatus != null && !userStatus.equals(flag))
//				continue;
//			if (userManager != null && !userManager.equals(parent))
//				continue;
//			if (account != null && !account.equals(acc))
//				continue;
//			if (chName != null && !chName.equals(cn))
//				continue;
//
//			newStaffExts.add(memberStaffExt);
//
//		}
//		staffExts.clear();
//		int first = page.getFirst() - 1;
//		int last = first + page.getPageSize();
//		if (last > staffExts.size())
//			last = staffExts.size();
//		Collections.sort(staffExts);
//		page.setTotalCount(staffExts.size());
//		page.setResult(staffExts.subList(first, last));
		return page;
	}

    public IAgentStaffExtDao getAgentStaffExtDao() {
        return agentStaffExtDao;
    }

    public void setAgentStaffExtDao(IAgentStaffExtDao agentStaffExtDao) {
        this.agentStaffExtDao = agentStaffExtDao;
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

    public IGenAgentStaffExtDao getGenAgentStaffExtDao() {
        return genAgentStaffExtDao;
    }

    public void setGenAgentStaffExtDao(IGenAgentStaffExtDao genAgentStaffExtDao) {
        this.genAgentStaffExtDao = genAgentStaffExtDao;
    }

    public IStockholderStaffExtDao getStockholderStaffExtDao() {
        return stockholderStaffExtDao;
    }

    public void setStockholderStaffExtDao(
            IStockholderStaffExtDao stockholderStaffExtDao) {
        this.stockholderStaffExtDao = stockholderStaffExtDao;
    }

/*    @Override
    public Map<String, Integer> findRate(MemberStaffExt memberStaffExt) {
        Map<String,Integer> rateMap = new HashMap<String, Integer>();
        if(memberStaffExt.getManagerStaff() instanceof AgentStaffExt){
            AgentStaffExt agentStaffExt  = ((AgentStaffExt) (memberStaffExt.getManagerStaff()));
            int agentRate = 0;
            int genAgentRate = 0;
            int shareholderRate = 0;
            int branchRate = 0;
            int chiefRate = 0;
            agentRate = memberStaffExt.getRate();
            rateMap.put("agentRate",agentRate);
            if(Constant.TRUE_STATUS.equals(agentStaffExt.getPureAccounted())){
                genAgentRate  = agentStaffExt.getGenAgentRate();
                rateMap.put("genAgentRate",genAgentRate);
            }else{
                int tempRate = 0;
                int countRate = 0;
                tempRate = agentStaffExt.getGenAgentRate() - agentStaffExt.getAgentRate();
                countRate = agentStaffExt.getAgentRate() - memberStaffExt.getRate();
                genAgentRate = tempRate+countRate;
                rateMap.put("genAgentRate",genAgentRate);
            }
            if(Constant.TRUE_STATUS.equals(agentStaffExt.getGenAgentStaffExt().getPureAccounted())){
                shareholderRate = agentStaffExt.getGenAgentStaffExt().getShareholderRate();
                rateMap.put("shareholderRate",shareholderRate);
            }else{
                int tempRate = 0;
                int countRate = 0;
                tempRate = agentStaffExt.getGenAgentStaffExt().getShareholderRate() - agentStaffExt.getGenAgentStaffExt().getGenAgentRate();
                countRate = agentStaffExt.getGenAgentStaffExt().getGenAgentRate() - genAgentRate - agentRate;
                shareholderRate = tempRate + countRate;
                rateMap.put("shareholderRate",shareholderRate);
            }
            if(Constant.TRUE_STATUS.equals(agentStaffExt.getGenAgentStaffExt().getStockholderStaffExt().getPureAccounted())){
                branchRate = agentStaffExt.getGenAgentStaffExt().getStockholderStaffExt().getBranchRate();
                rateMap.put("branchRate",branchRate);
            }else{
                int tempRate = 0;
                int countRate = 0;
                tempRate = agentStaffExt.getGenAgentStaffExt().getStockholderStaffExt().getBranchRate() - agentStaffExt.getGenAgentStaffExt().getStockholderStaffExt().getShareholderRate();
                countRate = agentStaffExt.getGenAgentStaffExt().getStockholderStaffExt().getShareholderRate() - shareholderRate - genAgentRate - agentRate;
                branchRate = tempRate + countRate;
                rateMap.put("branchRate",branchRate);
            }
            chiefRate = 100 - branchRate - shareholderRate - genAgentRate - agentRate;
            rateMap.put("chiefRate",chiefRate);
            
        }else if(memberStaffExt.getManagerStaff() instanceof GenAgentStaffExt){
            int agentRate = 0;
            int genAgentRate = 0;
            int shareholderRate = 0;
            int branchRate = 0;
            int chiefRate = 0;
            GenAgentStaffExt genAgentStaffExt  = ((GenAgentStaffExt) (memberStaffExt.getManagerStaff()));
            genAgentRate = memberStaffExt.getRate();
            rateMap.put("genAgentRate",genAgentRate);
            rateMap.put("agentRate",agentRate);
            if(Constant.TRUE_STATUS.equals(genAgentStaffExt.getPureAccounted())){
                shareholderRate = genAgentStaffExt.getShareholderRate();
                rateMap.put("shareholderRate",shareholderRate);
            }else{
                int tempRate = 0;
                int countRate = 0;
                tempRate = genAgentStaffExt.getShareholderRate() - genAgentStaffExt.getGenAgentRate();
                countRate = genAgentStaffExt.getGenAgentRate() - genAgentRate;
                shareholderRate = tempRate + countRate;
                rateMap.put("shareholderRate",shareholderRate);
            }
            if(Constant.TRUE_STATUS.equals(genAgentStaffExt.getStockholderStaffExt().getPureAccounted())){
                branchRate = genAgentStaffExt.getStockholderStaffExt().getBranchRate();
                rateMap.put("branchRate",branchRate);
            }else{
                int tempRate = 0;
                int countRate = 0;
                tempRate = genAgentStaffExt.getStockholderStaffExt().getBranchRate() - genAgentStaffExt.getStockholderStaffExt().getShareholderRate();
                countRate = genAgentStaffExt.getStockholderStaffExt().getShareholderRate() - shareholderRate - genAgentRate - agentRate;
                branchRate = tempRate + countRate;
                rateMap.put("branchRate",branchRate);
            }
            chiefRate = 100 - branchRate - shareholderRate - genAgentRate - agentRate;
            rateMap.put("chiefRate",chiefRate);
            
        }else if(memberStaffExt.getManagerStaff() instanceof StockholderStaffExt){
            int agentRate = 0;
            int genAgentRate = 0;
            int shareholderRate = 0;
            int branchRate = 0;
            int chiefRate = 0;
            StockholderStaffExt stockholderStaffExt  = ((StockholderStaffExt) (memberStaffExt.getManagerStaff()));
            shareholderRate = memberStaffExt.getRate();
            rateMap.put("shareholderRate",shareholderRate);
            rateMap.put("agentRate",agentRate);
            rateMap.put("genAgentRate",genAgentRate);
            if(Constant.TRUE_STATUS.equals(stockholderStaffExt.getPureAccounted())){
                branchRate = stockholderStaffExt.getBranchRate();
                rateMap.put("branchRate",branchRate);
            }else{
                int tempRate = 0;
                int countRate = 0;
                tempRate = stockholderStaffExt.getBranchRate() - stockholderStaffExt.getShareholderRate();
                countRate = stockholderStaffExt.getShareholderRate() - shareholderRate;
                branchRate = tempRate + countRate;
                rateMap.put("branchRate",branchRate);
            }
            chiefRate = 100 - branchRate - shareholderRate - genAgentRate - agentRate;
            rateMap.put("chiefRate",chiefRate);
            
        }else if(memberStaffExt.getManagerStaff() instanceof BranchStaffExt){
            int agentRate = 0;
            int genAgentRate = 0;
            int shareholderRate = 0;
            int branchRate = 0;
            int chiefRate = 0;
            branchRate = memberStaffExt.getRate();
            rateMap.put("branchRate",branchRate);
            rateMap.put("shareholderRate",shareholderRate);
            rateMap.put("agentRate",agentRate);
            rateMap.put("genAgentRate",genAgentRate);
            chiefRate = 100 - branchRate - shareholderRate - genAgentRate - agentRate;
            rateMap.put("chiefRate",chiefRate);
        }else if(memberStaffExt.getManagerStaff() instanceof ChiefStaffExt){
            int agentRate = 0;
            int genAgentRate = 0;
            int shareholderRate = 0;
            int branchRate = 0;
            int chiefRate = 0;
            rateMap.put("branchRate",branchRate);
            rateMap.put("shareholderRate",shareholderRate);
            rateMap.put("agentRate",agentRate);//总监直属会员，所以下面占成全不占
            rateMap.put("genAgentRate",genAgentRate);
            chiefRate = 100;
            rateMap.put("chiefRate",chiefRate);
        }
        return rateMap;
    }*/
 //待优化
    @Override
    public Map<String, Integer> findRate(MemberStaffExt memberStaffExt) {
        Map<String,Integer> rateMap = new HashMap<String, Integer>();
        ManagerStaff managerStaff=memberStaffExt.getManagerStaff();
        AgentStaffExt agentStaffExt=null;
        GenAgentStaffExt genAgent=null;
        StockholderStaffExt stockHold=null;
        BranchStaffExt branch=null;
        ChiefStaffExt chief=null;
        int agentRate = 0;
        int genAgentRate = 0;
        int shareholderRate = 0;
        int branchRate = 0;
        int chiefRate = 0;
        int leftRate=0;
        if(managerStaff instanceof AgentStaffExt){
        	 agentStaffExt  = ((AgentStaffExt) managerStaff);
             genAgent=agentStaffExt.getGenAgentStaffExt();
             stockHold=genAgent.getStockholderStaffExt();
             branch=stockHold.getBranchStaffExt();
             agentRate = memberStaffExt.getRate();
             genAgentRate= agentStaffExt.getGenAgentRate();
             shareholderRate=genAgent.getShareholderRate();
             branchRate=stockHold.getBranchRate();
             chiefRate=branch.getChiefRate();
        
        }
        else if(managerStaff instanceof GenAgentStaffExt)
        {
        	 genAgent=((GenAgentStaffExt) managerStaff);
             stockHold=genAgent.getStockholderStaffExt();
             branch=stockHold.getBranchStaffExt();
             genAgentRate=memberStaffExt.getRate();
             shareholderRate=genAgent.getShareholderRate();
             branchRate=stockHold.getBranchRate();
             chiefRate=branch.getChiefRate();
        }
        else if(managerStaff instanceof StockholderStaffExt)
        {
        	 stockHold=((StockholderStaffExt) managerStaff);
             branch=stockHold.getBranchStaffExt();
             shareholderRate=memberStaffExt.getRate();
             branchRate=stockHold.getBranchRate();
             chiefRate=branch.getChiefRate();
             
        }
        else if(managerStaff instanceof BranchStaffExt)
        {
        	branch=((BranchStaffExt) managerStaff);
        	branchRate=memberStaffExt.getRate();
        	chiefRate=branch.getChiefRate();
        }
        else if(managerStaff instanceof ChiefStaffExt)
        {
        	chief=((ChiefStaffExt) managerStaff);
        	chiefRate=memberStaffExt.getRate();
        }
        
        
           
           
            leftRate = 100 - branchRate - shareholderRate - genAgentRate - agentRate-chiefRate;
            if("0".equals(branch.getLeftOwner()))
            {
            	chiefRate=chiefRate+leftRate;
            }
            else
            	branchRate=branchRate+leftRate;
            rateMap.put("agentRate",agentRate);	
            rateMap.put("genAgentRate",genAgentRate);
            rateMap.put("shareholderRate",shareholderRate);
            rateMap.put("branchRate",branchRate);
            rateMap.put("chiefRate",chiefRate);
            
 
        return rateMap;
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

	@Override
    public List<MemberStaffExt> findAllMemeber() {
        return memberStaffExtDao.getAll();
    }

    @Override
    public Map<String, Integer> findManagerRate(ManagerUser userInfo) {
        boolean isStockholder = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_STOCKHOLDER);// 股东
        boolean isGenAgent = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_GEN_AGENT);// 总代理
        boolean isAgent = userInfo.getUserType().equals(
                ManagerStaff.USER_TYPE_AGENT);// 代理
        
        Map<String,Integer> rateMap = new HashMap<String, Integer>();
        if(isAgent){
            int genAgentRate = 0;
            int shareholderRate = 0;
            int branchRate = 0;
            int chiefRate = 0;
            AgentStaffExt agentStaffExt = new AgentStaffExt();
            agentStaffExt = agentStaffExtDao.findUniqueBy("ID",userInfo.getID());
            
            genAgentRate = agentStaffExt.getGenAgentRate();
            shareholderRate = agentStaffExt.getGenAgentStaffExt().getShareholderRate();
            branchRate = agentStaffExt.getGenAgentStaffExt().getStockholderStaffExt().getBranchRate();
            chiefRate = agentStaffExt.getGenAgentStaffExt().getStockholderStaffExt().getBranchStaffExt().getChiefRate();
            
            //判断占成余归问题
            if(Constant.LEFT_OWNER_CHIEF.equals(agentStaffExt.getGenAgentStaffExt().getStockholderStaffExt().getBranchStaffExt().getLeftOwner())){
            	chiefRate= 100 - genAgentRate - shareholderRate - branchRate;						
			}else{
				branchRate= 100 - genAgentRate - shareholderRate - chiefRate;	
			}
            
            rateMap.put("genAgentRate",genAgentRate);//总代理占成
            rateMap.put("shareholderRate",shareholderRate);//股东占成
            rateMap.put("branchRate",branchRate);
            rateMap.put("chiefRate",chiefRate);
            
            /*if(Constant.TRUE_STATUS.equals(agentStaffExt.getGenAgentStaffExt().getPureAccounted())){
                shareholderRate = agentStaffExt.getGenAgentStaffExt().getShareholderRate();
                rateMap.put("shareholderRate",shareholderRate);
            }else{
                int tempRate = 0;
                int countRate = 0;
                tempRate = agentStaffExt.getGenAgentStaffExt().getShareholderRate() - agentStaffExt.getGenAgentStaffExt().getGenAgentRate();
                countRate = agentStaffExt.getGenAgentStaffExt().getGenAgentRate() - genAgentRate;
                shareholderRate = tempRate + countRate;
                rateMap.put("shareholderRate",shareholderRate);
            }
            if(Constant.TRUE_STATUS.equals(agentStaffExt.getGenAgentStaffExt().getStockholderStaffExt().getPureAccounted())){
                branchRate = agentStaffExt.getGenAgentStaffExt().getStockholderStaffExt().getBranchRate();
                rateMap.put("branchRate",branchRate);
            }else{
                int tempRate = 0;
                int countRate = 0;
                tempRate = agentStaffExt.getGenAgentStaffExt().getStockholderStaffExt().getBranchRate() - agentStaffExt.getGenAgentStaffExt().getStockholderStaffExt().getShareholderRate();
                countRate = agentStaffExt.getGenAgentStaffExt().getStockholderStaffExt().getShareholderRate() - shareholderRate - genAgentRate - agentRate;
                branchRate = tempRate + countRate;
                rateMap.put("branchRate",branchRate);
            }*/
            return rateMap;
        }else if(isGenAgent){
            int shareholderRate = 0;
            int branchRate = 0;
            int chiefRate = 0;
            GenAgentStaffExt genAgentStaffExt = new GenAgentStaffExt();
            genAgentStaffExt = genAgentStaffExtDao.findUniqueBy("ID",userInfo.getID());
            
            shareholderRate = genAgentStaffExt.getShareholderRate();
            branchRate = genAgentStaffExt.getStockholderStaffExt().getBranchRate();
            chiefRate = genAgentStaffExt.getStockholderStaffExt().getBranchStaffExt().getChiefRate();
            
            //判断占成余归问题
            if(Constant.LEFT_OWNER_CHIEF.equals(genAgentStaffExt.getStockholderStaffExt().getBranchStaffExt().getLeftOwner())){
            	chiefRate= 100 - shareholderRate - branchRate;						
			}else{
				branchRate= 100 - shareholderRate - chiefRate;	
			}
            
            rateMap.put("shareholderRate",shareholderRate);//股东占成
            rateMap.put("branchRate",branchRate);
            rateMap.put("chiefRate",chiefRate);
            
            /*if(Constant.TRUE_STATUS.equals(genAgentStaffExt.getStockholderStaffExt().getPureAccounted())){
                branchRate = genAgentStaffExt.getStockholderStaffExt().getBranchRate();
                rateMap.put("branchRate",branchRate);
            }else{
                int tempRate = 0;
                int countRate = 0;
                tempRate = genAgentStaffExt.getStockholderStaffExt().getBranchRate() - genAgentStaffExt.getStockholderStaffExt().getShareholderRate();
                countRate = genAgentStaffExt.getStockholderStaffExt().getShareholderRate() - shareholderRate;
                branchRate = tempRate + countRate;
                rateMap.put("branchRate",branchRate);
            }*/
            return rateMap;
        }else if(isStockholder){
            int branchRate = 0;
            int chiefRate = 0;
            
            StockholderStaffExt stockholderStaffExt = new StockholderStaffExt();
            stockholderStaffExt = stockholderStaffExtDao.findUniqueBy("ID", userInfo.getID());
            
            branchRate = stockholderStaffExt.getBranchRate();
            
            //判断占成余归问题
            if(Constant.LEFT_OWNER_CHIEF.equals(stockholderStaffExt.getBranchStaffExt().getLeftOwner())){
            	chiefRate= 100 - branchRate;						
			}else{
				branchRate= 100 - chiefRate;	
			}
            
            rateMap.put("branchRate",branchRate);
            rateMap.put("chiefRate",chiefRate);
        }
        return rateMap;
    }
    
    /*
     * 保存 會員 提取相關邏輯到事物
     */
    public Long saveUserMemberStaff(MemberStaffExt entity,Long createUserId)
    {
    	
    	ManagerStaff parent=entity.getManagerStaff();
    	if (parent instanceof AgentStaffExt) 
        {
        	//userType=ManagerStaff.USER_TYPE_AGENT;
        	agentStaffExtDao.update((AgentStaffExt)parent);
        	
        } 
    	else if (parent instanceof GenAgentStaffExt) 
        {
        	//userType=ManagerStaff.USER_TYPE_GEN_AGENT;
    		genAgentStaffExtDao.update((GenAgentStaffExt)parent);
        }
        else if (parent instanceof BranchStaffExt) 
        {
        	//userType=ManagerStaff.USER_TYPE_BRANCH;
        	branchStaffExtDao.update((BranchStaffExt)parent);
        } 
        else if (parent instanceof StockholderStaffExt) 
        {
        	//userType=ManagerStaff.USER_TYPE_STOCKHOLDER;
        	stockholderStaffExtDao.update((StockholderStaffExt)parent);
        }
    	//agentStaffExtLogic.updateAgentStaffExt(sdeSat);
        
    	memberStaffExtDao.save(entity);
        
        List<UserCommission> userCommissions = new ArrayList<UserCommission>();
        userCommissions = userCommissionLogic.queryCommission(parent.getID(),parent.getUserType()); // 获取用户退水，跟前台进行比较
        List<UserCommission>  agentMemberCommissions=new ArrayList<UserCommission>();
        BigDecimal  backWater= entity.getBackWater();
       
    
        for (int i = 0; i < userCommissions.size(); i++) {
        	
        	UserCommission upUserCommission = userCommissions.get(i);
			Double ca=upUserCommission.getCommissionA();
        	Double cb=upUserCommission.getCommissionB();
        	Double cc=upUserCommission.getCommissionC();
        	if(backWater.doubleValue()!=100)
           	{
           		ca=ca.doubleValue()-backWater.doubleValue();
           		if(ca<0)
           			ca=0d;
           		cb=cb.doubleValue()-backWater.doubleValue();
           		if(cb<0)
           			cb=0d;
           		cc=cc.doubleValue()-backWater.doubleValue();
           		if(cc<0)
           			cc=0d;
           	}
           	else if(backWater.doubleValue()==100d)
           	{
           		ca=0d;
           		cb=0d;
           		cc=0d;
           	}
        	UserCommission userCommision=new UserCommission();
        	userCommision.setCreateTime(new Date());         	
        	userCommision.setCreateUser(createUserId);
        	userCommision.setUserId(entity.getID());
        	userCommision.setCommissionA(ca);
        	userCommision.setCommissionB(cb);
        	userCommision.setCommissionC(cc);
        	userCommision.setPlayType(upUserCommission.getPlayType());
        	userCommision.setUserType(MemberStaff.USER_TYPE_MEMBER);
        	userCommision.setItemQuotas(upUserCommission.getItemQuotas());
        	userCommision.setBettingQuotas(upUserCommission.getBettingQuotas());
        	userCommision.setPlayFinalType(upUserCommission.getPlayFinalType()); 
        	userCommision.setChiefId(upUserCommission.getChiefId());
        	agentMemberCommissions.add(userCommision);
            //userCommissionLogic.saveCommission(userCommision);
        	
        }
        userCommissionLogic.saveUserBatchCommission(agentMemberCommissions);
        return entity.getID();
    	
    }
    
    /*
     * 修改會員 提取相關邏輯到事物
     */
    public void updateUserMemberStaff(MemberStaffExt memberStaffExt)
    {
    	 MemberStaffExt memStaExt = memberStaffExtDao.findUniqueBy("account",memberStaffExt.getAccount());
         if(memStaExt==null)
         {
        	 throw new RuntimeException("不存在的會員帳號！！");
         }
         
             //信用额度
             int tempFlag = 0;
             tempFlag = memberStaffExt.getTotalCreditLine() - memStaExt.getTotalCreditLine();
     
             //减少会员的 信用额度
             if(tempFlag < 0){
             	Integer changedCred=memberStaffExt.getTotalCreditLine();
             	Integer userCred= memStaExt.getTotalCreditLine()-memStaExt.getAvailableCreditLine() ;
             	//如果会员是赢          	
             	if(changedCred<userCred)
             	{
             		throw new RuntimeException("修改会员 信用额度 小于 已经输掉的值！！"); 
    
             	}//如果是输
             	else
             	{
             		
             		memStaExt.setAvailableCreditLine(tempFlag+memStaExt.getAvailableCreditLine());
             	}
          
             }//增加会员的信用额度
             else{
             	memStaExt.setAvailableCreditLine(tempFlag+memStaExt.getAvailableCreditLine());
             }
             memStaExt.setTotalCreditLine(memberStaffExt.getTotalCreditLine());
             memStaExt.setChsName(memberStaffExt.getChsName());
             if(memberStaffExt.getRate()!=null)
             {
             memStaExt.setRate(memberStaffExt.getRate());
             }
             if(memberStaffExt.getPlate()!=null)
             {
             memStaExt.setPlate(memberStaffExt.getPlate());
             }
             memStaExt.setUpdateDate(new Date());
             // 当用户没有填新的密码时，默认为以前的密码
             if (StringUtils.isEmpty(memberStaffExt.getUserPwd())) {
            	 memStaExt.setUserPwd(memStaExt.getUserPwd());
                } else {
                    MD5 md5 = new MD5();
                    String userPwdOrignMd5 = md5.getMD5ofStr(memberStaffExt.getUserPwd()).trim();
                    memStaExt.setUserPwd(userPwdOrignMd5);
                    memStaExt.setPasswordResetFlag(memberStaffExt.getPasswordResetFlag());
                	memStaExt.setPasswordUpdateDate(memberStaffExt.getPasswordUpdateDate());
                }
             
             
            // Long parentID=0L;
            //String parentType=ManagerStaff.USER_TYPE_AGENT;
             if (memStaExt.getManagerStaff() instanceof AgentStaffExt) {
             	
             	 AgentStaffExt aStaffExt=(AgentStaffExt) (memStaExt.getManagerStaff());
             	int availableMoney = aStaffExt.getAvailableCreditLine();// 可用信用额度
                 
                 // 判断总信用额度不能超过剩下的信用额度
                 if (availableMoney-tempFlag < 0) {
                     throw new RuntimeException("总信用额度不能超过剩下的信用额度！"); 
                 }
                       
                         GenAgentStaffExt genAStaffExt=aStaffExt.getGenAgentStaffExt();
                         StockholderStaffExt stock=genAStaffExt.getStockholderStaffExt();
                         BranchStaffExt branch=stock.getBranchStaffExt();
                         //int maxRate=commonUserLogic.queryBelowMaxRate(genAStaffExt.getID(), ManagerStaff.USER_TYPE_GEN_AGENT);
                         if(memberStaffExt.getRate()!=null&&memberStaffExt.getRate()>100-aStaffExt.getGenAgentRate()-genAStaffExt.getShareholderRate()-stock.getBranchRate()-branch.getChiefRate())
                         {
                        	 throw new RuntimeException("超過代理 可以修改的最大值！！"); 
                         }
             
                 ((AgentStaffExt)(memStaExt.getManagerStaff())).setAvailableCreditLine(availableMoney-tempFlag);

                 
             }  else if (memStaExt.getManagerStaff() instanceof BranchStaffExt) {
                 
             	BranchStaffExt branch=(BranchStaffExt) (memStaExt.getManagerStaff());
                 int availableMoney = (branch).getAvailableCreditLine();

                 // 判断总信用额度不能超过剩下的信用额度
                 if (availableMoney-tempFlag < 0) {
                	 throw new RuntimeException("超過分公司 剩下的信用额度！！");
                 }
              
                 //int maxRate=commonUserLogic.queryBelowMaxRate(genAStaffExt.getID(), ManagerStaff.USER_TYPE_GEN_AGENT);
                 if(memberStaffExt.getRate()!=null&&memberStaffExt.getRate()>100-branch.getChiefRate())
                 { 
             		 throw new RuntimeException("超過分公司 可以修改的最大值！！");
                 }
                 
                 
         
                 ((BranchStaffExt) (memStaExt.getManagerStaff())).setAvailableCreditLine(availableMoney-tempFlag);

             } else if (memStaExt.getManagerStaff() instanceof StockholderStaffExt) {
                 
             	 StockholderStaffExt stock=(StockholderStaffExt) (memStaExt.getManagerStaff());
             	int availableMoney = (stock).getAvailableCreditLine();// 可用信用额度
                 // 判断总信用额度不能超过剩下的信用额度
                 if (availableMoney-tempFlag < 0) {
                     throw new RuntimeException("总信用额度不能超过剩下的信用额度");
                 }

                 BranchStaffExt branch=stock.getBranchStaffExt();
                 if(memberStaffExt.getRate()!=null&&memberStaffExt.getRate()>100-branch.getChiefRate()-stock.getBranchRate())
                 {
  
             		throw new RuntimeException("超過分公司 可以修改的最大值！！");
                 }
                 
             
                 ((StockholderStaffExt)(memStaExt.getManagerStaff())).setAvailableCreditLine(availableMoney-tempFlag);
  

             } else if (memStaExt.getManagerStaff() instanceof GenAgentStaffExt) {
                 
             	GenAgentStaffExt genAStaffExt=(GenAgentStaffExt) (memStaExt.getManagerStaff());
                 int availableMoney =((GenAgentStaffExt) (memStaExt.getManagerStaff())).getAvailableCreditLine();
               

                 // 算出一共用了多少信用额度
                
                 // 判断总信用额度不能超过剩下的信用额度
                 if (availableMoney-tempFlag < 0) {
                     throw new RuntimeException("會員總总信用额度不能超过剩下的信用额度");
                 }
                 
                 StockholderStaffExt stock=genAStaffExt.getStockholderStaffExt();
                 BranchStaffExt branch=stock.getBranchStaffExt();
                 if(memberStaffExt.getRate()!=null&&memberStaffExt.getRate()>100-branch.getChiefRate()-stock.getBranchRate()-genAStaffExt.getShareholderRate())
                 {
  
             		throw new RuntimeException("超過總代理 可以修改的最大值！！");
                 }
                
                 ((GenAgentStaffExt)(memStaExt.getManagerStaff())).setAvailableCreditLine(availableMoney-tempFlag);
             }
             boolean updateCommssion=false;
             if(memberStaffExt.getBackWater() != null && !memberStaffExt.getBackWater().equals(memStaExt.getBackWater()))
             {
             	updateCommssion=true;
                memStaExt.setBackWater(memberStaffExt.getBackWater());
             }
    	
    	ManagerStaff parent=memStaExt.getManagerStaff();
    	if (parent instanceof AgentStaffExt) 
        {
        	//userType=ManagerStaff.USER_TYPE_AGENT;
        	agentStaffExtDao.update((AgentStaffExt)parent);
        	
        } 
    	else if (parent instanceof GenAgentStaffExt) 
        {
        	//userType=ManagerStaff.USER_TYPE_GEN_AGENT;
    		genAgentStaffExtDao.update((GenAgentStaffExt)parent);
        }
        else if (parent instanceof BranchStaffExt) 
        {
        	//userType=ManagerStaff.USER_TYPE_BRANCH;
        	branchStaffExtDao.update((BranchStaffExt)parent);
        } 
        else if (parent instanceof StockholderStaffExt) 
        {
        	//userType=ManagerStaff.USER_TYPE_STOCKHOLDER;
        	stockholderStaffExtDao.update((StockholderStaffExt)parent);
        }
    	memStaExt.setFlag(memberStaffExt.getFlag());
    	
    	memberStaffExtDao.update(memStaExt);
    	
        if(updateCommssion)
        {
       
        List<UserCommission> userCommissions = new ArrayList<UserCommission>();
        userCommissions = userCommissionLogic.queryCommission(parent.getID(),parent.getUserType()); // 获取用户退水，跟前台进行比较
        List<UserCommission>  agentMemberCommissions=new ArrayList<UserCommission>();
        BigDecimal  backWater= memStaExt.getBackWater();
       
    
        for (int i = 0; i < userCommissions.size(); i++) {
        	
        	UserCommission upUserCommission = userCommissions.get(i);
			Double ca=upUserCommission.getCommissionA();
        	Double cb=upUserCommission.getCommissionB();
        	Double cc=upUserCommission.getCommissionC();
        	if(backWater.doubleValue()!=100)
           	{
           		ca=ca.doubleValue()-backWater.doubleValue();
           		if(ca<0)
           			ca=0d;
           		cb=cb.doubleValue()-backWater.doubleValue();
           		if(cb<0)
           			cb=0d;
           		cc=cc.doubleValue()-backWater.doubleValue();
           		if(cc<0)
           			cc=0d;
           	}
           	else if(backWater.doubleValue()==100d)
           	{
           		ca=0d;
           		cb=0d;
           		cc=0d;
           	}
        	UserCommission userCommision=new UserCommission();
        	userCommision.setUserId(memStaExt.getID());
        	userCommision.setCreateTime(new Date());         	
        	userCommision.setCommissionA(100-ca);
        	userCommision.setCommissionB(100-cb);
        	userCommision.setCommissionC(100-cc);
        	userCommision.setPlayType(upUserCommission.getPlayType());
        	userCommision.setUserType(MemberStaff.USER_TYPE_MEMBER);
        	userCommision.setItemQuotas(upUserCommission.getItemQuotas());
        	userCommision.setBettingQuotas(upUserCommission.getBettingQuotas());
        	userCommision.setPlayFinalType(upUserCommission.getPlayFinalType()); 
        	agentMemberCommissions.add(userCommision);
            //userCommissionLogic.saveCommission(userCommision);
        	
        }
        userCommissionLogic.batchUpdateMemberCommissiono(agentMemberCommissions, memStaExt.getPlate());
        
       
        
        }
    	
    	
    	
    }
    /*
     * 修改會員 提取相關邏輯到事物
     */
    @Override
	public void updateUserMemberStaff(MemberStaffExt memberStaffExt, ManagerUser currentUser)
    {
    	MemberStaffExt memStaExt = memberStaffExtDao.findUniqueBy("account",memberStaffExt.getAccount());
    	if(memStaExt==null)
    	{
    		throw new RuntimeException("不存在的會員帳號！！");
    	}
    	
    	//信用额度
    	int tempFlag = 0;
    	int newTotalCreditLine = memberStaffExt.getTotalCreditLine();
    	int orginalTotalCreditLine = memStaExt.getTotalCreditLine();
    	tempFlag = memberStaffExt.getTotalCreditLine() - memStaExt.getTotalCreditLine();
    	
    	//减少会员的 信用额度
    	if(tempFlag < 0){
    		Integer changedCred=memberStaffExt.getTotalCreditLine();
    		Integer userCred= memStaExt.getTotalCreditLine()-memStaExt.getAvailableCreditLine() ;
    		//如果会员是赢          	
    		if(changedCred<userCred)
    		{
    			throw new RuntimeException("修改会员 信用额度 小于 已经输掉的值！！"); 
    			
    		}//如果是输
    		else
    		{
    			
    			memStaExt.setAvailableCreditLine(tempFlag+memStaExt.getAvailableCreditLine());
    		}
    		
    	}//增加会员的信用额度
    	else{
    		memStaExt.setAvailableCreditLine(tempFlag+memStaExt.getAvailableCreditLine());
    	}
    	memStaExt.setTotalCreditLine(memberStaffExt.getTotalCreditLine());
    	memStaExt.setChsName(memberStaffExt.getChsName());
    	if(memberStaffExt.getRate()!=null)
    	{
    		memStaExt.setRate(memberStaffExt.getRate());
    	}
    	if(memberStaffExt.getPlate()!=null)
    	{
    		memStaExt.setPlate(memberStaffExt.getPlate());
    	}
    	memStaExt.setUpdateDate(new Date());
    	// 当用户没有填新的密码时，默认为以前的密码
    	if (StringUtils.isEmpty(memberStaffExt.getUserPwd())) {
    		memStaExt.setUserPwd(memStaExt.getUserPwd());
    	} else {
    		//MD5 md5 = new MD5();
    		String userPwdOrignMd5 = memberStaffExt.getUserPwd().trim();
    		memStaExt.setUserPwd(userPwdOrignMd5);
    		memStaExt.setPasswordResetFlag(memberStaffExt.getPasswordResetFlag());
    		memStaExt.setPasswordUpdateDate(memberStaffExt.getPasswordUpdateDate());
    	}
    	
    	
    	// Long parentID=0L;
    	//String parentType=ManagerStaff.USER_TYPE_AGENT;
    	if (memStaExt.getManagerStaff() instanceof AgentStaffExt) {
    		
    		AgentStaffExt aStaffExt=(AgentStaffExt) (memStaExt.getManagerStaff());
    		int availableMoney = aStaffExt.getAvailableCreditLine();// 可用信用额度
    		
    		// 判断总信用额度不能超过剩下的信用额度
    		if (availableMoney-tempFlag < 0) {
    			throw new RuntimeException("总信用额度不能超过剩下的信用额度！"); 
    		}
    		
    		GenAgentStaffExt genAStaffExt=aStaffExt.getGenAgentStaffExt();
    		StockholderStaffExt stock=genAStaffExt.getStockholderStaffExt();
    		BranchStaffExt branch=stock.getBranchStaffExt();
    		//int maxRate=commonUserLogic.queryBelowMaxRate(genAStaffExt.getID(), ManagerStaff.USER_TYPE_GEN_AGENT);
    		if(memberStaffExt.getRate()!=null&&memberStaffExt.getRate()>100-aStaffExt.getGenAgentRate()-genAStaffExt.getShareholderRate()-stock.getBranchRate()-branch.getChiefRate())
    		{
    			throw new RuntimeException("超過代理 可以修改的最大值！！"); 
    		}
    		
    		((AgentStaffExt)(memStaExt.getManagerStaff())).setAvailableCreditLine(availableMoney-tempFlag);
    		
    		
    	}  else if (memStaExt.getManagerStaff() instanceof BranchStaffExt) {
    		
    		BranchStaffExt branch=(BranchStaffExt) (memStaExt.getManagerStaff());
    		int availableMoney = (branch).getAvailableCreditLine();
    		
    		// 判断总信用额度不能超过剩下的信用额度
    		if (availableMoney-tempFlag < 0) {
    			throw new RuntimeException("超過分公司 剩下的信用额度！！");
    		}
    		
    		//int maxRate=commonUserLogic.queryBelowMaxRate(genAStaffExt.getID(), ManagerStaff.USER_TYPE_GEN_AGENT);
    		if(memberStaffExt.getRate()!=null&&memberStaffExt.getRate()>100-branch.getChiefRate())
    		{ 
    			throw new RuntimeException("超過分公司 可以修改的最大值！！");
    		}
    		
    		
    		
    		((BranchStaffExt) (memStaExt.getManagerStaff())).setAvailableCreditLine(availableMoney-tempFlag);
    		
    	} else if (memStaExt.getManagerStaff() instanceof StockholderStaffExt) {
    		
    		StockholderStaffExt stock=(StockholderStaffExt) (memStaExt.getManagerStaff());
    		int availableMoney = (stock).getAvailableCreditLine();// 可用信用额度
    		// 判断总信用额度不能超过剩下的信用额度
    		if (availableMoney-tempFlag < 0) {
    			throw new RuntimeException("总信用额度不能超过剩下的信用额度");
    		}
    		
    		BranchStaffExt branch=stock.getBranchStaffExt();
    		if(memberStaffExt.getRate()!=null&&memberStaffExt.getRate()>100-branch.getChiefRate()-stock.getBranchRate())
    		{
    			
    			throw new RuntimeException("超過分公司 可以修改的最大值！！");
    		}
    		
    		
    		((StockholderStaffExt)(memStaExt.getManagerStaff())).setAvailableCreditLine(availableMoney-tempFlag);
    		
    		
    	} else if (memStaExt.getManagerStaff() instanceof GenAgentStaffExt) {
    		
    		GenAgentStaffExt genAStaffExt=(GenAgentStaffExt) (memStaExt.getManagerStaff());
    		int availableMoney =((GenAgentStaffExt) (memStaExt.getManagerStaff())).getAvailableCreditLine();
    		
    		
    		// 算出一共用了多少信用额度
    		
    		// 判断总信用额度不能超过剩下的信用额度
    		if (availableMoney-tempFlag < 0) {
    			throw new RuntimeException("會員總总信用额度不能超过剩下的信用额度");
    		}
    		
    		StockholderStaffExt stock=genAStaffExt.getStockholderStaffExt();
    		BranchStaffExt branch=stock.getBranchStaffExt();
    		if(memberStaffExt.getRate()!=null&&memberStaffExt.getRate()>100-branch.getChiefRate()-stock.getBranchRate()-genAStaffExt.getShareholderRate())
    		{
    			
    			throw new RuntimeException("超過總代理 可以修改的最大值！！");
    		}
    		
    		((GenAgentStaffExt)(memStaExt.getManagerStaff())).setAvailableCreditLine(availableMoney-tempFlag);
    	}
    	boolean updateCommssion=false;
    	if(memberStaffExt.getBackWater() != null && !memberStaffExt.getBackWater().equals(memStaExt.getBackWater()))
    	{
    		updateCommssion=true;
    		memStaExt.setBackWater(memberStaffExt.getBackWater());
    	}
    	
    	ManagerStaff parent=memStaExt.getManagerStaff();
    	if (parent instanceof AgentStaffExt) 
    	{
    		//userType=ManagerStaff.USER_TYPE_AGENT;
    		agentStaffExtDao.update((AgentStaffExt)parent);
    		
    	} 
    	else if (parent instanceof GenAgentStaffExt) 
    	{
    		//userType=ManagerStaff.USER_TYPE_GEN_AGENT;
    		genAgentStaffExtDao.update((GenAgentStaffExt)parent);
    	}
    	else if (parent instanceof BranchStaffExt) 
    	{
    		//userType=ManagerStaff.USER_TYPE_BRANCH;
    		branchStaffExtDao.update((BranchStaffExt)parent);
    	} 
    	else if (parent instanceof StockholderStaffExt) 
    	{
    		//userType=ManagerStaff.USER_TYPE_STOCKHOLDER;
    		stockholderStaffExtDao.update((StockholderStaffExt)parent);
    	}
    	memStaExt.setFlag(memberStaffExt.getFlag());
    	
    	memberStaffExtDao.update(memStaExt);
    	//add by peter
    	//如果会员信用金额有变动，增加变动记录到日志表里
    	if(tempFlag!=0){
    		ReplenishAutoSetLog changeLog = this.setChangeLog(currentUser, "TOTAL_CREDITLINE", String.valueOf(orginalTotalCreditLine), String.valueOf(newTotalCreditLine),memStaExt);
    		replenishAutoSetLogLogic.saveReplenishLogSet(changeLog);
    	}
    	
    	if(updateCommssion)
    	{
    		
    		List<UserCommission> userCommissions = new ArrayList<UserCommission>();
    		userCommissions = userCommissionLogic.queryCommission(parent.getID(),parent.getUserType()); // 获取用户退水，跟前台进行比较
    		List<UserCommission>  agentMemberCommissions=new ArrayList<UserCommission>();
    		BigDecimal  backWater= memStaExt.getBackWater();
    		
    		
    		for (int i = 0; i < userCommissions.size(); i++) {
    			
    			UserCommission upUserCommission = userCommissions.get(i);
    			Double ca=upUserCommission.getCommissionA();
    			Double cb=upUserCommission.getCommissionB();
    			Double cc=upUserCommission.getCommissionC();
    			if(backWater.doubleValue()!=100)
    			{
    				ca=ca.doubleValue()-backWater.doubleValue();
    				if(ca<0)
    					ca=0d;
    				cb=cb.doubleValue()-backWater.doubleValue();
    				if(cb<0)
    					cb=0d;
    				cc=cc.doubleValue()-backWater.doubleValue();
    				if(cc<0)
    					cc=0d;
    			}
    			else if(backWater.doubleValue()==100d)
    			{
    				ca=0d;
    				cb=0d;
    				cc=0d;
    			}
    			UserCommission userCommision=new UserCommission();
    			userCommision.setUserId(memStaExt.getID());
    			userCommision.setCreateTime(new Date());         	
    			userCommision.setCommissionA(100-ca);
    			userCommision.setCommissionB(100-cb);
    			userCommision.setCommissionC(100-cc);
    			userCommision.setPlayType(upUserCommission.getPlayType());
    			userCommision.setUserType(MemberStaff.USER_TYPE_MEMBER);
    			userCommision.setItemQuotas(upUserCommission.getItemQuotas());
    			userCommision.setBettingQuotas(upUserCommission.getBettingQuotas());
    			userCommision.setPlayFinalType(upUserCommission.getPlayFinalType()); 
    			agentMemberCommissions.add(userCommision);
    			//userCommissionLogic.saveCommission(userCommision);
    			
    		}
    		userCommissionLogic.batchUpdateMemberCommissiono(agentMemberCommissions, memStaExt.getPlate());
    		
    		
    		
    	}
    	
    	
    	
    }
    
	private ReplenishAutoSetLog setChangeLog(ManagerUser currentUser, String changeSubType, String orginalValue, String newValue,
			MemberStaff modifyUser) {
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

		log.setType(modifyUser.getAccount());
		log.setTypeCode(modifyUser.getUserTypeName());
		log.setMoneyOrgin(0);
		log.setMoneyNew(0);

		// 更新的用户信息
		log.setUpdateUserID(currentUser.getID());
		log.setUpdateUserType(Integer.valueOf(currentUser.getUserType()));
		return log;
	}
	
	@Override
	public int updateMemberAvailableCreditToTotal(String scheme) {
		return memberStaffExtDao.updateMemberAvailableCreditToTotal(scheme);
	}
    
	@Override
	public String getMemberFlagById(long id) {
		return memberStaffExtDao.getMemberFlagById(id);
	}

    public ISubAccountActionDao getSubAccountActionDao() {
        return subAccountActionDao;
    }

    public void setSubAccountActionDao(ISubAccountActionDao subAccountActionDao) {
        this.subAccountActionDao = subAccountActionDao;
    }

	public IReplenishAutoSetLogLogic getReplenishAutoSetLogLogic() {
		return replenishAutoSetLogLogic;
	}

	public void setReplenishAutoSetLogLogic(IReplenishAutoSetLogLogic replenishAutoSetLogLogic) {
		this.replenishAutoSetLogLogic = replenishAutoSetLogLogic;
	}
}
