package com.dne.sie.common.upload;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;

import com.dne.sie.util.action.ControlAction;

public class TestUpload extends ControlAction{
	
	public String upfile(HttpServletRequest request, ActionForm form){
		try{
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(524288);
	
			List items = upload.parseRequest(request);
		
			 //Process the uploaded items
			 Iterator iter = items.iterator();
			 
			 if(iter.hasNext()) {
				 
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
	
					long sizeInBytes = item.getSize();
					String fn = item.getName();
					System.out.println("-----fn="+fn);
					System.out.println("-----sizeInBytes="+sizeInBytes);
					
					//InputStream iss = item.getInputStream();
							
					File f=new File("d:/temp");
					if(!f.exists()){
						f.mkdirs();
					}
					item.write(new File("d:/temp/testFile"));				
				
				}
				
			 }
		}catch(FileUploadException ex){
			ex.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "upload";
	}

}
