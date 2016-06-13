/**
 * 
 */
package com.npc.lottery.periods.logic.spring;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.member.logic.interf.ICheckLogic;
import com.npc.lottery.periods.dao.interf.IJSSBPeriodsInfoDao;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.util.DatetimeUtil;
import com.npc.lottery.util.Page;

/**
 * @author peteran
 * 
 */
public class JSSBPeriodsInfoLogic implements IJSSBPeriodsInfoLogic {
	private Logger				logger	= Logger.getLogger(JSSBPeriodsInfoLogic.class);
	private IJSSBPeriodsInfoDao	jssbPeriodsInfoDao;

	//private DataExtractor		dataExtractor;
	
	private ICheckLogic checkLogic;
	private ShopSchemeService shopSchemeService;
	@Override
	public JSSBPeriodsInfo queryByPeriodsStatus(String status) {
		long times = System.currentTimeMillis();
		//times = times + 3000;
		JSSBPeriodsInfo result = null;
		String hql = "FROM JSSBPeriodsInfo WHERE  state=? AND stopQuotTime>=? AND openQuotTime<=? ORDER BY openQuotTime DESC ";
		List<JSSBPeriodsInfo> periodsList = jssbPeriodsInfoDao.find(hql, status, new Date(), new Date(times));
		if (periodsList != null && periodsList.size() != 0) {
			result = periodsList.get(0);
		}
		return result;
	}

	@Override
	public JSSBPeriodsInfo queryStopPeriods(String status) {
		long times = System.currentTimeMillis();
		//times = times - 5000;
		JSSBPeriodsInfo result = null;
		String hql = "FROM JSSBPeriodsInfo WHERE  state=? and lotteryTime>=? AND openQuotTime<=? ORDER BY openQuotTime DESC ";
		List<JSSBPeriodsInfo> periodsList = jssbPeriodsInfoDao.find(hql, status, new Date(times), new Date());
		if (periodsList != null && periodsList.size() != 0)
			result = periodsList.get(0);
		return result;

	}

	@Override
	public JSSBPeriodsInfo queryLastLotteryPeriods() {
		JSSBPeriodsInfo result = null;

		String hql = "FROM JSSBPeriodsInfo WHERE  state IN (4,7) AND lotteryTime>=? ORDER BY lotteryTime DESC";
		String yes = DatetimeUtil.yesterday();
		String yesStr = yes + " 22:59:59";
		Date yestoday = java.sql.Timestamp.valueOf(yesStr);

		Object[] parameter = new Object[] { yestoday };
		List<JSSBPeriodsInfo> periodsList = jssbPeriodsInfoDao.find(hql, parameter);
		if (periodsList != null && periodsList.size() > 0) {
			result = periodsList.get(0);
		}
		return result;

	}

	@Override
	public void deletePeriods(String beginPeriodsNum, String endPeriodsNum) {
		String hql = "delete JSSBPeriodsInfo where periodsNum>=? and periodsNum <=?";
		jssbPeriodsInfoDao.batchExecute(hql, beginPeriodsNum, endPeriodsNum);
	}

	@Override
	public void saveJSPeriods() {
		try {
			Date date = new Date();
			SimpleDateFormat ageFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat openFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String ageDate = ageFormat.format(date) + " 08:30:15";
			Date openDate = openFormat.parse(ageDate);
			Calendar sysCalender = Calendar.getInstance();
			Calendar sysCalenderStop = Calendar.getInstance(); // 封盘时间设置
			sysCalender.setTime(openDate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); // 盘期时间格式
			String dateString = formatter.format(date);
			int periodsCount = 82; // 期数
			int endPerDate = -90; // 提前封盘的时间
			for (int i = 1; i <= periodsCount; i++) {
				JSSBPeriodsInfo periodsInfo = new JSSBPeriodsInfo();
				StringBuffer periodsNum = new StringBuffer(); // 期数设置
				if (i >= 10) {
					//011
					periodsNum.append(dateString).append("0").append(i);
				} else {
					//001
					periodsNum.append(dateString).append("0").append("0").append(i);
				}
				periodsInfo.setPeriodsNum(String.valueOf(periodsNum));
				sysCalender.add(Calendar.MINUTE, 10); // 在开盘的基础上加10分钟到开奖的时间点
				Date lotteryDate = (Date) sysCalender.getTime();
				if (i == 1) {
					// System.out.println("开盘时间："+openFormat.format(openDate));
					// logger.info("开盘时间：" + openFormat.format(openDate));
					periodsInfo.setOpenQuotTime(openDate);
				} else {
					sysCalender.add(Calendar.MINUTE, -10); // -10分钟
															// 在下轮开奖的基础上减去10分钟就是当前开盘时间
					Date resultDate = (Date) sysCalender.getTime();
					logger.info("开盘时间：" + openFormat.format(resultDate));
					// System.out.println("开盘时间："+resultDate);
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
				periodsInfo.setState(Constant.NOT_STATUS);
				periodsInfo.setCreateTime(new Date());
				sysCalender.setTime(lotteryDate);
				jssbPeriodsInfoDao.save(periodsInfo);
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * 改变江苏状态值
	 */
	@Override
	public void updateCommon(String commonDate) {
		JSSBPeriodsInfo jssbPeriodsInfo = new JSSBPeriodsInfo();
		jssbPeriodsInfo = queryLastNeedOpenPeriods(Constant.NOT_STATUS);
		if (jssbPeriodsInfo != null) {
			logger.info("江苏开盘 盘期号码：" + jssbPeriodsInfo.getPeriodsNum());
			jssbPeriodsInfo.setState(Constant.OPEN_STATUS);
			jssbPeriodsInfoDao.save(jssbPeriodsInfo);
		} else {
			logger.info("江苏开盘无法拿到开盘盘期：");
		}

	}

	private JSSBPeriodsInfo queryLastNeedOpenPeriods(String status) {
		JSSBPeriodsInfo result = null;
		String hql = "FROM JSSBPeriodsInfo WHERE  state=?  AND stopQuotTime>? AND openQuotTime<=? ORDER BY openQuotTime ASC";
		Object[] parameter = new Object[] { status, new Date(), new Date() };
		List<JSSBPeriodsInfo> resultList = jssbPeriodsInfoDao.find(hql, parameter);
		if (resultList != null && resultList.size() > 0) {
			result = resultList.get(0);
		}
		return result;
	}

	@Override
	public JSSBPeriodsInfo updateJSSBStatus() {

		JSSBPeriodsInfo jssbPeriodsInfo = queryLastNeedStopPeriods(Constant.OPEN_STATUS);

		if (jssbPeriodsInfo != null) {
			jssbPeriodsInfo.setState(Constant.STOP_STATUS);
			// logger.info("JS 封盘>>>>>"+bjPeriodsInfo.getPeriodsNum());
			jssbPeriodsInfoDao.update(jssbPeriodsInfo);

		} else {
			// logger.info("JS 封盘>>>>>未能 取得封盤 盤起。。。。");
		}

		return jssbPeriodsInfo;
	}

	/*@Override
	public JSSBPeriodsInfo updateJSSBStatusWithCheckMiragation() {

		JSSBPeriodsInfo jssbPeriodsInfo = queryLastNeedStopPeriods(Constant.OPEN_STATUS);

		if (jssbPeriodsInfo != null) {
			jssbPeriodsInfo.setState(Constant.STOP_STATUS);
			jssbPeriodsInfoDao.update(jssbPeriodsInfo);
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();

			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();
				// 迁移投注数据
				checkLogic.miragationJSDataToCheck(jssbPeriodsInfo.getPeriodsNum(),scheme);
			}
			// 迁移补货数据

		}

		return jssbPeriodsInfo;
	}*/

	@Override
	public void save(JSSBPeriodsInfo jssbPeriodsInfo) {
		jssbPeriodsInfoDao.save(jssbPeriodsInfo);
	}

	private JSSBPeriodsInfo queryLastNeedStopPeriods(String status) {
		JSSBPeriodsInfo result = null;
		String hql = "FROM JSSBPeriodsInfo WHERE  state=?  AND lotteryTime >? order by openQuotTime asc";
		Date now = new Date();
		Object[] parameter = new Object[] { status, now };
		List<JSSBPeriodsInfo> resultList = jssbPeriodsInfoDao.find(hql, parameter);
		if (resultList != null && resultList.size() > 0) {
			result = resultList.get(0);
		}
		return result;
	}

	@Override
	public List<JSSBPeriodsInfo> queryAllPeriods(Criterion... criterias) {
		return jssbPeriodsInfoDao.find(criterias);
	}

	@Override
	public JSSBPeriodsInfo queryByPeriods(Criterion... criterias) {
		JSSBPeriodsInfo gdp = null;
		List<JSSBPeriodsInfo> periodsList = jssbPeriodsInfoDao.find(criterias);
		if (periodsList != null && periodsList.size() != 0)
			gdp = periodsList.get(0);
		return gdp;
	}
	
	//保存修改K3的开奖结果
	@Override
	public void updateK3LotResult(JSSBPeriodsInfo jssbPeriodsInfo) {
		JSSBPeriodsInfo gdp=new JSSBPeriodsInfo();		
		gdp = jssbPeriodsInfoDao.findUniqueBy("periodsNum",jssbPeriodsInfo.getPeriodsNum());
		gdp.setResult1(jssbPeriodsInfo.getResult1());
		gdp.setResult2(jssbPeriodsInfo.getResult2());
		gdp.setResult3(jssbPeriodsInfo.getResult3());
		gdp.setState(jssbPeriodsInfo.getState());
		
		jssbPeriodsInfoDao.save(gdp);
            //这里增加其他操作
	}
	
	@Override
	public Page<JSSBPeriodsInfo> findPage(Page<JSSBPeriodsInfo> page, Criterion... criterions) {
		return jssbPeriodsInfoDao.findPage(page, criterions);
	}

	@Override
	public List<JSSBPeriodsInfo> queryPeriods(String beginPeriodsNum, String endPeriodsNum) {
		String hql = "from JSSBPeriodsInfo where periodsNum>=? and periodsNum <=?";
		Object[] parameter = new Object[] { beginPeriodsNum, endPeriodsNum };
		return jssbPeriodsInfoDao.find(hql, parameter);
	}

	@Override
	public List<JSSBPeriodsInfo> getCurrentDayHisLotteryPeridosInfoList() {
		String hql = "FROM JSSBPeriodsInfo WHERE state IN (4,7) AND TO_CHAR(lotteryTime,'yyyyMMdd')= :day";
		Map<String, String> paramMap = new HashMap<String, String>();
		String day = new SimpleDateFormat("yyyyMMdd").format(new Date());
		paramMap.put("day", day);
		return jssbPeriodsInfoDao.find(hql, paramMap);
	}

	@Override
	public Page<JSSBPeriodsInfo> queryHistoryPeriods(Page<JSSBPeriodsInfo> page) {
		String hql = "from JSSBPeriodsInfo where  state in (4,6,7) order by lotteryTime desc";
		Object[] parameter = new Object[] {};
		return jssbPeriodsInfoDao.findPage(page, hql, parameter);
	}
	
	@Override
	public Page<JSSBPeriodsInfo> queryHistoryPeriodsForBoss(Page<JSSBPeriodsInfo> page,String state)
    {
		String para = "";
    	if("7".equals(state)){
    		para = " where  state in (4,6,7) ";
    	}else if("5".equals(state)){
    		para = " where  state =5 ";
    	}
    	String hql="from JSSBPeriodsInfo "+para+" order by lotteryTime desc";
    	Object[] parameter=new Object[]{};
    	return jssbPeriodsInfoDao.findPage(page, hql, parameter);
    }

	

	@Override
	public JSSBPeriodsInfo findCurrentStopPeriodInfo() {
		final String hql = "FROM JSSBPeriodsInfo WHERE state=3 ORDER BY periodsNum DESC";
		List<JSSBPeriodsInfo> results = jssbPeriodsInfoDao.find(hql);
		return !CollectionUtils.isEmpty(results) ? results.get(0) : null;
	}

	@Override
	public void update(JSSBPeriodsInfo jssbPeriodsInfo) {
		jssbPeriodsInfoDao.update(jssbPeriodsInfo);
	}

/*	@Override
	public Map<String, List<Integer>> getResult() {
		return dataExtractor.getResult();
	}
	@Override
    public Map<String, List<Integer>> getResultByPeriodsNum(String periodsNum) {
        return dataExtractor.getResultByPeriodsNum(periodsNum);
    }*/
	
	@Override
	public List<JSSBPeriodsInfo> findAllExceptionPeriodInfo() {
		// 获取状态是5的或者比当前盘期期数小,但状态是1/2/3的数据
		/*final String hql = "FROM JSSBPeriodsInfo WHERE "
				+ "(state=5 OR (periodsNum < (SELECT MAX(periodsNum)-1 FROM  JSSBPeriodsInfo WHERE openQuotTime<= :today AND lotteryTime >= :today) AND state IN (1,2,3))) "
				+ "AND lotteryTime >= to_date(:todayStr,'yyyyMMdd') ORDER BY periodsNum desc";*/
		final String hql="FROM JSSBPeriodsInfo WHERE state not in (6,7) and lottery_time <= :sysdate ORDER BY periods_num desc";
		Date today = new Date();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sysdate", today);
		//paramMap.put("today", today);
		//paramMap.put("todayStr", new SimpleDateFormat("yyyyMMdd").format(today));
		List<JSSBPeriodsInfo> result = jssbPeriodsInfoDao.find(hql, paramMap);
		//return result.subList(0, result.size() > 10 ? 10 : result.size());
		//return result.subList(0, result.size() > 1 ? 1 : result.size());
		return result;
	}
	
	@Override
    public void updatePeriodsStatusByPeriodsNum(String periodsNum,String status){
	 
	 String hql="update JSSBPeriodsInfo set state=? where periodsNum=? ";
	 jssbPeriodsInfoDao.batchExecute(hql, status,periodsNum);
	 
	
    }
	
	@Override
    public List<JSSBPeriodsInfo> queryBeforeRunPeriodsNumList(Integer row) {
    	Calendar afterTime = Calendar.getInstance();
    	  afterTime.add(Calendar.MINUTE, 5);
    	  Date afterDate = (Date) afterTime.getTime(); 
    	  
        String hql = "from JSSBPeriodsInfo where lotteryTime <=?   order by lotteryTime desc";       
        Object[] parameter = new Object[] {afterDate};
        Page<JSSBPeriodsInfo> page = new Page<JSSBPeriodsInfo>();
        page.setPageSize(row);
        page.setPageNo(1);
        page = jssbPeriodsInfoDao.findPage(page, hql, parameter);
        return  page.getResult();
    }

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new FileSystemXmlApplicationContext("F:/JAVA/workspace/Lottery_v1.2/src/main/webapp/WEB-INF/applicationContext.xml");
		IJSSBPeriodsInfoLogic test = (IJSSBPeriodsInfoLogic) context.getBean("jssbPeriodsInfoLogic");
		//System.out.println(test.getPeriodsInfoFromBetTableForReLotteryEOD().size());
		System.out.println(test.findAllExceptionPeriodInfoByDate(new Date()).size());

	}

	public IJSSBPeriodsInfoDao getJssbPeriodsInfoDao() {
		return jssbPeriodsInfoDao;
	}

	public void setJssbPeriodsInfoDao(IJSSBPeriodsInfoDao jssbPeriodsInfoDao) {
		this.jssbPeriodsInfoDao = jssbPeriodsInfoDao;
	}

/*	public DataExtractor getDataExtractor() {
		return dataExtractor;
	}

	public void setDataExtractor(DataExtractor dataExtractor) {
		this.dataExtractor = dataExtractor;
	}*/

	@Override
	public List<JSSBPeriodsInfo> queryTodayPeriods() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// sdf.format();

		java.util.Date now = new java.util.Date();
		String nowStr = sdf.format(now);
		String before = nowStr + " 00:00:00";
		String after = nowStr + " 23:59:59";
		Date bef = java.sql.Timestamp.valueOf(before);
		Date af = java.sql.Timestamp.valueOf(after);

		String hql = "from JSSBPeriodsInfo where  lotteryTime>=? and lotteryTime<=? and state in (4,7) order by lotteryTime desc";
		Object[] parameter = new Object[] { bef, af };
		return jssbPeriodsInfoDao.find(hql, parameter);
	}
	
	@Override
	public List<JSSBPeriodsInfo> queryTodayAllPeriods() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// sdf.format();

		java.util.Date now = new java.util.Date();
		String nowStr = sdf.format(now);
		String before = nowStr + " 00:00:00";
		String after = nowStr + " 23:59:59";
		Date bef = java.sql.Timestamp.valueOf(before);
		Date af = java.sql.Timestamp.valueOf(after);

		String hql = "from JSSBPeriodsInfo where  lotteryTime>=? and lotteryTime<=?  order by lotteryTime desc";
		Object[] parameter = new Object[] { bef, af };
		return jssbPeriodsInfoDao.find(hql, parameter);
	}

	@Override
	public Map<String, JSSBPeriodsInfo> getPeriodsInfoFromBetTableForReLotteryEOD(String scheme) {
		Map<String, JSSBPeriodsInfo> resultMap = new TreeMap<String, JSSBPeriodsInfo>();
		List<JSSBPeriodsInfo> resultList = jssbPeriodsInfoDao.getPeriodsInfoFromBetTableForReLotteryEOD(scheme);
		if (!CollectionUtils.isEmpty(resultList)) {
			for (JSSBPeriodsInfo periodsInfo : resultList) {
				resultMap.put(periodsInfo.getPeriodsNum(), periodsInfo);
			}
		}
		return resultMap;
	}

	@Override
	public JSSBPeriodsInfo queryByPeriods(String periodsNum, Object number) {
		return jssbPeriodsInfoDao.findUniqueBy(periodsNum, number);
	}
	
	@Override
	public List<JSSBPeriodsInfo> findAllExceptionPeriodInfoByDate(Date queryDate) {
		// 获取状态是5的queryDate盘期
		final String hql = "FROM JSSBPeriodsInfo WHERE state not in (4,6,7) and to_char(openQuotTime,'yyyyMMdd')=:queryDate order by periodsNum desc";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("queryDate", new SimpleDateFormat("yyyyMMdd").format(queryDate));
		List<JSSBPeriodsInfo> result = jssbPeriodsInfoDao.find(hql, paramMap);
		return result;
	}

	public JSSBPeriodsInfo findCurrentPeriod() {
		final String hql = "FROM JSSBPeriodsInfo WHERE openQuotTime <= :sysdate and lotteryTime >= :sysdate";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sysdate", new Date());
		List<JSSBPeriodsInfo> result = jssbPeriodsInfoDao.find(hql, paramMap);
		return result.size() > 0 ? result.get(0) : null;
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
