package com.npc.lottery.user.dao.hibernate;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.common.Constant;
import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.user.dao.interf.ICommonUserDao;

public class CommonUserDao implements ICommonUserDao{
	private JdbcTemplate jdbcTemplate;
	
	
	public void updateBranchBelowAvailableCreditLine(String managerAccount)
	{
		String updSql="update tb_branch_staff_ext a " +
				"set a.available_credit_line=0," +
				"a.total_credit_line=0 where exists" +
				"( select id  from tb_frame_manager_staff ts where user_type=3 and a.manager_staff_id=ts.id " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry)";
		Object[] args = new Object[] {managerAccount};
		
		jdbcTemplate.update(updSql, args);
		
		
		
	}
	public void updateStockHolderBelowAvailableCreditLine(String managerAccount)
	{
		/*String updSql="update tb_frame_manager_staff ms set flag='0' " +
				" where exists" +
				" (select id from tb_frame_manager_staff ts where id<>? and ms.id=ts.id " +
				" START WITH id=? CONNECT BY PRIOR id = ts.parent_staff_qry)";
		Object[] args = new Object[] {managerId,managerId};
		
		jdbcTemplate.update(updSql, args);*/
		String updSql="update tb_stockholder_staff_ext a " +
				"set a.available_credit_line=0," +
				"a.total_credit_line=0 where exists" +
				"( select id  from tb_frame_manager_staff ts where user_type=4 and a.manager_staff_id=ts.id " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry)";
		Object[] args = new Object[] {managerAccount};
		
		jdbcTemplate.update(updSql, args);
		
		
	}
	public void updateGenAgentBelowAvailableCreditLine(String managerAccount)
	{
		String updSql="update tb_gen_agent_staff_ext a " +
				"set a.available_credit_line=0," +
				"a.total_credit_line=0 where exists" +
				"( select id  from tb_frame_manager_staff ts where user_type=5 and a.manager_staff_id=ts.id " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry)";
		Object[] args = new Object[] {managerAccount};
		
		jdbcTemplate.update(updSql, args);
		
	}
	public void updateAgentBelowAvailableCreditLine(String managerAccount)
	{
		String updSql="update tb_agent_staff_ext a " +
				"set a.available_credit_line=0," +
				"a.total_credit_line=0 where exists" +
				"( select id  from tb_frame_manager_staff ts where user_type=6 and a.manager_staff_id=ts.id " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry)";
		Object[] args = new Object[] {managerAccount};
		
		jdbcTemplate.update(updSql, args);
	}
	public void updateMemberBelowAvailableCreditLine(String managerAccount)
	{
		
		String updSql="update tb_member_staff_ext a set a.available_credit_line=0,a.total_credit_line=0 " +
				" where exists(select id  from tb_frame_manager_staff ts " +
				" where  a.parent_staff=ts.id and ts.account=? )";
		Object[] args = new Object[] {managerAccount};
		
		jdbcTemplate.update(updSql, args);
		
	}
	
	
	public void updateStockHolderBelowRate(String managerAccount)
	{
		String updSql="update tb_stockholder_staff_ext a " +
				"set a.branch_rate=0 " +
				" where exists" +
				"( select id  from tb_frame_manager_staff ts where user_type=4 and a.manager_staff_id=ts.id " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry)";
		Object[] args = new Object[] {managerAccount};
		
		jdbcTemplate.update(updSql, args);
		
	}
	public void updateGenAgentBelowRate(String managerAccount)
	{
		String updSql="update tb_gen_agent_staff_ext a " +
				"set a.shareholder_rate=0 " +
				" where exists" +
				"( select id  from tb_frame_manager_staff ts where user_type=5 and a.manager_staff_id=ts.id " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry)";
		Object[] args = new Object[] {managerAccount};
		
		jdbcTemplate.update(updSql, args);
		
	}
	public void updateAgentBelowRate(String managerAccount)
	{
		
		String updSql="update tb_agent_staff_ext a " +
				"set a.gen_agent_rate=0 " +
				" where exists" +
				"( select id  from tb_frame_manager_staff ts where user_type=6 and a.manager_staff_id=ts.id " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry)";
		Object[] args = new Object[] {managerAccount};
		
		jdbcTemplate.update(updSql, args);
	}
	public void updateMemberBelowRate(String managerAccount)
	{
		String updSql="update tb_member_staff_ext a " +
				" set a.rate=0 " +
				" where exists(select id  from tb_frame_manager_staff ts " +
				" where  a.parent_staff=ts.id and ts.account=? )";
		Object[] args = new Object[] {managerAccount};
		
		jdbcTemplate.update(updSql, args);
		
	}
	
	public void updateBranchBelowReplenishment(String managerAccount)
	{
		String updSql="update tb_branch_staff_ext a " +
				"set a.replenishment=1 " +
				" where exists" +
				"( select id  from tb_frame_manager_staff ts where user_type=3 and a.manager_staff_id=ts.id " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry)";
		Object[] args = new Object[] {managerAccount};
		
		jdbcTemplate.update(updSql, args);
		
	}
	public void updateStockHolderBelowReplenishment(String managerAccount)
	{
		String updSql="update tb_stockholder_staff_ext a " +
				"set a.replenishment=1 " +
				" where exists" +
				"( select id  from tb_frame_manager_staff ts where user_type=4 and a.manager_staff_id=ts.id " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry)";
		Object[] args = new Object[] {managerAccount};
		
		jdbcTemplate.update(updSql, args);
	}
	public void updateGenAgentBelowReplenishment(String managerAccount)
	{
		String updSql="update tb_gen_agent_staff_ext a " +
				"set a.replenishment=1 " +
				" where exists" +
				"( select id  from tb_frame_manager_staff ts where user_type=5 and a.manager_staff_id=ts.id " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry)";
		Object[] args = new Object[] {managerAccount};
		
		jdbcTemplate.update(updSql, args);
		
	}
	public void updateAgentBelowReplenishment(String managerAccount)
	{
		String updSql="update tb_agent_staff_ext a " +
				"set a.replenishment=1 " +
				" where exists" +
				"( select id  from tb_frame_manager_staff ts where user_type=6 and a.manager_staff_id=ts.id " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry)";
		Object[] args = new Object[] {managerAccount};
		
		jdbcTemplate.update(updSql, args);
		
	}
	//public void updateMemberBelowReplenishment(Long AgentId);
	
	//对操作的本级以下的帐号的修改状态
	public void updateManagerStatus(String managerAccount,String status,String managerType)
	{
		String updSql="update tb_frame_manager_staff a " +
				"set a.flag=?,a.pre_flag=? " +
				" where exists" +
				"( select id  from tb_frame_manager_staff ts where  a.id=ts.id and ts.account!=? " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry) and a.flag=0 or " +
				"		exists( select id  from tb_frame_manager_staff ts where  a.id=ts.id and ts.account!=? " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry) and a.pre_flag<=? ";
		Object[] args = new Object[] {status,managerType,managerAccount,managerAccount,managerAccount,managerAccount,managerType};
		
		jdbcTemplate.update(updSql, args);
		
	}
	
	public void updateMemberStatus(String managerAccount,String status,String managerType)
	{
		String updSql="update tb_frame_member_staff a " +
				" set a.flag=?,a.pre_flag=?  " +
				" where exists(select id from (select id  from tb_frame_manager_staff ts " +
				"START WITH ts.account=? CONNECT BY PRIOR id = ts.parent_staff_qry) tm where a.parent_staff_qry=tm.id ) and a.flag=0 or " +
				"		exists(select id from (select id  from tb_frame_manager_staff ts " +
				"START WITH ts.account=? CONNECT BY PRIOR id = ts.parent_staff_qry) tm where a.parent_staff_qry=tm.id ) and a.pre_flag<=? ";
		Object[] args = new Object[] {status,managerType,managerAccount,managerAccount,managerType};
		
		jdbcTemplate.update(updSql, args);
	}
	
	//查询符合相关条件的用户列表
	public List<UserVO> queryManagerStatus(String managerAccount,String status,String managerType)
	{
		String sql="select a.account as account,a.user_type as userType from tb_frame_manager_staff a " +
				" where exists" +
				"( select id  from tb_frame_manager_staff ts where  a.id=ts.id and ts.account!=? " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry) and a.flag=0 or " +
				"		exists( select id  from tb_frame_manager_staff ts where  a.id=ts.id and ts.account!=? " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry) and a.pre_flag<=? ";
		Object[] args = new Object[] {managerAccount,managerAccount,managerAccount,managerAccount,managerType};
		
		List<UserVO> reportList=jdbcTemplate.query(sql.toString(),args,new UserMapper());
        return reportList;
		
	}
	
	//查询符合相关条件的用户列表
	public List<UserVO> queryMemberStatus(String managerAccount,String status,String managerType)
	{
		String sql="select a.account as account, a.user_type as userType from tb_frame_member_staff a " +
				" where exists(select id from (select id  from tb_frame_manager_staff ts " +
				"START WITH ts.account=? CONNECT BY PRIOR id = ts.parent_staff_qry) tm where a.parent_staff_qry=tm.id ) and a.flag=0 or " +
				"		exists(select id from (select id  from tb_frame_manager_staff ts " +
				"START WITH ts.account=? CONNECT BY PRIOR id = ts.parent_staff_qry) tm where a.parent_staff_qry=tm.id ) and a.pre_flag<=? ";
		Object[] args = new Object[] {managerAccount,managerAccount,managerType};
		
		List<UserVO> reportList=jdbcTemplate.query(sql.toString(),args,new UserMapper());
        return reportList;
	}
	
	//对操作的本级帐号的修改状态--管理
	public void updateManagerStatusForMain(String managerAccount,String status,String managerType)
	{
		String updSql="update tb_frame_manager_staff a set  a.flag=?,a.pre_flag=?  where account=? ";
		Object[] args = new Object[] {status,managerType,managerAccount};
		
		jdbcTemplate.update(updSql, args);
		
	}
	
	//对操作的本级帐号的修改状态--会员
	public void updateMemberStatusForMain(String managerAccount,String status,String managerType)
	{
		String updSql="update tb_frame_member_staff a set  a.flag=?,a.pre_flag=?  where account=? ";
		Object[] args = new Object[] {status,managerType,managerAccount};
		
		jdbcTemplate.update(updSql, args);
		
	}
	
	/*public void updateManagerUsed(String managerAccount,String status)
	{
		//暂时 用comments 字段存储用户当前状态被更新前得状态
		String updSql="update tb_frame_manager_staff a " +
				"set  a.flag=a.comments " +
				" where exists" +
				"( select id  from tb_frame_manager_staff ts where  a.id=ts.id " +
				" START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry) and a.comments is not null ";
		Object[] args = new Object[] {managerAccount};
		
		jdbcTemplate.update(updSql, args);
		
	}
	
	public void updateMemberUsed(String managerAccount,String status)
	{
		
		String updSql="update tb_frame_member_staff a " +
				" set  a.flag=a.comments  " +
				" where exists" +
				"(select id from (select id  from tb_frame_manager_staff ts " +
				" START WITH ts.account=? CONNECT BY PRIOR id = ts.parent_staff_qry) tm where a.parent_staff_qry=tm.id  )  and a.comments is not null ";
		Object[] args = new Object[] {managerAccount};
		
		jdbcTemplate.update(updSql, args);
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void deleteMangerStaff(Long userId){
		String updSql="delete from tb_frame_manager_staff where id=?";
		Object[] args = new Object[] {userId};
		jdbcTemplate.update(updSql, args);
	}
	
	public void deleteUserOutReplenishStaff(Long userId){
		String updSql="delete from tb_out_replenish_staff_ext where MANAGER_STAFF_ID=?";
		Object[] args = new Object[] {userId};
		jdbcTemplate.update(updSql, args);
	}
	
	public void updateStockHolderBelowForbid(Long BranchId)
	{
		
	}
	public void updateGenAgentBelowForbid(Long stockHoderId)
	{
		
	}
	public void updateAgentBelowForbid(Long genAentId)
	{
		
	}
	public void updateMemberBelowForbid(Long AgentId)
	{
		
	}
	
	public void updateStockHolderBelowFreeze(Long BranchId)
	{
		
	}
	public void updateGenAgentBelowFreeze(Long stockHoderId)
	{
		
	}
	public void updateAgentBelowFreeze(Long genAentId)
	{
		
	}
	public void updateMemberBelowFreeze(Long AgentId)
	{
		
	}
	
	public List<ManagerUser> queryBelowManagerByUserId(String userId,String type)
	{
	    String sql="select id,account,flag,user_type as userType  " +
                "from tb_frame_manager_staff ts where user_type=? " +
                "START WITH id=? CONNECT BY PRIOR id = ts.parent_staff_qry ";
        Object[] parameters = new Object[] { type,userId };
        List<ManagerUser> userlist = (List<ManagerUser>)jdbcTemplate.query(sql, parameters,new ManagerUserItemMapper());
        return userlist;
	    
	    
	}
	
	public List<ManagerUser> queryBelowManager(String account,String type)
	{
		
		String sql="select id,account,flag,user_type as userType  " +
				"from tb_frame_manager_staff ts where user_type=? " +
				"START WITH account=? CONNECT BY PRIOR id = ts.parent_staff_qry ";
		Object[] parameters = new Object[] { type,account };
		List<ManagerUser> userlist = (List<ManagerUser>)jdbcTemplate.query(sql, parameters,new ManagerUserItemMapper());
		return userlist;
	}
	
	public Map queryManagerTreeCount(String account,String type,String searchUserStatus)
	{
	String sql=" select  user_type as userType,count(*) cnt " +
			" from (" +
			"  select id,account,user_type,parent_staff_qry from  tb_frame_manager_staff where flag=? " +
			" union all select id,account,user_type,parent_staff_qry from tb_frame_member_staff where flag=?) " +
			"START WITH account=? and user_type=? CONNECT BY PRIOR id = parent_staff_qry group by user_type";
	     Object[] parameters = new Object[] {searchUserStatus,searchUserStatus, account,type };
         
	     return convertListToMap(jdbcTemplate.queryForList(sql,parameters));
	}
	
	public Long queryStockHolderCredit(Long id)
	{
		String sql="select sum(a.total_credit_line) from tb_stockholder_staff_ext a " +
				" where exists ( select id  from tb_frame_manager_staff ts " +
				" where user_type=4 and a.manager_staff_id=ts.id START WITH id=? " +
				"CONNECT BY PRIOR id = ts.parent_staff_qry)";
       Object[] args = new Object[] {id};
		
		return jdbcTemplate.queryForLong(sql, args);
		
		
	}
	public Long queryGenAgentCredit(Long id)
	{
		String sql="select sum(a.total_credit_line) from tb_gen_agent_staff_ext a " +
				" where exists ( select id  from tb_frame_manager_staff ts " +
				" where user_type=5 and a.manager_staff_id=ts.id START WITH id=? " +
				"CONNECT BY PRIOR id = ts.parent_staff_qry)";
       Object[] args = new Object[] {id};
		
		return jdbcTemplate.queryForLong(sql, args);
		
	}
	public Long queryAgentCredit(Long id)
	{
		String sql="select sum(a.total_credit_line) from tb_agent_staff_ext a " +
				" where exists ( select id  from tb_frame_manager_staff ts " +
				" where user_type=6 and a.manager_staff_id=ts.id START WITH id=? " +
				"CONNECT BY PRIOR id = ts.parent_staff_qry)";
       Object[] args = new Object[] {id};
		
		return jdbcTemplate.queryForLong(sql, args);
	}
	public Long queryMemberCredit(Long id,String type)
	{
         String userCol="";	
		if(ManagerStaff.USER_TYPE_BRANCH.equals(type))
		{
			userCol="branch_staff";
		}
		else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(type))
		{
			userCol="stockholder_staff";	
		}
		else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(type))
		{
			userCol="gen_agen_staff";
		}
		else if(ManagerStaff.USER_TYPE_AGENT.equals(type))
		{
			userCol="agent_staff";
		}
		String sql="select sum(a.total_credit_line) from tb_member_staff_ext a  where "+userCol+" = ?";
		
	
       Object[] args = new Object[] {id};
		
		return jdbcTemplate.queryForLong(sql,args);
	}
	
	
	
	
	
	 private Map<String,String> convertListToMap(List treeList)
	    {
	    	 Map<String,String> retmap=new HashMap<String,String>();
	    	for (int i = 0; i < treeList.size(); i++) {
	    	Map	userTypeCount=(Map)treeList.get(i);
	    	String key=(String)userTypeCount.get("userType");
	    	String value=((BigDecimal)userTypeCount.get("cnt")).toString();
	    		
	    		retmap.put(key,value);
			}
	    	return retmap;
	    	
	    }
	 public boolean queryUserTreeHasBet(Long userId,String type)
	 {
		boolean ret=false;
		String userCol="";
		
		if(ManagerStaff.USER_TYPE_BRANCH.equals(type))
		{
			userCol="branchstaff";
		}
		else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(type))
		{
			userCol="stockholderstaff";	
		}
		else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(type))
		{
			userCol="genagenstaff";
		}
		else if(ManagerStaff.USER_TYPE_AGENT.equals(type))
		{
			userCol="agentstaff";
		}
		else if(MemberStaff.USER_TYPE_MEMBER.equals(type))
		{
			userCol="betting_user_id";
		}
		String selsql="SELECT COUNT(BETTING_DATE) FROM " ;
		
		String where=" WHERE  "+userCol+" = "+userId+" AND "
				+" BETTING_DATE BETWEEN TO_DATE(TO_CHAR((SYSDATE- INTERVAL '2' HOUR-INTERVAL '30' MINUTE),'YYYYMMDD'),'YYYYMMDD')+TO_DSINTERVAL('0 02:30:00') "
				+" AND  TO_DATE(TO_CHAR((SYSDATE- INTERVAL '2' HOUR-INTERVAL '30' MINUTE),'YYYYMMDD'),'YYYYMMDD')+1+TO_DSINTERVAL('0 02:30:00') "
				+" AND ROWNUM=1";
		
		// 扫描 北京历史表
		String sql1 = selsql + Constant.BJSC_HIS_TABLE_NAME + where;
		int count1 = jdbcTemplate.queryForInt(sql1);
		if (count1 > 0) {
			return true;
		}
		// 扫描重庆历史表
		String sql2 = selsql + Constant.CQSSC_HIS_TABLE_NAME + where;
		int count2 = jdbcTemplate.queryForInt(sql2);
		if (count2 > 0) {
			return true;
		}
		// 扫描广东历史表
		String sql3 = selsql + Constant.GDKLSF_HIS_TABLE_NAME + where;
		int count3 = jdbcTemplate.queryForInt(sql3);
		if (count3 > 0) {
			return true;
		}
		// 扫描江苏历史表
		String sql4 = selsql + Constant.K3_HIS_TABLE_NAME + where;
		int count4 = jdbcTemplate.queryForInt(sql4);
		if (count4 > 0) {
			return true;
		}
		// 扫描NC历史表
		String sql5 = selsql + Constant.NC_HIS_TABLE_NAME + where;
		int count5 = jdbcTemplate.queryForInt(sql5);
		if (count5 > 0) {
			return true;
		}

		// 扫描广东投注
		for (int i = 0; i < Constant.GDKLSF_TABLE_LIST.length; i++) {
			String sql = selsql + Constant.GDKLSF_TABLE_LIST[i] + where;
			int count = jdbcTemplate.queryForInt(sql);
			if (count > 0) {
				return true;
			}
		}
		// 扫描重庆投注
		for (int j = 0; j < Constant.CQSSC_TABLE_LIST.length; j++) {
			String sql = selsql + Constant.CQSSC_TABLE_LIST[j] + where;
			int count = jdbcTemplate.queryForInt(sql);
			if (count > 0) {
				return true;
			}
		}
		// 扫描北京投注
		for (int j = 0; j < Constant.BJSC_TABLE_LIST.length; j++) {
			String sql = selsql + Constant.BJSC_TABLE_LIST[j] + where;
			int count = jdbcTemplate.queryForInt(sql);
			if (count > 0) {
				return true;
			}
		}

		// 扫描江苏投注表
		for (int j = 0; j < Constant.K3_TABLE_LIST.length; j++) {
			String sql = selsql + Constant.K3_TABLE_LIST[j] + where;
			int count = jdbcTemplate.queryForInt(sql);
			if (count > 0) {
				return true;
			}
		}
		
		// 扫描NC投注表
		for (int j = 0; j < Constant.NC_TABLE_LIST.length; j++) {
			String sql = selsql + Constant.NC_TABLE_LIST[j] + where;
			int count = jdbcTemplate.queryForInt(sql);
			if (count > 0) {
				return true;
			}
		}

		return ret;
	 }
	
	 public int queryBelowMaxRate(Long managerId,String type)
	 {
		 
		 String chiefRate="chief_rate";
		 String branchRate="branch_rate";
		 String shareRate="shareholder_rate";
		 String genRate="gen_agent_rate";
		 String agenRate="rate";
		 if(ManagerStaff.USER_TYPE_BRANCH.equals(type))
		 {
			 chiefRate="0"; 
		 }
		 else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(type))
		 {
			 chiefRate="0";
			 branchRate="0";
		 }
		 else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(type))
		 {
			 chiefRate="0";
			 branchRate="0"; 
			 shareRate="0";
		 }
		 else if(ManagerStaff.USER_TYPE_AGENT.equals(type))
		 {
			 chiefRate="0";
			 branchRate="0"; 
			 shareRate="0";
			 genRate="0";
		 }
		 else if(MemberStaff.USER_TYPE_MEMBER.equals(type))
		 {
			 chiefRate="0";
			 branchRate="0"; 
			 shareRate="0";
			 genRate="0"; 
			 agenRate="0";
		 }
		 String sql="select rate from ( select CONNECT_BY_ISLEAF as leaf,sys_connect_by_path(rate,'+') rate ,id,user_type ,parent_staff " +
		 		" from "+
                "( "+
		 		
                 "  select a.manager_staff_id as id, 3 as user_type, "+chiefRate+" rate,a.parent_staff from tb_branch_staff_ext a "+
                 "  union all "+
                 "  select b.manager_staff_id as id ,4 as user_type,"+branchRate+" rate,b.parent_staff from tb_stockholder_staff_ext b "+
                 "  union all "+
                 "  select c.manager_staff_id as id ,5 as user_type,"+shareRate+" rate,c.parent_staff from tb_gen_agent_staff_ext c "+
                 "  union all "+
                 "  select d.manager_staff_id as id ,6 as user_type ,"+genRate+" rate ,d.parent_staff from tb_agent_staff_ext d "+
                 "  union all "+
                 "  select e.member_staff_id as id,9 as user_type ,e.rate rate,e.parent_staff from tb_member_staff_ext e " +
                
                 " )   "+
                 " START WITH id=? and user_type=? CONNECT BY PRIOR id = parent_staff ) where leaf=1";
		 Object[] args = new Object[] {managerId,type}; 
		List<Map<String,Object>> rateList=jdbcTemplate.queryForList(sql,args);
		int maxRate=0;
		for (int i = 0; i < rateList.size(); i++) {
			
			String rate=(String)rateList.get(i).get("rate");
			String [] rates=StringUtils.split(rate, "+");
			int totalRate=0;
			for (int j = 0; j < rates.length; j++) {
				String levelRate=rates[j];
				if(!GenericValidator.isInt(levelRate))
					levelRate="0";
				totalRate=totalRate+Integer.parseInt(levelRate) ;					
			}
			if(totalRate>maxRate)
				maxRate=totalRate;
			
		}
		 return maxRate;
		 
	 }
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	class ManagerUserItemMapper implements RowMapper {
		
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			ManagerUser managerUser = new ManagerUser();
			
			managerUser.setAccount(rs.getString("account"));
			managerUser.setFlag(rs.getString("flag"));
			managerUser.setID(rs.getLong("id"));
			managerUser.setUserType(rs.getString("userType"));

			return managerUser;
		}
		
	}
	
	class UserMapper implements RowMapper {  	  
		  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			   UserVO item = new UserVO();  
			   item.setAccount(rs.getString("account"));	   
			   item.setUserType(rs.getString("userType"));
			   return item;  
		  }  	  
	}
	
}
