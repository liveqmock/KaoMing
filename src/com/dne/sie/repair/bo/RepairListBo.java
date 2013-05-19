package com.dne.sie.repair.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.dne.sie.common.tools.CommonSearch;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.maintenance.form.IrisCodeForm;
import com.dne.sie.repair.form.RepairFeeInfoForm;
import com.dne.sie.repair.form.RepairIrisInfoForm;
import com.dne.sie.repair.form.RepairPartForm;
import com.dne.sie.repair.form.RepairSearchForm;
import com.dne.sie.repair.form.RepairServiceForm;
import com.dne.sie.repair.queryBean.RepairListQuery;
import com.dne.sie.util.bo.CommBo;

public class RepairListBo extends CommBo {
	private static Logger logger = Logger.getLogger(RepairListBo.class);

	private static final RepairListBo INSTANCE = new RepairListBo();
		
	private RepairListBo(){
	}
	
	public static final RepairListBo getInstance() {
	   return INSTANCE;
	}
	

	/**
	 * 维修单查询
	 * @param serviceOrder 查询条件
	 * @return 查询结果
	 */
	public ArrayList getRepairQueryList(RepairSearchForm serviceOrder) throws Exception {
		
		ArrayList alData = new ArrayList();
		RepairListQuery rqlq = new RepairListQuery(serviceOrder);

		int count = 0;
		List dataList = rqlq.doListQuery(serviceOrder.getFromPage(), serviceOrder.getToPage());
		count = rqlq.doCountQuery();
		RepairSearchForm rsf = null;
		CommonSearch cs = CommonSearch.getInstance();
		if (dataList != null) {
			for (int i = 0; i < dataList.size(); i++) {
				String[] data = new String[17];
				rsf = (RepairSearchForm) dataList.get(i);
				data[0] = rsf.getRepairNo() == null ? "" : rsf.getRepairNo().toString();
				data[1] = rsf.getServiceSheetNo();
				data[2] = rsf.getCustomerName();
				data[3] = rsf.getPhone();
				data[4] = rsf.getModelCode();
				data[5] = rsf.getSerialNo();
				data[6] = this.getRepairManName(rsf.getRepairNo());
				data[7] = rsf.getRr90()==null?"":rsf.getRr90();
				data[8] = rsf.getWarrantyType();
				data[9] = rsf.getUpdateDate() == null ? "" : Operate.formatYMDDate(rsf.getUpdateDate());
				data[10] = rsf.getActualRepairedDate() == null ? "" : Operate.formatYMDDate(rsf.getActualRepairedDate());
				data[11] = rsf.getTotalFee() == null ? "" : rsf.getTotalFee().toString();
				data[12] = rsf.getCurrentStatus() == null ? "" : rsf.getCurrentStatus();
				data[13] = rsf.getUnCompleteQuickStatus() == null ? "" : rsf.getUnCompleteQuickStatus();
				data[14] = rsf.getCreateDate() == null ? "" : Operate.formatYMDDate(rsf.getCreateDate());
				data[15] = rsf.getOperaterId()==null?"":cs.findUserNameByUserId(rsf.getOperaterId());	
				data[16] = rsf.getRepairProperites();
				alData.add(data);
			}
			alData.add(0, count + "");
		}
	
		return alData;
		
			
	}
	

	/**
	 * 在修查询
	 * @param serviceOrder 查询条件
	 * @return 查询结果
	 */
	public ArrayList getRepairList(RepairSearchForm serviceOrder) throws Exception {
		
		ArrayList alData = new ArrayList();
		RepairListQuery rqlq = new RepairListQuery(serviceOrder);

		int count = 0;
		List dataList = rqlq.doListQuery(serviceOrder.getFromPage(), serviceOrder.getToPage());
		count = rqlq.doCountQuery();
		RepairSearchForm rsf = null;
		if (dataList != null) {
			CommonSearch cs = CommonSearch.getInstance();
			for (int i = 0; i < dataList.size(); i++) {
				String[] data = new String[22];
				rsf = (RepairSearchForm) dataList.get(i);
				data[0] = rsf.getRepairNo() == null ? "" : rsf.getRepairNo().toString();
				data[1] = rsf.getServiceSheetNo();
				data[2] = rsf.getCustomerName();
				data[3] = rsf.getPhone();
				data[4] = rsf.getModelCode();
				data[5] = rsf.getSerialNo();
				data[6] = "";
				if(serviceOrder.getCurrentStatus().equals("D")){
					data[6] = this.getRepairManName(rsf.getRepairNo());
				}else if(((rsf.getWarrantyType().equals("B")||rsf.getWarrantyType().equals("C"))
						&&rsf.getCurrentStatus().equals("T")&&rsf.getLastRepairNo()!=null)){
					data[6] = this.getRepairManName(rsf.getLastRepairNo());
					if(rsf.getCustomerVisitDate()!=null){
						data[19] = Operate.formatYMDDate(rsf.getCustomerVisitDate());
						if(Operate.getDateCompare(rsf.getCustomerVisitDate())<=2){
							data[20] = "Y";
						}else{
							data[20] = "N";
						}
					}
				}
				data[7] = rsf.getRr90()== null ? "" :rsf.getRr90();
				data[8] = rsf.getWarrantyType();
				data[9] = rsf.getCustomerVisitDate() == null ? "" : Operate.formatYMDDate(rsf.getCustomerVisitDate());
				data[10] = rsf.getEstimateRepairDate() == null ? "" : Operate.formatYMDDate(rsf.getEstimateRepairDate());
				data[11] = rsf.getTotalFee() == null ? "" : rsf.getTotalFee().toString();
				data[12] = rsf.getCurrentStatus();
				data[13] = rsf.getLinkman();
				data[14] = rsf.getExpectedDuration() == null ? "" :rsf.getExpectedDuration().toString();
				data[15] = rsf.getWarrantyCardNo();
				data[16] = rsf.getActualOnsiteDate() == null ? "" : Operate.formatYMDDate(rsf.getActualOnsiteDate());
				data[17] = rsf.getCreateBy()==null?"":cs.findUserNameByUserId(rsf.getCreateBy());		
				data[18] = rsf.getOperaterId()==null?"":cs.findUserNameByUserId(rsf.getOperaterId());	
				data[21] = rsf.getUpdateDate() == null ? "" : Operate.formatYMDDate(rsf.getUpdateDate());
				
				alData.add(data);
			}
			alData.add(0, count + "");
		}
	
		return alData;
		
			
	}
	
	/**
	 * 维修员查询拼装
	 * @param repairNo
	 * @return
	 * @throws Exception
	 */
	public String getRepairManName(Long repairNo) throws Exception {
		String tmp = "";
		String strHql="select mi.repairManName from RepairManInfoForm mi where mi.repairNo=?";
		List<String> al = this.getDao().list(strHql,repairNo);
		for(int i=0;al!=null&&i<al.size();i++){
			String name = al.get(i);
			if(i==0) tmp=name;
			else{ 
				if(tmp.indexOf(name)==-1)
					tmp= tmp+","+name;
			}
		}
		return tmp;
		
	}
	
	
	/**
	 * 根据维修单ID查询维修单详细信息
	 * @param id 维修单ID
	 * @return 维修单详细信息
	 */
	public RepairServiceForm getRepairDetail(Long id)  throws Exception {
	
		return (RepairServiceForm) this.getDao().findById(RepairServiceForm.class,id);
	}
	
	public RepairSearchForm findById(Long id)  throws Exception {
		
		return (RepairSearchForm) this.getDao().findById(RepairSearchForm.class,id);
	}
	
	public List<RepairFeeInfoForm> getRepairFeeList(Long repairNo)  throws Exception {
		String strHql = "from RepairFeeInfoForm rf where rf.repairNo=? ";
		
		
		return this.getDao().list(strHql,repairNo);
	}
	
	public RepairFeeInfoForm getRepairFeeInfo(Long repairNo)  throws Exception {
		RepairFeeInfoForm feeInfo = null;
		
		String strHql = "from RepairFeeInfoForm rf where rf.repairNo=? and rf.feeType='P' ";
		List feeList = this.getDao().list(strHql,repairNo);
		
		if(feeList !=null&&!feeList.isEmpty()){
			feeInfo = (RepairFeeInfoForm)(this.getDao().list(strHql,repairNo).get(0));
		}
		return feeInfo;
	}
	
	/**
	 * 根据维修单ID查询对应销售零件信息
	 * @param id 维修单ID
	 * @return 维修零件信息
	 */
	public List getRepairPartsList(Long id)  throws Exception {
		String strHql = "from RepairPartForm rp where rp.repairNo=? and rp.repairPartStatus in('A','S') and rp.repairPartType='W'";
		
		return this.getDao().list(strHql,id);
	}
	public List getRepairPartsList(Long id,String status)  throws Exception {
		String where = "";
		if("return".equals(status)){
			where = " and rp.repairPartStatus in ('T','B','R')";
		}
		String strHql = "from RepairPartForm rp where rp.repairNo=?  and rp.repairPartType='W' " + where;
		
		return this.getDao().list(strHql,id);
	}
	
	public List getloanPartsList(Long id)  throws Exception {
		String strHql = "from RepairPartForm rp where rp.repairNo=? and rp.repairPartStatus in('L','X') and rp.repairPartType='X'";
		
		return this.getDao().list(strHql,id);
	}
	public List getloanPartsList(Long id,String status)  throws Exception {
		String where = "";
		if("return".equals(status)){
			where = " and rp.repairPartStatus in ('L','X','S','R')";
		}
		String strHql = "from RepairPartForm rp where rp.repairNo=? and rp.repairPartType='X'" + where;
		
		return this.getDao().list(strHql,id);
	}
	
	public List getToolsList(Long id)  throws Exception {
		String strHql = "from RepairPartForm rp where rp.repairNo=? and rp.repairPartStatus in('L','X') and rp.repairPartType='T'";
		
		return this.getDao().list(strHql,id);
	}
	
	public List getToolsList(Long id,String status)  throws Exception {
		String where = "";
		if("return".equals(status)){
			where = " and rp.repairPartStatus in('L','X','R')";
		}
		String strHql = "from RepairPartForm rp where rp.repairNo=? and rp.repairPartType='T' "+where;
		
		return this.getDao().list(strHql,id);
	}
	

	/**
	 * 根据维修单号，取得维修单对应的附件信息
	 * @param repairNo 维修单号
	 * @return 附件信息列表
	 */
	public ArrayList getRepairAttachment(Long repairNo)  throws Exception {
		ArrayList alData = new ArrayList();
		
		List ls = this.getDao().list(" select aif.attachedId,aif.attachedName," 
				+ "aif.saveAttachedName,aif.fileType,aif.createDate  "
				+ " from AttachedInfoForm as aif where aif.foreignId=? "
				+ " and aif.fileType !='S' order by aif.attachedId",repairNo);
		
		for (int i = 0; ls!=null && i < ls.size(); i++) {
			Object[] obj = (Object[]) ls.get(i);
			String[] data = new String[5];
			data[0] = obj[0] == null ? "" : ((Long) obj[0]).toString();
			data[1] = obj[1] == null ? "" : (String) obj[1]; //attachedName
			data[2] = "affix/fileInfo/"+(String) obj[2]; //下载文件的相对路径
			data[3] = obj[3] == null ? "" : (String) obj[3]; //fileType	
			data[4] = obj[4] == null ? "" : Operate.formatDate((Date) obj[4]);

			alData.add(data);
		}
		
		return alData;
	}
	
	
	/**
	 * 根据零件ID查零件信息
	 * @param partId 零件ID
	 * @return 零件信息
	 */
	public RepairPartForm getRepairPartInfo(Long partId) throws Exception {
		RepairPartForm returnForm = (RepairPartForm) this.getDao().findById(RepairPartForm.class,partId);
		
		return returnForm;
	}
	
	public List<RepairIrisInfoForm> getRepairIrisList(Long repairNo)  throws Exception {
		String strHql = "from RepairIrisInfoForm rp where rp.repairNo=? ";
		
		return (List<RepairIrisInfoForm>)this.getDao().list(strHql,repairNo);
	}
	
	
	public RepairIrisInfoForm getRepairIrisByCode(List<RepairIrisInfoForm> repairIrisCodeList,Long irisCode)  throws Exception {
		for(RepairIrisInfoForm iif : repairIrisCodeList){
			if(irisCode.longValue() == iif.getIrisCodeId().longValue()){
				return iif;
			}
		}
		
		return null;
		
	}
	
	public String[] getIrisTree(List<RepairIrisInfoForm> repairIrisCodeList) throws Exception {
		String[] type = {"A","B"};	//电气部分,机械部分
		String strRet[]=new String[type.length+1];
		String strTree="";
		String json="{ \"iristree\": [{";
		//int i=0;
		
		for(int i=0;i<type.length;i++){
			String strDate="data"+type[i];
			strTree="tree"+i;
			strRet[i]="var data"+type[i]+" = new Array();\n";
			
			List irisList = CommonSearch.getInstance().getIrisInfoList();
			
			for(int j=0;j<irisList.size();j++){
				IrisCodeForm icf=(IrisCodeForm)irisList.get(j);
		
				if(icf.getIrisType().equals(type[i])){
					String strCheck="false";
					
					Long id=icf.getIrisCodeId();				
					String strName=Operate.htmlInjectionFilter(icf.getIrisName());	
					String parentCode=icf.getParentCode().toString();
					
					RepairIrisInfoForm iif = this.getRepairIrisByCode(repairIrisCodeList, icf.getIrisCodeId());
					
					if(icf.getInputFlag().equals("Y")){
						String content = "";
						if(repairIrisCodeList!=null && iif!=null&&iif.getIrisContent()!=null){
							content = iif.getIrisContent();
						}
						strRet[i]="document.write('<input name=\"iris_"+icf.getIrisCodeId()+"\" type=\"hidden\" value=\""+content+"\">'); \r\n "+strRet[i];
//						strName+="&nbsp;&nbsp;<input name=\""+icf.getIrisDesc()+"\" size=16 value=\""+content+"\" maxlength=250 >";
						strName+="&nbsp;&nbsp;<img src=\"images/i_board.gif\" onclick=showIrisDialog(\""+content+"\",\""+icf.getIrisDesc()+"\",\""+icf.getIrisName()+"\","+icf.getIrisCodeId()+")  >";
						if(json.length()>20){
							json += ",";
						}
						json += "\""+icf.getIrisCodeId()+"\":\""+content+"\"";
					}
					if(repairIrisCodeList!=null && iif!=null){
						if(iif.getIrisCodeId().longValue() == id.longValue()){
							strCheck="true";
						}
					}
		
					strRet[i]+=strDate+"["+id+"] = new Array('"+id+"','"+strName+"','"+id+"','"+parentCode+"',"+strCheck+");\n";
				}
			}
			strRet[i]+="for (var i = 0; i < "+strDate+".length; i++) { \n"+
				"if("+strDate+"[i]!=undefined){\n"+
				"if ("+strDate+"[i][3] == \"0\") { \n"+
				"eval(\"var node\" + "+strDate+"[i][0] + \"= new Tree('\" + "+strDate+"[i][1] + \"','\" + "+strDate+"[i][2] + \"')\");\n"+
				"eval(\"var "+strTree+"=node\" + "+strDate+"[i][0]);\n"+
				"}else {\n"+
				"eval(\"var node\" + "+strDate+"[i][0] + \"=new CheckBoxTreeItem('  \" + "+strDate+"[i][1] + \"','\" + "+strDate+"[i][2] + \"',null,"+strDate+"[i][4],node\" + "+strDate+"[i][3] + \")\");\n"+
				"}}}\n"+
				strTree+".expandAll();\n"+
				"document.write(\"<div id='treeContainer' >\" + "+strTree+" + \"</div>\");";
		}
		json+=" }]}";
		strRet[type.length] =  json;
		
		return strRet;
	}
	
	public String[] getIrisContent(Long repairNo) throws Exception {
		String[] irisInfo = {"",""};
		List irisList = this.getDao().list("SELECT i.irisId,c.irisName,i.irisContent,c.irisType " +
				"FROM RepairIrisInfoForm i,IrisCodeForm c where i.irisCodeId=c.irisCodeId and c.layer>1 and i.repairNo=?",repairNo);
		if(irisList!=null&&!irisList.isEmpty()){
			StringBuffer sba = new StringBuffer();
			StringBuffer sbb = new StringBuffer();
			for(int i=0;i<irisList.size();i++){
				Object[] obj = (Object[])irisList.get(i);
				
				if("A".equals(obj[3].toString())){
					sba.append(obj[1]).append("    ").append(obj[2]==null?"":(":"+obj[2])).append("<br>");
				}else if("B".equals(obj[3].toString())){
					sbb.append(obj[1]).append("    ").append(obj[2]==null?"":(":"+obj[2])).append("<br>");
				}
			}
			irisInfo[0]=sba.toString();
			irisInfo[1]=sbb.toString();
		}
		
		return irisInfo;
	}
	
	
}
