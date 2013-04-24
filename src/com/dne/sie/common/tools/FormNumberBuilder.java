package com.dne.sie.common.tools;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import com.dne.sie.maintenance.form.FormNumber;
import com.dne.sie.util.hibernate.AllDefaultDaoImp;
import com.dne.sie.util.query.QueryParameter;


/**
 * ����Ψһ������
 * @author HaoShuang
 */
public class FormNumberBuilder {
	private static Logger logger = Logger.getLogger(FormNumberBuilder.class);

	/**
	 * ���캯��
	 */
	public FormNumberBuilder() {
		super();
	}
	

	/**
	 * ���۵���
	 * ����ΪPI+������(6λ)+���к�(3λ)+�ͻ�ID(4λ)
	 *  PI080725003SI
	 * @param custId String �ͻ�����
	 * @return ���ɵĵ���
	 */
	public synchronized static String getNewSaleFormNumber(String custId) throws Exception{
		String newSeq = getNewSeq("S");
		return "PI"+getThisYearMonthDate()+newSeq+custId;
	}
	
	

	/**
	 * ��������
	 * ( �ɹ���ΪP+������(6λ)+���к�(3λ)+������(2λ)+"-"+���䷽ʽ P080423001SH-ZL )
	 *  ����ΪP+������(6λ)+���к�(3λ)+������(2λ) P080423001SH
	 * @return ���ɵĵ���
	 */
	public static synchronized String getOrderNo(String delivery)throws Exception{
		
		String newSeq = getNewSeq("P");
		return "P"+getThisYearMonthDate()+newSeq+delivery;
	}

	/**
	 * ��ʾ��һ����������
	 *  ����ΪP+������(6λ)+���к�(3λ)+������(2λ)
	 * @return ��ʾ�ĵ���
	 */
	public static String findOrderNo(String delivery)throws Exception{
	
		return "P"+getThisYearMonthDate()+findNewSeq("P")+delivery;
		
	}


	/**
	 * ��ȡ�µ���ˮ��
	 * @return �����ˮ��
	 */
	public static synchronized Long getStockFlowId()throws Exception{
		String fNo=getNewSeq("F");
		
		return new Long(fNo);
	}

	/**
	 * ��������
	 * ����ΪH+������(6λ)+���к�(3λ)+�ͻ�ID(4λ)
	 * @param custId String �ͻ�����
	 * @return ���ɵĵ���
	 */
	public synchronized static String getNewAsnFormNumber(String custId) throws Exception{
		String newSeq = getNewSeq("H");
		return "H"+getThisYearMonthDate()+newSeq+custId;
	}

	/**
	 * ��ȡ�µ�ƾ֤��
	 *  ����ΪpayType+������(6λ)+���к�(6λ)
	 * @return ���ɵ�ƾ֤��
	 */
	public static String getVoucherNo(String payType,String feeDate)throws Exception{
	
		if(payType.equals("XS")||payType.equals("XF")){
			payType="X";
		}else if(payType.equals("YS")||payType.equals("YF")){
			payType="Y";
		}
		String newSeq = findNewVoucherSeq(payType,feeDate);
//		return payType+getThisYearMonthDate()+newSeq;
		
		return newSeq;
		
	}
	
	

	/**
	 * ��ʾ��һ����������
	 *  ����ΪH+������(6λ)+���к�(3λ)+�ͻ�ID(4λ)
	 * @return ��ʾ�ĵ���
	 */
	public static String findNewAsnFormNumber(String custId)throws Exception{
	
		String newSeq = findNewSeq("H");
		return "H"+getThisYearMonthDate()+newSeq+custId;
		
	}
	
	
	/**
	 * ��ʾ��һ��ά�޵���
	 *  ����ΪRS+������(6λ)+���к�(3λ)
	 * @return ��ʾ�ĵ���
	 */
	public static String findNewServiveSheetNo()throws Exception{
	
		return "RS"+getThisYearMonthDate()+findNewSeq("R");
		
	}
	
	/**
	 * ������һ��ά�޵���
	 *  ����ΪRS+������(6λ)+���к�(3λ)
	 * @return ��ʾ�ĵ���
	 */
	public synchronized static String getNewServiveSheetNo()throws Exception{
	
		return "RS"+getThisYearMonthDate()+getNewSeq("R");
		
	}
	/**
	 * ������һ��ά�޵���(����)
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public synchronized static String getNewServiveSheetNo(String flag)throws Exception{
		
		return "RT"+getThisYearMonthDate()+getNewSeq("R");
		
	}
	
	private static String getThisYearMonth(){
		try{
			return Operate.getDate("yyyyMM");
		}catch(Exception e){
			return "";
		}
	}
	private static String getThisYearMonthDate() throws Exception{
		return Operate.getDate("yyMMdd");
	}

	
	private static String getNewSeq(String formType){
		String returnStr = "";
		AllDefaultDaoImp addi = new AllDefaultDaoImp();
		try{
			String seq="from FormNumber as fn where fn.formType=:formType ";
			ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
			QueryParameter param = new QueryParameter();
			param.setName("formType");
			param.setValue(formType);
			param.setHbType(Hibernate.STRING);
			paramList.add(param);
			
			if(!formType.equals("F")){
				seq+=" and  fn.formDate=:formDate ";
				QueryParameter param2 = new QueryParameter();
				param2.setName("formDate");
				param2.setValue(getThisYearMonthDate());
				param2.setHbType(Hibernate.STRING);
				paramList.add(param2);
				
				seq+=" order by fn.FormNumberId desc";
			}
			

			List aList =addi.parameterQuery(seq,paramList);	
			if(aList == null || aList.size() == 0){
				returnStr = "001";
				FormNumber fn = new FormNumber();
				fn.setFormType(formType);
				fn.setFormDate(getThisYearMonthDate());
				fn.setFormSeq(new Integer("001"));
				addi.insert(fn);
			}else{
				FormNumber afnf = (FormNumber)aList.get(0);
				int oldSeq = afnf.getFormSeq().intValue();
				int newSeq = oldSeq+1;
				int seqLength = Integer.toString(newSeq).length();
				if(seqLength <3&&!formType.equals("F")){
					int moreZerolength = 3-seqLength;
					String moreZeroStr = "";
					for(int i=1;i<=moreZerolength;i++){
						moreZeroStr +="0";
					}
					returnStr = moreZeroStr+newSeq;
				}else{
					returnStr = Integer.toString(newSeq);
				}
				afnf.setFormSeq(new Integer(returnStr));
				addi.update(afnf);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnStr;
	}
	
	

	private static String findNewSeq(String formType){
		String returnStr = "";
		AllDefaultDaoImp addi = new AllDefaultDaoImp();
		try{
			String seq="from FormNumber as fn where fn.formType=:formType and " +
					"fn.formDate=:formDate order by fn.FormNumberId desc";
			ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
			QueryParameter param = new QueryParameter();
			param.setName("formType");
			param.setValue(formType);
			param.setHbType(Hibernate.STRING);
			paramList.add(param);
			
			QueryParameter param2 = new QueryParameter();
			param2.setName("formDate");
			param2.setValue(getThisYearMonthDate());
			param2.setHbType(Hibernate.STRING);
			paramList.add(param2);

			List aList =addi.parameterQuery(seq,paramList);	
			if(aList == null || aList.size() == 0){
				returnStr = "001";
				
			}else{
				FormNumber afnf = (FormNumber)aList.get(0);
				int oldSeq = afnf.getFormSeq().intValue();
				int newSeq = oldSeq+1;
				int seqLength = Integer.toString(newSeq).length();
				if(seqLength <3){
					int moreZerolength = 3-seqLength;
					String moreZeroStr = "";
					for(int i=1;i<=moreZerolength;i++){
						moreZeroStr +="0";
					}
					returnStr = moreZeroStr+newSeq;
				}else{
					returnStr = Integer.toString(newSeq);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnStr;
	}
	
	

	private static String findNewVoucherSeq(String payType,String feeDate){
		String returnStr = "";
		AllDefaultDaoImp addi = new AllDefaultDaoImp();
		String formType="V"+payType;
		try{
			String seq="from FormNumber as fn where fn.formType=:formType and fn.formDate=:formDate order by fn.FormNumberId desc";
			ArrayList<QueryParameter> paramList = new ArrayList<QueryParameter>();
			QueryParameter param = new QueryParameter();
			param.setName("formType");
			param.setValue(formType);
			param.setHbType(Hibernate.STRING);
			paramList.add(param);
			
			QueryParameter param2 = new QueryParameter();
			param2.setName("formDate");
			param2.setValue(feeDate);
			param2.setHbType(Hibernate.STRING);
			paramList.add(param2);
			
			List aList =addi.parameterQuery(seq,paramList);	
			if(aList == null || aList.size() == 0){
				returnStr = "001";
				FormNumber fn = new FormNumber();
				fn.setFormType(formType);
				fn.setFormDate(feeDate);
				fn.setFormSeq(new Integer("001"));
				addi.insert(fn);
			}else{
				FormNumber afnf = (FormNumber)aList.get(0);
				int oldSeq = afnf.getFormSeq().intValue();
				int newSeq = oldSeq+1;
				int seqLength = Integer.toString(newSeq).length();
				if(seqLength <3){
					int moreZerolength = 3-seqLength;
					String moreZeroStr = "";
					for(int i=1;i<=moreZerolength;i++){
						moreZeroStr +="0";
					}
					returnStr = moreZeroStr+newSeq;
				}else{
					returnStr = Integer.toString(newSeq);
				}
				afnf.setFormSeq(new Integer(returnStr));
				addi.update(afnf);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnStr;
	}
	
	public static void main(String[] args){
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
		
}
