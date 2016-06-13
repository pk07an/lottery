package com.npc.lottery.periods.logic.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.member.logic.interf.ICheckLogic;
import com.npc.lottery.periods.dao.interf.INCPeriodsInfoDao;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.INCPeriodsInfoLogic;
import com.npc.lottery.util.DatetimeUtil;
import com.npc.lottery.util.Page;

public class NCPeriodsInfoLogic implements INCPeriodsInfoLogic {

	private Logger				logger	= Logger.getLogger(NCPeriodsInfoLogic.class);
	private INCPeriodsInfoDao	ncPeriodsInfoDao;
	
	private ShopSchemeService shopSchemeService;
	//private DataExtractor		dataExtractor;
	
	private ICheckLogic checkLogic;

	@Override
	public void saveNCPeriods(Date peridosDate) {
		this.saveNC1To13Periods(peridosDate);
		this.saveNC14To97Periods(peridosDate);
	}

	private void saveNC14To97Periods(Date peridosDate) {
		try {
			SimpleDateFormat ageFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat openFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String ageDate = ageFormat.format(peridosDate) + " 09:53:15";
			Date openDate = openFormat.parse(ageDate);
			Calendar sysCalender = Calendar.getInstance();
			Calendar sysCalenderStop = Calendar.getInstance(); // 封盘时间设置
			sysCalender.setTime(openDate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); // 盘期时间格式
			String dateString = formatter.format(peridosDate.getTime());
			int periodsCount = 97; // 期数
			int endPerDate = -120; // 提前封盘的时间
			for (int i = 14; i <= periodsCount; i++) {
				NCPeriodsInfo periodsInfo = new NCPeriodsInfo();
				StringBuffer periodsNum = new StringBuffer(); // 期数设置
				periodsNum.append(dateString).append(i);
				periodsInfo.setPeriodsNum(String.valueOf(periodsNum));
				sysCalender.add(Calendar.MINUTE, 10); // 在开盘的基础上加10分钟到开奖的时间点
				Date lotteryDate = (Date) sysCalender.getTime();
				sysCalender.add(Calendar.MINUTE, -10); // -10分钟
														// 在下轮开奖的基础上减去10分钟就是当前开盘时间
				Date resultDate = (Date) sysCalender.getTime();
				logger.info("开盘时间：" + openFormat.format(resultDate));
				periodsInfo.setOpenQuotTime(resultDate);
				periodsInfo.setLotteryTime(lotteryDate);
				sysCalenderStop.setTime(lotteryDate);
				sysCalenderStop.add(Calendar.SECOND, endPerDate); // 开奖时间减去120秒就是这一轮封盘时间
				Date stopDate = (Date) sysCalenderStop.getTime();
				logger.info("盘期" + periodsNum + "-" + "封盘时间" + openFormat.format(stopDate) + "-" + openFormat.format(lotteryDate) + "每一期开奖的时间");
				periodsInfo.setStopQuotTime(stopDate);
				periodsInfo.setResult1(0);
				periodsInfo.setResult2(0);
				periodsInfo.setResult3(0);
				periodsInfo.setResult4(0);
				periodsInfo.setResult5(0);
				periodsInfo.setResult6(0);
				periodsInfo.setResult7(0);
				periodsInfo.setResult8(0);
				periodsInfo.setState(Constant.NOT_STATUS);
				periodsInfo.setCreateTime(new Date());
				sysCalender.setTime(lotteryDate);
				ncPeriodsInfoDao.save(periodsInfo);
			}
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
	}

	private void saveNC1To13Periods(Date peridosDate) {
		try {
			SimpleDateFormat ageFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat openFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar dateCalender = Calendar.getInstance();
			dateCalender.setTime(peridosDate);
			dateCalender.add(Calendar.DATE, -1);
			String ageDate = ageFormat.format(dateCalender.getTime()) + " 23:53:15";
			Date openDate = openFormat.parse(ageDate);
			Calendar sysCalender = Calendar.getInstance();
			Calendar sysCalenderStop = Calendar.getInstance(); // 封盘时间设置
			sysCalender.setTime(openDate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); // 盘期时间格式

			String dateString = formatter.format(peridosDate);
			int periodsCount = 13; // 期数
			int endPerDate = -120; // 提前封盘的时间
			for (int i = 1; i <= periodsCount; i++) {
				NCPeriodsInfo periodsInfo = new NCPeriodsInfo();
				StringBuffer periodsNum = new StringBuffer(); // 期数设置
				if (i >= 10) {
					periodsNum.append(dateString).append(i);
				} else {
					periodsNum.append(dateString).append("0").append(i);
				}
				periodsInfo.setPeriodsNum(String.valueOf(periodsNum));
				sysCalender.add(Calendar.MINUTE, 10); // 在开盘的基础上加10分钟到开奖的时间点
				Date lotteryDate = (Date) sysCalender.getTime();
				if (i == 1) {
					periodsInfo.setOpenQuotTime(openDate);
				} else {
					sysCalender.add(Calendar.MINUTE, -10); // -10分钟
					// 在下轮开奖的基础上减去10分钟就是当前开盘时间
					Date resultDate = (Date) sysCalender.getTime();
					logger.info("开盘时间：" + openFormat.format(resultDate));
					periodsInfo.setOpenQuotTime(resultDate);
				}
				periodsInfo.setLotteryTime(lotteryDate);
				sysCalenderStop.setTime(lotteryDate);
				sysCalenderStop.add(Calendar.SECOND, endPerDate); // 开奖时间减去120秒就是这一轮封盘时间
				Date stopDate = (Date) sysCalenderStop.getTime();
				logger.info("盘期" + periodsNum + "-" + "封盘时间" + openFormat.format(stopDate) + "-" + openFormat.format(lotteryDate) + "每一期开奖的时间");
				periodsInfo.setStopQuotTime(stopDate);
				periodsInfo.setResult1(0);
				periodsInfo.setResult2(0);
				periodsInfo.setResult3(0);
				periodsInfo.setResult4(0);
				periodsInfo.setResult5(0);
				periodsInfo.setResult6(0);
				periodsInfo.setResult7(0);
				periodsInfo.setResult8(0);
				if (i == 13) {
					// 第13期 不允许投注
					periodsInfo.setState(Constant.STOP_STATUS);
				} else {
					periodsInfo.setState(Constant.NOT_STATUS);
				}
				periodsInfo.setCreateTime(new Date());
				sysCalender.setTime(lotteryDate);
				ncPeriodsInfoDao.save(periodsInfo);
			}
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
	}

	@Override
	public NCPeriodsInfo queryByPeriods(String periodsNum, Object number) {
		return ncPeriodsInfoDao.findUniqueBy(periodsNum, number);
	}

	@Override
	public Page<NCPeriodsInfo> findPage(Page<NCPeriodsInfo> page, Criterion[] criterions) {
		return ncPeriodsInfoDao.findPage(page, criterions);
	}

	@Override
	public void save(NCPeriodsInfo NCPeriodsInfo) {
		ncPeriodsInfoDao.save(NCPeriodsInfo);
	}

	@Override
	public List<NCPeriodsInfo> queryAllPeriods() {
		return ncPeriodsInfoDao.getAll();
	}

	public INCPeriodsInfoDao getPeriodsInfoDao() {
		return ncPeriodsInfoDao;
	}

	public void setPeriodsInfoDao(INCPeriodsInfoDao periodsInfoDao) {
		this.ncPeriodsInfoDao = periodsInfoDao;
	}

	@Override
	public List<NCPeriodsInfo> queryAllPeriods(Criterion... criterias) {
		return ncPeriodsInfoDao.find(criterias);
	}

	@Override
	public NCPeriodsInfo queryByPeriods(Criterion... criterias) {
		NCPeriodsInfo ncp = null;
		List<NCPeriodsInfo> periodsList = ncPeriodsInfoDao.find(criterias);
		if (periodsList != null && periodsList.size() != 0)
			ncp = periodsList.get(0);
		return ncp;
	}

	public NCPeriodsInfo queryByPeriodsStatus(String status) {
		NCPeriodsInfo cqp = null;
		String hql = "from NCPeriodsInfo where  state=? and stopQuotTime>=? and openQuotTime<=? order by openQuotTime desc ";
		List<NCPeriodsInfo> periodsList = ncPeriodsInfoDao.find(hql, status, new Date(), new Date());
		if (periodsList != null && periodsList.size() != 0)
			cqp = periodsList.get(0);
		return cqp;

	}

	public NCPeriodsInfo queryStopPeriods(String status) {
		NCPeriodsInfo cqp = null;
		String hql = "from NCPeriodsInfo where  state=? and lotteryTime>=? and openQuotTime<=? order by openQuotTime desc ";
		List<NCPeriodsInfo> periodsList = ncPeriodsInfoDao.find(hql, status, new Date(), new Date());
		if (periodsList != null && periodsList.size() != 0)
			cqp = periodsList.get(0);
		return cqp;

	}

	/**
	 * 改变NC状态值
	 */
	@Override
	public void updateCommon(String commonDate) {
		NCPeriodsInfo NCPeriodsInfo = new NCPeriodsInfo();
		NCPeriodsInfo = queryLastNeedOpenPeriods(Constant.NOT_STATUS);
		if (NCPeriodsInfo != null) {
			logger.info("幸运农场开盘 盘期号码：" + NCPeriodsInfo.getPeriodsNum());
			NCPeriodsInfo.setState(Constant.OPEN_STATUS);
			ncPeriodsInfoDao.save(NCPeriodsInfo);
		} else
			logger.info("幸运农场开盘无法拿到开盘盘起：");

	}

	@Override
	public void updateNCStatus() {

		try {
			NCPeriodsInfo NCPeriodsInfo = queryLastNeedStopPeriods(Constant.OPEN_STATUS);
			if (NCPeriodsInfo != null) {
				NCPeriodsInfo.setState(Constant.STOP_STATUS);
				logger.info("幸运农场封盘：盘期：" + NCPeriodsInfo.getPeriodsNum());
				ncPeriodsInfoDao.update(NCPeriodsInfo);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行" + this.getClass().getSimpleName() + "中的方法updateNCStatus时出现错误 " + e.getMessage());
		}
	}
/*
	@Override
	public void updateNCStatusWithCheckMiragation() {

		try {
			NCPeriodsInfo NCPeriodsInfo = queryLastNeedStopPeriods(Constant.OPEN_STATUS);
			if (NCPeriodsInfo != null) {
				NCPeriodsInfo.setState(Constant.STOP_STATUS);
				logger.info("幸运农场封盘：盘期：" + NCPeriodsInfo.getPeriodsNum());
				ncPeriodsInfoDao.update(NCPeriodsInfo);
				// 获取商铺对应的scheme列表
				Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();

				// 每一个商铺按每期进去兑奖
				for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
					String scheme = shopScheme.getValue();
					// 迁移投注数据
					checkLogic.miragationNCDataToCheck(NCPeriodsInfo.getPeriodsNum(), scheme);
				}
				// 迁移补货数据
			} else {
				logger.info("幸运农场封盘：找不到封盘的盘期");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行" + this.getClass().getSimpleName() + "中的方法updateNCStatus时出现错误 " + e.getMessage());
		}
	}*/

	private NCPeriodsInfo queryLastNeedStopPeriods(String status) {
		NCPeriodsInfo cdp = null;
		String hql = "from NCPeriodsInfo where  state=?  and lotteryTime >?   order by openQuotTime asc";
		Object[] parameter = new Object[] { status, new Date() };
		List<NCPeriodsInfo> cqpList = ncPeriodsInfoDao.find(hql, parameter);
		if (cqpList != null && cqpList.size() > 0)
			cdp = cqpList.get(0);
		return cdp;
	}

	@Override
	public List<NCPeriodsInfo> queryTodayPeriods() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// sdf.format();

		java.util.Date now = new java.util.Date();
		String nowStr = sdf.format(now);
		String before = nowStr + " 00:00:00";
		String after = nowStr + " 23:59:59";
		Date bef = java.sql.Timestamp.valueOf(before);
		Date af = java.sql.Timestamp.valueOf(after);

		String hql = "from NCPeriodsInfo where  lotteryTime>=? and lotteryTime<=? and state in (4,7) order by lotteryTime desc";
		Object[] parameter = new Object[] { bef, af };
		return ncPeriodsInfoDao.find(hql, parameter);
	}

	@Override
	public List<NCPeriodsInfo> queryTodayAllPeriods() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// sdf.format();

		java.util.Date now = new java.util.Date();
		String nowStr = sdf.format(now);
		String before = nowStr + " 00:00:00";
		String after = nowStr + " 23:59:59";
		Date bef = java.sql.Timestamp.valueOf(before);
		Date af = java.sql.Timestamp.valueOf(after);

		String hql = "from NCPeriodsInfo where  lotteryTime>=? and lotteryTime<=? order by lotteryTime desc";
		Object[] parameter = new Object[] { bef, af };
		return ncPeriodsInfoDao.find(hql, parameter);
	}

	@Override
	public List<NCPeriodsInfo> queryLastPeriodsForRefer() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String yes = DatetimeUtil.yesterday();
		java.util.Date now = new java.util.Date();
		String nowStr = sdf.format(now);
		String after = nowStr + " 23:59:59";
		String yesStr = yes + " 22:59:59";
		Date af = java.sql.Timestamp.valueOf(after);
		Date yestoday = java.sql.Timestamp.valueOf(yesStr);
		String hql = "from NCPeriodsInfo where  lotteryTime>=? and lotteryTime<=? and state in (4,7) order by lotteryTime desc";
		Object[] parameter = new Object[] { yestoday, af };

		Page<NCPeriodsInfo> page = new Page<NCPeriodsInfo>();
		page.setPageSize(100);
		page.setPageNo(1);
		Page<NCPeriodsInfo> pageResult = ncPeriodsInfoDao.findPage(page, hql, parameter);
		List<NCPeriodsInfo> periodsList = pageResult.getResult();

		// List<NCPeriodsInfo> periodsList=periodsInfoDao.find(hql, parameter);
		// if(periodsList.size()>30)
		// periodsList=periodsList.subList(0, 30);
		return periodsList;

	}

	public Page<NCPeriodsInfo> queryHistoryPeriods(Page<NCPeriodsInfo> page) {
		String hql = "from NCPeriodsInfo where  state in (4,6,7) order by lotteryTime desc";
		// String hql="from NCPeriodsInfo where  state not in (1,2) order by lotteryTime desc";
		Object[] parameter = new Object[] {};
		return ncPeriodsInfoDao.findPage(page, hql, parameter);
	}

	public Page<NCPeriodsInfo> queryHistoryPeriodsForBoss(Page<NCPeriodsInfo> page) {
		String hql = "from NCPeriodsInfo where  state not in (1,2) order by lotteryTime desc";
		Object[] parameter = new Object[] {};
		return ncPeriodsInfoDao.findPage(page, hql, parameter);
	}

	public static void main(String[] args) {
		ApplicationContext context = new FileSystemXmlApplicationContext("F:/JAVA/workspace/Lottery_v1.9.0/src/main/webapp/WEB-INF/applicationContext.xml");
		INCPeriodsInfoLogic test = (INCPeriodsInfoLogic) context.getBean("ncPeriodsInfoLogic");
		test.saveNCPeriods(new Date());
	}

	// 保存修改幸运农场的开奖结果
	@Override
	public void updateLotResult(NCPeriodsInfo NCPeriodsInfo) {
		NCPeriodsInfo gdp = new NCPeriodsInfo();
		gdp = ncPeriodsInfoDao.findUniqueBy("periodsNum", NCPeriodsInfo.getPeriodsNum());
		gdp.setResult1(NCPeriodsInfo.getResult1());
		gdp.setResult2(NCPeriodsInfo.getResult2());
		gdp.setResult3(NCPeriodsInfo.getResult3());
		gdp.setResult4(NCPeriodsInfo.getResult4());
		gdp.setResult5(NCPeriodsInfo.getResult5());
		gdp.setResult6(NCPeriodsInfo.getResult6());
		gdp.setResult7(NCPeriodsInfo.getResult7());
		gdp.setResult8(NCPeriodsInfo.getResult8());
		gdp.setState(NCPeriodsInfo.getState());

		ncPeriodsInfoDao.save(gdp);
		// 这里增加其他操作

	}

	public NCPeriodsInfo queryLastLotteryPeriods() {
		NCPeriodsInfo gdp = null;

		String hql = "from NCPeriodsInfo where  state in (4,7) and lotteryTime>=? order by lotteryTime desc";
		String yes = DatetimeUtil.yesterday();
		String yesStr = yes + " 22:59:59";
		Date yestoday = java.sql.Timestamp.valueOf(yesStr);

		Object[] parameter = new Object[] { yestoday };
		List<NCPeriodsInfo> cqpList = ncPeriodsInfoDao.find(hql, parameter);
		if (cqpList != null && cqpList.size() > 0) {
			gdp = cqpList.get(0);
			cqpList.clear();
		}
		return gdp;

	}

	public NCPeriodsInfo queryLastLotteryPeriods_noTime() {
		NCPeriodsInfo gdp = null;

		String hql = "from NCPeriodsInfo where  state in (4,7) order by lotteryTime desc";

		Object[] parameter = new Object[] {};
		List<NCPeriodsInfo> cqpList = ncPeriodsInfoDao.find(hql, parameter);
		if (cqpList != null && cqpList.size() > 0) {
			gdp = cqpList.get(0);
			cqpList.clear();
		}
		return gdp;

	}

	public NCPeriodsInfo queryLastNotOpenPeriods() {
		NCPeriodsInfo gdp = null;
		String hql = "from NCPeriodsInfo where  state=1 and openQuotTime>? order by openQuotTime asc";
		Object[] parameter = new Object[] { new Date() };
		List<NCPeriodsInfo> cqpList = ncPeriodsInfoDao.find(hql, parameter);
		if (cqpList != null && cqpList.size() > 0)
			gdp = cqpList.get(0);
		return gdp;

	}

	private NCPeriodsInfo queryLastNeedOpenPeriods(String status) {
		NCPeriodsInfo gdp = null;
		String hql = "from NCPeriodsInfo where  state=?  and stopQuotTime>? and openQuotTime<=? order by openQuotTime asc";
		Object[] parameter = new Object[] { status, new Date(), new Date() };
		List<NCPeriodsInfo> cqpList = ncPeriodsInfoDao.find(hql, parameter);
		if (cqpList != null && cqpList.size() > 0)
			gdp = cqpList.get(0);
		return gdp;
	}

	@Override
	public void updatePeriodsStatusByPeriodsNum(String periodsNum, String status) {

		String hql = "update NCPeriodsInfo set state=? where periodsNum=? ";
		ncPeriodsInfoDao.batchExecute(hql, status, periodsNum);

	}

	@Override
	public NCPeriodsInfo queryPeriods(Object periodsNum, Object status) {
		return ncPeriodsInfoDao.findUnique("from NCPeriodsInfo where periodsNum = ? and state != ?", periodsNum, status);
	}

	@Override
	public void update(NCPeriodsInfo NCPeriodsInfo) {
		ncPeriodsInfoDao.update(NCPeriodsInfo);
	}

	@Override
	public List<NCPeriodsInfo> queryByNCPeriods(String periodsNum, String number) {
		return ncPeriodsInfoDao.findBy(periodsNum, number);
	}

	@Override
	public void deletePeriods(String beginPeriodsNum, String endPeriodsNum) {
		String hql = "delete NCPeriodsInfo where periodsNum>=? and periodsNum <=?";
		ncPeriodsInfoDao.batchExecute(hql, beginPeriodsNum, endPeriodsNum);
	}

	@Override
	public List<NCPeriodsInfo> queryReportPeriodsNum() {
		String hql = "from NCPeriodsInfo where state=7  and  lotteryTime <=?   order by lotteryTime desc";
		Object[] parameter = new Object[] { new Date() };
		Page<NCPeriodsInfo> page = new Page<NCPeriodsInfo>();
		page.setPageSize(50);
		page.setPageNo(1);
		page = ncPeriodsInfoDao.findPage(page, hql, parameter);
		return page.getResult();
	}

	/**
	 * 取当前盘期 和 之前 数据
	 * 
	 * @return
	 */
	@Override
	public List<NCPeriodsInfo> queryBeforeRunPeriodsNumList(Integer row) {

		Calendar afterTime = Calendar.getInstance();
		afterTime.add(Calendar.MINUTE, 10);
		Date afterDate = (Date) afterTime.getTime();

		String hql = "from NCPeriodsInfo where lotteryTime <=?   order by lotteryTime desc";
		Object[] parameter = new Object[] { afterDate };
		Page<NCPeriodsInfo> page = new Page<NCPeriodsInfo>();
		page.setPageSize(row);
		page.setPageNo(1);
		page = ncPeriodsInfoDao.findPage(page, hql, parameter);
		return page.getResult();
	}

	@Override
	public List<NCPeriodsInfo> queryPeriods(String beginPeriodsNum, String endPeriodsNum) {
		String hql = "from NCPeriodsInfo where periodsNum>=? and periodsNum <=?";
		Object[] parameter = new Object[] { beginPeriodsNum, endPeriodsNum };
		return ncPeriodsInfoDao.find(hql, parameter);
	}

	@Override
	public NCPeriodsInfo queryZhangdanPeriods() {
		NCPeriodsInfo NCPeriodsInfo = null;
		String hql = "FROM NCPeriodsInfo WHERE lotteryTime>=? AND openQuotTime<=? AND state not in (4,7)";
		List<NCPeriodsInfo> periodsList = ncPeriodsInfoDao.find(hql, new Date(), new Date());
		if (periodsList != null && periodsList.size() != 0)
			NCPeriodsInfo = periodsList.get(0);
		return NCPeriodsInfo;

	}

	/*@Override
	public Map<String, List<Integer>> getResult() {
		return dataExtractor.getResult();
	}

	public DataExtractor getDataExtractor() {
		return dataExtractor;
	}

	public void setDataExtractor(DataExtractor dataExtractor) {
		this.dataExtractor = dataExtractor;
	}

	@Override
	public Map<String, List<Integer>> getResultByPeriodsNum(String periodsNum) {
		return dataExtractor.getResultByPeriodsNum(periodsNum);
	}*/

	@Override
	public NCPeriodsInfo findCurrentStopPeriodInfo() {
		final String hql = "FROM NCPeriodsInfo WHERE state=3 ORDER BY periodsNum DESC";
		List<NCPeriodsInfo> results = ncPeriodsInfoDao.find(hql);
		return !CollectionUtils.isEmpty(results) ? results.get(0) : null;
	}

	@Override
	public List<NCPeriodsInfo> findAllExceptionPeriodInfo() {
		/*// 获取状态是5的或者比当前盘期期数小,但状态是1/2/3的数据
		final String hql = "FROM NCPeriodsInfo WHERE "
				+ "(state=5 OR (periodsNum < (SELECT MAX(periodsNum)-1 FROM  NCPeriodsInfo WHERE openQuotTime< :today AND lotteryTime >= :today) AND state IN (1,2,3))) "
				+ "AND lotteryTime >= to_date(:todayStr,'yyyyMMdd') ORDER BY periodsNum desc";*/
		final String hql="FROM NCPeriodsInfo WHERE state not in (6,7) and lottery_time <= :sysdate ORDER BY periods_num desc";
		Date today = new Date();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//paramMap.put("today", today);
		//paramMap.put("todayStr", new SimpleDateFormat("yyyyMMdd").format(today));
		paramMap.put("sysdate", today);
		List<NCPeriodsInfo> result = ncPeriodsInfoDao.find(hql, paramMap);
		//return result.subList(0, result.size() > 10 ? 10 : result.size());
		//return result.subList(0, result.size() > 1 ? 1 : result.size());
		return result;
	}

	@Override
	public Map<String, NCPeriodsInfo> getPeriodsInfoFromBetTableForReLotteryEOD(String scheme) {
		Map<String, NCPeriodsInfo> resultMap = new TreeMap<String, NCPeriodsInfo>();
		List<NCPeriodsInfo> resultList = ncPeriodsInfoDao.getPeriodsInfoFromBetTableForReLotteryEOD(scheme);
		if (!CollectionUtils.isEmpty(resultList)) {
			for (NCPeriodsInfo periodsInfo : resultList) {
				resultMap.put(periodsInfo.getPeriodsNum(), periodsInfo);
			}
		}
		return resultMap;
	}

	@Override
	public List<NCPeriodsInfo> findAllExceptionPeriodInfoByDate(Date queryDate) {
		List<NCPeriodsInfo> result = null;
		try {
			// 获取状态是5的queryDate盘期
			final String hql = "FROM NCPeriodsInfo WHERE state not in (4,6,7) AND openQuotTime BETWEEN :queryDateFrom AND :queryDateTo order by periodsNum desc";

			Map<String, Object> paramMap = new HashMap<String, Object>();
			SimpleDateFormat shortFormat = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat longFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

			paramMap.put("queryDateFrom", longFormat.parse(shortFormat.format(queryDate) + " 9:50:00"));
			paramMap.put("queryDateTo", longFormat.parse(shortFormat.format(DateUtils.addDays(queryDate, 1)) + " 02:00:00"));
			result = ncPeriodsInfoDao.find(hql, paramMap);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public NCPeriodsInfo findCurrentPeriod() {
		final String hql = "FROM NCPeriodsInfo WHERE openQuotTime <= :sysdate and lotteryTime >= :sysdate";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sysdate", new Date());
		List<NCPeriodsInfo> result = ncPeriodsInfoDao.find(hql, paramMap);
		return result.size() > 0 ? result.get(0) : null;
	}

	public INCPeriodsInfoDao getNcPeriodsInfoDao() {
		return ncPeriodsInfoDao;
	}

	public void setNcPeriodsInfoDao(INCPeriodsInfoDao ncPeriodsInfoDao) {
		this.ncPeriodsInfoDao = ncPeriodsInfoDao;
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
}
