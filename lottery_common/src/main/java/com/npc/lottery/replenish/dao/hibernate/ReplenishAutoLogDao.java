package com.npc.lottery.replenish.dao.hibernate;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.replenish.dao.interf.IReplenishAutoLog;
import com.npc.lottery.replenish.entity.ReplenishAutoLog;

public class ReplenishAutoLogDao extends HibernateDao<ReplenishAutoLog, Long> implements IReplenishAutoLog {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void saveReplenishLogByScheme(ReplenishAutoLog entity, String scheme) {
		if(StringUtils.isNotBlank(scheme)){
			scheme=scheme+".";
		}
		String sql="insert into "+scheme+"TB_REPLENISH_AUTO_LOG (ID,SHOP_ID,PLAY_TYPE,TYPE_CODE,MONEY,CREATE_USERID,CREATE_DATE,PERIODS_NUM,type) "+
					"values(SEQ_tb_replenish_auto_LOG.nextval,?,?,?,?,?,sysdate,?,?)";
		Object[] objects=new Object[]{
				entity.getShopID(),
				entity.getType(),
				entity.getTypeCode(),
				entity.getMoney(),
				entity.getCreateUserID(),
				entity.getPeriodsNum(),
				entity.getTypeBH()
		};
		jdbcTemplate.update(sql,objects);
	}
}
