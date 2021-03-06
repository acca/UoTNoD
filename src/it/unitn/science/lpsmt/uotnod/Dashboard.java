package it.unitn.science.lpsmt.uotnod;

import it.unitn.science.lpsmt.uotnod.plugins.*;

import java.util.Iterator;
import java.util.List;  

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends ListActivity {

	private UotnodDAO dao;
	
    // Whether the display should be refreshed.    
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
			Toast.makeText(this, MyApplication.getAppContext().getResources().getString(R.string.initMsg) + "\n" + initMsg,Toast.LENGTH_SHORT).show();      
		}
		
		PluginAdapter adapter = new PluginAdapter(this,R.layout.icon_two_lines_list_item,plugins);
		
		setListAdapter(adapter);
		
		ListView lv = getListView();
		
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,long id) {
				// Starting point for each plugin				
				Plugin selectedPlugin = (Plugin) adapter.getItemAtPosition(position);
				Log.d(MyApplication.DEBUGTAG,"Starting plugin: " + selectedPlugin.getLauncher());
				String actionName = MyApplication.PLUGINPKG + "." + new String(selectedPlugin.getLauncher()).toLowerCase() + "." + selectedPlugin.getLauncher();				
				Intent intent = new Intent(actionName);
				MyApplication.pluginInUse = selectedPlugin;
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
	        	if (pluginArr == null) {
	        		Toast.makeText(this, MyApplication.getAppContext().getResources().getString(R.string.no_enabled_plugin), Toast.LENGTH_SHORT).show();
	        	}
	        	else {
	        		doRefresh(pluginArr);	
	        	}	        		        		           
	            return true;
	        case R.id.action_setting:
	        	String actionName = MyApplication.APPPKG + ".Setting";
	        	Intent i = new Intent(actionName);
	        	startActivity(i);	        		            
	            return true;	
	        default:
	            return super.onOptionsItemSelected(item);
	    }	
	}

	private void doRefresh(Plugin... plugins){
	    UpdateManager myAsyncTask = new UpdateManager(this);	    
	    if (MyApplication.checkNetwork()) {
	    	myAsyncTask.execute(plugins);
	    }
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dao.close();
	}
	private class PluginAdapter extends ArrayAdapter<Plugin> {
		Context context;
		
	    PluginAdapter(Context context, int textViewResourceId, List<Plugin> items) {	    	
	    	super(context, textViewResourceId, items);
	    	this.context = context;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        if (view == null) {
	            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = inflater.inflate(R.layout.icon_two_lines_list_item, null);
	        }
	        Plugin item = getItem(position);
	        if (item!= null) {
	            TextView itemTitle = (TextView) view.findViewById(R.id.title);
	            if (itemTitle != null) {
	                itemTitle.setText(item.getName());
	            }
	            TextView itemDesc = (TextView) view.findViewById(R.id.desc);
	            if (itemDesc != null) {
	                itemDesc.setText(item.getDescription());
	            }
	            ImageView itemIcon = (ImageView) view.findViewById(R.id.icon);
	            if (itemIcon != null) {
	            	if (item.getLauncher().equals("Family")) {
	            		itemIcon.setImageDrawable(MyApplication.getAppContext().getResources().getDrawable(R.drawable.uotnod_fam));	
	            	}
	            	else if (item.getLauncher().equals("Shops")) {
	            		itemIcon.setImageDrawable(MyApplication.getAppContext().getResources().getDrawable(R.drawable.uotnod_sho));
	            	}
	            	else {
	            		itemIcon.setImageDrawable(MyApplication.getAppContext().getResources().getDrawable(R.drawable.uotnod));
	            	}
	            }
	         }
	        return view;
	    }
	}
}