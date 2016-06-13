/**
 * Hibernate工具类
 * 
 * @author none 创建日期
 */
package com.npc.lottery.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.npc.lottery.common.ConditionData;

public class HibernateTool {

	private static Logger log = Logger.getLogger(HibernateTool.class);

	/**
	 * 处理查询条件
	 * 
	 * @param criteria
	 * @param sqlData	查询条件数据集合
	 * @return
	 */
	public static Criteria parseMultiCondition(Criteria criteria,
			ConditionData sqlData) {

		if (null == sqlData) {
			log.info("无查询条件数据！");
			return criteria;
		}
		
		//equal的查询条件
		HashMap<String, Object> equalConditionMap = sqlData.getEqual();
		//notEqual的查询条件
		HashMap<String, Object> notEqualConditionMap = sqlData.getNotEqual();
		//in的查询条件
		HashMap<String, ArrayList> inConditionMap = sqlData.getIn();
		//null的查询条件
		ArrayList<String> isNullList = sqlData.getNull();
		//not null 的查询条件
		ArrayList<String> isNotNullList = sqlData.getNotNull();
		//like的查询条件
		HashMap<String, Object> likeConditionMap = sqlData.getLike();
		//between的查询条件
		HashMap<String, HashMap> betweenConditionMap = sqlData.getBetween();
		//排序
		ArrayList<HashMap> orderList = sqlData.getOrder();

		//处理 = 的查询条件
		if (null != equalConditionMap && 0 < equalConditionMap.size()) {

			Iterator conditionIter = equalConditionMap.keySet().iterator();
			String key = "";
			Object value = null;

			while (conditionIter.hasNext()) {
				key = (String) conditionIter.next();
				value = equalConditionMap.get(key);

				if (null != value) {
					criteria.add(Expression.eq(key, value));
				}
			}
		}

		//处理 <> 的查询条件
		if (null != notEqualConditionMap && 0 < notEqualConditionMap.size()) {

			Iterator conditionIter = notEqualConditionMap.keySet().iterator();
			String key = "";
			Object value = null;

			while (conditionIter.hasNext()) {
				key = (String) conditionIter.next();
				value = notEqualConditionMap.get(key);

				if (null != value) {
					criteria.add(Expression.ne(key, value));
				}
			}
		}

		//处理 in 的查询条件
		if (null != inConditionMap && 0 < inConditionMap.size()) {

			Iterator conditionIter = inConditionMap.keySet().iterator();
			String key = "";
			ArrayList value = null;

			while (conditionIter.hasNext()) {
				key = (String) conditionIter.next();
				value = (ArrayList) inConditionMap.get(key);

				if (null != value) {
					criteria.add(Expression.in(key, value));
				}
			}
		}

		//处理 null 的查询条件
		if (null != isNullList && 0 < isNullList.size()) {
			String key = "";
			for (int i = 0; i < isNullList.size(); i++) {
				key = (String) isNullList.get(i);
				criteria.add(Expression.isNull(key));
			}
		}

		//处理 not null 的查询条件
		if (null != isNotNullList && 0 < isNotNullList.size()) {
			String key = "";
			for (int i = 0; i < isNotNullList.size(); i++) {
				key = (String) isNotNullList.get(i);
				criteria.add(Expression.isNotNull(key));
			}
		}

		//处理 like 的查询条件
		if (null != likeConditionMap && 0 < likeConditionMap.size()) {

			Iterator conditionIter = likeConditionMap.keySet().iterator();
			String key = "";
			Object value = null;

			while (conditionIter.hasNext()) {
				key = (String) conditionIter.next();
				value = likeConditionMap.get(key);

				if (null != value) {
					criteria.add(Expression.like(key, "%" + value + "%"));
				}
			}
		}

		//处理between的查询条件
		if (null != betweenConditionMap && 0 < betweenConditionMap.size()) {

			Iterator conditionIter = betweenConditionMap.keySet().iterator();
			String key = "";
			HashMap value = null;
			Object firstValue = null;
			Boolean firstEqual = null;
			Object secondValue = null;
			Boolean secondEqual = null;

			while (conditionIter.hasNext()) {
				key = (String) conditionIter.next();
				value = (HashMap) betweenConditionMap.get(key);

				if (null != value) {
					firstValue = value.get(ConditionData.BETWEEN_FIRST);
					firstEqual = (Boolean) value
							.get(ConditionData.BETWEEN_FIRST_EQUAL);
					secondValue = value.get(ConditionData.BETWEEN_SECOND);
					secondEqual = (Boolean) value
							.get(ConditionData.BETWEEN_SECOND_EQUAL);

					if (null != firstValue) {
						//判断是否取等号
						if (firstEqual.booleanValue()) {
							criteria.add(Expression.ge(key, firstValue));
						} else {
							criteria.add(Expression.gt(key, firstValue));
						}
					}

					if (null != secondValue) {
						//判断是否取等号
						if (secondEqual.booleanValue()) {
							criteria.add(Expression.le(key, secondValue));
						} else {
							criteria.add(Expression.lt(key, secondValue));
						}
					}
				}
			}
		}

		//处理排序
		if (null != orderList && 0 < orderList.size()) {

			HashMap orderMap;
			String name = "";
			String type = "";
			for (int i = 0; i < orderList.size(); i++) {
				orderMap = orderList.get(i);
				name = (String) orderMap.get(ConditionData.ORDER_NAME);
				type = (String) orderMap.get(ConditionData.ORDER_TYPE);

				if (ConditionData.ORDER_TYPE_ASC.equalsIgnoreCase(type)) {
					criteria.addOrder(Order.asc(name));
				} else if (ConditionData.ORDER_TYPE_DESC.equalsIgnoreCase(type)) {
					criteria.addOrder(Order.desc(name));
				}
			}
		}

		return criteria;
	}

}
