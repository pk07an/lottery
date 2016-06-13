package com.npc.lottery.sysmge.entity;

import java.io.Serializable;
import java.util.Date;

import com.npc.lottery.common.action.BaseLotteryAction;

/**
 * 登陆日志表，对应数据表（TB_LOGIN_LOG_INFO）
 * 
 */
public class LoginLogInfo extends BaseLotteryAction implements Serializable {

    private Long ID;//ID

    private Long userId;//用户ID

    private String account;//登录账号

    private String userType;//用户类型

    private String shopsCode;//商铺编号

    private Date loginDate;//登录时间

    private String loginIp;//登录IP

    private String sessionId;//SESSION ID

    private Date logoutDate;//登出时间

    private String loginState;//登陆状态

    private String subLoginState;//子状态

    private String info;//信息

    private Long chiefStaffId;//总监ID

    private Long branchStaffId;//分公司ID

    private Long stockholderStaffId;//股东ID

    private Long genAgentStaffId;//总代理ID

    private Long agentStaffId;//代理ID

    private String chiefStaffAcc;//总监账号

    private String agentStaffAcc;//代理账号

    private String genAgentStaffAcc;//总代理账号

    private String stockholderStaffAcc;//股东账号

    private String branchStaffAcc;//分公司账号

    private String remark;//备注
    
    private String ipBelongTo;  //Ip归属地

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getShopsCode() {
        return shopsCode;
    }

    public void setShopsCode(String shopsCode) {
        this.shopsCode = shopsCode;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }

    public String getLoginState() {
        return loginState;
    }

    public void setLoginState(String loginState) {
        this.loginState = loginState;
    }

    public String getSubLoginState() {
        return subLoginState;
    }

    public void setSubLoginState(String subLoginState) {
        this.subLoginState = subLoginState;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getChiefStaffId() {
        return chiefStaffId;
    }

    public void setChiefStaffId(Long chiefStaffId) {
        this.chiefStaffId = chiefStaffId;
    }

    public Long getBranchStaffId() {
        return branchStaffId;
    }

    public void setBranchStaffId(Long branchStaffId) {
        this.branchStaffId = branchStaffId;
    }

    public Long getStockholderStaffId() {
        return stockholderStaffId;
    }

    public void setStockholderStaffId(Long stockholderStaffId) {
        this.stockholderStaffId = stockholderStaffId;
    }

    public Long getGenAgentStaffId() {
        return genAgentStaffId;
    }

    public void setGenAgentStaffId(Long genAgentStaffId) {
        this.genAgentStaffId = genAgentStaffId;
    }

    public Long getAgentStaffId() {
        return agentStaffId;
    }

    public void setAgentStaffId(Long agentStaffId) {
        this.agentStaffId = agentStaffId;
    }

    public String getChiefStaffAcc() {
        return chiefStaffAcc;
    }

    public void setChiefStaffAcc(String chiefStaffAcc) {
        this.chiefStaffAcc = chiefStaffAcc;
    }

    public String getAgentStaffAcc() {
        return agentStaffAcc;
    }

    public void setAgentStaffAcc(String agentStaffAcc) {
        this.agentStaffAcc = agentStaffAcc;
    }

    public String getGenAgentStaffAcc() {
        return genAgentStaffAcc;
    }

    public void setGenAgentStaffAcc(String genAgentStaffAcc) {
        this.genAgentStaffAcc = genAgentStaffAcc;
    }

    public String getStockholderStaffAcc() {
        return stockholderStaffAcc;
    }

    public void setStockholderStaffAcc(String stockholderStaffAcc) {
        this.stockholderStaffAcc = stockholderStaffAcc;
    }

    public String getBranchStaffAcc() {
        return branchStaffAcc;
    }

    public void setBranchStaffAcc(String branchStaffAcc) {
        this.branchStaffAcc = branchStaffAcc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getIpBelongTo() {
		/*String k="";
		try {
			 k=LoginLogInfo.class.getResource("/qqwry.dat").getFile();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	File file=new File(k);
    	String path=file.getPath();
    	String fileName=file.getName();
    	String dir=path.substring(0,path.indexOf(fileName));

		//IPSeeker ip=new IPSeeker("QQWry.Dat","E:/EclipseSpace/Lottery/WEB-INF/src/com/npc/lottery/util/IPparse/");  
		IPSeeker ip=new IPSeeker("qqwry.dat",dir);  
		this.ipBelongTo = ip.getIPLocation(this.loginIp).getCountry()+":"+ip.getIPLocation(this.loginIp).getArea();*/
		//return this.ipBelongTo;
		return this.getIpAddress(this.loginIp);
	}

	public void setIpBelongTo(String ipBelongTo) {
		this.ipBelongTo = ipBelongTo;
	}
}
