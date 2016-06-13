package com.npc.lottery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.boss.logic.interf.IBJSCPeriodsInfoBossLogic;
import com.npc.lottery.boss.logic.interf.ICQPeriodsInfoBossLogic;
import com.npc.lottery.boss.logic.interf.IGDPeriodsInfoBossLogic;
import com.npc.lottery.boss.logic.interf.INCPeriodsInfoBossLogic;
import com.npc.lottery.boss.logic.interf.IShopsLogic;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.manage.logic.interf.IShopsDeclarattonLogic;
import com.npc.lottery.member.logic.interf.ICheckLogic;
import com.npc.lottery.member.logic.interf.ILotteryResultLogic;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;
import com.npc.lottery.util.MD5;

public class BossAjax extends BaseLotteryAction {
	Logger logger = Logger.getLogger(BossAjax.class);
	private static final long serialVersionUID = 1L;
	private IShopsLogic shopsLogic;
	private IChiefStaffExtLogic chiefStaffExtLogic = null;
	private IShopOddsLogic shopOddsLogic =null;
	private ShopsInfo shopsInfo;
	private IShopsDeclarattonLogic shopsDeclarattonLogic;
	private ICheckLogic checkLogic;
	private ShopSchemeService shopSchemeService;
	private ILotteryResultLogic lotteryResultLogic = null;
	private IGDPeriodsInfoBossLogic gdPeriodsInfoBossLogic;
	private ICQPeriodsInfoBossLogic icqPeriodsInfoBossLogic;
	private IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic;
	private IBJSCPeriodsInfoBossLogic bjscPeriodsInfoBossLogic;
	private INCPeriodsInfoBossLogic ncPeriodsInfoBossLogic;
	
	//商铺号查询
	public String findShopsCode() {
		shopsInfo = shopsLogic.findShopsCode(getRequest().getParameter("shopsCode"));
		Map map=new HashMap();
		String sCount;
		if (shopsInfo==null){
			sCount = "0";
		}else{
			sCount = "1";
		}
		map.put("count", sCount);
		return this.ajaxJson(map);
	}
	//商铺名称查询
	public String findShopsName() {
		shopsInfo = shopsLogic.findShopsName(getRequest().getParameter("shopsName"));
		Map map=new HashMap();
		String sCount;
		if (shopsInfo==null){
			sCount = "0";
		}else{
			sCount = "1";
		}
		map.put("count", sCount);
		return this.ajaxJson(map);
	}
	
	// 数据校验
	public String checkPet() {
		
		Map<String,String> map = new HashMap<String,String>();
		String periodsNum = getRequest().getParameter("periodsNum");
		String lotteryType = getRequest().getParameter("type");
		String shopsCode = getRequest().getParameter("shopsCode");
		String result = "";
		// 获取商铺对应的scheme
		String scheme = shopSchemeService.getSchemeByShopCode(shopsCode);
		logger.info("开始手动校验投注表、补货表与备份表的数据,盘期" + periodsNum + ",种类" + lotteryType);
		boolean resultReplenish = checkLogic.checkReplenishWithHistory(periodsNum, scheme);// 校验补货
		boolean resultPet = checkLogic.checkBetWithBackupByPeridosNum(periodsNum, lotteryType, scheme);// 校验投注

		String resultR = "";
		String resultP = "";
		if (resultReplenish == true) {
			resultR = "正确";
		} else {
			resultR = "不正确";
		}
		if (resultPet == true) {
			resultP = "正确";
		} else {
			resultP = "不正确";
		}
		result = "结果是:" + "投注是" + resultP + ",补货是" + resultR;
		logger.info("结束手动校验投注表、补货表与备份表的数据,盘期" + periodsNum + ",种类" + lotteryType + "结果是:" + "投注是" + resultP + ",补货是"
		        + resultR);
		map.put("status", result);
		return this.ajaxJson(map);
	}
	
	public String ajaxUpdatePwdBoss(){
		Map<String, String> messageMap = new HashMap<String, String>();
		String shopsCode = getRequest().getParameter("shopsCode");
		String scheme = shopSchemeService.getSchemeByShopCode(shopsCode);
		List<ChiefStaffExt> list = shopOddsLogic.findChiefByShopsCodeByScheme(shopsCode, scheme);
		ChiefStaffExt chiefStaffExt=list.get(0);
//		String chiefAccount = list.get(0).getManagerStaff().getAccount();	
//		ChiefStaffExt chiefStaffExt = chiefStaffExtLogic.queryChiefStaffExt("account", chiefAccount);
		if(chiefStaffExt != null){
            MD5 md5 = new MD5();
            String userPwdOrignMd5 = md5.getMD5ofStr(chiefStaffExt.getManagerStaff().getAccount());
            chiefStaffExt.setUserPwd(userPwdOrignMd5);
            try {
                chiefStaffExtLogic.updateChiefPassword(chiefStaffExt,scheme);
            } catch (Exception e) {
            	messageMap.put("errorMessage","修改密码不成功");
                e.printStackTrace();
                logger.error("执行" + this.getClass().getSimpleName()
                        + "中的方法ajaxUpdatePwdBoss时出现错误 "
                        + e.getMessage());
            }
		}
		messageMap.put("success","修改密码成功");
		return this.ajaxJson(messageMap); 
	}
	
	public String ajaxDelPriod() {
		String type = this.getRequest().getParameter("type");
		if(type.equals("stop")){
			logger.info("进入总管该期停开");
		}else{
			logger.info("进入总管该期作废");
		}
		
		Map<String, String> messageMap = new HashMap<String, String>();
		
		String periodsNum = getRequest().getParameter("priodNum");
		String subType = getRequest().getParameter("subType");
		//停开查询投注表
		String[] stopTables=null;
		//作废查询投注历史表
		String invalidTables=null;
		Map<String,String> shopSchemeMap = shopSchemeService.getShopSchemeMap();
		try {
			// 获取商铺对应的scheme列表
			if ("GDKLSF".equals(subType)) {
				shopsLogic.updateGDStopStart(periodsNum, shopSchemeMap);
				stopTables=Constant.GDKLSF_TABLE_LIST;
				invalidTables=Constant.GDKLSF_HIS_TABLE_NAME;
			} else if ("CQSSC".equals(subType)) {
				shopsLogic.updateCQStopStart(periodsNum, shopSchemeMap);
				stopTables=Constant.CQSSC_TABLE_LIST;
				invalidTables=Constant.CQSSC_HIS_TABLE_NAME;
			} else if ("K3".equals(subType)) {
				shopsLogic.updateK3StopStart(periodsNum, shopSchemeMap);
				stopTables=Constant.K3_TABLE_LIST;
				invalidTables=Constant.GDKLSF_HIS_TABLE_NAME;
			} else if (Constant.LOTTERY_TYPE_BJSC.equals(subType)) {
				shopsLogic.updateBJStopStart(periodsNum, shopSchemeMap);
				stopTables=Constant.BJSC_TABLE_LIST;
				invalidTables=Constant.BJSC_HIS_TABLE_NAME;
			} else if (Constant.LOTTERY_TYPE_NC.equals(subType)) {
				shopsLogic.updateNCStopStart(periodsNum, shopSchemeMap);
				stopTables=Constant.NC_TABLE_LIST;
				invalidTables=Constant.NC_HIS_TABLE_NAME;
			}
			//重新计算报表
			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				logger.info("总管该期作废重新计算报表START");
				String scheme = shopScheme.getValue();
				boolean flag = lotteryResultLogic.saveReportPeriod(periodsNum, subType, scheme);// 统计报表
				logger.info("总管该期作废重新计算报表scheme IS :"+scheme+"，操作结果是："+flag);
				
			}
			boolean isTodayPeriods=checkPeriodsIsToday(subType,periodsNum);
			//属于今天盘期
			if(isTodayPeriods){
				if(type.equals("stop")){  
					//盘期停开，恢复会员信用额度
					shopsLogic.stopPeriodsByUpdateAvailableCredit(periodsNum,shopSchemeMap,stopTables);
				}else{
					//盘期作废，恢复会员信用额度
					shopsLogic.invalidPeriodsByUpdateAvailableCredit(periodsNum, shopSchemeMap, invalidTables);
				}
			}
		} catch (Exception e) {
			logger.error("<--停開盤期異常：periodsNum:" + periodsNum + "-->", e);
			messageMap.put("errorMessage", "停開盤期操作異常，請重試");
			return this.ajaxJson(messageMap);
		}
		messageMap.put("success", "操作成功");
		return this.ajaxJson(messageMap);
	}
	
	/**
	 * 判断是否属于今天盘期
	 * @param subType
	 * @param periodsNum
	 * @return
	 */
	private boolean checkPeriodsIsToday(String subType,String periodsNum){
		boolean ret=false;
		if ("GDKLSF".equals(subType)) {
			List<GDPeriodsInfo> gdPeriods=gdPeriodsInfoBossLogic.queryTodayAllPeriods();
			for (GDPeriodsInfo gdPeriodsInfo : gdPeriods) {
				if(gdPeriodsInfo.getPeriodsNum().equals(periodsNum)){
					ret=true;
				}
			}
		} else if ("CQSSC".equals(subType)) {
			List<CQPeriodsInfo> cqPeriods=icqPeriodsInfoBossLogic.queryTodayPeriods();
			for (CQPeriodsInfo cqPeriodsInfo : cqPeriods) {
				if(cqPeriodsInfo.getPeriodsNum().equals(periodsNum)){
					ret=true;
				}
			}
		} else if ("K3".equals(subType)) {
			List<JSSBPeriodsInfo> jssbPeriods=jssbPeriodsInfoLogic.queryTodayAllPeriods();
			for (JSSBPeriodsInfo jssbPeriodsInfo : jssbPeriods) {
				if(jssbPeriodsInfo.getPeriodsNum().equals(periodsNum)){
					ret=true;
				}
			}
		} else if (Constant.LOTTERY_TYPE_BJSC.equals(subType)) {
			List<BJSCPeriodsInfo> bjscPeriods=bjscPeriodsInfoBossLogic.queryTodayAllPeriods();
			for (BJSCPeriodsInfo bjscPeriodsInfo : bjscPeriods) {
				if(bjscPeriodsInfo.getPeriodsNum().equals(periodsNum)){
					ret=true;
				}
			}
		} else if (Constant.LOTTERY_TYPE_NC.equals(subType)) {
			List<NCPeriodsInfo> ncPeriods=ncPeriodsInfoBossLogic.queryTodayAllPeriods();
			for (NCPeriodsInfo ncPeriodsInfo : ncPeriods) {
				if(ncPeriodsInfo.getPeriodsNum().equals(periodsNum)){
					ret=true;
				}
			}
		}
		return ret;
	}
	
	public IShopsLogic getShopsLogic() {
		return shopsLogic;
	}

	public void setShopsLogic(IShopsLogic shopsLogic) {
		this.shopsLogic = shopsLogic;
	}

	public ShopsInfo getShopsInfo() {
		return shopsInfo;
	}

	public void setShopsInfo(ShopsInfo shopsInfo) {
		this.shopsInfo = shopsInfo;
	}
	public IChiefStaffExtLogic getChiefStaffExtLogic() {
		return chiefStaffExtLogic;
	}
	public void setChiefStaffExtLogic(IChiefStaffExtLogic chiefStaffExtLogic) {
		this.chiefStaffExtLogic = chiefStaffExtLogic;
	}
	public IShopOddsLogic getShopOddsLogic() {
		return shopOddsLogic;
	}
	public void setShopOddsLogic(IShopOddsLogic shopOddsLogic) {
		this.shopOddsLogic = shopOddsLogic;
	}
	public IShopsDeclarattonLogic getShopsDeclarattonLogic() {
		return shopsDeclarattonLogic;
	}
	public void setShopsDeclarattonLogic(
			IShopsDeclarattonLogic shopsDeclarattonLogic) {
		this.shopsDeclarattonLogic = shopsDeclarattonLogic;
	}
	public ICheckLogic getCheckLogic() {
		return checkLogic;
	}
	public void setCheckLogic(ICheckLogic checkLogic) {
		this.checkLogic = checkLogic;
	}
	public ShopSchemeService getShopSchemeService() {
		return shopSchemeService;
	}
	public void setShopSchemeService(ShopSchemeService shopSchemeService) {
		this.shopSchemeService = shopSchemeService;
	}
	public ILotteryResultLogic getLotteryResultLogic() {
		return lotteryResultLogic;
	}
	public void setLotteryResultLogic(ILotteryResultLogic lotteryResultLogic) {
		this.lotteryResultLogic = lotteryResultLogic;
	}
	public IGDPeriodsInfoBossLogic getGdPeriodsInfoBossLogic() {
		return gdPeriodsInfoBossLogic;
	}
	public void setGdPeriodsInfoBossLogic(IGDPeriodsInfoBossLogic gdPeriodsInfoBossLogic) {
		this.gdPeriodsInfoBossLogic = gdPeriodsInfoBossLogic;
	}
	public ICQPeriodsInfoBossLogic getIcqPeriodsInfoBossLogic() {
		return icqPeriodsInfoBossLogic;
	}
	public void setIcqPeriodsInfoBossLogic(ICQPeriodsInfoBossLogic icqPeriodsInfoBossLogic) {
		this.icqPeriodsInfoBossLogic = icqPeriodsInfoBossLogic;
	}
	public IJSSBPeriodsInfoLogic getJssbPeriodsInfoLogic() {
		return jssbPeriodsInfoLogic;
	}
	public void setJssbPeriodsInfoLogic(IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic) {
		this.jssbPeriodsInfoLogic = jssbPeriodsInfoLogic;
	}
	public IBJSCPeriodsInfoBossLogic getBjscPeriodsInfoBossLogic() {
		return bjscPeriodsInfoBossLogic;
	}
	public void setBjscPeriodsInfoBossLogic(IBJSCPeriodsInfoBossLogic bjscPeriodsInfoBossLogic) {
		this.bjscPeriodsInfoBossLogic = bjscPeriodsInfoBossLogic;
	}
	public INCPeriodsInfoBossLogic getNcPeriodsInfoBossLogic() {
		return ncPeriodsInfoBossLogic;
	}
	public void setNcPeriodsInfoBossLogic(INCPeriodsInfoBossLogic ncPeriodsInfoBossLogic) {
		this.ncPeriodsInfoBossLogic = ncPeriodsInfoBossLogic;
	}
	
}
