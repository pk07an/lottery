package com.npc.lottery.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.npc.lottery.member.entity.PlayAmount;

@Repository
public class PlayAmountFrontDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 批量通过ID更新赔率方法
	 * 
	 * @param shopsPlayOddsList
	 */
	public void updatePlayAmountBatchById(final List<PlayAmount> playAmountList, String scheme) {
		final String sql = "UPDATE " + scheme + ".TB_PLAY_AMOUNT SET MONEY_AMOUNT=? WHERE ID=?";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				PlayAmount PlayAmount = playAmountList.get(i);
				ps.setDouble(1, PlayAmount.getMoneyAmount());
				ps.setLong(2, PlayAmount.getId());

			}

			@Override
			public int getBatchSize() {
				return playAmountList.size();
			}

		});
	}

	public List<PlayAmount> getPlayAmountByShopCodeAndPlayType(String shopCode, String playType, String scheme) {
		final String sql = " select * from " + scheme + ".tb_play_amount where shops_code=? and play_Type=?";
		Object[] parameter = new Object[] { shopCode, playType };
		List<PlayAmount> playAmountlist = (List<PlayAmount>) jdbcTemplate.query(sql, parameter, new PlayAmountMappper());
		return playAmountlist;
	}

	class PlayAmountMappper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			PlayAmount playAmount = new PlayAmount();
			playAmount.setId(rs.getLong("ID"));
			playAmount.setCommissionType(rs.getString("COMMISSION_TYPE"));
			playAmount.setMoneyAmount(rs.getDouble("MONEY_AMOUNT"));
			playAmount.setPeriodsNum(rs.getString("PERIODS_NUM"));
			playAmount.setPlayType(rs.getString("PLAY_TYPE"));
			playAmount.setShopCode(rs.getString("SHOPS_CODE"));
			playAmount.setTypeCode(rs.getString("TYPE_CODE"));
			playAmount.setUpdateTime(rs.getDate("UPDATE_TIME"));
			return playAmount;
		}

	}

}
