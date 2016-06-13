<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- 
	交收报表明细（所有级别用户的下一级（非最终会员）明细信息页面）
 -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/index.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
	.alink{
   		color: #333333;
   		text-decoration:underline
   	}
</style>

<script type="text/javascript">

	/**
	 *	投注详细信息列表（会员投注明细）
	 */
	function openDetailedReportPop(infoLink){
		//alert(infoLink.href);
		//需要打开的页面URL，设置为实际值
		var url = infoLink.href;
		//打开模式窗口，固定写法，不要改变
// 		var ret = openModalDialogReport(url,'',1006,800);
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
     *  投注详细信息列表（非最终明细页面不弹出窗口）
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
    function showDesc(info){
        
        var infos = info.split("||");
        
        $("#showDescDivTitle").html(infos[1]);
        $("#showDescDivWin").html("<div align='left'>&nbsp;&nbsp;輸贏：" + infos[2] + "</div>");
        $("#showDescDivBackWater").html("<div align='left'>&nbsp;&nbsp;退水：" + infos[3] + "</div>");
        
        var offset = getElementOffset($('#' +infos[0] ));
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
</head>

<body style="padding-left: 5px;padding-right: 5px">

<!-- 应收下线明细所对应的div -->
<div id="showDescDiv" style="background-color: #EFF1FF; display: none;">
    <input id="typeCode" type="hidden" value=""/>
    <table border="0" cellspacing="0" cellpadding="0" class="king" id="betTable"
        style="position: absolute; left: 23px; top: 100px; width: 200px; background: #fff; z-index: 9999;">
        <tr>
            <th>應收&nbsp;<strong id="showDescDivTitle">&nbsp;</strong>&nbsp;明細</th>
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
		<div class="main">
			
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
                class="king mt4">
                <!-- 标题栏区域 -->    
                <tr>
                    <th width="233">
                        <img src="${contextPath}/images/report_iron.gif" border="0" align="middle"/>
                        <s:property value="#request.reportInfo"/>
                    </th>
                    <th>
                        <s:property value="#request.searchData"/>
                    </th>
                    <th width="233">
                        <img src="${contextPath}/images/report_ret.gif" border="0" align="middle"/>
                        <a href="javascript:void history.go(-1)">返囬</a>
                    </th>
                </tr>
                
                <!-- 正文区域 Begin -->    
                <tr>
                <td colspan="3">
                <table width="99.5%" border="0" cellpadding="0" cellspacing="0"
                class="king mt42">
                <tbody>
					<tr>
						<th width="6%" nowrap>帳號</th>
                        <th width="10%" nowrap>名稱</th>
                        <th width="6%" nowrap>筆</th>
                        <th width="6%" nowrap>有效金額</th>
                        <th width="6%" nowrap>会员輸贏</th>
                        <th width="6%" nowrap>应收下线</th>
                        <th width="6%" nowrap>占成%</th>
                        <th width="6%" nowrap>实占注额</th>
                        <th width="6%" nowrap>占货比%</th>
                        <th width="6%" nowrap>实占输赢</th>
                        <th width="6%" nowrap>实占退水</th>
                        <th width="6%" nowrap>实占结果</th>
                        <th width="6%" nowrap>赚取退水</th> 
                        <th width="6%" nowrap>赚水后结果</th> 
                        <th width="6%" nowrap>贡献上级</th> 
                        <th width="6%" nowrap>应付上级</th> 
					</tr>
					<!--  style="background-color:#f6f0da" -->

					<!-- 判断是否存在对应的数据 -->
		            <s:if test="null == #request.resultList || 0 == #request.resultList.size()">
		                <tr bgcolor='#FFFFFF' class=''>
		                    <td align="left" colspan='16'>
		                        <font color='#FF0000'>未查詢到满足条件的數據！</font>
		                    </td>
		                </tr>
		            </s:if>
		            
					<!-- 迭代显示 List 集合中存储的数据记录 -->
					<s:iterator value="#request.resultList" status="st">
					<!-- 行间隔色显示和当前行反白显示，固定写法 -->
					<tr <s:if test="true == #st.odd">class=''</s:if><s:else>class='even'</s:else> onmouseover='listSelect(this)' onmouseout='listUnSelect(this)' >
						<td nowrap>
							<s:if test="userType == 9">
								<!--  会员投注明细 -->
					            <s:url action="detailedList.action" id="viewUrl">
								 	<s:param name="lotteryType" value="lotteryType"/>
								 	<s:param name="playType" value="playType"/>
								 	<s:param name="bettingUserID" value="userID"/>
								 	<s:param name="bettingDateStart" value="bettingDateStart"/>
								 	<s:param name="bettingDateEnd" value="bettingDateEnd"/>
								 	<s:param name="detailUserAccount" value="subordinate"/><!-- 账号 -->
								 	<s:param name="parentUserType" value="parentUserType"/><!-- 上级用户类型 -->
								</s:url>  
								<s:a href="%{viewUrl}" cssClass="alink" onclick="return openDetailedReportPop(this)"  title="查看会员投注明细">
						  			<s:property value="subordinate"/>
								</s:a>
							</s:if>
							<s:elseif test="userType == 6">
								<!--  代理明细 -->
					            <s:url action="detailedReport.action" id="viewUrl">
								 	<s:param name="lotteryType" value="lotteryType"/>
								 	<s:param name="playType" value="playType"/>
								 	<s:param name="bettingDateStart" value="bettingDateStart"/>
								 	<s:param name="bettingDateEnd" value="bettingDateEnd"/>
								 	<s:param name="detailType" value="agent"/><!-- 明细类型 -->
								 	<s:param name="detailUserID" value="userID"/><!-- 代理ID -->
								 	<s:param name="detailUserType" value="userType"/><!-- 明细类型：代理 -->
								 	<s:param name="detailUserAccount" value="subordinate"/><!-- 账号 -->
								</s:url>  
								<s:a href="%{viewUrl}" cssClass="alink" onclick="return openDetailedReport(this)" title="查看代理用户明细">
						  			<s:property value="subordinate"/>
								</s:a>
					        </s:elseif>
					        <s:elseif test="userType == 5">
								<!--  總代理明细 -->
					            <s:url action="detailedReport.action" id="viewUrl">
								 	<s:param name="lotteryType" value="lotteryType"/>
								 	<s:param name="playType" value="playType"/>
								 	<s:param name="bettingDateStart" value="bettingDateStart"/>
								 	<s:param name="bettingDateEnd" value="bettingDateEnd"/>
								 	<s:param name="detailType" value="agent"/><!-- 明细类型 -->
								 	<s:param name="detailUserID" value="userID"/><!-- 總代理ID -->
								 	<s:param name="detailUserType" value="userType"/><!-- 明细类型：總代理 -->
								 	<s:param name="detailUserAccount" value="subordinate"/><!-- 账号 -->
								</s:url>  
								<s:a href="%{viewUrl}" cssClass="alink" onclick="return openDetailedReport(this)" title="查看總代理用户明细">
						  			<s:property value="subordinate"/>
								</s:a>
					        </s:elseif>
					        <s:elseif test="userType == 4">
								<!--  股东明细 -->
					            <s:url action="detailedReport.action" id="viewUrl">
								 	<s:param name="lotteryType" value="lotteryType"/>
								 	<s:param name="playType" value="playType"/>
								 	<s:param name="bettingDateStart" value="bettingDateStart"/>
								 	<s:param name="bettingDateEnd" value="bettingDateEnd"/>
								 	<s:param name="detailType" value="agent"/><!-- 明细类型 -->
								 	<s:param name="detailUserID" value="userID"/><!-- 總代理ID -->
								 	<s:param name="detailUserType" value="userType"/><!-- 明细类型：股东 -->
								 	<s:param name="detailUserAccount" value="subordinate"/><!-- 账号 -->
								</s:url>  
								<s:a href="%{viewUrl}" cssClass="alink" onclick="return openDetailedReport(this)" title="查看股东用户明细">
						  			<s:property value="subordinate"/>
								</s:a>
					        </s:elseif>
							<s:else>
								<a href="#" onclick="alert('详细信息')" title="点击链接查看详细信息">【<s:property value="subordinate" />】</a>
							</s:else>
						</td>
						<td>
                            <s:property value="userName" />
                        </td>
                        <td nowrap>
                            <s:property value="turnover" escape="false"/>
                        </td>
                        <td>
                            <s:property value="validAmountDis" />
                        </td>
                        <td nowrap>
                            <s:property value="memberAmountDis" escape="false"/>
                        </td>
                        <td nowrap>
                            <a href="javascript:function blankinfo(){return false;}" id='<s:property value="subordinate" escape="false"/>' 
                                 onmouseover="showDesc('<s:property value="subordinate" escape="false"/>||<s:property value="subordinate" escape="false"/>||<s:property value="subordinateRealValueDis" escape="false"/>||<s:property value="subordinateBackWaterDis" escape="false"/>')" 
                                 onmouseout="hideDesc('<s:property value="subordinate" escape="false"/>')"><s:property value="subordinateAmountDis"/></a>
                        </td>
                        <td nowrap>
                            <s:property value="rateName" />
                        </td>
                        <td nowrap>
                            <s:property value="rateValidAmountDis" />
                        </td>
                        <td nowrap>
                            <s:property value="realResultPerDis" />
                        </td>
                        <td nowrap>
                            <s:property value="realWinDis" />
                        </td>
                        <td nowrap>
                            <s:property value="realBackWaterDis" />
                        </td>
                        <td nowrap>
                            <s:property value="realResultNewDis" />
                        </td>
                        <td nowrap>
                            <s:property value="commissionDis" />
                        </td>
                        <td nowrap>
                            <s:property value="winBackWaterResultDis" />
                        </td>
                        <td nowrap>
                            <s:property value="offerSuperiorDis" />                             
                        </td>
                        <td nowrap>
                            <s:property value="paySuperiorDis" />
                        </td>
					</tr>
					</s:iterator>
					
					<!-- 合计数据 -->
		            <s:if test="null != #request.totalEntity">
		            	<s:iterator value ="#request.totalEntity">
		                <tr bgcolor='#FFFFFF' class=''>
		                    <td align="right" colspan="2">
                                <div align="right"><b><font color='#0343BA'><s:property value="userName"/></font></b></div>
                            </td>
                            <td nowrap>
                                <b><font color='#0343BA'><s:property value="turnover" escape="false"/></font></b>
                            </td>
                            <td nowrap>
                                <b><font color='#0343BA'><s:property value="validAmountDis" /></font></b>
                            </td>
                            <td nowrap>
                                <b><font color='#0343BA'><s:property value="memberAmountDis" /></font></b>
                            </td>
                            <td nowrap>
                                <b><font color='#0343BA'><s:property value="subordinateAmountDis" escape="false"/></font></b>
                            </td>
                            <td nowrap>
                                <b><font color='#0343BA'>--</font></b>
                            </td>
                            <td nowrap>
                                <b><font color='#0343BA'><s:property value="rateValidAmountDis" /></font></b>
                            </td>
                            <td nowrap>
                                <b><font color='#0343BA'><s:property value="realResultPerDis" /></font></b>
                            </td>
                            <td>
                                <b><font color='#0343BA'><s:property value="realWinDis" /></font></b>
                            </td>
                            <td nowrap>
                                <b><font color='#0343BA'><s:property value="realBackWaterDis" /></font></b>
                            </td>
                            <td nowrap>
                                <b><font color='#0343BA'><s:property value="realResultNewDis" escape="false"/></font></b>
                            </td>
                            <td nowrap>
                                <b><font color='#0343BA'><s:property value="commissionDis" /></font></b>
                            </td>
                            <td nowrap>
                                <b><font color='#0343BA'><s:property value="winBackWaterResultDis" /></font></b>
                            </td>
                            <td nowrap>
                                <b><font color='#0343BA'><s:property value="offerSuperiorDis" escape="false"/></font></b>
                            </td>
                            <td nowrap>
                                <b><font color='#0343BA'><s:property value="paySuperiorDis" escape="false"/></font></b>
                            </td>
		                </tr>
		                </s:iterator>
		            </s:if>

				</tbody>
			</table>
			
			<!-- 补货信息 -->
			<s:if test="null != #request.replenishEntity">
			<br/>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
			<td width="49.5%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="king mt4">
				<tbody>
					<tr>
						<th colspan="16" >
							補貨數據信息
						</th>
					</tr>
					<tr>
						<th width="15%">筆</th>
						<th width="17%">補貨金額</th>
						<th width="17%">有效金額</th>
						<th width="17%">補貨輸赢</th>
						<th width="17%">退水</th>
						<th width="17%">退水后結果</th>
					</tr>
					
					<!-- 迭代显示 List 集合中存储的数据记录 -->
					<s:iterator value="#request.replenishEntity" status="st">
					<!-- 行间隔色显示和当前行反白显示，固定写法 -->
					<tr <s:if test="true == #st.odd">class=''</s:if><s:else>class='even'</s:else> onmouseover='listSelect(this)' onmouseout='listUnSelect(this)' >
						<td>
							<s:url action="replenishDetailedList.action" id="replenishViewUrl">
							 	<s:param name="replenishUserId" value="replenishUserId"/>
							 	<s:param name="lotteryType" value="lotteryType"/>
								<s:param name="playType" value="playType"/>
								<s:param name="bettingDateStart" value="bettingDateStart"/>
								<s:param name="bettingDateEnd" value="bettingDateEnd"/>
								<s:param name="detailUserType" value="userType"/><!-- 明细用户类型 -->
							</s:url>  
							<s:a href="%{replenishViewUrl}" cssClass="alink" onclick="return openDetailedReport(this)" title="查看补货数据明细">
					  			<s:property value="turnover"/> 
							</s:a>
						</td>
						<td nowrap>
							<s:property value="replenishAmountDis"/>
						</td>
						<td nowrap>
							<s:property value="replenishValidAmountDis"/>
						</td>
						<td nowrap>
							<s:property value="replenishWinDis" />
						</td>
						<td>
							<s:property value="backWaterDis" />
						</td>
						<td nowrap>
							<s:property value="backWaterResultDis"/>
						</td>
					</tr>
					</s:iterator>
					
				</tbody>
			</table>
			</td>
			<td width="1%"></td>
			
			<!-- 含补货的合计数据 -->
			<td width="49.5%" align="right">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="king mt4">
				<tbody>
					<tr>
						<th colspan="16" >
							汇總
						</th>
					</tr>
					<tr>
						<th width="20%">實占結果</th>
						<th width="20%">賺取退水</th>
						<th width="40%" colspan="2">抵扣補貨及賺水后結果</th>
						<th width="20%">应付上級</th>
					</tr>
					
					<!-- 迭代显示 List 集合中存储的数据记录 -->
					<s:iterator value="#request.totalEntitySec" status="st">
					<!-- 行间隔色显示和当前行反白显示，固定写法 -->
					<tr <s:if test="true == #st.odd">class=''</s:if><s:else>class='even'</s:else> onmouseover='listSelect(this)' onmouseout='listUnSelect(this)' >
						<td>
							<s:property value="realResultDis"/>
						</td>
						<td nowrap>
							<s:property value="winBackWaterDis"/>
						</td>
						<td colspan="2" nowrap>
							<s:property value="winBackWaterRepleDis" />
						</td>
						<td nowrap>
							<s:property value="paySuperiorDis" />
						</td>
					</tr>
					</s:iterator>
					
				</tbody>
			</table>
            </td>
            </tr>
            </table>
            </s:if>
            </td>
            </tr>
            <!-- 正文区域 End -->    
            <s:if test="null != #request.resultList && 0 < #request.resultList.size()">
            <tr>
                <th colspan="3">
                                                    ***
                </th>
            </tr>
            </s:if>
            </table>
        </div>
    </s:form>
    
</html>