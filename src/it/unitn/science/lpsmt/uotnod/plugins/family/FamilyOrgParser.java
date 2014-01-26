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

public class FamilyOrgParser extends UotnodXMLParser {

	protected List readTag(XmlPullParser parser) throws XmlPullParserException, IOException {
		super.tagName = "TAB_org";
		return super.readTag(parser);		
	}

	// Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
	// to their respective "read" methods for processing. Otherwise, skips the tag.
	protected Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, super.tagName);
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
			//String orgId = parser.getValue();
			
			if (item.equals("ID_org"))
				orgId = Integer.parseInt(getValue(parser,"ID_org"));
			else if (item.equals("Nome_organizzazione"))
				name = getValue(parser,"Nome_organizzazione");
			else if (item.equals("Telefono_x0020_fisso_x0020__x0028_organizzazione_x0029_"))
				phone = getValue(parser,"Telefono_x0020_fisso_x0020__x0028_organizzazione_x0029_");
			else if (item.equals("Telefono_x0020_mobile_x0020__x0028_organizzazione_x0029_"))
				mobile = getValue(parser,"Telefono_x0020_mobile_x0020__x0028_organizzazione_x0029_");
			else if (item.equals("Sito_x0020_web_x0020__x0028_organizzazione_x0029_"))
				website = getValue(parser,"Sito_x0020_web_x0020__x0028_organizzazione_x0029_");
			else if (item.equals("Email_x0020__x0028_organizzazione_x0029_"))
				email = getValue(parser,"Email_x0020__x0028_organizzazione_x0029_");			
			else {
				skip(parser);
			}
		}
		return new FamilyOrg(orgId, name, phone, mobile, website, email);
	}

}