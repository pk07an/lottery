package com.npc.lottery.replenish.logic.spring;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.npc.lottery.common.Constant;
import com.npc.lottery.member.dao.interf.IBetDao;
import com.npc.lottery.replenish.dao.interf.IZhangdanDao;
import com.npc.lottery.replenish.entity.Zhangdan;
import com.npc.lottery.replenish.logic.interf.IZhangdanLogic;
import com.npc.lottery.replenish.vo.ZhanDangLMDetailVO;
import com.npc.lottery.replenish.vo.ZhanDangVO;
import com.npc.lottery.replenish.logic.interf.IReplenishLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.logic.interf.ISubAccountInfoLogic;

/**
 * 报表统计相关的逻辑处理类
 * 
 */
public class ZhangdanLogic implements IZhangdanLogic {
    
    private String myColumn = null;
    private String nextColumn = null;
	private String rateUser = null;
	private String commissionUser = null;
	private String outCommissionUser = null;

    public void setReplenishLogic2(IReplenishLogic replenishLogic2) {
    }
    
    public void getUserType(ManagerUser rUserInfo,String currentUserType){

		boolean isSubAccount = rUserInfo.getUserType().equals(ManagerStaff.USER_TYPE_SUB);//子帐号
        //子帐号处理
        if(isSubAccount){
			new ManagerUser();
			subAccountInfoLogic.changeSubAccountInfo(rUserInfo);
		}
		boolean isChief = rUserInfo.getUserType().equals(ManagerStaff.USER_TYPE_CHIEF);// 总监类型
        boolean isBranch = rUserInfo.getUserType().equals(ManagerStaff.USER_TYPE_BRANCH);// 分公司类型
        boolean isStockholder = rUserInfo.getUserType().equals(ManagerStaff.USER_TYPE_STOCKHOLDER);// 股东
        boolean isGenAgent = rUserInfo.getUserType().equals(ManagerStaff.USER_TYPE_GEN_AGENT);// 总代理
        boolean isAgent = rUserInfo.getUserType().equals(ManagerStaff.USER_TYPE_AGENT);// 代理
        
        if(currentUserType.equals(ManagerStaff.USER_TYPE_CHIEF)){rateUser = "rate_chief";}
        if(currentUserType.equals(ManagerStaff.USER_TYPE_BRANCH)){rateUser = "rate_branch";}
        if(currentUserType.equals(ManagerStaff.USER_TYPE_STOCKHOLDER)){rateUser = "RATE_STOCKHOLDER";}
        if(currentUserType.equals(ManagerStaff.USER_TYPE_GEN_AGENT)){rateUser = "RATE_GEN_AGENT";}
        if(currentUserType.equals(ManagerStaff.USER_TYPE_AGENT)){rateUser = "RATE_AGENT";}
        /* 
         * 统计投注数据时，占成拿本身的，统计补下级补上时拥金拿下级，
         *                    统计自已补出时，拥金拿自己的。
         * 统计补货数据时，占成拿本身的，当是代理时拥金拿本身，当是代理以上时，拥金拿下一级。
         * 
         */
        if (isChief){myColumn = "CHIEFSTAFF";nextColumn="BRANCHSTAFF";commissionUser = "COMMISSION_BRANCH";outCommissionUser="COMMISSION_CHIEF";}
        if (isBranch){myColumn = "BRANCHSTAFF";nextColumn="STOCKHOLDERSTAFF";commissionUser = "COMMISSION_STOCKHOLDER";outCommissionUser="COMMISSION_BRANCH";}
        if (isStockholder){myColumn = "STOCKHOLDERSTAFF";nextColumn="GENAGENSTAFF";commissionUser = "COMMISSION_GEN_AGENT";outCommissionUser="COMMISSION_STOCKHOLDER";}
        if (isGenAgent){myColumn = "GENAGENSTAFF";nextColumn="replenish_user_id";commissionUser = "COMMISSION_AGENT";outCommissionUser="COMMISSION_GEN_AGENT";}
        if (isAgent){myColumn = "replenish_user_id";nextColumn="replenish_user_id";commissionUser = "COMMISSION_MEMBER";outCommissionUser="COMMISSION_AGENT";}
	}


	@Override
	public List<Zhangdan> findUnSettledReport(String lotteryType, String playType,String periodNum, Long userid, String userType,String currentUserType) {
		List<Zhangdan> result = new ArrayList<Zhangdan>();
		
		ManagerUser rUserInfo = new ManagerUser();
		rUserInfo.setID(userid);
		rUserInfo.setUserType(userType);
		this.getUserType(rUserInfo,currentUserType);
		String tableName = "";
		if(playType.equals("HK_TA")){tableName = Constant.HK_TM_TABLE_NAME;}
		if(playType.equals("HK_ZM")){tableName = Constant.HK_ZM_TABLE_NAME;}
		if(playType.equals("HK_ZT")){tableName = Constant.HK_ZTM_TABLE_NAME;}
		if(playType.equals("HK_ZM1TO6")){tableName = Constant.HK_ZM1To6_TABLE_NAME;}
		if(playType.equals("HK_LM")){tableName = Constant.HK_LM_TABLE_NAME;}
		if(playType.equals("HK_TM_SX")){tableName = Constant.HK_TMSX_TABLE_NAME;}
		if(playType.equals("HK_SXWS_WS")){tableName = Constant.HK_SXWS_TABLE_NAME;}
		if(playType.equals("HK_BB")){tableName = Constant.HK_BB_TABLE_NAME;}
		if(playType.equals("HK_LX")){tableName = Constant.HK_LX_TABLE_NAME;}
		if(playType.equals("HK_SXL")){tableName = Constant.HK_SXL_TABLE_NAME;}
		if(playType.equals("HK_WSL")){tableName = Constant.HK_WSL_TABLE_NAME;}
		if(playType.equals("HK_WBZ")){tableName = Constant.HK_WBZ_TABLE_NAME;}
		if(playType.equals("HK_GG")){tableName = Constant.HK_GG_TABLE_NAME;}
		if(playType.equals("GD_ZH")){tableName = Constant.GDKLSF_DOUBLESIDE_TABLE_NAME;}
		if(playType.equals("GD_LM")){tableName = Constant.GDKLSF_STRAIGHTTHROUGH_TABLE_NAME;}
		result = zhangdanDao.queryUnSettledReport(lotteryType, playType, periodNum, userid, userType,myColumn,rateUser, commissionUser,nextColumn,tableName);		
		
		return result;
	}
	
	@Override
	public List<ZhanDangVO> queryZhangdan(String lotteryType,String periodNum, Long userid, String userType,String currentUserType,String tableName) {
		List<ZhanDangVO> result = new ArrayList<ZhanDangVO>();
		
		ManagerUser rUserInfo = new ManagerUser();
		rUserInfo.setID(userid);
		rUserInfo.setUserType(userType);
		this.getUserType(rUserInfo,currentUserType);
		if("TB_GDKLSF_DOUBLE_SIDE".equals(tableName)){
			result = zhangdanDao.queryZhangDangDoubleLH(lotteryType,periodNum, userid, userType,myColumn,rateUser, commissionUser,nextColumn);	
		}else{	
			result = zhangdanDao.queryZhangDang(lotteryType,periodNum, userid, userType,myColumn,rateUser, commissionUser,nextColumn,tableName);		
		}
		return result;
	}
	
	@Override
	public Map<String,Object> queryZhangdanLM(String lotteryType,String periodNum, Long userId, String userType,String currentUserType) {
		Map<String,Object> map = new HashMap<String,Object>();
		ManagerUser rUserInfo = new ManagerUser();
		rUserInfo.setID(userId);
		rUserInfo.setUserType(userType);
		this.getUserType(rUserInfo,currentUserType);
		List<ZhanDangLMDetailVO> result = zhangdanDao.queryZhangDangLM(lotteryType,periodNum, userId, userType,myColumn,rateUser, commissionUser,nextColumn);
		List<ZhanDangLMDetailVO> resultOut = zhangdanDao.queryReplenishOutDetailLM(userId, periodNum, userType, outCommissionUser);
		List<ZhanDangLMDetailVO> resultIn = zhangdanDao.queryReplenishInDetailLM(userId, periodNum, rateUser, userType, commissionUser);
		
		if(resultOut!=null) result.addAll(resultOut);
		if(resultIn!=null) result.addAll(resultIn);
		
		List<ZhanDangLMDetailVO> resultRX2 = new ArrayList<ZhanDangLMDetailVO>();
		List<ZhanDangLMDetailVO> resultR2LZ = new ArrayList<ZhanDangLMDetailVO>();
		List<ZhanDangLMDetailVO> resultRX3 = new ArrayList<ZhanDangLMDetailVO>();
		List<ZhanDangLMDetailVO> resultR3LZ = new ArrayList<ZhanDangLMDetailVO>();
		List<ZhanDangLMDetailVO> resultRX4 = new ArrayList<ZhanDangLMDetailVO>();
		List<ZhanDangLMDetailVO> resultRX5 = new ArrayList<ZhanDangLMDetailVO>();
		
		Integer totalTunover = 0;
		BigDecimal totalMoney = BigDecimal.ZERO;
		BigDecimal totalCommissionMoney = BigDecimal.ZERO;
		
		for(ZhanDangLMDetailVO vo:result){
			totalTunover += 1;
			totalMoney = totalMoney.add(vo.getMoney());
			totalCommissionMoney = totalCommissionMoney.add(vo.getCommissionMoney());
			if("GDKLSF_STRAIGHTTHROUGH_RX2".equalsIgnoreCase(vo.getTypeCode())){
				resultRX2.add(vo);
			}else if("GDKLSF_STRAIGHTTHROUGH_R2LZ".equalsIgnoreCase(vo.getTypeCode())){
				resultR2LZ.add(vo);
			}else if("GDKLSF_STRAIGHTTHROUGH_RX3".equalsIgnoreCase(vo.getTypeCode())){
				resultRX3.add(vo);
			}else if("GDKLSF_STRAIGHTTHROUGH_R3LZ".equalsIgnoreCase(vo.getTypeCode())){
				resultR3LZ.add(vo);
			}else if("GDKLSF_STRAIGHTTHROUGH_RX4".equalsIgnoreCase(vo.getTypeCode())){
				resultRX4.add(vo);
			}else if("GDKLSF_STRAIGHTTHROUGH_RX5".equalsIgnoreCase(vo.getTypeCode())){
				resultRX5.add(vo);
			}
		}
		if(resultRX2!=null && !resultRX2.isEmpty()) map.put("RX2", resultRX2);
		if(resultR2LZ!=null && !resultR2LZ.isEmpty()) map.put("R2LZ", resultR2LZ);
		if(resultRX3!=null && !resultRX3.isEmpty()) map.put("RX3", resultRX3);
		if(resultR3LZ!=null && !resultR3LZ.isEmpty()) map.put("R3LZ", resultR3LZ);
		if(resultRX4!=null && !resultRX4.isEmpty()) map.put("RX4", resultRX4);
		if(resultRX5!=null && !resultRX5.isEmpty()) map.put("RX5", resultRX5);
		if(result!=null && result.size()>0){
			DecimalFormat df = new DecimalFormat("0.0");
    		String totalMoneyStr =  df.format(totalMoney.setScale(1,BigDecimal.ROUND_HALF_UP));
			String totalCommissionMoneyStr =  df.format(totalCommissionMoney.setScale(1,BigDecimal.ROUND_HALF_UP));
			map.put("totalTunover", totalTunover);
			map.put("totalMoney", totalMoneyStr);
			map.put("totalCommissionMoney", totalCommissionMoneyStr);
		}
		
		
		return map;
	}
	
	@Override
	public Map<String,Object> queryZhangdanLM_NC(String lotteryType,String periodNum, Long userId, String userType,String currentUserType) {
		Map<String,Object> map = new HashMap<String,Object>();
		ManagerUser rUserInfo = new ManagerUser();
		rUserInfo.setID(userId);
		rUserInfo.setUserType(userType);
		this.getUserType(rUserInfo,currentUserType);
		List<ZhanDangLMDetailVO> result = zhangdanDao.queryZhangDangLM_NC(lotteryType,periodNum, userId, userType,myColumn,rateUser, commissionUser,nextColumn);
		List<ZhanDangLMDetailVO> resultOut = zhangdanDao.queryReplenishOutDetailLM_NC(userId, periodNum, userType, outCommissionUser);
		List<ZhanDangLMDetailVO> resultIn = zhangdanDao.queryReplenishInDetailLM_NC(userId, periodNum, rateUser, userType, commissionUser);
		
		if(resultOut!=null) result.addAll(resultOut);
		if(resultIn!=null) result.addAll(resultIn);
		
		List<ZhanDangLMDetailVO> resultRX2 = new ArrayList<ZhanDangLMDetailVO>();
		List<ZhanDangLMDetailVO> resultR2LZ = new ArrayList<ZhanDangLMDetailVO>();
		List<ZhanDangLMDetailVO> resultRX3 = new ArrayList<ZhanDangLMDetailVO>();
		List<ZhanDangLMDetailVO> resultR3LZ = new ArrayList<ZhanDangLMDetailVO>();
		List<ZhanDangLMDetailVO> resultRX4 = new ArrayList<ZhanDangLMDetailVO>();
		List<ZhanDangLMDetailVO> resultRX5 = new ArrayList<ZhanDangLMDetailVO>();
		
		Integer totalTunover = 0;
		BigDecimal totalMoney = BigDecimal.ZERO;
		BigDecimal totalCommissionMoney = BigDecimal.ZERO;
		
		for(ZhanDangLMDetailVO vo:result){
			if("NC_STRAIGHTTHROUGH_RX2".equalsIgnoreCase(vo.getTypeCode())){
				resultRX2.add(vo);
				totalTunover += 1;
				totalMoney = totalMoney.add(vo.getMoney());
				totalCommissionMoney = totalCommissionMoney.add(vo.getCommissionMoney());
			}else if("NC_STRAIGHTTHROUGH_R2LZ".equalsIgnoreCase(vo.getTypeCode())){
				resultR2LZ.add(vo);
				totalTunover += 1;
				totalMoney = totalMoney.add(vo.getMoney());
				totalCommissionMoney = totalCommissionMoney.add(vo.getCommissionMoney());
			}else if("NC_STRAIGHTTHROUGH_RX3".equalsIgnoreCase(vo.getTypeCode())){
				resultRX3.add(vo);
				totalTunover += 1;
				totalMoney = totalMoney.add(vo.getMoney());
				totalCommissionMoney = totalCommissionMoney.add(vo.getCommissionMoney());
			}else if("NC_STRAIGHTTHROUGH_R3LZ".equalsIgnoreCase(vo.getTypeCode())){
				resultR3LZ.add(vo);
				totalTunover += 1;
				totalMoney = totalMoney.add(vo.getMoney());
				totalCommissionMoney = totalCommissionMoney.add(vo.getCommissionMoney());
			}else if("NC_STRAIGHTTHROUGH_RX4".equalsIgnoreCase(vo.getTypeCode())){
				resultRX4.add(vo);
				totalTunover += 1;
				totalMoney = totalMoney.add(vo.getMoney());
				totalCommissionMoney = totalCommissionMoney.add(vo.getCommissionMoney());
			}else if("NC_STRAIGHTTHROUGH_RX5".equalsIgnoreCase(vo.getTypeCode())){
				resultRX5.add(vo);
				totalTunover += 1;
				totalMoney = totalMoney.add(vo.getMoney());
				totalCommissionMoney = totalCommissionMoney.add(vo.getCommissionMoney());
			}
		}
		if(resultRX2!=null && !resultRX2.isEmpty()) map.put("RX2", resultRX2);
		if(resultR2LZ!=null && !resultR2LZ.isEmpty()) map.put("R2LZ", resultR2LZ);
		if(resultRX3!=null && !resultRX3.isEmpty()) map.put("RX3", resultRX3);
		if(resultR3LZ!=null && !resultR3LZ.isEmpty()) map.put("R3LZ", resultR3LZ);
		if(resultRX4!=null && !resultRX4.isEmpty()) map.put("RX4", resultRX4);
		if(resultRX5!=null && !resultRX5.isEmpty()) map.put("RX5", resultRX5);
		if(result!=null && result.size()>0){
			DecimalFormat df = new DecimalFormat("0.0");
			String totalMoneyStr =  df.format(totalMoney.setScale(1,BigDecimal.ROUND_HALF_UP));
			String totalCommissionMoneyStr =  df.format(totalCommissionMoney.setScale(1,BigDecimal.ROUND_HALF_UP));
			map.put("totalTunover", totalTunover);
			map.put("totalMoney", totalMoneyStr);
			map.put("totalCommissionMoney", totalCommissionMoneyStr);
		}
		
		
		return map;
	}
	
	@Override
	public List<ZhanDangVO> queryZhangdanForReplenish(String lotteryType,String periodNum, Long userId, String userType,String currentUserType) {
		
		List<ZhanDangVO> resultIn = new ArrayList<ZhanDangVO>();
		
		ManagerUser rUserInfo = new ManagerUser();
		rUserInfo.setID(userId);
		rUserInfo.setUserType(userType);
		this.getUserType(rUserInfo,currentUserType);
		
		resultIn = zhangdanDao.queryZhangDangForIn(lotteryType,periodNum,userId,userType,myColumn,rateUser,commissionUser,outCommissionUser,nextColumn);		
		return resultIn;
	}
	
	public List<ZhanDangVO> queryRightTotal(String lotteryType, String periodNum,
			Long userid, String userType, String myColumn, String rateUser,String status){
		List<ZhanDangVO> result = new ArrayList<ZhanDangVO>();
		List<ZhanDangVO> resultZH = new ArrayList<ZhanDangVO>();
		List<ZhanDangVO> resultLM = new ArrayList<ZhanDangVO>();
		
		result = zhangdanDao.queryBallRightTotal(lotteryType, periodNum, userid, userType, myColumn, rateUser, status);
		if(Constant.LOTTERY_TYPE_GDKLSF.equals(lotteryType)){
			resultZH = zhangdanDao.queryGdZHRightTotal(lotteryType, periodNum, userid, userType, myColumn, rateUser, status);
			resultLM = zhangdanDao.queryGDLMRightTotal(lotteryType, periodNum, userid, userType, myColumn, rateUser, status);
			if(resultZH!=null){result.addAll(resultZH);}
			if(resultLM!=null){result.addAll(resultLM);}
		}else if(Constant.LOTTERY_TYPE_NC.equals(lotteryType)){
			resultZH = zhangdanDao.queryNCZHRightTotal(lotteryType, periodNum, userid, userType, myColumn, rateUser, status);
			resultLM = zhangdanDao.queryNCLMRightTotal(lotteryType, periodNum, userid, userType, myColumn, rateUser, status);
			if(resultZH!=null){result.addAll(resultZH);}
			if(resultLM!=null){result.addAll(resultLM);}
		}else{
			resultZH = zhangdanDao.queryBJDoubleRightTotal(lotteryType, periodNum, userid, userType, myColumn, rateUser, status);
			if(resultZH!=null){result.addAll(resultZH);}
		}
		return result;
	}
	
	private IZhangdanDao zhangdanDao;
	    
    private IBetDao betDao;

    private ISubAccountInfoLogic subAccountInfoLogic;//子账号
    
    private IReplenishLogic replenishLogic;
	
    public void setZhangdanDao(IZhangdanDao zhangdanDao) {
        this.zhangdanDao = zhangdanDao;
    }
	public IBetDao getBetDao() {
		return betDao;
	}

	public void setBetDao(IBetDao betDao) {
		this.betDao = betDao;
	}

	public ISubAccountInfoLogic getSubAccountInfoLogic() {
		return subAccountInfoLogic;
	}

	public void setSubAccountInfoLogic(ISubAccountInfoLogic subAccountInfoLogic) {
		this.subAccountInfoLogic = subAccountInfoLogic;
	}

	public IReplenishLogic getReplenishLogic() {
		return replenishLogic;
	}

	public void setReplenishLogic(IReplenishLogic replenishLogic) {
		this.replenishLogic = replenishLogic;
	}

}
