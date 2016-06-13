package com.npc.lottery.user.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.user.dao.interf.IAgentStaffExtDao;
import com.npc.lottery.user.entity.AgentStaffExt;

public class AgentStaffExtDao extends HibernateDao<AgentStaffExt, Long> implements IAgentStaffExtDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public AgentStaffExt findById(long userId, String scheme) {
		if(StringUtils.isNotBlank(scheme)){
			scheme=scheme+".";
		}
		String sql="select MANAGER_STAFF_ID,PARENT_STAFF,REPLENISHMENT,PURE_ACCOUNTED,AGENT_RATE,TOTAL_CREDIT_LINE,AVAILABLE_CREDIT_LINE, "+
					"GEN_AGENT_RATE,CHIEF_STAFF,BRANCH_STAFF,STOCKHOLDER_STAFF,RATE_RESTRICT,BELOW_RATE_LIMIT from "+scheme+"TB_AGENT_STAFF_EXT " + 
					"where MANAGER_STAFF_ID=?";	
		AgentStaffExt agent=null;
		try {
			agent=jdbcTemplate.queryForObject(sql, new Object[]{userId}, new AgentUserRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return agent;
	}

	
	class AgentUserRowMapper implements RowMapper{
		@Override
		public AgentStaffExt mapRow(ResultSet rs, int rowNum) throws SQLException {
			AgentStaffExt agent=new AgentStaffExt();
			agent.setManagerStaffID(Long.valueOf(rs.getInt("MANAGER_STAFF_ID")));
			agent.setParentStaff(rs.getInt("PARENT_STAFF"));
			agent.setReplenishment(rs.getString("REPLENISHMENT"));
			agent.setBranchStaff(Long.valueOf(rs.getInt("BRANCH_STAFF")));
			agent.setPureAccounted(rs.getString("PURE_ACCOUNTED"));
			agent.setTotalCreditLine(rs.getInt("TOTAL_CREDIT_LINE"));
			agent.setAvailableCreditLine(rs.getInt("AVAILABLE_CREDIT_LINE"));
			agent.setChiefStaff(Long.valueOf(rs.getInt("CHIEF_STAFF")));
			agent.setRateRestrict(rs.getString("RATE_RESTRICT"));
			agent.setBelowRateLimit(rs.getInt("BELOW_RATE_LIMIT"));
			agent.setGenAgentRate(rs.getInt("GEN_AGENT_RATE"));
			agent.setAgentRate(rs.getInt("AGENT_RATE"));
			agent.setStockholderStaff(Long.valueOf(rs.getInt("STOCKHOLDER_STAFF")));
			return agent;
		}
	}
	
}
