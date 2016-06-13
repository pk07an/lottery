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
import com.npc.lottery.statreport.dao.interf.ISettledReportEricDao;
import com.npc.lottery.statreport.entity.DeliveryReportEric;
import com.npc.lottery.statreport.entity.DeliveryReportPetList;
import com.npc.lottery.statreport.entity.DeliveryReportPetPeriod;
import com.npc.lottery.statreport.entity.DeliveryReportRList;
import com.npc.lottery.statreport.entity.DeliveryReportRPeriod;
import com.npc.lottery.statreport.vo.TopRightVO;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.util.Page;

/**
 * 报表统计相关的数据库处理类
 * 
 */
public class SettledReportEricDao implements ISettledReportEricDao {

    private static Logger log = Logger.getLogger(SettledReportEricDao.class);
    private JdbcTemplate jdbcTemplate;
    //处理输2、赢1、和9、作废4、停开5问题，
    String handleWinAmout = "(case when win_state=1 then win_amount " +
    		"when win_state=2 then -money " +
    		"when win_state=9 or win_state=4 or win_state=5 then 0 else 0 end )";
    String handleMoney = "(case when win_state=9 or win_state=4 or win_state=5 then 0 else money end )";
    @Override
    public List<DeliveryReportEric> querySettledReport(Date startDate,Date endDate,String lotteryType,String playType,String periodsNum,Long userid,String userType
    		,String myColumn,String rateUser,String nextRateColumn,String commissionUser,String nextColumn,String[] scanTableList,
    		String underLineRateColumn,String nextCommissionColumn,String winState,String schema)
    {
    	log.info("querySettledReport params is :"+ startDate+","+ endDate+","+lotteryType+","+playType+","+ periodsNum+","+ userid+","+ userType
    		+","+ myColumn+","+ rateUser+","+ nextRateColumn+","+ commissionUser+","+ nextColumn+","+ scanTableList+","+
    		 underLineRateColumn+","+ nextCommissionColumn+","+ winState);
    	
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
    	
    	StringBuffer finalSql=new StringBuffer();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterListPet=new ArrayList<Object>();
    	List<Object> templateParameterListReplenish=new ArrayList<Object>();
    	
    	finalSql.append("select id,max(userType) as userType,max(parentUserType) as parentUserType," +
    			"max(periodsNum) as periodsNum,max(bettingDate) as bettingDate,max(account) account,max(chs_name) chName," +
    			"sum(count) count,sum(totalMoney) totalMoney,sum(rateMoney) rateMoney, " +
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
    			"sum(moneyRateChief) as moneyRateChief "+
    			"from (");
    	
    	String underLine = "";
		if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){//如果是代理，就不用統下級占成字段
			underLine = "* (" + underLineRateColumn + ")";
		}else{
			underLine = "* 0";
		}
		sqTemplatelBuffer.append(" select b.id,max(b.user_type) as userType,max(b.parent_staff_type_qry) as parentUserType," +
				"max(a.periods_num) as periodsNum,max(a.betting_date-numtodsinterval(3,'hour')) as bettingDate," +
				"max(b.account) account,max(b.chs_name) chs_name, count(distinct order_no) as count,sum("+handleMoney+") as totalMoney," +
    			"sum("+handleWinAmout+") as memberAmount," +    /*会员输赢*/
    			"sum("+handleWinAmout+") - sum("+handleWinAmout+" " + underLine + ") as subordinateAmountWin," + /*应收下线的输赢       公式：会员输赢*(100%-下级的占成和 )*/
    			/*应收下线的退水     公式：有效投注金额*给下级的退水率*(100%-下级占成和 )*/
				"sum("+handleMoney+" *" +
						"(case when b.parent_staff_type_qry!='6' and b.user_type='9' then commission_member else " + nextCommissionColumn + " end)/100) - " 
    					+ "sum("+handleMoney+"*" + nextCommissionColumn + "/100 " + underLine + ") as subordinateAmountBackWater," + 
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
    			
    	sqTemplatelBuffer.append(" from {TableName} a, (select t1.id,t1.account,t1.chs_name,t1.user_type,t1.parent_staff_type_qry "
    			+ "from "+schema+"tb_frame_manager_staff t1 " +
    			"union select  t2.id,t2.account,t2.chs_name,t2.user_type,t2.parent_staff_type_qry from "+schema+"tb_frame_member_staff t2 ) b ");
    			
    	sqTemplatelBuffer.append(this.getSqlByUserType(userType));//WHERE关键字写在了这里面
    	templateParameterListPet.add(userid);
    	
		//sqTemplatelBuffer.append(" and a.commission_type like ? and a.win_state not in('9','4','5') " );
    	if(!"%".equals(playType)){
    		sqTemplatelBuffer.append(" and a.commission_type like ? " );
    		templateParameterListPet.add(playType);
    	}
		
		if(periodsNum!=null && periodsNum!="")
    	{
    		sqTemplatelBuffer.append(" and a.periods_num=? " );
    		templateParameterListPet.add(periodsNum);
    	}
		if(startDate!=null && endDate!=null){
			sqTemplatelBuffer.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00') " );
		}
    	sqTemplatelBuffer.append(" group by b.id");
    	
    	//如果winState的状态为9即打和的话，就把打和的笔数的加和。
			/*sqTemplatelBuffer.append(" union all ");
			sqTemplatelBuffer.append("select b.id,max(b.user_type) as userType,max(b.parent_staff_type_qry) as parentUserType," +
					"max(b.account) account,max(b.chs_name) chs_name,count(distinct order_no) count,0 totalMoney,0 rateMoney, " +
	    			"0 as memberAmount,0 as subordinateAmountWin,0 as subordinateAmountBackWater,0 as realWin,0 as realBackWater," +
	    			"0 as commission,0 as moneyRateAgent,0 as moneyRateGenAgent,0 as moneyRateStockholder, 0 as moneyRateBranch, 0 as moneyRateChief ");
			
			sqTemplatelBuffer.append(" from {TableName} a, (select t1.id,t1.account,t1.chs_name,t1.user_type,t1.parent_staff_type_qry from tb_frame_manager_staff t1 " +
					"union select  t2.id,t2.account,t2.chs_name,t2.user_type,t2.parent_staff_type_qry from tb_frame_member_staff t2 ) b ");
	    	
	    	sqTemplatelBuffer.append(this.getSqlByUserType(userType));
	    	templateParameterListPet.add(userid);
	    	
	    	if(startDate!=null && endDate!=null)
	    		sqTemplatelBuffer.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00') " );
	    	
			sqTemplatelBuffer.append(" and a.commission_type like ? and a.win_state in('9','4','5')  " );
			templateParameterListPet.add(playType);
			
			if(periodsNum!=null && periodsNum!="")
	    	{
	    		sqTemplatelBuffer.append(" and a.periods_num=? " );
	    		templateParameterListPet.add(periodsNum);
	    	}
	    	sqTemplatelBuffer.append(" group by b.id ");*/
    	
        for (int i = 0; i < scanTableList.length; i++) {
    	  finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", schema+scanTableList[i]));
    	  if(i!=scanTableList.length-1)
    	  finalSql.append(" union all ");
    	  finalParameterList.addAll(Lists.newArrayList(templateParameterListPet));
    	  
         }
        if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){
        	finalSql.append(" union all ");
        	
        	finalSql.append(" select decode(a." + nextColumn + ",'',a.replenish_user_id,a." + nextColumn + ") as id,max(b.user_type) as userType," +
        			"max(b.parent_staff_type_qry) as parentUserType,max(a.periods_num) as periodsNum,max(a.betting_date-numtodsinterval(3,'hour')) as bettingDate," +
        			"max(b.account) account," +
        			"max(b.chs_name) chs_name, 0 as count,0 as totalMoney," +
        			"0 as memberAmount," +    /*会员输赢*/
        			"sum("+handleWinAmout+") - sum("+handleWinAmout+" * (" + underLineRateColumn + ")) as subordinateAmountWin," + //应收下线的输赢       公式：会员输赢*(100%-下级的占成和 )
        			//应收下线的退水     公式：有效投注金额*给下级的退水率*(100%-下级占成和 )
    				"sum((case when win_state=9 or win_state=4 or win_state=5 then 0 else money end )*" + nextCommissionColumn + "/100) - " 
        				+ "sum((case when win_state=9 or win_state=4 or win_state=5 then 0 else money end )*" + nextCommissionColumn + "/100 * (" + underLineRateColumn + ")) as subordinateAmountBackWater," + 
    				"sum("+handleWinAmout+"*" + rateUser + "/100) as realWin," + //实占输赢     公式：会员投注结果*查看者自已的占成
        	        "sum(money*" + commissionUser + "/100 * " + rateUser + "/100) as realBackWater," + //实占退水     公式：指会员投注有效金额*查看者自已的退水率*查看者的占成
        	        //赚取退水     公式：－1*会员投注有效注额*（自已的退水－帐号对应的退水）*（1－对应帐号本身及以下管理占成的总和）总和
        	        "sum(-money*" + commissionUser + "/100) - sum(-money*" + nextCommissionColumn + "/100) - (" + 
        	        "sum(-money*" + commissionUser + "/100 *(" + underLineRateColumn + ")) - sum(-money*" + nextCommissionColumn + "/100 * (" + underLineRateColumn + "))) as commission,");
        	
        	finalSql.append(" sum(money * a.RATE_AGENT/100) as moneyRateAgent," +
        			"sum(money * a.RATE_GEN_AGENT/100) as moneyRateGenAgent, " +
        			"sum(money * a.RATE_STOCKHOLDER/100) as moneyRateStockholder, " +
        			"sum(money * a.RATE_BRANCH/100) as moneyRateBranch, " +
        			"sum(money * a.RATE_CHIEF/100) as moneyRateChief, " +
        			"sum(money * a." + rateUser + "/100) as rateMoney ");
        	
        	finalSql.append("from "+schema+"TB_REPLENISH_HIS a,"  +
                   "(select t1.id, t1.account, t1.chs_name, t1.user_type,t1.parent_staff_type_qry from "+schema+"tb_frame_manager_staff t1) b " +
              "where a." + myColumn + " = ?" +
              " and decode(a." + nextColumn + ",'',a.replenish_user_id,a." + nextColumn + ") = b.id" +
              " and user_type != 7 ");
        	templateParameterListReplenish.add(userid);
        	
        	if(!"%".equals(playType)){
        		finalSql.append(" and a.commission_type like ? " );
        		templateParameterListReplenish.add(playType);
        	}
        	//只计算补出,补入的笔数不用统计
        	finalSql.append(" and a.win_state not in('0','9','4','5') ");
        	
        	//finalSql.append(" and a.commission_type like ? and a.win_state not in('0','9','4','5') ");
        	//templateParameterListReplenish.add(playType);
        	
        	finalSql.append(" and a.type_code like ? ");
        	templateParameterListReplenish.add(lotteryType);//这个值是对应type_code,其他值在前面已经对应了
        	
            if(periodsNum!=null && periodsNum!=""){
            	finalSql.append(" and a.periods_num=? " );
            	templateParameterListReplenish.add(periodsNum);
            }
            
            if(startDate!=null && endDate!=null){   
            	finalSql.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00') " );
            }
            
            finalSql.append(" group by decode(a." + nextColumn + ",'',a.replenish_user_id,a." + nextColumn + ") ");
            finalParameterList.addAll(Lists.newArrayList(templateParameterListReplenish));
            
        }
        
        finalSql.append(") group by id ") ;
        List<DeliveryReportEric> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new DeliveryReportItemMapperT());
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
	public List<DeliveryReportEric> queryReplenish(Date startDate,Date endDate,Long userID,String typeCode,String periodsNum,String rateUser
			,String lotteryType,String userType,String commissionUser,String winState,String tableName,String schema) {
    	
    	log.info("queryReplenish out param is :"+startDate+","+ endDate+","+userID+","+typeCode+","+ periodsNum+","+ rateUser+","+ lotteryType
        		+","+ userType+","+ rateUser+","+ userType+","+ commissionUser+","+ winState+","+ tableName+","+schema);
    	
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
    	
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	finalSql.append("select sum(turnover) as turnover,sum(amount) as amount,sum(memberAmount) as memberAmount, " +
						 "sum(winBackWater)   as winBackWater,sum(backWaterResult)  as backWaterResult," +
						 "max(periodsNum) as periodsNum,max(bettingDate-numtodsinterval(3,'hour')) as bettingDate," +
						 "userID as userID FROM (");
    	
    	finalSql.append("select count(distinct order_no) 	              as turnover," +
						     "sum("+handleMoney+")  				  as amount, " +
							 "sum("+handleWinAmout+")  			  as memberAmount, " +
							 "sum("+handleMoney+" * " + commissionUser + "/100)  as winBackWater, " +
							 "sum("+handleWinAmout+") + sum("+handleMoney+" * " + commissionUser + "/100)  as backWaterResult," +
							 "max(a.periods_num) as periodsNum,max(a.betting_date) as bettingDate, ");
    	
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
    		finalSql.append(userID + " as userID ");
    	}else{
    		finalSql.append("a.replenish_user_id as userID ");
    	}
    	finalSql.append(" from "+schema+tableName+" a  " +
				     " where ");
	     if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
				finalSql.append(" replenish_user_id in (select b.manager_staff_id from "+schema+"tb_out_replenish_staff_ext b where b.parent_staff=?) ");
				
		 }else{
			finalSql.append(" replenish_user_id=? ");
		 }		
	     templateParameterList.add(userID);
	     
	     if(!"%".equals(typeCode)){
     		finalSql.append(" and a.commission_type like ? " );
     		templateParameterList.add(typeCode);
     	}
	     finalSql.append(" and a.type_code like ? ");
	     templateParameterList.add(lotteryType);
	     //finalSql.append(" and a.commission_type like ? " +
		//	         " and a.type_code like ? and a.win_state not in('0','9','4','5') ");
    	//templateParameterList.add(typeCode);
    	
    	if(startDate!=null && endDate!=null){   
    		finalSql.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00') " );
    	}
    	
    	if(periodsNum!=null && periodsNum!=""){
	    	 finalSql.append(" and a.periods_num = ? ");
	    	 templateParameterList.add(periodsNum);
	    }
    	finalSql.append("GROUP BY replenish_user_id");
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	
    	//如果winState的状态为9即打和的话，就把打和的笔数的加和。
			/*finalSql.append(" union all ");
			finalSql.append("select count(distinct order_no) as turnover,0 as amount, 0 as memberAmount, 0 as winBackWater,0 as backWaterResult, ");

			if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
				finalSql.append(userID + " as userID ");
			}else{
				finalSql.append("a.replenish_user_id as userID ");
			}
			finalSql.append(" from "+tableName+" a where ");
			if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
				finalSql.append(" replenish_user_id in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
					
			}else{
				finalSql.append(" replenish_user_id=? ");
			}		     
			finalSql.append(" and a.win_state != '0' and a.commission_type like ? and a.type_code like ? and a.win_state in('9','4','5')");
			if(startDate!=null && endDate!=null){   
	    		finalSql.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00') " );
	    	}
	    	
	    	if(periodsNum!=null && periodsNum!=""){
		    	 finalSql.append(" and a.periods_num = ? ");
		    }
	    	finalSql.append("GROUP BY replenish_user_id");
	    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));*/
    	
    	finalSql.append(") GROUP BY userID");
    	
		@SuppressWarnings("unchecked")
		List<DeliveryReportEric> retList = jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(), new TotalMapperT());
		return retList;
	}
    
    @Override
	public Page queryBetDetail(Page page, Long userId,Date startDate,Date endDate,String periodsNum,String playType,String[] scanTableList,
			String rateColumn,String commissionColumn,String myCommissionColumn) {
		
		List<DetailVO> retList = new ArrayList<DetailVO>();
		String handle = "(case when win_state=1 then win_amount when win_state=2 then -money when win_state=9 or win_state=4 or win_state=5 then 0 else 0 end )";
		
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
									"max(agentRate) AS agentRate,"+topAttribute+" AS attribute,max(userType) AS userType,"+
									"sum(winAmount) AS winAmount, sum(rateWinAmount) AS rateWinAmount,sum(rateBackWater) AS rateBackWater,count(orderNo) as count, " +
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
							"100-a.COMMISSION_AGENT 				 as genAgentCommission," +
							"100-a.COMMISSION_MEMBER   				 as agentCommission," +
							"a.RATE_CHIEF   					 as chiefRate," +
							"a.RATE_BRANCH   					 as branchRate," +
							"a.RATE_STOCKHOLDER   				 as stockRate," +
							"a.RATE_GEN_AGENT   				 as genAgentRate," +
							"a.RATE_AGENT   			 		 as agentRate," +
							attribute + "    		 			 as attribute," +
							"b.user_type		 				 as userType," +
							handle+"		 				 as winAmount," +
							handle+" * " + rateColumn + "/100		 	 as rateWinAmount," +
							"(case when win_state=9 or win_state=4 or win_state=5 then 0 else " +
							"(a.money*(case when b.parent_staff_type_qry!='6' and b.user_type='9' " +
							"then " + myCommissionColumn + " else " + commissionColumn + " end)/100*" + rateColumn + "/100) end ) as rateBackWater, " +
							"a.win_state as winState from "
				+ tableName + " a ,tb_frame_member_staff b"				
				+ " where a.betting_user_id= ? "
				+ " and a.betting_user_id= b.id "
				+ " and a.commission_type like ? ");
			templateParameterList.add(userId);  
		    templateParameterList.add(playType);
		    
		    if(startDate!=null && endDate!=null){ 
		    	sqTemplatelBuffer.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00') " );
		    }

	    	if(periodsNum!=null && periodsNum!=""){
	    		sqTemplatelBuffer.append(" and a.periods_num=? " );
	    		templateParameterList.add(periodsNum);
	    	}
	    	sqTemplatelBuffer.append(") group by orderNo,typeCode,periodsNum " );
	    	
			finalSql.append(sqTemplatelBuffer);
			finalParameterList.addAll(Lists.newArrayList(templateParameterList));
			
			List betlist = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapperT());
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
    public List<TopRightVO> queryBetDetailGroupByDate(Long userId,Date startDate,Date endDate,String periodsNum,String playType,String[] scanTableList) {
    	
    	List<TopRightVO> retList = new ArrayList<TopRightVO>();
    	
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	List<Object> templateParameterList=new ArrayList<Object>();
    	//这里的逻辑是先统计3点到23点的数据，然后统计24点到凌晨2点的数据，其日期提前一天，最后再合并统计
    	finalSql.append( " select max(t.bettingDate) as bettingDate,sum(t.count) as count from (");//统计头部
    	
    		//先统计3点到23点的数据
    		sqTemplatelBuffer.append( " select a.betting_date as bettingDate, to_char(max(a.betting_date),'hh24') as hh,count(distinct a.order_no) as count" 
    				+ " from {TableName} a ,tb_frame_member_staff b where a.betting_user_id= ? and a.betting_user_id= b.id " +
    				"and a.commission_type like ? and a.win_state not in('0')");
    		templateParameterList.add(userId);  
    		templateParameterList.add(playType);
    		
    		if(startDate!=null){ 
    			sqTemplatelBuffer.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00') " +
    					"and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00') " );
    		}
    		if(periodsNum!=null && periodsNum!=""){
    			sqTemplatelBuffer.append(" and a.periods_num=? " );
    			templateParameterList.add(periodsNum);
    		}
    		sqTemplatelBuffer.append(" group by a.betting_date having to_char(max(a.betting_date),'hh24')>=03 and to_char(max(a.betting_date),'hh24')<=23 " );
    		
    		//然后统计24点到凌晨2点的数据,其日期提前一天,如果是重庆和北京的不用执行的。
	    		sqTemplatelBuffer.append(" union all select a.betting_date-1 as bettingDate, to_char(max(a.betting_date),'hh24') as hh,count(distinct a.order_no) as count " 
	    				+ " from {TableName} a ,tb_frame_member_staff b where a.betting_user_id= ? and a.betting_user_id= b.id " +
	    				"and a.commission_type like ? and a.win_state not in('0')");
	    		templateParameterList.add(userId);  
	    		templateParameterList.add(playType);
	    		
	    		if(startDate!=null){ 
	    			sqTemplatelBuffer.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00') " +
	    					"and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00') " );
	    		}
	    		if(periodsNum!=null && periodsNum!=""){
	    			sqTemplatelBuffer.append(" and a.periods_num=? " );
	    			templateParameterList.add(periodsNum);
	    		}
	    		sqTemplatelBuffer.append(" group by a.betting_date having to_char(max(a.betting_date),'hh24')>=00 and to_char(max(a.betting_date),'hh24')<=02" );
    		
    	for (int i = 0; i < scanTableList.length; i++) {
      	  finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", scanTableList[i]));
      	  if(i!=scanTableList.length-1)
      	  finalSql.append(" union all ");
      	  finalParameterList.addAll(Lists.newArrayList(templateParameterList));
      	  
        }
    	
    	//统计尾部
    	finalSql.append(" ) t group by to_char(t.bettingDate,'yyyy-MM-dd') order by to_char(t.bettingDate,'yyyy-MM-dd') desc " );
    	
    	retList = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapperTj());
    	return retList;
    	
    }
    
    @Override
    public List<TopRightVO> queryBetDetailGroupByDateForReplnish(Long userId,Date startDate,Date endDate,String periodsNum,String playType,String userType) {
    	
    	List<TopRightVO> retList = new ArrayList<TopRightVO>();
    	
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	List<Object> templateParameterList=new ArrayList<Object>();
    	//这里的逻辑是先统计3点到23点的数据，然后统计24点到凌晨2点的数据，其日期提前一天，最后再合并统计
    	finalSql.append( " select max(t.bettingDate) as bettingDate,sum(t.count) as count from (");//统计头部
    	
    	//先统计3点到23点的数据
    	sqTemplatelBuffer.append( " select a.betting_date as bettingDate, to_char(max(a.betting_date),'hh24') as hh,count(distinct a.order_no) as count" 
    			+ " from tb_replenish_his a ,tb_frame_manager_staff b where a.replenish_user_id= b.id " +
    			"and a.commission_type like ? and a.win_state not in('0')");
    	
    	templateParameterList.add(playType);
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
    		sqTemplatelBuffer.append(" and replenish_user_id in (select t.manager_staff_id from tb_out_replenish_staff_ext t where t.parent_staff=?) ");
				
		}else{
			sqTemplatelBuffer.append(" and replenish_user_id=? ");
		}
    	templateParameterList.add(userId);  
    	
    	if(startDate!=null){ 
    		sqTemplatelBuffer.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00') " +
    				"and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00') " );
    	}
    	if(periodsNum!=null && periodsNum!=""){
    		sqTemplatelBuffer.append(" and a.periods_num=? " );
    		templateParameterList.add(periodsNum);
    	}
    	sqTemplatelBuffer.append(" group by a.betting_date having to_char(max(a.betting_date),'hh24')>=03 and to_char(max(a.betting_date),'hh24')<=23 " );
    	
    	//然后统计24点到凌晨2点的数据,其日期提前一天,如果是重庆和北京的不用执行的。
    	sqTemplatelBuffer.append(" union all select a.betting_date-1 as bettingDate, to_char(max(a.betting_date),'hh24') as hh,count(distinct a.order_no) as count " 
    			+ " from tb_replenish_his a ,tb_frame_manager_staff b where a.replenish_user_id= b.id " +
    			"and a.commission_type like ? and a.win_state not in('0')");
    	
    	templateParameterList.add(playType);
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
    		sqTemplatelBuffer.append(" and replenish_user_id in (select t.manager_staff_id from tb_out_replenish_staff_ext t where t.parent_staff=?) ");
				
		}else{
			sqTemplatelBuffer.append(" and replenish_user_id=? ");
		}
    	templateParameterList.add(userId);  
    	
    	if(startDate!=null){ 
    		sqTemplatelBuffer.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00') " +
    				"and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00') " );
    	}
    	if(periodsNum!=null && periodsNum!=""){
    		sqTemplatelBuffer.append(" and a.periods_num=? " );
    		templateParameterList.add(periodsNum);
    	}
    	sqTemplatelBuffer.append(" group by a.betting_date having to_char(max(a.betting_date),'hh24')>=00 and to_char(max(a.betting_date),'hh24')<=02" );
    	
    	finalSql.append(sqTemplatelBuffer.toString());
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	
    	//统计尾部
    	finalSql.append(" ) t group by to_char(t.bettingDate,'yyyy-MM-dd') order by to_char(t.bettingDate,'yyyy-MM-dd') desc " );
    	
    	retList = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapperTj());
    	return retList;
    	
    }
    
    @Override
	public Page queryReplenishOutDetail(Page page, Long userId,String commissionTypeCode,String periodsNum,String userType,String currentUserType,Date startDate
			,Date endDate,String lotteryType,String rateColumn,String commissionColumn) {
		
    	String handle = "(case when max(win_state)=1 then sum(win_amount) when max(win_state)=2 then sum(-money) " +
    			"when max(win_state)=9 or max(win_state)=4 or max(win_state)=5 then 0 else 0 end )";
		List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	
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
				"sum(a.money)   			 as money,");
		
		//如果是总监查自己的补出时，是取总监字段的拥金值，因为这里是存出货会员的拥金值
		if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
			sqTemplatelBuffer.append("sum(100-a.COMMISSION_CHIEF)  as chiefCommission,");
		}else{
			sqTemplatelBuffer.append("sum(100-a.COMMISSION_BRANCH)  as chiefCommission,");
		}
		
		sqTemplatelBuffer.append("100-max(a.COMMISSION_STOCKHOLDER)   				 as branchCommission," +
				"100-max(a.COMMISSION_GEN_AGENT)   		 as stockCommission," +
				"100-max(a.COMMISSION_AGENT)   		 	 as genAgentCommission," +
				"100-max(a.COMMISSION_MEMBER)   		 as agentCommission," +
				"max(a.RATE_CHIEF)   					 as chiefRate," +
				"max(a.RATE_BRANCH)  					 as branchRate," +
				"max(a.RATE_STOCKHOLDER)   				 as stockRate," +
				"max(a.RATE_GEN_AGENT)   				 as genAgentRate," +
				"max(a.RATE_AGENT)   			 		 as agentRate," +
				"max(a.attribute)		 				 as attribute ," +
				"max("+userType+")		 					 as userType," +
				handle+"		 				 as winAmount,");
		//如果是本级补出的才要前面加个减号进行运算,查补出明细时，自己查自己的补出时不用乘于占成，
		if(userType.equals(currentUserType)){
			sqTemplatelBuffer.append("(case when max(win_state)=1 then sum(-win_amount) when max(win_state)=2 then sum(money) " +
					"when max(win_state)=9 then 0 else 0 end ) as rateWinAmount," +
					"(case when max(win_state)=9 then 0 else (sum(-a.money)*max(" + commissionColumn + ")/100) end )		as rateBackWater,");
		}else{
			sqTemplatelBuffer.append(handle+"* max(" + rateColumn + ")/100 as rateWinAmount," +
					"(case when max(win_state)=9 then 0 else (sum(a.money)*max(" + commissionColumn + ")/100* max(" + rateColumn + ")/100) end ) as rateBackWater,");
		}
		
		sqTemplatelBuffer.append("1 as count,max(a.win_state) as winState "
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
			sqTemplatelBuffer.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00') " );
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
		
		List<DetailVO> betlist = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapperT());
		
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
	public List<ManagerStaff> queryAllManagerUser(String[] scanTableList,Date startDate, Date endDate,String periodsNum,String schema) {
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
    	
    	String[] userTypeList = new String[] {"chiefstaff:2","branchstaff:3","stockholderstaff:4","genagenstaff:5","agentstaff:6"};
    	StringBuffer finalSql=new StringBuffer();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameteList=new ArrayList<Object>();
    	String sqlTime = " where t.betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00')"+
			    	" and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00') ";
    	String sqlPeriodNum = " where t.periods_num = ? ";
    	
    	finalSql.append("select distinct(id) as id,max(userType) as userType from (");
    	
    	for (int i = 0; i < userTypeList.length; i++) {
    		String[] str = userTypeList[i].split(":");
	    	sqTemplatelBuffer.append("select distinct(t." + str[0] + ") as id," + str[1] + " as userType from {TableName} t ");
	    	if(periodsNum!=null){
	    		sqTemplatelBuffer.append(sqlPeriodNum);
	    		templateParameteList.add(periodsNum);
	    	}else{
	    		sqTemplatelBuffer.append(sqlTime);
	    	}
	    	
	    	if(i!=userTypeList.length-1){
	    		sqTemplatelBuffer.append(" union all ");
	    	}
    	}
			    	/*" select distinct(t.branchstaff) as id,3 as userType from {TableName} t where t.betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00')"+
			    	" and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00')"+
			    	" union all "+
			    	" select distinct(t.stockholderstaff) as id,4 as userType from {TableName} t where t.betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00')"+
			    	" and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00')"+
			    	" union all "+
			    	" select distinct(t.genagenstaff) as id,5 as userType from {TableName} t where t.betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00')"+
			    	" and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00')"+
			    	" union all "+
			    	" select distinct(t.agentstaff) as id,6 as userType from {TableName} t where t.betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:30:00')"+
			    	" and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:30:00')");*/
    	for (int i = 0; i < scanTableList.length; i++) {
      	  finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", schema+scanTableList[i]));
      	  if(i!=scanTableList.length-1){
	      	  finalSql.append(" union all ");
	          templateParameteList.addAll(Lists.newArrayList(templateParameteList));
      	  }
      	  
        }
    	
    	//特殊处理总监补货
    	finalSql.append(" union all "+
        " select b.Parent_Staff AS id, 2 as userType from "+ schema+"tb_out_replenish_staff_ext b where b.manager_staff_id IN ( "+ 
        " select distinct (t.replenish_user_id) as id "+
        "  from "+ schema+"TB_REPLENISH_HIS t "+
        " where t.periods_num = ? and t.chiefstaff is null) ");
    	templateParameteList.add(periodsNum);
    	
    	//finalSql.append(") group by id");
    	finalSql.append(") where id is not null group by id");//modify by eric 2015.9.17
    	finalParameterList.addAll(Lists.newArrayList(templateParameteList));
    	List<ManagerStaff> retList = jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new ManagerUserMapper());
		return retList;
    }
    
    @Override
    public List<ManagerStaff> queryAllManagerUserInReportPeriod(String[] scanTableList,Date startDate, Date endDate,String periodsNum,String schema) {
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
    	
    	StringBuffer finalSql=new StringBuffer();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameteList=new ArrayList<Object>();
    	String sqlTime = " where t.betting_date between to_date('"+startDate+"','yyyy-MM-dd') and to_date('"+endDate+"','yyyy-MM-dd') ";
    	String sqlPeriodNum = " where t.periods_num = ? ";
    	finalSql.append("select distinct(id) as id,max(userType) as userType from (");
    	
    	sqTemplatelBuffer.append("select distinct(USER_ID) as id,max(USER_TYPE) as userType from {TableName} t ");
    	if(periodsNum!=null){
    		sqTemplatelBuffer.append(sqlPeriodNum);
    		templateParameteList.add(periodsNum);
    	}else{
    		sqTemplatelBuffer.append(sqlTime);
    	}
    	sqTemplatelBuffer.append(" group by t.user_id ");
    	
    	for (int i = 0; i < scanTableList.length; i++) {
    		finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", schema+scanTableList[i]));
    		
    		if(i!=scanTableList.length-1){
    			finalSql.append(" union all ");
    			templateParameteList.addAll(Lists.newArrayList(templateParameteList));
    		}
    		
    	}
    	finalSql.append(") group by id");
    	finalParameterList.addAll(Lists.newArrayList(templateParameteList));
    	List<ManagerStaff> retList = jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new ManagerUserMapper());
    	return retList;
    }
    
    @Override
    public List<DeliveryReportPetList> queryDeliveryReportPetList(Long userID,String userType,java.sql.Date startDate,java.sql.Date endDate){
    	Object[] parameters= {userID,userType};		
    	String sql = "select BETTING_USER_ID as bettingUserID,max(BETTING_USER_TYPE) as bettingUserType,max(PARENT_USER_TYPE) as parentUserType" +
    			     ",max(ACCOUNT) as subordinate,max(CHNAME) as userName,sum(COUNT) as turnover" +
    			     ",sum(TOTAL_MONEY) as amount,sum(MONEY_RATE_AGENT) as moneyRateAgent,sum(MONEY_RATE_GENAGENT) as moneyRateGenAgent" +
    			     ",sum(MONEY_RATE_STOCKHOLDER) as moneyRateStockholder,sum(MONEY_RATE_BRANCH) as moneyRateBranch,sum(MONEY_RATE_CHIEF) as moneyRateChief" +
    			     ",sum(RATE_MONEY) as rateMoney,sum(MEMBER_AMOUNT) as memberAmount,sum(SUBORDINATE_AMOUNT_WIN) as subordinateAmountWin" +
    			     ",sum(SUBORDINATE_AMOUNT_BACKWATER) as subordinateAmountBackWater,sum(REALWIN) as realWin,sum(REAL_BACKWATER) as realBackWater" +
    			     ",sum(COMMISSION) as commission" +
    			     " from TB_SETTLED_REPORT_PET_LIST t where t.USER_ID =? and t.USER_TYPE=? " +
    			     " and t.BETTING_DATE between to_date('"+startDate+"','yyyy-MM-dd') and to_date('"+endDate+"','yyyy-MM-dd')" +
    			     " group by t.BETTING_USER_ID";
    	List<DeliveryReportPetList> retList = jdbcTemplate.query(sql,parameters, new DeliveryReportPetListMapper());
		return retList;
    }
    @Override
    public List<DeliveryReportRList> queryDeliveryReportRList(Long userID,String userType,java.sql.Date startDate,java.sql.Date endDate){
    	Object[] parameters= {userID,userType};		
    	String sql = "select USER_ID as userID,max(USER_TYPE) as userType,sum(COUNT) as turnover" +
    			",sum(AMOUNT) as amount,sum(MEMBER_AMOUNT) as memberAmount,sum(WIN_BACK_WATER) as winBackWater" +
    			",sum(BACK_WATER_RESULT) as backWaterResult" +
    			" from TB_SETTLED_REPORT_R_LIST t where t.USER_ID =? and t.USER_TYPE=? " +
    			" and t.BETTING_DATE between to_date('"+startDate+"','yyyy-MM-dd') and to_date('"+endDate+"','yyyy-MM-dd')" +
    			" group by t.USER_ID";
    	List<DeliveryReportRList> retList = jdbcTemplate.query(sql,parameters, new DeliveryReportRListMapper());
    	return retList;
    }
    
    @Override
    public List<DeliveryReportEric> queryDeliveryReportPetPeriod(Long userID,String userType,java.sql.Date startDate,java.sql.Date endDate,
    		String periodsNum, String lotteryType,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameter=new ArrayList<Object>();
    	finalSql.append("select BETTING_USER_ID as bettingUserID,max(BETTING_USER_TYPE) as bettingUserType,max(PARENT_USER_TYPE) as parentUserType" +
    			",max(ACCOUNT) as subordinate,max(CHNAME) as userName,sum(COUNT) as turnover" +
    			",sum(TOTAL_MONEY) as amount,sum(MONEY_RATE_AGENT) as moneyRateAgent,sum(MONEY_RATE_GENAGENT) as moneyRateGenAgent" +
    			",sum(MONEY_RATE_STOCKHOLDER) as moneyRateStockholder,sum(MONEY_RATE_BRANCH) as moneyRateBranch,sum(MONEY_RATE_CHIEF) as moneyRateChief" +
    			",sum(RATE_MONEY) as rateMoney,sum(MEMBER_AMOUNT) as memberAmount,sum(SUBORDINATE_AMOUNT_WIN) as subordinateAmountWin" +
    			",sum(SUBORDINATE_AMOUNT_BACKWATER) as subordinateAmountBackWater,sum(REALWIN) as realWin,sum(REAL_BACKWATER) as realBackWater" +
    			",sum(COMMISSION) as commission" +
    			" from "+schema+"TB_S_REPORT_PET_Period t where t.USER_ID =? and t.USER_TYPE=? ");
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
    	
    	finalSql.append(" group by t.BETTING_USER_ID");
    	finalParameterList.addAll(Lists.newArrayList(templateParameter));
    	List<DeliveryReportEric> retList = jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(), new DeliveryReportPetPeriodMapper());
    	return retList;
    }
    @Override
    public List<DeliveryReportEric> queryDeliveryReportRPeriod(Long userID,String userType,java.sql.Date startDate,java.sql.Date endDate,
    		String periodsNum, String lotteryType,String schema){
    	if (StringUtils.isNotEmpty(schema)) {
			schema = schema + ".";
		}
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameter=new ArrayList<Object>();
    	finalSql.append("select USER_ID as userID,max(USER_TYPE) as userType,sum(COUNT) as turnover" +
    			",sum(AMOUNT) as amount,sum(MEMBER_AMOUNT) as memberAmount,sum(WIN_BACK_WATER) as winBackWater" +
    			",sum(BACK_WATER_RESULT) as backWaterResult" +
    			" from "+schema+"TB_S_REPORT_R_Period t where t.USER_ID =? and t.USER_TYPE=? ");
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
    	finalSql.append(" group by t.USER_ID");
    	finalParameterList.addAll(Lists.newArrayList(templateParameter));
    	List<DeliveryReportEric> retList = jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(), new DeliveryReportRPeriodMapper());
    	return retList;
    }
    
    @Override
    public List<DeliveryReportEric> querySettledReportMerge(Date startDate,Date endDate,Date nowDate,Long userid,String userType){
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	
    	finalSql.append("select id,max(userType) as userType,max(parentUserType) as parentUserType,max(account) account,max(chName) chName," +
    			"sum(count) count,sum(totalMoney) totalMoney,sum(rateMoney) rateMoney, " +
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
    			"sum(moneyRateChief) as moneyRateChief "+
    			"from (");
    	//查今天的报表
    	finalSql.append("select BETTING_USER_ID as id,max(BETTING_USER_TYPE) as userType,max(PARENT_USER_TYPE) as parentUserType" +
    			",max(ACCOUNT) as account,max(CHNAME) as chName,sum(COUNT) as count" +
    			",sum(TOTAL_MONEY) as totalMoney,sum(MONEY_RATE_AGENT) as moneyRateAgent,sum(MONEY_RATE_GENAGENT) as moneyRateGenAgent" +
    			",sum(MONEY_RATE_STOCKHOLDER) as moneyRateStockholder,sum(MONEY_RATE_BRANCH) as moneyRateBranch,sum(MONEY_RATE_CHIEF) as moneyRateChief" +
    			",sum(RATE_MONEY) as rateMoney,sum(MEMBER_AMOUNT) as memberAmount,sum(SUBORDINATE_AMOUNT_WIN) as subordinateAmountWin" +
    			",sum(SUBORDINATE_AMOUNT_BACKWATER) as subordinateAmountBackWater,sum(REALWIN) as realWin,sum(REAL_BACKWATER) as realBackWater" +
    			",sum(COMMISSION) as commission" +
    			" from TB_S_REPORT_PET_Period t " +
    			"where t.USER_ID =? and t.USER_TYPE=? and t.BETTING_DATE = to_date('"+nowDate+"','yyyy-MM-dd') group by t.BETTING_USER_ID");
    	templateParameterList.add(userid);
    	templateParameterList.add(userType);
    	
    	finalSql.append(" union all ");
    	//查历史的报表
    	finalSql.append("select BETTING_USER_ID as id,max(BETTING_USER_TYPE) as userType,max(PARENT_USER_TYPE) as parentUserType " +
			     ",max(ACCOUNT) as account,max(CHNAME) as chName,sum(COUNT) as count,sum(TOTAL_MONEY) as totalMoney " +
			     ",sum(MONEY_RATE_AGENT) as moneyRateAgent,sum(MONEY_RATE_GENAGENT) as moneyRateGenAgent" +
			     ",sum(MONEY_RATE_STOCKHOLDER) as moneyRateStockholder,sum(MONEY_RATE_BRANCH) as moneyRateBranch,sum(MONEY_RATE_CHIEF) as moneyRateChief " +
			     ",sum(RATE_MONEY) as rateMoney,sum(MEMBER_AMOUNT) as memberAmount,sum(SUBORDINATE_AMOUNT_WIN) as subordinateAmountWin " +
			     ",sum(SUBORDINATE_AMOUNT_BACKWATER) as subordinateAmountBackWater,sum(REALWIN) as realWin,sum(REAL_BACKWATER) as realBackWater " +
			     ",sum(COMMISSION) as commission" +
			     " from TB_SETTLED_REPORT_PET_LIST t where t.USER_ID =? and t.USER_TYPE=? " +
			     " and t.BETTING_DATE ！= to_date('"+nowDate+"','yyyy-MM-dd') " +
			     " and t.BETTING_DATE between to_date('"+startDate+"','yyyy-MM-dd') and to_date('"+endDate+"','yyyy-MM-dd') " +
			     " group by t.BETTING_USER_ID " );
    	templateParameterList.add(userid);
    	templateParameterList.add(userType);
    	
    	finalSql.append(") group by id ") ;
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	List<DeliveryReportEric> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new DeliveryMergeItemMapperT());
    	return reportList;
    	
    }
    
    //查看补出的货的汇总，把当天的与历史合并
    @Override
	public List<DeliveryReportEric> queryReplenishMerge(Date startDate,Date endDate,Date nowDate,Long userID,String userType) {
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	
    	finalSql.append(" select sum(turnover) as turnover,sum(amount) as amount,sum(memberAmount)   as memberAmount, " +
    			" sum(winBackWater)   as winBackWater,sum(backWaterResult)  as backWaterResult,userID as userID FROM (");
    	
    	finalSql.append(" select sum(COUNT) as turnover,sum(AMOUNT) as amount" +
    			" ,sum(MEMBER_AMOUNT) as memberAmount,sum(WIN_BACK_WATER) as winBackWater" +
    			" ,sum(BACK_WATER_RESULT) as backWaterResult,USER_ID as userID " +
    			" from TB_S_REPORT_R_Period t " +
    			" where t.USER_ID =? and t.USER_TYPE=?  and t.BETTING_DATE = to_date('"+nowDate+"','yyyy-MM-dd') group by t.USER_ID ");
    	templateParameterList.add(userID);
    	templateParameterList.add(userType);
    	
    	finalSql.append(" union all ");
    	finalSql.append(" select sum(COUNT) as turnover,sum(AMOUNT) as amount,sum(MEMBER_AMOUNT) as memberAmount, " +
    			" sum(WIN_BACK_WATER) as winBackWater,sum(BACK_WATER_RESULT) as backWaterResult,USER_ID as userID " +
    			" from TB_SETTLED_REPORT_R_LIST t where t.USER_ID =? and t.USER_TYPE=? " +
    			" and t.BETTING_DATE ！= to_date('"+nowDate+"','yyyy-MM-dd') " +
    			" and t.BETTING_DATE between to_date('"+startDate+"','yyyy-MM-dd') and to_date('"+endDate+"','yyyy-MM-dd')" +
    			" group by t.USER_ID");
    	templateParameterList.add(userID);
    	templateParameterList.add(userType);
    	
    	finalSql.append(") group by userID ") ;
    	
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
		@SuppressWarnings("unchecked")
		List<DeliveryReportEric> retList = jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(), new TotalMergeMapperT());
		return retList;
	}
    
    public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


}

class DeliveryReportRListMapper implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	DeliveryReportRList reportInfo = new DeliveryReportRList();
    	reportInfo.setTurnover(rs.getLong("turnover"));
        reportInfo.setAmount(rs.getDouble("amount"));
        reportInfo.setUserID(rs.getLong("userID"));
        reportInfo.setMemberAmount(rs.getDouble("memberAmount"));
        reportInfo.setWinBackWater(rs.getDouble("winBackWater"));
        reportInfo.setBackWaterResult(rs.getDouble("backWaterResult"));
        return reportInfo;
    }
}

class DeliveryReportPetListMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

    	DeliveryReportPetList reportInfo = new DeliveryReportPetList();
    	reportInfo.setBettingUserID(rs.getLong("bettingUserID"));
    	reportInfo.setBettingUserType(rs.getString("bettingUserType"));
    	reportInfo.setParentUserType(rs.getString("parentUserType"));
    	reportInfo.setSubordinate(rs.getString("subordinate"));  	
    	reportInfo.setUserName(rs.getString("userName"));
    	reportInfo.setTurnover(rs.getLong("turnover"));
        reportInfo.setAmount(rs.getDouble("amount"));
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
        return reportInfo;
    }
}

class DeliveryReportRPeriodMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		DeliveryReportEric reportInfo = new DeliveryReportEric();
		reportInfo.setTurnover(rs.getLong("turnover"));
		reportInfo.setAmount(rs.getDouble("amount"));
		reportInfo.setUserID(rs.getLong("userID"));
		reportInfo.setMemberAmount(rs.getDouble("memberAmount"));
		reportInfo.setWinBackWater(rs.getDouble("winBackWater"));
		reportInfo.setBackWaterResult(rs.getDouble("backWaterResult"));
		return reportInfo;
	}
}

class DeliveryReportPetPeriodMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		DeliveryReportEric reportInfo = new DeliveryReportEric();
		reportInfo.setUserID(rs.getLong("bettingUserID"));
		reportInfo.setUserType(rs.getString("bettingUserType"));
		reportInfo.setBettingUserID(rs.getLong("bettingUserID"));
		reportInfo.setBettingUserType(rs.getString("bettingUserType"));
		reportInfo.setParentUserType(rs.getString("parentUserType"));
		reportInfo.setSubordinate(rs.getString("subordinate"));  	
		reportInfo.setUserName(rs.getString("userName"));
		reportInfo.setTurnover(rs.getLong("turnover"));
		reportInfo.setAmount(rs.getDouble("amount"));
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
		return reportInfo;
	}
}

class ManagerUserMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ManagerStaff item = new ManagerStaff();
		item.setID(rs.getLong("id"));
		item.setUserType(rs.getString("userType"));
		return item;
	}
}
class MemberUserMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberStaff item = new MemberStaff();
		item.setID(rs.getLong("id"));
		item.setUserType(rs.getString("userType"));
		return item;
	}
}
class DetailItemMapperTj implements RowMapper {
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		TopRightVO item = new TopRightVO();
		item.setBettingDate(rs.getTimestamp("bettingDate"));
		item.setCount(rs.getInt("count"));
		return item;
	}
}
class DetailItemMapperT implements RowMapper {
	
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

class GDBetDetailItemMapperT implements RowMapper {

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


class DeliveryReportItemMapperT implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

    	DeliveryReportEric reportInfo = new DeliveryReportEric();
    	reportInfo.setUserID(rs.getLong("id"));
    	reportInfo.setUserType(rs.getString("userType"));
    	reportInfo.setParentUserType(rs.getString("parentUserType"));
    	reportInfo.setPeriodNum(rs.getString("periodsNum"));
    	reportInfo.setBettingDate(rs.getDate("bettingDate"));
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
        
        reportInfo.setMemberAmount(rs.getDouble("memberAmount"));
        reportInfo.setSubordinateAmountWin(rs.getDouble("subordinateAmountWin"));
        reportInfo.setSubordinateAmountBackWater(rs.getDouble("subordinateAmountBackWater"));
        reportInfo.setRealWin(rs.getDouble("realWin"));
        reportInfo.setRealBackWater(rs.getDouble("realBackWater"));
        reportInfo.setCommission(rs.getDouble("commission"));
        return reportInfo;
    }
}

class TotalMapperT implements RowMapper {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	DeliveryReportEric reportInfo = new DeliveryReportEric();
    	reportInfo.setTurnover(rs.getLong("turnover"));
        reportInfo.setAmount(rs.getDouble("amount"));
        reportInfo.setUserID(rs.getLong("userID"));
        reportInfo.setMemberAmount(rs.getDouble("memberAmount"));
        reportInfo.setWinBackWater(rs.getDouble("winBackWater"));
        reportInfo.setBackWaterResult(rs.getDouble("backWaterResult"));
        reportInfo.setPeriodNum(rs.getString("periodsNum"));
        reportInfo.setBettingDate(rs.getDate("bettingDate"));
        return reportInfo;
    }
}

class DeliveryMergeItemMapperT implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		DeliveryReportEric reportInfo = new DeliveryReportEric();
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
		
		reportInfo.setMemberAmount(rs.getDouble("memberAmount"));
		reportInfo.setSubordinateAmountWin(rs.getDouble("subordinateAmountWin"));
		reportInfo.setSubordinateAmountBackWater(rs.getDouble("subordinateAmountBackWater"));
		reportInfo.setRealWin(rs.getDouble("realWin"));
		reportInfo.setRealBackWater(rs.getDouble("realBackWater"));
		reportInfo.setCommission(rs.getDouble("commission"));
		return reportInfo;
	}
}

class TotalMergeMapperT implements RowMapper {
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		DeliveryReportEric reportInfo = new DeliveryReportEric();
		reportInfo.setTurnover(rs.getLong("turnover"));
		reportInfo.setAmount(rs.getDouble("amount"));
		reportInfo.setUserID(rs.getLong("userID"));
		reportInfo.setMemberAmount(rs.getDouble("memberAmount"));
		reportInfo.setWinBackWater(rs.getDouble("winBackWater"));
		reportInfo.setBackWaterResult(rs.getDouble("backWaterResult"));
		return reportInfo;
	}
}