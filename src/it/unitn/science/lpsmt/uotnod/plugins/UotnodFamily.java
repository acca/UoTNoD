package it.unitn.science.lpsmt.uotnod.plugins;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;
import it.unitn.science.lpsmt.uotnod.R.layout;
import it.unitn.science.lpsmt.uotnod.R.menu;
import it.unitn.science.lpsmt.uotnod.UotnodXMLParser;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UotnodFamily extends Activity {

	private List<UotnodFamilyOrg> orgs;
	private UotnodDAO dao; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uotnod_family);
		
		dao = new UotnodDAO_DB();
		dao.open();
		
		
		this.orgs = dao.getAllFamilyOrgs();
		
		ArrayAdapter<UotnodFamilyOrg> adapter = new ArrayAdapter<UotnodFamilyOrg>(this,android.R.layout.simple_list_item_1,this.orgs);
		
		//UotnodFamilyOrg org = new UotnodFamilyOrg();
		
		ListView listView = (ListView) findViewById(R.id.listview1);
		listView.setAdapter(adapter);
		
//		ListView lv = getListView();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.uotnod_family, menu);
		return true;
	}
}
