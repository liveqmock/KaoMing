package com.dne.sie.util.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;

import com.dne.sie.common.encrypt.MD5;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.support.userRole.bo.FunctionBo;
import com.dne.sie.support.userRole.form.UserForm;
import com.dne.sie.util.hibernate.AllDefaultDaoImp;
import com.dne.sie.util.query.QueryParameter;

/**
 * action��Ԥ�������β������<br>
 * ÿ����Ŀ����ʵ���Լ���ControlAction����ControlAction����Щͳһ��Ԥ�������β����<br>
 * ����Action���̳���ControlAction<br>
 * @author xt
 * @version 1.1.5.6
 * @see ControlAction.java <br>
 */
public class ControlAction extends BaseControlAction {
	

	/**
	 * Ԥ�������<br>
	 * 2006-04-04 ����session�жϣ�У��ĳ�û��Ƿ��з��ʸ�action��Ȩ��<br>
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return int ��ʼ���ɹ����쳣��־
	 */
	protected int sessionInit(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
					
		HttpSession session=request.getSession();		
		
		
		int booRet=0;
		
		try{
			String url=request.getRequestURI()+"?"+request.getParameter("method");
//			System.out.println("---url="+url);
			//�û���¼
			if(url.endsWith("indexAction.do?loginConfirm")){
				session.removeAttribute("userId");
				session.removeAttribute("employeeCode");
				session.removeAttribute("userName");
				session.removeAttribute("sessionRoleIds");
				
				String loginUser=request.getParameter("loginUser");
				String pw=request.getParameter("passwd");
				if(pw!=null){
					MD5 md=new MD5();
					pw=md.getMD5ofStr(pw);
				}
				//System.out.println("----request--loginUser="+loginUser);
				if(loginUser!=null){
					String strHql="from UserForm as uf where uf.delFlag!=1 " +
					"and uf.employeeCode = :employeeCode and uf.password= :password";
						AllDefaultDaoImp dao = new AllDefaultDaoImp();
						ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
						QueryParameter param = new QueryParameter();
						param.setName("employeeCode");
						param.setValue(loginUser);
						param.setHbType(Hibernate.STRING);
						paramList.add(param);
						
						QueryParameter param2 = new QueryParameter();
						param2.setName("password");
						param2.setValue(pw);
						param2.setHbType(Hibernate.STRING);
						paramList.add(param2);
						
						List aList = dao.parameterQuery(strHql, paramList);
						if(aList!=null&&aList.size()>0){
							UserForm uf=(UserForm)aList.get(0);
							if(uf!=null){
								uf.setRoleCodeAndName();
								//��¼��¼��־
								initLogUser(loginUser,uf.getId(),uf.getUserName());
								
								//�����û�pk
								session.setAttribute("userId",uf.getId());
								session.setAttribute("employeeId",uf.getEmployeeId());
								
								//�����û���¼id
								session.setAttribute("employeeCode",uf.getEmployeeCode());
								//�����û���¼id
								session.setAttribute("userName",uf.getUserName());
								
								//����Ȩ��id
								String roleCodes=uf.getRoleCode();
								if(uf.getRoleCode()==null||"".equals(uf.getRoleCode())) roleCodes="0";
								session.setAttribute("sessionRoleIds",roleCodes);
							}else{
								booRet=2;
							}
				  
						}else{
							booRet=-1;	//�û����������
						}

				}else{
					booRet=2;
				}
			}else{
				String employeeCode=(String)session.getAttribute("employeeCode");
//				System.out.println("----session--employeeCode="+employeeCode);
				if(employeeCode==null){
					booRet=1;
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return booRet;
	}
	

	/**
	 * �û���¼��¼
	 * @param loginUser - �û�employeeCode
	 * @return boolean ����ֵ����queueUser����������Ŀǰ����
	 */
	private boolean initLogUser(String loginUser,Long userId,String userName){
		boolean booRet=false;
		try{
			
			//�������Ҫ�Ŷ�
			//if(!queueUser(ascLevel)){
			if(true){
				String strTime=Operate.getDate();
				strTime=strTime.substring(strTime.indexOf(" ")+1);
				if(Operate.userMap.containsKey(userId)){
					Object[] initVal=(Object[])Operate.userMap.get(userId);
					initVal[0]=initVal[0].toString()+", "+strTime;
					initVal[1]=new Date();
//					System.out.println("----1111--userMap="+initVal[0]+"    "+initVal[1]);
					Operate.userMap.put(userId,initVal);
				}else{
					Object[] initVal={strTime,new Date(),"1","1",userName,loginUser};
					//System.out.println("----2222--userMap="+initVal[0]+"    "+initVal[1]);
					Operate.userMap.put(userId,initVal);
				}
			}else{
				booRet=true;
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return booRet;
	}


	/**
	 * �жϵ�ǰ�û�����������Ƿ�ɷ���<br>
	 * ��ǰ���ʵ����������ts_function.link�����磺reportCreateAction.do?method=repairListQuery,
	 * ���жϵ�ǰ�û��Ƿ��з��ʸ�link��Ȩ��,���linkû�ж�����Ĭ����Ȩ��
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return boolean true - ��ʾ���ӿ��ã�false - ���ӷǷ�
	 */
	protected boolean roleCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		boolean linkRole=false;
		try{
//			String strLeft=request.getParameter("left");
			
//				Enumeration enr=request.getParameterNames();
//				String strParaName="",strFw="";
//				while(enr.hasMoreElements()){
//					String pName=(String)enr.nextElement();
//					if(pName.equals("forward")){
//						strFw="&forward="+request.getParameter(pName);
//					}else if(!pName.equals("method")&&!pName.equals("left")){
//						strParaName+="&"+pName+"="+request.getParameter(pName);
//					}
//				}
				String url=request.getRequestURI()+"?method="+request.getParameter("method");
//				String link=url+strParaName+strFw;
				String link=url;
				if(link.indexOf('/')!=-1&&link.indexOf('/')<link.indexOf('?')){
					link=link.substring(link.lastIndexOf('/')+1);
				}
//				System.out.println("-------url="+url);
//				System.out.println("-------link="+link);
				if(link.endsWith("indexAction.do?method=frameContent")||link.endsWith("userAction.do?method=leftCreat")
						|| link.endsWith("indexAction.do?method=loginConfirm")){
					return true;
				}
				
				AllDefaultDaoImp dao = new AllDefaultDaoImp();
				List functionIds=dao.list("select ff.id,functionType from FunctionForm as ff " +
					"where ff.link like '"+link+"%'");
//				System.out.println("-------functionId="+functionId);
				if(functionIds==null||functionIds.isEmpty()){
					functionIds=dao.list("select ff.id,functionType from FunctionForm as ff " +
							"where ff.link like '"+link.substring(0,link.indexOf("method="))+"%'");
					if(functionIds==null||functionIds.isEmpty()) return true;
				}
				HttpSession session=request.getSession();
				Long userId=(Long)session.getAttribute("userId");
				for(int i = 0; i< functionIds.size();i++){
					TreeSet linkSet=(TreeSet)FunctionBo.hm.get(userId);
					Object[] obj = (Object[])functionIds.get(i);
					
					if((linkSet!=null&&linkSet.contains(obj[0].toString())) 
							|| Integer.parseInt(obj[1].toString())==9){
						linkRole=true;
						break;
					}
				}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return linkRole;
	}
	
	/**
	 * Ԥ������룬����<br>
	 * 1.��߲˵�����ѯ��ֱ�ӷ��ؿ��б�ҳ��<br>
	 * 2.ά��վ��ѯ�����ʱ�䷶Χ����<br>
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return boolean true - ��ʾ���ӿ��ã�false - ���ӷǷ�
	 */
		protected boolean onInit(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
				
			boolean submitRole=true;
			String queryFlag = request.getParameter("queryFlag");
			if("N".equals(queryFlag)){
				submitRole=false;
			}
			
			return submitRole;
		}
		

	
	/** 
	 * ��β�������
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return String forward
	 */
	protected String onFinal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String forward) {
		
		return forward;
	}

}
