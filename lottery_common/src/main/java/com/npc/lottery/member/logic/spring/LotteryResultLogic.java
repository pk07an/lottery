package com.npc.lottery.member.logic.spring;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.npc.lottery.common.Constant;
import com.npc.lottery.member.dao.interf.IBetDao;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.entity.PlayWinInfo;
import com.npc.lottery.member.logic.interf.ICheckLogic;
import com.npc.lottery.member.logic.interf.ILotteryResultLogic;
import com.npc.lottery.member.logic.interf.IPlayTypeLogic;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.INCPeriodsInfoLogic;
import com.npc.lottery.rule.BJSCRule;
import com.npc.lottery.rule.CQSSCBallRule;
import com.npc.lottery.rule.GDKLSFRule;
import com.npc.lottery.rule.HKLHCRule;
import com.npc.lottery.rule.JSSBRule;
import com.npc.lottery.rule.NCRule;
import com.npc.lottery.statreport.logic.interf.IClassReportEricLogic;
import com.npc.lottery.statreport.logic.interf.IReportStatusLogic;
import com.npc.lottery.statreport.logic.interf.ISettledReportEricLogic;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.util.PlayTypeUtils;

/**
 * 功能逻辑处理类
 * 
 * @author none
 * 
 */
@Service
public class LotteryResultLogic implements ILotteryResultLogic
{
    private final static Logger log = Logger.getLogger(LotteryResultLogic.class);
    
	@Autowired
	private IBetDao betDao;
	@Autowired
	protected IPlayTypeLogic playTypeLogic;
	@Autowired
	protected IUserCommissionLogic userCommissionLogic;
	@Autowired
	private ICQPeriodsInfoLogic icqPeriodsInfoLogic;
	@Autowired
	private IGDPeriodsInfoLogic periodsInfoLogic;
	@Autowired
	protected IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
	@Autowired
	protected IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic;
	@Autowired
	protected INCPeriodsInfoLogic ncPeriodsInfoLogic;
	@Autowired
	private ISettledReportEricLogic settledReportEricLogic;// 报表统计
	@Autowired
	private IClassReportEricLogic classReportEricLogic;// 报表统计
	@Autowired
	private IReportStatusLogic reportStatusLogic;

    private ICheckLogic checkLogic;




    public void updateLotteryGD(String periodNum, List<Integer> winNums,String scheme)
    {
        long startTime = System.currentTimeMillis();
        try
        {
            // 更新所有投注 是否中奖
            log.info("开始更新广东快乐十分开奖数据>>>>>>>>>" + periodNum + ">>>>>>>>>>>>" + winNums.toString());
            this.updateGDBallLottery(periodNum, winNums, Lists.newArrayList(Constant.GDKLSF_LOTTERY_TABLE_LIST),scheme);

            // scanGDKLSFBall(periodNum, winNums);
            // 扫描中奖数据更新用户可用余额
            // scanGDKLSFBall(periodNum, winNums);
            // 更新连码是否中奖 并更新余额
            log.info("开始更新广东快乐十分连码开奖数据");
            updateGDLMResultLottery(periodNum, winNums,scheme);

            if (GDKLSFRule.HE(winNums))
            {
                upateGDHeResult(periodNum, Lists.newArrayList(Constant.GDKLSF_LOTTERY_TABLE_LIST),scheme);
            }

            scanReplenishDate(periodNum, "GDKLSF", winNums,scheme);
            // 批量更改用户信用额度
            batchUpdateUserAvailableCredit(periodNum, "GDKLSF",scheme);
           
            //log.info("开始迁移广东数据到历史表");
            //betDao.miragationGDDataToHistory(periodNum, "1",scheme);
            //log.info("开始迁移广东补货数据到历史表");
            //betDao.miragationReplenishDataToHistory(periodNum, "GD",scheme);

            //log.info("开始校验历史表与投注备份表数据");
            //checkLogic.checkBetWithBackupByPeridosNum(periodNum, Constant.LOTTERY_TYPE_GDKLSF,scheme);
            //boolean resultReplenish = checkLogic.checkReplenishWithHistory(periodNum,scheme);// 校验补货
            long endTime = System.currentTimeMillis();

            log.info("广东" + periodNum + "期兑奖结束 花费时间>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + (endTime - startTime) / 1000);
        }
        catch (Exception e)
        {
            log.error("广东兑奖发生异常",e);
            throw new RuntimeException("广东兑奖发生异常");
        }

    }


    /**
     * 农场兑奖
     * 
     * @param periodNum
     * @param winNums
     */

    public void updateLotteryNC(String periodNum, List<Integer> winNums,String scheme)
    {
        long startTime = System.currentTimeMillis();
        try
        {
            // 更新所有投注 是否中奖
            log.info("开始更新幸运农场开奖数据>>>>>>>>>" + periodNum + ">>>>>>>>>>>>" + winNums.toString());
            this.updateNCBallLottery(periodNum, winNums, Lists.newArrayList(Constant.NC_TABLE_LIST),scheme);

            log.info("开始更新幸运农场连码开奖数据");
            updateNCLMResultLottery(periodNum, winNums, Constant.NC_TABLE_NAME,scheme);
            if (NCRule.HE(winNums))
            {
                upateNCHeResult(periodNum, Lists.newArrayList(Constant.NC_TABLE_LIST),scheme);
            }

            scanReplenishDate(periodNum, "NC", winNums,scheme);
            // 批量更改用户信用额度
            batchUpdateUserAvailableCredit(periodNum, "NC",scheme);
           
           // log.info("开始迁移农场数据到历史表");
            //betDao.miragationNCDataToHistory(periodNum, "1",scheme);
            //log.info("开始迁移农场补货数据到历史表");
            //betDao.miragationReplenishDataToHistory(periodNum, "NC",scheme);
            //log.info("开始校验历史表与投注备份表数据");
            //checkLogic.checkBetWithBackupByPeridosNum(periodNum, Constant.LOTTERY_TYPE_NC,scheme);
            //boolean resultReplenish = checkLogic.checkReplenishWithHistory(periodNum,scheme);// 校验补货
            long endTime = System.currentTimeMillis();

			log.info(scheme + "农场" + periodNum + "期兑奖结束 花费时间>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + (endTime - startTime) / 1000);
        }
        catch (Exception e)
        {
            log.error("农场兑奖发生异常",e);
            throw new RuntimeException("农场兑奖发生异常");
        }

    }

    @Override
    public void updateNCForReLottery(String periodNum, List<Integer> winNums, Date openQuotTime,String scheme)
    {
        long startTime = System.currentTimeMillis();
        try
        {
            // 更新所有投注 是否中奖
            log.info("开始更新幸运农场开奖数据>>>>>>>>>" + periodNum + ">>>>>>>>>>>>" + winNums.toString());
            this.updateNCBallLottery(periodNum, winNums, Lists.newArrayList(Constant.NC_TABLE_LIST),scheme);

            log.info("开始更新幸运农场连码开奖数据");
            updateNCLMResultLottery(periodNum, winNums, Constant.NC_TABLE_NAME,scheme);
            if (NCRule.HE(winNums))
            {
                upateNCHeResult(periodNum, Lists.newArrayList(Constant.NC_TABLE_LIST),scheme);
            }

            scanReplenishDate(periodNum, "NC", winNums,scheme);

            Date now = new Date();
            String todayStr = new SimpleDateFormat("yyyyMMdd").format(now);
            String yesterdayStr = new SimpleDateFormat("yyyyMMdd").format(DateUtils.addDays(now, -1));
            Date restoreUserAvailableCreditTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(todayStr + " 02:30:00");
            Date yesterdayMondayOpenTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(yesterdayStr + " 09:50:00");// 前一天早上第一期
            Date todayMondayOpenTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(todayStr + " 09:50:00");// 当天天早上第一期
            if ((now.before(restoreUserAvailableCreditTime) && openQuotTime.after(yesterdayMondayOpenTime)) || (now.after(restoreUserAvailableCreditTime) && openQuotTime.after(todayMondayOpenTime)))
            {
                // 如果是当天2:30恢复额度前重新对前天一天早上10点后 或者 当天2:30恢复额度后，对当天早上10点后的盘期重新兑奖，需要恢复信用额度
                // 批量更改用户信用额度
                batchUpdateUserAvailableCredit(periodNum, "NC",scheme);
            }
            // 否则不会更新可用余额
            log.info("开始迁移农场数据到历史表");
            betDao.miragationNCDataToHistory(periodNum, "1",scheme);
            log.info("开始迁移农场补货数据到历史表");
            betDao.miragationReplenishDataToHistory(periodNum, "NC",scheme);

            log.info("开始校验历史表与投注备份表数据");
            //checkLogic.checkBetWithBackupByPeridosNum(periodNum, Constant.LOTTERY_TYPE_NC,scheme);
            //boolean resultReplenish = checkLogic.checkReplenishWithHistory(periodNum,scheme);// 校验补货
            long endTime = System.currentTimeMillis();

            log.info("农场" + periodNum + "期兑奖结束 花费时间>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + (endTime - startTime) / 1000);
        }
        catch (Exception e)
        {
            log.error("农场兑奖发生异常" ,e);
            throw new RuntimeException("农场兑奖发生异常");
        }
    }


    @Override
    public void updateGDForReLottery(String periodNum, List<Integer> winNums, Date openQuotTime,String scheme)
    {
        long startTime = System.currentTimeMillis();
        try
        {
            // 更新所有投注 是否中奖
            log.info("开始更新广东快乐十分重新开奖数据>>>>>>>>>" + periodNum + ">>>>>>>>>>>>" + winNums.toString());
            this.updateGDBallLottery(periodNum, winNums, Lists.newArrayList(Constant.GDKLSF_LOTTERY_TABLE_LIST),scheme);

            // 更新连码是否中奖 并更新余额
            log.info("开始更新广东快乐十分连码开奖数据");
            updateGDLMResultLottery(periodNum, winNums,scheme);

            if (GDKLSFRule.HE(winNums))
            {
                upateGDHeResult(periodNum, Lists.newArrayList(Constant.GDKLSF_LOTTERY_TABLE_LIST),scheme);
            }

            scanReplenishDate(periodNum, "GDKLSF", winNums,scheme);

            final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            String openTimeDate = sf.format(openQuotTime); // 开盘日期
            String today = sf.format(new Date());// 当天日期
            String yesterday = sf.format(DateUtils.addDays(new Date(), -1));// 前一天
            Date now = new Date();
            Date restoreUserAvailableCreditTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(today + " 02:30:00");
            if ((now.before(restoreUserAvailableCreditTime) && yesterday.equals(openTimeDate)) || (now.after(restoreUserAvailableCreditTime) && today.equals(openTimeDate)))
            {
                // 如果是当天恢复额度2:30前操作，前一天的数据需要恢复额度 或者 2:30后操作，当天的盘期重新兑奖，需要恢复信用额度
                // 批量更改用户信用额度
                batchUpdateUserAvailableCredit(periodNum, "GDKLSF",scheme);
            }
            // 否则不会更新可用余额
            log.info("开始迁移广东数据到历史表");
            betDao.miragationGDDataToHistory(periodNum, "1",scheme);
            log.info("开始迁移广东补货数据到历史表");
            betDao.miragationReplenishDataToHistory(periodNum, "GD",scheme);

            log.info("开始校验历史表与投注备份表数据");
            //checkLogic.checkBetWithBackupByPeridosNum(periodNum, Constant.LOTTERY_TYPE_GDKLSF,scheme);
            //boolean resultReplenish = checkLogic.checkReplenishWithHistory(periodNum,scheme);// 校验补货
            long endTime = System.currentTimeMillis();

            log.info("广东" + periodNum + "期兑奖结束 花费时间>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + (endTime - startTime) / 1000);
        }
        catch (Exception e)
        {
            log.error("广东兑奖发生异常",e);
            throw new RuntimeException("广东兑奖发生异常");
        }

    }


    public void updateLotteryCQ(String periodNum, List<Integer> winNums,String scheme)
    {
        long startTime = System.currentTimeMillis();
        // 更新所有投注 是否中奖
        try
        {
            log.info("开始更新重庆开奖数据>>>>>>>>>>>>>>>" + periodNum + ">>>>>>>>>" + winNums.toString());
            this.updateCQResultLottery(periodNum, winNums, Lists.newArrayList(Constant.CQSSC_LOTTERY_TABLE_LIST),scheme);
            // 更新重庆和数据
            if (CQSSCBallRule.HE(winNums))
            {
                log.info("打和了");
                upateCQHeResult(periodNum, Lists.newArrayList(Constant.CQSSC_TABLE_LIST),scheme);
            }

            scanReplenishDate(periodNum, "CQSSC", winNums,scheme);

            // 批量更改用户信用额度
            batchUpdateUserAvailableCredit(periodNum, "CQSSC",scheme);
            // log.info("开始更新重庆一次性开奖数据>>>>>>>>>>>>>>>>>>>>>>>>>>>" + periodNum);
            // this.scanCQSSCBall(periodNum, winNums);

            // 扫描中奖数据更新用户可用余额
            // scanCQSSCBall(periodNum, winNums);


            //log.info("开始迁移重庆开奖数据");
            //betDao.miragationCQDataToHistory(periodNum, "1",scheme);

            //log.info("开始迁移重庆补货数据到历史表");
            //betDao.miragationReplenishDataToHistory(periodNum, "CQSSC",scheme);

            //log.info("开始校验历史表与投注备份表数据");
            //checkLogic.checkBetWithBackupByPeridosNum(periodNum, Constant.LOTTERY_TYPE_CQSSC,scheme);
            //boolean resultReplenish = checkLogic.checkReplenishWithHistory(periodNum,scheme);// 校验补货

            long endTime = System.currentTimeMillis();
            log.info("重庆" + periodNum + "期兑奖结束 花费时间>>>>>>>>>>>>>>>>>>>>>>>>>>>" + (endTime - startTime) + "MS");
        }
        catch (Exception e)
        {
            log.error("重庆兑奖发生异常" ,e);
            throw new RuntimeException("重庆兑奖发生异常");
        }

    }


    @Override
    public void updateCQForReLottery(String periodNum, List<Integer> winNums, Date openQuotTime,String scheme)
    {
        long startTime = System.currentTimeMillis();
        // 更新所有投注 是否中奖
        try
        {
            log.info("开始更新重庆开奖数据>>>>>>>>>>>>>>>" + periodNum + ">>>>>>>>>" + winNums.toString());
            this.updateCQResultLottery(periodNum, winNums, Lists.newArrayList(Constant.CQSSC_LOTTERY_TABLE_LIST),scheme);
            // 更新重庆和数据
            if (CQSSCBallRule.HE(winNums))
            {
                log.info("打和了");
                upateCQHeResult(periodNum, Lists.newArrayList(Constant.CQSSC_TABLE_LIST),scheme);
            }

            scanReplenishDate(periodNum, "CQSSC", winNums,scheme);

            Date now = new Date();
            String todayStr = new SimpleDateFormat("yyyyMMdd").format(now);
            String yesterdayStr = new SimpleDateFormat("yyyyMMdd").format(DateUtils.addDays(now, -1));
            Date restoreUserAvailableCreditTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(todayStr + " 02:30:00");
            Date yesterdayMondayOpenTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(yesterdayStr + " 09:59:00");// 前一天早上第一期
            Date todayMondayOpenTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(todayStr + " 09:59:00");// 当天天早上第一期
            if ((now.before(restoreUserAvailableCreditTime) && openQuotTime.after(yesterdayMondayOpenTime)) || (now.after(restoreUserAvailableCreditTime) && openQuotTime.after(todayMondayOpenTime)))
            {
                // 如果是当天2:30恢复额度前重新对前天一天早上10点后 或者 当天2:30恢复额度后，对当天早上10点后的盘期重新兑奖，需要恢复信用额度
                // 批量更改用户信用额度
                batchUpdateUserAvailableCredit(periodNum, "CQSSC",scheme);
            }

            log.info("开始迁移重庆开奖数据");
            betDao.miragationCQDataToHistory(periodNum, "1",scheme);

            log.info("开始迁移重庆补货数据到历史表");
            betDao.miragationReplenishDataToHistory(periodNum, "CQSSC",scheme);

            log.info("开始校验历史表与投注备份表数据");
            //checkLogic.checkBetWithBackupByPeridosNum(periodNum, Constant.LOTTERY_TYPE_CQSSC,scheme);
            //boolean resultReplenish = checkLogic.checkReplenishWithHistory(periodNum,scheme);// 校验补货

            long endTime = System.currentTimeMillis();
            log.info("重庆" + periodNum + "期兑奖结束 花费时间>>>>>>>>>>>>>>>>>>>>>>>>>>>" + (endTime - startTime) + "MS");
        }
        catch (Exception e)
        {
            log.error("重庆兑奖发生异常" ,e);
            throw new RuntimeException("重庆兑奖发生异常");
        }

    }


    @Override
    public void updateLotteryBJSC(String periodNum, List<Integer> winNums,String scheme)
    {

        long startTime = System.currentTimeMillis();
        // 更新所有投注 是否中奖
        try
 {
			log.info("开始更新北京开奖数据>>>>>>>>>>>>>>>" + periodNum + ">>>>>>>>>" + winNums.toString());
			this.updateBJResultLottery(periodNum, winNums, Lists.newArrayList(Constant.BJSC_TABLE_LIST),scheme);

			scanReplenishDate(periodNum, "BJ", winNums, scheme);

			// 批量更改用户信用额度
			batchUpdateUserAvailableCredit(periodNum, "BJ", scheme);

			//log.info("开始迁移北京开奖数据");
			//betDao.miragationBJDataToHistory(periodNum, "1", scheme);
			//log.info("开始迁移北京补货数据到历史表");
			//betDao.miragationReplenishDataToHistory(periodNum, "BJ", scheme);

			//log.info("开始校验历史表与投注备份表数据");
			//checkLogic.checkBetWithBackupByPeridosNum(periodNum, Constant.LOTTERY_TYPE_BJSC, scheme);
			//boolean resultReplenish = checkLogic.checkReplenishWithHistory(periodNum, scheme);// 校验补货
			long endTime = System.currentTimeMillis();
			log.info("北京" + periodNum + "期兑奖结束 花费时间>>>>>>>>>>>>>>>>>>>>>>>>>>>" + (endTime - startTime) + "MS");
		}
        catch (Exception e)
        {
            
            log.error("北京兑奖发生异常" + e);
            throw new RuntimeException("北京兑奖发生异常");
        }
    }

    @Override
    public void updateBJForReLottery(String periodNum, List<Integer> winNums, Date openQuotTime,String scheme)
    {

        long startTime = System.currentTimeMillis();
        // 更新所有投注 是否中奖
        try
        {
            log.info("开始更新北京开奖数据>>>>>>>>>>>>>>>" + periodNum + ">>>>>>>>>" + winNums.toString());
            this.updateBJResultLottery(periodNum, winNums, Lists.newArrayList(Constant.BJSC_TABLE_LIST),scheme);

            scanReplenishDate(periodNum, "BJ", winNums,scheme);

            final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            String openTimeDate = sf.format(openQuotTime); // 开盘日期
            String today = sf.format(new Date());// 当天日期
            String yesterday = sf.format(DateUtils.addDays(new Date(), -1));// 前一天
            Date now = new Date();
            Date restoreUserAvailableCreditTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(today + " 02:30:00");
            if ((now.before(restoreUserAvailableCreditTime) && yesterday.equals(openTimeDate)) || (now.after(restoreUserAvailableCreditTime) && today.equals(openTimeDate)))
            {
                // 如果是当天恢复额度2:30前操作，前一天的数据需要恢复额度 或者 2:30后操作，当天的盘期重新兑奖，需要恢复信用额度
                // 批量更改用户信用额度
                batchUpdateUserAvailableCredit(periodNum, "BJ",scheme);
            }

            log.info("开始迁移北京开奖数据");
            betDao.miragationBJDataToHistory(periodNum, "1",scheme);

            log.info("开始迁移北京补货数据到历史表");
            betDao.miragationReplenishDataToHistory(periodNum, "BJ",scheme);

            log.info("开始校验历史表与投注备份表数据");
            //checkLogic.checkBetWithBackupByPeridosNum(periodNum, Constant.LOTTERY_TYPE_BJSC,scheme);
            //boolean resultReplenish = checkLogic.checkReplenishWithHistory(periodNum,scheme);// 校验补货
            long endTime = System.currentTimeMillis();
            log.info("北京" + periodNum + "期兑奖结束 花费时间>>>>>>>>>>>>>>>>>>>>>>>>>>>" + (endTime - startTime) + "MS");
        }
        catch (Exception e)
        {
            log.error("北京兑奖发生异常" ,e);
            throw new RuntimeException("北京兑奖发生异常");
        }
    }

    @Override
    public void updateLotteryJSSB(String periodNum, List<Integer> winNums,String scheme)
    {
        long startTime = System.currentTimeMillis();
        // 更新所有投注 是否中奖
        try
        {
            log.info("开始更新江苏开奖数据>>>>>>>>>>>>>>>" + periodNum + ">>>>>>>>>" + winNums.toString());
            this.updateJSResultLottery(periodNum, winNums, Lists.newArrayList(Constant.K3_TABLE_LIST), scheme);

            scanReplenishDate(periodNum, "K3", winNums, scheme);

            // 批量更改用户信用额度
            batchUpdateUserAvailableCredit(periodNum, Constant.LOTTERY_TYPE_K3, scheme);

            //log.info("开始迁移江苏开奖数据");
            //betDao.miragationJSDataToHistory(periodNum, "1",scheme);
            //log.info("开始迁移江苏补货数据到历史表");
            //betDao.miragationReplenishDataToHistory(periodNum, "K3",scheme);

            //log.info("开始校验历史表与投注备份表数据");
            //checkLogic.checkBetWithBackupByPeridosNum(periodNum, Constant.LOTTERY_TYPE_K3,scheme);
            //boolean resultReplenish = checkLogic.checkReplenishWithHistory(periodNum,scheme);// 校验补货

            long endTime = System.currentTimeMillis();
            log.info("江苏" + periodNum + "期兑奖结束 花费时间>>>>>>>>>>>>>>>>>>>>>>>>>>>" + (endTime - startTime) + "MS");
        }
        catch (Exception e)
        {
            log.error("江苏兑奖发生异常",e);
            throw new RuntimeException("江苏兑奖发生异常");
        }
    }


    @Override
    public void updateJSForReLottery(String periodNum, List<Integer> winNums, Date openQuotTime,String scheme)
    {
        long startTime = System.currentTimeMillis();
        // 更新所有投注 是否中奖
        try
        {
            log.info("开始更新江苏开奖数据>>>>>>>>>>>>>>>" + periodNum + ">>>>>>>>>" + winNums.toString());
            this.updateJSResultLottery(periodNum, winNums, Lists.newArrayList(Constant.K3_TABLE_LIST),scheme);

            scanReplenishDate(periodNum, "K3", winNums,scheme);
            final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            String openTimeDate = sf.format(openQuotTime); // 开盘日期
            String today = sf.format(new Date());// 当天日期
            String yesterday = sf.format(DateUtils.addDays(new Date(), -1));// 前一天
            Date now = new Date();
            Date restoreUserAvailableCreditTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(today + " 02:30:00");
            if ((now.before(restoreUserAvailableCreditTime) && yesterday.equals(openTimeDate)) || (now.after(restoreUserAvailableCreditTime) && today.equals(openTimeDate)))
            {
                // 如果是当天恢复额度2:30前操作，前一天的数据需要恢复额度 或者 2:30后操作，当天的盘期重新兑奖，需要恢复信用额度
                // 批量更改用户信用额度
                batchUpdateUserAvailableCredit(periodNum, Constant.LOTTERY_TYPE_K3,scheme);
            }

            log.info("开始迁移江苏开奖数据");
            betDao.miragationJSDataToHistory(periodNum, "1",scheme);

            log.info("开始迁移江苏补货数据到历史表");
            betDao.miragationReplenishDataToHistory(periodNum, "K3",scheme);

            log.info("开始校验历史表与投注备份表数据");
            //checkLogic.checkBetWithBackupByPeridosNum(periodNum, Constant.LOTTERY_TYPE_K3,scheme);
            //boolean resultReplenish = checkLogic.checkReplenishWithHistory(periodNum,scheme);// 校验补货

            long endTime = System.currentTimeMillis();
            log.info("江苏" + periodNum + "期兑奖结束 花费时间>>>>>>>>>>>>>>>>>>>>>>>>>>>" + (endTime - startTime) + "MS");
        }
        catch (Exception e)
        {
            log.error("江苏兑奖发生异常" ,e);
            throw new RuntimeException("江苏兑奖发生异常");
        }
    }


    private void updateGDBallLottery(String periodNum, List<Integer> winNums, List<String> tableList,String scheme)
    {

       
        // 批量更新表中奖数据
        for (int i = 0; i < tableList.size(); i++)
        {
            String tableName = tableList.get(i);
            betDao.batchUpdateBetResult(tableName, periodNum,scheme);
        }

    }


    /**
     * 更新幸运农场兑奖信息
     * 
     * @param periodNum
     * @param winNums
     * @param tableList
     */

    private void updateNCBallLottery(String periodNum, List<Integer> winNums, List<String> tableList,String scheme)
    {
        // 批量更新表中奖数据
        for (int i = 0; i < tableList.size(); i++)
        {
            String tableName = tableList.get(i);
            betDao.batchUpdateBetResultForNC(tableName, periodNum,scheme);
        }

    }


    private void updateCQResultLottery(String periodNum, List<Integer> winNums, List<String> tableList,String scheme)
 {
		// 批量更新投注表数据
		for (int i = 0; i < tableList.size(); i++) {
			String tableName = tableList.get(i);
			betDao.batchUpdateBetResult(tableName, periodNum, scheme);
		}

	}


    private void updateBJResultLottery(String periodNum, List<Integer> winNums, List<String> tableList,String scheme)
    {
        // 批量更新投注表数据
        for (int i = 0; i < tableList.size(); i++)
        {
            String tableName = tableList.get(i);
            betDao.batchUpdateBetResult(tableName, periodNum,scheme);
        }

    }


    /**
     * add by peter for K3
     * 
     * @param periodNum
     * @param winNums
     * @param tableList
     */
    private void updateJSResultLottery(String periodNum, List<Integer> winNums, List<String> tableList,String scheme)
 {

		// 重新更新中奖数据
		List<PlayType> playTypeList = playTypeLogic.findWinInfoPlayType(Constant.LOTTERY_TYPE_K3);
		for (int i = 0; i < playTypeList.size(); i++) {
			PlayType playType = playTypeList.get(i);
			List<Integer> newList = Lists.newArrayList(winNums);
			boolean win = JSSBRule.getBetResult(playType, newList);
			log.info("K3兑奖： 类型=" + playType.getTypeCode() + " || 结果 win=" + win + " || 盘期=" + periodNum);
			if (win) {
				if (playType.getTypeCode().startsWith("K3_SJ")) {
					// 计算三军的赔率
					int plusOdds = JSSBRule.getSJRealOdd(playType, newList);
					betDao.updatePlusOddsForJSSB(playType.getTypeCode(), periodNum, plusOdds, scheme);
				}
			}

		}
		// 批量更新投注表数据
		for (int i = 0; i < tableList.size(); i++) {
			String tableName = tableList.get(i);
			betDao.batchUpdateBetResultForJSSC(tableName, periodNum, scheme);
		}

	}


    /**
     * add by peter for K3 该方法用于历史表重新兑奖
     * 
     * @param periodNum
     * @param winNums
     * @param tableList
     */
/*	private void updateJSResultLotteryForHis(String periodNum, List<Integer> winNums, List<String> tableList,
	        String scheme) {

		// 重新更新中奖数据
		List<PlayType> playTypeList = playTypeLogic.findWinInfoPlayType(Constant.LOTTERY_TYPE_K3);
		for (int i = 0; i < playTypeList.size(); i++) {
			PlayType playType = playTypeList.get(i);
			List<Integer> newList = Lists.newArrayList(winNums);
			boolean win = JSSBRule.getBetResult(playType, newList);
			log.info("K3兑奖： 类型=" + playType.getTypeCode() + " || 结果 win=" + win + " || 盘期=" + periodNum);
			if (win) {
				if (playType.getTypeCode().startsWith("K3_SJ")) {
					// 计算三军的赔率
					int plusOdds = JSSBRule.getSJRealOdd(playType, newList);
					betDao.updatePlusOddsForJSSBHis(playType.getTypeCode(), periodNum, plusOdds, scheme);
				}
			}

		}
		// 批量更新投注表数据
		for (int i = 0; i < tableList.size(); i++) {
			String tableName = tableList.get(i);
			betDao.batchUpdateBetResultForJSSC(tableName, periodNum, scheme);
		}

	}*/


    /*
     * 扫描广东快乐十分第一球 更新用户中奖数据
     */
    private void scanGDKLSFLM(String periodNum, List<Integer> winNums,String scheme)
    {

        String tableName = Constant.GDKLSF_HIS_TABLE_NAME;
        // betDao.updateHistoryBetResultToUnLottery(tableName,periodNum);
        // this.updateGDBallLottery(periodNum, winNums, Lists.newArrayList(tableName));
        if(StringUtils.isNotBlank(scheme)){
        	tableName=scheme+"."+tableName;
        }
        log.info("开始扫描表" + tableName);
        Integer startNum = 0;
        Integer batchSize = 5000;
        while (true)
        {
            Integer endNum = startNum + batchSize;
            List lmBetList = betDao.queryHistoryBetResult(tableName, periodNum, Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME, startNum, endNum);
            if (lmBetList == null || lmBetList.size() == 0)
                break;
            for (int i = 0; i < lmBetList.size(); i++)
            {
                BaseBet bet = (BaseBet) lmBetList.get(i);
                String typeCode = bet.getTypeCode();
                PlayType playType = PlayTypeUtils.getPlayType(typeCode);
                String attr = bet.getSplitAttribute();
                Integer id = bet.getId();
                Integer money = bet.getMoney();
                BigDecimal odds = bet.getOdds();
                double winMoney = money * (odds.floatValue() - 1);
                BigDecimal winb = new BigDecimal(winMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
                int win = GDKLSFRule.straightthrough(attr, playType.getTypeCode(), Lists.newArrayList(winNums));
                if (win > 0)
                {
                    betDao.updateGDHistoryBetResult("1", id.toString(), winb.toString());
                    log.info("玩法" + playType.getSubTypeName() + " " + playType.getFinalTypeName() + " 中奖");
                }

            }
            startNum = startNum + batchSize;
        }

    }

    private void scanReplenishDate(String periodNum, String playType, List<Integer> winNums,String scheme)
    {
        Integer startNum = 0;
        Integer batchSize = 1000;
        log.info("开始扫描表 补货表" + periodNum + ">>>>>>>" + playType);
        while (true)
        {
            Integer endNum = startNum + batchSize;

            List replenishList = betDao.getReplenishDate(periodNum, playType, startNum, endNum,scheme);
            if (replenishList == null || replenishList.size() == 0)
                break;
            for (int i = 0; i < replenishList.size(); i++)
            {
                List<Integer> newList = Lists.newArrayList(winNums);
                BaseBet bet = (BaseBet) replenishList.get(i);
                String typeCode = bet.getTypeCode();
                PlayType play = PlayTypeUtils.getPlayType(typeCode);
                Integer money = bet.getMoney();
                BigDecimal odds = bet.getOdds();
                String attr = bet.getAttribute();

                Integer id = bet.getId();
                double winMoney = money * (odds.floatValue() - 1);
                BigDecimal winb = new BigDecimal(winMoney).setScale(2, BigDecimal.ROUND_HALF_UP);

                if ("CQSSC".equals(playType))
                {
                    if (("CQSSC_DOUBLESIDE_LONG".equals(play.getTypeCode()) || "CQSSC_DOUBLESIDE_HU".equals(play.getTypeCode())) && CQSSCBallRule.HE(newList))
                    {

                        betDao.updateReplenshBetResult("9", id.toString(), money.toString(),scheme);

                    }
                    else
                    {
                        boolean win = CQSSCBallRule.getBallBetResult(play, newList);

                        if (win)
                        {
                            betDao.updateReplenshBetResult("1", id.toString(), winb.toString(),scheme);
                        }

                    }

                }
                else if ("BJ".equals(playType))
                {
                    boolean win = BJSCRule.getBetResult(play, newList);
                    if (win)
                    {
                        betDao.updateReplenshBetResult("1", id.toString(), winb.toString(),scheme);
                    }
                }
                else if ("GDKLSF".equals(playType))
                {

                    boolean win = GDKLSFRule.getBetResult(play, newList);
                    if (("GDKLSF_DOUBLESIDE_ZHDA".equals(play.getTypeCode()) || "GDKLSF_DOUBLESIDE_ZHX".equals(play.getTypeCode())) && GDKLSFRule.HE(newList))
                    {

                        betDao.updateReplenshBetResult("9", id.toString(), money.toString(),scheme);

                    }
                    else if (play.getTypeCode().indexOf("GDKLSF_STRAIGHTTHROUGH") != -1)
                    {

                        int num = GDKLSFRule.straightthrough(attr, typeCode, newList);
                        if (num > 0)
                            betDao.updateReplenshBetResult("1", id.toString(), winb.toString(),scheme);

                    }
                    else if (win)
                    {
                        betDao.updateReplenshBetResult("1", id.toString(), winb.toString(),scheme);

                    }
                    // betDao.updateReplenshNotWinBetResult(periodNum, "GDKLSF");
                }
                // add by peter for K3
                else if (Constant.LOTTERY_TYPE_K3.equals(playType))
                {
                    boolean win = JSSBRule.getBetResult(play, newList);
                    if (win)
                    {
                        int plusOdd = JSSBRule.getSJRealOdd(play, newList);// 计算三军的附加赔率
                        BigDecimal winMoneytmp = plusOdd == 0 ? winb : winb.multiply(new BigDecimal(plusOdd));
                        betDao.updateReplenshBetResult("1", id.toString(), winMoneytmp.toString(),scheme);
                    }

                }
                // add by peter for NC
                else if ("NC".equals(playType))
                {

                    boolean win = NCRule.getBetResult(play, newList);
                    if (("NC_DOUBLESIDE_ZHDA".equals(play.getTypeCode()) || "NC_DOUBLESIDE_ZHX".equals(play.getTypeCode())) && NCRule.HE(newList))
                    {

                        betDao.updateReplenshBetResult("9", id.toString(), money.toString(),scheme);

                    }
                    else if (play.getTypeCode().indexOf("NC_STRAIGHTTHROUGH") != -1)
                    {

                        int num = NCRule.straightthrough(attr, typeCode, newList);
                        if (num > 0)
                            betDao.updateReplenshBetResult("1", id.toString(), winb.toString(),scheme);

                    }
                    else if (win)
                    {
                        betDao.updateReplenshBetResult("1", id.toString(), winb.toString(),scheme);

                    }
                }

            }
            startNum = startNum + batchSize;

        }

        if ("GDKLSF".equals(playType))
        {
            betDao.updateReplenshNotWinBetResult(periodNum, "GDKLSF",scheme);
        }
        else if ("CQSSC".equals(playType))
        {

            betDao.updateReplenshNotWinBetResult(periodNum, "CQSSC",scheme);

        }
        else if ("BJ".equals(playType))
        {
            betDao.updateReplenshNotWinBetResult(periodNum, "BJ",scheme);

        }
        // add by peter for K3
        else if (Constant.LOTTERY_TYPE_K3.equals(playType))
        {
            betDao.updateReplenshNotWinBetResult(periodNum, Constant.LOTTERY_TYPE_K3,scheme);
        }
        // add by peter for NC
        else if (Constant.LOTTERY_TYPE_NC.equals(playType))
        {
            betDao.updateReplenshNotWinBetResult(periodNum, Constant.LOTTERY_TYPE_NC,scheme);
        }
    }


   





    private void updateGDLMResultLottery(String periodNum, List<Integer> winNums,String scheme)
    {

        Integer startNum = 0;
        Integer batchSize = 3000;

        while (true)
        {
            Integer endNum = startNum + batchSize;
            List lmBetList = betDao.queryBetResult(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME, periodNum, startNum, endNum,scheme);

            if (lmBetList == null || lmBetList.size() == 0)
                break;
            for (int i = 0; i < lmBetList.size(); i++)
            {
                BaseBet bet = (BaseBet) lmBetList.get(i);
                String typeCode = bet.getTypeCode();
                String attr = bet.getSplitAttribute();
                // String orderNo=bet.getOrderNo();
                Integer id = bet.getId();
                Integer money = bet.getMoney();
                BigDecimal odds = bet.getOdds();
                double winMoney = money * (odds.floatValue() - 1);
                BigDecimal winb = new BigDecimal(winMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
                List<Integer> newList = Lists.newArrayList(winNums);
                int num = GDKLSFRule.straightthrough(attr, typeCode, newList);
                if (num > 0){
                    betDao.updateFSBetResult(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME, id, "1", winb.toString(), Integer.valueOf(num).toString(),scheme);
                }

            }
            startNum = startNum + batchSize;
        }
        betDao.updateNotWinBetResult(Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME, periodNum,scheme);

    }


    private void updateNCLMResultLottery(String periodNum, List<Integer> winNums, String tableName,String scheme)
    {

        Integer startNum = 0;
        Integer batchSize = 3000;

        while (true)
        {
            Integer endNum = startNum + batchSize;
            List lmBetList = betDao.queryBetResultForNC(tableName, periodNum, startNum, endNum,scheme);

            if (lmBetList == null || lmBetList.size() == 0)
                break;
            for (int i = 0; i < lmBetList.size(); i++)
            {
                BaseBet bet = (BaseBet) lmBetList.get(i);
                String typeCode = bet.getTypeCode();
                String attr = bet.getSplitAttribute();
                Integer id = bet.getId();
                Integer money = bet.getMoney();
                BigDecimal odds = bet.getOdds();
                double winMoney = money * (odds.floatValue() - 1);
                BigDecimal winb = new BigDecimal(winMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
                List<Integer> newList = Lists.newArrayList(winNums);
                int num = NCRule.straightthrough(attr, typeCode, newList);
                if (num > 0)
                {
                    betDao.updateFSBetResult(tableName, id, "1", winb.toString(), Integer.valueOf(num).toString(),scheme);
                }
            }
            startNum = startNum + batchSize;
        }
        betDao.updateNotWinBetResultForNC(tableName, periodNum,scheme);
    }

    private void upateCQHeResult(String periodsNum, List<String> tableNames,String scheme)
    {
        log.info("更新重庆打和数据");
        betDao.updateCQHeResult(periodsNum, tableNames,scheme);
    }


    private void upateGDHeResult(String periodsNum, List<String> tableNames,String scheme)
    {
        log.info("更新广东打和数据");
        betDao.updateGDHeResult(periodsNum, tableNames,scheme);
    }


    private void upateNCHeResult(String periodsNum, List<String> tableNames,String scheme)
    {
        log.info("更新农场打和数据");
        betDao.updateNCHeResult(periodsNum, tableNames,scheme);
    }


    private void batchUpdateUserAvailableCredit(String periodsNum, String playType,String scheme)
    {

        List<String> tableList = new ArrayList<String>();
        //

        if ("GDKLSF".equals(playType))
        {
            tableList = Lists.newArrayList(Constant.GDKLSF_TABLE_LIST);
        }
        else if ("CQSSC".equals(playType))
        {
            tableList = Lists.newArrayList(Constant.CQSSC_TABLE_LIST);
        }
        else if ("HKLHC".equals(playType))
        {
            tableList = Lists.newArrayList(Constant.HK_TABLE_LIST);
        }
        else if ("BJ".equals(playType))
        {
            tableList = Lists.newArrayList(Constant.BJSC_TABLE_LIST);
        }
        // add by peter for K3
        else if (Constant.LOTTERY_TYPE_K3.equals(playType))
        {
            tableList = Lists.newArrayList(Constant.K3_TABLE_LIST);
        }
        // add by peter for 农场
        else if (Constant.LOTTERY_TYPE_NC.equals(playType))
        {
            tableList = Lists.newArrayList(Constant.NC_TABLE_LIST);
        }
        for (int i = 0; i < tableList.size(); i++)
        {
            String tableName = tableList.get(i);
            betDao.batchUpdateUserAvailableCredit(periodsNum, tableName,scheme);
        }
    }




  




    @Override
    public void updateSecondLotteryGD(String periodNum, List<Integer> winNums, Date openQuotTime,String scheme) throws Exception
    {
        String tableName = Constant.GDKLSF_HIS_TABLE_NAME;
        final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String openTimeDate = sf.format(openQuotTime); // 开盘日期
        String today = sf.format(new Date());// 当天日期
        String yesterday = sf.format(DateUtils.addDays(new Date(), -1));// 前一天
        Date now = new Date();
        Date restoreUserAvailableCreditTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(today + " 02:30:00");
        if ((now.before(restoreUserAvailableCreditTime) && yesterday.equals(openTimeDate)) || (now.after(restoreUserAvailableCreditTime) && today.equals(openTimeDate)))
        {
            // 如果是当天恢复额度2:30前操作，前一天的数据需要恢复额度 或者 2:30后操作，当天的盘期重新兑奖，需要恢复信用额度
            betDao.batchRestoreUserAvailableCredit(periodNum, tableName,scheme);
        }
        log.info("廣東重新兌獎 更新歷史表投注狀態為0未兌獎");
        betDao.updateHistoryBetResultToUnLottery(tableName, periodNum,scheme);
        betDao.updateHistoryBetResultToUnLottery(Constant.REPLENISH_TABLE_NAME_HIS, periodNum,scheme);
        log.info("廣東重新兌獎  開始更新歷史表一次性兌獎數據");
        this.updateGDBallLottery(periodNum, winNums, Lists.newArrayList(tableName),scheme);
        log.info("廣東重新兌獎  開始 掃描其他類型投注");

        if (GDKLSFRule.HE(winNums))
        {
            upateGDHeResult(periodNum, Lists.newArrayList(tableName),scheme);
        }

        scanGDKLSFLM(periodNum, winNums,scheme);
        betDao.updateNotWinBetResult(Constant.GDKLSF_HIS_TABLE_NAME, periodNum,scheme);
        log.info("结束扫描表" + tableName);
        log.info("廣東重新兌獎開始 掃描補貨投注");
        scanReplenishDataHis(periodNum, "GDKLSF", winNums,scheme);
        if ((now.before(restoreUserAvailableCreditTime) && yesterday.equals(openTimeDate)) || (now.after(restoreUserAvailableCreditTime) && today.equals(openTimeDate)))
        {
            // 如果是当天恢复额度2:30前操作，前一天的数据需要恢复额度 或者 2:30后操作，当天的盘期重新兑奖，需要恢复信用额度
            betDao.batchUpdateUserAvailableCredit(periodNum, tableName,scheme);
        }
    }


    @Override
    public void updateSecondLotteryBJ(String periodNum, List<Integer> winNums, Date openQuotTime,String scheme) throws Exception
    {

        String tableName = Constant.BJSC_HIS_TABLE_NAME;
        final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String openTimeDate = sf.format(openQuotTime); // 开盘日期
        String today = sf.format(new Date());// 当天日期
        String yesterday = sf.format(DateUtils.addDays(new Date(), -1));// 前一天
        Date now = new Date();
        Date restoreUserAvailableCreditTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(today + " 02:30:00");
        if ((now.before(restoreUserAvailableCreditTime) && yesterday.equals(openTimeDate)) || (now.after(restoreUserAvailableCreditTime) && today.equals(openTimeDate)))
        {
            // 如果是当天恢复额度2:30前操作，前一天的数据需要恢复额度 或者 2:30后操作，当天的盘期重新兑奖，需要恢复信用额度
            betDao.batchRestoreUserAvailableCredit(periodNum, tableName,scheme);
        }
        log.info("北京重新兌獎 更新歷史表投注狀態為0未兌獎");
        betDao.updateHistoryBetResultToUnLottery(tableName, periodNum,scheme);
        betDao.updateHistoryBetResultToUnLottery(Constant.REPLENISH_TABLE_NAME_HIS, periodNum,scheme);
        log.info("北京重新兌獎 開始更新歷史表一次性兌獎數據");

        this.updateBJResultLottery(periodNum, winNums, Lists.newArrayList(tableName),scheme);
        log.info("北京重新兌獎 開始 掃描其他類型投注");
        log.info("北京重新兌獎 開始 掃描補貨投注");
        scanReplenishDataHis(periodNum, "BJ", winNums,scheme);
        if ((now.before(restoreUserAvailableCreditTime) && yesterday.equals(openTimeDate)) || (now.after(restoreUserAvailableCreditTime) && today.equals(openTimeDate)))
        {
            // 如果是当天恢复额度2:30前操作，前一天的数据需要恢复额度 或者 2:30后操作，当天的盘期重新兑奖，需要恢复信用额度
            betDao.batchUpdateUserAvailableCredit(periodNum, tableName,scheme);
        }
    }

    @Override
    public void updateSecondLotteryCQ(String periodNum, List<Integer> winNums, Date openQuotTime,String scheme) throws Exception
    {

        String tableName = Constant.CQSSC_HIS_TABLE_NAME;
        Date now = new Date();
        String todayStr = new SimpleDateFormat("yyyyMMdd").format(now);
        String yesterdayStr = new SimpleDateFormat("yyyyMMdd").format(DateUtils.addDays(now, -1));
        Date restoreUserAvailableCreditTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(todayStr + " 02:30:00");
        Date yesterdayMondayOpenTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(yesterdayStr + " 09:59:00");// 前一天早上第一期
        Date todayMondayOpenTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(todayStr + " 09:59:00");// 当天天早上第一期
        if ((now.before(restoreUserAvailableCreditTime) && openQuotTime.after(yesterdayMondayOpenTime)) || (now.after(restoreUserAvailableCreditTime) && openQuotTime.after(todayMondayOpenTime)))
        {
            // 如果是当天2:30恢复额度前重新对前天一天早上10点后 或者 当天2:30恢复额度后，对当天早上10点后的盘期重新兑奖，需要恢复信用额度
            betDao.batchRestoreUserAvailableCredit(periodNum, tableName,scheme);
        }
        log.info("重慶重新兌獎 更新歷史表投注狀態為0未兌獎");
        betDao.updateHistoryBetResultToUnLottery(tableName, periodNum,scheme);
        betDao.updateHistoryBetResultToUnLottery(Constant.REPLENISH_TABLE_NAME_HIS, periodNum,scheme);
        log.info("重慶重新兌獎 開始更新歷史表一次性兌獎數據");
        this.updateCQResultLottery(periodNum, winNums, Lists.newArrayList(tableName),scheme);
        log.info("重慶重新兌獎 開始 掃描其他類型投注");
        // 更新重庆和数据
        if (CQSSCBallRule.HE(winNums))
        {
            log.info("打和了");
            upateCQHeResult(periodNum, Lists.newArrayList(tableName),scheme);
        }
        log.info("重慶重新兌獎 開始 掃描補貨投注");
        scanReplenishDataHis(periodNum, "CQSSC", winNums,scheme);
        if ((now.before(restoreUserAvailableCreditTime) && openQuotTime.after(yesterdayMondayOpenTime)) || (now.after(restoreUserAvailableCreditTime) && openQuotTime.after(todayMondayOpenTime)))
        {
            // 如果是当天2:30恢复额度前重新对前天一天早上10点后 或者 当天2:30恢复额度后，对当天早上10点后的盘期重新兑奖，需要恢复信用额度
            betDao.batchUpdateUserAvailableCredit(periodNum, tableName,scheme);
        }

    }


    @Override
    public void updateSecondLotteryJS(String periodNum, List<Integer> winNums, Date openQuotTime,String scheme) throws Exception
    {

        String tableName = Constant.K3_HIS_TABLE_NAME;
        final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String openTimeDate = sf.format(openQuotTime); // 开盘日期
        String today = sf.format(new Date());// 当天日期
        String yesterday = sf.format(DateUtils.addDays(new Date(), -1));// 前一天
        Date now = new Date();
        Date restoreUserAvailableCreditTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(today + " 02:30:00");
        if ((now.before(restoreUserAvailableCreditTime) && yesterday.equals(openTimeDate)) || (now.after(restoreUserAvailableCreditTime) && today.equals(openTimeDate)))
        {
            // 如果是当天恢复额度2:30前操作，前一天的数据需要恢复额度 或者 2:30后操作，当天的盘期重新兑奖，需要恢复信用额度
            betDao.batchRestoreUserAvailableCredit(periodNum, tableName,scheme);
        }
        log.info("江苏重新兌獎 更新歷史表投注狀態為0未兌獎");
        betDao.updateHistoryBetResultToUnLottery(tableName, periodNum,scheme);
        betDao.updateHistoryBetResultToUnLottery(Constant.REPLENISH_TABLE_NAME_HIS, periodNum,scheme);
        log.info("江苏重新兌獎 開始更新歷史表一次性兌獎數據");

        this.updateJSResultLottery(periodNum, winNums, Lists.newArrayList(tableName),scheme);
        log.info("江苏重新兌獎 開始 掃描其他類型投注");
        log.info("江苏重新兌獎 開始 掃描補貨投注");
        scanReplenishDataHis(periodNum, Constant.LOTTERY_TYPE_K3, winNums,scheme);
        if ((now.before(restoreUserAvailableCreditTime) && yesterday.equals(openTimeDate)) || (now.after(restoreUserAvailableCreditTime) && today.equals(openTimeDate)))
        {
            betDao.batchUpdateUserAvailableCredit(periodNum, tableName,scheme);
        }
    }


    @Override
    public void updateSecondLotteryNC(String periodNum, List<Integer> winNums, Date openQuotTime,String scheme) throws Exception
    {

        String tableName = Constant.NC_HIS_TABLE_NAME;
        Date now = new Date();
        String todayStr = new SimpleDateFormat("yyyyMMdd").format(now);
        String yesterdayStr = new SimpleDateFormat("yyyyMMdd").format(DateUtils.addDays(now, -1));
        Date restoreUserAvailableCreditTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(todayStr + " 02:30:00");
        Date yesterdayMondayOpenTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(yesterdayStr + " 09:50:00");// 前一天早上第一期
        Date todayMondayOpenTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(todayStr + " 09:50:00");// 当天天早上第一期
        if ((now.before(restoreUserAvailableCreditTime) && openQuotTime.after(yesterdayMondayOpenTime)) || (now.after(restoreUserAvailableCreditTime) && openQuotTime.after(todayMondayOpenTime)))
        {
            // 如果是当天2:30恢复额度前重新对前天一天早上10点后 或者 当天2:30恢复额度后，对当天早上10点后的盘期重新兑奖，需要恢复信用额度
            betDao.batchRestoreUserAvailableCredit(periodNum, tableName,scheme);
        }
        log.info("幸运农场 重新兌獎 更新歷史表投注狀態為0未兌獎");
        betDao.updateHistoryBetResultToUnLottery(tableName, periodNum,scheme);
        betDao.updateHistoryBetResultToUnLottery(Constant.REPLENISH_TABLE_NAME_HIS, periodNum,scheme);
        log.info("幸运农场 重新兌獎 開始更新歷史表一次性兌獎數據");

        this.updateNCBallLottery(periodNum, winNums, Lists.newArrayList(tableName),scheme);

        log.info("幸运农场重新兑奖 连码开奖数据");
        updateNCLMResultLottery(periodNum, winNums, tableName,scheme);
        log.info("幸运农场 重新兌獎 開始 掃描其他類型投注");
        if (NCRule.HE(winNums))
        {
            upateNCHeResult(periodNum, Lists.newArrayList(tableName),scheme);
        }

        log.info("幸运农场 重新兌獎 開始 掃描補貨投注");
        scanReplenishDataHis(periodNum, "NC", winNums,scheme);
        if ((now.before(restoreUserAvailableCreditTime) && openQuotTime.after(yesterdayMondayOpenTime)) || (now.after(restoreUserAvailableCreditTime) && openQuotTime.after(todayMondayOpenTime)))
        {
            // 如果是当天2:30恢复额度前重新对前天一天早上9点50分后 或者 当天2:30恢复额度后，对当天早上9点50分后的盘期重新兑奖，需要恢复信用额度
            betDao.batchUpdateUserAvailableCredit(periodNum, tableName,scheme);
        }
    }


   


    private static BigDecimal getGGRealOdds(String selectedOdds, List<Integer> winNum)
    {
        BigDecimal odds = new BigDecimal(1);
        String[] ggOdds = StringUtils.split(selectedOdds, "|");
        for (int i = 0; i < ggOdds.length; i++)
        {
            String guan = ggOdds[i];
            String[] guanOdd = StringUtils.split(guan, "&");
            String maNum = guanOdd[0].substring(2, 3);
            String guanDetail = guanOdd[0].substring(4, guanOdd[0].length());
            Integer num = winNum.get(Integer.valueOf(maNum) - 1);

            if ("DAN".equals(guanDetail))
            {
                if (HKLHCRule.HE(num))
                {
                    odds = odds.multiply(new BigDecimal(1));
                }
                else
                    odds = odds.multiply(new BigDecimal(guanOdd[1]));
            }
            else if ("S".equals(guanDetail))
            {
                if (HKLHCRule.HE(num))
                {
                    odds = odds.multiply(new BigDecimal(1));
                }
                else
                    odds = odds.multiply(new BigDecimal(guanOdd[1]));
            }
            else if ("DA".equals(guanDetail))
            {
                if (HKLHCRule.HE(num))
                {
                    odds = odds.multiply(new BigDecimal(1));
                }
                else
                    odds = odds.multiply(new BigDecimal(guanOdd[1]));
            }
            else if ("X".equals(guanDetail))
            {
                if (HKLHCRule.HE(num))
                {
                    odds = odds.multiply(new BigDecimal(1));
                }
                else
                    odds = odds.multiply(new BigDecimal(guanOdd[1]));
            }
            else
                odds = odds.multiply(new BigDecimal(guanOdd[1]));

        }
        return odds;

    }


   

    @Override
    public boolean saveReportPeriod(String periodsNum, String lotteryType,String schema)
    {
        // 计算报表start
        // 先把要该日期的报表统计数据删除
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
        	List<Criterion> filtersPeriodInfoP = new ArrayList<Criterion>();
    		filtersPeriodInfoP.add(Restrictions.ge("periodsNum",periodsNum));
            String culDate = "";
            if(Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)){
            	GDPeriodsInfo periodsInfo = periodsInfoLogic.queryByPeriods(filtersPeriodInfoP.toArray(new Criterion[filtersPeriodInfoP.size()]));
            	java.util.Date openQuotTime = periodsInfo.getOpenQuotTime();
            	culDate = sdf.format(openQuotTime);
            	
            }else if(Constant.LOTTERY_TYPE_CQSSC.equals(lotteryType)){//cq : 1-24
            	CQPeriodsInfo periodsInfo = icqPeriodsInfoLogic.queryByPeriods(filtersPeriodInfoP.toArray(new Criterion[filtersPeriodInfoP.size()]));
            	java.util.Date openQuotTime = periodsInfo.getOpenQuotTime();
            	//盘期格式：20150920024,凌晨0点至2点生成的盘期是1至24期，这段时间算当天的，比如20150920024算是2015-09-19
        		Calendar date = Calendar.getInstance();
        		date.setTime(openQuotTime);
        		date.set(Calendar.HOUR, date.get(Calendar.HOUR) - 3);
        		culDate = sdf.format(date.getTime());
            	
            }else if(Constant.LOTTERY_TYPE_BJ.equals(lotteryType)){
            	BJSCPeriodsInfo periodsInfo = bjscPeriodsInfoLogic.queryByPeriods(filtersPeriodInfoP.toArray(new Criterion[filtersPeriodInfoP.size()]));
            	java.util.Date openQuotTime = periodsInfo.getOpenQuotTime();
            	culDate = sdf.format(openQuotTime);
            	
            }else if(Constant.LOTTERY_TYPE_K3.equals(lotteryType)){
            	JSSBPeriodsInfo periodsInfo = jssbPeriodsInfoLogic.queryByPeriods(filtersPeriodInfoP.toArray(new Criterion[filtersPeriodInfoP.size()]));
            	java.util.Date openQuotTime = periodsInfo.getOpenQuotTime();
            	culDate = sdf.format(openQuotTime);
            	
            }else if(Constant.LOTTERY_TYPE_NC.equals(lotteryType)){//nc : 1-13
            	NCPeriodsInfo periodsInfo = ncPeriodsInfoLogic.queryByPeriods(filtersPeriodInfoP.toArray(new Criterion[filtersPeriodInfoP.size()]));
            	java.util.Date openQuotTime = periodsInfo.getOpenQuotTime();
            	//盘期格式：2015092013,凌晨0点至2点生成的盘期是1至13期，这段时间算当天的，比如2015092013算是2015-09-19
        		Calendar date = Calendar.getInstance();
        		date.setTime(openQuotTime);
        		date.set(Calendar.HOUR, date.get(Calendar.HOUR) - 3);
        		culDate = sdf.format(date.getTime());
            	
            }
    		
            settledReportEricLogic.saveReportPeriod(periodsNum, lotteryType, schema, culDate);
            
            return true;
        }
        catch (ParseException e)
        {
            log.info("生成统计报表数据 异常，提示错误：" + e.getMessage());
            return false;
        }
        // 计算报表end
    }
    
    private void scanReplenishDataHis(String periodNum, String playType, List<Integer> winNums,String scheme)
    {
        Integer startNum = 0;
        Integer batchSize = 1000;
        log.info("开始扫描表 补货表" + periodNum + ">>>>>>>" + playType);
        while (true)
        {
            Integer endNum = startNum + batchSize;

            List replenishList = betDao.getReplenishDataHis(periodNum, playType, startNum, endNum,scheme);
            if (replenishList == null || replenishList.size() == 0)
                break;
            for (int i = 0; i < replenishList.size(); i++)
            {
                List<Integer> newList = Lists.newArrayList(winNums);
                BaseBet bet = (BaseBet) replenishList.get(i);
                String typeCode = bet.getTypeCode();
                PlayType play = PlayTypeUtils.getPlayType(typeCode);
                Integer money = bet.getMoney();
                BigDecimal odds = bet.getOdds();
                String attr = bet.getAttribute();

                Integer id = bet.getId();
                double winMoney = money * (odds.floatValue() - 1);
                BigDecimal winb = new BigDecimal(winMoney).setScale(2, BigDecimal.ROUND_HALF_UP);

                if ("CQSSC".equals(playType))
                {
                    if (("CQSSC_DOUBLESIDE_LONG".equals(play.getTypeCode()) || "CQSSC_DOUBLESIDE_HU".equals(play.getTypeCode())) && CQSSCBallRule.HE(newList))
                    {

                        betDao.updateReplenshBetResultHis("9", id.toString(), money.toString(),scheme);

                    }
                    else
                    {
                        boolean win = CQSSCBallRule.getBallBetResult(play, newList);

                        if (win)
                        {
                            betDao.updateReplenshBetResultHis("1", id.toString(), winb.toString(),scheme);
                        }

                    }
                }
                else if ("BJ".equals(playType))
                {
                    boolean win = BJSCRule.getBetResult(play, newList);
                    if (win)
                    {
                        betDao.updateReplenshBetResultHis("1", id.toString(), winb.toString(),scheme);
                    }
                }
                else if ("GDKLSF".equals(playType))
                {

                    boolean win = GDKLSFRule.getBetResult(play, newList);
                    if (("GDKLSF_DOUBLESIDE_ZHDA".equals(play.getTypeCode()) || "GDKLSF_DOUBLESIDE_ZHX".equals(play.getTypeCode())) && GDKLSFRule.HE(newList))
                    {

                        betDao.updateReplenshBetResultHis("9", id.toString(), money.toString(),scheme);

                    }
                    else if (play.getTypeCode().indexOf("GDKLSF_STRAIGHTTHROUGH") != -1)
                    {

                        int num = GDKLSFRule.straightthrough(attr, typeCode, newList);
                        if (num > 0)
                            betDao.updateReplenshBetResultHis("1", id.toString(), winb.toString(),scheme);

                    }
                    else if (win)
                    {
                        betDao.updateReplenshBetResultHis("1", id.toString(), winb.toString(),scheme);

                    }
                    // betDao.updateReplenshNotWinBetResult(periodNum, "GDKLSF");
                }
                // add by peter for K3
                else if (Constant.LOTTERY_TYPE_K3.equals(playType))
                {
                    boolean win = JSSBRule.getBetResult(play, newList);
                    if (win)
                    {
                        int plusOdd = JSSBRule.getSJRealOdd(play, newList);// 计算三军的附加赔率
                        BigDecimal winMoneytmp = plusOdd == 0 ? winb : winb.multiply(new BigDecimal(plusOdd));
                        betDao.updateReplenshBetResultHis("1", id.toString(), winMoneytmp.toString(),scheme);
                    }

                }
                else if ("NC".equals(playType))
                {

                    boolean win = NCRule.getBetResult(play, newList);
                    if (("NC_DOUBLESIDE_ZHDA".equals(play.getTypeCode()) || "NC_DOUBLESIDE_ZHX".equals(play.getTypeCode())) && NCRule.HE(newList))
                    {

                        betDao.updateReplenshBetResultHis("9", id.toString(), money.toString(),scheme);

                    }
                    else if (play.getTypeCode().indexOf("NC_STRAIGHTTHROUGH") != -1)
                    {

                        int num = NCRule.straightthrough(attr, typeCode, newList);
                        if (num > 0)
                            betDao.updateReplenshBetResultHis("1", id.toString(), winb.toString(),scheme);

                    }
                    else if (win)
                    {
                        betDao.updateReplenshBetResultHis("1", id.toString(), winb.toString(),scheme);

                    }
                }

            }
            startNum = startNum + batchSize;
        }

        if ("GDKLSF".equals(playType))
        {
            betDao.updateReplenshNotWinBetResultHis(periodNum, "GDKLSF",scheme);
        }
        else if ("CQSSC".equals(playType))
        {

            betDao.updateReplenshNotWinBetResultHis(periodNum, "CQSSC",scheme);

        }
        else if ("BJ".equals(playType))
        {
            betDao.updateReplenshNotWinBetResultHis(periodNum, "BJ",scheme);

        }
        // add by peter for K3
        else if (Constant.LOTTERY_TYPE_K3.equals(playType))
        {
            betDao.updateReplenshNotWinBetResultHis(periodNum, Constant.LOTTERY_TYPE_K3,scheme);
        }
        // add by peter for K3
        else if (Constant.LOTTERY_TYPE_NC.equals(playType))
        {
            betDao.updateReplenshNotWinBetResultHis(periodNum, Constant.LOTTERY_TYPE_NC,scheme);
        }

    }


    public INCPeriodsInfoLogic getNcPeriodsInfoLogic()
    {
        return ncPeriodsInfoLogic;
    }


    public void setNcPeriodsInfoLogic(INCPeriodsInfoLogic ncPeriodsInfoLogic)
    {
        this.ncPeriodsInfoLogic = ncPeriodsInfoLogic;
    }


    public ICheckLogic getCheckLogic()
    {
        return checkLogic;
    }


    public void setCheckLogic(ICheckLogic checkLogic)
    {
        this.checkLogic = checkLogic;
    }
    
    
    /**
     * 更新临时兑奖结果表的方法,目前是每期更新固定的数据,后续优化通过插入每期数据,而非更新覆盖
     * @param periodNum
     * @param winNums
     * @param playType
     */
    @Override
	public void updatePlayTypeWin(String periodNum, List<Integer> winNums, String playType) {
		if (Constant.LOTTERY_TYPE_BJSC.equals(playType)) {
			this.updateBjPlayTypeWin(periodNum, winNums);
		} else if (Constant.LOTTERY_TYPE_CQSSC.equals(playType)) {
			this.updateCQPlayTypeWin(periodNum, winNums);
		} else if (Constant.LOTTERY_TYPE_GDKLSF.equals(playType)) {
			this.updateGDPlayTypeWin(periodNum, winNums);
		} else if (Constant.LOTTERY_TYPE_K3.equals(playType)) {
			this.updateJSPlayTypeWin(periodNum, winNums);
		} else if (Constant.LOTTERY_TYPE_NC.equals(playType)) {
			this.updateNCPlayTypeWin(periodNum, winNums);
		}
	}

	private PlayWinInfo initPlayWinInfo(boolean win, PlayType playType) {
		PlayWinInfo playWinInfo = new PlayWinInfo();
		if (win) {
			playWinInfo.setPlayType(playType.getPlayType());
			playWinInfo.setTypeCode(playType.getTypeCode());
			playWinInfo.setWin("1");

		} else {
			playWinInfo.setPlayType(playType.getPlayType());
			playWinInfo.setTypeCode(playType.getTypeCode());
			playWinInfo.setWin("0");
		}
		return playWinInfo;
	}

	private void updateBjPlayTypeWin(String periodNum, List<Integer> winNums) {
		// /先根据盘期和类型删除兑奖记录
		betDao.deletePlayTypeWinInfoByPeriod(periodNum, "BJ");
		// 重新更新中奖数据
		List<PlayType> playTypeList = playTypeLogic.findWinInfoPlayType("BJ");
		List<PlayWinInfo> playWinInfoList = new ArrayList<PlayWinInfo>();
		for (int i = 0; i < playTypeList.size(); i++) {
			PlayType playType = playTypeList.get(i);
			List<Integer> newList = Lists.newArrayList(winNums);
			boolean win = BJSCRule.getBetResult(playType, newList);
			PlayWinInfo playWinInfo = this.initPlayWinInfo(win, playType);
			playWinInfoList.add(playWinInfo);

		}
		betDao.batchInsertPlayTypeToWin(playWinInfoList, periodNum);
	}

	private void updateCQPlayTypeWin(String periodNum, List<Integer> winNums) {
		// 先根据盘期和类型删除兑奖记录
		betDao.deletePlayTypeWinInfoByPeriod(periodNum, "CQSSC");
		// 重新更新中奖数据
		List<PlayType> playTypeList = playTypeLogic.findWinInfoPlayType("CQSSC");
		List<PlayWinInfo> playWinInfoList = new ArrayList<PlayWinInfo>();
		for (int i = 0; i < playTypeList.size(); i++) {
			PlayType playType = playTypeList.get(i);
			List<Integer> newList = Lists.newArrayList(winNums);
			boolean win = CQSSCBallRule.getBallBetResult(playType, newList);
			PlayWinInfo playWinInfo = this.initPlayWinInfo(win, playType);
			playWinInfoList.add(playWinInfo);
		}
		betDao.batchInsertPlayTypeToWin(playWinInfoList, periodNum);
	}

	private void updateGDPlayTypeWin(String periodNum, List<Integer> winNums) {
		// 先根据盘期和类型删除兑奖记录
		betDao.deletePlayTypeWinInfoByPeriod(periodNum, "GDKLSF");

		// 更新中奖数据
		List<PlayType> playTypeList = playTypeLogic.findWinInfoPlayType("GDKLSF");
		List<PlayWinInfo> playWinInfoList = new ArrayList<PlayWinInfo>();
		for (int i = 0; i < playTypeList.size(); i++) {
			PlayType playType = playTypeList.get(i);
			List<Integer> newList = Lists.newArrayList(winNums);
			boolean win = GDKLSFRule.getBetResult(playType, newList);
			PlayWinInfo playWinInfo = this.initPlayWinInfo(win, playType);
			playWinInfoList.add(playWinInfo);

		}
		betDao.batchInsertPlayTypeToWin(playWinInfoList, periodNum);
	}

	private void updateJSPlayTypeWin(String periodNum, List<Integer> winNums) {
		// /先根据盘期和类型删除兑奖记录
		betDao.deletePlayTypeWinInfoByPeriod(periodNum, Constant.LOTTERY_TYPE_K3);

		// 重新更新中奖数据
		List<PlayType> playTypeList = playTypeLogic.findWinInfoPlayType(Constant.LOTTERY_TYPE_K3);
		List<PlayWinInfo> playWinInfoList = new ArrayList<PlayWinInfo>();
		for (int i = 0; i < playTypeList.size(); i++) {
			PlayType playType = playTypeList.get(i);
			List<Integer> newList = Lists.newArrayList(winNums);
			boolean win = JSSBRule.getBetResult(playType, newList);
			log.info("K3兑奖： 类型=" + playType.getTypeCode() + " || 结果 win=" + win + " || 盘期=" + periodNum);
			PlayWinInfo playWinInfo = this.initPlayWinInfo(win, playType);
			playWinInfoList.add(playWinInfo);

		}
		betDao.batchInsertPlayTypeToWin(playWinInfoList, periodNum);
	}

	private void updateNCPlayTypeWin(String periodNum, List<Integer> winNums) {
		// 先根据盘期和类型删除兑奖记录
		betDao.deletePlayTypeWinInfoByPeriod(periodNum, "NC");

		// 更新中奖数据
		List<PlayType> playTypeList = playTypeLogic.findWinInfoPlayType("NC");
		List<PlayWinInfo> playWinInfoList = new ArrayList<PlayWinInfo>();
		for (int i = 0; i < playTypeList.size(); i++) {
			PlayType playType = playTypeList.get(i);
			List<Integer> newList = Lists.newArrayList(winNums);
			boolean win = NCRule.getBetResult(playType, newList);
			PlayWinInfo playWinInfo = this.initPlayWinInfo(win, playType);
			playWinInfoList.add(playWinInfo);
		}
		betDao.batchInsertPlayTypeToWin(playWinInfoList, periodNum);
	}
    @Override
	public void miragationLotDataToHistory(String periodNum, String playType, String scheme) {
		if (Constant.LOTTERY_TYPE_CQSSC.equals(playType)) {
			log.info("开始迁移重庆开奖数据");
			betDao.miragationCQDataToHistory(periodNum, "1", scheme);

			log.info("开始迁移重庆补货数据到历史表");
			betDao.miragationReplenishDataToHistory(periodNum, "CQSSC", scheme);
		} else if (Constant.LOTTERY_TYPE_NC.equals(playType)) {
			log.info("开始迁移农场数据到历史表");
			betDao.miragationNCDataToHistory(periodNum, "1", scheme);
			log.info("开始迁移农场补货数据到历史表");
			betDao.miragationReplenishDataToHistory(periodNum, "NC", scheme);
		}else if(Constant.LOTTERY_TYPE_GDKLSF.equals(playType)){
			   log.info("开始迁移广东数据到历史表");
	            betDao.miragationGDDataToHistory(periodNum, "1",scheme);
	            log.info("开始迁移广东补货数据到历史表");
	            betDao.miragationReplenishDataToHistory(periodNum, "GD",scheme);
		}else if(Constant.LOTTERY_TYPE_BJ.equals(playType)){
			log.info("开始迁移北京开奖数据");
			betDao.miragationBJDataToHistory(periodNum, "1", scheme);
			log.info("开始迁移北京补货数据到历史表");
			betDao.miragationReplenishDataToHistory(periodNum, "BJ", scheme);
		}else if(Constant.LOTTERY_TYPE_K3.equals(playType)){
			log.info("开始迁移江苏开奖数据");
            betDao.miragationJSDataToHistory(periodNum, "1",scheme);
            log.info("开始迁移江苏补货数据到历史表");
            betDao.miragationReplenishDataToHistory(periodNum, "K3",scheme);
		}

	}
}
