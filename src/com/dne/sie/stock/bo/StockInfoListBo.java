package com.dne.sie.stock.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.dne.sie.common.tools.DicInit;
import com.dne.sie.common.tools.ExcelEdit;
import com.dne.sie.common.tools.FormNumberBuilder;
import com.dne.sie.common.tools.Operate;

import com.dne.sie.maintenance.form.FormNumber;
import com.dne.sie.maintenance.form.PartInfoForm;
import com.dne.sie.stock.form.StockFlowForm;
import com.dne.sie.stock.form.StockInfoForm;
import com.dne.sie.stock.queryBean.StockInfoListQuery;
import com.dne.sie.stock.queryBean.StockInfoQuery;
import com.dne.sie.stock.queryBean.StockFlowQuery;
import com.dne.sie.stock.queryBean.StockTaxInfoListQuery;
import com.dne.sie.util.bo.CommBo;



/**
 * �����Ϣ����BO������
 * @author xt
 * @version 1.1.5.6
 * @see StockInfoListBo.java <br>
 */
public class StockInfoListBo extends CommBo {
	private static Logger logger = Logger.getLogger(StockInfoListBo.class);

	private static final StockInfoListBo INSTANCE = new StockInfoListBo();
		
	private StockInfoListBo(){
	}
	
	public static final StockInfoListBo getInstance() {
	   return INSTANCE;
	}
	
	/**
	 * ��ѯ����skuCode����ļ�¼
	 * @param StockInfoForm ��ѯ����
	 * @return ArrayList ��ѯ���
	 */
	public ArrayList stockInfoList(StockInfoForm sifQuery) {
		
		ArrayList alData = new ArrayList();
		StockInfoListQuery uq = new StockInfoListQuery(sifQuery);
		int count = 0;
		try {
			List dataList = uq.doListQuery();
			//count = uq.doCountQuery();
			count = dataList.size();
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = (Object[])dataList.get(i);
				String[] data = new String[6];
				data[0] = obj[0]==null?"":obj[0].toString();
				data[1] = obj[1]==null?"":obj[1].toString();
				data[2] = obj[2]==null?"":obj[2].toString();
				data[3] = obj[3]==null?"":Operate.roundF(Float.parseFloat(obj[3].toString()),2)+"";
				data[4] = obj[4]==null?"":Operate.roundF(Float.parseFloat(obj[4].toString()),2)+"";
				data[5] = DicInit.getSystemName("SKU_TYPE",(String)obj[5]);
				
				alData.add(data);
			}
			alData.add(0, count + "");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return alData;
	}
	

	/**
	 * ��ѯĳskuCode�ڿ���еľ����¼
	 * @param StockInfoForm ��ѯ����
	 * @return ArrayList[] ��ѯ���
	 */
	public ArrayList[] skuViewList(StockInfoForm sifQuery) {
		
		ArrayList alData[] = new ArrayList[2];
		ArrayList viewList = new ArrayList();
		ArrayList skuInfoList = new ArrayList();
		StockInfoQuery siq = new StockInfoQuery(sifQuery);

		int count = 0;
		try {
			List dataList = siq.doListQuery();
			//count = siq.doCountQuery();
			if(dataList!=null){
				count = dataList.size();
				StockInfoForm sif=new StockInfoForm();
				for (int i = 0; i < dataList.size(); i++) {
					sif = (StockInfoForm)dataList.get(i);
					String[] data = new String[18];
					data[0] = sif.getStockId().toString();
					data[1] = sif.getSkuCode();
					data[2] = sif.getShortCode();
					data[3] = sif.getStuffNo();
					data[4] = sif.getSkuNum()==null?"0":sif.getSkuNum().toString();
					data[5] = sif.getSkuUnit();
					data[6] = sif.getPerCost()==null?"":Operate.roundF(sif.getPerCost(),2)+"";
					data[7] = sif.getStandard();
					data[8] = sif.getCreateDate()==null?"":sif.getCreateDate().toString();
					//if(data[8].indexOf(" ")!=-1) data[8] = data[8].substring(0,data[8].indexOf(" "));
					data[9] = DicInit.getSystemName("STOCK_STATUS",sif.getStockStatus());
					data[10] = sif.getRemark();
					data[11] = sif.getSkuType();
					data[12] = "";
					data[13] = "";
					data[14] = sif.getOrderDollar()==null?"":Operate.roundF(sif.getOrderDollar(),2)+"";
					data[15] = sif.getInvoiceNo();
					data[16] = sif.getSkuNumTax()==null?"":sif.getSkuNumTax()+"";
					data[17] = sif.getPerCostTax()==null?"":Operate.roundF(sif.getPerCostTax(),2)+"";
					
					if("R".equals(sif.getStockStatus())) {
						if("S".equals(sif.getSkuType())){	//���۱������
							Object[] temps = this.getFormNo(sif.getRequestId());
							if(temps!=null){
								data[12]=(String)temps[0];
								data[13]=(String)temps[1];
							}
						}else if("L".equals(sif.getSkuType())){	//Я���������
							Object[] temps = this.getRepairFormNo(sif.getRequestId());
							if(temps!=null){
								data[12]=(String)temps[0];
								data[13]=(String)temps[1];
							}
						}
					}
					
					if(i==0){
						String[] skuInfo=new String[3];
						skuInfo[0]=sif.getSkuCode();
						skuInfo[1]=DicInit.getSystemName("SKU_TYPE",sif.getSkuType());
						skuInfo[2]=sif.getStuffNo();
						skuInfoList.add(skuInfo);
					}
					
					viewList.add(data);
				}
				viewList.add(0, count + "");
				alData[0]=viewList;
				alData[1]=skuInfoList;

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return alData;
	}
	
	

	/**
	 * ��ѯĳskuCode�ڿ���еľ����¼
	 * @param StockInfoForm ��ѯ����
	 * @return ArrayList[] ��ѯ���
	 */
	public ArrayList[] skuFlowList(StockFlowForm sffQuery) {
		
		ArrayList alData[] = new ArrayList[2];
		ArrayList viewList = new ArrayList();
		ArrayList skuInfoList = new ArrayList();
		StockFlowQuery sfq = new StockFlowQuery(sffQuery);

		int count = 0;
		try {
			List dataList = sfq.doListQuery();
			//count = siq.doCountQuery();
			if(dataList!=null){
				count = dataList.size();
				StockFlowForm sff=null;
				for (int i = 0; i < dataList.size(); i++) {
					sff = (StockFlowForm)dataList.get(i);
					String[] data = new String[10];
					data[0] = sff.getFlowId().toString();
					data[1] = sff.getCreateDate().toString();
					data[2] = sff.getSkuUnit();
					data[3] = sff.getPerCost()==null?"":Operate.roundF(sff.getPerCost(),2)+"";
					data[4] = sff.getRemark();
					data[5] = "";
					data[6] = "";
					if(sff.getFlowType().equals("I"))
						data[5] = sff.getSkuNum()==null?"":sff.getSkuNum().toString();
					else if(sff.getFlowType().equals("O"))
						data[6] = sff.getSkuNum()==null?"":sff.getSkuNum().toString();
					data[7] = sff.getRestNum()==null?"":sff.getRestNum().toString();
					data[8] = sff.getSkuType();
					data[9] = sff.getOrderDollar()==null?"":Operate.roundF(sff.getOrderDollar(),2)+"";
					
					if(i==0){
						String[] skuInfo=new String[3];
						skuInfo[0]=sff.getSkuCode();
						skuInfo[1]=DicInit.getSystemName("SKU_TYPE",sff.getSkuType());
						skuInfo[2]=sff.getStuffNo();
						skuInfoList.add(skuInfo);
					}
					
					viewList.add(data);
				}
				viewList.add(0, count + "");
				alData[0]=viewList;
				alData[1]=skuInfoList;

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return alData;
	}
	

	/**
	 *  ��ñ�����������۵��źͿͻ�
	 * @param Long ���۵�pk
	 * @return String ��ѯ���
	 */
	public Object[] getFormNo(Long request) {
		Object[] obj=null;
		try {
			obj=(Object[])this.getDao().uniqueResult("select si.saleNo,si.customerName " +
				"from SaleInfoForm si,SaleDetailForm sdf where si.saleNo=sdf.saleNo " +
				" and sdf.saleDetailId="+request);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;

	}
	public Object[] getRepairFormNo(Long request) {
		Object[] obj=null;
		try {
			obj=(Object[])this.getDao().uniqueResult("select si.serviceSheetNo,si.customerName " +
				"from RepairSearchForm si,RepairPartForm sdf where si.repairNo=sdf.repairNo " +
				" and sdf.partsId="+request);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;

	}
	
	
	
	/**
	 * ��������Ŀ��excel�ļ����ٲ������ݿ�
	 * @param String �ļ���
	 * @param String �����û���id
	 * @return �ɹ�����������
	 */
	public ArrayList stockInitialParse(String strPath, Long user) {
		ArrayList alRet = new ArrayList();
		ArrayList parseData = new ArrayList();
		int tag = -1;
		try {
			ExcelEdit ee = new ExcelEdit();

			strPath = Operate.getSysPath() + "affix/upTemp/" + strPath;
			//logger.error("-----------op.getSysPath()="+strPath);

			ArrayList alData = ee.excelParse(strPath)[0];


			String[] dataNull = new String[7];
			String[] dataErr = new String[2];
			
			for (int i = 0; i < alData.size(); i++) {

				String[] temp = (String[]) alData.get(i);

				if (!temp[0].equals("")) {
					//System.out.println("----"+i+"--------temp="+temp[0]);
					StockInfoForm ppf = new StockInfoForm();


					//temp[0]-�������
					if (!temp[0].equals("")) {
						ppf.setSkuCode(temp[0]);
					} else {
						if(dataNull[0]==null) dataNull[0] = (i + 2)+"";
						else dataNull[0] += "," + (i + 2);
					}
					//temp[1]-�������
					if (!"".equals(temp[1])) {
						if (Operate.isPositiveInteger(temp[1])) {
							if (Integer.parseInt(temp[1])>=0)
								ppf.setSkuNum(new Integer(temp[1]));
							else{
								if(dataErr[0]==null) dataErr[0] = (i + 2)+"";
								else dataErr[0] += "," + (i + 2);
							}
						} else {
							if(dataErr[0]==null) dataErr[0] = (i + 2)+"";
							else dataErr[0] += "," + (i + 2);
						}
					} else {
//						if(dataNull[5]==null) dataNull[5] = (i + 2)+"";
//						else dataNull[5] += "," + (i + 2);
						ppf.setSkuNum(new Integer(0));
					}
					
					
					//temp[2]-�Ϻ�
					if (!temp[2].equals("")) {
						ppf.setStuffNo(temp[2]);
					} else {
//						if(dataNull[3]==null) dataNull[3] = (i + 2)+"";
//						else dataNull[3] += "," + (i + 2);
						ppf.setStuffNo("N");
					}
					//temp[3]-�۸�US��
					if (!"".equals(temp[3])) {
						if (Operate.isNumeric(temp[3])) {
							ppf.setPerCost(new Float(temp[3]));
						} else {
							if(dataErr[1]==null) dataErr[1] = (i + 2)+"";
							else dataErr[1] += "," + (i + 2);
						}
					} 
					

					//temp[4]-��ע
					if (!temp[4].equals("")) {
						ppf.setRemark(temp[4]);
					} 
					//temp[5]-��λ
					if (!temp[5].equals("")) {
						ppf.setSkuUnit(temp[5]);
					} 
					
					ppf.setStockStatus("A");
					ppf.setCreateBy(user);

					parseData.add(ppf);
				} else {
					continue;
				}

			} //end for

			boolean chkNull = false;
			
			//У���ϴ����ݵĺϷ���
			for(int i=0;i<dataNull.length;i++){
				if (dataNull[i]!=null) {
					chkNull = true;
					break;
				}
			}
			
			if (chkNull) { //�п�ֵ
				tag = -2;
				alRet.add(new Integer(tag));
				alRet.add(dataNull);
			} else if (dataErr[0]!=null) { //�������Ϸ�
				tag = -3;
				alRet.add(new Integer(tag));
				alRet.add(dataErr);
			} else if (this.parseInsert(parseData)) { //�������ݿ�ɹ�
				tag  = parseData.size();
				alRet.add(new Integer(tag));
			} else { //�������ݿ�ʧ��
				tag  = -1;
				alRet.add(new Integer(tag));
			}
		} catch (Exception e) {
			alRet.add(new Integer(-1));
			e.printStackTrace();
		} finally {
			//ɾ���ϴ���excel�ļ�
			String[] filePath = { strPath };
			Operate.fileDelete(filePath);
		}

		return alRet;
	}
	
	/**
	 * ɾ����ȥ�Ŀ�����ݣ������µ�
	 * @param ArrayList �µ�����
	 * @return �Ƿ�ɹ�
	 */
	public boolean parseInsert(ArrayList parseData) throws Exception{
		boolean booRet = false;
		ArrayList al = new ArrayList();
		
		Object[] obj1={"delete from StockInfoForm","e"};
		al.add(obj1);
		for(int i=0;i<parseData.size();i++){
			Object[] obj={parseData.get(i),"i"};
			al.add(obj);
		}
		booRet = this.getBatchDao().allDMLBatch(al);
		return booRet;
	}
	
	

	/**
	 * ��������Ŀ����ˮexcel�ļ����ٲ������ݿ�
	 * @param String �ļ���
	 * @param String �����û���id
	 * @return �ɹ�����������
	 */
	public ArrayList stockFlowInitialParse(String strPath, Long user) {
		ArrayList alRet = new ArrayList();
		ArrayList parseData = new ArrayList();
		int tag = -1;
		try {
			ExcelEdit ee = new ExcelEdit();

			strPath = Operate.getSysPath() + "affix/upTemp/" + strPath;
			//logger.error("-----------op.getSysPath()="+strPath);

			ArrayList[] alData = ee.excelParse(strPath);

			String[] dataNull = new String[5];
			String[] dataErr = new String[1];
			
			if(alData.length>0&&alData[0].size()>0){
				this.getDao().execute("delete from FormNumber t where t.formType='F'");
			}
			int count=10;
			//alData[0]:�����ˮ��alData[1]:������ˮ
			for(int x=0;x<alData.length;x++){	
				for (int i = 0; i < alData[x].size(); i++) {
					String[] temp = (String[]) alData[x].get(i);
					
					//���,���������ݣ���ʾ������Ч��������
					if (!temp[0].equals("")&&!temp[1].equals("")) {
						//System.out.println("----"+i+"--------temp="+temp[0]);
						StockFlowForm sff = new StockFlowForm();
						
	
						//temp[1]-����
						if (!temp[1].equals("")) {
							sff.setCreateDate(Operate.toDate(temp[1]));
						} else {
							if(dataNull[0]==null) dataNull[0] = (i + 2)+"";
							else dataNull[0] += "," + (i + 2);
						}
						//temp[2]-�ͻ�
						if (!temp[2].equals("")) {
							sff.setCustomerName(temp[2]);
						} else {
							if(dataNull[1]==null) dataNull[1] = (i + 2)+"";
							else dataNull[1] += "," + (i + 2);
						}
						//temp[3]-�������
						if (!temp[3].equals("")) {
							sff.setSkuCode(temp[3]);
						} else {
							if(dataNull[2]==null) dataNull[2] = (i + 2)+"";
							else dataNull[2] += "," + (i + 2);
						}
						//temp[4]-�Ϻ�
						if (!temp[4].equals("")) {
							sff.setStuffNo(temp[4]);
						} else {
//							if(dataNull[3]==null) dataNull[3] = (i + 2)+"";
//							else dataNull[3] += "," + (i + 2);
							sff.setStuffNo("N");
						}
						
						//temp[5]-�������
						if (!"".equals(temp[5])) {
							if (Operate.isPositiveInteger(temp[5])) {
								if (Integer.parseInt(temp[5])>=0)
									sff.setSkuNum(new Integer(temp[5]));
								else{
									if(dataErr[0]==null) dataErr[0] = (i + 2)+"";
									else dataErr[0] += "," + (i + 2);
								}
							} else {
								if(dataErr[0]==null) dataErr[0] = (i + 2)+"";
								else dataErr[0] += "," + (i + 2);
							}
						} else {
//							if(dataNull[4]==null) dataNull[4] = (i + 2)+"";
//							else dataNull[4] += "," + (i + 2);
							sff.setSkuNum(new Integer(0));
						}
	
						//temp[6]-��λ
						sff.setSkuUnit(temp[6]);
						//temp[7]-��ע
						sff.setRemark(temp[7]);
						
						if(x==0){
							sff.setFlowType("I");
						}else{
							sff.setFlowType("O");
						}
						sff.setFlowItem("H");	//��ʷ��ˮ
						sff.setCreateBy(user);
						sff.setFlowId(new Long(count++));
						
	
						parseData.add(sff);
					} else {
						continue;
					}
	
				} //end for
			}
			boolean chkNull = false;
			
			//У���ϴ����ݵĺϷ���
			for(int i=0;i<dataNull.length;i++){
				if (dataNull[i]!=null) {
					chkNull = true;
					break;
				}
			}
			
			if (chkNull) { //�п�ֵ
				tag = -2;
				alRet.add(new Integer(tag));
				alRet.add(dataNull);
			} else if (dataErr[0]!=null) { //�������Ϸ�
				tag = -3;
				alRet.add(new Integer(tag));
				alRet.add(dataErr);
			} else if (this.parseFlowInsert(parseData,count)) { //�������ݿ�ɹ�
				tag  = parseData.size();
				alRet.add(new Integer(tag));
			} else { //�������ݿ�ʧ��
				tag  = -1;
				alRet.add(new Integer(tag));
			}
		} catch (Exception e) {
			alRet.add(new Integer(-1));
			e.printStackTrace();
		} finally {
			//ɾ���ϴ���excel�ļ�
			String[] filePath = { strPath };
			Operate.fileDelete(filePath);
		}

		return alRet;
	}
	
	

	/**
	 * ɾ����ȥ�Ŀ����ˮ���ݣ������µ�
	 * @param ArrayList �µĿ����ˮ����
	 * @return �Ƿ�ɹ�
	 */
	public boolean parseFlowInsert(ArrayList parseData,int count) throws Exception{
		boolean booRet = false;
		ArrayList al = new ArrayList();
		

		FormNumber fn = new FormNumber();
		fn.setFormType("F");
		fn.setFormDate(Operate.getDate("yyMMdd"));
		fn.setFormSeq(new Integer(count));
		Object[] obj1={fn,"i"};
		al.add(obj1);
		
		Object[] obj2={"delete from StockFlowForm","e"};
		al.add(obj2);
		
		for(int i=0;i<parseData.size();i++){
			Object[] obj={parseData.get(i),"i"};
			al.add(obj);
		}
		
		booRet = this.getBatchDao().allDMLBatch(al);
		return booRet;
	}
	

	/**
	 * ������������excel�ļ����ٲ������ݿ�
	 * @param String �ļ���
	 * @return �ɹ�����������
	 */
	public ArrayList partInitialParse(String strPath) {
		ArrayList alRet = new ArrayList();
		ArrayList parseData = new ArrayList();
		int tag = -1;
		try {
			ExcelEdit ee = new ExcelEdit();

			strPath = Operate.getSysPath() + "affix/upTemp/" + strPath;

			ArrayList alData = ee.excelParse(strPath)[0];


			String[] dataNull = new String[8];
			String[] dataErr = new String[2];
			ArrayList stuffList=new ArrayList();
			int repeatCount=1;
			for (int i = 0; i < alData.size(); i++) {

				String[] temp = (String[]) alData.get(i);
				if (!temp[0].equals("")) {
					PartInfoForm ppf = new PartInfoForm();

					//temp[0]-�Ϻ�
					if (!temp[0].equals("")) {
						if(!stuffList.contains(temp[0])){
							stuffList.add(temp[0]);
						}else{
							temp[0]=temp[0]+"-"+(repeatCount++);
							stuffList.add(temp[0]);
							//System.out.println("-------temp[0]="+temp[0]);
						}
						ppf.setStuffNo(temp[0]);
					} else {
						if(dataNull[0]==null) dataNull[0] = (i + 2)+"";
						else dataNull[0] += "," + (i + 2);
					}
					//temp[1]-�������
					if (!temp[1].equals("")) {
						ppf.setSkuCode(temp[1]);
					} else {
						if(dataNull[1]==null) dataNull[1] = (i + 2)+"";
						else dataNull[1] += "," + (i + 2);
					}
					//temp[2]-��λ
					ppf.setSkuUnit(temp[2]);
					//temp[3]-����
					if (!"".equals(temp[3])) {
						if (Operate.isNumeric(temp[3])) {
							if (Double.parseDouble(temp[3])>=0)
								ppf.setBuyCost(new Double(temp[3]));
							else{
								if(dataErr[0]==null) dataErr[0] = (i + 2)+"";
								else dataErr[0] += "," + (i + 2);
							}
						} else {
							if(dataErr[0]==null) dataErr[0] = (i + 2)+"";
							else dataErr[0] += "," + (i + 2);
						}
					} 
					//temp[4]-��ע 
					ppf.setRemark(temp[4]);
					
					/*
					//temp[2]-���
					if (!temp[2].equals("")) {
						ppf.setShortCode(temp[2]);
					} else {
						if(dataNull[2]==null) dataNull[2] = (i + 2)+"";
						else dataNull[2] += "," + (i + 2);
					}
					//temp[3]-���
					ppf.setStandard(temp[3]);
					//temp[4]-��λ
					ppf.setSkuUnit(temp[4]);
					
					//temp[5]-����
					if (!"".equals(temp[5])) {
						if (Operate.isNumeric(temp[5])) {
							if (Double.parseDouble(temp[5])>=0)
								ppf.setBuyCost(new Double(temp[5]));
							else{
								if(dataErr[0]==null) dataErr[0] = (i + 2)+"";
								else dataErr[0] += "," + (i + 2);
							}
						} else {
							if(dataErr[0]==null) dataErr[0] = (i + 2)+"";
							else dataErr[0] += "," + (i + 2);
						}
					} 
					
					//temp[6]-����	
					if (!"".equals(temp[6])) {
						if (Operate.isNumeric(temp[6])) {
							if (Double.parseDouble(temp[6])>=0)
								ppf.setSaleCost(new Double(temp[6]));
							else{
								if(dataErr[1]==null) dataErr[1] = (i + 2)+"";
								else dataErr[1] += "," + (i + 2);
							}
						} else {
							if(dataErr[1]==null) dataErr[1] = (i + 2)+"";
							else dataErr[1] += "," + (i + 2);
						}
					} 

					//temp[7]-��ע 
					ppf.setRemark(temp[7]);
					*/
					parseData.add(ppf);
				} else {
					continue;
				}

			} //end for

			boolean chkNull = false,chkErr=false;
			
			//У���ϴ����ݵĺϷ���
			for(int i=0;i<dataNull.length;i++){
				if (dataNull[i]!=null) {
					chkNull = true;
					break;
				}
			}
			for(int i=0;i<dataErr.length;i++){
				if (dataErr[i]!=null) {
					chkErr = true;
					break;
				}
			}
			if (chkNull) { //�п�ֵ
				tag = -2;
				alRet.add(new Integer(tag));
				alRet.add(dataNull);
			} else if (chkErr) { //�۸񲻺Ϸ�
				tag = -3;
				alRet.add(new Integer(tag));
				alRet.add(dataErr);
			} else if (this.parseInsertPart(parseData)) { //�������ݿ�ɹ�
				tag  = parseData.size();
				alRet.add(new Integer(tag));
			} else { //�������ݿ�ʧ��
				tag  = -1;
				alRet.add(new Integer(tag));
			}
		} catch (Exception e) {
			e.printStackTrace();
			alRet.add(new Integer(-1));
		} finally {
			//ɾ���ϴ���excel�ļ�
			String[] filePath = { strPath };
			Operate.fileDelete(filePath);
		}

		return alRet;
	}
	
	/**
	 * ɾ����ȥ�Ŀ�����ݣ������µ�
	 * @param ArrayList �µ�����
	 * @return �Ƿ�ɹ�
	 */
	public boolean parseInsertPart(ArrayList parseData) throws Exception{
		boolean booRet = false;
		ArrayList al = new ArrayList();
		
		Object[] obj1={"delete from PartInfoForm","e"};
		al.add(obj1);
		for(int i=0;i<parseData.size();i++){
			Object[] obj={parseData.get(i),"i"};
			al.add(obj);
		}
		booRet = this.getBatchDao().allDMLBatch(al);
		return booRet;
	}
		
	public ArrayList stockInfoTaxList(StockInfoForm sifQuery) {
		
		ArrayList alData = new ArrayList();
		StockTaxInfoListQuery uq = new StockTaxInfoListQuery(sifQuery);
		int count = 0;
		float allCost = 0;
		try {
			List dataList = uq.doListQuery();
			//count = uq.doCountQuery();
			count = dataList.size();
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = (Object[])dataList.get(i);
				String[] data = new String[4];
				data[0] = obj[0]==null?"":obj[0].toString();
				data[1] = obj[1]==null?"":obj[1].toString();
				data[2] = obj[2]==null?"":obj[2].toString();
				if(obj[3]!=null){
					data[3] = Operate.roundF(Float.parseFloat(obj[3].toString()),2)+"";
					allCost+=Float.parseFloat(obj[3].toString());
				}
				alData.add(data);
			}
			alData.add(0, count + "");
			alData.add(1, Operate.roundF(allCost,2) + "");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return alData;
	}


	public Long getAvailableStockNum(String stuffNo) throws Exception{
		String hql = "select sum(si.skuNum) from StockInfoForm si where si.stuffNo=? and si.stockStatus='A'";
		return (Long)this.getDao().uniqueResult(hql,stuffNo);
	}
	public Long getAvailableStockToolsNum(String stuffNo) throws Exception{
		String hql = "select sum(si.skuNum) from StockToolsInfoForm si where si.stuffNo=? and si.stockStatus='A'";
		return (Long)this.getDao().uniqueResult(hql,stuffNo);
	}
	
	
}