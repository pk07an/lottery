package com.npc.lottery.controller;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.util.Page;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;

public class BossUserAction extends BaseLotteryAction {
	private String periodsType;
	private String chief;
	
    /**
     * 查询所有的分公司并分页
     * 
     * @return
     */
    public String queryBranchStaff() {
    	
        String chief = getRequest().getParameter("chief");
        ManagerUser userInfo= new ManagerUser();
    	userInfo.setAccount(chief);
    	userInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
    	
        Page<BranchStaffExt> page = new Page<BranchStaffExt>(10);
        List<Criterion> filtersPlayType = new ArrayList<Criterion>();
        filtersPlayType.add(Restrictions.eq("userType",
                ManagerStaff.USER_TYPE_BRANCH));
        
        int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        page.setOrderBy("managerStaffID");
        page.setOrder("desc");
        page = branchStaffExtLogic.findPage(page,userInfo, null, null, null);
        this.getRequest().setAttribute("page", page);
        this.setChief(chief);
        this.setPeriodsType("user");
        return SUCCESS;
    }
    
    /**
     * 查询所有的股东并分页
     * 
     * @return
     */
    public String queryStockholder() {
    	
        String chief = getRequest().getParameter("chief");
        ManagerUser userInfo= new ManagerUser();
    	userInfo.setAccount(chief);
    	userInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
    	
        Page<StockholderStaffExt> page = new Page<StockholderStaffExt>(10);
        List<Criterion> filtersPlayType = new ArrayList<Criterion>();
        filtersPlayType.add(Restrictions.eq("userType",
                ManagerStaff.USER_TYPE_STOCKHOLDER));
        
        int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        page.setOrderBy("managerStaffID");
        page.setOrder("desc");
        page = stockholderStaffExtLogic.findPage(page,userInfo, null, null, null);
        this.getRequest().setAttribute("page", page);
        this.setChief(chief);
        this.setPeriodsType("user");
        return SUCCESS;
    }
    
    /**
     * 查询所有的总代理并分页
     * 
     * @return
     */
    public String queryGenAgentStaff() {
    	
        String chief = getRequest().getParameter("chief");
        ManagerUser userInfo= new ManagerUser();
    	userInfo.setAccount(chief);
    	userInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
    	
        Page<GenAgentStaffExt> page = new Page<GenAgentStaffExt>(10);
        List<Criterion> filtersPlayType = new ArrayList<Criterion>();
        filtersPlayType.add(Restrictions.eq("userType",
                ManagerStaff.USER_TYPE_GEN_AGENT));
        
        int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        page.setOrderBy("managerStaffID");
        page.setOrder("desc");
        page = genAgentStaffExtLogic.findPage(page,userInfo, null, null, null);
        this.getRequest().setAttribute("page", page);
        this.setChief(chief);
        this.setPeriodsType("user");
        return SUCCESS;
    }
    
    /**
     * 查询所有的代理并分页
     * 
     * @return
     */
    public String queryAgentStaff() {
    	
        String chief = getRequest().getParameter("chief");
        ManagerUser userInfo= new ManagerUser();
    	userInfo.setAccount(chief);
    	userInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
    	
        Page<AgentStaffExt> page = new Page<AgentStaffExt>(10);
        List<Criterion> filtersPlayType = new ArrayList<Criterion>();
        filtersPlayType.add(Restrictions.eq("userType",
                ManagerStaff.USER_TYPE_AGENT));
        
        int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        page.setOrderBy("managerStaffID");
        page.setOrder("desc");
        page = agentStaffExtLogic.findPage(page,userInfo, null, null, null);
        this.getRequest().setAttribute("page", page);
        this.setChief(chief);
        this.setPeriodsType("user");
        return SUCCESS;
    }
    
    /**
     * 查询所有的会员并分页
     * 
     * @return
     */
    public String queryMemberStaff() {
    	
        String chief = getRequest().getParameter("chief");
        ManagerUser userInfo= new ManagerUser();
    	userInfo.setAccount(chief);
    	userInfo.setUserType(ManagerStaff.USER_TYPE_CHIEF);
    	
        Page<MemberStaffExt> page = new Page<MemberStaffExt>(10);
        List<Criterion> filtersPlayType = new ArrayList<Criterion>();
        filtersPlayType.add(Restrictions.eq("userType",
                ManagerStaff.USER_TYPE_AGENT));
        
        int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        page.setOrderBy("managerStaffID");
        page.setOrder("desc");
        page = memberStaffExtLogic.findPage(page,userInfo, null, null, null, null);
        this.getRequest().setAttribute("page", page);
        this.setChief(chief);
        this.setPeriodsType("user");
        return SUCCESS;
    }
    
    
    
    
    private IBranchStaffExtLogic branchStaffExtLogic;// 分公司
	private IStockholderStaffExtLogic stockholderStaffExtLogic = null; // 股东
    private IGenAgentStaffExtLogic genAgentStaffExtLogic = null; // 总代理
    private IAgentStaffExtLogic agentStaffExtLogic = null; // 代理
    private IMemberStaffExtLogic memberStaffExtLogic;
    
	public IBranchStaffExtLogic getBranchStaffExtLogic() {
		return branchStaffExtLogic;
	}
	public IStockholderStaffExtLogic getStockholderStaffExtLogic() {
		return stockholderStaffExtLogic;
	}
	public IGenAgentStaffExtLogic getGenAgentStaffExtLogic() {
		return genAgentStaffExtLogic;
	}
	public IAgentStaffExtLogic getAgentStaffExtLogic() {
		return agentStaffExtLogic;
	}
	public void setBranchStaffExtLogic(IBranchStaffExtLogic branchStaffExtLogic) {
		this.branchStaffExtLogic = branchStaffExtLogic;
	}
	public void setStockholderStaffExtLogic(
			IStockholderStaffExtLogic stockholderStaffExtLogic) {
		this.stockholderStaffExtLogic = stockholderStaffExtLogic;
	}
	public void setGenAgentStaffExtLogic(
			IGenAgentStaffExtLogic genAgentStaffExtLogic) {
		this.genAgentStaffExtLogic = genAgentStaffExtLogic;
	}
	public void setAgentStaffExtLogic(IAgentStaffExtLogic agentStaffExtLogic) {
		this.agentStaffExtLogic = agentStaffExtLogic;
	}
	public IMemberStaffExtLogic getMemberStaffExtLogic() {
		return memberStaffExtLogic;
	}

	public void setMemberStaffExtLogic(IMemberStaffExtLogic memberStaffExtLogic) {
		this.memberStaffExtLogic = memberStaffExtLogic;
	}

	public String getPeriodsType() {
		return periodsType;
	}
	public void setPeriodsType(String periodsType) {
		this.periodsType = periodsType;
	}
	public String getChief() {
		return chief;
	}
	public void setChief(String chief) {
		this.chief = chief;
	}
	

}
