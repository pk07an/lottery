package com.npc.lottery.replenish.logic.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.replenish.dao.interf.IReplenishAutoSetLog;
import com.npc.lottery.replenish.entity.ReplenishAutoSetLog;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoSetLogLogic;
import com.npc.lottery.util.Page;


public class ReplenishAutoSetLogLogic implements IReplenishAutoSetLogLogic {
	 private static final Logger logger = Logger.getLogger(ReplenishAutoSetLogLogic.class);
	 private IReplenishAutoSetLog replenishAutoSetLogDao;
	 private JdbcTemplate jdbcTemplate;
	 
	 private TaskExecutor wcpTaskExecutor;//异步执行
	 
	@Override
	public List<ReplenishAutoSetLog> queryReplenishAutoSetLogList(
			Criterion... criterions) {
		return  replenishAutoSetLogDao.find(criterions);
	}

	@Override
	public void updateReplenishAutoSet(ReplenishAutoSetLog entity) {
		
	}

	@Override
	public void saveReplenishLogSet(ReplenishAutoSetLog entity) {
		replenishAutoSetLogDao.save(entity);
	}

	@Override
	public Page<ReplenishAutoSetLog> queryLogByPage(
			Page<ReplenishAutoSetLog> page, String userId) {
		List<ReplenishAutoSetLog> list = new ArrayList<ReplenishAutoSetLog>();
		
		String sql = " select *  from ( select ID,shop_id,type,type_code,money_orgin,money_new,"+
				     " (CASE WHEN create_userType=9 THEN ( SELECT account FROM tb_frame_member_staff WHERE id=create_userID) "+
				     " ELSE (SELECT account FROM  tb_frame_manager_staff WHERE id=create_userID)END) "+
					 " createUserName,create_userID,create_userType,create_time,state_orgin,state_new ,ip " +
					 " ,CHANGE_TYPE,CHANGE_SUB_TYPE,ORGINAL_VALUE,NEW_VALUE,(select account from tb_frame_manager_staff where id=UPDATE_USERID) updateUserName,UPDATE_USERTYPE"+
					 " from TB_REPLENISH_AUTO_SET_LOG t " +
					 " where t.create_time <= sysdate and t.create_time>=to_date(to_char(sysdate-15,'YYYY-MM-DD'),'YYYY-MM-DD') and CREATE_USERID=?" +
					 " union " +
					 " select ID,shop_id,type,type_code,money_orgin,money_new,(select account from tb_frame_manager_staff where id=create_userID) createUserName,create_userID,create_userType,create_time,state_orgin,state_new ,ip "+
					 " ,CHANGE_TYPE,CHANGE_SUB_TYPE,ORGINAL_VALUE,NEW_VALUE,(select account from tb_frame_manager_staff where id=UPDATE_USERID) updateUserName,UPDATE_USERTYPE"+
					 " from TB_REPLENISH_AUTO_SET_LOG t" +
					 " where t.create_time<=to_date(to_char(sysdate-15,'YYYY-MM-DD'),'YYYY-MM-DD') and  rownum <=50 and CREATE_USERID=?" +
					 " ) order by create_time desc " ;
		Object[] parameters = new Object[] {userId,userId };
		List betlist = jdbcTemplate.query(sql, parameters,
				new ReplenishAutoSetLogItemMapper());
		
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > betlist.size())
			last = betlist.size();
//		Collections.sort(betlist);
		page.setTotalCount(betlist.size());
		page.setResult(betlist.subList(first, last));
		return page;
//		return replenishAutoSetLogDao.findPage(page, criterions);
	}

	public IReplenishAutoSetLog getReplenishAutoSetLogDao() {
		return replenishAutoSetLogDao;
	}

	public void setReplenishAutoSetLogDao(
			IReplenishAutoSetLog replenishAutoSetLogDao) {
		this.replenishAutoSetLogDao = replenishAutoSetLogDao;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	class ReplenishAutoSetLogItemMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			ReplenishAutoSetLog item = new ReplenishAutoSetLog();
//			item.setID(rs.getString("id"));
			item.setShopID(rs.getLong("shop_id"));
			item.setType(rs.getString("type"));
			item.setTypeCode(rs.getString("type_code"));
			item.setMoneyOrgin(rs.getInt("money_orgin"));
			item.setMoneyNew(rs.getInt("money_new"));
			item.setCreateUserID(rs.getLong("create_userID"));
			item.setCreateUserName(rs.getString("createUserName"));
			item.setCreateUserType(rs.getInt("create_userType"));
			try {
				item.setCreateTime(sdf.parse(rs.getString("create_time")));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			item.setStateOrgin(rs.getString("state_orgin"));
			item.setStateNew(rs.getString("state_new"));
			item.setIp(rs.getString("ip"));
			//add by peter
			item.setChangeType(rs.getString("CHANGE_TYPE"));
			item.setChangeSubType(rs.getString("CHANGE_SUB_TYPE"));
			item.setOrginalValue(rs.getString("ORGINAL_VALUE"));
			item.setNewValue(rs.getString("NEW_VALUE"));
			item.setUpdateUserName(rs.getString("updateUserName"));
			item.setUpdateUserType(rs.getInt("UPDATE_USERTYPE"));
			return item;
		}

	}
	public TaskExecutor getWcpTaskExecutor() {
		return wcpTaskExecutor;
	}

	public void setWcpTaskExecutor(TaskExecutor wcpTaskExecutor) {
		this.wcpTaskExecutor = wcpTaskExecutor;
	}

	@Override
	public void saveUserCommissionLog(final List<ReplenishAutoSetLog> userCommissionLogList, final IReplenishAutoSetLogLogic replenishAutoSetLogLogic) {
		wcpTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					logger.info("<--用户修改退水设置触发异步退水修改日志记录   start-->");
					List<ReplenishAutoSetLog> result = new ArrayList<ReplenishAutoSetLog>();
					result.addAll(userCommissionLogList);
					long startTime = System.currentTimeMillis();
					// 插入修改记录到记录表
					if (null != result && result.size() > 0) {
						logger.info("<--用户修改退水设置触发异步退水修改日志记录  ===== 共有" + result.size() + "条用户退水修改需要记录日志-->");
						for (ReplenishAutoSetLog changeLog : result) {
							replenishAutoSetLogLogic.saveReplenishLogSet(changeLog);
						}
					}
					long end = System.currentTimeMillis();
					logger.info("<--用户修改退水设置触发异步退水修改日志记录：" + (end - startTime) / 1000 + "秒 -->");
				} catch (Exception ex) {
					logger.info("-用户修改退水设置触发异步退水修改日志记录 出错！" + ex.getMessage());
				}
			}
		});
	}
}
