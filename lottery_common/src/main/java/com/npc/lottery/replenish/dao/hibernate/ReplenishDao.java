package com.npc.lottery.replenish.dao.hibernate;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.google.common.collect.Lists;
import com.npc.lottery.common.Constant;
import com.npc.lottery.replenish.dao.interf.IReplenishDao;
import com.npc.lottery.replenish.vo.DetailVO;
import com.npc.lottery.replenish.vo.ReplenishHKVO;
import com.npc.lottery.replenish.vo.ReplenishVO;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.util.Page;

public class ReplenishDao implements IReplenishDao{
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * 这里取出的赔额是实占的
	 * 
	 */
	@Override
	public List<ReplenishVO> queryTotal_GD(String tableName, String userType,Long userID, String plate,String periodsNum,String rateUser,String commissionUser,String typeCode) {
		String sql="";
		Object[] parameters= null;		
		sql="select abc.playFinalType   as playFinalType," +
			       "sum(abc.oddsMoney)     as oddsMoney," +
			       "sum(abc.money)         as money," + 
			       "sum(abc.rateMoney)     as rateMoney," +
			       "sum(abc.commissionMoney)        as commissionMoney " +
			" from (" +				
				"select " +
					"TYPE_CODE                as playFinalType," +
					"sum(money*odds*" + rateUser + "/100)          as oddsMoney," +
					"sum(money)                                         as money, " +
					"sum(money*" + rateUser + "/100)               as rateMoney," +
				    "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +
				"from " +  tableName + " t " + 
				"where t." + userType + "=? " +
						  "and periods_num=? " +
						  "and TYPE_CODE like ? " +
						  "and win_state!=4 " +
						  "and win_state!=5 " +
			    "GROUP BY TYPE_CODE " +
			    "UNION ALL  " +
			    "select " +
					"TYPE_CODE                as playFinalType," +
					"sum(money*odds*" + rateUser + "/100)          as oddsMoney," +
					"sum(money)             					   as money, " +
					"sum(money*" + rateUser + "/100) 			   as rateMoney," +
				    "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)     as commissionMoney " +
				"from TB_GDKLSF_DOUBLE_SIDE t " + 
				"where t." + userType + "=? " +
						  "and periods_num=? " +
						  "and win_state!=4 " +
						  "and win_state!=5 " +
						  "and type_code != 'GDKLSF_DOUBLESIDE_ZHDA'" +
						  "and type_code != 'GDKLSF_DOUBLESIDE_ZHX'" +
						  "and type_code != 'GDKLSF_DOUBLESIDE_ZHDAN'" +
						  "and type_code != 'GDKLSF_DOUBLESIDE_ZHS'" +
						  "and type_code != 'GDKLSF_DOUBLESIDE_ZHWD'" +
						  "and type_code != 'GDKLSF_DOUBLESIDE_ZHWX'" +
						  "and type_code != 'GDKLSF_DOUBLESIDE_LONG'" +
						  "and type_code != 'GDKLSF_DOUBLESIDE_HU'" +
			    "GROUP BY TYPE_CODE " +
			") abc group by abc.playFinalType";
		parameters=new Object[]{userID,periodsNum,typeCode,userID,periodsNum};

		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper());
		return retList;
	}
	/*
	 * 这里取出的赔额是实占的
	 * 
	 */
	@Override
	public List<ReplenishVO> queryTotal_NC(String tableName, String userType,Long userID, String plate,String periodsNum,String rateUser,String commissionUser,String typeCode) {
		String sql="";
		Object[] parameters= null;		
		sql="select abc.playFinalType   as playFinalType," +
				"sum(abc.oddsMoney)     as oddsMoney," +
				"sum(abc.money)         as money," + 
				"sum(abc.rateMoney)     as rateMoney," +
				"sum(abc.commissionMoney)        as commissionMoney " +
				" from (" +				
				"select " +
				"TYPE_CODE                as playFinalType," +
				"sum(money*odds*" + rateUser + "/100)          as oddsMoney," +
				"sum(money)                                         as money, " +
				"sum(money*" + rateUser + "/100)               as rateMoney," +
				"sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +
				"from " +  tableName + " t " + 
				"where t." + userType + "=? " +
				"and periods_num=? " +
				"and TYPE_CODE like ? " +
				"and win_state!=4 " +
				"and win_state!=5 " +
				"GROUP BY TYPE_CODE " +
				/*"UNION ALL  " +
				"select " +
				"TYPE_CODE                as playFinalType," +
				"sum(money*odds*" + rateUser + "/100)          as oddsMoney," +
				"sum(money)             					   as money, " +
				"sum(money*" + rateUser + "/100) 			   as rateMoney," +
				"sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)     as commissionMoney " +
				"from TB_NC t " + 
				"where t." + userType + "=? " +
				"and periods_num=? " +
				"and win_state!=4 " +
				"and win_state!=5 " +
				"and type_code != 'NC_DOUBLESIDE_ZHDA'" +
				"and type_code != 'NC_DOUBLESIDE_ZHX'" +
				"and type_code != 'NC_DOUBLESIDE_ZHDAN'" +
				"and type_code != 'NC_DOUBLESIDE_ZHS'" +
				"and type_code != 'NC_DOUBLESIDE_ZHWD'" +
				"and type_code != 'NC_DOUBLESIDE_ZHWX'" +
				"and type_code != 'NC_DOUBLESIDE_LONG'" +
				"and type_code != 'NC_DOUBLESIDE_HU'" +
				"GROUP BY TYPE_CODE " +*/
				") abc group by abc.playFinalType";
		//parameters=new Object[]{userID,periodsNum,typeCode,userID,periodsNum};
		parameters=new Object[]{userID,periodsNum,typeCode};
		
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper());
		return retList;
	}
	@Override
	public List<ReplenishVO> queryTotal_BJ(String userType,Long userID, String plate,String periodsNum,String rateUser,String commissionUser) {
		String sql="";
		Object[] parameters= null;		
		sql="select  abc.playFinalType      	as playFinalType," +
					"abc.oddsMoney    			as oddsMoney," +
					"abc.money        			as money," + 
					"abc.rateMoney    			as rateMoney," +
					"abc.commissionMoney        as commissionMoney, " +
					"c.final_type_name          as playTypeName " +
				" from (" +				
				"select " +
					"TYPE_CODE               					   as playFinalType," +
					"sum(money*odds*" + rateUser + "/100)          as oddsMoney," +
					"sum(money)                                    as money, " +
					"sum(money*" + rateUser + "/100)               as rateMoney," +
					"sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +
				"from TB_BJSC t " + 
				"where t." + userType + "=? " +
					"and periods_num=? " +
					//"and TYPE_CODE like ? " +
					"and win_state!=4 " +
					"and win_state!=5 " +
				" GROUP BY TYPE_CODE ) abc,tb_play_type c " +
				" WHERE abc.playFinalType=c.type_code";
		parameters=new Object[]{userID,periodsNum};
		
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapperBJ());
		return retList;
	}
	
	@Override
	public List<ReplenishVO> queryTotal_K3(String userType,Long userID, String plate,String periodsNum,String rateUser,String commissionUser) {
		String sql="";
		Object[] parameters= null;		
		sql="select  abc.playFinalType      	as playFinalType," +
				"abc.oddsMoney    			as oddsMoney," +
				"abc.money        			as money," + 
				"abc.rateMoney    			as rateMoney," +
				"abc.commissionMoney        as commissionMoney, " +
				"c.final_type_name          as playTypeName " +
				" from (" +				
				"select " +
				"TYPE_CODE               					   as playFinalType," +
				"sum(money*odds*" + rateUser + "/100)          as oddsMoney," +
				"sum(money)                                    as money, " +
				"sum(money*" + rateUser + "/100)               as rateMoney," +
				"sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +
				"from TB_JSSB t " + 
				"where t." + userType + "=? " +
				"and periods_num=? " +
				//"and TYPE_CODE like ? " +
				"and win_state!=4 " +
				"and win_state!=5 " +
				" GROUP BY TYPE_CODE ) abc,tb_play_type c " +
				" WHERE abc.playFinalType=c.type_code";
		parameters=new Object[]{userID,periodsNum};
		
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapperBJ());
		return retList;
	}
	
	//用于投注时的数据校验
	@Override
	public List<ReplenishVO> queryTotal_GD_valiate(String tableName, String userType,Long userID, String plate,String periodsNum,String rateUser,String commissionUser,String typeCode) {
		String sql="";
		Object[] parameters= null;		
		sql="select abc.playFinalType   as playFinalType," +
			       "sum(abc.oddsMoney)     as oddsMoney," +
			       "sum(abc.money)         as money," + 
			       "sum(abc.rateMoney)     as rateMoney," +
			       "sum(abc.commissionMoney)        as commissionMoney " +
			" from (" +				
				"select " +
					"TYPE_CODE                as playFinalType," +
					"sum(money*odds*" + rateUser + "/100)          as oddsMoney," +
					"sum(money)                                         as money, " +
					"sum(money*" + rateUser + "/100)               as rateMoney," +
				    "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +
				"from " +  tableName + " t " + 
				"where t." + userType + "=? " +
						  "and periods_num=? " +
						  "and TYPE_CODE like ? " +
						  "and win_state!=4 " +
						  "and win_state!=5 " +
			    "GROUP BY TYPE_CODE " +
			    "UNION ALL  " +
			    "select " +
					"TYPE_CODE                as playFinalType," +
					"sum(money*odds*" + rateUser + "/100)          as oddsMoney," +
					"sum(money)             					   as money, " +
					"sum(money*" + rateUser + "/100) 			   as rateMoney," +
				    "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)     as commissionMoney " +
				"from TB_GDKLSF_DOUBLE_SIDE t " + 
				"where t." + userType + "=? " +
						  "and periods_num=? " +
						  "and TYPE_CODE like ? " +
						  "and win_state!=4 " +
						  "and win_state!=5 " +
			    "GROUP BY TYPE_CODE " +
			") abc group by abc.playFinalType";
		parameters=new Object[]{userID,periodsNum,typeCode,userID,periodsNum,typeCode};

		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper());
		return retList;
	}
	
	@Override
	public List<ReplenishVO> queryTotal(String tableName, String userType,Long userID, String plate,String periodsNum,String rateUser,String commissionUser,String typeCode) {
		String sql="";
		Object[] parameters= null;		
		sql="select " +
				"TYPE_CODE                as playFinalType," +
				"sum(money*odds*" + rateUser + "/100)          as oddsMoney," +
				"sum(money)               as money, " +
				"sum(money*" + rateUser + "/100) 			   as rateMoney," +
			    "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)     as commissionMoney " +
			"from " +  tableName + " t " + 
			"where t." + userType + "=? " +
					  "and periods_num=? " +
					  "and TYPE_CODE like ? " +
					  "and win_state!=4 " +
					  "and win_state!=5 " +
		    "GROUP BY TYPE_CODE";
		parameters=new Object[]{userID,periodsNum,typeCode};

		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper());
		return retList;
	}

	@Override
	public List<ReplenishVO> queryDoubleTotal(String userType,Long userID, String plate,String periodsNum,String rateUser,String commissionUser,String typeCode) {
		String sql="";
		Object[] parameters= null;
		String tableName = "TB_GDKLSF_DOUBLE_SIDE";
		
			sql="select " +
					"TYPE_CODE                as playFinalType," +
					"sum(money*odds*" + rateUser + "/100)          as oddsMoney," +
					"sum(money)               as money, " +
					"sum(money*" + rateUser + "/100) 			   as rateMoney," +
				    "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)     as commissionMoney " +
				"from " +  tableName + " t " + 
				"where " + userType + "=? " +
						  "and periods_num=? " +
						  "and TYPE_CODE like ? " +
						  "and win_state!=4 " +
						  "and win_state!=5 " +
			    "GROUP BY TYPE_CODE";
			parameters=new Object[]{userID,periodsNum,typeCode};
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper());
		return retList;
	}
	
	@Override
	public List<ReplenishVO> queryDoubleTotalForLh(String userType,Long userID, String plate,String periodsNum,String rateUser,String commissionUser) {
		String sql="";
		Object[] parameters= null;
		String tableName = "TB_GDKLSF_DOUBLE_SIDE";		
			sql="select " +
					"TYPE_CODE                as playFinalType," +
					"sum(money*" + rateUser + "/100*odds)          as oddsMoney," +
					"sum(money)               as money, " +
					"sum(money*" + rateUser + "/100) 			   as rateMoney," +
				    "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)     as commissionMoney " +
				"from " +  tableName + " t " + 
				"where " + userType + "=? " +
						  "and periods_num=? " +
						  "and win_state!=4 " +
						  "and win_state!=5 " +
						  "and( type_code = 'GDKLSF_DOUBLESIDE_ZHDA'" +
						  "or type_code = 'GDKLSF_DOUBLESIDE_ZHX'" +
						  "or type_code = 'GDKLSF_DOUBLESIDE_ZHDAN'" +
						  "or type_code = 'GDKLSF_DOUBLESIDE_ZHS'" +
						  "or type_code = 'GDKLSF_DOUBLESIDE_ZHWD'" +
						  "or type_code = 'GDKLSF_DOUBLESIDE_ZHWX'" +
						  "or type_code = 'GDKLSF_DOUBLESIDE_LONG'" +
						  "or type_code = 'GDKLSF_DOUBLESIDE_HU')" +
			    "GROUP BY TYPE_CODE";
			parameters=new Object[]{userID,periodsNum};
		
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper());
		return retList;
	}	
	
	@Override
	public List<ReplenishVO> queryDoubleTotalForLh_NC(String userType,Long userID, String plate,String periodsNum,String rateUser,String commissionUser) {
		String sql="";
		Object[] parameters= null;
		String tableName = "TB_NC";		
		sql="select " +
				"TYPE_CODE                as playFinalType," +
				"sum(money*" + rateUser + "/100*odds)          as oddsMoney," +
				"sum(money)               as money, " +
				"sum(money*" + rateUser + "/100) 			   as rateMoney," +
				"sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)     as commissionMoney " +
				"from " +  tableName + " t " + 
				"where " + userType + "=? " +
				"and periods_num=? " +
				"and win_state!=4 " +
				"and win_state!=5 " +
				"and( type_code = 'NC_DOUBLESIDE_ZHDA'" +
				"or type_code = 'NC_DOUBLESIDE_ZHX'" +
				"or type_code = 'NC_DOUBLESIDE_ZHDAN'" +
				"or type_code = 'NC_DOUBLESIDE_ZHS'" +
				"or type_code = 'NC_DOUBLESIDE_ZHWD'" +
				"or type_code = 'NC_DOUBLESIDE_ZHWX'" +
				"or type_code = 'NC_DOUBLESIDE_LONG'" +
				"or type_code = 'NC_DOUBLESIDE_HU')" +
				"GROUP BY TYPE_CODE";
		parameters=new Object[]{userID,periodsNum};
		
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper());
		return retList;
	}	
		
	/**
	 * 補出   update by Aaron 20120524
	 */
	@Override
	public List<ReplenishVO> queryReplenish(String userType,Long userID,String typeCode,String periodsNum,String plate,String rCommissionUser) {
		
		String sql = "select type_code as playFinalType," +
				" count(type_code) as totalCount," +
				" sum(money*odds)          as oddsMoney," +
				" sum(money)               as money," +
				" sum(money*(1-"+rCommissionUser+"/100)) tsMoney " +
	     " from tb_replenish " +
	     " where periods_num=? " +
	     " and " + userType + "=? " +
	     " and type_code like ? " +
	     " and win_state!=4 " +
		 " and win_state!=5 " +
		 " and win_state!=6 " +
		 " and win_state!=7 " +
	     " GROUP BY type_code";
		Object[] parameters=new Object[]{periodsNum,userID,typeCode};
//		System.out.println("queryReplenish : "+periodsNum+" "+plate+" "+userID+" "+typeCode);
//		System.out.println("queryReplenish : "+sql);
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapperForOut());
		return retList;
	}
	
	//此处统计的MONEY是实货，是补货人实际补出去的金额
	@Override
	public List<ReplenishVO> queryReplenishForOut(String userColumn,Long userID,String typeCode,String periodsNum,String plate,String rCommissionUser,String userType) {
		
		StringBuffer finalSql=new StringBuffer();
		finalSql.append("select type_code as playFinalType," +
				"sum(money*odds)          as oddsMoney," +
				"sum(money)               as money, " +
				"sum(money - money*" +  rCommissionUser + "/100)  as commissionMoney " +
				" from tb_replenish " +
				" where periods_num=? ");
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
					finalSql.append(" and " + userColumn + " in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
					
				}else{
					finalSql.append(" and " + userColumn + "=? ");
				}
				finalSql.append(" and type_code like ? "+
								" and win_state!=4 " +
								" and win_state!=5 " +
								" and win_state!=6 " +
								" and win_state!=7 " +
								"GROUP BY TYPE_CODE");
		Object[] parameters=new Object[]{periodsNum,userID,typeCode};
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(finalSql.toString(),parameters, new TotalMapperForR());
		return retList;
	}
	
	@Override
	public List<ReplenishVO> queryReplenishForIn(String userType,Long userID,String typeCode,String periodsNum,String plate,String rateUser,String rCommissionUser) {
		
		String sql = "select type_code as playFinalType," +
				"sum(money*odds*" + rateUser + "/100)          as oddsMoney," +
				"sum(money*" + rateUser + "/100)               as money, " +
				"sum(money*" + rateUser + "/100)                 as rateMoney," +
				"sum(money*" + rateUser + "/100 - money*" + rateUser + "/100 *" + rCommissionUser + "/100)  	as commissionMoney " +
	     " from tb_replenish " +
	     " where periods_num=? " +
	     " and " + userType + "=? " +
	     " and type_code like ? " +
	     " and win_state!=4 " +
		 " and win_state!=5 " +
		 " and win_state!=6 " +
		 " and win_state!=7 " +
	     "GROUP BY TYPE_CODE";
		Object[] parameters=new Object[]{periodsNum,userID,typeCode};
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper());
		return retList;
	}
	
	@Override
	public List<ReplenishVO> queryReplenishInForBetCheck(String userColumn,Long userID,String typeCode,String periodsNum,String rateUser) {
		
		String sql = "select sum(money*" + rateUser + "/100)  as money " +
				" from tb_replenish " +
				" where periods_num=? " +
				" and " + userColumn + "=? " +
				" and type_code like ? " +
				" and win_state not in (4,5,6,7) " +
				" GROUP BY TYPE_CODE";
		Object[] parameters=new Object[]{periodsNum,userID,typeCode};
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapperForBetCheck());
		return retList;
	}
	
	@Override
	public List<ReplenishVO> queryReplenishInForBetCheckByScheme(String userColumn,Long userID,String typeCode,String periodsNum,String rateUser,String scheme) {
		if(StringUtils.isNotBlank(scheme)){
			scheme=scheme+".";
		}
		
		String sql = "select sum(money*" + rateUser + "/100)  as money " +
				" from "+scheme+"tb_replenish " +
				" where periods_num=? " +
				" and " + userColumn + "=? " +
				" and type_code like ? " +
				" and win_state not in (4,5,6,7) " +
				" GROUP BY TYPE_CODE";
		Object[] parameters=new Object[]{periodsNum,userID,typeCode};
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapperForBetCheck());
		return retList;
	}
	/**
	 * 補入 - 
	 * 補入要*占成，補出不用 update by Aaron 20120524
	 */
	@Override
	public List<ReplenishVO> queryReplenishForAcc(String userType,Long userID,String typeCode,String periodsNum,String plate,String rateUser,String rCommissionUser) {
		
		String sql = "select type_code as playFinalType," +
				" count(type_code) as totalCount," +
				" sum(money*odds*("+rateUser+"/100))          as oddsMoney," +
				" sum(money*("+rateUser+"/100))               as money," +
				" sum(money*(1-"+rCommissionUser+"/100)*("+rateUser+"/100)) tsMoney " +
	     " from tb_replenish " +
	     " where periods_num=? " +
	     " and " + userType + "=? " +
	     " and type_code like ? " +
	     " and win_state!=4 " +
		 " and win_state!=5 " +
		 " and win_state!=6 " +
		 " and win_state!=7 " +
	     "GROUP BY type_code";
		
		Object[] parameters=new Object[]{periodsNum,userID,typeCode};

//		System.out.println("queryReplenishForAcc : "+periodsNum+" "+plate+" "+userID+" "+typeCode);
//		System.out.println("queryReplenishForAcc : "+sql);
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapperForOut());
		return retList;
	}
	/**GROUP typeCode
	 * 补出的  不用 * 占成
	 */
	@Override
	public List<ReplenishVO> queryReplenish_LM_Main(String userColumn,Long userID,String typeCode,String periodsNum,String plate,String rCommissionUser,String userType) {	
		StringBuffer finalSql=new StringBuffer();
		finalSql.append("select "  +
				"sum(money*odds)          as oddsMoney," +
				"sum(money)               as money, " +
				"sum(money - money*" +  rCommissionUser + "/100)     as commissionMoney, " +
				"a.type_code                                    as playFinalType " +
				"from tb_replenish a " +
				"where " +
				"a.periods_num=? ");
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
					finalSql.append(" and a." + userColumn + " in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
					
				}else{
					finalSql.append(" and a." + userColumn + "=? ");
				}
				finalSql.append(
				"and win_state!=4 " +
				"and win_state!=5 " +
				" and win_state!=6 " +
				" and win_state!=7 " +
				"group by a.type_code");
		Object[] parameters=new Object[]{periodsNum,userID};
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(finalSql.toString(),parameters, new TotalMapper_R_LM_Main());
		return retList;
	}
	
	/**
	 * 补出的  不用 * 占成
	 */
	@Override
	public List<ReplenishVO> queryReplenish_LM(String userType,Long userID,String typeCode,String periodsNum,String plate,String rCommissionUser) {		
		String sql= "select "  +
				"sum(money*odds)          as oddsMoney," +
				"sum(money)               as money, " +
			    "sum(money - money*" +  rCommissionUser + "/100)     as commissionMoney, " +
				"a.type_code                                    as playFinalType, " +
				"a.attribute                                    as attribute " +
	   "from tb_replenish a " +
	   "where a.type_code like ? " +
			  "and a." + userType + "=? " +
			  "and a.periods_num=? " +
			  "and win_state!=4 " +
			  "and win_state!=5 " +
			  " and win_state!=6 " +
			  " and win_state!=7 " +
	   "group by a.type_code,a.attribute";
		Object[] parameters=new Object[]{typeCode,userID,periodsNum};
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper_R_LM());
		return retList;
	}
	/**
	 * 补入的 要 * 占成
	 */
	@Override
	public List<ReplenishVO> queryReplenish_LM_Acc_Main(String userType,Long userID,String typeCode,String periodsNum,String plate,String rateUser,String rCommissionUser) {		
		String sql= "select "  +
				"a.type_code                                    as playFinalType, " +
				"sum(money*odds*" + rateUser + "/100)          as oddsMoney," +
				"sum(money*" + rateUser + "/100)               as rateMoney, " +
				"sum(money*" + rateUser + "/100 - money*" + rateUser + "/100 *" + rCommissionUser + "/100)  	as commissionMoney " +		
				"from tb_replenish a " +
				"where " +
				"a." + userType + "=? " +
				"and a.periods_num=? " +
				"and win_state!=4 " +
				"and win_state!=5 " +
				" and win_state!=6 " +
				" and win_state!=7 " +
				"group by a.type_code";
		Object[] parameters=new Object[]{userID,periodsNum};
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper_R_LM_In_Main());
		return retList;
	}
	
	/**
	 * 补入的 要 * 占成
	 */
	@Override
	public List<ReplenishVO> queryReplenish_LM_Acc(String userType,Long userID,String typeCode,String periodsNum,String plate,String rateUser,String rCommissionUser) {		
		String sql= "select "  +
				"a.type_code                                    as playFinalType, " +
				"a.attribute                                    as attribute, " +
				"sum(money*odds*" + rateUser + "/100)          as oddsMoney," +
				"sum(money*" + rateUser + "/100)               as rateMoney, " +
				"sum(money*" + rateUser + "/100 - money*" + rateUser + "/100 *" + rCommissionUser + "/100)  	as commissionMoney " +		
	   "from tb_replenish a " +
	   "where a.type_code like ? " +
			  "and a." + userType + "=? " +
			  "and a.periods_num=? " +
			  "and win_state!=4 " +
			  "and win_state!=5 " +
			  " and win_state!=6 " +
				 " and win_state!=7 " +
	   "group by a.type_code,a.attribute";
		Object[] parameters=new Object[]{typeCode,userID,periodsNum};
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper_R_LM_In());
		return retList;
	}
	
	
	//用于连码数据校验start
	@Override
	public List<ReplenishVO> queryReplenish_LM_valiate(String userColumn,Long userID,String typeCode,
			String periodsNum,String plate,String rCommissionUser,String attribute,String userType) {	
		StringBuffer finalSql=new StringBuffer();
		finalSql.append("select "  +
				"sum(money*odds)          as oddsMoney," +
				"sum(money)               as money, " +
			    "sum(money - money*" +  rCommissionUser + "/100)     as commissionMoney, " +
				"a.type_code                                    as playFinalType, " +
				"a.attribute                                    as attribute " +
	   "from tb_replenish a " +
	   "where a.type_code like ? ");
			  if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
					finalSql.append(" and " + userColumn + " in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
					
				}else{
					finalSql.append(" and " + userColumn + "=? ");
				}
			  finalSql.append("and a." + userColumn + "=? " +
			  "and a.periods_num=? " +
			  "and a.attribute=? " +
			  "and win_state!=4 " +
			  "and win_state!=5 " +
			  " and win_state!=6 " +
				 " and win_state!=7 " +
	   "group by a.type_code,a.attribute");
		Object[] parameters=new Object[]{typeCode,userID,periodsNum,attribute};
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(finalSql.toString(),parameters, new TotalMapper_R_LM());
		return retList;
	}
	
	@Override
	public List<ReplenishVO> queryReplenish_LM_Acc_valiate(String userType,Long userID,String typeCode,
			String periodsNum,String plate,String rateUser,String rCommissionUser,String attribute) {		
		String sql= "select "  +
				"a.type_code                                    as playFinalType, " +
				"a.attribute                                    as attribute, " +
				"sum(money*odds*" + rateUser + "/100)          as oddsMoney," +
				"sum(money*" + rateUser + "/100)               as rateMoney, " +
				"sum(money*" + rateUser + "/100 - money*" + rateUser + "/100 *" + rCommissionUser + "/100)  	as commissionMoney " +		
	   "from tb_replenish a " +
	   "where a.type_code like ? " +
			  "and a." + userType + "=? " +
			  "and a.periods_num=? " +
			  "and a.attribute=? " +
			  "and win_state!=4 " +
			  "and win_state!=5 " +
			  " and win_state!=6 " +
				 " and win_state!=7 " +
	   "group by a.type_code,a.attribute";
		Object[] parameters=new Object[]{typeCode,userID,periodsNum,attribute};
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper_R_LM_In());
		return retList;
	}	
	
	//用于连码数据校验end
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReplenishHKVO> queryDeliveryDetail(String tableName,String periodsNum,String userType,Long userID,String rateUser,String commissionUser,String state) {
		Object[] parameters=null;
		String sql="";
		String rate ="";
		if(state.equals(Constant.TRUE_STATUS)) // 如果是實戰
		{
			rate = rateUser;
		}else{
			rate = "100";
		}
		sql="select type_code as playTypeCode" +
				",sum(money*("+rate+"/100)) as totalMoney" +
				",sum(money*("+rateUser+"/100)) as totalMoneyYK" +
				",count(type_code) as totalCount " +
				",sum(money*"+commissionUser+"/100 * ("+rateUser+"/100)) tsMoney " +
				",sum(money*odds * ("+rateUser+"/100)) oddsMoney from  "+
				tableName+"  where periods_num = ? and "+userType+" = ? " +
				"and win_state!=4 " +
				"and win_state!=5 " +
				"group by type_code ";
		parameters=new Object[]{periodsNum,userID};
		
		List<ReplenishHKVO> retList = jdbcTemplate.query(sql, parameters,new ReplenishHKVOMapper());
		return retList;
	}
	
	/**
	 * 用於生肖連 尾數連
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReplenishHKVO> queryDeliveryDetailByAttr(String tableName,String periodsNum,String userType,Long userID,String rateUser,String commissionUser,String state) {
		String sql="";
		Object[] parameters=null;
		String rate ="";
		if(state.equals(Constant.TRUE_STATUS)) // 如果是實戰
		{
			rate = rateUser;
		}else{
			rate = "100";
		}
			sql="select split_attribute splitAttribute" +
					",type_code as playTypeCode" +
					",sum(money*("+rate+"/100)) as totalMoneyYK" +
					",count(type_code) as totalCount"+
					" from  "+tableName+" " +
							" where periods_num = ? and "+userType+" = ? " +
							"and win_state!=4 " +
							"and win_state!=5 " +
									"group by type_code ,split_attribute";
			parameters=new Object[]{periodsNum,userID};
		List<ReplenishHKVO> retList = jdbcTemplate.query(sql, parameters,new ReplenishHKVOSXLMapper());
		return retList;
	}
	
	@Override
	public List<ReplenishHKVO> queryDeliveryLXDetail(String periodsNum,String userType,Long userID,String rateUser,String commissionUser,String state) {
		String sql="";
		Object[] parameters=null;
		String rate ="";
		if(state.equals(Constant.TRUE_STATUS)) // 如果是實戰
		{
			rate = rateUser;
		}else{
			rate = "100";
		}
		
		sql="select type_code as playTypeCode, attribute as attributes" +
				",sum(money*("+rate+"/100)) as totalMoney" +
				",sum(money*("+rateUser+"/100)) as totalMoneyYK" +
				",count(attribute) as totalCount"+
				",sum(" + rateUser + ")/count(type_code)/100 as rate" +
				",sum(money*"+commissionUser+"/100 *("+rateUser+"/100)) tsMoney" +
				",sum(money*odds *("+rateUser+"/100)) oddsMoney" +
				" from  tb_hklhc_lx t where periods_num = ? and "+userType+" = ?  " +
				"and win_state!=4 " +
			    "and win_state!=5 " +
						"group by attribute,type_code";
		parameters=new Object[]{periodsNum,userID};
		
		List<ReplenishHKVO> retList = jdbcTemplate.query(sql, parameters,new ReplenishHKVOLXMapper());
		return retList;
	}
	
	@Override
	public List<ReplenishVO> queryTotal_LM_Main(String tableName, String userType,Long userID, String plate,String periodsNum,String rateUser,String commissionUser,String typeCode) {
		String sql = null;
		Object[] parameters= null;
			sql="select " +
					"t.type_code as playFinalType," +
					"sum(t.money*" + rateUser + "/100 * t.odds) as loseMoney," +
					"sum(t.money) as money," + 
					"sum(money*" + rateUser + "/100)               as rateMoney," +
					"sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +      
					"from " +  tableName + " t " +
					"where t." + userType + "=? " +
					// "and t.plate = ? " +
					"and t.periods_num = ? " +
					"and t.win_state!=4 " +
					"and t.win_state!=5 " +
					"GROUP BY t.type_code" ;
			parameters=new Object[]{userID,periodsNum};
		
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper_LM_Main());
		return retList;
		
	}
	
	@Override
	public List<ReplenishVO> queryTotal_LM(String tableName, String userType,Long userID, String plate,String periodsNum,String rateUser,String commissionUser,String typeCode) {
		String sql = null;
		Object[] parameters= null;
		if(typeCode.equals("HK_GG")){			
				sql="select " +
				        "t.type_code as playFinalType," +
				        "t.attribute as attribute," +
				        "sum(t.money*" + rateUser + "/100 * t.odds) as loseMoney," +
				        "sum(t.money) as money," + 
				        "sum(money*" + rateUser + "/100)               as rateMoney," +
				        "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +       
				   "from " +  tableName + " t " +
				   "where t." + userType + "=? " +
					   "and t.periods_num = ? " +
					   "and t.type_code = ? " +
					   "and t.win_state!=4 " +
					   "and t.win_state!=5 " +
					   "GROUP BY t.type_code,t.attribute" ;
				parameters=new Object[]{userID,periodsNum,typeCode};			
		}else{			
				sql="select " +
				        "t.type_code as playFinalType," +
				        "t.split_attribute as attribute," +
				        "sum(t.money*" + rateUser + "/100 * t.odds) as loseMoney," +
				        "sum(t.money) as money," + 
				        "sum(money*" + rateUser + "/100)               as rateMoney," +
				       // "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +      
				        "sum(money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +      
				   "from " +  tableName + " t " +
				   "where t." + userType + "=? " +
					   "and t.periods_num = ? " +
					   "and t.type_code = ? " +
					   "and t.win_state!=4 " +
					   "and t.win_state!=5 " +
					   "GROUP BY t.type_code,t.split_attribute" ;
				parameters=new Object[]{userID,periodsNum,typeCode};
		}
		
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper_LM());
		return retList;
			
	}
		
		@Override
		public List<ReplenishVO> queryTotal_LM_valite(String tableName, String userType,Long userID, String plate,
				String periodsNum,String rateUser,String commissionUser,String typeCode,String attribute) {
			String sql = null;
			Object[] parameters= null;
			if(typeCode.equals("HK_GG")){			
					sql="select " +
					        "t.type_code as playFinalType," +
					        "t.attribute as attribute," +
					        "sum(t.money*" + rateUser + "/100 * t.odds) as loseMoney," +
					        "sum(t.money) as money," + 
					        "sum(money*" + rateUser + "/100)               as rateMoney," +
					        "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +       
					   "from " +  tableName + " t " +
					   "where t." + userType + "=? " +
						   "and t.periods_num = ? " +
						   "and t.type_code = ? " +
						   "and t.attribute = ? " +
						   "and t.win_state!=4 " +
						   "and t.win_state!=5 " +
						   "GROUP BY t.type_code,t.attribute" ;
					parameters=new Object[]{userID,periodsNum,typeCode,attribute};			
			}else{			
					sql="select " +
					        "t.type_code as playFinalType," +
					        "t.split_attribute as attribute," +
					        "sum(t.money*" + rateUser + "/100 * t.odds) as loseMoney," +
					        "sum(t.money) as money," + 
					        "sum(money*" + rateUser + "/100)               as rateMoney," +
					        "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +      
					   "from " +  tableName + " t " +
					   "where t." + userType + "=? " +
						   "and t.periods_num = ? " +
						   "and t.type_code = ? " +
						   "and t.split_attribute = ? " +
						   "and t.win_state!=4 " +
						   "and t.win_state!=5 " +
						   "GROUP BY t.type_code,t.split_attribute" ;
					parameters=new Object[]{userID,periodsNum,typeCode,attribute};
				
			}
		
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper_LM());
		return retList;
	}
		
	@Override
	public List<ReplenishVO> queryTotal_BJ_valiate(String userType, Long userID,String plate, String periodsNum, String rateUser, String commissionUser,String typeCode) {
		String sql="";
		Object[] parameters= null;		
		sql="select " +
				"TYPE_CODE                					as playFinalType," +
				"sum(money*odds*" + rateUser + "/100)       as oddsMoney," +
				"sum(money)               					as money, " +
			    "sum(money*" + rateUser + "/100)            as rateMoney," +
			    "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +
			"from TB_BJSC t " + 
			"where " + userType + "=? " +
					  "and periods_num=? " +
					  "and type_code like ? " +
					  "and win_state!=4 " +
					  "and win_state!=5 " +
		    "GROUP BY TYPE_CODE ";
		parameters=new Object[]{userID,periodsNum,typeCode};		
	
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper());
		return retList;
	}
	@Override
	public List<ReplenishVO> queryTotal_K3_valiate(String userType, Long userID,String plate, String periodsNum, String rateUser, String commissionUser,String typeCode) {
		String sql="";
		Object[] parameters= null;		
		sql="select " +
				"TYPE_CODE                					as playFinalType," +
				"sum(money*odds*" + rateUser + "/100)       as oddsMoney," +
				"sum(money)               					as money, " +
				"sum(money*" + rateUser + "/100)            as rateMoney," +
				"sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +
				"from TB_JSSB t " + 
				"where " + userType + "=? " +
				"and periods_num=? " +
				"and type_code like ? " +
				"and win_state!=4 " +
				"and win_state!=5 " +
				"GROUP BY TYPE_CODE ";
		parameters=new Object[]{userID,periodsNum,typeCode};		
		
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper());
		return retList;
	}
	
	@Override
	public List<ReplenishVO> queryTotal_CQ(String userType, Long userID,String plate, String periodsNum, String rateUser, String commissionUser,String typeCode) {
		String sql="";
		Object[] parameters= null;		
			
			sql="select abc.playFinalType      as playFinalType," +
				       "sum(abc.oddsMoney)     as oddsMoney," +
				       "sum(abc.money)         as money," + 
				       "sum(abc.rateMoney)                 as rateMoney," +
				       "sum(abc.commissionMoney)           as commissionMoney " +
				    " from (" +
				"select " +
						"TYPE_CODE                					as playFinalType," +
						"sum(money*odds*" + rateUser + "/100)       as oddsMoney," +
						"sum(money)               					as money, " +
					    "sum(money*" + rateUser + "/100)            as rateMoney," +
					    "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +
					"from TB_CQSSC_BALL_FIRST t " + 
					"where " + userType + "=? " +
							  "and periods_num=? " +
							  "and type_code like ? " +
							  "and win_state!=4 " +
							  "and win_state!=5 " +
				    "GROUP BY TYPE_CODE " +
				    "UNION ALL " +
					"select " +
						"TYPE_CODE                as playFinalType," +
						"sum(money*odds*" + rateUser + "/100)       as oddsMoney," +
						"sum(money)               					as money, " +
					    "sum(money*" + rateUser + "/100)            as rateMoney," +
					    "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +
					"from TB_CQSSC_BALL_SECOND t " + 
					"where " + userType + "=? " +
							  "and periods_num=? " +
							  "and type_code like ? " +
							  "and win_state!=4 " +
							  "and win_state!=5 " +
				    "GROUP BY TYPE_CODE " +
				    "UNION ALL  " +
					"select " +
						"TYPE_CODE                as playFinalType," +
						"sum(money*odds*" + rateUser + "/100)       as oddsMoney," +
						"sum(money)               					as money, " +
					    "sum(money*" + rateUser + "/100)            as rateMoney," +
					    "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +
					"from TB_CQSSC_BALL_THIRD t " + 
					"where " + userType + "=? " +
							  "and periods_num=? " +
							  "and type_code like ? " +
							  "and win_state!=4 " +
							  "and win_state!=5 " +
				    "GROUP BY TYPE_CODE " +
				    "UNION ALL  " +
					"select " +
						"TYPE_CODE                as playFinalType," +
						"sum(money*odds*" + rateUser + "/100)       as oddsMoney," +
						"sum(money)               					as money, " +
					    "sum(money*" + rateUser + "/100)            as rateMoney," +
					    "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +
					"from TB_CQSSC_BALL_FORTH t " + 
					"where " + userType + "=? " +
							  "and periods_num=? " +
							  "and type_code like ? " +
							  "and win_state!=4 " +
							  "and win_state!=5 " +
				    "GROUP BY TYPE_CODE " +
				    "UNION ALL  " +
					"select " +
						"TYPE_CODE                as playFinalType," +
						"sum(money*odds*" + rateUser + "/100)       as oddsMoney," +
						"sum(money)               					as money, " +
					    "sum(money*" + rateUser + "/100)            as rateMoney," +
					    "sum(money*" + rateUser + "/100-money*" + rateUser + "/100*" + commissionUser + "/100)   as commissionMoney " +
					"from TB_CQSSC_BALL_FIFTH t " + 
					"where " + userType + "=? " +
							  "and periods_num=? " +
							  "and type_code like ? " +
							  "and win_state!=4 " +
							  "and win_state!=5 " +
					"GROUP BY TYPE_CODE ) abc group by abc.playFinalType";
			parameters=new Object[]{userID,periodsNum,typeCode,userID,periodsNum,typeCode,userID,periodsNum,typeCode
					,userID,periodsNum,typeCode,userID,periodsNum,typeCode};		
		
		@SuppressWarnings("unchecked")
		List<ReplenishVO> retList = jdbcTemplate.query(sql,parameters, new TotalMapper());
		return retList;
	}
	
	@Override
	public Page queryBetDetail(Page page, Long userId,String typeCode,String periodsNum,String rateUser,String userType,String subType) {
		
		List<DetailVO> retList = new ArrayList<DetailVO>();
		
		String[] list = new String[]{};
		if(subType.equals(Constant.LOTTERY_TYPE_GDKLSF)){
			list = Constant.GDKLSF_TABLE_LIST;
		}else if(subType.equals(Constant.LOTTERY_TYPE_CQSSC)){
			list = Constant.CQSSC_TABLE_LIST;
		}else if(subType.equals(Constant.LOTTERY_TYPE_BJ)){
			list = Constant.BJSC_TABLE_LIST;
		}else if(subType.equals(Constant.LOTTERY_TYPE_K3)){
			list = Constant.K3_TABLE_LIST;
		}else if(subType.equals(Constant.LOTTERY_TYPE_NC)){
			list = Constant.NC_TABLE_LIST;
		}else if(subType.equals(Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH)){
			list = Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME_LIST;
		}else if(subType.equals(Constant.LOTTERY_NC_SUBTYPE_STRAIGHTTHROUGH)){
			list = Constant.NC_TABLE_LIST;
		}
		for (int i = 0; i < list.length; i++) {
			StringBuffer finalSql=new StringBuffer();
			StringBuffer sqTemplatelBuffer=new StringBuffer();	
			String tableName = list[i];
			String attribute="null";
			if(tableName.equals(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME) || subType.equals(Constant.LOTTERY_NC_SUBTYPE_STRAIGHTTHROUGH)){
				attribute="max(a.attribute)";
			}
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
								"max(a.RATE_AGENT)   			 		 as agentRate," +
								"sum(a." + rateUser + "/100 * a.money) 		 as rateMoney," +
								attribute+"		 					 as attribute ," +
								"max(b.PARENT_STAFF_TYPE_QRY)               as parentUserType ," +
								"count(order_no) as count "
					+ " from "
					+ tableName + " a ,tb_frame_member_staff b"				
					+ " where  a.periods_num = ? ");
			    if(typeCode!=null){
					sqTemplatelBuffer.append(" and a.type_code = ? ");
				}
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){sqTemplatelBuffer.append(" and a.chiefstaff=? ");}
		    	else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType)){sqTemplatelBuffer.append(" and a.branchstaff=? ");}
		    	else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType)){sqTemplatelBuffer.append(" and a.stockholderstaff=? ");}
		    	else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.genagenstaff=? ");}
		    	else if(ManagerStaff.USER_TYPE_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.agentstaff=? ");}
				sqTemplatelBuffer.append(" and a.betting_user_id= b.id ");
				//尾部
				sqTemplatelBuffer.append(" group by a.order_no,type_code,a.periods_num " );
				
			Object[] parameters = null;
			if(typeCode==null){
				parameters = new Object[] { periodsNum,userId };
			}else{
				parameters = new Object[] { periodsNum,typeCode,userId };
			}
			//finalSql.append("select * from (");
			finalSql.append(sqTemplatelBuffer);
			//finalSql.append(") where rankNum=1");
			List betlist = jdbcTemplate.query(finalSql.toString(), parameters,new GDBetDetailItemMapper());
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
	public Page queryReplenishInDetail(Page page, Long userId,String typeCode,String periodsNum,String rateUser,String userType,String commissionType,
			Date startDate,Date endDate,String winState,String opType,String rateColumn,String commissionColumn,String tableName) {
		
		List<DetailVO> retList = new ArrayList<DetailVO>();
		
			StringBuffer finalSql=new StringBuffer();
			StringBuffer sqTemplatelBuffer=new StringBuffer();	
			
			List<Object> finalParameterList=new ArrayList<Object>();
	    	List<Object> templateParameterList=new ArrayList<Object>();
	    	//处理输、赢、和、问题
	    	String handleWinAmout = "(case when win_state=1 then win_amount when win_state=2 then -money " +
	    			"when win_state=9 or win_state=4 or win_state=5 then 0 else 0 end )";
	    	
			sqTemplatelBuffer.append( " select " +
					"a.order_no          as orderNo," +
					"a.betting_date      as bettingDate," +
					"a.periods_num 	     as periodsNum," +
					"a.type_code         as typeCode," +
					"b.account 		     as userName," +
					"a.plate             as plate," +
					"a.odds   		 	 as odds," +
					"a.money   			 as money," +
					handleWinAmout+"		 				 as winAmount," +
					handleWinAmout+" * " + rateColumn + "/100		 	 as rateWinAmount," +
					"(case when win_state=9 then 0 else (a.money*" + commissionColumn + "/100*" + rateColumn + "/100) end )		as rateBackWater,");
					
			sqTemplatelBuffer.append( "100-a.COMMISSION_STOCKHOLDER   				 as branchCommission," +
					"100-a.COMMISSION_GEN_AGENT   		 as stockCommission," +
					"100-a.COMMISSION_AGENT   		 	 as genAgentCommission," +
					"100-a.COMMISSION_MEMBER   				 as agentCommission," );
			sqTemplatelBuffer.append( "100-a.COMMISSION_BRANCH   as chiefCommission," +
					"a.RATE_CHIEF   					 as chiefRate," +
					"a.RATE_BRANCH   					 as branchRate," +
					"a.RATE_STOCKHOLDER   				 as stockRate," +
					"a.RATE_GEN_AGENT   				 as genAgentRate," +
					"a.RATE_AGENT   			 		 as agentRate," +
					"a." + rateUser + "/100 * a.money 		 as rateMoney," +
					"a.attribute		 					 as attribute," +
					"b.user_type		 					 as userType," +
					"1 as  count," +
					"a.win_state as winState "
					+ " from "
					+ tableName + " a ,tb_frame_manager_staff b"				
					+ " where 1=1 ");
			
			if(periodsNum!=null && periodsNum!=""){
				sqTemplatelBuffer.append(" and a.periods_num = ? ");
				templateParameterList.add(periodsNum);  
			}
			if(typeCode!=null && typeCode!=""){
				sqTemplatelBuffer.append(" and a.type_code like ? ");
				templateParameterList.add(typeCode);  
			}
			if(commissionType!=null && commissionType!=""){
				sqTemplatelBuffer.append(" and a.commission_type = ? ");
				templateParameterList.add(commissionType);  
			}
			if(startDate!=null && endDate!=null){ 
				sqTemplatelBuffer.append(" and betting_date between to_date('"+startDate+"','yyyy-MM-dd')+to_dsinterval('0 2:00:00')  and to_date('"+endDate+"','yyyy-MM-dd')+1+to_dsinterval('0 2:00:00') " );
			}
			if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){sqTemplatelBuffer.append(" and a.chiefstaff=? ");}
			else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType)){sqTemplatelBuffer.append(" and a.branchstaff=?  ");}
			else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType)){sqTemplatelBuffer.append(" and a.stockholderstaff=? ");}
			else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.genagenstaff=? ");}
			else if(ManagerStaff.USER_TYPE_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.agentstaff=? ");}
			templateParameterList.add(userId);  
			
			if("cleared".equals(opType)){
				sqTemplatelBuffer.append(" and a.replenish_user_id= b.id and a.win_state!=? ");
			}else{
				sqTemplatelBuffer.append(" and a.replenish_user_id= b.id and a.win_state=? ");
			}
			templateParameterList.add(winState);  
			
			//finalSql.append("select * from (");
			finalSql.append(sqTemplatelBuffer);
			//finalSql.append(") where rankNum=1");
			finalParameterList.addAll(Lists.newArrayList(templateParameterList));
			List betlist = jdbcTemplate.query(finalSql.toString(), finalParameterList.toArray(),new GDReplenishDetailItemMapper());
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
	
	@Override
	public Page queryReplenishOutDetail(Page page, Long userId,String typeCode,String periodsNum,String rateUser,String userType) {
		
		List<DetailVO> retList = new ArrayList<DetailVO>();
		
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
				/*"100-a.COMMISSION_CHIEF   				 as chiefOutCommission," +
				"100-a.COMMISSION_BRANCH   				 as chiefCommission," +
				"100-a.COMMISSION_STOCKHOLDER   		 as branchCommission," +
				"100-a.COMMISSION_GEN_AGENT   		 	 as stockCommission," +
				"100-a.COMMISSION_AGENT   				 as genAgentCommission," +
				"100-a.COMMISSION_MEMBER   				 as agentCommission," +*/
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
				"-(a." + rateUser + "/100 * a.money) 		 as rateMoney," +
				"a.attribute		 					 as attribute ," +
				"b.user_type		 					 as userType," +
				"rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
				+ " from "
				+ tableName + " a ,tb_frame_manager_staff b ");
				if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
					sqTemplatelBuffer.append(" where  a.periods_num = ? and a.replenish_user_id=b.id and a.replenish_user_id " +
							"in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?)  "); 
				}else{
					sqTemplatelBuffer.append(" where  a.periods_num = ? and a.replenish_user_id=b.id and a.replenish_user_id=? "); 
				}
		if(typeCode!=null){
			sqTemplatelBuffer.append(" and a.type_code like ? ");
		}
		//" in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) "
		Object[] parameters = null;
		if(typeCode==null){
			parameters = new Object[] { periodsNum,userId};
		}else{
			parameters = new Object[] { periodsNum,userId,typeCode};
		}
		finalSql.append("select * from (");
		finalSql.append(sqTemplatelBuffer);
		finalSql.append(") where rankNum=1");
		List betlist = jdbcTemplate.query(finalSql.toString(), parameters,new GDReplenishDetailItemMapperR());
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
	
	@Override
	public Page queryBetDetail_RealTime(Page page, Long userId,String typeCode,String periodsNum,String rateUser,
			String userType,String subType,Date prevSearchTime,Integer money) {
		
		List<DetailVO> retList = new ArrayList<DetailVO>();
		
		String[] list = new String[]{};
		if(subType.equals(Constant.LOTTERY_TYPE_GDKLSF)){
			list = Constant.GDKLSF_TABLE_LIST;
			//list = new String[]{Constant.GDKLSF_BALL_FIRST_TABLE_NAME};
		}else if(subType.equals(Constant.LOTTERY_TYPE_NC)){
			list = new String[]{Constant.NC_TABLE_NAME};
		}else if(subType.equals(Constant.LOTTERY_TYPE_CQSSC)){
			list = Constant.CQSSC_TABLE_LIST;
			//list = new String[]{Constant.CQSSC_BALL_FIRST_TABLE_NAME};
			
		}else if(subType.equals(Constant.LOTTERY_TYPE_BJ)){
			list = Constant.BJSC_TABLE_LIST;
			
		}else if(subType.equals(Constant.LOTTERY_TYPE_K3)){
			list = Constant.K3_TABLE_LIST;
			
		}else if(subType.equals(Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH)){
			list = Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME_LIST;
		}
		
		for (int i = 0; i < list.length; i++) {
			StringBuffer finalSql=new StringBuffer();
			StringBuffer sqTemplatelBuffer=new StringBuffer();	
			String tableName = list[i];
			String attribute="null";
			if(tableName.equals(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME) || subType.equals(Constant.LOTTERY_TYPE_NC)){
				attribute="max(a.attribute)";
			}
			sqTemplatelBuffer.append( " select " +
					"a.order_no          as orderNo," +
					"max(a.betting_date)      as bettingDate," +
					"a.periods_num 	     as periodsNum," +
					"a.type_code        	 as typeCode," +
					"max(a.plate)             as plate," +
					"max(a.odds)   		 	 as odds," +
					"sum(a.money)   			 as money," +
					"max((select c.account from tb_frame_member_staff c where a.betting_user_id = c.id))    as member," +
					"max((select b.account from tb_frame_manager_staff b where a.branchstaff = b.id)) 	   as branch," +
					"max((select b.account from tb_frame_manager_staff b where a.stockholderstaff = b.id))  as stock," +
					"max((select b.account from tb_frame_manager_staff b where a.genagenstaff = b.id)) 	   as genagen," +
					"max((select b.account from tb_frame_manager_staff b where a.agentstaff = b.id)) 	   as agent," +
				    "100-max(a.COMMISSION_BRANCH)  			 as chiefCommission," +
					"max(a.RATE_CHIEF)   					 as chiefRate," +
					"max(a.RATE_BRANCH)   					 as branchRate," +
					"max(a.RATE_STOCKHOLDER)   				 as stockRate," +
					"max(a.RATE_GEN_AGENT)   				 as genAgentRate," +
					"max(a.RATE_AGENT)   			 		 as agentRate," +
					"sum(a." + rateUser + "/100 * a.money) 	 as rateMoney," +
					attribute+"		 					 as attribute ," +
					"9		 					 as userType," +
					"count(order_no) as count "
					+ " from "
					+ tableName + " a "				
					+ " where  a.periods_num = ? ");
			
			if(typeCode!=null){
				sqTemplatelBuffer.append(" and a.type_code = ? ");
			}
			
			if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){sqTemplatelBuffer.append(" and a.chiefstaff=? ");}
			else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType)){sqTemplatelBuffer.append(" and a.branchstaff=? ");}
			else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType)){sqTemplatelBuffer.append(" and a.stockholderstaff=? ");}
			else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.genagenstaff=? ");}
			else if(ManagerStaff.USER_TYPE_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.agentstaff=? ");}
			
			sqTemplatelBuffer.append(" and a.betting_date> ? and a.money > ? " );
			sqTemplatelBuffer.append(" group by a.order_no,type_code,a.periods_num order by bettingDate desc" );
			//sqTemplatelBuffer.append(" order by a.betting_date asc ");
			
			Object[] parameters = null;
			if(typeCode==null){
				parameters = new Object[] { periodsNum,userId,prevSearchTime,money };
			}else{
				parameters = new Object[] { periodsNum,typeCode,userId,prevSearchTime,money };
			}
			finalSql.append("select * from ( ");
			finalSql.append("	select * from ( ");
			finalSql.append(sqTemplatelBuffer);
			finalSql.append(") order by orderNo desc) where rownum <=50 ");
			List betlist = jdbcTemplate.query(finalSql.toString(), parameters,new RealTimeDetailItemMapper());
			retList.addAll(betlist);
			
		}
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		//Collections.sort(retList);
		page.setTotalCount(retList.size());
		if(first>last){first=0;last=0;}
		page.setResult(retList.subList(first, last));
		return page;
		
	}
	
	@Override
	public Page queryReplenishInDetail_RealTime(Page page, Long userId,String typeCode,String periodsNum,String rateUser,String userType,Date prevSearchTime,Integer money) {
		
		List<DetailVO> retList = new ArrayList<DetailVO>();
		
		StringBuffer finalSql=new StringBuffer();
		StringBuffer sqTemplatelBuffer=new StringBuffer();	
		String tableName = "TB_REPLENISH";
		sqTemplatelBuffer.append( " select " +
				"a.order_no          as orderNo," +
				"a.betting_date      as bettingDate," +
				"a.periods_num 	     as periodsNum," +
				"a.type_code         as typeCode," +
				"a.plate             as plate," +
				"a.odds   		 	 as odds," +
				"a.money   			 as money," +
				"(select c.account from tb_frame_manager_staff c where a.REPLENISH_USER_ID = c.id)    as member," +
				"(select b.account from tb_frame_manager_staff b where a.branchstaff = b.id) 	   as branch," +
				"(select b.account from tb_frame_manager_staff b where a.stockholderstaff = b.id)  as stock," +
				"(select b.account from tb_frame_manager_staff b where a.genagenstaff = b.id) 	   as genagen," +
				"(select b.account from tb_frame_manager_staff b where a.agentstaff = b.id) 	   as agent," +
			    "100-a.COMMISSION_CHIEF   			 as chiefCommission," +
			    //"a.COMMISSION_CHIEF   			 as chiefCommission," +
				"a.RATE_CHIEF   					 as chiefRate," +
				"a.RATE_BRANCH   					 as branchRate," +
				"a.RATE_STOCKHOLDER   				 as stockRate," +
				"a.RATE_GEN_AGENT   				 as genAgentRate," +
				"a.RATE_AGENT   			 		 as agentRate," +
				"a." + rateUser + "/100 * a.money 		 as rateMoney," +
				"a.attribute		 					 as attribute," +
				"b.user_type		 					 as userType," +
				"rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
				+ " from "
				+ tableName + " a, tb_frame_manager_staff b  "				
				+ " where  a.periods_num = ? ");
		if(typeCode!=null){
			sqTemplatelBuffer.append(" and a.type_code like ? ");
		}
		
		if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){sqTemplatelBuffer.append(" and a.chiefstaff=? ");}
		else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType)){sqTemplatelBuffer.append(" and a.branchstaff=?  ");}
		else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType)){sqTemplatelBuffer.append(" and a.stockholderstaff=? ");}
		else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.genagenstaff=? ");}
		else if(ManagerStaff.USER_TYPE_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.agentstaff=? ");}
		
		sqTemplatelBuffer.append(" and a.betting_date> ? and a.money > ?  and a.replenish_user_id=b.id " );
		sqTemplatelBuffer.append(" order by a.betting_date desc,orderNo desc ");
		
		Object[] parameters = null;
		if(typeCode==null){
			parameters = new Object[] { periodsNum,userId,prevSearchTime,money };
		}else{
			parameters = new Object[] { periodsNum,typeCode,userId,prevSearchTime,money};
		}
		finalSql.append("select * from (");
		finalSql.append(sqTemplatelBuffer);
		finalSql.append(") where rankNum=1 and rownum<=50 ");
		List betlist = new ArrayList();
		try {
			betlist = jdbcTemplate.query(finalSql.toString(), parameters,new RealTimeDetailItemMapper());
            
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		retList.addAll(betlist);
		
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		page.setTotalCount(retList.size());
		if(first>last){first=0;last=0;}
		page.setResult(retList.subList(first, last));
		return page;
		
	}
	
	@Override
	public Page queryReplenishOutDetail_RealTime(Page page, Long userId,String typeCode,String periodsNum,String rateUser,String userType,Date prevSearchTime,Integer money) {
		
		List<DetailVO> retList = new ArrayList<DetailVO>();
		
		StringBuffer finalSql=new StringBuffer();
		StringBuffer sqTemplatelBuffer=new StringBuffer();	
		String tableName = "TB_REPLENISH";
		sqTemplatelBuffer.append( " select " +
				"a.order_no          as orderNo," +
				"a.betting_date      as bettingDate," +
				"a.periods_num 	     as periodsNum," +
				"a.type_code         as typeCode," +
				"a.plate             as plate," +
				"a.odds   		 	 as odds," +
				"a.money   			 as money," +
				"(select c.account from tb_frame_manager_staff c where a.REPLENISH_USER_ID = c.id)    as member," +
				"(select b.account from tb_frame_manager_staff b where a.branchstaff = b.id) 	   as branch," +
				"(select b.account from tb_frame_manager_staff b where a.stockholderstaff = b.id)  as stock," +
				"(select b.account from tb_frame_manager_staff b where a.genagenstaff = b.id) 	   as genagen," +
				"(select b.account from tb_frame_manager_staff b where a.agentstaff = b.id) 	   as agent," +
			    "100-a.COMMISSION_CHIEF   			 as chiefCommission," +
				"a.RATE_CHIEF   					 as chiefRate," +
				"a.RATE_BRANCH   					 as branchRate," +
				"a.RATE_STOCKHOLDER   				 as stockRate," +
				"a.RATE_GEN_AGENT   				 as genAgentRate," +
				"a.RATE_AGENT   			 		 as agentRate," +
				"-(a." + rateUser + "/100 * a.money) 		 as rateMoney," +
				"a.attribute		 					 as attribute ," +
				"b.user_type		 					 as userType," +
				"rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
				+ " from "
				+ tableName + " a, tb_frame_manager_staff b "				
				+ " where  a.periods_num = ? ");
		
		if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
			sqTemplatelBuffer.append(" and a.replenish_user_id in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
			
		}else{
			sqTemplatelBuffer.append(" and a.replenish_user_id=? ");
		}
		
		if(typeCode!=null){
			sqTemplatelBuffer.append(" and a.type_code like ? and a.replenish_user_id=b.id ");
		}
		
		sqTemplatelBuffer.append(" and a.betting_date> ? and a.money > ? " );
		sqTemplatelBuffer.append(" order by a.betting_date desc,orderNo desc ");
		
		Object[] parameters = null;
		if(typeCode==null){
			parameters = new Object[] { periodsNum,userId,prevSearchTime,money};
		}else{
			parameters = new Object[] { periodsNum,userId,typeCode,prevSearchTime,money};
		}
		finalSql.append("select * from (");
		finalSql.append(sqTemplatelBuffer);
		finalSql.append(") where rankNum=1 and rownum<=50 ");
		List betlist = jdbcTemplate.query(finalSql.toString(), parameters,new RealTimeDetailItemMapper());
		retList.addAll(betlist);
		
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		page.setTotalCount(retList.size());
		if(first>last){first=0;last=0;}
		page.setResult(retList.subList(first, last));
		return page;
		
	}
	
	//***********************************备份start*******************
	@Override
	public Page queryBetDetail_Backup(Page page, Long userId,String typeCode,String periodsNum,String userType,String subType) {
		
		List<DetailVO> retList = new ArrayList<DetailVO>();
		
		String[] list = new String[]{};
		if(subType.equals(Constant.LOTTERY_TYPE_GDKLSF)){
			list = Constant.GDKLSF_TABLE_LIST;
		}else if(subType.equals(Constant.LOTTERY_TYPE_NC)){
			list = new String[]{Constant.NC_TABLE_NAME};
		}else if(subType.equals(Constant.LOTTERY_TYPE_CQSSC)){
			list = Constant.CQSSC_TABLE_LIST;
		}else if(subType.equals(Constant.LOTTERY_TYPE_BJ)){
			list = Constant.BJSC_TABLE_LIST;
		}else if(subType.equals(Constant.LOTTERY_TYPE_K3)){
			list = Constant.K3_TABLE_LIST;
		}else if(subType.equals(Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH)){
			list = Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME_LIST;
		}
		for (int i = 0; i < list.length; i++) {
			StringBuffer finalSql=new StringBuffer();
			StringBuffer sqTemplatelBuffer=new StringBuffer();	
			String tableName = list[i];
			String attribute="null";
			if(tableName.equals(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME) || subType.equals(Constant.LOTTERY_TYPE_NC)){
				attribute="max(a.attribute)";
			}
			sqTemplatelBuffer.append( " select " +
					"a.order_no          as orderNo," +
					"max(a.betting_date)      as bettingDate," +
					"a.periods_num 	     as periodsNum," +
					"a.type_code         as typeCode," +
					"max(a.plate)             as plate," +
					"max(a.odds)   		 	 as odds," +
					"sum(a.money)   			 as money," +
					"max(c.account)    as member," +
					"max(a.RATE_CHIEF)   					 as chiefRate," +
					"max(a.RATE_BRANCH)   					 as branchRate," +
					"max(a.RATE_STOCKHOLDER)   				 as stockRate," +
					"max(a.RATE_GEN_AGENT)   				 as genAgentRate," +
					"max(a.RATE_AGENT)   			 		 as agentRate," +
					attribute+"		 					 as attribute ," +
					"9		 					 as userType," +
					"count(order_no) as count "
					+ " from "
					+ tableName + " a,tb_frame_member_staff c "				
					+ " where  a.periods_num = ? and a.betting_user_id = c.id ");
			
			if(typeCode!=null){
				sqTemplatelBuffer.append(" and a.type_code like ? ");
			}
			
			if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){sqTemplatelBuffer.append(" and a.chiefstaff=? ");}
			else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType)){sqTemplatelBuffer.append(" and a.branchstaff=? ");}
			else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType)){sqTemplatelBuffer.append(" and a.stockholderstaff=? ");}
			else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.genagenstaff=? ");}
			else if(ManagerStaff.USER_TYPE_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.agentstaff=? ");}
			
			sqTemplatelBuffer.append(" group by a.order_no,type_code,a.periods_num " );
			sqTemplatelBuffer.append(" order by max(a.betting_date) asc ");
			
			Object[] parameters = null;
			if(typeCode==null){
				parameters = new Object[] { periodsNum,userId };
			}else{
				parameters = new Object[] { periodsNum,typeCode,userId};
			}
			finalSql.append(sqTemplatelBuffer);
			List betlist = jdbcTemplate.query(finalSql.toString(), parameters,new BackupDetailItemMapper());
			retList.addAll(betlist);
			
		}
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		//Collections.sort(retList);
		page.setTotalCount(retList.size());
		if(first>last){first=0;last=0;}
		page.setResult(retList.subList(first, last));
		return page;
		
	}
	
	@Override
	public Page queryReplenishInDetail_Backup(Page page, Long userId,String typeCode,String periodsNum,String rateUser,String userType) {
		
		List<DetailVO> retList = new ArrayList<DetailVO>();
		
		StringBuffer finalSql=new StringBuffer();
		StringBuffer sqTemplatelBuffer=new StringBuffer();	
		String tableName = "TB_REPLENISH";
		sqTemplatelBuffer.append( " select " +
				"a.order_no          as orderNo," +
				"a.betting_date      as bettingDate," +
				"a.periods_num 	     as periodsNum," +
				"a.type_code         as typeCode," +
				"a.plate             as plate," +
				"a.odds   		 	 as odds," +
				"a.money   			 as money," +
				"(select c.account from tb_frame_manager_staff c where a.REPLENISH_USER_ID = c.id)    as member," +
				"a.RATE_CHIEF   					 as chiefRate," +
				"a.RATE_BRANCH   					 as branchRate," +
				"a.RATE_STOCKHOLDER   				 as stockRate," +
				"a.RATE_GEN_AGENT   				 as genAgentRate," +
				"a.RATE_AGENT   			 		 as agentRate," +
				"a." + rateUser + "/100 * a.money 		 as rateMoney," +
				"a.attribute		 					 as attribute," +
				"b.user_type		 					 as userType," +
				"1 as count "
				+ " from "
				+ tableName + " a, tb_frame_manager_staff b  "				
				+ " where  a.periods_num = ? ");
		if(typeCode!=null){
			sqTemplatelBuffer.append(" and a.type_code like ? ");
		}
		
		if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){sqTemplatelBuffer.append(" and a.chiefstaff=? ");}
		else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType)){sqTemplatelBuffer.append(" and a.branchstaff=?  ");}
		else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType)){sqTemplatelBuffer.append(" and a.stockholderstaff=? ");}
		else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.genagenstaff=? ");}
		else if(ManagerStaff.USER_TYPE_AGENT.equals(userType)){sqTemplatelBuffer.append(" and a.agentstaff=? ");}
		
		sqTemplatelBuffer.append(" and a.replenish_user_id=b.id " );
		
		sqTemplatelBuffer.append(" order by a.betting_date asc ");
		
		Object[] parameters = null;
		if(typeCode==null){
			parameters = new Object[] { periodsNum,userId};
		}else{
			parameters = new Object[] { periodsNum,typeCode,userId};
		}
		//finalSql.append("select * from (");
		finalSql.append(sqTemplatelBuffer);
		//finalSql.append(") where rankNum=1");
		List betlist = new ArrayList();
		try {
			betlist = jdbcTemplate.query(finalSql.toString(), parameters,new BackupDetailItemMapper());
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		retList.addAll(betlist);
		
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		page.setTotalCount(retList.size());
		if(first>last){first=0;last=0;}
		page.setResult(retList.subList(first, last));
		return page;
		
	}
	
	@Override
	public Page queryReplenishOutDetail_Backup(Page page, Long userId,String typeCode,String periodsNum,String rateUser,String userType) {
		
		List<DetailVO> retList = new ArrayList<DetailVO>();
		
		StringBuffer finalSql=new StringBuffer();
		StringBuffer sqTemplatelBuffer=new StringBuffer();	
		String tableName = "TB_REPLENISH";
		sqTemplatelBuffer.append( " select " +
				"a.order_no          as orderNo," +
				"a.betting_date      as bettingDate," +
				"a.periods_num 	     as periodsNum," +
				"a.type_code         as typeCode," +
				"a.plate             as plate," +
				"a.odds   		 	 as odds," +
				"a.money   			 as money," +
				"(select c.account from tb_frame_manager_staff c where a.REPLENISH_USER_ID = c.id)    as member," +
				"a.RATE_CHIEF   					 as chiefRate," +
				"a.RATE_BRANCH   					 as branchRate," +
				"a.RATE_STOCKHOLDER   				 as stockRate," +
				"a.RATE_GEN_AGENT   				 as genAgentRate," +
				"a.RATE_AGENT   			 		 as agentRate," +
				"a.attribute		 					 as attribute ," +
				"b.user_type		 					 as userType," +
				"1 as count "
				+ " from "
				+ tableName + " a, tb_frame_manager_staff b "				
				+ " where  a.periods_num = ? ");
		
		if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
			sqTemplatelBuffer.append(" and a.replenish_user_id in (select b.manager_staff_id from tb_out_replenish_staff_ext b where b.parent_staff=?) ");
			
		}else{
			sqTemplatelBuffer.append(" and a.replenish_user_id=? ");
		}
		
		if(typeCode!=null){
			sqTemplatelBuffer.append(" and a.type_code like ? and a.replenish_user_id=b.id ");
		}
		
		sqTemplatelBuffer.append(" order by a.betting_date asc ");
		
		Object[] parameters = null;
		if(typeCode==null){
			parameters = new Object[] { periodsNum,userId};
		}else{
			parameters = new Object[] { periodsNum,userId,typeCode};
		}
		//finalSql.append("select * from (");
		finalSql.append(sqTemplatelBuffer);
		//finalSql.append(") where rankNum=1");
		List betlist = jdbcTemplate.query(finalSql.toString(), parameters,new BackupDetailItemMapper());
		retList.addAll(betlist);
		
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		page.setTotalCount(retList.size());
		if(first>last){first=0;last=0;}
		page.setResult(retList.subList(first, last));
		return page;
		
	}
	//***********************************备份start*******************

	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 新方法，实时滚单补入、补进数据查询，将两条slq合并成一条查询，提高查询性能
	 * @param page
	 * @param userId
	 * @param typeCode
	 * @param periodsNum
	 * @param rateUser
	 * @param userType
	 * @param prevSearchTime
	 * @param money
	 * @return
	 */
	@Override
	public Page queryReplenishInAndOutDetail_RealTime(Page page, Long userId, String typeCode, String periodsNum,
			String rateUser, String userType, Date prevSearchTime, Integer money) {
		List<DetailVO> retList = new ArrayList<DetailVO>();
		StringBuffer finalSql=new StringBuffer();
		StringBuffer sqTemplatelBuffer=new StringBuffer();	
		String tableName = "TB_REPLENISH";
		sqTemplatelBuffer.append( " select " +
				"a.order_no          as orderNo," +
				"a.betting_date      as bettingDate," +
				"a.periods_num 	     as periodsNum," +
				"a.type_code         as typeCode," +
				"a.plate             as plate," +
				"a.odds   		 	 as odds," +
				"a.money   			 as money," +
				"(select c.account from tb_frame_manager_staff c where a.REPLENISH_USER_ID = c.id)    as member," +
				"(select b.account from tb_frame_manager_staff b where a.branchstaff = b.id) 	   as branch," +
				"(select b.account from tb_frame_manager_staff b where a.stockholderstaff = b.id)  as stock," +
				"(select b.account from tb_frame_manager_staff b where a.genagenstaff = b.id) 	   as genagen," +
				"(select b.account from tb_frame_manager_staff b where a.agentstaff = b.id) 	   as agent," +
			    "100-a.COMMISSION_CHIEF   			 as chiefCommission," +
				"a.RATE_CHIEF   					 as chiefRate," +
				"a.RATE_BRANCH   					 as branchRate," +
				"a.RATE_STOCKHOLDER   				 as stockRate," +
				"a.RATE_GEN_AGENT   				 as genAgentRate," +
				"a.RATE_AGENT   			 		 as agentRate," +
				"a." + rateUser + "/100 * a.money 		 as rateMoney," +
				"a.attribute		 					 as attribute," +
				"b.user_type		 					 as userType," +
				"case when {whenSql} =? then 'in' else 'out' end as inOrOut, "+ 
				"rank() over(partition by order_no order by rownum  desc) rankNum,count(*) over(partition by order_no) count "
				+ " from "
				+ tableName + " a, tb_frame_manager_staff b  "				
				+ " where  a.periods_num = ? ");
		if(typeCode!=null){
			sqTemplatelBuffer.append(" and a.type_code like ? ");
		}
		String whenSql="";
		if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
			whenSql="a.chiefstaff";
			sqTemplatelBuffer.append(" and (a.chiefstaff=? or a.replenish_user_id IN (SELECT b.manager_staff_id FROM tb_out_replenish_staff_ext b WHERE b.parent_staff=?)) ");
		}else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType)){
			whenSql="a.branchstaff";
			sqTemplatelBuffer.append(" and a.branchstaff=?  ");
		}else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType)){
			whenSql="a.stockholderstaff";
			sqTemplatelBuffer.append(" and a.stockholderstaff=? ");
		}else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType)){
			whenSql="a.genagenstaff";
			sqTemplatelBuffer.append(" and a.genagenstaff=? ");
		}else if(ManagerStaff.USER_TYPE_AGENT.equals(userType)){
			whenSql="a.agentstaff";
			sqTemplatelBuffer.append(" and a.agentstaff=? ");
		}
		
		sqTemplatelBuffer.append(" and a.betting_date> ? and a.money > ?  and a.replenish_user_id=b.id " );
		sqTemplatelBuffer.append(" order by a.betting_date desc");
		
		Object[] parameters = null;
		if(typeCode==null){
			if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
				parameters = new Object[] {userId, periodsNum,userId,userId,prevSearchTime,money };
			}else{
				parameters = new Object[] {userId, periodsNum,userId,prevSearchTime,money };
			}
			
		}else{
			if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
				parameters = new Object[] {userId, periodsNum,typeCode,userId,userId,prevSearchTime,money};
			}else{
				parameters = new Object[] {userId, periodsNum,typeCode,userId,prevSearchTime,money};
			}
			
		}
		finalSql.append("select * from (");
		finalSql.append("		select * from (");
		finalSql.append(sqTemplatelBuffer);
		finalSql.append("		) where rankNum=1 order by orderNo desc) where rownum<=50 ");
		List betlist = new ArrayList();
		try {
			String sql=finalSql.toString().replace("{whenSql}", whenSql);
			betlist = jdbcTemplate.query(sql, parameters,new RealTimeDetailItemMapper());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		retList.addAll(betlist);
		
		int first = page.getFirst() - 1;
		int last = first + page.getPageSize();
		if (last > retList.size())
			last = retList.size();
		page.setTotalCount(retList.size());
		if(first>last){first=0;last=0;}
		page.setResult(retList.subList(first, last));
		return page;
		
	}
}

class GDReplenishDetailItemMapper implements RowMapper {

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
		item.setRateMoney(rs.getBigDecimal("rateMoney"));
		//item.setChiefOutCommission(rs.getBigDecimal("chiefOutCommission"));
		item.setUserType(rs.getString("userType"));
		item.setWinAmount(rs.getDouble("winAmount"));
		item.setRateWinAmount(rs.getDouble("rateWinAmount"));
		item.setRateBackWater(rs.getDouble("rateBackWater"));
		item.setCount(rs.getInt("count"));
		item.setWinState(rs.getString("winState"));
		return item;
	}
}

class GDReplenishDetailItemMapperR implements RowMapper {
	
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
		item.setRateMoney(rs.getBigDecimal("rateMoney"));
		item.setUserType(rs.getString("userType"));
		return item;
	}
}
class GDBetDetailItemMapper implements RowMapper {
	
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
		item.setRateMoney(rs.getBigDecimal("rateMoney"));
		item.setParentUserType(rs.getString("parentUserType"));
		item.setCount(rs.getInt("count"));
		return item;
	}
}
class RealTimeDetailItemMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		DetailVO item = new DetailVO();
		item.setOrderNo(rs.getString("orderNo"));
		item.setBettingDate(rs.getTimestamp("bettingDate"));
		item.setPeriodsNum(rs.getString("periodsNum"));
		item.setAttribute(rs.getString("attribute"));
		item.setTypeCode(rs.getString("typeCode"));
		item.setPlate(rs.getString("plate"));
		item.setMoney(rs.getInt("money"));
		item.setOdds(rs.getBigDecimal("odds"));
		item.setChiefCommission(rs.getBigDecimal("chiefCommission"));
		item.setChiefRate(rs.getBigDecimal("chiefRate"));
		item.setBranchRate(rs.getBigDecimal("branchRate"));
		item.setStockRate(rs.getBigDecimal("stockRate"));
		item.setGenAgentRate(rs.getBigDecimal("genAgentRate"));
		item.setAgentRate(rs.getBigDecimal("agentRate"));
		item.setRateMoney(rs.getBigDecimal("rateMoney"));
		item.setUserName(rs.getString("member"));
		item.setBranch(rs.getString("branch"));
		item.setStock(rs.getString("stock"));
		item.setGenAgent(rs.getString("genagen"));
		item.setAgent(rs.getString("agent"));
		item.setUserType(rs.getString("userType"));
		item.setCount(rs.getInt("count"));
		String inOrOut="";
		boolean columnIsExist=false;
		try {
			inOrOut=rs.getString("inOrOut");
			columnIsExist=true;
		} catch (Exception e) {
			columnIsExist=false;
		}
		if(columnIsExist){
			if(inOrOut.equals("out")){
				if(ManagerStaff.USER_TYPE_CHIEF.equals(item.getUserType()) || ManagerStaff.USER_TYPE_OUT_REPLENISH.equals(item.getUserType())){
					item.setChiefRate(BigDecimal.valueOf(-100));
					item.setChiefCommission(item.getChiefOutCommission());
					item.setWhoReplenish("總監出貨");
				}
				if(ManagerStaff.USER_TYPE_BRANCH.equals(item.getUserType())){
					item.setBranchRate(BigDecimal.valueOf(-100));
					item.setWhoReplenish("分公司走飛");
				}
				if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(item.getUserType())){
					item.setStockRate(BigDecimal.valueOf(-100));
					item.setWhoReplenish("股東走飛");
				}
				if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(item.getUserType())){
					item.setGenAgentRate(BigDecimal.valueOf(-100));
					item.setWhoReplenish("總代理走飛");
				}
				if(ManagerStaff.USER_TYPE_AGENT.equals(item.getUserType())){
					item.setAgentRate(BigDecimal.valueOf(-100));
					item.setWhoReplenish("代理走飛");
				}
				item.setRateMoney(BigDecimal.valueOf(-item.getMoney()));
				item.setRateMoney(BigDecimal.valueOf(-item.getRateMoney().doubleValue()));
			}else{
				if(ManagerStaff.USER_TYPE_BRANCH.equals(item.getUserType())){
					item.setBranchRate(BigDecimal.valueOf(-100));
				}
				if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(item.getUserType())){
					item.setStockRate(BigDecimal.valueOf(-100));
				}
				if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(item.getUserType())){
					item.setGenAgentRate(BigDecimal.valueOf(-100));
				}
				if(ManagerStaff.USER_TYPE_AGENT.equals(item.getUserType())){
					item.setAgentRate(BigDecimal.valueOf(-100));
				}
				item.setWhoReplenish("向上走飛");
			}
		}
		
		return item;
	}
}


class BackupDetailItemMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		DetailVO item = new DetailVO();
		item.setOrderNo(rs.getString("orderNo"));
		item.setBettingDate(rs.getTimestamp("bettingDate"));
		item.setPeriodsNum(rs.getString("periodsNum"));
		item.setAttribute(rs.getString("attribute"));
		item.setTypeCode(rs.getString("typeCode"));
		item.setPlate(rs.getString("plate"));
		item.setMoney(rs.getInt("money"));
		item.setOdds(rs.getBigDecimal("odds"));
		item.setChiefRate(rs.getBigDecimal("chiefRate"));
		item.setBranchRate(rs.getBigDecimal("branchRate"));
		item.setStockRate(rs.getBigDecimal("stockRate"));
		item.setGenAgentRate(rs.getBigDecimal("genAgentRate"));
		item.setAgentRate(rs.getBigDecimal("agentRate"));
		item.setUserType(rs.getString("userType"));
		item.setUserName(rs.getString("member"));
		item.setCount(rs.getInt("count"));
		return item;
	}
}


class TotalMapperForOut implements RowMapper {  	  
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		ReplenishVO item = new ReplenishVO();  
		item.setPlayFinalType(rs.getString("playFinalType"));	   
		item.setOddsMoney(rs.getBigDecimal("oddsMoney"));
		item.setMoney(rs.getBigDecimal("money").intValue());
		item.setTsMoney(rs.getBigDecimal("tsMoney"));
		item.setTotalCount(rs.getInt("totalCount"));
		return item;  
	}  	  
}
class TotalMapperForR implements RowMapper {  	  
	  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		   ReplenishVO item = new ReplenishVO();  
		   item.setPlayFinalType(rs.getString("playFinalType"));	   
		   item.setOddsMoney(rs.getBigDecimal("oddsMoney"));
		   item.setMoney(rs.getInt("money"));
		   item.setCommissionMoney(rs.getBigDecimal("commissionMoney"));
		   return item;  
	  }  	  
}

class TotalMapperBJ implements RowMapper {  	  
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		ReplenishVO item = new ReplenishVO();  
		item.setPlayFinalType(rs.getString("playFinalType"));	   
		item.setOddsMoney(rs.getBigDecimal("oddsMoney"));
		item.setMoney(rs.getInt("money"));
		item.setCommissionMoney(rs.getBigDecimal("commissionMoney"));
		item.setRateMoney(rs.getBigDecimal("rateMoney"));
		item.setPlayTypeName(rs.getString("playTypeName"));
		return item;  
	}  	  
}

class TotalMapper implements RowMapper {  	  
	  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		   ReplenishVO item = new ReplenishVO();  
		   item.setPlayFinalType(rs.getString("playFinalType"));	   
		   item.setOddsMoney(rs.getBigDecimal("oddsMoney"));
		   item.setMoney(rs.getInt("money"));
		   item.setCommissionMoney(rs.getBigDecimal("commissionMoney"));
		   item.setRateMoney(rs.getBigDecimal("rateMoney"));
		   return item;  
	  }  	  
}

class TotalMapperForBetCheck implements RowMapper {  	  
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		ReplenishVO item = new ReplenishVO();  
		item.setMoney(rs.getInt("money"));
		return item;  
	}  	  
}

class TotalMapper_R_LM_Main implements RowMapper {  	  
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		ReplenishVO item = new ReplenishVO();  
		item.setPlayFinalType(rs.getString("playFinalType"));	   
		item.setOddsMoney(rs.getBigDecimal("oddsMoney"));
		item.setMoney(rs.getInt("money"));
		item.setCommissionMoney(rs.getBigDecimal("commissionMoney"));
		return item;  
	}  	  
}

class TotalMapper_R_LM implements RowMapper {  	  
	  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		   ReplenishVO item = new ReplenishVO();  
		   item.setPlayFinalType(rs.getString("playFinalType"));	   
		   item.setOddsMoney(rs.getBigDecimal("oddsMoney"));
		   item.setMoney(rs.getInt("money"));
		   item.setCommissionMoney(rs.getBigDecimal("commissionMoney"));
		   item.setAttribute(rs.getString("attribute"));
		   return item;  
	  }  	  
}

class TotalMapper_R_LM_In_Main implements RowMapper {  	  
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		ReplenishVO item = new ReplenishVO();  
		item.setPlayFinalType(rs.getString("playFinalType"));	   
		item.setOddsMoney(rs.getBigDecimal("oddsMoney"));
		item.setRateMoney(rs.getBigDecimal("rateMoney"));
		item.setCommissionMoney(rs.getBigDecimal("commissionMoney"));
		return item;  
	}  	  
}

class TotalMapper_R_LM_In implements RowMapper {  	  
	  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		   ReplenishVO item = new ReplenishVO();  
		   item.setPlayFinalType(rs.getString("playFinalType"));	   
		   item.setOddsMoney(rs.getBigDecimal("oddsMoney"));
		   item.setRateMoney(rs.getBigDecimal("rateMoney"));
		   item.setCommissionMoney(rs.getBigDecimal("commissionMoney"));
		   item.setAttribute(rs.getString("attribute"));
		   return item;  
	  }  	  
}

class TotalMapper_LM_Main implements RowMapper {  	  
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		ReplenishVO item = new ReplenishVO();  
		item.setPlayFinalType(rs.getString("playFinalType"));	   
		item.setLoseMoney(rs.getBigDecimal("loseMoney"));
		item.setMoney(rs.getInt("money"));
		item.setCommissionMoney(rs.getBigDecimal("commissionMoney"));
		item.setRateMoney(rs.getBigDecimal("rateMoney"));
		return item;  
	}  	  
}

class TotalMapper_LM implements RowMapper {  	  
	  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		   ReplenishVO item = new ReplenishVO();  
		   item.setPlayFinalType(rs.getString("playFinalType"));	   
		   item.setLoseMoney(rs.getBigDecimal("loseMoney"));
		   item.setMoney(rs.getInt("money"));
		   item.setCommissionMoney(rs.getBigDecimal("commissionMoney"));
		   item.setRateMoney(rs.getBigDecimal("rateMoney"));
		   item.setAttribute(rs.getString("attribute"));
		   return item;  
	  }  	  
}


class ReplenishHKVOMapper implements RowMapper {  	  
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		ReplenishHKVO item = new ReplenishHKVO();  
		item.setPlayTypeCode(rs.getString("playTypeCode"));	   
		item.setTotalCount(rs.getInt("totalCount"));
		item.setTotalMoney(rs.getBigDecimal("totalMoney").intValue());
		item.setTotalMoneyYK(rs.getBigDecimal("totalMoneyYK"));
		item.setTsMoney(rs.getBigDecimal("tsMoney"));
		item.setOddsMoney(rs.getBigDecimal("oddsMoney"));
		return item;  
	}  	  
}
class ReplenishHKVOLXMapper implements RowMapper {  	  
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		ReplenishHKVO item = new ReplenishHKVO();  
		item.setPlayTypeCode(rs.getString("playTypeCode"));	   
		item.setTotalCount(Integer.valueOf(rs.getString("totalCount")));
		item.setTotalMoney(rs.getBigDecimal("totalMoney").intValue());
		item.setAttributes(rs.getString("attributes"));
		item.setTotalMoneyYK(rs.getBigDecimal("totalMoneyYK"));
		item.setTsMoney(rs.getBigDecimal("tsMoney"));
		item.setOddsMoney(rs.getBigDecimal("oddsMoney"));
		return item;  
	}  	  
}
class ReplenishHKVOSXLMapper implements RowMapper {  	  
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		ReplenishHKVO item = new ReplenishHKVO();  
		item.setPlayTypeCode(rs.getString("playTypeCode"));	   
		item.setTotalCount(Integer.valueOf(rs.getString("totalCount")));
		item.setTotalMoneyYK(rs.getBigDecimal("totalMoneyYK"));
		item.setSplitAttribute(rs.getString("splitAttribute"));
		
		return item;  
	}  	  
}


