package it.unitn.science.lpsmt.uotnod.plugins.shops;

import java.util.ArrayList;
import java.util.List;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ShopsShopFilterView extends Activity {
	
	private Spinner shopTypeSpinner;
	private Button btnSubmit;
	private UotnodDAO dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shops_shop_filter);		
		 
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		addItemsOnSpinner();
		addListenerOnButton();
		addListenerOnSpinnerItemSelection();
	}
	
	private void addListenerOnSpinnerItemSelection() {		
	}

	private void addListenerOnButton() {

		shopTypeSpinner = (Spinner) findViewById(R.id.spinner_shop_filter_type);
		
		btnSubmit = (Button) findViewById(R.id.button_filter);
	 
		btnSubmit.setOnClickListener(new View.OnClickListener() {
	 
		  @Override
		  public void onClick(View v) {
			  Intent returnIntent = new Intent();
		      returnIntent.putExtra("shopFilter", String.valueOf(shopTypeSpinner.getSelectedItem()));                          
		      setResult(Activity.RESULT_OK,returnIntent);
			  finish();
		  }	 
		});
	}
	

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	private void addItemsOnSpinner() {
		
		dao = new UotnodDAO_DB();
		dao.open();
		List<String> list = new ArrayList<String>();		
		list = dao.getAllShopsTypes();
		list.add(0,MyApplication.getAppContext().getResources().getString(R.string.any));
		dao.close();		
		
		shopTypeSpinner = (Spinner) findViewById(R.id.spinner_shop_filter_type);
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		shopTypeSpinner.setAdapter(dataAdapter);
	}
}
