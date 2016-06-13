package com.npc.lottery.replenish.logic.interf;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.replenish.entity.ReplenishAuto;
import com.npc.lottery.replenish.vo.AutoReplenishSetVO;
import com.npc.lottery.sysmge.entity.ManagerUser;

public interface IReplenishAutoLogic {

	public List<ReplenishAuto> listReplenishAutoList(Criterion... criterions);
	public void updateReplenishAuto(ReplenishAuto entity);
	public void updateReplenishAuto(List<ReplenishAuto> entityList);
	public ReplenishAuto queryReplenishAuto(Long shopID, String typeCode,Integer createUser);

	/*public void updateReplenishAutoHK(Long shopID, String periodsNum, String type,
			Long userID);
	public void updateReplenishAutoGD(Long shopID, String periodsNum, String type,
			Long userID);
	public void updatReplenishAutoCQ(Long shopID, String periodsNum, String type,
			Long userID);*/
	//public void updateReplenishAutoHK();
	public void updateReplenishAutoGD();
	public void updateReplenishAutoCQ();
	public void updateReplenishAutoBJ();
	
	//****************实时触发的自动补货START***************
	public void updateReplenishAutoGDMenu(String periodsNum, String typeCode,
			ShopsInfo shopsInfo,String plate);
	public void updateReplenishAutoCQMenu(String periodsNum, String typeCode,
			ShopsInfo shopsInfo,String plate);
	public void updateReplenishAutoBJMenu(String periodsNum, String typeCode,
			ShopsInfo shopsInfo, String plate);
	//****************实时触发的自动补货END***************
	
	//获取所有玩法的实货额,并以自动补货为条件分组查出最大项
    public Map<String, String> queryTotalTrueMoney(ManagerUser userInfo);
    //查询每一项的最低投注额显示在起补额度里
	public Map<String, Integer> queryLowestMoney(Long userID);
	
	//把该用户的自动补货设置改为state,Constant.OPEN,Coonstant.CLOSE
	public void updateReplenishAutoSetByUser(String shopsID, Long userID,
			String userType, String state);
	
	public void updateReplenishAutoForUser(BaseBet betOrder);
	
	/**
	 * 新方法，异步补货
	 * @param betOrder
	 * @param scheme
	 */
	public void updateReplenishAutoForUser(BaseBet betOrder,String scheme);
	/**
	 * 手动补货触发的自动补货
	 * @param betOrder
	 * @param userInfo
	 * 必须准备的参数
	 * betOrder:
	 * periodsNum|playType|branchStaff|stockholderStaff
	 * |GenAgenStaff|AgentStaff|ShopInfo().getID|Plate|ShopCode
	 * |BranchRate|StockHolderRate|GenAgenRate|splitAttribute|
	 * 
	 */
	public void updateReplenishAutoForMenu(BaseBet betOrder,ManagerUser userInfo);
	//查询某一个用户的自动补货设置,条件是已打开设置的。
	public AutoReplenishSetVO queryReplenishAutoSetForUser(Long userID,String shopID,String typeCode);
	
	public void updateReplenishAutoSetForClose(String shopsID, Long userID,String userType, String state);
    
}
