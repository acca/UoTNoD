package it.unitn.science.lpsmt.uotnod.plugins.family;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.UotnodXMLParser;
import it.unitn.science.lpsmt.uotnod.plugins.*;

public class FamilyActParser extends UotnodXMLParser {

	FamilyActParser(){
		super.tagName = "TAB_Attività";	
	}	
	
	// Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
	// to their respective "read" methods for processing. Otherwise, skips the tag.
	protected Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, this.tagName);
		
		long orgId = -1;
		String phone = null;
		String name = null;
		String mobile = null;
		String website = null;
		String email = null;
		
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String item = parser.getName();
			
			if (item.equals("Id_attività")){
				Log.d(MyApplication.DEBUGTAG,getValue(parser,"Id_attività"));
			}
			else if (item.equals("Frequenza")){
				Log.d(MyApplication.DEBUGTAG,getValue(parser,"Frequenza"));
			}			
			else if (item.equals("Scheda")){
				Log.d(MyApplication.DEBUGTAG,getValue(parser,"Scheda"));
			} 
			else  {			
				skip(parser);
			}
		}
		return null;
	}
}