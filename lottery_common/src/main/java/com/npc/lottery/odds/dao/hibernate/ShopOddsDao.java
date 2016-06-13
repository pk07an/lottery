package com.npc.lottery.odds.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.odds.dao.interf.IShopOddsDao;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.vo.OddDetail;
import com.npc.lottery.util.Page;

public class ShopOddsDao extends HibernateDao<ShopsPlayOdds, Long> implements IShopOddsDao {
	private JdbcTemplate jdbcTemplate;
	private final static Logger logger = LoggerFactory.getLogger(ShopOddsDao.class);

	public List<ShopsPlayOdds> queryShopRealOdds(String shopCode, String playType) {
		String sql = "select a.odds_type oddsType, a.state state, a.play_type_code,a.odds_type_x,a.real_odds,a.real_odds+b.cut_odds_b oddsB,a.real_odds+b.cut_odds_c oddsC from tb_shops_play_odds a ,tb_open_play_odds b where a.odds_type=b.odds_type and a.shops_code=b.shops_code and a.shops_code=? and  play_type_code like '"
		        + playType + "%'";
		Object[] parameter = new Object[] { shopCode };
		List<ShopsPlayOdds> shopCodelist = jdbcTemplate.query(sql, parameter, new ShopOddsItemMapper());

		return shopCodelist;
	}

	@Override
	public List<ShopsPlayOdds> getCurrentShopRealOddsAndId(String shopCode, String playType) {
		String sql = "select id,real_odds,play_type_code from tb_shops_play_odds where shops_code=? and  play_type_code like '" + playType + "%'";
		Object[] parameter = new Object[] { shopCode };
		List<ShopsPlayOdds> shopCodelist = jdbcTemplate.query(sql, parameter, new CurrentShopOddsItemMapper());

		return shopCodelist;
	}

	public List<ShopsPlayOdds> queryShopRealOddsByLoop(String shopCode, List<String> list) {
		List<ShopsPlayOdds> retList = new ArrayList<ShopsPlayOdds>();
		for (int i = 0; i < list.size(); i++) {
			Object[] parameters = null;
			String sql = "select a.odds_type oddsType, a.state state, a.play_type_code,a.odds_type_x,a.real_odds,a.real_odds+b.cut_odds_b oddsB,a.real_odds+b.cut_odds_c oddsC "
			        + "from tb_shops_play_odds a ," + "tb_open_play_odds b " + "where a.odds_type=b.odds_type " + "and a.shops_code=b.shops_code " + "and a.shops_code=? "
			        + "and play_type_code like ? ";
			parameters = new Object[] { shopCode, list.get(i) };
			List betlist = jdbcTemplate.query(sql, parameters, new ShopOddsItemMapper());
			retList.addAll(betlist);
		}

		return retList;
	}

	public List<ShopsPlayOdds> queryShopRealOddsGroupByBall(String shopCode, String playType, String playType2) {
		String sql = "select a.odds_type oddsType, a.state state, a.play_type_code,a.odds_type_x,a.real_odds,a.real_odds+b.cut_odds_b oddsB,a.real_odds+b.cut_odds_c oddsC "
		        + "from tb_shops_play_odds a ," + "tb_open_play_odds b " + "where a.odds_type=b.odds_type " + "and a.shops_code=b.shops_code " + "and a.shops_code=? " + "and  play_type_code like '"
		        + playType + "%' " + "union all "
		        + "select a.odds_type oddsType, a.state state, a.play_type_code,a.odds_type_x,a.real_odds,a.real_odds+b.cut_odds_b oddsB,a.real_odds+b.cut_odds_c oddsC "
		        + "from tb_shops_play_odds a ," + "tb_open_play_odds b " + "where a.odds_type=b.odds_type " + "and a.shops_code=b.shops_code " + "and a.shops_code=? " + "and  play_type_code like '"
		        + playType2 + "%'";
		Object[] parameter = new Object[] { shopCode, shopCode };
		List<ShopsPlayOdds> shopCodelist = jdbcTemplate.query(sql, parameter, new ShopOddsItemMapper());

		return shopCodelist;
	}

	public List<ShopsPlayOdds> queryOddsByTypeCode(String shopCode, String typeCode) {

		String sql = "select a.odds_type oddsType,a.state state, a.play_type_code,a.odds_type_x,a.real_odds,a.real_odds+b.cut_odds_b oddsB,a.real_odds+b.cut_odds_c oddsC from tb_shops_play_odds a ,tb_open_play_odds b where a.odds_type=b.odds_type and a.shops_code=b.shops_code and a.shops_code=? and a.play_type_code=?";
		Object[] parameter = new Object[] { shopCode, typeCode };

		List<ShopsPlayOdds> shopCodelist = jdbcTemplate.query(sql, parameter, new ShopOddsItemMapper());
		return shopCodelist;

	}

	@Override
	public void saveShopsPlayOddsForAddShop(String shopCode, Long userId) {
		String sql = "Insert into tb_shops_play_odds(id,shops_code,play_type_code,odds_type_x,odds_type,real_odds," + "real_update_date,real_update_user,state) "
		        + "select seq_tb_shops_play_odds.nextval," + shopCode + ",b.play_type_code, b.odds_type_x,b.odds_type," + "b.odds,sysdate," + userId + ", 0 from tb_default_play_odds b";
		jdbcTemplate.execute(sql);

	}

	@Override
	public Page queryShopsPlayOddsLog(Page page, String shopCode, String periodsNum, String typeCode, Date prevSearchTime, String opType) {
		String sql = "select * from (SELECT  a.play_type_code 	playTypeCode, " + " a.odds_type_x 		oddsTypeX, " + " a.odds_type        oddsType," + " a.real_odds_new    realOddsNew,"
		        + " a.real_update_date_new   realUpdateDateNew" + " FROM tb_shops_play_odds_log a " + " WHERE  " + " a.shops_code=? " + " and a.periods_num = ? " + " and a.play_type_code like ? "
		        + " and a.type = ? " + " and a.real_update_date_new > ? " + " order by a.real_update_date_new desc) where rownum <= 5";
		Object[] parameter = new Object[] { shopCode, periodsNum, typeCode, opType, prevSearchTime };
		List<OddDetail> oddsList = jdbcTemplate.query(sql, parameter, new OddsLogItemMapper());

		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > oddsList.size())
			last = oddsList.size();
		page.setTotalCount(oddsList.size());
		page.setResult(oddsList.subList(first, last));
		return page;
	}

	/**
	 * 批量通过ID更新赔率方法
	 * 
	 * @param shopsPlayOddsList
	 */
	@Override
	public void updateRealOddsBatchById(final List<ShopsPlayOdds> shopsPlayOddsList) {
		final String sql = "UPDATE TB_SHOPS_PLAY_ODDS SET REAL_UPDATE_DATE=sysdate,REAL_ODDS= ? WHERE ID=?";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ShopsPlayOdds shopsPlayOdds = shopsPlayOddsList.get(i);
				//logger.debug("更新  ShopsPlayOdds id:" + shopsPlayOdds.getId() + " || realOdds:" + shopsPlayOdds.getRealOdds());
				ps.setBigDecimal(1, shopsPlayOdds.getRealOdds());
				ps.setLong(2, shopsPlayOdds.getId());

			}

			@Override
			public int getBatchSize() {
				return shopsPlayOddsList.size();
			}

		});
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ShopsPlayOdds> getStopShopsPlayOddsByShopCodeAndPlayTypePerfix(String shopCode, String playTypePerfix) {
		final String hql = "FROM ShopsPlayOdds WHERE shopsCode =:shopsCode AND playTypeCode like :playTypeCode AND state=1";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("shopsCode", shopCode);
		paramMap.put("playTypeCode", playTypePerfix + "%");
		return this.find(hql, paramMap);
	}
}

class ShopOddsItemMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ShopsPlayOdds item = new ShopsPlayOdds();
		item.setPlayTypeCode(rs.getString("play_type_code"));
		item.setOddsTypeX(rs.getString("odds_type_x"));
		item.setRealOdds(rs.getBigDecimal("real_odds"));
		item.setRealOddsB(rs.getBigDecimal("oddsB"));
		item.setRealOddsC(rs.getBigDecimal("oddsC"));
		item.setState(rs.getString("state"));
		item.setOddsType(rs.getString("oddsType"));
		return item;
	}
}

class OddsLogItemMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		OddDetail item = new OddDetail();
		item.setPlayTypeCode(rs.getString("playTypeCode"));
		item.setOddsTypeX(rs.getString("oddsTypeX"));
		item.setOddsType(rs.getString("oddsType"));
		item.setRealOddsNew(rs.getBigDecimal("realOddsNew"));
		item.setRealUpdateDateNew(rs.getTimestamp("realUpdateDateNew"));
		return item;
	}
}

class CurrentShopOddsItemMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ShopsPlayOdds item = new ShopsPlayOdds();
		item.setId(rs.getLong("id"));
		item.setRealOdds(rs.getBigDecimal("real_odds"));
		item.setPlayTypeCode(rs.getString("play_type_code"));
		return item;
	}
}