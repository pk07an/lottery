package com.npc.lottery.manage.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.npc.lottery.common.action.BaseAdminLotteryAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.util.Page;
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
import com.npc.lottery.sysmge.entity.ShopsPlayOddsLog;
import com.npc.lottery.sysmge.logic.interf.IShopsPlayOddsLogLogic;
import com.npc.lottery.util.SelectBox;

public class ShopsPlayOddsLogAction extends BaseAdminLotteryAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7167925354925708364L;

	private static Logger log = Logger.getLogger(ShopsPlayOddsLogAction.class);

	private IShopsPlayOddsLogLogic shopsPlayOddsLogLogic;

	private String type = "privateAdmin";

	private String periodsNum;
	private String periodsNumType;
	private String playType;

	private IGDPeriodsInfoLogic periodsInfoLogic = null;
	private ICQPeriodsInfoLogic icqPeriodsInfoLogic = null;
	private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic = null;
	private IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic = null;
	private INCPeriodsInfoLogic ncPeriodsInfoLogic = null;

	public String viewMain() throws Exception {

		Map<String, String> playTypeMap = Constant.GDKLSF_ODDS_TYPE;
		List<SelectBox> periodsNumList = getPeriodsNumList("GD");
		String areaLottery = this.getRequest().getParameter("areaLottery");
		String shopCode = this.getCurrentUser().getSafetyCode();

		getRequest().setAttribute("periodType", areaLottery);

		List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
		filtersPeriodInfo.add(Restrictions.eq("shopCode", shopCode));
		if (null == areaLottery || "".equals(areaLottery)) {
			areaLottery = "GDKLSF";
		} else if ("BJ".equals(areaLottery)) {
			playTypeMap = Constant.BJSC_ODDSLOG_TYPE;
			periodsNumList = getPeriodsNumList("BJ");
		} else if ("CQSSC".equals(areaLottery)) {
			playTypeMap = Constant.CQSSC_ODDSLOG_TYPE;
			periodsNumList = getPeriodsNumList("CQ");
		} else if ("GDKLSF".equals(areaLottery)) {
			playTypeMap = Constant.GDKLSF_ODDSLOG_TYPE;
			periodsNumList = getPeriodsNumList("GD");
		} else if (Constant.LOTTERY_TYPE_K3.equals(areaLottery)) {
			playTypeMap = Constant.K3_ODDSLOG_TYPE;
			periodsNumList = getPeriodsNumList("K3");
		} else if (Constant.LOTTERY_TYPE_NC.equals(areaLottery)) {
			playTypeMap = Constant.NC_ODDSLOG_TYPE;
			periodsNumList = getPeriodsNumList("NC");
		}

		// System.out.println(periodsNum);
		if (null == periodsNum || "".equals(periodsNum)) {
			if ("BJ".equals(areaLottery)) {
				periodsNum = getPeriodsNumList("BJ").get(0).getKey();
			} else if ("CQSSC".equals(areaLottery)) {
				periodsNum = getPeriodsNumList("CQ").get(0).getKey();
			} else if ("GDKLSF".equals(areaLottery)) {
				periodsNum = getPeriodsNumList("GD").get(0).getKey();
			} else if (Constant.LOTTERY_TYPE_K3.equals(areaLottery)) {
				periodsNum = getPeriodsNumList(Constant.LOTTERY_TYPE_K3).get(0).getKey();
			} else if (Constant.LOTTERY_TYPE_NC.equals(areaLottery)) {
				periodsNum = getPeriodsNumList(Constant.LOTTERY_TYPE_NC).get(0).getKey();
			}
		} else {
			if ("BJ".equals(areaLottery) && !periodsNumType.equals(areaLottery)) {
				periodsNum = getPeriodsNumList("BJ").get(0).getKey();
			} else if ("CQSSC".equals(areaLottery) && !periodsNumType.equals(areaLottery)) {
				periodsNum = getPeriodsNumList("CQ").get(0).getKey();
			} else if ("GDKLSF".equals(areaLottery) && !periodsNumType.equals(areaLottery)) {
				periodsNum = getPeriodsNumList("GD").get(0).getKey();
			} else if (Constant.LOTTERY_TYPE_K3.equals(areaLottery) && !periodsNumType.equals(areaLottery)) {
				periodsNum = getPeriodsNumList(Constant.LOTTERY_TYPE_K3).get(0).getKey();
			} else if (Constant.LOTTERY_TYPE_NC.equals(areaLottery) && !periodsNumType.equals(areaLottery)) {
				periodsNum = getPeriodsNumList(Constant.LOTTERY_TYPE_NC).get(0).getKey();
			}
		}

		filtersPeriodInfo.add(Restrictions.ilike("periodsNum", periodsNum, MatchMode.ANYWHERE));
		if (null != playType && !"".equals(playType)) {
			if (playType.equals("BJ_DOUBLESIDE_DS")) {
				filtersPeriodInfo.add(Restrictions.or(Restrictions.eq("oddsType", "BJ_DOUBLESIDE_S"),
				        Restrictions.eq("oddsType", "BJ_DOUBLESIDE_DAN")));
				filtersPeriodInfo.add(Restrictions.eq("oddsType", "BJ_DOUBLESIDE_DAN"));
			} else if (playType.equals("BJ_DOUBLESIDE_DX")) {
				filtersPeriodInfo.add(Restrictions.or(Restrictions.eq("oddsType", "BJ_DOUBLESIDE_X"),
				        Restrictions.eq("oddsType", "BJ_DOUBLESIDE_DA")));
			} else {

				filtersPeriodInfo.add(Restrictions.ilike("oddsType", playType, MatchMode.ANYWHERE));
			}
		}
		filtersPeriodInfo.add(Restrictions.ilike("playTypeCode", areaLottery, MatchMode.ANYWHERE));
		Page<ShopsPlayOddsLog> page = new Page<ShopsPlayOddsLog>(10);
		int pageNo = 1;
		if (this.getRequest().getParameter("pageNo") != null)
			pageNo = this.findParamInt("pageNo");
		page.setPageNo(pageNo);
		page.setOrderBy("realUpdateDateNew");
		page.setOrder("desc");
		try {
			page = shopsPlayOddsLogLogic.queryLogByPage(page,
			        filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
			this.getRequest().setAttribute("page", page);

		} catch (Exception e) {
			log.error("<--分頁 查詢異常：viewMain-->", e);
			return "exception";
		}

		this.getRequest().setAttribute("areaLottery", areaLottery);
		this.getRequest().setAttribute("playTypeMap", playTypeMap);
		this.getRequest().setAttribute("periodsNumList", periodsNumList);
		return SUCCESS;
	}

	private List<SelectBox> getPeriodsNumList(String type) throws IllegalArgumentException {

		List<GDPeriodsInfo> listGD = periodsInfoLogic.queryBeforeRunPeriodsNumList(50);
		List<CQPeriodsInfo> listCQ = icqPeriodsInfoLogic.queryBeforeRunPeriodsNumList(50);
		List<BJSCPeriodsInfo> listBJ = bjscPeriodsInfoLogic.queryBeforeRunPeriodsNumList(50);
		List<JSSBPeriodsInfo> listK3 = jssbPeriodsInfoLogic.queryBeforeRunPeriodsNumList(50);
		List<NCPeriodsInfo> listNC = ncPeriodsInfoLogic.queryBeforeRunPeriodsNumList(50);

		List<SelectBox> listPeriods = new ArrayList<SelectBox>();
		if ("GD".equals(type)) {
			for (GDPeriodsInfo v : listGD) {
				listPeriods.add(new SelectBox(v.getPeriodsNum(), v.getPeriodsNum() + " 期"));
			}
		} else if ("CQ".equals(type)) {
			for (CQPeriodsInfo v : listCQ) {
				listPeriods.add(new SelectBox(v.getPeriodsNum(), v.getPeriodsNum() + " 期"));
			}
		} else if ("BJ".equals(type)) {
			for (BJSCPeriodsInfo v : listBJ) {
				listPeriods.add(new SelectBox(v.getPeriodsNum(), v.getPeriodsNum() + " 期"));
			}
		} else if (Constant.LOTTERY_TYPE_K3.equals(type)) {
			for (JSSBPeriodsInfo v : listK3) {
				listPeriods.add(new SelectBox(v.getPeriodsNum(), v.getPeriodsNum() + " 期"));
			}
		} else if (Constant.LOTTERY_TYPE_NC.equals(type)) {
			for (NCPeriodsInfo v : listNC) {
				listPeriods.add(new SelectBox(v.getPeriodsNum(), v.getPeriodsNum() + " 期"));
			}
		}
		return listPeriods;
	}

	public IShopsPlayOddsLogLogic getShopsPlayOddsLogLogic() {
		return shopsPlayOddsLogLogic;
	}

	public void setShopsPlayOddsLogLogic(IShopsPlayOddsLogLogic shopsPlayOddsLogLogic) {
		this.shopsPlayOddsLogLogic = shopsPlayOddsLogLogic;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPeriodsNum() {
		return periodsNum;
	}

	public void setPeriodsNum(String periodsNum) {
		this.periodsNum = periodsNum;
	}

	public String getPeriodsNumType() {
		return periodsNumType;
	}

	public void setPeriodsNumType(String periodsNumType) {
		this.periodsNumType = periodsNumType;
	}

	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
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

	public IBJSCPeriodsInfoLogic getBjscPeriodsInfoLogic() {
		return bjscPeriodsInfoLogic;
	}

	public void setBjscPeriodsInfoLogic(IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic) {
		this.bjscPeriodsInfoLogic = bjscPeriodsInfoLogic;
	}

	public IJSSBPeriodsInfoLogic getJssbPeriodsInfoLogic() {
		return jssbPeriodsInfoLogic;
	}

	public void setJssbPeriodsInfoLogic(IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic) {
		this.jssbPeriodsInfoLogic = jssbPeriodsInfoLogic;
	}

	public INCPeriodsInfoLogic getNcPeriodsInfoLogic() {
		return ncPeriodsInfoLogic;
	}

	public void setNcPeriodsInfoLogic(INCPeriodsInfoLogic ncPeriodsInfoLogic) {
		this.ncPeriodsInfoLogic = ncPeriodsInfoLogic;
	}

}
