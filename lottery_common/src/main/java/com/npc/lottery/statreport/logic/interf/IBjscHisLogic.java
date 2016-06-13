package com.npc.lottery.statreport.logic.interf;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.statreport.entity.BjscHis;

/**
 * 投注历史（北京赛车）业务逻辑处理类接口
 * 
 */
public interface IBjscHisLogic {
    
    /**
     * 根据查询条件查询对应广东快乐十分历史信息
     * 
     * @param conditionStr
     * @return
     */
    public ArrayList<BjscHis> findBjscHis(String conditionStr);
    
    /**
     * 根据注单号查询对应的数据记录
     * 
     * @param orderNo
     * @return
     */
    public ArrayList<BjscHis> findBjscHisListByOrderNo(String orderNo);
    
    /**
     * 查询满足指定查询条件的数据记录
     * 
     * @param conditionData
     *            查询条件信息
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return  BjscHis 类型的 ArrayList
     */
    public ArrayList<BjscHis> findBjscHisList(ConditionData conditionData,
            int pageCurrentNo, int pageSize);

    public void updateBJSCHis(String orderNum);

    /**
     * 根据注单号 删除历史表记录-已结算的 addby Aaron 20121113
     * @param entity
     */
    public void deleteBjscHis(String orderNum);
    
    /**
     * add by peter 获取北京历史表里最大和最小的投注日期
     * @return
     */
    public Map<String, Date> findBjHisMaxAndMinBetDate();
}
