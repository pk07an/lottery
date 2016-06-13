    package com.npc.lottery.util.IPparse;  

import java.io.File;
      
    //import junit.framework.TestCase;  
      
   // public class IPtest extends TestCase {  
    	public class IPtest{  
          
      
        public static void main(String[] args) {
        	//指定纯真数据库的文件名，所在文件夹  
        	/*String k=IPSeeker.class.getResource("QQWry.Dat").getFile();
        	File file=new File(k);
        	String path=file.getPath();
        	String fileName=file.getName();
        	String dir=path.substring(0,path.indexOf(fileName));*/

    		IPSeeker ip=new IPSeeker("QQWry.Dat","E:/EclipseSpace/Lottery/WEB-INF/src/com");  
    		//IPSeeker ip=new IPSeeker("QQWry.Dat",dir);  
           //IPSeeker ip=new IPSeeker("QQWry.Dat","E:/EclipseSpace/Lottery/WEB-INF/src/com/npc/lottery/util/IPparse/");  
    System.out.println(ip.getIPLocation("112.213.126.103").getCountry()+":"+ip.getIPLocation("112.213.126.103").getArea());  
		}
    }  