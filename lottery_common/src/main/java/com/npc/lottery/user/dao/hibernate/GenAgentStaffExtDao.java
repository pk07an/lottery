package com.npc.lottery.user.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.user.dao.interf.IGenAgentStaffExtDao;
import com.npc.lottery.user.entity.GenAgentStaffExt;

public class GenAgentStaffExtDao extends HibernateDao<GenAgentStaffExt, Long> implements IGenAgentStaffExtDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public GenAgentStaffExt findById(long id, String scheme) {
		if(StringUtils.isNotBlank(scheme)){
			scheme=scheme+".";
		}
		String sql="select MANAGER_STAFF_ID,PARENT_STAFF,REPLENISHMENT,GEN_AGENT_RATE,PURE_ACCOUNTED,SHAREHOLDER_RATE,TOTAL_CREDIT_LINE, "+
				"AVAILABLE_CREDIT_LINE,CHIEF_STAFF,BRANCH_STAFF,RATE_RESTRICT,BELOW_RATE_LIMIT from "+scheme+"TB_GEN_AGENT_STAFF_EXT where MANAGER_STAFF_ID=?";
		GenAgentStaffExt genAgent=jdbcTemplate.queryForObject(sql, new Object[]{id}, new GenAgentUserRowMapper());	
		return genAgent;
	}

	class GenAgentUserRowMapper implements RowMapper{
		@Override
		public GenAgentStaffExt mapRow(ResultSet rs, int rowNum) throws SQLException {
			GenAgentStaffExt genAgent=new GenAgentStaffExt();
			genAgent.setManagerStaffID(Long.valueOf(rs.getInt("MANAGER_STAFF_ID")));
			genAgent.setParentStaff(rs.getInt("PARENT_STAFF"));
			genAgent.setReplenishment(rs.getString("REPLENISHMENT"));
			genAgent.setBranchStaff(Long.valueOf(rs.getInt("BRANCH_STAFF")));
			genAgent.setPureAccounted(rs.getString("PURE_ACCOUNTED"));
			genAgent.setTotalCreditLine(rs.getInt("TOTAL_CREDIT_LINE"));
			genAgent.setAvailableCreditLine(rs.getInt("AVAILABLE_CREDIT_LINE"));
			genAgent.setShareholderRate(rs.getInt("SHAREHOLDER_RATE"));
			genAgent.setChiefStaff(Long.valueOf(rs.getInt("CHIEF_STAFF")));
			genAgent.setRateRestrict(rs.getString("RATE_RESTRICT"));
			genAgent.setBelowRateLimit(rs.getInt("BELOW_RATE_LIMIT"));
			genAgent.setGenAgentRate(rs.getInt("GEN_AGENT_RATE"));
			return genAgent;
		}
	}
}
