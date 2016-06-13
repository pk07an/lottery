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
          <th colspan="2">造漏</th>
          </tr>
        <tr>
          <td class="lightgreen">第1球<span class="blue">总</span>： <span class="green"><%=map.get("BALL_FIRST").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">01</strong></td>
          <td><%=map.get("1").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">第2球<span class="blue">总</span>： <span class="green"><%=map.get("BALL_SECOND").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">02</strong></td>
          <td><%=map.get("2").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">第3球<span class="blue">总</span>： <span class="green"><%=map.get("BALL_THIRD").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">03</strong></td>
          <td><%=map.get("3").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">第4球<span class="blue">总</span>： <span class="green"><%=map.get("BALL_FORTH").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">04</strong></td>
          <td><%=map.get("4").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">第5球<span class="blue">总</span>： <span class="green"><%=map.get("BALL_FIFTH").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">05</strong></td>
          <td><%=map.get("5").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">第6球<span class="blue">总</span>： <span class="green"><%=map.get("BALL_SIXTH").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">06</strong></td>
          <td><%=map.get("6").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">第7球<span class="blue">总</span>： <span class="green"><%=map.get("BALL_SEVENTH").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">07</strong></td>
          <td><%=map.get("7").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">第8球<span class="blue">总</span>： <span class="green"><%=map.get("BALL_EIGHTH").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">08</strong></td>
          <td><%=map.get("8").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">總和大小： <span class="green"><%=map.get("GD_ZHDX_BALL").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">09</strong></td>
          <td><%=map.get("9").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">總和單雙： <span class="green"><%=map.get("GD_ZHDS_BALL").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">10</strong></td>
          <td><%=map.get("10").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">總尾大小： <span class="green"><%=map.get("GD_ZHWSDX_BALL").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">11</strong></td>
          <td><%=map.get("11").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">龍&nbsp;&nbsp;虎：<span class="green"><%=map.get("GD_LH_BALL").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">12</strong></td>
          <td><%=map.get("12").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">任選二： <span class="green"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX2").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">13</strong></td>
          <td><%=map.get("13").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">選二連直：<span class="green"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R2LZHI").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">14</strong></td>
          <td><%=map.get("14").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">選二連組：<span class="green"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R2LZ").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">15</strong></td>
          <td><%=map.get("15").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">任選三：<span class="green"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX3").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">16</strong></td>
          <td><%=map.get("16").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">選三前直：<span class="green"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R3LZHI").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">17</strong></td>
          <td><%=map.get("17").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">選三前組：<span class="green"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R3LZ").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">18</strong></td>
          <td><%=map.get("18").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">任選四：<span class="green"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX4").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">19</strong></td>
          <td><%=map.get("19").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">任選五：<span class="green"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX5").getRightBarMoney() %></span> </td>
          <td><strong class="blue_s">20</strong></td>
          <td><%=map.get("20").getYLsum() %></td>
        </tr>
</table>
<!-- 总额结束-->