package com.npc.lottery.member.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CancelPetLog implements Serializable, Comparable<CancelPetLog> {
	private static final long serialVersionUID = -8938875985960083150L;

	private java.lang.Long ID;
	private java.lang.Long tableId;
    private Timestamp createDate;//创建日期
    private java.lang.String billType;//注单类型  填GDKLSF等或R(代表补货)
    private java.lang.String type;//类型，1：注销 ，2：恢复
    private java.lang.String IP;//IP地址
    
    public static final String OP_TYPE_CANCEL = "1"; // 注銷
    public static final String OP_TYPE_RECOVERY = "2"; // 恢復
    
	public java.lang.Long getID() {
		return ID;
	}

	public void setID(java.lang.Long iD) {
		ID = iD;
	}

	public java.lang.Long getTableId() {
		return tableId;
	}

	public void setTableId(java.lang.Long tableId) {
		this.tableId = tableId;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}
	public String getCreateDateDis() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(createDate);
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public java.lang.String getBillType() {
		return billType;
	}

	public void setBillType(java.lang.String billType) {
		this.billType = billType;
	}

	public java.lang.String getType() {
		return type;
	}
	public java.lang.String getTypeName() {//1：注销 ，2：恢复
		if(this.OP_TYPE_CANCEL.equals(type)){
			return "注销";
		}else{
			return "恢复";
			
		}
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.lang.String getIP() {
		return IP;
	}

	public void setIP(java.lang.String iP) {
		IP = iP;
	}

	@Override
	public int compareTo(CancelPetLog o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
