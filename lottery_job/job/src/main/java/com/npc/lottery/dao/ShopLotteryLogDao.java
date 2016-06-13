package com.npc.lottery.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.npc.lottery.model.ShopLotteryLog;

@Repository
public class ShopLotteryLogDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Logger log = Logger.getLogger(ShopLotteryLogDao.class);

	public void batchInsertShopLotteryLog(final List<ShopLotteryLog> shopLotteryLogList) {
		final String sql = " INSERT INTO TB_SHOP_LOTTERY_LOG(ID,SHOP_CODE,PERIOD_NUM,PLAY_TYPE) VALUES (SEQ_TB_SHOP_LOTTERY_LOG.NEXTVAL,?,?,?)";

		for (final ShopLotteryLog shopLotteryLog : shopLotteryLogList) {
			try {
				jdbcTemplate.update(sql, new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, shopLotteryLog.getShopCode());
						ps.setString(2, shopLotteryLog.getPeriodNum());
						ps.setString(3, shopLotteryLog.getPlayType());
					}
				});
			} catch (DataAccessException ex) {
				log.error(ex);
			}
		}
	}

	public void updateStatusByShopCodeAndPeriodNumAndPlayType(String status, String shopCode, String periodNum, String playType) {
		final String sql = "UPDATE TB_SHOP_LOTTERY_LOG SET STATUS = ? WHERE SHOP_CODE = ? AND PERIOD_NUM = ? and PLAY_TYPE = ?";
		jdbcTemplate.update(sql, status, shopCode, periodNum, playType);

	}
}
