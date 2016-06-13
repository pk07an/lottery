package com.npc.lottery.statreport.logic.spring;

import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.statreport.dao.interf.ICqsscHisDao;
import com.npc.lottery.statreport.entity.CqsscHis;
import com.npc.lottery.statreport.logic.interf.ICqsscHisLogic;
import com.npc.lottery.statreport.logic.interf.IStatReportLogic;
import com.npc.lottery.util.Tool;

/**
 * 投注历史（重庆时时彩）业务逻辑处理类
 * 
 */
public class CqsscHisLogic implements ICqsscHisLogic {


    private ICqsscHisDao cqsscHisDao = null;//数据库处理类，由spring框架注入实例

    private IStatReportLogic statReportLogic = null;//报表统计

    public void setCqsscHisDao(ICqsscHisDao cqsscHisDao) {
        this.cqsscHisDao = cqsscHisDao;
    }

    public void setStatReportLogic(IStatReportLogic statReportLogic) {
        this.statReportLogic = statReportLogic;
    }

    public ICqsscHisDao getCqsscHisDao() {
        return cqsscHisDao;
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
     * @return  CqsscHis 类型的 ArrayList
     */
    public ArrayList<CqsscHis> findCqsscHisList(ConditionData conditionData,
            int pageCurrentNo, int pageSize) {
        ArrayList<CqsscHis> resultList = null;

        // 根据页码和页面大小，获得需要查询的开始数据和数量
        int firstResult = (pageCurrentNo - 1) * pageSize;
        int maxResults = pageSize;

        // 查询数据
        try {
            resultList = (ArrayList<CqsscHis>) cqsscHisDao.findCqsscHisList(
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
    public ArrayList<CqsscHis> findCqsscHisListByOrderNo(String orderNo) {
        ArrayList<CqsscHis> result = null;

        ConditionData conditionData = new ConditionData();
        conditionData.addEqual("orderNo", orderNo);

        result = this.findCqsscHisList(conditionData, 1, 9999);

        return result;
    }

    /**
     * 统计满足指定查询条件的记录数目

     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountCqsscHisList(ConditionData conditionData) {
        long amount = 0;

        try {
            amount = cqsscHisDao.findAmountCqsscHisList(conditionData);
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
    public CqsscHis findCqsscHisByID(long ID) {
        CqsscHis entity = null;

        ConditionData conditionData = new ConditionData();

        conditionData.addEqual("ID", new Long(ID));//增加ID的查询条件

        List resultList = this.findCqsscHisList(conditionData, 1, 999);

        if (null != resultList && 0 < resultList.size()) {
            entity = (CqsscHis) resultList.get(0);
        }

        return entity;
    }

    /**
     * 根据查询条件查询对应广东快乐十分历史信息
     * 
     * @param conditionStr
     * @return
     */
    public ArrayList<CqsscHis> findCqsscHis(String conditionStr) {

        ArrayList<CqsscHis> result = cqsscHisDao.findCqsscHis(conditionStr);

        //计算连码的相关值
        if (null != result) {
            CqsscHis entity;
            CqsscHis entitySec;
            ArrayList<CqsscHis> cqsscHisList;
            long money;
            double amount;
            double resultTotal;
            double resultTotalNoRate;
            for (int outer = 0; outer < result.size(); outer++) {
                entity = result.get(outer);
                //判断是否是连码
                if (entity.getRecordNum() > 1) {
                    //查询对应的所有记录
                    cqsscHisList = this.findCqsscHisListByOrderNo(entity
                            .getOrderNo());
                    
                    //循环统计记录信息
                    if (null != cqsscHisList) {

                        money = 0;
                        amount = 0;
                        resultTotal = 0;
                        resultTotalNoRate = 0;
                        for (int inner = 0; inner < cqsscHisList.size(); inner++) {
                            entitySec = cqsscHisList.get(inner);
                            
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
    public ArrayList<CqsscHis> findCqsscHisByBettingUserID(Long bettingUserID) {

        ArrayList<CqsscHis> resultList = null;

        ConditionData conditionData = new ConditionData();

        conditionData.addEqual("bettingUserID", bettingUserID);//增加bettingUserID的查询条件

        resultList = this.findCqsscHisList(conditionData, 1, 999);

        //获取下注类型名称信息 TODO 此处待优化，配置多表关联
        //下注类型列表
        ArrayList<PlayType> playTypeList = statReportLogic
                .findCommissionTypeList();

        //匹配下注类型名称
        CqsscHis cqsscHisEntity = null;
        PlayType playTypeEntity = null;
        String typeName;
        if (null != resultList || resultList.size() < 1) {
            for (int outer = 0; outer < resultList.size(); outer++) {
                cqsscHisEntity = resultList.get(outer);

                for (int inner = 0; inner < playTypeList.size(); inner++) {
                    playTypeEntity = playTypeList.get(inner);
                    //匹配下注类型
                    if (playTypeEntity
                            .getTypeCode()
                            .trim()
                            .equalsIgnoreCase(
                                    cqsscHisEntity.getTypeCode().trim())) {
                        typeName = playTypeEntity.getCommissionTypeName();

                        cqsscHisEntity.setTypeCodeName(typeName);
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
    public Long saveCqsscHis(CqsscHis entity) {

        Long result = null;

        result = cqsscHisDao.saveCqsscHis(entity);

        return result;
    }

    /**
     * 更新信息
     * 
     * @param entity 待更新的信息
     */
    public void update(CqsscHis entity) {
        cqsscHisDao.update(entity);
    }

    /**
     * 删除信息
     * 
     * @param entity
     */
    public void delete(CqsscHis entity) {
        cqsscHisDao.delete(entity);
    }
    /**
     * 根据注单号 修改历史表记录-已结算的
     * @param entity
     */
    @Override
    public void updateCqsscHis(String orderNum) {
    	Object[] values={orderNum};
    	String hql=" update CqsscHis set winState=4  where orderNo=? ";
    	cqsscHisDao.updateBatch(hql, values);
    }
    /**
     * 根据注单号 删除历史表记录-已结算的 addby Aaron 20121113
     * @param entity
     */
    @Override
    public void deleteCqsscHis(String orderNum) {
        ConditionData cd = new ConditionData();
        cd.addEqual("orderNo", orderNum);
        List list =  cqsscHisDao.findCqsscHisList(cd,0,100); //投注复试 可能有多少
      	if(null!=list){ 
	      for(int i=0;i<list.size();i++)
	      {
	    	  cqsscHisDao.delete((CqsscHis)list.get(i));
	      }
      	}
    }

}
