package it.unitn.science.lpsmt.uotnod.plugins.family;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import it.unitn.science.lpsmt.uotnod.UotnodXMLParser;
import it.unitn.science.lpsmt.uotnod.plugins.*;

public class FamilyOrgParser extends UotnodXMLParser {

	protected List readTag(XmlPullParser parser) throws XmlPullParserException, IOException {
		super.tagName = "TAB_org";
		parser.require(XmlPullParser.START_TAG, ns, "dataroot");
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
		List<FamilyAct> familyAct = new ArrayList<FamilyAct>();
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String item = parser.getName();
						
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
			else if (item.equals("TAB_Attività")){
				// Loop trough Activities
				FamilyActParser actParser = new FamilyActParser();
				familyAct.add(actParser.readActivity(parser));
			}				
			else {
				skip(parser);
			}
		}
		return new FamilyOrg(orgId, name, phone, mobile, website, email, familyAct);
	}
		
}