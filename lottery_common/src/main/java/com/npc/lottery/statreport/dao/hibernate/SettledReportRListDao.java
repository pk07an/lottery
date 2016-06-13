package com.npc.lottery.statreport.dao.hibernate;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.statreport.dao.interf.ISettledReportRListDao;
import com.npc.lottery.statreport.entity.DeliveryReportPetList;
import com.npc.lottery.statreport.entity.DeliveryReportRList;

public class SettledReportRListDao extends HibernateDao<DeliveryReportRList ,Long> implements ISettledReportRListDao{
	private static Logger log = Logger.getLogger(SettledReportRListDao.class);
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public void insertSettledReportRList(DeliveryReportRList reportInfo,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
    	
		String sqlLog = "insert into "+schema+"TB_SETTLED_REPORT_R_LIST (ID, USER_TYPE, COUNT, AMOUNT, "
				+ "MEMBER_AMOUNT, WIN_BACK_WATER, BACK_WATER_RESULT, BETTING_DATE, USER_ID) "+
						" VALUES (SEQ_TB_SETTLED_REPORT_R_LIST.nextval,?,?,?,?,?,?,?,?)";
		Object[] parameters = new Object[] { reportInfo.getUserType(), reportInfo.getTurnover(), 
				reportInfo.getAmount(), reportInfo.getMemberAmount(),reportInfo.getWinBackWater(),
				reportInfo.getBackWaterResult(),reportInfo.getBettingDate(),reportInfo.getUserID()};
		jdbcTemplate.update(sqlLog, parameters);
    }
    
    @Override
    public void deleteSettledReportRList(String date,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
    		schema = schema + ".";
    	}
    	String sqlLog = "delete "+schema+"TB_SETTLED_REPORT_R_LIST where BETTING_DATE=to_date(?,'yyyy-MM-dd')";
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
