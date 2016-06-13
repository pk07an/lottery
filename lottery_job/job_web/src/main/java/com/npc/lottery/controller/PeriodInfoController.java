package com.npc.lottery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.npc.lottery.common.ResultObject;
import com.npc.lottery.service.PeriodInfoService;

@Controller
public class PeriodInfoController {

	@Autowired
	private PeriodInfoService periodInfoService;

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/getTodayPeriodInfo.json", method = RequestMethod.POST)
	public ResultObject getTodayPeriodInfo(@RequestParam(value = "playtype", defaultValue = "") String playType) {
		ResultObject rs = new ResultObject();
		if (StringUtils.isEmpty(playType)) {
			rs.setErrorCode(-2);
			rs.setErrorMsg("playType must input");
		} else {
			List resulList = periodInfoService.getTodayPeridoInfoByPlayType(playType);
			if (!CollectionUtils.isEmpty(resulList)) {
				Map<String, List> resultMap = new HashMap<String, List>();
				resultMap.put(playType, resulList);
				rs.setData(resultMap);
				rs.setErrorCode(0);
				rs.setErrorMsg("success");
			} else {
				rs.setErrorCode(-1);
				rs.setErrorMsg("getToday error");
			}
		}
		return rs;
	}
}
