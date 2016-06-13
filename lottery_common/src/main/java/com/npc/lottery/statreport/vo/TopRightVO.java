package com.npc.lottery.statreport.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TopRightVO implements Serializable{
	private static final long serialVersionUID = 1L;
	//投注日期
	private Timestamp bettingDate;
	
	private Integer count;

	public Timestamp getBettingDate() {
		return bettingDate;
	}
	
	public java.sql.Date getSearchDate(){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String st = format.format(this.bettingDate);
		Date dd = null;
		try {
			dd = format.parse(st);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date date = new java.sql.Date(dd.getTime());
		return date;
	}
	
	public String getBettingDateStr() {
		DateFormat format = new SimpleDateFormat("yy-MM-dd E");
		java.sql.Date date = new java.sql.Date(this.bettingDate.getTime());
	    String st = format.format(date);
	    st = st.replace("星期", "周");
		return st;
	}

	public Integer getCount() {
		return count;
	}

	public void setBettingDate(Timestamp bettingDate) {
		this.bettingDate = bettingDate;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
