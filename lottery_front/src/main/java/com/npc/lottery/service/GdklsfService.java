package com.npc.lottery.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npc.lottery.common.Constant;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.logic.interf.IBetLogic;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.util.PlayTypeUtils;

@Service
public class GdklsfService {

	private static final Logger logger = Logger.getLogger(GdklsfService.class);

	public String ajaxHtmlResultForBet(List<BaseBet> betList, String playType, List<String> ballList,
	        List<String> assemblyList, Map<String, String> messageMap) {

		BaseBet bet = betList.get(0);
		String period = bet.getPeriodsNum();
		BigDecimal shopOdds = bet.getOdds();
		Integer price = bet.getMoney();
		String orderNo = bet.getOrderNo();
		String selectNo = StringUtils.join(ballList, ",");
		String group = price + "x" + assemblyList.size();
		Integer sumPrice = Integer.valueOf(price) * assemblyList.size();
		Float winMoney = Integer.valueOf(price) * (shopOdds.floatValue() - 1);
		float roundMoney = (float) (Math.round(winMoney * 100)) / 100;
		StringBuffer html = new StringBuffer();
		html.append("<div class=\"p\">");

		html.append("<div class=\"print_btn\"><span><input type=\"button\" value=\"打 印\" class=\"btn2\" name=\"\" id=\"print\"></span><span class=\"ml10\"><input type=\"button\" value=\"返 回\" class=\"btn2\" name=\"\" id=\"retbtn\"></span></div>");
		html.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"231\" class=\"print_l\">");
		html.append("<tbody><tr><td class='qishu' colspan=\"2\">" + period + "期</td></tr>");
		// html.append(period+"期</th></tr>");
		html.append(" <tr><td colspan=\"2\">");
		html.append("<ul><li><p>注单号：" + orderNo + "</p><p class=\"t_center\"><span class=\"blue\">" + playType
		        + "</span>@ <strong class=\"red\">" + shopOdds + "</strong></p>");
		html.append(" <p class=\"t_center\">" + selectNo + "</p>");
		html.append("<p>分组：" + group + "组</p><p>合计额：" + sumPrice + "</p><p>可赢额：" + roundMoney
		        + "</p></li></ul></td></tr>");
		html.append("<tr><td colspan=\"2\">");
		html.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" class=\"xzmore\">");
		html.append("<tbody><tr><th>ID</th><th>号码组合</th><th>下注额</th></tr>");
		for (int i = 1; i <= assemblyList.size(); i++) {
			String combOne = assemblyList.get(i - 1);
			html.append("<tr><td width=\"20%\">" + i + "</td><td width=\"50%\">" + combOne + "</td><td width=\"30%\">￥"
			        + Integer.valueOf(price) + "</td>");
		}
		html.append("</tr></tbody></table></td></tr>");
		html.append("<tr><td width=\"34%\" class=\"l_color\">下注笔数</td><td width=\"66%\">1 笔</td</tr>");
		html.append("<tr><td class=\"l_color\">合计注额</td><td>" + Integer.valueOf(price) * assemblyList.size()
		        + "</td></tr>");
		html.append("</tbody></table></div>");

		return html.toString();
	}

	public String ajaxGDSubmitResult(String period, List<BaseBet> betList, Integer totalMon, Map<String, String> mesMap) {

		StringBuffer html = new StringBuffer();
		html.append("<div class=\"p\">");
		if (mesMap.get("errorType") == null)
			html.append("<div class=\"print_btn\"><span><input type=\"button\" value=\"打 印\" class=\"btn2\" name=\"\" id=\"print\"></span><span class=\"ml10\"><input type=\"button\" value=\"返 回\" class=\"btn2\" name=\"\" id=\"retbtn\"></span></div>");

		html.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"230\" class=\"print_l\"><tbody>");
		if (mesMap.get("errorType") != null) {
			html.append("<tr><td class=\"t_td_caption_1\" colspan=\"2\"><strong class=\"red\">下注被退回，请再次确认！</strong></td></tr>");
		}
		html.append("<tr><td class='qishu' colspan=\"2\">" + period + "期</td></tr>");
		html.append("<tr><td colspan=\"2\"><ul>");
		StringBuffer cacheOdd = new StringBuffer("{");
		String disable = "";
		int canBetNum = 0;
		for (int i = 0; i < betList.size(); i++) {
			BaseBet bet = betList.get(i);
			Integer price = bet.getMoney();
			BigDecimal odds = bet.getOdds();
			String typeCode = bet.getPlayType();
			PlayType playType = PlayTypeUtils.getPlayType(typeCode);
			String subType = playType.getSubTypeName();
			String typeName = playType.getFinalTypeName();
			String orderNo = bet.getOrderNo();
			float winMoney = (odds.floatValue() - 1) * price;
			float roundMoney = (float) (Math.round(winMoney * 100)) / 100;

			if (subType == null)
				subType = "";

			String finalTypeName = playType.getFinalTypeName();
			if (playType.getPlaySubType() != null && playType.getPlaySubType().indexOf("BALL") != -1)
				finalTypeName = "『" + finalTypeName + "』";

			html.append("<li><p>注单号：" + orderNo + "#</p><p class=\"t_center\"><span class=\"blue\">" + subType + " 『 "
			        + typeName + " 』</span>@ <strong style=\"color:red\">" + odds + "</strong></p><p>下注额：" + price
			        + "</p><p>可赢额：" + roundMoney + "</p>");
			if ("ExceedItem".equals(bet.getBetError())) {
				html.append("<p><span class=\"lj\">累计超过 “单期最高限额”</span></p>");
				// disable="disabled";

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
			// 只处理广东的类型
			if (ele.indexOf("GDKLSF_BALL") != -1 || ele.indexOf("GDKLSF_DOUBLESIDE") != -1) {
				handlerMap.put(ele, price);
			}
		}
		return handlerMap;
	}
}
