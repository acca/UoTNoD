package it.unitn.science.lpsmt.uotnod.plugins.family;

import it.unitn.science.lpsmt.uotnod.plugins.Entry;

public class FamilyAct extends Entry {
	private long actId;
	private String name;
	
	public FamilyAct (Long actId, String name) {
		this.actId = actId;
		this.name = name;
	}

	public long getActId() {
		return actId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		String entry = 
				"Act name: "+ name + "\n" +
				"Act id: " + actId + "\n";				
		return entry;
	}
	
	@Override
	public boolean equals(Object o) {
		if ( (o != null) && (this.actId == ((FamilyAct)o).actId) && (this.name.equals(((FamilyAct)o).name)) ){
			return true;
		}
		else return false;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}

