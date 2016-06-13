package com.npc.lottery.member.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class CancelPet implements Serializable, Comparable<CancelPet> {
	private static final long serialVersionUID = -8938875985960083150L;

	private java.lang.Long ID;
	private java.lang.Long tableId;
    private java.lang.String winState;//兑奖结果
    private Timestamp createDate;//创建日期
    private java.lang.String billType;//注单类型  填GDKLSF等或R(代表补货)
    
    public static final String CANCEL_PET_PLAY_TYPE_GDKLSF = "GDKLSF"; // 广东快乐十分
    public static final String CANCEL_PET_PLAY_TYPE_NC = "NC"; // 幸运农场
    public static final String CANCEL_PET_PLAY_TYPE_BJ = "BJ"; // 北京赛车
    public static final String CANCEL_PET_PLAY_TYPE_CQSSC = "CQSSC"; // 重庆时时彩
    public static final String CANCEL_PET_PLAY_TYPE_K3 = "K3"; // 江苏骰寶(快3)
    public static final String CANCEL_PET_PLAY_TYPE_R = "R"; // 補貨
    
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

	public java.lang.String getWinState() {
		return winState;
	}

	public void setWinState(java.lang.String winState) {
		this.winState = winState;
	}

	public Timestamp getCreateDate() {
		return createDate;
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

	@Override
	public int compareTo(CancelPet o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
