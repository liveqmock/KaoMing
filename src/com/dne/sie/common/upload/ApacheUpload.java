package com.dne.sie.common.upload;

//Java 基础类
import java.util.*;
import java.io.File;
import java.io.IOException;

//Java 扩展类
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;

//第三方类
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.log4j.Logger;

//自定义类
import com.dne.sie.maintenance.bo.AttachedInfoBo;
import com.dne.sie.maintenance.form.AttachedInfoForm;
import com.dne.sie.util.action.ControlAction;
import com.dne.sie.common.tools.Operate;
import com.dne.sie.common.tools.EscapeUnescape;


public class ApacheUpload extends ControlAction{
	private static Logger logger = Logger.getLogger(ApacheUpload.class);
	
	public String fileUpload(HttpServletRequest request, ActionForm form){
		String forward = "fileUpload";
		int tag=-1;
		try{
			HttpSession session=request.getSession();
			Long userId=(Long)session.getAttribute("userId");
			
			String path=Operate.getSysPath()+"affix/fileInfo/";
			
			String id=request.getParameter("id")==null?"0":request.getParameter("id");
			String affixType=request.getParameter("fileType")==null?"":request.getParameter("fileType");
			String realName=request.getParameter("path")==null?"":request.getParameter("path");
			String fileMaxSize=request.getParameter("fileMaxSize")==null?"104857600":request.getParameter("fileMaxSize");
			
			String tempName="f"+userId+Operate.getSectTime();
			if(realName.lastIndexOf('.')!=-1){
				tempName+=realName.substring(realName.lastIndexOf('.'));
			}
			
			if(realName.lastIndexOf('/')!=-1){
				realName=realName.substring(realName.lastIndexOf('/')+1);
			}else if(realName.lastIndexOf('\\')!=-1){
				realName=realName.substring(realName.lastIndexOf('\\')+1);
			}
			
			//System.out.println("---------realName="+realName);
			
			if(!Operate.isPositiveInteger(fileMaxSize)){
				tag=1105;
			}else if(Integer.parseInt(fileMaxSize)>152428800){
				tag=1105;
			}else{
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				
				upload.setSizeMax(Long.parseLong(fileMaxSize));
			
				 // maximum size that will be stored in memory?
				 // 设置最多只允许在内存中存储的数据,单位:字节
				//upload.setSizeThreshold(4096);
				 // 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
				//upload.setRepositoryPath("/tmp");
	
	
				 // Parse the request
				 List items = upload.parseRequest(request);
				 //Process the uploaded items
				 Iterator iter = items.iterator();
				 if(iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					//忽略其他不是文件域的所有表单信息
					if (!item.isFormField()) {
	
						char split1=0x0001;
						String realName2=EscapeUnescape.unescape(realName.replaceAll(split1+"","%u"));
						//System.out.println("-------------realName2="+realName2);
							
							
						//String fieldName = item.getFieldName();
						//String realName = item.getName();
						String fileType = realName2.substring(realName2.lastIndexOf(".")+1).toLowerCase();
						//String contentType = item.getContentType().toLowerCase();
						//boolean isInMemory = item.isInMemory();
						long sizeInBytes = item.getSize();
						
						//System.out.println("-----fileType="+fileType);
						//System.out.println("-----sizeInBytes="+sizeInBytes);
						
						if(fileType.equals("jsp")||fileType.equals("html")||fileType.equals("sh")
							||fileType.equals("exe")||fileType.equals("bat")){
							tag=1010;
						}else if(sizeInBytes>Integer.parseInt(fileMaxSize)){
							tag=1105;
						}else{
							String createDate=Operate.getDate("yyyy-MM-dd");
							String strMonth=Operate.getMonth();
							
							tempName=strMonth+"/"+createDate+"/"+tempName;

							File f=new File(path+strMonth+"/"+createDate);
							if(!f.exists()){
								f.mkdirs();
							}
							item.write(new File(path+tempName));
							//System.out.println("-----path+tempName="+path+tempName);
							//插入上传记录表
							AttachedInfoForm aif = new AttachedInfoForm();
					
							aif.setAttachedName(realName2);
							aif.setSaveAttachedName(tempName);
							aif.setCreateBy(userId);
							aif.setCreateDate(new Date());
							aif.setFileLength(new Long(sizeInBytes));
							aif.setFileType(affixType);
							if(Operate.isInteger(id)) aif.setForeignId(new Long(id));
					  
							tag=AttachedInfoBo.getInstance().add(aif);
							
							String attacheId=aif.getAttachedId().toString();
							//String[] reportPath=Operate.getReportPath();
							String filePath="affix/fileInfo/"+tempName; //下载文件的相对路径
							request.setAttribute("filePath",filePath);
							request.setAttribute("path",realName);
							request.setAttribute("attacheId",attacheId);
							request.setAttribute("createDate",createDate);
							request.setAttribute("affixType",affixType);
							
							f=null;
							
						}
					}
				 }
			}
			request.setAttribute("tag",tag+"");
		}catch(FileUploadException ex){
			request.setAttribute("tag","1105");
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	


	public String excelUpload(HttpServletRequest request, ActionForm form)
					throws  ServletException, IOException {
		String forward = "excelUpload";
		int tag=-1;
		try{
			String strFlag=request.getParameter("flag")==null?"":request.getParameter("flag");
			
			HttpSession session=request.getSession();
			Long userId=session.getAttribute("userId")==null?new Long(0):(Long)session.getAttribute("userId");
			
			String path=Operate.getSysPath()+"affix/upTemp/";
			String tempName="f"+Operate.getSectTime()+userId.toString();
			if(strFlag.equals("d6Mapping")) tempName+=".txt";
			else tempName+=".xls";
			
			
			//检查输入请求是否为multipart表单数据。
			//boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			 RequestContext requestContext  =   new  ServletRequestContext(request);

			//为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			//10M
			upload.setSizeMax(10485760);
			
			 // maximum size that will be stored in memory?
			 // 设置最多只允许在内存中存储的数据,单位:字节
			//upload.setSizeThreshold(4096);
			 // 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
			//upload.setRepositoryPath("/tmp");
			 // Parse the request
			
			List items = upload.parseRequest(requestContext);
			
			 //Process the uploaded items
			 Iterator iter = items.iterator();
			 if(iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				//忽略其他不是文件域的所有表单信息
				if (!item.isFormField()) {
					String realName = item.getName();
					String fileType = realName.substring(realName.lastIndexOf(".")+1).toLowerCase();
					if(fileType.equals("xls")||fileType.equals("txt")){
						//解析完了就删除，因此不再按时间建立目录
						item.write(new File(path+tempName));
						tag=1;
					}else{
						tag=1010;
					}
				}
			 }
			 
			request.setAttribute("tempName",tempName);
			request.setAttribute("tag",tag+"");
			request.setAttribute("flag",strFlag);
		}catch(FileUploadException ex){
			request.setAttribute("tag","1105");
			ex.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return forward;
	}
	
	
	
}
