package com.npc.lottery.common;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * 查询条件数据结构
 * 
 * 该数据结构最终可组织成一个 HashMap 类型，如下所示
 * 
 *            key = equal，value 为使用 ＝ 的条件，HashMap类型；
 *            key = notEqual，value为使用 <> 的条件，HashMap类型
 *            key = in，value为使用 in 的条件，HashMap类型
 *                              key 为字段名称；value为字段的取值集合，ArrayList类型
 *            key = isNull，value为 null 的条件，ArrayList类型
 *            key = isNotNull，value为 not null 的条件，ArrayList类型
 *            key = like，value 为使用 like 的条件，HashMap类型；
 *            key = between，value为使用between的条件，HashMap类型，
 *                  但注意map的value为HashMap类型，
 *                      key = first，第一比较对象
 *                      key = firstEqual，第一比较对象是否取等号，Boolean类型
 *                      key = second，第二比较对象
 *                      key = secondEqual，第二比较对象是否取等号，Boolean类型
 *            key = order，排序条件，ArrayList类型，ArrayList中存储的为 HashMap类型的值
 *                      key = name，value 为排序名称
 *                      key = type，value 为排序类型，两种取值 asc 和 desc   
 *                      
 * 注意，除了 key = isNull 和 key = isNotNull，其他的查询条件中，如果存入的值为 null，
 * 则最后的解析结果中会直接忽略此查询条件，故在存入此数据对象之前，就需要对所存入的值作必要的 null 判断
 * 
 * @author none
 *
 */
public class ConditionData {

	private static Logger log = Logger.getLogger(ConditionData.class);

	public static final String EQUAL = "equal";// 查询条件的类型：=

	public static final String NOTEQUAL = "notEqual";// 查询条件的类型：<>

	public static final String IN = "in";// 查询条件的类型：in

	public static final String ISNULL = "isNull";// 查询条件的类型：null

	public static final String ISNOTNULL = "isNotNull";// 查询条件的类型：not

	public static final String LIKE = "like";// 查询条件的类型：like

	public static final String BETWEEN = "between";// 查询条件的类型：between

	public static final String BETWEEN_FIRST = "first";// 查询条件的第一操作数

	public static final String BETWEEN_FIRST_EQUAL = "firstEqual";// 查询条件的第一操作数是否取等号

	public static final String BETWEEN_SECOND = "second";// 查询条件的第二操作数

	public static final String BETWEEN_SECOND_EQUAL = "secondEqual";// 查询条件的第二操作数是否取等号

	public static final String ORDER = "order";// 查询条件的类型：order

	public static final String ORDER_NAME = "name";// 查询条件的排序名称

	public static final String ORDER_TYPE = "type";// 查询条件的排序类型

	public static final String ORDER_TYPE_DESC = "desc";// 查询条件的排序类型：降序

	public static final String ORDER_TYPE_ASC = "asc";// 查询条件的排序类型：升序

	private HashMap<String, Object> equalMap = new HashMap<String, Object>();//= 条件的查询数据

	private HashMap<String, Object> notEqualMap = new HashMap<String, Object>();// <> 条件的查询数据

	private HashMap<String, ArrayList> inMap = new HashMap<String, ArrayList>();//构造 in 类型的查询条件

	private ArrayList<String> nullList = new ArrayList<String>();//value为 null 的条件

	private ArrayList<String> notNullList = new ArrayList<String>();//value为 not null 的条件

	private HashMap<String, Object> likeMap = new HashMap<String, Object>();//like条件的查询数据

	private HashMap<String, HashMap> betweenMap = new HashMap<String, HashMap>();//between查询条件

	private ArrayList<HashMap> orderList = new ArrayList();//排序条件   

	/**
	 * 增加 = 条件
	 * 过滤 null 值
	 * 
	 * @param fieldName	对应的字段名称
	 * @param value		比较的值
	 */
	public void addEqual(String fieldName, Object value) {
		if (null == value) {
			log.info("所添加的属性【" + fieldName + "】条件值为 null，系统将忽略此属性的查询条件！");
			return;
		}
		equalMap.put(fieldName, value);
	}

	/**
	 * 增加 = 条件
	 * 不过滤 null 值
	 * 
	 * @param fieldName	对应的字段名称
	 * @param value		比较的值
	 */
	public void addEqual2(String fieldName, Object value) {
		equalMap.put(fieldName, value);
	}

	/**
	 * 增加 <> 条件
	 * 过滤 null 值
	 * 
	 * @param fieldName 对应的字段名称
	 * @param value		比较的值
	 */
	public void addNotEqual(String fieldName, Object value) {
		if (null == value) {
			log.info("所添加的属性【" + fieldName + "】条件值为 null，系统将忽略此属性的查询条件！");
			return;
		}
		notEqualMap.put(fieldName, value);
	}

	/**
	 * 增加 <> 条件
	 * 不过滤 null 值
	 * 
	 * @param fieldName 对应的字段名称
	 * @param value		比较的值
	 */
	public void addNotEqual2(String fieldName, Object value) {
		notEqualMap.put(fieldName, value);
	}

	/**
	 * 增加 in 条件
	 * 
	 * @param fieldName	对应的字段名称
	 * @param valueList 比较的值，ArrayList
	 */
	public void addInList(String fieldName, ArrayList valueList) {
		//判断是否存在此字段的其他比较值
		ArrayList originList = (ArrayList) inMap.get(fieldName);
		if (null == originList) {
			inMap.put(fieldName, valueList);
		} else {
			//将新数据加入
			originList.addAll(valueList);
		}
	}

	/**
	 * 增加 in 条件
	 * 过滤 null 值
	 * 
	 * @param fieldName 对应的字段名称
	 * @param value		比较的值
	 */
	public void addIn(String fieldName, Object value) {
		if (null == value) {
			log.info("所添加的属性【" + fieldName + "】条件值为 null，系统将忽略此属性的查询条件！");
			return;
		}
		//判断是否存在此字段的其他比较值
		ArrayList originList = (ArrayList) inMap.get(fieldName);
		if (null == originList) {
			//不存在则新建一个List存放
			originList = new ArrayList();
			originList.add(value);
			inMap.put(fieldName, originList);
		} else {
			//将新数据加入
			originList.add(value);
		}
	}

	/**
	 * 增加 in 条件
	 * 不过滤 null 值
	 * 
	 * @param fieldName 对应的字段名称
	 * @param value		比较的值
	 */
	public void addIn2(String fieldName, Object value) {
		//判断是否存在此字段的其他比较值
		ArrayList originList = (ArrayList) inMap.get(fieldName);
		if (null == originList) {
			//不存在则新建一个List存放
			originList = new ArrayList();
			originList.add(value);
			inMap.put(fieldName, originList);
		} else {
			//将新数据加入
			originList.add(value);
		}
	}

	/**
	 * 增加 null 条件
	 * 
	 * @param fieldName	对应的字段名称
	 */
	public void addNull(String fieldName) {
		nullList.add(fieldName);
	}

	/**
	 * 增加 not null 条件
	 * 
	 * @param fieldName 对应的字段名称
	 */
	public void addNotNull(String fieldName) {
		notNullList.add(fieldName);
	}

	/**
	 * 增加 like 条件
	 * 过滤 null 值
	 * 
	 * @param fieldName	对应的字段名称
	 * @param value		比较的值
	 */
	public void addLike(String fieldName, Object value) {
		if (null == value) {
			log.info("所添加的属性【" + fieldName + "】条件值为 null，系统将忽略此属性的查询条件！");
			return;
		}
		likeMap.put(fieldName, value);
	}

	/**
	 * 增加 like 条件
	 * 不过滤 null 值
	 * 
	 * @param fieldName	对应的字段名称
	 * @param value		比较的值
	 */
	public void addLike2(String fieldName, Object value) {
		likeMap.put(fieldName, value);
	}

	/**
	 * 增加 between 条件
	 * 
	 * @param fieldName		对应的字段名称
	 * @param firstValue	小值
	 * @param isFirstEqual	小值是否取等号
	 * @param secondValue	大值
	 * @param isSecondEqual	大值是否去等号
	 */
	public void addBetween(String fieldName, Object firstValue,
			boolean isFirstEqual, Object secondValue, boolean isSecondEqual) {
		//构造存放查询条件的集合
		HashMap<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put(BETWEEN_FIRST, firstValue);
		valueMap.put(BETWEEN_FIRST_EQUAL, new Boolean(isFirstEqual));
		valueMap.put(BETWEEN_SECOND, secondValue);
		valueMap.put(BETWEEN_SECOND_EQUAL, new Boolean(isSecondEqual));

		betweenMap.put(fieldName, valueMap);
	}

	/**
	 * 增加 order 条件
	 * 
	 * @param fieldName	对应的字段名称
	 * @param orderType	排序类型，取值为 ORDER_TYPE_DESC、ORDER_TYPE_ASC
	 */
	public void addOrder(String fieldName, String orderType) {

		HashMap orderMap = new HashMap();
		orderMap.put(ORDER_NAME, fieldName);
		orderMap.put(ORDER_TYPE, orderType);

		orderList.add(orderMap);
	}

	/**
	 * 返回查询条件的数据结构
	 * 
	 * @return
	 *            key = equal，value 为使用 ＝ 的条件，HashMap类型；
	 *            key = notEqual，value为使用 <> 的条件，HashMap类型
	 *            key = in，value为使用 in 的条件，HashMap类型
	 *                              key 为字段名称；value为字段的取值集合，ArrayList类型
	 *            key = isNull，value为 null 的条件，ArrayList类型
	 *            key = isNotNull，value为 not null 的条件，ArrayList类型
	 *            key = like，value 为使用 like 的条件，HashMap类型；
	 *            key = between，value为使用between的条件，HashMap类型，
	 *                  但注意map的value为HashMap类型，
	 *                      key = first，第一比较对象
	 *                      key = firstEqual，第一比较对象是否取等号，Boolean类型
	 *                      key = second，第二比较对象
	 *                      key = secondEqual，第二比较对象是否取等号，Boolean类型
	 *            key = order，排序条件，HashMap类型
	 *                      key = name，排序名称
	 *                      value = type，排序类型，两种取值 asc 和 desc            
	 */
	public HashMap getConditionMap() {

		HashMap conditionMap = new HashMap();

		conditionMap.put(EQUAL, equalMap);
		conditionMap.put(NOTEQUAL, notEqualMap);
		conditionMap.put(IN, inMap);
		conditionMap.put(ISNULL, nullList);
		conditionMap.put(ISNOTNULL, notNullList);
		conditionMap.put(LIKE, likeMap);
		conditionMap.put(BETWEEN, betweenMap);
		conditionMap.put(ORDER, orderList);

		return conditionMap;
	}

	/**
	 * 读取 = 条件的查询数据
	 * 
	 * @return
	 */
	public HashMap<String, Object> getEqual() {
		return equalMap;
	}

	/**
	 * 读取 <> 条件的查询数据
	 * 
	 * @return
	 */
	public HashMap<String, Object> getNotEqual() {
		return notEqualMap;
	}

	/**
	 * 读取 in 条件的查询数据
	 * 
	 * @return
	 */
	public HashMap<String, ArrayList> getIn() {
		return inMap;
	}

	/**
	 * 读取 null 条件的查询数据
	 * 
	 * @return
	 */
	public ArrayList<String> getNull() {
		return nullList;
	}

	/**
	 * 读取 not null 条件的查询数据
	 * 
	 * @return
	 */
	public ArrayList<String> getNotNull() {
		return notNullList;
	}

	/**
	 * 读取 like 条件的查询数据
	 * 
	 * @return
	 */
	public HashMap<String, Object> getLike() {
		return likeMap;
	}

	/**
	 * 读取 between 条件的查询数据
	 * 
	 * @return
	 */
	public HashMap<String, HashMap> getBetween() {
		return betweenMap;
	}

	/**
	 * 读取 Order 条件的查询数据
	 * 
	 * @return
	 */
	public ArrayList<HashMap> getOrder() {
		return orderList;
	}
}
