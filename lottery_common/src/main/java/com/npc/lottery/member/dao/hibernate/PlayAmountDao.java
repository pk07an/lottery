package com.npc.lottery.member.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.tree.TreePath;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.member.dao.interf.IPlayAmountDao;
import com.npc.lottery.member.entity.PlayAmount;

/**
 * @author none
 *
 */
public class PlayAmountDao extends HibernateDao<PlayAmount, Long> implements IPlayAmountDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public PlayAmount queryUniqueByTypeCode(String typeCode,String shopCode,String scheme){
		if(StringUtils.isNotBlank(scheme)){
			scheme=scheme+".";
		}
		String sql="select ID,TYPE_CODE,PLAY_TYPE,PERIODS_NUM,COMMISSION_TYPE,SHOPS_CODE,MONEY_AMOUNT, "+
					" UPDATE_TIME from "+scheme+"TB_PLAY_AMOUNT where TYPE_CODE=? and SHOPS_CODE=?";
		Object[] objs = new Object[]{
			typeCode,
			shopCode
		};
		PlayAmount playAmount=null;
		try {
			playAmount=jdbcTemplate.queryForObject(sql, objs,new PlayAmountRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return playAmount;
	}
	
	
	class PlayAmountRowMapper implements RowMapper{

		@Override
		public PlayAmount mapRow(ResultSet rs, int rowNum) throws SQLException {
			PlayAmount playAmount=new PlayAmount();
			playAmount.setId(Long.valueOf(rs.getInt("ID")));
			playAmount.setTypeCode(rs.getString("TYPE_CODE"));
			playAmount.setPlayType(rs.getString("PLAY_TYPE"));
			playAmount.setPeriodsNum(rs.getString("PERIODS_NUM"));
			playAmount.setCommissionType(rs.getString("COMMISSION_TYPE"));
			playAmount.setShopCode(rs.getString("SHOPS_CODE"));
			playAmount.setMoneyAmount(rs.getDouble("MONEY_AMOUNT"));
			playAmount.setUpdateTime(rs.getDate("UPDATE_TIME"));
			return playAmount;
		}
	}
}
