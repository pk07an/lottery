package com.npc.lottery.common.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.alibaba.fastjson.JSONObject;
import com.npc.lottery.common.Constant;
import com.npc.lottery.manage.logic.interf.ISystemLogic;
import com.npc.lottery.periods.entity.BJSCPeriodsInfo;
import com.npc.lottery.periods.entity.CQPeriodsInfo;
import com.npc.lottery.periods.entity.GDPeriodsInfo;
import com.npc.lottery.periods.entity.JSSBPeriodsInfo;
import com.npc.lottery.periods.entity.NCPeriodsInfo;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IJSSBPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.INCPeriodsInfoLogic;
import com.npc.lottery.replenish.vo.ReplenishVO;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;
import com.npc.lottery.util.LotteryComparator;
import com.npc.lottery.util.IPparse.IPSeeker;
import com.opensymphony.xwork2.ActionSupport;

public class BaseLotteryAction extends ActionSupport {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	protected IGDPeriodsInfoLogic periodsInfoLogic;
	protected IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
	protected ICQPeriodsInfoLogic icqPeriodsInfoLogic;
	protected ISystemLogic systemLogic;
	protected ISubAccountInfoLogic subAccountInfoLogic;
	protected IAuthorizLogic authorizLogic;
	//add by peter
	protected IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic;
	protected INCPeriodsInfoLogic ncPeriodsInfoLogic;
	private static Logger logger = Logger.getLogger(BaseLotteryAction.class);
	
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
	
	public MemberUser getCurrentUser()
	{
		
		return  (MemberUser) this.getRequest().getSession(true).getAttribute(Constant.MEMBER_LOGIN_INFO_IN_SESSION);

		
	}
	
	public ManagerUser getCurrentManagerUser()
	{
		
		return  (ManagerUser) this.getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		
		
	}
	
	//按号球排序
		public static Map sortByBall(Map<String, ReplenishVO> map) {  
	        List list = new LinkedList(map.entrySet());  
	        Collections.sort(list, new Comparator(){  
	               public int compare(Object o1, Object o2) {  
	            	   String playTypeCode1 = ((ReplenishVO) ((Map.Entry) o1).getValue()).getPlayFinalType();
						String playTypeCode2 = ((ReplenishVO) ((Map.Entry) o2).getValue()).getPlayFinalType();
						return new LotteryComparator().compare(playTypeCode1, playTypeCode2);
						// ((Comparable) ( (ReplenishVO)((Map.Entry)o1).getValue()).getPlayTypeName() )
						// .compareTo( ((ReplenishVO)(((Map.Entry)o2).getValue())).getPlayTypeName() );
	               }  
	        });  
	        Map result = new LinkedHashMap();  
	      
	        for (Iterator it = list.iterator(); it.hasNext();) {  
	              Map.Entry entry = (Map .Entry) it.next();  
	              result.put(entry.getKey(), entry.getValue());  
	        }  
	        return result;  
	    } 
		public static Map sortByBallInt(Map<String, ReplenishVO> map) {  
			List list = new LinkedList(map.entrySet());  
			Collections.sort(list, new Comparator(){  
				public int compare(Object o1, Object o2) {  
					Integer v1 = Integer.valueOf(((ReplenishVO)((Map.Entry)o1).getValue()).getPlayTypeName());
					Integer v2 = Integer.valueOf(((ReplenishVO)((Map.Entry)o2).getValue()).getPlayTypeName());
					/*return ((Comparable) (   (ReplenishVO)((Map.Entry)o1).getValue()).getPlayTypeName()   )  
							.compareTo(  ((ReplenishVO)(((Map.Entry)o2).getValue())).getPlayTypeName()   );  */
					return ((Comparable) v1   )  
							.compareTo(  v2   ); 
				}  
			});  
			Map result = new LinkedHashMap();  
			
			for (Iterator it = list.iterator(); it.hasNext();) {  
				Map.Entry entry = (Map .Entry) it.next();  
				result.put(entry.getKey(), entry.getValue());  
			}  
			return result;  
		} 
		//按亏排序
		@SuppressWarnings("unchecked")
		public static Map sortByLose(Map<String, ReplenishVO> map) {  
			List list = new LinkedList(map.entrySet());  
			
			Collections.sort(list, new Comparator(){  
			public int compare(Object o1, Object o2) {
				if (((ReplenishVO) ((Map.Entry) o1).getValue()).getLoseMoney() == ((ReplenishVO) (((Map.Entry) o2).getValue())).getLoseMoney()) {
					String playTypeCode1 = ((ReplenishVO) ((Map.Entry) o1).getValue()).getPlayFinalType();
					String playTypeCode2 = ((ReplenishVO) ((Map.Entry) o2).getValue()).getPlayFinalType();
					return new LotteryComparator().compare(playTypeCode1, playTypeCode2);
					// ((Comparable) ( (ReplenishVO)((Map.Entry)o1).getValue()).getPlayTypeName() )
					// .compareTo( ((ReplenishVO)(((Map.Entry)o2).getValue())).getPlayTypeName() );
				} else {
					return ((Comparable) ((ReplenishVO) ((Map.Entry) o1).getValue()).getLoseMoney()).compareTo(((ReplenishVO) (((Map.Entry) o2)
							.getValue())).getLoseMoney());
				}
			}
		});
			Map result = new LinkedHashMap();  
			
			for (Iterator it = list.iterator(); it.hasNext();) {  
				Map.Entry entry = (Map .Entry) it.next();  
				result.put(entry.getKey(), entry.getValue());  
			}  
			return result;  
		} 
		//按盈排序
		@SuppressWarnings("unchecked")
		public static Map sortByWin(Map<String, ReplenishVO> map) {  
			List list = new LinkedList(map.entrySet());  
			Collections.sort(list, new Comparator(){  
				public int compare(Object o1, Object o2) { 
					if(((ReplenishVO)((Map.Entry)o1).getValue()).getLoseMoney()==((ReplenishVO)(((Map.Entry)o2).getValue())).getLoseMoney()){
						String playTypeCode1 = ((ReplenishVO) ((Map.Entry) o1).getValue()).getPlayFinalType();
						String playTypeCode2 = ((ReplenishVO) ((Map.Entry) o2).getValue()).getPlayFinalType();
						return new LotteryComparator().compare(playTypeCode1, playTypeCode2);
						// ((Comparable) ( (ReplenishVO)((Map.Entry)o1).getValue()).getPlayTypeName() )
						// .compareTo( ((ReplenishVO)(((Map.Entry)o2).getValue())).getPlayTypeName() );
					}else{
					return ((Comparable) (   (ReplenishVO)((Map.Entry)o2).getValue()).getLoseMoney()   )  
							.compareTo(  ((ReplenishVO)(((Map.Entry)o1).getValue())).getLoseMoney()   );  
					}
				}  
			});  
			Map result = new LinkedHashMap();  
			
			for (Iterator it = list.iterator(); it.hasNext();) {  
				Map.Entry entry = (Map .Entry) it.next();  
				result.put(entry.getKey(), entry.getValue());  
			}  
			return result;  
		} 
		//按sorNo排序
		@SuppressWarnings("unchecked")
		public static Map sortBySortNo(Map<String, ReplenishVO> map) {  
			List list = new LinkedList(map.entrySet());  
			Collections.sort(list, new Comparator(){  
				public int compare(Object o1, Object o2) {  
					String playTypeCode1 = ((ReplenishVO) ((Map.Entry) o1).getValue()).getPlayFinalType();
					String playTypeCode2 = ((ReplenishVO) ((Map.Entry) o2).getValue()).getPlayFinalType();
					return new LotteryComparator().compare(playTypeCode1, playTypeCode2);
					// ((Comparable) ( (ReplenishVO)((Map.Entry)o1).getValue()).getPlayTypeName() )
					// .compareTo( ((ReplenishVO)(((Map.Entry)o2).getValue())).getPlayTypeName() ); 
				}  
			});  
			Map result = new LinkedHashMap();  
			
			for (Iterator it = list.iterator(); it.hasNext();) {  
				Map.Entry entry = (Map .Entry) it.next();  
				result.put(entry.getKey(), entry.getValue());  
			}  
			return result;  
		} 	
		
		//按sorNo排序LM
		@SuppressWarnings("unchecked")
		public static Map sortBySortNo_LM(Map<String, ReplenishVO> map) {  
			List list = new LinkedList(map.entrySet());  
			Collections.sort(list, new Comparator(){  
				public int compare(Object o1, Object o2) {  
					return ((Comparable) (   (ReplenishVO)((Map.Entry)o1).getValue()).getSortNo()   )  
							.compareTo(  ((ReplenishVO)(((Map.Entry)o2).getValue())).getSortNo()   );  
				}  
			});  
			Map result = new LinkedHashMap();  
			
			for (Iterator it = list.iterator(); it.hasNext();) {  
				Map.Entry entry = (Map .Entry) it.next();  
				result.put(entry.getKey(), entry.getValue());  
			}  
			return result;  
		} 	
	
	//IP归属地
	public String getIpAddress(String IP) {
		String k="";
		try {
			 k=BaseLotteryAction.class.getResource("/qqwry.dat").getFile();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	File file=new File(k);
    	String path=file.getPath();
    	String fileName=file.getName();
    	String dir=path.substring(0,path.indexOf(fileName));

		//IPSeeker ip=new IPSeeker("QQWry.Dat","E:/EclipseSpace/Lottery/WEB-INF/src/com/npc/lottery/util/IPparse/");  
		IPSeeker ipSeeker=new IPSeeker("qqwry.dat",dir);
		IP = IP.replace(":", ".");
		try {
			IP = ipSeeker.getIPLocation(IP).getCountry()+":"+ipSeeker.getIPLocation(IP).getArea();
		} catch (Exception e) {
			IP = ":";
		}
		return IP;
	}
	
	
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	
	public ServletContext getApplication() {
		return ServletActionContext.getServletContext();
	}

	
	public String ajax(String content, String type) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	protected CQPeriodsInfo getCQRunningPeriods()
	{
		
		
//		List<Criterion> filtersKP = new ArrayList<Criterion>();
//		filtersKP.add(Restrictions.eq("state",Constant.OPEN_STATUS));
		
		CQPeriodsInfo runningPeriods=icqPeriodsInfoLogic.findCurrentPeriod();

		if(null == runningPeriods){
			logger.info("拿不到重庆当前盘期");
		}else{
			Date now = new Date();
			if(now.after(runningPeriods.getStopQuotTime())){
				//当前时间在封盘时间之后,状态为封盘
				runningPeriods.setState(Constant.STOP_STATUS);
				logger.info("当前重庆是封盘状态盘期为"+runningPeriods.getPeriodsNum());
			}else{
				//当前时间在封盘时间之前,状态为开盘
				runningPeriods.setState(Constant.OPEN_STATUS);
			}
		}
		
		/*if(runningPeriods==null){
			logger.info("拿不到重庆开盘状态盘期");
			List<Criterion> filtersFP = new ArrayList<Criterion>();
			filtersFP.add(Restrictions.eq("state",Constant.STOP_STATUS));
			runningPeriods=icqPeriodsInfoLogic.queryStopPeriods(Constant.STOP_STATUS);
			if(runningPeriods!=null){
				logger.info("当前重庆是封盘状态盘期为"+runningPeriods.getPeriodsNum());
			}else{
				logger.info("拿不到重庆开盘状态盘期,也拿不到重庆封盘盘期 系统为 未看盘状态");
			}
		}*/
		CQPeriodsInfo lastcqp=icqPeriodsInfoLogic.queryLastLotteryPeriods();
		this.getRequest().setAttribute("LastLotteryPeriods", lastcqp);
		//CQPeriodsInfo lastwkpqp=icqPeriodsInfoLogic.queryLastNotOpenPeriods();
		//this.getRequest().setAttribute("LastwkpPeriods", lastwkpqp);
		return runningPeriods;
		
		
		
	}
	
	protected BJSCPeriodsInfo getBJSCRunningPeriods()
	{
		String shopCode=this.getCurrentUser().getShopsInfo().getShopsCode();
		String kpflag=systemLogic.checkAutoOddsByShopCode(shopCode, "BJSC_PERIODSTATE");
		if(kpflag==null||"0".equals(kpflag))
		{
			logger.info(" 北京盘期设置为不開盤");
			return null;
		}
//		List<Criterion> filtersKP = new ArrayList<Criterion>();
//		filtersKP.add(Restrictions.eq("state",Constant.OPEN_STATUS));
		BJSCPeriodsInfo runningPeriods=bjscPeriodsInfoLogic.findCurrentPeriod();
		if(null == runningPeriods){
			logger.info("拿不到北京赛车当前盘期");
		}else{
			Date now = new Date();
			if(now.after(runningPeriods.getStopQuotTime())){
				//当前时间在封盘时间之后,状态为封盘
				runningPeriods.setState(Constant.STOP_STATUS);
				logger.info("当前北京赛车是封盘状态盘期为"+runningPeriods.getPeriodsNum());
			}else{
				//当前时间在封盘时间之前,状态为开盘
				runningPeriods.setState(Constant.OPEN_STATUS);
			}
		}
		
		/*BJSCPeriodsInfo runningPeriods=bjscPeriodsInfoLogic.queryByPeriodsStatus(Constant.OPEN_STATUS);
		if(runningPeriods==null){
			logger.info("拿不到北京赛车开盘状态盘期");
			List<Criterion> filtersFP = new ArrayList<Criterion>();
			filtersFP.add(Restrictions.eq("state",Constant.STOP_STATUS));
			runningPeriods=bjscPeriodsInfoLogic.queryStopPeriods(Constant.STOP_STATUS);
			if(runningPeriods!=null){
				logger.info("当前北京赛车是封盘状态盘期为"+runningPeriods.getPeriodsNum());
			}else{
				logger.info("拿不到北京赛车开盘状态盘期,也拿不到北京赛车封盘盘期 系统为 未看盘状态");
			}
		}*/
		BJSCPeriodsInfo lastcqp=bjscPeriodsInfoLogic.queryLastLotteryPeriods();
		this.getRequest().setAttribute("LastLotteryPeriods", lastcqp);

		return runningPeriods;
		
		
		
	}

	/**
	 * add by peter for K3
	 * 
	 * @return
	 */
	protected JSSBPeriodsInfo getJSSBPeriodsInfo() {
		
		JSSBPeriodsInfo runningPeriods = jssbPeriodsInfoLogic.findCurrentPeriod();
		if(null == runningPeriods){
			logger.info("拿不到江苏骰宝当前盘期");
		}else{
			Date now = new Date();
			if(now.after(runningPeriods.getStopQuotTime())){
				//当前时间在封盘时间之后,状态为封盘
				runningPeriods.setState(Constant.STOP_STATUS);
				logger.info("当前江苏骰宝是封盘状态盘期为"+runningPeriods.getPeriodsNum());
			}else{
				//当前时间在封盘时间之前,状态为开盘
				runningPeriods.setState(Constant.OPEN_STATUS);
			}
		}
		
		/*List<Criterion> filtersKP = new ArrayList<Criterion>();
		filtersKP.add(Restrictions.eq("state", Constant.OPEN_STATUS));
		JSSBPeriodsInfo runningPeriods = jssbPeriodsInfoLogic.queryByPeriodsStatus(Constant.OPEN_STATUS);
		if (runningPeriods == null) {
			logger.info("拿不到江苏骰宝开盘状态盘期");
			List<Criterion> filtersFP = new ArrayList<Criterion>();
			filtersFP.add(Restrictions.eq("state", Constant.STOP_STATUS));
			runningPeriods = jssbPeriodsInfoLogic.queryStopPeriods(Constant.STOP_STATUS);
			if (runningPeriods != null)
				logger.info("当前江苏骰宝是封盘状态盘期为" + runningPeriods.getPeriodsNum());
			else
				logger.info("拿不到江苏骰宝开盘状态盘期,也拿不到江苏骰宝封盘盘期 系统为 未开盘状态");
		}*/
		JSSBPeriodsInfo lastcqp = jssbPeriodsInfoLogic.queryLastLotteryPeriods();
		this.getRequest().setAttribute("LastLotteryPeriods", lastcqp);

		return runningPeriods;

	}
	
	
	protected GDPeriodsInfo getGDRunningPeriods() 
	{
		GDPeriodsInfo runningPeriods=periodsInfoLogic.findCurrentPeriod();
		if(null == runningPeriods){
			logger.info("拿不到广东当前盘期");
		}else{
			Date now = new Date();
			if(now.after(runningPeriods.getStopQuotTime())){
				//当前时间在封盘时间之后,状态为封盘
				runningPeriods.setState(Constant.STOP_STATUS);
				logger.info("当前广东是封盘状态盘期为"+runningPeriods.getPeriodsNum());
			}else{
				//当前时间在封盘时间之前,状态为开盘
				runningPeriods.setState(Constant.OPEN_STATUS);
			}
		}
		
		/*List<Criterion> filtersKP = new ArrayList<Criterion>();
		filtersKP.add(Restrictions.eq("state",Constant.OPEN_STATUS));
		GDPeriodsInfo runningPeriods=periodsInfoLogic.queryByPeriodsStatus(Constant.OPEN_STATUS);
		if(runningPeriods==null)
		{
			logger.info("拿不到广东开盘状态盘期");	
		List<Criterion> filtersFP = new ArrayList<Criterion>();
		filtersFP.add(Restrictions.eq("state",Constant.STOP_STATUS));
		runningPeriods=periodsInfoLogic.queryStopPeriods(Constant.STOP_STATUS);
		
		}*/
		GDPeriodsInfo lastcqp=periodsInfoLogic.queryLastLotteryPeriods();
		this.getRequest().setAttribute("LastLotteryPeriods", lastcqp);
		//GDPeriodsInfo lastwkpqp=periodsInfoLogic.queryLastNotOpenPeriods();
		//this.getRequest().setAttribute("LastwkpPeriods", lastwkpqp);
		
		return runningPeriods;
		
		
		
		
	}
	protected NCPeriodsInfo getXYNCRunningPeriods() 
	{
		
		NCPeriodsInfo runningPeriods=ncPeriodsInfoLogic.findCurrentPeriod();
		if(null == runningPeriods){
			logger.info("拿不到农场当前盘期");
		}else{
			Date now = new Date();
			if(now.after(runningPeriods.getStopQuotTime())){
				//当前时间在封盘时间之后,状态为封盘
				runningPeriods.setState(Constant.STOP_STATUS);
				logger.info("当前农场是封盘状态盘期为"+runningPeriods.getPeriodsNum());
			}else{
				//当前时间在封盘时间之前,状态为开盘
				runningPeriods.setState(Constant.OPEN_STATUS);
			}
		}
		/*List<Criterion> filtersKP = new ArrayList<Criterion>();
		filtersKP.add(Restrictions.eq("state",Constant.OPEN_STATUS));
		NCPeriodsInfo runningPeriods=ncPeriodsInfoLogic.queryByPeriodsStatus(Constant.OPEN_STATUS);
		if(runningPeriods==null)
		{
			logger.info("拿不到农场开盘状态盘期");	
			List<Criterion> filtersFP = new ArrayList<Criterion>();
			filtersFP.add(Restrictions.eq("state",Constant.STOP_STATUS));
			runningPeriods=ncPeriodsInfoLogic.queryStopPeriods(Constant.STOP_STATUS);
			
		}*/
		GDPeriodsInfo lastcqp=periodsInfoLogic.queryLastLotteryPeriods();
		this.getRequest().setAttribute("LastLotteryPeriods", lastcqp);
		
		return runningPeriods;
		
		
		
		
	}
	
	//获取子帐号信息start
	public SubAccountInfo getSubAccountInfo(ManagerUser userInfo){
		SubAccountInfo subAccountInfo;
		subAccountInfo = subAccountInfoLogic.querySubAccountInfo("account",userInfo.getAccount());
        Map<String,String> autoSubMap = new HashMap<String, String>();
        autoSubMap = getAutoSub(subAccountInfo);
        getRequest().setAttribute("subAccountInfo",subAccountInfo);
        getRequest().setAttribute("replenishment",autoSubMap.get("replenishment"));
        getRequest().setAttribute("offLineAccount",autoSubMap.get("offLineAccount"));
        getRequest().setAttribute("subAccount",autoSubMap.get("subAccount"));
        getRequest().setAttribute("crossReport",autoSubMap.get("crossReport"));
        getRequest().setAttribute("classifyReport",autoSubMap.get("classifyReport"));
        
        getRequest().setAttribute("odd",autoSubMap.get("odd"));
        getRequest().setAttribute("outReplenishManager",autoSubMap.get("outReplenishManager"));
        getRequest().setAttribute("oddLog",autoSubMap.get("oddLog"));
        getRequest().setAttribute("sysInit",autoSubMap.get("sysInit"));
        getRequest().setAttribute("tradingSet",autoSubMap.get("tradingSet"));
        getRequest().setAttribute("message",autoSubMap.get("message"));
        getRequest().setAttribute("searchBill",autoSubMap.get("searchBill"));
        getRequest().setAttribute("backsysRole",autoSubMap.get("backsysRole"));
        getRequest().setAttribute("cancelBill",autoSubMap.get("cancelBill"));
        getRequest().setAttribute("jszd",autoSubMap.get("jszd"));
        return subAccountInfo;
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
	//获取子帐号信息end
	
	/**
	 * 当到子帐号的上一级帐号信息
	 * @param userInfoOld 要转换的对象
	 * @return
	 */
	public ManagerUser getSubAccountParent(ManagerUser userInfoOld){
		ManagerUser userInfoNew = new ManagerUser();	
		ManagerUser userInfoSub = new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfoNew, userInfoOld);
		} catch (Exception e) {
			logger.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
		//子帐号处理
		if(userInfoNew.getUserType().equals(ManagerStaff.USER_TYPE_SUB)){
			//获取子帐户的上级
			userInfoSub = subAccountInfoLogic.changeSubAccountInfo(userInfoNew);
			userInfoOld.setID(userInfoSub.getID());
			userInfoOld.setUserType(userInfoSub.getUserType());
		}
		return userInfoOld;
		
	}
	
    private ManagerUser getInfo() {
        ManagerUser userInfo = (ManagerUser) getRequest().getSession(true)
                .getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
        return userInfo;
    }
	
	
	public String ajaxText(String text) {
		return ajax(text, "text/plain");
	}

	
	public String ajaxHtml(String html) {
		return ajax(html, "text/html");
	}

	
	public String ajaxXml(String xml) {
		return ajax(xml, "text/xml");
	}

	
	public String ajaxJson(String jsonString) {
		return ajax(jsonString, "text/html");
	}
	
	
	public String ajaxJson(Map<String, String> jsonMap) {
		return ajax(JSONObject.toJSONString(jsonMap), "text/html");
	}
	
	public String ajaxObjectJson(Map<String, Object> jsonMap) {
		return ajax(JSONObject.toJSONString(jsonMap), "text/html");
	}

	
	public void setResponseNoCache() {
		getResponse().setHeader("progma", "no-cache");
		getResponse().setHeader("Cache-Control", "no-cache");
		getResponse().setHeader("Cache-Control", "no-store");
		getResponse().setDateHeader("Expires", 0);
	}
	
	protected String findParameter(String name) {
		return getRequest().getParameter(name);
	}

	protected float findParamFloat(String param) {
		return Float.parseFloat(findParameter(param));
	}

	protected short findParamShort(String param) {
		return Short.parseShort(findParameter(param));
	}

	protected int findParamInt(String param) {
		return Integer.parseInt(findParameter(param));
	}

	protected long findParamLong(String param) {
		return Long.parseLong(findParameter(param));
	}

	public IAuthorizLogic getAuthorizLogic() {
		return authorizLogic;
	}

	public void setAuthorizLogic(IAuthorizLogic authorizLogic) {
		this.authorizLogic = authorizLogic;
	}


	public IGDPeriodsInfoLogic getPeriodsInfoLogic() {
		return periodsInfoLogic;
	}


	public void setPeriodsInfoLogic(IGDPeriodsInfoLogic periodsInfoLogic) {
		this.periodsInfoLogic = periodsInfoLogic;
	}


	public IBJSCPeriodsInfoLogic getBjscPeriodsInfoLogic() {
		return bjscPeriodsInfoLogic;
	}


	public void setBjscPeriodsInfoLogic(IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic) {
		this.bjscPeriodsInfoLogic = bjscPeriodsInfoLogic;
	}


	public ICQPeriodsInfoLogic getIcqPeriodsInfoLogic() {
		return icqPeriodsInfoLogic;
	}


	public void setIcqPeriodsInfoLogic(ICQPeriodsInfoLogic icqPeriodsInfoLogic) {
		this.icqPeriodsInfoLogic = icqPeriodsInfoLogic;
	}

	public String getReplenishment() {
		return replenishment;
	}

	public String getOffLineAccount() {
		return offLineAccount;
	}

	public String getSubAccount() {
		return subAccount;
	}

	public String getCrossReport() {
		return crossReport;
	}

	public String getClassifyReport() {
		return classifyReport;
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

	public ISubAccountInfoLogic getSubAccountInfoLogic() {
		return subAccountInfoLogic;
	}

	public void setSubAccountInfoLogic(ISubAccountInfoLogic subAccountInfoLogic) {
		this.subAccountInfoLogic = subAccountInfoLogic;
	}

	public void setReplenishment(String replenishment) {
		this.replenishment = replenishment;
	}

	public void setOffLineAccount(String offLineAccount) {
		this.offLineAccount = offLineAccount;
	}

	public void setSubAccount(String subAccount) {
		this.subAccount = subAccount;
	}

	public void setCrossReport(String crossReport) {
		this.crossReport = crossReport;
	}

	public void setClassifyReport(String classifyReport) {
		this.classifyReport = classifyReport;
	}

	public ISystemLogic getSystemLogic() {
		return systemLogic;
	}

	public void setSystemLogic(ISystemLogic systemLogic) {
		this.systemLogic = systemLogic;
	}

	public IJSSBPeriodsInfoLogic getJssbPeriodsInfoLogic() {
		return jssbPeriodsInfoLogic;
	}

	public void setJssbPeriodsInfoLogic(IJSSBPeriodsInfoLogic jssbPeriodsInfoLogic) {
		this.jssbPeriodsInfoLogic = jssbPeriodsInfoLogic;
	}

	public INCPeriodsInfoLogic getNcPeriodsInfoLogic() {
		return ncPeriodsInfoLogic;
	}

	public void setNcPeriodsInfoLogic(INCPeriodsInfoLogic ncPeriodsInfoLogic) {
		this.ncPeriodsInfoLogic = ncPeriodsInfoLogic;
	}
	
}
