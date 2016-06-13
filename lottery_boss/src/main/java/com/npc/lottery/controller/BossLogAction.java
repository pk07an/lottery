package com.npc.lottery.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.npc.lottery.common.action.BaseAdminLotteryAction;
import com.npc.lottery.util.Page;
import com.npc.lottery.sysmge.entity.BossLog;
import com.npc.lottery.sysmge.logic.interf.IBossLogLogic;

public class BossLogAction extends BaseAdminLotteryAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8891330176830899433L;

	private static Logger log = Logger.getLogger(BossLogAction.class);

	private IBossLogLogic bossLogLogic;
	
	private  String type = "explog";
	
	private String logMessage ;
	
    public String queryBossLog() throws Exception {
    	

//    	String logMessage = this.getRequest().getParameter("logMessage");
    	
    	List<Criterion> filtersPeriodInfo = new ArrayList<Criterion>();
//    	filtersPeriodInfo.add(Restrictions.eq("logState","0"));
        if(null != logMessage && !"".equals(logMessage))
        {
        	filtersPeriodInfo.add(Restrictions.ilike("logMessage",logMessage,MatchMode.ANYWHERE));	
        }
    	 Page<BossLog> page = new Page<BossLog>(10);
    	 
         int pageNo = 1;
         if (this.getRequest().getParameter("pageNo") != null)
             pageNo = this.findParamInt("pageNo");
         page.setPageNo(pageNo);
         page.setOrderBy("createDate");
         page.setOrder("desc");
         try {
             page = bossLogLogic.queryLogByPage(page,filtersPeriodInfo.toArray(new Criterion[filtersPeriodInfo.size()]));
             
             this.getRequest().setAttribute("page", page);
             
         } catch (Exception e) { 
             log.error("<--分頁 查詢異常：queryBossLog-->",e);
             return "exception";
         }
          
    	return SUCCESS;
    }


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public IBossLogLogic getBossLogLogic() {
		return bossLogLogic;
	}


	public void setBossLogLogic(IBossLogLogic bossLogLogic) {
		this.bossLogLogic = bossLogLogic;
	}


	public String getLogMessage() {
		return logMessage;
	}


	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

}
