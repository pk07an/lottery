package com.npc.lottery.statreport.action;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.util.Page;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.replenish.vo.DetailVO;
import com.npc.lottery.replenish.vo.UserVO;
import com.npc.lottery.statreport.entity.DeliveryReportEric;
import com.npc.lottery.statreport.entity.DeliveryUnReport;
import com.npc.lottery.statreport.entity.GdklsfHis;
import com.npc.lottery.statreport.logic.interf.IUnsettledReportLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.user.logic.interf.ICommonUserLogic;
import edu.emory.mathcs.backport.java.util.Collections;

/**
 * 报表统计Action
 * 
 */
public class UnsettledReportAction extends BaseLotteryAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6763143609272139207L;

	private static Logger log = Logger.getLogger(UnsettledReportAction.class);

    private IUnsettledReportLogic unsettledReportLogic = null;//报表统计
    private ICommonUserLogic commonUserLogic;
    
    private IReplenishLogic replenishLogic = null;

    private String type;
    private String isUp = "false"; //是否是上级往下级查询,默认为否
    private String sitemeshType = "report"; //用于页面菜单显示

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~进入交收报表未结算报表-----------START
    /**
     * 报表列表
     * 
     * @return
     * @throws Exception
     */
    public String unsettledList() throws Exception {

        ManagerUser userInfo = (ManagerUser) getRequest().getSession().getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);

    	//子帐号处理*********START
    	ManagerUser userInfoNew = new ManagerUser();
    	try {
    		BeanUtils.copyProperties(userInfoNew, userInfo);
    	} catch (Exception e) {
    	  log.info("userInfoSys里出错"+ e.getMessage());
    	}
    	if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
    		userInfoNew = getSubAccountParent(userInfoNew);	
        }	
    	//子帐号处理*********END
    	   
        /*
         * userID为空即用户直接查询，如果当前查询者是子帐号查询的，还要查询子帐号的所属者，把该所属者的userID和userType赋于查询变量
         * userID不为空即从上级往下查下级的，如果当前查询者是子帐号，同上。
         * 
         */
    	if(userID==null){
        	userID = userInfoNew.getID();
  			userType = userInfoNew.getUserType();
  			detailUserAccount = userInfoNew.getAccount();
        	//如果公司的开放报表功能打开，分公司查报表时就等同于总监查询，与总监登录查询一模一样。
        	if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfoNew.getUserType())){
        	    UserVO userVO =  commonUserLogic.getUserVo(userInfoNew);
      			if(Constant.OPEN.equals(userVO.getReport())){
      				userID = userVO.getChiefStaffExt().getManagerStaffID();
      				userType = userVO.getChiefStaffExt().getUserType();
      				detailUserAccount = userVO.getChiefStaffExt().getAccount();
      			}
      		}
        }
        
        this.parentUserType = userType;//设置 parentUserType 的属性值，直属会员明细中需要使用
        
        String vPlayType = null;
        //如果没有选择小类就直接查全部，
        if("ALL".indexOf(playType)!=-1){
        	vPlayType = "%";
		}else{
			vPlayType = playType + "%";
		}
        //把没有选择的一项的参数赋于null
        String periodsNumFrm = ""; 
        String lotteryTypeFrm = ""; 
        if("typePeriod".equals(selectTypeRadio)){
        	bettingDateStart = null;
        	bettingDateEnd = null;
        	if(periodsNum!=null){
        		periodsNumFrm = periodsNum.split("_")[1];
            }
        }else{
        	periodsNumFrm = "";
        	periodsNum = "";
        }
        String[] scanTableList = new String[]{};
        if(lotteryType.indexOf("ALL")!=-1){
			scanTableList = Constant.ALL_TABLE_LIST;
			lotteryTypeFrm = "%";
		}else if(lotteryType.indexOf("GD")!=-1 || periodsNum.indexOf("GD")!=-1){
			scanTableList = Constant.GDKLSF_TABLE_LIST;
			lotteryTypeFrm = Constant.LOTTERY_TYPE_GDKLSF +"%";
		}else if(lotteryType.indexOf("CQ")!=-1 || periodsNum.indexOf("CQ")!=-1){
			scanTableList = Constant.CQSSC_TABLE_LIST;
			lotteryTypeFrm = Constant.LOTTERY_TYPE_CQSSC +"%";
		}else if(lotteryType.indexOf("BJ")!=-1 || periodsNum.indexOf("BJ")!=-1){
			scanTableList = Constant.BJSC_TABLE_LIST;
			lotteryTypeFrm = Constant.LOTTERY_TYPE_BJ +"%";
	    }else if(lotteryType.indexOf("K3")!=-1 || periodsNum.indexOf("K3")!=-1){
	    	scanTableList = Constant.K3_TABLE_LIST;
	    	lotteryTypeFrm = Constant.LOTTERY_TYPE_K3 +"%";
	    }else if(lotteryType.indexOf("NC")!=-1 || periodsNum.indexOf("NC")!=-1){
	    	scanTableList = Constant.NC_TABLE_LIST;
	    	lotteryTypeFrm = Constant.LOTTERY_TYPE_NC +"%";
	    }
        
        List<DeliveryUnReport> resultList = unsettledReportLogic
                .findUnSettledReport(bettingDateStart, bettingDateEnd, lotteryTypeFrm, vPlayType, periodsNumFrm, userID, userType,scanTableList);
        String userTitle = "";
        String adminTitle = "";
        for(DeliveryUnReport vo:resultList){
        	if(MemberStaff.USER_TYPE_MEMBER.equals(vo.getUserType())){
        		userTitle = ManagerStaff.USER_TYPE_AGENT.equals(userType)?"會員":"直屬會員";
        	}else{
        		adminTitle = DeliveryUnReport.getUserTypeName(String.valueOf(Integer.valueOf(userType)+1));
        	}
        }
        
        //查询补货信息
        DeliveryUnReport replenishEntity =  null;
        List<DeliveryUnReport> list = unsettledReportLogic.queryReplenish(bettingDateStart, bettingDateEnd,userID,userType,vPlayType, periodsNumFrm,lotteryTypeFrm);
        
        if(!list.isEmpty()){
        	replenishEntity= new DeliveryUnReport();
        	replenishEntity = (DeliveryUnReport) list.get(0);
        	replenishEntity.setSubordinate("补货");
        	replenishEntity.setUserName("补货");
        	replenishEntity.setUserType(userType);
        }
        
        //合计数据
        DeliveryUnReport totalEntity = null;
        if (null != resultList && resultList.size() > 0) {
        	totalEntity = new DeliveryUnReport();
        	Long turnover = 0L;
        	Double amount = (double) 0;
        	Double rateMoney = (double) 0;
            for(DeliveryUnReport vo : resultList){
            	turnover += vo.getTurnover();
            	amount += vo.getAmount();
            	rateMoney += vo.getRateMoney();
            }
            /*if(isUp.equals("true")){
	            if(!list.isEmpty()){
	            	turnover = turnover + replenishEntity.getTurnover();
	            	amount = amount + replenishEntity.getAmount();
	            }
            }*/
            totalEntity.setTurnover(turnover);
            totalEntity.setAmount(amount);
            totalEntity.setRateMoney(rateMoney);
        }
        
        //计算明细记录中的占货比、合计数据中的实占输赢、实占退水、贡献上级
        if (null != resultList && null != totalEntity) {
            //获取合计数据中的投注总额的值
            Double realResult = totalEntity.getRateMoney();
            //计算合计数据中的贡献上级
            Double offerSuperior = 0.0;
            for (int i = 0; i < resultList.size(); i++) {
                resultList.get(i).calRealResultPer(realResult);//占货比

                offerSuperior += resultList.get(i).getOfferSuperior();
            }

            //合计数据中赋值实占输赢、实占退水、贡献上级、赚取退水
            totalEntity.setOfferSuperior(offerSuperior);
            //设计合计中的占货比为100%
            totalEntity.setRealResultPer(1.0);
        }
        
        getRequest().setAttribute("resultList", resultList);
        getRequest().setAttribute("totalEntity", totalEntity);
        getRequest().setAttribute("replenishEntity", replenishEntity);
        String reportInfo = "";
        if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
        	reportInfo = DeliveryReportEric.getUserTypeName(userType) + " - 交收報錶";
        }else{
        	reportInfo = DeliveryReportEric.getUserTypeName(userType) + "（"
                    + detailUserAccount + "）-交收報錶";
        }
        getRequest().setAttribute("reportInfo",reportInfo);
        
        String searchData = "";
        if("typeTime".equals(selectTypeRadio)){
        	searchData = "按日期查詢：" + bettingDateStart + " — " + bettingDateEnd + "";
        }else{
        	searchData = "按期數查詢：" + periodsNumFrm + " 期" + "";
        }
        getRequest().setAttribute("searchData", searchData);
        String tableTitle  = "";
        if(adminTitle!="" && userTitle==""){
        	tableTitle = adminTitle;
        }else if(adminTitle=="" && userTitle!=""){
        	tableTitle = userTitle;
        }else if(adminTitle!="" && userTitle!=""){
        	tableTitle = adminTitle + "/" + userTitle;
        }else if(adminTitle=="" && userTitle==""){
        	tableTitle = DeliveryUnReport.getUserTypeName(String.valueOf(Integer.valueOf(userType)+1));
        }
        getRequest().setAttribute("userTypeName", tableTitle);
        getRequest().setAttribute("userType", userType);
        if(ManagerStaff.USER_TYPE_CHIEF.equals(userType)){
        	return "chiefList";
        }else{
        	return "list";
        }
    }

    /**
     * 补货会员明细信息
     * 
     * @return
     * @throws Exception
     */
    public String unsettledRelenishDetailed() throws Exception {
    	//子帐号处理*********START
    	ManagerUser userInfo = (ManagerUser) getRequest().getSession().getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
    	ManagerUser userInfoNew = new ManagerUser();
    	try {
    		BeanUtils.copyProperties(userInfoNew, userInfo);
    	} catch (Exception e) {
    	  log.info("userInfoSys里出错"+ e.getMessage());
    	}
    	if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
    		userInfoNew = getSubAccountParent(userInfoNew);	
        }
    	String currentUserType  = userInfoNew.getUserType();
    	//子帐号处理*********END

        Long userID = Long.valueOf(getRequest().getParameter("userID"));
  		Page<DetailVO> page = new Page<DetailVO>(25);
  		int pageNo=1;
  		
  		if(this.getRequest().getParameter("pageNo")!=null)
  			pageNo=this.findParamInt("pageNo");
  		page.setPageNo(pageNo);
  		
  		String commissionTypeCode = "";
		if("ALL".indexOf(playType)!=-1){
			commissionTypeCode = "%";
		}else{
			commissionTypeCode = playType + "%";
		}
		//把没有选择的一项的参数赋于null
		String periodsNumFrm = ""; 
        String lotteryTypeFrm = ""; 
        if("typePeriod".equals(selectTypeRadio)){
        	bettingDateStart = null;
        	bettingDateEnd = null;
        	if(periodsNum!=null){
        		periodsNumFrm = periodsNum.split("_")[1];
            }
        }else{
        	periodsNumFrm = null;
        }
        if("typePeriod".equals(selectTypeRadio)){
        	if(periodsNum.indexOf("GD")!=-1){
    			lotteryTypeFrm = Constant.LOTTERY_TYPE_GDKLSF +"%";
    		}else if(periodsNum.indexOf("CQ")!=-1){
    			lotteryTypeFrm = Constant.LOTTERY_TYPE_CQSSC +"%";
    		}else if(periodsNum.indexOf("BJ")!=-1){
    			lotteryTypeFrm = Constant.LOTTERY_TYPE_BJ +"%";
    	    }else if(periodsNum.indexOf("K3")!=-1){
    	    	lotteryTypeFrm = Constant.LOTTERY_TYPE_K3 +"%";
    	    }else if(periodsNum.indexOf("NC")!=-1){
    	    	lotteryTypeFrm = Constant.LOTTERY_TYPE_NC +"%";
    	    }
        }else{
        	if(lotteryType.indexOf("GD")!=-1){
    			lotteryTypeFrm = Constant.LOTTERY_TYPE_GDKLSF +"%";
    		}else if(lotteryType.indexOf("CQ")!=-1){
    			lotteryTypeFrm = Constant.LOTTERY_TYPE_CQSSC +"%";
    		}else if(lotteryType.indexOf("BJ")!=-1){
    			lotteryTypeFrm = Constant.LOTTERY_TYPE_BJ +"%";
    	    }else if(lotteryType.indexOf("K3")!=-1){
    	    	lotteryTypeFrm = Constant.LOTTERY_TYPE_K3 +"%";
    	    }else if(lotteryType.indexOf("NC")!=-1){
    	    	lotteryTypeFrm = Constant.LOTTERY_TYPE_NC +"%";
    	    }else if(lotteryType.indexOf("ALL")!=-1){
            	lotteryTypeFrm = "%";
    	    }
        }
        

        page=unsettledReportLogic.findReplenishDetail(page, bettingDateStart, bettingDateEnd, periodsNumFrm, userID, userType, commissionTypeCode, lotteryTypeFrm,currentUserType);
      //按照盈虧排序亏损高排前
 		 Collections.sort(page.getResult(),new Comparator<DetailVO>(){   
 	           public int compare(DetailVO arg0, DetailVO arg1) {   
 	               return arg1.getBettingDate().compareTo(arg0.getBettingDate());   
 	            }   
 	     });
 		//判断分公司的查询报表功能有没有打开
     	String branchReport = Constant.CLOSE;
     	
      	if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfoNew.getUserType())){
      		UserVO userVO =  commonUserLogic.getUserVo(userInfoNew);
    			if(Constant.OPEN.equals(userVO.getReport())){
    		    	branchReport = userVO.getReport();
    			}
    	}
     	
     	getRequest().setAttribute("branchReport",branchReport);//用于在页面判断公司是否开放报表
     	
 		this.getRequest().setAttribute("page", page);
 		this.getRequest().setAttribute("isPet", "false");//用来在明细里控制分页的ACTION
        return "playDetail";
    }
    
    //投注明細
  	public String detail(){
  		//String periodNum  = getRequest().getParameter("periodNum");
  		Page<DetailVO> page = new Page<DetailVO>(25);
  		int pageNo=1;
  		
  		if(this.getRequest().getParameter("pageNo")!=null)
  			pageNo=this.findParamInt("pageNo");
  		page.setPageNo(pageNo);
  		
        String vPlayType = "";
		if("ALL".indexOf(playType)!=-1){
			vPlayType = "%";
		}else{
			vPlayType = playType + "%";
		}
		//把没有选择的一项的参数赋于null
		String periodsNumFrm = ""; 
        if("typePeriod".equals(selectTypeRadio)){
        	bettingDateStart = null;
        	bettingDateEnd = null;
        	if(periodsNum!=null){
        		periodsNumFrm = periodsNum.split("_")[1];
            }
        }else{
        	periodsNumFrm = null;
        	//bettingDateStart =new Date(bettingDateStart.getTime() + 60 * 60 * 1000 * 2);
        	//bettingDateEnd = new Date(bettingDateEnd.getTime() + 60 * 60 * 1000 * 26);
        }
        
        String[] scanTableList = new String[]{};
        if("typePeriod".equals(selectTypeRadio)){
        	if(periodsNum.indexOf("GD")!=-1){
        		scanTableList = Constant.GDKLSF_TABLE_LIST;
			}else if(periodsNum.indexOf("CQ")!=-1){
				scanTableList = Constant.CQSSC_TABLE_LIST;
			}else if(periodsNum.indexOf("BJ")!=-1){
				scanTableList = Constant.BJSC_TABLE_LIST;
			}else if(periodsNum.indexOf("K3")!=-1){
				scanTableList = Constant.K3_TABLE_LIST;
			}else if(periodsNum.indexOf("NC")!=-1){
				scanTableList = Constant.NC_TABLE_LIST;
			}
        }else{
	        if(lotteryType.indexOf("GD")!=-1){
	        	scanTableList = Constant.GDKLSF_TABLE_LIST;
			}else if(lotteryType.indexOf("CQ")!=-1){
				scanTableList = Constant.CQSSC_TABLE_LIST;
			}else if(lotteryType.indexOf("BJ")!=-1){
				scanTableList = Constant.BJSC_TABLE_LIST;
			}else if(lotteryType.indexOf("K3")!=-1){
				scanTableList = Constant.K3_TABLE_LIST;
			}else if(lotteryType.indexOf("NC")!=-1){
				scanTableList = Constant.NC_TABLE_LIST;
			}else if(lotteryType.indexOf("ALL")!=-1){
				scanTableList = Constant.ALL_TABLE_LIST;
			}
        }
        
      	page=unsettledReportLogic.findDetail(page, bettingDateStart, bettingDateEnd, vPlayType, periodsNumFrm, userID,scanTableList);
        
      	//如果是分公公司、股东、总代理的直属会员，就要这样处理：比如：如果是分公司的直属会员，分公司，股东，总代理的下方是显示总监的退水
      	//										   如查是股东的直属会员，股东，总代理，的下方显示是分公司的退水
      	//                                       如果是总代理的直属会员，总代理的下方是显水股东的退水
      	if(ManagerStaff.USER_TYPE_BRANCH.equals(parentUserType) || ManagerStaff.USER_TYPE_STOCKHOLDER.equals(parentUserType) || ManagerStaff.USER_TYPE_GEN_AGENT.equals(parentUserType)){
	      	List<DetailVO> retList = page.getResult();
	      	for(DetailVO vo:retList){
	      		if(ManagerStaff.USER_TYPE_BRANCH.equals(parentUserType)){
	      			vo.setBranchCommission(vo.getChiefCommission());
	      			vo.setStockCommission(vo.getChiefCommission());
	      			vo.setGenAgentCommission(vo.getChiefCommission());
	      		}else if(ManagerStaff.USER_TYPE_STOCKHOLDER.equals(parentUserType)){
	      			vo.setStockCommission(vo.getBranchCommission());
	      			vo.setGenAgentCommission(vo.getBranchCommission());
	      		}else if(ManagerStaff.USER_TYPE_GEN_AGENT.equals(parentUserType)){
	      			vo.setGenAgentCommission(vo.getStockCommission());
	      		}
	      	}
      	}
  		//按照盈虧排序亏损高排前
  		 Collections.sort(page.getResult(),new Comparator<DetailVO>(){   
  	           public int compare(DetailVO arg0, DetailVO arg1) {   
  	               return arg1.getBettingDate().compareTo(arg0.getBettingDate());   
  	            }   
  	     });
  		//判断分公司的查询报表功能有没有打开
      	String branchReport = Constant.CLOSE;
      	
      	ManagerUser userInfo = (ManagerUser) getRequest().getSession().getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);

    	//子帐号处理*********START
    	ManagerUser userInfoNew = new ManagerUser();
    	try {
    		BeanUtils.copyProperties(userInfoNew, userInfo);
    	} catch (Exception e) {
    	  log.info("userInfoSys里出错"+ e.getMessage());
    	}
    	if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
    		userInfoNew = getSubAccountParent(userInfoNew);	
        }	
      	if(ManagerStaff.USER_TYPE_BRANCH.equals(userInfoNew.getUserType())){
      		UserVO userVO =  commonUserLogic.getUserVo(userInfoNew);
    			if(Constant.OPEN.equals(userVO.getReport())){
    		    	branchReport = userVO.getReport();
    			}
    	}
      	
      	//子帐号处理*********END
      	getRequest().setAttribute("branchReport",branchReport);//用于在页面判断公司是否开放报表
      	
  		this.getRequest().setAttribute("page", page);
  		this.getRequest().setAttribute("isPet", "true");//用来在明细里控制分页的ACTION
  		return "playDetail";
  	}
    
//~~~~~~~~~~~~~~~~~~~~~~~交收报表--未结算报表END

    private Long bettingUserID;//投注会员ID

    private String lotteryType;//彩票种类

    private String playType;//下注类型

    private String typePeriod;//按期数

    private String typeTime;//按时间

    private String periodsNum;//期数信息

    private Date bettingDateStart;//开始时间

    private Date bettingDateEnd;//结束时间

    private String reportType;//报表类型
    
    private String parentUserType;//上级用户userID

    private GdklsfHis gdklsfHisEntity;//广东快乐十分历史投注信息
    
    private Long userID;
    
    private String userType;
    
    private String detailUserAccount;
    
    private String selectTypeRadio; //判断是以盘期查询还是以日期查询    typePeriod:盘期    typeTime:日期

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public String getTypePeriod() {
        return typePeriod;
    }

    public void setTypePeriod(String typePeriod) {
        this.typePeriod = typePeriod;
    }

    public String getTypeTime() {
        return typeTime;
    }

    public void setTypeTime(String typeTime) {
        this.typeTime = typeTime;
    }

    public String getPeriodsNum() {
        return periodsNum;
    }

    public void setUnsettledReportLogic(IUnsettledReportLogic unsettledReportLogic) {
		this.unsettledReportLogic = unsettledReportLogic;
	}

	public ICommonUserLogic getCommonUserLogic() {
		return commonUserLogic;
	}

	public void setCommonUserLogic(ICommonUserLogic commonUserLogic) {
		this.commonUserLogic = commonUserLogic;
	}

	public IUnsettledReportLogic getUnsettledReportLogic() {
		return unsettledReportLogic;
	}

	public String getSitemeshType() {
		return sitemeshType;
	}

	public void setSitemeshType(String sitemeshType) {
		this.sitemeshType = sitemeshType;
	}

	public void setPeriodsNum(String periodsNum) {
        this.periodsNum = periodsNum;
    }

	public String getIsUp() {
		return isUp;
	}

	public void setIsUp(String isUp) {
		this.isUp = isUp;
	}

	public Date getBettingDateStart() {
        return bettingDateStart;
    }

    public void setBettingDateStart(Date bettingDateStart) {
        this.bettingDateStart = bettingDateStart;
    }

    public Date getBettingDateEnd() {
        return bettingDateEnd;
    }

    public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}

	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}

	public void setBettingDateEnd(Date bettingDateEnd) {
        this.bettingDateEnd = bettingDateEnd;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getParentUserType() {
		return parentUserType;
	}

	public void setParentUserType(String parentUserType) {
		this.parentUserType = parentUserType;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GdklsfHis getGdklsfHisEntity() {
        return gdklsfHisEntity;
    }

    public void setGdklsfHisEntity(GdklsfHis gdklsfHisEntity) {
        this.gdklsfHisEntity = gdklsfHisEntity;
    }

    public Long getBettingUserID() {
        return bettingUserID;
    }

    public void setBettingUserID(Long bettingUserID) {
        this.bettingUserID = bettingUserID;
    }

	public Long getUserID() {
		return userID;
	}

	public String getUserType() {
		return userType;
	}

	public String getDetailUserAccount() {
		return detailUserAccount;
	}

	public void setDetailUserAccount(String detailUserAccount) {
		this.detailUserAccount = detailUserAccount;
	}

	public String getSelectTypeRadio() {
		return selectTypeRadio;
	}

	public void setSelectTypeRadio(String selectTypeRadio) {
		this.selectTypeRadio = selectTypeRadio;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
