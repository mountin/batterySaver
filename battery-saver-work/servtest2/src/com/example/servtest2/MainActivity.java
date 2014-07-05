package com.example.servtest2;

import com.example.servtest2.ServiceExample;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;
import android.preference.PreferenceManager;

public class MainActivity extends ActionBarActivity implements OnSharedPreferenceChangeListener{

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		// Регистрируем этот OnSharedPreferenceChangeListener
        Context context = MyApp.getContext();
        SharedPreferences prefs =
            PreferenceManager.getDefaultSharedPreferences(context);
        prefs.registerOnSharedPreferenceChangeListener(this);
        
	}
	
	public void onResume(){
		super.onResume();
		Log.v("RESUME", "!!ONRESUME!!!");
	}
	
	public void showSettings()
	{
		Intent i = new Intent(this, Prefs.class);
		startActivity(i);
	}

	public void ReStartServices(){
				
		
		stopService(new Intent(this, ServiceExample.class));
		
		SystemClock.sleep(2000);
		
		startService(new Intent(this, ServiceExample.class));		
		Log.v(this.getClass().getName(), "Service: Restarted!");
		
				
	}
	
	public void onclick(View v){
		
		Log.v(this.getClass().getName(), "onClick: Starting service!");
		startService(new Intent(this, ServiceExample.class));
	}
	
	public void onclick2(View v){

		Log.v(this.getClass().getName(), "onClick: Stopping service!");
        stopService(new Intent(this, ServiceExample.class));
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			showSettings();
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		
		
		Log.v(this.getClass().getName(), "Pref="+prefs.getAll()+"; key="+key);
		
		
		switch (key) {
		case "bootOn":			
			prefs.getBoolean("bootOn", true);			
			break;
		case "INTERVAL":
			String inter = prefs.getString("INTERVAL", "30");
			
			ServiceExample.INTERVAL =  Integer.parseInt(inter) * 1000;
			//Log.v(this.getClass().getName(), "interv="+prefs.getInt("INTERVAL", 30)+"; key="+key);
			
			break;
		case "SizeToOff":
			String sizeoff = prefs.getString("SizeToOff", "30"); 
			
			ServiceExample.SizeToOff =  Integer.parseInt(sizeoff);
			//Log.v(this.getClass().getName(), "SizeToOff="+prefs.getInt("SizeToOff", 30)+"; key="+key);
			break;
			
		default:
			break;
		}
		

		ReStartServices();
		
	}

}
