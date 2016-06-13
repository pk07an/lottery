package com.npc.lottery.member.logic.spring;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.boss.logic.interf.IShopsLogic;
import com.npc.lottery.common.ConditionData;
import com.npc.lottery.common.Constant;
import com.npc.lottery.member.dao.hibernate.PlayAmountJdbcDao;
import com.npc.lottery.member.dao.interf.IBetDao;
import com.npc.lottery.member.dao.interf.ICancelPetDao;
import com.npc.lottery.member.dao.interf.ICancelPetLogDao;
import com.npc.lottery.member.dao.interf.IPlayAmountDao;
import com.npc.lottery.member.entity.BalanceInfo;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.BillSearchVo;
import com.npc.lottery.member.entity.CQandGDReportInfo;
import com.npc.lottery.member.entity.CancelPet;
import com.npc.lottery.member.entity.CancelPetLog;
import com.npc.lottery.member.entity.PlayAmount;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.logic.interf.IBetLogic;
import com.npc.lottery.odds.entity.OpenPlayOdds;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.entity.PeriodsNumVo;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoLogic;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.statreport.entity.CqsscHis;
import com.npc.lottery.statreport.entity.GdklsfHis;
import com.npc.lottery.statreport.entity.HklhcHis;
import com.npc.lottery.statreport.logic.interf.ICqsscHisLogic;
import com.npc.lottery.statreport.logic.interf.IGdklsfHisLogic;
import com.npc.lottery.statreport.logic.interf.IHklhcHisLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.sysmge.entity.ShopsPlayOddsLog;
import com.npc.lottery.sysmge.logic.interf.IShopsPlayOddsLogLogic;
import com.npc.lottery.user.dao.interf.IMemberStaffExtDao;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.util.Page;
import com.npc.lottery.util.PlayTypeUtils;
import com.npc.lottery.util.WebTools;


/**
 * 功能逻辑处理类
 *
 * @author none
 *
 */
public class BetLogic implements IBetLogic {

    private Logger logger = Logger.getLogger(BetLogic.class);
	private IBetDao betDao = null;
	private IMemberStaffExtDao memberStaffExtDao = null;
	private IPlayAmountDao  playAmountDao = null;
	private ICancelPetDao  cancelPetDao = null;
	private ICancelPetLogDao  cancelPetLogDao = null;
	protected IShopOddsLogic shopOddsLogic;
	private ICqsscHisLogic cqsscHisLogic;
	private IGdklsfHisLogic gdklsfHisLogic;
	private IHklhcHisLogic hklhcHisLogic;
	private IShopsPlayOddsLogLogic shopsPlayOddsLogLogic;
	private IReplenishAutoLogic replenishAutoLogic;
	
	private TaskExecutor wcpTaskExecutor;//异步执行
	
	//add by peter
	private IShopsLogic shopsLogic;
	private IReplenishLogic replenishLogic;
	
	private PlayAmountJdbcDao playAmountJdbcDao;
    
	
	/**
	 * 自动补货操作
	 * @param ballList
	 * @param memberStaff
	 * @param playType  玩法大类 常量传GDKLSF,CQSSC,BJ
	 */
	@Override
	public void autoReplenish(final List<BaseBet> ballList,MemberStaffExt memberStaff){
		
		
		wcpTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {

			try {
				List<BaseBet> result =  new ArrayList<BaseBet>();
				result.addAll(ballList);
				for(BaseBet betOrder:result){
					replenishAutoLogic.updateReplenishAutoForUser(betOrder);
			    }
			} catch (Throwable e) {
				logger.info("自动补货异步调出错！"+e.getMessage());

			}

			}

			});
	}
	
	/**
	 * 自动补货操作  新方法
	 * @param ballList
	 * @param memberStaff
	 * @param scheme
	 * @param playType  玩法大类 常量传GDKLSF,CQSSC,BJ
	 */
	@Override
	public void autoReplenish(final List<BaseBet> ballList, MemberStaffExt memberStaff,final String scheme) {
		wcpTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {

			try {
				List<BaseBet> result =  new ArrayList<BaseBet>();
				result.addAll(ballList);
				for(BaseBet betOrder:result){
					replenishAutoLogic.updateReplenishAutoForUser(betOrder,scheme);
			    }
			} catch (Throwable e) {
				logger.info("自动补货异步调出错！"+e.getMessage());

			}

			}

			});
		
	}
	
	/**
	 * 异步更行用户可用额度
	 * 
	 * @param memberStaff
	 */
	@Override
	public void updateMemberAvailableCreditLineAsyn(final MemberStaffExt memberStaff) {
		wcpTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				// 当前下注金额
				double betTotal = 0;
				// 今日输赢
				double todayWin = 0;
				// 信用额度
				double totalCreditLine = memberStaff.getTotalCreditLine().doubleValue();
				Page page = new Page();
				page.setPageSize(9999);
				page = betDao.querySSCUserBetDetail(page, memberStaff.getID());
				if (null != page) {
					betTotal = page.getTotal1();
					List<BaseBet> betList = page.getResult();
					final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
					for (BaseBet bet : betList) {
						Date bettingDate = bet.getBettingDate();
						bettingDate = DateUtils.addHours(bettingDate, -2);
						Date now = DateUtils.addHours(new Date(), -2);
						if (!sf.format(bettingDate).equals(sf.format(now))) {
							betTotal = betTotal - (bet.getMoney()*bet.getCount());
						}
					}
					
				}

				todayWin = betDao.queryTodayWinMoney(memberStaff.getID());

				// 用户可用额度 = 信用额度+今日输赢-当前下注金额
				double availableCreditLine = new BigDecimal(totalCreditLine).add(new BigDecimal(todayWin)).subtract(new BigDecimal(betTotal)).doubleValue();
				if (availableCreditLine <= 0) {
					// 如果小于0,设置当前可用额度为0
					availableCreditLine = 0;
				}
				logger.info("异步更新用户 : " + memberStaff.getAccount() + " 可用信用额度为 : " + availableCreditLine);
				memberStaffExtDao.updateMemberAvailableCreditLineById(availableCreditLine, memberStaff.getID());
				try {
				} catch (Exception e) {
					logger.info("异步更新用户 : " + memberStaff.getAccount() + "可用额度异常", e);
				}

			}

		});
	}
	
	/**
	 * 更新用户可用额度
	 * @param availableCreditLine
	 * @param id
	 */
	@Override
	public void updateMemberAvailableCreditLineById(double availableCreditLine, long id){
		memberStaffExtDao.updateMemberAvailableCreditLineById(availableCreditLine, id);
	}

	/**
	 * 通过报表获取用户可用额度
	 * 
	 * @param memberStaff
	 */
	@Override
	public double getMemberAvailableCreditLineRealTime(final MemberStaffExt memberStaff) {
		// 当前下注金额
		double betTotal = 0;
		// 今日输赢
		double todayWin = 0;
		// 信用额度
		double totalCreditLine = memberStaff.getTotalCreditLine().doubleValue();
		Page page = new Page();
		page.setPageSize(9999);
		page = betDao.querySSCUserBetDetail(page, memberStaff.getID());
		if (null != page) {
			betTotal = page.getTotal1();
			List<BaseBet> betList = page.getResult();
			final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			for (BaseBet bet : betList) {
				Date bettingDate = bet.getBettingDate();
				bettingDate = DateUtils.addHours(bettingDate, -2);
				Date now = DateUtils.addHours(new Date(), -2);
				if (!sf.format(bettingDate).equals(sf.format(now))) {
					betTotal = betTotal - (bet.getMoney() * bet.getCount());
				}
			}

		}

		todayWin = betDao.queryTodayWinMoney(memberStaff.getID());

		// 用户可用额度 = 信用额度+今日输赢-当前下注金额
		double availableCreditLine = new BigDecimal(totalCreditLine).add(new BigDecimal(todayWin)).subtract(new BigDecimal(betTotal)).doubleValue();
		if (availableCreditLine <= 0) {
			// 如果小于0,设置当前可用额度为0
			availableCreditLine = 0;
		}
		logger.info("用户 : " + memberStaff.getAccount() + " 可用信用额度为 : " + availableCreditLine);
		return availableCreditLine;
	}
	/**
	 * 农场投注保存方法
	 */
	@Override
	public void saveNCBet(List<BaseBet> ballList, MemberStaffExt memberStaff,boolean insertAttr,String shopCode) {
		betDao.batchInsert(ballList, Constant.NC_TABLE_NAME, insertAttr);
		updatePlayAmountAndShopRealOdds(ballList,shopCode,Constant.LOTTERY_TYPE_NC);

	}
	
	public void saveGDBet(List<BaseBet> betlList,MemberStaffExt memberStaff,String shopCode)
	{
		
		List<BaseBet> first=new ArrayList<BaseBet>();
		List<BaseBet> second=new ArrayList<BaseBet>();
		List<BaseBet> third=new ArrayList<BaseBet>();
		List<BaseBet> forth=new ArrayList<BaseBet>();
		List<BaseBet> fifth=new ArrayList<BaseBet>();
		List<BaseBet> sixth=new ArrayList<BaseBet>();
		List<BaseBet> seventh=new ArrayList<BaseBet>();
		List<BaseBet> eighth=new ArrayList<BaseBet>();
		List<BaseBet> doubleSide=new ArrayList<BaseBet>();
		List<BaseBet> straightthrough=new ArrayList<BaseBet>();
		
		
		String typeCode=null;
		for (int i = 0; i < betlList.size(); i++) {
			BaseBet bet=betlList.get(i);
			typeCode=bet.getPlayType();
			bet.setRemark("double");
			if(typeCode.indexOf("BALL_FIRST_")!=-1)
			{
				first.add(bet);
			}
			else if(typeCode.indexOf("BALL_SECOND_")!=-1)
			{
				second.add(bet);
			}
			else if(typeCode.indexOf("BALL_THIRD_")!=-1)
			{
				third.add(bet);
			}
			else if(typeCode.indexOf("BALL_FORTH_")!=-1)
			{
				forth.add(bet);
			}
			else if(typeCode.indexOf("BALL_FIFTH_")!=-1)
			{
				fifth.add(bet);
			}
			else if(typeCode.indexOf("BALL_SIXTH_")!=-1)
			{
				sixth.add(bet);
			}
			else if(typeCode.indexOf("BALL_SEVENTH_")!=-1)
			{
				seventh.add(bet);
			}
			else if(typeCode.indexOf("BALL_EIGHTH_")!=-1)
			{
				eighth.add(bet);
			}
			else if(typeCode.indexOf("DOUBLESIDE_")!=-1)
			{
				doubleSide.add(bet);
				
			}
			else if(typeCode.indexOf("STRAIGHTTHROUGH_")!=-1)
			{
				straightthrough.add(bet);
				
			}
			
		}
		
		if(first.size()!=0)
		{
			betDao.batchInsert(first,Constant.GDKLSF_BALL_FIRST_TABLE_NAME,false);
		}
		if(second.size()!=0)
		{
			betDao.batchInsert(second,Constant.GDKLSF_BALL_SECOND_TABLE_NAME,false);
		}
		if(third.size()!=0)
		{
			betDao.batchInsert(third,Constant.GDKLSF_BALL_THIRD_TABLE_NAME,false);
		}
		if(forth.size()!=0)
		{
			betDao.batchInsert(forth,Constant.GDKLSF_BALL_FORTH_TABLE_NAME,false);
		}
		if(fifth.size()!=0)
		{
			betDao.batchInsert(fifth,Constant.GDKLSF_BALL_FIFTH_TABLE_NAME,false);
		}
		if(sixth.size()!=0)
		{
			betDao.batchInsert(sixth,Constant.GDKLSF_BALL_SIXTH_TABLE_NAME,false);
		}
		if(seventh.size()!=0)
		{
			betDao.batchInsert(seventh,Constant.GDKLSF_BALL_SEVENTH_TABLE_NAME,false);
		}
		if(eighth.size()!=0)
		{
			betDao.batchInsert(eighth,Constant.GDKLSF_BALL_EIGHTH_TABLE_NAME,false);
		}
		if(doubleSide.size()!=0)
		{
			betDao.batchInsert(doubleSide,Constant.GDKLSF_DOUBLESIDE_TABLE_NAME,false);
		}
		
		if(straightthrough.size()!=0)
		{
			betDao.batchInsert(straightthrough,Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME,true);
		}
		
		updatePlayAmountAndShopRealOdds(betlList,shopCode,Constant.LOTTERY_TYPE_GDKLSF);
	}
	
	public void saveCQBet(List<BaseBet> ballList,MemberStaffExt memberStaff,String shopCode)
	{
		List<BaseBet> first=new ArrayList<BaseBet>();
		List<BaseBet> second=new ArrayList<BaseBet>();
		List<BaseBet> third=new ArrayList<BaseBet>();
		List<BaseBet> forth=new ArrayList<BaseBet>();
		List<BaseBet> fifth=new ArrayList<BaseBet>();
		
		String typeCode=null;
		for (int i = 0; i < ballList.size(); i++) {
			BaseBet bet=ballList.get(i);
			typeCode=bet.getPlayType();
			bet.setRemark("double");
			if(typeCode.indexOf("BALL_FIRST_")!=-1||typeCode.indexOf("DOUBLESIDE_1_")!=-1)
			{
				first.add(bet);
			}
			else if(typeCode.indexOf("BALL_SECOND_")!=-1||typeCode.indexOf("DOUBLESIDE_2_")!=-1)
			{
				second.add(bet);
			}
			else if(typeCode.indexOf("BALL_THIRD_")!=-1||typeCode.indexOf("DOUBLESIDE_3_")!=-1)
			{
				third.add(bet);
			}
			else if(typeCode.indexOf("BALL_FORTH_")!=-1||typeCode.indexOf("DOUBLESIDE_4_")!=-1)
			{
				forth.add(bet);
			}
			else if(typeCode.indexOf("BALL_FIFTH_")!=-1||typeCode.indexOf("DOUBLESIDE_5_")!=-1)
			{
				fifth.add(bet);
			}
			else
			{
				first.add(bet);
				
			}
		}
		//memberStaffExtDao.save(memberStaff);
		if(fifth.size()!=0)
		{
			betDao.batchInsert(fifth,Constant.CQSSC_BALL_FIFTH_TABLE_NAME,false);
		}
		if(forth.size()!=0)
		{
			betDao.batchInsert(forth,Constant.CQSSC_BALL_FORTH_TABLE_NAME,false);
		}
		if(third.size()!=0)
		{
			betDao.batchInsert(third,Constant.CQSSC_BALL_THIRD_TABLE_NAME,false);
		}
		if(second.size()!=0)
		{
			betDao.batchInsert(second,Constant.CQSSC_BALL_SECOND_TABLE_NAME,false);
		}
		if(first.size()!=0)
		{
			betDao.batchInsert(first,Constant.CQSSC_BALL_FIRST_TABLE_NAME,false);
		}
		
		
		
		
		updatePlayAmountAndShopRealOdds(ballList,shopCode,Constant.LOTTERY_TYPE_CQSSC);
	}
	public void saveBJSCBet(List<BaseBet> ballList,MemberStaffExt memberStaff,String shopCode)
	{
		betDao.batchInsert(ballList,Constant.BJSC_TABLE_NAME,false);
		updatePlayAmountAndShopRealOdds(ballList,shopCode,Constant.LOTTERY_TYPE_BJ);
		
	}
	
	
	
	public Page querySSCUserBet(Page page,Long userId)
	{
		
		return betDao.querySSCUserBetDetail(page, userId);
	}
	
	public Page queryBJSCUserBet(Page page,Long userId)
	{
		
		return betDao.queryBJSCUserBetDetail(page, userId);
	}
	
	
	
	public Page queryGDKLSFUserBet(Page page,Long userId)
	{
		
		return betDao.queryGDKLSFUserBetDetail(page, userId);
	}
	@Override
	public Page queryGDKLSFBetByObj(Page page,BillSearchVo entry)
	{
		Page  p = null;
		if("0".equals(entry.getLotteryType())) // 已结算 查询历史表
		{
			p = betDao.queryGDKLSFBetByObjHis(page, entry);
		}else{
			//未结算
			p = betDao.queryGDKLSFBetByObj(page, entry);
		}
		
		return p;
	}
	@Override
	public Page queryCQSSCBetByObj(Page page,BillSearchVo entry)
	{
		Page  p = null;
		if("0".equals(entry.getLotteryType())) // 已结算 查询历史表
		{
			p = betDao.queryCQSSCBetByObjHis(page, entry);
		}else{
			//未结算
			p = betDao.queryCQSSCBetByObj(page, entry);
		}
		return p;
	}
	
	@Override
	public Page queryBJSCBetByObj(Page page,BillSearchVo entry)
	{
		Page  p = null;
		if("0".equals(entry.getLotteryType())) // 已结算 查询历史表
		{
			p = betDao.queryBJSCBetByObjHis(page, entry);
		}else{
			//未结算
			p = betDao.queryBJSCBetByObj(page, entry);
		}
		return p;
	}
	@Override
	public Page queryReplishPage(Page page,BillSearchVo entry)
	{
		Page  p = null;
		// 补货不分 结算 还是 未结算
		p = betDao.queryReplenishByPage(page, entry);
		return p;
	}
	/**
	 * 总监 注单查询 
	 * @param page
	 * @param entry
	 * @return
	 */
	@Override
	public Page queryBillSerachPageAdmin(Page page,BillSearchVo entry)
	{
		Page  p = null;
		p = betDao.queryBetByObjAdmin(page, entry);
		return p;
	}
	/**
	 * 根据注单号 修改投注表记录-未结算的
	 * @param entity
	 */
	
	@Override
	public void updateCQSSCBet(String orderNum) {
		betDao.updateCQSSCBetByObj(orderNum);
	}
	/*@Override
	public void deleteCQSSCBet(String orderNum) {
		betDao.deleteCQSSCBetByObj(orderNum);
	}*/
	@Override
	public void updateGDKLSFBet(String orderNum) {
		betDao.updateGDKLSFBetByObj(orderNum);
	}
	/*@Override
	public void deleteGDKLSFBet(String orderNum) {
		betDao.deleteGDKLSFBetByObj(orderNum);
	}*/
	@Override
	public void updateBJSCBet(String orderNum) {
		betDao.updateBJSCBetByObj(orderNum);
	}
	/*@Override
	public void deleteBJSCBet(String orderNum) {
		betDao.deleteBJSCBetByObj(orderNum);
	}*/
	@Override
	public void updateReplenishStateByOrderNum(String orderNum,String state) {
		betDao.updateReplenishByObj(orderNum,state);
	}
	/**
	 * 删除 補貨表注单  add by Aaron20121113
	 * @param orderNum
	 * @return
	 */
	/*@Override
	public void deleteReplenishByOrderNum(String orderNum) {
		betDao.deleteReplenishByOrderNum(orderNum);
	}*/
	
	/**
	 * 删除 历史 補貨表注单  add by Aaron20121113
	 * @param orderNum
	 * @return
	 */
	/*@Override
	public void deleteHisReplenishByOrderNum(String orderNum) {
		betDao.deleteHisReplenishByOrderNum(orderNum);
	}*/
	/**
	 * 注销 历史 補貨表注单  add by peter,fixed by f
	 * @param orderNum
	 * @return
	 */
	@Override
	public void cancelHisReplenishByOrderNum(String typeCode, String orderNo, String periodsNum, String billType, String ip) {
		cancelPetDao.saveCancelPet(typeCode, orderNo, periodsNum, billType, CancelPetLog.OP_TYPE_CANCEL, ip);
		betDao.cancelHisReplenishByOrderNum(orderNo);
	}
	/**
	 * 恢复注销注单 历史 補貨表注单  add by f
	 * 
	 * @param orderNo
	 * @param typeCode
	 * @param periodsNum
	 * @param billType
	 * @param tableName
	 */
	@Override
	public void recoveryPet(String orderNo, String typeCode, String periodsNum, String billType, String ip) {
		cancelPetDao.recoveryPet(orderNo, typeCode, periodsNum, billType, CancelPetLog.OP_TYPE_RECOVERY, ip);
	}
	/**
	 * 删除 補貨表注单  add by Aaron20121122
	 * @param orderNum
	 * @return
	 */
    @Override
    public void updateReplenishByOrderNum(Integer money,String orderNum) {
    	betDao.updateReplenishByOrderNum(money,orderNum);
    }
    
	
	public Page queryCQSSCUserBet(Page page,Long userId)
	{
		
		return betDao.queryCQSSCUserBetDetail(page, userId);
	}
	
	public Page queryHKLHCUserBet(Page page,Long userId)
	{
		
		return betDao.queryHKLHCUserBetDetail(page, userId);
		
	}
	
	
	public IBetDao getBetDao() {
		return betDao;
	}
	public void setBetDao(IBetDao betDao) {
		this.betDao = betDao;
	}

	public IMemberStaffExtDao getMemberStaffExtDao() {
		return memberStaffExtDao;
	}

	public void setMemberStaffExtDao(IMemberStaffExtDao memberStaffExtDao) {
		this.memberStaffExtDao = memberStaffExtDao;
	}
	public ICancelPetDao getCancelPetDao() {
		return cancelPetDao;
	}

	public void setCancelPetDao(ICancelPetDao cancelPetDao) {
		this.cancelPetDao = cancelPetDao;
	}

	public ICancelPetLogDao getCancelPetLogDao() {
		return cancelPetLogDao;
	}

	public void setCancelPetLogDao(ICancelPetLogDao cancelPetLogDao) {
		this.cancelPetLogDao = cancelPetLogDao;
	}

	public Map<String,Double> getUserTreeCommission(List<Long> userTree,String userId,String playType,String plate)
	{
		Map<String,Double> commissionsMap=new HashMap<String,Double>();
		
		if(userTree==null||userTree.size()==0)
			return commissionsMap;
		List<UserCommission> commisionList=betDao.getManagerCommsion(userTree, playType);
		List<UserCommission> userCommisionList=betDao.getUserCommsion(userId, playType);
		commisionList.addAll(userCommisionList);
		for (int i = 0; i < commisionList.size(); i++) {
			UserCommission uc=commisionList.get(i);
			Double userCommission=null;
			if("A".equals(plate))
			{
			userCommission=uc.getCommissionA();
			}
			else if("B".equals(plate))
			{
				userCommission=uc.getCommissionB();
			}
			else if("C".equals(plate))
			{
				userCommission=uc.getCommissionC();
			}

			if(uc.getUserType().equals(MemberStaff.USER_TYPE_MEMBER))
			{
				commissionsMap.put(MemberStaff.USER_TYPE_MEMBER, userCommission);
			}
			else if(uc.getUserType().equals(ManagerStaff.USER_TYPE_AGENT))
			{
				commissionsMap.put(ManagerStaff.USER_TYPE_AGENT, userCommission);
			}
			else if(uc.getUserType().equals(ManagerStaff.USER_TYPE_GEN_AGENT))
			{
				commissionsMap.put(ManagerStaff.USER_TYPE_GEN_AGENT, userCommission);
			}
			else if(uc.getUserType().equals(ManagerStaff.USER_TYPE_STOCKHOLDER))
			{
				commissionsMap.put(ManagerStaff.USER_TYPE_STOCKHOLDER, userCommission);
			}
			else if(uc.getUserType().equals(ManagerStaff.USER_TYPE_BRANCH))
			{
				commissionsMap.put(ManagerStaff.USER_TYPE_BRANCH, userCommission);
			}
		}
		return commissionsMap;
	}
	public Double queryTotalCommissionMoney(String shopCode,String commissionType)
	{
		
		return betDao.queryTotalCommissionMoney(shopCode, commissionType);
	}
	
	public Integer queryUseritemQuotasMoney(String playType, String periodNum,String typeCode,String userId)
	{
		
		
		return betDao.queryUseritemQuotasMoney(playType, periodNum, typeCode, userId);
	}
	public Integer queryUserLMitemQuotasMoney(String playType, String periodNum,String typeCode,String userId,String attr)
	{
		return betDao.queryUserLMitemQuotasMoney(playType, periodNum, typeCode, userId,attr);
	}
	@Override
	public Integer queryUserLMitemQuotasMoneyForNC(String playType, String periodNum,String typeCode,String userId,String attr)
	{
		return betDao.queryUserLMitemQuotasMoneyForNC(playType, periodNum, typeCode, userId,attr);
	}
	
	public Integer queryTotalOddsMoney(String shopCode,String oddsType)
	{
		
		return betDao.queryTotalOddsMoney(shopCode, oddsType);
	}
	public IPlayAmountDao getPlayAmountDao() {
		return playAmountDao;
	}
	public void setPlayAmountDao(IPlayAmountDao playAmountDao) {
		this.playAmountDao = playAmountDao;
	}
	public double queryTodayWinMoney(Long userId)
	{
		
		return betDao.queryTodayWinMoney(userId);
		
	}
	
	private Map<String, PlayAmount> getPlayAmountByShopCodeAndPlayType(String shopCode, String playType) {
		Map<String, PlayAmount> playAmountMap = new HashMap<String, PlayAmount>();
		List<PlayAmount> playAmountList = playAmountJdbcDao.getPlayAmountByShopCodeAndPlayType(shopCode, playType);
		for (PlayAmount playAmount : playAmountList) {
			playAmountMap.put(playAmount.getTypeCode(), playAmount);
		}
		return playAmountMap;
	}
	
	private void updatePlayAmountAndShopRealOdds(List<BaseBet> ballList,String shopCode,String playTypeCode)
	{
		
		List<PlayAmount> playAmountList = new ArrayList<PlayAmount>();
		Map<String, PlayAmount> playAmountMap  =  this.getPlayAmountByShopCodeAndPlayType(shopCode, playTypeCode);
		Map<String, OpenPlayOdds> openShopOddsMap = shopOddsLogic.queryOpenPlayOddsMapByShopCode(shopCode);
		for (int i = 0; i < ballList.size(); i++) {
			BaseBet bet=ballList.get(i);
			Integer money=bet.getMoney();
			Long userID=bet.getBettingUserId();
			String typeCode=bet.getPlayType();
			PlayType playType=PlayTypeUtils.getPlayType(typeCode);
			String oddType=playType.getOddsType();
			Integer chiefRate=bet.getChiefRate();
			String periodsNum=bet.getPeriodsNum();
			OpenPlayOdds openOdd=openShopOddsMap.get(oddType);
			Integer quotas=openOdd.getAutoOddsQuotas();
	        BigDecimal autoMoney=openOdd.getAutoOdds();
	        
	        
	       PlayAmount playAmount=playAmountMap.get(typeCode);
	       
	       Double moneyAmount=playAmount.getMoneyAmount();
	       
	       //fixed by peter 修改自动降赔阙值错误问题
	       double replenishMoney=0;
			ShopsInfo shopsInfo = shopsLogic.findShopsCode(bet.getShopCode());
	       if(null!=shopsInfo){
				if(null!=shopsInfo.getChiefStaffExt()){
					replenishMoney = replenishLogic.queryReplenishForBetCheck(shopsInfo.getChiefStaffExt().getID(), ManagerUser.USER_TYPE_CHIEF, bet.getPlayType(), periodsNum).doubleValue();
				}
			}
			
	       int moneysAfterBet = (int)(moneyAmount+replenishMoney+money*chiefRate/100)/quotas;
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
			String ip =WebTools.getClientIpAddr(getRequest());
			log.setIp(ip);
			log.setType(Constant.ODD_LOG_AUTO);
			shopsPlayOddsLogLogic.saveLog(log);
	    	shopOddsLogic.updateShopOdds(shopOdds);
	    	   
	       }
	     
	       BigDecimal moneyAmountUpdate = BigDecimal.valueOf(playAmount.getMoneyAmount()).add(BigDecimal.valueOf(money*chiefRate).divide(BigDecimal.valueOf(100)));
	       playAmount.setMoneyAmount(moneyAmountUpdate.doubleValue());
	       
	       playAmountList.add(playAmount);
			
		}
		//批量更新playamount
		if(!CollectionUtils.isEmpty(playAmountList)){
			playAmountJdbcDao.updatePlayAmountBatchById(playAmountList);
		}
		
	}
	
	public List<BalanceInfo> queryCQBalance(List<String> list,Long userMemberID){
	    List<CqsscHis>  berfCQsscHisList = new ArrayList<CqsscHis>();
	    Calendar calendar=Calendar.getInstance();
	       calendar.setTime(java.sql.Date.valueOf(list.get(6).split(" ")[0]));
	       calendar.add(Calendar.DATE, 1);     // 加一天
	       logger.info(calendar.getTime());
	    ConditionData conditionData = new ConditionData();
        conditionData.addBetween("bettingDate",java.sql.Date.valueOf(list.get(0).split(" ")[0]) , true,
                calendar.getTime(), false);
        conditionData.addEqual("bettingUserID", userMemberID);
	    berfCQsscHisList = cqsscHisLogic.findCqsscHisList(conditionData, 1, 100000);
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    List<CqsscHis> oneCqHisList = new ArrayList<CqsscHis>();
	    List<CqsscHis> twoCqHisList = new ArrayList<CqsscHis>();
	    List<CqsscHis> threeCqHisList = new ArrayList<CqsscHis>();
	    List<CqsscHis> fourCqHisList = new ArrayList<CqsscHis>();
	    List<CqsscHis> fiveCqHisList = new ArrayList<CqsscHis>();
	    List<CqsscHis> sixCqHisList = new ArrayList<CqsscHis>();
	    List<CqsscHis> sevenCqHisList = new ArrayList<CqsscHis>();
	    String bettingDate;
	    for (int i = 0; i < berfCQsscHisList.size(); i++) {
	        bettingDate = dateFormat.format(berfCQsscHisList.get(i).getBettingDate());
          if(bettingDate.equals(list.get(0).split(" ")[0])){
              oneCqHisList.add(berfCQsscHisList.get(i));
          }else if(bettingDate.equals(list.get(1).split(" ")[0])){
              twoCqHisList.add(berfCQsscHisList.get(i));
          }else if(bettingDate.equals(list.get(2).split(" ")[0])){
              threeCqHisList.add(berfCQsscHisList.get(i));
          }else if(bettingDate.equals(list.get(3).split(" ")[0])){
              fourCqHisList.add(berfCQsscHisList.get(i));
          }else if(bettingDate.equals(list.get(4).split(" ")[0])){
              fiveCqHisList.add(berfCQsscHisList.get(i));
          }else if(bettingDate.equals(list.get(5).split(" ")[0])){
              sixCqHisList.add(berfCQsscHisList.get(i));
          }else if(bettingDate.equals(list.get(6).split(" ")[0])){
              sevenCqHisList.add(berfCQsscHisList.get(i));
          }
        }
	    List<BalanceInfo> balanceInfoList = new ArrayList<BalanceInfo>();
	    BalanceInfo balanceInfo = new BalanceInfo();
	    balanceInfo.setTransactionType(list.get(0));//上周星期一的数据
	    balanceInfo = getTypeBalance(oneCqHisList,balanceInfo);
	    balanceInfoList.add(balanceInfo);//这是上周星期一的数据
	    
	    //上周星期二数据
	    BalanceInfo balanceInfo1 = new BalanceInfo();
	    balanceInfo1 = getTypeBalance(twoCqHisList,balanceInfo1);
	    balanceInfo1.setTransactionType(list.get(1));//上周星期二的数据
	    balanceInfoList.add(balanceInfo1);//这是上周星期二的数据
	    
	    //上周星期三数据
        BalanceInfo balanceInfo2 = new BalanceInfo();
        balanceInfo2 = getTypeBalance(threeCqHisList,balanceInfo2);
        balanceInfo2.setTransactionType(list.get(2));//上周星期三的数据
        balanceInfoList.add(balanceInfo2);//这是上周星期三的数据
        
      //上周星期四数据
        BalanceInfo balanceInfo3 = new BalanceInfo();
        balanceInfo3 = getTypeBalance(fourCqHisList,balanceInfo3);
        balanceInfo3.setTransactionType(list.get(3));//上周星期四的数据
        balanceInfoList.add(balanceInfo3);//这是上周星期四的数据
        
      //上周星期五数据
        BalanceInfo balanceInfo4 = new BalanceInfo();
        balanceInfo4 = getTypeBalance(fiveCqHisList,balanceInfo4);
        balanceInfo4.setTransactionType(list.get(4));//上周星期五的数据
        balanceInfoList.add(balanceInfo4);//这是上周星期五的数据
        
      //上周星期六数据
        BalanceInfo balanceInfo5 = new BalanceInfo();
        balanceInfo5 = getTypeBalance(sixCqHisList,balanceInfo5);
        balanceInfo5.setTransactionType(list.get(5));//上周星期六的数据
        balanceInfoList.add(balanceInfo5);//这是上周星期六的数据
        
      //上周星期天数据
        BalanceInfo balanceInfo6 = new BalanceInfo();
        balanceInfo6 = getTypeBalance(sevenCqHisList,balanceInfo6);
        balanceInfo6.setTransactionType(list.get(6));//上周星期天的数据
        balanceInfoList.add(balanceInfo6);//这是上周星期天的数据
        
        
        //当前一周的数据
        calendar.setTime(java.sql.Date.valueOf(list.get(13).split(" ")[0]));
        calendar.add(Calendar.DATE, 1);     // 加一天
       // System.out.println(calendar.getTime());
	    List<CqsscHis>  cqsscHisList = new ArrayList<CqsscHis>();
	    conditionData.addBetween("bettingDate",java.sql.Date.valueOf(list.get(7).split(" ")[0]) , true,
	            calendar.getTime(), false);
	    conditionData.addEqual("bettingUserID", userMemberID);
	    cqsscHisList = cqsscHisLogic.findCqsscHisList(conditionData, 1, 100000);
	    List<CqsscHis> onlyOneCqHisList = new ArrayList<CqsscHis>();
        List<CqsscHis> onlyTwoCqHisList = new ArrayList<CqsscHis>();
        List<CqsscHis> onlyThreeCqHisList = new ArrayList<CqsscHis>();
        List<CqsscHis> onlyFourCqHisList = new ArrayList<CqsscHis>();
        List<CqsscHis> onlyFiveCqHisList = new ArrayList<CqsscHis>();
        List<CqsscHis> onlySixCqHisList = new ArrayList<CqsscHis>();
        List<CqsscHis> onlySevenCqHisList = new ArrayList<CqsscHis>();
        for (int i = 0; i < cqsscHisList.size(); i++) {
            bettingDate = dateFormat.format(cqsscHisList.get(i).getBettingDate());
          if(bettingDate.equals(list.get(7).split(" ")[0])){
              onlyOneCqHisList.add(cqsscHisList.get(i));
          }else if(bettingDate.equals(list.get(8).split(" ")[0])){
              onlyTwoCqHisList.add(cqsscHisList.get(i));
          }else if(bettingDate.equals(list.get(9).split(" ")[0])){
              onlyThreeCqHisList.add(cqsscHisList.get(i));
          }else if(bettingDate.equals(list.get(10).split(" ")[0])){
              onlyFourCqHisList.add(cqsscHisList.get(i));
          }else if(bettingDate.equals(list.get(11).split(" ")[0])){
              onlyFiveCqHisList.add(cqsscHisList.get(i));
          }else if(bettingDate.equals(list.get(12).split(" ")[0])){
              onlySixCqHisList.add(cqsscHisList.get(i));
          }else if(bettingDate.equals(list.get(13).split(" ")[0])){
              onlySevenCqHisList.add(cqsscHisList.get(i));
          }
        }
        BalanceInfo onlyBalanceInfo = new BalanceInfo();
        onlyBalanceInfo.setTransactionType(list.get(7));//当前一周星期一的数据
        onlyBalanceInfo = getTypeBalance(onlyOneCqHisList,onlyBalanceInfo);
        balanceInfoList.add(onlyBalanceInfo);//这是当前一周星期一的数据
        
        //当前一周星期二的数据
        BalanceInfo onlyBalanceInfo1 = new BalanceInfo();
        onlyBalanceInfo1.setTransactionType(list.get(8));//当前一周星期二的数据
        onlyBalanceInfo1 = getTypeBalance(onlyTwoCqHisList,onlyBalanceInfo1);
        balanceInfoList.add(onlyBalanceInfo1);//这是当前一周星期一的数据
        
        //当前一周星期三的数据
        BalanceInfo onlyBalanceInfo2 = new BalanceInfo();
        onlyBalanceInfo2.setTransactionType(list.get(9));//当前一周星期三的数据
        onlyBalanceInfo2 = getTypeBalance(onlyThreeCqHisList,onlyBalanceInfo2);
        balanceInfoList.add(onlyBalanceInfo2);//这是当前一周星期三的数据
        
        //当前一周星期四的数据
        BalanceInfo onlyBalanceInfo3 = new BalanceInfo();
        onlyBalanceInfo3.setTransactionType(list.get(10));//当前一周星期四的数据
        onlyBalanceInfo3 = getTypeBalance(onlyFourCqHisList,onlyBalanceInfo3);
        balanceInfoList.add(onlyBalanceInfo3);//这是当前一周星期四的数据
        
      //当前一周星期五的数据
        BalanceInfo onlyBalanceInfo4 = new BalanceInfo();
        onlyBalanceInfo4.setTransactionType(list.get(11));//当前一周星期五的数据
        onlyBalanceInfo4 = getTypeBalance(onlyFiveCqHisList,onlyBalanceInfo4);
        balanceInfoList.add(onlyBalanceInfo4);//这是当前一周星期五的数据
        
      //当前一周星期六的数据
        BalanceInfo onlyBalanceInfo5 = new BalanceInfo();
        onlyBalanceInfo5.setTransactionType(list.get(12));//当前一周星期六的数据
        onlyBalanceInfo5 = getTypeBalance(onlySixCqHisList,onlyBalanceInfo5);
        balanceInfoList.add(onlyBalanceInfo5);//这是当前一周星期六的数据
        
      //当前一周星期天的数据
        BalanceInfo onlyBalanceInfo6 = new BalanceInfo();
        onlyBalanceInfo6.setTransactionType(list.get(13));//当前一周星期天的数据
        onlyBalanceInfo6 = getTypeBalance(onlySevenCqHisList,onlyBalanceInfo6);
        balanceInfoList.add(onlyBalanceInfo6);//这是当前一周星期天的数据
        
	    return balanceInfoList;
	}
	private BalanceInfo getTypeBalance(List<CqsscHis> list,BalanceInfo balanceInfo){
	    double moneyCount = 0;//一共下了多少钱
        double winnWaterResult = 0;//中奖的结果
        double notWaterResult = 0;//没中奖的结果
        double waterResult = 0;    //退水后的结果
        double commission = 0;     //应该退多少水
        double commissionResult = 0;//今天一共退了多少钱
        double moneyFlag = 0;//一共赢了多少钱
      //星期一的数据
        for (CqsscHis cqsscHis : list) {
            moneyCount += cqsscHis.getMoney();
            if(CqsscHis.WINNING_LOTTERY.equals(cqsscHis.getWinState())){
                BigDecimal bigDecimal = new BigDecimal(cqsscHis.getMoney()); 
                String temp = (bigDecimal.multiply(cqsscHis.getOdds())).subtract(bigDecimal).toString();
                commission = ((double)(cqsscHis.getCommissionMember().doubleValue())/100)*cqsscHis.getMoney();
                commissionResult += commission;
                winnWaterResult += Double.valueOf(temp)+Double.valueOf(commission);
                moneyFlag += Double.valueOf(temp)+cqsscHis.getMoney();
            }else if(CqsscHis.NOT_WINNING_LOTTERY.equals(cqsscHis.getWinState())){
                commission = ((double)(cqsscHis.getCommissionMember().doubleValue())/100)*cqsscHis.getMoney();
                commissionResult += commission;
                notWaterResult += (-cqsscHis.getMoney())+commission;
            }
        }
        waterResult = notWaterResult+winnWaterResult;
        balanceInfo.setWagersOn(list.size());
        balanceInfo.setWagering(new BigDecimal(moneyCount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        double temp = 0;
        temp = moneyFlag - moneyCount;
        balanceInfo.setRecessionResults(new BigDecimal(temp+commissionResult).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        balanceInfo.setBunkoResults(new BigDecimal(temp).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        balanceInfo.setRecession(new BigDecimal(commissionResult).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	    return balanceInfo;
	}
	
	 @Override
    public List<BalanceInfo> queryGDBalance(List<String> list, Long userMemberID) {
	     List<GdklsfHis>  berfGDsscHisList = new ArrayList<GdklsfHis>();
	        Calendar calendar=Calendar.getInstance();
	           calendar.setTime(java.sql.Date.valueOf(list.get(6).split(" ")[0]));
	           calendar.add(Calendar.DATE, 1);     // 加一天
	           //System.out.println(calendar.getTime());
	        ConditionData conditionData = new ConditionData();
	        conditionData.addBetween("bettingDate",java.sql.Date.valueOf(list.get(0).split(" ")[0]) , true,
	                calendar.getTime(), false);
	        conditionData.addEqual("bettingUserID", userMemberID);
	        berfGDsscHisList = gdklsfHisLogic.findGdklsfHisList(conditionData, 1, 100000);
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        List<GdklsfHis> oneGdHisList = new ArrayList<GdklsfHis>();
	        List<GdklsfHis> twoGdHisList = new ArrayList<GdklsfHis>();
	        List<GdklsfHis> threeGdHisList = new ArrayList<GdklsfHis>();
	        List<GdklsfHis> fourGdHisList = new ArrayList<GdklsfHis>();
	        List<GdklsfHis> fiveGdHisList = new ArrayList<GdklsfHis>();
	        List<GdklsfHis> sixGdHisList = new ArrayList<GdklsfHis>();
	        List<GdklsfHis> sevenGdHisList = new ArrayList<GdklsfHis>();
	        String bettingDate;
	        for (int i = 0; i < berfGDsscHisList.size(); i++) {
	            bettingDate = dateFormat.format(berfGDsscHisList.get(i).getBettingDate());
	          if(bettingDate.equals(list.get(0).split(" ")[0])){
	              oneGdHisList.add(berfGDsscHisList.get(i));
	          }else if(bettingDate.equals(list.get(1).split(" ")[0])){
	              twoGdHisList.add(berfGDsscHisList.get(i));
	          }else if(bettingDate.equals(list.get(2).split(" ")[0])){
	              threeGdHisList.add(berfGDsscHisList.get(i));
	          }else if(bettingDate.equals(list.get(3).split(" ")[0])){
	              fourGdHisList.add(berfGDsscHisList.get(i));
	          }else if(bettingDate.equals(list.get(4).split(" ")[0])){
	              fiveGdHisList.add(berfGDsscHisList.get(i));
	          }else if(bettingDate.equals(list.get(5).split(" ")[0])){
	              sixGdHisList.add(berfGDsscHisList.get(i));
	          }else if(bettingDate.equals(list.get(6).split(" ")[0])){
	              sevenGdHisList.add(berfGDsscHisList.get(i));
	          }
	        }
	        
	        List<BalanceInfo> balanceInfoList = new ArrayList<BalanceInfo>();
	        BalanceInfo balanceInfo = new BalanceInfo();
	        balanceInfo.setTransactionType(list.get(0));//上周星期一的数据
	        balanceInfo = getGDTypeBalance(oneGdHisList,balanceInfo);
	        balanceInfoList.add(balanceInfo);//这是上周星期一的数据
	        
	        //上周星期二数据
	        BalanceInfo balanceInfo1 = new BalanceInfo();
	        balanceInfo1 = getGDTypeBalance(twoGdHisList,balanceInfo1);
	        balanceInfo1.setTransactionType(list.get(1));//上周星期二的数据
	        balanceInfoList.add(balanceInfo1);//这是上周星期二的数据
	        
	        //上周星期三数据
	        BalanceInfo balanceInfo2 = new BalanceInfo();
	        balanceInfo2 = getGDTypeBalance(threeGdHisList,balanceInfo2);
	        balanceInfo2.setTransactionType(list.get(2));//上周星期三的数据
	        balanceInfoList.add(balanceInfo2);//这是上周星期三的数据
	        
	      //上周星期四数据
	        BalanceInfo balanceInfo3 = new BalanceInfo();
	        balanceInfo3 = getGDTypeBalance(fourGdHisList,balanceInfo3);
	        balanceInfo3.setTransactionType(list.get(3));//上周星期四的数据
	        balanceInfoList.add(balanceInfo3);//这是上周星期四的数据
	        
	      //上周星期五数据
	        BalanceInfo balanceInfo4 = new BalanceInfo();
	        balanceInfo4 = getGDTypeBalance(fiveGdHisList,balanceInfo4);
	        balanceInfo4.setTransactionType(list.get(4));//上周星期五的数据
	        balanceInfoList.add(balanceInfo4);//这是上周星期五的数据
	        
	      //上周星期六数据
	        BalanceInfo balanceInfo5 = new BalanceInfo();
	        balanceInfo5 = getGDTypeBalance(sixGdHisList,balanceInfo5);
	        balanceInfo5.setTransactionType(list.get(5));//上周星期六的数据
	        balanceInfoList.add(balanceInfo5);//这是上周星期六的数据
	        
	      //上周星期天数据
	        BalanceInfo balanceInfo6 = new BalanceInfo();
	        balanceInfo6 = getGDTypeBalance(sevenGdHisList,balanceInfo6);
	        balanceInfo6.setTransactionType(list.get(6));//上周星期天的数据
	        balanceInfoList.add(balanceInfo6);//这是上周星期天的数据
	        
	      //当前一周的数据
	        calendar.setTime(java.sql.Date.valueOf(list.get(13).split(" ")[0]));
	        calendar.add(Calendar.DATE, 1);     // 加一天
	        //System.out.println(calendar.getTime());
	        List<GdklsfHis>  gdsscHisList = new ArrayList<GdklsfHis>();
	        conditionData.addBetween("bettingDate",java.sql.Date.valueOf(list.get(7).split(" ")[0]) , true,
	                calendar.getTime(), false);
	        conditionData.addEqual("bettingUserID", userMemberID);
	        gdsscHisList = gdklsfHisLogic.findGdklsfHisList(conditionData, 1, 100000);
	        List<GdklsfHis> onlyOneGdHisList = new ArrayList<GdklsfHis>();
	        List<GdklsfHis> onlyTwoGdHisList = new ArrayList<GdklsfHis>();
	        List<GdklsfHis> onlyThreeGdHisList = new ArrayList<GdklsfHis>();
	        List<GdklsfHis> onlyFourGdHisList = new ArrayList<GdklsfHis>();
	        List<GdklsfHis> onlyFiveGdHisList = new ArrayList<GdklsfHis>();
	        List<GdklsfHis> onlySixGdHisList = new ArrayList<GdklsfHis>();
	        List<GdklsfHis> onlySevenGdHisList = new ArrayList<GdklsfHis>();
	        for (int i = 0; i < gdsscHisList.size(); i++) {
	            bettingDate = dateFormat.format(gdsscHisList.get(i).getBettingDate());
	          if(bettingDate.equals(list.get(7).split(" ")[0])){
	              onlyOneGdHisList.add(gdsscHisList.get(i));
	          }else if(bettingDate.equals(list.get(8).split(" ")[0])){
	              onlyTwoGdHisList.add(gdsscHisList.get(i));
	          }else if(bettingDate.equals(list.get(9).split(" ")[0])){
	              onlyThreeGdHisList.add(gdsscHisList.get(i));
	          }else if(bettingDate.equals(list.get(10).split(" ")[0])){
	              onlyFourGdHisList.add(gdsscHisList.get(i));
	          }else if(bettingDate.equals(list.get(11).split(" ")[0])){
	              onlyFiveGdHisList.add(gdsscHisList.get(i));
	          }else if(bettingDate.equals(list.get(12).split(" ")[0])){
	              onlySixGdHisList.add(gdsscHisList.get(i));
	          }else if(bettingDate.equals(list.get(13).split(" ")[0])){
	              onlySevenGdHisList.add(gdsscHisList.get(i));
	          }
	        }
	        
	        BalanceInfo onlyBalanceInfo = new BalanceInfo();
	        onlyBalanceInfo.setTransactionType(list.get(7));//当前一周星期一的数据
	        onlyBalanceInfo = getGDTypeBalance(onlyOneGdHisList,onlyBalanceInfo);
	        balanceInfoList.add(onlyBalanceInfo);//这是当前一周星期一的数据
	        
	        //当前一周星期二的数据
	        BalanceInfo onlyBalanceInfo1 = new BalanceInfo();
	        onlyBalanceInfo1.setTransactionType(list.get(8));//当前一周星期二的数据
	        onlyBalanceInfo1 = getGDTypeBalance(onlyTwoGdHisList,onlyBalanceInfo1);
	        balanceInfoList.add(onlyBalanceInfo1);//这是当前一周星期一的数据
	        
	        //当前一周星期三的数据
	        BalanceInfo onlyBalanceInfo2 = new BalanceInfo();
	        onlyBalanceInfo2.setTransactionType(list.get(9));//当前一周星期三的数据
	        onlyBalanceInfo2 = getGDTypeBalance(onlyThreeGdHisList,onlyBalanceInfo2);
	        balanceInfoList.add(onlyBalanceInfo2);//这是当前一周星期三的数据
	        
	        //当前一周星期四的数据
	        BalanceInfo onlyBalanceInfo3 = new BalanceInfo();
	        onlyBalanceInfo3.setTransactionType(list.get(10));//当前一周星期四的数据
	        onlyBalanceInfo3 = getGDTypeBalance(onlyFourGdHisList,onlyBalanceInfo3);
	        balanceInfoList.add(onlyBalanceInfo3);//这是当前一周星期四的数据
	        
	      //当前一周星期五的数据
	        BalanceInfo onlyBalanceInfo4 = new BalanceInfo();
	        onlyBalanceInfo4.setTransactionType(list.get(11));//当前一周星期五的数据
	        onlyBalanceInfo4 = getGDTypeBalance(onlyFiveGdHisList,onlyBalanceInfo4);
	        balanceInfoList.add(onlyBalanceInfo4);//这是当前一周星期五的数据
	        
	      //当前一周星期六的数据
	        BalanceInfo onlyBalanceInfo5 = new BalanceInfo();
	        onlyBalanceInfo5.setTransactionType(list.get(12));//当前一周星期六的数据
	        onlyBalanceInfo5 = getGDTypeBalance(onlySixGdHisList,onlyBalanceInfo5);
	        balanceInfoList.add(onlyBalanceInfo5);//这是当前一周星期六的数据
	        
	      //当前一周星期天的数据
	        BalanceInfo onlyBalanceInfo6 = new BalanceInfo();
	        onlyBalanceInfo6.setTransactionType(list.get(13));//当前一周星期天的数据
	        onlyBalanceInfo6 = getGDTypeBalance(onlySevenGdHisList,onlyBalanceInfo6);
	        balanceInfoList.add(onlyBalanceInfo6);//这是当前一周星期天的数据
        return balanceInfoList;
    }
	   private BalanceInfo getGDTypeBalance(List<GdklsfHis> list,BalanceInfo balanceInfo){
	        double moneyCount = 0;//一共下了多少钱
	        double winnWaterResult = 0;//中奖的结果
	        double notWaterResult = 0;//没中奖的结果
	        double waterResult = 0;    //退水后的结果
	        double commission = 0;     //应该退多少水
	        double commissionResult = 0;//今天一共退了多少钱
	        double moneyFlag = 0;//一共赢了多少钱
	      //星期一的数据
	        for (GdklsfHis gdsscHis : list) {
	            moneyCount += gdsscHis.getMoney();
	            if(CqsscHis.WINNING_LOTTERY.equals(gdsscHis.getWinState())){
	                BigDecimal bigDecimal = new BigDecimal(gdsscHis.getMoney()); 
	                String temp = (bigDecimal.multiply(gdsscHis.getOdds())).subtract(bigDecimal).toString();
	                commission = ((double)(gdsscHis.getCommissionMember().doubleValue())/100)*gdsscHis.getMoney();
	                commissionResult += commission;
	                winnWaterResult += Double.valueOf(temp)+Double.valueOf(commission);
	                moneyFlag += Double.valueOf(temp)+gdsscHis.getMoney();
	            }else if(CqsscHis.NOT_WINNING_LOTTERY.equals(gdsscHis.getWinState())){
	                commission = ((double)(gdsscHis.getCommissionMember().doubleValue())/100)*gdsscHis.getMoney();
	                commissionResult += commission;
	                notWaterResult += (-gdsscHis.getMoney())+commission;
	            }
	        }
	        waterResult = notWaterResult+winnWaterResult;
	        balanceInfo.setWagersOn(list.size());
	        balanceInfo.setWagering(new BigDecimal(moneyCount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	        double temp = 0;
	        temp = moneyFlag - moneyCount;
	        balanceInfo.setRecessionResults(new BigDecimal(temp+commissionResult).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	        balanceInfo.setBunkoResults(new BigDecimal(temp).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	        balanceInfo.setRecession(new BigDecimal(commissionResult).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	        return balanceInfo;
	    }
	   
   
   private BalanceInfo getHKTypeBalance(List<HklhcHis> list,BalanceInfo balanceInfo){
       double moneyCount = 0;//一共下了多少钱
       double winnWaterResult = 0;//中奖的结果
       double notWaterResult = 0;//没中奖的结果
       double waterResult = 0;    //退水后的结果
       double commission = 0;     //应该退多少水
       double commissionResult = 0;//今天一共退了多少钱
       double moneyFlag = 0;//一共赢了多少钱
       
       HashSet<String> hkOrderNo = new HashSet<String>();
     //星期一的数据
       for (HklhcHis hksscHis : list) {
           hkOrderNo.add(hksscHis.getOrderNo());
           moneyCount += hksscHis.getMoney();
           if(CqsscHis.WINNING_LOTTERY.equals(hksscHis.getWinState())){
               BigDecimal bigDecimal = new BigDecimal(hksscHis.getMoney()); 
               String temp = (bigDecimal.multiply(hksscHis.getOdds())).subtract(bigDecimal).toString();
               commission = ((double)(hksscHis.getCommissionMember().doubleValue())/100)*hksscHis.getMoney();
               commissionResult += commission;
               //winnWaterResult += Double.valueOf(temp)+Double.valueOf(commission);
               moneyFlag += Double.valueOf(temp) ;
           }else if(CqsscHis.NOT_WINNING_LOTTERY.equals(hksscHis.getWinState())){
               commission = ((double)(hksscHis.getCommissionMember().doubleValue())/100)*hksscHis.getMoney();
               commissionResult += commission;
               notWaterResult += (-hksscHis.getMoney())+commission;
               moneyFlag=moneyFlag-hksscHis.getMoney().doubleValue();
           }
       }
       
       //waterResult = notWaterResult+winnWaterResult;
       balanceInfo.setWagersOn(hkOrderNo.size());
       balanceInfo.setWagering(new BigDecimal(moneyCount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
       //double temp = 0;
       //temp = moneyFlag - moneyCount;
       balanceInfo.setRecessionResults(new BigDecimal(moneyFlag+commissionResult).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
       balanceInfo.setBunkoResults(new BigDecimal(moneyFlag).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
       balanceInfo.setRecession(new BigDecimal(commissionResult).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
       return balanceInfo;
   }
	
   public Map<String,BalanceInfo> queryBalance(Long userMemberID,Date startDate,Date endDate)
   {
	 List<BalanceInfo>  balanceList=betDao.queryBalanceDao(userMemberID, startDate, endDate);
	 Map<String,BalanceInfo> retMap=new HashMap<String,BalanceInfo>();
	 for (int i = 0; i < balanceList.size(); i++) {
		
		 BalanceInfo	 balance=balanceList.get(i);
		 String  TransactionType=balance.getTransactionType();  
		
		 Integer wagersOn=balance.getWagersOn();             
		 Double  bunkoResults=balance.getBunkoResults();       
		 Double  recession=balance.getRecession(); 
		 Double  wagering=balance.getWagering(); 
		 
		 BalanceInfo mapBalance= retMap.get(TransactionType);
		 if(mapBalance!=null)
		 {
			 Integer mapWagersOn=mapBalance.getWagersOn();             
			 Double  mapBunkoResults=mapBalance.getBunkoResults();       
			 Double  mapRecession=mapBalance.getRecession();
			 Double  mapWagering=mapBalance.getWagering();
			 mapBalance.setWagersOn(mapWagersOn+wagersOn);
			 mapBalance.setBunkoResults(mapBunkoResults+bunkoResults);
			 mapBalance.setRecession(mapRecession+recession);
			 mapBalance.setWagering(mapWagering+wagering);
			 
		 }
		 else 
			 retMap.put(TransactionType, balance);
	}
	   
	   return retMap;
	   
   }
   @Override
   public List<PeriodsNumVo> queryPeriodsAllOrderTime(){
	  return betDao.queryPeriodsAllOrderTime();
   }

	public IShopOddsLogic getShopOddsLogic() {
		return shopOddsLogic;
	}
	public void setShopOddsLogic(IShopOddsLogic shopOddsLogic) {
		this.shopOddsLogic = shopOddsLogic;
	}
    public ICqsscHisLogic getCqsscHisLogic() {
        return cqsscHisLogic;
    }
    public void setCqsscHisLogic(ICqsscHisLogic cqsscHisLogic) {
        this.cqsscHisLogic = cqsscHisLogic;
    }
    public IGdklsfHisLogic getGdklsfHisLogic() {
        return gdklsfHisLogic;
    }
    public void setGdklsfHisLogic(IGdklsfHisLogic gdklsfHisLogic) {
        this.gdklsfHisLogic = gdklsfHisLogic;
    }
    public IHklhcHisLogic getHklhcHisLogic() {
        return hklhcHisLogic;
    }
    public void setHklhcHisLogic(IHklhcHisLogic hklhcHisLogic) {
        this.hklhcHisLogic = hklhcHisLogic;
    }
    public static void main(String[] args) {
		int a=800;
		//System.out.println((a+1300)/1000);
		
		//System.out.println((a )/1000);
		System.out.println("B4".substring(1, 2));
	}
    @Override
    public Page<CQandGDReportInfo> queryCQandGDReport(Page page,Long userMemberID,
            Date startDate) {
        List<CQandGDReportInfo> infoList = new ArrayList<CQandGDReportInfo>();
        infoList = betDao.queryCQandGDReportDao(userMemberID, startDate);
        
        //合計
		 double money = 0;
		 double commissionResult = 0;//今天一共退了多少钱
		for(int i = 0; i<infoList.size();i++)
			{    
	        if(!"9".equals(infoList.get(i).getWinState()) && !"4".equals(infoList.get(i).getWinState()) && !"5".equals(infoList.get(i).getWinState())){
		        commissionResult +=infoList.get(i).getRecessionResults();
		    }
		    if(!"4".equals(infoList.get(i).getWinState()) && !"5".equals(infoList.get(i).getWinState())){
		        money +=infoList.get(i).getMoney();
		    }
		}
        //
        
        int first=page.getFirst()-1;
        int last=first+page.getPageSize();
        if(last>infoList.size())
            last=infoList.size();
        Collections.sort(infoList);
        page.setTotalCount(infoList.size());
        page.setResult(infoList.subList(first, last));
        page.setTotal1(money);
        page.setTotal2(commissionResult);
        return page;    
    }
	public IShopsPlayOddsLogLogic getShopsPlayOddsLogLogic() {
		return shopsPlayOddsLogLogic;
	}
	public void setShopsPlayOddsLogLogic(
			IShopsPlayOddsLogLogic shopsPlayOddsLogLogic) {
		this.shopsPlayOddsLogLogic = shopsPlayOddsLogLogic;
	}
	
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	public HttpServletRequest getSpringRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public IReplenishAutoLogic getReplenishAutoLogic() {
		return replenishAutoLogic;
	}
	public void setReplenishAutoLogic(IReplenishAutoLogic replenishAutoLogic) {
		this.replenishAutoLogic = replenishAutoLogic;
	}
	public TaskExecutor getWcpTaskExecutor() {
		return wcpTaskExecutor;
	}

	public void setWcpTaskExecutor(TaskExecutor wcpTaskExecutor) {
		this.wcpTaskExecutor = wcpTaskExecutor;
	}

	public Map gdBetStatistics(String periodNum) 
	{
		return betDao.gdBetStatistics(periodNum);
	}

	public Map cqBetStatistics(String periodNum) 	
	{
			return betDao.cqBetStatistics(periodNum);
	}

	public Map bjBetStatistics(String periodNum)
	{
		   return betDao.bjBetStatistics(periodNum);
	}
    
	@Override
	public void saveJSSBBet(List<BaseBet> ballList, MemberStaffExt memberStaff,String shopCode) {
		//memberStaffExtDao.save(memberStaff);
		betDao.batchInsert(ballList, Constant.K3_TABLE_NAME, false);
		updatePlayAmountAndShopRealOdds(ballList,shopCode,Constant.LOTTERY_TYPE_K3);

	}
    
	/*@Override
	public void rollbackBet(String playType, String lotteryType, String orderNo,String periodsNum) {
		List<String> tableList = new ArrayList<String>();
		if ("1".equals(lotteryType)) {// 1 未結算- 投注表
			if ("GDKLSF".equals(playType)) {
				tableList = Lists.newArrayList(Constant.GDKLSF_TABLE_LIST);
			} else if ("CQSSC".equals(playType)) {
				tableList = Lists.newArrayList(Constant.CQSSC_TABLE_LIST);
			}else if ("BJ".equals(playType)) {
				tableList = Lists.newArrayList(Constant.BJSC_TABLE_LIST);
			} else if (Constant.LOTTERY_TYPE_K3.equals(playType)) {
				tableList = Lists.newArrayList(Constant.K3_TABLE_LIST);
			}else if (Constant.LOTTERY_TYPE_NC.equals(playType)) {// 幸运农场
				tableList = Lists.newArrayList(Constant.NC_TABLE_LIST);
			}

		} else if ("0".equals(lotteryType)) {// 0 已結算- 歷史表
			if ("GDKLSF".equals(playType)) {
				tableList.add(Constant.GDKLSF_HIS_TABLE_NAME);
			} else if ("CQSSC".equals(playType)) {
				tableList.add(Constant.CQSSC_HIS_TABLE_NAME);
			} else if ("BJ".equals(playType)) {
				tableList.add(Constant.BJSC_HIS_TABLE_NAME);
			} else if (Constant.LOTTERY_TYPE_K3.equals(playType)) {
				tableList.add(Constant.K3_HIS_TABLE_NAME);
			}else if (Constant.LOTTERY_TYPE_NC.equals(playType)) {// 幸运农场
				tableList.add(Constant.NC_HIS_TABLE_NAME);
			}
		}

		for (int i = 0; i < tableList.size(); i++) {
			String tableName = tableList.get(i);
			betDao.deleteBetInfoByOrderNum(orderNo, tableName, periodsNum);
		}
	}*/
	
	@Override
	public void cancelBet(String playType, String lotteryType, String orderNo, String periodsNum, String typeCode, String billType, String ip) {
		List<String> tableList = new ArrayList<String>();
		if ("0".equals(lotteryType)) {// 0 已結算- 歷史表
			if ("GDKLSF".equals(playType)) {
				tableList.add(Constant.GDKLSF_HIS_TABLE_NAME);
			} else if ("CQSSC".equals(playType)) {
				tableList.add(Constant.CQSSC_HIS_TABLE_NAME);
			} else if ("BJ".equals(playType)) {
				tableList.add(Constant.BJSC_HIS_TABLE_NAME);
			} else if (Constant.LOTTERY_TYPE_K3.equals(playType)) {
				tableList.add(Constant.K3_HIS_TABLE_NAME);
			} else if (Constant.LOTTERY_TYPE_NC.equals(playType)) {// 幸运农场
				tableList.add(Constant.NC_HIS_TABLE_NAME);
			}
		}

		for (int i = 0; i < tableList.size(); i++) {
			String tableName = tableList.get(i);
			cancelPetDao.saveCancelPet(typeCode, orderNo, periodsNum, billType, CancelPetLog.OP_TYPE_CANCEL, ip);
			betDao.cancelBetInfoByOrderNum(orderNo, tableName, periodsNum);
		}
	}
	
	@Override
	public CancelPet queryCancelPet(Criterion... criterions) {
		
		return cancelPetDao.findUnique(criterions);
	}
	
	@Override
	public List<CancelPetLog> queryCancelPetLogList(String orderNo, String typeCode, String periodsNum, String billType) {
		return cancelPetLogDao.queryCancelPetLogList(typeCode, orderNo, periodsNum, billType);
	}

	public IShopsLogic getShopsLogic() {
		return shopsLogic;
	}

	public void setShopsLogic(IShopsLogic shopsLogic) {
		this.shopsLogic = shopsLogic;
	}

	public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}

	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}

	public PlayAmountJdbcDao getPlayAmountJdbcDao() {
		return playAmountJdbcDao;
	}

	public void setPlayAmountJdbcDao(PlayAmountJdbcDao playAmountJdbcDao) {
		this.playAmountJdbcDao = playAmountJdbcDao;
	}

	

}
