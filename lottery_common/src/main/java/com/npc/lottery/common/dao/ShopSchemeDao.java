package com.npc.lottery.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class ShopSchemeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 获取所有shopcode对应的数据库scheme
	 * 
	 * @return
	 */
	public Map<String, String> getShopSchemeMap() {
		Map<String, String> map = new HashMap<String, String>();
		final String sql = "SELECT SHOP_CODE,SCHEME FROM TB_SHOP_SCHEME";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> result : resultList) {
			String shopCode = (String) result.get("SHOP_CODE");
			String scheme = (String) result.get("SCHEME");
			map.put(shopCode, scheme);
		}
		return map;
	}

	/**
	 * 通过shop code获取对应的scheme
	 * 
	 * @param shopCode
	 * @return
	 */
	public String getSchemeByShopCode(String shopCode) {
		String scheme = "";
		final String sql = "SELECT SCHEME FROM TB_SHOP_SCHEME WHERE SHOP_CODE = ?";
		List<String> resultList = jdbcTemplate.query(sql, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}

		}, shopCode);
		if (!CollectionUtils.isEmpty(resultList)) {
			scheme = resultList.get(0);
		}
		return scheme;
	}

}
