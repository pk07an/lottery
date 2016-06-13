package com.npc.lottery.member.dao.interf;


import java.util.List;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.member.entity.CancelPet;
import com.npc.lottery.member.entity.CancelPetLog;


public interface ICancelPetDao extends ILotteryBaseDao<Long, CancelPet>
{
    /**
     * 恢复注单
     * @param orderNo 注单号
     * @param typeCode 投注类型
     * @param periodsNum 盘期
     * @param billType 1：投注 0：补货
     * @param opType 类型，1：注销 ，2：恢复
     * @param ip 1：投注 0：补货
     */
	public void recoveryPet(String orderNo, String typeCode, String periodsNum,
			String billType, String opType, String ip);
    /**
     * 保存注銷操作
     * @param typeCode 投注类型
     * @param orderNo  注单号
     * @param periodsNum 盘期
     * @param billType 1：投注 0：补货
     * @param opType 类型，1：注销 ，2：恢复
     * @param ip
     */
	public void saveCancelPet(String typeCode, String orderNo, String periodsNum,
			String billType, String opType, String ip);

}
