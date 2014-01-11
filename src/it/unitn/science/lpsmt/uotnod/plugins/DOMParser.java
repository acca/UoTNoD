package it.unitn.science.lpsmt.uotnod.plugins;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;

import java.io.InputStream;
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

public class DOMParser extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Create a new layout to display the view */
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);

        /** Create a new textview array to display the results */
        TextView name[];
        TextView website[];
        TextView category[];

        try {

            //URL url = new URL("http://www.androidpeople.com/wp-content/uploads/2010/06/example.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            //Document doc = db.parse(new InputSource(url.openStream()));
            InputStream raw = getApplicationContext().getAssets().open("example.xml");
            Document doc = db.parse(raw);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("item");
            //Log.d(MyApplication.DEBUGTAG,nodeList.getLength());
            
            /** Assign textview array lenght by arraylist size */
            name = new TextView[nodeList.getLength()];
            website = new TextView[nodeList.getLength()];
            category = new TextView[nodeList.getLength()];

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                name[i] = new TextView(this);
                website[i] = new TextView(this);
                category[i] = new TextView(this);

                Element fstElmnt = (Element) node;
                NodeList nameList = fstElmnt.getElementsByTagName("name");
                Element nameElement = (Element) nameList.item(0);
                Log.d(MyApplication.DEBUGTAG,"nameElement: " + nameElement.toString());
                nameList = nameElement.getChildNodes();
                Log.d(MyApplication.DEBUGTAG,"nameList: " + nameList.toString());
                name[i].setText("Name = " + ((Node) nameList.item(0)).getNodeValue());
                Log.d(MyApplication.DEBUGTAG,name[i].getText().toString());

                NodeList websiteList = fstElmnt.getElementsByTagName("website");
                Element websiteElement = (Element) websiteList.item(0);
                websiteList = websiteElement.getChildNodes();
                website[i].setText("Website = " + ((Node) websiteList.item(0)).getNodeValue());
                Log.d(MyApplication.DEBUGTAG,website[i].getText().toString());

                category[i].setText("Website Category = " + websiteElement.getAttribute("category"));
                Log.d(MyApplication.DEBUGTAG,category[i].getText().toString());

                layout.addView(name[i]);
                layout.addView(website[i]);
                layout.addView(category[i]);
            }
        } catch (Exception e) {
            System.out.println("XML Parsing Excpetion = " + e);
        }

        /** Set the layout view to display */
        setContentView(layout);

    }
}