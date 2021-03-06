package it.unitn.science.lpsmt.uotnod;

import it.unitn.science.lpsmt.uotnod.plugins.Plugin;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import it.unitn.science.lpsmt.uotnod.UpdateManager.Progress;


// Implementation of AsyncTask used to download XML feed from stackoverflow.com.
public class UpdateManager extends AsyncTask<Plugin, Progress, String[]> {

	final UpdateManager me = this;
	
	private Activity mParentActivity;
	private ProgressDialog progDialog;
	private AlertDialog dialog;
	private int status;
	private EventListener updateListener;

	public UpdateManager(Activity act) {
		mParentActivity = act;
		
		// Initialize Progress Dialog
		progDialog = new ProgressDialog(mParentActivity);
		progDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				me.cancel(true);
			}				
		});
		progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progDialog.setProgress(0);
		progDialog.setMessage("Initializing update...");
		progDialog.setMax(100);		
		
		// Initialize Results Dialog		
		AlertDialog.Builder builder = new AlertDialog.Builder(mParentActivity);
		builder.setTitle(MyApplication.getAppContext().getResources().getString(R.string.title_update_manager_dialog));		
		builder.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
				dialog.cancel();
			}
		});
		dialog = builder.create();
    }
		
	public void setEventListener(EventListener updateListener) {
	    this.updateListener = updateListener;
	}
		
    @Override
    protected String[] doInBackground(Plugin... plugins) {
    	if (plugins.length == 0) {
    		return new String[] {MyApplication.getAppContext().getResources().getString(R.string.no_enabled_plugin)};
    	}
    	this.status = (progDialog.getMax()/(plugins.length*2));
    	Progress progress = new Progress(plugins.length);
    	String[] results = new String[plugins.length];        	
    	for (int i = 0; i < plugins.length; i++) {
    		progress.setPluginCnt(i+1);
    		publishProgress(progress);       
	    	try {	
	    		results[i] = loadXmlFromNetwork(plugins[i], progress);	    		
	        } catch (IOException e) {
	            results[i] = plugins[i].getName() + ": " + MyApplication.getAppContext().getResources().getString(R.string.connection_error);
	        }
    		// Escape early if cancel() is called     
	    	if (isCancelled()) break;
    	}			
		return results;
    }

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}
    
    @Override
    protected void onPreExecute() {
    	progDialog.setTitle("Updating plugin");
    	progDialog.show();
    }
    
    @Override
    protected void onProgressUpdate(Progress... values) {
    	Progress progress = ((Progress)values[0]);
    	progDialog.setTitle("Updating plugin " + "[" + progress.getPluginCnt()+"/" + progress.getPluginTot() + "]");
    	progDialog.setMessage(progress.getTask());
    	progDialog.setProgress(progress.getStatus());    	
    	//Toast.makeText(MyApplication.getAppContext(), "[" + progress.getPluginCnt()+"/" + progress.getPluginTot() + "]", Toast.LENGTH_SHORT).show();    	
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
		if (updateListener != null) updateListener.updateDone(true);		
		//MyApplication.refreshingAdapter.notifyDataSetChanged();
		//MyApplication.refreshingAdapter = null;
    }
		
    // Download XML from data source, parses it, and update internal storage if needed (db, cached file)
	private String loadXmlFromNetwork(Plugin plugin, Progress progress) throws IOException{
		progress.setStatus(progress.getStatus()+this.status);
		progress.setName(plugin.getName());
		publishProgress(progress);		
		String urlString = plugin.getDataSrc();
		if (urlString.equals(""))  {
			return plugin.getName() + ":\n" + MyApplication.getAppContext().getResources().getString(R.string.datasrc_error);
		}		
		// Downlaod from Data source
		InputStream stream = null;
	    try {	    	
	        stream = downloadUrl(urlString, progress);
	        progress.setTask(progress.getName() + ":\n" + MyApplication.getAppContext().getResources().getString(R.string.parse_task));
	        publishProgress(progress);
	        return plugin.parse(stream);
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (stream != null) {
	            stream.close();
	        } 
	    }
	}

	// Given a string representation of a URL, sets up a connection and gets
	// an input stream.
	private InputStream downloadUrl(String urlString, Progress progress) throws IOException {
		progress.setTask(progress.getName() + ":\n" + MyApplication.getAppContext().getResources().getString(R.string.download_task));
		publishProgress(progress);
	    URL url = new URL(urlString);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setReadTimeout(10000 /* milliseconds */);
	    conn.setConnectTimeout(15000 /* milliseconds */);
	    conn.setRequestMethod("GET");
	    conn.setDoInput(true);	    
	    String contentEncoding = null;	    
	    conn.connect();
	    InputStream input = conn.getInputStream();	    
	    contentEncoding = conn.getHeaderField("Content-Type");
	    //if (contentEncoding != null) Log.d(MyApplication.DEBUGTAG,contentEncoding);
	    if ("application/zip".equals(contentEncoding)) {
	    	InputStream is = input;
	    	ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
	    	try {
	    		@SuppressWarnings("unused")
				ZipEntry ze;
	    		while ((ze = zis.getNextEntry()) != null) {
	    			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    			byte[] buffer = new byte[1024];
	    			int count;
	    			while ((count = zis.read(buffer)) != -1) {
	    				baos.write(buffer, 0, count);
	    			}
	    			//String filename = ze.getName();
	    			byte[] bytes = baos.toByteArray();
	    			input = new ByteArrayInputStream(bytes);
	    		}
	    	} finally {
	    		zis.close();
	    	}
	    }
	    return input;
	}
	
	public class Progress {
		private int pluginTot;
		private int pluginCnt;
		private String task;
		private String name;
		private int status;
		
		Progress(int pluginTot){
			this.pluginTot = pluginTot;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
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
		public int getPluginTot() {
			return pluginTot;
		}
		public void setPluginTot(int pluginTot) {
			this.pluginTot = pluginTot;
		}
	}
	
	public interface EventListener {
	    void updateDone(boolean isFinished);
	}
}


