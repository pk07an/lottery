package com.npc.lottery.member.dao.hibernate;

import com.npc.lottery.common.dao.HibernateDao;
import com.npc.lottery.member.dao.interf.IFuncDao;
import com.npc.lottery.sysmge.entity.Function;

/**
 * 角色所拥有的功能数据库处理类
 *
 * @author none
 *
 */
public class FuncDao extends HibernateDao<Function, Long> implements IFuncDao {

	/*public Page<Function> findFuncPage(Page<Function> page) {
		// TODO Auto-generated method stub
		return this.getAll(page);
	}
	public List<Function> findFuncList()
	{
		String hql="from Function where funcName = :funcName";
		Map<String,String> map=new HashMap<String,String>();
		map.put("funcName", "投注");
		return this.find("from Function where funcName = :funcName", map);
		
		
		String hqlArray="from Function where funcName = ?";
		Object[] parameter=new String[]{"投注"}; 
		return this.find(hqlArray, parameter);
		
	}*/
}
