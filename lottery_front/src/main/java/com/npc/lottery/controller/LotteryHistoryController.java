package com.npc.lottery.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.npc.lottery.common.Constant;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.INCPeriodsInfoLogic;
import com.npc.lottery.util.Page;

@Controller
public class LotteryHistoryController {

	@Autowired
	private IGDPeriodsInfoLogic periodsInfoLogic;
	@Autowired
	private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
	@Autowired
	private ICQPeriodsInfoLogic icqPeriodsInfoLogic;
	@Autowired
	private IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic;
	@Autowired
	private INCPeriodsInfoLogic ncPeriodsInfoLogic;
	private final static Logger logger = Logger.getLogger(LotteryHistoryController.class);

	@RequestMapping("/{path}/enterLotResultHistory.xhtml")
	public ModelAndView enter(String subType,
	        @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo, @PathVariable String path) {
		ModelAndView mv = new ModelAndView();
		Page<?> page = null;
		;
		String viewName = "history/gdklsfLotResultHistory";

		if (subType.indexOf("CQSSC") != -1) {
			page = initCQSSCLotHistoryData(pageNo);
			viewName = "history/cqsscLotResultHistory";
		} else if (subType.indexOf("GDKLSF") != -1) {
			page = initGDKLSFLotHistoryData(pageNo);
			mv.addObject("page", page);
			viewName = "history/gdklsfLotResultHistory";
		} else if (subType.indexOf("BJSC") != -1) {
			page = initBJSCLotHistoryData(pageNo);
			viewName = "history/bjscLotResultHistory";
		} else if (subType.indexOf(Constant.LOTTERY_TYPE_K3) != -1) {
			page = initJSSBLotHistoryData(pageNo);
			viewName = "history/jssbLotResultHistory";
		} else if (subType.indexOf("NC") != -1) {
			page = initNCLotHistoryData(pageNo);
			viewName = "history/ncLotResultHistory";
		}

		if (null != page) {
			mv.addObject("page", page);
		}
		mv.addObject("path", path);
		mv.setViewName(viewName);

		logger.info("======投注历史记录查询 viewName=" + viewName);
		return mv;
	}

	private Page<?> initGDKLSFLotHistoryData(int pageNo) {
		Page<GDPeriodsInfo> page = new Page<GDPeriodsInfo>(15);
		page.setPageSize(15);
		page.setPageNo(pageNo);
		page = periodsInfoLogic.queryHistoryPeriods(page);
		return page;
	}

	private Page<?> initCQSSCLotHistoryData(int pageNo) {
		Page<CQPeriodsInfo> page = new Page<CQPeriodsInfo>();
		page.setPageSize(15);
		page.setPageNo(pageNo);
		page = icqPeriodsInfoLogic.queryHistoryPeriods(page);
		return page;

	}

	private Page<?> initBJSCLotHistoryData(int pageNo) {
		Page<BJSCPeriodsInfo> page = new Page<BJSCPeriodsInfo>();
		page.setPageSize(15);
		page.setPageNo(pageNo);
		page = bjscPeriodsInfoLogic.queryHistoryPeriods(page);
		return page;

	}

	private Page<?> initJSSBLotHistoryData(int pageNo) {
		Page<JSSBPeriodsInfo> page = new Page<JSSBPeriodsInfo>();
		page.setPageSize(15);
		page.setPageNo(pageNo);
		page = jssbPeriodsInfoLogic.queryHistoryPeriods(page);
		return page;

	}

	private Page<?> initNCLotHistoryData(int pageNo) {
		Page<NCPeriodsInfo> page = new Page<NCPeriodsInfo>();
		page.setPageSize(15);
		page.setPageNo(pageNo);
		page = ncPeriodsInfoLogic.queryHistoryPeriods(page);
		return page;

	}
}
