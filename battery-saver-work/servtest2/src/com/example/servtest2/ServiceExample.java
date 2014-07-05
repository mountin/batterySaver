package com.example.servtest2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class ServiceExample extends Service {

	  public static long SizeToOff = 25; // kb
	  public static int INTERVAL = 15000; // 15 sec
	  public static int FIRST_RUN = 10000; // 5 seconds
	  public static boolean REBOOTon = true; // 5 seconds
	  int REQUEST_CODE = 11223344;	  
	  public static MyThread MyThread = new MyThread();
	  public static TrafficStats start = new TrafficStats();
	  

	  AlarmManager alarmManager;

	  @Override
	  public void onCreate() {
	    super.onCreate();
	    
	    startService();
	    
	    Log.v(this.getClass().getName(), "onCreate(..)");
	  }

	  @Override
	  public IBinder onBind(Intent intent) {
	    Log.v(this.getClass().getName(), "onBind(..)");
	    return null;
	  }

	  @Override
	  public void onDestroy() {
	    if (alarmManager != null) {
	      Intent intent = new Intent(this, RepeatingAlarmService.class);
	      alarmManager.cancel(PendingIntent.getBroadcast(this, REQUEST_CODE, intent, 0));
	    }
	    Toast.makeText(this, "Service Stopped!", Toast.LENGTH_LONG).show();
	    Log.v(this.getClass().getName(), "Service onDestroy(). Stop AlarmManager at " + new java.sql.Timestamp(System.currentTimeMillis()).toString());
	  }

	  private void startService() {

	    Intent intent = new Intent(this, RepeatingAlarmService.class);
	    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, REQUEST_CODE, intent, 0);

	    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
	    alarmManager.setRepeating(
	        AlarmManager.ELAPSED_REALTIME_WAKEUP,
	        SystemClock.elapsedRealtime() + FIRST_RUN,
	        INTERVAL,
	        pendingIntent);

	    Toast.makeText(this, "Service Started.", Toast.LENGTH_LONG).show();
	    Log.v(this.getClass().getName(), "AlarmManger started at " + new java.sql.Timestamp(System.currentTimeMillis()).toString());
	  }


	}