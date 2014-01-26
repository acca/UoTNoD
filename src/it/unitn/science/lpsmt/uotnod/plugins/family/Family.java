package it.unitn.science.lpsmt.uotnod.plugins.family;

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
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Family extends Activity {

	private List<FamilyOrg> orgs;
	private UotnodDAO dao; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uotnod_family);
		
		dao = new UotnodDAO_DB();
		dao.open();
		
		
		this.orgs = dao.getAllFamilyOrgs();
		
		//ArrayAdapter<FamilyOrg> adapter = new ArrayAdapter<FamilyOrg>(this,android.R.layout.simple_list_item_1,this.orgs);
		MyClassAdapter adapter = new MyClassAdapter(this,R.layout.list_item,this.orgs);
		
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
	
	private class MyClassAdapter extends ArrayAdapter<FamilyOrg> {

		Context context;

	    public MyClassAdapter(Context context, int textViewResourceId, List<FamilyOrg> items) {
	        super(context, textViewResourceId, items);
	        this.context = context;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        if (view == null) {
	            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = inflater.inflate(R.layout.list_item, null);
	        }

	        FamilyOrg item = getItem(position);
	        if (item!= null) {
	            // My layout has only one TextView
	            TextView itemTitle = (TextView) view.findViewById(R.id.title);
	            if (itemTitle != null) {
	                // do whatever you want with your string and long
	                itemTitle.setText(item.getName());
	            }
	            TextView itemDesc = (TextView) view.findViewById(R.id.desc);
	            if (itemDesc != null) {
	                // do whatever you want with your string and long
	                itemDesc.setText(item.getWebsite());
	            }
	         }

	        return view;
	    }
	}
}


