package com.npc.lottery.statreport.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.google.common.collect.Lists;
import com.npc.lottery.common.Constant;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.replenish.vo.DetailVO;
import com.npc.lottery.statreport.dao.interf.IClassReportEricDao;
import com.npc.lottery.statreport.entity.ClassReportEric;
import com.npc.lottery.statreport.entity.ClassReportPetList;
import com.npc.lottery.statreport.entity.ClassReportPetPeriod;
import com.npc.lottery.statreport.entity.ClassReportRList;
import com.npc.lottery.statreport.entity.ClassReportRPeriod;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.util.Page;

/**
 * 报表统计相关的数据库处理类
 * 
 */
public class ClassReportEricDao implements IClassReportEricDao {

    private static Logger log = Logger.getLogger(ClassReportEricDao.class);
    private JdbcTemplate jdbcTemplate;
  //处理输、赢、和、问题
    String handleWinAmout = "(case when win_state=1 then win_amount when win_state=2 then -money when win_state=9 or win_state=4 or win_state=5 then 0 else 0 end )";
    String handleMoney = "(case when win_state=9 or win_state=4 or win_state=5 then 0 else money end )";
    @Override
    public List<ClassReportEric> queryClassReport(Date startDate,Date endDate,String lotteryType,String playType,String periodsNum,Long userid,String userType
    		,String myColumn,String rateUser,String nextRateColumn,String commissionUser,String nextColumn,String[] scanTableList,
    		String underLineRateColumn,String nextCommissionColumn,String winState,String schema)
    {
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
    	
    	StringBuffer finalSql=new StringBuffer();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterListPet=new ArrayList<Object>();
    	List<Object> templateParameterListReplenish=new ArrayList<Object>();
    	
    	finalSql.append("select commissionType, sum(count) count,max(periodsNum) as periodsNum,max(bettingDate) as bettingDate," +
    			"sum(totalMoney) totalMoney,sum(rateMoney) rateMoney, " +
    			"sum(memberAmount)					 as memberAmount," +
    			"sum(subordinateAmountWin) 			 as subordinateAmountWin," +
    			"sum(subordinateAmountBackWater)     as subordinateAmountBackWater," +
    			"sum(realWin) 					     as realWin," +
    			"sum(realBackWater) 				 as realBackWater," +
    			"sum(commission) 				     as commission," +
    			"sum(moneyRateAgent) as moneyRateAgent," +
    			"sum(moneyRateGenAgent) as moneyRateGenAgent, " +
    			"sum(moneyRateStockholder) as moneyRateStockholder, " +
    			"sum(moneyRateBranch) as moneyRateBranch, " +
    			"sum(moneyRateChief) as moneyRateChief, "+
    			userType+"		 					 as userType, " +
    			userid+"		 					 as userId " +
    			"from (");
    	
    	String underLine = "";
		if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){//如果是代理，就不用統下級占成字段
			underLine = "* (" + underLineRateColumn + ")";
		}else{
			underLine = "* 0";
		}
		
    	sqTemplatelBuffer.append(" select a.COMMISSION_TYPE as commissionType, count(distinct order_no) as count," +
    			"max(a.periods_num) as periodsNum,max(a.betting_date-numtodsinterval(3,'hour')) as bettingDate," +
    			"sum("+handleMoney+") as totalMoney,sum("+handleWinAmout+") as memberAmount," +    /*会员输赢*/
    			"sum("+handleWinAmout+") - sum("+handleWinAmout+" " + underLine + ") as subordinateAmountWin," + /*应收下线的输赢       公式：会员输赢*(100%-下级的占成和 )*/
    			/*应收下线的退水     公式：有效投注金额*给下级的退水率*(100%-下级占成和 )*/
    			"sum("+handleMoney+"*" +
    					"(case when b.parent_staff_type_qry!='6' and b.user_type='9' then commission_member else " + nextCommissionColumn + " end)/100) - " 
						+ "sum("+handleMoney+"*" 
    					+ nextCommissionColumn + "/100 " + underLine + ") as subordinateAmountBackWater," +  
				"sum("+handleWinAmout+" * " + rateUser + "/100) as realWin," + /*实占输赢     公式：会员投注结果*查看者自已的占成*/
    	        "sum("+handleMoney+"*" + commissionUser + "/100 * " + rateUser + "/100) as realBackWater," + /*实占退水     公式：指会员投注有效金额*查看者自已的退水率*查看者的占成*/
    	        /*赚取退水     公式：－1*会员投注有效注额*（自已的退水－帐号对应的退水）*（1－对应帐号本身及以下管理占成的总和）总和*/
    	        "sum(-"+handleMoney+" * " + commissionUser + "/100) " +
	        		"- sum(-"+handleMoney+"*(case when b.parent_staff_type_qry!='6' and b.user_type='9' then commission_member else " + nextCommissionColumn + " end)/100) - (" + 
	        		"sum(-"+handleMoney+"*" + commissionUser + "/100 " + underLine + ") - sum(-"+handleMoney+"*" + nextCommissionColumn + "/100 " + underLine + ")) as commission,");  
    	
    	sqTemplatelBuffer.append(" sum("+handleMoney+" * a.RATE_AGENT/100) as moneyRateAgent," +
    			"sum("+handleMoney+" * a.RATE_GEN_AGENT/100) as moneyRateGenAgent, " +
    			"sum("+handleMoney+" * a.RATE_STOCKHOLDER/100) as moneyRateStockholder, " +
    			"sum("+handleMoney+" * a.RATE_BRANCH/100) as moneyRateBranch, " +
    			"sum("+handleMoney+" * a.RATE_CHIEF/100) as moneyRateChief, "+
    			"sum("+handleMoney+" * a." + rateUser + "/100) as rateMoney ");
    			
    	sqTemplatelBuffer.append(" from {TableName} a, (select t1.id,t1.account,t1.chs_name,t1.user_type,"
    			+ "t1.parent_staff_type_qry from "+schema+"tb_frame_manager_staff t1 "
    			+ "union select  t2.id,t2.account,t2.chs_name,t2.user_type,t2.parent_staff_type_qry from "+schema+"tb_frame_member_staff t2 ) b ");
    	
    	sqTemplatelBuffer.append(this.getSqlByUserType(userType));
    	templateParameterListPet.add(userid);
    	
    	if(startDate!=null && endDate!=null){
    		sqTemplatelBuffer.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:00:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:00:00') " );
    	}
    	
		//sqTemplatelBuffer.append(" and a.commission_type like ? and a.win_state not in('9','4','5') " );
		//templateParameterListPet.add(playType);
    	if(!"%".equals(playType)){
    		sqTemplatelBuffer.append(" and a.commission_type like ? " );
    		templateParameterListPet.add(playType);
    	}
		
		if(periodsNum!=null && periodsNum!="")
    	{
    		sqTemplatelBuffer.append(" and a.periods_num=? " );
    		templateParameterListPet.add(periodsNum);
    	}
    	sqTemplatelBuffer.append(" group by a.COMMISSION_TYPE ");
    	
    	//如果winState的状态为9即打和的话，就把打和的笔数的加和。
			/*sqTemplatelBuffer.append(" union all ");
			sqTemplatelBuffer.append("select a.COMMISSION_TYPE as commissionType,count(distinct order_no) count,0 totalMoney,0 rateMoney, " +
	    			"0 as memberAmount,0 as subordinateAmountWin,0 as subordinateAmountBackWater,0 as realWin,0 as realBackWater," +
	    			"0 as commission,0 as moneyRateAgent,0 as moneyRateGenAgent,0 as moneyRateStockholder, 0 as moneyRateBranch, 0 as moneyRateChief ");
			
			sqTemplatelBuffer.append(" from {TableName} a, (select t1.id,t1.account,t1.chs_name,t1.user_type from tb_frame_manager_staff t1 union select  t2.id,t2.account,t2.chs_name,t2.user_type from tb_frame_member_staff t2 ) b ");
	    	
	    	sqTemplatelBuffer.append(this.getSqlByUserType(userType));
	    	templateParameterListPet.add(userid);
	    	
	    	if(startDate!=null && endDate!=null)
	    		sqTemplatelBuffer.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:00:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:00:00') " );
	    	
			sqTemplatelBuffer.append(" and a.commission_type like ? and a.win_state in('9','4','5') " );
			templateParameterListPet.add(playType);
			
			if(periodsNum!=null && periodsNum!="")
	    	{
	    		sqTemplatelBuffer.append(" and a.periods_num=? " );
	    		templateParameterListPet.add(periodsNum);
	    	}
	    	sqTemplatelBuffer.append(" group by a.COMMISSION_TYPE ");*/
    	
        for (int i = 0; i < scanTableList.length; i++) {
    	  finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", schema+scanTableList[i]));
    	  if(i!=scanTableList.length-1)
    	  finalSql.append(" union all ");
    	  finalParameterList.addAll(Lists.newArrayList(templateParameterListPet));
    	  
         }
        if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){
        	finalSql.append(" union all ");
        	
        	finalSql.append(" select a.COMMISSION_TYPE as commissionType, " +
        			"0 as count,max(a.periods_num) as periodsNum,max(a.betting_date-numtodsinterval(3,'hour')) as bettingDate,0 as totalMoney," +
        			"0 as memberAmount," +    /*会员输赢*/
        			"sum("+handleWinAmout+") - sum("+handleWinAmout+" * (" + underLineRateColumn + ")) as subordinateAmountWin," + /*应收下线的输赢       公式：会员输赢*(100%-下级的占成和 )*/
        			/*应收下线的退水     公式：有效投注金额*给下级的退水率*(100%-下级占成和 )*/
    				"sum(money*" + nextCommissionColumn + "/100) - " + "sum(money*" + nextCommissionColumn + "/100 * (" + underLineRateColumn + ")) as subordinateAmountBackWater," + 
    				"sum("+handleWinAmout+" * " + rateUser + "/100) as realWin," + /*实占输赢     公式：会员投注结果*查看者自已的占成*/
        	        "sum(money*" + commissionUser + "/100 * " + rateUser + "/100) as realBackWater," + /*实占退水     公式：指会员投注有效金额*查看者自已的退水率*查看者的占成*/
        	        /*赚取退水     公式：－1*会员投注有效注额*（自已的退水－帐号对应的退水）*（1－对应帐号本身及以下管理占成的总和）总和*/
        	        "sum(-money*" + commissionUser + "/100) - sum(-money*" + nextCommissionColumn + "/100) - (" + 
        	        "sum(-money*" + commissionUser + "/100 *(" + underLineRateColumn + ")) - sum(-money*" + nextCommissionColumn + "/100 * (" + underLineRateColumn + "))) as commission,");
        	
        	finalSql.append(" sum(money * a.RATE_AGENT/100) as moneyRateAgent," +
        			"sum(money * a.RATE_GEN_AGENT/100) as moneyRateGenAgent, " +
        			"sum(money * a.RATE_STOCKHOLDER/100) as moneyRateStockholder, " +
        			"sum(money * a.RATE_BRANCH/100) as moneyRateBranch, " +
        			"sum(money * a.RATE_CHIEF/100) as moneyRateChief, " +
        			"sum(money * a." + rateUser + "/100) as rateMoney ");
        	
        	finalSql.append("from "+schema+"TB_REPLENISH_HIS a,"  +
                   "(select t1.id, t1.account, t1.chs_name, t1.user_type from "+schema+"tb_frame_manager_staff t1) b " +
	              "where a." + myColumn + " = ?" +
	              " and decode(a." + nextColumn + ",'',a.replenish_user_id,a." + nextColumn + ") = b.id" +
	              " and user_type != 7 " +
	              " and a.win_state !='0' ");
        	templateParameterListReplenish.add(userid);
        	
        	if(startDate!=null && endDate!=null){   
        		finalSql.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:00:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:00:00') " );
        	}
        	//只计算补出,补入的笔数不用统计
        	finalSql.append(" and a.commission_type like ? and a.win_state not in('9','4','5') ");
        	templateParameterListReplenish.add(playType);
        	
            if(periodsNum!=null && periodsNum!=""){
            	finalSql.append(" and a.periods_num=? " );
            	templateParameterListReplenish.add(periodsNum);
            }
            finalSql.append(" and a.type_code like ? ");
            templateParameterListReplenish.add(lotteryType);//这个值是对应type_code,其他值在前面已经对应了
            
            finalSql.append(" group by a.COMMISSION_TYPE ");
            
        	finalParameterList.addAll(Lists.newArrayList(templateParameterListReplenish));
        	
        }
        
        finalSql.append(") group by commissionType ") ;
         List<ClassReportEric> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new DeliveryReportItemMapperC());
         return reportList;

    }
    
    private String getSqlByUserType(String userType){
    	String sqlUserType = "";
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType))
    	{
    		sqlUserType=" where  a.chiefstaff=? and decode(a.branchstaff,0,a.betting_user_id,a.branchstaff)=b.id ";
    	}
    	
    	else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType))
    	{
    		sqlUserType=" where  a.branchstaff=? and decode(a.stockholderstaff,0,a.betting_user_id,a.stockholderstaff)=b.id ";
    	}
    	else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType))
    	{
    		sqlUserType=" where  a.stockholderstaff=? and decode(a.genagenstaff,0,a.betting_user_id,a.genagenstaff)=b.id ";
    	}
    	else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType))
    	{
    		sqlUserType=" where  a.genagenstaff=? and decode(a.agentstaff,0,a.betting_user_id,a.agentstaff)=b.id ";
    	}
    	else if(ManagerStaff.USER_TYPE_AGENT.equals(userType))
    	{
    		sqlUserType=" where  a.agentstaff=? and a.betting_user_id=b.id and user_type!=7 ";
    	}	
    	return sqlUserType;
    }
    
    //查看补出的货的汇总
    @Override
	public List<ClassReportEric> queryReplenish(Date startDate,Date endDate,Long userID,String typeCode,String periodsNum,String rateUser
			,String lotteryType,String userType,String commissionUser,String winState,String tableName,String schema) {
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	finalSql.append("select sum(turnover) as turnover,sum(amount) as amount," +
    				    "max(periodsNum) as periodsNum,max(bettingDate-numtodsinterval(3,'hour')) as bettingDate," +
    					"sum(memberAmount) as memberAmount, commissionType as commissionType," +
				 		"sum(winBackWater)   as winBackWater,sum(backWaterResult)  as backWaterResult, "+userID+" as userId FROM (");
    	
    	finalSql.append("select count(*) 	              as turnover," +
						     "sum("+handleMoney+")  				  as amount, " +
						     "max(a.periods_num)          as periodsNum,max(a.betting_date) as bettingDate, " +
							 "sum("+handleWinAmout+")  			  as memberAmount, " +
							 "sum("+handleMoney+" * " + commissionUser + "/100)  as winBackWater, " +
							 "sum("+handleWinAmout+") + sum("+handleMoney+" * " + commissionUser + "/100)  as backWaterResult, " +
    						 "a.COMMISSION_TYPE  		  as commissionType ");
    	
    	finalSql.append(" from "+schema+tableName+" a  " +
				     " where ");
	     if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
				finalSql.append(" replenish_user_id in (select b.manager_staff_id from "+schema+"tb_out_replenish_staff_ext b where b.parent_staff=?) ");
				
		 }else{
			finalSql.append(" replenish_user_id=? ");
		 }		     
	     finalSql.append(" and a.win_state != '0'  and a.commission_type like ? and a.type_code like ? ");
	     //finalSql.append(" and a.win_state != '0'  and a.commission_type like ? and a.type_code like ? and a.win_state not in('9','4','5') ");
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
    	
    	//如果winState的状态为9即打和的话，就把打和的笔数的加和。
			/*finalSql.append(" union all ");
			finalSql.append("select count(distinct order_no) as turnover,0 as amount, 0 as memberAmount, 0 as winBackWater,0 as backWaterResult,a.COMMISSION_TYPE as commissionType ");

			finalSql.append(" from "+tableName+" a where ");
			if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
				finalSql.append(" replenish_user_id in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
					
			}else{
				finalSql.append(" replenish_user_id=? ");
			}		     
			finalSql.append(" and a.win_state != '0' and a.commission_type like ? and a.type_code like ? and a.win_state in('9','4','5') ");
			if(startDate!=null && endDate!=null){   
	    		finalSql.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:00:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:00:00') " );
	    	}
	    	
	    	if(periodsNum!=null && periodsNum!=""){
		    	 finalSql.append(" and a.periods_num = ? ");
		    }
	    	finalSql.append("GROUP BY COMMISSION_TYPE");
	    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));*/

    	finalSql.append(") GROUP BY commissionType");
		
		@SuppressWarnings("unchecked")
		List<ClassReportEric> retList = jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(), new TotalMapperC());
		return retList;
	}
    
    @Override
	public Page queryBetDetail(Page page, Long userId,Date startDate,Date endDate,String periodsNum,String playType,String[] scanTableList,
			String rateColumn,String commissionColumn,String myCommissionColumn, String myColumn) {
    	String handle = "(case when win_state=1 then win_amount when win_state=2 then -money when win_state=9 or win_state=4 or win_state=5 then 0 else 0 end )";
		List<DetailVO> retList = new ArrayList<DetailVO>();
		
		for (int i = 0; i < scanTableList.length; i++) {
			StringBuffer finalSql=new StringBuffer();
			StringBuffer sqTemplatelBuffer=new StringBuffer();	
			List<Object> finalParameterList=new ArrayList<Object>();
	    	List<Object> templateParameterList=new ArrayList<Object>();
			String tableName = scanTableList[i];
			String attribute = null;
			String topAttribute = null;
			if(Constant.GDKLSF_HIS_TABLE_NAME.equals(tableName) || Constant.NC_HIS_TABLE_NAME.equals(tableName)){
				topAttribute = "max(attribute)";
				attribute = "a.attribute";
				
			}
			sqTemplatelBuffer.append( "select orderNo,max(bettingDate) as bettingDate,periodsNum,typeCode,max(userName) AS userName,max(plate) AS plate," +
									"max(odds) AS odds,sum(money) AS money,max(chiefCommission) AS chiefCommission,max(branchCommission) AS branchCommission,"+
									"max(stockCommission) AS stockCommission,max(genAgentCommission) AS genAgentCommission,max(agentCommission) AS agentCommission,"+
									"max(chiefRate) AS chiefRate,max(branchRate) AS branchRate,max(stockRate) AS stockRate,max(genAgentRate) AS genAgentRate,"+
									"max(agentRate) AS agentRate,"+topAttribute+" AS attribute,max(userType) AS userType,max(parentUserType) AS parentUserType,"+
									"sum(winAmount) AS winAmount, sum(rateWinAmount) AS rateWinAmount,sum(rateBackWater) AS rateBackWater,count(orderNo) as count," +
									"max(winState) as winState FROM (" );
		    sqTemplatelBuffer.append( " select " +
							"a.order_no          as orderNo," +
							"a.betting_date      as bettingDate," +
							"a.periods_num 	     as periodsNum," +
							"a.type_code         as typeCode," +
							"b.account 		     as userName," +
							"a.plate             as plate," +
							"a.odds   		 	 as odds," +
							"a.money   			 as money," +
							"100-a.COMMISSION_BRANCH   				 as chiefCommission," +
							"100-a.COMMISSION_STOCKHOLDER   		 as branchCommission," +
							"100-a.COMMISSION_GEN_AGENT   		 	 as stockCommission," +
							"100-a.COMMISSION_AGENT  				 as genAgentCommission," +
							"100-a.COMMISSION_MEMBER   				 as agentCommission," +
							"a.RATE_CHIEF   					 as chiefRate," +
							"a.RATE_BRANCH   					 as branchRate," +
							"a.RATE_STOCKHOLDER   				 as stockRate," +
							"a.RATE_GEN_AGENT   				 as genAgentRate," +
							"a.RATE_AGENT  			 		 as agentRate," +
							attribute + "   		 			 as attribute ," +
							"b.user_type		 				 as userType," +
							"b.PARENT_STAFF_TYPE_QRY		 	 as parentUserType," +
							handle+"		 				 as winAmount," +
							handle+" * " + rateColumn + "/100		 	 as rateWinAmount," +
							"(case when win_state=9 then 0 else " +
								"(a.money*(case when b.parent_staff_type_qry!='6' and b.user_type='9' " +
								"then " + myCommissionColumn + " else " + commissionColumn + " end)/100*" + rateColumn + "/100) end ) as rateBackWater," +
							"a.win_state as winState from "
				+ tableName + " a ,tb_frame_member_staff b"				
				+ " where a." + myColumn + "= ? "
				+ " and a.betting_user_id= b.id "
				+ " and a.commission_type like ? and user_type!=7 ");
			templateParameterList.add(userId);  
		    templateParameterList.add(playType);
		    
		    if(startDate!=null && endDate!=null){ 
		    	sqTemplatelBuffer.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:00:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:00:00') " ); 
		    }

	    	if(periodsNum!=null && periodsNum!=""){
	    		sqTemplatelBuffer.append(" and a.periods_num=? " );
	    		templateParameterList.add(periodsNum);
	    	}
	    	sqTemplatelBuffer.append(") group by orderNo,typeCode,periodsNum " );
			finalSql.append(sqTemplatelBuffer);
			finalParameterList.addAll(Lists.newArrayList(templateParameterList));
			
			List betlist = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapperBetDetail());
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
	public Page queryReplenishOutDetail(Page page, Long userId,String commissionTypeCode,String periodsNum,String userType,Date startDate,Date endDate,
			String lotteryType,String rateColumn,String commissionColumn) {
		
		List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	
    	String handle = "(case when max(win_state)=1 then sum(win_amount) when max(win_state)=2 then sum(-money) " +
    			"when max(win_state)=9 or max(win_state)=4 or max(win_state)=5 then 0 else 0 end )";
    	
		StringBuffer finalSql=new StringBuffer();
		StringBuffer sqTemplatelBuffer=new StringBuffer();	
		String tableName = "tb_replenish_his";
		
		sqTemplatelBuffer.append( " select " +
				"a.order_no          as orderNo," +
				"max(a.betting_date)      as bettingDate," +
				"a.periods_num 	     as periodsNum," +
				"a.type_code         as typeCode," +
				"max(b.account) 		     as userName," +
				"max(a.plate)             as plate," +
				"max(a.odds)   		 	 as odds," +
				"sum(a.money)   			 as money," +
				"100-max(a.COMMISSION_CHIEF)   				 as chiefCommission," +
				"100-max(a.COMMISSION_BRANCH)   				 as branchCommission," +
				"100-max(a.COMMISSION_STOCKHOLDER)   		 as stockCommission," +
				"100-max(a.COMMISSION_GEN_AGENT)   		 	 as genAgentCommission," +
				"100-max(a.COMMISSION_AGENT)   				 as agentCommission," +
				"max(a.RATE_CHIEF)   					 as chiefRate," +
				"max(a.RATE_BRANCH)   					 as branchRate," +
				"max(a.RATE_STOCKHOLDER)   				 as stockRate," +
				"max(a.RATE_GEN_AGENT)   				 as genAgentRate," +
				"max(a.RATE_AGENT)   			 		 as agentRate," +
				"max(a.attribute)		 				 as attribute ," +
				"max("+userType+")		 					 as userType," +
				handle+"		 				 as winAmount," +
				"(case when max(win_state)=1 then sum(-win_amount) when max(win_state)=2 then sum(money) when max(win_state)=9 then 0 else 0 end ) as rateWinAmount," +
				"(case when max(win_state)=9 then 0 else (sum(-a.money)*max(" + commissionColumn + ")/100) end )		as rateBackWater," +
				"1 as count,max(a.win_state) as winState "
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
		sqTemplatelBuffer.append(" and a.type_code like ? and a.win_state not in('0') ");
		templateParameterList.add(lotteryType);//这个值是对应type_code,其他值在前面已经对应了
		
		//尾部
		sqTemplatelBuffer.append(" group by a.order_no,type_code,a.periods_num " );
				
		finalSql.append(sqTemplatelBuffer);
		finalParameterList.addAll(Lists.newArrayList(templateParameterList));
		
		List<DetailVO> betlist = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapperC());
		
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
    
    @Override
    public List<ClassReportPetList> queryClassReportPetList(Long userID,String userType,java.sql.Date startDate,java.sql.Date endDate){
    	Object[] parameters= {userID,userType};		
    	String sql = "select COMMISSION_TYPE as commissionType, sum(count) count,sum(TOTAL_MONEY) totalMoney,sum(RATE_MONEY) rateMoney, " +
		    			"sum(MEMBER_AMOUNT)					 as memberAmount," +
		    			"sum(SUBORDINATE_AMOUNT_WIN) 			 as subordinateAmountWin," +
		    			"sum(SUBORDINATE_AMOUNT_BACKWATER)     as subordinateAmountBackWater," +
		    			"sum(REALWIN) 					     as realWin," +
		    			"sum(REAL_BACKWATER) 				 as realBackWater," +
		    			"sum(commission) 				     as commission," +
		    			"sum(MONEY_RATE_AGENT) as moneyRateAgent," +
		    			"sum(MONEY_RATE_GENAGENT) as moneyRateGenAgent, " +
		    			"sum(MONEY_RATE_STOCKHOLDER) as moneyRateStockholder, " +
		    			"sum(MONEY_RATE_BRANCH) as moneyRateBranch, " +
		    			"sum(MONEY_RATE_CHIEF) as moneyRateChief, "+
		    			userType+"		 					 as userType " +
    			        " from tb_class_report_pet_list t where t.USER_ID =? and t.USER_TYPE=? " +
    			        " and t.BETTING_DATE between to_date('"+startDate+"','yyyy-MM-dd') and to_date('"+endDate+"','yyyy-MM-dd')" +
    			        " group by t.COMMISSION_TYPE";
    	List<ClassReportPetList> retList = jdbcTemplate.query(sql,parameters, new ClassReportPetListMapper());
		return retList;
    }
    @Override
    public List<ClassReportRList> queryClassReportRList(Long userID,String userType,java.sql.Date startDate,java.sql.Date endDate){
    	Object[] parameters= {userID,userType};		
    	String sql = "select sum(count) as turnover,sum(amount) as amount,sum(MEMBER_AMOUNT) as memberAmount, COMMISSION_TYPE as commissionType," +
		 		    "sum(WIN_BACK_WATER)   as winBackWater,sum(BACK_WATER_RESULT)  as backWaterResult " +
	    			" from tb_class_report_r_list t where t.USER_ID =? and t.USER_TYPE=? " +
	    			" and t.BETTING_DATE between to_date('"+startDate+"','yyyy-MM-dd') and to_date('"+endDate+"','yyyy-MM-dd')" +
	    			" group by t.COMMISSION_TYPE";
    	List<ClassReportRList> retList = jdbcTemplate.query(sql,parameters, new ClassReportRListMapper());
    	return retList;
    }
    
    @Override
    public List<ClassReportEric> queryClassReportPetPeriod(Long userID,String userType,java.sql.Date startDate,java.sql.Date endDate,
    		String periodsNum, String lotteryType,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameter=new ArrayList<Object>();
    	finalSql.append("select COMMISSION_TYPE as commissionType, sum(count) count,sum(TOTAL_MONEY) totalMoney,sum(RATE_MONEY) rateMoney, " +
    			"sum(MEMBER_AMOUNT)					 as memberAmount," +
    			"sum(SUBORDINATE_AMOUNT_WIN) 			 as subordinateAmountWin," +
    			"sum(SUBORDINATE_AMOUNT_BACKWATER)     as subordinateAmountBackWater," +
    			"sum(REALWIN) 					     as realWin," +
    			"sum(REAL_BACKWATER) 				 as realBackWater," +
    			"sum(commission) 				     as commission," +
    			"sum(MONEY_RATE_AGENT) as moneyRateAgent," +
    			"sum(MONEY_RATE_GENAGENT) as moneyRateGenAgent, " +
    			"sum(MONEY_RATE_STOCKHOLDER) as moneyRateStockholder, " +
    			"sum(MONEY_RATE_BRANCH) as moneyRateBranch, " +
    			"sum(MONEY_RATE_CHIEF) as moneyRateChief, "+
    			userType+"		 					 as userType " +
    			" from "+schema+"tb_c_report_pet_Period t where t.USER_ID =? and t.USER_TYPE=? ");
    	templateParameter.add(userID);
    	templateParameter.add(userType);
    	if(startDate!=null && endDate!=null){
    		finalSql.append(" and t.BETTING_DATE between to_date('"+startDate+"','yyyy-MM-dd') and to_date('"+endDate+"','yyyy-MM-dd')");
    	}
    	if(periodsNum!=null && periodsNum!=""){
    		finalSql.append(" and t.periods_num =?");
    		templateParameter.add(periodsNum);
    	}
    	if(lotteryType!=null && lotteryType!=""){
    		finalSql.append(" and t.LOTTERY_TYPE =?");
    		templateParameter.add(lotteryType);
    	}
    	finalSql.append(" group by t.COMMISSION_TYPE");
    	finalParameterList.addAll(Lists.newArrayList(templateParameter));
    	List<ClassReportEric> retList = jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(), new ClassReportPetPeriodMapper());
    	return retList;
    }
    @Override
    public List<ClassReportEric> queryClassReportRPeriod(Long userID,String userType,java.sql.Date startDate,java.sql.Date endDate,
    		String periodsNum, String lotteryType,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameter=new ArrayList<Object>();
    	finalSql.append("select sum(count) as turnover,sum(amount) as amount,sum(MEMBER_AMOUNT) as memberAmount, COMMISSION_TYPE as commissionType," +
    			"sum(WIN_BACK_WATER)   as winBackWater,sum(BACK_WATER_RESULT)  as backWaterResult " +
    			" from "+schema+"tb_c_report_r_Period t where t.USER_ID =? and t.USER_TYPE=? ");
    	templateParameter.add(userID);
    	templateParameter.add(userType);
    	if(startDate!=null && endDate!=null){
    		finalSql.append(" and t.BETTING_DATE between to_date('"+startDate+"','yyyy-MM-dd') and to_date('"+endDate+"','yyyy-MM-dd')");
    	}
    	if(periodsNum!=null && periodsNum!=""){
    		finalSql.append(" and t.periods_num =?");
    		templateParameter.add(periodsNum);
    	}
    	if(lotteryType!=null && lotteryType!=""){
    		finalSql.append(" and t.LOTTERY_TYPE =?");
    		templateParameter.add(lotteryType);
    	}
    	finalSql.append(" group by t.COMMISSION_TYPE");
    	finalParameterList.addAll(Lists.newArrayList(templateParameter));
    	List<ClassReportEric> retList = jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(), new ClassReportRPeriodMapper());
    	return retList;
    }
    
    
    
    public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}

class ClassReportPetListMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

	ClassReportPetList reportInfo = new ClassReportPetList();
    	reportInfo.setTurnover(rs.getLong("count"));
        reportInfo.setAmount(rs.getDouble("totalMoney"));
        reportInfo.setRateMoney(rs.getDouble("rateMoney"));
        reportInfo.setMemberAmount(rs.getDouble("memberAmount"));
        reportInfo.setSubordinateAmountWin(rs.getDouble("subordinateAmountWin"));
        reportInfo.setSubordinateAmountBackWater(rs.getDouble("subordinateAmountBackWater"));
        reportInfo.setRealWin(rs.getDouble("realWin"));
        reportInfo.setRealBackWater(rs.getDouble("realBackWater"));
        reportInfo.setCommission(rs.getDouble("commission"));
        reportInfo.setMoneyRateAgent(rs.getDouble("moneyRateAgent"));
        reportInfo.setMoneyRateGenAgent(rs.getDouble("moneyRateGenAgent"));
        reportInfo.setMoneyRateStockholder(rs.getDouble("moneyRateStockholder"));
        reportInfo.setMoneyRateBranch(rs.getDouble("moneyRateBranch"));
        reportInfo.setMoneyRateChief(rs.getDouble("moneyRateChief"));
        reportInfo.setCommissionType(rs.getString("commissionType"));
        reportInfo.setUserType(rs.getString("userType"));
        
        return reportInfo;
    }
}

class ClassReportRListMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    ClassReportRList reportInfo = new ClassReportRList();
    	reportInfo.setTurnover(rs.getLong("turnover"));
        reportInfo.setAmount(rs.getDouble("amount"));
        reportInfo.setCommissionType(rs.getString("commissionType"));
        reportInfo.setMemberAmount(rs.getDouble("memberAmount"));
        reportInfo.setWinBackWater(rs.getDouble("winBackWater"));
        reportInfo.setBackWaterResult(rs.getDouble("backWaterResult"));
        return reportInfo;
    }
}

class ClassReportPetPeriodMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ClassReportEric reportInfo = new ClassReportEric();
		reportInfo.setTurnover(rs.getLong("count"));
		reportInfo.setAmount(rs.getDouble("totalMoney"));
		reportInfo.setRateMoney(rs.getDouble("rateMoney"));
		reportInfo.setMemberAmount(rs.getDouble("memberAmount"));
		reportInfo.setSubordinateAmountWin(rs.getDouble("subordinateAmountWin"));
		reportInfo.setSubordinateAmountBackWater(rs.getDouble("subordinateAmountBackWater"));
		reportInfo.setRealWin(rs.getDouble("realWin"));
		reportInfo.setRealBackWater(rs.getDouble("realBackWater"));
		reportInfo.setCommission(rs.getDouble("commission"));
		reportInfo.setMoneyRateAgent(rs.getDouble("moneyRateAgent"));
		reportInfo.setMoneyRateGenAgent(rs.getDouble("moneyRateGenAgent"));
		reportInfo.setMoneyRateStockholder(rs.getDouble("moneyRateStockholder"));
		reportInfo.setMoneyRateBranch(rs.getDouble("moneyRateBranch"));
		reportInfo.setMoneyRateChief(rs.getDouble("moneyRateChief"));
		reportInfo.setCommissionType(rs.getString("commissionType"));
		reportInfo.setUserType(rs.getString("userType"));
		
		return reportInfo;
	}
}

class ClassReportRPeriodMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ClassReportEric reportInfo = new ClassReportEric();
		reportInfo.setTurnover(rs.getLong("turnover"));
		reportInfo.setAmount(rs.getDouble("amount"));
		reportInfo.setCommissionType(rs.getString("commissionType"));
		reportInfo.setMemberAmount(rs.getDouble("memberAmount"));
		reportInfo.setWinBackWater(rs.getDouble("winBackWater"));
		reportInfo.setBackWaterResult(rs.getDouble("backWaterResult"));
		return reportInfo;
	}
}

class DetailItemMapperBetDetail implements RowMapper {
	
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
		item.setParentUserType(rs.getString("parentUserType"));
		item.setWinAmount(rs.getDouble("winAmount"));
		item.setRateWinAmount(rs.getDouble("rateWinAmount"));
		item.setRateBackWater(rs.getDouble("rateBackWater"));
		item.setCount(rs.getInt("count"));
		item.setWinState(rs.getString("winState"));
		return item;
	}
}

class DetailItemMapperC implements RowMapper {
	
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
		item.setWinAmount(rs.getDouble("winAmount"));
		item.setRateWinAmount(rs.getDouble("rateWinAmount"));
		item.setRateBackWater(rs.getDouble("rateBackWater"));
		item.setCount(rs.getInt("count"));
		item.setWinState(rs.getString("winState"));
		return item;
	}
}

class GDBetDetailItemMapperC implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BaseBet item = new BaseBet();
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


class DeliveryReportItemMapperC implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

    	ClassReportEric reportInfo = new ClassReportEric();
    	reportInfo.setCommissionType(rs.getString("commissionType"));
    	reportInfo.setTurnover(rs.getLong("count"));
    	reportInfo.setPeriodNum(rs.getString("periodsNum"));
    	reportInfo.setBettingDate(rs.getDate("bettingDate"));
        reportInfo.setAmount(rs.getDouble("totalMoney"));
        reportInfo.setMoneyRateAgent(rs.getDouble("moneyRateAgent"));
        reportInfo.setMoneyRateGenAgent(rs.getDouble("moneyRateGenAgent"));
        reportInfo.setMoneyRateStockholder(rs.getDouble("moneyRateStockholder"));
        reportInfo.setMoneyRateBranch(rs.getDouble("moneyRateBranch"));
        reportInfo.setMoneyRateChief(rs.getDouble("moneyRateChief"));
        reportInfo.setRateMoney(rs.getDouble("rateMoney"));
        
        reportInfo.setMemberAmount(rs.getDouble("memberAmount"));
        reportInfo.setSubordinateAmountWin(rs.getDouble("subordinateAmountWin"));
        reportInfo.setSubordinateAmountBackWater(rs.getDouble("subordinateAmountBackWater"));
        reportInfo.setRealWin(rs.getDouble("realWin"));
        reportInfo.setRealBackWater(rs.getDouble("realBackWater"));
        reportInfo.setCommission(rs.getDouble("commission"));
        reportInfo.setUserType(rs.getString("userType"));
        reportInfo.setUserID(rs.getLong("userId"));
        return reportInfo;
    }
}

class TotalMapperC implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	ClassReportEric reportInfo = new ClassReportEric();
    	reportInfo.setTurnover(rs.getLong("turnover"));
        reportInfo.setAmount(rs.getDouble("amount"));
        reportInfo.setPeriodNum(rs.getString("periodsNum"));
        reportInfo.setBettingDate(rs.getDate("bettingDate"));
        reportInfo.setCommissionType(rs.getString("commissionType"));
        reportInfo.setMemberAmount(rs.getDouble("memberAmount"));
        reportInfo.setWinBackWater(rs.getDouble("winBackWater"));
        reportInfo.setBackWaterResult(rs.getDouble("backWaterResult"));
        reportInfo.setUserID(rs.getLong("userId"));
        return reportInfo;
    }
}