<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.math.BigDecimal,com.npc.lottery.util.Page,com.npc.lottery.common.Constant" %>
<%@page import="java.text.SimpleDateFormat,java.text.NumberFormat,com.npc.lottery.member.entity.BaseBet,org.apache.commons.lang.StringUtils,com.npc.lottery.util.PlayTypeUtils,com.npc.lottery.member.entity.PlayType"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<script>
var SX = {"SHU": "鼠", "HU": "虎","LONG": "龍","MA": "马","HOU": "猴","GOU": "狗","NIU": "牛","TU": "兔","SHE": "蛇","YANG": "羊","JI": "雞","ZHU": "豬"};
</script>

<style>
	.alink{
   		color: #333333;
   		text-decoration:underline
   	}
</style>

<div class="content">
	<s:form name="sForm" action="#">
		<div class="main">
			
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="king2">
				<tbody>
					<tr>
						<th width="21%">注單號碼/時間</th>
						<th width="21%">下注類型</th>
						<th width="8%">會員</th>
						<th width="21%">下注明細</th>
						<th width="7%">會員下注</th>
						<th width="7%">會員結果</th>
						<th width="7%">占成</th>
						<th width="8%">您的結果</th>
					</tr>
					<%
					    Page betPage=(Page)request.getAttribute("page");
					    List betList=betPage.getResult();
					    String preOrderNo="";
					    if(betList.size() > 0){
					        for(int i=0;i<betList.size();i++)
					        {      	
					        BaseBet betMap=(BaseBet)betList.get(i);
					        String orderNo=betMap.getOrderNo();
					        if(preOrderNo.equals(orderNo))
					         	continue;
					        Date betTime=betMap.getBettingDate();
					        SimpleDateFormat sm=new SimpleDateFormat("MM-dd HH:mm E");
					        String betStrTime= sm.format(betTime);
					        String typeCode=betMap.getTypeCode();
					       
					        PlayType playType=PlayTypeUtils.getPlayType(typeCode);
					        String subTypeName=PlayTypeUtils.getPlayTypeSubName(typeCode);
					        String subType=playType.getPlaySubType();
					        String finalType=playType.getPlayFinalType();
					        if(("ZHDA".equals(finalType)||"ZHX".equals(finalType)||"ZHDAN".equals(finalType)||"ZHS".equals(finalType)))
					            subTypeName="";
					        Integer count=betMap.getCount();
					        BigDecimal odds=(BigDecimal)betMap.getOdds();
					        BigDecimal odds2=(BigDecimal)betMap.getOdds2();     
					        String odds2S="";
					        if(odds2!=null&&odds2.intValue()>0)
					        	odds2S=odds2.toString();
					        Integer money=betMap.getMoney();
					        NumberFormat nf=NumberFormat.getNumberInstance();
					        nf.setMaximumFractionDigits(1);
					        String winMoney=nf.format(money.floatValue()*(odds.floatValue()-1)*count);
					        String attribute=betMap.getAttribute();
					        
					        String displayAttr="";
					        String selectedOdds=betMap.getSelectedOdds();
					        if(attribute==null)displayAttr="";
					       
					        if(typeCode.indexOf("HK_STRAIGHTTHROUGH")!=-1)
					        {     	
					        	String[] typeAndBet=StringUtils.split(attribute,"\\*");
					    		if(typeAndBet.length>=2)
					    		{
					    		String type=typeAndBet[0];
					    		String bet=typeAndBet[1];
					    		if("DZ".equals(type))
					    		{	
					    			if(count==1)
					    				displayAttr="單註"+bet.replace("|", ",");
					    			else
					    			displayAttr="複式"+bet.replace("|", ",");
					    		
					    		}
					    		else if("T1D".equals(type))
					    		{
					    			String[] danAndBall=StringUtils.split(bet,"\\&");
					    			String dan=danAndBall[0];
					    			//String[] balls=StringUtils.split(danAndBall[1],"\\|");
					    			displayAttr="膽  "+dan+" <font color=\"red\">拖</font>"+danAndBall[1].replace("|", ",");
					    		}
					    		else if("T2D".equals(type))
					    		{
					    			String[] danAndBall=StringUtils.split(bet,"\\&");
					    			String[] dan=StringUtils.split(danAndBall[0],"\\|");
					    			String[] balls=StringUtils.split(danAndBall[1],"\\|");
					    			displayAttr="膽  "+danAndBall[0].replace("|", ",")+" <font color=\"red\">拖</font>"+danAndBall[1].replace("|", ",");  			
					    		}
					    		else if("SXDP".equals(type))
					    		{
					    			String[] sxs=StringUtils.split(bet,"\\|");
					    			displayAttr="生肖對碰  "+Constant.HK_SX_MAP.get(sxs[0])+" <font color=\"red\">碰</font> "+Constant.HK_SX_MAP.get(sxs[1]);
					    		}
					    		else if("WSDP".equals(type))
					    		{
					    			String[] wss=StringUtils.split(bet,"\\|");
					    			displayAttr="尾数對碰  "+Constant.HK_WS_MAP.get(wss[0])+" <font color=\"red\">碰</font> "+Constant.HK_WS_MAP.get(wss[1]);	
					    		}
					    		
					        }
					    		
					        }
					        else if(typeCode.indexOf("HK_WSL")!=-1)
					        {
					        	String[] danAndWS=StringUtils.split(attribute,"\\&");
					    		String[] dan=null;
					    		String[] ws=null;
					    		if(danAndWS.length==2)
					    		{
					    			dan=StringUtils.split(danAndWS[0],"\\|");
					    			ws=StringUtils.split(danAndWS[1],"\\|");
					    			
					    			for(int j=0;j<dan.length;j++)
					    			{
					    				dan[j]=Constant.HK_WS_MAP.get(dan[j])+"@<font color='red'>"+StringUtils.substringBetween(selectedOdds+"|", dan[j]+"&", "|")+"</font>";
					    			}
					    			for(int k=0;k<ws.length;k++)
					    			{
					    				ws[k]=Constant.HK_WS_MAP.get(ws[k])+"@<font color='red'>"+StringUtils.substringBetween(selectedOdds+"|", ws[k]+"&", "|")+"</font>";
					    			}
					    			displayAttr=""+StringUtils.join(dan,",")+" <font color=\"red\">拖</font> "+StringUtils.join(ws,",");
					    		
					    		}
					    		else if(danAndWS.length==1)
					    		{
					    				ws=StringUtils.split(danAndWS[0],"\\|");
					    				for(int k=0;k<ws.length;k++)
					    				{
					    					ws[k]=Constant.HK_WS_MAP.get(ws[k])+"@<font color='red'>"+StringUtils.substringBetween(selectedOdds+"|", ws[k]+"&", "|")+"</font>";
					    				}
					    				displayAttr=StringUtils.join(ws,",");	
					    		}
					    		
					        	
					        }
					        else if(typeCode.indexOf("HK_SXL")!=-1)
					        {
					        	String[] danAndSX=StringUtils.split(attribute,"\\&");
					    		String[] dan=null;
					    		String[] sx=null;
					    		if(danAndSX.length==2)
					    		{
					    			dan=StringUtils.split(danAndSX[0],"\\|");
					    			sx=StringUtils.split(danAndSX[1],"\\|");
					    			
					    			for(int j=0;j<dan.length;j++)
					    			{
					    				dan[j]=Constant.HK_SX_MAP.get(dan[j])+"@<font color='red'>"+StringUtils.substringBetween(selectedOdds+"|", dan[j]+"&", "|")+"</font>";
					    			}
					    			for(int k=0;k<sx.length;k++)
					    			{
					    				sx[k]=Constant.HK_SX_MAP.get(sx[k])+"@<font color='red'>"+StringUtils.substringBetween(selectedOdds+"|", sx[k]+"&", "|")+"</font>";
					    			}
					    			displayAttr=""+StringUtils.join(dan,",")+" <font color=\"red\">拖</font> "+StringUtils.join(sx,",");
					    		
					    		}
					    		else if(danAndSX.length==1)
					    		{
					    			sx=StringUtils.split(danAndSX[0],"\\|");
					    				for(int k=0;k<sx.length;k++)
					    				{
					    					sx[k]=Constant.HK_SX_MAP.get(sx[k])+"@<font color='red'>"+StringUtils.substringBetween(selectedOdds+"|", sx[k]+"&", "|")+"</font>";
					    				}
					    				displayAttr=StringUtils.join(sx,",");	
					    		}
					    		
					    		
					        	
					        }
					        else if(typeCode.indexOf("HK_GG")!=-1)
					        {
					        	String[] guan=StringUtils.split(attribute,"\\|");
					        	for(int z=0;z<guan.length;z++){
					        		 String maCode="";
					    	    	 String betCode="";
					    	    	 String ballName=guan[z];
					        	 if(ballName.indexOf("ZM1")!=-1)
					        		 maCode="正碼一";
					        	 else if(ballName.indexOf("ZM2")!=-1)
					        		 maCode="正碼二";
					        	 else if(ballName.indexOf("ZM3")!=-1)
					        		 maCode="正碼三";
					        	 else if(ballName.indexOf("ZM4")!=-1)
					        		 maCode="正碼四";
					        	 else if(ballName.indexOf("ZM5")!=-1)
					        		 maCode="正碼五";
					        	 else if(ballName.indexOf("ZM6")!=-1)
					        		 maCode="正碼六";
					        	 if(ballName.indexOf("DAN")!=-1)
					        		 betCode="單";
					        	 else if(ballName.indexOf("DA")!=-1)
					        			 betCode="大";
					        	 else  if(ballName.indexOf("S")!=-1)
					        				 betCode="雙" ;
					        	 else if(ballName.indexOf("X")!=-1)
					        				 betCode="小";
					        	 else if(ballName.indexOf("RED")!=-1)
					        				 betCode="紅";
					        	 else if(ballName.indexOf("GREEN")!=-1)
					        				 betCode="綠";
					        	 else if(ballName.indexOf("BLUE")!=-1)
					        				 betCode="藍";
					        	 guan[z]=	maCode+"-"+betCode+"@<font color='red'>"+StringUtils.substringBetween(selectedOdds+"|", ballName+"&", "|")+"</font>";
					        	
					        }
					        	 displayAttr=StringUtils.join(guan,",");	
					        }
					        else if(typeCode.indexOf("HK_LX")!=-1)
					        {
					        	String[] sxs=attribute.split("\\|");
					        	for(int j=0;j<sxs.length;j++)
					    		{
					        		sxs[j]=Constant.HK_SX_MAP.get(sxs[j]);
					    		}
					        	displayAttr=StringUtils.join(sxs,",");	
					        	
					        }
					        else if(typeCode.indexOf("HK_WBZ")!=-1)
					        {
					        	String[] ballNum=attribute.split("\\|");
					        	for(int j=0;j<ballNum.length;j++)
					    		{
					        		String ballName=StringUtils.leftPad(ballNum[j], 2, '0');
					        		ballNum[j]=ballName+"@<font color='red'>"+StringUtils.substringBetween(selectedOdds+"|", ballName+"&", "|")+"</font>";;
					    		}
					        	displayAttr=StringUtils.join(ballNum,",");	
					        	
					        }
					        
					        else 
					        	 if(attribute!=null&&attribute.length()!=0)
					        		 displayAttr=attribute.replace("|", ",");
					        %>

					<tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
						<td align="center">
							<%=betMap.getOrderNo()%>#</br><%=betStrTime %>
						</td>
						<td align="center" nowrap>
							香港六合彩
                 			 </br><span class="green"><%=betMap.getPeriodsNum() %></span>
						</td>
						<td align="center" nowrap>
							<%=betMap.getUserName() %> / <%=betMap.getPlate() %>盤
						</td>
						<td align="center" 
							 style='word-break:break-all'><%=subTypeName %> 『<%=PlayTypeUtils.getPlayTypeFinalName(typeCode) %>』
					          <%if(!(typeCode.indexOf("HK_WBZ")!=-1||typeCode.indexOf("HK_WSL")!=-1||typeCode.indexOf("HK_SXL")!=-1||typeCode.indexOf("HK_GG")!=-1)) {%>
					          @<span class="red"><%=betMap.getOdds() %></span><%} %>
					          <%if("HK_STRAIGHTTHROUGH_3Z2".equals(typeCode)){%>中叁 @<span class="red"><%=odds2S%></span><%}
					          else if("HK_STRAIGHTTHROUGH_2ZT".equals(typeCode)){%>中二@<span class="red"><%=odds2S%></span><%}%>
					           <%if(count>1) {%></br>复式『<%=count%>组』<%} %>
					         </br>
					         <%=displayAttr %>      
						</td>
						
						<s:if test="winState==4">
		 	    	        <td style="text-decoration:line-through;color:red;" align="center" nowrap> 
		 	    	           <%if(count>1) {%> <%=betMap.getMoney()%>X<%=count%>组</br><%} %><%=betMap.getMoney()*count %>
		 	    	        </td>
		 	    	    </s:if><s:elseif test="winState==5">
		 	    	        <td style="text-decoration:line-through;color:red;" align="center" nowrap> 
		 	    	          <%if(count>1) {%> <%=betMap.getMoney()%>X<%=count%>组</br><%} %><%=betMap.getMoney()*count %>
		 	    	        </td>
		 	    	    </s:elseif><s:else>
			 	    	    <td align="center" nowrap>
								<%if(count>1) {%> <%=betMap.getMoney()%>X<%=count%>组</br><%} %><%=betMap.getMoney()*count %>
							</td>
		 	    	    </s:else>
						
						
						<%-- <td align="center" nowrap>
							<%if(count>1) {%> <%=betMap.getMoney()%>X<%=count%>组</br><%} %><%=betMap.getMoney()*count %>
						</td> --%>
						<td align="center" nowrap>
							--
						</td>
						<td align="center" nowrap>
							<%=betMap.getRate() %>%
						</td>
						<td align="center">
							<s:property value="winStateName" escape="false"/>
						</td>
					</tr>
					
					<!-- 合计数据 -->
		            <s:if test="null != #request.totalEntity">
		            	<s:iterator value ="#request.totalEntity">
		                <tr bgcolor='#FFFFFF' class=''>
		                    <td align="right" colspan="2">
								<s:property value="userName" />
							</td>
							<td align="center" nowrap>
								<s:property value="turnover" escape="false"/>
							</td>
							<td align="center" nowrap>
								<s:property value="amount" />
							</td>
							<td align="center">
								--
							</td>
							<td align="center" nowrap>
								--
							</td>
							<td align="center" nowrap>
								--
							</td>
							<td align="center" nowrap>
								--
							</td>
							<td align="center">
								--
							</td>
							<td align="center" nowrap>
								--
							</td>
							<td align="center" nowrap>
								--
							</td>
							<td align="center">
								--
							</td>
							<td align="center" nowrap>
								--
							</td>
		                </tr>
		                </s:iterator>
		            </s:if>
				   <%
				    preOrderNo=orderNo;
				        }
				    }else
				    {%>
				    <tr onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#FFFFA2'" style="">
					       <td colspan="5"><font color="red">当前没有下注</font></td>
					     </tr>
					<%}
				    %>
				</tbody>
			</table>
			<s:if test="#request.page.totalCount>0">
				<lottery:paginate url="${pageContext.request.contextPath}/statreport/unsettledDetailedList.action" 
				param="userID=${userID}&userType=${userType}&lotteryType=${lotteryType}&playType=${playType}& 
				bettingDateStart=${bettingDateStart}&bettingDateEnd=${bettingDateEnd}"/>
			</s:if>  
		</div>
	</s:form>
	
</html>
