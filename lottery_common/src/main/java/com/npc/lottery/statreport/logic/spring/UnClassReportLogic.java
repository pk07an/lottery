package com.npc.lottery.statreport.logic.spring;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.npc.lottery.member.dao.interf.IBetDao;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.replenish.vo.DetailVO;
import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.statreport.dao.interf.IUnClassReportDao;
import com.npc.lottery.statreport.entity.UnClassReport;
import com.npc.lottery.statreport.logic.interf.IUnClassReportLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.util.Page;

/**
 * 报表统计相关的逻辑处理类
 * 
 */
public class UnClassReportLogic implements IUnClassReportLogic {

    private IBetDao betDao;

    private IReplenishLogic replenishLogic;
    
    private IUnClassReportDao unClassReportDao;

	@Override
	public List<UnClassReport> findUnClassReport(Date startDate,Date endDate, String lotteryType, String playType,String periodNum, 
			Long userid, String userType,String[] scanTableList) {
		List<UnClassReport> result = new ArrayList<UnClassReport>();
		
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(userid);
		userInfo.setUserType(userType);
		UserVO vo = replenishLogic.getUserType(userInfo);
		
		result = unClassReportDao.queryUnClassReport(startDate, endDate, lotteryType, playType, periodNum, userid, userType,
				vo.getUserType(),vo.getRateUser(), vo.getCommissionUser(),vo.getNextColumn(),scanTableList);		
		
		return result;
	}
	
	@Override
	public Page findDetail(Page page,Date startDate,Date endDate,String playType,String periodNum, Long userId,String userType,String[] scanTableList) {
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(userId);
		userInfo.setUserType(userType);
		UserVO vo = replenishLogic.getUserType(userInfo);
		
		return unClassReportDao.queryBetDetail(page, userId, startDate, endDate, periodNum, playType,scanTableList,vo.getUserType());
	}
	
	@Override
	public Page findReplenishDetail(Page page,Date startDate,Date endDate,String periodNum, Long userId,String userType,String commissionTypeCode,String lotteryType) {
		
		Page pageResult = unClassReportDao.queryReplenishOutDetail(page, userId, commissionTypeCode, periodNum, userType, startDate, endDate, lotteryType);
		if(pageResult.getResult().size()>0){
			
			for(int i=0;i<pageResult.getResult().size();i++){
				DetailVO vo = (DetailVO)(pageResult.getResult().get(i));
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userType) || ManagerStaff.USER_TYPE_OUT_REPLENISH.equals(userType)){
					vo.setChiefRate(BigDecimal.valueOf(-100));
					vo.setChiefCommission(vo.getChiefCommission());
					vo.setWhoReplenish("總監出貨");
				}
				if(ManagerStaff.USER_TYPE_BRANCH.equals(userType)){
					vo.setBranchRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("分公司走飛");
				}
				if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType)){
					vo.setStockRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("股東走飛");
				}
				if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType)){
					vo.setGenAgentRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("總代理走飛");
				}
				if(ManagerStaff.USER_TYPE_AGENT.equals(userType)){
					vo.setAgentRate(BigDecimal.valueOf(-100));
					vo.setWhoReplenish("代理走飛");
				}
			}
		}
		return pageResult;
	}
	
	@Override
	public List<UnClassReport> queryReplenish(Date startDate, Date endDate, Long userID,String userType,String typeCode, String periodsNum,String lotteryType){
		List<UnClassReport> list = new ArrayList<UnClassReport>();
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(userID);
		userInfo.setUserType(userType);
		UserVO vo = replenishLogic.getUserType(userInfo);
		list = unClassReportDao.queryReplenish(startDate, endDate, userID, typeCode, periodsNum,vo.getRateUser(),lotteryType,userType);
		return list;
		
	}
	
	public IBetDao getBetDao() {
		return betDao;
	}

	public void setBetDao(IBetDao betDao) {
		this.betDao = betDao;
	}

	public IUnClassReportDao getUnClassReportDao() {
		return unClassReportDao;
	}

	public void setUnClassReportDao(IUnClassReportDao unClassReportDao) {
		this.unClassReportDao = unClassReportDao;
	}

	public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}

	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}

}
