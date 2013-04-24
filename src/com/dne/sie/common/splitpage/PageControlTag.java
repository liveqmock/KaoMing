package com.dne.sie.common.splitpage;

import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.http.HttpServletRequest;


public class PageControlTag extends BodyTagSupport{

	public PageControlTag()	{
		style = null;
		className = "datapilot";
		align = "left";
		visable = true;
		visible = true;
		idPage = "pageNum";
		idFirst = "first";
		idLast = "last";
		numOfRcds = 0;
		formName = "form_cestag";
		rcdPerPage = 10;
		currentPage = 1;
		url = null;
		maxPageCount=100;
	}

	public int doStartTag()	throws JspException	{
		try{
		
			if(url == null) url = "";
			
			String url1 = ((HttpServletRequest) super.pageContext.getRequest()).getRequestURI();
			if(url1!=null&&url1.indexOf("/")!=-1){
				url1=url1.substring(url1.lastIndexOf("/"));
				if(url1.indexOf("repair")!=-1){
					maxPageCount=50;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return 2;
	}

	public int doEndTag()
		throws JspException	{
		super.pageContext.setAttribute(idFirst, new Integer(getFirstRcd()), 1);
		super.pageContext.setAttribute(idLast, new Integer(getLastRcd()), 1);
		if(!visable || !visible)
			return 0;
		String strPrevious = "";
		String strNext = "";
		if(getCurrentPage() > 1)
			strPrevious = String.valueOf(String.valueOf((new StringBuffer("<span class='clickable' onclick=\"javascript:cescomPageUpDown(this,'")).append(url).append("','strPrevious')\")>\u4E0A\u4E00\u9875</span>")));
		else
			strPrevious = "<span>\u4E0A\u4E00\u9875</span>";
		if(getCurrentPage() < getNumOfPages())
			strNext = String.valueOf(String.valueOf((new StringBuffer("<span class='clickable' onclick=\"javascript:cescomPageUpDown(this,'")).append(url).append("','next')\">\u4E0B\u4E00\u9875</span>")));
		else
			strNext = "<span>\u4E0B\u4E00\u9875</span>";
		StringBuffer buf = new StringBuffer();
		buf.append("<input type=hidden name=hiddenGoto id=hiddenGoto>");
		buf.append("<TABLE ");
		if(className != null)
		{
			buf.append("class=");
			buf.append(className);
		}
		if(style != null)
		{
			buf.append("style=");
			buf.append(style);
		}
		buf.append(" id=");
		buf.append("datapilot");
		buf.append(">");
		buf.append("<TR ");
		buf.append(">\n");
		if(align != null)
			if(!align.equals("separate"))
				buf.append(String.valueOf(String.valueOf((new StringBuffer("<TD align=")).append(align).append(" nowrap >\n"))));
			else
				buf.append("<TD align='left' nowrap>\n");
		buf.append(String.valueOf(String.valueOf((new StringBuffer("  \u5171<span class='transformableNumber'>")).append(getNumOfRcds()).append("</span>\u6761\u8BB0\u5F55&nbsp;|&nbsp"))));
		buf.append(String.valueOf(String.valueOf((new StringBuffer("  \u7B2C<span class='transformableNumber'>")).append(getCurrentPage()).append("/").append(getNumOfPages()).append("</span>\u9875&nbsp;|&nbsp;"))));
		buf.append("  \u6BCF\u9875");
		buf.append("  <input id=txtNumPerPage type=\"text\"");
		buf.append(String.valueOf(String.valueOf((new StringBuffer("  name=\"txtNumPerPage\"   onfocus=\"{this.select()}\" value=\"")).append(getRcdsPerPage()).append("\" "))));
		buf.append("  onkeydown=\"if (event.keyCode == 13) {");
		buf.append("      cescomJumpToPage(document.getElementById('go'),'");
		buf.append(url);
		buf.append("');}\" ");
		buf.append(">\n");
		buf.append("  \u6761\u8BB0\u5F55&nbsp;|&nbsp;\n");
		if(align != null && align.equals("separate"))
			buf.append("</TD><TD align='right' nowrap>\n");
		buf.append(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(strPrevious)))).append("&nbsp;").append(strNext).append("\n"))));
		buf.append(String.valueOf(String.valueOf((new StringBuffer("<input name=\"currentPage\" type=\"hidden\" value=\"1\" >")))));
		buf.append("&nbsp;|&nbsp;\u5230\u7B2C");
		buf.append(String.valueOf(String.valueOf((new StringBuffer("<input type=\"text\" id=txtGoto name=\"txtGoto\" value =\"")).append(getCurrentPage()).append("\" "))));
		buf.append("  onkeydown=\"if (event.keyCode == 13) {");
		buf.append("      cescomJumpToPage(document.getElementById('go'),'");
		buf.append(url);
		buf.append("');} \"");
		buf.append(" onfocus=\"{this.select()}\" size=\"2\" >");
		buf.append("\u9875");
		buf.append("  <input type=button id=\"go\" name='buttonGo' value=' '");
		buf.append("  onclick=\"javascript:cescomJumpToPage(this,'");
		buf.append(url);
		buf.append("')\">\n");
		buf.append("  </TD>\n");
		buf.append("  </TR>\n");
		buf.append("  </TABLE>");
		buf.append("  <script language=\"JavaScript\">\n<!--\n\n");
		buf.append("  if (document.all.txtGoto.length == null) {\n");
		buf.append("      document.all.txtGoto.size=document.all.txtGoto.value.length;\n");
		buf.append("      document.all.txtNumPerPage.size=document.all.txtNumPerPage.value.length;\n");
		buf.append("  }\n");
		buf.append("  else {\n");
		buf.append("      for(var i = 0; i < document.all.txtGoto.length; i++) {\n");
		buf.append("          document.all.txtGoto[i].size = document.all.txtGoto[i].value.length;\n");
		buf.append("          document.all.txtNumPerPage[i].size=document.all.txtNumPerPage[i].value.length;\n");
		buf.append("      }\n");
		buf.append("  }\n");
		buf.append("function cescomPageUpDown(oThis,fileName,whichStep){\n");
		buf.append("  var formObj =  document.getElementById('datapilot').parentNode;\n");
		buf.append("  var i = 1; \n");
		buf.append("  while (formObj.tagName != 'FORM') {\n");
		buf.append("   \t  if (formObj.tagName == 'HTML') {\n");
		buf.append("          alert('\u9519\u8BEF\uFF1ApageControl\u6807\u7B7E\u5916\u5C42\u5E94\u8BE5\u6709form\u5143\u7D20\uFF01');\n");
		buf.append("          break;\n");
		buf.append("      }\n");
		buf.append("      formObj = formObj.parentNode;\n");
		buf.append("      i++;\n");
		buf.append("  }\n");
		buf.append("  if (document.all.txtNumPerPage.length == null) {\n");
		buf.append("      var intNumPerPage=document.all.txtNumPerPage.value;\n");
		buf.append("  }\n");
		buf.append("  else {\n");
		buf.append("      for (var i=0; i<document.all.txtNumPerPage.length;i++){\n");
		buf.append("          if(oThis == document.all.txtNumPerPage[i]){\n");
		buf.append("              var intNumPerPage=document.all.txtNumPerPage[i].value;\n");
		buf.append("              break;\n");
		buf.append("          }\n");
		buf.append("      }\n");
		buf.append("  }\n");
				buf.append("if(isNaN(intNumPerPage)||(intNumPerPage<1)||intNumPerPage.indexOf('.')!=-1){\n");
				buf.append(" alert(\"你输入了不合法的每页记录数！\");\n");
				buf.append("return;  }\n");
				buf.append("else if(intNumPerPage>"+maxPageCount+"){\n");
				buf.append("	 alert(\"每页记录请勿超过"+maxPageCount+"！\");\n");
				buf.append("     return;");
				buf.append("  }\n");
		buf.append("  if (whichStep==\"next\") {\n");
		buf.append("      if (document.all.currentPage.length == null) {\n");
		buf.append("          document.all.currentPage.value="+getCurrentPage()+";\n");
		buf.append("          document.all.currentPage.value++;\n");
		buf.append("      }\n");
		buf.append("      else {\n");
		buf.append("          for (var i=0; i<document.all.currentPage.length;i++){\n");
		buf.append("          	  document.all.currentPage[i].value="+getCurrentPage()+";\n");
		buf.append("              document.all.currentPage[i].value++;\n");
		buf.append("          }\n");
		buf.append("      }\n");
		buf.append("  }\n");
		buf.append("  else {\n");
		buf.append("      if (document.all.currentPage.length == null) {\n");
		buf.append("          document.all.currentPage.value="+getCurrentPage()+";\n");
		buf.append("          document.all.currentPage.value--;\n");
		buf.append("      }\n");
		buf.append("      else {\n");
		buf.append("          for (var i=0; i<document.all.currentPage.length;i++){\n");
		buf.append("          	  document.all.currentPage[i].value="+getCurrentPage()+";\n");
		buf.append("              document.all.currentPage[i].value--;\n");
		buf.append("          }\n");
		buf.append("      }\n");
		buf.append("  }\n");
		buf.append("  if(document.all.txtGoto.length == null) {\n");
		buf.append("      document.all.txtGoto.value='';\n");
		buf.append("  }\n");
		buf.append("  else {\n");
		buf.append("      for (var i=0; i<document.all.txtGoto.length;i++){\n");
		buf.append("          document.all.txtGoto[i].value='';\n");
		buf.append("      }\n");
		buf.append("  }\n");
		buf.append("  formObj.action=fileName;\n");
		buf.append("  formObj.submit();\n");
		buf.append("}\n");
		buf.append("function cescomJumpToPage(oThis,fileName){\n");
		buf.append("  var formObj =  document.getElementById('datapilot').parentNode;\n");
		buf.append("  var i = 1; \n");
		buf.append("  while (formObj.tagName != 'FORM') {\n");
		buf.append("   \t  if (formObj.tagName == 'HTML') {\n");
		buf.append("          alert('\u9519\u8BEF\uFF1ApageControl\u6807\u7B7E\u5916\u5C42\u5E94\u8BE5\u6709form\u5143\u7D20\uFF01');\n");
		buf.append("          break;\n");
		buf.append("      }\n");
		buf.append("      formObj = formObj.parentNode;\n");
		buf.append("      i++;\n");
		buf.append("  }\n");
		buf.append("  var flag=true;\n");
		buf.append("  if (document.all.txtNumPerPage.length == null) {\n");
		buf.append("      var intNumPerPage=document.all.txtNumPerPage.value;\n");
		buf.append("  }\n");
		buf.append("  else {\n");
		buf.append("      for (var i=0; i<document.all.txtNumPerPage.length;i++){\n");
		buf.append("          if(oThis == document.all.buttonGo[i]){\n");
		buf.append("              var intNumPerPage=document.all.txtNumPerPage[i].value;\n");
		buf.append("              break;\n");
		buf.append("          }\n");
		buf.append("      }\n");
		buf.append("  }\n");
		buf.append("  if (document.all.txtGoto.length == null) {\n");
		buf.append("      var intGoto=document.all.txtGoto.value;\n");
		buf.append("  }\n");
		buf.append("  else {\n");
		buf.append("      for (var i=0; i<document.all.txtGoto.length;i++){\n");
		buf.append("          if(oThis == document.all.buttonGo[i]){\n");
		buf.append("              var intGoto=document.all.txtGoto[i].value;\n");
		buf.append("              break;\n");
		buf.append("          }\n");
		buf.append("      }\n");
		buf.append("  }\n");
		buf.append("  if(isNaN(intNumPerPage)||(intNumPerPage<1)||intNumPerPage.indexOf('.')!=-1){\n");
		buf.append("	 alert(\"你输入了不合法的每页记录数！\");\n");
		buf.append("     flag=false;");
		buf.append("  }else if(intNumPerPage>"+maxPageCount+"){\n");
		buf.append("	 alert(\"每页记录请勿超过"+maxPageCount+"！\");\n");
		buf.append("     flag=false;");
		buf.append("  }\n");
		buf.append("  if(isNaN(intGoto)||(intGoto<1)||intGoto.indexOf('.')!=-1){\n");
		buf.append("	alert(\"你输入了不合法的当前页数！\");\n");
		buf.append("    flag=false;");
		buf.append("  }\n");
		buf.append("  if (flag){\n");
		buf.append("      document.all.hiddenGoto.value = intGoto;\n");
		buf.append("      formObj.currentPage.value='';\n");
		buf.append("      formObj.action=fileName;");
		buf.append("      formObj.submit();");
		buf.append("   }\n");
		buf.append("}\n");
		buf.append("-->\n\n");
		buf.append("</script>");
		try
		{
			super.pageContext.getOut().print(buf.toString());
		}
		catch(IOException ioexception)
		{
			throw new JspException("PageControlTag: \u4E0D\u80FD\u8F93\u51FA\u4FE1\u606F", ioexception);
		}
		url = null;
		return 6;
	}

	public void release()
	{
	}

	/**
	 * @deprecated Method setVisable is deprecated
	 */

	public void setVisable(boolean visable)
	{
		this.visable = visable;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public String getFormName()
	{
		return formName;
	}

	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	public String getStyle()
	{
		return style;
	}

	public void setStyle(String style)
	{
		this.style = style;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public String getAlign()
	{
		return align;
	}

	public void setAlign(String align)
	{
		this.align = align;
	}

	public int getNumOfRcds()
		throws JspException
	{
		return numOfRcds;
	}

	public void setNumOfRcds(int numOfRcds)
		throws JspException
	{
		this.numOfRcds = numOfRcds;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getIdPage()
	{
		return idPage;
	}

	public void setIdPage(String s)
	{
		idPage = s;
	}

	public String getIdFirst()
	{
		return idFirst;
	}

	public void setIdFirst(String s)
	{
		idFirst = s;
	}

	public String getIdLast()
	{
		return idLast;
	}

	public void setIdLast(String s)
	{
		idLast = s;
	}

	public void setRcdPerPage(int rcdPerPage)
	{
		this.rcdPerPage = rcdPerPage;
	}

	private int getRcdsPerPage()
		throws JspException
	{
		String pageSize = super.pageContext.getRequest().getParameter("txtNumPerPage");
		if(pageSize != null)
			try
			{
				rcdPerPage = Integer.parseInt(pageSize);
			}
			catch(Exception e)
			{
				int i = rcdPerPage;
				return i;
			}
		if(rcdPerPage < 1)
			rcdPerPage = 10;
		return rcdPerPage;
	}

	private int getCurrentPage()
		throws JspException
	{
		currentPage = 1;
		String page = super.pageContext.getRequest().getParameter("currentPage");
		if(page != null && !page.equals(""))
		{
			currentPage = Integer.parseInt(page);
		} else
		{
			page = super.pageContext.getRequest().getParameter("hiddenGoto");
			if(page != null && !page.equals(""))
				try
				{
					currentPage = Integer.parseInt(page);
				}
				catch(Exception e)
				{
					currentPage = 1;
					int i = 1;
					return i;
				}
		}
		if(currentPage <= 1)
			return 1;
		if(currentPage > getNumOfPages())
			return getNumOfPages();
		else
			return currentPage;
	}

	private int getNumOfPages()
		throws JspException
	{
		int pages = getNumOfRcds() / getRcdsPerPage();
		if(getNumOfRcds() == 0)
			pages = 1;
		return getNumOfRcds() % getRcdsPerPage() != 0 ? pages + 1 : pages;
	}

	private int getFirstRcd()
		throws JspException
	{
		return (getCurrentPage() - 1) * getRcdsPerPage() + 1;
	}

	private int getLastRcd()
		throws JspException
	{
		if(currentPage >= getNumOfPages())
			return getNumOfRcds();
		else
			return (getFirstRcd() + getRcdsPerPage()) - 1;
	}

	private int getFromQuery(String s, String s1)
	{
		if(s == null)
			return 1;
		int i;
		if((i = s.indexOf(s1)) < 0)
			return 1;
		String s2 = s.substring(i + s1.length());
		if((i = s2.indexOf("&")) > 0)
			s2 = s2.substring(0, i);
		try
		{
			i = Integer.parseInt(s2);
			if(i <= 0)
			{
				int j = 1;
				return j;
			}
		}
		catch(Exception exception)
		{
			int k = 1;
			return k;
		}
		return i;
	}

	private String style;
	private String className;
	private String align;
	private static final String TABLE_ID = "datapilot";
	private boolean visable;
	private boolean visible;
	private String idPage;
	private String idFirst;
	private String idLast;
	private int numOfRcds;
	private String formName;
	private int rcdPerPage;
	private int currentPage;
	private String url;
	private int maxPageCount;

}