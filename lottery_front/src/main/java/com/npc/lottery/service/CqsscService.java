package com.npc.lottery.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.util.PlayTypeUtils;

@Service
public class CqsscService {

	private static final Logger logger = Logger.getLogger(CqsscService.class);

	public String ajaxCQSubmitResult(String period, List<BaseBet> betList, Integer totalMon, Map<String, String> mesMap) {

		StringBuffer html = new StringBuffer();
		html.append("<div class=\"p\">");
		if (mesMap.get("errorType") == null)
			html.append("<div class=\"print_btn\"><span><input type=\"button\" value=\"打 印\" class=\"btn2\" name=\"\" id=\"print\"></span><span class=\"ml10\"><input type=\"button\" value=\"返 回\" class=\"btn2\" name=\"\" id=\"retbtn\"></span></div>");
		html.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"231\" class=\"print_l\"><tbody>");
		if (mesMap.get("errorType") != null) {
			html.append("<tr><td class=\"t_td_caption_1\" colspan=\"2\"><strong class=\"red\">下注被退回，请再次确认！</strong></td></tr>");
		}
		html.append("<tr><td class='qishu' colspan=\"2\">" + period + "期</td></tr><tr><td colspan=\"2\"><ul>");
		int canBetNum = 0;
		String disable = "";
		StringBuffer cacheOdd = new StringBuffer("{");
		for (int i = 0; i < betList.size(); i++) {
			BaseBet bet = betList.get(i);
			Integer price = bet.getMoney();
			BigDecimal odds = bet.getOdds();
			String typeCode = bet.getPlayType();
			PlayType playType = PlayTypeUtils.getPlayType(typeCode);
			String subType = playType.getSubTypeName();
			if (subType == null)
				subType = "";
			String typeName = playType.getFinalTypeName();
			float winMoney = (odds.floatValue() - 1) * price;
			float roundMoney = (float) (Math.round(winMoney * 100)) / 100;

			html.append("<li><p>注单号：" + bet.getOrderNo() + "#</p><p class=\"t_center\"><span class=\"blue\">" + subType
			        + " 『 " + typeName + " 』" + "</span>@ <strong class=\"red\">" + odds + "</strong></p><p>下注额："
			        + price + "</p><p>可赢额：" + roundMoney + "</p>");
			if ("ExceedItem".equals(bet.getBetError())) {
				html.append("<p><span class=\"lj\">累计超过 “单期最高限额”</span></p>");

			} else if ("OddChanged".equals(bet.getBetError())) {
				cacheOdd.append(typeCode).append(":").append(odds).append(",");
				html.append("<input type='hidden' name='" + typeCode + "' value='" + price + "'/>");
				html.append("<p><span class=\"bd\">下注賠率有變動 請再次確認</span></p>");
				canBetNum++;
			} else {
				cacheOdd.append(typeCode).append(":").append(odds).append(",");
				html.append("<input type='hidden' name='" + typeCode + "' value='" + price + "'/>");
				canBetNum++;
			}
			html.append("</li>");
		}
		cacheOdd.append("}");

		if (canBetNum > 0) {
			html.append("<input type='hidden' name='cachedOdd' value='" + cacheOdd + "'/>");
		} else
			disable = "disabled";
		String button = "<table width=\"230\" height=\"35\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"print_bg\">"
		        + "<tbody><tr><td align=\"center\">"
		        + "<input type=\"button\" value=\"取消\" class=\"btn2\" id=\"cancelButton\">"
		        + "<input type=\"submit\" value=\"确定下注\" class=\"btn2 ml10\" "
		        + disable
		        + " ></td></tr></tbody>"
		        + "</table>";
		html.append("</ul></td></tr>");
		html.append("<tr><td width=\"34%\" class=\"l_color\">下注笔数</td><td width=\"66%\">" + betList.size()
		        + "笔</td></tr>");
		html.append("<tr><td class=\"l_color\">合计注额</td><td>￥" + totalMon + "</td></tr>");
		html.append("</tbody></table>");
		if (mesMap.get("errorType") != null)
			html.append(button);
		html.append("</div>");

		return html.toString();

	}
	
	/**
	 * 投注提交数据清理方法,如果提交的参数值不符合校验或者不是该玩法的接口接收的投注数据,直接忽略
	 * 
	 * @param plist
	 * @param request
	 * @return
	 */
	public Map<String, String> clearData(List<String> plist, HttpServletRequest request) {
		Map<String, String> handlerMap = new HashMap<String, String>();
		for (String ele : plist) {
			String price = request.getParameter(ele);
			// 如果参数值为空或者非法,忽略该条数据提交
			if (GenericValidator.isBlankOrNull(price) || !GenericValidator.isInt(price)) {
				continue;
			}
			// 只处理重庆的类型
			if (ele.indexOf("CQSSC_BALL_") != -1 || ele.indexOf("CQSSC_DOUBLESIDE_") != -1 || ele.indexOf("CQSSC_BZ_") != -1 || ele.indexOf("CQSSC_SZ_") != -1 || ele.indexOf("CQSSC_BS_") != -1
			        || ele.indexOf("CQSSC_DZ_") != -1 || ele.indexOf("CQSSC_ZL_") != -1) {
				handlerMap.put(ele, price);
			}
		}
		return handlerMap;
	}
}
