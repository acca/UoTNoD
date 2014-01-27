package it.unitn.science.lpsmt.uotnod;

import it.unitn.science.lpsmt.uotnod.plugins.Entry;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public abstract class UotnodXMLParser {
	// We don't use namespaces
	protected static final String ns = null;
	protected String tagName = null;
	
	public List parse(InputStream in) throws XmlPullParserException, IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();			
			return readTag(parser);
		} finally {
			in.close();
		}
	}

	protected List readTag(XmlPullParser parser) throws XmlPullParserException, IOException {
		List entries = new ArrayList();

		parser.require(XmlPullParser.START_TAG, ns, "dataroot");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
						
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals(tagName)) {
				//Log.d(MyApplication.DEBUGTAG,"Sono in UotnodParser");
				//Log.d(MyApplication.DEBUGTAG,name);
				//Log.d(MyApplication.DEBUGTAG,parser.toString());
				entries.add(readEntry(parser));
			} else {
				skip(parser);
			}
			
		}  
		return entries;
	}
	
	protected abstract Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException;

	// Processes title tags in the feed.
	protected String getValue(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, tag);
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, tag);
		return title;
	}
	
	// Processes title tags in the feed.
	/*private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
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
*/
	// For the tags title and summary, extracts their text values.
	protected String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}
	
	protected void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
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

