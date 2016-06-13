<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld"%><!-- 分页标签 -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerUser"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerStaff"%>
<%@ page import="com.npc.lottery.common.Constant"%>
<% ManagerUser userInfo = (ManagerUser) session.getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION); %>
<head>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin/index.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<div class="content">
	<s:form name="sForm" action="">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="30"
					background="${pageContext.request.contextPath}/images/admin/tab_05.gif"><table
						width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12" height="30"><img
								src="${pageContext.request.contextPath}/images/admin/tab_03.gif"
								width="12" height="30" /></td>
							<td><table width="100%" border="0" cellspacing="0"
									cellpadding="0">
									<tr>
										<td width="30%" valign="middle">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="1%"><img
														src="${pageContext.request.contextPath}/images/admin/tb.gif"
														width="16" height="16" /></td>
													<td width="99%" class="F_bold">&nbsp;站內消息</td>
												</tr>
											</table>
										</td>
										<td align="right" width="70%"></td>
									</tr>
								</table></td>
							<td width="16"><img
								src="${pageContext.request.contextPath}/images/admin/tab_07.gif"
								width="16" height="30" /></td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="8" background="${pageContext.request.contextPath}/images/admin/tab_12.gif">&nbsp;</td>
							<td align="center">
								<!-- 開始  -->
								<table width="100%" border="0" cellspacing="1" cellpadding="0" class="king mt4">
									<tr>
										<td class="t_list_caption" style="width:7%">貼出時間</td>
										<td class="t_list_caption" style="width:93%">消息詳情</td>
									</tr>

									<tr class="t_list_tr_0" onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''">
										<td class="F_bold">公司規則</td>
										<td align="center">
											<table border="0" width="800" cellspacing="0" cellpadding="0" style="margin:0 auto;">
												<tr>
													 <td align="left" class="F_bold" style="word-wrap:break-word;white-Space:AD_Info;text-align:left;border:medium none;"><AD_Info><br><br>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;當您加入本公司成為管理層時，您必須清楚了解及遵從本公司的所有條例。您在本公司網站開出的第一個下線時，就代表您已同意及接受所有本公司的<a
													href="javascript:void(0)" onClick="document.getElementById('Rele_Div').style.display='block';"
													 class="red">《規則及條例》</a>。<br><br>
													 <div id="Rele_Div" style="display: none;position:absolute; background-color: #ffffa2">
													 1、使用本公司網站的各股東和代理商，請留意閣下所在的國家或居住地的相關法律規定，如有疑問應就相關問題，尋求當地法律意見。<br><br>

													 2、若發生遭駭客入侵破壞行為或不可抗拒之災害導致網站故障或資料損壞、資料丟失等情況，我們將以本公司之後備資料為最後處理依<br>
													 據。<br><br>
													
													 3、開獎統計等資料只供參考，并非是對客戶操作的指引，本公司也不接受關於統計數據產生錯誤而引起的相關投訴。<br><br>
													
													 4、國際網路的連接速度並非本公司所能控制，本公司也不接受關於網路引起的相關投訴。<br><br>
													
													 5、由於係統服務涉及高端的技術要求及外圍所不能控制的因素限制，因此係統的稳定性，連續性會有時受到影響，本公司也不承担由此而<br>
													 產生的損失。<br><br>
													
													 6、各股東和代理商必須留意下線的信用額度，在某種特殊情況下，下线之信用額可能會出現透支。<br><br>
													
													 7、本公司擁有一切判決及註消任何涉嫌以非正常方式下註註單之權利，在進行調查期間將停止發放與其有關之任何彩金。<br><br>
													
													 8、客戶有責任確保自己的帳戶及密碼的安全，如果客戶懷疑自己的資料被盜用，應立即通知本公司，並須更改其個人詳細資料。所有被盜<br>用帳號之損失將由客戶自行負責。<br><br>
													
													 9、本公司不接受任何人以任何理由要求註銷會員下註的註单，而不論該註單是否已有開獎結果，除非该註單是由于係統出现错误或人为操<br>
													 作造成出現赔率错误的註單，而“赔率错误”僅定义於：(1)無論出現任何開獎結果，會員進行單項目下注的註單结果都無法獲利，或 (2)<br>
													 無論出現任何開獎結果，會員在同一時間如果進行多項目下註的總结果都能獲利。<br><br>
													
													 10，本規則及條例的解释權及修改權歸本公司所有。<a href="#" style="float:right;margin-right:110px;"  onClick="document.getElementById('Rele_Div').style.display='none';" class="red">关闭</a><br><br>
															

　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　“<%=userInfo.getShopsInfo().getShopsName() %>" 敬啟
													</div><br>
													 </AD_Info></td>
												</tr>
											</table>
									  </td>
									</tr>

									<!-- 内容start -->
									<%
										int count = 2;
									%>
									<s:iterator value="#request.page.result" status="branch">
										<tr class="t_list_tr_0" onMouseOver="this.style.backgroundColor='#FFFFA2'" onMouseOut="this.style.backgroundColor=''">
											<td><s:date name="createDate" format="yy-MM-dd" /><br>
											<s:date name="createDate" format="HH:mm:ss" /></td>
											<td><div style="width:800px;margin:0 auto;text-align:left;"><AD_Info><s:property value="contentInfo" escape="false" /></AD_Info></div></td>
										</tr>
									</s:iterator>
									<!-- 内容end -->

								</table> <!-- 結束  -->
							</td>
							<td width="8"
								background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td height="35"
					background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table
						width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
						      <tr>
						        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
						        <td align="center">
						         <lottery:paginate
													url="${pageContext.request.contextPath}/user/queryAllMessage.action"
													param=""  recordType=" 條公告"/>
						        
						        </td>
							   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
						      </tr>
						    </table></td>
						  </tr>
					</table></td>
			</tr>
		</table>
	</s:form>
</div>
</body>
<script language="javascript" src="../js/Forbid.js" type="text/javascript"></script>
</html>
