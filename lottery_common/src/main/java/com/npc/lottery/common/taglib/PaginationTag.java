/**
 * $Id: AdminUserAction.java 150 2010-06-26 05:18:50Z feigme@gmail.com $
 */
package com.npc.lottery.common.taglib;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.npc.lottery.util.Page;



public class PaginationTag extends TagSupport {

	private static final long serialVersionUID = 2105673440806872545L;
	
	private static final Logger log = LoggerFactory
			.getLogger(PaginationTag.class);

	private String url;

	private String param;
	
	private String recordType;
	
	private String css;

	
	@SuppressWarnings("rawtypes")
	@Override
	public int doStartTag() throws JspException {
		Page page = (Page) pageContext.getRequest().getAttribute("page");
		HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
		
		StringBuffer str = new StringBuffer();
		str.append("<script src=\""+req.getContextPath()+"/js/jquery-1.7.2.min.js\" type=\"text/javascript\"></script>");
		str.append("<script>");
		str.append("$(document).ready(function() {");
		str.append("var args1  =/[\\':;*?~`!@#$%^&+=-_{}\\[\\]\\<\\>\\(\\),\\.']/;");
		str.append("var args2  = /[^\\d.]/g; ");
		str.append(" $(\"#pageNoInput\").keyup(function(){     ");
		str.append("      var tmptxt=$(this).val();     ");
		str.append("     if($(this).val() != \"\"){");
		str.append("  	   $(this).val(tmptxt.replace(args1,\"\").replace(args2,\"\"));   ");
		str.append("    }");
		str.append("     if($(this).val() != \"\" && $(this).val() >"+page.getTotalPages()+"){");
		str.append("  	   $(this).val(\"\");   ");
		str.append("    }");
		str.append("  }).bind(\"paste\",function(){     ");
		str.append("     var tmptxt=$(this).val();     ");
		str.append("     $(this).val(tmptxt.replace(args1,\"\").replace(args2,\"\"));  ");
		str.append(" }).css(\"ime-mode\", \"disabled\");");
		str.append("});");

		str.append("function goPage(){ ");
		str.append("$('form').submit(function(){return false;});");
		str.append("var event=arguments.callee.caller.arguments[0]||window.event; ");
		str.append(" if (event.keyCode == 13){var pageNoInput = $(\"#pageNoInput\").val();");
		str.append(" if (null==pageNoInput || ''==pageNoInput){return;}");
		str.append("window.location='" + url + "?pageNo='+pageNoInput+'" + ((getParam() != null && !"".equals(getParam())) ? ("&" + getParam()) : "" )+ "';");
		str.append("}}");
		str.append("</script>");
		if (page != null&&page.getTotalCount()>0) {
			//Locale locale = Locale.getDefault();
			/*ResourceBundle bundle = ResourceBundle.getBundle(
					"i18n/messages", locale);*/
			if(recordType==null||recordType.length()==0)
	           recordType=" 條記錄";
			
			String style="page";
			String css=this.getCss();
			if(css!=null)
				style=css;
			str.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" class=\""+style+"\" ><tr>");
			
			str.append("<td align=\"left\" width=\"20%\">");
			if (page.getTotalCount() > 0) {
				str.append("&nbsp;&nbsp;共 "+page.getTotalCount()+" "+recordType);
				str.append("&nbsp;");
			}
			str.append("<td align=\"center\" width=\"20%\">");
			if (page.getTotalPages() > 0) {
				str.append("共"+page.getTotalPages()+"頁");
				str.append("&nbsp;");
			}
			str.append("</td>");
			str.append("<td align=\"right\" width=\"60%\">");
			
			int cpageInt=page.getPageNo();
            long totalInt=page.getTotalPages();
            long pages=0;
            //fixed by peter
			//先找出需要分多少个10页
			int numOfOnePage = 5;
			if (page.getTotalPages() % numOfOnePage == 0) {
				pages = page.getTotalPages() / numOfOnePage;
			} else {
				pages = page.getTotalPages() / numOfOnePage + 1;
			}
            if(cpageInt>pages * numOfOnePage){
				cpageInt = Long.valueOf(totalInt).intValue();
				page.setPageNo(cpageInt+1);
			}
			if (page.isHasPre()) {
				str.append(buildUrl(1, "<img src=\""+req.getContextPath()+"/images/page/first.gif\"/>"));
				str.append(buildUrl(page.getPrePage(), "<img src=\""+req.getContextPath()+"/images/page/prev.gif\"/>"));
				str.append( "『");
			}
			else
			{
				str.append("<img src=\""+req.getContextPath()+"/images/page/first_disabled.gif\"/>");
				str.append( "<img src=\""+req.getContextPath()+"/images/page/prev_disabled.gif\"/>");
				str.append( "『");
			}
			
			if (page.getTotalPages() > 1) {
				
				long v=0;
				long v1=0;
				
				for (int i = 1; i <= pages; i++) {
					//如果当前页数小于当前分页最大页码
					if (cpageInt <= i * numOfOnePage) {
						//当前分页的最小页码
						v = (i - 1) * numOfOnePage + 1;
						v1 = i * numOfOnePage;
						//如果当前分页大于数据最大页码，取分页数据的页码
						if (v1 > totalInt) {
							v1 = totalInt;
						}
						break;
					}
				}

				// 10个为一组显示
				for (long i = v; i <= v1; i++) {
					str.append("&nbsp;");
					if (page.getPageNo() != i) {
						str.append(buildUrl(i, String.valueOf(i)));
					} else {
						str.append("<span class=\"current\">"+i+"</span>");
					}
				}
				
				
				
				/*for (int i = 1; i <= page.getTotalPages(); i++) {
					str.append("&nbsp;");
					if (page.getPageNo() != i) {
						str.append(buildUrl(i, String.valueOf(i)));
					} else {
						str.append(i);
					}
				}*/
			}

			if (page.isHasNext()) {
				str.append("&nbsp;");
				str.append( "』");
				str.append("<input id=\"pageNoInput\" type=\"text\" value=\"\" style=\"width:30px;\" onkeydown=\"goPage();\">");
				str.append(buildUrl(page.getNextPage(), "<img src=\""+req.getContextPath()+"/images/page/next.gif\"/>"));
				str.append(buildUrl(page.getTotalPages(), "<img src=\""+req.getContextPath()+"/images/page/last.gif\"/>"));
				
			}
			else if(page.getTotalPages()==1)
			{
				str.append("&nbsp;");
				str.append( "1  』");
				str.append( "<img src=\""+req.getContextPath()+"/images/page/next_disabled.gif\"/>");
				str.append("<img src=\""+req.getContextPath()+"/images/page/last_disabled.gif\"/>");
			}
			else
			{
				str.append("&nbsp;");
				str.append( " 』");
				str.append("<input id=\"pageNoInput\" type=\"text\" value=\"\" style=\"width:30px;\" onkeydown=\"goPage();\">");
				str.append( "<img src=\""+req.getContextPath()+"/images/page/next_disabled.gif\"/>");
				str.append("<img src=\""+req.getContextPath()+"/images/page/last_disabled.gif\"/>");
			}
			str.append("</td>");
			str.append("</tr></table>");

			
		}
		else
		{
			String css=this.getCss();
			String style="page";
			if(css!=null)
				style=css;
			str.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" class=\""+style+"\" >");
			str.append("<tr><td  align=\"center\" width=\"100%\"><font color=\"red\">當前沒有數據......</font></td></tr></table>");
		}
		try {
			pageContext.getOut().write(str.toString());
		} catch (IOException e) {
			log.warn(e.getMessage());
		}

		return SKIP_BODY;
	}

	private String buildUrl(long pageNo, String txt) {
		StringBuffer str = new StringBuffer();
		str.append("<a href=\"");
		str.append(url).append("?pageNo=").append(pageNo);
		if (getParam() != null && !"".equals(getParam())) {
			str.append("&").append(getParam());
		}
		str.append("\">");
		str.append(txt);
		str.append("</a>");
		return str.toString();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParam() {
		
	
			
			try {
				String parameter = new String(param.getBytes("iso-8859-1"),"utf-8");
				return parameter;
			} catch (UnsupportedEncodingException e) {
				return param;
			}
	
		
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

}
