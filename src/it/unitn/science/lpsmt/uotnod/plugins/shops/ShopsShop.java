package it.unitn.science.lpsmt.uotnod.plugins.shops;

import java.util.ArrayList;
import java.util.List;

import it.unitn.science.lpsmt.uotnod.plugins.Entry;
import it.unitn.science.lpsmt.uotnod.plugins.family.FamilyAct;

public class ShopsShop extends Entry {
	private long id;
	private String name;
	private String street;
	private long streetId;
	private String streetNum;
	private String gpsPoint;
	private List<ShopsType> shopsType;

	public ShopsShop (Long orgId, String name, String street,long streetId, String streetNum, String gpsPoint) {
		this.id = orgId;
		this.street = street;
		this.name = name;
		this.streetNum = streetNum;
		this.gpsPoint = gpsPoint;
		this.shopsType = new ArrayList<ShopsType>();
	}
	
	public ShopsShop (Long orgId, String name, String street,long streetId, String streetNum, String gpsPoint, List<ShopsType> shopsType) {
		this.id = orgId;
		this.street = street;
		this.name = name;
		this.streetNum = streetNum;
		this.gpsPoint = gpsPoint;
		this.shopsType = shopsType;
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public long getStreetId() {
		return streetId;
	}

	public void setStreetId(long streetId) {
		this.streetId = streetId;
	}

	public String getStreetNum() {
		return streetNum;
	}

	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}

	public String getGpsPoint() {
		return gpsPoint;
	}

	public void setGpsPoint(String gpsPoint) {
		this.gpsPoint = gpsPoint;
	}

	public List<ShopsType> getShopsType() {
		return shopsType;
	}

	public void setShopsType(List<ShopsType> shopsType) {
		this.shopsType = shopsType;
	}

	public String toString() {
		String entry = 
				"Shop name: "+ name + "\n"
						+ "Shop id: " + this.id + "\n"
						+ "Shop address: " + this.street + " " + this.streetNum + "\n";
		return entry;
	}	
	
	
	@Override
	public boolean equals(Object o) {
		List<ShopsType> oShopsType = ((ShopsShop)o).shopsType;
		List<ShopsType> tShopsType = this.shopsType;
		if ( (o != null) && (this.id == ((ShopsShop)o).id) && (this.name.equals(((ShopsShop)o).name)) && (tShopsType.equals(oShopsType)) ){
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

