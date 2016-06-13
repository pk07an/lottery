package com.npc.lottery.statreport.logic.spring;

import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.statreport.dao.interf.IHklhcHisDao;
import com.npc.lottery.statreport.entity.HklhcHis;
import com.npc.lottery.statreport.logic.interf.IHklhcHisLogic;
import com.npc.lottery.statreport.logic.interf.IStatReportLogic;
import com.npc.lottery.util.Tool;

/**
 * 投注历史（香港六合彩）逻辑处理类
 * 
 */
public class HklhcHisLogic implements IHklhcHisLogic {

    private IHklhcHisDao hklhcHisDao = null;//数据库处理类，由spring框架注入实例

    private IStatReportLogic statReportLogic = null;//报表统计

    public void setHklhcHisDao(IHklhcHisDao hklhcHisDao) {
        this.hklhcHisDao = hklhcHisDao;
    }

    public void setStatReportLogic(IStatReportLogic statReportLogic) {
        this.statReportLogic = statReportLogic;
    }

    public IHklhcHisDao getHklhcHisDao() {
        return hklhcHisDao;
    }

    /**
     * 查询满足指定查询条件的数据记录
     * 
     * @param conditionData
     *            查询条件信息
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return  HklhcHis 类型的 ArrayList
     */
    public ArrayList<HklhcHis> findHklhcHisList(ConditionData conditionData,
            int pageCurrentNo, int pageSize) {
        ArrayList<HklhcHis> resultList = null;

        // 根据页码和页面大小，获得需要查询的开始数据和数量
        int firstResult = (pageCurrentNo - 1) * pageSize;
        int maxResults = pageSize;

        // 查询数据
        try {
            resultList = (ArrayList<HklhcHis>) hklhcHisDao.findHklhcHisList(
                    conditionData, firstResult, maxResults);
        } catch (Exception ex) {
            Tool.printExceptionStack(ex);
        }

        return resultList;
    }

    /**
     * 根据注单号查询对应的数据记录
     * 
     * @param orderNo
     * @return
     */
    public ArrayList<HklhcHis> findHklhcHisListByOrderNo(String orderNo) {
        ArrayList<HklhcHis> result = null;

        ConditionData conditionData = new ConditionData();
        conditionData.addEqual("orderNo", orderNo);

        result = this.findHklhcHisList(conditionData, 1, 9999);

        return result;
    }

    /**
     * 统计满足指定查询条件的记录数目

     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountHklhcHisList(ConditionData conditionData) {
        long amount = 0;

        try {
            amount = hklhcHisDao.findAmountHklhcHisList(conditionData);
        } catch (Exception ex) {
            Tool.printExceptionStack(ex);
        }

        return amount;
    }

    /**
     * 根据ID查询数据
     * 
     * @return
     */
    public HklhcHis findHklhcHisByID(long ID) {
        HklhcHis entity = null;

        ConditionData conditionData = new ConditionData();

        conditionData.addEqual("ID", new Long(ID));//增加ID的查询条件

        List resultList = this.findHklhcHisList(conditionData, 1, 999);

        if (null != resultList && 0 < resultList.size()) {
            entity = (HklhcHis) resultList.get(0);
        }

        return entity;
    }

    /**
     * 根据查询条件查询对应广东快乐十分历史信息
     * 
     * @param conditionStr
     * @return
     */
    public ArrayList<HklhcHis> findHklhcHis(String conditionStr) {

        ArrayList<HklhcHis> result = hklhcHisDao.findHklhcHis(conditionStr);

        //计算连码的相关值
        if (null != result) {
            HklhcHis entity;
            HklhcHis entitySec;
            ArrayList<HklhcHis> hklhcHisList;
            long money;
            double amount;
            double resultTotal;
            double resultTotalNoRate;
            for (int outer = 0; outer < result.size(); outer++) {
                entity = result.get(outer);
                //判断是否是连码
                if (entity.getRecordNum() > 1) {
                    //查询对应的所有记录
                    hklhcHisList = this.findHklhcHisListByOrderNo(entity
                            .getOrderNo());
                    
                    //循环统计记录信息
                    if (null != hklhcHisList) {

                        money = 0;
                        amount = 0;
                        resultTotal = 0;
                        resultTotalNoRate = 0;
                        for (int inner = 0; inner < hklhcHisList.size(); inner++) {
                            entitySec = hklhcHisList.get(inner);
                            
                            money += entitySec.getMoney();//累加投注额

                            amount += entitySec.getWinAmount2();//累加会员结果
                            
                            resultTotal += entitySec.getResult();//累加您的结果
                            
                            resultTotalNoRate += entitySec.getResultNoRate();//累加您的结果（未计算占成）
                        }

                        //保存累加投注额
                        entity.setMoneyTotal(new Long(money).intValue());
                        
                        //保存会员结果
                        entity.setWinAmountTotal(new Double(amount).intValue());
                        
                        //保存您的结果 TODO 如果结果错误，resultTotal 换一种类型，以便于计算
                        entity.setResultTotal(((long)(resultTotal*100))/100.0);
                        
                        entity.setResultTotalNoRate(((long)(resultTotalNoRate*100))/100.0);
                    }
                }
            }
        }

        return result;
    }

    /**
     * 根据投注会员查询投注明细
     * 
     * @param bettingUserID     投注会员ID
     * @return
     */
    public ArrayList<HklhcHis> findHklhcHisByBettingUserID(Long bettingUserID) {

        ArrayList<HklhcHis> resultList = null;

        ConditionData conditionData = new ConditionData();

        conditionData.addEqual("bettingUserID", bettingUserID);//增加bettingUserID的查询条件

        resultList = this.findHklhcHisList(conditionData, 1, 999);

        //获取下注类型名称信息 TODO 此处待优化，配置多表关联
        //下注类型列表
        ArrayList<PlayType> playTypeList = statReportLogic
                .findCommissionTypeList();

        //匹配下注类型名称
        HklhcHis hklhcHisEntity = null;
        PlayType playTypeEntity = null;
        String typeName;
        if (null != resultList || resultList.size() < 1) {
            for (int outer = 0; outer < resultList.size(); outer++) {
                hklhcHisEntity = resultList.get(outer);

                for (int inner = 0; inner < playTypeList.size(); inner++) {
                    playTypeEntity = playTypeList.get(inner);
                    //匹配下注类型
                    if (playTypeEntity
                            .getTypeCode()
                            .trim()
                            .equalsIgnoreCase(
                                    hklhcHisEntity.getTypeCode().trim())) {
                        typeName = playTypeEntity.getCommissionTypeName();

                        hklhcHisEntity.setTypeCodeName(typeName);
                        break;
                    }
                }
            }
        }

        return resultList;
    }

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveHklhcHis(HklhcHis entity) {

        Long result = null;

        result = hklhcHisDao.saveHklhcHis(entity);

        return result;
    }

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void update(HklhcHis entity) {
        hklhcHisDao.update(entity);
    }

    /**
     * 删除信息
     * 
     * @param entity
     */
    public void delete(HklhcHis entity) {
        hklhcHisDao.delete(entity);
    }
    
    /**
     * 根据注单号 修改历史表记录-已结算的
     * @param entity
     */
    @Override
    public void updateHKlhcHis(String orderNum) {
        Object[] values={orderNum};
        String hql=" update HklhcHis set winState=4  where orderNo=? ";
        hklhcHisDao.updateBatch(hql, values);
    }
}
