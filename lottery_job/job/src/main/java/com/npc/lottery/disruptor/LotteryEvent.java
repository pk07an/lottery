package com.npc.lottery.disruptor;

import java.util.List;
import java.util.Map;

public class LotteryEvent {
	private Map<String, List<Integer>> resultMap;
	private String playType;

	public Map<String, List<Integer>> getResultMap() {
		return resultMap;
	}

	public String getPlayType() {
		return playType;
	}

	public void setResultMap(Map<String, List<Integer>> resultMap) {
		this.resultMap = resultMap;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}

}
