package com.npc.lottery.common.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npc.lottery.common.dao.ShopSchemeDao;
import com.npc.lottery.service.MemCachedService;

@Service
public class ShopSchemeService {

	@Autowired
	private ShopSchemeDao shopSchemeDao;

	@Autowired
	private MemCachedService memCachedService;

	public Map<String, String> getShopSchemeMap() {
		Map<String, String> map = shopSchemeDao.getShopSchemeMap();
		return MapUtils.isEmpty(map) ? new HashMap<String, String>() : map;
	}

	public String getSchemeByShopCode(String shopCode) {
		String scheme = memCachedService.getShopSchemeByShopCode(shopCode);
		if (StringUtils.isEmpty(scheme)) {
			scheme = shopSchemeDao.getSchemeByShopCode(shopCode);
			if (StringUtils.isNotEmpty(scheme)) {
				memCachedService.setShopScheme(shopCode, scheme);
			}
		}
		return StringUtils.isEmpty(scheme) ? "" : scheme;
	}

}
