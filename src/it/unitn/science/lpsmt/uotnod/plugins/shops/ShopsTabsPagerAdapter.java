package it.unitn.science.lpsmt.uotnod.plugins.shops;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ShopsTabsPagerAdapter extends FragmentPagerAdapter {
		 
	public ShopsTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Shops list fragment activity
			return new ShopsShopsFragmentList();			
		case 1:
			// Map of shops fragment activity
			return new ShopsMapFragment();			
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}
}
