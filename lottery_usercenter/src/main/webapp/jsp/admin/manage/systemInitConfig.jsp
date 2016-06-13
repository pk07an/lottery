<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.odds.entity.ShopsPlayOdds" %>
<%@ page import="com.npc.lottery.common.Constant "%>
<%@ taglib prefix="s" uri="/struts-tags"%>
 
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript"
		src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript"
		src="${pageContext.request.contextPath}/js/public.js"></script>
<script type="text/javascript">
function changeOddsType(obj){
// 	alert(obj.value);
	  window.location="${pageContext.request.contextPath}/admin/systemConfig.action?type=privateAdmin&autoOddsType="+obj.value+"";
}

$(document).ready(function() {
	
	//觸發reset 防止操作后 在刷新 頁面無響應
	$('#resetButton').click();
	$('#resetButtonTwo').click();
	
	
	 $("#tablesystemInit input[type=text]").keyup(function(){     
	        var tmptxt=$(this).val();     
	       if($(this).val() != ""){
	        $(this).val(tmptxt.replace(/[^\d.]/g,""));
	        $(this).val($(this).val().replace(/^\./g,""));  
	       $(this).val($(this).val().replace(/\.{2,}/g,"."));  
	       $(this).val($(this).val().replace(".","$#$").replace(/\./g,"").replace("$#$",".")); 
	       } 
	 });
});

</script>
<%
Map<String,String> autoOddsMap=(Map<String,String>)request.getAttribute("AutoOddsMap");  
 
//    out.write(autoOddsMap.get("GDKLSF_DOUBLESIDE/STATE"));
%>
<s:form id="form_doubleside" action="updateAutoOddsDoubleSide" method="post">
<div class="content">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
				<!--控制表格头部开始-->
				<tr>
					<td height="30"
						background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><div
							id="Tab_top">
							<table border="0" cellpadding="0" cellspacing="0" class="ssgd_l"
								id="Tab_top_left">
								<tr>
									<td width="14" height="30"><img
										src="${pageContext.request.contextPath}/images/admin/tab_03.gif"
										width="12" height="30" /></td>
									<td width="24" align="left"><img
										src="${pageContext.request.contextPath}/images/admin/tb.gif"
										width="16" height="16" /></td>
									<td width="81"><strong>初始設定</strong></td>
								</tr>
							</table>
							<table id="Tab_top_right" class="" border="0"
								cellspacing="0" cellpadding="0">
								<tr>
									<td width="56">
									&nbsp;
									</td>
									<td width="16" align="right"><img
										src="${pageContext.request.contextPath}/images/admin/tab_07.gif"
										width="16" height="30" /></td>
								</tr>
							</table>
						</div></td>
				</tr>
				<tr>

					<td><table width="100%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td width="8"
									background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
								<td align="center">
  <table width="100%" cellspacing="0" cellpadding="0" border="0" class="king autoHeight" id="tablesystemInit">
  <colgroup>
    <col width="16%">
    <col width="84%">
    </colgroup>
  <tbody>
  <tr>
    <th>&nbsp;</th>
    <th><strong>系統初始化設定</strong></th>
    </tr>
  <tr>
    <td class="t_right even">信用額度恢复模式</td>
    <td class="t_left"><input type="radio" class="" value="1" name="SYS_CREDITMODEL$CREDITMODEL" <%if("1".equals((autoOddsMap.get("SYS_CREDITMODEL/CREDITMODEL")))) out.print("checked"); %> readonly="readonly"/>
      投注扣除信用余額  [不論輸贏]（凌晨2點半恢復）
        <input type="radio" class="ml10" value="0" name="SYS_CREDITMODEL$CREDITMODEL" <%if("0".equals(autoOddsMap.get("SYS_CREDITMODEL/CREDITMODEL"))) out.print("checked"); %> readonly="readonly"/>
      按輸贏实时增減  "可以額度" （凌晨2點半恢復）</td>
    </tr>
  <tr>
    <td class="t_right even">"賠率"小數后保留位數</td>
    <td class="t_left"><span class="in">
      <input type="text" class="b_g"  maxlength="6" size="4"  value="<%=autoOddsMap.get("SYS_DECIMALDITIG/DECIMALDITIG")==null?"3":autoOddsMap.get("SYS_DECIMALDITIG/DECIMALDITIG")%>" name="SYS_DECIMALDITIG$DECIMALDITIG" readonly="readonly"/>
    </span>[如：1.922即保留3位]建議不超3位</td>
    </tr>
  <tr>
    <td class="t_right even">連碼“复式”允许最多號碼数</td>
    <td class="t_left"><span class="in">
      <input type="text" class="b_g"  maxlength="6" size="4" value="<%=autoOddsMap.get("SYS_LMMAXNUM/LMMAXNUM")==null?"8":autoOddsMap.get("SYS_LMMAXNUM/LMMAXNUM")%>"  name="SYS_LMMAXNUM$LMMAXNUM" readonly="readonly"/>
    </span>建議不超过10个</td>
    </tr>
    <tr>
	    <th>&nbsp;</th>
	    <th><strong>廣東快樂十分（相關設定）</strong></th>
    </tr>
   <tr>
    <td class="t_right even">廣東快乐十分（封盤提前）</td>
    <td class="t_left"><span class="in">
      <input type="text" class="b_g"  maxlength="6" size="6" value="<%=autoOddsMap.get("GDKLSF_FPTIME/FPTIME")==null?"90":autoOddsMap.get("GDKLSF_FPTIME/FPTIME")%>" name="GDKLSF_FPTIME$FPTIME" readonly="readonly"/>
      <!-- <input type="text" class="b_g"  maxlength="6" size="6" value="90" name="GDKLSF_FPTIME/FPTIME" readonly="readonly"/> -->
      秒 
    </span>建議不低过90秒
    </td>
    </tr>
    
  <tr>
    <td class="t_right even">
    	廣東快樂十分
     <br/>  “两面”自動降賠率</td>
    <td class="t_left">
    <table width="784" border="0" class="none_border">
      <tbody><tr>
        <td colspan="3"><input type="radio" value="0" name="GDKLSF_DOUBLESIDE$STATE" <%if("0".equals((autoOddsMap.get("GDKLSF_DOUBLESIDE/STATE")))) out.print("checked"); %>/>
          <strong>啟用</strong>  
            <input type="radio" class="ml10" value="1" name="GDKLSF_DOUBLESIDE$STATE" <%if("1".equals(autoOddsMap.get("GDKLSF_DOUBLESIDE/STATE")) || "".equals((autoOddsMap.get("GDKLSF_DOUBLESIDE/STATE")))
            		|| autoOddsMap.get("GDKLSF_DOUBLESIDE/STATE")==null) out.print("checked"); %>/>
            <strong>禁用</strong>  
            <strong class="ml22 red">同路“对號”大路降賠率比例：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$ODDSTL_DS" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/ODDSTL_DS")==null?"10":autoOddsMap.get("GDKLSF_DOUBLESIDE/ODDSTL_DS")%>" />
            </span></strong> <strong class="red">（0为不降）</strong></td>
        </tr>
      <tr>
        <td width="33%">01 期没出降：<span class="in">
        <input type="text" class="b_g" maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_1" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_1")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_1")%>"/>
        </span> 連出降：<span class="in">
        <input type="text" class="b_g" maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_1" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_1")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_1")%>"/>
        </span></td>
        <td width="33%">11 期没出降：<span class="in">
          <input type="text" class="b_g" maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_11" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_11")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_11")%>"/>
        </span> 連出降：<span class="in">
        <input type="text" class="b_g" maxlength="8" size="6" name="GDKLSF_DOUBLESIDE$L_11" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_11")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_11")%>"/>
        </span></td>
        <td>21 期没出降：<span class="in">
          <input type="text" class="b_g" maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_21" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_21")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_21")%>"/>
        </span> 連出降：<span class="in">
        <input type="text" class="b_g" maxlength="6" size="6"  name="GDKLSF_DOUBLESIDE$L_21" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_21")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_21")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>02 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_2" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_2")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_2")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_2" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_2")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_2")%>"/>
        </span></td>
        <td>12 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_12" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_12")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_12")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_12" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_12")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_12")%>"/>
        </span></td>
        <td>22 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_22" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_22")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_22")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_22" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_22")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_22")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>03 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_3" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_3")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_3")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_3" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_3")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_3")%>"/>
        </span></td>
        <td>13 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_13" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_13")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_13")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_13" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_13")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_13")%>"/>
        </span></td>
        <td>23 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_23" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_23")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_23")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_23" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_23")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_23")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>04 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_4" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_4")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_4")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_4" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_4")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_4")%>"/>
        </span></td>
        <td>14 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_14" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_14")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_14")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_14" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_14")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_14")%>"/>
        </span></td>
        <td>24 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_24" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_24")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_24")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_24"  value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_24")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_24")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>05 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_5" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_5")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_5")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_5" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_5")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_5")%>"/>
        </span></td>
        <td>15 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_15" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_15")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_15")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_15" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_15")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_15")%>"/>
        </span></td>
        <td>25 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_25" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_25")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_25")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_25" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_25")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_25")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>06 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_6" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_6")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_6")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_6" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_6")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_6")%>"/>
        </span></td>
        <td>16 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_16" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_16")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_16")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_16" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_16")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_16")%>"/>
        </span></td>
        <td> (超過25期按25期降 )</td>
      </tr>
      <tr>
        <td>07 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_7" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_7")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_7")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_7" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_7")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_7")%>"/>
        </span></td>
        <td>17 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_17" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_17")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_17")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_17" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_17")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_17")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>08 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_8" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_8")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_8")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_8" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_8")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_8")%>"/>
        </span></td>
        <td>18 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_18" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_18")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_18")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_18" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_18")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_18")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>09 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_9" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_9")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_9")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_9" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_9")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_9")%>"/>
        </span></td>
        <td>19 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_19" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_19")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_19")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_19" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_19")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_19")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>10 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_10" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_10")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_10")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_10" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_10")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_10")%>"/>
        </span></td>
        <td>20 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$N_20" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/N_20")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/N_20")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_DOUBLESIDE$L_20" value="<%=autoOddsMap.get("GDKLSF_DOUBLESIDE/L_20")==null?"0":autoOddsMap.get("GDKLSF_DOUBLESIDE/L_20")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td class="blue" colspan="3">注：本動作必須在官網出上期結果后執行，如官網結果延遲降導致本動作延遲或失敗
        &nbsp;&nbsp;
          </td>
        </tr>
    </tbody></table>
   </td>
    </tr>
  <tr>
    <td class="t_right even">
      
    	廣東快樂十分
<br/> “遗漏”自動降賠率</td>
    <td class="t_left">
    <table width="784" border="0" class="none_border">
        <tbody><tr>
          <td colspan="3"><input type="radio" value="0" name="GDKLSF_YILOU$STATE" <%if("0".equals(autoOddsMap.get("GDKLSF_YILOU/STATE"))) out.print("checked"); %>/>
              <strong>啟用</strong>
              <input type="radio" class="ml10" value="1" name="GDKLSF_YILOU$STATE" <%if("1".equals(autoOddsMap.get("GDKLSF_YILOU/STATE")) || "".equals(autoOddsMap.get("GDKLSF_YILOU/STATE"))
            		  || autoOddsMap.get("GDKLSF_YILOU/STATE")==null) out.print("checked"); %>/>
              <strong>禁用</strong>
            </td>
        </tr>
        <tr>
          <td>03 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_3" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_3")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_3")%>"/>
          </span></td>
          <td width="21%">13 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_13" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_13")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_13")%>"/>
          </span></td>
          <td width="58%">23 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_23" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_23")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_23")%>" />
          </span></td>
          </tr>
        <tr>
          <td>04 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_4" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_4")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_4")%>"/>
          </span></td>
          <td>14 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_14" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_14")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_14")%>"/>
          </span></td>
          <td>24 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_24" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_24")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_24")%>"/>
          </span></td>
          </tr>
        <tr>
          <td>05 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_5" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_5")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_5")%>" />
          </span></td>
          <td>15 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_15" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_15")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_15")%>"/>
          </span></td>
          <td>25 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_25" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_25")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_25")%>"/>
          </span></td>
          </tr>
        <tr>
          <td>06 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_6" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_6")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_6")%>"/>
          </span></td>
          <td>16 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_16" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_16")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_16")%>"/>
          </span></td>
          <td>(超過25期按25期降 )</td>
        </tr>
        <tr>
          <td>07 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_7" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_7")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_7")%>"/>
          </span></td>
          <td>17 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_17" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_17")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_17")%>"/>
          </span></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>08 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_8" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_8")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_8")%>"/>
          </span></td>
          <td>18 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_18" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_18")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_18")%>"/>
          </span></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>09 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_9" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_9")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_9")%>"/>
          </span></td>
          <td>19 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_19" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_19")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_19")%>"/>
          </span></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>10 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_10" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_10")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_10")%>"/>
          </span></td>
          <td>20 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_20" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_20")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_20")%>"/>
          </span></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td width="21%">11 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_11" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_11")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_11")%>"/>
          </span></td>
          <td>21 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_21" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_21")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_21")%>"/>
          </span></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>12 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_12" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_12")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_12")%>"/>
          </span></td>
          <td>22 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="GDKLSF_YILOU$N_22" value="<%=autoOddsMap.get("GDKLSF_YILOU/N_22")==null?"0":autoOddsMap.get("GDKLSF_YILOU/N_22")%>"/>
          </span></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td class="blue" colspan="3">注：本動作必須在官網出上期結果后執行，如官網結果延遲降導致本動作延遲或失敗</td>
        </tr>
    </tbody>
    </table>
    
    </td>
  </tr>
    <tr>
	    <th>&nbsp;</th>
	    <th><strong>重慶時時彩（相關設定）</strong></th>
    </tr>
    <tr>
    <td class="t_right even">重慶時時彩-日場（封盤提前）</td>
    <td class="t_left"><span class="in">
      <input type="text" class="b_g" maxlength="6" size="6" value="90" name="" readonly="readonly"/>
      秒 
    </span>建議不低過90秒
    </td>
    </tr>
    <td class="t_right even">重慶時時彩-夜場（封盤提前）</td>
    <td class="t_left"><span class="in">
      <input type="text" class="b_g" maxlength="6" size="6" value="60" name="" readonly="readonly"/>
      秒 
    </span>建議不低過60秒
    </td>
    </tr>
     <td class="t_right even">
    	重慶時時彩
     <br/>  “两面”自動降賠率</td>
    <td class="t_left">
    <table width="784" border="0" class="none_border">
      <tbody><tr>
        <td colspan="3"><input type="radio" value="0" name="CQSSC_DOUBLESIDE$STATE" <%if("0".equals(autoOddsMap.get("CQSSC_DOUBLESIDE/STATE"))) out.print("checked"); %>/>
          <strong>啟用</strong>  
            <input type="radio" class="ml10" value="1" name="CQSSC_DOUBLESIDE$STATE" <%if("1".equals(autoOddsMap.get("CQSSC_DOUBLESIDE/STATE")) || "".equals(autoOddsMap.get("CQSSC_DOUBLESIDE/STATE"))
            		|| autoOddsMap.get("CQSSC_DOUBLESIDE/STATE")==null) out.print("checked"); %>/>
            <strong>禁用</strong>  
            <strong class="ml22 red">同路“对號”大路降賠率比例：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$ODDSTL_DS" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/ODDSTL_DS")==null?"":autoOddsMap.get("CQSSC_DOUBLESIDE/ODDSTL_DS")%>" />
            </span></strong> <strong class="red">（0为不降）</strong></td>
        </tr>
      <tr>
        <td width="33%">01 期没出降：<span class="in">
        <input type="text" class="b_g" maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_1" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_1")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_1")%>"/>
        </span> 連出降：<span class="in">
        <input type="text" class="b_g" maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_1" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_1")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_1")%>"/>
        </span></td>
        <td width="33%">11 期没出降：<span class="in">
          <input type="text" class="b_g" maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_11" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_11")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_11")%>"/>
        </span> 連出降：<span class="in">
        <input type="text" class="b_g" maxlength="8" size="6" name="CQSSC_DOUBLESIDE$L_11" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_11")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_11")%>"/>
        </span></td>
        <td>21 期没出降：<span class="in">
          <input type="text" class="b_g" maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_21" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_21")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_21")%>"/>
        </span> 連出降：<span class="in">
        <input type="text" class="b_g" maxlength="6" size="6"  name="CQSSC_DOUBLESIDE$L_21" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_21")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_21")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>02 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_2" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_2")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_2")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_2" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_2")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_2")%>"/>
        </span></td>
        <td>12 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_12" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_12")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_12")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_12" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_12")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_12")%>"/>
        </span></td>
        <td>22 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_22" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_22")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_22")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_22" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_22")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_22")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>03 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_3" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_3")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_3")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_3" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_3")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_3")%>"/>
        </span></td>
        <td>13 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_13" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_13")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_13")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_13" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_13")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_13")%>"/>
        </span></td>
        <td>23 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_23" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_23")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_23")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_23" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_23")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_23")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>04 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_4" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_4")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_4")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_4" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_4")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_4")%>"/>
        </span></td>
        <td>14 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_14" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_14")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_14")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_14" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_14")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_14")%>"/>
        </span></td>
        <td>24 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_24" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_24")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_24")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_24"  value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_24")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_24")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>05 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_5" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_5")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_5")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_5" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_5")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_5")%>"/>
        </span></td>
        <td>15 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_15" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_15")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_15")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_15" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_15")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_15")%>"/>
        </span></td>
        <td>25 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_25" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_25")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_25")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_25" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_25")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_25")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>06 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_6" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_6")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_6")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_6" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_6")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_6")%>"/>
        </span></td>
        <td>16 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_16" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_16")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_16")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_16" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_16")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_16")%>"/>
        </span></td>
        <td> (超過25期按25期降 )</td>
      </tr>
      <tr>
        <td>07 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_7" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_7")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_7")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_7" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_7")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_7")%>"/>
        </span></td>
        <td>17 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_17" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_17")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_17")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_17" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_17")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_17")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>08 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_8" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_8")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_8")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_8" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_8")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_8")%>"/>
        </span></td>
        <td>18 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_18" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_18")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_18")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_18" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_18")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_18")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>09 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_9" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_9")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_9")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_9" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_9")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_9")%>"/>
        </span></td>
        <td>19 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_19" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_19")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_19")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_19" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_19")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_19")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>10 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_10" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_10")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_10")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_10" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_10")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_10")%>"/>
        </span></td>
        <td>20 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$N_20" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/N_20")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/N_20")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="CQSSC_DOUBLESIDE$L_20" value="<%=autoOddsMap.get("CQSSC_DOUBLESIDE/L_20")==null?"0":autoOddsMap.get("CQSSC_DOUBLESIDE/L_20")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td class="blue" colspan="3">注：本動作必須在官網出上期結果后執行，如官網結果延遲降導致本動作延遲或失敗
        &nbsp;&nbsp;
          </td>
        </tr>
    </tbody></table>
   </td>
    <tr>
	    <th>&nbsp;</th>
	    <th><strong>北京賽車（相關設定）</strong></th>
    </tr>
     <tr>
	    <td class="t_right even red">北京賽車(PK-10)-是否開盤</td>
	    <td class="t_left">
	   <input type="radio" class="" value="0" name="BJSC_PERIODSTATE$PERIODSTATE" <%if("0".equals(autoOddsMap.get("BJSC_PERIODSTATE/PERIODSTATE"))) out.print("checked"); %>/>
     	開盤
        <input type="radio" class="ml10" value="1" name="BJSC_PERIODSTATE$PERIODSTATE" <%if("1".equals(autoOddsMap.get("BJSC_PERIODSTATE/PERIODSTATE"))) out.print("checked"); %>/> 封盤
        </td>
    </tr>
     <tr>
    <td class="t_right even">北京賽車-（封盘提前）</td>
    <td class="t_left"><span class="in">
      <input type="text" class="b_g"  maxlength="6" size="6" value="<%=autoOddsMap.get("BJSC_FPTIME/FPTIME")==null?"75":autoOddsMap.get("BJSC_FPTIME/FPTIME")%>" name="BJSC_FPTIME$FPTIME"/>
      <!-- <input type="text" class="b_g"  maxlength="6" size="6" value="75" name="BJSC_FPTIME/FPTIME" readonly="readonly"/> -->
     	 秒 
    </span>建議不低过75秒
    </td>
    </tr>
    
    <tr>
     <td class="t_right even">
    	北京賽車
     <br/>  “两面”自動降賠率</td>
    <td class="t_left">
    <table width="784" border="0" class="none_border">
      <tbody><tr>
        <td colspan="3"><input type="radio" value="0" name="BJSC_DOUBLESIDE$STATE" <%if("0".equals(autoOddsMap.get("BJSC_DOUBLESIDE/STATE"))) out.print("checked"); %>/>
          <strong>啟用</strong>  
            <input type="radio" class="ml10" value="1" name="BJSC_DOUBLESIDE$STATE" <%if("1".equals(autoOddsMap.get("BJSC_DOUBLESIDE/STATE")) || "".equals(autoOddsMap.get("BJSC_DOUBLESIDE/STATE"))
            		|| autoOddsMap.get("BJSC_DOUBLESIDE/STATE")==null) out.print("checked"); %>/>
            <strong>禁用</strong>  
            <strong class="ml22 red">同路“对號”大路降賠率比例：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$ODDSTL_DS" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/ODDSTL_DS")==null?"":autoOddsMap.get("BJSC_DOUBLESIDE/ODDSTL_DS")%>" />
            </span></strong> <strong class="red">（0为不降）</strong></td>
        </tr>
      <tr>
        <td width="33%">01 期没出降：<span class="in">
        <input type="text" class="b_g" maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_1" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_1")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_1")%>"/>
        </span> 連出降：<span class="in">
        <input type="text" class="b_g" maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_1" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_1")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_1")%>"/>
        </span></td>
        <td width="33%">11 期没出降：<span class="in">
          <input type="text" class="b_g" maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_11" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_11")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_11")%>"/>
        </span> 連出降：<span class="in">
        <input type="text" class="b_g" maxlength="8" size="6" name="BJSC_DOUBLESIDE$L_11" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_11")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_11")%>"/>
        </span></td>
        <td>21 期没出降：<span class="in">
          <input type="text" class="b_g" maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_21" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_21")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_21")%>"/>
        </span> 連出降：<span class="in">
        <input type="text" class="b_g" maxlength="6" size="6"  name="BJSC_DOUBLESIDE$L_21" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_21")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_21")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>02 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_2" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_2")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_2")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_2" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_2")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_2")%>"/>
        </span></td>
        <td>12 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_12" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_12")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_12")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_12" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_12")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_12")%>"/>
        </span></td>
        <td>22 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_22" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_22")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_22")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_22" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_22")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_22")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>03 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_3" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_3")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_3")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_3" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_3")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_3")%>"/>
        </span></td>
        <td>13 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_13" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_13")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_13")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_13" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_13")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_13")%>"/>
        </span></td>
        <td>23 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_23" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_23")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_23")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_23" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_23")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_23")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>04 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_4" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_4")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_4")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_4" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_4")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_4")%>"/>
        </span></td>
        <td>14 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_14" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_14")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_14")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_14" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_14")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_14")%>"/>
        </span></td>
        <td>24 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_24" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_24")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_24")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_24"  value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_24")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_24")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>05 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_5" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_5")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_5")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_5" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_5")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_5")%>"/>
        </span></td>
        <td>15 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_15" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_15")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_15")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_15" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_15")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_15")%>"/>
        </span></td>
        <td>25 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_25" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_25")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_25")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_25" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_25")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_25")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>06 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_6" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_6")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_6")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_6" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_6")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_6")%>"/>
        </span></td>
        <td>16 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_16" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_16")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_16")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_16" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_16")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_16")%>"/>
        </span></td>
        <td> (超過25期按25期降 )</td>
      </tr>
      <tr>
        <td>07 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_7" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_7")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_7")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_7" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_7")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_7")%>"/>
        </span></td>
        <td>17 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_17" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_17")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_17")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_17" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_17")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_17")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>08 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_8" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_8")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_8")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_8" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_8")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_8")%>"/>
        </span></td>
        <td>18 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_18" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_18")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_18")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_18" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_18")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_18")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>09 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_9" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_9")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_9")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_9" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_9")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_9")%>"/>
        </span></td>
        <td>19 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_19" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_19")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_19")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_19" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_19")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_19")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>10 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_10" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_10")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_10")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_10" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_10")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_10")%>"/>
        </span></td>
        <td>20 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$N_20" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/N_20")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/N_20")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="BJSC_DOUBLESIDE$L_20" value="<%=autoOddsMap.get("BJSC_DOUBLESIDE/L_20")==null?"0":autoOddsMap.get("BJSC_DOUBLESIDE/L_20")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td class="blue" colspan="3">注：本動作必須在官網出上期結果后執行，如官網結果延遲降導致本動作延遲或失敗
        &nbsp;&nbsp;
          </td>
        </tr>
    </tbody></table>
   </td>
   </tr>
   <tr>
	    <th>&nbsp;</th>
	    <th><strong>江苏骰寶(快3)（相關設定）</strong></th>
    </tr>
     <tr>
	    <td class="t_right even red">江苏骰寶(快3)-是否開盤</td>
	    <td class="t_left">
	   <input type="radio" class="" value="0" name="K3_PERIODSTATE$PERIODSTATE" <%if("0".equals(autoOddsMap.get("K3_PERIODSTATE/PERIODSTATE"))) out.print("checked"); %>/>
     	開盤
        <input type="radio" class="ml10" value="1" name="K3_PERIODSTATE$PERIODSTATE" <%if("1".equals(autoOddsMap.get("K3_PERIODSTATE/PERIODSTATE"))) out.print("checked"); %>/> 封盤
        </td>
    </tr>
     <tr>
    <td class="t_right even">江苏骰寶(快3)-（封盘提前）</td>
    <td class="t_left"><span class="in">
      <input type="text" class="b_g"  maxlength="6" size="6" readonly="readonly" value="<%=autoOddsMap.get("K3_FPTIME/FPTIME")==null?"150":autoOddsMap.get("K3_FPTIME/FPTIME")%>" name="K3_FPTIME$FPTIME"/>
     	 秒 
    </span>建議不低过120秒
    </td>
    </tr>
    <tr>
	    <th>&nbsp;</th>
	    <th><strong>幸运农场（相關設定）</strong></th>
    </tr>
    <tr>
	    <td class="t_right even red">幸运农场-是否開盤</td>
	    <td class="t_left">
	   <input type="radio" class="" value="0" name="NC_PERIODSTATE$PERIODSTATE" <%if("0".equals(autoOddsMap.get("NC_PERIODSTATE/PERIODSTATE"))) out.print("checked"); %>/>
     	開盤
        <input type="radio" class="ml10" value="1" name="NC_PERIODSTATE$PERIODSTATE" <%if("1".equals(autoOddsMap.get("NC_PERIODSTATE/PERIODSTATE"))) out.print("checked"); %>/> 封盤
        </td>
    </tr>
   <tr>
    <td class="t_right even">幸运农场（封盤提前）</td>
    <td class="t_left"><span class="in">
      <input type="text" class="b_g"  maxlength="6" size="6" value="<%=autoOddsMap.get("NC_FPTIME/FPTIME")==null?"90":autoOddsMap.get("NC_FPTIME/FPTIME")%>" name="NC_FPTIME$FPTIME" readonly="readonly"/>
      秒 
    </span>建議不低过90秒
    </td>
    </tr>
      <tr>
    <td class="t_right even">
    	幸运农场
     <br/>  “两面”自動降賠率</td>
    <td class="t_left">
    <table width="784" border="0" class="none_border">
      <tbody><tr>
        <td colspan="3"><input type="radio" value="0" name="NC_DOUBLESIDE$STATE" <%if("0".equals((autoOddsMap.get("NC_DOUBLESIDE/STATE")))) out.print("checked"); %>/>
          <strong>啟用</strong>  
            <input type="radio" class="ml10" value="1" name="NC_DOUBLESIDE$STATE" <%if("1".equals(autoOddsMap.get("NC_DOUBLESIDE/STATE")) || "".equals((autoOddsMap.get("NC_DOUBLESIDE/STATE")))
            		|| autoOddsMap.get("NC_DOUBLESIDE/STATE")==null) out.print("checked"); %>/>
            <strong>禁用</strong>  
            <strong class="ml22 red">同路“对號”大路降賠率比例：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$ODDSTL_DS" value="<%=autoOddsMap.get("NC_DOUBLESIDE/ODDSTL_DS")==null?"10":autoOddsMap.get("NC_DOUBLESIDE/ODDSTL_DS")%>" />
            </span></strong> <strong class="red">（0为不降）</strong></td>
        </tr>
      <tr>
        <td width="33%">01 期没出降：<span class="in">
        <input type="text" class="b_g" maxlength="6" size="6" name="NC_DOUBLESIDE$N_1" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_1")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_1")%>"/>
        </span> 連出降：<span class="in">
        <input type="text" class="b_g" maxlength="6" size="6" name="NC_DOUBLESIDE$L_1" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_1")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_1")%>"/>
        </span></td>
        <td width="33%">11 期没出降：<span class="in">
          <input type="text" class="b_g" maxlength="6" size="6" name="NC_DOUBLESIDE$N_11" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_11")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_11")%>"/>
        </span> 連出降：<span class="in">
        <input type="text" class="b_g" maxlength="8" size="6" name="NC_DOUBLESIDE$L_11" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_11")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_11")%>"/>
        </span></td>
        <td>21 期没出降：<span class="in">
          <input type="text" class="b_g" maxlength="6" size="6" name="NC_DOUBLESIDE$N_21" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_21")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_21")%>"/>
        </span> 連出降：<span class="in">
        <input type="text" class="b_g" maxlength="6" size="6"  name="NC_DOUBLESIDE$L_21" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_21")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_21")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>02 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_2" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_2")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_2")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_2" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_2")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_2")%>"/>
        </span></td>
        <td>12 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_12" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_12")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_12")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_12" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_12")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_12")%>"/>
        </span></td>
        <td>22 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_22" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_22")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_22")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_22" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_22")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_22")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>03 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_3" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_3")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_3")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_3" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_3")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_3")%>"/>
        </span></td>
        <td>13 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_13" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_13")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_13")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_13" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_13")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_13")%>"/>
        </span></td>
        <td>23 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_23" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_23")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_23")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_23" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_23")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_23")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>04 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_4" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_4")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_4")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_4" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_4")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_4")%>"/>
        </span></td>
        <td>14 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_14" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_14")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_14")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_14" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_14")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_14")%>"/>
        </span></td>
        <td>24 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_24" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_24")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_24")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_24"  value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_24")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_24")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>05 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_5" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_5")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_5")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_5" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_5")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_5")%>"/>
        </span></td>
        <td>15 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_15" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_15")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_15")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_15" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_15")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_15")%>"/>
        </span></td>
        <td>25 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_25" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_25")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_25")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_25" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_25")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_25")%>"/>
        </span></td>
      </tr>
      <tr>
        <td>06 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_6" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_6")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_6")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_6" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_6")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_6")%>"/>
        </span></td>
        <td>16 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_16" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_16")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_16")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_16" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_16")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_16")%>"/>
        </span></td>
        <td> (超過25期按25期降 )</td>
      </tr>
      <tr>
        <td>07 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_7" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_7")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_7")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_7" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_7")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_7")%>"/>
        </span></td>
        <td>17 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_17" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_17")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_17")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_17" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_17")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_17")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>08 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_8" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_8")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_8")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_8" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_8")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_8")%>"/>
        </span></td>
        <td>18 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_18" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_18")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_18")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_18" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_18")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_18")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>09 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_9" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_9")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_9")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_9" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_9")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_9")%>"/>
        </span></td>
        <td>19 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_19" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_19")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_19")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_19" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_19")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_19")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>10 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_10" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_10")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_10")%>"/>
          </span> 連出降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_10" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_10")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_10")%>"/>
        </span></td>
        <td>20 期没出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$N_20" value="<%=autoOddsMap.get("NC_DOUBLESIDE/N_20")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/N_20")%>"/>
          </span> 連出降：<span class="in">
          <input type="text" class="b_g"  maxlength="6" size="6" name="NC_DOUBLESIDE$L_20" value="<%=autoOddsMap.get("NC_DOUBLESIDE/L_20")==null?"0":autoOddsMap.get("NC_DOUBLESIDE/L_20")%>"/>
        </span></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td class="blue" colspan="3">注：本動作必須在官網出上期結果后執行，如官網結果延遲降導致本動作延遲或失敗
        &nbsp;&nbsp;
          </td>
        </tr>
    </tbody></table>
   </td>
    </tr>
  <tr>
    <td class="t_right even">
    	幸运农场
<br/> “遗漏”自動降賠率</td>
    <td class="t_left">
    <table width="784" border="0" class="none_border">
        <tbody><tr>
          <td colspan="3"><input type="radio" value="0" name="NC_YILOU$STATE" <%if("0".equals(autoOddsMap.get("NC_YILOU/STATE"))) out.print("checked"); %>/>
              <strong>啟用</strong>
              <input type="radio" class="ml10" value="1" name="NC_YILOU$STATE" <%if("1".equals(autoOddsMap.get("NC_YILOU/STATE")) || "".equals(autoOddsMap.get("NC_YILOU/STATE"))
            		  || autoOddsMap.get("NC_YILOU/STATE")==null) out.print("checked"); %>/>
              <strong>禁用</strong>
            </td>
        </tr>
        <tr>
          <td>03 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_3" value="<%=autoOddsMap.get("NC_YILOU/N_3")==null?"0":autoOddsMap.get("NC_YILOU/N_3")%>"/>
          </span></td>
          <td width="21%">13 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_13" value="<%=autoOddsMap.get("NC_YILOU/N_13")==null?"0":autoOddsMap.get("NC_YILOU/N_13")%>"/>
          </span></td>
          <td width="58%">23 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_23" value="<%=autoOddsMap.get("NC_YILOU/N_23")==null?"0":autoOddsMap.get("NC_YILOU/N_23")%>" />
          </span></td>
          </tr>
        <tr>
          <td>04 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_4" value="<%=autoOddsMap.get("NC_YILOU/N_4")==null?"0":autoOddsMap.get("NC_YILOU/N_4")%>"/>
          </span></td>
          <td>14 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_14" value="<%=autoOddsMap.get("NC_YILOU/N_14")==null?"0":autoOddsMap.get("NC_YILOU/N_14")%>"/>
          </span></td>
          <td>24 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_24" value="<%=autoOddsMap.get("NC_YILOU/N_24")==null?"0":autoOddsMap.get("NC_YILOU/N_24")%>"/>
          </span></td>
          </tr>
        <tr>
          <td>05 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_5" value="<%=autoOddsMap.get("NC_YILOU/N_5")==null?"0":autoOddsMap.get("NC_YILOU/N_5")%>" />
          </span></td>
          <td>15 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_15" value="<%=autoOddsMap.get("NC_YILOU/N_15")==null?"0":autoOddsMap.get("NC_YILOU/N_15")%>"/>
          </span></td>
          <td>25 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_25" value="<%=autoOddsMap.get("NC_YILOU/N_25")==null?"0":autoOddsMap.get("NC_YILOU/N_25")%>"/>
          </span></td>
          </tr>
        <tr>
          <td>06 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_6" value="<%=autoOddsMap.get("NC_YILOU/N_6")==null?"0":autoOddsMap.get("NC_YILOU/N_6")%>"/>
          </span></td>
          <td>16 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_16" value="<%=autoOddsMap.get("NC_YILOU/N_16")==null?"0":autoOddsMap.get("NC_YILOU/N_16")%>"/>
          </span></td>
          <td>(超過25期按25期降 )</td>
        </tr>
        <tr>
          <td>07 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_7" value="<%=autoOddsMap.get("NC_YILOU/N_7")==null?"0":autoOddsMap.get("NC_YILOU/N_7")%>"/>
          </span></td>
          <td>17 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_17" value="<%=autoOddsMap.get("NC_YILOU/N_17")==null?"0":autoOddsMap.get("NC_YILOU/N_17")%>"/>
          </span></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>08 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_8" value="<%=autoOddsMap.get("NC_YILOU/N_8")==null?"0":autoOddsMap.get("NC_YILOU/N_8")%>"/>
          </span></td>
          <td>18 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_18" value="<%=autoOddsMap.get("NC_YILOU/N_18")==null?"0":autoOddsMap.get("NC_YILOU/N_18")%>"/>
          </span></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>09 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_9" value="<%=autoOddsMap.get("NC_YILOU/N_9")==null?"0":autoOddsMap.get("NC_YILOU/N_9")%>"/>
          </span></td>
          <td>19 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_19" value="<%=autoOddsMap.get("NC_YILOU/N_19")==null?"0":autoOddsMap.get("NC_YILOU/N_19")%>"/>
          </span></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>10 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_10" value="<%=autoOddsMap.get("NC_YILOU/N_10")==null?"0":autoOddsMap.get("NC_YILOU/N_10")%>"/>
          </span></td>
          <td>20 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_20" value="<%=autoOddsMap.get("NC_YILOU/N_20")==null?"0":autoOddsMap.get("NC_YILOU/N_20")%>"/>
          </span></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td width="21%">11 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_11" value="<%=autoOddsMap.get("NC_YILOU/N_11")==null?"0":autoOddsMap.get("NC_YILOU/N_11")%>"/>
          </span></td>
          <td>21 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_21" value="<%=autoOddsMap.get("NC_YILOU/N_21")==null?"0":autoOddsMap.get("NC_YILOU/N_21")%>"/>
          </span></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>12 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_12" value="<%=autoOddsMap.get("NC_YILOU/N_12")==null?"0":autoOddsMap.get("NC_YILOU/N_12")%>"/>
          </span></td>
          <td>22 期遺漏降：<span class="in">
            <input type="text" class="b_g"  maxlength="6" size="6" name="NC_YILOU$N_22" value="<%=autoOddsMap.get("NC_YILOU/N_22")==null?"0":autoOddsMap.get("NC_YILOU/N_22")%>"/>
          </span></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td class="blue" colspan="3">注：本動作必須在官網出上期結果后執行，如官網結果延遲降導致本動作延遲或失敗</td>
        </tr>
    </tbody>
    </table>
    
    </td>
  </tr>
</tbody></table>
<td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
	</tr>
</table>
</td>

</tr>
		<tr>
			<td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					<td width="12" height="35"><img
						src="${pageContext.request.contextPath}/images/admin/tab_18.gif"
						width="12" height="35" /></td>
					<td align='center'>
					  <input name="" type="submit"
						class="btn2" value="保存" onclick="return confirm('确定保存修改?')"/> <input name="" type="reset"
						class="btn2" value="取消" /> 
						</td>
					<td width="16"><img
						src="${pageContext.request.contextPath}/images/admin/tab_20.gif"
						width="16" height="35" /></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
</s:form>