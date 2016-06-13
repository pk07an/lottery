<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.periods.entity.BJSCPeriodsInfo,com.npc.lottery.common.Constant,
com.npc.lottery.member.entity.PlayType,com.npc.lottery.replenish.vo.ZhanDangVO" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tag/oscache.tld" prefix="cache" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%Map<String,ZhanDangVO> map = (Map<String,ZhanDangVO>)request.getAttribute("result");%>
<!-- 總额造漏开始-->
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="king zonge">
        <colgroup>
        <col width="126" />
        </colgroup>
        <tr>
          <th><b>總額：</b> <b><span class="green"><s:property value="#request.totalMoney"/></span></b></th>
          </tr>
        <tr>
          <td class="lightgreen">冠亞軍和： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_FIRST %>"><%=map.get("BJ_GROUP").getRightBarMoney() %></a></span></b> </td>
        </tr>
        <tr>
          <td class="lightgreen">冠亞大小： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_FIRST %>"><%=map.get("BJ_DOUBLSIDE_DX").getRightBarMoney() %></a></span></b> </td>
        </tr>
        <tr>
          <td class="lightgreen">冠亞單雙： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_FIRST %>"><%=map.get("BJ_DOUBLSIDE_DS").getRightBarMoney() %></a></span></b> </td>
        </tr>
        <tr>
          <td class="lightgreen">冠&nbsp;&nbsp;&nbsp;軍<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_FIRST %>"><%=map.get("BALL_FIRST").getRightBarMoney() %></a></span></b> </td>
        </tr>
        <tr>
          <td class="lightgreen">亞&nbsp;&nbsp;&nbsp;軍<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_FIRST %>"><%=map.get("BALL_SECOND").getRightBarMoney() %></a></span></b> </td>
        </tr>
        <tr>
          <td class="lightgreen">第三名<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_THIRD %>"><%=map.get("BALL_THIRD").getRightBarMoney() %></a></span></b> </td>
        </tr>
        <tr>
          <td class="lightgreen">第四名<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_THIRD %>"><%=map.get("BALL_FORTH").getRightBarMoney() %></a></span></b> </td>
        </tr>
        <tr>
          <td class="lightgreen">第五名<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_THIRD %>"><%=map.get("BALL_FIFTH").getRightBarMoney() %></a></span></b> </td>
        </tr>
        <tr>
          <td class="lightgreen">第六名<span class="blue">總</span>：  <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_THIRD %>"><%=map.get("BALL_SIXTH").getRightBarMoney() %></a></span></b> </td>
        </tr>
        <tr>
          <td class="lightgreen">第七名<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_SEVENTH %>"><%=map.get("BALL_SEVENTH").getRightBarMoney() %></a></span></b> </td>
        </tr>
        <tr>
          <td class="lightgreen">第八名<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_SEVENTH %>"><%=map.get("BALL_EIGHTH").getRightBarMoney() %></a></span></b> </td>
        </tr>
        <tr>
          <td class="lightgreen">第九名<span class="blue">總</span>： <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_SEVENTH %>"><%=map.get("BALL_NINTH").getRightBarMoney() %></a></span></b> </td>
        </tr>
        <tr>
          <td class="lightgreen">第十名<span class="blue">總</span>：  <b><span class="green">
          <a target="mainFrame" href="${pageContext.request.contextPath}/replenish/replenishSet.action?subType=<%=Constant.LOTTERY_BJSC_SUBTYPE_BALL_SEVENTH %>"><%=map.get("BALL_TENTH").getRightBarMoney() %></a></span></b> </td>
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
</html>