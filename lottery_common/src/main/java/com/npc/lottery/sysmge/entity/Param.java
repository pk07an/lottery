package com.npc.lottery.sysmge.entity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * 参数类别表
 * 
 */

public class Param implements java.io.Serializable {

	// Fields

	private Long ID;
	private String code;
	private String name;
	private String valueType;
	private Param parentParam;
	private String remark;
	private String type;
	private String state;
	private Set paramValues;

	//VALUE_TYPE 参数值类型，1-整型；2-字符类型；9-其他；默认值9；此字段暂时不使用
	public static final String VALUE_TYPE_INTEGER = "1";
	public static final String VALUE_TYPE_CHAR = "2";
	public static final String VALUE_TYPE_OTHER = "9";

	//TYPE 参数类型，1-系统初始化参数；2-普通参数
	public static final String TYPE_SYSTEM = "1";
	public static final String TYPE_ORDINARY = "2";

	//STATE 状态，0-启用；1-未启用；默认值0
	public static final String STATE_USE = "0";
	public static final String STATE_UNUSE = "1";

	// Property accessors
	public Long getID() {
		return ID;
	}

	public void setID(Long id) {
		ID = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static HashMap getValueTypeMap(boolean canBlank) {
		HashMap resultMap = new HashMap();
		if (canBlank)
			resultMap.put(null, null);
		resultMap.put(Param.VALUE_TYPE_CHAR, "字符型");
		resultMap.put(Param.VALUE_TYPE_INTEGER, "整形");
		resultMap.put(Param.VALUE_TYPE_OTHER, "其他");
		return resultMap;

	}

	public HashMap getValueTypeMap() {
		return getValueTypeMap(false);
	}

	public String getValueTypeName() {
		String valueTypeName = "";
		if (valueType.equals(Param.VALUE_TYPE_CHAR))
			valueTypeName = "字符型";
		else if (valueType.equals(Param.VALUE_TYPE_INTEGER))
			valueTypeName = "整形";
		else if (valueType.equals(Param.VALUE_TYPE_OTHER))
			valueTypeName = "其他";
		return valueTypeName;
	}

	public String getValueType() {
		return this.valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Param getParentParam() {
		return parentParam;
	}

	public void setParentParam(Param parentParam) {
		this.parentParam = parentParam;
	}

	public static HashMap getTypeMap(boolean canBlank) {
		HashMap resultMap = new HashMap();
		if (canBlank)
			resultMap.put(null, null);
		resultMap.put(Param.TYPE_ORDINARY, "普通参数");
		resultMap.put(Param.TYPE_SYSTEM, "系统初始化参数");
		return resultMap;
	}

	public HashMap getTypeMap() {
		return getTypeMap(false);
	}

	public String getTypeName() {
		String typeName = "";
		if (type.equals(Param.TYPE_ORDINARY))
			typeName = "普通参数";
		else if (type.equals(Param.TYPE_SYSTEM))
			typeName = "系统初始化参数";
		return typeName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static LinkedHashMap getStateMap(boolean canBlank) {
		LinkedHashMap resultMap = new LinkedHashMap();
		if (canBlank)
			resultMap.put(null, null);
		resultMap.put(Param.STATE_USE, "启用");
		resultMap.put(Param.STATE_UNUSE, "禁用");
		return resultMap;
	}

	public LinkedHashMap getStateMap() {
		return getStateMap(false);
	}

	public String getStateName() {
		String stateName = "<font color='FF0000'>数据错误</font>";

		if (STATE_USE.equalsIgnoreCase(state.trim())) {
			stateName = "启用";
		} else if (STATE_UNUSE.equalsIgnoreCase(state.trim())) {
			stateName = "<font color='FF0000'>禁用</font>";
		}
		return stateName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set getParamValues() {
		return paramValues;
	}

	public void setParamValues(Set paramValues) {
		this.paramValues = paramValues;
	}

}