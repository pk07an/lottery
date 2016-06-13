package com.npc.lottery.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.stereotype.Repository;

import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.util.PlayTypeUtils;
import com.npc.lottery.util.SpringUtil;

/**
 * 投注下单数据库DAO层
 * 
 * @author 888
 *
 */
@Repository
public class PlayBetFrontDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int[] batchInsert(final List<BaseBet> tempbetList, final String TBName, boolean insertAttr, String scheme) {
		if(StringUtils.isNotEmpty(scheme)){
			scheme = scheme+".";
		}

		Collections.sort(tempbetList, new BetCompare());
		OracleSequenceMaxValueIncrementer orderNoGener = (OracleSequenceMaxValueIncrementer) SpringUtil.getBean("betOrderNoGenerator");
		for (int i = 0; i < tempbetList.size(); i++) {

			BaseBet bet = tempbetList.get(i);
			if (bet.getOrderNo() == null) {
				String orderNo = orderNoGener.nextStringValue();
				bet.setOrderNo(orderNo);
			}
		}

		final String seqName = "SEQ_" + TBName;

		String sql = "insert into " + scheme + TBName + "(id,type_code,order_no,money,betting_user_id,periods_num,plate," + "  ChiefStaff,BranchStaff,StockholderStaff,GenAgenStaff,AgentStaff,"
		        + "  commission_branch,commission_stockholder,commission_gen_agent,commission_agent,commission_member,"
		        + "  rate_chief,rate_branch,rate_stockholder,rate_gen_agent,rate_agent,commission_type," + "  betting_date,odds,update_date ";

		if (insertAttr) {
			sql = sql + ",attribute";
		}
		if (TBName.indexOf("GDKLSF") != -1 || TBName.indexOf("NC") != -1) {
			sql = sql + ",compound_num";
		}
		if (TBName.indexOf("TB_GDKLSF_STRAIGHTTHROUGH") != -1 || TBName.indexOf("NC") != -1) {
			sql = sql + ",split_attribute";
		}
		sql = sql + ") " + "values(?, ?,?, ?, ?,?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, sysdate,?,sysdate";

		if (insertAttr)
			sql = sql + ",?";
		if (TBName.indexOf("GDKLSF") != -1 || TBName.indexOf("NC") != -1) {
			sql = sql + ",?";
		}
		if (TBName.indexOf("TB_GDKLSF_STRAIGHTTHROUGH") != -1 || TBName.indexOf("NC") != -1) {
			sql = sql + ",?";
		}

		sql = sql + ")";

		int[] result = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				BaseBet betOrder = tempbetList.get(i);
				String playType = betOrder.getPlayType();
				int money = betOrder.getMoney();
				Long userName = betOrder.getBettingUserId();
				String periodsNum = betOrder.getPeriodsNum();
				String plate = betOrder.getPlate();
				BigDecimal odds = betOrder.getOdds();
				String attribute = betOrder.getAttribute();
				String ordeNo = betOrder.getOrderNo();
				Integer commissionMember = betOrder.getCompoundNum();

				int id = getSeqId(seqName);
				betOrder.setId(id);
				ps.setLong(1, betOrder.getId());

				ps.setString(2, playType);
				ps.setString(3, ordeNo);
				ps.setInt(4, money);
				ps.setLong(5, userName);
				ps.setString(6, periodsNum);
				ps.setString(7, plate);

				ps.setLong(8, betOrder.getChiefStaff());
				ps.setLong(9, betOrder.getBranchStaff());
				ps.setLong(10, betOrder.getStockholderStaff());
				ps.setLong(11, betOrder.getGenAgenStaff());
				ps.setLong(12, betOrder.getAgentStaff());

				ps.setDouble(13, betOrder.getBranchCommission() == null ? 0 : betOrder.getBranchCommission());
				ps.setDouble(14, betOrder.getStockHolderCommission() == null ? 0 : betOrder.getStockHolderCommission());
				ps.setDouble(15, betOrder.getGenAgenCommission() == null ? 0 : betOrder.getGenAgenCommission());
				ps.setDouble(16, betOrder.getAgentStaffCommission() == null ? 0 : betOrder.getAgentStaffCommission());
				ps.setDouble(17, betOrder.getMemberCommission() == null ? 0 : betOrder.getMemberCommission());

				ps.setLong(18, betOrder.getChiefRate());
				ps.setLong(19, betOrder.getBranchRate());
				ps.setLong(20, betOrder.getStockHolderRate());
				ps.setLong(21, betOrder.getGenAgenRate());
				ps.setLong(22, betOrder.getAgentStaffRate());
				ps.setString(23, betOrder.getCommissionType());

				int k = 23;

				ps.setBigDecimal(24, odds);
				k = k + 2;

				if (attribute != null) {
					ps.setString(k, attribute);
					k++;
				}
				if (TBName.indexOf("GDKLSF") != -1 || TBName.indexOf("NC") != -1) {
					ps.setInt(k, commissionMember);
					k++;
				}
				if (TBName.indexOf("TB_GDKLSF_STRAIGHTTHROUGH") != -1 || TBName.indexOf("NC") != -1) {
					ps.setString(k, betOrder.getSplitAttribute());
					k++;
				}

			}

			public int getBatchSize() {
				return tempbetList.size();
			}
		});
		return result;
	}

	private int getSeqId(String seqName) {
		String sql = "select " + seqName + ".nextval from dual";
		return jdbcTemplate.queryForInt(sql);
	}

	class BetCompare implements Comparator<BaseBet> {

		@Override
		public int compare(BaseBet o1, BaseBet o2) {
			try {
				String typeCode1 = o1.getPlayType();
				String typeCode2 = o2.getPlayType();
				PlayType playType1 = PlayTypeUtils.getPlayType(typeCode1);
				PlayType playType2 = PlayTypeUtils.getPlayType(typeCode2);
				return playType2.getDisplayOrder() - playType1.getDisplayOrder();
			} catch (Exception e) {
				// e.printStackTrace();
			}
			return 0;
		}
	}
}
