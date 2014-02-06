package it.unitn.science.lpsmt.uotnod;

import it.unitn.science.lpsmt.uotnod.plugins.Plugin;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class MyApplication extends Application{
	private static Context context;
	public static final String WIFI = "Wi-Fi";
    public static final String ANY = "Any";
    public static final String PLUGINPKG = "it.unitn.science.lpsmt.uotnod.plugins";
    public static final String APPPKG = "it.unitn.science.lpsmt.uotnod";
    public static final String FAMILYPLUGINPKG = "it.unitn.science.lpsmt.uotnod.plugins.family.";
    public static final String SHOPSPLUGINPKG = "it.unitn.science.lpsmt.uotnod.plugins.shops.";
    public static boolean refreshDisplay = true; 
    public static String sPref = ANY;
	public static Plugin pluginInUse;
	
	@Override
	public void onCreate() {
		super.onCreate();
		MyApplication.context = getApplicationContext();
	}
	
	public static Context getAppContext(){
		return MyApplication.context;
	}
	
	public static final String DEBUGTAG="UoTNoD_debug";
	
	public static boolean isConnected(String type) {
		final ConnectivityManager cm = (ConnectivityManager) MyApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE); 
		final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		
		if (networkInfo == null) {
			return false;
		}
		else if (type.equals(networkInfo.getTypeName())) {
			return true;
		}
		else if (networkInfo.isConnected()){
			return true;
		}
		return false;
	}
	
	public static Boolean checkNetwork() {
		if((MyApplication.sPref.equals(MyApplication.ANY)) && (MyApplication.isConnected("WIFI") || MyApplication.isConnected("mobile"))) {
			return true;
	    }
	    else if ((MyApplication.sPref.equals(MyApplication.WIFI)) && (MyApplication.isConnected("WIFI"))) {
	    	return true;
	    } else {
	        Toast.makeText(MyApplication.getAppContext(), R.string.network_off, Toast.LENGTH_SHORT).show();
	        return false;
	    }	
	}
}
