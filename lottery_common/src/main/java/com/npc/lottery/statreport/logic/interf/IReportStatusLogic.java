package com.npc.lottery.statreport.logic.interf;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.npc.lottery.statreport.entity.ReportStatus;


public interface IReportStatusLogic {

	public ReportStatus findReportStatus(Criterion[] criterions);
	
	public ReportStatus findReportStatus(String scheme);

	public void updateReportStatus(ReportStatus reportStatus);

	public void updateReportStatus(String status, String schema);
	
	public void updateReportStatusByOpt(String opt,String scheme);
	
	public List<ReportStatus> findReportStatusBySchema(String schema);

}
