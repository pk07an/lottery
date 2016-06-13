<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<script language="javascript">
function handlePet() {
	$("#tj").html("处理中.....");
	$("#sForm").attr("action","${pageContext.request.contextPath}/boss/modifyK3LotResult.action");
	$("#sForm").submit();
}
function handleHistory() {
	$("#tj").html("处理中.....");
	$("#sForm").attr("action","${pageContext.request.contextPath}/boss/modifyK3HistoryResult.action");
	$("#sForm").submit();
}
</script>
<div class="content">
    <s:form id="sForm" action="/boss/modifyK3LotResult.action" >
	<div class="main">
	  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy pw" id="betTable">
	  <tbody>
	    <tr>
	      <th colspan="2">快3開獎結果修改</th>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">期數 </td>
	      <td class="tl" width="50%">
	          <input name="jssbPeriodsInfo.periodsNum" value="<s:property escape="false" value="jssbPeriodsInfo.periodsNum"/>" readonly/>                      
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">开盘时间</td>
	      <td class="tl" width="50%">
	          <input name="jssbPeriodsInfo.openQuotTime" value="<s:date name="jssbPeriodsInfo.openQuotTime" format="yyyy-MM-dd HH:mm:ss" />" readonly/>                      
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">开奖时间</td>
	      <td class="tl" width="50%">
	          <input name="jssbPeriodsInfo.lotteryTime" value="<s:date name="jssbPeriodsInfo.lotteryTime" format="yyyy-MM-dd HH:mm:ss" />" readonly/>                  
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">封盘时间</td>
	      <td class="tl" width="50%">
	          <input name="jssbPeriodsInfo.stopQuotTime" value="<s:date name="jssbPeriodsInfo.stopQuotTime" format="yyyy-MM-dd HH:mm:ss" />" readonly/>                      
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">第一骰 </td>
	      <td class="tl" width="50%">
	          <input name="jssbPeriodsInfo.result1" value="<s:property escape="false" value="jssbPeriodsInfo.result1"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>	                                       
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第二骰</td>
	      <td class="tl">
	       <input name="jssbPeriodsInfo.result2" value="<s:property escape="false" value="jssbPeriodsInfo.result2"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>	         
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第三骰</td>
	      <td class="tl">
	          <input name="jssbPeriodsInfo.result3" value="<s:property escape="false" value="jssbPeriodsInfo.result3"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>      
	      </td>
	    </tr>
	  </tbody>
	</table>
	<div class="tj" id="tj">
	     <span><input type="button" value="兑投注表" class="btn_4" name="" onclick="javascript:handlePet()"></input>&nbsp;&nbsp;&nbsp;&nbsp;</span><span>
	     <input type="button" value="兑历史表" class="btn_4" name="" onclick="javascript:handleHistory()"></input></span>
	</div>
	</div>
  </s:form>
</div>
</body>

</html>
