package com.npc.lottery.user.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.user.dao.interf.IStockholderStaffExtDao;
import com.npc.lottery.user.entity.StockholderStaffExt;

public class StockholderStaffExtDao extends HibernateDao<StockholderStaffExt, Long> implements IStockholderStaffExtDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	

	@Override
	public StockholderStaffExt findById(long id, String scheme) {
		if(StringUtils.isNotBlank(scheme)){
			scheme=scheme+".";
		}
		String sql="select MANAGER_STAFF_ID,PARENT_STAFF,REPLENISHMENT,BRANCH_RATE,PURE_ACCOUNTED,SHAREHOLDER_RATE,TOTAL_CREDIT_LINE,AVAILABLE_CREDIT_LINE, "+
					"CHIEF_STAFF,RATE_RESTRICT,BELOW_RATE_LIMIT from "+scheme+"TB_STOCKHOLDER_STAFF_EXT where MANAGER_STAFF_ID=?";
		StockholderStaffExt stockholder=jdbcTemplate.queryForObject(sql,new Object[]{id},new StockholderUserRowMapper());
		return stockholder;
	}
	
	class StockholderUserRowMapper implements RowMapper{
		@Override
		public StockholderStaffExt mapRow(ResultSet rs, int rowNum) throws SQLException {
			StockholderStaffExt stock=new StockholderStaffExt();
			stock.setManagerStaffID(Long.valueOf(rs.getInt("MANAGER_STAFF_ID")));
			stock.setParentStaff(rs.getInt("PARENT_STAFF"));
			stock.setReplenishment(rs.getString("REPLENISHMENT"));
			stock.setBranchRate(rs.getInt("BRANCH_RATE"));
			stock.setPureAccounted(rs.getString("PURE_ACCOUNTED"));
			stock.setTotalCreditLine(rs.getInt("TOTAL_CREDIT_LINE"));
			stock.setAvailableCreditLine(rs.getInt("AVAILABLE_CREDIT_LINE"));
			stock.setShareholderRate(rs.getInt("SHAREHOLDER_RATE"));
			stock.setChiefStaff(Long.valueOf(rs.getInt("CHIEF_STAFF")));
			stock.setRateRestrict(rs.getString("RATE_RESTRICT"));
			stock.setBelowRateLimit(rs.getInt("BELOW_RATE_LIMIT"));
			return stock;
		}
	}
	
}
