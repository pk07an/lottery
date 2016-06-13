package com.npc.lottery.statreport.logic.interf;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import com.npc.lottery.statreport.entity.ClassReport;
import com.npc.lottery.statreport.entity.LotInfoHis;

/**
 * 分类报表
 * 
 * @author User
 *
 */
public interface IClassReportLogic {

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
            Date startDate, Date endDate, String userType);

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
            Date startDate, Date endDate, String userType);

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
            Date startDate, Date endDate);

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
            Date startDate, Date endDate);

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
            Date startDate, Date endDate);

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
            Date startDate, Date endDate);

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
            Date startDate, Date endDate);

    /**
     * 根据分类报表列表数据计算总和分类报表数据
     * 此方法同时解析占货比数据
     * 
     * @param resultList
     * @return
     */
    public ClassReport parseTotalClassReport(ArrayList<ClassReport> resultList);
}
