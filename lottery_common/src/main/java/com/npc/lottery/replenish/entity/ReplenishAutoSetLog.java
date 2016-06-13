package com.npc.lottery.replenish.entity;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.sysmge.entity.ManagerStaff;


public class ReplenishAutoSetLog extends BaseLotteryAction{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7017105286138845884L;
	private Long ID;
	private Long shopID;
	private String type;
	private String typeCode;
	private Integer moneyOrgin = 0;
	private Integer moneyNew = 0;
	private long createUserID;
	private String createUserName;
	private Integer createUserType;
	private Date createTime = new Date(System.currentTimeMillis());
	private String stateOrgin;
	private String stateNew;
	private String ip;
	public PlayType playType;
	public ShopsInfo shopsInfo;
	
	public ManagerStaff userAccount;
	
	// add by peter for log start
	private String				changeType;
	private String				changeSubType;
	private String				orginalValue;
	private String				newValue;
	private long				updateUserID;
	private int					updateUserType;
	private String				updateUserName;
	// add by peter for log end
	
	public ReplenishAutoSetLog()
	{
	}
	public ReplenishAutoSetLog(Long shopID,String type,String typeCode,Integer moneyOrgin,Integer moneyNew,long createUserID,Integer createUserType,Date createTime,String state)
	{
		this.setShopID(shopID);
		this.setType(type);
		this.setTypeCode(typeCode);
		this.setMoneyOrgin(moneyOrgin);
		this.setMoneyNew(moneyNew);
		this.setCreateUserID(createUserID);
		this.setCreateUserType(createUserType);
		this.setCreateTime(createTime);
	}
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public Long getShopID() {
		return shopID;
	}
	public void setShopID(Long shopID) {
		this.shopID = shopID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public Integer getMoneyOrgin() {
		return moneyOrgin;
	}
	public void setMoneyOrgin(Integer moneyOrgin) {
		this.moneyOrgin = moneyOrgin;
	}
	public Integer getMoneyNew() {
		return moneyNew;
	}
	public void setMoneyNew(Integer moneyNew) {
		this.moneyNew = moneyNew;
	}
	public long getCreateUserID() {
		return createUserID;
	}
	public void setCreateUserID(long createUserID) {
		this.createUserID = createUserID;
	}
	public Integer getCreateUserType() {
		return createUserType;
	}
	public void setCreateUserType(Integer createUserType) {
		this.createUserType = createUserType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public PlayType getPlayType() {
		return playType;
	}
	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}
	public ShopsInfo getShopsInfo() {
		return shopsInfo;
	}
	public void setShopsInfo(ShopsInfo shopsInfo) {
		this.shopsInfo = shopsInfo;
	}
	public ManagerStaff getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(ManagerStaff userAccount) {
		this.userAccount = userAccount;
	}
	public String getStateOrgin() {
		return stateOrgin;
	}
	public void setStateOrgin(String stateOrgin) {
		this.stateOrgin = stateOrgin;
	}
	public String getStateNew() {
		return stateNew;
	}
	public void setStateNew(String stateNew) {
		this.stateNew = stateNew;
	}
	public String getTypeName() {
		String name = "";
		if("BJ".equals(this.type) || "BJSC".equals(this.type)) name = "北京賽車(PK10)";
		if("GDKLSF".equals(this.type)) name = "廣東快樂十分";
		if("CQSSC".equals(this.type)) name = "重慶時時彩";
		if("NC".equals(this.type)) name = "幸運農場";
		if(Constant.LOTTERY_TYPE_K3.equals(this.type)) name = "江苏骰寶(K3)";
		return name;
	}
	public void setTypeName(String typeName) {
	}
	public String getSubTypeName() {
		
		String name = "";
		Map<String, String> bj = Constant.BJSC_REPLENISHAUTOSETLOG_TYPE;
		Map<String, String> gd = Constant.GDKLSF_REPLENISHAUTOSETLOG_TYPE;
		Map<String, String> cq = Constant.CQSSC_REPLENISHAUTOSETLOG_TYPE;
		Map<String, String> k3 = Constant.K3_REPLENISHAUTOSETLOG_TYPE;
		Map<String, String> nc = Constant.NC_REPLENISHAUTOSETLOG_TYPE;
		//System.out.println(typeCode);
		if(this.typeCode.indexOf("GDKLSF")!=-1 || this.typeCode.indexOf("GD_") != -1){
			name = gd.get(typeCode);
		}else if(this.typeCode.indexOf("CQSSC")!=-1 || this.typeCode.indexOf("CQ_") != -1){
			name = cq.get(typeCode);
		}else if(this.typeCode.indexOf("BJ")!=-1){
			name = bj.get(typeCode);
		}else if(this.typeCode.indexOf("K3")!=-1){
			name = k3.get(typeCode);
		}else if(this.typeCode.indexOf("NC")!=-1){
			name = nc.get(typeCode);
		}
		// add by peter
		if (StringUtils.isEmpty(name)) {
			Map<String, String> bjCommission = Constant.BJSC_COMMISSION_TYPE;
			Map<String, String> gdCommission = Constant.GDKLSF_COMMISSION_TYPE;
			Map<String, String> cqCommission = Constant.CQSSC_COMMISSION_TYPE;
			Map<String, String> k3Commission = Constant.K3_COMMISSION_TYPE;
			Map<String, String> ncCommission = Constant.NC_COMMISSION_TYPE;
			if (this.typeCode.indexOf("GDKLSF") != -1 || this.typeCode.indexOf("GD_") != -1) {
				name = gdCommission.get(typeCode);
			} else if (this.typeCode.indexOf("CQSSC") != -1 || this.typeCode.indexOf("CQ_") != -1 ) {
				name = cqCommission.get(typeCode);
			} else if (this.typeCode.indexOf("BJ") != -1) {
				name = bjCommission.get(typeCode);
			} else if(this.typeCode.indexOf("K3")!=-1){
				name = k3Commission.get(typeCode);
			} else if(this.typeCode.indexOf("NC")!=-1){
				name = ncCommission.get(typeCode);
			}
		}
		return name;
	}
	public void setSubTypeName(String subTypeName) {
	}
	public String getIp() {
		return ip;
	}
	public String getIpBelongTo() {
		return this.getIpAddress(this.ip);
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getChangeSubType() {
		return changeSubType;
	}
	public void setChangeSubType(String changeSubType) {
		this.changeSubType = changeSubType;
	}
	public String getOrginalValue() {
		return orginalValue;
	}
	public void setOrginalValue(String orginalValue) {
		this.orginalValue = orginalValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public long getUpdateUserID() {
		return updateUserID;
	}
	public void setUpdateUserID(long updateUserID) {
		this.updateUserID = updateUserID;
	}
	public int getUpdateUserType() {
		return updateUserType;
	}
	public void setUpdateUserType(int updateUserType) {
		this.updateUserType = updateUserType;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	
	
}