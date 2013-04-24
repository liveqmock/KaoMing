package com.dne.sie.util.request;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
* �����ύ���е��а�ȫ�������ַ������Է�ֹSQLע��
* ʹ�÷�������Filterӳ���м���2������
* ����1��delete������ָ�������˵��Ĵʻ㣬�ÿո�ֿ������磺delete insert
* ����2����Ҫ���˵ı����������ÿո�ֿ������磺user pass
* @author lhy
* @version 1.0
*/
public class SQLInjectionFilter implements Filter {

    // The filter configuration object we are associated with. If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    private static LinkedList<Pair> wordMap = null;
    private static HashSet<String> paramSet = null;
    
    public SQLInjectionFilter() {
    }

    //��Servlet�����ж�ȡWords�滻���򲢴���wordMap��
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
    throws IOException, ServletException {
        
        if(wordMap==null || paramSet==null)
        {
            wordMap = new LinkedList<Pair>();
            paramSet = new HashSet();
            Enumeration enums = filterConfig.getInitParameterNames();
            while(enums.hasMoreElements())
            {
                String key = (String)enums.nextElement();
                String value = filterConfig.getInitParameter(key);
                if(key.equals("delete"))
                {
                    for(String s:value.split(" "))
                    {
                        wordMap.add(new Pair(s, ""));
                        System.out.println("----s="+s);
                    }
                }
                else if(key.equals("param"))
                {
                    for(String s:value.split(" "))
                    {
                        paramSet.add(s);
                    }
                }
                else
                {
                   wordMap.add(new Pair(key, value));
                   key = null;
                   value = null;
                }

            }
            
            //����������Filter�й��˵ķ��ţ�Ҳ����˳�������ݿ����ʱ��ķǷ�����
            wordMap.add(new Pair("\"", "&quot; "));//˫����
            wordMap.add(new Pair("\'", "''"));//��������''���
            wordMap.add(new Pair("&", "&amp; "));//&
            wordMap.add(new Pair("<[^>]*>", ""));//�����޵е����򡭡��滻�����е�HTML��JS��CSS��ǩ
            
            wordMap.add(new Pair("xxtt", "@@##"));//test
        }
    
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
    throws IOException, ServletException {
    //
    // Write code here to process the request and/or response after
    // the rest of the filter chain is invoked.
    //
    
    //
    // For example, a logging filter might log the attributes on the
    // request object after the request has been processed. 
    //
    /*
    for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
        String name = (String)en.nextElement();
        Object value = request.getAttribute(name);
        log("attribute: " + name + "=" + value.toString());

    }
    */
    //

    //
    // For example, a filter might append something to the response.
    //
    /*
    PrintWriter respOut = new PrintWriter(response.getWriter());
    respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
    */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
    throws IOException, ServletException {

    doBeforeProcessing(request, response);
    
    Throwable problem = null;

    try 
        {
            request = new NewWrapper((HttpServletRequest)request,wordMap,paramSet);

        chain.doFilter(request, response);
    }
    catch(Throwable t) {
        //
        // If an exception is thrown somewhere down the filter chain,
        // we still want to execute our after processing, and then
        // rethrow the problem after that.
        //
        problem = t;
        t.printStackTrace();
    }

    doAfterProcessing(request, response);

    //
    // If there was a problem, we want to rethrow it if it is
    // a known type, otherwise log it.
    //
    if (problem != null) {
        if (problem instanceof ServletException) throw (ServletException)problem;
        if (problem instanceof IOException) throw (IOException)problem;
        sendProcessingError(problem, response);
    }
    }

    
    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
    return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {

    this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter 
     *
     */
    public void destroy() { 
    }

    /**
     * Init method for this filter 
     *
     */
    public void init(FilterConfig filterConfig) {

    this.filterConfig = filterConfig;
    }

    /**
     * Return a String representation of this object.
     */
    public String toString() {

    if (filterConfig == null) return ("SQLInjectionFilter()");
    StringBuffer sb = new StringBuffer("SQLInjectionFilter(");
    sb.append(filterConfig);
    sb.append(")");
    return (sb.toString());

    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
    
    String stackTrace = getStackTrace(t);

    if(stackTrace != null && !stackTrace.equals("")) {

        try {
            
        response.setContentType("text/html");
        PrintStream ps = new PrintStream(response.getOutputStream());
        PrintWriter pw = new PrintWriter(ps); 
        pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N
            
        // PENDING! Localize this for next official release
        pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n"); 
        pw.print(stackTrace); 
        pw.print("</pre></body>\n</html>"); //NOI18N
        pw.close();
        ps.close();
        response.getOutputStream().close();;
        }
        
        catch(Exception ex){ }
    }
    else {
        try {
        PrintStream ps = new PrintStream(response.getOutputStream());
        t.printStackTrace(ps);
        ps.close();
        response.getOutputStream().close();;
        }
        catch(Exception ex){ }
    }
    }

    public static String getStackTrace(Throwable t) {

    String stackTrace = null;
        
    try {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.close();
        sw.close();
        stackTrace = sw.getBuffer().toString();
    }
    catch(Exception ex) {}
    return stackTrace;
    }

    public void log(String msg) {
    filterConfig.getServletContext().log(msg); 
    }

     public static void main(String args[])
    {
         String str="select";
    }
}

class Pair
{
    public String key;
    public String value;
    
    public Pair(String k,String v)
    {
        key = k;
        value = v;
    }
}

class NewWrapper extends HttpServletRequestWrapper
{
    private LinkedList<Pair> wordMap;
    private HashSet<String> paramMap;
    
    public NewWrapper(HttpServletRequest req,LinkedList<Pair> map,HashSet m2)
    {
        super(req);
        wordMap = map;
        paramMap = m2;
    }
    public String getParameter(String str)
    {
        try
        {
            String param = super.getParameter(str);
            
            if(!paramMap.contains(str))
            {
                //����������ڱ������б��У�ֱ�ӷ���
                return param;
            }
            
            Iterator<Pair> itr = wordMap.listIterator();
            while(itr.hasNext())
            {
                Pair p = itr.next();
                if(p.key.equals("xxtt")){
                System.out.println(p.key);
                System.out.println(p.value);
                }
                param = param.replaceAll(p.key, p.value);
            }
            return param.trim();
        } 
        catch (Exception e)
        {
            return null;
        }

    }
   

}
