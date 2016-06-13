package com.npc.lottery.sysmge.entity;

import java.io.Serializable;
import java.sql.Date;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.ConstantBusiness;

/**
 * 组织机构，对应数据表 TB_FRAME_ORG
 *
 * @author none
 *
 */
public class Org implements Serializable {

	public static long ORG_TYPE_COMPANY = 0;//ORG_TYPE：公司

	public static long ORG_TYPE_FILIALE = 1;//ORG_TYPE：分公司

	public static long ORG_TYPE_DEPARTMENTS = 2;//ORG_TYPE：部门

	public static long ORG_TYPE_DIVISIONS = 3;//ORG_TYPE：处室

	public static long ORG_TYPE_SECTIONS = 4;//ORG_TYPE：科组 

	public static long ORG_TYPE_CENTER = 5;//ORG_TYPE：中心 

	private Long orgID;

	private Long uporgID;

	private String upOrgName;//父节点机构名称

	private String adouName;

	private String orgName;

	private String shortName;

	private String showOrder;

	private Long orgType;

	private String orgArea;

	private Long suborgNum;

	private String sapID;

	private String isRealorg;

	private Date createDate;

	private OrgExt orgExtEntity = null;

	public String getAdouName() {
		return adouName;
	}

	public void setAdouName(String adouName) {
		this.adouName = adouName;
	}

	public String getOrgArea() {
		return orgArea;
	}

	public void setOrgArea(String orgArea) {
		this.orgArea = orgArea;
	}

	public Long getOrgID() {
		return orgID;
	}

	public void setOrgID(Long orgID) {
		this.orgID = orgID;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getOrgType() {
		return orgType;
	}

	/**
	 * orgType 对应的英文值
	 * 
	 * @return
	 */
	public String getOrgTypeEnName() {
		String orgTypeName = "error Data";

		switch (orgType.intValue()) {
		case 0:
			orgTypeName = "Company";
			break;
		case 1:
			orgTypeName = "Filiale";
			break;
		case 2:
			orgTypeName = "Departments";
			break;
		case 3:
			orgTypeName = "Divisions";
			break;
		case 4:
			orgTypeName = "Sections";
			break;
		case 5:
			orgTypeName = "Center";
			break;
		}

		return orgTypeName;
	}

	/**
	 * orgType 对应的中文值
	 * 
	 * @return
	 */
	public String getOrgTypeChName() {
		String orgTypeName = "<font color='#FF0000'>异常数据</font>";

		switch (orgType.intValue()) {
		case 0:
			orgTypeName = "公司";
			break;
		case 1:
			orgTypeName = "分公司";
			break;
		case 2:
			orgTypeName = "部门";
			break;
		case 3:
			orgTypeName = "处室";
			break;
		case 4:
			orgTypeName = "科组";
			break;
		case 5:
			orgTypeName = "中心";
			break;
		}

		return orgTypeName;
	}

	public void setOrgType(Long orgType) {
		this.orgType = orgType;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(String showOrder) {
		this.showOrder = showOrder;
	}

	public Long getUporgID() {
		return uporgID;
	}

	/**
	 * 将数据表中数据的根节点的父ID转换为页面上显示所需要的根节点父ID
	 * 
	 * @return
	 */
	public Long getUporgID2() {

		//将数据中的根节点所对应的父ID替换为页面所需要的值
		if (ConstantBusiness.ORG_ROOT_DATA_ID != orgID) {
			return uporgID;
		} else {
			return Constant.PAGE_TREE_ROOT_PARENT_ID;
		}
	}

	public void setUporgID(Long uporgID) {
		this.uporgID = uporgID;
	}

	/**
	 * 获取父节点名称
	 */
	public void setUpOrgName(String upOrgName) {
		this.upOrgName = upOrgName;
	}

	public String getUpOrgName() {
		return upOrgName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getIsRealorg() {
		return isRealorg;
	}

	public void setIsRealorg(String isRealorg) {
		this.isRealorg = isRealorg;
	}

	public String getSapID() {
		return sapID;
	}

	public void setSapID(String sapID) {
		this.sapID = sapID;
	}

	public Long getSuborgNum() {
		return suborgNum;
	}

	public void setSuborgNum(Long suborgNum) {
		this.suborgNum = suborgNum;
	}

	public OrgExt getOrgExtEntity() {
		return orgExtEntity;
	}

	public void setOrgExtEntity(OrgExt orgExtEntity) {
		this.orgExtEntity = orgExtEntity;
	}

}
