/**
 * 
 */
package com.npc.lottery.manage.logic.interf;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.npc.lottery.manage.entity.PeriodAutoOdds;
import com.npc.lottery.replenish.vo.ZhanDangVO;

/**
 * @author Administrator
 *
 */
public interface ISystemLogic {

	// public PeriodAutoOdds queryAutoOddsByType(String type) throws
	// SQLException;

	public void saveAutoOdds(PeriodAutoOdds autoOdds) throws SQLException;

	public List<PeriodAutoOdds> queryAutoOddsByTypeName(String type, String name);

	public void updateBJAutoOdds(PeriodAutoOdds periodAutoOdds, Map<String, String> map);

	public PeriodAutoOdds queryAutoOddsByTypeName(String type, String name, String shopsCode);

	public List<PeriodAutoOdds> findAutoOddsByType(String type, String shopCode);

	/**
	 * 查询当前店铺所有 将陪 设置
	 * 
	 * @param shopCode
	 * @return
	 */
	public List<PeriodAutoOdds> findAutoOddsByShopCode(String shopCode);

	/**
	 * 查询系统初始化设定里的设置值或状态
	 * 
	 * @param shopCode
	 * @param checkCode
	 *            ：
	 * @SYS_CREDITMODEL/CREDITMODEL 1:投注扣除信用余額 [不論輸贏]（凌晨2點半恢復） 0:按輸贏实时增減 "可以額度"
	 *                              （凌晨2點半恢復）
	 * @SYS_DECIMALDITIG/DECIMALDITIG "賠率"小數后保留位數
	 * @SYS_LMMAXNUM/LMMAXNUM 連碼“复式”允许最多號碼数
	 * @GDKLSF_FPTIME/FPTIME 广東快乐十分（封盘提前）
	 * @BJSC_PERIODSTATE/PERIODSTATE 北京賽車(PK-10)-是否開盤 1:開盤 0:封盤
	 * @BJSC_FPTIME/FPTIME 北京賽車-封盘提前）
	 * @return
	 */
	public String checkAutoOddsByShopCode(String shopCode, String checkCode);

	/**
	 * 查询系统初始设定里的北京或K3的盘期是开盘还是封盘
	 * 
	 * @param lotteryType
	 *            北京：Constant.LOTTERY_TYPE_BJ | 快3：Constant.LOTTERY_TYPE_K3
	 * @param shopCode
	 *            传商铺号：如7744
	 * @return 开盘：Constant.OPEN 封盘：Constant.CLOSE
	 */
	public String findPeriodState(String lotteryType, String shopCode);

	public Map<String, String> BjAutoInit();

	public Map<String, String> initCQAutoOdds();

	public void updateCQAutoOdds(PeriodAutoOdds periodAutoOdds, Map<String, String> initMap);

	public Map<String, String> initGDAutoOdds();

	public void updateGDAutoOdds(PeriodAutoOdds periodAutoOdds, Map<String, String> initMap);

	public void updateGDYLOdds(PeriodAutoOdds periodAutoOdds, Map<String, ZhanDangVO> initMap);

	public Map<String, ZhanDangVO> initGDYL();

	public void updateNCAutoOdds(PeriodAutoOdds periodAutoOdds, Map<String, String> initMap);

	public Map<String, String> initNCAutoOdds();

	public Map<String, ZhanDangVO> initNCYL();

	public void updateNCYLOdds(PeriodAutoOdds periodAutoOdds, Map<String, ZhanDangVO> initMap);

}
