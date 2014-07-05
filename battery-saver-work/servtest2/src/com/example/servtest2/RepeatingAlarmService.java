package com.example.servtest2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class RepeatingAlarmService extends BroadcastReceiver {

	  static int s = 0;
	  
	  @Override
	  public void onReceive(Context context, Intent intent) {
				  
		Log.v(this.getClass().getName(), "its Repeat " +s++);
		ServiceExample.MyThread.run();
	    Toast.makeText(context, "It's Service Time!", Toast.LENGTH_LONG).show();
	    
	    Log.v(this.getClass().getName(), "Timed alarm onReceive() started at time: " + new java.sql.Timestamp(System.currentTimeMillis()).toString());
	  }
	}
