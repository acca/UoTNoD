package it.unitn.science.lpsmt.uotnod;

public class Plugin {
	private long id;
	private String name;
	private String launcher;
	private boolean active;
	private String description;
	
	Plugin(){
		
	}
	
	Plugin(String name, String className){
		this.name = name;
		this.launcher = className;
		this.active = false;
	}
	
	Plugin(long id, String name, String className){
		this.id = -1; // Not installed
		this.name = name;
		this.launcher = className;
		this.active = false;	
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
	
	public boolean getActive(){
		return active;
	}
	
	public void setActive(boolean active){
		this.active = active;
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
}
