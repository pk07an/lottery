package com.npc.lottery.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.ResultObject;
import com.npc.lottery.disruptor.DisruptorService;

@Controller
public class LotteryController {
	private static final Logger log = Logger.getLogger(LotteryController.class);

	@Autowired
	private DisruptorService disruptorService;

	private Map<String, List<Integer>> conventJson(String content, int ballSize) {
		try {
			Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
			// 转换json
			JSONObject object = JSON.parseObject(content);
			Object jsonArray = object.get("data");
			List<JSONObject> list = JSON.parseArray(jsonArray + "", JSONObject.class);

			for (JSONObject json : list) {

				String period = json.get("period").toString();
				String[] results = json.get("result").toString().split("-");
				List<Integer> resultList = new ArrayList<Integer>();
				if (results.length == ballSize) {
					for (int i = 0; i < results.length; i++) {
						resultList.add(Integer.valueOf(results[i]));
					}
					map.put(period, resultList);
				} else {
					throw new Exception();
				}

			}

			return map;
		} catch (Exception e) {
			log.error(e);
			return new HashMap<String, List<Integer>>();
		}
	}

	/**
	 * {data:[{period:xxxxx,result:1-1-1-1-1}]}
	 * 
	 * @param content
	 */
	@RequestMapping(value = "/lotteryListener.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultObject lotteryListener(@RequestParam(value = "data", required = true) String data, @RequestParam(value = "playtype") String playType) {
		log.info("java兑奖监听获取playType:" + playType + "--------data:" + data + "的数据");
		ResultObject rs = new ResultObject();
		if (Constant.LOTTERY_TYPE_BJSC.equalsIgnoreCase(playType)) {

			Map<String, List<Integer>> resultMap = this.conventJson(data, 10);
			if (MapUtils.isNotEmpty(resultMap)) {
				boolean publishState = disruptorService.publishEvent(playType, resultMap);
				if (publishState) {
					rs.setErrorCode(0);
				} else {
					rs.setErrorCode(-3);
					rs.setErrorMsg("publish error");
				}
			} else {
				rs.setErrorCode(-2);
				rs.setErrorMsg("data error");
			}
		} else if (Constant.LOTTERY_TYPE_CQSSC.equalsIgnoreCase(playType)) {

			Map<String, List<Integer>> resultMap = this.conventJson(data, 5);
			if (MapUtils.isNotEmpty(resultMap)) {
				boolean publishState = disruptorService.publishEvent(playType, resultMap);
				if (publishState) {
					rs.setErrorCode(0);
				} else {
					rs.setErrorCode(-3);
					rs.setErrorMsg("publish error");
				}
			} else {
				rs.setErrorCode(-2);
				rs.setErrorMsg("data error");
			}
		} else if (Constant.LOTTERY_TYPE_GDKLSF.equalsIgnoreCase(playType)) {

			Map<String, List<Integer>> resultMap = this.conventJson(data, 8);
			if (MapUtils.isNotEmpty(resultMap)) {
				boolean publishState = disruptorService.publishEvent(playType, resultMap);
				if (publishState) {
					rs.setErrorCode(0);
				} else {
					rs.setErrorCode(-3);
					rs.setErrorMsg("publish error");
				}
			} else {
				rs.setErrorCode(-2);
				rs.setErrorMsg("data error");
			}
		} else if (Constant.LOTTERY_TYPE_K3.equalsIgnoreCase(playType)) {

			Map<String, List<Integer>> resultMap = this.conventJson(data, 3);
			if (MapUtils.isNotEmpty(resultMap)) {
				boolean publishState = disruptorService.publishEvent(playType, resultMap);
				if (publishState) {
					rs.setErrorCode(0);
				} else {
					rs.setErrorCode(-3);
					rs.setErrorMsg("publish error");
				}
			} else {
				rs.setErrorCode(-2);
				rs.setErrorMsg("data error");
			}
		} else if (Constant.LOTTERY_TYPE_NC.equalsIgnoreCase(playType)) {

			Map<String, List<Integer>> resultMap = this.conventJson(data, 8);
			if (MapUtils.isNotEmpty(resultMap)) {
				boolean publishState = disruptorService.publishEvent(playType, resultMap);
				if (publishState) {
					rs.setErrorCode(0);
				} else {
					rs.setErrorCode(-3);
					rs.setErrorMsg("publish error");
				}
			} else {
				rs.setErrorCode(-2);
				rs.setErrorMsg("data error");
			}
		} else {
			rs.setErrorCode(-1);
			rs.setErrorMsg("playType error");
		}
		return rs;
	}

}
