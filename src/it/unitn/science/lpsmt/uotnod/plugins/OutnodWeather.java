package it.unitn.science.lpsmt.uotnod.plugins;

import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.R.layout;
import it.unitn.science.lpsmt.uotnod.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class OutnodWeather extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_outnod_weather);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.outnod_weather, menu);
		return true;
	}

}
