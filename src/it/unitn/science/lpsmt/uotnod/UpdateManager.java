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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import it.unitn.science.lpsmt.uotnod.UpdateManager.Progress;

// Implementation of AsyncTask used to download XML feed from stackoverflow.com.
public class UpdateManager extends AsyncTask<Plugin, Progress, String[]> {

	final UpdateManager me = this;
	
	private Activity mParentActivity;
	private int pluginCnt;
	private Progress progress;
	ProgressDialog progDialog;
	AlertDialog dialog;
	 
	
	public UpdateManager(Activity act) {
		mParentActivity = act;
		progDialog = new ProgressDialog(mParentActivity);
		progDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				me.cancel(true);
			}
				
		});
		
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(mParentActivity);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setTitle(R.string.title_update_manager_dialog);
		
		builder.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               // User cancelled the dialog
	        	   dialog.cancel();
	           }
	       });
		
		// 3. Get the AlertDialog from create()
		dialog = builder.create();
    }
		
    @Override
    protected String[] doInBackground(Plugin... plugins) {
    	this.pluginCnt = plugins.length;
    	String[] result = new String[this.pluginCnt];        	
    	for (int i = 0; i < this.pluginCnt; i++) {
    		this.progress = new Progress(i+1);
    		publishProgress(this.progress);
    		// Escape early if cancel() is called            
	    	try {			
	    		result[i] = loadXmlFromNetwork(plugins[i]);	    		
	        } catch (IOException e) {
	            result[i] = plugins[i].getName() + ": " + mParentActivity.getResources().getString(R.string.connection_error);
	        } catch (XmlPullParserException e) {
	            result[i] = plugins[i].getName() + ": " + mParentActivity.getResources().getString(R.string.xml_error);
	        }
	    	if (isCancelled()) break;
    	}			
		return result;
    }

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}
    
    @Override
    protected void onPreExecute() {
    	//mParentActivity.setContentView(R.layout.dashboard);    	
    	progDialog.setTitle("Updating plugin");
    	progDialog.show();
    }
    
    @Override
    protected void onProgressUpdate(Progress... values) {
    	progDialog.setTitle("Updating plugin " + "[" + ((Progress)values[0]).getPluginCnt()+"/"+this.pluginCnt + "]");
    	
    	//mParentActivity.setContentView(R.layout.dashboard);
    	Toast.makeText(MyApplication.getAppContext(), ((Progress)values[0]).getPluginCnt()+"/"+this.pluginCnt, Toast.LENGTH_SHORT).show();
    	//ProgressDialog pd = new ProgressDialog(MyApplication.getAppContext());
    	//pd.setTitle("Updating plugin" + ((Progress)values[0]).getPluginCnt()+"/"+this.pluginCnt);
    	//pd.show();
    	// TODO Auto-generated method stub
    	//super.onProgressUpdate(values);
    }
    
    @Override
    protected void onPostExecute(String[] results) {  
        String message = "";
    	for (String result : results) {
    		message = message + "- " + result + "\n";
		}
    	progDialog.dismiss();
    	dialog.setMessage(message);
		dialog.show();
        
    }
		
		
		// Uploads XML from stackoverflow.com, parses it, and combines it with
		// HTML markup. Returns HTML string.
		private String loadXmlFromNetwork(Plugin plugin) throws XmlPullParserException, IOException {
			Log.d(MyApplication.DEBUGTAG,"Sono in loadXmlFromNetwork");
			String urlString = plugin.getDataSrc();
			if (urlString.equals(""))  {
				return plugin.getName() + ": " + mParentActivity.getResources().getString(R.string.datasrc_error);
			}
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
	
		protected class Progress {
			private int pluginCnt;
			private String task;
			private int status;
			
			Progress(int pluginCnt){
				this.pluginCnt = pluginCnt;
			}	
			Progress(String task, int status){
				this.status = status;
				this.task = task;
			}
			public int getPluginCnt() {
				return pluginCnt;
			}
			public void setPluginCnt(int pluginCnt) {
				this.pluginCnt = pluginCnt;
			}
			public String getTask() {
				return task;
			}
			public void setTask(String task) {
				this.task = task;
			}
			public int getStatus() {
				return status;
			}
			public void setStatus(int status) {
				this.status = status;
			}
			
			
		}
}

