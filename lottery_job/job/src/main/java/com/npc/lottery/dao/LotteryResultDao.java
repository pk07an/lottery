package com.npc.lottery.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.npc.lottery.common.Constant;

@Repository
public class LotteryResultDao {

	@Autowired
	private JdbcTemplate jdbcTemplateMysql;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger log = Logger.getLogger(LotteryResultDao.class);

	@SuppressWarnings("unchecked")
	public Map<String, List<Integer>> getResultByPeriodNumAndPlayType(String playType, final String periodNum) {
		String sql = "";
		Map<String, List<Integer>> resultMap = new HashMap<String, List<Integer>>();

		if (Constant.LOTTERY_TYPE_GDKLSF.equals(playType)) {
			sql = "select ball from tp_gdklsf_ball where qishu = ?";
		} else if (Constant.LOTTERY_TYPE_K3.equals(playType)) {
			sql = "select ball from tp_jsks_ball where qishu = ?";
		} else if (Constant.LOTTERY_TYPE_CQSSC.equals(playType)) {
			sql = "select ball from tp_cqssc_ball where qishu = ?";
		} else if (Constant.LOTTERY_TYPE_NC.equals(playType)) {
			sql = "select ball from tp_xync_ball where qishu = ?";
		} else if (Constant.LOTTERY_TYPE_BJ.equals(playType)) {
			sql = "select ball from tp_bjsc_ball where qishu = ?";
		}

		if (StringUtils.isNotEmpty(sql)) {

			List<String> result = jdbcTemplateMysql.query(sql, new Object[] { periodNum }, new RowMapper() {

				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					String result = rs.getString(1);
					if (StringUtils.isEmpty(result)) {
						result = "";
					}
					return result;
				}
			});
			if (null != result && !result.isEmpty()) {
				List<Integer> resultList = (List<Integer>) JSONArray.parse(result.get(0));
				resultMap.put(periodNum, resultList);
			}

		}

		return resultMap;
	}

	@SuppressWarnings("unchecked")
	public Map<String, List<Integer>> getResultListByPeriodNumAndPlayType(String playType, List<String> periodNumList) {
		if (CollectionUtils.isEmpty(periodNumList)) {
			return new HashMap<String, List<Integer>>();
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= periodNumList.size(); i++) {
			if (i != periodNumList.size()) {
				sb.append("?,");
			} else {
				sb.append("?");
			}

		}

		String sql = "";
		Map<String, List<Integer>> resultMap = new HashMap<String, List<Integer>>();

		if (Constant.LOTTERY_TYPE_GDKLSF.equals(playType)) {
			sql = "select ball,qishu from tp_gdklsf_ball where qishu in (" + sb.toString() + ") order by qishu desc";
		} else if (Constant.LOTTERY_TYPE_K3.equals(playType)) {
			sql = "select ball,qishu  from tp_jsks_ball where qishu in (" + sb.toString() + ") order by qishu desc";
		} else if (Constant.LOTTERY_TYPE_CQSSC.equals(playType)) {
			sql = "select ball,qishu  from tp_cqssc_ball where qishu in (" + sb.toString() + ") order by qishu desc";
		} else if (Constant.LOTTERY_TYPE_NC.equals(playType)) {
			sql = "select ball,qishu  from tp_xync_ball where qishu in (" + sb.toString() + ") order by qishu desc";
		} else if (Constant.LOTTERY_TYPE_BJSC.equals(playType)) {
			sql = "select ball,qishu  from tp_bjsc_ball where qishu in (" + sb.toString() + ") order by qishu desc";
		}

		if (StringUtils.isNotEmpty(sql)) {

			log.info("==========getResultListByPeriodNumAndPlayType执行sql:" + sql);

			List<Map<String, Object>> resultList = jdbcTemplateMysql.queryForList(sql, periodNumList.toArray());
			if (null != resultList && resultList.size() > 0) {
				for (Map<String, Object> rMap : resultList) {
					List<Integer> lotList = (List<Integer>) JSONArray.parse((String) rMap.get("ball"));
					Long periodNum = (Long) rMap.get("qishu");
					resultMap.put(String.valueOf(periodNum), lotList);

				}

			}

		}

		return resultMap;
	}
	
	/**
	 * 删除临时表的记录
	 * @author  wb
	 */
	public void deleteTypeWinInfo() {
		String sql="delete from tb_play_win_info where update_time<sysdate";
		jdbcTemplate.execute(sql);
	}
}
