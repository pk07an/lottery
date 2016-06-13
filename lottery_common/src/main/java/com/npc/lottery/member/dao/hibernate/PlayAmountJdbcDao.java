package com.npc.lottery.member.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.member.dao.interf.IPlayAmountJdbcDao;
import com.npc.lottery.member.entity.PlayAmount;

/**
 * @author none
 *
 */
public class PlayAmountJdbcDao implements IPlayAmountJdbcDao {
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void savePLayAmoutForOpen(String shopCode) {		
		String sql="Insert into tb_play_amount(id,type_code,play_type,shops_Code,money_amount) " +
				"select seq_tb_play_amount.nextval,b.type_code, b.play_type," + shopCode + ", 0 from tb_play_type b";
		jdbcTemplate.execute(sql);
		
	}
	
	/**
	 * 批量通过ID更新赔率方法
	 * 
	 * @param shopsPlayOddsList
	 */
	@Override
	public void updatePlayAmountBatchById(final List<PlayAmount> playAmountList) {
		final String sql = "UPDATE TB_PLAY_AMOUNT SET MONEY_AMOUNT=? WHERE ID=?";
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

	@Override
	public List<PlayAmount> getPlayAmountByShopCodeAndPlayType(String shopCode, String playType) {
		final String sql = " select * from tb_play_amount where shops_code=? and play_Type=?";
		Object[] parameter = new Object[] { shopCode, playType };
		List<PlayAmount> playAmountlist = (List<PlayAmount>)jdbcTemplate.query(sql, parameter, new PlayAmountMappper());
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

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
}
