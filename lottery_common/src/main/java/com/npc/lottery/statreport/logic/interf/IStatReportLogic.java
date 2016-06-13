package com.npc.lottery.statreport.logic.interf;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.replenish.entity.Replenish;
import com.npc.lottery.statreport.entity.DeliveryReport;
import com.npc.lottery.statreport.entity.ReplenishReport;

/**
 * 报表统计相关的逻辑处理类接口
 * 
 */
public interface IStatReportLogic {

    /**
     * 查询满足指定查询条件的数据记录
     * 
     * @param playType
     *            玩法类型
     *            
     * @return  PlayType 类型的 List
     */
    public ArrayList<PlayType> findCommissionTypeList(String playType);

    /**
     * 根据佣金类型查询对应的投注类型数据
     * 
     * @param commissionType 佣金类型
     * @return
     */
    public ArrayList<PlayType> findPlayTypeByCommission(String commissionType);

    /**
     * 查询赔率类型列表
     * 
     * @return
     */
    public ArrayList<PlayType> findCommissionTypeList();

    /**
     * 查询指定用户的交收报表数据
     * 
     * @param userID        ID
     * @param lotteryType   彩票种类
     * @param playType      下注类型
     * @param periodsNum    期数
     * @param startDate     开始时间（未处理凌晨两点的问题，传入参数需要处理）
     * @param endDate       结束时间（未处理凌晨两点的问题，传入参数需要处理）
     * @param userType      用户类型
     * @return
     */
    public ArrayList<DeliveryReport> findDeliveryReportList(Long userID,
            String lotteryType, String playType, String periodsNum,
            Date startDate, Date endDate, String userType);

    /**
     * 查询当天投注结果数据（所有玩法类型及投注类型结果数据）
     * 对应报表汇总部分的抵扣补货及赚水后结果
     * 
     * @param userID        用户ID  
     * @param userType      用户类型
     * 
     * @return 返回投注统计结果数据，异常则返回null
     */
    public Double findCurrentLotteryData(Long userID, String userType);

    /**
     * 查询当天投注结果数据
     * 对应报表汇总部分的抵扣补货及赚水后结果
     * 
     * @param userID        用户ID  
     * @param userType      用户类型
     * @param lotteryType   彩票类型：ALL-所有；GDKLSF-广东快乐十分；CQSSC-重庆时时彩；HKLHC-香港六合彩
     * @param playType      下注类型，不过滤则传入 null
     * @param periodsNum    盘期，不过滤则传入null
     * @return 返回投注统计结果数据，异常则返回null
     */
    public Double findCurrentLotteryData(Long userID, String userType,
            String lotteryType, String playType, String periodsNum);

    /**
     * 查询指定补货人的补货数据记录
     * 
     * @param replenishUserId
     *            补货人ID
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return  Replenish 类型的 ArrayList
     */
    public ArrayList<Replenish> findReplenishList(Long replenishUserId);

    /**
     * 查询指定补货人的补货数据记录
     * 
     * @param replenishUserId
     * @return
     */
    public ReplenishReport findReplenish(Long replenishUserId);

    /**
     * 查询指定补货人的补货数据记录
     * 
     * @param replenishUserId
     *            补货人ID
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return  Replenish 类型的 ArrayList
     */
    public ArrayList<Replenish> findReplenishList(ConditionData conditionData);
    
    /**
     * 查询报表的数据查询范围
     * 
     * @return
     *     key = start ; value = 最小的数据时间
     *     key = end ; value = 最大的数据时间
     */
    public Map<String, Date> parseReportScope();
}
