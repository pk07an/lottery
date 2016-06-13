package com.npc.lottery.replenish.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.npc.lottery.common.action.BaseLotteryAction;
import com.npc.lottery.common.Constant;
import com.npc.lottery.util.Page;
import com.npc.lottery.periods.logic.interf.IBJSCPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.ICQPeriodsInfoLogic;
import com.npc.lottery.periods.logic.interf.IGDPeriodsInfoLogic;
import com.npc.lottery.replenish.entity.ReplenishAuto;
import com.npc.lottery.replenish.entity.ReplenishAutoSetLog;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoLogLogic;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoLogic;
import com.npc.lottery.replenish.logic.interf.IReplenishAutoSetLogLogic;
import com.npc.lottery.sysmge.entity.ManagerStaff;
import com.npc.lottery.sysmge.entity.ManagerUser;
import com.npc.lottery.user.logic.interf.ICommonUserLogic;
import com.npc.lottery.util.Tools;
public class AutoReplenishAction extends BaseLotteryAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7778005466642622243L;
	private static Logger log = Logger.getLogger(AutoReplenishAction.class);
    private String type = "personalAdmin_autoReplenish";

	private IReplenishAutoLogic replenishAutoLogic;
	private IReplenishAutoLogLogic replenishAutoLogLogic;
	private IReplenishAutoSetLogLogic replenishAutoSetLogLogic;
	
	
	
	/**
	 * 自動補貨 设定入口
	 * 这里的最低的起补额度不从数据库取了，直接取交易设定里面的最低投注额
	 * @return
	 */
    public String autoReplenish(){
    	type = "personalAdmin_autoReplenish";
    	ManagerUser uu = this.getCurrentManagerUser();
    	//子帐号处理*********START
    	ManagerUser userInfoNew = new ManagerUser();
    	try {
    		BeanUtils.copyProperties(userInfoNew, uu);
    	} catch (Exception e) {
    	  log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
    	}
    	   if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
    		userInfoNew = getSubAccountParent(userInfoNew);	
    	   }	
    	//子帐号处理*********END
    	
    	long userId = userInfoNew.getID();
    	String userType = userInfoNew.getUserType()+"".trim();
    	Integer createUser = Integer.valueOf((userId+"").trim());
    	long shopsCode = userInfoNew.getShopsInfo().getID();
    	
    	Map<String, String> trueMoneyMap = replenishAutoLogic.queryTotalTrueMoney(userInfoNew);//統計全部玩法的實貨
    	
    	List<Criterion> filters = new ArrayList<Criterion>();
    	filters.add(Restrictions.eq("shopsID",shopsCode));
		filters.add(Restrictions.eq("createUser",Long.valueOf(userId).intValue()));
		
    	List<ReplenishAuto>	autoList = replenishAutoLogic.listReplenishAutoList(filters.toArray(new Criterion[filters.size()]));
    	
    	if(autoList==null || autoList.size()==0){
    	
    		autoList = new ArrayList<ReplenishAuto>();
    	ReplenishAuto e = new ReplenishAuto();
    	e.setTypeCode("CQSSC_BALL_FIRST");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("CQSSC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("CQSSC_BALL_SECOND");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("CQSSC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("CQSSC_BALL_THIRD");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("CQSSC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("CQSSC_BALL_FORTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("CQSSC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("CQSSC_BALL_FIFTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("CQSSC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("CQSSC_1T5_DX");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("CQSSC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("CQSSC_1T5_DS");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("CQSSC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("CQSSC_ZHDX");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("CQSSC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("CQSSC_ZHDS");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("CQSSC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("CQSSC_LHH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("CQSSC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("CQSSC_THREE_FRONT");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("CQSSC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("CQSSC_THREE_MID");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("CQSSC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("CQSSC_THREE_LAST");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("CQSSC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	//---------GD START---------------
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_BALL_FIRST");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_BALL_SECOND");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_BALL_THIRD");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_BALL_FORTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_BALL_FIFTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_BALL_SIXTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_BALL_SEVENTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_BALL_EIGHTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_1T8_DX");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_1T8_DS");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_1T8_WDX");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_1T8_HSDS");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_1T8_FW");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_1T8_ZFB");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	/*e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_1T8_ZFB_B");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);*/
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_ZHDX");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_ZHDS");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_ZHWSDX");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_DOUBLESIDE_LH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_STRAIGHTTHROUGH_RX2");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_STRAIGHTTHROUGH_R2LZ");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_STRAIGHTTHROUGH_R2LH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_STRAIGHTTHROUGH_RX4");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_STRAIGHTTHROUGH_R3LZ");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_STRAIGHTTHROUGH_R3LH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_STRAIGHTTHROUGH_RX5");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("GDKLSF_STRAIGHTTHROUGH_RX3");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("GDKLSF");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	//-----------------GD END------------------

    	//---------NC START---------------
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_BALL_FIRST");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_BALL_SECOND");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_BALL_THIRD");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_BALL_FORTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_BALL_FIFTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_BALL_SIXTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_BALL_SEVENTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_BALL_EIGHTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_1T8_DX");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_1T8_DS");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_1T8_WDX");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_1T8_HSDS");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_1T8_FW");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_1T8_ZFB");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	/*e = new ReplenishAuto();
    	e.setTypeCode("NC_1T8_ZFB_B");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);*/
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_ZHDX");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_ZHDS");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_ZHWSDX");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_DOUBLESIDE_LH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_STRAIGHTTHROUGH_RX2");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_STRAIGHTTHROUGH_R2LZ");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_STRAIGHTTHROUGH_R2LH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_STRAIGHTTHROUGH_RX4");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_STRAIGHTTHROUGH_R3LZ");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_STRAIGHTTHROUGH_R3LH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_STRAIGHTTHROUGH_RX5");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("NC_STRAIGHTTHROUGH_RX3");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("NC");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	//-----------------NC END------------------
    	
    	//--------------------BJ START------------
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_BALL_FIRST");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("BJ");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_BALL_SECOND");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("BJ");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_BALL_THIRD");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("BJ");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_BALL_FORTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("BJ");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_BALL_FIFTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("BJ");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_BALL_SIXTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("BJ");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_BALL_SEVENTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("BJ");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_BALL_EIGHTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("BJ");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_BALL_NINTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("BJ");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_BALL_TENTH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("BJ");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_1T10DX");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("BJ");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_1T10DS");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("BJ");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_1T5LH");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("BJ");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_GROUP_DX");
    	e.setCreateUser(createUser);
    	e.setType("BJ");
    	e.setShopsID(shopsCode);
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_GROUP_DS");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("BJ");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("BJ_GROUP");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("BJ");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	//***************************K3********************************************
    	e = new ReplenishAuto();
    	e.setTypeCode("K3_DX");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("K3");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("K3_SJ");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("K3");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("K3_WS");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("K3");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("K3_QS");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("K3");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("K3_DS");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("K3");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("K3_CP");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("K3");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	e = new ReplenishAuto();
    	e.setTypeCode("K3_DP");
    	e.setCreateUser(createUser);
    	e.setShopsID(shopsCode);
    	e.setType("K3");
    	e.setCreateUserType(userType);
    	autoList.add(e);
    	
    	
    	replenishAutoLogic.updateReplenishAuto(autoList);
    	}
    	
    	Map<String,Integer> lowestMap = replenishAutoLogic.queryLowestMoney(userInfoNew.getShopsInfo().getChiefStaffExt().getManagerStaffID());
    	
    	Map<String,ReplenishAuto> autoMap = new HashMap<String,ReplenishAuto>();
    	if(null != autoList && autoList.size()>0)
    	{
    		for(ReplenishAuto auto : autoList){
    			if(trueMoneyMap.get(auto.getTypeCode())!=null){
    				String trueM = trueMoneyMap.get(auto.getTypeCode());
    				//如果实货值比补货设置值大1的，就直接显示补货设置值
    				Integer moneyL = auto.getMoneyLimit();
    				if(new BigDecimal(trueM).subtract(new BigDecimal(moneyL)).compareTo(new BigDecimal(0))==1 &&
    						new BigDecimal(trueM).subtract(new BigDecimal(moneyL)).compareTo(new BigDecimal(1))==-1 ||
    						new BigDecimal(trueM).subtract(new BigDecimal(moneyL)).compareTo(new BigDecimal(1))==1){
    					
    					auto.setTrueMoney(new BigDecimal(moneyL));
    				}else{
    					//BigDecimal vv = new BigDecimal(trueM);
    					auto.setTrueMoney(new BigDecimal(trueM));
    				}
    			}else{
    				auto.setTrueMoney(BigDecimal.ZERO);
    			}
    			//处理起码额度
    			if(lowestMap.get(auto.getTypeCode())!=null){
    				auto.setMoneyRep(lowestMap.get(auto.getTypeCode()));
    			}else{
    				auto.setMoneyRep(0);
    			}
    			
    			autoMap.put(auto.getTypeCode(),auto);
    		}
    	}
    	//用户下面是否有投注
        boolean hasBet=commonUserLogic.queryUserTreeHasBet(userInfoNew.getID(),  userInfoNew.getUserType());
        this.getRequest().setAttribute("hasBet", hasBet);
        
    	this.getRequest().setAttribute("autoMap", autoMap);
    	this.getRequest().setAttribute("gdPeriodNum", trueMoneyMap.get("gdPeriodNum"));
    	this.getRequest().setAttribute("cqPeriodNum", trueMoneyMap.get("cqPeriodNum"));
    	this.getRequest().setAttribute("bjPeriodNum", trueMoneyMap.get("bjPeriodNum"));
    	this.getRequest().setAttribute("k3PeriodNum", trueMoneyMap.get("k3PeriodNum"));
    	this.getRequest().setAttribute("ncPeriodNum", trueMoneyMap.get("ncPeriodNum"));
    	
    	
    	
    	
    	return INPUT;
    }
    //检查
    public String checkSet(){
    	ManagerUser userInfo = this.getCurrentManagerUser();
    	//子帐号处理*********START
    	ManagerUser userInfoNew = new ManagerUser();
    	try {
    		BeanUtils.copyProperties(userInfoNew, userInfo);
    	} catch (Exception e) {
    	  log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
    	}
    	   if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
    		userInfoNew = getSubAccountParent(userInfoNew);	
    	   }	
    	//子帐号处理*********END
    	List<Criterion> filters = new ArrayList<Criterion>();
    	filters.add(Restrictions.eq("shopsID",userInfoNew.getShopsInfo().getID()));
		filters.add(Restrictions.eq("createUser",userInfoNew.getID()));
		
    	Map<String, String> trueMoneyMap = replenishAutoLogic.queryTotalTrueMoney(userInfoNew);//統計全部玩法的實貨
    	
    	Enumeration<?> names = this.getRequest().getParameterNames();
    	Map<String, String> resultMap = new HashMap<String, String>();
    	while (names.hasMoreElements()) {
			String typeCode = (String) names.nextElement();
			String moneyLimitStr = this.getRequest().getParameter(typeCode);
			if(moneyLimitStr!=null && GenericValidator.isFloat(moneyLimitStr)){
				if(trueMoneyMap.get(typeCode)!=null){
    				BigDecimal trueMoney = new BigDecimal(trueMoneyMap.get(typeCode));
    				trueMoney = trueMoney.setScale(0,BigDecimal.ROUND_HALF_UP);
    				BigDecimal moneyLimit =new BigDecimal(moneyLimitStr);
    				if(moneyLimit.compareTo(trueMoney) == -1){//小于
    					//TODO:以后要优化为開盤中......第一球‘最高控制額度’
    					String result = "開盤中......‘最高控制額度’不能低于‘最低可調額度’，若需低于‘最低可調額度’請先在‘手工補貨貨’將超出部分注額補出，請進行設定！！！";
    					resultMap.put("error", result);
    					return this.ajaxJson(resultMap);
    				}
    			}
			}
    	}
    	try {
    		this.updateAutoReplenish();
    		resultMap.put("success", "'自動補貨設置'已更新!");
		} catch (Exception e) {
			resultMap.put("error", "自動補貨設置異常,請稍侯再試,聯系系統管理員");
			log.info("自动补货設置出错。"+e.getMessage());
		}
    	return this.ajaxJson(resultMap);
    }
    /**
     * 更新自動補貨設置
     * @return
     */
	public String updateAutoReplenish(){
		
		type = "personalAdmin_autoReplenish";
//		String submitType = this.getRequest().getParameter("submitType");

		try{
		Enumeration<?> names = this.getRequest().getParameterNames();
    	List<ReplenishAuto> raList = new ArrayList<ReplenishAuto>();
    	String[] checkbox = this.getRequest().getParameterValues("checkbox");
    	ManagerUser uu = this.getCurrentManagerUser();
    	//子帐号处理*********START
    	ManagerUser userInfoNew = new ManagerUser();
    	try {
    		BeanUtils.copyProperties(userInfoNew, uu);
    	} catch (Exception e) {
    	  log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
    	}
    	   if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
    		userInfoNew = getSubAccountParent(userInfoNew);	
    	   }	
    	//子帐号处理*********END
    	long userId = userInfoNew.getID();
    	long shopsID = userInfoNew.getShopsInfo().getID();
    	
    	List<String> playList = new ArrayList<String>();
    	/*if (checkbox != null && checkbox.length != 0) {
    		notPlayList = Arrays.asList(checkbox);
    	}*/
    	while (names.hasMoreElements()) {
			String frm = (String) names.nextElement();
			playList.add(frm);
    	}
    	
    		ReplenishAuto spo = null;
    		List<Criterion> filters = new ArrayList<Criterion>();
        	filters.add(Restrictions.eq("shopsID",shopsID));
    		filters.add(Restrictions.eq("createUser",Long.valueOf(userId).intValue()));
    		
        	List<ReplenishAuto>	autoList = replenishAutoLogic.listReplenishAutoList(filters.toArray(new Criterion[filters.size()]));
        	
        	if(autoList!=null && autoList.size()!=0){
	    		for(ReplenishAuto vo:autoList){
	    		  String ele = (String) vo.getTypeCode();
	    		  //System.out.println("~~~~~~~~~~~~~~~~ele is :" + ele);
	    		  //while (names.hasMoreElements()) {
	    			//String frm = (String) names.nextElement();
	    			
	    			spo = new ReplenishAuto();
	    			if (ele.indexOf("GDKLSF") > -1 || ele.indexOf("CQSSC") > -1 || ele.indexOf("BJ") > -1 || ele.indexOf("K3") > -1  || ele.indexOf("NC") > -1) {
	    				String type = ele.split("_")[0];
	    				String moneyLimit = this.getRequest().getParameter(ele);
	    				
						if (playList.contains(ele)){
							if(moneyLimit!=null && GenericValidator.isFloat(moneyLimit)){
								spo.setMoneyLimit(Integer.valueOf(moneyLimit));
		    				}else{
		    					spo.setMoneyLimit(0);
		    				}
							spo.setMoneyRep(2);
							spo.setState(Constant.ALOW_AUTO_REPLENISH);
						}else{
							spo.setMoneyLimit(0);
							spo.setMoneyRep(0);
							spo.setState(Constant.NO_ALOW_AUTO_REPLENISH);
						}
						spo.setCreateTime(new Date());
						spo.setCreateUser(Long.valueOf(userId).intValue());
						spo.setCreateUserType(userInfoNew.getUserType().trim());
						spo.setShopsID(shopsID);
						spo.setType(type);
						spo.setTypeCode(ele);
						
						raList.add(spo);
	    			}
	    		  //}
	    		}
    		}
    		
    		
    		replenishAutoLogic.updateReplenishAuto(raList);
    		
    	}catch(Exception ex){
    		log.error("<--更新自動補貨設置 異常  :updateAutoReplenish-->",ex);
    		return "exception";
    	}
    	
		
		return SUCCESS;
	}
	/**
	 * 跳轉到 重慶廣東 或 香港
	 * @return
	 */
	public String changeReplenish()
	{
		type = "personalAdmin_autoReplenish";
		String type = this.getRequest().getParameter("typeSelect");
		this.getRequest().setAttribute("type",type);
		
		if(type == null || type.equals("GDCQ")) return INPUT;
		
		if(type.equals("HK")) return "inputHK";
		
		
		return null;
	}
	//自動補貨日志
	public String autoReplenishLog() throws Exception{
		
		type = "personalAdmin_autoReplenishLog";
//		String areaLottery = this.getRequest().getParameter("areaLottery");
		String userId = "";
		String qUserID = getRequest().getParameter("qUserID");
		ManagerUser userInfo = this.getCurrentManagerUser();
		if(qUserID==null){
			//子帐号处理*********START
			ManagerUser userInfoNew = new ManagerUser();
			try {
				BeanUtils.copyProperties(userInfoNew, userInfo);
			} catch (Exception e) {
			  log.info("replenishAction returnFromUserType方法出错，转换userInfoSys里出错"+ e.getMessage());
			}
			   if(ManagerStaff.USER_TYPE_SUB.equals(userInfoNew.getUserType())){
				userInfoNew = getSubAccountParent(userInfoNew);	
			   }	
			//子帐号处理*********END
			   userId = userInfoNew.getID().toString();
		}else{
		    qUserID=Tools.decodeWithKey(qUserID);
			userId = qUserID;
		}
    	
		List<Criterion> filters = new ArrayList<Criterion>();
    	
		Page<ReplenishAutoSetLog> page = new Page<ReplenishAutoSetLog>(10);
        int pageNo = 1;
        if (this.getRequest().getParameter("pageNo") != null)
            pageNo = this.findParamInt("pageNo");
        page.setPageNo(pageNo);
        page.setOrderBy("createTime");
		page.setOrder("desc");
		filters.add(Restrictions.eq("createUserID", userId));
        try {
            page = replenishAutoSetLogLogic.queryLogByPage(page,userId);
            this.getRequest().setAttribute("page", page);
            this.getRequest().setAttribute("COMMISSION_UPDATE", Constant.CHANGE_LOG_CHANGE_TYPE_COMMISSION_UPDATE);
            this.getRequest().setAttribute("USERINFO_UPDATE", Constant.CHANGE_LOG_CHANGE_TYPE_USERINFO_UPDATE);
            this.getRequest().setAttribute("REPLENISH_AUTO_UPDATE", Constant.CHANGE_LOG_CHANGE_TYPE_REPLENISH_AUTO_UPDATE);
            
            this.getRequest().setAttribute("COMMISSIONA", Constant.CHANGE_LOG_CHANGE_SUB_TYPE_COMMISSIONA);
            this.getRequest().setAttribute("COMMISSIONB", Constant.CHANGE_LOG_CHANGE_SUB_TYPE_COMMISSIONB);
            this.getRequest().setAttribute("COMMISSIONC", Constant.CHANGE_LOG_CHANGE_SUB_TYPE_COMMISSIONC);
            this.getRequest().setAttribute("BETTING_QUOTAS", Constant.CHANGE_LOG_CHANGE_SUB_TYPE_BETTING_QUOTAS);
            this.getRequest().setAttribute("ITEM_QUOTAS", Constant.CHANGE_LOG_CHANGE_SUB_TYPE_ITEM_QUOTAS);
            this.getRequest().setAttribute("qUserID", Tools.encodeWithKey(userId));

        } catch (Exception e) {
            log.error("<--分頁 查詢異常：autoReplenishLog-->",e);
            return "exception";
        }
//        this.getRequest().setAttribute("areaLottery", areaLottery);
        
		return "success";
	}
	
	
	private IGDPeriodsInfoLogic periodsInfoLogic;
	private IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic;
	private ICQPeriodsInfoLogic icqPeriodsInfoLogic;
	protected ICommonUserLogic commonUserLogic;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public IReplenishAutoLogic getReplenishAutoLogic() {
		return replenishAutoLogic;
	}
	public void setReplenishAutoLogic(IReplenishAutoLogic replenishAutoLogic) {
		this.replenishAutoLogic = replenishAutoLogic;
	}
	public IReplenishAutoLogLogic getReplenishAutoLogLogic() {
		return replenishAutoLogLogic;
	}
	public void setReplenishAutoLogLogic(
			IReplenishAutoLogLogic replenishAutoLogLogic) {
		this.replenishAutoLogLogic = replenishAutoLogLogic;
	}
	public IReplenishAutoSetLogLogic getReplenishAutoSetLogLogic() {
		return replenishAutoSetLogLogic;
	}
	public void setReplenishAutoSetLogLogic(
			IReplenishAutoSetLogLogic replenishAutoSetLogLogic) {
		this.replenishAutoSetLogLogic = replenishAutoSetLogLogic;
	}
	public IGDPeriodsInfoLogic getPeriodsInfoLogic() {
		return periodsInfoLogic;
	}
	public IBJSCPeriodsInfoLogic getBjscPeriodsInfoLogic() {
		return bjscPeriodsInfoLogic;
	}
	public ICQPeriodsInfoLogic getIcqPeriodsInfoLogic() {
		return icqPeriodsInfoLogic;
	}
	public void setPeriodsInfoLogic(IGDPeriodsInfoLogic periodsInfoLogic) {
		this.periodsInfoLogic = periodsInfoLogic;
	}
	public void setBjscPeriodsInfoLogic(IBJSCPeriodsInfoLogic bjscPeriodsInfoLogic) {
		this.bjscPeriodsInfoLogic = bjscPeriodsInfoLogic;
	}
	public void setIcqPeriodsInfoLogic(ICQPeriodsInfoLogic icqPeriodsInfoLogic) {
		this.icqPeriodsInfoLogic = icqPeriodsInfoLogic;
	}
	public ICommonUserLogic getCommonUserLogic() {
		return commonUserLogic;
	}
	public void setCommonUserLogic(ICommonUserLogic commonUserLogic) {
		this.commonUserLogic = commonUserLogic;
	}
	
}

