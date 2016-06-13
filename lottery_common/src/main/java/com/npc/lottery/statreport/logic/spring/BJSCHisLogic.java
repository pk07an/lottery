package com.npc.lottery.statreport.logic.spring;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.statreport.dao.interf.IBJSCHisDao;
import com.npc.lottery.statreport.entity.BjscHis;
import com.npc.lottery.statreport.logic.interf.IBjscHisLogic;
import com.npc.lottery.util.Tool;

/**
 * 投注历史（北京赛车）业务逻辑处理类
 * 
 */
public class BJSCHisLogic implements IBjscHisLogic {

    private IBJSCHisDao bjscHisDao;

    /**
     * 根据查询条件查询对应广东快乐十分历史信息
     * 
     * @param conditionStr
     * @return
     */
    public ArrayList<BjscHis> findBjscHis(String conditionStr) {

        ArrayList<BjscHis> result = bjscHisDao.findBjscHis(conditionStr);

        //计算连码的相关值
        if (null != result) {
            BjscHis entity;
            BjscHis entitySec;
            ArrayList<BjscHis> bjscHisList;
            long money;
            double amount;
            double resultTotal;
            double resultTotalNoRate;
            for (int outer = 0; outer < result.size(); outer++) {
                entity = result.get(outer);
                //判断是否是连码
                if (entity.getRecordNum() > 1) {
                    //查询对应的所有记录
                    bjscHisList = this.findBjscHisListByOrderNo(entity
                            .getOrderNo());

                    //循环统计记录信息
                    if (null != bjscHisList) {

                        money = 0;
                        amount = 0;
                        resultTotal = 0;
                        resultTotalNoRate = 0;
                        for (int inner = 0; inner < bjscHisList.size(); inner++) {
                            entitySec = bjscHisList.get(inner);

                            money += entitySec.getMoney();//累加投注额

                            amount += entitySec.getWinAmount2();//累加会员结果

                            resultTotal += entitySec.getResult();//累加您的结果

                            resultTotalNoRate += entitySec.getResultNoRate();//累加您的结果
                        }

                        //保存累加投注额
                        entity.setMoneyTotal(new Long(money).intValue());

                        //保存会员结果
                        entity.setWinAmountTotal(new Double(amount).intValue());

                        //保存您的结果 TODO 如果结果错误，resultTotal 换一种类型，以便于计算
                        entity.setResultTotal(((long) (resultTotal * 100)) / 100.0);
                        entity.setResultTotalNoRate(((long) (resultTotalNoRate * 100)) / 100.0);
                    }
                }
            }
        }

        return result;
    }

    /**
     * 根据注单号查询对应的数据记录
     * 
     * @param orderNo
     * @return
     */
    public ArrayList<BjscHis> findBjscHisListByOrderNo(String orderNo) {
        ArrayList<BjscHis> result = null;

        ConditionData conditionData = new ConditionData();
        conditionData.addEqual("orderNo", orderNo);

        result = this.findBjscHisList(conditionData, 1, 9999);

        return result;
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
     * @return  BjscHis 类型的 ArrayList
     */
    public ArrayList<BjscHis> findBjscHisList(ConditionData conditionData,
            int pageCurrentNo, int pageSize) {
        ArrayList<BjscHis> resultList = null;

        // 根据页码和页面大小，获得需要查询的开始数据和数量
        int firstResult = (pageCurrentNo - 1) * pageSize;
        int maxResults = pageSize;

        // 查询数据
        try {
            resultList = (ArrayList<BjscHis>) bjscHisDao.findBjscHisList(
                    conditionData, firstResult, maxResults);
        } catch (Exception ex) {
            Tool.printExceptionStack(ex);
        }

        return resultList;
    }

    /**
     * 根据注单号 修改历史表记录-已结算的
     * @param entity
     */
    @Override
    public void updateBJSCHis(String orderNum) {
        Object[] values = { orderNum };
        String hql = " update BjscHis set winState=4  where orderNo=? ";
        bjscHisDao.updateBatch(hql, values);
    }

    /**
     * 根据注单号 删除历史表记录-已结算的 addby Aaron 20121113
     * @param entity
     */
    @Override
    public void deleteBjscHis(String orderNum) {
        ConditionData cd = new ConditionData();
        cd.addEqual("orderNo", orderNum);
        List list = bjscHisDao.findBjscHisList(cd, 0, 100); //投注复试 可能有多少
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                bjscHisDao.delete((BjscHis) list.get(i));
            }
        }
    }

    public IBJSCHisDao getBjscHisDao() {
        return bjscHisDao;
    }

    public void setBjscHisDao(IBJSCHisDao bjscHisDao) {
        this.bjscHisDao = bjscHisDao;
    }

	@Override
	public Map<String, Date> findBjHisMaxAndMinBetDate() {
		Map<String, Date> resultMap = new HashMap<String, Date>();
		Object[] objs = bjscHisDao.findBjHisMaxAndMinBetDate();
		if (null != objs && objs.length == 2) {
			resultMap.put("start", (Date) objs[0]);
			resultMap.put("end", (Date) objs[1]);
		}
		return resultMap;
	}
}
