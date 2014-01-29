package it.unitn.science.lpsmt.uotnod.plugins.family;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class FamilyActFragment extends Fragment {
	
	private List<FamilyAct> acts;
	private UotnodDAO dao;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.family_act_fragment, container, false);
		dao = new UotnodDAO_DB();
		dao.open();		
		this.acts = dao.getAllFamilyActs();		
		dao.close();
		ActAdapter adapter = new ActAdapter(rootView.getContext(),R.layout.two_lines_list_item,this.acts);
		ListView listView = (ListView) rootView.findViewById(R.id.listview1);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		    	Log.d(MyApplication.DEBUGTAG,"Cliccata una attività");
		    }
		});	
		setHasOptionsMenu(true);
        return rootView;
    }
	
	private class ActAdapter extends ArrayAdapter<FamilyAct> {
		Context context;
	    public ActAdapter(Context context, int textViewResourceId, List<FamilyAct> items) {
	        super(context, textViewResourceId, items);
	        this.context = context;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        if (view == null) {
	            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = inflater.inflate(R.layout.two_lines_list_item, null);
	        }
	        FamilyAct item = getItem(position);
	        if (item!= null) {
	            TextView itemTitle = (TextView) view.findViewById(R.id.title);
	            if (itemTitle != null) {
	            	String name = item.getName(); 
	            	if ((name.length()) > 30){
	            		name = item.getDesc().substring(0, 30) + "...";
	            	}
	            	itemTitle.setText(name);
	            }
	            TextView itemDesc = (TextView) view.findViewById(R.id.desc);
	            if (itemDesc != null) {
	            	String desc = item.getDesc(); 
	            	if ((desc.length()) > 120){
	            		desc = item.getDesc().substring(0, 120) + "...";
	            	}	            	
	                itemDesc.setText(desc);
	            }
	         }
	        return view;
	    }
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu items for use in the action bar
	    inflater.inflate(R.menu.family_actions, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	            Toast.makeText(getActivity(), R.string.not_implemented, Toast.LENGTH_SHORT).show();
	            return true;
	        case R.id.action_refresh:
	        	Toast.makeText(getActivity(), R.string.not_implemented, Toast.LENGTH_SHORT).show();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}

