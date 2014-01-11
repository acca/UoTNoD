package it.unitn.science.lpsmt.uotnod;

import it.unitn.science.lpsmt.uotnod.plugins.*;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class Dashboard extends ListActivity {

	private UotnodDAO dao;
	
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
