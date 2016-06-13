package com.npc.lottery.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.npc.lottery.common.Constant;
import com.npc.lottery.member.entity.BalanceInfo;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.CQandGDReportInfo;
import com.npc.lottery.util.Page;

@Repository
public class BettingFrontDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Map<String, BigDecimal> getUseritemQuotasMoneyMap(String playType, String periodNum, Long userId, String scheme) {
		Map<String, BigDecimal> userItenMoneyMap = new HashMap<String, BigDecimal>();
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

			String sql = " select type_code typeCode ,sum(money) totelMoney from " + scheme + "." + tableName + " a where  a.periods_num=? and a.betting_user_id=? group by type_code";

			Object[] parameters = new Object[] { periodNum, userId };
			List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, parameters);
			if (!CollectionUtils.isEmpty(resultList)) {
				for (Map<String, Object> resultMap : resultList) {
					String typeCode = (String) resultMap.get("typeCode");
					BigDecimal totelMoney = (BigDecimal) resultMap.get("totelMoney");
					userItenMoneyMap.put(typeCode, totelMoney);
				}
			}

		}
		return userItenMoneyMap;
	}

	public Map<String, BigDecimal> getTotalCommissionMoneyMap(String shopCode, String playType, String scheme) {
		Map<String, BigDecimal> userItenMoneyMap = new HashMap<String, BigDecimal>();
		String sql = "select type_code,sum(money_amount) totelMoney from " + scheme + ".tb_play_amount a where shops_Code=? and type_code like ? group by type_code";
		Object[] parameter = new Object[] { shopCode, playType };
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

	public double getTodayWinMoney(Long userId, String scheme) {
		String sqlCQ = "select sum(decode(k.win_state,1,k.win_amount+k.money,0))+sum(money*(k.commission_member/100-1))  from " + scheme
		        + ".view_cqssc_his_today k where  trunc(k.betting_date- interval '2' hour)=trunc(sysdate- interval '2' hour) and k.betting_user_id=? and win_state<=2 and win_state>=1";
		String sqlGD = "select sum(decode(k.win_state,1,k.win_amount+k.money,0))+sum(money*(k.commission_member/100-1))  from " + scheme
		        + ".view_gdklsf_his_today k where  trunc(k.betting_date- interval '2' hour)=trunc(sysdate-interval '2' hour) and k.betting_user_id=? and win_state<=2 and win_state>=1";
		String sqlHK = "select sum(decode(k.win_state,1,k.win_amount+k.money,0))+sum(money*(k.commission_member/100-1))  from " + scheme
		        + ".view_bjsc_his_today k where  trunc(k.betting_date- interval '2' hour)=trunc(sysdate-interval '2' hour) and k.betting_user_id=? and win_state<=2 and win_state>=1";
		// add by peter for K3
		String sqlJS = "select sum(decode(k.win_state,1,k.win_amount+k.money,0))+sum(money*(k.commission_member/100-1))  from " + scheme
		        + ".view_jssb_his_today k where  trunc(k.betting_date- interval '2' hour)=trunc(sysdate-interval '2' hour) and k.betting_user_id=? and win_state<=2 and win_state>=1";
		// add by peter for NC
		String sqlNC = "select sum(decode(k.win_state,1,k.win_amount+k.money,0))+sum(money*(k.commission_member/100-1))  from " + scheme
		        + ".tb_nc_his k where  trunc(k.betting_date- interval '2' hour)=trunc(sysdate-interval '2' hour) and k.betting_user_id=? and win_state<=2 and win_state>=1";
		Object[] parameter = new Object[] { userId };
		Double cqWin = (Double) jdbcTemplate.queryForObject(sqlCQ, parameter, Double.class);
		Double gdWin = (Double) jdbcTemplate.queryForObject(sqlGD, parameter, Double.class);
		Double hkWin = (Double) jdbcTemplate.queryForObject(sqlHK, parameter, Double.class);
		// add by peter for K3
		Double jsWin = (Double) jdbcTemplate.queryForObject(sqlJS, parameter, Double.class);
		// add by peter for NC
		Double ncWin = (Double) jdbcTemplate.queryForObject(sqlNC, parameter, Double.class);
		if (cqWin == null)
			cqWin = 0d;
		if (gdWin == null)
			gdWin = 0d;
		if (hkWin == null)
			hkWin = 0d;
		// add by peter for K3
		if (jsWin == null)
			jsWin = 0d;
		// add by peter for NC
		if (ncWin == null)
			ncWin = 0d;
		return cqWin.doubleValue() + gdWin.doubleValue() + hkWin.doubleValue() + jsWin.doubleValue() + ncWin.doubleValue();

	}

	public Page getUserBetDetail(Page page, Long userId, String scheme) {

		List<BaseBet> retList = new ArrayList<BaseBet>();
		for (int i = 0; i < Constant.GDKLSF_TABLE_LIST.length; i++) {
			String tableName = Constant.GDKLSF_TABLE_LIST[i];
			String attribute = "null";
			if (tableName.equals(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME))
				attribute = "attribute";
			String sql = " select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,"
			        + attribute
			        + " as attribute ,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
			        + " from "
			        + scheme
			        + "."
			        + tableName
			        + " where betting_user_id= ? ";
			Object[] parameters = new Object[] { userId };
			sql = "select * from (" + sql + ") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters, new BetDetailItemMapper());
			retList.addAll(betlist);

		}

		for (int i = 0; i < Constant.CQSSC_TABLE_LIST.length; i++) {
			String tableName = Constant.CQSSC_TABLE_LIST[i];
			String sql = " select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,null as attribute,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
			        + "  from " + scheme + "." + tableName + " where betting_user_id= ? ";
			Object[] parameters = new Object[] { userId };
			sql = "select * from (" + sql + ") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters, new BetDetailItemMapper());

			retList.addAll(betlist);
		}

		for (int i = 0; i < Constant.BJSC_TABLE_LIST.length; i++) {
			String tableName = Constant.BJSC_TABLE_LIST[i];
			String sql = " select win_state as winState, '"
			        + tableName
			        + "' as tableName, type_code as typeCode,money as money,betting_user_id as bettingUserId,periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo,null as attribute,rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
			        + " from " + scheme + "." + tableName + " where betting_user_id= ? ";
			Object[] parameters = new Object[] { userId };
			sql = "select * from (" + sql + ") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters, new BetDetailItemMapper());

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
			        + " from " + scheme + "." + tableName + " where betting_user_id= ? ";
			Object[] parameters = new Object[] { userId };
			sql = "select * from (" + sql + ") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters, new BetDetailItemMapper());

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
			        + scheme
			        + "."
			        + tableName
			        + " where betting_user_id= ? ";
			Object[] parameters = new Object[] { userId };
			sql = "select * from (" + sql + ") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters, new BetDetailItemMapper());
			retList.addAll(betlist);

		}
		/*
		 * add by peter for NC end
		 */

		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		Collections.sort(retList, new Comparator<BaseBet>() {

			@Override
			public int compare(BaseBet o1, BaseBet o2) {
				try {

					return Integer.valueOf(o2.getOrderNo()) - Integer.valueOf(o1.getOrderNo());
				} catch (Exception e) {
				}
				return 0;
			}
		});

		Integer money = 0;
		double winMoney = 0d;
		for (int i = 0; i < retList.size(); i++) {
			Integer betMoney = retList.get(i).getMoney();
			BigDecimal odd = retList.get(i).getOdds();
			// fixed by peter
			int count = retList.get(i).getCount();
			money += betMoney * count;
			winMoney += betMoney * (odd.doubleValue() - 1) * count;

		}
		page.setTotalCount(retList.size());
		retList = retList.subList(first, last);
		page.setResult(retList);

		page.setTotal1(Double.valueOf(money));
		page.setTotal2(winMoney);
		return page;

	}

	public List<CQandGDReportInfo> getCQandGDReportDao(Long userId, Date beginDate, String scheme) {
		String cqSQL = "select ch.type_code,max(ch.betting_date) betDate,null as attribute,count(ch.order_no) count,ch.periods_num,sum(money) betMoney,ch.order_no orderNo,max(win_state) winState,sum(decode(win_state,'1',money*(ch.commission_member/100),-money*(1-ch.commission_member/100)))+sum(ch.win_amount) recessionResults,max(odds) odds from "
		        + scheme + ".tb_cqssc_his ch  where ch.betting_user_id=? and trunc(ch.betting_date- interval '2' hour)=trunc(?) group by ch.order_no,type_code,ch.periods_num";
		String gdSQL = "select ch.type_code,max(ch.betting_date) betDate,max(attribute) attribute,count(ch.order_no) count,ch.periods_num,sum(money) betMoney,ch.order_no orderNo,max(win_state) winState,sum(decode(win_state,'1',money*(ch.commission_member/100),-money*(1-ch.commission_member/100)))+sum(ch.win_amount) recessionResults,max(odds) odds from  "
		        + scheme + ".tb_gdklsf_his ch where ch.betting_user_id=? and trunc(ch.betting_date)=trunc(?) group by ch.order_no,type_code,ch.periods_num";
		String bjSQL = "select ch.type_code,max(ch.betting_date) betDate,null as attribute,count(ch.order_no) count,ch.periods_num,sum(money) betMoney,ch.order_no orderNo,max(win_state) winState,sum(decode(win_state,'1',money*(ch.commission_member/100),-money*(1-ch.commission_member/100)))+sum(ch.win_amount) recessionResults,max(odds) odds from   "
		        + scheme + ".tb_bjsc_his ch where ch.betting_user_id=? and trunc(ch.betting_date)=trunc(?) group by ch.order_no,type_code,ch.periods_num";
		// add by peter for K3
		String jsSQL = "select ch.type_code,max(ch.betting_date) betDate,null as attribute,count(ch.order_no) count,ch.periods_num,sum(money) betMoney,ch.order_no orderNo,max(win_state) winState,sum(decode(win_state,'1',money*(ch.commission_member/100),-money*(1-ch.commission_member/100)))+sum(ch.win_amount) recessionResults,max(odds) odds from   "
		        + scheme + ".tb_jssb_his ch where ch.betting_user_id=? and trunc(ch.betting_date)=trunc(?) group by ch.order_no,type_code,ch.periods_num";
		// add by peter for nc
		String ncSQL = "select ch.type_code,max(ch.betting_date) betDate,attribute as attribute,count(ch.order_no) count,ch.periods_num,sum(money) betMoney,ch.order_no orderNo,max(win_state) winState,sum(decode(win_state,'1',money*(ch.commission_member/100),-money*(1-ch.commission_member/100)))+sum(ch.win_amount) recessionResults,max(odds) odds from   "
		        + scheme + ".tb_nc_his ch where ch.betting_user_id=? and trunc(ch.betting_date- interval '2' hour)=trunc(?) group by ch.order_no,type_code,ch.periods_num,attribute";
		List<CQandGDReportInfo> retList = new ArrayList<CQandGDReportInfo>();
		Object[] parameter = new Object[] { userId, beginDate };
		List<CQandGDReportInfo> cqList = jdbcTemplate.query(cqSQL, parameter, new ReportItemMapper());
		List<CQandGDReportInfo> gdList = jdbcTemplate.query(gdSQL, parameter, new ReportItemMapper());
		List<CQandGDReportInfo> bjList = jdbcTemplate.query(bjSQL, parameter, new ReportItemMapper());
		// add by peter for K3
		List<CQandGDReportInfo> jsList = jdbcTemplate.query(jsSQL, parameter, new ReportItemMapper());
		// add by peter for NC
		List<CQandGDReportInfo> ncList = jdbcTemplate.query(ncSQL, parameter, new ReportItemMapper());
		retList.addAll(cqList);
		retList.addAll(gdList);
		retList.addAll(bjList);
		// add by peter for K3
		retList.addAll(jsList);
		// add by peter for NC
		retList.addAll(ncList);
		return retList;
	}

	public List<BalanceInfo> getBalanceInfoByDate(Long userId, Date beginDate, Date endDate, String scheme) {

		List<BalanceInfo> retList = new ArrayList<BalanceInfo>();
		String sqlcq = "select trunc(ch.betting_date- interval '2' hour) betDate,count(distinct order_no) count,sum(money) money,sum(case when win_state=1 then win_amount when win_state=2 then -money when win_state=9 then 0 else 0 end ) winMoney,sum(case when win_state=1 then money*ch.commission_member/100 when win_state=2 then money*ch.commission_member/100 when win_state=9 then 0 else 0 end ) commission from "
		        + scheme + ".tb_cqssc_his ch where trunc(ch.betting_date)<=trunc(?) and trunc(ch.betting_date)>=trunc(?) and betting_user_id=? group by trunc(ch.betting_date- interval '2' hour)";
		String sqlgd = "select trunc(ch.betting_date) betDate,count(distinct order_no) count,sum(money) money,sum(case when win_state=1 then win_amount when win_state=2 then -money when win_state=9 then 0 else 0 end ) winMoney,sum(case when win_state=1 then money*ch.commission_member/100 when win_state=2 then money*ch.commission_member/100 when win_state=9 then 0 else 0 end ) commission from  "
		        + scheme + ".tb_gdklsf_his ch where trunc(ch.betting_date)<=trunc(?) and trunc(ch.betting_date)>=trunc(?) and betting_user_id=? group by trunc(ch.betting_date)";
		String sqlbj = "select trunc(ch.betting_date) betDate,count(distinct order_no) count,sum(money) money,sum(case when win_state=1 then win_amount when win_state=2 then -money when win_state=9 then 0 else 0 end ) winMoney,sum(case when win_state=1 then money*ch.commission_member/100 when win_state=2 then money*ch.commission_member/100 when win_state=9 then 0 else 0 end ) commission from  "
		        + scheme + ".tb_bjsc_his ch where trunc(ch.betting_date)<=trunc(?) and trunc(ch.betting_date)>=trunc(?) and betting_user_id=? group by trunc(ch.betting_date)";
		// add by peter for K3
		String sqljs = "select trunc(ch.betting_date) betDate,count(distinct order_no) count,sum(money) money,sum(case when win_state=1 then win_amount when win_state=2 then -money when win_state=9 then 0 else 0 end ) winMoney,sum(case when win_state=1 then money*ch.commission_member/100 when win_state=2 then money*ch.commission_member/100 when win_state=9 then 0 else 0 end ) commission from  "
		        + scheme + ".tb_jssb_his ch where trunc(ch.betting_date)<=trunc(?) and trunc(ch.betting_date)>=trunc(?) and betting_user_id=? group by trunc(ch.betting_date)";
		// String
		// add by peter for NC
		String sqlnc = "select trunc(ch.betting_date- interval '2' hour) betDate,count(distinct order_no) count,sum(money) money,sum(case when win_state=1 then win_amount when win_state=2 then -money when win_state=9 then 0 else 0 end ) winMoney,sum(case when win_state=1 then money*ch.commission_member/100 when win_state=2 then money*ch.commission_member/100 when win_state=9 then 0 else 0 end ) commission from  "
		        + scheme + ".tb_nc_his ch where trunc(ch.betting_date)<=trunc(?) and trunc(ch.betting_date)>=trunc(?) and betting_user_id=? group by trunc(ch.betting_date- interval '2' hour)";
		Object[] parameter = new Object[] { endDate, beginDate, userId };
		List<BalanceInfo> cqList = jdbcTemplate.query(sqlcq, parameter, new BalanceInfoItemMapper());
		List<BalanceInfo> gdList = jdbcTemplate.query(sqlgd, parameter, new BalanceInfoItemMapper());
		List<BalanceInfo> bjList = jdbcTemplate.query(sqlbj, parameter, new BalanceInfoItemMapper());
		// add by peter for K3
		List<BalanceInfo> jsList = jdbcTemplate.query(sqljs, parameter, new BalanceInfoItemMapper());
		// add by peter for nc
		List<BalanceInfo> ncList = jdbcTemplate.query(sqlnc, parameter, new BalanceInfoItemMapper());

		retList.addAll(cqList);
		retList.addAll(gdList);
		retList.addAll(bjList);
		// add by peter for K3
		retList.addAll(jsList);
		// add by peter for nc
		retList.addAll(ncList);
		// retList.addAll(hkList);
		return retList;

	}

	public Integer getUserLMitemQuotasMoney(String periodNum, String typeCode, String userId, String attr, String tableName, String scheme) {
		String sql = " select sum(money) from " + scheme + "." + tableName + " a where a.type_code= ? and a.periods_num=? and a.betting_user_id=? and attribute=?";
		Object[] parameters = new Object[] { typeCode, periodNum, userId, attr };
		Integer sum = jdbcTemplate.queryForInt(sql, parameters);
		return sum;
	}

	class BetDetailItemMapper implements RowMapper {

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

	class ReportItemMapper implements RowMapper {

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
}
