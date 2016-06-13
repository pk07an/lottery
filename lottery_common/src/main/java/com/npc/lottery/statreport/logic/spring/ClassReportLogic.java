package com.npc.lottery.statreport.logic.spring;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.replenish.entity.Replenish;
import com.npc.lottery.statreport.dao.interf.IClassReportDao;
import com.npc.lottery.statreport.dao.interf.IStatReportDao;
import com.npc.lottery.statreport.entity.BjscHis;
import com.npc.lottery.statreport.entity.ClassReport;
import com.npc.lottery.statreport.entity.CqsscHis;
import com.npc.lottery.statreport.entity.GdklsfHis;
import com.npc.lottery.statreport.entity.HklhcHis;
import com.npc.lottery.statreport.entity.LotInfoHis;
import com.npc.lottery.statreport.entity.ReplenishReport;
import com.npc.lottery.statreport.entity.StatReport;
import com.npc.lottery.statreport.logic.interf.IBjscHisLogic;
import com.npc.lottery.statreport.logic.interf.IClassReportLogic;
import com.npc.lottery.statreport.logic.interf.ICqsscHisLogic;
import com.npc.lottery.statreport.logic.interf.IGdklsfHisLogic;
import com.npc.lottery.statreport.logic.interf.IHklhcHisLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.user.entity.MemberStaffExt;

/**
 * 分类报表
 * 
 * @author User
 *
 */
public class ClassReportLogic implements IClassReportLogic {

    private static Logger log = Logger.getLogger(ClassReportLogic.class);

    private IClassReportDao classReportDao;

    private IStatReportDao statReportDao;

    private IGdklsfHisLogic gdklsfHisLogic;

    private ICqsscHisLogic cqsscHisLogic;

    private IHklhcHisLogic hklhcHisLogic;

    private IBjscHisLogic bjscHisLogic;

    public void setClassReportDao(IClassReportDao classReportDao) {
        this.classReportDao = classReportDao;
    }

    public void setStatReportDao(IStatReportDao statReportDao) {
        this.statReportDao = statReportDao;
    }

    public void setGdklsfHisLogic(IGdklsfHisLogic gdklsfHisLogic) {
        this.gdklsfHisLogic = gdklsfHisLogic;
    }

    public void setCqsscHisLogic(ICqsscHisLogic cqsscHisLogic) {
        this.cqsscHisLogic = cqsscHisLogic;
    }

    public void setHklhcHisLogic(IHklhcHisLogic hklhcHisLogic) {
        this.hklhcHisLogic = hklhcHisLogic;
    }

    public void setBjscHisLogic(IBjscHisLogic bjscHisLogic) {
        this.bjscHisLogic = bjscHisLogic;
    }

    /**
     * 查询指定用户的分类报表数据
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
    public ArrayList<ClassReport> findClassReportList(Long userID,
            String lotteryType, String playType, String periodsNum,
            Date startDate, Date endDate, String userType) {

        ArrayList<ClassReport> result = new ArrayList<ClassReport>();
        HashMap<String, ArrayList<LotInfoHis>> lotInfoMap = new HashMap<String, ArrayList<LotInfoHis>>();

        HashMap<String, ArrayList> lotInfoMapOrigin = null;
        //判断用户类型，获取对应的投注信息
        if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
            //代理
            lotInfoMapOrigin = this.findAgentLotInfoList(userID, lotteryType,
                    playType, periodsNum, startDate, endDate);
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT.equalsIgnoreCase(userType)) {
            //总代理
            lotInfoMapOrigin = this.findGenAgentLotInfoList(userID,
                    lotteryType, playType, periodsNum, startDate, endDate);
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                .equalsIgnoreCase(userType)) {
            //股东
            lotInfoMapOrigin = this.findStockholderLotInfoList(userID,
                    lotteryType, playType, periodsNum, startDate, endDate);
        } else if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(userType)) {
            //分公司
            lotInfoMapOrigin = this.findBranchLotInfoList(userID, lotteryType,
                    playType, periodsNum, startDate, endDate);
        } else if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(userType)) {
            //总监
            lotInfoMapOrigin = this.findChiefLotInfoList(userID, lotteryType,
                    playType, periodsNum, startDate, endDate);
        }

        //根据彩票种类所对应的投注信息，将投注数据转换为分类报表数据
        if (null != lotInfoMapOrigin) {
            //将特定玩法的投注记录转换为公用的投注记录
            ArrayList<GdklsfHis> gdklsfListOrigin = lotInfoMapOrigin
                    .get(StatReport.PLAY_TYPE_GDKLSF);
            ArrayList<CqsscHis> cqsscListOrigin = lotInfoMapOrigin
                    .get(StatReport.PLAY_TYPE_CQSSC);
            ArrayList<HklhcHis> hklhcListOrigin = lotInfoMapOrigin
                    .get(StatReport.PLAY_TYPE_K3);
            ArrayList<BjscHis> bjscListOrigin = lotInfoMapOrigin
                    .get(StatReport.PLAY_TYPE_BJSC);

            ArrayList<LotInfoHis> gdklsfList = new ArrayList<LotInfoHis>();
            ArrayList<LotInfoHis> cqsscList = new ArrayList<LotInfoHis>();
            ArrayList<LotInfoHis> hklhcList = new ArrayList<LotInfoHis>();
            ArrayList<LotInfoHis> bjscList = new ArrayList<LotInfoHis>();

            if (null != gdklsfListOrigin) {
                for (int i = 0; i < gdklsfListOrigin.size(); i++) {
                    gdklsfList.add(new LotInfoHis(gdklsfListOrigin.get(i)));
                }
                //生成分类报表数据
                result.addAll(this.createClassReport(userType, gdklsfList));
            }
            if (null != cqsscListOrigin) {
                for (int i = 0; i < cqsscListOrigin.size(); i++) {
                    cqsscList.add(new LotInfoHis(cqsscListOrigin.get(i)));
                }
                //生成分类报表数据
                result.addAll(this.createClassReport(userType, cqsscList));
            }
            /*if (null != hklhcListOrigin) {
                for (int i = 0; i < hklhcListOrigin.size(); i++) {
                    hklhcList.add(new LotInfoHis(hklhcListOrigin.get(i)));
                }
                //生成分类报表数据
                result.addAll(this.createClassReport(userType, hklhcList));
            }*/
            if (null != bjscListOrigin) {
                for (int i = 0; i < bjscListOrigin.size(); i++) {
                    bjscList.add(new LotInfoHis(bjscListOrigin.get(i)));
                }
                //生成分类报表数据
                result.addAll(this.createClassReport(userType, bjscList));
            }
        }

        return result;
    }

    /**
     * 查询指定查询条件投注历史数据
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
    public ArrayList<LotInfoHis> findLotInfoList(Long userID,
            String lotteryType, String playType, String periodsNum,
            Date startDate, Date endDate, String userType) {

        ArrayList<LotInfoHis> result = new ArrayList<LotInfoHis>();
        HashMap<String, ArrayList<LotInfoHis>> lotInfoMap = new HashMap<String, ArrayList<LotInfoHis>>();

        HashMap<String, ArrayList> lotInfoMapOrigin = null;
        //判断用户类型，获取对应的投注信息
        if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
            //代理
            lotInfoMapOrigin = this.findAgentLotInfoList(userID, lotteryType,
                    playType, periodsNum, startDate, endDate);
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT.equalsIgnoreCase(userType)) {
            //总代理
            lotInfoMapOrigin = this.findGenAgentLotInfoList(userID,
                    lotteryType, playType, periodsNum, startDate, endDate);
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                .equalsIgnoreCase(userType)) {
            //股东
            lotInfoMapOrigin = this.findStockholderLotInfoList(userID,
                    lotteryType, playType, periodsNum, startDate, endDate);
        } else if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(userType)) {
            //分公司
            lotInfoMapOrigin = this.findBranchLotInfoList(userID, lotteryType,
                    playType, periodsNum, startDate, endDate);
        } else if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(userType)) {
            //总监
            lotInfoMapOrigin = this.findChiefLotInfoList(userID, lotteryType,
                    playType, periodsNum, startDate, endDate);
        }

        if (null != lotInfoMapOrigin) {
            //将特定玩法的投注记录转换为公用的投注记录
            ArrayList<GdklsfHis> gdklsfListOrigin = lotInfoMapOrigin
                    .get(StatReport.PLAY_TYPE_GDKLSF);
            ArrayList<CqsscHis> cqsscListOrigin = lotInfoMapOrigin
                    .get(StatReport.PLAY_TYPE_CQSSC);
            ArrayList<HklhcHis> hklhcListOrigin = lotInfoMapOrigin
                    .get(StatReport.PLAY_TYPE_K3);
            ArrayList<BjscHis> bjscListOrigin = lotInfoMapOrigin
                    .get(StatReport.PLAY_TYPE_BJSC);

            ArrayList<LotInfoHis> gdklsfList = new ArrayList<LotInfoHis>();
            ArrayList<LotInfoHis> cqsscList = new ArrayList<LotInfoHis>();
            ArrayList<LotInfoHis> hklhcList = new ArrayList<LotInfoHis>();
            ArrayList<LotInfoHis> bjscList = new ArrayList<LotInfoHis>();

            if (null != gdklsfListOrigin) {
                for (int i = 0; i < gdklsfListOrigin.size(); i++) {
                    //生成公共投注对象
                    gdklsfList.add(new LotInfoHis(gdklsfListOrigin.get(i)));
                }
                result.addAll(gdklsfList);
            }
            if (null != cqsscListOrigin) {
                for (int i = 0; i < cqsscListOrigin.size(); i++) {
                    //生成公共投注对象
                    cqsscList.add(new LotInfoHis(cqsscListOrigin.get(i)));
                }
                result.addAll(cqsscList);
            }
            /*if (null != hklhcListOrigin) {
                for (int i = 0; i < hklhcListOrigin.size(); i++) {
                    //生成公共投注对象
                    hklhcList.add(new LotInfoHis(hklhcListOrigin.get(i)));
                }
                result.addAll(hklhcList);
            }*/
            if (null != bjscListOrigin) {
                for (int i = 0; i < bjscListOrigin.size(); i++) {
                    //生成公共投注对象
                    bjscList.add(new LotInfoHis(bjscListOrigin.get(i)));
                }
                result.addAll(bjscList);
            }
        }

        return result;
    }

    /**
     * 根据投注记录解析出对应的分类报表数据
     * 
     * @param userType      用户类型
     * @param lotInfoList
     * @return 
     */
    private ArrayList<ClassReport> createClassReport(String userType,
            ArrayList<LotInfoHis> lotInfoList) {

        if (null == lotInfoList) {
            return null;
        }
        ArrayList<ClassReport> result = new ArrayList<ClassReport>();
        //按投注类型分类投注记录
        HashMap<String, ArrayList<LotInfoHis>> lotInfoMap = new HashMap<String, ArrayList<LotInfoHis>>();

        String commissionType;
        LotInfoHis lotEntity;
        //1、按佣金类型分类数据
        for (int i = 0; i < lotInfoList.size(); i++) {
            lotEntity = lotInfoList.get(i);
            commissionType = lotEntity.getCommissionType();

            //判断佣金类型是否已经加入
            if (lotInfoMap.containsKey(commissionType)) {
                lotInfoMap.get(commissionType).add(lotEntity);
            } else {
                lotInfoMap.put(commissionType, new ArrayList<LotInfoHis>());
                lotInfoMap.get(commissionType).add(lotEntity);
            }
        }

        //2、按佣金类型分类的不同处理数据
        Iterator<String> lotInfoKey = lotInfoMap.keySet().iterator();
        ArrayList<LotInfoHis> lotInfoValue = null;
        ClassReport classReportEntity;
        while (lotInfoKey.hasNext()) {
            commissionType = lotInfoKey.next();
            lotInfoValue = lotInfoMap.get(commissionType);

            //计算不同佣金类型对应的分类报表数据
            classReportEntity = this.parseClassReport(userType, commissionType,
                    lotInfoValue);

            result.add(classReportEntity);
        }

        return result;
    }

    /**
     * 根据分类报表列表数据计算总和分类报表数据
     * 此方法同时解析占货比数据
     * 
     * @param resultList
     * @return
     */
    public ClassReport parseTotalClassReport(ArrayList<ClassReport> resultList) {

        ClassReport entity = new ClassReport();

        if (null == resultList || 0 == resultList.size()) {
            return null;
        }

        Long turnover = 0L;//成交笔数
        Double amount = 0D;//投注总额
        Double memberAmount = 0D;//会员输赢
        Double subordinateAmount = 0D;//应收下线
        Double rate = 0D;//占成
        Double realAmount = 0D;//实占注额
        Double validAmount = 0D;//有效金额
        Double ratePerTotal = 0D;//占货比%
        Double realWin = 0D;//实占输赢
        Double realBackWater = 0D;//实占退水
        Double realResult = 0D;//实占结果
        Double winBackWater = 0D;//赚取退水
        Double winBackWaterResult = 0D;//赚水后结果
        Double offerSuperior = 0D;//贡献上级
        Double paySuperior = 0D;//应付上级
        Double subordinateChief = 0D; //下线应收（总监）
        Double subordinateBranch = 0D; //下线应收（分公司）
        Double subordinateStockholder = 0D; //下线应收（股东）
        Double subordinateGenAgent = 0D; //下线应收（总代理）
        Double subordinateAgent = 0D; //下线应收（代理）

        ClassReport entityTemp;
        //累加值
        for (int i = 0; i < resultList.size(); i++) {
            entityTemp = resultList.get(i);

            turnover += entityTemp.getTurnover();
            amount += entityTemp.getAmount();
            memberAmount += entityTemp.getMemberAmount();
            subordinateAmount += entityTemp.getSubordinateAmount();
            //rate = entityTemp.getRate();//占成由于多条数据的设置值可能不同，故后面根据相关值计算
            realAmount += entityTemp.getRealAmount();
            ratePerTotal += entityTemp.getRatePerTotal();
            realWin += entityTemp.getRealWin();
            realBackWater += entityTemp.getRealBackWater();
            realResult += entityTemp.getRealResult();
            winBackWater += entityTemp.getWinBackWater();
            winBackWaterResult += entityTemp.getWinBackWaterResult();
            offerSuperior += entityTemp.getOfferSuperior();
            paySuperior += entityTemp.getPaySuperior();
            validAmount += entityTemp.getValidAmount();
            subordinateChief += entityTemp.getSubordinateChief();
            subordinateBranch += entityTemp.getSubordinateBranch();
            subordinateStockholder += entityTemp.getSubordinateStockholder();
            subordinateGenAgent += entityTemp.getSubordinateGenAgent();
            subordinateAgent += entityTemp.getSubordinateAgent();
        }

        //构造总和对象数据
        entity.setTurnover(turnover);
        entity.setAmount(amount);
        entity.setMemberAmount(memberAmount);
        entity.setSubordinateAmount(subordinateAmount);
        entity.setValidAmount(validAmount);
        entity.setRate((realAmount / validAmount) * 100);//占成=实占注额/有效金额
        entity.setRealAmount(realAmount);
        entity.setRatePerTotal(100D);
        entity.setRealWin(realWin);
        entity.setRealBackWater(realBackWater);
        entity.setRealResult(realResult);
        entity.setWinBackWater(winBackWater);
        entity.setWinBackWaterResult(winBackWaterResult);
        entity.setOfferSuperior(offerSuperior);
        entity.setPaySuperior(paySuperior);
        entity.setSubordinateChief(subordinateChief);
        entity.setSubordinateBranch(subordinateBranch);
        entity.setSubordinateStockholder(subordinateStockholder);
        entity.setSubordinateGenAgent(subordinateGenAgent);
        entity.setSubordinateAgent(subordinateAgent);

        //解析占货比数据
        for (int i = 0; i < resultList.size(); i++) {
            //占货比%：实占注额/实占注额的合计
            resultList.get(i).setRatePerTotal(
                    resultList.get(i).getRealAmount() * 100 / realAmount);
        }

        //计算合计的占成（合计的实占注额除以合计的有效金额）
        entity.setRate((realAmount / validAmount) * 100);

        return entity;
    }

    /**
     * 解析指定的投注类型所对应的分类报表数据
     * 
     * @param userType          用户类型
     * @param commissionType
     * @param lotInfoList
     * @return
     */
    private ClassReport parseClassReport(String userType,
            String commissionType, ArrayList<LotInfoHis> lotInfoList) {

        ClassReport classEntity = new ClassReport();

        Long turnover = 0L;//成交笔数
        Double amount = 0D;//投注总额
        Double validAmount = 0D;//有效金额
        Double memberAmount = 0D;//会员输赢
        Double subordinateAmount = 0D;//应收下线
        Double rate = 0D;//占成
        Double realAmount = 0D;//实占注额
        Double ratePerTotal = 0D;//占货比%   
        Double realWin = 0D;//实占输赢 
        Double realBackWater = 0D;//实占退水 
        Double realResult = 0D;//实占结果
        Double winBackWater = 0D;//赚取退水
        Double winBackWaterResult = 0D;//赚水后结果
        Double offerSuperior = 0D;//贡献上级   
        Double paySuperior = 0D;//应付上级
        Double winBackWaterReple = 0D;//抵扣补货及赚水后结果   

        Double memberBackWater = 0D;//会员退水
        Double commissionBranch = 0D;//分公司佣金
        Double commissionGenAgent = 0D;//总代理佣金
        Double commissionStockholder = 0D;//股东佣金
        Double commissionAgent = 0D;//代理佣金
        Double commissionMember = 0D;//会员佣金

        Double winCommissionBranch = 0D; //分公司赚取佣金
        Double winCommissionGenAgent = 0D; //总代理赚取佣金
        Double winCommissionStockholder = 0D; //股东赚取佣金
        Double winCommissionAgent = 0D; //代理赚取佣金
        Double winCommissionMember = 0D; //会员赚取佣金     

        Double rateChief = 0D; //总监占成
        Double rateBranch = 0D; //分公司占成
        Double rateGenAgent = 0D; //总代理占成
        Double rateStockholder = 0D; //股东占成
        Double rateAgent = 0D; //代理占成

        Double subordinateChief = 0D; //下线应收（总监）
        Double subordinateBranch = 0D; //下线应收（分公司）
        Double subordinateStockholder = 0D; //下线应收（股东）
        Double subordinateGenAgent = 0D; //下线应收（总代理）
        Double subordinateAgent = 0D; //下线应收（代理）

        Double subordinateChiefReal = 0D; //下线应收（总监）（实占输赢，即不考虑退水的数值）
        Double subordinateBranchReal = 0D; //下线应收（分公司）（实占输赢，即不考虑退水的数值）
        Double subordinateStockholderReal = 0D; //下线应收（股东）（实占输赢，即不考虑退水的数值）
        Double subordinateGenAgentReal = 0D; //下线应收（总代理）（实占输赢，即不考虑退水的数值）
        Double subordinateAgentReal = 0D; //下线应收（代理）（实占输赢，即不考虑退水的数值）

        Double rateChiefSet = 0D; //总监占成设置值
        Double rateBranchSet = 0D; //分公司占成设置值
        Double rateStockholderSet = 0D; //股东占成设置值
        Double rateGenAgentSet = 0D; //总代理占成设置值
        Double rateAgentSet = 0D; //代理占成设置值

        LotInfoHis lotEntity;
        //成交笔数
        turnover = new Long(lotInfoList.size());
        for (int i = 0; i < lotInfoList.size(); i++) {
            lotEntity = lotInfoList.get(i);
            //投注总额
            amount += lotEntity.getMoney();

            Double memberAmount_temp = 0D;//会员输赢中间值
            Double memberBackWater_temp = 0D;//会员退水中间值

            //根据是否中奖计算相关数据取值
            if (LotInfoHis.WINNING_LOTTERY.equalsIgnoreCase(lotEntity
                    .getWinState())
                    || LotInfoHis.NOT_WINNING_LOTTERY
                            .equalsIgnoreCase(lotEntity.getWinState())) {
                //统计中奖、未中奖状态的值
                //赚取退水（佣金(截留部份)*投注额，除了打和，其他的都要退水）
                memberBackWater_temp = (new Double(lotEntity.getMoney()
                        * lotEntity.getCommissionMember().doubleValue())) / 100.0;
                //会员退水（会员自身的佣金*投注额，除了打和，其他的都要退水）
                memberBackWater = memberBackWater + memberBackWater_temp;

                //判断用户类型，获取对应的投注信息
                if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
                    //代理
                    //实占注额
                    realAmount = realAmount + lotEntity.getMoney()
                            * (lotEntity.getRateAgent().doubleValue() / 100.0);
                    //实占退水
                    realBackWater = realBackWater
                            + (new Double(lotEntity.getMoney())
                                    * (lotEntity.getCommissionAgent()
                                            .doubleValue() / 100.0) * (lotEntity
                                    .getRateAgent().doubleValue() / 100.0));
                    //贡献上级
                    offerSuperior = offerSuperior
                            + new Double(lotEntity.getMoney())
                            * (lotEntity.getRateGenAgent().doubleValue()
                                    + lotEntity.getRateStockholder()
                                            .doubleValue()
                                    + lotEntity.getRateBranch().doubleValue() + lotEntity
                                    .getRateChief().doubleValue()) / 100.0;
                    //赚取退水
                    winBackWater = winBackWater
                            + (new Double(lotEntity.getMoney())
                                    * (lotEntity.getCommissionAgent()
                                            .doubleValue() - lotEntity
                                            .getCommissionMember()
                                            .doubleValue()) / 100.0)
                            * (1 - 0 / 100.0);
                } else if (ManagerStaff.USER_TYPE_GEN_AGENT
                        .equalsIgnoreCase(userType)) {
                    //总代理
                    //实占注额
                    realAmount = realAmount
                            + lotEntity.getMoney()
                            * (lotEntity.getRateGenAgent().doubleValue() / 100.0);
                    //实占退水
                    realBackWater = realBackWater
                            + (new Double(lotEntity.getMoney())
                                    * (lotEntity.getCommissionGenAgent()
                                            .doubleValue() / 100.0) * (lotEntity
                                    .getRateGenAgent().doubleValue() / 100.0));
                    //贡献上级
                    offerSuperior = offerSuperior
                            + new Double(lotEntity.getMoney())
                            * (lotEntity.getRateStockholder().doubleValue()
                                    + lotEntity.getRateBranch().doubleValue() + lotEntity
                                    .getRateChief().doubleValue()) / 100.0;
                    //赚取退水
                    winBackWater = winBackWater
                            + (new Double(lotEntity.getMoney())
                                    * (lotEntity.getCommissionGenAgent()
                                            .doubleValue() - lotEntity
                                            .getCommissionAgent().doubleValue()) / 100.0)
                            * (1 - lotEntity.getRateAgent().doubleValue() / 100.0);
                } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                        .equalsIgnoreCase(userType)) {
                    //股东
                    //实占注额
                    realAmount = realAmount
                            + lotEntity.getMoney()
                            * (lotEntity.getRateStockholder().doubleValue() / 100.0);
                    //实占退水
                    realBackWater = realBackWater
                            + (new Double(lotEntity.getMoney())
                                    * (lotEntity.getCommissionStockholder()
                                            .doubleValue() / 100.0) * (lotEntity
                                    .getRateStockholder().doubleValue() / 100.0));
                    //贡献上级
                    offerSuperior = offerSuperior
                            + new Double(lotEntity.getMoney())
                            * (lotEntity.getRateBranch().doubleValue() + lotEntity
                                    .getRateChief().doubleValue()) / 100.0;
                    //赚取退水
                    winBackWater = winBackWater
                            + (new Double(lotEntity.getMoney())
                                    * (lotEntity.getCommissionStockholder()
                                            .doubleValue() - lotEntity
                                            .getCommissionGenAgent()
                                            .doubleValue()) / 100.0)
                            * (1 - ((lotEntity.getRateAgent().doubleValue() / 100.0) + (lotEntity
                                    .getRateGenAgent().doubleValue() / 100.0)));
                } else if (ManagerStaff.USER_TYPE_BRANCH
                        .equalsIgnoreCase(userType)) {
                    //分公司
                    //实占注额
                    realAmount = realAmount + lotEntity.getMoney()
                            * (lotEntity.getRateBranch().doubleValue() / 100.0);
                    //实占退水
                    realBackWater = realBackWater
                            + (new Double(lotEntity.getMoney())
                                    * (lotEntity.getCommissionBranch()
                                            .doubleValue() / 100.0) * (lotEntity
                                    .getRateBranch().doubleValue() / 100.0));
                    //贡献上级
                    offerSuperior = offerSuperior
                            + new Double(lotEntity.getMoney())
                            * (lotEntity.getRateChief().doubleValue()) / 100.0;
                    //赚取退水
                    winBackWater = winBackWater
                            + (new Double(lotEntity.getMoney())
                                    * (lotEntity.getCommissionBranch()
                                            .doubleValue() - lotEntity
                                            .getCommissionStockholder()
                                            .doubleValue()) / 100.0)
                            * (1 - ((lotEntity.getRateAgent().doubleValue() / 100.0)
                                    + (lotEntity.getRateGenAgent()
                                            .doubleValue() / 100.0) + (lotEntity
                                    .getRateStockholder().doubleValue() / 100.0)));
                } else if (ManagerStaff.USER_TYPE_CHIEF
                        .equalsIgnoreCase(userType)) {
                    //总监
                    //实占注额
                    realAmount = realAmount + lotEntity.getMoney()
                            * (lotEntity.getRateChief().doubleValue() / 100.0);
                    //实占退水
                    realBackWater = realBackWater
                            + (new Double(lotEntity.getMoney())
                                    * (lotEntity.getCommissionBranch()
                                            .doubleValue() / 100.0) * (lotEntity
                                    .getRateChief().doubleValue() / 100.0));
                    //贡献上级（总监无需贡献上级数据）
                    offerSuperior = offerSuperior + 0;
                }

                //有效金额
                validAmount = validAmount + lotEntity.getMoney();
            }

            //会员输赢，对应该会员所有输赢的总和(不计退水)
            if (LotInfoHis.WINNING_LOTTERY.equalsIgnoreCase(lotEntity
                    .getWinState())) {
                //累加“中奖”的投注额
                memberAmount = memberAmount + lotEntity.getWinAmount();
                memberAmount_temp = lotEntity.getWinAmount() * 1.0;
            }
            if (LotInfoHis.NOT_WINNING_LOTTERY.equalsIgnoreCase(lotEntity
                    .getWinState())) {
                //减去“未中奖”的投注额
                memberAmount = memberAmount - lotEntity.getMoney();
                memberAmount_temp = -lotEntity.getMoney() * 1.0;
            }

            if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
                //代理
                //实占输赢
                realWin = realWin + memberAmount_temp
                        * (lotEntity.getRateAgent().doubleValue() / 100.0);
            } else if (ManagerStaff.USER_TYPE_GEN_AGENT
                    .equalsIgnoreCase(userType)) {
                //总代理
                //实占输赢
                realWin = realWin + memberAmount_temp
                        * (lotEntity.getRateGenAgent().doubleValue() / 100.0);
            } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                    .equalsIgnoreCase(userType)) {
                //股东
                //实占输赢
                realWin = realWin
                        + memberAmount_temp
                        * (lotEntity.getRateStockholder().doubleValue() / 100.0);
            } else if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(userType)) {
                //分公司
                //实占输赢
                realWin = realWin + memberAmount_temp
                        * (lotEntity.getRateBranch().doubleValue() / 100.0);
            } else if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(userType)) {
                //总监
                //实占输赢
                realWin = realWin + memberAmount_temp
                        * (lotEntity.getRateChief().doubleValue() / 100.0);
            }

            //计算上线所对应的计算后的佣金、占成
            //打和则不计算佣金（退水）
            if (LotInfoHis.WINNING_LOTTERY.equalsIgnoreCase(lotEntity
                    .getWinState())
                    || LotInfoHis.NOT_WINNING_LOTTERY
                            .equalsIgnoreCase(lotEntity.getWinState())) {

                commissionBranch = commissionBranch
                        + (lotEntity.getMoney()
                                * 1.0
                                * (lotEntity.getCommissionBranch()
                                        .doubleValue() - lotEntity
                                        .getCommissionStockholder()
                                        .doubleValue()) / 100.0);
                commissionGenAgent = commissionGenAgent
                        + (lotEntity.getMoney()
                                * 1.0
                                * (lotEntity.getCommissionGenAgent()
                                        .doubleValue() - lotEntity
                                        .getCommissionAgent().doubleValue()) / 100.0);
                commissionStockholder = commissionStockholder
                        + (lotEntity.getMoney()
                                * 1.0
                                * (lotEntity.getCommissionStockholder()
                                        .doubleValue() - lotEntity
                                        .getCommissionGenAgent().doubleValue()) / 100.0);
                commissionAgent = commissionAgent
                        + (lotEntity.getMoney()
                                * 1.0
                                * (lotEntity.getCommissionAgent().doubleValue() - lotEntity
                                        .getCommissionMember().doubleValue()) / 100.0);
                commissionMember = commissionMember
                        + (lotEntity.getMoney()
                                * 1.0
                                * (lotEntity.getCommissionMember()
                                        .doubleValue() - 0) / 100.0);

                //赚取佣金
                winCommissionBranch = winCommissionBranch
                        + (lotEntity.getMoney()
                                * 1.0
                                * (lotEntity.getCommissionBranch()
                                        .doubleValue() - lotEntity
                                        .getCommissionStockholder()
                                        .doubleValue()) / 100.0)
                        * (1 - lotEntity.getRateBranch().doubleValue() / 100.0
                                - lotEntity.getRateStockholder().doubleValue()
                                / 100.0
                                - lotEntity.getRateGenAgent().doubleValue()
                                / 100.0 - lotEntity.getRateAgent()
                                .doubleValue() / 100.0);
                winCommissionGenAgent = winCommissionGenAgent
                        + (lotEntity.getMoney()
                                * 1.0
                                * (lotEntity.getCommissionGenAgent()
                                        .doubleValue() - lotEntity
                                        .getCommissionAgent().doubleValue()) / 100.0)
                        * (1 - lotEntity.getRateGenAgent().doubleValue() / 100.0 - lotEntity
                                .getRateAgent().doubleValue() / 100.0);
                winCommissionStockholder = winCommissionStockholder
                        + (lotEntity.getMoney()
                                * (lotEntity.getCommissionStockholder()
                                        .doubleValue() - lotEntity
                                        .getCommissionGenAgent().doubleValue()) / 100.0)
                        * (1 - lotEntity.getRateStockholder().doubleValue()
                                / 100.0
                                - lotEntity.getRateGenAgent().doubleValue()
                                / 100.0 - lotEntity.getRateAgent()
                                .doubleValue() / 100.0);
                winCommissionAgent = winCommissionAgent
                        + (lotEntity.getMoney()
                                * (lotEntity.getCommissionAgent().doubleValue() - lotEntity
                                        .getCommissionMember().doubleValue()) / 100.0)
                        * (1 - lotEntity.getRateAgent().doubleValue() / 100.0);
                winCommissionMember = winCommissionMember
                        + (lotEntity.getMoney()
                                * (lotEntity.getCommissionMember()
                                        .doubleValue() - 0) / 100.0);

                //实占结果（指会员输赢 + 会员退水）*占成%
                rateChief = rateChief
                        + (memberAmount_temp + (lotEntity.getMoney()
                                * lotEntity.getCommissionBranch().doubleValue() / 100.0))
                        * lotEntity.getRateChief().doubleValue() / 100.0;
                rateBranch = rateBranch
                        + (memberAmount_temp + (lotEntity.getMoney()
                                * lotEntity.getCommissionStockholder()
                                        .doubleValue() / 100.0))
                        * lotEntity.getRateBranch().doubleValue() / 100.0;
                rateGenAgent = rateGenAgent
                        + (memberAmount_temp + (lotEntity.getMoney()
                                * lotEntity.getCommissionAgent().doubleValue() / 100.0))
                        * lotEntity.getRateGenAgent().doubleValue() / 100.0;
                rateStockholder = rateStockholder
                        + (memberAmount_temp + (lotEntity.getMoney()
                                * lotEntity.getCommissionGenAgent()
                                        .doubleValue() / 100.0))
                        * lotEntity.getRateStockholder().doubleValue() / 100.0;

                rateAgent = rateAgent
                        + (memberAmount_temp + (lotEntity.getMoney()
                                * lotEntity.getCommissionMember().doubleValue() / 100.0))
                        * lotEntity.getRateAgent().doubleValue() / 100.0;

                //各级应收下线
                subordinateChief = subordinateChief
                        + (memberAmount_temp + (lotEntity.getMoney()
                                * lotEntity.getCommissionBranch().doubleValue() / 100.0))
                        * (lotEntity.getRateChief().doubleValue() / 100.0);
                subordinateBranch = subordinateBranch
                        + (memberAmount_temp + (lotEntity.getMoney()
                                * lotEntity.getCommissionStockholder()
                                        .doubleValue() / 100.0))
                        * (1 - lotEntity.getRateAgent().doubleValue() / 100.0
                                - lotEntity.getRateGenAgent().doubleValue()
                                / 100.0 - lotEntity.getRateStockholder()
                                .doubleValue() / 100.0);
                subordinateGenAgent = subordinateGenAgent
                        + (memberAmount_temp + (lotEntity.getMoney()
                                * lotEntity.getCommissionAgent().doubleValue() / 100.0))
                        * (1 - lotEntity.getRateAgent().doubleValue() / 100.0);
                subordinateStockholder = subordinateStockholder
                        + (memberAmount_temp + (lotEntity.getMoney()
                                * lotEntity.getCommissionGenAgent()
                                        .doubleValue() / 100.0))
                        * (1 - lotEntity.getRateAgent().doubleValue() / 100.0 - lotEntity
                                .getRateGenAgent().doubleValue() / 100.0);
                subordinateAgent = subordinateAgent
                        + (memberAmount_temp + (lotEntity.getMoney()
                                * lotEntity.getCommissionMember().doubleValue() / 100.0))
                        * 1;

                //各级应收下线（实占输赢，即不考虑退水的数值）
                subordinateChiefReal = subordinateChiefReal
                        + (memberAmount_temp)
                        * lotEntity.getRateChief().doubleValue() / 100.0;
                subordinateBranchReal = subordinateBranchReal
                        + (memberAmount_temp)
                        * (1 - lotEntity.getRateAgent().doubleValue() / 100.0
                                - lotEntity.getRateGenAgent().doubleValue()
                                / 100.0 - lotEntity.getRateStockholder()
                                .doubleValue() / 100.0);
                subordinateGenAgentReal = subordinateGenAgentReal
                        + (memberAmount_temp)
                        * (1 - lotEntity.getRateAgent().doubleValue() / 100.0);
                subordinateStockholderReal = subordinateStockholderReal
                        + (memberAmount_temp)
                        * (1 - lotEntity.getRateAgent().doubleValue() / 100.0 - lotEntity
                                .getRateGenAgent().doubleValue() / 100.0);
                subordinateAgentReal = subordinateAgentReal
                        + (memberAmount_temp) * 1;
            }

            //赚水后结果（实占结果（代理实占）－赚取退水）
            //直接在实体类里面通过实占结果+赚取退水计算
            //winBackWaterResult = rateAgent - winBackWater;

            rate = lotEntity.getRateAgent().doubleValue();//代理占成（只需要存最后一条记录的占成即可）
            rateChiefSet = rateChiefSet
                    + lotEntity.getRateChief().doubleValue();
            rateBranchSet = rateBranchSet
                    + lotEntity.getRateBranch().doubleValue();
            rateStockholderSet = rateStockholderSet
                    + lotEntity.getRateStockholder().doubleValue();
            rateGenAgentSet = rateGenAgentSet
                    + lotEntity.getRateGenAgent().doubleValue();
            rateAgentSet = rateAgentSet
                    + lotEntity.getRateAgent().doubleValue();
        }

        //应收下线，指对应该会员所有输赢的总和+退水总和(和局不算退水)
        subordinateAmount = memberAmount + memberBackWater;

        if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
            //代理
            //实占结果，会员退水后结果*占成%
            realResult = rateAgent;
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT.equalsIgnoreCase(userType)) {
            //总代理
            //实占结果，会员退水后结果*占成%
            realResult = rateGenAgent;
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                .equalsIgnoreCase(userType)) {
            //股东
            //实占结果，会员退水后结果*占成%
            realResult = rateStockholder;
        } else if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(userType)) {
            //分公司
            //实占结果，会员退水后结果*占成%
            realResult = rateBranch;
        } else if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(userType)) {
            //总监
            //实占结果，会员退水后结果*占成%
            realResult = rateChief;
        }

        //应付上级（应收下线－赚水后结果）
        //直接在实体类里面通过应收下线－赚水后结果计算
        //paySuperior = subordinateAmount - winBackWaterResult;

        classEntity.setCommissionType(commissionType);//下注类型
        classEntity.setTurnover(turnover);//成交笔数
        classEntity.setAmount(amount);//投注总额
        classEntity.setValidAmount(validAmount);//有效金额
        classEntity.setMemberAmount(memberAmount);//会员输赢
        classEntity.setSubordinateAmount(subordinateAmount);//应收下线
        classEntity.setRate(rate);//占成
        classEntity.setRealAmount(realAmount);//实占注额
        classEntity.setRatePerTotal(ratePerTotal);//占货比%
        classEntity.setRealWin(realWin);//实占输赢
        classEntity.setRealBackWater(realBackWater);//实占退水
        classEntity.setRealResult(realResult);//实占结果
        classEntity.setWinBackWater(winBackWater);//赚取退水
        classEntity.setWinBackWaterResult(winBackWaterResult);//赚水后结果
        classEntity.setOfferSuperior(offerSuperior);//贡献上级
        classEntity.setPaySuperior(paySuperior);//应付上级
        classEntity.setWinBackWaterReple(winBackWaterReple);//抵扣补货及赚水后结果

        classEntity.setSubordinateChief(subordinateChief);//下线应收（总监）
        classEntity.setSubordinateBranch(subordinateBranch);//下线应收（分公司）
        classEntity.setSubordinateStockholder(subordinateStockholder);//下线应收（股东）
        classEntity.setSubordinateGenAgent(subordinateGenAgent);//下线应收（总代理）
        classEntity.setSubordinateAgent(subordinateAgent);//下线应收（代理）

        classEntity.setSubordinateChiefReal(subordinateChiefReal);//下线应收（总监）（实占输赢，即不考虑退水的数值）
        classEntity.setSubordinateBranchReal(subordinateBranchReal);//下线应收（分公司）（实占输赢，即不考虑退水的数值）
        classEntity.setSubordinateStockholderReal(subordinateStockholderReal);//下线应收（股东）（实占输赢，即不考虑退水的数值）
        classEntity.setSubordinateGenAgentReal(subordinateGenAgentReal);//下线应收（总代理）（实占输赢，即不考虑退水的数值）
        classEntity.setSubordinateAgentReal(subordinateAgentReal);//下线应收（代理）（实占输赢，即不考虑退水的数值）

        return classEntity;
    }

    /**
     * 查询指定代理用户的投注数据
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
     * @return
     *         HashMap 数据结构，key为彩票种类，value为彩票种类所对应的投注数据列表
     *         如：key = StatReport.PLAY_TYPE_GDKLSF，value 的值为 ArrayList<GdklsfHis>
     *      
     */
    public HashMap<String, ArrayList> findAgentLotInfoList(Long userID,
            String lotteryType, String playType, String periodsNum,
            Date startDate, Date endDate) {

        HashMap<String, ArrayList> result;

        //1、查询代理用户所对应的会员用户信息（包括直属会员）
        ArrayList<MemberStaffExt> memberStaffList = classReportDao
                .findAgentMemberList(userID);

        //2、调用方法查询所对应的投注数据
        result = this.findLotInfoList(memberStaffList, lotteryType, playType,
                periodsNum, startDate, endDate);

        return result;
    }

    /**
     * 查询指定总代理用户相关的投注数据
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
     * @return
     *         HashMap 数据结构，key为彩票种类，value为彩票种类所对应的投注数据列表
     *         如：key = StatReport.PLAY_TYPE_GDKLSF，value 的值为 ArrayList<GdklsfHis>
     *      
     */
    public HashMap<String, ArrayList> findGenAgentLotInfoList(Long userID,
            String lotteryType, String playType, String periodsNum,
            Date startDate, Date endDate) {

        //初始化返回值
        HashMap<String, ArrayList> result;

        //目前是一次信查询出总代理下线对应的所有会员的数据统计
        //如果出现效率问题或者SQL语句超长的情况，则修改为分级统计，即总代理先统计单个代理的数据，再统计其他

        //1、查询总代理用户所对应的会员用户信息（包括下线及直属会员）
        ArrayList<MemberStaffExt> memberStaffList = classReportDao
                .findGenAgentMemberList(userID);

        //2、调用方法查询所对应的投注数据
        result = this.findLotInfoList(memberStaffList, lotteryType, playType,
                periodsNum, startDate, endDate);

        return result;
    }

    /**
     * 查询指定股东用户相关的投注数据
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
     * @return
     *         HashMap 数据结构，key为彩票种类，value为彩票种类所对应的投注数据列表
     *         如：key = StatReport.PLAY_TYPE_GDKLSF，value 的值为 ArrayList<GdklsfHis>
     *      
     */
    public HashMap<String, ArrayList> findStockholderLotInfoList(Long userID,
            String lotteryType, String playType, String periodsNum,
            Date startDate, Date endDate) {

        //初始化返回值
        HashMap<String, ArrayList> result;

        //目前是一次信查询出股东下线对应的所有会员的数据统计
        //如果出现效率问题或者SQL语句超长的情况，则修改为分级统计，即股东先统计单个总代理的数据，再统计其他

        //1、查询股东用户所对应的会员用户信息（包括下线及直属会员）
        ArrayList<MemberStaffExt> memberStaffList = classReportDao
                .findStockholderMemberList(userID);

        //2、调用方法查询所对应的投注数据
        result = this.findLotInfoList(memberStaffList, lotteryType, playType,
                periodsNum, startDate, endDate);

        return result;
    }

    /**
     * 查询指定分公司用户相关的投注数据
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
     * @return
     *         HashMap 数据结构，key为彩票种类，value为彩票种类所对应的投注数据列表
     *         如：key = StatReport.PLAY_TYPE_GDKLSF，value 的值为 ArrayList<GdklsfHis>
     *      
     */
    public HashMap<String, ArrayList> findBranchLotInfoList(Long userID,
            String lotteryType, String playType, String periodsNum,
            Date startDate, Date endDate) {

        //初始化返回值
        HashMap<String, ArrayList> result;

        //目前是一次信查询出分公司下线对应的所有会员的数据统计
        //如果出现效率问题或者SQL语句超长的情况，则修改为分级统计，即分公司先统计单个股东的数据，再统计其他

        //1、查询分公司用户所对应的会员用户信息（包括下线及直属会员）
        ArrayList<MemberStaffExt> memberStaffList = classReportDao
                .findBranchMemberList(userID);

        //2、调用方法查询所对应的投注数据
        result = this.findLotInfoList(memberStaffList, lotteryType, playType,
                periodsNum, startDate, endDate);

        return result;
    }

    /**
     * 查询指定总监用户相关的投注数据
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
     * @return
     *         HashMap 数据结构，key为彩票种类，value为彩票种类所对应的投注数据列表
     *         如：key = StatReport.PLAY_TYPE_GDKLSF，value 的值为 ArrayList<GdklsfHis>
     *      
     */
    public HashMap<String, ArrayList> findChiefLotInfoList(Long userID,
            String lotteryType, String playType, String periodsNum,
            Date startDate, Date endDate) {

        //初始化返回值
        HashMap<String, ArrayList> result;

        //TODO 目前是一次信查询出总监下线对应的所有会员的数据统计
        //如果出现效率问题或者SQL语句超长的情况，则修改为分级统计，即总监先统计单个分公司的数据，再统计其他

        //1、查询总监用户所对应的会员用户信息（包括下线及直属会员）
        ArrayList<MemberStaffExt> memberStaffList = classReportDao
                .findChiefMemberList(userID);

        //2、调用方法查询所对应的投注数据
        result = this.findLotInfoList(memberStaffList, lotteryType, playType,
                periodsNum, startDate, endDate);

        return result;
    }

    /**
     * 查询用户的投注数据
     * 
     * @param memberStaffList
     *            投注会员列表
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
     * @return
     *         HashMap 数据结构，key为彩票种类，value为彩票种类所对应的投注数据列表
     *         如：key = StatReport.PLAY_TYPE_GDKLSF，value 的值为 ArrayList<GdklsfHis>
     *      
     */
    private HashMap<String, ArrayList> findLotInfoList(
            ArrayList<MemberStaffExt> memberStaffList, String lotteryType,
            String playType, String periodsNum, Date startDate, Date endDate) {

        HashMap<String, ArrayList> result = new HashMap<String, ArrayList>();

        StringBuffer conditionStr = new StringBuffer();
        conditionStr.append(" 1=1 ");

        //1、构造相关查询条件
        //构造期数查询条件
        if (null != periodsNum && periodsNum.trim().length() > 0
                && !("--- 所有期 ---".equalsIgnoreCase(periodsNum.trim()))) {
            conditionStr.append(" AND PERIODS_NUM LIKE '" + periodsNum + "' ");
        }
        //构造下注类型查询条件（下注类型对应数据表中的COMMISSION_TYPE）
        if (null != playType && playType.trim().length() > 0
                && !(StatReport.PLAY_TYPE_ALL.equalsIgnoreCase(playType))) {
            conditionStr
                    .append(" AND COMMISSION_TYPE LIKE '" + playType + "' ");
        }
        //构造时间查询条件 bettingDate
        if (null != startDate || null != endDate) {
            //格式化需要的时间
            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
                    DateFormat.MEDIUM, Locale.CHINA);
            String startDateStr = df.format(startDate);
            String endDateStr = df.format(endDate);

            conditionStr.append(" AND BETTING_DATE BETWEEN to_date('"
                    + startDateStr + "','yyyy-mm-dd hh24:mi:ss') AND to_date('"
                    + endDateStr + "','yyyy-mm-dd hh24:mi:ss')");
        }

        //2、查询代理用户所对应的会员用户信息
        //ArrayList<MemberStaffExt> memberStaffList = classReportDao
        //        .findAgentMemberList(userID);

        //3、查询代理用户的直属用户信息（代理用户无直属会员，其他的管理账户需要）

        //4、构造会员用户ID查询条件
        StringBuffer memberIDGroup = new StringBuffer();
        String memberIDStr = null;
        if (null != memberStaffList && memberStaffList.size() > 0) {
            for (int i = 0; i < memberStaffList.size(); i++) {
                memberIDGroup.append(memberStaffList.get(i).getMemberStaffID());
                memberIDGroup.append(",");
            }
            memberIDStr = memberIDGroup
                    .substring(0, memberIDGroup.length() - 1);
        }

        //增加查询条件
        if (null != memberIDStr && memberIDStr.length() > 0) {
            conditionStr
                    .append(" AND BETTING_USER_ID IN (" + memberIDStr + ")");
        }
        log.info("查询条件为：" + conditionStr.toString());

        //5、根据彩票种类查询对应的投注信息（已经处理连码等问题）
        ArrayList<GdklsfHis> gdklsfList = null;
        ArrayList<CqsscHis> cqsscList = null;
        ArrayList<HklhcHis> hklhcList = null;
        ArrayList<BjscHis> bjscList = null;

        if (StatReport.PLAY_TYPE_ALL.equalsIgnoreCase(lotteryType)
                || StatReport.PLAY_TYPE_GDKLSF.equalsIgnoreCase(lotteryType)) {
            //广东快乐十分
            gdklsfList = gdklsfHisLogic.findGdklsfHis(conditionStr.toString());
        }
        if (StatReport.PLAY_TYPE_ALL.equalsIgnoreCase(lotteryType)
                || StatReport.PLAY_TYPE_CQSSC.equalsIgnoreCase(lotteryType)) {
            //重庆时时彩
            cqsscList = cqsscHisLogic.findCqsscHis(conditionStr.toString());
        }
        /*if (StatReport.PLAY_TYPE_ALL.equalsIgnoreCase(lotteryType)
                || StatReport.PLAY_TYPE_HKLHC.equalsIgnoreCase(lotteryType)) {
            //香港六合彩
            hklhcList = hklhcHisLogic.findHklhcHis(conditionStr.toString());
        }*/
        if (StatReport.PLAY_TYPE_ALL.equalsIgnoreCase(lotteryType)
                || StatReport.PLAY_TYPE_BJSC.equalsIgnoreCase(lotteryType)) {
            //北京赛车
            bjscList = bjscHisLogic.findBjscHis(conditionStr.toString());
        }

        //6、返回结果
        result.put(StatReport.PLAY_TYPE_GDKLSF, gdklsfList);
        result.put(StatReport.PLAY_TYPE_CQSSC, cqsscList);
        //result.put(StatReport.PLAY_TYPE_HKLHC, hklhcList);
        result.put(StatReport.PLAY_TYPE_BJSC, bjscList);

        return result;
    }

    /**
     * 查询补货数据
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
    public ReplenishReport findClassReportReplenish(Long userID,
            String lotteryType, String playType, String periodsNum,
            Date startDate, Date endDate, String userType) {

        ReplenishReport replenishEntity = null;

        ConditionData conditionData = new ConditionData();

        conditionData.addEqual("replenishUserId", userID);//补货人ID
        conditionData.addBetween("bettingDate", startDate, true, endDate, true);//补货时间
        //添加中奖状态
        conditionData.addIn("winState", Replenish.WIN_STATE_WIN);
        conditionData.addIn("winState", Replenish.WIN_STATE_NOT_WIN);
        conditionData.addIn("winState", Replenish.WIN_STATE_PRIZE);
        conditionData.addEqual("commissionType", playType);//投注类型
        //判断彩票种类是否是所有，如果不是所有，则增加对应的查询条件
        if (StatReport.PLAY_TYPE_GDKLSF.equalsIgnoreCase(lotteryType)) {
            //广东快乐十分
            conditionData.addLike("typeCode", "GDKLSF_%");
        /*} else if (StatReport.PLAY_TYPE_HKLHC.equalsIgnoreCase(lotteryType)) {
            //香港六合彩
            conditionData.addLike("typeCode", "HK_%");*/
        } else if (StatReport.PLAY_TYPE_CQSSC.equalsIgnoreCase(lotteryType)) {
            //重庆时时彩
            conditionData.addLike("typeCode", "CQSSC_%");
        }

        //调用数据库层访问类查询数据
        ArrayList<Replenish> replenishList = (ArrayList<Replenish>) classReportDao
                .findReplenishList(conditionData, 1, 99999);

        //TODO 计算数据

        return replenishEntity;
    }
}
