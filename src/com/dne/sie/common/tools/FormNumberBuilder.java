package com.dne.sie.common.tools;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import com.dne.sie.maintenance.form.FormNumber;
import com.dne.sie.util.hibernate.AllDefaultDaoImp;
import com.dne.sie.util.query.QueryParameter;


/**
 * 生成唯一单号类
 * @author HaoShuang
 */
public class FormNumberBuilder {
	private static Logger logger = Logger.getLogger(FormNumberBuilder.class);

	/**
	 * 构造函数
	 */
	public FormNumberBuilder() {
		super();
	}
	

	/**
	 * 销售单号
	 * 规则为PI+年月日(6位)+序列号(3位)+客户ID(4位)
	 *  PI080725003SI
	 * @param custId String 客户代码
	 * @return 生成的单号
	 */
	public synchronized static String getNewSaleFormNumber(String custId) throws Exception{
		String newSeq = getNewSeq("S");
		return "PI"+getThisYearMonthDate()+newSeq+custId;
	}
	
	

	/**
	 * 订购单号
	 * ( 旧规则为P+年月日(6位)+序列号(3位)+发货地(2位)+"-"+运输方式 P080423001SH-ZL )
	 *  规则为P+年月日(6位)+序列号(3位)+发货地(2位) P080423001SH
	 * @return 生成的单号
	 */
	public static synchronized String getOrderNo(String delivery)throws Exception{
		
		String newSeq = getNewSeq("P");
		return "P"+getThisYearMonthDate()+newSeq+delivery;
	}

	/**
	 * 显示下一个订购单号
	 *  规则为P+年月日(6位)+序列号(3位)+发货地(2位)
	 * @return 显示的单号
	 */
	public static String findOrderNo(String delivery)throws Exception{
	
		return "P"+getThisYearMonthDate()+findNewSeq("P")+delivery;
		
	}


	/**
	 * 获取新的流水号
	 * @return 库存流水号
	 */
	public static synchronized Long getStockFlowId()throws Exception{
		String fNo=getNewSeq("F");
		
		return new Long(fNo);
	}

	/**
	 * 出货单号
	 * 规则为H+年月日(6位)+序列号(3位)+客户ID(4位)
	 * @param custId String 客户代码
	 * @return 生成的单号
	 */
	public synchronized static String getNewAsnFormNumber(String custId) throws Exception{
		String newSeq = getNewSeq("H");
		return "H"+getThisYearMonthDate()+newSeq+custId;
	}

	/**
	 * 获取新的凭证号
	 *  规则为payType+年月日(6位)+序列号(6位)
	 * @return 生成的凭证号
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
	 * 显示下一个出货单号
	 *  规则为H+年月日(6位)+序列号(3位)+客户ID(4位)
	 * @return 显示的单号
	 */
	public static String findNewAsnFormNumber(String custId)throws Exception{
	
		String newSeq = findNewSeq("H");
		return "H"+getThisYearMonthDate()+newSeq+custId;
		
	}
	
	
	/**
	 * 显示下一个维修单号
	 *  规则为RS+年月日(6位)+序列号(3位)
	 * @return 显示的单号
	 */
	public static String findNewServiveSheetNo()throws Exception{
	
		return "RS"+getThisYearMonthDate()+findNewSeq("R");
		
	}
	
	/**
	 * 生成下一个维修单号
	 *  规则为RS+年月日(6位)+序列号(3位)
	 * @return 显示的单号
	 */
	public synchronized static String getNewServiveSheetNo()throws Exception{
	
		return "RS"+getThisYearMonthDate()+getNewSeq("R");
		
	}
	/**
	 * 生成下一个维修单号(安调)
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
