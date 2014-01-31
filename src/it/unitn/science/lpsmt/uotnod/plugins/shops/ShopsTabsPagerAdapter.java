package it.unitn.science.lpsmt.uotnod.plugins.shops;

import it.unitn.science.lpsmt.uotnod.plugins.family.FamilyActFragmentList;
import it.unitn.science.lpsmt.uotnod.plugins.family.FamilyOrgFragmentList;
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
			// Organizations fragment activity
			return new ShopsMapFragment();			
		case 1:
			// Activities fragment activity
			return new ShopsShopsFragmentList();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}
}
