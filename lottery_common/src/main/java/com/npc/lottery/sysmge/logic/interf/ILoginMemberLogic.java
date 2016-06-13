package com.npc.lottery.sysmge.logic.interf;

import com.npc.lottery.sysmge.entity.MemberUser;

/**
 * 会员用户登陆管理类接口
 * 
 * @author none
 *
 */
public interface ILoginMemberLogic {

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
            String userPwd);

    /**
     * 登陆成功后初始化用户相关信息
     * 
     * @param user 登陆的用户对象
     * 
     * @return 0 成功；1 没有授权信息
     */
    public int initResource(MemberUser user);

    /**
     * 判断是否指定的商铺编号是否存在
     * 
     * @param shopsCode
     * @return true：存在；false：不存在
     */
    public boolean isExistShopsCode(String shopsCode);
}
