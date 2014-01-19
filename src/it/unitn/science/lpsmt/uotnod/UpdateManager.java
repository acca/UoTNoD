package it.unitn.science.lpsmt.uotnod;

import it.unitn.science.lpsmt.uotnod.plugins.Entry;
import it.unitn.science.lpsmt.uotnod.plugins.UotnodFamilyOrg;
import it.unitn.science.lpsmt.uotnod.plugins.UotnodFamilyOrgParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

// Implementation of AsyncTask used to download XML feed from stackoverflow.com.
public class UpdateManager extends AsyncTask< String, Void, String> {

	private Activity mParentActivity;
	
	public UpdateManager(Activity act) {
		mParentActivity = act;
        
    }
		
		    @Override
		    protected String doInBackground(String... urls) {
		        try {
		            return loadXmlFromNetwork(urls[0]);
		        } catch (IOException e) {
		            return mParentActivity.getResources().getString(R.string.connection_error);
		        } catch (XmlPullParserException e) {
		            return mParentActivity.getResources().getString(R.string.xml_error);
		        }
		    }

		    @Override
		    protected void onPostExecute(String result) {  
		        mParentActivity.setContentView(R.layout.dashboard);
		        Toast.makeText(MyApplication.getAppContext(), result, Toast.LENGTH_SHORT).show();
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

