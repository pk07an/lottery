package com.npc.lottery.sysmge.logic.spring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IParamDao;
import com.npc.lottery.sysmge.entity.Param;
import com.npc.lottery.sysmge.entity.ParamValue;
import com.npc.lottery.sysmge.logic.interf.IParamLogic;
import com.npc.lottery.sysmge.logic.interf.IParamValueLogic;
import com.npc.lottery.util.Tool;

/**
 * 参数类别表
 * 
 * 
 */
public class ParamLogic implements IParamLogic {

	private IParamValueLogic paramValueLogic = null;

	private IParamDao paramDao = null;

	public void setParamValueLogic(IParamValueLogic paramValueLogic) {
		this.paramValueLogic = paramValueLogic;
	}

	public void setParamDao(IParamDao paramDao) {
		this.paramDao = paramDao;
	}

	public IParamDao getParamDao() {
		return paramDao;
	}

	/**
	 * 查询满足指定查询条件的功能信息数据记录
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @param pageCurrentNo
	 *            第一页为 1 需要查询的页码
	 * @param pageSize
	 *            页面大小
	 * @return Param 类型的 List
	 */
	public List findParamList(ConditionData conditionData, int pageCurrentNo,
			int pageSize) {
		List resultList = null;

		// 根据页码和页面大小，获得需要查询的开始数据和数量
		int firstResult = (pageCurrentNo - 1) * pageSize;
		int maxResults = pageSize;

		// 查询数据
		try {
			resultList = paramDao.findParamList(conditionData, firstResult,
					maxResults);
		} catch (Exception ex) {
			Tool.printExceptionStack(ex);
		}

		return resultList;
	}

	/**
	 * 根据编码，查询对应的参数值
	 * 
	 * @param code
	 * @return 不存在数据则返回null
	 */
	public ArrayList<ParamValue> findParamValueByCode(String code) {

		// 构造查询条件
		ConditionData conditionData = new ConditionData();
		conditionData.addEqual("code", code);
		List paramList = this.findParamList(conditionData, 1, 1000);

		if (null == paramList || paramList.size() == 0) {
			return null;
		}

		Param paramEntity = (Param) paramList.get(0);

		// 查询参数值
		conditionData = new ConditionData();
		conditionData.addEqual("param.ID", paramEntity.getID());
		ArrayList<ParamValue> resultList = paramValueLogic.findParamValueList(
				conditionData, 1, 1000);

		return resultList;
	}

	/**
	 * 统计满足指定查询条件的Param记录数目
	 * 
	 * @param conditionData
	 *            查询条件信息
	 * @return
	 */
	public long findAmountParam(ConditionData conditionData) {
		long amount = 0;

		try {
			amount = paramDao.findAmountParam(conditionData);
		} catch (Exception ex) {
			Tool.printExceptionStack(ex);
		}

		return amount;
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public Param getParamByID(Long id) {
		return paramDao.getParamByID(id);
	}

	/**
	 * 保存或更新对象
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(Param entity) {
		paramDao.saveOrUpdate(entity);
	}

	/**
	 * 删除对象
	 * 
	 * @param entity
	 */
	public void delete(Param entity) {
		paramDao.delete(entity);
	}

	/**
	 * 根据tb_frame_param的任意字段取tb_frame_param_value的记录
	 * 
	 * @param code
	 */
	public LinkedHashMap<String, String> getParamValue(String fieldName,
			String fieldValue) {
		LinkedHashMap<String, String> linkHashMap = new LinkedHashMap<String, String>();
		ConditionData conditionData = new ConditionData();
		conditionData.addEqual(fieldName, fieldValue);
		List list = findParamList(conditionData, 1, 99999);
		Param param = (Param) list.get(0);

		if (param != null) {

			Set set = param.getParamValues();
			Iterator iterator = set.iterator();
			List<ParamValue> sortlist = new ArrayList<ParamValue>();
			while (iterator.hasNext()) {
				ParamValue paramValue = (ParamValue) iterator.next();
				sortlist.add(paramValue);
				//paramValueIDArray[i] = paramValue.getID();
				//i++;
				// linkHashMap.put(paramValue.getValue(),paramValue.getName());
			}
			List<ParamValue> resultList = sort(sortlist, sortlist.size() - 1);
			for (int j = 0; j < resultList.size(); j++) {
				ParamValue paramValue = (ParamValue) resultList.get(j);
				if (paramValue != null)
					linkHashMap
							.put(paramValue.getValue(), paramValue.getName());
			}
		}
		return linkHashMap;
	}

	private List<ParamValue> sort(List<ParamValue> sortList, int size) {
		int i, j, w;
		for (i = 0; i <= size - 1; i++)
			for (j = size; j >= i + 1; j--)
				if (sortList.get(j).getID().compareTo(
						sortList.get(j - 1).getID()) < 0) /* 比较 */
				{ /* r[j]与r[j-1]进行交换 */
					ParamValue paramValue = sortList.get(j);
					sortList.set(j, sortList.get(j - 1));
					sortList.set(j - 1, paramValue);
				}

		return sortList;
	}

}
