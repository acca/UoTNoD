package it.unitn.science.lpsmt.uotnod.plugins.shops;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;

import android.os.Bundle;
import android.app.ActionBar.Tab;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.Toast;

public class Shops extends FragmentActivity implements ActionBar.TabListener {
	
	private ViewPager viewPager;
    private ShopsTabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private String filter = "ANY";
    private Boolean refresh = false;
    
    // Tab titles
    private String[] tabs = {
    		MyApplication.getAppContext().getResources().getString(R.string.shops_map_tab),
    		MyApplication.getAppContext().getResources().getString(R.string.shops_shop_tab)};
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uotnod_shops);
 
        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new ShopsTabsPagerAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
        //actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(true);
 
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));            
        }
        
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }
         
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
         
            @Override
            public void onPageScrollStateChanged(int arg0) {
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
    
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		//Toast.makeText(this, "arg0:" + arg0 + " " +"arg1:" + arg1 + " " +"arg2:" + arg2 + " ", Toast.LENGTH_LONG).show();		
		if (arg1 == Activity.RESULT_OK){
			switch (arg0) {
			case 0:
				// Coming from map filter	
				break;
			case 1:
				// Coming form Shops filter
				String tmpFilter = arg2.getExtras().getString("shopFilter"); 
				if (tmpFilter.equals(this.filter)){
					this.refresh = false;
				}
				else {
					this.refresh = true;
				}
				this.filter = tmpFilter;				
				Toast.makeText(this, MyApplication.getAppContext().getResources().getString(R.string.show_filter_msg) + this.filter, Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
				
		}
	}
	
	public String getFilter(){
		return this.filter;
	}
	
	public Boolean getRefresh(){
		return this.refresh;
	}
}


