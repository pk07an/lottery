<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.npc.lottery.util.Page,com.npc.lottery.periods.entity.CQPeriodsInfo,com.npc.lottery.rule.CQSSCBallRule" %>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<body>
<div class="content">
<div class="main">
  <div class="top_content">第1~8球、總和、龍虎投注匯總表</div>
  <table cellspacing="0" cellpadding="0" border="0" width="100%" class="king">
  <tbody>
    <tr>
      <th width="14%">類型</th>
      <th width="10%">筆</th>
      <th width="19%">實占金額</th>
      <th width="23%">實占輸贏</th>
      <th width="15%">退水</th>
      <th width="19%">退水后結果</th>
      </tr>
    <%
    Page<CQPeriodsInfo> pageResult=(Page)request.getAttribute("page");
    List<CQPeriodsInfo> result=pageResult.getResult();
    for(int i=0;i<result.size();i++)
    {
    	List<Integer> ballList=new ArrayList<Integer>();
    	
    	CQPeriodsInfo periond=result.get(i);
    	String periondNum=periond.getPeriodsNum();
        Integer b1=periond.getResult1();ballList.add(b1);
        Integer b2=periond.getResult2();ballList.add(b2);
        Integer b3=periond.getResult3();ballList.add(b3);
        Integer b4=periond.getResult4();ballList.add(b4);
        Integer b5=periond.getResult5();ballList.add(b5);
        List<Integer> fballList=new ArrayList<Integer>();
        List<Integer> mballList=new ArrayList<Integer>();
        List<Integer> lballList=new ArrayList<Integer>();
        fballList.add(b1); fballList.add(b2); fballList.add(b3);
        mballList.add(b2); mballList.add(b3); mballList.add(b4);
        lballList.add(b3); lballList.add(b4); lballList.add(b5);
        Date lotteryTime=periond.getLotteryTime();
        SimpleDateFormat sm=new SimpleDateFormat("MM-dd E HH:mm");
        String lotteryStrTime= sm.format(lotteryTime);
        
    
    %>
   
       
        
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td colspan="6"><div align="center">1-8大小</div></td>
        </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td>第一球【大】</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td>第二球【大】</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td colspan="6"><div align="center">1-8合數單雙</div></td>
        </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td>第一球【合雙】</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td colspan="6"><div align="center">1-8大小</div></td>
        </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td>第三球【小】</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td colspan="6"><div align="center">1-8單雙</div></td>
        </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td>第一球【單】</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td>第二球【單】</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td colspan="6"><div align="center">1-8尾數大小</div></td>
        </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td>第一球【尾大】</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td colspan="6"><div align="center">1-8單雙</div></td>
        </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td>第一球【大】</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td colspan="6"><div align="center">龍虎</div></td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td>龍</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
        <td>合計</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr onMouseOut="this.style.backgroundColor=''" onMouseOver="this.style.backgroundColor='#FFFFA2'" style="" <%if(i%2!=0)out.print("class=\"even\""); %>>
      <td colspan="6"><div align="center">報表生成時間：2012/5/7 15:00:00</div></td>
      </tr>
 <%
    
    }
    %>
   
        </table>
  <s:if test="#request.page.totalCount>0">
      
	<lottery:paginate url="${pageContext.request.contextPath}/admin/enterLotResultHistoryAdmin.action" param="subType=CQSSC"/>
</s:if>
    
  </div>

</div>
</body>

</html>
