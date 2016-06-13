package com.npc.lottery.member.entity;

import java.io.Serializable;

public class BalanceInfo implements Serializable{

    
    /**
     * 
     */
    private static final long serialVersionUID = 6519183627445976573L;
    private String  TransactionType;  //交易日期
    private Integer wagersOn;          //注單筆數
    private Double  wagering;          //下注金額
    private Double  bunkoResults;       //輸贏結果
    private Double  recession;          //退水
    private Double  recessionResults;   //退水後結果
    public String getTransactionType() {
        return TransactionType;
    }
    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }
    public Integer getWagersOn() {
        return wagersOn;
    }
    public void setWagersOn(Integer wagersOn) {
        this.wagersOn = wagersOn;
    }
    public Double getWagering() {
        return wagering;
    }
    public void setWagering(Double wagering) {
        this.wagering = wagering;
    }
    public Double getBunkoResults() {
        return bunkoResults;
    }
    public void setBunkoResults(Double bunkoResults) {
        this.bunkoResults = bunkoResults;
    }
    public Double getRecession() {
        return recession;
    }
    public void setRecession(Double recession) {
        this.recession = recession;
    }
    public Double getRecessionResults() {
        return recessionResults;
    }
    public void setRecessionResults(Double recessionResults) {
        this.recessionResults = recessionResults;
    }
    
    
}
