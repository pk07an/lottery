<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerStaff,com.npc.lottery.sysmge.entity.ManagerUser"%>
<%@ page import="com.npc.lottery.common.Constant,java.text.SimpleDateFormat"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/zhangdan.js"></script>

<script language="javascript">
/* function   saveDoc()  

{      
    var   strHTML   =   $("#petTable").html();   
    var   winSave   =   window.open(); 
    var now= new Date(); 
    var year=now.getYear(); 
    var month=now.getMonth()+1; 
    var day=now.getDate(); 
    var displayTime=$("#periodsNum").val()+"【1】";
    winSave.document.open("text/html","utf-8");   
    winSave.document.write(strHTML);   
    winSave.document.execCommand("SaveAs",true,displayTime+".htm");   
    winSave.close();   
} */

</script>


<div class="content">
	<s:form name="sForm" action="#">
	    <input type="hidden" id="periodsNum" value='<s:property value="#request.periodsNum" escape="false"/>'/>
	    <input type="hidden" id="emptyData" value="0"/>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" id="petTable">
			<!--控制表格头部开始-->
			  <td height="30" background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="12" height="30"><img src="${pageContext.request.contextPath}/images/admin/tab_03.gif" width="12" height="30" /></td>
			        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td width="21" align="left"><img src="${pageContext.request.contextPath}/images/admin/tb.gif" width="16" height="16" /></td>
			            <td width="124" align="left" class="F_bold">所有投註滙總表</td>
						<td width="1035" align="center"><table border="0" cellspacing="0" cellpadding="0">
						
						    <tr>
			                <td width="56">&nbsp;</td>
			                <td width="102">&nbsp;</td>
			                <td width="60" align="right">&nbsp;</td>
			                <td width="120" align="right">&nbsp;</td>
			                <td width="139" align="left">&nbsp;</td>
			              </tr>
			            </table></td>
			            <td class="t_right"width="229"><img src="${pageContext.request.contextPath}/images/admin/save.gif" width="14" height="13" /> 
										<a class="btn_4" href="javascript:saveDoc(1);">保存帳單</a>
			                <img src="${pageContext.request.contextPath}/images/admin/print.gif" width="14" height="13" /> 
										<a class="btn_4" href="javascript:window.print();">打印帳單</a>
			            </td>
			            </tr></table></td>
			        <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_07.gif" width="16" height="30" /></td>
			      </tr>
			    </table></td>
			<!--控制表格头部结束-->
			 <tr>
			    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
			        <td align="center" valign="top">
					<!-- 表格内容开始 一行六列-->
					<!-- 表格内容结束 一行六列-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="king mt4" >
                
				<tbody>
					<tr>
						<th width="20%">類型</th>
						<th width="10%">筆</th>
						<th width="20%">實佔金額</th>
						<th width="20%">實占輸贏</th>
						<th width="20%">退水</th>
						<th width="10%">退水后結果</th>
					</tr>
					<s:if test="#request.gdList1.size()>0">
					    
	                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>第一球</b></td></tr>
	                    <s:iterator value="#request.gdList1" status="branch">
							<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
								<td><s:property value="value.playTypeName" escape="false" /></td>
								<td><s:property value="value.turnover" escape="false" /></td>
								<td><s:property value="value.totalMoney" escape="false" /></td>
								<td>0</td>
								<td>0</td>
								<td>0</td>
						   </tr>
						</s:iterator>
					</s:if>
					<s:if test="#request.gdList2.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>第二球</b></td></tr>
                    <s:iterator value="#request.gdList2" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdList3.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>第三球</b></td></tr>
                    <s:iterator value="#request.gdList3" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdList4.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>第四球</b></td></tr>
                    <s:iterator value="#request.gdList4" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdList5.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>第五球</b></td></tr>
                    <s:iterator value="#request.gdList5" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdList6.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>第六球</b></td></tr>
                    <s:iterator value="#request.gdList6" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdList7.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>第七球</b></td></tr>
                    <s:iterator value="#request.gdList7" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdList8.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>第八球</b></td></tr>
                    <s:iterator value="#request.gdList8" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gd1To8DXList.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>第1-8大小</b></td></tr>
                    <s:iterator value="#request.gd1To8DXList" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gd1To8DSList.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>第1-8單雙</b></td></tr>
                    <s:iterator value="#request.gd1To8DSList" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gd1To8WSDXList.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>第1-8尾數大小</b></td></tr>
                    <s:iterator value="#request.gd1To8WSDXList" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gd1To8HSDSList.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>第1-8合數單雙</b></td></tr>
                    <s:iterator value="#request.gd1To8HSDSList" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gd1To8FWList.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>第1-8方位</b></td></tr>
                    <s:iterator value="#request.gd1To8FWList" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gd1To8ZFBList.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>第1-8中發白</b></td></tr>
                    <s:iterator value="#request.gd1To8ZFBList" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdZHDXList.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>總和大小</b></td></tr>
                    <s:iterator value="#request.gdZHDXList" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdZHDSList.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>總和單雙</b></td></tr>
                    <s:iterator value="#request.gdZHDSList" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdZHWSDXList.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>總和尾數大小</b></td></tr>
                    <s:iterator value="#request.gdZHWSDXList" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdLHList.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>龍虎</b></td></tr>
                    <s:iterator value="#request.gdLHList" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdLMList.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="6"><b>連碼</b></td></tr>
                    <s:iterator value="#request.gdLMList" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="value.playTypeName" escape="false" /></td>
							<td><s:property value="value.turnover" escape="false" /></td>
							<td><s:property value="value.totalMoney" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
                    <tr bgcolor="#EBF4FF">
						<td width="20%"><b>闔計</b></td>
						<td width="10%"><b><s:property value="#request.totalTurnover" escape="false"/></b></td>
						<td width="20%"><b><s:property value="#request.totalMoney" escape="false"/></b></td>
						<td width="20%"><b>0</b></td>
						<td width="20%"><b>0</b></td>
						<td width="10%"><b>0</b></td>
					</tr>
					
				</tbody>
			</table>
			
			<!--连码表格开始-->
			<s:if test="#request.lm=='true'">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="king mt4">
  
				<tbody>
					<tr>
						<th width="15%"><b>注單號碼/時間</b></th>
						<th width="10%"><b>會員/盤</b></th>
						<th width="20%"><b>下注明細</b></th>
						<th width="15%"><b>實占金額</b></th>
						<th width="20%"><b>實占輸贏</b></th>
						<th width="10%"><b>退水</b></th>
						<th width="10%"><b>退水后結果</b></th>
					</tr>
					<s:if test="#request.gdLMListRX2.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="7"><b>任選二</b></td></tr>
                    <s:iterator value="#request.gdLMListRX2" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="orderNo" escape="false" />#
			                  <span class="blue"><strong><s:property value="whoReplenish" escape="false" /></strong></span><br />
			                  <s:date name="bettingDate" format="MM-dd HH:mm:ss E"/>
			                </td>
							<td><s:property value="userName" escape="false" /><br />
		                        <s:property value="plate" escape="false" />盘</td>
							<td><s:property value="typeCodeNameOdd" escape="false" /></td>
							<td class="t_right"><s:property value="moneyDis" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdLMListR2LZ.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="7"><b>選二連組</b></td></tr>
                    <s:iterator value="#request.gdLMListR2LZ" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="orderNo" escape="false" />#
			                  <span class="blue"><strong><s:property value="whoReplenish" escape="false" /></strong></span><br />
			                  <s:date name="bettingDate" format="MM-dd HH:mm:ss E"/>
			                </td>
							<td><s:property value="userName" escape="false" /><br />
		                        <s:property value="plate" escape="false" />盘</td>
							<td><s:property value="typeCodeNameOdd" escape="false" /></td>
							<td class="t_right"><s:property value="moneyDis" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdLMListRX3.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="7"><b>任選三</b></td></tr>
                    <s:iterator value="#request.gdLMListRX3" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="orderNo" escape="false" />#
			                  <span class="blue"><strong><s:property value="whoReplenish" escape="false" /></strong></span><br />
			                  <s:date name="bettingDate" format="MM-dd HH:mm:ss E"/>
			                </td>
							<td><s:property value="userName" escape="false" /><br />
		                        <s:property value="plate" escape="false" />盘</td>
							<td><s:property value="typeCodeNameOdd" escape="false" /></td>
							<td class="t_right"><s:property value="moneyDis" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdLMListR3LZ.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="7"><b>選三前組</b></td></tr>
                    <s:iterator value="#request.gdLMListR3LZ" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="orderNo" escape="false" />#
			                  <span class="blue"><strong><s:property value="whoReplenish" escape="false" /></strong></span><br />
			                  <s:date name="bettingDate" format="MM-dd HH:mm:ss E"/>
			                </td>
							<td><s:property value="userName" escape="false" /><br />
		                        <s:property value="plate" escape="false" />盘</td>
							<td><s:property value="typeCodeNameOdd" escape="false" /></td>
							<td class="t_right"><s:property value="moneyDis" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdLMListRX4.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="7"><b>任選四</b></td></tr>
                    <s:iterator value="#request.gdLMListRX4" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="orderNo" escape="false" />#
			                  <span class="blue"><strong><s:property value="whoReplenish" escape="false" /></strong></span><br />
			                  <s:date name="bettingDate" format="MM-dd HH:mm:ss E"/>
			                </td>
							<td><s:property value="userName" escape="false" /><br />
		                        <s:property value="plate" escape="false" />盘</td>
							<td><s:property value="typeCodeNameOdd" escape="false" /></td>
							<td class="t_right"><s:property value="moneyDis" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
					<s:if test="#request.gdLMListRX5.size()>0">
                    <tr bgcolor="#F6FAFF"><td colspan="7"><b>任選五</b></td></tr>
                    <s:iterator value="#request.gdLMListRX5" status="branch">
						<tr onMouseOver="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''">
							<td><s:property value="orderNo" escape="false" />#
			                  <span class="blue"><strong><s:property value="whoReplenish" escape="false" /></strong></span><br />
			                  <s:date name="bettingDate" format="MM-dd HH:mm:ss E"/>
			                </td>
							<td><s:property value="userName" escape="false" /><br />
		                        <s:property value="plate" escape="false" />盘</td>
							<td><s:property value="typeCodeNameOdd" escape="false" /></td>
							<td class="t_right"><s:property value="moneyDis" escape="false" /></td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					   </tr>
					</s:iterator>
					</s:if>
                    <tr bgcolor="#EBF4FF">
						<td colspan="3"><b>闔計&nbsp;<s:property value="#request.totalTunoverLM" escape="false"/>&nbsp;筆</b></td>
						<td><b><s:property value="#request.totalMoneyLM" escape="false"/></b></td>
						<td><b>0</b></td>
						<td><b>0</b></td>
						<td><b>0</b></td>
					</tr>
				</tbody>
			</table>
			</s:if>
            <!--连码表格结束-->
			
			</td>
	        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
	      </tr>
	    </table></td>
	  </tr>
	  
		<tr>
		    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
		        <%SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
                 String strDate=fmt.format(new Date());%>
		        <td align="center" class="red">報表生成時間：<%=strDate %></td>
			   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
		      </tr>
		    </table></td>
		  </tr>
<!--控制底部操作按钮结束-->
     </table>
		<!--中间内容结束-->
	</s:form>
</div>
</html>
