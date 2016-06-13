package com.npc.lottery.statreport.dao.hibernate;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.statreport.dao.interf.IClassReportPetListDao;
import com.npc.lottery.statreport.entity.ClassReportPetList;

public class ClassReportPetListDao extends HibernateDao<ClassReportPetList ,Long> implements IClassReportPetListDao{
	private static Logger log = Logger.getLogger(ClassReportPetListDao.class);
    private JdbcTemplate jdbcTemplate;
    
    
    @Override
    public void insertClassReportPetPet(ClassReportPetList reportInfo,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
    	
		String sqlLog = "insert into "+schema+"TB_CLASS_REPORT_PET_LIST (ID, COUNT, TOTAL_MONEY, "
				+ "MONEY_RATE_AGENT, MONEY_RATE_GENAGENT, MONEY_RATE_STOCKHOLDER, "
				+ "MONEY_RATE_BRANCH, MONEY_RATE_CHIEF, RATE_MONEY, MEMBER_AMOUNT, "
				+ "SUBORDINATE_AMOUNT_WIN, SUBORDINATE_AMOUNT_BACKWATER, REALWIN, "
				+ "REAL_BACKWATER, COMMISSION, BETTING_DATE, USER_ID, USER_TYPE, COMMISSION_TYPE) "+
						" VALUES (SEQ_TB_CLASS_REPORT_PET_LIST.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] parameters = new Object[] { reportInfo.getTurnover(),reportInfo.getAmount(),
				reportInfo.getMoneyRateAgent(),reportInfo.getMoneyRateGenAgent(),
				reportInfo.getMoneyRateStockholder(),reportInfo.getMoneyRateBranch(),
				reportInfo.getMoneyRateChief(),reportInfo.getRateMoney(),reportInfo.getMemberAmount(),
				reportInfo.getSubordinateAmountWin(),reportInfo.getSubordinateAmountBackWater(),
				reportInfo.getRealWin(),reportInfo.getRealBackWater(),reportInfo.getCommission(),
				reportInfo.getBettingDate(),reportInfo.getUserID(),reportInfo.getUserType(),
				reportInfo.getCommissionType()};
		jdbcTemplate.update(sqlLog, parameters);
    }
    
    @Override
    public void deleteClassReportList(String date,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
    		schema = schema + ".";
    	}
    	String sqlLog = "delete "+schema+"TB_CLASS_REPORT_PET_LIST where BETTING_DATE=to_date(?,'yyyy-MM-dd')";
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
