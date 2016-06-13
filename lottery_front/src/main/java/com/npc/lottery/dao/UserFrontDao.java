package com.npc.lottery.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.sysmge.entity.LoginLogInfo;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.MemberStaffExt;

@Repository
public class UserFrontDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static Logger log = Logger.getLogger(UserFrontDao.class);

	public MemberStaff getMemberUserStaffByName(String account, String scheme) {
		final String sql = "SELECT ID,ACCOUNT,FLAG,USER_TYPE,USER_EXT_INFO_ID,USER_PWD,CHS_NAME,ENG_NAME,CREATE_DATE,UPDATE_DATE,LOGIN_DATE,LOGIN_IP,COMMENTS,"
		        + "PARENT_STAFF_QRY,PARENT_STAFF_TYPE_QRY,PASSWORD_UPDATE_DATE,PASSWORD_RESET_FLAG FROM " + scheme + ".TB_FRAME_MEMBER_STAFF WHERE ACCOUNT=?";
		MemberStaff memberStaff = null;
		try {
			memberStaff = jdbcTemplate.queryForObject(sql, new MemberStaffMapper(), account);
		} catch (Exception ex) {
			log.error(ex);
		}
		return memberStaff;
	}

	public MemberStaff getMemberUserStaffById(long userId, String scheme) {
		final String sql = "SELECT ID,ACCOUNT,FLAG,USER_TYPE,USER_EXT_INFO_ID,USER_PWD,CHS_NAME,ENG_NAME,CREATE_DATE,UPDATE_DATE,LOGIN_DATE,LOGIN_IP,COMMENTS,"
		        + "PARENT_STAFF_QRY,PARENT_STAFF_TYPE_QRY,PASSWORD_UPDATE_DATE,PASSWORD_RESET_FLAG FROM " + scheme + ".TB_FRAME_MEMBER_STAFF WHERE ID=?";
		MemberStaff memberStaff = null;
		try {
			memberStaff = jdbcTemplate.queryForObject(sql, new MemberStaffMapper(), userId);
		} catch (Exception ex) {
			log.error(ex);
		}
		return memberStaff;
	}

	public MemberStaffExt getMemberStaffExtById(long userId, String scheme) {
		final String sql = "SELECT MEMBER_STAFF_ID,PARENT_STAFF,PARENT_USER_TYPE,PLATE,TOTAL_CREDIT_LINE,AVAILABLE_CREDIT_LINE,RATE,BACK_WATER,CHIEF_STAFF,"
		        + "BRANCH_STAFF,STOCKHOLDER_STAFF,GEN_AGENT_STAFF,AGENT_STAFF FROM " + scheme + ".TB_MEMBER_STAFF_EXT WHERE MEMBER_STAFF_ID=?";
		MemberStaffExt memberStaffExt = null;
		try {
			memberStaffExt = jdbcTemplate.queryForObject(sql, new MemberStaffExtMapper(), userId);
		} catch (Exception ex) {
			log.error(ex);
		}
		MemberStaff memberStaff = this.getMemberUserStaffById(userId, scheme);
		if (null != memberStaff) {
			try {
				BeanUtils.copyProperties(memberStaffExt, memberStaff);
			} catch (Exception ex) {
				log.error("复制bean失败", ex);
			}
		}
		return memberStaffExt;
	}

	/**
	 * 根据总监id获取总监扩展表数据
	 * 
	 * @param chiefId
	 * @param scheme
	 * @return
	 */
	public ChiefStaffExt getChiefStaffExtById(long chiefId, String scheme) {
		final String sql = "SELECT MANAGER_STAFF_ID,SHOPS_CODE FROM " + scheme + ".TB_CHIEF_STAFF_EXT WHERE MANAGER_STAFF_ID=?";
		ChiefStaffExt chiefStaffExt = null;
		try {
			chiefStaffExt = jdbcTemplate.queryForObject(sql, new RowMapper<ChiefStaffExt>() {

				@Override
				public ChiefStaffExt mapRow(ResultSet rs, int rowNum) throws SQLException {
					ChiefStaffExt chiefStaffExt = new ChiefStaffExt();
					chiefStaffExt.setManagerStaffID(rs.getLong("MANAGER_STAFF_ID"));
					chiefStaffExt.setShopsCode(rs.getString("SHOPS_CODE"));
					chiefStaffExt.setID(rs.getLong("MANAGER_STAFF_ID"));
					return chiefStaffExt;
				}

			}, chiefId);
		} catch (Exception ex) {
			log.error(ex);
		}
		return chiefStaffExt;
	}

	/**
	 * 记录登录日志
	 * 
	 * @param logLogInfo
	 * @param scheme
	 */
	public void saveLoginLogInfo(final LoginLogInfo logLogInfo, String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		final String sql = " INSERT INTO "
		        + scheme
		        + "TB_LOGIN_LOG_INFO(ID,USER_ID,ACCOUNT,USER_TYPE,SHOPS_CODE,LOGIN_DATE,LOGIN_IP,SESSION_ID,LOGOUT_DATE,"
		        + "LOGIN_STATE,SUB_LOGIN_STATE,INFO,CHIEF_STAFF_ID,BRANCH_STAFF_ID,STOCKHOLDER_STAFF_ID,GEN_AGENT_STAFF_ID,AGENT_STAFF_ID,"
		        + "CHIEF_STAFF_ACC,AGENT_STAFF_ACC,GEN_AGENT_STAFF_ACC,STOCKHOLDER_STAFF_ACC,BRANCH_STAFF_ACC,REMARK) VALUES (SEQ_TB_LOGIN_LOG_INFO.NEXTVAL,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, logLogInfo.getUserId());
				ps.setString(2, logLogInfo.getAccount());
				ps.setString(3, logLogInfo.getUserType());
				ps.setString(4, logLogInfo.getShopsCode());
				ps.setString(5, logLogInfo.getLoginIp());
				ps.setDate(6, null);
				ps.setString(7, logLogInfo.getLoginState());
				ps.setString(8, logLogInfo.getSubLoginState());
				ps.setString(9, logLogInfo.getInfo());
				ps.setLong(10, logLogInfo.getChiefStaffId() == null ? 0 : logLogInfo.getChiefStaffId());
				ps.setLong(11, logLogInfo.getBranchStaffId() == null ? 0 : logLogInfo.getBranchStaffId());
				ps.setLong(12, logLogInfo.getBranchStaffId() == null ? 0 : logLogInfo.getBranchStaffId());
				ps.setLong(13, logLogInfo.getStockholderStaffId() == null ? 0 : logLogInfo.getStockholderStaffId());
				ps.setLong(14, logLogInfo.getGenAgentStaffId() == null ? 0 : logLogInfo.getGenAgentStaffId());
				ps.setLong(15, logLogInfo.getAgentStaffId() == null ? 0 : logLogInfo.getAgentStaffId());
				ps.setString(16, logLogInfo.getChiefStaffAcc());
				ps.setString(17, logLogInfo.getAgentStaffAcc());
				ps.setString(18, logLogInfo.getGenAgentStaffAcc());
				ps.setString(19, logLogInfo.getStockholderStaffAcc());
				ps.setString(20, logLogInfo.getBranchStaffAcc());
				ps.setString(21, logLogInfo.getRemark());
			}
		});
	}

	public String getMemberFlagById(long userId, String scheme) {
		final String sql = "SELECT FLAG FROM " + scheme + ".TB_FRAME_MEMBER_STAFF WHERE ID=?";
		String flag = "";
		try {
			flag = jdbcTemplate.queryForObject(sql, String.class, userId);
		} catch (Exception ex) {
			log.error(ex);
		}
		return flag;
	}

	public String getLeftOwnerByBranchId(long branchId, String scheme) {
		final String sql = "SELECT  LEFTOWNER FROM " + scheme + ".TB_BRANCH_STAFF_EXT WHERE MANAGER_STAFF_ID = ?";
		String leftOwner = "";
		try {
			leftOwner = jdbcTemplate.queryForObject(sql, String.class, branchId);
		} catch (Exception ex) {
			log.error(ex);
		}
		return leftOwner;
	}

	public Map<String, Integer> getUserLineRateMapByUserId(long userId, String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		final String sql = "SELECT  MEMBER.RATE AS RATE,AGENT.GEN_AGENT_RATE AS GEN_AGENT_RATE, GEN.SHAREHOLDER_RATE AS STOCK_RATE,STOCK.BRANCH_RATE AS BRANCH_RATE, BRANCH.CHIEF_RATE AS CHIEF_RATE"
		        + "  FROM " + scheme + "TB_MEMBER_STAFF_EXT MEMBER " + "LEFT JOIN " + scheme + "TB_AGENT_STAFF_EXT AGENT ON MEMBER.AGENT_STAFF=AGENT.MANAGER_STAFF_ID" + "  LEFT JOIN " + scheme
		        + "TB_GEN_AGENT_STAFF_EXT GEN  ON MEMBER.GEN_AGENT_STAFF=GEN.MANAGER_STAFF_ID  LEFT JOIN " + scheme + "TB_BRANCH_STAFF_EXT BRANCH ON MEMBER.BRANCH_STAFF=BRANCH.MANAGER_STAFF_ID"
		        + "  LEFT JOIN " + scheme + "TB_STOCKHOLDER_STAFF_EXT STOCK ON MEMBER.STOCKHOLDER_STAFF=STOCK.MANAGER_STAFF_ID WHERE MEMBER.MEMBER_STAFF_ID=?";
		Map<String, Integer> rateMap = new HashMap<String, Integer>();
		Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql, userId);
		for (Entry<String, Object> entry : resultMap.entrySet()) {
			int rate = entry.getValue() != null ? ((BigDecimal) entry.getValue()).intValue() : 0;
			rateMap.put(entry.getKey(), rate);
		}
		return rateMap;
	}

	public void updateMemberStaffPasswordById(long userId, String newPassword, String scheme) {
		final String sql = "UPDATE " + scheme + ".TB_FRAME_MEMBER_STAFF SET USER_PWD=?,PASSWORD_UPDATE_DATE=SYSDATE,PASSWORD_RESET_FLAG='N' WHERE ID= ?";
		jdbcTemplate.update(sql, newPassword, userId);
	}

	public void updateMemberAvailableCreditLineById(double availableCreditLine, long id, String scheme) {
		final String sql = "update " + scheme + ".TB_MEMBER_STAFF_EXT set AVAILABLE_CREDIT_LINE=? where MEMBER_STAFF_ID=?";
		jdbcTemplate.update(sql, new Object[] { availableCreditLine, id });
	}

	public ShopsInfo getShopInfoByChiefStaffExt(ChiefStaffExt chiefStaffExt) {
		ShopsInfo shopsInfo = null;
		final String sql = "SELECT  ID,SHOPS_CODE,SHOPS_NAME,STATE FROM TB_SHOPS_INFO WHERE SHOPS_CODE=?";
		List<ShopsInfo> resultList = jdbcTemplate.query(sql, new ShopsInfoMapper(), chiefStaffExt.getShopsCode());
		if (!CollectionUtils.isEmpty(resultList)) {
			shopsInfo = resultList.get(0);
			shopsInfo.setChiefStaffExt(chiefStaffExt);
		}
		return shopsInfo;
	}

	public ShopsInfo getShopInfoByShopCode(String shopCode) {
		ShopsInfo shopsInfo = null;
		final String sql = "SELECT  ID,SHOPS_CODE,SHOPS_NAME,STATE FROM TB_SHOPS_INFO WHERE SHOPS_CODE=?";
		List<ShopsInfo> resultList = jdbcTemplate.query(sql, new ShopsInfoMapper(), shopCode);
		if (!CollectionUtils.isEmpty(resultList)) {
			shopsInfo = resultList.get(0);
		}
		return shopsInfo;
	}

	class ShopsInfoMapper implements RowMapper<ShopsInfo> {

		@Override
		public ShopsInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ShopsInfo shopsInfo = new ShopsInfo();
			shopsInfo.setID(rs.getLong("ID"));
			shopsInfo.setShopsCode(rs.getString("SHOPS_CODE"));
			shopsInfo.setShopsName(rs.getString("SHOPS_NAME"));
			shopsInfo.setState(rs.getString("STATE"));
			return shopsInfo;
		}
	}

	class MemberStaffMapper implements RowMapper<MemberStaff> {
		@Override
		public MemberStaff mapRow(ResultSet rs, int rowNum) throws SQLException {
			MemberStaff memberStaff = new MemberStaff();
			memberStaff.setID(rs.getLong("ID"));
			memberStaff.setAccount(rs.getString("ACCOUNT"));
			memberStaff.setFlag(rs.getString("FLAG"));
			memberStaff.setUserType(rs.getString("USER_TYPE"));
			memberStaff.setUserExtInfoId(rs.getLong("USER_EXT_INFO_ID"));
			memberStaff.setUserPwd(rs.getString("USER_PWD"));
			memberStaff.setChsName(rs.getString("CHS_NAME"));
			memberStaff.setEngName(rs.getString("ENG_NAME"));
			memberStaff.setCreateDate(rs.getDate("CREATE_DATE"));
			memberStaff.setUpdateDate(rs.getDate("UPDATE_DATE"));
			memberStaff.setLoginDate(rs.getDate("LOGIN_DATE"));
			memberStaff.setLoginIp(rs.getString("LOGIN_IP"));
			memberStaff.setComments(rs.getString("COMMENTS"));
			memberStaff.setParentStaffQry(rs.getLong("PARENT_STAFF_QRY"));
			memberStaff.setParentStaffTypeQry(rs.getString("PARENT_STAFF_TYPE_QRY"));
			memberStaff.setPasswordUpdateDate(rs.getDate("PASSWORD_UPDATE_DATE"));
			memberStaff.setPasswordResetFlag(rs.getString("PASSWORD_RESET_FLAG"));
			return memberStaff;
		}

	}

	class MemberStaffExtMapper implements RowMapper<MemberStaffExt> {

		@Override
		public MemberStaffExt mapRow(ResultSet rs, int rowNum) throws SQLException {
			MemberStaffExt memberStaffExt = new MemberStaffExt();
			memberStaffExt.setMemberStaffID(rs.getLong("MEMBER_STAFF_ID"));
			memberStaffExt.setParentStaff(rs.getLong("PARENT_STAFF"));
			memberStaffExt.setParentUserType(rs.getString("PARENT_USER_TYPE"));
			memberStaffExt.setPlate(rs.getString("PLATE"));
			memberStaffExt.setTotalCreditLine(rs.getInt("TOTAL_CREDIT_LINE"));
			memberStaffExt.setAvailableCreditLine(rs.getInt("AVAILABLE_CREDIT_LINE"));
			memberStaffExt.setRate(rs.getInt("RATE"));
			memberStaffExt.setBackWater(rs.getBigDecimal("BACK_WATER"));
			memberStaffExt.setChiefStaff(rs.getLong("CHIEF_STAFF"));
			memberStaffExt.setBranchStaff(rs.getLong("BRANCH_STAFF"));
			memberStaffExt.setStockholderStaff(rs.getLong("STOCKHOLDER_STAFF"));
			memberStaffExt.setGenAgentStaff(rs.getLong("GEN_AGENT_STAFF"));
			memberStaffExt.setAgentStaff(rs.getLong("AGENT_STAFF"));
			memberStaffExt.setID(rs.getLong("MEMBER_STAFF_ID"));

			return memberStaffExt;
		}

	}
}
