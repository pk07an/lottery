package com.npc.lottery.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.npc.lottery.common.Constant;
import com.npc.lottery.common.service.ShopSchemeService;
import com.npc.lottery.member.entity.BalanceInfo;
import com.npc.lottery.member.entity.CQandGDReportInfo;
import com.npc.lottery.service.BettingService;
import com.npc.lottery.service.CookieService;
import com.npc.lottery.util.DatetimeUtil;
import com.npc.lottery.util.Page;
import com.npc.lottery.util.WebTools;

@Controller
public class ReportController {

	private static final Logger logger = Logger.getLogger(ReportController.class);
	@Autowired
	private BettingService bettingService;
	@Autowired
	private CookieService cookieService;
	@Autowired
	private ShopSchemeService shopSchemeService;
	@Autowired
	private WebTools webTools;

	@RequestMapping("/{path}/enterSettleReport.xhtml")
	public ModelAndView enter(HttpServletRequest request, @PathVariable String path) {

		ModelAndView mv = new ModelAndView();

		Calendar startDate = DatetimeUtil.getFirstDayOfWeekByOffset(-2, -30);
		startDate.add(Calendar.DATE, -7);
		Calendar endDate = DatetimeUtil.getLastDayOfWeek();

		Map<String, BalanceInfo> retMap = new HashMap<String, BalanceInfo>();
		String uid = cookieService.getCookieValue(Constant.COOKIE_UID, request);
		String scheme = shopSchemeService.getSchemeByShopCode(WebTools.getShopCodeByPath(path));
		if (StringUtils.isNotEmpty(uid) && StringUtils.isNotEmpty(scheme)) {
			retMap = bettingService.getBalanceInfoByDate(Long.valueOf(uid), startDate.getTime(), endDate.getTime(), scheme);
		}

		mv.addObject("BalanceInfoList", retMap);
		mv.addObject("path", path);
		mv.setViewName("settleReports");

		return mv;
	}

	@RequestMapping("/{path}/queryCurrentReport.xhtml")
	public ModelAndView queryCurrentReport(HttpServletRequest request, String dateTime, String pageNo, @PathVariable String path) {
		ModelAndView mv = new ModelAndView();
		String uid = cookieService.getCookieValue(Constant.COOKIE_UID, request);
		Page<CQandGDReportInfo> page = new Page<CQandGDReportInfo>(14);
		if (StringUtils.isNotEmpty(uid)) {
			int pageIntNo = 1;
			if (StringUtils.isNotEmpty(pageNo)) {
				try {
					pageIntNo = Integer.valueOf(pageNo);
				} catch (Exception ex) {
					pageIntNo = 1;
				}
			}
			page.setPageNo(pageIntNo);
			page.setOrderBy("orderNo");
			page.setOrder("desc");
			String scheme = shopSchemeService.getSchemeByShopCode(WebTools.getShopCodeByPath(path));
			if (StringUtils.isNotEmpty(scheme)) {
				page = bettingService.getReport(page, Long.valueOf(uid), java.sql.Date.valueOf(dateTime.split(" ")[0]), scheme);
			}

		}
		mv.addObject("page", page);
		mv.addObject("dateTime", dateTime);
		mv.addObject("path", path);
		mv.setViewName("currentPlayReport");
		return mv;
	}
}
