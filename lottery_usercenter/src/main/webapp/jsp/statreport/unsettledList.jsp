<!-- 
	交收报表（包括所有用户（不含总监）级别交收报表的第一个页面）
 -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/report.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">

<style>
	.alink{
   		color: #333333;
   		text-decoration:underline
   	}
</style>

<script type="text/javascript">
	
	/**
     *  明细信息列表（会员投注明细）
     */
    function openDetailedReportPop(infoLink){
        //alert(infoLink.href);
        //需要打开的页面URL，设置为实际值
        var url = infoLink.href;
        //打开模式窗口，固定写法，不要改变
//         var ret = openModalDialogReport(url,'',1006,800);
        var height=800;
        var width=1006
        //设置窗体居中
        var top = (window.screen.availHeight-30-height)/2; //获得窗口的垂直位置;
        var left = (window.screen.availWidth-10-width)/2; 
        var style="height="+height+", width="+width+", top="+top+",left="+left+", toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no";
        window.open(url,'',style);
        
        return false;
    }
	
    /**
     *  明细信息列表（非最终明细页面不弹出窗口）
     */
    function openDetailedReport(infoLink){
        //alert(infoLink.href);
        //需要打开的页面URL，设置为实际值
        var url = infoLink.href;
        //打开模式窗口，固定写法，不要改变
        //var ret = openModalDialogReport('${contextPath}/jsp/statreport/common/ModalDialogPage.jsp',url,1006,800);
        
        return true;
    }
    
    /**
     * 显示应收下线明细
     */
    function showDesc(id,title){
        
        $("#showDescDivTitle").html(title);
        $("#showDescDivWin").html("<div align='left'>&nbsp;&nbsp;輸贏：0.0</div>");
        $("#showDescDivBackWater").html("<div align='left'>&nbsp;&nbsp;退水：0.0</div>");
        
        var offset = getElementOffset($('#' +id ));
        var $div = setElementOffset($("#showDescDiv"), offset);
        $div.show();
    }
    
    /**
     * 隐藏应收下线明细
     */
    function hideDesc(info){
        var offset = getElementOffset($('#' +info ));
        var $div = setElementOffset($("#showDescDiv"), offset);
        $div.hide();
    }
    
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
    
</script>

<!-- 应收下线明细所对应的div -->
<div id="showDescDiv" style="background-color: #EFF1FF; display: none;">
    <input id="typeCode" type="hidden" value=""/>
    <table border="0" cellspacing="0" cellpadding="0" class="king" id="betTable"
        style="position: absolute; left: 23px; top: 100px; width: 200px; background: #fff; z-index: 9999;">
        <tr>
            <th><b>應收<span id="showDescDivTitle"></span>明細</b></th>
        </tr>
        <tr>
            <td id="showDescDivWin">&nbsp;</td>
        </tr>
        <tr>
            <td id="showDescDivBackWater">&nbsp;</td>
        </tr>
    </table>
</div>

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
			            <td width="224" align="left" class="F_bold"><s:property value="#request.reportInfo"/></td>
						<td width="935" align="center"><table border="0" cellspacing="0" cellpadding="0">
						<tr><td width="477" align="right" colspan="5"><s:property value="#request.searchData"/></td></tr>
			            </table></td>
			            <td class="t_right"width="229"><img src="${pageContext.request.contextPath}/images/report_ret.gif" width="14" height="13" /> 
										<a href="javascript:void history.go(-1)">返囬</a>
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
					    <th width="6%" nowrap><s:property value="#request.userTypeName"/></th>
                        <th width="10%" nowrap>名稱</th>
                        <th width="6%" nowrap>筆</th>
                        <th width="6%" nowrap><b>總下注額</b></th>
                        <th width="6%" nowrap>会员輸贏</th>
                        <th width="6%" nowrap>應收下線</th>
                        <th width="6%" nowrap>占成%</th>
                        <th width="6%" nowrap>實占注額</th>
                        <th width="6%" nowrap>占货比%</th>
                        <th width="6%" nowrap>實占輸贏</th>
                        <th width="6%" nowrap>實占退水</th>
                        <th width="6%" nowrap>實占结果</th>
                        <th width="6%" nowrap>賺取退水</th> 
                        <th width="6%" nowrap>賺水后结果</th> 
                        <th width="6%" nowrap>貢獻上級</th> 
                        <th width="6%" nowrap>應付上級</th> 
					</tr>

					<!-- 判断是否存在对应的数据 -->
		            <%-- <s:if test="null == #request.resultList || 0 == #request.resultList.size()">
		                <tr bgcolor='#FFFFFF' class=''>
		                    <td align="left" colspan='16'>
		                        <font color='#FF0000'>未查詢到满足条件的数据！</font>
		                    </td>
		                </tr>
		            </s:if> --%>
		            
					<!-- 迭代显示 List 集合中存储的数据记录 -->
					<s:iterator value="#request.resultList" status="st">
					<!-- 行间隔色显示和当前行反白显示，固定写法 -->
					<tr onmouseover="changeColorToYL(this)" onmouseout="changeColorToOld(this)" onclick="changeColor(this)">
						<td nowrap>
							<s:if test="userType == 6">
								<!-- 代理明细 -->
					            <s:url action="unsettledList.action" id="viewUrl">
								 	<s:param name="lotteryType" value="lotteryType"/>
								 	<s:param name="playType" value="playType"/>
								 	<s:param name="bettingDateStart" value="bettingDateStart"/>
								 	<s:param name="bettingDateEnd" value="bettingDateEnd"/>
								 	<s:param name="userType" value="userType"/><!-- 明细类型：代理 -->
								 	<s:param name="userID" value="userID"/><!-- 代理ID -->
								 	<s:param name="periodsNum" value="periodsNum"/>
								 	<s:param name="selectTypeRadio" value="selectTypeRadio"/><!-- 查询的方式 -->
								 	<s:param name="detailUserAccount" value="subordinate"/><!-- 代理账号 -->
								</s:url>  
								<s:a href="%{viewUrl}" cssClass="noneA_UnderLine blue" onclick="return openDetailedReport(this)" title="查看代理用户明细">
						  			<s:property value="subordinate"/>
								</s:a>
					        </s:if>
					        <s:elseif test="userType == 5">
								<!--  總代理明细 -->
					            <s:url action="unsettledList.action" id="viewUrl">
								 	<s:param name="lotteryType" value="lotteryType"/>
								 	<s:param name="playType" value="playType"/>
								 	<s:param name="bettingDateStart" value="bettingDateStart"/>
								 	<s:param name="bettingDateEnd" value="bettingDateEnd"/>
								 	<s:param name="userType" value="userType"/><!-- 明细类型：總代理 -->
								 	<s:param name="userID" value="userID"/><!-- 總代理ID -->
								 	<s:param name="periodsNum" value="periodsNum"/>
								 	<s:param name="selectTypeRadio" value="selectTypeRadio"/><!-- 查询的方式 -->
								 	<s:param name="detailUserAccount" value="subordinate"/><!-- 總代理账号 -->
								</s:url>  
								<s:a href="%{viewUrl}" cssClass="noneA_UnderLine blue" onclick="return openDetailedReport(this)" title="查看總代理用户明细">
						  			<s:property value="subordinate"/>
								</s:a>
					        </s:elseif>
					        <s:elseif test="userType == 4">
								<!--  股东明细 -->
					            <s:url action="unsettledList.action" id="viewUrl">
								 	<s:param name="lotteryType" value="lotteryType"/>
								 	<s:param name="playType" value="playType"/>
								 	<s:param name="bettingDateStart" value="bettingDateStart"/>
								 	<s:param name="bettingDateEnd" value="bettingDateEnd"/>
								 	<s:param name="userType" value="userType"/><!-- 明细类型：股东 -->
								 	<s:param name="userID" value="userID"/><!-- 股东ID -->
								 	<s:param name="periodsNum" value="periodsNum"/>
								 	<s:param name="selectTypeRadio" value="selectTypeRadio"/><!-- 查询的方式 -->
								 	<s:param name="detailUserAccount" value="subordinate"/><!-- 股东账号 -->
								</s:url>  
								<s:a href="%{viewUrl}" cssClass="noneA_UnderLine blue" onclick="return openDetailedReport(this)" title="查看股东用户明细">
						  			<s:property value="subordinate"/>
								</s:a>
					        </s:elseif>
					        <s:elseif test="userType == 3">
								<!--  分公司明细 -->
					            <s:url action="unsettledList.action" id="viewUrl">
								 	<s:param name="lotteryType" value="lotteryType"/>
								 	<s:param name="playType" value="playType"/>
								 	<s:param name="bettingDateStart" value="bettingDateStart"/>
								 	<s:param name="bettingDateEnd" value="bettingDateEnd"/>
								 	<s:param name="userType" value="userType"/><!-- 明细类型：分公司 -->
								 	<s:param name="userID" value="userID"/><!-- 分公司ID -->
								 	<s:param name="periodsNum" value="periodsNum"/>
								 	<s:param name="selectTypeRadio" value="selectTypeRadio"/><!-- 查询的方式 -->
								 	<s:param name="detailUserAccount" value="subordinate"/><!-- 分公司账号 -->
								</s:url>  
								<s:a href="%{viewUrl}" cssClass="noneA_UnderLine blue" onclick="return openDetailedReport(this)" title="查看分公司明细">
						  			<s:property value="subordinate"/>
								</s:a>
					        </s:elseif>
							<s:else>
								<s:property value="subordinate" />
							</s:else>
						</td>
						<td>
							<s:property value="userName" />
						</td>
						<td nowrap>
							<s:property value="turnover" escape="false"/>
						</td>
						<td nowrap class="t_right">
						   <s:if test="userType == 9">
								<!-- 查询普通会员、直属会员投注明细 -->
								<s:url action="unsettledDetailedList.action" id="viewUrl">
								 	<s:param name="lotteryType" value="lotteryType"/>
								 	<s:param name="playType" value="playType"/>
								 	<s:param name="userID" value="userID"/>
								 	<s:param name="bettingDateStart" value="bettingDateStart"/>
								 	<s:param name="bettingDateEnd" value="bettingDateEnd"/>
								 	<s:param name="userType" value="userType"/><!-- 明细类型：代理 -->
								 	<s:param name="detailUserAccount" value="subordinate"/><!-- 账号 -->
								 	<s:param name="periodsNum" value="periodsNum"/>
								 	<s:param name="selectTypeRadio" value="selectTypeRadio"/><!-- 查询的方式 -->
								 	<s:param name="parentUserType" value="parentUserType"/><!-- 上级用户类型 -->
								</s:url>  
								<s:a href="%{viewUrl}" cssClass="noneA_UnderLine blue" onclick="return openDetailedReportPop(this)" title="查看会员投注明细">
						  			<s:property value="amount"/><!-- 總下注額 -->
								</s:a>
							</s:if><s:else>
								<s:property value="amount" /> <!-- 總下注額 -->
							</s:else>
						</td>
						<td class="t_right">0.0</td>
						<td class="t_right getClass5" nowrap ><a href="javascript:function blankinfo(){return false;}" id='<s:property value="subordinate" escape="false"/>' 
                                 onmouseover="showDesc('<s:property value="subordinate" escape="false"/>','<s:property value="subordinate" escape="false"/>')" 
                                 onmouseout="hideDesc('<s:property value="subordinate" escape="false"/>')">0.0</a></td>
						<td  nowrap class="t_right">
							<s:property value="rateName" /><!-- 占成 -->
						</td>
						<td  nowrap class="t_right">
							<s:property value="rateValidAmountDis" /><!-- 实占金额 -->
						</td>
						<td  nowrap class="t_right">
							<s:property value="realResultPerDis" /><!-- 占货比 -->
						</td>
						<td class="t_right">0.0</td>
						<td class="t_right">0.0</td>
						<td class="t_right">0.0</td>
						<td class="t_right">0.0</td>
						<td class="t_right"><b>0.0</b></td>
                        <td nowrap class="t_right getClass6">
                            <s:property value="offerSuperiorDis" /><!-- 贡献上级 -->                             
                        </td>
                        <td class="t_right getClass6">0.0</td>
					</tr>
					</s:iterator>
					
					<!-- 合计数据 -->
		            <s:if test="null != #request.totalEntity">
		            	<s:iterator value ="#request.totalEntity">
		                <tr bgcolor='#F1F1F1'>
		                    <td align="right" colspan="2">
								<div align="right">合計：</div>
							</td>
							<td nowrap>
								<s:property value="turnover" escape="false"/>
							</td>
							<td  nowrap class="t_right">
								<s:property value="amount" />
							</td>
							<td nowrap class="t_right">0.0</td>
							<td bgcolor='#F1F1F1' nowrap class="t_right"><a href="javascript:function blankinfo(){return false;}" id="totalYinShou"
                                 onmouseover="showDesc('totalYinShou','<s:property value="#request.userTypeName"/>')" 
                                 onmouseout="hideDesc('totalYinShou')">0.0</a></td>
							<td nowrap class="t_right"><s:property value="rateName" /></td><!-- 占成 -->
							<td nowrap class="t_right"><s:property value="rateValidAmountDis" /></td><!-- 实占注额 -->
							<td class="t_right">100.00</td><!-- 占货比 -->
							<td class="t_right">0.0</td>
							<td class="t_right">0.0</td>
							<td class="t_right">0.0</td>
							<td class="t_right">0.0</td>
							<td class="t_right">0.0</td>
							<td nowrap class="t_right">
								<s:property value="offerSuperiorDis" escape="false"/><!-- 贡献上级 -->
							</td>
							<td class="t_right">0.0</td>
		                </tr>
		                </s:iterator>
		            </s:if>

				</tbody>
			</table></td>
	        <td width="8" background="${pageContext.request.contextPath}/images/admin/tab_15.gif">&nbsp;</td>
	      </tr>
	    </table></td>
	  </tr>
		<tr>
		    <td height="35" background="${pageContext.request.contextPath}/images/admin/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="12" height="35"><img src="${pageContext.request.contextPath}/images/admin/tab_18.gif" width="12" height="35" /></td>
		        <td align="center">
			        <strong>
			           	占成結果：<span class="red">0.0</span>&nbsp;&nbsp;/&nbsp;&nbsp;賺取退水：0.0&nbsp;&nbsp;/&nbsp;&nbsp;抵扣補貨及賺水后結果：<span class="blue">0.0</span>&nbsp;&nbsp;/&nbsp;&nbsp;應付上級：<span class="green">0.0</span>
			        </strong>
		        </td>
			   <td width="16"><img src="${pageContext.request.contextPath}/images/admin/tab_20.gif" width="16" height="35" /></td>
		      </tr>
		    </table></td>
		  </tr>
<!--控制底部操作按钮结束-->
     </table>
		<!--中间内容结束-->
			<!-- 补货信息 -->
			<s:if test="null != #request.replenishEntity">
			<br/>
			<table width="70%" border="0" cellpadding="0" cellspacing="0" style="align:center;margin:0px auto;">
			<tr>
			<td width="49.5%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="king mt4">
				<tbody>
					<tr>
						<th width="15%">筆</th>
						<th width="17%">補貨金額</th>
						<th width="17%">補貨輸赢</th>
						<th width="17%">退水</th>
						<th width="17%">退水后結果</th>
					</tr>
					<!-- 迭代显示 List 集合中存储的数据记录 -->
					<s:iterator value="#request.replenishEntity" status="st">
					<tr onmouseover="this.style.backgroundColor='#FFFFA2'" onmouseout="this.style.backgroundColor=''" onclick="changeColor(this)">
						<td><s:property value="turnover"/></td>
						<td nowrap class="t_right">
							<s:url action="unsettledRelenishDetailed.action" id="replenishViewUrl">
							 	<s:param name="userID" value="userID"/>
							 	<s:param name="lotteryType" value="lotteryType"/>
								<s:param name="playType" value="playType"/>
								<s:param name="bettingDateStart" value="bettingDateStart"/>
								<s:param name="bettingDateEnd" value="bettingDateEnd"/>
								<s:param name="periodsNum" value="periodsNum"/>
								<s:param name="selectTypeRadio" value="selectTypeRadio"/><!-- 查询的方式 -->
								<s:param name="userType" value="userType"/><!-- 明细用户类型 -->
							</s:url>  
							<s:a href="%{replenishViewUrl}" cssClass="alink" onclick="return openDetailedReportPop(this)"  title="查看補貨數據明细">
					  			<s:property value="amount"/> 
							</s:a>
						</td>
						<td nowrap class="t_right">0.0</td>
						<td nowrap class="t_right">0.0</td>
						<td nowrap class="t_right">0.0</td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
			</td>
			<td width="1%"></td>
            </tr>
            </table>
            </s:if>
    </s:form>
    </div>
    
    <script type="text/javascript">

$(document).ready(function() {	
	$(".getClass5").addClass("even5");
	$(".getClass6").addClass("even6");
	$(".getClass6").addClass("even6");
});
</script>
</html>