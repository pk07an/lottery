package com.npc.lottery.user.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.user.dao.interf.IChiefStaffExtDao;
import com.npc.lottery.user.entity.ChiefStaffExt;


public class ChiefStaffExtDao extends HibernateDao<ChiefStaffExt, Long>  implements IChiefStaffExtDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void updateChiefPassword(ChiefStaffExt entity, String scheme) {
		if(StringUtils.isNotEmpty(scheme)){
			scheme=scheme+".";
		}
		String sql="update "+scheme+"TB_FRAME_MANAGER_STAFF set USER_PWD=?,PASSWORD_UPDATE_DATE=SYSDATE where id=?";
		Object[] obj=new Object[]{entity.getUserPwd(),entity.getManagerStaffID()};
		jdbcTemplate.update(sql, obj);
	}

	@Override
	public ChiefStaffExt queryChiefByShopsCode(String shopsCode, String scheme) {
		if(StringUtils.isNotEmpty(scheme)){
			scheme=scheme+".";
		}
		
		String sql="select MANAGER_STAFF_ID,SHOPS_CODE from "+scheme+"TB_CHIEF_STAFF_EXT where SHOPS_CODE=?";
		List<ChiefStaffExt> lists=jdbcTemplate.query(sql, new Object[]{shopsCode}, new ChiefStaffRowMapper());
		return lists.get(0);
	}

	
	class ChiefStaffRowMapper implements RowMapper{

		@Override
		public ChiefStaffExt mapRow(ResultSet rs, int rowNum) throws SQLException {
			ChiefStaffExt chiefStaff=new ChiefStaffExt();
			chiefStaff.setID(rs.getLong("MANAGER_STAFF_ID"));
			chiefStaff.setManagerStaffID(rs.getLong("MANAGER_STAFF_ID"));
			chiefStaff.setShopsCode(rs.getString("SHOPS_CODE"));
			return chiefStaff;
		}
		
	}
}
