package com.npc.lottery.statreport.dao.interf;

import java.util.List;

import com.npc.lottery.common.dao.ILotteryBaseDao;
import com.npc.lottery.statreport.entity.ReportStatus;

public interface IReportStatusDao extends ILotteryBaseDao<Long, ReportStatus>{

	public void updateReportStatus(String status, String schema);
	
	public ReportStatus findReportStatus(String scheme);
	
	public void updateReportStatusByOpt(String opt,String scheme);

	public List<ReportStatus> findReportStatusBySchema(String schema);
}
