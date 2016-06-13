package com.npc.lottery.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 非业务数据的常量信息
 * 
 * @author none
 *
 */
public class Constant {

	public static final int LIST_PAGE_SIZE = 10;// 列表页面大小

	public static final int COMMENT_LIST_PAGE_SIZE = 10;// 评论列表页面大小

	public static final String USER_TYPE_LOGIN_IN_SESSION_MEMBER = "user_type_login_in_session_MEMBER";// 保存在session中的登陆用户类型（管理用户、会员用户）
	public static final String USER_TYPE_LOGIN_IN_SESSION_MANGER = "user_type_login_in_session_MANGER";// 保存在session中的登陆用户类型（管理用户、会员用户）

	public static final String LOGIN_CODE = "login_code";// 登陆验证码

	public static final String MANAGER_LOGIN_INFO_IN_SESSION = "manager_login_info_session";// 保存在session中的管理登陆信息

	public static final String MEMBER_LOGIN_INFO_IN_SESSION = "member_login_info_session";// 保存在session中的会员登陆信息

	public static final String SAFETY_INFO_IN_SESSION = "safety_info_in_session";// 保存在session中的安全码对象信息

	public static final String INFO_PAGE_MESSAGE = "infoPageMessage";// Info.jsp页面的信息（普通信息）提示参数

	public static final String INFO_PAGE_MESSAGE_KEY = "infoPageMessageKey";// Info.jsp页面的信息（资源文件中对应的ID）提示参数

	public static final String INFO_PAGE_MESSAGE_KEY_FILE = "infoPageMessageKeyFILE";

	public static final String INFO_PAGE_MESSAGE_KEY_ID = "infoPageMessageKeyID";

	public static final String INFO_PAGE_RETURN_URL = "infoPageReturnUrl";// Info.jsp页面的返回url参数

	public static final String INFO_PAGE_TYPE_CLOSE = "infoPageTypeClose";// Info.jsp页面显示关闭按钮

	public static final String INFO_PAGE_TYPE_RETURN = "infoPageTypeReturn";// Info.jsp页面显示返回按钮,返回到指定的url

	public static final String BACK_TREE_PAGE_RETURN = "treePageLocation";// back.jsp页面刷新树页面的url

	public static final String INFO_PAGE_TYPE_RETURN_SIMPLE = "infoPageTypeReturnSimple";// Info.jsp页面显示返回按钮，返回到history.back()

	public static final String PAGETAG_EXTEND_PARAM = "_pageButton";// 页面上分页标签提交操作时附加的参数

	public static final String PAGETAG_CURRENT = "_pagecount";// 页面上分页标签所记录跳转页码参数

	public static final String COLOR_GREEN = "#008000";// 绿色

	public static final String COLOR_GREY = "#808080";// 灰色

	public static final String COLOR_RED = "#FF0000";// 红色

	public static final long PAGE_TREE_ROOT_PARENT_ID = 0;// 显示树形结构的页面上，树的根节点所对应的上级节点ID值

	public static final String SELECT_USERID_USERNAME_SPLIT = "&&";// 弹出选择用户窗口中的返回值中，用户ID和用户名称之间的分割符号

	public static final String SELECT_ORGID_ORGNAME_SPLIT = "&&";// 弹出选择机构窗口中的返回值中，机构ID和机构名称之间的分割符号

	public static final String SELECT_MULTI_USER_SPLIT = "!!";// 弹出选择用户窗口中的返回值中，多个返回用户数据之间的分割符号

	public static final String SELECT_MULTI_ORG_SPLIT = "!!";// 弹出选择机构窗口中的返回值中，多个返回机构数据之间的分割符号

	public static final String TYPECODE_SPLIT = ","; // 用于分割从编辑表中读取的分类编码

	public static final String TYPECODE_VIEW_SPLIT = "；"; // 用于页面显示分类编码的分隔符

	public static final Integer AVALIABLE_CREDIT_LINE = 2000000000; // 初始化可用信用额度

	public static final Integer INIT_MEMBERNUM = 1000; // 初始化会员人数

	public static final String LOTTERY_TYPE_GDKLSF = "GDKLSF"; // 广东快乐十分

	public static final String LOTTERY_TYPE_NC = "NC"; // 幸运农场

	public static final String LOTTERY_TYPE_BJSC = "BJSC"; // 北京賽車

	public static final String LOTTERY_TYPE_BJ = "BJ"; // 广东快乐十分

	public static final String NOT_STATUS = "1"; // 未开盘

	public static final String OPEN_STATUS = "2"; // 已开盘

	public static final String INVALID_STATUS = "6"; // 盘期的状态停开或作废

	public static final String PET_STATUS_STOP = "4"; // 投注数据的状态停开

	public static final String PET_STATUS_CANCEL = "5"; // 投注数据的状态作废

	public static final String STOP_STATUS = "3"; // 已封盘

	public static final String HK_STOP_STATUS = "2"; // 已封盘

	public static final String HK_OPEN_STATUS = "0"; // 開盤

	public static final String HK_ZIDONG_OPEN_STATUS = "1"; // 自动開盤

	public static final String LOTTERY_STATUS = "4"; // 已开奖

	public static final String FAIL_STATUS = "5"; // 获取开奖号码失败

	public static final String SCAN_SUC_STATUS = "7"; // 系统兑奖成功

	public static final String TRUE_STATUS = "0"; // 实占

	public static final String EMPTY_STATUS = "1"; // 虚占

	public static final String COMPANY_STATUS = "2"; // 公司占

	public static final String DEFUALT_PLATE = "A"; // 默认盘期

	public static final String ALOW_REPLENISH = "0"; // 允许走飞

	public static final String NO_ALOW_REPLENISH = "1"; // 禁止走飞

	public static final String ALOW_AUTO_REPLENISH = "1"; // 允许自动走飞

	public static final String NO_ALOW_AUTO_REPLENISH = "0"; // 禁止自动走飞

	public static final String A = "A"; // A盘

	public static final String B = "B"; // B盘

	public static final String C = "C"; // C盘

	public static final String DEFAULT_PASSWORD = "111111"; // 默認密碼

	public static final String SHOP_OPEN = "0"; // 商铺状态：开放

	public static final String SHOP_FREEZE = "1"; // 商铺状态：冻结

	public static final String SHOP_CLOSE = "2"; // 商铺状态：关闭

	public static final String SHOP_OUT_DATE = "3"; // 商铺状态：过期

	public static final String ODD_LOG_AUTO = "1"; // 赔率操作的类型 --自动

	public static final String ODD_LOG_MENU = "2"; // 赔率操作的类型--手动

	public static final String OPEN = "0"; // 启用

	public static final String CLOSE = "1"; // 关闭或禁用

	public static final String LOGIN_USER_TYPE_MEMBER = "hh1"; // 从页面分析的登录类型--会员

	public static final String LOGIN_USER_TYPE_MANAGER = "kk1"; // 从页面分析的登录类型--管理

	public static final String LOTTERY_TYPE_GDKLSF_NAME = "廣東快樂十分";

	public static final String LOTTERY_TYPE_CQSSC_NAME = "重慶時時彩";

	public static final String LOTTERY_TYPE_BJSC_NAME = "北京賽車(PK10)";

	public static final String LOTTERY_TYPE_K3_NAME = "江苏骰寶(快3)";
	
	public static final String LOTTERY_TYPE_NC_NAME = "幸运农场";

	public static final String LEFT_OWNER_CHIEF = "0"; // 占余成数归 0，总监

	public static final String LEFT_OWNER_BRANCH = "1"; // 占余成数归1，分公司

	public static final String MENU_REPLENISH = "menuReplenish"; // 触发补货的类型为手动补货

	public static final String AUTO_REPLENISH = "autoReplenish"; // 触发补货的类型为自动补货

	public static final String LOTTERY_GDKLSF_SUBTYPE_DOUBLESIDE = "GDKLSF_DOUBLESIDE"; // 广东快乐
																						// 十分--两面盘

	public static final String LOTTERY_GDKLSF_SUBTYPE_DRAGON = "GDKLSF_ZHLH"; // 广东快乐
																			  // 十分--龙虎

	public static final String LOTTERY_GDKLSF_SUBTYPE_STRAIGHTTHROUGH = "GDKLSF_STRAIGHTTHROUGH"; // 广东快乐
																								  // 十分--连直

	public static final String LOTTERY_GDKLSF_SUBTYPE_BALL_FIRST = "GDKLSF_BALL_FIRST";
	public static final String LOTTERY_GDKLSF_SUBTYPE_BALL_SECOND = "GDKLSF_BALL_SECOND";
	public static final String LOTTERY_GDKLSF_SUBTYPE_BALL_THIRD = "GDKLSF_BALL_THIRD";
	public static final String LOTTERY_GDKLSF_SUBTYPE_BALL_FORTH = "GDKLSF_BALL_FORTH";
	public static final String LOTTERY_GDKLSF_SUBTYPE_BALL_FIFTH = "GDKLSF_BALL_FIFTH";
	public static final String LOTTERY_GDKLSF_SUBTYPE_BALL_SIXTH = "GDKLSF_BALL_SIXTH";
	public static final String LOTTERY_GDKLSF_SUBTYPE_BALL_SEVENTH = "GDKLSF_BALL_SEVENTH";
	public static final String LOTTERY_GDKLSF_SUBTYPE_BALL_EIGHTH = "GDKLSF_BALL_EIGHTH";
	public static final String LOTTERY_GDKLSF_SUBTYPE_ZHANGDAN = "GDKLSF_ZHANGDAN";

	// 农场
	public static final String LOTTERY_NC_SUBTYPE_DOUBLESIDE = "NC_DOUBLESIDE"; // 农场--两面盘

	public static final String LOTTERY_NC_SUBTYPE_DRAGON = "NC_ZHLH"; // 农场--龙虎

	public static final String LOTTERY_NC_SUBTYPE_STRAIGHTTHROUGH = "NC_STRAIGHTTHROUGH"; // 农场--连直

	public static final String LOTTERY_NC_SUBTYPE_BALL_FIRST = "NC_BALL_FIRST";
	public static final String LOTTERY_NC_SUBTYPE_BALL_SECOND = "NC_BALL_SECOND";
	public static final String LOTTERY_NC_SUBTYPE_BALL_THIRD = "NC_BALL_THIRD";
	public static final String LOTTERY_NC_SUBTYPE_BALL_FORTH = "NC_BALL_FORTH";
	public static final String LOTTERY_NC_SUBTYPE_BALL_FIFTH = "NC_BALL_FIFTH";
	public static final String LOTTERY_NC_SUBTYPE_BALL_SIXTH = "NC_BALL_SIXTH";
	public static final String LOTTERY_NC_SUBTYPE_BALL_SEVENTH = "NC_BALL_SEVENTH";
	public static final String LOTTERY_NC_SUBTYPE_BALL_EIGHTH = "NC_BALL_EIGHTH";
	public static final String LOTTERY_NC_SUBTYPE_ZHANGDAN = "NC_ZHANGDAN";

	// 北京賽車
	public static final String LOTTERY_BJSC_SUBTYPE_BALL_FIRST = "BJSC_BALL_FIRST";
	public static final String LOTTERY_BJSC_SUBTYPE_BALL_THIRD = "BJSC_BALL_THIRD";
	public static final String LOTTERY_BJSC_SUBTYPE_BALL_SEVENTH = "BJSC_BALL_SEVENTH";
	public static final String LOTTERY_BJSC_SUBTYPE_ZHANGDAN = "BJSC_ZHANGDAN";

	// 重庆时时彩
	public static final String LOTTERY_TYPE_CQSSC = "CQSSC";
	public static final String LOTTERY_CQSSC_SUBTYPE_BALL_FIRST = "CQSSC_BALL_FIRST";
	public static final String LOTTERY_CQSSC_SUBTYPE_BALL_SECOND = "CQSSC_BALL_SECOND";
	public static final String LOTTERY_CQSSC_SUBTYPE_BALL_THIRD = "CQSSC_BALL_THIRD";
	public static final String LOTTERY_CQSSC_SUBTYPE_BALL_FORTH = "CQSSC_BALL_FORTH";
	public static final String LOTTERY_CQSSC_SUBTYPE_BALL_FIFTH = "CQSSC_BALL_FIFTH";
	public static final String LOTTERY_CQSSC_SUBTYPE_DOUBLESIDE = "CQSSC_DOUBLESIDE";
	public static final String LOTTERY_CQSSC_SUBTYPE_ZHANGDAN = "CQSSC_ZHANGDAN";

	public static final String LOTTERY_TYPE_K3 = "K3"; // 江苏骰寶(快3)
	public static final String LOTTERY_K3_SUBTYPE_BALL = "K3_BALL";
	public static final String LOTTERY_K3_SUBTYPE_ZHANGDAN = "K3_ZHANGDAN";
	public static final String K3_TABLE_NAME = "TB_JSSB";// 江苏骰寶(快3)投注表
	public static final String K3_HIS_TABLE_NAME = "TB_JSSB_HIS";// 江苏骰寶(快3)投注历史表
	public static final String K3_HIS_TODAY_VIEW = "VIEW_JSSB_HIS_TODAY";
	public static final String K3_HIS_YESTERDAY_VIEW = "VIEW_JSSB_HIS_YESTERDAY";
	public static final String[] K3_TABLE_LIST = new String[] { K3_TABLE_NAME };
	public static final String LOTTERY_TYPE_HKLHC = "HKLHC"; // 香港六合彩
	public static final String LOTTERY_HKLHC_SUBTYPE_TE_MA = "HKLHC_TE_MA";
	public static final String LOTTERY_HKLHC_SUBTYPE_Z_TE_MA = "HKLHC_Z_TE_MA";
	public static final String LOTTERY_HKLHC_SUBTYPE_Z_TE_MA1 = "HKLHC_Z_TE_MA1";
	public static final String LOTTERY_HKLHC_SUBTYPE_Z_TE_MA2 = "HKLHC_Z_TE_MA2";
	public static final String LOTTERY_HKLHC_SUBTYPE_Z_TE_MA3 = "HKLHC_Z_TE_MA3";
	public static final String LOTTERY_HKLHC_SUBTYPE_Z_TE_MA4 = "HKLHC_Z_TE_MA4";
	public static final String LOTTERY_HKLHC_SUBTYPE_Z_TE_MA5 = "HKLHC_Z_TE_MA5";
	public static final String LOTTERY_HKLHC_SUBTYPE_Z_TE_MA6 = "HKLHC_Z_TE_MA6";
	public static final String LOTTERY_HKLHC_SUBTYPE_Z_MA = "HKLHC_Z_MA";

	public static final String LOTTERY_HKLHC_SUBTYPE_BAN_BO = "HKLHC_BAN_BO";
	public static final String LOTTERY_HKLHC_SUBTYPE_LIU_XIAO = "HKLHC_LIU_XIAO";

	public static final String LOTTERY_HKLHC_SUBTYPE_LIAN_XIAO = "HKLHC_LIAN_XIAO";

	public static final String LOTTERY_HKLHC_SUBTYPE_LIAN_WEI = "HKLHC_LIAN_WEI";
	public static final String LOTTERY_HKLHC_SUBTYPE_LIAN_MA = "HKLHC_LIAN_MA";
	public static final String LOTTERY_HKLHC_SUBTYPE_WU_BU_ZHONG = "HKLHC_WU_BU_ZHONG";
	public static final String LOTTERY_HKLHC_SUBTYPE_XIAO_WS = "HKLHC_XIAO_WS";
	public static final String LOTTERY_HKLHC_SUBTYPE_Z_1_6 = "HKLHC_Z_1_6";
	public static final String LOTTERY_HKLHC_SUBTYPE_TE_MA_XIAO = "HKLHC_TE_MA_XIAO";
	public static final String LOTTERY_HKLHC_SUBTYPE_GUO_GUAN = "HKLHC_GUO_GUAN";
	public static final String LOTTERY_HKLHC_SUBTYPE_ZHANGDAN_HK = "HKLHC_ZHANGDAN";

	// 广东快乐十分 分表
	public static final String GDKLSF_BALL_FIRST_TABLE_NAME = "TB_GDKLSF_BALL_FIRST";

	public static final String GDKLSF_BALL_SECOND_TABLE_NAME = "TB_GDKLSF_BALL_SECOND";
	public static final String GDKLSF_BALL_THIRD_TABLE_NAME = "TB_GDKLSF_BALL_THIRD";
	public static final String GDKLSF_BALL_FORTH_TABLE_NAME = "TB_GDKLSF_BALL_FORTH";
	public static final String GDKLSF_BALL_FIFTH_TABLE_NAME = "TB_GDKLSF_BALL_FIFTH";
	public static final String GDKLSF_BALL_SIXTH_TABLE_NAME = "TB_GDKLSF_BALL_SIXTH";
	public static final String GDKLSF_BALL_SEVENTH_TABLE_NAME = "TB_GDKLSF_BALL_SEVENTH";
	public static final String GDKLSF_BALL_EIGHTH_TABLE_NAME = "TB_GDKLSF_BALL_EIGHTH";

	public static final String GDKLSF_DOUBLESIDE_TABLE_NAME = "TB_GDKLSF_DOUBLE_SIDE";

	public static final String GDKLSF_HIS_TABLE_NAME = "TB_GDKLSF_HIS";
	public static final String GDKLSF_HIS_TODAY_VIEW = "VIEW_GDKLSF_HIS_TODAY";
	public static final String GDKLSF_HIS_YESTERDAY_VIEW = "VIEW_GDKLSF_HIS_YESTERDAY";

	// *****for mail
	public static final String SEND_MAIL_ACCOUNT = "cai1979168";
	public static final String SEND_MAIL_PASSWORD = "cai1979su";
	public static final String SEND_MAIL_SMTP = "smtp.163.com";
	public static final String SEND_MAIL_ADDRESS = "cai1979168@163.com";
	public static final String TO_MAIL_ADDRESS1 = "1684711942@qq.com";
	public static final String TO_MAIL_ADDRESS2 = "1684711942@qq.com";
	public static final String TO_MAIL_ADDRESS3 = "1684711942@qq.com";

	public static final String GDKLSF_STRAIGHTTHROUGH_TABLE_NAME = "TB_GDKLSF_STRAIGHTTHROUGH";
	public static final String[] GDKLSF_STRAIGHTTHROUGH_TABLE_NAME_LIST = new String[] { "TB_GDKLSF_STRAIGHTTHROUGH" };

	public static final String[] GDKLSF_TABLE_LIST = new String[] { GDKLSF_BALL_FIRST_TABLE_NAME,
	        GDKLSF_BALL_SECOND_TABLE_NAME, GDKLSF_BALL_THIRD_TABLE_NAME, GDKLSF_BALL_FORTH_TABLE_NAME,
	        GDKLSF_BALL_FIFTH_TABLE_NAME, GDKLSF_BALL_SIXTH_TABLE_NAME, GDKLSF_BALL_SEVENTH_TABLE_NAME,
	        GDKLSF_BALL_EIGHTH_TABLE_NAME, GDKLSF_DOUBLESIDE_TABLE_NAME, GDKLSF_STRAIGHTTHROUGH_TABLE_NAME };

	public static final String[] TEST_LIST = new String[] { GDKLSF_BALL_FIRST_TABLE_NAME, };

	public static final String SHOP_PLAY_ODD_STATUS_INVALID = "1";
	public static final String SHOP_PLAY_ODD_STATUS_VALID = "0";
	// 重庆时时彩
	public static final String CQSSC_BALL_FIRST_TABLE_NAME = "TB_CQSSC_BALL_FIRST";
	public static final String CQSSC_BALL_SECOND_TABLE_NAME = "TB_CQSSC_BALL_SECOND";
	public static final String CQSSC_BALL_THIRD_TABLE_NAME = "TB_CQSSC_BALL_THIRD";
	public static final String CQSSC_BALL_FORTH_TABLE_NAME = "TB_CQSSC_BALL_FORTH";
	public static final String CQSSC_BALL_FIFTH_TABLE_NAME = "TB_CQSSC_BALL_FIFTH";

	public static final String CQSSC_HIS_TABLE_NAME = "TB_CQSSC_HIS";
	public static final String CQSSC_HIS_TODAY_VIEW = "VIEW_CQSSC_HIS_TODAY";
	public static final String CQSSC_HIS_YESTERDAY_VIEW = "VIEW_CQSSC_HIS_YESTERDAY";

	public static final String BJSC_TABLE_NAME = "TB_BJSC";

	public static final String BJSC_HIS_TABLE_NAME = "TB_BJSC_HIS";
	public static final String BJSC_HIS_TODAY_VIEW = "VIEW_BJSC_HIS_TODAY";
	public static final String BJSC_HIS_YESTERDAY_VIEW = "VIEW_BJSC_HIS_YESTERDAY";

	// 农场
	public static final String NC_TABLE_NAME = "TB_NC";
	public static final String NC_HIS_TABLE_NAME = "TB_NC_HIS";
	public static final String NC_HIS_TODAY_VIEW = "VIEW_NC_HIS_TODAY";
	public static final String NC_HIS_YESTERDAY_VIEW = "VIEW_NC_HIS_YESTERDAY";

	public static final String[] CQSSC_TABLE_LIST = new String[] { CQSSC_BALL_FIRST_TABLE_NAME,
	        CQSSC_BALL_SECOND_TABLE_NAME, CQSSC_BALL_THIRD_TABLE_NAME, CQSSC_BALL_FORTH_TABLE_NAME,
	        CQSSC_BALL_FIFTH_TABLE_NAME };

	public static final String[] BJSC_TABLE_LIST = new String[] { BJSC_TABLE_NAME };

	public static final String[] BALANCE_TABLE_LIST = new String[] { CQSSC_BALL_FIRST_TABLE_NAME,
	        CQSSC_BALL_SECOND_TABLE_NAME, CQSSC_BALL_THIRD_TABLE_NAME, CQSSC_BALL_FORTH_TABLE_NAME,
	        CQSSC_BALL_FIFTH_TABLE_NAME, GDKLSF_BALL_FIRST_TABLE_NAME, GDKLSF_BALL_SECOND_TABLE_NAME,
	        GDKLSF_BALL_THIRD_TABLE_NAME, GDKLSF_BALL_FORTH_TABLE_NAME, GDKLSF_BALL_FIFTH_TABLE_NAME,
	        GDKLSF_BALL_SIXTH_TABLE_NAME, GDKLSF_BALL_SEVENTH_TABLE_NAME, GDKLSF_BALL_EIGHTH_TABLE_NAME,
	        GDKLSF_DOUBLESIDE_TABLE_NAME, GDKLSF_STRAIGHTTHROUGH_TABLE_NAME };

	public static final String[] ALL_TABLE_LIST = new String[] { CQSSC_BALL_FIRST_TABLE_NAME,
	        CQSSC_BALL_SECOND_TABLE_NAME, CQSSC_BALL_THIRD_TABLE_NAME, CQSSC_BALL_FORTH_TABLE_NAME,
	        CQSSC_BALL_FIFTH_TABLE_NAME, GDKLSF_BALL_FIRST_TABLE_NAME, GDKLSF_BALL_SECOND_TABLE_NAME,
	        GDKLSF_BALL_THIRD_TABLE_NAME, GDKLSF_BALL_FORTH_TABLE_NAME, GDKLSF_BALL_FIFTH_TABLE_NAME,
	        GDKLSF_BALL_SIXTH_TABLE_NAME, GDKLSF_BALL_SEVENTH_TABLE_NAME, GDKLSF_BALL_EIGHTH_TABLE_NAME,
	        GDKLSF_DOUBLESIDE_TABLE_NAME, GDKLSF_STRAIGHTTHROUGH_TABLE_NAME, BJSC_TABLE_NAME, K3_TABLE_NAME, NC_TABLE_NAME };

	public static final String[] ALL_HIS_LIST = new String[] { GDKLSF_HIS_TABLE_NAME, CQSSC_HIS_TABLE_NAME,
	        BJSC_HIS_TABLE_NAME, K3_HIS_TABLE_NAME, NC_HIS_TABLE_NAME };

	public static final String[] ALL_HIS_TODAY_VIEW = new String[] { GDKLSF_HIS_TODAY_VIEW, CQSSC_HIS_TODAY_VIEW,
	        BJSC_HIS_TODAY_VIEW, K3_HIS_TODAY_VIEW, NC_HIS_TODAY_VIEW };

	public static final String[] ALL_HIS_YESTERDAY_VIEW = new String[] { GDKLSF_HIS_YESTERDAY_VIEW,
	        CQSSC_HIS_YESTERDAY_VIEW, BJSC_HIS_YESTERDAY_VIEW, K3_HIS_YESTERDAY_VIEW, NC_HIS_YESTERDAY_VIEW };

	// 香港六合彩表
	public static final String HK_TM_TABLE_NAME = "TB_HKLHC_TE_MA";
	public static final String HK_ZM_TABLE_NAME = "TB_HKLHC_Z_MA";
	public static final String HK_ZTM_TABLE_NAME = "TB_HKLHC_Z_TE_MA";
	public static final String HK_ZM1To6_TABLE_NAME = "TB_HKLHC_ZM16";

	public static final String HK_LM_TABLE_NAME = "TB_HKLHC_LM";
	public static final String HK_TMSX_TABLE_NAME = "TB_HKLHC_TM_SX";
	public static final String HK_SXWS_TABLE_NAME = "TB_HKLHC_SX_WS";
	public static final String HK_BB_TABLE_NAME = "TB_HKLHC_BB";
	public static final String HK_LX_TABLE_NAME = "TB_HKLHC_LX";

	public static final String HK_SXL_TABLE_NAME = "TB_HKLHC_SXL";
	public static final String HK_WSL_TABLE_NAME = "TB_HKLHC_WSL";
	public static final String HK_WBZ_TABLE_NAME = "TB_HKLHC_WBZ";
	public static final String HK_GG_TABLE_NAME = "TB_HKLHC_GG";

	public static final String HKLHC_HIS_TABLE_NAME = "TB_HKLHC_HIS";

	public static final String HKLHC_REPLENISH_TABLE_NAME = "TB_REPLENISH";
	// add by peter for 重新兑奖补货历史表
	public static final String REPLENISH_TABLE_NAME_HIS = "TB_REPLENISH_HIS";
	public static final String REPLENISH_HIS_TODAY_VIEW = "VIEW_REPLENISH_HIS_TODAY";

	public static final String[] ALL_REPORT_HIS_LIST = new String[] { GDKLSF_HIS_TABLE_NAME, CQSSC_HIS_TABLE_NAME,
	        BJSC_HIS_TABLE_NAME, K3_HIS_TABLE_NAME, REPLENISH_TABLE_NAME_HIS,NC_HIS_TABLE_NAME };

	public static final String[] GDKLSF_LOTTERY_TABLE_LIST = new String[] { GDKLSF_BALL_FIRST_TABLE_NAME,
	        GDKLSF_BALL_SECOND_TABLE_NAME, GDKLSF_BALL_THIRD_TABLE_NAME, GDKLSF_BALL_FORTH_TABLE_NAME,
	        GDKLSF_BALL_FIFTH_TABLE_NAME, GDKLSF_BALL_SIXTH_TABLE_NAME, GDKLSF_BALL_SEVENTH_TABLE_NAME,
	        GDKLSF_BALL_EIGHTH_TABLE_NAME, GDKLSF_DOUBLESIDE_TABLE_NAME };
	public static final String[] CQSSC_LOTTERY_TABLE_LIST = new String[] { CQSSC_BALL_FIRST_TABLE_NAME,
	        CQSSC_BALL_SECOND_TABLE_NAME, CQSSC_BALL_THIRD_TABLE_NAME, CQSSC_BALL_FORTH_TABLE_NAME,
	        CQSSC_BALL_FIFTH_TABLE_NAME };
	public static final String[] HK_LOTTERY_TABLE_LIST = new String[] { HK_TM_TABLE_NAME, HK_ZM_TABLE_NAME,
	        HK_ZTM_TABLE_NAME, HK_ZM1To6_TABLE_NAME, HK_TMSX_TABLE_NAME, HK_SXWS_TABLE_NAME, HK_BB_TABLE_NAME };

	public static final String[] FS_TABLE_LIST = new String[] { GDKLSF_STRAIGHTTHROUGH_TABLE_NAME, HK_LM_TABLE_NAME,
	        HK_SXL_TABLE_NAME, HK_WSL_TABLE_NAME, HK_WBZ_TABLE_NAME, };

	public static final HashMap<String, String> HK_SX_MAP = new HashMap<String, String>();
	static {
		HK_SX_MAP.put("SHU", "鼠");
		HK_SX_MAP.put("HU", "虎");
		HK_SX_MAP.put("TU", "兔");
		HK_SX_MAP.put("SHE", "蛇");
		HK_SX_MAP.put("JI", "雞");
		HK_SX_MAP.put("MA", "馬");
		HK_SX_MAP.put("YANG", "羊");
		HK_SX_MAP.put("GOU", "狗");
		HK_SX_MAP.put("LONG", "龍");
		HK_SX_MAP.put("HOU", "猴");
		HK_SX_MAP.put("NIU", "牛");
		HK_SX_MAP.put("ZHU", "豬");
	}

	public static final HashMap<String, String> HK_WS_MAP = new HashMap<String, String>();
	static {
		HK_WS_MAP.put("W1", "1尾");
		HK_WS_MAP.put("W2", "2尾");
		HK_WS_MAP.put("W3", "3尾");
		HK_WS_MAP.put("W4", "4尾");
		HK_WS_MAP.put("W5", "5尾");
		HK_WS_MAP.put("W6", "6尾");
		HK_WS_MAP.put("W7", "7尾");
		HK_WS_MAP.put("W8", "8尾");
		HK_WS_MAP.put("W9", "9尾");
		HK_WS_MAP.put("W0", "0尾");

	}

	public static final HashMap<String, String> HK_GG_MAP = new HashMap<String, String>();
	static {
		HK_GG_MAP.put("ZM1_DA", "正碼一-大");
		HK_GG_MAP.put("ZM1_X", "正碼一-小");
		HK_GG_MAP.put("ZM1_DAN", "正碼一-單");
		HK_GG_MAP.put("ZM1_S", "正碼一-雙");
		HK_GG_MAP.put("ZM1_RED", "正碼一-紅波");
		HK_GG_MAP.put("ZM1_GREEN", "正碼一-綠波");
		HK_GG_MAP.put("ZM1_BLUE", "正碼一-藍波");

		HK_GG_MAP.put("ZM2_DA", "正碼二-大");
		HK_GG_MAP.put("ZM2_X", "正碼二-小");
		HK_GG_MAP.put("ZM2_DAN", "正碼二-單");
		HK_GG_MAP.put("ZM2_S", "正碼二-雙");
		HK_GG_MAP.put("ZM2_RED", "正碼二-紅波");
		HK_GG_MAP.put("ZM2_GREEN", "正碼二-綠波");
		HK_GG_MAP.put("ZM2_BLUE", "正碼二-藍波");

		HK_GG_MAP.put("ZM3_DA", "正碼三-大");
		HK_GG_MAP.put("ZM3_X", "正碼三-小");
		HK_GG_MAP.put("ZM3_DAN", "正碼三-單");
		HK_GG_MAP.put("ZM3_S", "正碼三-雙");
		HK_GG_MAP.put("ZM3_RED", "正碼三-紅波");
		HK_GG_MAP.put("ZM3_GREEN", "正碼三-綠波");
		HK_GG_MAP.put("ZM3_BLUE", "正碼三-藍波");

		HK_GG_MAP.put("ZM4_DA", "正碼四-大");
		HK_GG_MAP.put("ZM4_X", "正碼四-小");
		HK_GG_MAP.put("ZM4_DAN", "正碼四-單");
		HK_GG_MAP.put("ZM4_S", "正碼四-雙");
		HK_GG_MAP.put("ZM4_RED", "正碼四-紅波");
		HK_GG_MAP.put("ZM4_GREEN", "正碼四-綠波");
		HK_GG_MAP.put("ZM4_BLUE", "正碼四-藍波");

		HK_GG_MAP.put("ZM5_DA", "正碼五-大");
		HK_GG_MAP.put("ZM5_X", "正碼五-小");
		HK_GG_MAP.put("ZM5_DAN", "正碼五-單");
		HK_GG_MAP.put("ZM5_S", "正碼五-雙");
		HK_GG_MAP.put("ZM5_RED", "正碼五-紅波");
		HK_GG_MAP.put("ZM5_GREEN", "正碼五-綠波");
		HK_GG_MAP.put("ZM5_BLUE", "正碼五-藍波");

		HK_GG_MAP.put("ZM6_DA", "正碼六-大");
		HK_GG_MAP.put("ZM6_X", "正碼六-小");
		HK_GG_MAP.put("ZM6_DAN", "正碼六-單");
		HK_GG_MAP.put("ZM6_S", "正碼六-雙");
		HK_GG_MAP.put("ZM6_RED", "正碼六-紅波");
		HK_GG_MAP.put("ZM6_GREEN", "正碼六-綠波");
		HK_GG_MAP.put("ZM6_BLUE", "正碼六-藍波");

	}

	public static final String[] HK_TABLE_LIST = new String[] { HK_TM_TABLE_NAME, HK_ZM_TABLE_NAME, HK_ZTM_TABLE_NAME,
	        HK_ZM1To6_TABLE_NAME, HK_LM_TABLE_NAME, HK_TMSX_TABLE_NAME, HK_SXWS_TABLE_NAME, HK_BB_TABLE_NAME,
	        HK_LX_TABLE_NAME, HK_SXL_TABLE_NAME, HK_WSL_TABLE_NAME, HK_WBZ_TABLE_NAME, HK_GG_TABLE_NAME };

	public static final String COMMISSION_HK = "3";
	public static final String COMMISSION_GD = "1";
	public static final String COMMISSION_CQ = "2";
	public static final String COMMISSION_BJ = "4";
	// add by peter for K3
	public static final String COMMISSION_JS = "5";
	// add by peter for NC
	public static final String COMMISSION_NC = "6";

	// 总后台管理
	public static final String SHOPS_IN_USE = "0";// 新建商铺时默认为开启

	// 自動降賠類型：北京赛车 兩面盤
	public static final String BJSC_DOUBLESIDE = "BJSC_DOUBLESIDE";
	// 自動降賠類型：廣東 快樂十分 兩面盤
	public static final String GDKLSF_DOUBLESIDE = "GDKLSF_DOUBLESIDE";
	// 自動降賠類型：廣東 快樂十分 遺漏
	public static final String GDKLSF_YILOU = "GDKLSF_YILOU";
	// 自動降賠類型：幸运农场 遺漏
	public static final String NC_YILOU = "NC_YILOU";
	// 自動降賠類型： 重慶時時彩 兩面盤
	public static final String CQSSC_DOUBLESIDE = "CQSSC_DOUBLESIDE";
	// 自動降賠類型： 重慶時時彩 兩面盤
	public static final String NC_DOUBLESIDE = "NC_DOUBLESIDE";
	// 自動降賠類型： 重慶時時彩 遺漏
	public static final String CQSSC_YILOU = "CQSSC_YILOU";
	// 自動降賠類型： 香港六合彩 兩面盤
	public static final String HKLHC_DOUBLESIDE = "HKLHC_DOUBLESIDE";
	// 自動降賠類型： 香港六合彩 遺漏
	public static final String HKLHC_YILOU = "HKLHC_YILOU";

	public static final String SAFETYCODE_INTERNAL = "888888";// 系统 内置的安全码

	public static final Map<String, String> GDKLSF_COMMISSION_TYPE = new LinkedHashMap<String, String>();
	static {
		GDKLSF_COMMISSION_TYPE.put("GD_ONE_BALL", "第一球");
		GDKLSF_COMMISSION_TYPE.put("GD_TWO_BALL", "第二球");
		GDKLSF_COMMISSION_TYPE.put("GD_THREE_BALL", "第三球");
		GDKLSF_COMMISSION_TYPE.put("GD_FOUR_BALL", "第四球");
		GDKLSF_COMMISSION_TYPE.put("GD_FIVE_BALL", "第五球");
		GDKLSF_COMMISSION_TYPE.put("GD_SIX_BALL", "第六球");
		GDKLSF_COMMISSION_TYPE.put("GD_SEVEN_BALL", "第七球");
		GDKLSF_COMMISSION_TYPE.put("GD_EIGHT_BALL", "第八球");
		GDKLSF_COMMISSION_TYPE.put("GD_OEDX_BALL", "第1-8大小");
		GDKLSF_COMMISSION_TYPE.put("GD_OEDS_BALL", "第1-8單雙");
		GDKLSF_COMMISSION_TYPE.put("GD_OEWSDX_BALL", "第1-8尾數大小");

		GDKLSF_COMMISSION_TYPE.put("GD_HSDS_BALL", "第1-8合數單雙");
		GDKLSF_COMMISSION_TYPE.put("GD_FW_BALL", "第1-8方位");
		GDKLSF_COMMISSION_TYPE.put("GD_ZF_BALL", "第1-8中發白");

		GDKLSF_COMMISSION_TYPE.put("GD_ZHDX_BALL", "總和大小");
		GDKLSF_COMMISSION_TYPE.put("GD_ZHDS_BALL", "總和單雙");
		GDKLSF_COMMISSION_TYPE.put("GD_ZHWSDX_BALL", "總和尾數大小");
		GDKLSF_COMMISSION_TYPE.put("GD_LH_BALL", "龍虎");
		GDKLSF_COMMISSION_TYPE.put("GD_RXH_BALL", "任選二");
		GDKLSF_COMMISSION_TYPE.put("GD_RTLZ_BALL", "選二連組");

		// GDKLSF_COMMISSION_TYPE.put("GD_RTLZ_BALL", "任二連直");
		GDKLSF_COMMISSION_TYPE.put("GD_RXS_BALL", "任選三");
		// GDKLSF_COMMISSION_TYPE.put("GD_XSQZ_BALL", "選三前直");
		GDKLSF_COMMISSION_TYPE.put("GD_XTQZ_BALL", "選三前組");
		GDKLSF_COMMISSION_TYPE.put("GD_RXF_BALL", "任選四");
		GDKLSF_COMMISSION_TYPE.put("GD_RXW_BALL", "任選五");

	}

	public static final Map<String, String> NC_COMMISSION_TYPE = new LinkedHashMap<String, String>();
	static {
		NC_COMMISSION_TYPE.put("NC_ONE_BALL", "第一球");
		NC_COMMISSION_TYPE.put("NC_TWO_BALL", "第二球");
		NC_COMMISSION_TYPE.put("NC_THREE_BALL", "第三球");
		NC_COMMISSION_TYPE.put("NC_FOUR_BALL", "第四球");
		NC_COMMISSION_TYPE.put("NC_FIVE_BALL", "第五球");
		NC_COMMISSION_TYPE.put("NC_SIX_BALL", "第六球");
		NC_COMMISSION_TYPE.put("NC_SEVEN_BALL", "第七球");
		NC_COMMISSION_TYPE.put("NC_EIGHT_BALL", "第八球");
		NC_COMMISSION_TYPE.put("NC_OEDX_BALL", "第1-8大小");
		NC_COMMISSION_TYPE.put("NC_OEDS_BALL", "第1-8單雙");
		NC_COMMISSION_TYPE.put("NC_OEWSDX_BALL", "第1-8尾數大小");

		NC_COMMISSION_TYPE.put("NC_HSDS_BALL", "第1-8合數單雙");
		NC_COMMISSION_TYPE.put("NC_FW_BALL", "第1-8方位");
		NC_COMMISSION_TYPE.put("NC_ZF_BALL", "第1-8中發白");

		NC_COMMISSION_TYPE.put("NC_ZHDX_BALL", "總和大小");
		NC_COMMISSION_TYPE.put("NC_ZHDS_BALL", "總和單雙");
		NC_COMMISSION_TYPE.put("NC_ZHWSDX_BALL", "總和尾數大小");
		NC_COMMISSION_TYPE.put("NC_LH_BALL", "龍虎");
		NC_COMMISSION_TYPE.put("NC_RXH_BALL", "任選二");
		NC_COMMISSION_TYPE.put("NC_RTLZ_BALL", "選二連組");

		NC_COMMISSION_TYPE.put("NC_RXS_BALL", "任選三");
		NC_COMMISSION_TYPE.put("NC_XTQZ_BALL", "選三前組");
		NC_COMMISSION_TYPE.put("NC_RXF_BALL", "任選四");
		NC_COMMISSION_TYPE.put("NC_RXW_BALL", "任選五");

	}

	public static final Map<String, String> CQSSC_COMMISSION_TYPE = new LinkedHashMap<String, String>();
	static {

		CQSSC_COMMISSION_TYPE.put("CQ_ONE_BALL", "第一球");
		CQSSC_COMMISSION_TYPE.put("CQ_TWO_BALL", "第二球");
		CQSSC_COMMISSION_TYPE.put("CQ_THREE_BALL", "第三球");
		CQSSC_COMMISSION_TYPE.put("CQ_FOUR_BALL", "第四球");
		CQSSC_COMMISSION_TYPE.put("CQ_FIVE_BALL", "第五球");
		// CQSSC_COMMISSION_TYPE.put("GD_RXW_BALL", "任選五");

		CQSSC_COMMISSION_TYPE.put("CQ_ZHDX_BALL", "總和大小");
		CQSSC_COMMISSION_TYPE.put("CQ_ZHDS_BALL", "總和單雙");
		CQSSC_COMMISSION_TYPE.put("CQ_OFDX_BALL", "第1-5大小");
		CQSSC_COMMISSION_TYPE.put("CQ_OFDS_BALL", "第1-5單雙");
		CQSSC_COMMISSION_TYPE.put("CQ_LH_BALL", "龍虎和");
		CQSSC_COMMISSION_TYPE.put("CQ_QS_BALL", "前三");
		CQSSC_COMMISSION_TYPE.put("CQ_ZS_BALL", "中三");
		CQSSC_COMMISSION_TYPE.put("CQ_HS_BALL", "後三");

	}

	public static final Map<String, String> HKLHC_COMMISSION_TYPE = new LinkedHashMap<String, String>();
	static {

		HKLHC_COMMISSION_TYPE.put("HK_TA", "特A");
		HKLHC_COMMISSION_TYPE.put("HK_TB", "特B");
		HKLHC_COMMISSION_TYPE.put("HK_TM_DS", "特碼單雙");
		HKLHC_COMMISSION_TYPE.put("HK_TM_DX", "特碼大小");
		HKLHC_COMMISSION_TYPE.put("HK_TMHS_DS", "特碼合數單雙 ");
		HKLHC_COMMISSION_TYPE.put("HK_TMWS_DX", "特碼尾數大小");

		HKLHC_COMMISSION_TYPE.put("HK_TM_SB", "特碼色波");
		HKLHC_COMMISSION_TYPE.put("HK_ZM", "正碼");
		HKLHC_COMMISSION_TYPE.put("HK_ZT", "正特");
		HKLHC_COMMISSION_TYPE.put("HK_ZM1TO6_DS", "正碼1-6單雙");
		HKLHC_COMMISSION_TYPE.put("HK_ZM1TO6_DX", "正碼1-6大小");
		HKLHC_COMMISSION_TYPE.put("HK_ZM1TO6_HSDS", "正碼1-6合數單雙");

		HKLHC_COMMISSION_TYPE.put("HK_ZM1TO6_SB", "正碼1-6色波");
		HKLHC_COMMISSION_TYPE.put("HK_LM", "连碼");
		HKLHC_COMMISSION_TYPE.put("HK_TM_SX", "特碼生肖");
		HKLHC_COMMISSION_TYPE.put("HK_SXWS_SX", "生肖尾數(生肖)");
		HKLHC_COMMISSION_TYPE.put("HK_SXWS_WS", "生肖尾數(尾數)");
		HKLHC_COMMISSION_TYPE.put("HK_BB", "半波");

		HKLHC_COMMISSION_TYPE.put("HK_LX", "六肖");
		HKLHC_COMMISSION_TYPE.put("HK_SXL", "生肖連");
		HKLHC_COMMISSION_TYPE.put("HK_WSL", "尾数連");
		HKLHC_COMMISSION_TYPE.put("HK_WBZ", "五不中");
		HKLHC_COMMISSION_TYPE.put("HK_GG", "過關");
		HKLHC_COMMISSION_TYPE.put("HK_ZHDS", "總和單雙");
		HKLHC_COMMISSION_TYPE.put("HK_ZHDX", "總和大小");

	}
	// add by Aaron 20121110 北京赛车
	public static final Map<String, String> BJSC_COMMISSION_TYPE = new LinkedHashMap<String, String>();
	static {

		BJSC_COMMISSION_TYPE.put("BJ_1-10_DS", "1-10單雙");
		BJSC_COMMISSION_TYPE.put("BJ_1-10_DX", "1-10大小");
		BJSC_COMMISSION_TYPE.put("BJ_1-5_LH", "1-5龍虎");
		BJSC_COMMISSION_TYPE.put("BJ_BALL_FIRST", "冠军");
		BJSC_COMMISSION_TYPE.put("BJ_BALL_SECOND", "亞軍");
		BJSC_COMMISSION_TYPE.put("BJ_BALL_THIRD", "第三名");
		BJSC_COMMISSION_TYPE.put("BJ_BALL_FORTH", "第四名");
		BJSC_COMMISSION_TYPE.put("BJ_BALL_FIFTH", "第五名");
		BJSC_COMMISSION_TYPE.put("BJ_BALL_SIXTH", "第六名");
		BJSC_COMMISSION_TYPE.put("BJ_BALL_SEVENTH", "第七名");
		BJSC_COMMISSION_TYPE.put("BJ_BALL_EIGHTH", "第八名");
		BJSC_COMMISSION_TYPE.put("BJ_BALL_NINTH", "第九名");
		BJSC_COMMISSION_TYPE.put("BJ_BALL_TENTH", "第十名");
		BJSC_COMMISSION_TYPE.put("BJ_DOUBLSIDE_DS", "冠、亞軍和 單雙");
		BJSC_COMMISSION_TYPE.put("BJ_DOUBLSIDE_DX", "冠、亞軍和 大小");
		BJSC_COMMISSION_TYPE.put("BJ_GROUP", "冠、亞軍組合");

	}

	public static final Map<String, String> K3_COMMISSION_TYPE = new LinkedHashMap<String, String>();
	static {

		K3_COMMISSION_TYPE.put("K3_SJ", "三軍");
		K3_COMMISSION_TYPE.put("K3_WS", "圍骰");
		K3_COMMISSION_TYPE.put("K3_QS", "全骰");
		K3_COMMISSION_TYPE.put("K3_DS", "點數");
		K3_COMMISSION_TYPE.put("K3_CP", "長牌");
		K3_COMMISSION_TYPE.put("K3_DP", "短牌");
		K3_COMMISSION_TYPE.put("K3_DX", "大小");
	}

	/**
	 * 2012-12-14 by Eric 以下赔率对应类型是用于在实时滚单的左栏里显示大类用的。
	 */
	public static final Map<String, String> GDKLSF_ODDS_TYPE = new LinkedHashMap<String, String>();
	static {
		GDKLSF_ODDS_TYPE.put("GDKLSF_BALL_FIRST", "第一球");
		GDKLSF_ODDS_TYPE.put("GDKLSF_BALL_SECOND", "第二球");
		GDKLSF_ODDS_TYPE.put("GDKLSF_BALL_THIRD", "第三球");
		GDKLSF_ODDS_TYPE.put("GDKLSF_BALL_FORTH", "第四球");
		GDKLSF_ODDS_TYPE.put("GDKLSF_BALL_FIFTH", "第五球");
		GDKLSF_ODDS_TYPE.put("GDKLSF_BALL_SIXTH", "第六球");
		GDKLSF_ODDS_TYPE.put("GDKLSF_BALL_SEVENTH", "第七球");
		GDKLSF_ODDS_TYPE.put("GDKLSF_BALL_EIGHTH", "第八球");
		GDKLSF_ODDS_TYPE.put("GDKLSF_1-8_DX", "第1-8大小");
		GDKLSF_ODDS_TYPE.put("GDKLSF_1-8_DS", "第1-8單雙");
		GDKLSF_ODDS_TYPE.put("GDKLSF_1-8_WDX", "第1-8尾數大小");
		GDKLSF_ODDS_TYPE.put("GDKLSF_1-8_HSDS", "第1-8合數單雙");
		GDKLSF_ODDS_TYPE.put("GDKLSF_1-8_FW", "第1-8方位");
		GDKLSF_ODDS_TYPE.put("GDKLSF_1-8_ZFB", "第1-8中發白");
		GDKLSF_ODDS_TYPE.put("GDKLSF_ZHDX", "總和大小");
		GDKLSF_ODDS_TYPE.put("GDKLSF_ZHDS", "總和單雙");
		GDKLSF_ODDS_TYPE.put("GDKLSF_ZHWSDX", "總和尾數大小");
		GDKLSF_ODDS_TYPE.put("GDKLSF_DOUBLESIDE_LH", "龍虎");
		GDKLSF_ODDS_TYPE.put("GDKLSF_RX2", "任選二");
		GDKLSF_ODDS_TYPE.put("GDKLSF_R2LZ", "任二連直");
		GDKLSF_ODDS_TYPE.put("GDKLSF_RX3", "任選三");
		GDKLSF_ODDS_TYPE.put("GDKLSF_R3LZ", "選三前組");
		GDKLSF_ODDS_TYPE.put("GDKLSF_RX4", "任選四");
		GDKLSF_ODDS_TYPE.put("GDKLSF_RX5", "任選五");

	}

	public static final Map<String, String> CQSSC_ODDS_TYPE = new LinkedHashMap<String, String>();
	static {

		CQSSC_ODDS_TYPE.put("CQSSC_BALL_FIRST", "第一球");
		CQSSC_ODDS_TYPE.put("CQSSC_BALL_SECOND", "第二球");
		CQSSC_ODDS_TYPE.put("CQSSC_BALL_THIRD", "第三球");
		CQSSC_ODDS_TYPE.put("CQSSC_BALL_FORTH", "第四球");
		CQSSC_ODDS_TYPE.put("CQSSC_BALL_FIFTH", "第五球");
		CQSSC_ODDS_TYPE.put("CQSSC_ZHDX", "總和大小");
		CQSSC_ODDS_TYPE.put("CQSSC_ZHDS", "總和單雙");
		CQSSC_ODDS_TYPE.put("CQSSC_1-5_DX", "第1-5大小");
		CQSSC_ODDS_TYPE.put("CQSSC_1-5_DS", "第1-5單雙");
		CQSSC_ODDS_TYPE.put("CQSSC_DOUBLESIDE_LH", "龍虎和");
		CQSSC_ODDS_TYPE.put("CQSSC_DOUBLESIDE_HE", "龍虎和");
		CQSSC_ODDS_TYPE.put("CQSSC_BZ_FRONT", "前三");
		CQSSC_ODDS_TYPE.put("CQSSC_SZ_FRONT", "前三");
		CQSSC_ODDS_TYPE.put("CQSSC_DZ_FRONT", "前三");
		CQSSC_ODDS_TYPE.put("CQSSC_BS_FRONT", "前三");
		CQSSC_ODDS_TYPE.put("CQSSC_BZ_MID", "中三");
		CQSSC_ODDS_TYPE.put("CQSSC_SZ_MID", "中三");
		CQSSC_ODDS_TYPE.put("CQSSC_DZ_MID", "中三");
		CQSSC_ODDS_TYPE.put("CQSSC_BS_MID", "中三");
		CQSSC_ODDS_TYPE.put("CQSSC_BZ_LAST", "後三");
		CQSSC_ODDS_TYPE.put("CQSSC_SZ_LAST", "後三");
		CQSSC_ODDS_TYPE.put("CQSSC_DZ_LAST", "後三");
		CQSSC_ODDS_TYPE.put("CQSSC_BS_LAST", "後三");

	}

	public static final Map<String, String> BJSC_ODDS_TYPE = new LinkedHashMap<String, String>();
	static {

		BJSC_ODDS_TYPE.put("BJ_1-10_DS", "1-10單雙");
		BJSC_ODDS_TYPE.put("BJ_1-10_DX", "1-10大小");
		BJSC_ODDS_TYPE.put("BJ_1-5_LH", "1-5龍虎");
		BJSC_ODDS_TYPE.put("BJ_BALL_FIRST", "冠军");
		BJSC_ODDS_TYPE.put("BJ_BALL_SECOND", "亞軍");
		BJSC_ODDS_TYPE.put("BJ_BALL_THIRD", "第三名");
		BJSC_ODDS_TYPE.put("BJ_BALL_FORTH", "第四名");
		BJSC_ODDS_TYPE.put("BJ_BALL_FIFTH", "第五名");
		BJSC_ODDS_TYPE.put("BJ_BALL_SIXTH", "第六名");
		BJSC_ODDS_TYPE.put("BJ_BALL_SEVENTH", "第七名");
		BJSC_ODDS_TYPE.put("BJ_BALL_EIGHTH", "第八名");
		BJSC_ODDS_TYPE.put("BJ_BALL_NINTH", "第九名");
		BJSC_ODDS_TYPE.put("BJ_BALL_TENTH", "第十名");
		BJSC_ODDS_TYPE.put("BJ_DOUBLSIDE_DAN", "冠、亞軍和 單雙");
		BJSC_ODDS_TYPE.put("BJ_DOUBLSIDE_S", "冠、亞軍和 單雙");
		BJSC_ODDS_TYPE.put("BJ_DOUBLESIDE_DA", "冠、亞軍和 大小");
		BJSC_ODDS_TYPE.put("BJ_DOUBLSIDE_X", "冠、亞軍和 大小");
		BJSC_ODDS_TYPE.put("BJ_GROUP_ONE", "冠、亞軍組合");
		BJSC_ODDS_TYPE.put("BJ_GROUP_TWO", "冠、亞軍組合");
		BJSC_ODDS_TYPE.put("BJ_GROUP_THREE", "冠、亞軍組合");
		BJSC_ODDS_TYPE.put("BJ_GROUP_FOUR", "冠、亞軍組合");
		BJSC_ODDS_TYPE.put("BJ_GROUP_FIVE", "冠、亞軍組合");
	}

	public static final Map<String, String> K3_ODDS_TYPE = new LinkedHashMap<String, String>();
	static {

		K3_ODDS_TYPE.put("K3_SJ", "三軍");
		K3_ODDS_TYPE.put("K3_WS", "圍骰");
		K3_ODDS_TYPE.put("K3_QS", "全骰");
		K3_ODDS_TYPE.put("K3_DS_4", "點數");
		K3_ODDS_TYPE.put("K3_DS_5", "點數");
		K3_ODDS_TYPE.put("K3_DS_6", "點數");
		K3_ODDS_TYPE.put("K3_DS_7", "點數");
		K3_ODDS_TYPE.put("K3_DS_8", "點數");
		K3_ODDS_TYPE.put("K3_DS_9", "點數");
		K3_ODDS_TYPE.put("K3_CP", "長牌");
		K3_ODDS_TYPE.put("K3_DP", "短牌");
		K3_ODDS_TYPE.put("K3_DX", "大小");
	}

	public static final Map<String, String> BJSC_ODDSLOG_TYPE = new LinkedHashMap<String, String>();
	static {

		BJSC_ODDSLOG_TYPE.put("BJ_1-10_DS", "1-10單雙");
		BJSC_ODDSLOG_TYPE.put("BJ_1-10_DX", "1-10大小");
		BJSC_ODDSLOG_TYPE.put("BJ_1-5_LH", "1-5龍虎");
		BJSC_ODDSLOG_TYPE.put("BJ_BALL_FIRST", "冠军");
		BJSC_ODDSLOG_TYPE.put("BJ_BALL_SECOND", "亞軍");
		BJSC_ODDSLOG_TYPE.put("BALL_THIRD", "第三名");
		BJSC_ODDSLOG_TYPE.put("BALL_FORTH", "第四名");
		BJSC_ODDSLOG_TYPE.put("BALL_FIFTH", "第五名");
		BJSC_ODDSLOG_TYPE.put("BALL_SIXTH", "第六名");
		BJSC_ODDSLOG_TYPE.put("BALL_SEVENTH", "第七名");
		BJSC_ODDSLOG_TYPE.put("BALL_EIGHTH", "第八名");
		BJSC_ODDSLOG_TYPE.put("BALL_NINTH", "第九名");
		BJSC_ODDSLOG_TYPE.put("BALL_TENTH", "第十名");
		BJSC_ODDSLOG_TYPE.put("BJ_DOUBLESIDE_DS", "冠、亞軍和 單雙");
		BJSC_ODDSLOG_TYPE.put("BJ_DOUBLESIDE_DX", "冠、亞軍和 大小");
		BJSC_ODDSLOG_TYPE.put("BJ_GROUP", "冠、亞軍組合");

	}
	public static final Map<String, String> GDKLSF_ODDSLOG_TYPE = new LinkedHashMap<String, String>();
	static {
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_BALL_FIRST", "第一球");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_BALL_SECOND", "第二球");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_BALL_THIRD", "第三球");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_BALL_FORTH", "第四球");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_BALL_FIFTH", "第五球");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_BALL_SIXTH", "第六球");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_BALL_SEVENTH", "第七球");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_BALL_EIGHTH", "第八球");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_1-8_DX", "1-8大小");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_1-8_DS", "1-8單雙");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_1-8_WDX", "1-8尾數大小");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_1-8_HSDS", "1-8合數單雙");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_1-8_FW", "1-8方位");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_1-8_ZFB", "1-8中發白");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_ZHDX", "總和大小");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_ZHDS", "總和單雙");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_ZHWSDX", "總和尾數大小");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_DOUBLESIDE_LH", "龍虎");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_RX2", "任選二");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_R2LZ", "任二連直");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_RX3", "任選三");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_R3LZ", "選三前組");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_RX4", "任選四");
		GDKLSF_ODDSLOG_TYPE.put("GDKLSF_RX5", "任選五");

	}
	public static final Map<String, String> NC_ODDSLOG_TYPE = new LinkedHashMap<String, String>();
	static {
		NC_ODDSLOG_TYPE.put("NC_BALL_FIRST", "第一球");
		NC_ODDSLOG_TYPE.put("NC_BALL_SECOND", "第二球");
		NC_ODDSLOG_TYPE.put("NC_BALL_THIRD", "第三球");
		NC_ODDSLOG_TYPE.put("NC_BALL_FORTH", "第四球");
		NC_ODDSLOG_TYPE.put("NC_BALL_FIFTH", "第五球");
		NC_ODDSLOG_TYPE.put("NC_BALL_SIXTH", "第六球");
		NC_ODDSLOG_TYPE.put("NC_BALL_SEVENTH", "第七球");
		NC_ODDSLOG_TYPE.put("NC_BALL_EIGHTH", "第八球");
		NC_ODDSLOG_TYPE.put("NC_1-8_DX", "1-8大小");
		NC_ODDSLOG_TYPE.put("NC_1-8_DS", "1-8單雙");
		NC_ODDSLOG_TYPE.put("NC_1-8_WDX", "1-8尾數大小");
		NC_ODDSLOG_TYPE.put("NC_1-8_HSDS", "1-8合數單雙");
		NC_ODDSLOG_TYPE.put("NC_1-8_FW", "1-8方位");
		NC_ODDSLOG_TYPE.put("NC_1-8_ZFB", "1-8中發白");
		NC_ODDSLOG_TYPE.put("NC_ZHDX", "總和大小");
		NC_ODDSLOG_TYPE.put("NC_ZHDS", "總和單雙");
		NC_ODDSLOG_TYPE.put("NC_ZHWSDX", "總和尾數大小");
		NC_ODDSLOG_TYPE.put("NC_DOUBLESIDE_LH", "龍虎");
		NC_ODDSLOG_TYPE.put("NC_RX2", "任選二");
		NC_ODDSLOG_TYPE.put("NC_R2LZ", "任二連直");
		NC_ODDSLOG_TYPE.put("NC_RX3", "任選三");
		NC_ODDSLOG_TYPE.put("NC_R3LZ", "選三前組");
		NC_ODDSLOG_TYPE.put("NC_RX4", "任選四");
		NC_ODDSLOG_TYPE.put("NC_RX5", "任選五");

	}
	public static final Map<String, String> CQSSC_ODDSLOG_TYPE = new LinkedHashMap<String, String>();
	static {

		CQSSC_ODDSLOG_TYPE.put("CQSSC_BALL_FIRST", "第一球");
		CQSSC_ODDSLOG_TYPE.put("CQSSC_BALL_SECOND", "第二球");
		CQSSC_ODDSLOG_TYPE.put("CQSSC_BALL_THIRD", "第三球");
		CQSSC_ODDSLOG_TYPE.put("CQSSC_BALL_FORTH", "第四球");
		CQSSC_ODDSLOG_TYPE.put("CQSSC_BALL_FIFTH", "第五球");
		CQSSC_ODDSLOG_TYPE.put("CQSSC_ZHDX", "總合大小");
		CQSSC_ODDSLOG_TYPE.put("CQSSC_ZHDS", "總合單雙");
		CQSSC_ODDSLOG_TYPE.put("CQSSC_1-5_DX", "1-5大小");
		CQSSC_ODDSLOG_TYPE.put("CQSSC_1-5_DS", "1-5單雙");
		CQSSC_ODDSLOG_TYPE.put("CQSSC_DOUBLESIDE_%H%", "龍虎和");
		CQSSC_ODDSLOG_TYPE.put("CQSSC_%_FRONT", "前三");
		CQSSC_ODDSLOG_TYPE.put("CQSSC_%_MID", "中三");
		CQSSC_ODDSLOG_TYPE.put("CQSSC_%_LAST", "後三");

	}

	public static final Map<String, String> K3_ODDSLOG_TYPE = new LinkedHashMap<String, String>();
	static {

		K3_ODDSLOG_TYPE.put("K3_SJ", "三軍");
		K3_ODDSLOG_TYPE.put("K3_WS", "圍骰");
		K3_ODDSLOG_TYPE.put("K3_QS", "全骰");
		K3_ODDSLOG_TYPE.put("K3_DS_4", "點數");
		K3_ODDSLOG_TYPE.put("K3_DS_5", "點數");
		K3_ODDSLOG_TYPE.put("K3_DS_6", "點數");
		K3_ODDSLOG_TYPE.put("K3_DS_7", "點數");
		K3_ODDSLOG_TYPE.put("K3_DS_8", "點數");
		K3_ODDSLOG_TYPE.put("K3_DS_9", "點數");
		K3_ODDSLOG_TYPE.put("K3_CP", "長牌");
		K3_ODDSLOG_TYPE.put("K3_DP", "短牌");
		K3_ODDSLOG_TYPE.put("K3_DX", "大小");
	}

	public static final Map<String, String> BJSC_REPLENISHAUTOSETLOG_TYPE = new LinkedHashMap<String, String>();
	static {

		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_1T10DS", "1-10單雙");
		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_1T10DX", "1-10大小");
		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_1T5LH", "1-5龍虎");
		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_BALL_FIRST", "冠军");
		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_BALL_SECOND", "亞軍");
		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_BALL_THIRD", "第三名");
		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_BALL_FORTH", "第四名");
		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_BALL_FIFTH", "第五名");
		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_BALL_SIXTH", "第六名");
		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_BALL_SEVENTH", "第七名");
		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_BALL_EIGHTH", "第八名");
		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_BALL_NINTH", "第九名");
		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_BALL_TENTH", "第十名");
		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_GROUP_DS", "冠、亞軍和 單雙");
		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_GROUP_DX", "冠、亞軍和 大小");
		BJSC_REPLENISHAUTOSETLOG_TYPE.put("BJ_GROUP", "冠、亞軍組合");

	}
	public static final Map<String, String> GDKLSF_REPLENISHAUTOSETLOG_TYPE = new LinkedHashMap<String, String>();
	static {
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_BALL_FIRST", "第一球");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_BALL_SECOND", "第二球");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_BALL_THIRD", "第三球");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_BALL_FORTH", "第四球");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_BALL_FIFTH", "第五球");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_BALL_SIXTH", "第六球");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_BALL_SEVENTH", "第七球");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_BALL_EIGHTH", "第八球");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_1T8_DX", "1-8大小");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_1T8_DS", "1-8單雙");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_1T8_WDX", "1-8尾數大小");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_1T8_HSDS", "1-8合數單雙");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_1T8_FW", "1-8方位");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_1T8_ZFB", "1-8中發白");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_ZHDX", "總和大小");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_ZHDS", "總和單雙");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_ZHWSDX", "總和尾數大小");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_DOUBLESIDE_LH", "龍虎");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_STRAIGHTTHROUGH_RX2", "任選二");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_STRAIGHTTHROUGH_R2LZ", "任二連組");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_STRAIGHTTHROUGH_R2LH", "任二連直");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_STRAIGHTTHROUGH_RX3", "任選三");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_STRAIGHTTHROUGH_R3LZ", "選三前組");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_STRAIGHTTHROUGH_R3LH", "選三前直");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_STRAIGHTTHROUGH_RX4", "任選四");
		GDKLSF_REPLENISHAUTOSETLOG_TYPE.put("GDKLSF_STRAIGHTTHROUGH_RX5", "任選五");

	}
	public static final Map<String, String> CQSSC_REPLENISHAUTOSETLOG_TYPE = new LinkedHashMap<String, String>();
	static {

		CQSSC_REPLENISHAUTOSETLOG_TYPE.put("CQSSC_BALL_FIRST", "第一球");
		CQSSC_REPLENISHAUTOSETLOG_TYPE.put("CQSSC_BALL_SECOND", "第二球");
		CQSSC_REPLENISHAUTOSETLOG_TYPE.put("CQSSC_BALL_THIRD", "第三球");
		CQSSC_REPLENISHAUTOSETLOG_TYPE.put("CQSSC_BALL_FORTH", "第四球");
		CQSSC_REPLENISHAUTOSETLOG_TYPE.put("CQSSC_BALL_FIFTH", "第五球");
		CQSSC_REPLENISHAUTOSETLOG_TYPE.put("CQSSC_ZHDX", "總和大小");
		CQSSC_REPLENISHAUTOSETLOG_TYPE.put("CQSSC_ZHDS", "總和單雙");
		CQSSC_REPLENISHAUTOSETLOG_TYPE.put("CQSSC_1T5_DX", "1-5大小");
		CQSSC_REPLENISHAUTOSETLOG_TYPE.put("CQSSC_1T5_DS", "1-5單雙");
		CQSSC_REPLENISHAUTOSETLOG_TYPE.put("CQSSC_LHH", "龍虎和");
		CQSSC_REPLENISHAUTOSETLOG_TYPE.put("CQSSC_THREE_FRONT", "前三");
		CQSSC_REPLENISHAUTOSETLOG_TYPE.put("CQSSC_THREE_MID", "中三");
		CQSSC_REPLENISHAUTOSETLOG_TYPE.put("CQSSC_THREE_LAST", "後三");

	}
	public static final Map<String, String> NC_REPLENISHAUTOSETLOG_TYPE = new LinkedHashMap<String, String>();
	static {

		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_BALL_FIRST", "第一球");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_BALL_SECOND", "第二球");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_BALL_THIRD", "第三球");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_BALL_FORTH", "第四球");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_BALL_FIFTH", "第五球");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_BALL_SIXTH", "第六球");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_BALL_SEVENTH", "第七球");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_BALL_EIGHTH", "第八球");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_1T8_DX", "1-8大小");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_1T8_DS", "1-8單雙");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_1T8_WDX", "1-8尾數大小");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_1T8_HSDS", "1-8合數單雙");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_1T8_FW", "1-8方位");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_1T8_ZFB", "1-8中發白");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_ZHDX", "總和大小");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_ZHDS", "總和單雙");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_ZHWSDX", "總和尾數大小");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_DOUBLESIDE_LH", "龍虎");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_STRAIGHTTHROUGH_RX2", "任選二");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_STRAIGHTTHROUGH_R2LZ", "任二連組");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_STRAIGHTTHROUGH_R2LH", "任二連直");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_STRAIGHTTHROUGH_RX3", "任選三");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_STRAIGHTTHROUGH_R3LZ", "選三前組");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_STRAIGHTTHROUGH_R3LH", "選三前直");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_STRAIGHTTHROUGH_RX4", "任選四");
		NC_REPLENISHAUTOSETLOG_TYPE.put("NC_STRAIGHTTHROUGH_RX5", "任選五");

	}

	public static final Map<String, String> K3_REPLENISHAUTOSETLOG_TYPE = new LinkedHashMap<String, String>();
	static {

		K3_REPLENISHAUTOSETLOG_TYPE.put("K3_SJ", "三軍");
		K3_REPLENISHAUTOSETLOG_TYPE.put("K3_WS", "圍骰");
		K3_REPLENISHAUTOSETLOG_TYPE.put("K3_QS", "全骰");
		K3_REPLENISHAUTOSETLOG_TYPE.put("K3_DS", "點數");
		K3_REPLENISHAUTOSETLOG_TYPE.put("K3_CP", "長牌");
		K3_REPLENISHAUTOSETLOG_TYPE.put("K3_DP", "短牌");
		K3_REPLENISHAUTOSETLOG_TYPE.put("K3_DX", "大小");
	}

	// **************补货界面的总额统计START*只有广东和北京才有总额统计*
	public static final Map<String, String> GDKLSF_RIGHT_TYPE = new LinkedHashMap<String, String>();
	static {
		GDKLSF_RIGHT_TYPE.put("BALL_FIRST", "第一球");
		GDKLSF_RIGHT_TYPE.put("BALL_SECOND", "第二球");
		GDKLSF_RIGHT_TYPE.put("BALL_THIRD", "第三球");
		GDKLSF_RIGHT_TYPE.put("BALL_FORTH", "第四球");
		GDKLSF_RIGHT_TYPE.put("BALL_FIFTH", "第五球");
		GDKLSF_RIGHT_TYPE.put("BALL_SIXTH", "第六球");
		GDKLSF_RIGHT_TYPE.put("BALL_SEVENTH", "第七球");
		GDKLSF_RIGHT_TYPE.put("BALL_EIGHTH", "第八球");

		GDKLSF_RIGHT_TYPE.put("GD_ZHDX_BALL", "總和大小");
		GDKLSF_RIGHT_TYPE.put("GD_ZHDS_BALL", "總和單雙");
		GDKLSF_RIGHT_TYPE.put("GD_ZHWSDX_BALL", "總尾大小");
		GDKLSF_RIGHT_TYPE.put("GD_LH_BALL", "龍虎");

		GDKLSF_RIGHT_TYPE.put("GDKLSF_STRAIGHTTHROUGH_RX2", "任選二");
		GDKLSF_RIGHT_TYPE.put("GDKLSF_STRAIGHTTHROUGH_R2LZHI", "任二連直");
		GDKLSF_RIGHT_TYPE.put("GDKLSF_STRAIGHTTHROUGH_R2LZ", "任二連組");
		GDKLSF_RIGHT_TYPE.put("GDKLSF_STRAIGHTTHROUGH_RX3", "任選三");
		GDKLSF_RIGHT_TYPE.put("GDKLSF_STRAIGHTTHROUGH_R3LZHI", "選三前直");
		GDKLSF_RIGHT_TYPE.put("GDKLSF_STRAIGHTTHROUGH_R3LZ", "選三前組");
		GDKLSF_RIGHT_TYPE.put("GDKLSF_STRAIGHTTHROUGH_RX4", "任選四");
		GDKLSF_RIGHT_TYPE.put("GDKLSF_STRAIGHTTHROUGH_RX5", "任選五");

	}

	public static final Map<String, String> NC_RIGHT_TYPE = new LinkedHashMap<String, String>();
	static {
		NC_RIGHT_TYPE.put("BALL_FIRST", "第一球");
		NC_RIGHT_TYPE.put("BALL_SECOND", "第二球");
		NC_RIGHT_TYPE.put("BALL_THIRD", "第三球");
		NC_RIGHT_TYPE.put("BALL_FORTH", "第四球");
		NC_RIGHT_TYPE.put("BALL_FIFTH", "第五球");
		NC_RIGHT_TYPE.put("BALL_SIXTH", "第六球");
		NC_RIGHT_TYPE.put("BALL_SEVENTH", "第七球");
		NC_RIGHT_TYPE.put("BALL_EIGHTH", "第八球");

		NC_RIGHT_TYPE.put("NC_ZHDX_BALL", "總和大小");
		NC_RIGHT_TYPE.put("NC_ZHDS_BALL", "總和單雙");
		NC_RIGHT_TYPE.put("NC_ZHWSDX_BALL", "總尾大小");
		NC_RIGHT_TYPE.put("NC_LH_BALL", "龍虎");

		NC_RIGHT_TYPE.put("NC_STRAIGHTTHROUGH_RX2", "任選二");
		NC_RIGHT_TYPE.put("NC_STRAIGHTTHROUGH_R2LZHI", "任二連直");
		NC_RIGHT_TYPE.put("NC_STRAIGHTTHROUGH_R2LZ", "任二連組");
		NC_RIGHT_TYPE.put("NC_STRAIGHTTHROUGH_RX3", "任選三");
		NC_RIGHT_TYPE.put("NC_STRAIGHTTHROUGH_R3LZHI", "選三前直");
		NC_RIGHT_TYPE.put("NC_STRAIGHTTHROUGH_R3LZ", "選三前組");
		NC_RIGHT_TYPE.put("NC_STRAIGHTTHROUGH_RX4", "任選四");
		NC_RIGHT_TYPE.put("NC_STRAIGHTTHROUGH_RX5", "任選五");

	}

	public static final Map<String, String> BJSC_RIGHT_TYPE = new LinkedHashMap<String, String>();
	static {

		BJSC_RIGHT_TYPE.put("BJ_GROUP", "冠亞軍和");
		BJSC_RIGHT_TYPE.put("BJ_DOUBLSIDE_DS", "冠亞單雙");
		BJSC_RIGHT_TYPE.put("BJ_DOUBLSIDE_DX", "冠亞大小");
		BJSC_RIGHT_TYPE.put("BALL_FIRST", "冠军");
		BJSC_RIGHT_TYPE.put("BALL_SECOND", "亞軍");
		BJSC_RIGHT_TYPE.put("BALL_THIRD", "第三名");
		BJSC_RIGHT_TYPE.put("BALL_FORTH", "第四名");
		BJSC_RIGHT_TYPE.put("BALL_FIFTH", "第五名");
		BJSC_RIGHT_TYPE.put("BALL_SIXTH", "第六名");
		BJSC_RIGHT_TYPE.put("BALL_SEVENTH", "第七名");
		BJSC_RIGHT_TYPE.put("BALL_EIGHTH", "第八名");
		BJSC_RIGHT_TYPE.put("BALL_NINTH", "第九名");
		BJSC_RIGHT_TYPE.put("BALL_TENTH", "第十名");

	}
	// ***********补货界面的总额统计END**
	// add by peter for change log
	public static final String CHANGE_LOG_CHANGE_TYPE_COMMISSION_UPDATE = "COMMISSION_UPDATE";
	public static final String CHANGE_LOG_CHANGE_TYPE_USERINFO_UPDATE = "USERINFO_UPDATE";
	public static final String CHANGE_LOG_CHANGE_TYPE_REPLENISH_AUTO_UPDATE = "REPLENISH_AUTO_UPDATE";

	public static final String CHANGE_LOG_CHANGE_SUB_TYPE_COMMISSIONA = "COMMISSIONA";
	public static final String CHANGE_LOG_CHANGE_SUB_TYPE_COMMISSIONB = "COMMISSIONB";
	public static final String CHANGE_LOG_CHANGE_SUB_TYPE_COMMISSIONC = "COMMISSIONC";
	public static final String CHANGE_LOG_CHANGE_SUB_TYPE_BETTING_QUOTAS = "BETTING_QUOTAS";
	public static final String CHANGE_LOG_CHANGE_SUB_TYPE_ITEM_QUOTAS = "ITEM_QUOTAS";
	public static final String[] NC_TABLE_LIST = new String[] { NC_TABLE_NAME };

	public static final String GDKLSF_CHECK_TABLE_NAME = "TB_GDKLSF_CHECK";
	public static final String CQSSC_CHECK_TABLE_NAME = "TB_CQSSC_CHECK";
	public static final String BJSC_CHECK_TABLE_NAME = "TB_BJSC_CHECK";
	public static final String K3_CHECK_TABLE_NAME = "TB_JSSB_CHECK";
	public static final String NC_CHECK_TABLE_NAME = "TB_NC_CHECK";

	/** cookie中标签，会员中心用户ID */
	public static final String COOKIE_UID = "SESSION_UID";
	/** cookie中标签，用户名/昵称，页面显示用 */
	public static final String COOKIE_PATH = "SESSION_PATH";
	/** cookie中的动态cookie，登录/注册时随机生成 */
	public static final String COOKIE_CK = "SESSION_CK";
	/** cookie中标签，令牌 */
	public static final String COOKIE_TOKEN = "SESSION_TOKEN";
	/** cookie中标签，商铺号 */
	public static final String COOKIE_SHOPCODE = "SESSION_SHOPCODE";

}
