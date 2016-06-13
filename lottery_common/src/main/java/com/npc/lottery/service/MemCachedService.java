/**
 * 
 */
package com.npc.lottery.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.danga.MemCached.MemCachedClient;
import com.npc.lottery.sysmge.entity.MemberUser;

/**
 * 缓存处理服务类
 * 
 * @author
 */
@Service
public class MemCachedService {
	private static final Logger logger = Logger.getLogger(MemCachedService.class);
	@Autowired(required=false)
	private MemCachedClient memcachedClient;

	public final static String Key_Prefix_UID_MEMBER = "UID_MEMBER_";
	private static final String ONLINE_MEMBER = "ONLINE_MEMBER_";
	public final static String Key_Prefix_CKCV = "CKCV_";
	public final static String Key_Prefix_TOKEN = "TOKEN_";
	/** memcached不可用时，生成的默认CV值 */
	private final String Default_CV = "db19a086cbd94616b76a1990afd14975";
	private static final String SHOP_SCHEME = "SHOP_SCHEME_";

	/**
	 * 设置会员用户,保留8小时
	 * 
	 * @param memberUser
	 * @param request
	 */
	public void setMemberUserModel(MemberUser memberUser) {
		memcachedClient.set(Key_Prefix_UID_MEMBER + memberUser.getID() + "_" + memberUser.getShopsInfo().getShopsCode(), memberUser, new Date(System.currentTimeMillis() + 8 * 60 * 60 * 1000));
	}

	/**
	 * 根据用户ID获取会员用户基本模型<br>
	 * 如memcached 不存在,则返回null
	 * 
	 * @param id
	 * @return
	 */
	public MemberUser getMemberUserModel(Long id, String shopCode) {
		if (null == id) {
			return null;
		}
		MemberUser Obj = (MemberUser) memcachedClient.get(Key_Prefix_UID_MEMBER + id + "_" + shopCode);
		if (null == Obj) {
			logger.error("getMemberUserModel===========shopcode:" + shopCode + " id:" + id + "缓存没命中");
		}
		return Obj;
	}

	/**
	 * CV_${ID} 随机生成的value --> ${value} CV：对应的value<br>
	 * CK_${random} 登录时随机生成的CK<br>
	 * ${value} CV：对应的value<br>
	 * CK在客户端cookie中，仅保留12小时 会员专用
	 * 
	 * @param userID
	 * @return 返回CV
	 */
	public String setCKCV(String ck, String uid) {
		// TODO 仅保留至当天
		UUID uuid = UUID.randomUUID();
		String cv = uuid.toString();
		cv = cv.replaceAll("-", "");
		String ckcv = ck + ":ckcv:" + cv;
		boolean setFlag = memcachedClient.set(Key_Prefix_CKCV + "_" + uid, ckcv, new Date(System.currentTimeMillis() + 12 * 60 * 60 * 1000));
		return setFlag ? ckcv : ck + ":ckcv:" + Default_CV;
	}

	/**
	 * 获取CV<br>
	 * CV_${ID} 随机生成的value --> ${value} CV：对应的value<br>
	 * 用于cookie注入，且保留12小时
	 * 
	 * @param userID
	 * @return
	 */
	public String getCKCV(String ck, String uid) {

		String ckcv = (String) memcachedClient.get(Key_Prefix_CKCV + "_" + uid);
		if (StringUtils.isBlank(ckcv)) {
			logger.error("getCKCV=========id:" + uid + "缓存没有命中");
			ckcv = ck + ":ckcv:" + Default_CV;
		}

		return ckcv;
	}

	/**
	 * 设置后台token,保留1天
	 * 
	 * @param id
	 * @param token
	 * @param shopCode
	 */
	public void setToken(Long id, String token, String shopCode) {
		memcachedClient.set(Key_Prefix_TOKEN + id + "_" + shopCode, token, new Date(System.currentTimeMillis() + 1 * 60 * 60 * 1000));
	}

	/**
	 * 获取后台token,保留1天
	 * 
	 * @param uid
	 * @param shopCode
	 * @return
	 */
	public String getToken(Long uid, String shopCode) {
		String tokenMemcached = (String) memcachedClient.get(Key_Prefix_TOKEN + uid + "_" + shopCode);
		if (StringUtils.isEmpty(tokenMemcached)) {
			logger.error("getToken==============shopcode:" + shopCode + " id:" + uid + "缓存没有命中");
			tokenMemcached = "";
		}
		return tokenMemcached;
	}

	/**
	 * 获取今日盘期
	 * 
	 * @param key
	 * @return
	 */
	public List<?> getTodayPeriodInfoListByKey(String key) {
		List<?> periodList = (List<?>) memcachedClient.get(key);
		if (CollectionUtils.isEmpty(periodList)) {
			logger.error("getTodayPeriodInfoListByKey=========key:" + key + "缓存没有命中");
		}
		return periodList;
	}

	/**
	 * 设置当天盘期到缓存,当天有效
	 * 
	 * @param key
	 * @param todayPeriodInfoList
	 */
	public void setTodayPeriodInfoList(String key, List<?> todayPeriodInfoList) {

		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);

		memcachedClient.set(key, todayPeriodInfoList, todayEnd.getTime());
	}

	public boolean deleteCache(String key) {
		return memcachedClient.delete(key);
	}

	public String getShopSchemeByShopCode(String shopCode) {
		String scheme = (String) memcachedClient.get(SHOP_SCHEME + shopCode);
		if(StringUtils.isEmpty(scheme)){
			logger.error("getShopSchemeByShopCode==========shopcode:"+shopCode+"缓存没有命中");
		}
		return scheme;
	}

	/**
	 * 通过商铺号获取对应的scheme,保留12小时
	 * 
	 * @param shopCode
	 * @param scheme
	 */
	public void setShopScheme(String shopCode, String scheme) {
		memcachedClient.set(SHOP_SCHEME + shopCode, scheme, new Date(System.currentTimeMillis() + 12 * 60 * 60 * 1000));
	}

	/**
	 * 获取在线会员列表
	 * 
	 * @param shopCode
	 * @return
	 */
	public Map<String, Date> getOnlinMember(String shopCode) {
		Map<String, Date> onlineMemberMap = (Map<String, Date>) memcachedClient.get(ONLINE_MEMBER + shopCode);
		if (null == onlineMemberMap) {
			logger.error("getOnlinMember===============shopcode:"+shopCode+"缓存没有命中");
			onlineMemberMap = new ConcurrentHashMap<String, Date>();
		}
		return onlineMemberMap;
	}

	/**
	 * 设置在线会员列表
	 * 
	 * @param onlineMemberMap
	 * @param shopCode
	 */
	public void setOnlinMember(Map<String, Date> onlineMemberMap, String shopCode) {
		memcachedClient.set(ONLINE_MEMBER + shopCode, onlineMemberMap);
	}
}
