package com.npc.lottery.periods.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;

import com.google.common.collect.Lists;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.periods.dao.interf.IJSSBPeriodsInfoDao;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;

public class JSSBPeriodsInfoDao extends HibernateDao<JSSBPeriodsInfo, Long> implements IJSSBPeriodsInfoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<JSSBPeriodsInfo> getPeriodsInfoFromBetTableForReLotteryEOD(String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		List<String> jsLotteryTableList = Lists.newArrayList(Constant.K3_TABLE_LIST);
		StringBuilder sb = new StringBuilder();
		sb.append("		SELECT {jsPeriodInfo.*} FROM TB_JSSB_PERIODS_INFO jsPeriodInfo WHERE STATE IN(4,7) AND PERIODS_NUM IN ( ");
		for (String tableName : jsLotteryTableList) {
			sb.append(" SELECT PERIODS_NUM FROM " + scheme + tableName + " UNION ");
		}
		sb.append(" SELECT PERIODS_NUM FROM " + scheme +  Constant.HKLHC_REPLENISH_TABLE_NAME + " WHERE TYPE_CODE LIKE '%" + Constant.LOTTERY_TYPE_K3 + "%') ");
		sb.append("ORDER BY jsPeriodInfo.PERIODS_NUM DESC");

		SQLQuery query = this.getSession().createSQLQuery(sb.toString());
		query.addEntity("jsPeriodInfo", JSSBPeriodsInfo.class);

		return (List<JSSBPeriodsInfo>) query.list();
	}

}
