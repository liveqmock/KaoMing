package com.dne.sie.common.tools;


import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.io.*;
import com.dne.sie.common.tools.Operate;

public class LayOut {
 
	private static Logger logger = Logger.getLogger(LayOut.class);
    private String filePathName;
    private String htmlCode;

    public LayOut()    {
        
    }

    public LayOut(String fileType, String fileName) {
		String prefix = Operate.getSysPath();
        filePathName = prefix + "pages/p" + fileType + "/" + fileName + ".data";
    }
    
	public LayOut(String fullFilePathAndName) {
		String prefix = Operate.getSysPath();
		filePathName = prefix + fullFilePathAndName;
	}

    public boolean save(String content){
		FileOutputStream fileoutputstream=null;
		File file=null;
		boolean booRet=false;
        try{
            //htmlCode = new String(content.getBytes("iso-8859-1"), "GBK");
//			htmlCode=content;
			htmlCode=EscapeUnescape.escape(content);
        	
        	file = new File(filePathName);
            File fileParent = file.getParentFile();
            if(!fileParent.exists())
                fileParent.mkdirs();
            fileoutputstream = new FileOutputStream(file);
            fileoutputstream.write(htmlCode.getBytes());
			booRet=true;
        }catch(IOException ioexception) {
            logger.error("����������Ϣ����", ioexception);
		}catch(Exception ex1){
			ex1.printStackTrace();
        }finally{
        	try{
				fileoutputstream.close();
				file = null;
        	}catch(IOException ioe) {}
        }
        return booRet;
    }
    
	public boolean saveWithoutEscape(String content){
		FileOutputStream fileoutputstream=null;
		File file=null;
		boolean booRet=false;
		try{
			file = new File(filePathName);
			File fileParent = file.getParentFile();
			if(!fileParent.exists()) fileParent.mkdirs();
			
			fileoutputstream = new FileOutputStream(file);
			fileoutputstream.write(content.getBytes("GBK")); 
			booRet=true;
		}catch(IOException ioexception) {
			logger.error("����������Ϣ����", ioexception);
		}catch(Exception ex1){
			ex1.printStackTrace();
		}finally{
			try{
				if(fileoutputstream!=null) fileoutputstream.close();
				file = null;
			}catch(IOException ioe) {}
		}
		return booRet;
	}

	public boolean saveWithoutEscape(String content,String url){
		FileOutputStream fileoutputstream=null;
		File file=null;
		boolean booRet=false;
		try{
			file = new File(url);
			File fileParent = file.getParentFile();
			if(!fileParent.exists()) fileParent.mkdirs();
			
			fileoutputstream = new FileOutputStream(file);
			fileoutputstream.write(content.getBytes("GBK")); 
			booRet=true;
		}catch(IOException ioexception) {
			logger.error("����������Ϣ����", ioexception);
		}catch(Exception ex1){
			ex1.printStackTrace();
		}finally{
			try{
				if(fileoutputstream!=null) fileoutputstream.close();
				file = null;
			}catch(IOException ioe) {}
		}
		return booRet;
	}
	
    public String getHtmlCode(){
		BufferedReader in = null;
        try{
            in = new BufferedReader(new FileReader(filePathName));
            StringBuffer stbContent = new StringBuffer();
            String s;
            while((s = in.readLine()) != null){
				stbContent.append(s).append("\r\n");
            }
            
            htmlCode = stbContent.toString();
		}catch(IOException ioexception) {
			logger.error("��ò����ļ����ݳ���", ioexception);
        }finally{
        	try{
				if(in!=null) in.close();
        	}catch(IOException ioe){
				ioe.printStackTrace();
        	}
        }
        
        return htmlCode;
    }

    public String getInputHtmlCode(){
		BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(filePathName));
            StringBuffer stbContent = new StringBuffer();
            String s;
            while((s = in.readLine()) != null)
                stbContent.append(s);
            htmlCode = stbContent.toString();
		}catch(IOException ioexception) {
			logger.error("��ò����ļ����ݳ���", ioexception);
		}finally{
			try{
				if(in!=null) in.close();
			}catch(IOException ioe){}
		}
        
        return htmlCode;
    }
    
	/**
	 * ���ַ���д���ļ���׷�ӵ����һ�У��ļ����������Զ�������д��
	 * @param content
	 * @return
	 */
	public boolean writeFile(String content){
		boolean booRet=false;
		RandomAccessFile rf=null;
		try {
			//����һ����RandomAccessFile�Ķ��󣬲�ʵ����
			rf=new RandomAccessFile(filePathName,"rw");
			
			//��ָ���ƶ����ļ�ĩβ
			rf.seek(rf.length());
			rf.writeBytes(content+"\r\n");
			booRet = true;

		}catch(IOException ioexception) {
			logger.error("д�ļ����� "+ioexception);
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(rf!=null) rf.close();
			}catch(IOException ioe){}
		}
        
		return booRet;
	}

	public boolean writeFile(String content,String url){
		boolean booRet=false;
		RandomAccessFile rf=null;
		try {
			//����һ����RandomAccessFile�Ķ��󣬲�ʵ����
			rf=new RandomAccessFile(url,"rw");
			
			//��ָ���ƶ����ļ�ĩβ
			rf.seek(rf.length());
			rf.write((content+"\r\n").getBytes("GBK"));
			booRet = true;

		}catch(IOException ioexception) {
			logger.error("д�ļ����� "+ioexception);
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(rf!=null) rf.close();
			}catch(IOException ioe){}
		}
        
		return booRet;
	}

		/**
		 * CIP�����ļ��Ķ������ɣ�ÿ��ȡ��ԭ��txt���ݣ���ƴװ�µ����ݣ�����д��
		 * 		���ڱ��밴��׷�ӣ�RandomAccessFile�޷���ԭ���ļ����ַ����ƣ�
		 * 		д���ļ�ֻ�ܸ��ǣ����ȫ��ȡ����д��
		 * @param dataList ����һ�ܵ����� temp[0]-ά��վ��temp[1]-������
		 * @return
		 */
		public boolean writeCipFile(ArrayList dataList){
			boolean booRet=false;
			RandomAccessFile rf=null;
			try {
				File f=new File(filePathName);
				String reportDay=Operate.formatYMDDate(Operate.seekDate(-7));
			
				if(!f.exists()){
					//����һ����RandomAccessFile�Ķ��󣬲�ʵ����
					rf=new RandomAccessFile(filePathName,"rw");
					String content="\"ASS\",\""+reportDay+"\"\r\n";
					for(int i=0;i<dataList.size();i++){
						String[] temp=(String[])dataList.get(i);
						content+="\""+temp[0]+"\",\""+temp[1]+"\"\r\n";
					}
					//rf.writeBytes(content);
					rf.writeUTF(content);
				}else{
					//����һ����RandomAccessFile�Ķ��󣬲�ʵ����
					rf=new RandomAccessFile(filePathName,"rw");
					long filePointer =0; 
				   	long length =rf.length();  //�õ��ļ��ĳ���
				   	ArrayList allDateList=new ArrayList();
				   	int i=0;
				   	while(filePointer<length) {
					   	String strLine= rf.readLine();  //���ļ��ж�һ��
					   	if(i==0){
							strLine+=",\""+reportDay+"\"\r\n";
					   	}else{
							String[] temp=(String[])dataList.get(i-1);
							strLine+=",\""+temp[1]+"\"\r\n";
					   	}
					   	//System.out.println("-------strLine="+strLine);  
						allDateList.add(strLine);
					   	//�õ��ļ�ָ�뵱ǰ��λ��
						filePointer= rf.getFilePointer();	
						i++;				
					} 
					if(dataList.size()!=(i-1)){
						logger.error("last line is "+(i-1)+"  this line is "+dataList.size());
					}
					//��ָ���ƶ����ļ���ͷ
					rf.seek(0);
					for(int j=0;j<allDateList.size();j++){
						String tempDate=(String)allDateList.get(j);
						//д���ļ�������ԭ�����ַ�
						rf.writeBytes(tempDate);
					}
				
				}
			
				booRet = true;

			}catch(IOException ioexception) {
				logger.error("д�ļ����� "+ioexception);
			}catch(Exception e) {
				e.printStackTrace();
			}finally{
				try{
					if(rf!=null) rf.close();
				}catch(IOException ioe){}
			}
        
			return booRet;
		}
		

	public static void main(String args[]){
		LayOut lo=new LayOut("xx.text");
		lo.writeFile("time:"+Operate.getDate());
		
	}


}
