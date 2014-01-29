package it.unitn.science.lpsmt.uotnod.plugins.family;

import java.util.List;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;
import it.unitn.science.lpsmt.uotnod.UpdateManager;
import it.unitn.science.lpsmt.uotnod.plugins.Plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
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

public class FamilyOrgFragmentList extends ListFragment {
	
	private List<FamilyOrg> orgs;
	private UotnodDAO dao;
	private View rootView;
	
	int mCurCheckPosition = 0;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.family_org_fragment, container, false);
		dao = new UotnodDAO_DB();
		dao.open();		
		this.orgs = dao.getAllFamilyOrgs();		
		OrgAdapter adapter = new OrgAdapter(rootView.getContext(),R.layout.two_lines_list_item,this.orgs);		
		setListAdapter(adapter);
		setHasOptionsMenu(true);		
        return rootView;
    }

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.d(MyApplication.DEBUGTAG, "Item clicked");
		// Start ORg details activity
		showOrgDetails(position);

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
	        	showOrgFilter();
	            return true;
	        case R.id.action_refresh:
	        	doRefresh();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	void showOrgDetails(int orgId) {
        getListView().setItemChecked(orgId, true);
        Intent intent = new Intent(MyApplication.FAMILYPLUGINPKG + "FamilyOrgView");
        intent.putExtra("index", orgId);
        startActivity(intent);
	}
	
	void showOrgFilter() {        
        Intent intent = new Intent(MyApplication.FAMILYPLUGINPKG + "FamilyOrgFilterView");        
        startActivity(intent);
	}
	
	private void doRefresh(){
		UpdateManager myAsyncTask = new UpdateManager(getActivity());	    
		if (MyApplication.checkNetwork()) {
			Plugin plugin = MyApplication.inUsePlugin; 
			myAsyncTask.execute(plugin);
		}
	}

	private class OrgAdapter extends ArrayAdapter<FamilyOrg> {
		Context context;
	    public OrgAdapter(Context context, int textViewResourceId, List<FamilyOrg> items) {
	        super(context, textViewResourceId, items);
	        this.context = context;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        if (view == null) {
	            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = inflater.inflate(R.layout.two_lines_list_item, null);
	        }
	        FamilyOrg item = getItem(position);
	        if (item!= null) {
	            TextView itemTitle = (TextView) view.findViewById(R.id.title);
	            if (itemTitle != null) {
	                itemTitle.setText(item.getName());
	            }
	            TextView itemDesc = (TextView) view.findViewById(R.id.desc);
	            if (itemDesc != null) {
	                itemDesc.setText(item.getWebsite());
	            }
	         }
	        return view;
	    }
	}
	
	@Override
	public void onDestroy() {
		dao.close();
		super.onDestroy();
	}
}	
