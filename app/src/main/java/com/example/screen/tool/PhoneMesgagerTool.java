package com.example.screen.tool;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class PhoneMesgagerTool {

	/**
	* 使用WIFI时，获取本机IP地址 
    * @param mContext 
    * @return 
    */  
   public static String getWIFILocalIpAdress(Context mContext) {  
       
       //获取wifi服务  
       WifiManager wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);  
       //判断wifi是否开启  
       if (!wifiManager.isWifiEnabled()) {  
       wifiManager.setWifiEnabled(true);   
       }  
       WifiInfo wifiInfo = wifiManager.getConnectionInfo();      
       int ipAddress = wifiInfo.getIpAddress();  
       String ip = formatIpAddress(ipAddress);  
       return ip;  
   }    
   private static String formatIpAddress(int ipAdress) {      
       
        return (ipAdress & 0xFF ) + "." +      
       ((ipAdress >> 8 ) & 0xFF) + "." +      
       ((ipAdress >> 16 ) & 0xFF) + "." +      
       ( ipAdress >> 24 & 0xFF) ;  
    }
   
   /**
    * 隐性调用变成显性调用
    * @param context
    * @param implicitIntent
    * @return
    */
   public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {  
       // Retrieve all services that can match the given intent  
       PackageManager pm = context.getPackageManager();  
       List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);  
  
       // Make sure only one match was found  
       if (resolveInfo == null || resolveInfo.size() != 1) {  
           return null;  
       }  
  
       // Get component info and create ComponentName  
       ResolveInfo serviceInfo = resolveInfo.get(0);  
       String packageName = serviceInfo.serviceInfo.packageName;  
       String className = serviceInfo.serviceInfo.name;  
       ComponentName component = new ComponentName(packageName, className);  
  
       // Create a new intent. Use the old one for extras and such reuse  
       Intent explicitIntent = new Intent(implicitIntent);  
  
       // Set the component to be explicit  
       explicitIntent.setComponent(component);  
  
       return explicitIntent;  
   } 
}
