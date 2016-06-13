package com.npc.lottery.periods.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;

import com.google.common.collect.Lists;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.periods.dao.interf.IGDPeriodsInfoDao;
import com.npc.lottery.periods.entity.GDPeriodsInfo;

public class GDPeriodsInfoDao extends HibernateDao<GDPeriodsInfo, Long> implements
        IGDPeriodsInfoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<GDPeriodsInfo> getPeriodsInfoFromBetTableForReLotteryEOD(String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}
		List<String> gdLotteryTableList = Lists.newArrayList(Constant.GDKLSF_LOTTERY_TABLE_LIST);
		StringBuilder sb = new StringBuilder();
		sb.append("		SELECT {gdPeriodInfo.*} FROM TB_GDKLSF_PERIODS_INFO gdPeriodInfo WHERE STATE IN(4,7) AND PERIODS_NUM IN ( ");
		for (String tableName : gdLotteryTableList) {
			sb.append(" SELECT PERIODS_NUM FROM " + scheme + tableName + " UNION ");
		}
		sb.append(" SELECT PERIODS_NUM FROM " + scheme + Constant.HKLHC_REPLENISH_TABLE_NAME+" WHERE TYPE_CODE LIKE '%"+Constant.LOTTERY_TYPE_GDKLSF+"%') ");
		sb.append("ORDER BY gdPeriodInfo.PERIODS_NUM DESC");
		
		SQLQuery query = this.getSession().createSQLQuery(sb.toString());
		query.addEntity("gdPeriodInfo", GDPeriodsInfo.class);
		
		 
		return (List<GDPeriodsInfo>)query.list();
	}

}
