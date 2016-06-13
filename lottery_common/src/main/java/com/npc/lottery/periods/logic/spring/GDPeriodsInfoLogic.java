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

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.member.logic.interf.ICheckLogic;
import com.npc.lottery.periods.dao.interf.IGDPeriodsInfoDao;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.util.DatetimeUtil;
import com.npc.lottery.util.Page;
public class GDPeriodsInfoLogic implements IGDPeriodsInfoLogic {

    private Logger logger = Logger.getLogger(GDPeriodsInfoLogic.class);
    private IGDPeriodsInfoDao periodsInfoDao;
    
    //private DataExtractor dataExtractor;
    private ICheckLogic checkLogic;
    private ShopSchemeService shopSchemeService;
    @SuppressWarnings("deprecation")
	@Override
    public void saveGDPeriods() {
        try {
            Date date = new Date();
            SimpleDateFormat ageFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat openFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            String ageDate = null;
            //判断当前月份为11月-次年4月开盘时间为8:30 否则为9:00 
            if(date.getMonth()+1>=11&&date.getMonth()+1<=4){
            	ageDate = ageFormat.format(date) + " 08:30:15";
            }else{
            	ageDate = ageFormat.format(date) + " 09:00:15";
            }
            Date openDate = openFormat.parse(ageDate);
            Calendar sysCalender = Calendar.getInstance();
            Calendar sysCalenderStop = Calendar.getInstance(); // 封盘时间设置
            sysCalender.setTime(openDate);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); // 盘期时间格式
            String dateString = formatter.format(date);
            int periodsCount = 84; // 期数
            int endPerDate = -90; // 提前封盘的时间
            for (int i = 1; i <= periodsCount; i++) {
                GDPeriodsInfo periodsInfo = new GDPeriodsInfo();
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
                    // System.out.println("开盘时间："+openFormat.format(openDate));
                    //logger.info("开盘时间：" + openFormat.format(openDate));
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
                logger.info("盘期" + periodsNum + "-" + "封盘时间"
                        + openFormat.format(stopDate) + "-"
                        + openFormat.format(lotteryDate) + "每一期开奖的时间");
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
                periodsInfoDao.save(periodsInfo);
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public GDPeriodsInfo queryByPeriods(String periodsNum,Object number) {
            return periodsInfoDao.findUniqueBy(periodsNum,number);
    }
    
    @Override
    public Page<GDPeriodsInfo> findPage(Page<GDPeriodsInfo> page,Criterion... criterions) {
        return periodsInfoDao.findPage(page, criterions);
    }
    
    @Override
    public void save(GDPeriodsInfo gdPeriodsInfo) {
            periodsInfoDao.save(gdPeriodsInfo);
    }
    
    @Override
    public List<GDPeriodsInfo> queryAllPeriods() {
        return periodsInfoDao.getAll();
    }

    public IGDPeriodsInfoDao getPeriodsInfoDao() {
        return periodsInfoDao;
    }

    public void setPeriodsInfoDao(IGDPeriodsInfoDao periodsInfoDao) {
        this.periodsInfoDao = periodsInfoDao;
    }

    @Override
    public List<GDPeriodsInfo> queryAllPeriods(Criterion...criterias) {
        return periodsInfoDao.find(criterias);
    }
    @Override
    public GDPeriodsInfo queryByPeriods(Criterion...criterias) {
    	GDPeriodsInfo gdp=null;
    	List<GDPeriodsInfo> periodsList=periodsInfoDao.find(criterias);
    	if(periodsList!=null&&periodsList.size()!=0)
    		gdp=periodsList.get(0);
    	return gdp;
    }
    
    public GDPeriodsInfo queryByPeriodsStatus(String status)
    {
    	GDPeriodsInfo cqp=null;
    	String hql="from GDPeriodsInfo where  state=? and stopQuotTime>=? and openQuotTime<=? order by openQuotTime desc ";
    	List<GDPeriodsInfo> periodsList=periodsInfoDao.find(hql, status,new Date(),new Date());
    	if(periodsList!=null&&periodsList.size()!=0)
    		cqp=periodsList.get(0);
    	return cqp;
    	
    	
    }
    public GDPeriodsInfo queryStopPeriods(String status)
    {
    	GDPeriodsInfo cqp=null;
    	String hql="from GDPeriodsInfo where  state=? and lotteryTime>=? and openQuotTime<=? order by openQuotTime desc ";
    	List<GDPeriodsInfo> periodsList=periodsInfoDao.find(hql, status,new Date(),new Date());
    	if(periodsList!=null&&periodsList.size()!=0)
    		cqp=periodsList.get(0);
    	return cqp;
    	
    }
    /**
     * 改变GD状态值
     */
    @Override
    public void updateCommon(String commonDate){
        GDPeriodsInfo gdPeriodsInfo = new GDPeriodsInfo();
        gdPeriodsInfo =  queryLastNeedOpenPeriods(Constant.NOT_STATUS);
        if(gdPeriodsInfo!=null)
        {
        logger.info("广东开盘 盘期号码："+gdPeriodsInfo.getPeriodsNum());
        gdPeriodsInfo.setState(Constant.OPEN_STATUS);
        periodsInfoDao.save(gdPeriodsInfo);
        }
        else
        	 logger.info("广东开盘无法拿到开盘盘起：");
        	
    }
    @Override
    public void updateGDStatus(){
       
        try {
        	GDPeriodsInfo gdPeriodsInfo = queryLastNeedStopPeriods(Constant.OPEN_STATUS);
            if(gdPeriodsInfo!=null)
            {
            gdPeriodsInfo.setState(Constant.STOP_STATUS);
            logger.info("广东封盘：盘期："+gdPeriodsInfo.getPeriodsNum());
            periodsInfoDao.update(gdPeriodsInfo);
            }
           
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法updateGDStatus时出现错误 "
                    + e.getMessage());
        }
    }
   /* @Override
    public void updateGDStatusWithCheckMiragation(){
    	
		try {
			GDPeriodsInfo gdPeriodsInfo = queryLastNeedStopPeriods(Constant.OPEN_STATUS);
			if (gdPeriodsInfo != null) {
				gdPeriodsInfo.setState(Constant.STOP_STATUS);
				logger.info("广东封盘：盘期：" + gdPeriodsInfo.getPeriodsNum());
				periodsInfoDao.update(gdPeriodsInfo);
				// 获取商铺对应的scheme列表
				Map<String, String> shopSchemeMap = shopSchemeService.getShopSchemeMap();

				// 每一个商铺按每期进去兑奖
				for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
					String scheme = shopScheme.getValue();
					// 迁移广东投注数据
					checkLogic.miragationGDDataToCheck(gdPeriodsInfo.getPeriodsNum(), scheme);
					// 迁移补货数据
				}
			}

		} catch (Exception e) {
    		e.printStackTrace();
    		logger.error("执行" + this.getClass().getSimpleName()
    				+ "中的方法updateGDStatus时出现错误 "
    				+ e.getMessage());
    	}
    }*/
    
    private GDPeriodsInfo queryLastNeedStopPeriods(String status)
    {
        GDPeriodsInfo cdp=null;
        String hql = "from GDPeriodsInfo where  state=?  and lotteryTime >?   order by openQuotTime asc";       
        Object[] parameter = new Object[] { status,new Date()};
        List<GDPeriodsInfo> cqpList=periodsInfoDao.find(hql, parameter);
        if(cqpList!=null&&cqpList.size()>0)
            cdp=cqpList.get(0);
         return cdp;
    }
    
    @Override
    public List<GDPeriodsInfo> queryTodayPeriods() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//sdf.format();
    	
		java.util.Date now=new java.util.Date();
		String nowStr=sdf.format(now);
		String before=nowStr+" 00:00:00";
		String after=nowStr+" 23:59:59";
    	Date bef=java.sql.Timestamp.valueOf(before);
    	Date af=java.sql.Timestamp.valueOf(after);
    	                 
    	String hql="from GDPeriodsInfo where  lotteryTime>=? and lotteryTime<=? and state in (4,7) order by lotteryTime desc";
    	Object[] parameter=new Object[]{bef,af};
         return periodsInfoDao.find(hql,parameter);
    }
    
    @Override
    public List<GDPeriodsInfo> queryTodayAllPeriods() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//sdf.format();
    	
		java.util.Date now=new java.util.Date();
		String nowStr=sdf.format(now);
		String before=nowStr+" 00:00:00";
		String after=nowStr+" 23:59:59";
    	Date bef=java.sql.Timestamp.valueOf(before);
    	Date af=java.sql.Timestamp.valueOf(after);
    	                 
    	String hql="from GDPeriodsInfo where  lotteryTime>=? and lotteryTime<=? order by lotteryTime desc";
    	Object[] parameter=new Object[]{bef,af};
         return periodsInfoDao.find(hql,parameter);
    }
    
    @Override
    public List<GDPeriodsInfo> queryLastPeriodsForRefer() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yes=DatetimeUtil.yesterday();
        java.util.Date now = new java.util.Date();
        String nowStr = sdf.format(now);       
        String after = nowStr + " 23:59:59";     
        String yesStr=yes+" 22:59:59";
        Date af = java.sql.Timestamp.valueOf(after);
        Date yestoday=java.sql.Timestamp.valueOf(yesStr);
        String hql = "from GDPeriodsInfo where  lotteryTime>=? and lotteryTime<=? and state in (4,7) order by lotteryTime desc";
        Object[] parameter = new Object[] {yestoday,  af };
        
        Page<GDPeriodsInfo> page=new Page<GDPeriodsInfo>();
        page.setPageSize(100);
        page.setPageNo(1);
        Page<GDPeriodsInfo> pageResult=periodsInfoDao.findPage(page, hql, parameter);
        List<GDPeriodsInfo> periodsList=pageResult.getResult();

        
        //List<GDPeriodsInfo> periodsList=periodsInfoDao.find(hql, parameter);
        //if(periodsList.size()>30)
        //	periodsList=periodsList.subList(0, 30);	
        return periodsList;

    }
    
    
    
    public Page<GDPeriodsInfo> queryHistoryPeriods(Page<GDPeriodsInfo> page)
    {
    	String hql="from GDPeriodsInfo where  state in (4,6,7) order by lotteryTime desc";
    	//String hql="from GDPeriodsInfo where  state not in (1,2) order by lotteryTime desc";
    	Object[] parameter=new Object[]{};
             return periodsInfoDao.findPage(page,hql,parameter);
    }
    
    public Page<GDPeriodsInfo> queryHistoryPeriodsForBoss(Page<GDPeriodsInfo> page)
    {
    	String hql="from GDPeriodsInfo where  state not in (1,2) order by lotteryTime desc";
    	Object[] parameter=new Object[]{};
    	return periodsInfoDao.findPage(page,hql,parameter);
    }
    
    
    //保存修改广东快乐十分的开奖结果
	@Override
	public void updateLotResult(GDPeriodsInfo gdPeriodsInfo) {
		GDPeriodsInfo gdp=new GDPeriodsInfo();		
		gdp = periodsInfoDao.findUniqueBy("periodsNum",gdPeriodsInfo.getPeriodsNum());
		gdp.setResult1(gdPeriodsInfo.getResult1());
		gdp.setResult2(gdPeriodsInfo.getResult2());
		gdp.setResult3(gdPeriodsInfo.getResult3());
		gdp.setResult4(gdPeriodsInfo.getResult4());
		gdp.setResult5(gdPeriodsInfo.getResult5());
		gdp.setResult6(gdPeriodsInfo.getResult6());
		gdp.setResult7(gdPeriodsInfo.getResult7());
		gdp.setResult8(gdPeriodsInfo.getResult8());
		gdp.setState(gdPeriodsInfo.getState());
		
		periodsInfoDao.save(gdp);
            //这里增加其他操作
            
            
            
            
	}
	 public GDPeriodsInfo queryLastLotteryPeriods()
	 {
		    GDPeriodsInfo gdp=null;
		    
	    	String hql = "from GDPeriodsInfo where  state in (4,7) and lotteryTime>=? order by lotteryTime desc";   
	    	  String yes=DatetimeUtil.yesterday();	       
	          String yesStr=yes+" 22:59:59"; 
	          Date yestoday=java.sql.Timestamp.valueOf(yesStr);
	    	
	    	 Object[] parameter = new Object[] {yestoday};
	    	List<GDPeriodsInfo> cqpList=periodsInfoDao.find(hql, parameter);
	    	if(cqpList!=null&&cqpList.size()>0)
	    	{	
	    		gdp=cqpList.get(0);
	    		 cqpList.clear();
	    	}
	         return gdp;
	    
	 }
	 public GDPeriodsInfo queryLastLotteryPeriods_noTime()
	 {
		 GDPeriodsInfo gdp=null;
		 
		 String hql = "from GDPeriodsInfo where  state in (4,7) order by lotteryTime desc";   
   	
   	     Object[] parameter = new Object[] {};
		 List<GDPeriodsInfo> cqpList=periodsInfoDao.find(hql, parameter);
		 if(cqpList!=null&&cqpList.size()>0)
		 {	
			 gdp=cqpList.get(0);
			 cqpList.clear();
		 }
		 return gdp;
		 
	 }
	 public GDPeriodsInfo queryLastNotOpenPeriods()
	 {
		 GDPeriodsInfo gdp=null;
		 String hql = "from GDPeriodsInfo where  state=1 and openQuotTime>? order by openQuotTime asc";	    	
    	 Object[] parameter = new Object[] {new Date() };
    	 List<GDPeriodsInfo> cqpList=periodsInfoDao.find(hql, parameter);
    	 if(cqpList!=null&&cqpList.size()>0)
	    		gdp=cqpList.get(0);
    	 return gdp;
		 
	 }
	 private GDPeriodsInfo queryLastNeedOpenPeriods(String status)
	    {
		    GDPeriodsInfo gdp=null;
		    String hql = "from GDPeriodsInfo where  state=?  and stopQuotTime>? and openQuotTime<=? order by openQuotTime asc";
	    	Object[] parameter = new Object[] {status,new Date(),new Date() };	    	
	    	List<GDPeriodsInfo> cqpList=periodsInfoDao.find(hql, parameter);
	    	if(cqpList!=null&&cqpList.size()>0)
	    		gdp=cqpList.get(0);
	         return gdp;
	    }
	 
	 @Override
	    public void updatePeriodsStatusByPeriodsNum(String periodsNum,String status){
		 
		 String hql="update GDPeriodsInfo set state=? where periodsNum=? ";
		 periodsInfoDao.batchExecute(hql, status,periodsNum);
		 
		
	    }

    @Override
    public GDPeriodsInfo queryPeriods(Object periodsNum, Object status) {
        return periodsInfoDao.findUnique("from GDPeriodsInfo where periodsNum = ? and state != ?", periodsNum,status);
    }

    @Override
    public void update(GDPeriodsInfo gdPeriodsInfo) {
        periodsInfoDao.update(gdPeriodsInfo);
    }

    @Override
    public List<GDPeriodsInfo> queryByGDPeriods(String periodsNum, String number) {
        return periodsInfoDao.findBy(periodsNum, number);
    }
    
    @Override
    public void deletePeriods(String beginPeriodsNum,String endPeriodsNum){
        String hql = "delete GDPeriodsInfo where periodsNum>=? and periodsNum <=?";
        periodsInfoDao.batchExecute(hql, beginPeriodsNum,endPeriodsNum);
    }
    
    
    @Override
    public List<GDPeriodsInfo> queryReportPeriodsNum() {
    	String hql = "from GDPeriodsInfo where state=7  and  lotteryTime <=?   order by lotteryTime desc";       
    	Object[] parameter = new Object[] {new Date()};
    	Page<GDPeriodsInfo> page = new Page<GDPeriodsInfo>();
    	page.setPageSize(50);
    	page.setPageNo(1);
    	page = periodsInfoDao.findPage(page, hql, parameter);
    	return  page.getResult();
    }
    /**
     * 取当前盘期 和 之前 数据
     * @return
     */
    @Override
    public List<GDPeriodsInfo> queryBeforeRunPeriodsNumList(Integer row) {

    	  Calendar afterTime = Calendar.getInstance();
    	  afterTime.add(Calendar.MINUTE, 10);
    	  Date afterDate = (Date) afterTime.getTime(); 
    	  
        String hql = "from GDPeriodsInfo where lotteryTime <=?   order by lotteryTime desc";       
        Object[] parameter = new Object[] {afterDate};
        Page<GDPeriodsInfo> page = new Page<GDPeriodsInfo>();
        page.setPageSize(row);
        page.setPageNo(1);
        page = periodsInfoDao.findPage(page, hql, parameter);
        return  page.getResult();
    }

    @Override
    public List<GDPeriodsInfo> queryPeriods(String beginPeriodsNum,
            String endPeriodsNum) {
        String hql = "from GDPeriodsInfo where periodsNum>=? and periodsNum <=?";
        Object[] parameter = new Object[] {beginPeriodsNum,endPeriodsNum};
        return periodsInfoDao.find(hql, parameter);
    }
    
    @Override
    public GDPeriodsInfo queryZhangdanPeriods()
    {
    	GDPeriodsInfo gdPeriodsInfo = null;
    	String hql="FROM GDPeriodsInfo WHERE lotteryTime>=? AND openQuotTime<=? AND state not in (4,7)";
    	List<GDPeriodsInfo> periodsList=periodsInfoDao.find(hql, new Date(),new Date());
    	if(periodsList!=null&&periodsList.size()!=0)
    		gdPeriodsInfo = periodsList.get(0);
    	return gdPeriodsInfo;
    	
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
	public GDPeriodsInfo findCurrentStopPeriodInfo(){
		final String hql="FROM GDPeriodsInfo WHERE state=3 ORDER BY periodsNum DESC";
		List<GDPeriodsInfo> results = periodsInfoDao.find(hql);
		return !CollectionUtils.isEmpty(results)?results.get(0):null;
	}
	
	@Override
	public List<GDPeriodsInfo> findAllExceptionPeriodInfo() {
		// 获取状态是5的或者比当前盘期期数小,但状态是1/2/3的数据
		/*final String hql = "FROM GDPeriodsInfo WHERE "
				+ "(state=5 OR (periodsNum < (SELECT MAX(periodsNum)-1 FROM  GDPeriodsInfo WHERE openQuotTime< :today AND lotteryTime >= :today) AND state IN (1,2,3))) "
				+ "AND lotteryTime >= to_date(:todayStr,'yyyyMMdd') ORDER BY periodsNum desc";*/
		
		final String hql="FROM GDPeriodsInfo WHERE state not in (6,7) and lottery_time <= :sysdate ORDER BY periods_num desc";
		Date today = new Date();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sysdate", today);
		//paramMap.put("today", today);
		//paramMap.put("todayStr", new SimpleDateFormat("yyyyMMdd").format(today));
		List<GDPeriodsInfo> result = periodsInfoDao.find(hql, paramMap);
		//return result.subList(0, result.size() > 10 ? 10 : result.size());
		//return result.subList(0, result.size() > 1 ? 1 : result.size());
		return result;
	}

	@Override
	public Map<String, GDPeriodsInfo> getPeriodsInfoFromBetTableForReLotteryEOD(String scheme) {
		Map<String, GDPeriodsInfo> resultMap = new TreeMap<String, GDPeriodsInfo>();
		List<GDPeriodsInfo> resultList = periodsInfoDao.getPeriodsInfoFromBetTableForReLotteryEOD(scheme);
		if (!CollectionUtils.isEmpty(resultList)) {
			for (GDPeriodsInfo periodsInfo : resultList) {
				resultMap.put(periodsInfo.getPeriodsNum(), periodsInfo);
			}
		}
		return resultMap;
	}
	
	@Override
	public List<GDPeriodsInfo> findAllExceptionPeriodInfoByDate(Date queryDate) {
		// 获取状态是5的queryDate盘期
		final String hql = "FROM GDPeriodsInfo WHERE state not in (4,6,7) and to_char(openQuotTime,'yyyyMMdd')=:queryDate order by periodsNum DESC";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("queryDate", new SimpleDateFormat("yyyyMMdd").format(queryDate));
		List<GDPeriodsInfo> result = periodsInfoDao.find(hql, paramMap);
		return result;
	}
	
	public GDPeriodsInfo findCurrentPeriod() {
		final String hql = "FROM GDPeriodsInfo WHERE openQuotTime <= :sysdate and lotteryTime >= :sysdate";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sysdate", new Date());
		List<GDPeriodsInfo> result = periodsInfoDao.find(hql, paramMap);
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
