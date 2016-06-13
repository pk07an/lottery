package com.npc.lottery.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;

@Repository
public class CommissionFrontDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private Logger log = Logger.getLogger(CommissionFrontDao.class);

	public List<UserCommissionDefault> getDefaultCommissionListByPlayFinalTypeLike(String playFinalType, Long chiefId) {
		final String sql = "SELECT * FROM TB_USER_COMMISSION_DEFAULT WHERE USER_ID=? AND PLAY_FINAL_TYPE LIKE ?";
		Object[] parameter = new Object[] { chiefId, playFinalType };
		List<UserCommissionDefault> userCommissionDefaultlist = jdbcTemplate.query(sql, parameter, new UserCommissionDefaultMapper());
		return userCommissionDefaultlist;
	}

	public UserCommissionDefault getDefaultCommissionByPlayFinalType(String playFinalType, Long chiefId) {
		final String sql = "SELECT * FROM TB_USER_COMMISSION_DEFAULT WHERE USER_ID=? AND PLAY_FINAL_TYPE = ?";
		Object[] parameter = new Object[] { chiefId, playFinalType };
		UserCommissionDefault userCommissionDefault = null;
		try {
			userCommissionDefault = jdbcTemplate.queryForObject(sql, parameter, new UserCommissionDefaultMapper());
		} catch (Exception ex) {
			log.error(ex);
		}
		return userCommissionDefault;
	}

	public List<UserCommission> getUserCommissionListByPlayFinalTypeLike(String playFinalTypeLike, Long userId, String scheme) {
		final String sql = "select * from " + scheme + ".tb_user_commission where user_id=? and play_final_type like ?";
		Object[] parameter = new Object[] { userId, playFinalTypeLike };
		List<UserCommission> userCommissionlist = jdbcTemplate.query(sql, parameter, new UserCommissionMapper());
		return userCommissionlist;
	}

	public UserCommission getUserCommissionByPlayFinalType(String playFinalType, Long userId, String scheme) {
		final String sql = "select * from " + scheme + ".tb_user_commission where user_id=? and play_final_type = ?";
		Object[] parameter = new Object[] { userId, playFinalType };
		UserCommission userCommission = null;
		try {
			userCommission = jdbcTemplate.queryForObject(sql, parameter, new UserCommissionMapper());
		} catch (Exception ex) {
			log.error(ex);
		}
		return userCommission;
	}

	public List<UserCommission> getUserCommissionListByPlayFinalTypeAndIds(String playFinalType, List<Long> userIdList, String scheme) {
		String userIds = StringUtils.join(userIdList, ",");
		final String sql = "select * from " + scheme + ".tb_user_commission where user_id in (" + userIds + ") and play_final_type = ?";
		Object[] parameter = new Object[] { playFinalType };
		List<UserCommission> userCommissionlist = jdbcTemplate.query(sql, parameter, new UserCommissionMapper());
		return userCommissionlist;
	}

	public List<UserCommission> getUserCommissionListByUserId(Long userId, String scheme) {
		final String sql = "select * from " + scheme + ".tb_user_commission where user_id=?  order by id asc";
		Object[] parameter = new Object[] { userId };
		List<UserCommission> userCommissionlist = jdbcTemplate.query(sql, parameter, new UserCommissionMapper());
		return userCommissionlist;
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
			item.setPlayType(rs.getString("PLAY_TYPE"));
			return item;
		}
	}

	class UserCommissionMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserCommission item = new UserCommission();
			item.setID(rs.getLong("id"));
			item.setBettingQuotas(rs.getInt("BETTING_QUOTAS"));
			item.setCommissionA(rs.getDouble("COMMISSION_A"));
			item.setCommissionB(rs.getDouble("COMMISSION_B"));
			item.setCommissionC(rs.getDouble("COMMISSION_C"));
			item.setItemQuotas(rs.getInt("ITEM_QUOTAS"));
			item.setPlayFinalType(rs.getString("PLAY_FINAL_TYPE"));
			item.setUserId(rs.getLong("USER_ID"));
			item.setUserType(rs.getString("USER_TYPE"));
			item.setPlayType(rs.getString("PLAY_TYPE"));
			return item;
		}
	}
}
