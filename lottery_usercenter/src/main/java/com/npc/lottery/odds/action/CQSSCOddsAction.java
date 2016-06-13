package com.npc.lottery.odds.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.npc.lottery.boss.logic.interf.IShopsLogic;
import com.npc.lottery.common.action.BaseAdminLotteryAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.logic.interf.IPlayTypeLogic;
import com.npc.lottery.odds.entity.OpenPlayOdds;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.logic.interf.IOpenPlayOddsLogic;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.sysmge.entity.ShopsPlayOddsLog;
import com.npc.lottery.sysmge.logic.interf.IShopsPlayOddsLogLogic;
import com.npc.lottery.util.PlayTypeUtils;
import com.npc.lottery.util.WebTools;

import edu.emory.mathcs.backport.java.util.Arrays;
public class CQSSCOddsAction extends BaseAdminLotteryAction {

	private static final long serialVersionUID = 1L;

	private IPlayTypeLogic playTypeLogic;
	private IShopOddsLogic shopOddsLogic;

	private String type = "oddSet";

	/*
	 * 快乐十分 1,两面盘，2总和龙虎，3连码4，1-8球 时时彩 1-5球
	 */
	private String subType = Constant.LOTTERY_CQSSC_SUBTYPE_BALL_FIRST;
	private IShopsLogic shopsLogic = null;
	protected ICQPeriodsInfoLogic icqPeriodsInfoLogic;
	private IOpenPlayOddsLogic openPlayOddsLogic;	
	private IShopsPlayOddsLogLogic shopsPlayOddsLogLogic;
	private String periodsState;
	private String searchTime = "30";
	
	public IShopsLogic getShopsLogic() {
		return shopsLogic;
	}

	public void setShopsLogic(IShopsLogic shopsLogic) {
		this.shopsLogic = shopsLogic;
	}

	public String updateOdds() {

		String userId = this.getCurrentUser().getID().toString();
		String shopsCode = this.getCurrentUser().getSafetyCode();
		Enumeration<?> names = this.getRequest().getParameterNames();
		List<ShopsPlayOdds> oddsList = new ArrayList<ShopsPlayOdds>();
		String[] notPlay = this.getRequest().getParameterValues("notPlay");

		// 未開盤不能修改
    	if(null == this.getCQRunningPeriodsNum() ||"".equals(getCQRunningPeriodsNum()))
    		return SUCCESS;
    	
		String submitType = this.getRequest().getParameter("submitType");
		String money = this.getRequest().getParameter("odds");
		List<String> notPlayList = new ArrayList<String>();
		if (notPlay != null && notPlay.length != 0) {
			notPlayList = Arrays.asList(notPlay);
		}
		String type=this.getRequest().getParameter("integrate");
		ShopsPlayOdds spo = null;
		while (names.hasMoreElements()) {
			String ele = (String) names.nextElement();
			spo = new ShopsPlayOdds();

			if (ele.indexOf("CQSSC_BALL_") != -1
					|| ele.indexOf("CQSSC_DOUBLESIDE_") != -1|| ele.indexOf("CQSSC_BZ_") != -1|| ele.indexOf("CQSSC_SZ_") != -1
					|| ele.indexOf("CQSSC_BS_") != -1|| ele.indexOf("CQSSC_DZ_") != -1|| ele.indexOf("CQSSC_ZL_") != -1
					) {
				if ("integrate".equals(submitType)) {
					
					String eleLastStr=StringUtils.substring(ele, ele.lastIndexOf("_")+1);
					if(!"all".equals(type))
					{	
					if(!StringUtils.isNumeric(eleLastStr))
						continue;
                    if("da".equals(type)&&Integer.valueOf(eleLastStr)<=10)
                    {
                    	continue;
                    }
                    else if("xiao".equals(type)&&Integer.valueOf(eleLastStr)>=10)
                    {
                    	continue;
                    }
                    else if("dan".equals(type)&&Integer.valueOf(eleLastStr)%2==0)
                    {
                    	continue;
                    }
                    else if("shuang".equals(type)&&Integer.valueOf(eleLastStr)%2!=0)
                    {
                    	continue;
                    }
					}
					if (GenericValidator.isFloat(money)) {
						spo.setRealOdds(BigDecimal.valueOf(Double
								.valueOf(money)));
						spo.setPlayTypeCode(ele);
						spo.setShopsCode(shopsCode);
						spo.setRealUpdateDate(new Date());
						spo.setRealUpdateUser(Long.valueOf(userId));
						oddsList.add(spo);
					}
				

				} else {

					String odds = this.getRequest().getParameter(ele);
					if (GenericValidator.isFloat(odds)) {
						spo.setRealOdds(BigDecimal.valueOf(Double
								.valueOf(odds)));
						spo.setPlayTypeCode(ele);
						spo.setShopsCode(shopsCode);
						spo.setRealUpdateDate(new Date());
						spo.setRealUpdateUser(Long.valueOf(userId));
						if (notPlayList.contains(ele))
							spo.setState(Constant.SHOP_PLAY_ODD_STATUS_INVALID);
						else
							spo.setState(Constant.SHOP_PLAY_ODD_STATUS_VALID);
						oddsList.add(spo);
					}
				}
			}

		}
		shopOddsLogic.setRunningPeriodsNum(getCQRunningPeriodsNum());
		shopOddsLogic.updateShopOdds(oddsList);
		return SUCCESS;

	}
	public String getCQRunningPeriodsNum()
	{
		CQPeriodsInfo runningPeriods=icqPeriodsInfoLogic.findCurrentPeriod();
		String periodsNum="";
		if(null==runningPeriods){ 
			runningPeriods = new CQPeriodsInfo();
			this.periodsState = "當前盤未開盤，不能修改";
			return null;
		}else{
			Date now = new Date();
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
    	if(null == this.getCQRunningPeriodsNum() ||"".equals(getCQRunningPeriodsNum()))
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
		String ip =WebTools.getClientIpAddr(getRequest());
		log.setIp(ip);
		log.setType("2");
		try{
			log.setPeriodsNum(this.getCQRunningPeriodsNum());
			shopsPlayOddsLogLogic.saveLog(log);
		}catch(Exception e){
		}
		
		return this.ajaxJson(jsonMap);

	}
	
	public String updateOpenOdds(){
		
		return SUCCESS;
	}

	public String shopLogin() {

		return SUCCESS;
	}

	public String chooseLogin() {
		return SUCCESS;
	}

	public String userLogin() {
		return SUCCESS;
	}

	public IPlayTypeLogic getPlayTypeLogic() {
		return playTypeLogic;
	}

	public void setPlayTypeLogic(IPlayTypeLogic playTypeLogic) {
		this.playTypeLogic = playTypeLogic;
	}
	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public IShopOddsLogic getShopOddsLogic() {
		return shopOddsLogic;
	}

	public void setShopOddsLogic(IShopOddsLogic shopOddsLogic) {
		this.shopOddsLogic = shopOddsLogic;
	}
	
public ICQPeriodsInfoLogic getIcqPeriodsInfoLogic() {
		return icqPeriodsInfoLogic;
	}

	public void setIcqPeriodsInfoLogic(ICQPeriodsInfoLogic icqPeriodsInfoLogic) {
		this.icqPeriodsInfoLogic = icqPeriodsInfoLogic;
	}

public static void main(String[] args) {
	System.out.println(StringUtils.substring("22_", "22_".lastIndexOf("_")+1));

}

public IOpenPlayOddsLogic getOpenPlayOddsLogic() {
	return openPlayOddsLogic;
}

public void setOpenPlayOddsLogic(IOpenPlayOddsLogic openPlayOddsLogic) {
	this.openPlayOddsLogic = openPlayOddsLogic;
}

public IShopsPlayOddsLogLogic getShopsPlayOddsLogLogic() {
	return shopsPlayOddsLogLogic;
}

public void setShopsPlayOddsLogLogic(
		IShopsPlayOddsLogLogic shopsPlayOddsLogLogic) {
	this.shopsPlayOddsLogLogic = shopsPlayOddsLogLogic;
}

public String getPeriodsState() {
	return periodsState;
}

public void setPeriodsState(String periodsState) {
	this.periodsState = periodsState;
}

public String getSearchTime() {
	return searchTime;
}

public void setSearchTime(String searchTime) {
	this.searchTime = searchTime;
}
}

