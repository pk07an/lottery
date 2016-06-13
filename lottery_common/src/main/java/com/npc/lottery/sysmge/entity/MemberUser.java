package com.npc.lottery.sysmge.entity;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.util.MenuModel;

/**
 * 会员用户基础信息，继承自 MemberStaff
 * 此类中部分属性只有登录时才会填充，故此实体类只用来记录登录人员信息，不建议当做普通的实体类对象使用
 * 
 * 平常使用时，请使用 MemberStaff，如某信息表中的创建人员对象，如需要关联，建议关联 MemberStaff
 * 
 * @author none
 *
 */
public class MemberUser extends MemberStaff {

    private static Logger log = Logger.getLogger(MemberUser.class);//日志

    public static final int LOGIN_STATE_SUCCESS_NORMAL = 0;//正常登录成功

    public static final int LOGIN_STATE_FAILURE_USER_INEXIST = 1;//登录失败，用户不存在

    public static final int LOGIN_STATE_FAILURE_PWD_INCORRECT = 2;//登录失败，用户密码不正确
    
    public static final int LOGIN_STATE_FAILURE_SAFETYCODE_ERROR = 3;//登录失败，安全码错误
    
    public static final int LOGIN_STATE_FAILURE_USER_FORBID = 4;//登录失败，用户已被禁用
    
    public static final int LOGIN_STATE_FAILURE_USER_FREEZE = 5;//登录失败，用户已被冻结，可以登录

    //角色列表
    private ArrayList<Roles> roleList;

    //菜单，TODO 增加List内容类型的描述
    private ArrayList menuList;

    //菜单为三级（不计根菜单）
    //一级菜单，存放的对象是 com.npc.lottery.util.MenuModel
    private ArrayList<MenuModel> firstMenuList = new ArrayList<MenuModel>();

    //二级菜单，存放的对象是 com.npc.lottery.util.MenuModel
    private ArrayList<MenuModel> secondMenuList = new ArrayList<MenuModel>();

    //三级菜单，存放的对象是 com.npc.lottery.util.MenuModel
    private ArrayList<MenuModel> thirdMenuList = new ArrayList<MenuModel>();

    //资源对象
    //private ArrayList<Resource> resourceList = new ArrayList<Resource>();

    //URL信息
    private ArrayList<String> urlList = new ArrayList<String>();

    //登录状态，取值参考常量定义

    private int loginState = -1;

    private String userOrgName;//所属机构

    //private String safetyCode;//安全码（商铺编号）
    
    private ShopsInfo shopsInfo;//商铺信息对象
    
    private MemberStaffExt memberStaffExt;//扩展用户信息表

    public MemberUser() {

    }

    /**
     * 获取安全码
     * 
     * @deprecated 此方法尽量不要使用，采用 getShopsInfo().getShopsCode()方法获取数据
     * 
     * @return
     */
    public String getSafetyCode() {
        return shopsInfo.getShopsCode();
    }

    /**
     * 根据 staff 初始化用户信息
     * 
     * @param staff
     */
    public MemberUser(MemberStaff MemberStaff, String userOrgName) {
        this.fillData(MemberStaff);
        this.userOrgName = userOrgName;
    }

    public MemberUser(MemberStaff MemberStaff) {
        this.fillData(MemberStaff);
    }

    /**
     * 根据 MemberStaff 填充用户信息
     * 
     * @param MemberStaff
     */
    public void fillData(MemberStaff MemberStaff) {

        if (null == MemberStaff) {
            log.error("用来填充的数据为空！");
            return;
        }
        this.setID(MemberStaff.getID());//用户ID
        this.setAccount(MemberStaff.getAccount());//登录账号
        this.setFlag(MemberStaff.getFlag());//状态
        this.setUserType(MemberStaff.getUserType());//用户类型
        this.setUserExtInfoId(MemberStaff.getUserExtInfoId());//用户扩展信息ID，根据目前的逻辑，此字段的取值与ID的取值相同
        this.setUserPwd(MemberStaff.getUserPwd());//用户密码
        this.setChsName(MemberStaff.getChsName());//中文名字
        this.setEngName(MemberStaff.getEngName());//英文名字
        this.setEmailAddr(MemberStaff.getEmailAddr());//eMail地址
        this.setOfficePhone(MemberStaff.getOfficePhone());//办公室电话
        this.setMobilePhone(MemberStaff.getMobilePhone());//移动电话
        this.setFax(MemberStaff.getFax());//传真
        this.setCreateDate(MemberStaff.getCreateDate());//创建时间
        this.setUpdateDate(MemberStaff.getUpdateDate());//更新时间
        this.setLoginDate(MemberStaff.getLoginDate());//最后登录时间
        this.setLoginIp(MemberStaff.getLoginIp());//最后登录IP
        this.setComments(MemberStaff.getComments());//备注
        this.setPasswordUpdateDate(MemberStaff.getPasswordUpdateDate());//密码更新时间
        this.setPasswordResetFlag(MemberStaff.getPasswordResetFlag());//密码是否重新设置
    }

    /**
     * 获取登录状态
     * 
     * 0-正常登录成功；1 -用户信息不存在；2-用户密码不正确
     * 
     * @return
     */
    public int getLoginState() {
        return loginState;
    }

    /**
     * 判断用户是否（成功）登录
     * 
     * @return
     */
    public boolean isLogin() {

        boolean result = false;

        if (LOGIN_STATE_SUCCESS_NORMAL == loginState || LOGIN_STATE_FAILURE_USER_FREEZE == loginState) {
            result = true;
        }

        return result;
    }

    public void setLoginState(int loginState) {
        this.loginState = loginState;
    }

    public String getUserOrgName() {
        return userOrgName;
    }

    public void setUserOrgName(String userOrgName) {
        this.userOrgName = userOrgName;
    }

    public ArrayList<MenuModel> getFirstMenuList() {
        return firstMenuList;
    }

    public void setFirstMenuList(ArrayList<MenuModel> firstMenuList) {
        this.firstMenuList = firstMenuList;
    }

    public ArrayList<MenuModel> getSecondMenuList() {
        return secondMenuList;
    }

    /**
     * 查询指定的一级菜单所对应的二级菜单
     * 
     * @param firstMenuID
     * @return
     */
    public ArrayList<MenuModel> getSecondMenuList(String firstMenuID) {
        if (null == firstMenuID) {
            return null;
        }

        ArrayList<MenuModel> resultList = null;

        //查询对应的一级菜单

        for (int i = 0; i < firstMenuList.size(); i++) {
            if (firstMenuID.equalsIgnoreCase(firstMenuList.get(i).getId())) {
                resultList = firstMenuList.get(i).getSubMenuList();
                break;
            }
        }

        return resultList;
    }

    public void setSecondMenuList(ArrayList<MenuModel> secondMenuList) {
        this.secondMenuList = secondMenuList;
    }

    public ArrayList<MenuModel> getThirdMenuList() {
        return thirdMenuList;
    }

    /**
     * 查询指定的二级菜单所对应的三级菜单
     * 
     * @param secondMenuID
     * @return
     */
    public ArrayList<MenuModel> getThirdMenuList(String secondMenuID) {
        if (null == secondMenuID) {
            return null;
        }

        ArrayList<MenuModel> resultList = null;

        //查询对应的一级菜单

        for (int i = 0; i < secondMenuList.size(); i++) {
            if (secondMenuID.equalsIgnoreCase(secondMenuList.get(i).getId())) {
                resultList = secondMenuList.get(i).getSubMenuList();
                break;
            }
        }

        return resultList;
    }

    public void setThirdMenuList(ArrayList<MenuModel> thirdMenuList) {
        this.thirdMenuList = thirdMenuList;
    }

    public ArrayList<Roles> getRoleList() {
        return roleList;
    }

    public void setRoleList(ArrayList<Roles> roleList) {
        this.roleList = roleList;
    }

    /**
     * 判断是否存在指定的角色（根据角色编码判断）
     * 
     * @param roleCode
     * @return
     */
    public boolean existRoles(String roleCode) {

        boolean result = false;

        if (null == roleCode || roleCode.trim().length() < 1
                || null == roleList || roleList.size() < 1) {
            return false;
        }

        for (int i = 0; i < roleList.size(); i++) {
            if (roleCode.trim().equalsIgnoreCase(roleList.get(i).getRoleCode())) {
                result = true;
                break;
            }
        }

        return result;
    }

    public ArrayList<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(ArrayList<String> urlList) {
        this.urlList = urlList;
    }

    public MemberStaffExt getMemberStaffExt() {
        return memberStaffExt;
    }

    public void setMemberStaffExt(MemberStaffExt memberStaffExt) {
        this.memberStaffExt = memberStaffExt;
    }

    public ShopsInfo getShopsInfo() {
        return shopsInfo;
    }

    public void setShopsInfo(ShopsInfo shopsInfo) {
        this.shopsInfo = shopsInfo;
    }
}
