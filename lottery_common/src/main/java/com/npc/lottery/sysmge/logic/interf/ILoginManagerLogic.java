package com.npc.lottery.sysmge.logic.interf;

import com.npc.lottery.boss.entity.ShopsInfo;
import com.npc.lottery.sysmge.entity.ManagerUser;

/**
 * 管理人员登录的逻辑处理类
 * 
 * @author none
 *
 */
public interface ILoginManagerLogic {

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
            String userPwd);

    /**
     * 登陆成功后初始化用户相关信息
     * 
     * @param user 登陆的用户对象
     * 
     * @return 0 成功；1 没有授权信息
     */
    public int initResource(ManagerUser user);

    /**
     * 判断是否指定的商铺编号是否存在
     * 
     * @param shopsCode
     * @return true：存在；false：不存在
     */
    public boolean isExistShopsCode(String shopsCode);

    /**
     * 根据商铺编码查询商铺信息
     * 
     * @param shopsCode
     * @return
     */
    public ShopsInfo findShopsCode(String shopsCode);

    /**
     * 判断商铺是否可用
     * 
     * @param shopsCode
     * @return
     *      Constant.SHOP_OPEN      商铺正常可用
     *      Constant.SHOP_FREEZE    商铺冻结
     *      Constant.SHOP_CLOSE     商铺关闭
     */
    public String verifyShop(String shopsCode);

}
