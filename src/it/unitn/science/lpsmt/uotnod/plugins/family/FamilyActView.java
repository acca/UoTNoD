package it.unitn.science.lpsmt.uotnod.plugins.family;

import java.util.ArrayList;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;
import it.unitn.science.lpsmt.uotnod.plugins.family.FamilyOrgFragmentList.OrgAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FamilyActView extends Activity {

	private UotnodDAO_DB dao;
	private FamilyAct myAct;
	private LinearLayout linearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.family_act);
		linearLayout = (LinearLayout)findViewById(R.id.org_view_inner);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		int index = intent.getIntExtra("index", 0);

		if (index != 0) {
			dao = new UotnodDAO_DB();
			dao.open();		
			this.myAct = dao.getFamilyActById(index);
			showAct();
			if (myAct.getOrgId() != -1) showOrg();
		} 
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
        	NavUtils.navigateUpFromSameTask(this);
        	return true;

        case R.id.action_gowebsite:
        	// Visit website
        	goWebsite();
        	return true;
        }
        return super.onOptionsItemSelected(item);
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
	    inflater.inflate(R.menu.act_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	private void showAct(){
		
		int numParam = 19;
		TextView[] tvL = new TextView[numParam];
		TextView[] tvV = new TextView[numParam];
		for (int i = 0; i < numParam; i++) {
			tvL[i] = new TextView(this);
			tvL[i].setTypeface(Typeface.DEFAULT_BOLD);
			tvV[i] = new TextView(this);
			tvV[i].setTextIsSelectable(true);
		}

		tvL[0].setText("Name: ");
		tvV[0].setText(this.myAct.getName());		
		

		tvL[1].setText("Type: ");
		tvV[1].setText(this.myAct.getType());
		

		tvL[2].setText("Description: ");
		tvV[2].setText(this.myAct.getDesc());		
		

		tvL[3].setText("Date start: ");
		tvV[3].setText(this.myAct.getDateStart());
		
		tvL[4].setText("Date end: ");
		tvV[4].setText(this.myAct.getDateEnd());
		
		tvL[5].setText("Frequency: ");
		tvV[5].setText(this.myAct.getFreq());
		
		tvL[6].setText("Timetable: ");
		tvV[6].setText(this.myAct.getTimes());
		
		tvL[7].setText("Days: ");
		tvV[7].setText(this.myAct.getDays());
		
		tvL[8].setText("Address: ");
		tvV[8].setText(this.myAct.getAddress());
		
		tvL[9].setText("Type of activity (Diurna,Residenziale): ");
		tvV[9].setText(this.myAct.getTypeDR());
		
		tvL[10].setText("Price: ");
		tvV[10].setText(this.myAct.getPrice());
		
		tvL[11].setText("Prices specifications: ");
		tvV[11].setText(this.myAct.getPriceType());
		
		tvL[12].setText("Children age: ");
		tvV[12].setText(this.myAct.getAge());
		
		tvL[13].setText("Children age notes: ");
		tvV[13].setText(this.myAct.getAgeNotes());
		
		tvL[14].setText("Residence restrictions: ");
		tvV[14].setText(this.myAct.getVinRes());
		
		tvL[15].setText("Reference person/address: ");
		tvV[15].setText(this.myAct.getRef());
		
		tvL[16].setText("Registration info: ");
		tvV[16].setText(this.myAct.getReg());
		
		tvL[17].setText("Family audit certified: ");		
		tvV[17].setTextColor(Color.parseColor("#FF0000"));
		if (this.myAct.getFamilyCert()) tvV[17].setTextColor(Color.parseColor("#009933"));
		tvV[17].setText(this.myAct.getFamilyCertFormatted());
		
		tvL[18].setText("Website: ");
		tvV[18].setText(this.myAct.getInfoLink());

		for (int i = 0; i < numParam; i++) {
			linearLayout.addView(tvL[i]);
			linearLayout.addView(tvV[i]);			
		}	
			
	}
	
	private void showOrg(){
		ArrayList<FamilyOrg> myOrgList = new ArrayList<FamilyOrg>();
		myOrgList.add(dao.getFamilyOrgById(myAct.getOrgId()));		
		OrgAdapter adapter = new OrgAdapter(this,R.layout.two_lines_list_item, myOrgList);
		ListView listView = (ListView) findViewById(R.id.listview1);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
					FamilyOrg myOrg = (FamilyOrg) arg0.getAdapter().getItem(arg2);
					int orgId = (int)myOrg.getOrgId();
			        ((ListView)arg0).setItemChecked(orgId, true);
			        Intent intent = new Intent(MyApplication.FAMILYPLUGINPKG + "FamilyOrgView");
			        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			        intent.putExtra("index", orgId);
			        startActivity(intent);
			}
		});
	}
	
	private void goWebsite(){
		String website = this.myAct.getInfoLink();
		
		if ( (!website.equals("")) ){
			if (!website.startsWith("http://") && !website.startsWith("https://")) website = "http://" + website;
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(website));
			startActivity(i);
		}
		else {	
			Toast.makeText(this, MyApplication.getAppContext().getResources().getString(R.string.nowebsite), Toast.LENGTH_SHORT).show();
		}
	}
}
