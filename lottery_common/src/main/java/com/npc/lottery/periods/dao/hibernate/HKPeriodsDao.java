package com.npc.lottery.periods.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.periods.dao.interf.IHKPeriodsDao;
import com.npc.lottery.periods.entity.HKPeriods;

public class HKPeriodsDao extends HibernateDao<HKPeriods, Long> implements IHKPeriodsDao{
	private JdbcTemplate jdbcTemplate;
	
	public HKPeriods queryShopRunningPeriods(String shopsCode)
	{
		String sql="select b.periods_num,a.periods_state,a.open_quot_time,a.stop_quot_time from tb_shops_hklhc_periods a,tb_hklhc_periods_info b where a.periods_info_id=b.id and a.shops_code=? and a.periods_state<=1 ";
		Object[] parameters = new Object[] { shopsCode };
		List<HKPeriods> periodsList = jdbcTemplate.query(sql, parameters,new HKPeriodsItemMapper());
		if(periodsList!=null&&periodsList.size()>0)
			return periodsList.get(0);
		else 
			return null;
		
		
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
	
}
class HKPeriodsItemMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        
    	HKPeriods hkPeriods = new HKPeriods();
    	hkPeriods.setPeriodsNum(rs.getString("periods_num"));
    	hkPeriods.setPeriodsState(rs.getString("periods_state"));
    	hkPeriods.setOpenQuotTime(rs.getTimestamp("open_quot_time"));
    	hkPeriods.setStopQuotTime(rs.getTimestamp("stop_quot_time"));
        return hkPeriods;
    }
    
    

}