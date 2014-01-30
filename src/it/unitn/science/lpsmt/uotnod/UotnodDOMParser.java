package it.unitn.science.lpsmt.uotnod;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UotnodDOMParser {
	String tagName = null;
    
	UotnodDOMParser(InputStream in){
		
	}

    public void parse(InputStream in){
    	InputStream input = in;

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