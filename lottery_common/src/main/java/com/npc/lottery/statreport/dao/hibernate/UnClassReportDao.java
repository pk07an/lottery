package com.npc.lottery.statreport.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.google.common.collect.Lists;
import com.npc.lottery.common.Constant;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.replenish.vo.DetailVO;
import com.npc.lottery.statreport.dao.interf.IUnClassReportDao;
import com.npc.lottery.statreport.entity.UnClassReport;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.util.Page;

/**
 * 报表统计相关的数据库处理类
 * 
 */
public class UnClassReportDao implements IUnClassReportDao {

    private static Logger log = Logger.getLogger(UnClassReportDao.class);
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public List<UnClassReport> queryUnClassReport(Date startDate,Date endDate,String lotteryType,String playType,String periodNum,Long userid,String userType
    		,String myColumn,String rateUser,String commissionUser,String nextColumn,String[] scanTableList)
    {
    	StringBuffer finalSql=new StringBuffer();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	
    	finalSql.append("select commissionType, sum(count) count,sum(totalMoney) totalMoney,sum(rateMoney) rateMoney, " +
    			"sum(moneyRateAgent) as moneyRateAgent," +
    			"sum(moneyRateGenAgent) as moneyRateGenAgent, " +
    			"sum(moneyRateStockholder) as moneyRateStockholder, " +
    			"sum(moneyRateBranch) as moneyRateBranch, " +
    			"sum(moneyRateChief) as moneyRateChief "+
    			"from (");
    	
    	sqTemplatelBuffer.append(" select a.COMMISSION_TYPE as commissionType, count(distinct order_no) as count,sum(money) as totalMoney,");
    	
    	sqTemplatelBuffer.append(" sum(money * a.RATE_AGENT/100) as moneyRateAgent," +
    			"sum(money * a.RATE_GEN_AGENT/100) as moneyRateGenAgent, " +
    			"sum(money * a.RATE_STOCKHOLDER/100) as moneyRateStockholder, " +
    			"sum(money * a.RATE_BRANCH/100) as moneyRateBranch, " +
    			"sum(money * a.RATE_CHIEF/100) as moneyRateChief, "+
    			"sum(money * a." + rateUser + "/100) as rateMoney ");
    			
    	sqTemplatelBuffer.append(" from {TableName} a, (select t1.id,t1.account,t1.chs_name,t1.user_type from tb_frame_manager_staff t1 union select  t2.id,t2.account,t2.chs_name,t2.user_type from tb_frame_member_staff t2 ) b ");
    	
    	
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.chiefstaff=? and decode(a.branchstaff,0,a.betting_user_id,a.branchstaff)=b.id ");
    	}
    	
    	else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.branchstaff=? and decode(a.stockholderstaff,0,a.betting_user_id,a.stockholderstaff)=b.id ");
    	}
    	else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.stockholderstaff=? and decode(a.genagenstaff,0,a.betting_user_id,a.genagenstaff)=b.id ");
    	}
    	else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.genagenstaff=? and decode(a.agentstaff,0,a.betting_user_id,a.agentstaff)=b.id ");
    	}
    	else if(ManagerStaff.USER_TYPE_AGENT.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.agentstaff=? and a.betting_user_id=b.id and user_type!=7 ");
    	}	
    	templateParameterList.add(userid);
    	
    	if(startDate!=null && endDate!=null){
    		sqTemplatelBuffer.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:00:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:00:00') " );
    	}
    	
		sqTemplatelBuffer.append(" and a.commission_type like ? and user_type!=7 " );
		templateParameterList.add(playType);
		
		if(periodNum!=null && periodNum!="")
    	{
    		sqTemplatelBuffer.append(" and a.periods_num=? " );
    		templateParameterList.add(periodNum);
    	}
    	sqTemplatelBuffer.append(" group by a.COMMISSION_TYPE ");
    	
        for (int i = 0; i < scanTableList.length; i++) {
    	  finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", scanTableList[i]));
    	  if(i!=scanTableList.length-1)
    	  finalSql.append(" union all ");
    	  finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	  
         }
        if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){
        	finalSql.append(" union all ");
        	
        	finalSql.append(" select a.COMMISSION_TYPE as commissionType, 0 as count,0 as totalMoney,");
        	
        	finalSql.append(" sum(money * a.RATE_AGENT/100) as moneyRateAgent," +
        			"sum(money * a.RATE_GEN_AGENT/100) as moneyRateGenAgent, " +
        			"sum(money * a.RATE_STOCKHOLDER/100) as moneyRateStockholder, " +
        			"sum(money * a.RATE_BRANCH/100) as moneyRateBranch, " +
        			"sum(money * a.RATE_CHIEF/100) as moneyRateChief, " +
        			"sum(money * a." + rateUser + "/100) as rateMoney ");
        	
        	finalSql.append("from tb_replenish a,"  +
                   "(select t1.id, t1.account, t1.chs_name, t1.user_type from tb_frame_manager_staff t1) b " +
              "where a." + myColumn + " = ?" +
              " and decode(a." + nextColumn + ",'',a.replenish_user_id,a." + nextColumn + ") = b.id" +
              " and user_type != 7 " +
              " and a.win_state ='0' ");
        	if(startDate!=null && endDate!=null){   
        		finalSql.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:00:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:00:00') " );
        	}
        	finalSql.append(" and a.commission_type like ? ");
        	
            if(periodNum!=null && periodNum!="")
            	finalSql.append(" and a.periods_num=? " );
            
            finalSql.append(" and a.type_code like ? ");
            templateParameterList.add(lotteryType);//这个值是对应type_code,其他值在前面已经对应了
            
            finalSql.append(" group by a.COMMISSION_TYPE ");
            
        	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
        }
        
        finalSql.append(") group by commissionType ") ;
        List<UnClassReport> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new DeliveryReportItemMapperClass());
         return reportList;

    }
    
    //查看补出的货的汇总
    @Override
	public List<UnClassReport> queryReplenish(Date startDate,Date endDate,Long userID,String typeCode,String periodsNum,String rateUser,String lotteryType,String userType) {
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	finalSql.append("select count(*) 	              as turnover," +
						     "sum(money)  				  as amount, " +
    						"a.COMMISSION_TYPE  		  as commissionType ");

    	finalSql.append(" from tb_replenish a  " +
				     " where ");
	     if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
				finalSql.append(" replenish_user_id in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
				
		 }else{
			finalSql.append(" replenish_user_id=? ");
		 }		     
	     finalSql.append(" and a.win_state = '0' " +
				         " and a.commission_type like ? " +
	    		 		 " and a.type_code like ? ");
    	templateParameterList.add(userID);
    	templateParameterList.add(typeCode);
    	templateParameterList.add(lotteryType);
    	
    	if(startDate!=null && endDate!=null){   
    		finalSql.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:00:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:00:00') " );
    	}
    	
    	if(periodsNum!=null && periodsNum!=""){
	    	 finalSql.append(" and a.periods_num = ? ");
	    	 templateParameterList.add(periodsNum);
	    }
	     
    	finalSql.append("GROUP BY COMMISSION_TYPE");
    	
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
		@SuppressWarnings("unchecked")
		List<UnClassReport> retList = jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(), new TotalMapperClass());
		return retList;
	}
    
    @Override
	public Page queryBetDetail(Page page, Long userId,Date startDate,Date endDate,String periodNum,String playType,String[] scanTableList,String myColumn) {
		
		List<DetailVO> retList = new ArrayList<DetailVO>();
		
		for (int i = 0; i < scanTableList.length; i++) {
			StringBuffer finalSql=new StringBuffer();
			StringBuffer sqTemplatelBuffer=new StringBuffer();	
			List<Object> finalParameterList=new ArrayList<Object>();
	    	List<Object> templateParameterList=new ArrayList<Object>();
			String tableName = scanTableList[i];
			String attribute="null";
			if(tableName.equals(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME) || tableName.equals(Constant.NC_TABLE_NAME))
				attribute="max(a.attribute)";
			    sqTemplatelBuffer.append( " select " +
								"a.order_no          as orderNo," +
								"max(a.betting_date)      as bettingDate," +
								"a.periods_num 	     as periodsNum," +
								"a.type_code         as typeCode," +
								"max(b.account) 		     as userName," +
								"max(a.plate)             as plate," +
								"max(a.odds)   		 	 as odds," +
								"sum(a.money)   			 as money," +
								"100-max(a.COMMISSION_BRANCH)   				 as chiefCommission," +
								"100-max(a.COMMISSION_STOCKHOLDER)   		 as branchCommission," +
								"100-max(a.COMMISSION_GEN_AGENT)   		 	 as stockCommission," +
								"100-max(a.COMMISSION_AGENT)   				 as genAgentCommission," +
								"100-max(a.COMMISSION_MEMBER)   				 as agentCommission," +
								"max(a.RATE_CHIEF)   					 as chiefRate," +
								"max(a.RATE_BRANCH)   					 as branchRate," +
								"max(a.RATE_STOCKHOLDER)   				 as stockRate," +
								"max(a.RATE_GEN_AGENT)   				 as genAgentRate," +
								"max(a.RATE_AGENT)  			 		 as agentRate," +
								attribute+"		 					 as attribute ," +
								"max(b.PARENT_STAFF_TYPE_QRY)		 	 as parentUserType," +
								"count(order_no) as count from "
					+ tableName + " a ,tb_frame_member_staff b ");
					
			        sqTemplatelBuffer.append(" where  a." + myColumn + "=? and a.betting_user_id=b.id ");
					
					templateParameterList.add(userId);  
					
			sqTemplatelBuffer.append(" and a.commission_type = ? and user_type!=7 ");
		    templateParameterList.add(playType);
		    
		    if(startDate!=null && endDate!=null){ 
		    	sqTemplatelBuffer.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:00:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:00:00') " ); 
		    }

	    	if(periodNum!=null && periodNum!=""){
	    		sqTemplatelBuffer.append(" and a.periods_num=? " );
	    		templateParameterList.add(periodNum);
	    	}
	    	sqTemplatelBuffer.append(" group by a.order_no,type_code,a.periods_num " );
			finalSql.append(sqTemplatelBuffer);
			finalParameterList.addAll(Lists.newArrayList(templateParameterList));
			
			List betlist = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapperClass());
			retList.addAll(betlist);
			
		}
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		Collections.sort(retList);
		page.setTotalCount(retList.size());
		if(first>last){first=0;last=0;}
		page.setResult(retList.subList(first, last));
		return page;

	}
    
    @Override
	public Page queryReplenishOutDetail(Page page, Long userId,String commissionTypeCode,String periodsNum,String userType,Date startDate,Date endDate,String lotteryType) {
		
		List<DetailVO> retList = new ArrayList<DetailVO>();
		List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	
		StringBuffer finalSql=new StringBuffer();
		StringBuffer sqTemplatelBuffer=new StringBuffer();	
		String tableName = "TB_REPLENISH";
		
		sqTemplatelBuffer.append( " select " +
				"a.order_no          as orderNo," +
				"a.betting_date      as bettingDate," +
				"a.periods_num 	     as periodsNum," +
				"a.type_code         as typeCode," +
				"b.account 		     as userName," +
				"a.plate             as plate," +
				"a.odds   		 	 as odds," +
				"a.money   			 as money," +
				"100-a.COMMISSION_CHIEF   				 as chiefCommission," +
				"100-a.COMMISSION_BRANCH   				 as branchCommission," +
				"100-a.COMMISSION_STOCKHOLDER   		 as stockCommission," +
				"100-a.COMMISSION_GEN_AGENT   		 	 as genAgentCommission," +
				"100-a.COMMISSION_AGENT   				 as agentCommission," +
				"a.RATE_CHIEF   					 as chiefRate," +
				"a.RATE_BRANCH   					 as branchRate," +
				"a.RATE_STOCKHOLDER   				 as stockRate," +
				"a.RATE_GEN_AGENT   				 as genAgentRate," +
				"a.RATE_AGENT   			 		 as agentRate," +
				"a.attribute		 				 as attribute ," +
				"b.PARENT_STAFF_TYPE_QRY		 	 as parentUserType," +
				"1 as count "
				+ " from "
				+ tableName + " a ,tb_frame_manager_staff b ");
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
					sqTemplatelBuffer.append(" where a.replenish_user_id=b.id and a.replenish_user_id " +
							"in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?)  "); 
				}else{
					sqTemplatelBuffer.append(" where a.replenish_user_id=b.id and a.replenish_user_id=? "); 
				}
		templateParameterList.add(userId);  
		
		if(startDate!=null && endDate!=null){ 
			sqTemplatelBuffer.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:00:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:00:00') " );
		}
		
		if(periodsNum!=null && periodsNum!=""){
			sqTemplatelBuffer.append(" and a.periods_num = ? ");
			templateParameterList.add(periodsNum);  
		}
		
		if(commissionTypeCode!=null){
			sqTemplatelBuffer.append(" and a.commission_type = ? ");
			templateParameterList.add(commissionTypeCode);  
		}
		sqTemplatelBuffer.append(" and a.type_code like ? and a.win_state='0' ");
		templateParameterList.add(lotteryType);//这个值是对应type_code,其他值在前面已经对应了
		
		//finalSql.append("select * from (");
		finalSql.append(sqTemplatelBuffer);
		//finalSql.append(") where rankNum=1");
		
		finalParameterList.addAll(Lists.newArrayList(templateParameterList));
		
		List betlist = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapperClass());
		retList.addAll(betlist);
		
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		Collections.sort(retList);
		page.setTotalCount(retList.size());
		if(first>last){first=0;last=0;}
		page.setResult(retList.subList(first, last));
		return page;
		
	}
    
    public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}

class DetailItemMapperClass implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		DetailVO item = new DetailVO();
		item.setOrderNo(rs.getString("orderNo"));
		item.setBettingDate(rs.getTimestamp("bettingDate"));
		item.setPeriodsNum(rs.getString("periodsNum"));
		item.setAttribute(rs.getString("attribute"));
		item.setTypeCode(rs.getString("typeCode"));
		item.setUserName(rs.getString("userName"));
		item.setPlate(rs.getString("plate"));
		item.setMoney(rs.getInt("money"));
		item.setOdds(rs.getBigDecimal("odds"));
		item.setChiefCommission(rs.getBigDecimal("chiefCommission"));
		item.setBranchCommission(rs.getBigDecimal("branchCommission"));
		item.setStockCommission(rs.getBigDecimal("stockCommission"));
		item.setGenAgentCommission(rs.getBigDecimal("genAgentCommission"));
		item.setAgentCommission(rs.getBigDecimal("agentCommission"));
		item.setChiefRate(rs.getBigDecimal("chiefRate"));
		item.setBranchRate(rs.getBigDecimal("branchRate"));
		item.setStockRate(rs.getBigDecimal("stockRate"));
		item.setGenAgentRate(rs.getBigDecimal("genAgentRate"));
		item.setAgentRate(rs.getBigDecimal("agentRate"));
		item.setParentUserType(rs.getString("parentUserType"));
		item.setCount(rs.getInt("count"));
		return item;
	}
}

class GDBetDetailItemMapperClass implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BaseBet item = new BaseBet();
		// item.setId(rs.getInt("id"));
		item.setBettingDate(rs.getTimestamp("bettingDate"));
		item.setTypeCode(rs.getString("typeCode"));
		item.setAttribute(rs.getString("attribute"));
		item.setOrderNo(rs.getString("orderNo"));
		item.setPeriodsNum(rs.getString("periodsNum"));
		item.setBettingUserId(rs.getLong("bettingUserId"));
		item.setMoney(rs.getInt("money"));
		item.setOdds(rs.getBigDecimal("odds"));
		item.setPlate(rs.getString("plate"));
		item.setCount(rs.getInt("count"));
		item.setWinState(rs.getString("winState"));
		item.setUserName(rs.getString("userName"));
		item.setRate(String.valueOf(rs.getBigDecimal("rate")));
		return item;
	}
	
	

}


class DeliveryReportItemMapperClass implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

    	UnClassReport reportInfo = new UnClassReport();
    	reportInfo.setTurnover(rs.getLong("count"));
        reportInfo.setAmount(rs.getDouble("totalMoney"));
        reportInfo.setMoneyRateAgent(rs.getDouble("moneyRateAgent"));
        reportInfo.setMoneyRateGenAgent(rs.getDouble("moneyRateGenAgent"));
        reportInfo.setMoneyRateStockholder(rs.getDouble("moneyRateStockholder"));
        reportInfo.setMoneyRateBranch(rs.getDouble("moneyRateBranch"));
        reportInfo.setMoneyRateChief(rs.getDouble("moneyRateChief"));
        reportInfo.setRateMoney(rs.getDouble("rateMoney"));
        reportInfo.setCommissionType(rs.getString("commissionType"));
        return reportInfo;
    }
}

class TotalMapperClass implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	UnClassReport reportInfo = new UnClassReport();
    	reportInfo.setTurnover(rs.getLong("turnover"));
        reportInfo.setAmount(rs.getDouble("amount"));
        reportInfo.setCommissionType(rs.getString("commissionType"));
        return reportInfo;
    }
}