package com.npc.lottery.user.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.user.dao.interf.IUserCommissionDefaultDao;
import com.npc.lottery.user.entity.UserCommissionDefault;

public class UserCommissionDefaultDao extends HibernateDao<UserCommissionDefault, Long> implements IUserCommissionDefaultDao {

	private JdbcTemplate jdbcTemplate;

	@Override
	public List<UserCommissionDefault> getDefaultCommissionMapByPlayFinalType(String playFinalType, Long chiefId) {
		final String sql = "select * from tb_user_commission_default where user_id=? and play_final_type like ?";
		Object[] parameter = new Object[] { chiefId,playFinalType };
		List<UserCommissionDefault> userCommissionDefaultlist = jdbcTemplate.query(sql, parameter, new UserCommissionDefaultMapper());
		return userCommissionDefaultlist;
	}
	
	@Override
	public List<UserCommissionDefault> queryCommissionDefaultById(long userId, String scheme) {
		if(StringUtils.isNotBlank(scheme)){
			scheme=scheme+".";
		}
		final String sql = "select * from "+scheme+"tb_user_commission_default where user_id=? and play_final_type like ?";
		Object[] parameter = new Object[] { userId};
		List<UserCommissionDefault> userCommissionDefaultlist = jdbcTemplate.query(sql, parameter, new UserCommissionDefaultMapper());
		return userCommissionDefaultlist;
	}
	
	class UserCommissionDefaultMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserCommissionDefault item = new UserCommissionDefault();
			item.setID(rs.getLong("id"));
			item.setBettingQuotas(rs.getInt("BETTING_QUOTAS"));
			item.setCommissionA(rs.getDouble("COMMISSION_A"));
			item.setCommissionB(rs.getDouble("COMMISSION_B"));
			item.setCommissionC(rs.getDouble("COMMISSION_C"));
			item.setItemQuotas(rs.getInt("ITEM_QUOTAS"));
			item.setLoseQuatas(rs.getInt("TOTAL_QUOTAS"));
			item.setPlayFinalType(rs.getString("PLAY_FINAL_TYPE"));
			item.setTotalQuatas(rs.getInt("TOTAL_QUOTAS"));
			item.setUserId(rs.getLong("USER_ID"));
			item.setUserType(rs.getString("USER_TYPE"));
			item.setWinQuatas(rs.getInt("WIN_QUOTAS"));
			item.setLowestQuatas(rs.getInt("LOWEST_QUOTAS"));
			return item;
		}
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
