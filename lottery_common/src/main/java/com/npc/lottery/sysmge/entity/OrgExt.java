package com.npc.lottery.sysmge.entity;

import java.io.Serializable;

public class OrgExt implements Serializable {

	public static String SALE_TYPE_YES = "000";//是否销售部门（营销机构）：是

	public static String SALE_TYPE_NO = "001";//是否销售部门（营销机构）：否

	private Long orgID;

	private String channelID;

	private String saleType;

	private Long cmmsOrgID;

	private String cmmsOrgName;

	private String orgType1;

	private String orgType2;

	private Long accessOrg;

	public Long getAccessOrg() {
		return accessOrg;
	}

	public void setAccessOrg(Long accessOrg) {
		this.accessOrg = accessOrg;
	}

	public String getChannelID() {
		return channelID;
	}

	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}

	public Long getCmmsOrgID() {
		return cmmsOrgID;
	}

	public void setCmmsOrgID(Long cmmsOrgID) {
		this.cmmsOrgID = cmmsOrgID;
	}

	public String getCmmsOrgName() {
		return cmmsOrgName;
	}

	public void setCmmsOrgName(String cmmsOrgName) {
		this.cmmsOrgName = cmmsOrgName;
	}

	public Long getOrgID() {
		return orgID;
	}

	public void setOrgID(Long orgID) {
		this.orgID = orgID;
	}

	public String getOrgType1() {
		return orgType1;
	}

	public void setOrgType1(String orgType1) {
		this.orgType1 = orgType1;
	}

	public String getOrgType2() {
		return orgType2;
	}

	public void setOrgType2(String orgType2) {
		this.orgType2 = orgType2;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

}
