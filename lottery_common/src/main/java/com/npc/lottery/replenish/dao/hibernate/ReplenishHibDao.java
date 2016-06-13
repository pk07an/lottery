package com.npc.lottery.replenish.dao.hibernate;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.replenish.dao.interf.IReplenishHibDao;
import com.npc.lottery.replenish.entity.Replenish;

public class ReplenishHibDao extends HibernateDao<Replenish, Long> implements IReplenishHibDao {

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void insertReplenish(Replenish entity, String scheme) {
		if(StringUtils.isNotBlank(scheme)){
			scheme=scheme+".";
		}
		String insetSql="insert into "+scheme+"tb_replenish (ID, ORDER_NO, TYPE_CODE, MONEY, ATTRIBUTE, REPLENISH_USER_ID, " +
    			"PERIODS_NUM, PLATE, BETTING_DATE, ODDS, UPDATE_USER, UPDATE_DATE, REMARK, " +
    			"CHIEFSTAFF, BRANCHSTAFF, STOCKHOLDERSTAFF, GENAGENSTAFF, AGENTSTAFF, " +
    			"RATE_CHIEF, RATE_BRANCH, RATE_STOCKHOLDER, RATE_GEN_AGENT, RATE_AGENT, " +
    			"ODDS2, COMMISSION_CHIEF, COMMISSION_BRANCH, COMMISSION_GEN_AGENT, " +
    			"COMMISSION_STOCKHOLDER, COMMISSION_AGENT, COMMISSION_MEMBER, COMMISSION_TYPE, SELECT_ODDS)" +
    			"values (seq_tb_replenish.nextval, ?, ?, ?, ?, ?, ?, ?, sysdate, ?, ?, ?, ?, ?, ?, ?, ?, " +
    			"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    	Object[] args = new Object[] { entity.getOrderNo(),entity.getTypeCode(),entity.getMoney(),
    			entity.getAttribute(),entity.getReplenishUserId(),entity.getPeriodsNum(),
    			entity.getPlate(),
    			entity.getOdds(),
    			entity.getUpdateUser(),
    			new java.sql.Date(new java.util.Date().getTime()),
    			entity.getRemark(),entity.getChiefStaff(),entity.getBranchStaff(),
    			entity.getStockHolderStaff(),entity.getGenAgenStaff(),entity.getAgentStaff(),
    			entity.getRateChief(),entity.getRateBranch(),entity.getRateStockHolder(),
    			entity.getRateGenAgent(),entity.getRateAgent(),entity.getOdds2(),
    			entity.getCommissionChief(),entity.getCommissionBranch(),entity.getCommissionGenAgent(),
    			entity.getCommissionStockHolder(),entity.getCommissionAgent(),entity.getCommissionMember(),
    			entity.getCommissionType(),entity.getSelectedOdds()};
    	jdbcTemplate.update(insetSql, args);
	}

	

}
