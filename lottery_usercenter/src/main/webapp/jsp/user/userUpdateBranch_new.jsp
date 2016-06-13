<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/admin/user.js"></script>
<script src="${pageContext.request.contextPath}/js/jQuery.md5.js" type="text/javascript"></script>
<script>
$(document).ready(function() {
	var args1  = /[\':;*?~`!@#$%^&+=-_{}\[\]\<\>\(\),\.']/; 
	var args2  = /[^\d.]/g; 
	
	var maxRate=$('#jq_maxRate').val();
	 $("#chiefRate").keyup(function(){     
	        var tmptxt=$(this).val();     
	       if($(this).val() != ""){
	    	   $(this).val(tmptxt.replace(args1,"").replace(args2,""));
	       }
	       //if($(this).val()>maxRate)
			//   $(this).val(maxRate);
	    }).bind("paste",function(){     
	        var tmptxt=$(this).val();      
	        $(this).val(tmptxt.replace(args1,"").replace(args2,""));   
	    }).css("ime-mode", "disabled");
	 $("#credLine").keyup(function(){     
	        var tmptxt=$(this).val();     
	       if($(this).val() != ""){
	    	   $(this).val(tmptxt.replace(args1,"").replace(args2,""));
	       }
	       var k = new change($(this).val()); 
	       $("#jq_ch_money").html(k.pri_ary());
	    }).bind("paste",function(){     
	        var tmptxt=$(this).val();     
	        $(this).val(tmptxt.replace(args1,"").replace(args2,""));    
	    }).css("ime-mode", "disabled");
	  
	 var k = new change($('#credLine').val()); 
     $("#jq_ch_money").html(k.pri_ary());  
     
     
/*  	function checkSubmit() {
 		//信用額度校驗
		var credLine = $("##credLine").val();
		
	
		if($('#chiefRate').val()<4)
		{
			  alert("'總監占成' 不可低於 4% ，請重新設定！！！");
			  return false;
		}
		$("#sForm").submit();
		
	} */
     
	getMaxRate(${branchStaffExt.ID},3);
     
});
function checkSubmit()
{
	var minCredLine=parseInt('${branchStaffExt.totalCreditLine}')-parseInt('${branchStaffExt.availableCreditLine}');
	
	var maxRate=$('#jq_maxRate').val();
	
	//add by peter add password validation
	var password=$("#userPassword").val();
	if(null !=  password && password != "" && !passwordCheck(password))
	{
		//alert("false");
		$("#userPassword").focus();
		return false;
	}
	
	if(!$.trim($('#credLine').val()))
	{
		alert("信用額度 不能为空");
		return false;
	}
	if(!$.isNumeric($('#credLine').val()))
	{
		return false;
	}
	
	if(parseInt($("#chiefRate").val())<4)
	{
		alert("总监占成不能低于4%");
		return false;
	}
	if(eval($("#chiefRate").val())>parseInt(maxRate))
	{
		alert("总监占成不能高于"+maxRate+"%");
		$("#chiefRate").focus();
		return false;
	}
	if(parseInt($("#credLine").val())<parseInt(minCredLine))
	{
		alert("信用额度值不能小于已经使用的额度");
		return false;
	}
	//用户密码加密
	if(null !=  password && password != ""){
		//如果用户修改了密码才需要加密
		$("#userPassword").val($.md5(password).toUpperCase());
	}
	$("#sForm").submit();
	$("#userPassword").val("");
	
}

function onChechHasBetSuccess(json) {
	if (null != json && "" != json) {
		var hasBet = json.hasBet;
		if (hasBet != "true") {// 当天没有投注
			$("#chiefRate").removeAttr("disabled");
			$(":radio[name='branchStaffExt.replenishment']")
					.removeAttr("disabled");
			$(":radio[name='branchStaffExt.leftOwner']").removeAttr(
					"disabled");
		}
	}
}
function onGetMaxRateSuccess(json) {
	if (null != json && "" != json) {
		var maxRate = json.maxRate;
		if (null != maxRate && "" != maxRate) {
			$("#maxRate").html(maxRate);

		}
	}
}
</script>


</head>
<body>
<div id="content">
<s:form id="sForm" action="%{pageContext.request.contextPath}/user/updateByBranchStaff.action?searchType=%{searchType}&searchValue=%{searchValue}&searchUserStatus=%{searchUserStatus}" method="post" target="_self">
<table width="100%" border="0" >
  <tr>
    <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="30%" valign="middle">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="1%"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
                <td width="99%" align="left" class="F_bold">&nbsp;分公司 -&gt; 修改</td>
              </tr>
            </table>
            </td>
            <td align="right" width="70%">
            </td>
            </tr></table></td>
        <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_07.gif" width="16" height="30" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0">
      <tr>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
        <td align="center"><!--修改开始   -->
 <table width="100%" border="0px" cellspacing="0" cellpadding="0" class="mt4 king higher">
  <tr>
    <th colspan="2"><strong>賬戶资料</strong></th>
  </tr>
  <tr>
    <td width="18%" class="t_right even4">分公司帳號</td>
    <td width="82%" class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"> ${branchStaffExt.account}
        <input type="hidden" value="${branchStaffExt.account}" maxlength="20" id="account" name="branchStaffExt.account"></td>
        <td> <td>【
        <s:radio name="branchStaffExt.flag" id="flag" list="#{'0':'启用','2':'冻结','1':'停用'}" label="分公司状态" />】<span class="blue ml10"></span></td></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_right even4">新密碼</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"><input id="userPassword" type="text" class=""
							value="" name="branchStaffExt.userPwd" maxlength="15"></input></td>
        <td><span class="blue ml10"></span></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_right even4">分公司名稱</td>
    <td class="t_left"><input name="branchStaffExt.chsName" type="text"  maxlength="20" value="${branchStaffExt.chsName }"/></td>
  </tr>
  <tr>
    <td class="t_right even4">信用額度</td>
    <td class="t_left">
    <input type="text"  id="credLine" name="branchStaffExt.totalCreditLine" value="${branchStaffExt.totalCreditLine}" maxlength="9" /><span id="jq_ch_money" style="color:red; padding-left: 5px; font-weight: bold;"></span>『 可  '回收' 馀额  ${branchStaffExt.availableCreditLine} 』 </td>
  </tr>
  <tr>
    <td class="t_right even4">總監占成</td>
    <td class="t_left"><input name="branchStaffExt.chiefRate"  size="6" maxlength="10" id="chiefRate" value="${branchStaffExt.chiefRate }" disabled="true"/>
      % &nbsp;最高可設占成&nbsp;<span id="maxRate"></span>%</td>
  </tr>
  
  <tr>
    <td class="t_right even4">開放公司報表</td>
    <td class="t_left"><s:radio name="branchStaffExt.openReport" id="openReport" list="#{'0':'顯示','1':'禁看'}" label="開放公司報表" >
						</s:radio>
     </td>
  </tr>
  
  <tr>
    <td class="t_right even4">補貨功能</td>
    <td class="t_left">   
    <s:radio name="branchStaffExt.replenishment" id="replenishment" list="#{'0':'啟用','1':'禁用'}" label="補貨功能" disabled="true"></s:radio> 
     </td>
  </tr>
  
  <tr>
    <td class="t_right even4">占餘成數歸</td>
    <td class="t_left">
    <s:radio name="branchStaffExt.leftOwner"  list="#{'0':'總监','1':'分公司'}" disabled="true"/>
    <span class="blue ml10">(包括下線補貨注單)</span>
     </td>
  </tr>
  
</table>

          <!--  修改结束 --></td>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
        <td align='center'><input name="" type="button" class="btn2" value="确定" onclick="checkSubmit();"/>
        <input name="" type="button" class="btn2" value="取消" onclick="self.location='${pageContext.request.contextPath}/user/queryBranchStaff.action?type=userBranch&searchType=${searchType}&searchValue=${searchValue}&searchUserStatus=${searchUserStatus}'"/></td>
	   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
<s:token/>
</s:form>
<input type="hidden" name="maxRate" id="jq_maxRate" value="${maxRate}"/>
<input type="hidden"  id="jq_hasBet" value="${hasBet}"/>
</div>
</body>
</html>
