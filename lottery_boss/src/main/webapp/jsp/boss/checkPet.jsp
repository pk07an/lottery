<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<script language="javascript">
function checkPet(){
	var	i = $("#periodsNum").val();
	if (i!=""){	
   		var strUrl = '${pageContext.request.contextPath}/boss/ajaxCheckPet.action?shopsCode=${code}';		
		var queryUrl = encodeURI(encodeURI(strUrl));
		$.ajax({
			type : "POST",
			url : queryUrl,
			data : {"periodsNum":i,"type":$("#type").val()},
			dataType : "json",
			success : callBackName
		});           	
   	}		
   	
}
function callBackName(json) {
	$("#status").html(json.status);
}
</script>
<body>
<div class="content">
	<div class="main">
	  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king2 xy pw">
	  <tbody>
	    <tr>
	      <th colspan="2">请录入要校验的盘期(校验)</th>
	    </tr>
	    <tr>
	    
		      <td class="t_right" width="229">
		               <select style="width:120px" class="mr10" id="type">
					       <option value="GDKLSF">廣東快樂十分</option>
					      <option value="CQSSC">重慶時時彩</option>
					      <option value="BJSC">北京賽車(PK10)</option>
					      <option value="K3">江苏骰寶(快3)</option>
					      <option value="NC">幸运农场</option>  
					    </select>
		      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">盘期</td>
	      <td class="tl">
	          <input type="text" maxlength="20" id="periodsNum" onafterpaste="this.value=this.value.replace(/\D/g,'')" onkeyup="this.value=this.value.replace(/\D/g,'')"/>   	          
	      </td>
	    </tr>
	    <tr bgcolor="#ffffff">
	      <td class="tr">对比结果</td>
	      <td id="status">&nbsp;</td>
	    </tr>
	  </tbody>
	</table>
	<div class="tj">
	     <input type="button" value="確認" class="btn_4" onclick="checkPet()"/>
	</div>
	</div>
</div>
</body>

</html>
