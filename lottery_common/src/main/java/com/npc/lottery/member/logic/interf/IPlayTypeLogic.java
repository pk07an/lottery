package com.npc.lottery.member.logic.interf;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.member.entity.PlayType;

/**
 * 功能逻辑处理类
 * 
 * @author none
 * 
 */
public interface IPlayTypeLogic {

	public List<PlayType> findPlayType(Criterion... criterions);

	public PlayType getPlayTypeByTypeCode(String typeCode);

	public List<PlayType> findWinInfoPlayType(String playType);

	public void updatePlayTypeAmountZero(String playType, String scheme);

}
