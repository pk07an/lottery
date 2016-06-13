package com.npc.lottery.statreport.dao.hibernate;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.statreport.dao.interf.IClassReportRPeriodDao;
import com.npc.lottery.statreport.entity.ClassReportRPeriod;
import com.npc.lottery.statreport.entity.DeliveryReportRPeriod;

public class ClassReportRPeriodDao extends HibernateDao<ClassReportRPeriod ,Long> implements IClassReportRPeriodDao{
	private static Logger log = Logger.getLogger(ClassReportRPeriodDao.class);
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public void insertClassReportRPeriods(ClassReportRPeriod reportInfo,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
		String sqlLog = "insert into "+schema+"TB_C_REPORT_R_Period (ID,USER_ID,USER_TYPE,COUNT,AMOUNT,MEMBER_AMOUNT,"
				+ "WIN_BACK_WATER,BACK_WATER_RESULT,PERIODS_NUM,BETTING_DATE,LOTTERY_TYPE,COMMISSION_TYPE) "+
						" VALUES (SEQ_TB_C_REPORT_R_Period.nextval,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] parameters = new Object[] { reportInfo.getUserID(), reportInfo.getUserType(), reportInfo.getTurnover(), 
				reportInfo.getAmount(), reportInfo.getMemberAmount(),reportInfo.getWinBackWater(),
				reportInfo.getBackWaterResult(),reportInfo.getPeriodsNum(),reportInfo.getBettingDate(),
				reportInfo.getLotteryType(),reportInfo.getCommissionType()};
		jdbcTemplate.update(sqlLog, parameters);
    }
    
    @Override
    public void deleteClassReportRPeriods(String periodsNum,String lotteryType,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
    		schema = schema + ".";
    	}
    	String sqlLog = "delete "+schema+"TB_C_REPORT_R_Period where PERIODS_NUM=? and LOTTERY_TYPE=?";
    	Object[] parameters = new Object[] { periodsNum,lotteryType};
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
