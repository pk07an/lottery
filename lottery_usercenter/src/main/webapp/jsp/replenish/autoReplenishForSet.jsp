<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.lang.Exception, com.npc.lottery.replenish.entity.ReplenishAuto"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
Map<String,ReplenishAuto> map =(Map<String,ReplenishAuto>)request.getAttribute("autoMap");  
%>
<head>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<base target="content" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
<script type="text/javascript">
 
$(document).ready(function() {
	$(".valiteMoney").keyup(function(){     
	    var tmptxt=$(this).val();     
	   if($(this).val() != ""){
		   $(this).val(tmptxt.replace(/[^\d.]/g,""));
	   }
	   
	}).bind("paste",function(){     
	    var tmptxt=$(this).val();     
	    $(this).val(tmptxt.replace(/[^\d.]/g,""));     
	}).css("ime-mode", "disabled");
	
	var options = { 
	 	       // target:        '#output2',   // target element(s) to be updated with server response 
	 	        beforeSubmit:  validate, // pre-submit callback 
	 	        success:       showResponse,  // post-submit callback 		 
	 	        // other available options: 
	 	        url:       'ajaxAutoReplenishSet.action ',         // override for form's 'action' attribute 
	 	        type:      'post',        // 'get' or 'post', override for form's 'method' attribute 
	 	        dataType:  'json'       // 'xml', 'script', or 'json' (expected server response type) 		     
	 	        //resetForm: true        // reset the form after successful submit 
	 	    }; 
    $('#sform').submit(function() { 
        $(this).ajaxSubmit(options);
        return false; 
    }); 
});

function showResponse(jsonData, statusText)  { 
	
	if(jsonData.error)
	{
		alert(jsonData.error);
	    return false;
	}	
    if(jsonData.success)
	{
    	window.location = "${pageContext.request.contextPath}/replenishauto/autoReplenish.action?type=personalAdmin_autoReplenish";
    	alert(jsonData.success);

	}
} 

function selectedCheckBox(){
	
	var type=$("#type").val();
	var playType=$("#palyType").val();
	var temp;
	if(type=="ALL")
	{
		if(playType=='ALL')
		{
			temp = $("[name=checkbox]");
		}
		if(playType=="BALL")
		{
			temp = $("[name=checkbox][value*=BALL]");
		}
		if(playType=="DOUBLE_SIDE")
		{	
			temp = $("[name=checkbox][value$=DX],[value$=DS]:not([value$=K3_DS]),[value$=LH]:not([value$=R2LH]):not([value$=R3LH]),[value$=SJ]");
		}
		if(playType=="OTHER")
		{
			temp = $("[name=checkbox]:not([value*=BALL]):not([value$=DX]):not([value$=DS]):not([value$=LH]):not([value$=SJ]),[value$=R2LH],[value$=R3LH],[value=K3_DS]");
		}
	}
	if(type=="GD")
	{
		if(playType=="ALL")
		{	
			temp = $("[name=checkbox][value^=GDKLSF]");	
		}
		if(playType=="BALL")
		{	
			temp = $("[name=checkbox][value^=GDKLSF_BALL]");
		}
		if(playType=="DOUBLE_SIDE")
		{
			temp = $("[name=checkbox][value^=GDKLSF][value$=DX],[value^=GDKLSF][value$=DS],[value^=GDKLSF][value$=LH]:not([value$=R2LH]):not([value$=R3LH])");
		}
		if(playType=="OTHER")
		{	
			temp = $("[name=checkbox][value^=GDKLSF]:not([value*=BALL]):not([value$=DX]):not([value$=DS]):not([value$=LH]),[name=checkbox][value^=GDKLSF][value$=R2LH],[name=checkbox][value^=GDKLSF][value$=R3LH]");
		}
	}
	if(type=="CQ")
	{
		if(playType=="ALL")
		{	
			temp = $("[name=checkbox][value^=CQSSC]");
		}
		if(playType=="BALL")
		{
			temp = $("[name=checkbox][value^=CQSSC_BALL]");
		}
		if(playType=="DOUBLE_SIDE")
		{	
			temp = $("[name=checkbox][value^=CQSSC][value$=DX],[value^=CQSSC][value$=DS]");		
		}
		if(playType=="OTHER")
		{	
			temp = $("[name=checkbox][value^=CQSSC]:not([value*=BALL]):not([value$=DX]):not([value$=DS])");
		}
	}
	if(type=="NC")
	{
		if(playType=="ALL")
		{
			temp = $("[name=checkbox][value^=NC]");
		}
		if(playType=="BALL")
		{
			temp = $("[name=checkbox][value^=NC_BALL]");
		}
		if(playType=="DOUBLE_SIDE")
		{
			temp = $("[name=checkbox][value^=NC][value$=DX],[value^=NC][value$=DS],[value^=NC][value$=LH]:not([value$=R2LH]):not([value$=R3LH])")
		}
		if(playType=="OTHER")
		{
			temp = $("[name=checkbox][value^=NC]:not([value*=BALL]):not([value$=DX]):not([value$=DS]):not([value$=LH]),[value^=NC][value$=R2LH],[name=checkbox][value^=NC][value$=R3LH]");
		}
	}
	if(type=="JS")
	{
		if(playType=="ALL")
		{
			temp = $("[name=checkbox][value^=K3]");
		}
		if(playType=="BALL")
		{
			temp = $("[name=checkbox][value^=K3_BALL]");
		}
		if(playType=="DOUBLE_SIDE")
		{
			temp = $("[name=checkbox][value^=K3][value$=DX],[value^=K3][value$=SJ]");
		}
		if(playType=="OTHER")
		{
			temp = $("[name=checkbox][value^=K3]:not([value$=DX]):not([value$=SJ])");
		}
	}
	if(type=="BJ")
	{
		if(playType=="ALL")
		{
			temp = $("[name=checkbox][value^=BJ]");
			
		}
		if(playType=="BALL")
		{
			temp = $("[name=checkbox][value^=BJ_BALL]");
			
		}
		if(playType=="DOUBLE_SIDE")
		{
			temp = $("[name=checkbox][value^=BJ][value$=DX],[value^=BJ][value$=DS],[value^=BJ][value$=LH]");
		}
		if(playType=="OTHER")
		{
			temp = $("[name=checkbox][value^=BJ]:not([value*=BALL]):not([value$=DX]):not([value$=DS]):not([value$=LH])");
		}
	}
	var num=$("#lines").val();
	if(temp.attr("checked")=="checked"){
		temp.attr("checked",false);
		temp.parent().parent().prev().prev().prev().children().attr("disabled","disabled");	
	}else{
		if((isNaN(num)||num.substring(0,1)=="0")&&num!=""){
			
			alert("请输入有效数字");
		}else{
			if(num!=""){
				temp.parent().parent().prev().prev().prev().children().val(num); 
			}
			temp.attr("checked",true);
			temp.parent().parent().prev().prev().prev().children().removeAttr("disabled");
		}
		
		
	}
}



function validate(){
	
}

</script>
</head>
<body>
<%if(null == map) map = new HashMap<String,ReplenishAuto>();%>
<%try{ %>
<!--内容开始-->
<div id="content">
  <s:form id="sform" action="#" method="post">
  <s:hidden name="submitType" value="GD"></s:hidden>
  <input type="hidden" id="cqPeriodNum" value="<s:property value="#request.cqPeriodNum"/>"/>
  <input type="hidden" id="gdPeriodNum" value="<s:property value="#request.gdPeriodNum"/>"/>
  <input type="hidden" id="bjPeriodNum" value="<s:property value="#request.bjPeriodNum"/>"/>
  <input type="hidden" id="k3PeriodNum" value="<s:property value="#request.k3PeriodNum"/>"/>
  <input type="hidden" id="ncPeriodNum" value="<s:property value="#request.ncPeriodNum"/>"/>
  <input type="hidden" id="hasBet" value="<s:property value="#request.hasBet"/>"/>
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="70%" valign="middle">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="1%"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
                <td width="99%" align="left" class="F_bold">&nbsp;自动补货设定
                	<span style="padding-left:50px;">彩种：<select id="type">
                		<option value="ALL">全部</option>
                		<option value="GD">广东快乐十分</option>
                		<option value="CQ">重庆时时彩</option>
                		<option value="BJ">北京赛车</option>
                		<option value="JS">江苏骰宝</option>
                		<option value="NC">幸运农场</option>
                	</select>&nbsp;&nbsp;&nbsp;
                	玩法：<select id="palyType">
                		<option value="ALL">全部</option>
                		<option value="BALL">号球</option>
                		<option value="DOUBLE_SIDE">双面</option>
                		<option value="OTHER">其他</option>
                	</select>&nbsp;&nbsp;&nbsp;
                	调整额度：
                	<input type="text" id="lines" style = "width:30px;" />&nbsp;&nbsp;&nbsp;
                	<input type="button" onclick="selectedCheckBox();" value="确定"/></span>
                </td>
              </tr>
            </table>
            </td>
            <td align="right" width="70%"><span style="display:none;">&nbsp;</span></td>
            </tr>
            </table></td>
        <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_07.gif" width="16" height="30" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
     <!--广东-->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
        <td>
        <!--内容操作开始-->
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="mt4">
          <tr>
            <td width="50%" align="left" valign="top">
            <table width="99%" border="0" cellspacing="0" cellpadding="0" class="king">
            <colgroup>
              <col width="16%" />
              <col width="24%" />
              <col width="24%" />
              <col width="16%" />
              <col width="12%" />
              <col width="7%" />
            </colgroup>
         <tr>
          <th>補貨類型</th>
          <th>選擇計算方式</th>
          <th>控制額度</th>
          <th>最低可調額度</th>
          <th>起補額度</th>
          <th>啟用</th>
        </tr>
        <tr>
          <th colspan="6"><strong>廣東快樂十分</strong></th>
          <input type="hidden" name="GDKLSF_BUTTON" value="1"/>
          <input value="GDKLSF_BUTTON" type="checkbox" name="checkbox" checked="checked" style="display:none" />
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第一球</td>
          <td><select name="select" id="select">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_BALL_FIRST" value="<%=map.get("GDKLSF_BALL_FIRST").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;" class="t_left"/></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_FIRST").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_FIRST").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_BALL_FIRST" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_BALL_FIRST").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第二球</td>
          <td><select name="select" id="select2">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_BALL_SECOND" value="<%=map.get("GDKLSF_BALL_SECOND").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_SECOND").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_SECOND").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_BALL_SECOND" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_BALL_SECOND").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第三球</td>
          <td><select name="select" id="select3">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_BALL_THIRD" value="<%=map.get("GDKLSF_BALL_THIRD").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_THIRD").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_THIRD").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_BALL_THIRD" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_BALL_THIRD").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第四球</td>
          <td><select name="select" id="select4">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_BALL_FORTH" value="<%=map.get("GDKLSF_BALL_FORTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_FORTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_FORTH").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_BALL_FORTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_BALL_FORTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第五球</td>
          <td><select name="select" id="select5">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_BALL_FIFTH" value="<%=map.get("GDKLSF_BALL_FIFTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_FIFTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_FIFTH").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_BALL_FIFTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_BALL_FIFTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第六球</td>
          <td><select name="select" id="select28">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_BALL_SIXTH" value="<%=map.get("GDKLSF_BALL_SIXTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_SIXTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_SIXTH").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_BALL_SIXTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_BALL_SIXTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第七球</td>
          <td><select name="select" id="select27">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_BALL_SEVENTH" value="<%=map.get("GDKLSF_BALL_SEVENTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_SEVENTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_SEVENTH").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_BALL_SEVENTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_BALL_SEVENTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第八球</td>
          <td><select name="select" id="select26">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_BALL_EIGHTH" value="<%=map.get("GDKLSF_BALL_EIGHTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_EIGHTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_EIGHTH").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_BALL_EIGHTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_BALL_EIGHTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-8大小</td>
          <td><select name="select" id="select25">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_1T8_DX" value="<%=map.get("GDKLSF_1T8_DX").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_1T8_DX").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_1T8_DX").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_1T8_DX" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_1T8_DX").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-8單雙</td>
          <td><select name="select" id="select24">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_1T8_DS" value="<%=map.get("GDKLSF_1T8_DS").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_1T8_DS").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_1T8_DS").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_1T8_DS" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_1T8_DS").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-8尾數大小</td>
          <td><select name="select" id="select23">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_1T8_WDX" value="<%=map.get("GDKLSF_1T8_WDX").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_1T8_WDX").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_1T8_WDX").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_1T8_WDX" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_1T8_WDX").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-8合數單雙</td>
          <td><select name="select" id="select6">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_1T8_HSDS" value="<%=map.get("GDKLSF_1T8_HSDS").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_1T8_HSDS").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_1T8_HSDS").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_1T8_HSDS" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_1T8_HSDS").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-8方位</td>
          <td><select name="select" id="select10">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_1T8_FW" value="<%=map.get("GDKLSF_1T8_FW").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_1T8_FW").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_1T8_FW").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_1T8_FW" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_1T8_FW").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-8中發白</td>
          <td><select name="select" id="select9">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_1T8_ZFB" value="<%=map.get("GDKLSF_1T8_ZFB").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_1T8_ZFB").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_1T8_ZFB").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_1T8_ZFB" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_1T8_ZFB").getState())) out.println("checked"); %>/></label></td>
        </tr>
    
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>總和大小</td>
         <td><select name="select" id="select8">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_ZHDX" value="<%=map.get("GDKLSF_ZHDX").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_ZHDX").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_ZHDX").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_ZHDX" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_ZHDX").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>總和單雙</td>
          <td><select name="select" id="select7">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_ZHDS" value="<%=map.get("GDKLSF_ZHDS").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_ZHDS").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_BALL_THIRD").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_ZHDS" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_ZHDS").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>總和尾數大小</td>
          <td><select name="select" id="select11">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_ZHWSDX" value="<%=map.get("GDKLSF_ZHWSDX").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_ZHWSDX").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_ZHWSDX").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_ZHWSDX" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_ZHWSDX").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>龍虎</td>
          <td><select name="select" id="select29">
            <option>下注額</option>
          </select></td>
          <td><input name="GDKLSF_DOUBLESIDE_LH" value="<%=map.get("GDKLSF_DOUBLESIDE_LH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_DOUBLESIDE_LH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_DOUBLESIDE_LH").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_DOUBLESIDE_LH" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_DOUBLESIDE_LH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>任選二</td>
          <td><select name="select" id="select30">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="GDKLSF_STRAIGHTTHROUGH_RX2" value="<%=map.get("GDKLSF_STRAIGHTTHROUGH_RX2").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX2").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX2").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_STRAIGHTTHROUGH_RX2" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_STRAIGHTTHROUGH_RX2").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>選二連直</td>
          <td><select name="select" id="select32">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="GDKLSF_STRAIGHTTHROUGH_R2LH" value="<%=map.get("GDKLSF_STRAIGHTTHROUGH_R2LH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R2LH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R2LH").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_STRAIGHTTHROUGH_R2LH" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_STRAIGHTTHROUGH_R2LH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>選二連組</td>
          <td><select name="select" id="select32">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="GDKLSF_STRAIGHTTHROUGH_R2LZ" value="<%=map.get("GDKLSF_STRAIGHTTHROUGH_R2LZ").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R2LZ").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R2LZ").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_STRAIGHTTHROUGH_R2LZ" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_STRAIGHTTHROUGH_R2LZ").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>任選三</td>
          <td><select name="select" id="select33">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="GDKLSF_STRAIGHTTHROUGH_RX3" value="<%=map.get("GDKLSF_STRAIGHTTHROUGH_RX3").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX3").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX3").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_STRAIGHTTHROUGH_RX3" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_STRAIGHTTHROUGH_RX3").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>選三前直</td>
          <td><select name="select" id="select35">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="GDKLSF_STRAIGHTTHROUGH_R3LH" value="<%=map.get("GDKLSF_STRAIGHTTHROUGH_R3LH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R3LH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R3LH").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_STRAIGHTTHROUGH_R3LH" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_STRAIGHTTHROUGH_R3LH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>選三前組</td>
          <td><select name="select" id="select35">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="GDKLSF_STRAIGHTTHROUGH_R3LZ" value="<%=map.get("GDKLSF_STRAIGHTTHROUGH_R3LZ").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R3LZ").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_R3LZ").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_STRAIGHTTHROUGH_R3LZ" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_STRAIGHTTHROUGH_R3LZ").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>任選四</td>
          <td><select name="select" id="select36">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="GDKLSF_STRAIGHTTHROUGH_RX4" value="<%=map.get("GDKLSF_STRAIGHTTHROUGH_RX4").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX4").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX4").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_STRAIGHTTHROUGH_RX4" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_STRAIGHTTHROUGH_RX4").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>任選五</td>
          <td><select name="select" id="select6">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="GDKLSF_STRAIGHTTHROUGH_RX5" value="<%=map.get("GDKLSF_STRAIGHTTHROUGH_RX5").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX5").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("GDKLSF_STRAIGHTTHROUGH_RX5").getMoneyRep()%></td>
          <td><label><input value="GDKLSF_STRAIGHTTHROUGH_RX5" type="checkbox" name="checkbox" <%if("1".equals(map.get("GDKLSF_STRAIGHTTHROUGH_RX5").getState())) out.println("checked"); %>/></label></td>
        </tr>
      	 <tr>
          <th colspan="6"><strong>江苏骰寶(快3)</strong></th>
          <input type="hidden" name="K3_BUTTON" value="1"/>
          <input value="K3_BUTTON" type="checkbox" name="checkbox" checked="checked" style="display:none" />
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>大小</td>
          <td><select name="select" id="select">
            <option>下注額</option>
          </select></td>
          <td><input name="K3_DX" value="<%=map.get("K3_DX").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;" class="t_left"/></td>
          <td class="t_right"><%=map.get("K3_DX").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("K3_DX").getMoneyRep()%></td>
          <td><label><input value="K3_DX" type="checkbox" name="checkbox" <%if("1".equals(map.get("K3_DX").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>三军</td>
          <td><select name="select" id="select">
            <option>下注額</option>
          </select></td>
          <td><input name="K3_SJ" value="<%=map.get("K3_SJ").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;" class="t_left"/></td>
          <td class="t_right"><%=map.get("K3_SJ").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("K3_SJ").getMoneyRep()%></td>
          <td><label><input value="K3_SJ" type="checkbox" name="checkbox" <%if("1".equals(map.get("K3_SJ").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>圍骰</td>
          <td><select name="select" id="select">
            <option>下注額</option>
          </select></td>
          <td><input name="K3_WS" value="<%=map.get("K3_WS").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;" class="t_left"/></td>
          <td class="t_right"><%=map.get("K3_WS").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("K3_WS").getMoneyRep()%></td>
          <td><label><input value="K3_WS" type="checkbox" name="checkbox" <%if("1".equals(map.get("K3_WS").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>全骰</td>
          <td><select name="select" id="select">
            <option>下注額</option>
          </select></td>
          <td><input name="K3_QS" value="<%=map.get("K3_QS").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;" class="t_left"/></td>
          <td class="t_right"><%=map.get("K3_QS").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("K3_QS").getMoneyRep()%></td>
          <td><label><input value="K3_QS" type="checkbox" name="checkbox" <%if("1".equals(map.get("K3_QS").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>點數</td>
          <td><select name="select" id="select">
            <option>下注額</option>
          </select></td>
          <td><input name="K3_DS" value="<%=map.get("K3_DS").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;" class="t_left"/></td>
          <td class="t_right"><%=map.get("K3_DS").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("K3_DS").getMoneyRep()%></td>
          <td><label><input value="K3_DS" type="checkbox" name="checkbox" <%if("1".equals(map.get("K3_DS").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>長牌</td>
          <td><select name="select" id="select">
            <option>下注額</option>
          </select></td>
          <td><input name="K3_CP" value="<%=map.get("K3_CP").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;" class="t_left"/></td>
          <td class="t_right"><%=map.get("K3_CP").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("K3_CP").getMoneyRep()%></td>
          <td><label><input value="K3_CP" type="checkbox" name="checkbox" <%if("1".equals(map.get("K3_CP").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>短牌</td>
          <td><select name="select" id="select">
            <option>下注額</option>
          </select></td>
          <td><input name="K3_DP" value="<%=map.get("K3_DP").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;" class="t_left"/></td>
          <td class="t_right"><%=map.get("K3_DP").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("K3_DP").getMoneyRep()%></td>
          <td><label><input value="K3_DP" type="checkbox" name="checkbox" <%if("1".equals(map.get("K3_DP").getState())) out.println("checked"); %>/></label></td>
        </tr>
      </table>
      </td>
       <td width="50%" align="left" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
         <colgroup>
           <col width="16%" />
           <col width="24%" />
           <col width="24%" />
           <col width="16%" />
           <col width="12%" />
           <col width="8%" />
           </colgroup>
        <tr>
          <th>補貨類型</th>
          <th>選擇計算方式</th>
          <th>控制額度</th>
          <th>最低可調額度</th>
          <th>起補額度</th>
          <th>啟用</th>
        </tr>
        <tr>
          <th colspan="6"><strong>重慶时时彩</strong></th>
          <input type="hidden" name="CQSSC_BUTTON" value="1"/>
          <input value="CQSSC_BUTTON" type="checkbox" name="checkbox" checked="checked" style="display:none" />
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第一球</td>
          <td><select name="select23" id="select41">
            <option>下注額</option>
          </select></td>
          <td><input name="CQSSC_BALL_FIRST" value="<%=map.get("CQSSC_BALL_FIRST").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;" /></td>
          <td class="t_right"><%=map.get("CQSSC_BALL_FIRST").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("CQSSC_BALL_FIRST").getMoneyRep()%></td>
          <td><label><input value="CQSSC_BALL_FIRST" type="checkbox" name="checkbox" <%if("1".equals(map.get("CQSSC_BALL_FIRST").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第二球</td>
          <td><select name="select23" id="select40">
            <option>下注額</option>
          </select></td>
          <td><input name="CQSSC_BALL_SECOND" value="<%=map.get("CQSSC_BALL_SECOND").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("CQSSC_BALL_SECOND").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("CQSSC_BALL_SECOND").getMoneyRep()%></td>
          <td><label><input value="CQSSC_BALL_SECOND" type="checkbox" name="checkbox" <%if("1".equals(map.get("CQSSC_BALL_SECOND").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第三球</td>
          <td><select name="select23" id="select39">
            <option>下注額</option>
          </select></td>
          <td><input name="CQSSC_BALL_THIRD" value="<%=map.get("CQSSC_BALL_THIRD").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("CQSSC_BALL_THIRD").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("CQSSC_BALL_THIRD").getMoneyRep()%></td>
          <td><label><input value="CQSSC_BALL_THIRD" type="checkbox" name="checkbox" <%if("1".equals(map.get("CQSSC_BALL_THIRD").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第四球</td>
          <td><select name="select23" id="select38">
            <option>下注額</option>
          </select></td>
          <td><input name="CQSSC_BALL_FORTH" value="<%=map.get("CQSSC_BALL_FORTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("CQSSC_BALL_FORTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("CQSSC_BALL_FORTH").getMoneyRep()%></td>
          <td><label><input value="CQSSC_BALL_FORTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("CQSSC_BALL_FORTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第五球</td>
          <td><select name="select23" id="select37">
            <option>下注額</option>
          </select></td>
          <td><input name="CQSSC_BALL_FIFTH" value="<%=map.get("CQSSC_BALL_FIFTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("CQSSC_BALL_FIFTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("CQSSC_BALL_FIFTH").getMoneyRep()%></td>
          <td><label><input value="CQSSC_BALL_FIFTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("CQSSC_BALL_FIFTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-5大小</td>
          <td><select name="select9" id="select17">
            <option>下注額</option>
          </select></td>
          <td><input name="CQSSC_1T5_DX" value="<%=map.get("CQSSC_1T5_DX").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("CQSSC_1T5_DX").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("CQSSC_1T5_DX").getMoneyRep()%></td>
          <td><label><input value="CQSSC_1T5_DX" type="checkbox" name="checkbox" <%if("1".equals(map.get("CQSSC_1T5_DX").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-5單雙</td>
          <td><select name="select8" id="select18">
            <option>下注額</option>
          </select></td>
          <td><input name="CQSSC_1T5_DS" value="<%=map.get("CQSSC_1T5_DS").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("CQSSC_1T5_DS").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("CQSSC_1T5_DS").getMoneyRep()%></td>
          <td><label><input value="CQSSC_1T5_DS" type="checkbox" name="checkbox" <%if("1".equals(map.get("CQSSC_1T5_DS").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>總和大小</td>
          <td><select name="select8" id="select19">
            <option>下注額</option>
          </select></td>
          <td><input name="CQSSC_ZHDX" value="<%=map.get("CQSSC_ZHDX").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("CQSSC_ZHDX").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("CQSSC_ZHDX").getMoneyRep()%></td>
          <td><label><input value="CQSSC_ZHDX" type="checkbox" name="checkbox" <%if("1".equals(map.get("CQSSC_ZHDX").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>總和單雙</td>
          <td><select name="select8" id="select20">
            <option>下注額</option>
          </select></td>
          <td><input name="CQSSC_ZHDS" value="<%=map.get("CQSSC_ZHDS").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("CQSSC_ZHDS").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("CQSSC_ZHDS").getMoneyRep()%></td>
          <td><label><input value="CQSSC_ZHDS" type="checkbox" name="checkbox" <%if("1".equals(map.get("CQSSC_ZHDS").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>龍虎和</td>
          <td><select name="select8" id="select21">
            <option>下注額</option>
          </select></td>
          <td><input name="CQSSC_LHH" value="<%=map.get("CQSSC_LHH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("CQSSC_LHH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("CQSSC_LHH").getMoneyRep()%></td>
          <td><label><input value="CQSSC_LHH" type="checkbox" name="checkbox" <%if("1".equals(map.get("CQSSC_LHH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>前三</td>
          <td><select name="select10" id="select12">
            <option>下注額</option>
          </select></td>
          <td><input name="CQSSC_THREE_FRONT" value="<%=map.get("CQSSC_THREE_FRONT").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("CQSSC_THREE_FRONT").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("CQSSC_THREE_FRONT").getMoneyRep()%></td>
          <td><label><input value="CQSSC_THREE_FRONT" type="checkbox" name="checkbox" <%if("1".equals(map.get("CQSSC_THREE_FRONT").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>中三</td>
          <td><select name="select11" id="select13">
            <option>下注額</option>
          </select></td>
          <td><input name="CQSSC_THREE_MID" value="<%=map.get("CQSSC_THREE_MID").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("CQSSC_THREE_MID").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("CQSSC_THREE_MID").getMoneyRep()%></td>
          <td><label><input value="CQSSC_THREE_MID" type="checkbox" name="checkbox" <%if("1".equals(map.get("CQSSC_THREE_MID").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>后三</td>
          <td><select name="select12" id="select14">
            <option>下注額</option>
          </select></td>
          <td><input name="CQSSC_THREE_LAST" value="<%=map.get("CQSSC_THREE_LAST").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("CQSSC_THREE_LAST").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("CQSSC_THREE_LAST").getMoneyRep()%></td>
          <td><label><input value="CQSSC_THREE_LAST" type="checkbox" name="checkbox" <%if("1".equals(map.get("CQSSC_THREE_LAST").getState())) out.println("checked"); %>/></label></td>
        </tr>
      </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
          <colgroup>
            <col width="16%" />
            <col width="24%" />
            <col width="24%" />
            <col width="16%" />
            <col width="12%" />
            <col width="8%" />
            </colgroup>
         <tr>
          <th colspan="6"><strong>北京賽車(PK10)</strong></th>
          <input type="hidden" name="BJ_BUTTON" value="1"/>
           <input value="BJ_BUTTON" type="checkbox" name="checkbox" checked="checked" style="display:none" />
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>冠軍</td>
          <td><select name="select23" id="select41">
            <option>下注額</option>
          </select></td>
          <td><input name="BJ_BALL_FIRST" value="<%=map.get("BJ_BALL_FIRST").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_BALL_FIRST").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_BALL_FIRST").getMoneyRep()%></td>
          <td><label><input value="BJ_BALL_FIRST" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_BALL_FIRST").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>亞軍</td>
          <td><select name="select23" id="select40">
            <option>下注額</option>
          </select></td>
          <td><input name="BJ_BALL_SECOND" value="<%=map.get("BJ_BALL_SECOND").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_BALL_SECOND").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_BALL_SECOND").getMoneyRep()%></td>
          <td><label><input value="BJ_BALL_SECOND" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_BALL_SECOND").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第三名</td>
          <td><select name="select23" id="select39">
            <option>下注額</option>
          </select></td>
          <td><input name="BJ_BALL_THIRD" value="<%=map.get("BJ_BALL_THIRD").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_BALL_THIRD").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_BALL_THIRD").getMoneyRep()%></td>
          <td><label><input value="BJ_BALL_THIRD" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_BALL_THIRD").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第四名</td>
          <td><select name="select23" id="select38">
            <option>下注額</option>
          </select></td>
          <td><input name="BJ_BALL_FORTH" value="<%=map.get("BJ_BALL_FORTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_BALL_FORTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_BALL_FORTH").getMoneyRep()%></td>
          <td><label><input value="BJ_BALL_FORTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_BALL_FORTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第五名</td>
          <td><select name="select23" id="select37">
            <option>下注額</option>
          </select></td>
          <td><input name="BJ_BALL_FIFTH" value="<%=map.get("BJ_BALL_FIFTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_BALL_FIFTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_BALL_FIFTH").getMoneyRep()%></td>
          <td><label><input value="BJ_BALL_FIFTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_BALL_FIFTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第六名</td>
          <td><select name="select9" id="select17">
           <option>下注額</option>
          </select></td>
          <td><input name="BJ_BALL_SIXTH" value="<%=map.get("BJ_BALL_SIXTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_BALL_SIXTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_BALL_SIXTH").getMoneyRep()%></td>
          <td><label><input value="BJ_BALL_SIXTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_BALL_SIXTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第七名</td>
          <td><select name="select8" id="select18">
            <option>下注額</option>
          </select></td>
          <td><input name="BJ_BALL_SEVENTH" value="<%=map.get("BJ_BALL_SEVENTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_BALL_SEVENTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_BALL_SEVENTH").getMoneyRep()%></td>
          <td><label><input value="BJ_BALL_SEVENTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_BALL_SEVENTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第八名</td>
          <td><select name="select8" id="select19">
            <option>下注額</option>
          </select></td>
          <td><input name="BJ_BALL_EIGHTH" value="<%=map.get("BJ_BALL_EIGHTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_BALL_EIGHTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_BALL_EIGHTH").getMoneyRep()%></td>
          <td><label><input value="BJ_BALL_EIGHTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_BALL_EIGHTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第九名</td>
          <td><select name="select8" id="select20">
            <option>下注額</option>
          </select></td>
          <td><input name="BJ_BALL_NINTH" value="<%=map.get("BJ_BALL_NINTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_BALL_NINTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_BALL_NINTH").getMoneyRep()%></td>
          <td><label><input value="BJ_BALL_NINTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_BALL_NINTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第十名</td>
          <td><select name="select8" id="select21">
            <option>下注額</option>
          </select></td>
          <td><input name="BJ_BALL_TENTH" value="<%=map.get("BJ_BALL_TENTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_BALL_TENTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_BALL_TENTH").getMoneyRep()%></td>
          <td><label><input value="BJ_BALL_TENTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_BALL_TENTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-10大小</td>
          <td><select name="select10" id="select12">
            <option>下注額</option>
          </select></td>
          <td><input name="BJ_1T10DX" value="<%=map.get("BJ_1T10DX").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_1T10DX").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_1T10DX").getMoneyRep()%></td>
          <td><label><input value="BJ_1T10DX" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_1T10DX").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-10單雙</td>
          <td><select name="select11" id="select13">
            <option>下注額</option>
          </select></td>
          <td><input name="BJ_1T10DS" value="<%=map.get("BJ_1T10DS").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_1T10DS").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_1T10DS").getMoneyRep()%></td>
          <td><label><input value="BJ_1T10DS" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_1T10DS").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-5龍虎</td>
          <td><select name="select12" id="select14">
            <option>下注額</option>
          </select></td>
          <td><input name="BJ_1T5LH" value="<%=map.get("BJ_1T5LH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_1T5LH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_1T5LH").getMoneyRep()%></td>
          <td><label><input value="BJ_1T5LH" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_1T5LH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>冠亞軍和大小 </td>
          <td><select name="select12" id="select14">
            <option>下注額</option>
          </select></td>
          <td><input name="BJ_GROUP_DX" value="<%=map.get("BJ_GROUP_DX").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_GROUP_DX").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_GROUP_DX").getMoneyRep()%></td>
          <td><label><input value="BJ_GROUP_DX" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_GROUP_DX").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>冠亞軍和單雙 </td>
          <td><select name="select12" id="select14">
            <option>下注額</option>
          </select></td>
          <td><input name="BJ_GROUP_DS" value="<%=map.get("BJ_GROUP_DS").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_GROUP_DS").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_GROUP_DS").getMoneyRep()%></td>
          <td><label><input value="BJ_GROUP_DS" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_GROUP_DS").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>冠亞軍和 </td>
          <td><select name="select12" id="select14">
            <option>下注額</option>
          </select></td>
          <td><input name="BJ_GROUP" value="<%=map.get("BJ_GROUP").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("BJ_GROUP").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("BJ_GROUP").getMoneyRep()%></td>
          <td><label><input value="BJ_GROUP" type="checkbox" name="checkbox" <%if("1".equals(map.get("BJ_GROUP").getState())) out.println("checked"); %>/></label></td>
        </tr>
        
         </table>
         <!--nc start-->
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
          <colgroup>
            <col width="16%" />
            <col width="24%" />
            <col width="24%" />
            <col width="16%" />
            <col width="12%" />
            <col width="8%" />
            </colgroup>
        <tr>
          <th colspan="6"><strong>幸运农场</strong></th>
          <input type="hidden" name="NC_BUTTON" value="1"/>
          <input value="NC_BUTTON" type="checkbox" name="checkbox" checked="checked" style="display:none" />
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第一球</td>
          <td><select name="select" id="select">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_BALL_FIRST" value="<%=map.get("NC_BALL_FIRST").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;" class="t_left"/></td>
          <td class="t_right"><%=map.get("NC_BALL_FIRST").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_BALL_FIRST").getMoneyRep()%></td>
          <td><label><input value="NC_BALL_FIRST" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_BALL_FIRST").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第二球</td>
          <td><select name="select" id="select2">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_BALL_SECOND" value="<%=map.get("NC_BALL_SECOND").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_BALL_SECOND").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_BALL_SECOND").getMoneyRep()%></td>
          <td><label><input value="NC_BALL_SECOND" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_BALL_SECOND").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第三球</td>
          <td><select name="select" id="select3">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_BALL_THIRD" value="<%=map.get("NC_BALL_THIRD").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_BALL_THIRD").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_BALL_THIRD").getMoneyRep()%></td>
          <td><label><input value="NC_BALL_THIRD" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_BALL_THIRD").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第四球</td>
          <td><select name="select" id="select4">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_BALL_FORTH" value="<%=map.get("NC_BALL_FORTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_BALL_FORTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_BALL_FORTH").getMoneyRep()%></td>
          <td><label><input value="NC_BALL_FORTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_BALL_FORTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第五球</td>
          <td><select name="select" id="select5">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_BALL_FIFTH" value="<%=map.get("NC_BALL_FIFTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_BALL_FIFTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_BALL_FIFTH").getMoneyRep()%></td>
          <td><label><input value="NC_BALL_FIFTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_BALL_FIFTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第六球</td>
          <td><select name="select" id="select28">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_BALL_SIXTH" value="<%=map.get("NC_BALL_SIXTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_BALL_SIXTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_BALL_SIXTH").getMoneyRep()%></td>
          <td><label><input value="NC_BALL_SIXTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_BALL_SIXTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第七球</td>
          <td><select name="select" id="select27">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_BALL_SEVENTH" value="<%=map.get("NC_BALL_SEVENTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_BALL_SEVENTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_BALL_SEVENTH").getMoneyRep()%></td>
          <td><label><input value="NC_BALL_SEVENTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_BALL_SEVENTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>第八球</td>
          <td><select name="select" id="select26">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_BALL_EIGHTH" value="<%=map.get("NC_BALL_EIGHTH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_BALL_EIGHTH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_BALL_EIGHTH").getMoneyRep()%></td>
          <td><label><input value="NC_BALL_EIGHTH" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_BALL_EIGHTH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-8大小</td>
          <td><select name="select" id="select25">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_1T8_DX" value="<%=map.get("NC_1T8_DX").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_1T8_DX").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_1T8_DX").getMoneyRep()%></td>
          <td><label><input value="NC_1T8_DX" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_1T8_DX").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-8單雙</td>
          <td><select name="select" id="select24">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_1T8_DS" value="<%=map.get("NC_1T8_DS").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_1T8_DS").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_1T8_DS").getMoneyRep()%></td>
          <td><label><input value="NC_1T8_DS" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_1T8_DS").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-8尾數大小</td>
          <td><select name="select" id="select23">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_1T8_WDX" value="<%=map.get("NC_1T8_WDX").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_1T8_WDX").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_1T8_WDX").getMoneyRep()%></td>
          <td><label><input value="NC_1T8_WDX" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_1T8_WDX").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-8合數單雙</td>
          <td><select name="select" id="select6">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_1T8_HSDS" value="<%=map.get("NC_1T8_HSDS").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_1T8_HSDS").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_1T8_HSDS").getMoneyRep()%></td>
          <td><label><input value="NC_1T8_HSDS" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_1T8_HSDS").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-8方位</td>
          <td><select name="select" id="select10">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_1T8_FW" value="<%=map.get("NC_1T8_FW").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_1T8_FW").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_1T8_FW").getMoneyRep()%></td>
          <td><label><input value="NC_1T8_FW" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_1T8_FW").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>1-8中發白</td>
          <td><select name="select" id="select9">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_1T8_ZFB" value="<%=map.get("NC_1T8_ZFB").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_1T8_ZFB").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_1T8_ZFB").getMoneyRep()%></td>
          <td><label><input value="NC_1T8_ZFB" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_1T8_ZFB").getState())) out.println("checked"); %>/></label></td>
        </tr>
    
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>總和大小</td>
         <td><select name="select" id="select8">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_ZHDX" value="<%=map.get("NC_ZHDX").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_ZHDX").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_ZHDX").getMoneyRep()%></td>
          <td><label><input value="NC_ZHDX" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_ZHDX").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>總和單雙</td>
          <td><select name="select" id="select7">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_ZHDS" value="<%=map.get("NC_ZHDS").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_ZHDS").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_BALL_THIRD").getMoneyRep()%></td>
          <td><label><input value="NC_ZHDS" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_ZHDS").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>總和尾數大小</td>
          <td><select name="select" id="select11">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_ZHWSDX" value="<%=map.get("NC_ZHWSDX").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_ZHWSDX").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_ZHWSDX").getMoneyRep()%></td>
          <td><label><input value="NC_ZHWSDX" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_ZHWSDX").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>龍虎</td>
          <td><select name="select" id="select29">
            <option>下注額</option>
          </select></td>
          <td><input name="NC_DOUBLESIDE_LH" value="<%=map.get("NC_DOUBLESIDE_LH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_DOUBLESIDE_LH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_DOUBLESIDE_LH").getMoneyRep()%></td>
          <td><label><input value="NC_DOUBLESIDE_LH" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_DOUBLESIDE_LH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>任選二</td>
          <td><select name="select" id="select30">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="NC_STRAIGHTTHROUGH_RX2" value="<%=map.get("NC_STRAIGHTTHROUGH_RX2").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_RX2").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_RX2").getMoneyRep()%></td>
          <td><label><input value="NC_STRAIGHTTHROUGH_RX2" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_STRAIGHTTHROUGH_RX2").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>選二連直</td>
          <td><select name="select" id="select32">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="NC_STRAIGHTTHROUGH_R2LH" value="<%=map.get("NC_STRAIGHTTHROUGH_R2LH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_R2LH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_R2LH").getMoneyRep()%></td>
          <td><label><input value="NC_STRAIGHTTHROUGH_R2LH" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_STRAIGHTTHROUGH_R2LH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>選二連組</td>
          <td><select name="select" id="select32">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="NC_STRAIGHTTHROUGH_R2LZ" value="<%=map.get("NC_STRAIGHTTHROUGH_R2LZ").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_R2LZ").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_R2LZ").getMoneyRep()%></td>
          <td><label><input value="NC_STRAIGHTTHROUGH_R2LZ" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_STRAIGHTTHROUGH_R2LZ").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>任選三</td>
          <td><select name="select" id="select33">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="NC_STRAIGHTTHROUGH_RX3" value="<%=map.get("NC_STRAIGHTTHROUGH_RX3").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_RX3").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_RX3").getMoneyRep()%></td>
          <td><label><input value="NC_STRAIGHTTHROUGH_RX3" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_STRAIGHTTHROUGH_RX3").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>選三前直</td>
          <td><select name="select" id="select35">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="NC_STRAIGHTTHROUGH_R3LH" value="<%=map.get("NC_STRAIGHTTHROUGH_R3LH").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_R3LH").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_R3LH").getMoneyRep()%></td>
          <td><label><input value="NC_STRAIGHTTHROUGH_R3LH" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_STRAIGHTTHROUGH_R3LH").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>選三前組</td>
          <td><select name="select" id="select35">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="NC_STRAIGHTTHROUGH_R3LZ" value="<%=map.get("NC_STRAIGHTTHROUGH_R3LZ").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_R3LZ").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_R3LZ").getMoneyRep()%></td>
          <td><label><input value="NC_STRAIGHTTHROUGH_R3LZ" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_STRAIGHTTHROUGH_R3LZ").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>任選四</td>
          <td><select name="select" id="select36">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="NC_STRAIGHTTHROUGH_RX4" value="<%=map.get("NC_STRAIGHTTHROUGH_RX4").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_RX4").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_RX4").getMoneyRep()%></td>
          <td><label><input value="NC_STRAIGHTTHROUGH_RX4" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_STRAIGHTTHROUGH_RX4").getState())) out.println("checked"); %>/></label></td>
        </tr>
        <tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
          <td>任選五</td>
          <td><select name="select" id="select6">
            <option>下注額[單組計]</option>
          </select></td>
          <td><input name="NC_STRAIGHTTHROUGH_RX5" value="<%=map.get("NC_STRAIGHTTHROUGH_RX5").getMoneyLimit()%>" size="8" maxlength="9" class="b_g valiteMoney" style="width:60%;"/></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_RX5").getTrueMoney()%></td>
          <td class="t_right"><%=map.get("NC_STRAIGHTTHROUGH_RX5").getMoneyRep()%></td>
          <td><label><input value="NC_STRAIGHTTHROUGH_RX5" type="checkbox" name="checkbox" <%if("1".equals(map.get("NC_STRAIGHTTHROUGH_RX5").getState())) out.println("checked"); %>/></label></td>
        </tr>
</table>
<!--nc end-->
         </td>
          </tr>
        </table>
         <!--内容操作结束-->
         <!-- 提示统一样式 -->
		<div style="background:#f1f1f1;border-top:1px solid #f6f6f6;text-align:center;height:22px;line-height:22px;font-weight:bold;color:#000;">註意：單筆低于"起補額度"時不自動補出；連碼為"單組"註額度計。</div>
		<div id="openPeriod" style="text-align:center;color:#FF0000;height:18px;line-height:18px;margin-top:10px;display:none;">註意：開盤中…… 虧損額 和 單組纍計註額 糢式只能上調無法下降；已開啓自動補貨項目不能關閉。</div>
		<!-- 提示统一样式 -->
      </td>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif" >&nbsp;</td>
      </tr>
    </table>
    </td>
  </tr>
  <tr>
    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
        <td align='center'><input name="" type="submit" class="btn2" value="保 存" />
        <input name="" type="reset" class="btn2 ml10" value="取消" /></td>
	   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
  </s:form>
 </div>
<%}catch(Exception e){} %>

</body>

<script type="text/javascript">
var bgcolor = "color:#aaa;";
$(document).ready(function() {
		
	setContDisable();
	$("#content input").click(function() {
		
		if($(this).attr("name")=="checkbox")
		{
			if($(this).attr("checked")){
				$(this).parent().parent().prev().prev().prev().children().removeAttr('disabled');
				$(this).parent().parent().prev().prev().prev().prev().children().removeAttr('disabled');
			}else{
				$(this).parent().parent().prev().prev().prev().children().attr('disabled','disabled');
				$(this).parent().parent().prev().prev().prev().prev().children().attr('disabled','disabled');
			}
		}
	
	});
	//如果已经CHECK的,如果已经开了盘的项目灰化START-----调试时注释
	<%for (Iterator i = map.keySet().iterator(); i.hasNext();) {
		Object key = i.next();%>
		if($("#gdPeriodNum").val()!="" && $("#gdPeriodNum").val()!=null && $("#hasBet").val()=="true"){
			<%if("1".equals(map.get(key).getState()) && key.toString().indexOf("GDKLSF")!=-1 && !key.toString().equals("GDKLSF_BUTTON")){%>
			   <%-- $("input[name='<%=key.toString()%>']").attr('disabled','disabled'); --%> /* 控制額度 */
			   $("input[name='<%=key.toString()%>']").parent().prev().children().attr('disabled','disabled'); /* 列表框 */
			   $("input[name='<%=key.toString()%>']").parent().next().next().next().children().children().attr('disabled','disabled'); /* checkBox */
		    <%}%>
		    $("#openPeriod").show();
		}
		if($("#ncPeriodNum").val()!="" && $("#ncPeriodNum").val()!=null && $("#hasBet").val()=="true"){
			<%if("1".equals(map.get(key).getState()) && key.toString().indexOf("NC")!=-1 && !key.toString().equals("NC_BUTTON")){%>
			   $("input[name='<%=key.toString()%>']").parent().prev().children().attr('disabled','disabled'); /* 列表框 */
			   $("input[name='<%=key.toString()%>']").parent().next().next().next().children().children().attr('disabled','disabled'); /* checkBox */
		    <%}%>
		    $("#openPeriod").show();
		}
		if($("#cqPeriodNum").val()!="" && $("#cqPeriodNum").val()!=null && $("#hasBet").val()=="true"){
				<%if("1".equals(map.get(key).getState()) && key.toString().indexOf("CQSSC")!=-1 && !key.toString().equals("CQSSC_BUTTON")){%>
					   <%-- $("input[name='<%=key.toString()%>']").attr('disabled','disabled'); --%> /* 控制額度 */
					   $("input[name='<%=key.toString()%>']").parent().prev().children().attr('disabled','disabled'); /* 列表框 */
					   $("input[name='<%=key.toString()%>']").parent().next().next().next().children().children().attr('disabled','disabled'); /* checkBox */
					  
				 <%	}%>
				 $("#openPeriod").show();
		}
		if($("#bjPeriodNum").val()!="" && $("#bjPeriodNum").val()!=null && $("#hasBet").val()=="true"){
				 <%if("1".equals(map.get(key).getState()) && key.toString().indexOf("BJ")!=-1 && !key.toString().equals("BJ_BUTTON")){%>
					   <%-- $("input[name='<%=key.toString()%>']").attr('disabled','disabled'); --%> /* 控制額度 */
					   $("input[name='<%=key.toString()%>']").parent().prev().children().attr('disabled','disabled'); /* 列表框 */
					   $("input[name='<%=key.toString()%>']").parent().next().next().next().children().children().attr('disabled','disabled'); /* checkBox */
				<%  }%>
				$("#openPeriod").show();
		}
		if($("#k3PeriodNum").val()!="" && $("#k3PeriodNum").val()!=null && $("#hasBet").val()=="true"){
				 <%if("1".equals(map.get(key).getState()) && key.toString().indexOf("K3")!=-1 && !key.toString().equals("K3_BUTTON")){%>
					   <%-- $("input[name='<%=key.toString()%>']").attr('disabled','disabled'); --%> /* 控制額度 */
					   $("input[name='<%=key.toString()%>']").parent().prev().children().attr('disabled','disabled'); /* 列表框 */
					   $("input[name='<%=key.toString()%>']").parent().next().next().next().children().children().attr('disabled','disabled'); /* checkBox */
				<%  }%>
				$("#openPeriod").show();
		}
	<%}
	%>
	//如果已经CHECK的,如果已经开了盘的项目灰化END
});

function setContDisable()
{
	$("input[name=checkbox]").each(function(){
			if($(this).attr("checked")!='checked'){
				//alert("checked : "+$(this).parent().parent().prev().prev().prev().html());
				$(this).parent().parent().prev().prev().prev().children().attr('disabled','disabled');//控制額度
				$(this).parent().parent().prev().prev().prev().prev().children().attr('disabled','disabled');//下注額列表框
	 		}
	  });
}
</script>
</html>