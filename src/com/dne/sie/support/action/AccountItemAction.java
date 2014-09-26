package com.dne.sie.support.action;

//Java ����
//import java.util.List;
import java.util.Date;

//Java ��չ��
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//������
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

//�Զ�����
import com.dne.sie.util.action.ControlAction;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.bo.EmployeeInfoBo;
import com.dne.sie.support.bo.AccountBo;
import com.dne.sie.support.bo.AccountItemBo;
import com.dne.sie.support.form.AccountItemForm;


/**
 * ������ϢAction������
 * @author xt
 * @version 1.1.5.6
 */
public class AccountItemAction extends ControlAction {
	
	
	/**
	* ���ñ���Ϣ �б�ҳ��
	* @param request HttpServletRequest
	* @param form �?���
	* @return ҳ��
	*/
   public String accountList(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "accountList";
		String queryFlag=request.getParameter("queryFlag");
		AccountItemForm pif=(AccountItemForm)form;
		AccountItemBo pib = AccountItemBo.getInstance();
		if(!"T".equals(queryFlag)){
			request.setAttribute("accountList",pib.accountList(pif));
		}
		request.setAttribute("empList",EmployeeInfoBo.getInstance().getEmpSelectList());
		
		
		return forward;
   }
   
   /**
	* ���ñ���Ϣ ����ҳ��
	* @param request HttpServletRequest
	* @param form �?���
	* @return ҳ��
	*/
  public String accountInit(HttpServletRequest request, ActionForm form) throws Exception {
		String forward = "accountEdit";
		
		request.setAttribute("empList",EmployeeInfoBo.getInstance().getEmpSelectList());
		
		request.setAttribute("accountItemForm",new AccountItemForm());
		request.setAttribute("flag","init");
		
		return forward;
  }
  
  /**
	* ���ñ���Ϣ �޸�ҳ��
	* @param request HttpServletRequest
	* @param form �?���
	* @return ҳ��
	*/
  public String accountEdit(HttpServletRequest request, ActionForm form) {
		String forward = "accountEdit";
		try{
			String sysId=request.getParameter("ids");
			AccountItemForm aif=AccountItemBo.getInstance().find(new Long(sysId));
			if(aif.getRatio()!=null){
				aif.setStrRatio(aif.getRatio().toString());
			}
			aif.setFeeDate1(Operate.trimDate(aif.getFeeDate()));
			request.setAttribute("accountItemForm", aif);
			request.setAttribute("empList",EmployeeInfoBo.getInstance().getEmpSelectList());
			request.setAttribute("flag","edit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }

  /**
	* ���ñ���Ϣ �������
	* @param request HttpServletRequest
	* @param form �?���
	* @return ҳ��
	*/
  public String insertAccount(HttpServletRequest request, ActionForm form) {
		String forward = "accountEdit";
		try{

			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");

			AccountItemForm pif=(AccountItemForm)form;
			pif.setFeeDate(Operate.toSqlDate(pif.getFeeDate1()));
			pif.setCreateBy(userId);
			pif.setVoucherNo(FormNumberBuilder.getVoucherNo(pif.getPayType(),Operate.getFeeDate(pif.getFeeDate())));
			if(pif.getStrRatio()!=null&&!pif.getStrRatio().equals("")){
				pif.setRatio(new Float(pif.getStrRatio()));
			}
			AccountItemBo cibo = AccountItemBo.getInstance();
			int tag=cibo.add(pif);
			request.setAttribute("tag", tag + "");
			request.setAttribute("accountItemForm", pif);
			request.setAttribute("empList",EmployeeInfoBo.getInstance().getEmpSelectList());
			request.setAttribute("flag","init");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* ���ñ���Ϣ �޸Ĳ���
	* @param request HttpServletRequest
	* @param form �?���
	* @return ҳ��
	*/
  public String updateAccount(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			HttpSession session = request.getSession();
			Long userId = (Long) session.getAttribute("userId");

			AccountItemForm pif=(AccountItemForm)form;
			pif.setFeeDate(Operate.toSqlDate(pif.getFeeDate1()));
			pif.setUpdateBy(new Long(userId));
			pif.setUpdateDate(new Date());
			if(pif.getStrRatio()!=null&&!pif.getStrRatio().equals("")){
				pif.setRatio(new Float(pif.getStrRatio()));
			}
			
			AccountItemBo cibo = AccountItemBo.getInstance();

            if(pif.getCustomerName()==null||pif.getCustomerName().isEmpty()){
                pif.setCustomerId("");
            }
			int tag=cibo.modify(pif);
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "updateAccount");
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }
  

  /**
	* ���ñ���Ϣ ɾ�����
	* @param request HttpServletRequest
	* @param form �?���
	* @return ҳ��
	*/
  public String deleteAccount(HttpServletRequest request, ActionForm form) {
		String forward = "resultMessage";
		try{
			String sysId=request.getParameter("ids");
			int tag=AccountItemBo.getInstance().delete(sysId);
			request.setAttribute("tag", tag + "");
			request.setAttribute("businessFlag", "deleteAccount");
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
  }

	/**
	 * ��Ŀ����ʼ��ҳ��
	 * @param request HttpServletRequest
	 * @param form �?���
	 * @return ҳ��
	 */
	public String treeItemRadio(HttpServletRequest request,ActionForm form) {
		String forward = "treeItem";	
		
		try{
			String subId=request.getParameter("subId")==null?"":request.getParameter("subId");
			
			AccountItemBo abo = AccountItemBo.getInstance();
			
			request.setAttribute("tree",abo.writeTree(subId));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	 	return forward;
	}
	

	/**
	* ���ñ���Ϣ �б�ҳ��
	* @param request HttpServletRequest
	* @param form �?���
	* @return ҳ��
	*/
   public String dropdownTree(HttpServletRequest request, ActionForm form) throws Exception{
		String forward = "dropdownTree";
		
		request.setAttribute("strTree",AccountItemBo.getInstance().getDropdownTree());
		
		
		return forward;
   }

}

