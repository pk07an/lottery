<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<script language="javascript">
function handlePet() {
	$("#tj").html("处理中.....");
	$("#sForm").attr("action","${pageContext.request.contextPath}/boss/modifyBJSCLotResult.action");
	$("#sForm").submit();
}
function handleHistory() {
	$("#tj").html("处理中.....");
	$("#sForm").attr("action","${pageContext.request.contextPath}/boss/modifyBJSCHistoryResult.action");
	$("#sForm").submit();
}
</script>
<div class="content">
    <s:form id="sForm" action="/boss/modifyBJSCLotResult.action" >
	<div class="main">
	  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy pw" id="betTable">
	  <tbody>
	    <tr>
	      <th colspan="2">北京賽車(PK10)開獎結果修改</th>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">期數 </td>
	      <td class="tl" width="50%">
	          <input name="bjscPeriodsInfo.periodsNum" value="<s:property escape="false" value="bjscPeriodsInfo.periodsNum"/>" readonly/>                      
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">开盘时间</td>
	      <td class="tl" width="50%">
	          <input name="bjscPeriodsInfo.openQuotTime" value="<s:date name="bjscPeriodsInfo.openQuotTime" format="yyyy-MM-dd HH:mm:ss" />" readonly/>                      
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">开奖时间</td>
	      <td class="tl" width="50%">
	          <input name="bjscPeriodsInfo.lotteryTime" value="<s:date name="bjscPeriodsInfo.lotteryTime" format="yyyy-MM-dd HH:mm:ss" />" readonly/>                  
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">封盘时间</td>
	      <td class="tl" width="50%">
	          <input name="bjscPeriodsInfo.stopQuotTime" value="<s:date name="bjscPeriodsInfo.stopQuotTime" format="yyyy-MM-dd HH:mm:ss" />" readonly/>                      
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">第一球 </td>
	      <td class="tl" width="50%">
	          <input name="bjscPeriodsInfo.result1" value="<s:property escape="false" value="bjscPeriodsInfo.result1"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>	                                       
          </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第二球 </td>
	      <td class="tl">
	       <input name="bjscPeriodsInfo.result2" value="<s:property escape="false" value="bjscPeriodsInfo.result2"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>	         
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第三球 </td>
	      <td class="tl">
	          <input name="bjscPeriodsInfo.result3" value="<s:property escape="false" value="bjscPeriodsInfo.result3"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>      
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第四球 </td>
	      <td class="tl">
	         <input name="bjscPeriodsInfo.result4" value="<s:property escape="false" value="bjscPeriodsInfo.result4"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>       
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第五球 </td>
	      <td class="tl">
	          <input name="bjscPeriodsInfo.result5" value="<s:property escape="false" value="bjscPeriodsInfo.result5"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>       
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第六球 </td>
	      <td class="tl">
	          <input name="bjscPeriodsInfo.result6" value="<s:property escape="false" value="bjscPeriodsInfo.result6"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>       
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第七球 </td>
	      <td class="tl">
	         <input name="bjscPeriodsInfo.result7" value="<s:property escape="false" value="bjscPeriodsInfo.result7"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>        
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">第八球 </td>
	      <td class="tl">
	         <input name="bjscPeriodsInfo.result8" value="<s:property escape="false" value="bjscPeriodsInfo.result8"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>       
	      </td>
	    </tr>	   
	    <tr bgcolor="#ffffff">
	      <td class="tr">第九球 </td>
	      <td class="tl">
	         <input name="bjscPeriodsInfo.result9" value="<s:property escape="false" value="bjscPeriodsInfo.result9"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>       
	      </td>
	    </tr>	   
	    <tr bgcolor="#ffffff">
	      <td class="tr">第十球 </td>
	      <td class="tl">
	         <input name="bjscPeriodsInfo.result10" value="<s:property escape="false" value="bjscPeriodsInfo.result10"/>" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>       
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
