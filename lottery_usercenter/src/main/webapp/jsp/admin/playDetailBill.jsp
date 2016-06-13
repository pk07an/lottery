<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.math.BigDecimal,com.npc.lottery.util.Page" %>
<%@page import="java.text.SimpleDateFormat,java.text.NumberFormat,com.npc.lottery.member.entity.BaseBet,com.npc.lottery.util.PlayTypeUtils"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<html xmlns="http://www.w3.org/1999/xhtml">

<link href="${pageContext.request.contextPath}/css/admin/index.css"
	rel="stylesheet" type="text/css" />
<script language="javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript"
	src="${pageContext.request.contextPath}/js/public.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	 <script language="javascript" src="${pageContext.request.contextPath}/js/drag.js"></script>
	 
<body>
<script>
/* function forwardToPlayDetail(optValue)
{
window.location="${pageContext.request.contextPath}/member/enterPlayDetail.action?subType="+optValue;
} */
</script>
<div class="main">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" >
	   <!--控制表格头部开始-->
			  <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
			        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td width="21" align="left"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
			            <td width="124" align="left" class="F_bold">注單搜索</td>
						<td width="1035" align="center"><table border="0" cellspacing="0" cellpadding="0">
						
						    <tr>
			                <td width="56">&nbsp;</td>
			                <td width="102">&nbsp;</td>
			                <td width="60" align="right">&nbsp;</td>
			                <td width="120" align="right">&nbsp;</td>
			                <td width="139" align="left">&nbsp;</td>
			              </tr>
			            </table></td>
			            <td class="t_right"width="229">&nbsp;</td>
			            </tr></table></td>
			        <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_07.gif" width="16" height="30" /></td>
			      </tr>
			    </table></td>
			<!--控制表格头部结束-->
           <tr>
			    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
			        <td align="center" valign="top">
					<!-- 表格内容开始 一行六列-->
					<!-- 表格内容结束 一行六列-->
		      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="king mt4">
          <colgroup>
        <col width="8%">
		<col width="8%">
		<col width="8%">
		<col width="8%">
		<col width="8%">
		<col width="8%">
		<col width="8%">
		<col width="8%">
		<col width="14%">
		<col width="6%">
		<col width="6%">
        <col width="6%">
        <col width="8%">
        </colgroup>
  <tbody>
				
    <tr>
      <th>注單號/時間</th>
      <th>下注類型</th>
      <th>會員/盤</th>
      <th>代理</th>
      <th>總代理</th>
      <th>股東</th>
      <th>分公司</th>
      <th>總監占比/實占</th>
      <th>下注明細</th>
      <th>
      	下注金額
      	</th>
      	<th>輸贏結果</th>
      <th>状态/註銷人</th>
  	<%if("Y".equals((String)request.getAttribute("enableBetCancel"))){ %>
      <th>操作</th>
	<%} %>    
    </tr>
    <%
    Page betPage=(Page)request.getAttribute("page");
    if(betPage!=null){
    List betList=betPage.getResult();
    String preOrderNo="";
    if(betList.size()>0){
        for(int i=0;i<betList.size();i++)
        {
       
        BaseBet betMap=(BaseBet)betList.get(i);
        Date betTime=betMap.getBettingDate();
        String orderNo=betMap.getOrderNo();
        if(preOrderNo.equals(orderNo))
        	continue;
        
        SimpleDateFormat sm=new SimpleDateFormat("MM-dd HH:mm E");
        String betStrTime= sm.format(betTime);
        betStrTime = betStrTime.substring(0, betStrTime.indexOf("期")-1) + betStrTime.substring(betStrTime.indexOf("期")+1,betStrTime.length());
        String typeCode=betMap.getTypeCode();
        Integer count=betMap.getCount();
        String subTypeName=PlayTypeUtils.getPlayTypeSubName(typeCode);
        String playType=PlayTypeUtils.getPlayType(typeCode).getPlayType();
        
        BigDecimal odds=(BigDecimal)betMap.getOdds();
        Integer money=betMap.getMoney();
        NumberFormat nf=NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(1);
        String winMoney=nf.format(money.floatValue()*(odds.floatValue()-1)*count);
        
        String attribute=betMap.getAttribute();
        if(attribute==null)attribute="";
        if(attribute!=null&&attribute.length()!=0)
        	attribute=attribute.replace("|", ",");
        
        String commisType = PlayTypeUtils.getPlayTypeCommissionTypeName(typeCode);
        if(null==commisType) commisType = PlayTypeUtils.getPlayTypeSubName(typeCode);
        %>
          <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''" id=<%=betMap.getOrderNo()%>>
          <td><%=betMap.getOrderNo()%>#</br><%=betStrTime %></td>
          <td>
          <%=PlayTypeUtils.getPlayType(typeCode).getTypeName() %>
          <br/>
          <span class="green"><%=betMap.getPeriodsNum() %>&nbsp;期</span>
          <br>
          <%if(subTypeName==""){ %>
            <%=PlayTypeUtils.getPlayTypeFinalName(typeCode) %>
          <%}else{ %>
          	<%=subTypeName %>
          <%}%>
          </td>
         	<td><% if(betMap.getBettingUserName()!=null) {%> <%=betMap.getBettingUserName() %> <%} %>
         	</br><%=betMap.getPlate() %> 盤</td>
         	<td> <% if(betMap.getAgentStaffName()!=null) {%> <%=betMap.getAgentStaffName()%><% }%></br><span style="color:#666666"><%=betMap.getAgentStaffRate() %>%</span></td>
         	<td><% if(betMap.getGenAgenStaffName()!=null) {%> <%=betMap.getGenAgenStaffName()%><% }%></br><span style="color:#666666"><%=betMap.getGenAgenRate() %>%</span></td>
         	<td><% if(betMap.getStockholderStaffName()!=null){ %> <%=betMap.getStockholderStaffName()%><% }%> </br><span style="color:#666666"><%=betMap.getStockHolderRate() %>%</span></td>
         	<td><% if(betMap.getBranchStaffName()!=null) {%><%=betMap.getBranchStaffName()%><% }%></br><span style="color:#666666"><%=betMap.getBranchRate() %>%</span></td>
         	<td><% if(betMap.getChiefRate()!=null) %><%=betMap.getChiefRate()%>%</br><span class="green"><%= ((betMap.getMoney()*betMap.getChiefRate().floatValue())/100)%></span></td>
          <td><span class="blue"><%=subTypeName %> 『<%=PlayTypeUtils.getPlayTypeFinalName(typeCode) %>』</span>@<strong><span class="red"><%=betMap.getOdds() %></span></strong>
         <%if(count>1) {%></br>复式『<%=count%>组』<%} %>
         </br>
         <%=attribute %> 
      
          </td>
             <% if("4".equals(betMap.getWinState()) || "6".equals(betMap.getWinState())){%>
             <td width="6%" style="text-decoration:line-through;color:red;text-align:right;" >
              <%
            }else{
			%> 
            <td width="6%" style="text-align:right">
             <%} %>
            <%if(count>1) {%> <%=betMap.getMoney()/count%>X<%=count%>组</br><%} %>
           <%int betMoney = betMap.getMoney();%>
           &nbsp;<%=betMoney%>&nbsp;
          </td>
          <td style="text-align:right">
          <%BigDecimal displayWinMoney = BigDecimal.valueOf((double)0);%>
          <% if("0".equals(betMap.getLotteryType())){%>
            <%  displayWinMoney = betMap.getWinMoney().divide(new BigDecimal(count)).setScale(1,BigDecimal.ROUND_DOWN);%>
           &nbsp;<%=displayWinMoney%>&nbsp;
           <%
            }else{
			%> 
			   
			<%} %>
           </td>
            <td width="6%">
              <% if("4".equals(betMap.getWinState()) || "6".equals(betMap.getWinState())){%>
             	<span style="color:red">註銷</span>
              <%
            }else{
			%> 
			<span>正常</span>
    	   <%} %>
          </td>
          <%if("Y".equals((String)request.getAttribute("enableBetCancel"))){ %>
         	    <td width="6%">
	    	 	<%if("0".equals(betMap.getLotteryType()) && !"4".equals(betMap.getWinState())){//已结算才允许註銷 %>
	    	 		<a href="#" betMoneyCancel="<%=betMoney %>" winMoneyCancel="<%=displayWinMoney %>" 
	    	 		orderNoCancel="<%=betMap.getOrderNo()%>"  playTypeCancel="<%=playType%>"  
	    	 		periodsNumCancel="<%=betMap.getPeriodsNum() %>" searchDateCancel="<%=betMap.getBettingDate() %>" 
	    	 		lotteryTypeCancel="<%=betMap.getLotteryType() %>"  billTypeCancel="<%=betMap.getBillType() %>" 
	    	 		class="btn td_btn" name="cancelBill" ><img src="${pageContext.request.contextPath}/images/admin/delete.gif"/>註銷</a>
	    	 	<%}else{ %>
		    	 	<a style="display:none" href="#" betMoneyCancel="<%=betMoney %>" winMoneyCancel="<%=displayWinMoney %>" 
		    	 		orderNoCancel="<%=betMap.getOrderNo()%>"  playTypeCancel="<%=playType%>"  
		    	 		periodsNumCancel="<%=betMap.getPeriodsNum() %>" searchDateCancel="<%=betMap.getBettingDate() %>" 
		    	 		lotteryTypeCancel="<%=betMap.getLotteryType() %>"  billTypeCancel="<%=betMap.getBillType() %>" 
		    	 		class="btn td_btn" name="cancelBill" ><img src="${pageContext.request.contextPath}/images/admin/delete.gif"/>註銷</a>
	    	 	<%}%>
	    	 	<%if("4".equals(betMap.getWinState())){//已註銷才允许恢復 %>
	    	 		<a href="#" betMoneyRecovery="<%=betMoney %>" winMoneyRecovery="<%=displayWinMoney %>" 
	    	 		orderNoRecovery="<%=betMap.getOrderNo()%>"  playTypeRecovery="<%=playType%>"  
	    	 		periodsNumRecovery="<%=betMap.getPeriodsNum() %>" searchDateRecovery="<%=betMap.getBettingDate() %>"  
	    	 		billTypeRecovery="<%=betMap.getBillType() %>" class="btn td_btn" name="recoveryBill" >
	    	 		<img  src="${pageContext.request.contextPath}/images/admin/fanhui.gif"/>恢復</a>
	    	 	<%}else{ %>
		    	 	<a style="display:none" href="#" betMoneyRecovery="<%=betMoney %>" winMoneyRecovery="<%=displayWinMoney %>" 
		    	 		orderNoRecovery="<%=betMap.getOrderNo()%>"  playTypeRecovery="<%=playType%>"  
		    	 		periodsNumRecovery="<%=betMap.getPeriodsNum() %>" searchDateRecovery="<%=betMap.getBettingDate() %>"  
		    	 		billTypeRecovery="<%=betMap.getBillType() %>" class="btn td_btn" name="recoveryBill" >
		    	 		<img  src="${pageContext.request.contextPath}/images/admin/fanhui.gif"/>恢復</a>
	    	 	<%}%>
	    	 	</br>
	    	 	<a href="#" betMoneyLog="<%=betMoney %>" winMoneyLog="<%=displayWinMoney %>" 
		    	 		orderNoLog="<%=betMap.getOrderNo()%>"  playTypeLog="<%=playType%>"  
		    	 		periodsNumLog="<%=betMap.getPeriodsNum() %>" searchDateLog="<%=betMap.getBettingDate() %>"  
		    	 		billTypeLog="<%=betMap.getBillType() %>" class="btn td_btn" name="btnLog" >
		    	 		<img  src="${pageContext.request.contextPath}/images/admin/rizhi.gif"/>日志</a>
	          </td>
          <%} %>
        </tr>
    <%
    preOrderNo=orderNo;
        }
    
   }else
   {%>
       <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="">
	       <td colspan="13"><font color="red">当前没有下注</font></td>
	     </tr>
  <%  }
    }
   %>
  </tbody>
			</table></td>
	        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
	      </tr>
	    </table></td>
	  </tr>
		<tr>
		    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
		        <td align="center">
		         <s:if test="#request.page.totalCount>0">
					<lottery:paginate url="${pageContext.request.contextPath}/admin/queryBillAdmin.action" recordType=" 筆注單" param="periodsNum=${vo.periodsNum}&userType=${vo.userType}&radioBox=${vo.radioBox}&playType=${vo.playType}&createTime=${vo.createTime}&orderNum=${vo.orderNum}&lotteryType=${vo.lotteryType}&memberName=${vo.memberName}&eduMin=${vo.eduMin}&eduMax=${vo.eduMax}&resMin=${vo.resMin}&resMax=${vo.resMax}&billType=${vo.billType}"/>
				 </s:if><s:else>
                 &nbsp;
                 </s:else>
		        
		        </td>
			   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
		      </tr>
		    </table></td>
		  </tr>
<!--控制底部操作按钮结束-->
     </table>
		<!--中间内容结束-->
        
  </div>
 <%
 //註單註銷
 if("Y".equals((String)request.getAttribute("enableBetCancel"))){ %>
<div id="cancalDiv" style="background-color:#EFF1FF; display:none;">
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king" >
		    <colgroup>
		      <col width="35%" />
		      <col width="65%" />
		    </colgroup>
		    <tr><th colspan="2">
		                          操作
		        </th></tr>
		    <tr><td>下注金額</td><td id="xzMoneyCancel"></td></tr>
		    <tr><td>输赢结果</td><td id="kyMoneyCancel"></td></tr>
		     <tr> <td colspan="2"><span style="color:red"> 注單註銷后会影响結算結果,請慎用.</span></td></tr>
		    <tr>
		    	<td>
		    		<input type="button" class="btn td_btn" value="注單註銷" id="billCancel"/>
		    	</td>
		    	<td>
		    		<input id="rBtnCancel" type="button" class="btn td_btn" value="關閉窗口" />
		    	</td>
		    </tr>
     </table>
     <input type="hidden" id="orderNoCancel" name="orderNoCancel"  value=""/>
     <input type="hidden" id="playTypeCancel" name="playTypeCancel" value=""/>
     <input type="hidden" id="periodsNumCancel" name="periodsNumCancel" value=""/>
     <input type="hidden" id="searchDateCancel" name="searchDateCancel" value=""/>
     <input type="hidden" id="lotteryTypeCancel" name="lotteryTypeCancel" value=""/>
     <input type="hidden" id="billTypeCancel" name="billTypeCancel" value=""/>
   
</div>
<div id="recoveryDiv" style="background-color:#EFF1FF; display:none;">
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king" >
		    <colgroup>
		      <col width="35%" />
		      <col width="65%" />
		    </colgroup>
		    <tr><th colspan="2">
		                          操作
		        </th></tr>
		    <tr><td>下注金額</td><td id="xzMoneyRecovery"></td></tr>
		    <tr><td>输赢结果</td><td id="kyMoneyRecovery"></td></tr>
		     <tr> <td colspan="2"><span style="color:red"> 注單恢復后会影响結算結果,請慎用.</span></td></tr>
		    <tr>
		    	<td>
		    		<input type="button" class="btn td_btn" value="注單恢復" id="billRecovery"/>
		    	</td>
		    	<td>
		    		<input id="rBtnRecovery" type="button" class="btn td_btn" value="關閉窗口" />
		    	</td>
		    </tr>
     </table>
     <input type="hidden" id="orderNoRecovery" name="orderNoRecovery"  value=""/>
     <input type="hidden" id="playTypeRecovery" name="playTypeRecovery" value=""/>
     <input type="hidden" id="periodsNumRecovery" name="periodsNumRecovery" value=""/>
     <input type="hidden" id="searchDateRecovery" name="searchDateRecovery" value=""/>
     <input type="hidden" id="billTypeRecovery" name="billTypeRecovery" value=""/>
   
</div>
<%} %>
<script type="text/javascript">
function setStyle(obj,ty)
{
	if(ty==0)
	obj.setAttribute("style","color:#FF0000; text-decoration:underline;");
	else{
		obj.setAttribute("style","");	
	}
}

<%if("Y".equals((String)request.getAttribute("enableBetCancel"))){ %>
$(document).ready(function(){
	//获取元素的坐标    
	var getElementOffset = function _getElementOffset($this) {
	    var offset = $this.position();
	    var left = offset.left;
	    var top = offset.top + $this.height() + 6;  //把text元素本身的高度也算上
	    return { left: left, top: top }
	}

	//设置元素的坐标
	var setElementOffset = function _setElementOffset(id, offset) {
	    var $this = $('#' + id);
	    $this.css({ "position": "absolute", "left": eval(offset.left-100), "top": offset.top });
	    return $this;
	}
	
	//註單註銷
	$("[name=cancelBill]").on("click", function () {
		var xzMoney = $(this).attr("betMoneyCancel");
		var kyMoney = $(this).attr("winMoneyCancel");
		var orderNo = $(this).attr("orderNoCancel");
		var playType = $(this).attr("playTypeCancel");
		var periodsNum = $(this).attr("periodsNumCancel");
		var searchDate = $(this).attr("searchDateCancel");
		var lotteryType = $(this).attr("lotteryTypeCancel");
		var billType = $(this).attr("billTypeCancel");
		$("#orderNoCancel").val(orderNo);
		$("#playTypeCancel").val(playType);
		$("#periodsNumCancel").val(periodsNum);
		$("#searchDateCancel").val(searchDate);
		$("#lotteryTypeCancel").val(lotteryType);
		$("#billTypeCancel").val(billType);
		
		$("#xzMoneyCancel").html(xzMoney);
		$("#kyMoneyCancel").html(kyMoney);
		var offset = getElementOffset($(this));
		var $div = setElementOffset("cancalDiv", offset);
		$div.show();
	});
	//註單恢復
	$("[name=recoveryBill]").on("click", function () {
		var xzMoney = $(this).attr("betMoneyRecovery");
		var kyMoney = $(this).attr("winMoneyRecovery");
		var orderNo = $(this).attr("orderNoRecovery");
		var playType = $(this).attr("playTypeRecovery");
		var periodsNum = $(this).attr("periodsNumRecovery");
		var searchDate = $(this).attr("searchDateRecovery");
		var billType = $(this).attr("billTypeRecovery");
		$("#orderNoRecovery").val(orderNo);
		$("#playTypeRecovery").val(playType);
		$("#periodsNumRecovery").val(periodsNum);
		$("#searchDateRecovery").val(searchDate);
		$("#billTypeRecovery").val(billType);
		
		$("#xzMoneyRecovery").html(xzMoney);
		$("#kyMoneyRecovery").html(kyMoney);
		var offset = getElementOffset($(this));
		var $div = setElementOffset("recoveryDiv", offset);
		$div.show();
	});

	$("#rBtnCancel").on("click", function () {
		
		$("#cancalDiv").hide();
	});
	$("#rBtnRecovery").on("click", function () {
		
		$("#recoveryDiv").hide();
	});
	
	$("#billCancel").on("click",function(){
	var strUrl = '${pageContext.request.contextPath}/admin/ajaxCancelBillSubmitForChief.action';	
		
		var queryUrl = encodeURI(encodeURI(strUrl));
		var obj=$.ajax({
			url:queryUrl,
			dataType:"text",
			data:{"playType":$("#playTypeCancel").val(),
				"orderNo":$("#orderNoCancel").val(),
				"periodsNum":$("#periodsNumCancel").val(),
				"searchDate":$("#searchDateCancel").val(),
				"lotteryType":$("#lotteryTypeCancel").val(),
				"billType":$("#billTypeCancel").val()
				},
			type:"POST",
			success:function(data){
				
				if(data == "success"){
					var tds = $("#"+$("#orderNoCancel").val()).children();
					
						tds.each(function(i){
							if(i==eval(tds.size()-2)){
								$(this).html("<span style='color:red'>註銷</span>");
							}
							if(i==eval(tds.size()-4)){
								$(this).attr("style","text-decoration:line-through;color:red;");
							}
							if(i==tds.size()-1){
								$(this).children("[name=cancelBill]").hide();
								$(this).children("[name=recoveryBill]").show();
							}
							
						});
						
				}else if(data =="noLogin"){
					alert("請以總監用戶進行該操作");
				}else if(data == "notallow"){
					alert("未結算注單不允許註銷");
				}else if(data == "disable"){
					alert("總監沒有開放註銷注單功能");
				}else{
					alert("程序異常,重試或聯繫管理員.");
				}
				$("#cancalDiv").hide();
			},
			error:function(e){
				alert("程序異常,重試或聯繫管理員.");
				$("#cancalDiv").hide();
			}
			
			});
	});
	
	$("#billRecovery").on("click",function(){
		$("#recoveryDiv").hide();
		var strUrl = '${pageContext.request.contextPath}/admin/ajaxRecoveryBillSubmit.action';	
		
		var queryUrl = encodeURI(encodeURI(strUrl));
		var obj=$.ajax({
			url:queryUrl,
			dataType:"text",
			data:{"playType":$("#playTypeRecovery").val(),
				"orderNo":$("#orderNoRecovery").val(),
				"periodsNum":$("#periodsNumRecovery").val(),
				"searchDate":$("#searchDateRecovery").val(),
				"billType":$("#billTypeRecovery").val()
				},
			type:"POST",
			success:function(data){
				
				if(data == "success"){
					var tds = $("#"+$("#orderNoRecovery").val()).children();
					
						tds.each(function(i){
							if(i==eval(tds.size()-2)){
								$(this).html("<span>正常</span>");
							}
							if(i==eval(tds.size()-4)){
								$(this).attr("style","text-align:right;");
							}
							if(i==tds.size()-1){
								$(this).children("[name=recoveryBill]").hide();
								$(this).children("[name=cancelBill]").show();
							}
							
						});
						
				}else if(data =="noLogin"){
					alert("請以總監用戶進行該操作");
				}else if(data == "disable"){
					alert("總監沒有開放恢復注單功能");
				}else{
					alert("程序異常,重試或聯繫管理員.");
				}
				
			},
			error:function(e){
				alert("程序異常,重試或聯繫管理員.");
				$("#recoveryDiv").hide();
			}
			
			});
	});
	
	$("[name=btnLog]").on("click",function(){
		var strUrl = '${pageContext.request.contextPath}/admin/queryCancelPetLog.action';	
		var orderNo = $(this).attr("orderNoLog");
		var playType = $(this).attr("playTypeLog");
		var periodsNum = $(this).attr("periodsNumLog");
		var billType = $(this).attr("billTypeLog");
		var searchDate = $(this).attr("searchDateLog");
		window.location="${pageContext.request.contextPath}/admin/queryCancelPetLog.action?"+
				"orderNo="+orderNo+"&&playType="+playType+"&&periodsNum="+periodsNum+"&&billType="+billType+"&&searchDate="+searchDate;
	});
	
});
<%}%>



</script>
</body>
</html>
