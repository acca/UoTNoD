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

public class Poliparser2 {
	private static final String ns = null;

	public List parse(InputStream in) throws XmlPullParserException, IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();			
			return this.readFeed(parser);
		} finally {
			in.close();
		}
	}

	private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
		List entries = new ArrayList();

		//parser.require(XmlPullParser.START_TAG, ns, "feed");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
						
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("TAB_org")) {
				//Log.d(MyApplication.DEBUGTAG,name);
				//Log.d(MyApplication.DEBUGTAG,parser.toString());
				entries.add(readEntry(parser));
			} else {
				skip(parser);
			}
			
		}  
		return entries;
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
	// Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
	// to their respective "read" methods for processing. Otherwise, skips the tag.
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
		return new Entry(orgId, name, phone);
	}

	// Processes title tags in the feed.
	private String getValue(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, tag);
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, tag);
		return title;
	}
	
	// Processes title tags in the feed.
	private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "title");
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "title");
		return title;
	}

	// Processes link tags in the feed.
	private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
		String link = "";
		parser.require(XmlPullParser.START_TAG, ns, "link");
		String tag = parser.getName();
		String relType = parser.getAttributeValue(null, "rel");  
		if (tag.equals("link")) {
			if (relType.equals("alternate")){
				link = parser.getAttributeValue(null, "href");
				parser.nextTag();
			} 
		}
		parser.require(XmlPullParser.END_TAG, ns, "link");
		return link;
	}

	// Processes summary tags in the feed.
	private String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "summary");
		String summary = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "summary");
		return summary;
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
	
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
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
