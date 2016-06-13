package com.npc.lottery.sysmge.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IManagerStaffDao;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.util.HibernateTool;


/**
 * 基础管理用户所对应的数据库实现类
 * 
 * @author none
 *
 */
public class ManagerStaffDao extends HibernateDaoSupport implements
        IManagerStaffDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
    /**
     * 统计满足指定查询条件的人员信息数目
     * 
     * @param conditionData
     *            查询条件信息
     * @return
     */
    public long findAmountManagerStaffList(final ConditionData conditionData) {
        long amount = 0;

        amount = ((Long) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {

                        Criteria criteria = session
                                .createCriteria(ManagerStaff.class);

                        //处理查询条件
                        criteria = HibernateTool.parseMultiCondition(criteria,
                                conditionData);

                        long count = ((Number) criteria.setProjection(
                                Projections.rowCount()).uniqueResult())
                                .intValue();

                        return count;
                    }
                })).longValue();

        return amount;
    }

    /**
     * 查询满足指定查询条件的人员信息数据记录
     * 
     * @param conditionData
     *            查询条件信息
     * @param firstResult
     *            需要查询的第一个记录数
     * @param maxResults
     *            需要查询的记录数目
     * @return  ManagerStaff 类型的 List
     */
    public List findManagerStaffList(final ConditionData conditionData,
            final int firstResult, final int maxResults) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(ManagerStaff.class);

                //处理查询条件
                criteria = HibernateTool.parseMultiCondition(criteria,
                        conditionData);

                criteria.setFirstResult(firstResult);
                criteria.setMaxResults(maxResults);

                return criteria.list();
            }
        });
    }

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveManagerStaff(ManagerStaff entity) {

        Long result = null;

        result = (Long) getHibernateTemplate().save(entity);

        return result;
    }

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void updateManagerStaff(ManagerStaff entity) {
        getHibernateTemplate().update(entity);
    }

    /**
     * 删除对象
     * @param entity
     */
    public void delManagerStaff(ManagerStaff entity) {
        getHibernateTemplate().delete(entity);
    }
    
    /**
     * 根据总监查询商铺号
     * @param entity
     * @author Eric
     */
    public List<ManagerStaff> findShopsCodeByChief(Long userID) {
       return getHibernateTemplate().find("from ManagerStaff where ID=?", userID);
    }
    /**
     * 通过商铺号查总监
     * @param shopsCode
     * @author Eric
     */
    public List<ChiefStaffExt> findChiefByShopsCode(String shopsCode) {
       return getHibernateTemplate().find("from ChiefStaffExt where shopsCode=?", shopsCode);
    }

    /**
     * 查询总管账户
     */
	@Override
	public ManagerStaff findManagerStaffByAccount(String account) {
		Object[] parameter = new Object[] { account };
		String sql="select ms.* from TB_FRAME_MANAGER_STAFF ms where account=? order by id";
		List<ManagerStaff> staffList=new ArrayList<ManagerStaff>();
			Connection connenction=this.getSession().connection();
			PreparedStatement pstmt = null;
			ResultSet rs=null;
		    try {
		        pstmt = (PreparedStatement)connenction.prepareStatement(sql);
		        pstmt.setString(1, account);
		        rs = pstmt.executeQuery();
		        while (rs.next()) {
		        	ManagerStaff staff=new ManagerStaff();
					staff.setID(Long.valueOf(rs.getInt("ID")));
					staff.setAccount(rs.getString("ACCOUNT"));
					staff.setFlag(rs.getString("FLAG"));
					staff.setUserType(rs.getString("USER_TYPE"));
					staff.setUserExtInfoId(Long.valueOf(rs.getInt("USER_EXT_INFO_ID")));
					staff.setUserPwd(rs.getString("USER_PWD"));
					staff.setChsName(rs.getString("CHS_NAME"));
					staff.setEngName(rs.getString("ENG_NAME"));
					staff.setEmailAddr(rs.getString("EMAIL_ADDR"));
					staff.setOfficePhone(rs.getString("OFFICE_PHONE"));
					staff.setMobilePhone(rs.getString("MOBILE_PHONE"));
					staff.setFax(rs.getString("FAX"));
					staff.setCreateDate(rs.getDate("CREATE_DATE"));
					staff.setUpdateDate(rs.getDate("UPDATE_DATE"));
					staff.setLoginDate(rs.getDate("LOGIN_DATE"));
					staff.setLoginIp(rs.getString("LOGIN_IP"));
					staff.setComments(rs.getString("COMMENTS"));
					staff.setParentStaffQry(Long.valueOf(rs.getInt("PARENT_STAFF_QRY")));
					staff.setParentStaffTypeQry(rs.getString("PARENT_STAFF_TYPE_QRY"));
					staff.setPasswordUpdateDate(rs.getDate("PASSWORD_UPDATE_DATE"));
					staff.setPasswordResetFlag(rs.getString("PASSWORD_RESET_FLAG"));
					staffList.add(staff);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
				logger.error(e);
		    }finally {
				try {
					rs.close();
					pstmt.close();
					connenction.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    }
			
		return staffList.get(0);
	}

	@Override
	public List<ChiefStaffExt> findChiefByShopsCodeByScheme(String shopsCode, String scheme) {
		if(StringUtils.isNotEmpty(scheme)){
			scheme=scheme+".";
		}
		List<ChiefStaffExt> chiefList=new ArrayList<ChiefStaffExt>();
		String sql="select chi.MANAGER_STAFF_ID,chi.SHOPS_CODE,ms.ACCOUNT,ms.FLAG,ms.USER_TYPE,ms.USER_EXT_INFO_ID,ms.USER_PWD,ms.CHS_NAME,"+
					"ms.CREATE_DATE,ms.UPDATE_DATE,ms.PASSWORD_UPDATE_DATE,ms.PASSWORD_RESET_FLAG from "+scheme+"TB_CHIEF_STAFF_EXT chi "+
					"inner join "+scheme+"TB_FRAME_MANAGER_STAFF ms on ms.ID= chi.MANAGER_STAFF_ID where chi.SHOPS_CODE=?";
		Connection connection=this.getSession().connection();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt=connection.prepareStatement(sql);
			pstmt.setString(1, shopsCode);
			rs=pstmt.executeQuery();
			while(rs.next()){
				ChiefStaffExt chief=new ChiefStaffExt();
				chief.setManagerStaffID(Long.valueOf(rs.getInt("MANAGER_STAFF_ID")));
				chief.setShopsCode(rs.getString("SHOPS_CODE"));
				ManagerStaff managerStaff=new ManagerStaff();
				managerStaff.setID(Long.valueOf(rs.getInt("MANAGER_STAFF_ID")));
				managerStaff.setAccount(rs.getString("ACCOUNT"));
				managerStaff.setFlag(rs.getString("FLAG"));
				managerStaff.setUserType(rs.getString("USER_TYPE"));
				managerStaff.setUserExtInfoId(Long.valueOf(rs.getInt("USER_EXT_INFO_ID")));
				managerStaff.setUserPwd(rs.getString("USER_PWD"));
				managerStaff.setChsName(rs.getString("CHS_NAME"));
				managerStaff.setCreateDate(rs.getDate("CREATE_DATE"));
				managerStaff.setUpdateDate(rs.getDate("UPDATE_DATE"));
				managerStaff.setPasswordUpdateDate(rs.getDate("PASSWORD_UPDATE_DATE"));
				managerStaff.setPasswordResetFlag(rs.getString("PASSWORD_RESET_FLAG"));
				chief.setManagerStaff(managerStaff);
				chiefList.add(chief);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}finally{
			try {
				rs.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return chiefList;
	}

	
}
