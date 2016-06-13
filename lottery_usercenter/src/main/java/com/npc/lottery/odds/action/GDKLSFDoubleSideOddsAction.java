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

import com.npc.lottery.boss.logic.interf.IShopsLogic;
import com.npc.lottery.common.action.BaseAdminLotteryAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.member.logic.interf.IPlayTypeLogic;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;

import edu.emory.mathcs.backport.java.util.Arrays;

public class GDKLSFDoubleSideOddsAction extends BaseAdminLotteryAction {

	private static final long serialVersionUID = 1L;

	private IPlayTypeLogic playTypeLogic;
	private IShopOddsLogic shopOddsLogic;

	private IGDPeriodsInfoLogic periodsInfoLogic;

	private String type = "oddSet";

	/*
	 * 快乐十分 1,两面盘，2总和龙虎，3连码4，1-8球 时时彩 1-5球
	 */
	private String subType = Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIRST;
	private IShopsLogic shopsLogic = null;
	private String periodsState;
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
    	if(null == this.getGDRunningPeriodsNum() ||"".equals(getGDRunningPeriodsNum()))
    		return SUCCESS;
    	
    	
		String submitType = this.getRequest().getParameter("submitType");
		String money = this.getRequest().getParameter("odds");
		List<String> notPlayList = new ArrayList<String>();
		if (notPlay != null && notPlay.length != 0) {
			notPlayList = Arrays.asList(notPlay);
		}
		String type=this.getRequest().getParameter("integrate");
		while (names.hasMoreElements()) {
			String ele = (String) names.nextElement();

			if (ele.indexOf("GDKLSF_DOUBLESIDE_") != -1) {
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
					ShopsPlayOdds spo = new ShopsPlayOdds();
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
					ShopsPlayOdds spo = new ShopsPlayOdds();
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
		shopOddsLogic.updateShopOdds(oddsList);
		return SUCCESS;

	}

	public String ajaxSetOdds() {

		String typeCode = this.getRequest().getParameter("typeCode");
		String jiajian = this.getRequest().getParameter("jiajian");
		String shopCode = this.getCurrentUser().getSafetyCode();

		ShopsPlayOdds shopOdds = shopOddsLogic.queryShopPlayOdds(shopCode,
				typeCode);
		BigDecimal openOdds = shopOdds.getRealOdds();

		if ("jia".equals(jiajian)) {
			openOdds = openOdds.add(BigDecimal.valueOf(0.1));
		} else if ("jian".equals(jiajian))
			openOdds = openOdds.add(BigDecimal.valueOf(-0.1));
		shopOdds.setRealOdds(openOdds);
		shopOddsLogic.updateShopOdds(shopOdds);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("changedValue", openOdds.toString());
		jsonMap.put("typeCode", typeCode);
		return this.ajaxJson(jsonMap);

	}
	public String getGDRunningPeriodsNum()
	{
		GDPeriodsInfo runningPeriods= periodsInfoLogic.findCurrentPeriod();
		String periodsNum="";
		if(null==runningPeriods){
			runningPeriods = new GDPeriodsInfo();
			periodsState="當前盤未開盤，不能修改";
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

	public IGDPeriodsInfoLogic getPeriodsInfoLogic() {
		return periodsInfoLogic;
	}

	public void setPeriodsInfoLogic(IGDPeriodsInfoLogic periodsInfoLogic) {
		this.periodsInfoLogic = periodsInfoLogic;
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
public static void main(String[] args) {
	System.out.println(StringUtils.substring("22_", "22_".lastIndexOf("_")+1));

}

public String getPeriodsState() {
	return periodsState;
}

public void setPeriodsState(String periodsState) {
	this.periodsState = periodsState;
}
}
