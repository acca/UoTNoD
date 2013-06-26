package it.unitn.science.lpsmt.uotnod;



import it.unitn.science.lpsmt.uotnod.plugins.UotnodFamilyOrg;

import java.util.List;

public interface UotnodDAO {

	public void open();
	public void close();
	
	public Plugin insertPlugin(Plugin plugin);
	public void deletePlugin(Plugin plugin);
	public Plugin enablePlugin(Plugin plugin);
	public Plugin disablePlugin(Plugin plugin);
	public List<Plugin> getAllPlugins();
	
	public UotnodFamilyOrg insertFamilyOrg(UotnodFamilyOrg organization);
	public List<UotnodFamilyOrg> getAllFamilyOrgs();
}
