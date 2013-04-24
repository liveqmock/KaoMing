package com.dne.sie.common.upload;

import java.io.*;
import com.dne.sie.common.tools.Operate;

/**
 * @author xt
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class FileOperate {
	
	private static void fileTranser(String fromPath,String backPath) {
		
		try{
			System.out.println("fromPath="+fromPath);
			System.out.println("backPath="+backPath);
			
			File bFile=new File(backPath);
			if(!bFile.exists()){
				bFile.mkdirs();
			}
			File fFile=new File(fromPath);
			File[] list=fFile.listFiles();
			if(list!=null){
				FileInputStream input = null;
				FileOutputStream output =null;
				try{
					String tempName=null;
					for(int i=0;i<list.length;i++){
						if(list[i].isFile()){
							tempName=list[i].getName();
							input = new FileInputStream(fromPath+tempName);
							output = new FileOutputStream(backPath+tempName);
							
							byte[] b=new byte[1204*5];
							int len;
							while((len=input.read(b))!=-1){
								output.write(b,0,len);
							}
							
						}
					}
				}catch(Exception e1){
					e1.printStackTrace();
				}finally{
					if(output!=null){
						output.flush();
						output.close();
					}
					if(input!=null){
						input.close();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("==============");
		
	}
	
	private static void deleteHistoryFile(String fromPath){
		try{
			  File dFile=new File(fromPath);

			  File[] list=dFile.listFiles();
			  if(list!=null){
				  for(int i=0;i<list.length;i++){
					
					  if(list[i].isFile()){
					  	System.out.println("list["+i+"].getName()="+list[i].getName());
						list[i].delete();
				
					  }
				  }
			  }


		}catch(Exception e){
			e.printStackTrace();
		}
	}
	


	public static void main(String args[]){
		try{
			String prefix = Operate.getSysPath()+"affix/fileInfo/";
			fileTranser(prefix,prefix+"initialC/");
			//deleteHistoryFile(prefix);
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}
	
	

}
