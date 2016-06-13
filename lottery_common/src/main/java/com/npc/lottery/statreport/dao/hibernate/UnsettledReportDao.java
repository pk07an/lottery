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
import com.npc.lottery.statreport.dao.interf.IUnsettledReportDao;
import com.npc.lottery.statreport.entity.DeliveryUnReport;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.util.Page;

/**
 * 报表统计相关的数据库处理类
 * 
 */
public class UnsettledReportDao implements IUnsettledReportDao {

    private static Logger log = Logger.getLogger(UnsettledReportDao.class);
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public List<DeliveryUnReport> queryUnSettledReport(Date startDate,Date endDate,String lotteryType,String playType,String periodNum,Long userid,String userType
    		,String myColumn,String rateUser,String commissionUser,String nextColumn,String[] scanTableList)
    {
    	StringBuffer finalSql=new StringBuffer();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	
    	finalSql.append("select id,max(userType) as userType,max(parentUserType) as parentUserType,max(account) account," +
    			"max(chs_name) chName,sum(count) count,sum(totalMoney) totalMoney,sum(rateMoney) rateMoney, " +
    			"sum(moneyRateAgent) as moneyRateAgent," +
    			"sum(moneyRateGenAgent) as moneyRateGenAgent, " +
    			"sum(moneyRateStockholder) as moneyRateStockholder, " +
    			"sum(moneyRateBranch) as moneyRateBranch, " +
    			"sum(moneyRateChief) as moneyRateChief "+
    			"from (");
    	
    	sqTemplatelBuffer.append(" select b.id,max(b.user_type) as userType,max(b.parent_staff_type_qry) as parentUserType," +
    			"max(b.account) account,max(b.chs_name) chs_name, count(distinct order_no) as count,sum(money) as totalMoney,");
    	
    	sqTemplatelBuffer.append(" sum(money * a.RATE_AGENT/100) as moneyRateAgent," +
    			"sum(money * a.RATE_GEN_AGENT/100) as moneyRateGenAgent, " +
    			"sum(money * a.RATE_STOCKHOLDER/100) as moneyRateStockholder, " +
    			"sum(money * a.RATE_BRANCH/100) as moneyRateBranch, " +
    			"sum(money * a.RATE_CHIEF/100) as moneyRateChief, "+
    			"sum(money * a." + rateUser + "/100) as rateMoney ");
    			
    	sqTemplatelBuffer.append(" from {TableName} a, (select t1.id,t1.account,t1.chs_name,t1.user_type,t1.parent_staff_type_qry from tb_frame_manager_staff t1 union select  t2.id,t2.account,t2.chs_name,t2.user_type,t2.parent_staff_type_qry from tb_frame_member_staff t2 ) b ");
    	
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
    	
		sqTemplatelBuffer.append(" and a.commission_type like ? " );
		templateParameterList.add(playType);
		
		if(periodNum!=null && periodNum!="")
    	{
    		sqTemplatelBuffer.append(" and a.periods_num=? " );
    		templateParameterList.add(periodNum);
    	}
    	sqTemplatelBuffer.append(" group by b.id ");
    	
        for (int i = 0; i < scanTableList.length; i++) {
    	  finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", scanTableList[i]));
    	  if(i!=scanTableList.length-1)
    	  finalSql.append(" union all ");
    	  finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	  
         }
        if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){
        	finalSql.append(" union all ");
        	
        	finalSql.append(" select decode(a." + nextColumn + ",'',a.replenish_user_id,a." + nextColumn + ") as id," +
        			"max(b.user_type) as userType,max(b.parent_staff_type_qry) as parentUserType," +
        			"max(b.account) account,max(b.chs_name) chs_name, 0 as count,0 as totalMoney,");
        	
        	finalSql.append(" sum(money * a.RATE_AGENT/100) as moneyRateAgent," +
        			"sum(money * a.RATE_GEN_AGENT/100) as moneyRateGenAgent, " +
        			"sum(money * a.RATE_STOCKHOLDER/100) as moneyRateStockholder, " +
        			"sum(money * a.RATE_BRANCH/100) as moneyRateBranch, " +
        			"sum(money * a.RATE_CHIEF/100) as moneyRateChief, " +
        			"sum(money * a." + rateUser + "/100) as rateMoney ");
        	
        	finalSql.append("from tb_replenish a,"  +
                   "(select t1.id, t1.account, t1.chs_name, t1.user_type,t1.parent_staff_type_qry from tb_frame_manager_staff t1) b " +
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
            
            finalSql.append(" group by decode(a." + nextColumn + ",'',a.replenish_user_id,a." + nextColumn + ") ");
            
        	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
        }
        
        finalSql.append(") group by id ") ;
        List<DeliveryUnReport> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new DeliveryReportItemMapper());
         return reportList;

    }
    
    //查看补出的货的汇总
    @Override
	public List<DeliveryUnReport> queryReplenish(Date startDate,Date endDate,Long userID,String typeCode,String periodsNum,String rateUser,String lotteryType,String userType) {
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	finalSql.append("select count(*) 	              as turnover," +
						     "sum(money)  				  as amount, ");
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
    		finalSql.append(userID + " as userID ");
    	}else{
    		finalSql.append("a.replenish_user_id as userID ");
    	}
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
	     
    	finalSql.append("GROUP BY replenish_user_id");
    	
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
		@SuppressWarnings("unchecked")
		List<DeliveryUnReport> retList = jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(), new TotalMapper());
		return retList;
	}
    
    @Override
	public Page queryGDKLSFUserBetDetail(Page page, Date startDate,Date endDate,Long userId,String typeCode,String periodsNum,String rateUser) {

		
		List<BaseBet> retList = new ArrayList<BaseBet>();
		for (int i = 0; i < Constant.GDKLSF_TABLE_LIST.length; i++) {
			String tableName = Constant.GDKLSF_TABLE_LIST[i];
			String attribute="null";
			if(tableName.equals(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME))
				attribute="attribute";
			String sql = " select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,a." + rateUser + " as rate," +
					     "(select account from tb_frame_member_staff b where b.id=a.betting_user_id) as userName," +
					     "periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds," +
						 "order_no as orderNo,"+attribute+" as attribute ," +
						 "rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
					+ " from "
					+ tableName				
					+ " a where betting_user_id= ? and a.commission_type like ? and a.betting_date >= ? and a.betting_date <= ? ";
			Object[] parameters = new Object[] { userId,typeCode,startDate,endDate };
			sql="select * from ("+sql+") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters,new GDBetDetailItemMapper());
			retList.addAll(betlist);
			
		}
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		Collections.sort(retList);
		page.setTotalCount(retList.size());
		page.setResult(retList.subList(first, last));
		return page;

	}
    @Override
	public Page queryCQSSCUserBetDetail(Page page, Date startDate,Date endDate,Long userId,String typeCode,String periodsNum,String rateUser) {
//"+tableName+" as tableName 
		List<BaseBet> retList = new ArrayList<BaseBet>();
		for (int i = 0; i < Constant.CQSSC_TABLE_LIST.length; i++) {
			String tableName = Constant.CQSSC_TABLE_LIST[i];
			String sql = " select win_state as winState, type_code as typeCode,money as money,betting_user_id as bettingUserId,a." + rateUser + " as rate," +
						"(select account from tb_frame_member_staff b where b.id=a.betting_user_id) as userName," +	
						"periods_num as periodsNum,plate as plate,betting_date as bettingDate,odds as odds,order_no as orderNo," +
						"null as attribute,rank() over(partition by order_no order by rownum  desc) rankNum," +
						"count(*) over(partition by order_no) count "
					+ " from "
					+ tableName				
					+ " a where betting_user_id= ? and a.commission_type like ? and a.betting_date >= ? and a.betting_date <= ? ";
			Object[] parameters = new Object[] { userId,typeCode,startDate,endDate };
			sql="select * from ("+sql+") where rankNum=1";
			List betlist = jdbcTemplate.query(sql, parameters,
					new GDBetDetailItemMapper());

			retList.addAll(betlist);
		}
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		Collections.sort(retList);
		page.setTotalCount(retList.size());
		page.setResult(retList.subList(first, last));
		return page;

	}
    
    @Override
	public Page queryBetDetail(Page page, Long userId,Date startDate,Date endDate,String periodNum,String playType,String[] scanTableList) {
		
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
								"max(a.RATE_AGENT )  			 		 as agentRate," +
								attribute+"		 					     as attribute ," +
								"max(b.user_type)		 				 as userType," +
								"count(order_no) as count "
					+ " from "
					+ tableName + " a ,tb_frame_member_staff b"				
					+ " where a.betting_user_id= ? "
					+ " and a.betting_user_id= b.id "
					+ " and a.commission_type like ? ");
			templateParameterList.add(userId);  
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
			
			List betlist = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapper());
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
		
		List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	
		StringBuffer finalSql=new StringBuffer();
		StringBuffer sqTemplatelBuffer=new StringBuffer();	
		String tableName = "TB_REPLENISH";
		
		sqTemplatelBuffer.append( " select " +
				"a.order_no          as orderNo," +
				"max(a.betting_date)      as bettingDate," +
				"a.periods_num 	     as periodsNum," +
				"a.type_code         as typeCode," +
				"max(b.account) 		     as userName," +
				"max(a.plate)             as plate," +
				"max(a.odds)   		 	 as odds," +
				"sum(a.money)   			 as money,");
		
		//如果是总监查自己的补出时，是取总监字段的拥金值，因为这里是存出货会员的拥金值
		if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
			sqTemplatelBuffer.append("100-max(a.COMMISSION_CHIEF)  as chiefCommission,");
		}else{
			sqTemplatelBuffer.append("100-max(a.COMMISSION_BRANCH)  as chiefCommission,");
		}
				
		sqTemplatelBuffer.append("100-max(a.COMMISSION_STOCKHOLDER)   as branchCommission," +
				"100-max(a.COMMISSION_GEN_AGENT)   		 as stockCommission," +
				"100-max(a.COMMISSION_AGENT)   		 	 as genAgentCommission," +
				"100-max(a.COMMISSION_MEMBER)   		 as agentCommission," +
				"max(a.RATE_CHIEF)   					 as chiefRate," +
				"max(a.RATE_BRANCH)   					 as branchRate," +
				"max(a.RATE_STOCKHOLDER)   				 as stockRate," +
				"max(a.RATE_GEN_AGENT)   				 as genAgentRate," +
				"max(a.RATE_AGENT)  			 		 as agentRate," +
				"max(a.attribute)		 				 as attribute ," +
				userType+"		 					 as userType," +
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
			sqTemplatelBuffer.append(" and a.commission_type like ? ");
			templateParameterList.add(commissionTypeCode);  
		}
		sqTemplatelBuffer.append(" and a.type_code like ? and a.win_state='0' ");
		templateParameterList.add(lotteryType);//这个值是对应type_code,其他值在前面已经对应了
		
		sqTemplatelBuffer.append(" group by a.order_no,type_code,a.periods_num " );
		
		finalSql.append(sqTemplatelBuffer);
		finalParameterList.addAll(Lists.newArrayList(templateParameterList));
		
		List<DetailVO> betlist = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapper());
		
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > betlist.size())
			last = betlist.size();
		Collections.sort(betlist);
		page.setTotalCount(betlist.size());
		if(first>last){first=0;last=0;}
		page.setResult(betlist.subList(first, last));
		return page;
		
	}
    
    public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}

class DetailItemMapper implements RowMapper {
	
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
		item.setUserType(rs.getString("userType"));
		item.setCount(rs.getInt("count"));
		return item;
	}
}

class GDBetDetailItemMapper implements RowMapper {

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


class HKBetDetailItemMapper implements RowMapper {

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
		item.setOdds2(rs.getBigDecimal("odds2"));
		item.setPlate(rs.getString("plate"));
		item.setCount(rs.getInt("count"));
		item.setSelectedOdds(rs.getString("selectOdds"));
		item.setWinState(rs.getString("winState"));
		item.setUserName(rs.getString("userName"));
		item.setRate(String.valueOf(rs.getBigDecimal("rate")));
		return item;
	}
	
	

}

class DeliveryReportItemMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

    	DeliveryUnReport reportInfo = new DeliveryUnReport();
    	reportInfo.setUserID(rs.getLong("id"));
    	reportInfo.setUserType(rs.getString("userType"));
    	reportInfo.setParentUserType(rs.getString("parentUserType"));
    	reportInfo.setSubordinate(rs.getString("account"));  	
    	reportInfo.setUserName(rs.getString("chName"));
    	reportInfo.setTurnover(rs.getLong("count"));
        reportInfo.setAmount(rs.getDouble("totalMoney"));
        reportInfo.setMoneyRateAgent(rs.getDouble("moneyRateAgent"));
        reportInfo.setMoneyRateGenAgent(rs.getDouble("moneyRateGenAgent"));
        reportInfo.setMoneyRateStockholder(rs.getDouble("moneyRateStockholder"));
        reportInfo.setMoneyRateBranch(rs.getDouble("moneyRateBranch"));
        reportInfo.setMoneyRateChief(rs.getDouble("moneyRateChief"));
        reportInfo.setRateMoney(rs.getDouble("rateMoney"));
        return reportInfo;
    }
}

class TotalMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	DeliveryUnReport reportInfo = new DeliveryUnReport();
    	reportInfo.setTurnover(rs.getLong("turnover"));
        reportInfo.setAmount(rs.getDouble("amount"));
        reportInfo.setUserID(rs.getLong("userID"));
        return reportInfo;
    }
}