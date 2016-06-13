package com.npc.lottery.periods.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;

import com.google.common.collect.Lists;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.periods.dao.interf.ICQPeriodsInfoDao;
import com.npc.lottery.periods.entity.CQPeriodsInfo;

public class CQPeriodsInfoDao extends HibernateDao<CQPeriodsInfo, Long> implements ICQPeriodsInfoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<CQPeriodsInfo> getPeriodsInfoFromBetTableForReLotteryEOD(String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		List<String> cqLotteryTableList = Lists.newArrayList(Constant.CQSSC_LOTTERY_TABLE_LIST);
		StringBuilder sb = new StringBuilder();
		sb.append("		SELECT {cqPeriodInfo.*} FROM TB_CQSSC_PERIODS_INFO cqPeriodInfo WHERE STATE IN(4,7) AND PERIODS_NUM IN ( ");
		for (String tableName : cqLotteryTableList) {
			sb.append(" SELECT PERIODS_NUM FROM " + scheme + tableName + " UNION ");
		}
		sb.append(" SELECT PERIODS_NUM FROM " + scheme + Constant.HKLHC_REPLENISH_TABLE_NAME + " WHERE TYPE_CODE LIKE '%" + Constant.LOTTERY_TYPE_CQSSC + "%') ");
		sb.append("ORDER BY cqPeriodInfo.PERIODS_NUM DESC");

		SQLQuery query = this.getSession().createSQLQuery(sb.toString());
		query.addEntity("cqPeriodInfo", CQPeriodsInfo.class);

		return (List<CQPeriodsInfo>) query.list();
	}

}
