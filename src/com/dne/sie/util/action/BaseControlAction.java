package com.dne.sie.util.action;

//Java ������
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

//Java ��չ��
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//��������
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * ���Ʒַ���<br>
 * �̳���org.apache.struts.action.Action����ControlActionԤ����ͨ��֮��ִ��
 * @author xt
 * @version 1.1.5.6
 * @see BaseControlAction.java <br>
 */
public abstract class BaseControlAction extends Action {
	

	/**
	 * ���Ʒ���ִ��
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward 
	 */
	public ActionForward execute(ActionMapping mapping,ActionForm form,
								 HttpServletRequest request, HttpServletResponse response)
								 throws Exception {
		String forward = null;
		try {		
			int flag=sessionInit(mapping,form,request,response);
			
			if(flag==-1){//�û����������
				forward="sessionDisable";
				request.setAttribute("flag", "err");
			}else if(flag==1){//session��Ч
				forward="sessionDisable";
			}else if(flag==2){//�û����������
				forward="userErr";
			}else if(!roleCheck(mapping,form,request,response)){	//Ȩ�޴���
				forward="roleFalse";
			
			}else if(onInit(mapping,form,request,response)){//Ԥ����ʧ�ܾͲ�ִ��
				//HttpSession session=request.getSession();	
				
				Class cls = getClass();
				String strMethodName = request.getParameter("method");
				
				try{
					Class[] class1={HttpServletRequest.class,ActionForm.class};
					Method methods1=cls.getDeclaredMethod(strMethodName,class1);
					//System.out.println("--------methods1="+methods1);
					Object arglist[] = new Object[2];
					arglist[0] = request;
					arglist[1] = form;
					forward = (String)methods1.invoke(this,arglist);
				}catch(NoSuchMethodException en){
					Class[] class2={ActionMapping.class,ActionForm.class,HttpServletRequest.class,HttpServletResponse.class};
					Method methods2=cls.getDeclaredMethod(strMethodName,class2);
					//System.out.println("--------methods2="+methods2);
					Object arglist[] = new Object[4];
					arglist[0] = mapping;
					arglist[1] = form;
					arglist[2] = request;
					arglist[3] = response;
					forward = (String)methods2.invoke(this,arglist);
				}
			
				
			}else{
				//forward="submitRepeat";
				forward = request.getParameter("forward");
				if(forward==null) forward = request.getParameter("method");
				
				if(forward==null||"".equals(forward)){
					forward = "queueUser";
				}
			}
			forward = onFinal(mapping,form,request,response,forward);//��β����
		} catch (Exception e) {
			e.printStackTrace();
			forward =  errorPageForward(request, e, "Program Exception!");
		}
		return mapping.findForward(forward);
	}
	


	/**
	 * Ԥ������Ŀ�п���������ʵ��һЩͳһ�Ĵ�����Ȩ��У�顢�ظ��ύУ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Ԥ����ɹ���ʧ��
	 */
	protected abstract boolean onInit(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * ��β����������ĿҪ��action�Ĵ������ټӹ�һ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param forward action������forward
	 * @return ������forward
	 */
	protected abstract String onFinal(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			String forward);

	/**
	 * ��¼Ԥ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Ԥ����ɹ���ʧ��
	 */	
	protected abstract int sessionInit(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response);		
		
	/**
	 * ����Ȩ��Ԥ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Ԥ����ɹ���ʧ��
	 */	
	protected abstract boolean roleCheck(ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response);			


	/**
	 * Forward error processing
	 * 
	 * @param request
	 * @param e
	 * @param exceptionMsg
	 * @return
	 */
	protected String errorPageForward(HttpServletRequest request,Exception e, String exceptionMsg) {
		return doPageForward(request, e, (exceptionMsg == null ? ""	: exceptionMsg)	+ ":" + e.getMessage());
	}
	/**
	 * error processing��and forward to show page
	 * 
	 * @param request
	 * @param e
	 * @param message
	 * @return
	 */
	private String doPageForward(HttpServletRequest request,
			Exception e, String message) {
		// �����쳣��Ϣ
//		StringBuffer msgSb = new StringBuffer();
//		if (message != null && !message.equals("")) {
//			String[] msg = message.split(";");
//			for (int i = 0; i < msg.length; i++) {
//				msgSb.append(msg[i] + "<br>");
//			}
//		}
		// �����ջ��Ϣ
//		StackTraceElement[] traceElement = e.getStackTrace();
		String eErrorStr = e.toString();
//		StringBuffer sb = new StringBuffer();
//		sb.append(eErrorStr + "<br>");
//		for (int i = 0; i < traceElement.length; i++) {
//			sb.append(traceElement[i].getClassName() + "."
//					+ traceElement[i].getMethodName() + "("
//					+ traceElement[i].getFileName() + ":"
//					+ traceElement[i].getLineNumber() + ")<br>");
//		}
		try{
			StringWriter   sw=new   StringWriter(); 
			e.printStackTrace(new PrintWriter(sw,true));
			eErrorStr=sw.toString(); 
			if(eErrorStr.indexOf("Exception Information:")!=-1){
				eErrorStr = eErrorStr.substring(eErrorStr.indexOf("Exception Information:"));
			}
		}catch(Exception e2){
			System.err.println(e2.toString());
		}
		
		request.setAttribute("errorMsg", eErrorStr);
		request.setAttribute("mess", e.getMessage());
//		request.setAttribute("stackTraces", sb.toString());
		return "errorMsg";
	}
	
}
