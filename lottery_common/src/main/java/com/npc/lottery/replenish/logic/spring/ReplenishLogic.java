package com.npc.lottery.replenish.logic.spring;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.boss.logic.interf.IShopsLogic;
import com.npc.lottery.common.Constant;
import com.npc.lottery.member.dao.interf.ICheckDao;
import com.npc.lottery.member.dao.interf.IPlayAmountDao;
import com.npc.lottery.member.entity.PlayAmount;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.logic.interf.IPlayTypeLogic;
import com.npc.lottery.odds.entity.OpenPlayOdds;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.INCPeriodsInfoLogic;
import com.npc.lottery.replenish.dao.interf.IReplenishCheckDao;
import com.npc.lottery.replenish.dao.interf.IReplenishDao;
import com.npc.lottery.replenish.dao.interf.IReplenishHibDao;
import com.npc.lottery.replenish.dao.interf.IReplenishHisDao;
import com.npc.lottery.replenish.entity.Replenish;
import com.npc.lottery.replenish.entity.ReplenishCheck;
import com.npc.lottery.replenish.entity.ReplenishHis;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoLogic;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.replenish.vo.DetailVO;
import com.npc.lottery.replenish.vo.ReplenishVO;
import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.replenish.vo.ZhanDangVO;
import com.npc.lottery.statreport.entity.GdklsfHis;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.ShopsPlayOddsLog;
import com.npc.lottery.sysmge.logic.interf.IBossLogLogic;
import com.npc.lottery.sysmge.logic.interf.IShopsPlayOddsLogLogic;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;
import com.npc.lottery.user.logic.interf.IUserCommissionDefault;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.user.vo.UserInfoVO;
import com.npc.lottery.util.Page;
import com.npc.lottery.util.PlayTypeUtils;
import com.npc.lottery.util.SpringUtil;


public class ReplenishLogic implements IReplenishLogic {
	private static Logger log = Logger.getLogger(ReplenishLogic.class);
	private String userType = null;
	private String rateUser = null;
	private String nextColumn = null;
	private String commissionUser = null;
	private String outCommissionUser = null;
	private String inCommissionUser = null;
	
	private List<ReplenishVO> petList = null;  //球表
	private List<ReplenishVO> replenishList = null;  //补货人_补货球表
	private List<ReplenishVO> replenishAccList = null;  //被补货人_补货球表
	
	public UserVO getUserType(ManagerUser userInfo){
		
		UserVO userVO = new UserVO();
		boolean isSubAccount = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB);//子帐号
        /*
         * 子帐号处理
         * 如果是子帐号，就把当前用户的SESSION更改为该子帐号的所有者的userID和userType
         * 
         */
        /*if(isSubAccount){
			ManagerUser userInfoSub = new ManagerUser();
			userInfoSub = subAccountInfoLogic.changeSubAccountInfo(userInfo);
			userInfo.setID(userInfoSub.getID());
			userInfo.setUserType(userInfoSub.getUserType());
		}*/
        
		boolean isChief = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_CHIEF);// 总监类型
        boolean isBranch = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
        boolean isStockholder = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_STOCKHOLDER);// 股东
        boolean isGenAgent = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_GEN_AGENT);// 总代理
        boolean isAgent = userInfo.getUserType().equals(ManagerStaff.USER_TYPE_AGENT);// 代理
        
        /* 
         * 统计投注数据时，占成拿本身的，统计补下级补上时拥金拿下级，
         *                    统计自已补出时，拥金拿自己的。
         * 统计补货数据时，占成拿本身的，当是代理时拥金拿本身，当是代理以上时，拥金拿下一级。
         * nextColumn主要用于统计报表时使用，因为是上一级查下一级的，当是代理补货时，代理那栏是空的。
         * 
         */
        String nextRateColumn = "";
        if (isChief){userType = "CHIEFSTAFF";nextColumn="BRANCHSTAFF";rateUser = "rate_chief";nextRateColumn = "rate_branch";
        commissionUser = "COMMISSION_BRANCH"; inCommissionUser="COMMISSION_BRANCH";outCommissionUser="COMMISSION_CHIEF";}
        
        if (isBranch){userType = "BRANCHSTAFF";nextColumn="STOCKHOLDERSTAFF";rateUser = "rate_branch";nextRateColumn = "RATE_STOCKHOLDER";
        commissionUser = "COMMISSION_STOCKHOLDER"; inCommissionUser="COMMISSION_STOCKHOLDER";outCommissionUser="COMMISSION_BRANCH";}
        
        if (isStockholder){userType = "STOCKHOLDERSTAFF";nextColumn="GENAGENSTAFF";rateUser = "RATE_STOCKHOLDER";nextRateColumn = "RATE_GEN_AGENT";
        commissionUser = "COMMISSION_GEN_AGENT"; inCommissionUser="COMMISSION_GEN_AGENT";outCommissionUser="COMMISSION_STOCKHOLDER";}
        
        if (isGenAgent){userType = "GENAGENSTAFF";nextColumn="replenish_user_id";rateUser = "RATE_GEN_AGENT";nextRateColumn = "RATE_AGENT";
        commissionUser = "COMMISSION_AGENT"; inCommissionUser="COMMISSION_AGENT";outCommissionUser="COMMISSION_GEN_AGENT";}
        
        if (isAgent){userType = "AGENTSTAFF";nextColumn="replenish_user_id";rateUser = "RATE_AGENT";
        commissionUser = "COMMISSION_MEMBER"; inCommissionUser="COMMISSION_AGENT";outCommissionUser="COMMISSION_AGENT";}
        
        //userID = userInfo.getID();    
        userVO.setUserType(userType);//myColumn
        userVO.setNextColumn(nextColumn);//myColumn
        userVO.setRateUser(rateUser);
        userVO.setNextRateColumn(nextRateColumn);
        userVO.setCommissionUser(commissionUser);
        userVO.setInCommissionUser(inCommissionUser);
        userVO.setOutCommissionUser(outCommissionUser);
        return userVO;
	}
	
	/*
	 * 用于进行补货操作时校验数据
	 * 页面上其实已经有了补货限额的JS校验，此方法主要是防止人为恶意改页面数据强行提交的问题。
	 */
	@Override
	public Boolean varifyData(Integer rMoney,ManagerUser userInfo,String plate,String periodsNum,String typeCode,String attribute){
		String validType = "";
		this.getUserType(userInfo);
	  try {
			
		if(typeCode.indexOf(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIRST)!=-1  || typeCode.indexOf("GDKLSF_DOUBLESIDE_1")!=-1){
			validType="NORMAL";
			petList = replenishDao.queryTotal_GD_valiate(Constant.GDKLSF_TABLE_LIST[0], userType, userInfo.getID(), plate, periodsNum,rateUser,commissionUser,typeCode);
		}else 
		if(typeCode.indexOf(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SECOND)!=-1 || typeCode.indexOf("GDKLSF_DOUBLESIDE_2")!=-1){
			validType="NORMAL";
			petList = replenishDao.queryTotal_GD_valiate(Constant.GDKLSF_TABLE_LIST[1], userType, userInfo.getID(), plate, periodsNum,rateUser,commissionUser,typeCode);
		}else 
		if(typeCode.indexOf(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_THIRD)!=-1 || typeCode.indexOf("GDKLSF_DOUBLESIDE_3")!=-1){
			validType="NORMAL";
			petList = replenishDao.queryTotal_GD_valiate(Constant.GDKLSF_TABLE_LIST[2], userType, userInfo.getID(), plate, periodsNum,rateUser,commissionUser,typeCode);
		}else 
		if(typeCode.indexOf(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FORTH)!=-1 || typeCode.indexOf("GDKLSF_DOUBLESIDE_4")!=-1){
			validType="NORMAL";
			petList = replenishDao.queryTotal_GD_valiate(Constant.GDKLSF_TABLE_LIST[3], userType, userInfo.getID(), plate, periodsNum,rateUser,commissionUser,typeCode);
		}else 
		if(typeCode.indexOf(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIFTH)!=-1 || typeCode.indexOf("GDKLSF_DOUBLESIDE_5")!=-1){
			validType="NORMAL";
			petList = replenishDao.queryTotal_GD_valiate(Constant.GDKLSF_TABLE_LIST[4], userType, userInfo.getID(), plate, periodsNum,rateUser,commissionUser,typeCode);
		}else 
		if(typeCode.indexOf(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SIXTH)!=-1 || typeCode.indexOf("GDKLSF_DOUBLESIDE_6")!=-1){
			validType="NORMAL";
			petList = replenishDao.queryTotal_GD_valiate(Constant.GDKLSF_TABLE_LIST[5], userType, userInfo.getID(), plate, periodsNum,rateUser,commissionUser,typeCode);
		}else 
		if(typeCode.indexOf(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SEVENTH)!=-1 || typeCode.indexOf("GDKLSF_DOUBLESIDE_7")!=-1){
			validType="NORMAL";
			petList = replenishDao.queryTotal_GD_valiate(Constant.GDKLSF_TABLE_LIST[6], userType, userInfo.getID(), plate, periodsNum,rateUser,commissionUser,typeCode);
		}else 
		if(typeCode.indexOf(Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_EIGHTH)!=-1 || typeCode.indexOf("GDKLSF_DOUBLESIDE_8")!=-1){
			validType="NORMAL";
			petList = replenishDao.queryTotal_GD_valiate(Constant.GDKLSF_TABLE_LIST[7], userType, userInfo.getID(), plate, periodsNum,rateUser,commissionUser,typeCode);
		}else 
		if(typeCode.indexOf("GDKLSF_DOUBLESIDE_ZH")!=-1 || typeCode.indexOf("GDKLSF_DOUBLESIDE_LONG")!=-1 || typeCode.indexOf("GDKLSF_DOUBLESIDE_HU")!=-1){
			validType="NORMAL";
			petList = replenishDao.queryDoubleTotal(userType, userInfo.getID(), plate, periodsNum,rateUser,commissionUser,typeCode);
		}else 	
		if(typeCode.indexOf(Constant.LOTTERY_TYPE_CQSSC)!=-1){
			validType="NORMAL";
			petList = replenishDao.queryTotal_CQ(userType, userInfo.getID(), plate, periodsNum, rateUser,commissionUser,typeCode);
		}else 
		if(typeCode.indexOf(Constant.LOTTERY_TYPE_BJ)!=-1){
			validType="NORMAL";
			petList = replenishDao.queryTotal_BJ_valiate(userType, userInfo.getID(), plate, periodsNum, rateUser,commissionUser,typeCode);
		}else 
		if(typeCode.indexOf(Constant.LOTTERY_TYPE_K3)!=-1){
			validType="NORMAL";
			petList = replenishDao.queryTotal_K3_valiate(userType, userInfo.getID(), plate, periodsNum, rateUser,commissionUser,typeCode);
		}
	    if(validType.equals("NORMAL")){
			this.findReplenishList(periodsNum, plate, typeCode+"%",userInfo);
	    }		
	    
	    //处理连码
	    if(typeCode.indexOf(Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH)!=-1){
	    	validType="LM";
	    	petList = replenishDao.queryTotal_LM_valite(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME, userType, userInfo.getID(), plate, 
	    			periodsNum,rateUser,commissionUser,typeCode,attribute);
		}
	    if(validType.equals("LM")){
	    	//补出的货
		    replenishList = replenishDao.queryReplenish_LM_valiate("replenish_user_id",userInfo.getID(),typeCode+"%",
		    		periodsNum,plate,outCommissionUser,attribute,userInfo.getUserType());
		    //接受补入的货		    
		    replenishAccList = replenishDao.queryReplenish_LM_Acc_valiate(userType,userInfo.getID(),typeCode+"%",
		    		periodsNum,plate,rateUser,inCommissionUser,attribute);
	    }
	    BigDecimal rPrice = BigDecimal.ZERO;
	    BigDecimal rAccPrice = BigDecimal.ZERO;
	    if(!replenishList.isEmpty()){
	    	rPrice = BigDecimal.valueOf(replenishList.get(0).getMoney());
	    }
	    if(!replenishAccList.isEmpty()){
	    	rAccPrice = replenishAccList.get(0).getRateMoney();
	    	
	    }
	    //郑断当用补货用户的占货额+已经补出的额度-下级补入的额度-准备要补货的金额后            的结果是否小于0，如果小于0，就返回错误
	    //System.out.println(petList.get(0).getRateMoney()+","+rPrice+","+rAccPrice+","+rMoney+",");
	    int r = (petList.get(0).getRateMoney().subtract(rPrice).add(rAccPrice).subtract(BigDecimal.valueOf(rMoney))).compareTo(BigDecimal.ZERO);
	    if(r==-1){
	    	return false;	    	
	    }	    	    
		
	  } catch (Exception e) {
			log.info("补货校验异常！"+e.getMessage());
	  }
	  return true;
	}
	
   //统计每一笔补货减去拥金后金额
   public BigDecimal computeCommission(List<ReplenishVO> rList,String type){
	   BigDecimal totalCommission = BigDecimal.ZERO;
		for(int i=0;i<rList.size();i++){
			ReplenishVO tVo = rList.get(i);
			//补货金额*(1-补货的拥金率)
			totalCommission = totalCommission.add(tVo.getCommissionMoney());
		}
		return totalCommission;
		
	}
   
	//查询朴出和补进的货的清单
	public void findReplenishList(String periodsNum,String plate,String subType,ManagerUser userInfo){
		replenishList = replenishDao.queryReplenishForOut("replenish_user_id",userInfo.getID(),subType,periodsNum,plate,outCommissionUser,userInfo.getUserType());
		replenishAccList = replenishDao.queryReplenishForIn(userType,userInfo.getID(),subType,periodsNum,plate,rateUser,inCommissionUser);
	}
	
	public Integer queryReplenishForBetCheck(Long userID,String userType,String typeCode,String periodsNum){
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(userID);
		userInfo.setUserType(userType);
		UserVO vo = this.getUserType(userInfo);
		
		List<ReplenishVO> list = replenishDao.queryReplenishInForBetCheck(vo.getUserType(), userID, typeCode, periodsNum, vo.getRateUser());
		Integer money = 0;
		if(list!=null && list.size()>0){
			money = list.get(0).getMoney();
		}
		return money;
	}
	
	public Integer queryReplenishForBetCheckByScheme(Long userID,String userType,String typeCode,String periodsNum,String scheme){
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(userID);
		userInfo.setUserType(userType);
		UserVO vo = this.getUserType(userInfo);
		
		List<ReplenishVO> list = replenishDao.queryReplenishInForBetCheckByScheme(vo.getUserType(), userID, typeCode, periodsNum, vo.getRateUser(),scheme);
		Integer money = 0;
		if(list!=null && list.size()>0){
			money = list.get(0).getMoney();
		}
		return money;
	}
	
	//处理补货信息，减去补出的，加上补入的
	public void totalReplenish(String searchType,String typeCode,String playSubType){	
		//因为没有投注的玩法也要计算盈亏，所以要从玩法表取出所有玩法进行与投注LIST合并
		List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		filtersPlayType.add(Restrictions.eq("playType",typeCode));
		if(!typeCode.equals(Constant.LOTTERY_TYPE_CQSSC)){
			filtersPlayType.add(Restrictions.ilike("playSubType", playSubType, MatchMode.ANYWHERE));
		}
		List<PlayType> ptList = playTypeLogic.findPlayType(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));
		
		List<ReplenishVO> tmpPetList =Lists.newArrayList(petList);
		for(PlayType sVo: ptList){
			String isSame = "false";
			for(ReplenishVO vo: tmpPetList){
				if(sVo.getTypeCode().equals(vo.getPlayFinalType())){
					isSame="true";
				}
			}
			if(isSame.equals("false")){
				ReplenishVO rvo = new ReplenishVO();
				rvo.setPlayFinalType(sVo.getTypeCode());
				petList.add(rvo);
			}
		}
		
		for(ReplenishVO vo: petList){
			/*
			 * 实占和虚注查询情况下，都要显示实占的盈亏额，但在虚注情况下余额项是不计算补货额的,但要显示已补额
			 */
			if(Constant.TRUE_STATUS.equals(searchType) || Constant.COMPANY_STATUS.equals(searchType)){  //实占或公司占的情况	
				vo.setMoney(vo.getRateMoney().intValue());
			}
			//减去补出的
			Integer outMoney = 0;
			BigDecimal outCommissionMoney = BigDecimal.ZERO;
			for(ReplenishVO tVo: replenishList){
				if(vo.getPlayFinalType().equals(tVo.getPlayFinalType())){
					//统计已补额
					outMoney += tVo.getMoney();
					outCommissionMoney = outCommissionMoney.add(tVo.getCommissionMoney());
			    }
			}
			vo.setRMoney(outMoney);
			if(Constant.TRUE_STATUS.equals(searchType) || Constant.COMPANY_STATUS.equals(searchType)){  //实占或公司占的情况
				//实占投注额-补货的注额
				vo.setMoney((vo.getRateMoney().subtract(BigDecimal.valueOf(outMoney))).intValue());
			}
			//加上接受补入的
			Integer inMoney = 0;
			BigDecimal inCommissionMoney = BigDecimal.ZERO;
			for(ReplenishVO tVo: replenishAccList){
				if(vo.getPlayFinalType().equals(tVo.getPlayFinalType())){
					inMoney += tVo.getMoney();
					inCommissionMoney = inCommissionMoney.subtract(tVo.getCommissionMoney());
				}
			}
			if(Constant.TRUE_STATUS.equals(searchType) || Constant.COMPANY_STATUS.equals(searchType)){  //实占或公司占的情况
				//实占投注额+补货的注额
				vo.setMoney((BigDecimal.valueOf(vo.getMoney()).add(BigDecimal.valueOf(inMoney))).intValue());	
				
			}
			//System.out.println("~~~~~~~~~~~~~~~c :"+vo.getCommissionMoney());
			//System.out.println("~~~~~~~~~~~~~~~in :"+inCommissionMoney);
			//System.out.println("~~~~~~~~~~~~~~~out :"+outCommissionMoney);
			vo.setTotalCommissionMoney(vo.getCommissionMoney().subtract(inCommissionMoney).subtract(outCommissionMoney));
	     }
		
	}
	
	
	/*
	 * 广东号球和两面的补货
	 * 此界面同时统计号球表和双面表，只为号球表里面也存有双面表里的双面相关投注信息，要合并统计
	 */
	@Override
	public List<ReplenishVO> findReplenishPetList(String tableName,ManagerUser userInfo,
			    String plate,String periodsNum,String searchType,String ballNum,String preName) {
	    //存MONEY*(1-拥金)合计
		BigDecimal cBallPetTotal = BigDecimal.ZERO;    //号球
		BigDecimal cDxPetTotal = BigDecimal.ZERO;      //大小
		BigDecimal cDsPetTotal = BigDecimal.ZERO;      //单双
		BigDecimal cWdxPetTotal = BigDecimal.ZERO;     //尾大小
		BigDecimal cHsdsPetTotal = BigDecimal.ZERO;    //合数单双
		BigDecimal cFwPetTotal = BigDecimal.ZERO;      //方位
		BigDecimal cZfbPetTotal = BigDecimal.ZERO;     //中发白
		BigDecimal cPetTotal = BigDecimal.ZERO;   
		//存补出的补货统计
		BigDecimal outBallPetTotal = BigDecimal.ZERO;    //号球
		BigDecimal outDxPetTotal = BigDecimal.ZERO;      //大小
		BigDecimal outDsPetTotal = BigDecimal.ZERO;      //单双
		BigDecimal outWdxPetTotal = BigDecimal.ZERO;     //尾大小
		BigDecimal outHsdsPetTotal = BigDecimal.ZERO;    //合数单双
		BigDecimal outFwPetTotal = BigDecimal.ZERO;      //方位
		BigDecimal outZfbPetTotal = BigDecimal.ZERO;     //中发白
		BigDecimal outPetTotal = BigDecimal.ZERO;   
		//存补出的补货统计
		BigDecimal inBallPetTotal = BigDecimal.ZERO;    //号球
		BigDecimal inDxPetTotal = BigDecimal.ZERO;      //大小
		BigDecimal inDsPetTotal = BigDecimal.ZERO;      //单双
		BigDecimal inWdxPetTotal = BigDecimal.ZERO;     //尾大小
		BigDecimal inHsdsPetTotal = BigDecimal.ZERO;    //合数单双
		BigDecimal inFwPetTotal = BigDecimal.ZERO;      //方位
		BigDecimal inZfbPetTotal = BigDecimal.ZERO;     //中发白
		BigDecimal inPetTotal = BigDecimal.ZERO; 
		
		Map<String,BigDecimal> rMapOut = new HashMap<String,BigDecimal>();
		Map<String,BigDecimal> rMapIn = new HashMap<String,BigDecimal>();

		
		boolean isSys = userInfo.getUserType().equals( ManagerStaff.USER_TYPE_SYS);// 系统类型
		if (!isSys)// 系统管理员一般不操作
        {
			this.getUserType(userInfo);
					   
			String typeCode = "%";
			petList = replenishDao.queryTotal_GD(tableName, userType, userInfo.getID(), plate, periodsNum,rateUser,commissionUser,typeCode);
		    //补出的货
			this.findReplenishList(periodsNum, plate, "GDKLSF_%",userInfo);
			
			this.totalReplenish(searchType,Constant.LOTTERY_TYPE_GDKLSF,"BALL_"+preName);
		    
			for(ReplenishVO vo : replenishList){
				BigDecimal rOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						rOddsMoney = rOddsMoney.add(rvo.getOddsMoney());
					}
				}
				rMapOut.put(vo.getPlayFinalType(), rOddsMoney);
			}
			//补进
			//统计每种玩法的补货的赔额
			for(ReplenishVO vo : replenishAccList){
				BigDecimal inOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishAccList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						inOddsMoney = inOddsMoney.add(rvo.getOddsMoney());
					}
				}
				rMapIn.put(vo.getPlayFinalType(), inOddsMoney);
			}
			
		    //分类统计投注总额	
			for(ReplenishVO vo : petList){
				if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_DA") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_X")){
						cDxPetTotal = cDxPetTotal.add(vo.getCommissionMoney());	
				}else 
					if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_DAN") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_S")){
						   cDsPetTotal = cDsPetTotal.add(vo.getCommissionMoney());	
					}else 
						if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_WD") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_WX")){							
							   cWdxPetTotal = cWdxPetTotal.add(vo.getCommissionMoney());	
						}else
							if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_HSD") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_HSS")){
								    cHsdsPetTotal = cHsdsPetTotal.add(vo.getCommissionMoney());	
							}else
								if(vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_DONG") || vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_NAN") ||
										vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_XI") || vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_BEI")){									
									    cFwPetTotal = cFwPetTotal.add(vo.getCommissionMoney());	
								}else
									if(vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_Z") || vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_F") ||
											vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_B")){
										   cZfbPetTotal = cZfbPetTotal.add(vo.getCommissionMoney());	
									}else
										if(vo.getPlayFinalType().indexOf("GDKLSF_BALL_" + preName)==0){
										    cBallPetTotal = cBallPetTotal.add(vo.getCommissionMoney());	
									    }				
		 	}
			
			//分类统计补出的补货	
			for(ReplenishVO vo : replenishList){
				if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_DA") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_X")){
						outDxPetTotal = outDxPetTotal.add(vo.getCommissionMoney());	
				}else 
					if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_DAN") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_S")){
						outDsPetTotal = outDsPetTotal.add(vo.getCommissionMoney());	
					}else 
						if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_WD") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_WX")){							
							outWdxPetTotal = outWdxPetTotal.add(vo.getCommissionMoney());	
						}else
							if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_HSD") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_HSS")){
								outHsdsPetTotal = outHsdsPetTotal.add(vo.getCommissionMoney());	
							}else
								if(vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_DONG") || vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_NAN") ||
										vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_XI") || vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_BEI")){									
									outFwPetTotal = outFwPetTotal.add(vo.getCommissionMoney());	
								}else
									if(vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_Z") || vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_F") ||
											vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_B")){
										outZfbPetTotal = outZfbPetTotal.add(vo.getCommissionMoney());	
									}else 
									  if(vo.getPlayFinalType().indexOf("GDKLSF_BALL_" + preName)==0){
										outBallPetTotal = outBallPetTotal.add(vo.getCommissionMoney());	
									  }				
		 	}
			
			//分类统计补进的补货	
			for(ReplenishVO vo : replenishAccList){
				if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_DA") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_X")){
						inDxPetTotal = inDxPetTotal.add(vo.getCommissionMoney());	
				}else 
					if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_DAN") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_S")){
						inDsPetTotal = inDsPetTotal.add(vo.getCommissionMoney());	
					}else 
						if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_WD") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_WX")){							
							inWdxPetTotal = inWdxPetTotal.add(vo.getCommissionMoney());	
						}else
							if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_HSD") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_" +ballNum+"_HSS")){
								inHsdsPetTotal = inHsdsPetTotal.add(vo.getCommissionMoney());	
							}else
								if(vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_DONG") || vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_NAN") ||
										vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_XI") || vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_BEI")){									
									inFwPetTotal = inFwPetTotal.add(vo.getCommissionMoney());	
								}else
									if(vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_Z") || vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_F") ||
											vo.getPlayFinalType().equals("GDKLSF_BALL_" +preName+"_B")){
										inZfbPetTotal = inZfbPetTotal.add(vo.getCommissionMoney());	
									}else
										if(vo.getPlayFinalType().indexOf("GDKLSF_BALL_" + preName)==0){
										   inBallPetTotal = inBallPetTotal.add(vo.getCommissionMoney());	
									    }				
		 	}
			
			//计算亏盈
            for(int ii=0; ii<petList.size(); ii++){
				BigDecimal pet = BigDecimal.ZERO;
				ReplenishVO vo = petList.get(ii);
				
				String petPlayFinalType = vo.getPlayFinalType();
				if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_DA") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_X")){
					cPetTotal = cDxPetTotal;
					outPetTotal = outDxPetTotal;
					inPetTotal = inDxPetTotal;
				}else
					if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_DAN") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_S")){
						cPetTotal = cDsPetTotal;
						outPetTotal = outDsPetTotal;
						inPetTotal = inDsPetTotal;
					}else
						if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_WD") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_WX")){
							cPetTotal = cWdxPetTotal;
							outPetTotal  = outWdxPetTotal;
							inPetTotal  = inWdxPetTotal;
						}else
							if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_HSD") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_" +ballNum+"_HSS")){
								cPetTotal = cHsdsPetTotal;
								outPetTotal  = outHsdsPetTotal;
								inPetTotal  = inHsdsPetTotal;
							}else
								if(petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_DONG") || petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_NAN") ||
										petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_XI") || petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_BEI")){
									cPetTotal = cFwPetTotal;
									outPetTotal = outFwPetTotal;
									inPetTotal = inFwPetTotal;
								}else
									if(petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_Z") || petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_F") ||
											petPlayFinalType.equals("GDKLSF_BALL_" +preName+"_B")){
										cPetTotal = cZfbPetTotal;
										outPetTotal = outZfbPetTotal;
										inPetTotal = inZfbPetTotal;
									}else{
										cPetTotal = cBallPetTotal;
										outPetTotal = outBallPetTotal;
										inPetTotal = inBallPetTotal;
									}
				
				pet = cPetTotal.subtract(vo.getOddsMoney());
				BigDecimal outM = BigDecimal.ZERO;
				BigDecimal inM = BigDecimal.ZERO;		
				if(rMapOut.get(vo.getPlayFinalType())!=null){
					outM = rMapOut.get(vo.getPlayFinalType()).subtract(outPetTotal);
				}else{
					outM = outM.subtract(outPetTotal);
				}
				if(rMapIn.get(vo.getPlayFinalType())!=null){
					inM = rMapIn.get(vo.getPlayFinalType()).subtract(inPetTotal);
				}else{
					inM = inM.subtract(inPetTotal);
				}
				petList.get(ii).setLoseMoney(pet.add(outM).subtract(inM));
            }	
		}
					
		return petList;
	}
	
	/*
	 * 广东号球和两面的补货
	 * 此界面同时统计号球表和双面表，只为号球表里面也存有双面表里的双面相关投注信息，要合并统计
	 */
	@Override
	public List<ReplenishVO> findReplenishPetList_NC(String tableName,ManagerUser userInfo,
			String plate,String periodsNum,String searchType,String ballNum,String preName) {
		//存MONEY*(1-拥金)合计
		BigDecimal cBallPetTotal = BigDecimal.ZERO;    //号球
		BigDecimal cDxPetTotal = BigDecimal.ZERO;      //大小
		BigDecimal cDsPetTotal = BigDecimal.ZERO;      //单双
		BigDecimal cWdxPetTotal = BigDecimal.ZERO;     //尾大小
		BigDecimal cHsdsPetTotal = BigDecimal.ZERO;    //合数单双
		BigDecimal cFwPetTotal = BigDecimal.ZERO;      //方位
		BigDecimal cZfbPetTotal = BigDecimal.ZERO;     //中发白
		BigDecimal cPetTotal = BigDecimal.ZERO;   
		//存补出的补货统计
		BigDecimal outBallPetTotal = BigDecimal.ZERO;    //号球
		BigDecimal outDxPetTotal = BigDecimal.ZERO;      //大小
		BigDecimal outDsPetTotal = BigDecimal.ZERO;      //单双
		BigDecimal outWdxPetTotal = BigDecimal.ZERO;     //尾大小
		BigDecimal outHsdsPetTotal = BigDecimal.ZERO;    //合数单双
		BigDecimal outFwPetTotal = BigDecimal.ZERO;      //方位
		BigDecimal outZfbPetTotal = BigDecimal.ZERO;     //中发白
		BigDecimal outPetTotal = BigDecimal.ZERO;   
		//存补出的补货统计
		BigDecimal inBallPetTotal = BigDecimal.ZERO;    //号球
		BigDecimal inDxPetTotal = BigDecimal.ZERO;      //大小
		BigDecimal inDsPetTotal = BigDecimal.ZERO;      //单双
		BigDecimal inWdxPetTotal = BigDecimal.ZERO;     //尾大小
		BigDecimal inHsdsPetTotal = BigDecimal.ZERO;    //合数单双
		BigDecimal inFwPetTotal = BigDecimal.ZERO;      //方位
		BigDecimal inZfbPetTotal = BigDecimal.ZERO;     //中发白
		BigDecimal inPetTotal = BigDecimal.ZERO; 
		
		Map<String,BigDecimal> rMapOut = new HashMap<String,BigDecimal>();
		Map<String,BigDecimal> rMapIn = new HashMap<String,BigDecimal>();
		
		
		boolean isSys = userInfo.getUserType().equals( ManagerStaff.USER_TYPE_SYS);// 系统类型
		if (!isSys)// 系统管理员一般不操作
		{
			this.getUserType(userInfo);
			
			String typeCode = "%";
			petList = replenishDao.queryTotal_NC(tableName, userType, userInfo.getID(), plate, periodsNum,rateUser,commissionUser,typeCode);
			//补出的货
			this.findReplenishList(periodsNum, plate, "NC_%",userInfo);
			
			this.totalReplenish(searchType,Constant.LOTTERY_TYPE_NC,"BALL_"+preName);
			
			for(ReplenishVO vo : replenishList){
				BigDecimal rOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						rOddsMoney = rOddsMoney.add(rvo.getOddsMoney());
					}
				}
				rMapOut.put(vo.getPlayFinalType(), rOddsMoney);
			}
			//补进
			//统计每种玩法的补货的赔额
			for(ReplenishVO vo : replenishAccList){
				BigDecimal inOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishAccList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						inOddsMoney = inOddsMoney.add(rvo.getOddsMoney());
					}
				}
				rMapIn.put(vo.getPlayFinalType(), inOddsMoney);
			}
			
			//分类统计投注总额	
			for(ReplenishVO vo : petList){
				if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_DA") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_X")){
					cDxPetTotal = cDxPetTotal.add(vo.getCommissionMoney());	
				}else 
					if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_DAN") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_S")){
						cDsPetTotal = cDsPetTotal.add(vo.getCommissionMoney());	
					}else 
						if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_WD") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_WX")){							
							cWdxPetTotal = cWdxPetTotal.add(vo.getCommissionMoney());	
						}else
							if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_HSD") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_HSS")){
								cHsdsPetTotal = cHsdsPetTotal.add(vo.getCommissionMoney());	
							}else
								if(vo.getPlayFinalType().equals("NC_BALL_" +preName+"_DONG") || vo.getPlayFinalType().equals("NC_BALL_" +preName+"_NAN") ||
										vo.getPlayFinalType().equals("NC_BALL_" +preName+"_XI") || vo.getPlayFinalType().equals("NC_BALL_" +preName+"_BEI")){									
									cFwPetTotal = cFwPetTotal.add(vo.getCommissionMoney());	
								}else
									if(vo.getPlayFinalType().equals("NC_BALL_" +preName+"_Z") || vo.getPlayFinalType().equals("NC_BALL_" +preName+"_F") ||
											vo.getPlayFinalType().equals("NC_BALL_" +preName+"_B")){
										cZfbPetTotal = cZfbPetTotal.add(vo.getCommissionMoney());	
									}else
										if(vo.getPlayFinalType().indexOf("NC_BALL_" + preName)==0){
											cBallPetTotal = cBallPetTotal.add(vo.getCommissionMoney());	
										}				
			}
			
			//分类统计补出的补货	
			for(ReplenishVO vo : replenishList){
				if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_DA") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_X")){
					outDxPetTotal = outDxPetTotal.add(vo.getCommissionMoney());	
				}else 
					if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_DAN") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_S")){
						outDsPetTotal = outDsPetTotal.add(vo.getCommissionMoney());	
					}else 
						if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_WD") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_WX")){							
							outWdxPetTotal = outWdxPetTotal.add(vo.getCommissionMoney());	
						}else
							if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_HSD") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_HSS")){
								outHsdsPetTotal = outHsdsPetTotal.add(vo.getCommissionMoney());	
							}else
								if(vo.getPlayFinalType().equals("NC_BALL_" +preName+"_DONG") || vo.getPlayFinalType().equals("NC_BALL_" +preName+"_NAN") ||
										vo.getPlayFinalType().equals("NC_BALL_" +preName+"_XI") || vo.getPlayFinalType().equals("NC_BALL_" +preName+"_BEI")){									
									outFwPetTotal = outFwPetTotal.add(vo.getCommissionMoney());	
								}else
									if(vo.getPlayFinalType().equals("NC_BALL_" +preName+"_Z") || vo.getPlayFinalType().equals("NC_BALL_" +preName+"_F") ||
											vo.getPlayFinalType().equals("NC_BALL_" +preName+"_B")){
										outZfbPetTotal = outZfbPetTotal.add(vo.getCommissionMoney());	
									}else 
										if(vo.getPlayFinalType().indexOf("NC_BALL_" + preName)==0){
											outBallPetTotal = outBallPetTotal.add(vo.getCommissionMoney());	
										}				
			}
			
			//分类统计补进的补货	
			for(ReplenishVO vo : replenishAccList){
				if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_DA") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_X")){
					inDxPetTotal = inDxPetTotal.add(vo.getCommissionMoney());	
				}else 
					if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_DAN") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_S")){
						inDsPetTotal = inDsPetTotal.add(vo.getCommissionMoney());	
					}else 
						if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_WD") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_WX")){							
							inWdxPetTotal = inWdxPetTotal.add(vo.getCommissionMoney());	
						}else
							if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_HSD") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_" +ballNum+"_HSS")){
								inHsdsPetTotal = inHsdsPetTotal.add(vo.getCommissionMoney());	
							}else
								if(vo.getPlayFinalType().equals("NC_BALL_" +preName+"_DONG") || vo.getPlayFinalType().equals("NC_BALL_" +preName+"_NAN") ||
										vo.getPlayFinalType().equals("NC_BALL_" +preName+"_XI") || vo.getPlayFinalType().equals("NC_BALL_" +preName+"_BEI")){									
									inFwPetTotal = inFwPetTotal.add(vo.getCommissionMoney());	
								}else
									if(vo.getPlayFinalType().equals("NC_BALL_" +preName+"_Z") || vo.getPlayFinalType().equals("NC_BALL_" +preName+"_F") ||
											vo.getPlayFinalType().equals("NC_BALL_" +preName+"_B")){
										inZfbPetTotal = inZfbPetTotal.add(vo.getCommissionMoney());	
									}else
										if(vo.getPlayFinalType().indexOf("NC_BALL_" + preName)==0){
											inBallPetTotal = inBallPetTotal.add(vo.getCommissionMoney());	
										}				
			}
			
			//计算亏盈
			for(int ii=0; ii<petList.size(); ii++){
				BigDecimal pet = BigDecimal.ZERO;
				ReplenishVO vo = petList.get(ii);
				
				String petPlayFinalType = vo.getPlayFinalType();
				if(petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_DA") || petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_X")){
					cPetTotal = cDxPetTotal;
					outPetTotal = outDxPetTotal;
					inPetTotal = inDxPetTotal;
				}else
					if(petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_DAN") || petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_S")){
						cPetTotal = cDsPetTotal;
						outPetTotal = outDsPetTotal;
						inPetTotal = inDsPetTotal;
					}else
						if(petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_WD") || petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_WX")){
							cPetTotal = cWdxPetTotal;
							outPetTotal  = outWdxPetTotal;
							inPetTotal  = inWdxPetTotal;
						}else
							if(petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_HSD") || petPlayFinalType.equals("NC_DOUBLESIDE_" +ballNum+"_HSS")){
								cPetTotal = cHsdsPetTotal;
								outPetTotal  = outHsdsPetTotal;
								inPetTotal  = inHsdsPetTotal;
							}else
								if(petPlayFinalType.equals("NC_BALL_" +preName+"_DONG") || petPlayFinalType.equals("NC_BALL_" +preName+"_NAN") ||
										petPlayFinalType.equals("NC_BALL_" +preName+"_XI") || petPlayFinalType.equals("NC_BALL_" +preName+"_BEI")){
									cPetTotal = cFwPetTotal;
									outPetTotal = outFwPetTotal;
									inPetTotal = inFwPetTotal;
								}else
									if(petPlayFinalType.equals("NC_BALL_" +preName+"_Z") || petPlayFinalType.equals("NC_BALL_" +preName+"_F") ||
											petPlayFinalType.equals("NC_BALL_" +preName+"_B")){
										cPetTotal = cZfbPetTotal;
										outPetTotal = outZfbPetTotal;
										inPetTotal = inZfbPetTotal;
									}else{
										cPetTotal = cBallPetTotal;
										outPetTotal = outBallPetTotal;
										inPetTotal = inBallPetTotal;
									}
				
				pet = cPetTotal.subtract(vo.getOddsMoney());
				BigDecimal outM = BigDecimal.ZERO;
				BigDecimal inM = BigDecimal.ZERO;		
				if(rMapOut.get(vo.getPlayFinalType())!=null){
					outM = rMapOut.get(vo.getPlayFinalType()).subtract(outPetTotal);
				}else{
					outM = outM.subtract(outPetTotal);
				}
				if(rMapIn.get(vo.getPlayFinalType())!=null){
					inM = rMapIn.get(vo.getPlayFinalType()).subtract(inPetTotal);
				}else{
					inM = inM.subtract(inPetTotal);
				}
				petList.get(ii).setLoseMoney(pet.add(outM).subtract(inM));
			}	
		}
		
		return petList;
	}
	
	//重庆补货
	@Override
	public List<ReplenishVO> findReplenishPetList_CQ(ManagerUser userInfo,String plate,String periodsNum,String searchType) {
		BigDecimal ballPetTotal_1 = BigDecimal.ZERO;    //号球总投注额
		BigDecimal ballPetTotal_2 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_3 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_4 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_5 = BigDecimal.ZERO;
		BigDecimal dxPetTotal_1 = BigDecimal.ZERO;    //大小总投注额
		BigDecimal dxPetTotal_2 = BigDecimal.ZERO;
		BigDecimal dxPetTotal_3 = BigDecimal.ZERO;
		BigDecimal dxPetTotal_4 = BigDecimal.ZERO;
		BigDecimal dxPetTotal_5 = BigDecimal.ZERO;
		BigDecimal dsPetTotal_1 = BigDecimal.ZERO;    //单双总投注额
		BigDecimal dsPetTotal_2 = BigDecimal.ZERO;
		BigDecimal dsPetTotal_3 = BigDecimal.ZERO;
		BigDecimal dsPetTotal_4 = BigDecimal.ZERO;
		BigDecimal dsPetTotal_5 = BigDecimal.ZERO;
		BigDecimal zhdxPetTotal = BigDecimal.ZERO;    //总和大小总投注额
		BigDecimal zhdsPetTotal = BigDecimal.ZERO;    //合数单双总投注额
		BigDecimal lhPetTotal = BigDecimal.ZERO;    //龙虎和总投注额(龙虎)
		BigDecimal lhHePetTotal = BigDecimal.ZERO;    //龙虎和总投注额(和)
		BigDecimal frontPetTotal = BigDecimal.ZERO;    //前三总投注额
		BigDecimal midPetTotal = BigDecimal.ZERO;   //中三总投注额
		BigDecimal lastPetTotal = BigDecimal.ZERO;   //后三总投注额
		BigDecimal petTotal = BigDecimal.ZERO;   //统计总投注额
		//补出
		BigDecimal outballPetTotal_1 = BigDecimal.ZERO;    //号球补出货的总投注额
		BigDecimal outballPetTotal_2 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_3 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_4 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_5 = BigDecimal.ZERO;
		BigDecimal outdxPetTotal_1 = BigDecimal.ZERO;    //大小补出货的总投注额
		BigDecimal outdxPetTotal_2 = BigDecimal.ZERO;
		BigDecimal outdxPetTotal_3 = BigDecimal.ZERO;
		BigDecimal outdxPetTotal_4 = BigDecimal.ZERO;
		BigDecimal outdxPetTotal_5 = BigDecimal.ZERO;
		BigDecimal outdsPetTotal_1 = BigDecimal.ZERO;    //单双补出货的总投注额
		BigDecimal outdsPetTotal_2 = BigDecimal.ZERO;
		BigDecimal outdsPetTotal_3 = BigDecimal.ZERO;
		BigDecimal outdsPetTotal_4 = BigDecimal.ZERO;
		BigDecimal outdsPetTotal_5 = BigDecimal.ZERO;
		BigDecimal outzhdxPetTotal = BigDecimal.ZERO;    //总和大小补出货的总投注额
		BigDecimal outzhdsPetTotal = BigDecimal.ZERO;    //合数单双补出货的总投注额
		BigDecimal outlhPetTotal = BigDecimal.ZERO;    //龙虎和补出货的总投注额
		BigDecimal outlhHePetTotal = BigDecimal.ZERO;    //龙虎和补出货的总投注额(和)
		BigDecimal outfrontPetTotal = BigDecimal.ZERO;    //前三补出货的总投注额
		BigDecimal outmidPetTotal = BigDecimal.ZERO;   //中三补出货的总投注额
		BigDecimal outlastPetTotal = BigDecimal.ZERO;   //后三补出货的总投注额
		BigDecimal outpetTotal = BigDecimal.ZERO;   //统计补出货的总投注额
		//补进
		BigDecimal inballPetTotal_1 = BigDecimal.ZERO;    //号球补进货的总投注额
		BigDecimal inballPetTotal_2 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_3 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_4 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_5 = BigDecimal.ZERO;
		BigDecimal indxPetTotal_1 = BigDecimal.ZERO;    //大小总投注额
		BigDecimal indxPetTotal_2 = BigDecimal.ZERO;
		BigDecimal indxPetTotal_3 = BigDecimal.ZERO;
		BigDecimal indxPetTotal_4 = BigDecimal.ZERO;
		BigDecimal indxPetTotal_5 = BigDecimal.ZERO;
		BigDecimal indsPetTotal_1 = BigDecimal.ZERO;    //单双总投注额
		BigDecimal indsPetTotal_2 = BigDecimal.ZERO;
		BigDecimal indsPetTotal_3 = BigDecimal.ZERO;
		BigDecimal indsPetTotal_4 = BigDecimal.ZERO;
		BigDecimal indsPetTotal_5 = BigDecimal.ZERO;
		BigDecimal inzhdxPetTotal = BigDecimal.ZERO;    //总和大小总投注额
		BigDecimal inzhdsPetTotal = BigDecimal.ZERO;    //合数单双总投注额
		BigDecimal inlhPetTotal = BigDecimal.ZERO;    //龙虎和总投注额
		BigDecimal inlhHePetTotal = BigDecimal.ZERO;    //龙虎和补出货的总投注额(和)
		BigDecimal infrontPetTotal = BigDecimal.ZERO;    //前三总投注额
		BigDecimal inmidPetTotal = BigDecimal.ZERO;   //中三总投注额
		BigDecimal inlastPetTotal = BigDecimal.ZERO;   //后三总投注额
		BigDecimal inpetTotal = BigDecimal.ZERO;   //统计总投注额
		
		Map<String,BigDecimal> rMapOut = new HashMap<String,BigDecimal>();
		Map<String,BigDecimal> rMapIn = new HashMap<String,BigDecimal>();
		
		boolean isSys = userInfo.getUserType().equals( ManagerStaff.USER_TYPE_SYS);// 系统类型
		if (!isSys)// 系统管理员一般不操作
        {
			this.getUserType(userInfo);
			String typeCode = "%";
		    petList = replenishDao.queryTotal_CQ(userType, userInfo.getID(), plate, periodsNum, rateUser,commissionUser,typeCode);

		    this.findReplenishList(periodsNum, plate, "CQSSC_%",userInfo);
			
			this.totalReplenish(searchType,Constant.LOTTERY_TYPE_CQSSC,"%");
			
			for(ReplenishVO vo : replenishList){
				BigDecimal rOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						rOddsMoney = rOddsMoney.add(vo.getOddsMoney());
					}
				}
				rMapOut.put(vo.getPlayFinalType(), rOddsMoney);
			}
			//补进
			//统计每种玩法的补货的赔额
			for(ReplenishVO vo : replenishAccList){
				BigDecimal rOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishAccList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						rOddsMoney = rOddsMoney.add(vo.getOddsMoney());
					}
				}
				rMapIn.put(vo.getPlayFinalType(), rOddsMoney);
			}
		    		    
		    //计算投注总额	
			for(ReplenishVO vo : petList){
				String ballNum = "";
				String petPlayFinalType = vo.getPlayFinalType();
				if(petPlayFinalType.indexOf("1")!=-1){ballNum="1";}
				if(petPlayFinalType.indexOf("2")!=-1){ballNum="2";}
				if(petPlayFinalType.indexOf("3")!=-1){ballNum="3";}
				if(petPlayFinalType.indexOf("4")!=-1){ballNum="4";}
				if(petPlayFinalType.indexOf("5")!=-1){ballNum="5";}
				
				if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_DA") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_X")){
					if(ballNum.equals("1")){dxPetTotal_1 = dxPetTotal_1.add(vo.getCommissionMoney());}	
					if(ballNum.equals("2")){dxPetTotal_2 = dxPetTotal_2.add(vo.getCommissionMoney());}	
					if(ballNum.equals("3")){dxPetTotal_3 = dxPetTotal_3.add(vo.getCommissionMoney());}	
					if(ballNum.equals("4")){dxPetTotal_4 = dxPetTotal_4.add(vo.getCommissionMoney());}	
					if(ballNum.equals("5")){dxPetTotal_5 = dxPetTotal_5.add(vo.getCommissionMoney());}	
				}else 
					if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_DAN") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_S")){
						if(ballNum.equals("1")){dsPetTotal_1 = dsPetTotal_1.add(vo.getCommissionMoney());}	
						if(ballNum.equals("2")){dsPetTotal_2 = dsPetTotal_2.add(vo.getCommissionMoney());}	
						if(ballNum.equals("3")){dsPetTotal_3 = dsPetTotal_3.add(vo.getCommissionMoney());}	
						if(ballNum.equals("4")){dsPetTotal_4 = dsPetTotal_4.add(vo.getCommissionMoney());}	
						if(ballNum.equals("5")){dsPetTotal_5 = dsPetTotal_5.add(vo.getCommissionMoney());}						  
					}else 
						if(vo.getPlayFinalType().equals("CQSSC_DOUBLESIDE_HE")){									
						    lhHePetTotal = lhHePetTotal.add(vo.getCommissionMoney());
				        }
						if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHDA") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHX")){							
							   zhdxPetTotal = zhdxPetTotal.add(vo.getCommissionMoney());							
						}else
							if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHDAN") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHS")){
								    zhdsPetTotal = zhdsPetTotal.add(vo.getCommissionMoney());
							}else
								if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_LONG") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_HU") 
										   || vo.getPlayFinalType().equals("CQSSC_DOUBLESIDE_HE")){									
									    lhPetTotal = lhPetTotal.add(vo.getCommissionMoney());
								}else
									if(petPlayFinalType.equals("CQSSC_BZ_FRONT") || petPlayFinalType.equals("CQSSC_SZ_FRONT") ||
											petPlayFinalType.equals("CQSSC_DZ_FRONT") || petPlayFinalType.equals("CQSSC_BS_FRONT")
											|| vo.getPlayFinalType().equals("CQSSC_ZL_FRONT")){
										   frontPetTotal = frontPetTotal.add(vo.getCommissionMoney());
									}else
										if(petPlayFinalType.equals("CQSSC_BZ_MID") || petPlayFinalType.equals("CQSSC_SZ_MID") ||
												petPlayFinalType.equals("CQSSC_DZ_MID") || petPlayFinalType.equals("CQSSC_BS_MID")
												|| petPlayFinalType.equals("CQSSC_ZL_MID")){
											   midPetTotal = midPetTotal.add(vo.getCommissionMoney());
										}else
											if(petPlayFinalType.equals("CQSSC_BZ_LAST") || petPlayFinalType.equals("CQSSC_SZ_LAST") ||
													petPlayFinalType.equals("CQSSC_DZ_LAST") || petPlayFinalType.equals("CQSSC_BS_LAST")
													|| petPlayFinalType.equals("CQSSC_ZL_LAST")){
												   lastPetTotal = lastPetTotal.add(vo.getCommissionMoney());
											}else{
												if(petPlayFinalType.indexOf("FIRST")>-1){ballPetTotal_1 = ballPetTotal_1.add(vo.getCommissionMoney());}	
												if(petPlayFinalType.indexOf("SECOND")>-1){ballPetTotal_2 = ballPetTotal_2.add(vo.getCommissionMoney());}	
												if(petPlayFinalType.indexOf("THIRD")>-1){ballPetTotal_3 = ballPetTotal_3.add(vo.getCommissionMoney());}	
												if(petPlayFinalType.indexOf("FORTH")>-1){ballPetTotal_4 = ballPetTotal_4.add(vo.getCommissionMoney());}	
												if(petPlayFinalType.indexOf("FIFTH")>-1){ballPetTotal_5 = ballPetTotal_5.add(vo.getCommissionMoney());}		    
											}				
		 	}
			
			//统计补出的补货总额	
			for(ReplenishVO vo : replenishList){
				String ballNum = "";
				String petPlayFinalType = vo.getPlayFinalType();
				if(petPlayFinalType.indexOf("1")!=-1){ballNum="1";}
				if(petPlayFinalType.indexOf("2")!=-1){ballNum="2";}
				if(petPlayFinalType.indexOf("3")!=-1){ballNum="3";}
				if(petPlayFinalType.indexOf("4")!=-1){ballNum="4";}
				if(petPlayFinalType.indexOf("5")!=-1){ballNum="5";}
				
				if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_DA") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_X")){
					if(ballNum.equals("1")){outdxPetTotal_1 = outdxPetTotal_1.add(vo.getCommissionMoney());}	
					if(ballNum.equals("2")){outdxPetTotal_2 = outdxPetTotal_2.add(vo.getCommissionMoney());}	
					if(ballNum.equals("3")){outdxPetTotal_3 = outdxPetTotal_3.add(vo.getCommissionMoney());}	
					if(ballNum.equals("4")){outdxPetTotal_4 = outdxPetTotal_4.add(vo.getCommissionMoney());}	
					if(ballNum.equals("5")){outdxPetTotal_5 = outdxPetTotal_5.add(vo.getCommissionMoney());}	
				}else 
					if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_DAN") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_S")){
						if(ballNum.equals("1")){outdsPetTotal_1 = outdsPetTotal_1.add(vo.getCommissionMoney());}	
						if(ballNum.equals("2")){outdsPetTotal_2 = outdsPetTotal_2.add(vo.getCommissionMoney());}	
						if(ballNum.equals("3")){outdsPetTotal_3 = outdsPetTotal_3.add(vo.getCommissionMoney());}	
						if(ballNum.equals("4")){outdsPetTotal_4 = outdsPetTotal_4.add(vo.getCommissionMoney());}	
						if(ballNum.equals("5")){outdsPetTotal_5 = outdsPetTotal_5.add(vo.getCommissionMoney());}						  
					}else 
						if(vo.getPlayFinalType().equals("CQSSC_DOUBLESIDE_HE")){									
							outlhHePetTotal = outlhHePetTotal.add(vo.getCommissionMoney());
					    }
						if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHDA") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHX")){							
							outzhdxPetTotal = outzhdxPetTotal.add(vo.getCommissionMoney());							
						}else
							if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHDAN") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHS")){
								outzhdsPetTotal = outzhdsPetTotal.add(vo.getCommissionMoney());
							}else
								if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_LONG") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_HU")
										 || vo.getPlayFinalType().equals("CQSSC_DOUBLESIDE_HE")){									
									
									outlhPetTotal = outlhPetTotal.add(vo.getCommissionMoney());
								}else
									if(petPlayFinalType.equals("CQSSC_BZ_FRONT") || petPlayFinalType.equals("CQSSC_SZ_FRONT") ||
											petPlayFinalType.equals("CQSSC_DZ_FRONT") || petPlayFinalType.equals("CQSSC_BS_FRONT")
											|| vo.getPlayFinalType().equals("CQSSC_ZL_FRONT")){
										outfrontPetTotal = outfrontPetTotal.add(vo.getCommissionMoney());
									}else
										if(petPlayFinalType.equals("CQSSC_BZ_MID") || petPlayFinalType.equals("CQSSC_SZ_MID") ||
												petPlayFinalType.equals("CQSSC_DZ_MID") || petPlayFinalType.equals("CQSSC_BS_MID")
												|| petPlayFinalType.equals("CQSSC_ZL_MID")){
											outmidPetTotal = outmidPetTotal.add(vo.getCommissionMoney());
										}else
											if(petPlayFinalType.equals("CQSSC_BZ_LAST") || petPlayFinalType.equals("CQSSC_SZ_LAST") ||
													petPlayFinalType.equals("CQSSC_DZ_LAST") || petPlayFinalType.equals("CQSSC_BS_LAST")
													|| petPlayFinalType.equals("CQSSC_ZL_LAST")){
												outlastPetTotal = outlastPetTotal.add(vo.getCommissionMoney());
											}else{
												if(petPlayFinalType.indexOf("FIRST")>-1){outballPetTotal_1 = outballPetTotal_1.add(vo.getCommissionMoney());}	
												if(petPlayFinalType.indexOf("SECOND")>-1){outballPetTotal_2 = outballPetTotal_2.add(vo.getCommissionMoney());}	
												if(petPlayFinalType.indexOf("THIRD")>-1){outballPetTotal_3 = outballPetTotal_3.add(vo.getCommissionMoney());}	
												if(petPlayFinalType.indexOf("FORTH")>-1){outballPetTotal_4 = outballPetTotal_4.add(vo.getCommissionMoney());}	
												if(petPlayFinalType.indexOf("FIFTH")>-1){outballPetTotal_5 = outballPetTotal_5.add(vo.getCommissionMoney());}		    
											}				
		 	}
			
			//统计补进的补货总额	
			for(ReplenishVO vo : replenishAccList){
				String ballNum = "";
				String petPlayFinalType = vo.getPlayFinalType();
				if(petPlayFinalType.indexOf("1")!=-1){ballNum="1";}
				if(petPlayFinalType.indexOf("2")!=-1){ballNum="2";}
				if(petPlayFinalType.indexOf("3")!=-1){ballNum="3";}
				if(petPlayFinalType.indexOf("4")!=-1){ballNum="4";}
				if(petPlayFinalType.indexOf("5")!=-1){ballNum="5";}
				
				if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_DA") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_X")){
					if(ballNum.equals("1")){indxPetTotal_1 = indxPetTotal_1.add(vo.getCommissionMoney());}	
					if(ballNum.equals("2")){indxPetTotal_2 = indxPetTotal_2.add(vo.getCommissionMoney());}	
					if(ballNum.equals("3")){indxPetTotal_3 = indxPetTotal_3.add(vo.getCommissionMoney());}	
					if(ballNum.equals("4")){indxPetTotal_4 = indxPetTotal_4.add(vo.getCommissionMoney());}	
					if(ballNum.equals("5")){indxPetTotal_5 = indxPetTotal_5.add(vo.getCommissionMoney());}	
				}else 
					if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_DAN") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_S")){
						if(ballNum.equals("1")){indsPetTotal_1 = indsPetTotal_1.add(vo.getCommissionMoney());}	
						if(ballNum.equals("2")){indsPetTotal_2 = indsPetTotal_2.add(vo.getCommissionMoney());}	
						if(ballNum.equals("3")){indsPetTotal_3 = indsPetTotal_3.add(vo.getCommissionMoney());}	
						if(ballNum.equals("4")){indsPetTotal_4 = indsPetTotal_4.add(vo.getCommissionMoney());}	
						if(ballNum.equals("5")){indsPetTotal_5 = indsPetTotal_5.add(vo.getCommissionMoney());}						  
					}else 
						if(vo.getPlayFinalType().equals("CQSSC_DOUBLESIDE_HE")){									
							inlhHePetTotal = inlhHePetTotal.add(vo.getCommissionMoney());
					    }
						if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHDA") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHX")){							
							inzhdxPetTotal = inzhdxPetTotal.add(vo.getCommissionMoney());							
						}else
							if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHDAN") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHS")){
								inzhdsPetTotal = inzhdsPetTotal.add(vo.getCommissionMoney());
							}else
								if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_LONG") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_HU")
										 || vo.getPlayFinalType().equals("CQSSC_DOUBLESIDE_HE")){									
									inlhPetTotal = inlhPetTotal.add(vo.getCommissionMoney());
								}else
									if(petPlayFinalType.equals("CQSSC_BZ_FRONT") || petPlayFinalType.equals("CQSSC_SZ_FRONT") ||
											petPlayFinalType.equals("CQSSC_DZ_FRONT") || petPlayFinalType.equals("CQSSC_BS_FRONT")
											|| vo.getPlayFinalType().equals("CQSSC_ZL_FRONT")){
										infrontPetTotal = infrontPetTotal.add(vo.getCommissionMoney());
									}else
										if(petPlayFinalType.equals("CQSSC_BZ_MID") || petPlayFinalType.equals("CQSSC_SZ_MID") ||
												petPlayFinalType.equals("CQSSC_DZ_MID") || petPlayFinalType.equals("CQSSC_BS_MID")
												|| petPlayFinalType.equals("CQSSC_ZL_MID")){
											inmidPetTotal = inmidPetTotal.add(vo.getCommissionMoney());
										}else
											if(petPlayFinalType.equals("CQSSC_BZ_LAST") || petPlayFinalType.equals("CQSSC_SZ_LAST") ||
													petPlayFinalType.equals("CQSSC_DZ_LAST") || petPlayFinalType.equals("CQSSC_BS_LAST")
													|| petPlayFinalType.equals("CQSSC_ZL_LAST")){
												inlastPetTotal = inlastPetTotal.add(vo.getCommissionMoney());
											}else{
												if(petPlayFinalType.indexOf("FIRST")>-1){inballPetTotal_1 = inballPetTotal_1.add(vo.getCommissionMoney());}	
												if(petPlayFinalType.indexOf("SECOND")>-1){inballPetTotal_2 = inballPetTotal_2.add(vo.getCommissionMoney());}	
												if(petPlayFinalType.indexOf("THIRD")>-1){inballPetTotal_3 = inballPetTotal_3.add(vo.getCommissionMoney());}	
												if(petPlayFinalType.indexOf("FORTH")>-1){inballPetTotal_4 = inballPetTotal_4.add(vo.getCommissionMoney());}	
												if(petPlayFinalType.indexOf("FIFTH")>-1){inballPetTotal_5 = inballPetTotal_5.add(vo.getCommissionMoney());}		    
											}				
		 	}
			
			//计算亏盈
            for(int ii=0; ii<petList.size(); ii++){
				BigDecimal pet = BigDecimal.valueOf(0);
				ReplenishVO vo = petList.get(ii);
				String ballNum = "";
				String petPlayFinalType = vo.getPlayFinalType();
				if(petPlayFinalType.indexOf("1")!=-1){ballNum="1";}
				if(petPlayFinalType.indexOf("2")!=-1){ballNum="2";}
				if(petPlayFinalType.indexOf("3")!=-1){ballNum="3";}
				if(petPlayFinalType.indexOf("4")!=-1){ballNum="4";}
				if(petPlayFinalType.indexOf("5")!=-1){ballNum="5";}
				
				if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_DA") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_X")){
					if(ballNum.equals("1")){petTotal = dxPetTotal_1;outpetTotal = outdxPetTotal_1;inpetTotal = indxPetTotal_1;}	
					if(ballNum.equals("2")){petTotal = dxPetTotal_2;outpetTotal = outdxPetTotal_2;inpetTotal = indxPetTotal_2;}	
					if(ballNum.equals("3")){petTotal = dxPetTotal_3;outpetTotal = outdxPetTotal_3;inpetTotal = indxPetTotal_3;}	
					if(ballNum.equals("4")){petTotal = dxPetTotal_4;outpetTotal = outdxPetTotal_4;inpetTotal = indxPetTotal_4;}	
					if(ballNum.equals("5")){petTotal = dxPetTotal_5;outpetTotal = outdxPetTotal_5;inpetTotal = indxPetTotal_5;}	
					
					if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_DA")){
						vo.setSortNo(1);
					}else{
						vo.setSortNo(2);
					}
				}else
					if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_DAN") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_S")){
						if(ballNum.equals("1")){petTotal = dsPetTotal_1;outpetTotal = outdsPetTotal_1;inpetTotal = indsPetTotal_1;}	
						if(ballNum.equals("2")){petTotal = dsPetTotal_2;outpetTotal = outdsPetTotal_2;inpetTotal = indsPetTotal_2;}	
						if(ballNum.equals("3")){petTotal = dsPetTotal_3;outpetTotal = outdsPetTotal_3;inpetTotal = indsPetTotal_3;}	
						if(ballNum.equals("4")){petTotal = dsPetTotal_4;outpetTotal = outdsPetTotal_4;inpetTotal = indsPetTotal_4;}	
						if(ballNum.equals("5")){petTotal = dsPetTotal_5;outpetTotal = outdsPetTotal_5;inpetTotal = indsPetTotal_5;}	
						if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_" +ballNum+"_DAN")){
							vo.setSortNo(3);
						}else{
							vo.setSortNo(4);
						}
					}else
						if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHDA") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHX")){
							petTotal = zhdxPetTotal;outpetTotal = outzhdxPetTotal;inpetTotal = inzhdxPetTotal;
							if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHDA")){
								vo.setSortNo(1);
							}else{
								vo.setSortNo(2);
							}
						}else
							if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHDAN") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHS")){
								petTotal = zhdsPetTotal;outpetTotal = outzhdsPetTotal;inpetTotal = inzhdsPetTotal;
								if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_ZHDAN")){
									vo.setSortNo(3);
								}else{
									vo.setSortNo(4);
								}
							}else
								if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_LONG") || petPlayFinalType.equals("CQSSC_DOUBLESIDE_HU")
										|| petPlayFinalType.equals("CQSSC_DOUBLESIDE_HE")){
									petTotal = lhPetTotal;outpetTotal = outlhPetTotal;inpetTotal = inlhPetTotal;
									if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_LONG")){
										vo.setSortNo(5);
									}else if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_HU")){
										vo.setSortNo(6);
									}else{
										vo.setSortNo(7);
										
									}
								}else
									if(petPlayFinalType.equals("CQSSC_BZ_FRONT") || petPlayFinalType.equals("CQSSC_SZ_FRONT") ||
											petPlayFinalType.equals("CQSSC_DZ_FRONT") || petPlayFinalType.equals("CQSSC_BS_FRONT")
											|| petPlayFinalType.equals("CQSSC_ZL_FRONT")){
										petTotal = frontPetTotal;outpetTotal = outfrontPetTotal;inpetTotal = infrontPetTotal;
										if(petPlayFinalType.equals("CQSSC_BZ_FRONT")){
											vo.setSortNo(1);
										}else if(petPlayFinalType.equals("CQSSC_SZ_FRONT")){
											vo.setSortNo(2);
										}else if(petPlayFinalType.equals("CQSSC_DZ_FRONT")){
											vo.setSortNo(3);
										}else if(petPlayFinalType.equals("CQSSC_BS_FRONT")){
											vo.setSortNo(4);
										}else{
											vo.setSortNo(5);
										}
											
									}else
										if(petPlayFinalType.equals("CQSSC_BZ_MID") || petPlayFinalType.equals("CQSSC_SZ_MID") ||
												petPlayFinalType.equals("CQSSC_DZ_MID") || petPlayFinalType.equals("CQSSC_BS_MID")
												|| petPlayFinalType.equals("CQSSC_ZL_MID")){
											petTotal = midPetTotal;outpetTotal = outmidPetTotal;inpetTotal = inmidPetTotal;
											if(petPlayFinalType.equals("CQSSC_BZ_MID")){
												vo.setSortNo(1);
											}else if(petPlayFinalType.equals("CQSSC_SZ_MID")){
												vo.setSortNo(2);
											}else if(petPlayFinalType.equals("CQSSC_DZ_MID")){
												vo.setSortNo(3);
											}else if(petPlayFinalType.equals("CQSSC_BS_MID")){
												vo.setSortNo(4);
											}else{
												vo.setSortNo(5);
											}
										}else
											if(petPlayFinalType.equals("CQSSC_BZ_LAST") || petPlayFinalType.equals("CQSSC_SZ_LAST") ||
													petPlayFinalType.equals("CQSSC_DZ_LAST") || petPlayFinalType.equals("CQSSC_BS_LAST")
													|| petPlayFinalType.equals("CQSSC_ZL_LAST")){
												petTotal = lastPetTotal;outpetTotal = outlastPetTotal;inpetTotal = inlastPetTotal;
												if(petPlayFinalType.equals("CQSSC_BZ_LAST")){
													vo.setSortNo(1);
												}else if(petPlayFinalType.equals("CQSSC_SZ_LAST")){
													vo.setSortNo(2);
												}else if(petPlayFinalType.equals("CQSSC_DZ_LAST")){
													vo.setSortNo(3);
												}else if(petPlayFinalType.equals("CQSSC_BS_LAST")){
													vo.setSortNo(4);
												}else{
													vo.setSortNo(5);
												}
											}else{
												if(petPlayFinalType.indexOf("FIRST")!=-1){petTotal = ballPetTotal_1;outpetTotal = outballPetTotal_1;inpetTotal = inballPetTotal_1;}	
												if(petPlayFinalType.indexOf("SECOND")!=-1){petTotal = ballPetTotal_2;outpetTotal = outballPetTotal_2;inpetTotal = inballPetTotal_2;}	
												if(petPlayFinalType.indexOf("THIRD")!=-1){petTotal = ballPetTotal_3;outpetTotal = outballPetTotal_3;inpetTotal = inballPetTotal_3;}	
												if(petPlayFinalType.indexOf("FORTH")!=-1){petTotal = ballPetTotal_4;outpetTotal = outballPetTotal_4;inpetTotal = inballPetTotal_4;}	
												if(petPlayFinalType.indexOf("FIFTH")!=-1){petTotal = ballPetTotal_5;outpetTotal = outballPetTotal_5;inpetTotal = inballPetTotal_5;}	
												//因为排在前的4个，所以这里截取最后一个数字再加上5，就是排列序号
												vo.setSortNo(Integer.valueOf(petPlayFinalType.substring(petPlayFinalType.length()-1,petPlayFinalType.length()))+5);
												System.out.println();
											}
				if(petPlayFinalType.equals("CQSSC_DOUBLESIDE_HE")){
					petTotal = lhHePetTotal;outpetTotal = outlhHePetTotal;inpetTotal = inlhHePetTotal;
					vo.setSortNo(7);
				}
				pet = petTotal.subtract(vo.getOddsMoney());
				BigDecimal outM = BigDecimal.ZERO;
				BigDecimal inM = BigDecimal.ZERO;		
				if(rMapOut.get(vo.getPlayFinalType())!=null){
					outM = rMapOut.get(vo.getPlayFinalType()).subtract(outpetTotal);
				}else{
					outM = outM.subtract(outpetTotal);
				}
				if(rMapIn.get(vo.getPlayFinalType())!=null){
					inM = rMapIn.get(vo.getPlayFinalType()).subtract(inpetTotal);
				}else{
					inM = inM.subtract(inpetTotal);
				}
				petList.get(ii).setLoseMoney(pet.add(outM).subtract(inM));
            }	
		}
					
		return petList;
	}
	
	//北京补货
	@Override
	public List<ReplenishVO> findReplenishPetList_BJ(ManagerUser userInfo,String plate,String periodsNum,String searchType) {
		BigDecimal ballPetTotal_1 = BigDecimal.ZERO;    //号球总投注额
		BigDecimal ballPetTotal_2 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_group = BigDecimal.ZERO; //冠、亞軍和 指定
		BigDecimal dxPetTotal_1 = BigDecimal.ZERO;    //大小总投注额
		BigDecimal dxPetTotal_2 = BigDecimal.ZERO;
		BigDecimal dxPetTotal_double = BigDecimal.ZERO;//冠亞軍和 兩面 大小
		BigDecimal dsPetTotal_1 = BigDecimal.ZERO;    //单双总投注额
		BigDecimal dsPetTotal_2 = BigDecimal.ZERO;
		BigDecimal dsPetTotal_double = BigDecimal.ZERO;//冠亞軍和 兩面 單雙
		BigDecimal lhPetTotal_1 = BigDecimal.ZERO;//龙虎
		BigDecimal lhPetTotal_2 = BigDecimal.ZERO;//龙虎
		BigDecimal petTotal = BigDecimal.ZERO;   //统计总投注额
		//补出
		BigDecimal outballPetTotal_1 = BigDecimal.ZERO;    //号球补出货的总投注额
		BigDecimal outballPetTotal_2 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_group = BigDecimal.ZERO;
		BigDecimal outdxPetTotal_1 = BigDecimal.ZERO;    //大小补出货的总投注额
		BigDecimal outdxPetTotal_2 = BigDecimal.ZERO;
		BigDecimal outdxPetTotal_double = BigDecimal.ZERO;
		BigDecimal outdsPetTotal_1 = BigDecimal.ZERO;    //单双补出货的总投注额
		BigDecimal outdsPetTotal_2 = BigDecimal.ZERO;
		BigDecimal outlhPetTotal_1 = BigDecimal.ZERO;    //龙虎
		BigDecimal outlhPetTotal_2 = BigDecimal.ZERO;
		BigDecimal outdsPetTotal_double = BigDecimal.ZERO;
		BigDecimal outpetTotal = BigDecimal.ZERO;   //统计补出货的总投注额
		//补进
		BigDecimal inballPetTotal_1 = BigDecimal.ZERO;    //号球补进货的总投注额
		BigDecimal inballPetTotal_2 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_group = BigDecimal.ZERO;
		BigDecimal indxPetTotal_1 = BigDecimal.ZERO;    //大小总投注额
		BigDecimal indxPetTotal_2 = BigDecimal.ZERO;
		BigDecimal indxPetTotal_double = BigDecimal.ZERO;
		BigDecimal indsPetTotal_1 = BigDecimal.ZERO;    //单双总投注额
		BigDecimal indsPetTotal_2 = BigDecimal.ZERO;
		BigDecimal inlhPetTotal_1 = BigDecimal.ZERO;    //龙虎
		BigDecimal inlhPetTotal_2 = BigDecimal.ZERO;
		BigDecimal indsPetTotal_double = BigDecimal.ZERO;
		BigDecimal inpetTotal = BigDecimal.ZERO;   //统计总投注额
		
		Map<String,BigDecimal> rMapOut = new HashMap<String,BigDecimal>();
		Map<String,BigDecimal> rMapIn = new HashMap<String,BigDecimal>();
		
		boolean isSys = userInfo.getUserType().equals( ManagerStaff.USER_TYPE_SYS);// 系统类型
		if (!isSys)// 系统管理员一般不操作
		{
			this.getUserType(userInfo);
			//String typeCode = "BJ%";
			petList = replenishDao.queryTotal_BJ(userType, userInfo.getID(), plate, periodsNum, rateUser,commissionUser);
			
			this.findReplenishList(periodsNum, plate, "BJ%",userInfo);
			
			this.totalReplenish(searchType,Constant.LOTTERY_TYPE_BJ,"%");
			
			for(ReplenishVO vo : replenishList){
				BigDecimal rOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						rOddsMoney = rOddsMoney.add(vo.getOddsMoney());
					}
				}
				rMapOut.put(vo.getPlayFinalType(), rOddsMoney);
			}
			//补进
			//统计每种玩法的补货的赔额
			for(ReplenishVO vo : replenishAccList){
				BigDecimal rOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishAccList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						rOddsMoney = rOddsMoney.add(vo.getOddsMoney());
					}
				}
				rMapIn.put(vo.getPlayFinalType(), rOddsMoney);
			}
			
			//计算投注总额	
			for(ReplenishVO vo : petList){
				String petPlayFinalType = vo.getPlayFinalType();
				
				if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_DA") || petPlayFinalType.equals("BJ_DOUBLESIDE_1_X")){
					dxPetTotal_1 = dxPetTotal_1.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_DAN") || petPlayFinalType.equals("BJ_DOUBLESIDE_1_S")){
						dsPetTotal_1 = dsPetTotal_1.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_DA") || petPlayFinalType.equals("BJ_DOUBLESIDE_2_X")){
						dxPetTotal_2 = dxPetTotal_2.add(vo.getCommissionMoney());
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_DAN") || petPlayFinalType.equals("BJ_DOUBLESIDE_2_S")){
					    dsPetTotal_2 = dsPetTotal_2.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.indexOf("BJ_BALL_FIRST")!=-1){
					    ballPetTotal_1 = ballPetTotal_1.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.indexOf("BJ_BALL_SECOND")!=-1){
						ballPetTotal_2 = ballPetTotal_2.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.indexOf("BJ_GROUP")!=-1){
					ballPetTotal_group = ballPetTotal_group.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_DA")){
					dxPetTotal_double = dxPetTotal_double.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_X")){
					dxPetTotal_double = dxPetTotal_double.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_DAN")){
					dsPetTotal_double = dsPetTotal_double.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_S")){
					dsPetTotal_double = dsPetTotal_double.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_LONG")){
					lhPetTotal_1 = lhPetTotal_1.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_HU")){
					lhPetTotal_1 = lhPetTotal_1.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_LONG")){
					lhPetTotal_2 = lhPetTotal_2.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_HU")){
					lhPetTotal_2 = lhPetTotal_2.add(vo.getCommissionMoney());	
				}
			}
			
			//统计补出的补货总额	
			for(ReplenishVO vo : replenishList){
				String petPlayFinalType = vo.getPlayFinalType();
				
				if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_DA") || petPlayFinalType.equals("BJ_DOUBLESIDE_1_X")){
					outdxPetTotal_1 = outdxPetTotal_1.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_DAN") || petPlayFinalType.equals("BJ_DOUBLESIDE_1_S")){
					outdsPetTotal_1 = outdsPetTotal_1.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_DA") || petPlayFinalType.equals("BJ_DOUBLESIDE_2_X")){
					outdxPetTotal_2 = outdxPetTotal_2.add(vo.getCommissionMoney());
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_DAN") || petPlayFinalType.equals("BJ_DOUBLESIDE_2_S")){
					outdsPetTotal_2 = outdsPetTotal_2.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.indexOf("BJ_BALL_FIRST")!=-1){
					outballPetTotal_1 = outballPetTotal_1.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.indexOf("BJ_BALL_SECOND")!=-1){
					outballPetTotal_2 = outballPetTotal_2.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.indexOf("BJ_GROUP")!=-1){
					outballPetTotal_group = outballPetTotal_group.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_DA")){
					outdxPetTotal_double = outdxPetTotal_double.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_X")){
					outdxPetTotal_double = outdxPetTotal_double.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_DAN")){
					outdsPetTotal_double = outdsPetTotal_double.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_S")){
					outdsPetTotal_double = outdsPetTotal_double.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_LONG")){
					outlhPetTotal_1 = outlhPetTotal_1.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_HU")){
					outlhPetTotal_1 = outlhPetTotal_1.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_LONG")){
					outlhPetTotal_2 = outlhPetTotal_2.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_HU")){
					outlhPetTotal_2 = outlhPetTotal_2.add(vo.getCommissionMoney());	
				}
			}
			
			//统计补进的补货总额	
			for(ReplenishVO vo : replenishAccList){
				String petPlayFinalType = vo.getPlayFinalType();
				
				if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_DA") || petPlayFinalType.equals("BJ_DOUBLESIDE_1_X")){
					indxPetTotal_1 = indxPetTotal_1.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_DAN") || petPlayFinalType.equals("BJ_DOUBLESIDE_1_S")){
					indsPetTotal_1 = indsPetTotal_1.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_DA") || petPlayFinalType.equals("BJ_DOUBLESIDE_2_X")){
					indxPetTotal_2 = indxPetTotal_2.add(vo.getCommissionMoney());
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_DAN") || petPlayFinalType.equals("BJ_DOUBLESIDE_2_S")){
					indsPetTotal_2 = indsPetTotal_2.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.indexOf("BJ_BALL_FIRST")!=-1){
					inballPetTotal_1 = inballPetTotal_1.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.indexOf("BJ_BALL_SECOND")!=-1){
					inballPetTotal_2 = inballPetTotal_2.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.indexOf("BJ_GROUP")!=-1){
					inballPetTotal_group = inballPetTotal_group.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_DA")){
					indxPetTotal_double = indxPetTotal_double.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_X")){
					indxPetTotal_double = indxPetTotal_double.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_DAN")){
					indsPetTotal_double = indsPetTotal_double.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_S")){
					indsPetTotal_double = indsPetTotal_double.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_LONG")){
					inlhPetTotal_1 = inlhPetTotal_1.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_HU")){
					inlhPetTotal_1 = inlhPetTotal_1.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_LONG")){
					inlhPetTotal_2 = inlhPetTotal_2.add(vo.getCommissionMoney());	
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_HU")){
					inlhPetTotal_2 = inlhPetTotal_2.add(vo.getCommissionMoney());	
				}
			}
			
			//计算亏盈
			for(int ii=0; ii<petList.size(); ii++){
				BigDecimal pet = BigDecimal.valueOf(0);
				ReplenishVO vo = petList.get(ii);
				String petPlayFinalType = vo.getPlayFinalType();
				if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_DA") || petPlayFinalType.equals("BJ_DOUBLESIDE_1_X")){
					petTotal = dxPetTotal_1;outpetTotal = outdxPetTotal_1;inpetTotal = indxPetTotal_1;
					
					if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_DA")){
						vo.setSortNo(1);
					}else{
						vo.setSortNo(2);
					}
				}else 
					if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_DAN") || petPlayFinalType.equals("BJ_DOUBLESIDE_1_S")){
						petTotal = dsPetTotal_1;outpetTotal = outdsPetTotal_1;inpetTotal = indsPetTotal_1;
						if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_DAN")){
							vo.setSortNo(3);
						}else{
							vo.setSortNo(4);
						}
					}else
						if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_LONG") || petPlayFinalType.equals("BJ_DOUBLESIDE_1_HU")){
							petTotal = lhPetTotal_1;outpetTotal = outlhPetTotal_1;inpetTotal = inlhPetTotal_1;
							if(petPlayFinalType.equals("BJ_DOUBLESIDE_1_LONG")){
								vo.setSortNo(5);
							}else{
								vo.setSortNo(6);
								
							}
						}else
							if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_DA") || petPlayFinalType.equals("BJ_DOUBLESIDE_2_X")){
								petTotal = dxPetTotal_2;outpetTotal = outdxPetTotal_2;inpetTotal = indxPetTotal_2;
								
								if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_DA")){
									vo.setSortNo(1);
								}else{
									vo.setSortNo(2);
								}
							}else 
								if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_DAN") || petPlayFinalType.equals("BJ_DOUBLESIDE_2_S")){
									petTotal = dsPetTotal_2;outpetTotal = outdsPetTotal_2;inpetTotal = indsPetTotal_2;
									if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_DAN")){
										vo.setSortNo(3);
									}else{
										vo.setSortNo(4);
									}
								}else
									if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_LONG") || petPlayFinalType.equals("BJ_DOUBLESIDE_2_HU")){
										petTotal = lhPetTotal_2;outpetTotal = outlhPetTotal_2;inpetTotal = inlhPetTotal_2;
										if(petPlayFinalType.equals("BJ_DOUBLESIDE_2_LONG")){
											vo.setSortNo(5);
										}else{
											vo.setSortNo(6);
											
										}
									}else
										if(petPlayFinalType.indexOf("BJ_GROUP")!=-1){
											petTotal = ballPetTotal_group;outpetTotal = outballPetTotal_group;inpetTotal = inballPetTotal_group;
										}else 
											if(petPlayFinalType.equals("BJ_DOUBLESIDE_DA") || petPlayFinalType.equals("BJ_DOUBLESIDE_X")){
												petTotal = dxPetTotal_double;outpetTotal = outdxPetTotal_double;inpetTotal = indxPetTotal_double;
												
												if(petPlayFinalType.equals("BJ_DOUBLESIDE_DA")){
													vo.setSortNo(1);
												}else{
													vo.setSortNo(2);
												}
											}else 
												if(petPlayFinalType.equals("BJ_DOUBLESIDE_DAN") || petPlayFinalType.equals("BJ_DOUBLESIDE_S")){
													petTotal = dsPetTotal_double;outpetTotal = outdsPetTotal_double;inpetTotal = indsPetTotal_double;
													if(petPlayFinalType.equals("BJ_DOUBLESIDE_DAN")){
														vo.setSortNo(3);
													}else{
														vo.setSortNo(4);
													}
												}else{
													//因为排在前的4个，所以这里截取最后一个数字再加上6，就是排列序号
													if(petPlayFinalType.indexOf("FIRST")!=-1){
														petTotal = ballPetTotal_1;outpetTotal = outballPetTotal_1;inpetTotal = inballPetTotal_1;
														vo.setSortNo(Integer.valueOf(vo.getPlayTypeName())+6);
													}	
													if(petPlayFinalType.indexOf("SECOND")!=-1){
														petTotal = ballPetTotal_2;outpetTotal = outballPetTotal_2;inpetTotal = inballPetTotal_2;
														vo.setSortNo(Integer.valueOf(vo.getPlayTypeName())+6);
													}	
									}
				pet = petTotal.subtract(vo.getOddsMoney());
				BigDecimal outM = BigDecimal.ZERO;
				BigDecimal inM = BigDecimal.ZERO;		
				if(rMapOut.get(vo.getPlayFinalType())!=null){
					outM = rMapOut.get(vo.getPlayFinalType()).subtract(outpetTotal);
				}else{
					outM = outM.subtract(outpetTotal);
				}
				if(rMapIn.get(vo.getPlayFinalType())!=null){
					inM = rMapIn.get(vo.getPlayFinalType()).subtract(inpetTotal);
				}else{
					inM = inM.subtract(inpetTotal);
				}
				vo.setLoseMoney(pet.add(outM).subtract(inM));
			}	
		}
		
		return petList;
	}
	
	//北京其他两个界面补货
	/**
	 * type "secondForm":第三到第六名，"threeForm":第七到第十名
	 */
	@Override
	public List<ReplenishVO> findReplenishPetList_BJ_Other(ManagerUser userInfo,String plate,String periodsNum,String searchType,String type) {
		BigDecimal ballPetTotal_1 = BigDecimal.ZERO;    //号球总投注额
		BigDecimal ballPetTotal_2 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_3 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_4 = BigDecimal.ZERO;
		
		BigDecimal dxPetTotal_1 = BigDecimal.ZERO;    //大小总投注额
		BigDecimal dxPetTotal_2 = BigDecimal.ZERO;
		BigDecimal dxPetTotal_3 = BigDecimal.ZERO;
		BigDecimal dxPetTotal_4 = BigDecimal.ZERO;
		
		BigDecimal dsPetTotal_1 = BigDecimal.ZERO;    //单双总投注额
		BigDecimal dsPetTotal_2 = BigDecimal.ZERO;
		BigDecimal dsPetTotal_3 = BigDecimal.ZERO;
		BigDecimal dsPetTotal_4 = BigDecimal.ZERO;
		
		BigDecimal lhPetTotal_1 = BigDecimal.ZERO;//龙虎
		BigDecimal lhPetTotal_2 = BigDecimal.ZERO;
		BigDecimal lhPetTotal_3 = BigDecimal.ZERO;
		
		BigDecimal petTotal = BigDecimal.ZERO;   //统计总投注额
		//补出
		BigDecimal outballPetTotal_1 = BigDecimal.ZERO;    //号球补出货的总投注额
		BigDecimal outballPetTotal_2 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_3 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_4 = BigDecimal.ZERO;

		BigDecimal outdxPetTotal_1 = BigDecimal.ZERO;    //大小补出货的总投注额
		BigDecimal outdxPetTotal_2 = BigDecimal.ZERO;
		BigDecimal outdxPetTotal_3 = BigDecimal.ZERO;
		BigDecimal outdxPetTotal_4 = BigDecimal.ZERO;

		BigDecimal outdsPetTotal_1 = BigDecimal.ZERO;    //单双补出货的总投注额
		BigDecimal outdsPetTotal_2 = BigDecimal.ZERO;
		BigDecimal outdsPetTotal_3 = BigDecimal.ZERO;
		BigDecimal outdsPetTotal_4 = BigDecimal.ZERO;
		
		BigDecimal outlhPetTotal_1 = BigDecimal.ZERO;    //龙虎
		BigDecimal outlhPetTotal_2 = BigDecimal.ZERO;
		BigDecimal outlhPetTotal_3 = BigDecimal.ZERO;
		
		BigDecimal outpetTotal = BigDecimal.ZERO;   //统计补出货的总投注额
		//补进
		BigDecimal inballPetTotal_1 = BigDecimal.ZERO;    //号球补进货的总投注额
		BigDecimal inballPetTotal_2 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_3 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_4 = BigDecimal.ZERO;
		
		BigDecimal indxPetTotal_1 = BigDecimal.ZERO;    //大小总投注额
		BigDecimal indxPetTotal_2 = BigDecimal.ZERO;
		BigDecimal indxPetTotal_3 = BigDecimal.ZERO;
		BigDecimal indxPetTotal_4 = BigDecimal.ZERO;
		
		BigDecimal indsPetTotal_1 = BigDecimal.ZERO;    //单双总投注额
		BigDecimal indsPetTotal_2 = BigDecimal.ZERO;
		BigDecimal indsPetTotal_3 = BigDecimal.ZERO;
		BigDecimal indsPetTotal_4 = BigDecimal.ZERO;
		
		BigDecimal inlhPetTotal_1 = BigDecimal.ZERO;    //龙虎
		BigDecimal inlhPetTotal_2 = BigDecimal.ZERO;
		BigDecimal inlhPetTotal_3 = BigDecimal.ZERO;
		
		BigDecimal inpetTotal = BigDecimal.ZERO;   //统计总投注额
		
		Map<String,BigDecimal> rMapOut = new HashMap<String,BigDecimal>();
		Map<String,BigDecimal> rMapIn = new HashMap<String,BigDecimal>();
		
		boolean isSys = userInfo.getUserType().equals( ManagerStaff.USER_TYPE_SYS);// 系统类型
		if (!isSys)// 系统管理员一般不操作
		{
			this.getUserType(userInfo);
			//String typeCode = "BJ%";
			petList = replenishDao.queryTotal_BJ(userType, userInfo.getID(), plate, periodsNum, rateUser,commissionUser);
			
			this.findReplenishList(periodsNum, plate, "BJ%",userInfo);
			
			this.totalReplenish(searchType,Constant.LOTTERY_TYPE_BJ,"%");
			
			for(ReplenishVO vo : replenishList){
				BigDecimal rOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						rOddsMoney = rOddsMoney.add(vo.getOddsMoney());
					}
				}
				rMapOut.put(vo.getPlayFinalType(), rOddsMoney);
			}
			//补进
			//统计每种玩法的补货的赔额
			for(ReplenishVO vo : replenishAccList){
				BigDecimal rOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishAccList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						rOddsMoney = rOddsMoney.add(vo.getOddsMoney());
					}
				}
				rMapIn.put(vo.getPlayFinalType(), rOddsMoney);
			}
			
			//计算投注总额	
			for(ReplenishVO vo : petList){
				String ballNum = "";
				String petPlayFinalType = vo.getPlayFinalType();
				
				if("secondForm".equals(type)){
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_3")!=-1){ballNum="3";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_4")!=-1){ballNum="4";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_5")!=-1){ballNum="5";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_6")!=-1){ballNum="6";}
				}else{
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_7")!=-1){ballNum="7";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_8")!=-1){ballNum="8";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_9")!=-1){ballNum="9";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_10")!=-1){ballNum="10";}
				}
				
				if(petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_DA") || petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_X")){
					if(ballNum.equals("3") || ballNum.equals("7")){
						dxPetTotal_1 = dxPetTotal_1.add(vo.getCommissionMoney());
					}else if(ballNum.equals("4") || ballNum.equals("8")){
						dxPetTotal_2 = dxPetTotal_2.add(vo.getCommissionMoney());
					}else if(ballNum.equals("5") || ballNum.equals("9")){
						dxPetTotal_3 = dxPetTotal_3.add(vo.getCommissionMoney());
					}else if(ballNum.equals("6") || ballNum.equals("10")){
						dxPetTotal_4 = dxPetTotal_4.add(vo.getCommissionMoney());
					}
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_DAN") || petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_S")){
					if(ballNum.equals("3") || ballNum.equals("7")){
						dsPetTotal_1 = dsPetTotal_1.add(vo.getCommissionMoney());
					}else if(ballNum.equals("4") || ballNum.equals("8")){
						dsPetTotal_2 = dsPetTotal_2.add(vo.getCommissionMoney());
					}else if(ballNum.equals("5") || ballNum.equals("9")){
						dsPetTotal_3 = dsPetTotal_3.add(vo.getCommissionMoney());
					}else if(ballNum.equals("6") || ballNum.equals("10")){
						dsPetTotal_4 = dsPetTotal_4.add(vo.getCommissionMoney());
					}
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_LONG") || petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_HU")){
					if(ballNum.equals("3")){
						lhPetTotal_1 = lhPetTotal_1.add(vo.getCommissionMoney());	
					}else if(ballNum.equals("4")){
						lhPetTotal_2 = lhPetTotal_2.add(vo.getCommissionMoney());	
					}else if(ballNum.equals("5")){
						lhPetTotal_3 = lhPetTotal_3.add(vo.getCommissionMoney());	
					}
				}else{
					if("secondForm".equals(type)){
						if(petPlayFinalType.indexOf("THIRD")!=-1){ballPetTotal_1 = ballPetTotal_1.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("FORTH")!=-1){ballPetTotal_2 = ballPetTotal_2.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("FIFTH")!=-1){ballPetTotal_3 = ballPetTotal_3.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("SIXTH")!=-1){ballPetTotal_4 = ballPetTotal_4.add(vo.getCommissionMoney());}
					}else{
						if(petPlayFinalType.indexOf("SEVENTH")!=-1){ballPetTotal_1 = ballPetTotal_1.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("EIGHTH")!=-1){ballPetTotal_2 = ballPetTotal_2.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("NINTH")!=-1){ballPetTotal_3 = ballPetTotal_3.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("TENTH")!=-1){ballPetTotal_4 = ballPetTotal_4.add(vo.getCommissionMoney());}
						
					}
				}
			}
			
			//统计补出的补货总额	
			for(ReplenishVO vo : replenishList){
				String ballNum = "";
				String petPlayFinalType = vo.getPlayFinalType();
				
				if("secondForm".equals(type)){
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_3")!=-1){ballNum="3";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_4")!=-1){ballNum="4";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_5")!=-1){ballNum="5";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_6")!=-1){ballNum="6";}
				}else{
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_7")!=-1){ballNum="7";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_8")!=-1){ballNum="8";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_9")!=-1){ballNum="9";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_10")!=-1){ballNum="10";}
				}
				
				if(petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_DA") || petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_X")){
					if(ballNum.equals("3") || ballNum.equals("7")){
						outdxPetTotal_1 = outdxPetTotal_1.add(vo.getCommissionMoney());
					}else if(ballNum.equals("4") || ballNum.equals("8")){
						outdxPetTotal_2 = outdxPetTotal_2.add(vo.getCommissionMoney());
					}else if(ballNum.equals("5") || ballNum.equals("9")){
						outdxPetTotal_3 = outdxPetTotal_3.add(vo.getCommissionMoney());
					}else if(ballNum.equals("6") || ballNum.equals("10")){
						outdxPetTotal_4 = outdxPetTotal_4.add(vo.getCommissionMoney());
					}
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_DAN") || petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_S")){
					if(ballNum.equals("3") || ballNum.equals("7")){
						outdsPetTotal_1 = outdsPetTotal_1.add(vo.getCommissionMoney());
					}else if(ballNum.equals("4") || ballNum.equals("8")){
						outdsPetTotal_2 = outdsPetTotal_2.add(vo.getCommissionMoney());
					}else if(ballNum.equals("5") || ballNum.equals("9")){
						outdsPetTotal_3 = outdsPetTotal_3.add(vo.getCommissionMoney());
					}else if(ballNum.equals("6") || ballNum.equals("10")){
						outdsPetTotal_4 = outdsPetTotal_4.add(vo.getCommissionMoney());
					}
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_LONG") || petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_HU")){
					if(ballNum.equals("3")){
						outlhPetTotal_1 = outlhPetTotal_1.add(vo.getCommissionMoney());	
					}else if(ballNum.equals("4")){
						outlhPetTotal_2 = outlhPetTotal_2.add(vo.getCommissionMoney());	
					}else if(ballNum.equals("5")){
						outlhPetTotal_3 = outlhPetTotal_3.add(vo.getCommissionMoney());	
					}
				}else{
					if("secondForm".equals(type)){
						if(petPlayFinalType.indexOf("THIRD")!=-1){outballPetTotal_1 = outballPetTotal_1.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("FORTH")!=-1){outballPetTotal_2 = outballPetTotal_2.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("FIFTH")!=-1){outballPetTotal_3 = outballPetTotal_3.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("SIXTH")!=-1){outballPetTotal_4 = outballPetTotal_4.add(vo.getCommissionMoney());}
					}else{
						if(petPlayFinalType.indexOf("SEVENTH")!=-1){outballPetTotal_1 = outballPetTotal_1.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("EIGHTH")!=-1){outballPetTotal_2 = outballPetTotal_2.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("NINTH")!=-1){outballPetTotal_3 = outballPetTotal_3.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("TENTH")!=-1){outballPetTotal_4 = outballPetTotal_4.add(vo.getCommissionMoney());}
					}
				}
			}
			
			//统计补进的补货总额	
			for(ReplenishVO vo : replenishAccList){
				String ballNum = "";
				String petPlayFinalType = vo.getPlayFinalType();
				
				if("secondForm".equals(type)){
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_3")!=-1){ballNum="3";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_4")!=-1){ballNum="4";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_5")!=-1){ballNum="5";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_6")!=-1){ballNum="6";}
				}else{
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_7")!=-1){ballNum="7";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_8")!=-1){ballNum="8";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_9")!=-1){ballNum="9";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_10")!=-1){ballNum="10";}
				}
				
				if(petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_DA") || petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_X")){
					if(ballNum.equals("3") || ballNum.equals("7")){
						indxPetTotal_1 = indxPetTotal_1.add(vo.getCommissionMoney());
					}else if(ballNum.equals("4") || ballNum.equals("8")){
						indxPetTotal_2 = indxPetTotal_2.add(vo.getCommissionMoney());
					}else if(ballNum.equals("5") || ballNum.equals("9")){
						indxPetTotal_3 = indxPetTotal_3.add(vo.getCommissionMoney());
					}else if(ballNum.equals("6") || ballNum.equals("10")){
						indxPetTotal_4 = indxPetTotal_4.add(vo.getCommissionMoney());
					}
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_DAN") || petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_S")){
					if(ballNum.equals("3") || ballNum.equals("7")){
						indsPetTotal_1 = indsPetTotal_1.add(vo.getCommissionMoney());
					}else if(ballNum.equals("4") || ballNum.equals("8")){
						indsPetTotal_2 = indsPetTotal_2.add(vo.getCommissionMoney());
					}else if(ballNum.equals("5") || ballNum.equals("9")){
						indsPetTotal_3 = indsPetTotal_3.add(vo.getCommissionMoney());
					}else if(ballNum.equals("6") || ballNum.equals("10")){
						indsPetTotal_4 = indsPetTotal_4.add(vo.getCommissionMoney());
					}
				}else if(petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_LONG") || petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_HU")){
					if(ballNum.equals("3")){
						inlhPetTotal_1 = inlhPetTotal_1.add(vo.getCommissionMoney());	
					}else if(ballNum.equals("4")){
						inlhPetTotal_2 = inlhPetTotal_2.add(vo.getCommissionMoney());	
					}else if(ballNum.equals("5")){
						inlhPetTotal_3 = inlhPetTotal_3.add(vo.getCommissionMoney());	
					}
				}else{
					if("secondForm".equals(type)){
						if(petPlayFinalType.indexOf("THIRD")!=-1){inballPetTotal_1 = inballPetTotal_1.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("FORTH")!=-1){inballPetTotal_2 = inballPetTotal_2.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("FIFTH")!=-1){inballPetTotal_3 = inballPetTotal_3.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("SIXTH")!=-1){inballPetTotal_4 = inballPetTotal_4.add(vo.getCommissionMoney());}
					}else{
						if(petPlayFinalType.indexOf("SEVENTH")!=-1){inballPetTotal_1 = inballPetTotal_1.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("EIGHTH")!=-1){inballPetTotal_2 = inballPetTotal_2.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("NINTH")!=-1){inballPetTotal_3 = inballPetTotal_3.add(vo.getCommissionMoney());}
						if(petPlayFinalType.indexOf("TENTH")!=-1){inballPetTotal_4 = inballPetTotal_4.add(vo.getCommissionMoney());}
					}
				}
				
			}
			
			//计算亏盈
			for(ReplenishVO vo : petList){
				String ballNum = "";
				String petPlayFinalType = vo.getPlayFinalType();
				
				if("secondForm".equals(type)){
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_3")!=-1){ballNum="3";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_4")!=-1){ballNum="4";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_5")!=-1){ballNum="5";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_6")!=-1){ballNum="6";}
				}else{
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_7")!=-1){ballNum="7";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_8")!=-1){ballNum="8";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_9")!=-1){ballNum="9";}
					if(petPlayFinalType.indexOf("BJ_DOUBLESIDE_10")!=-1){ballNum="10";}
				}
				
				if(petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_DA") || petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_X")){
					if(ballNum.equals("3") || ballNum.equals("7")){
						petTotal = dxPetTotal_1;outpetTotal = outdxPetTotal_1;inpetTotal = indxPetTotal_1;
					}else if(ballNum.equals("4") || ballNum.equals("8")){
						petTotal = dxPetTotal_2;outpetTotal = outdxPetTotal_2;inpetTotal = indxPetTotal_2;
					}else if(ballNum.equals("5") || ballNum.equals("9")){
						petTotal = dxPetTotal_3;outpetTotal = outdxPetTotal_3;inpetTotal = indxPetTotal_3;
					}else if(ballNum.equals("6") || ballNum.equals("10")){
						petTotal = dxPetTotal_4;outpetTotal = outdxPetTotal_4;inpetTotal = indxPetTotal_4;
					}
					
					if(petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_DA")){
						vo.setSortNo(1);
					}else{
						vo.setSortNo(2);
					}
				}else 
					if(petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_DAN") || petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_S")){
						if(ballNum.equals("3") || ballNum.equals("7")){
							petTotal = dsPetTotal_1;outpetTotal = outdsPetTotal_1;inpetTotal = indsPetTotal_1;
						}else if(ballNum.equals("4") || ballNum.equals("8")){
							petTotal = dsPetTotal_2;outpetTotal = outdsPetTotal_2;inpetTotal = indsPetTotal_2;
						}else if(ballNum.equals("5") || ballNum.equals("9")){
							petTotal = dsPetTotal_3;outpetTotal = outdsPetTotal_3;inpetTotal = indsPetTotal_3;
						}else if(ballNum.equals("6") || ballNum.equals("10")){
							petTotal = dsPetTotal_4;outpetTotal = outdsPetTotal_4;inpetTotal = indsPetTotal_4;
						}
						
						if(petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_DAN")){
							vo.setSortNo(3);
						}else{
							vo.setSortNo(4);
						}
					}else
						if(petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_LONG") || petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_HU")){
							if(ballNum.equals("3")){
								petTotal = lhPetTotal_1;outpetTotal = outlhPetTotal_1;inpetTotal = inlhPetTotal_1;
							}else if(ballNum.equals("4")){
								petTotal = lhPetTotal_2;outpetTotal = outlhPetTotal_2;inpetTotal = inlhPetTotal_2;	
							}else if(ballNum.equals("5")){
								petTotal = lhPetTotal_3;outpetTotal = outlhPetTotal_3;inpetTotal = inlhPetTotal_3;	
							}
							
							if(petPlayFinalType.equals("BJ_DOUBLESIDE_"+ballNum+"_LONG")){
								vo.setSortNo(5);
							}else{
								vo.setSortNo(6);
								
							}
						}else
							//因为排在前的4个，所以这里截取最后一个数字再加上6，就是排列序号,而第6名后因为少了龙虎所以加5就行了。
							if("secondForm".equals(type)){
								if(petPlayFinalType.indexOf("THIRD")!=-1){
									petTotal = ballPetTotal_1;outpetTotal = outballPetTotal_1;inpetTotal = inballPetTotal_1;
									vo.setSortNo(Integer.valueOf(vo.getPlayTypeName())+6);
								}
								if(petPlayFinalType.indexOf("FORTH")!=-1){
									petTotal = ballPetTotal_2;outpetTotal = outballPetTotal_2;inpetTotal = inballPetTotal_2;
									vo.setSortNo(Integer.valueOf(vo.getPlayTypeName())+6);
								}
								if(petPlayFinalType.indexOf("FIFTH")!=-1){
									petTotal = ballPetTotal_3;outpetTotal = outballPetTotal_3;inpetTotal = inballPetTotal_3;
									vo.setSortNo(Integer.valueOf(vo.getPlayTypeName())+6);
								}
								if(petPlayFinalType.indexOf("SIXTH")!=-1){
									petTotal = ballPetTotal_4;outpetTotal = outballPetTotal_4;inpetTotal = inballPetTotal_4;
									vo.setSortNo(Integer.valueOf(vo.getPlayTypeName())+5);
								}
								
							}else{
								if(petPlayFinalType.indexOf("SEVENTH")!=-1){
									petTotal = ballPetTotal_1;outpetTotal = outballPetTotal_1;inpetTotal = inballPetTotal_1;
									vo.setSortNo(Integer.valueOf(vo.getPlayTypeName())+5);
								}
								if(petPlayFinalType.indexOf("EIGHTH")!=-1){
									petTotal = ballPetTotal_2;outpetTotal = outballPetTotal_2;inpetTotal = inballPetTotal_2;
									vo.setSortNo(Integer.valueOf(vo.getPlayTypeName())+5);
								}
								if(petPlayFinalType.indexOf("NINTH")!=-1){
									petTotal = ballPetTotal_3;outpetTotal = outballPetTotal_3;inpetTotal = inballPetTotal_3;
									vo.setSortNo(Integer.valueOf(vo.getPlayTypeName())+5);
								}
								if(petPlayFinalType.indexOf("TENTH")!=-1){
									petTotal = ballPetTotal_4;outpetTotal = outballPetTotal_4;inpetTotal = inballPetTotal_4;
									vo.setSortNo(Integer.valueOf(vo.getPlayTypeName())+5);
								}
								
							}
							
				BigDecimal pet = BigDecimal.valueOf(0);
				pet = petTotal.subtract(vo.getOddsMoney());
				BigDecimal outM = BigDecimal.ZERO;
				BigDecimal inM = BigDecimal.ZERO;		
				if(rMapOut.get(vo.getPlayFinalType())!=null){
					outM = rMapOut.get(vo.getPlayFinalType()).subtract(outpetTotal);
				}else{
					outM = outM.subtract(outpetTotal);
				}
				if(rMapIn.get(vo.getPlayFinalType())!=null){
					inM = rMapIn.get(vo.getPlayFinalType()).subtract(inpetTotal);
				}else{
					inM = inM.subtract(inpetTotal);
				}
				vo.setLoseMoney(pet.add(outM).subtract(inM));
				
			}	
		}
		
		return petList;
	}
	
	/**k3界面补货
	 * 
	 */
	@Override
	public List<ReplenishVO> findReplenishPetList_K3(ManagerUser userInfo,String plate,String periodsNum,String searchType,String type) {
		BigDecimal ballPetTotal_sj_1 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_sj_2 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_sj_3 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_sj_4 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_sj_5 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_sj_6 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_dx = BigDecimal.ZERO;
		BigDecimal ballPetTotal_qs = BigDecimal.ZERO;
		BigDecimal ballPetTotal_ws = BigDecimal.ZERO;
		BigDecimal ballPetTotal_ds = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_12 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_13 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_14 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_15 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_16 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_23 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_24 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_25 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_26 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_34 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_35 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_36 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_45 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_46 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_cp_56 = BigDecimal.ZERO;
		BigDecimal ballPetTotal_dp = BigDecimal.ZERO;
		
		BigDecimal petTotal = BigDecimal.ZERO;   //统计总投注额
		//补出
		BigDecimal outballPetTotal_sj_1 = BigDecimal.ZERO;    //补出货的总投注额
		BigDecimal outballPetTotal_sj_2 = BigDecimal.ZERO;    //补出货的总投注额
		BigDecimal outballPetTotal_sj_3 = BigDecimal.ZERO;    //补出货的总投注额
		BigDecimal outballPetTotal_sj_4 = BigDecimal.ZERO;    //补出货的总投注额
		BigDecimal outballPetTotal_sj_5 = BigDecimal.ZERO;    //补出货的总投注额
		BigDecimal outballPetTotal_sj_6 = BigDecimal.ZERO;    //补出货的总投注额
		BigDecimal outballPetTotal_dx = BigDecimal.ZERO;
		BigDecimal outballPetTotal_qs = BigDecimal.ZERO;
		BigDecimal outballPetTotal_ws = BigDecimal.ZERO;
		BigDecimal outballPetTotal_ds = BigDecimal.ZERO;
		BigDecimal outballPetTotal_cp_12 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_cp_13 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_cp_14 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_cp_15 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_cp_16 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_cp_23 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_cp_24 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_cp_25 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_cp_26 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_cp_34 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_cp_35 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_cp_36 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_cp_45 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_cp_46 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_cp_56 = BigDecimal.ZERO;
		BigDecimal outballPetTotal_dp = BigDecimal.ZERO;
		
		BigDecimal outpetTotal = BigDecimal.ZERO;   //统计补出货的总投注额
		//补进
		BigDecimal inballPetTotal_sj_1 = BigDecimal.ZERO;    //补进货的总投注额
		BigDecimal inballPetTotal_sj_2 = BigDecimal.ZERO;    //补进货的总投注额
		BigDecimal inballPetTotal_sj_3 = BigDecimal.ZERO;    //补进货的总投注额
		BigDecimal inballPetTotal_sj_4 = BigDecimal.ZERO;    //补进货的总投注额
		BigDecimal inballPetTotal_sj_5 = BigDecimal.ZERO;    //补进货的总投注额
		BigDecimal inballPetTotal_sj_6 = BigDecimal.ZERO;    //补进货的总投注额
		BigDecimal inballPetTotal_dx = BigDecimal.ZERO;
		BigDecimal inballPetTotal_qs = BigDecimal.ZERO;
		BigDecimal inballPetTotal_ws = BigDecimal.ZERO;
		BigDecimal inballPetTotal_ds = BigDecimal.ZERO;
		BigDecimal inballPetTotal_cp_12 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_cp_13 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_cp_14 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_cp_15 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_cp_16 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_cp_23 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_cp_24 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_cp_25 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_cp_26 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_cp_34 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_cp_35 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_cp_36 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_cp_45 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_cp_46 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_cp_56 = BigDecimal.ZERO;
		BigDecimal inballPetTotal_dp = BigDecimal.ZERO;
		
		BigDecimal inpetTotal = BigDecimal.ZERO;   //统计总投注额
		
		Map<String,BigDecimal> rMapOut = new HashMap<String,BigDecimal>();
		Map<String,BigDecimal> rMapIn = new HashMap<String,BigDecimal>();
		
		boolean isSys = userInfo.getUserType().equals( ManagerStaff.USER_TYPE_SYS);// 系统类型
		if (!isSys)// 系统管理员一般不操作
		{
			this.getUserType(userInfo);
			petList = replenishDao.queryTotal_K3(userType, userInfo.getID(), plate, periodsNum, rateUser,commissionUser);
			
			this.findReplenishList(periodsNum, plate, "K3%",userInfo);
			
			this.totalReplenish(searchType,Constant.LOTTERY_TYPE_K3,"%");
			
			for(ReplenishVO vo : replenishList){
				BigDecimal rOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						rOddsMoney = rOddsMoney.add(vo.getOddsMoney());
					}
				}
				rMapOut.put(vo.getPlayFinalType(), rOddsMoney);
			}
			//补进
			//统计每种玩法的补货的赔额
			for(ReplenishVO vo : replenishAccList){
				BigDecimal rOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishAccList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						rOddsMoney = rOddsMoney.add(vo.getOddsMoney());
					}
				}
				rMapIn.put(vo.getPlayFinalType(), rOddsMoney);
			}
			
			//计算投注总额	
			for(ReplenishVO vo : petList){
				String petPlayFinalType = vo.getPlayFinalType();
				
				if(petPlayFinalType.indexOf("K3_SJ_")!=-1){
					if("K3_SJ_1".equals(petPlayFinalType)) ballPetTotal_sj_1 = ballPetTotal_sj_1.add(vo.getCommissionMoney());
					if("K3_SJ_2".equals(petPlayFinalType)) ballPetTotal_sj_2 = ballPetTotal_sj_2.add(vo.getCommissionMoney());
					if("K3_SJ_3".equals(petPlayFinalType)) ballPetTotal_sj_3 = ballPetTotal_sj_3.add(vo.getCommissionMoney());
					if("K3_SJ_4".equals(petPlayFinalType)) ballPetTotal_sj_4 = ballPetTotal_sj_4.add(vo.getCommissionMoney());
					if("K3_SJ_5".equals(petPlayFinalType)) ballPetTotal_sj_5 = ballPetTotal_sj_5.add(vo.getCommissionMoney());
					if("K3_SJ_6".equals(petPlayFinalType)) ballPetTotal_sj_6 = ballPetTotal_sj_6.add(vo.getCommissionMoney());
					
				}
				if("K3_DA".equals(petPlayFinalType) || "K3_X".equals(petPlayFinalType)){ballPetTotal_dx = ballPetTotal_dx.add(vo.getCommissionMoney());}
				if("K3_QS".equals(petPlayFinalType)){ballPetTotal_qs = ballPetTotal_qs.add(vo.getCommissionMoney());}
				if(petPlayFinalType.indexOf("K3_WS_")!=-1){ballPetTotal_ws = ballPetTotal_ws.add(vo.getCommissionMoney());}
				if(petPlayFinalType.indexOf("K3_DS_")!=-1){ballPetTotal_ds = ballPetTotal_ds.add(vo.getCommissionMoney());}
				if(petPlayFinalType.indexOf("K3_CP_")!=-1){
					if("K3_CP_1_2".equals(petPlayFinalType)) ballPetTotal_cp_12 = ballPetTotal_cp_12.add(vo.getCommissionMoney());
					if("K3_CP_1_3".equals(petPlayFinalType)) ballPetTotal_cp_13 = ballPetTotal_cp_13.add(vo.getCommissionMoney());
					if("K3_CP_1_4".equals(petPlayFinalType)) ballPetTotal_cp_14 = ballPetTotal_cp_14.add(vo.getCommissionMoney());
					if("K3_CP_1_5".equals(petPlayFinalType)) ballPetTotal_cp_15 = ballPetTotal_cp_15.add(vo.getCommissionMoney());
					if("K3_CP_1_6".equals(petPlayFinalType)) ballPetTotal_cp_16 = ballPetTotal_cp_16.add(vo.getCommissionMoney());
					if("K3_CP_2_3".equals(petPlayFinalType)) ballPetTotal_cp_23 = ballPetTotal_cp_23.add(vo.getCommissionMoney());
					if("K3_CP_2_4".equals(petPlayFinalType)) ballPetTotal_cp_24 = ballPetTotal_cp_24.add(vo.getCommissionMoney());
					if("K3_CP_2_5".equals(petPlayFinalType)) ballPetTotal_cp_25 = ballPetTotal_cp_25.add(vo.getCommissionMoney());
					if("K3_CP_2_6".equals(petPlayFinalType)) ballPetTotal_cp_26 = ballPetTotal_cp_26.add(vo.getCommissionMoney());
					if("K3_CP_3_4".equals(petPlayFinalType)) ballPetTotal_cp_34 = ballPetTotal_cp_34.add(vo.getCommissionMoney());
					if("K3_CP_3_5".equals(petPlayFinalType)) ballPetTotal_cp_35 = ballPetTotal_cp_35.add(vo.getCommissionMoney());
					if("K3_CP_3_6".equals(petPlayFinalType)) ballPetTotal_cp_36 = ballPetTotal_cp_36.add(vo.getCommissionMoney());
					if("K3_CP_4_5".equals(petPlayFinalType)) ballPetTotal_cp_45 = ballPetTotal_cp_45.add(vo.getCommissionMoney());
					if("K3_CP_4_6".equals(petPlayFinalType)) ballPetTotal_cp_46 = ballPetTotal_cp_46.add(vo.getCommissionMoney());
					if("K3_CP_5_6".equals(petPlayFinalType)) ballPetTotal_cp_56 = ballPetTotal_cp_56.add(vo.getCommissionMoney());
				}
				if(petPlayFinalType.indexOf("K3_DP_")!=-1){ballPetTotal_dp = ballPetTotal_dp.add(vo.getCommissionMoney());}
			}
			
			//统计补出的补货总额	
			for(ReplenishVO vo : replenishList){
				String petPlayFinalType = vo.getPlayFinalType();
				
				if(petPlayFinalType.indexOf("K3_SJ_")!=-1){
					if("K3_SJ_1".equals(petPlayFinalType)) outballPetTotal_sj_1 = outballPetTotal_sj_1.add(vo.getCommissionMoney());
					if("K3_SJ_2".equals(petPlayFinalType)) outballPetTotal_sj_2 = outballPetTotal_sj_2.add(vo.getCommissionMoney());
					if("K3_SJ_3".equals(petPlayFinalType)) outballPetTotal_sj_3 = outballPetTotal_sj_3.add(vo.getCommissionMoney());
					if("K3_SJ_4".equals(petPlayFinalType)) outballPetTotal_sj_4 = outballPetTotal_sj_4.add(vo.getCommissionMoney());
					if("K3_SJ_5".equals(petPlayFinalType)) outballPetTotal_sj_5 = outballPetTotal_sj_5.add(vo.getCommissionMoney());
					if("K3_SJ_6".equals(petPlayFinalType)) outballPetTotal_sj_6 = outballPetTotal_sj_6.add(vo.getCommissionMoney());
					}
				if("K3_DA".equals(petPlayFinalType) || "K3_X".equals(petPlayFinalType)){outballPetTotal_dx = outballPetTotal_dx.add(vo.getCommissionMoney());}
				if("K3_QS".equals(petPlayFinalType)){outballPetTotal_qs = outballPetTotal_qs.add(vo.getCommissionMoney());}
				if(petPlayFinalType.indexOf("K3_WS_")!=-1){outballPetTotal_ws = outballPetTotal_ws.add(vo.getCommissionMoney());}
				if(petPlayFinalType.indexOf("K3_DS_")!=-1){outballPetTotal_ds = outballPetTotal_ds.add(vo.getCommissionMoney());}
				if(petPlayFinalType.indexOf("K3_CP_")!=-1){
					if("K3_CP_1_2".equals(petPlayFinalType)) outballPetTotal_cp_12 = outballPetTotal_cp_12.add(vo.getCommissionMoney());
					if("K3_CP_1_3".equals(petPlayFinalType)) outballPetTotal_cp_13 = outballPetTotal_cp_13.add(vo.getCommissionMoney());
					if("K3_CP_1_4".equals(petPlayFinalType)) outballPetTotal_cp_14 = outballPetTotal_cp_14.add(vo.getCommissionMoney());
					if("K3_CP_1_5".equals(petPlayFinalType)) outballPetTotal_cp_15 = outballPetTotal_cp_15.add(vo.getCommissionMoney());
					if("K3_CP_1_6".equals(petPlayFinalType)) outballPetTotal_cp_16 = outballPetTotal_cp_16.add(vo.getCommissionMoney());
					if("K3_CP_2_3".equals(petPlayFinalType)) outballPetTotal_cp_23 = outballPetTotal_cp_23.add(vo.getCommissionMoney());
					if("K3_CP_2_4".equals(petPlayFinalType)) outballPetTotal_cp_24 = outballPetTotal_cp_24.add(vo.getCommissionMoney());
					if("K3_CP_2_5".equals(petPlayFinalType)) outballPetTotal_cp_25 = outballPetTotal_cp_25.add(vo.getCommissionMoney());
					if("K3_CP_2_6".equals(petPlayFinalType)) outballPetTotal_cp_26 = outballPetTotal_cp_26.add(vo.getCommissionMoney());
					if("K3_CP_3_4".equals(petPlayFinalType)) outballPetTotal_cp_34 = outballPetTotal_cp_34.add(vo.getCommissionMoney());
					if("K3_CP_3_5".equals(petPlayFinalType)) outballPetTotal_cp_35 = outballPetTotal_cp_35.add(vo.getCommissionMoney());
					if("K3_CP_3_6".equals(petPlayFinalType)) outballPetTotal_cp_36 = outballPetTotal_cp_36.add(vo.getCommissionMoney());
					if("K3_CP_4_5".equals(petPlayFinalType)) outballPetTotal_cp_45 = outballPetTotal_cp_45.add(vo.getCommissionMoney());
					if("K3_CP_4_6".equals(petPlayFinalType)) outballPetTotal_cp_46 = outballPetTotal_cp_46.add(vo.getCommissionMoney());
					if("K3_CP_5_6".equals(petPlayFinalType)) outballPetTotal_cp_56 = outballPetTotal_cp_56.add(vo.getCommissionMoney());
					}
				if(petPlayFinalType.indexOf("K3_DP_")!=-1){outballPetTotal_dp = outballPetTotal_dp.add(vo.getCommissionMoney());}
			}
			
			//统计补进的补货总额	
			for(ReplenishVO vo : replenishAccList){
				String petPlayFinalType = vo.getPlayFinalType();
				
				if(petPlayFinalType.indexOf("K3_SJ_")!=-1){
					if("K3_SJ_1".equals(petPlayFinalType)) inballPetTotal_sj_1 = inballPetTotal_sj_1.add(vo.getCommissionMoney());
					if("K3_SJ_2".equals(petPlayFinalType)) inballPetTotal_sj_2 = inballPetTotal_sj_2.add(vo.getCommissionMoney());
					if("K3_SJ_3".equals(petPlayFinalType)) inballPetTotal_sj_3 = inballPetTotal_sj_3.add(vo.getCommissionMoney());
					if("K3_SJ_4".equals(petPlayFinalType)) inballPetTotal_sj_4 = inballPetTotal_sj_4.add(vo.getCommissionMoney());
					if("K3_SJ_5".equals(petPlayFinalType)) inballPetTotal_sj_5 = inballPetTotal_sj_5.add(vo.getCommissionMoney());
					if("K3_SJ_6".equals(petPlayFinalType)) inballPetTotal_sj_6 = inballPetTotal_sj_6.add(vo.getCommissionMoney());
					}
				if("K3_DA".equals(petPlayFinalType) || "K3_X".equals(petPlayFinalType)){inballPetTotal_dx = inballPetTotal_dx.add(vo.getCommissionMoney());}
				if("K3_QS".equals(petPlayFinalType)){inballPetTotal_qs = inballPetTotal_qs.add(vo.getCommissionMoney());}
				if(petPlayFinalType.indexOf("K3_WS_")!=-1){inballPetTotal_ws = inballPetTotal_ws.add(vo.getCommissionMoney());}
				if(petPlayFinalType.indexOf("K3_DS_")!=-1){inballPetTotal_ds = inballPetTotal_ds.add(vo.getCommissionMoney());}
				if(petPlayFinalType.indexOf("K3_CP_")!=-1){
					if("K3_CP_1_2".equals(petPlayFinalType)) inballPetTotal_cp_12 = inballPetTotal_cp_12.add(vo.getCommissionMoney());
					if("K3_CP_1_3".equals(petPlayFinalType)) inballPetTotal_cp_13 = inballPetTotal_cp_13.add(vo.getCommissionMoney());
					if("K3_CP_1_4".equals(petPlayFinalType)) inballPetTotal_cp_14 = inballPetTotal_cp_14.add(vo.getCommissionMoney());
					if("K3_CP_1_5".equals(petPlayFinalType)) inballPetTotal_cp_15 = inballPetTotal_cp_15.add(vo.getCommissionMoney());
					if("K3_CP_1_6".equals(petPlayFinalType)) inballPetTotal_cp_16 = inballPetTotal_cp_16.add(vo.getCommissionMoney());
					if("K3_CP_2_3".equals(petPlayFinalType)) inballPetTotal_cp_23 = inballPetTotal_cp_23.add(vo.getCommissionMoney());
					if("K3_CP_2_4".equals(petPlayFinalType)) inballPetTotal_cp_24 = inballPetTotal_cp_24.add(vo.getCommissionMoney());
					if("K3_CP_2_5".equals(petPlayFinalType)) inballPetTotal_cp_25 = inballPetTotal_cp_25.add(vo.getCommissionMoney());
					if("K3_CP_2_6".equals(petPlayFinalType)) inballPetTotal_cp_26 = inballPetTotal_cp_26.add(vo.getCommissionMoney());
					if("K3_CP_3_4".equals(petPlayFinalType)) inballPetTotal_cp_34 = inballPetTotal_cp_34.add(vo.getCommissionMoney());
					if("K3_CP_3_5".equals(petPlayFinalType)) inballPetTotal_cp_35 = inballPetTotal_cp_35.add(vo.getCommissionMoney());
					if("K3_CP_3_6".equals(petPlayFinalType)) inballPetTotal_cp_36 = inballPetTotal_cp_36.add(vo.getCommissionMoney());
					if("K3_CP_4_5".equals(petPlayFinalType)) inballPetTotal_cp_45 = inballPetTotal_cp_45.add(vo.getCommissionMoney());
					if("K3_CP_4_6".equals(petPlayFinalType)) inballPetTotal_cp_46 = inballPetTotal_cp_46.add(vo.getCommissionMoney());
					if("K3_CP_5_6".equals(petPlayFinalType)) inballPetTotal_cp_56 = inballPetTotal_cp_56.add(vo.getCommissionMoney());
				}
				if(petPlayFinalType.indexOf("K3_DP_")!=-1){inballPetTotal_dp = inballPetTotal_dp.add(vo.getCommissionMoney());}
			}
			
			//计算亏盈
			for(ReplenishVO vo : petList){
				String petPlayFinalType = vo.getPlayFinalType();
				
				if(petPlayFinalType.indexOf("K3_SJ_")!=-1){
					if("K3_SJ_1".equals(petPlayFinalType)){ petTotal = ballPetTotal_sj_1;outpetTotal = outballPetTotal_sj_1;inpetTotal = inballPetTotal_sj_1;}
					if("K3_SJ_2".equals(petPlayFinalType)){ petTotal = ballPetTotal_sj_2;outpetTotal = outballPetTotal_sj_2;inpetTotal = inballPetTotal_sj_2;}
					if("K3_SJ_3".equals(petPlayFinalType)){ petTotal = ballPetTotal_sj_3;outpetTotal = outballPetTotal_sj_3;inpetTotal = inballPetTotal_sj_3;}
					if("K3_SJ_4".equals(petPlayFinalType)){ petTotal = ballPetTotal_sj_4;outpetTotal = outballPetTotal_sj_4;inpetTotal = inballPetTotal_sj_4;}
					if("K3_SJ_5".equals(petPlayFinalType)){ petTotal = ballPetTotal_sj_5;outpetTotal = outballPetTotal_sj_5;inpetTotal = inballPetTotal_sj_5;}
					if("K3_SJ_6".equals(petPlayFinalType)){ petTotal = ballPetTotal_sj_6;outpetTotal = outballPetTotal_sj_6;inpetTotal = inballPetTotal_sj_6;}
				}
				if("K3_DA".equals(petPlayFinalType) || "K3_X".equals(petPlayFinalType)){
					petTotal = ballPetTotal_dx;outpetTotal = outballPetTotal_dx;inpetTotal = inballPetTotal_dx;
				}
				if("K3_QS".equals(petPlayFinalType)){
					petTotal = ballPetTotal_qs;outpetTotal = outballPetTotal_qs;inpetTotal = inballPetTotal_qs;
				}
				if(petPlayFinalType.indexOf("K3_WS_")!=-1){
					petTotal = ballPetTotal_ws;outpetTotal = outballPetTotal_ws;inpetTotal = inballPetTotal_ws;
				}
				if(petPlayFinalType.indexOf("K3_DS_")!=-1){
					petTotal = ballPetTotal_ds;outpetTotal = outballPetTotal_ds;inpetTotal = inballPetTotal_ds;
				}
				if(petPlayFinalType.indexOf("K3_CP_")!=-1){
					if("K3_CP_1_2".equals(petPlayFinalType)){ petTotal = ballPetTotal_cp_12;outpetTotal = outballPetTotal_cp_12;inpetTotal = inballPetTotal_cp_12;}
					if("K3_CP_1_3".equals(petPlayFinalType)){ petTotal = ballPetTotal_cp_13;outpetTotal = outballPetTotal_cp_13;inpetTotal = inballPetTotal_cp_13;}
					if("K3_CP_1_4".equals(petPlayFinalType)){ petTotal = ballPetTotal_cp_14;outpetTotal = outballPetTotal_cp_14;inpetTotal = inballPetTotal_cp_14;}
					if("K3_CP_1_5".equals(petPlayFinalType)){ petTotal = ballPetTotal_cp_15;outpetTotal = outballPetTotal_cp_15;inpetTotal = inballPetTotal_cp_15;}
					if("K3_CP_1_6".equals(petPlayFinalType)){ petTotal = ballPetTotal_cp_16;outpetTotal = outballPetTotal_cp_16;inpetTotal = inballPetTotal_cp_16;}
					if("K3_CP_2_3".equals(petPlayFinalType)){ petTotal = ballPetTotal_cp_23;outpetTotal = outballPetTotal_cp_23;inpetTotal = inballPetTotal_cp_23;}
					if("K3_CP_2_4".equals(petPlayFinalType)){ petTotal = ballPetTotal_cp_24;outpetTotal = outballPetTotal_cp_24;inpetTotal = inballPetTotal_cp_24;}
					if("K3_CP_2_5".equals(petPlayFinalType)){ petTotal = ballPetTotal_cp_25;outpetTotal = outballPetTotal_cp_25;inpetTotal = inballPetTotal_cp_25;}
					if("K3_CP_2_6".equals(petPlayFinalType)){ petTotal = ballPetTotal_cp_26;outpetTotal = outballPetTotal_cp_26;inpetTotal = inballPetTotal_cp_26;}
					if("K3_CP_3_4".equals(petPlayFinalType)){ petTotal = ballPetTotal_cp_34;outpetTotal = outballPetTotal_cp_34;inpetTotal = inballPetTotal_cp_34;}
					if("K3_CP_3_5".equals(petPlayFinalType)){ petTotal = ballPetTotal_cp_35;outpetTotal = outballPetTotal_cp_35;inpetTotal = inballPetTotal_cp_35;}
					if("K3_CP_3_6".equals(petPlayFinalType)){ petTotal = ballPetTotal_cp_36;outpetTotal = outballPetTotal_cp_36;inpetTotal = inballPetTotal_cp_36;}
					if("K3_CP_4_5".equals(petPlayFinalType)){ petTotal = ballPetTotal_cp_45;outpetTotal = outballPetTotal_cp_45;inpetTotal = inballPetTotal_cp_45;}
					if("K3_CP_4_6".equals(petPlayFinalType)){ petTotal = ballPetTotal_cp_46;outpetTotal = outballPetTotal_cp_46;inpetTotal = inballPetTotal_cp_46;}
					if("K3_CP_5_6".equals(petPlayFinalType)){ petTotal = ballPetTotal_cp_56;outpetTotal = outballPetTotal_cp_56;inpetTotal = inballPetTotal_cp_56;}
				}
				if(petPlayFinalType.indexOf("K3_DP_")!=-1){
					petTotal = ballPetTotal_dp;outpetTotal = outballPetTotal_dp;inpetTotal = inballPetTotal_dp;
				}
								
				
				BigDecimal pet = BigDecimal.valueOf(0);
				pet = petTotal.subtract(vo.getOddsMoney());
				BigDecimal outM = BigDecimal.ZERO;
				BigDecimal inM = BigDecimal.ZERO;		
				if(rMapOut.get(vo.getPlayFinalType())!=null){
					outM = rMapOut.get(vo.getPlayFinalType()).subtract(outpetTotal);
				}else{
					outM = outM.subtract(outpetTotal);
				}
				if(rMapIn.get(vo.getPlayFinalType())!=null){
					inM = rMapIn.get(vo.getPlayFinalType()).subtract(inpetTotal);
				}else{
					inM = inM.subtract(inpetTotal);
				}
				vo.setLoseMoney(pet.add(outM).subtract(inM));
				
			}	
		}
		
		return petList;
	}
	
	
	@Override
	public List<ReplenishVO> findReplenishPetListForLh(String tableName,ManagerUser userInfo,String plate,String periodsNum,String searchType) {
		BigDecimal dxPetTotal = BigDecimal.ZERO;    //大小总投注额
		BigDecimal dsPetTotal = BigDecimal.ZERO;    //单双总投注额
		BigDecimal wdxPetTotal = BigDecimal.ZERO;    //尾大小总投注额
		BigDecimal lhPetTotal = BigDecimal.ZERO;    //
		BigDecimal petTotal = BigDecimal.ZERO;   //统计总投注额
		//补出统计
		BigDecimal outdxPetTotal = BigDecimal.ZERO;    
		BigDecimal outdsPetTotal = BigDecimal.ZERO;    
		BigDecimal outwdxPetTotal = BigDecimal.ZERO;    
		BigDecimal outlhPetTotal = BigDecimal.ZERO;   
		BigDecimal outpetTotal = BigDecimal.ZERO; 
		//补进统计
		BigDecimal indxPetTotal = BigDecimal.ZERO;    
		BigDecimal indsPetTotal = BigDecimal.ZERO;    
		BigDecimal inwdxPetTotal = BigDecimal.ZERO;    
		BigDecimal inlhPetTotal = BigDecimal.ZERO;   
		BigDecimal inpetTotal = BigDecimal.ZERO; 
		
		Map<String,BigDecimal> rMapOut = new HashMap<String,BigDecimal>();
		Map<String,BigDecimal> rMapIn = new HashMap<String,BigDecimal>();
		
		boolean isSys = userInfo.getUserType().equals( ManagerStaff.USER_TYPE_SYS);// 系统类型
		if (!isSys)// 系统管理员一般不操作
        {
			this.getUserType(userInfo);  
			petList = replenishDao.queryDoubleTotalForLh(userType, userInfo.getID(), plate, periodsNum, rateUser, commissionUser);
		    
			this.findReplenishList(periodsNum, plate, "GDKLSF_DOUBLE_%",userInfo);
			
			this.totalReplenish(searchType,Constant.LOTTERY_TYPE_GDKLSF,"DOUBLESIDE");
			
			for(ReplenishVO vo : replenishList){
				BigDecimal rOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						rOddsMoney = rOddsMoney.add(vo.getOddsMoney());
					}
				}
				rMapOut.put(vo.getPlayFinalType(), rOddsMoney);
			}
			//补进
			//统计每种玩法的补货的赔额
			for(ReplenishVO vo : replenishAccList){
				BigDecimal rOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishAccList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						rOddsMoney = rOddsMoney.add(vo.getOddsMoney());
					}
				}
				rMapIn.put(vo.getPlayFinalType(), rOddsMoney);
			}
		    
		    //计算投注总额	
			for(ReplenishVO vo : petList){
				if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHDA") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHX")){
						dxPetTotal = dxPetTotal.add(vo.getCommissionMoney());
				}else 
					if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHDAN") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHS")){
						   dsPetTotal = dsPetTotal.add(vo.getCommissionMoney());						  
					}else 
						if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHWD") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHWX")){							
							   wdxPetTotal = wdxPetTotal.add(vo.getCommissionMoney());							
						}else
							if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_LONG") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_HU")){
								    lhPetTotal = lhPetTotal.add(vo.getCommissionMoney());
							}			
		 	}
			
			//统计补出总额	
			for(ReplenishVO vo : replenishList){
				if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHDA") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHX")){
						outdxPetTotal = outdxPetTotal.add(vo.getCommissionMoney());
				}else 
					if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHDAN") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHS")){
						outdsPetTotal = outdsPetTotal.add(vo.getCommissionMoney());						  
					}else 
						if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHWD") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHWX")){							
							outwdxPetTotal = outwdxPetTotal.add(vo.getCommissionMoney());							
						}else
							if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_LONG") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_HU")){
								outlhPetTotal = outlhPetTotal.add(vo.getCommissionMoney());
							}			
		 	}
			
			//统计补进总额	
			for(ReplenishVO vo : replenishAccList){
				if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHDA") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHX")){
						indxPetTotal = indxPetTotal.add(vo.getCommissionMoney());
				}else 
					if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHDAN") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHS")){
						indsPetTotal = indsPetTotal.add(vo.getCommissionMoney());						  
					}else 
						if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHWD") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_ZHWX")){							
							inwdxPetTotal = inwdxPetTotal.add(vo.getCommissionMoney());							
						}else
							if(vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_LONG") || vo.getPlayFinalType().equals("GDKLSF_DOUBLESIDE_HU")){
								inlhPetTotal = inlhPetTotal.add(vo.getCommissionMoney());
							}			
		 	}
			
			//计算亏盈
            for(int ii=0; ii<petList.size(); ii++){
				BigDecimal pet = BigDecimal.valueOf(0);
				ReplenishVO vo = petList.get(ii);
				
				String petPlayFinalType = vo.getPlayFinalType();
				if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_ZHDA") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_ZHX")){
					petTotal = dxPetTotal;outpetTotal = outdxPetTotal;inpetTotal = indxPetTotal;
				}else
					if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_ZHDAN") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_ZHS")){
						petTotal = dsPetTotal;outpetTotal = outdsPetTotal;inpetTotal = indsPetTotal;
					}else
						if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_ZHWD") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_ZHWX")){
							petTotal = wdxPetTotal;outpetTotal = outwdxPetTotal;inpetTotal = inwdxPetTotal;
						}else
							if(petPlayFinalType.equals("GDKLSF_DOUBLESIDE_LONG") || petPlayFinalType.equals("GDKLSF_DOUBLESIDE_HU")){
								petTotal = lhPetTotal;outpetTotal = outlhPetTotal;inpetTotal = inlhPetTotal;
							}
				pet = petTotal.subtract(vo.getOddsMoney());
				BigDecimal outM = BigDecimal.ZERO;
				BigDecimal inM = BigDecimal.ZERO;		
				if(rMapOut.get(vo.getPlayFinalType())!=null){
					outM = rMapOut.get(vo.getPlayFinalType()).subtract(outpetTotal);
				}else{
					outM = outM.subtract(outpetTotal);
				}
				if(rMapIn.get(vo.getPlayFinalType())!=null){
					inM = rMapIn.get(vo.getPlayFinalType()).subtract(inpetTotal);
				}else{
					inM = inM.subtract(inpetTotal);
				}
				petList.get(ii).setLoseMoney(pet.add(outM).subtract(inM));
            }
		}
					
		return petList;
	}
	
	@Override
	public List<ReplenishVO> findReplenishPetListForLh_NC(String tableName,ManagerUser userInfo,String plate,String periodsNum,String searchType) {
		BigDecimal dxPetTotal = BigDecimal.ZERO;    //大小总投注额
		BigDecimal dsPetTotal = BigDecimal.ZERO;    //单双总投注额
		BigDecimal wdxPetTotal = BigDecimal.ZERO;    //尾大小总投注额
		BigDecimal lhPetTotal = BigDecimal.ZERO;    //
		BigDecimal petTotal = BigDecimal.ZERO;   //统计总投注额
		//补出统计
		BigDecimal outdxPetTotal = BigDecimal.ZERO;    
		BigDecimal outdsPetTotal = BigDecimal.ZERO;    
		BigDecimal outwdxPetTotal = BigDecimal.ZERO;    
		BigDecimal outlhPetTotal = BigDecimal.ZERO;   
		BigDecimal outpetTotal = BigDecimal.ZERO; 
		//补进统计
		BigDecimal indxPetTotal = BigDecimal.ZERO;    
		BigDecimal indsPetTotal = BigDecimal.ZERO;    
		BigDecimal inwdxPetTotal = BigDecimal.ZERO;    
		BigDecimal inlhPetTotal = BigDecimal.ZERO;   
		BigDecimal inpetTotal = BigDecimal.ZERO; 
		
		Map<String,BigDecimal> rMapOut = new HashMap<String,BigDecimal>();
		Map<String,BigDecimal> rMapIn = new HashMap<String,BigDecimal>();
		
		boolean isSys = userInfo.getUserType().equals( ManagerStaff.USER_TYPE_SYS);// 系统类型
		if (!isSys)// 系统管理员一般不操作
		{
			this.getUserType(userInfo);  
			petList = replenishDao.queryDoubleTotalForLh_NC(userType, userInfo.getID(), plate, periodsNum, rateUser, commissionUser);
			
			this.findReplenishList(periodsNum, plate, "NC_DOUBLE_%",userInfo);
			
			this.totalReplenish(searchType,Constant.LOTTERY_TYPE_NC,"DOUBLESIDE");
			
			for(ReplenishVO vo : replenishList){
				BigDecimal rOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						rOddsMoney = rOddsMoney.add(vo.getOddsMoney());
					}
				}
				rMapOut.put(vo.getPlayFinalType(), rOddsMoney);
			}
			//补进
			//统计每种玩法的补货的赔额
			for(ReplenishVO vo : replenishAccList){
				BigDecimal rOddsMoney = BigDecimal.ZERO;
				for(ReplenishVO rvo : replenishAccList){
					if(vo.getPlayFinalType().equals(rvo.getPlayFinalType())){
						rOddsMoney = rOddsMoney.add(vo.getOddsMoney());
					}
				}
				rMapIn.put(vo.getPlayFinalType(), rOddsMoney);
			}
			
			//计算投注总额	
			for(ReplenishVO vo : petList){
				if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHDA") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHX")){
					dxPetTotal = dxPetTotal.add(vo.getCommissionMoney());
				}else 
					if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHDAN") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHS")){
						dsPetTotal = dsPetTotal.add(vo.getCommissionMoney());						  
					}else 
						if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHWD") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHWX")){							
							wdxPetTotal = wdxPetTotal.add(vo.getCommissionMoney());							
						}else
							if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_LONG") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_HU")){
								lhPetTotal = lhPetTotal.add(vo.getCommissionMoney());
							}			
			}
			
			//统计补出总额	
			for(ReplenishVO vo : replenishList){
				if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHDA") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHX")){
					outdxPetTotal = outdxPetTotal.add(vo.getCommissionMoney());
				}else 
					if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHDAN") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHS")){
						outdsPetTotal = outdsPetTotal.add(vo.getCommissionMoney());						  
					}else 
						if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHWD") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHWX")){							
							outwdxPetTotal = outwdxPetTotal.add(vo.getCommissionMoney());							
						}else
							if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_LONG") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_HU")){
								outlhPetTotal = outlhPetTotal.add(vo.getCommissionMoney());
							}			
			}
			
			//统计补进总额	
			for(ReplenishVO vo : replenishAccList){
				if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHDA") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHX")){
					indxPetTotal = indxPetTotal.add(vo.getCommissionMoney());
				}else 
					if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHDAN") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHS")){
						indsPetTotal = indsPetTotal.add(vo.getCommissionMoney());						  
					}else 
						if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHWD") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_ZHWX")){							
							inwdxPetTotal = inwdxPetTotal.add(vo.getCommissionMoney());							
						}else
							if(vo.getPlayFinalType().equals("NC_DOUBLESIDE_LONG") || vo.getPlayFinalType().equals("NC_DOUBLESIDE_HU")){
								inlhPetTotal = inlhPetTotal.add(vo.getCommissionMoney());
							}			
			}
			
			//计算亏盈
			for(int ii=0; ii<petList.size(); ii++){
				BigDecimal pet = BigDecimal.valueOf(0);
				ReplenishVO vo = petList.get(ii);
				
				String petPlayFinalType = vo.getPlayFinalType();
				if(petPlayFinalType.equals("NC_DOUBLESIDE_ZHDA") || petPlayFinalType.equals("NC_DOUBLESIDE_ZHX")){
					petTotal = dxPetTotal;outpetTotal = outdxPetTotal;inpetTotal = indxPetTotal;
				}else
					if(petPlayFinalType.equals("NC_DOUBLESIDE_ZHDAN") || petPlayFinalType.equals("NC_DOUBLESIDE_ZHS")){
						petTotal = dsPetTotal;outpetTotal = outdsPetTotal;inpetTotal = indsPetTotal;
					}else
						if(petPlayFinalType.equals("NC_DOUBLESIDE_ZHWD") || petPlayFinalType.equals("NC_DOUBLESIDE_ZHWX")){
							petTotal = wdxPetTotal;outpetTotal = outwdxPetTotal;inpetTotal = inwdxPetTotal;
						}else
							if(petPlayFinalType.equals("NC_DOUBLESIDE_LONG") || petPlayFinalType.equals("NC_DOUBLESIDE_HU")){
								petTotal = lhPetTotal;outpetTotal = outlhPetTotal;inpetTotal = inlhPetTotal;
							}
				pet = petTotal.subtract(vo.getOddsMoney());
				BigDecimal outM = BigDecimal.ZERO;
				BigDecimal inM = BigDecimal.ZERO;		
				if(rMapOut.get(vo.getPlayFinalType())!=null){
					outM = rMapOut.get(vo.getPlayFinalType()).subtract(outpetTotal);
				}else{
					outM = outM.subtract(outpetTotal);
				}
				if(rMapIn.get(vo.getPlayFinalType())!=null){
					inM = rMapIn.get(vo.getPlayFinalType()).subtract(inpetTotal);
				}else{
					inM = inM.subtract(inpetTotal);
				}
				petList.get(ii).setLoseMoney(pet.add(outM).subtract(inM));
			}
		}
		
		return petList;
	}
	
	@Override
	public List<ReplenishVO> queryReplenish_LM(String tableName, ManagerUser userInfo, String plate,String periodsNum, String searchType,String typeCode) {
		List<ReplenishVO> petList = new ArrayList<ReplenishVO>();
		List<ReplenishVO> replenishList = null;  //补货人_补货球表
		List<ReplenishVO> replenishAccList = null;  //被补货人_补货球表
				   
		boolean isSys = userInfo.getUserType().equals( ManagerStaff.USER_TYPE_SYS);// 系统类型
		if (!isSys)// 系统管理员一般不操作
        {
			this.getUserType(userInfo);
		    petList = replenishDao.queryTotal_LM_Main(tableName,userType, userInfo.getID(), plate, periodsNum, rateUser, commissionUser, typeCode);
		    //补出的货
		    replenishList = replenishDao.queryReplenish_LM_Main("replenish_user_id",userInfo.getID(),typeCode+"%",periodsNum,plate,outCommissionUser,userInfo.getUserType());
		    //接受补入的货		    
		    replenishAccList = replenishDao.queryReplenish_LM_Acc_Main(userType,userInfo.getID(),typeCode+"%",periodsNum,plate,rateUser,inCommissionUser);
		    
		  //处理补货信息，减去补出的，加上补入的	
		    if(Constant.TRUE_STATUS.equals(searchType) || Constant.COMPANY_STATUS.equals(searchType)){  //实占或公司占的情况	
		    	for(ReplenishVO vo : petList){
		    		BigDecimal outMoney = BigDecimal.ZERO;
			    	BigDecimal inMoney = BigDecimal.ZERO;
					//减去补出的
		    		for(ReplenishVO tVo : replenishList){
						if(vo.getPlayFinalType().equals(tVo.getPlayFinalType())){
							outMoney = outMoney.add(BigDecimal.valueOf(tVo.getMoney()));
						}
					}
					//加上接受补入的
		    		for(ReplenishVO tVo : replenishAccList){
						if(vo.getPlayFinalType().equals(tVo.getPlayFinalType())){
							inMoney = inMoney.add(tVo.getRateMoney());
						}
					}
		    		vo.setMoney((vo.getRateMoney().subtract(outMoney).add(inMoney)).intValue());
		    		vo.setLoseMoney(outMoney);
			    }
		    }
		    else{
	        	for(ReplenishVO vo : petList){
		    		BigDecimal outMoney = BigDecimal.ZERO;
		    		for(ReplenishVO tVo : replenishList){
						if(vo.getPlayFinalType().equals(tVo.getPlayFinalType())){
							outMoney = outMoney.add(BigDecimal.valueOf(tVo.getMoney()));
						}
					}
		    		vo.setLoseMoney(outMoney);
			    }
	        }
        }	
		return petList;
	}
	
	@Override
	public List<ReplenishVO> queryReplenish_LM_Attribute(String tableName, ManagerUser userInfo, String plate,String periodsNum, String searchType,String typeCode) {
		List<ReplenishVO> petList = new ArrayList<ReplenishVO>();
		List<ReplenishVO> replenishList = null;  //补货人_补货球表
		List<ReplenishVO> replenishAccList = null;  //被补货人_补货球表
		
		boolean isSys = userInfo.getUserType().equals( ManagerStaff.USER_TYPE_SYS);// 系统类型
		if (!isSys)// 系统管理员一般不操作
		{
			this.getUserType(userInfo);
			petList = replenishDao.queryTotal_LM(tableName,userType, userInfo.getID(), plate, periodsNum, rateUser, commissionUser, typeCode);
			//补出的货
			replenishList = replenishDao.queryReplenish_LM("replenish_user_id",userInfo.getID(),typeCode+"%",periodsNum,plate,outCommissionUser);
			//接受补入的货		    
			replenishAccList = replenishDao.queryReplenish_LM_Acc(userType,userInfo.getID(),typeCode+"%",periodsNum,plate,rateUser,inCommissionUser);
			
			//处理补货信息，减去补出的，加上补入的	
			if(searchType.equals(Constant.TRUE_STATUS)){  //实占的情况
				for(ReplenishVO vo : petList){
					BigDecimal outMoney = BigDecimal.ZERO;
					BigDecimal inMoney = BigDecimal.ZERO;
					//减去补出的
					for(ReplenishVO tVo : replenishList){
						if(vo.getPlayFinalType().equals(tVo.getPlayFinalType()) && vo.getAttribute().equals(tVo.getAttribute())){
							outMoney = outMoney.add(BigDecimal.valueOf(tVo.getMoney()));
						}
					}
					//加上接受补入的
					for(ReplenishVO tVo : replenishAccList){
						if(vo.getPlayFinalType().equals(tVo.getPlayFinalType()) && vo.getAttribute().equals(tVo.getAttribute())){
							inMoney = inMoney.add(tVo.getRateMoney());
						}
					}
					vo.setRateMoney((vo.getRateMoney().subtract(outMoney).add(inMoney)));
					vo.setLoseMoney(outMoney);
				}
			}
			else{
				for(ReplenishVO vo : petList){
					BigDecimal outMoney = BigDecimal.ZERO;
					for(ReplenishVO tVo : replenishList){
						if(vo.getPlayFinalType().equals(tVo.getPlayFinalType()) && vo.getAttribute().equals(tVo.getAttribute())){
							outMoney = outMoney.add(BigDecimal.valueOf(tVo.getMoney()));
						}
					}
					vo.setLoseMoney(outMoney);
				}
			}
		}	
		return petList;
	}
	
	@Override
	public void saveReplenishSubmit(Replenish replenish) {
		//ReplenishCheck replenishCheck = new ReplenishCheck();
		//BeanUtils.copyProperties(replenish,replenishCheck);
		//replenishCheckDao.save(replenishCheck);
		checkDao.insertReplenishCheck(replenish);
			replenishHibDao.save(replenish);	
	}
	
	public void saveReplenishSubmitByScheme(Replenish replenish,String scheme){
		replenishHibDao.insertReplenish(replenish, scheme);
	}

	@Override
	public List<Replenish> queryFinishReplenish(String userType,Long replenishUserId, String typeCode, String periodsNum,String plate) {		
		String hql="from Replenish " +
				   "where " + userType + "=? " +
				   "and typeCode =? " +
				   "and periodsNum=? " +
				   "and plate=?";		
		return replenishHibDao.find(hql, replenishUserId, typeCode, periodsNum, plate);
	}
	
	@Override
	public List<Replenish> queryFinishReplenish_LM(String userType,Long replenishUserId, String typeCode, String periodsNum,String plate,String attribute) {		
		String hql="from Replenish " +
				   "where " + userType + "=? " +
				   "and typeCode =? " +
				   "and periodsNum=? " +
				   "and plate=? " +
				   "and attribute=?";		
		return replenishHibDao.find(hql, replenishUserId, typeCode, periodsNum, plate, attribute);
	}
	
	@Override
	public List<Replenish> findReplenish(Criterion...criterions)
	{
		return replenishHibDao.find(criterions);
		
	}
	
	@Override
	public List<ReplenishCheck> findReplenishCheck(Criterion...criterions)
	{
		return replenishCheckDao.find(criterions);
		
	}
	
	@Override
	public List<ReplenishHis> findReplenishHis(Criterion...criterions)
	{
		return replenishHisDao.find(criterions);
		
	}
	
	/**
	 * 保存补货新方法，jdbc进行操作
	 */
	@Override
	public Map<String, Replenish> saveReplenishByScheme(Replenish replenish,ManagerUser userInfo,String periodsNum ,String str,
			String isQuick,String isAutoReplenish,String scheme){
		Long currentUserId = null;
		String shopCode = null;
		Date currentDate = new Date();
		Map<String, Replenish> messageMap = new HashMap<String, Replenish>();
		try{
		
		UserInfoVO userInfoVO = userCommissionLogic.queryUserInfoByScheme(userInfo,scheme); 
		if(userInfoVO.getReplenishment().equals(Constant.ALOW_REPLENISH)){   //判断该用户是否允许补货
			//获取SEQ
			OracleSequenceMaxValueIncrementer orderNoGener = (OracleSequenceMaxValueIncrementer) SpringUtil
					.getBean("replenishOrderNoGenerator");
			
			String orderNo = orderNoGener.nextStringValue();				
			replenish.setOrderNo(orderNo);
			
			currentUserId= userInfo.getID();   
			//userChsName = userInfo.getChsName();
			
			//如果是总监补货时，是以出货会员补货的，所以补货人不用取当前用户id,而是直接从页面取
			if(!ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType())){
				replenish.setReplenishUserId(currentUserId);	//补货人id
			}
			
			shopCode = userInfo.getSafetyCode();
			replenish.setPeriodsNum(periodsNum);   //期数	
			/*List<ShopsPlayOdds> oddsList = null;					
			
				oddsList = shopOddsLogic.queryOddsByTypeCode(shopCode,replenish.getTypeCode());  
				ShopsPlayOdds shopsPlayOdds = new ShopsPlayOdds();
				if(oddsList!=null && oddsList.size()>0){
					shopsPlayOdds=oddsList.get(0);
					
				}else{
					log.info("保存补货信息異常：商鋪號:" + shopCode + ",用戶：" + currentUserId);
					replenish.setMessage("补货异常,請返回重試.");
					messageMap.put("errorMessage", replenish);
					return messageMap;
				}*/
				/*if(!userInfo.getUserType().equals(ManagerStaff.USER_TYPE_CHIEF) ){
					//如果是自动补货的，就要根据传值的盘口取赔率
					if(Constant.MENU_REPLENISH.equals(isAutoReplenish)){
						replenish.setOdds(shopsPlayOdds.getRealOdds());  //玩法的当前赔率,而不是投注页面的赔率
					}else{
						replenish.setOdds(replenish.getOdds());
						if(Constant.A.equals(replenish.getPlate())){
							replenish.setOdds(shopsPlayOdds.getRealOdds());
						}else if(Constant.B.equals(replenish.getPlate())){
							replenish.setOdds(shopsPlayOdds.getRealOddsB());
						}else{
							replenish.setOdds(shopsPlayOdds.getRealOddsC());
						}
					}
				}else if("yes".equals(isQuick)){
					replenish.setOdds(shopsPlayOdds.getRealOdds());  //如果是快速补货的，就取当前赔率
				}*/
			replenish.setBettingDate(currentDate);
			replenish.setWinState(GdklsfHis.NOT_LOTTERY);
			//replenish.setPlate(replenish.getPlate());
			PlayType playType = PlayTypeUtils.getPlayType(replenish.getTypeCode());//获取拥金类型
			replenish.setCommissionType(playType.getCommissionType());
			if(!ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType())){
				if(!"autoReplenish".equals(isAutoReplenish)){
					//if(!this.verifyData(userInfo,replenish,periodsNum,replenish.getPlate()))
					//{
						//log.info("保存补货信息異常：商鋪號:" + shopCode + ",用戶：" + currentUserId);
						//replenish.setMessage("補貨金額超過最大金額,請返回重試.");
						//messageMap.put("errorMessage", replenish);
						//return messageMap;
					//}
				}
			}
			try {
				log.info("进行补货操作,"+replenish.getTypeCode()+","+replenish.getReplenishUserId());
				replenishLogic.saveReplenishSubmitByScheme(replenish,scheme);//保存補貨信息
				
				messageMap.put("success", replenish);
				if(!"autoReplenish".equals(isAutoReplenish)){
					//补货触发的自动降赔
					Integer chiefPet = (BigDecimal.valueOf(replenish.getMoney()).multiply(replenish.getRateChief().divide(BigDecimal.valueOf(100)))).intValue();
					updateShopRealOddsForReplenishByScheme(userInfo.getSafetyCode(), chiefPet, userInfo.getID(), replenish.getTypeCode(), periodsNum,scheme);
				}
//				log.info("补货完成");
			} catch (Exception e) {
				log.info("保存补货信息異常：商鋪號:" + shopCode + ",用戶：" + currentUserId,e);
				replenish.setMessage("用戶：" + currentUserId + " 補貨操作異常");
				messageMap.put("errorMessage", replenish);
				return messageMap;
			}
		}else{
			log.info("禁止走飞：商鋪號:" + shopCode + ",用戶：" + currentUserId);
			replenish.setMessage("禁止走飞");
			messageMap.put("errorMessage", replenish);
			return messageMap;
		}
		} catch (Exception e) {
			log.info("保存补货信息異常:"+e.getMessage());
		}
		long end3 = System.currentTimeMillis();
		return messageMap;
	}
	
	public void updateShopRealOddsForReplenishByScheme(String shopCode,Integer money,Long userID,String typeCode,String periodsNum,String scheme)
	{
		
			PlayType playType=PlayTypeUtils.getPlayType(typeCode);
			String oddType=playType.getOddsType();
			
			OpenPlayOdds openOdd=shopOddsLogic.queryOpenPlayOdds(oddType,shopCode);
			Integer quotas=openOdd.getAutoOddsQuotas();
	        BigDecimal autoMoney=openOdd.getAutoOdds();
	        
//	        List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
//	        filtersPeriodInfo.add(Restrictions.eq("typeCode", typeCode));
//	        filtersPeriodInfo.add(Restrictions.eq("shopCode", shopCode));
//		
//	       	PlayAmount playAmount=playAmountDao.findUnique(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
	       PlayAmount playAmount=playAmountDao.queryUniqueByTypeCode(typeCode, shopCode, scheme);
	       Double moneyAmount=playAmount.getMoneyAmount();
	       
	       //fixed by peter 修改自动降赔阙值错误问题
	       double replenishMoney=0;
//			ShopsInfo shopsInfo = shopsLogic.findShopsInfoByCode(shopCode);
//	       if(null!=shopsInfo){
//				if(null!=shopsInfo.getChiefStaffExt()){
//					replenishMoney = replenishLogic.queryReplenishForBetCheckByScheme(shopsInfo.getChiefStaffExt().getID(), ManagerUser.USER_TYPE_CHIEF, typeCode, periodsNum,scheme).doubleValue();
//				}
//			}
	       //根据商铺号查询总监
	       ChiefStaffExt chiefStaff=chiefStaffExtLogic.queryChiefByShopCode(shopCode, scheme);
	       if(null!=chiefStaff){
				replenishMoney = replenishLogic.queryReplenishForBetCheckByScheme(chiefStaff.getID(), ManagerUser.USER_TYPE_CHIEF, typeCode, periodsNum,scheme).doubleValue();
	       }
	       int moneysAfterBet = (int)(moneyAmount+replenishMoney+money)/quotas;
	       int moneyBeforeBet = (int)((moneyAmount+replenishMoney)/quotas);
			if (moneysAfterBet > moneyBeforeBet){
	    	   //int quotaCount=((moneyAmount.intValue()+money*chiefRate/100)/quotas)-(moneyAmount.intValue()/quotas);
	    	   //fixed by peter 增加补货的计算
			   int quotaCount=moneysAfterBet-moneyBeforeBet;
	    	   ShopsPlayOdds shopOdds= shopOddsLogic.queryShopPlayOdds(shopCode, typeCode);
	    	   BigDecimal odds=shopOdds.getRealOdds();
	    	   float realOdds=odds.floatValue()-autoMoney.floatValue()*quotaCount;
	    	   if(realOdds<0)
	    		   realOdds=0f;
	    	   shopOdds.setRealOdds(new BigDecimal(realOdds));
	    	   
	    	   ShopsPlayOddsLog log = new ShopsPlayOddsLog();
	   		   log.setRealOddsOrigin(odds);
	   		log.setShopCode(shopCode);
			log.setRealUpdateDateNew(new Date(System.currentTimeMillis()));
			log.setPlayTypeCode(typeCode);
			log.setRealOddsNew(new BigDecimal(realOdds));
			log.setRealUpdateUserNew(Integer.valueOf(userID+""));
			log.setRealUpdateDateOrigin(shopOdds.getRealUpdateDate());
			log.setRealUpdateUserOrigin(shopOdds.getRealUpdateUser().intValue());
			log.setRemark("");
			
			log.setOddsType(shopOdds.getOddsType());
			log.setOddsTypeX(shopOdds.getOddsTypeX());
			log.setPeriodsNum(periodsNum);
			//获取修改赔率者的IP地址
			String ip = "127.0.0.1";
			log.setIp(ip);
			log.setType(Constant.ODD_LOG_AUTO);
			shopsPlayOddsLogLogic.saveLogByScheme(log,scheme);
	    	shopOddsLogic.updateShopOdds(shopOdds);
	    	   
	       }
	     
	}	
	
	@Override
	public Map<String, Replenish> saveReplenish(Replenish replenish,ManagerUser userInfo,String periodsNum ,String str,String isQuick,String isAutoReplenish)
	{
		Long currentUserId = null;
		String shopCode = null;
		Date currentDate = new Date();
		Map<String, Replenish> messageMap = new HashMap<String, Replenish>();
		try{
		
		UserInfoVO userInfoVO = userCommissionLogic.queryUserInfo(userInfo); 
		if(userInfoVO.getReplenishment().equals(Constant.ALOW_REPLENISH)){   //判断该用户是否允许补货
			//获取SEQ
			OracleSequenceMaxValueIncrementer orderNoGener = (OracleSequenceMaxValueIncrementer) SpringUtil
					.getBean("replenishOrderNoGenerator");
			
			String orderNo = orderNoGener.nextStringValue();				
			replenish.setOrderNo(orderNo);
			
			currentUserId= userInfo.getID();   
			//userChsName = userInfo.getChsName();
			
			//如果是总监补货时，是以出货会员补货的，所以补货人不用取当前用户id,而是直接从页面取
			if(!ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType())){
				replenish.setReplenishUserId(currentUserId);	//补货人id
			}
			
			shopCode = userInfo.getSafetyCode();
			replenish.setPeriodsNum(periodsNum);   //期数	
			/*List<ShopsPlayOdds> oddsList = null;					
			
				oddsList = shopOddsLogic.queryOddsByTypeCode(shopCode,replenish.getTypeCode());  
				ShopsPlayOdds shopsPlayOdds = new ShopsPlayOdds();
				if(oddsList!=null && oddsList.size()>0){
					shopsPlayOdds=oddsList.get(0);
					
				}else{
					log.info("保存补货信息異常：商鋪號:" + shopCode + ",用戶：" + currentUserId);
					replenish.setMessage("补货异常,請返回重試.");
					messageMap.put("errorMessage", replenish);
					return messageMap;
				}*/
				/*if(!userInfo.getUserType().equals(ManagerStaff.USER_TYPE_CHIEF) ){
					//如果是自动补货的，就要根据传值的盘口取赔率
					if(Constant.MENU_REPLENISH.equals(isAutoReplenish)){
						replenish.setOdds(shopsPlayOdds.getRealOdds());  //玩法的当前赔率,而不是投注页面的赔率
					}else{
						replenish.setOdds(replenish.getOdds());
						if(Constant.A.equals(replenish.getPlate())){
							replenish.setOdds(shopsPlayOdds.getRealOdds());
						}else if(Constant.B.equals(replenish.getPlate())){
							replenish.setOdds(shopsPlayOdds.getRealOddsB());
						}else{
							replenish.setOdds(shopsPlayOdds.getRealOddsC());
						}
					}
				}else if("yes".equals(isQuick)){
					replenish.setOdds(shopsPlayOdds.getRealOdds());  //如果是快速补货的，就取当前赔率
				}*/
			replenish.setBettingDate(currentDate);
			replenish.setWinState(GdklsfHis.NOT_LOTTERY);
			//replenish.setPlate(replenish.getPlate());
			PlayType playType = PlayTypeUtils.getPlayType(replenish.getTypeCode());//获取拥金类型
			replenish.setCommissionType(playType.getCommissionType());
			if(!ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType())){
				if(!"autoReplenish".equals(isAutoReplenish)){
					//if(!this.verifyData(userInfo,replenish,periodsNum,replenish.getPlate()))
					//{
						//log.info("保存补货信息異常：商鋪號:" + shopCode + ",用戶：" + currentUserId);
						//replenish.setMessage("補貨金額超過最大金額,請返回重試.");
						//messageMap.put("errorMessage", replenish);
						//return messageMap;
					//}
				}
			}
			try {
				log.info("进行补货操作,"+replenish.getTypeCode()+","+replenish.getReplenishUserId());
				replenishLogic.saveReplenishSubmit(replenish);//保存補貨信息
				
				messageMap.put("success", replenish);
				if(!"autoReplenish".equals(isAutoReplenish)){
					//补货触发的自动降赔
					Integer chiefPet = (BigDecimal.valueOf(replenish.getMoney()).multiply(replenish.getRateChief().divide(BigDecimal.valueOf(100)))).intValue();
					updateShopRealOddsForReplenish(userInfo.getSafetyCode(), chiefPet, userInfo.getID(), replenish.getTypeCode(), periodsNum);
				}
//				log.info("补货完成");
			} catch (Exception e) {
				log.info("保存补货信息異常：商鋪號:" + shopCode + ",用戶：" + currentUserId,e);
				replenish.setMessage("用戶：" + currentUserId + " 補貨操作異常");
				messageMap.put("errorMessage", replenish);
				return messageMap;
			}
		}else{
			log.info("禁止走飞：商鋪號:" + shopCode + ",用戶：" + currentUserId);
			replenish.setMessage("禁止走飞");
			messageMap.put("errorMessage", replenish);
			return messageMap;
		}
		} catch (Exception e) {
			log.info("保存补货信息異常:"+e.getMessage());
		}
		long end3 = System.currentTimeMillis();
		return messageMap;
	}
	
	public void updateShopRealOddsForReplenish(String shopCode,Integer money,Long userID,String typeCode,String periodsNum)
	{
		
			PlayType playType=PlayTypeUtils.getPlayType(typeCode);
			String oddType=playType.getOddsType();
			
			OpenPlayOdds openOdd=shopOddsLogic.queryOpenPlayOdds(oddType,shopCode);
			Integer quotas=openOdd.getAutoOddsQuotas();
	        BigDecimal autoMoney=openOdd.getAutoOdds();
	        
	        List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
	        filtersPeriodInfo.add(Restrictions.eq("typeCode", typeCode));
	        filtersPeriodInfo.add(Restrictions.eq("shopCode", shopCode));
		
	       PlayAmount playAmount=playAmountDao.findUnique(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
	       
	       Double moneyAmount=playAmount.getMoneyAmount();
	       
	       //fixed by peter 修改自动降赔阙值错误问题
	       double replenishMoney=0;
			ShopsInfo shopsInfo = shopsLogic.findShopsCode(shopCode);
	       if(null!=shopsInfo){
				if(null!=shopsInfo.getChiefStaffExt()){
					replenishMoney = replenishLogic.queryReplenishForBetCheck(shopsInfo.getChiefStaffExt().getID(), ManagerUser.USER_TYPE_CHIEF, typeCode, periodsNum).doubleValue();
				}
			}
			
	       int moneysAfterBet = (int)(moneyAmount+replenishMoney+money)/quotas;
	       int moneyBeforeBet = (int)((moneyAmount+replenishMoney)/quotas);
			if (moneysAfterBet > moneyBeforeBet)
	       {
	    	   //int quotaCount=((moneyAmount.intValue()+money*chiefRate/100)/quotas)-(moneyAmount.intValue()/quotas);
	    	   //fixed by peter 增加补货的计算
			   int quotaCount=moneysAfterBet-moneyBeforeBet;
	    	   ShopsPlayOdds shopOdds= shopOddsLogic.queryShopPlayOdds(shopCode, typeCode);
	    	   BigDecimal odds=shopOdds.getRealOdds();
	    	   float realOdds=odds.floatValue()-autoMoney.floatValue()*quotaCount;
	    	   if(realOdds<0)
	    		   realOdds=0f;
	    	   shopOdds.setRealOdds(new BigDecimal(realOdds));
	    	   
	    	   ShopsPlayOddsLog log = new ShopsPlayOddsLog();
	   		   log.setRealOddsOrigin(odds);
	   		log.setShopCode(shopCode);
			log.setRealUpdateDateNew(new Date(System.currentTimeMillis()));
			log.setPlayTypeCode(typeCode);
			log.setRealOddsNew(new BigDecimal(realOdds));
			log.setRealUpdateUserNew(Integer.valueOf(userID+""));
			log.setRealUpdateDateOrigin(shopOdds.getRealUpdateDate());
			log.setRealUpdateUserOrigin(shopOdds.getRealUpdateUser().intValue());
			log.setRemark("");
			
			log.setOddsType(shopOdds.getOddsType());
			log.setOddsTypeX(shopOdds.getOddsTypeX());
			log.setPeriodsNum(periodsNum);
			//获取修改赔率者的IP地址
			String ip = "127.0.0.1";
			log.setIp(ip);
			log.setType(Constant.ODD_LOG_AUTO);
			shopsPlayOddsLogLogic.saveLog(log);
	    	shopOddsLogic.updateShopOdds(shopOdds);
	    	   
	       }
	     
	}	
	
	
	//校验传进来的补货数据是否合法
	public Boolean verifyData(ManagerUser orgUserInfo,Replenish replenish,String periodsNum,String plate){	
		log.info("补货校验");
		if(replenishLogic.varifyData(replenish.getMoney(),orgUserInfo, plate, periodsNum, replenish.getTypeCode(),replenish.getAttribute())){
			return true;
		}else
		{
			return false;
		}	
	}
	public Map<String,ShopsPlayOdds> initLMShopOdds(String shopCode,String playType)
	{
		List<ShopsPlayOdds> hkoddList=shopOddsLogic.queryOddsByTypeCode(shopCode,playType);
		Map<String,ShopsPlayOdds> oddMap=Maps.newHashMap();
		for (int i = 0; i < hkoddList.size(); i++) {
			ShopsPlayOdds shopodds=	hkoddList.get(i);
			oddMap.put(shopodds.getOddsTypeX(), shopodds);
			
		}
		return oddMap;
		
	}
	
	public Page queryBetDetail(Page page, ManagerUser userInfo,String typeCode,String periodsNum,String subType) {
		this.getUserType(userInfo);
		return replenishDao.queryBetDetail(page, userInfo.getID(), typeCode, periodsNum, rateUser,userInfo.getUserType(),subType);
	}
	
	public Page queryReplenishInDetail(Page page, ManagerUser userInfo,String typeCode,String periodsNum,String commissionType,
			Date startDate,Date endDate,String winState,String opType,String tableName) {
		UserVO userVo = this.getUserType(userInfo);
		
		Page pageResult = replenishDao.queryReplenishInDetail(page, userInfo.getID(), typeCode, periodsNum, rateUser,
				userInfo.getUserType(),commissionType,startDate,endDate,winState,opType,userVo.getRateUser(),userVo.getCommissionUser(),tableName);
		if(pageResult.getResult().size()>0){
		
			for(int i=0;i<pageResult.getResult().size();i++){
				DetailVO vo = (DetailVO)(pageResult.getResult().get(i));
				if(ManagerStaff.USER_TYPE_BRANCH.equals(vo.getUserType())){
					vo.setBranchRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("分公司走飛");
				}
				if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(vo.getUserType())){
					vo.setStockRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("股東走飛");
				}
				if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(vo.getUserType())){
					vo.setGenAgentRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("總代理走飛");
				}
				if(ManagerStaff.USER_TYPE_AGENT.equals(vo.getUserType())){
					vo.setAgentRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("代理走飛");
				}
			}
		}
		return pageResult;
	}
	
	public Page queryReplenishOutDetail(Page page, ManagerUser userInfo,String typeCode,String periodsNum) {
		this.getUserType(userInfo);
		Page pageResult = replenishDao.queryReplenishOutDetail(page, userInfo.getID(), typeCode, periodsNum, rateUser,userInfo.getUserType());
		if(pageResult.getResult().size()>0){
			for(int i=0;i<pageResult.getResult().size();i++){
				DetailVO vo = (DetailVO)(pageResult.getResult().get(i));
				if(ManagerStaff.USER_TYPE_CHIEF.equals(vo.getUserType()) || ManagerStaff.USER_TYPE_OUT_REPLENISH.equals(vo.getUserType())){
					vo.setChiefRate(BigDecimal.valueOf(-100));
					vo.setChiefCommission(vo.getChiefCommission());
					vo.setWhoReplenish("總監出貨");
				}
				if(ManagerStaff.USER_TYPE_BRANCH.equals(vo.getUserType())){
					vo.setBranchRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("分公司走飛");
				}
				if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(vo.getUserType())){
					vo.setStockRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("股東走飛");
				}
				if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(vo.getUserType())){
					vo.setGenAgentRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("總代理走飛");
				}
				if(ManagerStaff.USER_TYPE_AGENT.equals(vo.getUserType())){
					vo.setAgentRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("代理走飛");
				}
				vo.setRateMoney(BigDecimal.valueOf(-vo.getMoney()));
			}
		}
		return pageResult;
	}
	
	//實時滾單START
	public Page queryBetDetail_RealTime(Page page, ManagerUser userInfo,String typeCode,String periodsNum,String subType,Date prevSearchTime,Integer money) {
		
		this.getUserType(userInfo);
		return replenishDao.queryBetDetail_RealTime(page, userInfo.getID(), typeCode, periodsNum, 
				rateUser,userInfo.getUserType(),subType,prevSearchTime,money);
	}
	
	public Page queryReplenishInDetail_RealTime(Page page, ManagerUser userInfo,String typeCode,String periodsNum,Date prevSearchTime,Integer money) {
		this.getUserType(userInfo);
		Page pageResult = replenishDao.queryReplenishInDetail_RealTime(page, userInfo.getID(), typeCode, periodsNum, 
				rateUser,userInfo.getUserType(),prevSearchTime,money);
		if(pageResult.getResult().size()>0){
			
			for(int i=0;i<pageResult.getResult().size();i++){
				DetailVO vo = (DetailVO)(pageResult.getResult().get(i));
				if(ManagerStaff.USER_TYPE_BRANCH.equals(vo.getUserType())){
					vo.setBranchRate(BigDecimal.valueOf(-100));
				}
				if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(vo.getUserType())){
					vo.setStockRate(BigDecimal.valueOf(-100));
				}
				if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(vo.getUserType())){
					vo.setGenAgentRate(BigDecimal.valueOf(-100));
				}
				if(ManagerStaff.USER_TYPE_AGENT.equals(vo.getUserType())){
					vo.setAgentRate(BigDecimal.valueOf(-100));
				}
				vo.setWhoReplenish("向上走飛");
			}
		}
		return pageResult;
	}
	
	public Page queryReplenishOutDetail_RealTime(Page page, ManagerUser userInfo,String typeCode,String periodsNum,Date prevSearchTime,Integer money) {
		this.getUserType(userInfo);
		Page pageResult = replenishDao.queryReplenishOutDetail_RealTime(page, userInfo.getID(), typeCode, periodsNum, rateUser,userInfo.getUserType(),prevSearchTime,money);
		if(pageResult.getResult().size()>0){
			for(int i=0;i<pageResult.getResult().size();i++){
				DetailVO vo = (DetailVO)(pageResult.getResult().get(i));
				if(ManagerStaff.USER_TYPE_CHIEF.equals(vo.getUserType()) || ManagerStaff.USER_TYPE_OUT_REPLENISH.equals(vo.getUserType())){
					vo.setChiefRate(BigDecimal.valueOf(-100));
					vo.setChiefCommission(vo.getChiefOutCommission());
					vo.setWhoReplenish("總監出貨");
				}
				if(ManagerStaff.USER_TYPE_BRANCH.equals(vo.getUserType())){
					vo.setBranchRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("分公司走飛");
				}
				if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(vo.getUserType())){
					vo.setStockRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("股東走飛");
				}
				if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(vo.getUserType())){
					vo.setGenAgentRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("總代理走飛");
				}
				if(ManagerStaff.USER_TYPE_AGENT.equals(vo.getUserType())){
					vo.setAgentRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("代理走飛");
				}
				vo.setRateMoney(BigDecimal.valueOf(-vo.getMoney()));
			}
		}
		return pageResult;
	}
	/**
	 * 新方法，将补入、补出两方法合并一起查询
	 */
	@Override
	public Page<DetailVO> queryReplenishInAndOutDetail_RealTime(Page<DetailVO> page, ManagerUser userInfo,
			String typeCode, String periodsNum, Date prevSearchTime, Integer money) {
		this.getUserType(userInfo);
		Page pageResult = replenishDao.queryReplenishInAndOutDetail_RealTime(page, userInfo.getID(), typeCode, periodsNum, rateUser, userInfo.getUserType(), prevSearchTime, money);
		return pageResult;
	}
	
	
	//********************實時滾單END
	
	//********************备份START*****************************************
	public Page queryBetDetail_Backup(Page page, ManagerUser userInfo,String typeCode,String periodsNum,String subType) {
		
		this.getUserType(userInfo);
		return replenishDao.queryBetDetail_Backup(page, userInfo.getID(), typeCode, periodsNum, 
				userInfo.getUserType(),subType);
	}
	
	public Page queryReplenishInDetail_Backup(Page page, ManagerUser userInfo,String typeCode,String periodsNum) {
		this.getUserType(userInfo);
		Page pageResult = replenishDao.queryReplenishInDetail_Backup(page, userInfo.getID(), typeCode, periodsNum, 
				rateUser,userInfo.getUserType());
		if(pageResult.getResult().size()>0){
			//补货人所属级别填入-100
			for(int i=0;i<pageResult.getResult().size();i++){
				DetailVO vo = (DetailVO)(pageResult.getResult().get(i));
				if(ManagerStaff.USER_TYPE_BRANCH.equals(vo.getUserType())){
					vo.setBranchRate(BigDecimal.valueOf(-100));
				}
				if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(vo.getUserType())){
					vo.setStockRate(BigDecimal.valueOf(-100));
				}
				if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(vo.getUserType())){
					vo.setGenAgentRate(BigDecimal.valueOf(-100));
				}
				if(ManagerStaff.USER_TYPE_AGENT.equals(vo.getUserType())){
					vo.setAgentRate(BigDecimal.valueOf(-100));
				}
				vo.setWhoReplenish("向上走飛");
			}
		}
		return pageResult;
	}
	
	public Page queryReplenishOutDetail_Backup(Page page, ManagerUser userInfo,String typeCode,String periodsNum) {
		this.getUserType(userInfo);
		Page pageResult = replenishDao.queryReplenishOutDetail_Backup(page, userInfo.getID(), typeCode, periodsNum, rateUser,userInfo.getUserType());
		if(pageResult.getResult().size()>0){
			//补货人所属级别填入-100
			for(int i=0;i<pageResult.getResult().size();i++){
				DetailVO vo = (DetailVO)(pageResult.getResult().get(i));
				if(ManagerStaff.USER_TYPE_CHIEF.equals(vo.getUserType()) || ManagerStaff.USER_TYPE_OUT_REPLENISH.equals(vo.getUserType())){
					vo.setChiefRate(BigDecimal.valueOf(-100));
					vo.setChiefCommission(vo.getChiefOutCommission());
					vo.setWhoReplenish("總監出貨");
				}
				if(ManagerStaff.USER_TYPE_BRANCH.equals(vo.getUserType())){
					vo.setBranchRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("分公司走飛");
				}
				if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(vo.getUserType())){
					vo.setStockRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("股東走飛");
				}
				if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(vo.getUserType())){
					vo.setGenAgentRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("總代理走飛");
				}
				if(ManagerStaff.USER_TYPE_AGENT.equals(vo.getUserType())){
					vo.setAgentRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("代理走飛");
				}
				vo.setRateMoney(BigDecimal.valueOf(-vo.getMoney()));
			}
		}
		return pageResult;
	}	
	//********************备份END**********************************************
	
	public Replenish readyReplenishDataForRate(Replenish replenish,ManagerUser userInfo){
		Map<String,Integer> rateMap=memberStaffExtLogic.findManagerRate(userInfo);
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_CHIEF)){
			replenish.setRateChief(BigDecimal.valueOf(0));
		}
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_BRANCH)){
			replenish.setRateChief(BigDecimal.valueOf(100));
		}
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_STOCKHOLDER)){
			replenish.setRateChief(BigDecimal.valueOf(rateMap.get("chiefRate")));
			replenish.setRateBranch(BigDecimal.valueOf(rateMap.get("branchRate")));
		}
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_GEN_AGENT)){
			replenish.setRateChief(BigDecimal.valueOf(rateMap.get("chiefRate")));
			replenish.setRateBranch(BigDecimal.valueOf(rateMap.get("branchRate")));
			replenish.setRateStockHolder(BigDecimal.valueOf(rateMap.get("shareholderRate")));
		}
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_AGENT)){
			replenish.setRateChief(BigDecimal.valueOf(rateMap.get("chiefRate")));
			replenish.setRateBranch(BigDecimal.valueOf(rateMap.get("branchRate")));
			replenish.setRateStockHolder(BigDecimal.valueOf(rateMap.get("shareholderRate")));
			replenish.setRateGenAgent(BigDecimal.valueOf(rateMap.get("genAgentRate")));
		}
		return replenish;
		
	}
	
	public Replenish readyReplenishDataForUserId(Replenish replenish,ManagerUser userInfo){
		Long currentUserId= userInfo.getID();   
		
		//如果是总监补货时，是以出货会员补货的，所以补货人不用取当前用户id,而是直接从页面取
		if(!ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType())){
			replenish.setReplenishUserId(currentUserId);	//补货人id
		}
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_BRANCH)){
			BranchStaffExt branchStaffExt = branchStaffExtLogic.queryBranchStaffExt("ID", userInfo.getID());
			replenish.setChiefStaff(Long.valueOf(branchStaffExt.getParentStaff()));
		}else if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_STOCKHOLDER)){
			StockholderStaffExt stockholderStaffExt = stockholderStaffExtLogic.queryStockholderStaffExt("ID", userInfo.getID());
			replenish.setChiefStaff(stockholderStaffExt.getChiefStaff());
			replenish.setBranchStaff(Long.valueOf(stockholderStaffExt.getParentStaff()));
		}else if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_GEN_AGENT)){
			GenAgentStaffExt genAgentStaffExt = genAgentStaffExtLogic.queryGenAgentStaffExt("ID", userInfo.getID());
			replenish.setChiefStaff(genAgentStaffExt.getChiefStaff());
			replenish.setBranchStaff(genAgentStaffExt.getBranchStaff());
			replenish.setStockHolderStaff(Long.valueOf(genAgentStaffExt.getParentStaff()));
		}else if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_AGENT)){
			AgentStaffExt agentStaffExt = agentStaffExtLogic.queryAgenStaffExt("ID", userInfo.getID());
			replenish.setChiefStaff(agentStaffExt.getChiefStaff());
			replenish.setBranchStaff(agentStaffExt.getBranchStaff());
			replenish.setStockHolderStaff(agentStaffExt.getStockholderStaff());
			replenish.setGenAgenStaff(Long.valueOf(agentStaffExt.getParentStaff()));
		}
		return replenish;
	}
	
	/**
	 * 新方法，jdbc查询数据操作
	 */
	@Override
	public Replenish readyReplenishDataForCommission(Replenish replenish, ManagerUser userInfo, String scheme) {
		Long currentUserId= userInfo.getID();   
		//如果是总监补货时，是以出货会员补货的，所以补货人不用取当前用户id,而是直接从页面取
		if(!ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType())){
			replenish.setReplenishUserId(currentUserId);	//补货人id
		}
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_CHIEF)){//总监的拥金从交易设定里面取
			
			    //总监补货时，拥金取出货会员的拥金
			    UserCommission c = userCommissionLogic.queryUserPlayTypeCommissionByScheme(replenish.getReplenishUserId(),ManagerStaff.USER_TYPE_OUT_REPLENISH,replenish.getTypeCode(),scheme);
				if(c!=null){
					if("A".equals(replenish.getPlate())){
						replenish.setCommissionChief(BigDecimal.valueOf(c.getCommissionA()));  
					}else if("B".equals(replenish.getPlate())){
						replenish.setCommissionChief(BigDecimal.valueOf(c.getCommissionB()));  
					}else if("C".equals(replenish.getPlate())){
						replenish.setCommissionChief(BigDecimal.valueOf(c.getCommissionC()));  
					}
				}
		}else 
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_BRANCH)){
			BranchStaffExt branchStaffExt = branchStaffExtLogic.findById(userInfo.getID(), scheme);
			UserCommission uCommission = userCommissionLogic.queryUserPlayTypeCommissionByScheme(currentUserId,userInfo.getUserType(),replenish.getTypeCode(),scheme);
			List<UserCommissionDefault> commission = userCommissionDefaultLogic.queryCommissionDefault(Long.valueOf(branchStaffExt.getParentStaff()));
			
			if("A".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(uCommission.getCommissionA()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionA()));  //总监的拥金从交易设定里面取
			}else if("B".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(uCommission.getCommissionB()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionB()));  //总监的拥金从交易设定里面取
			}else if("C".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(uCommission.getCommissionC()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionC()));  //总监的拥金从交易设定里面取
			}
			
		}else 
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_STOCKHOLDER)){
			StockholderStaffExt stockholderStaffExt = stockholderStaffExtLogic.findById(userInfo.getID(),scheme);
			
			UserCommission meCommission = userCommissionLogic.queryUserPlayTypeCommissionByScheme(currentUserId,userInfo.getUserType(),replenish.getTypeCode(),scheme);
			UserCommission fCommission = userCommissionLogic.queryUserPlayTypeCommissionByScheme(Long.valueOf(stockholderStaffExt.getParentStaff()),ManagerStaff.USER_TYPE_BRANCH,replenish.getTypeCode(),scheme);
			List<UserCommissionDefault> commission = userCommissionDefaultLogic.queryCommissionDefault(stockholderStaffExt.getChiefStaff());
			
			if("A".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionA()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(meCommission.getCommissionA()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionA())); 
			}else if("B".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionB()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(meCommission.getCommissionB()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionB())); 
			}else if("C".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionC()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(meCommission.getCommissionC()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionC())); 
			}
		}else 
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_GEN_AGENT)){
			GenAgentStaffExt genAgentStaffExt = genAgentStaffExtLogic.findById(userInfo.getID(),scheme);
			
			UserCommission fCommission = userCommissionLogic.queryUserPlayTypeCommissionByScheme(Long.valueOf(genAgentStaffExt.getBranchStaff()),ManagerStaff.USER_TYPE_BRANCH,replenish.getTypeCode(),scheme);
			UserCommission sCommission = userCommissionLogic.queryUserPlayTypeCommissionByScheme(Long.valueOf(genAgentStaffExt.getParentStaff()),ManagerStaff.USER_TYPE_STOCKHOLDER,replenish.getTypeCode(),scheme);
			UserCommission meCommission = userCommissionLogic.queryUserPlayTypeCommissionByScheme(currentUserId,userInfo.getUserType(),replenish.getTypeCode(),scheme);
			List<UserCommissionDefault> commission = userCommissionDefaultLogic.queryCommissionDefault(genAgentStaffExt.getChiefStaff());
			 
			if("A".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionA()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(sCommission.getCommissionA()));				
				replenish.setCommissionGenAgent(BigDecimal.valueOf(meCommission.getCommissionA()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionA()));
			}else if("B".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionB()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(sCommission.getCommissionB()));				
				replenish.setCommissionGenAgent(BigDecimal.valueOf(meCommission.getCommissionB()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionB()));
			}else if("C".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionC()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(sCommission.getCommissionC()));				
				replenish.setCommissionGenAgent(BigDecimal.valueOf(meCommission.getCommissionC()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionC()));
			}
		}else 
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_AGENT)){
			AgentStaffExt agentStaffExt = agentStaffExtLogic.findByID(userInfo.getID(),scheme);
			
			UserCommission fCommission = userCommissionLogic.queryUserPlayTypeCommissionByScheme(Long.valueOf(agentStaffExt.getBranchStaff()),ManagerStaff.USER_TYPE_BRANCH,replenish.getTypeCode(),scheme);
			UserCommission sCommission = userCommissionLogic.queryUserPlayTypeCommissionByScheme(Long.valueOf(agentStaffExt.getStockholderStaff()),ManagerStaff.USER_TYPE_STOCKHOLDER,replenish.getTypeCode(),scheme);
			UserCommission gCommission = userCommissionLogic.queryUserPlayTypeCommissionByScheme(Long.valueOf(agentStaffExt.getParentStaff()),ManagerStaff.USER_TYPE_GEN_AGENT,replenish.getTypeCode(),scheme);
			UserCommission meCommission = userCommissionLogic.queryUserPlayTypeCommissionByScheme(currentUserId,userInfo.getUserType(),replenish.getTypeCode(),scheme);
			List<UserCommissionDefault> commission = userCommissionDefaultLogic.queryCommissionDefault(agentStaffExt.getChiefStaff());
			
			if("A".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionA()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(sCommission.getCommissionA()));
				replenish.setCommissionGenAgent(BigDecimal.valueOf(gCommission.getCommissionA()));
				replenish.setCommissionAgent(BigDecimal.valueOf(meCommission.getCommissionA()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionA())); 
			}else if("B".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionB()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(sCommission.getCommissionB()));
				replenish.setCommissionGenAgent(BigDecimal.valueOf(gCommission.getCommissionB()));
				replenish.setCommissionAgent(BigDecimal.valueOf(meCommission.getCommissionB()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionB())); 
			}else if("C".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionC()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(sCommission.getCommissionC()));
				replenish.setCommissionGenAgent(BigDecimal.valueOf(gCommission.getCommissionC()));
				replenish.setCommissionAgent(BigDecimal.valueOf(meCommission.getCommissionC()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionC())); 
			}
		}	
		
		return replenish;
	}
	
	
	public Replenish readyReplenishDataForCommission(Replenish replenish,ManagerUser userInfo){
		Long currentUserId= userInfo.getID();   
		
		//如果是总监补货时，是以出货会员补货的，所以补货人不用取当前用户id,而是直接从页面取
		if(!ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType())){
			replenish.setReplenishUserId(currentUserId);	//补货人id
		}
		
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_CHIEF)){//总监的拥金从交易设定里面取
			
			    //总监补货时，拥金取出货会员的拥金
			    UserCommission c = userCommissionLogic.queryUserPlayTypeCommission(replenish.getReplenishUserId(),ManagerStaff.USER_TYPE_OUT_REPLENISH,replenish.getTypeCode());
				if(c!=null){
					if("A".equals(replenish.getPlate())){
						replenish.setCommissionChief(BigDecimal.valueOf(c.getCommissionA()));  
					}else if("B".equals(replenish.getPlate())){
						replenish.setCommissionChief(BigDecimal.valueOf(c.getCommissionB()));  
					}else if("C".equals(replenish.getPlate())){
						replenish.setCommissionChief(BigDecimal.valueOf(c.getCommissionC()));  
					}
				}
		}else 
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_BRANCH)){
			BranchStaffExt branchStaffExt = branchStaffExtLogic.queryBranchStaffExt("ID", userInfo.getID());
			UserCommission uCommission = userCommissionLogic.queryUserPlayTypeCommission(currentUserId,userInfo.getUserType(),replenish.getTypeCode());
			List<UserCommissionDefault> commission = userCommissionDefaultLogic.queryCommissionDefault(Long.valueOf(branchStaffExt.getParentStaff()));
			
			if("A".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(uCommission.getCommissionA()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionA()));  //总监的拥金从交易设定里面取
			}else if("B".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(uCommission.getCommissionB()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionB()));  //总监的拥金从交易设定里面取
			}else if("C".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(uCommission.getCommissionC()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionC()));  //总监的拥金从交易设定里面取
			}
			
		}else 
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_STOCKHOLDER)){
			StockholderStaffExt stockholderStaffExt = stockholderStaffExtLogic.queryStockholderStaffExt("ID", userInfo.getID());
			
			UserCommission meCommission = userCommissionLogic.queryUserPlayTypeCommission(currentUserId,userInfo.getUserType(),replenish.getTypeCode());
			UserCommission fCommission = userCommissionLogic.queryUserPlayTypeCommission(Long.valueOf(stockholderStaffExt.getParentStaff()),ManagerStaff.USER_TYPE_BRANCH,replenish.getTypeCode());
			List<UserCommissionDefault> commission = userCommissionDefaultLogic.queryCommissionDefault(stockholderStaffExt.getChiefStaff());
			
			if("A".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionA()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(meCommission.getCommissionA()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionA())); 
			}else if("B".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionB()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(meCommission.getCommissionB()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionB())); 
			}else if("C".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionC()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(meCommission.getCommissionC()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionC())); 
			}
		}else 
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_GEN_AGENT)){
			GenAgentStaffExt genAgentStaffExt = genAgentStaffExtLogic.queryGenAgentStaffExt("ID", userInfo.getID());
			
			UserCommission fCommission = userCommissionLogic.queryUserPlayTypeCommission(Long.valueOf(genAgentStaffExt.getBranchStaff()),ManagerStaff.USER_TYPE_BRANCH,replenish.getTypeCode());
			UserCommission sCommission = userCommissionLogic.queryUserPlayTypeCommission(Long.valueOf(genAgentStaffExt.getParentStaff()),ManagerStaff.USER_TYPE_STOCKHOLDER,replenish.getTypeCode());
			UserCommission meCommission = userCommissionLogic.queryUserPlayTypeCommission(currentUserId,userInfo.getUserType(),replenish.getTypeCode());
			List<UserCommissionDefault> commission = userCommissionDefaultLogic.queryCommissionDefault(genAgentStaffExt.getChiefStaff());
			 
			if("A".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionA()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(sCommission.getCommissionA()));				
				replenish.setCommissionGenAgent(BigDecimal.valueOf(meCommission.getCommissionA()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionA()));
			}else if("B".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionB()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(sCommission.getCommissionB()));				
				replenish.setCommissionGenAgent(BigDecimal.valueOf(meCommission.getCommissionB()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionB()));
			}else if("C".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionC()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(sCommission.getCommissionC()));				
				replenish.setCommissionGenAgent(BigDecimal.valueOf(meCommission.getCommissionC()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionC()));
			}
		}else 
		if(userInfo.getUserType().equals(ManagerStaff.USER_TYPE_AGENT)){
			AgentStaffExt agentStaffExt = agentStaffExtLogic.queryAgenStaffExt("ID", userInfo.getID());
			
			UserCommission fCommission = userCommissionLogic.queryUserPlayTypeCommission(Long.valueOf(agentStaffExt.getBranchStaff()),ManagerStaff.USER_TYPE_BRANCH,replenish.getTypeCode());
			UserCommission sCommission = userCommissionLogic.queryUserPlayTypeCommission(Long.valueOf(agentStaffExt.getStockholderStaff()),ManagerStaff.USER_TYPE_STOCKHOLDER,replenish.getTypeCode());
			UserCommission gCommission = userCommissionLogic.queryUserPlayTypeCommission(Long.valueOf(agentStaffExt.getParentStaff()),ManagerStaff.USER_TYPE_GEN_AGENT,replenish.getTypeCode());
			UserCommission meCommission = userCommissionLogic.queryUserPlayTypeCommission(currentUserId,userInfo.getUserType(),replenish.getTypeCode());
			List<UserCommissionDefault> commission = userCommissionDefaultLogic.queryCommissionDefault(agentStaffExt.getChiefStaff());
			
			if("A".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionA()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(sCommission.getCommissionA()));
				replenish.setCommissionGenAgent(BigDecimal.valueOf(gCommission.getCommissionA()));
				replenish.setCommissionAgent(BigDecimal.valueOf(meCommission.getCommissionA()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionA())); 
			}else if("B".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionB()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(sCommission.getCommissionB()));
				replenish.setCommissionGenAgent(BigDecimal.valueOf(gCommission.getCommissionB()));
				replenish.setCommissionAgent(BigDecimal.valueOf(meCommission.getCommissionB()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionB())); 
			}else if("C".equals(replenish.getPlate())){
				replenish.setCommissionBranch(BigDecimal.valueOf(fCommission.getCommissionC()));
				replenish.setCommissionStockHolder(BigDecimal.valueOf(sCommission.getCommissionC()));
				replenish.setCommissionGenAgent(BigDecimal.valueOf(gCommission.getCommissionC()));
				replenish.setCommissionAgent(BigDecimal.valueOf(meCommission.getCommissionC()));
				replenish.setCommissionChief(BigDecimal.valueOf(commission.get(0).getCommissionC())); 
			}
		}	
		
		return replenish;
	}
	
	//广东遗漏
    public Map<String,ZhanDangVO> notAppearCnt(Map<String,ZhanDangVO> map)
	{
    	List<GDPeriodsInfo> periodInfoList=periodsInfoLogic.queryTodayPeriods();
		List<List<String>> listBallAll=new ArrayList<List<String>>();
		for (int i = 0;  i< periodInfoList.size(); i++) {
			GDPeriodsInfo periondInfo=periodInfoList.get(i);
			String b1=periondInfo.getResult1()+"";
			String b2=periondInfo.getResult2()+"";
			String b3=periondInfo.getResult3()+"";
			String b4=periondInfo.getResult4()+"";
			String b5=periondInfo.getResult5()+"";
			String b6=periondInfo.getResult6()+"";
			String b7=periondInfo.getResult7()+"";
			String b8=periondInfo.getResult8()+"";
						
			List<String> listBallForOnePer=new ArrayList<String>();
			listBallForOnePer.add(b1);
			listBallForOnePer.add(b2);
			listBallForOnePer.add(b3);
			listBallForOnePer.add(b4);
			listBallForOnePer.add(b5);
			listBallForOnePer.add(b6);
			listBallForOnePer.add(b7);
			listBallForOnePer.add(b8);
			listBallAll.add(listBallForOnePer);
		}
		
		for (int i = 1; i <= 20; i++) {
			String key=Integer.valueOf(i).toString();
			Integer cnt=new Integer(0);
			ZhanDangVO vo = new ZhanDangVO();
			for (int j = 0; j < listBallAll.size(); j++) {
				List<String> onePeriondNum=listBallAll.get(j);
				if(onePeriondNum.indexOf(key)!=-1)
				{	
					vo.setYLsum(cnt);
					map.put(key, vo);
					break;
				}
				else
				{
					cnt++;
				}
				
			}
			vo.setYLsum(cnt);
			map.put(key, vo);
			
		}
		return map;
	}	
    //农场遗漏
    @Override
    public Map<String,ZhanDangVO> notAppearCntForNc(Map<String,ZhanDangVO> map)
    {
    	List<NCPeriodsInfo> periodInfoList=ncPeriodsInfoLogic.queryTodayPeriods();
    	List<List<String>> listBallAll=new ArrayList<List<String>>();
    	for (int i = 0;  i< periodInfoList.size(); i++) {
    		NCPeriodsInfo periondInfo=periodInfoList.get(i);
    		String b1=periondInfo.getResult1()+"";
    		String b2=periondInfo.getResult2()+"";
    		String b3=periondInfo.getResult3()+"";
    		String b4=periondInfo.getResult4()+"";
    		String b5=periondInfo.getResult5()+"";
    		String b6=periondInfo.getResult6()+"";
    		String b7=periondInfo.getResult7()+"";
    		String b8=periondInfo.getResult8()+"";
    		
    		List<String> listBallForOnePer=new ArrayList<String>();
    		listBallForOnePer.add(b1);
    		listBallForOnePer.add(b2);
    		listBallForOnePer.add(b3);
    		listBallForOnePer.add(b4);
    		listBallForOnePer.add(b5);
    		listBallForOnePer.add(b6);
    		listBallForOnePer.add(b7);
    		listBallForOnePer.add(b8);
    		listBallAll.add(listBallForOnePer);
    	}
    	
    	for (int i = 1; i <= 20; i++) {
    		String key=Integer.valueOf(i).toString();
    		Integer cnt=new Integer(0);
    		ZhanDangVO vo = new ZhanDangVO();
    		for (int j = 0; j < listBallAll.size(); j++) {
    			List<String> onePeriondNum=listBallAll.get(j);
    			if(onePeriondNum.indexOf(key)!=-1)
    			{	
    				vo.setYLsum(cnt);
    				map.put(key, vo);
    				break;
    			}
    			else
    			{
    				cnt++;
    			}
    			
    		}
    		vo.setYLsum(cnt);
    		map.put(key, vo);
    		
    	}
    	return map;
    }	
	
	public Page<Replenish> findReplenishPet(Page<Replenish> page,Criterion... criterions) {
		return replenishHibDao.findPage(page, criterions);
	}
	
	private IReplenishDao replenishDao = null;
	private ICheckDao checkDao = null;
	private IReplenishHibDao replenishHibDao = null;
	private IReplenishHisDao replenishHisDao = null;
	private IReplenishCheckDao replenishCheckDao = null;
	private IPlayAmountDao  playAmountDao = null;
	private IShopOddsLogic shopOddsLogic;
	private IShopsLogic shopsLogic;
	private IBossLogLogic bossLogLogic; // 記錄系統日誌接口
	private ISubAccountInfoLogic subAccountInfoLogic;//子账号
	private IGDPeriodsInfoLogic periodsInfoLogic;
	private IPlayTypeLogic playTypeLogic;
	private IReplenishAutoLogic replenishAutoLogic;
	private IShopsPlayOddsLogLogic shopsPlayOddsLogLogic;
	private IUserCommissionLogic userCommissionLogic;
	private IMemberStaffExtLogic memberStaffExtLogic;
	private IUserCommissionDefault userCommissionDefaultLogic;
	private IBranchStaffExtLogic branchStaffExtLogic;// 公公司
	private IStockholderStaffExtLogic stockholderStaffExtLogic;
	private IGenAgentStaffExtLogic genAgentStaffExtLogic; // 总代理
	private IAgentStaffExtLogic agentStaffExtLogic; // 代理
	private IReplenishLogic replenishLogic;
	private INCPeriodsInfoLogic ncPeriodsInfoLogic;
	private IChiefStaffExtLogic chiefStaffExtLogic;
	
	public IChiefStaffExtLogic getChiefStaffExtLogic() {
		return chiefStaffExtLogic;
	}

	public void setChiefStaffExtLogic(IChiefStaffExtLogic chiefStaffExtLogic) {
		this.chiefStaffExtLogic = chiefStaffExtLogic;
	}

	public IReplenishDao getReplenishDao() {
		return replenishDao;
	}

	public void setReplenishDao(IReplenishDao replenishDao) {
		this.replenishDao = replenishDao;
	}

	public ICheckDao getCheckDao() {
		return checkDao;
	}

	public void setCheckDao(ICheckDao checkDao) {
		this.checkDao = checkDao;
	}

	public IReplenishHibDao getReplenishHibDao() {
		return replenishHibDao;
	}

	public void setReplenishHibDao(IReplenishHibDao replenishHibDao) {
		this.replenishHibDao = replenishHibDao;
	}

	public IReplenishHisDao getReplenishHisDao() {
		return replenishHisDao;
	}

	public void setReplenishHisDao(IReplenishHisDao replenishHisDao) {
		this.replenishHisDao = replenishHisDao;
	}

	public IReplenishCheckDao getReplenishCheckDao() {
		return replenishCheckDao;
	}

	public void setReplenishCheckDao(IReplenishCheckDao replenishCheckDao) {
		this.replenishCheckDao = replenishCheckDao;
	}

	public IPlayAmountDao getPlayAmountDao() {
		return playAmountDao;
	}

	public void setPlayAmountDao(IPlayAmountDao playAmountDao) {
		this.playAmountDao = playAmountDao;
	}

	public IShopOddsLogic getShopOddsLogic() {
		return shopOddsLogic;
	}

	public void setShopOddsLogic(IShopOddsLogic shopOddsLogic) {
		this.shopOddsLogic = shopOddsLogic;
	}

	public IShopsLogic getShopsLogic() {
		return shopsLogic;
	}

	public void setShopsLogic(IShopsLogic shopsLogic) {
		this.shopsLogic = shopsLogic;
	}

	public IBossLogLogic getBossLogLogic() {
		return bossLogLogic;
	}

	public void setBossLogLogic(IBossLogLogic bossLogLogic) {
		this.bossLogLogic = bossLogLogic;
	}

	public IPlayTypeLogic getPlayTypeLogic() {
		return playTypeLogic;
	}

	public IReplenishAutoLogic getReplenishAutoLogic() {
		return replenishAutoLogic;
	}
	public void setReplenishAutoLogic(IReplenishAutoLogic replenishAutoLogic) {
		this.replenishAutoLogic = replenishAutoLogic;
	}

	public IShopsPlayOddsLogLogic getShopsPlayOddsLogLogic() {
		return shopsPlayOddsLogLogic;
	}

	public void setShopsPlayOddsLogLogic(
			IShopsPlayOddsLogLogic shopsPlayOddsLogLogic) {
		this.shopsPlayOddsLogLogic = shopsPlayOddsLogLogic;
	}

	public void setPlayTypeLogic(IPlayTypeLogic playTypeLogic) {
		this.playTypeLogic = playTypeLogic;
	}

	public ISubAccountInfoLogic getSubAccountInfoLogic() {
		return subAccountInfoLogic;
	}
	
	public void setSubAccountInfoLogic(ISubAccountInfoLogic subAccountInfoLogic) {
		this.subAccountInfoLogic = subAccountInfoLogic;
	}

	public IGDPeriodsInfoLogic getPeriodsInfoLogic() {
		return periodsInfoLogic;
	}

	public void setPeriodsInfoLogic(IGDPeriodsInfoLogic periodsInfoLogic) {
		this.periodsInfoLogic = periodsInfoLogic;
	}

	public IUserCommissionLogic getUserCommissionLogic() {
		return userCommissionLogic;
	}

	public void setUserCommissionLogic(IUserCommissionLogic userCommissionLogic) {
		this.userCommissionLogic = userCommissionLogic;
	}

	public IMemberStaffExtLogic getMemberStaffExtLogic() {
		return memberStaffExtLogic;
	}

	public void setMemberStaffExtLogic(IMemberStaffExtLogic memberStaffExtLogic) {
		this.memberStaffExtLogic = memberStaffExtLogic;
	}

	public IUserCommissionDefault getUserCommissionDefaultLogic() {
		return userCommissionDefaultLogic;
	}

	public void setUserCommissionDefaultLogic(
			IUserCommissionDefault userCommissionDefaultLogic) {
		this.userCommissionDefaultLogic = userCommissionDefaultLogic;
	}

	public IBranchStaffExtLogic getBranchStaffExtLogic() {
		return branchStaffExtLogic;
	}

	public void setBranchStaffExtLogic(IBranchStaffExtLogic branchStaffExtLogic) {
		this.branchStaffExtLogic = branchStaffExtLogic;
	}

	public IStockholderStaffExtLogic getStockholderStaffExtLogic() {
		return stockholderStaffExtLogic;
	}

	public void setStockholderStaffExtLogic(
			IStockholderStaffExtLogic stockholderStaffExtLogic) {
		this.stockholderStaffExtLogic = stockholderStaffExtLogic;
	}

	public IGenAgentStaffExtLogic getGenAgentStaffExtLogic() {
		return genAgentStaffExtLogic;
	}

	public void setGenAgentStaffExtLogic(
			IGenAgentStaffExtLogic genAgentStaffExtLogic) {
		this.genAgentStaffExtLogic = genAgentStaffExtLogic;
	}

	public IAgentStaffExtLogic getAgentStaffExtLogic() {
		return agentStaffExtLogic;
	}

	public void setAgentStaffExtLogic(IAgentStaffExtLogic agentStaffExtLogic) {
		this.agentStaffExtLogic = agentStaffExtLogic;
	}

	public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}

	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}
	
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public INCPeriodsInfoLogic getNcPeriodsInfoLogic() {
		return ncPeriodsInfoLogic;
	}

	public void setNcPeriodsInfoLogic(INCPeriodsInfoLogic ncPeriodsInfoLogic) {
		this.ncPeriodsInfoLogic = ncPeriodsInfoLogic;
	}


	

}
