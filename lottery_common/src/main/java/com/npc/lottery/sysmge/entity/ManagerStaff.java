package com.npc.lottery.sysmge.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

import com.npc.lottery.common.Constant;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.util.ListEntity;
import com.npc.lottery.util.Tools;

/**
 * 管理用户基础类对应的实体类，对应的数据表为（TB_FRAME_MANAGER_STAFF）
 * 
 * @author none
 *
 */
public class ManagerStaff implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String USER_TYPE_SYS = "0";//用户类型：系统管理员

    public static final String USER_TYPE_MANAGER = "1";//用户类型：总管

    public static final String USER_TYPE_CHIEF = "2";//用户类型：总监

    public static final String USER_TYPE_BRANCH = "3";//用户类型：分公司

    public static final String USER_TYPE_STOCKHOLDER = "4";//用户类型：股东

    public static final String USER_TYPE_GEN_AGENT = "5";//用户类型：总代理

    public static final String USER_TYPE_AGENT = "6";//用户类型：代理用户

    public static final String USER_TYPE_SUB = "7";//用户类型：子账号
    
    public static final String USER_TYPE_OUT_REPLENISH = "8";//用户类型：出货会员
   
    public final static String FLAG_USE = "0";//状态：用户有效

    public final static String FLAG_FORBID = "1";//状态：禁用

    public final static String FLAG_FREEZE = "2";//状态：冻结

    public final static String FLAG_DELETE = "3";//状态：删除

    public final static String REPLENIS_USE = "0";//允许走飞

    public final static String REPLENIS_FORBID = "1";//禁止走飞
    
    public final static String CHIEF_SUB_ROLE_REPLENISH = "CHIEF_SUB_ROLE_REPLENISH";     //手工补货、自动补货设定（及变更记录）
    
    public final static String CHIEF_SUB_ROLE_OFFLINE = "CHIEF_SUB_ROLE_OFFLINE";     // 下线账号管理
    
    public final static String CHIEF_SUB_ROLE_SUB = "CHIEF_SUB_ROLE_SUB";     //子账号管理
    
    public final static String CHIEF_SUB_ROLE_DELIVERY = "CHIEF_SUB_ROLE_DELIVERY";     //总监交收报表
    
    public final static String CHIEF_SUB_ROLE_CLASSIFY = "CHIEF_SUB_ROLE_CLASSIFY";     //总监分类报表
    
    public final static String CHIEF_SUB_ROLE_ODD = "CHIEF_SUB_ROLE_ODD";     //操盤權限、輸贏分析
    //public final static String CHIEF_SUB_ROLE_OUT_REPLENISH = "CHIEF_SUB_ROLE_OUT_REPLENISH";     //補貨（外補做帳）
    public final static String CHIEF_SUB_ROLE_OUT_USER_MANAGER = "CHIEF_SUB_ROLE_OUT_USER_MANAGER";     //出貨會員管理
    public final static String CHIEF_SUB_ROLE_ODD_LOG = "CHIEF_SUB_ROLE_ODD_LOG";     //每期彩票管理、操盤記錄查詢
    public final static String CHIEF_SUB_ROLE_SYS_INIT = "CHIEF_SUB_ROLE_SYS_INIT";     //系統初始設定
    public final static String CHIEF_SUB_ROLE_TRADING_SET = "CHIEF_SUB_ROLE_TRADING_SET";     //交易設定、賠率設定
    public final static String CHIEF_SUB_ROLE_MESSAGE = "CHIEF_SUB_ROLE_MESSAGE";     //站內消息管理
    public final static String CHIEF_SUB_ROLE_SEARCH_BILL = "CHIEF_SUB_ROLE_SEARCH_BILL";     //注單搜索
    public final static String CHIEF_SUB_ROLE_BACKSYS_ROLE = "CHIEF_SUB_ROLE_BACKSYS_ROLE";     //系統后臺維護權限
    public final static String CHIEF_SUB_ROLE_CANCEL_BILL = "CHIEF_SUB_ROLE_CANCEL_BILL";     //注單取消權限
    public final static String CHIEF_SUB_ROLE_JSZD = "CHIEF_SUB_ROLE_JSZD";     //即時注單
    
    public final static String BRANCH_SUB_ROLE_REPLENISH = "BRANCH_SUB_ROLE_REPLENISH";     //手工补货、自动补货设定（及变更记录）
    
    public final static String BRANCH_SUB_ROLE_OFFLINE = "BRANCH_SUB_ROLE_OFFLINE";     // 下线账号管理
    
    public final static String BRANCH_SUB_ROLE_SUB  = "BRANCH_SUB_ROLE_SUB";     //子账号管理
    
    public final static String BRANCH_SUB_ROLE_DELIVERY = "BRANCH_SUB_ROLE_DELIVERY";     //分公司交收报表
    
    public final static String BRANCH_SUB_ROLE_CLASSIFY = "BRANCH_SUB_ROLE_CLASSIFY";     //分公司分类报表
    public final static String BRANCH_SUB_ROLE_JSZD = "BRANCH_SUB_ROLE_JSZD";     //即時注單
    
    public final static String STOCKHOLDER_SUB_ROLE_REPLENISH = "STOCKHOLDER_SUB_ROLE_REPLENISH";     //手工补货、自动补货设定（及变更记录）
    
    public final static String STOCKHOLDER_SUB_ROLE_OFFLINE = "STOCKHOLDER_SUB_ROLE_OFFLINE";     // 下线账号管理
    
    public final static String STOCKHOLDER_SUB_ROLE_SUB  = "STOCKHOLDER_SUB_ROLE_SUB";     //子账号管理
    
    public final static String STOCKHOLDER_SUB_ROLE_DELIVERY = "STOCKHOLDER_SUB_ROLE_DELIVERY";     //股东交收报表
    
    public final static String STOCKHOLDER_SUB_ROLE_CLASSIFY = "STOCKHOLDER_SUB_ROLE_CLASSIFY";     //股东分类报表
    public final static String STOCKHOLDER_SUB_ROLE_JSZD = "STOCKHOLDER_SUB_ROLE_JSZD";     //即時注單
    
    public final static String GEN_AGENT_SUB_ROLE_REPLENISH = "GEN_AGENT_SUB_ROLE_REPLENISH";     //手工补货、自动补货设定（及变更记录）
    
    public final static String GEN_AGENT_SUB_ROLE_OFFLINE = "GEN_AGENT_SUB_ROLE_OFFLINE";     // 下线账号管理
    
    public final static String GEN_AGENT_SUB_ROLE_SUB  = "GEN_AGENT_SUB_ROLE_SUB";     //子账号管理
    
    public final static String GEN_AGENT_SUB_ROLE_DELIVERY = "GEN_AGENT_SUB_ROLE_DELIVERY";     //总代理交收报表
    
    public final static String GEN_AGENT_SUB_ROLE_CLASSIFY = "GEN_AGENT_SUB_ROLE_CLASSIFY";     //总代理分类报表
    public final static String GEN_AGENT_SUB_ROLE_JSZD = "GEN_AGENT_SUB_ROLE_JSZD";     //即時注單
    
    public final static String AGENT_SUB_ROLE_REPLENISH = "AGENT_SUB_ROLE_REPLENISH";     //手工补货、自动补货设定（及变更记录）
    
    public final static String AGENT_SUB_ROLE_OFFLINE = "AGENT_SUB_ROLE_OFFLINE";     // 下线账号管理
    
    public final static String AGENT_SUB_ROLE_SUB  = "AGENT_SUB_ROLE_SUB";     //子账号管理
    
    public final static String AGENT_SUB_ROLE_DELIVERY = "AGENT_SUB_ROLE_DELIVERY";     //代理交收报表
    
    public final static String AGENT_SUB_ROLE_CLASSIFY = "AGENT_SUB_ROLE_CLASSIFY";     //代理分类报表
    public final static String AGENT_SUB_ROLE_JSZD = "AGENT_SUB_ROLE_JSZD";     //即時注單
    
    //************子帐号的权限关键字START******************
    public final static String SUB_ROLE_REPLENISH = "SUB_ROLE_REPLENISH";     //手工补货、自动补货设定（及变更记录）
    public final static String SUB_ROLE_OFFLINE = "SUB_ROLE_OFFLINE";     // 下线账号管理
    public final static String SUB_ROLE_SUB = "SUB_ROLE_SUB";     //子账号管理
    public final static String SUB_ROLE_DELIVERY = "SUB_ROLE_DELIVERY";     //总监交收报表
    public final static String SUB_ROLE_CLASSIFY = "SUB_ROLE_CLASSIFY";     //总监分类报表
    public final static String SUB_ROLE_ODD = "SUB_ROLE_ODD";     //操盤權限、輸贏分析
    public final static String SUB_ROLE_OUT_USER_MANAGER = "SUB_ROLE_OUT_USER_MANAGER";     //出貨會員管理
    public final static String SUB_ROLE_ODD_LOG = "SUB_ROLE_ODD_LOG";     //每期彩票管理、操盤記錄查詢
    public final static String SUB_ROLE_SYS_INIT = "SUB_ROLE_SYS_INIT";     //系統初始設定
    public final static String SUB_ROLE_TRADING_SET = "SUB_ROLE_TRADING_SET";     //交易設定、賠率設定
    public final static String SUB_ROLE_MESSAGE = "SUB_ROLE_MESSAGE";     //站內消息管理
    public final static String SUB_ROLE_SEARCH_BILL = "SUB_ROLE_SEARCH_BILL";     //注單搜索
    public final static String SUB_ROLE_BACKSYS_ROLE = "SUB_ROLE_BACKSYS_ROLE";     //系統后臺維護權限
    public final static String SUB_ROLE_CANCEL_BILL = "SUB_ROLE_CANCEL_BILL";     //注單取消權限
    public final static String SUB_ROLE_JSZD = "SUB_ROLE_JSZD";     //即時注單
    
    //************子帐号的权限关键字END******************

    private Long ID;//ID

    private String account;//登录账号

    private String flag;//状态
    
    private String userType;//用户类型

    private Long userExtInfoId;//用户扩展信息ID

    private String userPwd;//用户密码

    private String chsName;//中文名字

    private String engName;//英文名字

    private String emailAddr;//eMail地址

    private String officePhone;//办公室电话

    private String mobilePhone;//移动电话

    private String fax;//传真

    private Date createDate;//创建时间

    private Date updateDate;//更新时间

    private Date loginDate;//最后登录时间

    private String loginIp;//最后登录IP

    private String comments;//备注

    private ChiefStaffExt chiefStaffExt;
    private Long parentStaffQry;
    private String parentStaffTypeQry;
 
    //add by peter
    private Date passwordUpdateDate;
    
    private String passwordResetFlag;//密码是否需要重新设置

    public String getParentStaffTypeQry() {
		return parentStaffTypeQry;
	}

	public void setParentStaffTypeQry(String parentStaffTypeQry) {
		this.parentStaffTypeQry = parentStaffTypeQry;
	}

	public Long getParentStaffQry() {
		return parentStaffQry;
	}

	public void setParentStaffQry(Long parentStaffQry) {
		this.parentStaffQry = parentStaffQry;
	}

	public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
        //根据目前的系统实际情况，各用户扩展信息表中采用基础表的主键作主键，故用户扩展信息ID与主键相同
        userExtInfoId = iD;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getFlag() {
        return flag;
    }

	/**
     * 根据 flag 的取值，返回所对应的中文名称
     * 
     * @return
     */
    public String getFlagName() {
        String result = "<font color='FF0000'>\u6570\u636e\u9519\u8bef</font>";//数据错误
        try {
            if (ManagerStaff.FLAG_USE.equalsIgnoreCase(flag.trim())) {
                result = "<font color='" + Constant.COLOR_GREEN
                        + "'>\u6709\u6548</font>";//有效
            } else if (ManagerStaff.FLAG_FORBID.equalsIgnoreCase(flag.trim())) {
                result = "<font color='" + Constant.COLOR_RED
                        + "'>\u7981\u7528</font>";//禁用
            } else if (ManagerStaff.FLAG_FREEZE.equalsIgnoreCase(flag.trim())) {
                result = "<font color='" + Constant.COLOR_RED
                        + "'>\u51bb\u7ed3</font>";//冻结
            } else if (ManagerStaff.FLAG_DELETE.equalsIgnoreCase(flag.trim())) {
                result = "<font color='" + Constant.COLOR_GREY
                        + "'>\u5df2\u5220\u9664</font>";//已删除
            }
        } catch (Exception ex) {

        }

        return result;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUserType() {
        return userType;
    }

    public String getUserTypeName() {

        String result = "<font color='FF0000'>\u6570\u636e\u9519\u8bef</font>";//数据错误

        try {
            if (ManagerStaff.USER_TYPE_SYS.equalsIgnoreCase(userType)) {
                result = "系统管理员";
            } else if (ManagerStaff.USER_TYPE_MANAGER
                    .equalsIgnoreCase(userType)) {
                result = "总管";
            } else if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(userType)) {
                result = "总监";
            } else if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(userType)) {
                result = "分公司";
            } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                    .equalsIgnoreCase(userType)) {
                result = "股东";
            } else if (ManagerStaff.USER_TYPE_GEN_AGENT
                    .equalsIgnoreCase(userType)) {
                result = "总代理";
            } else if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
                result = "代理";
            } else if (ManagerStaff.USER_TYPE_SUB.equalsIgnoreCase(userType)) {
                result = "子账号";
            }
        } catch (Exception ex) {

        }

        return result;

    }

    /**
     * 获取 userType 字段所对应的值 和 中文名称 Map 信息
     * 
     * key = userType 的值
     * value = 对应的中文名称
     * 
     * @param canBlank 是否添加空值
     * 
     * @return
     */
    public static Hashtable getUserTypeMap(boolean canBlank) {

        Hashtable result = new Hashtable();

        if (canBlank) {
            result.put("", "");
        }
        result.put(ManagerStaff.USER_TYPE_SUB, "子账号");
        result.put(ManagerStaff.USER_TYPE_AGENT, "代理");
        result.put(ManagerStaff.USER_TYPE_GEN_AGENT, "总代理");
        result.put(ManagerStaff.USER_TYPE_STOCKHOLDER, "股东");
        result.put(ManagerStaff.USER_TYPE_BRANCH, "分公司");
        result.put(ManagerStaff.USER_TYPE_CHIEF, "总监");
        result.put(ManagerStaff.USER_TYPE_MANAGER, "总管");
        result.put(ManagerStaff.USER_TYPE_SYS, "系统管理员");

        return result;
    }

    /**
     * 获取 userType 字段所对应的值 和 中文名称List信息
     * 
     * key = userType 的值
     * name = 对应的中文名称
     * 
     * @param canBlank 是否添加空值
     * 
     * @return
     */
    public static ArrayList<ListEntity> getUserTypeList(boolean canBlank) {

        ArrayList<ListEntity> resultList = new ArrayList<ListEntity>();
        ListEntity entity;

        if (canBlank) {
            entity = new ListEntity();
            entity.setKey("");
            entity.setName("");
            resultList.add(entity);
        }

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_SYS);
        entity.setName("系统管理员");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_MANAGER);
        entity.setName("总管");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_CHIEF);
        entity.setName("总监");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_BRANCH);
        entity.setName("分公司");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_STOCKHOLDER);
        entity.setName("股东");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_GEN_AGENT);
        entity.setName("总代理");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_AGENT);
        entity.setName("代理");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_SUB);
        entity.setName("子账号");
        resultList.add(entity);

        return resultList;
    }
    
    /**
     * 获取 userType 字段所对应的值 和 中文名称List信息
     * 
     * key = userType 的值
     * name = 对应的中文名称
     * 
     * @param canBlank 是否添加空值
     * 
     * @return
     */
    public static ArrayList<ListEntity> getUserTypeList2(boolean canBlank) {

        ArrayList<ListEntity> resultList = new ArrayList<ListEntity>();
        ListEntity entity;

        if (canBlank) {
            entity = new ListEntity();
            entity.setKey("");
            entity.setName("");
            resultList.add(entity);
        }

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_SYS);
        entity.setName("系统管理员");
        resultList.add(entity);

        entity = new ListEntity();
        entity.setKey(ManagerStaff.USER_TYPE_MANAGER);
        entity.setName("总管");
        resultList.add(entity);

        return resultList;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getUserExtInfoId() {
        return userExtInfoId;
    }

    public void setUserExtInfoId(Long userExtInfoId) {
        this.userExtInfoId = userExtInfoId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getChsName() {
        return chsName;
    }

    public void setChsName(String chsName) {
        this.chsName = chsName;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ChiefStaffExt getChiefStaffExt() {
        return chiefStaffExt;
    }

    public void setChiefStaffExt(ChiefStaffExt chiefStaffExt) {
        this.chiefStaffExt = chiefStaffExt;
    }

    /**
     * 判断用户是否在线
     * 
     * @return
     */
    public boolean isInLine(){
        return SessionStatInfo.isInLine(this.account, this.userType);
    }

	public String getEncodeAccount() {
		if(account!=null)
			return Tools.encodeWithKey(account);
			else 
				return null;
	}

	public String getEncodeId() {
		if(account!=null)
			return Tools.encodeWithKey(account);
			else 
				return null;
	}

	public Date getPasswordUpdateDate() {
		return passwordUpdateDate;
	}

	/**
	 * 默认是设置当天的日期
	 * @param passwordUpdateDate
	 */
	public void setPasswordUpdateDate(Date passwordUpdateDate) {
		if (null == passwordUpdateDate) {
			this.passwordUpdateDate = new Date();
		} else {
			this.passwordUpdateDate = passwordUpdateDate;
		}
	}

	public String getPasswordResetFlag() {
		return passwordResetFlag;
	}

	/**
	 * 默认为Y
	 * @param passwordResetFlag
	 */
	public void setPasswordResetFlag(String passwordResetFlag) {
		if (StringUtils.isEmpty(passwordResetFlag)) {
			this.passwordResetFlag = "Y";
		} else {
			this.passwordResetFlag = passwordResetFlag;
		}
	}

}
