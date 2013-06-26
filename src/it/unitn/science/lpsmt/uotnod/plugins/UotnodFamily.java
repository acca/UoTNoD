package it.unitn.science.lpsmt.uotnod.plugins;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.R.layout;
import it.unitn.science.lpsmt.uotnod.R.menu;
import it.unitn.science.lpsmt.uotnod.UotnodXMLParser;
import it.unitn.science.lpsmt.uotnod.UotnodXMLParsernew;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class UotnodFamily extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uotnod_family);
			
		//UotnodXMLParser parser = new UotnodXMLParser();
		UotnodFamilyOrgParser orgParser = new UotnodFamilyOrgParser();
		InputStream raw;
		List<Entry> entries = null;
		
		try {
			raw = getApplicationContext().getAssets().open("Estate-giovani-e-famiglia_2013.xml");			
			//InputStream object = this.getResources().openRawResource(R.raw.fileName);			
			try {
				entries = orgParser.parse(raw);
				
				//OrgParser orgParser = new OrgParser(raw);
				//entries = orgParser.parse();
				
				//Poliparser2 pippo = new Poliparser2(raw);				
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		Iterator<Entry> iterator = entries.iterator();
		while (iterator.hasNext()) {			
			Log.d(MyApplication.DEBUGTAG, iterator.next().toString());
		}
					
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.uotnod_family, menu);
		return true;
	}

}
