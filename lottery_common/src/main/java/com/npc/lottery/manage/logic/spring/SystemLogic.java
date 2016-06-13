/**
 * 
 */
package com.npc.lottery.manage.logic.spring;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.CollectionUtils;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.ConstantType;
import com.npc.lottery.manage.dao.interf.ISystemDao;
import com.npc.lottery.manage.entity.PeriodAutoOdds;
import com.npc.lottery.manage.logic.interf.ISystemLogic;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.logic.interf.IPlayTypeLogic;
import com.npc.lottery.monitoring.logic.interf.IMonitoringInfoLogic;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.INCPeriodsInfoLogic;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.replenish.vo.ZhanDangVO;
import com.npc.lottery.rule.BJSCRule;
import com.npc.lottery.rule.CQSSCBallRule;
import com.npc.lottery.rule.GDKLSFRule;
import com.npc.lottery.rule.NCRule;
import com.npc.lottery.util.PlayTypeUtils;

/**
 * @author Administrator
 *
 */
public class SystemLogic implements ISystemLogic {

	private ISystemDao systemDao;
	private static Logger log = Logger.getLogger(SystemLogic.class);
	private IGDPeriodsInfoLogic periodsInfoLogic;
	private ICQPeriodsInfoLogic icqPeriodsInfoLogic;
	private IShopOddsLogic shopOddsLogic;
	private IPlayTypeLogic playTypeLogic;
	private String shopsCode;
	private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
	private IReplenishLogic replenishLogic;
	private INCPeriodsInfoLogic ncPeriodsInfoLogic;
	private TaskExecutor wcpTaskExecutor;// 异步执行

	@Override
	public List<PeriodAutoOdds> queryAutoOddsByTypeName(String type, String name) {
		return systemDao.find("from PeriodAutoOdds where type=? and name=? ", type, name);
	}

	public PeriodAutoOdds queryAutoOddsByTypeName(String type, String name, String shopsCode) {
		return systemDao.findUnique("from PeriodAutoOdds where type=? and name=? and shopCode=?", type, name, shopsCode);
	}

	@Override
	public void saveAutoOdds(PeriodAutoOdds autoOdds) throws SQLException {
		systemDao.save(autoOdds);
	}

	@Override
	public List<PeriodAutoOdds> findAutoOddsByType(String type, String shopCode) {
		String hql = "from PeriodAutoOdds where type=? and shopCode=?";
		return systemDao.find(hql, type, shopCode);
	}

	@Override
	public String findPeriodState(String lotteryType, String shopCode) {
		String type = "";
		String state = Constant.OPEN;
		if (Constant.LOTTERY_TYPE_BJ.equals(lotteryType)) {
			type = "BJSC_PERIODSTATE";
		} else if (Constant.LOTTERY_TYPE_K3.equals(lotteryType)) {
			type = "K3_PERIODSTATE";
		} else if (Constant.LOTTERY_TYPE_NC.equals(lotteryType)) {
			type = "NC_PERIODSTATE";
		}
		String hql = "from PeriodAutoOdds where type=? and shopCode=?";
		List<PeriodAutoOdds> list = systemDao.find(hql, type, shopCode);
		if (list != null && list.size() > 0) {
			PeriodAutoOdds p = list.get(0);
			Integer i = p.getAutoOdds().intValue();
			if (i == 0) {
				state = Constant.OPEN;
			} else {
				state = Constant.CLOSE;
			}
		}
		return state;
	}

	@Override
	public List<PeriodAutoOdds> findAutoOddsByShopCode(String shopCode) {
		String hql = "from PeriodAutoOdds where shopCode=?";

		return systemDao.find(hql, shopCode);
	}

	@Override
	public String checkAutoOddsByShopCode(String shopCode, String checkCode) {
		String result = "";
		List<PeriodAutoOdds> oddsList = this.findAutoOddsByShopCode(shopCode);
		Map<String, String> autoOddsMap = new HashMap<String, String>();
		for (int i = 0; i < oddsList.size(); i++) {
			PeriodAutoOdds ent = oddsList.get(i);
			autoOddsMap.put(ent.getType() + "/" + ent.getName(), ent.getAutoOdds().toString());
		}
		if (autoOddsMap.get(checkCode) != null) {
			result = autoOddsMap.get(checkCode);
		}
		return result;
	}

	public ISystemDao getSystemDao() {
		return systemDao;
	}

	public void setSystemDao(ISystemDao systemDao) {
		this.systemDao = systemDao;
	}

	@Override
	public void updateGDAutoOdds(PeriodAutoOdds periodAutoOdds, Map<String, String> initMap) {
		try {
			long startTime = System.currentTimeMillis();
			log.info("<----廣東开始兩面盤自動降賠 处理商铺 --shopCode--：" + periodAutoOdds.getShopCode());
			updateOddsDOUBLESIDE(periodAutoOdds, initMap, Constant.GDKLSF_DOUBLESIDE, Constant.LOTTERY_TYPE_GDKLSF);
			log.info("<----廣東结束兩面盤自動降賠 处理商铺 --shopCode--：" + periodAutoOdds.getShopCode() + "商铺--" + "时间:" + (System.currentTimeMillis() - startTime) + "MS");
		} catch (Exception e) {
			log.error("<--廣東 兩面盤自動降賠 設置異常：SystemConfigAction.GDDSAutoOdds-->", e);
		}

		log.info("<--end 自动降赔 GD-->");
	}

	@Override
	public void updateGDYLOdds(PeriodAutoOdds periodAutoOdds, Map<String, ZhanDangVO> initMap) {
		// 对所有已经设置遗漏的商铺进行操作
		try {
			long startTime = System.currentTimeMillis();
			log.info("<----廣東开始兩面盤YL降賠 处理商铺 --shopCode--：" + periodAutoOdds.getShopCode());
			updateOddsYILOU(periodAutoOdds, initMap, Constant.GDKLSF_YILOU, Constant.LOTTERY_TYPE_GDKLSF);
			log.info("<----廣東结束兩面盤YL降賠 处理商铺 --shopCode--：" + periodAutoOdds.getShopCode() + "商铺--" + (System.currentTimeMillis() - startTime) + "MS");
		} catch (Exception e) {
			log.error("<--廣東 遺漏自動降賠 設置異常:SystemConfigAction.GDYLAutoOdds-->", e);
		}
		log.info("<--end YL降赔 GD-->");
	}

	@Override
	public void updateNCAutoOdds(PeriodAutoOdds periodAutoOdds, Map<String, String> initMap) {
		try {
			long startTime = System.currentTimeMillis();
			log.info("<----农场开始兩面盤自動降賠 处理商铺 --shopCode--：" + periodAutoOdds.getShopCode());
			updateOddsDOUBLESIDE(periodAutoOdds, initMap, Constant.LOTTERY_NC_SUBTYPE_DOUBLESIDE, Constant.LOTTERY_TYPE_NC);
			log.info("<----农场结束兩面盤自動降賠 处理商铺 --shopCode--：" + periodAutoOdds.getShopCode() + "商铺--" + "时间:" + (System.currentTimeMillis() - startTime) + "MS");
		} catch (Exception e) {
			log.error("<--幸运农场 兩面盤自動降賠 設置異常：SystemConfigAction.NCDSAutoOdds-->", e);
		}
		log.info("<--end 自动降赔 NC-->");
	}

	@Override
	public void updateNCYLOdds(PeriodAutoOdds periodAutoOdds, Map<String, ZhanDangVO> initMap) {
		// 对所有已经设置遗漏的商铺进行操作
		try {
			long startTime = System.currentTimeMillis();
			log.info("<----农场开始兩面盤YL降賠 处理商铺 --shopCode--：" + periodAutoOdds.getShopCode());
			updateOddsYILOU(periodAutoOdds, initMap, Constant.NC_YILOU, Constant.LOTTERY_TYPE_NC);
			log.info("<----农场结束兩面盤YL降賠 处理商铺 --shopCode--：" + periodAutoOdds.getShopCode() + "商铺--" + (System.currentTimeMillis() - startTime) + "MS");
		} catch (Exception e) {
			log.error("<--幸运农场 遺漏自動降賠 設置異常:SystemConfigAction.NCYLAutoOdds-->", e);
		}
		log.info("<--end YL降赔 GD-->");
	}

	@Override
	public void updateCQAutoOdds(PeriodAutoOdds periodAutoOdds, Map<String, String> initMap) {
		try {
			long startTime = System.currentTimeMillis();
			log.info("<----重慶 开始兩面盤自動降賠 --处理商铺shopCode--：" + periodAutoOdds.getShopCode());
			updateOddsDOUBLESIDE(periodAutoOdds, initMap, Constant.CQSSC_DOUBLESIDE, Constant.LOTTERY_TYPE_CQSSC);
			log.info("<----重慶 结束兩面盤自動降賠 --处理商铺shopCode--：" + periodAutoOdds.getShopCode() + "时间:" + (System.currentTimeMillis() - startTime) / 1000 + "S");
		} catch (Exception e) {
			log.error("<--重慶 兩面盤自動降賠 設置異常：SystemConfigAction.CQDSAutoOdds-->", e);
		}
		log.info("<--end 自动降赔 CQ-->");
	}

	@Override
	public void updateBJAutoOdds(PeriodAutoOdds periodAutoOdds, Map<String, String> map) {
		final long startTime = System.currentTimeMillis();
		try {
			log.info("<----北京兩面盤自動降賠 --shopCode--：" + periodAutoOdds.getShopCode());
			updateOddsDOUBLESIDE(periodAutoOdds, map, Constant.BJSC_DOUBLESIDE, Constant.LOTTERY_TYPE_BJ);
			log.info("<----北京兩面盤自動降賠 --结束处理" + periodAutoOdds.getShopCode() + "商铺--" + "时间:" + (System.currentTimeMillis() - startTime) + "MS");
		} catch (Exception e) {
			log.error("<--北京 兩面盤自動降賠異常：SystemConfigAction.BJDSAutoOdds-->", e);
		}

		log.info("<--end 自动降赔 BJ-->");
	}

	@Override
	public Map<String, String> initCQAutoOdds() {
		long startTime = System.currentTimeMillis();
		log.info("<--重慶 兩面盤自動降賠 設置 start-->");
		// 不針對 某個店鋪
		List<CQPeriodsInfo> cqPeriodList = icqPeriodsInfoLogic.queryLastPeriodsForRefer();

		List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		filtersPlayType.add(Restrictions.ilike("typeCode", "CQSSC_DOUBLESIDE", MatchMode.ANYWHERE));
		List<PlayType> playTypeList = playTypeLogic.findPlayType(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));
		List<Integer> winNums = null;

		Map<String, String> map = new HashMap<String, String>();
		for (PlayType playTypeInfo : playTypeList) {
			CQPeriodsInfo gdInfo = null;
			for (int i = 0; i < cqPeriodList.size(); i++) {
				gdInfo = cqPeriodList.get(i);
				winNums = new ArrayList<Integer>();
				winNums.add(gdInfo.getResult1());
				winNums.add(gdInfo.getResult2());
				winNums.add(gdInfo.getResult3());
				winNums.add(gdInfo.getResult4());
				winNums.add(gdInfo.getResult5());

				String typeWinStr = map.get(playTypeInfo.getTypeCode());
				boolean betResult = CQSSCBallRule.getBallBetResult(playTypeInfo, winNums);
				String winBooleanStr = "0";
				if (betResult)
					winBooleanStr = "1"; // 連出
				if (typeWinStr == null) {
					typeWinStr = winBooleanStr;
				} else {
					typeWinStr = typeWinStr + winBooleanStr;
				}
				if (!"CQSSC_DOUBLESIDE_HE".equals(playTypeInfo.getTypeCode())) {
					map.put(playTypeInfo.getTypeCode(), typeWinStr);
				}

			}
		}
		log.info("<--重慶 兩面盤自動降賠 設置 End-->" + (System.currentTimeMillis() - startTime) / 1000 + "s");
		return map;
	}

	/**
	 * 根據type 類型更新雙面盤賠率
	 * 
	 * @param pO查詢出開關
	 *            狀態
	 * @param map
	 *            key:TypeCode value:0101010011存儲開獎結果（中，不中）
	 * @param option
	 *            操作類型
	 * @throws Exception
	 */
	private void updateOddsDOUBLESIDE(PeriodAutoOdds pO, Map<String, String> map, String option, String playType) throws Exception {
		List<PeriodAutoOdds> autoOddsList = this.findAutoOddsByType(option, pO.getShopCode());
		Map<String, PeriodAutoOdds> autoOddsMap = new HashMap<String, PeriodAutoOdds>();
		Map<String, BigDecimal> playOddsMap = new HashMap<String, BigDecimal>(); // 降同路，如果玩法降了，同路則將；如果玩法沒將同路不將
		List<ShopsPlayOdds> shopsPlayOddsList = new ArrayList<ShopsPlayOdds>();
		for (PeriodAutoOdds o : autoOddsList) {
			autoOddsMap.put(o.getName(), o);
		}
		// 0為啟用 1 為禁用
		if (null != pO && Integer.valueOf(pO.getAutoOdds().toString()) == 0) {
			long startTime = System.currentTimeMillis();
			Map<String, ShopsPlayOdds> shopPlayOddsMap = shopOddsLogic.getCurrentShopRealOddsAndId(pO.getShopCode(), playType);
			for (Map.Entry<String, String> entry : map.entrySet()) {
				PeriodAutoOdds periodAutoOdds = null;
				int count = getMaxLength(entry.getValue());
				if (count < 0) {
					periodAutoOdds = autoOddsMap.get("N_" + Math.abs(count));
				} else if (count > 0) {
					periodAutoOdds = autoOddsMap.get("L_" + Math.abs(count));
				}

				// p==null ==0說明 沒有設置自動將賠 不需要修改賠率
				if (null == periodAutoOdds || periodAutoOdds.getAutoOdds().compareTo(BigDecimal.ZERO) == 0) {
					continue;
				} else {
					ShopsPlayOdds shopsPlayOdds = shopPlayOddsMap.get(entry.getKey());
					if (null != shopsPlayOdds) {
						BigDecimal orginOdds = shopsPlayOdds.getRealOdds();
						BigDecimal changeOdds = orginOdds.subtract(periodAutoOdds.getAutoOdds());
						// 如果更新后的赔率少于0,则更新为0
						changeOdds = (changeOdds.compareTo(BigDecimal.ZERO) == -1) ? BigDecimal.ZERO : changeOdds;
						shopsPlayOdds.setRealOdds(changeOdds);
						shopsPlayOddsList.add(shopsPlayOdds);
						playOddsMap.put(shopsPlayOdds.getPlayTypeCode(), periodAutoOdds.getAutoOdds());
						log.info("-------处理商铺(" + pO.getShopCode() + ")" + option + "自动降赔------(" + count + ")typeCode:" + shopsPlayOdds.getPlayTypeCode() + "原来赔率:" + orginOdds + "更新后赔率:"
						        + changeOdds);

					}
				}
			}
			log.info("完成" + pO.getType() + "自动降赔" + pO.getShopCode() + "赔率初始化操作:" + (System.currentTimeMillis() - startTime) + "MS");

			if (!CollectionUtils.isEmpty(shopsPlayOddsList)) {
				long startTime2 = System.currentTimeMillis();
				shopOddsLogic.updateRealOddsBatchById(shopsPlayOddsList);
				log.info("完成" + pO.getType() + "自动降赔" + pO.getShopCode() + "赔率update操作:" + (System.currentTimeMillis() - startTime2) + "MS");
			}

			// 設置同路
			updateOddsDOUBLESIDETL(playOddsMap, option, pO.getShopCode(), shopPlayOddsMap);

		}
	}

	// 廣東 重慶、 雙面盤的同路
	private void updateOddsDOUBLESIDETL(Map<String, BigDecimal> playOddsMap, String option, String shopCode, Map<String, ShopsPlayOdds> shopPlayOddsMap) throws Exception {
		long startTime = System.currentTimeMillis();
		List<ShopsPlayOdds> shopsPlayOddsList = new ArrayList<ShopsPlayOdds>();
		// 查找DB
		PeriodAutoOdds peroddsInfo = this.queryAutoOddsByTypeName(option, "ODDSTL_DS", shopCode);
		// 同路
		if (null != peroddsInfo && peroddsInfo.getAutoOdds().compareTo(BigDecimal.ZERO) == 1) {
			for (Map.Entry<String, BigDecimal> entryInfo : playOddsMap.entrySet()) {

				// 主要用 sub_type,final_type
				PlayType playType = PlayTypeUtils.getPlayType(entryInfo.getKey());
				// 取出對應規則對應的好球 同路 下降的賠率=(同路值%*當前賠率=0.04),
				List<Integer> list = null;
				if (Constant.GDKLSF_DOUBLESIDE.equals(option)) {
					list = GDKLSFRule.getMatchList(playType.getPlayFinalType());
				} else if (Constant.CQSSC_DOUBLESIDE.equals(option)) {
					list = CQSSCBallRule.getMatchList(playType.getPlayFinalType());
				} else if (Constant.BJSC_DOUBLESIDE.equals(option)) {
					list = BJSCRule.getMatchList(playType.getPlayFinalType());
				} else if (Constant.NC_DOUBLESIDE.equals(option)) {
					list = NCRule.getMatchList(playType.getPlayFinalType());
				}
				BigDecimal odds = peroddsInfo.getAutoOdds().multiply(entryInfo.getValue());
				for (Integer i : list) {
					if (null != playType.getPlaySubType())// 重慶 虎、龍..對應為空
					{
						String playTypeCode = playType.getPlayType() + "_" + playType.getPlaySubType() + "_" + i;
						ShopsPlayOdds shopsPlayOdds = shopPlayOddsMap.get(playTypeCode);
						if (null != shopsPlayOdds) {
							BigDecimal orginOdds = shopsPlayOdds.getRealOdds();
							BigDecimal changeOdds = orginOdds.subtract(odds);
							// 如果更新后的赔率少于0,则更新为0
							changeOdds = (changeOdds.compareTo(BigDecimal.ZERO) == -1) ? BigDecimal.ZERO : changeOdds;
							// 修改对路赔率
							shopsPlayOdds.setRealOdds(changeOdds);
							// 保存待处理update操作的赔率对象到list
							shopsPlayOddsList.add(shopsPlayOdds);
							log.info("-------处理商铺(" + shopCode + ")" + option + "同路降赔------typeCode:" + shopsPlayOdds.getPlayTypeCode() + "原来赔率:" + orginOdds + "更新后赔率:" + changeOdds);
						}

					}
				}
			}
			log.info("完成" + peroddsInfo.getType() + "同路降赔" + peroddsInfo.getShopCode() + "赔率初始化操作:" + (System.currentTimeMillis() - startTime) + "MS");
			if (!CollectionUtils.isEmpty(shopsPlayOddsList)) {
				long startTime2 = System.currentTimeMillis();
				shopOddsLogic.updateRealOddsBatchById(shopsPlayOddsList);
				log.info("完成" + peroddsInfo.getType() + "同路降赔" + peroddsInfo.getShopCode() + "赔率update操作:" + (System.currentTimeMillis() - startTime2) + "MS");
			}

		}
	}

	@Override
	public Map<String, String> BjAutoInit() {
		long startTime = System.currentTimeMillis();
		log.info("<--北京 兩面盤自動降賠 設置 start-->");
		// 不針對 某個店鋪
		List<BJSCPeriodsInfo> bjPeriodList = bjscPeriodsInfoLogic.queryLastPeriodsForRefer();

		List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		filtersPlayType.add(Restrictions.ilike("typeCode", "BJ_DOUBLESIDE", MatchMode.ANYWHERE));
		List<PlayType> playTypeList = playTypeLogic.findPlayType(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));

		// type:0101010111
		Map<String, String> map = new HashMap<String, String>();
		for (PlayType playTypeInfo : playTypeList) {
			for (int i = 0; i < bjPeriodList.size(); i++) {
				BJSCPeriodsInfo gdInfo = bjPeriodList.get(i);
				List<Integer> winNums = new ArrayList<Integer>();
				winNums.add(gdInfo.getResult1());
				winNums.add(gdInfo.getResult2());
				winNums.add(gdInfo.getResult3());
				winNums.add(gdInfo.getResult4());
				winNums.add(gdInfo.getResult5());
				winNums.add(gdInfo.getResult6());
				winNums.add(gdInfo.getResult7());
				winNums.add(gdInfo.getResult8());
				winNums.add(gdInfo.getResult9());
				winNums.add(gdInfo.getResult10());

				String typeWinStr = map.get(playTypeInfo.getTypeCode());
				boolean betResult = BJSCRule.getBetResult(playTypeInfo, winNums);
				String winBooleanStr = "0";
				if (betResult) {
					winBooleanStr = "1"; // 連出
				}
				if (typeWinStr == null) {
					typeWinStr = winBooleanStr;
				} else {
					typeWinStr = typeWinStr + winBooleanStr;
				}
				map.put(playTypeInfo.getTypeCode(), typeWinStr);

			}
		}
		log.info("<--北京 兩面盤自動降賠 設置 end-->" + (System.currentTimeMillis() - startTime) / 1000 + "S");
		return map;
	}

	@Override
	public Map<String, String> initGDAutoOdds() {

		log.info("<--廣東 兩面盤自動降賠 設置 start-->");
		List<GDPeriodsInfo> gdPeriodList = periodsInfoLogic.queryLastPeriodsForRefer();

		List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		filtersPlayType.add(Restrictions.ilike("typeCode", "GDKLSF_DOUBLESIDE", MatchMode.ANYWHERE));
		List<PlayType> playTypeList = playTypeLogic.findPlayType(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));
		List<Integer> winNums = null;

		// type:0101010111
		Map<String, String> map = new HashMap<String, String>();
		for (PlayType playTypeInfo : playTypeList) {
			GDPeriodsInfo gdInfo = null;
			for (int i = 0; i < gdPeriodList.size(); i++) {
				gdInfo = gdPeriodList.get(i);
				winNums = new ArrayList<Integer>();
				winNums.add(gdInfo.getResult1());
				winNums.add(gdInfo.getResult2());
				winNums.add(gdInfo.getResult3());
				winNums.add(gdInfo.getResult4());
				winNums.add(gdInfo.getResult5());
				winNums.add(gdInfo.getResult6());
				winNums.add(gdInfo.getResult7());
				winNums.add(gdInfo.getResult8());

				String typeWinStr = map.get(playTypeInfo.getTypeCode());
				boolean betResult = GDKLSFRule.getBetResult(playTypeInfo, winNums);
				String winBooleanStr = "0";
				if (betResult)
					winBooleanStr = "1"; // 連出
				if (typeWinStr == null) {
					typeWinStr = winBooleanStr;
				} else {
					typeWinStr = typeWinStr + winBooleanStr;
				}
				map.put(playTypeInfo.getTypeCode(), typeWinStr);

			}
		}
		return map;
	}

	@Override
	public Map<String, String> initNCAutoOdds() {
		log.info("<--农场 兩面盤自動降賠 設置 start-->");
		// 不針對 某個店鋪
		List<NCPeriodsInfo> ncPeriodList = ncPeriodsInfoLogic.queryLastPeriodsForRefer();

		List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		filtersPlayType.add(Restrictions.ilike("typeCode", "NC_DOUBLESIDE", MatchMode.ANYWHERE));
		List<PlayType> playTypeList = playTypeLogic.findPlayType(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));
		List<Integer> winNums = null;

		// type:0101010111
		Map<String, String> map = new HashMap<String, String>();
		for (PlayType playTypeInfo : playTypeList) {
			NCPeriodsInfo ncInfo = null;
			for (int i = 0; i < ncPeriodList.size(); i++) {
				ncInfo = ncPeriodList.get(i);
				winNums = new ArrayList<Integer>();
				winNums.add(ncInfo.getResult1());
				winNums.add(ncInfo.getResult2());
				winNums.add(ncInfo.getResult3());
				winNums.add(ncInfo.getResult4());
				winNums.add(ncInfo.getResult5());
				winNums.add(ncInfo.getResult6());
				winNums.add(ncInfo.getResult7());
				winNums.add(ncInfo.getResult8());

				String typeWinStr = map.get(playTypeInfo.getTypeCode());
				boolean betResult = NCRule.getBetResult(playTypeInfo, winNums);
				String winBooleanStr = "0";
				if (betResult)
					winBooleanStr = "1"; // 連出
				if (typeWinStr == null) {
					typeWinStr = winBooleanStr;
				} else {
					typeWinStr = typeWinStr + winBooleanStr;
				}
				map.put(playTypeInfo.getTypeCode(), typeWinStr);

			}
		}
		return map;
	}

	/**
	 * 根據type 類型更新賠率
	 * 
	 * @param pO查詢出開關
	 *            狀態
	 * @param map
	 *            key:TypeCode value:0101010011存儲開獎結果（中，不中）
	 * @param option
	 *            操作類型
	 * @throws Exception
	 */
	private void updateOddsYILOU(PeriodAutoOdds pO, Map<String, ZhanDangVO> map, String option, String playType) throws Exception {
		List<ShopsPlayOdds> shopsPlayOddsList = new ArrayList<ShopsPlayOdds>();
		List<PeriodAutoOdds> autoOddsList = this.findAutoOddsByType(option, pO.getShopCode());
		Map<String, PeriodAutoOdds> autoOddsMap = new HashMap<String, PeriodAutoOdds>();
		for (PeriodAutoOdds o : autoOddsList) {
			autoOddsMap.put(o.getName(), o);
		}

		// 0為啟用 1 為禁用
		if (null != pO && Integer.valueOf(pO.getAutoOdds().toString()) == 0) {
			long startTime = System.currentTimeMillis();
			// 获取商铺赔率列表
			Map<String, ShopsPlayOdds> shopPlayOddsMap = shopOddsLogic.getCurrentShopRealOddsAndId(pO.getShopCode(), playType);
			for (Map.Entry<String, ZhanDangVO> entry : map.entrySet()) {
				int countN = entry.getValue().getYLsum() > 25 ? 25 : entry.getValue().getYLsum();
				PeriodAutoOdds periodAutoOdds = autoOddsMap.get("N_" + countN);
				// p==null ==0說明 沒有設置自動將賠 不需要修改賠率
				if ((null == periodAutoOdds || periodAutoOdds.getAutoOdds().compareTo(BigDecimal.ZERO) == 0)) {
					continue;
				} else {
					for (Map.Entry<String, String> entryGD : ConstantType.GD_MAP.entrySet()) {
						String typeCode = "";
						if (option.equals(Constant.GDKLSF_YILOU)) {
							typeCode = "GDKLSF_BALL_" + entryGD.getValue() + "_" + entry.getKey();
						} else if (option.equals(Constant.NC_YILOU)) {
							typeCode = "NC_BALL_" + entryGD.getValue() + "_" + entry.getKey();
						}

						if (StringUtils.isNotEmpty(typeCode)) {
							ShopsPlayOdds shopsPlayOdds = shopPlayOddsMap.get(typeCode);
							if (null != shopsPlayOdds) {
								BigDecimal orginOdds = shopsPlayOdds.getRealOdds();
								BigDecimal changeOdds = orginOdds.subtract(periodAutoOdds.getAutoOdds());
								// 如果更新后的赔率少于0,则更新为0
								changeOdds = (changeOdds.compareTo(BigDecimal.ZERO) == -1) ? BigDecimal.ZERO : changeOdds;
								shopsPlayOdds.setRealOdds(changeOdds);
								shopsPlayOddsList.add(shopsPlayOdds);
								log.info("-------处理商铺(" + pO.getShopCode() + ")" + option + "遗漏------(" + countN + ")typeCode:" + typeCode + "原来赔率:" + orginOdds + "更新后赔率:" + changeOdds);
							}
						}
					}
				}

			}

			log.info("完成" + pO.getType() + "遗漏" + pO.getShopCode() + "赔率初始化操作:" + (System.currentTimeMillis() - startTime) + "MS");

			if (!CollectionUtils.isEmpty(shopsPlayOddsList)) {
				long startTime2 = System.currentTimeMillis();
				shopOddsLogic.updateRealOddsBatchById(shopsPlayOddsList);
				log.info("完成" + pO.getType() + "遗漏" + pO.getShopCode() + "赔率update操作:" + (System.currentTimeMillis() - startTime2) + "MS");
			}
		}
	}

	@Override
	public Map<String, ZhanDangVO> initGDYL() {
		log.info("<--廣東 遺漏自動降賠 設置 start-->");
		Map<String, ZhanDangVO> mapYL = new HashMap<String, ZhanDangVO>();
		mapYL = replenishLogic.notAppearCnt(mapYL);// 统计广东遗漏
		return mapYL;
	}

	@Override
	public Map<String, ZhanDangVO> initNCYL() {
		log.info("<--农场 遺漏自動降賠 設置 start-->");
		Map<String, ZhanDangVO> mapYL = new HashMap<String, ZhanDangVO>();
		mapYL = replenishLogic.notAppearCntForNc(mapYL);// 统计农场遗漏
		return mapYL;
	}

	/**
	 * 判断连出或者连没出,连出为正数,连没出为负数
	 * 
	 * @param str
	 * @return
	 */
	private static int getMaxLength(final String str) {
		int k = 0;
		String first = str.substring(0, 1);
		if ("1".equals(first)) {// 连出
			k = str.indexOf("0");
			if (k == -1) {
				k = str.lastIndexOf("1") + 1;
			}
			if (k > 25) {
				k = 25;
			}

		} else if ("0".equals(first)) {// 连续没出
			k = str.indexOf("1");
			if (k == -1) {
				k = -(str.lastIndexOf("0") + 1);
			} else {
				k = -k;
			}
			if (k < -25) {
				k = -25;
			}
		}
		return k;
	}

	public ICQPeriodsInfoLogic getIcqPeriodsInfoLogic() {
		return icqPeriodsInfoLogic;
	}

	public void setIcqPeriodsInfoLogic(ICQPeriodsInfoLogic icqPeriodsInfoLogic) {
		this.icqPeriodsInfoLogic = icqPeriodsInfoLogic;
	}

	public IGDPeriodsInfoLogic getPeriodsInfoLogic() {
		return periodsInfoLogic;
	}

	public void setPeriodsInfoLogic(IGDPeriodsInfoLogic periodsInfoLogic) {
		this.periodsInfoLogic = periodsInfoLogic;
	}

	public IShopOddsLogic getShopOddsLogic() {
		return shopOddsLogic;
	}

	public void setShopOddsLogic(IShopOddsLogic shopOddsLogic) {
		this.shopOddsLogic = shopOddsLogic;
	}

	public IPlayTypeLogic getPlayTypeLogic() {
		return playTypeLogic;
	}

	public void setPlayTypeLogic(IPlayTypeLogic playTypeLogic) {
		this.playTypeLogic = playTypeLogic;
	}

	public String getShopsCode() {
		return shopsCode;
	}

	public void setShopsCode(String shopsCode) {
		this.shopsCode = shopsCode;
	}

	public IBJSCPeriodsInfoLogic getBjscPeriodsInfoLogic() {
		return bjscPeriodsInfoLogic;
	}

	public void setBjscPeriodsInfoLogic(IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic) {
		this.bjscPeriodsInfoLogic = bjscPeriodsInfoLogic;
	}

	public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}

	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}

	public INCPeriodsInfoLogic getNcPeriodsInfoLogic() {
		return ncPeriodsInfoLogic;
	}

	public void setNcPeriodsInfoLogic(INCPeriodsInfoLogic ncPeriodsInfoLogic) {
		this.ncPeriodsInfoLogic = ncPeriodsInfoLogic;
	}

	public TaskExecutor getWcpTaskExecutor() {
		return wcpTaskExecutor;
	}

	public void setWcpTaskExecutor(TaskExecutor wcpTaskExecutor) {
		this.wcpTaskExecutor = wcpTaskExecutor;
	}
	
}
