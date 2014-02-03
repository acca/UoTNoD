package it.unitn.science.lpsmt.uotnod.plugins.family;

import java.util.List;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.UpdateManager.EventListener;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;
import it.unitn.science.lpsmt.uotnod.UpdateManager;
import it.unitn.science.lpsmt.uotnod.plugins.Plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FamilyOrgFragmentList extends ListFragment implements EventListener {
	
	private UotnodDAO dao;
	private View rootView;
	private OrgAdapter adapter;
	//private ListFragment me = this;
	List<FamilyOrg> orgs;
	
	int mCurCheckPosition = 0;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.family_org_fragment, container, false);
		dao = new UotnodDAO_DB();
		dao.open();		
		orgs = dao.getAllFamilyOrgs();	
		adapter = new OrgAdapter(rootView.getContext(),R.layout.small_icon_two_lines_list_item,orgs);		
		setListAdapter(adapter);
		setHasOptionsMenu(true);		
        return rootView;
    }

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {		
		FamilyOrg myOrg = (FamilyOrg) l.getAdapter().getItem(position);
		showOrgDetails((int)myOrg.getOrgId());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu items for use in the action bar
	    inflater.inflate(R.menu.family_org_actions, menu);
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
			Plugin plugin = MyApplication.pluginInUse;
			myAsyncTask.setEventListener(this);
			myAsyncTask.execute(plugin);			
		}
	}
		
	protected static class OrgAdapter extends ArrayAdapter<FamilyOrg> {
		Context context;
		//List<FamilyOrg> orgs;
		
	    OrgAdapter(Context context, int textViewResourceId, List<FamilyOrg> items) {	    	
	    	super(context, textViewResourceId, items);
	    	this.context = context;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        if (view == null) {
	            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = inflater.inflate(R.layout.small_icon_two_lines_list_item, null);
	        }
	        FamilyOrg item = getItem(position);
	        if (item!= null) {
	            TextView itemTitle = (TextView) view.findViewById(R.id.title);
	            if (itemTitle != null) {
	                itemTitle.setText(item.getName());
	            }
	            TextView itemDesc = (TextView) view.findViewById(R.id.desc);
	            if (itemDesc != null) {
	            	if (!item.getWebsite().equals("0")) itemDesc.setText(item.getWebsite());
	            	else itemDesc.setText("");
	            }
	            ImageView itemIcon = (ImageView) view.findViewById(R.id.icon);
	            if (itemIcon != null) {		            	
	            	itemIcon.setImageDrawable(MyApplication.getAppContext().getResources().getDrawable(R.drawable.family));			            	
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

	@Override
	public void updateDone(boolean isFinished) {
		if (isFinished) { 			
			this.orgs = dao.getAllFamilyOrgs();
			this.adapter.clear();
        	this.adapter.addAll(this.orgs);
			this.adapter.notifyDataSetChanged();
		}
	}
}	
