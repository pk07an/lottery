<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<script language="javascript">
	function checkShopsName(){
		var	i = $("#shopsName").val();
		if (i!=$("#oldName").val()){
			if (i!=""){	
		   		var strUrl = '${pageContext.request.contextPath}/boss/ajaxFindShopsName.action';		   							
				var queryUrl = encodeURI(encodeURI(strUrl));
				$.ajax({
					type : "POST",
					url : queryUrl,
					data : {"shopsName":i},
					dataType : "json",
					success : callBackName
				});           	
		   	}		
		}else{
			$("#verifyName").val("OK");
		}
	   	
	}
	function callBackName(json) {
		if (json.count>0){
			$("#shopsNameAgain").html("該商鋪名稱已存在");
			$("#verifyName").val("NO");
		}else{
			$("#shopsNameAgain").html("");
			$("#verifyName").val("OK");
		}
	}
	function checkExpityTime(){
		var dDate = new Date();
		currentDate = dDate.getFullYear() + "-" + (dDate.getMonth()+1) + "-" + dDate.getDate();
		if(DateDiff($("#expityTime").val(),currentDate) > 0){
			$("#verifyExpityTime").val("OK");
			$("#expityTimeMsg").html("");
		}else{
			$("#verifyExpityTime").val("NO");
			$("#expityTimeMsg").html("租約有效期必須大于今天");
		}	
	}
    function checkExpityWarningTime(){
    	if(DateDiff($("#expityTime").val(),$("#expityWarningTime").val()) > 0){
    		$("#verifyExpityWarningTime").val("OK");
    		$("#expityWarningTimeMsg").html("");	
		}else{
			$("#verifyExpityWarningTime").val("NO");
			$("#expityWarningTimeMsg").html("租約有效期必須大于到期提醒日");			
		}
	}
	function checkSubmit(){
		if($("#shopsName").val()!=""){
			$("#verifyName").val("OK");
			$("#shopsNameAgain").html("");
		}else{
			$("#verifyName").val("NO");
			$("#shopsNameAgain").html("必須要填商鋪名稱");
		}
		if( $("#verifyName").val()=="OK" && $("#verifyExpityTime").val()=="OK" && $("#verifyExpityWarningTime").val()=="OK"){
			$("#sForm").submit();
	 	}else{
			alert("數據校驗錯誤，請修正后再確認");
		}

	}
	//比较日期
	function DateDiff(d1, d2) {
	    var result = Date.parse(d1.replace(/-/g, "/")) - Date.parse(d2.replace(/-/g, "/"));
	    return result;
	}
	function getReport() {
		$("#processing").removeAttr("hidden");
		$("#sc").attr("hidden","true");
		var reportStartDate = $("#reportStartDate").val();
		var reportEndDate = $("#reportEndDate").val();
		var shopCode=$("#shopsCode").val().trim();
		window.location.href="${pageContext.request.contextPath}/boss/queryReport.do?reportStartDate="+reportStartDate+"&reportEndDate="+reportEndDate+"&shopCode="+shopCode;
	}
</script>
<body>
<div class="content">
    <s:form id="sForm" action="/boss/modifyShop.action">
	<div class="main">
	  <table cellspacing="0" cellpadding="0" border="0" width="60%" class="king xy pw">
	  <tbody>
	    <tr>
	      <th colspan="2">商鋪信息修改</th>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">商鋪號碼 </td>
	      <td class="tl t_left" width="50%"><!--  -->
	          <input type="text" maxlength="20" id="shopsCode" name="shopsInfo.shopsCode" readonly class="b_g"
	                 value="<s:property  escape="false" value="shopsInfo.shopsCode"/>" 
	                 onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>                                      
              <s:hidden id="verifyCode" value="OK"/>                         
           </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">商鋪名稱 </td>
	      <td class="tl t_left">
	          <input id="shopsName" name="shopsInfo.shopsName" onfocus="this.className='inp1m'" maxlength="20" onblur="this.className='inp1'" onblur="javascript:checkShopsName();"
	                value="<s:property  escape="false" value="shopsInfo.shopsName"/>" onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\@\.]/g,'')" class="b_g"/>	         
	          <span class="red" id="shopsNameAgain"></span>
	          <s:hidden id="verifyName" value="OK"/>
	          <input type="hidden" id="oldName" name="oldName" value="<s:property value="shopsInfo.shopsName" escape="false" />" />
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">商鋪租約有效期 </td>
	      <td class="tl t_left">	          
	          <input type="text" id="expityTime" name="shopsRent.expityTime"  class="b_g"
	                 value="<s:date name="shopsInfo.expityTime" format="yyyy-MM-dd"/>"
	                 onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()} });"  onblur="javascript:checkExpityTime();"/>
	          <span  class="red" id="expityTimeMsg"></span>
	          <s:hidden id="verifyExpityTime" value="OK"/>        	          
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">商鋪到期提醒日 </td>
	      <td class="tl t_left">	          
	          <input type="text" id="expityWarningTime" name="shopsRent.expityWarningTime"  class="b_g"
	                 value="<s:date name="shopsInfo.expityWarningTime" format="yyyy-MM-dd"/>"
	                 onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()} });" onblur="javascript:checkExpityWarningTime();"/>
	          <span  class="red" id="expityWarningTimeMsg"></span>
	          <s:hidden id="verifyExpityWarningTime" value="OK"/>         	          
	      </td>
	    </tr>
	     <tr bgcolor="#ffffff">
	      <td class="tr">商鋪樣式 </td>
	      <td class="tl t_left">	          
	          <s:select name="shopsInfo.css" list="#{1:'春',2:'夏'}" listKey="key" listValue="value"/>	          
	      </td>
	    </tr>
	     <tr bgcolor="#ffffff">
	      <td class="tr">商鋪狀態 </td>
	      <td class="tl t_left">	          
	         <s:select name="shopsInfo.state" list="#{0:'開放',1:'凍結',2:'關閉'}" listKey="key" listValue="value"/>
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">備注</td>
	      <td class="tl t_left">
	         <input name="shopsInfo.remark" onfocus="this.className='inp1m'" onblur="this.className='inp1'" maxlength="20" class="b_g"
	                value="<s:property  escape="false" value="shopsInfo.remark"/>" onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\@\.]/g,'')"/>
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">总监注销注单</td>
	      <td class="tl t_left">
	        <s:select name="shopsInfo.enableBetCancel" list="#{'Y':'启用','N':'關閉'}" listKey="key" listValue="value"/>
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">用新方法计算报表</td>
	      <td class="tl t_left">
	        <s:select name="computReportType" list="#{'N':'關閉','Y':'启用'}" listKey="key" listValue="value"/>
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">重新生成报表统计(一次最多生成一个月)</td>
	      <td class="tl t_left">
	        <input type="text" id="reportStartDate"  class="b_g"
	                 onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()} });" />
	        至
	        <input type="text" id="reportEndDate" class="b_g"
	                 onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()} });" />
	      <input type="button" value="生成"  id="sc" onclick="getReport();" /><span id="processing" hidden="true">正在处理中。。。。</span>
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">报表计算状态</td>
	      <td class="tl t_left">
	        <s:property value="#request.computeStatus"/>
	      </td>
	    </tr>
	    <%-- <tr bgcolor="#ffffff">
	      <td class="tr">发邮件人帐号</td>
	      <td class="tl">
	          <input type="text" maxlength="20" name="shopsInfo.sendMailAccount" class="b_g" value="<s:property  escape="false" value="shopsInfo.sendMailAccount"/>"/>   	          
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">发邮件人密码</td>
	      <td class="tl">
	          <input type="text" maxlength="20" name="shopsInfo.sendMailPassword" class="b_g" value="<s:property  escape="false" value="shopsInfo.sendMailPassword"/>"/>   	          
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">发邮件人SMTP服务器</td>
	      <td class="tl">
	          <input type="text" maxlength="20" name="shopsInfo.sendMailSMTP" class="b_g" value="<s:property  escape="false" value="shopsInfo.sendMailSMTP"/>"/>   	          
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">发邮件人地址</td>
	      <td class="tl">
	          <input type="text" maxlength="20" name="shopsInfo.sendMailAddress" class="b_g" value="<s:property  escape="false" value="shopsInfo.sendMailAddress"/>"/>   	          
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">收邮件人地址1</td>
	      <td class="tl">
	          <input type="text" maxlength="20" name="shopsInfo.getMailAddress1" class="b_g" value="<s:property  escape="false" value="shopsInfo.getMailAddress1"/>"/>   	          
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">收邮件人地址2</td>
	      <td class="tl">
	          <input type="text" maxlength="20" name="shopsInfo.getMailAddress2" class="b_g" value="<s:property  escape="false" value="shopsInfo.getMailAddress2"/>"/>   	          
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">收邮件人地址3</td>
	      <td class="tl">
	          <input type="text" maxlength="20" name="shopsInfo.getMailAddress3" class="b_g" value="<s:property  escape="false" value="shopsInfo.getMailAddress3"/>"/>   	          
	      </td>
	    </tr> --%>
	  </tbody>
	</table>
	<div class="tj t_left">
	     <input type="button" value="確認" class="btn_4" name="" onclick="checkSubmit();"></input>
	</div>
	</div>
  </s:form>
</div>
</body>

</html>
