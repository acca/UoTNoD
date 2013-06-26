package it.unitn.science.lpsmt.uotnod.plugins;

public class UotnodFamilyOrg extends Entry {
	private long orgId;
	private String name;
	private String phone;
	private String mobile;
	private String website;
	private String email;

	public UotnodFamilyOrg (Long orgId, String name, String phone,String mobile, String website, String email) {
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
						+ "Org phone: " + phone;
		return entry;
	}			
}

