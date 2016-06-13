package com.npc.lottery.user.action;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.UserCommissionDefault;
import com.npc.lottery.user.logic.interf.IUserCommissionDefault;

public class UserCommissionAction extends BaseLotteryAction{

    /**
     * 
     */
    private static final long serialVersionUID = 5357667482302467290L;
    private static Logger logger = Logger.getLogger(UserCommissionAction.class);
    private List<UserCommissionDefault> commissionsList;
    private IUserCommissionDefault userCommissionDefaultLogic;
    private IShopOddsLogic shopOddsLogic;
    private String type="userCommission";
    private String shopsCode;
    
    
 
    public void validateUpdateCommission() {
    	logger.info("开始校验");
    /*	if(commissionsList==null)
    		this.addActionError("请求参数不能为空");
        else
        {
    	for (int i = 0; i < commissionsList.size(); i++) {
    		UserCommissionDefault	userDefaultCom=commissionsList.get(i);
    		Double ca=userDefaultCom.getCommissionA();
    		Double cb=userDefaultCom.getCommissionB();
    		Double cc=userDefaultCom.getCommissionC();
    		
    			
		}
        }*/
    	logger.info("结束校验");
    }
    //跳到默认退水页面
    public String queryCommission(){
    	try {
    		String code = getRequest().getParameter("code");
    	    if (code!=null){
    	    	shopsCode = code;
    	    }
    	    List<ChiefStaffExt> cList = shopOddsLogic.findChiefByShopsCode(shopsCode); 
    		Long chiefID = cList.get(0).getManagerStaffID();	
            commissionsList = userCommissionDefaultLogic.queryCommissionDefault(chiefID);            
            if(commissionsList != null && commissionsList.size() != 0){
                return SUCCESS;
            }else{
                return "default";
            }
		} catch (Exception e) {
			e.printStackTrace();
			return "failure";
		}    	
    }
    
    public String saveCommission(){
        try {
        	List<ChiefStaffExt> cList = shopOddsLogic.findChiefByShopsCode(shopsCode);
        	Long chiefID = cList.get(0).getManagerStaffID();
            if(commissionsList != null && commissionsList.size() != 0){
            	UserCommissionDefault commissionDefault = null;
                for (int i=0; i<commissionsList.size(); i++){	
                	commissionDefault = (UserCommissionDefault)commissionsList.get(i);
                    commissionDefault.setCreateTime(new Date());
                    commissionDefault.setUserId(chiefID);
                    commissionDefault.setCreateUser(chiefID);
                    userCommissionDefaultLogic.saveCommission(commissionDefault);
                    
                }
            }
            return SUCCESS;
        } catch (Exception e) {
			e.printStackTrace();
			return "failure";
		}             
    }
    
    public String updateCommission(){
    	try {
    		List<ChiefStaffExt> cList = shopOddsLogic.findChiefByShopsCode(shopsCode);
    		Long chiefID = cList.get(0).getManagerStaffID();
            if(commissionsList != null && commissionsList.size() != 0){
            	UserCommissionDefault commissionDefault = null;
            	for (int i=0; i<commissionsList.size(); i++){	
                	commissionDefault = (UserCommissionDefault)commissionsList.get(i);
                    commissionDefault.setModifyTime(new Date());
                    commissionDefault.setUserId(chiefID);
                    commissionDefault.setCreateUser(chiefID);
                    userCommissionDefaultLogic.updateCommissionDefault(commissionDefault);
                }
            }
            return SUCCESS;
    	} catch (Exception e) {
 			e.printStackTrace();
 			return "failure";
 		}    
       
        
    }
    public IUserCommissionDefault getUserCommissionDefaultLogic() {
        return userCommissionDefaultLogic;
    }

    public void setUserCommissionDefaultLogic(
            IUserCommissionDefault userCommissionDefaultLogic) {
        this.userCommissionDefaultLogic = userCommissionDefaultLogic;
    }

    public List<UserCommissionDefault> getCommissionsList() {
        return commissionsList;
    }

    public void setCommissionsList(List<UserCommissionDefault> commissionsList) {
        this.commissionsList = commissionsList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public IShopOddsLogic getShopOddsLogic() {
		return shopOddsLogic;
	}

	public void setShopOddsLogic(IShopOddsLogic shopOddsLogic) {
		this.shopOddsLogic = shopOddsLogic;
	}

	public String getShopsCode() {
		return shopsCode;
	}

	public void setShopsCode(String shopsCode) {
		this.shopsCode = shopsCode;
	}
	
    
}
