package com.npc.lottery.member.dao.hibernate;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.npc.lottery.common.Constant;
import com.npc.lottery.member.dao.interf.IBetDao;
import com.npc.lottery.member.dao.interf.ICheckDao;
import com.npc.lottery.member.dao.interf.IPlayTypeDao;
import com.npc.lottery.member.entity.BalanceInfo;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.BillSearchVo;
import com.npc.lottery.member.entity.CQandGDReportInfo;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.entity.PlayWinInfo;
import com.npc.lottery.periods.entity.PeriodsNumVo;
import com.npc.lottery.statreport.entity.CqsscHis;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.sysmge.logic.interf.IManagerStaffLogic;
import com.npc.lottery.sysmge.logic.interf.IMemberStaffLogic;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.util.Page;
import com.npc.lottery.util.PlayTypeUtils;
import com.npc.lottery.util.SpringUtil;

/**
 * 角色所拥有的功能数据库处理类
 * 
 * @author none
 * @param <T>
 * 
 */
public class BetDao<T> implements IBetDao {

	private JdbcTemplate jdbcTemplate;
	private IPlayTypeDao playTypeDao;
	private IMemberStaffLogic memberStaffLogic;
	private IManagerStaffLogic managerStaffLogic;
	
	private ICheckDao checkDao;
	
	private static final Logger log = Logger.getLogger(BetDao.class);
	private int getSeqId(String seqName){
		String sql = "select "+seqName+".nextval from dual";
		return jdbcTemplate.queryForInt(sql);
	}
	
	/**
	 * 查询商铺下的会员输赢信息，盘期作废时，更新会员可用金额所用
	 * @param periodNum 盘期
	 * @param scheme
	 * @param tables  投注历史表
	 * @return
	 */
	@Override
	public List<BaseBet> queryAllMemberWinOrLoseMoney(String periodNum,String scheme,String tableName){
		if(StringUtils.isNotBlank(scheme)){
			scheme=scheme+".";
		}
		List<BaseBet> betList=new ArrayList<BaseBet>();
		Object[] parmeters=new Object[]{periodNum};
		String sql="select SUM(DECODE(win_state,1,win_amount+money,0))+SUM(money*(commission_member/100-1)) as money,BETTING_USER_ID as bettingUserId  from "+
					scheme+
					tableName+ 
				   " where PERIODS_NUM=? group by BETTING_USER_ID ";
		betList=jdbcTemplate.query(sql, parmeters,new AllMemberBetMoneyMapper());
		return betList;
	}
	
	/**
	 * 查询商铺下的会员投注信息，盘期停开时，更新会员可用金额所用
	 * @param periodNum 盘期
	 * @param scheme
	 * @param tables  投注表
	 * @return
	 */
	@Override
	public List<BaseBet> queryAllMemberBetMoney(String periodNum, String scheme, String[] tables) {
		if(StringUtils.isNotBlank(scheme)){
			scheme=scheme+".";
		}
		List<BaseBet> betList=new ArrayList<BaseBet>();
		Object[] parmeters=new Object[tables.length];
		StringBuffer findSql=new StringBuffer("select sum(money) money,bettingUserId from ( ");
		for (int i = 0; i < tables.length; i++) {
			String tableName=tables[i];
			findSql.append("	select sum(money) as money,betting_user_id as bettingUserId from "+
								scheme+
								tableName+ 
								" where periods_num=? group by betting_user_id ");
			if(i<tables.length-1){
				findSql.append("	union all");
			}
			parmeters[i]=periodNum;
		}
		findSql.append(" ) group by bettingUserId");
		betList=jdbcTemplate.query(findSql.toString(), parmeters,new AllMemberBetMoneyMapper());
		return betList;
	}
	
	@Override
	public int[] batchInsert(final List<BaseBet> tempbetList, final String TBName, boolean insertAttr) {

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

		String sql = "insert into " + TBName + "(id,type_code,order_no,money,betting_user_id,periods_num,plate,"
				+ "  ChiefStaff,BranchStaff,StockholderStaff,GenAgenStaff,AgentStaff,"
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
		//同步插入校验表
		checkDao.batchInsertToCheck(tempbetList, TBName, insertAttr);
		return result;
	}

	private Object[] getParameters(BaseBet betOrder, String TBName) {
		List<Object> parameter = Lists.newArrayList();
		String playType = betOrder.getPlayType();
		int money = betOrder.getMoney();
		Long userName = betOrder.getBettingUserId();
		String periodsNum = betOrder.getPeriodsNum();
		String plate = betOrder.getPlate();
		BigDecimal odds = betOrder.getOdds();
		String attribute = betOrder.getAttribute();
		String ordeNo = betOrder.getOrderNo();
		Integer commissionMember = betOrder.getCompoundNum();
		// 投注类型
		parameter.add(playType);
		parameter.add(ordeNo);
		parameter.add(money);

		parameter.add(userName);

		parameter.add(periodsNum);

		parameter.add(plate);

		parameter.add(betOrder.getChiefStaff());
		parameter.add(betOrder.getBranchStaff());
		parameter.add(betOrder.getStockholderStaff());
		parameter.add(betOrder.getGenAgenStaff());
		parameter.add(betOrder.getAgentStaff());

		parameter.add(betOrder.getBranchCommission() == null ? 0 : betOrder
				.getBranchCommission());
		parameter.add(betOrder.getStockHolderCommission() == null ? 0
				: betOrder.getStockHolderCommission());
		parameter.add(betOrder.getGenAgenCommission() == null ? 0 : betOrder
				.getGenAgenCommission());
		parameter.add(betOrder.getAgentStaffCommission() == null ? 0 : betOrder
				.getAgentStaffCommission());
		parameter.add(betOrder.getMemberCommission() == null ? 0 : betOrder
				.getMemberCommission());

		parameter.add(betOrder.getChiefRate());
		parameter.add(betOrder.getBranchRate());
		parameter.add(betOrder.getStockHolderRate());
		parameter.add(betOrder.getGenAgenRate());
		parameter.add(betOrder.getAgentStaffRate());

		parameter.add(betOrder.getCommissionType());

		if (Constant.HK_LM_TABLE_NAME.equals(TBName)) {

			parameter.add(betOrder.getHklmOdd().split("\\|")[0]);
			parameter.add(betOrder.getHklmOdd().split("\\|")[1]);

		} else if (Constant.HK_GG_TABLE_NAME.equals(TBName)) {

			parameter.add(betOrder.getOdds());
			parameter.add(betOrder.getOdds2());

		} else {
			parameter.add(odds);

		}

		if (attribute != null) {
			parameter.add(attribute);

		}
		if (TBName.indexOf("HKLHC") != -1 || TBName.indexOf("GDKLSF") != -1) {
			parameter.add(commissionMember);

		}
		if (TBName.indexOf("TB_GDKLSF_STRAIGHTTHROUGH") != -1
				|| TBName.indexOf("TB_HKLHC_LM") != -1
				|| TBName.indexOf("TB_HKLHC_SXL") != -1
				|| TBName.indexOf("TB_HKLHC_WSL") != -1
				|| TBName.indexOf("TB_HKLHC_WBZ") != -1) {
			parameter.add(betOrder.getSplitAttribute());

		}
		if (TBName.indexOf("TB_HKLHC_SXL") != -1
				|| TBName.indexOf("TB_HKLHC_WSL") != -1
				|| TBName.indexOf("TB_HKLHC_WBZ") != -1
				|| Constant.HK_GG_TABLE_NAME.equals(TBName))
			parameter.add(betOrder.getSelectedOdds());

		return parameter.toArray(new Object[parameter.size()]);
	}

	public Page querySSCUserBetDetail(Page page, Long userId) {

		List<BaseBet> retList = new ArrayList<BaseBet>();
		for (int i = 0; i < Constant.GDKLSF_TABLE_LIST.length; i++) {
			String tableName = Constant.GDKLSF_TABLE_LIST[i];
			String attribute = "null";
			if (tableName.equals(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME))
				attribute = "attribute";
			String sql = " select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,"
					+ attribute
					+ " as attribute ,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from " + tableName + " where betting_user_id= ? ";
			Object[] parameters = new Object[] { userId };
			sql = "select * from (" + sql + ") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters,
					new GDBetDetailItemMapper());
			retList.addAll(betlist);

		}

		for (int i = 0; i < Constant.CQSSC_TABLE_LIST.length; i++) {
			String tableName = Constant.CQSSC_TABLE_LIST[i];
			String sql = " select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,null as attribute,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from " + tableName + " where betting_user_id= ? ";
			Object[] parameters = new Object[] { userId };
			sql = "select * from (" + sql + ") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters,
					new GDBetDetailItemMapper());

			retList.addAll(betlist);
		}

		for (int i = 0; i < Constant.BJSC_TABLE_LIST.length; i++) {
			String tableName = Constant.BJSC_TABLE_LIST[i];
			String sql = " select win_state as winState, '"
					+ tableName
					+ "' as tableName, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,null as attribute,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from " + tableName + " where betting_user_id= ? ";
			Object[] parameters = new Object[] { userId };
			sql = "select * from (" + sql + ") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters,
					new BJSCBetDetailItemMapper());

			retList.addAll(betlist);
		}
		/*
		 * add by peter for K3 start
		 */
		for (int i = 0; i < Constant.K3_TABLE_LIST.length; i++) {
			String tableName = Constant.K3_TABLE_LIST[i];
			String sql = " select win_state as winState, '"
					+ tableName
					+ "' as tableName, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,null as attribute,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from " + tableName + " where betting_user_id= ? ";
			Object[] parameters = new Object[] { userId };
			sql = "select * from (" + sql + ") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters,
					new BJSCBetDetailItemMapper());
			
			retList.addAll(betlist);
		}
		/*
		 * add by peter for K3 end
		 */

		/*
		 * add by peter for NC start
		 */
		for (int i = 0; i < Constant.NC_TABLE_LIST.length; i++) {
			String tableName = Constant.NC_TABLE_LIST[i];
			String attribute = "attribute";
			String sql = " select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,"
					+ attribute
					+ " as attribute ,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from "
					+ tableName
					+ " where betting_user_id= ? ";
			Object[] parameters = new Object[] { userId };
			sql = "select * from (" + sql + ") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters, new GDBetDetailItemMapper());
			retList.addAll(betlist);

		}
		/*
		 * add by peter for NC end
		 */
		
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		Collections.sort(retList,new OrderBetCompare());
	
		Integer money = 0;
		double winMoney = 0d; 
		for (int i = 0; i < retList.size(); i++) { 
			Integer betMoney=retList.get(i).getMoney();
			BigDecimal odd=retList.get(i).getOdds();
			//fixed by peter
			int count = retList.get(i).getCount();
			money += betMoney * count;
			winMoney+=betMoney*(odd.doubleValue()-1) * count;
			
		}
		page.setTotalCount(retList.size());
		retList=retList.subList(first, last) ;
		page.setResult(retList);
		
		page.setTotal1(Double.valueOf(money));
		page.setTotal2(winMoney);
		return page;

	}

	/**
	 * 查詢廣東快樂十分注單信息-已结算 -历史表 add by Aaron
	 * 
	 */
	@Override
	public Page queryGDKLSFBetByObjHis(Page page, BillSearchVo entry) {

		String whereSQL = this.getWhereSQLByBillSearchVo(entry, "GD");
		int startNum = page.getPageSize() * (page.getPageNo() - 1);
		int endNum = page.getPageSize() * (page.getPageNo());
		String sql = "";
		String countSQl = "";

		// 結果範圍 b.money*(b.odds-1)*count 要与页面公式一样 String
		// winMoney=nf.format(money.floatValue()*(odds.floatValue()-1)*count);
		if (!"".equals(entry.getResMax()) && null != entry.getResMax()
				&& !"".equals(entry.getResMin()) && null != entry.getResMin()) {
			sql = " select * from (select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,attribute as attribute ,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from tb_gdklsf_his "
					+ " where  1=1  "
					+ whereSQL
					+ " ) b where rankNum=1 and b.money*(b.odds-1)*count >= "
					+ entry.getResMin()
					+ " and b.money*(b.odds-1)*count <= "
					+ entry.getResMax() + "";
			countSQl = "select count(1) from (" + sql + ") ";

		} else {

			sql = " select * from (select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,attribute as attribute ,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from tb_gdklsf_his "
					+ " where  1=1 "
					+ whereSQL
					+ " ) where rankNum=1 ";
			countSQl = "select count(1) from (" + sql + ") ";
		}
		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		paginationSQL.append(sql);
		paginationSQL.append("　) temp where ROWNUM <= "
				+ String.valueOf(endNum));
		paginationSQL.append(" ) WHERE　num > " + String.valueOf(startNum));

		Object[] parameters = new Object[] {};
		List betlist = jdbcTemplate.query(paginationSQL.toString(), parameters,
				new GDBetDetailItemMapper());

		int count = jdbcTemplate.queryForInt(countSQl);
		page.setTotalCount(count);
		page.setResult(betlist);

		return page;
	}

	/**
	 * 查詢廣東快樂十分注單信息 add by Aaron
	 * 
	 */
	@Override
	public Page queryGDKLSFBetByObj(Page page, BillSearchVo entry) {

		String whereSQL = this.getWhereSQLByBillSearchVo(entry, "GD");

		List<BaseBet> retList = new ArrayList<BaseBet>();
		for (int i = 0; i < Constant.GDKLSF_TABLE_LIST.length; i++) {
			String tableName = Constant.GDKLSF_TABLE_LIST[i];
			String attribute = "null";
			if (tableName.equals(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME))
				attribute = "attribute";
			String sql = " select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,"
					+ attribute
					+ " as attribute ,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from " + tableName + " where 1=1 " + whereSQL + "";
			Object[] parameters = new Object[] {};

			// 結果範圍 b.money*(b.odds-1)*count 要与页面公式一样 String
			// winMoney=nf.format(money.floatValue()*(odds.floatValue()-1)*count);
			if (!"".equals(entry.getResMax()) && null != entry.getResMax()
					&& !"".equals(entry.getResMin())
					&& null != entry.getResMin()) {
				sql = "select * from ("
						+ sql
						+ ") b where rankNum=1  and b.money*(b.odds-1)*count >= "
						+ entry.getResMin()
						+ " and b.money*(b.odds-1)*count <= "
						+ entry.getResMax() + "";
			} else {
				sql = "select * from (" + sql + ") where rankNum=1";
			}

			List betlist = jdbcTemplate.query(sql, parameters,
					new GDBetDetailItemMapper());
			retList.addAll(betlist);

		}
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		Collections.sort(retList);
		page.setTotalCount(retList.size());
		page.setResult(retList.subList(first, last));
		return page;
	}

	/**
	 * 查詢注單信息 add by Aaron 20121116 只用于 总监 大运的 GD CQ BJ
	 */
	@Override
	public Page queryBetByObjAdmin(Page page, BillSearchVo entry) {

		int startNum = page.getPageSize() * (page.getPageNo() - 1);
		int endNum = page.getPageSize() * (page.getPageNo());
		String sql = "";
		String countSQl = "";

		String sqlGD = getGDTZHISBillSearchSQLAdmin(entry);
		String sqlCQ = getCQTZHISBillSearchSQLAdmin(entry);
		String sqlBJ = getBJTZHISBillSearchSQLAdmin(entry);
		
		String sqlBH = getRepleishBillSearchSQLAdmin(entry);
		// add by peter for K3
		String sqlJS = getJSTZHISBillSearchSQLAdmin(entry);
		// add by peter for NC
		String sqlNC = getNCTZHISBillSearchSQLAdmin(entry);
		if (null == entry.getSubType() || "".equals(entry.getSubType())) { // 全部
			if ("1".equals(entry.getBillType())) { // 会员
				sql = sqlGD + " union " + sqlCQ + " union " + sqlBJ + " union "+sqlJS+" union"+sqlNC+" ";
			} else if ("0".equals(entry.getBillType())) {// 捕获
				sql = sqlBH;
			} else if (null == entry.getBillType()
					|| "".equals(entry.getBillType())) {// 会员+捕获
				sql = sqlGD + " union " + sqlCQ + " union " + sqlBJ + " union " +sqlJS +" union"+sqlNC+" union "
						+ sqlBH + " ";
			}
		} else if ("GD".equals(entry.getSubType())) {
			if ("1".equals(entry.getBillType())) { // 会员
				sql = sqlGD;
			} else if ("0".equals(entry.getBillType())) {// 捕获
				sql = sqlBH;
			} else if (null == entry.getBillType()
					|| "".equals(entry.getBillType())) {// 会员+捕获
				sql = sqlGD + "  union " + sqlBH + "";
			}
		} else if ("CQ".equals(entry.getSubType())) {
			if ("1".equals(entry.getBillType())) { // 会员
				sql = sqlCQ;
			} else if ("0".equals(entry.getBillType())) {// 捕获
				sql = sqlBH;
			} else if (null == entry.getBillType()
					|| "".equals(entry.getBillType())) {// 会员+捕获
				sql = sqlCQ + " union " + sqlBH + "";
			}
		} else if ("BJ".equals(entry.getSubType())) {
			if ("1".equals(entry.getBillType())) { // 会员
				sql = sqlBJ;
			} else if ("0".equals(entry.getBillType())) {// 捕获
				sql = sqlBH;
			} else if (null == entry.getBillType()
					|| "".equals(entry.getBillType())) {// 会员+捕获
				sql = " " + sqlBJ + " union " + sqlBH + "";
			}
		}
		//add by peter for k3
		else if ("K3".equals(entry.getSubType())) {//快三
			if ("1".equals(entry.getBillType())) { // 会员
				sql = sqlJS;
			} else if ("0".equals(entry.getBillType())) {// 捕获
				sql = sqlBH;
			} else if (null == entry.getBillType()
					|| "".equals(entry.getBillType())) {// 会员+捕获
				sql = " " + sqlJS + " union " + sqlBH + "";
			}
		}
		//add by peter for NC
		else if ("NC".equals(entry.getSubType())) {//幸运农场
			if ("1".equals(entry.getBillType())) { // 会员
				sql = sqlNC;
			} else if ("0".equals(entry.getBillType())) {// 捕获
				sql = sqlBH;
			} else if (null == entry.getBillType()
					|| "".equals(entry.getBillType())) {// 会员+捕获
				sql = " " + sqlNC + " union " + sqlBH + "";
			}
		}

		//sql = sqlBH;//FOR TEST,delete after test
		
		countSQl = "select count(1) from (" + sql + ") ";
		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		paginationSQL.append(sql);
		paginationSQL.append("　) temp where ROWNUM <= "
				+ String.valueOf(endNum));
		paginationSQL.append(" ) WHERE　num > " + String.valueOf(startNum));

		Object[] parameters = new Object[] {};
		List betlist = jdbcTemplate.query(paginationSQL.toString(), parameters,
				new AdminBetDetailItemMapper());
		int count = jdbcTemplate.queryForInt(countSQl);
		page.setTotalCount(count);
		page.setResult(betlist);
		return page;
	}

	// 总监条件查询 Bj 投注 历史表的sql
	public String getBJTZHISBillSearchSQLAdmin(BillSearchVo entry) {
		String whereSQL = this.getWhereSQLByBillSearchVoAdmin(entry, "BJ");
		String sqlObj = "";
		StringBuffer sqlHis=new StringBuffer();
		for (int i = 0; i < Constant.BJSC_TABLE_LIST.length; i++) {
			String tableName = Constant.BJSC_TABLE_LIST[i];
			String sql = "";
			
			sql = this.getHisSql(tableName, null,"betting_user_id", whereSQL,"tb_frame_member_staff");
			
			if (i != (Constant.BJSC_TABLE_LIST.length - 1)) {
				sqlObj = sqlObj + "(" + sql + ") union ";
			} else {
				sqlObj = sqlObj + sql;
			}
		}
		
		sqlHis.append(this.getHisSql("TB_BJSC_HIS",null,"betting_user_id", whereSQL,"tb_frame_member_staff"));
		
		return "( " + sqlObj + " )" + " union all (" + sqlHis + ") ";
	}
	// 总监条件查询 JS(K3) 投注 历史表的sql
	private String getJSTZHISBillSearchSQLAdmin(BillSearchVo entry) {
		String whereSQL = this.getWhereSQLByBillSearchVoAdmin(entry, "K3");
		String sqlObj = "";
		StringBuffer sqlHis=new StringBuffer();
		for (int i = 0; i < Constant.K3_TABLE_LIST.length; i++) {
			String tableName = Constant.K3_TABLE_LIST[i];
			String sql = "";
			
			sql = this.getHisSql(tableName, null,"betting_user_id", whereSQL,"tb_frame_member_staff");
			
			if (i != (Constant.K3_TABLE_LIST.length - 1)) {
				sqlObj = sqlObj + "(" + sql + ") union ";
			} else {
				sqlObj = sqlObj + sql;
			}
		}
		
		sqlHis.append(this.getHisSql("TB_JSSB_HIS",null,"betting_user_id", whereSQL,"tb_frame_member_staff"));
		
		return "( " + sqlObj + " )" + " union all (" + sqlHis + ") ";
	}

	// 总监条件查询CQ 投注 历史表的sql
	public String getCQTZHISBillSearchSQLAdmin(BillSearchVo entry) {
		String whereSQL = this.getWhereSQLByBillSearchVoAdmin(entry, "CQ");
		String sqlObj = "";
		StringBuffer sqlHis=new StringBuffer();
		for (int i = 0; i < Constant.CQSSC_TABLE_LIST.length; i++) {
			String tableName = Constant.CQSSC_TABLE_LIST[i];
			String sql = "";
			sql = this.getHisSql(tableName, null,"betting_user_id", whereSQL,"tb_frame_member_staff");
			
			if (i != (Constant.CQSSC_TABLE_LIST.length - 1)) {
				sqlObj = sqlObj + "(" + sql + ") union all ";
			} else {
				sqlObj = sqlObj + sql + " ";
			}
		}
		
        sqlHis.append(this.getHisSql(" TB_CQSSC_HIS ",null,"betting_user_id", whereSQL,"tb_frame_member_staff"));
		

		return "( " + sqlObj + " )" + " union all (" + sqlHis + ") ";

	}

	// 总监条件查询 GD 投注 历史表的sql
	public String getGDTZHISBillSearchSQLAdmin(BillSearchVo entry) {
		String whereSQL = this.getWhereSQLByBillSearchVoAdmin(entry, "GD");

		String sqlObj = "";
		StringBuffer sqlHis=new StringBuffer();
		for (int i = 0; i < Constant.GDKLSF_TABLE_LIST.length; i++) {
			String sql = "";
			String tableName = Constant.GDKLSF_TABLE_LIST[i];
			String attribute = "null";
			if (tableName.equals(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME)) {
				attribute = "attribute";
			}
			
			sql = this.getHisSql(tableName, attribute,"betting_user_id", whereSQL,"tb_frame_member_staff");
			
			if (i != (Constant.GDKLSF_TABLE_LIST.length - 1)) {
				sqlObj = sqlObj + "(" + sql + ") union all ";
			} else {
				sqlObj = sqlObj + sql;
			}

		}
		
		sqlHis.append(this.getHisSql("TB_GDKLSF_HIS","attribute","betting_user_id", whereSQL,"tb_frame_member_staff"));
		

		return "( " + sqlObj + " )" + " union all (" + sqlHis + ") ";
	}
	
	// 总监条件查询 NC 投注 历史表的sql
	public String getNCTZHISBillSearchSQLAdmin(BillSearchVo entry) {
		String whereSQL = this.getWhereSQLByBillSearchVoAdmin(entry, "NC");

		String sqlObj = "";
		StringBuffer sqlHis = new StringBuffer();
		for (int i = 0; i < Constant.NC_TABLE_LIST.length; i++) {
			String sql = "";
			String tableName = Constant.NC_TABLE_LIST[i];
			String attribute = "attribute";

			sql = this.getHisSql(tableName, attribute, "betting_user_id", whereSQL, "tb_frame_member_staff");

			if (i != (Constant.NC_TABLE_LIST.length - 1)) {
				sqlObj = sqlObj + "(" + sql + ") union all ";
			} else {
				sqlObj = sqlObj + sql;
			}

		}

		sqlHis.append(this.getHisSql("TB_NC_HIS", "attribute", "betting_user_id", whereSQL, "tb_frame_member_staff"));

		return "( " + sqlObj + " )" + " union all (" + sqlHis + ") ";
	}

	// 总监条件查询 取 捕获表的sql
	public String getRepleishBillSearchSQLAdmin(BillSearchVo entry) {
		// 捕获sql
		String whereSQLBH = this.getWhereSQLByBillSearchVoReplenishAdmin(entry,"BH");
		
		StringBuffer sqlBH=new StringBuffer();
		//add by peter
		StringBuffer sqlBHHis=new StringBuffer();
		
		sqlBH.append(this.getHisSql("tb_replenish","attribute","replenish_user_id", whereSQLBH,"tb_frame_manager_staff"));
		sqlBHHis.append(this.getHisSql("tb_replenish_his","attribute","replenish_user_id", whereSQLBH,"tb_frame_manager_staff"));
		
		return "( " + sqlBH + " )" + " union all (" + sqlBHHis + ") ";
	}

	/**
	 * 取消 廣東快樂十分注單信息 add by Aaron
	 * 
	 * @param orderNum
	 *            注單號 修改狀態為 4
	 */
	@Override
	public void updateGDKLSFBetByObj(String orderNum) {

		for (int i = 0; i < Constant.GDKLSF_TABLE_LIST.length; i++) {
			String tableName = Constant.GDKLSF_TABLE_LIST[i];
			String sql = "update " + tableName
					+ "  set win_state=4 where order_no=?";
			Object[] args = new Object[] { orderNum };
			jdbcTemplate.update(sql, args);

			sql = "select ID from  " + tableName + "  where order_no=?";
			List<Integer> listID = jdbcTemplate.queryForList(sql, args,
					Integer.class);
			if (listID != null && listID.size() > 0) {
				for (int k = 0; k < listID.size(); k++) {
					miragationGDReLotteryDataToHistory(listID.get(i), tableName);
					// System.out.println(listID.get(i)+"  -- "+tableName);
				}
			}

		}
	}

	/**
	 * 删除 廣東快樂十分注單信息 add by Aaron
	 * 
	 * @param orderNum
	 *            注單號
	 */
	/*@Override
	public void deleteGDKLSFBetByObj(String orderNum) {

		for (int i = 0; i < Constant.GDKLSF_TABLE_LIST.length; i++) {
			String tableName = Constant.GDKLSF_TABLE_LIST[i];
			String sql = " delete from  " + tableName + " where order_no=? ";
			Object[] args = new Object[] { orderNum };
			jdbcTemplate.update(sql, args);

		}
	}*/

	/**
	 * 取消 北京赛车注單信息 add by Aaron
	 * 
	 * @param orderNum
	 *            注單號 修改狀態為 4
	 */
	public void updateBJSCBetByObj(String orderNum) {

		for (int i = 0; i < Constant.BJSC_TABLE_LIST.length; i++) {
			String tableName = Constant.BJSC_TABLE_LIST[i];
			String sql = "update " + tableName
					+ "  set win_state=4 where order_no=?";
			Object[] args = new Object[] { orderNum };
			jdbcTemplate.update(sql, args);

			sql = "select ID from  " + tableName + "  where order_no=?";
			List<Integer> listID = jdbcTemplate.queryForList(sql, args,
					Integer.class);
			if (listID != null && listID.size() > 0) {
				for (int k = 0; k < listID.size(); k++) {

					miragationBJReLotteryDataToHistory(listID.get(i), tableName);
				}
			}

		}
	}

	/**
	 * 删除 北京赛车注單信息\ 投注表 add by Aaron20121112
	 * 
	 * @param orderNum
	 *            注單號
	 */
	/*@Override
	public void deleteBJSCBetByObj(String orderNum) {
		String sql = " delete from TB_BJSC where order_no=? ";
		Object[] args = new Object[] { orderNum };
		jdbcTemplate.update(sql, args);
	}*/

	/**
	 * 查詢CQSSC注單信息 -已结算历史表 add by Aaron
	 * 
	 * @param page
	 * @return
	 */
	@Override
	public Page queryCQSSCBetByObjHis(Page page, BillSearchVo entry) {

		String whereSQL = this.getWhereSQLByBillSearchVo(entry, "CQ");
		int startNum = page.getPageSize() * (page.getPageNo() - 1);
		int endNum = page.getPageSize() * (page.getPageNo());
		String sql = "";
		String countSQl = "";
		// 結果範圍 b.money*(b.odds-1)*count 要与页面公式一样 String
		// winMoney=nf.format(money.floatValue()*(odds.floatValue()-1)*count);
		if (!"".equals(entry.getResMax()) && null != entry.getResMax()
				&& !"".equals(entry.getResMin()) && null != entry.getResMin()) {
			sql = " select * from (select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,null as attribute,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from tb_cqssc_his "
					+ " where  1=1  "
					+ whereSQL
					+ " ) b where rankNum=1 and b.money*(b.odds-1)*count >= "
					+ entry.getResMin()
					+ " and b.money*(b.odds-1)*count <= "
					+ entry.getResMax() + "";
			countSQl = "select count(1) from (" + sql + ") ";
		} else {
			sql = " select * from (select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,null as attribute,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from tb_cqssc_his "
					+ " where 1=1 "
					+ whereSQL
					+ "  ) where rankNum=1 ";
			countSQl = "select count(1) from  (" + sql + ") ";
		}
		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		paginationSQL.append(sql);
		paginationSQL.append("　) temp where ROWNUM <= "
				+ String.valueOf(endNum));
		paginationSQL.append(" ) WHERE　num > " + String.valueOf(startNum));

		Object[] parameters = new Object[] {};
		// sql="select * from ("+sql+") where rankNum=1";
		List betlist = jdbcTemplate.query(paginationSQL.toString(), parameters,
				new GDBetDetailItemMapper());

		int count = jdbcTemplate.queryForInt(countSQl);
		page.setTotalCount(count);
		page.setResult(betlist);
		return page;

	}

	/**
	 * 修改補貨表狀態 add by Aaron
	 * 
	 * @param orderNum
	 * @param state
	 * @return
	 */
	@Override
	public void updateReplenishByObj(String orderNum, String state) {

		String sql = "update tb_replenish set win_state=? where order_no=?";
		Object[] args = new Object[] { state, orderNum };
		jdbcTemplate.update(sql, args);
	}

	/**
	 * 删除 補貨表注单 add by Aaron20121113
	 * 
	 * @param orderNum
	 * @return
	 */
	/*@Override
	public void deleteReplenishByOrderNum(String orderNum) {

		String sql = " delete from tb_replenish where order_no=? ";
		Object[] args = new Object[] { orderNum };
		jdbcTemplate.update(sql, args);
	}*/
	/**
	 * 删除 历史 補貨表注单 add by Aaron20121113
	 * 
	 * @param orderNum
	 * @return
	 */
	/*@Override
	public void deleteHisReplenishByOrderNum(String orderNum) {
		
		String sql = " delete from tb_replenish_his where order_no=? ";
		Object[] args = new Object[] { orderNum };
		jdbcTemplate.update(sql, args);
	}*/
	/**
	 * 注销历史 補貨表注单 add by peter
	 * 
	 * @param orderNum
	 * @return
	 */
	@Override
	public void cancelHisReplenishByOrderNum(String orderNum) {
		
		String sql = " UPDATE  TB_REPLENISH_HIS SET WIN_STATE=4 WHERE ORDER_NO=? ";
		Object[] args = new Object[] { orderNum };
		jdbcTemplate.update(sql, args);
	}

	/**
	 * 删除 補貨表注单 add by Aaron20121122
	 * 
	 * @param orderNum
	 * @return
	 */
	@Override
	public void updateReplenishByOrderNum(Integer money, String orderNum) {

		String sql = " update   tb_replenish set MONEY=? where order_no=? ";
		Object[] args = new Object[] { money, orderNum };
		jdbcTemplate.update(sql, args);
	}

	/**
	 * 修改QSSC注單信息 add by Aaron
	 * 
	 * @param userId
	 */
	@Override
	public void updateCQSSCBetByObj(String orderNum) {

		for (int i = 0; i < Constant.CQSSC_TABLE_LIST.length; i++) {
			String tableName = Constant.CQSSC_TABLE_LIST[i];
			String sql = "update " + tableName
					+ "  set win_state=4 where order_no=?";
			Object[] args = new Object[] { orderNum };
			jdbcTemplate.update(sql, args);

			sql = "select ID from  " + tableName + "  where order_no=?";
			List<Integer> listID = jdbcTemplate.queryForList(sql, args,
					Integer.class);
			if (listID != null && listID.size() > 0) {
				for (int k = 0; k < listID.size(); k++) {
					miragationCQReLotteryDataToHistory(listID.get(i), tableName);
					// System.out.println(listID.get(i)+"  "+tableName);
				}
			}

		}
	}

	/**
	 * 删除CQSSC注單信息 add by Aaron20121112
	 * 
	 * @param userId
	 */
	/*@Override
	public void deleteCQSSCBetByObj(String orderNum) {

		for (int i = 0; i < Constant.CQSSC_TABLE_LIST.length; i++) {
			String tableName = Constant.CQSSC_TABLE_LIST[i];
			String sql = "delete from  " + tableName + "  where order_no=?";
			Object[] args = new Object[] { orderNum };
			jdbcTemplate.update(sql, args);

		}
	}*/

	/**
	 * 查詢BJSC注單信息 add by Aaron 20121110
	 * 
	 * @param page
	 * @param userId
	 * @return
	 */
	@Override
	public Page queryBJSCBetByObj(Page page, BillSearchVo entry) {

		String whereSQL = this.getWhereSQLByBillSearchVo(entry, "BJ");

		List<BaseBet> retList = new ArrayList<BaseBet>();
		for (int i = 0; i < Constant.BJSC_TABLE_LIST.length; i++) {
			String tableName = Constant.BJSC_TABLE_LIST[i];
			String sql = " select win_state as winState, '"
					+ tableName
					+ "' as tableName, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,null as attribute,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from " + tableName + " where 1=1 " + whereSQL + "";
			Object[] parameters = new Object[] {};
			// 結果範圍 b.money*(b.odds-1)*count 要与页面公式一样 String
			// winMoney=nf.format(money.floatValue()*(odds.floatValue()-1)*count);
			if (!"".equals(entry.getResMax()) && null != entry.getResMax()
					&& !"".equals(entry.getResMin())
					&& null != entry.getResMin()) {
				sql = "select * from ("
						+ sql
						+ ") b where rankNum=1  and b.money*(b.odds-1)*count >= "
						+ entry.getResMin()
						+ " and b.money*(b.odds-1)*count <= "
						+ entry.getResMax() + "";
			} else {
				sql = "select * from (" + sql + ") where rankNum=1";
			}
			List betlist = jdbcTemplate.query(sql, parameters,
					new GDBetDetailItemMapper());
			retList.addAll(betlist);
		}

		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		Collections.sort(retList);
		page.setTotalCount(retList.size());
		page.setResult(retList.subList(first, last));
		return page;

	}

	/**
	 * 查詢BJSC注單信息 -已结算历史表 add by Aaron 20121011
	 * 
	 * @param page
	 * @return
	 */
	@Override
	public Page queryBJSCBetByObjHis(Page page, BillSearchVo entry) {

		String whereSQL = this.getWhereSQLByBillSearchVo(entry, "BJ");
		int startNum = page.getPageSize() * (page.getPageNo() - 1);
		int endNum = page.getPageSize() * (page.getPageNo());
		String sql = "";
		String countSQl = "";
		// 結果範圍 b.money*(b.odds-1)*count 要与页面公式一样 String
		// winMoney=nf.format(money.floatValue()*(odds.floatValue()-1)*count);
		if (!"".equals(entry.getResMax()) && null != entry.getResMax()
				&& !"".equals(entry.getResMin()) && null != entry.getResMin()) {
			sql = " select * from (select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,null as attribute,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from TB_BJSC_HIS "
					+ " where  1=1  "
					+ whereSQL
					+ " ) b where rankNum=1 and b.money*(b.odds-1)*count >= "
					+ entry.getResMin()
					+ " and b.money*(b.odds-1)*count <= "
					+ entry.getResMax() + "";
			countSQl = "select count(1) from (" + sql + ") ";
		} else {
			sql = " select * from (select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,null as attribute,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from TB_BJSC_HIS "
					+ " where 1=1 "
					+ whereSQL
					+ "  ) where rankNum=1 ";
			countSQl = "select count(1) from  (" + sql + ") ";
		}
		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		paginationSQL.append(sql);
		paginationSQL.append("　) temp where ROWNUM <= "
				+ String.valueOf(endNum));
		paginationSQL.append(" ) WHERE　num > " + String.valueOf(startNum));

		Object[] parameters = new Object[] {};
		List betlist = jdbcTemplate.query(paginationSQL.toString(), parameters,
				new GDBetDetailItemMapper());

		int count = jdbcTemplate.queryForInt(countSQl);
		page.setTotalCount(count);
		page.setResult(betlist);
		return page;

	}

	/**
	 * 查詢CQSSC注單信息 add by Aaron
	 * 
	 * @param page
	 * @param userId
	 * @return
	 */
	@Override
	public Page queryCQSSCBetByObj(Page page, BillSearchVo entry) {

		String whereSQL = this.getWhereSQLByBillSearchVo(entry, "CQ");

		List<BaseBet> retList = new ArrayList<BaseBet>();
		for (int i = 0; i < Constant.CQSSC_TABLE_LIST.length; i++) {
			String tableName = Constant.CQSSC_TABLE_LIST[i];
			String sql = " select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,null as attribute,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from " + tableName + " where 1=1 " + whereSQL + "";
			Object[] parameters = new Object[] {};
			// 結果範圍 b.money*(b.odds-1)*count 要与页面公式一样 String
			// winMoney=nf.format(money.floatValue()*(odds.floatValue()-1)*count);
			if (!"".equals(entry.getResMax()) && null != entry.getResMax()
					&& !"".equals(entry.getResMin())
					&& null != entry.getResMin()) {
				sql = "select * from ("
						+ sql
						+ ") b where rankNum=1  and b.money*(b.odds-1)*count >= "
						+ entry.getResMin()
						+ " and b.money*(b.odds-1)*count <= "
						+ entry.getResMax() + "";
			} else {
				sql = "select * from (" + sql + ") where rankNum=1";
			}
			List betlist = jdbcTemplate.query(sql, parameters,
					new GDBetDetailItemMapper());
			retList.addAll(betlist);
		}

		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		Collections.sort(retList);
		page.setTotalCount(retList.size());
		page.setResult(retList.subList(first, last));
		return page;

	}

	public Page queryGDKLSFUserBetDetail(Page page, Long userId) {

		List<BaseBet> retList = new ArrayList<BaseBet>();
		for (int i = 0; i < Constant.GDKLSF_TABLE_LIST.length; i++) {
			String tableName = Constant.GDKLSF_TABLE_LIST[i];
			String attribute = "null";
			if (tableName.equals(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME))
				attribute = "attribute";
			String sql = " select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,"
					+ attribute
					+ " as attribute ,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from " + tableName + " where betting_user_id= ? ";
			Object[] parameters = new Object[] { userId };
			sql = "select * from (" + sql + ") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters,
					new GDBetDetailItemMapper());
			retList.addAll(betlist);

		}
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		Collections.sort(retList);
		page.setTotalCount(retList.size());
		page.setResult(retList.subList(first, last));
		return page;

	}

	public Page queryCQSSCUserBetDetail(Page page, Long userId) {

		List<BaseBet> retList = new ArrayList<BaseBet>();
		for (int i = 0; i < Constant.CQSSC_TABLE_LIST.length; i++) {
			String tableName = Constant.CQSSC_TABLE_LIST[i];
			String sql = " select win_state as winState, "
					+ tableName
					+ " as tableName type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,null as attribute,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from " + tableName + " where betting_user_id= ? ";
			Object[] parameters = new Object[] { userId };
			sql = "select * from (" + sql + ") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters,
					new GDBetDetailItemMapper());

			retList.addAll(betlist);
		}
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		Collections.sort(retList);
		page.setTotalCount(retList.size());
		page.setResult(retList.subList(first, last));
		return page;

	}

	public Page queryBJSCUserBetDetail(Page page, Long userId) {

		List<BaseBet> retList = new ArrayList<BaseBet>();
		for (int i = 0; i < Constant.BJSC_TABLE_LIST.length; i++) {
			String tableName = Constant.BJSC_TABLE_LIST[i];
			String sql = " select win_state as winState, '"
					+ tableName
					+ "' as tableName, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,null as attribute,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from " + tableName + " where betting_user_id= ? ";
			Object[] parameters = new Object[] { userId };
			sql = "select * from (" + sql + ") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters,
					new BJSCBetDetailItemMapper());

			retList.addAll(betlist);
		}
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		Collections.sort(retList);
		page.setTotalCount(retList.size());
		page.setResult(retList.subList(first, last));
		return page;

	}

	/**
	 * 修改HKLHC注單搜索 - 投注表 未结算的add by Aaron entry 搜索條件
	 */
	@Override
	public void updateHKLHCBetByObj(String orderNum) {

		// List<BaseBet> retList = new ArrayList<BaseBet>();
		// System.out.println("============");
		for (int i = 0; i < Constant.HK_TABLE_LIST.length; i++) {
			String tableName = Constant.HK_TABLE_LIST[i];
			String sql = "update " + tableName
					+ "  set win_state=4 where order_no=?";
			Object[] args = new Object[] { orderNum };
			jdbcTemplate.update(sql, args);

			sql = "select ID from  " + tableName + "  where order_no=?";
			List<Integer> listID = jdbcTemplate.queryForList(sql, args,
					Integer.class);
			if (listID != null && listID.size() > 0) {
				for (int k = 0; k < listID.size(); k++)
					miragationHKRelotteryDataToHistory(listID.get(i), tableName);
			}
		}

	}

	/**
	 * 查詢HKLHC注單搜索 - 投注表 未结算的add by Aaron entry 搜索條件
	 */
	@Override
	public Page queryHKLHCBetByObj(Page page, BillSearchVo entry) {

		String whereSQL = this.getWhereSQLByBillSearchVo(entry, "HK");

		List<BaseBet> retList = new ArrayList<BaseBet>();
		for (int i = 0; i < Constant.HK_TABLE_LIST.length; i++) {
			String tableName = Constant.HK_TABLE_LIST[i];
			String attribute = "null";
			if (tableName.equals(Constant.HK_LM_TABLE_NAME)
					|| tableName.equals(Constant.HK_LX_TABLE_NAME)
					|| tableName.equals(Constant.HK_SXL_TABLE_NAME)
					|| tableName.equals(Constant.HK_WSL_TABLE_NAME)
					|| tableName.equals(Constant.HK_WBZ_TABLE_NAME)
					|| tableName.equals(Constant.HK_GG_TABLE_NAME)

			)
				attribute = "attribute";

			String odd2 = "null";
			if (tableName.equals(Constant.HK_LM_TABLE_NAME))
				odd2 = "odds2";

			String selectedOdd = "null";
			if (tableName.equals(Constant.HK_SXL_TABLE_NAME)
					|| tableName.equals(Constant.HK_WSL_TABLE_NAME)
					|| tableName.equals(Constant.HK_WBZ_TABLE_NAME)
					|| tableName.equals(Constant.HK_GG_TABLE_NAME)

			)
				selectedOdd = "select_odds";

			String sql = " select win_state winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,"
					+ selectedOdd
					+ " as selectOdds,order_no as orderNo,"
					+ attribute
					+ " as attribute, "
					+ odd2
					+ " as odds2,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from "
					+ tableName
					+ " a"
					+ " where 1=1  "
					+ whereSQL
					+ "";

			Object[] parameters = new Object[] {};
			// 結果範圍 b.money*(b.odds-1)*count 要与页面公式一样 String
			// winMoney=nf.format(money.floatValue()*(odds.floatValue()-1)*count);
			if (!"".equals(entry.getResMax()) && null != entry.getResMax()
					&& !"".equals(entry.getResMin())
					&& null != entry.getResMin()) {
				sql = "select * from ("
						+ sql
						+ ") b where rankNum=1  and b.money*(b.odds-1)*count >= "
						+ entry.getResMin()
						+ " and b.money*(b.odds-1)*count <= "
						+ entry.getResMax() + "";
			} else {
				sql = "select * from (" + sql + ") where rankNum=1";
			}
			List betlist = jdbcTemplate.query(sql, parameters,
					new HKBetDetailItemMapper());

			retList.addAll(betlist);
		}
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		Collections.sort(retList);
		page.setTotalCount(retList.size());
		page.setResult(retList.subList(first, last));
		return page;
	}

	/**
	 * 根据条件 分页查询补货
	 * 
	 * @param page
	 * @param entry
	 * @return
	 */
	@Override
	public Page queryReplenishByPage(Page page, BillSearchVo entry) {
		String whereSQL = this.getWhereSQLByBillSearchVoReplenish(entry, "BH");
		int startNum = page.getPageSize() * (page.getPageNo() - 1);
		int endNum = page.getPageSize() * (page.getPageNo());
		String sql = "";
		String countSQl = "";
		if (!"".equals(entry.getResMax()) && null != entry.getResMax()
				&& !"".equals(entry.getResMin()) && null != entry.getResMin()) {
			// sql =
			// " select * from (select order_no as orderNo, type_code as typeCode, money as money, replenish_user_id as replenishUserId, betting_date as bettingDate, win_state as winState, odds as odds, commission as commission,attribute as attribute from tb_replenish "
			// +
			sql = " select * from (select win_state as winState, type_code as typeCode,money as money,replenish_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,select_odds as selectOdds,order_no as orderNo,attribute as attribute, odds2 as odds2,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from tb_replenish "
					+ " "
					+ " where  1=1  "
					+ whereSQL
					+ " ) b where  rankNum=1 and  b.money*(b.odds-1)*count >= "
					+ entry.getResMin()
					+ " and b.money*(b.odds-1)*count <= "
					+ entry.getResMax() + "";
			countSQl = "select count(1) from (" + sql + ") ";
		} else {
			sql = " select * from (select win_state as winState, type_code as typeCode,money as money,replenish_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,select_odds as selectOdds,order_no as orderNo,attribute as attribute, odds2 as odds2,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from tb_replenish "
					+ " "
					+ " where  1=1  "
					+ whereSQL
					+ " )";
			countSQl = "select count(1) from  (" + sql + ") ";
		}
		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		paginationSQL.append(sql);
		paginationSQL.append("　) temp where ROWNUM <= "
				+ String.valueOf(endNum));
		paginationSQL.append(" ) WHERE　num > " + String.valueOf(startNum));
		// System.out.println(sql);
		Object[] parameters = new Object[] {};

		List list = jdbcTemplate.query(paginationSQL.toString(), parameters,
				new HKBetDetailItemMapper());

		int count = jdbcTemplate.queryForInt(countSQl);
		page.setTotalCount(count);
		page.setResult(list);
		return page;

	}

	/**
	 * 补货表查询条件 、
	 * 
	 * @param entry
	 * @param type
	 * @return
	 */
	private String getWhereSQLByBillSearchVoReplenish(BillSearchVo entry,
			String type) {
		String whereSQL = "";
		if ("HKLHC".equals(entry.getSubType())) {
			whereSQL += " and type_code like 'HK%'";
		} else if ("GDKLSF".equals(entry.getSubType())) {
			whereSQL += " and type_code like 'GD%'";
		} else if ("CQSSC".equals(entry.getSubType())) {
			whereSQL += " and type_code like 'CQ%'";
		} else if ("BJSC".equals(entry.getSubType())) {
			whereSQL += " and type_code like 'BJ%'";
		}
		String playType = entry.getPlayType();
		List<PlayType> playTypeList = playTypeDao.findBy("commissionType",
				playType);
		String typeCode = "";
		if (playTypeList != null) {
			for (int i = 0; i < playTypeList.size(); i++) {
				PlayType p = playTypeList.get(i);
				typeCode += (i == 0 ? "" : ",");
				typeCode += "'" + p.getTypeCode() + "'";
			}
		}
		if (!typeCode.equals(""))
			whereSQL += " and type_code in(" + typeCode + ") ";

		if (!"".equals(entry.getBettingDateEnd())
				&& !"".equals(entry.getBettingDateStart())) {
			whereSQL += " and betting_date between to_date('"
					+ entry.getBettingDateStart()
					+ "','yyyy-MM-dd')+to_dsinterval('0 2:00:00')  and to_date('"
					+ entry.getBettingDateEnd()
					+ "','yyyy-MM-dd')+1+to_dsinterval('0 2:00:00') ";
		}

		if (!"".equals(entry.getOrderNum())) {
			String periods = "";
			String[] str = entry.getOrderNum().split(",");
			if (str != null) {
				for (int i = 0; i < str.length; i++) {
					periods += (i == 0 ? "" : ",");
					periods += "'" + str[i] + "'";
				}
				whereSQL += " and order_no in(" + periods + ") ";
			}
		}

		// 補貨搜索 - 暫時無 此條件 -- 狀態 未結算 已結算也屏蔽掉
		// if(!"".equals(entry.getMemberID()) && null!=entry.getMemberID())
		// {
		// MemberStaff m =
		// memberStaffLogic.findMemberStaffByAccount(entry.getMemberID());
		// if(m!=null)
		// whereSQL += " and replenish_user_id = '"+m.getID()+"' ";
		// }
		// 注額範圍
		if (!"".equals(entry.getEduMax()) && null != entry.getEduMax()
				&& !"".equals(entry.getEduMin()) && null != entry.getEduMin()) {
			whereSQL += " and money >= " + entry.getEduMin()
					+ " and  money <= " + entry.getEduMax() + "";
		}

		if ("0".equals(entry.getLotteryType())) // 已结算 查询历史表
		{
			whereSQL += " and win_state in('1','2','3','4','5') "; // 2 已經開獎
																	// 4已結註銷
																	// 5已結停開
		} else if ("1".equals(entry.getLotteryType())) {
			whereSQL += " and win_state in('0','6','7') "; // 1 未開獎 6未結註銷 7未結停開
		}

		return whereSQL;
	}

	/**
	 * 补货表查询条件 、 只用于总监
	 * 
	 * @param entry
	 * @param type
	 * @return
	 */
	private String getWhereSQLByBillSearchVoReplenishAdmin(BillSearchVo entry,
			String type) {
		String whereSQL = "";
		if ("BJSC".equals(entry.getSubType())) {
			whereSQL += " and type_code like 'BJ%'";
		} else if ("GDKLSF".equals(entry.getSubType())) {
			whereSQL += " and type_code like 'GD%'";
		} else if ("CQSSC".equals(entry.getSubType())) {
			whereSQL += " and type_code like 'CQ%'";
		}
		String playType = entry.getPlayType();
		List<PlayType> playTypeList = playTypeDao.findBy("commissionType",
				playType);
		String typeCode = "";
		if (playTypeList != null) {
			for (int i = 0; i < playTypeList.size(); i++) {
				PlayType p = playTypeList.get(i);
				typeCode += (i == 0 ? "" : ",");
				typeCode += "'" + p.getTypeCode() + "'";
			}
		}
		if (!typeCode.equals(""))
			whereSQL += " and type_code in(" + typeCode + ") ";

		if (null != entry.getPeriodsNum() && !"".equals(entry.getPeriodsNum()))
			whereSQL += " and PERIODS_NUM = "
					+ entry.getPeriodsNum().split("_")[1] + " ";

		if (null != entry.getCreateTime() && !"".equals(entry.getCreateTime())) {
			whereSQL += " and betting_date between to_date('"
					+ entry.getCreateTime()
					+ "','yyyy-MM-dd')+to_dsinterval('0 2:00:00')  and to_date('"
					+ entry.getCreateTime()
					+ "','yyyy-MM-dd')+1+to_dsinterval('0 2:00:00') ";
		}

		if (!"".equals(entry.getOrderNum()) && entry.getOrderNum()!=null) {
			String periods = "";
			String[] str = entry.getOrderNum().split(",");
			if (str != null) {
				for (int i = 0; i < str.length; i++) {
					periods += (i == 0 ? "" : ",");
					periods += "'" + str[i] + "'";
				}
				whereSQL += " and order_no in(" + periods + ") ";
			}
		}

		// 注額範圍
		if (!"".equals(entry.getEduMax()) && null != entry.getEduMax()
				&& !"".equals(entry.getEduMin()) && null != entry.getEduMin()) {
			whereSQL += " and money >= " + entry.getEduMin()
					+ " and  money <= " + entry.getEduMax() + "";
		}

		if ("0".equals(entry.getLotteryType())) // 已结算 查询历史表
		{
			whereSQL += " and win_state in('1','2','3','4','5') "; // 2 已經開獎
																	// 4已結註銷
																	// 5已結停開
		} else if ("1".equals(entry.getLotteryType())) {
			whereSQL += " and win_state in('0','6','7') "; // 1 未開獎 6未結註銷 7未結停開
		}
		
		// add by peter
		if (null != entry.getMemberName() && !"".equals(entry.getMemberName())) {
			if ("1".equals(entry.getUserType())) { // 会员
				whereSQL += " and REPLENISH_USER_ID is null "; // 会员不可能补货，设置为null
			} else {// 非会员
				ManagerStaff m2 = managerStaffLogic.findManagerStaffByAccount(entry.getMemberName());
				if ("6".equals(entry.getUserType())) { // 代理

					if (m2 != null) {
						whereSQL += " and AGENTSTAFF = '" + m2.getID() + "' ";
					} else {
						whereSQL += " and AGENTSTAFF = '' ";
					}
				} else if ("5".equals(entry.getUserType())) { // 总代理
					if (m2 != null) {
						whereSQL += " and GENAGENSTAFF = '" + m2.getID() + "' ";
					} else {
						whereSQL += " and GENAGENSTAFF = '' ";
					}
				} else if ("4".equals(entry.getUserType())) { // 股东
					if (m2 != null) {
						whereSQL += " and STOCKHOLDERSTAFF = '" + m2.getID() + "' ";
					} else {
						whereSQL += " and STOCKHOLDERSTAFF = '' ";
					}
				} else if ("3".equals(entry.getUserType())) { // 分公司
					if (m2 != null) {
						whereSQL += " and BRANCHSTAFF = '" + m2.getID() + "' ";
					} else {
						whereSQL += " and BRANCHSTAFF = '' ";
					}

				}
				if (m2 != null) {
					whereSQL += " or REPLENISH_USER_ID = '" + m2.getID() + "' ";
				} else {
					whereSQL += " or REPLENISH_USER_ID = '' ";
				}
			}
		}

		whereSQL += " and chiefstaff='" + entry.getChiefID() + "' GROUP BY order_no) c WHERE 1=1 ";
		
		// 注額範圍
		if (!"".equals(entry.getEduMax()) && null != entry.getEduMax()
				&& !"".equals(entry.getEduMin()) && null != entry.getEduMin()) {
			whereSQL += " and money*count >= " + entry.getEduMin()
					+ " and  money*count <= " + entry.getEduMax() + "";
		}
		
		//
		if (!"".equals(entry.getResMax()) && null != entry.getResMax()
				&& !"".equals(entry.getResMin()) && null != entry.getResMin()) {
			whereSQL += " and -money * count + winAmount * count >= "
					+ entry.getResMin()
					+ " and -money * count + winAmount * count <= "
					+ entry.getResMax() + " ";
		}
		
		return whereSQL;
	}

	/**
	 * hk cq gd 通用查询条件
	 * 
	 * @param entry
	 * @param type
	 *            to_dsinterval('0 2:00:00') + 2個小時
	 * @return
	 */
	private String getWhereSQLByBillSearchVo(BillSearchVo entry, String type) {
		String playType = entry.getPlayType();
		List<PlayType> playTypeList = playTypeDao.findBy("commissionType",
				playType);
		String whereSQL = "";
		String typeCode = "";
		if (playTypeList != null) {
			for (int i = 0; i < playTypeList.size(); i++) {
				PlayType p = playTypeList.get(i);
				typeCode += (i == 0 ? "" : ",");
				typeCode += "'" + p.getTypeCode() + "'";
			}
		}
		if (!typeCode.equals(""))
			whereSQL += " and type_code in(" + typeCode + ") ";

		// add 當總監時根據 win_state 查詢條件
		if (null != entry.getWinState() && !"".equals(entry.getWinState()))
			whereSQL += " and win_state = " + entry.getWinState() + " ";

		if (!"".equals(entry.getBettingDateEnd())
				&& !"".equals(entry.getBettingDateStart())) {
			whereSQL += " and betting_date between to_date('"
					+ entry.getBettingDateStart()
					+ "','yyyy-MM-dd')+to_dsinterval('0 2:30:00')  and to_date('"
					+ entry.getBettingDateEnd()
					+ "','yyyy-MM-dd')+1 +to_dsinterval('0 2:30:00')";
		}

		if (!"".equals(entry.getOrderNum())) {
			String periods = "";
			String[] str = entry.getOrderNum().split(",");
			if (str != null) {
				for (int i = 0; i < str.length; i++) {
					periods += (i == 0 ? "" : ",");
					periods += "'" + str[i] + "'";
				}
				whereSQL += " and order_no in(" + periods + ") ";
			}
		}

		if (!"".equals(entry.getMemberID()) && null != entry.getMemberID()) {
			MemberStaff m = memberStaffLogic.findMemberStaffByAccount(entry
					.getMemberID());
			if (m != null)
				whereSQL += " and BETTING_USER_ID = '" + m.getID() + "' ";
		}
		// 注額範圍
		if (!"".equals(entry.getEduMax()) && null != entry.getEduMax()
				&& !"".equals(entry.getEduMin()) && null != entry.getEduMin()) {
			whereSQL += " and money >= " + entry.getEduMin()
					+ " and  money <= " + entry.getEduMax() + "";
		}

		// 結果範圍
		if (!"".equals(entry.getResMax()) && null != entry.getResMax()
				&& !"".equals(entry.getResMin()) && null != entry.getResMin()) {
			String countSQl = "select count(order_no) count from tb_hklhc_his where 1=1 "
					+ whereSQL + " group by order_no";

			// int count = jdbcTemplate.queryForInt(countSQl);
			// System.out.println(count+"  "+countSQl);
			// 根据 页面得出的 String
			// winMoney=nf.format(money.floatValue()*(odds.floatValue()-1)*count);
			// whereSQL +=
			// " and money*(odds-1)*"+count+" >= "+entry.getResMin()+" and  money*(odds-1)*"+count+" <= "+entry.getResMax()+"";
		}
		return whereSQL;
	}

	/**
	 * hk cq gd 通用查询条件
	 * 
	 * @param entry
	 * @param type
	 *            to_dsinterval('0 2:00:00') + 2個小時 20121116
	 * @return
	 */
	private String getWhereSQLByBillSearchVoAdmin(BillSearchVo entry,
			String type) {
		String playType = entry.getPlayType();
		List<PlayType> playTypeList = playTypeDao.findBy("commissionType",
				playType);
		String whereSQL = "";
		String typeCode = "";
		if (playTypeList != null) {
			for (int i = 0; i < playTypeList.size(); i++) {
				PlayType p = playTypeList.get(i);
				typeCode += (i == 0 ? "" : ",");
				typeCode += "'" + p.getTypeCode() + "'";
			}
		}
		if (!typeCode.equals(""))
			whereSQL += " and type_code in(" + typeCode + ") ";

		// win_state 查詢條件
		if (null != entry.getWinState() && !"".equals(entry.getWinState()))
			whereSQL += " and win_state = " + entry.getWinState() + " ";

		if (null != entry.getPeriodsNum() && !"".equals(entry.getPeriodsNum()))
			whereSQL += " and PERIODS_NUM = "
					+ entry.getPeriodsNum().split("_")[1] + " ";

		if (!"".equals(entry.getCreateTime())) {
			whereSQL += " and betting_date between to_date('"
					+ entry.getCreateTime()
					+ "','yyyy-MM-dd')+to_dsinterval('0 2:30:00')  and to_date('"
					+ entry.getCreateTime()
					+ "','yyyy-MM-dd')+1 +to_dsinterval('0 2:30:00')";
		}

		if (!"".equals(entry.getOrderNum()) && entry.getOrderNum()!=null) {
			String periods = "";
			String[] str = entry.getOrderNum().split(",");
			if (str != null) {
				for (int i = 0; i < str.length; i++) {
					periods += (i == 0 ? "" : ",");
					periods += "'" + str[i] + "'";
				}
				whereSQL += " and order_no in(" + periods + ") ";
			}
		}

		if (null != entry.getMemberName() && !"".equals(entry.getMemberName())) {
			if ("1".equals(entry.getUserType())) { // 会员

				MemberStaff m = memberStaffLogic.findMemberStaffByAccount(entry
						.getMemberName());
				if (m != null)
					whereSQL += " and BETTING_USER_ID = '" + m.getID() + "' ";
				else
					whereSQL += " and BETTING_USER_ID = '' ";
			} else if ("6".equals(entry.getUserType())) { // 代理
				ManagerStaff m2 = managerStaffLogic
						.findManagerStaffByAccount(entry.getMemberName());
				if (m2 != null)
					whereSQL += " and AGENTSTAFF = '" + m2.getID() + "' ";
				else
					whereSQL += " and AGENTSTAFF = '' ";
			} else if ("5".equals(entry.getUserType())) { // 总代理
				ManagerStaff m2 = managerStaffLogic
						.findManagerStaffByAccount(entry.getMemberName());
				if (m2 != null)
					whereSQL += " and GENAGENSTAFF = '" + m2.getID() + "' ";
				else
					whereSQL += " and GENAGENSTAFF = '' ";
			} else if ("4".equals(entry.getUserType())) { // 股东
				ManagerStaff m2 = managerStaffLogic
						.findManagerStaffByAccount(entry.getMemberName());
				if (m2 != null)
					whereSQL += " and STOCKHOLDERSTAFF = '" + m2.getID() + "' ";
				else
					whereSQL += " and STOCKHOLDERSTAFF = '' ";
			} else if ("3".equals(entry.getUserType())) { // 分公司
				ManagerStaff m2 = managerStaffLogic
						.findManagerStaffByAccount(entry.getMemberName());
				if (m2 != null)
					whereSQL += " and BRANCHSTAFF = '" + m2.getID() + "' ";
				else
					whereSQL += " and BRANCHSTAFF = '' ";
			}
		}
		

		whereSQL += " and chiefstaff='" + entry.getChiefID() + "' GROUP BY order_no) c " +
				" WHERE 1=1 ";
		// 注額範圍
		if (!"".equals(entry.getEduMax()) && null != entry.getEduMax()
				&& !"".equals(entry.getEduMin()) && null != entry.getEduMin()) {
			whereSQL += " and money*count >= " + entry.getEduMin()
					+ " and  money*count <= " + entry.getEduMax() + "";
		}
				
		//结果范围
		if (!"".equals(entry.getResMax()) && null != entry.getResMax()
				&& !"".equals(entry.getResMin()) && null != entry.getResMin()) {
			whereSQL += " and -money * count + winAmount * count >= "
					+ entry.getResMin()
					+ " and -money * count + winAmount * count <= "
					+ entry.getResMax() + " ";
		}
				
		return whereSQL;
	}

	/**
	 * 查詢HKLHC注單搜索- 历史表已结算的 add by Aaron entry 搜索條件
	 */
	@Override
	public Page queryHKLHCBetByObjHis(Page page, BillSearchVo entry) {
		String whereSQL = this.getWhereSQLByBillSearchVo(entry, "HK");

		int startNum = page.getPageSize() * (page.getPageNo() - 1);
		int endNum = page.getPageSize() * (page.getPageNo());
		String sql = "";
		String countSQl = "";
		// 結果範圍 b.money*(b.odds-1)*count 要与页面公式一样 String
		// winMoney=nf.format(money.floatValue()*(odds.floatValue()-1)*count);
		if (!"".equals(entry.getResMax()) && null != entry.getResMax()
				&& !"".equals(entry.getResMin()) && null != entry.getResMin()) {
			sql = " select * from (select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,select_odds as selectOdds,order_no as orderNo,attribute as attribute, odds2 as odds2,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from tb_hklhc_his "
					+ " a "
					+ " where  1=1  "
					+ whereSQL
					+ " ) b where rankNum=1 and b.money*(b.odds-1)*count >= "
					+ entry.getResMin()
					+ " and b.money*(b.odds-1)*count <= "
					+ entry.getResMax() + "";
			countSQl = "select count(1) from (" + sql + ") ";

		} else {
			sql = " select * from (select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,select_odds as selectOdds,order_no as orderNo,attribute as attribute, odds2 as odds2,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from tb_hklhc_his "
					+ " a "
					+ " where  1=1  "
					+ whereSQL + " ) where rankNum=1 ";
			countSQl = "select count(1) from (" + sql + ") ";
		}

		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		paginationSQL.append(sql);
		paginationSQL.append("　) temp where ROWNUM <= "
				+ String.valueOf(endNum));
		paginationSQL.append(" ) WHERE　num > " + String.valueOf(startNum));

		Object[] parameters = new Object[] {};
		List betlist = jdbcTemplate.query(paginationSQL.toString(), parameters,
				new HKBetDetailItemMapper());

		int count = jdbcTemplate.queryForInt(countSQl);

		page.setTotalCount(count);
		page.setResult(betlist);
		return page;
	}

	public Page queryHKLHCUserBetDetail(Page page, Long userId) {

		List<BaseBet> retList = new ArrayList<BaseBet>();
		for (int i = 0; i < Constant.HK_TABLE_LIST.length; i++) {
			String tableName = Constant.HK_TABLE_LIST[i];
			String attribute = "null";
			if (tableName.equals(Constant.HK_LM_TABLE_NAME)
					|| tableName.equals(Constant.HK_LX_TABLE_NAME)
					|| tableName.equals(Constant.HK_SXL_TABLE_NAME)
					|| tableName.equals(Constant.HK_WSL_TABLE_NAME)
					|| tableName.equals(Constant.HK_WBZ_TABLE_NAME)
					|| tableName.equals(Constant.HK_GG_TABLE_NAME)

			)
				attribute = "attribute";

			String odd2 = "null";
			if (tableName.equals(Constant.HK_LM_TABLE_NAME))
				odd2 = "odds2";

			String selectedOdd = "null";
			if (tableName.equals(Constant.HK_SXL_TABLE_NAME)
					|| tableName.equals(Constant.HK_WSL_TABLE_NAME)
					|| tableName.equals(Constant.HK_WBZ_TABLE_NAME)
					|| tableName.equals(Constant.HK_GG_TABLE_NAME)

			)
				selectedOdd = "select_odds";

			String sql = " select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,"
					+ selectedOdd
					+ " as selectOdds,order_no as orderNo,"
					+ attribute
					+ " as attribute, "
					+ odd2
					+ " as odds2,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from "
					+ tableName
					+ " a"
					+ " where betting_user_id= ? ";
			Object[] parameters = new Object[] { userId };
			sql = "select * from (" + sql + ") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters,
					new HKBetDetailItemMapper());

			retList.addAll(betlist);
		}
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		Collections.sort(retList);
		page.setTotalCount(retList.size());
		page.setResult(retList.subList(first, last));
		return page;

	}

	/*
	 * public Page queryAllUserBetDetail(Page page, Long userId,Date date){
	 * List<BaseBet> retList = new ArrayList<BaseBet>(); SimpleDateFormat format
	 * = new SimpleDateFormat("yyyy-MM-dd"); format.format(date); for (int i =
	 * 0; i < Constant.BALANCE_TABLE_LIST.length; i++) { String tableName =
	 * Constant.BALANCE_TABLE_LIST[i]; String sql =
	 * " select a.type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,b.sub_type_name as subTypeName,b.final_type_name as finalTypeName,b.type_code as typeCode,b.play_sub_type as playSubType,null as attribute ,win_state as winState,null as compoundNum"
	 * + " from " + tableName + " a, tb_play_type b" +
	 * " where betting_user_id= ? and a.type_code=b.type_code and "; Object[]
	 * parameters = new Object[] { userId }; List betlist =
	 * jdbcTemplate.query(sql, parameters, new BetItemMapper());
	 * 
	 * retList.addAll(betlist); } int first = page.getFirst() - 1; int last =
	 * first + page.getPageSize(); if (last > retList.size()) last =
	 * retList.size(); Collections.sort(retList);
	 * page.setTotalCount(retList.size()); page.setResult(retList.subList(first,
	 * last)); return page; }
	 */

	public List queryHKPlayMoneyByTypeCode(String typeCode, String shopCode) {
		List list = new ArrayList();
		// String
		// sql="SELECT TYPE_CODE as TYPECODE,SUM(MONEY) as TOTALMONEY FROM "+tableName+" GROUP BY TYPE_CODE";
		String sql = "SELECT ID ENTRYID,TYPE_CODE AS TYPECODE,PLAY_TYPE PLAYTYPE,PERIODS_NUM PERIODSNUM,MONEY_AMOUNT MONEYAMOUNT,UPDATE_TIME UPDATETIME FROM TB_PLAY_AMOUNT T WHERE shops_code='"
				+ shopCode
				+ "' and TYPE_CODE LIKE '"
				+ typeCode
				+ "'   ESCAPE '\\' ";
		// Object[] parameters=new Object[]{};
		list = jdbcTemplate.queryForList(sql);

		return list;
	}

	// public List queryHKLMMoneyTotal(String typeCode)
	// {
	// // List list = new ArrayList();
	// String
	// sql=" SELECT TYPE_CODE AS TYPECODE,MONEY AS MONEY,ATTRIBUTE AS ATTRIBUTE FROM TB_HKLHC_LM WHERE TYPE_CODE='"+typeCode+"'";
	// List list = jdbcTemplate.queryForList(sql);
	//
	// return list;
	// }
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Integer queryTotalBySubType(String userIdList,
			String queryTableName, String plate, String typeCode) {
		return null;
	}

	public List getReplenishDate(String periodNum, String playType,
			Integer startNum, Integer endNum ,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======getReplenishDate开始执行SCHEME为:"+scheme+"==============");
		
		String replenishSql = "select id,order_no orderNo,type_code as typeCode,money as money,periods_num,win_state as winState,odds as odds,attribute as attribute ,odds2 as odds2,select_odds as selectedOdds from "+
		scheme+"tb_replenish where type_code like '"
				+ playType + "%' AND PERIODS_NUM=? ";

		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		paginationSQL.append(replenishSql);
		paginationSQL.append("　) temp where ROWNUM <= "
				+ String.valueOf(endNum));
		paginationSQL.append(" ) WHERE　num > " + String.valueOf(startNum));
		Object[] parameters = new Object[] { periodNum };
		List betlist = jdbcTemplate.query(paginationSQL.toString(), parameters,
				new ReplenshResultItemMapper());

		return betlist;

	}

	public void updateReplenshNotWinBetResult(String periodNum, String playType,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======updateReplenshNotWinBetResult开始执行SCHEME为:"+scheme+"==============");
		
		String sql = "update "+scheme+"tb_replenish set win_state=2 ,win_amount=0 where type_code like '"
				+ playType + "%' and win_state=0 and periods_num=? ";
		Object[] parameters = new Object[] { periodNum };
		jdbcTemplate.update(sql, parameters);

	}

	public void updateReplenshWinBetResult(Integer id, String winState,
			String winAmount) {
		String sql = "update tb_replenish set win_state=? ,win_amount=?, update_date=sysdate where order_no=? ";
		Object[] parameters = new Object[] { winState, winAmount, id };
		jdbcTemplate.update(sql, parameters);

	}

	public void updateHistoryBetResultToUnLottery(String tableName,
			String periodNum,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======updateHistoryBetResultToUnLottery开始执行SCHEME为:"+scheme+"==============");
		String sql = "update "
				+scheme
				+ tableName
				+ " set win_state=0 ,win_amount=0, update_date=sysdate where PERIODS_NUM=? ";
		Object[] parameters = new Object[] { periodNum };
		jdbcTemplate.update(sql, parameters);

	}

	public List queryBetResult(String tableName, String periodNum,
			Integer startNum, Integer endNum,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======queryBetResult开始执行SCHEME为:"+scheme+"==============");
		
		String compoundNum = "null";
		if (tableName.indexOf("HKLHC") != -1
				|| tableName.indexOf("GDKLSF") != -1) {
			compoundNum = "compound_num";

		}
		String odd2 = "null";
		String selectOdds = "null";
		if (tableName.equals(Constant.HK_LM_TABLE_NAME)
				|| tableName.equals(Constant.HK_GG_TABLE_NAME))
			odd2 = "odds2";
		if (tableName.equals(Constant.HK_GG_TABLE_NAME))
			selectOdds = "select_odds";
		String attribute = "attribute";
		if (Lists.newArrayList(Constant.GDKLSF_LOTTERY_TABLE_LIST).contains(
				tableName)
				|| Lists.newArrayList(Constant.CQSSC_LOTTERY_TABLE_LIST)
						.contains(tableName)
				|| Lists.newArrayList(Constant.HK_LOTTERY_TABLE_LIST).contains(
						tableName)
				|| Constant.CQSSC_HIS_TABLE_NAME.equals(tableName)) {
			attribute = "null";

		}
		String splitAttribute = "null";
		if (Lists.newArrayList(Constant.FS_TABLE_LIST).contains(tableName)) {
			splitAttribute = "split_attribute";
		}

		String betSql = "select id,betting_user_id as bettingUserId,type_code as typeCode,"
				+ attribute
				+ " as attribute,order_no as orderNo,win_state as winState,money as money,odds as odds, "
				+ compoundNum
				+ " as compoundNum,"
				+ "chiefstaff as chiefstaff,"
				+ "branchstaff as branchstaff,"
				+ "stockholderstaff as stockholderstaff,"
				+ "genagenstaff as genagenstaff,"
				+ "agentstaff as agentstaff, "
				+ odd2
				+ " as odds2, "
				+ splitAttribute
				+ " as splitAttribute, "
				+ selectOdds
				+ " as selectOdds "
				+ " from "
				+ scheme
				+ tableName
				+ " where periods_num=? and win_state=0";

		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		paginationSQL.append(betSql);
		paginationSQL.append("　) temp where ROWNUM <= "
				+ String.valueOf(endNum));
		paginationSQL.append(" ) WHERE　num > " + String.valueOf(startNum));
		Object[] parameters = new Object[] { periodNum };
		List betlist = jdbcTemplate.query(paginationSQL.toString(), parameters,
				new LotteryResultItemMapper());

		return betlist;

	}

	/**
	 * 获取农场复试的投注信息
	 * @param tableName
	 * @param periodNum
	 * @param startNum
	 * @param endNum
	 * @return
	 */
	@Override
	public List queryBetResultForNC(String tableName, String periodNum, Integer startNum, Integer endNum,String scheme) {

		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		log.info("=======queryBetResultForNC开始执行SCHEME为:"+scheme+"==============");
		String compoundNum = "compound_num";
		String odd2 = "null";
		String selectOdds = "null";
		String attribute = "attribute";
		String splitAttribute = "split_attribute";

		String betSql = "select id,betting_user_id as bettingUserId,type_code as typeCode," + attribute + " as attribute,order_no as orderNo,win_state as winState,money as money,odds as odds, "
				+ compoundNum + " as compoundNum," + "chiefstaff as chiefstaff," + "branchstaff as branchstaff," + "stockholderstaff as stockholderstaff," + "genagenstaff as genagenstaff,"
				+ "agentstaff as agentstaff, " + odd2 + " as odds2, " + splitAttribute + " as splitAttribute, " + selectOdds + " as selectOdds " + " from " + scheme + tableName
				+ " where periods_num=? and win_state=0 and type_code like '%STRAIGHTTHROUGH%'";

		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		paginationSQL.append(betSql);
		paginationSQL.append("　) temp where ROWNUM <= " + String.valueOf(endNum));
		paginationSQL.append(" ) WHERE　num > " + String.valueOf(startNum));
		Object[] parameters = new Object[] { periodNum };
		List betlist = jdbcTemplate.query(paginationSQL.toString(), parameters, new LotteryResultItemMapper());

		return betlist;

	}

	public List queryHistoryBetResult(String tableName, String periodNum,
			String orgTableName, Integer startNum, Integer endNum) {

		String compoundNum = "null";
		if (tableName.indexOf("HKLHC") != -1
				|| tableName.indexOf("GDKLSF") != -1) {
			compoundNum = "compound_num";

		}
		String odd2 = "null";
		String selectOdds = "null";
		if (tableName.equals(Constant.HKLHC_HIS_TABLE_NAME))
			odd2 = "odds2";
		if (tableName.equals(Constant.HKLHC_HIS_TABLE_NAME))
			selectOdds = "select_odds";
		String attribute = "attribute";
		if (Constant.CQSSC_HIS_TABLE_NAME.equals(tableName)) {
			attribute = "null";

		}
		String splitAttribute = "split_attribute";
		if (Constant.CQSSC_HIS_TABLE_NAME.equals(tableName)) {
			splitAttribute = "null";
		}

		String betSql = "select id,betting_user_id as bettingUserId,type_code as typeCode,"
				+ attribute
				+ " as attribute,order_no as orderNo,win_state as winState,money as money,odds as odds, "
				+ compoundNum
				+ " as compoundNum,"
				+ "chiefstaff as chiefstaff,"
				+ "branchstaff as branchstaff,"
				+ "stockholderstaff as stockholderstaff,"
				+ "genagenstaff as genagenstaff,"
				+ "agentstaff as agentstaff, "
				+ odd2
				+ " as odds2, "
				+ splitAttribute
				+ " as splitAttribute, "
				+ selectOdds
				+ " as selectOdds "
				+ " from "
				+ tableName
				+ " where periods_num=? and origin_tb_name=? ";

		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		paginationSQL.append(betSql);
		paginationSQL.append("　) temp where ROWNUM <= "
				+ String.valueOf(endNum));
		paginationSQL.append(" ) WHERE　num > " + String.valueOf(startNum));
		Object[] parameters = new Object[] { periodNum, orgTableName };
		List betlist = jdbcTemplate.query(paginationSQL.toString(), parameters,
				new LotteryResultItemMapper());

		return betlist;

	}

	/*
	 * public List queryWinBetResult(String TableName,String periodNum,Integer
	 * startNum,Integer endNum) {
	 * 
	 * String betSql=
	 * "select betting_user_id as bettingUserId,type_code as typeCode,order_no as orderNo,win_state as winState,money as money, "
	 * + "chiefstaff as chiefstaff," + "branchstaff as branchstaff," +
	 * "stockholderstaff as stockholderstaff," + "genagenstaff as genagenstaff,"
	 * + "agentstaff as agentstaff" +
	 * " from "+TableName+" where periods_num=? and win_State='1'";
	 * 
	 * 
	 * StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
	 * paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
	 * paginationSQL.append(betSql);
	 * paginationSQL.append("　) temp where ROWNUM <= " +
	 * String.valueOf(endNum)); paginationSQL.append(" ) WHERE　num > " +
	 * String.valueOf(startNum)); Object[] parameters = new Object[] { periodNum
	 * }; List betlist = jdbcTemplate.query(paginationSQL.toString(),
	 * parameters, new LotteryResultItemMapper());
	 * 
	 * 
	 * return betlist;
	 * 
	 * }
	 */

	/*
	 * public void updateUserCommison(String TableName,String commission,Long
	 * userId,String typeCode) { String sql="update "+TableName+
	 * " set commission=? , update_date=sysdate where betting_user_id=? and TYPE_CODE=?"
	 * ; Object[] parameters = new Object[] { commission,userId,typeCode };
	 * jdbcTemplate.update(sql,parameters);
	 * 
	 * 
	 * }
	 */
	// 更新复式投注中奖信息
	public void updateFSBetResult(String TableName, Integer recordID,
			String win, String winAmount, String winCount,String scheme) {

		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======updateFSBetResult开始执行SCHEME为:"+scheme+"==============");
		
		String sql = "update "
				+ scheme
				+ TableName
				+ " set win_state=? ,win_amount=?, compound_num=? ,update_date=sysdate where id=?";
		Object[] parameters = new Object[] { win, winAmount, winCount, recordID };
		jdbcTemplate.update(sql, parameters);

	}

	// 更新复式投注中奖信息
	public void updateGGBetResult(String TableName, Integer recordID,
			String win, String winAmount, String winCount, String odds) {

		String sql = "update "
				+ TableName
				+ " set win_state=?,odds=? ,win_amount=?, compound_num=? ,update_date=sysdate where id=?";
		Object[] parameters = new Object[] { win, odds, winAmount, winCount,
				recordID };
		jdbcTemplate.update(sql, parameters);

	}

	// 批量更新未中奖信息
	public void updateNotWinBetResult(String TableName, String periodNum,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======updateNotWinBetResult开始执行SCHEME为:"+scheme+"==============");
		String sql = "update "
				+ scheme
				+ TableName
				+ " set win_state=2 ,win_amount=0 where win_state=0 and periods_num=? ";
		Object[] parameters = new Object[] { periodNum };
		jdbcTemplate.update(sql, parameters);

	}
	// 批量更新未中奖信息
	@Override
	public void updateNotWinBetResultForNC(String TableName, String periodNum,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======updateNotWinBetResultForNC开始执行SCHEME为:"+scheme+"==============");
		
		String sql = "update "
				+ scheme
				+ TableName
				+ " set win_state=2 ,win_amount=0 where win_state=0 and periods_num=? and type_code like '%STRAIGHTTHROUGH%'";
		Object[] parameters = new Object[] { periodNum };
		jdbcTemplate.update(sql, parameters);
		
	}

	// 更新香港打和的记录
	public void updateHKHeResult(String periodsNum, List<Integer> winNums,
			boolean history) {
		Object[] parameters = new Object[] { periodsNum };

		String subType = null;
		if (winNums.get(0).intValue() == 49) {
			subType = "ZM1";
		} else if (winNums.get(1).intValue() == 49) {
			subType = "ZM2";
		} else if (winNums.get(2).intValue() == 49) {
			subType = "ZM3";
		} else if (winNums.get(3).intValue() == 49) {
			subType = "ZM4";
		} else if (winNums.get(4).intValue() == 49) {
			subType = "ZM5";
		} else if (winNums.get(5).intValue() == 49) {
			subType = "ZM6";
		}
		if (!history) {
			if (winNums.get(6).intValue() == 49) {
				String temaHeSql = "UPDATE TB_HKLHC_TE_MA A SET A.WIN_STATE=9,a.win_amount=money WHERE exists (select 1 from tb_play_type b where play_type='HKLHC' and play_sub_type='TM' AND PLAY_FINAL_TYPE IN('DA','X','DAN','S' ,'HSD','HSS','TWD','TWX')  and a.type_code=b.type_code) AND A.PERIODS_NUM=?";
				jdbcTemplate.update(temaHeSql, parameters);
			}

			if (subType != null) {
				String zm16Sql = "UPDATE tb_hklhc_zm16 A SET A.WIN_STATE=9,a.win_amount=money WHERE exists (select 1 from tb_play_type b where play_type='HKLHC' AND PLAY_FINAL_TYPE IN('DA','X','DAN','S' ,'HSD','HSS','TWD','TWX')  and a.type_code=b.type_code and b.play_sub_type='"
						+ subType + "') AND A.PERIODS_NUM=?";

				jdbcTemplate.update(zm16Sql, parameters);
			}
		} else {
			if (winNums.get(6).intValue() == 49) {
				String temaHeSql = "UPDATE TB_HKLHC_HIS A SET A.WIN_STATE=9,a.win_amount=money WHERE exists (select 1 from tb_play_type b where play_type='HKLHC' and play_sub_type='TM' AND PLAY_FINAL_TYPE IN('DA','X','DAN','S' ,'HSD','HSS','TWD','TWX')  and a.type_code=b.type_code) AND A.PERIODS_NUM=? AND origin_tb_name='TB_HKLHC_TE_MA'";
				jdbcTemplate.update(temaHeSql, parameters);
			}
			if (subType != null) {
				String zm16Sql = "UPDATE TB_HKLHC_HIS A SET A.WIN_STATE=9,a.win_amount=money WHERE exists (select 1 from tb_play_type b where play_type='HKLHC' AND PLAY_FINAL_TYPE IN('DA','X','DAN','S' ,'HSD','HSS','TWD','TWX')  and a.type_code=b.type_code and b.play_sub_type='"
						+ subType
						+ "') AND A.PERIODS_NUM=? AND origin_tb_name='TB_HKLHC_ZM16'";
				jdbcTemplate.update(zm16Sql, parameters);
			}

		}
	}

	// 更新重庆打和的记录
	public void updateCQHeResult(String periodsNum, List<String> tableNames,String scheme) {

		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======updateCQHeResult开始执行SCHEME为:"+scheme+"==============");
		
		for (int i = 0; i < tableNames.size(); i++) {
			String tableName = tableNames.get(i);

			String heSql = "UPDATE "
					+scheme
					+ tableName
					+ " A SET A.WIN_STATE=9,a.win_amount=money WHERE exists (select 1 from tb_play_type b where play_type='CQSSC' AND PLAY_FINAL_TYPE IN('LONG','HU')  and a.type_code=b.type_code) AND A.PERIODS_NUM=? ";
			Object[] parameters = new Object[] { periodsNum };
			jdbcTemplate.update(heSql, parameters);
		}
	}

	// 更新广东打和的记录
	public void updateGDHeResult(String periodsNum, List<String> tableNames,String scheme) {

		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======updateGDHeResult开始执行SCHEME为:"+scheme+"==============");
		
		for (int i = 0; i < tableNames.size(); i++) {
			String tableName = tableNames.get(i);

			String heSql = "UPDATE "
					+scheme
					+ tableName
					+ " A SET A.WIN_STATE=9,a.win_amount=money WHERE exists (select 1 from tb_play_type b where play_type='GDKLSF' AND PLAY_FINAL_TYPE IN('ZHDA','ZHX')  and a.type_code=b.type_code) AND A.PERIODS_NUM=? ";
			Object[] parameters = new Object[] { periodsNum };
			jdbcTemplate.update(heSql, parameters);
		}
	}
	// 更新农场打和的记录
	@Override
	public void updateNCHeResult(String periodsNum, List<String> tableNames,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======开始执行SCHEME为:"+scheme+"==============");
		
		for (int i = 0; i < tableNames.size(); i++) {
			String tableName = tableNames.get(i);
			
			String heSql = "UPDATE "
					+ scheme
					+ tableName
					+ " A SET A.WIN_STATE=9,a.win_amount=money WHERE exists (select 1 from tb_play_type b where play_type='NC' AND PLAY_FINAL_TYPE IN('ZHDA','ZHX')  and a.type_code=b.type_code) AND A.PERIODS_NUM=? ";
			Object[] parameters = new Object[] { periodsNum };
			jdbcTemplate.update(heSql, parameters);
		}
	}

	public void updateBetResult(String TableName, String winNum,
			String orderNo, String winAmount) {

		String sql = "update "
				+ TableName
				+ " set win_state=? ,win_amount=?, update_date=sysdate where order_no=?";
		Object[] parameters = new Object[] { winNum, winAmount, orderNo };
		jdbcTemplate.update(sql, parameters);

	}

	public void updateCQHistoryBetResult(String winState, String id,
			String winAmount) {
		String sql = "update tb_cqssc_his set win_state=? ,win_amount=?, update_date=sysdate where id=?";
		Object[] parameters = new Object[] { winState, winAmount, id };
		jdbcTemplate.update(sql, parameters);
	}

	public void updateGDHistoryBetResult(String winState, String id,
			String winAmount) {
		String sql = "update tb_gdklsf_his set win_state=? ,win_amount=?, update_date=sysdate where id=?";
		Object[] parameters = new Object[] { winState, winAmount, id };
		jdbcTemplate.update(sql, parameters);

	}

	public void updateHKHistoryBetResult(String winState, String id,
			String winAmount) {
		String sql = "update tb_hklhc_his set win_state=? ,win_amount=?, update_date=sysdate where id=?";
		Object[] parameters = new Object[] { winState, winAmount, id };
		jdbcTemplate.update(sql, parameters);

	}

	public void updateHKHisGGResult(String winState, String id,
			String winAmount, String realOdds) {
		String sql = "update tb_hklhc_his set win_state=? ,win_amount=?,odds=?,odds2=?, update_date=sysdate where id=?";
		Object[] parameters = new Object[] { winState, winAmount, realOdds,
				realOdds, id };
		jdbcTemplate.update(sql, parameters);

	}

	public void updateReplenshGGResult(String winState, String id,
			String winAmount, String realOdds) {
		String sql = "update tb_replenish set win_state=? ,win_amount=?,odds=?,odds2=?, update_date=sysdate where id=?";
		Object[] parameters = new Object[] { winState, winAmount, realOdds,
				realOdds, id };
		jdbcTemplate.update(sql, parameters);

	}

	public void updateReplenshBetResult(String winState, String id,
			String winAmount,String scheme) {
		
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		log.info("=======updateReplenshBetResult开始执行SCHEME为:"+scheme+"==============");
		String sql = "update "+scheme+"tb_replenish set win_state=? ,win_amount=?, update_date=sysdate where id=?";
		Object[] parameters = new Object[] { winState, winAmount, id };
		jdbcTemplate.update(sql, parameters);

	}

	public void updateReLotteryBetResult(Integer id, String winState,
			String winAmount, String orgState, String tableName, String playType) {

		String sql = "update "
				+ tableName
				+ " set win_state=? ,win_amount=?, update_date=sysdate where id=? ";

		Object[] parameters = new Object[] { winState, winAmount, id };
		jdbcTemplate.update(sql, parameters);
		if ("0".equals(orgState)) {
			if ("CQSSC".equals(playType)) {
				miragationCQReLotteryDataToHistory(id, tableName);
			} else if ("GDKLSF".equals(playType)) {
				miragationGDReLotteryDataToHistory(id, tableName);
			} else if ("HKLHC".equals(playType)) {
				miragationHKRelotteryDataToHistory(id, tableName);
			}

		}

	}

	public void miragationCQReLotteryDataToHistory(Integer id, String tableName) {

		String winStateAttribute = "win_state";

		String sql = "insert into tb_cqssc_his "
				+ "(id,create_date,origin_tb_name,origin_id,order_no,type_code,money,plate,"
				+ "betting_user_id,chiefstaff,branchstaff,stockholderstaff,genagenstaff,agentstaff,"
				+ "commission_branch,commission_gen_agent,commission_stockholder,commission_agent,commission_member,"
				+ "rate_chief,rate_branch,rate_gen_agent,rate_stockholder,rate_agent,"
				+ "periods_num,betting_date,win_state,win_amount,odds,update_date,commission_type)"
				+ "(select seq_tb_cqssc_his.nextval,SYSDATE,'"
				+ tableName
				+ "',c.id,c.order_no,c.type_code,c.money,plate,"
				+ "c.betting_user_id,c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.genagenstaff,"
				+ "c.commission_branch,c.commission_gen_agent,c.commission_stockholder,c.commission_agent,c.commission_member,"
				+ "c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent,"
				+ "c.periods_num,c.betting_date," + winStateAttribute
				+ ",c.win_amount,c.odds,sysdate,commission_type from " + tableName
				+ " c where c.id=?  )";
		Object[] parameter = new Object[] { id };
		jdbcTemplate.update(sql, parameter);
		String deleteSql = "delete " + tableName + " where id=?  ";
		jdbcTemplate.update(deleteSql, parameter);

	}

	public void miragationGDReLotteryDataToHistory(Integer id, String tableName) {
		String winStateAttribute = "win_state";

		String attribute = "null";
		String splitAttr = "null";
		if (tableName.equals(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME)) {
			attribute = "attribute";
			splitAttr = "split_attribute";
		}

		String insertSql = "insert into tb_gdklsf_his "
				+ "(id,create_date,origin_tb_name,origin_id,order_no,type_code,money,plate,"
				+ "betting_user_id,chiefstaff,branchstaff,stockholderstaff,genagenstaff,agentstaff,"
				+ "commission_branch,commission_gen_agent,commission_stockholder,commission_agent,commission_member,"
				+ "rate_chief,rate_branch,rate_gen_agent,rate_stockholder,rate_agent,"
				+ "attribute,split_attribute,compound_num,periods_num,betting_date,win_state,win_amount,odds,update_date,commission_type)"
				+ "(select seq_tb_gdklsf_his.nextval,SYSDATE,'"
				+ tableName
				+ "',c.id,c.order_no,c.type_code,c.money,plate,"
				+ "c.betting_user_id,c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.genagenstaff,"
				+ "c.commission_branch,c.commission_gen_agent,c.commission_stockholder,c.commission_agent,c.commission_member,"
				+ "c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent,"
				+ attribute + " as attribute ," + splitAttr
				+ ",c.compound_num,c.periods_num,c.betting_date,"
				+ winStateAttribute + ",c.win_amount,c.odds,sysdate,commission_type from "
				+ tableName + " c where c.id=?)";
		Object[] parameter = new Object[] { id };
		jdbcTemplate.update(insertSql, parameter);
		String deleteSql = "delete " + tableName + " where id=? ";
		jdbcTemplate.update(deleteSql, parameter);

	}

	/**
	 * 注销 单后将 投注表数据移至历史表
	 * 
	 * @param id
	 * @param tableName
	 */
	public void miragationBJReLotteryDataToHistory(Integer id, String tableName) {
		String winStateAttribute = "win_state";

		String insertSql = "insert into TB_BJSC_HIS "
				+ "(id,create_date,origin_tb_name,origin_id,order_no,type_code,money,plate,"
				+ "betting_user_id,chiefstaff,branchstaff,stockholderstaff,genagenstaff,agentstaff,"
				+ "commission_branch,commission_gen_agent,commission_stockholder,commission_agent,commission_member,"
				+ "rate_chief,rate_branch,rate_gen_agent,rate_stockholder,rate_agent,"
				+ " periods_num,betting_date,win_state,win_amount,odds,update_date,commission_type)"
				+ "(select seq_tb_bjsc_his.nextval,SYSDATE,'"
				+ tableName
				+ "',c.id,c.order_no,c.type_code,c.money,plate,"
				+ "c.betting_user_id,c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.genagenstaff,"
				+ "c.commission_branch,c.commission_gen_agent,c.commission_stockholder,c.commission_agent,c.commission_member,"
				+ "c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent,"
				+ " c.periods_num,c.betting_date," + winStateAttribute
				+ ",c.win_amount,c.odds,sysdate,commission_type from " + tableName
				+ " c where c.id=?)";
		Object[] parameter = new Object[] { id };
		jdbcTemplate.update(insertSql, parameter);
		String deleteSql = "delete " + tableName + " where id=? ";
		jdbcTemplate.update(deleteSql, parameter);

	}

	public void miragationHKRelotteryDataToHistory(Integer id, String tableName) {
		String winStateAttribute = "win_state";

		String attribute = "null";
		String splitAttr = "null";
		String odds2 = "null";
		String selectedOdds = null;
		if (tableName.equals(Constant.HK_LM_TABLE_NAME)
				|| tableName.equals(Constant.HK_LX_TABLE_NAME)
				|| tableName.equals(Constant.HK_SXL_TABLE_NAME)
				|| tableName.equals(Constant.HK_WSL_TABLE_NAME)
				|| tableName.equals(Constant.HK_WBZ_TABLE_NAME)
				|| tableName.equals(Constant.HK_GG_TABLE_NAME)

		) {
			attribute = "attribute";
		}

		if (tableName.equals(Constant.HK_LM_TABLE_NAME)
				|| tableName.equals(Constant.HK_SXL_TABLE_NAME)
				|| tableName.equals(Constant.HK_WSL_TABLE_NAME)
				|| tableName.equals(Constant.HK_WBZ_TABLE_NAME)) {

			splitAttr = "split_attribute";
		}
		if (Constant.HK_LM_TABLE_NAME.equals(tableName)
				|| Constant.HK_GG_TABLE_NAME.equals(tableName)) {
			odds2 = "odds2";

		}

		if (tableName.equals(Constant.HK_SXL_TABLE_NAME)
				|| tableName.equals(Constant.HK_WSL_TABLE_NAME)
				|| tableName.equals(Constant.HK_WBZ_TABLE_NAME)
				|| Constant.HK_GG_TABLE_NAME.equals(tableName)) {

			selectedOdds = "select_odds";
		}
		String insertSql = "insert into tb_hklhc_his "
				+ "(id,create_date,origin_tb_name,origin_id,order_no,type_code,money,plate,"
				+ "betting_user_id,chiefstaff,branchstaff,stockholderstaff,genagenstaff,agentstaff,"
				+ "commission_branch,commission_gen_agent,commission_stockholder,commission_agent,commission_member,"
				+ "rate_chief,rate_branch,rate_gen_agent,rate_stockholder,rate_agent,"
				+ "attribute,split_attribute,compound_num,periods_num,betting_date,win_state,win_amount,odds,update_date,odds2,select_odds)"
				+ "(select seq_tb_hklhc_his.nextval,SYSDATE,'"
				+ tableName
				+ "',c.id,c.order_no,c.type_code,c.money,plate,"
				+ "c.betting_user_id,c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.genagenstaff,"
				+ "c.commission_branch,c.commission_gen_agent,c.commission_stockholder,c.commission_agent,c.commission_member,"
				+ "c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent,"
				+ attribute + " as attribute ," + splitAttr
				+ ",c.compound_num,c.periods_num,c.betting_date,"
				+ winStateAttribute + ",c.win_amount,c.odds,sysdate," + odds2
				+ ", " + selectedOdds + " from " + tableName
				+ " c where c.id=? )";
		Object[] parameter = new Object[] { id };
		jdbcTemplate.update(insertSql, parameter);

		String deleteSql = "delete " + tableName + " where id=?";
		jdbcTemplate.update(deleteSql, parameter);

	}

	@Override
	public void updateBetResultInvalid(String periodNum, String lotteryType,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======updateBetResultInvalid开始执行SCHEME为:"+scheme+"==============");
		
		Object[] parameter = new Object[] { periodNum };
		if (Constant.LOTTERY_TYPE_K3.equals(lotteryType)) {
			String k3sql = "update "+scheme+"tb_jssb_his set win_state=5 ,win_amount=0 where periods_num=?";
			jdbcTemplate.update(k3sql, parameter);
		} else if ("CQSSC".equals(lotteryType)) {
			String cqsql = "update "+scheme+"tb_cqssc_his set win_state=5 ,win_amount=0 where periods_num=?";
			jdbcTemplate.update(cqsql, parameter);
		} else if ("GDKLSF".equals(lotteryType)) {
			String gdsql = "update "+scheme+"tb_gdklsf_his set win_state=5 ,win_amount=0 where periods_num=?";
			jdbcTemplate.update(gdsql, parameter);
		} else if ("BJ".equals(lotteryType)) {
			String gdsql = "update "+scheme+"tb_bjsc_his set win_state=5 ,win_amount=0 where periods_num=?";
			jdbcTemplate.update(gdsql, parameter);
		} else if ("NC".equals(lotteryType)) {
			String ncsql = "update "+scheme+"tb_nc_his set win_state=5 ,win_amount=0 where periods_num=?";
			jdbcTemplate.update(ncsql, parameter);
		}

	}

	/*
	 * 盘期停开操作时对补货表里的数据的win_state字段进行赋值 对于已经结算的补货数据赋于5，未结算的补货数据赋于7
	 */
	public void updateReplenishResultInvalid(String periodNum,
			String lotteryType, String state,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======updateReplenishResultInvalid开始执行SCHEME为:"+scheme+"==============");
		Object[] parameter = new Object[] { state, periodNum, lotteryType };
		String hksql = "update "+scheme+"tb_replenish_his set win_state=?,win_amount=0 where periods_num=? and type_code like ?";
		jdbcTemplate.update(hksql, parameter);
	}

	public void batchUpdateBetResult(String TableName, String periodNum,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======batchUpdateBetResult开始执行SCHEME为:"+scheme+"==============");
		String sql = "UPDATE "
				+scheme
				+ TableName
				+ " a "
				+ " set win_state=1,win_amount=round(money*(odds-1),2),update_date=sysdate  where   exists "
				+ " (select   1   from   tb_play_win_info   b   where  a.type_code=b.type_code and a.periods_num=b.periods_num and b.win='1' and b.periods_num=?) and a.periods_num=?";

		String sqlNotWin = "UPDATE "
				+scheme
				+ TableName
				+ " a "
				+ " set win_state=2 ,win_amount=0,update_date=sysdate where  not exists "
				+ " (select   1   from  tb_play_win_info   b   where  a.type_code=b.type_code and a.periods_num=b.periods_num and b.win='1' and b.periods_num=?) and a.periods_num=? ";
		Object[] parameters = new Object[] { periodNum, periodNum };

		int winCnt=jdbcTemplate.update(sql, parameters);
		
		int notWinCnt=jdbcTemplate.update(sqlNotWin, parameters);

	}
	
	@Override
	public void batchUpdateBetResultForNC(String TableName, String periodNum,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======batchUpdateBetResultForNC开始执行SCHEME为:"+scheme+"==============");
		
		String sql = "UPDATE "
				+ scheme
				+ TableName
				+ " a "
				+ " set win_state=1,win_amount=round(money*(odds-1),2),update_date=sysdate  where   exists "
				+ " (select   1   from  tb_play_win_info   b   where  a.type_code=b.type_code and a.periods_num=b.periods_num and b.win='1' and b.periods_num=?) and a.periods_num=? and a.type_code not like '%STRAIGHTTHROUGH%'";

		String sqlNotWin = "UPDATE "
				+scheme
				+ TableName
				+ " a "
				+ " set win_state=2 ,win_amount=0,update_date=sysdate where  not exists "
				+ " (select   1   from   tb_play_win_info   b   where  a.type_code=b.type_code and a.periods_num=b.periods_num and b.win='1' and b.periods_num=?) and a.periods_num=?  and a.type_code not like '%STRAIGHTTHROUGH%'";
		Object[] parameters = new Object[] { periodNum, periodNum };

		int winCnt = jdbcTemplate.update(sql, parameters);

		int notWinCnt = jdbcTemplate.update(sqlNotWin, parameters);

	}

	/**
	 * add by peter for K3
	 * 
	 * @param TableName
	 * @param periodNum
	 */
	@Override
	public void batchUpdateBetResultForJSSC(String TableName, String periodNum,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======batchUpdateBetResultForJSSC开始执行SCHEME为:"+scheme+"==============");
		
		String sql = "UPDATE "
				+ scheme
				+ TableName
				+ " a "
				+ " set win_state=1,win_amount=round(money*(odds-1)*plus_odds,2),update_date=sysdate  where   exists "
				+ " (select  1  from   tb_play_win_info   b   where  a.type_code=b.type_code and a.periods_num=b.periods_num and b.win='1' and b.periods_num=?) and a.periods_num=?";

		String sqlNotWin = "UPDATE "
				+scheme
				+ TableName
				+ " a "
				+ " set win_state=2 ,win_amount=0,update_date=sysdate where  not exists "
				+ " (select   1   from   tb_play_win_info   b   where  a.type_code=b.type_code and a.periods_num=b.periods_num and b.win='1' and b.periods_num=?) and a.periods_num=? ";
		Object[] parameters = new Object[] { periodNum, periodNum };

		int winCnt = jdbcTemplate.update(sql, parameters);

		int notWinCnt = jdbcTemplate.update(sqlNotWin, parameters);
	}

	public void deletePlayTypeWinInfoByPeriod(String period, String playType) {
		
		String sql = "delete from  tb_play_win_info  where play_type=? and PERIODS_NUM=?";
		Object[] parameters = new Object[] { playType,period };
		jdbcTemplate.update(sql, parameters);
	}

	public void batchInsertPlayTypeToWin(final List<PlayWinInfo> playwinInfoList, final String periodsNum) {
		String sql = "insert into tb_play_win_info (ID,TYPE_CODE,PLAY_TYPE,PERIODS_NUM,WIN,UPDATE_TIME) VALUES(?,?,?,?,?,SYSDATE)";

		int[] result = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				PlayWinInfo playwinInfo = playwinInfoList.get(i);
				int id = getSeqId("SEQ_TB_PLAY_WIN_INFO");
				ps.setLong(1, id);
				ps.setString(2, playwinInfo.getTypeCode());
				ps.setString(3, playwinInfo.getPlayType());
				ps.setString(4, periodsNum);
				ps.setString(5, playwinInfo.getWin());

			}

			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return playwinInfoList.size();
			}
		});
	}

	public void updateUserTotalCreditLine(Integer Winmoney, Long userId) {
		String sql = "update tb_member_staff_ext set total_credit_line=total_credit_line"
				+ Winmoney + " where member_staff_id=?";
		Object[] parameter = new Object[] { userId };
		jdbcTemplate.update(sql, parameter);

	}

	public List getUserCommsion(String userId, String playType) {

		String sql = "select user_id,user_type,commission_a,commission_b,commission_c from tb_user_commission a, tb_play_type b where a.play_final_type=b.commission_type and b.type_code= ? and user_id ="
				+ userId
				+ " and a.user_type='"
				+ MemberStaff.USER_TYPE_MEMBER
				+ "'";
		Object[] parameter = new Object[] { playType };
		List commissionlist = jdbcTemplate.query(sql, parameter,
				new CommissonMapper());
		return commissionlist;

	}

	public List getManagerCommsion(List userIds, String playType) {
		String userId = StringUtils.join(userIds, ",");
		String sql = "select user_id,user_type,commission_a,commission_b,commission_c from tb_user_commission a, tb_play_type b where a.play_final_type=b.commission_type and b.type_code= ? and user_id in ("
				+ userId
				+ ") and a.user_type!='"
				+ MemberStaff.USER_TYPE_MEMBER + "'";
		Object[] parameter = new Object[] { playType };
		List commissionlist = jdbcTemplate.query(sql, parameter,
				new CommissonMapper());
		return commissionlist;

	}

	public Double queryTotalCommissionMoney(String shopCode,
			String commissionType) {
		String sql = "select sum(money_amount) from tb_play_amount a where type_Code=? and shops_Code=?";
		Object[] parameter = new Object[] { commissionType, shopCode };
		return (Double)jdbcTemplate.queryForObject(sql, parameter, Double.class);

	}
	
	@Override
	public Map<String, BigDecimal> queryTotalCommissionMoneyMap(String shopCode,String playType) {
		Map<String, BigDecimal> userItenMoneyMap = new HashMap<String, BigDecimal>();
		String sql = "select type_code,sum(money_amount) totelMoney from tb_play_amount a where shops_Code=? and type_code like ? group by type_code";
		Object[] parameter = new Object[] { shopCode,playType };
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, parameter);
		if (!CollectionUtils.isEmpty(resultList)) {
			for (Map<String, Object> resultMap : resultList) {
				String typeCode = (String) resultMap.get("type_code");
				BigDecimal totelMoney = (BigDecimal) resultMap.get("totelMoney");
				userItenMoneyMap.put(typeCode, totelMoney);
			}
		}
		return userItenMoneyMap;

	}

	public Integer queryTotalOddsMoney(String shopCode, String oddType) {
		String sql = "select sum(money_amount) from tb_play_amount a where a.odds_type=? and shops_Code=?";
		Object[] parameter = new Object[] { oddType, shopCode };
		return jdbcTemplate.queryForInt(sql, parameter);

	}

	public Integer queryUseritemQuotasMoney(String playType, String periodNum,
			String typeCode, String userId) {
		String[] scanTableList = null;
		Integer sum = 0;
		if (Constant.LOTTERY_TYPE_GDKLSF.equals(playType))
			scanTableList = Constant.GDKLSF_TABLE_LIST;
		else if (Constant.LOTTERY_TYPE_CQSSC.equals(playType))
			scanTableList = Constant.CQSSC_TABLE_LIST;
		else if (Constant.LOTTERY_TYPE_BJ.equals(playType))
			scanTableList = Constant.BJSC_TABLE_LIST;
		//add by peter for k3
		else if(Constant.LOTTERY_TYPE_K3.equals(playType)){
			scanTableList = Constant.K3_TABLE_LIST;
		}
		else if(Constant.LOTTERY_TYPE_NC.equals(playType)){
			scanTableList = Constant.NC_TABLE_LIST;
		}
		else
			scanTableList = Constant.HK_TABLE_LIST;
		for (int i = 0; i < scanTableList.length; i++) {
			String tableName = scanTableList[i];

			String sql = " select sum(money) from "
					+ tableName
					+ " a where a.type_code= ? and a.periods_num=? and a.betting_user_id=?";
			Object[] parameters = new Object[] { typeCode, periodNum, userId };
			sum = jdbcTemplate.queryForInt(sql, parameters);
			if (sum != null && sum > 0)
				break;
		}

		return sum;

	}

	@Override
	public Map<String, BigDecimal> queryUseritemQuotasMoneyMap(String playType, String periodNum, Long userId) {
		Map<String,BigDecimal> userItenMoneyMap = new HashMap<String, BigDecimal>();
		String[] scanTableList = null;
		if (Constant.LOTTERY_TYPE_GDKLSF.equals(playType))
			scanTableList = Constant.GDKLSF_TABLE_LIST;
		else if (Constant.LOTTERY_TYPE_CQSSC.equals(playType))
			scanTableList = Constant.CQSSC_TABLE_LIST;
		else if (Constant.LOTTERY_TYPE_BJ.equals(playType))
			scanTableList = Constant.BJSC_TABLE_LIST;
		// add by peter for k3
		else if (Constant.LOTTERY_TYPE_K3.equals(playType)) {
			scanTableList = Constant.K3_TABLE_LIST;
		} else if (Constant.LOTTERY_TYPE_NC.equals(playType)) {
			scanTableList = Constant.NC_TABLE_LIST;
		}
		for (int i = 0; i < scanTableList.length; i++) {
			String tableName = scanTableList[i];

			String sql = " select type_code typeCode ,sum(money) totelMoney from " + tableName + " a where  a.periods_num=? and a.betting_user_id=? group by type_code";

			Object[] parameters = new Object[] { periodNum, userId };
			List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, parameters);
			if (!CollectionUtils.isEmpty(resultList)) {
				for (Map<String, Object> resultMap : resultList) {
					String typeCode = (String) resultMap.get("typeCode");
					BigDecimal totelMoney = (BigDecimal)resultMap.get("totelMoney");
					userItenMoneyMap.put(typeCode, totelMoney);
				}
			}

		}
		return userItenMoneyMap;
	}
	
	public Integer  queryUserLMitemQuotasMoney(String playType, String periodNum,String typeCode,String userId,String attr)
	{
		String sql = " select sum(money) from tb_gdklsf_straightthrough "
				+ " a where a.type_code= ? and a.periods_num=? and a.betting_user_id=? and attribute=?";
		Object[] parameters = new Object[] { typeCode, periodNum, userId,attr };
		Integer sum  = jdbcTemplate.queryForInt(sql, parameters);
		
		return sum;
		
	}
	@Override
	public Integer  queryUserLMitemQuotasMoneyForNC(String playType, String periodNum,String typeCode,String userId,String attr)
	{
		String sql = " select sum(money) from tb_nc "
				+ " a where a.type_code= ? and a.periods_num=? and a.betting_user_id=? and attribute=?";
		Object[] parameters = new Object[] { typeCode, periodNum, userId,attr };
		Integer sum  = jdbcTemplate.queryForInt(sql, parameters);
		
		return sum;
		
	}
	//state -1 表示當期 所有數據遷移到 歷史表
	public void miragationCQDataToHistory(String periodsNum, String state,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======miragationCQDataToHistory开始执行SCHEME为:"+scheme+"==============");
		String winStateAttribute = "win_state";
		if ("0".equals(state))
			winStateAttribute = "'4'";
		for (int i = 0; i < Constant.CQSSC_TABLE_LIST.length; i++) {
			String tableName = Constant.CQSSC_TABLE_LIST[i];

			String sql = "insert into "+scheme+"tb_cqssc_his "
					+ "(id,create_date,origin_tb_name,origin_id,order_no,type_code,money,plate,"
					+ "betting_user_id,chiefstaff,branchstaff,stockholderstaff,genagenstaff,agentstaff,"
					+ "commission_branch,commission_gen_agent,commission_stockholder,commission_agent,commission_member,"
					+ "rate_chief,rate_branch,rate_gen_agent,rate_stockholder,rate_agent,"
					+ "periods_num,betting_date,win_state,win_amount,odds,update_date,commission_type)"
					+ "(select seq_tb_cqssc_his.nextval,SYSDATE,'"
					+ tableName
					+ "',c.id,c.order_no,c.type_code,c.money,plate,"
					+ "c.betting_user_id,c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.agentstaff,"
					+ "c.commission_branch,c.commission_gen_agent,c.commission_stockholder,c.commission_agent,c.commission_member,"
					+ "c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent,"
					+ "c.periods_num,c.betting_date," + winStateAttribute
					+ ",c.win_amount,c.odds,sysdate,commission_type from " + scheme + tableName
					+ " c where c.win_state>=? and c.periods_num=? )";
			Object[] parameter = new Object[] { state, periodsNum };
			jdbcTemplate.update(sql, parameter);
			String deleteSql = "delete " + scheme + tableName
					+ " where win_state>=? and periods_num=? ";
			jdbcTemplate.update(deleteSql, parameter);

		}

	}
	//state -1 表示當期 所有數據遷移到 歷史表
	public void miragationBJDataToHistory(String periodsNum, String state,String scheme) {

		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		log.info("=======开始执行SCHEME为:"+scheme+"==============");
		String winStateAttribute = "win_state";
		if ("0".equals(state)){
			winStateAttribute = "'5'";
			}
		for (int i = 0; i < Constant.BJSC_TABLE_LIST.length; i++) {
			String tableName = Constant.BJSC_TABLE_LIST[i];

			String sql = "insert into "+scheme+"tb_bjsc_his "
					+ "(id,create_date,origin_tb_name,origin_id,order_no,type_code,money,plate,"
					+ "betting_user_id,chiefstaff,branchstaff,stockholderstaff,genagenstaff,agentstaff,"
					+ "commission_branch,commission_gen_agent,commission_stockholder,commission_agent,commission_member,"
					+ "rate_chief,rate_branch,rate_gen_agent,rate_stockholder,rate_agent,"
					+ "periods_num,betting_date,win_state,win_amount,odds,update_date,commission_type)"
					+ "(select seq_tb_bjsc_his.nextval,SYSDATE,'"
					+ tableName
					+ "',c.id,c.order_no,c.type_code,c.money,plate,"
					+ "c.betting_user_id,c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.agentstaff,"
					+ "c.commission_branch,c.commission_gen_agent,c.commission_stockholder,c.commission_agent,c.commission_member,"
					+ "c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent,"
					+ "c.periods_num,c.betting_date," + winStateAttribute
					+ ",c.win_amount,c.odds,sysdate,commission_type from " 
					+ scheme
					+ tableName
					+ " c where c.win_state>=? and c.periods_num=? )";
			Object[] parameter = new Object[] { state, periodsNum };
			jdbcTemplate.update(sql, parameter);
			String deleteSql = "delete " + scheme + tableName
					+ " where win_state>=? and periods_num=? ";
			jdbcTemplate.update(deleteSql, parameter);

		}

	}
	//state -1 表示當期 所有數據遷移到 歷史表
	public void miragationGDDataToHistory(String periodsNum, String state,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======miragationGDDataToHistory开始执行SCHEME为:"+scheme+"==============");
		
		String winStateAttribute = "win_state";
		if ("0".equals(state))
			winStateAttribute = "'5'";

		for (int i = 0; i < Constant.GDKLSF_TABLE_LIST.length; i++) {

			String tableName = Constant.GDKLSF_TABLE_LIST[i];
			String attribute = "null";
			String splitAttr = "null";
			if (tableName.equals(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME)) {
				attribute = "attribute";
				splitAttr = "split_attribute";
			}

			String insertSql = "insert into " + scheme + "tb_gdklsf_his "
					+ "(id,create_date,origin_tb_name,origin_id,order_no,type_code,money,plate,"
					+ "betting_user_id,chiefstaff,branchstaff,stockholderstaff,genagenstaff,agentstaff,"
					+ "commission_branch,commission_gen_agent,commission_stockholder,commission_agent,commission_member,"
					+ "rate_chief,rate_branch,rate_gen_agent,rate_stockholder,rate_agent,"
					+ "attribute,split_attribute,compound_num,periods_num,betting_date,win_state,win_amount,odds,update_date,commission_type)"
					+ "(select seq_tb_gdklsf_his.nextval,SYSDATE,'"
					+ tableName
					+ "',c.id,c.order_no,c.type_code,c.money,plate,"
					+ "c.betting_user_id,c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.agentstaff,"
					+ "c.commission_branch,c.commission_gen_agent,c.commission_stockholder,c.commission_agent,c.commission_member,"
					+ "c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent,"
					+ attribute + " as attribute ," + splitAttr
					+ ",c.compound_num,c.periods_num,c.betting_date,"
					+ winStateAttribute + ",c.win_amount,c.odds,sysdate,commission_type from "
					+scheme
					+ tableName
					+ " c where c.win_state>=? and c.periods_num=?)";
			Object[] parameter = new Object[] { state, periodsNum };
			jdbcTemplate.update(insertSql, parameter);
			String deleteSql = "delete " + scheme + tableName
					+ " where win_state>=? and periods_num=? ";
			jdbcTemplate.update(deleteSql, parameter);

		}

	}

	/**
	 * 迁移农场投注信息到历史表
	 * 
	 * @param periodsNum
	 * @param state
	 */
	@Override
	public void miragationNCDataToHistory(String periodsNum, String state,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======miragationNCDataToHistory开始执行SCHEME为:"+scheme+"==============");
		String winStateAttribute = "win_state";
		if ("0".equals(state))
			winStateAttribute = "'5'";

		for (int i = 0; i < Constant.NC_TABLE_LIST.length; i++) {

			String tableName = Constant.NC_TABLE_LIST[i];
			String attribute = "attribute";
			String splitAttr = "split_attribute";

			String insertSql = "insert into "+scheme+"tb_nc_his " + "(id,create_date,origin_tb_name,origin_id,order_no,type_code,money,plate,"
					+ "betting_user_id,chiefstaff,branchstaff,stockholderstaff,genagenstaff,agentstaff,"
					+ "commission_branch,commission_gen_agent,commission_stockholder,commission_agent,commission_member," + "rate_chief,rate_branch,rate_gen_agent,rate_stockholder,rate_agent,"
					+ "attribute,split_attribute,compound_num,periods_num,betting_date,win_state,win_amount,odds,update_date,commission_type)" + "(select seq_tb_nc_his.nextval,SYSDATE,'"
					+ tableName + "',c.id,c.order_no,c.type_code,c.money,plate," + "c.betting_user_id,c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.agentstaff,"
					+ "c.commission_branch,c.commission_gen_agent,c.commission_stockholder,c.commission_agent,c.commission_member,"
					+ "c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent," + attribute + " as attribute ," + splitAttr + ",c.compound_num,c.periods_num,c.betting_date,"
					+ winStateAttribute + ",c.win_amount,c.odds,sysdate,commission_type from " + scheme + tableName + " c where c.win_state>=? and c.periods_num=?)";
			Object[] parameter = new Object[] { state, periodsNum };
			jdbcTemplate.update(insertSql, parameter);
			String deleteSql = "delete " + scheme + tableName + " where win_state>=? and periods_num=? ";
			jdbcTemplate.update(deleteSql, parameter);

		}

	}

	public void miragationHKDataToHistory(String periodsNum, String state) {
		String winStateAttribute = "win_state";

		if ("0".equals(state))
			winStateAttribute = "'4'";
		for (int i = 0; i < Constant.HK_TABLE_LIST.length; i++) {
			String tableName = Constant.HK_TABLE_LIST[i];
			String attribute = "null";
			String splitAttr = "null";
			String odds2 = "null";
			String selectedOdds = null;
			if (tableName.equals(Constant.HK_LM_TABLE_NAME)
					|| tableName.equals(Constant.HK_LX_TABLE_NAME)
					|| tableName.equals(Constant.HK_SXL_TABLE_NAME)
					|| tableName.equals(Constant.HK_WSL_TABLE_NAME)
					|| tableName.equals(Constant.HK_WBZ_TABLE_NAME)
					|| tableName.equals(Constant.HK_GG_TABLE_NAME)

			) {
				attribute = "attribute";
			}

			if (tableName.equals(Constant.HK_LM_TABLE_NAME)
					|| tableName.equals(Constant.HK_SXL_TABLE_NAME)
					|| tableName.equals(Constant.HK_WSL_TABLE_NAME)
					|| tableName.equals(Constant.HK_WBZ_TABLE_NAME)) {

				splitAttr = "split_attribute";
			}
			if (Constant.HK_LM_TABLE_NAME.equals(tableName)
					|| Constant.HK_GG_TABLE_NAME.equals(tableName)) {
				odds2 = "odds2";

			}

			if (tableName.equals(Constant.HK_SXL_TABLE_NAME)
					|| tableName.equals(Constant.HK_WSL_TABLE_NAME)
					|| tableName.equals(Constant.HK_WBZ_TABLE_NAME)
					|| Constant.HK_GG_TABLE_NAME.equals(tableName)) {

				selectedOdds = "select_odds";
			}
			String insertSql = "insert into tb_hklhc_his "
					+ "(id,create_date,origin_tb_name,origin_id,order_no,type_code,money,plate,"
					+ "betting_user_id,chiefstaff,branchstaff,stockholderstaff,genagenstaff,agentstaff,"
					+ "commission_branch,commission_gen_agent,commission_stockholder,commission_agent,commission_member,"
					+ "rate_chief,rate_branch,rate_gen_agent,rate_stockholder,rate_agent,"
					+ "attribute,split_attribute,compound_num,periods_num,betting_date,win_state,win_amount,odds,update_date,odds2,select_odds)"
					+ "(select seq_tb_hklhc_his.nextval,SYSDATE,'"
					+ tableName
					+ "',c.id,c.order_no,c.type_code,c.money,plate,"
					+ "c.betting_user_id,c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.agentstaff,"
					+ "c.commission_branch,c.commission_gen_agent,c.commission_stockholder,c.commission_agent,c.commission_member,"
					+ "c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent,"
					+ attribute + " as attribute ," + splitAttr
					+ ",c.compound_num,c.periods_num,c.betting_date,"
					+ winStateAttribute + ",c.win_amount,c.odds,sysdate,"
					+ odds2 + ", " + selectedOdds + " from " + tableName
					+ " c where c.win_state>=? and c.periods_num=? )";
			Object[] parameter = new Object[] { state, periodsNum };
			jdbcTemplate.update(insertSql, parameter);

			String deleteSql = "delete " + tableName
					+ " where win_state>=? and periods_num=?";
			jdbcTemplate.update(deleteSql, parameter);

		}

	}
	
	@Override
	public void miragationJSDataToHistory(String periodsNum, String state,String scheme) {

		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======miragationJSDataToHistory开始执行SCHEME为:"+scheme+"==============");
		
		String winStateAttribute = "win_state";
		if ("0".equals(state)){
			winStateAttribute = "'5'";
		}
		for (int i = 0; i < Constant.K3_TABLE_LIST.length; i++) {
			String tableName = Constant.K3_TABLE_LIST[i];

			String sql = "insert into "+scheme+"tb_jssb_his "
					+ "(id,create_date,origin_tb_name,origin_id,order_no,type_code,money,plate,"
					+ "betting_user_id,chiefstaff,branchstaff,stockholderstaff,genagenstaff,agentstaff,"
					+ "commission_branch,commission_gen_agent,commission_stockholder,commission_agent,commission_member,"
					+ "rate_chief,rate_branch,rate_gen_agent,rate_stockholder,rate_agent,"
					+ "periods_num,betting_date,win_state,win_amount,odds,update_date,commission_type,plus_odds)"
					+ "(select seq_tb_jssb_his.nextval,SYSDATE,'"
					+ tableName
					+ "',c.id,c.order_no,c.type_code,c.money,plate,"
					+ "c.betting_user_id,c.chiefstaff,c.branchstaff,c.stockholderstaff,c.genagenstaff,c.agentstaff,"
					+ "c.commission_branch,c.commission_gen_agent,c.commission_stockholder,c.commission_agent,c.commission_member,"
					+ "c.rate_chief,c.rate_branch,c.rate_gen_agent,c.rate_stockholder,c.rate_agent,"
					+ "c.periods_num,c.betting_date," + winStateAttribute
					+ ",c.win_amount,c.odds,sysdate,commission_type,plus_odds from " + scheme + tableName
					+ " c where c.win_state>=? and c.periods_num=? )";
			Object[] parameter = new Object[] { state, periodsNum };
			jdbcTemplate.update(sql, parameter);
			String deleteSql = "delete " + scheme + tableName
					+ " where win_state>=? and periods_num=? ";
			jdbcTemplate.update(deleteSql, parameter);

		}

	}
	@Override
	public void miragationReplenishDataToHistory(String periodsNum,String typeCode,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======miragationReplenishDataToHistory开始执行SCHEME为:"+scheme+"==============");
			String sql = " Insert into "+scheme+"TB_REPLENISH_HIS VALUE ("
					+ " SELECT SEQ_TB_REPLENISH_HIS.nextval,ORDER_NO,TYPE_CODE,MONEY,ATTRIBUTE,REPLENISH_USER_ID,"
					+ " REPLENISH_ACC_USER_ID,PERIODS_NUM,PLATE,BETTING_DATE,WIN_STATE,WIN_AMOUNT,"
					+ " ODDS,COMMISSION,RATE,UPDATE_USER,UPDATE_DATE,REMARK,CHIEFSTAFF,BRANCHSTAFF,"
					+ " STOCKHOLDERSTAFF,GENAGENSTAFF,AGENTSTAFF,RATE_CHIEF,RATE_BRANCH,RATE_STOCKHOLDER,"
					+ " RATE_GEN_AGENT,RATE_AGENT,ODDS2,COMMISSION_CHIEF,COMMISSION_BRANCH,COMMISSION_GEN_AGENT"
					+ ",COMMISSION_STOCKHOLDER,COMMISSION_AGENT,COMMISSION_MEMBER,COMMISSION_TYPE,SELECT_ODDS "
					+ " FROM "+scheme+"TB_REPLENISH WHERE WIN_STATE != 0 AND PERIODS_NUM =? AND TYPE_CODE LIKE ?" + ")";
			Object[] parameter = new Object[] { periodsNum, typeCode+"%" };
			jdbcTemplate.update(sql, parameter);
			
			String deleteSql = "DELETE FROM "+scheme+"TB_REPLENISH WHERE WIN_STATE!=0 AND PERIODS_NUM=? AND TYPE_CODE LIKE ?";
			jdbcTemplate.update(deleteSql, parameter);
	}
	
	/**
	 *   给总管停开调用的补货移库方法,将补货数据写入补货历史表,并设定win_state=5,删除原来补货投注表的win_state=0的数据
	 */
	@Override
	public void miragationReplenishDataToHistoryForNotOpen(String periodsNum,String typeCode,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======miragationReplenishDataToHistoryForNotOpen开始执行SCHEME为:"+scheme+"==============");
			String sql = " Insert into "+scheme+"TB_REPLENISH_HIS VALUE ("
					+ " SELECT SEQ_TB_REPLENISH_HIS.nextval,ORDER_NO,TYPE_CODE,MONEY,ATTRIBUTE,REPLENISH_USER_ID,"
					+ " REPLENISH_ACC_USER_ID,PERIODS_NUM,PLATE,BETTING_DATE,5,WIN_AMOUNT,"
					+ " ODDS,COMMISSION,RATE,UPDATE_USER,UPDATE_DATE,REMARK,CHIEFSTAFF,BRANCHSTAFF,"
					+ " STOCKHOLDERSTAFF,GENAGENSTAFF,AGENTSTAFF,RATE_CHIEF,RATE_BRANCH,RATE_STOCKHOLDER,"
					+ " RATE_GEN_AGENT,RATE_AGENT,ODDS2,COMMISSION_CHIEF,COMMISSION_BRANCH,COMMISSION_GEN_AGENT"
					+ ",COMMISSION_STOCKHOLDER,COMMISSION_AGENT,COMMISSION_MEMBER,COMMISSION_TYPE,SELECT_ODDS "
					+ " FROM "+scheme+"TB_REPLENISH WHERE WIN_STATE = 0 AND PERIODS_NUM =? AND TYPE_CODE LIKE ?" + ")";
			Object[] parameter = new Object[] { periodsNum, typeCode+"%" };
			jdbcTemplate.update(sql, parameter);
			
			String deleteSql = "DELETE FROM "+scheme+"TB_REPLENISH WHERE WIN_STATE=0 AND PERIODS_NUM=? AND TYPE_CODE LIKE ?";
			jdbcTemplate.update(deleteSql, parameter);
	}
	
	/**
	 * add by peter for K3,更新投注表三军的附加赔率字段
	 * @param tableName
	 * @param typeCode
	 * @param periodsNum
	 * @param plusOdds
	 */
	@Override
	public void updatePlusOddsForJSSB(String typeCode, String periodsNum, int plusOdds, String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======updatePlusOddsForJSSB开始执行SCHEME为:"+scheme+"==============");
		
		String sql = "UPDATE "+scheme+"TB_JSSB SET PLUS_ODDS=? WHERE TYPE_CODE=? AND PERIODS_NUM=?";
		Object[] parameters = new Object[] { plusOdds, typeCode, periodsNum };
		jdbcTemplate.update(sql, parameters);
	}
	/**
	 * add by peter for K3,更新历史表三军的附加赔率字段
	 * @param tableName
	 * @param typeCode
	 * @param periodsNum
	 * @param plusOdds
	 */
	@Override
	public void updatePlusOddsForJSSBHis(String typeCode, String periodsNum, int plusOdds,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======updatePlusOddsForJSSBHis开始执行SCHEME为:"+scheme+"==============");
		String sql = "UPDATE "+scheme+"TB_JSSB_HIS SET PLUS_ODDS=? WHERE TYPE_CODE=? AND PERIODS_NUM=?";
		Object[] parameters = new Object[] { plusOdds, typeCode, periodsNum };
		jdbcTemplate.update(sql, parameters);
	}

	public Map gdBetStatistics(String periodNum) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select commission_type as type,sum(money) as money from (select b1.commission_type,b1.money,b1.periods_num  from  tb_gdklsf_ball_first b1");
		sql.append("union all select b2.commission_type,b2.money,b2.periods_num  from tb_gdklsf_ball_second b2");
		sql.append(" union all ");
		sql.append("select b3.commission_type,b3.money,b3.periods_num from  tb_gdklsf_ball_third b3");
		sql.append(" union all ");
		sql.append(" select b4.commission_type,b4.money, b4.periods_num from tb_gdklsf_ball_forth b4");
		sql.append("union all ");
		sql.append("select b5.commission_type,b5.money , b5.periods_num from  tb_gdklsf_ball_fifth b5");
		sql.append("union all ");
		sql.append("select b6.commission_type,b6.money , b6.periods_num from   tb_gdklsf_ball_sixth b6");
		sql.append("union all ");
		sql.append("select b7.commission_type,b7.money , b7.periods_num from  tb_gdklsf_ball_seventh b7");
		sql.append("union all ");
		sql.append(" select b8.commission_type,b8.money , b8.periods_num from   tb_gdklsf_ball_eighth b8");
		sql.append("union all ");
		sql.append("select b9.commission_type,b9.money , b9.periods_num from    tb_gdklsf_double_side b9");
		sql.append("union all");
		sql.append("select b10.commission_type,b10.money , b10.periods_num from  tb_gdklsf_straightthrough b10");
		sql.append(")");
		sql.append("where  periods_num=?");

		sql.append("group by commission_type");
		Object[] parameters = new Object[] { periodNum };
		return convertListToMap(jdbcTemplate.queryForList(sql.toString(),parameters));
	}

	public Map cqBetStatistics(String periodNum) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select commission_type as type,sum(money) as money from ");
		sql.append(" (select b1.commission_type,b1.money,b1.periods_num  from  tb_cqssc_ball_first b1");
		sql.append(" union all ");
		sql.append("   select b2.commission_type,b2.money,b2.periods_num  from  tb_cqssc_ball_second b2");
		sql.append(" union all ");
		sql.append("  select b3.commission_type,b3.money,b3.periods_num from  tb_cqssc_ball_third b3");
		sql.append("   union all ");
		sql.append("  select b4.commission_type,b4.money, b4.periods_num from tb_cqssc_ball_forth b4");
		sql.append("   union all ");
		sql.append("    select b5.commission_type,b5.money , b5.periods_num from  tb_cqssc_ball_fifth b5  ");
		sql.append("    )  where periods_num=? ");
		sql.append("    group by commission_type ");
		
		 Object[] parameters = new Object[] { periodNum };
		
		   return convertListToMap(jdbcTemplate.queryForList(sql.toString(),parameters));
	}

	public Map bjBetStatistics(String periodNum) {
		StringBuffer sql = new StringBuffer();
        sql.append("select commission_type  as type,sum(money) as money from tb_bjsc");
        sql.append("  where periods_num=?");
        sql.append("  group by commission_type");
        Object[] parameters = new Object[] { periodNum };
         return convertListToMap(jdbcTemplate.queryForList(sql.toString(),parameters));

	}

	 private Map<String,Integer> convertListToMap(List treeList)
	    {
	    	Map<String,Integer> retmap=new HashMap<String,Integer>();
	    	for (int i = 0; i < treeList.size(); i++) {
	    	Map	typeMoney=(Map)treeList.get(i);
	    	String key=(String)typeMoney.get("type");
	    	Integer value=(Integer)typeMoney.get("money");
	    		
	    		retmap.put(key,value);
			}
	    	return retmap;
	    	
	    }
	public void batchUpdateUserAvailableCredit(String periodsNum,
			String tableName,String scheme) {

		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		log.info("=======开始执行SCHEME为:"+scheme+"==============");
		String sql = "update " + scheme + "tb_member_staff_ext b set b.available_credit_line=available_credit_line+(select sum(case when win_state=1 then money+win_amount +money*c.commission_member/100 when win_state=2 then money*c.commission_member/100 when win_state=9 then money else 0 end ) winMoney "
				+ "from "
				+ scheme
				+ tableName
				+ " c where c.betting_user_id=b.member_staff_id and c.periods_num=? group by c.betting_user_id) where  exists (select 1 from   "
				+ scheme
				+ tableName
				+ " c where  c.betting_user_id=b.member_staff_id and c.periods_num=?)";

		Object[] parameter = new Object[] { periodsNum, periodsNum };
		jdbcTemplate.update(sql, parameter);

	}
	
	//批量恢复用户信用额
    public void batchRestoreUserAvailableCredit(String periodsNum,String tableName,String scheme)
    {
    	if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======batchRestoreUserAvailableCredit开始执行SCHEME为:"+scheme+"==============");
    	
    	String sql = "update "+scheme+"tb_member_staff_ext b set b.available_credit_line=available_credit_line-(select sum(case when win_state=1 then money+win_amount +money*c.commission_member/100 when win_state=2 then money*c.commission_member/100 when win_state=9 then money else 0 end ) winMoney "
				+ "from "
    			+ scheme
				+ tableName
				+ " c where c.betting_user_id=b.member_staff_id and c.periods_num=? group by c.betting_user_id) where  exists (select 1 from   "
				+ scheme
				+ tableName
				+ " c where  c.betting_user_id=b.member_staff_id and c.periods_num=?)";


		Object[] parameter = new Object[] { periodsNum, periodsNum };
		jdbcTemplate.update(sql, parameter);
  	
    }
	
	
	

	public double queryTodayWinMoney(Long userId) {
		String sqlCQ = "select sum(decode(k.win_state,1,k.win_amount+k.money,0))+sum(money*(k.commission_member/100-1))  from view_cqssc_his_today k where  trunc(k.betting_date- interval '2' hour)=trunc(sysdate- interval '2' hour) and k.betting_user_id=? and win_state<=2 and win_state>=1";
		String sqlGD = "select sum(decode(k.win_state,1,k.win_amount+k.money,0))+sum(money*(k.commission_member/100-1))  from view_gdklsf_his_today k where  trunc(k.betting_date- interval '2' hour)=trunc(sysdate-interval '2' hour) and k.betting_user_id=? and win_state<=2 and win_state>=1";
		String sqlHK = "select sum(decode(k.win_state,1,k.win_amount+k.money,0))+sum(money*(k.commission_member/100-1))  from view_bjsc_his_today k where  trunc(k.betting_date- interval '2' hour)=trunc(sysdate-interval '2' hour) and k.betting_user_id=? and win_state<=2 and win_state>=1";
		//add by peter for K3
		String sqlJS = "select sum(decode(k.win_state,1,k.win_amount+k.money,0))+sum(money*(k.commission_member/100-1))  from view_jssb_his_today k where  trunc(k.betting_date- interval '2' hour)=trunc(sysdate-interval '2' hour) and k.betting_user_id=? and win_state<=2 and win_state>=1";
		//add by peter for NC
		String sqlNC = "select sum(decode(k.win_state,1,k.win_amount+k.money,0))+sum(money*(k.commission_member/100-1))  from tb_nc_his k where  trunc(k.betting_date- interval '2' hour)=trunc(sysdate-interval '2' hour) and k.betting_user_id=? and win_state<=2 and win_state>=1";
		Object[] parameter = new Object[] { userId };
		Double cqWin = (Double) jdbcTemplate.queryForObject(sqlCQ, parameter,
				Double.class);
		Double gdWin = (Double) jdbcTemplate.queryForObject(sqlGD, parameter,
				Double.class);
		Double hkWin = (Double) jdbcTemplate.queryForObject(sqlHK, parameter,
				Double.class);
		//add by peter for K3
		Double jsWin = (Double) jdbcTemplate.queryForObject(sqlJS, parameter,
				Double.class);
		//add by peter for NC
		Double ncWin = (Double) jdbcTemplate.queryForObject(sqlNC, parameter,
				Double.class);
		if (cqWin == null)
			cqWin = 0d;
		if (gdWin == null)
			gdWin = 0d;
		if (hkWin == null)
			hkWin = 0d;
		//add by peter for K3
		if (jsWin == null)
			jsWin = 0d;
		//add by peter for NC
		if (ncWin == null)
			ncWin = 0d;
		return cqWin.doubleValue() + gdWin.doubleValue() + hkWin.doubleValue()+jsWin.doubleValue()+ncWin.doubleValue();

	}

	@Override
	public List<CqsscHis> queryCQBalanceDao(String beginDate, String endDate) {
		String sql = "select * from TB_GDKLSF_HIS where trunc(create_date) >= Date '"
				+ beginDate
				+ "' and trunc(create_date) <= Date '"
				+ endDate
				+ "' ";
		
		return jdbcTemplate.queryForList(sql, CqsscHis.class);
	}

	public List<BalanceInfo> queryBalanceDao(Long userId, Date beginDate,
			Date endDate) {

		List<BalanceInfo> retList = new ArrayList<BalanceInfo>();
		String sqlcq = "select trunc(ch.betting_date- interval '2' hour) betDate,count(distinct order_no) count,sum(money) money,sum(case when win_state=1 then win_amount when win_state=2 then -money when win_state=9 then 0 else 0 end ) winMoney,sum(case when win_state=1 then money*ch.commission_member/100 when win_state=2 then money*ch.commission_member/100 when win_state=9 then 0 else 0 end ) commission from tb_cqssc_his ch where trunc(ch.betting_date)<=trunc(?) and trunc(ch.betting_date)>=trunc(?) and betting_user_id=? group by trunc(ch.betting_date- interval '2' hour)";
		String sqlgd = "select trunc(ch.betting_date) betDate,count(distinct order_no) count,sum(money) money,sum(case when win_state=1 then win_amount when win_state=2 then -money when win_state=9 then 0 else 0 end ) winMoney,sum(case when win_state=1 then money*ch.commission_member/100 when win_state=2 then money*ch.commission_member/100 when win_state=9 then 0 else 0 end ) commission from tb_gdklsf_his ch where trunc(ch.betting_date)<=trunc(?) and trunc(ch.betting_date)>=trunc(?) and betting_user_id=? group by trunc(ch.betting_date)";
		String sqlbj = "select trunc(ch.betting_date) betDate,count(distinct order_no) count,sum(money) money,sum(case when win_state=1 then win_amount when win_state=2 then -money when win_state=9 then 0 else 0 end ) winMoney,sum(case when win_state=1 then money*ch.commission_member/100 when win_state=2 then money*ch.commission_member/100 when win_state=9 then 0 else 0 end ) commission from tb_bjsc_his ch where trunc(ch.betting_date)<=trunc(?) and trunc(ch.betting_date)>=trunc(?) and betting_user_id=? group by trunc(ch.betting_date)";
		//add by peter for K3
		String sqljs = "select trunc(ch.betting_date) betDate,count(distinct order_no) count,sum(money) money,sum(case when win_state=1 then win_amount when win_state=2 then -money when win_state=9 then 0 else 0 end ) winMoney,sum(case when win_state=1 then money*ch.commission_member/100 when win_state=2 then money*ch.commission_member/100 when win_state=9 then 0 else 0 end ) commission from tb_jssb_his ch where trunc(ch.betting_date)<=trunc(?) and trunc(ch.betting_date)>=trunc(?) and betting_user_id=? group by trunc(ch.betting_date)";
		// String
		//add by peter for NC
		String sqlnc = "select trunc(ch.betting_date- interval '2' hour) betDate,count(distinct order_no) count,sum(money) money,sum(case when win_state=1 then win_amount when win_state=2 then -money when win_state=9 then 0 else 0 end ) winMoney,sum(case when win_state=1 then money*ch.commission_member/100 when win_state=2 then money*ch.commission_member/100 when win_state=9 then 0 else 0 end ) commission from tb_nc_his ch where trunc(ch.betting_date)<=trunc(?) and trunc(ch.betting_date)>=trunc(?) and betting_user_id=? group by trunc(ch.betting_date- interval '2' hour)";
		// sqlHK="select trunc(ch.betting_date) betDate ,count(*) count,sum(money) money,sum(decode(win_state,'1',win_amount,-money)) winMoney,sum(money*ch.commission_member/100) commission from tb_hklhc_his ch where trunc(ch.betting_date)<=trunc(?) and trunc(ch.betting_date)>=trunc(?) and betting_user_id=? group by trunc(ch.betting_date) ";
		Object[] parameter = new Object[] { endDate, beginDate, userId };
		List<BalanceInfo> cqList = jdbcTemplate.query(sqlcq, parameter,
				new BalanceInfoItemMapper());
		List<BalanceInfo> gdList = jdbcTemplate.query(sqlgd, parameter,
				new BalanceInfoItemMapper());
		// List<BalanceInfo> hkList=jdbcTemplate.query(sqlHK,parameter,new
		// BalanceInfoItemMapper());
		List<BalanceInfo> bjList = jdbcTemplate.query(sqlbj, parameter,
				new BalanceInfoItemMapper());
		//add by peter for K3
		List<BalanceInfo> jsList = jdbcTemplate.query(sqljs, parameter,
				new BalanceInfoItemMapper());
		//add by peter for nc
		List<BalanceInfo> ncList = jdbcTemplate.query(sqlnc, parameter,
				new BalanceInfoItemMapper());

		retList.addAll(cqList);
		retList.addAll(gdList);
		retList.addAll(bjList);
		//add by peter for K3
		retList.addAll(jsList);
		//add by peter for nc
		retList.addAll(ncList);
		// retList.addAll(hkList);
		return retList;

	}

	@Override
	public List<BalanceInfo> queryHKBalanceDao(Long userId, Date beginDate,
			Date endDate) {
		List<BalanceInfo> retList = new ArrayList<BalanceInfo>();
		String sqlhk = "select trunc(ch.betting_date) betDate,count(distinct order_no) count,sum(money) money,sum(case when win_state=1 then win_amount when win_state=2 then -money when win_state=9 then 0 else 0 end ) winMoney,sum(case when win_state=1 then money*ch.commission_member/100 when win_state=2 then money*ch.commission_member/100when win_state=9 then 0 else 0 end ) commission from tb_hklhc_his ch where trunc(ch.betting_date)<=trunc(?) and trunc(ch.betting_date)>=trunc(?) and betting_user_id=? group by trunc(ch.betting_date)";
		Object[] parameter = new Object[] { endDate, beginDate, userId };
		List<BalanceInfo> gdList = jdbcTemplate.query(sqlhk, parameter,
				new BalanceInfoItemMapper());
		retList.addAll(gdList);
		return retList;
	}

	public List<BalanceInfo> querySSCHistoryBet(Long userId, String betDate) {

		List<BalanceInfo> retList = new ArrayList<BalanceInfo>();
		String sqlcq = "select ch.type_code,money,decode(win_state,'1',money,-money)*(1-ch.commission_member/100)+ch.win_amount,count(1) as count from tb_cqssc_his ch where ch.betting_user_id=? and trunc(ch.betting_date- interval '2' hour)=trunc(date ?)";
		String sqlgd = "select ch.type_code,money,decode(win_state,'1',money,-money)*(1-ch.commission_member/100)+ch.win_amount,count(1) as count from tb_cqssc_his ch where ch.betting_user_id=? and trunc(ch.betting_date)=trunc(date ?)";

		Object[] parameter = new Object[] { userId, betDate };
		List<BalanceInfo> cqList = jdbcTemplate.query(sqlcq, parameter,
				new BalanceInfoItemMapper());
		List<BalanceInfo> gdList = jdbcTemplate.query(sqlgd, parameter,
				new BalanceInfoItemMapper());

		retList.addAll(cqList);
		retList.addAll(gdList);
		return retList;

	}

	public List<BalanceInfo> queryHKHistoryBet(Long userId, String betDate) {

		List<BalanceInfo> retList = new ArrayList<BalanceInfo>();

		String sqlHK = "select ch.type_code,money,decode(win_state,'1',money,-money)*(1-ch.commission_member/100)+ch.win_amount from tb_cqssc_his ch where ch.betting_user_id=? and trunc(ch.betting_date)=trunc(date ?) ";
		Object[] parameter = new Object[] { betDate, userId };

		List<BalanceInfo> hkList = jdbcTemplate.query(sqlHK, parameter,
				new BalanceInfoItemMapper());

		retList.addAll(hkList);
		return retList;

	}

	@Override
	public List<CQandGDReportInfo> queryCQandGDReportDao(Long userId,
			Date beginDate) {
		String cqSQL = "select ch.type_code,max(ch.betting_date) betDate,null as attribute,count(ch.order_no) count,ch.periods_num,sum(money) betMoney,ch.order_no orderNo,max(win_state) winState,sum(decode(win_state,'1',money*(ch.commission_member/100),-money*(1-ch.commission_member/100)))+sum(ch.win_amount) recessionResults,max(odds) odds from tb_cqssc_his ch  where ch.betting_user_id=? and trunc(ch.betting_date- interval '2' hour)=trunc(?) group by ch.order_no,type_code,ch.periods_num";
		String gdSQL = "select ch.type_code,max(ch.betting_date) betDate,max(attribute) attribute,count(ch.order_no) count,ch.periods_num,sum(money) betMoney,ch.order_no orderNo,max(win_state) winState,sum(decode(win_state,'1',money*(ch.commission_member/100),-money*(1-ch.commission_member/100)))+sum(ch.win_amount) recessionResults,max(odds) odds from tb_gdklsf_his ch where ch.betting_user_id=? and trunc(ch.betting_date)=trunc(?) group by ch.order_no,type_code,ch.periods_num";
		String bjSQL = "select ch.type_code,max(ch.betting_date) betDate,null as attribute,count(ch.order_no) count,ch.periods_num,sum(money) betMoney,ch.order_no orderNo,max(win_state) winState,sum(decode(win_state,'1',money*(ch.commission_member/100),-money*(1-ch.commission_member/100)))+sum(ch.win_amount) recessionResults,max(odds) odds from tb_bjsc_his ch where ch.betting_user_id=? and trunc(ch.betting_date)=trunc(?) group by ch.order_no,type_code,ch.periods_num";
		//add by peter for K3
		String jsSQL = "select ch.type_code,max(ch.betting_date) betDate,null as attribute,count(ch.order_no) count,ch.periods_num,sum(money) betMoney,ch.order_no orderNo,max(win_state) winState,sum(decode(win_state,'1',money*(ch.commission_member/100),-money*(1-ch.commission_member/100)))+sum(ch.win_amount) recessionResults,max(odds) odds from tb_jssb_his ch where ch.betting_user_id=? and trunc(ch.betting_date)=trunc(?) group by ch.order_no,type_code,ch.periods_num";
		//add by peter for nc
		String ncSQL = "select ch.type_code,max(ch.betting_date) betDate,attribute as attribute,count(ch.order_no) count,ch.periods_num,sum(money) betMoney,ch.order_no orderNo,max(win_state) winState,sum(decode(win_state,'1',money*(ch.commission_member/100),-money*(1-ch.commission_member/100)))+sum(ch.win_amount) recessionResults,max(odds) odds from tb_nc_his ch where ch.betting_user_id=? and trunc(ch.betting_date- interval '2' hour)=trunc(?) group by ch.order_no,type_code,ch.periods_num,attribute";
		List<CQandGDReportInfo> retList = new ArrayList<CQandGDReportInfo>();
		Object[] parameter = new Object[] { userId, beginDate };
		List<CQandGDReportInfo> cqList = jdbcTemplate.query(cqSQL, parameter,
				new CQandGDReportItemMapper());
		List<CQandGDReportInfo> gdList = jdbcTemplate.query(gdSQL, parameter,
				new CQandGDReportItemMapper());
		List<CQandGDReportInfo> bjList = jdbcTemplate.query(bjSQL, parameter,
				new CQandGDReportItemMapper());
		//add by peter for K3
		List<CQandGDReportInfo> jsList = jdbcTemplate.query(jsSQL, parameter,
				new CQandGDReportItemMapper());
		//add by peter for NC
		List<CQandGDReportInfo> ncList = jdbcTemplate.query(ncSQL, parameter,
				new CQandGDReportItemMapper());
		retList.addAll(cqList);
		retList.addAll(gdList);
		retList.addAll(bjList);
		//add by peter for K3
		retList.addAll(jsList);
		//add by peter for NC
		retList.addAll(ncList);
		return retList;
	}

	public Map getSXLBetMoney(String periodNum) {
		String sql = "select "
				+ "SUM(decode(INSTR(attribute,'SHU'),0,0,money)*rate_chief/100) SHU ,"
				+ "SUM(decode(INSTR(attribute,'HU'),0,0,money)*rate_chief/100) HU, "
				+ "SUM(decode(INSTR(attribute,'LONG'),0,0,money)*rate_chief/100) LON, "
				+ "SUM(decode(INSTR(attribute,'MA'),0,0,money)*rate_chief/100) MA, "
				+ "SUM(decode(INSTR(attribute,'HOU'),0,0,money)*rate_chief/100) HOU, "
				+ "SUM(decode(INSTR(attribute,'GOU'),0,0,money)*rate_chief/100) GOU, "
				+ "SUM(decode(INSTR(attribute,'NIU'),0,0,money)*rate_chief/100) NIU, "
				+ "SUM(decode(INSTR(attribute,'TU'),0,0,money)*rate_chief/100) TU, "
				+ "SUM(decode(INSTR(attribute,'SHE'),0,0,money)*rate_chief/100) SHE, "
				+ "SUM(decode(INSTR(attribute,'YANG'),0,0,money)*rate_chief/100) YANG, "
				+ "SUM(decode(INSTR(attribute,'JI'),0,0,money)*rate_chief/100) JI, "
				+ "SUM(decode(INSTR(attribute,'ZHU'),0,0,money)*rate_chief/100) ZHU "
				+ "from tb_hklhc_sxl where periods_num= ?";
		Object[] parameter = new Object[] { periodNum };
		return jdbcTemplate.queryForMap(sql, parameter);

	}

	public Map getWSLBetMoney(String periodNum) {
		String sql = "  select SUM(decode(INSTR(attribute,'W1'),0,0,money)) W1 ,"
				+ "SUM(decode(INSTR(attribute,'W2'),0,0,money)*rate_chief/100) W2, "
				+ "SUM(decode(INSTR(attribute,'W3'),0,0,money)*rate_chief/100) W3, "
				+ "SUM(decode(INSTR(attribute,'W4'),0,0,money)*rate_chief/100) W4, "
				+ "SUM(decode(INSTR(attribute,'W5'),0,0,money)*rate_chief/100) W5, "
				+ "SUM(decode(INSTR(attribute,'W6'),0,0,money)*rate_chief/100) W6, "
				+ "SUM(decode(INSTR(attribute,'W7'),0,0,money)*rate_chief/100) W7, "
				+ "SUM(decode(INSTR(attribute,'W8'),0,0,money)*rate_chief/100) W8, "
				+ "SUM(decode(INSTR(attribute,'W9'),0,0,money)*rate_chief/100) W9, "
				+ "SUM(decode(INSTR(attribute,'W0'),0,0,money)*rate_chief/100) W0 "
				+ "from tb_hklhc_wsl  where periods_num= ?";
		Object[] parameter = new Object[] { periodNum };
		return jdbcTemplate.queryForMap(sql, parameter);

	}

	public Map getWBZBetMoney(String periodNum) {
		String sql = "select "
				+ "SUM(decode(INSTR(attribute,'01'),0,0,money)*rate_chief/100) B1 ,"
				+ "SUM(decode(INSTR(attribute,'02'),0,0,money)*rate_chief/100) B2, "
				+ "SUM(decode(INSTR(attribute,'03'),0,0,money)*rate_chief/100) B3, "
				+ "SUM(decode(INSTR(attribute,'04'),0,0,money)*rate_chief/100) B4, "
				+ "SUM(decode(INSTR(attribute,'05'),0,0,money)*rate_chief/100) B5, "
				+ "SUM(decode(INSTR(attribute,'06'),0,0,money)*rate_chief/100) B6, "
				+ "SUM(decode(INSTR(attribute,'07'),0,0,money)*rate_chief/100) B7, "
				+ "SUM(decode(INSTR(attribute,'08'),0,0,money)*rate_chief/100) B8, "
				+ "SUM(decode(INSTR(attribute,'09'),0,0,money)*rate_chief/100) B9, "
				+ "SUM(decode(INSTR(attribute,'10'),0,0,money)*rate_chief/100) B10,"
				+ "SUM(decode(INSTR(attribute,'11'),0,0,money)*rate_chief/100) B11,"
				+ "SUM(decode(INSTR(attribute,'12'),0,0,money)*rate_chief/100) B12,"
				+ "SUM(decode(INSTR(attribute,'13'),0,0,money)*rate_chief/100) B13,"
				+ "SUM(decode(INSTR(attribute,'14'),0,0,money)*rate_chief/100) B14,"
				+ "SUM(decode(INSTR(attribute,'15'),0,0,money)*rate_chief/100) B15,"
				+ "SUM(decode(INSTR(attribute,'16'),0,0,money)*rate_chief/100) B16,"
				+ "SUM(decode(INSTR(attribute,'17'),0,0,money)*rate_chief/100) B17,"
				+ "SUM(decode(INSTR(attribute,'18'),0,0,money)*rate_chief/100) B18,"
				+ "SUM(decode(INSTR(attribute,'19'),0,0,money)*rate_chief/100) B19,"
				+ "SUM(decode(INSTR(attribute,'20'),0,0,money)*rate_chief/100) B20,"
				+ "SUM(decode(INSTR(attribute,'21'),0,0,money)*rate_chief/100) B21,"
				+ "SUM(decode(INSTR(attribute,'22'),0,0,money)*rate_chief/100) B22, "
				+ "SUM(decode(INSTR(attribute,'23'),0,0,money)*rate_chief/100) B23,"
				+ "SUM(decode(INSTR(attribute,'24'),0,0,money)*rate_chief/100) B24,"
				+ "SUM(decode(INSTR(attribute,'25'),0,0,money)*rate_chief/100) B25,"
				+ "SUM(decode(INSTR(attribute,'26'),0,0,money)*rate_chief/100) B26,"
				+ "SUM(decode(INSTR(attribute,'27'),0,0,money)*rate_chief/100) B27,"
				+ "SUM(decode(INSTR(attribute,'28'),0,0,money)*rate_chief/100) B28,"
				+ "SUM(decode(INSTR(attribute,'29'),0,0,money)*rate_chief/100) B29,"
				+ "SUM(decode(INSTR(attribute,'30'),0,0,money)*rate_chief/100) B30,"
				+ "SUM(decode(INSTR(attribute,'31'),0,0,money)*rate_chief/100) B31,"
				+ "SUM(decode(INSTR(attribute,'32'),0,0,money)*rate_chief/100) B32,"
				+ "SUM(decode(INSTR(attribute,'33'),0,0,money)*rate_chief/100) B33,"
				+ "SUM(decode(INSTR(attribute,'34'),0,0,money)*rate_chief/100) B34,"
				+ "SUM(decode(INSTR(attribute,'35'),0,0,money)*rate_chief/100) B35,"
				+ "SUM(decode(INSTR(attribute,'36'),0,0,money)*rate_chief/100) B36,"
				+ "SUM(decode(INSTR(attribute,'37'),0,0,money)*rate_chief/100) B37,"
				+ "SUM(decode(INSTR(attribute,'38'),0,0,money)*rate_chief/100) B38,"
				+ "SUM(decode(INSTR(attribute,'39'),0,0,money)*rate_chief/100) B39, "
				+ "SUM(decode(INSTR(attribute,'40'),0,0,money)*rate_chief/100) B40,"
				+ "SUM(decode(INSTR(attribute,'41'),0,0,money)*rate_chief/100) B41,"
				+ "SUM(decode(INSTR(attribute,'42'),0,0,money)*rate_chief/100) B42,"
				+ "SUM(decode(INSTR(attribute,'43'),0,0,money)*rate_chief/100) B43,"
				+ "SUM(decode(INSTR(attribute,'44'),0,0,money)*rate_chief/100) B44,"
				+ "SUM(decode(INSTR(attribute,'45'),0,0,money)*rate_chief/100) B45,"
				+ "SUM(decode(INSTR(attribute,'46'),0,0,money)*rate_chief/100) B46,"
				+ "SUM(decode(INSTR(attribute,'47'),0,0,money)*rate_chief/100) B47, "
				+ "SUM(decode(INSTR(attribute,'48'),0,0,money)*rate_chief/100) B48,"
				+ "SUM(decode(INSTR(attribute,'49'),0,0,money)*rate_chief/100) B49 "
				+ "from tb_hklhc_wbz where periods_num= ?";
		Object[] parameter = new Object[] { periodNum };
		return jdbcTemplate.queryForMap(sql, parameter);

	}

	public Map getGGBetMoney(String periodNum) {
		String sql = "select "
				+ "SUM(decode(INSTR(attribute,'ZM1_DA'),0,0,money)*rate_chief/100) ZM1_DA ,"
				+ "SUM(decode(INSTR(attribute,'ZM1_X'),0,0,money)*rate_chief/100) ZM1_X, "
				+ "SUM(decode(INSTR(attribute,'ZM1_DAN'),0,0,money)*rate_chief/100) ZM1_DAN, "
				+ "SUM(decode(INSTR(attribute,'ZM1_S'),0,0,money)*rate_chief/100) ZM1_S, "
				+ "SUM(decode(INSTR(attribute,'ZM1_RED'),0,0,money)*rate_chief/100) ZM1_RED, "
				+ "SUM(decode(INSTR(attribute,'ZM1_BLUE'),0,0,money)*rate_chief/100) ZM1_BLUE, "
				+ "SUM(decode(INSTR(attribute,'ZM1_GREEN'),0,0,money)*rate_chief/100) ZM1_GREEN,"
				+ "SUM(decode(INSTR(attribute,'ZM2_DA'),0,0,money)*rate_chief/100) ZM2_DA ,"
				+ "SUM(decode(INSTR(attribute,'ZM2_X'),0,0,money)*rate_chief/100) ZM2_X, "
				+ "SUM(decode(INSTR(attribute,'ZM2_DAN'),0,0,money)*rate_chief/100) ZM2_DAN, "
				+ "SUM(decode(INSTR(attribute,'ZM2_S'),0,0,money)*rate_chief/100) ZM2_S, "
				+ "SUM(decode(INSTR(attribute,'ZM2_RED'),0,0,money)*rate_chief/100) ZM2_RED, "
				+ "SUM(decode(INSTR(attribute,'ZM2_BLUE'),0,0,money)*rate_chief/100) ZM2_BLUE, "
				+ "SUM(decode(INSTR(attribute,'ZM2_GREEN'),0,0,money)*rate_chief/100) ZM2_GREEN ,"
				+ "SUM(decode(INSTR(attribute,'ZM3_DA'),0,0,money)*rate_chief/100) ZM3_DA ,"
				+ "SUM(decode(INSTR(attribute,'ZM3_X'),0,0,money)*rate_chief/100) ZM3_X, "
				+ "SUM(decode(INSTR(attribute,'ZM3_DAN'),0,0,money)*rate_chief/100) ZM3_DAN, "
				+ "SUM(decode(INSTR(attribute,'ZM3_S'),0,0,money)*rate_chief/100) ZM3_S, "
				+ "SUM(decode(INSTR(attribute,'ZM3_RED'),0,0,money)*rate_chief/100) ZM3_RED, "
				+ "SUM(decode(INSTR(attribute,'ZM3_BLUE'),0,0,money)*rate_chief/100) ZM3_BLUE, "
				+ "SUM(decode(INSTR(attribute,'ZM3_GREEN'),0,0,money)*rate_chief/100) ZM3_GREEN, "
				+ "SUM(decode(INSTR(attribute,'ZM4_DA'),0,0,money)*rate_chief/100) ZM4_DA ,"
				+ "SUM(decode(INSTR(attribute,'ZM4_X'),0,0,money)*rate_chief/100) ZM4_X, "
				+ "SUM(decode(INSTR(attribute,'ZM4_DAN'),0,0,money)*rate_chief/100) ZM4_DAN, "
				+ "SUM(decode(INSTR(attribute,'ZM4_S'),0,0,money)*rate_chief/100) ZM4_S, "
				+ "SUM(decode(INSTR(attribute,'ZM4_RED'),0,0,money)*rate_chief/100) ZM4_RED, "
				+ "SUM(decode(INSTR(attribute,'ZM4_BLUE'),0,0,money)*rate_chief/100) ZM4_BLUE, "
				+ "SUM(decode(INSTR(attribute,'ZM4_GREEN'),0,0,money)*rate_chief/100) ZM4_GREEN, "
				+ "SUM(decode(INSTR(attribute,'ZM5_DA'),0,0,money)*rate_chief/100) ZM5_DA ,"
				+ "SUM(decode(INSTR(attribute,'ZM5_X'),0,0,money)*rate_chief/100) ZM5_X, "
				+ "SUM(decode(INSTR(attribute,'ZM5_DAN'),0,0,money)*rate_chief/100) ZM5_DAN, "
				+ "SUM(decode(INSTR(attribute,'ZM5_S'),0,0,money)*rate_chief/100) ZM5_S, "
				+ "SUM(decode(INSTR(attribute,'ZM5_RED'),0,0,money)*rate_chief/100) ZM5_RED, "
				+ "SUM(decode(INSTR(attribute,'ZM5_BLUE'),0,0,money)*rate_chief/100) ZM5_BLUE, "
				+ "SUM(decode(INSTR(attribute,'ZM5_GREEN'),0,0,money)*rate_chief/100) ZM5_GREEN, "
				+ "SUM(decode(INSTR(attribute,'ZM6_DA'),0,0,money)*rate_chief/100) ZM6_DA ,"
				+ "SUM(decode(INSTR(attribute,'ZM6_X'),0,0,money)*rate_chief/100) ZM6_X, "
				+ "SUM(decode(INSTR(attribute,'ZM6_DAN'),0,0,money)*rate_chief/100) ZM6_DAN, "
				+ "SUM(decode(INSTR(attribute,'ZM6_S'),0,0,money)*rate_chief/100) ZM6_S, "
				+ "SUM(decode(INSTR(attribute,'ZM6_RED'),0,0,money)*rate_chief/100) ZM6_RED, "
				+ "SUM(decode(INSTR(attribute,'ZM6_BLUE'),0,0,money)*rate_chief/100) ZM6_BLUE, "
				+ "SUM(decode(INSTR(attribute,'ZM6_GREEN'),0,0,money)*rate_chief/100) ZM6_GREEN  "
				+ "from tb_hklhc_gg where periods_num= ?";
		Object[] parameter = new Object[] { periodNum };
		return jdbcTemplate.queryForMap(sql, parameter);

	}

	@Override
	public List<CQandGDReportInfo> queryHKReportDao(Long userId, Date beginDate) {
		String hkSQL = "select ch.type_code,max(ch.betting_date) betDate,max(attribute) attribute,ch.periods_num,sum(money) betMoney,count(ch.order_no) count,ch.order_no orderNo,max(win_state) winState,sum(decode(win_state,'1',money*(ch.commission_member/100),-money*(1-ch.commission_member/100)))+sum(ch.win_amount) recessionResults,max(odds) odds,max(odds2) odds2,max(select_odds) selectOdds from tb_hklhc_his ch where ch.betting_user_id=? and trunc(ch.betting_date)=trunc(?) group by ch.order_no,type_code,ch.periods_num";

		List<CQandGDReportInfo> retList = new ArrayList<CQandGDReportInfo>();
		Object[] parameter = new Object[] { userId, beginDate };
		List<CQandGDReportInfo> hkList = jdbcTemplate.query(hkSQL, parameter,
				new HKReportItemMapper());
		retList.addAll(hkList);
		return retList;
	}

	public List queryBranchstaffUnSettledReport() {

		String sql = "select a.stockholderstaff,max(b.account) account,max(b.chs_name) CHName, count(*) as count,sum(money) as totalMoney,sum(money*a.commission_member) as totalCommission,avg(a.rate_branch) as rate  from tb_gdklsf_ball_first a, tb_frame_manager_staff b where  a.branchstaff=5360 and a.stockholderstaff=b.id group by a.stockholderstaff ";

		return null;

	}

	@Override
	public List<PeriodsNumVo> queryPeriodsAllOrderTime() {
		String sql = "  select * from (select * from ( "
				+ " SELECT * FROM (select 'CQ_'||periods_num p1, '重慶時時彩'||periods_num||'期' p2,c.lottery_time lotteryTime,c.create_time createTime from  tb_cQSSC_periods_info c "
				+ " where to_char(c.lottery_time,'YYYY-MM-DD')=to_char(sysdate,'YYYY-MM-DD') and c.lottery_time <=(select lottery_time from tb_cQSSC_periods_info c2 where c2.open_quot_time<sysdate and c2.lottery_time>=sysdate) "
				+ " order by c.lottery_time desc ) where rownum <50 union "
				+ " SELECT * FROM (select  'BJ_'||periods_num p1, '北京賽車(PK10)'||to_char(sysdate,'MMDD')||' '||periods_num||'期' p2,b.lottery_time lotteryTime,b.create_time createTime from  tb_bjsc_periods_info b "
				+ " where to_char(b.lottery_time,'YYYY-MM-DD')=to_char(sysdate,'YYYY-MM-DD') and b.lottery_time <=(select lottery_time from tb_bjsc_periods_info b2 where b2.open_quot_time<sysdate and b2.lottery_time>=sysdate) "
				+ " order by b.lottery_time desc ) where rownum <50 union "
				+ " SELECT * FROM (select  'K3_'||periods_num p1, '江苏骰寶(K3)'||' '||periods_num||'期' p2,n.lottery_time lotteryTime,n.create_time createTime from  tb_jssb_periods_info n "
				+ " where to_char(n.lottery_time,'YYYY-MM-DD')=to_char(sysdate,'YYYY-MM-DD') and n.lottery_time <=(select lottery_time from tb_jssb_periods_info n2 where n2.open_quot_time<sysdate and n2.lottery_time>=sysdate) "
				+ " order by n.lottery_time desc ) where rownum <50 union "
				+ " SELECT * FROM (select  'NC_'||periods_num p1, '幸运农场'||' '||periods_num||'期' p2,n.lottery_time lotteryTime,n.create_time createTime from  tb_nc_periods_info n "
				+ " where to_char(n.lottery_time,'YYYY-MM-DD')=to_char(sysdate,'YYYY-MM-DD') and n.lottery_time <=(select lottery_time from tb_nc_periods_info n2 where n2.open_quot_time<sysdate and n2.lottery_time>=sysdate) "
				+ " order by n.lottery_time desc ) where rownum <50 union "
				+ " SELECT * FROM (select 'GD_'||periods_num p1, '廣東快樂十分'||periods_num||'期' p2,g.lottery_time lotteryTime,g.create_time createTime from  tb_gdklsf_periods_info g "
				+ " where to_char(g.lottery_time,'YYYY-MM-DD')=to_char(sysdate,'YYYY-MM-DD') and g.lottery_time <=(select lottery_time from tb_gdklsf_periods_info g2 where g2.open_quot_time<sysdate and g2.lottery_time>=sysdate) "
				+ " order by g.lottery_time desc ) where rownum <50 "
				+ " )  t order by t.lotteryTime desc) where rownum <=50 ";

		String sql2 = "select * from (select * from ("
				+ " SELECT * FROM (select 'CQ_'||periods_num p1, '重慶時時彩'||periods_num||'期' p2,c.lottery_time lotteryTime,c.create_time createTime from  tb_cQSSC_periods_info c "
				+ " where to_char(c.lottery_time,'YYYY-MM-DD')=to_char(sysdate,'YYYY-MM-DD') and c.lottery_time <=(select lottery_time from tb_cQSSC_periods_info c2 where c2.open_quot_time<sysdate and c2.lottery_time>=sysdate) "
				+ " order by c.lottery_time desc ) where rownum <50 "
				+ " union "
				+ " SELECT * FROM (select  'NC_'||periods_num p1, '幸运农场'||to_char(sysdate-1,'MMDD')||' '||periods_num||'期' p2,b.lottery_time lotteryTime,b.create_time createTime from  tb_nc_periods_info b "
				+ " where to_char(b.lottery_time,'YYYY-MM-DD')=to_char(sysdate-1,'YYYY-MM-DD') "
				+ " order by b.lottery_time desc ) where rownum <50"
				+ " union "
				+ " SELECT * FROM (select  'BJ_'||periods_num p1, '北京賽車(PK10)'||to_char(sysdate-1,'MMDD')||' '||periods_num||'期' p2,b.lottery_time lotteryTime,b.create_time createTime from  tb_bjsc_periods_info b "
				+ " where to_char(b.lottery_time,'YYYY-MM-DD')=to_char(sysdate-1,'YYYY-MM-DD') "
				+ " order by b.lottery_time desc ) where rownum <50"
				/*+ " union "
				+ " SELECT * FROM (select 'CQ_'||periods_num p1, '重慶時時彩'||periods_num||'期' p2,c.lottery_time lotteryTime,c.create_time createTime from  tb_cQSSC_periods_info c "
				+ " where to_char(c.lottery_time,'YYYY-MM-DD')=to_char(sysdate-1,'YYYY-MM-DD') "
				+ " order by c.lottery_time desc ) where rownum <50 "*/
				+ " )  t order by t.lotteryTime desc  ) where rownum <=50 ";
		Calendar c = Calendar.getInstance();
		if (c.getTime().getHours() >= 0 && c.getTime().getHours() <= 2) { // 如果是凌晨0-2点时
																			// 当前只有重庆开盘
																			// 加上北京
																			// 昨天的
			sql = sql2;
		}
		List<PeriodsNumVo> list = jdbcTemplate.query(sql, new Object[] {},
				new PeriodsNumItemMapper());
		return list;
	}
	
	/**
	 * 
	 * @param tableName
	 * @param attribute 如果是历史表是传"attribute",如果是投注表就根据条件传
	 * @param whereSQL
	 * @return
	 */
	public String getHisSql(String tableName, String attribute, String petColumn, String whereSQL, String petTable) {
		String sql = "select billType, lotteryType,orderNo, -money*count+winAmount*count as winMoney,typeCode,money,"
				+ "odds, periodsNum,plate,bettingDate, attribute,count," + " (select account from " + petTable
				+ " where id = bettingUserId) as bettingUserName,"
				+ " (select account from tb_frame_manager_staff  where id = CHIEFSTAFF) as CHIEFSTAFF,"
				+ " (select account from tb_frame_manager_staff where id = BRANCHSTAFF) as BRANCHSTAFF,"
				+ " (select account from tb_frame_manager_staff where id = STOCKHOLDERSTAFF) as STOCKHOLDERSTAFF,"
				+ " (select account from tb_frame_manager_staff where id = GENAGENSTAFF) as GENAGENSTAFF,"
				+ " (select account from tb_frame_manager_staff where id = AGENTSTAFF) as AGENTSTAFF," + " (select  user_type from " + petTable
				+ " where id=bettingUserId) as bettingUserType, "
				+ " RATE_CHIEF,RATE_BRANCH, RATE_STOCKHOLDER, RATE_GEN_AGENT, RATE_AGENT,bettingUserID,memberCommission,winState from("
				+ "select ";
				
				//add by peter
				if(tableName.toUpperCase().indexOf("TB_REPLENISH")!=-1){
					//补货
					sql += "0";
				}else{
					//会员
					sql += "1";
				}
		sql     += " as billType, ";

				//add by peter
				if (tableName.toUpperCase().indexOf("HIS") != -1) {
					//历史表，已结算
					sql += "0";
				} else {
					//投注表，未结算
					sql += "1";
				}
		sql 	+= " as lotteryType, MAX(win_state) as winState, MAX(type_code) as typeCode," + "SUM(money) as money, MAX(" + petColumn
				+ ") as bettingUserId, MAX(periods_num) as periodsNum,"
				+ "sum(case when win_state=1 then money+win_amount when win_state=2 then win_amount "
				+ "when win_state=9 then win_amount else 0 end ) AS winAmount,"
				+ "MAX(plate) as plate, MAX(betting_date) as bettingDate, MAX(odds) as odds, MAX(order_no) as orderNo," + "MAX(" + attribute
				+ ") as attribute ,count(*) AS count, MAX(CHIEFSTAFF) as CHIEFSTAFF,MAX(BRANCHSTAFF) as BRANCHSTAFF,"
				+ "MAX(STOCKHOLDERSTAFF) as STOCKHOLDERSTAFF, MAX(GENAGENSTAFF) as GENAGENSTAFF, MAX(AGENTSTAFF) as AGENTSTAFF,"
				+ " MAX(RATE_CHIEF) AS RATE_CHIEF,MAX(RATE_BRANCH) AS RATE_BRANCH,MAX(RATE_GEN_AGENT) AS RATE_GEN_AGENT,"
				+ " MAX(RATE_STOCKHOLDER) AS RATE_STOCKHOLDER, MAX(RATE_AGENT) AS RATE_AGENT,MAX(COMMISSION_MEMBER) AS memberCommission " + " from "
				+ tableName + " r  where  1=1  " + whereSQL;
		return sql;
	}

	public IPlayTypeDao getPlayTypeDao() {
		return playTypeDao;
	}

	public void setPlayTypeDao(IPlayTypeDao playTypeDao) {
		this.playTypeDao = playTypeDao;
	}

	public IMemberStaffLogic getMemberStaffLogic() {
		return memberStaffLogic;
	}

	public void setMemberStaffLogic(IMemberStaffLogic memberStaffLogic) {
		this.memberStaffLogic = memberStaffLogic;
	}

	public IManagerStaffLogic getManagerStaffLogic() {
		return managerStaffLogic;
	}

	public void setManagerStaffLogic(IManagerStaffLogic managerStaffLogic) {
		this.managerStaffLogic = managerStaffLogic;
	}
	
	/*@Override
	public void deleteBetInfoByOrderNum(String orderNo,String tableName,String periodsNum){
		String sql = "DELETE FROM  " + tableName + "  WHERE ORDER_NO=? AND PERIODS_NUM=?";
		Object[] args = new Object[] { orderNo, periodsNum};
		jdbcTemplate.update(sql, args);
	}*/
	
	@Override
	public void cancelBetInfoByOrderNum(String orderNo,String tableName,String periodsNum){
		String sql = "UPDATE " + tableName + " SET WIN_STATE=4  WHERE ORDER_NO=? AND PERIODS_NUM=?";
		Object[] args = new Object[] { orderNo, periodsNum};
		jdbcTemplate.update(sql, args);
	}

	@Override
	public void updateReplenshNotWinBetResultHis(String periodNum, String playType,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======updateReplenshNotWinBetResultHis开始执行SCHEME为:"+scheme+"==============");
		
		String sql = "update "+scheme+"tb_replenish_his set win_state=2 ,win_amount=0 where type_code like '" + playType + "%' and win_state=0 and periods_num=? ";
		Object[] parameters = new Object[] { periodNum };
		jdbcTemplate.update(sql, parameters);

	}

	@Override
	public void updateReplenshBetResultHis(String winState, String id, String winAmount,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		
		log.info("=======updateReplenshBetResultHis开始执行SCHEME为:"+scheme+"==============");
		String sql = "update "+scheme+"tb_replenish_his set win_state=? ,win_amount=?, update_date=sysdate where id=?";
		Object[] parameters = new Object[] { winState, winAmount, id };
		jdbcTemplate.update(sql, parameters);

	}
	
	@Override
	public List getReplenishDataHis(String periodNum, String playType,
			Integer startNum, Integer endNum,String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		log.info("=======getReplenishDataHis开始执行SCHEME为:"+scheme+"==============");
		String replenishSql = "select id,order_no orderNo,type_code as typeCode,money as money,periods_num, "+
		"win_state as winState,odds as odds,attribute as attribute ,odds2 as odds2,select_odds as selectedOdds from "+
				scheme+"tb_replenish_his where type_code like '"
				+ playType + "%' AND PERIODS_NUM=? ";

		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		paginationSQL.append(replenishSql);
		paginationSQL.append("　) temp where ROWNUM <= "
				+ String.valueOf(endNum));
		paginationSQL.append(" ) WHERE　num > " + String.valueOf(startNum));
		Object[] parameters = new Object[] { periodNum };
		List betlist = jdbcTemplate.query(paginationSQL.toString(), parameters,
				new ReplenshResultItemMapper());

		return betlist;

	}
	public ICheckDao getCheckDao() {
		return checkDao;
	}
	public void setCheckDao(ICheckDao checkDao) {
		this.checkDao = checkDao;
	}
}

class PeriodsNumItemMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		PeriodsNumVo vo = new PeriodsNumVo();
		vo.setPeriodsNum1(rs.getString("p1"));
		vo.setPeriodsNum2(rs.getString("p2"));
		vo.setLotteryTime(rs.getString("lotteryTime"));
		vo.setCreateTime(rs.getString("createTime"));
		return vo;
	}

}

class CommissonMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserCommission userCommision = new UserCommission();
		userCommision.setCommissionA(rs.getDouble("commission_a"));
		userCommision.setCommissionB(rs.getDouble("commission_b"));
		userCommision.setCommissionC(rs.getDouble("commission_c"));
		userCommision.setUserId(rs.getLong("user_id"));
		userCommision.setUserType(rs.getString("user_type"));

		return userCommision;
	}

}

class LotteryResultItemMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BaseBet item = new BaseBet();
		item.setId(rs.getInt("id"));
		item.setTypeCode(rs.getString("typeCode"));
		item.setAttribute(rs.getString("attribute"));
		item.setOrderNo(rs.getString("orderNo"));
		item.setWinState(rs.getString("winState"));
		item.setMoney(rs.getInt("money"));
		item.setBettingUserId(rs.getLong("bettingUserId"));
		item.setChiefStaff(rs.getLong("chiefstaff"));
		item.setBranchStaff(rs.getLong("branchstaff"));
		item.setStockholderStaff(rs.getLong("stockholderstaff"));
		item.setGenAgenStaff(rs.getLong("genagenstaff"));
		item.setAgentStaff(rs.getLong("agentstaff"));
		item.setOdds2(rs.getBigDecimal("odds2"));
		item.setOdds(rs.getBigDecimal("odds"));
		item.setCompoundNum(rs.getInt("compoundNum"));
		item.setSplitAttribute(rs.getString("splitAttribute"));
		item.setSelectedOdds(rs.getString("selectOdds"));
		return item;
	}

}

class ReplenshResultItemMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BaseBet item = new BaseBet();
		item.setId(rs.getInt("id"));
		item.setTypeCode(rs.getString("typeCode"));

		item.setAttribute(rs.getString("attribute"));
		item.setOrderNo(rs.getString("orderNo"));
		item.setWinState(rs.getString("winState"));
		item.setMoney(rs.getInt("money"));

		item.setOdds2(rs.getBigDecimal("odds2"));
		item.setOdds(rs.getBigDecimal("odds"));
		item.setSelectedOdds(rs.getString("selectedOdds"));

		return item;
	}

}

class BetItemMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BaseBet item = new BaseBet();
		// item.setId(rs.getInt("id"));
		item.setBettingDate(rs.getTimestamp("bettingDate"));
		item.setTypeCode(rs.getString("typeCode"));
		item.setAttribute(rs.getString("attribute"));
		item.setOrderNo(rs.getString("orderNo"));
		item.setPeriodsNum(rs.getString("periodsNum"));
		item.setBettingUserId(rs.getLong("bettingUserId"));
		item.setMoney(rs.getInt("money"));
		item.setOdds(rs.getBigDecimal("odds"));
		item.setPlate(rs.getString("plate"));
		item.setFinalTypeName(rs.getString("finalTypeName"));
		item.setSubTypeName(rs.getString("subTypeName"));
		item.setWinState(rs.getString("winState"));
		item.setCompoundNum(rs.getInt("compoundNum"));

		item.setOdds2(rs.getBigDecimal("odds2"));

		return item;
	}

}

class GDBetDetailItemMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BaseBet item = new BaseBet();
		// item.setId(rs.getInt("id"));
		item.setBettingDate(rs.getTimestamp("bettingDate"));
		item.setTypeCode(rs.getString("typeCode"));
		item.setAttribute(rs.getString("attribute"));
		item.setOrderNo(rs.getString("orderNo"));
		item.setPeriodsNum(rs.getString("periodsNum"));
		item.setBettingUserId(rs.getLong("bettingUserId"));
		item.setMoney(rs.getInt("money"));
		item.setOdds(rs.getBigDecimal("odds"));
		item.setPlate(rs.getString("plate"));
		item.setCount(rs.getInt("count"));
		item.setWinState(rs.getString("winState"));
		return item;
	}
}

class AdminBetDetailItemMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BaseBet item = new BaseBet();
		// item.setId(rs.getInt("id"));
		item.setBettingDate(rs.getTimestamp("bettingDate"));
		item.setTypeCode(rs.getString("typeCode"));
		item.setAttribute(rs.getString("attribute"));
		item.setOrderNo(rs.getString("orderNo"));
		item.setPeriodsNum(rs.getString("periodsNum"));
		item.setMoney(rs.getInt("money"));
		item.setWinMoney(rs.getBigDecimal("winMoney"));
		item.setOdds(rs.getBigDecimal("odds"));
		item.setPlate(rs.getString("plate"));
		item.setCount(rs.getInt("count"));
		item.setAgentStaffName(rs.getString("AGENTSTAFF"));
		item.setGenAgenStaffName(rs.getString("GENAGENSTAFF"));
		item.setBranchStaffName(rs.getString("BRANCHSTAFF"));
		item.setChiefStaffName(rs.getString("CHIEFSTAFF"));
		item.setStockholderStaffName(rs.getString("STOCKHOLDERSTAFF"));
		item.setBettingUserName(rs.getString("bettingUserName"));
		item.setChiefRate(rs.getInt("RATE_CHIEF"));
		item.setStockHolderRate(rs.getInt("RATE_STOCKHOLDER"));
		item.setBranchRate(rs.getInt("RATE_BRANCH"));
		item.setAgentStaffRate(rs.getInt("RATE_AGENT"));
		item.setGenAgenRate(rs.getInt("RATE_GEN_AGENT"));
		item.setBillType(rs.getString("billType"));
		item.setLotteryType(rs.getString("lotteryType"));
		item.setBettingUserType(rs.getString("bettingUserType"));
		//add by peter for bet delete function
		item.setBettingUserId(rs.getLong("bettingUserID"));
		item.setMemberCommission(rs.getDouble("memberCommission"));
		item.setWinState(rs.getString("winState"));
		// ,RATE_CHIEF,RATE_BRANCH,RATE_GEN_AGENT,RATE_STOCKHOLDER ,RATE_AGENT
		// CHIEFSTAFF ,BRANCHSTAFF,STOCKHOLDERSTAFF,GENAGENSTAFF,AGENTSTAFF
		return item;
	}
}

class BJSCBetDetailItemMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BaseBet item = new BaseBet();
		// item.setId(rs.getInt("id"));
		item.setBettingDate(rs.getTimestamp("bettingDate"));
		item.setTypeCode(rs.getString("typeCode"));
		item.setAttribute(rs.getString("attribute"));
		item.setOrderNo(rs.getString("orderNo"));
		item.setPeriodsNum(rs.getString("periodsNum"));
		item.setBettingUserId(rs.getLong("bettingUserId"));
		item.setMoney(rs.getInt("money"));
		item.setOdds(rs.getBigDecimal("odds"));
		item.setPlate(rs.getString("plate"));
		item.setCount(rs.getInt("count"));
		item.setWinState(rs.getString("winState"));
		return item;
	}

}

class HKBetDetailItemMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BaseBet item = new BaseBet();
		// item.setId(rs.getInt("id"));
		item.setBettingDate(rs.getTimestamp("bettingDate"));
		item.setTypeCode(rs.getString("typeCode"));
		item.setAttribute(rs.getString("attribute"));
		item.setOrderNo(rs.getString("orderNo"));
		item.setPeriodsNum(rs.getString("periodsNum"));
		item.setBettingUserId(rs.getLong("bettingUserId"));
		item.setMoney(rs.getInt("money"));
		item.setOdds(rs.getBigDecimal("odds"));
		item.setOdds2(rs.getBigDecimal("odds2"));
		item.setPlate(rs.getString("plate"));
		item.setCount(rs.getInt("count"));
		item.setSelectedOdds(rs.getString("selectOdds"));
		item.setWinState(rs.getString("winState"));
		return item;
	}

}

class BalanceInfoItemMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BalanceInfo item = new BalanceInfo();
		Date betDate = rs.getDate("betDate");
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		item.setTransactionType(sm.format(betDate));
		item.setWagering(rs.getDouble("money"));
		item.setWagersOn(rs.getInt("count"));
		item.setBunkoResults(rs.getDouble("winMoney"));
		item.setRecession(rs.getDouble("commission"));
		return item;
	}

}

class BetDetailItemMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BaseBet item = new BaseBet();
		item.setBettingDate(rs.getTimestamp("bettingDate"));
		item.setTypeCode(rs.getString("typeCode"));
		item.setAttribute(rs.getString("attribute"));
		item.setOrderNo(rs.getString("orderNo"));
		item.setPeriodsNum(rs.getString("periodsNum"));
		item.setBettingUserId(rs.getLong("bettingUserId"));
		item.setMoney(rs.getInt("money"));
		item.setOdds(rs.getBigDecimal("odds"));
		item.setPlate(rs.getString("plate"));
		item.setCount(rs.getInt("count"));
		return item;

	}

}

class CQandGDReportItemMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

		CQandGDReportInfo reportInfo = new CQandGDReportInfo();
		Date betDate = rs.getTimestamp("betDate");
		reportInfo.setBettingDate(betDate);
		reportInfo.setAttribute(rs.getString("attribute"));
		reportInfo.setMoney(rs.getInt("betMoney"));
		reportInfo.setOrderNo(rs.getString("orderNo"));
		reportInfo.setPeriodsNum(rs.getString("periods_num"));
		reportInfo.setRecessionResults(rs.getDouble("recessionResults"));
		reportInfo.setTypeCode(rs.getString("type_code"));
		reportInfo.setOdds(rs.getDouble("odds"));
		reportInfo.setWinState(rs.getString("winState"));
		reportInfo.setCount(rs.getInt("count"));
		return reportInfo;
	}

}

class HKReportItemMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

		CQandGDReportInfo reportInfo = new CQandGDReportInfo();
		Date betDate = rs.getTimestamp("betDate");
		reportInfo.setBettingDate(betDate);
		reportInfo.setAttribute(rs.getString("attribute"));
		reportInfo.setMoney(rs.getInt("betMoney"));
		reportInfo.setOrderNo(rs.getString("orderNo"));
		reportInfo.setPeriodsNum(rs.getString("periods_num"));
		reportInfo.setRecessionResults(rs.getDouble("recessionResults"));
		reportInfo.setTypeCode(rs.getString("type_code"));
		reportInfo.setOdds(rs.getDouble("odds"));
		reportInfo.setOddsTwo(rs.getDouble("odds2"));
		reportInfo.setWinState(rs.getString("winState"));
		reportInfo.setSelectOdds(rs.getString("selectOdds"));
		reportInfo.setCount(rs.getInt("count"));
		return reportInfo;
	}

}
class AllMemberBetMoneyMapper implements RowMapper{
	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BaseBet bet=new BaseBet();
		bet.setMoney(rs.getInt("money"));  //总投注金额
		bet.setBettingUserId(rs.getLong("bettingUserId"));  //投注用户ID
		return bet;
	}
}
class BetCompare implements Comparator<BaseBet>
{

	@Override
	public int compare(BaseBet o1, BaseBet o2) {
		try{
		String typeCode1=o1.getPlayType();
		String typeCode2=o2.getPlayType();
		PlayType playType1=PlayTypeUtils.getPlayType(typeCode1);
		PlayType playType2=PlayTypeUtils.getPlayType(typeCode2);
		return playType2.getDisplayOrder()-playType1.getDisplayOrder();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
		}
		return 0;
}

}
class OrderBetCompare implements Comparator<BaseBet>
{
@Override
public int compare(BaseBet o1, BaseBet o2) {
			try{
			
			return Integer.valueOf(o2.getOrderNo())-Integer.valueOf(o1.getOrderNo());
			}
			catch(Exception e)
			{
				//e.printStackTrace();
			}
			return 0;
		}
		
	}
