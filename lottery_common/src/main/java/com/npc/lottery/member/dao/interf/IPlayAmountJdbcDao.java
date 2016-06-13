package com.npc.lottery.member.dao.interf;

import java.util.List;

import com.npc.lottery.member.entity.PlayAmount;



/**
 * 类
 *
 * @author none
 *
 */
public interface IPlayAmountJdbcDao
{
	//新建商铺时初始化tb_play_amount
	public void savePLayAmoutForOpen(String shopCode);
	public void updatePlayAmountBatchById(List<PlayAmount> playAmountList);

	public List<PlayAmount> getPlayAmountByShopCodeAndPlayType(String shopCode, String playType);

}
