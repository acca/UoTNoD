package it.unitn.science.lpsmt.uotnod;

import it.unitn.science.lpsmt.uotnod.plugins.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ListActivity;
import android.content.Intent;
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
		//UotnodXMLParser parser = new UotnodXMLParser();
		UotnodFamilyOrgParser orgParser = new UotnodFamilyOrgParser();
		InputStream raw;
		List<Entry> entries = null;
		
		try {
			raw = getApplicationContext().getAssets().open("Estate-giovani-e-famiglia_2013.xml");
			//InputStream object = this.getResources().openRawResource(R.raw.fileName);			
			try {
				entries = orgParser.parse(raw);
				
				//OrgParser orgParser = new OrgParser(raw);
				//entries = orgParser.parse();
				
				//Poliparser2 pippo = new Poliparser2(raw);				
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
		UotnodDAO dao;
		dao = new UotnodDAO_DB();
		dao.open();
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

	}
}