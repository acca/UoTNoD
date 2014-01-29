package it.unitn.science.lpsmt.uotnod.plugins.family;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FamilyTabsPagerAdapter extends FragmentPagerAdapter {
		 
	public FamilyTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Organizations fragment activity
			return new FamilyOrgFragment();			
		case 1:
			// Activities fragment activity
			return new FamilyActFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}
}
