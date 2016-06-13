package com.npc.lottery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npc.lottery.dao.CommissionFrontDao;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.entity.UserCommissionDefault;

@Service
public class CommissionService {
	@Autowired
	private CommissionFrontDao commissionFrontDao;

	public List<UserCommissionDefault> getDefaultCommissionListByPlayFinalTypeLike(String playFinalTypeLike, Long chiefId) {
		return commissionFrontDao.getDefaultCommissionListByPlayFinalTypeLike(playFinalTypeLike, chiefId);
	}

	public UserCommissionDefault getDefaultCommissionByPlayFinalType(String playFinalType, Long chiefId) {
		return commissionFrontDao.getDefaultCommissionByPlayFinalType(playFinalType, chiefId);
	}

	public List<UserCommission> getUserCommissionListByPlayFinalTypeLike(String playFinalTypeLike, Long userId, String scheme) {
		return commissionFrontDao.getUserCommissionListByPlayFinalTypeLike(playFinalTypeLike, userId, scheme);
	}

	public UserCommission getUserCommissionByPlayFinalType(String playFinalType, Long userId, String scheme) {

		return commissionFrontDao.getUserCommissionByPlayFinalType(playFinalType, userId, scheme);
	}

	public List<UserCommission> getUserCommissionListByPlayFinalTypeAndIds(String playFinalType, List<Long> userIdList, String scheme) {
		return commissionFrontDao.getUserCommissionListByPlayFinalTypeAndIds(playFinalType, userIdList, scheme);
	}

	public List<UserCommission> getUserCommissionListByUserId(Long userId, String scheme) {
		return commissionFrontDao.getUserCommissionListByUserId(userId, scheme);
	}
}
