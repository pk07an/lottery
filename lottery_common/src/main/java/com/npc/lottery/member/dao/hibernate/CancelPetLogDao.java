package com.npc.lottery.member.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.member.dao.interf.ICancelPetLogDao;
import com.npc.lottery.member.entity.CancelPet;
import com.npc.lottery.member.entity.CancelPetLog;

/**
 * @author f
 *
 */
public class CancelPetLogDao extends HibernateDao<CancelPetLog, Long> implements ICancelPetLogDao {
	
	@Override
	public List<CancelPetLog> queryCancelPetLogList(String typeCode, String orderNo, String periodsNum, String billType) {
		String tableName = "";
		if("0".equals(billType)){
			tableName = Constant.REPLENISH_TABLE_NAME_HIS;
			billType = CancelPet.CANCEL_PET_PLAY_TYPE_R;
		}else{
			if(typeCode.indexOf(Constant.LOTTERY_TYPE_GDKLSF) != -1){
				tableName = Constant.GDKLSF_HIS_TABLE_NAME;
				billType = CancelPet.CANCEL_PET_PLAY_TYPE_GDKLSF;
			}else if(typeCode.indexOf(Constant.LOTTERY_TYPE_NC) != -1){
				tableName = Constant.NC_HIS_TABLE_NAME;
				billType = CancelPet.CANCEL_PET_PLAY_TYPE_NC;
				
			}else if(typeCode.indexOf(Constant.LOTTERY_TYPE_BJ) != -1){
				tableName = Constant.BJSC_HIS_TABLE_NAME;
				billType = CancelPet.CANCEL_PET_PLAY_TYPE_BJ;
				
			}else if(typeCode.indexOf(Constant.LOTTERY_TYPE_CQSSC) != -1){
				tableName = Constant.CQSSC_HIS_TABLE_NAME;
				billType = CancelPet.CANCEL_PET_PLAY_TYPE_CQSSC;
				
			}else if(typeCode.indexOf(Constant.LOTTERY_TYPE_K3) != -1){
				tableName = Constant.K3_HIS_TABLE_NAME;
				billType = CancelPet.CANCEL_PET_PLAY_TYPE_K3;
				
			}
		}
		String sql="";
		Object[] parameters= null;		
		sql="select CREATE_DATE as createDate,TYPE as type,IP from tb_cancel_pet_log where table_id in "
				+ " (select a.table_id from tb_cancel_pet_log a,"+tableName+" b where a.table_id=b.id and b.order_no=? "
				+" and b.periods_num=? and bill_type=?)";
		parameters=new Object[]{orderNo,periodsNum,billType};

		@SuppressWarnings("unchecked")
		List<CancelPetLog> retList = jdbcTemplate.query(sql,parameters, new CancelPetLogMapper());
		return retList;
	}
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}

class CancelPetLogMapper implements RowMapper {  	  
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
		CancelPetLog item = new CancelPetLog();  
		item.setCreateDate(rs.getTimestamp("createDate"));	   
		item.setType(rs.getString("type"));
		item.setIP(rs.getString("IP"));
		
		return item;  
	}  	  
}

