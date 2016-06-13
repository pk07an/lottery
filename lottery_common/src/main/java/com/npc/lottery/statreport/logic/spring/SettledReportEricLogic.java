package com.npc.lottery.statreport.logic.spring;

import java.math.BigDecimal;
import java.nio.channels.SeekableByteChannel;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.core.task.TaskExecutor;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.service.BaseLogic;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.replenish.vo.DetailVO;
import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.statreport.dao.hibernate.ClassReportRListDao;
import com.npc.lottery.statreport.dao.interf.IClassReportEricDao;
import com.npc.lottery.statreport.dao.interf.IClassReportPetListDao;
import com.npc.lottery.statreport.dao.interf.IClassReportPetPeriodDao;
import com.npc.lottery.statreport.dao.interf.IClassReportRListDao;
import com.npc.lottery.statreport.dao.interf.IClassReportRPeriodDao;
import com.npc.lottery.statreport.dao.interf.IReportStatusDao;
import com.npc.lottery.statreport.dao.interf.ISettledReportEricDao;
import com.npc.lottery.statreport.dao.interf.ISettledReportPetListDao;
import com.npc.lottery.statreport.dao.interf.ISettledReportPetPeriodDao;
import com.npc.lottery.statreport.dao.interf.ISettledReportRListDao;
import com.npc.lottery.statreport.dao.interf.ISettledReportRPeriodDao;
import com.npc.lottery.statreport.entity.ClassReportEric;
import com.npc.lottery.statreport.entity.ClassReportPetList;
import com.npc.lottery.statreport.entity.ClassReportPetPeriod;
import com.npc.lottery.statreport.entity.ClassReportRList;
import com.npc.lottery.statreport.entity.ClassReportRPeriod;
import com.npc.lottery.statreport.entity.DeliveryReportEric;
import com.npc.lottery.statreport.entity.DeliveryReportPetList;
import com.npc.lottery.statreport.entity.DeliveryReportPetPeriod;
import com.npc.lottery.statreport.entity.DeliveryReportRList;
import com.npc.lottery.statreport.entity.DeliveryReportRPeriod;
import com.npc.lottery.statreport.entity.ReportStatus;
import com.npc.lottery.statreport.logic.interf.IClassReportEricLogic;
import com.npc.lottery.statreport.logic.interf.IReportStatusLogic;
import com.npc.lottery.statreport.logic.interf.ISettledReportEricLogic;
import com.npc.lottery.statreport.vo.TopRightVO;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;
import com.npc.lottery.util.Page;

/**
 * 报表统计相关的逻辑处理类
 * 
 */
public class SettledReportEricLogic extends BaseLogic implements ISettledReportEricLogic{
	private static Logger LOG = Logger.getLogger(SettledReportEricLogic.class);

    private ISubAccountInfoLogic subAccountInfoLogic;//子账号
    private IReplenishLogic replenishLogic;
    private IReportStatusLogic reportStatusLogic;
    private TaskExecutor wcpTaskExecutor;//异步执行
    
    private ISettledReportEricDao settledReportEricDao;
    private ISettledReportPetListDao settledReportPetListDao;
    private IClassReportEricDao classReportEricDao;
    private IClassReportPetListDao classReportPetListDao;
    private IClassReportRListDao classReportRListDao;
    private ISettledReportRListDao settledReportRListDao;
    private ISettledReportPetPeriodDao settledReportPetPeriodDao;
    private ISettledReportRPeriodDao settledReportRPeriodDao;
    private IClassReportPetPeriodDao classReportPetPeriodDao;
    private IClassReportRPeriodDao classReportRPeriodDao;

    /**
     * 管理后台用旧方法和在总管后台手动生成交收报表时都是用这个方法
     */
	@Override
	public List<DeliveryReportEric> findSettledReport(Date startDate,Date endDate, String lotteryType, String playType,String periodNum, 
			Long userid, String userType,String[] scanTableList,String winState,String schema) {
		List<DeliveryReportEric> result = new ArrayList<DeliveryReportEric>();
		
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(userid);
		userInfo.setUserType(userType);
		UserVO vo = replenishLogic.getUserType(userInfo);
		
		String underLineRateColumn = ""; //下级的占成字段组合
		if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
			underLineRateColumn = " (rate_branch + RATE_STOCKHOLDER + RATE_GEN_AGENT + RATE_AGENT)/100 ";
			vo.setOutCommissionUser("COMMISSION_BRANCH");
		}else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType)){
			underLineRateColumn = " (RATE_STOCKHOLDER + RATE_GEN_AGENT + RATE_AGENT)/100 ";
		}else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType)){
			underLineRateColumn = " (RATE_GEN_AGENT + RATE_AGENT)/100 ";
		}else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType)){
			underLineRateColumn = " RATE_AGENT/100 ";
		}
		
		result = settledReportEricDao.querySettledReport(startDate, endDate, lotteryType, playType, periodNum, userid, userType,
				vo.getUserType(),vo.getRateUser(),vo.getNextRateColumn(), vo.getOutCommissionUser(),vo.getNextColumn(),
				scanTableList,underLineRateColumn,vo.getCommissionUser(),winState,schema);		
		
		return result;
	}
	@Override
	public List<DeliveryReportEric> findSettledReportMerge(Date startDate,Date endDate, Date nowDate, Long userid, String userType) {
		
		List<DeliveryReportEric> result = settledReportEricDao.querySettledReportMerge(startDate, endDate, nowDate, userid, userType);		
		
		return result;
	}
	
	@Override
	public List<TopRightVO> findDetailGroupByDate(Date startDate,Date endDate,String playType,String periodNum, Long userId,String[] scanTableList) {
		return settledReportEricDao.queryBetDetailGroupByDate(userId, startDate,endDate, periodNum, playType, scanTableList);
	}
	
	@Override
	public List<TopRightVO> findDetailGroupByDateForReplenish(Date startDate,Date endDate,String playType,String periodNum, Long userId,String userType) {
		return settledReportEricDao.queryBetDetailGroupByDateForReplnish(userId, startDate,endDate, periodNum, playType,userType);
	}
	
	@Override
	public Page findDetail(Page page,Date startDate,Date endDate,String playType,String periodNum, Long userId,String userType,String[] scanTableList,Long currentUserId) {
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(currentUserId);
		userInfo.setUserType(userType);
		UserVO vo = replenishLogic.getUserType(userInfo);
		if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
			vo.setOutCommissionUser("COMMISSION_BRANCH");
		}
		return settledReportEricDao.queryBetDetail(page, userId, startDate, endDate, periodNum, playType,scanTableList,vo.getRateUser(),vo.getCommissionUser(),vo.getOutCommissionUser());
	}
	
	@Override
	public Page findReplenishDetail(Page page,Date startDate,Date endDate,String periodNum, Long userId,String userType,String currentUserType,String commissionTypeCode,String lotteryType,Long currentUserId) {
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(currentUserId);
		userInfo.setUserType(currentUserType);
		UserVO userVo = replenishLogic.getUserType(userInfo);
		
		//如果是本级查本级的，就拿自己的拥金字段，否则就取下级的拥金字段
		String commissionColumn = userType.equals(currentUserType)?userVo.getOutCommissionUser():userVo.getCommissionUser();
		
		Page pageResult = settledReportEricDao.queryReplenishOutDetail(page, userId, commissionTypeCode, periodNum, userType, currentUserType,
				startDate, endDate, lotteryType,userVo.getRateUser(),commissionColumn);
		if(pageResult.getResult().size()>0){
			//那个补货退水的显示，实际上就是本级查看自己的补货时，显示上一级的退水，上级查看时，显示100.0
			for(int i=0;i<pageResult.getResult().size();i++){
				DetailVO vo = (DetailVO)(pageResult.getResult().get(i));
				if(ManagerStaff.USER_TYPE_CHIEF.equals(vo.getUserType()) || ManagerStaff.USER_TYPE_OUT_REPLENISH.equals(vo.getUserType())){
					vo.setChiefRate(BigDecimal.valueOf(-100));
					vo.setChiefCommission(vo.getChiefCommission());
					vo.setWhoReplenish("總監出貨");
				}
				if(ManagerStaff.USER_TYPE_BRANCH.equals(vo.getUserType())){
					vo.setBranchRate(BigDecimal.valueOf(-100));
					if(currentUserType.equals(vo.getUserType())){
						vo.setBranchCommission(vo.getChiefCommission());
					}
					vo.setWhoReplenish("分公司走飛");
				}
				if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(vo.getUserType())){
					vo.setStockRate(BigDecimal.valueOf(-100));
					if(currentUserType.equals(vo.getUserType())){
						vo.setStockCommission(vo.getBranchCommission());
					}
					vo.setWhoReplenish("股東走飛");
				}
				if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(vo.getUserType())){
					vo.setGenAgentRate(BigDecimal.valueOf(-100));
					if(currentUserType.equals(vo.getUserType())){
						vo.setGenAgentCommission(vo.getStockCommission());
					}
					vo.setWhoReplenish("總代理走飛");
				}
				if(ManagerStaff.USER_TYPE_AGENT.equals(vo.getUserType())){
					vo.setAgentRate(BigDecimal.valueOf(-100));
					if(currentUserType.equals(vo.getUserType())){
						vo.setAgentCommission(vo.getGenAgentCommission());
					}
					vo.setWhoReplenish("代理走飛");
				}
			}
		}
		return pageResult;
	}
	
	@Override
	public List<DeliveryReportEric> queryReplenish(Date startDate, Date endDate, Long userID,String userType,String typeCode, String periodsNum,
			String lotteryType,String winState,String tableName,String schema){
		List<DeliveryReportEric> list = new ArrayList<DeliveryReportEric>();
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(userID);
		userInfo.setUserType(userType);
		UserVO vo = replenishLogic.getUserType(userInfo);
		list = settledReportEricDao.queryReplenish(startDate, endDate, userID, typeCode, periodsNum,
				vo.getRateUser(),lotteryType,userType,vo.getOutCommissionUser(),winState,tableName,schema);
		return list;
		
	}
	@Override
	public List<DeliveryReportEric> queryReplenishMerge(Date startDate, Date endDate,Date nowDate, Long userID,String userType){
		List<DeliveryReportEric> list = settledReportEricDao.queryReplenishMerge(startDate, endDate, nowDate, userID, userType);
		return list;
		
	}
	
	@Override
	public Double queryTodayWinLose(Long userID, String userType,String lotteryType, String playType, String periodNum,
			Date startDate,Date endDate,String schema) {
		//处理投注和补进的START-------------------
		/*List<DeliveryReportEric> resultList = settledReportEricDao.querySettledReport(startDate, endDate, lotteryTypeFrm, vPlayType, periodNum, userID, userType,
				userVO.getUserType(),userVO.getRateUser(),userVO.getNextRateColumn(), userVO.getOutCommissionUser(),userVO.getNextColumn(),
				scanTableList,underLineRateColumn,userVO.getCommissionUser(),winState)*/
        List<DeliveryReportEric> resultList = settledReportEricDao.queryDeliveryReportPetPeriod(userID,userType, startDate, endDate,null,null,schema);
		//处理投注和补进的END-------------------
		
		//处理补出的START-------------------
		//查询补出货信息
        DeliveryReportEric replenishEntity =  null;
        /*List<DeliveryReportEric> list = settledReportEricDao.queryReplenish(startDate, endDate, userID, vPlayType, periodNum,
        		userVO.getRateUser(),lotteryTypeFrm,userType,userVO.getOutCommissionUser(),winState,replenishTableName)*/
        List<DeliveryReportEric> list = settledReportEricDao.queryDeliveryReportRPeriod(userID, userType,startDate, endDate,null,null,schema);
        
        if(!list.isEmpty()){
        	replenishEntity= new DeliveryReportEric();
        	replenishEntity = (DeliveryReportEric) list.get(0);
        	replenishEntity.setUserType(userType);
        }
        
        //合计数据
        DeliveryReportEric totalEntity = null;
        if (null != resultList && resultList.size() > 0) {
        	totalEntity = new DeliveryReportEric();
        	Long turnover = 0L;
        	Double amount = (double) 0;
        	Double memberAmount = (double) 0;
        	Double rateMoney = (double) 0;
        	Double realWin = (double) 0;
        	Double realBackWater = (double) 0;
        	Double commission = (double) 0;
        	Double offerSuperior = (double) 0;
        	Double subordinateAmountWin = (double) 0;/*应收下线输赢*/
        	Double subordinateAmountBackWater = (double) 0;/*应收下线退水*/
        	
            for(DeliveryReportEric vo : resultList){
            	turnover += vo.getTurnover();
            	amount += vo.getAmount();
            	memberAmount += vo.getMemberAmount();
            	rateMoney += vo.getRateMoney();
            	realWin += vo.getRealWin();
            	realBackWater += vo.getRealBackWater();
            	commission += vo.getCommission();
            	offerSuperior += vo.getOfferSuperior();
            	subordinateAmountWin += vo.getSubordinateAmountWin();
            	subordinateAmountBackWater += vo.getSubordinateAmountBackWater();
            }
            totalEntity.setTurnover(turnover);
            totalEntity.setAmount(amount);
            totalEntity.setMemberAmount(memberAmount);
            totalEntity.setRateMoney(rateMoney);
            totalEntity.setRealWin(realWin);
            totalEntity.setRealBackWater(realBackWater);
            totalEntity.setCommission(commission);
            totalEntity.setOfferSuperior(offerSuperior);
            totalEntity.setSubordinateAmountWin(subordinateAmountWin);
            totalEntity.setSubordinateAmountBackWater(subordinateAmountBackWater);
        }
        
        //计算明细记录中的占货比、合计数据中的实占输赢、实占退水、贡献上级
        if (null != resultList && null != totalEntity) {
            
            Double realResult = totalEntity.getRateMoney();//获取合计数据中的投注总额的值
            Double offerSuperior = 0.0;//计算合计数据中的贡献上级
            for (int i = 0; i < resultList.size(); i++) {
                resultList.get(i).calRealResultPer(realResult);//占货比      实占金额/实占金额合计

                offerSuperior += resultList.get(i).getOfferSuperior();
            }

            totalEntity.setOfferSuperior(offerSuperior);//合计数据中赋值实占输赢、实占退水、贡献上级、赚取退水
            totalEntity.setRealResultPer(1.0);//设计合计中的占货比为100%
        }
        
        //抵扣补货及赚水后结果    占成结果+赚取退水-补货报表的退水后结果（总监少了一个赚取退水）
        Double dk = (double) 0;
        Double dBackWaterResult = (double) 0;
        if(replenishEntity!=null){
        	dBackWaterResult = replenishEntity.getBackWaterResult();
        }
        if(totalEntity!=null){
	        if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
	            dk = totalEntity.getRealResultNew() - dBackWaterResult;
	        }else{
	        	dk = totalEntity.getRealResultNew() + totalEntity.getCommission() - dBackWaterResult;
	        }
        }
		//处理补出的END-------------------
		
		return dk;
	}
	
	/** 分两个步骤：
	 * 1、先按期重新计算报表
	 * 2、以userid和bettingDate来求和报表的期数统计表再写入报表统计历史表里，如果是当天的就不写入报表统计历史表里。
	 */
	@Override
	public void saveReportListForReCompute(final String periodNum,final String lotteryType, final String dateStr,
			final Boolean isToday,final ISettledReportEricLogic settledReportEricLogic,
			final IClassReportEricLogic classReportEricLogic,final IReportStatusLogic reportStatusLogic,final String schema) throws ParseException{
		wcpTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {

				try {
					//1、先按期重新计算报表
					//先把要该日期的报表统计数据删除
					settledReportEricLogic.deleteReportPetPeriod(periodNum, lotteryType, schema);
					settledReportEricLogic.deleteReportRPeriod(periodNum, lotteryType, schema);
					classReportEricLogic.deleteReportPetPeriod(periodNum, lotteryType, schema);
					classReportEricLogic.deleteReportRPeriod(periodNum, lotteryType, schema);
					
					settledReportEricLogic.saveReportPeriod(periodNum, lotteryType, schema, dateStr);
					
					if(isToday==false){
						//2、以userid和bettingDate来求和报表的期数统计表再写入报表统计历史表里，如果是当天的就不写入报表统计历史表里。
						settledReportEricLogic.deleteReportPetList(dateStr, dateStr, schema);
						settledReportEricLogic.deleteReportRList(dateStr, dateStr, schema);
						classReportEricLogic.deleteReportPetList(dateStr, dateStr, schema);
						classReportEricLogic.deleteReportRList(dateStr, dateStr, schema);
						settledReportEricLogic.saveReportList(dateStr,dateStr,schema);
					}
					
				} catch (Throwable e) {
					String status = "N";
					reportStatusLogic.updateReportStatus(status, schema);
			    	
			    	LOG.info("自动重新计算报表异步调出错！"+e.getMessage());
	
				}
				String status = "Y";
				reportStatusLogic.updateReportStatus(status, schema);
			}

			});
	}
	
	/** 手动生成报表时使用
	 * 1、先按期重新计算报表
	 */
	/*@Override
	public void saveReportListForReComputeMenu(final String periodNum,final String lotteryType, final String dateStr,
			final ISettledReportEricLogic settledReportEricLogic,
			final IClassReportEricLogic classReportEricLogic,final IReportStatusLogic reportStatusLogic,final String schema) throws ParseException{
		wcpTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				
				try {
					//1、先按期重新计算报表
					//先把要该日期的报表统计数据删除
					settledReportEricLogic.deleteReportPetPeriod(periodNum, lotteryType, schema);
					settledReportEricLogic.deleteReportRPeriod(periodNum, lotteryType, schema);
					classReportEricLogic.deleteReportPetPeriod(periodNum, lotteryType, schema);
					classReportEricLogic.deleteReportRPeriod(periodNum, lotteryType, schema);
					
					settledReportEricLogic.saveReportPeriod(periodNum, lotteryType, schema);
					
				} catch (Throwable e) {
					String status = "N";
					reportStatusLogic.updateReportStatus(status, schema);
					
					LOG.info("自动重新计算报表异步调出错！"+e.getMessage());
					
				}
				String status = "Y";
				reportStatusLogic.updateReportStatus(status, schema);
			}
			
		});
	}*/
	
	@Override
	public void saveReportListForReComputeMenuForBoss(String periodNum, String lotteryType,  String dateStr, String schema){
		try {
			
			
			this.saveReportPeriodForBoss(periodNum, lotteryType, schema);
			
		} catch (Throwable e) {
			String status = "N";
			reportStatusLogic.updateReportStatus(status, schema);
			
			LOG.info("自动重新计算报表异步调出错！"+e.getMessage());
			
		}
		String status = "Y";
		reportStatusLogic.updateReportStatus(status, schema);
	}
	
	/**
	 * 以userid和bettingDate来求和报表的期数统计表再写入报表统计历史表里，如果是当天的就不写入报表统计历史表里
	 */
	@Override
	public void saveReportList(String rStart,String rEnd,String schema) throws ParseException{
		LOG.info("<--生成统计历史报表  从"+rStart+"至"+rEnd+" start-->");
    	
    	long startTime = System.currentTimeMillis();
    	
		String[] strStart = rStart.split("-");
        String[] strEnd = rEnd.split("-");
        String head = strStart[0]+'-'+strStart[1];
        Integer startDate = Integer.valueOf(strStart[2]);
        Integer endDate = Integer.valueOf(strEnd[2]);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int ii = 0;
    	//获取所有管理员
		java.util.Date startdate=sdf.parse(rStart);
		java.util.Date enddate=sdf.parse(rEnd);
    	java.sql.Date startD = new Date(startdate.getTime());
    	java.sql.Date endD = new Date(enddate.getTime());
    	
    	String[] reportPeriodTables = new String[]{"tb_s_report_pet_period","tb_s_report_r_period"};
    	List<ManagerStaff> managerList  = this.queryAllManagerUserInReportPeriod(reportPeriodTables,startD,endD,null,schema);
		for(ManagerStaff managerStaff:managerList){
			Long userId = managerStaff.getID();
			String userType = managerStaff.getUserType();
	        LOG.info("~~~~~~~no:"+ii+"~~~~~~~~~~~~~~userid is :"+userId+"~~~~~~~~~~~~~~userTyep is :"+userType);
	        ii+=1;
			//生成交收统计报表
	        for(int i=startDate;i<=endDate;i++){
	        	java.util.Date date=sdf.parse(head+'-'+i);
	        	java.sql.Date bettingDate = new Date(date.getTime());
		        	
		        	List<DeliveryReportEric> resultList = settledReportEricDao.queryDeliveryReportPetPeriod(userId, 
		        			userType, bettingDate, bettingDate,null,null,schema);
		        	//计算投注总额
		        	DeliveryReportEric totalEntity = null;
	                if (null != resultList && resultList.size() > 0) {
	                	totalEntity = new DeliveryReportEric();
	                	Double rateMoney = (double) 0;
	                	
	                    for(DeliveryReportEric vo : resultList){
	                    	rateMoney += vo.getRateMoney();
	                    }
	                    totalEntity.setRateMoney(rateMoney);
	                }
	                
		        	for(DeliveryReportEric resultEntity:resultList){
		        		DeliveryReportPetList reportInfo = new DeliveryReportPetList();
		        		reportInfo.setUserID(managerStaff.getID());
		            	reportInfo.setUserType(managerStaff.getUserType());
		            	reportInfo.setBettingUserID(resultEntity.getBettingUserID());
		            	reportInfo.setBettingUserType(resultEntity.getBettingUserType());
		            	reportInfo.setParentUserType(resultEntity.getParentUserType());
		            	reportInfo.setSubordinate(resultEntity.getSubordinate());  	
		            	reportInfo.setUserName(resultEntity.getUserName());
		            	reportInfo.setTurnover(resultEntity.getTurnover());
		                reportInfo.setAmount(resultEntity.getAmount());
		                reportInfo.setMoneyRateAgent(resultEntity.getMoneyRateAgent());
		                reportInfo.setMoneyRateGenAgent(resultEntity.getMoneyRateGenAgent());
		                reportInfo.setMoneyRateStockholder(resultEntity.getMoneyRateStockholder());
		                reportInfo.setMoneyRateBranch(resultEntity.getMoneyRateBranch());
		                reportInfo.setMoneyRateChief(resultEntity.getMoneyRateChief());
		                reportInfo.setRateMoney(resultEntity.getRateMoney());
		                
		                reportInfo.setMemberAmount(resultEntity.getMemberAmount());
		                reportInfo.setSubordinateAmountWin(resultEntity.getSubordinateAmountWin());
		                reportInfo.setSubordinateAmountBackWater(resultEntity.getSubordinateAmountBackWater());
		                reportInfo.setRealWin(resultEntity.getRealWin());
		                reportInfo.setRealBackWater(resultEntity.getRealBackWater());
		                reportInfo.setCommission(resultEntity.getCommission());
		                reportInfo.setBettingDate(bettingDate);
		                //处理占货比，计算明细记录中的占货比、合计数据中的实占输赢、实占退水、贡献上级
		                if (null != resultList && null != totalEntity) {
		                	Double realResult = totalEntity.getRateMoney();//获取合计数据中的投注总额的值
		                	reportInfo.calRealResultPer(realResult);//占货比      实占金额/实占金额合计
		                }
		                settledReportPetListDao.insertSettledReportPetPet(reportInfo, schema);
		                //saveReportPetList(reportInfo);
		        		
		        	}
		        	
		        	//处理补出货信息
		        	List<DeliveryReportEric> rlist = settledReportEricDao.queryDeliveryReportRPeriod(userId, userType, 
		        			bettingDate, bettingDate,null,null,schema);
		        	for(DeliveryReportEric resultEntity:rlist){
		        		DeliveryReportRList reportInfoR = new DeliveryReportRList();
		        		reportInfoR.setUserID(managerStaff.getID());
		        		reportInfoR.setUserType(managerStaff.getUserType());
		        		reportInfoR.setTurnover(resultEntity.getTurnover());
		        		reportInfoR.setAmount(resultEntity.getAmount());
		        		reportInfoR.setMemberAmount(resultEntity.getMemberAmount());
		        		reportInfoR.setWinBackWater(resultEntity.getWinBackWater());
		        		reportInfoR.setBackWaterResult(resultEntity.getBackWaterResult());
		        		reportInfoR.setBettingDate(bettingDate);
		        		
		        		settledReportRListDao.insertSettledReportRList(reportInfoR, schema);
			        	//saveReportRList(reportInfoR);
		        		
		        	}
		        	
		        //}
	        }
			
	        //生成分类报表
	        for(int i=startDate;i<=endDate;i++){
	        	java.util.Date date=sdf.parse(head+'-'+i);
	        	java.sql.Date bettingDate = new Date(date.getTime());
	        		List<ClassReportEric> resultListClass = classReportEricDao.queryClassReportPetPeriod(userId, userType, bettingDate, bettingDate, null,null, schema);
	        		
	        		for(ClassReportEric resultEntity:resultListClass){
	        			ClassReportPetList reportInfo = new ClassReportPetList();
	        			reportInfo.setUserID(managerStaff.getID());
	        			reportInfo.setUserType(managerStaff.getUserType());
	        			reportInfo.setTurnover(resultEntity.getTurnover());
	        			reportInfo.setAmount(resultEntity.getAmount());
	        			reportInfo.setMoneyRateAgent(resultEntity.getMoneyRateAgent());
	        			reportInfo.setMoneyRateGenAgent(resultEntity.getMoneyRateGenAgent());
	        			reportInfo.setMoneyRateStockholder(resultEntity.getMoneyRateStockholder());
	        			reportInfo.setMoneyRateBranch(resultEntity.getMoneyRateBranch());
	        			reportInfo.setMoneyRateChief(resultEntity.getMoneyRateChief());
	        			reportInfo.setRateMoney(resultEntity.getRateMoney());
	        			
	        			reportInfo.setMemberAmount(resultEntity.getMemberAmount());
	        			reportInfo.setSubordinateAmountWin(resultEntity.getSubordinateAmountWin());
	        			reportInfo.setSubordinateAmountBackWater(resultEntity.getSubordinateAmountBackWater());
	        			reportInfo.setRealWin(resultEntity.getRealWin());
	        			reportInfo.setRealBackWater(resultEntity.getRealBackWater());
	        			reportInfo.setCommission(resultEntity.getCommission());
	        			reportInfo.setBettingDate(bettingDate);
	        			reportInfo.setCommissionType(resultEntity.getCommissionType());
	        			
	        			classReportPetListDao.insertClassReportPetPet(reportInfo, schema);
	        			//classReportEricLogic.saveReportPetList(reportInfo);
	        			
	        		}
	        		
	        		//处理补出货信息
	        		List<ClassReportEric> rlistClass = classReportEricDao.queryClassReportRPeriod(userId, userType, 
	        				bettingDate, bettingDate, null,null, schema);
	        		for(ClassReportEric resultEntity:rlistClass){
	        			ClassReportRList reportInfo = new ClassReportRList();
	        			reportInfo.setUserID(managerStaff.getID());
	        			reportInfo.setUserType(managerStaff.getUserType());
	        			reportInfo.setTurnover(resultEntity.getTurnover());
	        			reportInfo.setAmount(resultEntity.getAmount());
	        			reportInfo.setMemberAmount(resultEntity.getMemberAmount());
	        			reportInfo.setWinBackWater(resultEntity.getWinBackWater());
	        			reportInfo.setBackWaterResult(resultEntity.getBackWaterResult());
	        			reportInfo.setCommissionType(resultEntity.getCommissionType());
	        			reportInfo.setBettingDate(bettingDate);
	        			
	        			classReportRListDao.insertClassReportRList(reportInfo, schema);
	        			//classReportEricLogic.saveReportRList(reportInfo);
	        			
	        		}
	          }
		  }
        long endClass = System.currentTimeMillis();
		LOG.info("<--生成统计历史报表 结束  所用时间："+(endClass-startTime)/1000+" 秒 -->");
		
	}
	
	/**
	 * 该方法是直接重新生成整天的统计报表
	 */
	@Override
	public void saveReportFullDayList(String rStart,String rEnd, Boolean isJOB, String schema) throws ParseException{
		LOG.info("<--生成统计报表  从"+rStart+"至"+rEnd+" start-->");
		
		long startTime = System.currentTimeMillis();
		Date today = new Date(new java.util.Date().getTime());
		String[] todayStr = today.toString().split("-");
		int todayDay = Integer.valueOf(todayStr[2]);
		
		String vPlayType = "%";
		String lotteryTypeFrm = "%";
		String periodsNumFrm = null;
		String[] scanTableList = null;
		if(isJOB==false){
			scanTableList = Constant.ALL_HIS_LIST;
		}else{
			scanTableList = Constant.ALL_HIS_YESTERDAY_VIEW;
		}
		String winState = "9";
		String replenishTableName = Constant.REPLENISH_TABLE_NAME_HIS;
		//String mananerStaffTableName = "tb_frame_manager_staff";
		//String memberStaffTableName = "tb_frame_member_staff";
		//String outReplenishStaffExtTabelName = "tb_out_replenish_staff_ext";
		
		String[] strStart = rStart.split("-");
		String[] strEnd = rEnd.split("-");
		String head = strStart[0]+'-'+strStart[1];
		Integer startDate = Integer.valueOf(strStart[2]);
		Integer endDate = Integer.valueOf(strEnd[2]);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int ii = 0;
		//获取所有管理员
		java.util.Date startdate=sdf.parse(rStart);
		java.util.Date enddate=sdf.parse(rEnd);
		java.sql.Date startD = new Date(startdate.getTime());
		java.sql.Date endD = new Date(enddate.getTime());
		
		List<ManagerStaff> managerList  = this.queryAllManagerUser(Constant.ALL_REPORT_HIS_LIST,startD,endD,null,schema);
		for(ManagerStaff managerStaff:managerList){
			LOG.info("~~~~~~~no:"+ii+"~~~~~~~~~~~~~~userid is :"+managerStaff.getID());
			ii+=1;
			//生成交收统计报表
			for(int i=startDate;i<=endDate;i++){
				java.util.Date date=sdf.parse(head+'-'+i);
				java.sql.Date bettingDate = new Date(date.getTime());
				//当天是不用统计的。
				if(todayDay==i){
					continue;
				}
				
				List<DeliveryReportEric> resultList = findSettledReport(bettingDate, bettingDate, lotteryTypeFrm, vPlayType, 
						periodsNumFrm, managerStaff.getID(), managerStaff.getUserType(),scanTableList,winState,schema);
				//计算投注总额
				DeliveryReportEric totalEntity = null;
				if (null != resultList && resultList.size() > 0) {
					totalEntity = new DeliveryReportEric();
					Double rateMoney = (double) 0;
					
					for(DeliveryReportEric vo : resultList){
						rateMoney += vo.getRateMoney();
					}
					totalEntity.setRateMoney(rateMoney);
				}
				
				for(DeliveryReportEric resultEntity:resultList){
					DeliveryReportPetList reportInfo = new DeliveryReportPetList();
					reportInfo.setUserID(managerStaff.getID());
					reportInfo.setUserType(managerStaff.getUserType());
					reportInfo.setBettingUserID(resultEntity.getUserID());
					reportInfo.setBettingUserType(resultEntity.getUserType());
					reportInfo.setParentUserType(resultEntity.getParentUserType());
					reportInfo.setSubordinate(resultEntity.getSubordinate());  	
					reportInfo.setUserName(resultEntity.getUserName());
					reportInfo.setTurnover(resultEntity.getTurnover());
					reportInfo.setAmount(resultEntity.getAmount());
					reportInfo.setMoneyRateAgent(resultEntity.getMoneyRateAgent());
					reportInfo.setMoneyRateGenAgent(resultEntity.getMoneyRateGenAgent());
					reportInfo.setMoneyRateStockholder(resultEntity.getMoneyRateStockholder());
					reportInfo.setMoneyRateBranch(resultEntity.getMoneyRateBranch());
					reportInfo.setMoneyRateChief(resultEntity.getMoneyRateChief());
					reportInfo.setRateMoney(resultEntity.getRateMoney());
					
					reportInfo.setMemberAmount(resultEntity.getMemberAmount());
					reportInfo.setSubordinateAmountWin(resultEntity.getSubordinateAmountWin());
					reportInfo.setSubordinateAmountBackWater(resultEntity.getSubordinateAmountBackWater());
					reportInfo.setRealWin(resultEntity.getRealWin());
					reportInfo.setRealBackWater(resultEntity.getRealBackWater());
					reportInfo.setCommission(resultEntity.getCommission());
					reportInfo.setBettingDate(bettingDate);
					//处理占货比，计算明细记录中的占货比、合计数据中的实占输赢、实占退水、贡献上级
					if (null != resultList && null != totalEntity) {
						Double realResult = totalEntity.getRateMoney();//获取合计数据中的投注总额的值
						reportInfo.calRealResultPer(realResult);//占货比      实占金额/实占金额合计
					}
					
					settledReportPetListDao.insertSettledReportPetPet(reportInfo, schema);
					//saveReportPetList(reportInfo);
					
				}
				
				//处理补出货信息
				List<DeliveryReportEric> rlist = queryReplenish(bettingDate, bettingDate,managerStaff.getID(), 
						managerStaff.getUserType(),vPlayType, periodsNumFrm,lotteryTypeFrm,winState,replenishTableName,schema);
				for(DeliveryReportEric resultEntity:rlist){
					DeliveryReportRList reportInfoR = new DeliveryReportRList();
					reportInfoR.setUserID(managerStaff.getID());
					reportInfoR.setUserType(managerStaff.getUserType());
					reportInfoR.setTurnover(resultEntity.getTurnover());
					reportInfoR.setAmount(resultEntity.getAmount());
					reportInfoR.setMemberAmount(resultEntity.getMemberAmount());
					reportInfoR.setWinBackWater(resultEntity.getWinBackWater());
					reportInfoR.setBackWaterResult(resultEntity.getBackWaterResult());
					reportInfoR.setBettingDate(bettingDate);
					
					settledReportRListDao.insertSettledReportRList(reportInfoR, schema);
					//saveReportRList(reportInfoR);
					
				}
				
				//}
		}
			
			//生成分类报表
			for(int i=startDate;i<=endDate;i++){
				java.util.Date date=sdf.parse(head+'-'+i);
				java.sql.Date bettingDate = new Date(date.getTime());
				//当天是不用统计的。
				if(todayDay==i){
					continue;
				}
				List<ClassReportEric> resultListClass = classReportEricLogic
						.findClassReport(bettingDate, bettingDate, lotteryTypeFrm, vPlayType, periodsNumFrm, 
								managerStaff.getID(), managerStaff.getUserType(),scanTableList,winState,schema);
				
				for(ClassReportEric resultEntity:resultListClass){
					ClassReportPetList reportInfo = new ClassReportPetList();
					reportInfo.setUserID(managerStaff.getID());
					reportInfo.setUserType(managerStaff.getUserType());
					reportInfo.setTurnover(resultEntity.getTurnover());
					reportInfo.setAmount(resultEntity.getAmount());
					reportInfo.setMoneyRateAgent(resultEntity.getMoneyRateAgent());
					reportInfo.setMoneyRateGenAgent(resultEntity.getMoneyRateGenAgent());
					reportInfo.setMoneyRateStockholder(resultEntity.getMoneyRateStockholder());
					reportInfo.setMoneyRateBranch(resultEntity.getMoneyRateBranch());
					reportInfo.setMoneyRateChief(resultEntity.getMoneyRateChief());
					reportInfo.setRateMoney(resultEntity.getRateMoney());
					
					reportInfo.setMemberAmount(resultEntity.getMemberAmount());
					reportInfo.setSubordinateAmountWin(resultEntity.getSubordinateAmountWin());
					reportInfo.setSubordinateAmountBackWater(resultEntity.getSubordinateAmountBackWater());
					reportInfo.setRealWin(resultEntity.getRealWin());
					reportInfo.setRealBackWater(resultEntity.getRealBackWater());
					reportInfo.setCommission(resultEntity.getCommission());
					reportInfo.setBettingDate(bettingDate);
					reportInfo.setCommissionType(resultEntity.getCommissionType());
					
					classReportPetListDao.insertClassReportPetPet(reportInfo, schema);
					//classReportEricLogic.saveReportPetList(reportInfo);
					
				}
				
				//处理补出货信息
				List<ClassReportEric> rlistClass = classReportEricLogic.queryReplenish(bettingDate, bettingDate,managerStaff.getID(), 
						managerStaff.getUserType(),vPlayType, periodsNumFrm,lotteryTypeFrm,winState,replenishTableName,schema);
				for(ClassReportEric resultEntity:rlistClass){
					ClassReportRList reportInfo = new ClassReportRList();
					reportInfo.setUserID(managerStaff.getID());
					reportInfo.setUserType(managerStaff.getUserType());
					reportInfo.setTurnover(resultEntity.getTurnover());
					reportInfo.setAmount(resultEntity.getAmount());
					reportInfo.setMemberAmount(resultEntity.getMemberAmount());
					reportInfo.setWinBackWater(resultEntity.getWinBackWater());
					reportInfo.setBackWaterResult(resultEntity.getBackWaterResult());
					reportInfo.setCommissionType(resultEntity.getCommissionType());
					reportInfo.setBettingDate(bettingDate);
					
					classReportRListDao.insertClassReportRList(reportInfo, schema);
					//classReportEricLogic.saveReportRList(reportInfo);
					
				}
			}
		}
		long endClass = System.currentTimeMillis();
		LOG.info("<--生成统计报表 结束  所用时间："+(endClass-startTime)/1000+" 秒 -->");
		
	}
	
	/**
	 * 该方法是直接重新生成某期的统计报表
	 * @lotteryType 用常量
	 */
/*	@Override
	public void saveReportPeriod(String periodsNum,String lotteryType) throws ParseException{
		LOG.info("<--按盘期生成统计报表,种类是: "+lotteryType+",盘期是:"+periodsNum+" start-->");
		
		long startTime = System.currentTimeMillis();
		
		String vPlayType = "%";
    	String lotteryTypeFrm = "%";
		String[] scanTableList = null;
		String[] scanTableListFindUser = null;
		if(Constant.LOTTERY_TYPE_BJ.equals(lotteryType)){
			scanTableList = new String[] {"TB_BJSC_HIS"};
			scanTableListFindUser = new String[] {"TB_BJSC_HIS",Constant.REPLENISH_TABLE_NAME_HIS};
		}else if(Constant.LOTTERY_TYPE_CQSSC.equals(lotteryType)){
			scanTableList = new String[] {"TB_CQSSC_HIS"};
			scanTableListFindUser = new String[] {"TB_CQSSC_HIS",Constant.REPLENISH_TABLE_NAME_HIS};
		}else if(Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)){
			scanTableList = new String[] {"TB_GDKLSF_HIS"};
			scanTableListFindUser = new String[] {"TB_GDKLSF_HIS",Constant.REPLENISH_TABLE_NAME_HIS};
		}else if(Constant.LOTTERY_TYPE_NC.equals(lotteryType)){
			scanTableList = new String[] {"TB_NC_HIS"};
			scanTableListFindUser = new String[] {"TB_NC_HIS",Constant.REPLENISH_TABLE_NAME_HIS};
		}else{
			scanTableList = new String[] {"TB_JSSB_HIS"};
			scanTableListFindUser = new String[] {"TB_JSSB_HIS",Constant.REPLENISH_TABLE_NAME_HIS};
		}
			
		String replenishTableName = Constant.REPLENISH_TABLE_NAME_HIS;
		String mananerStaffTableName = "tb_frame_manager_staff";
		String memberStaffTableName = "tb_frame_member_staff";
		String outReplenishStaffExtTabelName = "tb_out_replenish_staff_ext";
		String winState = "9";
		
		int ii = 0;
		//获取所有管理员
		
		List<ManagerStaff> managerList  = this.queryAllManagerUser(scanTableListFindUser,null,null,periodsNum);
		for(ManagerStaff managerStaff:managerList){
			LOG.info("~~~~~~~no:"+ii+"~~~~~~~~~~~~~~userid is :"+managerStaff.getID());
			ii+=1;
			//生成交收统计报表
			Date bettingDate = null;
			List<DeliveryReportEric> resultList = findSettledReport(bettingDate, bettingDate, lotteryTypeFrm, vPlayType, 
					periodsNum, managerStaff.getID(), managerStaff.getUserType(),scanTableList,winState,
					replenishTableName,mananerStaffTableName,memberStaffTableName);
			//计算投注总额
			DeliveryReportEric totalEntity = null;
			if (null != resultList && resultList.size() > 0) {
				totalEntity = new DeliveryReportEric();
				Double rateMoney = (double) 0;
				
				for(DeliveryReportEric vo : resultList){
					rateMoney += vo.getRateMoney();
				}
				totalEntity.setRateMoney(rateMoney);
			}
			
			for(DeliveryReportEric resultEntity:resultList){
				DeliveryReportPetPeriod reportInfo = new DeliveryReportPetPeriod();
				reportInfo.setUserID(managerStaff.getID());
				reportInfo.setUserType(managerStaff.getUserType());
				reportInfo.setBettingUserID(resultEntity.getUserID());
				reportInfo.setBettingUserType(resultEntity.getUserType());
				reportInfo.setParentUserType(resultEntity.getParentUserType());
				reportInfo.setSubordinate(resultEntity.getSubordinate());  	
				reportInfo.setUserName(resultEntity.getUserName());
				reportInfo.setTurnover(resultEntity.getTurnover());
				reportInfo.setAmount(resultEntity.getAmount());
				reportInfo.setMoneyRateAgent(resultEntity.getMoneyRateAgent());
				reportInfo.setMoneyRateGenAgent(resultEntity.getMoneyRateGenAgent());
				reportInfo.setMoneyRateStockholder(resultEntity.getMoneyRateStockholder());
				reportInfo.setMoneyRateBranch(resultEntity.getMoneyRateBranch());
				reportInfo.setMoneyRateChief(resultEntity.getMoneyRateChief());
				reportInfo.setRateMoney(resultEntity.getRateMoney());
				
				reportInfo.setMemberAmount(resultEntity.getMemberAmount());
				reportInfo.setSubordinateAmountWin(resultEntity.getSubordinateAmountWin());
				reportInfo.setSubordinateAmountBackWater(resultEntity.getSubordinateAmountBackWater());
				reportInfo.setRealWin(resultEntity.getRealWin());
				reportInfo.setRealBackWater(resultEntity.getRealBackWater());
				reportInfo.setCommission(resultEntity.getCommission());
				reportInfo.setBettingDate(resultEntity.getBettingDate());
				reportInfo.setPeriodsNum(resultEntity.getPeriodNum());
				reportInfo.setLotteryType(lotteryType);
				
				//处理占货比，计算明细记录中的占货比、合计数据中的实占输赢、实占退水、贡献上级
				if (null != resultList && null != totalEntity) {
					Double realResult = totalEntity.getRateMoney();//获取合计数据中的投注总额的值
					reportInfo.calRealResultPer(realResult);//占货比      实占金额/实占金额合计
				}
				
				saveReportPetPeriod(reportInfo);
				
			}
			
			//处理补出货信息
			
			List<DeliveryReportEric> rlist = queryReplenish(bettingDate, bettingDate,managerStaff.getID(), 
					managerStaff.getUserType(),vPlayType, periodsNum,lotteryTypeFrm,winState,replenishTableName,outReplenishStaffExtTabelName);
			for(DeliveryReportEric resultEntity:rlist){
				DeliveryReportRPeriod reportInfoR = new DeliveryReportRPeriod();
				reportInfoR.setUserID(managerStaff.getID());
				reportInfoR.setUserType(managerStaff.getUserType());
				reportInfoR.setTurnover(resultEntity.getTurnover());
				reportInfoR.setAmount(resultEntity.getAmount());
				reportInfoR.setMemberAmount(resultEntity.getMemberAmount());
				reportInfoR.setWinBackWater(resultEntity.getWinBackWater());
				reportInfoR.setBackWaterResult(resultEntity.getBackWaterResult());
				reportInfoR.setBettingDate(resultEntity.getBettingDate());
				reportInfoR.setPeriodsNum(resultEntity.getPeriodNum());
				reportInfoR.setLotteryType(lotteryType);
				saveReportRPeriod(reportInfoR);
				
			}
				
			
			//生成分类报表
			List<ClassReportEric> resultListClass = classReportEricLogic
					.findClassReport(bettingDate, bettingDate, lotteryTypeFrm, vPlayType, periodsNum, managerStaff.getID(), managerStaff.getUserType(),
							scanTableList,winState,replenishTableName,mananerStaffTableName,memberStaffTableName);
			
			for(ClassReportEric resultEntity:resultListClass){
				ClassReportPetPeriod reportInfo = new ClassReportPetPeriod();
				reportInfo.setUserID(managerStaff.getID());
				reportInfo.setUserType(managerStaff.getUserType());
				reportInfo.setTurnover(resultEntity.getTurnover());
				reportInfo.setAmount(resultEntity.getAmount());
				reportInfo.setMoneyRateAgent(resultEntity.getMoneyRateAgent());
				reportInfo.setMoneyRateGenAgent(resultEntity.getMoneyRateGenAgent());
				reportInfo.setMoneyRateStockholder(resultEntity.getMoneyRateStockholder());
				reportInfo.setMoneyRateBranch(resultEntity.getMoneyRateBranch());
				reportInfo.setMoneyRateChief(resultEntity.getMoneyRateChief());
				reportInfo.setRateMoney(resultEntity.getRateMoney());
				
				reportInfo.setMemberAmount(resultEntity.getMemberAmount());
				reportInfo.setSubordinateAmountWin(resultEntity.getSubordinateAmountWin());
				reportInfo.setSubordinateAmountBackWater(resultEntity.getSubordinateAmountBackWater());
				reportInfo.setRealWin(resultEntity.getRealWin());
				reportInfo.setRealBackWater(resultEntity.getRealBackWater());
				reportInfo.setCommission(resultEntity.getCommission());
				reportInfo.setBettingDate(resultEntity.getBettingDate());
				reportInfo.setCommissionType(resultEntity.getCommissionType());
				reportInfo.setPeriodsNum(resultEntity.getPeriodNum());
				reportInfo.setLotteryType(lotteryType);
				classReportEricLogic.saveReportPetPeriod(reportInfo);
				
			}
			
			//处理补出货信息
			List<ClassReportEric> rlistClass = classReportEricLogic.queryReplenish(bettingDate, bettingDate,managerStaff.getID(), managerStaff.getUserType(),
					vPlayType, periodsNum,lotteryTypeFrm,winState,replenishTableName,outReplenishStaffExtTabelName);
			for(ClassReportEric resultEntity:rlistClass){
				ClassReportRPeriod reportInfo = new ClassReportRPeriod();
				reportInfo.setUserID(managerStaff.getID());
				reportInfo.setUserType(managerStaff.getUserType());
				reportInfo.setTurnover(resultEntity.getTurnover());
				reportInfo.setAmount(resultEntity.getAmount());
				reportInfo.setMemberAmount(resultEntity.getMemberAmount());
				reportInfo.setWinBackWater(resultEntity.getWinBackWater());
				reportInfo.setBackWaterResult(resultEntity.getBackWaterResult());
				reportInfo.setCommissionType(resultEntity.getCommissionType());
				reportInfo.setBettingDate(resultEntity.getBettingDate());
				reportInfo.setPeriodsNum(resultEntity.getPeriodNum());
				reportInfo.setLotteryType(lotteryType);
				classReportEricLogic.saveReportRPeriod(reportInfo);
				
			}
		}
		
		long endClass = System.currentTimeMillis();
		LOG.info("<--按盘期生成统计报表 结束  所用时间："+(endClass-startTime)/1000+" 秒 -->");
		
	}*/
	
	/**
	 * 开放给投注端调的生成报表方法
	 * 如果是当天的，就不生成pet list,只重新计算生成当期的报表计算
	 * @param periodsNum
	 * @param lotteryType
	 * @param schema
	 * @throws ParseException
	 */
	@Override
	public void saveReportPeriod(String periodsNum,String lotteryType,String scheme, String culDate) throws ParseException{
		this.saveReportPeriods(periodsNum, lotteryType, scheme);//首先生成以盘期为统计的报表
		
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate = new java.util.Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.HOUR, date.get(Calendar.HOUR) - 3);
		String nowDate = dft.format(date.getTime());
		
		if(!nowDate.equals(culDate)){
			//生成历史报表
			//2、以userid和bettingDate来求和报表的期数统计表再写入报表统计历史表里，如果是当天的就不写入报表统计历史表里。
			this.deleteReportPetList(culDate, culDate,scheme);
			this.deleteReportRList(culDate, culDate,scheme);
			classReportEricLogic.deleteReportPetList(culDate, culDate,scheme);
			classReportEricLogic.deleteReportRList(culDate, culDate,scheme);
			
			this.saveReportList(culDate,culDate,scheme);
		}
	}
	
	/**
	 * 给总管调用的计算报表的方法
	 * @param periodsNum
	 * @param lotteryType
	 * @param schema
	 * @throws ParseException
	 */
	@Override
	public void saveReportPeriodForBoss(String periodsNum,String lotteryType,String schema) throws ParseException{
		this.saveReportPeriods(periodsNum, lotteryType, schema);
	}
	
	
	/**
	 * 该方法是直接重新生成某期的统计报表_for job
	 * 独立写的原因JOB里是要传表的用户名的，所以不能用hibernate，而要用SQL写。
	 * 与上面的方法相比，只是取数据改用SQL，计算方式是一样的。
	 * @lotteryType 用常量
	 * @schema 没有就赋于""
	 */
	private void saveReportPeriods(String periodsNum,String lotteryType,String schema) throws ParseException{
		LOG.info("<--按盘期生成统计报表,种类是: "+lotteryType+",盘期是:"+periodsNum+",schema:"+schema+" start-->");
		
		long startTime = System.currentTimeMillis();
		
		String vPlayType = "%";
		String lotteryTypeFrm = "%";
		String[] scanTableList = null;
		String[] scanTableListFindUser = null;
		if(Constant.LOTTERY_TYPE_BJ.equals(lotteryType)){
			scanTableList = new String[] {"TB_BJSC_HIS"};
			scanTableListFindUser = new String[] {"TB_BJSC_HIS",Constant.REPLENISH_TABLE_NAME_HIS};
			lotteryTypeFrm = "BJ%";
		}else if(Constant.LOTTERY_TYPE_CQSSC.equals(lotteryType)){
			scanTableList = new String[] {"TB_CQSSC_HIS"};
			scanTableListFindUser = new String[] {"TB_CQSSC_HIS",Constant.REPLENISH_TABLE_NAME_HIS};
			lotteryTypeFrm = "CQSSC%";
		}else if(Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)){
			scanTableList = new String[] {"TB_GDKLSF_HIS"};
			scanTableListFindUser = new String[] {"TB_GDKLSF_HIS",Constant.REPLENISH_TABLE_NAME_HIS};
			lotteryTypeFrm = "GDKLSF%";
		}else if(Constant.LOTTERY_TYPE_NC.equals(lotteryType)){
			scanTableList = new String[] {"TB_NC_HIS"};
			scanTableListFindUser = new String[] {"TB_NC_HIS",Constant.REPLENISH_TABLE_NAME_HIS};
			lotteryTypeFrm = "NC%";
		}else{
			scanTableList = new String[] {"TB_JSSB_HIS"};
			scanTableListFindUser = new String[] {"TB_JSSB_HIS",Constant.REPLENISH_TABLE_NAME_HIS};
			lotteryTypeFrm = "K3%";
		}
		
		String replenishTableName = Constant.REPLENISH_TABLE_NAME_HIS;
		String winState = "9";
		
		int ii = 0;
		//1、先按期重新计算报表
		//先把要该日期的报表统计数据删除
		settledReportPetPeriodDao.deleteSettledReportPet(periodsNum, lotteryType, schema);
		settledReportRPeriodDao.deleteSettledReportR(periodsNum, lotteryType, schema);
		classReportPetPeriodDao.deleteClassReportPetPeriods(periodsNum, lotteryType, schema);
		classReportRPeriodDao.deleteClassReportRPeriods(periodsNum, lotteryType, schema);
		
		
		//获取所有管理员
		List<ManagerStaff> managerList  = this.queryAllManagerUser(scanTableListFindUser,null,null,periodsNum,schema);
		for(ManagerStaff managerStaff:managerList){
			Long userId = managerStaff.getID();
			String userType = managerStaff.getUserType();
			LOG.info("~~~~~~~no:"+ii+"~~~~~userid is :"+userId+"~~~~~usertype is :"+userType+"~~~~~periodsNum is :"+periodsNum);
			ii+=1;
			//生成交收统计报表
			Date bettingDate = null;
			List<DeliveryReportEric> resultList = findSettledReport(bettingDate, bettingDate, lotteryTypeFrm, vPlayType, periodsNum, 
					userId, userType,scanTableList,winState,schema);
			//计算投注总额
			DeliveryReportEric totalEntity = null;
			if (null != resultList && resultList.size() > 0) {
				totalEntity = new DeliveryReportEric();
				Double rateMoney = (double) 0;
				
				for(DeliveryReportEric vo : resultList){
					rateMoney += vo.getRateMoney();
				}
				totalEntity.setRateMoney(rateMoney);
			}
			
			for(DeliveryReportEric resultEntity:resultList){
				DeliveryReportPetPeriod reportInfo = new DeliveryReportPetPeriod();
				reportInfo.setUserID(managerStaff.getID());
				reportInfo.setUserType(managerStaff.getUserType());
				reportInfo.setBettingUserID(resultEntity.getUserID());
				reportInfo.setBettingUserType(resultEntity.getUserType());
				reportInfo.setParentUserType(resultEntity.getParentUserType());
				reportInfo.setSubordinate(resultEntity.getSubordinate());  	
				reportInfo.setUserName(resultEntity.getUserName());
				reportInfo.setTurnover(resultEntity.getTurnover());
				reportInfo.setAmount(resultEntity.getAmount());
				reportInfo.setMoneyRateAgent(resultEntity.getMoneyRateAgent());
				reportInfo.setMoneyRateGenAgent(resultEntity.getMoneyRateGenAgent());
				reportInfo.setMoneyRateStockholder(resultEntity.getMoneyRateStockholder());
				reportInfo.setMoneyRateBranch(resultEntity.getMoneyRateBranch());
				reportInfo.setMoneyRateChief(resultEntity.getMoneyRateChief());
				reportInfo.setRateMoney(resultEntity.getRateMoney());
				
				reportInfo.setMemberAmount(resultEntity.getMemberAmount());
				reportInfo.setSubordinateAmountWin(resultEntity.getSubordinateAmountWin());
				reportInfo.setSubordinateAmountBackWater(resultEntity.getSubordinateAmountBackWater());
				reportInfo.setRealWin(resultEntity.getRealWin());
				reportInfo.setRealBackWater(resultEntity.getRealBackWater());
				reportInfo.setCommission(resultEntity.getCommission());
				reportInfo.setBettingDate(resultEntity.getBettingDate());
				reportInfo.setPeriodsNum(resultEntity.getPeriodNum());
				reportInfo.setLotteryType(lotteryType);
				
				//处理占货比，计算明细记录中的占货比、合计数据中的实占输赢、实占退水、贡献上级
				if (null != resultList && null != totalEntity) {
					Double realResult = totalEntity.getRateMoney();//获取合计数据中的投注总额的值
					reportInfo.calRealResultPer(realResult);//占货比      实占金额/实占金额合计
				}
				
				settledReportPetPeriodDao.insertSettledReportPetPeriods(reportInfo,schema);
				
			}
			
			//处理补出货信息
			
			List<DeliveryReportEric> rlist = queryReplenish(bettingDate, bettingDate,userId, userType
					,vPlayType, periodsNum,lotteryTypeFrm,winState,replenishTableName,schema);
			for(DeliveryReportEric resultEntity:rlist){
				DeliveryReportRPeriod reportInfoR = new DeliveryReportRPeriod();
				reportInfoR.setUserID(managerStaff.getID());
				reportInfoR.setUserType(managerStaff.getUserType());
				reportInfoR.setTurnover(resultEntity.getTurnover());
				reportInfoR.setAmount(resultEntity.getAmount());
				reportInfoR.setMemberAmount(resultEntity.getMemberAmount());
				reportInfoR.setWinBackWater(resultEntity.getWinBackWater());
				reportInfoR.setBackWaterResult(resultEntity.getBackWaterResult());
				reportInfoR.setBettingDate(resultEntity.getBettingDate());
				reportInfoR.setPeriodsNum(resultEntity.getPeriodNum());
				reportInfoR.setLotteryType(lotteryType);
				
				settledReportRPeriodDao.insertSettledReportRPeriods(reportInfoR,schema);
				
			}
			
			
			//生成分类报表
			List<ClassReportEric> resultListClass = classReportEricLogic
					.findClassReport(bettingDate, bettingDate, lotteryTypeFrm, vPlayType, periodsNum, userId, userType,
							scanTableList,winState,schema);
			
			for(ClassReportEric resultEntity:resultListClass){
				ClassReportPetPeriod reportInfo = new ClassReportPetPeriod();
				reportInfo.setUserID(managerStaff.getID());
				reportInfo.setUserType(managerStaff.getUserType());
				reportInfo.setTurnover(resultEntity.getTurnover());
				reportInfo.setAmount(resultEntity.getAmount());
				reportInfo.setMoneyRateAgent(resultEntity.getMoneyRateAgent());
				reportInfo.setMoneyRateGenAgent(resultEntity.getMoneyRateGenAgent());
				reportInfo.setMoneyRateStockholder(resultEntity.getMoneyRateStockholder());
				reportInfo.setMoneyRateBranch(resultEntity.getMoneyRateBranch());
				reportInfo.setMoneyRateChief(resultEntity.getMoneyRateChief());
				reportInfo.setRateMoney(resultEntity.getRateMoney());
				
				reportInfo.setMemberAmount(resultEntity.getMemberAmount());
				reportInfo.setSubordinateAmountWin(resultEntity.getSubordinateAmountWin());
				reportInfo.setSubordinateAmountBackWater(resultEntity.getSubordinateAmountBackWater());
				reportInfo.setRealWin(resultEntity.getRealWin());
				reportInfo.setRealBackWater(resultEntity.getRealBackWater());
				reportInfo.setCommission(resultEntity.getCommission());
				reportInfo.setBettingDate(resultEntity.getBettingDate());
				reportInfo.setCommissionType(resultEntity.getCommissionType());
				reportInfo.setPeriodsNum(resultEntity.getPeriodNum());
				reportInfo.setLotteryType(lotteryType);
				
				classReportPetPeriodDao.insertClassReportPetPeriods(reportInfo,schema);
				
			}
			
			//处理补出货信息
			List<ClassReportEric> rlistClass = classReportEricLogic.queryReplenish(bettingDate, bettingDate,userId, userType,
					vPlayType, periodsNum,lotteryTypeFrm,winState,replenishTableName,schema);
			for(ClassReportEric resultEntity:rlistClass){
				ClassReportRPeriod reportInfo = new ClassReportRPeriod();
				reportInfo.setUserID(managerStaff.getID());
				reportInfo.setUserType(managerStaff.getUserType());
				reportInfo.setTurnover(resultEntity.getTurnover());
				reportInfo.setAmount(resultEntity.getAmount());
				reportInfo.setMemberAmount(resultEntity.getMemberAmount());
				reportInfo.setWinBackWater(resultEntity.getWinBackWater());
				reportInfo.setBackWaterResult(resultEntity.getBackWaterResult());
				reportInfo.setCommissionType(resultEntity.getCommissionType());
				reportInfo.setBettingDate(resultEntity.getBettingDate());
				reportInfo.setPeriodsNum(resultEntity.getPeriodNum());
				reportInfo.setLotteryType(lotteryType);
				
				classReportRPeriodDao.insertClassReportRPeriods(reportInfo,schema);
				
			}
		}
		
		long endClass = System.currentTimeMillis();
		LOG.info("<--按盘期生成统计报表 结束  所用时间："+(endClass-startTime)/1000+" 秒 -->");
		
	}
	
	@Override
	public List<DeliveryReportPetList> queryDeliveryReportPetList(Long userID,String userType,java.sql.Date startDate,java.sql.Date endDate){
		return settledReportEricDao.queryDeliveryReportPetList(userID, userType, startDate, endDate);
		
	}
	@Override
	public List<DeliveryReportRList> queryDeliveryReportRList(Long userID,String userType,java.sql.Date startDate,java.sql.Date endDate){
		return settledReportEricDao.queryDeliveryReportRList(userID, userType, startDate, endDate);
		
	}
	
	@Override
	public List<DeliveryReportEric> queryDeliveryReportPetPeriod(Long userID,String userType,java.sql.Date startDate,
			java.sql.Date endDate,String periodNum, String playType,String schema){
		return settledReportEricDao.queryDeliveryReportPetPeriod(userID, userType, startDate, endDate,periodNum,playType,schema);
		
	}
	@Override
	public List<DeliveryReportEric> queryDeliveryReportRPeriod(Long userID,String userType,java.sql.Date startDate,
			java.sql.Date endDate,String periodNum, String playType,String schema){
		return settledReportEricDao.queryDeliveryReportRPeriod(userID, userType, startDate, endDate,periodNum,playType,schema);
		
	}
	
	@Override
	public List<ManagerStaff> queryAllManagerUser(String[] scanTableList, Date startDate, Date endDate,String periodNum,String schema) {
		return settledReportEricDao.queryAllManagerUser(scanTableList, startDate, endDate,periodNum,schema);
	}
	
	@Override
	public List<ManagerStaff> queryAllManagerUserInReportPeriod(String[] scanTableList, Date startDate, Date endDate,String periodNum, String schema) {
		return settledReportEricDao.queryAllManagerUserInReportPeriod(scanTableList, startDate, endDate,periodNum, schema);
	}
	
	/*@Override
	public void saveReportPetList(DeliveryReportPetList entity) {
		settledReportPetListDao.save(entity);
		settledReportPetListDao.flush();
	}*/
	@Override
	public void updateReportPetList(DeliveryReportPetList entity) {
		settledReportPetListDao.update(entity);
		settledReportPetListDao.flush();
	}
	/*@Override
	public void saveReportRList(DeliveryReportRList entity) {
		settledReportRListDao.save(entity);
		settledReportRListDao.flush();
	}*/
	@Override
	public void updateReportRList(DeliveryReportRList entity) {
		settledReportRListDao.update(entity);
		settledReportRListDao.flush();
	}
	/*@Override
	public void saveReportPetPeriod(DeliveryReportPetPeriod entity) {
		settledReportPetPeriodDao.save(entity);
		settledReportPetPeriodDao.flush();
	}*/
	/*@Override
	public void saveReportRPeriod(DeliveryReportRPeriod entity) {
		settledReportRPeriodDao.save(entity);
		settledReportRPeriodDao.flush();
	}*/
	@Override
	public List<DeliveryReportPetList> queryReportPetList(Criterion...criterias) {
		return settledReportPetListDao.find(criterias); 
	}
	@Override
	public List<DeliveryReportRList> queryReportRList(Criterion...criterias) {
		return settledReportRListDao.find(criterias); 
	}
	@Override
	public void deleteReportPetList(String rStart,String rEnd,String schema) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] strStart = rStart.split("-");
		String[] strEnd = rEnd.split("-");
		String head = strStart[0]+'-'+strStart[1];
		Integer startDate = Integer.valueOf(strStart[2]);
        Integer endDate = Integer.valueOf(strEnd[2]);
        for(int i=startDate;i<=endDate;i++){
        	java.util.Date date=sdf.parse(head+'-'+i);
			/*String hql = "delete DeliveryReportPetList where bettingDate=to_date(?,'yyyy-MM-dd')";
			settledReportPetListDao.batchExecute(hql, sdf.format(date));*/
        	settledReportPetListDao.deleteSettledReportList(sdf.format(date), schema);
        }
	}
	@Override
	public void deleteReportRList(String rStart,String rEnd,String schema) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] strStart = rStart.split("-");
		String[] strEnd = rEnd.split("-");
		String head = strStart[0]+'-'+strStart[1];
		Integer startDate = Integer.valueOf(strStart[2]);
        Integer endDate = Integer.valueOf(strEnd[2]);
        for(int i=startDate;i<=endDate;i++){
        	java.util.Date date=sdf.parse(head+'-'+i);
			/*String hql = "delete DeliveryReportRList where bettingDate=to_date(?,'yyyy-MM-dd')";
			settledReportRListDao.batchExecute(hql, sdf.format(date));*/
        	settledReportRListDao.deleteSettledReportRList(sdf.format(date), schema);
        }
	}
/*	@Override
	public void deleteReportPetPeriod(String periodsNum,String playType) throws ParseException {
		String hql = "delete DeliveryReportPetPeriod where periods_num=? and lotteryType=?";
		settledReportPetListDao.batchExecute(hql, periodsNum, playType);
	}
	@Override
	public void deleteReportRPeriod(String periodsNum,String playType) throws ParseException {
			String hql = "delete DeliveryReportRPeriod where periods_num=? and lotteryType=?";
			settledReportRListDao.batchExecute(hql, periodsNum, playType);
	}*/
	@Override
	public void deleteReportPetPeriod(String periodNum,String lotteryType,String schema) throws ParseException {
		settledReportPetPeriodDao.deleteSettledReportPet(periodNum, lotteryType, schema);
	}
	@Override
	public void deleteReportRPeriod(String periodNum,String lotteryType,String schema) throws ParseException {
		settledReportRPeriodDao.deleteSettledReportR(periodNum, lotteryType, schema);
	}
	
	private IClassReportEricLogic classReportEricLogic = null;//报表统计 S
	
	public ISubAccountInfoLogic getSubAccountInfoLogic() {
		return subAccountInfoLogic;
	} 

	public void setSubAccountInfoLogic(ISubAccountInfoLogic subAccountInfoLogic) {
		this.subAccountInfoLogic = subAccountInfoLogic;
	}

	public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}

	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}

	public IReportStatusLogic getReportStatusLogic() {
		return reportStatusLogic;
	}
	public void setReportStatusLogic(IReportStatusLogic reportStatusLogic) {
		this.reportStatusLogic = reportStatusLogic;
	}
	public TaskExecutor getWcpTaskExecutor() {
		return wcpTaskExecutor;
	}
	public void setWcpTaskExecutor(TaskExecutor wcpTaskExecutor) {
		this.wcpTaskExecutor = wcpTaskExecutor;
	}
	public ISettledReportEricDao getSettledReportEricDao() {
		return settledReportEricDao;
	}

	public void setSettledReportEricDao(ISettledReportEricDao settledReportEricDao) {
		this.settledReportEricDao = settledReportEricDao;
	}

	public ISettledReportPetListDao getSettledReportPetListDao() {
		return settledReportPetListDao;
	}

	public void setSettledReportPetListDao(
			ISettledReportPetListDao settledReportPetListDao) {
		this.settledReportPetListDao = settledReportPetListDao;
	}

	public ISettledReportPetPeriodDao getSettledReportPetPeriodDao() {
		return settledReportPetPeriodDao;
	}
	public void setSettledReportPetPeriodDao(
			ISettledReportPetPeriodDao settledReportPetPeriodDao) {
		this.settledReportPetPeriodDao = settledReportPetPeriodDao;
	}
	public ISettledReportRPeriodDao getSettledReportRPeriodDao() {
		return settledReportRPeriodDao;
	}
	public void setSettledReportRPeriodDao(
			ISettledReportRPeriodDao settledReportRPeriodDao) {
		this.settledReportRPeriodDao = settledReportRPeriodDao;
	}
	public IClassReportPetPeriodDao getClassReportPetPeriodDao() {
		return classReportPetPeriodDao;
	}
	public void setClassReportPetPeriodDao(
			IClassReportPetPeriodDao classReportPetPeriodDao) {
		this.classReportPetPeriodDao = classReportPetPeriodDao;
	}
	public IClassReportRPeriodDao getClassReportRPeriodDao() {
		return classReportRPeriodDao;
	}
	public void setClassReportRPeriodDao(
			IClassReportRPeriodDao classReportRPeriodDao) {
		this.classReportRPeriodDao = classReportRPeriodDao;
	}
	public ISettledReportRListDao getSettledReportRListDao() {
		return settledReportRListDao;
	}

	public void setSettledReportRListDao(
			ISettledReportRListDao settledReportRListDao) {
		this.settledReportRListDao = settledReportRListDao;
	}
	public IClassReportEricDao getClassReportEricDao() {
		return classReportEricDao;
	}
	public void setClassReportEricDao(IClassReportEricDao classReportEricDao) {
		this.classReportEricDao = classReportEricDao;
	}
	public IClassReportPetListDao getClassReportPetListDao() {
		return classReportPetListDao;
	}
	public void setClassReportPetListDao(
			IClassReportPetListDao classReportPetListDao) {
		this.classReportPetListDao = classReportPetListDao;
	}
	public IClassReportRListDao getClassReportRListDao() {
		return classReportRListDao;
	}
	public void setClassReportRListDao(IClassReportRListDao classReportRListDao) {
		this.classReportRListDao = classReportRListDao;
	}
	public IClassReportEricLogic getClassReportEricLogic() {
		return classReportEricLogic;
	}
	public void setClassReportEricLogic(IClassReportEricLogic classReportEricLogic) {
		this.classReportEricLogic = classReportEricLogic;
	}

}
