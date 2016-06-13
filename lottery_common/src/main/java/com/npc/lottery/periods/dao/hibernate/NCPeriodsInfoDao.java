package com.npc.lottery.periods.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.periods.dao.interf.INCPeriodsInfoDao;
import com.npc.lottery.periods.entity.NCPeriodsInfo;

public class NCPeriodsInfoDao extends HibernateDao<NCPeriodsInfo, Long> implements INCPeriodsInfoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<NCPeriodsInfo> getPeriodsInfoFromBetTableForReLotteryEOD(String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("		SELECT {ncPeriodInfo.*} FROM TB_NC_PERIODS_INFO ncPeriodInfo WHERE STATE IN(4,7) AND PERIODS_NUM IN ( ");
		sb.append(" SELECT PERIODS_NUM FROM " + scheme + "TB_NC UNION ");
		sb.append(" SELECT PERIODS_NUM FROM " + scheme + Constant.HKLHC_REPLENISH_TABLE_NAME + " WHERE TYPE_CODE LIKE '%" + Constant.LOTTERY_TYPE_NC + "%') ");
		sb.append("ORDER BY ncPeriodInfo.PERIODS_NUM DESC");

		SQLQuery query = this.getSession().createSQLQuery(sb.toString());
		query.addEntity("ncPeriodInfo", NCPeriodsInfo.class);

		return (List<NCPeriodsInfo>) query.list();
	}

}
