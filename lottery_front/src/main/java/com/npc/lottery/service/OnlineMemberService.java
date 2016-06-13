package com.npc.lottery.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineMemberService {

	@Autowired
	private MemCachedService memCachedService;

	public void updateOnlineMemberDate(String uid, String shopCode) {
		Map<String, Date> onlineMemberMap = memCachedService.getOnlinMember(shopCode);
		onlineMemberMap.put(uid, new Date());
		memCachedService.setOnlinMember(onlineMemberMap, shopCode);
	}

	public void logoutOnlineMemberInCache(String uid, String shopCode) {
		Map<String, Date> onlineMemberMap = memCachedService.getOnlinMember(shopCode);
		if (onlineMemberMap.containsKey(uid)) {
			onlineMemberMap.remove(uid);
			memCachedService.setOnlinMember(onlineMemberMap, shopCode);
		}
	}
}
