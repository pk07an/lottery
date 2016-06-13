package com.npc.lottery.statreport.entity;

import java.io.Serializable;

public class ReportStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ID;//ID
	
	private String opt;//报表是否使用新方法计算，在总官里设置，0：关闭 1：开启，默认为关闭

    private String status;//报表计算的状态，0：未成功 1：成功，默认为未成功

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
