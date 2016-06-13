package com.npc.lottery.util;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.npc.lottery.common.Constant;

/**
 * 工具类
 * 
 * @author none
 */
public class Tool {

	public static final boolean isDebug = true; //是否是调试信息
    
	//判断该字符串是否为整型
	public static boolean isInteger(String value) {
		  try {
		      Integer.parseInt(value);
		      return true;
		  } catch (NumberFormatException e) {
		      return false;
		  }
		 }
	/**
	 * 打印调试信息
	 * 
	 * @param info
	 */
	public static void printInfo(Object info) {
		if (isDebug) {
			System.out.println(info);
		}
	}

	/**
	 * 打印异常的堆栈信息
	 */
	public static void printExceptionStack(Exception ex) {
		if (isDebug) {
			try {
				ex.printStackTrace();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 从 request 中读取参数的值
	 * 
	 * @param request
	 * @param paraName
	 *            参数名称
	 * @param defaultStr
	 *            当参数不存在时返回的默认值
	 * @return
	 */
	public static String getParameter(HttpServletRequest request,
			String paraName, String defaultStr) {
		String result = defaultStr;

		try {
			result = request.getParameter(paraName);

			if (null == result) {
				result = defaultStr;
			}

		} catch (Exception ex) {
			printInfo(ex);
		}

		return result;
	}

	/**
	 * 从 request 中读取参数的值
	 * 
	 * @param request
	 * @param paraName
	 *            参数名称
	 * @param defaultValue
	 *            当参数为null时的返回值
	 * @return
	 */
	public static Object getAttributes(HttpServletRequest request,
			String paraName, Object defaultValue) {

		Object result = defaultValue;

		try {
			result = request.getAttribute(paraName);

			if (null == result) {
				result = defaultValue;
			}

		} catch (Exception ex) {
			printInfo(ex);
		}

		return result;
	}

	/**
	 * 截取指定字符长度 TODO 加入中文判断逻辑
	 * 
	 * @param originStr
	 *            原字符串
	 * @param num
	 *            截取的长度
	 * @param suffixFlag
	 *            截取长度后是否加后缀
	 * @return
	 */
	public static String subString(String originStr, int num, boolean suffixFlag) {
		String result = originStr;

		if (null == originStr || num >= originStr.length()) {
			return result;
		}

		result = originStr.substring(0, num);

		if (suffixFlag) {
			result += " ...";
		}

		return result;
	}

	/**
	 * 将字符串（日期格式，如2005-10-11）解析成 Timestamp
	 * 
	 * @param time
	 * @return
	 */
	public static Timestamp parseString2Timestamp(String dateStr) {
		Timestamp times = null;

		try {
			Date date = Date.valueOf(dateStr);
			times = new Timestamp(date.getTime());
		} catch (Exception ex) {

		}

		return times;
	}

	/**
	 * 读取工程的根目录（web 应用的 Context 根目录）
	 * 
	 * @return
	 */
	public static String getBasePath() {

		String path = "";
		try {
			String className = Tool.class.getName();//取得类名
			//类名与context的class文件放置的根目录的距离
			int levelNum = (className.split("\\.")).length - 1;

			//读取context的class文件放置的根目录
			URL thisUrl = Tool.class.getResource("");
			path = thisUrl.toString();
			//去掉协议名称
			path = path.substring(path.indexOf("/") + 1);

			//回退到context的class文件放置的根目录
			if (levelNum > 0) {
				for (int i = 0; i < levelNum; i++) {
					path = path + "../";
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		path = path + "../../";

		return path;
	}

	/**
	 * 判断字符串originStr是否包含另一个字符串testStr（不区分大小写）
	 * 
	 * @param originStr 包含的字符串（“大”字符串）
	 * @param testStr   被包含的字符串（“小”字符串）
	 * @return
	 */
	public static int indexOfIgnoreCase(String originStr, String testStr) {

		originStr = originStr.toUpperCase(new Locale("UTF-8"));
		testStr = testStr.toUpperCase(new Locale("UTF-8"));

		return originStr.indexOf(testStr);

	}

	/**
	 * 将显示为元格式的转换为分格式（##,###.00 -> #######）
	 * 
	 * @param disStr
	 * @return
	 */
	public static String parseMoneyDis2Store(String disStr) {

		String store = null;

		if (null == disStr || 0 == disStr.trim().length()) {
			return null;
		}

		try {
			//过滤掉 ,
			disStr = disStr.replaceAll(",", "");
			//定位小数点
			int dotIndex = disStr.indexOf(".");
			if (-1 != dotIndex) {

				int length = disStr.length();

				//将不满两位小数的补全
				if (length == dotIndex + 1) {
					disStr += "00";
				} else if (length == dotIndex + 2) {
					disStr += "0";
				}
				store = disStr.substring(0, dotIndex + 3);

				store = store.replaceAll("\\.", "");
			} else {
				store = disStr + "00";
			}
		} catch (Exception ex) {
			//ex.printStackTrace();
		}

		return store;
	}

	/**
	 * 将tagLib产生的翻页url转换成使用servlet形式的url
	 *  
	 */
	public static String toServletStr(String urlStr, String servletStr) {

		String resultStr = urlStr;
		int pos = urlStr.indexOf("?");
		if (pos >= 0) {
			//TODO 此步骤可能不需要，测试删除
			resultStr = servletStr + urlStr.substring(pos);
		}

		//TODO 暂时删除非法字符，不安全，查看标签的源码，考虑如何处理特殊字符
		resultStr = resultStr.replace("!!", "");

		try {
			//判断分页提交标志
			int index = resultStr.indexOf(Constant.PAGETAG_EXTEND_PARAM);
			if (-1 != index) {
				//如果是分页，转换数据为UTF-8
				//需要考虑应用服务器中的配置情况，比如tomcat中，如果设置了 URIEncoding="UTF-8" ，此处则无需转码
				//resultStr = new String(resultStr.getBytes("ISO-8859-1"), "UTF-8");
			} else {
				//添加分页提交标志
				resultStr += "&" + Constant.PAGETAG_EXTEND_PARAM + "=true";
			}
		} catch (Exception ex) {

		}

		return resultStr;
	}

	public static String arrayToString(String[] arrayStr) {
		//将数组状态的业务信息转换成字符串，以便组装SQL
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arrayStr.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append("'");
			sb.append(arrayStr[i]);
			sb.append("'");
		}

		return sb.toString();
	}

	/***
	 * 求出两个日期之间的毫秒差
	 * @param begin
	 * @param end
	 * @author 朱冬志
	 * @return
	 */
	public static long diffDate(java.util.Date begin, java.util.Date end) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(begin);
		long timeBegin = calendar.getTimeInMillis();

		calendar.setTime(end);
		long timeEnd = calendar.getTimeInMillis();
		return timeEnd - timeBegin;
	}
	
	/***
	 * 求出两个日期之间的毫秒差
	 * @param str 需要进行排列组合的字符串
	 * @param cnt  每组的个数
	 * @return
	 */
	public static List<String> assembly(List<String> str,int n)
	{
		ArrayList<String> ret = new ArrayList<String>();	
       
        if(n>str.size())
        	return ret;
        
        ArrayList<String> arr = new ArrayList<String>();//模拟队列  
        for (int k = 0; k < str.size(); k++) {  
            //取出首节点  
            String s = str.get(k) + "";  
            //入队  
            arr.add(s);  
            //开始循环  
            while (arr.size() > 0) {  
                //出队  
                String ss = arr.get(0);  
                //判断是否符合要求  
                if (StringUtils.split(ss,",").length == n) {
                	ret.add(ss);
                }
                String[] sArr=ss.split(",");
                //追加字符。  
                for (int i = str.indexOf(sArr[sArr.length - 1])+1; i < str.size(); i++) 
                { 
                	if (sArr.length < n) {  
                        String m = ss +","+ str.get(i);  
                        arr.add(m);  
                    }                 
                }  
                //去除取出来的节点。  
                arr.remove(0);  
            }  
        }  
        //System.out.println("共有" + count + "种组合");
        return ret;
    }  
		

	/**
	 * 测试主函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		
	}
}
