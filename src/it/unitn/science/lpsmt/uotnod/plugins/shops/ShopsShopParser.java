package it.unitn.science.lpsmt.uotnod.plugins.shops;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.unitn.science.lpsmt.uotnod.plugins.shops.ShopsShop;

public class ShopsShopParser {
	private InputStream in;
	private long id;
	private String name;
	private String street;
	private long streetId;
	private String streetNum;
	private String gpsPoint;
	private String type;
	HashMap<Long,ShopsShop> hm;
	

	ShopsShopParser(InputStream in){
		this.in = in;
		this.hm = new HashMap<Long,ShopsShop>();		
	}
	
    public List<ShopsShop> parse() throws ParserConfigurationException, SAXException, IOException{

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

        
            Document doc = db.parse(this.in);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Placemark");
         
            for (int i = 0; i < nodeList.getLength(); i++) {
            	            	
                Node node = nodeList.item(i);
      
                Element myElement;
                
                Element fstElmnt = (Element) node;
                NodeList nameList = fstElmnt.getElementsByTagName("SimpleData");
                
                myElement = (Element) nameList.item(0);                                
                //Log.d(MyApplication.DEBUGTAG,"Element value: " + myElement.getTextContent());
                this.id = Long.parseLong(myElement.getTextContent());
                
                myElement = (Element) nameList.item(1);                                
                //Log.d(MyApplication.DEBUGTAG,"Element value: " + myElement.getTextContent());
                this.name = myElement.getTextContent();
                
                myElement = (Element) nameList.item(2);                                
                //Log.d(MyApplication.DEBUGTAG,"Element value: " + myElement.getTextContent());
                this.type = myElement.getTextContent();

                myElement = (Element) nameList.item(4);                                
                //Log.d(MyApplication.DEBUGTAG,"Element value: " + myElement.getTextContent());
                this.street = myElement.getTextContent();
                
                myElement = (Element) nameList.item(3);                                
                //Log.d(MyApplication.DEBUGTAG,"Element value: " + myElement.getTextContent());
                this.streetId = Long.parseLong(myElement.getTextContent());
                
                myElement = (Element) nameList.item(5);                                
                //Log.d(MyApplication.DEBUGTAG,"Element value: " + myElement.getTextContent());
                this.streetNum = myElement.getTextContent();
                
                
                nameList = fstElmnt.getElementsByTagName("coordinates");
                
                myElement = (Element) nameList.item(0);                                
                //Log.d(MyApplication.DEBUGTAG,"Element value: " + myElement.getTextContent());
                this.gpsPoint = myElement.getTextContent();                
                
                ShopsShop shop = new ShopsShop(id,name,street,streetId,streetNum,gpsPoint);
                
                if (this.hm.containsKey(shop.getId())) {
                	// Elemnto già presente, devo aggiungrgli una tipologia
                	ShopsShop hmShop = this.hm.get(shop.getId());
                	hmShop.getShopsType().add(new ShopsType(this.id,this.type));                	
                }
                else {
                	// Elemnto non presente, devo inserirlo con la sua tipologia
                	shop.getShopsType().add(new ShopsType(this.id,this.type));
                	this.hm.put(shop.getId(),shop);
                }
                
                //TYPE
                //myElement = (Element) nameList.item(2);                                
                //Log.d(MyApplication.DEBUGTAG,"Element value: " + myElement.getTextContent());
                //this.gpsPoint = myElement.getTextContent();
                
                //this.shopsShopList.add(new ShopsShop(id,name,street,streetId,streetNum,gpsPoint));
            }
		
		return new ArrayList<ShopsShop>(hm.values());
    }	
}