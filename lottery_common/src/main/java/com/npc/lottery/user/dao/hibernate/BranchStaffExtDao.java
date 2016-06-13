package com.npc.lottery.user.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;



import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.user.dao.interf.IBranchStaffExtDao;
import com.npc.lottery.user.entity.BranchStaffExt;

public class BranchStaffExtDao extends HibernateDao<BranchStaffExt, Long> implements IBranchStaffExtDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public BranchStaffExt findById(long id,String scheme) {
		if(StringUtils.isNotBlank(scheme)){
			scheme=scheme+".";
		}
		String sql="select MANAGER_STAFF_ID,PARENT_STAFF,REPLENISHMENT,CHIEF_RATE,COMPANY_RATE,TOTAL_CREDIT_LINE,AVAILABLE_CREDIT_LINE,"+
				" OPENREPORT,LEFTOWNER,DEFAULT_COMMISSION from "+scheme+"TB_BRANCH_STAFF_EXT where MANAGER_STAFF_ID=?";
		BranchStaffExt branch=jdbcTemplate.queryForObject(sql, new Object[]{id},new BranchUserRowMapper());
		return branch;
	}
	
	class BranchUserRowMapper implements RowMapper{
		@Override
		public BranchStaffExt mapRow(ResultSet rs, int rowNum) throws SQLException {
			BranchStaffExt branch=new BranchStaffExt();
			branch.setManagerStaffID(Long.valueOf(rs.getInt("MANAGER_STAFF_ID")));
			branch.setParentStaff(rs.getInt("PARENT_STAFF"));
			branch.setReplenishment(rs.getString("REPLENISHMENT"));
			branch.setChiefRate(rs.getInt("CHIEF_RATE"));
			branch.setCompanyRate(rs.getInt("COMPANY_RATE"));
			branch.setTotalCreditLine(rs.getInt("TOTAL_CREDIT_LINE"));
			branch.setAvailableCreditLine(rs.getInt("AVAILABLE_CREDIT_LINE"));
			branch.setOpenReport(rs.getString("OPENREPORT"));
			branch.setLeftOwner(rs.getString("LEFTOWNER"));
			branch.setDefaultCommission(rs.getInt("DEFAULT_COMMISSION"));
			return branch;
		}
	}
}
