<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<div id="content">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
                <td width="99%" align="left" class="F_bold"><strong>&nbsp;信用資料</strong></td>
              </tr>
            </table>
            </td>
            <td align="right" width="70%">&nbsp;</td>
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
		        <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy autoHeight" >
		  <tbody>
		    <tr>
		      <th colspan="3"><strong>基礎信息</strong></th>
		    </tr>
		    <tr bgcolor="#ffffff">
		      <td width="22%" class="t_right even2">帳&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;號</td>
		      <td width="78%" class="t_left" colspan="2" align="left">
		          <s:property value="#session.manager_login_info_session.account" />&nbsp;&nbsp;&nbsp;【級別：
					<s:if test="userInfo.userType==3">
					      分公司
					</s:if><s:elseif test="userInfo.userType==4">
					股东
					</s:elseif><s:elseif test="userInfo.userType==5">
					總代理
					</s:elseif><s:else>
					代理
					</s:else>
			   】</td>
		    </tr>
		    <tr bgcolor="#ffffff">
		      <td class="t_right even2">信用額度 </td>
		      <td class="t_left" colspan="2"><s:property value="#request.totalCredit" escape="false" /></td>
		    </tr>
		    <tr bgcolor="#ffffff">
		      <td class="t_right even2">可用金額</td>
		      <td colspan="2" class="t_left"><s:property value="#request.avalilableCredit" escape="false" /></td>
		    </tr>
		  </tbody>
		</table>
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
        <th>交易類型</th>
        <th>A盤</th>
        <th>B盤</th>
        <th>C盤</th>
        <th>單注限額</th>
        <th>單期限額</th>
      </tr>
      <tr>
        <td class="even2">第一球 </td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_ONE_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_ONE_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_ONE_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_ONE_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_ONE_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">第二球</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_TWO_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_TWO_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_TWO_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_TWO_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_TWO_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第三球</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_THREE_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_THREE_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_THREE_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_THREE_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_THREE_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第四球</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_FOUR_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_FOUR_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_FOUR_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_FOUR_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_FOUR_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第五球</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_FIVE_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_FIVE_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_FIVE_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_FIVE_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_FIVE_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第六球</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_SIX_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_SIX_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_SIX_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_SIX_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_SIX_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第七球</td>
          <td name="gd_commission"><s:property value="#request.commissions.GD_SEVEN_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_SEVEN_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_SEVEN_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_SEVEN_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_SEVEN_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第八球</td>
           <td name="gd_commission"><s:property value="#request.commissions.GD_EIGHT_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_EIGHT_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_EIGHT_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_EIGHT_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_EIGHT_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">1-8大小</td>
           <td name="gd_commission"><s:property value="#request.commissions.GD_OEDX_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_OEDX_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_OEDX_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_OEDX_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_OEDX_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">1-8單雙</td>
          <td name="gd_commission"><s:property value="#request.commissions.GD_OEDS_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_OEDS_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_OEDS_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_OEDS_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_OEDS_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">1-8尾數大小</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_OEWSDX_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_OEWSDX_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_OEWSDX_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_OEWSDX_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_OEWSDX_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">1-8合數單雙</td>
       <td name="gd_commission"><s:property value="#request.commissions.GD_HSDS_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_HSDS_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_HSDS_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_HSDS_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_HSDS_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">1-8方位</td>
         <td name="gd_commission"><s:property value="#request.commissions.GD_FW_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_FW_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_FW_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_FW_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_FW_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">1-8中發白</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_ZF_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_ZF_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_ZF_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_ZF_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_ZF_BALL.itemQuotas"/></td>
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
        <th>交易類型</th>
        <th>A盤</th>
        <th>B盤</th>
        <th>C盤</th>
        <th>單注限額</th>
        <th>單期限額</th>
      </tr>
      <tr>
        <td class="even2">總和大小</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_ZHDX_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_ZHDX_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_ZHDX_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_ZHDX_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_ZHDX_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">總和單雙</td>
         <td name="gd_commission"><s:property value="#request.commissions.GD_ZHDS_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_ZHDS_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_ZHDS_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_ZHDS_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_ZHDS_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">總和尾數大小</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_ZHWSDX_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_ZHWSDX_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_ZHWSDX_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_ZHWSDX_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_ZHWSDX_BALL.itemQuotas"/></td>
      </tr>
    
      <tr>
        <td class="even2">龍虎</td>
          <td name="gd_commission"><s:property value="#request.commissions.GD_LH_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_LH_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_LH_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_LH_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_LH_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">任選二</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RXH_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RXH_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RXH_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_RXH_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_RXH_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">選二連直</td>
         <td name="gd_commission"><s:property value="#request.commissions.GD_RELZ_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RELZ_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RELZ_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_RELZ_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_RELZ_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">選二連組</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RTLZ_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RTLZ_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RTLZ_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_RTLZ_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_RTLZ_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">任選三</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RXS_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RXS_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RXS_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_RXS_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_RXS_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">選三前直</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_XSQZ_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_XSQZ_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_XSQZ_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_XSQZ_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_XSQZ_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">選三前組</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_XTQZ_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_XTQZ_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_XTQZ_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_XTQZ_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_XTQZ_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">任選四</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RXF_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RXF_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RXF_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_RXF_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_RXF_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">任選五</td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RXW_BALL.displayCommissionA"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RXW_BALL.displayCommissionB"/></td>
        <td name="gd_commission"><s:property value="#request.commissions.GD_RXW_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.GD_RXW_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.GD_RXW_BALL.itemQuotas"/></td>
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
        <th>交易類型</th>
        <th>A盤</th>
        <th>B盤</th>
        <th>C盤</th>
        <th>單注限額</th>
        <th>單期限額</th>
      </tr>
      <tr>
        <td class="even2">第一球</td>
      <td name="cq_commission"><s:property value="#request.commissions.CQ_ONE_BALL.displayCommissionA"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_ONE_BALL.displayCommissionB"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_ONE_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.CQ_ONE_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.CQ_ONE_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">第二球</td>
    <td name="cq_commission"><s:property value="#request.commissions.CQ_TWO_BALL.displayCommissionA"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_TWO_BALL.displayCommissionB"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_TWO_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.CQ_TWO_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.CQ_TWO_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第三球</td>
      <td name="cq_commission"><s:property value="#request.commissions.CQ_THREE_BALL.displayCommissionA"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_THREE_BALL.displayCommissionB"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_THREE_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.CQ_THREE_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.CQ_THREE_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第四球</td>
      <td name="cq_commission"><s:property value="#request.commissions.CQ_FOUR_BALL.displayCommissionA"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_FOUR_BALL.displayCommissionB"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_FOUR_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.CQ_FOUR_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.CQ_FOUR_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第五球</td>
         <td name="cq_commission"><s:property value="#request.commissions.CQ_FIVE_BALL.displayCommissionA"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_FIVE_BALL.displayCommissionB"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_FIVE_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.CQ_FIVE_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.CQ_FIVE_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">1-5大小</td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_OFDX_BALL.displayCommissionA"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_OFDX_BALL.displayCommissionB"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_OFDX_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.CQ_OFDX_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.CQ_OFDX_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">1-5單雙</td>
   <td name="cq_commission"><s:property value="#request.commissions.CQ_OFDS_BALL.displayCommissionA"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_OFDS_BALL.displayCommissionB"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_OFDS_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.CQ_OFDS_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.CQ_OFDS_BALL.itemQuotas"/></td>
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
        <th>交易類型</th>
        <th>A盤</th>
        <th>B盤</th>
        <th>C盤</th>
        <th>單注限額</th>
        <th>單期限額</th>
      </tr>
      <tr>
        <td class="even2">總和大小</td>
     <td name="cq_commission"><s:property value="#request.commissions.CQ_ZHDX_BALL.displayCommissionA"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_ZHDX_BALL.displayCommissionB"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_ZHDX_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.CQ_ZHDX_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.CQ_ZHDX_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">總和單雙</td>
 	    <td name="cq_commission"><s:property value="#request.commissions.CQ_ZHDS_BALL.displayCommissionA"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_ZHDS_BALL.displayCommissionB"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_ZHDS_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.CQ_ZHDS_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.CQ_ZHDS_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">龍虎和</td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_LH_BALL.displayCommissionA"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_LH_BALL.displayCommissionB"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_LH_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.CQ_LH_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.CQ_LH_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">前三</td>
  <td name="cq_commission"><s:property value="#request.commissions.CQ_QS_BALL.displayCommissionA"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_QS_BALL.displayCommissionB"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_QS_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.CQ_QS_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.CQ_QS_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">中三</td>
    <td name="cq_commission"><s:property value="#request.commissions.CQ_ZS_BALL.displayCommissionA"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_ZS_BALL.displayCommissionB"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_ZS_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.CQ_ZS_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.CQ_ZS_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">后三</td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_HS_BALL.displayCommissionA"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_HS_BALL.displayCommissionB"/></td>
        <td name="cq_commission"><s:property value="#request.commissions.CQ_HS_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.CQ_HS_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.CQ_HS_BALL.itemQuotas"/></td>
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
        <th>交易類型</th>
        <th>A盤</th>
        <th>B盤</th>
        <th>C盤</th>
        <th>單注限額</th>
        <th>單期限額</th>
      </tr>
      <tr>
        <td class="even2">冠軍</td>
     <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_FIRST.displayCommissionA"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_FIRST.displayCommissionB"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_FIRST.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_FIRST.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_FIRST.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">亚軍</td>
     <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_SECOND.displayCommissionA"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_SECOND.displayCommissionB"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_SECOND.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_SECOND.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_SECOND.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第三名</td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_THIRD.displayCommissionA"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_THIRD.displayCommissionB"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_THIRD.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_THIRD.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_THIRD.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第四名</td>
       <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_FORTH.displayCommissionA"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_FORTH.displayCommissionB"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_FORTH.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_FORTH.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_FORTH.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第五</td>
     <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_FIFTH.displayCommissionA"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_FIFTH.displayCommissionB"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_FIFTH.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_FIFTH.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_FIFTH.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第六名</td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_SIXTH.displayCommissionA"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_SIXTH.displayCommissionB"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_SIXTH.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_SIXTH.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_SIXTH.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第七名</td>
      <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_SEVENTH.displayCommissionA"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_SEVENTH.displayCommissionB"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_SEVENTH.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_SEVENTH.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_SEVENTH.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第八名</td>
     <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_EIGHTH.displayCommissionA"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_EIGHTH.displayCommissionB"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_EIGHTH.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_EIGHTH.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_EIGHTH.itemQuotas"/></td>
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
        <th>交易類型</th>
        <th>A盤</th>
        <th>B盤</th>
        <th>C盤</th>
        <th>單注限額</th>
        <th>單期限額</th>
      </tr>
      <tr>
        <td class="even2">第九名</td>
     <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_NINTH.displayCommissionA"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_NINTH.displayCommissionB"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_NINTH.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_NINTH.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_NINTH.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">第十名</td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_TENTH.displayCommissionA"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_TENTH.displayCommissionB"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_BALL_TENTH.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_TENTH.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_BALL_TENTH.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">1-10大小</td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_1_10_DX.displayCommissionA"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_1_10_DX.displayCommissionB"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_1_10_DX.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_1_10_DX.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_1_10_DX.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">1-10單雙</td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_1_10_DS.displayCommissionA"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_1_10_DS.displayCommissionB"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_1_10_DS.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_1_10_DS.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_1_10_DS.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">1-5龍虎</td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_1_5_LH.displayCommissionA"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_1_5_LH.displayCommissionB"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_1_5_LH.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_1_5_LH.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_1_5_LH.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">冠亞軍和大小</td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_DOUBLSIDE_DX.displayCommissionA"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_DOUBLSIDE_DX.displayCommissionB"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_DOUBLSIDE_DX.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_DOUBLSIDE_DX.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_DOUBLSIDE_DX.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">冠亞軍和單雙</td>
     <td><s:property value="#request.commissions.BJ_DOUBLSIDE_DS.displayCommissionA"/></td>
        <td><s:property value="#request.commissions.BJ_DOUBLSIDE_DS.displayCommissionB"/></td>
        <td><s:property value="#request.commissions.BJ_DOUBLSIDE_DS.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_DOUBLSIDE_DS.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_DOUBLSIDE_DS.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">冠亞軍和</td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_GROUP.displayCommissionA"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_GROUP.displayCommissionB"/></td>
        <td name="bj_commission"><s:property value="#request.commissions.BJ_GROUP.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.BJ_GROUP.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.BJ_GROUP.itemQuotas"/></td>
      </tr>
    </table></td>
  </tr>
</table>
        <!--NC开始-->
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
        <th>交易類型</th>
        <th>A盤</th>
        <th>B盤</th>
        <th>C盤</th>
        <th>單注限額</th>
        <th>單期限額</th>
      </tr>
      <tr>
        <td class="even2">第一球 </td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_ONE_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_ONE_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_ONE_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_ONE_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_ONE_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">第二球</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_TWO_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_TWO_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_TWO_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_TWO_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_TWO_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第三球</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_THREE_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_THREE_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_THREE_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_THREE_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_THREE_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第四球</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_FOUR_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_FOUR_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_FOUR_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_FOUR_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_FOUR_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第五球</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_FIVE_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_FIVE_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_FIVE_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_FIVE_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_FIVE_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第六球</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_SIX_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_SIX_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_SIX_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_SIX_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_SIX_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第七球</td>
          <td name="NC_commission"><s:property value="#request.commissions.NC_SEVEN_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_SEVEN_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_SEVEN_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_SEVEN_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_SEVEN_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">第八球</td>
           <td name="NC_commission"><s:property value="#request.commissions.NC_EIGHT_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_EIGHT_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_EIGHT_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_EIGHT_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_EIGHT_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">1-8大小</td>
           <td name="NC_commission"><s:property value="#request.commissions.NC_OEDX_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_OEDX_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_OEDX_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_OEDX_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_OEDX_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">1-8單雙</td>
          <td name="NC_commission"><s:property value="#request.commissions.NC_OEDS_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_OEDS_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_OEDS_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_OEDS_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_OEDS_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">1-8尾數大小</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_OEWSDX_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_OEWSDX_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_OEWSDX_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_OEWSDX_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_OEWSDX_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">1-8合數單雙</td>
       <td name="NC_commission"><s:property value="#request.commissions.NC_HSDS_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_HSDS_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_HSDS_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_HSDS_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_HSDS_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">1-8方位</td>
         <td name="NC_commission"><s:property value="#request.commissions.NC_FW_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_FW_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_FW_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_FW_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_FW_BALL.itemQuotas"/></td>
        </tr>
      <tr>
        <td class="even2">1-8中發白</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_ZF_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_ZF_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_ZF_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_ZF_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_ZF_BALL.itemQuotas"/></td>
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
        <th>交易類型</th>
        <th>A盤</th>
        <th>B盤</th>
        <th>C盤</th>
        <th>單注限額</th>
        <th>單期限額</th>
      </tr>
      <tr>
        <td class="even2">總和大小</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_ZHDX_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_ZHDX_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_ZHDX_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_ZHDX_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_ZHDX_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">總和單雙</td>
         <td name="NC_commission"><s:property value="#request.commissions.NC_ZHDS_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_ZHDS_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_ZHDS_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_ZHDS_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_ZHDS_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">總和尾數大小</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_ZHWSDX_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_ZHWSDX_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_ZHWSDX_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_ZHWSDX_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_ZHWSDX_BALL.itemQuotas"/></td>
      </tr>
    
      <tr>
        <td class="even2">龍虎</td>
          <td name="NC_commission"><s:property value="#request.commissions.NC_LH_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_LH_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_LH_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_LH_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_LH_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">任選二</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RXH_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RXH_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RXH_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_RXH_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_RXH_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">選二連直</td>
         <td name="NC_commission"><s:property value="#request.commissions.NC_RELZ_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RELZ_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RELZ_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_RELZ_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_RELZ_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">選二連組</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RTLZ_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RTLZ_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RTLZ_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_RTLZ_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_RTLZ_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">任選三</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RXS_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RXS_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RXS_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_RXS_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_RXS_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">選三前直</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_XSQZ_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_XSQZ_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_XSQZ_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_XSQZ_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_XSQZ_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">選三前組</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_XTQZ_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_XTQZ_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_XTQZ_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_XTQZ_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_XTQZ_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">任選四</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RXF_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RXF_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RXF_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_RXF_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_RXF_BALL.itemQuotas"/></td>
      </tr>
      <tr>
        <td class="even2">任選五</td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RXW_BALL.displayCommissionA"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RXW_BALL.displayCommissionB"/></td>
        <td name="NC_commission"><s:property value="#request.commissions.NC_RXW_BALL.displayCommissionC"/></td>
        <td><s:property value="#request.commissions.NC_RXW_BALL.bettingQuotas"/></td>
        <td><s:property value="#request.commissions.NC_RXW_BALL.itemQuotas"/></td>
      </tr>
    </table>
    </td>
  </tr>
</table>
          <!-- NC结束 -->
          <!--北京结束-->         
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



</body>
</html>