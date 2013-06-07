package it.unitn.science.lpsmt.uotnod;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class Dashboard extends ListActivity {

	private PluginDAO dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		
		dao = new PluginDAO_DB();
		dao.open();
		
		List<Plugin> values = dao.getAllPlugin();		
		
		ArrayAdapter<Plugin> adapter = new ArrayAdapter<Plugin>(this,android.R.layout.simple_list_item_1,values);
		
		setListAdapter(adapter);
		
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				// Starting point for each plugin
				
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
