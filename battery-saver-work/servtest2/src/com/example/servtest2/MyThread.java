package com.example.servtest2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiManager;
import android.os.Looper;
import android.util.Log;

public class MyThread extends Thread{

	
public static long SizeToOff = ServiceExample.SizeToOff;
public  boolean bluet;
static public  long  straffStart; //all traff from start app
public static  TrafficStats stats = new TrafficStats();
final String LOG_TAG = "myLogs";
private static int s=0 ;
	

	public MyThread (){
		setStraffStart();		
		//Log.d("sample", "!Constructor");
	}

	public void run(){
				
				
		 
			try {
				Log.d("sample", "!Run Curr traf = "+getCurrentTraff()+" . this is MyThread."+s++);
				checkTraff();
				//Thread.sleep(SecCheck * 1000);
				Log.d("sample", " ?All traf is:"+stats.getTotalRxBytes() / 1024+ "; Starting from:"+straffStart);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	


static public void setStraffStart(){
    straffStart = (long) (stats.getTotalRxBytes() / 1024);
    //Log.d("sample", "WE SETT straffStart is "+straffStart);
}


public static long  getCurrentTraff(){
    	
    	return (stats.getTotalRxBytes() / 1024);
}


public void setOffAll(){


	
     	ConnectivityManager connectivityManager =  (ConnectivityManager)   MyApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE); 

     	try {
     			NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    	
     		    boolean bluemm = BluetoothAdapter.getDefaultAdapter().isEnabled();
     		    
     		    WifiManager wifi = (WifiManager) MyApp.getContext().getSystemService(Context.WIFI_SERVICE);
     		    //Log.d(LOG_TAG, " is WIFI: "+wifi);
     		   
     	        boolean isWiFi  = wifi.isWifiEnabled(); 	        
     	        if(isWiFi){
     		    	setWifiOff();
     		    }

     		    if (bluemm){
     			  setBluetoothOff(); 		    			  
     		    }
     		   
    	
    		if(activeNetwork == null) 
    	    {
    	    	
    			//Log.d(LOG_TAG, "Network is NOT connected! ");
    	
    	    }
    	    else{
    	    	
    	    	SetUnActiveNetwork(activeNetwork);
    	    	
    	    }
    	    
    	 	}
    	 	 catch (NullPointerException e) {
    	 		//tvHello.append("this is null pointer Exception");
    	 		//Log.d(LOG_TAG, e.toString());
    	 		
    	 		
    	 	 }
    	 	 catch (Exception e) {
    	 	 
    	 		//Log.d(LOG_TAG, e.toString());
    			// TODO: handle exception
    		}
    	    	        	
}

   public void setBluetoothOff(){
 		try{
 		    BluetoothAdapter bluemm = BluetoothAdapter.getDefaultAdapter();
 		    bluet  = bluemm.isEnabled();
 		    
 		    if(bluemm != null){
 		    	bluemm.disable();
 		    	//Log.d(LOG_TAG,"Bluetooth setted OFF"); 		    	
 		    }
 		    } catch (Exception e){
 		    	//Log.d(LOG_TAG,"exception Bluetooth null pointer ");
 		    }
    }

public void SetUnActiveNetwork(NetworkInfo activeNetwork) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{

                    
    boolean isMobile = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
    //boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    WifiManager wifi = (WifiManager) MyApp.getContext().getSystemService(Context.WIFI_SERVICE);
    boolean isWiFi  = wifi.isWifiEnabled();
    
    //boolean isBlueTooth = activeNetwork.getType() == ConnectivityManager.TYPE_BLUETOOTH;
    
	  
    Log.d(LOG_TAG,"Mobile GOING TO BE OFF....."+isMobile);
	    //if(isMobile)
	    {


		    try { 
		    	//Log.d(LOG_TAG," Mobile is off! ");
		    	updateAPN(MyApp.getContext(), false);
		        Thread.sleep(10); 
		  } catch (InterruptedException e) { 
			  e.printStackTrace(); 
		  } 
	    }    	        	    
	    
	    if(isWiFi){    	    	   			
	    	setWifiOff();
	    }

}

public void setWifiOff(){
	
    WifiManager wifi = (WifiManager) MyApp.getContext().getSystemService(Context.WIFI_SERVICE); 
    wifi.setWifiEnabled(false); 
    //Log.d(LOG_TAG," WiFi is OFF! ");
    try { 
        Thread.sleep(10); 
  } catch (InterruptedException e) { 
	  e.printStackTrace(); 
  } 
	
}

public static void updateAPN(Context paramContext, boolean enable) {
    try {
        ConnectivityManager connectivityManager = (ConnectivityManager) paramContext.getSystemService("connectivity");
        Method setMobileDataEnabledMethod = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(false);
        setMobileDataEnabledMethod.invoke(connectivityManager, enable);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void checkTraff(){	        

	
        String  mesg = "";
        
    	if((getCurrentTraff() - straffStart) < SizeToOff){
    	
    		setOffAll();
    		
    		setStraffStart();
    		mesg = "So...Data turned OFF... straffStart = "+straffStart;
    		
    	}else{
    		
    		mesg = "So...Data Continue Work...";
    	}
    	
    	
    	
    	//Log.d(LOG_TAG, "was - "+straffStart+ "- all traf "+getCurrentTraff()+"  . Now Traffic is "+(getCurrentTraff() - straffStart) + "Kb. / last 15 sec. " + mesg);
    	  
    	setStraffStart();
    	
    }

	
}
