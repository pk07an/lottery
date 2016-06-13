package com.npc.lottery.sysmge.logic.spring;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.boss.logic.interf.IShopsLogic;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.ConstantBusiness;
import com.npc.lottery.sysmge.entity.Function;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.Resource;
import com.npc.lottery.sysmge.entity.Roles;
import com.npc.lottery.sysmge.logic.interf.IAuthorizLogic;
import com.npc.lottery.sysmge.logic.interf.ILoginManagerLogic;
import com.npc.lottery.sysmge.logic.interf.IManagerUserLogic;
import com.npc.lottery.user.entity.AgentStaffExt;
import com.npc.lottery.user.entity.BranchStaffExt;
import com.npc.lottery.user.entity.ChiefStaffExt;
import com.npc.lottery.user.entity.GenAgentStaffExt;
import com.npc.lottery.user.entity.StockholderStaffExt;
import com.npc.lottery.user.entity.SubAccountInfo;
import com.npc.lottery.user.logic.interf.IAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IBranchStaffExtLogic;
import com.npc.lottery.user.logic.interf.IChiefStaffExtLogic;
import com.npc.lottery.user.logic.interf.IGenAgentStaffExtLogic;
import com.npc.lottery.user.logic.interf.IStockholderStaffExtLogic;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;
import com.npc.lottery.util.MenuModel;

/**
 * 人员登录的逻辑处理类
 * 
 * @author none
 *
 */
public class LoginManagerLogic implements ILoginManagerLogic {

    private static Logger log = Logger.getLogger(LoginManagerLogic.class);

    private IManagerUserLogic managerUserLogic = null;

    private IAuthorizLogic authorizLogic;

    private IShopsLogic shopsLogic;//商铺管理类

    private IChiefStaffExtLogic chiefStaffExtLogic;//总监

    private IBranchStaffExtLogic branchStaffExtLogic;//公公司

    private IStockholderStaffExtLogic stockholderStaffExtLogic;//股东

    private IGenAgentStaffExtLogic genAgentStaffExtLogic; //总代理

    private IAgentStaffExtLogic agentStaffExtLogic; //代理

    private ISubAccountInfoLogic subAccountInfoLogic;//子账号

    public void setManagerUserLogic(IManagerUserLogic managerUserLogic) {
        this.managerUserLogic = managerUserLogic;
    }

    public void setAuthorizLogic(IAuthorizLogic authorizLogic) {
        this.authorizLogic = authorizLogic;
    }

    public void setShopsLogic(IShopsLogic shopsLogic) {
        this.shopsLogic = shopsLogic;
    }

    public void setChiefStaffExtLogic(IChiefStaffExtLogic chiefStaffExtLogic) {
        this.chiefStaffExtLogic = chiefStaffExtLogic;
    }

    public void setBranchStaffExtLogic(IBranchStaffExtLogic branchStaffExtLogic) {
        this.branchStaffExtLogic = branchStaffExtLogic;
    }

    public void setStockholderStaffExtLogic(
            IStockholderStaffExtLogic stockholderStaffExtLogic) {
        this.stockholderStaffExtLogic = stockholderStaffExtLogic;
    }

    public void setGenAgentStaffExtLogic(
            IGenAgentStaffExtLogic genAgentStaffExtLogic) {
        this.genAgentStaffExtLogic = genAgentStaffExtLogic;
    }

    public void setAgentStaffExtLogic(IAgentStaffExtLogic agentStaffExtLogic) {
        this.agentStaffExtLogic = agentStaffExtLogic;
    }

    public void setSubAccountInfoLogic(ISubAccountInfoLogic subAccountInfoLogic) {
        this.subAccountInfoLogic = subAccountInfoLogic;
    }

    /**
     * 校验用户登录结果
     * 
     * @param safetyCode  安全码 
     * @param userName       登录用户名
     * @param userPwd        登录密码
     * @return  返回 MemberUser 对象，根据 User.loginState 值判断登陆结果
     *          MemberUser.LOGIN_STATE_SUCCESS_NORMAL             正常登录成功
     *          MemberUser.LOGIN_STATE_FAILURE_INEXIST            用户信息不存在
     *          MemberUser.LOGIN_STATE_FAILURE_PWD_INCORRECT      用户密码不正确
     *          MemberUser.LOGIN_STATE_FAILURE_SAFETYCODE_ERROR   安全码错误
     */
    public ManagerUser verifyLogin(String safetyCode, String userName,
            String userPwd) {

        ManagerUser managerUser = null;
        //1、校验用户是否存在

        //读取用户信息
        managerUser = managerUserLogic.findManagerUserByName(userName);
        if (null == managerUser) {
            managerUser = new ManagerUser();
            //设置登陆状态并返回
            managerUser
                    .setLoginState(ManagerUser.LOGIN_STATE_FAILURE_USER_INEXIST);
            return managerUser;
        }

        //判断用户的状态是否为禁用
        if (ManagerStaff.FLAG_FORBID.equalsIgnoreCase(managerUser.getFlag())) {
            //用户禁用
            managerUser
                    .setLoginState(ManagerUser.LOGIN_STATE_FAILURE_USER_FORBID);
            return managerUser;
        }
        
        //2、校验安全码
        boolean safetyCodeResult = this.safetyCodeTest(safetyCode, managerUser);
        if (!safetyCodeResult) {
            managerUser
                    .setLoginState(ManagerUser.LOGIN_STATE_FAILURE_SAFETYCODE_ERROR);
            return managerUser;
        }

        //3、校验用户密码
        String userPwdDb = managerUser.getUserPwd();
        if (null != userPwdDb) {
            userPwdDb = userPwdDb.trim();
        } else {
            managerUser
                    .setLoginState(ManagerUser.LOGIN_STATE_FAILURE_PWD_INCORRECT);
            return managerUser;
        }
//        if (null != userPwd) {
//            userPwd = new MD5().getMD5ofStr(userPwd).trim();
//        }

        if (!(userPwdDb.equalsIgnoreCase(userPwd))) {
            managerUser
                    .setLoginState(ManagerUser.LOGIN_STATE_FAILURE_PWD_INCORRECT);
            return managerUser;
        }
        
      //判断用户的状态是否为冻结
        if (ManagerStaff.FLAG_FREEZE.equalsIgnoreCase(managerUser.getFlag())) {
        	//用户冻结
        	managerUser
        	.setLoginState(ManagerUser.LOGIN_STATE_FAILURE_USER_FREEZE);
        	return managerUser;
        }

        //设置登陆状态为正常登陆
        managerUser.setLoginState(ManagerUser.LOGIN_STATE_SUCCESS_NORMAL);

        return managerUser;
    }

    /**
     * 登陆成功后初始化用户相关信息
     * 
     * @param user 登陆的用户对象
     * 
     * @return 0 成功；1 没有授权信息
     */
    public int initResource(ManagerUser user) {

        //1、解析用户对应的菜单数据
    	//目前是对TB_FRAME_FUNCTION该表不进行操作，根据需求再做更改
//        int result = this.parseUserMenu(user);

        //2、读取用户的角色信息列表
        this.parseUserRoles(user);

        return 0;
    }

    /**
     * 解析用户的角色信息
     * 
     * @param user
     * 
     * @return 0 成功；其他取值失败
     */
    private int parseUserRoles(ManagerUser user) {

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
    private int parseUserMenu(ManagerUser user) {

        long userID = user.getID();

        //查询用户对应的授权功能信息（包括默认角色信息等）
        ArrayList<Function> resultList = authorizLogic.findUserAuthorizFunc(
                userID, user.getUserType());

        //解析资源信息（解析用户授予的URL信息）
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
    private int parseResource(ManagerUser user, ArrayList<Function> functionList) {

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
     * @param originList	原始菜单列表
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
     * 根据商铺编码查询商铺信息
     * 
     * @param shopsCode
     * @return
     */
    public ShopsInfo findShopsCode(String shopsCode) {
        ShopsInfo shopsInfoEntity = shopsLogic.findShopsCode(shopsCode);

        return shopsInfoEntity;
    }

    /**
     * 判断商铺是否可用
     * 
     * @param shopsCode
     * @return
     *      Constant.SHOP_OPEN      商铺正常可用
     *      Constant.SHOP_FREEZE    商铺冻结
     *      Constant.SHOP_CLOSE     商铺关闭
     */
    public String verifyShop(String shopsCode) {
        return shopsLogic.verifyShopState(shopsCode);
    }

    /**
     * 校验安全码是否与用户匹配
     * 
     * @param safetyCode    安全码
     * @param user          用信息
     * @return
     */
    public boolean safetyCodeTest(String safetyCode, ManagerUser user) {

        //如果是内置安全码，则校验通过
        //        if(Constant.SAFETYCODE_INTERNAL.equalsIgnoreCase(safetyCode)){
        //            
        //            return true;
        //        }

        //判断用户类型
        if (ManagerStaff.USER_TYPE_SYS.equalsIgnoreCase(user.getUserType())) {
            //系统管理员
            if (Constant.SAFETYCODE_INTERNAL.equalsIgnoreCase(safetyCode)) {
                return true;
            }
        } else if (ManagerStaff.USER_TYPE_MANAGER.equalsIgnoreCase(user
                .getUserType())) {
            //总管
            if (Constant.SAFETYCODE_INTERNAL.equalsIgnoreCase(safetyCode)) {
                return true;
            }
        } else if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(user
                .getUserType())) {
            //总监
            ChiefStaffExt entity = chiefStaffExtLogic
                    .findChiefStaffExtByID(user.getID());
            if (safetyCode.equalsIgnoreCase(entity.getShopsCode())) {
                return true;
            }
        } else if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(user
                .getUserType())) {
            //分公司
            BranchStaffExt entity = branchStaffExtLogic
                    .findBranchStaffExtByID(user.getID());
            if (null == entity) {
                return false;
            }
            if (safetyCode.equalsIgnoreCase(entity.getChiefStaffExt()
                    .getShopsCode())) {
                return true;
            }
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER.equalsIgnoreCase(user
                .getUserType())) {
            //股东
            StockholderStaffExt entity = stockholderStaffExtLogic
                    .findStockholderStaffExtByID(user.getID());
            if (null == entity) {
                return false;
            }

            ChiefStaffExt entityChief = chiefStaffExtLogic
                    .findChiefStaffExtByID(entity.getChiefStaff());
            if (null == entityChief) {
                log.error("对应的总监用户不存在！");
                return false;
            }

            if (safetyCode.equalsIgnoreCase(entityChief.getShopsCode())) {
                return true;
            }
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT.equalsIgnoreCase(user
                .getUserType())) {
            //总代理
            GenAgentStaffExt entity = genAgentStaffExtLogic
                    .findGenAgentStaffExtByID(user.getID());
            if (null == entity) {
                return false;
            }
            ChiefStaffExt entityChief = chiefStaffExtLogic
                    .findChiefStaffExtByID(entity.getChiefStaff());
            if (null == entityChief) {
                log.error("对应的总监用户不存在！");
                return false;
            }
            if (safetyCode.equalsIgnoreCase(entityChief.getShopsCode())) {
                return true;
            }
        } else if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(user
                .getUserType())) {
            //代理
            AgentStaffExt entity = agentStaffExtLogic.findManagerStaffByID(user
                    .getID());
            if (null == entity) {
                return false;
            }
            ChiefStaffExt entityChief = chiefStaffExtLogic
                    .findChiefStaffExtByID(entity.getChiefStaff());
            if (null == entityChief) {
                log.error("对应的总监用户不存在！");
                return false;
            }
            if (safetyCode.equalsIgnoreCase(entityChief.getShopsCode())) {
                return true;
            }
        } else if (ManagerStaff.USER_TYPE_SUB.equalsIgnoreCase(user
                .getUserType())) {
            //子账号
            SubAccountInfo entity = subAccountInfoLogic.querySubAccountInfo(
                    "managerStaffID", user.getID());
            if (null == entity) {
                return false;
            }
            ChiefStaffExt entityChief = chiefStaffExtLogic
                    .findChiefStaffExtByID(entity.getChiefStaff());
            if (null == entityChief) {
                log.error("对应的总监用户不存在！");
                return false;
            }
            if (safetyCode.equalsIgnoreCase(entityChief.getShopsCode())) {
                return true;
            }
        }

        return false;
    }
}
