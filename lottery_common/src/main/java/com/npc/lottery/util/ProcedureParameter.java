package com.npc.lottery.util;

public class ProcedureParameter {

	// 参数类型
	private int number;// 参数据位置

	private boolean isOutType;// 是否为输出类型

	private int type = 0;// 参数类型

	private String value;// 参数传入时值统一用String类型，会根据type自动转换

	public ProcedureParameter() {

	}

	public ProcedureParameter(int number, boolean isOutType, int type,
			String value) {
		this.number = number;
		this.isOutType = isOutType;
		this.type = type;
		this.value = value;
	}

	public boolean isOutType() {
		return isOutType;
	}

	public void setOutType(boolean isOutType) {
		this.isOutType = isOutType;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
