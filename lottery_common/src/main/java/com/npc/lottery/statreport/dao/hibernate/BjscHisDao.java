package com.npc.lottery.statreport.dao.hibernate;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.statreport.dao.interf.IBJSCHisDao;
import com.npc.lottery.statreport.entity.BjscHis;
import com.npc.lottery.util.HibernateTool;

public class BjscHisDao extends HibernateDaoSupport implements IBJSCHisDao {

    /**
     * 根据查询条件查询对应北京赛车历史信息
     * 
     * @param conditionStr
     * @return
     */
    public ArrayList<BjscHis> findBjscHis(String conditionStr) {

        StringBuffer sqlInfo = new StringBuffer();
        sqlInfo.append("SELECT * FROM (SELECT t.*, RANK() OVER (PARTITION BY order_no ORDER BY rownum DESC) rankNum, ");
        sqlInfo.append("COUNT(*) OVER(PARTITION BY order_no) recordNum FROM tb_bjsc_his t WHERE 1=1 ");
        if (null != conditionStr && conditionStr.trim().length() > 0) {
            sqlInfo.append(" AND ");
            sqlInfo.append(conditionStr);
        }
        sqlInfo.append(") WHERE rankNum = 1 ORDER BY BETTING_DATE DESC");

        Session session = getHibernateTemplate().getSessionFactory()
                .getCurrentSession();
        
        List resultTemp = session.createSQLQuery(sqlInfo.toString())
                .addEntity("t", BjscHis.class).addScalar("recordNum").list();

        ArrayList<BjscHis> result = null;

        BjscHis entity;
        BigDecimal recordNum;
        //将统计值填充如对象
        if (null == resultTemp || resultTemp.size() < 1) {
            result = null;
        } else {
            result = new ArrayList<BjscHis>();
            for (int i = 0; i < resultTemp.size(); i++) {
                entity = (BjscHis)((Object[])resultTemp.get(i))[0];
                recordNum = (BigDecimal)((Object[])resultTemp.get(i))[1];
                entity.setRecordNum(recordNum.longValue());//保存数量
                
                //保存数据
                result.add(entity);
            }
        }

        return result;
    }
    
	 /**
     * 批量更新
     * @param entity
     */
    @Override
    public void updateBatch(String hql,Object[] values) {
        getHibernateTemplate().bulkUpdate(hql, values);
    }
    
    /**
     * 查询满足指定查询条件的数据记录
     * 
     * 
     * @param conditionData
     *            查询条件信息
     * @param firstResult
     *            需要查询的第一个记录数
     * @param maxResults
     *            需要查询的记录数目
     * @return  GdklsfHis 类型的 List
     */
    public List findBjscHisList(final ConditionData conditionData,
            final int firstResult, final int maxResults) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(BjscHis.class);

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
     * 删除对象
     * @param entity
     */
    public void delete(BjscHis entity){
    	getHibernateTemplate().delete(entity);
    }


	@Override
	public Object[] findBjHisMaxAndMinBetDate() {
		final String sql = "SELECT MIN(BETTING_DATE) AS startTime, MAX(BETTING_DATE) AS endTime FROM TB_BJSC_HIS";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.addScalar("startTime", Hibernate.DATE);
		query.addScalar("endTime", Hibernate.DATE);
		return (Object[]) query.uniqueResult();
	}
}
