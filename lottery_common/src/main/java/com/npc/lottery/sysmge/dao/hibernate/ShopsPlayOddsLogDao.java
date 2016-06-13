package com.npc.lottery.sysmge.dao.hibernate;



import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.sysmge.dao.interf.IShopsPlayOddsLogDao;
import com.npc.lottery.sysmge.entity.ShopsPlayOddsLog;

public class ShopsPlayOddsLogDao extends HibernateDao<ShopsPlayOddsLog, Long> implements IShopsPlayOddsLogDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void saveLogByScheme(ShopsPlayOddsLog log, String scheme) {
		if(StringUtils.isNotBlank(scheme)){
			scheme=scheme+".";
		}
		String sql="insert into "+scheme+"TB_SHOPS_PLAY_ODDS_LOG (id,shops_code,PLAY_TYPE_CODE,odds_type_x,odds_type,real_odds_origin, "+
				   "real_update_date_origin,real_update_user_origin,real_odds_new,real_update_date_new,REAL_UPDATE_USER_NEW,periods_num,ip,remark,type) "+
				   "values(SEQ_TB_SHOPS_PLAY_ODDS_LOG.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] objects=new Object[]{
				log.getShopCode(),
				log.getPlayTypeCode(),
				log.getOddsTypeX(),
				log.getOddsType(),
				log.getRealOddsOrigin(),
				new java.sql.Date(new java.util.Date().getTime()),
				log.getRealUpdateUserOrigin(),
				log.getRealOddsNew(),
				new java.sql.Date(new java.util.Date().getTime()),
				log.getRealUpdateUserNew(),
				log.periodsNum,
				log.getIp(),
				log.getRemark(),
				log.getType()
		};
		jdbcTemplate.update(sql,objects);
	}

}
