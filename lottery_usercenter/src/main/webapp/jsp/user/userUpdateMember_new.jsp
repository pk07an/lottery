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
	var leftCredit=$('#jq_left_credit').val();
	var maxRate=$('#jq_max_rate').val();
	var owner_cred=$('#jq_owner_credit').val();
	 $("#upperRate").keyup(function(){     
	        var tmptxt=$(this).val();     
	       if($(this).val() != ""){
	    	   $(this).val(tmptxt.replace(args1,"").replace(args2,""));
	       }
// 	       if(parseInt($(this).val())>parseInt(maxRate)){
// 	    	   alert("占成不可高於"+maxRate+"% 請重新設定");
// 	    	   $(this).focus();
// 	    	   $(this).val(maxRate);
// 	       }
	    }).bind("paste",function(){     
	        var tmptxt=$(this).val();     
	        $(this).val(tmptxt.replace(args1,"").replace(args2,""));    
	    }).css("ime-mode", "disabled");
	 
	 $("#credLine").keyup(function(){     
	        var tmptxt=$(this).val();     
	       if($(this).val() != ""){
	    	   $(this).val(tmptxt.replace(args1,"").replace(args2,""));
	       }
	       
	       if((parseInt($(this).val())-parseInt(owner_cred))>parseInt(leftCredit)){
	    	   if(owner_cred!=0){
	    	   	$(this).val(owner_cred);
	    	   }else{
	    		   $(this).val("");
	    	   }
	       }
	       var k = new change($(this).val()); 
	       $("#jq_ch_money").html(k.pri_ary());
	    }).bind("paste",function(){     
	        var tmptxt=$(this).val();     
	        $(this).val(tmptxt.replace(args1,"").replace(args2,""));   
	    }).css("ime-mode", "disabled");
	 var k = new change($('#credLine').val()); 
     $("#jq_ch_money").html(k.pri_ary());   
	  
	
});


function checkSubmit() {
	
	var minCredLine=${memberStaffExt.totalCreditLine}-${memberStaffExt.availableCreditLine<0?0:memberStaffExt.availableCreditLine};
	var leftCredit=$('#jq_left_credit').val();
	var maxRate=$('#jq_max_rate').val();
	var owner_cred=$('#jq_owner_credit').val();	
    var upd_cred=$('#credLine').val();
  	
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
	if((parseInt(upd_cred)-parseInt(owner_cred))>parseInt(leftCredit))
	{
		alert("設定'信用額度'超過上級'可用餘額'");
		return false;
	}
	else if(parseInt($("#credLine").val())<parseInt(minCredLine))
	{
		alert("信用额度值不能小于已经使用的额度");
		return false;
	}
	if(parseInt(maxRate)<parseInt($('#upperRate').val()))
	{
		alert("占成不可高於"+maxRate+"% 請重新設定");
		$("#upperRate").focus();
		return false;
	}
	 var item = $("input[name='agentStaffExt.rateRestrict']:checked").val();
		
		if(item==1&&$('#jq_below_rate_limit').val().length==0)
		 {
			alert("限制下級占成數不能為空");
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

</script>


</head>
<body>
<div id="content">
<s:form id="sForm" action="%{pageContext.request.contextPath}/user/updateMember.action?searchAccount=%{searchAccount}&parentAccount=%{parentAccount}&searchUserStatus=%{searchUserStatus}&type=%{type}&searchType=%{searchType}&searchValue=%{searchValue}" method="post" target="_self">
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
                <td width="99%" align="left" class="F_bold">
                <s:if test="#request.managerType==2">總監 &nbsp;直屬</s:if>
    <s:if test="#request.managerType==3">分公司 &nbsp;直屬</s:if>
    <s:if test="#request.managerType==4">股東 &nbsp;直屬</s:if>
    <s:if test="#request.managerType==5">總代理 &nbsp;直屬</s:if>
                
               會員-&gt; 修改</td>
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
    <td class="t_right even4">上級<s:if test="#request.managerType==2">總監</s:if>
    <s:if test="#request.managerType==3">分公司</s:if>
    <s:if test="#request.managerType==4">股東</s:if>
    <s:if test="#request.managerType==5">總代理</s:if>
    <s:if test="#request.managerType==6">代理</s:if>
    
    
    </td>
   
    
     <td class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74">${managerAccount}<input type="hidden" class="b_g" value="${managerAccount}" name="agentStaffExt.account" readonly="readonly" /></td>
        <td><span>名稱：${managerName} 餘額：${managerAvailable}</span></td>
      </tr>
    </table></td>
 
  </tr>
  
    <tr>
    <td width="18%" class="t_right even4">會員帳號</td>
    <td width="82%" class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"> ${memberStaffExt.account}
        <input type="hidden" value="${memberStaffExt.account}" maxlength="20" id="account" name="memberStaffExt.account"></td>
        <td> <td>【
        <s:radio name="memberStaffExt.flag" id="flag" list="#{'0':'启用','2':'冻结','1':'停用'}" label="會員状态" />】<span class="blue ml10"></span></td></td>
      </tr>
    </table></td>
  </tr>
 
  <tr>
    <td class="t_right even4">登入密碼</td>
    <td class="t_left"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="none_border">
      <tr>
        <td width="74"><input id="userPassword" type="text" class=""
							value="" name="memberStaffExt.userPwd" maxlength="15"></input></td>
        <td><span class="blue ml10"></span></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_right even4">會員名稱</td>
    <td class="t_left"><s:textfield name="memberStaffExt.chsName" type="text"  /></td>
  </tr>
  <tr>
    <td class="t_right even4">信用額度</td>
    <td class="t_left"><s:textfield  id="credLine" name="memberStaffExt.totalCreditLine"  maxlength="9" /> <span id="jq_ch_money" style="color:red; padding-left: 5px; font-weight: bold;"></span>『 可  '回收' 馀额<s:if test="memberStaffExt.availableCreditLine>memberStaffExt.totalCreditLine">   ${memberStaffExt.totalCreditLine} </s:if><s:else>${memberStaffExt.availableCreditLine<0?0:memberStaffExt.availableCreditLine} </s:else>』
    </td>
  </tr>
  <tr>
    <td class="t_right even4">
    <s:if test="#request.managerType==2">總監</s:if>
    <s:if test="#request.managerType==3">分公司</s:if>
    <s:if test="#request.managerType==4">股東</s:if>
    <s:if test="#request.managerType==5">總代理</s:if>
    <s:if test="#request.managerType==6">代理</s:if>占成</td>
    <td class="t_left"><input type="text" value="${memberStaffExt.rate}" name="memberStaffExt.rate"  size="6" maxlength="10" id="upperRate" <s:if test="#request.hasBet==true">disabled="true"</s:if>/>
      % &nbsp;最高可設占成&nbsp;
      <s:if test="#request.rateRestrict==1">
			<s:property value="#request.belowRateLimit"/>
	  </s:if>
	  <s:else>
      	<s:property value="#request.rate"/>
      </s:else>
      %</td>
  </tr>
  
  <tr>
    <td class="t_right even4">開放盤口</td>
    <td class="t_left">
    
    <s:if test="#request.hasBet==true">
     <s:radio name="memberStaffExt.plate" id="plate" list="#{'A':' A 盤','B':' B 盤','C':' C 盤'}" label="開放盤口"  disabled="true"/>
    </s:if>
    <s:else>
     <s:radio name="memberStaffExt.plate" id="plate" list="#{'A':' A 盤','B':' B 盤','C':' C 盤'}" label="開放盤口"  />
    </s:else>
    
    
   
     </td>
  </tr>
  
  
  
  
  <tr>
    <td class="t_right even4">退水設定</td>
    <td class="t_left">
     <s:if test="#request.hasBet==true">
      <s:select list="#{'0':'水全退到底','0.3':' 賺取0.3退水','0.5':' 賺取0.5退水','1':' 賺取1.0退水','1.5':' 賺取1.5退水','2':' 賺取2退水','2.5':' 賺取2.5退水','100':' 賺取所有退水'}"   name="memberStaffExt.backWater" disabled="true"></s:select>
    </s:if>
    <s:else>
      <s:select list="#{'0':'水全退到底','0.3':' 賺取0.3退水','0.5':' 賺取0.5退水','1':' 賺取1.0退水','1.5':' 賺取1.5退水','2':' 賺取2退水','2.5':' 賺取2.5退水','100':' 賺取所有退水'}"   name="memberStaffExt.backWater"></s:select>
    </s:else>
    
    
  
     </td>
  </tr>
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
        <input name="" type="button" class="btn2" value="取消" onclick="self.location='${pageContext.request.contextPath}/user/queryMemberStaff.action?account=${searchAccount}&parentAccount=${parentAccount}&searchUserStatus=${searchUserStatus}&type=${type}&searchType=${searchType}&searchValue=${searchValue}'"/></td>
	   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
<s:token/>
</s:form>
<input type="hidden"  id="jq_left_credit" name="leftcredit" value="${managerAvailable}" />

 	<s:if test="#request.rateRestrict==1">
		<input type="hidden" id="jq_max_rate" value="${belowRateLimit}"/>
	</s:if>
	<s:else>
		<input type="hidden" id="jq_max_rate" name="maxrate"  value="${rate}"/>
	</s:else>


<s:hidden id="jq_owner_credit"  name="memberStaffExt.totalCreditLine"/>
<input type="hidden"  id="jq_hasBet" value="${hasBet}"/>
</div>
</body>
</html>
