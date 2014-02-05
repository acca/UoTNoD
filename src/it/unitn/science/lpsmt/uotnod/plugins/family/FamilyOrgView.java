package it.unitn.science.lpsmt.uotnod.plugins.family;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;
import it.unitn.science.lpsmt.uotnod.plugins.family.FamilyActFragmentList.ActAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FamilyOrgView extends Activity {

	private UotnodDAO_DB dao;
	private FamilyOrg myOrg;
	private LinearLayout linearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.family_org);
		linearLayout = (LinearLayout)findViewById(R.id.org_view_inner);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		int index = intent.getIntExtra("index", 0);

		if (index != 0) {
			dao = new UotnodDAO_DB();
			dao.open();		
			this.myOrg = dao.getFamilyOrgById(index);
			showOrg();
			if (this.myOrg.getFamilyAct() != null) showAct();
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

        case R.id.action_gomail:
        	// Send mail
        	sendMail();
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
	    inflater.inflate(R.menu.org_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	private void showOrg(){
		
		int numParam = 5;
		TextView[] tvL = new TextView[numParam];
		TextView[] tvV = new TextView[numParam];
		for (int i = 0; i < numParam; i++) {
			tvL[i] = new TextView(this);
			tvL[i].setTypeface(Typeface.DEFAULT_BOLD);
			tvV[i] = new TextView(this);
			tvV[i].setTextIsSelectable(true);
		}

		tvL[0].setText("Name: ");
		tvV[0].setText(this.myOrg.getName());		
		

		tvL[1].setText("Phone: ");
		tvV[1].setText(this.myOrg.getPhone());		
		

		tvL[2].setText("Mobile: ");
		tvV[2].setText(this.myOrg.getMobile());		
		

		tvL[3].setText("Website: ");
		tvV[3].setText(this.myOrg.getWebsite());		
		

		tvL[4].setText("E-mail: ");
		tvV[4].setText(this.myOrg.getEmail());		
		

		for (int i = 0; i < numParam; i++) {
			linearLayout.addView(tvL[i]);
			linearLayout.addView(tvV[i]);			
		}	
			
	}
	
	private void showAct(){
		ActAdapter adapter = new ActAdapter(this,R.layout.two_lines_list_item,this.myOrg.getFamilyAct());
		ListView listView = (ListView) findViewById(R.id.listview1);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
					FamilyAct myAct = (FamilyAct) arg0.getAdapter().getItem(arg2);
					int actId = (int)myAct.getId();
			        ((ListView)arg0).setItemChecked(actId, true);
			        Intent intent = new Intent(MyApplication.FAMILYPLUGINPKG + "FamilyActView");
			        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			        intent.putExtra("index", actId);
			        startActivity(intent);
			}
		});
	}
	
	private void goWebsite(){
		String website = this.myOrg.getWebsite();
		
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
	
	private void sendMail(){
		String email = this.myOrg.getEmail();
								
		if ( (!email.equals("")) ){
			Intent intent = new Intent(Intent.ACTION_SEND);			
			intent.setType("message/rfc822");
			intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
			//intent.putExtra(Intent.EXTRA_EMAIL, email);
			intent.putExtra(Intent.EXTRA_SUBJECT, "Information request for :" + this.myOrg.getName());
			intent.putExtra(Intent.EXTRA_TEXT, "My request is about....");

			startActivity(Intent.createChooser(intent, "Choose an Email client :"));
		}
		else {	
			Toast.makeText(this, MyApplication.getAppContext().getResources().getString(R.string.nomail), Toast.LENGTH_SHORT).show();
		}
	}
}
