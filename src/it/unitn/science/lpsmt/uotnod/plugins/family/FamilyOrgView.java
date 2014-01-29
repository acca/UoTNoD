package it.unitn.science.lpsmt.uotnod.plugins.family;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class FamilyOrgView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		int index = intent.getIntExtra("index", 0);
		
		Toast.makeText(this, "DetailsActivity: " + index, Toast.LENGTH_SHORT).show();
		
	}
}
