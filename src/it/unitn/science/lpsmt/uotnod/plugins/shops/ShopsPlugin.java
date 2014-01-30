package it.unitn.science.lpsmt.uotnod.plugins.shops;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import it.unitn.science.lpsmt.uotnod.UotnodDOMParser;
import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.UotnodDAO;
import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;
import it.unitn.science.lpsmt.uotnod.plugins.Entry;
import it.unitn.science.lpsmt.uotnod.plugins.Plugin;
import it.unitn.science.lpsmt.uotnod.plugins.family.FamilyOrg;
import it.unitn.science.lpsmt.uotnod.plugins.family.FamilyOrgParser;

public class ShopsPlugin extends Plugin {

	public ShopsPlugin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ShopsPlugin(String name, String className){
		super(name,className);
	}
	
	public ShopsPlugin(long id, String name, String className,Boolean status, String description, String dataSrc){
		super(id,name,className,status,description,dataSrc);
	}
	
	public ShopsPlugin(long id, String name, String className,Boolean status, String description, String dataSrc,Boolean isEmpty){
		super(id,name,className,status,description,dataSrc,isEmpty);
	}

	public String parse(InputStream stream) {
		ShopsShopParser shopParser = new ShopsShopParser(stream); 		
		List<ShopsShop> shopInXml = null;
				
		try {
			shopInXml = shopParser.parse();
		} catch (Exception e) {
			return this.getName() + ":\n" + MyApplication.getAppContext().getResources().getString(R.string.xml_error);		
		}
		
  		UotnodDAO dao = new UotnodDAO_DB();
  		dao.open();
	    List<ShopsShop> shopInDb = dao.getAllShopsShops();
  		int shopTotal = shopInXml.size();  		
  		Log.d(MyApplication.DEBUGTAG,this.getName() + " - " + "Shops in Data source: " + shopInXml.size() + ".");
  		Log.d(MyApplication.DEBUGTAG,this.getName() + " - " + "Shops in Internal cache: " + shopInDb.size() + ".");  		
  		shopInXml.removeAll(shopInDb);
  		Iterator<ShopsShop> iterator = shopInXml.iterator();  		  		
  		Log.d(MyApplication.DEBUGTAG,this.getName() + " - " + "Shops to be updated: " + shopInXml.size() + ".");  		
  		while (iterator.hasNext()) {
  			ShopsShop shop = (ShopsShop) iterator.next();  			
  			dao.insertShopsShop(shop);
  		}
  		dao.close();
  		this.setEmpty(false);
	    return this.getName() + ":\n" + "Shops updated: " + shopInXml.size() + "/" + shopTotal;
	}
}

