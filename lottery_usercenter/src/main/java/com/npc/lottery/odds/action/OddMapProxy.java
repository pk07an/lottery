package com.npc.lottery.odds.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.npc.lottery.common.Constant;
import com.npc.lottery.odds.entity.ShopsPlayOdds;

public class OddMapProxy {
	
	 Map<String,ShopsPlayOdds> shopOdds=new HashMap<String,ShopsPlayOdds>();
	 public OddMapProxy(Map<String,ShopsPlayOdds> shopOddMap)
	 {
		 
		 shopOdds=shopOddMap;
	 }
	 
	 public  void setOddMap(Map<String,ShopsPlayOdds> shopOddMap)
	 {
	 	  
	 	  this.shopOdds=shopOddMap;
	 }
	 
	 public ShopsPlayOdds get(String key)
	 {
		if(shopOdds.get(key)==null)
		{
			ShopsPlayOdds odd=new ShopsPlayOdds();
			odd.setRealOdds(BigDecimal.valueOf(0));
			return odd ;
		}
		else if(shopOdds.get(key).getRealOdds()==null)
		{
			ShopsPlayOdds odd=shopOdds.get(key);
			odd.setRealOdds(BigDecimal.valueOf(0));
			return odd;
		}
		else 
			return shopOdds.get(key);
			
	 }
	 
	 public String getRealShopOdds(String key,boolean stop)
	 {
		 ShopsPlayOdds shopOdd= this.get(key);
		 if(Constant.SHOP_PLAY_ODD_STATUS_INVALID.equals(shopOdd.getState()))
			 return "-";
		 else if(stop)
			 return "封盘中";
		 else
             return shopOdd.getRealOdds().toString();
		 
	 }
	 
	 
	 public String getRealShopOdds(String key)
	 {
		 ShopsPlayOdds shopOdd= this.get(key);
		 if(Constant.SHOP_PLAY_ODD_STATUS_INVALID.equals(shopOdd.getState()))
			 return "-";
		 else
             return shopOdd.getRealOdds().toString();
		 
	 }
	 
	 public ShopsPlayOdds getHKTM(String key,String tab)
	 {
		return this.get(key+"_"+tab);
			
	 }
	 
	 public ShopsPlayOdds getZTM(String key,String zt)
	 {
		 
		return this.get(key+"_"+zt);
			
	 }
	 
	 public String getZTMRealShopOdds(String key,String zt)
	 {
		 ShopsPlayOdds shopOdd= this.getZTM(key,zt);
		 
		 if(Constant.SHOP_PLAY_ODD_STATUS_INVALID.equals(shopOdd.getState()))
			 return "-";
		 else
             return shopOdd.getRealOdds().toString();
			
	 }
	
}
