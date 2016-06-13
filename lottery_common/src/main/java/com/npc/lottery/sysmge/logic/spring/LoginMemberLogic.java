package com.npc.lottery.sysmge.logic.spring;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.boss.logic.interf.IShopsLogic;
import com.npc.lottery.common.ConstantBusiness;
import com.npc.lottery.sysmge.entity.Function;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.sysmge.entity.Resource;
import com.npc.lottery.sysmge.entity.Roles;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.sysmge.logic.interf.ILoginMemberLogic;
import com.npc.lottery.sysmge.logic.interf.IMemberUserLogic;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.MemberStaffExt;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;
import com.npc.lottery.user.logic.interf.IMemberStaffExtLogic;
import com.npc.lottery.util.MenuModel;

/**
 * 会员用户登陆
 * 
 * @author none
 *
 */
public class LoginMemberLogic implements ILoginMemberLogic {

    private static Logger log = Logger.getLogger(LoginMemberLogic.class);

    private IMemberUserLogic memberUserLogic = null;

    private IAuthorizLogic authorizLogic;

    private IShopsLogic shopsLogic;//商铺管理类

    private IMemberStaffExtLogic memberStaffExtLogic;//会员扩展信息

    private IChiefStaffExtLogic chiefStaffExtLogic;//总监

    public void setMemberUserLogic(IMemberUserLogic memberUserLogic) {
        this.memberUserLogic = memberUserLogic;
    }

    public void setAuthorizLogic(IAuthorizLogic authorizLogic) {
        this.authorizLogic = authorizLogic;
    }

    public void setShopsLogic(IShopsLogic shopsLogic) {
        this.shopsLogic = shopsLogic;
    }

    public void setMemberStaffExtLogic(IMemberStaffExtLogic memberStaffExtLogic) {
        this.memberStaffExtLogic = memberStaffExtLogic;
    }

    public void setChiefStaffExtLogic(IChiefStaffExtLogic chiefStaffExtLogic) {
        this.chiefStaffExtLogic = chiefStaffExtLogic;
    }

    /**
     * 校验用户登录结果
     * 
     * @param safetyCode  安全码 
     * @param userName  登录用户名
     * @param userPwd   登录密码
     * @return  返回 MemberUser 对象，根据 MemberUser.loginState   值判断登陆结果
     *          MemberUser.LOGIN_STATE_SUCCESS_NORMAL            正常登录成功
     *          MemberUser.LOGIN_STATE_FAILURE_INEXIST           用户信息不存在
     *          MemberUser.LOGIN_STATE_FAILURE_PWD_INCORRECT     用户密码不正确
     */
    public MemberUser verifyLogin(String safetyCode, String userName,
            String userPwd) {

        MemberUser memberUser = null;
        //1、校验用户是否存在

        //读取用户信息
        memberUser = memberUserLogic.findMemberUserByName(userName);
        if (null == memberUser) {
            memberUser = new MemberUser();
            //设置登陆状态并返回
            memberUser
                    .setLoginState(MemberUser.LOGIN_STATE_FAILURE_USER_INEXIST);
            return memberUser;
        }
        
        //判断用户的状态是否为禁用
        if (MemberStaff.FLAG_FORBID.equalsIgnoreCase(memberUser.getFlag())) {
            //用户禁用
            memberUser
                    .setLoginState(memberUser.LOGIN_STATE_FAILURE_USER_FORBID);
            return memberUser;
        }
        
        //2、校验用户密码
        String userPwdDb = memberUser.getUserPwd();
        if (null != userPwdDb) {
            userPwdDb = userPwdDb.trim();
        } else {
            memberUser
                    .setLoginState(MemberUser.LOGIN_STATE_FAILURE_PWD_INCORRECT);
            return memberUser;
        }
//        if (null != userPwd) {
//            userPwd = new MD5().getMD5ofStr(userPwd).trim();
//        }

        if (!(userPwdDb.equalsIgnoreCase(userPwd))) {
            memberUser
                    .setLoginState(MemberUser.LOGIN_STATE_FAILURE_PWD_INCORRECT);
            return memberUser;
        }

        //3、读取会员用户扩展信息
        MemberStaffExt memberStaffExt = memberStaffExtLogic
                .findMemberStaffExtByID(memberUser.getID());
        memberUser.setMemberStaffExt(memberStaffExt);

        //4、校验安全码
        boolean safetyCodeResult = safetyCodeTest(safetyCode, memberStaffExt);
        if (!safetyCodeResult) {
            memberUser
                    .setLoginState(MemberUser.LOGIN_STATE_FAILURE_SAFETYCODE_ERROR);
            return memberUser;
        }
        
      //判断用户的状态是否为冻结
        if (ManagerStaff.FLAG_FREEZE.equalsIgnoreCase(memberUser.getFlag())) {
        	//用户冻结
        	memberUser
        	.setLoginState(ManagerUser.LOGIN_STATE_FAILURE_USER_FREEZE);
        	return memberUser;
        }

        //设置登陆状态为正常登陆
        memberUser.setLoginState(MemberUser.LOGIN_STATE_SUCCESS_NORMAL);

        return memberUser;
    }

    /**
     * 登陆成功后初始化用户相关信息
     * 
     * @param user 登陆的用户对象
     * 
     * @return 0 成功；1 没有授权信息
     */
    public int initResource(MemberUser user) {

        //1、解析用户对应的菜单数据
        int result = this.parseUserMenu(user);

        //2、读取用户的角色信息列表
        this.parseUserRoles(user);

        return result;
    }

    /**
     * 解析用户的角色信息
     * 
     * @param user
     * 
     * @return 0 成功；其他取值失败
     */
    private int parseUserRoles(MemberUser user) {

        ArrayList<Roles> rolesList = authorizLogic.findAuthorizRolesByUserID(
                user.getID(), user.getUserType());

        user.setRoleList(rolesList);

        return 0;
    }

    /**
     * 解析用户对应的菜单数据
     * 
     * @param user 登陆的用户对象
     * @return 0 成功；1 没有授权信息
     */
    private int parseUserMenu(MemberUser user) {

        long userID = user.getID();

        //查询用户对应的授权功能信息

        ArrayList<Function> resultList = authorizLogic.findUserAuthorizFunc(
                userID, user.getUserType());

        //解析资源信息
        parseResource(user, resultList);

        //TODO 解析菜单的代码考虑优化
        ArrayList<MenuModel> firstMenuList = new ArrayList<MenuModel>();//所有的一级菜单列表

        ArrayList<MenuModel> secondMenuList = new ArrayList<MenuModel>();//所有的二级菜单列表

        ArrayList<MenuModel> thirdMenuList = new ArrayList<MenuModel>();//所有的三级菜单列表

        long parentFuncID = ConstantBusiness.FUNCTION_ROOT_ID;
        MenuModel menuTemp = null;
        ArrayList<MenuModel> menuList = null;
        //解析一级菜单

        //一级菜单的父ID
        for (int i = 0; i < resultList.size(); i++) {
            //过滤不是一级菜单的数据
            if (parentFuncID != resultList.get(i).getParentFunc().getID()
                    .longValue()) {
                continue;
            }

            //过滤掉根节点
            if (parentFuncID == resultList.get(i).getID().longValue()) {
                resultList.remove(i);
                i = i - 1;
                continue;
            }

            //构建菜单对象
            menuTemp = new MenuModel(resultList.get(i));
            firstMenuList.add(menuTemp);
            menuTemp = null;

            //移除已经使用的数据

            resultList.remove(i);
            i = i - 1;
        }

        //如果没有任何授权数据信息，则返回
        if (firstMenuList.size() < 1) {
            return 1;
        }

        //解析二级菜单
        for (int first = 0; first < firstMenuList.size(); first++) {
            parentFuncID = Long.parseLong(firstMenuList.get(first).getId());
            menuList = new ArrayList();

            for (int i = 0; i < resultList.size(); i++) {
                //过滤不是对应一级菜单的子菜单数据

                if (parentFuncID != resultList.get(i).getParentFunc().getID()
                        .longValue()) {
                    continue;
                }

                //构建菜单对象
                menuTemp = new MenuModel(resultList.get(i));
                menuList.add(menuTemp);
                secondMenuList.add(menuTemp);
                menuTemp = null;

                //移除已经使用的数据

                resultList.remove(i);
                i = i - 1;
            }

            //菜单排序
            this.sortMenuList(menuList);
            //记录一级菜单所对应的子菜单（二级菜单）
            firstMenuList.get(first).setSubMenuList(menuList);
            menuList = null;
        }

        //解析三级菜单
        for (int second = 0; second < secondMenuList.size(); second++) {
            parentFuncID = Long.parseLong(secondMenuList.get(second).getId());
            menuList = new ArrayList();

            for (int i = 0; i < resultList.size(); i++) {
                //过滤不是对应二级菜单的子菜单数据
                if (parentFuncID != resultList.get(i).getParentFunc().getID()
                        .longValue()) {
                    continue;
                }

                //构建菜单对象
                menuTemp = new MenuModel(resultList.get(i));
                menuList.add(menuTemp);
                thirdMenuList.add(menuTemp);
                menuTemp = null;

                //移除已经使用的数据

                resultList.remove(i);
                i = i - 1;
            }

            //菜单排序
            this.sortMenuList(menuList);
            //记录二级菜单所对应的子菜单（三级菜单）
            secondMenuList.get(second).setSubMenuList(menuList);
            menuList = null;
        }

        //菜单排序
        this.sortMenuList(firstMenuList);
        this.sortMenuList(secondMenuList);
        this.sortMenuList(thirdMenuList);

        //保存菜单数据到用户登陆信息对象中
        user.setFirstMenuList(firstMenuList);
        user.setSecondMenuList(secondMenuList);
        user.setThirdMenuList(thirdMenuList);

        log.error("成功解析用户所对应的菜单信息：一级菜单【" + firstMenuList.size() + "】个，二级菜单【"
                + secondMenuList.size() + "】个，三级菜单【" + thirdMenuList.size()
                + "】");

        return 0;
    }

    /**
     * 解析用户所对应的资源信息
     * 
     * @param user
     * @param functionList
     * @return
     */
    private int parseResource(MemberUser user, ArrayList<Function> functionList) {

        ArrayList<Resource> resourceList = new ArrayList<Resource>();

        //解析出Resource信息
        Function funEntity;
        Iterator resIte;
        for (int i = 0; i < functionList.size(); i++) {
            funEntity = functionList.get(i);
            resIte = funEntity.getResources().iterator();
            while (resIte.hasNext()) {
                resourceList.add((Resource) resIte.next());
            }
        }

        //解析出URL信息
        ArrayList<String> urlList = new ArrayList<String>();
        Resource resEntity;
        String[] urlStr;
        for (int i = 0; i < resourceList.size(); i++) {
            resEntity = resourceList.get(i);
            //解析多个URL字符串
            urlStr = resEntity.getUrl().split(Resource.URL_SPLIT);
            if (null != urlStr && urlStr.length > 0) {
                for (int m = 0; m < urlStr.length; m++) {
                    urlList.add(urlStr[m].trim());
                }
            }
        }

        //保存URL信息
        user.setUrlList(urlList);

        return 0;
    }

    /**
     * 菜单排序，使用选择排序法

     * 
     * @param originList    原始菜单列表
     * @return
     */
    private int sortMenuList(ArrayList<MenuModel> originList) {

        if (null == originList) {
            return -1;
        }

        if (originList.size() < 2) {
            return 1;
        }

        MenuModel temp = null;
        for (int one = 0; one < originList.size(); one++) {
            for (int two = one; two < originList.size(); two++) {
                if (originList.get(two).getSortNum() < originList.get(one)
                        .getSortNum()) {
                    temp = originList.get(one);
                    originList.set(one, originList.get(two));
                    originList.set(two, temp);
                }
            }
        }

        return 0;
    }

    /**
     * 判断是否指定的商铺编号是否存在
     * 
     * @param shopsCode
     * @return true：存在；false：不存在
     */
    public boolean isExistShopsCode(String shopsCode) {

        boolean result = false;

        ShopsInfo shopsInfoEntity = shopsLogic.findShopsCode(shopsCode);
        if (null == shopsInfoEntity || shopsInfoEntity.getID() < 0) {
            result = false;
        } else {
            result = true;
        }

        return result;
    }

    /**
     * 校验安全码是否与用户匹配
     * 
     * @param safetyCode            安全码
     * @param memberStaffExt        用户信息
     * @return
     */
    public boolean safetyCodeTest(String safetyCode,
            MemberStaffExt memberStaffExt) {

        Long userID = memberStaffExt.getChiefStaff();
        if (null == userID) {
            log.error("对应的总监用户不存在！");
            return false;
        }

        ChiefStaffExt entityChief = chiefStaffExtLogic
                .findChiefStaffExtByID(userID);
        if (null == entityChief) {
            return false;
        }
        if (safetyCode.equalsIgnoreCase(entityChief.getShopsCode())) {
            return true;
        }

        return false;
    }

}
