package com.npc.lottery.statreport.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.statreport.dao.interf.IReportStatusDao;
import com.npc.lottery.statreport.entity.ReportStatus;

public class ReportStatusDao  extends HibernateDao<ReportStatus, Long> implements IReportStatusDao {

	
	@Override
    public void updateReportStatus(String status,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
    		schema = schema + ".";
    	}
    	String sqlLog = "update "+schema+"TB_REPORT_STATUS set STATUS=?";
    	Object[] parameters = new Object[] { status};
    	jdbcTemplate.update(sqlLog, parameters);
    }
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public ReportStatus findReportStatus(String scheme) {
		if(StringUtils.isNotEmpty(scheme)){
			scheme=scheme+".";
		}
		List<ReportStatus> rsList=new ArrayList<ReportStatus>();
		String sql="select ID,OPT,STATUS from "+scheme+"TB_REPORT_STATUS";
		SqlRowSet rs=jdbcTemplate.queryForRowSet(sql);
		while(rs.next()){
			ReportStatus reportStatus=new ReportStatus();
			reportStatus.setID(Long.valueOf(rs.getInt("ID")));
			reportStatus.setOpt(rs.getString("OPT"));
			reportStatus.setStatus(rs.getString("STATUS"));
			rsList.add(reportStatus);
		}
		return rsList.get(0);
	}
	@Override
	public void updateReportStatusByOpt(String opt, String scheme) {
		if(StringUtils.isNotEmpty(scheme)){
			scheme=scheme+".";
		}
		String sqlLog = "update "+scheme+"TB_REPORT_STATUS set OPT=?";
    	Object[] parameters = new Object[] { opt};
    	jdbcTemplate.update(sqlLog, parameters);
	}
	
	@Override
	public List<ReportStatus> findReportStatusBySchema(String schema) {
		if (StringUtils.isNotEmpty(schema)) {
    		schema = schema + ".";
    	}
		String sqlLog="selecr * from "+schema+"TB_REPORT_STATUS";
		
		List<ReportStatus> reportStatusList= jdbcTemplate.query(sqlLog, new RowMapper<ReportStatus>(){
			@Override
			public ReportStatus mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ReportStatus reportStatus=new ReportStatus();
				reportStatus.setID(rs.getLong("ID"));
				reportStatus.setOpt(rs.getString("OPT"));
				reportStatus.setStatus(rs.getString("STATUS"));
				return reportStatus;
			}
		});
		return reportStatusList;
	}
}
