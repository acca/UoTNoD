package it.unitn.science.lpsmt.uotnod.plugins;

import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.R.layout;
import it.unitn.science.lpsmt.uotnod.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UotnodFamily extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uotnod_family);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.uotnod_family, menu);
		return true;
	}

}
