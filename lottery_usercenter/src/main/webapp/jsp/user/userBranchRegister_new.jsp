
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ page import="com.npc.lottery.common.Constant "%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css" ></link>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
  <script language="javascript" src="${pageContext.request.contextPath}/js/jquery.form.js" type="text/javascript"></script>  
<script language="javascript" src="${pageContext.request.contextPath}/js/admin/user.js"></script>
<script src="${pageContext.request.contextPath}/js/jQuery.md5.js" type="text/javascript"></script>
<script language="javascript">



$(document).ready(function() {
	var args1  = /[\':;*?~`!@#$%^&+=-_{}\[\]\<\>\(\),\.']/; 
	var args2  = /[^\d.]/g; 
	//總監占成校驗 
	$("#chiefRate").keyup(function(){     
	        var tmptxt=$(this).val();     
	       if($(this).val() != ""){
	    	   $(this).val(tmptxt.replace(args1,"").replace(args2,""));
	       }
	       if(eval($(this).val())>100)
			   $(this).val(100);
	       //if($(this).val()<4)
	    	//{
			 //  $(this).val(4);
			 
	    	//}
	    }).bind("paste",function(){     
	        var tmptxt=$(this).val();     
	        $(this).val(tmptxt.replace(args1,"").replace(args2,"")); 
	    }).css("ime-mode", "disabled");
	//分公司信用額度校驗 
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
	
});

    //帳號唯一校驗
	function checkShopsName() {
		var account = $("#account").val().NoSpace();
		if (account != "") {
			var strUrl = '${pageContext.request.contextPath}/user/ajaxQueryUserName.action?account='
					+ account;
			var queryUrl = encodeURI(encodeURI(strUrl));
			$.ajax({
				type : "POST",
				url : queryUrl,
				data : {},
				dataType : "json",
				success : callBackName
			});
		}
		$("#account").val(account);
	}
	function callBackName(json) {
		if (json.count > 0) {
			$("#accountAgain").html("選擇帳號已被占用，不可用!!!");
			$("#accountAgain").addClass("red");
			$("#verifyName").val("NO");
			$("#account").focus();
		} else {
			$("#accountAgain").html("選擇帳號可用!!!");
			$("#accountAgain").removeClass("red");
			$("#verifyName").val("OK");
		}
	}
	/*  var options = { 
		        beforeSubmit:  validate, 
		        success:       showResponse,	 
		        url:       context+'/member/ajaxcqsscBallSub.do?time='+new Date().getTime(),
		        type:      'post',
		        dataType:  'json'		     
		    }; 
		 
		    $('#sForm').submit(function() { 
		        $(this).ajaxSubmit(options); 
		        return false; 
		    });  */
	    
	function checkSubmit() {
		var accountName = $("#account").val();
		
		var args  = /[\':;*?~`!@#$%^&+={}\[\]\<\>\(\),\.']/; 
		if($.trim(accountName).length==0)
		{
			alert("帳號不能為空！");
			$("#account").focus();
			return false;
		}
		
		if(args.test(accountName)) 
		{
			alert("帳號不能包含特殊字符:~@#$%^&*<>()");
			$("#account").focus();
			return false;
		}
		
		//add by peter add password validation
		var password=$("#userPassword").val();
		if(!passwordCheck(password))
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
			
		}
		if ($("#verifyName").val() == "OK"  && $("#userAccount").val() != 0) {
			
		} else {
			alert("户名稱數據有誤，請修正后再確認");
			return false;
		}
		
		//fixed by peter 
		if(eval($('#chiefRate').val())<4 || $('#chiefRate').val()=="" || $('#chiefRate').val() ==null)
		{
			  alert("'總監占成' 不可低於 4% ，請重新設定！！！");
			  $('#chiefRate').focus();
			  return false;
		}
		//ajaxCreateUser();
         var account=$("#account").val();
       //add by peter for 提交时灰化按纽
 		$(".btn2").attr("disabled", true);
		alert("账户'"+account+"' 已成功新增，请设定退水及投注限额");
		//用户密码加密
		$("#userPassword").val($.md5(password).toUpperCase());
		$("#sForm").submit();
		$("#userPassword").val("");
		
		
	}
	
</script>
<body>

<div id="content">
<s:form id="sForm" action="%{pageContext.request.contextPath}/user/saveBranch.action?searchUserStatus=0" method="post">
<input type="hidden" name="forward" id="jq_forward" value="/user/queryBranchcommission.action" />
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
                <td width="99%" align="left" class="F_bold">&nbsp;分公司 -&gt; 新增</td>
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
        <td width="74"><input type="text" value="" class="" maxlength="20" id="account" name="branchStaffExt.account" onblur="javascript:checkShopsName();" maxlength="20"></input></td>
        <td><span id="accountAgain" style="margin-left:5px"></span> <s:hidden id="verifyName" value="" /> <s:hidden name="branchStaffExt.userType" value="3" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="t_right even4">登入密碼</td>
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
    <td class="t_left"><input name="branchStaffExt.chsName" type="text" value="" maxlength="20" /></td>
  </tr>
  <tr>
    <td class="t_right even4">信用額度</td>
    <td class="t_left"><input type="text"  id="credLine" name="branchStaffExt.totalCreditLine" value="0" maxlength="9" /><span id="jq_ch_money" style="color:red; padding-left: 5px; font-weight: bold;"></span> </td>
  </tr>
  <tr>
    <td class="t_right even4">總監占成</td>
    <td class="t_left"><input name="branchStaffExt.chiefRate" value="" size="6" maxlength="10" id="chiefRate"/>
      % &nbsp;最高可設占成&nbsp;100%</td>
  </tr>
  
  <tr>
    <td class="t_right even4">開放公司報表</td>
    <td class="t_left"><s:radio name="branchStaffExt.openReport" id="openReport" list="#{'0':'顯示','1':'禁看'}" label="開放公司報表" value="1">
						</s:radio>
     </td>
  </tr>
  
  <tr>
    <td class="t_right even4">補貨功能</td>
    <td class="t_left"><s:radio name="branchStaffExt.replenishment" id="replenishment" list="#{'0':'啟用','1':'禁用'}" label="補貨功能" value="0"></s:radio> 
     </td>
  </tr>
  
  <tr>
    <td class="t_right even4">占餘成數歸</td>
    <td class="t_left"><input type="radio" name="branchStaffExt.leftOwner"  value='0'  checked/>總監 <input type="radio" name="branchStaffExt.leftOwner"  value='1' />分公司<span class="blue ml10">(包括下線補貨注單)</span>
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
</div>
</body>
</html>
