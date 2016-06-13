package com.npc.lottery.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.npc.lottery.sysmge.entity.ShopsPlayOddsLog;

@Repository
public class ShopOddsLogFrontDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void insert(final ShopsPlayOddsLog log, String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		final String sql = "INSERT INTO "+scheme+"TB_SHOPS_PLAY_ODDS_LOG "
		        + "(ID,SHOPS_CODE,PLAY_TYPE_CODE,ODDS_TYPE_X,ODDS_TYPE,REAL_ODDS_ORIGIN,REAL_UPDATE_DATE_ORIGIN,REAL_UPDATE_USER_ORIGIN,REAL_ODDS_NEW,"
		        + "REAL_UPDATE_DATE_NEW,REAL_UPDATE_USER_NEW,PERIODS_NUM,IP,REMARK,TYPE) VALUES " + "(SEQ_TB_SHOPS_PLAY_ODDS_LOG.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, log.getShopCode());
				ps.setString(2, log.getPlayTypeCode());
				ps.setString(3, log.getOddsTypeX());
				ps.setString(4, log.getOddsType());
				ps.setBigDecimal(5, log.getRealOddsOrigin());
				ps.setDate(6, new Date(log.getRealUpdateDateOrigin().getTime()));
				ps.setInt(7, log.getRealUpdateUserOrigin());
				ps.setBigDecimal(8, log.getRealOddsNew());
				ps.setDate(9, new Date(log.getRealUpdateDateNew().getTime()));
				ps.setInt(10, log.getRealUpdateUserNew());
				ps.setString(11, log.getPeriodsNum());
				ps.setString(12, log.getIp());
				ps.setString(13, log.getRemark());
				ps.setString(14, log.getType());
			}
		});
	}
}
