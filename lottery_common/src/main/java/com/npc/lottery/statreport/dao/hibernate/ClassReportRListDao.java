package com.npc.lottery.statreport.dao.hibernate;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.statreport.dao.interf.IClassReportRListDao;
import com.npc.lottery.statreport.entity.ClassReportPetList;
import com.npc.lottery.statreport.entity.ClassReportRList;

public class ClassReportRListDao extends HibernateDao<ClassReportRList ,Long> implements IClassReportRListDao{
	private static Logger log = Logger.getLogger(ClassReportRListDao.class);
    private JdbcTemplate jdbcTemplate;
    
    
    @Override
    public void insertClassReportRList(ClassReportRList reportInfo,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
    	
		String sqlLog = "insert into "+schema+"TB_CLASS_REPORT_R_LIST (ID, USER_TYPE, COUNT, AMOUNT, "
				+ "MEMBER_AMOUNT, WIN_BACK_WATER, BACK_WATER_RESULT, "
				+ "BETTING_DATE, USER_ID, COMMISSION_TYPE) "+
						" VALUES (SEQ_TB_CLASS_REPORT_R_LIST.nextval,?,?,?,?,?,?,?,?,?)";
		Object[] parameters = new Object[] { reportInfo.getUserType(),reportInfo.getTurnover(),
				reportInfo.getAmount(),reportInfo.getMemberAmount(),reportInfo.getWinBackWater(),
				reportInfo.getBackWaterResult(),reportInfo.getBettingDate(),reportInfo.getUserID(),
				reportInfo.getCommissionType()};
		jdbcTemplate.update(sqlLog, parameters);
    }
    
    @Override
    public void deleteClassReportRList(String date,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
    		schema = schema + ".";
    	}
    	String sqlLog = "delete "+schema+"TB_CLASS_REPORT_R_LIST where BETTING_DATE=to_date(?,'yyyy-MM-dd')";
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
