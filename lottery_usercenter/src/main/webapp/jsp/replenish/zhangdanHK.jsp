<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="content">
<s:form action="#" id="rForm" >
  <div class="t_l"></div>
  <div class="t_r"></div>
  <div class="b_l"></div>
  <div class="b_r"></div>
	 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king">
	    <tr>
	      <th>帳單列表 &nbsp;&nbsp;&nbsp;&nbsp; <s:property value="periodsNum"/></th>
	    </tr>
	    <tr>
	    </tr>
	  </table>
 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="king">
        <colgroup>
        <col width="10%"></col>
        <col width="40%"></col>
        <col width="40%"></col>
        <col width="10%"></col>
        </colgroup>
         <tr>
          <th>編號</th>
          <th>項目</th>
          <th>操作說明</th>
          <th>...</th>
        </tr>
        <tr>
          <td>1</td>
          <td>特碼</td>
          <td>封盤后(搖獎前)備份</td>
           <td>
       		   <s:url action="queryZhangdan.action" id="viewUrl">
				 	<s:param name="subType" value="'HKLHC_ZHANGDAN'"/>
				 	<s:param name="playSubType" value="'HK_TA'"/>
				 	<s:param name="lotteryType" value="'HKLHC'"/>
			   </s:url> 
			   <s:a href="%{viewUrl}" cssClass="alink">打開</s:a>
          </td>
        </tr>
        <tr>
          <td>2</td>
          <td>正碼</td>
          <td>封盤后(搖獎前)備份</td>
          <td>
       		   <s:url action="queryZhangdan.action" id="viewUrl">
				 	<s:param name="subType" value="'HKLHC_ZHANGDAN'"/>
				 	<s:param name="playSubType" value="'HK_ZM'"/>
				 	<s:param name="lotteryType" value="'HKLHC'"/>
			   </s:url> 
			   <s:a href="%{viewUrl}" cssClass="alink">打開</s:a>
          </td>
        </tr>
        <tr>
          <td>3</td>
          <td>正特碼</td>
          <td>封盤后(搖獎前)備份</td>
          <td>
       		   <s:url action="queryZhangdan.action" id="viewUrl">
				 	<s:param name="subType" value="'HKLHC_ZHANGDAN'"/>
				 	<s:param name="playSubType" value="'HK_ZT'"/>
				 	<s:param name="lotteryType" value="'HKLHC'"/>
			   </s:url> 
			   <s:a href="%{viewUrl}" cssClass="alink">打開</s:a>
          </td>
        </tr>
        <tr>
          <td>4</td>
          <td>正碼1-6</td>
          <td>封盤后(搖獎前)備份</td>
          <td>
       		   <s:url action="queryZhangdan.action" id="viewUrl">
				 	<s:param name="subType" value="'HKLHC_ZHANGDAN'"/>
				 	<s:param name="playSubType" value="'HK_ZM1TO6'"/>
				 	<s:param name="lotteryType" value="'HKLHC'"/>
			   </s:url> 
			   <s:a href="%{viewUrl}" cssClass="alink">打開</s:a>
          </td>
        </tr>
        <tr>
          <td>5</td>
          <td>連碼</td>
          <td>封盤后(搖獎前)備份</td>
          <td>
       		   <s:url action="queryZhangdan.action" id="viewUrl">
				 	<s:param name="subType" value="'HKLHC_ZHANGDAN'"/>
				 	<s:param name="playSubType" value="'HK_LM'"/>
				 	<s:param name="lotteryType" value="'HKLHC'"/>
			   </s:url> 
			   <s:a href="%{viewUrl}" cssClass="alink">打開</s:a>
          </td>
        </tr>
        <tr>
          <td>6</td>
          <td>特碼生肖</td>
          <td>封盤后(搖獎前)備份</td>
          <td>
       		   <s:url action="queryZhangdan.action" id="viewUrl">
				 	<s:param name="subType" value="'HKLHC_ZHANGDAN'"/>
				 	<s:param name="playSubType" value="'HK_TM_SX'"/>
				 	<s:param name="lotteryType" value="'HKLHC'"/>
			   </s:url> 
			   <s:a href="%{viewUrl}" cssClass="alink">打開</s:a>
          </td>
        </tr>
        <tr>
          <td>7</td>
          <td>生肖尾數</td>
          <td>封盤后(搖獎前)備份</td>
          <td>
       		   <s:url action="queryZhangdan.action" id="viewUrl">
				 	<s:param name="subType" value="'HKLHC_ZHANGDAN'"/>
				 	<s:param name="playSubType" value="'HK_SXWS_WS'"/>
				 	<s:param name="lotteryType" value="'HKLHC'"/>
			   </s:url> 
			   <s:a href="%{viewUrl}" cssClass="alink">打開</s:a>
          </td>
        </tr>
        <tr>
          <td>8</td>
          <td>半波</td>
          <td>封盤后(搖獎前)備份</td>
          <td>
       		   <s:url action="queryZhangdan.action" id="viewUrl">
				 	<s:param name="subType" value="'HKLHC_ZHANGDAN'"/>
				 	<s:param name="playSubType" value="'HK_BB'"/>
				 	<s:param name="lotteryType" value="'HKLHC'"/>
			   </s:url> 
			   <s:a href="%{viewUrl}" cssClass="alink">打開</s:a>
          </td>
        </tr>
        <tr>
          <td>9</td>
          <td>六肖</td>
          <td>封盤后(搖獎前)備份</td>
          <td>
       		   <s:url action="queryZhangdan.action" id="viewUrl">
				 	<s:param name="subType" value="'HKLHC_ZHANGDAN'"/>
				 	<s:param name="playSubType" value="'HK_LX'"/>
				 	<s:param name="lotteryType" value="'HKLHC'"/>
			   </s:url> 
			   <s:a href="%{viewUrl}" cssClass="alink">打開</s:a>
          </td>
        </tr>
        <tr>
          <td>10</td>
          <td>生肖連</td>
          <td>封盤后(搖獎前)備份</td>
          <td>
       		   <s:url action="queryZhangdan.action" id="viewUrl">
				 	<s:param name="subType" value="'HKLHC_ZHANGDAN'"/>
				 	<s:param name="playSubType" value="'HK_SXL'"/>
				 	<s:param name="lotteryType" value="'HKLHC'"/>
			   </s:url> 
			   <s:a href="%{viewUrl}" cssClass="alink">打開</s:a>
          </td>
        </tr>
        <tr>
          <td>11</td>
          <td>尾數連</td>
          <td>封盤后(搖獎前)備份</td>
          <td>
       		   <s:url action="queryZhangdan.action" id="viewUrl">
				 	<s:param name="subType" value="'HKLHC_ZHANGDAN'"/>
				 	<s:param name="playSubType" value="'HK_WSL'"/>
				 	<s:param name="lotteryType" value="'HKLHC'"/>
			   </s:url> 
			   <s:a href="%{viewUrl}" cssClass="alink">打開</s:a>
          </td>
        </tr>
        <tr>
          <td>12</td>
          <td>五不中</td>
          <td>封盤后(搖獎前)備份</td>
          <td>
       		   <s:url action="queryZhangdan.action" id="viewUrl">
				 	<s:param name="subType" value="'HKLHC_ZHANGDAN'"/>
				 	<s:param name="playSubType" value="'HK_WBZ'"/>
				 	<s:param name="lotteryType" value="'HKLHC'"/>
			   </s:url> 
			   <s:a href="%{viewUrl}" cssClass="alink">打開</s:a>
          </td>
        </tr>
        <tr>
          <td>13</td>
          <td>過關</td>
          <td>封盤后(搖獎前)備份</td>
          <td>
       		   <s:url action="queryZhangdan.action" id="viewUrl">
				 	<s:param name="subType" value="'HKLHC_ZHANGDAN'"/>
				 	<s:param name="playSubType" value="'HK_GG'"/>
				 	<s:param name="lotteryType" value="'HKLHC'"/>
			   </s:url> 
			   <s:a href="%{viewUrl}" cssClass="alink">打開</s:a>
          </td>
        </tr>
        <tr>
          <th colspan="4"><font color='#00F'>帳單校對公式:(總投注額-公員贏項目總投注額)-總退水-和局無交收水錢-輸贏金額=實際輸贏結果</font></th>
        </tr>
      </table>
</s:form>
</div>