package it.unitn.science.lpsmt.uotnod.plugins.weather;

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
import it.unitn.science.lpsmt.uotnod.plugins.Plugin;

public class WeatherPlugin extends Plugin {

	public WeatherPlugin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public WeatherPlugin(String name, String className){
		super(name,className);
	}
	
	public WeatherPlugin(long id, String name, String className,Boolean status, String description, String dataSrc){
		super(id,name,className,status,description,dataSrc);
	}
	
	public WeatherPlugin(long id, String name, String className,Boolean status, String description, String dataSrc,Boolean isEmpty){
		super(id,name,className,status,description,dataSrc,isEmpty);
	}

	public String parse(InputStream stream) {
		return "Not still implemented";
	}
}
