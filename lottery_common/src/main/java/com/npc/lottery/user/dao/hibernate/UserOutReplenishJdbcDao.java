package com.npc.lottery.user.dao.hibernate;

import org.springframework.jdbc.core.JdbcTemplate;

import com.npc.lottery.user.dao.interf.IUserOutReplenishJdbcDao;

/**
 * @author Eric
 *
 */
public class UserOutReplenishJdbcDao implements IUserOutReplenishJdbcDao {
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void saveUserCommissionFromDefault(Long chiefId,Long userId,String userType) {		
		String sql="INSERT INTO tb_user_commission " +
    			"(id ,user_id,user_type,play_type,play_final_type,commission_a,commission_b,commission_c," +
    			" betting_quotas,item_quotas,create_user,create_time,modify_user,modify_time) " +
    			" SELECT seq_tb_user_commission.nextval," + userId + "," + userType + ",a.play_type,a.play_final_type," +
		    			" a.commission_a,a.commission_b,a.commission_c," +
		    			" a.betting_quotas,a.item_quotas," + userId + ",sysdate," + userId + ",sysdate " +
    			" FROM tb_user_commission_default a " +
    			" WHERE a.user_id=" + chiefId;
		jdbcTemplate.execute(sql);
		
	}
	
	public void updateOutReplenishCommission(Double commissionA,Integer bettingQuotas,Integer itemQuotas,
			 Long userId,String userType,String typeCode)
	    {
			StringBuffer finalSql=new StringBuffer();	
			finalSql.append("update tb_user_commission uc " +
	        		"set uc.betting_quotas=?,uc.item_quotas=?,uc.commission_a=? ");
			finalSql.append(" where uc.user_id=? and uc.user_type=? and uc.play_final_type=? ");
			Object[] args = new Object[] {bettingQuotas,itemQuotas,commissionA,userId,userType,typeCode};
			
			jdbcTemplate.update(finalSql.toString(), args);
		}
	
	public void updateManagerStatusById(Long userId,String status)
	{
		String updSql="update tb_frame_manager_staff a set a.flag=? where id=?";
		Object[] args = new Object[] {status,userId};
		
		jdbcTemplate.update(updSql, args);
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
}
