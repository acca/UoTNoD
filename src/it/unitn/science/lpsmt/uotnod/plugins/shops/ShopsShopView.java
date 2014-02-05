package it.unitn.science.lpsmt.uotnod.plugins.shops;

import java.util.List;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopsShopView extends Activity {

	private UotnodDAO_DB dao;
	private ShopsShop myShop;
	private LinearLayout linearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shops_shop);
		linearLayout = (LinearLayout)findViewById(R.id.org_view_inner);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		int index = intent.getIntExtra("index", 0);

		if (index != 0) {
			dao = new UotnodDAO_DB();
			dao.open();		
			this.myShop = dao.getShopsShopById(index);
			showShop();
			if (this.myShop.getShopsType() != null) showType();
		}		
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
        	NavUtils.navigateUpFromSameTask(this);
        	return true;

        case R.id.action_goshop:
        	// Visit website
        	goShop();
        	return true;
        default:
        	return super.onOptionsItemSelected(item);
        }
    }
	
	@Override
	protected void onDestroy() {	
		super.onDestroy();
		dao.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.shop_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	private void showShop(){
		
		int numParam = 2;
		TextView[] tvL = new TextView[numParam];
		TextView[] tvV = new TextView[numParam];
		for (int i = 0; i < numParam; i++) {
			tvL[i] = new TextView(this);
			tvL[i].setTypeface(Typeface.DEFAULT_BOLD);
			tvV[i] = new TextView(this);
			tvV[i].setTextIsSelectable(true);
		}

		tvL[0].setText("Name: ");
		tvV[0].setText(this.myShop.getName());		
		

		tvL[1].setText("Address: ");
		tvV[1].setText(this.myShop.getStreet() + " " + this.myShop.getStreetNum());		
				

		for (int i = 0; i < numParam; i++) {
			linearLayout.addView(tvL[i]);
			linearLayout.addView(tvV[i]);			
		}	
			
	}
	
	private void showType(){
		TypeAdapter adapter = new TypeAdapter(this,R.layout.two_lines_list_item,this.myShop.getShopsType());
		ListView listView = (ListView) findViewById(R.id.listview1);
		listView.setAdapter(adapter);
	}
	
	private void goShop(){
		if (this.myShop.getPoint() != null) {
		String latitude = this.myShop.getPoint().latitude+"";
		String longitude = this.myShop.getPoint().longitude+"";
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
				Uri.parse("http://maps.google.com/maps?daddr=" + latitude.substring(0, 6) + "," + longitude.substring(0, 6)));
				startActivity(intent);
		}
		else {	
			Toast.makeText(this, MyApplication.getAppContext().getResources().getString(R.string.noshop), Toast.LENGTH_SHORT).show();
		}
	}
	
	private class TypeAdapter extends ArrayAdapter<ShopsType> {
		Context context;
		//List<ShopsShop> shops;
		
	    TypeAdapter(Context context, int textViewResourceId, List<ShopsType> items) {	    	
	    	super(context, textViewResourceId, items);
	    	this.context = context;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        if (view == null) {
	            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = inflater.inflate(R.layout.two_lines_list_item, null);
	        }
	        ShopsType item = getItem(position);
	        if (item!= null) {
	            TextView itemTitle = (TextView) view.findViewById(R.id.title);
	            if (itemTitle != null) {
	                itemTitle.setText(item.getTypeFormatted());
	            }
	         }
	        return view;
	    }
	}
}
