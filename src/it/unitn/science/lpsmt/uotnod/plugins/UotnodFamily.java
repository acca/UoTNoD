package it.unitn.science.lpsmt.uotnod.plugins;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;
import it.unitn.science.lpsmt.uotnod.R.layout;
import it.unitn.science.lpsmt.uotnod.R.menu;
import it.unitn.science.lpsmt.uotnod.UotnodXMLParser;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
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
