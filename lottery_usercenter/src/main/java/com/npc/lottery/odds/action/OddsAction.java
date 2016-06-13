package com.npc.lottery.odds.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.npc.lottery.boss.logic.interf.IShopsLogic;
import com.npc.lottery.common.Constant;
import com.npc.lottery.common.action.BaseAdminLotteryAction;
import com.npc.lottery.manage.logic.interf.ISystemLogic;
import com.npc.lottery.member.logic.interf.IBetLogic;
import com.npc.lottery.member.logic.interf.IPlayTypeLogic;
import com.npc.lottery.odds.entity.OpenPlayOdds;
import com.npc.lottery.odds.entity.ShopsPlayOdds;
import com.npc.lottery.odds.logic.interf.IShopOddsLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.sysmge.entity.ManagerUser;
public class OddsAction extends BaseAdminLotteryAction {
	
	private static final long serialVersionUID = 1L;
	
    private IPlayTypeLogic playTypeLogic;
    private IShopOddsLogic shopOddsLogic;
    private IBetLogic betLogic;
	private IGDPeriodsInfoLogic periodsInfoLogic;
    private String type="oddSet";
    private String oddsSubType="GDKLSF";
    private ArrayList<OpenPlayOdds> openOddsList = new ArrayList<OpenPlayOdds>();
    private String playType = "gd";   
	
	/*
	 * 	快乐十分 1,两面盘，2总和龙虎，3连码4，1-8球
	 *  时时彩  1-5球
	 */
	private String subType=Constant.LOTTERY_GDKLSF_SUBTYPE_BALL_FIRST;
	private IShopsLogic shopsLogic = null;
	private ISystemLogic systemLogic;
	private String periodsState;
	public ISystemLogic getSystemLogic() {
		return systemLogic;
	}

	public void setSystemLogic(ISystemLogic systemLogic) {
		this.systemLogic = systemLogic;
	}

	/**
	 * 特码赔率:A、B 首次进入为A
	 */
	private String oddsType = "A";
	/**
	 * 刷新時間
	 */
	private String searchTime = "30";
	 
	public IShopsLogic getShopsLogic() {
		return shopsLogic;
	}

	public void setShopsLogic(IShopsLogic shopsLogic) {
		this.shopsLogic = shopsLogic;
	}
	
	public String enterOddSet()
	{
	   this.getRequest().setAttribute("subType", subType);
 
		return "memberCenter";
	}
	
	//进入开盘赔率界面
	public String enterOpenOdds(){
		ManagerUser userInfo = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
		String shopCode = userInfo.getSafetyCode();
		if(subType.indexOf("GDKLSF")!=-1){
			initGDKLSFLotOpenOddsData(shopCode);
			type="privateAdmin";
			return "gd";
		}else if(subType.indexOf("CQSSC")!=-1){
			initCQSSCLotOpenOddsData(shopCode);
			type="privateAdmin";
			return "cq";
		}else if(subType.indexOf(Constant.LOTTERY_TYPE_BJSC)!=-1){
			initBJSCLotOpenOddsData(shopCode);
			type="privateAdmin";
			return "bjsc";
		}else if(subType.indexOf(Constant.LOTTERY_TYPE_NC)!=-1){
			initNCLotOpenOddsData(shopCode);
			type="privateAdmin";
			return "NC";
		}else{
			initK3LotOpenOddsData(shopCode);
			type="privateAdmin";
			return "K3";
		}
		/*else{
			initHKLotOpenOddsData(shopCode);
			type="privateAdmin";
			return "hk";
			
		}*/
	}
	//初始化重庆时时彩开盘赔率
	public void initCQSSCLotOpenOddsData(String shopCode){
		List<OpenPlayOdds> list=shopOddsLogic.queryCQSSCOpenOdds(shopCode);
		for(int i=0; i<list.size(); i++){
			OpenPlayOdds obj = list.get(i);
			openOddsList.add(obj);
		}
		for(int j=0; j<list.size(); j++){
			OpenPlayOdds obj = list.get(j);
			if(obj.getOddsType().trim().equals("CQSSC_BALL_FIRST")){openOddsList.set(0, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_BALL_SECOND")){openOddsList.set(1, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_BALL_THIRD")){openOddsList.set(2, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_BALL_FORTH")){openOddsList.set(3, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_BALL_FIFTH")){openOddsList.set(4, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_1-5_DX")){openOddsList.set(5, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_1-5_DS")){openOddsList.set(6, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_ZHDX")){openOddsList.set(7, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_ZHDS")){openOddsList.set(8, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_DOUBLESIDE_LH")){openOddsList.set(9, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_DOUBLESIDE_HE")){openOddsList.set(10, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_BZ_FRONT")){openOddsList.set(11, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_SZ_FRONT")){openOddsList.set(12, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_DZ_FRONT")){openOddsList.set(13, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_BS_FRONT")){openOddsList.set(14, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_ZL_FRONT")){openOddsList.set(15, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_BZ_MID")){openOddsList.set(16, obj);}
			if(obj.getOddsType().trim().equals("CQSSC_BZ_LAST")){openOddsList.set(17, obj);}
		}
	}
	
	//初始化北京赛车开盘赔率
	public void initBJSCLotOpenOddsData(String shopCode){
		List<OpenPlayOdds> list=shopOddsLogic.queryBJSCOpenOdds(shopCode);
		for(int i=0; i<list.size(); i++){
			OpenPlayOdds obj = list.get(i);
			openOddsList.add(obj);
		}
		for(int j=0; j<list.size(); j++){
			OpenPlayOdds obj = list.get(j);
			if(obj.getOddsType().trim().equals("BJ_BALL_FIRST")){openOddsList.set(0, obj);}
			if(obj.getOddsType().trim().equals("BJ_BALL_SECOND")){openOddsList.set(1, obj);}
			if(obj.getOddsType().trim().equals("BJ_BALL_THIRD")){openOddsList.set(2, obj);}
			if(obj.getOddsType().trim().equals("BJ_BALL_FORTH")){openOddsList.set(3, obj);}
			if(obj.getOddsType().trim().equals("BJ_BALL_FIFTH")){openOddsList.set(4, obj);}
			if(obj.getOddsType().trim().equals("BJ_BALL_SIXTH")){openOddsList.set(5, obj);}
			if(obj.getOddsType().trim().equals("BJ_BALL_SEVENTH")){openOddsList.set(6, obj);}
			if(obj.getOddsType().trim().equals("BJ_BALL_EIGHTH")){openOddsList.set(7, obj);}
			if(obj.getOddsType().trim().equals("BJ_BALL_NINTH")){openOddsList.set(8, obj);}
			if(obj.getOddsType().trim().equals("BJ_BALL_TENTH")){openOddsList.set(9, obj);}
			if(obj.getOddsType().trim().equals("BJ_1-10_DX")){openOddsList.set(10, obj);}
			if(obj.getOddsType().trim().equals("BJ_1-10_DS")){openOddsList.set(11, obj);}
			if(obj.getOddsType().trim().equals("BJ_1-5_LH")){openOddsList.set(12, obj);}
			if(obj.getOddsType().trim().equals("BJ_DOUBLESIDE_DA")){openOddsList.set(13, obj);}
			if(obj.getOddsType().trim().equals("BJ_DOUBLESIDE_X")){openOddsList.set(14, obj);}
			if(obj.getOddsType().trim().equals("BJ_DOUBLESIDE_DAN")){openOddsList.set(15, obj);}
			if(obj.getOddsType().trim().equals("BJ_DOUBLESIDE_S")){openOddsList.set(16, obj);}
			if(obj.getOddsType().trim().equals("BJ_GROUP_ONE")){openOddsList.set(17, obj);}
			if(obj.getOddsType().trim().equals("BJ_GROUP_TWO")){openOddsList.set(18, obj);}
			if(obj.getOddsType().trim().equals("BJ_GROUP_THREE")){openOddsList.set(19, obj);}
			if(obj.getOddsType().trim().equals("BJ_GROUP_FOUR")){openOddsList.set(20, obj);}
			if(obj.getOddsType().trim().equals("BJ_GROUP_FIVE")){openOddsList.set(21, obj);}
		}
	}
	
	//初始化K3开盘赔率
	public void initK3LotOpenOddsData(String shopCode){
		List<OpenPlayOdds> list=shopOddsLogic.queryK3OpenOdds(shopCode);
		for(int i=0; i<list.size(); i++){
			OpenPlayOdds obj = list.get(i);
			openOddsList.add(obj);
		}
		for(int j=0; j<list.size(); j++){
			OpenPlayOdds obj = list.get(j);
			if(obj.getOddsType().trim().equals("K3_DX")){openOddsList.set(0, obj);}
			if(obj.getOddsType().trim().equals("K3_SJ")){openOddsList.set(1, obj);}
			if(obj.getOddsType().trim().equals("K3_WS")){openOddsList.set(2, obj);}
			if(obj.getOddsType().trim().equals("K3_QS")){openOddsList.set(3, obj);}
			if(obj.getOddsType().trim().equals("K3_DS_4")){openOddsList.set(4, obj);}
			if(obj.getOddsType().trim().equals("K3_DS_5")){openOddsList.set(5, obj);}
			if(obj.getOddsType().trim().equals("K3_DS_6")){openOddsList.set(6, obj);}
			if(obj.getOddsType().trim().equals("K3_DS_7")){openOddsList.set(7, obj);}
			if(obj.getOddsType().trim().equals("K3_DS_8")){openOddsList.set(8, obj);}
			if(obj.getOddsType().trim().equals("K3_DS_9")){openOddsList.set(9, obj);}
			if(obj.getOddsType().trim().equals("K3_CP")){openOddsList.set(10, obj);}
			if(obj.getOddsType().trim().equals("K3_DP")){openOddsList.set(11, obj);}
		}
	}
	
	public void initHKLotOpenOddsData(String shopCode){
		openOddsList = new ArrayList<OpenPlayOdds>();
		List<OpenPlayOdds> list=shopOddsLogic.queryHKOpenOdds(shopCode);
		for(int i=0; i<list.size(); i++){
			OpenPlayOdds obj = list.get(i);
			openOddsList.add(obj);
		}
		for(int j=0; j<list.size(); j++){
			OpenPlayOdds obj = list.get(j);
			if(obj.getOddsType().trim().equals("HK_TA")){openOddsList.set(0, obj);}
			if(obj.getOddsType().trim().equals("HK_TB")){openOddsList.set(1, obj);}
			if(obj.getOddsType().trim().equals("HK_TM_DS")){openOddsList.set(2, obj);}
			if(obj.getOddsType().trim().equals("HK_TM_DX")){openOddsList.set(3, obj);}
			if(obj.getOddsType().trim().equals("HK_TMHS_DS")){openOddsList.set(4, obj);}
			if(obj.getOddsType().trim().equals("HK_TMWS_DX")){openOddsList.set(5, obj);}
			if(obj.getOddsType().trim().equals("HK_TM_RED")){openOddsList.set(6, obj);}
			if(obj.getOddsType().trim().equals("HK_TM_GREEN")){openOddsList.set(7, obj);}
			if(obj.getOddsType().trim().equals("HK_TM_BLUE")){openOddsList.set(8, obj);}
			if(obj.getOddsType().trim().equals("HK_TMSX_LONG")){openOddsList.set(9, obj);}
			if(obj.getOddsType().trim().equals("HK_TMSX_OTHER")){openOddsList.set(10, obj);}
			if(obj.getOddsType().trim().equals("HK_BB_RED_DAN")){openOddsList.set(11, obj);}
			if(obj.getOddsType().trim().equals("HK_BB_RED_SHUANG")){openOddsList.set(12, obj);}
			if(obj.getOddsType().trim().equals("HK_BB_RED_DA")){openOddsList.set(13, obj);}
			if(obj.getOddsType().trim().equals("HK_BB_RED_XIAO")){openOddsList.set(14, obj);}
			if(obj.getOddsType().trim().equals("HK_BB_GREEN_DAN")){openOddsList.set(15, obj);}
			if(obj.getOddsType().trim().equals("HK_BB_GREEN_SHUANG")){openOddsList.set(16, obj);}
			if(obj.getOddsType().trim().equals("HK_BB_GREEN_DA")){openOddsList.set(17, obj);}
			if(obj.getOddsType().trim().equals("HK_BB_GREEN_XIAO")){openOddsList.set(18, obj);}
			if(obj.getOddsType().trim().equals("HK_BB_BLUE_DAN")){openOddsList.set(19, obj);}
			if(obj.getOddsType().trim().equals("HK_BB_BLUE_SHUANG")){openOddsList.set(20, obj);}
			if(obj.getOddsType().trim().equals("HK_BB_BLUE_DA")){openOddsList.set(21, obj);}
			if(obj.getOddsType().trim().equals("HK_BB_BLUE_XIAO")){openOddsList.set(22, obj);}
			if(obj.getOddsType().trim().equals("HK_LX")){openOddsList.set(23, obj);}
			if(obj.getOddsType().trim().equals("HK_ZM")){openOddsList.set(24, obj);}
			if(obj.getOddsType().trim().equals("HK_SX_LONG")){openOddsList.set(25, obj);}
			if(obj.getOddsType().trim().equals("HK_SX_OTHER")){openOddsList.set(26, obj);}
			if(obj.getOddsType().trim().equals("HK_WS_ZERO")){openOddsList.set(27, obj);}
			if(obj.getOddsType().trim().equals("HK_WS_OTHER")){openOddsList.set(28, obj);}
			if(obj.getOddsType().trim().equals("HK_ZHDX")){openOddsList.set(29, obj);}
			if(obj.getOddsType().trim().equals("HK_ZHDS")){openOddsList.set(30, obj);}
			if(obj.getOddsType().trim().equals("HK_ZM1TO6")){openOddsList.set(31, obj);}
			if(obj.getOddsType().trim().equals("HK_ZM1TO6_DX")){openOddsList.set(32, obj);}
			if(obj.getOddsType().trim().equals("HK_ZM1TO6_DS")){openOddsList.set(33, obj);}
			if(obj.getOddsType().trim().equals("HK_ZM1TO6_HSDS")){openOddsList.set(34, obj);}
			if(obj.getOddsType().trim().equals("HK_ZM1TO6_RED")){openOddsList.set(35, obj);}			
			if(obj.getOddsType().trim().equals("HK_ZM1TO6_GREEN")){openOddsList.set(36, obj);}
			if(obj.getOddsType().trim().equals("HK_ZM1TO6_BLUE")){openOddsList.set(37, obj);}
			if(obj.getOddsType().trim().equals("HK_SXL_2XL_LONG")){openOddsList.set(38, obj);}
			if(obj.getOddsType().trim().equals("HK_SXL_2XL_OTHER")){openOddsList.set(39, obj);}
			if(obj.getOddsType().trim().equals("HK_SXL_3XL_LONG")){openOddsList.set(40, obj);}
			if(obj.getOddsType().trim().equals("HK_SXL_3XL_OTHER")){openOddsList.set(41, obj);}
			if(obj.getOddsType().trim().equals("HK_SXL_4XL_LONG")){openOddsList.set(42, obj);}
			if(obj.getOddsType().trim().equals("HK_SXL_4XL_OTHER")){openOddsList.set(43, obj);}
			if(obj.getOddsType().trim().equals("HK_WSL_2WL_ZERO")){openOddsList.set(44, obj);}
			if(obj.getOddsType().trim().equals("HK_WSL_2WL_OTHER")){openOddsList.set(45, obj);}
			if(obj.getOddsType().trim().equals("HK_WSL_3WL_ZERO")){openOddsList.set(46, obj);}
			if(obj.getOddsType().trim().equals("HK_WSL_3WL_OTHER")){openOddsList.set(47, obj);}
			if(obj.getOddsType().trim().equals("HK_WSL_4WL_ZERO")){openOddsList.set(48, obj);}			
			if(obj.getOddsType().trim().equals("HK_WSL_4WL_OTHER")){openOddsList.set(49, obj);}
			if(obj.getOddsType().trim().equals("HK_WBZ")){openOddsList.set(50, obj);}
			if(obj.getOddsType().trim().equals("HK_GG_ZMDS")){openOddsList.set(51, obj);}
			if(obj.getOddsType().trim().equals("HK_GG_ZMDX")){openOddsList.set(52, obj);}
			if(obj.getOddsType().trim().equals("HK_GG_ZMRED")){openOddsList.set(53, obj);}
			if(obj.getOddsType().trim().equals("HK_GG_ZMGREEN")){openOddsList.set(54, obj);}
			if(obj.getOddsType().trim().equals("HK_GG_ZMBLUE")){openOddsList.set(55, obj);}
			if(obj.getOddsType().trim().equals("HK_LM_SQZ")){openOddsList.set(56, obj);}
			if(obj.getOddsType().trim().equals("HK_LM_3Z2")){openOddsList.set(57, obj);}
			if(obj.getOddsType().trim().equals("HK_LM_Z3")){openOddsList.set(58, obj);}
			if(obj.getOddsType().trim().equals("HK_LM_2QZ")){openOddsList.set(59, obj);}
			if(obj.getOddsType().trim().equals("HK_LM_2ZT")){openOddsList.set(60, obj);}
			if(obj.getOddsType().trim().equals("HK_LM_Z2")){openOddsList.set(61, obj);}
			if(obj.getOddsType().trim().equals("HK_LM_TC")){openOddsList.set(62, obj);}
		}
	}
	
	public void initGDKLSFLotOpenOddsData(String shopCode){
		openOddsList = new ArrayList<OpenPlayOdds>();
		List<OpenPlayOdds> list=shopOddsLogic.queryGDKLSFOpenOdds(shopCode);	
		for(int i=0; i<list.size(); i++){
			OpenPlayOdds obj = list.get(i);
			openOddsList.add(obj);
		}
		for(int j=0; j<list.size(); j++){
			OpenPlayOdds obj = list.get(j);
			if(obj.getOddsType().trim().equals("GDKLSF_BALL_FIRST")){openOddsList.set(0, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_BALL_SECOND")){openOddsList.set(1, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_BALL_THIRD")){openOddsList.set(2, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_BALL_FORTH")){openOddsList.set(3, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_BALL_FIFTH")){openOddsList.set(4, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_BALL_SIXTH")){openOddsList.set(5, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_BALL_SEVENTH")){openOddsList.set(6, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_BALL_EIGHTH")){openOddsList.set(7, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_1-8_DX")){openOddsList.set(8, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_1-8_DS")){openOddsList.set(9, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_1-8_WDX")){openOddsList.set(10, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_1-8_HSDS")){openOddsList.set(11, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_1-8_FW")){openOddsList.set(12, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_1-8_ZFB")){openOddsList.set(13, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_1-8_ZFB_B")){openOddsList.set(14, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_ZHDX")){openOddsList.set(15, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_ZHDS")){openOddsList.set(16, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_ZHWSDX")){openOddsList.set(17, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_DOUBLESIDE_LH")){openOddsList.set(18, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_RX2")){openOddsList.set(19, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_R2LZ")){openOddsList.set(20, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_RX3")){openOddsList.set(21, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_R3LZ")){openOddsList.set(22, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_RX4")){openOddsList.set(23, obj);}
			if(obj.getOddsType().trim().equals("GDKLSF_RX5")){openOddsList.set(24, obj);}
		}
	}
	
	public void initNCLotOpenOddsData(String shopCode){
		openOddsList = new ArrayList<OpenPlayOdds>();
		List<OpenPlayOdds> list=shopOddsLogic.queryNCOpenOdds(shopCode);	
		for(int i=0; i<list.size(); i++){
			OpenPlayOdds obj = list.get(i);
			openOddsList.add(obj);
		}
		for(int j=0; j<list.size(); j++){
			OpenPlayOdds obj = list.get(j);
			if(obj.getOddsType().trim().equals("NC_BALL_FIRST")){openOddsList.set(0, obj);}
			if(obj.getOddsType().trim().equals("NC_BALL_SECOND")){openOddsList.set(1, obj);}
			if(obj.getOddsType().trim().equals("NC_BALL_THIRD")){openOddsList.set(2, obj);}
			if(obj.getOddsType().trim().equals("NC_BALL_FORTH")){openOddsList.set(3, obj);}
			if(obj.getOddsType().trim().equals("NC_BALL_FIFTH")){openOddsList.set(4, obj);}
			if(obj.getOddsType().trim().equals("NC_BALL_SIXTH")){openOddsList.set(5, obj);}
			if(obj.getOddsType().trim().equals("NC_BALL_SEVENTH")){openOddsList.set(6, obj);}
			if(obj.getOddsType().trim().equals("NC_BALL_EIGHTH")){openOddsList.set(7, obj);}
			if(obj.getOddsType().trim().equals("NC_1-8_DX")){openOddsList.set(8, obj);}
			if(obj.getOddsType().trim().equals("NC_1-8_DS")){openOddsList.set(9, obj);}
			if(obj.getOddsType().trim().equals("NC_1-8_WDX")){openOddsList.set(10, obj);}
			if(obj.getOddsType().trim().equals("NC_1-8_HSDS")){openOddsList.set(11, obj);}
			if(obj.getOddsType().trim().equals("NC_1-8_FW")){openOddsList.set(12, obj);}
			if(obj.getOddsType().trim().equals("NC_1-8_ZFB")){openOddsList.set(13, obj);}
			if(obj.getOddsType().trim().equals("NC_1-8_ZFB_B")){openOddsList.set(14, obj);}
			if(obj.getOddsType().trim().equals("NC_ZHDX")){openOddsList.set(15, obj);}
			if(obj.getOddsType().trim().equals("NC_ZHDS")){openOddsList.set(16, obj);}
			if(obj.getOddsType().trim().equals("NC_ZHWSDX")){openOddsList.set(17, obj);}
			if(obj.getOddsType().trim().equals("NC_DOUBLESIDE_LH")){openOddsList.set(18, obj);}
			if(obj.getOddsType().trim().equals("NC_RX2")){openOddsList.set(19, obj);}
			if(obj.getOddsType().trim().equals("NC_R2LZ")){openOddsList.set(20, obj);}
			if(obj.getOddsType().trim().equals("NC_RX3")){openOddsList.set(21, obj);}
			if(obj.getOddsType().trim().equals("NC_R3LZ")){openOddsList.set(22, obj);}
			if(obj.getOddsType().trim().equals("NC_RX4")){openOddsList.set(23, obj);}
			if(obj.getOddsType().trim().equals("NC_RX5")){openOddsList.set(24, obj);}
		}
	}
	
	public String updateGdOpenOdds(){
		try {
			ManagerUser userInfo = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
			Long userID = userInfo.getID();
			String shopsCode = userInfo.getSafetyCode();
			shopOddsLogic.updateGdOpenOdds(openOddsList,userID,shopsCode);
			oddsSubType="GDKLSF";
			return SUCCESS;
		} catch (Exception e) {
			this.LOG.error("<-- 更新異常：updateGdOpenOdds-->", e);
			return INPUT;
		}
		
	}
	
	public String updateNCOpenOdds(){
		try {
			ManagerUser userInfo = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
			Long userID = userInfo.getID();
			String shopsCode = userInfo.getSafetyCode();
			shopOddsLogic.updateGdOpenOdds(openOddsList,userID,shopsCode);
			oddsSubType="NC";
			return SUCCESS;
		} catch (Exception e) {
			this.LOG.error("<-- 更新異常：updateNCOpenOdds-->", e);
			return INPUT;
		}
		
	}
	
	public String updateBjOpenOdds(){
		try {
			ManagerUser userInfo = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
			Long userID = userInfo.getID();
			String shopsCode = userInfo.getSafetyCode();
			shopOddsLogic.updateBjOpenOdds(openOddsList,userID,shopsCode);
			oddsSubType="BJSC";
			return SUCCESS;
		} catch (Exception e) {
			this.LOG.error("<-- 更新異常：updateGdOpenOdds-->", e);
			return INPUT;
		}
		
	}
	public String updateCqOpenOdds(){
		try {
			ManagerUser userInfo = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
			Long userID = userInfo.getID();
			String shopsCode = userInfo.getSafetyCode();
			shopOddsLogic.updateCqOpenOdds(openOddsList,userID,shopsCode);
			oddsSubType="CQSSC";			
			return SUCCESS;
		} catch (Exception e) {
			this.LOG.error("<-- 更新異常：updateCqOpenOdds-->", e);
			return INPUT;
		}
		
	}
	public String updateK3OpenOdds(){
		try {
			ManagerUser userInfo = (ManagerUser) getRequest().getSession(true).getAttribute(Constant.MANAGER_LOGIN_INFO_IN_SESSION);
			Long userID = userInfo.getID();
			String shopsCode = userInfo.getSafetyCode();
			shopOddsLogic.updateK3OpenOdds(openOddsList,userID,shopsCode);
			oddsSubType="K3";			
			return SUCCESS;
		} catch (Exception e) {
			this.LOG.error("<-- 更新異常：updateK3OpenOdds-->", e);
			return INPUT;
		}
		
	}

	public String shopLogin(){
	    
		return SUCCESS;
	}
	
	public String chooseLogin(){
		return SUCCESS;
	}
	
	public String userLogin(){
		return SUCCESS;
	}

	public IPlayTypeLogic getPlayTypeLogic() {
		return playTypeLogic;
	}

	public void setPlayTypeLogic(IPlayTypeLogic playTypeLogic) {
		this.playTypeLogic = playTypeLogic;
	}

	public IGDPeriodsInfoLogic getPeriodsInfoLogic() {
		return periodsInfoLogic;
	}

	public void setPeriodsInfoLogic(IGDPeriodsInfoLogic periodsInfoLogic) {
		this.periodsInfoLogic = periodsInfoLogic;
	}
	
	public String getSubType() {
		return subType;
	}


	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public IShopOddsLogic getShopOddsLogic() {
		return shopOddsLogic;
	}

	public void setShopOddsLogic(IShopOddsLogic shopOddsLogic) {
		this.shopOddsLogic = shopOddsLogic;
	}

	public String getOddsSubType() {
		return oddsSubType;
	}

	public void setOddsSubType(String oddsSubType) {
		this.oddsSubType = oddsSubType;
	}


	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}

	public ArrayList<OpenPlayOdds> getOpenOddsList() {
		return openOddsList;
	}

	public void setOpenOddsList(ArrayList<OpenPlayOdds> openOddsList) {
		this.openOddsList = openOddsList;
	}

	public String getOddsType() {
		return oddsType;
	}

	public void setOddsType(String oddsType) {
		this.oddsType = oddsType;
	}

	public IBetLogic getBetLogic() {
		return betLogic;
	}

	public void setBetLogic(IBetLogic betLogic) {
		this.betLogic = betLogic;
	}

	public String getPeriodsState() {
		return periodsState;
	}

	public void setPeriodsState(String periodsState) {
		this.periodsState = periodsState;
	}

	public String getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
	}

}

