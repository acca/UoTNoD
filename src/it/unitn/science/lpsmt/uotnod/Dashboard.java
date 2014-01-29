package it.unitn.science.lpsmt.uotnod;

import it.unitn.science.lpsmt.uotnod.plugins.*;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;
import java.net.URL;  

import org.xmlpull.v1.XmlPullParserException;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;



public class Dashboard extends ListActivity {

	private UotnodDAO dao;
	public static final String WIFI = "Wi-Fi";
    public static final String ANY = "Any";
    private static final String PLUGINPREFIX = "it.unitn.science.lpsmt.uotnod.plugins";
   
    
    // Whether the display should be refreshed.
    public static boolean refreshDisplay = true; 
    public static String sPref = ANY;
    private List<Plugin> plugins;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		
		dao = new UotnodDAO_DB();
		dao.open();
		
		this.plugins = dao.getAllPlugins(true);
		
		Iterator<Plugin> iterator = this.plugins.iterator();
		String initMsg = "";
		while (iterator.hasNext()){
			Plugin plugin = (Plugin)iterator.next();
			if (plugin.isEmpty()) initMsg += "\n\n- " + plugin.getName();
		}
		if (!initMsg.isEmpty()) {			
			Toast.makeText(this, MyApplication.getAppContext().getResources().getString(R.string.initMsg) + "\n" + initMsg,Toast.LENGTH_LONG).show();      
		}
		
		ArrayAdapter<Plugin> adapter = new ArrayAdapter<Plugin>(this,android.R.layout.simple_list_item_1,this.plugins);
		
		setListAdapter(adapter);
		
		ListView lv = getListView();
		
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,long id) {
				// Starting point for each plugin				
				Plugin selectedPlugin = (Plugin) adapter.getItemAtPosition(position);
				Log.d(MyApplication.DEBUGTAG,"Starting plugin: " + selectedPlugin.getLauncher());
				String actionName = PLUGINPREFIX + "." + new String(selectedPlugin.getLauncher()).toLowerCase() + "." + selectedPlugin.getLauncher();				
				Intent intent = new Intent(actionName);
				startActivity(intent);
			}
		});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.dashboard_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		// Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_refresh:
	        	Plugin[] pluginArr = this.plugins.toArray(new Plugin[this.plugins.size()]);
	        	doRefresh(pluginArr);	        		            
	            return true;	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }	
	}

	private void doRefresh(Plugin... plugins){
	    UpdateManager myAsyncTask = new UpdateManager(this);
	    
		if((sPref.equals(ANY)) && (MyApplication.isConnected("WIFI") || MyApplication.isConnected("mobile"))) {
			myAsyncTask.execute(plugins);
        }
        else if ((sPref.equals(WIFI)) && (MyApplication.isConnected("WIFI"))) {
        	myAsyncTask.execute(plugins);
        } else {
            Toast.makeText(MyApplication.getAppContext(), R.string.network_off, Toast.LENGTH_SHORT).show();
        }			    
	}
}