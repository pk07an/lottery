package com.npc.lottery.manage.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.boss.logic.interf.IShopsLogic;
import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.util.Page;
import com.npc.lottery.util.WebTools;
import com.npc.lottery.manage.logic.interf.IShopsDeclarattonLogic;
import com.npc.lottery.member.entity.BaseBet;
import com.npc.lottery.member.entity.BillSearchVo;
import com.npc.lottery.member.entity.CancelPet;
import com.npc.lottery.member.entity.CancelPetLog;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.logic.interf.IBetLogic;
import com.npc.lottery.periods.entity.PeriodsNumVo;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.statreport.dao.interf.IClassReportPetPeriodDao;
import com.npc.lottery.statreport.dao.interf.IClassReportRPeriodDao;
import com.npc.lottery.statreport.dao.interf.IReportStatusDao;
import com.npc.lottery.statreport.dao.interf.ISettledReportPetPeriodDao;
import com.npc.lottery.statreport.dao.interf.ISettledReportRPeriodDao;
import com.npc.lottery.statreport.entity.ReportStatus;
import com.npc.lottery.statreport.logic.interf.IBjscHisLogic;
import com.npc.lottery.statreport.logic.interf.IClassReportEricLogic;
import com.npc.lottery.statreport.logic.interf.ICqsscHisLogic;
import com.npc.lottery.statreport.logic.interf.IGdklsfHisLogic;
import com.npc.lottery.statreport.logic.interf.IHklhcHisLogic;
import com.npc.lottery.statreport.logic.interf.IReportStatusLogic;
import com.npc.lottery.statreport.logic.interf.ISettledReportEricLogic;
import com.npc.lottery.statreport.logic.interf.IStatReportLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;
import com.npc.lottery.util.SelectBox;

public class BillSearchAction extends BaseLotteryAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -436139968770943701L;

	private static final Logger log = Logger.getLogger(BillSearchAction.class);
	private String type = "privateAdmin";
	private String titleStyle = "searchBill";
	private IStatReportLogic statReportLogic;//报表统计
	private IShopsDeclarattonLogic shopsDeclarattonLogic;
	private IBetLogic betLogic;
	private  BillSearchVo vo ;
	private IHklhcHisLogic hklhcHisLogic;
	private IGdklsfHisLogic gdklsfHisLogic;
	private ICqsscHisLogic cqsscHisLogic;
	private IBjscHisLogic bjscHisLogic;
	private IReplenishLogic replenishLogic;
	
    private IGDPeriodsInfoLogic periodsInfoLogic = null;
    private ICQPeriodsInfoLogic icqPeriodsInfoLogic = null;
    private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic = null;
    private ISettledReportEricLogic settledReportEricLogic = null;//报表统计 
    private IClassReportEricLogic classReportEricLogic = null;//报表统计
    private IReportStatusLogic reportStatusLogic;
    
    //add by peter
    private IMemberStaffExtLogic memberStaffExtLogic;
    private IShopsLogic shopsLogic;
    
	/**
	 * 总管 总监 公用方法
	 * @return
	 */
	public String searchBill() throws Exception
	{
		try{
			
		 ArrayList<PlayType> playTypeList = statReportLogic.findCommissionTypeList();
		 
		this.getRequest().setAttribute("listPeriods", getPeriodsNumList());

		this.getRequest().setAttribute("playTypeList", playTypeList);
		this.getRequest().setAttribute("playTypeMapGD", Constant.GDKLSF_COMMISSION_TYPE);
		this.getRequest().setAttribute("playTypeMapCQ", Constant.CQSSC_COMMISSION_TYPE);
		this.getRequest().setAttribute("playTypeMapBJ", Constant.BJSC_COMMISSION_TYPE);
		//add by peter for K3
		this.getRequest().setAttribute("playTypeMapJS", Constant.K3_COMMISSION_TYPE);
		//add by peter for nc
		this.getRequest().setAttribute("playTypeMapNC", Constant.NC_COMMISSION_TYPE);
		
		Map<String,String> mapAll = new LinkedHashMap<String,String>(); ;
		for(Map.Entry<String, String> entry:Constant.GDKLSF_COMMISSION_TYPE.entrySet()){
			mapAll.put(entry.getKey(), "广东快乐十分:"+entry.getValue());
		}
		for(Map.Entry<String, String> entry:Constant.CQSSC_COMMISSION_TYPE.entrySet()){
			mapAll.put(entry.getKey(), "重庆时时彩:"+entry.getValue());
		}
		for(Map.Entry<String, String> entry:Constant.BJSC_COMMISSION_TYPE.entrySet()){
			mapAll.put(entry.getKey(), "北京赛车:"+entry.getValue());
		}
		for(Map.Entry<String, String> entry:Constant.K3_COMMISSION_TYPE.entrySet()){
			mapAll.put(entry.getKey(), "江苏快三:"+entry.getValue());
		}
		for(Map.Entry<String, String> entry:Constant.NC_COMMISSION_TYPE.entrySet()){
			mapAll.put(entry.getKey(), "幸运农场:"+entry.getValue());
		}
		
		this.getRequest().setAttribute("playTypeMapAll", mapAll);
		
		}catch(Exception e){
			log.error("<--注单搜索查询异常：-->",e);
//			throw new IllegalArgumentException("-注单搜索查询异常",e);
			return "exception";
		}
		return SUCCESS;
	}
	public List<SelectBox> getPeriodsNumList() throws IllegalArgumentException
	{
		// 總監 按期數查詢 start 
		/* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date now = new java.util.Date();
        String startStr = sdf.format(now) + " 00:00:00";
        String endStr = sdf.format(now) + " 23:59:59";
        Date sqlDateStart = java.sql.Timestamp.valueOf(startStr);
        Date sqlDateEnd = java.sql.Timestamp.valueOf(endStr);
        sqlDateStart =new Date(sqlDateStart.getTime() + 60 * 60 * 1000 * 0);
    	sqlDateEnd = new Date(sqlDateEnd.getTime() + 60 * 60 * 1000 * 26);
    	
		 List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
         filtersPeriodInfo.add(Restrictions.between("createTime",sqlDateStart,sqlDateEnd)); 
			 Page page= new Page(200);
	         int pageNo = 1;
	         page.setPageNo(pageNo);
	         page.setOrderBy("ID");
	         page.setOrder("asc");
         page = periodsInfoLogic.findPage(page,filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
		 List<GDPeriodsInfo> listGD = page.getResult();
		 
		 List<Criterion> filtersKP = new ArrayList<Criterion>();
			filtersKP.add(Restrictions.le("openQuotTime",new Date()));
			filtersKP.add(Restrictions.ge("lotteryTime",new Date()));
	        GDPeriodsInfo  gdPeriods = periodsInfoLogic.queryByPeriods(filtersKP.toArray(new Criterion[filtersKP.size()]));
//		 GDPeriodsInfo gdPeriods = periodsInfoLogic.queryLastLotteryPeriods();
		  
		 page = icqPeriodsInfoLogic.findPage(page,filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
		 List<CQPeriodsInfo> listCQ = page.getResult();
		 filtersKP = new ArrayList<Criterion>();
		 filtersKP.add(Restrictions.le("openQuotTime",new Date()));
		 filtersKP.add(Restrictions.ge("lotteryTime",new Date()));
		 CQPeriodsInfo cqPeriods=icqPeriodsInfoLogic.queryByPeriods(filtersKP.toArray(new Criterion[filtersKP.size()]));
			
//		 CQPeriodsInfo cqPeriods = icqPeriodsInfoLogic.queryLastLotteryPeriods();
		 
		 page = bjscPeriodsInfoLogic.findPage(page,filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
		 List<BJSCPeriodsInfo> listBJ = page.getResult();
		   filtersKP = new ArrayList<Criterion>();
		 filtersKP.add(Restrictions.le("openQuotTime",new Date()));
		 filtersKP.add(Restrictions.ge("lotteryTime",new Date()));
		 BJSCPeriodsInfo bjPeriods=bjscPeriodsInfoLogic.queryByPeriods(filtersKP.toArray(new Criterion[filtersKP.size()]));
			*/
//		 BJSCPeriodsInfo bjPeriods = bjscPeriodsInfoLogic.queryLastLotteryPeriods();
		 
		 List<PeriodsNumVo> list = betLogic.queryPeriodsAllOrderTime();
		 List<SelectBox> listPeriods = new ArrayList<SelectBox>();
		 Integer count = 10; //下拉列表顯示多少行
		 for(PeriodsNumVo ent:list){
//			String p = ent.getPeriodsNum();
//			if(null!=gdPeriods){
//				if(Integer.valueOf(p)>(Integer.valueOf(gdPeriods.getPeriodsNum())-count)&& Integer.valueOf(p)<=Integer.valueOf(gdPeriods.getPeriodsNum())) 
				listPeriods.add(new SelectBox(ent.getPeriodsNum1(),ent.getPeriodsNum2()));
//			}
		 }
		/* FOR(CQPERIODSINFO ENT:LISTCQ){
			 STRING P = ENT.GETPERIODSNUM();
				IF(NULL!=CQPERIODS)
				{
					IF(LONG.VALUEOF(P)>(LONG.VALUEOF(CQPERIODS.GETPERIODSNUM())-COUNT)&& LONG.VALUEOF(P)<=LONG.VALUEOF(CQPERIODS.GETPERIODSNUM())) 
					LISTPERIODS.ADD(NEW SELECTBOX("CQ_"+ENT.GETPERIODSNUM(),"重慶時時彩  "+ENT.GETPERIODSNUM()+" 期"));
				}
		 }
		 FOR(BJSCPERIODSINFO ENT:LISTBJ){
			 STRING P = ENT.GETPERIODSNUM();
				IF(NULL!=BJPERIODS){
					IF(INTEGER.VALUEOF(P)>(INTEGER.VALUEOF(BJPERIODS.GETPERIODSNUM())-COUNT)&& INTEGER.VALUEOF(P)<=INTEGER.VALUEOF(BJPERIODS.GETPERIODSNUM())) 
					LISTPERIODS.ADD(NEW SELECTBOX("BJ_"+ENT.GETPERIODSNUM(),"北京賽車(PK10)  "+ENT.GETPERIODSNUM()+" 期"));
				}
		 }*/
		 return listPeriods;
	}
	/**
	 * 总管
	 * @return
	 */
	/*public String queryBill()
	{
		try{
			
			//參數只用于分頁時
			String sType = this.getRequest().getParameter("subType");
			String playType = this.getRequest().getParameter("playType");
			String bettingDateStart = this.getRequest().getParameter("bettingDateStart");
			String bettingDateEnd = this.getRequest().getParameter("bettingDateEnd");
			String orderNum = this.getRequest().getParameter("orderNum");
			String lotteryType = this.getRequest().getParameter("lotteryType");
			String memberID= this.getRequest().getParameter("memberID");
			String eduMax= this.getRequest().getParameter("eduMax");
			String eduMin= this.getRequest().getParameter("eduMin");
			String resMin= this.getRequest().getParameter("resMin");
			String resMax= this.getRequest().getParameter("resMax");
			String billType= this.getRequest().getParameter("billType");
			
			String subType = null;
			ArrayList<PlayType> playTypeList = statReportLogic.findCommissionTypeList();
			this.getRequest().setAttribute("playTypeList", playTypeList);
			
			this.getRequest().setAttribute("playTypeMapGD", Constant.GDKLSF_COMMISSION_TYPE);
			this.getRequest().setAttribute("playTypeMapCQ", Constant.CQSSC_COMMISSION_TYPE);
//			this.getRequest().setAttribute("playTypeMapHK", Constant.HKLHC_COMMISSION_TYPE);
			this.getRequest().setAttribute("playTypeMapBJ", Constant.BJSC_COMMISSION_TYPE);
			
			Page<BaseBet> page = new Page<BaseBet>(15);
			int pageNo=1;
			if(this.getRequest().getParameter("pageNo")!=null) // 分頁
			{
				pageNo=this.findParamInt("pageNo");
				vo = new BillSearchVo();
				vo.setSubType(sType);
				vo.setLotteryType(lotteryType);
				vo.setOrderNum(orderNum);
				vo.setBettingDateEnd(bettingDateEnd);
				vo.setBettingDateStart(bettingDateStart);
				vo.setPlayType(playType);
				vo.setMemberID(memberID);
				vo.setEduMax(eduMax);
				vo.setEduMin(eduMin);
				vo.setResMax(resMax);
				vo.setResMin(resMin);
				vo.setBillType(billType);
				
				subType = sType;
			}else{
				subType = vo.getSubType();
			}
			page.setPageNo(pageNo);
			
			if(billType==null){
				billType = vo.getBillType();
			}
			if("0".equals(billType)){//補貨注單 
				
				page = betLogic.queryReplishPage(page, vo);
				
			}else if("1".equals(billType)){//會員注單
				
				if(subType.indexOf("HKLHC")!=-1)
				{
					page=betLogic.queryHKLHCBetByObj(page,vo);
				}else if(subType.indexOf("GDKLSF")!=-1)
				{
					page=betLogic.queryGDKLSFBetByObj(page,vo);
				}else if(subType.indexOf("CQSSC")!=-1)
				{
					page=betLogic.queryCQSSCBetByObj(page,vo);
				}else if(subType.indexOf("BJSC")!=-1)
				{
					page=betLogic.queryBJSCBetByObj(page,vo);
				}
			}
			this.getRequest().setAttribute("page", page);
			
		}catch(Exception e){
			log.error("<--注单搜索查询异常：-->",e);
			throw new IllegalArgumentException("-注单搜索查询异常",e);
		}
		return SUCCESS;
	}*/
	/**
	 * 总监
	 * @return
	 */
	public String queryBillAdmin()
	{
		try{
			
			//參數只用于分頁時
			String playType = this.getRequest().getParameter("playType");
			String createTime = this.getRequest().getParameter("createTime");
			
			String orderNum = this.getRequest().getParameter("orderNum");
			String lotteryType = this.getRequest().getParameter("lotteryType");
			String memberName= this.getRequest().getParameter("memberName");
			String eduMax= this.getRequest().getParameter("eduMax");
			String eduMin= this.getRequest().getParameter("eduMin");
			String resMin= this.getRequest().getParameter("resMin");
			String resMax= this.getRequest().getParameter("resMax");
			String billType= this.getRequest().getParameter("billType");
			String radioBox= this.getRequest().getParameter("radioBox");
			String userType= this.getRequest().getParameter("userType");
			String periodsNum= this.getRequest().getParameter("periodsNum");
			
			ArrayList<PlayType> playTypeList = statReportLogic.findCommissionTypeList();
			this.getRequest().setAttribute("playTypeList", playTypeList);
			
			this.getRequest().setAttribute("listPeriods",  getPeriodsNumList());
			this.getRequest().setAttribute("playTypeMapGD", Constant.GDKLSF_COMMISSION_TYPE);
			this.getRequest().setAttribute("playTypeMapCQ", Constant.CQSSC_COMMISSION_TYPE);
			this.getRequest().setAttribute("playTypeMapBJ", Constant.BJSC_COMMISSION_TYPE);
			this.getRequest().setAttribute("playTypeMapNC", Constant.NC_COMMISSION_TYPE);
			this.getRequest().setAttribute("playTypeMapK3", Constant.K3_COMMISSION_TYPE);
			Map<String,String> mapAll = new HashMap<String,String>(); ;
			mapAll.putAll(Constant.GDKLSF_COMMISSION_TYPE);
			mapAll.putAll(Constant.CQSSC_COMMISSION_TYPE);
			mapAll.putAll(Constant.BJSC_COMMISSION_TYPE);
			mapAll.putAll(Constant.NC_COMMISSION_TYPE);
			mapAll.putAll(Constant.K3_COMMISSION_TYPE);
			this.getRequest().setAttribute("playTypeMapAll", mapAll);
			
			//子帐号处理*********START
			ManagerUser userInfo = this.getCurrentManagerUser();
			ManagerUser userInfoNew = new ManagerUser();
			try {
				BeanUtils.copyProperties(userInfoNew, userInfo);
			} catch (Exception e) {
			  log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
			}
			   if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
				userInfoNew = getSubAccountParent(userInfoNew);	
			   }	
			//子帐号处理*********END
			
			Page<BaseBet> page = new Page<BaseBet>(15);
			int pageNo=1;
			if(this.getRequest().getParameter("pageNo")!=null) // 分頁
			{
				pageNo=this.findParamInt("pageNo");
				vo = new BillSearchVo();
				vo.setSubType(periodsNum.split("_")[0]);
				vo.setLotteryType(lotteryType);
				vo.setOrderNum(orderNum);
				vo.setPlayType(playType);
				//vo.setMemberID(memberID);
				//fixed by peter
				vo.setMemberName(memberName);
				vo.setEduMax(eduMax);//注額範圍
				vo.setEduMin(eduMin);
				vo.setResMax(resMax);//結果範圍
				vo.setResMin(resMin);
				vo.setBillType(billType);
				vo.setRadioBox(radioBox);
				vo.setUserType(userType);
				vo.setPeriodsNum(periodsNum);
				vo.setCreateTime(createTime);
				vo.setChiefID(userInfoNew.getShopsInfo().getChiefStaffExt().getManagerStaffID());//获取商铺的总监ID
				
			}else{
				vo.setChiefID(userInfoNew.getShopsInfo().getChiefStaffExt().getManagerStaffID());//获取商铺的总监ID
//				subType = vo.getSubType().split("_")[0];
//				vo.setSubType(vo.getPeriodsNum().split("_")[0]);
			}
			page.setPageNo(pageNo);
			
			if(billType==null ||"".equals(billType)){
				billType = vo.getBillType();
			}
			if("1".equals(vo.getRadioBox()))//按期數
			{
				vo.setCreateTime("");
				vo.setSubType(vo.getPeriodsNum().split("_")[0]);
			}else if("0".equals(vo.getRadioBox()))//按日期
			{
				vo.setPeriodsNum("");
				if(!"".equals(vo.getPlayType()))
					vo.setSubType(vo.getPlayType().split("_")[0]);
				else vo.setSubType("");
			}
			
			page = betLogic.queryBillSerachPageAdmin(page, vo);
				
			this.getRequest().setAttribute("page", page);
			//add by peter for 分页
			this.getRequest().setAttribute("vo", vo);
			
			//add by peter 增加从商铺信息获取是否允许总监注销注单功能
			ShopsInfo shopInfo = shopsLogic.findShopsCode(this.getCurrentManagerUser().getShopsInfo().getShopsCode());
			//this.getRequest().setAttribute("enableBetDelete", shopInfo.getEnableBetDelete());
			this.getRequest().setAttribute("enableBetCancel", shopInfo.getEnableBetCancel());
		}catch(Exception e){
			log.error("<--注单搜索查询异常：-->",e);
			throw new IllegalArgumentException("-注单搜索查询异常",e);
		}
		return SUCCESS;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitleStyle() {
		return titleStyle;
	}

	public void setTitleStyle(String titleStyle) {
		this.titleStyle = titleStyle;
	}
	public IStatReportLogic getStatReportLogic() {
		return statReportLogic;
	}
	public void setStatReportLogic(IStatReportLogic statReportLogic) {
		this.statReportLogic = statReportLogic;
	}
	public BillSearchVo getVo() {
		return vo;
	}
	public void setVo(BillSearchVo vo) {
		this.vo = vo;
	}
	public IBetLogic getBetLogic() {
		return betLogic;
	}
	public void setBetLogic(IBetLogic betLogic) {
		this.betLogic = betLogic;
	}
	public IHklhcHisLogic getHklhcHisLogic() {
		return hklhcHisLogic;
	}
	public void setHklhcHisLogic(IHklhcHisLogic hklhcHisLogic) {
		this.hklhcHisLogic = hklhcHisLogic;
	}
	public IGdklsfHisLogic getGdklsfHisLogic() {
		return gdklsfHisLogic;
	}
	public void setGdklsfHisLogic(IGdklsfHisLogic gdklsfHisLogic) {
		this.gdklsfHisLogic = gdklsfHisLogic;
	}
	public ICqsscHisLogic getCqsscHisLogic() {
		return cqsscHisLogic;
	}
	public void setCqsscHisLogic(ICqsscHisLogic cqsscHisLogic) {
		this.cqsscHisLogic = cqsscHisLogic;
	}
	public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}
	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}
	public IBjscHisLogic getBjscHisLogic() {
		return bjscHisLogic;
	}
	public void setBjscHisLogic(IBjscHisLogic bjscHisLogic) {
		this.bjscHisLogic = bjscHisLogic;
	}
	public IGDPeriodsInfoLogic getPeriodsInfoLogic() {
		return periodsInfoLogic;
	}
	public void setPeriodsInfoLogic(IGDPeriodsInfoLogic periodsInfoLogic) {
		this.periodsInfoLogic = periodsInfoLogic;
	}
	public ICQPeriodsInfoLogic getIcqPeriodsInfoLogic() {
		return icqPeriodsInfoLogic;
	}
	public void setIcqPeriodsInfoLogic(ICQPeriodsInfoLogic icqPeriodsInfoLogic) {
		this.icqPeriodsInfoLogic = icqPeriodsInfoLogic;
	}
	public IBJSCPeriodsInfoLogic getBjscPeriodsInfoLogic() {
		return bjscPeriodsInfoLogic;
	}
	public void setBjscPeriodsInfoLogic(IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic) {
		this.bjscPeriodsInfoLogic = bjscPeriodsInfoLogic;
	}
	
	public ISettledReportEricLogic getSettledReportEricLogic() {
		return settledReportEricLogic;
	}
	public void setSettledReportEricLogic(
			ISettledReportEricLogic settledReportEricLogic) {
		this.settledReportEricLogic = settledReportEricLogic;
	}
	public IClassReportEricLogic getClassReportEricLogic() {
		return classReportEricLogic;
	}
	public void setClassReportEricLogic(IClassReportEricLogic classReportEricLogic) {
		this.classReportEricLogic = classReportEricLogic;
	}
	public IReportStatusLogic getReportStatusLogic() {
		return reportStatusLogic;
	}
	public void setReportStatusLogic(IReportStatusLogic reportStatusLogic) {
		this.reportStatusLogic = reportStatusLogic;
	}
	/**
	 * add by peter for 总监注单删除
	 * 
	 * @return
	 */
	/*public String ajaxDeleteBillSubmitForChief() {
		try {
			// 先判断是否登录和是否总监登录
			ManagerUser user = this.getCurrentManagerUser();

			if (user == null || !ManagerStaff.USER_TYPE_CHIEF.equals(user.getUserType())) {

				return this.ajaxText("noLogin");
			}

			ShopsInfo shopInfo = shopsLogic.findShopsCode(this.getCurrentManagerUser().getShopsInfo().getShopsCode());
			if ("Y".equals(shopInfo.getEnableBetDelete())) {
				// 如果删除注单的功能是开放的
				String orderNo = this.getRequest().getParameter("orderNo");// 注单号
				String playType = this.getRequest().getParameter("playType");// 类型
				String periodsNum = this.getRequest().getParameter("periodsNum");// 盘期
				String searchDate = this.getRequest().getParameter("searchDate");// 时间
				String billTypeParam = this.getRequest().getParameter("billType");// 注单类型
				Page<BaseBet> page = new Page<BaseBet>(9999);
				BillSearchVo searchVo = new BillSearchVo();
				searchVo.setOrderNum(orderNo);
				String subType = "";
				if ("GDKLSF".equals(playType)) {
					subType = "GD";
				} else if ("CQSSC".equals(playType)) {
					subType = "CQ";
				} else if ("BJ".equals(playType)) {
					subType = "BJ";
				} else if ("K3".equals(playType)) {
					subType = "K3";
				}else if ("NC".equals(playType)) {
					subType = "NC";
				}
				searchVo.setSubType(subType);

				searchVo.setPeriodsNum(playType + "_" + periodsNum);
				searchVo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").parse(searchDate)));
				searchVo.setChiefID(user.getID());
				// fixed by peter, 查询对应注单类型的注单
				searchVo.setBillType(billTypeParam);
				page = betLogic.queryBillSerachPageAdmin(page, searchVo);

				List<BaseBet> results = page.getResult();
				if (null != results && results.size() > 0) {
					BaseBet bet = results.get(0);
					String lotteryType = bet.getLotteryType();// 0 已結算- 歷史表 1 未結算- 投注表
					String billType = bet.getBillType();// 注單類型
					String commission = String.valueOf(bet.getMemberCommission());// 会员退水
					String winMoney = String.valueOf(bet.getWinMoney().divide(new BigDecimal(bet.getCount())));// 输赢金额
					String bettingUserID = String.valueOf(bet.getBettingUserId());// 投注会员ID
					String betMoney = String.valueOf(bet.getMoney());// 下注金额
					String winState = bet.getWinState();// 输赢状态

					if ("0".equals(billType)) // 補貨注單
					{
						if (StringUtils.isNotEmpty(lotteryType)) {
							if ("1".equals(lotteryType)) {
								// 未结算
								// 删除補货表注单
								betLogic.deleteReplenishByOrderNum(orderNo);
							} else {
								// 已结算
								// 删除補货表注单
								betLogic.deleteHisReplenishByOrderNum(orderNo);
							}
						}

					} else if ("1".equals(billType)) {// 會員注單

						if (StringUtils.isNotEmpty(lotteryType)) {

							// 如果删除的是当天的注单，需要还原用户可用额度
							if (new SimpleDateFormat("yyyy-MM-dd").format(new Date()).equals(searchVo.getCreateTime())) {

								MemberStaffExt member = memberStaffExtLogic.findMemberStaffExtByID(Long.valueOf(bettingUserID));
								BigDecimal rollbackAvailableCreditLine = null;
								// 恢复会员可用额度
								if ("1".equals(lotteryType)) {
									// 未结算 回滚额度=现有可用额度+已投注金额
									rollbackAvailableCreditLine = new BigDecimal(member.getAvailableCreditLine()).add(new BigDecimal(betMoney));
									
									member.setAvailableCreditLine(rollbackAvailableCreditLine.intValue());
									memberStaffExtLogic.updateMemberStaffExt(member);
								} else {// 已经结算
										// 如果中奖/未中奖
									if ("1".equals(winState) || "2".equals(winState)) {
										// 回滚额度=现有额度-（输赢金额（中奖为+/未中奖未-）+会员退水）
										rollbackAvailableCreditLine = new BigDecimal(member.getAvailableCreditLine()).subtract((new BigDecimal(
												winMoney).add(new BigDecimal(betMoney).multiply(new BigDecimal(commission)).divide(
												new BigDecimal(100)))));
										
										member.setAvailableCreditLine(rollbackAvailableCreditLine.intValue());
										memberStaffExtLogic.updateMemberStaffExt(member);
									}
								}

								
							}
							// 删除会员注单
							betLogic.rollbackBet(playType, lotteryType, orderNo, periodsNum);
							
							//重新计算报表START
							//计算前先把状态改为N,是为了不影响客户查询
							List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
							ReportStatus reportStatus = reportStatusLogic.findReportStatus(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
							String status = "N";
							
							java.util.Date strDate =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").parse(searchDate);
							java.sql.Date quoteTime =  new java.sql.Date(strDate.getTime());
							String today = new Date(new java.util.Date().getTime()).toString();
							String dateStr = quoteTime.toString();	
							
							Boolean isToday=false;
							if(today.equals(dateStr)){isToday=true;}
								
							try {
								reportStatus.setStatus(status);
								reportStatusLogic.updateReportStatus(reportStatus);
								settledReportEricLogic.saveReportListForReCompute(periodsNum, lotteryType, dateStr,isToday,
										this.getSettledReportEricLogic(),this.getClassReportEricLogic(),this.getReportStatusLogic());
								status = "Y";
								reportStatus.setStatus(status);
								reportStatusLogic.updateReportStatus(reportStatus);
							} catch (Exception e) {
								status = "N";
						    	reportStatus.setStatus(status);
						    	reportStatusLogic.updateReportStatus(reportStatus);
						    	log.info("注单注销重新计算报表 异常，提示错误："+e.getMessage());
							}
							//重新计算报表END
						} else {
							throw new IllegalArgumentException("查詢條件為空：lotteryType=" + lotteryType);
						}
					} else {
						throw new IllegalArgumentException("查詢條件為空：billType=" + billType);
					}
				}
			} else {
				return this.ajaxText("disable");
			}
		} catch (Exception e) {
			log.error("<--注单搜索-删除 注單异常：ajaxDeleteBillSubmit-->", e);
			return this.ajaxText("error");
		}
		return this.ajaxText("success");
	}*/
	
	/**
	 * add by peter for 总监注单注销
	 * 
	 * @return
	 */
	public String ajaxCancelBillSubmitForChief() {
		try {
			// 先判断是否登录和是否总监登录
			ManagerUser user = this.getCurrentManagerUser();

			if (user == null || !ManagerStaff.USER_TYPE_CHIEF.equals(user.getUserType())) {

				return this.ajaxText("noLogin");
			}
			ShopsInfo shopInfo = shopsLogic.findShopsCode(this.getCurrentManagerUser().getShopsInfo().getShopsCode());
			if ("Y".equals(shopInfo.getEnableBetCancel())) {
				// 记录登陆IP
				String ip = WebTools.getClientIpAddr(getRequest());
				String orderNo = this.getRequest().getParameter("orderNo");// 注单号
				String playType = this.getRequest().getParameter("playType");// 类型
				String periodsNum = this.getRequest().getParameter("periodsNum");// 盘期
				String searchDate = this.getRequest().getParameter("searchDate");// 时间
				String billTypeParam = this.getRequest().getParameter("billType");// 注单类型
				String lotteryTypeParam = this.getRequest().getParameter("lotteryType");// 结算类型
				log.info("提交注销的注单，orderNo："+orderNo+",playType:"+playType+",periodsNum:"+periodsNum+
						",searchDate:"+searchDate+",billTypeParam:"+billTypeParam+",lotteryTypeParam:"+lotteryTypeParam+",ip:"+ip);
				// 如果是未结算的注单，不允许注销，一般不会进入这个逻辑，因为前台已对这个状态做了按纽是否显示的控制，为了保险而已
				if (!"0".equals(lotteryTypeParam)) {
					return this.ajaxText("notallow");
				}
				Page<BaseBet> page = new Page<BaseBet>(9999);
				BillSearchVo searchVo = new BillSearchVo();
				searchVo.setOrderNum(orderNo);
				String subType = "";
				if ("GDKLSF".equals(playType)) {
					subType = "GD";
				} else if ("CQSSC".equals(playType)) {
					subType = "CQ";
				} else if ("BJ".equals(playType)) {
					subType = "BJ";
				} else if ("K3".equals(playType)) {
					subType = "K3";
				}else if ("NC".equals(playType)) {
					subType = "NC";
				}
				searchVo.setSubType(subType);

				searchVo.setPeriodsNum(playType + "_" + periodsNum);
				searchVo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").parse(searchDate)));
				searchVo.setChiefID(user.getID());
				// fixed by peter, 查询对应注单类型的注单
				searchVo.setBillType(billTypeParam);
				page = betLogic.queryBillSerachPageAdmin(page, searchVo);

				List<BaseBet> results = page.getResult();
				if (null != results && results.size() > 0) {
					BaseBet bet = results.get(0);
					String lotteryType = bet.getLotteryType();// 0 已結算- 歷史表 1 未結算- 投注表
					String billType = bet.getBillType();// 注單類型  "0":補貨 "1":注單
					String commission = String.valueOf(bet.getMemberCommission());// 会员退水
					String winMoney = String.valueOf(bet.getWinMoney().divide(new BigDecimal(bet.getCount())));// 输赢金额
					String bettingUserID = String.valueOf(bet.getBettingUserId());// 投注会员ID
					String betMoney = String.valueOf(bet.getMoney());// 下注金额
					String winState = bet.getWinState();// 输赢状态
					
					if ("0".equals(billType)) // 操作的类型補貨注單
					{
						if (StringUtils.isNotEmpty(lotteryType)) {
							if ("0".equals(lotteryType)) {// 已结算的才能注销 
								betLogic.cancelHisReplenishByOrderNum(bet.getTypeCode(), orderNo, periodsNum, "0",ip);//把补货注单状态改为注销WIN_STATE=4
							}
						} else {
							throw new IllegalArgumentException("查詢條件為空：lotteryType=" + lotteryType);
						}
					} else if ("1".equals(billType)) {// 會員注單

						if (StringUtils.isNotEmpty(lotteryType)) {
							if ("0".equals(lotteryType)) {// 已结算的才能注销
								// 如果删除的是当天的注单，需要还原用户可用额度
								if (new SimpleDateFormat("yyyy-MM-dd").format(new Date()).equals(searchVo.getCreateTime())) {

									MemberStaffExt member = memberStaffExtLogic.findMemberStaffExtByID(Long.valueOf(bettingUserID));
									BigDecimal rollbackAvailableCreditLine = null;
									// 恢复会员可用额度
									// 已经结算
									// 如果中奖/未中奖
									if ("1".equals(winState) || "2".equals(winState)) {
										// 回滚额度=现有额度-（输赢金额（中奖为+/未中奖未-）+会员退水）
										rollbackAvailableCreditLine = new BigDecimal(member.getAvailableCreditLine()).subtract((new BigDecimal(
												winMoney).add(new BigDecimal(betMoney).multiply(new BigDecimal(commission)).divide(
												new BigDecimal(100)))));
										member.setAvailableCreditLine(rollbackAvailableCreditLine.intValue());
										memberStaffExtLogic.updateMemberStaffExt(member);
									}

									
								}
								// 修改会员注单为注销状态 "0":補貨 "1":注單
								betLogic.cancelBet(playType, lotteryType, orderNo, periodsNum, bet.getTypeCode(), "1", ip);//把注单状态改为注销WIN_STATE=4
							} else {
								return this.ajaxText("notallow");
							}

						} else {
							throw new IllegalArgumentException("查詢條件為空：lotteryType=" + lotteryType);
						}
					} else {
						throw new IllegalArgumentException("查詢條件為空：billType=" + billType);
					}		
					//重新计算报表START
					//计算前先把状态改为N,是为了不影响客户查询
					List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
					ReportStatus reportStatus = reportStatusLogic.findReportStatus(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
					String status = "N";
					reportStatus.setStatus(status);
					reportStatusLogic.updateReportStatus(reportStatus);
					
					
					java.util.Date strDate =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").parse(searchDate);
					java.sql.Date quoteTime =  new java.sql.Date(strDate.getTime());
					String today = new Date(new java.util.Date().getTime()).toString();
					String dateStr = quoteTime.toString();	
					
					
					Boolean isToday=false;
					if(today.equals(dateStr)){isToday=true;}
						
					try {
						reportStatus.setStatus(status);
						reportStatusLogic.updateReportStatus(reportStatus);
						String schema = "";
						settledReportEricLogic.saveReportListForReCompute(periodsNum, playType, dateStr,isToday,
								this.getSettledReportEricLogic(),this.getClassReportEricLogic(),this.getReportStatusLogic(),schema);
						status = "Y";
						reportStatus.setStatus(status);
						reportStatusLogic.updateReportStatus(reportStatus);
					} catch (Exception e) {
						status = "N";
				    	reportStatus.setStatus(status);
				    	reportStatusLogic.updateReportStatus(reportStatus);
				    	log.info("注单注销重新计算报表 异常，提示错误："+e.getMessage());
					}
					//重新计算报表END
								
							
				}
			} else {
				return this.ajaxText("disable");
			}
		} catch (Exception e) {
			log.error("<--注单搜索-注销 注單异常：ajaxDeleteBillSubmit-->", e);
			return this.ajaxText("error");
		}
		return this.ajaxText("success");
	}
	
	/**
	 * add by f for 总监注单恢复
	 * @return
	 */
	public String ajaxRecoveryBillSubmitForChief() {
		try {
			// 先判断是否登录和是否总监登录
			ManagerUser user = this.getCurrentManagerUser();

			if (user == null || !ManagerStaff.USER_TYPE_CHIEF.equals(user.getUserType())) {

				return this.ajaxText("noLogin");
			}
			ShopsInfo shopInfo = shopsLogic.findShopsCode(this.getCurrentManagerUser().getShopsInfo().getShopsCode());
			if ("Y".equals(shopInfo.getEnableBetCancel())) {
				// 记录登陆IP
				String ip =WebTools.getClientIpAddr(getRequest());
				String orderNo = this.getRequest().getParameter("orderNo");// 注单号
				String playType = this.getRequest().getParameter("playType");// 类型
				String periodsNum = this.getRequest().getParameter("periodsNum");// 盘期
				String searchDate = this.getRequest().getParameter("searchDate");// 时间
				String billTypeParam = this.getRequest().getParameter("billType");// 注单类型 0：補貨注單 1：會員注單
				log.info("提交恢复的注单，orderNo："+orderNo+",playType:"+playType+",periodsNum:"+periodsNum+
						",searchDate:"+searchDate+",billTypeParam:"+billTypeParam+",ip:"+ip);
				
				Page<BaseBet> page = new Page<BaseBet>(9999);
				BillSearchVo searchVo = new BillSearchVo();
				searchVo.setOrderNum(orderNo);
				String subType = "";
				if ("GDKLSF".equals(playType)) {
					subType = "GD";
				} else if ("CQSSC".equals(playType)) {
					subType = "CQ";
				} else if ("BJ".equals(playType)) {
					subType = "BJ";
				} else if ("K3".equals(playType)) {
					subType = "K3";
				}else if ("NC".equals(playType)) {
					subType = "NC";
				}
				searchVo.setSubType(subType);

				searchVo.setPeriodsNum(playType + "_" + periodsNum);
				searchVo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").parse(searchDate)));
				searchVo.setChiefID(user.getID());
				// fixed by f, 查询对应注单类型的注单
				searchVo.setBillType(billTypeParam);
				page = betLogic.queryBillSerachPageAdmin(page, searchVo);

				List<BaseBet> results = page.getResult();
				if (null != results && results.size() > 0) {
					BaseBet bet1 = results.get(0);
					String billType1 = bet1.getBillType();// 注單類型
					
					//把补货注单状态(WIN_STATE)恢复为注销前的状态
					betLogic.recoveryPet(orderNo, bet1.getTypeCode(), periodsNum, billType1, ip);
					
					page = betLogic.queryBillSerachPageAdmin(page, searchVo);

					List<BaseBet> results2 = page.getResult();
					if (null != results2 && results2.size() > 0) {
						BaseBet bet = results2.get(0);
						//String lotteryType = bet.getLotteryType();// 0 已結算- 歷史表 1 未結算- 投注表
						String billType = bet.getBillType();// 注單類型
						String commission = String.valueOf(bet.getMemberCommission());// 会员退水
						String winMoney = String.valueOf(bet.getWinMoney().divide(new BigDecimal(bet.getCount())));// 输赢金额
						String bettingUserID = String.valueOf(bet.getBettingUserId());// 投注会员ID
						String betMoney = String.valueOf(bet.getMoney());// 下注金额
						String winState = bet.getWinState();// 输赢状态
					
						// 如果删除的是当天的会员注单，需要还原用户可用额度，补货注单不恢复
						if ("1".equals(billType)){ // 操作的类型会员注單
							if (new SimpleDateFormat("yyyy-MM-dd").format(new Date()).equals(searchVo.getCreateTime())) {
		
								MemberStaffExt member = memberStaffExtLogic.findMemberStaffExtByID(Long.valueOf(bettingUserID));
								BigDecimal rollbackAvailableCreditLine = null;
								// 恢复会员可用额度
								// 已经结算
								// 回滚额度=现有额度+（输赢金额（中奖为+/未中奖未-）+会员退水）
								rollbackAvailableCreditLine = new BigDecimal(member.getAvailableCreditLine()).add((new BigDecimal(
										winMoney).add(new BigDecimal(betMoney).multiply(new BigDecimal(commission)).divide(
										new BigDecimal(100)))));
								member.setAvailableCreditLine(rollbackAvailableCreditLine.intValue());
								memberStaffExtLogic.updateMemberStaffExt(member);
		
								
							}
					    }
						
						//重新计算报表START
						//计算前先把状态改为N,是为了不影响客户查询
						List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
						ReportStatus reportStatus = reportStatusLogic.findReportStatus(filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
						String status = "N";
						reportStatus.setStatus(status);
						reportStatusLogic.updateReportStatus(reportStatus);
						
						
						java.util.Date strDate =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").parse(searchDate);
						java.sql.Date quoteTime =  new java.sql.Date(strDate.getTime());
						String today = new Date(new java.util.Date().getTime()).toString();
						String dateStr = quoteTime.toString();	
						
						
						Boolean isToday=false;
						if(today.equals(dateStr)){isToday=true;}
							
						try {
							reportStatus.setStatus(status);
							reportStatusLogic.updateReportStatus(reportStatus);
							String schema = "";
							settledReportEricLogic.saveReportListForReCompute(periodsNum, playType, dateStr,isToday,
									this.getSettledReportEricLogic(),this.getClassReportEricLogic(),this.getReportStatusLogic(),schema);
							status = "Y";
							reportStatus.setStatus(status);
							reportStatusLogic.updateReportStatus(reportStatus);
						} catch (Exception e) {
							status = "N";
					    	reportStatus.setStatus(status);
					    	reportStatusLogic.updateReportStatus(reportStatus);
					    	log.info("注单恢复重新计算报表 异常，提示错误："+e.getMessage());
						}
						//重新计算报表END
					}
							
				}
			} else {
				return this.ajaxText("disable");
			}
		} catch (Exception e) {
			log.error("<--注单搜索-恢复 注單异常：ajaxRecoveryBillSubmitForChief-->", e);
			return this.ajaxText("error");
		}
		return this.ajaxText("success");
	}
	
	public String queryCancelPetLog(){
		try {
			
			// 先判断是否登录和是否总监登录或者子账户登录
			ManagerUser user = this.getCurrentManagerUser();
			
			if (user == null || (!ManagerStaff.USER_TYPE_CHIEF.equals(user.getUserType()) && !ManagerStaff.USER_TYPE_SUB.equals(user.getUserType()))) {

				return this.ajaxText("noLogin");
			}
			String orderNo = this.getRequest().getParameter("orderNo");// 注单号
			String playType = this.getRequest().getParameter("playType");// 类型
			String periodsNum = this.getRequest().getParameter("periodsNum");// 盘期
			String searchDate = this.getRequest().getParameter("searchDate");// 时间
			String billType = this.getRequest().getParameter("billType");// 注单类型 0：補貨注單 1：會員注單
			log.info("提交查詢註銷日志的注单，orderNo："+orderNo+",playType:"+playType+",periodsNum:"+periodsNum+
					",searchDate:"+searchDate+",billType:"+billType);
			
			Page<BaseBet> page = new Page<BaseBet>(9999);
			BillSearchVo searchVo = new BillSearchVo();
			searchVo.setOrderNum(orderNo);
			String subType = "";
			if ("GDKLSF".equals(playType)) {
				subType = "GD";
			} else if ("CQSSC".equals(playType)) {
				subType = "CQ";
			} else if ("BJ".equals(playType)) {
				subType = "BJ";
			} else if ("K3".equals(playType)) {
				subType = "K3";
			}else if ("NC".equals(playType)) {
				subType = "NC";
			}
			searchVo.setSubType(subType);

			searchVo.setPeriodsNum(playType + "_" + periodsNum);
			searchVo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").parse(searchDate)));
			if(user.getUserType().equals(ManagerStaff.USER_TYPE_SUB)){
				searchVo.setChiefID(user.getParentStaffQry()); //总监子账号登录，取总监ID
			}else{
				searchVo.setChiefID(user.getID());
			}
			// fixed by f, 查询对应注单类型的注单
			searchVo.setBillType(billType);
			page = betLogic.queryBillSerachPageAdmin(page, searchVo);

			List<BaseBet> results = page.getResult();
			List<CancelPetLog> list = new ArrayList<CancelPetLog>();
			BaseBet bet = null;
			if (null != results && results.size() > 0) {
				bet = results.get(0);
				list = betLogic.queryCancelPetLogList(orderNo, bet.getTypeCode(), periodsNum, billType);
			}
			getRequest().setAttribute("list", list);
			
		} catch (Exception e) {
			log.error("<--注单搜索-恢复 注單异常：ajaxRecoveryBillSubmitForChief-->", e);
			return this.ajaxText("error");
		}
		return SUCCESS;
		
	}
	
	
	
	public IMemberStaffExtLogic getMemberStaffExtLogic() {
		return memberStaffExtLogic;
	}
	public void setMemberStaffExtLogic(IMemberStaffExtLogic memberStaffExtLogic) {
		this.memberStaffExtLogic = memberStaffExtLogic;
	}
	public IShopsLogic getShopsLogic() {
		return shopsLogic;
	}
	public void setShopsLogic(IShopsLogic shopsLogic) {
		this.shopsLogic = shopsLogic;
	}
}
