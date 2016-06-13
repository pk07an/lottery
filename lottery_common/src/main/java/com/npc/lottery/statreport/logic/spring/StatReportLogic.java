package com.npc.lottery.statreport.logic.spring;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.member.dao.interf.IBetDao;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.replenish.entity.Replenish;
import com.npc.lottery.statreport.dao.interf.IStatReportDao;
import com.npc.lottery.statreport.entity.DeliveryReport;
import com.npc.lottery.statreport.entity.ReplenishReport;
import com.npc.lottery.statreport.entity.StatReport;
import com.npc.lottery.statreport.logic.interf.IBjscHisLogic;
import com.npc.lottery.statreport.logic.interf.IGdklsfHisLogic;
import com.npc.lottery.statreport.logic.interf.IReplenishLogic;
import com.npc.lottery.statreport.logic.interf.IStatReportLogic;

/**
 * 报表统计相关的逻辑处理类
 * 
 */
public class StatReportLogic implements IStatReportLogic {

    private IStatReportDao statReportDao;

    private IBetDao betDao;

    private IReplenishLogic replenishLogic2;

    private IGdklsfHisLogic gdklsfHisLogic;
    private IBjscHisLogic bjscHisLogic;

    public void setStatReportDao(IStatReportDao statReportDao) {
        this.statReportDao = statReportDao;
    }

    public void setReplenishLogic2(IReplenishLogic replenishLogic2) {
        this.replenishLogic2 = replenishLogic2;
    }

    public IGdklsfHisLogic getGdklsfHisLogic() {
        return gdklsfHisLogic;
    }

    public void setGdklsfHisLogic(IGdklsfHisLogic gdklsfHisLogic) {
        this.gdklsfHisLogic = gdklsfHisLogic;
    }

    public IBjscHisLogic getBjscHisLogic() {
		return bjscHisLogic;
	}

	public void setBjscHisLogic(IBjscHisLogic bjscHisLogic) {
		this.bjscHisLogic = bjscHisLogic;
	}

	/**
     * 根据玩法类型查询对应的赔率类型列表
     * 
     * @param playType
     *            玩法类型
     * 
     * @return PlayType 类型的 List
     */
    public ArrayList<PlayType> findCommissionTypeList(String playType) {
        return (ArrayList<PlayType>) statReportDao
                .findCommissionTypeList(playType);
    }

    /**
     * 根据佣金类型查询对应的投注类型数据
     * 
     * @param commissionType
     *            佣金类型
     * @return
     */
    public ArrayList<PlayType> findPlayTypeByCommission(String commissionType) {
        return (ArrayList<PlayType>) statReportDao
                .findPlayTypeByCommission(commissionType);
    }

    /**
     * 查询赔率类型列表
     * 
     * @return
     */
    public ArrayList<PlayType> findCommissionTypeList() {

        ArrayList<PlayType> result = new ArrayList<PlayType>();

        // 广东快乐十分
        result.addAll(this.findCommissionTypeList(StatReport.PLAY_TYPE_GDKLSF));
        // 重庆时时彩
        result.addAll(this.findCommissionTypeList(StatReport.PLAY_TYPE_CQSSC));
        // 北京赛车
        result.addAll(this.findCommissionTypeList(StatReport.PLAY_TYPE_BJ));
        // K3
        result.addAll(this.findCommissionTypeList(StatReport.PLAY_TYPE_K3));
        // NC
        result.addAll(this.findCommissionTypeList(StatReport.PLAY_TYPE_NC));

        return result;
    }

    /**
     * 查询指定用户的交收报表数据
     * 
     * @param userID
     *            ID
     * @param lotteryType
     *            彩票种类
     * @param playType
     *            下注类型
     * @param periodsNum
     *            期数
     * @param startDate
     *            开始时间（未处理凌晨两点的问题，传入参数需要处理）
     * @param endDate
     *            结束时间（未处理凌晨两点的问题，传入参数需要处理）
     * @param userType
     *            用户类型
     * @return
     */
    public ArrayList<DeliveryReport> findDeliveryReportList(Long userID,
            String lotteryType, String playType, String periodsNum,
            Date startDate, Date endDate, String userType) {

        ArrayList<DeliveryReport> result = new ArrayList<DeliveryReport>();

        result = statReportDao.findDeliveryReportList(userID, lotteryType,
                playType, periodsNum, startDate, endDate, userType);

        return result;
    }

    /**
     * 查询当天投注结果数据（所有玩法类型及投注类型结果数据） 对应报表汇总部分的抵扣补货及赚水后结果
     * 
     * @param userID
     *            用户ID
     * @param userType
     *            用户类型
     * 
     * @return 返回投注统计结果数据，异常则返回null
     */
    public Double findCurrentLotteryData(Long userID, String userType) {
        return this.findCurrentLotteryData(userID, userType, "ALL", null, null);
    }

    /**
     * 查询当天投注结果数据 对应报表汇总部分的抵扣补货及赚水后结果
     * 
     * @param userID
     *            用户ID
     * @param userType
     *            用户类型
     * @param lotteryType
     *            彩票类型：ALL-所有；GDKLSF-广东快乐十分；CQSSC-重庆时时彩；HKLHC-香港六合彩
     * @param playType
     *            下注类型，不过滤则传入 null
     * @param periodsNum
     *            盘期，不过滤则传入null
     * @return 返回投注统计结果数据，异常则返回null
     */
    public Double findCurrentLotteryData(Long userID, String userType,
            String lotteryType, String playType, String periodsNum) {

        // 获取当天0点时间
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);

        // 判断当前时间是否是凌晨2点之前
        boolean isToday = false;
        if (System.currentTimeMillis() > (cal.getTime().getTime() + 60 * 60 * 1000 * 2)) {
            isToday = true;
        } else {
            isToday = false;
        }

        // 构造符合业务需要的当天时间（凌晨2点）
        Date startDate = null;
        Date endDate = null;

        if (isToday) {
            //读取当天2点以后及第二天2点之前的数据
            startDate = new Date(cal.getTime().getTime() + 60 * 60 * 1000 * 2);
            endDate = new Date(cal.getTime().getTime() + 60 * 60 * 1000 * 26);
        } else {
            //读取前一天的2点以后及当天2点之前的数据
            startDate = new Date(cal.getTime().getTime() - 60 * 60 * 1000 * 22);
            endDate = new Date(cal.getTime().getTime() + 60 * 60 * 1000 * 2);
        }

        ArrayList<DeliveryReport> resultList = new ArrayList<DeliveryReport>();

        resultList = statReportDao.findDeliveryReportList(userID, lotteryType,
                playType, periodsNum, startDate, endDate, userType);

        if (null == resultList) {
            return null;
        }

        if (0 == resultList.size()) {
            return 0d;
        }

        // 解析结果数据
        DeliveryReport totalEntity = null;
        DeliveryReport totalEntitySec = null;// 统计值（含补货）
        ReplenishReport replenishEntity = null;
        DeliveryReport deliveryReport = null;
        if (null != resultList && resultList.size() > 0) {

            // 取出补货数据，代理的补货数据对应的用户类型为 g，由于存储过程中补货数据放置在最后，故从最后数据开始循环效率更高
            // 取出最后的合计数据，代理的合计数据对应的用户类型为G
            // c（2总监）、d（3分公司）、e（4股东）、f（5总代理）、g（6代理）、h（7子账号）
            for (int i = resultList.size() - 1; i > -1; i--) {
                if ("g".equals(resultList.get(i).getUserType())
                        || "f".equals(resultList.get(i).getUserType())
                        || "e".equals(resultList.get(i).getUserType())
                        || "d".equals(resultList.get(i).getUserType())
                        || "c".equals(resultList.get(i).getUserType())) {
                    deliveryReport = resultList.remove(i);

                    // 构造补货数据对象
                    replenishEntity = new ReplenishReport();
                    replenishEntity.setReplenishUserId(deliveryReport
                            .getUserID());// 补货人
                    replenishEntity.setTurnover(deliveryReport.getTurnover());// 笔数
                    replenishEntity.setReplenishAmount(deliveryReport
                            .getAmount());// 补货金额
                    replenishEntity.setReplenishValidAmount(deliveryReport
                            .getValidAmount());// 有效金额
                    replenishEntity.setReplenishWin(deliveryReport
                            .getMemberAmount());// 补货输赢
                    replenishEntity.setBackWater(deliveryReport
                            .getMemberBackWater());// 退水
                    replenishEntity.setBackWaterResult(deliveryReport
                            .getMemberBackWaterResult());// 退水后结果
                    // 设置补货对象的用户类型
                    // replenishEntity.setUserType(user.getUserType());

                    continue;
                }
                // C（2总监）、D（3分公司）、E（4股东）、F（5总代理）、G（6代理）、H（7子账号）
                if ("G".equals(resultList.get(i).getUserType())
                        || "F".equals(resultList.get(i).getUserType())
                        || "E".equals(resultList.get(i).getUserType())
                        || "D".equals(resultList.get(i).getUserType())
                        || "C".equals(resultList.get(i).getUserType())) {
                    // 合计记录数据
                    totalEntity = resultList.remove(i);
                    continue;
                }
            }
        }

        // 如果补货对象不为空，则合计数据里面去除补货数据
        // 填充含补货的合计数据
        if (null != replenishEntity && null != totalEntity) {
            // 成交笔数
            totalEntity.setTurnover(totalEntity.getTurnover()
                    - replenishEntity.getTurnover());
            // 投注总额
            totalEntity.setAmount(totalEntity.getAmount()
                    - replenishEntity.getReplenishAmount());
            // 有效金额
            totalEntity.setValidAmount(totalEntity.getValidAmount()
                    - replenishEntity.getReplenishValidAmount());
            // 会员输赢
            totalEntity.setMemberAmount(totalEntity.getMemberAmount()
                    - replenishEntity.getReplenishWin());
            // 会员退水
            totalEntity.setMemberBackWater(totalEntity.getMemberBackWater()
                    - replenishEntity.getBackWater());
            // 赚水后结果
            totalEntity.setWinBackWaterResult(totalEntity
                    .getWinBackWaterResult()
                    - replenishEntity.getBackWaterResult());
        }

        // 返回结果值，对应报表汇总部分的抵扣补货及赚水后结果
        double result = 0;
        if (null != totalEntity) {
            result = totalEntity.getWinBackWaterResult();
            if (null != replenishEntity) {
                // 抵扣补货及赚水后结果 = 合计赚取退水 - 补货退水后结果
                result = totalEntity.getWinBackWaterResult()
                        - replenishEntity.getBackWaterResult();
            }
        } else {
            return null;
        }

        return result;
    }

    /**
     * 查询指定补货人的补货数据记录
     * 
     * @param replenishUserId
     *            补货人ID
     * @param pageCurrentNo
     *            第一页为 1 需要查询的页码
     * @param pageSize
     *            页面大小
     * @return Replenish 类型的 ArrayList
     */
    public ArrayList<Replenish> findReplenishList(Long replenishUserId) {
        return replenishLogic2.findReplenishList(replenishUserId);
    }

    /**
     * 查询指定补货人的补货数据记录
     * 
     * @param replenishUserId
     *            补货人ID
     * @param pageCurrentNo
     *            第一页为 1 需要查询的页码
     * @param pageSize
     *            页面大小
     * @return Replenish 类型的 ArrayList
     */
    public ArrayList<Replenish> findReplenishList(ConditionData conditionData) {
        // TODO 后续修改为真正的支持分页
        return replenishLogic2.findReplenishList(conditionData, 1, 99999999);
    }

    /**
     * 查询指定补货人的补货数据记录
     * 
     * @param replenishUserId
     * @return
     */
    public ReplenishReport findReplenish(Long replenishUserId) {

        ArrayList<Replenish> replenishList = this
                .findReplenishList(replenishUserId);

        ReplenishReport entity = null;

        if (null != replenishList && replenishList.size() > 0) {
            entity = new ReplenishReport();
            // 设置笔数
            entity.setTurnover(new Long(replenishList.size()));
            // 补货金额
            Double replenishAmount = 0.0;// 补货金额
            Double replenishWin = 0.0;// 补货输赢
            Double backWater = 0.0;// 退水
            for (int i = 0; i < replenishList.size(); i++) {
                // 未兑奖的不统计
                if ("0".equalsIgnoreCase(replenishList.get(i).getWinState())) {
                    continue;
                }
                replenishAmount += replenishList.get(i).getMoney();
                replenishWin += replenishList.get(i).getWinAmount()
                        .doubleValue();
                // TODO 此方法需要传入用户类型参数，以便于决定使用哪种退水
                backWater += replenishList.get(i).getMoney()
                        * replenishList.get(i).getCommissionAgent()
                                .doubleValue() / 100;
            }
            entity.setReplenishAmount(replenishAmount);
            entity.setReplenishWin(replenishWin);
            entity.setBackWater(backWater);
            entity.setBackWaterResult(replenishWin + backWater);
        }

        return entity;
    }

	/**
	 * 查询报表的数据查询范围
	 * 
	 * TODO 此方法需要优化，目前只查询了北京赛车的数据，需要查询所有的数据，之后取最大时间范围值
	 * 
	 * @return key = start ; value = 最小的数据时间 key = end ; value = 最大的数据时间
	 */
	public Map<String, Date> parseReportScope() {
		return bjscHisLogic.findBjHisMaxAndMinBetDate();
	}

    public IBetDao getBetDao() {
        return betDao;
    }

    public void setBetDao(IBetDao betDao) {
        this.betDao = betDao;
    }
}
