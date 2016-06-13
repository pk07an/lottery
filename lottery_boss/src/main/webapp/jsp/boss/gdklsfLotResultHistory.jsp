<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.util.Page,com.npc.lottery.periods.entity.GDPeriodsInfo,com.npc.lottery.rule.GDKLSFRule" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<style>
    .lotPage {
	    width:100%;
	    border-right:1px solid #d6ceb4;border-bottom:1px solid #d6ceb4;border-left:1px solid #d6ceb4;padding:3px;
	}
</style>
<script language="javascript">
function stopPriod(than,priodNum,type){
	if(confirm("確認是否停盤期操作."))
	{ 
		var strUrl = '${pageContext.request.contextPath}/boss/ajaxDelPriod.action';
		var queryUrl = encodeURI(encodeURI(strUrl));
		$.ajax({
			type : "POST",
			url : queryUrl,
			data : {"priodNum":priodNum,"subType":$("#optionType").val(),"type":type},
			dataType : "json",
			beforeSend: function(){
				$(than).hide();
				$(than).next("div").show();
			},
			success : function (json){
				$(than).next("div").hide();
				if(json.errorMessage)
				{
					alert(json.errorMessage);
				    return false;
				}
				if(json.success){
					alert(json.success);
					location.reload();
				}	
			}
		});
	}
}
</script>
<body>
<div class="content">
<div class="main">
  <div class="top_content"><select class="red mr10" id="optionType" name="" onChange="forwardToLotteryHistory()">
       <option value="GDKLSF"  <s:if test="subType.indexOf('GDKLSF')>-1">selected</s:if> >“廣東快樂十分”開獎網</option>
      <option value="CQSSC"  <s:if test="subType.indexOf('CQSSC')>-1">selected</s:if> >“重慶時時彩”開獎網</option>
      <option value="BJSC"  <s:if test="subType.indexOf('BJSC')>-1">selected</s:if> >“北京賽車”開獎網</option>  
      <option value="K3"  <s:if test="subType.indexOf('K3')>-1">selected</s:if> >“江苏骰寶(快3)”開獎網</option>
      <option value="NC"  <s:if test="subType.indexOf('NC')>-1">selected</s:if> >“幸运农场”開獎網</option>
    </select>&nbsp;&nbsp;盘期状态：<select class="red mr10" id="state" name="" onChange="forwardToLotteryHistory()">
       <option value="7"  <s:if test="state.indexOf('7')>-1">selected</s:if> >已开奖|停开</option>
      <option value="all"  <s:if test="state.indexOf('all')>-1">selected</s:if> >全部</option>
      <option value="5"  <s:if test="state.indexOf('5')>-1">selected</s:if> >未取到结果</option>  
    </select></div>
  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king">
  <tbody>
    <tr>
      <th width="6%">期數</th>
      <th width="20%">開獎時間</th>
      <th colspan="8">開出號碼</th>
      <th colspan="4">總和</th>
      <th width="6%">龍虎</th>
      <th width="6%">停盤操作</th>
    </tr>
    <%
    Page<GDPeriodsInfo> pageResult=(Page)request.getAttribute("page");
    List<GDPeriodsInfo> result=pageResult.getResult();
    for(int i=0;i<result.size();i++)
    {
    	List<Integer> ballList=new ArrayList<Integer>();
    	GDPeriodsInfo periond=result.get(i);
    	String periondNum=periond.getPeriodsNum();
        Integer b1=periond.getResult1();ballList.add(b1);
        Integer b2=periond.getResult2();ballList.add(b2);
        Integer b3=periond.getResult3();ballList.add(b3);
        Integer b4=periond.getResult4();ballList.add(b4);
        Integer b5=periond.getResult5();ballList.add(b5);
        Integer b6=periond.getResult6();ballList.add(b6);
        Integer b7=periond.getResult7();ballList.add(b7);
        Integer b8=periond.getResult8();ballList.add(b8);
        Date lotteryTime=periond.getLotteryTime();
        SimpleDateFormat sm=new SimpleDateFormat("MM-dd E HH:mm");
        String lotteryStrTime= sm.format(lotteryTime); 
    %>      
      <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style=""<%if(i%2!=0)out.print("class=\"even\""); %>>
      <%if("5".equals(periond.getState().trim())){ %>
          <td bgcolor="#FF0000"><a href="${pageContext.request.contextPath}/boss/queryGdklsfLotResult.action?periodsNum=<%=periondNum %>"><%=periondNum %></a></td>
      <%}else{ %>
          <td><a href="${pageContext.request.contextPath}/boss/queryGdklsfLotResult.action?periodsNum=<%=periondNum %>"><%=periondNum %></a></td>
      <%}%>
      <td><%=lotteryStrTime %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span <%if(b1==19||b1==20) {%>class="ball-bg ball-red" <%}else{ %>class="ball-bg ball-blue"<%} %>><%=b1 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span <%if(b2==19||b2==20) {%>class="ball-bg ball-red" <%}else{ %>class="ball-bg ball-blue"<%} %>><%=b2 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span <%if(b3==19||b3==20) {%>class="ball-bg ball-red" <%}else{ %>class="ball-bg ball-blue"<%} %>><%=b3%></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span <%if(b4==19||b4==20) {%>class="ball-bg ball-red" <%}else{ %>class="ball-bg ball-blue"<%} %>><%=b4 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span <%if(b5==19||b5==20) {%>class="ball-bg ball-red" <%}else{ %>class="ball-bg ball-blue"<%} %>><%=b5 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span <%if(b6==19||b6==20) {%>class="ball-bg ball-red" <%}else{ %>class="ball-bg ball-blue"<%} %>><%=b6 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span <%if(b7==19||b7==20) {%>class="ball-bg ball-red" <%}else{ %>class="ball-bg ball-blue"<%} %>><%=b7 %></span><%} %></td>
      <td width="3%"><% if("6".equals(periond.getState().trim()) ){ %> &nbsp;<%}else{ %><span <%if(b8==19||b8==20) {%>class="ball-bg ball-red" <%}else{ %>class="ball-bg ball-blue"<%} %>><%=b8 %></span><%} %></td>
      <% if("6".equals(periond.getState().trim()) ){ %><td width="18%" colspan="5"><strong class="blue">官方停售，獎期取消</strong></td><%}else{ %>
	      <td width="3%"><%=b1+b2+b3+b4+b5+b6+b7+b8 %></td>
	      <td width="3%">
	      <%if(GDKLSFRule.ZHX(ballList)){ %>小 <%} else if(GDKLSFRule.ZHDA(ballList)){%><span class="red">大</span><%} else{%>和<%} %>
	      </td>
	      
	      <td width="5%">
	        <%if(GDKLSFRule.ZHDAN(ballList)){ %>單 <%} else{%><span class="red">雙</span><%} %>
	      </td> 
	      <td width="5%">
	   <%if(GDKLSFRule.ZHWX(ballList)){ %>尾小 <%} else{%><span class="red">尾大</span><%} %>
	      </td>
	      <td><%if(GDKLSFRule.HU(ballList)){ %>虎 <%} else{%><span class="red">龍</span><%} %></td>
      <%} %>
      <td> 
      
    <% if(!"6".equals(periond.getState().trim())&& !"7".equals(periond.getState().trim())&&periond.getLotteryTime().compareTo(new Date())<0 ){ %> 
    <input type="button" name="stopPriod" id="button" value="該期停開" class="btn td_btn" onclick="stopPriod(this,<%=periondNum %>,'stop')" title="慎用：點擊停開后，當期所有設注作廢。"/>
    <div style="width:170px;color:blue;margin:auto;display:none;">数据计算中，请耐心等待...<img src='/images/wait_msg.gif' alt='msg' style='height:20px;width:20px;'/><div>
    <%} %>
    <% if("6".equals(periond.getState().trim()) ){ %> 该期已作废<%} %>
    <% if("7".equals(periond.getState().trim()) ){ %> <input type="button" name="stopPriod" id="button" value="該期作废" class="btn td_btn" onclick="stopPriod(this,<%=periondNum %>,'Invalid')" title="慎用：點擊停開后，當期所有設注作廢。"/>
    		<div style="width:170px;color:blue;margin:auto;display:none;">数据计算中，请耐心等待...<img src='/images/wait_msg.gif' alt='msg' style='height:20px;width:20px;'/><div>
    <%} %>
      
      </td>
    </tr>
 <% }%>   
        </table>
  <s:if test="#request.page.totalCount>0">
     
	<lottery:paginate url="${pageContext.request.contextPath}/boss/enterLotResultHistoryBoss.action" param="subType=GDKLSF&state=${state}" css="lotPage"/>
</s:if>
    
  </div>

</div>
</body>

</html>
