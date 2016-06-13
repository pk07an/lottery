package com.npc.lottery.user.dao.interf;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.user.entity.ChiefStaffExt;

public interface IChiefStaffExtDao  extends ILotteryBaseDao<Long, ChiefStaffExt>{
	/**
	 * 更新用户密码，总管恢复总监密码所用到,密码更新为用户名
	 * @param entity
	 * @param scheme
	 */
    public void updateChiefPassword(ChiefStaffExt entity,String scheme);
    
    /**
     * 根据商铺号查询总监信息，使用scheme查询
     * @param shopsCode
     * @param scheme
     * @return
     */
    public ChiefStaffExt queryChiefByShopsCode(String shopsCode,String scheme);
}
