package com.npc.lottery.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npc.lottery.common.ResultObject;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.service.BettingService;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.util.Page;
import com.npc.lottery.util.PlayTypeUtils;
import com.npc.lottery.util.WebTools;

@Controller
public class PlayDetailController {
	
	private Logger logger = Logger.getLogger(PlayDetailController.class);
	@Autowired
	private WebTools webTools;
	@Autowired
	private BettingService bettingService;
	@Autowired
	private ShopSchemeService shopSchemeService;
	/**
	 * 下注明细
	 * @param request
	 * @param response
	 * @param path
	 * @param subType
	 * @return
	 */
	@SuppressWarnings("unchecked")
    @ResponseBody
	@RequestMapping("/{path}/getPlayDetail.json")
	public ResultObject getPlayDetail(HttpServletRequest request,HttpServletResponse response,@PathVariable String path,String subType) {
		ResultObject resultObject=new ResultObject();
		MemberUser currentUser = webTools.getCurrentMemberUserByCookieUid(request);
		Page<BaseBet> page = new Page<BaseBet>();
		int pgNo = 1;
		page.setPageNo(pgNo);
		page.setPageSize(10000);
		Long userId = currentUser.getID();
		if(null != subType || StringUtils.isEmpty(subType)){
			subType="GDKLSF";
		}
		if (subType.indexOf("GDKLSF") != -1 || subType.indexOf("CQSSC") != -1 || subType.indexOf("BJSC") != -1
		        || subType.indexOf("K3") != -1 || subType.indexOf("NC") != -1) {
			String scheme = shopSchemeService.getSchemeByShopCode(WebTools.getShopCodeByPath(path));
			if(StringUtils.isNotEmpty(scheme)){
				page = bettingService.getUserBetDetail(page, userId,scheme);
			}
			
		}
		Map<String,Object> retMap=new HashMap<String, Object>();
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		for (BaseBet baseBet : page.getResult()) {
			Map<String,Object> resultMap=new HashMap<String,Object>();
			String orderNo=baseBet.getOrderNo();
			String bettingDate= DateFormatUtils.format(baseBet.getBettingDate(), "MM-dd HH:mm:ss E").replace("星期", "");
			String typeCode=baseBet.getTypeCode();
			String periodsNum=baseBet.getPeriodsNum();
			String subTypeName=PlayTypeUtils.getPlayTypeSubName(typeCode);
			String finalName=PlayTypeUtils.getPlayTypeFinalName(typeCode);
			BigDecimal odds=baseBet.getOdds();
			int count=baseBet.getCount();
			int money=baseBet.getMoney();
			//计算可赢金额
			BigDecimal winMoney=new BigDecimal(money*(odds.floatValue()-1)*count).setScale(1, BigDecimal.ROUND_HALF_UP);
			resultMap.put("orderNo", orderNo);
			resultMap.put("bettingDate", bettingDate);
			resultMap.put("typeCode", typeCode);
			resultMap.put("periodsNum", periodsNum);
			resultMap.put("subTypeName", subTypeName);
			resultMap.put("finalName", finalName);
			resultMap.put("odds", odds);
			resultMap.put("money", money);
			resultMap.put("count", count);
			resultMap.put("winMoney", winMoney);
			if(null != baseBet.getAttribute()){
				resultMap.put("attribute", baseBet.getAttribute().replace("|", ","));
			}
			resultList.add(resultMap);
		}
		retMap.put("totalCount", page.getTotalCount());
		retMap.put("totalMoney", new BigDecimal(page.getTotal1()).setScale(0, BigDecimal.ROUND_HALF_UP));
		retMap.put("totalWinMoney", new BigDecimal(page.getTotal2()).setScale(1, BigDecimal.ROUND_HALF_UP) );
		retMap.put("result", resultList);
		resultObject.setData(retMap);
		return resultObject;
	}
}
