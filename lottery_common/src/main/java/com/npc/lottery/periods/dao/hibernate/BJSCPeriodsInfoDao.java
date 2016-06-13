package com.npc.lottery.periods.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.periods.dao.interf.IBJSCPeriodsInfoDao;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;

public class BJSCPeriodsInfoDao extends HibernateDao<BJSCPeriodsInfo, Long> implements IBJSCPeriodsInfoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<BJSCPeriodsInfo> getPeriodsInfoFromBetTableForReLotteryEOD(String scheme) {
		if(StringUtils.isNotEmpty(scheme)){
			scheme = scheme +".";
		}
		List<String> bjLotteryTableList = Lists.newArrayList(Constant.BJSC_TABLE_LIST);
		StringBuilder sb = new StringBuilder();
		sb.append("		SELECT {bjPeriodInfo.*} FROM TB_BJSC_PERIODS_INFO bjPeriodInfo WHERE STATE IN(4,7) AND PERIODS_NUM IN ( ");
		for (String tableName : bjLotteryTableList) {
			sb.append(" SELECT PERIODS_NUM FROM " + scheme + tableName + " UNION ");
		}
		sb.append(" SELECT PERIODS_NUM FROM " + scheme +Constant.HKLHC_REPLENISH_TABLE_NAME + " WHERE TYPE_CODE LIKE '%" + Constant.LOTTERY_TYPE_BJ + "%') ");
		sb.append("ORDER BY bjPeriodInfo.PERIODS_NUM DESC");

		SQLQuery query = this.getSession().createSQLQuery(sb.toString());
		query.addEntity("bjPeriodInfo", BJSCPeriodsInfo.class);

		return (List<BJSCPeriodsInfo>) query.list();
	}

	/**
	 * 北京获取昨天生成盘期的最大值方法,如果昨天没有生成,返回-1,不再生成当天的盘期
	 */
	@Override
	public long getBJSCYesterdayLastPeriods() {
		long periods = -1;
		String sql = "select max(periods_num) from tb_bjsc_periods_info where trunc(create_time) = trunc(sysdate-1)";
		try {
			List resultList = this.getSession().createSQLQuery(sql).list();

			if (!CollectionUtils.isEmpty(resultList)) {
				periods = Long.valueOf((String)resultList.get(0));
			}

		} catch (Exception e) {
			logger.error("getBJSCYesterdayLastPeriods error", e);
		}
		return periods;
	}

}
