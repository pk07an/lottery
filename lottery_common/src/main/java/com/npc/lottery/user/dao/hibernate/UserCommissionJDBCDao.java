package com.npc.lottery.user.dao.hibernate;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.npc.lottery.user.dao.interf.IUserCommissionJDBCDao;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;


public class UserCommissionJDBCDao  implements IUserCommissionJDBCDao{

    private JdbcTemplate jdbcTemplate;
    
    public void updateCommission(Long userID,String userType)
    {
        
    }
    
    public int[] batchUpdateUserCommission(final List<UserCommission> commissions)
    {
        String updSql="update tb_user_commission uc " +
        		"set uc.commission_a=? ,uc.commission_b=?,uc.commission_c=?,uc.betting_quotas=?,uc.item_quotas=? " +
        		"where uc.user_id=? and uc.user_type=? and uc.play_final_type=? ";
        return jdbcTemplate.batchUpdate(updSql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {
                    	
                    	UserCommission userCommission = commissions.get(i);
                    	Double ca=userCommission.getCommissionA();
                    	Double cb=userCommission.getCommissionB();
                    	Double cc=userCommission.getCommissionC();
						Integer betQuotas = userCommission.getBettingQuotas();
				        Integer itemQuotas=userCommission.getItemQuotas();
				        Long userId=userCommission.getUserId();
				        String userType=userCommission.getUserType();
				        String comType=userCommission.getPlayFinalType();
						ca = new BigDecimal("100").subtract(new BigDecimal(ca.toString()).setScale(3, BigDecimal.ROUND_DOWN))
								.setScale(3, BigDecimal.ROUND_DOWN).doubleValue();
						cb = new BigDecimal("100").subtract(new BigDecimal(cb.toString()).setScale(3, BigDecimal.ROUND_DOWN))
								.setScale(3, BigDecimal.ROUND_DOWN).doubleValue();
						cc = new BigDecimal("100").subtract(new BigDecimal(cc.toString()).setScale(3, BigDecimal.ROUND_DOWN))
								.setScale(3, BigDecimal.ROUND_DOWN).doubleValue();

				        ps.setDouble(1, ca);
				        ps.setDouble(2, cb);
				        ps.setDouble(3, cc);
				        ps.setInt(4, betQuotas);
				        ps.setInt(5, itemQuotas);
						ps.setLong(6, userId);
	
						ps.setString(7, userType);
						ps.setString(8, comType);
                        
                        
                    }

                    public int getBatchSize() {
                        return commissions.size();
                    }
                });
        
        
    }
    
    public int[]  updateMemberUserCommission(final List<UserCommission> commissions,final String plate)
    {
    	String commissionCol="uc.commission_a=? ";
    	if("B".equals(plate))
    		commissionCol="uc.commission_b=? ";
    	else if("C".equals(plate))
    		commissionCol="uc.commission_c=? ";
    	 String updSql="update tb_user_commission uc " +
         		"set "+commissionCol+" ," +
         		" uc.betting_quotas=?,uc.item_quotas=? " +
         		" where uc.user_id=? and uc.user_type=? and uc.play_final_type=? ";
         return jdbcTemplate.batchUpdate(updSql,
                 new BatchPreparedStatementSetter() {
                     public void setValues(PreparedStatement ps, int i)
                             throws SQLException {
                     	
                     	UserCommission userCommission = commissions.get(i);
                     	
                     	
                     	
 						Integer betQuotas = userCommission.getBettingQuotas();
 				        Integer itemQuotas=userCommission.getItemQuotas();
 				        Long userId=userCommission.getUserId();
 				        String userType=userCommission.getUserType();
 				        String comType=userCommission.getPlayFinalType();
 				       
 				       if("A".equals(plate))
 				       {
 				    	  Double ca=userCommission.getCommissionA();
						  ca = new BigDecimal("100").subtract(new BigDecimal(ca.toString()).setScale(3, BigDecimal.ROUND_DOWN))
								  .setScale(3, BigDecimal.ROUND_DOWN).doubleValue();
 				    	   ps.setDouble(1, ca);
 				    	  
 				       }
 				    	else if("B".equals(plate))
 				    	{
 				    		Double cb=userCommission.getCommissionB();
 				    		cb = new BigDecimal("100").subtract(new BigDecimal(cb.toString()).setScale(3, BigDecimal.ROUND_DOWN))
 	 								.setScale(3, BigDecimal.ROUND_DOWN).doubleValue();
 				    		 ps.setDouble(1, cb);
 				    		
 				    	}
 				    	else if("C".equals(plate))
 				    	{
 				    		Double cc=userCommission.getCommissionC();
 				    		cc = new BigDecimal("100").subtract(new BigDecimal(cc.toString()).setScale(3, BigDecimal.ROUND_DOWN))
 	 								.setScale(3, BigDecimal.ROUND_DOWN).doubleValue();
 				    		 ps.setDouble(1, cc);
 				    	}
 				        ps.setInt(2, betQuotas);
 				        ps.setInt(3, itemQuotas);
 						ps.setLong(4, userId);
 	
 						ps.setString(5, userType);
 						ps.setString(6, comType);
                         
                         
                     }

                     public int getBatchSize() {
                         return commissions.size();
                     }
                 });
    	
    }
    

    public int[] batchUpdateUserCommissionRun(final List<UserCommission> commissions)
    {
        String updSql="update tb_user_commission uc " +
        		"set uc.betting_quotas=?,uc.item_quotas=? " +
        		"where uc.user_id=? and uc.user_type=? and uc.play_final_type=? ";
        return jdbcTemplate.batchUpdate(updSql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {
                        
                    	UserCommission userCommission = commissions.get(i);
						Integer betQuotas = userCommission.getBettingQuotas();
				        Integer itemQuotas=userCommission.getItemQuotas();
				        Long userId=userCommission.getUserId();
				        String userType=userCommission.getUserType();
				        String comType=userCommission.getPlayFinalType();
				        ps.setInt(1, betQuotas);
				        ps.setInt(2, itemQuotas);
						ps.setLong(3, userId);
	
						ps.setString(4, userType);
						ps.setString(5, comType);
                        
                    }

                    public int getBatchSize() {
                        return commissions.size();
                    }
                });
        
        
    }
    
    public int[] batchUpdateUserTreeCommission(final List<UserCommission> commissions)
    {
    	
    	String updSql="update tb_user_commission a " +
    			"set a.commission_a= (case when a.commission_a>? then ? else a.commission_a end ), " +
    			" a.commission_b= (case when a.commission_b>? then ? else a.commission_b end ), " +
    			" a.commission_c= (case when a.commission_c>? then ? else a.commission_c end ), " +
    			" a.item_quotas= (case when a.item_quotas>? then ? else a.item_quotas end )," +
    			" a.betting_quotas= (case when a.betting_quotas>? then ? else a.betting_quotas end )" +
    			" where exists" +
    			"( " +
    			"  select id from (  select id,parent_staff_qry from " +
    			" (select id ,tm.parent_staff_qry from tb_frame_manager_staff tm " +
    			"  union all " +
    			" select id, tu.parent_staff_qry  from tb_frame_member_staff tu) ts " +
    			" where  id=ts.id  " +
    			" START WITH  id=?  CONNECT BY PRIOR id = ts.parent_staff_qry)  ps  where a.user_id=ps.id " +
    			") and a.play_final_type=?";
    	
    	 return jdbcTemplate.batchUpdate(updSql,
                 new BatchPreparedStatementSetter() {
                     public void setValues(PreparedStatement ps, int i)
                             throws SQLException {
                     	
                     	UserCommission userCommission = commissions.get(i);
                     	Double ca=userCommission.getCommissionA();
                     	Double cb=userCommission.getCommissionB();
                     	Double cc=userCommission.getCommissionC();
 						Integer betQuotas = userCommission.getBettingQuotas();
 				        Integer itemQuotas=userCommission.getItemQuotas();
 				        Long userId=userCommission.getUserId();
 				        //String userType=userCommission.getUserType();
 				        String playFinalType=userCommission.getPlayFinalType();
			    		ca = new BigDecimal("100").subtract(new BigDecimal(ca.toString()).setScale(3, BigDecimal.ROUND_DOWN))
								.setScale(3, BigDecimal.ROUND_DOWN).doubleValue();
						cb = new BigDecimal("100").subtract(new BigDecimal(cb.toString()).setScale(3, BigDecimal.ROUND_DOWN))
								.setScale(3, BigDecimal.ROUND_DOWN).doubleValue();
						cc = new BigDecimal("100").subtract(new BigDecimal(cc.toString()).setScale(3, BigDecimal.ROUND_DOWN))
								.setScale(3, BigDecimal.ROUND_DOWN).doubleValue();
 				        
 				        ps.setDouble(1, ca);ps.setDouble(2, ca);
 				        ps.setDouble(3, cb); ps.setDouble(4, cb);
 				        ps.setDouble(5, cc); ps.setDouble(6, cc);
 				        ps.setInt(7,itemQuotas);ps.setInt(8, itemQuotas);
 				        ps.setInt(9,betQuotas );ps.setInt(10, betQuotas);
 						ps.setLong(11, userId);
 	
 						ps.setString(12, playFinalType);

                         
                         
                     }

                     public int getBatchSize() {
                         return commissions.size();
                     }
                 });
    	

    	
    }
    
    
    
    
    public int[] batchInsertUserCommission(final List<UserCommission> commissions)
    
    {
    	
    	String insetSql="insert into tb_user_commission " +
    			"(id ,user_id,user_type,play_type,play_final_type,commission_a,commission_b,commission_c," +
    			" betting_quotas,item_quotas,create_user,create_time,modify_user,modify_time,chief_id) " +
    			" values(seq_tb_user_commission.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        return jdbcTemplate.batchUpdate(insetSql,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {
                        
                    	UserCommission userCommission = commissions.get(i);
						Integer betQuotas = userCommission.getBettingQuotas();
				        Integer itemQuotas=userCommission.getItemQuotas();
				        Long userId=userCommission.getUserId();
				        String userType=userCommission.getUserType();
				        String comType=userCommission.getPlayFinalType();
				        String playType=userCommission.getPlayType();
				        
				        Double ca=userCommission.getCommissionA();
                    	Double cb=userCommission.getCommissionB();
                    	Double cc=userCommission.getCommissionC();
                    	Long creatUserID=userCommission.getCreateUser();
						ps.setLong(1, userId);
						ps.setString(2, userType);
						ps.setString(3, playType);
						ps.setString(4, comType);
						ps.setDouble(5, ca);
					    ps.setDouble(6, cb);
					    ps.setDouble(7, cc);
						
						 ps.setInt(8, betQuotas);
					     ps.setInt(9, itemQuotas);
					     ps.setLong(10, creatUserID);
					     ps.setDate(11,new Date(System.currentTimeMillis()));
					     ps.setLong(12, creatUserID);
					     ps.setDate(13,new Date(System.currentTimeMillis()));
					   
					     //add by peter for add chief id
					     Long chiefId = userCommission.getChiefId();
					     if(null!=chiefId){
					    	  ps.setLong(14, chiefId);
					     }else{
					    	 ps.setNull(14, Types.NUMERIC);
					     }
					   
                        
                    }

                    public int getBatchSize() {
                        return commissions.size();
                    }
                });
    	
    	
    }
    
    public int[] batchUpdateChiefUsereCommission(final List<UserCommissionDefault> commissions)
    {
    	
    	String updSql="update tb_user_commission a " +
    			"set a.commission_a= (case when a.commission_a>? then ? else a.commission_a end ), " +
    			" a.commission_b= (case when a.commission_b>? then ? else a.commission_b end ), " +
    			" a.commission_c= (case when a.commission_c>? then ? else a.commission_c end ), " +
    			" a.item_quotas= (case when a.item_quotas>? then ? else a.item_quotas end )," +
    			" a.betting_quotas= (case when a.betting_quotas>? then ? else a.betting_quotas end )" +
    			" where a.play_final_type=? and chief_id=?";
    	
    	 return jdbcTemplate.batchUpdate(updSql,
                 new BatchPreparedStatementSetter() {
                     public void setValues(PreparedStatement ps, int i)
                             throws SQLException {
                     	
                    	UserCommissionDefault userCommission = commissions.get(i);
                    	Long userID=userCommission.getUserId();
                     	Double ca=userCommission.getCommissionA();
                     	Double cb=userCommission.getCommissionB();
                     	Double cc=userCommission.getCommissionC();
 						Integer betQuotas = userCommission.getBettingQuotas();
 				        Integer itemQuotas=userCommission.getItemQuotas();
 				        Long userId=userCommission.getUserId();
 				        //String userType=userCommission.getUserType();
 				        String playFinalType=userCommission.getPlayFinalType();
						ca = new BigDecimal("100").subtract(new BigDecimal(ca.toString()).setScale(3, BigDecimal.ROUND_DOWN))
								.setScale(3, BigDecimal.ROUND_DOWN).doubleValue();
						cb = new BigDecimal("100").subtract(new BigDecimal(cb.toString()).setScale(3, BigDecimal.ROUND_DOWN))
								.setScale(3, BigDecimal.ROUND_DOWN).doubleValue();
						cc = new BigDecimal("100").subtract(new BigDecimal(cc.toString()).setScale(3, BigDecimal.ROUND_DOWN))
								.setScale(3, BigDecimal.ROUND_DOWN).doubleValue();
 				        
 				        
 				        ps.setDouble(1, ca);ps.setDouble(2, ca);
 				        ps.setDouble(3, cb); ps.setDouble(4, cb);
 				        ps.setDouble(5, cc); ps.setDouble(6, cc);
 				        ps.setInt(7,itemQuotas);ps.setInt(8, itemQuotas);
 				        ps.setInt(9,betQuotas );ps.setInt(10, betQuotas);
 						
 	
 						ps.setString(11, playFinalType);
 						ps.setLong(12, userID);

                         
                         
                     }

                     public int getBatchSize() {
                         return commissions.size();
                     }
                 });
    	

    	
    }
    
    
    
    
    
    
    
    
   public void  updateBranchforDefualtUserCommission(Long chiefId,Long branchId)
    {
	   String updSql=" update tb_user_commission_default a  " +
	   		"set ( a.commission_a , a.commission_b, " +
	   		"a.commission_c, a.betting_quotas,a.item_quotas" +
	   		")=(select b.commission_a,b.commission_b,b.commission_c,b.betting_quotas,b.item_quotas " +
	   		" from tb_user_commission b where b.user_id= ? and user_type='3' and a.play_final_type = b.play_final_type " +
	   		") where  exists ( select 1 from tb_user_commission c where c.user_id= ? and  user_type='3' and a.play_final_type=c.play_final_type) and a.user_id=? ";
	   
	   
	   
	   
/*	  String updSql=" update tb_user_commission_default a  set " +
	  		" a.commission_a=100-b.commission_a ," +
	  		" a.commission_b=100-b.commission_b," +
	  		" a.commission_c=100-b.commission_c," +
	  		" a.betting_quotas=b.betting_quotas," +
	  		" a.item_quotas=b.item_quotas " +
	  		" where  exists ( select 1 from tb_user_commission b where b.user_id= ?) " +
	  		" and a.user_id=?";*/
    	
	  Object[] args = new Object[] {branchId,branchId,chiefId};
		
		jdbcTemplate.update(updSql, args);
    }

    
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
}
