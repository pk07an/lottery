package com.npc.lottery.statreport.dao.hibernate;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.statreport.dao.interf.ISettledReportPetListDao;
import com.npc.lottery.statreport.entity.DeliveryReportPetList;
import com.npc.lottery.statreport.entity.DeliveryReportPetPeriod;

public class SettledReportPetListDao extends HibernateDao<DeliveryReportPetList ,Long> implements ISettledReportPetListDao{
	private static Logger log = Logger.getLogger(SettledReportPetListDao.class);
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public void insertSettledReportPetPet(DeliveryReportPetList reportInfo,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
    	
		String sqlLog = "insert into "+schema+"TB_SETTLED_REPORT_PET_LIST (ID, BETTING_USER_ID, BETTING_USER_TYPE, "
				+ "PARENT_USER_TYPE, ACCOUNT, CHNAME, COUNT, TOTAL_MONEY, MONEY_RATE_AGENT, "
				+ "MONEY_RATE_GENAGENT, MONEY_RATE_STOCKHOLDER, MONEY_RATE_BRANCH, "
				+ "MONEY_RATE_CHIEF, RATE_MONEY, MEMBER_AMOUNT, SUBORDINATE_AMOUNT_WIN, "
				+ "SUBORDINATE_AMOUNT_BACKWATER, REALWIN, REAL_BACKWATER, COMMISSION, "
				+ "BETTING_DATE, USER_ID, USER_TYPE, REAL_RESULT_PER) "+
						" VALUES (SEQ_TB_SETTLED_REPORT_PET_LIST.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] parameters = new Object[] { reportInfo.getBettingUserID(), reportInfo.getBettingUserType(), 
				reportInfo.getParentUserType(), reportInfo.getSubordinate(),reportInfo.getUserName(),
				reportInfo.getTurnover(),reportInfo.getAmount(),reportInfo.getMoneyRateAgent(),
				reportInfo.getMoneyRateGenAgent(),reportInfo.getMoneyRateStockholder(),reportInfo.getMoneyRateBranch(),
				reportInfo.getMoneyRateChief(),reportInfo.getRateMoney(),reportInfo.getMemberAmount(),
				reportInfo.getSubordinateAmountWin(),reportInfo.getSubordinateAmountBackWater(),
				reportInfo.getRealWin(),reportInfo.getRealBackWater(),reportInfo.getCommission(),
				reportInfo.getBettingDate(),reportInfo.getUserID(),reportInfo.getUserType(),reportInfo.getRealResultPer()};
		jdbcTemplate.update(sqlLog, parameters);
    }
    
    @Override
    public void deleteSettledReportList(String date,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
    		schema = schema + ".";
    	}
    	String sqlLog = "delete "+schema+"TB_SETTLED_REPORT_PET_LIST where BETTING_DATE=to_date(?,'yyyy-MM-dd')";
    	Object[] parameters = new Object[] { date};
    	jdbcTemplate.update(sqlLog, parameters);
    }
    
    
    
    @Override
    public void flush(){
    	this.getSession().flush();
    }
    
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
