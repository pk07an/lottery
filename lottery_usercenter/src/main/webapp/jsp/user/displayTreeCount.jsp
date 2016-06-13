
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
Map treeMap=(Map)request.getAttribute("treeMap");
String userType=(String)request.getAttribute("userType");
String account=(String)request.getAttribute("account");
String status=request.getParameter("searchUserStatus");
if(status==null)
	status="0";

/* Set keys=treeMap.keySet()
Iterator it=keys.iterator();
while(it.hasNext())
{
	String key=it.next();

} */
String branchCount=(String)treeMap.get("3");
String stockCount=(String)treeMap.get("4");
String genAgentCount=(String)treeMap.get("5");
String agentCount=(String)treeMap.get("6");
String memberCount=(String)treeMap.get("9");

String branchURL="<a target='_self' href='"+request.getContextPath()+"/user/queryBranchStaff.action?type="+userType+"&account="+account+"&searchUserStatus="+status+"' >"+(branchCount==null?"0":branchCount)+"</a>";
String stockURL="<a target='_self' href='"+request.getContextPath()+"/user/queryStockholder.action?type="+userType+"&account="+account+"&searchUserStatus="+status+("3".equals(userType)?"&parentAccount="+account:"")+"'>"+(stockCount==null?"0":stockCount)+"</a>";
String genAgentURL="<a target='_self' href='"+request.getContextPath()+"/user/queryGenAgentStaff.action?type="+userType+"&account="+account+"&searchUserStatus="+status+("4".equals(userType)?"&parentAccount="+account:"")+"'>"+(genAgentCount==null?"0":genAgentCount)+"</a>";
String agentURL="<a target='_self' href='"+request.getContextPath()+"/user/queryAgentStaff.action?type="+userType+"&account="+account+"&searchUserStatus="+status+("5".equals(userType)?"&parentAccount="+account:"")+"'>"+(agentCount==null?"0":agentCount)+"</a>";
String memberURL="<a target='_self' href='"+request.getContextPath()+"/user/queryMemberStaff.action?type="+userType+"&account="+account+"&searchUserStatus="+status+("6".equals(userType)?"&parentAccount="+account:"")+"'>"+(memberCount==null?"0":memberCount)+"</a>";
if("2".equals(userType)){%>
<td><b><%=branchURL%></b></td><td><b><%=stockURL%></b></td><td><b><%=genAgentURL%></b></td><td><b><%=agentURL %></b></td>
<td><b><%=memberURL %></b></td>
<%}else if("3".equals(userType)){%>
<td><b><%=stockURL%></b></td><td><b><%=genAgentURL %></b></td><td><b><%=agentURL %></b></td>
<td><b><%=memberURL %></b></td>

<% }else if("4".equals(userType)){%>
<td><b><%=genAgentURL %></b></td><td><b><%=agentURL %></b></b></td>
<td><b><%=memberURL %></b></td>

<% }else if("5".equals(userType)){%>

<td><b><%=agentURL %></b></td>
<td><b><%=memberURL %></b></td>
<%}else if("6".equals(userType)){%>

<td><b><%=memberURL %></b></td>

<%} %>













