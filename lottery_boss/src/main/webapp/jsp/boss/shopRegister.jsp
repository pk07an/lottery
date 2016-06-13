<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
<script language="javascript">
	//查詢商铺号
	function checkShopsCode(){
		var i = $("#shopsCode").val();
	   	if (i!=""){	   	  		
	   		var strUrl = '${pageContext.request.contextPath}/boss/ajaxFindShopsCode.action';		   							
			var queryUrl = encodeURI(encodeURI(strUrl));
			$.ajax({
				type : "POST",
				url : queryUrl,
				data : {"shopsCode":i},
				dataType : "json",
				success : callBack
			});           	
	   	}		
		
	}
	function callBack(json) {
		if (json.count>0){
			$("#shopsCodeAgain").html("該商鋪號已存在");
			$("#confirm").attr("disabled","disabled");
			$("#verifyCode").val("NO");
		}else{
			$("#shopsCodeAgain").html("");
			$("#confirm").attr("disabled",false);
			$("#verifyCode").val("OK");
		}
	}
	function checkShopsName(){
		var	i = $("#shopsName").val();
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
	   	}else{
	   		$("#shopsNameAgain").html("必須要填商鋪名稱");
	   		$("#confirm").attr("disabled","disabled");
	   	}		
	}
	function callBackName(json) {
		if (json.count>0){
			$("#shopsNameAgain").html("該商鋪名稱已存在");
			$("#confirm").attr("disabled","disabled");
			$("#verifyName").val("NO");
		}else{
			$("#shopsNameAgain").html("");
			$("#confirm").attr("disabled",false);
			$("#verifyName").val("OK");
		}
	}
	//查詢總監的登錄帳號
	function checkChiefAccount(){
		var i = $("#chiefAccount").val();		
	   	if (i!=""){	   	  		
	   		var strUrl = '${pageContext.request.contextPath}/user/ajaxQueryUserName.action';		   							
			var queryUrl = encodeURI(encodeURI(strUrl));
			$.ajax({
				type : "POST",
				url : queryUrl,
				data : {"account":i},
				dataType : "json",
				success : callBackChiefAccount
			});           	
	   	}else{
	   		$("#chiefAccountMsg").html("必須要填總监登錄帳號");
	   		$("#confirm").attr("disabled","disabled");
	   	}				
	}
	function callBackChiefAccount(json) {
		if (json.count>0){
			$("#chiefAccountMsg").html("總監的登錄帳號已存在");
			$("#confirm").attr("disabled","disabled");
			$("#verifyChiefAccount").val("NO");
		}else{
			$("#chiefAccountMsg").html("");
			$("#confirm").attr("disabled",false);
			$("#verifyChiefAccount").val("OK");
		}
	}
	//查詢總監名稱
	function checkChiefChsName(){
		var i = $("#chiefChsName").val();
		
	   	if (i!=""){	   	  		
	   		var strUrl = '${pageContext.request.contextPath}/user/ajaxQueryChsName.action';		   							
			var queryUrl = encodeURI(encodeURI(strUrl));
			$.ajax({
				type : "POST",
				url : queryUrl,
				data : {"account":i},
				dataType : "json",
				success : callBackChiefChsName
			});           	
	   	}else{
	   		$("#chiefChsNameMsg").html("必須要填總监名稱");
	   		$("#confirm").attr("disabled","disabled");
	   	}		
		
	}
	function callBackChiefChsName(json) {
		if (json.count>0){
			$("#chiefChsNameMsg").html("總監的中文名已存在");
			$("#confirm").attr("disabled","disabled");
			$("#verifyChiefChsName").val("NO");
		}else{
			$("#chiefChsNameMsg").html("");
			$("#confirm").attr("disabled",false);
			$("#verifyChiefChsName").val("OK");
		}
	}
	function checkExpityTime(){
		var dDate = new Date();
		currentDate = dDate.getFullYear() + "-" + (dDate.getMonth()+1) + "-" + dDate.getDate();
		if(DateDiff($("#expityTime").val(),currentDate) > 0){
			$("#verifyExpityTime").val("OK");
			$("#confirm").attr("disabled",false);
			$("#expityTimeMsg").html("");
		}else{
			$("#verifyExpityTime").val("NO");
			$("#confirm").attr("disabled","disabled");
			$("#expityTimeMsg").html("租約有效期必須大于今天");
		}	
	}
    function checkExpityWarningTime(){
    	if(DateDiff($("#expityTime").val(),$("#expityWarningTime").val()) > 0){
    		$("#verifyExpityWarningTime").val("OK");
    		$("#confirm").attr("disabled",false);
    		$("#expityWarningTimeMsg").html("");
		}else{
			$("#verifyExpityWarningTime").val("NO");
			$("#confirm").attr("disabled","disabled");
			$("#expityWarningTimeMsg").html("租約有效期必須大于到期提醒日");			
		}
	}
	function checkSubmit(){				
		if($("#shopsCode").val()!=""){
			$("#verifyCode").val("OK");
			$("#shopsCodeAgain").html("");
		}else{
			$("#verifyCode").val("NO");
			$("#shopsCodeAgain").html("必須要填商鋪號");
		}
		if($("#shopsName").val()!=""){
			$("#verifyName").val("OK");
			$("#shopsNameAgain").html("");
		}else{
			$("#verifyName").val("NO");
			$("#shopsNameAgain").html("必須要填商鋪名稱");
		}
		/* alert($("#verifyCode").val() + $("#verifyName").val() + $("#verifyExpityTime").val() +
				$("#verifyExpityWarningTime").val() + $("#verifyChiefAccount").val() + $("#verifyChiefChsName").val()); */
				
		if($("#verifyCode").val()=="OK" && $("#verifyName").val()=="OK" && $("#verifyExpityTime").val()=="OK" 
				&& $("#verifyExpityWarningTime").val()=="OK" && $("#verifyChiefAccount").val()=="OK" && $("#verifyChiefChsName").val()=="OK"){
			$("#confirm").attr("disabled","disabled");
			$("#sForm").submit();
	 	}else{
	 		$("#confirm").attr("disabled","disabled");
			alert("數據校驗錯誤，請修正后再確認");
		}
		
	}
	//比较日期
	function DateDiff(d1, d2) {
	    var result = Date.parse(d1.replace(/-/g, "/")) - Date.parse(d2.replace(/-/g, "/"));
	    return result;
	}
</script>
<body>
<div class="content">
    <s:form id="sForm" action="shopRegisterSubmit.action" namespace="boss">
	<div class="main">
	  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy pw">
	  <tbody>
	    <tr>
	      <th colspan="2">111商鋪注冊 </th>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr" width="50%">商鋪號碼 </td>
	      <td class="tl t_left" width="50%">
	          <input type="text" maxlength="10" id="shopsCode" name="shopsInfo.shopsCode" class="b_g"
	                 value="<s:property  escape="false" value="shopsInfo.shopsCode"/>" onblur="javascript:checkShopsCode();" 
	                 onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>                                      
              <span  class="red" id="shopsCodeAgain"></span> 	
              <s:hidden id="verifyCode" value=""/>            
           </td>	      
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">商鋪名稱 </td>
	      <td class="tl t_left">
	          <s:textfield id="shopsName" name="shopsInfo.shopsName" maxlength="10" onfocus="this.className='inp1m'" onblur="javascript:checkShopsName();" cssClass="b_g"></s:textfield>
	          <span  class="red" id="shopsNameAgain"></span>
	          <s:hidden id="verifyName" value=""/>
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">商鋪租約有效期 </td>
	      <td class="tl t_left">
	          <s:textfield id="expityTime" name="shopsRent.expityTime" 
	          onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()} });" onblur="javascript:checkExpityTime();" cssClass="b_g"></s:textfield>
	          <span  class="red" id="expityTimeMsg"></span>
	           <s:hidden id="verifyExpityTime" value=""/> 
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">商鋪到期提醒日 </td>
	      <td class="tl t_left">
	          <s:textfield id="expityWarningTime" name="shopsRent.expityWarningTime" 
	          onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){$(this).blur()} });" onblur="javascript:checkExpityWarningTime();" cssClass="b_g"></s:textfield>
	          <span  class="red" id="expityWarningTimeMsg"></span>
	          <s:hidden id="verifyExpityWarningTime" value=""/> 
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">總監登錄帳號</td>
	      <td class="tl t_left">
	          <input type="text" maxlength="20" id="chiefAccount" name="chiefAccount" value="" onBlur="javascript:checkChiefAccount();" 
	                 class="b_g"/>   
	          <span  class="red" id="chiefAccountMsg"></span>
	          <s:hidden id="verifyChiefAccount" value=""/> 
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">總監用戶名</td>
	      <td class="tl t_left">
	          <input type="text" maxlength="20" id="chiefChsName" name="chiefChsName" value="" onBlur="javascript:checkChiefChsName();" class="b_g"/>   	          
	          <span  class="red" id="chiefChsNameMsg"></span>
	          <s:hidden id="verifyChiefChsName" value=""/> 
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">備注</td>
	      <td class="tl t_left">
	         <s:textfield name="shopsInfo.remark" maxlength="20" onfocus="this.className='inp1m'" onblur="this.className='inp1'" cssClass="b_g"></s:textfield>
	      </td>
	    </tr>
	    <!-- <tr bgcolor="#ffffff">
	      <td class="tr">发邮件人帐号</td>
	      <td class="tl">
	          <input type="text" maxlength="20" name="shopsInfo.sendMailAccount" onafterpaste="this.value=this.value.replace(/\D/g,'')" onkeyup="this.value=this.value.replace(/\D/g,'')" class="b_g"/>   	          
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">发邮件人密码</td>
	      <td class="tl">
	          <input type="text" maxlength="20" name="shopsInfo.sendMailPass" onafterpaste="this.value=this.value.replace(/\D/g,'')" onkeyup="this.value=this.value.replace(/\D/g,'')" class="b_g"/>   	          
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">发邮件人SMTP服务器</td>
	      <td class="tl">
	          <input type="text" maxlength="20" name="shopsInfo.sendMailSMTP" onafterpaste="this.value=this.value.replace(/\D/g,'')" onkeyup="this.value=this.value.replace(/\D/g,'')" class="b_g"/>   	          
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">发邮件人地址</td>
	      <td class="tl">
	          <input type="text" maxlength="20" name="shopsInfo.sendMailAddress" onafterpaste="this.value=this.value.replace(/\D/g,'')" onkeyup="this.value=this.value.replace(/\D/g,'')" class="b_g"/>   	          
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">收邮件人地址1</td>
	      <td class="tl">
	          <input type="text" maxlength="20" name="shopsInfo.getMailAddress1" onafterpaste="this.value=this.value.replace(/\D/g,'')" onkeyup="this.value=this.value.replace(/\D/g,'')" class="b_g"/>   	          
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">收邮件人地址2</td>
	      <td class="tl">
	          <input type="text" maxlength="20" name="shopsInfo.getMailAddress2" onafterpaste="this.value=this.value.replace(/\D/g,'')" onkeyup="this.value=this.value.replace(/\D/g,'')" class="b_g"/>   	          
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">收邮件人地址3</td>
	      <td class="tl">
	          <input type="text" maxlength="20" name="shopsInfo.getMailAddress3" onafterpaste="this.value=this.value.replace(/\D/g,'')" onkeyup="this.value=this.value.replace(/\D/g,'')" class="b_g"/>   	          
	      </td>
	    </tr> -->
	    <s:hidden name="shopsInfo.css" value="1"/>
	  </tbody>
	</table>
	<div class="tj" style="width:92px;"><input type="button" value="確認" class="btn_4" id="confirm" disabled="disabled" onClick="checkSubmit();"/></div>
	</div>
  </s:form>
</div>
</body>

</html>
