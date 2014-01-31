package it.unitn.science.lpsmt.uotnod.plugins.shops;

import java.util.List;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;
import it.unitn.science.lpsmt.uotnod.UpdateManager;
import it.unitn.science.lpsmt.uotnod.UpdateManager.EventListener;
import it.unitn.science.lpsmt.uotnod.plugins.Plugin;
import it.unitn.science.lpsmt.uotnod.plugins.family.FamilyOrg;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShopsShopsFragmentList extends ListFragment implements EventListener {
		
		private UotnodDAO dao;
		private View rootView;
		private ShopAdapter adapter;
		//private ListFragment me = this;
		List<ShopsShop> shops;
		
		int mCurCheckPosition = 0;
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.family_org_fragment, container, false);
			dao = new UotnodDAO_DB();
			dao.open();		
			shops = dao.getAllShopsShops();	
			adapter = new ShopAdapter(rootView.getContext(),R.layout.two_lines_list_item,shops);		
			setListAdapter(adapter);
			setHasOptionsMenu(true);		
	        return rootView;
	    }

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// Start Shop details activity
			ShopsShop myShop = (ShopsShop) l.getAdapter().getItem(position);			
			showShopDetails((int)myShop.getId());
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
		        	showShopFilter();
		            return true;
		        case R.id.action_refresh:	        	
		        	doRefresh();    	
		            return true;
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}
		
		void showShopDetails(int shopId) {
	        getListView().setItemChecked(shopId, true);
	        Intent intent = new Intent(MyApplication.SHOPSPLUGINPKG + "ShopsShopView");
	        intent.putExtra("index", shopId);
	        startActivity(intent);
		}
		
		void showShopFilter() {        
	        Intent intent = new Intent(MyApplication.SHOPSPLUGINPKG + "ShopsShopFilterView");        
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
			
		private class ShopAdapter extends ArrayAdapter<ShopsShop> {
			Context context;
			//List<ShopsShop> shops;
			
		    ShopAdapter(Context context, int textViewResourceId, List<ShopsShop> items) {	    	
		    	super(context, textViewResourceId, items);
		    	this.context = context;
		    }

		    public View getView(int position, View convertView, ViewGroup parent) {
		        View view = convertView;
		        if (view == null) {
		            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		            view = inflater.inflate(R.layout.two_lines_list_item, null);
		        }
		        ShopsShop item = getItem(position);
		        if (item!= null) {
		            TextView itemTitle = (TextView) view.findViewById(R.id.title);
		            if (itemTitle != null) {
		                itemTitle.setText(item.getName());
		            }
		            TextView itemDesc = (TextView) view.findViewById(R.id.desc);
		            if (itemDesc != null) {
		                itemDesc.setText(item.getStreet() + " " + item.getStreetNum());
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
				this.shops = dao.getAllShopsShops();
				this.adapter.clear();
	        	this.adapter.addAll(this.shops);
				this.adapter.notifyDataSetChanged();
			}
		}}
