package com.npc.lottery.service;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineMemberService {

	@Autowired
	private MemCachedService memCachedService;

	/**
	 * 管理在缓存里获取在线会员列表,如果缓存里的时间在当前1个小时之前,视为过期,从map里移除并更新缓存
	 * @param shopCode
	 * @return
	 */
	public Map<String, Date> getOnlineMember(String shopCode) {
		Map<String, Date> onlineMemberMap = memCachedService.getOnlinMember(shopCode);
		boolean needUpdate = false;
		for (Entry<String, Date> entry : onlineMemberMap.entrySet()) {
			Date onlineDate = entry.getValue();
			// 如果统计日期是在当前时间1个小时之前的,视为过期
			if (onlineDate.before(DateUtils.addHours(new Date(), -1))) {
				onlineMemberMap.remove(entry.getKey());
				needUpdate = true;
			}
		}
		if (needUpdate) {
			memCachedService.setOnlinMember(onlineMemberMap, shopCode);
		}
		return onlineMemberMap;
	}
}
