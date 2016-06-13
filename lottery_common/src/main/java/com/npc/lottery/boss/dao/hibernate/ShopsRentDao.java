package com.npc.lottery.boss.dao.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.npc.lottery.boss.dao.interf.IShopsRentDao;
import com.npc.lottery.boss.entity.ShopsRent;
import com.npc.lottery.common.dao.HibernateDao;

public class ShopsRentDao extends HibernateDao<ShopsRent, Long> implements IShopsRentDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void update(ShopsRent entity) {
		
		String sql="update tb_shops_rent set expity_time=?,expity_warning_time=?,last_modify_user=?,last_modify_date=?,remark=? where shops_code=?";
		try {
			jdbcTemplate.update(sql, new Object[]{entity.getExpityTime(),entity.getExpityWarningTime(),entity.getLastModifyUser(),entity.getLastModifyDate(),entity.getRemark(),entity.getShopsInfo().getShopsCode()});
			logger.info("更新商铺有效信息成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新商铺有效信息失败>>>"+e);
		}
		
	}

}
