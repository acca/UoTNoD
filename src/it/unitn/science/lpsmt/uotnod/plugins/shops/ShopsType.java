package it.unitn.science.lpsmt.uotnod.plugins.shops;

import it.unitn.science.lpsmt.uotnod.plugins.Entry;

public class ShopsType extends Entry {
	

	private long id = -1;	
	private long shopId = -1;	
	private String type = null;
	
	public ShopsType (Long shopId, String type) {
		this.shopId = shopId;
		this.type = type;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public String getType() {
		return type;
	}
	
	public String getTypeFormatted() {
		return type.substring(6);
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		String entry = 
				"Type ShopId: " + shopId + "\n" +
				"Type: " + type + "\n";		
		return entry;
	}
	
	@Override
	public boolean equals(Object o) {
		if ( (o != null) && (this.shopId == ((ShopsType)o).shopId) && (this.type.equals(((ShopsType)o).type))){
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

