package com.npc.lottery.odds.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.npc.lottery.common.action.BaseAdminLotteryAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.logic.interf.IPlayTypeLogic;
import com.npc.lottery.odds.entity.OpenPlayOdds;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.logic.interf.IOpenPlayOddsLogic;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.sysmge.entity.ShopsPlayOddsLog;
import com.npc.lottery.sysmge.logic.interf.IShopsPlayOddsLogLogic;
import com.npc.lottery.util.PlayTypeUtils;
import com.npc.lottery.util.WebTools;

public class K3OddsAction extends BaseAdminLotteryAction {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7158171852631826168L;
	private String type = "oddSet";
	private String searchTime = "30";
	private String periodsState;
	
	public String getK3RunningPeriodsNum()
	{
		JSSBPeriodsInfo runningPeriods=jssbPeriodsInfoLogic.findCurrentPeriod();
		String periodsNum="";
		if(null==runningPeriods){ 
			runningPeriods = new JSSBPeriodsInfo();
			this.periodsState = "當前盤未開盤，不能修改";
			return null;
		}else{
			Date now=new Date();
			if(now.before(runningPeriods.getStopQuotTime())){
				//当前时间在封盘时间之前,状态为开盘
				periodsNum=runningPeriods.getPeriodsNum();
			}
		}
		return periodsNum;
	}
	
	@SuppressWarnings("null")
	public String ajaxSetOdds() {

		String typeCode = this.getRequest().getParameter("typeCode");
		String jiajian = this.getRequest().getParameter("jiajian");
		String shopCode = this.getCurrentUser().getSafetyCode();
		String sOdd = this.getRequest().getParameter("odd");

		// 未開盤不能修改
    	if(null == this.getK3RunningPeriodsNum() ||"".equals(getK3RunningPeriodsNum()))
    	{
    		Map<String, String> jsonMap = new HashMap<String, String>();
    		jsonMap.put("error","當前盤未開盤，不能修改");
    		return this.ajaxJson(jsonMap);
    	}
    	
		ShopsPlayOddsLog log = new ShopsPlayOddsLog();
		ShopsPlayOdds shopOdds = shopOddsLogic.queryShopPlayOdds(shopCode,typeCode);
		log.setRealOddsOrigin(shopOdds.getRealOdds());
		BigDecimal realOdds = shopOdds.getRealOdds();
		
		//获取自动降赔率
//		PlayType playType=PlayTypeUtils.getPlayType(typeCode);
//		String oddType=playType.getOddsType();
//		OpenPlayOdds openOdd=shopOddsLogic.queryOpenPlayOdds(oddType,shopCode);
//        BigDecimal autoMoney=openOdd.getAutoOdds();//自动降赔率
		BigDecimal autoMoney=null;
		if(sOdd!=null||sOdd.length()!=0){
			autoMoney=BigDecimal.valueOf(Double.parseDouble(sOdd));
		}else{
			//获取自动降赔率
			PlayType playType=PlayTypeUtils.getPlayType(typeCode);
			String oddType=playType.getOddsType();
			OpenPlayOdds openOdd=shopOddsLogic.queryOpenPlayOdds(oddType,shopCode);
	        autoMoney=openOdd.getAutoOdds();//自动降赔率
		}

        if (null!=jiajian){
			if ("jia".equals(jiajian)) {
				realOdds = realOdds.add(autoMoney);
			} else if ("jian".equals(jiajian))
				realOdds = realOdds.subtract(autoMoney);
		}else if (null!=sOdd){
			try {
				realOdds = BigDecimal.valueOf(Double.valueOf(sOdd));
			} catch (Exception e) {
				realOdds = shopOdds.getRealOdds();
			}
		}

		List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		filtersPlayType.add(Restrictions.eq("shopsCode",shopCode));
		filtersPlayType.add(Restrictions.eq("oddsType",shopOdds.getOddsType()));
		OpenPlayOdds openOdds =	openPlayOddsLogic.findOpenPlayOdds(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));
		//如果值大於 開盤限制的最大值 則為最大值
		if(realOdds.compareTo(openOdds.getBigestOdds())==1)
		{
			realOdds = openOdds.getBigestOdds();
		}
		if(realOdds.compareTo(BigDecimal.ZERO)==-1)
		{
			realOdds = BigDecimal.ZERO;
		}
		shopOdds.setRealOdds(realOdds);
		shopOddsLogic.updateShopOdds(shopOdds);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("changedValue", realOdds.toString());
		jsonMap.put("typeCode", typeCode);
		

		log.setShopCode(shopCode);
		log.setRealUpdateDateNew(new Date());
		log.setPlayTypeCode(typeCode);
		log.setRealOddsNew(realOdds);
		log.setRealUpdateUserNew(Integer.valueOf(this.getCurrentUser().getID()+""));
		log.setRealUpdateDateOrigin(shopOdds.getRealUpdateDate());
		log.setRealUpdateUserOrigin(shopOdds.getRealUpdateUser().intValue());
		log.setRemark("");
		log.setOddsType(shopOdds.getOddsType());
		log.setOddsTypeX(shopOdds.getOddsTypeX());
		//获取修改赔率者的IP地址
		String ip = WebTools.getClientIpAddr(getRequest());
        log.setIp(ip);
        log.setType("2");
		try{
			log.setPeriodsNum(this.getK3RunningPeriodsNum());
			shopsPlayOddsLogLogic.saveLog(log);
		}catch(Exception e){
		}
		
		return this.ajaxJson(jsonMap);

	}
	
	private IShopsPlayOddsLogLogic shopsPlayOddsLogLogic;
	private IOpenPlayOddsLogic openPlayOddsLogic;	
	private IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic	= null;
	private IPlayTypeLogic playTypeLogic;
	private IShopOddsLogic shopOddsLogic;

	public String getType() {
		return type;
	}

	public String getSearchTime() {
		return searchTime;
	}

	public String getPeriodsState() {
		return periodsState;
	}

	public IShopsPlayOddsLogLogic getShopsPlayOddsLogLogic() {
		return shopsPlayOddsLogLogic;
	}

	public IOpenPlayOddsLogic getOpenPlayOddsLogic() {
		return openPlayOddsLogic;
	}

	public IPlayTypeLogic getPlayTypeLogic() {
		return playTypeLogic;
	}

	public IShopOddsLogic getShopOddsLogic() {
		return shopOddsLogic;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
	}

	public void setPeriodsState(String periodsState) {
		this.periodsState = periodsState;
	}

	public void setShopsPlayOddsLogLogic(
			IShopsPlayOddsLogLogic shopsPlayOddsLogLogic) {
		this.shopsPlayOddsLogLogic = shopsPlayOddsLogLogic;
	}

	public void setOpenPlayOddsLogic(IOpenPlayOddsLogic openPlayOddsLogic) {
		this.openPlayOddsLogic = openPlayOddsLogic;
	}

	public IJSSBPeriodsInfoLogic getJssbPeriodsInfoLogic() {
		return jssbPeriodsInfoLogic;
	}

	public void setJssbPeriodsInfoLogic(IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic) {
		this.jssbPeriodsInfoLogic = jssbPeriodsInfoLogic;
	}

	public void setPlayTypeLogic(IPlayTypeLogic playTypeLogic) {
		this.playTypeLogic = playTypeLogic;
	}

	public void setShopOddsLogic(IShopOddsLogic shopOddsLogic) {
		this.shopOddsLogic = shopOddsLogic;
	}
}
