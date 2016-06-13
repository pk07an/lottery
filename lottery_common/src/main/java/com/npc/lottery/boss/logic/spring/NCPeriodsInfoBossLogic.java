package com.npc.lottery.boss.logic.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.npc.lottery.boss.logic.interf.INCPeriodsInfoBossLogic;
import com.npc.lottery.common.Constant;
import com.npc.lottery.periods.dao.interf.INCPeriodsInfoDao;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.INCPeriodsInfoLogic;
import com.npc.lottery.util.DatetimeUtil;
import com.npc.lottery.util.Page;
public class NCPeriodsInfoBossLogic implements INCPeriodsInfoBossLogic {

    private Logger logger = Logger.getLogger(NCPeriodsInfoBossLogic.class);
    private INCPeriodsInfoDao ncPeriodsInfoDao;
    
    //private DataExtractor dataExtractor;

    @Override
    public void saveNCPeriods() {
        try {
            Date date = new Date();
            SimpleDateFormat ageFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat openFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            String ageDate = ageFormat.format(date) + " 09:00:00";
            Date openDate = openFormat.parse(ageDate);
            Calendar sysCalender = Calendar.getInstance();
            Calendar sysCalenderStop = Calendar.getInstance(); // 封盘时间设置
            sysCalender.setTime(openDate);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); // 盘期时间格式
            String dateString = formatter.format(date);
            int periodsCount = 84; // 期数
            int endPerDate = -90; // 提前封盘的时间
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
                ncPeriodsInfoDao.save(periodsInfo);
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public NCPeriodsInfo queryByPeriods(String periodsNum,Object number) {
            return ncPeriodsInfoDao.findUniqueBy(periodsNum,number);
    }
    
    @Override
    public Page<NCPeriodsInfo> findPage(Page<NCPeriodsInfo> page,Criterion... criterions) {
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

    public INCPeriodsInfoDao getncPeriodsInfoDao() {
        return ncPeriodsInfoDao;
    }

    public void setncPeriodsInfoDao(INCPeriodsInfoDao ncPeriodsInfoDao) {
        this.ncPeriodsInfoDao = ncPeriodsInfoDao;
    }

    @Override
    public List<NCPeriodsInfo> queryAllPeriods(Criterion...criterias) {
        return ncPeriodsInfoDao.find(criterias);
    }
    @Override
    public NCPeriodsInfo queryByPeriods(Criterion...criterias) {
    	NCPeriodsInfo NCp=null;
    	List<NCPeriodsInfo> periodsList=ncPeriodsInfoDao.find(criterias);
    	if(periodsList!=null&&periodsList.size()!=0)
    		NCp=periodsList.get(0);
    	return NCp;
    }
    
    public NCPeriodsInfo queryByPeriodsStatus(String status)
    {
    	NCPeriodsInfo cqp=null;
    	String hql="from NCPeriodsInfo where  state=? and stopQuotTime>=? and openQuotTime<=? order by openQuotTime desc ";
    	List<NCPeriodsInfo> periodsList=ncPeriodsInfoDao.find(hql, status,new Date(),new Date());
    	if(periodsList!=null&&periodsList.size()!=0)
    		cqp=periodsList.get(0);
    	return cqp;
    	
    	
    }
    public NCPeriodsInfo queryStopPeriods(String status)
    {
    	NCPeriodsInfo cqp=null;
    	String hql="from NCPeriodsInfo where  state=? and lotteryTime>=? and openQuotTime<=? order by openQuotTime desc ";
    	List<NCPeriodsInfo> periodsList=ncPeriodsInfoDao.find(hql, status,new Date(),new Date());
    	if(periodsList!=null&&periodsList.size()!=0)
    		cqp=periodsList.get(0);
    	return cqp;
    	
    }
    /**
     * 改变NC状态值
     */
    @Override
    public void updateCommon(String commonDate){
        NCPeriodsInfo NCPeriodsInfo = new NCPeriodsInfo();
        NCPeriodsInfo =  queryLastNeedOpenPeriods(Constant.NOT_STATUS);
        if(NCPeriodsInfo!=null)
        {
        logger.info("广东开盘 盘期号码："+NCPeriodsInfo.getPeriodsNum());
        NCPeriodsInfo.setState(Constant.OPEN_STATUS);
        ncPeriodsInfoDao.save(NCPeriodsInfo);
        }
        else
        	 logger.info("广东开盘无法拿到开盘盘起：");
        	
    }
    @Override
    public void updateNCStatus(){
       
        try {
        	NCPeriodsInfo NCPeriodsInfo = queryLastNeedStopPeriods(Constant.OPEN_STATUS);
            if(NCPeriodsInfo!=null)
            {
            NCPeriodsInfo.setState(Constant.STOP_STATUS);
            logger.info("广东封盘：盘期："+NCPeriodsInfo.getPeriodsNum());
            ncPeriodsInfoDao.update(NCPeriodsInfo);
            }
           
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行" + this.getClass().getSimpleName()
                    + "中的方法updateNCStatus时出现错误 "
                    + e.getMessage());
        }
    }
    
    private NCPeriodsInfo queryLastNeedStopPeriods(String status)
    {
        NCPeriodsInfo cdp=null;
        String hql = "from NCPeriodsInfo where  state=?  and lotteryTime >?   order by openQuotTime asc";       
        Object[] parameter = new Object[] { status,new Date()};
        List<NCPeriodsInfo> cqpList=ncPeriodsInfoDao.find(hql, parameter);
        if(cqpList!=null&&cqpList.size()>0)
            cdp=cqpList.get(0);
         return cdp;
    }
    
    @Override
    public List<NCPeriodsInfo> queryTodayPeriods() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//sdf.format();
    	
		java.util.Date now=new java.util.Date();
		String nowStr=sdf.format(now);
		String before=nowStr+" 00:00:00";
		String after=nowStr+" 23:59:59";
    	Date bef=java.sql.Timestamp.valueOf(before);
    	Date af=java.sql.Timestamp.valueOf(after);
    	                 
    	String hql="from NCPeriodsInfo where  lotteryTime>=? and lotteryTime<=? and state in (4,7) order by lotteryTime desc";
    	Object[] parameter=new Object[]{bef,af};
         return ncPeriodsInfoDao.find(hql,parameter);
    }
    
    @Override
    public List<NCPeriodsInfo> queryTodayAllPeriods() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//sdf.format();
    	
		java.util.Date now=new java.util.Date();
		String nowStr=sdf.format(now);
		String before=nowStr+" 00:00:00";
		String after=nowStr+" 23:59:59";
    	Date bef=java.sql.Timestamp.valueOf(before);
    	Date af=java.sql.Timestamp.valueOf(after);
    	                 
    	String hql="from NCPeriodsInfo where  lotteryTime>=? and lotteryTime<=? order by lotteryTime desc";
    	Object[] parameter=new Object[]{bef,af};
         return ncPeriodsInfoDao.find(hql,parameter);
    }
    
    @Override
    public List<NCPeriodsInfo> queryLastPeriodsForRefer() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yes=DatetimeUtil.yesterday();
        java.util.Date now = new java.util.Date();
        String nowStr = sdf.format(now);       
        String after = nowStr + " 23:59:59";     
        String yesStr=yes+" 22:59:59";
        Date af = java.sql.Timestamp.valueOf(after);
        Date yestoday=java.sql.Timestamp.valueOf(yesStr);
        String hql = "from NCPeriodsInfo where  lotteryTime>=? and lotteryTime<=? and state in (4,7) order by lotteryTime desc";
        Object[] parameter = new Object[] {yestoday,  af };
        
        Page<NCPeriodsInfo> page=new Page<NCPeriodsInfo>();
        page.setPageSize(100);
        page.setPageNo(1);
        Page<NCPeriodsInfo> pageResult=ncPeriodsInfoDao.findPage(page, hql, parameter);
        List<NCPeriodsInfo> periodsList=pageResult.getResult();

        
        //List<NCPeriodsInfo> periodsList=ncPeriodsInfoDao.find(hql, parameter);
        //if(periodsList.size()>30)
        //	periodsList=periodsList.subList(0, 30);	
        return periodsList;

    }
    
    
    
    public Page<NCPeriodsInfo> queryHistoryPeriods(Page<NCPeriodsInfo> page)
    {
    	String hql="from NCPeriodsInfo where  state in (4,6,7) order by lotteryTime desc";
    	//String hql="from NCPeriodsInfo where  state not in (1,2) order by lotteryTime desc";
    	Object[] parameter=new Object[]{};
             return ncPeriodsInfoDao.findPage(page,hql,parameter);
    }
    
    public Page<NCPeriodsInfo> queryHistoryPeriodsForBoss(Page<NCPeriodsInfo> page,String state)
    {
    	String para = "";
    	if("7".equals(state)){
    		para = " where  state in (4,6,7) ";
    	}else if("5".equals(state)){
    		para = " where  state in (5) ";
    	}
    	String hql="from NCPeriodsInfo "+para+" order by lotteryTime desc";
    	Object[] parameter=new Object[]{};
    	return ncPeriodsInfoDao.findPage(page,hql,parameter);
    }
    
    
    public static void main(String[] args) {
       // new NCPeriodsInfoLogic().queryCommon("openQuotTime");
    	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:com/npc/lottery/periods/springPeriods.xml");
    	INCPeriodsInfoLogic test = (INCPeriodsInfoLogic)context.getBean("periodsInfoLogic");
    	//System.out.println(test.getResult());
    }
    
    //保存修改广东快乐十分的开奖结果
	@Override
	public void updateLotResult(NCPeriodsInfo NCPeriodsInfo) {
		NCPeriodsInfo NCp=new NCPeriodsInfo();		
		NCp = ncPeriodsInfoDao.findUniqueBy("periodsNum",NCPeriodsInfo.getPeriodsNum());
		NCp.setResult1(NCPeriodsInfo.getResult1());
		NCp.setResult2(NCPeriodsInfo.getResult2());
		NCp.setResult3(NCPeriodsInfo.getResult3());
		NCp.setResult4(NCPeriodsInfo.getResult4());
		NCp.setResult5(NCPeriodsInfo.getResult5());
		NCp.setResult6(NCPeriodsInfo.getResult6());
		NCp.setResult7(NCPeriodsInfo.getResult7());
		NCp.setResult8(NCPeriodsInfo.getResult8());
		NCp.setState(NCPeriodsInfo.getState());
		
		ncPeriodsInfoDao.save(NCp);
            //这里增加其他操作
            
            
            
            
	}
	 public NCPeriodsInfo queryLastLotteryPeriods()
	 {
		    NCPeriodsInfo NCp=null;
		    
	    	String hql = "from NCPeriodsInfo where  state in (4,7) and lotteryTime>=? order by lotteryTime desc";   
	    	  String yes=DatetimeUtil.yesterday();	       
	          String yesStr=yes+" 22:59:59"; 
	          Date yestoday=java.sql.Timestamp.valueOf(yesStr);
	    	
	    	 Object[] parameter = new Object[] {yestoday};
	    	List<NCPeriodsInfo> cqpList=ncPeriodsInfoDao.find(hql, parameter);
	    	if(cqpList!=null&&cqpList.size()>0)
	    	{	
	    		NCp=cqpList.get(0);
	    		 cqpList.clear();
	    	}
	         return NCp;
	    
	 }
	 public NCPeriodsInfo queryLastLotteryPeriods_noTime()
	 {
		 NCPeriodsInfo NCp=null;
		 
		 String hql = "from NCPeriodsInfo where  state in (4,7) order by lotteryTime desc";   
   	
   	     Object[] parameter = new Object[] {};
		 List<NCPeriodsInfo> cqpList=ncPeriodsInfoDao.find(hql, parameter);
		 if(cqpList!=null&&cqpList.size()>0)
		 {	
			 NCp=cqpList.get(0);
			 cqpList.clear();
		 }
		 return NCp;
		 
	 }
	 public NCPeriodsInfo queryLastNotOpenPeriods()
	 {
		 NCPeriodsInfo NCp=null;
		 String hql = "from NCPeriodsInfo where  state=1 and openQuotTime>? order by openQuotTime asc";	    	
    	 Object[] parameter = new Object[] {new Date() };
    	 List<NCPeriodsInfo> cqpList=ncPeriodsInfoDao.find(hql, parameter);
    	 if(cqpList!=null&&cqpList.size()>0)
	    		NCp=cqpList.get(0);
    	 return NCp;
		 
	 }
	 private NCPeriodsInfo queryLastNeedOpenPeriods(String status)
	    {
		    NCPeriodsInfo NCp=null;
		    String hql = "from NCPeriodsInfo where  state=?  and stopQuotTime>? and openQuotTime<=? order by openQuotTime asc";
	    	Object[] parameter = new Object[] {status,new Date(),new Date() };	    	
	    	List<NCPeriodsInfo> cqpList=ncPeriodsInfoDao.find(hql, parameter);
	    	if(cqpList!=null&&cqpList.size()>0)
	    		NCp=cqpList.get(0);
	         return NCp;
	    }
	 
	 @Override
	    public void updatePeriodsStatusByPeriodsNum(String periodsNum,String status){
		 
		 String hql="update NCPeriodsInfo set state=? where periodsNum=? ";
		 ncPeriodsInfoDao.batchExecute(hql, status,periodsNum);
		 
		
	    }

    @Override
    public NCPeriodsInfo queryPeriods(Object periodsNum, Object status) {
        return ncPeriodsInfoDao.findUnique("from NCPeriodsInfo where periodsNum = ? and state != ?", periodsNum,status);
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
    public void deletePeriods(String beginPeriodsNum,String endPeriodsNum){
        String hql = "delete NCPeriodsInfo where periodsNum>=? and periodsNum <=?";
        ncPeriodsInfoDao.batchExecute(hql, beginPeriodsNum,endPeriodsNum);
    }
    
    
    @Override
    public List<NCPeriodsInfo> queryReportPeriodsNum() {
    	String hql = "from NCPeriodsInfo where state=7  and  lotteryTime <=?   order by lotteryTime desc";       
    	Object[] parameter = new Object[] {new Date()};
    	Page<NCPeriodsInfo> page = new Page<NCPeriodsInfo>();
    	page.setPageSize(50);
    	page.setPageNo(1);
    	page = ncPeriodsInfoDao.findPage(page, hql, parameter);
    	return  page.getResult();
    }
    /**
     * 取当前盘期 和 之前 数据
     * @return
     */
    @Override
    public List<NCPeriodsInfo> queryBeforeRunPeriodsNumList(Integer row) {

    	  Calendar afterTime = Calendar.getInstance();
    	  afterTime.add(Calendar.MINUTE, 10);
    	  Date afterDate = (Date) afterTime.getTime(); 
    	  
        String hql = "from NCPeriodsInfo where lotteryTime <=?   order by lotteryTime desc";       
        Object[] parameter = new Object[] {afterDate};
        Page<NCPeriodsInfo> page = new Page<NCPeriodsInfo>();
        page.setPageSize(row);
        page.setPageNo(1);
        page = ncPeriodsInfoDao.findPage(page, hql, parameter);
        return  page.getResult();
    }

    @Override
    public List<NCPeriodsInfo> queryPeriods(String beginPeriodsNum,
            String endPeriodsNum) {
        String hql = "from NCPeriodsInfo where periodsNum>=? and periodsNum <=?";
        Object[] parameter = new Object[] {beginPeriodsNum,endPeriodsNum};
        return ncPeriodsInfoDao.find(hql, parameter);
    }
    
    @Override
    public NCPeriodsInfo queryZhanNCanPeriods()
    {
    	NCPeriodsInfo NCPeriodsInfo = null;
    	String hql="FROM NCPeriodsInfo WHERE lotteryTime>=? AND openQuotTime<=? AND state not in (4,7)";
    	List<NCPeriodsInfo> periodsList=ncPeriodsInfoDao.find(hql, new Date(),new Date());
    	if(periodsList!=null&&periodsList.size()!=0)
    		NCPeriodsInfo = periodsList.get(0);
    	return NCPeriodsInfo;
    	
    }

    /**
     * to do
     */
	@Override
	public Map<String, List<Integer>> getResult() {
	    return new HashMap<String, List<Integer>>();
		//return dataExtractor.getResult();
	}

	/*public DataExtractor getDataExtractor() {
		return dataExtractor;
	}

	public void setDataExtractor(DataExtractor dataExtractor) {
		this.dataExtractor = dataExtractor;
	}*/
}
