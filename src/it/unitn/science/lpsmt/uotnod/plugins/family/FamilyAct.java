package it.unitn.science.lpsmt.uotnod.plugins.family;

import it.unitn.science.lpsmt.uotnod.MyApplication;
import it.unitn.science.lpsmt.uotnod.R;
import it.unitn.science.lpsmt.uotnod.plugins.Entry;

public class FamilyAct extends Entry {
	

	private long id = -1;	
	private long orgId = -1;
	private String name = null;
	private String type = null;
	private String desc = null;
	private String dateStart = null; // Date type
	private String dateEnd = null; // Date type
	private String freq = null;
	private String times = null;
	private String days = null;
	private String address = null;
	private String typeDR = null;
	private String priceType = null;
	private String price = null;
	private String age = null;
	private String ageNotes = null;
	private String vinRes = null;
	private String ref = null;
	private String reg = null;
	private Boolean familyCert = false;
	private String infoLink = null;
	//Comuni
	//Mese
	
	public FamilyAct (Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public FamilyAct(long id, long orgId, String name, String type,
			String desc, String dateStart, String dateEnd, String freq,
			String times, String days, String address, String typeDR,
			String priveType, String price, String age, String ageNotes,
			String vinRes, String ref, String reg, Boolean familyCert,
			String infoLink) {
		this.id = id;
		this.orgId = orgId;
		this.name = name;
		this.type = type;
		this.desc = desc;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.freq = freq;
		this.times = times;
		this.days = days;
		this.address = address;
		this.typeDR = typeDR;
		this.priceType = priveType;
		this.price = price;
		this.age = age;
		this.ageNotes = ageNotes;
		this.vinRes = vinRes;
		this.ref = ref;
		this.reg = reg;
		this.familyCert = familyCert;
		this.infoLink = infoLink;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTypeDR() {
		return typeDR;
	}

	public void setTypeDR(String typeDR) {
		this.typeDR = typeDR;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priveType) {
		this.priceType = priveType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAgeNotes() {
		return ageNotes;
	}

	public void setAgeNotes(String ageNotes) {
		this.ageNotes = ageNotes;
	}

	public String getVinRes() {
		return vinRes;
	}

	public void setVinRes(String vinRes) {
		this.vinRes = vinRes;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public Boolean getFamilyCert() {
		return familyCert;
	}
	
	public String getFamilyCertFormatted() {
		if (this.familyCert){
			return MyApplication.getAppContext().getString(R.string.act_cert_true);
		}
		else {
			return MyApplication.getAppContext().getString(R.string.act_cert_false);
		}
	}

	public void setFamilyCert(Boolean familyCert) {
		this.familyCert = familyCert;
	}

	public String getInfoLink() {
		return infoLink;
	}

	public void setInfoLink(String infoLink) {
		this.infoLink = infoLink;
	}

	public String toString() {
		String entry = 
				"Act Name: "+ name + "\n" +
				"Act Id: " + id + "\n" +
				"Act OrgId: " + orgId + "\n";		
		return entry;
	}
	
	@Override
	public boolean equals(Object o) {
		if ( (o != null) && (this.id == ((FamilyAct)o).id) && (this.name.equals(((FamilyAct)o).name)) ){
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

