<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="lottery" uri="/WEB-INF/tag/pagination.tld" %><!-- 分页标签 -->
<%@ page import="com.npc.lottery.util.Tool,com.npc.lottery.common.Constant,com.npc.lottery.member.entity.PlayType"%>  

<table cellspacing="0" cellpadding="0" border="0" width="100%" class="king changlong" >
  <tbody><tr>
    <td colspan="2">兩面長龍排行</td>
    </tr>
    <%
    
    List<PlayType> playTypes=(List<PlayType>)request.getAttribute("twoSide");
    int count=0;
    int displaySize=0;
    for(int i=0;i<playTypes.size();i++)
    {
    	PlayType playType=playTypes.get(i);
    	String subTypeName=playType.getSubTypeName();
    	String finalTypeName=playType.getFinalTypeName();
    	if(playType.getTypeCode().indexOf("BJ")!=-1)
    	{      
    		if("GROUP".equals(playType.getPlaySubType()))
    		{
    			if("DA".equals(playType.getPlayFinalType())
    			 ||"X".equals(playType.getPlayFinalType())
    			 ||"DAN".equals(playType.getPlayFinalType())
    			 ||"S".equals(playType.getPlayFinalType())
    					)
    				subTypeName="";
    		}
    	}
    	if(subTypeName!=null)
    	{
    		if(subTypeName.indexOf("一")!=-1)
    		{	
    		subTypeName=subTypeName.replace("一", "1");
    		}
    		else if(subTypeName.indexOf("二")!=-1)
    			subTypeName=subTypeName.replace("二", "2");

    		else if(subTypeName.indexOf("三")!=-1)
    			subTypeName=subTypeName.replace("三", "3");
    		else if(subTypeName.indexOf("四")!=-1)
    			subTypeName=subTypeName.replace("四", "4");
    		else if(subTypeName.indexOf("五")!=-1)
    			subTypeName=subTypeName.replace("五", "5");
    		else if(subTypeName.indexOf("六")!=-1)
    			subTypeName=subTypeName.replace("六", "6");
    		else if(subTypeName.indexOf("七")!=-1)
    			subTypeName=subTypeName.replace("七", "7");
    		else if(subTypeName.indexOf("八")!=-1)
    			subTypeName=subTypeName.replace("八", "8");
    		else if(subTypeName.indexOf("九")!=-1)
    			subTypeName=subTypeName.replace("九", "9");
    		else if(subTypeName.indexOf("十")!=-1)
    			subTypeName=subTypeName.replace("十", "10");
    	}
    	
    	int typeCount=playType.getCount();
    	if(typeCount<2)
    		break;
    	/* if(count!=typeCount)
    		displaySize++;	
    	if(displaySize==4)
    		break; */
    %>
   
        
        
        
   <tr>
    <td width="50%" class="danlan">
    <%if(subTypeName!=null&&subTypeName.length()!=0){ %><%=subTypeName %>-<%} %><%=finalTypeName %>
    </td>
    <td width="20%" class="danhong"><%=typeCount %> 期</td>
  </tr>
    <%
    count= typeCount;
    }
    %>    

 
</tbody></table>
