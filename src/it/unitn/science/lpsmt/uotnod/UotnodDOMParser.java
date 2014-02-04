package it.unitn.science.lpsmt.uotnod;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import android.util.Log;

public class UotnodDOMParser {
	String tagName = null;
    
	UotnodDOMParser(InputStream in){
		
	}

    public void parse(InputStream in){    	

    	try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            InputStream raw = in;
            Document doc = db.parse(raw);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("SchemaData");
         
            for (int i = 0; i < nodeList.getLength(); i++) {
            	
                Node node = nodeList.item(i);
      
                Element fstElmnt = (Element) node;
                NodeList nameList = fstElmnt.getElementsByTagName("SimpleData");
                Element myElement;
                
                myElement = (Element) nameList.item(0);                                
                Log.d(MyApplication.DEBUGTAG,"Element value: " + myElement.getTextContent());
                
                myElement = (Element) nameList.item(1);                                
                Log.d(MyApplication.DEBUGTAG,"Element value: " + myElement.getTextContent());

                myElement = (Element) nameList.item(2);                                
                Log.d(MyApplication.DEBUGTAG,"Element value: " + myElement.getTextContent());
                
                myElement = (Element) nameList.item(3);                                
                Log.d(MyApplication.DEBUGTAG,"Element value: " + myElement.getTextContent());
                
                myElement = (Element) nameList.item(4);                                
                Log.d(MyApplication.DEBUGTAG,"Element value: " + myElement.getTextContent());
                
                myElement = (Element) nameList.item(5);                                
                Log.d(MyApplication.DEBUGTAG,"Element value: " + myElement.getTextContent());
            }
        } catch (Exception e) {
            System.out.println("XML Parsing Excpetion = " + e);
        }

    }
}