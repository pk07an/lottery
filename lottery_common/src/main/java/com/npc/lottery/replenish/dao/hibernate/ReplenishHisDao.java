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
import com.npc.lottery.replenish.dao.interf.IReplenishHisDao;
import com.npc.lottery.replenish.entity.ReplenishHis;

public class ReplenishHisDao extends HibernateDao<ReplenishHis, Long> implements IReplenishHisDao {

	@Override
	public List<ReplenishHis> queryReplenishHisList(String periodsNum,String schema) {
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
		List<ReplenishHis> retList = jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(), new ReplenishHisMapperT());
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

class ReplenishHisMapperT implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	ReplenishHis replenishHis = new ReplenishHis();
    	replenishHis.setID(rs.getLong("ID"));
    	replenishHis.setOrderNo(rs.getString("ORDER_NO"));
    	replenishHis.setOrderNo(rs.getString("TYPE_CODE"));
    	replenishHis.setOrderNo(rs.getString("MONEY"));
    	replenishHis.setOrderNo(rs.getString("ATTRIBUTE"));
    	replenishHis.setOrderNo(rs.getString("REPLENISH_USER_ID"));
    	replenishHis.setOrderNo(rs.getString("REPLENISH_ACC_USER_ID"));
    	replenishHis.setOrderNo(rs.getString("PERIODS_NUM"));
    	replenishHis.setOrderNo(rs.getString("PLATE"));
    	replenishHis.setOrderNo(rs.getString("BETTING_DATE"));
    	replenishHis.setOrderNo(rs.getString("WIN_STATE"));
    	replenishHis.setOrderNo(rs.getString("WIN_AMOUNT"));
    	replenishHis.setOrderNo(rs.getString("ODDS"));
    	replenishHis.setOrderNo(rs.getString("COMMISSION"));
    	replenishHis.setOrderNo(rs.getString("RATE"));
    	replenishHis.setOrderNo(rs.getString("UPDATE_USER"));
    	replenishHis.setOrderNo(rs.getString("UPDATE_DATE"));
    	replenishHis.setOrderNo(rs.getString("REMARK"));
    	replenishHis.setOrderNo(rs.getString("CHIEFSTAFF"));
    	replenishHis.setOrderNo(rs.getString("BRANCHSTAFF"));
    	replenishHis.setOrderNo(rs.getString("STOCKHOLDERSTAFF"));
    	replenishHis.setOrderNo(rs.getString("GENAGENSTAFF"));
    	replenishHis.setOrderNo(rs.getString("AGENTSTAFF"));
    	replenishHis.setOrderNo(rs.getString("RATE_CHIEF"));
    	replenishHis.setOrderNo(rs.getString("RATE_BRANCH"));
    	replenishHis.setOrderNo(rs.getString("RATE_STOCKHOLDER"));
    	replenishHis.setOrderNo(rs.getString("RATE_GEN_AGENT"));
    	replenishHis.setOrderNo(rs.getString("RATE_AGENT"));
    	replenishHis.setOrderNo(rs.getString("ODDS2"));
    	replenishHis.setOrderNo(rs.getString("COMMISSION_CHIEF"));
    	replenishHis.setOrderNo(rs.getString("COMMISSION_BRANCH"));
    	replenishHis.setOrderNo(rs.getString("COMMISSION_GEN_AGENT"));
    	replenishHis.setOrderNo(rs.getString("COMMISSION_STOCKHOLDER"));
    	replenishHis.setOrderNo(rs.getString("COMMISSION_AGENT"));
    	replenishHis.setOrderNo(rs.getString("COMMISSION_MEMBER"));
    	replenishHis.setOrderNo(rs.getString("COMMISSION_TYPE"));
    	replenishHis.setOrderNo(rs.getString("SELECT_ODDS"));
    	return replenishHis;
    }
}
