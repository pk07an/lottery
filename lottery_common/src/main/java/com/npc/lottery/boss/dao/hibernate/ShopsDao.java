package com.npc.lottery.boss.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.npc.lottery.boss.dao.interf.IShopsDao;
import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.boss.entity.ShopsRent;
import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.user.entity.ChiefStaffExt;

public class ShopsDao extends HibernateDao<ShopsInfo, Long> implements IShopsDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public ShopsInfo findShopsCode(String cShopsCode)
	{
		String hql="from ShopsInfo where shopsCode = :cShopsCode";
		Map<String,String> map=new HashMap<String,String>();
		map.put("cShopsCode", cShopsCode);
		return this.findUnique(hql, map);
		
	}

	@Override
	public ShopsInfo findShopsName(String cShopsName) {
		String hql="from ShopsInfo where lower(shopsName) = :cShopsName";
		Map<String,String> map=new HashMap<String,String>();
		map.put("cShopsName", cShopsName.toLowerCase());
		return this.findUnique(hql, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAllShopsCode() {
		 String sql="select shops_code from tb_shops_info";
		List<String> shopsList=jdbcTemplate.query(sql,new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String result = rs.getString("shops_code");
				if (StringUtils.isEmpty(result)) {
					result = "";
				}
				return result;
			}
		});
		return shopsList;
	}

	@Override
	public List<ShopsInfo> findShopsAll(Map<String,String> schemeMap) {
		List<ShopsInfo> shopList = new ArrayList<ShopsInfo>();
		Connection connection=this.getSession().connection();
		//shopsInfo查询
		PreparedStatement pstmt = null;
		ResultSet result=null;
		try {
			if(null == schemeMap){
				return null;
			}
		   //通过scheme遍历查询
		     for (String s : schemeMap.values()) {
		    	int i=0;
				String createSql="select si.*,fms.account as account,sr.EXPITY_TIME as expityTime,sr.EXPITY_WARNING_TIME as expityWarningTime from tb_shops_info si "+
							"inner join "+s+".TB_CHIEF_STAFF_EXT cse on cse.shops_code=si.shops_code "+
							"inner join "+s+".TB_FRAME_MANAGER_STAFF fms on fms.id = cse.MANAGER_STAFF_ID "+
							"inner join TB_SHOPS_RENT sr on sr.shops_code=si.shops_code";
				pstmt=(PreparedStatement)connection.prepareStatement(createSql);
				result = pstmt.executeQuery();
				shopList.addAll(i, this.setResultRow(result));
				i++;
			}
		} catch (Exception e) {
			 e.printStackTrace();
			 logger.error(e.getMessage());
		}finally{
			try {
				pstmt.close();
				result.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return shopList;
	}

	@Override
	public ShopsInfo findShopsInfoByCode(String shopsCode) {
		ShopsInfo shopsInfo=null;
		String sql=" select si.*,sr.EXPITY_TIME as expityTime,sr.EXPITY_WARNING_TIME as expityWarningTime from tb_shops_info si "+
					"inner join TB_SHOPS_RENT sr on sr.shops_code=si.shops_code where si.SHOPS_CODE=?";
		Connection connection=this.getSession().connection();
		//shopsInfo查询
		PreparedStatement pstmt = null;
		ResultSet result=null;
		try {
			pstmt=(PreparedStatement)connection.prepareStatement(sql);
			pstmt.setString(1, shopsCode);
			result = pstmt.executeQuery();
			List<ShopsInfo> list=this.setResultRow(result);
			if(list.size()>0){
				shopsInfo=list.get(0);
			}
			
		} catch (Exception e) {
			 e.printStackTrace();
			 logger.error(e.getMessage());
		}finally{
			try {
				result.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return shopsInfo;
	}
	
	//查询结果填充
	private List<ShopsInfo> setResultRow(ResultSet result){
		List<ShopsInfo> shopList = new ArrayList<ShopsInfo>();
		try {
			while (result.next()) {
				ShopsInfo shopsInfo=new ShopsInfo();
				shopsInfo.setID(Long.valueOf(result.getInt("ID")));
				shopsInfo.setShopsCode(result.getString("SHOPS_CODE"));
				shopsInfo.setShopsName(result.getString("SHOPS_NAME"));
				shopsInfo.setState(result.getString("STATE"));
				shopsInfo.setCreateUser(Long.valueOf(result.getInt("CREATE_USER")));
				shopsInfo.setCreateTime(result.getDate("CREATE_TIME"));
				shopsInfo.setCss(result.getString("CSS"));
				shopsInfo.setRemark(result.getString("REMARK"));
				shopsInfo.setEnableBetDelete(result.getString("ENABLE_BET_DELETE"));
				shopsInfo.setEnableBetCancel(result.getString("ENABLE_BET_CANCEL"));
				ChiefStaffExt chiefStaff=new ChiefStaffExt();
				ManagerStaff managerStaff=new ManagerStaff();
				managerStaff.setAccount(isExistColumn(result,"account") ? result.getString("account") : "");
				chiefStaff.setManagerStaff(managerStaff);
				shopsInfo.setChiefStaffExt(chiefStaff);
				ShopsRent sr=new ShopsRent();
				sr.setExpityTime(result.getDate("expityTime"));
				sr.setExpityWarningTime(result.getDate("expityWarningTime"));
				Set<ShopsRent> rsSet=new HashSet<ShopsRent>();
				rsSet.add(sr);
				shopsInfo.setShopsRent(rsSet);
				shopList.add(shopsInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			return shopList;
		}
	}

	//判断查询数据是否存在该列
	private boolean isExistColumn(ResultSet rs, String columnName) {
	    try {
	        if (rs.findColumn(columnName) > 0 ) {
	            return true;
	        } 
	    }
	    catch (SQLException e) {
	        return false;
	    }
	    return false;
	}

}
