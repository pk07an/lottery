package com.npc.lottery.replenish.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.google.common.collect.Lists;
import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.replenish.dao.interf.IReplenishCheckDao;
import com.npc.lottery.replenish.entity.ReplenishCheck;
import com.npc.lottery.replenish.entity.ReplenishHis;

public class ReplenishCheckDao extends HibernateDao<ReplenishCheck, Long> implements IReplenishCheckDao {

	@Override
	public List<ReplenishCheck> queryReplenishCheckList(String periodsNum,String schema) {
		if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
		
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	finalSql.append("SELECT * FROM "+schema+"tb_replenish_his WHERE PERIODS_NUM=?");
    	finalParameterList.add(periodsNum);
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
		@SuppressWarnings("unchecked")
		List<ReplenishCheck> retList = jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(), new ReplenishCheckMapperT());
		return retList;
	}
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}

class ReplenishCheckMapperT implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	ReplenishCheck entity = new ReplenishCheck();
    	entity.setID(rs.getLong("ID"));
    	entity.setOrderNo(rs.getString("ORDER_NO"));
    	entity.setOrderNo(rs.getString("TYPE_CODE"));
    	entity.setOrderNo(rs.getString("MONEY"));
    	entity.setOrderNo(rs.getString("ATTRIBUTE"));
    	entity.setOrderNo(rs.getString("REPLENISH_USER_ID"));
    	entity.setOrderNo(rs.getString("REPLENISH_ACC_USER_ID"));
    	entity.setOrderNo(rs.getString("PERIODS_NUM"));
    	entity.setOrderNo(rs.getString("PLATE"));
    	entity.setOrderNo(rs.getString("BETTING_DATE"));
    	entity.setOrderNo(rs.getString("WIN_STATE"));
    	entity.setOrderNo(rs.getString("WIN_AMOUNT"));
    	entity.setOrderNo(rs.getString("ODDS"));
    	entity.setOrderNo(rs.getString("COMMISSION"));
    	entity.setOrderNo(rs.getString("RATE"));
    	entity.setOrderNo(rs.getString("UPDATE_USER"));
    	entity.setOrderNo(rs.getString("UPDATE_DATE"));
    	entity.setOrderNo(rs.getString("REMARK"));
    	entity.setOrderNo(rs.getString("CHIEFSTAFF"));
    	entity.setOrderNo(rs.getString("BRANCHSTAFF"));
    	entity.setOrderNo(rs.getString("STOCKHOLDERSTAFF"));
    	entity.setOrderNo(rs.getString("GENAGENSTAFF"));
    	entity.setOrderNo(rs.getString("AGENTSTAFF"));
    	entity.setOrderNo(rs.getString("RATE_CHIEF"));
    	entity.setOrderNo(rs.getString("RATE_BRANCH"));
    	entity.setOrderNo(rs.getString("RATE_STOCKHOLDER"));
    	entity.setOrderNo(rs.getString("RATE_GEN_AGENT"));
    	entity.setOrderNo(rs.getString("RATE_AGENT"));
    	entity.setOrderNo(rs.getString("ODDS2"));
    	entity.setOrderNo(rs.getString("COMMISSION_CHIEF"));
    	entity.setOrderNo(rs.getString("COMMISSION_BRANCH"));
    	entity.setOrderNo(rs.getString("COMMISSION_GEN_AGENT"));
    	entity.setOrderNo(rs.getString("COMMISSION_STOCKHOLDER"));
    	entity.setOrderNo(rs.getString("COMMISSION_AGENT"));
    	entity.setOrderNo(rs.getString("COMMISSION_MEMBER"));
    	entity.setOrderNo(rs.getString("COMMISSION_TYPE"));
    	entity.setOrderNo(rs.getString("SELECT_ODDS"));
    	return entity;
    }
}

