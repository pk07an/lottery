<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<body>
<div class="content">
  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king xy">
  <tbody>
    <tr>
      <th colspan="3">信用資料</th>
    </tr>
    <tr bgcolor="#ffffff">
      <td width="22%" class="tr">本帳號</td>
      <td width="78%" valign="middle" class="tl" colspan="2" align="center"><s:property value="#session.manager_login_info_session.account"/></td>
    </tr>
  </tbody>
</table>
<table cellspacing="0" cellpadding="0" border="0" width="100%" class="king">
        <tbody>
          <tr>
            <td class="type" colspan="7">廣東快樂十分鐘</td>
          </tr>
          <tr>
          
            <th width="15%">交易類型</th>
            <th width="13%">A盤</th>
            <th width="12%">B盤</th>
            <th width="12%">C盤</th>
            
            <th width="19%">單註限額</th>
            <th width="18%">單期限額</th>
          </tr>
          
          
          	<s:iterator value="#request.gdChiefCommission" status="st">
        
        <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
            <td class="even">${playFinalTypeName}</td>
            <td>${commissionA}</td>
            <td>${commissionB}</td>
            <td>${commissionC}</td>
            
            <td>${bettingQuotas} </td>
            <td>${itemQuotas}</td>
          </tr>
        </s:iterator>
          
          
        
         <tr>
            <td class="type" colspan="7">重慶時時彩</td>
          </tr>
          
          
         
        
       
          
          
		  <tr>
            <th width="15%">交易類型</th>
            <th width="13%">A盤</th>
            <th width="12%">B盤</th>
            <th width="12%">C盤</th>
           
            <th width="19%">單註限額</th>
            <th width="18%">單期限額</th>
          </tr>
          
          
         <s:iterator value="#request.cqChiefCommission" status="st">
           <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
            <td class="even">${playFinalTypeName}</td>
            <td>${commissionA}</td>
            <td>${commissionB}</td>
            <td>${commissionC}</td>
           
            <td>${bettingQuotas} </td>
            <td>${itemQuotas}</td>
          </tr>
        </s:iterator>
        
        
         <tr>
            <td class="type" colspan="7">香港六合彩</td>
          </tr>
          
          
         
        
       
          
          
		  <tr>
            <th width="15%">交易類型</th>
            <th width="13%">A盤</th>
            <th width="12%">B盤</th>
            <th width="12%">C盤</th>
           
            <th width="19%">單註限額</th>
            <th width="18%">單期限額</th>
          </tr>
          
          
         <s:iterator value="#request.hkChiefCommission" status="st">
           <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
            <td class="even">${playFinalTypeName}</td>
            <td>${commissionA}</td>
            <td>${commissionB}</td>
            <td>${commissionC}</td>
           
            <td>${bettingQuotas} </td>
            <td>${itemQuotas}</td>
          </tr>
        </s:iterator>
        
        
          
        
        </tbody>
      </table>
  </div>


</body>

</html>
