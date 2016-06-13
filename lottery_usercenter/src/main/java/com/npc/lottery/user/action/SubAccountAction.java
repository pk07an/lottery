package com.npc.lottery.user.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.npc.lottery.common.Constant;
import com.npc.lottery.util.Page;
import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;
import com.npc.lottery.util.Tools;

public class SubAccountAction extends BaseUserAction{

    Logger logger = Logger.getLogger(SubAccountAction.class);
    private IChiefStaffExtLogic chiefStaffExtLogic = null;// 总监
    private IBranchStaffExtLogic branchStaffExtLogic = null;// 公公司
    private IGenAgentStaffExtLogic genAgentStaffExtLogic; // 总代理
    private IAgentStaffExtLogic agentStaffExtLogic; // 代理
    private IStockholderStaffExtLogic stockholderStaffExtLogic = null;// 股东
    private ChiefStaffExt chiefStaffExt;
    private BranchStaffExt branchStaffExt;
    private StockholderStaffExt stockholderStaffExt;
    private GenAgentStaffExt genAgentStaffExt;
    private AgentStaffExt agentStaffExt;
    private ManagerUser userInfo;
    private String type="userSubMember";
    private SubAccountInfo subAccountInfo;
    private IAuthorizLogic authorizLogic;
    
    //***************權限start**********************
    private String replenishment = null;//补货
    private String offLineAccount = null;//下线账号管理
    private String subAccount = null;//子账号管理
    private String crossReport = null;//总监交收报表
    private String classifyReport = null;//总监分类报表
    
    private String odd = null;//操盤權限、輸贏分析
    private String outReplenishManager = null;//出貨會員管理
    private String oddLog = null;//每期彩票管理、操盤記錄查詢
    private String sysInit = null;//系統初始設定
    private String tradingSet = null;//交易設定、賠率設定
    private String message = null;//站內消息管理
    private String searchBill = null;//注單搜索
    private String backsysRole = null;//系統后臺維護權限
    private String cancelBill = null;//注單取消權限
    private String jszd = null;//即時注單
    //***************權限end**********************
    
    private String updateReplenishment = null;//补货
    private String updateOffLineAccount = null;//下线账号管理
    private String updateSubAccount = null;//子账号管理
    private String updateCrossReport = null;//总监交收报表
    private String updateClassifyReport = null;//总监分类报表
    
    private String updateOdd = null;//操盤權限、輸贏分析
    private String updateOutReplenishManager = null;//出貨會員管理
    private String updateOddLog = null;//每期彩票管理、操盤記錄查詢
    private String updateSysInit = null;//系統初始設定
    private String updateTradingSet = null;//交易設定、賠率設定
    private String updateMessage = null;//站內消息管理
    private String updateSearchBill = null;//注單搜索
    private String updateBacksysRole = null;//系統后臺維護權限
    private String updateCancelBill = null;//注單取消權限
    private String updateJszd = null;//即時注單
    /**
     * 
     */
    private static final long serialVersionUID = 3803695142949529903L;
    
    public String querySubAccount(){
        ManagerUser userInfoOld = this.getCurrentManagerUser();
      //子帐号处理*********START
        ManagerUser userInfo = new ManagerUser();
        try {
        	BeanUtils.copyProperties(userInfo, userInfoOld);
        } catch (Exception e) {
        	logger.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
        }
           if(ManagerStaff.USER_TYPE_SUB.equals(userInfo.getUserType())){
        	   userInfo = getSubAccountParent(userInfo);	
           }	
        
        boolean isSys = ManagerStaff.USER_TYPE_SYS.equals(userInfo.getUserType());// 系统类型
        boolean isManager = ManagerStaff.USER_TYPE_MANAGER.equals(userInfo.getUserType());// 总管类型
       
        if (!isSys || !isManager)// 总管和系统管理员一般不操作
        {
            boolean isChief = ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType());// 总监类型
            boolean isBranch = ManagerStaff.USER_TYPE_BRANCH.equals(userInfo.getUserType());// 分公司类型
            boolean isStockholder = ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfo.getUserType());// 股东
            boolean isGenAgent = ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfo.getUserType());// 总代理
            boolean isAgent = ManagerStaff.USER_TYPE_AGENT.equals(userInfo.getUserType());// 代理
            boolean isSubChief = false;
            boolean isSubBranch = false;
            boolean isSubStockholder = false;
            boolean isSubGenAgent = false;
            boolean isSubAgent = false;
            if(subAccountInfo !=null){
                 isSubChief  = ManagerStaff.USER_TYPE_CHIEF.equals(subAccountInfo.getParentUserType());// 总监类型
                 isSubBranch = ManagerStaff.USER_TYPE_BRANCH.equals(subAccountInfo.getParentUserType());// 分公司类型
                 isSubStockholder = ManagerStaff.USER_TYPE_STOCKHOLDER.equals(subAccountInfo.getParentUserType());// 股东
                 isSubGenAgent = ManagerStaff.USER_TYPE_GEN_AGENT.equals(subAccountInfo.getParentUserType());// 总代理
                 isSubAgent = ManagerStaff.USER_TYPE_AGENT.equals(subAccountInfo.getParentUserType());// 代理
            }
            if(isChief  || isSubChief){
                Page<ChiefStaffExt> page = new Page<ChiefStaffExt>(10);
                
                int pageNo = 1;
                if (this.getRequest().getParameter("pageNo") != null)
                    pageNo = this.findParamInt("pageNo");
                page.setPageNo(pageNo);
                page.setOrderBy("managerStaffID");
                page.setOrder("desc");
                page = chiefStaffExtLogic.findSubPage(page, userInfo);
                this.getRequest().setAttribute("page", page);
                return SUCCESS;
            }else if(isBranch  || isSubBranch){
                Page<BranchStaffExt> page = new Page<BranchStaffExt>(10);
                int pageNo = 1;
                if (this.getRequest().getParameter("pageNo") != null)
                    pageNo = this.findParamInt("pageNo");
                page.setPageNo(pageNo);
                page.setOrderBy("managerStaffID");
                page.setOrder("desc");
                page =  branchStaffExtLogic.findSubPage(page,userInfo);
                this.getRequest().setAttribute("page", page);
                return SUCCESS;
            }else if(isStockholder  || isSubStockholder){
                Page<StockholderStaffExt> page = new Page<StockholderStaffExt>(10);
                int pageNo = 1;
                if (this.getRequest().getParameter("pageNo") != null)
                    pageNo = this.findParamInt("pageNo");
                page.setPageNo(pageNo);
                page.setOrderBy("managerStaffID");
                page.setOrder("desc");
                page = stockholderStaffExtLogic.findSubPage(page,userInfo);
                this.getRequest().setAttribute("page", page);
                return SUCCESS;
            }else if(isGenAgent || isSubGenAgent){
                Page<GenAgentStaffExt> page = new Page<GenAgentStaffExt>(10);
                int pageNo = 1;
                if (this.getRequest().getParameter("pageNo") != null)
                    pageNo = this.findParamInt("pageNo");
                page.setPageNo(pageNo);
                page.setOrderBy("managerStaffID");
                page.setOrder("desc");
                page = genAgentStaffExtLogic.findSubPage(page, userInfo);
                this.getRequest().setAttribute("page", page);
                return SUCCESS;
            }else if(isAgent  || isSubAgent){
                Page<AgentStaffExt> page = new Page<AgentStaffExt>(10);
                int pageNo = 1;
                if (this.getRequest().getParameter("pageNo") != null)
                    pageNo = this.findParamInt("pageNo");
                page.setPageNo(pageNo);
                page.setOrderBy("managerStaffID");
                page.setOrder("desc");
                page = agentStaffExtLogic.findSubPage(page, userInfo);
                this.getRequest().setAttribute("page", page);
                return SUCCESS;
            }
        } 
        getFailuere("查询子账号出错");
        return "failure";
    }
    

    
    public String savefindSubAccount(){
        userInfo = getInfo();
      //子帐号处理*********START
        ManagerUser userInfoNew = new ManagerUser();
        try {
        	BeanUtils.copyProperties(userInfoNew, userInfo);
        } catch (Exception e) {
          logger.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
        }
           if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
        	userInfoNew = getSubAccountParent(userInfoNew);	
           }	
        //子帐号处理*********END
           
        UserVO userVO =  commonUserLogic.getUserVo(userInfoNew);
 		getRequest().setAttribute("replenishMent",userVO.getReplenishMent());	//查询是否允许补货

        getRequest().setAttribute("userInfo", userInfoNew);
        return SUCCESS;
    }
    
    public String saveSubAccount(){
        SubAccountInfo subInfo = new SubAccountInfo();
        subInfo = subAccountInfoLogic.querySubAccountInfo("account",subAccountInfo.getAccount());
        ManagerUser userInfoOld = this.getCurrentManagerUser();
        //子帐号处理*********START
        ManagerUser userInfo = new ManagerUser();
        try {
        	BeanUtils.copyProperties(userInfo, userInfoOld);
        } catch (Exception e) {
        	logger.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
        }
           if(ManagerStaff.USER_TYPE_SUB.equals(userInfo.getUserType())){
        	   userInfo = getSubAccountParent(userInfo);	
           }	
        //子帐号处理*********END
        if(subInfo == null){
            boolean isChief = ManagerStaff.USER_TYPE_CHIEF.equals(userInfo.getUserType());// 总监类型
            boolean isBranch = ManagerStaff.USER_TYPE_BRANCH.equals(userInfo.getUserType());// 分公司类型
            boolean isStockholder = ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfo.getUserType());// 股东
            boolean isGenAgent = ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfo.getUserType());// 总代理
            boolean isAgent = ManagerStaff.USER_TYPE_AGENT.equals(userInfo.getUserType());// 代理
            if(isChief){
                ChiefStaffExt  chiExt= chiefStaffExtLogic.queryChiefStaffExt("managerStaffID", userInfo.getID());
                subAccountInfo.setChiefStaff(chiExt.getID());
                subAccountInfo.setParentStaff(chiExt.getID());
                subAccountInfo.setParentUserType(ManagerStaff.USER_TYPE_CHIEF);
                subAccountInfo.setParentStaffQry(chiExt.getID());
                subAccountInfo.setParentStaffTypeQry(ManagerStaff.USER_TYPE_CHIEF);
                
            }else if(isBranch){
                BranchStaffExt branExt = branchStaffExtLogic.queryBranchStaffExt("managerStaffID", userInfo.getID());
                subAccountInfo.setBranchStaff(branExt.getID());
                subAccountInfo.setChiefStaff(branExt.getChiefStaffExt().getID());
                subAccountInfo.setParentStaff(branExt.getID());
                subAccountInfo.setParentUserType(ManagerStaff.USER_TYPE_BRANCH);
                subAccountInfo.setParentStaffQry(branExt.getID());
                subAccountInfo.setParentStaffTypeQry(ManagerStaff.USER_TYPE_BRANCH);
            }else if(isStockholder){
                StockholderStaffExt stocaffExt = stockholderStaffExtLogic.queryStockholderStaffExt("managerStaffID", userInfo.getID());
                subAccountInfo.setBranchStaff(stocaffExt.getBranchStaffExt().getID());
                subAccountInfo.setChiefStaff(stocaffExt.getBranchStaffExt().getChiefStaffExt().getID());
                subAccountInfo.setStockholderStaff(stocaffExt.getID());
                subAccountInfo.setParentStaff(stocaffExt.getID());
                subAccountInfo.setParentUserType(ManagerStaff.USER_TYPE_STOCKHOLDER);
                subAccountInfo.setParentStaffQry(stocaffExt.getID());
                subAccountInfo.setParentStaffTypeQry(ManagerStaff.USER_TYPE_STOCKHOLDER);
            }else if(isGenAgent){
                GenAgentStaffExt genAgStfExt = genAgentStaffExtLogic.queryGenAgentStaffExt("managerStaffID", userInfo.getID());
                subAccountInfo.setBranchStaff(genAgStfExt.getStockholderStaffExt().getBranchStaffExt().getID());
                subAccountInfo.setChiefStaff(genAgStfExt.getStockholderStaffExt().getBranchStaffExt().getChiefStaffExt().getID());
                subAccountInfo.setStockholderStaff(genAgStfExt.getStockholderStaffExt().getID());
                subAccountInfo.setGenAgentStaff(genAgStfExt.getID());
                subAccountInfo.setParentStaff(genAgStfExt.getID());
                subAccountInfo.setParentUserType(ManagerStaff.USER_TYPE_GEN_AGENT);
                subAccountInfo.setParentStaffQry(genAgStfExt.getID());
                subAccountInfo.setParentStaffTypeQry(ManagerStaff.USER_TYPE_GEN_AGENT);
            }else if(isAgent){
                AgentStaffExt ageExt = agentStaffExtLogic.queryAgenStaffExt("managerStaffID", userInfo.getID());
                subAccountInfo.setBranchStaff(ageExt.getGenAgentStaffExt().getStockholderStaffExt().getBranchStaffExt().getID());
                subAccountInfo.setChiefStaff(ageExt.getGenAgentStaffExt().getStockholderStaffExt().getBranchStaffExt().getChiefStaffExt().getID());
                subAccountInfo.setStockholderStaff(ageExt.getGenAgentStaffExt().getStockholderStaffExt().getID());
                subAccountInfo.setGenAgentStaff(ageExt.getGenAgentStaffExt().getID());
                subAccountInfo.setParentStaff(ageExt.getID());
                subAccountInfo.setParentUserType(ManagerStaff.USER_TYPE_AGENT);
                subAccountInfo.setParentStaffQry(ageExt.getID());
                subAccountInfo.setParentStaffTypeQry(ManagerStaff.USER_TYPE_AGENT);
            }
            //MD5 md5 = new MD5();
            String userPwdOrignMd5 = subAccountInfo.getUserPwd();
            subAccountInfo.setUserPwd(userPwdOrignMd5);
            subAccountInfo.setCreateDate(new Date());
            subAccountInfo.setUserType(ManagerStaff.USER_TYPE_SUB);
            subAccountInfo.setFlag(ManagerStaff.FLAG_USE);
            SubAccountInfo subAccountAction = new SubAccountInfo();
            try {
            	//BeanUtils.copyProperties(subAccountInfo, subAccountAction);
            	BeanUtils.copyProperties(subAccountAction, subAccountInfo);
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
            try {
            	subAccountAction.setID(null);
                subAccountInfoLogic.saveSubAccountInfo(subAccountAction);
                ArrayList<String> newRoleNameList =new ArrayList<String>();
                if(isChief){
                    if(ManagerStaff.CHIEF_SUB_ROLE_REPLENISH.equals(replenishment)){
                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_REPLENISH);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_OFFLINE.equals(offLineAccount)){
                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_OFFLINE);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_SUB.equals(subAccount)){
                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_SUB);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_DELIVERY.equals(crossReport)){
                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_DELIVERY);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_CLASSIFY.equals(classifyReport)){
                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_CLASSIFY);
                        
                    }if(ManagerStaff.CHIEF_SUB_ROLE_ODD.equals(odd)){
                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_ODD);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_OUT_USER_MANAGER.equals(outReplenishManager)){
                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_OUT_USER_MANAGER);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_ODD_LOG.equals(oddLog)){
                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_ODD_LOG);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_SYS_INIT.equals(sysInit)){
                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_SYS_INIT);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_TRADING_SET.equals(tradingSet)){
                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_TRADING_SET);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_MESSAGE.equals(message)){
                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_MESSAGE);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_SEARCH_BILL.equals(searchBill)){
                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_SEARCH_BILL);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_BACKSYS_ROLE.equals(backsysRole)){
                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_BACKSYS_ROLE);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_CANCEL_BILL.equals(cancelBill)){
                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_CANCEL_BILL);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_JSZD.equals(jszd)){
                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_JSZD);
                    }
                    
                    authorizLogic.updateSubRole(subAccountAction.getID(), subAccountAction.getUserType(), newRoleNameList);
                }else if(isBranch){
                    if(ManagerStaff.BRANCH_SUB_ROLE_REPLENISH.equals(replenishment)){
                        newRoleNameList.add(ManagerStaff.BRANCH_SUB_ROLE_REPLENISH);
                    }if(ManagerStaff.BRANCH_SUB_ROLE_OFFLINE.equals(offLineAccount)){
                        newRoleNameList.add(ManagerStaff.BRANCH_SUB_ROLE_OFFLINE);
                    }if(ManagerStaff.BRANCH_SUB_ROLE_SUB.equals(subAccount)){
                        newRoleNameList.add(ManagerStaff.BRANCH_SUB_ROLE_SUB);
                    }if(ManagerStaff.BRANCH_SUB_ROLE_DELIVERY.equals(crossReport)){
                        newRoleNameList.add(ManagerStaff.BRANCH_SUB_ROLE_DELIVERY);
                    }if(ManagerStaff.BRANCH_SUB_ROLE_CLASSIFY.equals(classifyReport)){
                        newRoleNameList.add(ManagerStaff.BRANCH_SUB_ROLE_CLASSIFY);
                    }if(ManagerStaff.BRANCH_SUB_ROLE_JSZD.equals(jszd)){
                        newRoleNameList.add(ManagerStaff.BRANCH_SUB_ROLE_JSZD);
                    }
                    authorizLogic.updateSubRole(subAccountAction.getID(), subAccountAction.getUserType(), newRoleNameList);
                }else if(isStockholder){
                    if(ManagerStaff.STOCKHOLDER_SUB_ROLE_REPLENISH.equals(replenishment)){
                        newRoleNameList.add(ManagerStaff.STOCKHOLDER_SUB_ROLE_REPLENISH);
                    }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_OFFLINE.equals(offLineAccount)){
                        newRoleNameList.add(ManagerStaff.STOCKHOLDER_SUB_ROLE_OFFLINE);
                    }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_SUB.equals(subAccount)){
                        newRoleNameList.add(ManagerStaff.STOCKHOLDER_SUB_ROLE_SUB);
                    }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_DELIVERY.equals(crossReport)){
                        newRoleNameList.add(ManagerStaff.STOCKHOLDER_SUB_ROLE_DELIVERY);
                    }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_CLASSIFY.equals(classifyReport)){
                        newRoleNameList.add(ManagerStaff.STOCKHOLDER_SUB_ROLE_CLASSIFY);
                    }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_JSZD.equals(jszd)){
                        newRoleNameList.add(ManagerStaff.STOCKHOLDER_SUB_ROLE_JSZD);
                    }
                    authorizLogic.updateSubRole(subAccountAction.getID(), subAccountAction.getUserType(), newRoleNameList);
                }else if(isGenAgent){
                    if(ManagerStaff.GEN_AGENT_SUB_ROLE_REPLENISH.equals(replenishment)){
                        newRoleNameList.add(ManagerStaff.GEN_AGENT_SUB_ROLE_REPLENISH);
                    }if(ManagerStaff.GEN_AGENT_SUB_ROLE_OFFLINE.equals(offLineAccount)){
                        newRoleNameList.add(ManagerStaff.GEN_AGENT_SUB_ROLE_OFFLINE);
                    }if(ManagerStaff.GEN_AGENT_SUB_ROLE_SUB.equals(subAccount)){
                        newRoleNameList.add(ManagerStaff.GEN_AGENT_SUB_ROLE_SUB);
                    }if(ManagerStaff.GEN_AGENT_SUB_ROLE_DELIVERY.equals(crossReport)){
                        newRoleNameList.add(ManagerStaff.GEN_AGENT_SUB_ROLE_DELIVERY);
                    }if(ManagerStaff.GEN_AGENT_SUB_ROLE_CLASSIFY.equals(classifyReport)){
                        newRoleNameList.add(ManagerStaff.GEN_AGENT_SUB_ROLE_CLASSIFY);
                    }if(ManagerStaff.GEN_AGENT_SUB_ROLE_JSZD.equals(jszd)){
                        newRoleNameList.add(ManagerStaff.GEN_AGENT_SUB_ROLE_JSZD);
                    }
                    authorizLogic.updateSubRole(subAccountAction.getID(), subAccountAction.getUserType(), newRoleNameList);
                }else if(isAgent){
                    if(ManagerStaff.AGENT_SUB_ROLE_REPLENISH.equals(replenishment)){
                        newRoleNameList.add(ManagerStaff.AGENT_SUB_ROLE_REPLENISH);
                    }if(ManagerStaff.AGENT_SUB_ROLE_OFFLINE.equals(offLineAccount)){
                        newRoleNameList.add(ManagerStaff.AGENT_SUB_ROLE_OFFLINE);
                    }if(ManagerStaff.AGENT_SUB_ROLE_SUB.equals(subAccount)){
                        newRoleNameList.add(ManagerStaff.AGENT_SUB_ROLE_SUB);
                    }if(ManagerStaff.AGENT_SUB_ROLE_DELIVERY.equals(crossReport)){
                        newRoleNameList.add(ManagerStaff.AGENT_SUB_ROLE_DELIVERY);
                    }if(ManagerStaff.AGENT_SUB_ROLE_CLASSIFY.equals(classifyReport)){
                        newRoleNameList.add(ManagerStaff.AGENT_SUB_ROLE_CLASSIFY);
                    }if(ManagerStaff.AGENT_SUB_ROLE_JSZD.equals(jszd)){
                        newRoleNameList.add(ManagerStaff.AGENT_SUB_ROLE_JSZD);
                    }
                    authorizLogic.updateSubRole(subAccountAction.getID(), subAccountAction.getUserType(), newRoleNameList);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("执行" + this.getClass().getSimpleName()
                        + "中的方法saveSubAccount时出现错误 "
                        + e.getMessage());
            }
        }
        return SUCCESS;
    }
    
    public String updateFindSubAccount(){
       /* String qUserID = getRequest().getParameter("qUserID");
        Long userId = (long) 0;
        if (null != qUserID && 0 != qUserID.trim().length()) {
        	qUserID=Tools.decodeWithKey(qUserID);
        	userId = Long.valueOf(qUserID);
        }*/
    	//ManagerUser userInfo = this.getCurrentManagerUser();
    	
    	Long userId = (long) 0;
    	String qUserID = getRequest().getParameter("qUserID");
    	if (null != qUserID && 0 != qUserID.trim().length()) {
        	qUserID=Tools.decodeWithKey(qUserID);
        	userId = Long.valueOf(qUserID);
    	}
        subAccountInfo = subAccountInfoLogic.querySubAccountInfo("managerStaffID",userId);
        List<String> authoriz = new ArrayList<String>();
        authoriz = authorizLogic.findSubRole(subAccountInfo.getID(),subAccountInfo.getUserType());
        if(ManagerStaff.USER_TYPE_CHIEF.equals(subAccountInfo.getParentUserType())){
            for (String authority : authoriz) {
                if(ManagerStaff.CHIEF_SUB_ROLE_REPLENISH.equals(authority)){
                    updateReplenishment = authority;
                    replenishment = ManagerStaff.CHIEF_SUB_ROLE_REPLENISH;
                }if(ManagerStaff.CHIEF_SUB_ROLE_OFFLINE.equals(authority)){
                    updateOffLineAccount = authority;
                    offLineAccount = ManagerStaff.CHIEF_SUB_ROLE_OFFLINE;
                }if(ManagerStaff.CHIEF_SUB_ROLE_SUB.equals(authority)){
                    updateSubAccount = authority;
                    subAccount = ManagerStaff.CHIEF_SUB_ROLE_SUB;
                }if(ManagerStaff.CHIEF_SUB_ROLE_DELIVERY.equals(authority)){
                    updateCrossReport = authority;
                    crossReport = ManagerStaff.CHIEF_SUB_ROLE_DELIVERY;
                }if(ManagerStaff.CHIEF_SUB_ROLE_CLASSIFY.equals(authority)){
                    updateClassifyReport = authority;
                    classifyReport = ManagerStaff.CHIEF_SUB_ROLE_CLASSIFY;
                }
                
                if(ManagerStaff.CHIEF_SUB_ROLE_ODD.equals(authority)){
                	updateOdd = authority;
                	odd = ManagerStaff.CHIEF_SUB_ROLE_ODD;
                }if(ManagerStaff.CHIEF_SUB_ROLE_OUT_USER_MANAGER.equals(authority)){
                	updateOutReplenishManager = authority;
                	outReplenishManager = ManagerStaff.CHIEF_SUB_ROLE_OUT_USER_MANAGER;
                }if(ManagerStaff.CHIEF_SUB_ROLE_ODD_LOG.equals(authority)){
                	updateOddLog = authority;
                	oddLog = ManagerStaff.CHIEF_SUB_ROLE_ODD_LOG;
                }if(ManagerStaff.CHIEF_SUB_ROLE_SYS_INIT.equals(authority)){
                	updateSysInit = authority;
                	sysInit = ManagerStaff.CHIEF_SUB_ROLE_SYS_INIT;
                }if(ManagerStaff.CHIEF_SUB_ROLE_TRADING_SET.equals(authority)){
                	updateTradingSet = authority;
                	tradingSet = ManagerStaff.CHIEF_SUB_ROLE_TRADING_SET;
                }if(ManagerStaff.CHIEF_SUB_ROLE_MESSAGE.equals(authority)){
                	updateMessage = authority;
                	message = ManagerStaff.CHIEF_SUB_ROLE_MESSAGE;
                }if(ManagerStaff.CHIEF_SUB_ROLE_SEARCH_BILL.equals(authority)){
                	updateSearchBill = authority;
                	searchBill = ManagerStaff.CHIEF_SUB_ROLE_SEARCH_BILL;
                }if(ManagerStaff.CHIEF_SUB_ROLE_BACKSYS_ROLE.equals(authority)){
                	updateBacksysRole = authority;
                }if(ManagerStaff.CHIEF_SUB_ROLE_CANCEL_BILL.equals(authority)){
                	updateCancelBill = authority;
                }if(ManagerStaff.CHIEF_SUB_ROLE_JSZD.equals(authority)){
                	updateJszd = authority;
                	jszd = ManagerStaff.CHIEF_SUB_ROLE_JSZD;
                }
                
            }
        }else if(ManagerStaff.USER_TYPE_BRANCH.equals(subAccountInfo.getParentUserType())){
            for (String authority : authoriz) {
                if(ManagerStaff.BRANCH_SUB_ROLE_REPLENISH.equals(authority)){
                    updateReplenishment = authority;
                    replenishment = ManagerStaff.BRANCH_SUB_ROLE_REPLENISH;
                }if(ManagerStaff.BRANCH_SUB_ROLE_OFFLINE.equals(authority)){
                    updateOffLineAccount = authority;
                    offLineAccount = ManagerStaff.BRANCH_SUB_ROLE_OFFLINE;
                }if(ManagerStaff.BRANCH_SUB_ROLE_SUB.equals(authority)){
                    updateSubAccount = authority;
                    subAccount = ManagerStaff.BRANCH_SUB_ROLE_SUB;
                }if(ManagerStaff.BRANCH_SUB_ROLE_DELIVERY.equals(authority)){
                    updateCrossReport = authority;
                    crossReport = ManagerStaff.BRANCH_SUB_ROLE_DELIVERY;
                }if(ManagerStaff.BRANCH_SUB_ROLE_CLASSIFY.equals(authority)){
                    updateClassifyReport = authority;
                    classifyReport = ManagerStaff.BRANCH_SUB_ROLE_CLASSIFY;
                }if(ManagerStaff.BRANCH_SUB_ROLE_JSZD.equals(authority)){
                	updateJszd = authority;
                	jszd = ManagerStaff.BRANCH_SUB_ROLE_JSZD;
                }
            }
            
        }else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(subAccountInfo.getParentUserType())){
            for (String authority : authoriz) {
                if(ManagerStaff.STOCKHOLDER_SUB_ROLE_REPLENISH.equals(authority)){
                    updateReplenishment = authority;
                    replenishment = ManagerStaff.STOCKHOLDER_SUB_ROLE_REPLENISH;
                }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_OFFLINE.equals(authority)){
                    updateOffLineAccount = authority;
                    offLineAccount = ManagerStaff.STOCKHOLDER_SUB_ROLE_OFFLINE;
                }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_SUB.equals(authority)){
                    updateSubAccount = authority;
                    subAccount = ManagerStaff.STOCKHOLDER_SUB_ROLE_SUB;
                }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_DELIVERY.equals(authority)){
                    updateCrossReport = authority;
                    crossReport = ManagerStaff.STOCKHOLDER_SUB_ROLE_DELIVERY;
                }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_CLASSIFY.equals(authority)){
                    updateClassifyReport = authority;
                    classifyReport = ManagerStaff.STOCKHOLDER_SUB_ROLE_CLASSIFY;
                }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_JSZD.equals(authority)){
                	updateJszd = authority;
                	jszd = ManagerStaff.STOCKHOLDER_SUB_ROLE_JSZD;
                }
            }
            
        }else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(subAccountInfo.getParentUserType())){
            for (String authority : authoriz) {
                if(ManagerStaff.GEN_AGENT_SUB_ROLE_REPLENISH.equals(authority)){
                    updateReplenishment = authority;
                    replenishment = ManagerStaff.GEN_AGENT_SUB_ROLE_REPLENISH;
                }if(ManagerStaff.GEN_AGENT_SUB_ROLE_OFFLINE.equals(authority)){
                    updateOffLineAccount = authority;
                    offLineAccount = ManagerStaff.GEN_AGENT_SUB_ROLE_OFFLINE;
                }if(ManagerStaff.GEN_AGENT_SUB_ROLE_SUB.equals(authority)){
                    updateSubAccount = authority;
                    subAccount = ManagerStaff.GEN_AGENT_SUB_ROLE_SUB;
                }if(ManagerStaff.GEN_AGENT_SUB_ROLE_DELIVERY.equals(authority)){
                    updateCrossReport = authority;
                    crossReport = ManagerStaff.GEN_AGENT_SUB_ROLE_DELIVERY;
                }if(ManagerStaff.GEN_AGENT_SUB_ROLE_CLASSIFY.equals(authority)){
                    updateClassifyReport = authority;
                    classifyReport = ManagerStaff.GEN_AGENT_SUB_ROLE_CLASSIFY;
                }if(ManagerStaff.GEN_AGENT_SUB_ROLE_JSZD.equals(authority)){
                	updateJszd = authority;
                	jszd = ManagerStaff.GEN_AGENT_SUB_ROLE_JSZD;
                }
            }
            
        }else if(ManagerStaff.USER_TYPE_AGENT.equals(subAccountInfo.getParentUserType())){
            for (String authority : authoriz) {
                if(ManagerStaff.AGENT_SUB_ROLE_REPLENISH.equals(authority)){
                    updateReplenishment = authority;
                    replenishment = ManagerStaff.AGENT_SUB_ROLE_REPLENISH;
                }if(ManagerStaff.AGENT_SUB_ROLE_OFFLINE.equals(authority)){
                    updateOffLineAccount = authority;
                    offLineAccount = ManagerStaff.AGENT_SUB_ROLE_OFFLINE;
                }if(ManagerStaff.AGENT_SUB_ROLE_SUB.equals(authority)){
                    updateSubAccount = authority;
                    subAccount = ManagerStaff.AGENT_SUB_ROLE_SUB;
                }if(ManagerStaff.AGENT_SUB_ROLE_DELIVERY.equals(authority)){
                    updateCrossReport = authority;
                    crossReport = ManagerStaff.AGENT_SUB_ROLE_DELIVERY;
                }if(ManagerStaff.AGENT_SUB_ROLE_CLASSIFY.equals(authority)){
                    updateClassifyReport = authority;
                    classifyReport = ManagerStaff.AGENT_SUB_ROLE_CLASSIFY;
                }if(ManagerStaff.AGENT_SUB_ROLE_JSZD.equals(authority)){
                	updateJszd = authority;
                	jszd = ManagerStaff.AGENT_SUB_ROLE_JSZD;
                }
            }
        }
        
  //查看主帐号的补货权限      start
        ManagerUser userInfo = this.getCurrentManagerUser();
      //子帐号处理*********START
        ManagerUser userInfoNew = new ManagerUser();
        try {
        	BeanUtils.copyProperties(userInfoNew, userInfo);
        } catch (Exception e) {
        	logger.info("转换userInfoSys里出错"+ e.getMessage());
        }
           if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
        	userInfoNew = getSubAccountParent(userInfoNew);	
           }	
        //子帐号处理*********END
        UserVO userVO =  commonUserLogic.getUserVo(userInfoNew);
 		getRequest().setAttribute("replenishMent",userVO.getReplenishMent());	//查询是否允许补货   
           
         if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfo.getUserType())){
        	   BranchStaffExt db =  branchStaffExtLogic.queryBranchStaffExt("managerStaffID",userInfo.getID()); 
        	   getRequest().setAttribute("replenishMent",db.getReplenishment());
  		 }
  		 if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(userInfo.getUserType())){
  			 StockholderStaffExt db =  stockholderStaffExtLogic.queryStockholderStaffExt("managerStaffID",userInfo.getID()); // 判断是否存在相同用户信息
  			 getRequest().setAttribute("replenishMent",db.getReplenishment());
  		 }
  		 if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(userInfo.getUserType())){
  			 GenAgentStaffExt db =  genAgentStaffExtLogic.queryGenAgentStaffExt("managerStaffID",userInfo.getID()); // 判断是否存在相同用户信息
  			 getRequest().setAttribute("replenishMent",db.getReplenishment());
  		 }
  		 if(ManagerStaff.USER_TYPE_AGENT.equals(userInfo.getUserType())){
  			 AgentStaffExt db =  agentStaffExtLogic.queryAgenStaffExt("managerStaffID",userInfo.getID()); // 判断是否存在相同用户信息
  			 getRequest().setAttribute("replenishMent",db.getReplenishment());
  		 }   
   //查看主帐号的补货权限          end  
        
        getRequest().setAttribute("subAccountInfo",subAccountInfo);
        getRequest().setAttribute("updateReplenishment",updateReplenishment);
        getRequest().setAttribute("updateOffLineAccount",updateOffLineAccount);
        getRequest().setAttribute("updateSubAccount",updateSubAccount);
        getRequest().setAttribute("updateCrossReport",updateCrossReport);
        getRequest().setAttribute("updateClassifyReport",updateClassifyReport);
        
        getRequest().setAttribute("odd",updateOdd);
        getRequest().setAttribute("outReplenishManager",updateOutReplenishManager);
        getRequest().setAttribute("oddLog",updateOddLog);
        getRequest().setAttribute("sysInit",updateSysInit);
        getRequest().setAttribute("tradingSet",updateTradingSet);
        getRequest().setAttribute("message",updateMessage);
        getRequest().setAttribute("searchBill",updateSearchBill);
        getRequest().setAttribute("backsysRole",updateBacksysRole);
        getRequest().setAttribute("cancelBill",updateCancelBill);
        getRequest().setAttribute("jszd",updateJszd);
        
        return SUCCESS;
    }
    
    public String updateSubAccount(){
        SubAccountInfo subInfo =  new SubAccountInfo();
        subInfo = subAccountInfoLogic.querySubAccountInfo("account",subAccountInfo.getAccount());
        if(subInfo != null){
            if(StringUtils.isEmpty(subAccountInfo.getUserPwd())){
                subAccountInfo.setUserPwd(subInfo.getUserPwd());
                //add by peter
                subAccountInfo.setPasswordUpdateDate(subInfo.getPasswordUpdateDate());
                subAccountInfo.setPasswordResetFlag(subInfo.getPasswordResetFlag());
            }else{
                //MD5 md5 = new MD5();
                String userPwdOrignMd5 = subAccountInfo.getUserPwd().trim();
                        
                subAccountInfo.setUserPwd(userPwdOrignMd5);
                //add by peter
                subAccountInfo.setPasswordUpdateDate(new Date());
                subAccountInfo.setPasswordResetFlag("Y");
            }
                subAccountInfo.setCreateDate(subInfo.getCreateDate());
                subAccountInfo.setUpdateDate(new Date());
                subAccountInfo.setID(subInfo.getID());
                subAccountInfo.setFlag(subInfo.getFlag());
                subAccountInfo.setUserType(subInfo.getUserType());
                subAccountInfo.setBranchStaff(subInfo.getBranchStaff());
                subAccountInfo.setChiefStaff(subInfo.getChiefStaff());
                subAccountInfo.setStockholderStaff(subInfo.getStockholderStaff());
                subAccountInfo.setParentUserType(subInfo.getParentUserType());
                subAccountInfo.setParentStaff(subInfo.getParentStaff());
                subAccountInfo.setGenAgentStaff(subInfo.getGenAgentStaff());
               
                try {
                    SubAccountInfo subAccountAction = new SubAccountInfo();
                    BeanUtils.copyProperties(subAccountAction, subAccountInfo); 
                    subAccountInfoLogic.updateSubAccountInfo(subAccountAction);
                    ArrayList<String> newRoleNameList =new ArrayList<String>();
                    if(ManagerStaff.USER_TYPE_CHIEF.equals(subAccountAction.getParentUserType())){
                        if(ManagerStaff.CHIEF_SUB_ROLE_REPLENISH.equals(replenishment)){
                            newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_REPLENISH);
                        }if(ManagerStaff.CHIEF_SUB_ROLE_OFFLINE.equals(offLineAccount)){
                            newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_OFFLINE);
                        }if(ManagerStaff.CHIEF_SUB_ROLE_SUB.equals(subAccount)){
                            newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_SUB);
                        }if(ManagerStaff.CHIEF_SUB_ROLE_DELIVERY.equals(crossReport)){
                            newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_DELIVERY);
                        }if(ManagerStaff.CHIEF_SUB_ROLE_CLASSIFY.equals(classifyReport)){
                            newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_CLASSIFY);
                        }
                        
	                    if(ManagerStaff.CHIEF_SUB_ROLE_ODD.equals(odd)){
	                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_ODD);
	                    }if(ManagerStaff.CHIEF_SUB_ROLE_OUT_USER_MANAGER.equals(outReplenishManager)){
	                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_OUT_USER_MANAGER);
	                    }if(ManagerStaff.CHIEF_SUB_ROLE_ODD_LOG.equals(oddLog)){
	                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_ODD_LOG);
	                    }if(ManagerStaff.CHIEF_SUB_ROLE_SYS_INIT.equals(sysInit)){
	                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_SYS_INIT);
	                    }if(ManagerStaff.CHIEF_SUB_ROLE_TRADING_SET.equals(tradingSet)){
	                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_TRADING_SET);
	                    }if(ManagerStaff.CHIEF_SUB_ROLE_MESSAGE.equals(message)){
	                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_MESSAGE);
	                    }if(ManagerStaff.CHIEF_SUB_ROLE_SEARCH_BILL.equals(searchBill)){
	                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_SEARCH_BILL);
	                    }if(ManagerStaff.CHIEF_SUB_ROLE_BACKSYS_ROLE.equals(backsysRole)){
	                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_BACKSYS_ROLE);
	                    }if(ManagerStaff.CHIEF_SUB_ROLE_CANCEL_BILL.equals(cancelBill)){
	                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_CANCEL_BILL);
	                    }if(ManagerStaff.CHIEF_SUB_ROLE_JSZD.equals(jszd)){
	                        newRoleNameList.add(ManagerStaff.CHIEF_SUB_ROLE_JSZD);
	                    }
                        
                        authorizLogic.updateSubRole(subAccountAction.getID(), subAccountAction.getUserType(), newRoleNameList);  
                    }else if(ManagerStaff.USER_TYPE_BRANCH.equals(subAccountAction.getParentUserType())){
                        if(ManagerStaff.BRANCH_SUB_ROLE_REPLENISH.equals(replenishment)){
                            newRoleNameList.add(ManagerStaff.BRANCH_SUB_ROLE_REPLENISH);
                        }if(ManagerStaff.BRANCH_SUB_ROLE_OFFLINE.equals(offLineAccount)){
                            newRoleNameList.add(ManagerStaff.BRANCH_SUB_ROLE_OFFLINE);
                        }if(ManagerStaff.BRANCH_SUB_ROLE_SUB.equals(subAccount)){
                            newRoleNameList.add(ManagerStaff.BRANCH_SUB_ROLE_SUB);
                        }if(ManagerStaff.BRANCH_SUB_ROLE_DELIVERY.equals(crossReport)){
                            newRoleNameList.add(ManagerStaff.BRANCH_SUB_ROLE_DELIVERY);
                        }if(ManagerStaff.BRANCH_SUB_ROLE_CLASSIFY.equals(classifyReport)){
                            newRoleNameList.add(ManagerStaff.BRANCH_SUB_ROLE_CLASSIFY);
                        }if(ManagerStaff.BRANCH_SUB_ROLE_JSZD.equals(jszd)){
	                        newRoleNameList.add(ManagerStaff.BRANCH_SUB_ROLE_JSZD);
	                    }
                        authorizLogic.updateSubRole(subAccountAction.getID(), subAccountAction.getUserType(), newRoleNameList);  
                    }else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(subAccountAction.getParentUserType())){
                        if(ManagerStaff.STOCKHOLDER_SUB_ROLE_REPLENISH.equals(replenishment)){
                            newRoleNameList.add(ManagerStaff.STOCKHOLDER_SUB_ROLE_REPLENISH);
                        }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_OFFLINE.equals(offLineAccount)){
                            newRoleNameList.add(ManagerStaff.STOCKHOLDER_SUB_ROLE_OFFLINE);
                        }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_SUB.equals(subAccount)){
                            newRoleNameList.add(ManagerStaff.STOCKHOLDER_SUB_ROLE_SUB);
                        }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_DELIVERY.equals(crossReport)){
                            newRoleNameList.add(ManagerStaff.STOCKHOLDER_SUB_ROLE_DELIVERY);
                        }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_CLASSIFY.equals(classifyReport)){
                            newRoleNameList.add(ManagerStaff.STOCKHOLDER_SUB_ROLE_CLASSIFY);
                        }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_JSZD.equals(jszd)){
	                        newRoleNameList.add(ManagerStaff.STOCKHOLDER_SUB_ROLE_JSZD);
	                    }
                        authorizLogic.updateSubRole(subAccountAction.getID(), subAccountAction.getUserType(), newRoleNameList);  
                    }else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(subAccountAction.getParentUserType())){
                        if(ManagerStaff.GEN_AGENT_SUB_ROLE_REPLENISH.equals(replenishment)){
                            newRoleNameList.add(ManagerStaff.GEN_AGENT_SUB_ROLE_REPLENISH);
                        }if(ManagerStaff.GEN_AGENT_SUB_ROLE_OFFLINE.equals(offLineAccount)){
                            newRoleNameList.add(ManagerStaff.GEN_AGENT_SUB_ROLE_OFFLINE);
                        }if(ManagerStaff.GEN_AGENT_SUB_ROLE_SUB.equals(subAccount)){
                            newRoleNameList.add(ManagerStaff.GEN_AGENT_SUB_ROLE_SUB);
                        }if(ManagerStaff.GEN_AGENT_SUB_ROLE_DELIVERY.equals(crossReport)){
                            newRoleNameList.add(ManagerStaff.GEN_AGENT_SUB_ROLE_DELIVERY);
                        }if(ManagerStaff.GEN_AGENT_SUB_ROLE_CLASSIFY.equals(classifyReport)){
                            newRoleNameList.add(ManagerStaff.GEN_AGENT_SUB_ROLE_CLASSIFY);
                        }if(ManagerStaff.GEN_AGENT_SUB_ROLE_JSZD.equals(jszd)){
	                        newRoleNameList.add(ManagerStaff.GEN_AGENT_SUB_ROLE_JSZD);
	                    }
                        authorizLogic.updateSubRole(subAccountAction.getID(), subAccountAction.getUserType(), newRoleNameList);  
                    }else if(ManagerStaff.USER_TYPE_AGENT.equals(subAccountAction.getParentUserType())){
                        if(ManagerStaff.AGENT_SUB_ROLE_REPLENISH.equals(replenishment)){
                            newRoleNameList.add(ManagerStaff.AGENT_SUB_ROLE_REPLENISH);
                        }if(ManagerStaff.AGENT_SUB_ROLE_OFFLINE.equals(offLineAccount)){
                            newRoleNameList.add(ManagerStaff.AGENT_SUB_ROLE_OFFLINE);
                        }if(ManagerStaff.AGENT_SUB_ROLE_SUB.equals(subAccount)){
                            newRoleNameList.add(ManagerStaff.AGENT_SUB_ROLE_SUB);
                        }if(ManagerStaff.AGENT_SUB_ROLE_DELIVERY.equals(crossReport)){
                            newRoleNameList.add(ManagerStaff.AGENT_SUB_ROLE_DELIVERY);
                        }if(ManagerStaff.AGENT_SUB_ROLE_CLASSIFY.equals(classifyReport)){
                            newRoleNameList.add(ManagerStaff.AGENT_SUB_ROLE_CLASSIFY);
                        }if(ManagerStaff.AGENT_SUB_ROLE_JSZD.equals(jszd)){
	                        newRoleNameList.add(ManagerStaff.AGENT_SUB_ROLE_JSZD);
	                    }
                        authorizLogic.updateSubRole(subAccountAction.getID(), subAccountAction.getUserType(), newRoleNameList);  
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("执行" + this.getClass().getSimpleName()
                            + "中的方法updateSubAccount时出现错误 "
                            + e.getMessage());
                }
                return SUCCESS;
        }
        getRequest().setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
        getRequest().setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                + Constant.COLOR_RED + "'>子账号修改失败！</font>");
        return "failure";
    }
    public String delSubAccount(){
        SubAccountInfo subInfo =  new SubAccountInfo();
        String account = getRequest().getParameter("account");
        subInfo = subAccountInfoLogic.querySubAccountInfo("account",account);
        if(subInfo != null){
            subInfo.setFlag(ManagerStaff.FLAG_DELETE);
            try {
                subAccountInfoLogic.updateSubAccountInfo(subInfo);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("执行" + this.getClass().getSimpleName()
                        + "中的方法updateSubAccount时出现错误 "
                        + e.getMessage());
            }
            getFailuere("删除成功");
            return SUCCESS;
        }
        getFailuere("删除失败");
        return "failure";
    }

    
    public IChiefStaffExtLogic getChiefStaffExtLogic() {
        return chiefStaffExtLogic;
    }

    public void setChiefStaffExtLogic(IChiefStaffExtLogic chiefStaffExtLogic) {
        this.chiefStaffExtLogic = chiefStaffExtLogic;
    }

    public IBranchStaffExtLogic getBranchStaffExtLogic() {
        return branchStaffExtLogic;
    }

    public void setBranchStaffExtLogic(IBranchStaffExtLogic branchStaffExtLogic) {
        this.branchStaffExtLogic = branchStaffExtLogic;
    }

    public IGenAgentStaffExtLogic getGenAgentStaffExtLogic() {
        return genAgentStaffExtLogic;
    }

    public void setGenAgentStaffExtLogic(
            IGenAgentStaffExtLogic genAgentStaffExtLogic) {
        this.genAgentStaffExtLogic = genAgentStaffExtLogic;
    }

    public IAgentStaffExtLogic getAgentStaffExtLogic() {
        return agentStaffExtLogic;
    }

    public void setAgentStaffExtLogic(IAgentStaffExtLogic agentStaffExtLogic) {
        this.agentStaffExtLogic = agentStaffExtLogic;
    }

    public IStockholderStaffExtLogic getStockholderStaffExtLogic() {
        return stockholderStaffExtLogic;
    }

    public void setStockholderStaffExtLogic(
            IStockholderStaffExtLogic stockholderStaffExtLogic) {
        this.stockholderStaffExtLogic = stockholderStaffExtLogic;
    }

    public ManagerUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ManagerUser userInfo) {
        this.userInfo = userInfo;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public ChiefStaffExt getChiefStaffExt() {
        return chiefStaffExt;
    }


    public void setChiefStaffExt(ChiefStaffExt chiefStaffExt) {
        this.chiefStaffExt = chiefStaffExt;
    }


    public BranchStaffExt getBranchStaffExt() {
        return branchStaffExt;
    }


    public void setBranchStaffExt(BranchStaffExt branchStaffExt) {
        this.branchStaffExt = branchStaffExt;
    }


    public StockholderStaffExt getStockholderStaffExt() {
        return stockholderStaffExt;
    }


    public void setStockholderStaffExt(StockholderStaffExt stockholderStaffExt) {
        this.stockholderStaffExt = stockholderStaffExt;
    }


    public GenAgentStaffExt getGenAgentStaffExt() {
        return genAgentStaffExt;
    }


    public void setGenAgentStaffExt(GenAgentStaffExt genAgentStaffExt) {
        this.genAgentStaffExt = genAgentStaffExt;
    }


    public AgentStaffExt getAgentStaffExt() {
        return agentStaffExt;
    }


    public void setAgentStaffExt(AgentStaffExt agentStaffExt) {
        this.agentStaffExt = agentStaffExt;
    }
    public SubAccountInfo getSubAccountInfo() {
        return subAccountInfo;
    }
    public void setSubAccountInfo(SubAccountInfo subAccountInfo) {
        this.subAccountInfo = subAccountInfo;
    }

    public IAuthorizLogic getAuthorizLogic() {
        return authorizLogic;
    }

    public void setAuthorizLogic(IAuthorizLogic authorizLogic) {
        this.authorizLogic = authorizLogic;
    }

    public String getReplenishment() {
        return replenishment;
    }

    public void setReplenishment(String replenishment) {
        this.replenishment = replenishment;
    }

    public String getOffLineAccount() {
        return offLineAccount;
    }

    public void setOffLineAccount(String offLineAccount) {
        this.offLineAccount = offLineAccount;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getCrossReport() {
        return crossReport;
    }

    public void setCrossReport(String crossReport) {
        this.crossReport = crossReport;
    }

    public String getClassifyReport() {
        return classifyReport;
    }

    public void setClassifyReport(String classifyReport) {
        this.classifyReport = classifyReport;
    }

    public String getOdd() {
		return odd;
	}






	public String getOutReplenishManager() {
		return outReplenishManager;
	}



	public String getOddLog() {
		return oddLog;
	}



	public String getSysInit() {
		return sysInit;
	}



	public String getTradingSet() {
		return tradingSet;
	}



	public String getMessage() {
		return message;
	}



	public String getSearchBill() {
		return searchBill;
	}



	public String getBacksysRole() {
		return backsysRole;
	}



	public String getCancelBill() {
		return cancelBill;
	}



	public String getJszd() {
		return jszd;
	}



	public void setOdd(String odd) {
		this.odd = odd;
	}



	public void setOutReplenishManager(String outReplenishManager) {
		this.outReplenishManager = outReplenishManager;
	}



	public void setOddLog(String oddLog) {
		this.oddLog = oddLog;
	}



	public void setSysInit(String sysInit) {
		this.sysInit = sysInit;
	}



	public void setTradingSet(String tradingSet) {
		this.tradingSet = tradingSet;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public void setSearchBill(String searchBill) {
		this.searchBill = searchBill;
	}



	public void setBacksysRole(String backsysRole) {
		this.backsysRole = backsysRole;
	}



	public void setCancelBill(String cancelBill) {
		this.cancelBill = cancelBill;
	}



	public void setJszd(String jszd) {
		this.jszd = jszd;
	}



	public String getUpdateReplenishment() {
        return updateReplenishment;
    }

    public void setUpdateReplenishment(String updateReplenishment) {
        this.updateReplenishment = updateReplenishment;
    }

    public String getUpdateOffLineAccount() {
        return updateOffLineAccount;
    }

    public void setUpdateOffLineAccount(String updateOffLineAccount) {
        this.updateOffLineAccount = updateOffLineAccount;
    }

    public String getUpdateSubAccount() {
        return updateSubAccount;
    }

    public void setUpdateSubAccount(String updateSubAccount) {
        this.updateSubAccount = updateSubAccount;
    }

    public String getUpdateCrossReport() {
        return updateCrossReport;
    }

    public void setUpdateCrossReport(String updateCrossReport) {
        this.updateCrossReport = updateCrossReport;
    }

    public String getUpdateClassifyReport() {
        return updateClassifyReport;
    }

    public void setUpdateClassifyReport(String updateClassifyReport) {
        this.updateClassifyReport = updateClassifyReport;
    }
    public String getUpdateOdd() {
		return updateOdd;
	}

	public String getUpdateOutReplenishManager() {
		return updateOutReplenishManager;
	}



	public String getUpdateOddLog() {
		return updateOddLog;
	}



	public String getUpdateSysInit() {
		return updateSysInit;
	}



	public String getUpdateTradingSet() {
		return updateTradingSet;
	}



	public String getUpdateMessage() {
		return updateMessage;
	}



	public String getUpdateSearchBill() {
		return updateSearchBill;
	}



	public String getUpdateBacksysRole() {
		return updateBacksysRole;
	}



	public String getUpdateCancelBill() {
		return updateCancelBill;
	}



	public String getUpdateJszd() {
		return updateJszd;
	}



	public void setUpdateOdd(String updateOdd) {
		this.updateOdd = updateOdd;
	}

	public void setUpdateOutReplenishManager(String updateOutReplenishManager) {
		this.updateOutReplenishManager = updateOutReplenishManager;
	}



	public void setUpdateOddLog(String updateOddLog) {
		this.updateOddLog = updateOddLog;
	}



	public void setUpdateSysInit(String updateSysInit) {
		this.updateSysInit = updateSysInit;
	}



	public void setUpdateTradingSet(String updateTradingSet) {
		this.updateTradingSet = updateTradingSet;
	}



	public void setUpdateMessage(String updateMessage) {
		this.updateMessage = updateMessage;
	}



	public void setUpdateSearchBill(String updateSearchBill) {
		this.updateSearchBill = updateSearchBill;
	}



	public void setUpdateBacksysRole(String updateBacksysRole) {
		this.updateBacksysRole = updateBacksysRole;
	}



	public void setUpdateCancelBill(String updateCancelBill) {
		this.updateCancelBill = updateCancelBill;
	}



	public void setUpdateJszd(String updateJszd) {
		this.updateJszd = updateJszd;
	}



	public Map<String,String> getAutoSub(SubAccountInfo subAccountInfo){
        Map<String,String> autoSubMap = new HashMap<String, String>();
        if(subAccountInfo != null){
            List<String> authoriz = new ArrayList<String>();
            authoriz = authorizLogic.findSubRole(subAccountInfo.getID(),subAccountInfo.getUserType());
            if(ManagerStaff.USER_TYPE_CHIEF.equals(subAccountInfo.getParentUserType())){
                for (String authority : authoriz) {
                    if(ManagerStaff.CHIEF_SUB_ROLE_REPLENISH.equals(authority)){
                        replenishment = authority;
                        autoSubMap.put("replenishment",replenishment);
                    }else{autoSubMap.put("replenishment",replenishment);}
                    if(ManagerStaff.CHIEF_SUB_ROLE_OFFLINE.equals(authority)){
                        offLineAccount = authority;
                        autoSubMap.put("offLineAccount",offLineAccount);
                    }else{autoSubMap.put("offLineAccount",offLineAccount);}
                    if(ManagerStaff.CHIEF_SUB_ROLE_SUB.equals(authority)){
                        subAccount = authority;
                        autoSubMap.put("subAccount",subAccount);
                    }else{autoSubMap.put("subAccount",subAccount);}
                    if(ManagerStaff.CHIEF_SUB_ROLE_DELIVERY.equals(authority)){
                        crossReport = authority;
                        autoSubMap.put("crossReport",crossReport);
                    }else{autoSubMap.put("crossReport",crossReport);}
                    if(ManagerStaff.CHIEF_SUB_ROLE_CLASSIFY.equals(authority)){
                        classifyReport = authority;
                        autoSubMap.put("classifyReport",classifyReport);
                    }else{autoSubMap.put("classifyReport",classifyReport);}
                    
                    
                    if(ManagerStaff.CHIEF_SUB_ROLE_ODD.equals(authority)){
                    	odd = authority;
                    	autoSubMap.put("odd",odd);
                    }else{autoSubMap.put("odd",odd);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_OUT_USER_MANAGER.equals(authority)){
                    	outReplenishManager = authority;
                    	autoSubMap.put("outReplenishManager",outReplenishManager);
                    }else{autoSubMap.put("outReplenishManager",outReplenishManager);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_ODD_LOG.equals(authority)){
                    	oddLog = authority;
                    	autoSubMap.put("oddLog",oddLog);
                    }else{autoSubMap.put("oddLog",oddLog);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_SYS_INIT.equals(authority)){
                    	sysInit = authority;
                    	autoSubMap.put("sysInit",sysInit);
                    }else{autoSubMap.put("sysInit",sysInit);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_TRADING_SET.equals(authority)){
                    	tradingSet = authority;
                    	autoSubMap.put("tradingSet",tradingSet);
                    }else{autoSubMap.put("tradingSet",tradingSet);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_MESSAGE.equals(authority)){
                    	message = authority;
                    	autoSubMap.put("message",message);
                    }else{autoSubMap.put("message",message);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_SEARCH_BILL.equals(authority)){
                    	searchBill = authority;
                    	autoSubMap.put("searchBill",searchBill);
                    }else{autoSubMap.put("searchBill",searchBill);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_BACKSYS_ROLE.equals(authority)){
                    	backsysRole = authority;
                    	autoSubMap.put("backsysRole",backsysRole);
                    }else{autoSubMap.put("backsysRole",backsysRole);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_CANCEL_BILL.equals(authority)){
                    	cancelBill = authority;
                    	autoSubMap.put("cancelBill",cancelBill);
                    }else{autoSubMap.put("cancelBill",cancelBill);
                    }if(ManagerStaff.CHIEF_SUB_ROLE_JSZD.equals(authority)){
                    	jszd = authority;
                    	autoSubMap.put("jszd",jszd);
                    }else{autoSubMap.put("jszd",jszd);
                    }
                }
            }else if(ManagerStaff.USER_TYPE_BRANCH.equals(subAccountInfo.getParentUserType())){
                for (String authority : authoriz) {
                    if(ManagerStaff.BRANCH_SUB_ROLE_REPLENISH.equals(authority)){
                        replenishment = authority;
                        autoSubMap.put("replenishment",replenishment);
                    }if(ManagerStaff.BRANCH_SUB_ROLE_OFFLINE.equals(authority)){
                        offLineAccount = authority;
                        autoSubMap.put("offLineAccount",offLineAccount);
                    }if(ManagerStaff.BRANCH_SUB_ROLE_SUB.equals(authority)){
                        subAccount = authority;
                        autoSubMap.put("subAccount",subAccount);
                    }if(ManagerStaff.BRANCH_SUB_ROLE_DELIVERY.equals(authority)){
                        crossReport = authority;
                        autoSubMap.put("crossReport",crossReport);
                    }if(ManagerStaff.BRANCH_SUB_ROLE_CLASSIFY.equals(authority)){
                        classifyReport = authority;
                        autoSubMap.put("classifyReport",classifyReport);
                    }if(ManagerStaff.BRANCH_SUB_ROLE_JSZD.equals(authority)){
                    	jszd = authority;
                    	autoSubMap.put("jszd",jszd);
                    }else{autoSubMap.put("jszd",jszd);
                    }
                }
                
            }else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(subAccountInfo.getParentUserType())){
                for (String authority : authoriz) {
                    if(ManagerStaff.STOCKHOLDER_SUB_ROLE_REPLENISH.equals(authority)){
                        replenishment = authority;
                        autoSubMap.put("replenishment",replenishment);
                    }else{autoSubMap.put("replenishment",replenishment);}
                    if(ManagerStaff.STOCKHOLDER_SUB_ROLE_OFFLINE.equals(authority)){
                        offLineAccount = authority;
                        autoSubMap.put("offLineAccount",offLineAccount);
                    }else{autoSubMap.put("offLineAccount",offLineAccount);}
                    if(ManagerStaff.STOCKHOLDER_SUB_ROLE_SUB.equals(authority)){
                        subAccount = authority;
                        autoSubMap.put("subAccount",subAccount);
                    }else{autoSubMap.put("subAccount",subAccount);}
                    if(ManagerStaff.STOCKHOLDER_SUB_ROLE_DELIVERY.equals(authority)){
                        crossReport = authority;
                        autoSubMap.put("crossReport",crossReport);
                    }else{autoSubMap.put("crossReport",crossReport);}
                    if(ManagerStaff.STOCKHOLDER_SUB_ROLE_CLASSIFY.equals(authority)){
                        classifyReport = authority;
                        autoSubMap.put("classifyReport",classifyReport);
                    }else{
                        autoSubMap.put("classifyReport",classifyReport);
                    }if(ManagerStaff.STOCKHOLDER_SUB_ROLE_JSZD.equals(authority)){
                    	jszd = authority;
                    	autoSubMap.put("jszd",jszd);
                    }else{autoSubMap.put("jszd",jszd);
                    }
                }
                
            }else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(subAccountInfo.getParentUserType())){
                for (String authority : authoriz) {
                    if(ManagerStaff.GEN_AGENT_SUB_ROLE_REPLENISH.equals(authority)){
                        replenishment = authority;
                        autoSubMap.put("replenishment",replenishment);
                    }else{autoSubMap.put("replenishment",replenishment);}
                    if(ManagerStaff.GEN_AGENT_SUB_ROLE_OFFLINE.equals(authority)){
                        offLineAccount = authority;
                        autoSubMap.put("offLineAccount",offLineAccount);
                    }else{autoSubMap.put("offLineAccount",offLineAccount);}
                    if(ManagerStaff.GEN_AGENT_SUB_ROLE_SUB.equals(authority)){
                        subAccount = authority;
                        autoSubMap.put("subAccount",subAccount);
                    }else{
                        autoSubMap.put("subAccount",subAccount);
                    }
                    if(ManagerStaff.GEN_AGENT_SUB_ROLE_DELIVERY.equals(authority)){
                        crossReport = authority;
                        autoSubMap.put("crossReport",crossReport);
                    }else{
                        autoSubMap.put("crossReport",crossReport);
                    }
                    if(ManagerStaff.GEN_AGENT_SUB_ROLE_CLASSIFY.equals(authority)){
                        classifyReport = authority;
                        autoSubMap.put("classifyReport",classifyReport);
                    }else{
                        autoSubMap.put("classifyReport",classifyReport);
                    }if(ManagerStaff.GEN_AGENT_SUB_ROLE_JSZD.equals(authority)){
                    	jszd = authority;
                    	autoSubMap.put("jszd",jszd);
                    }else{autoSubMap.put("jszd",jszd);
                    }
                }
                
            }else if(ManagerStaff.USER_TYPE_AGENT.equals(subAccountInfo.getParentUserType())){
                for (String authority : authoriz) {
                    if(ManagerStaff.AGENT_SUB_ROLE_REPLENISH.equals(authority)){
                        replenishment = authority;
                        autoSubMap.put("replenishment",replenishment);
                    }else{
                        autoSubMap.put("replenishment",replenishment);
                    }
                    if(ManagerStaff.AGENT_SUB_ROLE_OFFLINE.equals(authority)){
                        offLineAccount = authority;
                        autoSubMap.put("offLineAccount",offLineAccount);
                    }else{
                        autoSubMap.put("offLineAccount",offLineAccount);
                    }
                    if(ManagerStaff.AGENT_SUB_ROLE_SUB.equals(authority)){
                        subAccount = authority;
                        autoSubMap.put("subAccount",subAccount);
                    }else{
                        autoSubMap.put("subAccount",subAccount);
                    }
                    if(ManagerStaff.AGENT_SUB_ROLE_DELIVERY.equals(authority)){
                        crossReport = authority;
                        autoSubMap.put("crossReport",crossReport);
                    }else{
                        autoSubMap.put("crossReport",crossReport);
                    }
                    if(ManagerStaff.AGENT_SUB_ROLE_CLASSIFY.equals(authority)){
                        classifyReport = authority;
                        autoSubMap.put("classifyReport",classifyReport);
                    }else{
                        autoSubMap.put("classifyReport",classifyReport);
                    }if(ManagerStaff.AGENT_SUB_ROLE_JSZD.equals(authority)){
                    	jszd = authority;
                    	autoSubMap.put("jszd",jszd);
                    }else{autoSubMap.put("jszd",jszd);
                    }
                }
            }
        }else{
            autoSubMap.put("replenishment",replenishment);
            autoSubMap.put("offLineAccount",offLineAccount);
            autoSubMap.put("subAccount",subAccount);
            autoSubMap.put("crossReport",crossReport);
            autoSubMap.put("classifyReport",classifyReport);
            
            autoSubMap.put("odd",odd);
            autoSubMap.put("outReplenishManager",outReplenishManager);
            autoSubMap.put("oddLog",oddLog);
            autoSubMap.put("sysInit",sysInit);
            autoSubMap.put("tradingSet",tradingSet);
            autoSubMap.put("message",message);
            autoSubMap.put("searchBill",searchBill);
            autoSubMap.put("backsysRole",backsysRole);
            autoSubMap.put("cancelBill",cancelBill);
            autoSubMap.put("jszd",jszd);
        }
        return autoSubMap;
    }
	
}
