package com.npc.lottery.statreport.logic.interf;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.statreport.entity.ClassReportEric;
import com.npc.lottery.statreport.entity.ClassReportPetList;
import com.npc.lottery.statreport.entity.ClassReportPetPeriod;
import com.npc.lottery.statreport.entity.ClassReportRList;
import com.npc.lottery.statreport.entity.ClassReportRPeriod;
import com.npc.lottery.util.Page;

/**
 * 报表统计相关的逻辑处理类接口
 * 
 */
public interface IClassReportEricLogic {

    /**
     * 查询未结算
     * @param lotteryType 如广东，重庆，香港
     * @param playType  玩法类型，这里取拥金类型
     * @param userType  用户类型，要查询的用户类型，如股东登录，查到总代理时，这个字段就是总代理，而currentUserType就是股东
     * @param currentUserType 当前登录用户的用户类型
     * @return
     */
    public List<ClassReportEric> findClassReport(Date startDate,Date endDate,String lotteryType,
    		String playType,String periodNum,Long userid,String userType,String[] scanTableList,String winState,String schema);

	public List<ClassReportEric> queryReplenish(Date startDate, Date endDate, Long userID,String userType,
			String typeCode, String periodsNum,String lotteryType,String winState,String replenishTableName,String schema);
	
	public Page findDetail(Page page, Date startDate, Date endDate, String playType,
			String periodNum, Long userId,String userType,String[] scanTableList);

	public Page findReplenishDetail(Page page, Date startDate, Date endDate,String periodNum, Long userId, String userType,String commissionTypeCode,String lotteryType);

	/*public void saveReportPetList(ClassReportPetList entity);*/

	public void updateReportPetList(ClassReportPetList entity);

	/*public void saveReportRList(ClassReportRList entity);*/

	public void updateReportRList(ClassReportRList entity);

	public List<ClassReportPetList> queryClassReportPetList(Criterion[] criterias);

	public List<ClassReportRList> queryClassReportRList(Criterion[] criterias);

	public List<ClassReportPetList> queryClassReportPetList(Long userID,
			String userType, Date startDate, Date endDate);

	public List<ClassReportRList> queryClassReportRList(Long userID, String userType,
			Date startDate, Date endDate);

	public void deleteReportPetList(String rStart, String rEnd,String schema) throws ParseException;

	public void deleteReportRList(String rStart, String rEnd,String schema) throws ParseException;

	/*public void saveReportPetPeriod(ClassReportPetPeriod entity);

	public void saveReportRPeriod(ClassReportRPeriod entity);*/

	public void deleteReportPetPeriod(String periodNum, String lotteryType,String schema) throws ParseException;

	public void deleteReportRPeriod(String periodNum, String lotteryType,String schema) throws ParseException;

	public List<ClassReportEric> queryClassReportPetPeriod(Long userID,
			String userType, Date startDate, Date endDate, String periodNum, String lotteryType,String schema);

	public List<ClassReportEric> queryClassReportRPeriod(Long userID, String userType,
			Date startDate, Date endDate, String periodNum, String lotteryType,String schema);
    
}
