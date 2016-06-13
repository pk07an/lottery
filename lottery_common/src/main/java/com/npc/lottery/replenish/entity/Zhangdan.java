package com.npc.lottery.replenish.entity;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.util.PlayTypeUtils;

/**
 * 
 * 帳單实体类
 * 
 */
public class Zhangdan implements Serializable, Cloneable {

    private Long turnover;//成交笔数

    private Double amount;//投注总额

    private String typeCode;//投注类型
    private String typeCodeName;//投注类型

    
    public String getTypeCodeName() {

    	typeCodeName = PlayTypeUtils.getPlayTypeSubName(this.typeCode);

    	typeCodeName = typeCodeName + "『"
                + PlayTypeUtils.getPlayTypeFinalName(this.typeCode) + "』";

        return typeCodeName;
    }
    
    public Long getTurnover() {
        return turnover;
    }

    public void setTurnover(Long turnover) {
        this.turnover = turnover;
    }

    public Double getAmount() {
        return amount;
    }

    public String getAmountDis() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(amount);
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
}
