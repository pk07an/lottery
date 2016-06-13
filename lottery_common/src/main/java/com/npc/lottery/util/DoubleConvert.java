package com.npc.lottery.util;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class DoubleConvert extends StrutsTypeConverter {
	
	
	
	
	@Override  
    public Object convertFromString(Map context, String[] values, Class toClass) {   
        
        if (Double.class == toClass) {   
             String doubleStr = values[0];   
             //System.out.println("获取到的字符串" + doubleStr);   
             Double d = Double.parseDouble(doubleStr);   
            return d;   
         }   
        return 0;   
     }   
  
    @Override  
    public String convertToString(Map context, Object o) {   
  
        return o.toString();   
     }   

}
