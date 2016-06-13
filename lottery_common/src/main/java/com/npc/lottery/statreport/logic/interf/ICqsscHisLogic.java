package com.npc.lottery.statreport.logic.interf;

import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.statreport.entity.CqsscHis;
import com.npc.lottery.util.Tool;

/**
 * 投注历史（重庆时时彩）业务逻辑处理类接口
 * 
 */
public interface ICqsscHisLogic {

    /**
     * 查询满足指定查询条件的数据记录
     * 
     * @param conditionData
     *            查询条件信息
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return  CqsscHis 类型的 ArrayList
     */
    public ArrayList<CqsscHis> findCqsscHisList(ConditionData conditionData,
            int pageCurrentNo, int pageSize);

    /**
     * 根据注单号查询对应的数据记录
     * 
     * @param orderNo
     * @return
     */
    public ArrayList<CqsscHis> findCqsscHisListByOrderNo(String orderNo);

    /**
     * 统计满足指定查询条件的记录数目

     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountCqsscHisList(ConditionData conditionData);

    /**
     * 根据ID查询数据
     * 
     * @return
     */
    public CqsscHis findCqsscHisByID(long ID);

    /**
     * 根据查询条件查询对应广东快乐十分历史信息
     * 
     * @param conditionStr
     * @return
     */
    public ArrayList<CqsscHis> findCqsscHis(String conditionStr);

    /**
     * 根据投注会员查询投注明细
     * 
     * @param bettingUserID     投注会员ID
     * @return
     */
    public ArrayList<CqsscHis> findCqsscHisByBettingUserID(Long bettingUserID);

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveCqsscHis(CqsscHis entity);

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void update(CqsscHis entity);

    /**
     * 删除信息
     * 
     * @param entity
     */
    public void delete(CqsscHis entity);

    /**
     * 根据注单号 修改历史表记录-已结算的
     * @param entity
     */
    public void updateCqsscHis(String orderNum);

    public void deleteCqsscHis(String orderNum);
}
