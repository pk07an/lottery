package com.npc.lottery.odds.logic.spring;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.npc.lottery.boss.dao.interf.IShopsDao;
import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.common.Constant;
import com.npc.lottery.odds.dao.interf.IOpenPlayOddsDao;
import com.npc.lottery.odds.dao.interf.IOpenPlayOddsJdbcDao;
import com.npc.lottery.odds.dao.interf.IShopOddsDao;
import com.npc.lottery.odds.entity.OpenPlayOdds;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.logic.interf.IOpenPlayOddsLogic;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.HKPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IHKPeriodsInfoLogic;
import com.npc.lottery.sysmge.dao.interf.IManagerStaffDao;
import com.npc.lottery.sysmge.dao.interf.IShopsPlayOddsLogDao;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.ShopsPlayOddsLog;
import com.npc.lottery.sysmge.logic.interf.IShopsPlayOddsLogLogic;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.util.Page;
import com.npc.lottery.util.WebTools;

public class ShopOddsLogic implements IShopOddsLogic {

	private Logger logger = Logger.getLogger(ShopOddsLogic.class);
	private IShopOddsDao shopOddsDao = null;
	private IShopsDao shopsDao = null;
	private IOpenPlayOddsDao openPlayOddsDao = null;
	private IOpenPlayOddsJdbcDao openPlayOddsJdbcDao = null;
	private IManagerStaffDao managerStaffDao = null;

	private IShopsPlayOddsLogLogic shopsPlayOddsLogLogic;
	protected IHKPeriodsInfoLogic skperiodsInfoLogic;
	private IOpenPlayOddsLogic openPlayOddsLogic;
	private IShopsPlayOddsLogDao shopsPlayOddsLogDao;
	private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
	private IGDPeriodsInfoLogic periodsInfoLogic;
	private ICQPeriodsInfoLogic icqPeriodsInfoLogic;

	private String runningPeriodsNum = "";

	public Page queryShopsPlayOddsLog(Page page, String shopCode, String periodsNum, String typeCode, Date prevSearchTime, String opType) {

		return shopOddsDao.queryShopsPlayOddsLog(page, shopCode, periodsNum, typeCode, prevSearchTime, opType);
	}

	public List<ShopsPlayOdds> queryCQSSCRealOdds(String shopCode, String like) {

		// String hql =
		// "from ShopsPlayOdds where shopsCode=? and playTypeCode like 'CQSSC%'";
		// Object[] parameter = new Object[] {shopCode};
		return shopOddsDao.queryShopRealOdds(shopCode, like);
		// return shopOddsDao.find(hql, parameter);

	}

	public List<ShopsPlayOdds> queryBJSCRealOdds(String shopCode, String like) {

		return shopOddsDao.queryShopRealOdds(shopCode, like);
	}

	public List<ShopsPlayOdds> queryGDKLSFRealOdds(String shopCode, String like) {
		// String hql =
		// "from ShopsPlayOdds where shopsCode=? and playTypeCode like 'GDKLSF%'";
		// Object[] parameter = new Object[] {shopCode};
		return shopOddsDao.queryShopRealOdds(shopCode, like);
		// return shopOddsDao.find(hql, parameter);

	}

	public List<ShopsPlayOdds> queryShopRealOddsByLoop(String shopCode, List<String> list) {
		return shopOddsDao.queryShopRealOddsByLoop(shopCode, list);
	}

	public List<ShopsPlayOdds> queryGDKLSFRealOddsGroupByBall(String shopCode, String playType, String playType2) {
		return shopOddsDao.queryShopRealOddsGroupByBall(shopCode, playType, playType2);
	}

	public List<ShopsPlayOdds> queryHKRealOdds(String shopCode, String like) {
		// String hql =
		// "from ShopsPlayOdds where shopsCode=? and playTypeCode like 'HK%'";
		// Object[] parameter = new Object[] {shopCode};
		return shopOddsDao.queryShopRealOdds(shopCode, like);
		// return shopOddsDao.find(hql, parameter);

	}

	public List<ShopsPlayOdds> queryCQSSCOdds(String shopCode, String likeTypeCode) {
		String hql = "from ShopsPlayOdds where shopsCode=? and playTypeCode like '" + likeTypeCode + "%'";
		Object[] parameter = new Object[] { shopCode, likeTypeCode };

		return shopOddsDao.find(hql, parameter);
	}

	public List<ShopsPlayOdds> queryGDKLSFOdds(String shopCode, String likeTypeCode) {
		String hql = "from ShopsPlayOdds where shopsCode=? and playTypeCode like '" + likeTypeCode + "%'";
		Object[] parameter = new Object[] { shopCode };

		return shopOddsDao.find(hql, parameter);
	}

	public List<ShopsPlayOdds> queryHKOdds(String shopCode, String likeTypeCode) {
		String hql = "from ShopsPlayOdds where shopsCode=? and playTypeCode like '" + likeTypeCode + "%'";
		Object[] parameter = new Object[] { shopCode };

		return shopOddsDao.find(hql, parameter);
	}

	/*
	 * public List<ShopsPlayOdds> queryHKLMOdds(String shopCode) {
	 * 
	 * //String hql =
	 * "from ShopsPlayOdds where shopsCode=? and playTypeCode like 'HK_STRAIGHTTHROUGH%'"
	 * ; //Object[] parameter = new Object[] {shopCode}; return
	 * shopOddsDao.queryHKLMShopOdds(shopCode); //return shopOddsDao.find(hql,
	 * parameter);
	 * 
	 * 
	 * 
	 * }
	 */
	public List<ShopsPlayOdds> queryOddsByTypeCode(String shopCode, String typeCode) {

		return shopOddsDao.queryOddsByTypeCode(shopCode, typeCode);
		// String hql =
		// "from ShopsPlayOdds where shopsCode=? and playTypeCode= ?";
		// Object[] parameter = new Object[] {shopCode,typeCode};

		// return shopOddsDao.find(hql, parameter);

	}

	public OpenPlayOdds getOpenPlayOddsBy(String shopCode, String oddsType) {
		List<Criterion> filtersPlayType = new ArrayList<Criterion>();
		filtersPlayType.add(Restrictions.eq("shopsCode", shopCode));
		filtersPlayType.add(Restrictions.eq("oddsType", oddsType));
		OpenPlayOdds openOdds = openPlayOddsLogic.findOpenPlayOdds(filtersPlayType.toArray(new Criterion[filtersPlayType.size()]));
		return openOdds;
	}

	/**
	 * 修改重慶 和 香港、廣東
	 */
	public void updateShopOdds(List<ShopsPlayOdds> shopOdds) {
		if (null == shopOdds)
			return;
		OpenPlayOdds openOdds = null;
		ShopsPlayOdds persistent = null;
		for (int i = 0; i < shopOdds.size(); i++) {
			ShopsPlayOddsLog log = new ShopsPlayOddsLog();
			ShopsPlayOdds odds = shopOdds.get(i);
			String typeCode = odds.getPlayTypeCode();
			String shopCode = odds.getShopsCode();
			BigDecimal realOdds = odds.getRealOdds();
			persistent = queryShopPlayOdds(shopCode, typeCode);
			log.setRealOddsOrigin(persistent.getRealOdds());
			log.setRealUpdateUserOrigin(persistent.getRealUpdateUser().intValue());
			if (persistent != null) {
				openOdds = this.getOpenPlayOddsBy(shopCode, persistent.getOddsType());
				if (realOdds.compareTo(openOdds.getBigestOdds()) == 1) {
					realOdds = openOdds.getBigestOdds();
				}
				if (realOdds.compareTo(BigDecimal.ZERO) == -1) {
					realOdds = BigDecimal.ZERO;
				}

				persistent.setRealUpdateDate(new Date());
				persistent.setRealOdds(realOdds);
				persistent.setRealUpdateUser(odds.getRealUpdateUser());
				if (odds.getState() != null)
					persistent.setState(odds.getState());
				// BeanUtils.copyProperties(odds, persistent, new String[]
				// {"id","openingOdds",
				// "state","openingUpdateDate","openingUpdateUser"});
				shopOddsDao.update(persistent);

				try {
					if (log.getRealOddsOrigin().subtract(odds.getRealOdds()).doubleValue() != 0) {
						log.setShopCode(persistent.getShopsCode());
						log.setRealUpdateDateNew(new Date(System.currentTimeMillis()));
						log.setPlayTypeCode(persistent.getPlayTypeCode());
						log.setRealOddsNew(persistent.getRealOdds());
						log.setRealUpdateUserNew(Integer.valueOf(odds.getRealUpdateUser() + ""));
						log.setRealUpdateDateOrigin(persistent.getRealUpdateDate());
						log.setRemark("");
						log.setOddsType(persistent.getOddsType());
						log.setOddsTypeX(persistent.getOddsTypeX());
						log.setPeriodsNum(runningPeriodsNum);
						// 获取修改赔率者的IP地址
						String ip = WebTools.getClientIpAddr(getRequest());
						log.setIp(ip);
						log.setType("2");
						shopsPlayOddsLogLogic.saveLog(log);
					}
				} catch (Exception e) {
				}
			}
		}
	}

	public String getHKRunningPeriodsNum() {
		HKPeriodsInfo runningPeriods = skperiodsInfoLogic.queryByPeriodsStatus(Constant.OPEN_STATUS);
		return runningPeriods.getPeriodsNum();
	}

	/**
	 * 只有 香港 連碼 調用
	 */
	public void updateShopOddsByTypeX(List<ShopsPlayOdds> shopOdds) {
		if (null == shopOdds)
			return;
		OpenPlayOdds openOdds = null;
		for (int i = 0; i < shopOdds.size(); i++) {
			ShopsPlayOddsLog log = new ShopsPlayOddsLog();
			ShopsPlayOdds odds = shopOdds.get(i);
			String typeX = odds.getOddsTypeX();
			BigDecimal realOdds = odds.getRealOdds();
			String shopCode = odds.getShopsCode();
			ShopsPlayOdds persistent = queryShopPlayOddsByTypeX(shopCode, typeX);
			log.setRealOddsOrigin(persistent.getRealOdds());
			log.setRealUpdateUserOrigin(persistent.getRealUpdateUser().intValue());
			if (persistent != null) {
				String str = "";
				if ("HK_STRAIGHTTHROUGH_TC".equals(typeX)) {
					str = "HK_LM_TC";
				} else if ("HK_STRAIGHTTHROUGH_2QZ".equals(typeX)) {
					str = "HK_LM_2QZ";
				} else if ("HK_STRAIGHTTHROUGH_3QZ".equals(typeX)) {
					str = "HK_LM_SQZ";
				} else {
					str = typeX;
				}
				openOdds = this.getOpenPlayOddsBy(shopCode, str);
				if (realOdds.compareTo(openOdds.getBigestOdds()) == 1) {
					realOdds = openOdds.getBigestOdds();
				}
				if (realOdds.compareTo(BigDecimal.ZERO) == -1) {
					realOdds = BigDecimal.ZERO;
				}

				persistent.setRealUpdateDate(new Date());
				persistent.setRealOdds(realOdds);
				persistent.setRealUpdateUser(odds.getRealUpdateUser());
				if (odds.getState() != null)
					persistent.setState(odds.getState());
				shopOddsDao.update(persistent);
				try {
					if (log.getRealOddsOrigin().subtract(odds.getRealOdds()).doubleValue() != 0) {
						log.setShopCode(persistent.getShopsCode());
						log.setRealUpdateDateNew(new Date(System.currentTimeMillis()));
						log.setPlayTypeCode(persistent.getPlayTypeCode());
						log.setRealOddsNew(persistent.getRealOdds());
						log.setRealUpdateUserNew(Integer.valueOf(odds.getRealUpdateUser() + ""));
						log.setRealUpdateDateOrigin(persistent.getRealUpdateDate());
						log.setRemark("");
						log.setOddsType(persistent.getOddsType());
						log.setOddsTypeX(persistent.getOddsTypeX());
						log.setPeriodsNum(this.getHKRunningPeriodsNum());
						// 获取修改赔率者的IP地址
						String ip = WebTools.getClientIpAddr(getRequest());
						log.setIp(ip);
						log.setType("2");
						shopsPlayOddsLogLogic.saveLog(log);
					}
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * 只有 香港 調用
	 */
	public void updateShopOddsByTypeCodeTypeX(List<ShopsPlayOdds> shopOdds) {
		if (null == shopOdds)
			return;
		OpenPlayOdds openOdds = null;
		for (int i = 0; i < shopOdds.size(); i++) {
			ShopsPlayOddsLog log = new ShopsPlayOddsLog();
			ShopsPlayOdds odds = shopOdds.get(i);
			String typeX = odds.getOddsTypeX();
			String shopCode = odds.getShopsCode();
			BigDecimal realOdds = odds.getRealOdds();
			String playTypeCode = odds.getPlayTypeCode();
			ShopsPlayOdds persistent = queryShopPlayOddsByTypeCodeTypeX(shopCode, playTypeCode, typeX);
			log.setRealOddsOrigin(persistent.getRealOdds());
			log.setRealUpdateUserOrigin(persistent.getRealUpdateUser().intValue());
			if (persistent != null) {
				openOdds = this.getOpenPlayOddsBy(shopCode, persistent.getOddsType());
				if (realOdds.compareTo(openOdds.getBigestOdds()) == 1) {
					realOdds = openOdds.getBigestOdds();
				}
				if (realOdds.compareTo(BigDecimal.ZERO) == -1) {
					realOdds = BigDecimal.ZERO;
				}

				persistent.setRealUpdateDate(new Date());
				persistent.setRealOdds(realOdds);
				persistent.setRealUpdateUser(odds.getRealUpdateUser());
				if (odds.getState() != null)
					persistent.setState(odds.getState());
				shopOddsDao.update(persistent);

				try {
					if (log.getRealOddsOrigin().subtract(odds.getRealOdds()).doubleValue() != 0) {
						log.setShopCode(persistent.getShopsCode());
						log.setRealUpdateDateNew(new Date(System.currentTimeMillis()));
						log.setPlayTypeCode(persistent.getPlayTypeCode());
						log.setRealOddsNew(persistent.getRealOdds());
						log.setRealUpdateUserNew(Integer.valueOf(odds.getRealUpdateUser() + ""));
						log.setRealUpdateDateOrigin(persistent.getRealUpdateDate());
						log.setRemark("");
						log.setOddsType(persistent.getOddsType());
						log.setOddsTypeX(persistent.getOddsTypeX());
						log.setPeriodsNum(this.getHKRunningPeriodsNum());
						// 获取修改赔率者的IP地址
						String ip = WebTools.getClientIpAddr(getRequest());
						log.setIp(ip);
						log.setType("2");
						shopsPlayOddsLogLogic.saveLog(log);
					}
				} catch (Exception e) {
				}

			}

		}
	}

	public ShopsPlayOdds queryShopPlayOdds(String shopsCode, String playTypeCode) {
		String hql = "from ShopsPlayOdds where playTypeCode=? and shopsCode=?";
		Object[] parameter = new Object[] { playTypeCode, shopsCode };
		return shopOddsDao.findUnique(hql, parameter);

	}

	public ShopsPlayOdds queryPlateRealShopPlayOdds(String shopsCode, String playTypeCode, String plate) {

		// Object[] parameter = new Object[] {playTypeCode,shopsCode};
		ShopsPlayOdds shopOdds = null;
		List<ShopsPlayOdds> shopOddslist = shopOddsDao.queryOddsByTypeCode(shopsCode, playTypeCode);
		if (shopOddslist != null && shopOddslist.size() > 0) {
			shopOdds = shopOddslist.get(0);
			if ("B".equals(plate))
				shopOdds.setRealOdds(shopOdds.getRealOddsB());
			else if ("C".equals(plate))
				shopOdds.setRealOdds(shopOdds.getRealOddsC());
		}

		return shopOdds;

	}
	
	@Override
	public Map<String, BigDecimal> queryPlateRealOddsMap(String shopsCode, String playTypeCode, String plate) {

		Map<String, BigDecimal> resultMap = new HashMap<>();
		List<ShopsPlayOdds> shopOddslist = shopOddsDao.queryShopRealOdds(shopsCode, playTypeCode);
		for (ShopsPlayOdds shopsPlayOdds : shopOddslist) {
			if ("B".equals(plate)) {
				resultMap.put(shopsPlayOdds.getPlayTypeCode(), shopsPlayOdds.getRealOddsB());
			} else if ("C".equals(plate)) {
				resultMap.put(shopsPlayOdds.getPlayTypeCode(), shopsPlayOdds.getRealOddsC());
			} else {
				resultMap.put(shopsPlayOdds.getPlayTypeCode(), shopsPlayOdds.getRealOdds());
			}
		}

		return resultMap;

	}

	@Override
	public Map<String, ShopsPlayOdds> getCurrentShopRealOddsAndId(String shopCode, String playType) {
		Map<String, ShopsPlayOdds> playOddsMap = new HashMap<String, ShopsPlayOdds>();
		List<ShopsPlayOdds> playOddsList = shopOddsDao.getCurrentShopRealOddsAndId(shopCode, playType);
		for (ShopsPlayOdds shopsPlayOdds : playOddsList) {
			playOddsMap.put(shopsPlayOdds.getPlayTypeCode(), shopsPlayOdds);
		}
		return playOddsMap;
	}

	@Override
	public List<ShopsPlayOdds> queryShopPlayOddsListByTypeCode(String playTypeCode, String shopCode) {
		String hql = "from ShopsPlayOdds where playTypeCode=? and shopsCode=?";
		return shopOddsDao.find(hql, playTypeCode, shopCode);
	}

	@Override
	public List<ShopsPlayOdds> queryShopPlayOddsListByLike(String playTypeCode) {
		String hql = "from ShopsPlayOdds where playTypeCode like '" + playTypeCode + "'  escape '\\'";
		Object[] parameter = new Object[] {};
		return shopOddsDao.find(hql, parameter);
	}

	/**
	 * 香港 連碼 設置賠率
	 */
	public ShopsPlayOdds queryShopPlayOddsByTypeX(String shopsCode, String typeX) {
		String hql = "from ShopsPlayOdds where oddsTypeX=? and shopsCode=?";
		Object[] parameter = new Object[] { typeX, shopsCode };
		return shopOddsDao.findUnique(hql, parameter);

	}

	public ShopsPlayOdds queryShopPlayOddsByTypeCodeTypeX(String shopsCode, String playTypeCode, String typeX) {
		String hql = "from ShopsPlayOdds where oddsTypeX=? and shopsCode=? and playTypeCode=?";
		Object[] parameter = new Object[] { typeX, shopsCode, playTypeCode };
		return shopOddsDao.findUnique(hql, parameter);

	}

	public void updateShopOdds(ShopsPlayOdds shopOdds) {
		shopOddsDao.save(shopOdds);

	}

	@Override
	public void updateShopOddsByObj(ShopsPlayOdds shopOdds) {
		// saveShopsPlayOddsLog(shopOdds);
		// shopOddsDao.save(shopOdds);

		logger.info("更新 ShopsPlayOdds id:" + shopOdds.getId() + " || realOdds:" + shopOdds.getRealOdds() + " || shopCode:" + shopOdds.getShopsCode());
		int a = shopOddsDao.batchExecute(" update ShopsPlayOdds set realOdds=? where id=? ", shopOdds.getRealOdds(), shopOdds.getId());

	}

	public void saveShopsPlayOddsLog(ShopsPlayOdds shopOdds) {
		try {
			ShopsPlayOddsLog log = new ShopsPlayOddsLog();
			ShopsPlayOdds odds = shopOdds;
			String typeCode = odds.getPlayTypeCode();
			String shopCode = odds.getShopsCode();
			// BigDecimal realOdds=odds.getRealOdds();
			ShopsPlayOdds persistent = queryShopPlayOdds(shopCode, typeCode);
			log.setRealOddsOrigin(persistent.getRealOdds());
			log.setRealUpdateUserOrigin(persistent.getRealUpdateUser().intValue());

			BJSCPeriodsInfo bjPeriods = new BJSCPeriodsInfo();
			GDPeriodsInfo gdPeriods = new GDPeriodsInfo();
			CQPeriodsInfo cqPeriods = new CQPeriodsInfo();
			String periodsNum = "";

			if (odds.getPlayTypeCode().indexOf("GDKLSF") != -1) {
				gdPeriods = periodsInfoLogic.queryLastLotteryPeriods();
				periodsNum = gdPeriods.getPeriodsNum();
			} else if (odds.getPlayTypeCode().indexOf("CQSSC") != -1) {
				cqPeriods = icqPeriodsInfoLogic.queryLastLotteryPeriods();
				periodsNum = cqPeriods.getPeriodsNum();
			} else if (odds.getPlayTypeCode().indexOf("BJ") != -1) {
				bjPeriods = bjscPeriodsInfoLogic.queryLastLotteryPeriods();
				periodsNum = bjPeriods.getPeriodsNum();
			}

			// if(log.getRealOddsOrigin().subtract(odds.getRealOdds()).doubleValue()!=0)
			{
				log.setShopCode(odds.getShopsCode());
				log.setRealUpdateDateNew(new Date(System.currentTimeMillis()));
				log.setPlayTypeCode(odds.getPlayTypeCode());
				log.setRealOddsNew(odds.getRealOdds());
				log.setRealUpdateUserNew(Integer.valueOf(odds.getRealUpdateUser() + ""));
				log.setRealUpdateDateOrigin(persistent.getRealUpdateDate());
				log.setRemark("");
				log.setOddsType(odds.getOddsType());
				log.setOddsTypeX(odds.getOddsTypeX());
				log.setPeriodsNum(periodsNum);
				// 获取修改赔率者的IP地址
				String ip = WebTools.getClientIpAddr(getRequest());
				log.setIp(ip);
				log.setType("1");
				shopsPlayOddsLogLogic.saveLog(log);
				// shopsPlayOddsLogDao.save(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateShopOddsState(String state, String shopsCode, String oddsType) {
		String hql = "update ShopsPlayOdds set state=?  where  playTypeCode like  'HK_TM\\_%\\_" + oddsType + "' ESCAPE '\\' and shopsCode=?";
		Object[] parameter = new Object[] { state, shopsCode };
		shopOddsDao.batchExecute(hql, parameter);
	}

	public void updateShopOddsStateZMA(String state, String shopsCode) {
		String hql = "update ShopsPlayOdds set state=?  where  playTypeCode like 'HK_ZM\\_%' ESCAPE '\\' and shopsCode=?";
		Object[] parameter = new Object[] { state, shopsCode };
		shopOddsDao.batchExecute(hql, parameter);
	}

	public void updateShopOddsStateZT(String state, String shopsCode, String ballNum) {
		String hql = "update ShopsPlayOdds set state=?  where  playTypeCode like 'HK_ZT\\_%_Z" + ballNum + "' ESCAPE '\\' and shopsCode=?";
		Object[] parameter = new Object[] { state, shopsCode };
		shopOddsDao.batchExecute(hql, parameter);
	}

	// 封号
	public void updateFengHao(String state, String typeCode, String shopsCode) {
		String hql = "update ShopsPlayOdds set state=?  where  playTypeCode = ? and shopsCode=?";
		Object[] parameter = new Object[] { state, typeCode, shopsCode };
		shopOddsDao.batchExecute(hql, parameter);
	}

	// 下降規則：19号球是9期没出,付合条件,下降赔率0.4
	// 那么同路下降比例10,(10%*0.4=0.04),
	@Override
	public void updateShopOddsRealOddsByPlayTypeCode(String playTypeCode, BigDecimal realOdds, String shopsCode) {
		String hql = "update ShopsPlayOdds set  realOdds = decode(sign(realOdds - ?),1,realOdds - ?,0) where  playTypeCode =? and shopsCode=? ";
		Object[] parameter = new Object[] { realOdds, realOdds, playTypeCode, shopsCode };

		shopOddsDao.batchExecute(hql, parameter);
	}

	public List<OpenPlayOdds> queryCQSSCOpenOdds(String shopsCode) {

		String hql = "from OpenPlayOdds where shopsCode=? and oddsType like 'CQSSC%'";
		Object[] parameter = new Object[] { shopsCode };

		return openPlayOddsDao.find(hql, parameter);

	}

	public List<OpenPlayOdds> queryBJSCOpenOdds(String shopsCode) {

		String hql = "from OpenPlayOdds where shopsCode=? and oddsType like 'BJ%'";
		Object[] parameter = new Object[] { shopsCode };

		return openPlayOddsDao.find(hql, parameter);

	}

	public List<OpenPlayOdds> queryK3OpenOdds(String shopsCode) {

		String hql = "from OpenPlayOdds where shopsCode=? and oddsType like 'K3%'";
		Object[] parameter = new Object[] { shopsCode };

		return openPlayOddsDao.find(hql, parameter);

	}

	public List<OpenPlayOdds> queryGDKLSFOpenOdds(String shopsCode) {
		String hql = "from OpenPlayOdds where shopsCode=? and oddsType like 'GDKLSF%'";
		Object[] parameter = new Object[] { shopsCode };

		return openPlayOddsDao.find(hql, parameter);

	}

	public List<OpenPlayOdds> queryNCOpenOdds(String shopsCode) {
		String hql = "from OpenPlayOdds where shopsCode=? and oddsType like 'NC%'";
		Object[] parameter = new Object[] { shopsCode };

		return openPlayOddsDao.find(hql, parameter);

	}

	public IShopsDao getShopsDao() {
		return shopsDao;
	}

	public void setShopsDao(IShopsDao shopsDao) {
		this.shopsDao = shopsDao;
	}

	public List<OpenPlayOdds> queryHKOpenOdds(String shopsCode) {
		String hql = "from OpenPlayOdds where shopsCode=? and oddsType like 'HK%'";
		Object[] parameter = new Object[] { shopsCode };

		return openPlayOddsDao.find(hql, parameter);
	}

	public void updateGdOpenOdds(List<OpenPlayOdds> list, Long userID, String shopsCode) {
		Date nowDate = new Date();
		for (int i = 0; i < list.size(); i++) {
			OpenPlayOdds obj = list.get(i);
			obj.setOpeningUpdateUser(userID);
			obj.setOpeningUpdateDate(nowDate);
			obj.setShopsCode(shopsCode);
			openPlayOddsDao.save(obj);
		}
	}

	public void updateBjOpenOdds(List<OpenPlayOdds> list, Long userID, String shopsCode) {
		Date nowDate = new Date();
		for (int i = 0; i < list.size(); i++) {
			OpenPlayOdds obj = list.get(i);
			obj.setOpeningUpdateUser(userID);
			obj.setOpeningUpdateDate(nowDate);
			obj.setShopsCode(shopsCode);
			openPlayOddsDao.save(obj);
		}
	}

	public void updateK3OpenOdds(List<OpenPlayOdds> list, Long userID, String shopsCode) {
		Date nowDate = new Date();
		OpenPlayOdds objDS = list.get(4);
		for (int i = 0; i < list.size(); i++) {
			OpenPlayOdds obj = list.get(i);

			if ("K3_DS_5".equals(obj.getOddsType()) || "K3_DS_6".equals(obj.getOddsType()) || "K3_DS_7".equals(obj.getOddsType()) || "K3_DS_8".equals(obj.getOddsType())
			        || "K3_DS_9".equals(obj.getOddsType())) {
				obj.setBigestOdds(objDS.getBigestOdds());
				obj.setCutOddsB(objDS.getCutOddsB());
				obj.setCutOddsC(objDS.getCutOddsC());
				obj.setAutoOddsQuotas(objDS.getAutoOddsQuotas());
				obj.setAutoOdds(objDS.getAutoOdds());
			}
			obj.setOpeningUpdateUser(userID);
			obj.setOpeningUpdateDate(nowDate);
			obj.setShopsCode(shopsCode);
			openPlayOddsDao.save(obj);
		}
	}

	public void updateCqOpenOdds(List<OpenPlayOdds> frmList, Long userID, String shopsCode) {
		Date nowDate = new Date();
		List<OpenPlayOdds> list = queryCQSSCOpenOdds(shopsCode);
		for (int i = 0; i < list.size(); i++) {
			OpenPlayOdds obj = list.get(i);
			OpenPlayOdds frmObj = new OpenPlayOdds();
			if (obj.getOddsType().equals("CQSSC_BALL_FIRST")) {
				frmObj = frmList.get(0);
			}
			if (obj.getOddsType().equals("CQSSC_BALL_SECOND")) {
				frmObj = frmList.get(1);
			}
			if (obj.getOddsType().equals("CQSSC_BALL_THIRD")) {
				frmObj = frmList.get(2);
			}
			if (obj.getOddsType().equals("CQSSC_BALL_FORTH")) {
				frmObj = frmList.get(3);
			}
			if (obj.getOddsType().equals("CQSSC_BALL_FIFTH")) {
				frmObj = frmList.get(4);
			}
			if (obj.getOddsType().equals("CQSSC_1-5_DX")) {
				frmObj = frmList.get(5);
			}
			if (obj.getOddsType().equals("CQSSC_1-5_DS")) {
				frmObj = frmList.get(6);
			}
			if (obj.getOddsType().equals("CQSSC_ZHDX")) {
				frmObj = frmList.get(7);
			}
			if (obj.getOddsType().equals("CQSSC_ZHDS")) {
				frmObj = frmList.get(8);
			}
			if (obj.getOddsType().equals("CQSSC_DOUBLESIDE_LH")) {
				frmObj = frmList.get(9);
			}
			if (obj.getOddsType().equals("CQSSC_DOUBLESIDE_HE")) {
				BeanUtils.copyProperties(frmList.get(9), frmObj); // 取龙虎的资料填充
				                                                  // ,排序看OddsAction.initCQSSCLotOpenOddsData方法
				frmObj.setOpeningOdds(frmList.get(10).getOpeningOdds()); // 在页面上看到龙虎和在LIST里只差openingOdds
			}
			// 前三
			if (obj.getOddsType().equals("CQSSC_BZ_FRONT")) {
				frmObj = frmList.get(11);
			}
			if (obj.getOddsType().equals("CQSSC_SZ_FRONT")) { // 其他几个子的除了开盘赔率外的数据，都从FRONT取
				BeanUtils.copyProperties(frmList.get(11), frmObj);
				frmObj.setOpeningOdds(frmList.get(12).getOpeningOdds());
			}
			if (obj.getOddsType().equals("CQSSC_DZ_FRONT")) {
				BeanUtils.copyProperties(frmList.get(11), frmObj);
				frmObj.setOpeningOdds(frmList.get(13).getOpeningOdds());
			}
			if (obj.getOddsType().equals("CQSSC_BS_FRONT")) {
				BeanUtils.copyProperties(frmList.get(11), frmObj);
				frmObj.setOpeningOdds(frmList.get(14).getOpeningOdds());
			}
			if (obj.getOddsType().equals("CQSSC_ZL_FRONT")) {
				BeanUtils.copyProperties(frmList.get(11), frmObj);
				frmObj.setOpeningOdds(frmList.get(15).getOpeningOdds());
			}
			// 中三
			if (obj.getOddsType().equals("CQSSC_BZ_MID")) {
				BeanUtils.copyProperties(frmList.get(16), frmObj);
				frmObj.setOpeningOdds(frmList.get(11).getOpeningOdds());
			}
			if (obj.getOddsType().equals("CQSSC_SZ_MID")) {
				BeanUtils.copyProperties(frmList.get(16), frmObj);
				frmObj.setOpeningOdds(frmList.get(12).getOpeningOdds());
			}
			if (obj.getOddsType().equals("CQSSC_DZ_MID")) {
				BeanUtils.copyProperties(frmList.get(16), frmObj);
				frmObj.setOpeningOdds(frmList.get(13).getOpeningOdds());
			}
			if (obj.getOddsType().equals("CQSSC_BS_MID")) {
				BeanUtils.copyProperties(frmList.get(16), frmObj);
				frmObj.setOpeningOdds(frmList.get(14).getOpeningOdds());
			}
			if (obj.getOddsType().equals("CQSSC_ZL_MID")) {
				BeanUtils.copyProperties(frmList.get(16), frmObj);
				frmObj.setOpeningOdds(frmList.get(15).getOpeningOdds());
			}
			// 后三
			if (obj.getOddsType().equals("CQSSC_BZ_LAST")) {
				BeanUtils.copyProperties(frmList.get(17), frmObj);
				frmObj.setOpeningOdds(frmList.get(11).getOpeningOdds());
			}
			if (obj.getOddsType().equals("CQSSC_SZ_LAST")) {
				BeanUtils.copyProperties(frmList.get(17), frmObj);
				frmObj.setOpeningOdds(frmList.get(12).getOpeningOdds());
			}
			if (obj.getOddsType().equals("CQSSC_DZ_LAST")) {
				BeanUtils.copyProperties(frmList.get(17), frmObj);
				frmObj.setOpeningOdds(frmList.get(13).getOpeningOdds());
			}
			if (obj.getOddsType().equals("CQSSC_BS_LAST")) {
				BeanUtils.copyProperties(frmList.get(17), frmObj);
				frmObj.setOpeningOdds(frmList.get(14).getOpeningOdds());
			}
			if (obj.getOddsType().equals("CQSSC_ZL_LAST")) {
				BeanUtils.copyProperties(frmList.get(17), frmObj);
				frmObj.setOpeningOdds(frmList.get(15).getOpeningOdds());
			}
			// 目前只有‘龙虎和’，‘前三’，‘中三’，‘后三‘需要做特殊处理;

			obj.setOpeningOdds(frmObj.getOpeningOdds());
			obj.setBigestOdds(frmObj.getBigestOdds());
			obj.setCutOddsB(frmObj.getCutOddsB());
			obj.setCutOddsC(frmObj.getCutOddsC());
			obj.setAutoOddsQuotas(frmObj.getAutoOddsQuotas());
			obj.setAutoOdds(frmObj.getAutoOdds());
			obj.setOpeningUpdateDate(nowDate);
			obj.setOpeningUpdateUser(userID);
			openPlayOddsDao.save(obj);
		}
	}

	public IOpenPlayOddsJdbcDao getOpenPlayOddsJdbcDao() {
		return openPlayOddsJdbcDao;
	}

	public void setOpenPlayOddsJdbcDao(IOpenPlayOddsJdbcDao openPlayOddsJdbcDao) {
		this.openPlayOddsJdbcDao = openPlayOddsJdbcDao;
	}

	/**
	 * 根据总监查询商铺号
	 * 
	 * @param entity
	 */
	public String getCurrentShopsCode() {
		ManagerUser userInfo = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		Long userID = userInfo.getID();
		List<ManagerStaff> cList = managerStaffDao.findShopsCodeByChief(userID);
		String shopsCode = cList.get(0).getChiefStaffExt().getShopsCode();
		return shopsCode;
	}

	@Override
	public List<ChiefStaffExt> findChiefByShopsCode(String shopsCode) {
		List<ChiefStaffExt> cList = managerStaffDao.findChiefByShopsCode(shopsCode);
		Long chiefID = cList.get(0).getManagerStaffID();
		return cList;
	}

	@Override
	public void updateRealOddsFromOpenOddsForGD(String type) {
		if (type.equals("GD") || type.equals("CQ") || type.equals("BJ") || type.equals("K3") || type.equals("NC")) {
			logger.info("开始执行把实时赔率更新为开盘赔率" + type);
			List<String> shops = shopsDao.findAllShopsCode();
			Iterator<String> it = shops.iterator();
			while (it.hasNext()) {
				String shopsCode = it.next();
				if (type.equals("GD")) {
					openPlayOddsJdbcDao.updateRealOddsFromOpenOdds(shopsCode, "GD");
				} else if (type.equals("CQ")) {
					openPlayOddsJdbcDao.updateRealOddsFromOpenOdds(shopsCode, "CQ");
				} else if (type.equals("BJ")) {
					openPlayOddsJdbcDao.updateRealOddsFromOpenOdds(shopsCode, "BJ");
				} else if (type.equals("K3")) {
					openPlayOddsJdbcDao.updateRealOddsFromOpenOdds(shopsCode, "K3");
				}
				// add by peter for 幸运农场
				else if (type.equals("NC")) {
					openPlayOddsJdbcDao.updateRealOddsFromOpenOdds(shopsCode, "NC");
				}

			}
			logger.info("结束执行把实时赔率更新为开盘赔率" + type);
		}

	}

	@Override
	public void updateRealOddsFromOpenOddsForHK(String shopCode) {
		openPlayOddsJdbcDao.updateRealOddsFromOpenOdds(shopCode, "HK");
	}

	public IShopOddsDao getShopOddsDao() {
		return shopOddsDao;
	}

	public void setShopOddsDao(IShopOddsDao shopOddsDao) {
		this.shopOddsDao = shopOddsDao;
	}

	public IOpenPlayOddsDao getOpenPlayOddsDao() {
		return openPlayOddsDao;
	}

	public void setOpenPlayOddsDao(IOpenPlayOddsDao openPlayOddsDao) {
		this.openPlayOddsDao = openPlayOddsDao;
	}

	public IManagerStaffDao getManagerStaffDao() {
		return managerStaffDao;
	}

	public void setManagerStaffDao(IManagerStaffDao managerStaffDao) {
		this.managerStaffDao = managerStaffDao;
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public OpenPlayOdds queryOpenPlayOdds(String oddType, String shopCode) {
		String hql = "select a from OpenPlayOdds a  where a.oddsType=? and a.shopsCode=?";
		Object[] parameter = new Object[] { oddType, shopCode };
		return openPlayOddsDao.findUnique(hql, parameter);

	}
	
	@Override
	public Map<String, OpenPlayOdds> queryOpenPlayOddsMapByShopCode(String shopCode) {
		Map<String, OpenPlayOdds> map = new HashMap<String, OpenPlayOdds>();
		String hql = "from OpenPlayOdds a  where a.shopsCode=?";
		List<OpenPlayOdds> list = openPlayOddsDao.find(hql, shopCode);
		for (OpenPlayOdds openPlayOdds : list) {
			map.put(openPlayOdds.getOddsType(), openPlayOdds);
		}
		return map;

	}

	public IShopsPlayOddsLogLogic getShopsPlayOddsLogLogic() {
		return shopsPlayOddsLogLogic;
	}

	public void setShopsPlayOddsLogLogic(IShopsPlayOddsLogLogic shopsPlayOddsLogLogic) {
		this.shopsPlayOddsLogLogic = shopsPlayOddsLogLogic;
	}

	public IHKPeriodsInfoLogic getSkperiodsInfoLogic() {
		return skperiodsInfoLogic;
	}

	public void setSkperiodsInfoLogic(IHKPeriodsInfoLogic skperiodsInfoLogic) {
		this.skperiodsInfoLogic = skperiodsInfoLogic;
	}

	@Override
	public void setRunningPeriodsNum(String runningPeriodsNum) {
		this.runningPeriodsNum = runningPeriodsNum;
	}

	public IOpenPlayOddsLogic getOpenPlayOddsLogic() {
		return openPlayOddsLogic;
	}

	public void setOpenPlayOddsLogic(IOpenPlayOddsLogic openPlayOddsLogic) {
		this.openPlayOddsLogic = openPlayOddsLogic;
	}

	public IShopsPlayOddsLogDao getShopsPlayOddsLogDao() {
		return shopsPlayOddsLogDao;
	}

	public void setShopsPlayOddsLogDao(IShopsPlayOddsLogDao shopsPlayOddsLogDao) {
		this.shopsPlayOddsLogDao = shopsPlayOddsLogDao;
	}

	public IBJSCPeriodsInfoLogic getBjscPeriodsInfoLogic() {
		return bjscPeriodsInfoLogic;
	}

	public void setBjscPeriodsInfoLogic(IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic) {
		this.bjscPeriodsInfoLogic = bjscPeriodsInfoLogic;
	}

	public IGDPeriodsInfoLogic getPeriodsInfoLogic() {
		return periodsInfoLogic;
	}

	public ICQPeriodsInfoLogic getIcqPeriodsInfoLogic() {
		return icqPeriodsInfoLogic;
	}

	public void setPeriodsInfoLogic(IGDPeriodsInfoLogic periodsInfoLogic) {
		this.periodsInfoLogic = periodsInfoLogic;
	}

	public void setIcqPeriodsInfoLogic(ICQPeriodsInfoLogic icqPeriodsInfoLogic) {
		this.icqPeriodsInfoLogic = icqPeriodsInfoLogic;
	}

	@Override
	public List<ShopsPlayOdds> getStopShopsPlayOddsByShopCodeAndPlayTypePerfix(String shopCode, String playTypePerfix) {
		return shopOddsDao.getStopShopsPlayOddsByShopCodeAndPlayTypePerfix(shopCode, playTypePerfix);
	}

	@Override
	public void updateRealOddsBatchById(final List<ShopsPlayOdds> shopsPlayOddsList) {
		shopOddsDao.updateRealOddsBatchById(shopsPlayOddsList);
	}

	@Override
	public List<ChiefStaffExt> findChiefByShopsCodeByScheme(String shopsCode, String scheme) {
		return managerStaffDao.findChiefByShopsCodeByScheme(shopsCode, scheme);
	}

}
