package com.npc.lottery.user.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.replenish.vo.ReplenishVO;
import com.npc.lottery.user.dao.interf.IUserCommissionDao;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.vo.ShopCommissionVO;

public class UserCommissionDao extends HibernateDao<UserCommission, Long> implements IUserCommissionDao {

	private JdbcTemplate jdbcTemplate;

	@Override
	public void updateCommission(Long userID, String userType) {
		String hqSQL = "update tb_user_commission uc set (uc.commission_a,uc.commission_b,uc.commission_c,uc.item_quotas,uc.betting_quotas)= "
		        + " (select CASE WHEN ruc.commission_a < uc.commission_a THEN ruc.commission_a ELSE uc.commission_a end, "

		        + " CASE WHEN ruc.commission_b < uc.commission_b " + " THEN ruc.commission_b ELSE uc.commission_b end,"

		        + " CASE WHEN ruc.commission_c < uc.commission_c " + " THEN ruc.commission_c ELSE uc.commission_c end,"

		        + " CASE WHEN ruc.item_quotas < uc.item_quotas " + " THEN ruc.item_quotas ELSE uc.item_quotas end,"

		        + " CASE WHEN ruc.betting_quotas < uc.betting_quotas " + " THEN ruc.betting_quotas ELSE uc.betting_quotas end "

		        + " from tb_user_commission ruc  where ruc.user_id='" + userID + "' and ruc.user_type='" + userType + "' and uc.play_final_type=ruc.play_final_type) "
		        + " where exists(select distinct member_staff_id from tb_member_staff_ext b1 where b1.branch_staff='" + userID
		        + "' and b1.member_staff_id=uc.user_id and b1.member_staff_id is not null " + " union all"
		        + " select distinct b2.stockholder_staff from tb_member_staff_ext b2 where b2.branch_staff='" + userID + "' and b2.member_staff_id=uc.user_id and b2.stockholder_staff is not null "
		        + " union all select distinct b3.gen_agent_staff from tb_member_staff_ext b3 where b3.branch_staff='" + userID
		        + "' and b3.member_staff_id=uc.user_id and b3.gen_agent_staff is not null " + " union all select distinct b4.agent_staff from tb_member_staff_ext b4 where b4.branch_staff='" + userID
		        + "' and b4.member_staff_id=uc.user_id and b4.agent_staff is not null) ";

		jdbcTemplate.update(hqSQL);
	}

	@Override
	public List<ShopCommissionVO> queryShopCommission(Long userID, String typeCode) {
		String sql = "";
		Object[] parameters = null;
		sql = "select distinct(a.type_code)      as playFinalType," + "b.commission_a             as commission" + "from tb_play_type a," + "tb_user_commission b," + "tb_shops_play_odds c "
		        + "where     a.type_code = c.play_type_code " + "and b.play_final_type = a.commission_type" + "and b.user_id=? " + "and b.user_type!='9' " + "and type_code like ?"
		        + "order by type_code";
		parameters = new Object[] { userID, typeCode };
		@SuppressWarnings("unchecked")
		List<ShopCommissionVO> retList = jdbcTemplate.query(sql, parameters, new TotalMapper());
		return retList;
	}

	public void deleteUserCommission(Long userId) {
		String updSql = "delete from tb_user_commission where user_id=?";
		Object[] args = new Object[] { userId };
		jdbcTemplate.update(updSql, args);
	}

	@Override
	public List<UserCommission> getUserCommissionMapByPlayFinalType(String playFinalType, Long userId) {
		final String sql = "select * from tb_user_commission where user_id=? and play_final_type like ?";
		Object[] parameter = new Object[] { userId,playFinalType };
		List<UserCommission> userCommissionlist=null;
		try {
			userCommissionlist = jdbcTemplate.query(sql, parameter, new UserCommissionMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userCommissionlist;
	}

	class TotalMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ReplenishVO item = new ReplenishVO();
			item.setPlayFinalType(rs.getString("playFinalType"));
			item.setOddsMoney(rs.getBigDecimal("oddsMoney"));
			return item;
		}
	}


	@Override
	public UserCommission queryUserPlayTypeCommission(Object[] values,String scheme){
	    if(StringUtils.isNotBlank(scheme)){
	    	scheme=scheme+".";
	    }
	    String sql="select uc.ID,USER_ID,USER_TYPE,uc.PLAY_TYPE,uc.PLAY_FINAL_TYPE,COMMISSION_A,COMMISSION_B,COMMISSION_C, "+
	    "BETTING_QUOTAS,ITEM_QUOTAS,CREATE_USER,CREATE_TIME,MODIFY_TIME,CHIEF_ID from "+scheme+"tb_user_commission uc "+
	    "inner join TB_PLAY_TYPE pt on pt.COMMISSION_TYPE=uc.PLAY_FINAL_TYPE " +
	    "where USER_ID=? and USER_TYPE=? and  pt.TYPE_CODE= ? ";
	    UserCommission userCommission=jdbcTemplate.queryForObject(sql, values, new UserCommissionMapper());
	    return userCommission;
	}
	
	 class UserCommissionMapper implements RowMapper{
			@Override
			public UserCommission mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserCommission userCommission=new UserCommission();
				userCommission.setID(Long.valueOf(rs.getInt("ID")));
				userCommission.setUserId(Long.valueOf(rs.getInt("USER_ID")));
				userCommission.setUserType(rs.getString("USER_TYPE"));
				userCommission.setPlayType(rs.getString("PLAY_TYPE"));
				userCommission.setPlayFinalType(rs.getString("PLAY_FINAL_TYPE"));
				userCommission.setCommissionA(rs.getDouble("COMMISSION_A"));
				userCommission.setCommissionB(rs.getDouble("COMMISSION_B"));
				userCommission.setCommissionC(rs.getDouble("COMMISSION_C"));
				userCommission.setBettingQuotas(rs.getInt("BETTING_QUOTAS"));
				userCommission.setItemQuotas(rs.getInt("ITEM_QUOTAS"));
				userCommission.setCreateUser(Long.valueOf(rs.getInt("CREATE_USER")));
				userCommission.setCreateTime(rs.getDate("CREATE_TIME"));
				userCommission.setModifyTime(rs.getDate("MODIFY_TIME"));
				userCommission.setChiefId(Long.valueOf(rs.getInt("CHIEF_ID")));
				return userCommission;
			}
	    	
	    }
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
