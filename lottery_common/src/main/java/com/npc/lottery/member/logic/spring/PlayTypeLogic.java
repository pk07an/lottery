package com.npc.lottery.member.logic.spring;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springmodules.cache.annotations.Cacheable;

import com.npc.lottery.member.dao.interf.IPlayTypeDao;
import com.npc.lottery.member.entity.PlayType;
import com.npc.lottery.member.logic.interf.IPlayTypeLogic;

/**
 * 功能逻辑处理类
 *
 * @author none
 *
 */
public class PlayTypeLogic implements IPlayTypeLogic {

	private IPlayTypeDao playTypeDao = null;
	
	private JdbcTemplate jdbcTemplate;

	private final static Logger log = Logger.getLogger(PlayTypeLogic.class);

	public IPlayTypeDao getPlayTypeDao() {
		return playTypeDao;
	}

	public void setPlayTypeDao(IPlayTypeDao playTypeDao) {
		this.playTypeDao = playTypeDao;
	}

	public List<PlayType> findPlayType(Criterion... criterions) {
		return playTypeDao.find(criterions);

	}

	@Cacheable(modelId = "testCaching")
	public PlayType getPlayTypeByTypeCode(String typeCode) {
		return playTypeDao.findUniqueBy("typeCode", typeCode);

	}

	public List<PlayType> findWinInfoPlayType(String playType) {
		Object[] values = { playType };
		String hql = "from PlayType  where playType=?";
		return playTypeDao.find(hql, values);

	}

	public void updatePlayTypeAmountZero(String playType, String scheme) {
		if (StringUtils.isNotEmpty(scheme)) {
			scheme = scheme + ".";
		}

		log.info("=======updatePlayTypeAmountZero开始执行SCHEME为:" + scheme + "==============");

		//String hql = "update " + scheme + "PlayAmount set moneyAmount=0 where  playType=?";
		//Object[] values = { playType };
		//playTypeDao.batchExecute(hql, values);
		final String sql = "update " + scheme + "TB_PLAY_AMOUNT set MONEY_AMOUNT=0 where  PLAY_TYPE=?";
		jdbcTemplate.update(sql, playType);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
