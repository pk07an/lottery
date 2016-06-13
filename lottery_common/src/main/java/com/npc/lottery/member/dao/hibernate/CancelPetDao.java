package com.npc.lottery.member.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.member.dao.interf.ICancelPetDao;
import com.npc.lottery.member.entity.CancelPet;
import com.npc.lottery.member.entity.CancelPetLog;
import com.npc.lottery.replenish.vo.ReplenishHKVO;
import com.npc.lottery.replenish.vo.ReplenishVO;

/**
 * @author none
 *
 */
public class CancelPetDao extends HibernateDao<CancelPet, Long> implements ICancelPetDao {

	/**
	 * 恢复注单
	 * 1、先把注销注单的备份表(TB_CANCEL_PET)的win_state状态恢复到投注历史单
	 * 2、删除对应的注销注单的备份表的记录(TB_CANCEL_PET)
	 * 
	 */
	@Override
	public void recoveryPet(String orderNo, String typeCode, String periodsNum, String billType, String opType, String ip){
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
		//恢復狀態
		String sql = " update "+tableName+" set win_state="
					+ "(select win_state from tb_cancel_pet where "+tableName+".id=tb_cancel_pet.table_id and tb_cancel_pet.bill_type=?)"+
						" where order_no=? and type_code=? and periods_num=? ";
		Object[] parameters = new Object[] { billType,orderNo, typeCode, periodsNum};
		jdbcTemplate.update(sql, parameters);
		
		//刪除對應的TB_CANCEL_PET記錄
		String sqlDel = " delete from tb_cancel_pet where id in "
				+ " (select a.id from tb_cancel_pet a,"+tableName+" b where a.table_id=b.id and b.order_no=? "
				+ " and b.type_code=? and b.periods_num=? and a.bill_type=?)";
		Object[] parametersDel = new Object[] { orderNo, typeCode, periodsNum, billType };
		jdbcTemplate.update(sqlDel, parametersDel);
		
		//插入操作日誌表
		String sqlLog = "insert into tb_cancel_pet_log (id,create_date,table_id,type,bill_type,ip) "+
						" (select seq_tb_cancel_pet_log.nextval,sysdate,a.ID,?,?,? "
						+ " from "+tableName+" a WHERE ORDER_NO=? AND PERIODS_NUM=?)";
		Object[] parametersLog = new Object[] { opType, billType, ip, orderNo, periodsNum};
		jdbcTemplate.update(sqlLog, parametersLog);
	}
	
	@Override
	public void saveCancelPet(String typeCode, String orderNo, String periodsNum, String billType, String opType, String ip){
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
		String sql = "insert into tb_cancel_pet (id,create_date,table_id,win_state,bill_type)"+
						"(select seq_tb_cancel_pet.nextval,sysdate,a.ID,a.win_state,? "
						+ "from "+tableName+" a WHERE ORDER_NO=? AND PERIODS_NUM=?)";
		Object[] parameters = new Object[] { billType, orderNo, periodsNum};
		jdbcTemplate.update(sql, parameters);
		
		//插入操作日誌表
		String sqlLog = "insert into tb_cancel_pet_log (id,create_date,table_id,type,bill_type,ip) "+
						" (select seq_tb_cancel_pet_log.nextval,sysdate,a.ID,?,?,? "
						+ " from "+tableName+" a WHERE ORDER_NO=? AND PERIODS_NUM=?)";
		Object[] parametersLog = new Object[] { opType, billType, ip, orderNo, periodsNum};
		jdbcTemplate.update(sqlLog, parametersLog);
	}
	
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
