package com.npc.lottery.sysmge.logic.spring;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.npc.lottery.common.ConditionData;
import com.npc.lottery.sysmge.dao.interf.IRolesDao;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.sysmge.entity.MemberStaff;
import com.npc.lottery.sysmge.entity.MemberUser;
import com.npc.lottery.sysmge.entity.Roles;
import com.npc.lottery.sysmge.logic.interf.IManagerUserLogic;
import com.npc.lottery.sysmge.logic.interf.IMemberUserLogic;
import com.npc.lottery.sysmge.logic.interf.IRolesLogic;
import com.npc.lottery.util.Tool;

/**
 * 角色逻辑处理类
 *
 *
 */
public class RolesLogic implements IRolesLogic {

    private static Logger log = Logger.getLogger(RolesLogic.class);

    private IRolesDao rolesDao = null;

    private IManagerUserLogic managerUserLogic = null;

    private IMemberUserLogic memberUserLogic = null;

    public void setRolesDao(IRolesDao rolesDao) {
        this.rolesDao = rolesDao;
    }

    public IRolesDao getRolesDao() {
        return rolesDao;
    }

    public void setManagerUserLogic(IManagerUserLogic managerUserLogic) {
        this.managerUserLogic = managerUserLogic;
    }

    public void setMemberUserLogic(IMemberUserLogic memberUserLogic) {
        this.memberUserLogic = memberUserLogic;
    }

    /**
     * 查询满足指定查询条件的角色信息数据记录
     * 
     * @param conditionData
     *            查询条件信息
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return  Roles 类型的 List
     */
    public List findRolesList(ConditionData conditionData, int pageCurrentNo,
            int pageSize) {
        List resultList = null;

        // 根据页码和页面大小，获得需要查询的开始数据和数量
        int firstResult = (pageCurrentNo - 1) * pageSize;
        int maxResults = pageSize;

        // 查询数据
        try {
            resultList = rolesDao.findRolesList(conditionData, firstResult,
                    maxResults);
        } catch (Exception ex) {
            Tool.printExceptionStack(ex);
        }

        return resultList;
    }

    /**
     * 统计满足指定查询条件的记录数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountRecord(ConditionData conditionData) {
        long amount = 0;

        try {
            amount = rolesDao.findAmountRecord(conditionData);
        } catch (Exception ex) {
            Tool.printExceptionStack(ex);
        }

        return amount;
    }

    /**
     * 查询所有的功能权限类型的角色
     * 
     * @param conditionData
     *            查询条件信息
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return
     */
    public ArrayList<Roles> findAllRolesRoleTypeFunc(
            ConditionData conditionData, int pageCurrentNo, int pageSize) {

        //设置查询条件：角色类型 = 资源角色
        if (null == conditionData) {
            conditionData = new ConditionData();
        }
        conditionData.addEqual("roleType", Roles.ROLE_TYPE_RES);

        ArrayList<Roles> resultList = (ArrayList<Roles>) this.findRolesList(
                conditionData, pageCurrentNo, pageSize);

        return resultList;
    }

    /**
     * 统计满足指定查询条件的功能权限类型的角色数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountAllRolesRoleTypeFunc(ConditionData conditionData) {
        long amount = 0;

        //设置查询条件：角色类型 = 资源角色
        if (null == conditionData) {
            conditionData = new ConditionData();
        }
        conditionData.addEqual("roleType", Roles.ROLE_TYPE_RES);

        amount = this.findAmountRecord(conditionData);

        return amount;
    }

    /**
     * 查询所有的数据权限类型的角色
     * 
     * @param conditionData
     *            查询条件信息
     * @param pageCurrentNo 第一页为 1
     *            需要查询的页码
     * @param pageSize
     *            页面大小
     * @return
     */
    public ArrayList<Roles> findAllRolesRoleTypeData(
            ConditionData conditionData, int pageCurrentNo, int pageSize) {

        //设置查询条件：角色类型 = 资源角色
        if (null == conditionData) {
            conditionData = new ConditionData();
        }
        conditionData.addEqual("roleType", Roles.ROLE_TYPE_RES);

        ArrayList<Roles> resultList = (ArrayList<Roles>) this.findRolesList(
                conditionData, pageCurrentNo, pageSize);

        return resultList;
    }

    /**
     * 统计满足指定查询条件的数据权限类型的角色数目
     * 
     * @param conditionData
     *            查询条件信息     
     * @return
     */
    public long findAmountAllRolesRoleTypeData(ConditionData conditionData) {
        long amount = 0;

        //设置查询条件：角色类型 = 资源角色
        if (null == conditionData) {
            conditionData = new ConditionData();
        }
        conditionData.addEqual("roleType", Roles.ROLE_TYPE_RES);

        amount = this.findAmountRecord(conditionData);

        return amount;
    }

    /**
     * 根据ID查询角色数据
     * 
     * @param ID
     * @return
     */
    public Roles findByID(Long ID) {

        Roles entity = null;

        ConditionData conditionData = new ConditionData();

        conditionData.addEqual("ID", new Long(ID));//增加ID的查询条件

        List resultList = this.findRolesList(conditionData, 1, 9999);

        if (null != resultList && 0 < resultList.size()) {
            entity = (Roles) resultList.get(0);
        }

        return entity;
    }

    /**
     * 查找指定用户的私有功能角色
     * 
     * @param userID        用户ID
     * @param userType      用户类型
     * @param createNew     此用户所对应的私有权限不存在时是否创建，true创建，false不创建
     * @return  未查询到数据或者失败则返回null
     */
    public Roles findPrivateFuncRole(Long userID, String userType,
            boolean createNew) {

        if (null == userID) {
            return null;
        }

        Roles entity = null;

        ConditionData conditionData = new ConditionData();

        conditionData.addEqual("roleCode", userID.toString());//增加ID的查询条件
        conditionData.addEqual("roleType", Roles.ROLE_TYPE_PRIVATE);//角色类型
        conditionData.addEqual("roleLevel", Roles.getLevelByUserType(userType));//角色等级

        List resultList = this.findRolesList(conditionData, 1, 9999);

        if (null != resultList && 0 < resultList.size()) {
            entity = (Roles) resultList.get(0);
        }

        if (null == entity && createNew) {
            //用户私有权限不存在，则重新创建
            String userName = "";

            //查询用户是否存在（根据userType判断是管理用户还是会员用户）
            if (MemberStaff.USER_TYPE_MEMBER.equalsIgnoreCase(userType)) {
                //会员用户
                MemberUser userEntity = memberUserLogic
                        .findMemberUserByID(userID);
                if (null == userEntity) {
                    log.error("用户ID为【" + userID + "】的用户不存在，无法为其创建私有功能角色！");
                    return null;
                }
                userName = userEntity.getChsName();
            } else {
                //管理用户
                ManagerUser userEntity = managerUserLogic
                        .findManagerUserByID(userID);
                if (null == userEntity) {
                    log.error("用户ID为【" + userID + "】的用户不存在，无法为其创建私有功能角色！");
                    return null;
                }
                userName = userEntity.getChsName();
            }

            //创建用户私有角色
            Roles roles = new Roles();
            roles.setRoleCode(userID.toString());
            roles.setRoleName("私有角色");
            roles.setRoleLevel(Roles.getLevelByUserType(userType));
            roles.setRoleType(Roles.ROLE_TYPE_PRIVATE);
            roles.setRemark("用户【" + userName + "（" + userID + "）】的私有功能角色");
            Long rolesID = this.saveRoles(roles);

            if (null == rolesID) {
                log.error("创建用户私有功能角色失败！");
            } else {
                log.info("成功创建用户【" + userName + "（" + userID + "）】的私有功能角色");
            }
            roles.setID(rolesID);//设置主键

            //设置返回值
            entity = roles;
        }

        return entity;
    }
    
    /**
     * 根据角色编码查询对应的角色
     * 
     * @param roleCode
     * @return
     */
    public Roles findRolesByCode(String roleCode){
        
        Roles entity = null;

        ConditionData conditionData = new ConditionData();

        conditionData.addEqual("roleCode", roleCode);//增加roleCode的查询条件

        List resultList = this.findRolesList(conditionData, 1, 9999);

        if (null != resultList && 0 < resultList.size()) {
            entity = (Roles) resultList.get(0);
        }

        return entity;
    }

    /**
     * 查询所有的角色信息
     * 
     * @return Function 类型的 List
     */
    public List findAll() {
        return this.findRolesList(null, 1, 9999);
    }

    /**
     * 保存或更新对象
     * @param entity
     */
    public void saveOrUpdate(Roles entity) {
        rolesDao.saveOrUpdate(entity);
    }

    /**
     * 保存信息
     * 
     * @param entity    待保存的信息
     * @return  此信息记录所对应的ID，Long类型
     */
    public Long saveRoles(Roles entity) {
        return rolesDao.saveRoles(entity);
    }

    /**
     * 删除对象
     * @param entity
     */
    public void delete(Roles entity) {
        rolesDao.delete(entity);
    }

}
