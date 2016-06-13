package com.npc.lottery.statreport.dao.hibernate;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.statreport.dao.interf.ISettledReportRPeriodDao;
import com.npc.lottery.statreport.entity.DeliveryReportPetPeriod;
import com.npc.lottery.statreport.entity.DeliveryReportRPeriod;

public class SettledReportRPeriodDao extends HibernateDao<DeliveryReportRPeriod ,Long> implements ISettledReportRPeriodDao{
	private static Logger log = Logger.getLogger(SettledReportRPeriodDao.class);
    private JdbcTemplate jdbcTemplate;
    
    
    @Override
    public void insertSettledReportRPeriods(DeliveryReportRPeriod reportInfo,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
    	
		String sqlLog = "insert into "+schema+"TB_S_REPORT_R_Period (ID,USER_ID,USER_TYPE,COUNT,AMOUNT,MEMBER_AMOUNT,"
				+ "WIN_BACK_WATER,BACK_WATER_RESULT,PERIODS_NUM,BETTING_DATE,LOTTERY_TYPE) "+
						" VALUES (SEQ_TB_S_REPORT_R_Period.nextval,?,?,?,?,?,?,?,?,?,?)";
		Object[] parameters = new Object[] { reportInfo.getUserID(), reportInfo.getUserType(), reportInfo.getTurnover(), 
				reportInfo.getAmount(), reportInfo.getMemberAmount(),reportInfo.getWinBackWater(),
				reportInfo.getBackWaterResult(),reportInfo.getPeriodsNum(),reportInfo.getBettingDate(),reportInfo.getLotteryType()};
		jdbcTemplate.update(sqlLog, parameters);
    }
    
    @Override
    public void deleteSettledReportR(String periodsNum,String lotteryType,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
    		schema = schema + ".";
    	}
    	String sqlLog = "delete "+schema+"TB_S_REPORT_R_Period where PERIODS_NUM=? and LOTTERY_TYPE=?";
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
