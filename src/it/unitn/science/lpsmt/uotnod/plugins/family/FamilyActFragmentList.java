package it.unitn.science.lpsmt.uotnod.plugins.family;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;
import it.unitn.science.lpsmt.uotnod.UpdateManager;
import it.unitn.science.lpsmt.uotnod.UpdateManager.EventListener;
import it.unitn.science.lpsmt.uotnod.plugins.Plugin;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class FamilyActFragmentList extends Fragment implements EventListener {
	
	private List<FamilyAct> acts;
	private UotnodDAO dao;
	private ListView listView;
	private ActAdapter adapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
		
        View rootView = inflater.inflate(R.layout.family_act_fragment, container, false);
		dao = new UotnodDAO_DB();
		dao.open();		
		this.acts = dao.getAllFamilyActs();		
		this.adapter = new ActAdapter(rootView.getContext(),R.layout.two_lines_list_item,this.acts);
		this.listView = (ListView) rootView.findViewById(R.id.listview1);
		this.listView.setAdapter(adapter);
		this.listView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		    	FamilyAct myAct = (FamilyAct) l.getAdapter().getItem(position);
		    	showActDetails((int)myAct.getId());
		    }
		});
		setHasOptionsMenu(true);
        return rootView;
    }
	
	@Override
	public void onResume() {	
		super.onResume();		
		Family parentActivity = (Family)this.getActivity();
		String type = parentActivity.getFilter();
		if (type == null) return;
		if ( ( !type.equals("ANY") ) ){
			this.acts = dao.getAllFamilyActByType(type);
			this.adapter.clear();
        	this.adapter.addAll(this.acts);
			this.adapter.notifyDataSetChanged();
		}
		else {
			this.acts = dao.getAllFamilyActs();
			this.adapter.clear();
        	this.adapter.addAll(this.acts);
			this.adapter.notifyDataSetChanged();
		}		
		
		TextView tv = (TextView)getActivity().findViewById(R.id.textView1);
		tv.setText(MyApplication.getAppContext().getResources().getString(R.string.show_filter_msg) + type);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu items for use in the action bar
	    inflater.inflate(R.menu.family_act_actions, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public void onDestroy() {
		dao.close();
		super.onDestroy();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	        	showActFilter();
	            return true;
	        case R.id.action_refresh:
	        	doRefresh();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	void showActDetails(int actId) {
        this.listView.setItemChecked(actId, true);
        Intent intent = new Intent(MyApplication.FAMILYPLUGINPKG + "FamilyActView");
        intent.putExtra("index", actId);
        startActivity(intent);
	}
	
	void showActFilter() {
        Intent intent = new Intent(MyApplication.FAMILYPLUGINPKG + "FamilyActFilterView");
        getActivity().startActivityForResult(intent,1);
	}
	
	private void doRefresh(){
		UpdateManager myAsyncTask = new UpdateManager(getActivity());	    
		if (MyApplication.checkNetwork()) {
			Plugin plugin = MyApplication.pluginInUse;
			myAsyncTask.setEventListener(this);
			myAsyncTask.execute(plugin);			
		}
	}

	protected static class ActAdapter extends ArrayAdapter<FamilyAct> {
		Context context;
	    public ActAdapter(Context context, int textViewResourceId, List<FamilyAct> items) {
	        super(context, textViewResourceId, items);
	        this.context = context;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        if (view == null) {
	            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = inflater.inflate(R.layout.small_icon_two_lines_list_item, null);
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
	            ImageView itemIcon = (ImageView) view.findViewById(R.id.icon);
	            if (itemIcon != null) {		            	
	            	itemIcon.setImageDrawable(MyApplication.getAppContext().getResources().getDrawable(R.drawable.family));			            	
	            }     
	         }
	        return view;
	    }
	}

	@Override
	public void updateDone(boolean isFinished) {
		if (isFinished) {
			this.acts = dao.getAllFamilyActs();
			this.adapter.clear();
			this.adapter.addAll(this.acts);
			this.adapter.notifyDataSetChanged();
		}
	}	
}

