package com.npc.lottery.statreport.dao.interf;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.replenish.vo.ReplenishVO;
import com.npc.lottery.statreport.entity.DeliveryReport;
import com.npc.lottery.sysmge.entity.ManagerStaff;

/**
 * 报表统计相关的数据库处理类接口
 * 
 */
public interface IStatReportDao {

    /**
     * 查询满足指定查询条件的数据记录
     * 
     * @param playType
     *            玩法类型
     *            
     * @return  PlayType 类型的 List
     */
    public List findCommissionTypeList(String playType);
    
    /**
     * 根据佣金类型查询对应的投注类型数据
     * 
     * @param commissionType
     * @return
     */
    public List findPlayTypeByCommission(String commissionType);

    /**
     * 查询指定用户的交收报表数据
     * 
     * @param userID
     * @param userType
     * @deprecated 此方法测试使用，待删除
     * 
     * @return
     */
    public ArrayList<DeliveryReport> findDeliveryReportList(Long userID,
            String userType);
    
    /**
     * 查询指定用户的交收报表数据
     * 
     * @param userID        ID
     * @param lotteryType   彩票种类
     * @param playType      下注类型
     * @param periodsNum    期数
     * @param startDate     开始时间
     * @param endDate       结束时间
     * @param userType      用户类型
     * @return
     */
    public ArrayList<DeliveryReport> findDeliveryReportList(Long userID,
            String lotteryType, String playType, String periodsNum,
            Date startDate, Date endDate, String userType);

}
