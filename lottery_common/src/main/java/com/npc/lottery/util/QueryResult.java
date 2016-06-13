package com.npc.lottery.util;

import java.util.List;

/***
 * 用于返回检索结果
 * @author 刘忍让
 * 
 */
public class QueryResult {
	private int recordCount;
	private List recordList;
	private long titleCount;
	private long keyCount;

	public long getKeyCount() {
		return keyCount;
	}

	public void setKeyCount(long keyCount) {
		this.keyCount = keyCount;
	}

	public long getTitleCount() {
		return titleCount;
	}

	public void setTitleCount(long titleCount) {
		this.titleCount = titleCount;
	}

	public QueryResult(int recordCount, long titleCount, long keyCount,
			List recordList) {
		super();
		this.recordCount = recordCount;
		this.titleCount = titleCount;
		this.keyCount = keyCount;
		this.recordList = recordList;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = (int) titleCount + (int) keyCount;
	}

	public List getRecordList() {
		return recordList;
	}

	public void setRecordList(List recordList) {
		this.recordList = recordList;
	}

}
