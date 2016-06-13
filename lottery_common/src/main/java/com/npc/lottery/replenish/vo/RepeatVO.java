package com.npc.lottery.replenish.vo;

import java.io.Serializable;

public class RepeatVO implements Serializable {
 
   private static final long serialVersionUID = 1L;
   private Integer pKey;
   private Integer dKey;
   private String isSame = "f";
   
public Integer getpKey() {
	return pKey;
}
public Integer getdKey() {
	return dKey;
}
public String getIsSame() {
	return isSame;
}
public void setpKey(Integer pKey) {
	this.pKey = pKey;
}
public void setdKey(Integer dKey) {
	this.dKey = dKey;
}
public void setIsSame(String isSame) {
	this.isSame = isSame;
}
   
}
