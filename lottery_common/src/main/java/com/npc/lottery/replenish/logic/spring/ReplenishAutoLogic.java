package com.npc.lottery.replenish.logic.spring;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.google.common.collect.Lists;
import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.boss.logic.interf.IShopsLogic;
import com.npc.lottery.common.Constant;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.INCPeriodsInfoLogic;
import com.npc.lottery.replenish.dao.interf.IReplenishAuto;
import com.npc.lottery.replenish.dao.interf.IReplenishAutoSetJDBCDao;
import com.npc.lottery.replenish.entity.Replenish;
import com.npc.lottery.replenish.entity.ReplenishAuto;
import com.npc.lottery.replenish.entity.ReplenishAutoLog;
import com.npc.lottery.replenish.entity.ReplenishAutoSetLog;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoLogLogic;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoLogic;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoSetLogLogic;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.replenish.vo.AutoReplenishSetVO;
import com.npc.lottery.replenish.vo.ReplenishVO;
import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.rule.GDKLSFRule;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.logic.interf.IManagerStaffLogic;
import com.npc.lottery.sysmge.logic.interf.IManagerUserLogic;
import com.npc.lottery.user.dao.interf.IBranchStaffExtDao;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;
import com.npc.lottery.util.PlayTypeUtils;
import com.npc.lottery.util.WebTools;

public class ReplenishAutoLogic implements IReplenishAutoLogic {

	private IReplenishAuto replenishAutoDao;
	
	private IReplenishLogic replenishLogic;
	//private IHKPeriodsInfoLogic skperiodsInfoLogic;	
	 private ICQPeriodsInfoLogic icqPeriodsInfoLogic;
	 private IJSSBPeriodsInfoLogic	jssbPeriodsInfoLogic	= null; //快3
	 private IGDPeriodsInfoLogic periodsInfoLogic;
	 private INCPeriodsInfoLogic ncPeriodsInfoLogic;
	 private IManagerUserLogic managerUserLogic;
	 private IMemberStaffExtLogic memberStaffExtLogic;
	 private IManagerStaffLogic managerStaffLogic;
	 private IShopsLogic shopsLogic;
	 private IReplenishAutoLogLogic replenishAutoLogLogic; 
	 private IReplenishAutoSetLogLogic replenishAutoSetLogLogic; 
	 private IReplenishAutoSetJDBCDao replenishAutoSetJDBCDao;
	 
	 private static Logger LOG = Logger.getLogger(ReplenishAutoLogic.class);
	 private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic = null;
	@Override
	public List<ReplenishAuto> listReplenishAutoList(Criterion... criterions) {
		
//		List<ReplenishAuto>	list = replenishAutoDao.find(" from ReplenishAuto where shopsCode=? and createUser=? ","6000",4780);
//		return list;
		return replenishAutoDao.find(criterions);
	}
	
	//获取所有玩法的实货额,并以自动补货为条件分组查出最大项
    public Map<String, String> queryTotalTrueMoney(ManagerUser userInfo){
    	Map<String,String> mapPeriodNum = new HashMap<String,String>();
    	mapPeriodNum.put(Constant.LOTTERY_TYPE_CQSSC, "");
    	mapPeriodNum.put(Constant.LOTTERY_TYPE_GDKLSF, "");
    	mapPeriodNum.put(Constant.LOTTERY_TYPE_BJSC, "");
    	mapPeriodNum.put(Constant.LOTTERY_TYPE_K3, "");
    	mapPeriodNum.put(Constant.LOTTERY_TYPE_NC, "");
    	
    	List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
    	filtersPeriodInfo.add(Restrictions.le("openQuotTime",new Date()));
        filtersPeriodInfo.add(Restrictions.ge("lotteryTime",new Date()));
        
        CQPeriodsInfo runningPeriodsCQ = icqPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
		if(runningPeriodsCQ!=null){
			mapPeriodNum.put(Constant.LOTTERY_TYPE_CQSSC, runningPeriodsCQ.getPeriodsNum());
		}
		
		GDPeriodsInfo runningPeriodsGD = periodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
		if(runningPeriodsGD!=null){
			mapPeriodNum.put(Constant.LOTTERY_TYPE_GDKLSF, runningPeriodsGD.getPeriodsNum());
		}
		
		NCPeriodsInfo runningPeriodsNC = ncPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
		if(runningPeriodsNC!=null){
			mapPeriodNum.put(Constant.LOTTERY_TYPE_NC, runningPeriodsNC.getPeriodsNum());
		}
		
		BJSCPeriodsInfo runningPeriodsBJ = bjscPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
		if(runningPeriodsBJ!=null){
			mapPeriodNum.put(Constant.LOTTERY_TYPE_BJSC, runningPeriodsBJ.getPeriodsNum());
		}
		
		JSSBPeriodsInfo runningPeriodsK3 = jssbPeriodsInfoLogic.queryByPeriods(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
		if(runningPeriodsK3!=null){
			mapPeriodNum.put(Constant.LOTTERY_TYPE_K3, runningPeriodsK3.getPeriodsNum());
		}
		
    	UserVO vo = replenishLogic.getUserType(userInfo);
    	
    	//查询每一项的实货
    	List<AutoReplenishSetVO> gdtotalList = replenishAutoSetJDBCDao.queryTotalTrueMoney(mapPeriodNum.get(Constant.LOTTERY_TYPE_GDKLSF), userInfo.getID(), 
    			userInfo.getUserType(), vo.getUserType(), vo.getRateUser(),Constant.LOTTERY_TYPE_GDKLSF);
    	List<AutoReplenishSetVO> gdtotalList_LM = replenishAutoSetJDBCDao.queryTotalTrueMoney_LM(mapPeriodNum.get(Constant.LOTTERY_TYPE_GDKLSF), userInfo.getID(), 
    			userInfo.getUserType(), vo.getUserType(), vo.getRateUser(),Constant.LOTTERY_TYPE_GDKLSF);
    	List<AutoReplenishSetVO> cqtotalList = replenishAutoSetJDBCDao.queryTotalTrueMoney(mapPeriodNum.get(Constant.LOTTERY_TYPE_CQSSC), userInfo.getID(), 
    			userInfo.getUserType(), vo.getUserType(), vo.getRateUser(),Constant.LOTTERY_TYPE_CQSSC);
    	List<AutoReplenishSetVO> bjtotalList = replenishAutoSetJDBCDao.queryTotalTrueMoney(mapPeriodNum.get(Constant.LOTTERY_TYPE_BJSC), userInfo.getID(), 
    			userInfo.getUserType(), vo.getUserType(), vo.getRateUser(),Constant.LOTTERY_TYPE_BJSC);
    	List<AutoReplenishSetVO> k3totalList = replenishAutoSetJDBCDao.queryTotalTrueMoney(mapPeriodNum.get(Constant.LOTTERY_TYPE_K3), userInfo.getID(), 
    			userInfo.getUserType(), vo.getUserType(), vo.getRateUser(),Constant.LOTTERY_TYPE_K3);
    	List<AutoReplenishSetVO> nctotalList = replenishAutoSetJDBCDao.queryTotalTrueMoney(mapPeriodNum.get(Constant.LOTTERY_TYPE_NC), userInfo.getID(), 
    			userInfo.getUserType(), vo.getUserType(), vo.getRateUser(),Constant.LOTTERY_TYPE_NC);
    	List<AutoReplenishSetVO> nctotalList_LM = replenishAutoSetJDBCDao.queryTotalTrueMoney_LM(mapPeriodNum.get(Constant.LOTTERY_TYPE_NC), userInfo.getID(), 
    			userInfo.getUserType(), vo.getUserType(), vo.getRateUser(),Constant.LOTTERY_TYPE_NC);
    	
    	Map<String, String> trueMoneyMap = new LinkedHashMap<String,String>();
		for(AutoReplenishSetVO v:gdtotalList){
			trueMoneyMap.put(v.getAutoReplenishType(), v.getTotalMoney().toString());
		}
		for(AutoReplenishSetVO v:gdtotalList_LM){
			trueMoneyMap.put(v.getAutoReplenishType(), v.getTotalMoney().toString());
		}
		for(AutoReplenishSetVO v:cqtotalList){
			trueMoneyMap.put(v.getAutoReplenishType(), v.getTotalMoney().toString());
		}
		for(AutoReplenishSetVO v:bjtotalList){
			trueMoneyMap.put(v.getAutoReplenishType(), v.getTotalMoney().toString());
		}
		for(AutoReplenishSetVO v:k3totalList){
			trueMoneyMap.put(v.getAutoReplenishType(), v.getTotalMoney().toString());
		}
		for(AutoReplenishSetVO v:nctotalList){
			trueMoneyMap.put(v.getAutoReplenishType(), v.getTotalMoney().toString());
		}
		for(AutoReplenishSetVO v:nctotalList_LM){
			trueMoneyMap.put(v.getAutoReplenishType(), v.getTotalMoney().toString());
		}
		
		trueMoneyMap.put("gdPeriodNum", mapPeriodNum.get(Constant.LOTTERY_TYPE_GDKLSF));
		trueMoneyMap.put("cqPeriodNum", mapPeriodNum.get(Constant.LOTTERY_TYPE_CQSSC));
		trueMoneyMap.put("bjPeriodNum", mapPeriodNum.get(Constant.LOTTERY_TYPE_BJSC));
		trueMoneyMap.put("k3PeriodNum", mapPeriodNum.get(Constant.LOTTERY_TYPE_K3));
		trueMoneyMap.put("ncPeriodNum", mapPeriodNum.get(Constant.LOTTERY_TYPE_NC));
		return trueMoneyMap;
	}
    
    @Override
    public Map<String, Integer> queryLowestMoney(Long userID){ 
    	//查询每一项的最低投注额显示在起补额度里
    	Map<String, Integer> map = new LinkedHashMap<String,Integer>();
		List<AutoReplenishSetVO> lowestList = replenishAutoSetJDBCDao.queryLowestQuotasByAutoType(userID);
		for(AutoReplenishSetVO v:lowestList){
			map.put(v.getAutoReplenishType(), v.getLowestMoney());
		}
		return map;
    }

	/**
	 * 更新補貨設置
	 */
	@Override
	public void updateReplenishAuto(ReplenishAuto entity) {
		replenishAutoDao.save(entity);
	}
	@Override
	public ReplenishAuto queryReplenishAuto(Long shopID,String typeCode,Integer createUser) {

		return replenishAutoDao.findUnique(" from ReplenishAuto where shopsID=? and typeCode=? and createUser=? ", shopID,typeCode,createUser);
	}
	/**
	 * 更新補貨設置
	 */
	@Override
	public void updateReplenishAuto(List<ReplenishAuto> entityList) {

		
		if(entityList!=null && entityList.size()>0)
		{
			for(ReplenishAuto ent : entityList){
				
				int num=0;
//				System.out.println(ent.getShopsCode()+"   "+ent.getTypeCode()+" "+ent.getCreateUser());
				ReplenishAuto persistent = queryReplenishAuto(ent.getShopsID(),ent.getTypeCode(),ent.getCreateUser());
				if(persistent == null){ persistent = ent ;num=2;}; // ==null 说明是第一次添加 
				
				ReplenishAutoSetLog log = new ReplenishAutoSetLog();
				log.setMoneyOrgin(persistent.getMoneyLimit());
				log.setOrginalValue(String.valueOf(persistent.getMoneyLimit()));
				log.setStateOrgin(persistent.getState());
				if(num==0 && persistent.getMoneyLimit().toString().equals(ent.getMoneyLimit().toString().trim()) && persistent.getState().trim().equals(ent.getState().trim())){
					num=1;
					continue;
				}
				persistent.setMoneyLimit(ent.getMoneyLimit());
				persistent.setMoneyRep(ent.getMoneyRep());
				persistent.setState(ent.getState());
				replenishAutoDao.save(persistent);
				
				log.setShopID(persistent.getShopsID());
				log.setMoneyNew(ent.getMoneyLimit());
				log.setCreateUserID(persistent.getCreateUser());
				log.setCreateUserType(Integer.valueOf(persistent.getCreateUserType()));
				log.setType(persistent.getType());
				log.setTypeCode(persistent.getTypeCode());
				log.setCreateTime(new Date());
				log.setStateNew(ent.getState());
				
				String ip = WebTools.getClientIpAddr(getRequest());
		        log.setIp(ip);
		        //add by peter for change log
		        // 更新的用户信息
				log.setUpdateUserID(persistent.getCreateUser());
				log.setUpdateUserType(Integer.valueOf(persistent.getCreateUserType()));
				log.setChangeType(Constant.CHANGE_LOG_CHANGE_TYPE_REPLENISH_AUTO_UPDATE);
				log.setChangeSubType("MONEY_LIMIT");
				log.setNewValue(String.valueOf(ent.getMoneyLimit()));
				if(num==0)
				{
					replenishAutoSetLogLogic.saveReplenishLogSet(log);
				}
//				throw new IllegalArgumentException("000000000000000000000");
			}
		}
//		updateReplenishAutoHK();
//		updateReplenishAutoGD();
//		updateReplenishAutoCQ();
//		updateReplenishAutoBJ();
	}
	
	/**
	 * 获取上级的UserID，拥金和计算占成，补货新方法调用
	 * @param replenish
	 * @param betOrder
	 * @param opType 操作方式，"menuReplenish"即手动补货触发的，"autoReplenish":自动补货触发的，
	 *                      区别在于手动补货时占成已经处理好了，不用再处理，而自动补货触发的要在这里对占成处理一下，主要是余占问题
	 * @param scheme                     
	 * @return
	 */
	private Replenish getReplenishDataByScheme(Replenish replenish,BaseBet betOrder,String opType,String scheme){
		String leftOwnerType = "";
		//if("autoReplenish".equals(opType)){
			BranchStaffExt branchStaffExt = branchStaffExtDao.findById(betOrder.getBranchStaff(), scheme);
			leftOwnerType = branchStaffExt.getLeftOwner();
		//}
		if(ManagerStaff.USER_TYPE_BRANCH.equals(replenish.getReplenishUserType())){
			if(betOrder.getChiefStaff()!=null){replenish.setChiefStaff(betOrder.getChiefStaff());}
			if(betOrder.getChiefRate()!=null){replenish.setRateChief(BigDecimal.valueOf(100));}
			
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_BRANCH);
			userInfo.setID(betOrder.getBranchStaff());
			replenish = replenishLogic.readyReplenishDataForCommission(replenish, userInfo,scheme);
			
		}else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(replenish.getReplenishUserType())){
			Integer chiefRate = betOrder.getChiefRate();
			Integer branchRate = betOrder.getBranchRate();
			//处理占成余归问题
			if(Constant.LEFT_OWNER_CHIEF.equals(leftOwnerType)){
            	chiefRate= 100 - betOrder.getBranchRate();						
			}else{
				branchRate= 100 - betOrder.getChiefRate();	
			}
			if(betOrder.getChiefStaff()!=null){replenish.setChiefStaff(betOrder.getChiefStaff());}
			if(betOrder.getChiefRate()!=null){replenish.setRateChief(BigDecimal.valueOf(chiefRate));}
			
			if(betOrder.getBranchStaff()!=null){replenish.setBranchStaff(betOrder.getBranchStaff());}
			if(betOrder.getBranchRate()!=null){replenish.setRateBranch(BigDecimal.valueOf(branchRate));}
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
			userInfo.setID(betOrder.getStockholderStaff());
			replenish = replenishLogic.readyReplenishDataForCommission(replenish, userInfo,scheme);
			
			
		}else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(replenish.getReplenishUserType())){
			Integer chiefRate = betOrder.getChiefRate();
			Integer branchRate = betOrder.getBranchRate();
			//处理占成余归问题
			//if("autoReplenish".equals(opType)){
				if(Constant.LEFT_OWNER_CHIEF.equals(leftOwnerType)){
	            	chiefRate= 100 - betOrder.getStockHolderRate() - betOrder.getBranchRate();						
				}else{
					branchRate= 100 - betOrder.getStockHolderRate() - betOrder.getChiefRate();	
				}
			//}
			
			if(betOrder.getChiefStaff()!=null){replenish.setChiefStaff(betOrder.getChiefStaff());}
			if(betOrder.getChiefRate()!=null){replenish.setRateChief(BigDecimal.valueOf(chiefRate));}
			
			if(betOrder.getBranchStaff()!=null){replenish.setBranchStaff(betOrder.getBranchStaff());}
			if(betOrder.getBranchRate()!=null){replenish.setRateBranch(BigDecimal.valueOf(branchRate));}
			
			if(betOrder.getStockholderStaff()!=null){replenish.setStockHolderStaff(betOrder.getStockholderStaff());}
			if(betOrder.getStockHolderRate()!=null){replenish.setRateStockHolder(BigDecimal.valueOf(betOrder.getStockHolderRate()));}
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
			userInfo.setID(betOrder.getGenAgenStaff());
			replenish = replenishLogic.readyReplenishDataForCommission(replenish, userInfo,scheme);
			
			
		}else if(ManagerStaff.USER_TYPE_AGENT.equals(replenish.getReplenishUserType())){
			Integer chiefRate = betOrder.getChiefRate();
			Integer branchRate = betOrder.getBranchRate();
			//处理占成余归问题
			//if("autoReplenish".equals(opType)){
				if(Constant.LEFT_OWNER_CHIEF.equals(leftOwnerType)){
	            	chiefRate= 100 - betOrder.getGenAgenRate() - betOrder.getStockHolderRate() - betOrder.getBranchRate();						
				}else{
					branchRate= 100 - betOrder.getGenAgenRate() - betOrder.getStockHolderRate() - betOrder.getChiefRate();	
				}
			//}
			
			if(betOrder.getChiefStaff()!=null){replenish.setChiefStaff(betOrder.getChiefStaff());}
			if(betOrder.getChiefRate()!=null){replenish.setRateChief(BigDecimal.valueOf(chiefRate));}
			
			if(betOrder.getBranchStaff()!=null){replenish.setBranchStaff(betOrder.getBranchStaff());}
			if(betOrder.getBranchRate()!=null){replenish.setRateBranch(BigDecimal.valueOf(branchRate));}
			
			if(betOrder.getStockholderStaff()!=null){replenish.setStockHolderStaff(betOrder.getStockholderStaff());}
			if(betOrder.getStockHolderRate()!=null){replenish.setRateStockHolder(BigDecimal.valueOf(betOrder.getStockHolderRate()));}
			
			if(betOrder.getGenAgenStaff()!=null){replenish.setGenAgenStaff(betOrder.getGenAgenStaff());}
			if(betOrder.getGenAgenRate()!=null){replenish.setRateGenAgent(BigDecimal.valueOf(betOrder.getGenAgenRate()));}
			
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_AGENT);
			userInfo.setID(betOrder.getAgentStaff());
			replenish = replenishLogic.readyReplenishDataForCommission(replenish, userInfo,scheme);
			
		}
		
		return replenish;
	}
	
	
	/**
	 * 获取上级的UserID，拥金和计算占成
	 * @param replenish
	 * @param betOrder
	 * @param opType 操作方式，"menuReplenish"即手动补货触发的，"autoReplenish":自动补货触发的，
	 *                      区别在于手动补货时占成已经处理好了，不用再处理，而自动补货触发的要在这里对占成处理一下，主要是余占问题
	 * @return
	 */
	public Replenish getReplenishData(Replenish replenish,BaseBet betOrder,String opType){
		String leftOwnerType = "";
		//if("autoReplenish".equals(opType)){
			BranchStaffExt branchStaffExt = branchStaffExtDao.findUniqueBy("ID", betOrder.getBranchStaff());
			leftOwnerType = branchStaffExt.getLeftOwner();
		//}
		
		if(ManagerStaff.USER_TYPE_BRANCH.equals(replenish.getReplenishUserType())){
			if(betOrder.getChiefStaff()!=null){replenish.setChiefStaff(betOrder.getChiefStaff());}
			if(betOrder.getChiefRate()!=null){replenish.setRateChief(BigDecimal.valueOf(100));}
			
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_BRANCH);
			userInfo.setID(betOrder.getBranchStaff());
			replenish = replenishLogic.readyReplenishDataForCommission(replenish, userInfo);
			
		}else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(replenish.getReplenishUserType())){
			Integer chiefRate = betOrder.getChiefRate();
			Integer branchRate = betOrder.getBranchRate();
			//处理占成余归问题
			if(Constant.LEFT_OWNER_CHIEF.equals(leftOwnerType)){
            	chiefRate= 100 - betOrder.getBranchRate();						
			}else{
				branchRate= 100 - betOrder.getChiefRate();	
			}
			if(betOrder.getChiefStaff()!=null){replenish.setChiefStaff(betOrder.getChiefStaff());}
			if(betOrder.getChiefRate()!=null){replenish.setRateChief(BigDecimal.valueOf(chiefRate));}
			
			if(betOrder.getBranchStaff()!=null){replenish.setBranchStaff(betOrder.getBranchStaff());}
			if(betOrder.getBranchRate()!=null){replenish.setRateBranch(BigDecimal.valueOf(branchRate));}
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
			userInfo.setID(betOrder.getStockholderStaff());
			replenish = replenishLogic.readyReplenishDataForCommission(replenish, userInfo);
			
			
		}else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(replenish.getReplenishUserType())){
			Integer chiefRate = betOrder.getChiefRate();
			Integer branchRate = betOrder.getBranchRate();
			//处理占成余归问题
			//if("autoReplenish".equals(opType)){
				if(Constant.LEFT_OWNER_CHIEF.equals(leftOwnerType)){
	            	chiefRate= 100 - betOrder.getStockHolderRate() - betOrder.getBranchRate();						
				}else{
					branchRate= 100 - betOrder.getStockHolderRate() - betOrder.getChiefRate();	
				}
			//}
			
			if(betOrder.getChiefStaff()!=null){replenish.setChiefStaff(betOrder.getChiefStaff());}
			if(betOrder.getChiefRate()!=null){replenish.setRateChief(BigDecimal.valueOf(chiefRate));}
			
			if(betOrder.getBranchStaff()!=null){replenish.setBranchStaff(betOrder.getBranchStaff());}
			if(betOrder.getBranchRate()!=null){replenish.setRateBranch(BigDecimal.valueOf(branchRate));}
			
			if(betOrder.getStockholderStaff()!=null){replenish.setStockHolderStaff(betOrder.getStockholderStaff());}
			if(betOrder.getStockHolderRate()!=null){replenish.setRateStockHolder(BigDecimal.valueOf(betOrder.getStockHolderRate()));}
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
			userInfo.setID(betOrder.getGenAgenStaff());
			replenish = replenishLogic.readyReplenishDataForCommission(replenish, userInfo);
			
			
		}else if(ManagerStaff.USER_TYPE_AGENT.equals(replenish.getReplenishUserType())){
			Integer chiefRate = betOrder.getChiefRate();
			Integer branchRate = betOrder.getBranchRate();
			//处理占成余归问题
			//if("autoReplenish".equals(opType)){
				if(Constant.LEFT_OWNER_CHIEF.equals(leftOwnerType)){
	            	chiefRate= 100 - betOrder.getGenAgenRate() - betOrder.getStockHolderRate() - betOrder.getBranchRate();						
				}else{
					branchRate= 100 - betOrder.getGenAgenRate() - betOrder.getStockHolderRate() - betOrder.getChiefRate();	
				}
			//}
			
			if(betOrder.getChiefStaff()!=null){replenish.setChiefStaff(betOrder.getChiefStaff());}
			if(betOrder.getChiefRate()!=null){replenish.setRateChief(BigDecimal.valueOf(chiefRate));}
			
			if(betOrder.getBranchStaff()!=null){replenish.setBranchStaff(betOrder.getBranchStaff());}
			if(betOrder.getBranchRate()!=null){replenish.setRateBranch(BigDecimal.valueOf(branchRate));}
			
			if(betOrder.getStockholderStaff()!=null){replenish.setStockHolderStaff(betOrder.getStockholderStaff());}
			if(betOrder.getStockHolderRate()!=null){replenish.setRateStockHolder(BigDecimal.valueOf(betOrder.getStockHolderRate()));}
			
			if(betOrder.getGenAgenStaff()!=null){replenish.setGenAgenStaff(betOrder.getGenAgenStaff());}
			if(betOrder.getGenAgenRate()!=null){replenish.setRateGenAgent(BigDecimal.valueOf(betOrder.getGenAgenRate()));}
			
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_AGENT);
			userInfo.setID(betOrder.getAgentStaff());
			replenish = replenishLogic.readyReplenishDataForCommission(replenish, userInfo);
			
		}
		
		return replenish;
	}
	
	/**
	 * 新方法，异步补货
	 */
	@Override
	public void updateReplenishAutoForUser(BaseBet betOrder, String scheme) {
		List<Long> userList = new ArrayList<Long>();
		int i = 0;
		List<AutoReplenishSetVO> branchList = new ArrayList<AutoReplenishSetVO>();
		List<AutoReplenishSetVO> stockList = new ArrayList<AutoReplenishSetVO>();
		List<AutoReplenishSetVO> genAgentList = new ArrayList<AutoReplenishSetVO>();
		List<AutoReplenishSetVO> agentList = new ArrayList<AutoReplenishSetVO>();
		
		String[] tableList = getTableList(betOrder);
		if(betOrder.getBranchStaff()!=null && betOrder.getBranchStaff()!=0){
			userList.add(i, betOrder.getBranchStaff());
			i+=1;
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_BRANCH);
			userInfo.setID(betOrder.getBranchStaff());
			UserVO userVO = replenishLogic.getUserType(userInfo);
			branchList=replenishAutoSetJDBCDao.queryTrueMoneyForSignal(betOrder.getPeriodsNum(), betOrder.getBranchStaff(), 
					ManagerStaff.USER_TYPE_BRANCH, userVO.getUserType(), userVO.getRateUser(), betOrder.getPlayType(), tableList,betOrder.getSplitAttribute(),scheme);
		}
		if(betOrder.getStockholderStaff()!=null && betOrder.getStockholderStaff()!=0){
			userList.add(i, betOrder.getStockholderStaff());
			i+=1;
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
			userInfo.setID(betOrder.getStockholderStaff());
			UserVO userVO = replenishLogic.getUserType(userInfo);
			stockList=replenishAutoSetJDBCDao.queryTrueMoneyForSignal(betOrder.getPeriodsNum(), betOrder.getStockholderStaff(), 
					ManagerStaff.USER_TYPE_STOCKHOLDER, userVO.getUserType(), userVO.getRateUser(), betOrder.getPlayType(), tableList,betOrder.getSplitAttribute(),scheme);
		}
		if(betOrder.getGenAgenStaff()!=null && betOrder.getGenAgenStaff()!=0){
			userList.add(i, betOrder.getGenAgenStaff());
			i+=1;
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
			userInfo.setID(betOrder.getGenAgenStaff());
			UserVO userVO = replenishLogic.getUserType(userInfo);
			genAgentList=replenishAutoSetJDBCDao.queryTrueMoneyForSignal(betOrder.getPeriodsNum(), betOrder.getGenAgenStaff(), 
					ManagerStaff.USER_TYPE_GEN_AGENT, userVO.getUserType(), userVO.getRateUser(), betOrder.getPlayType(), tableList,betOrder.getSplitAttribute(),scheme);
		}
		if(betOrder.getAgentStaff()!=null && betOrder.getAgentStaff()!=0){
			userList.add(i, betOrder.getAgentStaff());
			i+=1;
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_AGENT);
			userInfo.setID(betOrder.getAgentStaff());
			UserVO userVO = replenishLogic.getUserType(userInfo);
			agentList=replenishAutoSetJDBCDao.queryTrueMoneyForSignal(betOrder.getPeriodsNum(), betOrder.getAgentStaff(), 
					ManagerStaff.USER_TYPE_AGENT, userVO.getUserType(), userVO.getRateUser(), betOrder.getPlayType(), tableList,betOrder.getSplitAttribute(),scheme);
		}
		//查出关系用户树的自动补货设置,jdbc查询
		List<AutoReplenishSetVO> setList = replenishAutoSetJDBCDao.queryReplenishAutoSet(userList, betOrder.getShopInfo().getID().toString(), betOrder.getPlayType(),scheme);
//		List<AutoReplenishSetVO> setList = replenishAutoSetJDBCDao.queryReplenishAutoSet(userList, betOrder.getShopInfo().getID().toString(), betOrder.getPlayType());
		Map<String,Integer> map = new HashMap<String,Integer>();
		for(AutoReplenishSetVO vo:setList){
			map.put(vo.getUserType(), vo.getMoneyLimit());
		}
		//从代理开始计算应补金额，逐级往上算，统计完总的补货额后一次后补出
		Integer agentPet = 0;
		Integer genAgentPet = 0;
		Integer stockPet = 0;
		Integer branchPet = 0;
		
		BigDecimal agentPet_chief = BigDecimal.ZERO;
		BigDecimal genAgentPet_chief = BigDecimal.ZERO;
		BigDecimal stockPet_chief = BigDecimal.ZERO;
		BigDecimal branchPet_chief = BigDecimal.ZERO;
		
		//因为分公司占成有余占问题，所以要在这里存好，在统计分公司时使用
		Integer agent_BranchRate = 0;
		Integer genAgent_BranchRate = 0;
		Integer stock_BranchRate = 0;
		
	    //代理当前实货+投注额*占成-限额
		if(betOrder.getAgentStaff()!=null && betOrder.getAgentStaff()!=0 && map.get(ManagerStaff.USER_TYPE_AGENT)!=null){
			BigDecimal totalMoney = BigDecimal.ZERO;
		    if(agentList.size()!=0){
		    	totalMoney = agentList.get(0).getTotalMoney();
		    }
		    
		    Replenish replenish = new Replenish();
			replenish.setTypeCode(betOrder.getPlayType());// 要与界面传入的type一致
			
			replenish.setPlate(betOrder.getPlate());
			replenish.setOdds(betOrder.getOdds());
			replenish.setWinAmount(BigDecimal.ZERO);
			replenish.setAttribute(betOrder.getSplitAttribute());
			replenish.setReplenishUserType(ManagerStaff.USER_TYPE_AGENT);
			
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_AGENT);
			userInfo.setID(betOrder.getAgentStaff());
			
			ShopsInfo shopsInfo = new ShopsInfo();
			shopsInfo.setShopsCode(betOrder.getShopCode());
			userInfo.setShopsInfo(shopsInfo);
			
			replenish = this.getReplenishDataByScheme(replenish, betOrder,"autoReplenish",scheme);
			
			agent_BranchRate = replenish.getRateBranch().intValue();
			
			agentPet = totalMoney.intValue() - map.get(ManagerStaff.USER_TYPE_AGENT);
			
			
			if(agentPet>0){	
				//System.out.println("~~~~~~~~~~~~~~~replenish.getRateChief():"+ replenish.getRateChief());
				//System.out.println("~~~~~~~~~~~~~~~replenish.getRateChief().intValue()/100:"+ replenish.getRateChief().divide(BigDecimal.valueOf(100)));
				agentPet_chief = BigDecimal.valueOf(agentPet).multiply(replenish.getRateChief().divide(BigDecimal.valueOf(100)));
				replenish.setMoney(agentPet);
				replenishLogic.saveReplenishByScheme(replenish, userInfo, betOrder.getPeriodsNum(), "","no","autoReplenish",scheme);
				replenishAutoLogLogic.saveReplenishLogByScheme(new ReplenishAutoLog(betOrder.getShopInfo().getID(), betOrder.getPlayType().split("_")[0], betOrder.getPlayType(), 
						agentPet, betOrder.getAgentStaff().intValue(),betOrder.getPeriodsNum(),"1"),scheme);
	            LOG.info("<--自动补货类型："+betOrder.getPlayType()+", 补货金额："+agentPet+", 补货人："+betOrder.getAgentStaff()+", 盘期："+betOrder.getPeriodsNum()+" -->");
		    }else{
		    	agentPet=0;
		    }
		}
		//总代理当前实货+投注额*占成-限额+代理补出额*占成
		if(betOrder.getGenAgenStaff()!=null && betOrder.getGenAgenStaff()!=0 && map.get(ManagerStaff.USER_TYPE_GEN_AGENT)!=null){
			BigDecimal totalMoney = BigDecimal.ZERO;
		    if(genAgentList.size()!=0){
		    	totalMoney = genAgentList.get(0).getTotalMoney();
		    }
			
			Replenish replenish = new Replenish();
			replenish.setTypeCode(betOrder.getPlayType());// 要与界面传入的type一致
			
			replenish.setPlate(betOrder.getPlate());
			replenish.setOdds(betOrder.getOdds());
			replenish.setWinAmount(BigDecimal.ZERO);
			replenish.setAttribute(betOrder.getSplitAttribute());
			replenish.setReplenishUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
			
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
			userInfo.setID(betOrder.getGenAgenStaff());
			
			ShopsInfo shopsInfo = new ShopsInfo();
			shopsInfo.setShopsCode(betOrder.getShopCode());
			userInfo.setShopsInfo(shopsInfo);
			
			replenish = this.getReplenishDataByScheme(replenish, betOrder,"autoReplenish",scheme);
			
			genAgent_BranchRate = replenish.getRateBranch().intValue();
			
			genAgentPet = totalMoney.intValue() + agentPet * betOrder.getGenAgenRate()/100 - map.get(ManagerStaff.USER_TYPE_GEN_AGENT);
			
			if(genAgentPet>0){	
				genAgentPet_chief = BigDecimal.valueOf(genAgentPet).multiply(replenish.getRateChief().divide(BigDecimal.valueOf(100)));
				replenish.setMoney(genAgentPet);
				replenishLogic.saveReplenishByScheme(replenish, userInfo, betOrder.getPeriodsNum(), "","no","autoReplenish",scheme);
				replenishAutoLogLogic.saveReplenishLogByScheme(new ReplenishAutoLog(betOrder.getShopInfo().getID(), betOrder.getPlayType().split("_")[0], betOrder.getPlayType(), 
						genAgentPet, betOrder.getGenAgenStaff().intValue(),betOrder.getPeriodsNum(),"1"),scheme);
	            LOG.info("<--自动补货类型："+betOrder.getPlayType()+", 补货金额："+genAgentPet+", 补货人："+betOrder.getGenAgenStaff()+", 盘期："+betOrder.getPeriodsNum()+" -->");
			}else{
				genAgentPet=0;
		    }
		}
		//股东当前实货+投注额*占成-限额+代理补出额*占成+总代理补出额*占成
		if(betOrder.getStockholderStaff()!=null && betOrder.getStockholderStaff()!=0 && map.get(ManagerStaff.USER_TYPE_STOCKHOLDER)!=null){
			BigDecimal totalMoney = BigDecimal.ZERO;
		    if(stockList.size()!=0){
		    	totalMoney = stockList.get(0).getTotalMoney();
		    }
			stockPet = totalMoney.intValue() + agentPet * betOrder.getStockHolderRate()/100  + genAgentPet * betOrder.getStockHolderRate()/100 - map.get(ManagerStaff.USER_TYPE_STOCKHOLDER);
			if(stockPet>0){
				Replenish replenish = new Replenish();
				replenish.setTypeCode(betOrder.getPlayType());// 要与界面传入的type一致
				replenish.setMoney(stockPet);
				replenish.setPlate(betOrder.getPlate());
				replenish.setOdds(betOrder.getOdds());
				replenish.setWinAmount(BigDecimal.ZERO);
				replenish.setAttribute(betOrder.getSplitAttribute());
				replenish.setReplenishUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
				
				ManagerUser userInfo = new ManagerUser();
				userInfo.setUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
				userInfo.setID(betOrder.getStockholderStaff());
				
				ShopsInfo shopsInfo = new ShopsInfo();
				shopsInfo.setShopsCode(betOrder.getShopCode());
				userInfo.setShopsInfo(shopsInfo);
				
				replenish = this.getReplenishDataByScheme(replenish, betOrder,"autoReplenish",scheme);
				
				stockPet_chief = BigDecimal.valueOf(stockPet).multiply(replenish.getRateChief().divide(BigDecimal.valueOf(100)));
				
				stock_BranchRate = replenish.getRateBranch().intValue();
				
				replenishLogic.saveReplenishByScheme(replenish, userInfo, betOrder.getPeriodsNum(), "","no","autoReplenish",scheme);
				replenishAutoLogLogic.saveReplenishLogByScheme(new ReplenishAutoLog(betOrder.getShopInfo().getID(), betOrder.getPlayType().split("_")[0], betOrder.getPlayType(), 
						stockPet, betOrder.getStockholderStaff().intValue(),betOrder.getPeriodsNum(),"1"),scheme);
	            LOG.info("<--自动补货类型："+betOrder.getPlayType()+", 补货金额："+stockPet+", 补货人："+betOrder.getStockholderStaff()+" -->");
			}else{
				stockPet=0;
		    }     
		}
		//分公司当前实货+投注额*占成-限额+代理补出额*占成+总代理补出额*占成+股东补出额*占成
		if(betOrder.getBranchStaff()!=null && betOrder.getBranchStaff()!=0 && map.get(ManagerStaff.USER_TYPE_BRANCH)!=null){
			BigDecimal totalMoney = BigDecimal.ZERO;
		    if(branchList.size()!=0){
		    	totalMoney = branchList.get(0).getTotalMoney();
		    }
			//这里不处理谁是补货人问题,放在了replenishLogic.saveReplenish处理。
			
			Replenish replenish = new Replenish();
			replenish.setTypeCode(betOrder.getPlayType());// 要与界面传入的type一致
			replenish.setPlate(betOrder.getPlate());
			replenish.setOdds(betOrder.getOdds());
			replenish.setWinAmount(BigDecimal.ZERO);
			replenish.setAttribute(betOrder.getSplitAttribute());
			replenish.setReplenishUserType(ManagerStaff.USER_TYPE_BRANCH);

			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_BRANCH);
			userInfo.setID(betOrder.getBranchStaff());
			
			ShopsInfo shopsInfo = new ShopsInfo();
			shopsInfo.setShopsCode(betOrder.getShopCode());
			userInfo.setShopsInfo(shopsInfo);
			
			replenish = this.getReplenishDataByScheme(replenish, betOrder,"autoReplenish",scheme);
			
			Integer limit = map.get(ManagerStaff.USER_TYPE_BRANCH);
			
			branchPet = (totalMoney.intValue() + agentPet * agent_BranchRate/100 + genAgentPet * genAgent_BranchRate/100 + stockPet * stock_BranchRate/100) - limit;
				
			if(branchPet>0){	
				branchPet_chief = BigDecimal.valueOf(branchPet).multiply(replenish.getRateChief().divide(BigDecimal.valueOf(100)));
				replenish.setMoney(branchPet);
				replenishLogic.saveReplenishByScheme(replenish, userInfo, betOrder.getPeriodsNum(), "","no","autoReplenish",scheme);
				replenishAutoLogLogic.saveReplenishLogByScheme(new ReplenishAutoLog(betOrder.getShopInfo().getID(), betOrder.getPlayType().split("_")[0], betOrder.getPlayType(), 
						branchPet, betOrder.getBranchStaff().intValue(),betOrder.getPeriodsNum(),"1"),scheme);
	            LOG.info("<--自动补货类型："+betOrder.getPlayType()+", 补货金额："+branchPet+", 补货人："+betOrder.getBranchStaff()+", 盘期："+betOrder.getPeriodsNum()+" -->");
			}else{
				branchPet=0;
		    }    
		}
		//补货触发的自动降赔,上面每一级补货时会把补出的货*总监占成后，存到变量里面，放到这里作为总监本次增加的实货，再进行自动降赔计算
		Integer chiefPet = (agentPet_chief.add(genAgentPet_chief).add(stockPet_chief).add(branchPet_chief)).intValue();
		replenishLogic.updateShopRealOddsForReplenishByScheme(betOrder.getShopCode(), chiefPet, betOrder.getBettingUserId(), betOrder.getPlayType(), betOrder.getPeriodsNum(),scheme);
	}
	
	/**
	 * 获取查询表数组
	 * @param betOrder
	 * @return
	 */
	private String[] getTableList(BaseBet betOrder){
		String[] tableList = new String[] {};
		PlayType playType = PlayTypeUtils.getPlayType(betOrder.getPlayType());
		if(Constant.LOTTERY_TYPE_GDKLSF.equals(playType.getPlayType())){
			if(playType.getPlaySubType().indexOf("BALL")!=-1){
				String[] str = playType.getPlaySubType().split("_");
				tableList =  new String[] {"TB_GDKLSF_BALL_"+str[1],Constant.GDKLSF_DOUBLESIDE_TABLE_NAME};
			}else if(playType.getPlaySubType().indexOf("DOUBLESIDE")!=-1){
				tableList =  Constant.GDKLSF_LOTTERY_TABLE_LIST;
			}else{
				tableList =  new String[] {Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME};
			}
		}else 
		if(Constant.LOTTERY_TYPE_CQSSC.equals(playType.getPlayType())){
			if(betOrder.getPlayType().indexOf("CQSSC_DOUBLESIDE_ZH")!=-1 || "CQSSC_DOUBLESIDE_LONG".equals(betOrder.getPlayType()) 
					|| "CQSSC_DOUBLESIDE_HU".equals(betOrder.getPlayType()) || "CQSSC_DOUBLESIDE_HE".equals(betOrder.getPlayType())){
				
				tableList =  Constant.CQSSC_TABLE_LIST;
			}else if(playType.getPlaySubType().indexOf("BALL")!=-1){
				String[] str = playType.getPlaySubType().split("_");
				tableList =  new String[] {"TB_CQSSC_BALL_"+str[1]};
			}else{
				tableList =  Constant.CQSSC_TABLE_LIST;
			}
		}else if(Constant.LOTTERY_TYPE_BJ.equals(playType.getPlayType())){
			tableList =  new String[] {"TB_BJSC"};
		}else if(Constant.LOTTERY_TYPE_NC.equals(playType.getPlayType())){
			tableList =  new String[] {"TB_NC"};
		}else if(Constant.LOTTERY_TYPE_K3.equals(playType.getPlayType())){
				tableList =  new String[] {"TB_JSSB"};
		}
		return tableList;
	}
	
	/**
	 * 逻辑：1、先查出关系用户树的自动补货设置
	 *     2、再查出关系用户的实货
	 *     3、循环用户，统计补货额
	 *     4、补货操作
	 * 自动补货
	 * 
	 */
	@Override
	public void updateReplenishAutoForUser(BaseBet betOrder){
		List<Long> userList = new ArrayList<Long>();
		int i = 0;
		List<AutoReplenishSetVO> branchList = new ArrayList<AutoReplenishSetVO>();
		List<AutoReplenishSetVO> stockList = new ArrayList<AutoReplenishSetVO>();
		List<AutoReplenishSetVO> genAgentList = new ArrayList<AutoReplenishSetVO>();
		List<AutoReplenishSetVO> agentList = new ArrayList<AutoReplenishSetVO>();
		
		String[] tableList=getTableList(betOrder);
		
		if(betOrder.getBranchStaff()!=null && betOrder.getBranchStaff()!=0){
			userList.add(i, betOrder.getBranchStaff());
			i+=1;
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_BRANCH);
			userInfo.setID(betOrder.getBranchStaff());
			UserVO userVO = replenishLogic.getUserType(userInfo);
			branchList=replenishAutoSetJDBCDao.queryTrueMoneyForSignal(betOrder.getPeriodsNum(), betOrder.getBranchStaff(), 
					ManagerStaff.USER_TYPE_BRANCH, userVO.getUserType(), userVO.getRateUser(), betOrder.getPlayType(), tableList,betOrder.getSplitAttribute(),"");
		}
		if(betOrder.getStockholderStaff()!=null && betOrder.getStockholderStaff()!=0){
			userList.add(i, betOrder.getStockholderStaff());
			i+=1;
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
			userInfo.setID(betOrder.getStockholderStaff());
			UserVO userVO = replenishLogic.getUserType(userInfo);
			stockList=replenishAutoSetJDBCDao.queryTrueMoneyForSignal(betOrder.getPeriodsNum(), betOrder.getStockholderStaff(), 
					ManagerStaff.USER_TYPE_STOCKHOLDER, userVO.getUserType(), userVO.getRateUser(), betOrder.getPlayType(), tableList,betOrder.getSplitAttribute(),"");
		}
		if(betOrder.getGenAgenStaff()!=null && betOrder.getGenAgenStaff()!=0){
			userList.add(i, betOrder.getGenAgenStaff());
			i+=1;
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
			userInfo.setID(betOrder.getGenAgenStaff());
			UserVO userVO = replenishLogic.getUserType(userInfo);
			genAgentList=replenishAutoSetJDBCDao.queryTrueMoneyForSignal(betOrder.getPeriodsNum(), betOrder.getGenAgenStaff(), 
					ManagerStaff.USER_TYPE_GEN_AGENT, userVO.getUserType(), userVO.getRateUser(), betOrder.getPlayType(), tableList,betOrder.getSplitAttribute(),"");
		}
		if(betOrder.getAgentStaff()!=null && betOrder.getAgentStaff()!=0){
			userList.add(i, betOrder.getAgentStaff());
			i+=1;
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_AGENT);
			userInfo.setID(betOrder.getAgentStaff());
			UserVO userVO = replenishLogic.getUserType(userInfo);
			agentList=replenishAutoSetJDBCDao.queryTrueMoneyForSignal(betOrder.getPeriodsNum(), betOrder.getAgentStaff(), 
					ManagerStaff.USER_TYPE_AGENT, userVO.getUserType(), userVO.getRateUser(), betOrder.getPlayType(), tableList,betOrder.getSplitAttribute(),"");
		}
		//查出关系用户树的自动补货设置
		List<AutoReplenishSetVO> setList = replenishAutoSetJDBCDao.queryReplenishAutoSet(userList, betOrder.getShopInfo().getID().toString(), betOrder.getPlayType());
		Map<String,Integer> map = new HashMap<String,Integer>();
		for(AutoReplenishSetVO vo:setList){
			map.put(vo.getUserType(), vo.getMoneyLimit());
		}
		//从代理开始计算应补金额，逐级往上算，统计完总的补货额后一次后补出
		Integer agentPet = 0;
		Integer genAgentPet = 0;
		Integer stockPet = 0;
		Integer branchPet = 0;
		
		BigDecimal agentPet_chief = BigDecimal.ZERO;
		BigDecimal genAgentPet_chief = BigDecimal.ZERO;
		BigDecimal stockPet_chief = BigDecimal.ZERO;
		BigDecimal branchPet_chief = BigDecimal.ZERO;
		
		//因为分公司占成有余占问题，所以要在这里存好，在统计分公司时使用
		Integer agent_BranchRate = 0;
		Integer genAgent_BranchRate = 0;
		Integer stock_BranchRate = 0;
		
	    //代理当前实货+投注额*占成-限额
		if(betOrder.getAgentStaff()!=null && betOrder.getAgentStaff()!=0 && map.get(ManagerStaff.USER_TYPE_AGENT)!=null){
			BigDecimal totalMoney = BigDecimal.ZERO;
		    if(agentList.size()!=0){
		    	totalMoney = agentList.get(0).getTotalMoney();
		    }
		    
		    Replenish replenish = new Replenish();
			replenish.setTypeCode(betOrder.getPlayType());// 要与界面传入的type一致
			
			replenish.setPlate(betOrder.getPlate());
			replenish.setOdds(betOrder.getOdds());
			replenish.setWinAmount(BigDecimal.ZERO);
			replenish.setAttribute(betOrder.getSplitAttribute());
			replenish.setReplenishUserType(ManagerStaff.USER_TYPE_AGENT);
			
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_AGENT);
			userInfo.setID(betOrder.getAgentStaff());
			
			ShopsInfo shopsInfo = new ShopsInfo();
			shopsInfo.setShopsCode(betOrder.getShopCode());
			userInfo.setShopsInfo(shopsInfo);
			
			replenish = this.getReplenishData(replenish, betOrder,"autoReplenish");
			
			agent_BranchRate = replenish.getRateBranch().intValue();
			
			agentPet = totalMoney.intValue() - map.get(ManagerStaff.USER_TYPE_AGENT);
			
			
			if(agentPet>0){	
				//System.out.println("~~~~~~~~~~~~~~~replenish.getRateChief():"+ replenish.getRateChief());
				//System.out.println("~~~~~~~~~~~~~~~replenish.getRateChief().intValue()/100:"+ replenish.getRateChief().divide(BigDecimal.valueOf(100)));
				agentPet_chief = BigDecimal.valueOf(agentPet).multiply(replenish.getRateChief().divide(BigDecimal.valueOf(100)));
				replenish.setMoney(agentPet);
				replenishLogic.saveReplenish(replenish, userInfo, betOrder.getPeriodsNum(), "","no","autoReplenish");
				replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(betOrder.getShopInfo().getID(), betOrder.getPlayType().split("_")[0], betOrder.getPlayType(), 
						agentPet, betOrder.getAgentStaff().intValue(),betOrder.getPeriodsNum(),"1"));
	            LOG.info("<--自动补货类型："+betOrder.getPlayType()+", 补货金额："+agentPet+", 补货人："+betOrder.getAgentStaff()+", 盘期："+betOrder.getPeriodsNum()+" -->");
		    }else{
		    	agentPet=0;
		    }
		}
		//总代理当前实货+投注额*占成-限额+代理补出额*占成
		if(betOrder.getGenAgenStaff()!=null && betOrder.getGenAgenStaff()!=0 && map.get(ManagerStaff.USER_TYPE_GEN_AGENT)!=null){
			BigDecimal totalMoney = BigDecimal.ZERO;
		    if(genAgentList.size()!=0){
		    	totalMoney = genAgentList.get(0).getTotalMoney();
		    }
			
			Replenish replenish = new Replenish();
			replenish.setTypeCode(betOrder.getPlayType());// 要与界面传入的type一致
			
			replenish.setPlate(betOrder.getPlate());
			replenish.setOdds(betOrder.getOdds());
			replenish.setWinAmount(BigDecimal.ZERO);
			replenish.setAttribute(betOrder.getSplitAttribute());
			replenish.setReplenishUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
			
			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
			userInfo.setID(betOrder.getGenAgenStaff());
			
			ShopsInfo shopsInfo = new ShopsInfo();
			shopsInfo.setShopsCode(betOrder.getShopCode());
			userInfo.setShopsInfo(shopsInfo);
			
			replenish = this.getReplenishData(replenish, betOrder,"autoReplenish");
			
			genAgent_BranchRate = replenish.getRateBranch().intValue();
			
			genAgentPet = totalMoney.intValue() + agentPet * betOrder.getGenAgenRate()/100 - map.get(ManagerStaff.USER_TYPE_GEN_AGENT);
			
			if(genAgentPet>0){	
				genAgentPet_chief = BigDecimal.valueOf(genAgentPet).multiply(replenish.getRateChief().divide(BigDecimal.valueOf(100)));
				replenish.setMoney(genAgentPet);
				replenishLogic.saveReplenish(replenish, userInfo, betOrder.getPeriodsNum(), "","no","autoReplenish");
				replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(betOrder.getShopInfo().getID(), betOrder.getPlayType().split("_")[0], betOrder.getPlayType(), 
						genAgentPet, betOrder.getGenAgenStaff().intValue(),betOrder.getPeriodsNum(),"1"));
	            LOG.info("<--自动补货类型："+betOrder.getPlayType()+", 补货金额："+genAgentPet+", 补货人："+betOrder.getGenAgenStaff()+", 盘期："+betOrder.getPeriodsNum()+" -->");
			}else{
				genAgentPet=0;
		    }
		}
		//股东当前实货+投注额*占成-限额+代理补出额*占成+总代理补出额*占成
		if(betOrder.getStockholderStaff()!=null && betOrder.getStockholderStaff()!=0 && map.get(ManagerStaff.USER_TYPE_STOCKHOLDER)!=null){
			BigDecimal totalMoney = BigDecimal.ZERO;
		    if(stockList.size()!=0){
		    	totalMoney = stockList.get(0).getTotalMoney();
		    }
			stockPet = totalMoney.intValue() + agentPet * betOrder.getStockHolderRate()/100  + genAgentPet * betOrder.getStockHolderRate()/100 - map.get(ManagerStaff.USER_TYPE_STOCKHOLDER);
			if(stockPet>0){
				Replenish replenish = new Replenish();
				replenish.setTypeCode(betOrder.getPlayType());// 要与界面传入的type一致
				replenish.setMoney(stockPet);
				replenish.setPlate(betOrder.getPlate());
				replenish.setOdds(betOrder.getOdds());
				replenish.setWinAmount(BigDecimal.ZERO);
				replenish.setAttribute(betOrder.getSplitAttribute());
				replenish.setReplenishUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
				
				ManagerUser userInfo = new ManagerUser();
				userInfo.setUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
				userInfo.setID(betOrder.getStockholderStaff());
				
				ShopsInfo shopsInfo = new ShopsInfo();
				shopsInfo.setShopsCode(betOrder.getShopCode());
				userInfo.setShopsInfo(shopsInfo);
				
				replenish = this.getReplenishData(replenish, betOrder,"autoReplenish");
				
				stockPet_chief = BigDecimal.valueOf(stockPet).multiply(replenish.getRateChief().divide(BigDecimal.valueOf(100)));
				
				stock_BranchRate = replenish.getRateBranch().intValue();
				
				replenishLogic.saveReplenish(replenish, userInfo, betOrder.getPeriodsNum(), "","no","autoReplenish");
				replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(betOrder.getShopInfo().getID(), betOrder.getPlayType().split("_")[0], betOrder.getPlayType(), 
						stockPet, betOrder.getStockholderStaff().intValue(),betOrder.getPeriodsNum(),"1"));
	            LOG.info("<--自动补货类型："+betOrder.getPlayType()+", 补货金额："+stockPet+", 补货人："+betOrder.getStockholderStaff()+" -->");
			}else{
				stockPet=0;
		    }     
		}
		//分公司当前实货+投注额*占成-限额+代理补出额*占成+总代理补出额*占成+股东补出额*占成
		if(betOrder.getBranchStaff()!=null && betOrder.getBranchStaff()!=0 && map.get(ManagerStaff.USER_TYPE_BRANCH)!=null){
			BigDecimal totalMoney = BigDecimal.ZERO;
		    if(branchList.size()!=0){
		    	totalMoney = branchList.get(0).getTotalMoney();
		    }
			//这里不处理谁是补货人问题,放在了replenishLogic.saveReplenish处理。
			
			Replenish replenish = new Replenish();
			replenish.setTypeCode(betOrder.getPlayType());// 要与界面传入的type一致
			replenish.setPlate(betOrder.getPlate());
			replenish.setOdds(betOrder.getOdds());
			replenish.setWinAmount(BigDecimal.ZERO);
			replenish.setAttribute(betOrder.getSplitAttribute());
			replenish.setReplenishUserType(ManagerStaff.USER_TYPE_BRANCH);

			ManagerUser userInfo = new ManagerUser();
			userInfo.setUserType(ManagerStaff.USER_TYPE_BRANCH);
			userInfo.setID(betOrder.getBranchStaff());
			
			ShopsInfo shopsInfo = new ShopsInfo();
			shopsInfo.setShopsCode(betOrder.getShopCode());
			userInfo.setShopsInfo(shopsInfo);
			
			replenish = this.getReplenishData(replenish, betOrder,"autoReplenish");
			
			Integer limit = map.get(ManagerStaff.USER_TYPE_BRANCH);
			
			branchPet = (totalMoney.intValue() + agentPet * agent_BranchRate/100 + genAgentPet * genAgent_BranchRate/100 + stockPet * stock_BranchRate/100) - limit;
				
			if(branchPet>0){	
				branchPet_chief = BigDecimal.valueOf(branchPet).multiply(replenish.getRateChief().divide(BigDecimal.valueOf(100)));
				replenish.setMoney(branchPet);
				replenishLogic.saveReplenish(replenish, userInfo, betOrder.getPeriodsNum(), "","no","autoReplenish");
				replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(betOrder.getShopInfo().getID(), betOrder.getPlayType().split("_")[0], betOrder.getPlayType(), 
						branchPet, betOrder.getBranchStaff().intValue(),betOrder.getPeriodsNum(),"1"));
	            LOG.info("<--自动补货类型："+betOrder.getPlayType()+", 补货金额："+branchPet+", 补货人："+betOrder.getBranchStaff()+", 盘期："+betOrder.getPeriodsNum()+" -->");
			}else{
				branchPet=0;
		    }    
		}
		//补货触发的自动降赔,上面每一级补货时会把补出的货*总监占成后，存到变量里面，放到这里作为总监本次增加的实货，再进行自动降赔计算
		Integer chiefPet = (agentPet_chief.add(genAgentPet_chief).add(stockPet_chief).add(branchPet_chief)).intValue();
		replenishLogic.updateShopRealOddsForReplenish(betOrder.getShopCode(), chiefPet, betOrder.getBettingUserId(), betOrder.getPlayType(), betOrder.getPeriodsNum());
	}
	
	/**
	 * 逻辑：1、先查出关系用户树的自动补货设置
	 *     2、再查出关系用户的实货
	 *     3、循环用户，统计补货额
	 *     4、补货操作
	 * 自动补货
	 * 
	 */
	@Override
	public void updateReplenishAutoForMenu(BaseBet betOrder,ManagerUser userInfoOrg){
		//LOG.info("<--手动补货触发自動補貨   start-->");
		long startTime = System.currentTimeMillis();
		List<Long> userList = new ArrayList<Long>();
		int i = 0;
		List<AutoReplenishSetVO> branchList = new ArrayList<AutoReplenishSetVO>();
		List<AutoReplenishSetVO> stockList = new ArrayList<AutoReplenishSetVO>();
		List<AutoReplenishSetVO> genAgentList = new ArrayList<AutoReplenishSetVO>();
		List<AutoReplenishSetVO> agentList = new ArrayList<AutoReplenishSetVO>();
		String[] tableList = new String[] {};
		
		PlayType playType = PlayTypeUtils.getPlayType(betOrder.getPlayType());
		if(Constant.LOTTERY_TYPE_GDKLSF.equals(playType.getPlayType())){
			if(playType.getPlaySubType().indexOf("BALL")!=-1){
				String[] str = playType.getPlaySubType().split("_");
				tableList =  new String[] {"TB_GDKLSF_BALL_"+str[1],Constant.GDKLSF_DOUBLESIDE_TABLE_NAME};
			}else if(playType.getPlaySubType().indexOf("DOUBLESIDE")!=-1){
				tableList =  Constant.GDKLSF_LOTTERY_TABLE_LIST;
			}else{
				tableList =  new String[] {Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME};
			}
		}else 
		if(betOrder.getPlayType().indexOf("CQSSC_DOUBLESIDE_ZH")!=-1 || "CQSSC_DOUBLESIDE_LONG".equals(betOrder.getPlayType()) 
		|| "CQSSC_DOUBLESIDE_HU".equals(betOrder.getPlayType()) || "CQSSC_DOUBLESIDE_HE".equals(betOrder.getPlayType())){
	
			tableList =  Constant.CQSSC_TABLE_LIST;
		}else if(Constant.LOTTERY_TYPE_CQSSC.equals(playType.getPlayType())){
			if(playType.getPlaySubType().indexOf("BALL")!=-1){
				String[] str = playType.getPlaySubType().split("_");
				tableList =  new String[] {"TB_CQSSC_BALL_"+str[1]};
			}else{
				tableList =  Constant.CQSSC_TABLE_LIST;
			}
		}else if(Constant.LOTTERY_TYPE_BJ.equals(playType.getPlayType())){ 
			tableList =  new String[] {"TB_BJSC"};
		}else if(Constant.LOTTERY_TYPE_NC.equals(playType.getPlayType())){ 
			tableList =  new String[] {"TB_NC"};
		}else if(Constant.LOTTERY_TYPE_K3.equals(playType.getPlayType())){
				tableList =  new String[] {"TB_JSSB"};
		}
		
		if(ManagerStaff.USER_TYPE_AGENT.equals(userInfoOrg.getUserType()) || ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfoOrg.getUserType()) 
				|| ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfoOrg.getUserType()) || ManagerStaff.USER_TYPE_BRANCH.equals(userInfoOrg.getUserType())){
			if(betOrder.getBranchStaff()!=null && betOrder.getBranchStaff()!=0){
				userList.add(i, betOrder.getBranchStaff());
				i+=1;
				ManagerUser userInfo = new ManagerUser();
				userInfo.setUserType(ManagerStaff.USER_TYPE_BRANCH);
				userInfo.setID(betOrder.getBranchStaff());
				UserVO userVO = replenishLogic.getUserType(userInfo);
				branchList=replenishAutoSetJDBCDao.queryTrueMoneyForSignal(betOrder.getPeriodsNum(), betOrder.getBranchStaff(), 
						ManagerStaff.USER_TYPE_BRANCH, userVO.getUserType(), userVO.getRateUser(), betOrder.getPlayType(), tableList,betOrder.getSplitAttribute(),"");
			}
		}
		if(ManagerStaff.USER_TYPE_AGENT.equals(userInfoOrg.getUserType()) || ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfoOrg.getUserType()) 
				|| ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfoOrg.getUserType())){
			if(betOrder.getStockholderStaff()!=null && betOrder.getStockholderStaff()!=0){
				userList.add(i, betOrder.getStockholderStaff());
				i+=1;
				ManagerUser userInfo = new ManagerUser();
				userInfo.setUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
				userInfo.setID(betOrder.getStockholderStaff());
				UserVO userVO = replenishLogic.getUserType(userInfo);
				stockList=replenishAutoSetJDBCDao.queryTrueMoneyForSignal(betOrder.getPeriodsNum(), betOrder.getStockholderStaff(), 
						ManagerStaff.USER_TYPE_STOCKHOLDER, userVO.getUserType(), userVO.getRateUser(), betOrder.getPlayType(), tableList,betOrder.getSplitAttribute(),"");
			}
		}
		if(ManagerStaff.USER_TYPE_AGENT.equals(userInfoOrg.getUserType()) || ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfoOrg.getUserType())){
			if(betOrder.getGenAgenStaff()!=null && betOrder.getGenAgenStaff()!=0){
				userList.add(i, betOrder.getGenAgenStaff());
				i+=1;
				ManagerUser userInfo = new ManagerUser();
				userInfo.setUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
				userInfo.setID(betOrder.getGenAgenStaff());
				UserVO userVO = replenishLogic.getUserType(userInfo);
				genAgentList=replenishAutoSetJDBCDao.queryTrueMoneyForSignal(betOrder.getPeriodsNum(), betOrder.getGenAgenStaff(), 
						ManagerStaff.USER_TYPE_GEN_AGENT, userVO.getUserType(), userVO.getRateUser(), betOrder.getPlayType(), tableList,betOrder.getSplitAttribute(),"");
			}
		}
		if(ManagerStaff.USER_TYPE_AGENT.equals(userInfoOrg.getUserType())){
			if(betOrder.getAgentStaff()!=null && betOrder.getAgentStaff()!=0){
				userList.add(i, betOrder.getAgentStaff());
				i+=1;
				ManagerUser userInfo = new ManagerUser();
				userInfo.setUserType(ManagerStaff.USER_TYPE_AGENT);
				userInfo.setID(betOrder.getAgentStaff());
				UserVO userVO = replenishLogic.getUserType(userInfo);
				agentList=replenishAutoSetJDBCDao.queryTrueMoneyForSignal(betOrder.getPeriodsNum(), betOrder.getAgentStaff(), 
						ManagerStaff.USER_TYPE_AGENT, userVO.getUserType(), userVO.getRateUser(), betOrder.getPlayType(), tableList,betOrder.getSplitAttribute(),"");
			}
		}
		//查出关系用户树的自动补货设置
		List<AutoReplenishSetVO> setList = replenishAutoSetJDBCDao.queryReplenishAutoSet(userList, betOrder.getShopInfo().getID().toString(), betOrder.getPlayType());
		Map<String,Integer> map = new HashMap<String,Integer>();
		for(AutoReplenishSetVO vo:setList){
			map.put(vo.getUserType(), vo.getMoneyLimit());
		}
		//从代理开始计算应补金额，逐级往上算，统计完总的补货额后一次后补出
		Integer agentPet = 0;
		Integer genAgentPet = 0;
		Integer stockPet = 0;
		Integer branchPet = 0;
		
		BigDecimal agentPet_chief = BigDecimal.ZERO;
		BigDecimal genAgentPet_chief = BigDecimal.ZERO;
		BigDecimal stockPet_chief = BigDecimal.ZERO;
		BigDecimal branchPet_chief = BigDecimal.ZERO;
		
		//因为分公司占成有余占问题，所以要在这里存好，在统计分公司时使用
		Integer agent_BranchRate = 0;
		Integer genAgent_BranchRate = 0;
		Integer stock_BranchRate = 0;
		
		//代理当前实货+投注额*占成-限额
		if(ManagerStaff.USER_TYPE_AGENT.equals(userInfoOrg.getUserType())){
			if(betOrder.getAgentStaff()!=null && betOrder.getAgentStaff()!=0 && map.get(ManagerStaff.USER_TYPE_AGENT)!=null){
				BigDecimal totalMoney = BigDecimal.ZERO;
				if(agentList.size()!=0){
					totalMoney = agentList.get(0).getTotalMoney();
				}
				agentPet = totalMoney.intValue() - map.get(ManagerStaff.USER_TYPE_AGENT);
				
				if(agentPet>0){
					Replenish replenish = new Replenish();
					replenish.setTypeCode(betOrder.getPlayType());// 要与界面传入的type一致
					replenish.setMoney(agentPet);
					replenish.setPlate(betOrder.getPlate());
					replenish.setOdds(betOrder.getOdds());
					replenish.setWinAmount(BigDecimal.ZERO);
					replenish.setAttribute(betOrder.getSplitAttribute());
					replenish.setReplenishUserType(ManagerStaff.USER_TYPE_AGENT);
					
					ManagerUser userInfo = new ManagerUser();
					userInfo.setUserType(ManagerStaff.USER_TYPE_AGENT);
					userInfo.setID(betOrder.getAgentStaff());
					
					ShopsInfo shopsInfo = new ShopsInfo();
					shopsInfo.setShopsCode(betOrder.getShopCode());
					userInfo.setShopsInfo(shopsInfo);
					
					replenish = this.getReplenishData(replenish, betOrder,"menuReplenish");
					agentPet_chief = BigDecimal.valueOf(agentPet).multiply(replenish.getRateChief().divide(BigDecimal.valueOf(100)));
					agent_BranchRate = replenish.getRateBranch().intValue();
					
					replenishLogic.saveReplenish(replenish, userInfo, betOrder.getPeriodsNum(), "","no","autoReplenish");
					replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(betOrder.getShopInfo().getID(), betOrder.getPlayType().split("_")[0], betOrder.getPlayType(), 
							agentPet, betOrder.getAgentStaff().intValue(),betOrder.getPeriodsNum(),"1"));
					
					//LOG.info("<--自动补货类型："+betOrder.getPlayType()+", 补货金额："+agentPet+", 补货人："+betOrder.getAgentStaff()+", 盘期："+betOrder.getPeriodsNum()+" -->");
				}else{
					agentPet=0;
				}
			}
		}
		//总代理当前实货+投注额*占成-限额+代理补出额*占成
		if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfoOrg.getUserType()) || ManagerStaff.USER_TYPE_AGENT.equals(userInfoOrg.getUserType())){
			if(betOrder.getGenAgenStaff()!=null && betOrder.getGenAgenStaff()!=0 && map.get(ManagerStaff.USER_TYPE_GEN_AGENT)!=null){
				BigDecimal totalMoney = BigDecimal.ZERO;
				if(genAgentList.size()!=0){
					totalMoney = genAgentList.get(0).getTotalMoney();
				}
				genAgentPet = totalMoney.intValue() - map.get(ManagerStaff.USER_TYPE_GEN_AGENT) + agentPet * betOrder.getGenAgenRate()/100 ;
				if(genAgentPet>0){
					Replenish replenish = new Replenish();
					replenish.setReplenishUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
					replenish.setTypeCode(betOrder.getPlayType());// 要与界面传入的type一致
					replenish.setMoney(genAgentPet);
					replenish.setPlate(betOrder.getPlate());
					replenish.setOdds(betOrder.getOdds());
					replenish.setWinAmount(BigDecimal.ZERO);
					replenish.setAttribute(betOrder.getSplitAttribute());
					
					ManagerUser userInfo = new ManagerUser();
					userInfo.setUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
					userInfo.setID(betOrder.getGenAgenStaff());
					
					ShopsInfo shopsInfo = new ShopsInfo();
					shopsInfo.setShopsCode(betOrder.getShopCode());
					userInfo.setShopsInfo(shopsInfo);
					
					replenish = this.getReplenishData(replenish, betOrder,"menuReplenish");
					genAgentPet_chief = BigDecimal.valueOf(genAgentPet).multiply(replenish.getRateChief().divide(BigDecimal.valueOf(100)));
					genAgent_BranchRate = replenish.getRateBranch().intValue();
					
					replenishLogic.saveReplenish(replenish, userInfo, betOrder.getPeriodsNum(), "","no","autoReplenish");
					replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(betOrder.getShopInfo().getID(), betOrder.getPlayType().split("_")[0], betOrder.getPlayType(), 
							genAgentPet, betOrder.getGenAgenStaff().intValue(),betOrder.getPeriodsNum(),"1"));
					
					//LOG.info("<--自动补货类型："+betOrder.getPlayType()+", 补货金额："+genAgentPet+", 补货人："+betOrder.getGenAgenStaff()+", 盘期："+betOrder.getPeriodsNum()+" -->");
				}else{
					genAgentPet=0;
				}
			}
		}
		//股东当前实货+投注额*占成-限额+代理补出额*占成+总代理补出额*占成
		if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfoOrg.getUserType()) || ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfoOrg.getUserType()) || ManagerStaff.USER_TYPE_AGENT.equals(userInfoOrg.getUserType())){
			if(betOrder.getStockholderStaff()!=null && betOrder.getStockholderStaff()!=0 && map.get(ManagerStaff.USER_TYPE_STOCKHOLDER)!=null){
				BigDecimal totalMoney = BigDecimal.ZERO;
				if(stockList.size()!=0){
					totalMoney = stockList.get(0).getTotalMoney();
				}
				stockPet = totalMoney.intValue() - map.get(ManagerStaff.USER_TYPE_STOCKHOLDER) + agentPet * betOrder.getStockHolderRate()/100  + genAgentPet * betOrder.getStockHolderRate()/100 ;
				if(stockPet>0){
					Replenish replenish = new Replenish();
					replenish.setReplenishUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
					replenish.setTypeCode(betOrder.getPlayType());// 要与界面传入的type一致
					replenish.setMoney(stockPet);
					replenish.setPlate(betOrder.getPlate());
					replenish.setOdds(betOrder.getOdds());
					replenish.setWinAmount(BigDecimal.ZERO);
					replenish.setAttribute(betOrder.getSplitAttribute());
					
					ManagerUser userInfo = new ManagerUser();
					userInfo.setUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
					userInfo.setID(betOrder.getStockholderStaff());
					
					ShopsInfo shopsInfo = new ShopsInfo();
					shopsInfo.setShopsCode(betOrder.getShopCode());
					userInfo.setShopsInfo(shopsInfo);
					
					replenish = this.getReplenishData(replenish, betOrder,"menuReplenish");
					stockPet_chief = BigDecimal.valueOf(stockPet).multiply(replenish.getRateChief().divide(BigDecimal.valueOf(100)));
					stock_BranchRate = replenish.getRateBranch().intValue();
					
					replenishLogic.saveReplenish(replenish, userInfo, betOrder.getPeriodsNum(), "","no","autoReplenish");
					replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(betOrder.getShopInfo().getID(), betOrder.getPlayType().split("_")[0], betOrder.getPlayType(), 
							stockPet, betOrder.getStockholderStaff().intValue(),betOrder.getPeriodsNum(),"1"));
					
					//LOG.info("<--自动补货类型："+betOrder.getPlayType()+", 补货金额："+stockPet+", 补货人："+betOrder.getStockholderStaff()+" -->");
				}else{
					stockPet=0;
				}     
			}
		}
		//分公司当前实货+投注额*占成-限额+代理补出额*占成+总代理补出额*占成+股东补出额*占成
		if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfoOrg.getUserType()) || ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfoOrg.getUserType()) 
				|| ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfoOrg.getUserType()) || ManagerStaff.USER_TYPE_AGENT.equals(userInfoOrg.getUserType())){
			if(betOrder.getBranchStaff()!=null && betOrder.getBranchStaff()!=0 && map.get(ManagerStaff.USER_TYPE_BRANCH)!=null){
				BigDecimal totalMoney = BigDecimal.ZERO;
				if(branchList.size()!=0){
					totalMoney = branchList.get(0).getTotalMoney();
				}
				
				Replenish replenish = new Replenish();
				replenish.setReplenishUserType(ManagerStaff.USER_TYPE_BRANCH);
				replenish.setTypeCode(betOrder.getPlayType());// 要与界面传入的type一致
				replenish.setPlate(betOrder.getPlate());
				replenish.setOdds(betOrder.getOdds());
				replenish.setWinAmount(BigDecimal.ZERO);
				replenish.setAttribute(betOrder.getSplitAttribute());
				
				ManagerUser userInfo = new ManagerUser();
				userInfo.setUserType(ManagerStaff.USER_TYPE_BRANCH);
				userInfo.setID(betOrder.getBranchStaff());
				
				ShopsInfo shopsInfo = new ShopsInfo();
				shopsInfo.setShopsCode(betOrder.getShopCode());
				userInfo.setShopsInfo(shopsInfo);
				
				replenish = this.getReplenishData(replenish, betOrder,"menuReplenish");
				
				Integer limit = map.get(ManagerStaff.USER_TYPE_BRANCH);
				
				branchPet = (totalMoney.intValue() + agentPet * agent_BranchRate/100 + genAgentPet * genAgent_BranchRate/100 + stockPet * stock_BranchRate/100) - limit;
				if(branchPet>0){	
					branchPet_chief = BigDecimal.valueOf(branchPet).multiply(replenish.getRateChief().divide(BigDecimal.valueOf(100)));
					replenish.setMoney(branchPet);
					replenishLogic.saveReplenish(replenish, userInfo, betOrder.getPeriodsNum(), "","no",Constant.AUTO_REPLENISH);
					replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(betOrder.getShopInfo().getID(), betOrder.getPlayType().split("_")[0], betOrder.getPlayType(), 
							branchPet, betOrder.getBranchStaff().intValue(),betOrder.getPeriodsNum(),"1"));
					
					//LOG.info("<--自动补货类型："+betOrder.getPlayType()+", 补货金额："+branchPet+", 补货人："+betOrder.getBranchStaff()+", 盘期："+betOrder.getPeriodsNum()+" -->");
				}else{
					branchPet=0;
				}    
			}
		}
		//补货触发的自动降赔,上面每一级补货时会把补出的货*总监占成后，存到变量里面，放到这里作为总监本次增加的实货，再进行自动降赔计算
		Integer chiefPet = (agentPet_chief.add(genAgentPet_chief).add(stockPet_chief).add(branchPet_chief)).intValue();
		replenishLogic.updateShopRealOddsForReplenish(betOrder.getShopCode(), chiefPet, betOrder.getBettingUserId(), betOrder.getPlayType(), betOrder.getPeriodsNum());
		long end = System.currentTimeMillis();
		//LOG.info("<--手动补货触发自動補貨 结束  所用时间："+(end-startTime)+" -->");
		
	}
	
	/**
	 * 自動補貨 北京  add 20121220 by Aaron
	 * 
	 */
	@Override
	public void updateReplenishAutoBJ(){
		
//		if(1==1) return;
		LOG.info("<--自動補貨 北京  start-->");
		long startTime = System.currentTimeMillis();
		
		List<Long> shopIDS = replenishAutoDao.find("select shopsID from ReplenishAuto where 1=? group by shopsID ",1);
		if(shopIDS == null || shopIDS.size()==0) return ;
		
		BJSCPeriodsInfo bjPeriodsInfo = bjscPeriodsInfoLogic.queryByPeriodsStatus(Constant.OPEN_STATUS);
		if(bjPeriodsInfo==null) return; // ==null 沒開盤
		String	periodsNum = bjPeriodsInfo.getPeriodsNum();
		
		Map<Long,String> shopsCodeMap = new HashMap<Long,String>();
		List<Criterion> criList = new ArrayList<Criterion>();
		List<ShopsInfo> shopsList = this.shopsLogic.findShopsList( criList.toArray(new Criterion[criList.size()]));
		for(ShopsInfo s:shopsList){
			shopsCodeMap.put(s.getID(), s.getShopsCode());
		}
		
		for(int i=0;i<shopIDS.size();i++)//  循环shopCode
		{
			
			Long shopID = shopIDS.get(i);
			String shopCode = shopsCodeMap.get(shopID).trim();
			
			for(int m=6;m>=3;m--)// 6 为代理、5为总代理、4为股东、3分公司  依次进行补货
			{
				//查詢開個 為打開
				List<Criterion> filters = new ArrayList<Criterion>();
				filters.add(Restrictions.eq("typeCode","BJ_BUTTON")); // 页面隐藏的  为了代码方便
				filters.add(Restrictions.eq("state","1"));
				filters.add(Restrictions.eq("shopsID",shopID));
				filters.add(Restrictions.eq("createUserType",m+""));
//				System.out.println(filters);
				List<ReplenishAuto>	autoList = listReplenishAutoList(filters.toArray(new Criterion[filters.size()]));
				if(autoList == null || autoList.size()==0) continue;
				
				for(ReplenishAuto ent: autoList)
				{
					
					filters = new ArrayList<Criterion>();
					filters.add(Restrictions.eq("shopsID",shopID));
					filters.add(Restrictions.eq("type","BJ"));
					filters.add(Restrictions.eq("state","1"));
					filters.add(Restrictions.eq("createUser",ent.getCreateUser()));
					List<ReplenishAuto> opList = listReplenishAutoList(filters.toArray(new Criterion[filters.size()]));
					ManagerStaff staff = managerStaffLogic.findManagerStaffByID(ent.getCreateUser().longValue());
					
					for(ReplenishAuto po : opList){ // 循环需要自动补货的玩法
						
						ManagerUser userInfo = new ManagerUser();
						userInfo.setUserType(po.getCreateUserType().trim()+"");
						userInfo.setID(po.getCreateUser().longValue());
						userInfo.setAccount(staff.getAccount());
						
						ShopsInfo shopsInfo = new ShopsInfo();
						shopsInfo.setShopsCode(shopCode+"");
						userInfo.setShopsInfo(shopsInfo);
						
//						if(po.getTypeCode().indexOf("BJ_1T10DS")!=-1 || po.getTypeCode().indexOf("BJ_1T10DX")!=-1 || po.getTypeCode().indexOf("BJ_1T5LH")!=-1 || po.getTypeCode().indexOf("BJ_GROUP")!=-1 || po.getTypeCode().indexOf("BJ_GROUP_DS")!=-1 || po.getTypeCode().indexOf("BJ_GROUP_DX")!=-1 || po.getTypeCode().indexOf("BJ_BALL_SECOND")!=-1 || po.getTypeCode().indexOf("BJ_BALL_FIRST")!=-1){
						if(po.getTypeCode().indexOf("BJ_1T10DS")!=-1 || po.getTypeCode().indexOf("BJ_1T10DX")!=-1 ){
							List<ReplenishVO> list = replenishLogic.findReplenishPetList_BJ(userInfo,"A",periodsNum,Constant.TRUE_STATUS);
							if(list!=null){
//								System.out.println(list.size()+"=BJ_1T10DS=");
								for(int n=1;n<=10;n++){
									for(ReplenishVO v : list)
									{
										if(v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_S")||v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_DAN")||v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_DA")||v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_X") ){
										if(v.getMoney() - po.getMoneyLimit()>=0)
										{
											Integer buMoney = v.getMoney()-po.getMoneyLimit();
											if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
												
												Replenish replenish = new Replenish();
												replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
												replenish.setMoney(buMoney);
												replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
												replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "BJSC", v.getPlayFinalType(), buMoney, po.getCreateUser(),periodsNum,"1"));
//												LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
											}
											
										}
										}
									}
								}
							}
						}else if(po.getTypeCode().indexOf("BJ_1T5LH")!=-1){
							List<ReplenishVO> list = replenishLogic.findReplenishPetList_BJ(userInfo,"A",periodsNum,Constant.TRUE_STATUS);
							if(list!=null){
								for(int n=1;n<=5;n++){
									for(ReplenishVO v : list)
									{
										if(v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_LONG")||v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_HU")){
											if(v.getMoney() - po.getMoneyLimit()>=0)
											{
												Integer buMoney = v.getMoney()-po.getMoneyLimit();
												if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
													
													Replenish replenish = new Replenish();
													replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
													replenish.setMoney(buMoney);
													replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
													replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "BJSC", v.getPlayFinalType(), buMoney, po.getCreateUser(),periodsNum,"1"));
//													LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
												}
												
											}
										}
									}
								}
							}
						}else if(po.getTypeCode().indexOf("BJ_BALL_SECOND")!=-1 || po.getTypeCode().indexOf("BJ_BALL_FIRST")!=-1){
							List<ReplenishVO> list = replenishLogic.findReplenishPetList_BJ(userInfo,"A",periodsNum,Constant.TRUE_STATUS);
							if(list!=null){
								for(ReplenishVO v : list)
								{
									if(v.getPlayFinalType().indexOf("BJ_BALL_FIRST")!=-1 ||v.getPlayFinalType().indexOf("BJ_BALL_SECOND")!=-1){
										if(v.getMoney() - po.getMoneyLimit()>=0)
										{
											Integer buMoney = v.getMoney()-po.getMoneyLimit();
											if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
												
												Replenish replenish = new Replenish();
												replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
												replenish.setMoney(buMoney);
												replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
												replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "BJSC", v.getPlayFinalType(), buMoney, po.getCreateUser(),periodsNum,"1"));
//												LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
											}
											
										}
									}
								}
							}
						}else if(po.getTypeCode().indexOf("BJ_GROUP_DX")!=-1||po.getTypeCode().indexOf("BJ_GROUP_DS")!=-1){
							List<ReplenishVO> list = replenishLogic.findReplenishPetList_BJ(userInfo,"A",periodsNum,Constant.TRUE_STATUS);
							if(list!=null){
								for(ReplenishVO v : list)
								{
									if(!v.getPlayFinalType().equals("BJ_DOUBLESIDE_DA") || !v.getPlayFinalType().equals("BJ_DOUBLESIDE_X")) continue;
									if(!v.getPlayFinalType().equals("BJ_DOUBLESIDE_DAN") || !v.getPlayFinalType().equals("BJ_DOUBLESIDE_S")) continue;
									if(v.getMoney() - po.getMoneyLimit()>=0)
									{
										Integer buMoney = v.getMoney()-po.getMoneyLimit();
										if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
											
											Replenish replenish = new Replenish();
											replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
											replenish.setMoney(buMoney);
											replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
											replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "BJSC", v.getPlayFinalType(), buMoney, po.getCreateUser(),periodsNum,"1"));
//											LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
										}
										
									}
								}
								
							}
						}else if(po.getTypeCode().indexOf("BJ_GROUP")!=-1){
							List<ReplenishVO> list = replenishLogic.findReplenishPetList_BJ(userInfo,"A",periodsNum,Constant.TRUE_STATUS);
							if(list!=null){
								for(ReplenishVO v : list)
								{
									if(v.getPlayFinalType().indexOf("BJ_GROUP")==-1) continue;
									if(v.getMoney() - po.getMoneyLimit()>=0)
									{
										Integer buMoney = v.getMoney()-po.getMoneyLimit();
										if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
											
											Replenish replenish = new Replenish();
											replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
											replenish.setMoney(buMoney);
											replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
											replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "BJSC", v.getPlayFinalType(), buMoney, po.getCreateUser(),periodsNum,"1"));
//											LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
										}
										
									}
									}
								 
							}
						}else if(po.getTypeCode().indexOf("BJ_BALL_THIRD")!=-1 || po.getTypeCode().indexOf("BJ_BALL_FORTH")!=-1 || po.getTypeCode().indexOf("BJ_BALL_FIFTH")!=-1 || po.getTypeCode().indexOf("BJ_BALL_SIXTH")!=-1){
							List<ReplenishVO> list = replenishLogic.findReplenishPetList_BJ_Other(userInfo,"A",periodsNum,Constant.TRUE_STATUS,"secondForm");
										
							if(list!=null){
								for(int n=3;n<=6;n++){
									for(ReplenishVO v : list)
									{
										if(v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_DA")||v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_X")||v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_DAN")||v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_S")||v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_LONG")||v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_HU")){
//											
										if(v.getMoney() - po.getMoneyLimit()>=0)
										{
											Integer buMoney = v.getMoney()-po.getMoneyLimit();
											if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
												
												Replenish replenish = new Replenish();
												replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
												replenish.setMoney(buMoney);
												replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
												replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "BJSC", v.getPlayFinalType(), buMoney, po.getCreateUser(),periodsNum,"1"));
//												LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
											}
											
										}
										}
									}
								}
							}
						}else if(po.getTypeCode().indexOf("BJ_BALL_SEVENTH")!=-1 || po.getTypeCode().indexOf("BJ_BALL_EIGHTH")!=-1 || po.getTypeCode().indexOf("BJ_BALL_NINTH")!=-1 || po.getTypeCode().indexOf("BJ_BALL_TENTH")!=-1){
							List<ReplenishVO> list = replenishLogic.findReplenishPetList_BJ_Other(userInfo,"A",periodsNum,Constant.TRUE_STATUS,"threeForm");
								
							if(list!=null){
								for(int n=7;n<=10;n++){
									for(ReplenishVO v : list)
									{
										if(v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_DA")||v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_X")||v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_DAN")||v.getPlayFinalType().equals("BJ_DOUBLESIDE_"+n+"_S")){
//											
										if(v.getMoney() - po.getMoneyLimit()>=0)
										{
											Integer buMoney = v.getMoney()-po.getMoneyLimit();
											if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
												
												Replenish replenish = new Replenish();
												replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
												replenish.setMoney(buMoney);
												replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
												replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "BJSC", v.getPlayFinalType(), buMoney, po.getCreateUser(),periodsNum,"1"));
//												LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
											}
											
										}
										}
									}
								}
							}
					}
					}
				}
			}
		}
		long end = System.currentTimeMillis();
		LOG.info("<--自動補貨 北京 end  所用时间："+(end-startTime)+" -->");
	}
	/**
	 * 获取除 总监外的 会员对应 上一级 用户
	 * @param userID
	 * @return
	 */
	private List<Long> getUserList(Long userID) {

		
		MemberStaffExt memberStaff = memberStaffExtLogic.findMemberStaffExtByID(userID);
	
		Long chiefStaff = memberStaff.getChiefStaff()==null?0L:memberStaff.getChiefStaff();
		Long branchStaff = memberStaff.getBranchStaff()==null?0L:memberStaff.getBranchStaff();
		Long stockholderStaff = memberStaff.getStockholderStaff()==null?0L:memberStaff.getStockholderStaff();
		Long genAgenStaff = memberStaff.getGenAgentStaff()==null?0L:memberStaff.getGenAgentStaff();
		Long agentStaff = memberStaff.getAgentStaff()==null?0L:memberStaff.getAgentStaff();
		
		List<Long> userList = Lists.newArrayList();
		userList.add(agentStaff);
		userList.add(genAgenStaff);
		userList.add(stockholderStaff);
		userList.add(branchStaff);
		
		return userList;
	}
	/**
	 * 查询要补货的 type 
	 * @param type 只能是 以下 equals 参数
	 * @return
	 */
	private List<Criterion> getFilters(String type){
		
		List<Criterion> filters = new ArrayList<Criterion>();
		if("HK_TMHZ".equals(type)){
			filters.add(Restrictions.eq("typeCode","HK_TMHZ"));
		}else if("HK_ZM".equals(type)){
			String[] str = new String[3];
			str[0] = "HK_ZM";
			str[1] = "HK_ZM_ZHDS";
			str[2] = "HK_ZM_ZHDX";
			filters.add(Restrictions.in("typeCode", str));
		}else if(type.indexOf("HK_ZT_")!=-1){
			filters.add(Restrictions.eq("typeCode",type));
		}else if(type.equals("HK_STRAIGHTTHROUGH")){
			filters.add(Restrictions.ilike("typeCode","HK_STRAIGHTTHROUGH_",MatchMode.ANYWHERE));
		}else if(type.equals("HK_SXL")){
			filters.add(Restrictions.ilike("typeCode","HK_SXL_",MatchMode.ANYWHERE));
		}else if(type.equals("HK_WSL")){
			filters.add(Restrictions.ilike("typeCode","HK_WSL_",MatchMode.ANYWHERE));
		}else if(type.equals("HK_SXWS")){
			String[] str = new String[2];
			str[0] = "HK_SX";
			str[1] = "HK_WS";
			filters.add(Restrictions.in("typeCode", str));
		}else if(type.equals("HK_WBZ")){
			filters.add(Restrictions.eq("typeCode",type));
		}else if(type.equals("HK_GG")){
			filters.add(Restrictions.eq("typeCode",type));
		}else if(type.indexOf("GDKLSF_BALL_")!=-1){
			String[] str = new String[8];
			str[0] = type;
			str[1] = "GDKLSF_1T8_DX";
			str[2] = "GDKLSF_1T8_DS";
			str[3] = "GDKLSF_1T8_WDX";
			str[4] = "GDKLSF_1T8_HSDS";
			str[5] = "GDKLSF_1T8_FW";
			str[6] = "GDKLSF_1T8_ZFB_ZF";
			str[7] = "GDKLSF_1T8_ZFB_B";
			filters.add(Restrictions.in("typeCode", str));
		}else if(type.equals("GDKLSF_ZHLH")){
			String[] str = new String[4];
			str[0] = "GDKLSF_ZHDX";
			str[1] = "GDKLSF_ZHDS";
			str[2] = "GDKLSF_ZHWSDX";
			str[3] = "GDKLSF_DOUBLESIDE_LH";
			filters.add(Restrictions.in("typeCode", str));
		}else if(type.equals("GDKLSF_STRAIGHTTHROUGH")){
			filters.add(Restrictions.ilike("typeCode", "GDKLSF_STRAIGHTTHROUGH_",MatchMode.ANYWHERE));
		}
		
		
		
		return filters;
	}
	/**
	 * 自動補貨 GD
	 * 
	 */
	@Override
	public void updateReplenishAutoGD()
	{
		long startTime = System.currentTimeMillis();
		
		List<Long> shopIDS = replenishAutoDao.find("select shopsID from ReplenishAuto where 1=? group by shopsID ",1);
		if(shopIDS == null || shopIDS.size()==0) return ;
		
		GDPeriodsInfo gdPeriodsInfo = periodsInfoLogic.queryByPeriodsStatus(Constant.OPEN_STATUS);
		if(gdPeriodsInfo==null) return; // ==null 當前shop 沒開盤
		String	periodsNum = gdPeriodsInfo.getPeriodsNum();
		Map<Long,String> shopsCodeMap = new HashMap<Long,String>();
		List<Criterion> criList = new ArrayList<Criterion>();
		List<ShopsInfo> shopsList = this.shopsLogic.findShopsList( criList.toArray(new Criterion[criList.size()]));
		for(ShopsInfo s:shopsList){
			shopsCodeMap.put(s.getID(), s.getShopsCode());
		}
		
		for(int i=0;i<shopIDS.size();i++)//  循环shopCode
		{
		
			Long shopID = shopIDS.get(i);
			String shopCode = shopsCodeMap.get(shopID).trim();
			
			for(int m=6;m>=3;m--)// 6 为代理、5为总代理、4为股东、3分公司  依次进行补货
			{
				//查詢開個 為打開
				List<Criterion> filters = new ArrayList<Criterion>();
				filters.add(Restrictions.eq("typeCode","GDKLSF_BUTTON"));
				filters.add(Restrictions.eq("state","1"));
				filters.add(Restrictions.eq("shopsID",shopID));
				filters.add(Restrictions.eq("createUserType",m+""));
//				System.out.println(filters);
				List<ReplenishAuto>	autoList = listReplenishAutoList(filters.toArray(new Criterion[filters.size()]));
				if(autoList == null || autoList.size()==0) continue;
				
				for(ReplenishAuto ent: autoList){
					
					filters = new ArrayList<Criterion>();
					filters.add(Restrictions.eq("shopsID",shopID));
					filters.add(Restrictions.eq("type","GDKLSF"));
					filters.add(Restrictions.eq("state","1"));
					filters.add(Restrictions.eq("createUser",ent.getCreateUser()));
					List<ReplenishAuto> opList = listReplenishAutoList(filters.toArray(new Criterion[filters.size()]));
					ManagerStaff staff = managerStaffLogic.findManagerStaffByID(ent.getCreateUser().longValue());
					
					for(ReplenishAuto po : opList){ // 循环需要自动补货的玩法
						
						ManagerUser userInfo = new ManagerUser();
						userInfo.setUserType(po.getCreateUserType().trim()+"");
						userInfo.setID(po.getCreateUser().longValue());
						userInfo.setAccount(staff.getAccount());
						
						ShopsInfo shopsInfo = new ShopsInfo();
						shopsInfo.setShopsCode(shopCode+"");
						userInfo.setShopsInfo(shopsInfo);
						
						if(po.getTypeCode().indexOf("GDKLSF_BALL_")!=-1){
							
							String numEN = po.getTypeCode().split("_")[2];
							Integer num = 0;
							
							Map<Integer,String> map = GDKLSFRule.getNumberMatchEN();
							for(Map.Entry<Integer,String> entry : map.entrySet())   
							{
								if(numEN.equals(entry.getValue())) num = entry.getKey();
							}
							
							List<ReplenishVO> list = replenishLogic.findReplenishPetList(Constant.GDKLSF_TABLE_LIST[num-1], userInfo, "A", periodsNum,Constant.TRUE_STATUS,num+"",numEN);
//		    				 System.out.println(0+" "+Constant.GDKLSF_TABLE_LIST[0]+" "+userInfo+" "+"A"+" "+ periodsNum+" "+Constant.TRUE_STATUS+" "+num+numEN+""+"=====================5555555555==================");
							if(list!=null){
								for(ReplenishVO v : list)
								{
									if(v.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_"+num+"_DA") || v.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_"+num+"_X")) continue;
									if(v.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_"+num+"_DAN") || v.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_"+num+"_S")) continue;
									if(v.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_"+num+"_WD") || v.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_"+num+"_WX")) continue;
									if(v.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_"+num+"_HSD") || v.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_"+num+"_HSS")) continue;
									if(v.getPlayFinalType().equals("GDKLSF_BALL_"+numEN+"_DONG") || v.getPlayFinalType().equals("GDKLSF_BALL_"+numEN+"_NAN")|| v.getPlayFinalType().equals("GDKLSF_BALL_"+numEN+"_XI")|| v.getPlayFinalType().equals("GDKLSF_BALL_"+numEN+"_BEI")) continue;
									if(v.getPlayFinalType().equals("GDKLSF_BALL_"+numEN+"_Z") || v.getPlayFinalType().equals("GDKLSF_BALL_"+numEN+"_F")|| v.getPlayFinalType().equals("GDKLSF_BALL_"+numEN+"_B")) continue;
									if(v.getMoney() - po.getMoneyLimit()>=0)
									{
										Integer buMoney = v.getMoney()-po.getMoneyLimit();
										if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
											
											Replenish replenish = new Replenish();
											replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
											replenish.setMoney(buMoney);
											replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
											replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "GDKLSF", v.getPlayFinalType(), buMoney, po.getCreateUser(),periodsNum,"1"));
//											LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
										}
										
									}
								}
							}
							
						}else if(po.getTypeCode().equals("GDKLSF_1T8_DX") || po.getTypeCode().equals("GDKLSF_1T8_DS") ||po.getTypeCode().equals("GDKLSF_1T8_WDX")||po.getTypeCode().equals("GDKLSF_1T8_HSDS") ){
							
							String[] str = getGDKLSFMntissa(po.getTypeCode());
							
							Map<Integer,String> map = GDKLSFRule.getNumberMatchEN();
							for(Map.Entry<Integer,String> entry : map.entrySet())   
							{
								List<ReplenishVO> list = replenishLogic.findReplenishPetList(Constant.GDKLSF_TABLE_LIST[0], userInfo, "A", periodsNum,Constant.TRUE_STATUS,entry.getKey()+"",entry.getValue());
								for(ReplenishVO v : list)
								{
									if(v.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_"+entry.getKey()+"_"+str[0]+"") || v.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_"+entry.getKey()+"_"+str[1]+"")){
										if(v.getMoney() - po.getMoneyLimit()>=0)
										{
											Integer buMoney = v.getMoney()-po.getMoneyLimit();
											if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
												
												Replenish replenish = new Replenish();
												replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
												replenish.setMoney(buMoney);
												replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
												replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "GDKLSF", v.getPlayFinalType(), buMoney, po.getCreateUser(), periodsNum,"1"));
//												LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
											}
											
										}
									}
								}
							}
						}else if(po.getTypeCode().equals("GDKLSF_1T8_FW")){
							
							Map<Integer,String> map = GDKLSFRule.getNumberMatchEN();
							for(Map.Entry<Integer,String> entry : map.entrySet())   
							{
								List<ReplenishVO> list = replenishLogic.findReplenishPetList(Constant.GDKLSF_TABLE_LIST[0], userInfo, "A", periodsNum,Constant.TRUE_STATUS,entry.getKey()+"",entry.getValue());
								for(ReplenishVO v : list)
								{
									if(v.getPlayFinalType().equals("GDKLSF_BALL_"+entry.getValue()+"_DOND") || v.getPlayFinalType().equals("GDKLSF_BALL_"+entry.getValue()+"_NAN")|| v.getPlayFinalType().equals("GDKLSF_BALL_"+entry.getValue()+"_XI")|| v.getPlayFinalType().equals("GDKLSF_BALL_"+entry.getValue()+"_BEI")){
										if(v.getMoney() - po.getMoneyLimit()>=0)
										{
											Integer buMoney = v.getMoney()-po.getMoneyLimit();
											if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
												
												Replenish replenish = new Replenish();
												replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
												replenish.setMoney(buMoney);
												replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
												replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "GDKLSF", v.getPlayFinalType(), buMoney, po.getCreateUser(), periodsNum,"1"));
//												LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
											}
											
										}
									}
								}
							}
						}else if(po.getTypeCode().equals("GDKLSF_1T8_ZFB_ZF")){
							
							Map<Integer,String> map = GDKLSFRule.getNumberMatchEN();
							for(Map.Entry<Integer,String> entry : map.entrySet())   
							{
								List<ReplenishVO> list = replenishLogic.findReplenishPetList(Constant.GDKLSF_TABLE_LIST[0], userInfo, "A", periodsNum,Constant.TRUE_STATUS,entry.getKey()+"",entry.getValue());
								for(ReplenishVO v : list)
								{
									if(v.getPlayFinalType().equals("GDKLSF_BALL_"+entry.getValue()+"_Z") || v.getPlayFinalType().equals("GDKLSF_BALL_"+entry.getValue()+"_F")){
										if(v.getMoney() - po.getMoneyLimit()>=0)
										{
											Integer buMoney = v.getMoney()-po.getMoneyLimit();
											if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
												
												Replenish replenish = new Replenish();
												replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
												replenish.setMoney(buMoney);
												replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
												replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "GDKLSF", v.getPlayFinalType(), buMoney, po.getCreateUser(), periodsNum,"1"));
//												LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
											}
											
										}
									}
								}
							}
						}else if(po.getTypeCode().equals("GDKLSF_1T8_ZFB_B")){
							
							Map<Integer,String> map = GDKLSFRule.getNumberMatchEN();
							for(Map.Entry<Integer,String> entry : map.entrySet())   
							{
								List<ReplenishVO> list = replenishLogic.findReplenishPetList(Constant.GDKLSF_TABLE_LIST[0], userInfo, "A", periodsNum,Constant.TRUE_STATUS,entry.getKey()+"",entry.getValue());
								for(ReplenishVO v : list)
								{
									if(v.getPlayFinalType().equals("GDKLSF_BALL_"+entry.getValue()+"_B")){
										if(v.getMoney() - po.getMoneyLimit()>=0)
										{
											Integer buMoney = v.getMoney()-po.getMoneyLimit();
											if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
												
												Replenish replenish = new Replenish();
												replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
												replenish.setMoney(buMoney);
												replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
												replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "GDKLSF", v.getPlayFinalType(), buMoney, po.getCreateUser(), periodsNum,"1"));
//												LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
											}
											
										}
									}
								}
							}
						}else if(po.getTypeCode().equals("GDKLSF_ZHDX")||po.getTypeCode().equals("GDKLSF_ZHDS")||po.getTypeCode().equals("GDKLSF_ZHWSDX")||po.getTypeCode().equals("GDKLSF_DOUBLESIDE_LH")){
							
							String[] str = getGDKLSFMntissa(po.getTypeCode()); 
							List<ReplenishVO> list = replenishLogic.findReplenishPetListForLh(Constant.GDKLSF_DOUBLESIDE_TABLE_NAME, userInfo, "A", periodsNum,Constant.TRUE_STATUS);
							for(ReplenishVO v : list)
							{
								if(v.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_"+str[0]+"")||v.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_"+str[1]+"")){
									if(v.getMoney() - po.getMoneyLimit()>=0)
									{
										Integer buMoney = v.getMoney()-po.getMoneyLimit();
										if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
											
											Replenish replenish = new Replenish();
											replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
											replenish.setMoney(buMoney);
											replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
											replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "GDKLSF", v.getPlayFinalType(), buMoney, po.getCreateUser(), periodsNum,"1"));
//											LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+"  -->");
										}
										
									}
								}
							}
							
						}else if(po.getTypeCode().indexOf("GDKLSF_STRAIGHTTHROUGH_")!=-1){
							
							List<ReplenishVO> list = replenishLogic.queryReplenish_LM(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME, userInfo, "A", periodsNum, Constant.TRUE_STATUS, po.getTypeCode());
							for(ReplenishVO v : list)
							{
								if(v.getMoney() - po.getMoneyLimit()>=0)
								{
									Integer buMoney = v.getMoney()-po.getMoneyLimit();
									if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
										
										Replenish replenish = new Replenish();
										replenish.setTypeCode(po.getTypeCode());// 要与界面传入的type一致
										replenish.setMoney(buMoney);
										replenish.setAttribute(v.getAttribute());
										replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
										replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "GDKLSF", po.getTypeCode(), buMoney, po.getCreateUser(), periodsNum,"1"));
//										LOG.info("<--自动补货类型："+po.getTypeCode()+"/"+v.getAttribute()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
									}
									
								}
							}
							
						}
						
						
					}
				}
			}
		}
		long end = System.currentTimeMillis();
		LOG.info("<--自動補貨 广东  所用时间："+(end-startTime)+" -->");
		
	}
	public String[] getGDKLSFMntissa(String type)
	{
		String str[] = new String[2];
		if(type.equals("GDKLSF_1T8_DX")){
			str[0] = "DA";	
			str[1] = "X";	
		}else if(type.equals("GDKLSF_1T8_DS")){
			str[0] = "DAN";	
			str[1] = "S";	
		}else if(type.equals("GDKLSF_1T8_WDX")){
			str[0] = "WD";	
			str[1] = "WX";	
		}else if(type.equals("GDKLSF_1T8_HSDS")){
			str[0] = "HSD";	
			str[1] = "HSS";	
		}else if(type.equals("GDKLSF_ZHDX")){
			str[0] = "ZHDA";	
			str[1] = "ZHX";	
		}else if(type.equals("GDKLSF_ZHDS")){
			str[0] = "ZHDAN";	
			str[1] = "ZHS";	
		}else if(type.equals("GDKLSF_ZHWSDX")){
			str[0] = "ZHWD";	
			str[1] = "ZHWX";	
		}else if(type.equals("GDKLSF_DOUBLESIDE_LH")){
			str[0] = "LONG";	
			str[1] = "HU";	
		}else if(type.equals("CQSSC_1T5_DX")){
			str[0] = "DA";	
			str[1] = "X";	
		}else if(type.equals("CQSSC_1T5_DS")){
			str[0] = "DAN";	
			str[1] = "S";	
		}
		
		
		return str;
	}
	/**
	 * 自動補貨 CQ
	 * 
	 */
	public void updateReplenishAutoCQ()
	{
		long startTime = System.currentTimeMillis();
		
		List<Long> shopsID = replenishAutoDao.find("select shopsID from ReplenishAuto where 1=? group by shopsID",1);
		if(shopsID == null || shopsID.size()==0) return ;
		
		CQPeriodsInfo runningPeriods = icqPeriodsInfoLogic.queryByPeriodsStatus(Constant.OPEN_STATUS);
		if(runningPeriods==null) return; // ==null 當前shop 沒開盤
		String	periodsNum = runningPeriods.getPeriodsNum();

		Map<Long,String> shopsCodeMap = new HashMap<Long,String>();
		List<Criterion> criList = new ArrayList<Criterion>();
		List<ShopsInfo> shopsList = this.shopsLogic.findShopsList( criList.toArray(new Criterion[criList.size()]));
		for(ShopsInfo s:shopsList){
			shopsCodeMap.put(s.getID(), s.getShopsCode());
		}
		
		for(int i=0;i<shopsID.size();i++)//  循环shopCode
		{
			
			Long shopID = shopsID.get(i);
			String shopCode = shopsCodeMap.get(shopID).trim();
			
			for(int m=6;m>=3;m--)// 6 为代理、5为总代理、4为股东、3分公司  依次进行补货
			{
				//查詢開個 為打開
				List<Criterion> filters = new ArrayList<Criterion>();
				filters.add(Restrictions.eq("typeCode","CQSSC_BUTTON"));
				filters.add(Restrictions.eq("state","1"));
				filters.add(Restrictions.eq("shopsID",shopID));
				filters.add(Restrictions.eq("createUserType",m+""));
//				System.out.println(filters);
				List<ReplenishAuto>	autoList = listReplenishAutoList(filters.toArray(new Criterion[filters.size()]));
				if(autoList == null || autoList.size()==0) continue;
		    	for(ReplenishAuto ent: autoList){
		    		
		    		 filters = new ArrayList<Criterion>();
		    		 filters.add(Restrictions.eq("shopsID",shopID));
		    		 filters.add(Restrictions.eq("type","CQSSC"));
		    		 filters.add(Restrictions.eq("state","1"));
		    		 filters.add(Restrictions.eq("createUser",ent.getCreateUser()));
		    		 List<ReplenishAuto> opList = listReplenishAutoList(filters.toArray(new Criterion[filters.size()]));
		    		 ManagerStaff staff = managerStaffLogic.findManagerStaffByID(ent.getCreateUser().longValue());
						
		    		 for(ReplenishAuto po : opList){ // 循环需要自动补货的玩法
		    			 
		    			 ManagerUser userInfo = new ManagerUser();
		    			 userInfo.setUserType(po.getCreateUserType().trim()+"");
		    			 userInfo.setID(po.getCreateUser().longValue());
		    			 userInfo.setAccount(staff.getAccount());
		    			 
		    			 ShopsInfo shopsInfo = new ShopsInfo();
		    			 shopsInfo.setShopsCode(shopCode);
		    			 userInfo.setShopsInfo(shopsInfo);
		    				 
		    			 if(po.getTypeCode().indexOf("CQSSC_BALL_")!=-1){
		    			 
		    				 String numEN = po.getTypeCode().split("_")[2];
		    				 List<ReplenishVO> list = replenishLogic.findReplenishPetList_CQ(userInfo, "A", periodsNum,Constant.TRUE_STATUS);
		    				 for(ReplenishVO v : list)
		    				 {
		    					 if(v.getPlayFinalType().indexOf("CQSSC_BALL_"+numEN+"")!=-1)
		    					 {
			    					 if(v.getMoney() - po.getMoneyLimit()>=0)
		    						 {
		    							 Integer buMoney = v.getMoney()-po.getMoneyLimit();
		    							 if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
		    								 
		    								 Replenish replenish = new Replenish();
		    								 replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
		    								 replenish.setMoney(buMoney);
		    								 replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
		    								 replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "CQSSC", v.getPlayFinalType(), buMoney, po.getCreateUser(), periodsNum,"1"));
//		    								 LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
		    							 }
		    							 
		    						 }
		    					 }
		    				 }
		    			 }else  if(po.getTypeCode().equals("CQSSC_1T5_DX")||po.getTypeCode().equals("CQSSC_1T5_DS")){
		    			 
		    				 String[] str = getGDKLSFMntissa(po.getTypeCode());
		    				 List<ReplenishVO> list = replenishLogic.findReplenishPetList_CQ(userInfo, "A", periodsNum,Constant.TRUE_STATUS);
		    				 for(ReplenishVO v : list)
		    				 {
		    					 if( v.getPlayFinalType().endsWith("_"+str[0]) || v.getPlayFinalType().endsWith("_"+str[1]))
		    					 {
			    					 if(v.getMoney() - po.getMoneyLimit()>=0)
		    						 {
		    							 Integer buMoney = v.getMoney()-po.getMoneyLimit();
		    							 if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
		    								 
		    								 Replenish replenish = new Replenish();
		    								 replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
		    								 replenish.setMoney(buMoney);
		    								 replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
		    								 replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "CQSSC", v.getPlayFinalType(), buMoney, po.getCreateUser(), periodsNum,"1"));
//		    								 LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
		    							 }
		    							 
		    						 }
		    					 }
		    				 }
		    			 } else if(po.getTypeCode().equals("CQSSC_ZHDS")){
		    			 
		    				 List<ReplenishVO> list = replenishLogic.findReplenishPetList_CQ(userInfo, "A", periodsNum,Constant.TRUE_STATUS);
		    				 for(ReplenishVO v : list)
		    				 {
		    					 if( v.getPlayFinalType().equals("CQSSC_DOUBLESIDE_ZHDAN") || v.getPlayFinalType().equals("CQSSC_DOUBLESIDE_ZHS"))
		    					 {
			    					 if(v.getMoney() - po.getMoneyLimit()>=0)
		    						 {
		    							 Integer buMoney = v.getMoney()-po.getMoneyLimit();
		    							 if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
		    								 
		    								 Replenish replenish = new Replenish();
		    								 replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
		    								 replenish.setMoney(buMoney);
		    								 replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
		    								 replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "CQSSC", v.getPlayFinalType(), buMoney, po.getCreateUser(), periodsNum,"1"));
//		    								 LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
		    							 }
		    							 
		    						 }
		    					 }
		    				 }
		    			 }else if(po.getTypeCode().equals("CQSSC_ZHDX")){
		    				 
		    				 List<ReplenishVO> list = replenishLogic.findReplenishPetList_CQ(userInfo, "A", periodsNum,Constant.TRUE_STATUS);
		    				 for(ReplenishVO v : list)
		    				 {
		    					 if( v.getPlayFinalType().equals("CQSSC_DOUBLESIDE_ZHDA") || v.getPlayFinalType().equals("CQSSC_DOUBLESIDE_ZHX"))
		    					 {
		    						 if(v.getMoney() - po.getMoneyLimit()>=0)
		    						 {
		    							 Integer buMoney = v.getMoney()-po.getMoneyLimit();
		    							 if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
		    								 
		    								 Replenish replenish = new Replenish();
		    								 replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
		    								 replenish.setMoney(buMoney);
		    								 replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
		    								 replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "CQSSC", v.getPlayFinalType(), buMoney, po.getCreateUser(), periodsNum,"1"));
//		    								 LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
		    							 }
		    							 
		    						 }
		    					 }
		    				 }
		    			 }else if(po.getTypeCode().equals("CQSSC_LHH")){
		    			 
		    				 List<ReplenishVO> list = replenishLogic.findReplenishPetList_CQ(userInfo, "A", periodsNum,Constant.TRUE_STATUS);
		    				 for(ReplenishVO v : list)
		    				 {
		    					 if( v.getPlayFinalType().equals("CQSSC_DOUBLESIDE_LONG") || v.getPlayFinalType().equals("CQSSC_DOUBLESIDE_HU")|| v.getPlayFinalType().equals("CQSSC_DOUBLESIDE_HE"))
		    					 {
			    					 if(v.getMoney() - po.getMoneyLimit()>=0)
		    						 {
		    							 Integer buMoney = v.getMoney()-po.getMoneyLimit();
		    							 if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
		    								 
		    								 Replenish replenish = new Replenish();
		    								 replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
		    								 replenish.setMoney(buMoney);
		    								 replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
		    								 replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "CQSSC", v.getPlayFinalType(), buMoney, po.getCreateUser(), periodsNum,"1"));
//		    								 LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
		    							 }
		    							 
		    						 }
		    					 }
		    				 }
		    			 }else if(po.getTypeCode().indexOf("CQSSC_THREE_")!=-1){
		    			 
		    				 String str = po.getTypeCode().split("_")[2];
		    				 List<ReplenishVO> list = replenishLogic.findReplenishPetList_CQ(userInfo, "A", periodsNum,Constant.TRUE_STATUS);
		    				 for(ReplenishVO v : list)
		    				 {
		    					 if( v.getPlayFinalType().equals("CQSSC_BZ_"+str+"") || v.getPlayFinalType().equals("CQSSC_SZ_"+str+"")|| v.getPlayFinalType().equals("CQSSC_DZ_"+str+"")|| v.getPlayFinalType().equals("CQSSC_BS_"+str+"")|| v.getPlayFinalType().equals("CQSSC_ZL_"+str+""))
		    					 {
			    					 if(v.getMoney() - po.getMoneyLimit()>=0)
		    						 {
		    							 Integer buMoney = v.getMoney()-po.getMoneyLimit();
		    							 if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
		    								 
		    								 Replenish replenish = new Replenish();
		    								 replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
		    								 replenish.setMoney(buMoney);
		    								 replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
		    								 replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "CQSSC", v.getPlayFinalType(), buMoney, po.getCreateUser(), periodsNum,"1"));
//		    								 LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
		    							 }
		    							 
		    						 }
		    					 }
		    				 }
		    			 }
		    			 
		    		 }
		    	}
	    	}
		}
		long end = System.currentTimeMillis();
		LOG.info("<--自動補貨 重庆  所用时间："+(end-startTime)+" -->");
		
	}
	
//********************触发自动补货START*************************	
	/**
	 * 自動補貨 GD
	 * 这里userInfoFrom的作用是取商铺号和商铺Id
	 */
	@Override
	public void updateReplenishAutoGDMenu(String periodsNum,String typeCode,ShopsInfo shopsInfo,String plate)
	{
		LOG.info("<--自動補貨 广东  start-->");
		long startTime = System.currentTimeMillis();
		
		Long shopID = shopsInfo.getID();
		
		    //自动补货里面的typeCode,这里要根据传过来的玩法类型与自动补货的typeCode对应;
			String autoTypeCode = PlayTypeUtils.getAutoReplenishType(typeCode);
			
			for(int m=6;m>=3;m--)// 6 为代理、5为总代理、4为股东、3分公司  依次进行补货
			{
				//查詢開個 為打開
				List<Criterion> filters = new ArrayList<Criterion>();
				filters.add(Restrictions.eq("typeCode","GDKLSF_BUTTON"));
				filters.add(Restrictions.eq("state","1"));
				filters.add(Restrictions.eq("shopsID",shopID));
				filters.add(Restrictions.eq("createUserType",m+""));
				List<ReplenishAuto>	autoList = listReplenishAutoList(filters.toArray(new Criterion[filters.size()]));
				if(autoList == null || autoList.size()==0) continue;
				
				for(ReplenishAuto ent: autoList){
					
					filters = new ArrayList<Criterion>();
					filters.add(Restrictions.eq("shopsID",shopID));
					filters.add(Restrictions.eq("type","GDKLSF"));
					filters.add(Restrictions.eq("state","1"));
					filters.add(Restrictions.eq("createUser",ent.getCreateUser()));
					//这里加上需要操作的自动补货类弄条件
					filters.add(Restrictions.eq("typeCode",autoTypeCode));
					List<ReplenishAuto> opList = listReplenishAutoList(filters.toArray(new Criterion[filters.size()]));
					ManagerStaff staff = managerStaffLogic.findManagerStaffByID(ent.getCreateUser().longValue());
					
					if(opList == null || opList.size()==0) continue;
					ReplenishAuto po = opList.get(0);//需要自动补货的玩法
						
						ManagerUser userInfo = new ManagerUser();
						userInfo.setUserType(po.getCreateUserType().trim()+"");
						userInfo.setID(po.getCreateUser().longValue());
						userInfo.setAccount(staff.getAccount());
						
						userInfo.setShopsInfo(shopsInfo);
						List<ReplenishVO> list = new ArrayList<ReplenishVO>();
						String ballNum = "";
						String preName = "";
						if(po.getTypeCode().indexOf("GDKLSF_BALL_")!=-1 || po.getTypeCode().indexOf("1T8")!=-1){
							PlayType playType = PlayTypeUtils.getPlayType(typeCode);
							String playSubType = playType.getPlaySubType();//获取subType,用于判断双面要查哪张表
							if(playSubType.indexOf("FIRST")!=-1){ballNum="1"; preName="FIRST";}else 
							if(playSubType.indexOf("SECOND")!=-1){ballNum="2"; preName="SECOND";}else 
							if(playSubType.indexOf("THIRD")!=-1){ballNum="3"; preName="THIRD";}else  
							if(playSubType.indexOf("FORTH")!=-1){ballNum="4"; preName="FORTH";}else 
							if(playSubType.indexOf("FIFTH")!=-1){ballNum="5"; preName="FIFTH";}else  
							if(playSubType.indexOf("SIXTH")!=-1){ballNum="6"; preName="SIXTH";}else  
							if(playSubType.indexOf("SEVENTH")!=-1){ballNum="7"; preName="SEVENTH";}else 
							if(playSubType.indexOf("EIGHTH")!=-1){ballNum="8"; preName="EIGHTH";} 
							list = replenishLogic.findReplenishPetList("TB_GDKLSF_"+playSubType, userInfo, plate, periodsNum,Constant.TRUE_STATUS,ballNum,preName);
						
						}else if(po.getTypeCode().equals("GDKLSF_ZHDX")||po.getTypeCode().equals("GDKLSF_ZHDS")||po.getTypeCode().equals("GDKLSF_ZHWSDX")||po.getTypeCode().equals("GDKLSF_DOUBLESIDE_LH")){
							list = replenishLogic.findReplenishPetListForLh(Constant.GDKLSF_DOUBLESIDE_TABLE_NAME, userInfo, "A", periodsNum,Constant.TRUE_STATUS);
							
						}else if(po.getTypeCode().indexOf("GDKLSF_STRAIGHTTHROUGH_")!=-1){
							list = replenishLogic.queryReplenish_LM(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME, userInfo, "A", periodsNum, Constant.TRUE_STATUS, po.getTypeCode());
						
						}
						for(ReplenishVO v : list)
						{
							if(typeCode.equals(v.getPlayFinalType())){
								if(v.getMoney() - po.getMoneyLimit()>=0)
								{
									Integer buMoney = v.getMoney()-po.getMoneyLimit();
									if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
										
										Replenish replenish = new Replenish();
										replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
										replenish.setMoney(buMoney);
										replenish.setPlate(plate);
										try {
											replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
											replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "GDKLSF", v.getPlayFinalType(), buMoney, po.getCreateUser(), periodsNum,"1"));
										} catch (Exception e) {
											LOG.info("自动补货异常!" + e.getMessage());
										}
										LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+"  -->");
									}
									
								}
							}
						}
						
				}
			}
		//}
		long end = System.currentTimeMillis();
		LOG.info("<--自動補貨 广东  所用时间："+(end-startTime)+" -->");
		
	}	
	
	
	//重庆
	@Override
	public void updateReplenishAutoCQMenu(String periodsNum,String typeCode,ShopsInfo shopsInfo,String plate)
	{
		LOG.info("<--自動補貨 重庆  start-->");
		long startTime = System.currentTimeMillis();
		Long shopID = shopsInfo.getID();
		    //自动补货里面的typeCode,这里要根据传过来的玩法类型与自动补货的typeCode对应;
			String autoTypeCode = PlayTypeUtils.getAutoReplenishType(typeCode);
			for(int m=6;m>=3;m--)// 6 为代理、5为总代理、4为股东、3分公司  依次进行补货
			{
				//查詢開個 為打開
				List<Criterion> filters = new ArrayList<Criterion>();
				filters.add(Restrictions.eq("typeCode","CQSSC_BUTTON"));
				filters.add(Restrictions.eq("state","1"));
				filters.add(Restrictions.eq("shopsID",shopID));
				filters.add(Restrictions.eq("createUserType",m+""));
				List<ReplenishAuto>	autoList = new ArrayList<ReplenishAuto>();
				try {
					autoList = listReplenishAutoList(filters.toArray(new Criterion[filters.size()]));
					
				} catch (Exception e) {
					LOG.info("~~~~~~~~~~~自动补货调用出错！" + e.getMessage());
				}
				if(autoList == null || autoList.size()==0) continue;
		    	for(ReplenishAuto ent: autoList){
		    		
		    		 filters = new ArrayList<Criterion>();
		    		 filters.add(Restrictions.eq("shopsID",shopID));
		    		 filters.add(Restrictions.eq("type","CQSSC"));
		    		 filters.add(Restrictions.eq("state","1"));
		    		 filters.add(Restrictions.eq("createUser",ent.getCreateUser()));
		    		//这里加上需要操作的自动补货类弄条件
					 filters.add(Restrictions.eq("typeCode",autoTypeCode));
		    		 List<ReplenishAuto> opList = listReplenishAutoList(filters.toArray(new Criterion[filters.size()]));
		    		 ManagerStaff staff = managerStaffLogic.findManagerStaffByID(ent.getCreateUser().longValue());
					
		    		 if(opList == null || opList.size()==0) continue;
		    		 ReplenishAuto po = opList.get(0);//需要自动补货的玩法 
		    			 ManagerUser userInfo = new ManagerUser();
		    			 userInfo.setUserType(po.getCreateUserType().trim()+"");
		    			 userInfo.setID(po.getCreateUser().longValue());
		    			 userInfo.setAccount(staff.getAccount());
		    			 
		    			 userInfo.setShopsInfo(shopsInfo);
		    			 
		    			 List<ReplenishVO> list = replenishLogic.findReplenishPetList_CQ(userInfo, plate, periodsNum,Constant.TRUE_STATUS);
		    			 if(list!=null){
			    			 for(ReplenishVO v : list)
		    				 {
		    					 if(typeCode.equals(v.getPlayFinalType()))
		    					 {
			    					 if(v.getMoney() - po.getMoneyLimit()>=0)
		    						 {
		    							 Integer buMoney = v.getMoney()-po.getMoneyLimit();
		    							 if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
		    								 
		    								 Replenish replenish = new Replenish();
		    								 replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
		    								 replenish.setMoney(buMoney);
		    								 replenish.setPlate(plate);
		    								 replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
		    								 replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "CQSSC", v.getPlayFinalType(), buMoney, po.getCreateUser(), periodsNum,"1"));
		    								 LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
		    								
		    							 }
		    							 
		    						 }
		    					 }
		    				 }
		    			 }
		    	}
	    	}
		long end = System.currentTimeMillis();
		LOG.info("<--自動補貨 重庆  所用时间："+(end-startTime)+" -->");
		//执行自动补货
		/*if(typeCode.indexOf(Constant.LOTTERY_TYPE_GDKLSF)!=-1){
			updateReplenishAutoGDMenu(periodsNum, typeCode, shopsInfo, plate);
		}else if(typeCode.indexOf(Constant.LOTTERY_TYPE_CQSSC)!=-1){
			updateReplenishAutoCQMenu(periodsNum, typeCode, shopsInfo, plate);
		}else if(typeCode.indexOf(Constant.LOTTERY_TYPE_BJ)!=-1){
			updateReplenishAutoBJMenu(periodsNum, typeCode, shopsInfo, plate);
		}*/
	}	
	
	
	//北京
	@Override
	public void updateReplenishAutoBJMenu(String periodsNum,String typeCode,ShopsInfo shopsInfo,String plate){
		
		LOG.info("<--自動補貨 北京  start-->");
		long startTime = System.currentTimeMillis();
		Long shopID = shopsInfo.getID();
		    //自动补货里面的typeCode,这里要根据传过来的玩法类型与自动补货的typeCode对应;
			String autoTypeCode = PlayTypeUtils.getAutoReplenishType(typeCode);
			for(int m=6;m>=3;m--)// 6 为代理、5为总代理、4为股东、3分公司  依次进行补货
			{
				//查詢開個 為打開
				List<Criterion> filters = new ArrayList<Criterion>();
				filters.add(Restrictions.eq("typeCode","BJ_BUTTON")); // 页面隐藏的  为了代码方便
				filters.add(Restrictions.eq("state","1"));
				filters.add(Restrictions.eq("shopsID",shopID));
				filters.add(Restrictions.eq("createUserType",m+""));
//				System.out.println(filters);
				List<ReplenishAuto>	autoList = listReplenishAutoList(filters.toArray(new Criterion[filters.size()]));
				if(autoList == null || autoList.size()==0) continue;
				
				for(ReplenishAuto ent: autoList)
				{
					
					filters = new ArrayList<Criterion>();
					filters.add(Restrictions.eq("shopsID",shopID));
					filters.add(Restrictions.eq("type","BJ"));
					filters.add(Restrictions.eq("state","1"));
					filters.add(Restrictions.eq("createUser",ent.getCreateUser()));
					//这里加上需要操作的自动补货类弄条件
					filters.add(Restrictions.eq("typeCode",autoTypeCode));
					List<ReplenishAuto> opList = listReplenishAutoList(filters.toArray(new Criterion[filters.size()]));
					ManagerStaff staff = managerStaffLogic.findManagerStaffByID(ent.getCreateUser().longValue());
					
					if(opList == null || opList.size()==0) continue;
					ReplenishAuto po = opList.get(0);//需要自动补货的玩法 
						
						ManagerUser userInfo = new ManagerUser();
						userInfo.setUserType(po.getCreateUserType().trim()+"");
						userInfo.setID(po.getCreateUser().longValue());
						userInfo.setAccount(staff.getAccount());
						
						userInfo.setShopsInfo(shopsInfo);
						List<ReplenishVO> list = replenishLogic.findReplenishPetList_BJ(userInfo,plate,periodsNum,Constant.TRUE_STATUS);
						if(list!=null){
								for(ReplenishVO v : list)
								{
									if(v.getPlayFinalType().equals(typeCode) ){
									if(v.getMoney() - po.getMoneyLimit()>=0)
									{
										Integer buMoney = v.getMoney()-po.getMoneyLimit();
										if(buMoney >= po.getMoneyRep()){ //補貨額 = 補貨最大額度 - 自動補貨的控制額度
											
											Replenish replenish = new Replenish();
											replenish.setTypeCode(v.getPlayFinalType());// 要与界面传入的type一致
											replenish.setMoney(buMoney);
											replenishLogic.saveReplenish(replenish, userInfo, periodsNum, "","no","autoReplenish");
											replenishAutoLogLogic.saveReplenishLog(new ReplenishAutoLog(shopID, "BJSC", v.getPlayFinalType(), buMoney, po.getCreateUser(),periodsNum,"1"));
											LOG.info("<--自动补货类型："+v.getPlayFinalType()+", 补货金额："+buMoney+", 补货人："+staff.getAccount()+" -->");
										}
										
									}
									}
								}
						}
				}
			}
		long end = System.currentTimeMillis();
		LOG.info("<--自動補貨 北京 end  所用时间："+(end-startTime)+" -->");
	}	
	
//********************触发自动补货END*************************	
	@Override
	public void updateReplenishAutoSetByUser(String shopsID,Long userID,String userType,String state){
		replenishAutoSetJDBCDao.updateReplenishAutoSetByUser(shopsID, userID, userType, state);
	}
	
	@Override
	public void updateReplenishAutoSetForClose(String shopsID,Long userID,String userType,String state){
		String strUser = "";
		List<Long> userList = replenishAutoSetJDBCDao.queryReplenishAutoSetUserList(userID, userType);
		for(int i=0; i<userList.size(); i++){
			if(i==0) strUser = "'";
			if(i != userList.size()-1){
				strUser = strUser + userList.get(i) + "','";
			}else{
				strUser = strUser + userID +"','";
				strUser = strUser + userList.get(i).toString() +"'";
			}
		}
		replenishAutoSetJDBCDao.updateReplenishAutoSetForClose(shopsID, strUser);
	}
	
	public AutoReplenishSetVO queryReplenishAutoSetForUser(Long userID,String shopID,String typeCode){
		List<Long> userList = new ArrayList<Long>();
		userList.add(userID);
	    List<AutoReplenishSetVO> setList = replenishAutoSetJDBCDao.queryReplenishAutoSet(userList, shopID, typeCode);
	    if(setList.size()==0){
	    	return null;
	    }else{
	    	return setList.get(0); 
	    }
	}
	
	private IBranchStaffExtDao branchStaffExtDao = null;
	
	public IReplenishAuto getReplenishAutoDao() {
		return replenishAutoDao;
	}

	public void setReplenishAutoDao(IReplenishAuto replenishAutoDao) {
		this.replenishAutoDao = replenishAutoDao;
	}

	public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}

	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}

	/*public IHKPeriodsInfoLogic getSkperiodsInfoLogic() {
		return skperiodsInfoLogic;
	}

	public void setSkperiodsInfoLogic(IHKPeriodsInfoLogic skperiodsInfoLogic) {
		this.skperiodsInfoLogic = skperiodsInfoLogic;
	}*/

	public ICQPeriodsInfoLogic getIcqPeriodsInfoLogic() {
		return icqPeriodsInfoLogic;
	}

	public void setIcqPeriodsInfoLogic(ICQPeriodsInfoLogic icqPeriodsInfoLogic) {
		this.icqPeriodsInfoLogic = icqPeriodsInfoLogic;
	}

	public IJSSBPeriodsInfoLogic getJssbPeriodsInfoLogic() {
		return jssbPeriodsInfoLogic;
	}

	public void setJssbPeriodsInfoLogic(IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic) {
		this.jssbPeriodsInfoLogic = jssbPeriodsInfoLogic;
	}

	public IGDPeriodsInfoLogic getPeriodsInfoLogic() {
		return periodsInfoLogic;
	}

	public void setPeriodsInfoLogic(IGDPeriodsInfoLogic periodsInfoLogic) {
		this.periodsInfoLogic = periodsInfoLogic;
	}

	public INCPeriodsInfoLogic getNcPeriodsInfoLogic() {
		return ncPeriodsInfoLogic;
	}

	public void setNcPeriodsInfoLogic(INCPeriodsInfoLogic ncPeriodsInfoLogic) {
		this.ncPeriodsInfoLogic = ncPeriodsInfoLogic;
	}

	public IManagerUserLogic getManagerUserLogic() {
		return managerUserLogic;
	}

	public void setManagerUserLogic(IManagerUserLogic managerUserLogic) {
		this.managerUserLogic = managerUserLogic;
	}

	public IMemberStaffExtLogic getMemberStaffExtLogic() {
		return memberStaffExtLogic;
	}

	public void setMemberStaffExtLogic(IMemberStaffExtLogic memberStaffExtLogic) {
		this.memberStaffExtLogic = memberStaffExtLogic;
	}

	public IManagerStaffLogic getManagerStaffLogic() {
		return managerStaffLogic;
	}

	public void setManagerStaffLogic(IManagerStaffLogic managerStaffLogic) {
		this.managerStaffLogic = managerStaffLogic;
	}

	public IShopsLogic getShopsLogic() {
		return shopsLogic;
	}

	public void setShopsLogic(IShopsLogic shopsLogic) {
		this.shopsLogic = shopsLogic;
	}

	public IReplenishAutoLogLogic getReplenishAutoLogLogic() {
		return replenishAutoLogLogic;
	}

	public void setReplenishAutoLogLogic(
			IReplenishAutoLogLogic replenishAutoLogLogic) {
		this.replenishAutoLogLogic = replenishAutoLogLogic;
	}

	public IBJSCPeriodsInfoLogic getBjscPeriodsInfoLogic() {
		return bjscPeriodsInfoLogic;
	}

	public void setBjscPeriodsInfoLogic(IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic) {
		this.bjscPeriodsInfoLogic = bjscPeriodsInfoLogic;
	}

	public IReplenishAutoSetLogLogic getReplenishAutoSetLogLogic() {
		return replenishAutoSetLogLogic;
	}

	public void setReplenishAutoSetLogLogic(
			IReplenishAutoSetLogLogic replenishAutoSetLogLogic) {
		this.replenishAutoSetLogLogic = replenishAutoSetLogLogic;
	}
	public IReplenishAutoSetJDBCDao getReplenishAutoSetJDBCDao() {
		return replenishAutoSetJDBCDao;
	}

	public void setReplenishAutoSetJDBCDao(
			IReplenishAutoSetJDBCDao replenishAutoSetJDBCDao) {
		this.replenishAutoSetJDBCDao = replenishAutoSetJDBCDao;
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public IBranchStaffExtDao getBranchStaffExtDao() {
		return branchStaffExtDao;
	}

	public void setBranchStaffExtDao(IBranchStaffExtDao branchStaffExtDao) {
		this.branchStaffExtDao = branchStaffExtDao;
	}

	

}
