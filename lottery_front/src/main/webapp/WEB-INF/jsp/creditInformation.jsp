<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <link type="text/css" rel="stylesheet" href="/css/main.css" />
 </head>
<body style="width:95%">
<table cellspacing="0" cellpadding="0" border="0" width="804">
  <tbody><tr>
    <!-- 中间内容开始 -->
    <td align="left" width="804" valign="top">
<div class="main">
  <table cellspacing="0" cellpadding="0" border="0" width="88%" class="king xy autoHeight">
  <tbody>
    <tr>
      <th colspan="3">信用資料</th>
    </tr>
    <tr bgcolor="#ffffff">
      <td width="22%" class="tr" bgcolor="#FFF5EC">會員帳號</td>
      <td width="78%" valign="middle" class="tl" colspan="2">${account }&nbsp;（${plate}盤）</td>
    </tr>
    <tr bgcolor="#ffffff">
      <td class="tr" bgcolor="#FFF5EC">信用額度 </td>
      <td class="tl" colspan="2">${totalCredit}</td>
    </tr>
    <tr bgcolor="#ffffff">
      <td class="tr" bgcolor="#FFF5EC">可用金額</td>
      <td colspan="2" class="tl">${avalilableCredit }</td>
    </tr>
  </tbody>
</table>
<table width="88%" border="0" cellspacing="0" cellpadding="0" >
 <tr>
 <td width="100%" align="left" valign="top" colspan="2">
 <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy">
          <th class="type" >廣東快樂十分鐘</th>
 </table></td>
        </tr>
  <tr>
    <td width="50%" align="left" valign="top"><table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy">
      <tbody>
        
        <tr>
          <th width="18%">交易類型</th>
          <th width="10%">${plate }
            盤</th>
          <th width="19%">單註限額</th>
          <th width="18%">單期限額</th>
        </tr>
        <c:if test="${ plate=='A'}">
          <c:forEach  items="${ gdCommissionless15}"  var="gd" begin="0" end="12" >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${gd.playFinalTypeName}</td>
              <td>${gd.displayCommissionA}</td>
              <td class="t_right">${gd.bettingQuotas}&nbsp;</td>
              <td class="t_right">${gd.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
       <c:if test="${ plate=='B'}">
          <c:forEach  items="${ gdCommissionless15}" var="gd"  begin="0" end="12" >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${gd.playFinalTypeName}</td>
              <td>${gd.displayCommissionB}</td>
              <td class="t_right">${gd.bettingQuotas}&nbsp;</td>
              <td class="t_right">${gd.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
       <c:if test="${ plate=='C'}">
          <c:forEach  items="${ gdCommissionless15}" var="gd"  begin="0" end="12" >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${gd.playFinalTypeName}</td>
              <td>${gd.displayCommissionC}</td>
              <td class="t_right">${gd.bettingQuotas}&nbsp;</td>
              <td class="t_right">${gd.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
       
      </tbody>
    </table></td>
    <td width="50%" align="left" valign="top"><table cellspacing="0" cellpadding="0" border="0" width="100%" class="king">
      <tbody>
        
       
        <tr>
          <th width="18%">交易類型</th>
          <th width="10%">${plate}
            盤</th>
          <th width="19%">單註限額</th>
          <th width="18%">單期限額</th>
        </tr>
        
        
        <c:if test="${ plate=='A'}">
          <c:forEach  items="${ gdCommissionless15}" var="gd"  begin="13"  >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${gd.playFinalTypeName}</td>
              <td>${gd.displayCommissionA}</td>
              <td class="t_right">${gd.bettingQuotas}&nbsp;</td>
              <td class="t_right">${gd.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
       <c:if test="${ plate=='B'}">
          <c:forEach  items="${ gdCommissionless15}" var="gd"  begin="13"  >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${gd.playFinalTypeName}</td>
              <td>${gd.displayCommissionB}</td>
              <td class="t_right">${gd.bettingQuotas}&nbsp;</td>
              <td class="t_right">${gd.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        <c:if test="${ plate=='C'}">
          <c:forEach  items="${ gdCommissionless15}" var="gd"  begin="13" >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${gd.playFinalTypeName}</td>
              <td>${gd.displayCommissionC}</td>
              <td class="t_right">${gd.bettingQuotas}&nbsp;</td>
              <td class="t_right">${gd.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
      </tbody>
    </table></td>
  </tr>
  <tr>
   <td width="100%" align="center" valign="top" colspan="2">
 <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy">
          <th class="type" >重慶時時彩</th>
 </table></td>
        </tr>
  <tr>
    <td width="50%" align="left" valign="top"><table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy">
      <tbody>
        
        <tr>
          <th width="18%">交易類型</th>
          <th width="10%">${plate}
            盤</th>
          <th width="19%">單註限額</th>
          <th width="18%">單期限額</th>
        </tr>
        
        <c:if test="${ plate=='A'}">
          <c:forEach  items="${ cqCommission1}" var="cq"  begin="0"  end="6">
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${cq.playFinalTypeName}</td>
              <td>${cq.displayCommissionA}</td>
              <td class="t_right">${cq.bettingQuotas}&nbsp;</td>
              <td class="t_right">${cq.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        <c:if test="${ plate=='B'}">
          <c:forEach  items="${ cqCommission1}" var="cq"   begin="0"  end="6">
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${cq.playFinalTypeName}</td>
              <td>${cq.displayCommissionB}</td>
              <td class="t_right">${cq.bettingQuotas}&nbsp;</td>
              <td class="t_right">${cq.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        <c:if test="${ plate=='C'}">
          <c:forEach  items="${ cqCommission1}" var="cq"   begin="0"  end="6">
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${cq.playFinalTypeName}</td>
              <td>${cq.displayCommissionC}</td>
              <td class="t_right">${cq.bettingQuotas}&nbsp;</td>
              <td class="t_right">${cq.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
      </tbody>
    </table></td>
    <td width="50%" align="left" valign="top"><table cellspacing="0" cellpadding="0" border="0" width="100%" class="king">
      <tbody>
        
       
        <tr>
          <th width="18%">交易類型</th>
          <th width="10%">${plate}
            盤</th>
          <th width="19%">單註限額</th>
          <th width="18%">單期限額</th>
        </tr>
       <c:if test="${ plate=='A'}">
          <c:forEach   items="${ cqCommission1}" var="cq"   begin="7"  >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${cq.playFinalTypeName}</td>
              <td>${cq.displayCommissionA}</td>
              <td class="t_right">${cq.bettingQuotas}&nbsp;</td>
              <td class="t_right">${cq.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        <c:if test="${ plate=='B'}">
          <c:forEach   items="${ cqCommission1}" var="cq"   begin="7"  >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${cq.playFinalTypeName}</td>
              <td>${cq.displayCommissionB}</td>
              <td class="t_right">${cq.bettingQuotas}&nbsp;</td>
              <td class="t_right">${cq.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        <c:if test="${ plate=='C'}">
          <c:forEach  items="${ cqCommission1}" var="cq"   begin="7"  >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${cq.playFinalTypeName}</td>
              <td>${cq.displayCommissionC}</td>
              <td class="t_right">${cq.bettingQuotas}&nbsp;</td>
              <td class="t_right">${cq.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
      </tbody>
    </table></td>
  </tr>
  
    <tr>
    <td width="100%" align="center" valign="top" colspan="2">
 <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy">
          <th class="type" >北京賽車(PK10)</th>
 </table></td>
        </tr>
  <tr>
    <td width="50%" align="left" valign="top"><table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy">
      <tbody>
        
        <tr>
          <th width="18%">交易類型</th>
          <th width="10%">${plate}
            盤</th>
          <th width="19%">單註限額</th>
          <th width="18%">單期限額</th>
        </tr>
        
        <c:if test="${ plate=='A'}">
          <c:forEach  items="${ bjCommission1}" var="bj"  begin="0"  end="7" >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${bj.playFinalTypeName}</td>
              <td>${bj.displayCommissionA}</td>
              <td class="t_right">${bj.bettingQuotas}&nbsp;</td>
              <td class="t_right">${bj.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        <c:if test="${ plate=='B'}">
          <c:forEach  items="${ bjCommission1}" var="bj"  begin="0"  end="7" >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${bj.playFinalTypeName}</td>
              <td>${bj.displayCommissionB}</td>
              <td class="t_right">${bj.bettingQuotas}&nbsp;</td>
              <td class="t_right">${bj.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        <c:if test="${ plate=='C'}">
          <c:forEach  items="${ bjCommission1}" var="bj"  begin="0"  end="7" >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${bj.playFinalTypeName}</td>
              <td>${bj.displayCommissionC}</td>
              <td class="t_right">${bj.bettingQuotas}&nbsp;</td>
              <td class="t_right">${bj.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
       
      </tbody>
    </table></td>
    <td width="50%" align="left" valign="top"><table cellspacing="0" cellpadding="0" border="0" width="100%" class="king">
      <tbody>
        
       
        <tr>
          <th width="18%">交易類型</th>
          <th width="10%">${plate}
            盤</th>
          <th width="19%">單註限額</th>
          <th width="18%">單期限額</th>
        </tr>
        <c:if test="${ plate=='A'}">
          <c:forEach  items="${ bjCommission1}" var="bj"  begin="8">
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${bj.playFinalTypeName}</td>
              <td>${bj.displayCommissionA}</td>
              <td class="t_right">${bj.bettingQuotas}&nbsp;</td>
              <td class="t_right">${bj.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        <c:if test="${ plate=='B'}">
          <c:forEach  items="${ bjCommission1}" var="bj"  begin="8" >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${bj.playFinalTypeName}</td>
              <td>${bj.displayCommissionB}</td>
              <td class="t_right">${bj.bettingQuotas}&nbsp;</td>
              <td class="t_right">${bj.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        
        <c:if test="${ plate=='C'}">
          <c:forEach items="${ bjCommission1}" var="bj"  begin="8" >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${bj.playFinalTypeName}</td>
              <td>${bj.displayCommissionC}</td>
              <td class="t_right">${bj.bettingQuotas}&nbsp;</td>
              <td class="t_right">${bj.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
      </tbody>
    </table></td>
  </tr>
  
   <!-- 江苏骰宝开始 -->
    <tr>
    <td width="100%" align="center" valign="top" colspan="2">
	 <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy">
	          <th class="type" >江苏骰寶(快3)</th>
	 </table>
 	</td>
    </tr>
  <tr>
    <td width="50%" align="left" valign="top">
    <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy">
      <tbody>
        
        <tr>
          <th width="18%">交易類型</th>
          <th width="10%">${plate} 盤</th>
          <th width="19%">單註限額</th>
          <th width="18%">單期限額</th>
        </tr>
        
        <c:if test="${ plate=='A'}">
          <c:forEach  items="${ jsCommission1}" var="js"  begin="0"  end="3" >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${js.playFinalTypeName}</td>
              <td>${js.displayCommissionA}</td>
              <td class="t_right">${js.bettingQuotas}&nbsp;</td>
              <td class="t_right">${js.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
       	<c:if test="${ plate=='B'}">
          <c:forEach  items="${ jsCommission1}" var="js"  begin="0"  end="3">
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${js.playFinalTypeName}</td>
              <td>${js.displayCommissionB}</td>
              <td class="t_right">${js.bettingQuotas}&nbsp;</td>
              <td class="t_right">${js.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        <c:if test="${ plate=='C'}">
          <c:forEach  items="${ jsCommission1}" var="js"  begin="0"  end="3">
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${js.playFinalTypeName}</td>
              <td>${js.displayCommissionC}</td>
              <td class="t_right">${js.bettingQuotas}&nbsp;</td>
              <td class="t_right">${js.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
       
      </tbody>
    </table></td>
    <td width="50%" align="left" valign="top">
    <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy">
      <tbody>
        
        <tr>
          <th width="18%">交易類型</th>
          <th width="10%">${plate} 盤</th>
          <th width="19%">單註限額</th>
          <th width="18%">單期限額</th>
        </tr>
        <c:if test="${ plate=='A'}">
          <c:forEach   items="${ jsCommission1}" var="js"  begin="4">
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${js.playFinalTypeName}</td>
              <td>${js.displayCommissionA}</td>
              <td class="t_right">${js.bettingQuotas}&nbsp;</td>
              <td class="t_right">${js.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
       	<c:if test="${ plate=='B'}">
          <c:forEach items="${ jsCommission1}" var="js"  begin="4">
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${js.playFinalTypeName}</td>
              <td>${js.displayCommissionB}</td>
              <td class="t_right">${js.bettingQuotas}&nbsp;</td>
              <td class="t_right">${js.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        <c:if test="${ plate=='C'}">
          <c:forEach  items="${ jsCommission1}" var="js"  begin="4">
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${js.playFinalTypeName}</td>
              <td>${js.displayCommissionC}</td>
              <td class="t_right">${js.bettingQuotas}&nbsp;</td>
              <td class="t_right">${js.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
      </tbody>
    </table></td>
  </tr>
  
   <tr>
 <td width="100%" align="left" valign="top" colspan="2">
 <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy">
          <th class="type" >幸运农场</th>
 </table></td>
        </tr>
  <tr>
    <td width="50%" align="left" valign="top"><table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy">
      <tbody>
        
        <tr>
          <th width="18%">交易類型</th>
          <th width="10%">${plate}
            盤</th>
          <th width="19%">單註限額</th>
          <th width="18%">單期限額</th>
        </tr>
        
        <c:if test="${ plate=='A'}">
          <c:forEach  items="${ ncCommission1}" var="nc"  begin="0" end="12" >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${nc.playFinalTypeName}</td>
              <td>${nc.displayCommissionA}</td>
              <td class="t_right">${nc.bettingQuotas}&nbsp;</td>
              <td class="t_right">${nc.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        <c:if test="${ plate=='B'}">
          <c:forEach  items="${ ncCommission1}" var="nc"  begin="0" end="12" >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${nc.playFinalTypeName}</td>
              <td>${nc.displayCommissionB}</td>
              <td class="t_right">${nc.bettingQuotas}&nbsp;</td>
              <td class="t_right">${nc.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        <c:if test="${ plate=='C'}">
          <c:forEach  items="${ ncCommission1}" var="nc"  begin="0" end="12" >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${nc.playFinalTypeName}</td>
              <td>${nc.displayCommissionC}</td>
              <td class="t_right">${nc.bettingQuotas}&nbsp;</td>
              <td class="t_right">${nc.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        
       
      </tbody>
    </table></td>
    <td width="50%" align="left" valign="top"><table cellspacing="0" cellpadding="0" border="0" width="100%" class="king">
      <tbody>
        
       
        <tr>
          <th width="18%">交易類型</th>
          <th width="10%">${plate}
            盤</th>
          <th width="19%">單註限額</th>
          <th width="18%">單期限額</th>
        </tr>
        <c:if test="${ plate=='A'}">
          <c:forEach  items="${ ncCommission1}" var="nc"  begin="13">
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${nc.playFinalTypeName}</td>
              <td>${nc.displayCommissionA}</td>
              <td class="t_right">${nc.bettingQuotas}&nbsp;</td>
              <td class="t_right">${nc.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        <c:if test="${ plate=='B'}">
          <c:forEach items="${ ncCommission1}" var="nc"  begin="13">
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${nc.playFinalTypeName}</td>
              <td>${nc.displayCommissionB}</td>
              <td class="t_right">${nc.bettingQuotas}&nbsp;</td>
              <td class="t_right">${nc.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        <c:if test="${ plate=='C'}">
          <c:forEach items="${ ncCommission1}" var="nc"  begin="13" >
            <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
              <td class="even">${nc.playFinalTypeName}</td>
              <td>${nc.displayCommissionC}</td>
              <td class="t_right">${nc.bettingQuotas}&nbsp;</td>
              <td class="t_right">${nc.itemQuotas}&nbsp;</td>
            </tr>
          </c:forEach>
        </c:if>
        
      </tbody>
    </table></td>
  </tr>
</table>
</div>
</td></tr></tbody></table>

</body>
<script language="javascript" src="../js/Forbid.js" type="text/javascript"></script>
</html>
