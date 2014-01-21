package it.unitn.science.lpsmt.uotnod.plugins;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;

public class UotnodFamilyPlugin extends Plugin {

	public UotnodFamilyPlugin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UotnodFamilyPlugin(String name, String className){
		super(name,className);
	}
	
	public UotnodFamilyPlugin(long id, String name, String className,Boolean status, String description, String dataSrc){
		super(id,name,className,status,description,dataSrc);
	}

	public String parse(InputStream stream) {
		UotnodFamilyOrgParser orgParser = new UotnodFamilyOrgParser(); 		
		List<Entry> orgInXml = null;
		try {
			orgInXml = orgParser.parse(stream);
		} catch (XmlPullParserException e) {			
			return this.getName() + ":\n" + MyApplication.getAppContext().getResources().getString(R.string.xml_error);
		} catch (IOException e) {
			return this.getName() + ":\n" + MyApplication.getAppContext().getResources().getString(R.string.xml_error);
		}						    
  		UotnodDAO dao = new UotnodDAO_DB();
  		dao.open();  		
	    List<Entry> orgInDb = dao.getAllFamilyOrgs();
  		int orgTotal = orgInXml.size();  		
  		Log.d(MyApplication.DEBUGTAG,this.getName() + " - " + "Organizazion in Data source: " + orgInXml.size() + ".");
  		Log.d(MyApplication.DEBUGTAG,this.getName() + " - " + "Organization in Internal cache: " + orgInDb.size() + ".");  		
  		orgInXml.removeAll(orgInDb);
  		Iterator<Entry> iterator = orgInXml.iterator();  		  		
  		Log.d(MyApplication.DEBUGTAG,this.getName() + " - " + "Organizations to be updated: " + orgInXml.size() + ".");  		
  		while (iterator.hasNext()) {
  			UotnodFamilyOrg org = (UotnodFamilyOrg) iterator.next();  			
  			dao.insertFamilyOrg(org);
  		}	    
	    return this.getName() + ":\n" + "Organizations updated: " + orgInXml.size() + "/" + orgTotal;
	}
}
