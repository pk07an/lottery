package com.npc.lottery.odds.dao.hibernate;

import org.springframework.jdbc.core.JdbcTemplate;

import com.npc.lottery.odds.dao.interf.IOpenPlayOddsJdbcDao;

public class OpenPlayOddsJdbcDao implements IOpenPlayOddsJdbcDao {
	private JdbcTemplate jdbcTemplate;

	@Override
	public void updateRealOddsFromOpenOdds(String shopCode,String type) {		
		String sql="update tb_shops_play_odds a " +
				"set a.real_odds = " +
				"(select b.opening_odds " +
				         "from tb_open_play_odds b " +
				         "where a.shops_code=b.shops_code " +
				               "and a.odds_type=b.odds_type " +
				               "and a.shops_code='"+ shopCode + "'" +
				               "and odds_type like '" + type+ "%')" +
				         " where exists " +
				              "(select b.opening_odds " +
				              "from tb_open_play_odds b " +
				              "where a.shops_code=b.shops_code " +
				                    "and a.odds_type=b.odds_type " +
				                    "and a.shops_code='"+ shopCode + "' " +
				                    "and odds_type like '" + type+ "%')";
		
		jdbcTemplate.execute(sql);
		
	}
	
	@Override
	public void saveOpenPlayOddsForAddShop(String shopCode,Long userId) {		
		String sql="Insert into tb_open_play_odds(id,shops_code," +
				"opening_update_date,opening_update_user,create_user,create_time," +
				"auto_odds_quotas,auto_odds,odds_type,lowest_odds," +
				"opening_odds,bigest_odds,cut_odds_b,cut_odds_c)" +
				" select seq_tb_open_play_odds.nextval," + shopCode + 
				",sysdate," + userId + "," + userId + ", sysdate,d.*" +
				" from (select distinct b.auto_odds_quotas, b.auto_odds,b.odds_type," +
				"b.lowest_odds,b.odds,b.bigest_odds,b.cut_odds_b,b.cut_odds_c" +
				" from tb_default_play_odds b) d";
		jdbcTemplate.execute(sql);
		
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
