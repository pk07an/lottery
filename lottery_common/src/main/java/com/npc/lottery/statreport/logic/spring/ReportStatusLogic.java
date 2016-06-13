package com.npc.lottery.statreport.logic.spring;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;

import com.npc.lottery.common.service.BaseLogic;
import com.npc.lottery.statreport.dao.interf.IReportStatusDao;
import com.npc.lottery.statreport.entity.ReportStatus;
import com.npc.lottery.statreport.logic.interf.IReportStatusLogic;

public class ReportStatusLogic extends BaseLogic implements IReportStatusLogic{
   
	@Override
	public ReportStatus findReportStatus(Criterion... criterions) {
		List<ReportStatus> list = reportStatusDao.find(criterions);
		ReportStatus reportStatus = null;
		if(list.size()>0){
			reportStatus = list.get(0);
		}
		return reportStatus;
	}
	
	@Override
    public void updateReportStatus(String status,String schema){
		reportStatusDao.updateReportStatus(status, schema);
    }
	
	@Override
	public void updateReportStatus(ReportStatus reportStatus) {
		reportStatusDao.update(reportStatus);
	}
	
	private IReportStatusDao reportStatusDao;

	public IReportStatusDao getReportStatusDao() {
		return reportStatusDao;
	}
	
	public void setReportStatusDao(IReportStatusDao reportStatusDao) {
		this.reportStatusDao = reportStatusDao;
	}

	/**
	 * 查询报表计算信息
	 */
	@Override
	public ReportStatus findReportStatus(String scheme) {
		return reportStatusDao.findReportStatus(scheme);
	}

	@Override
	public void updateReportStatusByOpt(String opt, String scheme) {
		reportStatusDao.updateReportStatusByOpt(opt, scheme);
		
	}

	@Override
	public List<ReportStatus> findReportStatusBySchema(String schema) {
		
		return reportStatusDao.findReportStatusBySchema(schema);
	}

}
