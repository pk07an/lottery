package com.npc.lottery.statreport.dao.interf;

import java.util.ArrayList;
import java.util.List;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.user.entity.MemberStaffExt;

/**
 * 分类报表
 * 
 * @author User
 *
 */
public interface IClassReportDao {

    /**
     * 查询指定代理用户所对应的普通用户信息（包括直属会员）
     * 
     * @param agentUserID 代理用户ID
     * 
     * @return  MemberStaffExt 类型的 List
     */
    public ArrayList<MemberStaffExt> findAgentMemberList(Long agentUserID);

    /**
     * 查询指定总代理用户所对应的的普通用户信息（包括直属会员）
     * 
     * @param genAgentUserID 总代理用户ID
     * 
     * @return  MemberStaffExt 类型的 List
     */
    public ArrayList<MemberStaffExt> findGenAgentMemberList(Long genAgentUserID);

    /**
     * 查询指定股东用户所对应的的普通用户信息（包括直属会员）
     * 
     * @param stockholderUserID 股东用户ID
     * 
     * @return  MemberStaffExt 类型的 List
     */
    public ArrayList<MemberStaffExt> findStockholderMemberList(
            Long stockholderUserID);

    /**
     * 查询指定分公司用户所对应的的普通用户信息（包括直属会员）
     * 
     * @param branchUserID 分公司用户ID
     * 
     * @return  MemberStaffExt 类型的 List
     */
    public ArrayList<MemberStaffExt> findBranchMemberList(Long branchUserID);

    /**
     * 查询指定总监用户所对应的的普通用户信息（包括直属会员）
     * 
     * @param chiefUserID 总监用户ID
     * 
     * @return  MemberStaffExt 类型的 List
     */
    public ArrayList<MemberStaffExt> findChiefMemberList(Long chiefUserID);

    /**
     * 查询满足指定查询条件的补货数据记录
     * 
     * @param conditionData
     *            查询条件信息
     * @param firstResult
     *            需要查询的第一个记录数
     * @param maxResults
     *            需要查询的记录数目
     * @return  Replenish 类型的 List
     */
    public List findReplenishList(final ConditionData conditionData,
            final int firstResult, final int maxResults);
}
