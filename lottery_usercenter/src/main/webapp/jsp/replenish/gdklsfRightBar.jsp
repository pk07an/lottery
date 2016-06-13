<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.npc.lottery.common.Constant,
com.npc.lottery.member.entity.PlayType,com.npc.lottery.replenish.vo.ZhanDangVO" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tag/oscache.tld" prefix="cache" %>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> -->
<%Map<String,ZhanDangVO> map = (Map<String,ZhanDangVO>)request.getAttribute("result");%>
      <!-- 總额造漏开始-->
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="king zonge">
        <colgroup>
        <col width="112" />
        <col width="24" />
		<col width="20" />
        </colgroup>
        <tr>
          <th><b>總額：</b> <b><span class="green"><s:property value="#request.totalMoney"/></span></b></th>
          <th colspan="2"><b>遺漏</b></th>
          </tr>
        <tr>
          <td class="lightgreen">第一球<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIRST %>"><%=map.get("BALL_FIRST").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">01</strong></td>
          <td><%=map.get("1").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">第二球<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SECOND %>"><%=map.get("BALL_SECOND").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">02</strong></td>
          <td><%=map.get("2").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">第三球<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_THIRD %>"><%=map.get("BALL_THIRD").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">03</strong></td>
          <td><%=map.get("3").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">第四球<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FORTH %>"><%=map.get("BALL_FORTH").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">04</strong></td>
          <td><%=map.get("4").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">第五球<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIFTH %>"><%=map.get("BALL_FIFTH").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">05</strong></td>
          <td><%=map.get("5").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">第六球<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SIXTH %>"><%=map.get("BALL_SIXTH").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">06</strong></td>
          <td><%=map.get("6").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">第七球<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_SEVENTH %>"><%=map.get("BALL_SEVENTH").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">07</strong></td>
          <td><%=map.get("7").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">第八球<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_EIGHTH %>"><%=map.get("BALL_EIGHTH").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">08</strong></td>
          <td><%=map.get("8").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">總和大小： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_DRAGON %>"><%=map.get("GD_ZHDX_BALL").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">09</strong></td>
          <td><%=map.get("9").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">總和單雙： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_DRAGON %>"><%=map.get("GD_ZHDS_BALL").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">10</strong></td>
          <td><%=map.get("10").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">總尾大小： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_DRAGON %>"><%=map.get("GD_ZHWSDX_BALL").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">11</strong></td>
          <td><%=map.get("11").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">龍&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;虎： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_DRAGON %>"><%=map.get("GD_LH_BALL").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">12</strong></td>
          <td><%=map.get("12").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">任&nbsp;選&nbsp;&nbsp;二： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH %>"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX2").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">13</strong></td>
          <td><%=map.get("13").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">選二連直： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH %>"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R2LZHI").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">14</strong></td>
          <td><%=map.get("14").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">選二連組： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH %>"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R2LZ").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">15</strong></td>
          <td><%=map.get("15").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">任&nbsp;選&nbsp;&nbsp;三： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH %>"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX3").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">16</strong></td>
          <td><%=map.get("16").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">選三前直： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH %>"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R3LZHI").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">17</strong></td>
          <td><%=map.get("17").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">選三前組： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH %>"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R3LZ").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">18</strong></td>
          <td><%=map.get("18").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">任&nbsp;選&nbsp;&nbsp;四： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH %>"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX4").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">19</strong></td>
          <td><%=map.get("19").getYLsum() %></td>
        </tr>
        <tr>
          <td class="lightgreen">任&nbsp;選&nbsp;&nbsp;五： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH %>"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX5").getRightBarMoney() %></a></span></b> </td>
          <td><strong class="blue_s">20</strong></td>
          <td><%=map.get("20").getYLsum() %></td>
        </tr>
</table>      
      
      
      <!-- 總额造漏结束--></td>
			<td width="110" valign="top" align="left">
			<!-- 两面长龙开始-->
			<table cellspacing="0" cellpadding="0" border="0" width="100%" class="king changlong" >
  <colgroup>
        <col width="81" />
        <col width="26" />
        </colgroup>
        <tr>
          <th colspan="2"><b>两面长龙</b></th>
        </tr>
    <%
    
    List<PlayType> playTypes=(List<PlayType>)request.getAttribute("twoSide");
    int count=0;
    int displaySize=0;
    for(int i=0;i<playTypes.size();i++)
    {
    	PlayType playType=playTypes.get(i);
    	String subTypeName=playType.getSubTypeName();
    	String finalTypeName=playType.getFinalTypeName();
    	if(playType.getTypeCode().indexOf("BJ")!=-1)
    	{      
    		if("GROUP".equals(playType.getPlaySubType()))
    		{
    			if("DA".equals(playType.getPlayFinalType())
    			 ||"X".equals(playType.getPlayFinalType())
    			 ||"DAN".equals(playType.getPlayFinalType())
    			 ||"S".equals(playType.getPlayFinalType())
    					)
    				subTypeName="";
    		}
    	}
    	if(subTypeName!=null)
    	{
    		if(subTypeName.indexOf("一")!=-1)
    		{	
    		subTypeName=subTypeName.replace("一", "1");
    		}
    		else if(subTypeName.indexOf("二")!=-1)
    			subTypeName=subTypeName.replace("二", "2");

    		else if(subTypeName.indexOf("三")!=-1)
    			subTypeName=subTypeName.replace("三", "3");
    		else if(subTypeName.indexOf("四")!=-1)
    			subTypeName=subTypeName.replace("四", "4");
    		else if(subTypeName.indexOf("五")!=-1)
    			subTypeName=subTypeName.replace("五", "5");
    		else if(subTypeName.indexOf("六")!=-1)
    			subTypeName=subTypeName.replace("六", "6");
    		else if(subTypeName.indexOf("七")!=-1)
    			subTypeName=subTypeName.replace("七", "7");
    		else if(subTypeName.indexOf("八")!=-1)
    			subTypeName=subTypeName.replace("八", "8");
    		else if(subTypeName.indexOf("九")!=-1)
    			subTypeName=subTypeName.replace("九", "9");
    		else if(subTypeName.indexOf("十")!=-1)
    			subTypeName=subTypeName.replace("十", "10");
    	}
    	
    	int typeCount=playType.getCount();
    	if(typeCount<2)
    		break;
    	/* if(count!=typeCount)
    		displaySize++;	
    	if(displaySize==4)
    		break; */
    %>
   <tr>
    <td width="50%" class="danlan"><span class="blue">
    <%if(subTypeName!=null&&subTypeName.length()!=0){ %><%=subTypeName %>-<%} %><%=finalTypeName %></span>
    </td>
    <td width="20%" class="danhong"><span class="red"><%=typeCount %> 期</span></td>
  </tr>
    <%
    count= typeCount;
    }
    %>    

 
</table>
			<!-- 两面长龙结束-->
<!-- </html> -->