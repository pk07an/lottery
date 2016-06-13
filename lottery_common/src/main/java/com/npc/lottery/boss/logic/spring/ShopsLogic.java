package com.npc.lottery.boss.logic.spring;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.npc.lottery.boss.dao.interf.IShopsDao;
import com.npc.lottery.boss.dao.interf.IShopsExtDao;
import com.npc.lottery.boss.dao.interf.IShopsRentDao;
import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.boss.logic.interf.IShopsLogic;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.service.BaseLogic;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.member.dao.interf.IBetDao;
import com.npc.lottery.member.dao.interf.IPlayAmountJdbcDao;
import com.npc.lottery.member.dao.interf.IPlayTypeDao;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.odds.dao.interf.IOpenPlayOddsJdbcDao;
import com.npc.lottery.odds.dao.interf.IShopOddsDao;
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
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.dao.hibernate.ChiefStaffExtDao;
import com.npc.lottery.user.dao.interf.IMemberStaffExtDao;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.util.Page;

/**
 * 功能逻辑处理类
 *
 * @author Eric
 *
 */
public class ShopsLogic extends BaseLogic implements IShopsLogic {

	Logger logger = Logger.getLogger(ShopsLogic.class);
	
	/**
	 * 盘期停开 更新会员可用额度
	 * @param periodsNum
	 * @param schemeMap
	 * @param tables
	 */
	@Override
	public void stopPeriodsByUpdateAvailableCredit(String periodsNum,Map<String,String> schemeMap,String[] tables){
		for (Entry<String,String> schemes : schemeMap.entrySet()) {
			String scheme=schemes.getValue();
			String shopCode=schemes.getKey();
			logger.info("盘期停开，更新"+shopCode+"商铺会员可用金额开始，盘期："+periodsNum);
			//查询出该盘期下注的会员和下注金额,查询投注表
			List<BaseBet> betList=betDao.queryAllMemberBetMoney(periodsNum, scheme, tables);
			for (BaseBet baseBet : betList) {
				//更新下注会员的可用额度，在原有基础上 加上
				memberStaffExtDao.updateMemberAvailableCreditLineByAdd(baseBet.getMoney().doubleValue(), baseBet.getBettingUserId(), scheme);
			}
			logger.info("盘期停开，更新"+shopCode+"商铺会员可用金额成功，盘期："+periodsNum);
		}
	}

	/**
	 * 盘期作废 更新会员可用额度
	 * @param periodsNum
	 * @param schemeMap
	 * @param tables
	 */
	@Override
	public void invalidPeriodsByUpdateAvailableCredit(String periodsNum,Map<String,String> schemeMap,String tables){
		for (Entry<String,String> schemes : schemeMap.entrySet()) {
			String scheme=schemes.getValue();
			String shopCode=schemes.getKey();
			logger.info("盘期作废，更新"+shopCode+"商铺会员可用金额开始，盘期："+periodsNum);
			//查询出该盘期下注的会员和下注金额,查询投注历史表
			List<BaseBet> betList=betDao.queryAllMemberWinOrLoseMoney(periodsNum, scheme, tables);
			for (BaseBet baseBet : betList) {
				double money=0;
				//如果为赢，则在原有基础上减去
				if(baseBet.getMoney()>0){
					//将正数转为负数
					money= new BigInteger(baseBet.getMoney().toString()).negate().doubleValue();
				}else{
					//为输，在原有基础上 加上
					//负数转正数
					money= new BigInteger(baseBet.getMoney().toString()).abs().doubleValue(); 
				}
				memberStaffExtDao.updateMemberAvailableCreditLineByAdd(money, baseBet.getBettingUserId(), scheme);
			}
			logger.info("盘期作废，更新"+shopCode+"商铺会员可用金额成功，盘期："+periodsNum);
		}
	}
	
	@Override
	@Transactional
	public void saveShopsRegister(ShopsInfo shopsInfo, ChiefStaffExt entity) {
		ManagerUser userInfo = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);

		// 保存商铺信息和商铺租赁信息表 tb_shops_info,tb_shops_rent
		shopsDao.save(shopsInfo);
		// 新建总监
		chiefStaffExtDao.save(entity);
		// 初始化玩法金额汇总表 tb_play_amount
		playAmountJdbcDao.savePLayAmoutForOpen(shopsInfo.getShopsCode());
		// 初始化商铺赔率表数据 tb_shops_play_odds
		shopOddsDao.saveShopsPlayOddsForAddShop(shopsInfo.getShopsCode(), userInfo.getID());
		// 初始化开盘赔率表数据 tb_open_play_odds
		openPlayOddsJdbcDao.saveOpenPlayOddsForAddShop(shopsInfo.getShopsCode(), userInfo.getID());

	}

	@Override
	public Page<ShopsInfo> findShopsPage(Page<ShopsInfo> page) {
		return shopsDao.getAll(page);
	}

	@Override
	public ShopsInfo findShopsCode(String shopsCode) {

		return shopsDao.findShopsCode(shopsCode);
	}

	@Override
	public String verifyShopState(String shopsCode) {
		// TODO Auto-generated method stub
		String state = null;
		ShopsInfo shopInfo = shopsDao.findShopsCode(shopsCode);
		if (shopInfo.getState().equals(Constant.SHOP_OPEN)) {
			state = Constant.SHOP_OPEN;
		}
		if (shopInfo.getState().equals(Constant.SHOP_FREEZE)) {
			state = Constant.SHOP_FREEZE;
		}
		if (shopInfo.getState().equals(Constant.SHOP_CLOSE)) {
			state = Constant.SHOP_CLOSE;
		}
		return state;
	}

	@Transactional
	@Override
	public void updateGDStopStart(String periodsNum, Map<String, String> shopSchemeMap) {
		GDPeriodsInfo gdp = gdPeriodsInfoLogic.queryByPeriods("periodsNum", periodsNum);

		// 每一个商铺按每期进去兑奖
		for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
			String scheme = shopScheme.getValue();
			if ("7".equals(gdp.getState())) {// 如果已经兑奖了
				betDao.updateBetResultInvalid(periodsNum, "GDKLSF", scheme);
				betDao.updateReplenishResultInvalid(periodsNum, "GDKLSF%", "5", scheme);

			} else {
				if (new Date().after(gdp.getStopQuotTime())) {
					// 如果执行在封盘时间之后的
					this.betDao.miragationGDDataToHistory(periodsNum, "0", scheme);
					this.betDao.miragationReplenishDataToHistoryForNotOpen(periodsNum, "GDKLSF", scheme);
				}
			}
		}
		gdPeriodsInfoLogic.updatePeriodsStatusByPeriodsNum(periodsNum, "6");// 6為停盤期
	}

	@Transactional
	@Override
	public void updateCQStopStart(String periodsNum, Map<String, String> shopSchemeMap) {
		CQPeriodsInfo cqp = icqPeriodsInfoLogic.queryByPeriods("periodsNum", periodsNum);
		// 每一个商铺按每期进去兑奖
		for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
			String scheme = shopScheme.getValue();
			if ("7".equals(cqp.getState())) {
				betDao.updateBetResultInvalid(periodsNum, "CQSSC", scheme);
				betDao.updateReplenishResultInvalid(periodsNum, "CQSSC%", "5", scheme);

			} else {
				if (new Date().after(cqp.getStopQuotTime())) {
					// 如果执行在封盘时间之后的
					this.betDao.miragationCQDataToHistory(periodsNum, "0", scheme);
					this.betDao.miragationReplenishDataToHistoryForNotOpen(periodsNum, "CQSSC", scheme);
				}
			}
		}
		icqPeriodsInfoLogic.updatePeriodsStatusByPeriodsNum(periodsNum, "6");
	}

	@Transactional
	@Override
	public void updateBJStopStart(String periodsNum, Map<String, String> shopSchemeMap) {
		BJSCPeriodsInfo cqp = bjscPeriodsInfoLogic.queryByPeriods("periodsNum", periodsNum);
		// 每一个商铺按每期进去兑奖
		for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
			String scheme = shopScheme.getValue();
			if ("7".equals(cqp.getState())) {
				betDao.updateBetResultInvalid(periodsNum, "BJ", scheme);
				betDao.updateReplenishResultInvalid(periodsNum, "BJ%", "5", scheme);

			} else {
				if (new Date().after(cqp.getStopQuotTime())) {
					// 如果执行在封盘时间之后的
					this.betDao.miragationBJDataToHistory(periodsNum, "0", scheme);
					this.betDao.miragationReplenishDataToHistoryForNotOpen(periodsNum, "BJ", scheme);
				}
			}
		}
		bjscPeriodsInfoLogic.updatePeriodsStatusByPeriodsNum(periodsNum, "6");
	}

	@Transactional
	@Override
	public void updateK3StopStart(String periodsNum, Map<String, String> shopSchemeMap) {
		List<Criterion> filters = new ArrayList<Criterion>();
		filters.add(Restrictions.eq("periodsNum", periodsNum));

		JSSBPeriodsInfo cqp = jssbPeriodsInfoLogic.queryByPeriods(filters.toArray(new Criterion[filters.size()]));
		// 每一个商铺按每期进去兑奖
		for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
			String scheme = shopScheme.getValue();
			if ("7".equals(cqp.getState())) {
				betDao.updateBetResultInvalid(periodsNum, Constant.LOTTERY_TYPE_K3, scheme);
				betDao.updateReplenishResultInvalid(periodsNum, "K3%", "5", scheme);
			} else {
				if (new Date().after(cqp.getStopQuotTime())) {
					// 如果执行在封盘时间之后的

					this.betDao.miragationBJDataToHistory(periodsNum, "0", scheme);
					this.betDao.miragationReplenishDataToHistoryForNotOpen(periodsNum, Constant.LOTTERY_TYPE_K3, scheme);
				}
			}
		}
		jssbPeriodsInfoLogic.updatePeriodsStatusByPeriodsNum(periodsNum, "6");
	}

	@Transactional
	@Override
	public void updateNCStopStart(String periodsNum, Map<String, String> shopSchemeMap) {
		List<Criterion> filters = new ArrayList<Criterion>();
		filters.add(Restrictions.eq("periodsNum", periodsNum));

		NCPeriodsInfo cqp = ncPeriodsInfoLogic.queryByPeriods(filters.toArray(new Criterion[filters.size()]));
		// 每一个商铺按每期进去兑奖
		for (Entry<String, String> shopScheme : shopSchemeMap.entrySet()) {
			String scheme = shopScheme.getValue();
			if ("7".equals(cqp.getState())) {
				betDao.updateBetResultInvalid(periodsNum, Constant.LOTTERY_TYPE_NC, scheme);
				betDao.updateReplenishResultInvalid(periodsNum, "NC%", "5", scheme);
			} else {
				if (new Date().after(cqp.getStopQuotTime())) {
					// 如果执行在封盘时间之后的
					this.betDao.miragationNCDataToHistory(periodsNum, "0", scheme);
					this.betDao.miragationReplenishDataToHistoryForNotOpen(periodsNum, Constant.LOTTERY_TYPE_NC, scheme);
				}
			}
		}
		ncPeriodsInfoLogic.updatePeriodsStatusByPeriodsNum(periodsNum, "6");
	}

	@Override
	public ShopsInfo findShopsName(String shopsName) {
		// TODO Auto-generated method stub
		return shopsDao.findShopsName(shopsName);
	}

	@Override
	public List<ShopsInfo> findShopsList(Criterion... criterions) {
		return shopsDao.find(criterions);
	}

	/**
	 * 查询所有商铺信息
	 */
	@Override
	public List<ShopsInfo> findShopsAll(Map<String, String> schemeMap) {

		return shopsDao.findShopsAll(schemeMap);
	}

	public void update(ShopsInfo entity) {
		shopsExtDao.update(entity);
		shopsRentDao.update(entity.getShopsRent().iterator().next());
	}

	private IShopsDao shopsDao = null;
	private IShopsExtDao shopsExtDao = null;
	private IShopsRentDao shopsRentDao = null;
	private ChiefStaffExtDao chiefStaffExtDao = null;
	private IPlayTypeDao playTypeDao = null;
	private IShopOddsDao shopOddsDao = null;
	private IOpenPlayOddsJdbcDao openPlayOddsJdbcDao = null;
	private IPlayAmountJdbcDao playAmountJdbcDao = null;

	private IBetDao betDao;
	private IGDPeriodsInfoLogic gdPeriodsInfoLogic;
	private ICQPeriodsInfoLogic icqPeriodsInfoLogic;
	private IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic;
	private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
	private INCPeriodsInfoLogic ncPeriodsInfoLogic;
	private IMemberStaffExtDao memberStaffExtDao;

	public IMemberStaffExtDao getMemberStaffExtDao() {
		return memberStaffExtDao;
	}

	public void setMemberStaffExtDao(IMemberStaffExtDao memberStaffExtDao) {
		this.memberStaffExtDao = memberStaffExtDao;
	}

	private ShopSchemeService shopSchemeService;

	public ShopSchemeService getShopSchemeService() {
		return shopSchemeService;
	}

	public void setShopSchemeService(ShopSchemeService shopSchemeService) {
		this.shopSchemeService = shopSchemeService;
	}

	public IShopsDao getShopsDao() {
		return shopsDao;
	}

	public void setShopsDao(IShopsDao shopsDao) {
		this.shopsDao = shopsDao;
	}

	public IShopsRentDao getShopsRentDao() {
		return shopsRentDao;
	}

	public void setShopsRentDao(IShopsRentDao shopsRentDao) {
		this.shopsRentDao = shopsRentDao;
	}

	public IShopsExtDao getShopsExtDao() {
		return shopsExtDao;
	}

	public void setShopsExtDao(IShopsExtDao shopsExtDao) {
		this.shopsExtDao = shopsExtDao;
	}

	public ChiefStaffExtDao getChiefStaffExtDao() {
		return chiefStaffExtDao;
	}

	public void setChiefStaffExtDao(ChiefStaffExtDao chiefStaffExtDao) {
		this.chiefStaffExtDao = chiefStaffExtDao;
	}

	public IPlayTypeDao getPlayTypeDao() {
		return playTypeDao;
	}

	public void setPlayTypeDao(IPlayTypeDao playTypeDao) {
		this.playTypeDao = playTypeDao;
	}

	public IShopOddsDao getShopOddsDao() {
		return shopOddsDao;
	}

	public void setShopOddsDao(IShopOddsDao shopOddsDao) {
		this.shopOddsDao = shopOddsDao;
	}

	public IOpenPlayOddsJdbcDao getOpenPlayOddsJdbcDao() {
		return openPlayOddsJdbcDao;
	}

	public void setOpenPlayOddsJdbcDao(IOpenPlayOddsJdbcDao openPlayOddsJdbcDao) {
		this.openPlayOddsJdbcDao = openPlayOddsJdbcDao;
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public IPlayAmountJdbcDao getPlayAmountJdbcDao() {
		return playAmountJdbcDao;
	}

	public void setPlayAmountJdbcDao(IPlayAmountJdbcDao playAmountJdbcDao) {
		this.playAmountJdbcDao = playAmountJdbcDao;
	}

	public IBetDao getBetDao() {
		return betDao;
	}

	public void setBetDao(IBetDao betDao) {
		this.betDao = betDao;
	}

	public IGDPeriodsInfoLogic getGdPeriodsInfoLogic() {
		return gdPeriodsInfoLogic;
	}

	public void setGdPeriodsInfoLogic(IGDPeriodsInfoLogic gdPeriodsInfoLogic) {
		this.gdPeriodsInfoLogic = gdPeriodsInfoLogic;
	}

	public ICQPeriodsInfoLogic getIcqPeriodsInfoLogic() {
		return icqPeriodsInfoLogic;
	}

	public void setIcqPeriodsInfoLogic(ICQPeriodsInfoLogic icqPeriodsInfoLogic) {
		this.icqPeriodsInfoLogic = icqPeriodsInfoLogic;
	}

	public IJSSBPeriodsInfoLogic getJssbPeriodsInfoLogic() {
		return jssbPeriodsInfoLogic;
	}

	public void setJssbPeriodsInfoLogic(IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic) {
		this.jssbPeriodsInfoLogic = jssbPeriodsInfoLogic;
	}

	public IBJSCPeriodsInfoLogic getBjscPeriodsInfoLogic() {
		return bjscPeriodsInfoLogic;
	}

	public void setBjscPeriodsInfoLogic(IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic) {
		this.bjscPeriodsInfoLogic = bjscPeriodsInfoLogic;
	}

	public INCPeriodsInfoLogic getNcPeriodsInfoLogic() {
		return ncPeriodsInfoLogic;
	}

	public void setNcPeriodsInfoLogic(INCPeriodsInfoLogic ncPeriodsInfoLogic) {
		this.ncPeriodsInfoLogic = ncPeriodsInfoLogic;
	}

	@Override
	public ShopsInfo findShopsInfoByCode(String shopsCode) {
		// TODO Auto-generated method stub
		return shopsDao.findShopsInfoByCode(shopsCode);
	}

}
