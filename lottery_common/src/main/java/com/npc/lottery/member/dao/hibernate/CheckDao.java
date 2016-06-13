/**
 * 
 */
package com.npc.lottery.member.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.common.Constant;
import com.npc.lottery.member.dao.interf.ICheckDao;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.BetCheckVo;
import com.npc.lottery.replenish.entity.Replenish;

/**
 * @author peteran
 * 
 */
public class CheckDao implements ICheckDao {
	private JdbcTemplate jdbcTemplate;
	
	private static final Logger log =Logger.getLogger(CheckDao.class);

	@Override
	public void miragationGDDataToCheck(String periodsNum,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======miragationGDDataToCheck开始执行SCHEME为:"+scheme+"==============");
		for (int i = 0; i < Constant.GDKLSF_TABLE_LIST.length; i++) {

			String tableName = Constant.GDKLSF_TABLE_LIST[i];
			String attribute = "null";
			String splitAttr = "null";
			if (tableName.equals(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME)) {
				attribute = "attribute";
				splitAttr = "split_attribute";
			}

			String insertSql = "insert into "+scheme+"TB_GDKLSF_CHECK " + "(id,create_date,origin_tb_name,origin_id,order_no,type_code,money,plate,"
					+ "betting_user_id,chiefstaff,branchstaff,stockholderstaff,genagenstaff,agentstaff,"
					+ "commission_branch,commission_gen_agent,commission_stockholder,commission_agent,commission_member,"
					+ "rate_chief,rate_branch,rate_gen_agent,rate_stockholder,rate_agent,"
					+ "attribute,split_attribute,compound_num,periods_num,betting_date,odds,update_date,commission_type)"
					+ "(select SEQ_TB_GDKLSF_CHECK.nextval,SYSDATE,'" + tableName + "',c.id,c.order_no,c.type_code,c.money,plate,"
					+ "c.betting_user_id,c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.agentstaff,"
					+ "c.commission_branch,c.commission_gen_agent,c.commission_stockholder,c.commission_agent,c.commission_member,"
					+ "c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent," + attribute + " as attribute ," + splitAttr
					+ ",c.compound_num,c.periods_num,c.betting_date,c.odds,sysdate,commission_type from " +scheme+ tableName
					+ " c where c.periods_num=?)";
			Object[] parameter = new Object[] { periodsNum };
			jdbcTemplate.update(insertSql, parameter);

		}

	}

	@Override
	public void miragationCQDataToCheckTable(String periodsNum,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======miragationCQDataToCheckTable开始执行SCHEME为:"+scheme+"==============");
		for (int i = 0; i < Constant.CQSSC_TABLE_LIST.length; i++) {
			String tableName = Constant.CQSSC_TABLE_LIST[i];

			String sql = "insert into "+scheme+"TB_CQSSC_CHECK (id,create_date,origin_tb_name,origin_id,order_no,type_code,money,plate,"
					+ "betting_user_id,chiefstaff,branchstaff,stockholderstaff,genagenstaff,agentstaff,"
					+ "commission_branch,commission_gen_agent,commission_stockholder,commission_agent,commission_member,"
					+ "rate_chief,rate_branch,rate_gen_agent,rate_stockholder,rate_agent,"
					+ "periods_num,betting_date,odds,update_date,commission_type)"
					+ "(select SEQ_TB_CQSSC_CHECK.nextval,SYSDATE,'" + tableName + "',c.id,c.order_no,c.type_code,c.money,plate,"
					+ "c.betting_user_id,c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.agentstaff,"
					+ "c.commission_branch,c.commission_gen_agent,c.commission_stockholder,c.commission_agent,c.commission_member,"
					+ "c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent," + "c.periods_num,c.betting_date,"
					+ "c.odds,sysdate,commission_type from " + scheme +tableName + " c where c.periods_num=? )";
			Object[] parameter = new Object[] { periodsNum };
			jdbcTemplate.update(sql, parameter);
		}
	}

	@Override
	public void miragationBJDataToCheck(String periodsNum,String scheme) {

		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======miragationBJDataToCheck开始执行SCHEME为:"+scheme+"==============");
		for (int i = 0; i < Constant.BJSC_TABLE_LIST.length; i++) {
			String tableName = Constant.BJSC_TABLE_LIST[i];

			String sql = "insert into "+scheme+"TB_BJSC_CHECK " + "(id,create_date,origin_tb_name,origin_id,order_no,type_code,money,plate,"
					+ "betting_user_id,chiefstaff,branchstaff,stockholderstaff,genagenstaff,agentstaff,"
					+ "commission_branch,commission_gen_agent,commission_stockholder,commission_agent,commission_member,"
					+ "rate_chief,rate_branch,rate_gen_agent,rate_stockholder,rate_agent,"
					+ "periods_num,betting_date,odds,update_date,commission_type)"
					+ "(select SEQ_TB_BJSC_CHECK.nextval,SYSDATE,'" + tableName + "',c.id,c.order_no,c.type_code,c.money,plate,"
					+ "c.betting_user_id,c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.agentstaff,"
					+ "c.commission_branch,c.commission_gen_agent,c.commission_stockholder,c.commission_agent,c.commission_member,"
					+ "c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent,"
					+ "c.periods_num,c.betting_date,c.odds,sysdate,commission_type from " +scheme+ tableName
					+ " c where c.periods_num=? )";
			Object[] parameter = new Object[] { periodsNum };
			jdbcTemplate.update(sql, parameter);
		}

	}

	@Override
	public void miragationNCDataToCheck(String periodsNum,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======miragationNCDataToCheck开始执行SCHEME为:"+scheme+"==============");
		for (int i = 0; i < Constant.NC_TABLE_LIST.length; i++) {

			String tableName = Constant.NC_TABLE_LIST[i];
			String attribute = "attribute";
			String splitAttr = "split_attribute";

			String insertSql = "insert into "+scheme+"TB_NC_CHECK " + "(id,create_date,origin_tb_name,origin_id,order_no,type_code,money,plate,"
					+ "betting_user_id,chiefstaff,branchstaff,stockholderstaff,genagenstaff,agentstaff,"
					+ "commission_branch,commission_gen_agent,commission_stockholder,commission_agent,commission_member,"
					+ "rate_chief,rate_branch,rate_gen_agent,rate_stockholder,rate_agent,"
					+ "attribute,split_attribute,compound_num,periods_num,betting_date,odds,update_date,commission_type)"
					+ "(select seq_tb_nc_his.nextval,SYSDATE,'" + tableName + "',c.id,c.order_no,c.type_code,c.money,plate,"
					+ "c.betting_user_id,c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.agentstaff,"
					+ "c.commission_branch,c.commission_gen_agent,c.commission_stockholder,c.commission_agent,c.commission_member,"
					+ "c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent," + attribute + " as attribute ," + splitAttr
					+ ",c.compound_num,c.periods_num,c.betting_date,c.odds,sysdate,commission_type from " +scheme+ tableName
					+ " c where c.periods_num=?)";
			Object[] parameter = new Object[] { periodsNum };
			jdbcTemplate.update(insertSql, parameter);
		}
	}

	@Override
	public void miragationJSDataToCheck(String periodsNum,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======miragationJSDataToCheck开始执行SCHEME为:"+scheme+"==============");
		for (int i = 0; i < Constant.K3_TABLE_LIST.length; i++) {
			String tableName = Constant.K3_TABLE_LIST[i];

			String sql = "insert into "+scheme+"TB_JSSB_CHECK " + "(id,create_date,origin_tb_name,origin_id,order_no,type_code,money,plate,"
					+ "betting_user_id,chiefstaff,branchstaff,stockholderstaff,genagenstaff,agentstaff,"
					+ "commission_branch,commission_gen_agent,commission_stockholder,commission_agent,commission_member,"
					+ "rate_chief,rate_branch,rate_gen_agent,rate_stockholder,rate_agent,"
					+ "periods_num,betting_date,odds,update_date,commission_type,plus_odds)"
					+ "(select SEQ_TB_JSSB_CHECK.nextval,SYSDATE,'" + tableName + "',c.id,c.order_no,c.type_code,c.money,plate,"
					+ "c.betting_user_id,c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.agentstaff,"
					+ "c.commission_branch,c.commission_gen_agent,c.commission_stockholder,c.commission_agent,c.commission_member,"
					+ "c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent,"
					+ "c.periods_num,c.betting_date,c.odds,sysdate,commission_type,plus_odds from " + scheme+tableName
					+ " c where c.periods_num=? )";
			Object[] parameter = new Object[] { periodsNum };
			jdbcTemplate.update(sql, parameter);
		}
	}

	@Override
	public int[] batchInsertToCheck(final List<BaseBet> tempbetList, final String TBName, boolean insertAttr) {

		Collections.sort(tempbetList, new BetCompare());
		String seqName = "";
		String checkTableName = "";
		if (TBName.indexOf("GDKLSF") != -1) {
			seqName = "SEQ_TB_GDKLSF_CHECK";
			checkTableName = "TB_GDKLSF_CHECK";
		} else if (TBName.indexOf("CQSSC") != -1) {
			seqName = "SEQ_TB_CQSSC_CHECK";
			checkTableName = "TB_CQSSC_CHECK";
		} else if (TBName.indexOf("BJSC") != -1) {
			seqName = "SEQ_TB_BJSC_CHECK";
			checkTableName = "TB_BJSC_CHECK";
		} else if (TBName.indexOf("JSSB") != -1) {
			seqName = "SEQ_TB_JSSB_CHECK";
			checkTableName = "TB_JSSB_CHECK";
		} else if (TBName.indexOf("NC") != -1) {
			seqName = "SEQ_TB_NC_CHECK";
			checkTableName = "TB_NC_CHECK";
		}

		String sql = "INSERT INTO " + checkTableName
					+ " (ID,CREATE_DATE,ORIGIN_TB_NAME,ORIGIN_ID,ORDER_NO,TYPE_CODE,MONEY,PLATE,BETTING_USER_ID,PERIODS_NUM,BETTING_DATE,ODDS,"
					+"CHIEFSTAFF,BRANCHSTAFF,STOCKHOLDERSTAFF,GENAGENSTAFF,AGENTSTAFF,COMMISSION_BRANCH,COMMISSION_GEN_AGENT,COMMISSION_STOCKHOLDER,"
					+"COMMISSION_AGENT,COMMISSION_MEMBER,RATE_CHIEF,RATE_BRANCH,RATE_GEN_AGENT,RATE_STOCKHOLDER,RATE_AGENT,COMMISSION_TYPE";

		if (insertAttr) {
			sql = sql + ",attribute";
		}
		if (TBName.indexOf("TB_GDKLSF_STRAIGHTTHROUGH") != -1 || TBName.indexOf("NC") != -1) {
			sql = sql + ",split_attribute";
		}
		sql = sql + ") " + "(select " + seqName + ".nextval,SYSDATE,'" + TBName+"',"
				+ "c.id,c.order_no,c.type_code,c.money,plate,c.betting_user_id,c.periods_num,c.betting_date,c.odds,"
				+"c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.agentstaff,c.commission_branch,c.commission_gen_agent,c.commission_stockholder,"
				+"c.commission_agent,c.commission_member,c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent,c.commission_type";

		if (insertAttr) {
			sql = sql + ",c.attribute";
		}

		if (TBName.indexOf("TB_GDKLSF_STRAIGHTTHROUGH") != -1 || TBName.indexOf("NC") != -1) {
			sql = sql + ",c.split_attribute";
		}

		sql = sql + " FROM " + TBName + " c where c.periods_num=? and c.id=?)";

		return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				BaseBet betOrder = tempbetList.get(i);

				String periodsNum = betOrder.getPeriodsNum();
				long id = betOrder.getId();
				ps.setString(1, periodsNum);
				ps.setLong(2, id);
			}

			public int getBatchSize() {
				return tempbetList.size();
			}
		});
	}

	@Override
	public int getHisCountByPeriodsNum(String periodsNum, String lotteryType) {
		String hisTableName = "";
		if ("GD".equals(lotteryType)) {
			hisTableName = Constant.GDKLSF_HIS_TABLE_NAME;
		} else if ("CQ".equals(lotteryType)) {
			hisTableName = Constant.CQSSC_HIS_TABLE_NAME;
		} else if ("BJ".equals(lotteryType)) {
			hisTableName = Constant.BJSC_HIS_TABLE_NAME;
		} else if ("K3".equals(lotteryType)) {
			hisTableName = Constant.K3_HIS_TABLE_NAME;
		} else if ("NC".equals(lotteryType)) {
			hisTableName = Constant.NC_HIS_TABLE_NAME;
		}
		final String sql = "select count(*) from " + hisTableName + " where periods_num = ?";
		return jdbcTemplate.queryForInt(sql, new Object[] { periodsNum });
	}

	@Override
	public int getMatchCheckCountByPeriodsNum(String periodsNum, String lotteryType) {
		String hisTableName = "";
		String checkTableName = "";
		if ("GD".equals(lotteryType)) {
			hisTableName = Constant.GDKLSF_HIS_TABLE_NAME;
			checkTableName = Constant.GDKLSF_CHECK_TABLE_NAME;
		} else if ("CQ".equals(lotteryType)) {
			hisTableName = Constant.CQSSC_HIS_TABLE_NAME;
			checkTableName = Constant.CQSSC_CHECK_TABLE_NAME;
		} else if ("BJ".equals(lotteryType)) {
			hisTableName = Constant.BJSC_HIS_TABLE_NAME;
			checkTableName = Constant.BJSC_CHECK_TABLE_NAME;
		} else if ("K3".equals(lotteryType)) {
			hisTableName = Constant.K3_HIS_TABLE_NAME;
			checkTableName = Constant.K3_CHECK_TABLE_NAME;
		} else if ("NC".equals(lotteryType)) {
			hisTableName = Constant.NC_HIS_TABLE_NAME;
			checkTableName = Constant.NC_CHECK_TABLE_NAME;
		}

		String sql = "select count(*) from "
				+ hisTableName
				+ " his,"
				+ checkTableName
				+ " checkTb where "
				+ " his.origin_tb_name=checkTb.origin_tb_name and his.origin_id=checkTb.origin_id and his.order_no=checkTb.order_no "
				+ " and his.type_code=checkTb.type_code and his.money=checkTb.money and his.plate=checkTb.plate and his.betting_user_id = checkTb.betting_user_id "
				+ " and his.periods_num=checkTb.periods_num and his.betting_date=checkTb.betting_date and his.odds=checkTb.odds ";
		if ("GD".equals(lotteryType) || "NC".equals(lotteryType)) {
			sql = sql
					+ " and nvl ( his.attribute,'1')=nvl ( checkTb.attribute, '1') and nvl ( his.split_attribute,'2')=nvl ( checkTb.split_attribute, '2')";
		}
		sql = sql + " and checkTb.periods_num = ?";
		return jdbcTemplate.queryForInt(sql, new Object[] { periodsNum });
	}

	@Override
	public List<BetCheckVo> getCheckVoByPeriodsNumInCheckTable(String periodsNum, String lotteryType,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======getCheckVoByPeriodsNumInCheckTable开始执行SCHEME为:"+scheme+"==============");
		
		String checkTableName = "";
		if (Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)) {
			checkTableName = Constant.GDKLSF_CHECK_TABLE_NAME;
		} else if (Constant.LOTTERY_TYPE_CQSSC.equals(lotteryType)) {
			checkTableName = Constant.CQSSC_CHECK_TABLE_NAME;
		} else if (Constant.LOTTERY_TYPE_BJSC.equals(lotteryType)) {
			checkTableName = Constant.BJSC_CHECK_TABLE_NAME;
		} else if (Constant.LOTTERY_TYPE_K3.equals(lotteryType)) {
			checkTableName = Constant.K3_CHECK_TABLE_NAME;
		} else if (Constant.LOTTERY_TYPE_NC.equals(lotteryType)) {
			checkTableName = Constant.NC_CHECK_TABLE_NAME;
		}
		final String sql = "select * from " + scheme+checkTableName + " where periods_num = ?";
		return (List<BetCheckVo>) jdbcTemplate.query(sql, new Object[] { periodsNum }, new BetMapper(lotteryType));
	}

	@Override
	public List<BetCheckVo> getCheckVoByPeriodsNumInHisTable(String periodsNum, String lotteryType,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======getCheckVoByPeriodsNumInHisTable开始执行SCHEME为:"+scheme+"==============");
		String hisTableName = "";
		if (Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)) {
			hisTableName = Constant.GDKLSF_HIS_TABLE_NAME;
		} else if (Constant.LOTTERY_TYPE_CQSSC.equals(lotteryType)) {
			hisTableName = Constant.CQSSC_HIS_TABLE_NAME;
		} else if (Constant.LOTTERY_TYPE_BJSC.equals(lotteryType)) {
			hisTableName = Constant.BJSC_HIS_TABLE_NAME;
		} else if (Constant.LOTTERY_TYPE_K3.equals(lotteryType)) {
			hisTableName = Constant.K3_HIS_TABLE_NAME;
		} else if (Constant.LOTTERY_TYPE_NC.equals(lotteryType)) {
			hisTableName = Constant.NC_HIS_TABLE_NAME;
		}
		final String sql = "select * from " + scheme+hisTableName + " where periods_num = ?";
		return (List<BetCheckVo>) jdbcTemplate.query(sql, new Object[] { periodsNum }, new BetMapper(lotteryType));
	}
	
	@Override
    public void insertReplenishCheck(final Replenish replenish){
		//public int[] insertReplenishCheck(final Replenish replenish){
    	String insetSql="insert into tb_replenish_check (ID, ORDER_NO, TYPE_CODE, MONEY, ATTRIBUTE, REPLENISH_USER_ID, " +
    			"PERIODS_NUM, PLATE, BETTING_DATE, ODDS, UPDATE_USER, UPDATE_DATE, REMARK, " +
    			"CHIEFSTAFF, BRANCHSTAFF, STOCKHOLDERSTAFF, GENAGENSTAFF, AGENTSTAFF, " +
    			"RATE_CHIEF, RATE_BRANCH, RATE_STOCKHOLDER, RATE_GEN_AGENT, RATE_AGENT, " +
    			"ODDS2, COMMISSION_CHIEF, COMMISSION_BRANCH, COMMISSION_GEN_AGENT, " +
    			"COMMISSION_STOCKHOLDER, COMMISSION_AGENT, COMMISSION_MEMBER, COMMISSION_TYPE, SELECT_ODDS)" +
    			"values (seq_tb_replenish_check.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
    			"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    	Object[] args = new Object[] { replenish.getOrderNo(),replenish.getTypeCode(),replenish.getMoney(),
    			replenish.getAttribute(),replenish.getReplenishUserId(),replenish.getPeriodsNum(),
    			replenish.getPlate(),
    			new java.sql.Date(replenish.getBettingDate().getTime()),
    			replenish.getOdds(),
    			replenish.getUpdateUser(),
    			new java.sql.Date(new java.util.Date().getTime()),
    			replenish.getRemark(),replenish.getChiefStaff(),replenish.getBranchStaff(),
    			replenish.getStockHolderStaff(),replenish.getGenAgenStaff(),replenish.getAgentStaff(),
    			replenish.getRateChief(),replenish.getRateBranch(),replenish.getRateStockHolder(),
    			replenish.getRateGenAgent(),replenish.getRateAgent(),replenish.getOdds2(),
    			replenish.getCommissionChief(),replenish.getCommissionBranch(),replenish.getCommissionStockHolder(),
    			replenish.getCommissionGenAgent(),replenish.getCommissionAgent(),replenish.getCommissionMember(),
    			replenish.getCommissionType(),replenish.getSelectedOdds()};
    	jdbcTemplate.update(insetSql, args);
    }

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}

class BetMapper implements RowMapper {
	private String lotteryType;

	public BetMapper() {
		super();
	}

	public BetMapper(String lotteryType) {
		super();
		this.lotteryType = lotteryType;
	}

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BetCheckVo betCheck = new BetCheckVo();

		betCheck.setId(String.valueOf(rs.getLong("id")));
		betCheck.setOriginTBName(rs.getString("origin_tb_name"));
		betCheck.setOriginID(String.valueOf(rs.getLong("origin_id")));
		betCheck.setOrderNo(rs.getString("order_no"));
		betCheck.setTypeCode(rs.getString("type_code"));
		betCheck.setMoney(String.valueOf(rs.getDouble("money")));
		betCheck.setPlate(rs.getString("plate"));
		betCheck.setBettingUserID(String.valueOf(rs.getLong("betting_user_id")));
		betCheck.setPeriodsNum(rs.getString("periods_num"));
		betCheck.setBettingDate(rs.getDate("betting_date"));
		betCheck.setOdds(String.valueOf(rs.getDouble("odds")));

		betCheck.setChiefStaff(String.valueOf(rs.getLong("chiefstaff")));
		betCheck.setBranchStaff(String.valueOf(rs.getLong("branchstaff")));
		betCheck.setStockholderStaff(String.valueOf(rs.getLong("stockholderstaff")));
		betCheck.setGenagenStaff(String.valueOf(rs.getLong("genagenstaff")));
		betCheck.setAgentStaff(String.valueOf(rs.getLong("agentstaff")));

		betCheck.setCommissionBranch(String.valueOf(rs.getDouble("commission_branch")));
		betCheck.setCommissionGenAgent(String.valueOf(rs.getDouble("commission_gen_agent")));
		betCheck.setCommissionStockholder(String.valueOf(rs.getDouble("commission_stockholder")));
		betCheck.setCommissionAgent(String.valueOf(rs.getDouble("commission_agent")));
		betCheck.setCommissionMember(String.valueOf(rs.getDouble("commission_member")));
		betCheck.setRateChief(String.valueOf(rs.getDouble("rate_chief")));
		betCheck.setRateBranch(String.valueOf(rs.getDouble("rate_branch")));
		betCheck.setRateGenAgent(String.valueOf(rs.getDouble("rate_gen_agent")));
		betCheck.setRateStockholder(String.valueOf(rs.getDouble("rate_stockholder")));
		betCheck.setRateAgent(String.valueOf(rs.getDouble("rate_agent")));
		betCheck.setCommissionType(rs.getString("commission_type"));

		if (Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType) || Constant.LOTTERY_TYPE_NC.equals(lotteryType)) {
			betCheck.setAttribute(rs.getString("attribute"));
			betCheck.setSplitAttribute(rs.getString("split_attribute"));
		}
		return betCheck;
	}

}