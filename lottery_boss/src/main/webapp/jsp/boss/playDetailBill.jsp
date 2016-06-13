<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.math.BigDecimal,com.npc.lottery.util.Page" %>
<%@page import="java.text.SimpleDateFormat,java.text.NumberFormat,com.npc.lottery.member.entity.BaseBet,com.npc.lottery.util.PlayTypeUtils"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<html xmlns="http://www.w3.org/1999/xhtml">
<body>

<div class="main">
  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king">
  <colgroup>
        <col width="14%">
		<col width="12%">
		<col width="26%">
		<col width="6%">
        <col width="10%">
        </colgroup>
  <tbody>
    <tr>
      <th>注單號/時間</th>
      <th>下注類型</th>
      <th>注單明細</th>
      <th>
      	<s:if test="vo.billType==0">
      		補貨金額
      	</s:if><s:else>
      	下注金額
      	</s:else>
      	</th>
      <th>退水金額</th>
      <th>操作</th>
     
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
//         out.print("====="+orderNo+"======="+i);
        if(preOrderNo.equals(orderNo))
        	continue;
        
        SimpleDateFormat sm=new SimpleDateFormat("MM-dd HH:mm E");
        String betStrTime= sm.format(betTime);
        String typeCode=betMap.getTypeCode();
        Integer count=betMap.getCount();
        String subTypeName=PlayTypeUtils.getPlayTypeSubName(typeCode);
        
        BigDecimal odds=(BigDecimal)betMap.getOdds();
        Integer money=betMap.getMoney();
        NumberFormat nf=NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(1);
        String winMoney=nf.format(money.floatValue()*(odds.floatValue()-1)*count);
        
        String attribute=betMap.getAttribute();
        if(attribute==null)attribute="";
        if(attribute!=null&&attribute.length()!=0)
        	attribute=attribute.replace("|", ",");
        %>
        
          <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
          <td><%=betMap.getOrderNo()%>#</br><%=betStrTime %></td>
          <td><%if(typeCode.indexOf("GDKLSF")>-1) {%>廣東快樂十分<%} %>
          <%if(typeCode.indexOf("CQSSC")>-1) {%>重慶時時彩<%} %>
          <%if(typeCode.indexOf("BJ")>-1) {%>北京赛车<%} %>
          
          </br><span class="green"><%=betMap.getPeriodsNum() %></span></td>
          <td><%=subTypeName %> 『<%=PlayTypeUtils.getPlayTypeFinalName(typeCode) %>』@<span class="red"><%=betMap.getOdds() %></span>
         <%if(count>1) {%></br>复式『<%=count%>组』<%} %>
         </br>
         <%=attribute %> 
      
          </td>
         
             <% if("4".equals(betMap.getWinState()) || "6".equals(betMap.getWinState())){%>
             <td width="6%" style="text-decoration:line-through;color:red;" >
              <%
            }else{
			%> 
            <td width="6%">
             <%} %>
            <%if(count>1) {%> <%=betMap.getMoney()%>X<%=count%>组</br><%} %>
           <%=betMap.getMoney()*count %>
          </td>
            <td width="6%">
              <% if("4".equals(betMap.getWinState()) || "6".equals(betMap.getWinState())){%>
             	<span style="color:red"><input type="hidden" id="winstate" value=<%=betMap.getWinState()%> /> 註銷</span>
              <%
            }else{
			%> 
    	   <span class="mon" orderNo="<%=betMap.getOrderNo()%>" onMouseOver="setStyle(this,0)" onMouseOut="setStyle(this,1)" ><%=winMoney %></span>
    	   <%} %>
          </td>
            <td width="6%">
    	  	 <a href="#" orderNoDelete="<%=betMap.getOrderNo()%>"  class="deleteBill" >删除</a>
<%--     	  	 <a href="#" orderNoUpdate="<%=betMap.getOrderNo()%>"  class="updateBill" >修改</a> --%>
          </td>
        
        </tr>
    <%
    preOrderNo=orderNo;
        }
    
   }else
   {%>
       
       <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="">
	       <td colspan="6"><font color="red">当前没有下注</font></td>
	     </tr>
  <%  }
    }
   %>
   
    
   
        </table>

        <s:if test="#request.page.totalCount>0">
        
	<lottery:paginate url="${pageContext.request.contextPath}/boss/queryBill.action" param="subType=${vo.subType}&playType=${vo.playType}&bettingDateStart=${vo.bettingDateStart}&bettingDateEnd=${vo.bettingDateEnd}&orderNum=${vo.orderNum}&lotteryType=${vo.lotteryType}&memberID=${vo.memberID }&eduMin=${vo.eduMin}&eduMax=${vo.eduMax}&resMin=${vo.resMin}&resMax=${vo.resMax}&billType=${vo.billType}"/>
	</s:if>  
  </div>
  
  
  
   <div id="optionDiv" style="background-color:#EFF1FF; display:none;">
   <s:form id="optionForm" action="ajaxCancelBillSubmit" method="post">
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king" style="width:200px;">
		    <colgroup>
		      <col width="30%" />
		      <col width="70%" />
		    </colgroup>
		    <tr><th colspan="2">
		                          操作
		        </th></tr>
		    <tr><td>下注金額</td><td id="xzMoney"></td></tr>
		    <tr><td>可贏金額</td><td id="kyMoney"></td></tr>
		     <tr> <td colspan="2"><span style="color:red"> 注單取消后不能恢復,請慎用.</span></td></tr>
		    <tr>
		    	<td colspan="2"><input id="rBtnSubmit" type="button" onClick="onCancel()" class="btn td_btn" value="注單取消" />
		    &nbsp; <span class="ml10"><input id="rBtnCancel" type="button" class="btn td_btn" value="關閉窗口" /></span>
		    </td>
		    </tr>
<!-- 		    <tr> -->
<!-- 		    	<td>操作額</td> -->
<!-- 		    	<td><input id="money" name="replenish.money" value="" size="6" maxlength="9" /></td> -->
<!-- 		    </tr> -->
<!-- 		    <tr><td colspan="2" class="even"><div class="tj"> -->
<%-- 		          <span><input id="rBtnSubmit" type="submit" class="btn td_btn" value="確定" /></span> --%>
<%-- 		          <span class="ml10"><input id="rBtnCancel" type="button" class="btn td_btn" value="取消" /></span></div> --%>
<!-- 		      </td> -->
<!-- 		    </tr> -->
     </table>
     <input type="hidden" id="orderNum" name="orderNum"  value=""/>
     <input type="hidden" id="type" name="type" value="<s:property value="vo.subType"/>"/>
     <input type="hidden" id="typeChil" name="typeChil" value="<s:property value="vo.lotteryType"/>"/>
     <input type="hidden" id="billTy" name="billTy" value="<s:property value="vo.billType"/>"/>
   </s:form>
</div>
   <div id="deleteDiv" style="background-color:#EFF1FF; display:none;">
   <s:form id="optionFormDelete" action="ajaxCancelBillSubmit" method="post">
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king" style="width:200px;">
		    <colgroup>
		      <col width="30%" />
		      <col width="70%" />
		    </colgroup>
		    <tr><th colspan="2">
		                          操作
		        </th></tr>
		    <tr><td>下注金額</td><td id="xzMoneyDelete"></td></tr>
		    <tr><td>可贏金額</td><td id="kyMoneyDelete"></td></tr>
		     <tr> <td colspan="2"><span style="color:red"> 注單删除后不能恢復,請慎用.</span></td></tr>
		    <tr>
		    	<td colspan="2"><input type="button" onClick="onDelete()" class="btn td_btn" value="注單删除" />
		    &nbsp; <span class="ml10"><input id="rBtnCancelDelete" type="button" class="btn td_btn" value="關閉窗口" /></span>
		    </td>
		    </tr>
     </table>
     <input type="hidden" id="orderNumDelete" name="orderNumDelete"  value=""/>
     <input type="hidden" id="typeDelete" name="typeDelete" value="<s:property value="vo.subType"/>"/>
     <input type="hidden" id="typeChilDelete" name="typeChilDelete" value="<s:property value="vo.lotteryType"/>"/>
     <input type="hidden" id="billTyDelete" name="billTyDelete" value="<s:property value="vo.billType"/>"/>
   </s:form>
</div>
   <div id="updateDiv" style="background-color:#EFF1FF; display:none;">
   <s:form id="optionFormUpdate" action="ajaxUpdateBillSubmit" method="post">
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king" style="width:200px;">
		    <colgroup>
		      <col width="50%" />
		      <col width="50%" />
		    </colgroup>
		    <tr><th colspan="2">
		                          操作
		        </th></tr>
		    <tr><td>下注金額</td><td id="xzMoneyUpdate"></td></tr>
		    <tr><td>修改金額</td><td> <input type="text" id="updateMoney" name="updateMoney" value="" /></td></tr>
<%-- 		     <tr> <td colspan="2"><span style="color:red"> 注單删除后不能恢復,請慎用.</span></td></tr> --%>
		    <tr>
		    	<td colspan="2"><input type="button" onClick="onUpdate()" class="btn td_btn" value="注單修改" />
		    &nbsp; <span class="ml10"><input id="rBtnCancelUpdate" type="button" class="btn td_btn" value="關閉窗口" /></span>
		    </td>
		    </tr>
     </table>
     <input type="hidden" id="orderNumUpdate" name="orderNumUpdate"  value=""/>
     <input type="hidden" id="typeUpdate" name="typeUpdate" value="<s:property value="vo.subType"/>"/>
     <input type="hidden" id="typeChilUpdate" name="typeChilUpdate" value="<s:property value="vo.lotteryType"/>"/>
     <input type="hidden" id="billTyUpdate" name="billTyUpdate" value="<s:property value="vo.billType"/>"/>
   </s:form>
</div>

<script type="text/javascript">
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
    $this.css({ "position": "absolute", "left": offset.left, "top": offset.top });
    return $this;
}
var replenishShow = new Drag("optionDiv", { Handle: "optionDiv" });
$(".mon").on("click", function () {
	
	var radio = document.getElementsByName("vo.lotteryType");
	var subType = document.getElementsByName("vo.subType")[0].value;
	var opp = "1";
	for(var i=0;i<radio.length;i++)
	{
		if(radio[i].checked==true)
		{
			opp = radio[i].value;
		}
	}
	
	var xzMoney = $(this).parent().prev().text();
	var kyMoney = $(this).html();
	var orderNo = $(this).attr("orderNo");
// 	alert(opp);
	$("#xzMoney").html(xzMoney);
	$("#orderNum").val(orderNo);
	$("#kyMoney").html(kyMoney);
// 	$("#type").val(subType);
// 	$("#typeChil").val(opp);
	var offset = getElementOffset($(this));
	var $div = setElementOffset("optionDiv", offset);
	$div.show();
	
	
});
$(".deleteBill").on("click", function () {
	
	var xzMoney = $(this).parent().prev().prev().text();
	var kyMoney = $(this).parent().prev().text();
	var orderNo = $(this).attr("orderNoDelete");
	$("#xzMoneyDelete").html(xzMoney);
	$("#orderNumDelete").val(orderNo);
	$("#kyMoneyDelete").html(kyMoney);
	var offset = getElementOffset($(this));
	var $div = setElementOffset("deleteDiv", offset);
	$div.show();
	
	
});
$(".updateBill").on("click", function () {
	if($("#billTyUpdate").val()!=0){
		alert("只有補貨注單才能修改。");
		return false;
	}
	if($("#winstate").val()==4 || $("#winstate").val()==6 ) {
		alert("注消單不能修改。");
		return false;
	}
	var xzMoney = $(this).parent().prev().prev().text();
	var kyMoney = $(this).parent().prev().text();
	var orderNo = $(this).attr("orderNoUpdate");
	$("#xzMoneyUpdate").html(xzMoney);
	$("#orderNumUpdate").val(orderNo);
	$("#kyMoneyUpdate").html(kyMoney);
	var offset = getElementOffset($(this));
	var $div = setElementOffset("updateDiv", offset);
	$div.show();
	
	
});
$("#rBtnCancel").on("click", function () {
	
	$("#optionDiv").hide();
});
$("#rBtnCancelDelete").on("click", function () {
	
	$("#deleteDiv").hide();
});
$("#rBtnCancelUpdate").on("click", function () {
	
	$("#updateDiv").hide();
});
function onCancel() {
	
	var strUrl = '${pageContext.request.contextPath}/admin/ajaxCancelBillSubmit.action';	
	
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj=$.ajax({
		url:queryUrl,
		dataType:"text",
		data:{"type":$("#type").val(),"typeChil":$("#typeChil").val(),"orderNum":$("#orderNum").val(),"billTy":$("#billTy").val()},
		type:"POST",
		success:function(date){
// 			alert(date);
			$("#optionDiv").hide();
			if($("#pageNoCancel").val()==1)
			{
				document.getElementById("statReportForm").submit();
			}else{
				location.reload();
			}
		},
		error:function(e){
			alert("程序異常,重試或聯繫管理員.");
		}
		
		});
}
function onDelete() {
	
	var strUrl = '${pageContext.request.contextPath}/admin/ajaxDeleteBillSubmit.action';	
	
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj=$.ajax({
		url:queryUrl,
		dataType:"text",
		data:{"type":$("#typeDelete").val(),"typeChil":$("#typeChilDelete").val(),"orderNum":$("#orderNumDelete").val(),"billTy":$("#billTyDelete").val()},
		type:"POST",
		success:function(date){
			$("#deleteDiv").hide();
			if($("#pageNoCancel").val()==1)
			{
				document.getElementById("statReportForm").submit();
			}else{
				location.reload();
			}
		},
		error:function(e){
			alert("程序異常,重試或聯繫管理員.");
		}
		
		});
}
function onUpdate() {
	
	var strUrl = '${pageContext.request.contextPath}/admin/ajaxUpdateBillSubmit.action';	
	
	var queryUrl = encodeURI(encodeURI(strUrl));
	var obj=$.ajax({
		url:queryUrl,
		dataType:"text",
		data:{"type":$("#typeUpdate").val(),"typeChil":$("#typeChilUpdate").val(),"orderNum":$("#orderNumUpdate").val(),"billTy":$("#billTyUpdate").val(),"updateMoney":$("#updateMoney").val() },
		type:"POST",
		success:function(date){
			$("#updateDiv").hide();
			if($("#pageNoCancel").val()==1)
			{
				document.getElementById("statReportForm").submit();
			}else{
				location.reload();
			}
		},
		error:function(e){
			alert("程序異常,重試或聯繫管理員.");
		}
		
		});
}

function setStyle(obj,ty)
{
	if(ty==0)
	obj.setAttribute("style","color:#FF0000; text-decoration:underline;");
	else{
		obj.setAttribute("style","");	
	}
	
}
</script>


</body>

</html>
