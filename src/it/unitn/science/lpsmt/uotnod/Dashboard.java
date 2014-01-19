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
    private static final String URL = "http://dati.trentino.it/storage/f/2013-05-08T083538/Estate-giovani-e-famiglia_2013.xml";
   
    // Whether there is a Wi-Fi connection.
    private static boolean wifiConnected = false; 
    // Whether there is a mobile connection.
    private static boolean mobileConnected = false;
    // Whether the display should be refreshed.
    public static boolean refreshDisplay = true; 
    public static String sPref = ANY;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		
		dao = new UotnodDAO_DB();
		dao.open();
		
		List<Plugin> values = dao.getAllPlugins(true);		
		
		ArrayAdapter<Plugin> adapter = new ArrayAdapter<Plugin>(this,android.R.layout.simple_list_item_1,values);
		
		setListAdapter(adapter);
		
		ListView lv = getListView();
		
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,long id) {
				// Starting point for each plugin
				
				Plugin selectedPlugin = (Plugin) adapter.getItemAtPosition(position);
				Log.d(MyApplication.DEBUGTAG,selectedPlugin.getLauncher());
				String actionName = "it.unitn.science.lpsmt.uotnod.plugins."+selectedPlugin.getLauncher();				
				Intent intent = new Intent(actionName);
				startActivity(intent);
			}

		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		// Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_refresh:
	            doRefresh();
	            return true;	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }	
	}

	private void doRefresh(){
	    DownloadXmlTask myAsyncTask = new DownloadXmlTask();
	    
		if((sPref.equals(ANY)) && (MyApplication.isConnected("WIFI") || MyApplication.isConnected("mobile"))) {
			myAsyncTask.execute(URL);
        }
        else if ((sPref.equals(WIFI)) && (MyApplication.isConnected("WIFI"))) {
        	myAsyncTask.execute(URL);
        } else {
            Toast.makeText(MyApplication.getAppContext(), R.string.network_off, Toast.LENGTH_SHORT).show();
        }			    
	}
	
	// Implementation of AsyncTask used to download XML feed from stackoverflow.com.
	private class DownloadXmlTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... urls) {
	        try {
	            return loadXmlFromNetwork(urls[0]);
	        } catch (IOException e) {
	            return getResources().getString(R.string.connection_error);
	        } catch (XmlPullParserException e) {
	            return getResources().getString(R.string.xml_error);
	        }
	    }

	    @Override
	    protected void onPostExecute(String result) {  
	        setContentView(R.layout.dashboard);
	        Toast.makeText(MyApplication.getAppContext(), result, Toast.LENGTH_SHORT).show();
	    }
	}
	
	// Uploads XML from stackoverflow.com, parses it, and combines it with
	// HTML markup. Returns HTML string.
	private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
		Log.d(MyApplication.DEBUGTAG,"Sono in loadXmlFromNetwork");
	    InputStream stream = null;
	    // Instantiate the parser
	    //UotnodXMLParser parser = new UotnodXMLParser();
  		UotnodFamilyOrgParser orgParser = new UotnodFamilyOrgParser();
  		//InputStream raw;
  		List<Entry> entries = null;
  		
  		UotnodDAO dao;
  		dao = new UotnodDAO_DB();
  		dao.open();
  				    
	    // Checks whether the user set the preference to include summary text
	    //SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
	    //boolean pref = sharedPrefs.getBoolean("summaryPref", false);
	        
	        
	    try {
	        stream = downloadUrl(urlString);
	        Log.d(MyApplication.DEBUGTAG,stream.toString());
	        entries = orgParser.parse(stream);
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (stream != null) {
	            stream.close();
	        } 
	     }
	    Log.d(MyApplication.DEBUGTAG,"Fuori dal blocco try-finally");
	    List<Entry> orgInDb = dao.getAllFamilyOrgs();
  		List<Entry> orgInXml = entries;
  		
  		Log.d(MyApplication.DEBUGTAG,"Read " + orgInXml.size() + " record from XML.");
  		Log.d(MyApplication.DEBUGTAG,"Read " + orgInDb.size() + " record from DB.");
  		
  		if (orgInXml.removeAll(orgInDb))
  			entries = (List<Entry>) orgInXml;
  		Iterator<Entry> iterator = entries.iterator();
  		
  		
  		Log.d(MyApplication.DEBUGTAG,"Updating " + entries.size() + " record from XML source to DB.");
  		
  		while (iterator.hasNext()) {
  			UotnodFamilyOrg org = (UotnodFamilyOrg) iterator.next();
  			Log.d(MyApplication.DEBUGTAG,"Trying to insert a new family Org in DB:" + org.toString());
  			dao.insertFamilyOrg(org);
  		}
	    
	    return "Parse compeltato (loadXmlFrom Network)";
	}

	// Given a string representation of a URL, sets up a connection and gets
	// an input stream.
	private InputStream downloadUrl(String urlString) throws IOException {
	    URL url = new URL(urlString);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setReadTimeout(10000 /* milliseconds */);
	    conn.setConnectTimeout(15000 /* milliseconds */);
	    conn.setRequestMethod("GET");
	    conn.setDoInput(true);
	    // Starts the query
	    conn.connect();	    
	    return conn.getInputStream();
	}
}