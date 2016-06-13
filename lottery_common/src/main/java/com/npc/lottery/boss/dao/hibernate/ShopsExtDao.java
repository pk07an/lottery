package com.npc.lottery.boss.dao.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.npc.lottery.boss.dao.interf.IShopsExtDao;
import com.npc.lottery.boss.entity.ShopsInfo;


public class ShopsExtDao extends HibernateDaoSupport implements IShopsExtDao {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	

	/**
	 * 更新信息
	 * 
	 * @param entity 待更新的信息
	 */
	public void update(ShopsInfo entity) {
		//getHibernateTemplate().merge(entity);
		//getHibernateTemplate().update(entity);
		String sql="update tb_shops_info set shops_code=?,shops_name=?,CREATE_TIME=sysdate,remark=?,state=?,CREATE_USER=?,css=?,enable_bet_delete=?,enable_bet_cancel=? where id=?";
		Object[] obj=new Object[9];
		obj[0]=entity.getShopsCode();
		obj[1]=entity.getShopsName();
		obj[2]=entity.getRemark();
		obj[3]=entity.getState();
		obj[4]=entity.getCreateUser();
		obj[5]=entity.getCss();
		obj[6]=entity.getEnableBetDelete();
		obj[7]=entity.getEnableBetCancel();
		obj[8]=entity.getID();
		try {
			jdbcTemplate.update(sql, obj);
			logger.info("更新商铺成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新商铺失败>>>>"+e);
		}
	}
	
}
