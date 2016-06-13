<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/drag.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript">

$(document).ready(function() {
	/* $("#betTable tr td input").keyup(function(){     
	    var tmptxt=$(this).val();     
	    $(this).val(tmptxt.replace(/\D|^0/g,''));     
	}).bind("paste",function(){     
	    var tmptxt=$(this).val();     
	    $(this).val(tmptxt.replace(/\D|^0/g,''));     
	}).blur(function(){     
	    var tmptxt=$(this).val();     
	    $(this).val(tmptxt.replace(/\D|^0/g,''));     
	}).css("ime-mode", "disabled");     */
});
	
//获取元素的坐标
var getElementOffset = function _getElementOffset($this) {
    var offset = $this.position();
    var left = offset.left-20;
    var top = offset.top + $this.height()-98;  //把text元素本身的高度也算上
    return { left: left, top: top }
}

//设置元素的坐标
var setElementOffset = function _setElementOffset($this, offset) {
    $this.css({ "position": "absolute", "left": offset.left, "top": offset.top });
    return $this;
}
   
function pop(typeCode) {
	var strUrl = "${pageContext.request.contextPath}/user/ajaxQueryCommission.action";
	var queryUrl = encodeURI(encodeURI(strUrl));
	$.ajax({
		type : "POST",
		url : queryUrl,
		data : {"id":$("#id").val(),"typeCode":typeCode},
		dataType : "json",
		success : getCallback
	});
}

function getCallback(json) {
	$("#setShowTitle").html($('#' +json.typeCode ).parent().children().html());
   	$("#setShowPlate").html($("#plate").val());
    $("#commission").val(json.commission);
   	$("#bettingQuotas").val(json.bettingQuotas);
   	$("#itemQuotas").val(json.itemQuotas);
   	$("#typeCode").val(json.typeCode);
   	var offset = getElementOffset($('#' +json.typeCode ));
    var $div = setElementOffset($("#setShow"), offset);
    $div.show();
}

function setShowSubmit(){
	var strUrl = "${pageContext.request.contextPath}/user/updateOutReplenishCommission.action";
	var queryUrl = encodeURI(encodeURI(strUrl));
	$.ajax({
		type : "POST",
		url : queryUrl,
		data : {"id":$("#id").val(),"typeCode":$("#typeCode").val(),"commission":$("#commission").val(),
			"bettingQuotas":$("#bettingQuotas").val(),"itemQuotas":$("#itemQuotas").val()},
		dataType : "json",
		success : getCallbackSubmit
	});
}

function getCallbackSubmit(json) {
    $('#' +json.typeCode ).siblings(".a").text(json.commission);
    $('#' +json.typeCode ).siblings(".b").text(json.bettingQuotas);
    $('#' +json.typeCode ).siblings(".c").text(json.itemQuotas);
    $("#setShow").hide();
}

function closeSetShow(){
	$("#setShow").hide();
}
</script>
</head>
<body>
<div id="content">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <input type="hidden" id="plate" value='<s:property value="#request.plate" escape="false"/>'/>
  <input type="hidden" id="id" value='<s:property value="#request.id" escape="false"/>'/>
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
                <td width="99%" align="left" class="F_bold"><strong>&nbsp;退水設定 -&gt; 出貨會員(<s:property value="#request.account"/>)</strong></td>
              </tr>
            </table>
            </td>
            <td align="right" width="70%">&nbsp;名稱：<s:property value="#request.chsName"/></td>
            </tr></table></td>
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
		        <td align="center">
        <!--广东开始-->
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mt4">
        <colgroup>
          <col width="50%" />
          <col width="50%" />
        </colgroup>
  <tr>
    <th colspan="2" class="tabtop"><strong>廣東快樂十分鐘</strong></th>
    </tr>
  <tr>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
    <colgroup>
          <col width="15%" />
          <col width="15%" />
          <col width="15%" />
          <col width="15%" />
          <col width="20%" />
          <col width="20%" />
        </colgroup>
      <tr>
        <th width="15%">交易類型</th>
        <th width="15%"><s:property value="#request.plate"/>盤</th>
        <th width="25%">單注限額</th>
        <th width="25%">單期限額</th>
        <th width="20%">...</th>
      </tr>
      <tr>
        <td class="even2">第一球 </td>
        <td class="a"><s:property value="#request.commissions.GD_ONE_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_ONE_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_ONE_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_ONE_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_ONE_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">第二球</td>
        <td class="a"><s:property value="#request.commissions.GD_TWO_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_TWO_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_TWO_BALL.itemQuotas"/></td>
  		<td class="rOp" id="GD_TWO_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_TWO_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第三球</td>
        <td class="a"><s:property value="#request.commissions.GD_THREE_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_THREE_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_THREE_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_THREE_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_THREE_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第四球</td>
        <td class="a"><s:property value="#request.commissions.GD_FOUR_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_FOUR_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_FOUR_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_FOUR_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_FOUR_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第五球</td>
        <td class="a"><s:property value="#request.commissions.GD_FIVE_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_FIVE_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_FIVE_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_FIVE_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_FIVE_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第六球</td>
        <td class="a"><s:property value="#request.commissions.GD_SIX_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_SIX_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_SIX_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_SIX_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_SIX_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第七球</td>
        <td class="a"><s:property value="#request.commissions.GD_SEVEN_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_SEVEN_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_SEVEN_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_SEVEN_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_SEVEN_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第八球</td>
        <td class="a"><s:property value="#request.commissions.GD_EIGHT_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_EIGHT_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_EIGHT_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_EIGHT_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_EIGHT_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">1-8大小</td>
        <td class="a"><s:property value="#request.commissions.GD_OEDX_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_OEDX_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_OEDX_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_OEDX_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_OEDX_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">1-8單雙</td>
        <td class="a"><s:property value="#request.commissions.GD_OEDS_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_OEDS_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_OEDS_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_OEDS_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_OEDS_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">1-8尾數大小</td>
        <td class="a"><s:property value="#request.commissions.GD_OEWSDX_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_OEWSDX_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_OEWSDX_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_OEWSDX_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_OEWSDX_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">1-8合數單雙</td>
        <td class="a"><s:property value="#request.commissions.GD_HSDS_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_HSDS_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_HSDS_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_HSDS_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_HSDS_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">1-8方位</td>
        <td class="a"><s:property value="#request.commissions.GD_FW_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_FW_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_FW_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_FW_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_FW_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">1-8中發白</td>
        <td class="a"><s:property value="#request.commissions.GD_ZF_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_ZF_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_ZF_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_ZF_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_ZF_BALL')">設定</a>
        </td>
      </tr>
    </table></td>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
      <colgroup>
        <col width="15%" />
        <col width="15%" />
        <col width="15%" />
        <col width="15%" />
        <col width="20%" />
        <col width="20%" />
        </colgroup>
      <tr>
        <th width="15%">交易類型</th>
        <th width="15%"><s:property value="#request.plate"/>盤</th>
        <th width="25%">單注限額</th>
        <th width="25%">單期限額</th>
        <th width="20%">...</th>
      </tr>
      <tr>
        <td class="even2">總和大小</td>
        <td class="a"><s:property value="#request.commissions.GD_ZHDX_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_ZHDX_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_ZHDX_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_ZHDX_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_ZHDX_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">總和單雙</td>
        <td class="a"><s:property value="#request.commissions.GD_ZHDS_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_ZHDS_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_ZHDS_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_ZHDS_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_ZHDS_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">總和尾數大小</td>
        <td class="a"><s:property value="#request.commissions.GD_ZHWSDX_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_ZHWSDX_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_ZHWSDX_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_ZHWSDX_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_ZHWSDX_BALL')">設定</a>
        </td>
      </tr>
    
      <tr>
        <td class="even2">龍虎</td>
        <td class="a"><s:property value="#request.commissions.GD_LH_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_LH_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_LH_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_LH_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_LH_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">任選二</td>
        <td class="a"><s:property value="#request.commissions.GD_RXH_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_RXH_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_RXH_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_RXH_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_RXH_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">選二連直</td>
        <td class="a"><s:property value="#request.commissions.GD_RELZ_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_RELZ_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_RELZ_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_RELZ_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_RELZ_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">選二連組</td>
        <td class="a"><s:property value="#request.commissions.GD_RTLZ_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_RTLZ_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_RTLZ_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_RTLZ_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_RTLZ_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">任選三</td>
        <td class="a"><s:property value="#request.commissions.GD_RXS_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_RXS_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_RXS_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_RXS_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_RXS_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">選三前直</td>
        <td class="a"><s:property value="#request.commissions.GD_XSQZ_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_XSQZ_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_XSQZ_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_XSQZ_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_XSQZ_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">選三前組</td>
        <td class="a"><s:property value="#request.commissions.GD_XTQZ_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_XTQZ_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_XTQZ_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_XTQZ_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_XTQZ_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">任選四</td>
        <td class="a"><s:property value="#request.commissions.GD_RXF_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_RXF_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_RXF_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_RXF_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_RXF_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">任選五</td>
        <td class="a"><s:property value="#request.commissions.GD_RXW_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.GD_RXW_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.GD_RXW_BALL.itemQuotas"/></td>
        <td class="rOp" id="GD_RXW_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('GD_RXW_BALL')">設定</a>
        </td>
      </tr>
    </table>
    </td>
  </tr>
</table>
          <!-- 广东结束 -->
          <!--重庆开始-->
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <colgroup>
          <col width="50%" />
          <col width="50%" />
        </colgroup>
  <tr>
    <th colspan="2" class="tabtop"><strong>重慶時時彩</strong></th>
    </tr>
  <tr>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
    <colgroup>
          <col width="15%" />
          <col width="15%" />
          <col width="15%" />
          <col width="15%" />
          <col width="20%" />
          <col width="20%" />
        </colgroup>
      <tr>
        <th width="15%">交易類型</th>
        <th width="15%"><s:property value="#request.plate"/>盤</th>
        <th width="25%">單注限額</th>
        <th width="25%">單期限額</th>
        <th width="20%">...</th>
      </tr>
      <tr>
        <td class="even2">第一球</td>
        <td class="a"><s:property value="#request.commissions.CQ_ONE_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.CQ_ONE_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.CQ_ONE_BALL.itemQuotas"/></td>
        <td class="rOp" id="CQ_ONE_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('CQ_ONE_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">第二球</td>
        <td class="a"><s:property value="#request.commissions.CQ_TWO_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.CQ_TWO_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.CQ_TWO_BALL.itemQuotas"/></td>
        <td class="rOp" id="CQ_TWO_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('CQ_TWO_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第三球</td>
        <td class="a"><s:property value="#request.commissions.CQ_THREE_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.CQ_THREE_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.CQ_THREE_BALL.itemQuotas"/></td>
        <td class="rOp" id="CQ_THREE_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('CQ_THREE_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第四球</td>
        <td class="a"><s:property value="#request.commissions.CQ_FOUR_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.CQ_FOUR_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.CQ_FOUR_BALL.itemQuotas"/></td>
        <td class="rOp" id="CQ_FOUR_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('CQ_FOUR_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第五球</td>
        <td class="a"><s:property value="#request.commissions.CQ_FIVE_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.CQ_FIVE_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.CQ_FIVE_BALL.itemQuotas"/></td>
        <td class="rOp" id="CQ_FIVE_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('CQ_FIVE_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">1-5大小</td>
        <td class="a"><s:property value="#request.commissions.CQ_OFDX_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.CQ_OFDX_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.CQ_OFDX_BALL.itemQuotas"/></td>
        <td class="rOp" id="CQ_OFDX_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('CQ_OFDX_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">1-5單雙</td>
   		<td class="a"><s:property value="#request.commissions.CQ_OFDS_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.CQ_OFDS_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.CQ_OFDS_BALL.itemQuotas"/></td>
        <td class="rOp" id="CQ_OFDS_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('CQ_OFDS_BALL')">設定</a>
        </td>
        </tr>
    </table></td>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
      <colgroup>
        <col width="15%" />
        <col width="15%" />
        <col width="15%" />
        <col width="15%" />
        <col width="20%" />
        <col width="20%" />
        </colgroup>
      <tr>
        <th width="15%">交易類型</th>
        <th width="15%"><s:property value="#request.plate"/>盤</th>
        <th width="25%">單注限額</th>
        <th width="25%">單期限額</th>
        <th width="20%">...</th>
      </tr>
      <tr>
        <td class="even2">總和大小</td>
     	<td class="a"><s:property value="#request.commissions.CQ_ZHDX_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.CQ_ZHDX_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.CQ_ZHDX_BALL.itemQuotas"/></td>
        <td class="rOp" id="CQ_ZHDX_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('CQ_ZHDX_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">總和單雙</td>
 	    <td class="a"><s:property value="#request.commissions.CQ_ZHDS_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.CQ_ZHDS_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.CQ_ZHDS_BALL.itemQuotas"/></td>
        <td class="rOp" id="CQ_ZHDS_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('CQ_ZHDS_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">龍虎和</td>
        <td class="a"><s:property value="#request.commissions.CQ_LH_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.CQ_LH_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.CQ_LH_BALL.itemQuotas"/></td>
        <td class="rOp" id="CQ_LH_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('CQ_LH_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">前三</td>
  		<td class="a"><s:property value="#request.commissions.CQ_QS_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.CQ_QS_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.CQ_QS_BALL.itemQuotas"/></td>
        <td class="rOp" id="CQ_QS_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('CQ_QS_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">中三</td>
   	    <td class="a"><s:property value="#request.commissions.CQ_ZS_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.CQ_ZS_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.CQ_ZS_BALL.itemQuotas"/></td>
        <td class="rOp" id="CQ_ZS_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('CQ_ZS_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">后三</td>
        <td class="a"><s:property value="#request.commissions.CQ_HS_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.CQ_HS_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.CQ_HS_BALL.itemQuotas"/></td>
        <td class="rOp" id="CQ_HS_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('CQ_HS_BALL')">設定</a>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
          <!--重庆结束-->
          <!--北京开始-->
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <colgroup>
          <col width="50%" />
          <col width="50%" />
        </colgroup>
  <tr>
    <th colspan="2" class="tabtop"><strong>北京賽車(PK10)</strong></th>
    </tr>
  <tr>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
    <colgroup>
          <col width="15%" />
          <col width="15%" />
          <col width="15%" />
          <col width="15%" />
          <col width="20%" />
          <col width="20%" />
        </colgroup>
      <tr>
        <th width="15%">交易類型</th>
        <th width="15%"><s:property value="#request.plate"/>盤</th>
        <th width="25%">單注限額</th>
        <th width="25%">單期限額</th>
        <th width="20%">...</th>
      </tr>
      <tr>
        <td class="even2">冠軍</td>
        <td class="a"><s:property value="#request.commissions.BJ_BALL_FIRST.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_BALL_FIRST.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_BALL_FIRST.itemQuotas"/></td>
        <td class="rOp" id="BJ_BALL_FIRST">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_BALL_FIRST')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">亚軍</td>
        <td class="a"><s:property value="#request.commissions.BJ_BALL_SECOND.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_BALL_SECOND.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_BALL_SECOND.itemQuotas"/></td>
        <td class="rOp" id="BJ_BALL_SECOND">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_BALL_SECOND')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第三名</td>
        <td class="a"><s:property value="#request.commissions.BJ_BALL_THIRD.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_BALL_THIRD.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_BALL_THIRD.itemQuotas"/></td>
        <td class="rOp" id="BJ_BALL_THIRD">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_BALL_THIRD')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第四名</td>
        <td class="a"><s:property value="#request.commissions.BJ_BALL_FORTH.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_BALL_FORTH.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_BALL_FORTH.itemQuotas"/></td>
        <td class="rOp" id="BJ_BALL_FORTH">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_BALL_FORTH')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第五</td>
        <td class="a"><s:property value="#request.commissions.BJ_BALL_FIFTH.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_BALL_FIFTH.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_BALL_FIFTH.itemQuotas"/></td>
        <td class="rOp" id="BJ_BALL_FIFTH">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_BALL_FIFTH')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第六名</td>
        <td class="a"><s:property value="#request.commissions.BJ_BALL_SIXTH.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_BALL_SIXTH.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_BALL_SIXTH.itemQuotas"/></td>
        <td class="rOp" id="BJ_BALL_SIXTH">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_BALL_SIXTH')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第七名</td>
        <td class="a"><s:property value="#request.commissions.BJ_BALL_SEVENTH.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_BALL_SEVENTH.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_BALL_SEVENTH.itemQuotas"/></td>
        <td class="rOp" id="BJ_BALL_SEVENTH">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_BALL_SEVENTH')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第八名</td>
        <td class="a"><s:property value="#request.commissions.BJ_BALL_EIGHTH.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_BALL_EIGHTH.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_BALL_EIGHTH.itemQuotas"/></td>
        <td class="rOp" id="BJ_BALL_EIGHTH">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_BALL_EIGHTH')">設定</a>
        </td>
        </tr>
    </table></td>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
      <colgroup>
        <col width="15%" />
        <col width="15%" />
        <col width="15%" />
        <col width="15%" />
        <col width="20%" />
        <col width="20%" />
        </colgroup>
      <tr>
        <th width="15%">交易類型</th>
        <th width="15%"><s:property value="#request.plate"/>盤</th>
        <th width="25%">單注限額</th>
        <th width="25%">單期限額</th>
        <th width="20%">...</th>
      </tr>
      <tr>
        <td class="even2">第九名</td>
        <td class="a"><s:property value="#request.commissions.BJ_BALL_NINTH.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_BALL_NINTH.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_BALL_NINTH.itemQuotas"/></td>
        <td class="rOp" id="BJ_BALL_NINTH">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_BALL_NINTH')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">第十名</td>
        <td class="a"><s:property value="#request.commissions.BJ_BALL_TENTH.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_BALL_TENTH.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_BALL_TENTH.itemQuotas"/></td>
        <td class="rOp" id="BJ_BALL_TENTH">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_BALL_TENTH')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">1-10大小</td>
        <td class="a"><s:property value="#request.commissions.BJ_1_10_DX.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_1_10_DX.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_1_10_DX.itemQuotas"/></td>
        <td class="rOp" id="BJ_1_10_DX">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_1_10_DX')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">1-10單雙</td>
        <td class="a"><s:property value="#request.commissions.BJ_1_10_DS.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_1_10_DS.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_1_10_DS.itemQuotas"/></td>
        <td class="rOp" id="BJ_1_10_DS">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_1_10_DS')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">1-5龍虎</td>
        <td class="a"><s:property value="#request.commissions.BJ_1_5_LH.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_1_5_LH.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_1_5_LH.itemQuotas"/></td>
        <td class="rOp" id="BJ_1_5_LH">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_1_5_LH')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">冠亞軍和大小</td>
        <td class="a"><s:property value="#request.commissions.BJ_DOUBLSIDE_DX.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_DOUBLSIDE_DX.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_DOUBLSIDE_DX.itemQuotas"/></td>
        <td class="rOp" id="BJ_DOUBLSIDE_DX">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_DOUBLSIDE_DX')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">冠亞軍和單雙</td>
        <td class="a"><s:property value="#request.commissions.BJ_DOUBLSIDE_DS.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_DOUBLSIDE_DS.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_DOUBLSIDE_DS.itemQuotas"/></td>
        <td class="rOp" id="BJ_DOUBLSIDE_DS">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_DOUBLSIDE_DS')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">冠亞軍和</td>
        <td class="a"><s:property value="#request.commissions.BJ_GROUP.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.BJ_GROUP.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.BJ_GROUP.itemQuotas"/></td>
        <td class="rOp" id="BJ_GROUP">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('BJ_GROUP')">設定</a>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
          <!--北京结束-->         
<!--快3开始-->
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <colgroup>
          <col width="50%" />
          <col width="50%" />
        </colgroup>
  <tr>
    <th colspan="2" class="tabtop"><strong>江苏骰寶(K3)</strong></th>
    </tr>
  <tr>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
    <colgroup>
          <col width="15%" />
          <col width="15%" />
          <col width="15%" />
          <col width="15%" />
          <col width="20%" />
          <col width="20%" />
        </colgroup>
      <tr>
        <th width="15%">交易類型</th>
        <th width="15%"><s:property value="#request.plate"/>盤</th>
        <th width="25%">單注限額</th>
        <th width="25%">單期限額</th>
        <th width="20%">...</th>
      </tr>
      <tr>
        <td class="even2">大小</td>
        <td class="a"><s:property value="#request.commissions.K3_DX.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.K3_DX.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.K3_DX.itemQuotas"/></td>
        <td class="rOp" id="K3_DX">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('K3_DX')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">三軍</td>
        <td class="a"><s:property value="#request.commissions.K3_SJ.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.K3_SJ.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.K3_SJ.itemQuotas"/></td>
        <td class="rOp" id="K3_SJ">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('K3_SJ')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">圍骰</td>
        <td class="a"><s:property value="#request.commissions.K3_WS.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.K3_WS.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.K3_WS.itemQuotas"/></td>
        <td class="rOp" id="K3_WS">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('K3_WS')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">全骰</td>
        <td class="a"><s:property value="#request.commissions.K3_QS.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.K3_QS.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.K3_QS.itemQuotas"/></td>
        <td class="rOp" id="K3_QS">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('K3_QS')">設定</a>
        </td>
        </tr>
    </table></td>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
      <colgroup>
        <col width="15%" />
        <col width="15%" />
        <col width="15%" />
        <col width="15%" />
        <col width="20%" />
        <col width="20%" />
        </colgroup>
      <tr>
        <th width="15%">交易類型</th>
        <th width="15%"><s:property value="#request.plate"/>盤</th>
        <th width="25%">單注限額</th>
        <th width="25%">單期限額</th>
        <th width="20%">...</th>
      </tr>
      <tr>
        <td class="even2">點數</td>
        <td class="a"><s:property value="#request.commissions.K3_DS.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.K3_DS.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.K3_DS.itemQuotas"/></td>
        <td class="rOp" id="K3_DS">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('K3_DS')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">長牌</td>
        <td class="a"><s:property value="#request.commissions.K3_CP.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.K3_CP.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.K3_CP.itemQuotas"/></td>
        <td class="rOp" id="K3_CP">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('K3_CP')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">短牌</td>
        <td class="a"><s:property value="#request.commissions.K3_DP.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.K3_DP.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.K3_DP.itemQuotas"/></td>
        <td class="rOp" id="K3_DP">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('K3_DP')">設定</a>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
          <!--快3结束-->  
        <!--农场开始-->
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mt4">
        <colgroup>
          <col width="50%" />
          <col width="50%" />
        </colgroup>
  <tr>
    <th colspan="2" class="tabtop"><strong>幸运农场</strong></th>
    </tr>
  <tr>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
    <colgroup>
          <col width="15%" />
          <col width="15%" />
          <col width="15%" />
          <col width="15%" />
          <col width="20%" />
          <col width="20%" />
        </colgroup>
      <tr>
        <th width="15%">交易類型</th>
        <th width="15%"><s:property value="#request.plate"/>盤</th>
        <th width="25%">單注限額</th>
        <th width="25%">單期限額</th>
        <th width="20%">...</th>
      </tr>
      <tr>
        <td class="even2">第一球 </td>
        <td class="a"><s:property value="#request.commissions.NC_ONE_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_ONE_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_ONE_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_ONE_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_ONE_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">第二球</td>
        <td class="a"><s:property value="#request.commissions.NC_TWO_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_TWO_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_TWO_BALL.itemQuotas"/></td>
  		<td class="rOp" id="NC_TWO_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_TWO_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第三球</td>
        <td class="a"><s:property value="#request.commissions.NC_THREE_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_THREE_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_THREE_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_THREE_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_THREE_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第四球</td>
        <td class="a"><s:property value="#request.commissions.NC_FOUR_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_FOUR_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_FOUR_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_FOUR_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_FOUR_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第五球</td>
        <td class="a"><s:property value="#request.commissions.NC_FIVE_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_FIVE_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_FIVE_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_FIVE_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_FIVE_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第六球</td>
        <td class="a"><s:property value="#request.commissions.NC_SIX_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_SIX_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_SIX_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_SIX_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_SIX_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第七球</td>
        <td class="a"><s:property value="#request.commissions.NC_SEVEN_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_SEVEN_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_SEVEN_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_SEVEN_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_SEVEN_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">第八球</td>
        <td class="a"><s:property value="#request.commissions.NC_EIGHT_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_EIGHT_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_EIGHT_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_EIGHT_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_EIGHT_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">1-8大小</td>
        <td class="a"><s:property value="#request.commissions.NC_OEDX_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_OEDX_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_OEDX_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_OEDX_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_OEDX_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">1-8單雙</td>
        <td class="a"><s:property value="#request.commissions.NC_OEDS_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_OEDS_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_OEDS_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_OEDS_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_OEDS_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">1-8尾數大小</td>
        <td class="a"><s:property value="#request.commissions.NC_OEWSDX_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_OEWSDX_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_OEWSDX_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_OEWSDX_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_OEWSDX_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">1-8合數單雙</td>
        <td class="a"><s:property value="#request.commissions.NC_HSDS_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_HSDS_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_HSDS_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_HSDS_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_HSDS_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">1-8方位</td>
        <td class="a"><s:property value="#request.commissions.NC_FW_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_FW_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_FW_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_FW_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_FW_BALL')">設定</a>
        </td>
        </tr>
      <tr>
        <td class="even2">1-8中發白</td>
        <td class="a"><s:property value="#request.commissions.NC_ZF_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_ZF_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_ZF_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_ZF_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_ZF_BALL')">設定</a>
        </td>
      </tr>
    </table></td>
    <td width="50%" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="king">
      <colgroup>
        <col width="15%" />
        <col width="15%" />
        <col width="15%" />
        <col width="15%" />
        <col width="20%" />
        <col width="20%" />
        </colgroup>
      <tr>
        <th width="15%">交易類型</th>
        <th width="15%"><s:property value="#request.plate"/>盤</th>
        <th width="25%">單注限額</th>
        <th width="25%">單期限額</th>
        <th width="20%">...</th>
      </tr>
      <tr>
        <td class="even2">總和大小</td>
        <td class="a"><s:property value="#request.commissions.NC_ZHDX_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_ZHDX_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_ZHDX_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_ZHDX_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_ZHDX_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">總和單雙</td>
        <td class="a"><s:property value="#request.commissions.NC_ZHDS_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_ZHDS_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_ZHDS_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_ZHDS_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_ZHDS_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">總和尾數大小</td>
        <td class="a"><s:property value="#request.commissions.NC_ZHWSDX_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_ZHWSDX_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_ZHWSDX_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_ZHWSDX_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_ZHWSDX_BALL')">設定</a>
        </td>
      </tr>
    
      <tr>
        <td class="even2">龍虎</td>
        <td class="a"><s:property value="#request.commissions.NC_LH_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_LH_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_LH_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_LH_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_LH_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">任選二</td>
        <td class="a"><s:property value="#request.commissions.NC_RXH_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_RXH_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_RXH_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_RXH_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_RXH_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">選二連直</td>
        <td class="a"><s:property value="#request.commissions.NC_RELZ_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_RELZ_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_RELZ_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_RELZ_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_RELZ_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">選二連組</td>
        <td class="a"><s:property value="#request.commissions.NC_RTLZ_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_RTLZ_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_RTLZ_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_RTLZ_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_RTLZ_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">任選三</td>
        <td class="a"><s:property value="#request.commissions.NC_RXS_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_RXS_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_RXS_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_RXS_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_RXS_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">選三前直</td>
        <td class="a"><s:property value="#request.commissions.NC_XSQZ_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_XSQZ_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_XSQZ_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_XSQZ_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_XSQZ_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">選三前組</td>
        <td class="a"><s:property value="#request.commissions.NC_XTQZ_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_XTQZ_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_XTQZ_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_XTQZ_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_XTQZ_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">任選四</td>
        <td class="a"><s:property value="#request.commissions.NC_RXF_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_RXF_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_RXF_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_RXF_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_RXF_BALL')">設定</a>
        </td>
      </tr>
      <tr>
        <td class="even2">任選五</td>
        <td class="a"><s:property value="#request.commissions.NC_RXW_BALL.displayCommissionA"/></td>
        <td class="b"><s:property value="#request.commissions.NC_RXW_BALL.bettingQuotas"/></td>
        <td class="c"><s:property value="#request.commissions.NC_RXW_BALL.itemQuotas"/></td>
        <td class="rOp" id="NC_RXW_BALL">
			<img src="${pageContext.request.contextPath}/images/admin/tuishui.gif"/>
			  <a class="btn_4" href="javascript:void(0)" target="_self" onclick="pop('NC_RXW_BALL')">設定</a>
        </td>
      </tr>
    </table>
    </td>
  </tr>
</table>
<!-- 农场结束 -->       
          </td>
        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
      </tr>
    </table>
    
    
    
    </td>
  </tr>
  <tr>
    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
        <td align='center'>&nbsp;</td>
	   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
</div>

<!-- 設定开始-->
	<div id="setShow"
		style="background-color: #EFF1FF; display: none;">
			<input id="typeCode" type="hidden" value=""/>
			<table border="0" cellspacing="0" cellpadding="0" class="king" id="betTable"
				style="position: absolute; left: 23px; top: 100px; width: 200px; background: #fff; z-index: 9999;">
				<tr>
					<th colspan="2"><strong id="setShowTitle">&nbsp;</strong></th>
				</tr>
				<tr>
					<td style="height:30px"><strong id="setShowPlate">&nbsp;</strong>盤</td>
					<td class="t_left">
					    <s:select id="commission" list="#request.mapC" listKey="key" listValue="value" headerKey="0"/>
					</td>
				</tr>
				<tr>
					<td style="height:30px">單注限額</td>
					<td class="t_left"><input id="bettingQuotas" class="b_g" name="outReplenishStaff.bettingQuotas"
						style="width: 76px;" /></td>
				</tr>
				<tr>
					<td style="height:30px">單期限額</td>
					<td class="t_left"><input id="itemQuotas" name="bettingQuotas.itemQuotas" class="b_g"
						style="width: 76px;"/></td>
				</tr>
				<tr>
					<td colspan="2" bgcolor="#f1f1f1" style="height:30px"><span>
					   <input id="rBtnSubmit" type="button" class="sl btn3" value=" 確 定 " onclick="setShowSubmit()"/> 
					   <input id="retbtn" type="button" class="sl btn3" value=" 取 消 " onclick="closeSetShow()"/></span></td>
				</tr>
			</table>
	</div>
	<!-- 設定结束-->


</body>
</html>
