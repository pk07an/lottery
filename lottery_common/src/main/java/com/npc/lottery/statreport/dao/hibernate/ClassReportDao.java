package com.npc.lottery.statreport.dao.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.replenish.entity.Replenish;
import com.npc.lottery.statreport.dao.interf.IClassReportDao;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.util.HibernateTool;

/**
 * 交收报表
 * 
 * @author User
 *
 */
public class ClassReportDao extends HibernateDaoSupport implements
        IClassReportDao {
    
    private static Logger log = Logger.getLogger(ClassReportDao.class);
    
    /**
     * 查询指定代理用户所对应的普通用户信息（包括直属会员）
     * 
     * @param agentUserID 代理用户ID
     * 
     * @return  MemberStaffExt 类型的 List
     */
    public ArrayList<MemberStaffExt> findAgentMemberList(Long agentUserID) {
        
        final ConditionData conditionData = new ConditionData();
        conditionData.addEqual("agentStaff", agentUserID);//代理ID
        
        return (ArrayList<MemberStaffExt>)getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(MemberStaffExt.class);

                //处理查询条件
                criteria = HibernateTool.parseMultiCondition(criteria,
                        conditionData);

                criteria.setFirstResult(0);
                criteria.setMaxResults(99999);

                return criteria.list();
            }
        });
    }
    
    /**
     * 查询指定总代理用户所对应的的普通用户信息（包括直属会员）
     * 
     * @param genAgentUserID 总代理用户ID
     * 
     * @return  MemberStaffExt 类型的 List
     */
    public ArrayList<MemberStaffExt> findGenAgentMemberList(Long genAgentUserID) {
        
        final ConditionData conditionData = new ConditionData();
        conditionData.addEqual("genAgentStaff", genAgentUserID);//总代理ID
        
        return (ArrayList<MemberStaffExt>)getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(MemberStaffExt.class);

                //处理查询条件
                criteria = HibernateTool.parseMultiCondition(criteria,
                        conditionData);

                criteria.setFirstResult(0);
                criteria.setMaxResults(99999);

                return criteria.list();
            }
        });
    }
    
    
    /**
     * 查询指定股东用户所对应的的普通用户信息（包括直属会员）
     * 
     * @param stockholderUserID 股东用户ID
     * 
     * @return  MemberStaffExt 类型的 List
     */
    public ArrayList<MemberStaffExt> findStockholderMemberList(Long stockholderUserID) {
        
        final ConditionData conditionData = new ConditionData();
        conditionData.addEqual("stockholderStaff", stockholderUserID);//股东ID
        
        return (ArrayList<MemberStaffExt>)getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(MemberStaffExt.class);

                //处理查询条件
                criteria = HibernateTool.parseMultiCondition(criteria,
                        conditionData);

                criteria.setFirstResult(0);
                criteria.setMaxResults(99999);

                return criteria.list();
            }
        });
    }
    
    /**
     * 查询指定分公司用户所对应的的普通用户信息（包括直属会员）
     * 
     * @param branchUserID 分公司用户ID
     * 
     * @return  MemberStaffExt 类型的 List
     */
    public ArrayList<MemberStaffExt> findBranchMemberList(Long branchUserID) {
        
        final ConditionData conditionData = new ConditionData();
        conditionData.addEqual("branchStaff", branchUserID);//分公司ID
        
        return (ArrayList<MemberStaffExt>)getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(MemberStaffExt.class);

                //处理查询条件
                criteria = HibernateTool.parseMultiCondition(criteria,
                        conditionData);

                criteria.setFirstResult(0);
                criteria.setMaxResults(99999);

                return criteria.list();
            }
        });
    }
    
    /**
     * 查询指定总监用户所对应的的普通用户信息（包括直属会员）
     * 
     * @param chiefUserID 总监用户ID
     * 
     * @return  MemberStaffExt 类型的 List
     */
    public ArrayList<MemberStaffExt> findChiefMemberList(Long chiefUserID) {
        
        final ConditionData conditionData = new ConditionData();
        conditionData.addEqual("chiefStaff", chiefUserID);//总监ID
        
        return (ArrayList<MemberStaffExt>)getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(MemberStaffExt.class);

                //处理查询条件
                criteria = HibernateTool.parseMultiCondition(criteria,
                        conditionData);

                criteria.setFirstResult(0);
                criteria.setMaxResults(99999);

                return criteria.list();
            }
        });
    }
    
    /**
     * 查询满足指定查询条件的补货数据记录
     * 
     * @param conditionData
     *            查询条件信息
     * @param firstResult
     *            需要查询的第一个记录数
     * @param maxResults
     *            需要查询的记录数目
     * @return  Replenish 类型的 List
     */
    public List findReplenishList(final ConditionData conditionData,
            final int firstResult, final int maxResults) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(Replenish.class);

                //处理查询条件
                criteria = HibernateTool.parseMultiCondition(criteria,
                        conditionData);

                criteria.setFirstResult(firstResult);
                criteria.setMaxResults(maxResults);

                return criteria.list();
            }
        });
    }
}
