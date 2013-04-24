package com.dne.sie.util.form;

import org.apache.struts.action.ActionForm;

/**
 * @author xt
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class CommForm extends ActionForm implements java.io.Serializable{

	private	String currentPage="1";
	private	String txtNumPerPage="10";
	private	String hiddenGoto="1";
	
		public String getCurrentPage() {
			return this.currentPage;
		}
		
		public void setCurrentPage(String currentPage) {
			this.currentPage = currentPage;
		}
	
		public String getTxtNumPerPage() {
			return this.txtNumPerPage;
		}
		
		public void setTxtNumPerPage(String txtNumPerPage) {
			this.txtNumPerPage = txtNumPerPage;
		}
	
		public String getHiddenGoto() {
			return this.hiddenGoto;
		}
		
		public void setHiddenGoto(String hiddenGoto) {
			this.hiddenGoto = hiddenGoto;
		}
	
		public String getFromPage(){
			int cp=Integer.parseInt(getCurrentPage().equals("")?getHiddenGoto():getCurrentPage());
			int tnp=Integer.parseInt(getTxtNumPerPage());
			int intFrom=(cp-1)*tnp+1;
		
			return intFrom+"";		
		}
	
		public String getToPage(){
			int cp=Integer.parseInt(getCurrentPage().equals("")?getHiddenGoto():getCurrentPage());
			int tnp=Integer.parseInt(getTxtNumPerPage());
			int intTo=cp*tnp;
			return intTo+"";		
		}
}
