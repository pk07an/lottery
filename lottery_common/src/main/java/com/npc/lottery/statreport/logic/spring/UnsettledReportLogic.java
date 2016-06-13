package com.npc.lottery.statreport.logic.spring;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.npc.lottery.member.dao.interf.IBetDao;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.replenish.vo.DetailVO;
import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.statreport.dao.interf.IStatReportDao;
import com.npc.lottery.statreport.dao.interf.IUnsettledReportDao;
import com.npc.lottery.statreport.entity.DeliveryUnReport;
import com.npc.lottery.statreport.entity.StatReport;
import com.npc.lottery.statreport.logic.interf.IUnsettledReportLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;
import com.npc.lottery.util.Page;

/**
 * 报表统计相关的逻辑处理类
 * 
 */
public class UnsettledReportLogic implements IUnsettledReportLogic {

    private IStatReportDao statReportDao;
    
    private IBetDao betDao;

    private ISubAccountInfoLogic subAccountInfoLogic;//子账号
    private IReplenishLogic replenishLogic;
    
    private IUnsettledReportDao unsettledReportDao;
	public void setStatReportDao(IStatReportDao statReportDao) {
        this.statReportDao = statReportDao;
    }

    /**
     * 根据玩法类型查询对应的赔率类型列表
     * 
     * @param playType
     *            玩法类型
     *            
     * @return  PlayType 类型的 List
     */
    public ArrayList<PlayType> findCommissionTypeList(String playType) {
        return (ArrayList<PlayType>) statReportDao
                .findCommissionTypeList(playType);
    }

    /**
     * 根据佣金类型查询对应的投注类型数据
     * 
     * @param commissionType 佣金类型
     * @return
     */
    public ArrayList<PlayType> findPlayTypeByCommission(String commissionType) {
        return (ArrayList<PlayType>) statReportDao
                .findPlayTypeByCommission(commissionType);
    }

    /**
     * 查询赔率类型列表
     * 
     * @return
     */
    public ArrayList<PlayType> findCommissionTypeList() {

        ArrayList<PlayType> result = new ArrayList<PlayType>();

        //广东快乐十分
        result.addAll(this.findCommissionTypeList(StatReport.PLAY_TYPE_GDKLSF));
        //重庆时时彩
        result.addAll(this.findCommissionTypeList(StatReport.PLAY_TYPE_CQSSC));
        //K3
        result.addAll(this.findCommissionTypeList(StatReport.PLAY_TYPE_K3));

        return result;
    }

	@Override
	public List<DeliveryUnReport> findUnSettledReport(Date startDate,Date endDate, String lotteryType, String playType,String periodNum, 
			Long userid, String userType,String[] scanTableList) {
		List<DeliveryUnReport> result = new ArrayList<DeliveryUnReport>();
		
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(userid);
		userInfo.setUserType(userType);
		UserVO vo = replenishLogic.getUserType(userInfo);
		
		result = unsettledReportDao.queryUnSettledReport(startDate, endDate, lotteryType, playType, periodNum, userid, userType,
				vo.getUserType(),vo.getRateUser(), vo.getCommissionUser(),vo.getNextColumn(),scanTableList);		
		
		return result;
	}
	
	@Override
	public Page findDetail(Page page,Date startDate,Date endDate,String playType,String periodNum, Long userId,String[] scanTableList) {
		return unsettledReportDao.queryBetDetail(page, userId, startDate, endDate, periodNum, playType,scanTableList);
	}
	
	@Override
	public Page findReplenishDetail(Page page,Date startDate,Date endDate,String periodNum, Long userId,
			String userType,String commissionTypeCode,String lotteryType,String currentUserType) {
		
		Page pageResult = unsettledReportDao.queryReplenishOutDetail(page, userId, commissionTypeCode, periodNum, userType, startDate, endDate, lotteryType);
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
	public List<DeliveryUnReport> queryReplenish(Date startDate, Date endDate, Long userID,String userType,String typeCode, String periodsNum,String lotteryType){
		List<DeliveryUnReport> list = new ArrayList<DeliveryUnReport>();
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(userID);
		userInfo.setUserType(userType);
		UserVO vo = replenishLogic.getUserType(userInfo);
		list = unsettledReportDao.queryReplenish(startDate, endDate, userID, typeCode, periodsNum,vo.getRateUser(),lotteryType,userType);
		return list;
		
	}
	
	public Page queryGDKLSFUserBet(Page page,Date startDate, Date endDate, Long userID,String userType,String typeCode, String periodsNum)
	{
		startDate =new Date(startDate.getTime() + 60 * 60 * 1000 * 2);
		endDate = new Date(endDate.getTime() + 60 * 60 * 1000 * 26);
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(userID);
		userInfo.setUserType(userType);
		UserVO vo = replenishLogic.getUserType(userInfo);
		return unsettledReportDao.queryGDKLSFUserBetDetail(page, startDate, endDate, userID, typeCode, periodsNum, vo.getRateUser());
	}
	
	public Page queryCQSSCUserBet(Page page,Date startDate, Date endDate, Long userID,String userType,String typeCode, String periodsNum)
	{
		startDate =new Date(startDate.getTime() + 60 * 60 * 1000 * 2);
		endDate = new Date(endDate.getTime() + 60 * 60 * 1000 * 26);
		ManagerUser userInfo = new ManagerUser();
		userInfo.setID(userID);
		userInfo.setUserType(userType);
		UserVO vo = replenishLogic.getUserType(userInfo);
		return unsettledReportDao.queryCQSSCUserBetDetail(page, startDate, endDate, userID, typeCode, periodsNum, vo.getRateUser());
	}
	
	public IBetDao getBetDao() {
		return betDao;
	}

	public void setBetDao(IBetDao betDao) {
		this.betDao = betDao;
	}

	public IUnsettledReportDao getUnsettledReportDao() {
		return unsettledReportDao;
	}

	public void setUnsettledReportDao(IUnsettledReportDao unsettledReportDao) {
		this.unsettledReportDao = unsettledReportDao;
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

}
