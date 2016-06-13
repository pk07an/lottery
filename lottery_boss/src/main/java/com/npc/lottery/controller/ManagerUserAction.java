package com.npc.lottery.controller;

import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.action.BaseAction;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.SessionStatInfo;
import com.npc.lottery.sysmge.logic.interf.IManagerStaffLogic;
import com.npc.lottery.sysmge.logic.interf.IManagerUserLogic;
import com.npc.lottery.util.MD5;
import com.npc.lottery.util.Tool;

/**
 * 基础管理用户 Action 类
 * 
 * @author none
 *
 */
public class ManagerUserAction extends BaseAction {

    private static Logger log = Logger.getLogger(ManagerUserAction.class);

    private IManagerStaffLogic managerStaffLogic;

    private IManagerUserLogic managerUserLogic;

    public void setManagerStaffLogic(IManagerStaffLogic managerStaffLogic) {
        this.managerStaffLogic = managerStaffLogic;
    }

    public void setManagerUserLogic(IManagerUserLogic managerUserLogic) {
        this.managerUserLogic = managerUserLogic;
    }

    /**
     * 用户信息列表
     * 
     * @return
     * @throws Exception
     */
    public String list() throws Exception {

        //调用search方法
        this.search();

        return "userList";
    }

    /**
     * 创建信息
     * 
     * @return
     * @throws Exception
     */
    public String create() throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();//获取request对象

        ManagerUser user = new ManagerUser();

        user.setCreateDate(new Date(System.currentTimeMillis()));

        request.setAttribute("entity", user);
        request.setAttribute("userTypeList", ManagerStaff.getUserTypeList2(false));//用户类型的页面下拉列表数据

        return "create";
    }

    /**
     * 编辑信息
     * 
     * @return
     * @throws Exception
     */
    public String modify() throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();//获取request对象

        ManagerUser user = managerUserLogic.findManagerUserByID(ID);

        request.setAttribute("user", user);

        return "modify";
    }

    /**
     * 提交编辑用户信息
     * 
     * @return
     * @throws Exception
     */
    public String modifySubmit() throws Exception {

        if (null == ID) {
            request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>用户不存在！</font>");

            return "failure";
        }

        //查询原始数据
        ManagerStaff staffEntity = managerStaffLogic.findManagerStaffByID(ID);
        //更新数据
        staffEntity.setChsName(chsName);//中文名字
        staffEntity.setEngName(engName);//英文名字
        staffEntity.setEmailAddr(emailAddr);//eMail地址
        staffEntity.setOfficePhone(officePhone);//办公室电话
        staffEntity.setMobilePhone(mobilePhone);//移动电话
        staffEntity.setFax(fax);//传真
        staffEntity.setUpdateDate(new Date(System.currentTimeMillis()));//更新时间
        staffEntity.setComments(comments);//备注

        HttpServletRequest request = ServletActionContext.getRequest();

        try {
            //保存数据
            managerUserLogic.update(staffEntity);

            //设置返回页面提示信息
            //request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_GREEN + "'>编辑用户成功！</font>");

            return "success";
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>编辑用户失败！</font>");

            return "failure";
        }
    }

    /**
     * 编辑密码
     * 
     * @return
     * @throws Exception
     */
    public String modifyPassword() throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();//获取request对象

        ManagerUser user = managerUserLogic.findManagerUserByID(ID);

        //获取登录信息
        HttpSession session = request.getSession();
        ManagerUser currentUser = (ManagerUser) session
                .getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);

        if (ID.longValue() == currentUser.getID().longValue()) {
            request.setAttribute("isSelf", "true");
        } else {
            request.setAttribute("isSelf", "false");
        }
        request.setAttribute("user", user);

        return "modifyPassword";
    }

    /**
     * 提交所编辑的密码
     * 
     * @return
     * @throws Exception
     */
    public String modifyPasswordSubmit() throws Exception {

        if (null == ID) {
            request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>用户不存在！</font>");

            return "failure";
        }

        //查询原始数据
        ManagerStaff staffEntity = managerStaffLogic.findManagerStaffByID(ID);

        HttpServletRequest request = ServletActionContext.getRequest();
        
        MD5 md5 = new MD5();
        
        //获取登录信息
        HttpSession session = request.getSession();
        ManagerUser currentUser = (ManagerUser) session
                .getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);

        if (ID.longValue() == currentUser.getID().longValue()) {
            //获取输入的密码信息
            String userPwdOrign = request.getParameter("userPwdOrign");

            //加密密码
            String userPwdOrignMd5 = md5.getMD5ofStr(userPwdOrign).trim();

            //判断旧密码是否正确
            //注意旧密码不适合使用ajax在界面上校验，必须放在后台校验
            if (!userPwdOrignMd5.equalsIgnoreCase(staffEntity.getUserPwd())) {
                request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
                request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                        + Constant.COLOR_RED + "'>旧密码错误！</font>");
                return "failure";
            }
        } 

        String userPwdMd5 = md5.getMD5ofStr(userPwd).trim();
        staffEntity.setUserPwd(userPwdMd5);//记录新密码
        staffEntity.setUpdateDate(new Date(System.currentTimeMillis()));

        try {
            //保存数据
            managerUserLogic.update(staffEntity);

            //设置返回页面提示信息
            //request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_GREEN + "'>修改密码成功！</font>");

            return "success";
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>修改密码失败！</font>");

            return "failure";
        }
    }

    /**
     * 删除用户信息
     * 
     * 只做标记删除
     * 
     * @return
     * @throws Exception
     */
    public String deleteInfo() throws Exception {

        if (null == ID) {
            request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>用户不存在！</font>");

            return "failure";
        }

        //查询原始数据
        ManagerStaff staffEntity = managerStaffLogic.findManagerStaffByID(ID);

        //设置删除标记
        staffEntity.setFlag(ManagerStaff.FLAG_DELETE);
        staffEntity.setUpdateDate(new Date(System.currentTimeMillis()));

        HttpServletRequest request = ServletActionContext.getRequest();

        try {
            //保存数据
            managerUserLogic.update(staffEntity);

            //设置返回页面提示信息
            //request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_GREEN + "'>删除用户成功！</font>");

            return "success";
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>删除用户失败！</font>");

            return "failure";
        }
    }

    /**
     * 提交新增用户
     * 
     * @return
     * @throws Exception
     */
    public String createSubmit() throws Exception {

        ManagerStaff userEntity = new ManagerStaff();//存储数据
        userEntity.setAccount(account);//登录账号
        userEntity.setUserType(userType);//用户类型
        userEntity.setFlag(ManagerStaff.FLAG_USE);//状态
        userEntity.setChsName(chsName);//中文名字
        userEntity.setEngName(engName);//英文名字
        userEntity.setEmailAddr(emailAddr);//eMail地址
        userEntity.setOfficePhone(officePhone);//办公室电话
        userEntity.setMobilePhone(mobilePhone);//移动电话
        userEntity.setFax(fax);//传真
        userEntity.setCreateDate(new Date(System.currentTimeMillis()));
        userEntity.setComments(comments);//备注

        //加密密码
        String userPasswordMD5 = new MD5().getMD5ofStr(this.userPwd);
        System.out.println("userPasswordMD5 = " + userPasswordMD5
                + "   length = " + userPasswordMD5.length());
        userEntity.setUserPwd(userPasswordMD5);

        HttpServletRequest request = ServletActionContext.getRequest();

        try {
            //保存数据
            managerUserLogic.saveManagerStaff(userEntity);

            //设置返回页面提示信息
            //request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_GREEN + "'>新增用户成功！</font>");

            return "success";
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>新增用户失败！</font>");

            return "failure";
        }
    }

    public String view() throws Exception {

        if (null == ID) {
            request.setAttribute(Constant.INFO_PAGE_TYPE_RETURN_SIMPLE, "true");
            request.setAttribute(Constant.INFO_PAGE_MESSAGE, "<font color='"
                    + Constant.COLOR_RED + "'>查询用户信息失败！</font>");

            return "failure";
        }

        ManagerUser user = managerUserLogic.findManagerUserByID(ID);
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("user", user);

        //判断查看的数据是否是当前登录者自身，如果是，则不允许删除
        boolean isSelf = false;
        ManagerUser userInfo = (ManagerUser) request.getSession().getAttribute(
                Constant.MANAGER_LOGIN_INFO_IN_SESSION);
        if (userInfo.getID().equals(ID)) {
            isSelf = true;
        }
        request.setAttribute("isSelf", isSelf);
        
        //判断用户是否在线
        System.out.println("****** + " + SessionStatInfo.inLineNum("9646"));

        return "view";
    }

    /**
     * 根据输入信息，做查询显示
     * @return
     * @throws Exception
     */
    public String search() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();

        //获取请求的页码
        String pageCurrentNoStr = Tool.getParameter(request, "_pagecount", "1");
        int pageCurrentNo = 1;
        try {
            pageCurrentNo = Integer.parseInt(pageCurrentNoStr);
        } catch (Exception ex) {

        }

        //构造查询条件
        ConditionData conditionData = new ConditionData();
        conditionData.addLike("account", account);
        conditionData.addLike("chsName", chsName);
        conditionData.addLike("userType", userType);
        //只查询有效和禁用的用户
        conditionData.addIn("flag", ManagerStaff.FLAG_USE);
        conditionData.addIn("flag", ManagerStaff.FLAG_FORBID);

        //获取记录总数
        long recordAmount = managerUserLogic
                .findAmountManagerUserList(conditionData);
        log.info("记录总数：" + recordAmount);
        long sumPages = (recordAmount - 1) / Constant.LIST_PAGE_SIZE + 1;

        //查询记录列表
        ArrayList<ManagerUser> userList = (ArrayList<ManagerUser>) managerUserLogic
                .findManagerUserList(conditionData, pageCurrentNo,
                        Constant.LIST_PAGE_SIZE);

        request.setAttribute("sumPages", sumPages + "");
        request.setAttribute("recordAmount", recordAmount + "");
        request.setAttribute("pageCurrentNo", pageCurrentNo + "");
        request.setAttribute("resultList", userList);
        request.setAttribute("userTypeList", ManagerStaff.getUserTypeList(true));//用户类型的页面下拉列表数据

        return "userList";
    }

    private Long ID;//ID

    private String account;//登录账号

    private String flag;//状态

    private String userType;//用户类型

    private Integer userExtInfoId;//用户扩展信息ID

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

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
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

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getUserExtInfoId() {
        return userExtInfoId;
    }

    public void setUserExtInfoId(Integer userExtInfoId) {
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
}
