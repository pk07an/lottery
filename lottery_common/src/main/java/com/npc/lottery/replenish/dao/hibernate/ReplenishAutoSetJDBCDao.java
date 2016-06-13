package com.npc.lottery.replenish.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.google.common.collect.Lists;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.replenish.dao.interf.IReplenishAutoSetJDBCDao;
import com.npc.lottery.replenish.dao.interf.IReplenishAutoSetLog;
import com.npc.lottery.replenish.entity.ReplenishAutoSetLog;
import com.npc.lottery.replenish.vo.AutoReplenishSetVO;
import com.npc.lottery.replenish.vo.ZhanDangVO;
import com.npc.lottery.sysmge.entity.ManagerStaff;
 
public class ReplenishAutoSetJDBCDao implements IReplenishAutoSetJDBCDao {

	private static Logger log = Logger.getLogger(ReplenishAutoSetJDBCDao.class);
    private JdbcTemplate jdbcTemplate;
    
    
    @Override
    public List<AutoReplenishSetVO> queryTotalTrueMoney(String periodNum,Long userid,String userType,String myColumn,String rateUser,String playType)
    {
    	String[] scanTableList = new String[]{};
    	StringBuffer finalSql=new StringBuffer();
    	
    	finalSql.append("SELECT autoReplenishType, max(totalMoney) AS totalMoney FROM (");//这一层以自动补货类型为分组条件查出最大的投注额实货
    	
    	finalSql.append("SELECT typeCode, c.auto_replenish_type AS autoReplenishType, totalMoney FROM (");//这一层把统计类型与TB_PALY_TYPE的自动补货类弄匹配
    	
    	finalSql.append("SELECT typeCode,sum(totalMoney) AS totalMoney FROM (");//这一层统计出所有玩法的投注总额(已经减去补出和加入下级补入的)
    	
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	
    	sqTemplatelBuffer.append(" select a.type_code AS typeCode, sum(a.money * a." + rateUser + "/100) AS totalMoney " +
    			" from {TableName}  a ");
    			//" from {TableName}  a," +
    			//" (select t1.id,t1.account,t1.chs_name,t1.user_type from tb_frame_manager_staff t1 union select  t2.id,t2.account,t2.chs_name,t2.user_type from tb_frame_member_staff t2 ) b ");
    	
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.chiefstaff=? ");
    		//sqTemplatelBuffer.append(" where  a.chiefstaff=? and decode(a.branchstaff,'',a.betting_user_id,a.branchstaff)=b.id ");
    	}
    	
    	else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.branchstaff=? ");
    		//sqTemplatelBuffer.append(" where  a.branchstaff=? and decode(a.stockholderstaff,'',a.betting_user_id,a.stockholderstaff)=b.id ");
    	}
    	else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.stockholderstaff=? ");
    		//sqTemplatelBuffer.append(" where  a.stockholderstaff=? and decode(a.genagenstaff,'',a.betting_user_id,a.genagenstaff)=b.id ");
    	}
    	else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.genagenstaff=? ");
    		//sqTemplatelBuffer.append(" where  a.genagenstaff=? and decode(a.agentstaff,'',a.betting_user_id,a.agentstaff)=b.id ");
    	}
    	else if(ManagerStaff.USER_TYPE_AGENT.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.agentstaff=? ");
    		//sqTemplatelBuffer.append(" where  a.agentstaff=? and a.betting_user_id=b.id and user_type!=7 ");
    	}	
    	
    	sqTemplatelBuffer.append(" and a.periods_num=? group by a.type_code ");
    	
    	if(Constant.LOTTERY_TYPE_GDKLSF.equals(playType)){
    		scanTableList = Constant.GDKLSF_TABLE_LIST;
    	}else if(Constant.LOTTERY_TYPE_CQSSC.equals(playType)){
    		scanTableList = Constant.CQSSC_TABLE_LIST;
    	}else if(Constant.LOTTERY_TYPE_BJSC.equals(playType)){
    		scanTableList = Constant.BJSC_TABLE_LIST;
    	}else if(Constant.LOTTERY_TYPE_NC.equals(playType)){
    		scanTableList = Constant.NC_TABLE_LIST;
    	}else{
    		scanTableList = Constant.K3_TABLE_LIST;
    	}
    	
    	for (int i = 0; i < scanTableList.length; i++) {
    		if(scanTableList[i].indexOf("STRAIGHTTHROUGH")==-1){
    			finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", scanTableList[i]));
        		finalSql.append(" union all ");
        		templateParameterList.add(userid);
        		templateParameterList.add(periodNum);
    		}
    		
    	}
    	
  //************处理补货*************START********
    	
    	//*********************本级补出 START*******************
    	finalSql.append(" select a.type_code AS typeCode,-sum(a.money) as totalMoney from tb_replenish a " +
    			"where a.replenish_user_id=?  and a.win_state in(0,6,7) and a.periods_num=? ");
    	if(playType.equals(Constant.LOTTERY_TYPE_GDKLSF)){
    		finalSql.append(" and a.type_code like 'GDKLSF_%' and a.type_code not like 'GDKLSF_STRAIGHTTHROUGH%'");
    	}
    	if(playType.equals(Constant.LOTTERY_TYPE_NC)){
    		finalSql.append(" and a.type_code like 'NC_%'  and a.type_code not like 'NC_STRAIGHTTHROUGH%' ");
    	}
    	finalSql.append("group by a.type_code ");		
		templateParameterList.add(userid);
		templateParameterList.add(periodNum);
		
    	//*********************本级补出 END*******************
    	
    	//**********************加上下级补进的************START****************
    	if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){
    		finalSql.append(" UNION ALL "); 
    		finalSql.append(" select a.type_code AS typeCode,sum(a.money*a." + rateUser + "/100) as totalMoney from tb_replenish a " +
    				" where a." + myColumn + " = ? and a.win_state in(0,6,7) and a.periods_num=? ");
    		if(playType.equals(Constant.LOTTERY_TYPE_GDKLSF)){
        		finalSql.append(" and a.type_code like 'GDKLSF_%' and a.type_code not like 'GDKLSF_STRAIGHTTHROUGH%'");
        	}
        	if(playType.equals(Constant.LOTTERY_TYPE_NC)){
        		finalSql.append(" and a.type_code like 'NC_%'  and a.type_code not like 'NC_STRAIGHTTHROUGH%' ");
        	}
        	finalSql.append("group by a.type_code ");
    		templateParameterList.add(userid);
    		templateParameterList.add(periodNum);
        	//**********************查询下级补进的************END****************
    	}
   //************处理补货*************END********
    	
    	finalSql.append(") group by typeCode "); //这一层统计出所有玩法的投注总额(已经减去补出和加入下级补入的)
    	
    	finalSql.append(") b,tb_play_type c where b.typeCode = c.type_code "); //这一层把统计类型与TB_PALY_TYPE的自动补货类弄匹配
    	
    	finalSql.append(")d group by d.autoReplenishType order by d.autoReplenishType ") ;
    	
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	
    	List<AutoReplenishSetVO> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new totalItemMapperZ());
    	return reportList;
    	
    }
    
    /**
     * 目前只有广东和农场的连码，连码的表结构有特殊性，所以分开处理
     *  连码的取值方法：
     *  
     *  以实占的值为例
     *  		
     *  比如a投了1,2,3:1000元
     *  拆后：1，2：1000元，
     *  拆后：1，3：1000元，
     *  拆后：2，3：1000元，
     *  		
     *  比如b投了1,2,3:2000元
     *  拆后：1，2：2000元，
     *  拆后：1，3：2000元，
     *  拆后：2，3：2000元，
     *  C投了1，2 ：1000元
     *  我就显示折散后最高组值1000+2000+1000=4000,如有补货，就减去补货的值！ 
     */
    @Override
    public List<AutoReplenishSetVO> queryTotalTrueMoney_LM(String periodNum,Long userid,String userType,String myColumn,String rateUser,String playType)
    {
    	String[] scanTableList = new String[]{};
    	StringBuffer finalSql=new StringBuffer();
    	
    	finalSql.append("SELECT autoReplenishType, max(totalMoney) AS totalMoney FROM (");//这一层以自动补货类型为分组条件查出最大的投注额实货
    	
    	finalSql.append("SELECT typeCode, c.auto_replenish_type AS autoReplenishType, totalMoney FROM (");//这一层把统计类型与TB_PALY_TYPE的自动补货类弄匹配
    	
    	finalSql.append("SELECT typeCode,sum(totalMoney) AS totalMoney FROM (");//这一层统计出所有玩法的投注总额(已经减去补出和加入下级补入的)
    	
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	
    	sqTemplatelBuffer.append(" select a.type_code AS typeCode, sum(a.money * a." + rateUser + "/100) AS totalMoney,a.SPLIT_ATTRIBUTE AS splitAttribute " +
    			" from {TableName}  a ");
    	//" from {TableName}  a," +
    	//" (select t1.id,t1.account,t1.chs_name,t1.user_type from tb_frame_manager_staff t1 union select  t2.id,t2.account,t2.chs_name,t2.user_type from tb_frame_member_staff t2 ) b ");
    	
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.chiefstaff=? ");
    		//sqTemplatelBuffer.append(" where  a.chiefstaff=? and decode(a.branchstaff,'',a.betting_user_id,a.branchstaff)=b.id ");
    	}
    	
    	else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.branchstaff=? ");
    		//sqTemplatelBuffer.append(" where  a.branchstaff=? and decode(a.stockholderstaff,'',a.betting_user_id,a.stockholderstaff)=b.id ");
    	}
    	else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.stockholderstaff=? ");
    		//sqTemplatelBuffer.append(" where  a.stockholderstaff=? and decode(a.genagenstaff,'',a.betting_user_id,a.genagenstaff)=b.id ");
    	}
    	else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.genagenstaff=? ");
    		//sqTemplatelBuffer.append(" where  a.genagenstaff=? and decode(a.agentstaff,'',a.betting_user_id,a.agentstaff)=b.id ");
    	}
    	else if(ManagerStaff.USER_TYPE_AGENT.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.agentstaff=? ");
    		//sqTemplatelBuffer.append(" where  a.agentstaff=? and a.betting_user_id=b.id and user_type!=7 ");
    	}	
    	
    	sqTemplatelBuffer.append(" and a.periods_num=? ");
    	
    	if(Constant.LOTTERY_TYPE_GDKLSF.equals(playType)){
    		scanTableList = Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME_LIST;
    		sqTemplatelBuffer.append(" and a.type_code like 'GDKLSF_STRAIGHTTHROUGH%' ");
    	}else{
    		scanTableList = Constant.NC_TABLE_LIST;
    		sqTemplatelBuffer.append(" and a.type_code like 'NC_STRAIGHTTHROUGH%' ");
    	}
    	
    	sqTemplatelBuffer.append(" group by a.type_code,a.SPLIT_ATTRIBUTE ");//以拆分开的规则来排序
    	
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
    	finalSql.append(" select a.type_code AS typeCode,-sum(a.money) as totalMoney,a.ATTRIBUTE AS splitAttribute from tb_replenish a " +
    			"where a.replenish_user_id=?  and a.win_state in(0,6,7) and a.periods_num=? ");
    	
    	if(Constant.LOTTERY_TYPE_GDKLSF.equals(playType)){
    		scanTableList = Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME_LIST;
    		finalSql.append(" and a.type_code like 'GDKLSF_STRAIGHTTHROUGH%' ");
    	}else{
    		scanTableList = Constant.NC_TABLE_LIST;
    		finalSql.append(" and a.type_code like 'NC_STRAIGHTTHROUGH%' ");
    	}
    	finalSql.append("group by a.type_code,a.ATTRIBUTE ");
    	
    	templateParameterList.add(userid);
    	templateParameterList.add(periodNum);
    	
    	//*********************本级补出 END*******************
    	
    	//**********************加上下级补进的************START****************
    	if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){
    		finalSql.append(" UNION ALL "); 
    		finalSql.append(" select a.type_code AS typeCode,sum(a.money*a." + rateUser + "/100) as totalMoney,a.ATTRIBUTE AS splitAttribute from tb_replenish a " +
    				" where a." + myColumn + " = ? and a.win_state in(0,6,7) and a.periods_num=? ");
    		
    		if(Constant.LOTTERY_TYPE_GDKLSF.equals(playType)){
        		scanTableList = Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME_LIST;
        		finalSql.append(" and a.type_code like 'GDKLSF_STRAIGHTTHROUGH%' ");
        	}else{
        		scanTableList = Constant.NC_TABLE_LIST;
        		finalSql.append(" and a.type_code like 'NC_STRAIGHTTHROUGH%' ");
        	}
        	finalSql.append("group by a.type_code,a.ATTRIBUTE ");
    		
    		templateParameterList.add(userid);
    		templateParameterList.add(periodNum);
    		//**********************查询下级补进的************END****************
    	}
    	//************处理补货*************END********
    	
    	finalSql.append(") group by typeCode,splitAttribute "); //这一层统计出所有玩法的投注总额(已经减去补出和加入下级补入的)
    	
    	finalSql.append(") b,tb_play_type c where b.typeCode = c.type_code "); //这一层把统计类型与TB_PALY_TYPE的自动补货类弄匹配
    	
    	finalSql.append(")d group by d.autoReplenishType order by d.autoReplenishType ") ;
    	
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	
    	List<AutoReplenishSetVO> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new totalItemMapperZ());
    	return reportList;
    	
    }
    
    @Override
    public List<AutoReplenishSetVO> queryTrueMoneyForSignal(String periodNum,Long userid,String userType,String myColumn,
    		String rateUser,String typeCode,String[] tableList,String attribute,String scheme)
    {
    	if(StringUtils.isNotBlank(scheme)){
    		scheme=scheme+".";
    	}
    	StringBuffer finalSql=new StringBuffer();
    	//如果是广东连码和农场，就要再选上attribute
    	if(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME.equals(tableList[0]) || Constant.NC_TABLE_NAME.equals(tableList[0]) && null!=attribute){
    		finalSql.append("SELECT typeCode,sum(totalMoney) AS totalMoney,attribute FROM (");//这一层统计出所有玩法的投注总额(已经减去补出和加入下级补入的)
    	}else{
    		finalSql.append("SELECT typeCode,sum(totalMoney) AS totalMoney FROM (");//这一层统计出所有玩法的投注总额(已经减去补出和加入下级补入的)
    	}
    	
    	List<Object> finalParameterList=new ArrayList<Object>();
    	List<Object> templateParameterList=new ArrayList<Object>();
    	StringBuffer sqTemplatelBuffer=new StringBuffer();	
    	
    	
    	if(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME.equals(tableList[0]) || Constant.NC_TABLE_NAME.equals(tableList[0]) && null!=attribute){
    		sqTemplatelBuffer.append(" select a.type_code AS typeCode, sum(a.money * " + rateUser + "/100) AS totalMoney,a.SPLIT_ATTRIBUTE AS attribute ");
    	}else{
    		sqTemplatelBuffer.append(" select a.type_code AS typeCode, sum(a.money * " + rateUser + "/100) AS totalMoney ");
    	}
    	
    	
    	sqTemplatelBuffer.append("" +
    			" from {TableName}  a ");
    			//" (select t1.id,t1.account,t1.chs_name,t1.user_type from tb_frame_manager_staff t1 union select  t2.id,t2.account,t2.chs_name,t2.user_type from tb_frame_member_staff t2 ) b ");
    	
    	if(ManagerStaff.USER_TYPE_CHIEF.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.chiefstaff=? ");
    	}
    	
    	else if(ManagerStaff.USER_TYPE_BRANCH.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.branchstaff=? ");
    	}
    	else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.stockholderstaff=? ");
    	}
    	else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.genagenstaff=? ");
    	}
    	else if(ManagerStaff.USER_TYPE_AGENT.equals(userType))
    	{
    		sqTemplatelBuffer.append(" where  a.agentstaff=? ");
    	}	
    	
    	sqTemplatelBuffer.append(" and a.periods_num=? and a.type_code=? ");
    	
    	if(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME.equals(tableList[0]) || Constant.NC_TABLE_NAME.equals(tableList[0]) && null!=attribute){
    		sqTemplatelBuffer.append(" and a.SPLIT_ATTRIBUTE=? ");
    		sqTemplatelBuffer.append(" group by a.type_code,a.SPLIT_ATTRIBUTE ");
    	}else{
    		sqTemplatelBuffer.append(" group by a.type_code ");
    	}
    	
    	String[] scanTableList = tableList;
    	
    	for (int i = 0; i < scanTableList.length; i++) {
    		finalSql.append(sqTemplatelBuffer.toString().replace("{TableName}", scheme+scanTableList[i]));
    		if(i!=scanTableList.length-1)
    			finalSql.append(" union all ");
    		templateParameterList.add(userid);
    		templateParameterList.add(periodNum);
    		templateParameterList.add(typeCode);
    		if(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME.equals(tableList[0]) || Constant.NC_TABLE_NAME.equals(tableList[0]) && null!=attribute){
    			templateParameterList.add(attribute);
        	}	
    	}
    	
    	//************处理补货*************START********
    	finalSql.append(" union all ");
    	
    	//*********************本级补出 START*******************
    	if(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME.equals(tableList[0]) || Constant.NC_TABLE_NAME.equals(tableList[0]) && null!=attribute){
    		finalSql.append(" select a.type_code AS typeCode,-sum(a.money) as totalMoney,a.attribute ");
    	}else{
    		finalSql.append(" select a.type_code AS typeCode,-sum(a.money) as totalMoney ");
    	}
    	
		finalSql.append(" from "+scheme+"tb_replenish a " +
    			"where a.replenish_user_id=?  and a.win_state in(0,6,7) and a.periods_num=? and a.type_code=? ");
    	
    	if(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME.equals(tableList[0]) || Constant.NC_TABLE_NAME.equals(tableList[0]) && null!=attribute){
    		finalSql.append(" and a.attribute=? ");
    		finalSql.append(" group by a.type_code,a.attribute ");
    	}else{
    		finalSql.append(" group by a.type_code ");
    	}
    	
    	templateParameterList.add(userid);
    	templateParameterList.add(periodNum);
    	templateParameterList.add(typeCode);
    	if(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME.equals(tableList[0]) || Constant.NC_TABLE_NAME.equals(tableList[0]) && null!=attribute){
			templateParameterList.add(attribute);
    	}
    	
    	//*********************本级补出 END*******************
    	
    	//**********************加上下级补进的************START****************
    	if(!ManagerStaff.USER_TYPE_AGENT.equals(userType)){
    		finalSql.append(" UNION ALL "); 
    		if(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME.equals(tableList[0]) || Constant.NC_TABLE_NAME.equals(tableList[0]) && null!=attribute){
        		finalSql.append(" select a.type_code AS typeCode,sum(a.money*a." + rateUser + "/100) as totalMoney,a.attribute ");
        	}else{
        		finalSql.append(" select a.type_code AS typeCode,sum(a.money*a." + rateUser + "/100) as totalMoney ");
        	}
    		
    		finalSql.append("from "+scheme+"tb_replenish a " +
    				" where a." + myColumn + " = ? and a.win_state in(0,6,7) and a.periods_num=? and a.type_code=? ");
    		
    		if(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME.equals(tableList[0]) || Constant.NC_TABLE_NAME.equals(tableList[0]) && null!=attribute){
    			finalSql.append(" and a.attribute=? ");
    			finalSql.append(" group by a.type_code,a.attribute ");
        	}else{
        		finalSql.append(" group by a.type_code ");
        	}
    		
    		templateParameterList.add(userid);
    		templateParameterList.add(periodNum);
    		templateParameterList.add(typeCode);
    		if(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME.equals(tableList[0]) || Constant.NC_TABLE_NAME.equals(tableList[0]) && null!=attribute){
    			templateParameterList.add(attribute);
        	}
    		//**********************查询下级补进的************END****************
    	}
    	//************处理补货*************END********
    	if(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME.equals(tableList[0]) || Constant.NC_TABLE_NAME.equals(tableList[0]) && null!=attribute){
    		finalSql.append(") group by typeCode,attribute ");
    	}else{
    		finalSql.append(") group by typeCode "); //这一层统计出所有玩法的投注总额(已经减去补出和加入下级补入的)
    	}
    	
    	
    	finalParameterList.addAll(Lists.newArrayList(templateParameterList));
    	List<AutoReplenishSetVO> reportList= new ArrayList<AutoReplenishSetVO>();
    	
    	if(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME.equals(tableList[0]) || Constant.NC_TABLE_NAME.equals(tableList[0]) && null!=attribute){
    		reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new trueMoneyAttributeForSignalItemMapperZ());
    	}else{
    		reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new trueMoneyForSignalItemMapperZ());
    	}
    	
    	return reportList;
    	
    }
    
    @Override
    public List<AutoReplenishSetVO> queryLowestQuotasByAutoType(Long userid)
    {
    	StringBuffer finalSql=new StringBuffer();
    	List<Object> finalParameterList=new ArrayList<Object>();
    	finalSql.append(" select b.lowest_quotas AS lowestMoney,d.auto_replenish_type AS autoReplenishType " +
		    			" from tb_user_commission_default b,tb_play_type d " +
		    			" where b.user_id =? and b.play_final_type = d.commission_type ");
    	
    	finalParameterList.add(userid);
    	List<AutoReplenishSetVO> reportList=jdbcTemplate.query(finalSql.toString(),finalParameterList.toArray(),new lowestItemMapper());
    	return reportList;
    }
    
    @Override
    public void updateReplenishAutoSetByUser(String shopsID,Long userID,String userType,String state){
		String sql=" UPDATE tb_replenish_auto SET state=? " +
		    			" WHERE create_user =? AND shops_id =? AND create_user_type=?";
    	
    	Object[] parameters=new Object[]{state,userID,shopsID,userType};
    	jdbcTemplate.update(sql,parameters);
    }
    
    @Override
    public void updateReplenishAutoSetForClose(String shopsID,String userIDList){
    	String sql=" UPDATE tb_replenish_auto SET MONEY_LIMIT=0, MONEY_REP=0,state='0' " +
    			" WHERE create_user in("+userIDList+") AND shops_id =? ";
    	
    	Object[] parameters=new Object[]{shopsID};
    	jdbcTemplate.update(sql,parameters);
    }
    
    @Override
    public List<Long> queryReplenishAutoSetUserList(Long userID,String userType){
    	String sql="";
    	Object[] parameters=new Object[]{};
    	/*if(ManagerStaff.USER_TYPE_BRANCH.equals(userType)){
    		sql="select manager_staff_id from tb_stockholder_staff_ext b where b.parent_staff=? union all "+
				"select manager_staff_id from tb_gen_agent_staff_ext c where c.branch_staff=? union all "+
				"select manager_staff_id from tb_agent_staff_ext c where c.branch_staff=? ";
    		parameters=new Object[]{userID,userID,userID};
    	}else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userType)){
    		sql="select manager_staff_id from tb_gen_agent_staff_ext c where c.parent_staff=? union all "+
				"select manager_staff_id from tb_agent_staff_ext c where c.stockholder_staff=? ";
    		parameters=new Object[]{userID,userID};
    	}else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userType)){
    		sql="select manager_staff_id from tb_agent_staff_ext c where c.parent_staff=? ";
    		parameters=new Object[]{userID};
    	}*/
    	sql="select id  from tb_frame_manager_staff ts START WITH id=? CONNECT BY PRIOR id = ts.parent_staff_qry ";
		parameters=new Object[]{userID};
		
    	List<Long> list=jdbcTemplate.query(sql,parameters,new replenishUserListItemMapper());
    	return list;
    }
    
    @Override
    public List<AutoReplenishSetVO> queryReplenishAutoSet(List<Long> userList,String shopsID,String typeCode){
    	StringBuffer finalSql=new StringBuffer();
    	finalSql.append(" select a.create_user_type as userType,a.money_limit as moneyLimit " +
    			" from tb_replenish_auto a,tb_play_type t " +
				   " where trim(a.shops_id)=? " +
				   " and t.type_code=? " +
				   " and a.state=1 " +
				   " and a.type_code=t.auto_replenish_type" +
				   " and a.create_user in(");
        
    	for(int i=0;i<userList.size();i++){
    		if(i!=userList.size()-1){
    			finalSql.append("'"+userList.get(i)+"',");
    		}else{
    			finalSql.append("'"+userList.get(i)+"')");
    		}
    	}
    	
    	Object[] parameters=new Object[]{shopsID,typeCode};
    	List<AutoReplenishSetVO> reportList=jdbcTemplate.query(finalSql.toString(),parameters,new moneyLimitItemMapper());
    	return reportList;
    	
    }
    
    @Override
    public List<AutoReplenishSetVO> queryReplenishAutoSet(List<Long> userList,String shopsID,String typeCode,String scheme){
    	StringBuffer finalSql=new StringBuffer();
    	if(StringUtils.isNotBlank(scheme)){
    		scheme=scheme+".";
    	}
    	finalSql.append(" select a.create_user_type as userType,a.money_limit as moneyLimit " +
    			" from "+scheme+"tb_replenish_auto a,tb_play_type t " +
				   " where trim(a.shops_id)=? " +
				   " and t.type_code=? " +
				   " and a.state=1 " +
				   " and a.type_code=t.auto_replenish_type" +
				   " and a.create_user in(");
        
    	for(int i=0;i<userList.size();i++){
    		if(i!=userList.size()-1){
    			finalSql.append("'"+userList.get(i)+"',");
    		}else{
    			finalSql.append("'"+userList.get(i)+"')");
    		}
    	}
    	
    	Object[] parameters=new Object[]{shopsID,typeCode};
    	List<AutoReplenishSetVO> reportList=jdbcTemplate.query(finalSql.toString(),parameters,new moneyLimitItemMapper());
    	return reportList;
    }
    
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}

class replenishUserListItemMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return rs.getLong("id");
	}
}
class totalItemMapperZ implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AutoReplenishSetVO reportInfo = new AutoReplenishSetVO();
		reportInfo.setAutoReplenishType(rs.getString("autoReplenishType"));
		reportInfo.setTotalMoney(rs.getBigDecimal("totalMoney"));
		
		return reportInfo;
	}
}
class trueMoneyForSignalItemMapperZ implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AutoReplenishSetVO reportInfo = new AutoReplenishSetVO();
		reportInfo.setTypeCode(rs.getString("typeCode"));
		reportInfo.setTotalMoney(rs.getBigDecimal("totalMoney"));
		
		return reportInfo;
	}
}
class trueMoneyAttributeForSignalItemMapperZ implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AutoReplenishSetVO reportInfo = new AutoReplenishSetVO();
		reportInfo.setTypeCode(rs.getString("typeCode"));
		reportInfo.setTotalMoney(rs.getBigDecimal("totalMoney"));
		reportInfo.setAttribute(rs.getString("attribute"));
		
		return reportInfo;
	}
}
class lowestItemMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AutoReplenishSetVO reportInfo = new AutoReplenishSetVO();
		reportInfo.setAutoReplenishType(rs.getString("autoReplenishType"));
		reportInfo.setLowestMoney(rs.getInt("lowestMoney"));
		
		return reportInfo;
	}
}
class moneyLimitItemMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AutoReplenishSetVO reportInfo = new AutoReplenishSetVO();
		reportInfo.setUserType(rs.getString("userType"));
		reportInfo.setMoneyLimit(rs.getInt("moneyLimit"));
		
		return reportInfo;
	}
}
