package com.npc.lottery.common.action;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.common.Constant;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.logic.interf.IBetLogic;
import com.npc.lottery.member.logic.interf.IPlayTypeLogic;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.logic.interf.IOpenPlayOddsLogic;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.entity.HKPeriods;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IHKPeriodsInfoLogic;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;
import com.npc.lottery.user.logic.interf.IUserCommissionDefault;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.util.PlayTypeUtils;
public class BetAction extends BaseLotteryAction {

	private static Logger logger = Logger.getLogger(BetAction.class);
	private static final long serialVersionUID = 1L;
	protected IUserCommissionLogic userCommissionLogic;
	protected IUserCommissionDefault userCommissionDefaultLogic;
	protected IPlayTypeLogic playTypeLogic;
	protected IMemberStaffExtLogic memberStaffExtLogic;
	protected MemberStaffExt memberStaff=null;
	protected IShopOddsLogic shopOddsLogic;
	protected IBetLogic betLogic;
/*	protected IGDPeriodsInfoLogic periodsInfoLogic;
	protected IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
	protected ICQPeriodsInfoLogic icqPeriodsInfoLogic;*/
	protected IHKPeriodsInfoLogic skperiodsInfoLogic;
	private IOpenPlayOddsLogic openPlayOddsLogic;
	
	private IReplenishLogic replenishLogic;
	
	/**
	 * 后续需要引入memcached
	 */
	public MemberUser getCurrentUser()
	{
		
		return  (MemberUser) this.getRequest().getSession(true).getAttribute(Constant.MEMBER_LOGIN_INFO_IN_SESSION);
	
	}
	
	protected BaseBet assemblyBet(String playTypeCode,String price,String periodsNum,Map<String,Integer> rateMap)
	{
		
		
		String plate=this.getCurrentUser().getMemberStaffExt().getPlate();
		
		BaseBet ballBet=new BaseBet();
		this.setBetUserTree(ballBet,playTypeCode,rateMap);
		ballBet.setMoney(new Integer(price));
		ballBet.setPeriodsNum(periodsNum);
		ballBet.setPlate(plate);		
		ballBet.setPlayType(playTypeCode);
		/*OracleSequenceMaxValueIncrementer orderNoGener = (OracleSequenceMaxValueIncrementer) SpringUtil
				.getBean("betOrderNoGenerator");
		String orderNo = orderNoGener.nextStringValue();
		ballBet.setOrderNo(orderNo);*/
		ShopsInfo shopInfo=this.getCurrentUser().getShopsInfo();
		ballBet.setShopCode(shopInfo.getShopsCode());
		ballBet.setShopInfo(shopInfo);
		return ballBet;
		
	}
	
	protected BaseBet assemblyFSBet(String playTypeCode,String price,String periodsNum,Map<String,Integer> rateMap)
	{
		
		
		String plate=this.getCurrentUser().getMemberStaffExt().getPlate();
		
		BaseBet ballBet=new BaseBet();
		this.setBetUserTree(ballBet,playTypeCode,rateMap);
		ballBet.setMoney(new Integer(price));
		ballBet.setPeriodsNum(periodsNum);
		ballBet.setPlate(plate);		
		ballBet.setPlayType(playTypeCode);
		ShopsInfo shopInfo=this.getCurrentUser().getShopsInfo();
		ballBet.setShopCode(shopInfo.getShopsCode());
		ballBet.setShopInfo(shopInfo);
		return ballBet;
		
	}
	
	
	
	
    protected void setBetUserTree(BaseBet bet,String playTypeCode,Map<String,Integer> rateMap)
    {    	
    	
    	
    	 String  plate=this.getCurrentUser().getMemberStaffExt().getPlate();	
    	 Long memberStaff=this.getCurrentUser().getID();
    	 Long chiefStaff=this.getMemberStaff().getChiefStaff()==null?0L:this.getMemberStaff().getChiefStaff();
    	 Long branchStaff=this.getMemberStaff().getBranchStaff()==null?0L:this.getMemberStaff().getBranchStaff();
    	 Long stockholderStaff=this.getMemberStaff().getStockholderStaff()==null?0L:this.getMemberStaff().getStockholderStaff();
    	 Long genAgenStaff=this.getMemberStaff().getGenAgentStaff()==null?0L:this.getMemberStaff().getGenAgentStaff();
    	 Long agentStaff=this.getMemberStaff().getAgentStaff()==null?0L:this.getMemberStaff().getAgentStaff();
    	     List<Long> userList=Lists.newArrayList();
    	     //userList.add(memberStaff);
    	     if(agentStaff!=null)
    	    	 userList.add(agentStaff);
    	     if(genAgenStaff!=null)
    	    	 userList.add(genAgenStaff);
    	     if(stockholderStaff!=null)
    	    	 userList.add(stockholderStaff);
    	     if(branchStaff!=null)
    	    	 userList.add(branchStaff);
    	   //UserCommissionDefault chiefCommission = userCommissionDefaultLogic.queryUserPlayTypeCommission(chiefStaff,null,playTypeCode);
    	   Map<String,Double> commissionMap=  betLogic.getUserTreeCommission(userList,memberStaff.toString(), playTypeCode, plate);
    	   bet.setBranchCommission(commissionMap.get(ManagerStaff.USER_TYPE_BRANCH));
    	   bet.setGenAgenCommission(commissionMap.get(ManagerStaff.USER_TYPE_GEN_AGENT));
    	   bet.setStockHolderCommission(commissionMap.get(ManagerStaff.USER_TYPE_STOCKHOLDER));
    	   bet.setAgentStaffCommission(commissionMap.get(ManagerStaff.USER_TYPE_AGENT));
    	   bet.setMemberCommission(commissionMap.get(MemberStaff.USER_TYPE_MEMBER));
    	   
    	  
    	   
    	   bet.setChiefRate(rateMap.get("chiefRate"));
    	   bet.setBranchRate(rateMap.get("branchRate"));
    	   bet.setStockHolderRate(rateMap.get("shareholderRate"));
    	   bet.setGenAgenRate(rateMap.get("genAgentRate"));
    	   bet.setAgentStaffRate(rateMap.get("agentRate"));
    
    	bet.setBettingUserId(memberStaff);
    	bet.setAgentStaff(agentStaff);
    	bet.setGenAgenStaff(genAgenStaff);
    	bet.setStockholderStaff(stockholderStaff);
    	bet.setBranchStaff(branchStaff);
    	bet.setChiefStaff(chiefStaff);
    	PlayType playType = PlayTypeUtils.getPlayType(playTypeCode);
    	bet.setCommissionType(playType.getCommissionType());
    	
    }
	
   
	
	public Map<String,String> betCheck(String  playTypeCode,String price,int playCount,String periodsNum,Map<String,Integer> rateMap)
	{
		HashMap<String,String> messageMap=new HashMap<String,String>();
		PlayType playType = PlayTypeUtils.getPlayType(playTypeCode);
		String shopCode=this.getCurrentUser().getShopsInfo().getShopsCode();
		
		 MemberStaffExt memberStaffExt =this.getMemberStaff();
		 if(MemberStaff.FLAG_FORBID.equals(memberStaffExt.getFlag()))
		 {
			 messageMap.put("errorMessage", "账户已经禁用!");
			 messageMap.put("errorType", "accountError!");
				return messageMap;
		 }
		 else if(MemberStaff.FLAG_FREEZE.equals(memberStaffExt.getFlag()))
		 {
			 messageMap.put("errorMessage", "账户已经冻结!");
			 messageMap.put("errorType", "accountError!");
				return messageMap;
		 }
		 else if(MemberStaff.FLAG_DELETE.equals(memberStaffExt.getFlag()))
		 {
			 messageMap.put("errorMessage", "不存在的账户!");
			 messageMap.put("errorType", "accountError!");
				return messageMap;
		 }
		//校验输入参数是否合法
		if(!GenericValidator.isInt(price))
		{
			messageMap.put("errorMessage", "输入非法参数!");
			return messageMap;
			
		}
		
		  String subName = playType.getSubTypeName();
			if (subName == null)
				subName = "";
			String finalName = playType.getFinalTypeName();
			if(subName.length()!=0)finalName="["+finalName+"]";
		
		
		String type=Constant.LOTTERY_TYPE_CQSSC;
		if(playTypeCode.indexOf("CQSSC")!=-1)
		{
			type=Constant.LOTTERY_TYPE_CQSSC;
		}
		else if(playTypeCode.indexOf("GDKLSF")!=-1)
		{		
			type=Constant.LOTTERY_TYPE_GDKLSF;
		}
		else if(playTypeCode.indexOf("BJ")!=-1)
		{
			type=Constant.LOTTERY_TYPE_BJ;
		}
		// add by peter for k3
		else if (playTypeCode.indexOf("K3") != -1) {
			type = Constant.LOTTERY_TYPE_K3;
		}
		else if (playTypeCode.indexOf("NC") != -1) {
			type = Constant.LOTTERY_TYPE_NC;
		}
		else
		{
			type=Constant.LOTTERY_TYPE_HKLHC;
		}
		//校验单注最低投注額，单注最高限额
		UserCommissionDefault userCommsionDefault=userCommissionDefaultLogic.queryPlayTypeCommission(playType.getCommissionType(),shopCode);
		
		//总监总的投注受额
		Integer totalQuatas=userCommsionDefault.getTotalQuatas();
		
		//单注最低
		Integer lowestQuatas=userCommsionDefault.getLowestQuatas();
		
		UserCommission userCommsion=userCommissionLogic.queryUserPlayTypeCommission(this.getCurrentUser().getID(),MemberStaff.USER_TYPE_MEMBER, playTypeCode);
		
		//单注限额
        Integer bettingQuotas=userCommsion.getBettingQuotas();
        if(playTypeCode.indexOf("GDKLSF_STRAIGHTTHROUGH")==-1)//广东连码 单独处理单项限额校验
        { //单项限额
        Integer itemQuotas= userCommsion.getItemQuotas();
		//扫描表统计 用户 当期 该玩法的投注总和
		Integer userTotalMoney=betLogic.queryUseritemQuotasMoney(type, periodsNum,playTypeCode, this.getCurrentUser().getID().toString() );
		
		 if(userTotalMoney+Integer.valueOf(price)*playCount>itemQuotas)
			{
				messageMap.put("errorMessage", "超过单项限额!"+itemQuotas);
				messageMap.put("errorType", "ExceedItem");
				return messageMap;
			}
        }
		//统计 店铺下 所有用户的投注总额
		Double exsitTotalQuatas=betLogic.queryTotalCommissionMoney(shopCode, playTypeCode);
		
        if(Integer.parseInt(price)<lowestQuatas||Integer.parseInt(price)>bettingQuotas)
		{	
			String errorMes="";
			if(Integer.parseInt(price)<lowestQuatas)
			{
				errorMes="下注金额，低於最低投注金额，请更改。\n\n";
			}
			if(Integer.parseInt(price)>bettingQuotas)
			{
				errorMes="下注金额，超过最高金额，请更改。\n\n";
			}
			if(subName.length()!=0)
			finalName=subName;
			messageMap.put("errorType", "ExceedMinMax");
			messageMap.put("errorMessage",errorMes+finalName+"\n\n单注最低投注額: "+lowestQuatas+"\n\n单注最高限额: "+bettingQuotas);
			return messageMap;
		
		}
		
		
		Integer chiefRate = rateMap.get("chiefRate");
		// 已经投注实占+当前投注实占 >总监实占总金额
		double replenishMoney=0;
		ShopsInfo shopsInfo = this.getCurrentShopsInfo();
		if(null!=shopsInfo){
			if(null!=shopsInfo.getChiefStaffExt()){
				replenishMoney = replenishLogic.queryReplenishForBetCheck(shopsInfo.getChiefStaffExt().getID(), ManagerUser.USER_TYPE_CHIEF, playTypeCode, periodsNum).doubleValue();
			}
		}
		BigDecimal moneyResult = BigDecimal.valueOf(exsitTotalQuatas).add(
				BigDecimal.valueOf((Double.valueOf(price) * playCount) * chiefRate).divide(BigDecimal.valueOf(100))).add(BigDecimal.valueOf(replenishMoney));
		if (moneyResult.compareTo(BigDecimal.valueOf(totalQuatas)) > 0) {
			messageMap.put("errorMessage", "超过总监单项受注额!");
			messageMap.put("errorType", "ExceedChief");
			return messageMap;
		}
					
        
       
      

		return messageMap;
	}
	public IUserCommissionLogic getUserCommissionLogic() {
		return userCommissionLogic;
	}
	public void setUserCommissionLogic(IUserCommissionLogic userCommissionLogic) {
		this.userCommissionLogic = userCommissionLogic;
	}
	public IUserCommissionDefault getUserCommissionDefaultLogic() {
		return userCommissionDefaultLogic;
	}
	public void setUserCommissionDefaultLogic(
			IUserCommissionDefault userCommissionDefaultLogic) {
		this.userCommissionDefaultLogic = userCommissionDefaultLogic;
	}
	public IPlayTypeLogic getPlayTypeLogic() {
		return playTypeLogic;
	}
	public void setPlayTypeLogic(IPlayTypeLogic playTypeLogic) {
		this.playTypeLogic = playTypeLogic;
	}

	public IMemberStaffExtLogic getMemberStaffExtLogic() {
		return memberStaffExtLogic;
	}

	public void setMemberStaffExtLogic(IMemberStaffExtLogic memberStaffExtLogic) {
		this.memberStaffExtLogic = memberStaffExtLogic;
	}


	public MemberStaffExt getMemberStaff() {
		if (memberStaff == null) {
			memberStaff = memberStaffExtLogic.findMemberStaffExtByID(this.getCurrentUser().getID());
		}
		return memberStaff;
	}

	public IShopOddsLogic getShopOddsLogic() {
		return shopOddsLogic;
	}

	public void setShopOddsLogic(IShopOddsLogic shopOddsLogic) {
		this.shopOddsLogic = shopOddsLogic;
	}

	public void setMemberStaff(MemberStaffExt memberStaff) {
		this.memberStaff = memberStaff;
	}

	public Map<String,ShopsPlayOdds> initGDShopOdds(String shopCode)
	{
		List<ShopsPlayOdds> gdoddList=shopOddsLogic.queryGDKLSFRealOdds(shopCode,"GDKLSF");
		
		String  plate=this.getCurrentUser().getMemberStaffExt().getPlate();
		
		Map<String,ShopsPlayOdds> shopOddMap=new HashMap<String,ShopsPlayOdds>();
		for (int i = 0; i < gdoddList.size(); i++) {
			ShopsPlayOdds shopodds=gdoddList.get(i);
			if("B".equals(plate))
			 shopodds.setRealOdds(shopodds.getRealOddsB());
			 else if("C".equals(plate))
				 shopodds.setRealOdds(shopodds.getRealOddsC());
			shopOddMap.put(shopodds.getPlayTypeCode(), shopodds);
		}
		
		return shopOddMap;
		
		
	}

	public Map<String,ShopsPlayOdds> initCQShopOdds(String shopCode)
	{
		List<ShopsPlayOdds> cqoddList=shopOddsLogic.queryCQSSCRealOdds(shopCode,"CQSSC");
		Map<String,ShopsPlayOdds> shopOddMap=new HashMap<String,ShopsPlayOdds>();
		String  plate=this.getCurrentUser().getMemberStaffExt().getPlate();
		for (int i = 0; i < cqoddList.size(); i++) {
			ShopsPlayOdds shopodds=cqoddList.get(i);
			if("B".equals(plate))
				 shopodds.setRealOdds(shopodds.getRealOddsB());
				 else if("C".equals(plate))
					 shopodds.setRealOdds(shopodds.getRealOddsC());
			shopOddMap.put(shopodds.getPlayTypeCode(), shopodds);
		}
		return shopOddMap;
		
		
	}

	public Map<String,ShopsPlayOdds> initBJSCShopOdds(String shopCode)
	{
		List<ShopsPlayOdds> cqoddList=shopOddsLogic.queryCQSSCRealOdds(shopCode,"BJ");
		Map<String,ShopsPlayOdds> shopOddMap=new HashMap<String,ShopsPlayOdds>();
		String  plate=this.getCurrentUser().getMemberStaffExt().getPlate();
		for (int i = 0; i < cqoddList.size(); i++) {
			ShopsPlayOdds shopodds=cqoddList.get(i);
			if("B".equals(plate))
				 shopodds.setRealOdds(shopodds.getRealOddsB());
				 else if("C".equals(plate))
					 shopodds.setRealOdds(shopodds.getRealOddsC());
			shopOddMap.put(shopodds.getPlayTypeCode(), shopodds);
		}
		return shopOddMap;
		
		
	}
	
	/**
	 * add by peter K3 project 初始化江苏骰宝的赔率
	 * 
	 * @param shopCode
	 * @return
	 */
	public Map<String, ShopsPlayOdds> initJSSBShopOdds(String shopCode) {
		List<ShopsPlayOdds> cqoddList = shopOddsLogic.queryCQSSCRealOdds(shopCode, Constant.LOTTERY_TYPE_K3);
		Map<String, ShopsPlayOdds> shopOddMap = new HashMap<String, ShopsPlayOdds>();
		String plate = this.getCurrentUser().getMemberStaffExt().getPlate();
		for (int i = 0; i < cqoddList.size(); i++) {
			ShopsPlayOdds shopodds = cqoddList.get(i);
			if ("B".equals(plate))
				shopodds.setRealOdds(shopodds.getRealOddsB());
			else if ("C".equals(plate))
				shopodds.setRealOdds(shopodds.getRealOddsC());
			shopOddMap.put(shopodds.getPlayTypeCode(), shopodds);
		}
		return shopOddMap;

	}
	
	public Map<String,ShopsPlayOdds> initNCShopOdds(String shopCode)
	{
		List<ShopsPlayOdds> gdoddList=shopOddsLogic.queryGDKLSFRealOdds(shopCode,"NC");
		
		String  plate=this.getCurrentUser().getMemberStaffExt().getPlate();
		
		Map<String,ShopsPlayOdds> shopOddMap=new HashMap<String,ShopsPlayOdds>();
		for (int i = 0; i < gdoddList.size(); i++) {
			ShopsPlayOdds shopodds=gdoddList.get(i);
			if("B".equals(plate))
			 shopodds.setRealOdds(shopodds.getRealOddsB());
			 else if("C".equals(plate))
				 shopodds.setRealOdds(shopodds.getRealOddsC());
			shopOddMap.put(shopodds.getPlayTypeCode(), shopodds);
		}
		
		return shopOddMap;
		
		
	}
	
	public  Map<String,ShopsPlayOdds> initHKShopOdds(String shopCode)
	{
		List<ShopsPlayOdds> hkOddList=shopOddsLogic.queryHKRealOdds(shopCode,"HK");
		String  plate=this.getCurrentUser().getMemberStaffExt().getPlate();
		Map<String,ShopsPlayOdds> shopOddMap=new HashMap<String,ShopsPlayOdds>();
		for (int i = 0; i < hkOddList.size(); i++) {
			ShopsPlayOdds shopodds=hkOddList.get(i);
			if("B".equals(plate))
				 shopodds.setRealOdds(shopodds.getRealOddsB());
				 else if("C".equals(plate))
					 shopodds.setRealOdds(shopodds.getRealOddsC());
			shopOddMap.put(shopodds.getPlayTypeCode(), shopodds);
		}
		return shopOddMap;
		
		
	}
	/**
	 * 获取精度处理后的商品价格
	 * 
	 */
	protected static BigDecimal getPriceScaleBigDecimal(float price) {
		
		  
		    return  new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP);
		
			
		}

	
	
	public IBetLogic getBetLogic() {
		return betLogic;
	}

	public void setBetLogic(IBetLogic betLogic) {
		this.betLogic = betLogic;
	}

	public IGDPeriodsInfoLogic getPeriodsInfoLogic() {
		return periodsInfoLogic;
	}

	public void setPeriodsInfoLogic(IGDPeriodsInfoLogic periodsInfoLogic) {
		this.periodsInfoLogic = periodsInfoLogic;
	}

	public ICQPeriodsInfoLogic getIcqPeriodsInfoLogic() {
		return icqPeriodsInfoLogic;
	}

	public void setIcqPeriodsInfoLogic(ICQPeriodsInfoLogic icqPeriodsInfoLogic) {
		this.icqPeriodsInfoLogic = icqPeriodsInfoLogic;
	}

	public IHKPeriodsInfoLogic getSkperiodsInfoLogic() {
		return skperiodsInfoLogic;
	}

	public void setSkperiodsInfoLogic(IHKPeriodsInfoLogic skperiodsInfoLogic) {
		this.skperiodsInfoLogic = skperiodsInfoLogic;
	}
	
	

	
/*	protected CQPeriodsInfo getCQRunningPeriods()
	{
		
		
		List<Criterion> filtersKP = new ArrayList<Criterion>();
		filtersKP.add(Restrictions.eq("state",Constant.OPEN_STATUS));
		CQPeriodsInfo runningPeriods=icqPeriodsInfoLogic.queryByPeriodsStatus(Constant.OPEN_STATUS);
		if(runningPeriods==null)
		{
		logger.info("拿不到重庆开盘状态盘期");
		List<Criterion> filtersFP = new ArrayList<Criterion>();
		filtersFP.add(Restrictions.eq("state",Constant.STOP_STATUS));
		runningPeriods=icqPeriodsInfoLogic.queryStopPeriods(Constant.STOP_STATUS);
		if(runningPeriods!=null)
		logger.info("当前重庆是封盘状态盘期为"+runningPeriods.getPeriodsNum());
		else 
			logger.info("拿不到重庆开盘状态盘期,也拿不到重庆封盘盘期 系统为 未看盘状态");
		}
		CQPeriodsInfo lastcqp=icqPeriodsInfoLogic.queryLastLotteryPeriods();
		this.getRequest().setAttribute("LastLotteryPeriods", lastcqp);
		//CQPeriodsInfo lastwkpqp=icqPeriodsInfoLogic.queryLastNotOpenPeriods();
		//this.getRequest().setAttribute("LastwkpPeriods", lastwkpqp);
		return runningPeriods;
		
		
		
	}
	
	protected BJSCPeriodsInfo getBJSCRunningPeriods()
	{
		
		
		List<Criterion> filtersKP = new ArrayList<Criterion>();
		filtersKP.add(Restrictions.eq("state",Constant.OPEN_STATUS));
		BJSCPeriodsInfo runningPeriods=bjscPeriodsInfoLogic.queryByPeriodsStatus(Constant.OPEN_STATUS);
		if(runningPeriods==null)
		{
		logger.info("拿不到北京赛车开盘状态盘期");
		List<Criterion> filtersFP = new ArrayList<Criterion>();
		filtersFP.add(Restrictions.eq("state",Constant.STOP_STATUS));
		runningPeriods=bjscPeriodsInfoLogic.queryStopPeriods(Constant.STOP_STATUS);
		if(runningPeriods!=null)
		logger.info("当前北京赛车是封盘状态盘期为"+runningPeriods.getPeriodsNum());
		else 
			logger.info("拿不到北京赛车开盘状态盘期,也拿不到北京赛车封盘盘期 系统为 未看盘状态");
		}
		BJSCPeriodsInfo lastcqp=bjscPeriodsInfoLogic.queryLastLotteryPeriods();
		this.getRequest().setAttribute("LastLotteryPeriods", lastcqp);

		return runningPeriods;
		
		
		
	}
	
	
	protected GDPeriodsInfo getGDRunningPeriods() 
	{
		
		List<Criterion> filtersKP = new ArrayList<Criterion>();
		filtersKP.add(Restrictions.eq("state",Constant.OPEN_STATUS));
		GDPeriodsInfo runningPeriods=periodsInfoLogic.queryByPeriodsStatus(Constant.OPEN_STATUS);
		if(runningPeriods==null)
		{
			logger.info("拿不到广东开盘状态盘期");	
		List<Criterion> filtersFP = new ArrayList<Criterion>();
		filtersFP.add(Restrictions.eq("state",Constant.STOP_STATUS));
		runningPeriods=periodsInfoLogic.queryStopPeriods(Constant.STOP_STATUS);
		
		}
		GDPeriodsInfo lastcqp=periodsInfoLogic.queryLastLotteryPeriods();
		this.getRequest().setAttribute("LastLotteryPeriods", lastcqp);
		//GDPeriodsInfo lastwkpqp=periodsInfoLogic.queryLastNotOpenPeriods();
		//this.getRequest().setAttribute("LastwkpPeriods", lastwkpqp);
		
		return runningPeriods;
		
		
		
		
	}*/
	protected HKPeriods getHKRunningPeriods()
	{
		
		HKPeriods runningPeriods =skperiodsInfoLogic.queryRunningPeriods(this.getCurrentUser().getShopsInfo().getShopsCode());
		return runningPeriods;
	
	}
	
	protected Map<String,ManagerStaff> getUserMap()
	{
		
		
		 Map<String,ManagerStaff> map=new HashMap<String,ManagerStaff>();
		 ManagerStaff managerStaff=this.getMemberStaff().getManagerStaff();
    	 String userType=managerStaff.getUserType();
    	 
    	if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(userType)) 
    	{
    				
    		 
    		 map.put(ManagerStaff.USER_TYPE_CHIEF, managerStaff);
    	
    	}
        else if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(userType)) 
        {
        	   BranchStaffExt branch = (BranchStaffExt)managerStaff;
        	   map.put(ManagerStaff.USER_TYPE_BRANCH, branch);
        	 
    		   map.put(ManagerStaff.USER_TYPE_CHIEF, branch.getChiefStaffExt());
    		   
        }
          else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                  .equalsIgnoreCase(userType)) {
        	  StockholderStaffExt stockHolder = (StockholderStaffExt)managerStaff;  	  
        	      	     	  
    		  BranchStaffExt branch=stockHolder.getBranchStaffExt();
     		 map.put(ManagerStaff.USER_TYPE_STOCKHOLDER, stockHolder);
     		 map.put(ManagerStaff.USER_TYPE_BRANCH, branch);       	 
  		     map.put(ManagerStaff.USER_TYPE_CHIEF, branch.getChiefStaffExt());
             
          } else if (ManagerStaff.USER_TYPE_GEN_AGENT
                  .equalsIgnoreCase(userType)) {
        	  GenAgentStaffExt gas=((GenAgentStaffExt)managerStaff);
        	  StockholderStaffExt stockHolder=gas.getStockholderStaffExt();
        	  BranchStaffExt branch=stockHolder.getBranchStaffExt();
        	  ChiefStaffExt cs=branch.getChiefStaffExt();
        	  map.put(ManagerStaff.USER_TYPE_GEN_AGENT, gas);
        	  map.put(ManagerStaff.USER_TYPE_STOCKHOLDER, stockHolder);
      		  map.put(ManagerStaff.USER_TYPE_BRANCH, branch);       	 
   		      map.put(ManagerStaff.USER_TYPE_CHIEF, cs);
        	
   
        	  
             
          } else if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
        	    	 
        	  AgentStaffExt agentSta = (AgentStaffExt)managerStaff;
    		  GenAgentStaffExt gas=agentSta.getGenAgentStaffExt();
        	  StockholderStaffExt shs=gas.getStockholderStaffExt();  	  
        	  BranchStaffExt bs=shs.getBranchStaffExt();
        	  ChiefStaffExt cs=bs.getChiefStaffExt();
        	  map.put(ManagerStaff.USER_TYPE_AGENT, agentSta);
        	  map.put(ManagerStaff.USER_TYPE_GEN_AGENT, gas);
        	  map.put(ManagerStaff.USER_TYPE_STOCKHOLDER,  shs);
      		  map.put(ManagerStaff.USER_TYPE_BRANCH, bs);       	 
   		      map.put(ManagerStaff.USER_TYPE_CHIEF, cs);
     
  
             
          }
		
		return map;
		
	}
	
	
	public IOpenPlayOddsLogic getOpenPlayOddsLogic() {
		return openPlayOddsLogic;
	}

	public void setOpenPlayOddsLogic(IOpenPlayOddsLogic openPlayOddsLogic) {
		this.openPlayOddsLogic = openPlayOddsLogic;
	}

	public IBJSCPeriodsInfoLogic getBjscPeriodsInfoLogic() {
		return bjscPeriodsInfoLogic;
	}

	public void setBjscPeriodsInfoLogic(IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic) {
		this.bjscPeriodsInfoLogic = bjscPeriodsInfoLogic;
	}

	protected String ajaxCQSubmitResult(String period, List<BaseBet> betList,
			Integer totalMon,Map<String,String> mesMap) {

		StringBuffer html = new StringBuffer();
		html.append("<div class=\"p\">");
		if(mesMap.get("errorType")==null)
		html.append("<div class=\"print_btn\"><span><input type=\"button\" value=\"打 印\" class=\"btn2\" name=\"\" id=\"print\"></span><span class=\"ml10\"><input type=\"button\" value=\"返 回\" class=\"btn2\" name=\"\" id=\"retbtn\"></span></div>");
		html.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"231\" class=\"print_l\"><tbody>");
		if(mesMap.get("errorType")!=null)
		{
			html.append("<tr><td class=\"t_td_caption_1\" colspan=\"2\"><strong class=\"red\">下注被退回，请再次确认！</strong></td></tr>");
		}
		html.append("<tr><td class='qishu' colspan=\"2\">" + period+ "期</td></tr><tr><td colspan=\"2\"><ul>");
		boolean canBet=false;
		int canBetNum=0;
		int canNotBetNum=0;
		String disable="";
		StringBuffer cacheOdd=new StringBuffer("{");
		for (int i = 0; i < betList.size(); i++) {
			BaseBet bet = betList.get(i);
			Integer price = bet.getMoney();
			BigDecimal odds = bet.getOdds();
			String typeCode = bet.getPlayType();
			PlayType playType = playTypeLogic.getPlayTypeByTypeCode(typeCode);
			String subType = playType.getSubTypeName();
			if (subType == null)
				subType = "";
			String typeName = playType.getFinalTypeName();
			float winMoney = (odds.floatValue()-1) * price;
			float roundMoney = (float) (Math.round(winMoney * 100)) / 100;

			html.append("<li><p>注单号："+bet.getOrderNo()+"#</p><p class=\"t_center\"><span class=\"blue\">"
					+ subType
					+ " 『 "
					+ typeName
					+ " 』"
					+ "</span>@ <strong class=\"red\">"
					+ odds
					+ "</strong></p><p>下注额："
					+ price
					+ "</p><p>可赢额："
					+ roundMoney + "</p>");
			if("ExceedItem".equals(bet.getBetError()))
			{
				   html.append("<p><span class=\"lj\">累计超过 “单期最高限额”</span></p>");
				   canNotBetNum++;

			}
			else if("OddChanged".equals(bet.getBetError()))
			{
			cacheOdd.append(typeCode).append(":").append(odds).append(",");
			html.append("<input type='hidden' name='"+typeCode+"' value='"+price+"'/>");
			html.append("<p><span class=\"bd\">下注賠率有變動 請再次確認</span></p>");	
			canBetNum++;
			}
			else
			{
				cacheOdd.append(typeCode).append(":").append(odds).append(",");
				html.append("<input type='hidden' name='"+typeCode+"' value='"+price+"'/>");
				canBetNum++;
			}
			html.append("</li>");
		}
		cacheOdd.append("}");
		
		if(canBetNum>0)
		{
			html.append("<input type='hidden' name='cachedOdd' value='"+cacheOdd+"'/>");	
		}
		else
			disable="disabled";
		String button="<table width=\"230\" height=\"35\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"print_bg\">" +
		  		"<tbody><tr><td align=\"center\">" +
		  		"<input type=\"button\" value=\"取消\" class=\"btn2\" id=\"cancelButton\">" +
		  		"<input type=\"submit\" value=\"确定下注\" class=\"btn2 ml10\" "+disable+" ></td></tr></tbody>" +
		  		"</table>";
		html.append("</ul></td></tr>");
		html.append("<tr><td width=\"34%\" class=\"l_color\">下注笔数</td><td width=\"66%\">"
				+ betList.size() + "笔</td></tr>");
		html.append("<tr><td class=\"l_color\">合计注额</td><td>￥" + totalMon
				+ "</td></tr>");
		html.append("</tbody></table>");
		if(mesMap.get("errorType")!=null)
		html.append(button);
		html.append("</div>");

		return html.toString();

	}
	
	
	protected String ajaxBJSCSubmitResult(String period, List<BaseBet> betList,
			Integer totalMon,Map<String,String> mesMap) {

		StringBuffer html = new StringBuffer();
		html.append("<div class=\"p\">");
		if(mesMap.get("errorType")==null)
		html.append("<div class=\"print_btn\"><span><input type=\"button\" value=\"打 印\" class=\"btn2\" name=\"\" id=\"print\"></span><span class=\"ml10\"><input type=\"button\" value=\"返 回\" class=\"btn2\" name=\"\" id=\"retbtn\"></span></div>");
		html.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"231\" class=\"print_l\"><tbody>");
		if(mesMap.get("errorType")!=null)
		{
			html.append("<tr><td class=\"t_td_caption_1\" colspan=\"2\"><strong class=\"red\">下注被退回，请再次确认！</strong></td></tr>");
		}
		html.append("<tr><td colspan=\"2\" class=\"qishu\">" + period+ "期</td></tr><tr><td colspan=\"2\"><ul>");
		boolean errorCanBet=true;
		String disable="";
		int canBetNum=0;
		StringBuffer cacheOdd=new StringBuffer("{");
		for (int i = 0; i < betList.size(); i++) {
			BaseBet bet = betList.get(i);
			Integer price = bet.getMoney();
			BigDecimal odds = bet.getOdds();
			String typeCode = bet.getPlayType();
			PlayType playType=PlayTypeUtils.getPlayType(typeCode);
			String subType=playType.getPlaySubType();
	        String finalType=playType.getPlayFinalType();
			String subTypeName = playType.getSubTypeName();
			if (subTypeName == null)
				subTypeName = "";
			else if(("GROUP".equals(subType)&&("DA".equals(finalType)||"X".equals(finalType)||"DAN".equals(finalType)||"S".equals(finalType))))
				subTypeName="";
			String typeName = playType.getFinalTypeName();
			float winMoney = (odds.floatValue()-1) * price;
			float roundMoney = (float) (Math.round(winMoney * 100)) / 100;

			html.append("<li><p>注单号："+bet.getOrderNo()+"#</p><p class=\"t_center\"><span class=\"blue\">"
					+ subTypeName
					+ " 『 "
					+ typeName
					+ " 』"
					+ "</span>@ <strong class=\"red\">"
					+ odds
					+ "</strong></p><p>下注额："
					+ price
					+ "</p><p>可赢额："
					+ roundMoney + "</p>");
			if("ExceedItem".equals(bet.getBetError()))
			{
			   html.append("<p><span class=\"lj\">累计超过 “单期最高限额”</span></p>");
			   errorCanBet=false;
			   //disable="disabled";
			}
			else if("OddChanged".equals(bet.getBetError()))
			{
				cacheOdd.append(typeCode).append(":").append(odds).append(",");
				html.append("<input type='hidden' name='"+typeCode+"' value='"+price+"'/>");
			html.append("<p><span class=\"bd\">下注賠率有變動 請再次確認</span></p>");
			canBetNum++;
			
			}
			else
			{
				cacheOdd.append(typeCode).append(":").append(odds).append(",");
				html.append("<input type='hidden' name='"+typeCode+"' value='"+price+"'/>");
				canBetNum++;
			}
			html.append("</li>");
			
		}
		cacheOdd.append("}");
		if(canBetNum>0)
		{
			html.append("<input type='hidden' name='cachedOdd' value='"+cacheOdd+"'/>");	
		}
		else
			disable="disabled";
		String button="<table width=\"230\" height=\"35\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"print_bg\">" +
		  		"<tbody><tr><td align=\"center\">" +
		  		"<input type=\"button\" value=\"取消\" class=\"btn2\" id=\"cancelButton\">" +
		  		"<input type=\"submit\" value=\"确定下注\" class=\"btn2 ml10\" "+disable+" ></td></tr></tbody>" +
		  		"</table>";
		html.append("</ul></td></tr>");
		if(mesMap.get("errorType")==null)
		{
		html.append("<tr><td width=\"34%\" class=\"l_color\">下注笔数</td><td width=\"66%\">"+ betList.size() + "笔</td></tr>");
		html.append("<tr><td class=\"l_color\">合计注额</td><td>￥" + totalMon+ "</td></tr>");
		}
		html.append("</tbody></table>");
		if(mesMap.get("errorType")!=null)
		   html.append(button);
		html.append("</div>");

		return html.toString();

	}

	protected String ajaxGDSubmitResult(String period, List<BaseBet> betList,Integer totalMon,Map<String,String> mesMap) {

		
		StringBuffer html = new StringBuffer();
		html.append("<div class=\"p\">");
		if(mesMap.get("errorType")==null)
		html.append("<div class=\"print_btn\"><span><input type=\"button\" value=\"打 印\" class=\"btn2\" name=\"\" id=\"print\"></span><span class=\"ml10\"><input type=\"button\" value=\"返 回\" class=\"btn2\" name=\"\" id=\"retbtn\"></span></div>");
		
		html.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"230\" class=\"print_l\"><tbody>");
		if(mesMap.get("errorType")!=null)
		{
			html.append("<tr><td class=\"t_td_caption_1\" colspan=\"2\"><strong class=\"red\">下注被退回，请再次确认！</strong></td></tr>");
		}
		html.append("<tr><td class='qishu' colspan=\"2\">" + period+ "期</td></tr>");
		html.append("<tr><td colspan=\"2\"><ul>");
		StringBuffer cacheOdd=new StringBuffer("{");
		boolean errorCanBet=true;
		String disable="";
		int canBetNum=0;
		for (int i = 0; i < betList.size(); i++) {
			BaseBet bet = betList.get(i);
			Integer price = bet.getMoney();
			BigDecimal odds = bet.getOdds();
			String typeCode = bet.getPlayType();
			PlayType playType = PlayTypeUtils.getPlayType(typeCode);
			String subType = playType.getSubTypeName();
			String typeName = playType.getFinalTypeName();
			String orderNo = bet.getOrderNo();
			float winMoney = (odds.floatValue()-1) * price;
			float roundMoney = (float) (Math.round(winMoney * 100)) / 100;
            
			if(subType==null)
				subType="";
			
			String finalTypeName=playType.getFinalTypeName();
			if(playType.getPlaySubType()!=null&&playType.getPlaySubType().indexOf("BALL")!=-1)
				finalTypeName="『"+finalTypeName+"』";
			
			html.append("<li><p>注单号：" + orderNo
					+ "#</p><p class=\"t_center\"><span class=\"blue\">"
					+ subType + " 『 " + typeName
					+ " 』</span>@ <strong style=\"color:red\">" + odds
					+ "</strong></p><p>下注额：" + price + "</p><p>可赢额："
					+ roundMoney + "</p>");
			if("ExceedItem".equals(bet.getBetError()))
			{
			html.append("<p><span class=\"lj\">累计超过 “单期最高限额”</span></p>");
			 errorCanBet=false;
			  // disable="disabled";

			}
			else if("OddChanged".equals(bet.getBetError()))
			{
			cacheOdd.append(typeCode).append(":").append(odds).append(",");
			html.append("<input type='hidden' name='"+typeCode+"' value='"+price+"'/>");
			html.append("<p><span class=\"bd\">下注賠率有變動 請再次確認</span></p>");	
			canBetNum++;
			}
			else
			{
				cacheOdd.append(typeCode).append(":").append(odds).append(",");
				html.append("<input type='hidden' name='"+typeCode+"' value='"+price+"'/>");
				canBetNum++;
			}
			html.append("</li>");
		}
		cacheOdd.append("}");
		if(canBetNum>0)
		{
			html.append("<input type='hidden' name='cachedOdd' value='"+cacheOdd+"'/>");	
		}
		else
			disable="disabled";
	
		  String button="<table width=\"230\" height=\"35\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"print_bg\">" +
			  		"<tbody><tr><td align=\"center\">" +
			  		"<input type=\"button\" value=\"取消\" class=\"btn2\" id=\"cancelButton\">" +
			  		"<input type=\"submit\" value=\"确定下注\" class=\"btn2 ml10\" "+disable+" ></td></tr></tbody>" +
			  		"</table>";
		html.append("</ul></td></tr>");
		html.append("<tr><td width=\"34%\" class=\"l_color\">下注笔数</td><td width=\"66%\">"
				+ betList.size() + "笔</td></tr>");
		html.append("<tr><td class=\"l_color\">合计注额</td><td>￥" + totalMon
				+ "</td></tr>");
		html.append("</tbody></table>");
		if(mesMap.get("errorType")!=null)
		html.append(button);
		html.append("</div>");
		
	

		return html.toString();

	}
	protected String ajaxNCSubmitResult(String period, List<BaseBet> betList,Integer totalMon,Map<String,String> mesMap) {
		
		
		StringBuffer html = new StringBuffer();
		html.append("<div class=\"p\">");
		if(mesMap.get("errorType")==null)
			html.append("<div class=\"print_btn\"><span><input type=\"button\" value=\"打 印\" class=\"btn2\" name=\"\" id=\"print\"></span><span class=\"ml10\"><input type=\"button\" value=\"返 回\" class=\"btn2\" name=\"\" id=\"retbtn\"></span></div>");
		
		html.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"230\" class=\"print_l\"><tbody>");
		if(mesMap.get("errorType")!=null)
		{
			html.append("<tr><td class=\"t_td_caption_1\" colspan=\"2\"><strong class=\"red\">下注被退回，请再次确认！</strong></td></tr>");
		}
		html.append("<tr><td class='qishu' colspan=\"2\">" + period+ "期</td></tr>");
		html.append("<tr><td colspan=\"2\"><ul>");
		StringBuffer cacheOdd=new StringBuffer("{");
		boolean errorCanBet=true;
		String disable="";
		int canBetNum=0;
		for (int i = 0; i < betList.size(); i++) {
			BaseBet bet = betList.get(i);
			Integer price = bet.getMoney();
			BigDecimal odds = bet.getOdds();
			String typeCode = bet.getPlayType();
			PlayType playType = PlayTypeUtils.getPlayType(typeCode);
			String subType = playType.getSubTypeName();
			String typeName = playType.getFinalTypeName();
			String orderNo = bet.getOrderNo();
			float winMoney = (odds.floatValue()-1) * price;
			float roundMoney = (float) (Math.round(winMoney * 100)) / 100;
			
			if(subType==null)
				subType="";
			
			String finalTypeName=playType.getFinalTypeName();
			if(playType.getPlaySubType()!=null&&playType.getPlaySubType().indexOf("BALL")!=-1)
				finalTypeName="『"+finalTypeName+"』";
			
			html.append("<li><p>注单号：" + orderNo
					+ "#</p><p class=\"t_center\"><span class=\"blue\">"
					+ subType + " 『 " + typeName
					+ " 』</span>@ <strong style=\"color:red\">" + odds
					+ "</strong></p><p>下注额：" + price + "</p><p>可赢额："
					+ roundMoney + "</p>");
			if("ExceedItem".equals(bet.getBetError()))
			{
				html.append("<p><span class=\"lj\">累计超过 “单期最高限额”</span></p>");
				errorCanBet=false;
				// disable="disabled";
				
			}
			else if("OddChanged".equals(bet.getBetError()))
			{
				cacheOdd.append(typeCode).append(":").append(odds).append(",");
				html.append("<input type='hidden' name='"+typeCode+"' value='"+price+"'/>");
				html.append("<p><span class=\"bd\">下注賠率有變動 請再次確認</span></p>");	
				canBetNum++;
			}
			else
			{
				cacheOdd.append(typeCode).append(":").append(odds).append(",");
				html.append("<input type='hidden' name='"+typeCode+"' value='"+price+"'/>");
				canBetNum++;
			}
			html.append("</li>");
		}
		cacheOdd.append("}");
		if(canBetNum>0)
		{
			html.append("<input type='hidden' name='cachedOdd' value='"+cacheOdd+"'/>");	
		}
		else
			disable="disabled";
		
		String button="<table width=\"230\" height=\"35\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"print_bg\">" +
				"<tbody><tr><td align=\"center\">" +
				"<input type=\"button\" value=\"取消\" class=\"btn2\" id=\"cancelButton\">" +
				"<input type=\"submit\" value=\"确定下注\" class=\"btn2 ml10\" "+disable+" ></td></tr></tbody>" +
				"</table>";
		html.append("</ul></td></tr>");
		html.append("<tr><td width=\"34%\" class=\"l_color\">下注笔数</td><td width=\"66%\">"
				+ betList.size() + "笔</td></tr>");
		html.append("<tr><td class=\"l_color\">合计注额</td><td>￥" + totalMon
				+ "</td></tr>");
		html.append("</tbody></table>");
		if(mesMap.get("errorType")!=null)
			html.append(button);
		html.append("</div>");
		
		
		
		return html.toString();
		
	}
	
	protected Map<String,String> checkLMItemQuotas(List<BaseBet> betList,String playTypeCode)
	{
		Map<String,String> messageMap=new HashMap<String,String>();
		UserCommission userCommsion=userCommissionLogic.queryUserPlayTypeCommission(this.getCurrentUser().getID(),MemberStaff.USER_TYPE_MEMBER, playTypeCode);
		Integer itemQuotas=userCommsion.getItemQuotas();
		for (int i = 0; i < betList.size(); i++) {
			BaseBet bet=betList.get(i);
			String attr=bet.getSplitAttribute();
			String periodsNum=bet.getPeriodsNum();
			Integer price=bet.getMoney();
			Integer userTotalMoney=betLogic.queryUserLMitemQuotasMoney(Constant.LOTTERY_TYPE_GDKLSF, periodsNum,playTypeCode, this.getCurrentUser().getID().toString() ,attr);
			 if(userTotalMoney+Integer.valueOf(price)>itemQuotas)
				{
					messageMap.put("errorMessage", "超过单项限额!"+itemQuotas);
					messageMap.put("errorType", "ExceedItem");
					return messageMap;
				}
		}
		
		
		return messageMap;
	}
	
	protected Map<String, String> checkLMItemQuotasForNC(List<BaseBet> betList, String playTypeCode) {
		Map<String, String> messageMap = new HashMap<String, String>();
		UserCommission userCommsion = userCommissionLogic.queryUserPlayTypeCommission(this.getCurrentUser().getID(), MemberStaff.USER_TYPE_MEMBER, playTypeCode);
		Integer itemQuotas = userCommsion.getItemQuotas();
		for (int i = 0; i < betList.size(); i++) {
			BaseBet bet = betList.get(i);
			String attr = bet.getSplitAttribute();
			String periodsNum = bet.getPeriodsNum();
			Integer price = bet.getMoney();
			Integer userTotalMoney = betLogic.queryUserLMitemQuotasMoneyForNC(Constant.LOTTERY_TYPE_NC, periodsNum, playTypeCode, this.getCurrentUser().getID().toString(), attr);
			if (userTotalMoney + Integer.valueOf(price) > itemQuotas) {
				messageMap.put("errorMessage", "超过单项限额!" + itemQuotas);
				messageMap.put("errorType", "ExceedItem");
				return messageMap;
			}
		}

		return messageMap;
	}
	
	public static void main(String[] args) {
		String a="[\"17\",\"01\",\"04\",\"05\",\"09\",\"02\",\"08\",\"18\"]";
        String b=a.replace("[", "").replace("]", "").replace("\"", "");
        //System.out.println(b);
        	
		
	}

	public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}

	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}
	
	public ShopsInfo getCurrentShopsInfo() {

		return (ShopsInfo) this.getRequest().getSession(true).getAttribute(Constant.SAFETY_INFO_IN_SESSION);

	}
	
	protected String ajaxJSSBSubmitResult(String period, List<BaseBet> betList,
 Integer totalMon, Map<String, String> mesMap) {

		StringBuffer html = new StringBuffer();
		html.append("<div class=\"p\">");
		if (mesMap.get("errorType") == null)
			html.append("<div class=\"print_btn\"><span><input type=\"button\" value=\"打 印\" class=\"btn2\" name=\"\" id=\"print\"></span><span class=\"ml10\"><input type=\"button\" value=\"返 回\" class=\"btn2\" name=\"\" id=\"retbtn\"></span></div>");
		html.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"231\" class=\"print_l\"><tbody>");
		if (mesMap.get("errorType") != null) {
			html.append("<tr><td class=\"t_td_caption_1\" colspan=\"2\"><strong class=\"red\">下注被退回，请再次确认！</strong></td></tr>");
		}
		html.append("<tr><td colspan=\"2\" class=\"qishu\">" + period + "期</td></tr><tr><td colspan=\"2\"><ul>");
		boolean errorCanBet = true;
		String disable = "";
		int canBetNum = 0;
		StringBuffer cacheOdd = new StringBuffer("{");
		for (int i = 0; i < betList.size(); i++) {
			BaseBet bet = betList.get(i);
			Integer price = bet.getMoney();
			BigDecimal odds = bet.getOdds();
			String typeCode = bet.getPlayType();
			PlayType playType = PlayTypeUtils.getPlayType(typeCode);
			String subType = playType.getPlaySubType();
			String finalType = playType.getPlayFinalType();
			String subTypeName = playType.getSubTypeName();
			if (subTypeName == null)
				subTypeName = "";
			else if (("GROUP".equals(subType) && ("DA".equals(finalType) || "X".equals(finalType) || "DAN".equals(finalType) || "S".equals(finalType))))
				subTypeName = "";
			String typeName = playType.getFinalTypeName();
			float winMoney = (odds.floatValue() - 1) * price;
			float roundMoney = (float) (Math.round(winMoney * 100)) / 100;

			html.append("<li><p>注单号：" + bet.getOrderNo() + "#</p><p class=\"t_center\"><span class=\"blue\">" + subTypeName + " 『 " + typeName + " 』"
					+ "</span>@ <strong class=\"red\">" + odds + "</strong></p><p>下注额：" + price + "</p><p>可赢额：" + roundMoney + "</p>");
			if ("ExceedItem".equals(bet.getBetError())) {
				html.append("<p><span class=\"lj\">累计超过 “单期最高限额”</span></p>");
				errorCanBet = false;
				// disable="disabled";
			} else if ("OddChanged".equals(bet.getBetError())) {
				cacheOdd.append(typeCode).append(":").append(odds).append(",");
				html.append("<input type='hidden' name='" + typeCode + "' value='" + price + "'/>");
				html.append("<p><span class=\"bd\">下注賠率有變動 請再次確認</span></p>");
				canBetNum++;

			} else {
				cacheOdd.append(typeCode).append(":").append(odds).append(",");
				html.append("<input type='hidden' name='" + typeCode + "' value='" + price + "'/>");
				canBetNum++;
			}
			html.append("</li>");

		}
		cacheOdd.append("}");
		if (canBetNum > 0) {
			html.append("<input type='hidden' name='cachedOdd' value='" + cacheOdd + "'/>");
		} else {
			disable = "disabled";
		}
		String button = "<table width=\"230\" height=\"35\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"print_bg\">"
				+ "<tbody><tr><td align=\"center\">" + "<input type=\"button\" value=\"取消\" class=\"btn2\" id=\"cancelButton\">"
				+ "<input type=\"submit\" value=\"确定下注\" class=\"btn2 ml10\" " + disable + " ></td></tr></tbody>" + "</table>";
		html.append("</ul></td></tr>");
		if (mesMap.get("errorType") == null) {
			html.append("<tr><td width=\"34%\" class=\"l_color\">下注笔数</td><td width=\"66%\">" + betList.size() + "笔</td></tr>");
			html.append("<tr><td class=\"l_color\">合计注额</td><td>￥" + totalMon + "</td></tr>");
		}
		html.append("</tbody></table>");
		if (mesMap.get("errorType") != null)
			html.append(button);
		html.append("</div>");

		return html.toString();

	}
	
}
