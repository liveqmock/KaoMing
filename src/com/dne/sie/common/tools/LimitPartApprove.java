package com.dne.sie.common.tools;

import java.util.ArrayList;

import com.dne.sie.util.bo.CommBo;


/**
 * 判断零件是否为受控零件,是,则返回审批角色
 * @author HaoShuang
 *
 */
public class LimitPartApprove extends CommBo{
	
	/**
	 * 判断零件是否为受控零件,是,则返回审批角色
	 * @param warrantyType 保修类型
	 * @param factoryCategory 厂商类别
	 * @param modelCode 机型号
	 * @param partCode 零件编号
	 * @return 审批角色
	 */
	public String[] getApproveRoles(String warrantyType , Long factoryCategory ,String modelCode,String partCode ){
		
		Object[] result = new String[5] ;
		int len = 0;		
		
		String hql=" select lt.roleRc,lt.roleTsd,lt.roleCrpc,lt.roleControl,lt.roleFactory from TsLimitPartApproveForm lt where 1=1";
		
		if(warrantyType!=null && !warrantyType.equals("")){
			hql += " and ( lt.warrantyType='"+warrantyType.trim()+"' or lt.warrantyType is null) ";
		}

		if(factoryCategory!=null && !factoryCategory.equals("")){
			hql += " and ( lt.factoryCategory ="+factoryCategory+" or lt.factoryCategory is null )";
		}
		 
		if( modelCode!=null && !modelCode.equals("")){
			hql += " and ( lt.modelCode ='"+modelCode+"' or lt.modelCode is null)";
			hql += " and ( lt.bizGroupCode = (select bgc.bizGroupCode from ModelForm bgc where bgc.modelCode='"+modelCode+"') or lt.bizGroupCode is null)";			
			hql += " and ( lt.d6Code = (select m6dmf.d6Code from Model6DMappingForm m6dmf where m6dmf.modelCode='"+modelCode+"') or lt.d6Code is null)";
		}
		
		if( partCode!=null && !partCode.equals("")){
			hql += " and ( lt.partCode ='"+partCode+"' or lt.partCode is null)";
			hql += " and (lt.minorCat = (select pi.minorCat from PartInfoForm pi where pi.partCode='"+partCode+"') or lt.minorCat is null)";
			hql += " and (lt.majorCat = (select pi.majorCat from PartInfoForm pi where pi.partCode='"+partCode+"') or lt.majorCat is null)";
			hql += " and (lt.priceFrom <=(select pi.stdCost from PartInfoForm pi where pi.partCode='"+partCode+"') or lt.priceFrom is null)"; 
			hql += " and (lt.priceTo >=(select pi.stdCost from PartInfoForm pi where pi.partCode='"+partCode+"' ) or lt.priceTo is null)" ;
		}
		hql += " and lt.delFlag=0 order by lt.createDate";
		try{
		
			ArrayList alist = (ArrayList)this.getDao().list(hql);	
			if(alist!=null&&alist.size()>0){
				result = (Object[])alist.get(0);
			}	
			else
				return null;
			
		}catch(Exception e){
			e.printStackTrace();	
		}
				
		
		
		for(int i=0;i<result.length;i++){						
			if(result[i]!=null&&!result[i].toString().equals("")){
				len ++;							
			}
		}
		String[] approveRoles = new String[len];
		int j=0;
		for(int i=0;i<result.length;i++){						
			if(result[i]!=null&&!result[i].toString().equals("")){
				if(i == 0){//为RC
					approveRoles[j] = "95";
				}
				if(i == 1){//为TSD
					approveRoles[j] = "96";
				}
				if(i == 2){//为CRPC
					approveRoles[j] = "97";
				}
				if(i == 3){//为control
					approveRoles[j] = "98";
				}				
				if(i == 4){//为工厂特殊处理下
					approveRoles[j] = "99";
				}
				j++;
			}
		}
		return approveRoles ;
	}
	
	public static void main(String[] args){
		LimitPartApprove lpa = new LimitPartApprove();
		lpa.getApproveRoles("I",new Long(0),"DSC-W30","A1166044A");
	}


}
