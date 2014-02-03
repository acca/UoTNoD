package it.unitn.science.lpsmt.uotnod.plugins;

import it.unitn.science.lpsmt.uotnod.UotnodDAO_DB;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParserException;


public abstract class Plugin {
	private long id;
	private String name;
	private String launcher;
	private boolean status;
	private String description;
	private String dataSrc;
	private boolean isEmpty;
	
	public Plugin(){
		
	}
	
	public Plugin(String name, String className){
		this.name = name;
		this.launcher = className;
	}
	
	public Plugin(long id, String name, String className,Boolean status, String description, String dataSrc){
		this.id = id;
		this.name = name;
		this.launcher = className;
		this.status = status;
		this.description = description;
		this.dataSrc = dataSrc;
	}
	
	public Plugin(long id, String name, String className,Boolean status, String description, String dataSrc, Boolean isEmpty){
		this.id = id;
		this.name = name;
		this.launcher = className;
		this.status = status;
		this.description = description;
		this.dataSrc = dataSrc;
		this.isEmpty = isEmpty;
	}
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getLauncher(){
		return launcher;
	}
	
	public void setLauncher(String className){
		this.launcher = className;
	}
	
	public boolean getStatus(){
		return status;
	}
	
	public void setStatus(boolean status){
		this.status = status;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescritpion(String description){
		this.description = description;
	}

	public String toString() {
		return name + " " + description;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		UotnodDAO_DB dao = new UotnodDAO_DB();
		dao.open();
		dao.pluginSetEmpty(this);
		dao.close();
		this.isEmpty = isEmpty;
	}

	public abstract String parse(InputStream stream);
	
}
