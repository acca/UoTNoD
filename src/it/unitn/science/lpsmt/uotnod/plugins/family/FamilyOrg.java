package it.unitn.science.lpsmt.uotnod.plugins.family;

import it.unitn.science.lpsmt.uotnod.plugins.Entry;

public class FamilyOrg extends Entry {
	private long orgId;
	private String name;
	private String phone;
	private String mobile;
	private String website;
	private String email;

	public FamilyOrg (Long orgId, String name, String phone,String mobile, String website, String email) {
		this.orgId = orgId;
		this.phone = phone;
		this.name = name;
		this.mobile = mobile;
		this.website = website;
		this.email = email;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
		String entry = 
				"Org name: "+ name + "\n"
						+ "Org id: " + orgId + "\n"
						+ "Org phone: " + phone + "\n";
		return entry;
	}
	
	@Override
	public boolean equals(Object o) {
		if ( (o != null) && (this.orgId == ((FamilyOrg)o).orgId) && (this.name.equals(((FamilyOrg)o).name)) ){
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

