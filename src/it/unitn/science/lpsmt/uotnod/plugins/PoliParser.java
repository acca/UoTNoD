package it.unitn.science.lpsmt.uotnod.plugins;

import it.unitn.science.lpsmt.uotnod.MyApplication;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class PoliParser {
	//protected XmlPullParser parser2;
	//protected InputStream in;
	
	Integer i=0;
	
	private static final String ns = null;
	
	PoliParser(InputStream in) throws XmlPullParserException, IOException {
		//this.in = in;
		try {
			XmlPullParser parser;
			parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();			
			//return this.readFeed(parser);
			parse(parser);
		} finally {
			in.close();
		}		
	}
	public List parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		List entries = new ArrayList();

		//parser.require(XmlPullParser.START_TAG, ns, "feed");
		while (parser.next() != XmlPullParser.END_DOCUMENT) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals("Id_attivitˆ")) {
				Log.d(MyApplication.DEBUGTAG,"****"+getValue(parser,"Id_attivitˆ")+"****");
			}
			Log.d(MyApplication.DEBUGTAG,name);
			/*
			// Starts by looking for the entry tag
			if (name.equals("TAB_org")) {
				//Log.d(MyApplication.DEBUGTAG,name);
				//Log.d(MyApplication.DEBUGTAG,parser.toString());
				entries.add(readEntry());
			} else {
				skip(parser);
			}
			*/
		}  
		return entries;
	}
	
	// Processes title tags in the feed.
	protected String getValue(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, tag);
		String title = this.readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, tag);
		return title;
	}
	
	// For the tags title and summary, extracts their text values.
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}
	
	protected void skip(XmlPullParser parser1) throws XmlPullParserException, IOException {
	    if (parser1.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    int count = 0;
	    while (depth != 0) {
	    	count++;
	    	//Log.d(MyApplication.DEBUGTAG,count+"");
	    	if (count == 146) {
	    		//nothing
	    		int a = 0;
	    		a = a++;
	    	}
	        switch (parser1.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;	            
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;	           
	            break;
	        }
	    }
	 }
}

class OrgParser1 extends PoliParser {

	OrgParser1(InputStream in) throws XmlPullParserException, IOException {
		super(in);
	}
	
	
	
	
	public static class Entry {

		public final String orgId;
		public final String name;
		public final String phone;

		private Entry(String orgId, String name, String phone) {
			this.orgId = orgId;
			this.phone = phone;
			this.name = name;
		}
		
		public String toString() {
			String entry = "Org name: " + name + "Org id: " + orgId + "Org phone: " + phone;
			return entry;
		}

	}
	
	public List parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		List entries = new ArrayList();

		//parser.require(XmlPullParser.START_TAG, ns, "feed");
		while (parser.next() != XmlPullParser.END_DOCUMENT) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals("Id_attivitˆ")) {
				Log.d(MyApplication.DEBUGTAG,"****"+getValue(parser,"Id_attivitˆ")+"****");
			}
			Log.d(MyApplication.DEBUGTAG,name);
			/*
			// Starts by looking for the entry tag
			if (name.equals("TAB_org")) {
				//Log.d(MyApplication.DEBUGTAG,name);
				//Log.d(MyApplication.DEBUGTAG,parser.toString());
				entries.add(readEntry());
			} else {
				skip(parser);
			}
			*/
		}  
		return entries;
	}
	
	private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
		//parser.require(XmlPullParser.START_TAG, ns, "entry");
		String orgId = null;
		String phone = null;
		String name = null;
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			
			/*
			HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // adding each child node to HashMap key => value
            map.put(KEY_ID, parser.getValue(e, KEY_ID));
            map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
            map.put(KEY_COST, "Rs." + parser.getValue(e, KEY_COST));
            map.put(KEY_DESC, parser.getValue(e, KEY_DESC));
			*/
            
			
			
			String item = parser.getName();
			//String orgId = parser.getValue();
			
			
			if (item.equals("Nome_organizzazione"))
				
				name = getValue(parser,"Nome_organizzazione");
				
			else if (item.equals("Telefono_x0020_fisso_x0020__x0028_organizzazione_x0029_"))
				phone = getValue(parser,"Telefono_x0020_fisso_x0020__x0028_organizzazione_x0029_");
				
			else if (item.equals("ID_org"))
				orgId = getValue(parser,"ID_org");
			
			
			
			
			/*if (item.equals("Nome_organizzazione")) {
				name = getValue(parser,"Nome_organizzazione");
			/*} else if (item.equals("summary")) {
				summary = readSummary(parser);
			} else if (item.equals("link")) {
				link = readLink(parser);
				*/			
			else {
				skip(parser);
			}
			
		}
		Log.d(MyApplication.DEBUGTAG,"Org id: "+ orgId+"\n");
		return new Entry(orgId, name, phone);
	}
}
