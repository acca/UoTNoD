package it.unitn.science.lpsmt.uotnod.plugins;

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
	
	public Plugin(){
		
	}
	
	public Plugin(String name, String className){
		this.name = name;
		this.launcher = className;
		this.status = false;
	}
	
	public Plugin(long id, String name, String className,Boolean status, String description, String dataSrc){
		this.id = -1; // Not installed
		this.name = name;
		this.launcher = className;
		this.status = status;
		this.status = false;
		this.description = description;
		this.dataSrc = dataSrc;
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

	public abstract String parse(InputStream stream);
	
}
