package com.dne.sie.common.tools;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.dne.sie.support.userRole.bo.RoleBo;
import com.dne.sie.support.userRole.bo.UserBo;
import com.dne.sie.support.userRole.form.RoleForm;
import com.dne.sie.support.userRole.form.UserForm;

/**
 * @author xt
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class AtomRoleCheck {
	
	public static final int 	ADMIN =0;
	public static final int 	RECEPTION =1;
	public static final int 	PIC =2;
	public static final int 	SYSTEM =3;
	public static final int 	REPORT =4;
	public static final int 	REPAIRMAN =5;
	public static final int 	REPAIRMANAGER =6;
//	public static final int 	LOGISTIC =7;
	public static final int 	LOGISTICMANAGER =7;
	public static final int 	MANAGER =8;
	
	
	
	public static ArrayList tokenList[]=new ArrayList[2];

	public static boolean checkRole(Long userId,String roleType){
		boolean booRet=false;
		try{
			
			ArrayList roleList=null;
			if(!roleType.equals("admin")){
				roleList = getAtomRole(userId);
			}
			
			if(roleType.equals("admin")){ //系统管理员
				if(userId.intValue()==1)	booRet=true; 
			}else if(roleType.equals("RECEPTION")){ 
				if(roleList.contains(new Long(RECEPTION))) booRet=true; 
			}else if(roleType.equals("PIC")){ //仓库管理员
				if(roleList.contains(new Long(PIC))) booRet=true; 
			}else if(roleType.equals("SYSTEM")){ 
				if(roleList.contains(new Long(SYSTEM))) booRet=true; 
			}else if(roleType.equals("REPORT")){ 
				if(roleList.contains(new Long(REPORT))) booRet=true; 
			}else if(roleType.equals("REPAIRMAN")){ //维修员
				if(roleList.contains(new Long(REPAIRMAN))) booRet=true; 
			
			}else if(roleType.equals("REPAIRMANAGER")){ 		
				if(roleList.contains(new Long(REPAIRMANAGER))) booRet=true; 
			}else if(roleType.equals("LOGISTICMANAGER")){ 		
				if(roleList.contains(new Long(LOGISTICMANAGER))) booRet=true; 
			}else if(roleType.equals("MANAGER")){ 		
				if(roleList.contains(new Long(MANAGER))) booRet=true; 
			
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return booRet;
	}

	/**
	 * 该userId包含任意一个roleTypes中的权限，则返回true
	 */
	public static boolean checkRoleContains(Long userId,String roleTypes[]){
		boolean booRet=false;
		try{
			if(userId.intValue()==0&&Operate.arrayContains(roleTypes,"admin")){
				booRet=true;
			}else{
				ArrayList roleList=getAtomRole(userId);
				
				for(int i=0;i<roleTypes.length;i++){
					
					if(roleTypes[i].equals("RECEPTION")){ 
						if(roleList.contains(new Long(RECEPTION))) booRet=true; 
					}else if(roleTypes[i].equals("PIC")){ //仓库管理员
						if(roleList.contains(new Long(PIC))) booRet=true; 
					}else if(roleTypes[i].equals("SYSTEM")){ 
						if(roleList.contains(new Long(SYSTEM))) booRet=true; 
					}else if(roleTypes[i].equals("REPORT")){ 
						if(roleList.contains(new Long(REPORT))) booRet=true; 
					}else if(roleTypes[i].equals("REPAIRMAN")){ //维修员
						if(roleList.contains(new Long(REPAIRMAN))) booRet=true; 
					
					}else if(roleTypes[i].equals("REPAIRMANAGER")){ 		
						if(roleList.contains(new Long(REPAIRMANAGER))) booRet=true; 
					}else if(roleTypes[i].equals("LOGISTICMANAGER")){ 		
						if(roleList.contains(new Long(LOGISTICMANAGER))) booRet=true; 
					}else if(roleTypes[i].equals("MANAGER")){ 		
						if(roleList.contains(new Long(MANAGER))) booRet=true; 
					
					}
				}
				

			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return booRet;
	}
	
	
	public static ArrayList getAtomRole(Long userId){
		ArrayList roleList=new ArrayList();
		try{
			UserBo ub = UserBo.getInstance();
			UserForm uf=ub.find(userId.toString());
			
			Iterator it=uf.getRoles().iterator();
			
			while(it.hasNext()){
				RoleForm rf=(RoleForm)it.next();
//				if(rf.getRoleType().equals("A")||rf.getRoleType().equals("B")){
					roleList.add(rf.getId());
//				}
			}
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return roleList;
	}
	
	
	public static boolean checkAdmin(Long userId) throws Exception{
		
		String[] roles={"admin","BASETABLE"};
		
		return checkRoleContains(userId,roles);
	}
	
	public static boolean checkRepairIW(String warrantyType) throws Exception{
		
		if(warrantyType.equals("I") || warrantyType.equals("E")){
			return true;
		}else{
			return false;
		}
		
		
	}
	
	public static boolean checkSaleIW(String warrantyType) throws Exception{
		
		if(warrantyType.equals("I")){
			return true;
		}else{
			return false;
		}
		
		
	}
	
	public static List<String[]> getAllUserByRole(Integer[] roleId) throws Exception{
		String ids="",names="";
		for(Integer id : roleId){
			RoleForm rf = RoleBo.getInstance().findRoleUser(id+"");
			ids+= rf.getUserCode();
			names+= rf.getUserName();
		}
		List<String[]> userList = new ArrayList<String[]>();
		String[] strIds=ids.split(",");
		String[] strNames=names.split(",");
		HashSet<String> idSet = new HashSet<String>();
		for(int i=0;i<strIds.length;i++){
			if(idSet.contains(strIds[i])){
				continue;
			}else{
				idSet.add(strIds[i]);
				String[] temp={strIds[i],strNames[i]};
				userList.add(temp);
			}
			
		}
		
		return userList;
	}
	
	
}
