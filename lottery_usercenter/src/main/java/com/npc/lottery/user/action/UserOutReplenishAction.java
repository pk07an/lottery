package com.npc.lottery.user.action;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.alibaba.fastjson.JSONObject;
import com.npc.lottery.common.Constant;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.entity.OutReplenishStaffExt;
import com.npc.lottery.user.entity.UserCommission;
import com.npc.lottery.user.logic.interf.IUserCommissionLogic;
import com.npc.lottery.user.logic.interf.IUserOutReplenishLogic;
import com.npc.lottery.util.MD5;
import com.npc.lottery.util.Page;

public class UserOutReplenishAction extends BaseUserAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3800671962314380484L;
	Logger log = Logger.getLogger(UserOutReplenishAction.class);
	
	public String queryUserOutReplenish(){
		ManagerUser userInfo = this.getInfo();
		//子帐号处理*********START
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
		   
		List<Criterion> filters = new ArrayList<Criterion>();
		filters.add(Restrictions.eq("parentStaff", userInfoNew.getID().intValue()));
		filters.add(Restrictions.eq("flag", ManagerStaff.FLAG_USE));
		Page<OutReplenishStaffExt> page = new Page<OutReplenishStaffExt>(20);
		int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        page.setOrderBy("createDate");
        page.setOrder("asc");
        try {
	        page = userOutReplenishLogic.findUserOutReplenish(page,filters
	                .toArray(new Criterion[filters.size()]));
	        this.getRequest().setAttribute("page", page);
        } catch (Exception e) {
            log.error("<--分頁 查詢異常：queryUserOutReplenish-->",e);
            return "failure";
        }
		return SUCCESS;
	}
	
	//删除该出货会员
	public String deleteUserOutReplenish(){
		String userId = getRequest().getParameter("id");
		Long id = (long)0;
		try {
			if(userId!=null){
				id = Long.valueOf(userId);
			}
		} catch (Exception e) {
			log.error("<--數據轉換出錯string換轉成Long：deleteUserOutReplenish-->",e);
            return "failure";
		}
		userOutReplenishLogic.deleteUserOutReplenish(id);
		return SUCCESS;
	} 
	
	public String registerUserOutReplenish(){
		return SUCCESS;
	}
	
	public String findUserOutReplenish(){
		String ID = getRequest().getParameter("ID");
		Long userId = (long) 0;
		if(ID!=null){
			userId = Long.valueOf(ID);
		}
		List<Criterion> filters = new ArrayList<Criterion>();
		filters.add(Restrictions.eq("ID", userId));
		Page<OutReplenishStaffExt> page = new Page<OutReplenishStaffExt>(1);
		int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        try {
	        page = userOutReplenishLogic.findUserOutReplenish(page,filters
	                .toArray(new Criterion[filters.size()]));
	        outReplenishStaff = page.getResult().get(0);
        } catch (Exception e) {
            log.error("<--分頁 查詢異常：queryUserOutReplenish-->",e);
            return "failure";
        }
		return SUCCESS;
	}
	
	
	
	public String saveUserOutReplenish(){
		ManagerUser user = this.getInfo();
		
		//子帐号处理*********START
		ManagerUser userInfoNew = new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfoNew, user);
		} catch (Exception e) {
		  log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
		   if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
			userInfoNew = getSubAccountParent(userInfoNew);	
		}	
		//子帐号处理*********END
		
		outReplenishStaff.setParentStaff(userInfoNew.getID().intValue());
		MD5 md5 = new MD5();
        String userPwdOrignMd5 = md5.getMD5ofStr(Constant.DEFAULT_PASSWORD).trim();
        outReplenishStaff.setUserPwd(userPwdOrignMd5);
		if(outReplenishStaff.getID()==null){
			outReplenishStaff.setCreateDate(new Date());
		}
		outReplenishStaff.setFlag(ManagerStaff.FLAG_USE);
		outReplenishStaff.setUserType(ManagerStaff.USER_TYPE_OUT_REPLENISH);
		try {
			userOutReplenishLogic.saveUserOutReplenish(outReplenishStaff);
		} catch (Exception e) {
			log.error("<--新建出貨會員異常：saveUserOutReplenish-->",e);
            return "failure";
		}
		return SUCCESS;
	}
	
	public String updateUserOutReplenish(){
		ManagerUser user = this.getInfo();
		
		//子帐号处理*********START
		ManagerUser userInfoNew = new ManagerUser();
		try {
			BeanUtils.copyProperties(userInfoNew, user);
		} catch (Exception e) {
		  log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
		}
		   if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
			userInfoNew = getSubAccountParent(userInfoNew);	
		}	
		//子帐号处理*********END
		
		outReplenishStaff.setParentStaff(userInfoNew.getID().intValue());
		outReplenishStaff.setFlag(ManagerStaff.FLAG_USE);
		outReplenishStaff.setUserType(ManagerStaff.USER_TYPE_OUT_REPLENISH);
		userOutReplenishLogic.updateUserOutReplenish(outReplenishStaff);
		return SUCCESS;
	}
	
	public String findUserOutCommission(){
		String userId = getRequest().getParameter("ID");
		Long id = (long)0;
		try {
			if(userId!=null){
				id = Long.valueOf(userId);
			}
		} catch (Exception e) {
			log.error("<--數據轉換出錯string換轉成Long：findUserOutCommission-->",e);
            return "failure";
		}
		Map<String,UserCommission> map=new HashMap<String,UserCommission>();
        List<UserCommission> userCommission = userCommissionLogic
                .queryCommission(id,ManagerStaff.USER_TYPE_OUT_REPLENISH);
        map=convertCommissionListToMap(userCommission);
        
        String jsonCommission=JSONObject.toJSONString(map);
        try {
            jsonCommission=URLEncoder.encode(jsonCommission,"utf-8");
        } catch (UnsupportedEncodingException e) {
            
            e.printStackTrace();
        }
       
        List<Criterion> filters = new ArrayList<Criterion>();
		filters.add(Restrictions.eq("ID", id));
		Page<OutReplenishStaffExt> page = new Page<OutReplenishStaffExt>(1);
        page.setPageNo(1);
        try {
	        page = userOutReplenishLogic.findUserOutReplenish(page,filters
	                .toArray(new Criterion[filters.size()]));
	        outReplenishStaff = page.getResult().get(0);
        } catch (Exception e) {
            log.error("<--分頁 查詢異常：findUserOutCommission-->",e);
            return "failure";
        }
        //準備擁金設置框的擁金列表
        java.util.HashMap mapC = new java.util.LinkedHashMap();
        Double i = (double) 100;
        //拥金列表里有70种拥金选项
        while(i>=70){
        	int intValue = 0;
        	if(String.valueOf(i).indexOf(".0")!=-1){
        		BigDecimal b = new BigDecimal(i.toString()); 
        		intValue = b.setScale(0, BigDecimal.ROUND_DOWN).intValue();  
        		mapC.put(intValue,intValue);
        	}else{
        		BigDecimal b = new BigDecimal(i.toString()); 
        		i = b.setScale(1, BigDecimal.ROUND_DOWN).doubleValue();  
        		mapC.put(i,i);
        	}
        	i = i - 0.1;
        }
        this.getRequest().setAttribute("mapC", mapC);
        this.getRequest().setAttribute("plate", outReplenishStaff.getPlate());
        this.getRequest().setAttribute("account", outReplenishStaff.getAccount());
        this.getRequest().setAttribute("id", outReplenishStaff.getID());
        this.getRequest().setAttribute("chsName", outReplenishStaff.getChsName());
        this.getRequest().setAttribute("commissions", map);
		return SUCCESS;
	}
	
	public String ajaxQueryCommission(){
    	String userId = getRequest().getParameter("id");
    	String typeCode = getRequest().getParameter("typeCode");
    	Long id = (long)0;
		try {
			if(userId!=null){
				id = Long.valueOf(userId);
			}
		} catch (Exception e) {
			log.error("<--數據轉換出錯string換轉成Long：ajaxQueryCommission-->",e);
            return "failure";
		}
    	List<UserCommission> commissions=userCommissionLogic.queryCommissionByType(id,ManagerStaff.USER_TYPE_OUT_REPLENISH,typeCode);
    	Map<String, String> map = new HashMap<String, String>();
    	UserCommission userCommission = commissions.get(0);
    	Double commission = 100-userCommission.getCommissionA();
    	Integer intValue = 0;
    	if(String.valueOf(commission).indexOf(".0")!=-1){
    		BigDecimal b = new BigDecimal(commission.toString()); 
    		intValue = b.setScale(0, BigDecimal.ROUND_DOWN).intValue();  
    		map.put("commission", intValue.toString());
    	}else{
    		BigDecimal b = new BigDecimal(commission.toString()); 
    		commission = b.setScale(1, BigDecimal.ROUND_DOWN).doubleValue();  
    		map.put("commission", commission.toString());
    	}
    	map.put("bettingQuotas", userCommission.getBettingQuotas().toString());
    	map.put("itemQuotas", userCommission.getItemQuotas().toString());
    	map.put("typeCode", typeCode);
        return this.ajaxJson(map);
    	
    }
    
    public String updateOutReplenishCommission(){
    	OutReplenishStaffExt outReplenishStaff = new OutReplenishStaffExt();
    	UserCommission userCommission = new UserCommission();
    	
    	String userId = getRequest().getParameter("id");
    	String typeCode = getRequest().getParameter("typeCode");
    	String commission = getRequest().getParameter("commission");
    	String bettingQuotas = getRequest().getParameter("bettingQuotas");
    	String itemQuotas = getRequest().getParameter("itemQuotas");
    	Long id = (long)0;
		try {
			if(userId!=null) {
				id = Long.valueOf(userId);
				outReplenishStaff.setID(id);
				outReplenishStaff.setUserType(ManagerStaff.USER_TYPE_OUT_REPLENISH);
			}
			if(commission!=null){
				Double commissionD = BigDecimal.valueOf(100).subtract(BigDecimal.valueOf(Double.valueOf(commission))).setScale(1, BigDecimal.ROUND_DOWN).doubleValue(); ;
				userCommission.setCommissionA(commissionD);
			}
			if(commission!=null) userCommission.setBettingQuotas(Integer.valueOf(bettingQuotas));
			if(commission!=null) userCommission.setItemQuotas(Integer.valueOf(itemQuotas));
		} catch (Exception e) {
			log.error("<--數據轉換出錯：updateOutReplenishCommission-->",e);
            return "failure";
		}
		try {
			userOutReplenishLogic.updateOutReplenishCommission(outReplenishStaff, userCommission, typeCode);
		} catch (Exception e) {
			log.error("<--更新出貨會員擁金設定時出錯：updateOutReplenishCommission-->",e);
            return "failure";
		}
		//重新查庫，顯示在頁面上
		List<UserCommission> commissions=userCommissionLogic.queryCommissionByType(id,ManagerStaff.USER_TYPE_OUT_REPLENISH,typeCode);
		Map<String, String> map = new HashMap<String, String>();
		UserCommission userCommissionOut = commissions.get(0);
    	Double commissionOut = 100-userCommissionOut.getCommissionA();
		Integer intValue = 0;
    	if(String.valueOf(commissionOut).indexOf(".0")!=-1){
    		BigDecimal b = new BigDecimal(commissionOut.toString()); 
    		intValue = b.setScale(0, BigDecimal.ROUND_DOWN).intValue();  
    		map.put("commission", intValue.toString());
    	}else{
    		BigDecimal b = new BigDecimal(commissionOut.toString()); 
    		commissionOut = b.setScale(1, BigDecimal.ROUND_DOWN).doubleValue();  
    		map.put("commission", commissionOut.toString());
    	}
    	map.put("bettingQuotas", userCommissionOut.getBettingQuotas().toString());
    	map.put("itemQuotas", userCommissionOut.getItemQuotas().toString());
		map.put("typeCode", typeCode);
		return this.ajaxJson(map);
    }
	
	private Map<String,UserCommission> convertCommissionListToMap(List<UserCommission> commissions)
    {
    	 Map<String,UserCommission> map=new HashMap<String,UserCommission>();
    	for (int i = 0; i < commissions.size(); i++) {
    		UserCommission	userCommsion=commissions.get(i);
    		
    		String key=userCommsion.getPlayFinalType();
    		key=key.replace("-", "_");
    		
    		map.put(key, userCommsion);
		}
    	return map;
    	
    }
	
	private OutReplenishStaffExt outReplenishStaff;
	private IUserOutReplenishLogic userOutReplenishLogic;
	private IUserCommissionLogic userCommissionLogic;
	private IReplenishLogic replenishLogic;


	public OutReplenishStaffExt getOutReplenishStaff() {
		return outReplenishStaff;
	}

	public void setOutReplenishStaff(OutReplenishStaffExt outReplenishStaff) {
		this.outReplenishStaff = outReplenishStaff;
	}

	public IUserOutReplenishLogic getUserOutReplenishLogic() {
		return userOutReplenishLogic;
	}

	public void setUserOutReplenishLogic(
			IUserOutReplenishLogic userOutReplenishLogic) {
		this.userOutReplenishLogic = userOutReplenishLogic;
	}

	public IUserCommissionLogic getUserCommissionLogic() {
		return userCommissionLogic;
	}

	public void setUserCommissionLogic(IUserCommissionLogic userCommissionLogic) {
		this.userCommissionLogic = userCommissionLogic;
	}

	public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}
	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}
	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

}
