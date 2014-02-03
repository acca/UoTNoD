package it.unitn.science.lpsmt.uotnod;

import it.unitn.science.lpsmt.uotnod.plugins.Plugin;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends Activity {
	
	private UotnodDAO dao;
	private List<Plugin> plugins;
	private ListView listView;
	private PluginSettingAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);		
		dao = new UotnodDAO_DB();
		dao.open();

		this.plugins = dao.getAllPlugins(false);

		this.listView = (ListView)findViewById(R.id.listview1); 
		this.adapter = new PluginSettingAdapter(this,R.layout.setting_list_item,this.plugins);
		this.listView.setAdapter(adapter);
			
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
        	NavUtils.navigateUpFromSameTask(this);
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dao.close();
	}

	private class PluginSettingAdapter extends ArrayAdapter<Plugin> implements OnCheckedChangeListener {
		Context context;
		
	    PluginSettingAdapter(Context context, int textViewResourceId, List<Plugin> items) {	    	
	    	super(context, textViewResourceId, items);
	    	this.context = context;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        if (view == null) {
	            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = inflater.inflate(R.layout.setting_list_item, null);
	        }
	        Plugin item = getItem(position);
	        if (item!= null) {
	            TextView itemTitle = (TextView) view.findViewById(R.id.title);
	            if (itemTitle != null) {
	                itemTitle.setText(item.getName());
	            }
	            TextView itemDesc = (TextView) view.findViewById(R.id.desc);
	            if (itemDesc != null) {
	                itemDesc.setText("Launcher: " + item.getLauncher() + "\nDatasource: " + item.getDataSrc());
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
	            CheckBox itemCheck = (CheckBox) view.findViewById(R.id.check);
	            if ( (itemCheck != null) && (item.getStatus()) ) {
	            	itemCheck.setChecked(true);
	            }
	            itemCheck.setTag(item.getId());
	            itemCheck.setOnCheckedChangeListener(this);	            
	         }
	        return view;
	    }
	    @Override
	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	    	UotnodDAO_DB dao = new UotnodDAO_DB();
	    	dao.open();
	    	long pId = (Long)buttonView.getTag();
	    	Plugin p = dao.getPluginById(pId);
	    	if ( (isChecked) && (!p.getStatus()) ){
	    		p.setStatus(true);
	    		dao.enablePlugin(p);
	    		Toast.makeText(MyApplication.getAppContext(), "Enabled plugin: " + p.getName(), Toast.LENGTH_SHORT).show();
	    	}
	    	else if (p.getStatus()) {
	    		p.setStatus(false);
	    		dao.disablePlugin(p);
	    		Toast.makeText(MyApplication.getAppContext(), "Disabled plugin: " + p.getName(), Toast.LENGTH_SHORT).show();
	    	}
 
			this.clear();
			this.addAll(dao.getAllPlugins(false));
			this.notifyDataSetChanged();
	    	dao.close();
	    }
	}
}
