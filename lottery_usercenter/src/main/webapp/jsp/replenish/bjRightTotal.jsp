<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ page import="com.npc.lottery.util.Tool,com.npc.lottery.replenish.vo.ZhanDangVO"%>  
<%Map<String,ZhanDangVO> map = (Map<String,ZhanDangVO>)request.getAttribute("result");%>
<!-- 总额开始-->
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="king zonge">
        <colgroup>
        <col width="156" />
        </colgroup>
        <tr>
          <th>总额： <span class="green"><s:property value="#request.totalMoney"/></span></th>
          </tr>
        <tr>
          <td class="lightgreen">冠亞軍和： <span class="green"><%=map.get("BJ_GROUP").getRightBarMoney() %></span> </td>
        </tr>
        <tr>
          <td class="lightgreen">冠亞單雙： <span class="green"><%=map.get("BJ_DOUBLSIDE_DS").getRightBarMoney() %></span> </td>
        </tr>
        <tr>
          <td class="lightgreen">冠亞大小： <span class="green"><%=map.get("BJ_DOUBLSIDE_DX").getRightBarMoney() %></span> </td>
        </tr>
        <tr>
          <td class="lightgreen">冠军<span class="blue">总</span>： <span class="green"><%=map.get("BALL_FIRST").getRightBarMoney() %></span> </td>
        </tr>
        <tr>
          <td class="lightgreen">亞軍<span class="blue">总</span>： <span class="green"><%=map.get("BALL_SECOND").getRightBarMoney() %></span> </td>
        </tr>
        <tr>
          <td class="lightgreen">第三名<span class="blue">总</span>： <span class="green"><%=map.get("BALL_THIRD").getRightBarMoney() %></span> </td>
        </tr>
        <tr>
          <td class="lightgreen">第四名<span class="blue">总</span>： <span class="green"><%=map.get("BALL_FORTH").getRightBarMoney() %></span> </td>
        </tr>
        <tr>
          <td class="lightgreen">第五名<span class="blue">总</span>： <span class="green"><%=map.get("BALL_FIFTH").getRightBarMoney() %></span> </td>
        </tr>
        <tr>
          <td class="lightgreen">第六名<span class="blue">总</span>： <span class="green"><%=map.get("BALL_SIXTH").getRightBarMoney() %></span> </td>
        </tr>
        <tr>
          <td class="lightgreen">第七名<span class="blue">总</span>： <span class="green"><%=map.get("BALL_SEVENTH").getRightBarMoney() %></span> </td>
        </tr>
        <tr>
          <td class="lightgreen">第八名<span class="blue">总</span>： <span class="green"><%=map.get("BALL_EIGHTH").getRightBarMoney() %></span> </td>
        </tr>
        <tr>
          <td class="lightgreen">第九名<span class="blue">总</span>：<span class="green"><%=map.get("BALL_NINTH").getRightBarMoney() %></span> </td>
        </tr>
        <tr>
          <td class="lightgreen">第十名<span class="blue">总</span>： <span class="green"><%=map.get("BALL_TENTH").getRightBarMoney() %></span> </td>
        </tr>
</table>
<!-- 总额结束-->