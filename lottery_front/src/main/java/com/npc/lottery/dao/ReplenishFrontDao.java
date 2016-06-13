package com.npc.lottery.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReplenishFrontDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private final static Logger log = Logger.getLogger(ReplenishFrontDao.class);

	public Double getChiefReplenishMoneyForBetCheck(Long userID, String typeCode, String periodsNum, String scheme) {

		String sql = "select sum(money*rate_chief /100)  as money  from " + scheme + ".tb_replenish where periods_num=? and  CHIEFSTAFF =? and type_code = ? and win_state not in (4,5,6,7) ";
		Object[] parameters = new Object[] { periodsNum, userID, typeCode };
		Double sumMoney = 0D;
		try {
			sumMoney = jdbcTemplate.queryForObject(sql, parameters, Double.class);
		} catch (Exception ex) {
			log.error(ex);
		}
		return sumMoney == null ? 0 : sumMoney;
	}
}
