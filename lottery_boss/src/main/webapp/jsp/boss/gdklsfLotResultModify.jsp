<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<script language="javascript">
function handlePet() {
	$("#tj").html("处理中.....");
	$("#sForm").attr("action","${pageContext.request.contextPath}/boss/modifyGdklsfLotResult.action");
	$("#sForm").submit();
}
function handleHistory() {
	$("#tj").html("处理中.....");
	$("#sForm").attr("action","${pageContext.request.contextPath}/boss/modifyGdklsfHistoryResult.action");
	$("#sForm").submit();
}
</script>
<div class="content">
    <s:form id="sForm" action="/boss/modifyGdklsfLotResult.action" >
	<div class="main">
	  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy pw" id="betTable">
	  <tbody>
	    <tr>
	      <th colspan="2">廣東快樂十分開獎結果修改</th>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">期數 </td>
	      <td class="tl" width="50%">
	          <input name="gdPeriodsInfo.periodsNum" value="<s:property escape="false" value="gdPeriodsInfo.periodsNum"/>" readonly/>                      
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">开盘时间</td>
	      <td class="tl" width="50%">
	          <input name="gdPeriodsInfo.openQuotTime" value="<s:date name="gdPeriodsInfo.openQuotTime" format="yyyy-MM-dd HH:mm:ss" />" readonly/>                      
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">开奖时间</td>
	      <td class="tl" width="50%">
	          <input name="gdPeriodsInfo.lotteryTime" value="<s:date name="gdPeriodsInfo.lotteryTime" format="yyyy-MM-dd HH:mm:ss" />" readonly/>                  
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">封盘时间</td>
	      <td class="tl" width="50%">
	          <input name="gdPeriodsInfo.stopQuotTime" value="<s:date name="gdPeriodsInfo.stopQuotTime" format="yyyy-MM-dd HH:mm:ss" />" readonly/>                      
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">第一球 </td>
	      <td class="tl" width="50%">
	          <input name="gdPeriodsInfo.result1" value="<s:property escape="false" value="gdPeriodsInfo.result1"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>	                                       
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第二球 </td>
	      <td class="tl">
	       <input name="gdPeriodsInfo.result2" value="<s:property escape="false" value="gdPeriodsInfo.result2"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>	         
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第三球 </td>
	      <td class="tl">
	          <input name="gdPeriodsInfo.result3" value="<s:property escape="false" value="gdPeriodsInfo.result3"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>      
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第四球 </td>
	      <td class="tl">
	         <input name="gdPeriodsInfo.result4" value="<s:property escape="false" value="gdPeriodsInfo.result4"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>       
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第五球 </td>
	      <td class="tl">
	          <input name="gdPeriodsInfo.result5" value="<s:property escape="false" value="gdPeriodsInfo.result5"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>       
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第六球 </td>
	      <td class="tl">
	          <input name="gdPeriodsInfo.result6" value="<s:property escape="false" value="gdPeriodsInfo.result6"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>       
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第七球 </td>
	      <td class="tl">
	         <input name="gdPeriodsInfo.result7" value="<s:property escape="false" value="gdPeriodsInfo.result7"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>        
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第八球 </td>
	      <td class="tl">
	         <input name="gdPeriodsInfo.result8" value="<s:property escape="false" value="gdPeriodsInfo.result8"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>       
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
