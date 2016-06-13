package com.npc.lottery.user.dao.hibernate;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.user.dao.interf.IMemberStaffExtDao;
import com.npc.lottery.user.entity.MemberStaffExt;

public class MemberStaffExtDao extends HibernateDao<MemberStaffExt, Long> implements IMemberStaffExtDao {
	private JdbcTemplate jdbcTemplate;
	private Logger logger = Logger.getLogger(MemberStaffExtDao.class);
	@Override
	public String getMemberFlagById(long id) {
		final String sql = "SELECT FLAG FROM TB_FRAME_MEMBER_STAFF WHERE ID=?";
		return (String) jdbcTemplate.queryForObject(sql, new Object[] { id }, String.class);

	}
	
	@Override
	public void updateMemberAvailableCreditLineByAdd(double availableCreditLine, long id, String scheme) {
		if(StringUtils.isNotBlank(scheme)){
			scheme=scheme+".";
		}
		Object[] parmeters=new Object[]{availableCreditLine,id};
		String sql="update "+scheme+"TB_MEMBER_STAFF_EXT set AVAILABLE_CREDIT_LINE=AVAILABLE_CREDIT_LINE+? where MEMBER_STAFF_ID=?";
		jdbcTemplate.update(sql,parmeters);
	}
	
	@Override
	public void updateMemberAvailableCreditLineById(double availableCreditLine, long id) {
		final String sql = "update TB_MEMBER_STAFF_EXT set AVAILABLE_CREDIT_LINE=? where MEMBER_STAFF_ID=?";
		jdbcTemplate.update(sql, new Object[] { availableCreditLine, id });
	}

	@Override
	public int updateMemberAvailableCreditToTotal(String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		logger.info("=======updateMemberAvailableCreditToTotal开始执行SCHEME为:" + scheme + "==============");
		final String sql = "update " + scheme + "TB_MEMBER_STAFF_EXT set AVAILABLE_CREDIT_LINE=TOTAL_CREDIT_LINE";
		return jdbcTemplate.update(sql);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
}
