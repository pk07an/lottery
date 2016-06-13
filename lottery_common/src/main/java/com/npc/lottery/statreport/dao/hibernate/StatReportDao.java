package com.npc.lottery.statreport.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.npc.lottery.replenish.vo.ReplenishVO;
import com.npc.lottery.statreport.dao.interf.IStatReportDao;
import com.npc.lottery.statreport.entity.DeliveryReport;
import com.npc.lottery.sysmge.entity.ManagerStaff;

/**
 * 报表统计相关的数据库处理类
 * 
 */
public class StatReportDao extends HibernateDaoSupport implements
        IStatReportDao {

    private static Logger log = Logger.getLogger(StatReportDao.class);
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询满足指定查询条件的数据记录
     * 
     * @param playType
     *            玩法类型
     *            
     * @return  PlayType 类型的 List
     */
    public List findCommissionTypeList(String playType) {

        StringBuffer sqlStr = new StringBuffer();

        sqlStr.append("FROM PlayType p WHERE p.id = (SELECT p2.id FROM PlayType p2 WHERE p2.commissionType");
        sqlStr.append(" = p.commissionType AND rownum < 2) AND p.playType = '"
                + playType + "'");

        List result = getHibernateTemplate().find(sqlStr.toString());

        return result;
    }

    /**
     * 根据佣金类型查询对应的投注类型数据
     * 
     * @param commissionType 佣金类型
     * @return
     */
    public List findPlayTypeByCommission(String commissionType) {

        StringBuffer sqlStr = new StringBuffer();

        sqlStr.append("FROM PlayType t WHERE t.commissionType LIKE '"
                + commissionType + "'");

        List result = getHibernateTemplate().find(sqlStr.toString());

        return result;
    }

    /**
     * 查询指定用户的交收报表数据
     * 
     * @param userID        ID
     * @param lotteryType   彩票种类
     * @param playType      下注类型
     * @param periodsNum    期数
     * @param startDate     开始时间
     * @param endDate       结束时间
     * @param userType      用户类型
     * @return
     */
    public ArrayList<DeliveryReport> findDeliveryReportList(Long userID,
            String lotteryType, String playType, String periodsNum,
            Date startDate, Date endDate, String userType) {

        ArrayList<DeliveryReport> resultList = new ArrayList<DeliveryReport>();

        StringBuffer storedProcName = new StringBuffer("call ");

        if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
            //代理
            storedProcName.append("Delivery_Report_Agent" + "(");
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT.equalsIgnoreCase(userType)) {
            //总代理
            storedProcName.append("Delivery_Report_Gen_Agent" + "(");
        } else if (ManagerStaff.USER_TYPE_STOCKHOLDER
                .equalsIgnoreCase(userType)) {
            //股东
            storedProcName.append("Delivery_Report_Stockholder" + "(");
        } else if (ManagerStaff.USER_TYPE_BRANCH.equalsIgnoreCase(userType)) {
            //分公司
            storedProcName.append("Delivery_Report_Branch" + "(");
        } else if (ManagerStaff.USER_TYPE_CHIEF.equalsIgnoreCase(userType)) {
            //总监
            storedProcName.append("Delivery_Report_Chief" + "(");
        } else {
            log.error("用户类型及用户数据异常！");
            return null;
        }

        //格式化需要的时间
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
                DateFormat.MEDIUM, Locale.CHINA);
        String startDateStr = df.format(startDate);
        String endDateStr = df.format(endDate);

        //会员ID（总监、总代理、代理等）
        storedProcName.append("?");
        storedProcName.append(", ");
        //彩票种类
        storedProcName.append("?");
        storedProcName.append(", ");
        //下注类型
        storedProcName.append("?");
        storedProcName.append(", ");
        //期数
        storedProcName.append("?");
        storedProcName.append(", ");
        //开始时间
        storedProcName.append("?");
        storedProcName.append(", ");
        //结束时间
        storedProcName.append("?");
        storedProcName.append(", ");
        //存储返回结果
        storedProcName.append("?");
        storedProcName.append(", ");
        //存储返回结果集
        storedProcName.append("?");
        storedProcName.append(")");

        CallableStatement cs = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = SessionFactoryUtils.getDataSource(getSessionFactory())
                    .getConnection();

            conn.commit();
            conn.setAutoCommit(false);

            cs = conn.prepareCall(storedProcName.toString());

            //设置参数
            cs.setLong(1, userID);//会员ID（总监、总代理、代理等）
            cs.setString(2, lotteryType);//彩票种类
            cs.setString(3, playType);//下注类型
            cs.setString(4, periodsNum);//期数
            //cs.setDate(5, startDate);//开始时间
            //cs.setDate(6, endDate);//结束时间
            cs.setString(5, startDateStr);
            cs.setString(6, endDateStr);

            //返回参数类型
            cs.registerOutParameter(7, OracleTypes.VARCHAR);//resultFlag
            cs.registerOutParameter(8, OracleTypes.CURSOR);//statReportAgent

            cs.execute();

            String resultFlag = cs.getString(7);
            //判断结果值
            if ("0".equalsIgnoreCase(resultFlag)) {
                log.info("报表统计成功！");
            } else if ("1".equalsIgnoreCase(resultFlag)) {
                log.info("用户ID为空！");
                return null;
            } else if ("2".equalsIgnoreCase(resultFlag)) {
                log.info("报表数据为空！");
                return null;
            } else {
                log.info("执行存储过程中出现未知错误！");
                return null;
            }

            rs = (ResultSet) cs.getObject(8);//获取返回值

            DeliveryReport entity;
            while (rs.next()) {
                //存储数据
                entity = new DeliveryReport();
                entity.setUserID(rs.getLong("USER_ID"));//用户ID
                entity.setUserType(rs.getString("USER_TYPE"));//用户类型
                entity.setRecordType(rs.getString("RECORD_TYPE"));//记录类型
                entity.setSubordinate(rs.getString("SUBORDINATE"));//账号
                entity.setUserName(rs.getString("USER_NAME"));//用户名
                entity.setMemberBackWater(rs.getDouble("MEMBER_BACK_WATER"));//会员退水
                entity.setTurnover(rs.getLong("TURNOVER"));//成交笔数
                entity.setAmount(rs.getDouble("AMOUNT"));//投注总额
                entity.setValidAmount(rs.getDouble("VALID_AMOUNT"));//有效金额
                entity.setMemberAmount(rs.getDouble("MEMBER_AMOUNT"));//会员输赢
                entity.setSubordinateAmount(rs.getDouble("SUBORDINATE_AMOUNT"));//应收下线
                entity.setWinBackWater(rs.getDouble("WIN_BACK_WATER"));//赚取退水
                entity.setRealResult(rs.getDouble("REAL_RESULT"));//实占结果
                entity.setWinBackWaterResult(rs
                        .getDouble("WIN_BACK_WATER_RESULT"));//赚水后结果
                entity.setPaySuperior(rs.getDouble("PAY_SUPERIOR"));//应付上级
                entity.setRate(rs.getDouble("RATE"));//占成
                entity.setRateChiefSet(rs.getDouble("RATE_CHIEF_SET"));//总监占成设置值
                entity.setRateBranchSet(rs.getDouble("RATE_BRANCH_SET"));//分公司占成设置值
                entity.setRateStockholderSet(rs
                        .getDouble("RATE_STOCKHOLDER_SET"));//股东占成设置值
                entity.setRateGenAgentSet(rs.getDouble("RATE_GEN_AGENT_SET"));//总代理占成设置值
                entity.setRateAgentSet(rs.getDouble("RATE_AGENT_SET"));//代理占成设置值
                entity.setCommissionBranch(rs.getDouble("COMMISSION_BRANCH"));//分公司佣金
                entity.setCommissionStockholder(rs
                        .getDouble("COMMISSION_STOCKHOLDER"));//股东佣金
                entity.setCommissionGenAgent(rs
                        .getDouble("COMMISSION_GEN_AGENT"));//总代理佣金
                entity.setCommissionAgent(rs.getDouble("COMMISSION_AGENT"));//代理佣金
                entity.setCommissionMember(rs.getDouble("COMMISSION_MEMBER"));//会员佣金
                entity.setCommissionBranchSet(rs
                        .getDouble("COMMISSION_BRANCH_SET"));//分公司退水设置值
                entity.setCommissionStockholderSet(rs
                        .getDouble("COMMISSION_STOCKHOLDER_SET"));//股东退水设置值
                entity.setCommissionGenAgentSet(rs
                        .getDouble("COMMISSION_GEN_AGENT_SET"));//总代理退水设置值
                entity.setCommissionAgentSet(rs
                        .getDouble("COMMISSION_AGENT_SET"));//代理退水设置值

                //实占注额
                entity.setMoneyRateChief(rs.getDouble("MONEY_RATE_CHIEF"));
                entity.setMoneyRateBranch(rs.getDouble("MONEY_RATE_BRANCH"));
                entity.setMoneyRateStockholder(rs
                        .getDouble("MONEY_RATE_STOCKHOLDER"));
                entity.setMoneyRateGenAgent(rs
                        .getDouble("MONEY_RATE_GEN_AGENT"));
                entity.setMoneyRateAgent(rs.getDouble("MONEY_RATE_AGENT"));

                //赚取退水
                entity.setWinCommissionBranch(rs
                        .getDouble("WIN_COMMISSION_BRANCH"));
                entity.setWinCommissionGenAgent(rs
                        .getDouble("WIN_COMMISSION_GEN_AGENT"));
                entity.setWinCommissionStockholder(rs
                        .getDouble("WIN_COMMISSION_STOCKHOLDER"));
                entity.setWinCommissionAgent(rs
                        .getDouble("WIN_COMMISSION_AGENT"));
                entity.setWinCommissionMember(rs
                        .getDouble("WIN_COMMISSION_MEMBER"));

                resultList.add(entity);
            }

            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != cs) {
                    cs.close();
                }
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultList;
    }

    /**
     * 查询指定用户的交收报表数据
     * 
     * @param userID
     * @param userType
     * 
     * @deprecated 测试方法，后续不使用
     * @return
     */
    public ArrayList<DeliveryReport> findDeliveryReportList(Long userID,
            String userType) {

        ArrayList<DeliveryReport> resultList = new ArrayList<DeliveryReport>();

        StringBuffer storedProcName = new StringBuffer("call ");

        if (ManagerStaff.USER_TYPE_AGENT.equalsIgnoreCase(userType)) {
            //代理
            storedProcName.append("Delivery_Report_Agent" + "(");
        } else if (ManagerStaff.USER_TYPE_GEN_AGENT.equalsIgnoreCase(userType)) {
            //总代理
            storedProcName.append("Delivery_Report_Gen_Agent" + "(");
        } else {
            log.error("用户类型及用户数据异常！");
            return null;
        }

        //当前会员ID（总监、总代理、代理等）
        storedProcName.append("?");
        storedProcName.append(", ");
        //彩票种类
        storedProcName.append("?");
        storedProcName.append(", ");
        //下注类型
        storedProcName.append("?");
        storedProcName.append(", ");
        //期数
        storedProcName.append("?");
        storedProcName.append(", ");
        //存储返回结果
        storedProcName.append("?");
        storedProcName.append(", ");
        //存储返回结果集
        storedProcName.append("?");
        storedProcName.append(")");

        CallableStatement cs = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = SessionFactoryUtils.getDataSource(getSessionFactory())
                    .getConnection();

            conn.commit();
            conn.setAutoCommit(false);

            cs = conn.prepareCall(storedProcName.toString());

            cs.setLong(1, userID);//userID
            cs.setObject(2, null);//playType
            cs.setObject(3, null);//playFinalType
            cs.setObject(4, null);//periodsNum
            //返回参数类型
            cs.registerOutParameter(5, OracleTypes.VARCHAR);//resultFlag
            cs.registerOutParameter(6, OracleTypes.CURSOR);//statReportAgent

            cs.execute();

            String resultFlag = cs.getString(5);
            //判断结果值
            if ("0".equalsIgnoreCase(resultFlag)) {
                log.info("报表统计成功！");
            } else if ("1".equalsIgnoreCase(resultFlag)) {
                log.info("用户ID为空！");
                return null;
            } else if ("2".equalsIgnoreCase(resultFlag)) {
                log.info("报表数据为空！");
                return null;
            } else {
                log.info("执行存储过程中出现未知错误！");
                return null;
            }

            rs = (ResultSet) cs.getObject(6);//获取返回值

            DeliveryReport entity;
            while (rs.next()) {
                //存储数据
                entity = new DeliveryReport();
                entity.setUserID(rs.getLong("USER_ID"));//用户ID
                entity.setUserType(rs.getString("USER_TYPE"));//用户类型
                entity.setSubordinate(rs.getString("SUBORDINATE"));//账号
                entity.setUserName(rs.getString("USER_NAME"));//用户名
                entity.setMemberBackWater(rs.getDouble("MEMBER_BACK_WATER"));//会员退水
                entity.setTurnover(rs.getLong("TURNOVER"));//成交笔数
                entity.setAmount(rs.getDouble("AMOUNT"));//投注总额
                entity.setValidAmount(rs.getDouble("VALID_AMOUNT"));//有效金额
                entity.setMemberAmount(rs.getDouble("MEMBER_AMOUNT"));//会员输赢
                entity.setSubordinateAmount(rs.getDouble("SUBORDINATE_AMOUNT"));//应收下线
                entity.setWinBackWater(rs.getDouble("WIN_BACK_WATER"));//赚取退水
                entity.setRealResult(rs.getDouble("REAL_RESULT"));//实占结果
                entity.setWinBackWaterResult(rs
                        .getDouble("WIN_BACK_WATER_RESULT"));//赚水后结果
                entity.setPaySuperior(rs.getDouble("PAY_SUPERIOR"));//应付上级

                resultList.add(entity);
            }

            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != cs) {
                    cs.close();
                }
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultList;
    }

}
