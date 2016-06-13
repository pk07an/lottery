<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/drag.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.form.js" type="text/javascript"></script> 
<script language="javascript" src="${pageContext.request.contextPath}/js/lottery.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {	
	
	var options = { 
	        success:       showResponse,	 
	        url:       context+'/user/ajaxUpdateUserStatus.do',
	        type:      'post',
	        dataType:  'json'		     
	    }; 
	 
	    $('#statusForm').submit(function() { 
	        $(this).ajaxSubmit(options); 
	        return false; 
	    }); 	
	
	
//获取元素的坐标FOR 补货
var getElementOffset = function _getElementOffset($this) {
	
    var offset = $this.position();
    var left = offset.left-100;
    var top = offset.top + $this.height();  //把text元素本身的高度也算上
    return { left: left, top: top };
};

//设置元素的坐标
var setElementOffset = function _setElementOffset($this, offset) {
	
    $this.css({ "position": "absolute", "left": offset.left, "top": offset.top });
    return $this;
};
$('input[name=status]').click(function() {
	$('#statusForm').submit();

});
$('a[name="userStatus"]').click(function() {

	var data=$(this).attr("data");
	var account=data.split("|")[0];
	var status=data.split("|")[1];
	var userType=data.split("|")[2];
	
	//alert(account);
	//alert(status);
	$("input[name=status][value="+status+"]").attr("checked",true); 
    
    $("#jq_account").val(account);
    $("#jq_userType").val(userType);
	var offset = getElementOffset($(this));
	 var $div = setElementOffset($("#jq_user_status"), offset);
	    $div.show();   
	   window.setTimeout("hideChangePop()",5000);
});


});
function hideChangePop()
{
	$('#jq_user_status').hide();
	
}
// post-submit callback 

</script>
</head>
<body>

	<!-- 手写设置赔率 -->
<div id="jq_user_status" style="background-color:#fff; display:none;">
   <s:form id="statusForm" action=""  method="post" >
   <input type="hidden" name="account" id="jq_account"/>
   <input type="hidden" name="userType" id="jq_userType"/>
 
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king" style="width:136px;">
		    <tr style="height:30px"><th colspan="3" >修改"<s:property value="account" escape="false" />"用戶狀態</th></tr>
		    <tr style="height:30px">
			    <td colspan="3" ><input type="radio" name="status" value="0"/>启用<input type="radio" name="status" value="2" disabled="disabled"/>冻结<input type="radio" name="status" value="1"/>停用</td>
			    
		    </tr>	 
     </table>
   </s:form>
</div>
<!-- 總監赔率修改结束-->
</body>
<script language="javascript" src="../js/Forbid.js" type="text/javascript"></script>
</html>