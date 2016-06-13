package com.npc.lottery.periods.logic.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.springframework.util.CollectionUtils;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.member.logic.interf.ICheckLogic;
import com.npc.lottery.periods.dao.interf.IBJSCPeriodsInfoDao;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.util.DatetimeUtil;
import com.npc.lottery.util.Page;
public class BJSCPeriodsInfoLogic implements IBJSCPeriodsInfoLogic {

    private Logger logger = Logger.getLogger(BJSCPeriodsInfoLogic.class);
    private IBJSCPeriodsInfoDao bjscPeriodsInfoDao;
    //private DataExtractor dataExtractor;
    private ShopSchemeService shopSchemeService;
    private ICheckLogic checkLogic;
    @Override
    public void saveBJSCPeriods() {
        	long startPeriodNum = bjscPeriodsInfoDao.getBJSCYesterdayLastPeriods()+1;
        	Date openQuotTime=getBJOpenDate(new Date());
        	if(startPeriodNum!=0){
        		saveBJSCPeriods(String.valueOf(startPeriodNum),openQuotTime,179);
        	}else{
        		logger.info("没有找到昨天的结束盘号...");
        	}
    }

    
    public void saveBJSCPeriods(final String startPeriodNum,final Date startTime,int periodCnt)
    {
    	if(!GenericValidator.isInt(startPeriodNum))
    		logger.error("起始盘期号格式错误"+startPeriodNum);
    	Integer startNum=Integer.valueOf(startPeriodNum);
    	Integer endNum=Integer.valueOf(startNum+periodCnt-1);
    	this.deletePeriods(startNum.toString(), endNum.toString());
    	
    	Date openQuotTime=startTime;
    	if(openQuotTime==null)
    	{
			openQuotTime=getBJOpenDate(new Date());
    	}else{
    		openQuotTime=getBJOpenDate(startTime);
    	}
        Date stopQuotTime=null;
    	Date lotteryTime=null;
    	//Date startTempleTime=openQuotTime;
    	SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd");
    	int timeGap=300;
    	int fpGap=75;
    	for(int i=0;i<periodCnt;i++)
    	{
    		 String periodNum=String.valueOf(Integer.valueOf(startPeriodNum)+i);
    		
    		 
    		 String stopStr=sm.format(openQuotTime)+" 23:57:00";
    		 
    	
    		
    		 stopQuotTime=DateUtils.add(openQuotTime, Calendar.SECOND, timeGap-fpGap);
    		 lotteryTime=DateUtils.add(stopQuotTime, Calendar.SECOND, fpGap);
    		 
    		 
    		 BJSCPeriodsInfo  periodsInfo = new BJSCPeriodsInfo();
    		 periodsInfo.setPeriodsNum(periodNum);
    		 periodsInfo.setOpenQuotTime(openQuotTime);
    		 periodsInfo.setStopQuotTime(stopQuotTime);
    		 periodsInfo.setLotteryTime(lotteryTime);
    		 
    		 periodsInfo.setResult1(0);
             periodsInfo.setResult2(0);
             periodsInfo.setResult3(0);
             periodsInfo.setResult4(0);
             periodsInfo.setResult5(0);
             periodsInfo.setResult6(0);
             periodsInfo.setResult7(0);
             periodsInfo.setResult8(0);
             periodsInfo.setResult9(0);
             periodsInfo.setResult10(0);
             periodsInfo.setState(Constant.NOT_STATUS);
             periodsInfo.setCreateTime(new Date());
             bjscPeriodsInfoDao.save(periodsInfo);
             Date nextOpenTime=DateUtils.add(openQuotTime, Calendar.SECOND, timeGap);
             //openQuotTime=DateUtils.add(openQuotTime, Calendar.SECOND, timeGap);
        	 try {
 				Date lastStopDayTime=DatetimeUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", stopStr);
 				if(nextOpenTime.after(lastStopDayTime))
 				{
 					openQuotTime=getBJOpenDate(DateUtils.add(lastStopDayTime, Calendar.DATE, 1));
 				}
 				else
 					openQuotTime=nextOpenTime;
 			} catch (ParseException e) {
 				logger.error("BJSC开盘时间解析错误");
 			}
    	}
    	
    	
    	
    	
    }
    @Override
    public BJSCPeriodsInfo queryByPeriods(String periodsNum,Object number) {
            return bjscPeriodsInfoDao.findUniqueBy(periodsNum,number);
    }
    
    @Override
    public Page<BJSCPeriodsInfo> findPage(Page<BJSCPeriodsInfo> page,Criterion... criterions) {
        return bjscPeriodsInfoDao.findPage(page, criterions);
    }
    
    @Override
    public void save(BJSCPeriodsInfo gdPeriodsInfo) {
            bjscPeriodsInfoDao.save(gdPeriodsInfo);
    }
    
    @Override
    public List<BJSCPeriodsInfo> queryAllPeriods() {
        return bjscPeriodsInfoDao.getAll();
    }

    public IBJSCPeriodsInfoDao getPeriodsInfoDao() {
        return bjscPeriodsInfoDao;
    }

    public void setPeriodsInfoDao(IBJSCPeriodsInfoDao periodsInfoDao) {
        this.bjscPeriodsInfoDao = periodsInfoDao;
    }

    @Override
    public List<BJSCPeriodsInfo> queryAllPeriods(Criterion...criterias) {
        return bjscPeriodsInfoDao.find(criterias);
    }
    @Override
    public BJSCPeriodsInfo queryByPeriods(Criterion...criterias) {
    	BJSCPeriodsInfo gdp=null;
    	List<BJSCPeriodsInfo> periodsList=bjscPeriodsInfoDao.find(criterias);
    	if(periodsList!=null&&periodsList.size()!=0)
    		gdp=periodsList.get(0);
    	return gdp;
    }
    
    public BJSCPeriodsInfo queryByPeriodsStatus(String status)
    {
    	long times=System.currentTimeMillis();
	    times=times+3000;
    	BJSCPeriodsInfo cqp=null;
    	String hql="from BJSCPeriodsInfo where  state=? and stopQuotTime>=? and openQuotTime<=? order by openQuotTime desc ";
    	List<BJSCPeriodsInfo> periodsList=bjscPeriodsInfoDao.find(hql, status,new Date(),new Date(times));
    	if(periodsList!=null&&periodsList.size()!=0)
    		cqp=periodsList.get(0);
    	return cqp;
    	
    	
    }
    public BJSCPeriodsInfo queryStopPeriods(String status)
    {
    	BJSCPeriodsInfo cqp=null;
    	String hql="from BJSCPeriodsInfo where  state=? and lotteryTime>=? and openQuotTime<=? order by openQuotTime desc ";
    	List<BJSCPeriodsInfo> periodsList=bjscPeriodsInfoDao.find(hql, status,new Date(),new Date());
    	if(periodsList!=null&&periodsList.size()!=0)
    		cqp=periodsList.get(0);
    	return cqp;
    	
    }
    /**
     * 改变GD状态值
     */
    @Override
    public BJSCPeriodsInfo updateCommon(String commonDate){
        BJSCPeriodsInfo bjPeriodsInfo = new BJSCPeriodsInfo();
        bjPeriodsInfo =  queryLastNeedOpenPeriods(Constant.NOT_STATUS);
        if(bjPeriodsInfo!=null)
        {
        logger.info("北京开盘 盘期号码："+bjPeriodsInfo.getPeriodsNum());
        bjPeriodsInfo.setState(Constant.OPEN_STATUS);
        bjscPeriodsInfoDao.save(bjPeriodsInfo);
        return bjPeriodsInfo;
        }
        else
        {
        	 logger.info("北京开盘无法拿到开盘盘起：");
        	 return null;
        }
        	
    }
    @Override
    public BJSCPeriodsInfo updateBJSCStatus(){
       
    	BJSCPeriodsInfo bjPeriodsInfo = queryLastNeedStopPeriods(Constant.OPEN_STATUS);	
        	
            if(bjPeriodsInfo!=null)
            {
            	bjPeriodsInfo.setState(Constant.STOP_STATUS);
            //logger.info("BJ 封盘>>>>>"+bjPeriodsInfo.getPeriodsNum());
            bjscPeriodsInfoDao.update(bjPeriodsInfo);
            
            }
            else
            {
            	//logger.info("BJ 封盘>>>>>未能 取得封盤 盤起。。。。");
            }
            
           
        return bjPeriodsInfo;
    }
    
  /*  @Override
	public BJSCPeriodsInfo updateBJSCStatusWithCheckMiragation() {

		BJSCPeriodsInfo bjPeriodsInfo = queryLastNeedStopPeriods(Constant.OPEN_STATUS);

		if (bjPeriodsInfo != null) {
			bjPeriodsInfo.setState(Constant.STOP_STATUS);
			bjscPeriodsInfoDao.update(bjPeriodsInfo);
			// 获取商铺对应的scheme列表
			Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();

			// 每一个商铺按每期进去兑奖
			for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
				String scheme = shopScheme.getValue();

				// 迁移北京投注数据
				checkLogic.miragationBJDataToCheck(bjPeriodsInfo.getPeriodsNum(), scheme);
				// 迁移补货数据
			}

		}

		return bjPeriodsInfo;
	}*/
    
    private BJSCPeriodsInfo queryLastNeedStopPeriods(String status)
    {
        BJSCPeriodsInfo cdp=null;
        String hql = "from BJSCPeriodsInfo where  state=?  and lotteryTime >?   order by openQuotTime asc";       
        Object[] parameter = new Object[] { status,new Date()};
        List<BJSCPeriodsInfo> cqpList=bjscPeriodsInfoDao.find(hql, parameter);
        if(cqpList!=null&&cqpList.size()>0)
            cdp=cqpList.get(0);
         return cdp;
    }
    
    @Override
    public List<BJSCPeriodsInfo> queryTodayPeriods() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//sdf.format();
    	
		java.util.Date now=new java.util.Date();
		String nowStr=sdf.format(now);
		String before=nowStr+" 00:00:00";
		String after=nowStr+" 23:59:59";
    	Date bef=java.sql.Timestamp.valueOf(before);
    	Date af=java.sql.Timestamp.valueOf(after);
    	                 
    	String hql="from BJSCPeriodsInfo where  lotteryTime>=? and lotteryTime<=? and state in (4,7) order by lotteryTime desc";
    	Object[] parameter=new Object[]{bef,af};
         return bjscPeriodsInfoDao.find(hql,parameter);
    }
    
    @Override
    public List<BJSCPeriodsInfo> queryTodayAllPeriods() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//sdf.format();
    	
		java.util.Date now=new java.util.Date();
		String nowStr=sdf.format(now);
		String before=nowStr+" 00:00:00";
		String after=nowStr+" 23:59:59";
    	Date bef=java.sql.Timestamp.valueOf(before);
    	Date af=java.sql.Timestamp.valueOf(after);
    	                 
    	String hql="from BJSCPeriodsInfo where  lotteryTime>=? and lotteryTime<=? order by lotteryTime desc";
    	Object[] parameter=new Object[]{bef,af};
         return bjscPeriodsInfoDao.find(hql,parameter);
    }
    
    @Override
    public List<BJSCPeriodsInfo> queryLastPeriodsForRefer() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yes=DatetimeUtil.yesterday();
        java.util.Date now = new java.util.Date();
        String nowStr = sdf.format(now);       
        String after = nowStr + " 23:59:59";     
        String yesStr=yes+" 22:59:59";
        Date af = java.sql.Timestamp.valueOf(after);
        Date yestoday=java.sql.Timestamp.valueOf(yesStr);
        String hql = "from BJSCPeriodsInfo where  lotteryTime>=? and lotteryTime<=? and state in (4,7) order by lotteryTime desc";
        Object[] parameter = new Object[] {yestoday,  af };
        
        Page<BJSCPeriodsInfo> page=new Page<BJSCPeriodsInfo>();
        page.setPageSize(100);
        page.setPageNo(1);
        Page<BJSCPeriodsInfo> pageResult=bjscPeriodsInfoDao.findPage(page, hql, parameter);
        List<BJSCPeriodsInfo> periodsList=pageResult.getResult();

        
        //List<GDPeriodsInfo> periodsList=periodsInfoDao.find(hql, parameter);
        //if(periodsList.size()>30)
        //	periodsList=periodsList.subList(0, 30);	
        return periodsList;

    }
    
    
    
    public Page<BJSCPeriodsInfo> queryHistoryPeriods(Page<BJSCPeriodsInfo> page)
    {
    	String hql="from BJSCPeriodsInfo where  state in (4,6,7) order by lotteryTime desc";
    	//String hql="from BJSCPeriodsInfo where  state not in (1,2) order by lotteryTime desc";
    	Object[] parameter=new Object[]{};
             return bjscPeriodsInfoDao.findPage(page,hql,parameter);   
    } 
    
    public Page<BJSCPeriodsInfo> queryHistoryPeriodsForBoss(Page<BJSCPeriodsInfo> page)
    {
    	String hql="from BJSCPeriodsInfo where  state not in (1,2) order by lotteryTime desc";
    	Object[] parameter=new Object[]{};
    	return bjscPeriodsInfoDao.findPage(page,hql,parameter);   
    }   
    
    
  
    
    //保存修改广东快乐十分的开奖结果
	@Override
	public void updateLotResult(BJSCPeriodsInfo gdPeriodsInfo) {
		BJSCPeriodsInfo gdp=new BJSCPeriodsInfo();		
		gdp = bjscPeriodsInfoDao.findUniqueBy("periodsNum",gdPeriodsInfo.getPeriodsNum());
		gdp.setResult1(gdPeriodsInfo.getResult1());
		gdp.setResult2(gdPeriodsInfo.getResult2());
		gdp.setResult3(gdPeriodsInfo.getResult3());
		gdp.setResult4(gdPeriodsInfo.getResult4());
		gdp.setResult5(gdPeriodsInfo.getResult5());
		gdp.setResult6(gdPeriodsInfo.getResult6());
		gdp.setResult7(gdPeriodsInfo.getResult7());
		gdp.setResult8(gdPeriodsInfo.getResult8());
		gdp.setResult9(gdPeriodsInfo.getResult9());
		gdp.setResult10(gdPeriodsInfo.getResult10());
		gdp.setState(gdPeriodsInfo.getState());
		
		bjscPeriodsInfoDao.save(gdp);
            //这里增加其他操作
            
            
            
            
	}
	 public BJSCPeriodsInfo queryLastLotteryPeriods()
	 {
		    BJSCPeriodsInfo gdp=null;
		    
	    	String hql = "from BJSCPeriodsInfo where  state in (4,7) and lotteryTime>=? order by lotteryTime desc";   
	    	  String yes=DatetimeUtil.yesterday();	       
	          String yesStr=yes+" 22:59:59"; 
	          Date yestoday=java.sql.Timestamp.valueOf(yesStr);
	    	
	    	 Object[] parameter = new Object[] {yestoday};
	    	List<BJSCPeriodsInfo> cqpList=bjscPeriodsInfoDao.find(hql, parameter);
	    	if(cqpList!=null&&cqpList.size()>0)
	    	{	
	    		gdp=cqpList.get(0);
	    		 cqpList.clear();
	    	}
	         return gdp;
	    
	 }
	 public BJSCPeriodsInfo queryLastNotOpenPeriods()
	 {
		 BJSCPeriodsInfo gdp=null;
		 String hql = "from BJSCPeriodsInfo where  state=1 and openQuotTime>? order by openQuotTime asc";	    	
    	 Object[] parameter = new Object[] {new Date() };
    	 List<BJSCPeriodsInfo> cqpList=bjscPeriodsInfoDao.find(hql, parameter);
    	 if(cqpList!=null&&cqpList.size()>0)
	    		gdp=cqpList.get(0);
    	 return gdp;
		 
	 }
	 private BJSCPeriodsInfo queryLastNeedOpenPeriods(String status)
	    {
		    BJSCPeriodsInfo gdp=null;
		    long times=System.currentTimeMillis();
		    times=times+5000;
		    String hql = "from BJSCPeriodsInfo where  state=?  and stopQuotTime>? and openQuotTime<=? order by openQuotTime asc";
	    	Object[] parameter = new Object[] {status,new Date(),new Date(times) };	    	
	    	List<BJSCPeriodsInfo> cqpList=bjscPeriodsInfoDao.find(hql, parameter);
	    	if(cqpList!=null&&cqpList.size()>0)
	    		gdp=cqpList.get(0);
	         return gdp;
	    }
	 
	 @Override
	    public void updatePeriodsStatusByPeriodsNum(String periodsNum,String status){
		 
		 String hql="update BJSCPeriodsInfo set state=? where periodsNum=? ";
		 bjscPeriodsInfoDao.batchExecute(hql, status,periodsNum);
		 
		
	    }

    @Override
    public BJSCPeriodsInfo queryPeriods(Object periodsNum, Object status) {
        return bjscPeriodsInfoDao.findUnique("from BJSCPeriodsInfo where periodsNum = ? and state != ?", periodsNum,status);
    }

    @Override
    public void update(BJSCPeriodsInfo gdPeriodsInfo) {
        bjscPeriodsInfoDao.update(gdPeriodsInfo);
    }

    @Override
    public List<BJSCPeriodsInfo> queryByBJSCPeriods(String periodsNum, String number) {
        return bjscPeriodsInfoDao.findBy(periodsNum, number);
    }
    
    @Override
    public void deletePeriods(String beginPeriodsNum,String endPeriodsNum){
        String hql = "delete BJSCPeriodsInfo where periodsNum>=? and periodsNum <=?";
        bjscPeriodsInfoDao.batchExecute(hql, beginPeriodsNum,endPeriodsNum);
    }
    
    
    @Override
    public List<BJSCPeriodsInfo> queryReportPeriodsNum() {
    	String hql = "from BJSCPeriodsInfo where  state=7  and lotteryTime <=?   order by lotteryTime desc";       
    	Object[] parameter = new Object[] {new Date()};
    	Page<BJSCPeriodsInfo> page = new Page<BJSCPeriodsInfo>();
    	page.setPageSize(50);
    	page.setPageNo(1);
    	page = bjscPeriodsInfoDao.findPage(page, hql, parameter);
    	return  page.getResult();
    }
    @Override
    public List<BJSCPeriodsInfo> queryBeforeRunPeriodsNumList(Integer row) {
    	Calendar afterTime = Calendar.getInstance();
    	  afterTime.add(Calendar.MINUTE, 5);
    	  Date afterDate = (Date) afterTime.getTime(); 
    	  
        String hql = "from BJSCPeriodsInfo where lotteryTime <=?   order by lotteryTime desc";       
        Object[] parameter = new Object[] {afterDate};
        Page<BJSCPeriodsInfo> page = new Page<BJSCPeriodsInfo>();
        page.setPageSize(row);
        page.setPageNo(1);
        page = bjscPeriodsInfoDao.findPage(page, hql, parameter);
        return  page.getResult();
    }

    public IBJSCPeriodsInfoDao getBjscPeriodsInfoDao() {
		return bjscPeriodsInfoDao;
	}


	public void setBjscPeriodsInfoDao(IBJSCPeriodsInfoDao bjscPeriodsInfoDao) {
		this.bjscPeriodsInfoDao = bjscPeriodsInfoDao;
	}


	@Override
    public List<BJSCPeriodsInfo> queryPeriods(String beginPeriodsNum,
            String endPeriodsNum) {
        String hql = "from BJSCPeriodsInfo where periodsNum>=? and periodsNum <=?";
        Object[] parameter = new Object[] {beginPeriodsNum,endPeriodsNum};
        return bjscPeriodsInfoDao.find(hql, parameter);
    }
    
    @Override
    public BJSCPeriodsInfo queryZhangdanPeriods()
    {
    	BJSCPeriodsInfo gdPeriodsInfo = null;
    	String hql="FROM BJSCPeriodsInfo WHERE lotteryTime>=? AND openQuotTime<=? AND state not in (4,7)";
    	List<BJSCPeriodsInfo> periodsList=bjscPeriodsInfoDao.find(hql, new Date(),new Date());
    	if(periodsList!=null&&periodsList.size()!=0)
    		gdPeriodsInfo = periodsList.get(0);
    	return gdPeriodsInfo;
    	
    }
    private Date getBJOpenDate(Date openDay)
    {
    	SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd");
    	String openStr=sm.format(openDay)+" 09:02:35";
    	Date lastStopDayTime=null;
		try {
			lastStopDayTime = DatetimeUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", openStr);
		} catch (ParseException e) {
			logger.error("BJSC开盘时间解析错误");
		}
    	return lastStopDayTime;
    }
	 
	 public List<BJSCPeriodsInfo> queryExceptionPeriods()
	 {

	    	String hql="FROM BJSCPeriodsInfo WHERE  lotteryTime<=? AND state not in (4,7)";
	    	List<BJSCPeriodsInfo> periodsList=bjscPeriodsInfoDao.find(hql, new Date());
	    	return periodsList;
		 
	 }


	/*@Override
	public Map<String, List<Integer>> getResult() {
		return dataExtractor.getResult();
	}

	@Override
	public Map<String, List<Integer>> getResultByPeriodsNum(String periodsNum) {
		return dataExtractor.getResultByPeriodsNum(periodsNum);
	}*/

	@Override
	public BJSCPeriodsInfo findCurrentStopPeriodInfo() {
		final String hql = "FROM BJSCPeriodsInfo WHERE state=3 ORDER BY periodsNum DESC";
		List<BJSCPeriodsInfo> results = bjscPeriodsInfoDao.find(hql);
		return !CollectionUtils.isEmpty(results) ? results.get(0) : null;
	}

	@Override
	public List<BJSCPeriodsInfo> findAllExceptionPeriodInfo() {
		// 获取状态是5的或者比当前盘期期数小,但状态是1/2/3的数据
		/*final String hql = "FROM BJSCPeriodsInfo WHERE "
				+ "(state=5 OR (periodsNum < (SELECT MAX(periodsNum)-1 FROM  BJSCPeriodsInfo WHERE openQuotTime<= :today AND stopQuotTime >= :today) AND state IN (1,2,3))) "
				+ "AND createTime >= to_date(:todayStr,'yyyyMMdd') ORDER BY periodsNum desc";*/
		final String hql="FROM BJSCPeriodsInfo WHERE state not in (6,7) and lottery_time <= :sysdate ORDER BY periods_num desc";
		Date today = new Date();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sysdate", today);
		//paramMap.put("today", today);
		//paramMap.put("todayStr", new SimpleDateFormat("yyyyMMdd").format(today));
		List<BJSCPeriodsInfo> result = bjscPeriodsInfoDao.find(hql, paramMap);
		//return result.subList(0, result.size() > 10 ? 10 : result.size());
		//return result.subList(0, result.size() > 1 ? 1 : result.size());
		return result;
	}

	/*public DataExtractor getDataExtractor() {
		return dataExtractor;
	}

	public void setDataExtractor(DataExtractor dataExtractor) {
		this.dataExtractor = dataExtractor;
	}*/
	
	@Override
	public List<BJSCPeriodsInfo> queryTodayPeriodsForEOD() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// sdf.format();

		java.util.Date now = new java.util.Date();
		String nowStr = sdf.format(DateUtils.addDays(now, -1));
		String before = nowStr + " 00:00:00";
		String after = nowStr + " 23:59:59";
		Date bef = java.sql.Timestamp.valueOf(before);
		Date af = java.sql.Timestamp.valueOf(after);

		String hql = "from BJSCPeriodsInfo where  lotteryTime>=? and lotteryTime<=? and state in (4,7) order by lotteryTime desc";
		Object[] parameter = new Object[] { bef, af };
		return bjscPeriodsInfoDao.find(hql, parameter);
	}


	@Override
	public Map<String, BJSCPeriodsInfo> getPeriodsInfoFromBetTableForReLotteryEOD(String scheme) {
		Map<String, BJSCPeriodsInfo> resultMap = new TreeMap<String, BJSCPeriodsInfo>();
		List<BJSCPeriodsInfo> resultList = bjscPeriodsInfoDao.getPeriodsInfoFromBetTableForReLotteryEOD(scheme);
		if (!CollectionUtils.isEmpty(resultList)) {
			for (BJSCPeriodsInfo periodsInfo : resultList) {
				resultMap.put(periodsInfo.getPeriodsNum(), periodsInfo);
			}
		}
		return resultMap;
	}
	
	@Override
	public List<BJSCPeriodsInfo> findAllExceptionPeriodInfoByDate(Date queryDate) {
		// 获取状态是5的queryDate盘期
		final String hql = "FROM BJSCPeriodsInfo WHERE state not in (4,6,7) and to_char(openQuotTime,'yyyyMMdd')=:queryDate order by periodsNum desc";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("queryDate", new SimpleDateFormat("yyyyMMdd").format(queryDate));
		List<BJSCPeriodsInfo> result = bjscPeriodsInfoDao.find(hql, paramMap);
		return result;
	}
	@Override
	public BJSCPeriodsInfo findCurrentPeriod() {
		final String hql = "FROM BJSCPeriodsInfo WHERE openQuotTime <= :sysdate and lotteryTime >= :sysdate";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sysdate", new Date());
		List<BJSCPeriodsInfo> result = bjscPeriodsInfoDao.find(hql, paramMap);
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
