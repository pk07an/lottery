package com.npc.lottery.common;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.npc.lottery.common.action.BaseAction;
import com.npc.lottery.sysmge.entity.ParamValue;
import com.npc.lottery.sysmge.logic.interf.IParamLogic;

/**
 * 字典表中存放的业务相关常量
 * 
 * 注意：
 *      1、此类中定义的常量数据都是从字典表中读取，在系统启动时初始化
 *      2、如果在运行期间需要改变的数据（包括手工修改数据库数据记录），则不能放置在此处
 *        （特殊情况下可考虑变量不要设置为 final 类型从而在数据变化时，由程序主动修改，但不推荐这样做）
 *      3、手工修改了数据库数据记录，需要重新启动WEB应用才能重新读取数据
 * 
 * TODO 待完善
 *
 * @author none
 *
 */
public class ConstantBusiness extends BaseAction {

	private static Logger log = Logger.getLogger(ConstantBusiness.class);

	private static IParamLogic paramLogic = null;

	public static void setParamLogic() {
		ApplicationContext ac = WebApplicationContextUtils
				.getWebApplicationContext(ServletActionContext
						.getServletContext());
		paramLogic = (IParamLogic) ac.getBean("paramLogic");
	}

	static {
		setParamLogic();
	}

	/**
	 * 根据参数类别编码，查询对应的参数值
	 * 
	 * @param code  待查询参数类别编码，对应数据表TB_FRAME_PARAM中的CODE字段
	 * @return
	 */
	public static ArrayList<ParamValue> getParamValueByCode(String code) {

		//查询数据
		ArrayList<ParamValue> resultList = (ArrayList<ParamValue>) paramLogic
				.findParamValueByCode(code);

		return resultList;
	}

	//TODO 下面常量数据需要修改为从字典表中获取
	public static final long FUNCTION_ROOT_ID;//功能表中根节点所对应ID
	static {
		//TODO 修改为从字典表中读取数据
		FUNCTION_ROOT_ID = 1;
		log.error("初始化业务常量：【功能表中根节点所对应ID】= 1");
	}

	public static final long ORG_ROOT_DATA_ID;//机构数据表记录中根节点
	static {
		//TODO 修改为从字典表中读取数据
		ORG_ROOT_DATA_ID = 360000100;
		log.error("初始化业务常量：【机构数据表记录中根节点】= 360000100");
	}

	public static final long RESOURCE_ROOT_ID;//资源表中根节点所对应ID
	static {
		//TODO 修改为从字典表中读取数据
		RESOURCE_ROOT_ID = 1;
		log.error("初始化业务常量：【资源表中根节点所对应ID】= 1");
	}

	public static final long AUTHORIZ_AREA_ROOT_ID;//权限域表中根节点所对应ID
	static {
		//TODO 修改为从字典表中读取数据
		AUTHORIZ_AREA_ROOT_ID = 1;
		log.error("初始化业务常量：【权限域表中根节点所对应ID】= 1");
	}

}
