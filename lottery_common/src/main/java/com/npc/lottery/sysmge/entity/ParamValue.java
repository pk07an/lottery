package com.npc.lottery.sysmge.entity;

/**
 * TbFRAMEParamValue entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ParamValue implements java.io.Serializable {

	// Fields

	private Long ID;
	private Param param;
	private String code;
	private String name;
	private String value;
	private String remark;
	private Long sortNum;

	// Property accessors
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

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Param getParam() {
		return param;
	}

	public void setParam(Param param) {
		this.param = param;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long id) {
		ID = id;
	}

	public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
	}

}