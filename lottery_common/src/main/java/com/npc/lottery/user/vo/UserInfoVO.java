package com.npc.lottery.user.vo;

import java.io.Serializable;

public class UserInfoVO implements Serializable {
 
   private static final long serialVersionUID = 1L;
   private java.lang.Integer parentUserId; // 上级用户
   private java.lang.String replenishment; // 走飞，即补货。取值含义如下：0 — 允许走飞；1 — 禁止走飞
   
public java.lang.Integer getParentUserId() {
	return parentUserId;
}
public java.lang.String getReplenishment() {
	return replenishment;
}
public void setParentUserId(java.lang.Integer parentUserId) {
	this.parentUserId = parentUserId;
}
public void setReplenishment(java.lang.String replenishment) {
	this.replenishment = replenishment;
}
   

   
}
