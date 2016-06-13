package com.npc.lottery.member.entity;

import java.io.Serializable;
import java.sql.Date;
public class PlayWinInfo  implements Serializable{
	private static final long serialVersionUID = 1L;

	    //primary key
		private Long id;

		// fields
		private String typeCode;
		private String playType;
		private String periodsNum;
		private String win;
		private Date  updateTime;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public java.lang.String getTypeCode() {
			return typeCode;
		}
		public void setTypeCode(java.lang.String typeCode) {
			this.typeCode = typeCode;
		}
		public String getPlayType() {
			return playType;
		}
		public void setPlayType(String playType) {
			this.playType = playType;
		}
		public String getPeriodsNum() {
			return periodsNum;
		}
		public void setPeriodsNum(String periodsNum) {
			this.periodsNum = periodsNum;
		}
		public String getWin() {
			return win;
		}
		public void setWin(String win) {
			this.win = win;
		}
		public Date getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		
		
			
			
		}
	
		
		