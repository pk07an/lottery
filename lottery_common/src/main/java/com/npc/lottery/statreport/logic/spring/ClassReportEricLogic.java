package com.npc.lottery.statreport.logic.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.replenish.vo.DetailVO;
import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.statreport.dao.interf.IClassReportEricDao;
import com.npc.lottery.statreport.dao.interf.IClassReportPetListDao;
import com.npc.lottery.statreport.dao.interf.IClassReportPetPeriodDao;
import com.npc.lottery.statreport.dao.interf.IClassReportRListDao;
import com.npc.lottery.statreport.dao.interf.IClassReportRPeriodDao;
import com.npc.lottery.statreport.dao.interf.ISettledReportPetListDao;
import com.npc.lottery.statreport.dao.interf.ISettledReportRListDao;
import com.npc.lottery.statreport.entity.ClassReportEric;
import com.npc.lottery.statreport.entity.ClassReportPetList;
import com.npc.lottery.statreport.entity.ClassReportPetPeriod;
import com.npc.lottery.statreport.entity.ClassReportRList;
import com.npc.lottery.statreport.entity.ClassReportRPeriod;
import com.npc.lottery.statreport.entity.DeliveryReportEric;
import com.npc.lottery.statreport.entity.DeliveryReportPetList;
import com.npc.lottery.statreport.entity.DeliveryReportRList;
import com.npc.lottery.statreport.logic.interf.IClassReportEricLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;
import com.npc.lottery.util.Page;

/**
 * 报表统计相关的逻辑处理类
 * 
 */
public class ClassReportEricLogic implements IClassReportEricLogic {

    private ISubAccountInfoLogic subAccountInfoLogic;//子账号
    private IReplenishLogic replenishLogic;
    
    private IClassReportEricDao classReportEricDao;
    private IClassReportPetListDao classReportPetListDao;
    private IClassReportRListDao classReportRListDao;
    private IClassReportPetPeriodDao classReportPetPeriodDao;
    private IClassReportRPeriodDao classReportRPeriodDao;

	@Override
	public List<ClassReportEric> findClassReport(Date startDate,Date endDate, String lotteryType, String playType,String periodNum, 
			Long userid, String userType,String[] scanTableList,String winState,String schema) {
		List<ClassReportEric> result = new ArrayList<ClassReportEric>();
		
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
		
		result = classReportEricDao.queryClassReport(startDate, endDate, lotteryType, playType, periodNum, userid, userType,
				vo.getUserType(),vo.getRateUser(),vo.getNextRateColumn(), vo.getOutCommissionUser(),vo.getNextColumn(),
				scanTableList,underLineRateColumn,vo.getCommissionUser(),winState, schema);		
		
		return result;
	}
	
	@Override
	public Page findDetail(Page page,Date startDate,Date endDate,String playType,String periodNum, Long userId,String userType,String[] scanTableList) {
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(userId);
		userInfo.setUserType(userType);
		UserVO vo = replenishLogic.getUserType(userInfo);
		if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
			vo.setOutCommissionUser("COMMISSION_BRANCH");
		}
		return classReportEricDao.queryBetDetail(page, userId, startDate, endDate, periodNum, playType,scanTableList,vo.getRateUser(),vo.getCommissionUser(),vo.getOutCommissionUser(),vo.getUserType());
	}
	
	@Override
	public Page findReplenishDetail(Page page,Date startDate,Date endDate,String periodNum, Long userId,String userType,String commissionTypeCode,String lotteryType) {
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(userId);
		userInfo.setUserType(userType);
		UserVO userVo = replenishLogic.getUserType(userInfo);
		
		Page pageResult = classReportEricDao.queryReplenishOutDetail(page, userId, commissionTypeCode, periodNum, userType, 
				startDate, endDate, lotteryType,userVo.getRateUser(),userVo.getOutCommissionUser());
		if(pageResult.getResult().size()>0){
			
			for(int i=0;i<pageResult.getResult().size();i++){
				DetailVO vo = (DetailVO)(pageResult.getResult().get(i));
				if(ManagerStaff.USER_TYPE_CHIEF.equals(vo.getUserType()) || ManagerStaff.USER_TYPE_OUT_REPLENISH.equals(vo.getUserType())){
					vo.setChiefRate(BigDecimal.valueOf(-100));
					vo.setChiefCommission(vo.getChiefCommission());
					vo.setWhoReplenish("總監出貨");
				}
				if(ManagerStaff.USER_TYPE_BRANCH.equals(vo.getUserType())){
					vo.setBranchRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("分公司走飛");
				}
				if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(vo.getUserType())){
					vo.setStockRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("股東走飛");
				}
				if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(vo.getUserType())){
					vo.setGenAgentRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("總代理走飛");
				}
				if(ManagerStaff.USER_TYPE_AGENT.equals(vo.getUserType())){
					vo.setAgentRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("代理走飛");
				}
			}
		}
		return pageResult;
	}
	
	@Override
	public List<ClassReportEric> queryReplenish(Date startDate, Date endDate, Long userID,String userType,String typeCode, String periodsNum,
			String lotteryType,String winState,String tableName,String schema){
		List<ClassReportEric> list = new ArrayList<ClassReportEric>();
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(userID);
		userInfo.setUserType(userType);
		UserVO vo = replenishLogic.getUserType(userInfo);
		list = classReportEricDao.queryReplenish(startDate, endDate, userID, typeCode, periodsNum,
				vo.getRateUser(),lotteryType,userType,vo.getOutCommissionUser(),winState,tableName,schema);
		return list;
		
	}
	
	/*@Override
	public void saveReportPetList(ClassReportPetList entity) {
		classReportPetListDao.save(entity);
		classReportPetListDao.flush();
	}*/
	@Override
	public void updateReportPetList(ClassReportPetList entity) {
		classReportPetListDao.update(entity);
		classReportPetListDao.flush();
	}
	/*@Override
	public void saveReportRList(ClassReportRList entity) {
		classReportRListDao.save(entity);
		classReportRListDao.flush();
	}*/
	@Override
	public void updateReportRList(ClassReportRList entity) {
		classReportRListDao.update(entity);
		classReportRListDao.flush();
	}
	/*@Override
	public void saveReportPetPeriod(ClassReportPetPeriod entity) {
		classReportPetPeriodDao.save(entity);
		classReportPetPeriodDao.flush();
	}
	@Override
	public void saveReportRPeriod(ClassReportRPeriod entity) {
		classReportRPeriodDao.save(entity);
		classReportRPeriodDao.flush();
	}*/
	
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
			/*String hql = "delete ClassReportPetList where bettingDate=to_date(?,'yyyy-MM-dd')";
			classReportPetListDao.batchExecute(hql, sdf.format(date));*/
        	classReportPetListDao.deleteClassReportList(sdf.format(date), schema);
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
			/*String hql = "delete ClassReportRList where bettingDate=to_date(?,'yyyy-MM-dd')";
			classReportRListDao.batchExecute(hql, sdf.format(date));*/
        	classReportRListDao.deleteClassReportRList(sdf.format(date), schema);
        }
	}
	
	@Override
	public void deleteReportPetPeriod(String periodNum, String lotteryType,String schema) throws ParseException {
			classReportPetPeriodDao.deleteClassReportPetPeriods(periodNum, lotteryType, schema);
	}
	@Override
	public void deleteReportRPeriod(String periodNum, String lotteryType,String schema) throws ParseException {
		classReportRPeriodDao.deleteClassReportRPeriods(periodNum, lotteryType, schema);
	}
	
	@Override
	public List<ClassReportPetList> queryClassReportPetList(Criterion...criterias) {
		return classReportPetListDao.find(criterias);
	}
	
	@Override
	public List<ClassReportRList> queryClassReportRList(Criterion...criterias) {
		return classReportRListDao.find(criterias);
	}
	
	@Override
	public List<ClassReportPetList> queryClassReportPetList(Long userID,String userType,java.sql.Date startDate,java.sql.Date endDate){
		return classReportEricDao.queryClassReportPetList(userID, userType, startDate, endDate);
		
	}
	@Override
	public List<ClassReportRList> queryClassReportRList(Long userID,String userType,java.sql.Date startDate,java.sql.Date endDate){
		return classReportEricDao.queryClassReportRList(userID, userType, startDate, endDate);
		
	}
	
	@Override
	public List<ClassReportEric> queryClassReportPetPeriod(Long userID,String userType,java.sql.Date startDate,
			java.sql.Date endDate,String periodNum, String lotteryType,String schema){
		return classReportEricDao.queryClassReportPetPeriod(userID, userType, startDate, endDate,periodNum,lotteryType,schema);
		
	}
	@Override
	public List<ClassReportEric> queryClassReportRPeriod(Long userID,String userType,java.sql.Date startDate,
			java.sql.Date endDate,String periodNum, String lotteryType,String schema){
		return classReportEricDao.queryClassReportRPeriod(userID, userType, startDate, endDate,periodNum,lotteryType,schema);
		
	}
	
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


}
