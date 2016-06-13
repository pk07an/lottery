/**
 * 
 */
package com.npc.lottery.member.logic.spring;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.npc.lottery.common.Constant;
import com.npc.lottery.member.dao.interf.ICheckDao;
import com.npc.lottery.member.entity.BetCheckVo;
import com.npc.lottery.member.logic.interf.ICheckLogic;
import com.npc.lottery.replenish.dao.interf.IReplenishCheckDao;
import com.npc.lottery.replenish.dao.interf.IReplenishHisDao;
import com.npc.lottery.replenish.entity.ReplenishCheck;
import com.npc.lottery.replenish.entity.ReplenishHis;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.util.SendMail;

/**
 * @author peteran
 * 
 */
public class CheckLogic implements ICheckLogic {
	private ICheckDao	checkDao;
	private IReplenishHisDao replenishHisDao;
	private IReplenishCheckDao replenishCheckDao;
	private IReplenishLogic replenishLogic;
	
	private static final Logger					logger	= Logger.getLogger(CheckLogic.class);

	@Override
	public void miragationJSDataToCheck(String periodsNum,String scheme) {
		checkDao.miragationJSDataToCheck(periodsNum,scheme);

	}

	@Override
	public void miragationNCDataToCheck(String periodsNum,String scheme) {
		checkDao.miragationNCDataToCheck(periodsNum,scheme);

	}

	@Override
	public void miragationBJDataToCheck(String periodsNum,String scheme) {
		checkDao.miragationBJDataToCheck(periodsNum,scheme);

	}

	@Override
	public void miragationCQDataToCheckTable(String periodsNum,String scheme) {
		checkDao.miragationCQDataToCheckTable(periodsNum,scheme);

	}

	@Override
	public void miragationGDDataToCheck(String periodsNum,String scheme) {
		checkDao.miragationGDDataToCheck(periodsNum,scheme);

	}
	
	@Override
	public boolean checkReplenishWithHistory(String periodsNum,String schema) {
		//TODO
		boolean isSame = true;
		List<String> resultList = new ArrayList<String>();
		//取出补货表数据
		/*List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
        filtersPeriodInfo.add(Restrictions.eq("periodsNum",periodsNum));
        List<ReplenishHis> replenishList = replenishLogic.findReplenishHis(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));*/
		List<ReplenishHis> replenishList = replenishHisDao.queryReplenishHisList(periodsNum, schema);
        //取出补货备份表数据
       /* List<Criterion> filtersPeriodInfoCheck = new ArrayList<Criterion>();
        filtersPeriodInfoCheck.add(Restrictions.eq("periodsNum",periodsNum));
        List<ReplenishCheck> replenishListCheck = replenishLogic.findReplenishCheck(filtersPeriodInfoCheck.toArray(new Criterion[filtersPeriodInfoCheck.size()]));*/
		List<ReplenishCheck> replenishListCheck = replenishCheckDao.queryReplenishCheckList(periodsNum, schema);
        
        if(replenishList.size()==0 && replenishListCheck.size()==0){
			//历史和投注备份表都没有数据
        	isSame= true;
			logger.info("补货校验,盘期:" + periodsNum +"  补货备份表与历史表都没有数据,没有投注");
			//resultList.add("补货校验,盘期:" + periodsNum +"  补货备份表与历史表都没有数据,没有投注");
			SendMail sendMail = new SendMail();
			sendMail.sendMailByBoss(resultList, Constant.SEND_MAIL_SMTP, Constant.SEND_MAIL_ACCOUNT, 
					Constant.SEND_MAIL_PASSWORD, Constant.TO_MAIL_ADDRESS1, Constant.SEND_MAIL_ADDRESS);
			sendMail.sendMailByBoss(resultList, Constant.SEND_MAIL_SMTP, Constant.SEND_MAIL_ACCOUNT, 
					Constant.SEND_MAIL_PASSWORD, Constant.TO_MAIL_ADDRESS2, Constant.SEND_MAIL_ADDRESS);
			sendMail.sendMailByBoss(resultList, Constant.SEND_MAIL_SMTP, Constant.SEND_MAIL_ACCOUNT, 
					Constant.SEND_MAIL_PASSWORD, Constant.TO_MAIL_ADDRESS3, Constant.SEND_MAIL_ADDRESS);
			return isSame;
		}
        if (replenishList.size() != replenishListCheck.size()) {
			resultList.add("补货校验,盘期:" + periodsNum + "补货备份表与历史表记录总条数不匹配,投注备份表条数:" + replenishListCheck.size() + " || 历史表条数:" + replenishList.size());
		}
        
        //对比两个List
        if (replenishList.size() > replenishListCheck.size()) {
			// 如果历史表的记录比备份表多
			for (ReplenishHis hisVo : replenishList) {
				boolean matchFlag = false;
				for (ReplenishCheck checkVo : replenishListCheck) {
					if (hisVo.getOrderNo().equals(checkVo.getOrderNo())) {
						matchFlag = true;
						resultList.addAll(this.validateFieldForReplenish(hisVo, checkVo,periodsNum));
						break;
					}
				}
				if (!matchFlag) {
					resultList.add("补货校验,盘期:" + periodsNum +" 历史表注单号:"+hisVo.getOrderNo()+",金额是:"+hisVo.getMoney()+" 在补货备份表里不存在");
				}
			}
		} else {
			// 如果历史表数据少于或者等备份表
			for (ReplenishCheck checkVo : replenishListCheck) {
				boolean matchFlag = false;
				for (ReplenishHis hisVo : replenishList) {
						if (hisVo.getOrderNo().equals(checkVo.getOrderNo())) {
							matchFlag = true;
							resultList.addAll(this.validateFieldForReplenish(hisVo, checkVo,periodsNum));
							break;
						}
				}
				if (!matchFlag) {
					resultList.add("补货校验,盘期:" + periodsNum +" 补货备份表注单号:"+checkVo.getOrderNo()+",金额是:"+checkVo.getMoney()+" 在历史表里不存在");
				}
			}
		}
        if(CollectionUtils.isNotEmpty(resultList)){
			logger.info("补货数据校验错误");
			for(String msg:resultList){
				logger.info(msg);
			}
			isSame = false;
			//CHECK EMAIL
			SendMail sendMail = new SendMail();
			sendMail.sendMailByBoss(resultList, Constant.SEND_MAIL_SMTP, Constant.SEND_MAIL_ACCOUNT, 
					Constant.SEND_MAIL_PASSWORD, Constant.TO_MAIL_ADDRESS1, Constant.SEND_MAIL_ADDRESS);
			sendMail.sendMailByBoss(resultList, Constant.SEND_MAIL_SMTP, Constant.SEND_MAIL_ACCOUNT, 
					Constant.SEND_MAIL_PASSWORD, Constant.TO_MAIL_ADDRESS2, Constant.SEND_MAIL_ADDRESS);
			sendMail.sendMailByBoss(resultList, Constant.SEND_MAIL_SMTP, Constant.SEND_MAIL_ACCOUNT, 
					Constant.SEND_MAIL_PASSWORD, Constant.TO_MAIL_ADDRESS3, Constant.SEND_MAIL_ADDRESS);

		}else{
			isSame = true;
			logger.info("补货校验通过");
		}
        return isSame;
	}
	
	@Override
	public boolean checkBetWithBackupByPeridosNum(String periodsNum, String lotteryType,String scheme) {
		String lotteryName = "";
		if (Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)) {
			lotteryName = "广东快乐十分";
		} else if (Constant.LOTTERY_TYPE_CQSSC.equals(lotteryType)) {
			lotteryName = "重庆时时彩";
		} else if (Constant.LOTTERY_TYPE_BJSC.equals(lotteryType)) {
			lotteryName = "北京赛车";
		} else if (Constant.LOTTERY_TYPE_K3.equals(lotteryType)) {
			lotteryName = "江苏骰宝";
		} else if (Constant.LOTTERY_TYPE_NC.equals(lotteryType)) {
			lotteryName = "幸运农场";
		}
		boolean flag = true;
		List<String> resultList = new ArrayList<String>();
		//int hisCount = checkDao.getHisCountByPeriodsNum(periodsNum, lotteryType);
		//int backupCount = checkDao.getMatchCheckCountByPeriodsNum(periodsNum, lotteryType);
		List<BetCheckVo> hisVoList = checkDao.getCheckVoByPeriodsNumInHisTable(periodsNum, lotteryType,scheme);
		List<BetCheckVo> backupVoList = checkDao.getCheckVoByPeriodsNumInCheckTable(periodsNum, lotteryType,scheme);
		if(hisVoList.size()==0 && backupVoList.size()==0){
			//历史和投注备份表都没有数据
			flag= true;
			logger.info(lotteryName + " 盘期:" + periodsNum +"  投注备份表与历史表都没有数据,没有投注");
			//resultList.add(lotteryName + "数据校验,盘期:" + periodsNum +"  投注备份表与历史表都没有数据,没有投注");
			
			SendMail sendMail = new SendMail();
			sendMail.sendMailByBoss(resultList, Constant.SEND_MAIL_SMTP, Constant.SEND_MAIL_ACCOUNT, 
					Constant.SEND_MAIL_PASSWORD, Constant.TO_MAIL_ADDRESS1, Constant.SEND_MAIL_ADDRESS);
			sendMail.sendMailByBoss(resultList, Constant.SEND_MAIL_SMTP, Constant.SEND_MAIL_ACCOUNT, 
					Constant.SEND_MAIL_PASSWORD, Constant.TO_MAIL_ADDRESS2, Constant.SEND_MAIL_ADDRESS);
			sendMail.sendMailByBoss(resultList, Constant.SEND_MAIL_SMTP, Constant.SEND_MAIL_ACCOUNT, 
					Constant.SEND_MAIL_PASSWORD, Constant.TO_MAIL_ADDRESS3, Constant.SEND_MAIL_ADDRESS);
			
			return flag;
		}
		if (hisVoList.size() != backupVoList.size()) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + "投注备份表与历史表记录总条数不匹配,投注备份表条数:" + backupVoList.size() + " || 历史表条数:" + hisVoList.size());
		}

		if (hisVoList.size() > backupVoList.size()) {
			// 如果历史表的记录比备份表多
			for (BetCheckVo hisVo : hisVoList) {
				boolean matchFlag = false;
				for (BetCheckVo backupVo : backupVoList) {

					if (hisVo.getOriginTBName().equals(backupVo.getOriginTBName())) {
						if (hisVo.getOriginID().equals(backupVo.getOriginID())) {
							matchFlag = true;
							resultList.addAll(this.validateField(hisVo, backupVo,lotteryName,periodsNum));
							break;
						}
					}
				}
				if (!matchFlag) {
					resultList.add(lotteryName + " 盘期:" + periodsNum +" 历史表注单号:"+hisVo.getOrderNo()+" 在投注备份表里不存在");
				}
			}
		} else {
			// 如果历史表数据少于或者等备份表
			for (BetCheckVo backupVo : backupVoList) {
				boolean matchFlag = false;
				for (BetCheckVo hisVo : hisVoList) {

					if (hisVo.getOriginTBName().equals(backupVo.getOriginTBName())) {
						if (hisVo.getOriginID().equals(backupVo.getOriginID())) {
							matchFlag = true;
							resultList.addAll(this.validateField(hisVo, backupVo,lotteryName,periodsNum));
							break;
						}
					}
				}
				if (!matchFlag) {
					resultList.add(lotteryName + " 盘期:" + periodsNum +" 投注备份表注单号:"+backupVo.getOrderNo()+" 在历史表里不存在");
				}
			}
		}
		
		if(CollectionUtils.isNotEmpty(resultList)){
			logger.info(lotteryName+"  投注数据校验错误");
			for(String msg:resultList){
				logger.info(msg);
			}
			flag = false;
			//CHECK EMAIL
			SendMail sendMail = new SendMail();
			sendMail.sendMailByBoss(resultList, Constant.SEND_MAIL_SMTP, Constant.SEND_MAIL_ACCOUNT, 
					Constant.SEND_MAIL_PASSWORD, Constant.TO_MAIL_ADDRESS1, Constant.SEND_MAIL_ADDRESS);
			sendMail.sendMailByBoss(resultList, Constant.SEND_MAIL_SMTP, Constant.SEND_MAIL_ACCOUNT, 
					Constant.SEND_MAIL_PASSWORD, Constant.TO_MAIL_ADDRESS2, Constant.SEND_MAIL_ADDRESS);
			sendMail.sendMailByBoss(resultList, Constant.SEND_MAIL_SMTP, Constant.SEND_MAIL_ACCOUNT, 
					Constant.SEND_MAIL_PASSWORD, Constant.TO_MAIL_ADDRESS3, Constant.SEND_MAIL_ADDRESS);

		}else{
			flag = true;
			logger.info(lotteryName+"  投注数据校验通过");
		}

		return flag;
	}
	
	private List<String> validateFieldForReplenish(ReplenishHis r, ReplenishCheck c, String periodsNum) {
		List<String> resultList = new ArrayList<String>();
		if(!r.getTypeCode().equals(c.getTypeCode())){
			resultList.add("补货单号为:"+r.getOrderNo()+",种类不一样,"+"历史表为:"+r.getTypeCode()+",备份表为:"+c.getTypeCode());
			}
		if(r.getMoney()!=c.getMoney()){
			resultList.add("补货单号为:"+r.getOrderNo()+",金额不一样,"+"历史表为:"+r.getMoney()+",备份表为:"+c.getMoney());
			}
		if(r.getReplenishUserId()!=c.getReplenishUserId()){
			resultList.add("补货单号为:"+r.getOrderNo()+",补货人不一样,"+"历史表为:"+r.getReplenishUserId()+",备份表为:"+c.getReplenishUserId());
			}
		if(!r.getReplenishUserType().equals(c.getReplenishUserType())){
			resultList.add("补货单号为:"+r.getOrderNo()+",补货人类型不一样,"+"历史表为:"+r.getReplenishUserType()+",备份表为:"+c.getReplenishUserType());
			}
		if(!r.getPlate().equals(c.getPlate())){
			resultList.add("补货单号为:"+r.getOrderNo()+",盘口不一样,"+"历史表为:"+r.getPlate()+",备份表为:"+c.getPlate());
			}
		if(!r.getBettingDate().equals(c.getBettingDate())){
			resultList.add("补货单号为:"+r.getOrderNo()+",发生时间不一样,"+"历史表为:"+r.getBettingDate()+",备份表为:"+c.getBettingDate());
			}
		if(r.getOdds().compareTo(c.getOdds())!=0){
			resultList.add("补货单号为:"+r.getOrderNo()+",赔率不一样,"+"历史表为:"+r.getOdds()+",备份表为:"+c.getOdds());
			}
		if(!r.getAttribute().equals(c.getAttribute())){
			resultList.add("补货单号为:"+r.getOrderNo()+",attribute不一样,"+"历史表为:"+r.getAttribute()+",备份表为:"+c.getAttribute());
			}
		if(!r.getSelectedOdds().equals(c.getSelectedOdds())){
			resultList.add("补货单号为:"+r.getOrderNo()+",selectedOdds不一样,"+"历史表为:"+r.getSelectedOdds()+",备份表为:"+c.getSelectedOdds());
			}
		if(r.getChiefStaff()!=c.getChiefStaff()){
			resultList.add("补货单号为:"+r.getOrderNo()+",所属总监不一样,"+"历史表为:"+r.getChiefStaff()+",备份表为:"+c.getChiefStaff());
			}
		if(r.getBranchStaff()!=c.getBranchStaff()){
			resultList.add("补货单号为:"+r.getOrderNo()+",所属分公司不一样,"+"历史表为:"+r.getBranchStaff()+",备份表为:"+c.getBranchStaff());
			}
		if(r.getStockHolderStaff()!=c.getStockHolderStaff()){
			resultList.add("补货单号为:"+r.getOrderNo()+",所属股东不一样,"+"历史表为:"+r.getStockHolderStaff()+",备份表为:"+c.getStockHolderStaff());
			}
		if(r.getGenAgenStaff()!=c.getGenAgenStaff()){
			resultList.add("补货单号为:"+r.getOrderNo()+",所属总代理不一样,"+"历史表为:"+r.getGenAgenStaff()+",备份表为:"+c.getGenAgenStaff());
			}
		if(r.getAgentStaff()!=c.getAgentStaff()){
			resultList.add("补货单号为:"+r.getOrderNo()+",所属代理不一样,"+"历史表为:"+r.getAgentStaff()+",备份表为:"+c.getAgentStaff());
			}
		if(r.getRateChief().compareTo(c.getRateChief())==0){
			resultList.add("补货单号为:"+r.getOrderNo()+",总监占成不一样,"+"历史表为:"+r.getRateChief()+",备份表为:"+c.getRateChief());
			}
		if(r.getRateBranch().compareTo(c.getRateBranch())!=0){
			resultList.add("补货单号为:"+r.getOrderNo()+",分公司占成不一样,"+"历史表为:"+r.getRateBranch()+",备份表为:"+c.getRateBranch());
			}
		if(r.getRateStockHolder().compareTo(c.getRateStockHolder())!=0){
			resultList.add("补货单号为:"+r.getOrderNo()+",股东占成不一样,"+"历史表为:"+r.getRateStockHolder()+",备份表为:"+c.getRateStockHolder());
			}
		if(r.getRateGenAgent().compareTo(c.getRateGenAgent())!=0){
			resultList.add("补货单号为:"+r.getOrderNo()+",总代理占成不一样,"+"历史表为:"+r.getRateGenAgent()+",备份表为:"+c.getRateGenAgent());
			}
		if(r.getRateAgent().compareTo(c.getRateAgent())!=0){
			resultList.add("补货单号为:"+r.getOrderNo()+",代理占成不一样,"+"历史表为:"+r.getRateAgent()+",备份表为:"+c.getRateAgent());
			}
		if(r.getCommissionChief().compareTo(c.getCommissionChief())!=0){
			resultList.add("补货单号为:"+r.getOrderNo()+",CommissionChief不一样,"+"历史表为:"+r.getCommissionChief()+",备份表为:"+c.getCommissionChief());
			}
		if(r.getCommissionBranch().compareTo(c.getCommissionBranch())!=0){
			resultList.add("补货单号为:"+r.getOrderNo()+",CommissionBranch不一样,"+"历史表为:"+r.getCommissionBranch()+",备份表为:"+c.getCommissionBranch());
			}
		if(r.getCommissionStockHolder().compareTo(c.getCommissionStockHolder())!=0){
			resultList.add("补货单号为:"+r.getOrderNo()+",CommissionStockHolder不一样,"+"历史表为:"+r.getCommissionStockHolder()+",备份表为:"+c.getCommissionStockHolder());
			}
		if(r.getCommissionGenAgent().compareTo(c.getCommissionGenAgent())!=0){
			resultList.add("补货单号为:"+r.getOrderNo()+",CommissionGenAgent不一样,"+"历史表为:"+r.getCommissionGenAgent()+",备份表为:"+c.getCommissionGenAgent());
			}
		if(r.getCommissionAgent().compareTo(c.getCommissionAgent())!=0){
			resultList.add("补货单号为:"+r.getOrderNo()+",CommissionAgent不一样,"+"历史表为:"+r.getCommissionAgent()+",备份表为:"+c.getCommissionAgent());
			}
		if(r.getCommissionMember().compareTo(c.getCommissionMember())!=0){
			resultList.add("补货单号为:"+r.getOrderNo()+",CommissionMember不一样,"+"历史表为:"+r.getCommissionMember()+",备份表为:"+c.getCommissionMember());
			}
		if(r.getOdds2().compareTo(c.getOdds2())!=0){
			resultList.add("补货单号为:"+r.getOrderNo()+",odds2不一样,"+"历史表为:"+r.getOdds2()+",备份表为:"+c.getOdds2());
			}
		if(!r.getCommissionType().equals(c.getCommissionType())){
			resultList.add("补货单号为:"+r.getOrderNo()+",退水类型不一样,"+"历史表为:"+r.getCommissionType()+",备份表为:"+c.getCommissionType());
			}
		return resultList;
	}
	
	private List<String> validateField(BetCheckVo hisVo, BetCheckVo backupVo, String lotteryName, String periodsNum) {
		List<String> resultList = new ArrayList<String>();
		if (!StringUtils.equals(hisVo.getOrderNo(), backupVo.getOrderNo())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 注单号不匹配,历史表注单号:" + hisVo.getOrderNo() + " || 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getTypeCode(), backupVo.getTypeCode())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 投注类型不匹配,历史表投注类型:" + hisVo.getTypeCode() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表投注类型:" + backupVo.getTypeCode() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getMoney(), backupVo.getMoney())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 投注金额不匹配,历史表投注金额:" + hisVo.getMoney() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表投注金额:" + backupVo.getMoney() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getPlate(), backupVo.getPlate())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 盘口不匹配,历史表盘口:" + hisVo.getPlate() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表盘口:" + backupVo.getPlate() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getBettingUserID(), backupVo.getBettingUserID())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 投注人不匹配,历史表投注人:" + hisVo.getBettingUserID() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表投注人:" + backupVo.getBettingUserID() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}

		if (hisVo.getBettingDate().compareTo(backupVo.getBettingDate()) != 0) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 投注日期不匹配,历史表 投注日期:" + hisVo.getBettingDate() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表 投注日期:" + backupVo.getBettingDate() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getOdds(), backupVo.getOdds())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 赔率不匹配,历史表赔率:" + hisVo.getOdds() + " / 历史表注单号:" + hisVo.getOrderNo() + " || 投注备份表赔率:"
					+ backupVo.getOdds() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getAttribute(), backupVo.getAttribute())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 连码Attribute不匹配,历史表连码Attribute:" + hisVo.getAttribute() + " / 历史表注单号:"
					+ hisVo.getOrderNo() + " || 投注备份表连码Attribute:" + backupVo.getAttribute() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getSplitAttribute(), backupVo.getSplitAttribute())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 连码SplitAttribute不匹配,历史表SplitAttribute:" + hisVo.getSplitAttribute() + " / 历史表注单号:"
					+ hisVo.getOrderNo() + " || 投注备份表SplitAttribute:" + backupVo.getSplitAttribute() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}

		if (!StringUtils.equals(hisVo.getChiefStaff(), backupVo.getChiefStaff())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属总监不匹配,历史表总监:" + hisVo.getChiefStaff() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表总监:" + backupVo.getChiefStaff() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getBranchStaff(), backupVo.getBranchStaff())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属分公司不匹配,历史表分公司:" + hisVo.getBranchStaff() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表分公司:" + backupVo.getBranchStaff() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getStockholderStaff(), backupVo.getStockholderStaff())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属股东不匹配,历史表股东:" + hisVo.getStockholderStaff() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表股东:" + backupVo.getStockholderStaff() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}

		if (!StringUtils.equals(hisVo.getGenagenStaff(), backupVo.getGenagenStaff())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属总代理不匹配,历史表总代理:" + hisVo.getGenagenStaff() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表总代理:" + backupVo.getGenagenStaff() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getAgentStaff(), backupVo.getAgentStaff())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属代理不匹配,历史表代理:" + hisVo.getAgentStaff() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表代理:" + backupVo.getAgentStaff() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}

		if (!StringUtils.equals(hisVo.getCommissionBranch(), backupVo.getCommissionBranch())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属分公司退水不匹配,历史表分公司退水:" + hisVo.getCommissionBranch() + " / 历史表注单号:"
					+ hisVo.getOrderNo() + " || 投注备份表分公司退水:" + backupVo.getCommissionBranch() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getCommissionGenAgent(), backupVo.getCommissionGenAgent())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属总代理退水不匹配,历史表总代理退水:" + hisVo.getCommissionGenAgent() + " / 历史表注单号:"
					+ hisVo.getOrderNo() + " || 投注备份表总代理退水:" + backupVo.getCommissionGenAgent() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getCommissionStockholder(), backupVo.getCommissionStockholder())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属股东退水不匹配,历史表股东退水:" + hisVo.getCommissionStockholder() + " / 历史表注单号:"
					+ hisVo.getOrderNo() + " || 投注备份表股东退水:" + backupVo.getCommissionStockholder() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getCommissionAgent(), backupVo.getCommissionAgent())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属代理退水不匹配,历史表代理退水:" + hisVo.getCommissionAgent() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表代理退水:" + backupVo.getCommissionAgent() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getCommissionMember(), backupVo.getCommissionMember())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属会员退水不匹配,历史表会员退水:" + hisVo.getCommissionMember() + " / 历史表注单号:"
					+ hisVo.getOrderNo() + " || 投注备份表会员退水:" + backupVo.getCommissionMember() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}

		if (!StringUtils.equals(hisVo.getRateChief(), backupVo.getRateChief())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属总监占成不匹配,历史表总监占成:" + hisVo.getRateChief() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表总监占成:" + backupVo.getRateChief() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getRateBranch(), backupVo.getRateBranch())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属分公司占成不匹配,历史表分公司占成:" + hisVo.getRateBranch() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表分公司占成:" + backupVo.getRateBranch() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getRateStockholder(), backupVo.getRateStockholder())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属股东占成不匹配,历史表股东占成:" + hisVo.getRateStockholder() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表股东占成:" + backupVo.getRateStockholder() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getRateGenAgent(), backupVo.getRateGenAgent())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属总代理占成不匹配,历史表总代理占成:" + hisVo.getRateGenAgent() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表总代理占成:" + backupVo.getRateGenAgent() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getRateAgent(), backupVo.getRateAgent())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属代理占成不匹配,历史表代理占成:" + hisVo.getRateAgent() + " / 历史表注单号:" + hisVo.getOrderNo()
					+ " || 投注备份表代理占成:" + backupVo.getRateAgent() + " / 投注备份表注单号:" + backupVo.getOrderNo());
		}
		if (!StringUtils.equals(hisVo.getCommissionType(), backupVo.getCommissionType())) {
			resultList.add(lotteryName + " 盘期:" + periodsNum + " 所属退水类型CommissionType不匹配,历史表退水类型CommissionType:" + hisVo.getCommissionType()
					+ " / 历史表注单号:" + hisVo.getOrderNo() + " || 投注备份表退水类型CommissionType:" + backupVo.getCommissionType() + " / 投注备份表注单号:"
					+ backupVo.getOrderNo());
		}

		return resultList;
	}

	public ICheckDao getCheckDao() {
		return checkDao;
	}

	public void setCheckDao(ICheckDao checkDao) {
		this.checkDao = checkDao;
	}

	public IReplenishHisDao getReplenishHisDao() {
		return replenishHisDao;
	}

	public void setReplenishHisDao(IReplenishHisDao replenishHisDao) {
		this.replenishHisDao = replenishHisDao;
	}

	public IReplenishCheckDao getReplenishCheckDao() {
		return replenishCheckDao;
	}

	public void setReplenishCheckDao(IReplenishCheckDao replenishCheckDao) {
		this.replenishCheckDao = replenishCheckDao;
	}

	public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}

	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}

}
