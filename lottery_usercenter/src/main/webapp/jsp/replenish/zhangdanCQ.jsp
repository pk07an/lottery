<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerStaff,com.npc.lottery.sysmge.entity.ManagerUser"%>
<%@ page import="com.npc.lottery.common.Constant,java.text.SimpleDateFormat"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/zhangdan.js"></script>

<div class="content">
	<s:form name="sForm" action="#">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
										<a class="btn_4" href="javascript:saveDoc(2);">保存帳單</a>
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
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="king mt4">
  
				<tbody>
					<tr>
						<th width="20%">類型</th>
						<th width="10%">筆</th>
						<th width="20%">實佔金額</th>
						<th width="20%">實占輸贏</th>
						<th width="20%">退水</th>
						<th width="10%">退水后結果</th>
					</tr>
					<s:if test="#request.cqList1.size()>0">
                    <tr><td colspan="6">第一球</td></tr>
                    <s:iterator value="#request.cqList1" status="branch">
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
					<s:if test="#request.cqList2.size()>0">
                    <tr><td colspan="6">第二球</td></tr>
                    <s:iterator value="#request.cqList2" status="branch">
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
					<s:if test="#request.cqList3.size()>0">
                    <tr><td colspan="6">第三球</td></tr>
                    <s:iterator value="#request.cqList3" status="branch">
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
					<s:if test="#request.cqList4.size()>0">
                    <tr><td colspan="6">第四球</td></tr>
                    <s:iterator value="#request.cqList4" status="branch">
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
					<s:if test="#request.cqList5.size()>0">
                    <tr><td colspan="6">第五球</td></tr>
                    <s:iterator value="#request.cqList5" status="branch">
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
					<s:if test="#request.cq1To5DXList.size()>0">
                    <tr><td colspan="6">第1-5大小</td></tr>
                    <s:iterator value="#request.cq1To5DXList" status="branch">
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
					<s:if test="#request.cq1To5DSList.size()>0">
                    <tr><td colspan="6">第1-5單雙</td></tr>
                    <s:iterator value="#request.cq1To5DSList" status="branch">
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
					<s:if test="#request.cqZHDXList.size()>0">
                    <tr><td colspan="6">總和大小</td></tr>
                    <s:iterator value="#request.cqZHDXList" status="branch">
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
					<s:if test="#request.cqZHDSList.size()>0">
                    <tr><td colspan="6">總和單雙</td></tr>
                    <s:iterator value="#request.cqZHDSList" status="branch">
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
					<s:if test="#request.cqLHList.size()>0">
                    <tr><td colspan="6">龍虎和</td></tr>
                    <s:iterator value="#request.cqLHList" status="branch">
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
					<s:if test="#request.cqFrontList.size()>0">
                    <tr><td colspan="6">前三</td></tr>
                    <s:iterator value="#request.cqFrontList" status="branch">
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
					<s:if test="#request.cqMidList.size()>0">
                    <tr><td colspan="6">中三</td></tr>
                    <s:iterator value="#request.cqMidList" status="branch">
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
					<s:if test="#request.cqLastList.size()>0">
                    <tr><td colspan="6">后三</td></tr>
                    <s:iterator value="#request.cqLastList" status="branch">
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
						<td width="20%"><b>合計</b></td>
						<td width="10%"><b><s:property value="#request.totalTurnover" escape="false"/></b></td>
						<td width="20%"><b><s:property value="#request.totalMoney" escape="false"/></b></td>
						<td width="20%"><b>0</b></td>
						<td width="20%"><b>0</b></td>
						<td width="10%"><b>0</b></td>
					</tr>
				</tbody>
			</table>
			<!--新表格开始-->
            <!--新表格结束-->
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
