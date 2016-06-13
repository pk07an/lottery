package com.npc.lottery.boss.logic.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;

import com.npc.lottery.boss.logic.interf.IBJSCPeriodsInfoBossLogic;
import com.npc.lottery.common.Constant;
import com.npc.lottery.periods.dao.interf.IBJSCPeriodsInfoDao;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.util.DatetimeUtil;
import com.npc.lottery.util.Page;
public class BJSCPeriodsInfoBossLogic implements IBJSCPeriodsInfoBossLogic {

    private Logger logger = Logger.getLogger(BJSCPeriodsInfoBossLogic.class);
    private IBJSCPeriodsInfoDao bjscPeriodsInfoDao = null;

    @Override
    public void saveBJSCPeriods() {
        try {
        	int referPeriod=346710;
        	String referDate="2013-02-21";
        	SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd");      	
        	Date now=new Date();
        	int daysGap=getDaysBetween(referDate,sm.format(now));
            int startPeriodNum=referPeriod+179*daysGap;
        	Date openQuotTime=getBJOpenDate(new Date());
        	saveBJSCPeriods(Integer.valueOf(startPeriodNum).toString(),openQuotTime,179);
            
        	
        } catch (ParseException e2) {
            e2.printStackTrace();
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
    
    public Page<BJSCPeriodsInfo> queryHistoryPeriodsForBoss(Page<BJSCPeriodsInfo> page,String state)
    {
    	String para = "";
    	if("7".equals(state)){
    		para = " where  state in (4,6,7) ";
    	}else if("5".equals(state)){
    		para = " where  state in (5) ";
    	}
    	String hql="from BJSCPeriodsInfo "+para+" order by lotteryTime desc";
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
    	String openStr=sm.format(openDay)+" 09:03:20";
    	Date lastStopDayTime=null;
		try {
			lastStopDayTime = DatetimeUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", openStr);
		} catch (ParseException e) {
			logger.error("BJSC开盘时间解析错误");
		}
    	return lastStopDayTime;
    }
	 private Date getBJStopDate(Date stopDay)
	 {
		 SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd");
	    	String openStr=sm.format(stopDay)+" 23:57:30";
	    	Date lastStopDayTime=null;
			try {
				lastStopDayTime = DatetimeUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", openStr);
			} catch (ParseException e) {
				logger.error("BJSC每天最后一期时间解析错误");
			}
	    	return lastStopDayTime;
	 }
	 public static void main(String[] args) throws ParseException {
		 int referPeriod=330958;
     	String referDate="2012-11-18";
     	SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd");      	
     	Date now=new Date();
     	int daysGap=getDaysBetween(referDate,sm.format(now));
     	System.out.println(daysGap);
         int startPeriodNum=referPeriod+179*daysGap;
     	
     	System.out.println(startPeriodNum);
	}
	 public static int getDaysBetween (String beginDate, String endDate) throws ParseException { 
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		 Date bDate = format.parse(beginDate); 
		 Date eDate = format.parse(endDate); 
		 Calendar d1 = new GregorianCalendar(); 
		 d1.setTime(bDate); 
		 Calendar d2 = new GregorianCalendar(); 
		 d2.setTime(eDate); 
		   int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR); 
		   int y2 = d2.get(Calendar.YEAR); 
		   if (d1.get(Calendar.YEAR) != y2) { 
		   d1 = (Calendar) d1.clone(); 
		   do { 
		   days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数 
		   d1.add(Calendar.YEAR, 1); 
		   } while (d1.get(Calendar.YEAR) != y2); 
		    
		   } 
		   return days; 
		    
		   }
	 
	 public List<BJSCPeriodsInfo> queryExceptionPeriods()
	 {

	    	String hql="FROM BJSCPeriodsInfo WHERE  lotteryTime<=? AND state not in (4,7)";
	    	List<BJSCPeriodsInfo> periodsList=bjscPeriodsInfoDao.find(hql, new Date());
	    	return periodsList;
		 
	 }
}
