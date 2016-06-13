<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<script language="javascript">
function handlePet() {
	$("#tj").html("处理中.....");
	$("#sForm").attr("action","${pageContext.request.contextPath}/boss/modifyCqsscLotResult.action");
	$("#sForm").submit();
}
function handleHistory() {
	$("#tj").html("处理中.....");
	$("#sForm").attr("action","${pageContext.request.contextPath}/boss/modifyCqsscHistoryResult.action");
	$("#sForm").submit();
}
</script>
<div class="content">
    <s:form id="sForm" action="/boss/modifyCqsscLotResult.action" >
	<div class="main">
	  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy pw" id="betTable">
	  <tbody>
	    <tr>
	      <th colspan="2">重慶時時彩開獎結果修改</th>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">期數 </td>
	      <td class="tl" width="50%">
	          <input name="cqPeriodsInfo.periodsNum" value="<s:property escape="false" value="cqPeriodsInfo.periodsNum"/>" readonly/>	                                
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">开盘时间</td>
	      <td class="tl" width="50%">
	          <input name="cqPeriodsInfo.openQuotTime" value="<s:date name="cqPeriodsInfo.openQuotTime" format="yyyy-MM-dd HH:mm:ss" />" readonly/>                      
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">开奖时间</td>
	      <td class="tl" width="50%">
	          <input name="cqPeriodsInfo.lotteryTime" value="<s:date name="cqPeriodsInfo.lotteryTime" format="yyyy-MM-dd HH:mm:ss" />" readonly/>                  
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">封盘时间</td>
	      <td class="tl" width="50%">
	          <input name="cqPeriodsInfo.stopQuotTime" value="<s:date name="cqPeriodsInfo.stopQuotTime" format="yyyy-MM-dd HH:mm:ss" />" readonly/>                      
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">第一球 </td>
	      <td class="tl" width="50%">
	          <input name="cqPeriodsInfo.result1" value="<s:property escape="false" value="cqPeriodsInfo.result1"/>" />                            
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第二球 </td>
	      <td class="tl">
	          <input name="cqPeriodsInfo.result2" value="<s:property escape="false" value="cqPeriodsInfo.result2"/>" />       
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第三球 </td>
	      <td class="tl">
	          <input name="cqPeriodsInfo.result3" value="<s:property escape="false" value="cqPeriodsInfo.result3"/>" />        
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第四球 </td>
	      <td class="tl">
	          <input name="cqPeriodsInfo.result4" value="<s:property escape="false" value="cqPeriodsInfo.result4"/>" />      
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第五球 </td>
	      <td class="tl">
	          <input name="cqPeriodsInfo.result5" value="<s:property escape="false" value="cqPeriodsInfo.result5"/>" />       
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
