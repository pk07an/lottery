package com.npc.lottery.replenish.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.google.common.collect.Lists;
import com.npc.lottery.common.Constant;
import com.npc.lottery.replenish.dao.interf.IZhangdanDao;
import com.npc.lottery.replenish.entity.Zhangdan;
import com.npc.lottery.replenish.vo.ZhanDangLMDetailVO;
import com.npc.lottery.replenish.vo.ZhanDangVO;
import com.npc.lottery.sysmge.entity.ManagerStaff;

/**
 * 报表统计相关的数据库处理类
 * 
 */
public class ZhangdanDao implements IZhangdanDao {

    private static Logger log = Logger.getLogger(ZhangdanDao.class);
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public List<Zhangdan> queryUnSettledReport(String lotteryType,String playType,String periodNum,Long userid,String userType
    		,String myColumn,String rateUser,String commissionUser,String nextColumn,String tableName)
    {
    	StringBuffer finalSql=new StringBuffer();
    	finalSql.append("select typeCode,sum(turnover) turnover,sum(totalMoney) totalMoney from (");
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	sqTemplatelBuffer.append(" select a.type_code as typeCode, count(distinct order_no) as turnover,sum(money*a." + rateUser + "/100) as totalMoney");
    	
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
    	
    	sqTemplatelBuffer.append(" and a.periods_num=? " );
		templateParameterList.add(periodNum);
		
    	if(!Constant.LOTTERY_TYPE_HKLHC.equals(lotteryType)){
        	if(playType.indexOf("GD")>-1){
	    		if(playType.equals("GD_1-8")){
					sqTemplatelBuffer.append(" and a.type_code not in ('GDKLSF_DOUBLESIDE_LONG','GDKLSF_DOUBLESIDE_HU') " +
											 " and a.type_code not like 'GDKLSF_DOUBLESIDE_ZH%' " );
				}else if(playType.equals("GD_ZH")){
					sqTemplatelBuffer.append(" or a.type_code like 'GDKLSF_DOUBLESIDE_ZH%' " + 
							" and a.type_code in ('GDKLSF_DOUBLESIDE_LONG','GDKLSF_DOUBLESIDE_HU')");
					
				}
        	}else if(playType.indexOf("CQ")>-1){
    			if(playType.equals("CQ_1-5")){
    				sqTemplatelBuffer.append(" and a.type_code not like 'CQSSC_DOUBLESIDE_ZH%'" +
				    						" and a.type_code not like 'CQSSC_BZ%'" +
				    						" and a.type_code not like 'CQSSC_SZ%'" +
				    						" and a.type_code not like 'CQSSC_DZ%'" +
				    						" and a.type_code not like 'CQSSC_BS%'" +
				    						" and a.type_code not like 'CQSSC_ZL%'" +
											 " and a.type_code not in ('CQSSC_DOUBLESIDE_LONG','CQSSC_DOUBLESIDE_HU','CQSSC_DOUBLESIDE_HE')" +
				    						" or a.type_code like 'CQSSC_DOUBLESIDE_%'" +
    						                 " and a.type_code like 'CQSSC_BALL%' ");
    			}else if(playType.equals("CQ_ZH")){
    				sqTemplatelBuffer.append(" and a.type_code not like 'CQSSC_BALL%'" +
				    						" and a.type_code not like 'CQSSC_BZ%'" +
				    						" and a.type_code not like 'CQSSC_SZ%'" +
				    						" and a.type_code not like 'CQSSC_DZ%'" +
				    						" and a.type_code not like 'CQSSC_BS%'" +
				    						" and a.type_code not like 'CQSSC_ZL%'" +
    										 " or a.type_code like 'CQSSC_DOUBLESIDE_ZH%'" +
    										 " and a.type_code in ('CQSSC_DOUBLESIDE_LONG','CQSSC_DOUBLESIDE_HU','CQSSC_DOUBLESIDE_HE')");
    										 
    				
    			}else if(playType.equals("CQ_QIANSAN")){
    				sqTemplatelBuffer.append(" and a.type_code not like 'CQSSC_BALL%' " +
    						" and a.type_code not like 'CQSSC_DOUBLESIDE_%' ");
    				
    			}
    		}
    	}
		
    	sqTemplatelBuffer.append(" group by a.type_code ");
      
        String[] scanTableList = new String[]{tableName};
        
    	if(("GD_1-8").equals(playType)){
    		scanTableList=Constant.GDKLSF_LOTTERY_TABLE_LIST;
    	}
    	if(Constant.LOTTERY_TYPE_CQSSC.equals(lotteryType)){
    			scanTableList=Constant.CQSSC_TABLE_LIST;
    	}
        for (int i = 0; i < scanTableList.length; i++) {
    	  finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", scanTableList[i]));
    	  if(i!=scanTableList.length-1)
    	  finalSql.append(" union all ");
    	  finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	  
        }
        if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){
        	finalSql.append(" union all ");
            String sql ="select a.type_code as typeCode," +
                   "count(distinct order_no)   as turnover," +
                   "sum(money*a." + rateUser + "/100) as totalMoney" +
              " from tb_replenish a,"  +
                   "(select t1.id, t1.account, t1.chs_name, t1.user_type from tb_frame_manager_staff t1) b " +
              "where a." + myColumn + " = ?" +
              " and decode(a." + nextColumn + ",'',a.replenish_user_id,a." + nextColumn + ") = b.id" +
              " and user_type != 7 " +
              " and a.win_state in(0,6,7) " +
              " and a.periods_num=? ";
            finalSql.append(sql);
            if(playType.indexOf("HK")>-1){
    	    	if(playType.equals("HK_TA")){
    	        	finalSql.append(" and a.commission_type in ('HK_TA','HK_TB') " );
    			}else{
    				finalSql.append(" and a.commission_type = ? " );
    				templateParameterList.add(playType);
    			}
        	}else{
        		if(playType.indexOf("GD")>-1){
	        		if(playType.equals("GD_1-8")){
	        			finalSql.append(" and a.type_code != 'GDKLSF_DOUBLESIDE_LONG' " +
	    										 " and a.type_code != 'GDKLSF_DOUBLESIDE_HU' " +
	    										 " and a.type_code not like 'GDKLSF_STRA%' " +
	    										 " and a.type_code not like 'GDKLSF_DOUBLESIDE_ZH%' " );
	    			}else if(playType.equals("GD_ZH")){
	    				finalSql.append(" and a.type_code not like 'GDKLSF_BALL%'" +
	    						" and a.type_code not like 'GDKLSF_STRA%' " + 
	    						" or a.type_code like 'GDKLSF_DOUBLESIDE_ZH%' " +
	    						" and a.type_code in ('GDKLSF_DOUBLESIDE_LONG','GDKLSF_DOUBLESIDE_HU') ");
	    				
	    			}else{
	    				finalSql.append(" and a.type_code like 'GDKLSF_STRA%' " );
	    			}
        		}else if(playType.indexOf("CQ")>-1){
        			if(playType.equals("CQ_1-5")){
        				finalSql.append(" and a.type_code not like 'CQSSC_DOUBLESIDE_ZH%'" +
		        						" and a.type_code not like 'CQSSC_BZ%'" +
			    						" and a.type_code not like 'CQSSC_SZ%'" +
			    						" and a.type_code not like 'CQSSC_DZ%'" +
			    						" and a.type_code not like 'CQSSC_BS%'" +
			    						" and a.type_code not like 'CQSSC_ZL%'" +
								        " and a.type_code not in ('CQSSC_DOUBLESIDE_LONG','CQSSC_DOUBLESIDE_HU','CQSSC_DOUBLESIDE_HE')" +
        						        " or a.type_code like 'CQSSC_DOUBLESIDE_%'" +
									    " and a.type_code like 'CQSSC_BALL%' ");
	    			}else if(playType.equals("CQ_ZH")){
	    				finalSql.append(" and a.type_code not like 'CQSSC_BALL%'" +
			    						" and a.type_code not like 'CQSSC_BZ%'" +
			    						" and a.type_code not like 'CQSSC_SZ%'" +
			    						" and a.type_code not like 'CQSSC_DZ%'" +
			    						" and a.type_code not like 'CQSSC_BS%'" +
			    						" and a.type_code not like 'CQSSC_ZL%'" +
	    								" or a.type_code like 'CQSSC_DOUBLESIDE_ZH%'" +
	    								" and a.type_code in ('CQSSC_DOUBLESIDE_LONG','CQSSC_DOUBLESIDE_HU','CQSSC_DOUBLESIDE_HE')");
	    				
	    			}else if(playType.equals("CQ_QIANSAN")){
	    				finalSql.append(" and a.type_code not like 'CQSSC_BALL%' " +
	    						" and a.type_code not like 'CQSSC_DOUBLESIDE_%' ");
	    				
	    			}
        		}
        	}
    		finalSql.append(" group by a.type_code ");
            
        	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
        }
        
        finalSql.append(") group by typeCode ") ;
        List<Zhangdan> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new DeliveryReportItemMapperZ());
         return reportList;

    }
    
  //查询帐单(本级补出的)
    @Override
    public List<ZhanDangVO> queryZhangDangForOut(String lotteryType,String periodNum,Long userid,String userType,String rateUser,String commissionUser)
	{
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	finalSql.append("select a.type_code 				 as typeCode," +
				"count(distinct order_no)  				 as turnover," +
				"-sum(money*a." + rateUser + "/100)	     as totalMoney," +
				"-sum(money*a." + commissionUser + "/100) as commissionMoney " +
				" from tb_replenish a ");
				
		finalSql.append(" where " );
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
					finalSql.append(" a.replenish_user_id in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
					
				}else{
					finalSql.append(" a.replenish_user_id=? ");
				}
		finalSql.append(" and a.win_state in(0,6,7) and a.periods_num=? ");
		
		if(Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)){
				finalSql.append(" and a.type_code like 'GDKLSF_%' " );
		}else if(Constant.LOTTERY_TYPE_CQSSC.equals(lotteryType)){
				finalSql.append(" and a.type_code not like 'CQSSC_%' ");
		}else if(Constant.LOTTERY_TYPE_BJ.equals(lotteryType)){
			finalSql.append(" and a.type_code not like 'BJ_%' ");
		}else if(Constant.LOTTERY_TYPE_K3.equals(lotteryType)){
			finalSql.append(" and a.type_code not like 'K3_%' ");
		}else if(Constant.LOTTERY_TYPE_NC.equals(lotteryType)){
			finalSql.append(" and a.type_code not like 'NC_%' ");
	    }
		
		finalSql.append(" group by a.type_code ");
		
        templateParameterList.add(userid);
    	templateParameterList.add(periodNum);
    	
		finalParameterList.addAll(Lists.newArrayList(templateParameterList));
		
		List<ZhanDangVO> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new ZhanDangItemMapperZ());
		return reportList;
    	
	}
    
    //查询帐单(下级补进的)
    @Override
    public List<ZhanDangVO> queryZhangDangForIn(String lotteryType,String periodNum,Long userid,String userType
    		,String myColumn,String rateUser,String commissionUser,String outCommissionUser,String nextColumn)
    {
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	
    	finalSql.append("select typeCode,0 as turnover,sum(totalMoney) as totalMoney,sum(commissionMoney) as commissionMoney from (");
    	
    	//*********************本级补出 START*******************
    	finalSql.append("select a.type_code 				 as typeCode," +
				"count(distinct order_no)  				 as turnover," +
				"-sum(money)	     as totalMoney," +
				"-sum(money*a." + outCommissionUser + "/100) as commissionMoney " +
				" from tb_replenish a ");
				
		finalSql.append(" where " );
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
					finalSql.append(" a.replenish_user_id in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
					
				}else{
					finalSql.append(" a.replenish_user_id=? ");
				}
		finalSql.append(" and a.win_state in(0,6,7) and a.periods_num=? "
				+ "and a.type_code not like 'GDKLSF_STRAIGHTTHROUGH%' and a.type_code not like 'NC_STRAIGHTTHROUGH%'");
		
		if(Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)){
				finalSql.append(" and a.type_code like 'GDKLSF_%' " );
		}else if(Constant.LOTTERY_TYPE_CQSSC.equals(lotteryType)){
				finalSql.append(" and a.type_code like 'CQSSC_%' ");
		}else if(Constant.LOTTERY_TYPE_K3.equals(lotteryType)){
			finalSql.append(" and a.type_code like 'K3_%' ");
		}else if(Constant.LOTTERY_TYPE_BJ.equals(lotteryType)){
			finalSql.append(" and a.type_code like 'BJ_%' ");
	    }else if(Constant.LOTTERY_TYPE_NC.equals(lotteryType)){
	    	finalSql.append(" and a.type_code like 'NC_%' ");
	    }
		
		finalSql.append(" group by a.type_code ");
		
        templateParameterList.add(userid);
    	templateParameterList.add(periodNum);
    	//*********************本级补出 END*******************
    	
    	//**********************加上下级补进的************START****************
    	if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){
    		finalSql.append(" UNION ALL "); 
    		finalSql.append("select a.type_code 				 as typeCode," +
    				"0  				 as turnover," +
    				"sum(money*a." + rateUser + "/100)	     as totalMoney," +
					"sum(money*a." + commissionUser + "/100*a." + rateUser + "/100) as commissionMoney " +
    				" from tb_replenish a " +
    				" where a." + myColumn + " = ?" +
    				" and a.win_state in(0,6,7) " +
    				" and a.periods_num=? and a.type_code not like 'GDKLSF_STRAIGHTTHROUGH%' and a.type_code not like 'NC_STRAIGHTTHROUGH%'");
    		if(Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)){
					finalSql.append(" and a.type_code like 'GDKLSF_%' " );
			}else if(Constant.LOTTERY_TYPE_CQSSC.equals(lotteryType)){
					finalSql.append(" and a.type_code like 'CQSSC_%' ");
			}else if(Constant.LOTTERY_TYPE_K3.equals(lotteryType)){
				finalSql.append(" and a.type_code like 'K3_%' ");
			}else if(Constant.LOTTERY_TYPE_BJ.equals(lotteryType)){
				finalSql.append(" and a.type_code like 'BJ_%' ");
			}else if(Constant.LOTTERY_TYPE_NC.equals(lotteryType)){
				finalSql.append(" and a.type_code like 'NC_%' ");
		    }
    		
    		finalSql.append(" group by a.type_code ");
    		
    		templateParameterList.add(userid);
        	templateParameterList.add(periodNum);
        	//**********************查询下级补进的************END****************
    	}
    		
    	finalSql.append(") group by typeCode order by typeCode asc ") ;
    	
		finalParameterList.addAll(Lists.newArrayList(templateParameterList));
		List<ZhanDangVO> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new ZhanDangItemMapperZ());
    	
    	return reportList;
    	
    }
    
    //查询帐单(投注的)
    @Override
    public List<ZhanDangVO> queryZhangDang(String lotteryType,String periodNum,Long userid,String userType
    		,String myColumn,String rateUser,String commissionUser,String nextColumn,String tableName)
    		{
    	StringBuffer finalSql=new StringBuffer();
    	finalSql.append("select typeCode,sum(turnover) turnover,sum(totalMoney) totalMoney,sum(commissionMoney) commissionMoney from (");
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	sqTemplatelBuffer.append(" select a.type_code as typeCode, count(distinct order_no) as turnover,sum(money*a." + rateUser + "/100) as totalMoney," +
    			"sum(money*a." + commissionUser + "/100*a." + rateUser + "/100) as commissionMoney ");
    	
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
    	
    	sqTemplatelBuffer.append(" and a.periods_num=? " );
    	templateParameterList.add(periodNum);
    	
    	if(tableName.indexOf("GD")!=-1){
    		sqTemplatelBuffer.append(" and a.type_code not in('GDKLSF_DOUBLESIDE_LONG','GDKLSF_DOUBLESIDE_HU',"+
			            "'GDKLSF_DOUBLESIDE_ZHDAN','GDKLSF_DOUBLESIDE_ZHS','GDKLSF_DOUBLESIDE_ZHWD',"+
			            "'GDKLSF_DOUBLESIDE_ZHWX','GDKLSF_DOUBLESIDE_ZHDA','GDKLSF_DOUBLESIDE_ZHX') ");
    	}
    	
    	sqTemplatelBuffer.append(" group by a.type_code ");
    	
    	//在处理广东时，双面的投注，其他8张投注表也会有双面的投注
    	String[] scanTableList = new String[]{tableName,"TB_GDKLSF_DOUBLE_SIDE"};
    	
    	/*if(Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)){
    		scanTableList=Constant.GDKLSF_TABLE_LIST;
    	}else */
    	if(Constant.LOTTERY_TYPE_CQSSC.equals(lotteryType)){
    		scanTableList=Constant.CQSSC_TABLE_LIST;
    	}else if(Constant.LOTTERY_TYPE_BJ.equals(lotteryType)){
    		scanTableList=Constant.BJSC_TABLE_LIST;
		}else if(Constant.LOTTERY_TYPE_K3.equals(lotteryType)){
			scanTableList=Constant.K3_TABLE_LIST;
		}else if(Constant.LOTTERY_TYPE_NC.equals(lotteryType)){
			scanTableList=Constant.NC_TABLE_LIST;
		}
    	
    	for (int i = 0; i < scanTableList.length; i++) {
    		finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", scanTableList[i]));
    		if(i!=scanTableList.length-1)
    			finalSql.append(" union all ");
    		finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    		
    	}
    	
    	finalSql.append(") group by typeCode order by typeCode asc ") ;
    	List<ZhanDangVO> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new ZhanDangItemMapperZ());
    	return reportList;
    	
    }
//连码处理start-------------------------------------------------------   
    //查询帐单(连码的)
    @Override
    public List<ZhanDangLMDetailVO> queryZhangDangLM(String lotteryType,String periodNum,Long userId,String userType
    		,String myColumn,String rateUser,String commissionUser,String nextColumn)
    		{
		StringBuffer finalSql=new StringBuffer();
		StringBuffer sqTemplatelBuffer=new StringBuffer();	
		List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
	    sqTemplatelBuffer.append( " select " +
						"a.order_no          as orderNo," +
						"max(a.betting_date) as bettingDate," +
						"a.periods_num 	     as periodsNum," +
						"a.type_code         as typeCode," +
						"max(b.account) 	 as userName," +
						"max(a.plate)        as plate," +
						"max(a.odds)   		 as odds," +
						"sum(a.money*a."+rateUser+"/100)   	 as money," +
						"sum(a.money*a."+commissionUser+"/100*a."+rateUser+"/100)   as commissionMoney," +
						"max(a.attribute)		 				 as attribute ," +
						"max(b.user_type)		 					 as userType," +
						//"max(b.PARENT_STAFF_TYPE_QRY)		 	 as parentUserType," +
						"count(order_no) as count " +
						"from TB_GDKLSF_STRAIGHTTHROUGH a ,tb_frame_member_staff b where");
			
	    if(ManagerStaff.USER_TYPE_CHIEF.equals(userType))
    	{
    		sqTemplatelBuffer.append(" a.chiefstaff=? ");
    	}
    	
    	else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType))
    	{
    		sqTemplatelBuffer.append(" a.branchstaff=? ");
    	}
    	else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType))
    	{
    		sqTemplatelBuffer.append(" a.stockholderstaff=? ");
    	}
    	else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType))
    	{
    		sqTemplatelBuffer.append(" a.genagenstaff=? ");
    	}
    	else if(ManagerStaff.USER_TYPE_AGENT.equals(userType))
    	{
    		sqTemplatelBuffer.append(" a.agentstaff=? ");
    	}	
    	
    	sqTemplatelBuffer.append(" and a.betting_user_id=b.id and user_type!=7 and a.periods_num=? ");
	    
	        /*sqTemplatelBuffer.append(" where  a." + myColumn + "=? and a.betting_user_id=b.id and user_type!=7 and a.periods_num=? ")*/
			
		templateParameterList.add(userId);  
		templateParameterList.add(periodNum);
				
    	sqTemplatelBuffer.append(" group by a.order_no,type_code,a.periods_num " );
		finalSql.append(sqTemplatelBuffer);
		finalParameterList.addAll(Lists.newArrayList(templateParameterList));
		
		List<ZhanDangLMDetailVO> retList = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapperClass());
		
		return retList;
    	
 }
    
    //查询帐单(农场连码的)
    @Override
    public List<ZhanDangLMDetailVO> queryZhangDangLM_NC(String lotteryType,String periodNum,Long userId,String userType
    		,String myColumn,String rateUser,String commissionUser,String nextColumn)
    		{
    	StringBuffer finalSql=new StringBuffer();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	sqTemplatelBuffer.append( " select " +
    			"a.order_no          as orderNo," +
    			"max(a.betting_date) as bettingDate," +
    			"a.periods_num 	     as periodsNum," +
    			"a.type_code         as typeCode," +
    			"max(b.account) 	 as userName," +
    			"max(a.plate)        as plate," +
    			"max(a.odds)   		 as odds," +
    			"sum(a.money*a."+rateUser+"/100)   	 as money," +
    			"sum(a.money*a."+commissionUser+"/100*a."+rateUser+"/100)   as commissionMoney," +
    			"max(a.attribute)		 				 as attribute ," +
    			"max(b.user_type)		 					 as userType," +
    			//"max(b.PARENT_STAFF_TYPE_QRY)		 	 as parentUserType," +
    			"count(order_no) as count " +
    			"from TB_NC a ,tb_frame_member_staff b where ");
    	
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType))
    	{
    		sqTemplatelBuffer.append(" a.chiefstaff=? ");
    	}
    	
    	else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType))
    	{
    		sqTemplatelBuffer.append(" a.branchstaff=? ");
    	}
    	else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType))
    	{
    		sqTemplatelBuffer.append(" a.stockholderstaff=? ");
    	}
    	else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType))
    	{
    		sqTemplatelBuffer.append(" a.genagenstaff=? ");
    	}
    	else if(ManagerStaff.USER_TYPE_AGENT.equals(userType))
    	{
    		sqTemplatelBuffer.append(" a.agentstaff=? ");
    	}	
    	
    	sqTemplatelBuffer.append(" and a.betting_user_id=b.id and user_type!=7 "
    			+ "and a.periods_num=? and a.type_code like 'NC_STRAIGHTTHROUGH%'");
    			
    	/*sqTemplatelBuffer.append(" where  a." + myColumn + "=? and a.betting_user_id=b.id and user_type!=7 "
    					+ "and a.periods_num=? and a.type_code like 'NC_STRAIGHTTHROUGH%'")*/
    	
    	templateParameterList.add(userId);  
    	templateParameterList.add(periodNum);
    	
    	sqTemplatelBuffer.append(" group by a.order_no,type_code,a.periods_num " );
    	finalSql.append(sqTemplatelBuffer);
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	
    	List<ZhanDangLMDetailVO> retList = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapperClass());
    	
    	return retList;
    	
    		}
    
    @Override
	public List<ZhanDangLMDetailVO> queryReplenishOutDetailLM(Long userId,String periodsNum,String userType,String commissionUser) {
		
		List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	
		StringBuffer finalSql=new StringBuffer();
		StringBuffer sqTemplatelBuffer=new StringBuffer();	
		sqTemplatelBuffer.append( " select " +
				"a.order_no          as orderNo," +
				"a.betting_date      as bettingDate," +
				"a.periods_num 	     as periodsNum," +
				"a.type_code         as typeCode," +
				"b.account 		     as userName," +
				"a.plate             as plate," +
				"a.odds   		 	 as odds," +
				"-a.money   			 as money," +
				"-(a.money*a."+commissionUser+"/100)   as commissionMoney," +
				"a.attribute		 				 as attribute ," +
				userType +		 				 "   as userType ," +
				//"b.PARENT_STAFF_TYPE_QRY		 	 as parentUserType," +
				"1 as count "
				+ " from TB_REPLENISH a ,tb_frame_manager_staff b ");
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
					sqTemplatelBuffer.append(" where a.replenish_user_id=b.id and a.replenish_user_id " +
							"in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?)  "); 
				}else{
					sqTemplatelBuffer.append(" where a.replenish_user_id=b.id and a.replenish_user_id=? "); 
				}
		templateParameterList.add(userId);  
		
		sqTemplatelBuffer.append(" and a.type_code like 'GDKLSF_STRAIGHTTHROUGH%' and a.win_state='0' and a.periods_num = ? ");
		templateParameterList.add(periodsNum);  
		
		finalSql.append(sqTemplatelBuffer);
		finalParameterList.addAll(Lists.newArrayList(templateParameterList));
		
		List<ZhanDangLMDetailVO> betlist = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapperClass());
		return betlist;
		
	}   
    
    @Override
	public List<ZhanDangLMDetailVO> queryReplenishInDetailLM(Long userId,String periodsNum,String rateUser,String userType,String commissionUser) {
		
			StringBuffer finalSql=new StringBuffer();
			StringBuffer sqTemplatelBuffer=new StringBuffer();	
			
			List<Object> finalParameterList=new ArrayList<Object>();
	    	List<Object> templateParameterList=new ArrayList<Object>();
	    	
			sqTemplatelBuffer.append( " select " +
					"a.order_no          as orderNo," +
					"a.betting_date      as bettingDate," +
					"a.periods_num 	     as periodsNum," +
					"a.type_code         as typeCode," +
					"b.account 		     as userName," +
					"a.plate             as plate," +
					"a.odds   		 	 as odds," +
					"a." + rateUser + "/100 * a.money   	  as money," +
					"a.money*a."+commissionUser+"/100*(a." + rateUser + "/100)   as commissionMoney," +
					"a.attribute		 					 as attribute," +
					"b.user_type		 					 as userType," +
					"1 as count "
					+ " from TB_REPLENISH a ,tb_frame_manager_staff b"				
					+ " where a.periods_num = ? and a.type_code like 'GDKLSF_STRAIGHTTHROUGH%' and a.win_state='0' and a.replenish_user_id= b.id ");
			templateParameterList.add(periodsNum);  
			
			if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){sqTemplatelBuffer.append(" and a.chiefstaff=? ");}
			else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType)){sqTemplatelBuffer.append(" and a.branchstaff=?  ");}
			else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType)){sqTemplatelBuffer.append(" and a.stockholderstaff=? ");}
			else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.genagenstaff=? ");}
			else if(ManagerStaff.USER_TYPE_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.agentstaff=? ");}
			templateParameterList.add(userId);  
			
			finalSql.append(sqTemplatelBuffer);
			finalParameterList.addAll(Lists.newArrayList(templateParameterList));
			
			List<ZhanDangLMDetailVO> betlist = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapperClass());
		return betlist;
		
	}
    @Override
    public List<ZhanDangLMDetailVO> queryReplenishOutDetailLM_NC(Long userId,String periodsNum,String userType,String commissionUser) {
    	
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	
    	StringBuffer finalSql=new StringBuffer();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	sqTemplatelBuffer.append( " select " +
    			"a.order_no          as orderNo," +
    			"a.betting_date      as bettingDate," +
    			"a.periods_num 	     as periodsNum," +
    			"a.type_code         as typeCode," +
    			"b.account 		     as userName," +
    			"a.plate             as plate," +
    			"a.odds   		 	 as odds," +
    			"-a.money   			 as money," +
    			"-(a.money*a."+commissionUser+"/100)   as commissionMoney," +
    			"a.attribute		 				 as attribute ," +
    			userType +		 				 "   as userType ," +
    			//"b.PARENT_STAFF_TYPE_QRY		 	 as parentUserType," +
    			"1 as count "
    			+ " from TB_REPLENISH a ,tb_frame_manager_staff b ");
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
    		sqTemplatelBuffer.append(" where a.replenish_user_id=b.id and a.replenish_user_id " +
    				"in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?)  "); 
    	}else{
    		sqTemplatelBuffer.append(" where a.replenish_user_id=b.id and a.replenish_user_id=? "); 
    	}
    	templateParameterList.add(userId);  
    	
    	sqTemplatelBuffer.append(" and a.type_code like 'NC_STRAIGHTTHROUGH%' and a.win_state='0' and a.periods_num = ? ");
    	templateParameterList.add(periodsNum);  
    	
    	finalSql.append(sqTemplatelBuffer);
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	
    	List<ZhanDangLMDetailVO> betlist = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapperClass());
    	return betlist;
    	
    }   
    
    @Override
    public List<ZhanDangLMDetailVO> queryReplenishInDetailLM_NC(Long userId,String periodsNum,String rateUser,String userType,String commissionUser) {
    	
    	StringBuffer finalSql=new StringBuffer();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	
    	sqTemplatelBuffer.append( " select " +
    			"a.order_no          as orderNo," +
    			"a.betting_date      as bettingDate," +
    			"a.periods_num 	     as periodsNum," +
    			"a.type_code         as typeCode," +
    			"b.account 		     as userName," +
    			"a.plate             as plate," +
    			"a.odds   		 	 as odds," +
    			"a." + rateUser + "/100 * a.money   	  as money," +
    			"a.money*a."+commissionUser+"/100*(a." + rateUser + "/100)   as commissionMoney," +
    			"a.attribute		 					 as attribute," +
    			"b.user_type		 					 as userType," +
    			"1 as count "
    			+ " from TB_REPLENISH a ,tb_frame_manager_staff b"				
    			+ " where a.periods_num = ? and a.type_code like 'NC_STRAIGHTTHROUGH%' and a.win_state='0' and a.replenish_user_id= b.id ");
    	templateParameterList.add(periodsNum);  
    	
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){sqTemplatelBuffer.append(" and a.chiefstaff=? ");}
    	else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType)){sqTemplatelBuffer.append(" and a.branchstaff=?  ");}
    	else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType)){sqTemplatelBuffer.append(" and a.stockholderstaff=? ");}
    	else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.genagenstaff=? ");}
    	else if(ManagerStaff.USER_TYPE_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.agentstaff=? ");}
    	templateParameterList.add(userId);  
    	
    	finalSql.append(sqTemplatelBuffer);
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	
    	List<ZhanDangLMDetailVO> betlist = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new DetailItemMapperClass());
    	return betlist;
    	
    }
//连码处理end-------------------------------------------------------   
    
    
    //查询帐单(双面的总数龙虎的)
    @Override
    public List<ZhanDangVO> queryZhangDangDoubleLH(String lotteryType,String periodNum,Long userid,String userType
    		,String myColumn,String rateUser,String commissionUser,String nextColumn)
    		{
    	StringBuffer finalSql=new StringBuffer();
    	finalSql.append("select typeCode,sum(turnover) turnover,sum(totalMoney) totalMoney,sum(commissionMoney) commissionMoney from (");
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	sqTemplatelBuffer.append(" select a.type_code as typeCode, count(distinct order_no) as turnover,sum(money*a." + rateUser + "/100) as totalMoney," +
    			"sum(money*a." + commissionUser + "/100*a." + rateUser + "/100) as commissionMoney ");
    	
    	sqTemplatelBuffer.append(" from TB_GDKLSF_DOUBLE_SIDE a, (select t1.id,t1.account,t1.chs_name,t1.user_type from tb_frame_manager_staff t1 union select  t2.id,t2.account,t2.chs_name,t2.user_type from tb_frame_member_staff t2 ) b ");
    	
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
    	
    	sqTemplatelBuffer.append(" and a.periods_num=? and a.type_code in('GDKLSF_DOUBLESIDE_LONG','GDKLSF_DOUBLESIDE_HU',"+
            "'GDKLSF_DOUBLESIDE_ZHDAN','GDKLSF_DOUBLESIDE_ZHS','GDKLSF_DOUBLESIDE_ZHWD','GDKLSF_DOUBLESIDE_ZHWX',"+
            "'GDKLSF_DOUBLESIDE_ZHDA','GDKLSF_DOUBLESIDE_ZHX') " );
    	templateParameterList.add(periodNum);
    	
    	sqTemplatelBuffer.append(" group by a.type_code ");
    	
    	finalSql.append(sqTemplatelBuffer.toString());
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	
    	finalSql.append(") group by typeCode order by typeCode asc ") ;
    	List<ZhanDangVO> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new ZhanDangItemMapperZ());
    	return reportList;
    	
    		}
    
    
    //******************总额统计******************START*************
    //统计广东和北京的号球
    @Override
    public List<ZhanDangVO> queryBallRightTotal(String lotteryType,String periodNum,Long userid,String userType
    		,String myColumn,String rateUser,String status)
    		{
    	StringBuffer finalSql=new StringBuffer();
    	finalSql.append("SELECT commissionType AS commissionType,sum(totalMoney) AS totalMoney FROM (");
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	sqTemplatelBuffer.append(" select c.play_sub_type AS commissionType,");
    	
    	if(Constant.TRUE_STATUS.equals(status)){
    		sqTemplatelBuffer.append("sum(money * " + rateUser + "/100) AS totalMoney ");
    	}else{
    		sqTemplatelBuffer.append("sum(money) AS totalMoney ");
    	}
    	
    	sqTemplatelBuffer.append(" from {TableName}  a," +
    							 "tb_play_type       c," +
    			" (select t1.id,t1.account,t1.chs_name,t1.user_type from tb_frame_manager_staff t1 union select  t2.id,t2.account,t2.chs_name,t2.user_type from tb_frame_member_staff t2 ) b ");
    	
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
    	
    	sqTemplatelBuffer.append(" and a.periods_num=? and a.type_code=c.type_code and c.play_type =? and c.play_sub_type like 'BALL%'" );
    	
    	sqTemplatelBuffer.append(" group by c.play_sub_type ");
    	
    	String[] scanTableList = new String[]{};
    	
    	if(Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)){
    		scanTableList=Constant.GDKLSF_LOTTERY_TABLE_LIST;//不对连码处理，连码单独处理
    	}else if(Constant.LOTTERY_TYPE_BJ.equals(lotteryType)){
    		scanTableList=Constant.BJSC_TABLE_LIST;
		}else if(Constant.LOTTERY_TYPE_NC.equals(lotteryType.trim())){
			scanTableList=new String[]{Constant.NC_TABLE_NAME};
		}
    	
    	for (int i = 0; i < scanTableList.length; i++) {
    		finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", scanTableList[i]));
    		if(i!=scanTableList.length-1)
    			finalSql.append(" union all ");
    		templateParameterList.add(userid);
    		templateParameterList.add(periodNum);
    		templateParameterList.add(lotteryType);
    		
    	}
    	
  //************处理补货*************START********
    	finalSql.append(" union all ");
    	
    	//*********************本级补出 START*******************
    	finalSql.append("select b.play_sub_type AS commissionType,");
    	
    		finalSql.append("-sum(t.money)  as totalMoney ");
    			finalSql.append(" from tb_replenish t,tb_play_type b ");
				
		finalSql.append(" where " );
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
					finalSql.append(" t.replenish_user_id in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
					
				}else{
					finalSql.append(" t.replenish_user_id=? ");
				}
				
		templateParameterList.add(userid);
		
		finalSql.append(" and t.win_state in(0,6,7) and t.periods_num=? and t.type_code=b.type_code and b.play_type =? and b.play_sub_type like 'BALL%' ");
		templateParameterList.add(periodNum);
		templateParameterList.add(lotteryType);
		
		if(Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)){
				finalSql.append(" and t.type_code like 'GDKLSF_%' " );
		}else if(Constant.LOTTERY_TYPE_BJ.equals(lotteryType)){
			//finalSql.append(" and t.type_code not like 'BJ_%' ");
	    }else if(Constant.LOTTERY_TYPE_NC.equals(lotteryType)){
			finalSql.append(" and t.type_code like 'NC_%' " );
	    }
		
		finalSql.append(" group by b.play_sub_type ");
		
    	//*********************本级补出 END*******************
    	
    	//**********************加上下级补进的************START****************
    	if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){
    		finalSql.append(" UNION ALL "); 
    		finalSql.append("select b.play_sub_type      AS commissionType,");
    				if(Constant.TRUE_STATUS.equals(status)){
    					finalSql.append("sum(c.money*c." + rateUser + "/100)	     as totalMoney ");
    				}else{
    					finalSql.append("sum(c.money)	     as totalMoney ");
    				}
    		finalSql.append(" from tb_replenish c,tb_play_type b " +
    				" where c." + myColumn + " = ?" +
    				" and c.win_state in(0,6,7) " +
    				" and c.periods_num=? and c.type_code=b.type_code and b.play_type =? and b.play_sub_type like 'BALL%' ");
    		
    		if(Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)){
					finalSql.append(" and c.type_code like 'GDKLSF_%' " );
			}else if(Constant.LOTTERY_TYPE_BJ.equals(lotteryType)){
				//finalSql.append(" and c.type_code not like 'BJ_%' ");
			}else if(Constant.LOTTERY_TYPE_NC.equals(lotteryType)){
				finalSql.append(" and c.type_code like 'NC_%' " );
		    }
    		
    		finalSql.append(" group by b.play_sub_type ");
    		
    		templateParameterList.add(userid);
        	templateParameterList.add(periodNum);
        	templateParameterList.add(lotteryType);
        	//**********************查询下级补进的************END****************
    	}
   //************处理补货*************END********
    	
    	finalSql.append(") group by commissionType order by commissionType asc ") ;
    	
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	
    	List<ZhanDangVO> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new rightBarItemMapperZ());
    	return reportList;
    	
    }
    
    //统计广东连码
    @Override
    public List<ZhanDangVO> queryGDLMRightTotal(String lotteryType,String periodNum,Long userid,String userType,String myColumn,String rateUser,String status)
    		{
    	StringBuffer finalSql=new StringBuffer();
    	finalSql.append("SELECT commissionType AS commissionType,sum(totalMoney) AS totalMoney FROM (");
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	sqTemplatelBuffer.append(" select a.type_code AS commissionType,");
    	
    	if(Constant.TRUE_STATUS.equals(status)){
    		sqTemplatelBuffer.append("sum(money * " + rateUser + "/100) AS totalMoney ");
    	}else{
    		sqTemplatelBuffer.append("sum(money) AS totalMoney ");
    	}
    	
    	sqTemplatelBuffer.append(" from {TableName}  a,(select t1.id,t1.account,t1.chs_name,t1.user_type from tb_frame_manager_staff t1 union select  t2.id,t2.account,t2.chs_name,t2.user_type from tb_frame_member_staff t2 ) b ");
    	
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
    	
    	sqTemplatelBuffer.append(" and a.periods_num=?" );
    	
    	sqTemplatelBuffer.append(" group by a.type_code ");
    	
    	String[] scanTableList = new String[]{Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME};
    	
    	for (int i = 0; i < scanTableList.length; i++) {
    		finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", scanTableList[i]));
    		if(i!=scanTableList.length-1)
    			finalSql.append(" union all ");
    		templateParameterList.add(userid);
    		templateParameterList.add(periodNum);
    		
    	}
    	
    	//************处理补货*************START********
    	finalSql.append(" union all ");
    	
    	//*********************本级补出 START*******************
    	finalSql.append("select t.type_code AS commissionType,");
    	
    	finalSql.append("-sum(t.money)  as totalMoney ");
    	finalSql.append(" from tb_replenish t");
    	
    	finalSql.append(" where " );
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
    		finalSql.append(" t.replenish_user_id in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
    		
    	}else{
    		finalSql.append(" t.replenish_user_id=? ");
    	}
    	
    	templateParameterList.add(userid);
    	
    	finalSql.append(" and t.win_state in(0,6,7) and t.periods_num=? ");
    	templateParameterList.add(periodNum);
    	
		finalSql.append(" and t.type_code like 'GDKLSF_STRAIGHTTHROUGH%' " );
    	
    	finalSql.append(" group by t.type_code ");
    	
    	//*********************本级补出 END*******************
    	
    	//**********************加上下级补进的************START****************
    	if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){
    		finalSql.append(" UNION ALL "); 
    		finalSql.append("select c.type_code      AS commissionType,");
    		if(Constant.TRUE_STATUS.equals(status)){
    			finalSql.append("sum(c.money*c." + rateUser + "/100)	     as totalMoney ");
    		}else{
    			finalSql.append("sum(c.money)	     as totalMoney ");
    		}
    		finalSql.append(" from tb_replenish c " +
    				" where c." + myColumn + " = ?" +
    				" and c.win_state in(0,6,7) " +
    				" and c.periods_num=? ");
    		
			finalSql.append(" and c.type_code like 'GDKLSF_STRAIGHTTHROUGH%' " );
    		
    		finalSql.append(" group by c.type_code ");
    		
    		templateParameterList.add(userid);
    		templateParameterList.add(periodNum);
    		//**********************查询下级补进的************END****************
    	}
    	//************处理补货*************END********
    	
    	finalSql.append(") group by commissionType order by commissionType asc ") ;
    	
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	
    	List<ZhanDangVO> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new rightBarItemMapperZ());
    	return reportList;
    	
    }
    
    //统计农场连码
    @Override
    public List<ZhanDangVO> queryNCLMRightTotal(String lotteryType,String periodNum,Long userid,String userType,String myColumn,String rateUser,String status)
    {
    	StringBuffer finalSql=new StringBuffer();
    	finalSql.append("SELECT commissionType AS commissionType,sum(totalMoney) AS totalMoney FROM (");
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	sqTemplatelBuffer.append(" select a.type_code AS commissionType,");
    	
    	if(Constant.TRUE_STATUS.equals(status)){
    		sqTemplatelBuffer.append("sum(money * " + rateUser + "/100) AS totalMoney ");
    	}else{
    		sqTemplatelBuffer.append("sum(money) AS totalMoney ");
    	}
    	
    	sqTemplatelBuffer.append(" from {TableName}  a,(select t1.id,t1.account,t1.chs_name,t1.user_type from tb_frame_manager_staff t1 union select  t2.id,t2.account,t2.chs_name,t2.user_type from tb_frame_member_staff t2 ) b ");
    	
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
    	
    	sqTemplatelBuffer.append(" and a.periods_num=?" );
    	
    	sqTemplatelBuffer.append(" and a.type_code like 'NC_STRAIGHTTHROUGH%' " );
    	
    	sqTemplatelBuffer.append(" group by a.type_code ");
    	
    	String[] scanTableList = new String[]{Constant.NC_TABLE_NAME};
    	
    	for (int i = 0; i < scanTableList.length; i++) {
    		finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", scanTableList[i]));
    		if(i!=scanTableList.length-1)
    			finalSql.append(" union all ");
    		templateParameterList.add(userid);
    		templateParameterList.add(periodNum);
    		
    	}
    	
    	//************处理补货*************START********
    	finalSql.append(" union all ");
    	
    	//*********************本级补出 START*******************
    	finalSql.append("select t.type_code AS commissionType,");
    	
    	finalSql.append("-sum(t.money)  as totalMoney ");
    	finalSql.append(" from tb_replenish t");
    	
    	finalSql.append(" where " );
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
    		finalSql.append(" t.replenish_user_id in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
    		
    	}else{
    		finalSql.append(" t.replenish_user_id=? ");
    	}
    	
    	templateParameterList.add(userid);
    	
    	finalSql.append(" and t.win_state in(0,6,7) and t.periods_num=? ");
    	templateParameterList.add(periodNum);
    	
    	finalSql.append(" and t.type_code like 'NC_STRAIGHTTHROUGH%' " );
    	
    	finalSql.append(" group by t.type_code ");
    	
    	//*********************本级补出 END*******************
    	
    	//**********************加上下级补进的************START****************
    	if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){
    		finalSql.append(" UNION ALL "); 
    		finalSql.append("select c.type_code      AS commissionType,");
    		if(Constant.TRUE_STATUS.equals(status)){
    			finalSql.append("sum(c.money*c." + rateUser + "/100)	     as totalMoney ");
    		}else{
    			finalSql.append("sum(c.money)	     as totalMoney ");
    		}
    		finalSql.append(" from tb_replenish c " +
    				" where c." + myColumn + " = ?" +
    				" and c.win_state in(0,6,7) " +
    				" and c.periods_num=? ");
    		
    		finalSql.append(" and c.type_code like 'NC_STRAIGHTTHROUGH%' " );
    		
    		finalSql.append(" group by c.type_code ");
    		
    		templateParameterList.add(userid);
    		templateParameterList.add(periodNum);
    		//**********************查询下级补进的************END****************
    	}
    	//************处理补货*************END********
    	
    	finalSql.append(") group by commissionType order by commissionType asc ") ;
    	
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	
    	List<ZhanDangVO> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new rightBarItemMapperZ());
    	return reportList;
    	
    }
    
    
    //处理广东总数大小和总和单双
    @Override
    public List<ZhanDangVO> queryGdZHRightTotal(String lotteryType,String periodNum,Long userid,String userType,String myColumn,String rateUser,String status)
    		{
    	StringBuffer finalSql=new StringBuffer();
    	finalSql.append("SELECT commissionType AS commissionType,sum(totalMoney) AS totalMoney FROM (");
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	
    	sqTemplatelBuffer.append(" select d.commission_type as commissionType,sum(totalMoney) AS totalMoney from (");
    	sqTemplatelBuffer.append(" select a.type_code AS finaltype,");
    	
    	if(Constant.TRUE_STATUS.equals(status)){
    		sqTemplatelBuffer.append("sum(money * " + rateUser + "/100) AS totalMoney ");
    	}else{
    		sqTemplatelBuffer.append("sum(money) AS totalMoney ");
    	}
    	
    	sqTemplatelBuffer.append(" from {TableName}  a," +
    							 "tb_play_type       c," +
    			" (select t1.id,t1.account,t1.chs_name,t1.user_type from tb_frame_manager_staff t1 union select  t2.id,t2.account,t2.chs_name,t2.user_type from tb_frame_member_staff t2 ) b ");
    	
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
    	
    	sqTemplatelBuffer.append(" and a.periods_num=? and a.type_code=c.type_code and c.play_type =? and c.play_sub_type like 'DOUBLESIDE%'" );
    	
    	sqTemplatelBuffer.append(" group by a.type_code )t,tb_play_type d where t.finaltype=d.type_code  group by d.commission_type ");
    	
    	String[] scanTableList = new String[]{Constant.GDKLSF_DOUBLESIDE_TABLE_NAME};
    	
    	for (int i = 0; i < scanTableList.length; i++) {
    		finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", scanTableList[i]));
    		if(i!=scanTableList.length-1)
    			finalSql.append(" union all ");
    		templateParameterList.add(userid);
    		templateParameterList.add(periodNum);
    		templateParameterList.add(lotteryType);
    		
    	}
    	
  //************处理补货*************START********
    	finalSql.append(" union all ");
    	
    	//*********************本级补出 START*******************
    	finalSql.append(" select d.commission_type as commissionType,sum(totalMoney)  AS totalMoney from (");
    	finalSql.append(" select t.type_code AS finaltype,");
    	
    		finalSql.append("-sum(t.money)  as totalMoney ");
    			finalSql.append(" from tb_replenish t,tb_play_type b ");
				
		finalSql.append(" where " );
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
					finalSql.append(" t.replenish_user_id in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
					
				}else{
					finalSql.append(" t.replenish_user_id=? ");
				}
				
		templateParameterList.add(userid);
		
		finalSql.append(" and t.win_state in(0,6,7) and t.periods_num=? and t.type_code=b.type_code and b.play_type =? and b.play_sub_type like 'DOUBLESIDE%' ");
		templateParameterList.add(periodNum);
		templateParameterList.add(lotteryType);
		
		finalSql.append(" and t.type_code like 'GDKLSF_%' " );
		
		finalSql.append(" group by t.type_code)c,tb_play_type d where c.finaltype=d.type_code  group by d.commission_type ");
		
    	//*********************本级补出 END*******************
    	
    	//**********************加上下级补进的************START****************
    	if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){
    		finalSql.append(" UNION ALL "); 
    		
    		finalSql.append(" select d.commission_type as commissionType,sum(totalMoney) AS totalMoney from (");
    		finalSql.append("select c.type_code AS finaltype,");
    				if(Constant.TRUE_STATUS.equals(status)){
    					finalSql.append("sum(c.money*c." + rateUser + "/100)	     as totalMoney ");
    				}else{
    					finalSql.append("sum(c.money)	     as totalMoney ");
    				}
    		finalSql.append(" from tb_replenish c,tb_play_type b " +
    				" where c." + myColumn + " = ?" +
    				" and c.win_state in(0,6,7) " +
    				" and c.periods_num=? and c.type_code=b.type_code and b.play_type =? and b.play_sub_type like 'DOUBLESIDE%' ");
    		
			finalSql.append(" and c.type_code like 'GDKLSF_%' " );
    		
    		finalSql.append(" group by c.type_code)t,tb_play_type d where t.finaltype=d.type_code  group by d.commission_type ");
    		
    		templateParameterList.add(userid);
        	templateParameterList.add(periodNum);
        	templateParameterList.add(lotteryType);
        	//**********************查询下级补进的************END****************
    	}
   //************处理补货*************END********
    	
    	finalSql.append(") group by commissionType order by commissionType asc ") ;
    	
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	
    	List<ZhanDangVO> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new rightBarItemMapperZ());
    	return reportList;
    	
    } 
    
    //处理农场总数大小和总和单双
    @Override
    public List<ZhanDangVO> queryNCZHRightTotal(String lotteryType,String periodNum,Long userid,String userType,String myColumn,String rateUser,String status)
    {
    	StringBuffer finalSql=new StringBuffer();
    	finalSql.append("SELECT commissionType AS commissionType,sum(totalMoney) AS totalMoney FROM (");
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	
    	sqTemplatelBuffer.append(" select d.commission_type as commissionType,sum(totalMoney) AS totalMoney from (");
    	sqTemplatelBuffer.append(" select a.type_code AS finaltype,");
    	
    	if(Constant.TRUE_STATUS.equals(status)){
    		sqTemplatelBuffer.append("sum(money * " + rateUser + "/100) AS totalMoney ");
    	}else{
    		sqTemplatelBuffer.append("sum(money) AS totalMoney ");
    	}
    	
    	sqTemplatelBuffer.append(" from {TableName}  a," +
    			"tb_play_type       c," +
    			" (select t1.id,t1.account,t1.chs_name,t1.user_type from tb_frame_manager_staff t1 union select  t2.id,t2.account,t2.chs_name,t2.user_type from tb_frame_member_staff t2 ) b ");
    	
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
    	
    	sqTemplatelBuffer.append(" and a.periods_num=? and a.type_code=c.type_code and c.play_type =? and c.play_sub_type like 'DOUBLESIDE%'" );
    	
    	sqTemplatelBuffer.append(" group by a.type_code )t,tb_play_type d where t.finaltype=d.type_code  group by d.commission_type ");
    	
    	String[] scanTableList = new String[]{Constant.NC_TABLE_NAME};
    	
    	for (int i = 0; i < scanTableList.length; i++) {
    		finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", scanTableList[i]));
    		if(i!=scanTableList.length-1)
    			finalSql.append(" union all ");
    		templateParameterList.add(userid);
    		templateParameterList.add(periodNum);
    		templateParameterList.add(lotteryType);
    		
    	}
    	
    	//************处理补货*************START********
    	finalSql.append(" union all ");
    	
    	//*********************本级补出 START*******************
    	finalSql.append(" select d.commission_type as commissionType,sum(totalMoney)  AS totalMoney from (");
    	finalSql.append(" select t.type_code AS finaltype,");
    	
    	finalSql.append("-sum(t.money)  as totalMoney ");
    	finalSql.append(" from tb_replenish t,tb_play_type b ");
    	
    	finalSql.append(" where " );
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
    		finalSql.append(" t.replenish_user_id in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
    		
    	}else{
    		finalSql.append(" t.replenish_user_id=? ");
    	}
    	
    	templateParameterList.add(userid);
    	
    	finalSql.append(" and t.win_state in(0,6,7) and t.periods_num=? and t.type_code=b.type_code and b.play_type =? and b.play_sub_type like 'DOUBLESIDE%' ");
    	templateParameterList.add(periodNum);
    	templateParameterList.add(lotteryType);
    	
    	finalSql.append(" and t.type_code like 'NC_%' " );
    	
    	finalSql.append(" group by t.type_code)c,tb_play_type d where c.finaltype=d.type_code  group by d.commission_type ");
    	
    	//*********************本级补出 END*******************
    	
    	//**********************加上下级补进的************START****************
    	if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){
    		finalSql.append(" UNION ALL "); 
    		
    		finalSql.append(" select d.commission_type as commissionType,sum(totalMoney) AS totalMoney from (");
    		finalSql.append("select c.type_code AS finaltype,");
    		if(Constant.TRUE_STATUS.equals(status)){
    			finalSql.append("sum(c.money*c." + rateUser + "/100)	     as totalMoney ");
    		}else{
    			finalSql.append("sum(c.money)	     as totalMoney ");
    		}
    		finalSql.append(" from tb_replenish c,tb_play_type b " +
    				" where c." + myColumn + " = ?" +
    				" and c.win_state in(0,6,7) " +
    				" and c.periods_num=? and c.type_code=b.type_code and b.play_type =? and b.play_sub_type like 'DOUBLESIDE%' ");
    		
    		finalSql.append(" and c.type_code like 'NC_%' " );
    		
    		finalSql.append(" group by c.type_code)t,tb_play_type d where t.finaltype=d.type_code  group by d.commission_type ");
    		
    		templateParameterList.add(userid);
    		templateParameterList.add(periodNum);
    		templateParameterList.add(lotteryType);
    		//**********************查询下级补进的************END****************
    	}
    	//************处理补货*************END********
    	
    	finalSql.append(") group by commissionType order by commissionType asc ") ;
    	
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	
    	List<ZhanDangVO> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new rightBarItemMapperZ());
    	return reportList;
    	
    } 
    
    //处理北京双面
    @Override
    public List<ZhanDangVO> queryBJDoubleRightTotal(String lotteryType,String periodNum,Long userid,String userType,String myColumn,String rateUser,String status)
    {
    	StringBuffer finalSql=new StringBuffer();
    	finalSql.append("SELECT commissionType AS commissionType,sum(totalMoney) AS totalMoney FROM (");
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	
    	sqTemplatelBuffer.append(" select d.commission_type as commissionType,sum(totalMoney) AS totalMoney from (");
    	sqTemplatelBuffer.append(" select a.type_code AS finaltype,");
    	
    	if(Constant.TRUE_STATUS.equals(status)){
    		sqTemplatelBuffer.append("sum(money * " + rateUser + "/100) AS totalMoney ");
    	}else{
    		sqTemplatelBuffer.append("sum(money) AS totalMoney ");
    	}
    	
    	sqTemplatelBuffer.append(" from {TableName}  a," +
    			"tb_play_type       c," +
    			" (select t1.id,t1.account,t1.chs_name,t1.user_type from tb_frame_manager_staff t1 union select  t2.id,t2.account,t2.chs_name,t2.user_type from tb_frame_member_staff t2 ) b ");
    	
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
    	
    	//sqTemplatelBuffer.append(" and a.periods_num=? and a.type_code=c.type_code and c.play_type =? and c.play_sub_type = 'GROUP'" );
    	sqTemplatelBuffer.append(" and a.periods_num=? and a.type_code=c.type_code and c.play_type =? and c.play_sub_type = 'GROUP'" );
    	
    	sqTemplatelBuffer.append(" group by a.type_code )t,tb_play_type d where t.finaltype=d.type_code  group by d.commission_type ");
    	
    	String[] scanTableList = new String[]{Constant.BJSC_TABLE_NAME};
    	
    	for (int i = 0; i < scanTableList.length; i++) {
    		finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", scanTableList[i]));
    		if(i!=scanTableList.length-1)
    			finalSql.append(" union all ");
    		templateParameterList.add(userid);
    		templateParameterList.add(periodNum);
    		templateParameterList.add(lotteryType);
    		
    	}
    	
    	//************处理补货*************START********
    	finalSql.append(" union all ");
    	
    	//*********************本级补出 START*******************
    	finalSql.append(" select d.commission_type as commissionType,sum(totalMoney)  AS totalMoney from (");
    	finalSql.append(" select t.type_code AS finaltype,");
    	finalSql.append("-sum(t.money)  as totalMoney ");
    	finalSql.append(" from tb_replenish t,tb_play_type b ");
    	
    	finalSql.append(" where " );
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
    		finalSql.append(" t.replenish_user_id in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
    		
    	}else{
    		finalSql.append(" t.replenish_user_id=? ");
    	}
    	
    	templateParameterList.add(userid);
    	
    	finalSql.append(" and t.win_state in(0,6,7) and t.periods_num=? and t.type_code=b.type_code and b.play_type =? and b.play_sub_type = 'GROUP' ");
    	//finalSql.append(" and t.win_state in(0,6,7) and t.periods_num=? and t.type_code=b.type_code and b.play_type =? and b.play_sub_type = 'GROUP' ");
    	
    	templateParameterList.add(periodNum);
    	templateParameterList.add(lotteryType);
    	
    	finalSql.append(" and t.type_code like 'BJ%' " );
    	
    	finalSql.append(" group by t.type_code)c,tb_play_type d where c.finaltype=d.type_code  group by d.commission_type ");
    	
    	//*********************本级补出 END*******************
    	
    	//**********************加上下级补进的************START****************
    	if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){
    		finalSql.append(" UNION ALL "); 
    		
    		finalSql.append(" select d.commission_type as commissionType,sum(totalMoney) AS totalMoney from (");
    		finalSql.append("select c.type_code AS finaltype,");
    		if(Constant.TRUE_STATUS.equals(status)){
    			finalSql.append("sum(c.money*c." + rateUser + "/100)	     as totalMoney ");
    		}else{
    			finalSql.append("sum(c.money)	     as totalMoney ");
    		}
    		finalSql.append(" from tb_replenish c,tb_play_type b " +
    				" where c." + myColumn + " = ?" +
    				" and c.win_state in(0,6,7) " +
    				" and c.periods_num=? and c.type_code=b.type_code and b.play_type =? and b.play_sub_type = 'GROUP' ");
    	//" and c.periods_num=? and c.type_code=b.type_code and b.play_type =? and b.play_sub_type = 'GROUP' ");
    		
    		finalSql.append(" and c.type_code like 'BJ%' " );
    		
    		finalSql.append(" group by c.type_code)t,tb_play_type d where t.finaltype=d.type_code  group by d.commission_type ");
    		
    		templateParameterList.add(userid);
    		templateParameterList.add(periodNum);
    		templateParameterList.add(lotteryType);
    		//**********************查询下级补进的************END****************
    	}
    	//************处理补货*************END********
    	
    	finalSql.append(") group by commissionType order by commissionType asc ") ;
    	
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	
    	List<ZhanDangVO> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new rightBarItemMapperZ());
    	return reportList;
    	
    }    
    
    
    //******************总额统计******************END*************
    
    
    public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}

class DeliveryReportItemMapperZ implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

    	Zhangdan reportInfo = new Zhangdan();
    	reportInfo.setTypeCode(rs.getString("typeCode"));
    	reportInfo.setTurnover(rs.getLong("turnover"));
        reportInfo.setAmount(rs.getDouble("totalMoney"));
        
        return reportInfo;
    }
}

class ZhanDangItemMapperZ implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ZhanDangVO reportInfo = new ZhanDangVO();
		reportInfo.setTypeCode(rs.getString("typeCode"));
		reportInfo.setTurnover(rs.getInt("turnover"));
		reportInfo.setCommissionMoney(rs.getBigDecimal("commissionMoney"));
		reportInfo.setTotalMoney(rs.getBigDecimal("totalMoney"));
		
		return reportInfo;
	}
}

class rightBarItemMapperZ implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ZhanDangVO reportInfo = new ZhanDangVO();
		reportInfo.setCommissionType(rs.getString("commissionType"));
		reportInfo.setTotalMoney(rs.getBigDecimal("totalMoney"));
		
		return reportInfo;
	}
}
class DetailItemMapperClass implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ZhanDangLMDetailVO item = new ZhanDangLMDetailVO();
		item.setOrderNo(rs.getString("orderNo"));
		item.setBettingDate(rs.getTimestamp("bettingDate"));
		item.setPeriodsNum(rs.getString("periodsNum"));
		item.setAttribute(rs.getString("attribute"));
		item.setTypeCode(rs.getString("typeCode"));
		item.setUserName(rs.getString("userName"));
		item.setPlate(rs.getString("plate"));
		item.setMoney(rs.getBigDecimal("money"));
		item.setOdds(rs.getBigDecimal("odds"));
		item.setCommissionMoney(rs.getBigDecimal("commissionMoney"));
		item.setUserType(rs.getString("userType"));
		item.setCount(rs.getInt("count"));
		return item;
	}
}