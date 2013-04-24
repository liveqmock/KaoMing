package com.dne.sie.support.userRole.bo;

//Java 基础类
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.StringTokenizer;
//Java 扩展类


//第三方类
import org.apache.log4j.Logger;

//自定义类
import com.dne.sie.support.userRole.form.FunctionForm;
import com.dne.sie.support.userRole.form.UserForm;
import com.dne.sie.support.userRole.form.RoleForm;
import com.dne.sie.common.tools.LayOut;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.util.bo.CommBo;


/**
 * 功能点管理BO处理类
 * @author xt
 * @version 1.1.5.6
 * @see FunctionBo.java <br>
 */
public class FunctionBo extends CommBo {
	private static Logger logger = Logger.getLogger(FunctionBo.class);

	private static final FunctionBo INSTANCE = new FunctionBo();
		
	private FunctionBo(){
	}
	
	public static final FunctionBo getInstance() {
	   return INSTANCE;
	}
    public static HashMap hm=new HashMap();
	  
		
	   /**
		* 根据角色id查询该角色下的功能点信息
		* @param String 角色id；Integer 部门类型
		* @return Set[] 分别存FunctionForm、functionType、functionCodes
		*/
		public Set[] findFunction(String roleId,Integer orgType) {
			
			RoleForm rf = new RoleForm();
			Set[] retSet={new HashSet(),new TreeSet(),new HashSet()};
			try {
				Iterator it = null;
				FunctionForm ff=new FunctionForm();
				
				List functionList=this.getDao().list("from FunctionForm as ff order by ff.parentFunctionCode,ff.id");
				if(orgType.intValue()!=0){
					
				}else{
					retSet[0]=new HashSet(functionList);
				}
					
				it=functionList.iterator();
				while(it.hasNext()){
					ff=(FunctionForm)it.next();
					if(ff.getFunctionType()!=null){
						retSet[1].add(ff.getFunctionType());
					}
					
				}
					
				if(roleId!=null&&!roleId.equals("")){ 
					RoleBo rbo = RoleBo.getInstance();
					rf=rbo.findById(roleId);
					if(rf!=null&&rf.getFunctionCodes()!=null){
						retSet[2].add(rf.getFunctionCodes());
					}
					
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			return retSet;		    	
		} 
		
		
		/**
		* 查询全部功能点信息
		* @param Integer 部门类型
		* @return Set[] 分别存FunctionForm、functionType、functionCodes
		*/
//		public Set[] findAdminFunction(Integer orgType) {
//		
//			RoleForm rf = new RoleForm();
//			Set[] retSet={new HashSet(),new TreeSet(),new HashSet()};
//			try {
//				FunctionForm ff=new FunctionForm();
//			
//				List functionList=this.getDao().list("from FunctionForm as ff order by ff.parentFunctionCode,ff.id");
//				retSet[0]=new HashSet(functionList);
//				
//				Iterator it =functionList.iterator();
//				String strFunCodes="";
//				while(it.hasNext()){
//					ff=(FunctionForm)it.next();
//					if(ff.getFunctionType()!=null){
//						retSet[1].add(ff.getFunctionType());
//					}
//					if(orgType.intValue()!=0){ 
//						
//					}
//				}
//				
//				if(strFunCodes.length()>1)  strFunCodes=strFunCodes.substring(1);
//				retSet[2].add(strFunCodes);
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//			return retSet;		    	
//		} 
	

		
		
		
	/**
	 * 根据id查询功能点信息
	 * @param String id 功能点记录pk
	 * @return 功能点Form
	 */
	public FunctionForm findById(String id) {
		FunctionForm ff = null;
		try {
			ff = (FunctionForm)this.getDao().findById(FunctionForm.class,new Long(id));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ff;		    	
	} 	
				
	
			
	   
		/**
		  * 插入一条功能点信息
		  * @param FunctionForm 功能点信息Form
		  * @return 是否成功标志
		  */
		public int add(FunctionForm ff) {
			int tag=-1;
			boolean t = false;
			try {
				t = this.getDao().insert(ff);
			} catch(Exception e) {
				e.printStackTrace();
			} 
			if (t) {
				tag = 1;
			}
			return tag;	   	
		}
    
		/**
		  * 删除传入的功能点信息
		  * @param List 待删除功能点id的List
		  * @return 是否成功标志
		  */
		public int deleteList(List idList) {
			int tag=-1;
			boolean t = false;
			try {
				t = this.getBatchDao().deleteBatch(idList);
			} catch(Exception e) {
				e.printStackTrace();
			} 
			if (t) {
				tag = 1;
			}
			return tag;	     	
		}
		
		
	
		/**
		 * 修改一条功能点信息
		 * @param FunctionForm 功能点信息Form
		 * @return 是否成功标志，
		 */
	   public int modify(FunctionForm ff) {
			int tag=-1;
			boolean t = false;
			try {
				t = this.getDao().update(ff);
			} catch(Exception e) {
			} finally {
			}
			if (t) {
				tag = 1;
			}
			return tag;	   	
		}
	
	
	 
	/**
	 * 取出功能点复选树信息
	 * @param String - 权限id，Integer - 功能大类，String - 操作权限
	 * @return 封装js树的List
	 */
	 public String[] checkBoxTree(String roleId,Integer orgType,String flag) {
		
		FunctionForm ff=new FunctionForm();
		Set[] sets=null;
		
		if(flag.equals("common")){
			sets=this.findFunction(roleId,orgType);
		}else if(flag.equals("admin")){
			sets=this.findFunction(roleId,new Integer(0));
		}
		
		
		Iterator itType=sets[1].iterator();
		String functionCodes="";
		if(sets[2]!=null&&sets[2].size()>0) functionCodes=(String)sets[2].iterator().next();
		
		String strRet[]=new String[sets[1].size()];
		try{
			String strDate="",strTree="";
			int i=0;
			while(itType.hasNext()){
				Iterator itFunction=sets[0].iterator();
				
				String strType=(String)itType.next();
				strDate="data"+strType;
				strTree="tree"+i;
				strRet[i]="var data"+strType+" = new Array();\n";
				
				while(itFunction.hasNext()){
				
					ff=(FunctionForm)itFunction.next();
					String strFunctionType=ff.getFunctionType();		//功能大类
					if(strType.equals(strFunctionType)){
						String strCheck="false";
						
						String strId=ff.getId().toString();							//功能id
						String strName=Operate.htmlInjectionFilter(ff.getFunctionName());	//功能名称
						String parentCode=ff.getParentFunctionCode().toString();    //父节点id
						
						if(functionCodes!=null&&!functionCodes.equals("")){
							StringTokenizer st = new StringTokenizer(functionCodes,",");
							while(st.hasMoreTokens()){
								if(st.nextToken().equals(strId)){
									strCheck="true";
									break;
								}
							}
						}
						
						strRet[i]+=strDate+"["+strId+"] = new Array('"+strId+"','"+strName+"','"+strId+"','"+parentCode+"',"+strCheck+");\n";
						
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
				
				i++;
			}
			
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return strRet;
 	
	 }
	 
	 
	/**
	* 查询权限信息
	* @param Long 部门id；flag 操作权限
	* @return ArrayList 权限Form
    */
	public ArrayList findRoleList(String flag) {
		ArrayList roleList = new ArrayList();
		String strWhere=" where rf.delFlag=0 ";
		if(flag.equals("all")){
//			strWhere+=" and rf.roleType='A'";
		}
		/*else if(flag.equals("RC")){
			strWhere+=" and ((rf.organizationCode=2 and rf.roleType='A') or (rf.roleType='C' and rf.organizationCode="+orgCode+"))";
		}else if(flag.equals("HQ")){
			strWhere+=" and ((rf.organizationCode=3 and rf.roleType='A') or (rf.roleType='C' and rf.organizationCode="+orgCode+"))";
		}else if(flag.equals("station")){
			strWhere+=" and ((rf.organizationCode=1 and rf.roleType='A') or (rf.roleType='C' and rf.organizationCode="+orgCode+"))";
		}else if(flag.equals("fatory")){
			strWhere+=" and ((rf.organizationCode=6 and rf.roleType='A') or (rf.roleType='C' and rf.organizationCode="+orgCode+"))";
		}*/
		else{
//			strWhere+=" and rf.roleType='C' ";
		}
		try {
			ArrayList tempList = (ArrayList)this.getDao().list("from RoleForm as rf "+strWhere+" order by rf.id");
			for(int i=0;i<tempList.size();i++){
				RoleForm rf=(RoleForm)tempList.get(i);
				String[] temp={rf.getId().toString(),rf.getRoleName()};
				roleList.add(temp);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return roleList;		    	
	} 	
			
	/**
	* 修改某权限包含的功能点（普通权限）
	* @param String 权限id；String 功能点id（逗号分隔）
	* @return 成功标志
	*/
	public int roleModify(String roleId,String functionId) {
		int tag=-1;
		try {
			RoleBo rbo = RoleBo.getInstance();
			RoleForm rf=rbo.findById(roleId);
			rf.setFunctionCodes(functionId);
			
			tag=rbo.modify(rf);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return tag;		    	
	} 
	
	
	
	

	/**
	* 生成左边菜单的链接和权限配置树的html代码， 由jsp直接调用
	* @param Long 操作用户id
	* @return String[][] 返回html代码
	*/
	public String[][] modelBuild(Long userId) {
		String[][] htmlTree=new String[2][6];
		TreeSet typeSet = new TreeSet();
		
		try {
			//获取该用户的linkId集合 (逗号分隔的字符串)
			String functionId=getRoleLinks(userId);
			
			if(!functionId.equals("")){
									
				FunctionForm ff = new FunctionForm();
				List functionList=this.getDao().list("from FunctionForm as ff where ff.id in ("+functionId+") order by ff.functionType,ff.orderId");
				for(int i=0;i<functionList.size();i++){
					ff=(FunctionForm)functionList.get(i);
					//取出7大模块type的集合
					typeSet.add(ff.getFunctionType());
				}
				
				int intType=0;
				//按每种functionType取link
				Iterator itType = typeSet.iterator();
				while(itType.hasNext()){
					String typeName=(String)itType.next();
					String strHtmlCode="";
					String strTreeVal="";
					
					intType=Integer.parseInt(typeName)-1;
					
					//System.out.println("---------typeName="+typeName);
					for(int i=0;i<functionList.size();i++){
						ff=(FunctionForm)functionList.get(i);
						if(ff.getFunctionType().equals(typeName)){
							//System.out.println("---------ff.getFunctionName()="+ff.getFunctionName());
							
							int layer=ff.getLayer().intValue();
							String functonCode=ff.getId().intValue()+"";
							String functionName=Operate.htmlInjectionFilter(ff.getFunctionName());
							String link=ff.getLink()==null?"":ff.getLink()+"&left=1";
							String pendingName="pendingName";
							
							String strImg2="src='images/i_arrow2.gif' width='3' height='5'";
							String strImg3="images/i_arrow2.gif";
							
							
							if(!link.equals("")){	
								functionName="<a href='"+link+"' target='content' onclick='setTitle(\""+functionName+"\")'>"+functionName+"</a>"; 
								strImg2="src='images/i_arrow4.gif' width='8' height='7'";
								strImg3="images/i_point.gif";
								
								if(!pendingName.equals("")){
									strTreeVal+="<tr class='tableback2'> " +
										"<td ><input type='checkbox' name='chk' value='"+functonCode+"'>"
										+pendingName+"</td></tr> \n";
									
								}
							}
							
							switch(layer){
								case 0:	break;
								case 1:
//									strHtmlCode+="<br><img src='googleImg/icon/rc1.png' align='absmiddle'>&nbsp;&nbsp;<b>"+functionName+"</b><br >";
									strHtmlCode+="<tr><td class='content14_bold'><img src='images/i_point1.gif' width='12' height='13' align='absmiddle'> \n"+
											"&nbsp;&nbsp;"+functionName+"</td></tr>\n"+
											"<tr> <td height='1' bgcolor='#CCCCCC'></td></tr>\n";
									break;
								case 2:
//									strHtmlCode+="&nbsp;&nbsp;&nbsp;&nbsp;<img src='googleImg/icon/rc.png' align='absmiddle'>&nbsp;&nbsp;"+functionName+"<br >";
									strHtmlCode+="<tr> <td> <img src='images/i_tran.gif' width='30' height='8' align='absmiddle'>\n"+
											"<img "+strImg2+" align='absmiddle'>\n"+ 
											"&nbsp;&nbsp;"+functionName+"</td> </tr>\n"+
											"<tr> <td background='images/i_line.gif' height='1'></td> </tr>\n";
									break;
								case 3:
									strHtmlCode+="<tr><td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+functionName+"</td> </tr>\n";
									break;
								default:
									strHtmlCode+="<tr> <td><img src='images/i_tran.gif' width='"+(layer+1)*10+"' height='3' align='absmiddle'>\n"+
											"<img src='"+strImg3+"' width='4' height='5' align='absmiddle'>\n"+
											"&nbsp;&nbsp;"+functionName+"</td></tr>\n";
								
							}//switch
							
						}//if
					}//for
					//System.out.println("---------strHtmlCode="+strHtmlCode.length());
					//System.out.println("---------strTreeVal="+strTreeVal.length());
					htmlTree[0][intType]=strHtmlCode;
					htmlTree[1][intType]=strTreeVal;
					
				} //while
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return htmlTree;
	} 
	
	/**
	* 根据某个userId查出该user包含的role所有的linkId集合
	* @param Long - 用户id
	* @return String linkIds
	*/
	public String getRoleLinks(Long userId) {
		String linkIds="";
			
		try {
			if(userId!=null){
				
				UserForm uf=(UserForm)this.getDao().findById(UserForm.class,userId);
				//取出该userId下的所有role
				ArrayList roleList=new ArrayList(uf.getRoles());
				
				TreeSet ts = new TreeSet();
				for(int i=0;i<roleList.size();i++){
					RoleForm rf = (RoleForm)roleList.get(i);
					//每个role的链接id集合 （逗号分割的String）
					String tempFun=rf.getFunctionCodes();
					if(tempFun!=null&&!tempFun.equals("")){
						String[] conFun=tempFun.split(",");
						for(int j=0;j<conFun.length;j++){
							//该userId的linkId集合 （排序，无重复）
							ts.add(conFun[j]);
						}
					}
				}
				//ControlAction控制权限调用
				hm.put(userId,ts);
				
				//转换成逗号分隔的字符串
				linkIds=Operate.arrayListToString(new ArrayList(ts));
				//System.out.println("-----linkIds="+linkIds);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return linkIds;
	} 
	
	
		
}
