<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.npc.lottery.common.Constant "%>
<%@ page import="com.npc.lottery.sysmge.entity.Roles"%>
<%@ page import="com.npc.lottery.sysmge.entity.ManagerStaff,com.npc.lottery.sysmge.entity.ManagerUser"%>
<%@ page import="com.npc.lottery.user.entity.SubAccountInfo"%>
<% ManagerUser userInfo = (ManagerUser) session.getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION); %>
<% 

	   boolean isSys = ManagerStaff.USER_TYPE_SYS.equals(userInfo.getUserType());// 系统類型
       boolean isManager = ManagerStaff.USER_TYPE_MANAGER.equals(userInfo.getUserType());// 總管類型
      boolean isChief = ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType());// 總监類型
      boolean isBranch = ManagerStaff.USER_TYPE_BRANCH.equals(userInfo.getUserType());// 分公司類型
      boolean isStockholder = ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfo.getUserType());// 股東
      boolean isGenAgent = ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfo.getUserType());// 總代理
      boolean isAgent = ManagerStaff.USER_TYPE_AGENT.equals(userInfo.getUserType());// 代理
      boolean isSub = ManagerStaff.USER_TYPE_SUB.equals(userInfo.getUserType());// 子账号
      boolean isFreeze = false;
      if(ManagerUser.LOGIN_STATE_FAILURE_USER_FREEZE==userInfo.getLoginState()) isFreeze=true;
      
      
      boolean isSubChief = false;
      boolean isSubBranch = false;
      boolean isSubStockholder = false;
      boolean isSubGenAgent = false;
      boolean isSubAgent = false;
      
      if(isSub==true){
          if(ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getParentStaffTypeQry())) isSubChief=true;
          if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfo.getParentStaffTypeQry())) isSubBranch=true;
          if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfo.getParentStaffTypeQry())) isSubStockholder=true;
          if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfo.getParentStaffTypeQry())) isSubGenAgent=true;
          if(ManagerStaff.USER_TYPE_AGENT.equals(userInfo.getParentStaffTypeQry())) isSubAgent=true;
      }
      
     /*  if(subAccountInfo !=null){
          isSubChief = ManagerStaff.USER_TYPE_CHIEF.equals(subAccountInfo.getParentUserType());// 子账号總监類型
          isSubBranch = ManagerStaff.USER_TYPE_BRANCH.equals(subAccountInfo.getParentUserType());// 子账号分公司類型
          isSubStockholder = ManagerStaff.USER_TYPE_STOCKHOLDER.equals(subAccountInfo.getParentUserType());// 子账号股東
          isSubGenAgent = ManagerStaff.USER_TYPE_GEN_AGENT.equals(subAccountInfo.getParentUserType());// 子账号總代理
          isSubAgent = ManagerStaff.USER_TYPE_AGENT.equals(subAccountInfo.getParentUserType());// 子账号代理  
      }  */
      String replenishment = null;
      String offLineAccount = null;
      String subAccount = null;
      String crossReport = null;//交收报表
      String classifyReport = null;//分类报表
      
      String odd = null;
      String outReplenishManager = null;
      String oddLog = null;//操作記錄
      String sysInit = null;
      String tradingSet = null;
      String message = null;
      String searchBill = null;
      String backsysRole = null;
      String cancelBill = null;
      String jszd = null;
      
      List list = userInfo.getRoleList();
      if(list.size() > 0){
    	  isSub = true;
      }
      
      for(int i=0;i<list.size();i++){
    	  Roles roles = (Roles)list.get(i);
    	  String roleCode = roles.getRoleCode();
    	  
    	  if(roleCode.indexOf("CHIEF")!=-1){
    		  isSubChief = true;
    	  }else if(roleCode.indexOf("BRANCH")!=-1){
    	      isSubBranch = true;
		  }else if(roleCode.indexOf("STOCKHOLDER")!=-1){
    	      isSubStockholder = true;
		  }else if(roleCode.indexOf("GEN_AGENT")!=-1){
    	      isSubGenAgent = true;
		  }else if(roleCode.indexOf("AGENT")!=-1){
    	      isSubAgent = true;
		  }
    	  
    	  if(roleCode.indexOf(ManagerStaff.SUB_ROLE_REPLENISH)!=-1){
              replenishment = roleCode;
          }if(roleCode.indexOf(ManagerStaff.SUB_ROLE_OFFLINE)!=-1){
              offLineAccount = roleCode;
          }if(roleCode.indexOf(ManagerStaff.SUB_ROLE_SUB)!=-1){
              subAccount = roleCode;
          }if(roleCode.indexOf(ManagerStaff.SUB_ROLE_DELIVERY)!=-1){
              crossReport = roleCode;
          }if(roleCode.indexOf(ManagerStaff.SUB_ROLE_CLASSIFY)!=-1){
              classifyReport = roleCode;
          }
          
          if(roleCode.indexOf(ManagerStaff.SUB_ROLE_ODD)!=-1 && roleCode.indexOf(ManagerStaff.SUB_ROLE_ODD_LOG)==-1){
              odd = roleCode;
          }if(roleCode.indexOf(ManagerStaff.SUB_ROLE_OUT_USER_MANAGER)!=-1){
          	outReplenishManager = roleCode;
          }if(roleCode.indexOf(ManagerStaff.SUB_ROLE_ODD_LOG)!=-1){
          	oddLog = roleCode;
          }if(roleCode.indexOf(ManagerStaff.SUB_ROLE_SYS_INIT)!=-1){
          	sysInit = roleCode;
          }if(roleCode.indexOf(ManagerStaff.SUB_ROLE_TRADING_SET)!=-1){
          	tradingSet = roleCode;
          }if(roleCode.indexOf(ManagerStaff.SUB_ROLE_MESSAGE)!=-1){
          	message = roleCode;
          }if(roleCode.indexOf(ManagerStaff.SUB_ROLE_SEARCH_BILL)!=-1){
          	searchBill = roleCode;
          }if(roleCode.indexOf(ManagerStaff.SUB_ROLE_BACKSYS_ROLE)!=-1){
          	backsysRole = roleCode;
          }if(roleCode.indexOf(ManagerStaff.SUB_ROLE_CANCEL_BILL)!=-1){
          	cancelBill = roleCode;
          }if(roleCode.indexOf(ManagerStaff.SUB_ROLE_JSZD)!=-1){
          	jszd = roleCode;
          }
          //System.out.print("~~~~~~offLineAccount : "+offLineAccount);
      }
      String usreType = "";
      if(isChief || isSubChief) usreType = "管理員";
      if(isBranch || isSubBranch) usreType = "分公司";
      if(isStockholder || isSubStockholder) usreType = "股東";
      if(isGenAgent || isSubGenAgent) usreType = "總代理";
      if(isAgent || isSubAgent) usreType = "代理";
      
      //System.out.print("~~~~~~isSubChief : "+isSubChief);
      %>