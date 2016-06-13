package com.npc.lottery.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.dao.UserFrontDao;
import com.npc.lottery.sysmge.entity.LoginLogInfo;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.MemberStaffExt;

@Service
public class UserService {
	@Autowired
	private UserFrontDao userFrontDao;

	private final static Logger log = Logger.getLogger(UserService.class);

	public MemberUser getMemberUserByName(String account, String scheme) {
		MemberStaff memberStaff = userFrontDao.getMemberUserStaffByName(account, scheme);
		return new MemberUser(memberStaff);
	}

	public MemberStaffExt getMemberStaffExtById(long userId, String scheme) {
		return userFrontDao.getMemberStaffExtById(userId, scheme);
	}

	public ChiefStaffExt getChiefStaffExtById(long chiefId, String scheme) {
		return userFrontDao.getChiefStaffExtById(chiefId, scheme);

	}

	public MemberStaff getMemberUserStaffById(long userId, String scheme) {
		return userFrontDao.getMemberUserStaffById(userId, scheme);
	}

	public MemberUser getMemberUserById(long userId, String scheme) {

		MemberStaff memberStaff = userFrontDao.getMemberUserStaffById(userId, scheme);
		MemberUser user = new MemberUser(memberStaff);
		MemberStaffExt memberStaffExt = userFrontDao.getMemberStaffExtById(userId, scheme);

		if (null != memberStaffExt) {
			user.setMemberStaffExt(memberStaffExt);
			ChiefStaffExt chiefStaffExt = userFrontDao.getChiefStaffExtById(memberStaffExt.getChiefStaff(), scheme);
			if (null != chiefStaffExt) {
				ShopsInfo shopsInfo = this.getShopInfoByChiefStaffExt(chiefStaffExt);
				user.setShopsInfo(shopsInfo);
			}

		}
		return user;
	}

	public String getMemberFlagById(long userId, String scheme) {
		return userFrontDao.getMemberFlagById(userId, scheme);
	}

	public void saveLoginLogInfo(LoginLogInfo logLogInfo, String scheme) {
		userFrontDao.saveLoginLogInfo(logLogInfo, scheme);
	}

	public void updateMemberStaffPasswordById(long userId, String newPassword, String scheme) {
		userFrontDao.updateMemberStaffPasswordById(userId, newPassword, scheme);
	}

	public void updateMemberAvailableCreditLineById(double availableCreditLine, long id, String scheme) {
		userFrontDao.updateMemberAvailableCreditLineById(availableCreditLine, id, scheme);
	}

	/**
	 * 获取会员整条线的占成数据 待实现
	 * 
	 * @param userId
	 * @param scheme
	 * @return
	 */
	public Map<String, Integer> getUserRateLine(MemberStaffExt memberStaff, String scheme) {
		// 找出改会员直接上级的类型
		String memberParentType = memberStaff.getParentUserType();
		long branchId = memberStaff.getBranchStaff();
		// 通过branchId查询余数占成归属 0总监 1 分公司
		String leftOwner = userFrontDao.getLeftOwnerByBranchId(branchId, scheme);
		// 找出会员线上的占成
		Map<String, Integer> userRateLineMap = userFrontDao.getUserLineRateMapByUserId(memberStaff.getID(), scheme);

		int chiefRate = 0;
		int branchRate = 0;
		int stockRate = 0;
		int genAgentRate = 0;
		int agentRate = 0;
		// 上级是分公司
		if (ManagerStaff.USER_TYPE_BRANCH.equals(memberParentType)) {
			chiefRate = userRateLineMap.get("CHIEF_RATE");
			branchRate = userRateLineMap.get("RATE");
		}
		// 上级是股东
		else if (ManagerStaff.USER_TYPE_STOCKHOLDER.equals(memberParentType)) {
			chiefRate = userRateLineMap.get("CHIEF_RATE");
			branchRate = userRateLineMap.get("BRANCH_RATE");
			stockRate = userRateLineMap.get("RATE");
		}
		// 上级是总代理
		else if (ManagerStaff.USER_TYPE_GEN_AGENT.equals(memberParentType)) {
			chiefRate = userRateLineMap.get("CHIEF_RATE");
			branchRate = userRateLineMap.get("BRANCH_RATE");
			stockRate = userRateLineMap.get("STOCK_RATE");
			genAgentRate = userRateLineMap.get("RATE");
		}
		// 上级是代理
		else if (ManagerStaff.USER_TYPE_AGENT.equals(memberParentType)) {
			chiefRate = userRateLineMap.get("CHIEF_RATE");
			branchRate = userRateLineMap.get("BRANCH_RATE");
			stockRate = userRateLineMap.get("STOCK_RATE");
			genAgentRate = userRateLineMap.get("GEN_AGENT_RATE");
			agentRate = userRateLineMap.get("RATE");
		}

		int left = 100 - (chiefRate + branchRate + stockRate + genAgentRate + agentRate);
		if (left > 0) {
			if ("0".equals(leftOwner)) {
				// 归总监
				chiefRate = chiefRate + left;
			} else if ("1".equals(leftOwner)) {
				// 归分公司
				branchRate = branchRate + left;
			}
		}
		Map<String, Integer> rateMap = new HashMap<String, Integer>();

		rateMap.put("agentRate", agentRate);
		rateMap.put("genAgentRate", genAgentRate);
		rateMap.put("shareholderRate", stockRate);
		rateMap.put("branchRate", branchRate);
		rateMap.put("chiefRate", chiefRate);
		return rateMap;
	}

	public ShopsInfo getShopInfoByChiefStaffExt(ChiefStaffExt chiefStaffExt) {
		return userFrontDao.getShopInfoByChiefStaffExt(chiefStaffExt);
	}

	public ShopsInfo getShopInfoByShopCode(String shopCode) {
		return userFrontDao.getShopInfoByShopCode(shopCode);
	}
}
